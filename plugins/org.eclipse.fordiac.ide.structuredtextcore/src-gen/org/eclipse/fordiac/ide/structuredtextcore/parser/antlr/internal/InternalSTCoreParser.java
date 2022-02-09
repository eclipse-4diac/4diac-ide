package org.eclipse.fordiac.ide.structuredtextcore.parser.antlr.internal;

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
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalSTCoreParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "TIME_OF_DAY", "END_REPEAT", "VAR_OUTPUT", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "VAR_TEMP", "WSTRING", "END_FOR", "END_VAR", "STRING", "DWORD", "END_IF", "LDATE", "LREAL", "LTIME", "LWORD", "REPEAT", "RETURN", "UDINT", "ULINT", "USINT", "WCHAR", "ARRAY", "BOOL", "BYTE", "CHAR", "DATE", "DINT", "ELSIF", "FALSE", "LINT", "LTOD", "REAL", "SINT", "TIME", "UINT", "UNTIL", "WHILE", "WORD", "CASE", "ELSE", "EXIT", "INT", "LDT", "THEN", "TOD", "TRUE", "AND", "DT", "FOR", "LD", "LT", "MOD", "NOT", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "D_2", "DO", "IF", "MS", "NS", "OF", "OR", "T", "TO", "US", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "H", "M", "S", "LeftSquareBracket", "RightSquareBracket", "KW__", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_WSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=24;
    public static final int LessThanSignGreaterThanSign=74;
    public static final int VAR=63;
    public static final int END_IF=20;
    public static final int ULINT=28;
    public static final int END_CASE=13;
    public static final int LessThanSign=99;
    public static final int LeftParenthesis=89;
    public static final int BYTE=33;
    public static final int ELSE=49;
    public static final int IF=80;
    public static final int LINT=39;
    public static final int GreaterThanSign=101;
    public static final int WORD=47;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=113;
    public static final int TOD=54;
    public static final int DINT=36;
    public static final int UDINT=27;
    public static final int CASE=48;
    public static final int GreaterThanSignEqualsSign=75;
    public static final int AT=76;
    public static final int PlusSign=92;
    public static final int RULE_INT=111;
    public static final int END_FOR=16;
    public static final int RULE_ML_COMMENT=116;
    public static final int THEN=53;
    public static final int XOR=64;
    public static final int LeftSquareBracket=106;
    public static final int EXIT=50;
    public static final int TIME_OF_DAY=6;
    public static final int B=65;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=25;
    public static final int D=102;
    public static final int H=103;
    public static final int CHAR=34;
    public static final int L=67;
    public static final int M=104;
    public static final int LTIME=23;
    public static final int Comma=93;
    public static final int HyphenMinus=94;
    public static final int S=105;
    public static final int T=85;
    public static final int W=68;
    public static final int BY=77;
    public static final int X=69;
    public static final int ELSIF=37;
    public static final int END_REPEAT=7;
    public static final int LessThanSignEqualsSign=73;
    public static final int Solidus=96;
    public static final int VAR_INPUT=10;
    public static final int FullStop=95;
    public static final int VAR_TEMP=14;
    public static final int CONSTANT=11;
    public static final int KW__=108;
    public static final int CONTINUE=12;
    public static final int Semicolon=98;
    public static final int LD=59;
    public static final int VAR_OUTPUT=8;
    public static final int STRING=18;
    public static final int RULE_HEX_DIGIT=109;
    public static final int TO=86;
    public static final int UINT=44;
    public static final int LTOD=40;
    public static final int ARRAY=31;
    public static final int LT=60;
    public static final int DO=79;
    public static final int WSTRING=15;
    public static final int DT=57;
    public static final int END_VAR=17;
    public static final int FullStopFullStop=71;
    public static final int Ampersand=88;
    public static final int US=87;
    public static final int RightSquareBracket=107;
    public static final int USINT=29;
    public static final int MS=81;
    public static final int DWORD=19;
    public static final int FOR=58;
    public static final int RightParenthesis=90;
    public static final int TRUE=55;
    public static final int ColonEqualsSign=72;
    public static final int RULE_WSTRING=115;
    public static final int END_WHILE=9;
    public static final int DATE=35;
    public static final int NOT=62;
    public static final int LDATE=21;
    public static final int AND=56;
    public static final int REAL=41;
    public static final int AsteriskAsterisk=70;
    public static final int SINT=42;
    public static final int LREAL=22;
    public static final int WCHAR=30;
    public static final int NS=82;
    public static final int RULE_STRING=114;
    public static final int INT=51;
    public static final int RULE_SL_COMMENT=117;
    public static final int RETURN=26;
    public static final int EqualsSign=100;
    public static final int OF=83;
    public static final int Colon=97;
    public static final int EOF=-1;
    public static final int LDT=52;
    public static final int Asterisk=91;
    public static final int MOD=61;
    public static final int OR=84;
    public static final int RULE_WS=118;
    public static final int RULE_EXT_INT=112;
    public static final int TIME=43;
    public static final int RULE_ANY_OTHER=119;
    public static final int UNTIL=45;
    public static final int BOOL=32;
    public static final int D_2=78;
    public static final int D_1=66;
    public static final int FALSE=38;
    public static final int WHILE=46;
    public static final int RULE_NON_DECIMAL=110;

    // delegates
    // delegators


        public InternalSTCoreParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSTCoreParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSTCoreParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSTCoreParser.g"; }



     	private STCoreGrammarAccess grammarAccess;

        public InternalSTCoreParser(TokenStream input, STCoreGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Code";
       	}

       	@Override
       	protected STCoreGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleCode"
    // InternalSTCoreParser.g:58:1: entryRuleCode returns [EObject current=null] : iv_ruleCode= ruleCode EOF ;
    public final EObject entryRuleCode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCode = null;


        try {
            // InternalSTCoreParser.g:58:45: (iv_ruleCode= ruleCode EOF )
            // InternalSTCoreParser.g:59:2: iv_ruleCode= ruleCode EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCodeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCode=ruleCode();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCode; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCode"


    // $ANTLR start "ruleCode"
    // InternalSTCoreParser.g:65:1: ruleCode returns [EObject current=null] : ( (lv_statements_0_0= ruleSTStatement ) )* ;
    public final EObject ruleCode() throws RecognitionException {
        EObject current = null;

        EObject lv_statements_0_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:71:2: ( ( (lv_statements_0_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:72:2: ( (lv_statements_0_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:72:2: ( (lv_statements_0_0= ruleSTStatement ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=LDATE_AND_TIME && LA1_0<=TIME_OF_DAY)||LA1_0==CONTINUE||LA1_0==WSTRING||(LA1_0>=STRING && LA1_0<=DWORD)||(LA1_0>=LDATE && LA1_0<=WCHAR)||(LA1_0>=BOOL && LA1_0<=DINT)||(LA1_0>=FALSE && LA1_0<=UINT)||(LA1_0>=WHILE && LA1_0<=CASE)||(LA1_0>=EXIT && LA1_0<=LDT)||(LA1_0>=TOD && LA1_0<=TRUE)||(LA1_0>=DT && LA1_0<=LT)||LA1_0==D_2||LA1_0==IF||LA1_0==T||LA1_0==LeftParenthesis||LA1_0==PlusSign||LA1_0==HyphenMinus||LA1_0==Semicolon||(LA1_0>=RULE_NON_DECIMAL && LA1_0<=RULE_INT)||(LA1_0>=RULE_ID && LA1_0<=RULE_WSTRING)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSTCoreParser.g:73:3: (lv_statements_0_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:73:3: (lv_statements_0_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:74:4: lv_statements_0_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getCodeAccess().getStatementsSTStatementParserRuleCall_0());
            	      			
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_statements_0_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				if (current==null) {
            	      					current = createModelElementForParent(grammarAccess.getCodeRule());
            	      				}
            	      				add(
            	      					current,
            	      					"statements",
            	      					lv_statements_0_0,
            	      					"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCode"


    // $ANTLR start "entryRuleVarDeclaration"
    // InternalSTCoreParser.g:94:1: entryRuleVarDeclaration returns [EObject current=null] : iv_ruleVarDeclaration= ruleVarDeclaration EOF ;
    public final EObject entryRuleVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarDeclaration = null;


        try {
            // InternalSTCoreParser.g:94:55: (iv_ruleVarDeclaration= ruleVarDeclaration EOF )
            // InternalSTCoreParser.g:95:2: iv_ruleVarDeclaration= ruleVarDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVarDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVarDeclaration=ruleVarDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVarDeclaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVarDeclaration"


    // $ANTLR start "ruleVarDeclaration"
    // InternalSTCoreParser.g:101:1: ruleVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon ) ;
    public final EObject ruleVarDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_array_5_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token lv_count_12_0=null;
        Token otherlv_13=null;
        Token lv_count_14_0=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        EObject lv_ranges_7_0 = null;

        EObject lv_ranges_9_0 = null;

        EObject lv_maxLength_19_0 = null;

        EObject lv_defaultValue_22_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:107:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon ) )
            // InternalSTCoreParser.g:108:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon )
            {
            // InternalSTCoreParser.g:108:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon )
            // InternalSTCoreParser.g:109:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon
            {
            // InternalSTCoreParser.g:109:3: ()
            // InternalSTCoreParser.g:110:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalSTCoreParser.g:116:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTCoreParser.g:117:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTCoreParser.g:117:4: (lv_name_1_0= RULE_ID )
            // InternalSTCoreParser.g:118:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getVarDeclarationAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVarDeclarationRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
              				
            }

            }


            }

            // InternalSTCoreParser.g:134:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==AT) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalSTCoreParser.g:135:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalSTCoreParser.g:139:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSTCoreParser.g:140:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSTCoreParser.g:140:5: (otherlv_3= RULE_ID )
                    // InternalSTCoreParser.g:141:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Colon,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getVarDeclarationAccess().getColonKeyword_3());
              		
            }
            // InternalSTCoreParser.g:157:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ARRAY) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSTCoreParser.g:158:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalSTCoreParser.g:158:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalSTCoreParser.g:159:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalSTCoreParser.g:159:5: (lv_array_5_0= ARRAY )
                    // InternalSTCoreParser.g:160:6: lv_array_5_0= ARRAY
                    {
                    lv_array_5_0=(Token)match(input,ARRAY,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_array_5_0, grammarAccess.getVarDeclarationAccess().getArrayARRAYKeyword_4_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getVarDeclarationRule());
                      						}
                      						setWithLastConsumed(current, "array", lv_array_5_0 != null, "ARRAY");
                      					
                    }

                    }


                    }

                    // InternalSTCoreParser.g:172:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==LeftSquareBracket) ) {
                        int LA5_1 = input.LA(2);

                        if ( ((LA5_1>=LDATE_AND_TIME && LA5_1<=TIME_OF_DAY)||LA5_1==WSTRING||(LA5_1>=STRING && LA5_1<=DWORD)||(LA5_1>=LDATE && LA5_1<=LWORD)||(LA5_1>=UDINT && LA5_1<=WCHAR)||(LA5_1>=BOOL && LA5_1<=DINT)||(LA5_1>=FALSE && LA5_1<=UINT)||LA5_1==WORD||(LA5_1>=INT && LA5_1<=LDT)||(LA5_1>=TOD && LA5_1<=TRUE)||LA5_1==DT||(LA5_1>=LD && LA5_1<=LT)||LA5_1==NOT||LA5_1==D_2||LA5_1==T||LA5_1==LeftParenthesis||LA5_1==PlusSign||LA5_1==HyphenMinus||(LA5_1>=RULE_NON_DECIMAL && LA5_1<=RULE_INT)||(LA5_1>=RULE_ID && LA5_1<=RULE_WSTRING)) ) {
                            alt5=1;
                        }
                        else if ( (LA5_1==Asterisk) ) {
                            alt5=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 5, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 0, input);

                        throw nvae;
                    }
                    switch (alt5) {
                        case 1 :
                            // InternalSTCoreParser.g:173:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalSTCoreParser.g:173:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalSTCoreParser.g:174:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalSTCoreParser.g:178:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalSTCoreParser.g:179:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalSTCoreParser.g:179:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalSTCoreParser.g:180:8: lv_ranges_7_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_10);
                            lv_ranges_7_0=ruleSTExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getVarDeclarationRule());
                              								}
                              								add(
                              									current,
                              									"ranges",
                              									lv_ranges_7_0,
                              									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            // InternalSTCoreParser.g:197:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop3:
                            do {
                                int alt3=2;
                                int LA3_0 = input.LA(1);

                                if ( (LA3_0==Comma) ) {
                                    alt3=1;
                                }


                                switch (alt3) {
                            	case 1 :
                            	    // InternalSTCoreParser.g:198:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTCoreParser.g:202:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalSTCoreParser.g:203:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalSTCoreParser.g:203:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalSTCoreParser.g:204:9: lv_ranges_9_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_10);
                            	    lv_ranges_9_0=ruleSTExpression();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElementForParent(grammarAccess.getVarDeclarationRule());
                            	      									}
                            	      									add(
                            	      										current,
                            	      										"ranges",
                            	      										lv_ranges_9_0,
                            	      										"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                            	      									afterParserOrEnumRuleCall();
                            	      								
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop3;
                                }
                            } while (true);

                            otherlv_10=(Token)match(input,RightSquareBracket,FOLLOW_11); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:228:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalSTCoreParser.g:228:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalSTCoreParser.g:229:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_12); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalSTCoreParser.g:233:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalSTCoreParser.g:234:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalSTCoreParser.g:234:7: (lv_count_12_0= Asterisk )
                            // InternalSTCoreParser.g:235:8: lv_count_12_0= Asterisk
                            {
                            lv_count_12_0=(Token)match(input,Asterisk,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_count_12_0, grammarAccess.getVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getVarDeclarationRule());
                              								}
                              								addWithLastConsumed(current, "count", lv_count_12_0, "*");
                              							
                            }

                            }


                            }

                            // InternalSTCoreParser.g:247:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop4:
                            do {
                                int alt4=2;
                                int LA4_0 = input.LA(1);

                                if ( (LA4_0==Comma) ) {
                                    alt4=1;
                                }


                                switch (alt4) {
                            	case 1 :
                            	    // InternalSTCoreParser.g:248:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_12); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTCoreParser.g:252:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalSTCoreParser.g:253:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalSTCoreParser.g:253:8: (lv_count_14_0= Asterisk )
                            	    // InternalSTCoreParser.g:254:9: lv_count_14_0= Asterisk
                            	    {
                            	    lv_count_14_0=(Token)match(input,Asterisk,FOLLOW_10); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									newLeafNode(lv_count_14_0, grammarAccess.getVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_2_1_0());
                            	      								
                            	    }
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElement(grammarAccess.getVarDeclarationRule());
                            	      									}
                            	      									addWithLastConsumed(current, "count", lv_count_14_0, "*");
                            	      								
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop4;
                                }
                            } while (true);

                            otherlv_15=(Token)match(input,RightSquareBracket,FOLLOW_11); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_15, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,OF,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:278:3: ( (otherlv_17= RULE_ID ) )
            // InternalSTCoreParser.g:279:4: (otherlv_17= RULE_ID )
            {
            // InternalSTCoreParser.g:279:4: (otherlv_17= RULE_ID )
            // InternalSTCoreParser.g:280:5: otherlv_17= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVarDeclarationRule());
              					}
              				
            }
            otherlv_17=(Token)match(input,RULE_ID,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_17, grammarAccess.getVarDeclarationAccess().getTypeLibraryElementCrossReference_5_0());
              				
            }

            }


            }

            // InternalSTCoreParser.g:291:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LeftSquareBracket) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSTCoreParser.g:292:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalSTCoreParser.g:296:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:297:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:297:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalSTCoreParser.g:298:6: lv_maxLength_19_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_14);
                    lv_maxLength_19_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getVarDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"maxLength",
                      							lv_maxLength_19_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_20=(Token)match(input,RightSquareBracket,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:320:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==ColonEqualsSign) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSTCoreParser.g:321:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalSTCoreParser.g:325:4: ( (lv_defaultValue_22_0= ruleInitializerExpression ) )
                    // InternalSTCoreParser.g:326:5: (lv_defaultValue_22_0= ruleInitializerExpression )
                    {
                    // InternalSTCoreParser.g:326:5: (lv_defaultValue_22_0= ruleInitializerExpression )
                    // InternalSTCoreParser.g:327:6: lv_defaultValue_22_0= ruleInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVarDeclarationAccess().getDefaultValueInitializerExpressionParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_defaultValue_22_0=ruleInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getVarDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"defaultValue",
                      							lv_defaultValue_22_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.InitializerExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_23=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_23, grammarAccess.getVarDeclarationAccess().getSemicolonKeyword_8());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVarDeclaration"


    // $ANTLR start "entryRuleInitializerExpression"
    // InternalSTCoreParser.g:353:1: entryRuleInitializerExpression returns [EObject current=null] : iv_ruleInitializerExpression= ruleInitializerExpression EOF ;
    public final EObject entryRuleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitializerExpression = null;


        try {
            // InternalSTCoreParser.g:353:62: (iv_ruleInitializerExpression= ruleInitializerExpression EOF )
            // InternalSTCoreParser.g:354:2: iv_ruleInitializerExpression= ruleInitializerExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getInitializerExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleInitializerExpression=ruleInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleInitializerExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInitializerExpression"


    // $ANTLR start "ruleInitializerExpression"
    // InternalSTCoreParser.g:360:1: ruleInitializerExpression returns [EObject current=null] : (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) ;
    public final EObject ruleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STExpression_0 = null;

        EObject this_ArrayInitializerExpression_1 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:366:2: ( (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) )
            // InternalSTCoreParser.g:367:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            {
            // InternalSTCoreParser.g:367:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=LDATE_AND_TIME && LA9_0<=TIME_OF_DAY)||LA9_0==WSTRING||(LA9_0>=STRING && LA9_0<=DWORD)||(LA9_0>=LDATE && LA9_0<=LWORD)||(LA9_0>=UDINT && LA9_0<=WCHAR)||(LA9_0>=BOOL && LA9_0<=DINT)||(LA9_0>=FALSE && LA9_0<=UINT)||LA9_0==WORD||(LA9_0>=INT && LA9_0<=LDT)||(LA9_0>=TOD && LA9_0<=TRUE)||LA9_0==DT||(LA9_0>=LD && LA9_0<=LT)||LA9_0==NOT||LA9_0==D_2||LA9_0==T||LA9_0==LeftParenthesis||LA9_0==PlusSign||LA9_0==HyphenMinus||(LA9_0>=RULE_NON_DECIMAL && LA9_0<=RULE_INT)||(LA9_0>=RULE_ID && LA9_0<=RULE_WSTRING)) ) {
                alt9=1;
            }
            else if ( (LA9_0==LeftSquareBracket) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalSTCoreParser.g:368:3: this_STExpression_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getInitializerExpressionAccess().getSTExpressionParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STExpression_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STExpression_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:377:3: this_ArrayInitializerExpression_1= ruleArrayInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getInitializerExpressionAccess().getArrayInitializerExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ArrayInitializerExpression_1=ruleArrayInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ArrayInitializerExpression_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInitializerExpression"


    // $ANTLR start "entryRuleArrayInitializerExpression"
    // InternalSTCoreParser.g:389:1: entryRuleArrayInitializerExpression returns [EObject current=null] : iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF ;
    public final EObject entryRuleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitializerExpression = null;


        try {
            // InternalSTCoreParser.g:389:67: (iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF )
            // InternalSTCoreParser.g:390:2: iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayInitializerExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArrayInitializerExpression=ruleArrayInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrayInitializerExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrayInitializerExpression"


    // $ANTLR start "ruleArrayInitializerExpression"
    // InternalSTCoreParser.g:396:1: ruleArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:402:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTCoreParser.g:403:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTCoreParser.g:403:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTCoreParser.g:404:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTCoreParser.g:408:3: ( (lv_values_1_0= ruleArrayInitElement ) )
            // InternalSTCoreParser.g:409:4: (lv_values_1_0= ruleArrayInitElement )
            {
            // InternalSTCoreParser.g:409:4: (lv_values_1_0= ruleArrayInitElement )
            // InternalSTCoreParser.g:410:5: lv_values_1_0= ruleArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrayInitializerExpressionAccess().getValuesArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_10);
            lv_values_1_0=ruleArrayInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArrayInitializerExpressionRule());
              					}
              					add(
              						current,
              						"values",
              						lv_values_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ArrayInitElement");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:427:3: (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==Comma) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSTCoreParser.g:428:4: otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTCoreParser.g:432:4: ( (lv_values_3_0= ruleArrayInitElement ) )
            	    // InternalSTCoreParser.g:433:5: (lv_values_3_0= ruleArrayInitElement )
            	    {
            	    // InternalSTCoreParser.g:433:5: (lv_values_3_0= ruleArrayInitElement )
            	    // InternalSTCoreParser.g:434:6: lv_values_3_0= ruleArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getArrayInitializerExpressionAccess().getValuesArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_values_3_0=ruleArrayInitElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getArrayInitializerExpressionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"values",
            	      							lv_values_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.ArrayInitElement");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            otherlv_4=(Token)match(input,RightSquareBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getArrayInitializerExpressionAccess().getRightSquareBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrayInitializerExpression"


    // $ANTLR start "entryRuleArrayInitElement"
    // InternalSTCoreParser.g:460:1: entryRuleArrayInitElement returns [EObject current=null] : iv_ruleArrayInitElement= ruleArrayInitElement EOF ;
    public final EObject entryRuleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitElement = null;


        try {
            // InternalSTCoreParser.g:460:57: (iv_ruleArrayInitElement= ruleArrayInitElement EOF )
            // InternalSTCoreParser.g:461:2: iv_ruleArrayInitElement= ruleArrayInitElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayInitElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArrayInitElement=ruleArrayInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrayInitElement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrayInitElement"


    // $ANTLR start "ruleArrayInitElement"
    // InternalSTCoreParser.g:467:1: ruleArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) ;
    public final EObject ruleArrayInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_indexOrInitExpression_0_0 = null;

        EObject lv_initExpression_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:473:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) )
            // InternalSTCoreParser.g:474:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            {
            // InternalSTCoreParser.g:474:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            // InternalSTCoreParser.g:475:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            {
            // InternalSTCoreParser.g:475:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:476:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:476:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            // InternalSTCoreParser.g:477:5: lv_indexOrInitExpression_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrayInitElementAccess().getIndexOrInitExpressionSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_18);
            lv_indexOrInitExpression_0_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArrayInitElementRule());
              					}
              					set(
              						current,
              						"indexOrInitExpression",
              						lv_indexOrInitExpression_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:494:3: (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==LeftParenthesis) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSTCoreParser.g:495:4: otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalSTCoreParser.g:499:4: ( (lv_initExpression_2_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:500:5: (lv_initExpression_2_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:500:5: (lv_initExpression_2_0= ruleSTExpression )
                    // InternalSTCoreParser.g:501:6: lv_initExpression_2_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getArrayInitElementAccess().getInitExpressionSTExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_19);
                    lv_initExpression_2_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getArrayInitElementRule());
                      						}
                      						set(
                      							current,
                      							"initExpression",
                      							lv_initExpression_2_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getArrayInitElementAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrayInitElement"


    // $ANTLR start "entryRuleSTStatement"
    // InternalSTCoreParser.g:527:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTCoreParser.g:527:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTCoreParser.g:528:2: iv_ruleSTStatement= ruleSTStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTStatement=ruleSTStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTStatement"


    // $ANTLR start "ruleSTStatement"
    // InternalSTCoreParser.g:534:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) ;
    public final EObject ruleSTStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        EObject this_STIfStatment_0 = null;

        EObject this_STCaseStatement_1 = null;

        EObject this_STForStatement_2 = null;

        EObject this_STWhileStatement_3 = null;

        EObject this_STRepeatStatement_4 = null;

        EObject this_STAssignmentStatement_5 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:540:2: ( ( ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) )
            // InternalSTCoreParser.g:541:2: ( ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            {
            // InternalSTCoreParser.g:541:2: ( ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=LDATE_AND_TIME && LA13_0<=TIME_OF_DAY)||LA13_0==CONTINUE||LA13_0==WSTRING||(LA13_0>=STRING && LA13_0<=DWORD)||(LA13_0>=LDATE && LA13_0<=WCHAR)||(LA13_0>=BOOL && LA13_0<=DINT)||(LA13_0>=FALSE && LA13_0<=UINT)||(LA13_0>=WHILE && LA13_0<=CASE)||(LA13_0>=EXIT && LA13_0<=LDT)||(LA13_0>=TOD && LA13_0<=TRUE)||(LA13_0>=DT && LA13_0<=LT)||LA13_0==D_2||LA13_0==IF||LA13_0==T||LA13_0==LeftParenthesis||LA13_0==PlusSign||LA13_0==HyphenMinus||(LA13_0>=RULE_NON_DECIMAL && LA13_0<=RULE_INT)||(LA13_0>=RULE_ID && LA13_0<=RULE_WSTRING)) ) {
                alt13=1;
            }
            else if ( (LA13_0==Semicolon) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalSTCoreParser.g:542:3: ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    {
                    // InternalSTCoreParser.g:542:3: ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    // InternalSTCoreParser.g:543:4: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon
                    {
                    // InternalSTCoreParser.g:543:4: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )
                    int alt12=9;
                    switch ( input.LA(1) ) {
                    case IF:
                        {
                        alt12=1;
                        }
                        break;
                    case CASE:
                        {
                        alt12=2;
                        }
                        break;
                    case FOR:
                        {
                        alt12=3;
                        }
                        break;
                    case WHILE:
                        {
                        alt12=4;
                        }
                        break;
                    case REPEAT:
                        {
                        alt12=5;
                        }
                        break;
                    case LDATE_AND_TIME:
                    case DATE_AND_TIME:
                    case TIME_OF_DAY:
                    case WSTRING:
                    case STRING:
                    case DWORD:
                    case LDATE:
                    case LREAL:
                    case LTIME:
                    case LWORD:
                    case UDINT:
                    case ULINT:
                    case USINT:
                    case WCHAR:
                    case BOOL:
                    case BYTE:
                    case CHAR:
                    case DATE:
                    case DINT:
                    case FALSE:
                    case LINT:
                    case LTOD:
                    case REAL:
                    case SINT:
                    case TIME:
                    case UINT:
                    case WORD:
                    case INT:
                    case LDT:
                    case TOD:
                    case TRUE:
                    case DT:
                    case LD:
                    case LT:
                    case D_2:
                    case T:
                    case LeftParenthesis:
                    case PlusSign:
                    case HyphenMinus:
                    case RULE_NON_DECIMAL:
                    case RULE_INT:
                    case RULE_ID:
                    case RULE_STRING:
                    case RULE_WSTRING:
                        {
                        alt12=6;
                        }
                        break;
                    case RETURN:
                        {
                        alt12=7;
                        }
                        break;
                    case CONTINUE:
                        {
                        alt12=8;
                        }
                        break;
                    case EXIT:
                        {
                        alt12=9;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }

                    switch (alt12) {
                        case 1 :
                            // InternalSTCoreParser.g:544:5: this_STIfStatment_0= ruleSTIfStatment
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTIfStatmentParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STIfStatment_0=ruleSTIfStatment();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STIfStatment_0;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:553:5: this_STCaseStatement_1= ruleSTCaseStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCaseStatementParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STCaseStatement_1=ruleSTCaseStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STCaseStatement_1;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 3 :
                            // InternalSTCoreParser.g:562:5: this_STForStatement_2= ruleSTForStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTForStatementParserRuleCall_0_0_2());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STForStatement_2=ruleSTForStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STForStatement_2;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 4 :
                            // InternalSTCoreParser.g:571:5: this_STWhileStatement_3= ruleSTWhileStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTWhileStatementParserRuleCall_0_0_3());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STWhileStatement_3=ruleSTWhileStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STWhileStatement_3;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 5 :
                            // InternalSTCoreParser.g:580:5: this_STRepeatStatement_4= ruleSTRepeatStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTRepeatStatementParserRuleCall_0_0_4());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STRepeatStatement_4=ruleSTRepeatStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STRepeatStatement_4;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 6 :
                            // InternalSTCoreParser.g:589:5: this_STAssignmentStatement_5= ruleSTAssignmentStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentStatementParserRuleCall_0_0_5());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STAssignmentStatement_5=ruleSTAssignmentStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STAssignmentStatement_5;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 7 :
                            // InternalSTCoreParser.g:598:5: ( () otherlv_7= RETURN )
                            {
                            // InternalSTCoreParser.g:598:5: ( () otherlv_7= RETURN )
                            // InternalSTCoreParser.g:599:6: () otherlv_7= RETURN
                            {
                            // InternalSTCoreParser.g:599:6: ()
                            // InternalSTCoreParser.g:600:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_6_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_7=(Token)match(input,RETURN,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_6_1());
                              					
                            }

                            }


                            }
                            break;
                        case 8 :
                            // InternalSTCoreParser.g:612:5: ( () otherlv_9= CONTINUE )
                            {
                            // InternalSTCoreParser.g:612:5: ( () otherlv_9= CONTINUE )
                            // InternalSTCoreParser.g:613:6: () otherlv_9= CONTINUE
                            {
                            // InternalSTCoreParser.g:613:6: ()
                            // InternalSTCoreParser.g:614:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_7_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_9=(Token)match(input,CONTINUE,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_9, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_7_1());
                              					
                            }

                            }


                            }
                            break;
                        case 9 :
                            // InternalSTCoreParser.g:626:5: ( () otherlv_11= EXIT )
                            {
                            // InternalSTCoreParser.g:626:5: ( () otherlv_11= EXIT )
                            // InternalSTCoreParser.g:627:6: () otherlv_11= EXIT
                            {
                            // InternalSTCoreParser.g:627:6: ()
                            // InternalSTCoreParser.g:628:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTExitAction_0_0_8_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_11=(Token)match(input,EXIT,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTStatementAccess().getEXITKeyword_0_0_8_1());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_12=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getSTStatementAccess().getSemicolonKeyword_0_1());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:646:3: ( () otherlv_14= Semicolon )
                    {
                    // InternalSTCoreParser.g:646:3: ( () otherlv_14= Semicolon )
                    // InternalSTCoreParser.g:647:4: () otherlv_14= Semicolon
                    {
                    // InternalSTCoreParser.g:647:4: ()
                    // InternalSTCoreParser.g:648:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTNopAction_1_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_14=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getSTStatementAccess().getSemicolonKeyword_1_1());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTStatement"


    // $ANTLR start "entryRuleSTAssignmentStatement"
    // InternalSTCoreParser.g:663:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTCoreParser.g:663:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTCoreParser.g:664:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAssignmentStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAssignmentStatement=ruleSTAssignmentStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAssignmentStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAssignmentStatement"


    // $ANTLR start "ruleSTAssignmentStatement"
    // InternalSTCoreParser.g:670:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:676:2: ( ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) )
            // InternalSTCoreParser.g:677:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            {
            // InternalSTCoreParser.g:677:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            // InternalSTCoreParser.g:678:3: ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) )
            {
            // InternalSTCoreParser.g:678:3: ( (lv_left_0_0= ruleSTAccessExpression ) )
            // InternalSTCoreParser.g:679:4: (lv_left_0_0= ruleSTAccessExpression )
            {
            // InternalSTCoreParser.g:679:4: (lv_left_0_0= ruleSTAccessExpression )
            // InternalSTCoreParser.g:680:5: lv_left_0_0= ruleSTAccessExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAssignmentStatementAccess().getLeftSTAccessExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_20);
            lv_left_0_0=ruleSTAccessExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTAssignmentStatementRule());
              					}
              					set(
              						current,
              						"left",
              						lv_left_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAccessExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTAssignmentStatementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTCoreParser.g:701:3: ( (lv_right_2_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:702:4: (lv_right_2_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:702:4: (lv_right_2_0= ruleSTExpression )
            // InternalSTCoreParser.g:703:5: lv_right_2_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAssignmentStatementAccess().getRightSTExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_right_2_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTAssignmentStatementRule());
              					}
              					set(
              						current,
              						"right",
              						lv_right_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTAssignmentStatement"


    // $ANTLR start "entryRuleSTIfStatment"
    // InternalSTCoreParser.g:724:1: entryRuleSTIfStatment returns [EObject current=null] : iv_ruleSTIfStatment= ruleSTIfStatment EOF ;
    public final EObject entryRuleSTIfStatment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatment = null;


        try {
            // InternalSTCoreParser.g:724:53: (iv_ruleSTIfStatment= ruleSTIfStatment EOF )
            // InternalSTCoreParser.g:725:2: iv_ruleSTIfStatment= ruleSTIfStatment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTIfStatmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTIfStatment=ruleSTIfStatment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTIfStatment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTIfStatment"


    // $ANTLR start "ruleSTIfStatment"
    // InternalSTCoreParser.g:731:1: ruleSTIfStatment returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
    public final EObject ruleSTIfStatment() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_6=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;

        EObject lv_elseifs_4_0 = null;

        EObject lv_else_5_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:737:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTCoreParser.g:738:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTCoreParser.g:738:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTCoreParser.g:739:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatmentAccess().getIFKeyword_0());
              		
            }
            // InternalSTCoreParser.g:743:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:744:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:744:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:745:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatmentAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_21);
            lv_condition_1_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTIfStatmentRule());
              					}
              					set(
              						current,
              						"condition",
              						lv_condition_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,THEN,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatmentAccess().getTHENKeyword_2());
              		
            }
            // InternalSTCoreParser.g:766:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>=LDATE_AND_TIME && LA14_0<=TIME_OF_DAY)||LA14_0==CONTINUE||LA14_0==WSTRING||(LA14_0>=STRING && LA14_0<=DWORD)||(LA14_0>=LDATE && LA14_0<=WCHAR)||(LA14_0>=BOOL && LA14_0<=DINT)||(LA14_0>=FALSE && LA14_0<=UINT)||(LA14_0>=WHILE && LA14_0<=CASE)||(LA14_0>=EXIT && LA14_0<=LDT)||(LA14_0>=TOD && LA14_0<=TRUE)||(LA14_0>=DT && LA14_0<=LT)||LA14_0==D_2||LA14_0==IF||LA14_0==T||LA14_0==LeftParenthesis||LA14_0==PlusSign||LA14_0==HyphenMinus||LA14_0==Semicolon||(LA14_0>=RULE_NON_DECIMAL && LA14_0<=RULE_INT)||(LA14_0>=RULE_ID && LA14_0<=RULE_WSTRING)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSTCoreParser.g:767:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:767:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:768:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatmentAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_22);
            	    lv_statements_3_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTIfStatmentRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            // InternalSTCoreParser.g:785:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==ELSIF) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalSTCoreParser.g:786:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTCoreParser.g:786:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTCoreParser.g:787:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatmentAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_23);
            	    lv_elseifs_4_0=ruleSTElseIfPart();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTIfStatmentRule());
            	      					}
            	      					add(
            	      						current,
            	      						"elseifs",
            	      						lv_elseifs_4_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STElseIfPart");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            // InternalSTCoreParser.g:804:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ELSE) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSTCoreParser.g:805:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTCoreParser.g:805:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTCoreParser.g:806:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatmentAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_24);
                    lv_else_5_0=ruleSTElsePart();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTIfStatmentRule());
                      					}
                      					set(
                      						current,
                      						"else",
                      						lv_else_5_0,
                      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STElsePart");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,END_IF,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getSTIfStatmentAccess().getEND_IFKeyword_6());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTIfStatment"


    // $ANTLR start "entryRuleSTElseIfPart"
    // InternalSTCoreParser.g:831:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTCoreParser.g:831:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTCoreParser.g:832:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTElseIfPartRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTElseIfPart=ruleSTElseIfPart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTElseIfPart; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTElseIfPart"


    // $ANTLR start "ruleSTElseIfPart"
    // InternalSTCoreParser.g:838:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:844:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:845:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:845:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:846:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTCoreParser.g:850:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:851:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:851:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:852:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_21);
            lv_condition_1_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTElseIfPartRule());
              					}
              					set(
              						current,
              						"condition",
              						lv_condition_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,THEN,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalSTCoreParser.g:873:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>=LDATE_AND_TIME && LA17_0<=TIME_OF_DAY)||LA17_0==CONTINUE||LA17_0==WSTRING||(LA17_0>=STRING && LA17_0<=DWORD)||(LA17_0>=LDATE && LA17_0<=WCHAR)||(LA17_0>=BOOL && LA17_0<=DINT)||(LA17_0>=FALSE && LA17_0<=UINT)||(LA17_0>=WHILE && LA17_0<=CASE)||(LA17_0>=EXIT && LA17_0<=LDT)||(LA17_0>=TOD && LA17_0<=TRUE)||(LA17_0>=DT && LA17_0<=LT)||LA17_0==D_2||LA17_0==IF||LA17_0==T||LA17_0==LeftParenthesis||LA17_0==PlusSign||LA17_0==HyphenMinus||LA17_0==Semicolon||(LA17_0>=RULE_NON_DECIMAL && LA17_0<=RULE_INT)||(LA17_0>=RULE_ID && LA17_0<=RULE_WSTRING)) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSTCoreParser.g:874:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:874:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:875:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_statements_3_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTElseIfPartRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalSTCoreParser.g:896:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTCoreParser.g:896:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTCoreParser.g:897:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTCaseStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTCaseStatement=ruleSTCaseStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTCaseStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTCaseStatement"


    // $ANTLR start "ruleSTCaseStatement"
    // InternalSTCoreParser.g:903:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
    public final EObject ruleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_selector_1_0 = null;

        EObject lv_cases_3_0 = null;

        EObject lv_else_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:909:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTCoreParser.g:910:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTCoreParser.g:910:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTCoreParser.g:911:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTCoreParser.g:915:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:916:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:916:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:917:5: lv_selector_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_11);
            lv_selector_1_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTCaseStatementRule());
              					}
              					set(
              						current,
              						"selector",
              						lv_selector_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,OF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCaseStatementAccess().getOFKeyword_2());
              		
            }
            // InternalSTCoreParser.g:938:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=LDATE_AND_TIME && LA18_0<=TIME_OF_DAY)||LA18_0==WSTRING||(LA18_0>=STRING && LA18_0<=DWORD)||(LA18_0>=LDATE && LA18_0<=LWORD)||(LA18_0>=UDINT && LA18_0<=WCHAR)||(LA18_0>=BOOL && LA18_0<=DINT)||(LA18_0>=FALSE && LA18_0<=UINT)||LA18_0==WORD||(LA18_0>=INT && LA18_0<=LDT)||(LA18_0>=TOD && LA18_0<=TRUE)||LA18_0==DT||(LA18_0>=LD && LA18_0<=LT)||LA18_0==NOT||LA18_0==D_2||LA18_0==T||LA18_0==LeftParenthesis||LA18_0==PlusSign||LA18_0==HyphenMinus||(LA18_0>=RULE_NON_DECIMAL && LA18_0<=RULE_INT)||(LA18_0>=RULE_ID && LA18_0<=RULE_WSTRING)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalSTCoreParser.g:939:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTCoreParser.g:939:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTCoreParser.g:940:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_26);
            	    lv_cases_3_0=ruleSTCaseCases();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTCaseStatementRule());
            	      					}
            	      					add(
            	      						current,
            	      						"cases",
            	      						lv_cases_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STCaseCases");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt18 >= 1 ) break loop18;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(18, input);
                        throw eee;
                }
                cnt18++;
            } while (true);

            // InternalSTCoreParser.g:957:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ELSE) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSTCoreParser.g:958:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTCoreParser.g:958:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTCoreParser.g:959:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_27);
                    lv_else_4_0=ruleSTElsePart();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTCaseStatementRule());
                      					}
                      					set(
                      						current,
                      						"else",
                      						lv_else_4_0,
                      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STElsePart");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,END_CASE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getSTCaseStatementAccess().getEND_CASEKeyword_5());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTCaseStatement"


    // $ANTLR start "entryRuleSTCaseCases"
    // InternalSTCoreParser.g:984:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTCoreParser.g:984:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTCoreParser.g:985:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTCaseCasesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTCaseCases=ruleSTCaseCases();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTCaseCases; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTCaseCases"


    // $ANTLR start "ruleSTCaseCases"
    // InternalSTCoreParser.g:991:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:997:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:998:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:998:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:999:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:999:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1000:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1000:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTCoreParser.g:1001:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_28);
            lv_conditions_0_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTCaseCasesRule());
              					}
              					add(
              						current,
              						"conditions",
              						lv_conditions_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:1018:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==Comma) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSTCoreParser.g:1019:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTCoreParser.g:1023:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTCoreParser.g:1024:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTCoreParser.g:1024:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTCoreParser.g:1025:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_28);
            	    lv_conditions_2_0=ruleSTExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTCaseCasesRule());
            	      						}
            	      						add(
            	      							current,
            	      							"conditions",
            	      							lv_conditions_2_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1047:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop21:
            do {
                int alt21=2;
                alt21 = dfa21.predict(input);
                switch (alt21) {
            	case 1 :
            	    // InternalSTCoreParser.g:1048:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1052:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1053:5: lv_statements_4_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_statements_4_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTCaseCasesRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_4_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTCoreParser.g:1074:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTCoreParser.g:1074:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTCoreParser.g:1075:2: iv_ruleSTElsePart= ruleSTElsePart EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTElsePartRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTElsePart=ruleSTElsePart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTElsePart; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTElsePart"


    // $ANTLR start "ruleSTElsePart"
    // InternalSTCoreParser.g:1081:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1087:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:1088:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:1088:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:1089:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:1089:3: ()
            // InternalSTCoreParser.g:1090:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,ELSE,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
              		
            }
            // InternalSTCoreParser.g:1100:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>=LDATE_AND_TIME && LA22_0<=TIME_OF_DAY)||LA22_0==CONTINUE||LA22_0==WSTRING||(LA22_0>=STRING && LA22_0<=DWORD)||(LA22_0>=LDATE && LA22_0<=WCHAR)||(LA22_0>=BOOL && LA22_0<=DINT)||(LA22_0>=FALSE && LA22_0<=UINT)||(LA22_0>=WHILE && LA22_0<=CASE)||(LA22_0>=EXIT && LA22_0<=LDT)||(LA22_0>=TOD && LA22_0<=TRUE)||(LA22_0>=DT && LA22_0<=LT)||LA22_0==D_2||LA22_0==IF||LA22_0==T||LA22_0==LeftParenthesis||LA22_0==PlusSign||LA22_0==HyphenMinus||LA22_0==Semicolon||(LA22_0>=RULE_NON_DECIMAL && LA22_0<=RULE_INT)||(LA22_0>=RULE_ID && LA22_0<=RULE_WSTRING)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalSTCoreParser.g:1101:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1101:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1102:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_statements_2_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTElsePartRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_2_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalSTCoreParser.g:1123:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTCoreParser.g:1123:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTCoreParser.g:1124:2: iv_ruleSTForStatement= ruleSTForStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTForStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTForStatement=ruleSTForStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTForStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTForStatement"


    // $ANTLR start "ruleSTForStatement"
    // InternalSTCoreParser.g:1130:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) ;
    public final EObject ruleSTForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_for_1_0 = null;

        EObject lv_to_3_0 = null;

        EObject lv_by_5_0 = null;

        EObject lv_statements_7_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1136:2: ( (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) )
            // InternalSTCoreParser.g:1137:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            {
            // InternalSTCoreParser.g:1137:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            // InternalSTCoreParser.g:1138:3: otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1142:3: ( (lv_for_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1143:4: (lv_for_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1143:4: (lv_for_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1144:5: lv_for_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getForSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_29);
            lv_for_1_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTForStatementRule());
              					}
              					set(
              						current,
              						"for",
              						lv_for_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,TO,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getTOKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1165:3: ( (lv_to_3_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1166:4: (lv_to_3_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1166:4: (lv_to_3_0= ruleSTExpression )
            // InternalSTCoreParser.g:1167:5: lv_to_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_30);
            lv_to_3_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTForStatementRule());
              					}
              					set(
              						current,
              						"to",
              						lv_to_3_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:1184:3: (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==BY) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSTCoreParser.g:1185:4: otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) )
                    {
                    otherlv_4=(Token)match(input,BY,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getBYKeyword_4_0());
                      			
                    }
                    // InternalSTCoreParser.g:1189:4: ( (lv_by_5_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:1190:5: (lv_by_5_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:1190:5: (lv_by_5_0= ruleSTExpression )
                    // InternalSTCoreParser.g:1191:6: lv_by_5_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
                    lv_by_5_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTForStatementRule());
                      						}
                      						set(
                      							current,
                      							"by",
                      							lv_by_5_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,DO,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getDOKeyword_5());
              		
            }
            // InternalSTCoreParser.g:1213:3: ( (lv_statements_7_0= ruleSTStatement ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>=LDATE_AND_TIME && LA24_0<=TIME_OF_DAY)||LA24_0==CONTINUE||LA24_0==WSTRING||(LA24_0>=STRING && LA24_0<=DWORD)||(LA24_0>=LDATE && LA24_0<=WCHAR)||(LA24_0>=BOOL && LA24_0<=DINT)||(LA24_0>=FALSE && LA24_0<=UINT)||(LA24_0>=WHILE && LA24_0<=CASE)||(LA24_0>=EXIT && LA24_0<=LDT)||(LA24_0>=TOD && LA24_0<=TRUE)||(LA24_0>=DT && LA24_0<=LT)||LA24_0==D_2||LA24_0==IF||LA24_0==T||LA24_0==LeftParenthesis||LA24_0==PlusSign||LA24_0==HyphenMinus||LA24_0==Semicolon||(LA24_0>=RULE_NON_DECIMAL && LA24_0<=RULE_INT)||(LA24_0>=RULE_ID && LA24_0<=RULE_WSTRING)) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalSTCoreParser.g:1214:4: (lv_statements_7_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1214:4: (lv_statements_7_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1215:5: lv_statements_7_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_6_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
            	    lv_statements_7_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTForStatementRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_7_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            otherlv_8=(Token)match(input,END_FOR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getEND_FORKeyword_7());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTForStatement"


    // $ANTLR start "entryRuleSTWhileStatement"
    // InternalSTCoreParser.g:1240:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTCoreParser.g:1240:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTCoreParser.g:1241:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTWhileStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTWhileStatement=ruleSTWhileStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTWhileStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTWhileStatement"


    // $ANTLR start "ruleSTWhileStatement"
    // InternalSTCoreParser.g:1247:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1253:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTCoreParser.g:1254:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTCoreParser.g:1254:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTCoreParser.g:1255:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1259:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1260:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1260:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1261:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_condition_1_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTWhileStatementRule());
              					}
              					set(
              						current,
              						"condition",
              						lv_condition_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,DO,FOLLOW_33); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1282:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>=LDATE_AND_TIME && LA25_0<=TIME_OF_DAY)||LA25_0==CONTINUE||LA25_0==WSTRING||(LA25_0>=STRING && LA25_0<=DWORD)||(LA25_0>=LDATE && LA25_0<=WCHAR)||(LA25_0>=BOOL && LA25_0<=DINT)||(LA25_0>=FALSE && LA25_0<=UINT)||(LA25_0>=WHILE && LA25_0<=CASE)||(LA25_0>=EXIT && LA25_0<=LDT)||(LA25_0>=TOD && LA25_0<=TRUE)||(LA25_0>=DT && LA25_0<=LT)||LA25_0==D_2||LA25_0==IF||LA25_0==T||LA25_0==LeftParenthesis||LA25_0==PlusSign||LA25_0==HyphenMinus||LA25_0==Semicolon||(LA25_0>=RULE_NON_DECIMAL && LA25_0<=RULE_INT)||(LA25_0>=RULE_ID && LA25_0<=RULE_WSTRING)) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalSTCoreParser.g:1283:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1283:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1284:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_33);
            	    lv_statements_3_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTWhileStatementRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_WHILE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTWhileStatementAccess().getEND_WHILEKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTWhileStatement"


    // $ANTLR start "entryRuleSTRepeatStatement"
    // InternalSTCoreParser.g:1309:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTCoreParser.g:1309:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTCoreParser.g:1310:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTRepeatStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTRepeatStatement=ruleSTRepeatStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTRepeatStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTRepeatStatement"


    // $ANTLR start "ruleSTRepeatStatement"
    // InternalSTCoreParser.g:1316:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1322:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTCoreParser.g:1323:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTCoreParser.g:1323:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTCoreParser.g:1324:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1328:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>=LDATE_AND_TIME && LA26_0<=TIME_OF_DAY)||LA26_0==CONTINUE||LA26_0==WSTRING||(LA26_0>=STRING && LA26_0<=DWORD)||(LA26_0>=LDATE && LA26_0<=WCHAR)||(LA26_0>=BOOL && LA26_0<=DINT)||(LA26_0>=FALSE && LA26_0<=UINT)||(LA26_0>=WHILE && LA26_0<=CASE)||(LA26_0>=EXIT && LA26_0<=LDT)||(LA26_0>=TOD && LA26_0<=TRUE)||(LA26_0>=DT && LA26_0<=LT)||LA26_0==D_2||LA26_0==IF||LA26_0==T||LA26_0==LeftParenthesis||LA26_0==PlusSign||LA26_0==HyphenMinus||LA26_0==Semicolon||(LA26_0>=RULE_NON_DECIMAL && LA26_0<=RULE_INT)||(LA26_0>=RULE_ID && LA26_0<=RULE_WSTRING)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSTCoreParser.g:1329:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1329:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1330:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_34);
            	    lv_statements_1_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTRepeatStatementRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_1_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1351:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1352:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1352:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTCoreParser.g:1353:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_35);
            lv_condition_3_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTRepeatStatementRule());
              					}
              					set(
              						current,
              						"condition",
              						lv_condition_3_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_4=(Token)match(input,END_REPEAT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTRepeatStatementAccess().getEND_REPEATKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTRepeatStatement"


    // $ANTLR start "entryRuleSTExpression"
    // InternalSTCoreParser.g:1378:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTCoreParser.g:1378:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTCoreParser.g:1379:2: iv_ruleSTExpression= ruleSTExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTExpression=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTExpression"


    // $ANTLR start "ruleSTExpression"
    // InternalSTCoreParser.g:1385:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1391:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTCoreParser.g:1392:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getSTExpressionAccess().getSTSubrangeExpressionParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_STSubrangeExpression_0=ruleSTSubrangeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current = this_STSubrangeExpression_0;
              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTExpression"


    // $ANTLR start "entryRuleSTSubrangeExpression"
    // InternalSTCoreParser.g:1403:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTCoreParser.g:1403:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTCoreParser.g:1404:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTSubrangeExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTSubrangeExpression=ruleSTSubrangeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTSubrangeExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTSubrangeExpression"


    // $ANTLR start "ruleSTSubrangeExpression"
    // InternalSTCoreParser.g:1410:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1416:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTCoreParser.g:1417:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1417:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTCoreParser.g:1418:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_36);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1426:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==FullStopFullStop) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSTCoreParser.g:1427:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1427:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTCoreParser.g:1428:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1428:5: ()
            	    // InternalSTCoreParser.g:1429:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1435:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTCoreParser.g:1436:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTCoreParser.g:1436:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTCoreParser.g:1437:7: lv_op_2_0= ruleSubrangeOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=ruleSubrangeOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTSubrangeExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.SubrangeOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1455:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTCoreParser.g:1456:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTCoreParser.g:1456:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTCoreParser.g:1457:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_36);
            	    lv_right_3_0=ruleSTOrExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTSubrangeExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STOrExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalSTCoreParser.g:1479:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTCoreParser.g:1479:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTCoreParser.g:1480:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTOrExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTOrExpression=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTOrExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTOrExpression"


    // $ANTLR start "ruleSTOrExpression"
    // InternalSTCoreParser.g:1486:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1492:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTCoreParser.g:1493:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1493:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTCoreParser.g:1494:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_37);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1502:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==OR) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSTCoreParser.g:1503:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1503:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTCoreParser.g:1504:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1504:5: ()
            	    // InternalSTCoreParser.g:1505:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1511:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTCoreParser.g:1512:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTCoreParser.g:1512:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTCoreParser.g:1513:7: lv_op_2_0= ruleOrOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=ruleOrOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTOrExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.OrOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1531:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTCoreParser.g:1532:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTCoreParser.g:1532:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTCoreParser.g:1533:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_37);
            	    lv_right_3_0=ruleSTXorExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTOrExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STXorExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalSTCoreParser.g:1555:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTCoreParser.g:1555:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTCoreParser.g:1556:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTXorExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTXorExpression=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTXorExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTXorExpression"


    // $ANTLR start "ruleSTXorExpression"
    // InternalSTCoreParser.g:1562:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1568:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTCoreParser.g:1569:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1569:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTCoreParser.g:1570:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_38);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1578:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==XOR) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSTCoreParser.g:1579:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1579:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTCoreParser.g:1580:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1580:5: ()
            	    // InternalSTCoreParser.g:1581:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1587:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTCoreParser.g:1588:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTCoreParser.g:1588:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTCoreParser.g:1589:7: lv_op_2_0= ruleXorOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=ruleXorOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTXorExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.XorOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1607:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTCoreParser.g:1608:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTCoreParser.g:1608:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTCoreParser.g:1609:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_38);
            	    lv_right_3_0=ruleSTAndExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTXorExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAndExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalSTCoreParser.g:1631:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTCoreParser.g:1631:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTCoreParser.g:1632:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAndExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAndExpression=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAndExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAndExpression"


    // $ANTLR start "ruleSTAndExpression"
    // InternalSTCoreParser.g:1638:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1644:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTCoreParser.g:1645:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1645:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTCoreParser.g:1646:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_39);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1654:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==AND||LA30_0==Ampersand) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSTCoreParser.g:1655:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1655:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTCoreParser.g:1656:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1656:5: ()
            	    // InternalSTCoreParser.g:1657:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1663:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTCoreParser.g:1664:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTCoreParser.g:1664:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTCoreParser.g:1665:7: lv_op_2_0= ruleAndOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=ruleAndOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTAndExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.AndOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1683:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTCoreParser.g:1684:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTCoreParser.g:1684:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTCoreParser.g:1685:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_39);
            	    lv_right_3_0=ruleSTEqualityExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTAndExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STEqualityExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalSTCoreParser.g:1707:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTCoreParser.g:1707:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTCoreParser.g:1708:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTEqualityExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTEqualityExpression=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTEqualityExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTEqualityExpression"


    // $ANTLR start "ruleSTEqualityExpression"
    // InternalSTCoreParser.g:1714:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1720:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTCoreParser.g:1721:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1721:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTCoreParser.g:1722:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_40);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1730:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==LessThanSignGreaterThanSign||LA31_0==EqualsSign) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTCoreParser.g:1731:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1731:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTCoreParser.g:1732:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1732:5: ()
            	    // InternalSTCoreParser.g:1733:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1739:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTCoreParser.g:1740:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTCoreParser.g:1740:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTCoreParser.g:1741:7: lv_op_2_0= ruleEqualityOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=ruleEqualityOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTEqualityExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.EqualityOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1759:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTCoreParser.g:1760:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTCoreParser.g:1760:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTCoreParser.g:1761:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_40);
            	    lv_right_3_0=ruleSTComparisonExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTEqualityExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STComparisonExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalSTCoreParser.g:1783:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTCoreParser.g:1783:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTCoreParser.g:1784:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTComparisonExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTComparisonExpression=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTComparisonExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTComparisonExpression"


    // $ANTLR start "ruleSTComparisonExpression"
    // InternalSTCoreParser.g:1790:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1796:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTCoreParser.g:1797:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1797:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTCoreParser.g:1798:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_41);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1806:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==LessThanSignEqualsSign||LA32_0==GreaterThanSignEqualsSign||LA32_0==LessThanSign||LA32_0==GreaterThanSign) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSTCoreParser.g:1807:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1807:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTCoreParser.g:1808:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1808:5: ()
            	    // InternalSTCoreParser.g:1809:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1815:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTCoreParser.g:1816:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTCoreParser.g:1816:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTCoreParser.g:1817:7: lv_op_2_0= ruleCompareOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=ruleCompareOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTComparisonExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.CompareOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1835:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTCoreParser.g:1836:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTCoreParser.g:1836:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTCoreParser.g:1837:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_41);
            	    lv_right_3_0=ruleSTAddSubExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTComparisonExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAddSubExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalSTCoreParser.g:1859:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTCoreParser.g:1859:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTCoreParser.g:1860:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAddSubExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAddSubExpression=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAddSubExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAddSubExpression"


    // $ANTLR start "ruleSTAddSubExpression"
    // InternalSTCoreParser.g:1866:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1872:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTCoreParser.g:1873:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1873:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTCoreParser.g:1874:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_42);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1882:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==PlusSign||LA33_0==HyphenMinus) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSTCoreParser.g:1883:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1883:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTCoreParser.g:1884:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1884:5: ()
            	    // InternalSTCoreParser.g:1885:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1891:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTCoreParser.g:1892:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTCoreParser.g:1892:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTCoreParser.g:1893:7: lv_op_2_0= ruleAddSubOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=ruleAddSubOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTAddSubExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.AddSubOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1911:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTCoreParser.g:1912:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTCoreParser.g:1912:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTCoreParser.g:1913:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_42);
            	    lv_right_3_0=ruleSTMulDivModExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTAddSubExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STMulDivModExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalSTCoreParser.g:1935:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTCoreParser.g:1935:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTCoreParser.g:1936:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTMulDivModExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTMulDivModExpression=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTMulDivModExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTMulDivModExpression"


    // $ANTLR start "ruleSTMulDivModExpression"
    // InternalSTCoreParser.g:1942:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1948:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTCoreParser.g:1949:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1949:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTCoreParser.g:1950:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_43);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1958:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==MOD||LA34_0==Asterisk||LA34_0==Solidus) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTCoreParser.g:1959:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1959:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTCoreParser.g:1960:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1960:5: ()
            	    // InternalSTCoreParser.g:1961:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1967:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTCoreParser.g:1968:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTCoreParser.g:1968:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTCoreParser.g:1969:7: lv_op_2_0= ruleMulDivModOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=ruleMulDivModOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTMulDivModExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.MulDivModOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1987:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTCoreParser.g:1988:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTCoreParser.g:1988:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTCoreParser.g:1989:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_43);
            	    lv_right_3_0=ruleSTPowerExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTMulDivModExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STPowerExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTMulDivModExpression"


    // $ANTLR start "entryRuleSTPowerExpression"
    // InternalSTCoreParser.g:2011:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTCoreParser.g:2011:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTCoreParser.g:2012:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTPowerExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTPowerExpression=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTPowerExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTPowerExpression"


    // $ANTLR start "ruleSTPowerExpression"
    // InternalSTCoreParser.g:2018:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2024:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalSTCoreParser.g:2025:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2025:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalSTCoreParser.g:2026:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2034:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==AsteriskAsterisk) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSTCoreParser.g:2035:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2035:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTCoreParser.g:2036:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTCoreParser.g:2036:5: ()
            	    // InternalSTCoreParser.g:2037:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2043:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTCoreParser.g:2044:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTCoreParser.g:2044:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTCoreParser.g:2045:7: lv_op_2_0= rulePowerOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_op_2_0=rulePowerOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTPowerExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.PowerOperator");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:2063:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalSTCoreParser.g:2064:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalSTCoreParser.g:2064:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalSTCoreParser.g:2065:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
            	    lv_right_3_0=ruleSTUnaryExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTPowerExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STUnaryExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTUnaryExpression"
    // InternalSTCoreParser.g:2087:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalSTCoreParser.g:2087:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalSTCoreParser.g:2088:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTUnaryExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTUnaryExpression=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTUnaryExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTUnaryExpression"


    // $ANTLR start "ruleSTUnaryExpression"
    // InternalSTCoreParser.g:2094:1: ruleSTUnaryExpression returns [EObject current=null] : ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2100:2: ( ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalSTCoreParser.g:2101:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalSTCoreParser.g:2101:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            int alt36=2;
            alt36 = dfa36.predict(input);
            switch (alt36) {
                case 1 :
                    // InternalSTCoreParser.g:2102:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    {
                    // InternalSTCoreParser.g:2102:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    // InternalSTCoreParser.g:2103:4: ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getSTAccessExpressionParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_STAccessExpression_0=ruleSTAccessExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_STAccessExpression_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2114:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalSTCoreParser.g:2114:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    // InternalSTCoreParser.g:2115:4: () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    {
                    // InternalSTCoreParser.g:2115:4: ()
                    // InternalSTCoreParser.g:2116:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2122:4: ( (lv_op_2_0= ruleUnaryOperator ) )
                    // InternalSTCoreParser.g:2123:5: (lv_op_2_0= ruleUnaryOperator )
                    {
                    // InternalSTCoreParser.g:2123:5: (lv_op_2_0= ruleUnaryOperator )
                    // InternalSTCoreParser.g:2124:6: lv_op_2_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_9);
                    lv_op_2_0=ruleUnaryOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTUnaryExpressionRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_2_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.UnaryOperator");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSTCoreParser.g:2141:4: ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    // InternalSTCoreParser.g:2142:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    {
                    // InternalSTCoreParser.g:2142:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    // InternalSTCoreParser.g:2143:6: lv_expression_3_0= ruleSTUnaryExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getExpressionSTUnaryExpressionParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_3_0=ruleSTUnaryExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTUnaryExpressionRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_3_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STUnaryExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTUnaryExpression"


    // $ANTLR start "entryRuleSTAccessExpression"
    // InternalSTCoreParser.g:2165:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalSTCoreParser.g:2165:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalSTCoreParser.g:2166:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAccessExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAccessExpression=ruleSTAccessExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAccessExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAccessExpression"


    // $ANTLR start "ruleSTAccessExpression"
    // InternalSTCoreParser.g:2172:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
    public final EObject ruleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject this_STPrimaryExpression_0 = null;

        EObject lv_member_3_1 = null;

        EObject lv_member_3_2 = null;

        EObject lv_index_6_0 = null;

        EObject lv_index_8_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2178:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalSTCoreParser.g:2179:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalSTCoreParser.g:2179:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalSTCoreParser.g:2180:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_45);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2188:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop39:
            do {
                int alt39=3;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==FullStop) ) {
                    alt39=1;
                }
                else if ( (LA39_0==LeftSquareBracket) ) {
                    alt39=2;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSTCoreParser.g:2189:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalSTCoreParser.g:2189:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalSTCoreParser.g:2190:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalSTCoreParser.g:2190:5: ()
            	    // InternalSTCoreParser.g:2191:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_46); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalSTCoreParser.g:2201:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalSTCoreParser.g:2202:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2202:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalSTCoreParser.g:2203:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalSTCoreParser.g:2203:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt37=2;
            	    int LA37_0 = input.LA(1);

            	    if ( (LA37_0==RULE_ID) ) {
            	        alt37=1;
            	    }
            	    else if ( ((LA37_0>=B && LA37_0<=X)||LA37_0==RULE_INT) ) {
            	        alt37=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 37, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt37) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2204:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_45);
            	            lv_member_3_1=ruleSTFeatureExpression();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElementForParent(grammarAccess.getSTAccessExpressionRule());
            	              								}
            	              								set(
            	              									current,
            	              									"member",
            	              									lv_member_3_1,
            	              									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STFeatureExpression");
            	              								afterParserOrEnumRuleCall();
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:2220:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_45);
            	            lv_member_3_2=ruleSTMultibitPartialExpression();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElementForParent(grammarAccess.getSTAccessExpressionRule());
            	              								}
            	              								set(
            	              									current,
            	              									"member",
            	              									lv_member_3_2,
            	              									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STMultibitPartialExpression");
            	              								afterParserOrEnumRuleCall();
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalSTCoreParser.g:2240:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalSTCoreParser.g:2240:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalSTCoreParser.g:2241:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalSTCoreParser.g:2241:5: ()
            	    // InternalSTCoreParser.g:2242:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_5=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_5, grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1());
            	      				
            	    }
            	    // InternalSTCoreParser.g:2252:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalSTCoreParser.g:2253:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalSTCoreParser.g:2253:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalSTCoreParser.g:2254:7: lv_index_6_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_index_6_0=ruleSTExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getSTAccessExpressionRule());
            	      							}
            	      							add(
            	      								current,
            	      								"index",
            	      								lv_index_6_0,
            	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    // InternalSTCoreParser.g:2271:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop38:
            	    do {
            	        int alt38=2;
            	        int LA38_0 = input.LA(1);

            	        if ( (LA38_0==Comma) ) {
            	            alt38=1;
            	        }


            	        switch (alt38) {
            	    	case 1 :
            	    	    // InternalSTCoreParser.g:2272:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalSTCoreParser.g:2276:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalSTCoreParser.g:2277:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalSTCoreParser.g:2277:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalSTCoreParser.g:2278:8: lv_index_8_0= ruleSTExpression
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0());
            	    	      							
            	    	    }
            	    	    pushFollow(FOLLOW_10);
            	    	    lv_index_8_0=ruleSTExpression();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      								if (current==null) {
            	    	      									current = createModelElementForParent(grammarAccess.getSTAccessExpressionRule());
            	    	      								}
            	    	      								add(
            	    	      									current,
            	    	      									"index",
            	    	      									lv_index_8_0,
            	    	      									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
            	    	      								afterParserOrEnumRuleCall();
            	    	      							
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop38;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_45); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTAccessExpression"


    // $ANTLR start "entryRuleSTPrimaryExpression"
    // InternalSTCoreParser.g:2306:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalSTCoreParser.g:2306:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalSTCoreParser.g:2307:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTPrimaryExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTPrimaryExpression=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTPrimaryExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTPrimaryExpression"


    // $ANTLR start "ruleSTPrimaryExpression"
    // InternalSTCoreParser.g:2313:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STLiteralExpressions_4 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2319:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) )
            // InternalSTCoreParser.g:2320:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            {
            // InternalSTCoreParser.g:2320:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            int alt40=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt40=1;
                }
                break;
            case RULE_ID:
                {
                alt40=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case TIME_OF_DAY:
            case WSTRING:
            case STRING:
            case DWORD:
            case LDATE:
            case LREAL:
            case LTIME:
            case LWORD:
            case UDINT:
            case ULINT:
            case USINT:
            case WCHAR:
            case BOOL:
            case BYTE:
            case CHAR:
            case DATE:
            case DINT:
            case FALSE:
            case LINT:
            case LTOD:
            case REAL:
            case SINT:
            case TIME:
            case UINT:
            case WORD:
            case INT:
            case LDT:
            case TOD:
            case TRUE:
            case DT:
            case LD:
            case LT:
            case D_2:
            case T:
            case PlusSign:
            case HyphenMinus:
            case RULE_NON_DECIMAL:
            case RULE_INT:
            case RULE_STRING:
            case RULE_WSTRING:
                {
                alt40=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // InternalSTCoreParser.g:2321:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTCoreParser.g:2321:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTCoreParser.g:2322:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_19);
                    this_STExpression_1=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_STExpression_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    otherlv_2=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTPrimaryExpressionAccess().getRightParenthesisKeyword_0_2());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2340:3: this_STFeatureExpression_3= ruleSTFeatureExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTFeatureExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STFeatureExpression_3=ruleSTFeatureExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STFeatureExpression_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2349:3: this_STLiteralExpressions_4= ruleSTLiteralExpressions
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTLiteralExpressionsParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STLiteralExpressions_4=ruleSTLiteralExpressions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STLiteralExpressions_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTPrimaryExpression"


    // $ANTLR start "entryRuleSTFeatureExpression"
    // InternalSTCoreParser.g:2361:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalSTCoreParser.g:2361:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalSTCoreParser.g:2362:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTFeatureExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTFeatureExpression=ruleSTFeatureExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTFeatureExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTFeatureExpression"


    // $ANTLR start "ruleSTFeatureExpression"
    // InternalSTCoreParser.g:2368:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2374:2: ( ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalSTCoreParser.g:2375:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalSTCoreParser.g:2375:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalSTCoreParser.g:2376:3: () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalSTCoreParser.g:2376:3: ()
            // InternalSTCoreParser.g:2377:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTCoreParser.g:2383:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTCoreParser.g:2384:4: (otherlv_1= RULE_ID )
            {
            // InternalSTCoreParser.g:2384:4: (otherlv_1= RULE_ID )
            // InternalSTCoreParser.g:2385:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
              				
            }

            }


            }

            // InternalSTCoreParser.g:2396:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )?
            int alt43=2;
            alt43 = dfa43.predict(input);
            switch (alt43) {
                case 1 :
                    // InternalSTCoreParser.g:2397:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalSTCoreParser.g:2397:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis )
                    // InternalSTCoreParser.g:2398:5: ( LeftParenthesis )=>otherlv_2= LeftParenthesis
                    {
                    otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_47); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getSTFeatureExpressionAccess().getLeftParenthesisKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2404:4: ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( ((LA42_0>=LDATE_AND_TIME && LA42_0<=TIME_OF_DAY)||LA42_0==WSTRING||(LA42_0>=STRING && LA42_0<=DWORD)||(LA42_0>=LDATE && LA42_0<=LWORD)||(LA42_0>=UDINT && LA42_0<=WCHAR)||(LA42_0>=BOOL && LA42_0<=DINT)||(LA42_0>=FALSE && LA42_0<=UINT)||LA42_0==WORD||(LA42_0>=INT && LA42_0<=LDT)||(LA42_0>=TOD && LA42_0<=TRUE)||LA42_0==DT||(LA42_0>=LD && LA42_0<=LT)||LA42_0==NOT||LA42_0==D_2||LA42_0==T||LA42_0==LeftParenthesis||LA42_0==PlusSign||LA42_0==HyphenMinus||(LA42_0>=RULE_NON_DECIMAL && LA42_0<=RULE_INT)||(LA42_0>=RULE_ID && LA42_0<=RULE_WSTRING)) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // InternalSTCoreParser.g:2405:5: ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )*
                            {
                            // InternalSTCoreParser.g:2405:5: ( (lv_parameters_3_0= ruleSTExpression ) )
                            // InternalSTCoreParser.g:2406:6: (lv_parameters_3_0= ruleSTExpression )
                            {
                            // InternalSTCoreParser.g:2406:6: (lv_parameters_3_0= ruleSTExpression )
                            // InternalSTCoreParser.g:2407:7: lv_parameters_3_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTExpressionParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_48);
                            lv_parameters_3_0=ruleSTExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getSTFeatureExpressionRule());
                              							}
                              							add(
                              								current,
                              								"parameters",
                              								lv_parameters_3_0,
                              								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalSTCoreParser.g:2424:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )*
                            loop41:
                            do {
                                int alt41=2;
                                int LA41_0 = input.LA(1);

                                if ( (LA41_0==Comma) ) {
                                    alt41=1;
                                }


                                switch (alt41) {
                            	case 1 :
                            	    // InternalSTCoreParser.g:2425:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalSTCoreParser.g:2429:6: ( (lv_parameters_5_0= ruleSTExpression ) )
                            	    // InternalSTCoreParser.g:2430:7: (lv_parameters_5_0= ruleSTExpression )
                            	    {
                            	    // InternalSTCoreParser.g:2430:7: (lv_parameters_5_0= ruleSTExpression )
                            	    // InternalSTCoreParser.g:2431:8: lv_parameters_5_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTExpressionParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_48);
                            	    lv_parameters_5_0=ruleSTExpression();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getSTFeatureExpressionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"parameters",
                            	      									lv_parameters_5_0,
                            	      									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop41;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTFeatureExpressionAccess().getRightParenthesisKeyword_2_2());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTFeatureExpression"


    // $ANTLR start "entryRuleSTMultibitPartialExpression"
    // InternalSTCoreParser.g:2459:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalSTCoreParser.g:2459:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalSTCoreParser.g:2460:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTMultibitPartialExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTMultibitPartialExpression=ruleSTMultibitPartialExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTMultibitPartialExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTMultibitPartialExpression"


    // $ANTLR start "ruleSTMultibitPartialExpression"
    // InternalSTCoreParser.g:2466:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Enumerator lv_specifier_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2472:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) ) )
            // InternalSTCoreParser.g:2473:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) )
            {
            // InternalSTCoreParser.g:2473:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) )
            // InternalSTCoreParser.g:2474:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) )
            {
            // InternalSTCoreParser.g:2474:3: ()
            // InternalSTCoreParser.g:2475:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTCoreParser.g:2481:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( ((LA44_0>=B && LA44_0<=X)) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalSTCoreParser.g:2482:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalSTCoreParser.g:2482:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalSTCoreParser.g:2483:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_49);
                    lv_specifier_1_0=ruleSTMultiBitAccessSpecifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTMultibitPartialExpressionRule());
                      					}
                      					set(
                      						current,
                      						"specifier",
                      						lv_specifier_1_0,
                      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STMultiBitAccessSpecifier");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTCoreParser.g:2500:3: ( (lv_index_2_0= RULE_INT ) )
            // InternalSTCoreParser.g:2501:4: (lv_index_2_0= RULE_INT )
            {
            // InternalSTCoreParser.g:2501:4: (lv_index_2_0= RULE_INT )
            // InternalSTCoreParser.g:2502:5: lv_index_2_0= RULE_INT
            {
            lv_index_2_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_index_2_0, grammarAccess.getSTMultibitPartialExpressionAccess().getIndexINTTerminalRuleCall_2_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTMultibitPartialExpressionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"index",
              						lv_index_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.INT");
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTMultibitPartialExpression"


    // $ANTLR start "entryRuleSTLiteralExpressions"
    // InternalSTCoreParser.g:2522:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTCoreParser.g:2522:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTCoreParser.g:2523:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTLiteralExpressionsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTLiteralExpressions=ruleSTLiteralExpressions();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTLiteralExpressions; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTLiteralExpressions"


    // $ANTLR start "ruleSTLiteralExpressions"
    // InternalSTCoreParser.g:2529:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
    public final EObject ruleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject this_STNumericLiteral_0 = null;

        EObject this_STDateLiteral_1 = null;

        EObject this_STTimeLiteral_2 = null;

        EObject this_STTimeOfDayLiteral_3 = null;

        EObject this_STDateAndTimeLiteral_4 = null;

        EObject this_STStringLiteral_5 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2535:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalSTCoreParser.g:2536:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalSTCoreParser.g:2536:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt45=6;
            switch ( input.LA(1) ) {
            case DWORD:
            case LREAL:
            case LWORD:
            case UDINT:
            case ULINT:
            case USINT:
            case BOOL:
            case BYTE:
            case DINT:
            case FALSE:
            case LINT:
            case REAL:
            case SINT:
            case UINT:
            case WORD:
            case INT:
            case TRUE:
            case PlusSign:
            case HyphenMinus:
            case RULE_NON_DECIMAL:
            case RULE_INT:
                {
                alt45=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D_2:
                {
                alt45=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt45=3;
                }
                break;
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt45=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt45=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
            case RULE_WSTRING:
                {
                alt45=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }

            switch (alt45) {
                case 1 :
                    // InternalSTCoreParser.g:2537:3: this_STNumericLiteral_0= ruleSTNumericLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTNumericLiteralParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STNumericLiteral_0=ruleSTNumericLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STNumericLiteral_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2546:3: this_STDateLiteral_1= ruleSTDateLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTDateLiteralParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STDateLiteral_1=ruleSTDateLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STDateLiteral_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2555:3: this_STTimeLiteral_2= ruleSTTimeLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTTimeLiteralParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STTimeLiteral_2=ruleSTTimeLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STTimeLiteral_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:2564:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTTimeOfDayLiteralParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STTimeOfDayLiteral_3=ruleSTTimeOfDayLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STTimeOfDayLiteral_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:2573:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTDateAndTimeLiteralParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STDateAndTimeLiteral_4=ruleSTDateAndTimeLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STDateAndTimeLiteral_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTCoreParser.g:2582:3: this_STStringLiteral_5= ruleSTStringLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTStringLiteralParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STStringLiteral_5=ruleSTStringLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STStringLiteral_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTLiteralExpressions"


    // $ANTLR start "entryRuleSTNumericLiteralType"
    // InternalSTCoreParser.g:2594:1: entryRuleSTNumericLiteralType returns [EObject current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final EObject entryRuleSTNumericLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteralType = null;


        try {
            // InternalSTCoreParser.g:2594:61: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalSTCoreParser.g:2595:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTNumericLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTNumericLiteralType=ruleSTNumericLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTNumericLiteralType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTNumericLiteralType"


    // $ANTLR start "ruleSTNumericLiteralType"
    // InternalSTCoreParser.g:2601:1: ruleSTNumericLiteralType returns [EObject current=null] : (otherlv_0= BOOL | otherlv_1= BYTE | otherlv_2= WORD | otherlv_3= DWORD | otherlv_4= LWORD | otherlv_5= SINT | otherlv_6= INT | otherlv_7= DINT | otherlv_8= LINT | otherlv_9= USINT | otherlv_10= UINT | otherlv_11= UDINT | otherlv_12= ULINT | otherlv_13= REAL | otherlv_14= LREAL ) ;
    public final EObject ruleSTNumericLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_14=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:2607:2: ( (otherlv_0= BOOL | otherlv_1= BYTE | otherlv_2= WORD | otherlv_3= DWORD | otherlv_4= LWORD | otherlv_5= SINT | otherlv_6= INT | otherlv_7= DINT | otherlv_8= LINT | otherlv_9= USINT | otherlv_10= UINT | otherlv_11= UDINT | otherlv_12= ULINT | otherlv_13= REAL | otherlv_14= LREAL ) )
            // InternalSTCoreParser.g:2608:2: (otherlv_0= BOOL | otherlv_1= BYTE | otherlv_2= WORD | otherlv_3= DWORD | otherlv_4= LWORD | otherlv_5= SINT | otherlv_6= INT | otherlv_7= DINT | otherlv_8= LINT | otherlv_9= USINT | otherlv_10= UINT | otherlv_11= UDINT | otherlv_12= ULINT | otherlv_13= REAL | otherlv_14= LREAL )
            {
            // InternalSTCoreParser.g:2608:2: (otherlv_0= BOOL | otherlv_1= BYTE | otherlv_2= WORD | otherlv_3= DWORD | otherlv_4= LWORD | otherlv_5= SINT | otherlv_6= INT | otherlv_7= DINT | otherlv_8= LINT | otherlv_9= USINT | otherlv_10= UINT | otherlv_11= UDINT | otherlv_12= ULINT | otherlv_13= REAL | otherlv_14= LREAL )
            int alt46=15;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt46=1;
                }
                break;
            case BYTE:
                {
                alt46=2;
                }
                break;
            case WORD:
                {
                alt46=3;
                }
                break;
            case DWORD:
                {
                alt46=4;
                }
                break;
            case LWORD:
                {
                alt46=5;
                }
                break;
            case SINT:
                {
                alt46=6;
                }
                break;
            case INT:
                {
                alt46=7;
                }
                break;
            case DINT:
                {
                alt46=8;
                }
                break;
            case LINT:
                {
                alt46=9;
                }
                break;
            case USINT:
                {
                alt46=10;
                }
                break;
            case UINT:
                {
                alt46=11;
                }
                break;
            case UDINT:
                {
                alt46=12;
                }
                break;
            case ULINT:
                {
                alt46=13;
                }
                break;
            case REAL:
                {
                alt46=14;
                }
                break;
            case LREAL:
                {
                alt46=15;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // InternalSTCoreParser.g:2609:3: otherlv_0= BOOL
                    {
                    otherlv_0=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTNumericLiteralTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2614:3: otherlv_1= BYTE
                    {
                    otherlv_1=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2619:3: otherlv_2= WORD
                    {
                    otherlv_2=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTNumericLiteralTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:2624:3: otherlv_3= DWORD
                    {
                    otherlv_3=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_3, grammarAccess.getSTNumericLiteralTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:2629:3: otherlv_4= LWORD
                    {
                    otherlv_4=(Token)match(input,LWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_4, grammarAccess.getSTNumericLiteralTypeAccess().getLWORDKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTCoreParser.g:2634:3: otherlv_5= SINT
                    {
                    otherlv_5=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_5, grammarAccess.getSTNumericLiteralTypeAccess().getSINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTCoreParser.g:2639:3: otherlv_6= INT
                    {
                    otherlv_6=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_6, grammarAccess.getSTNumericLiteralTypeAccess().getINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTCoreParser.g:2644:3: otherlv_7= DINT
                    {
                    otherlv_7=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_7, grammarAccess.getSTNumericLiteralTypeAccess().getDINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTCoreParser.g:2649:3: otherlv_8= LINT
                    {
                    otherlv_8=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_8, grammarAccess.getSTNumericLiteralTypeAccess().getLINTKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSTCoreParser.g:2654:3: otherlv_9= USINT
                    {
                    otherlv_9=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_9, grammarAccess.getSTNumericLiteralTypeAccess().getUSINTKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalSTCoreParser.g:2659:3: otherlv_10= UINT
                    {
                    otherlv_10=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_10, grammarAccess.getSTNumericLiteralTypeAccess().getUINTKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalSTCoreParser.g:2664:3: otherlv_11= UDINT
                    {
                    otherlv_11=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_11, grammarAccess.getSTNumericLiteralTypeAccess().getUDINTKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalSTCoreParser.g:2669:3: otherlv_12= ULINT
                    {
                    otherlv_12=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_12, grammarAccess.getSTNumericLiteralTypeAccess().getULINTKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalSTCoreParser.g:2674:3: otherlv_13= REAL
                    {
                    otherlv_13=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_13, grammarAccess.getSTNumericLiteralTypeAccess().getREALKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalSTCoreParser.g:2679:3: otherlv_14= LREAL
                    {
                    otherlv_14=(Token)match(input,LREAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_14, grammarAccess.getSTNumericLiteralTypeAccess().getLREALKeyword_14());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTNumericLiteralType"


    // $ANTLR start "entryRuleSTNumericLiteral"
    // InternalSTCoreParser.g:2687:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalSTCoreParser.g:2687:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalSTCoreParser.g:2688:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTNumericLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTNumericLiteral=ruleSTNumericLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTNumericLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTNumericLiteral"


    // $ANTLR start "ruleSTNumericLiteral"
    // InternalSTCoreParser.g:2694:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_1_3=null;
        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_1_1 = null;

        AntlrDatatypeRuleToken lv_value_1_2 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2700:2: ( ( ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTCoreParser.g:2701:2: ( ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTCoreParser.g:2701:2: ( ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) ) )
            // InternalSTCoreParser.g:2702:3: ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTCoreParser.g:2702:3: ( (lv_type_0_0= ruleSTNumericLiteralType ) )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==DWORD||LA47_0==LREAL||LA47_0==LWORD||(LA47_0>=UDINT && LA47_0<=USINT)||(LA47_0>=BOOL && LA47_0<=BYTE)||LA47_0==DINT||LA47_0==LINT||(LA47_0>=REAL && LA47_0<=SINT)||LA47_0==UINT||LA47_0==WORD||LA47_0==INT) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalSTCoreParser.g:2703:4: (lv_type_0_0= ruleSTNumericLiteralType )
                    {
                    // InternalSTCoreParser.g:2703:4: (lv_type_0_0= ruleSTNumericLiteralType )
                    // InternalSTCoreParser.g:2704:5: lv_type_0_0= ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeSTNumericLiteralTypeParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_50);
                    lv_type_0_0=ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
                      					}
                      					set(
                      						current,
                      						"type",
                      						lv_type_0_0,
                      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STNumericLiteralType");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTCoreParser.g:2721:3: ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) )
            // InternalSTCoreParser.g:2722:4: ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) )
            {
            // InternalSTCoreParser.g:2722:4: ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) )
            // InternalSTCoreParser.g:2723:5: (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL )
            {
            // InternalSTCoreParser.g:2723:5: (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL )
            int alt48=3;
            switch ( input.LA(1) ) {
            case FALSE:
            case TRUE:
                {
                alt48=1;
                }
                break;
            case PlusSign:
            case HyphenMinus:
            case RULE_INT:
                {
                alt48=2;
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt48=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }

            switch (alt48) {
                case 1 :
                    // InternalSTCoreParser.g:2724:6: lv_value_1_1= ruleBoolLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueBoolLiteralParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_1_1=ruleBoolLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_1_1,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.BoolLiteral");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2740:6: lv_value_1_2= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueNumberParserRuleCall_1_0_1());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_1_2=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_1_2,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2756:6: lv_value_1_3= RULE_NON_DECIMAL
                    {
                    lv_value_1_3=(Token)match(input,RULE_NON_DECIMAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_1_3, grammarAccess.getSTNumericLiteralAccess().getValueNON_DECIMALTerminalRuleCall_1_0_2());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"value",
                      							lv_value_1_3,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.NON_DECIMAL");
                      					
                    }

                    }
                    break;

            }


            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTNumericLiteral"


    // $ANTLR start "entryRuleSTDateLiteralType"
    // InternalSTCoreParser.g:2777:1: entryRuleSTDateLiteralType returns [EObject current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final EObject entryRuleSTDateLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteralType = null;


        try {
            // InternalSTCoreParser.g:2777:58: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalSTCoreParser.g:2778:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTDateLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTDateLiteralType=ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTDateLiteralType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTDateLiteralType"


    // $ANTLR start "ruleSTDateLiteralType"
    // InternalSTCoreParser.g:2784:1: ruleSTDateLiteralType returns [EObject current=null] : (otherlv_0= DATE | otherlv_1= LDATE | otherlv_2= D_2 | otherlv_3= LD ) ;
    public final EObject ruleSTDateLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:2790:2: ( (otherlv_0= DATE | otherlv_1= LDATE | otherlv_2= D_2 | otherlv_3= LD ) )
            // InternalSTCoreParser.g:2791:2: (otherlv_0= DATE | otherlv_1= LDATE | otherlv_2= D_2 | otherlv_3= LD )
            {
            // InternalSTCoreParser.g:2791:2: (otherlv_0= DATE | otherlv_1= LDATE | otherlv_2= D_2 | otherlv_3= LD )
            int alt49=4;
            switch ( input.LA(1) ) {
            case DATE:
                {
                alt49=1;
                }
                break;
            case LDATE:
                {
                alt49=2;
                }
                break;
            case D_2:
                {
                alt49=3;
                }
                break;
            case LD:
                {
                alt49=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }

            switch (alt49) {
                case 1 :
                    // InternalSTCoreParser.g:2792:3: otherlv_0= DATE
                    {
                    otherlv_0=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTDateLiteralTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2797:3: otherlv_1= LDATE
                    {
                    otherlv_1=(Token)match(input,LDATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralTypeAccess().getLDATEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2802:3: otherlv_2= D_2
                    {
                    otherlv_2=(Token)match(input,D_2,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:2807:3: otherlv_3= LD
                    {
                    otherlv_3=(Token)match(input,LD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_3, grammarAccess.getSTDateLiteralTypeAccess().getLDKeyword_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTDateLiteralType"


    // $ANTLR start "entryRuleSTDateLiteral"
    // InternalSTCoreParser.g:2815:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalSTCoreParser.g:2815:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalSTCoreParser.g:2816:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTDateLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTDateLiteral=ruleSTDateLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTDateLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTDateLiteral"


    // $ANTLR start "ruleSTDateLiteral"
    // InternalSTCoreParser.g:2822:1: ruleSTDateLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2828:2: ( ( ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) ) ) )
            // InternalSTCoreParser.g:2829:2: ( ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) ) )
            {
            // InternalSTCoreParser.g:2829:2: ( ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) ) )
            // InternalSTCoreParser.g:2830:3: ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) )
            {
            // InternalSTCoreParser.g:2830:3: ( (lv_type_0_0= ruleSTDateLiteralType ) )
            // InternalSTCoreParser.g:2831:4: (lv_type_0_0= ruleSTDateLiteralType )
            {
            // InternalSTCoreParser.g:2831:4: (lv_type_0_0= ruleSTDateLiteralType )
            // InternalSTCoreParser.g:2832:5: lv_type_0_0= ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeSTDateLiteralTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_49);
            lv_type_0_0=ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTDateLiteralRule());
              					}
              					set(
              						current,
              						"type",
              						lv_type_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STDateLiteralType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:2849:3: ( (lv_value_1_0= ruleDate ) )
            // InternalSTCoreParser.g:2850:4: (lv_value_1_0= ruleDate )
            {
            // InternalSTCoreParser.g:2850:4: (lv_value_1_0= ruleDate )
            // InternalSTCoreParser.g:2851:5: lv_value_1_0= ruleDate
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getValueDateParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_1_0=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTDateLiteralRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.Date");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTDateLiteral"


    // $ANTLR start "entryRuleSTTimeLiteralType"
    // InternalSTCoreParser.g:2872:1: entryRuleSTTimeLiteralType returns [EObject current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final EObject entryRuleSTTimeLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteralType = null;


        try {
            // InternalSTCoreParser.g:2872:58: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalSTCoreParser.g:2873:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTTimeLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTTimeLiteralType=ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTTimeLiteralType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTTimeLiteralType"


    // $ANTLR start "ruleSTTimeLiteralType"
    // InternalSTCoreParser.g:2879:1: ruleSTTimeLiteralType returns [EObject current=null] : (otherlv_0= TIME | otherlv_1= LTIME | otherlv_2= T | otherlv_3= LT ) ;
    public final EObject ruleSTTimeLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:2885:2: ( (otherlv_0= TIME | otherlv_1= LTIME | otherlv_2= T | otherlv_3= LT ) )
            // InternalSTCoreParser.g:2886:2: (otherlv_0= TIME | otherlv_1= LTIME | otherlv_2= T | otherlv_3= LT )
            {
            // InternalSTCoreParser.g:2886:2: (otherlv_0= TIME | otherlv_1= LTIME | otherlv_2= T | otherlv_3= LT )
            int alt50=4;
            switch ( input.LA(1) ) {
            case TIME:
                {
                alt50=1;
                }
                break;
            case LTIME:
                {
                alt50=2;
                }
                break;
            case T:
                {
                alt50=3;
                }
                break;
            case LT:
                {
                alt50=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // InternalSTCoreParser.g:2887:3: otherlv_0= TIME
                    {
                    otherlv_0=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTTimeLiteralTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2892:3: otherlv_1= LTIME
                    {
                    otherlv_1=(Token)match(input,LTIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralTypeAccess().getLTIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2897:3: otherlv_2= T
                    {
                    otherlv_2=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:2902:3: otherlv_3= LT
                    {
                    otherlv_3=(Token)match(input,LT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_3, grammarAccess.getSTTimeLiteralTypeAccess().getLTKeyword_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTTimeLiteralType"


    // $ANTLR start "entryRuleSTTimeLiteral"
    // InternalSTCoreParser.g:2910:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalSTCoreParser.g:2910:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalSTCoreParser.g:2911:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTTimeLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTTimeLiteral=ruleSTTimeLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTTimeLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTTimeLiteral"


    // $ANTLR start "ruleSTTimeLiteral"
    // InternalSTCoreParser.g:2917:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2923:2: ( ( ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) ) ) )
            // InternalSTCoreParser.g:2924:2: ( ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) ) )
            {
            // InternalSTCoreParser.g:2924:2: ( ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) ) )
            // InternalSTCoreParser.g:2925:3: ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) )
            {
            // InternalSTCoreParser.g:2925:3: ( (lv_type_0_0= ruleSTTimeLiteralType ) )
            // InternalSTCoreParser.g:2926:4: (lv_type_0_0= ruleSTTimeLiteralType )
            {
            // InternalSTCoreParser.g:2926:4: (lv_type_0_0= ruleSTTimeLiteralType )
            // InternalSTCoreParser.g:2927:5: lv_type_0_0= ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeSTTimeLiteralTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_51);
            lv_type_0_0=ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTTimeLiteralRule());
              					}
              					set(
              						current,
              						"type",
              						lv_type_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STTimeLiteralType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:2944:3: ( (lv_value_1_0= ruleTime ) )
            // InternalSTCoreParser.g:2945:4: (lv_value_1_0= ruleTime )
            {
            // InternalSTCoreParser.g:2945:4: (lv_value_1_0= ruleTime )
            // InternalSTCoreParser.g:2946:5: lv_value_1_0= ruleTime
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getValueTimeParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_1_0=ruleTime();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTTimeLiteralRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.Time");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTTimeLiteral"


    // $ANTLR start "entryRuleSTTimeOfDayLiteralType"
    // InternalSTCoreParser.g:2967:1: entryRuleSTTimeOfDayLiteralType returns [EObject current=null] : iv_ruleSTTimeOfDayLiteralType= ruleSTTimeOfDayLiteralType EOF ;
    public final EObject entryRuleSTTimeOfDayLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteralType = null;


        try {
            // InternalSTCoreParser.g:2967:63: (iv_ruleSTTimeOfDayLiteralType= ruleSTTimeOfDayLiteralType EOF )
            // InternalSTCoreParser.g:2968:2: iv_ruleSTTimeOfDayLiteralType= ruleSTTimeOfDayLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTTimeOfDayLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTTimeOfDayLiteralType=ruleSTTimeOfDayLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTTimeOfDayLiteralType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTTimeOfDayLiteralType"


    // $ANTLR start "ruleSTTimeOfDayLiteralType"
    // InternalSTCoreParser.g:2974:1: ruleSTTimeOfDayLiteralType returns [EObject current=null] : (otherlv_0= TIME_OF_DAY | otherlv_1= TOD | otherlv_2= LTOD ) ;
    public final EObject ruleSTTimeOfDayLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:2980:2: ( (otherlv_0= TIME_OF_DAY | otherlv_1= TOD | otherlv_2= LTOD ) )
            // InternalSTCoreParser.g:2981:2: (otherlv_0= TIME_OF_DAY | otherlv_1= TOD | otherlv_2= LTOD )
            {
            // InternalSTCoreParser.g:2981:2: (otherlv_0= TIME_OF_DAY | otherlv_1= TOD | otherlv_2= LTOD )
            int alt51=3;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt51=1;
                }
                break;
            case TOD:
                {
                alt51=2;
                }
                break;
            case LTOD:
                {
                alt51=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }

            switch (alt51) {
                case 1 :
                    // InternalSTCoreParser.g:2982:3: otherlv_0= TIME_OF_DAY
                    {
                    otherlv_0=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTTimeOfDayLiteralTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2987:3: otherlv_1= TOD
                    {
                    otherlv_1=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralTypeAccess().getTODKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2992:3: otherlv_2= LTOD
                    {
                    otherlv_2=(Token)match(input,LTOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTTimeOfDayLiteralTypeAccess().getLTODKeyword_2());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTTimeOfDayLiteralType"


    // $ANTLR start "entryRuleSTTimeOfDayLiteral"
    // InternalSTCoreParser.g:3000:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalSTCoreParser.g:3000:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalSTCoreParser.g:3001:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTTimeOfDayLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTTimeOfDayLiteral=ruleSTTimeOfDayLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTTimeOfDayLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTTimeOfDayLiteral"


    // $ANTLR start "ruleSTTimeOfDayLiteral"
    // InternalSTCoreParser.g:3007:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3013:2: ( ( ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) ) ) )
            // InternalSTCoreParser.g:3014:2: ( ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) ) )
            {
            // InternalSTCoreParser.g:3014:2: ( ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) ) )
            // InternalSTCoreParser.g:3015:3: ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) )
            {
            // InternalSTCoreParser.g:3015:3: ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) )
            // InternalSTCoreParser.g:3016:4: (lv_type_0_0= ruleSTTimeOfDayLiteralType )
            {
            // InternalSTCoreParser.g:3016:4: (lv_type_0_0= ruleSTTimeOfDayLiteralType )
            // InternalSTCoreParser.g:3017:5: lv_type_0_0= ruleSTTimeOfDayLiteralType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeSTTimeOfDayLiteralTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_49);
            lv_type_0_0=ruleSTTimeOfDayLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              					set(
              						current,
              						"type",
              						lv_type_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STTimeOfDayLiteralType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:3034:3: ( (lv_value_1_0= ruleTimeOfDay ) )
            // InternalSTCoreParser.g:3035:4: (lv_value_1_0= ruleTimeOfDay )
            {
            // InternalSTCoreParser.g:3035:4: (lv_value_1_0= ruleTimeOfDay )
            // InternalSTCoreParser.g:3036:5: lv_value_1_0= ruleTimeOfDay
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getValueTimeOfDayParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_1_0=ruleTimeOfDay();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.TimeOfDay");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTTimeOfDayLiteral"


    // $ANTLR start "entryRuleSTDateAndTimeLiteralType"
    // InternalSTCoreParser.g:3057:1: entryRuleSTDateAndTimeLiteralType returns [EObject current=null] : iv_ruleSTDateAndTimeLiteralType= ruleSTDateAndTimeLiteralType EOF ;
    public final EObject entryRuleSTDateAndTimeLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteralType = null;


        try {
            // InternalSTCoreParser.g:3057:65: (iv_ruleSTDateAndTimeLiteralType= ruleSTDateAndTimeLiteralType EOF )
            // InternalSTCoreParser.g:3058:2: iv_ruleSTDateAndTimeLiteralType= ruleSTDateAndTimeLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTDateAndTimeLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTDateAndTimeLiteralType=ruleSTDateAndTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTDateAndTimeLiteralType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTDateAndTimeLiteralType"


    // $ANTLR start "ruleSTDateAndTimeLiteralType"
    // InternalSTCoreParser.g:3064:1: ruleSTDateAndTimeLiteralType returns [EObject current=null] : (otherlv_0= DATE_AND_TIME | otherlv_1= LDATE_AND_TIME | otherlv_2= DT | otherlv_3= LDT ) ;
    public final EObject ruleSTDateAndTimeLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3070:2: ( (otherlv_0= DATE_AND_TIME | otherlv_1= LDATE_AND_TIME | otherlv_2= DT | otherlv_3= LDT ) )
            // InternalSTCoreParser.g:3071:2: (otherlv_0= DATE_AND_TIME | otherlv_1= LDATE_AND_TIME | otherlv_2= DT | otherlv_3= LDT )
            {
            // InternalSTCoreParser.g:3071:2: (otherlv_0= DATE_AND_TIME | otherlv_1= LDATE_AND_TIME | otherlv_2= DT | otherlv_3= LDT )
            int alt52=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt52=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt52=2;
                }
                break;
            case DT:
                {
                alt52=3;
                }
                break;
            case LDT:
                {
                alt52=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }

            switch (alt52) {
                case 1 :
                    // InternalSTCoreParser.g:3072:3: otherlv_0= DATE_AND_TIME
                    {
                    otherlv_0=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTDateAndTimeLiteralTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3077:3: otherlv_1= LDATE_AND_TIME
                    {
                    otherlv_1=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3082:3: otherlv_2= DT
                    {
                    otherlv_2=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTDateAndTimeLiteralTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3087:3: otherlv_3= LDT
                    {
                    otherlv_3=(Token)match(input,LDT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_3, grammarAccess.getSTDateAndTimeLiteralTypeAccess().getLDTKeyword_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTDateAndTimeLiteralType"


    // $ANTLR start "entryRuleSTDateAndTimeLiteral"
    // InternalSTCoreParser.g:3095:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalSTCoreParser.g:3095:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalSTCoreParser.g:3096:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTDateAndTimeLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTDateAndTimeLiteral=ruleSTDateAndTimeLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTDateAndTimeLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTDateAndTimeLiteral"


    // $ANTLR start "ruleSTDateAndTimeLiteral"
    // InternalSTCoreParser.g:3102:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_dateValue_1_0 = null;

        AntlrDatatypeRuleToken lv_timeOfDayValue_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3108:2: ( ( ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) ) ) )
            // InternalSTCoreParser.g:3109:2: ( ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) ) )
            {
            // InternalSTCoreParser.g:3109:2: ( ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) ) )
            // InternalSTCoreParser.g:3110:3: ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) )
            {
            // InternalSTCoreParser.g:3110:3: ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) )
            // InternalSTCoreParser.g:3111:4: (lv_type_0_0= ruleSTDateAndTimeLiteralType )
            {
            // InternalSTCoreParser.g:3111:4: (lv_type_0_0= ruleSTDateAndTimeLiteralType )
            // InternalSTCoreParser.g:3112:5: lv_type_0_0= ruleSTDateAndTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeSTDateAndTimeLiteralTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_49);
            lv_type_0_0=ruleSTDateAndTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              					set(
              						current,
              						"type",
              						lv_type_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STDateAndTimeLiteralType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:3129:3: ( (lv_dateValue_1_0= ruleDate ) )
            // InternalSTCoreParser.g:3130:4: (lv_dateValue_1_0= ruleDate )
            {
            // InternalSTCoreParser.g:3130:4: (lv_dateValue_1_0= ruleDate )
            // InternalSTCoreParser.g:3131:5: lv_dateValue_1_0= ruleDate
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getDateValueDateParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_52);
            lv_dateValue_1_0=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              					set(
              						current,
              						"dateValue",
              						lv_dateValue_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.Date");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,HyphenMinus,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTDateAndTimeLiteralAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalSTCoreParser.g:3152:3: ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) )
            // InternalSTCoreParser.g:3153:4: (lv_timeOfDayValue_3_0= ruleTimeOfDay )
            {
            // InternalSTCoreParser.g:3153:4: (lv_timeOfDayValue_3_0= ruleTimeOfDay )
            // InternalSTCoreParser.g:3154:5: lv_timeOfDayValue_3_0= ruleTimeOfDay
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTimeOfDayValueTimeOfDayParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_timeOfDayValue_3_0=ruleTimeOfDay();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              					set(
              						current,
              						"timeOfDayValue",
              						lv_timeOfDayValue_3_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.TimeOfDay");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTDateAndTimeLiteral"


    // $ANTLR start "entryRuleSTStringLiteralType"
    // InternalSTCoreParser.g:3175:1: entryRuleSTStringLiteralType returns [EObject current=null] : iv_ruleSTStringLiteralType= ruleSTStringLiteralType EOF ;
    public final EObject entryRuleSTStringLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteralType = null;


        try {
            // InternalSTCoreParser.g:3175:60: (iv_ruleSTStringLiteralType= ruleSTStringLiteralType EOF )
            // InternalSTCoreParser.g:3176:2: iv_ruleSTStringLiteralType= ruleSTStringLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTStringLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTStringLiteralType=ruleSTStringLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTStringLiteralType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTStringLiteralType"


    // $ANTLR start "ruleSTStringLiteralType"
    // InternalSTCoreParser.g:3182:1: ruleSTStringLiteralType returns [EObject current=null] : (otherlv_0= STRING | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR ) ;
    public final EObject ruleSTStringLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3188:2: ( (otherlv_0= STRING | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR ) )
            // InternalSTCoreParser.g:3189:2: (otherlv_0= STRING | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )
            {
            // InternalSTCoreParser.g:3189:2: (otherlv_0= STRING | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )
            int alt53=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt53=1;
                }
                break;
            case WSTRING:
                {
                alt53=2;
                }
                break;
            case CHAR:
                {
                alt53=3;
                }
                break;
            case WCHAR:
                {
                alt53=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }

            switch (alt53) {
                case 1 :
                    // InternalSTCoreParser.g:3190:3: otherlv_0= STRING
                    {
                    otherlv_0=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTStringLiteralTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3195:3: otherlv_1= WSTRING
                    {
                    otherlv_1=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3200:3: otherlv_2= CHAR
                    {
                    otherlv_2=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTStringLiteralTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3205:3: otherlv_3= WCHAR
                    {
                    otherlv_3=(Token)match(input,WCHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_3, grammarAccess.getSTStringLiteralTypeAccess().getWCHARKeyword_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTStringLiteralType"


    // $ANTLR start "entryRuleSTStringLiteral"
    // InternalSTCoreParser.g:3213:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalSTCoreParser.g:3213:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalSTCoreParser.g:3214:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTStringLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTStringLiteral=ruleSTStringLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTStringLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTStringLiteral"


    // $ANTLR start "ruleSTStringLiteral"
    // InternalSTCoreParser.g:3220:1: ruleSTStringLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_1_1=null;
        Token lv_value_1_2=null;
        EObject lv_type_0_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3226:2: ( ( ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) ) ) )
            // InternalSTCoreParser.g:3227:2: ( ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) ) )
            {
            // InternalSTCoreParser.g:3227:2: ( ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) ) )
            // InternalSTCoreParser.g:3228:3: ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) )
            {
            // InternalSTCoreParser.g:3228:3: ( (lv_type_0_0= ruleSTStringLiteralType ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==WSTRING||LA54_0==STRING||LA54_0==WCHAR||LA54_0==CHAR) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalSTCoreParser.g:3229:4: (lv_type_0_0= ruleSTStringLiteralType )
                    {
                    // InternalSTCoreParser.g:3229:4: (lv_type_0_0= ruleSTStringLiteralType )
                    // InternalSTCoreParser.g:3230:5: lv_type_0_0= ruleSTStringLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeSTStringLiteralTypeParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_53);
                    lv_type_0_0=ruleSTStringLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTStringLiteralRule());
                      					}
                      					set(
                      						current,
                      						"type",
                      						lv_type_0_0,
                      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStringLiteralType");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTCoreParser.g:3247:3: ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) )
            // InternalSTCoreParser.g:3248:4: ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) )
            {
            // InternalSTCoreParser.g:3248:4: ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) )
            // InternalSTCoreParser.g:3249:5: (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING )
            {
            // InternalSTCoreParser.g:3249:5: (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_STRING) ) {
                alt55=1;
            }
            else if ( (LA55_0==RULE_WSTRING) ) {
                alt55=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // InternalSTCoreParser.g:3250:6: lv_value_1_1= RULE_STRING
                    {
                    lv_value_1_1=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_1_1, grammarAccess.getSTStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"value",
                      							lv_value_1_1,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STRING");
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3265:6: lv_value_1_2= RULE_WSTRING
                    {
                    lv_value_1_2=(Token)match(input,RULE_WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_1_2, grammarAccess.getSTStringLiteralAccess().getValueWSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"value",
                      							lv_value_1_2,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.WSTRING");
                      					
                    }

                    }
                    break;

            }


            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTStringLiteral"


    // $ANTLR start "entryRuleBoolLiteral"
    // InternalSTCoreParser.g:3286:1: entryRuleBoolLiteral returns [String current=null] : iv_ruleBoolLiteral= ruleBoolLiteral EOF ;
    public final String entryRuleBoolLiteral() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoolLiteral = null;


        try {
            // InternalSTCoreParser.g:3286:51: (iv_ruleBoolLiteral= ruleBoolLiteral EOF )
            // InternalSTCoreParser.g:3287:2: iv_ruleBoolLiteral= ruleBoolLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBoolLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBoolLiteral=ruleBoolLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBoolLiteral.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBoolLiteral"


    // $ANTLR start "ruleBoolLiteral"
    // InternalSTCoreParser.g:3293:1: ruleBoolLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE ) ;
    public final AntlrDatatypeRuleToken ruleBoolLiteral() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3299:2: ( (kw= TRUE | kw= FALSE ) )
            // InternalSTCoreParser.g:3300:2: (kw= TRUE | kw= FALSE )
            {
            // InternalSTCoreParser.g:3300:2: (kw= TRUE | kw= FALSE )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==TRUE) ) {
                alt56=1;
            }
            else if ( (LA56_0==FALSE) ) {
                alt56=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // InternalSTCoreParser.g:3301:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getBoolLiteralAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3307:3: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getBoolLiteralAccess().getFALSEKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBoolLiteral"


    // $ANTLR start "entryRuleNumber"
    // InternalSTCoreParser.g:3316:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;


        try {
            // InternalSTCoreParser.g:3316:46: (iv_ruleNumber= ruleNumber EOF )
            // InternalSTCoreParser.g:3317:2: iv_ruleNumber= ruleNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNumber=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNumber.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumber"


    // $ANTLR start "ruleNumber"
    // InternalSTCoreParser.g:3323:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_EXT_INT_4=null;
        Token this_INT_5=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3329:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) )
            // InternalSTCoreParser.g:3330:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            {
            // InternalSTCoreParser.g:3330:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            // InternalSTCoreParser.g:3331:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            {
            // InternalSTCoreParser.g:3331:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt57=3;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==PlusSign) ) {
                alt57=1;
            }
            else if ( (LA57_0==HyphenMinus) ) {
                alt57=2;
            }
            switch (alt57) {
                case 1 :
                    // InternalSTCoreParser.g:3332:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3338:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1());
              		
            }
            // InternalSTCoreParser.g:3351:3: ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==FullStop) ) {
                int LA59_1 = input.LA(2);

                if ( (LA59_1==RULE_INT) ) {
                    int LA59_3 = input.LA(3);

                    if ( (synpred4_InternalSTCoreParser()) ) {
                        alt59=1;
                    }
                }
                else if ( (LA59_1==RULE_EXT_INT) && (synpred4_InternalSTCoreParser())) {
                    alt59=1;
                }
            }
            switch (alt59) {
                case 1 :
                    // InternalSTCoreParser.g:3352:4: ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    {
                    // InternalSTCoreParser.g:3352:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTCoreParser.g:3353:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTCoreParser.g:3360:4: (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==RULE_EXT_INT) ) {
                        alt58=1;
                    }
                    else if ( (LA58_0==RULE_INT) ) {
                        alt58=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 58, 0, input);

                        throw nvae;
                    }
                    switch (alt58) {
                        case 1 :
                            // InternalSTCoreParser.g:3361:5: this_EXT_INT_4= RULE_EXT_INT
                            {
                            this_EXT_INT_4=(Token)match(input,RULE_EXT_INT,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_EXT_INT_4);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_EXT_INT_4, grammarAccess.getNumberAccess().getEXT_INTTerminalRuleCall_2_1_0());
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:3369:5: this_INT_5= RULE_INT
                            {
                            this_INT_5=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_INT_5);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_INT_5, grammarAccess.getNumberAccess().getINTTerminalRuleCall_2_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNumber"


    // $ANTLR start "entryRuleDate"
    // InternalSTCoreParser.g:3382:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalSTCoreParser.g:3382:44: (iv_ruleDate= ruleDate EOF )
            // InternalSTCoreParser.g:3383:2: iv_ruleDate= ruleDate EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDateRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDate=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDate.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDate"


    // $ANTLR start "ruleDate"
    // InternalSTCoreParser.g:3389:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3395:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTCoreParser.g:3396:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTCoreParser.g:3396:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTCoreParser.g:3397:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_52); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_52); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDateAccess().getINTTerminalRuleCall_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDate"


    // $ANTLR start "entryRuleTimeOfDay"
    // InternalSTCoreParser.g:3432:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalSTCoreParser.g:3432:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalSTCoreParser.g:3433:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTimeOfDayRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTimeOfDay=ruleTimeOfDay();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTimeOfDay.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTimeOfDay"


    // $ANTLR start "ruleTimeOfDay"
    // InternalSTCoreParser.g:3439:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3445:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) )
            // InternalSTCoreParser.g:3446:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            {
            // InternalSTCoreParser.g:3446:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            // InternalSTCoreParser.g:3447:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTCoreParser.g:3478:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==FullStop) ) {
                int LA60_1 = input.LA(2);

                if ( (LA60_1==RULE_INT) ) {
                    int LA60_3 = input.LA(3);

                    if ( (synpred5_InternalSTCoreParser()) ) {
                        alt60=1;
                    }
                }
            }
            switch (alt60) {
                case 1 :
                    // InternalSTCoreParser.g:3479:4: ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT
                    {
                    // InternalSTCoreParser.g:3479:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTCoreParser.g:3480:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getFullStopKeyword_5_0());
                      				
                    }

                    }

                    this_INT_6=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_6);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_6, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_5_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTimeOfDay"


    // $ANTLR start "entryRuleTime"
    // InternalSTCoreParser.g:3499:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;


        try {
            // InternalSTCoreParser.g:3499:44: (iv_ruleTime= ruleTime EOF )
            // InternalSTCoreParser.g:3500:2: iv_ruleTime= ruleTime EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTimeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTime=ruleTime();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTime.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTime"


    // $ANTLR start "ruleTime"
    // InternalSTCoreParser.g:3506:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Number_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3512:2: ( (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ )
            // InternalSTCoreParser.g:3513:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            {
            // InternalSTCoreParser.g:3513:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            int cnt63=0;
            loop63:
            do {
                int alt63=2;
                switch ( input.LA(1) ) {
                case PlusSign:
                    {
                    int LA63_2 = input.LA(2);

                    if ( (LA63_2==RULE_INT) ) {
                        int LA63_5 = input.LA(3);

                        if ( (LA63_5==FullStop) ) {
                            int LA63_6 = input.LA(4);

                            if ( (LA63_6==RULE_EXT_INT) ) {
                                int LA63_7 = input.LA(5);

                                if ( ((LA63_7>=MS && LA63_7<=NS)||LA63_7==US||(LA63_7>=D && LA63_7<=S)) ) {
                                    alt63=1;
                                }


                            }
                            else if ( (LA63_6==RULE_INT) ) {
                                int LA63_8 = input.LA(5);

                                if ( ((LA63_8>=MS && LA63_8<=NS)||LA63_8==US||(LA63_8>=D && LA63_8<=S)) ) {
                                    alt63=1;
                                }


                            }


                        }
                        else if ( ((LA63_5>=MS && LA63_5<=NS)||LA63_5==US||(LA63_5>=D && LA63_5<=S)) ) {
                            alt63=1;
                        }


                    }


                    }
                    break;
                case HyphenMinus:
                    {
                    int LA63_3 = input.LA(2);

                    if ( (LA63_3==RULE_INT) ) {
                        int LA63_5 = input.LA(3);

                        if ( (LA63_5==FullStop) ) {
                            int LA63_6 = input.LA(4);

                            if ( (LA63_6==RULE_EXT_INT) ) {
                                int LA63_7 = input.LA(5);

                                if ( ((LA63_7>=MS && LA63_7<=NS)||LA63_7==US||(LA63_7>=D && LA63_7<=S)) ) {
                                    alt63=1;
                                }


                            }
                            else if ( (LA63_6==RULE_INT) ) {
                                int LA63_8 = input.LA(5);

                                if ( ((LA63_8>=MS && LA63_8<=NS)||LA63_8==US||(LA63_8>=D && LA63_8<=S)) ) {
                                    alt63=1;
                                }


                            }


                        }
                        else if ( ((LA63_5>=MS && LA63_5<=NS)||LA63_5==US||(LA63_5>=D && LA63_5<=S)) ) {
                            alt63=1;
                        }


                    }


                    }
                    break;
                case RULE_INT:
                    {
                    alt63=1;
                    }
                    break;

                }

                switch (alt63) {
            	case 1 :
            	    // InternalSTCoreParser.g:3514:3: this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )?
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getTimeAccess().getNumberParserRuleCall_0());
            	      		
            	    }
            	    pushFollow(FOLLOW_56);
            	    this_Number_0=ruleNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_Number_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }
            	    // InternalSTCoreParser.g:3524:3: (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS )
            	    int alt61=7;
            	    switch ( input.LA(1) ) {
            	    case D:
            	        {
            	        alt61=1;
            	        }
            	        break;
            	    case H:
            	        {
            	        alt61=2;
            	        }
            	        break;
            	    case M:
            	        {
            	        alt61=3;
            	        }
            	        break;
            	    case S:
            	        {
            	        alt61=4;
            	        }
            	        break;
            	    case MS:
            	        {
            	        alt61=5;
            	        }
            	        break;
            	    case US:
            	        {
            	        alt61=6;
            	        }
            	        break;
            	    case NS:
            	        {
            	        alt61=7;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 61, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt61) {
            	        case 1 :
            	            // InternalSTCoreParser.g:3525:4: kw= D
            	            {
            	            kw=(Token)match(input,D,FOLLOW_57); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getDKeyword_1_0());
            	              			
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:3531:4: kw= H
            	            {
            	            kw=(Token)match(input,H,FOLLOW_57); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getHKeyword_1_1());
            	              			
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTCoreParser.g:3537:4: kw= M
            	            {
            	            kw=(Token)match(input,M,FOLLOW_57); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMKeyword_1_2());
            	              			
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTCoreParser.g:3543:4: kw= S
            	            {
            	            kw=(Token)match(input,S,FOLLOW_57); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getSKeyword_1_3());
            	              			
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalSTCoreParser.g:3549:4: kw= MS
            	            {
            	            kw=(Token)match(input,MS,FOLLOW_57); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMSKeyword_1_4());
            	              			
            	            }

            	            }
            	            break;
            	        case 6 :
            	            // InternalSTCoreParser.g:3555:4: kw= US
            	            {
            	            kw=(Token)match(input,US,FOLLOW_57); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getUSKeyword_1_5());
            	              			
            	            }

            	            }
            	            break;
            	        case 7 :
            	            // InternalSTCoreParser.g:3561:4: kw= NS
            	            {
            	            kw=(Token)match(input,NS,FOLLOW_57); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getNSKeyword_1_6());
            	              			
            	            }

            	            }
            	            break;

            	    }

            	    // InternalSTCoreParser.g:3567:3: (kw= KW__ )?
            	    int alt62=2;
            	    int LA62_0 = input.LA(1);

            	    if ( (LA62_0==KW__) ) {
            	        alt62=1;
            	    }
            	    switch (alt62) {
            	        case 1 :
            	            // InternalSTCoreParser.g:3568:4: kw= KW__
            	            {
            	            kw=(Token)match(input,KW__,FOLLOW_58); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().get_Keyword_2());
            	              			
            	            }

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt63 >= 1 ) break loop63;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(63, input);
                        throw eee;
                }
                cnt63++;
            } while (true);


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTime"


    // $ANTLR start "ruleSubrangeOperator"
    // InternalSTCoreParser.g:3578:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3584:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTCoreParser.g:3585:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTCoreParser.g:3585:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTCoreParser.g:3586:3: enumLiteral_0= FullStopFullStop
            {
            enumLiteral_0=(Token)match(input,FullStopFullStop,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = grammarAccess.getSubrangeOperatorAccess().getRangeEnumLiteralDeclaration().getEnumLiteral().getInstance();
              			newLeafNode(enumLiteral_0, grammarAccess.getSubrangeOperatorAccess().getRangeEnumLiteralDeclaration());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSubrangeOperator"


    // $ANTLR start "ruleOrOperator"
    // InternalSTCoreParser.g:3595:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3601:2: ( (enumLiteral_0= OR ) )
            // InternalSTCoreParser.g:3602:2: (enumLiteral_0= OR )
            {
            // InternalSTCoreParser.g:3602:2: (enumLiteral_0= OR )
            // InternalSTCoreParser.g:3603:3: enumLiteral_0= OR
            {
            enumLiteral_0=(Token)match(input,OR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = grammarAccess.getOrOperatorAccess().getOREnumLiteralDeclaration().getEnumLiteral().getInstance();
              			newLeafNode(enumLiteral_0, grammarAccess.getOrOperatorAccess().getOREnumLiteralDeclaration());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrOperator"


    // $ANTLR start "ruleXorOperator"
    // InternalSTCoreParser.g:3612:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3618:2: ( (enumLiteral_0= XOR ) )
            // InternalSTCoreParser.g:3619:2: (enumLiteral_0= XOR )
            {
            // InternalSTCoreParser.g:3619:2: (enumLiteral_0= XOR )
            // InternalSTCoreParser.g:3620:3: enumLiteral_0= XOR
            {
            enumLiteral_0=(Token)match(input,XOR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = grammarAccess.getXorOperatorAccess().getXOREnumLiteralDeclaration().getEnumLiteral().getInstance();
              			newLeafNode(enumLiteral_0, grammarAccess.getXorOperatorAccess().getXOREnumLiteralDeclaration());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXorOperator"


    // $ANTLR start "ruleAndOperator"
    // InternalSTCoreParser.g:3629:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3635:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTCoreParser.g:3636:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTCoreParser.g:3636:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==AND) ) {
                alt64=1;
            }
            else if ( (LA64_0==Ampersand) ) {
                alt64=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // InternalSTCoreParser.g:3637:3: (enumLiteral_0= AND )
                    {
                    // InternalSTCoreParser.g:3637:3: (enumLiteral_0= AND )
                    // InternalSTCoreParser.g:3638:4: enumLiteral_0= AND
                    {
                    enumLiteral_0=(Token)match(input,AND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getAndOperatorAccess().getANDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getAndOperatorAccess().getANDEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3645:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTCoreParser.g:3645:3: (enumLiteral_1= Ampersand )
                    // InternalSTCoreParser.g:3646:4: enumLiteral_1= Ampersand
                    {
                    enumLiteral_1=(Token)match(input,Ampersand,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getAndOperatorAccess().getAMPERSANDEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getAndOperatorAccess().getAMPERSANDEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAndOperator"


    // $ANTLR start "ruleEqualityOperator"
    // InternalSTCoreParser.g:3656:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3662:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTCoreParser.g:3663:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTCoreParser.g:3663:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==EqualsSign) ) {
                alt65=1;
            }
            else if ( (LA65_0==LessThanSignGreaterThanSign) ) {
                alt65=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }
            switch (alt65) {
                case 1 :
                    // InternalSTCoreParser.g:3664:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTCoreParser.g:3664:3: (enumLiteral_0= EqualsSign )
                    // InternalSTCoreParser.g:3665:4: enumLiteral_0= EqualsSign
                    {
                    enumLiteral_0=(Token)match(input,EqualsSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getEqualityOperatorAccess().getEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getEqualityOperatorAccess().getEQEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3672:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTCoreParser.g:3672:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTCoreParser.g:3673:4: enumLiteral_1= LessThanSignGreaterThanSign
                    {
                    enumLiteral_1=(Token)match(input,LessThanSignGreaterThanSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getEqualityOperatorAccess().getNEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getEqualityOperatorAccess().getNEEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEqualityOperator"


    // $ANTLR start "ruleCompareOperator"
    // InternalSTCoreParser.g:3683:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3689:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTCoreParser.g:3690:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTCoreParser.g:3690:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt66=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt66=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt66=2;
                }
                break;
            case GreaterThanSign:
                {
                alt66=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt66=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    // InternalSTCoreParser.g:3691:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTCoreParser.g:3691:3: (enumLiteral_0= LessThanSign )
                    // InternalSTCoreParser.g:3692:4: enumLiteral_0= LessThanSign
                    {
                    enumLiteral_0=(Token)match(input,LessThanSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getCompareOperatorAccess().getLTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getCompareOperatorAccess().getLTEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3699:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTCoreParser.g:3699:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTCoreParser.g:3700:4: enumLiteral_1= LessThanSignEqualsSign
                    {
                    enumLiteral_1=(Token)match(input,LessThanSignEqualsSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getCompareOperatorAccess().getLEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getCompareOperatorAccess().getLEEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3707:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTCoreParser.g:3707:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTCoreParser.g:3708:4: enumLiteral_2= GreaterThanSign
                    {
                    enumLiteral_2=(Token)match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getCompareOperatorAccess().getGTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getCompareOperatorAccess().getGTEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3715:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTCoreParser.g:3715:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTCoreParser.g:3716:4: enumLiteral_3= GreaterThanSignEqualsSign
                    {
                    enumLiteral_3=(Token)match(input,GreaterThanSignEqualsSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getCompareOperatorAccess().getGEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_3, grammarAccess.getCompareOperatorAccess().getGEEnumLiteralDeclaration_3());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCompareOperator"


    // $ANTLR start "ruleAddSubOperator"
    // InternalSTCoreParser.g:3726:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3732:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTCoreParser.g:3733:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTCoreParser.g:3733:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==PlusSign) ) {
                alt67=1;
            }
            else if ( (LA67_0==HyphenMinus) ) {
                alt67=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // InternalSTCoreParser.g:3734:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTCoreParser.g:3734:3: (enumLiteral_0= PlusSign )
                    // InternalSTCoreParser.g:3735:4: enumLiteral_0= PlusSign
                    {
                    enumLiteral_0=(Token)match(input,PlusSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getAddSubOperatorAccess().getADDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getAddSubOperatorAccess().getADDEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3742:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTCoreParser.g:3742:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTCoreParser.g:3743:4: enumLiteral_1= HyphenMinus
                    {
                    enumLiteral_1=(Token)match(input,HyphenMinus,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getAddSubOperatorAccess().getSUBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getAddSubOperatorAccess().getSUBEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAddSubOperator"


    // $ANTLR start "ruleMulDivModOperator"
    // InternalSTCoreParser.g:3753:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3759:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTCoreParser.g:3760:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTCoreParser.g:3760:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt68=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt68=1;
                }
                break;
            case Solidus:
                {
                alt68=2;
                }
                break;
            case MOD:
                {
                alt68=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }

            switch (alt68) {
                case 1 :
                    // InternalSTCoreParser.g:3761:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTCoreParser.g:3761:3: (enumLiteral_0= Asterisk )
                    // InternalSTCoreParser.g:3762:4: enumLiteral_0= Asterisk
                    {
                    enumLiteral_0=(Token)match(input,Asterisk,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMulDivModOperatorAccess().getMULEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getMulDivModOperatorAccess().getMULEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3769:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTCoreParser.g:3769:3: (enumLiteral_1= Solidus )
                    // InternalSTCoreParser.g:3770:4: enumLiteral_1= Solidus
                    {
                    enumLiteral_1=(Token)match(input,Solidus,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMulDivModOperatorAccess().getDIVEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getMulDivModOperatorAccess().getDIVEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3777:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTCoreParser.g:3777:3: (enumLiteral_2= MOD )
                    // InternalSTCoreParser.g:3778:4: enumLiteral_2= MOD
                    {
                    enumLiteral_2=(Token)match(input,MOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMulDivModOperatorAccess().getMODEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getMulDivModOperatorAccess().getMODEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMulDivModOperator"


    // $ANTLR start "rulePowerOperator"
    // InternalSTCoreParser.g:3788:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3794:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTCoreParser.g:3795:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTCoreParser.g:3795:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTCoreParser.g:3796:3: enumLiteral_0= AsteriskAsterisk
            {
            enumLiteral_0=(Token)match(input,AsteriskAsterisk,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = grammarAccess.getPowerOperatorAccess().getPOWEREnumLiteralDeclaration().getEnumLiteral().getInstance();
              			newLeafNode(enumLiteral_0, grammarAccess.getPowerOperatorAccess().getPOWEREnumLiteralDeclaration());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePowerOperator"


    // $ANTLR start "ruleUnaryOperator"
    // InternalSTCoreParser.g:3805:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3811:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTCoreParser.g:3812:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTCoreParser.g:3812:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt69=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt69=1;
                }
                break;
            case PlusSign:
                {
                alt69=2;
                }
                break;
            case NOT:
                {
                alt69=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // InternalSTCoreParser.g:3813:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTCoreParser.g:3813:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTCoreParser.g:3814:4: enumLiteral_0= HyphenMinus
                    {
                    enumLiteral_0=(Token)match(input,HyphenMinus,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnaryOperatorAccess().getMINUSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnaryOperatorAccess().getMINUSEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3821:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTCoreParser.g:3821:3: (enumLiteral_1= PlusSign )
                    // InternalSTCoreParser.g:3822:4: enumLiteral_1= PlusSign
                    {
                    enumLiteral_1=(Token)match(input,PlusSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnaryOperatorAccess().getPLUSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnaryOperatorAccess().getPLUSEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3829:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTCoreParser.g:3829:3: (enumLiteral_2= NOT )
                    // InternalSTCoreParser.g:3830:4: enumLiteral_2= NOT
                    {
                    enumLiteral_2=(Token)match(input,NOT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnaryOperatorAccess().getNOTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getUnaryOperatorAccess().getNOTEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnaryOperator"


    // $ANTLR start "ruleSTMultiBitAccessSpecifier"
    // InternalSTCoreParser.g:3840:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3846:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalSTCoreParser.g:3847:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalSTCoreParser.g:3847:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt70=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt70=1;
                }
                break;
            case D_1:
                {
                alt70=2;
                }
                break;
            case W:
                {
                alt70=3;
                }
                break;
            case B:
                {
                alt70=4;
                }
                break;
            case X:
                {
                alt70=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // InternalSTCoreParser.g:3848:3: (enumLiteral_0= L )
                    {
                    // InternalSTCoreParser.g:3848:3: (enumLiteral_0= L )
                    // InternalSTCoreParser.g:3849:4: enumLiteral_0= L
                    {
                    enumLiteral_0=(Token)match(input,L,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getLEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getSTMultiBitAccessSpecifierAccess().getLEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3856:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTCoreParser.g:3856:3: (enumLiteral_1= D_1 )
                    // InternalSTCoreParser.g:3857:4: enumLiteral_1= D_1
                    {
                    enumLiteral_1=(Token)match(input,D_1,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getDEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getSTMultiBitAccessSpecifierAccess().getDEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3864:3: (enumLiteral_2= W )
                    {
                    // InternalSTCoreParser.g:3864:3: (enumLiteral_2= W )
                    // InternalSTCoreParser.g:3865:4: enumLiteral_2= W
                    {
                    enumLiteral_2=(Token)match(input,W,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getWEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getSTMultiBitAccessSpecifierAccess().getWEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3872:3: (enumLiteral_3= B )
                    {
                    // InternalSTCoreParser.g:3872:3: (enumLiteral_3= B )
                    // InternalSTCoreParser.g:3873:4: enumLiteral_3= B
                    {
                    enumLiteral_3=(Token)match(input,B,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getBEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_3, grammarAccess.getSTMultiBitAccessSpecifierAccess().getBEnumLiteralDeclaration_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:3880:3: (enumLiteral_4= X )
                    {
                    // InternalSTCoreParser.g:3880:3: (enumLiteral_4= X )
                    // InternalSTCoreParser.g:3881:4: enumLiteral_4= X
                    {
                    enumLiteral_4=(Token)match(input,X,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getXEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_4, grammarAccess.getSTMultiBitAccessSpecifierAccess().getXEnumLiteralDeclaration_4());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTMultiBitAccessSpecifier"

    // $ANTLR start synpred1_InternalSTCoreParser
    public final void synpred1_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:1048:4: ( ( ruleSTStatement ) )
        // InternalSTCoreParser.g:1048:5: ( ruleSTStatement )
        {
        // InternalSTCoreParser.g:1048:5: ( ruleSTStatement )
        // InternalSTCoreParser.g:1049:5: ruleSTStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalSTCoreParser

    // $ANTLR start synpred2_InternalSTCoreParser
    public final void synpred2_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:2103:4: ( ruleSTAccessExpression )
        // InternalSTCoreParser.g:2103:5: ruleSTAccessExpression
        {
        pushFollow(FOLLOW_2);
        ruleSTAccessExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalSTCoreParser

    // $ANTLR start synpred3_InternalSTCoreParser
    public final void synpred3_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:2398:5: ( LeftParenthesis )
        // InternalSTCoreParser.g:2398:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalSTCoreParser

    // $ANTLR start synpred4_InternalSTCoreParser
    public final void synpred4_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:3353:5: ( FullStop )
        // InternalSTCoreParser.g:3353:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_InternalSTCoreParser

    // $ANTLR start synpred5_InternalSTCoreParser
    public final void synpred5_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:3480:5: ( FullStop )
        // InternalSTCoreParser.g:3480:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_InternalSTCoreParser

    // Delegated rules

    public final boolean synpred4_InternalSTCoreParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalSTCoreParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalSTCoreParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalSTCoreParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalSTCoreParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalSTCoreParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalSTCoreParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalSTCoreParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalSTCoreParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalSTCoreParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA21 dfa21 = new DFA21(this);
    protected DFA36 dfa36 = new DFA36(this);
    protected DFA43 dfa43 = new DFA43(this);
    static final String dfa_1s = "\72\uffff";
    static final String dfa_2s = "\1\1\71\uffff";
    static final String dfa_3s = "\1\4\2\uffff\54\0\13\uffff";
    static final String dfa_4s = "\1\163\2\uffff\54\0\13\uffff";
    static final String dfa_5s = "\1\uffff\1\2\57\uffff\11\1";
    static final String dfa_6s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\13\uffff}>";
    static final String[] dfa_7s = {
            "\1\46\1\45\1\42\5\uffff\1\67\1\1\1\uffff\1\52\2\uffff\1\51\1\10\1\uffff\1\33\1\23\1\37\1\11\1\65\1\66\1\20\1\21\1\16\1\54\1\uffff\1\5\1\6\1\53\1\32\1\14\1\uffff\1\25\1\15\1\44\1\22\1\12\1\36\1\17\1\uffff\1\64\1\7\1\62\1\1\1\70\1\13\1\50\1\uffff\1\43\1\24\1\uffff\1\47\1\63\1\35\1\41\1\uffff\1\1\17\uffff\1\34\1\uffff\1\61\4\uffff\1\40\3\uffff\1\3\2\uffff\1\26\1\uffff\1\27\3\uffff\1\71\13\uffff\1\31\1\30\1\uffff\1\4\1\55\1\56",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 1047:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_0 = input.LA(1);

                         
                        int index21_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA21_0==EOF||LA21_0==END_CASE||LA21_0==ELSE||LA21_0==NOT) ) {s = 1;}

                        else if ( (LA21_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA21_0==RULE_ID) ) {s = 4;}

                        else if ( (LA21_0==BOOL) ) {s = 5;}

                        else if ( (LA21_0==BYTE) ) {s = 6;}

                        else if ( (LA21_0==WORD) ) {s = 7;}

                        else if ( (LA21_0==DWORD) ) {s = 8;}

                        else if ( (LA21_0==LWORD) ) {s = 9;}

                        else if ( (LA21_0==SINT) ) {s = 10;}

                        else if ( (LA21_0==INT) ) {s = 11;}

                        else if ( (LA21_0==DINT) ) {s = 12;}

                        else if ( (LA21_0==LINT) ) {s = 13;}

                        else if ( (LA21_0==USINT) ) {s = 14;}

                        else if ( (LA21_0==UINT) ) {s = 15;}

                        else if ( (LA21_0==UDINT) ) {s = 16;}

                        else if ( (LA21_0==ULINT) ) {s = 17;}

                        else if ( (LA21_0==REAL) ) {s = 18;}

                        else if ( (LA21_0==LREAL) ) {s = 19;}

                        else if ( (LA21_0==TRUE) ) {s = 20;}

                        else if ( (LA21_0==FALSE) ) {s = 21;}

                        else if ( (LA21_0==PlusSign) ) {s = 22;}

                        else if ( (LA21_0==HyphenMinus) ) {s = 23;}

                        else if ( (LA21_0==RULE_INT) ) {s = 24;}

                        else if ( (LA21_0==RULE_NON_DECIMAL) ) {s = 25;}

                        else if ( (LA21_0==DATE) ) {s = 26;}

                        else if ( (LA21_0==LDATE) ) {s = 27;}

                        else if ( (LA21_0==D_2) ) {s = 28;}

                        else if ( (LA21_0==LD) ) {s = 29;}

                        else if ( (LA21_0==TIME) ) {s = 30;}

                        else if ( (LA21_0==LTIME) ) {s = 31;}

                        else if ( (LA21_0==T) ) {s = 32;}

                        else if ( (LA21_0==LT) ) {s = 33;}

                        else if ( (LA21_0==TIME_OF_DAY) ) {s = 34;}

                        else if ( (LA21_0==TOD) ) {s = 35;}

                        else if ( (LA21_0==LTOD) ) {s = 36;}

                        else if ( (LA21_0==DATE_AND_TIME) ) {s = 37;}

                        else if ( (LA21_0==LDATE_AND_TIME) ) {s = 38;}

                        else if ( (LA21_0==DT) ) {s = 39;}

                        else if ( (LA21_0==LDT) ) {s = 40;}

                        else if ( (LA21_0==STRING) ) {s = 41;}

                        else if ( (LA21_0==WSTRING) ) {s = 42;}

                        else if ( (LA21_0==CHAR) ) {s = 43;}

                        else if ( (LA21_0==WCHAR) ) {s = 44;}

                        else if ( (LA21_0==RULE_STRING) ) {s = 45;}

                        else if ( (LA21_0==RULE_WSTRING) ) {s = 46;}

                        else if ( (LA21_0==IF) && (synpred1_InternalSTCoreParser())) {s = 49;}

                        else if ( (LA21_0==CASE) && (synpred1_InternalSTCoreParser())) {s = 50;}

                        else if ( (LA21_0==FOR) && (synpred1_InternalSTCoreParser())) {s = 51;}

                        else if ( (LA21_0==WHILE) && (synpred1_InternalSTCoreParser())) {s = 52;}

                        else if ( (LA21_0==REPEAT) && (synpred1_InternalSTCoreParser())) {s = 53;}

                        else if ( (LA21_0==RETURN) && (synpred1_InternalSTCoreParser())) {s = 54;}

                        else if ( (LA21_0==CONTINUE) && (synpred1_InternalSTCoreParser())) {s = 55;}

                        else if ( (LA21_0==EXIT) && (synpred1_InternalSTCoreParser())) {s = 56;}

                        else if ( (LA21_0==Semicolon) && (synpred1_InternalSTCoreParser())) {s = 57;}

                         
                        input.seek(index21_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA21_3 = input.LA(1);

                         
                        int index21_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA21_4 = input.LA(1);

                         
                        int index21_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA21_5 = input.LA(1);

                         
                        int index21_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA21_6 = input.LA(1);

                         
                        int index21_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA21_7 = input.LA(1);

                         
                        int index21_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA21_8 = input.LA(1);

                         
                        int index21_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA21_9 = input.LA(1);

                         
                        int index21_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA21_10 = input.LA(1);

                         
                        int index21_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA21_11 = input.LA(1);

                         
                        int index21_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA21_12 = input.LA(1);

                         
                        int index21_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA21_13 = input.LA(1);

                         
                        int index21_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA21_14 = input.LA(1);

                         
                        int index21_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA21_15 = input.LA(1);

                         
                        int index21_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA21_16 = input.LA(1);

                         
                        int index21_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA21_17 = input.LA(1);

                         
                        int index21_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA21_18 = input.LA(1);

                         
                        int index21_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA21_19 = input.LA(1);

                         
                        int index21_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA21_20 = input.LA(1);

                         
                        int index21_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA21_21 = input.LA(1);

                         
                        int index21_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA21_22 = input.LA(1);

                         
                        int index21_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA21_23 = input.LA(1);

                         
                        int index21_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA21_24 = input.LA(1);

                         
                        int index21_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA21_25 = input.LA(1);

                         
                        int index21_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA21_26 = input.LA(1);

                         
                        int index21_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA21_27 = input.LA(1);

                         
                        int index21_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA21_28 = input.LA(1);

                         
                        int index21_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA21_29 = input.LA(1);

                         
                        int index21_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA21_30 = input.LA(1);

                         
                        int index21_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA21_31 = input.LA(1);

                         
                        int index21_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA21_32 = input.LA(1);

                         
                        int index21_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA21_33 = input.LA(1);

                         
                        int index21_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA21_34 = input.LA(1);

                         
                        int index21_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA21_35 = input.LA(1);

                         
                        int index21_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA21_36 = input.LA(1);

                         
                        int index21_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA21_37 = input.LA(1);

                         
                        int index21_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA21_38 = input.LA(1);

                         
                        int index21_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA21_39 = input.LA(1);

                         
                        int index21_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA21_40 = input.LA(1);

                         
                        int index21_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA21_41 = input.LA(1);

                         
                        int index21_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA21_42 = input.LA(1);

                         
                        int index21_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA21_43 = input.LA(1);

                         
                        int index21_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA21_44 = input.LA(1);

                         
                        int index21_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA21_45 = input.LA(1);

                         
                        int index21_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA21_46 = input.LA(1);

                         
                        int index21_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index21_46);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 21, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\56\uffff";
    static final String dfa_9s = "\1\4\23\uffff\2\0\30\uffff";
    static final String dfa_10s = "\1\163\23\uffff\2\0\30\uffff";
    static final String dfa_11s = "\1\uffff\23\1\2\uffff\27\1\1\2";
    static final String dfa_12s = "\1\0\23\uffff\1\2\1\1\30\uffff}>";
    static final String[] dfa_13s = {
            "\1\44\1\43\1\40\10\uffff\1\50\2\uffff\1\47\1\6\1\uffff\1\31\1\21\1\35\1\7\2\uffff\1\16\1\17\1\14\1\52\1\uffff\1\3\1\4\1\51\1\30\1\12\1\uffff\1\23\1\13\1\42\1\20\1\10\1\34\1\15\2\uffff\1\5\3\uffff\1\11\1\46\1\uffff\1\41\1\22\1\uffff\1\45\1\uffff\1\33\1\37\1\uffff\1\55\17\uffff\1\32\6\uffff\1\36\3\uffff\1\1\2\uffff\1\24\1\uffff\1\25\17\uffff\1\27\1\26\1\uffff\1\2\1\53\1\54",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "2101:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA36_0 = input.LA(1);

                         
                        int index36_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA36_0==LeftParenthesis) && (synpred2_InternalSTCoreParser())) {s = 1;}

                        else if ( (LA36_0==RULE_ID) && (synpred2_InternalSTCoreParser())) {s = 2;}

                        else if ( (LA36_0==BOOL) && (synpred2_InternalSTCoreParser())) {s = 3;}

                        else if ( (LA36_0==BYTE) && (synpred2_InternalSTCoreParser())) {s = 4;}

                        else if ( (LA36_0==WORD) && (synpred2_InternalSTCoreParser())) {s = 5;}

                        else if ( (LA36_0==DWORD) && (synpred2_InternalSTCoreParser())) {s = 6;}

                        else if ( (LA36_0==LWORD) && (synpred2_InternalSTCoreParser())) {s = 7;}

                        else if ( (LA36_0==SINT) && (synpred2_InternalSTCoreParser())) {s = 8;}

                        else if ( (LA36_0==INT) && (synpred2_InternalSTCoreParser())) {s = 9;}

                        else if ( (LA36_0==DINT) && (synpred2_InternalSTCoreParser())) {s = 10;}

                        else if ( (LA36_0==LINT) && (synpred2_InternalSTCoreParser())) {s = 11;}

                        else if ( (LA36_0==USINT) && (synpred2_InternalSTCoreParser())) {s = 12;}

                        else if ( (LA36_0==UINT) && (synpred2_InternalSTCoreParser())) {s = 13;}

                        else if ( (LA36_0==UDINT) && (synpred2_InternalSTCoreParser())) {s = 14;}

                        else if ( (LA36_0==ULINT) && (synpred2_InternalSTCoreParser())) {s = 15;}

                        else if ( (LA36_0==REAL) && (synpred2_InternalSTCoreParser())) {s = 16;}

                        else if ( (LA36_0==LREAL) && (synpred2_InternalSTCoreParser())) {s = 17;}

                        else if ( (LA36_0==TRUE) && (synpred2_InternalSTCoreParser())) {s = 18;}

                        else if ( (LA36_0==FALSE) && (synpred2_InternalSTCoreParser())) {s = 19;}

                        else if ( (LA36_0==PlusSign) ) {s = 20;}

                        else if ( (LA36_0==HyphenMinus) ) {s = 21;}

                        else if ( (LA36_0==RULE_INT) && (synpred2_InternalSTCoreParser())) {s = 22;}

                        else if ( (LA36_0==RULE_NON_DECIMAL) && (synpred2_InternalSTCoreParser())) {s = 23;}

                        else if ( (LA36_0==DATE) && (synpred2_InternalSTCoreParser())) {s = 24;}

                        else if ( (LA36_0==LDATE) && (synpred2_InternalSTCoreParser())) {s = 25;}

                        else if ( (LA36_0==D_2) && (synpred2_InternalSTCoreParser())) {s = 26;}

                        else if ( (LA36_0==LD) && (synpred2_InternalSTCoreParser())) {s = 27;}

                        else if ( (LA36_0==TIME) && (synpred2_InternalSTCoreParser())) {s = 28;}

                        else if ( (LA36_0==LTIME) && (synpred2_InternalSTCoreParser())) {s = 29;}

                        else if ( (LA36_0==T) && (synpred2_InternalSTCoreParser())) {s = 30;}

                        else if ( (LA36_0==LT) && (synpred2_InternalSTCoreParser())) {s = 31;}

                        else if ( (LA36_0==TIME_OF_DAY) && (synpred2_InternalSTCoreParser())) {s = 32;}

                        else if ( (LA36_0==TOD) && (synpred2_InternalSTCoreParser())) {s = 33;}

                        else if ( (LA36_0==LTOD) && (synpred2_InternalSTCoreParser())) {s = 34;}

                        else if ( (LA36_0==DATE_AND_TIME) && (synpred2_InternalSTCoreParser())) {s = 35;}

                        else if ( (LA36_0==LDATE_AND_TIME) && (synpred2_InternalSTCoreParser())) {s = 36;}

                        else if ( (LA36_0==DT) && (synpred2_InternalSTCoreParser())) {s = 37;}

                        else if ( (LA36_0==LDT) && (synpred2_InternalSTCoreParser())) {s = 38;}

                        else if ( (LA36_0==STRING) && (synpred2_InternalSTCoreParser())) {s = 39;}

                        else if ( (LA36_0==WSTRING) && (synpred2_InternalSTCoreParser())) {s = 40;}

                        else if ( (LA36_0==CHAR) && (synpred2_InternalSTCoreParser())) {s = 41;}

                        else if ( (LA36_0==WCHAR) && (synpred2_InternalSTCoreParser())) {s = 42;}

                        else if ( (LA36_0==RULE_STRING) && (synpred2_InternalSTCoreParser())) {s = 43;}

                        else if ( (LA36_0==RULE_WSTRING) && (synpred2_InternalSTCoreParser())) {s = 44;}

                        else if ( (LA36_0==NOT) ) {s = 45;}

                         
                        input.seek(index36_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA36_21 = input.LA(1);

                         
                        int index36_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 44;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index36_21);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA36_20 = input.LA(1);

                         
                        int index36_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 44;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index36_20);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 36, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_14s = "\43\uffff";
    static final String dfa_15s = "\1\2\42\uffff";
    static final String dfa_16s = "\1\7\1\0\41\uffff";
    static final String dfa_17s = "\1\153\1\0\41\uffff";
    static final String dfa_18s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_19s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_20s = {
            "\1\2\55\uffff\1\2\2\uffff\1\2\4\uffff\1\2\2\uffff\1\2\5\uffff\6\2\1\uffff\1\2\1\uffff\1\2\3\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\14\2\4\uffff\2\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA43 extends DFA {

        public DFA43(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 43;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "2396:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA43_1 = input.LA(1);

                         
                        int index43_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTCoreParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index43_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 43, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x1EDDDFDF7FEC9072L,0x000EC00452214000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000200001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000080000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x5AD89FDF79EC8070L,0x000EC00052204000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000080020000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000040400000100L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000100L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x5AD89FDF79EC8070L,0x000EC40052204000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x1EDFDFFF7FFC9070L,0x000EC00452214000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0002002000100000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x1EDDDFDF7FEC9070L,0x000EC00452214000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x5ADA9FDF79ECA070L,0x000EC00052204000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000220000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x000000000000A000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x1EDDDFDF7FED9070L,0x000EC00452214000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x1EDDDFDF7FEC9270L,0x000EC00452214000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x1EDDFFDF7FEC9070L,0x000EC00452214000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0100000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000400L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000002800000A00L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000000050000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x2000000000000002L,0x0000000108000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000040080000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000000L,0x000280000000003EL});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x5AD89FDF79EC8070L,0x000EC00056204000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0080004000000000L,0x0000C00050000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000800050000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x000C000000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000002L,0x0000000080000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0001800000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x000003C000860000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000002L,0x0000900050000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000002L,0x0000800050000000L});

}