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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "TIME_OF_DAY", "END_REPEAT", "VAR_OUTPUT", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "VAR_TEMP", "WSTRING", "END_FOR", "END_VAR", "STRING", "DWORD", "END_IF", "LDATE", "LREAL", "LTIME", "LWORD", "REPEAT", "RETURN", "UDINT", "ULINT", "USINT", "WCHAR", "ARRAY", "BOOL", "BYTE", "CHAR", "DATE", "DINT", "ELSIF", "FALSE", "LINT", "LTOD", "REAL", "SINT", "TIME", "UINT", "UNTIL", "WHILE", "WORD", "CASE", "ELSE", "EXIT", "INT", "LDT", "THEN", "TOD", "TRUE", "B", "D_1", "L", "W", "X", "AND", "DT", "FOR", "LD", "LT", "MOD", "NOT", "VAR", "XOR", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "D", "DO", "IF", "OF", "OR", "T", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "LeftSquareBracket", "RightSquareBracket", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_TIME", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_WSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=24;
    public static final int LessThanSignGreaterThanSign=74;
    public static final int VAR=68;
    public static final int END_IF=20;
    public static final int ULINT=28;
    public static final int END_CASE=13;
    public static final int LessThanSign=97;
    public static final int LeftParenthesis=87;
    public static final int BYTE=33;
    public static final int ELSE=49;
    public static final int RULE_TIME=105;
    public static final int IF=80;
    public static final int LINT=39;
    public static final int GreaterThanSign=99;
    public static final int WORD=47;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=107;
    public static final int TOD=54;
    public static final int DINT=36;
    public static final int UDINT=27;
    public static final int CASE=48;
    public static final int GreaterThanSignEqualsSign=75;
    public static final int AT=76;
    public static final int PlusSign=90;
    public static final int RULE_INT=104;
    public static final int END_FOR=16;
    public static final int RULE_ML_COMMENT=110;
    public static final int THEN=53;
    public static final int XOR=69;
    public static final int LeftSquareBracket=100;
    public static final int EXIT=50;
    public static final int TIME_OF_DAY=6;
    public static final int B=56;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=25;
    public static final int D=78;
    public static final int CHAR=34;
    public static final int L=58;
    public static final int LTIME=23;
    public static final int Comma=91;
    public static final int HyphenMinus=92;
    public static final int T=83;
    public static final int W=59;
    public static final int BY=77;
    public static final int X=60;
    public static final int ELSIF=37;
    public static final int END_REPEAT=7;
    public static final int LessThanSignEqualsSign=73;
    public static final int Solidus=94;
    public static final int VAR_INPUT=10;
    public static final int FullStop=93;
    public static final int VAR_TEMP=14;
    public static final int CONSTANT=11;
    public static final int CONTINUE=12;
    public static final int Semicolon=96;
    public static final int LD=64;
    public static final int VAR_OUTPUT=8;
    public static final int STRING=18;
    public static final int RULE_HEX_DIGIT=102;
    public static final int TO=84;
    public static final int UINT=44;
    public static final int LTOD=40;
    public static final int ARRAY=31;
    public static final int LT=65;
    public static final int DO=79;
    public static final int WSTRING=15;
    public static final int DT=62;
    public static final int END_VAR=17;
    public static final int FullStopFullStop=71;
    public static final int Ampersand=86;
    public static final int RightSquareBracket=101;
    public static final int USINT=29;
    public static final int DWORD=19;
    public static final int FOR=63;
    public static final int RightParenthesis=88;
    public static final int TRUE=55;
    public static final int ColonEqualsSign=72;
    public static final int RULE_WSTRING=109;
    public static final int END_WHILE=9;
    public static final int DATE=35;
    public static final int NOT=67;
    public static final int LDATE=21;
    public static final int AND=61;
    public static final int NumberSign=85;
    public static final int REAL=41;
    public static final int AsteriskAsterisk=70;
    public static final int SINT=42;
    public static final int LREAL=22;
    public static final int WCHAR=30;
    public static final int RULE_STRING=108;
    public static final int INT=51;
    public static final int RULE_SL_COMMENT=111;
    public static final int RETURN=26;
    public static final int EqualsSign=98;
    public static final int OF=81;
    public static final int Colon=95;
    public static final int EOF=-1;
    public static final int LDT=52;
    public static final int Asterisk=89;
    public static final int MOD=66;
    public static final int OR=82;
    public static final int RULE_WS=112;
    public static final int RULE_EXT_INT=106;
    public static final int TIME=43;
    public static final int RULE_ANY_OTHER=113;
    public static final int UNTIL=45;
    public static final int BOOL=32;
    public static final int D_1=57;
    public static final int FALSE=38;
    public static final int WHILE=46;
    public static final int RULE_NON_DECIMAL=103;

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

                if ( (LA1_0==CONTINUE||(LA1_0>=REPEAT && LA1_0<=RETURN)||LA1_0==WHILE||LA1_0==CASE||LA1_0==EXIT||LA1_0==FOR||LA1_0==IF||LA1_0==Semicolon||LA1_0==RULE_ID) ) {
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
    // InternalSTCoreParser.g:101:1: ruleVarDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon ) ;
    public final EObject ruleVarDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_array_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_count_11_0=null;
        Token otherlv_12=null;
        Token lv_count_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        EObject lv_ranges_6_0 = null;

        EObject lv_ranges_8_0 = null;

        EObject lv_maxLength_18_0 = null;

        EObject lv_defaultValue_21_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:107:2: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon ) )
            // InternalSTCoreParser.g:108:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon )
            {
            // InternalSTCoreParser.g:108:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon )
            // InternalSTCoreParser.g:109:3: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon
            {
            // InternalSTCoreParser.g:109:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalSTCoreParser.g:110:4: (lv_name_0_0= RULE_ID )
            {
            // InternalSTCoreParser.g:110:4: (lv_name_0_0= RULE_ID )
            // InternalSTCoreParser.g:111:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getVarDeclarationAccess().getNameIDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVarDeclarationRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
              				
            }

            }


            }

            // InternalSTCoreParser.g:127:3: (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==AT) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalSTCoreParser.g:128:4: otherlv_1= AT ( (otherlv_2= RULE_ID ) )
                    {
                    otherlv_1=(Token)match(input,AT,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getVarDeclarationAccess().getATKeyword_1_0());
                      			
                    }
                    // InternalSTCoreParser.g:132:4: ( (otherlv_2= RULE_ID ) )
                    // InternalSTCoreParser.g:133:5: (otherlv_2= RULE_ID )
                    {
                    // InternalSTCoreParser.g:133:5: (otherlv_2= RULE_ID )
                    // InternalSTCoreParser.g:134:6: otherlv_2= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_2=(Token)match(input,RULE_ID,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_2, grammarAccess.getVarDeclarationAccess().getLocatedAtVarDeclarationCrossReference_1_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,Colon,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getVarDeclarationAccess().getColonKeyword_2());
              		
            }
            // InternalSTCoreParser.g:150:3: ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ARRAY) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSTCoreParser.g:151:4: ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF
                    {
                    // InternalSTCoreParser.g:151:4: ( (lv_array_4_0= ARRAY ) )
                    // InternalSTCoreParser.g:152:5: (lv_array_4_0= ARRAY )
                    {
                    // InternalSTCoreParser.g:152:5: (lv_array_4_0= ARRAY )
                    // InternalSTCoreParser.g:153:6: lv_array_4_0= ARRAY
                    {
                    lv_array_4_0=(Token)match(input,ARRAY,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_array_4_0, grammarAccess.getVarDeclarationAccess().getArrayARRAYKeyword_3_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getVarDeclarationRule());
                      						}
                      						setWithLastConsumed(current, "array", lv_array_4_0 != null, "ARRAY");
                      					
                    }

                    }


                    }

                    // InternalSTCoreParser.g:165:4: ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) )
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==LeftSquareBracket) ) {
                        int LA5_1 = input.LA(2);

                        if ( ((LA5_1>=LDATE_AND_TIME && LA5_1<=TIME_OF_DAY)||LA5_1==WSTRING||(LA5_1>=STRING && LA5_1<=DWORD)||(LA5_1>=LDATE && LA5_1<=LWORD)||(LA5_1>=UDINT && LA5_1<=WCHAR)||(LA5_1>=BOOL && LA5_1<=DINT)||(LA5_1>=FALSE && LA5_1<=UINT)||LA5_1==WORD||(LA5_1>=INT && LA5_1<=LDT)||(LA5_1>=TOD && LA5_1<=TRUE)||LA5_1==DT||(LA5_1>=LD && LA5_1<=LT)||LA5_1==NOT||LA5_1==D||LA5_1==T||LA5_1==LeftParenthesis||LA5_1==PlusSign||LA5_1==HyphenMinus||(LA5_1>=RULE_NON_DECIMAL && LA5_1<=RULE_INT)||(LA5_1>=RULE_ID && LA5_1<=RULE_STRING)) ) {
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
                            // InternalSTCoreParser.g:166:5: (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
                            {
                            // InternalSTCoreParser.g:166:5: (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
                            // InternalSTCoreParser.g:167:6: otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
                            {
                            otherlv_5=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_5, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_3_1_0_0());
                              					
                            }
                            // InternalSTCoreParser.g:171:6: ( (lv_ranges_6_0= ruleSTExpression ) )
                            // InternalSTCoreParser.g:172:7: (lv_ranges_6_0= ruleSTExpression )
                            {
                            // InternalSTCoreParser.g:172:7: (lv_ranges_6_0= ruleSTExpression )
                            // InternalSTCoreParser.g:173:8: lv_ranges_6_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getVarDeclarationAccess().getRangesSTExpressionParserRuleCall_3_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_10);
                            lv_ranges_6_0=ruleSTExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getVarDeclarationRule());
                              								}
                              								add(
                              									current,
                              									"ranges",
                              									lv_ranges_6_0,
                              									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            // InternalSTCoreParser.g:190:6: (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )*
                            loop3:
                            do {
                                int alt3=2;
                                int LA3_0 = input.LA(1);

                                if ( (LA3_0==Comma) ) {
                                    alt3=1;
                                }


                                switch (alt3) {
                            	case 1 :
                            	    // InternalSTCoreParser.g:191:7: otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_7=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_7, grammarAccess.getVarDeclarationAccess().getCommaKeyword_3_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTCoreParser.g:195:7: ( (lv_ranges_8_0= ruleSTExpression ) )
                            	    // InternalSTCoreParser.g:196:8: (lv_ranges_8_0= ruleSTExpression )
                            	    {
                            	    // InternalSTCoreParser.g:196:8: (lv_ranges_8_0= ruleSTExpression )
                            	    // InternalSTCoreParser.g:197:9: lv_ranges_8_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getVarDeclarationAccess().getRangesSTExpressionParserRuleCall_3_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_10);
                            	    lv_ranges_8_0=ruleSTExpression();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElementForParent(grammarAccess.getVarDeclarationRule());
                            	      									}
                            	      									add(
                            	      										current,
                            	      										"ranges",
                            	      										lv_ranges_8_0,
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

                            otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_11); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_9, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_3_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:221:5: (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket )
                            {
                            // InternalSTCoreParser.g:221:5: (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket )
                            // InternalSTCoreParser.g:222:6: otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket
                            {
                            otherlv_10=(Token)match(input,LeftSquareBracket,FOLLOW_12); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_3_1_1_0());
                              					
                            }
                            // InternalSTCoreParser.g:226:6: ( (lv_count_11_0= Asterisk ) )
                            // InternalSTCoreParser.g:227:7: (lv_count_11_0= Asterisk )
                            {
                            // InternalSTCoreParser.g:227:7: (lv_count_11_0= Asterisk )
                            // InternalSTCoreParser.g:228:8: lv_count_11_0= Asterisk
                            {
                            lv_count_11_0=(Token)match(input,Asterisk,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_count_11_0, grammarAccess.getVarDeclarationAccess().getCountAsteriskKeyword_3_1_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getVarDeclarationRule());
                              								}
                              								addWithLastConsumed(current, "count", lv_count_11_0, "*");
                              							
                            }

                            }


                            }

                            // InternalSTCoreParser.g:240:6: (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )*
                            loop4:
                            do {
                                int alt4=2;
                                int LA4_0 = input.LA(1);

                                if ( (LA4_0==Comma) ) {
                                    alt4=1;
                                }


                                switch (alt4) {
                            	case 1 :
                            	    // InternalSTCoreParser.g:241:7: otherlv_12= Comma ( (lv_count_13_0= Asterisk ) )
                            	    {
                            	    otherlv_12=(Token)match(input,Comma,FOLLOW_12); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_12, grammarAccess.getVarDeclarationAccess().getCommaKeyword_3_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTCoreParser.g:245:7: ( (lv_count_13_0= Asterisk ) )
                            	    // InternalSTCoreParser.g:246:8: (lv_count_13_0= Asterisk )
                            	    {
                            	    // InternalSTCoreParser.g:246:8: (lv_count_13_0= Asterisk )
                            	    // InternalSTCoreParser.g:247:9: lv_count_13_0= Asterisk
                            	    {
                            	    lv_count_13_0=(Token)match(input,Asterisk,FOLLOW_10); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									newLeafNode(lv_count_13_0, grammarAccess.getVarDeclarationAccess().getCountAsteriskKeyword_3_1_1_2_1_0());
                            	      								
                            	    }
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElement(grammarAccess.getVarDeclarationRule());
                            	      									}
                            	      									addWithLastConsumed(current, "count", lv_count_13_0, "*");
                            	      								
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop4;
                                }
                            } while (true);

                            otherlv_14=(Token)match(input,RightSquareBracket,FOLLOW_11); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_14, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_3_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_15=(Token)match(input,OF,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_15, grammarAccess.getVarDeclarationAccess().getOFKeyword_3_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:271:3: ( (otherlv_16= RULE_ID ) )
            // InternalSTCoreParser.g:272:4: (otherlv_16= RULE_ID )
            {
            // InternalSTCoreParser.g:272:4: (otherlv_16= RULE_ID )
            // InternalSTCoreParser.g:273:5: otherlv_16= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVarDeclarationRule());
              					}
              				
            }
            otherlv_16=(Token)match(input,RULE_ID,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_16, grammarAccess.getVarDeclarationAccess().getTypeLibraryElementCrossReference_4_0());
              				
            }

            }


            }

            // InternalSTCoreParser.g:284:3: (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LeftSquareBracket) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSTCoreParser.g:285:4: otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket
                    {
                    otherlv_17=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_17, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_5_0());
                      			
                    }
                    // InternalSTCoreParser.g:289:4: ( (lv_maxLength_18_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:290:5: (lv_maxLength_18_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:290:5: (lv_maxLength_18_0= ruleSTExpression )
                    // InternalSTCoreParser.g:291:6: lv_maxLength_18_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_14);
                    lv_maxLength_18_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getVarDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"maxLength",
                      							lv_maxLength_18_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_19=(Token)match(input,RightSquareBracket,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_5_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:313:3: (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==ColonEqualsSign) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSTCoreParser.g:314:4: otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) )
                    {
                    otherlv_20=(Token)match(input,ColonEqualsSign,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getVarDeclarationAccess().getColonEqualsSignKeyword_6_0());
                      			
                    }
                    // InternalSTCoreParser.g:318:4: ( (lv_defaultValue_21_0= ruleInitializerExpression ) )
                    // InternalSTCoreParser.g:319:5: (lv_defaultValue_21_0= ruleInitializerExpression )
                    {
                    // InternalSTCoreParser.g:319:5: (lv_defaultValue_21_0= ruleInitializerExpression )
                    // InternalSTCoreParser.g:320:6: lv_defaultValue_21_0= ruleInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVarDeclarationAccess().getDefaultValueInitializerExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_defaultValue_21_0=ruleInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getVarDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"defaultValue",
                      							lv_defaultValue_21_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.InitializerExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_22=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_22, grammarAccess.getVarDeclarationAccess().getSemicolonKeyword_7());
              		
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
    // InternalSTCoreParser.g:346:1: entryRuleInitializerExpression returns [EObject current=null] : iv_ruleInitializerExpression= ruleInitializerExpression EOF ;
    public final EObject entryRuleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitializerExpression = null;


        try {
            // InternalSTCoreParser.g:346:62: (iv_ruleInitializerExpression= ruleInitializerExpression EOF )
            // InternalSTCoreParser.g:347:2: iv_ruleInitializerExpression= ruleInitializerExpression EOF
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
    // InternalSTCoreParser.g:353:1: ruleInitializerExpression returns [EObject current=null] : (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) ;
    public final EObject ruleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STExpression_0 = null;

        EObject this_ArrayInitializerExpression_1 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:359:2: ( (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) )
            // InternalSTCoreParser.g:360:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            {
            // InternalSTCoreParser.g:360:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=LDATE_AND_TIME && LA9_0<=TIME_OF_DAY)||LA9_0==WSTRING||(LA9_0>=STRING && LA9_0<=DWORD)||(LA9_0>=LDATE && LA9_0<=LWORD)||(LA9_0>=UDINT && LA9_0<=WCHAR)||(LA9_0>=BOOL && LA9_0<=DINT)||(LA9_0>=FALSE && LA9_0<=UINT)||LA9_0==WORD||(LA9_0>=INT && LA9_0<=LDT)||(LA9_0>=TOD && LA9_0<=TRUE)||LA9_0==DT||(LA9_0>=LD && LA9_0<=LT)||LA9_0==NOT||LA9_0==D||LA9_0==T||LA9_0==LeftParenthesis||LA9_0==PlusSign||LA9_0==HyphenMinus||(LA9_0>=RULE_NON_DECIMAL && LA9_0<=RULE_INT)||(LA9_0>=RULE_ID && LA9_0<=RULE_STRING)) ) {
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
                    // InternalSTCoreParser.g:361:3: this_STExpression_0= ruleSTExpression
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
                    // InternalSTCoreParser.g:370:3: this_ArrayInitializerExpression_1= ruleArrayInitializerExpression
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
    // InternalSTCoreParser.g:382:1: entryRuleArrayInitializerExpression returns [EObject current=null] : iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF ;
    public final EObject entryRuleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitializerExpression = null;


        try {
            // InternalSTCoreParser.g:382:67: (iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF )
            // InternalSTCoreParser.g:383:2: iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF
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
    // InternalSTCoreParser.g:389:1: ruleArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:395:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTCoreParser.g:396:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTCoreParser.g:396:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTCoreParser.g:397:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTCoreParser.g:401:3: ( (lv_values_1_0= ruleArrayInitElement ) )
            // InternalSTCoreParser.g:402:4: (lv_values_1_0= ruleArrayInitElement )
            {
            // InternalSTCoreParser.g:402:4: (lv_values_1_0= ruleArrayInitElement )
            // InternalSTCoreParser.g:403:5: lv_values_1_0= ruleArrayInitElement
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

            // InternalSTCoreParser.g:420:3: (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==Comma) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSTCoreParser.g:421:4: otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTCoreParser.g:425:4: ( (lv_values_3_0= ruleArrayInitElement ) )
            	    // InternalSTCoreParser.g:426:5: (lv_values_3_0= ruleArrayInitElement )
            	    {
            	    // InternalSTCoreParser.g:426:5: (lv_values_3_0= ruleArrayInitElement )
            	    // InternalSTCoreParser.g:427:6: lv_values_3_0= ruleArrayInitElement
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
    // InternalSTCoreParser.g:453:1: entryRuleArrayInitElement returns [EObject current=null] : iv_ruleArrayInitElement= ruleArrayInitElement EOF ;
    public final EObject entryRuleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitElement = null;


        try {
            // InternalSTCoreParser.g:453:57: (iv_ruleArrayInitElement= ruleArrayInitElement EOF )
            // InternalSTCoreParser.g:454:2: iv_ruleArrayInitElement= ruleArrayInitElement EOF
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
    // InternalSTCoreParser.g:460:1: ruleArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) ;
    public final EObject ruleArrayInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_indexOrInitExpression_0_0 = null;

        EObject lv_initExpression_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:466:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) )
            // InternalSTCoreParser.g:467:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            {
            // InternalSTCoreParser.g:467:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            // InternalSTCoreParser.g:468:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            {
            // InternalSTCoreParser.g:468:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:469:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:469:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            // InternalSTCoreParser.g:470:5: lv_indexOrInitExpression_0_0= ruleSTExpression
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

            // InternalSTCoreParser.g:487:3: (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==LeftParenthesis) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSTCoreParser.g:488:4: otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalSTCoreParser.g:492:4: ( (lv_initExpression_2_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:493:5: (lv_initExpression_2_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:493:5: (lv_initExpression_2_0= ruleSTExpression )
                    // InternalSTCoreParser.g:494:6: lv_initExpression_2_0= ruleSTExpression
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
    // InternalSTCoreParser.g:520:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTCoreParser.g:520:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTCoreParser.g:521:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalSTCoreParser.g:527:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) ;
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
            // InternalSTCoreParser.g:533:2: ( ( ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) )
            // InternalSTCoreParser.g:534:2: ( ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            {
            // InternalSTCoreParser.g:534:2: ( ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==CONTINUE||(LA13_0>=REPEAT && LA13_0<=RETURN)||LA13_0==WHILE||LA13_0==CASE||LA13_0==EXIT||LA13_0==FOR||LA13_0==IF||LA13_0==RULE_ID) ) {
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
                    // InternalSTCoreParser.g:535:3: ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    {
                    // InternalSTCoreParser.g:535:3: ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    // InternalSTCoreParser.g:536:4: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon
                    {
                    // InternalSTCoreParser.g:536:4: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )
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
                    case RULE_ID:
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
                            // InternalSTCoreParser.g:537:5: this_STIfStatment_0= ruleSTIfStatment
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
                            // InternalSTCoreParser.g:546:5: this_STCaseStatement_1= ruleSTCaseStatement
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
                            // InternalSTCoreParser.g:555:5: this_STForStatement_2= ruleSTForStatement
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
                            // InternalSTCoreParser.g:564:5: this_STWhileStatement_3= ruleSTWhileStatement
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
                            // InternalSTCoreParser.g:573:5: this_STRepeatStatement_4= ruleSTRepeatStatement
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
                            // InternalSTCoreParser.g:582:5: this_STAssignmentStatement_5= ruleSTAssignmentStatement
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
                            // InternalSTCoreParser.g:591:5: ( () otherlv_7= RETURN )
                            {
                            // InternalSTCoreParser.g:591:5: ( () otherlv_7= RETURN )
                            // InternalSTCoreParser.g:592:6: () otherlv_7= RETURN
                            {
                            // InternalSTCoreParser.g:592:6: ()
                            // InternalSTCoreParser.g:593:7: 
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
                            // InternalSTCoreParser.g:605:5: ( () otherlv_9= CONTINUE )
                            {
                            // InternalSTCoreParser.g:605:5: ( () otherlv_9= CONTINUE )
                            // InternalSTCoreParser.g:606:6: () otherlv_9= CONTINUE
                            {
                            // InternalSTCoreParser.g:606:6: ()
                            // InternalSTCoreParser.g:607:7: 
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
                            // InternalSTCoreParser.g:619:5: ( () otherlv_11= EXIT )
                            {
                            // InternalSTCoreParser.g:619:5: ( () otherlv_11= EXIT )
                            // InternalSTCoreParser.g:620:6: () otherlv_11= EXIT
                            {
                            // InternalSTCoreParser.g:620:6: ()
                            // InternalSTCoreParser.g:621:7: 
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
                    // InternalSTCoreParser.g:639:3: ( () otherlv_14= Semicolon )
                    {
                    // InternalSTCoreParser.g:639:3: ( () otherlv_14= Semicolon )
                    // InternalSTCoreParser.g:640:4: () otherlv_14= Semicolon
                    {
                    // InternalSTCoreParser.g:640:4: ()
                    // InternalSTCoreParser.g:641:5: 
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
    // InternalSTCoreParser.g:656:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTCoreParser.g:656:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTCoreParser.g:657:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTCoreParser.g:663:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_rhs_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:669:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) ) ) )
            // InternalSTCoreParser.g:670:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) ) )
            {
            // InternalSTCoreParser.g:670:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) ) )
            // InternalSTCoreParser.g:671:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) )
            {
            // InternalSTCoreParser.g:671:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTCoreParser.g:672:4: (otherlv_0= RULE_ID )
            {
            // InternalSTCoreParser.g:672:4: (otherlv_0= RULE_ID )
            // InternalSTCoreParser.g:673:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTAssignmentStatementRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTAssignmentStatementAccess().getLhsVarDeclarationCrossReference_0_0());
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTAssignmentStatementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTCoreParser.g:688:3: ( (lv_rhs_2_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:689:4: (lv_rhs_2_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:689:4: (lv_rhs_2_0= ruleSTExpression )
            // InternalSTCoreParser.g:690:5: lv_rhs_2_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAssignmentStatementAccess().getRhsSTExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_rhs_2_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTAssignmentStatementRule());
              					}
              					set(
              						current,
              						"rhs",
              						lv_rhs_2_0,
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
    // InternalSTCoreParser.g:711:1: entryRuleSTIfStatment returns [EObject current=null] : iv_ruleSTIfStatment= ruleSTIfStatment EOF ;
    public final EObject entryRuleSTIfStatment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatment = null;


        try {
            // InternalSTCoreParser.g:711:53: (iv_ruleSTIfStatment= ruleSTIfStatment EOF )
            // InternalSTCoreParser.g:712:2: iv_ruleSTIfStatment= ruleSTIfStatment EOF
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
    // InternalSTCoreParser.g:718:1: ruleSTIfStatment returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTCoreParser.g:724:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTCoreParser.g:725:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTCoreParser.g:725:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTCoreParser.g:726:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatmentAccess().getIFKeyword_0());
              		
            }
            // InternalSTCoreParser.g:730:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:731:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:731:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:732:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:753:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==CONTINUE||(LA14_0>=REPEAT && LA14_0<=RETURN)||LA14_0==WHILE||LA14_0==CASE||LA14_0==EXIT||LA14_0==FOR||LA14_0==IF||LA14_0==Semicolon||LA14_0==RULE_ID) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSTCoreParser.g:754:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:754:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:755:5: lv_statements_3_0= ruleSTStatement
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

            // InternalSTCoreParser.g:772:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==ELSIF) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalSTCoreParser.g:773:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTCoreParser.g:773:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTCoreParser.g:774:5: lv_elseifs_4_0= ruleSTElseIfPart
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

            // InternalSTCoreParser.g:791:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ELSE) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSTCoreParser.g:792:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTCoreParser.g:792:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTCoreParser.g:793:5: lv_else_5_0= ruleSTElsePart
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
    // InternalSTCoreParser.g:818:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTCoreParser.g:818:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTCoreParser.g:819:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTCoreParser.g:825:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:831:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:832:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:832:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:833:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTCoreParser.g:837:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:838:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:838:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:839:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:860:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==CONTINUE||(LA17_0>=REPEAT && LA17_0<=RETURN)||LA17_0==WHILE||LA17_0==CASE||LA17_0==EXIT||LA17_0==FOR||LA17_0==IF||LA17_0==Semicolon||LA17_0==RULE_ID) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSTCoreParser.g:861:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:861:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:862:5: lv_statements_3_0= ruleSTStatement
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
    // InternalSTCoreParser.g:883:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTCoreParser.g:883:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTCoreParser.g:884:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTCoreParser.g:890:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTCoreParser.g:896:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTCoreParser.g:897:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTCoreParser.g:897:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTCoreParser.g:898:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTCoreParser.g:902:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:903:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:903:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:904:5: lv_selector_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:925:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=LDATE_AND_TIME && LA18_0<=TIME_OF_DAY)||LA18_0==WSTRING||(LA18_0>=STRING && LA18_0<=DWORD)||(LA18_0>=LDATE && LA18_0<=LWORD)||(LA18_0>=UDINT && LA18_0<=WCHAR)||(LA18_0>=BOOL && LA18_0<=DINT)||(LA18_0>=FALSE && LA18_0<=UINT)||LA18_0==WORD||(LA18_0>=INT && LA18_0<=LDT)||(LA18_0>=TOD && LA18_0<=TRUE)||LA18_0==DT||(LA18_0>=LD && LA18_0<=LT)||LA18_0==NOT||LA18_0==D||LA18_0==T||LA18_0==LeftParenthesis||LA18_0==PlusSign||LA18_0==HyphenMinus||(LA18_0>=RULE_NON_DECIMAL && LA18_0<=RULE_INT)||(LA18_0>=RULE_ID && LA18_0<=RULE_STRING)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalSTCoreParser.g:926:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTCoreParser.g:926:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTCoreParser.g:927:5: lv_cases_3_0= ruleSTCaseCases
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

            // InternalSTCoreParser.g:944:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ELSE) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSTCoreParser.g:945:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTCoreParser.g:945:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTCoreParser.g:946:5: lv_else_4_0= ruleSTElsePart
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
    // InternalSTCoreParser.g:971:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTCoreParser.g:971:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTCoreParser.g:972:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTCoreParser.g:978:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:984:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:985:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:985:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:986:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:986:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:987:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:987:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTCoreParser.g:988:5: lv_conditions_0_0= ruleSTExpression
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

            // InternalSTCoreParser.g:1005:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==Comma) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSTCoreParser.g:1006:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTCoreParser.g:1010:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTCoreParser.g:1011:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTCoreParser.g:1011:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTCoreParser.g:1012:6: lv_conditions_2_0= ruleSTExpression
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
            // InternalSTCoreParser.g:1034:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop21:
            do {
                int alt21=2;
                alt21 = dfa21.predict(input);
                switch (alt21) {
            	case 1 :
            	    // InternalSTCoreParser.g:1035:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1039:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1040:5: lv_statements_4_0= ruleSTStatement
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
    // InternalSTCoreParser.g:1061:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTCoreParser.g:1061:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTCoreParser.g:1062:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTCoreParser.g:1068:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1074:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:1075:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:1075:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:1076:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:1076:3: ()
            // InternalSTCoreParser.g:1077:4: 
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
            // InternalSTCoreParser.g:1087:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==CONTINUE||(LA22_0>=REPEAT && LA22_0<=RETURN)||LA22_0==WHILE||LA22_0==CASE||LA22_0==EXIT||LA22_0==FOR||LA22_0==IF||LA22_0==Semicolon||LA22_0==RULE_ID) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalSTCoreParser.g:1088:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1088:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1089:5: lv_statements_2_0= ruleSTStatement
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
    // InternalSTCoreParser.g:1110:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTCoreParser.g:1110:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTCoreParser.g:1111:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTCoreParser.g:1117:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) ;
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
            // InternalSTCoreParser.g:1123:2: ( (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) )
            // InternalSTCoreParser.g:1124:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            {
            // InternalSTCoreParser.g:1124:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            // InternalSTCoreParser.g:1125:3: otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1129:3: ( (lv_for_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1130:4: (lv_for_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1130:4: (lv_for_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1131:5: lv_for_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:1152:3: ( (lv_to_3_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1153:4: (lv_to_3_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1153:4: (lv_to_3_0= ruleSTExpression )
            // InternalSTCoreParser.g:1154:5: lv_to_3_0= ruleSTExpression
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

            // InternalSTCoreParser.g:1171:3: (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==BY) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSTCoreParser.g:1172:4: otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) )
                    {
                    otherlv_4=(Token)match(input,BY,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getBYKeyword_4_0());
                      			
                    }
                    // InternalSTCoreParser.g:1176:4: ( (lv_by_5_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:1177:5: (lv_by_5_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:1177:5: (lv_by_5_0= ruleSTExpression )
                    // InternalSTCoreParser.g:1178:6: lv_by_5_0= ruleSTExpression
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
            // InternalSTCoreParser.g:1200:3: ( (lv_statements_7_0= ruleSTStatement ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==CONTINUE||(LA24_0>=REPEAT && LA24_0<=RETURN)||LA24_0==WHILE||LA24_0==CASE||LA24_0==EXIT||LA24_0==FOR||LA24_0==IF||LA24_0==Semicolon||LA24_0==RULE_ID) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalSTCoreParser.g:1201:4: (lv_statements_7_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1201:4: (lv_statements_7_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1202:5: lv_statements_7_0= ruleSTStatement
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
    // InternalSTCoreParser.g:1227:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTCoreParser.g:1227:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTCoreParser.g:1228:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTCoreParser.g:1234:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1240:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTCoreParser.g:1241:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTCoreParser.g:1241:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTCoreParser.g:1242:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1246:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1247:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1247:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1248:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:1269:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==CONTINUE||(LA25_0>=REPEAT && LA25_0<=RETURN)||LA25_0==WHILE||LA25_0==CASE||LA25_0==EXIT||LA25_0==FOR||LA25_0==IF||LA25_0==Semicolon||LA25_0==RULE_ID) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalSTCoreParser.g:1270:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1270:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1271:5: lv_statements_3_0= ruleSTStatement
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
    // InternalSTCoreParser.g:1296:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTCoreParser.g:1296:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTCoreParser.g:1297:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTCoreParser.g:1303:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1309:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTCoreParser.g:1310:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTCoreParser.g:1310:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTCoreParser.g:1311:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1315:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==CONTINUE||(LA26_0>=REPEAT && LA26_0<=RETURN)||LA26_0==WHILE||LA26_0==CASE||LA26_0==EXIT||LA26_0==FOR||LA26_0==IF||LA26_0==Semicolon||LA26_0==RULE_ID) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSTCoreParser.g:1316:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1316:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1317:5: lv_statements_1_0= ruleSTStatement
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
            // InternalSTCoreParser.g:1338:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1339:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1339:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTCoreParser.g:1340:5: lv_condition_3_0= ruleSTExpression
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
    // InternalSTCoreParser.g:1365:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTCoreParser.g:1365:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTCoreParser.g:1366:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTCoreParser.g:1372:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1378:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTCoreParser.g:1379:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTCoreParser.g:1390:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTCoreParser.g:1390:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTCoreParser.g:1391:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTCoreParser.g:1397:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1403:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTCoreParser.g:1404:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1404:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTCoreParser.g:1405:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
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
            // InternalSTCoreParser.g:1413:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==FullStopFullStop) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSTCoreParser.g:1414:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1414:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTCoreParser.g:1415:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1415:5: ()
            	    // InternalSTCoreParser.g:1416:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1422:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTCoreParser.g:1423:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTCoreParser.g:1423:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTCoreParser.g:1424:7: lv_op_2_0= ruleSubrangeOperator
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

            	    // InternalSTCoreParser.g:1442:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTCoreParser.g:1443:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTCoreParser.g:1443:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTCoreParser.g:1444:6: lv_right_3_0= ruleSTOrExpression
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
    // InternalSTCoreParser.g:1466:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTCoreParser.g:1466:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTCoreParser.g:1467:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTCoreParser.g:1473:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1479:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTCoreParser.g:1480:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1480:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTCoreParser.g:1481:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
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
            // InternalSTCoreParser.g:1489:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==OR) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSTCoreParser.g:1490:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1490:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTCoreParser.g:1491:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1491:5: ()
            	    // InternalSTCoreParser.g:1492:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1498:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTCoreParser.g:1499:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTCoreParser.g:1499:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTCoreParser.g:1500:7: lv_op_2_0= ruleOrOperator
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

            	    // InternalSTCoreParser.g:1518:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTCoreParser.g:1519:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTCoreParser.g:1519:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTCoreParser.g:1520:6: lv_right_3_0= ruleSTXorExpression
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
    // InternalSTCoreParser.g:1542:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTCoreParser.g:1542:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTCoreParser.g:1543:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTCoreParser.g:1549:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1555:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTCoreParser.g:1556:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1556:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTCoreParser.g:1557:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
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
            // InternalSTCoreParser.g:1565:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==XOR) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSTCoreParser.g:1566:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1566:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTCoreParser.g:1567:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1567:5: ()
            	    // InternalSTCoreParser.g:1568:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1574:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTCoreParser.g:1575:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTCoreParser.g:1575:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTCoreParser.g:1576:7: lv_op_2_0= ruleXorOperator
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

            	    // InternalSTCoreParser.g:1594:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTCoreParser.g:1595:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTCoreParser.g:1595:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTCoreParser.g:1596:6: lv_right_3_0= ruleSTAndExpression
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
    // InternalSTCoreParser.g:1618:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTCoreParser.g:1618:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTCoreParser.g:1619:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTCoreParser.g:1625:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1631:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTCoreParser.g:1632:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1632:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTCoreParser.g:1633:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
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
            // InternalSTCoreParser.g:1641:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==AND||LA30_0==Ampersand) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSTCoreParser.g:1642:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1642:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTCoreParser.g:1643:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1643:5: ()
            	    // InternalSTCoreParser.g:1644:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1650:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTCoreParser.g:1651:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTCoreParser.g:1651:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTCoreParser.g:1652:7: lv_op_2_0= ruleAndOperator
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

            	    // InternalSTCoreParser.g:1670:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTCoreParser.g:1671:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTCoreParser.g:1671:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTCoreParser.g:1672:6: lv_right_3_0= ruleSTEqualityExpression
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
    // InternalSTCoreParser.g:1694:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTCoreParser.g:1694:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTCoreParser.g:1695:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTCoreParser.g:1701:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1707:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTCoreParser.g:1708:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1708:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTCoreParser.g:1709:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
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
            // InternalSTCoreParser.g:1717:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==LessThanSignGreaterThanSign||LA31_0==EqualsSign) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTCoreParser.g:1718:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1718:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTCoreParser.g:1719:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1719:5: ()
            	    // InternalSTCoreParser.g:1720:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1726:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTCoreParser.g:1727:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTCoreParser.g:1727:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTCoreParser.g:1728:7: lv_op_2_0= ruleEqualityOperator
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

            	    // InternalSTCoreParser.g:1746:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTCoreParser.g:1747:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTCoreParser.g:1747:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTCoreParser.g:1748:6: lv_right_3_0= ruleSTComparisonExpression
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
    // InternalSTCoreParser.g:1770:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTCoreParser.g:1770:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTCoreParser.g:1771:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTCoreParser.g:1777:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1783:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTCoreParser.g:1784:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1784:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTCoreParser.g:1785:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
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
            // InternalSTCoreParser.g:1793:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==LessThanSignEqualsSign||LA32_0==GreaterThanSignEqualsSign||LA32_0==LessThanSign||LA32_0==GreaterThanSign) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSTCoreParser.g:1794:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1794:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTCoreParser.g:1795:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1795:5: ()
            	    // InternalSTCoreParser.g:1796:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1802:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTCoreParser.g:1803:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTCoreParser.g:1803:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTCoreParser.g:1804:7: lv_op_2_0= ruleCompareOperator
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

            	    // InternalSTCoreParser.g:1822:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTCoreParser.g:1823:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTCoreParser.g:1823:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTCoreParser.g:1824:6: lv_right_3_0= ruleSTAddSubExpression
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
    // InternalSTCoreParser.g:1846:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTCoreParser.g:1846:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTCoreParser.g:1847:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTCoreParser.g:1853:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1859:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTCoreParser.g:1860:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1860:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTCoreParser.g:1861:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
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
            // InternalSTCoreParser.g:1869:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==PlusSign||LA33_0==HyphenMinus) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSTCoreParser.g:1870:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1870:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTCoreParser.g:1871:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1871:5: ()
            	    // InternalSTCoreParser.g:1872:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1878:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTCoreParser.g:1879:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTCoreParser.g:1879:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTCoreParser.g:1880:7: lv_op_2_0= ruleAddSubOperator
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

            	    // InternalSTCoreParser.g:1898:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTCoreParser.g:1899:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTCoreParser.g:1899:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTCoreParser.g:1900:6: lv_right_3_0= ruleSTMulDivModExpression
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
    // InternalSTCoreParser.g:1922:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTCoreParser.g:1922:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTCoreParser.g:1923:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTCoreParser.g:1929:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1935:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTCoreParser.g:1936:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1936:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTCoreParser.g:1937:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
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
            // InternalSTCoreParser.g:1945:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==MOD||LA34_0==Asterisk||LA34_0==Solidus) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTCoreParser.g:1946:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1946:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTCoreParser.g:1947:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1947:5: ()
            	    // InternalSTCoreParser.g:1948:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1954:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTCoreParser.g:1955:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTCoreParser.g:1955:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTCoreParser.g:1956:7: lv_op_2_0= ruleMulDivModOperator
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

            	    // InternalSTCoreParser.g:1974:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTCoreParser.g:1975:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTCoreParser.g:1975:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTCoreParser.g:1976:6: lv_right_3_0= ruleSTPowerExpression
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
    // InternalSTCoreParser.g:1998:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTCoreParser.g:1998:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTCoreParser.g:1999:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTCoreParser.g:2005:1: ruleSTPowerExpression returns [EObject current=null] : (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSignumExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2011:2: ( (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* ) )
            // InternalSTCoreParser.g:2012:2: (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2012:2: (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* )
            // InternalSTCoreParser.g:2013:3: this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTSignumExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_STSignumExpression_0=ruleSTSignumExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STSignumExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2021:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==AsteriskAsterisk) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSTCoreParser.g:2022:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2022:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTCoreParser.g:2023:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTCoreParser.g:2023:5: ()
            	    // InternalSTCoreParser.g:2024:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2030:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTCoreParser.g:2031:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTCoreParser.g:2031:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTCoreParser.g:2032:7: lv_op_2_0= rulePowerOperator
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

            	    // InternalSTCoreParser.g:2050:4: ( (lv_right_3_0= ruleSTSignumExpression ) )
            	    // InternalSTCoreParser.g:2051:5: (lv_right_3_0= ruleSTSignumExpression )
            	    {
            	    // InternalSTCoreParser.g:2051:5: (lv_right_3_0= ruleSTSignumExpression )
            	    // InternalSTCoreParser.g:2052:6: lv_right_3_0= ruleSTSignumExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTSignumExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
            	    lv_right_3_0=ruleSTSignumExpression();

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
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STSignumExpression");
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


    // $ANTLR start "entryRuleSTSignumExpression"
    // InternalSTCoreParser.g:2074:1: entryRuleSTSignumExpression returns [EObject current=null] : iv_ruleSTSignumExpression= ruleSTSignumExpression EOF ;
    public final EObject entryRuleSTSignumExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignumExpression = null;


        try {
            // InternalSTCoreParser.g:2074:59: (iv_ruleSTSignumExpression= ruleSTSignumExpression EOF )
            // InternalSTCoreParser.g:2075:2: iv_ruleSTSignumExpression= ruleSTSignumExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTSignumExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTSignumExpression=ruleSTSignumExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTSignumExpression; 
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
    // $ANTLR end "entryRuleSTSignumExpression"


    // $ANTLR start "ruleSTSignumExpression"
    // InternalSTCoreParser.g:2081:1: ruleSTSignumExpression returns [EObject current=null] : (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) ) ;
    public final EObject ruleSTSignumExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STLiteralExpressions_0 = null;

        EObject this_STSelectionExpression_1 = null;

        Enumerator lv_op_3_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2087:2: ( (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) ) )
            // InternalSTCoreParser.g:2088:2: (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) )
            {
            // InternalSTCoreParser.g:2088:2: (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) )
            int alt36=3;
            switch ( input.LA(1) ) {
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
            case D:
            case T:
            case RULE_NON_DECIMAL:
            case RULE_INT:
            case RULE_STRING:
                {
                alt36=1;
                }
                break;
            case PlusSign:
                {
                int LA36_2 = input.LA(2);

                if ( (LA36_2==RULE_INT) ) {
                    alt36=1;
                }
                else if ( (LA36_2==LeftParenthesis||LA36_2==RULE_ID) ) {
                    alt36=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 2, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA36_3 = input.LA(2);

                if ( (LA36_3==RULE_INT) ) {
                    alt36=1;
                }
                else if ( (LA36_3==LeftParenthesis||LA36_3==RULE_ID) ) {
                    alt36=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 3, input);

                    throw nvae;
                }
                }
                break;
            case LeftParenthesis:
            case RULE_ID:
                {
                alt36=2;
                }
                break;
            case NOT:
                {
                alt36=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // InternalSTCoreParser.g:2089:3: this_STLiteralExpressions_0= ruleSTLiteralExpressions
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTSignumExpressionAccess().getSTLiteralExpressionsParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STLiteralExpressions_0=ruleSTLiteralExpressions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STLiteralExpressions_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2098:3: this_STSelectionExpression_1= ruleSTSelectionExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTSignumExpressionAccess().getSTSelectionExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STSelectionExpression_1=ruleSTSelectionExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STSelectionExpression_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2107:3: ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) )
                    {
                    // InternalSTCoreParser.g:2107:3: ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) )
                    // InternalSTCoreParser.g:2108:4: () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) )
                    {
                    // InternalSTCoreParser.g:2108:4: ()
                    // InternalSTCoreParser.g:2109:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTSignumExpressionAccess().getSTUnaryExpressionAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2115:4: ( (lv_op_3_0= ruleUnaryOperator ) )
                    // InternalSTCoreParser.g:2116:5: (lv_op_3_0= ruleUnaryOperator )
                    {
                    // InternalSTCoreParser.g:2116:5: (lv_op_3_0= ruleUnaryOperator )
                    // InternalSTCoreParser.g:2117:6: lv_op_3_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTSignumExpressionAccess().getOpUnaryOperatorEnumRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_45);
                    lv_op_3_0=ruleUnaryOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTSignumExpressionRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_3_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.UnaryOperator");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSTCoreParser.g:2134:4: ( (lv_expression_4_0= ruleSTSelectionExpression ) )
                    // InternalSTCoreParser.g:2135:5: (lv_expression_4_0= ruleSTSelectionExpression )
                    {
                    // InternalSTCoreParser.g:2135:5: (lv_expression_4_0= ruleSTSelectionExpression )
                    // InternalSTCoreParser.g:2136:6: lv_expression_4_0= ruleSTSelectionExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTSignumExpressionAccess().getExpressionSTSelectionExpressionParserRuleCall_2_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_4_0=ruleSTSelectionExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTSignumExpressionRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_4_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STSelectionExpression");
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
    // $ANTLR end "ruleSTSignumExpression"


    // $ANTLR start "entryRuleSTSelectionExpression"
    // InternalSTCoreParser.g:2158:1: entryRuleSTSelectionExpression returns [EObject current=null] : iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF ;
    public final EObject entryRuleSTSelectionExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSelectionExpression = null;


        try {
            // InternalSTCoreParser.g:2158:62: (iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF )
            // InternalSTCoreParser.g:2159:2: iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTSelectionExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTSelectionExpression=ruleSTSelectionExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTSelectionExpression; 
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
    // $ANTLR end "entryRuleSTSelectionExpression"


    // $ANTLR start "ruleSTSelectionExpression"
    // InternalSTCoreParser.g:2165:1: ruleSTSelectionExpression returns [EObject current=null] : (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* ) ;
    public final EObject ruleSTSelectionExpression() throws RecognitionException {
        EObject current = null;

        Token lv_structAccess_2_0=null;
        Token otherlv_3=null;
        Token lv_arrayAccess_4_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_poeInvocation_9_0=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        EObject this_STAtomicExpression_0 = null;

        EObject lv_index_5_0 = null;

        EObject lv_index_7_0 = null;

        EObject lv_parameters_10_0 = null;

        EObject lv_parameters_12_0 = null;

        EObject lv_bitaccessor_14_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2171:2: ( (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* ) )
            // InternalSTCoreParser.g:2172:2: (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* )
            {
            // InternalSTCoreParser.g:2172:2: (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* )
            // InternalSTCoreParser.g:2173:3: this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getSTAtomicExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_STAtomicExpression_0=ruleSTAtomicExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAtomicExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2181:3: ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==FullStop||LA43_0==LeftSquareBracket) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSTCoreParser.g:2182:4: () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )?
            	    {
            	    // InternalSTCoreParser.g:2182:4: ()
            	    // InternalSTCoreParser.g:2183:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getSTSelectionExpressionAccess().getSTMemberSelectionReceiverAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }

            	    // InternalSTCoreParser.g:2189:4: ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) )
            	    int alt38=2;
            	    int LA38_0 = input.LA(1);

            	    if ( (LA38_0==FullStop) ) {
            	        alt38=1;
            	    }
            	    else if ( (LA38_0==LeftSquareBracket) ) {
            	        alt38=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 38, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt38) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2190:5: ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) )
            	            {
            	            // InternalSTCoreParser.g:2190:5: ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) )
            	            // InternalSTCoreParser.g:2191:6: ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) )
            	            {
            	            // InternalSTCoreParser.g:2191:6: ( (lv_structAccess_2_0= FullStop ) )
            	            // InternalSTCoreParser.g:2192:7: (lv_structAccess_2_0= FullStop )
            	            {
            	            // InternalSTCoreParser.g:2192:7: (lv_structAccess_2_0= FullStop )
            	            // InternalSTCoreParser.g:2193:8: lv_structAccess_2_0= FullStop
            	            {
            	            lv_structAccess_2_0=(Token)match(input,FullStop,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_structAccess_2_0, grammarAccess.getSTSelectionExpressionAccess().getStructAccessFullStopKeyword_1_1_0_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTSelectionExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "structAccess", lv_structAccess_2_0 != null, ".");
            	              							
            	            }

            	            }


            	            }

            	            // InternalSTCoreParser.g:2205:6: ( (otherlv_3= RULE_ID ) )
            	            // InternalSTCoreParser.g:2206:7: (otherlv_3= RULE_ID )
            	            {
            	            // InternalSTCoreParser.g:2206:7: (otherlv_3= RULE_ID )
            	            // InternalSTCoreParser.g:2207:8: otherlv_3= RULE_ID
            	            {
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTSelectionExpressionRule());
            	              								}
            	              							
            	            }
            	            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_47); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(otherlv_3, grammarAccess.getSTSelectionExpressionAccess().getMemberVarDeclarationCrossReference_1_1_0_1_0());
            	              							
            	            }

            	            }


            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:2220:5: ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket )
            	            {
            	            // InternalSTCoreParser.g:2220:5: ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket )
            	            // InternalSTCoreParser.g:2221:6: ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket
            	            {
            	            // InternalSTCoreParser.g:2221:6: ( (lv_arrayAccess_4_0= LeftSquareBracket ) )
            	            // InternalSTCoreParser.g:2222:7: (lv_arrayAccess_4_0= LeftSquareBracket )
            	            {
            	            // InternalSTCoreParser.g:2222:7: (lv_arrayAccess_4_0= LeftSquareBracket )
            	            // InternalSTCoreParser.g:2223:8: lv_arrayAccess_4_0= LeftSquareBracket
            	            {
            	            lv_arrayAccess_4_0=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_arrayAccess_4_0, grammarAccess.getSTSelectionExpressionAccess().getArrayAccessLeftSquareBracketKeyword_1_1_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTSelectionExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "arrayAccess", lv_arrayAccess_4_0 != null, "[");
            	              							
            	            }

            	            }


            	            }

            	            // InternalSTCoreParser.g:2235:6: ( (lv_index_5_0= ruleSTExpression ) )
            	            // InternalSTCoreParser.g:2236:7: (lv_index_5_0= ruleSTExpression )
            	            {
            	            // InternalSTCoreParser.g:2236:7: (lv_index_5_0= ruleSTExpression )
            	            // InternalSTCoreParser.g:2237:8: lv_index_5_0= ruleSTExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_1_1_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_10);
            	            lv_index_5_0=ruleSTExpression();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElementForParent(grammarAccess.getSTSelectionExpressionRule());
            	              								}
            	              								add(
            	              									current,
            	              									"index",
            	              									lv_index_5_0,
            	              									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
            	              								afterParserOrEnumRuleCall();
            	              							
            	            }

            	            }


            	            }

            	            // InternalSTCoreParser.g:2254:6: (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )*
            	            loop37:
            	            do {
            	                int alt37=2;
            	                int LA37_0 = input.LA(1);

            	                if ( (LA37_0==Comma) ) {
            	                    alt37=1;
            	                }


            	                switch (alt37) {
            	            	case 1 :
            	            	    // InternalSTCoreParser.g:2255:7: otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) )
            	            	    {
            	            	    otherlv_6=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	            	    if ( state.backtracking==0 ) {

            	            	      							newLeafNode(otherlv_6, grammarAccess.getSTSelectionExpressionAccess().getCommaKeyword_1_1_1_2_0());
            	            	      						
            	            	    }
            	            	    // InternalSTCoreParser.g:2259:7: ( (lv_index_7_0= ruleSTExpression ) )
            	            	    // InternalSTCoreParser.g:2260:8: (lv_index_7_0= ruleSTExpression )
            	            	    {
            	            	    // InternalSTCoreParser.g:2260:8: (lv_index_7_0= ruleSTExpression )
            	            	    // InternalSTCoreParser.g:2261:9: lv_index_7_0= ruleSTExpression
            	            	    {
            	            	    if ( state.backtracking==0 ) {

            	            	      									newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_1_2_1_0());
            	            	      								
            	            	    }
            	            	    pushFollow(FOLLOW_10);
            	            	    lv_index_7_0=ruleSTExpression();

            	            	    state._fsp--;
            	            	    if (state.failed) return current;
            	            	    if ( state.backtracking==0 ) {

            	            	      									if (current==null) {
            	            	      										current = createModelElementForParent(grammarAccess.getSTSelectionExpressionRule());
            	            	      									}
            	            	      									add(
            	            	      										current,
            	            	      										"index",
            	            	      										lv_index_7_0,
            	            	      										"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
            	            	      									afterParserOrEnumRuleCall();
            	            	      								
            	            	    }

            	            	    }


            	            	    }


            	            	    }
            	            	    break;

            	            	default :
            	            	    break loop37;
            	                }
            	            } while (true);

            	            otherlv_8=(Token)match(input,RightSquareBracket,FOLLOW_47); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_8, grammarAccess.getSTSelectionExpressionAccess().getRightSquareBracketKeyword_1_1_1_3());
            	              					
            	            }

            	            }


            	            }
            	            break;

            	    }

            	    // InternalSTCoreParser.g:2285:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )?
            	    int alt41=2;
            	    alt41 = dfa41.predict(input);
            	    switch (alt41) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2286:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis
            	            {
            	            // InternalSTCoreParser.g:2286:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) )
            	            // InternalSTCoreParser.g:2287:6: ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis )
            	            {
            	            // InternalSTCoreParser.g:2291:6: (lv_poeInvocation_9_0= LeftParenthesis )
            	            // InternalSTCoreParser.g:2292:7: lv_poeInvocation_9_0= LeftParenthesis
            	            {
            	            lv_poeInvocation_9_0=(Token)match(input,LeftParenthesis,FOLLOW_48); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							newLeafNode(lv_poeInvocation_9_0, grammarAccess.getSTSelectionExpressionAccess().getPoeInvocationLeftParenthesisKeyword_1_2_0_0());
            	              						
            	            }
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElement(grammarAccess.getSTSelectionExpressionRule());
            	              							}
            	              							setWithLastConsumed(current, "poeInvocation", lv_poeInvocation_9_0 != null, "(");
            	              						
            	            }

            	            }


            	            }

            	            // InternalSTCoreParser.g:2304:5: ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )?
            	            int alt40=2;
            	            int LA40_0 = input.LA(1);

            	            if ( ((LA40_0>=LDATE_AND_TIME && LA40_0<=TIME_OF_DAY)||LA40_0==WSTRING||(LA40_0>=STRING && LA40_0<=DWORD)||(LA40_0>=LDATE && LA40_0<=LWORD)||(LA40_0>=UDINT && LA40_0<=WCHAR)||(LA40_0>=BOOL && LA40_0<=DINT)||(LA40_0>=FALSE && LA40_0<=UINT)||LA40_0==WORD||(LA40_0>=INT && LA40_0<=LDT)||(LA40_0>=TOD && LA40_0<=TRUE)||LA40_0==DT||(LA40_0>=LD && LA40_0<=LT)||LA40_0==NOT||LA40_0==D||LA40_0==T||LA40_0==LeftParenthesis||LA40_0==PlusSign||LA40_0==HyphenMinus||(LA40_0>=RULE_NON_DECIMAL && LA40_0<=RULE_INT)||(LA40_0>=RULE_ID && LA40_0<=RULE_STRING)) ) {
            	                alt40=1;
            	            }
            	            switch (alt40) {
            	                case 1 :
            	                    // InternalSTCoreParser.g:2305:6: ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )*
            	                    {
            	                    // InternalSTCoreParser.g:2305:6: ( (lv_parameters_10_0= ruleSTExpression ) )
            	                    // InternalSTCoreParser.g:2306:7: (lv_parameters_10_0= ruleSTExpression )
            	                    {
            	                    // InternalSTCoreParser.g:2306:7: (lv_parameters_10_0= ruleSTExpression )
            	                    // InternalSTCoreParser.g:2307:8: lv_parameters_10_0= ruleSTExpression
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      								newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getParametersSTExpressionParserRuleCall_1_2_1_0_0());
            	                      							
            	                    }
            	                    pushFollow(FOLLOW_49);
            	                    lv_parameters_10_0=ruleSTExpression();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      								if (current==null) {
            	                      									current = createModelElementForParent(grammarAccess.getSTSelectionExpressionRule());
            	                      								}
            	                      								add(
            	                      									current,
            	                      									"parameters",
            	                      									lv_parameters_10_0,
            	                      									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
            	                      								afterParserOrEnumRuleCall();
            	                      							
            	                    }

            	                    }


            	                    }

            	                    // InternalSTCoreParser.g:2324:6: (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )*
            	                    loop39:
            	                    do {
            	                        int alt39=2;
            	                        int LA39_0 = input.LA(1);

            	                        if ( (LA39_0==Comma) ) {
            	                            alt39=1;
            	                        }


            	                        switch (alt39) {
            	                    	case 1 :
            	                    	    // InternalSTCoreParser.g:2325:7: otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) )
            	                    	    {
            	                    	    otherlv_11=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	                    	    if ( state.backtracking==0 ) {

            	                    	      							newLeafNode(otherlv_11, grammarAccess.getSTSelectionExpressionAccess().getCommaKeyword_1_2_1_1_0());
            	                    	      						
            	                    	    }
            	                    	    // InternalSTCoreParser.g:2329:7: ( (lv_parameters_12_0= ruleSTExpression ) )
            	                    	    // InternalSTCoreParser.g:2330:8: (lv_parameters_12_0= ruleSTExpression )
            	                    	    {
            	                    	    // InternalSTCoreParser.g:2330:8: (lv_parameters_12_0= ruleSTExpression )
            	                    	    // InternalSTCoreParser.g:2331:9: lv_parameters_12_0= ruleSTExpression
            	                    	    {
            	                    	    if ( state.backtracking==0 ) {

            	                    	      									newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getParametersSTExpressionParserRuleCall_1_2_1_1_1_0());
            	                    	      								
            	                    	    }
            	                    	    pushFollow(FOLLOW_49);
            	                    	    lv_parameters_12_0=ruleSTExpression();

            	                    	    state._fsp--;
            	                    	    if (state.failed) return current;
            	                    	    if ( state.backtracking==0 ) {

            	                    	      									if (current==null) {
            	                    	      										current = createModelElementForParent(grammarAccess.getSTSelectionExpressionRule());
            	                    	      									}
            	                    	      									add(
            	                    	      										current,
            	                    	      										"parameters",
            	                    	      										lv_parameters_12_0,
            	                    	      										"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
            	                    	      									afterParserOrEnumRuleCall();
            	                    	      								
            	                    	    }

            	                    	    }


            	                    	    }


            	                    	    }
            	                    	    break;

            	                    	default :
            	                    	    break loop39;
            	                        }
            	                    } while (true);


            	                    }
            	                    break;

            	            }

            	            otherlv_13=(Token)match(input,RightParenthesis,FOLLOW_50); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(otherlv_13, grammarAccess.getSTSelectionExpressionAccess().getRightParenthesisKeyword_1_2_2());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalSTCoreParser.g:2355:4: ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )?
            	    int alt42=2;
            	    int LA42_0 = input.LA(1);

            	    if ( (LA42_0==L) && (synpred3_InternalSTCoreParser())) {
            	        alt42=1;
            	    }
            	    else if ( (LA42_0==D_1) && (synpred3_InternalSTCoreParser())) {
            	        alt42=1;
            	    }
            	    else if ( (LA42_0==W) && (synpred3_InternalSTCoreParser())) {
            	        alt42=1;
            	    }
            	    else if ( (LA42_0==B) && (synpred3_InternalSTCoreParser())) {
            	        alt42=1;
            	    }
            	    else if ( (LA42_0==X) && (synpred3_InternalSTCoreParser())) {
            	        alt42=1;
            	    }
            	    else if ( (LA42_0==FullStop) ) {
            	        int LA42_6 = input.LA(2);

            	        if ( (LA42_6==RULE_INT) && (synpred3_InternalSTCoreParser())) {
            	            alt42=1;
            	        }
            	    }
            	    switch (alt42) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2356:5: ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess )
            	            {
            	            // InternalSTCoreParser.g:2360:5: (lv_bitaccessor_14_0= ruleMultibitPartialAccess )
            	            // InternalSTCoreParser.g:2361:6: lv_bitaccessor_14_0= ruleMultibitPartialAccess
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getBitaccessorMultibitPartialAccessParserRuleCall_1_3_0());
            	              					
            	            }
            	            pushFollow(FOLLOW_46);
            	            lv_bitaccessor_14_0=ruleMultibitPartialAccess();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTSelectionExpressionRule());
            	              						}
            	              						set(
            	              							current,
            	              							"bitaccessor",
            	              							lv_bitaccessor_14_0,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.MultibitPartialAccess");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop43;
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
    // $ANTLR end "ruleSTSelectionExpression"


    // $ANTLR start "entryRuleMultibitPartialAccess"
    // InternalSTCoreParser.g:2383:1: entryRuleMultibitPartialAccess returns [EObject current=null] : iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF ;
    public final EObject entryRuleMultibitPartialAccess() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultibitPartialAccess = null;


        try {
            // InternalSTCoreParser.g:2383:62: (iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF )
            // InternalSTCoreParser.g:2384:2: iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMultibitPartialAccessRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMultibitPartialAccess=ruleMultibitPartialAccess();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMultibitPartialAccess; 
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
    // $ANTLR end "entryRuleMultibitPartialAccess"


    // $ANTLR start "ruleMultibitPartialAccess"
    // InternalSTCoreParser.g:2390:1: ruleMultibitPartialAccess returns [EObject current=null] : ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) ) ;
    public final EObject ruleMultibitPartialAccess() throws RecognitionException {
        EObject current = null;

        Token lv_index_1_0=null;
        Enumerator lv_accessSpecifier_0_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2396:2: ( ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) ) )
            // InternalSTCoreParser.g:2397:2: ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) )
            {
            // InternalSTCoreParser.g:2397:2: ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) )
            // InternalSTCoreParser.g:2398:3: ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) )
            {
            // InternalSTCoreParser.g:2398:3: ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) )
            // InternalSTCoreParser.g:2399:4: (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier )
            {
            // InternalSTCoreParser.g:2399:4: (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier )
            // InternalSTCoreParser.g:2400:5: lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMultibitPartialAccessAccess().getAccessSpecifierMultiBitAccessSpecifierEnumRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_51);
            lv_accessSpecifier_0_0=ruleMultiBitAccessSpecifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getMultibitPartialAccessRule());
              					}
              					set(
              						current,
              						"accessSpecifier",
              						lv_accessSpecifier_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.MultiBitAccessSpecifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:2417:3: ( (lv_index_1_0= RULE_INT ) )
            // InternalSTCoreParser.g:2418:4: (lv_index_1_0= RULE_INT )
            {
            // InternalSTCoreParser.g:2418:4: (lv_index_1_0= RULE_INT )
            // InternalSTCoreParser.g:2419:5: lv_index_1_0= RULE_INT
            {
            lv_index_1_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_index_1_0, grammarAccess.getMultibitPartialAccessAccess().getIndexINTTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getMultibitPartialAccessRule());
              					}
              					setWithLastConsumed(
              						current,
              						"index",
              						lv_index_1_0,
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
    // $ANTLR end "ruleMultibitPartialAccess"


    // $ANTLR start "entryRuleSTAtomicExpression"
    // InternalSTCoreParser.g:2439:1: entryRuleSTAtomicExpression returns [EObject current=null] : iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF ;
    public final EObject entryRuleSTAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAtomicExpression = null;


        try {
            // InternalSTCoreParser.g:2439:59: (iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF )
            // InternalSTCoreParser.g:2440:2: iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAtomicExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAtomicExpression=ruleSTAtomicExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAtomicExpression; 
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
    // $ANTLR end "entryRuleSTAtomicExpression"


    // $ANTLR start "ruleSTAtomicExpression"
    // InternalSTCoreParser.g:2446:1: ruleSTAtomicExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) ) ;
    public final EObject ruleSTAtomicExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_poeInvocation_8_0=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        EObject this_STExpression_1 = null;

        EObject lv_bitaccessor_7_0 = null;

        EObject lv_parameters_9_0 = null;

        EObject lv_parameters_11_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2452:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) ) )
            // InternalSTCoreParser.g:2453:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) )
            {
            // InternalSTCoreParser.g:2453:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==LeftParenthesis) ) {
                alt49=1;
            }
            else if ( (LA49_0==RULE_ID) ) {
                alt49=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // InternalSTCoreParser.g:2454:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTCoreParser.g:2454:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTCoreParser.g:2455:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTAtomicExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTAtomicExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
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

                      				newLeafNode(otherlv_2, grammarAccess.getSTAtomicExpressionAccess().getRightParenthesisKeyword_0_2());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2473:3: ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? )
                    {
                    // InternalSTCoreParser.g:2473:3: ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? )
                    // InternalSTCoreParser.g:2474:4: () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?
                    {
                    // InternalSTCoreParser.g:2474:4: ()
                    // InternalSTCoreParser.g:2475:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTAtomicExpressionAccess().getSTSymbolAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2481:4: ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==RULE_ID) ) {
                        int LA44_1 = input.LA(2);

                        if ( (LA44_1==NumberSign) ) {
                            alt44=1;
                        }
                    }
                    switch (alt44) {
                        case 1 :
                            // InternalSTCoreParser.g:2482:5: ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign
                            {
                            // InternalSTCoreParser.g:2482:5: ( (otherlv_4= RULE_ID ) )
                            // InternalSTCoreParser.g:2483:6: (otherlv_4= RULE_ID )
                            {
                            // InternalSTCoreParser.g:2483:6: (otherlv_4= RULE_ID )
                            // InternalSTCoreParser.g:2484:7: otherlv_4= RULE_ID
                            {
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getSTAtomicExpressionRule());
                              							}
                              						
                            }
                            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_52); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(otherlv_4, grammarAccess.getSTAtomicExpressionAccess().getTypeDataTypeCrossReference_1_1_0_0());
                              						
                            }

                            }


                            }

                            otherlv_5=(Token)match(input,NumberSign,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_5, grammarAccess.getSTAtomicExpressionAccess().getNumberSignKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalSTCoreParser.g:2500:4: ( (otherlv_6= RULE_ID ) )
                    // InternalSTCoreParser.g:2501:5: (otherlv_6= RULE_ID )
                    {
                    // InternalSTCoreParser.g:2501:5: (otherlv_6= RULE_ID )
                    // InternalSTCoreParser.g:2502:6: otherlv_6= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTAtomicExpressionRule());
                      						}
                      					
                    }
                    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_6, grammarAccess.getSTAtomicExpressionAccess().getSymbolVarDeclarationCrossReference_1_2_0());
                      					
                    }

                    }


                    }

                    // InternalSTCoreParser.g:2513:4: ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( ((LA45_0>=B && LA45_0<=X)) ) {
                        alt45=1;
                    }
                    else if ( (LA45_0==FullStop) ) {
                        int LA45_2 = input.LA(2);

                        if ( (LA45_2==RULE_INT) ) {
                            alt45=1;
                        }
                    }
                    switch (alt45) {
                        case 1 :
                            // InternalSTCoreParser.g:2514:5: (lv_bitaccessor_7_0= ruleMultibitPartialAccess )
                            {
                            // InternalSTCoreParser.g:2514:5: (lv_bitaccessor_7_0= ruleMultibitPartialAccess )
                            // InternalSTCoreParser.g:2515:6: lv_bitaccessor_7_0= ruleMultibitPartialAccess
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getSTAtomicExpressionAccess().getBitaccessorMultibitPartialAccessParserRuleCall_1_3_0());
                              					
                            }
                            pushFollow(FOLLOW_18);
                            lv_bitaccessor_7_0=ruleMultibitPartialAccess();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getSTAtomicExpressionRule());
                              						}
                              						set(
                              							current,
                              							"bitaccessor",
                              							lv_bitaccessor_7_0,
                              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.MultibitPartialAccess");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalSTCoreParser.g:2532:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?
                    int alt48=2;
                    alt48 = dfa48.predict(input);
                    switch (alt48) {
                        case 1 :
                            // InternalSTCoreParser.g:2533:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis
                            {
                            // InternalSTCoreParser.g:2533:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) )
                            // InternalSTCoreParser.g:2534:6: ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis )
                            {
                            // InternalSTCoreParser.g:2538:6: (lv_poeInvocation_8_0= LeftParenthesis )
                            // InternalSTCoreParser.g:2539:7: lv_poeInvocation_8_0= LeftParenthesis
                            {
                            lv_poeInvocation_8_0=(Token)match(input,LeftParenthesis,FOLLOW_48); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_poeInvocation_8_0, grammarAccess.getSTAtomicExpressionAccess().getPoeInvocationLeftParenthesisKeyword_1_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getSTAtomicExpressionRule());
                              							}
                              							setWithLastConsumed(current, "poeInvocation", lv_poeInvocation_8_0 != null, "(");
                              						
                            }

                            }


                            }

                            // InternalSTCoreParser.g:2551:5: ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )?
                            int alt47=2;
                            int LA47_0 = input.LA(1);

                            if ( ((LA47_0>=LDATE_AND_TIME && LA47_0<=TIME_OF_DAY)||LA47_0==WSTRING||(LA47_0>=STRING && LA47_0<=DWORD)||(LA47_0>=LDATE && LA47_0<=LWORD)||(LA47_0>=UDINT && LA47_0<=WCHAR)||(LA47_0>=BOOL && LA47_0<=DINT)||(LA47_0>=FALSE && LA47_0<=UINT)||LA47_0==WORD||(LA47_0>=INT && LA47_0<=LDT)||(LA47_0>=TOD && LA47_0<=TRUE)||LA47_0==DT||(LA47_0>=LD && LA47_0<=LT)||LA47_0==NOT||LA47_0==D||LA47_0==T||LA47_0==LeftParenthesis||LA47_0==PlusSign||LA47_0==HyphenMinus||(LA47_0>=RULE_NON_DECIMAL && LA47_0<=RULE_INT)||(LA47_0>=RULE_ID && LA47_0<=RULE_STRING)) ) {
                                alt47=1;
                            }
                            switch (alt47) {
                                case 1 :
                                    // InternalSTCoreParser.g:2552:6: ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )*
                                    {
                                    // InternalSTCoreParser.g:2552:6: ( (lv_parameters_9_0= ruleSTExpression ) )
                                    // InternalSTCoreParser.g:2553:7: (lv_parameters_9_0= ruleSTExpression )
                                    {
                                    // InternalSTCoreParser.g:2553:7: (lv_parameters_9_0= ruleSTExpression )
                                    // InternalSTCoreParser.g:2554:8: lv_parameters_9_0= ruleSTExpression
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getSTAtomicExpressionAccess().getParametersSTExpressionParserRuleCall_1_4_1_0_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_49);
                                    lv_parameters_9_0=ruleSTExpression();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElementForParent(grammarAccess.getSTAtomicExpressionRule());
                                      								}
                                      								add(
                                      									current,
                                      									"parameters",
                                      									lv_parameters_9_0,
                                      									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                                      								afterParserOrEnumRuleCall();
                                      							
                                    }

                                    }


                                    }

                                    // InternalSTCoreParser.g:2571:6: (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )*
                                    loop46:
                                    do {
                                        int alt46=2;
                                        int LA46_0 = input.LA(1);

                                        if ( (LA46_0==Comma) ) {
                                            alt46=1;
                                        }


                                        switch (alt46) {
                                    	case 1 :
                                    	    // InternalSTCoreParser.g:2572:7: otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) )
                                    	    {
                                    	    otherlv_10=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
                                    	    if ( state.backtracking==0 ) {

                                    	      							newLeafNode(otherlv_10, grammarAccess.getSTAtomicExpressionAccess().getCommaKeyword_1_4_1_1_0());
                                    	      						
                                    	    }
                                    	    // InternalSTCoreParser.g:2576:7: ( (lv_parameters_11_0= ruleSTExpression ) )
                                    	    // InternalSTCoreParser.g:2577:8: (lv_parameters_11_0= ruleSTExpression )
                                    	    {
                                    	    // InternalSTCoreParser.g:2577:8: (lv_parameters_11_0= ruleSTExpression )
                                    	    // InternalSTCoreParser.g:2578:9: lv_parameters_11_0= ruleSTExpression
                                    	    {
                                    	    if ( state.backtracking==0 ) {

                                    	      									newCompositeNode(grammarAccess.getSTAtomicExpressionAccess().getParametersSTExpressionParserRuleCall_1_4_1_1_1_0());
                                    	      								
                                    	    }
                                    	    pushFollow(FOLLOW_49);
                                    	    lv_parameters_11_0=ruleSTExpression();

                                    	    state._fsp--;
                                    	    if (state.failed) return current;
                                    	    if ( state.backtracking==0 ) {

                                    	      									if (current==null) {
                                    	      										current = createModelElementForParent(grammarAccess.getSTAtomicExpressionRule());
                                    	      									}
                                    	      									add(
                                    	      										current,
                                    	      										"parameters",
                                    	      										lv_parameters_11_0,
                                    	      										"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                                    	      									afterParserOrEnumRuleCall();
                                    	      								
                                    	    }

                                    	    }


                                    	    }


                                    	    }
                                    	    break;

                                    	default :
                                    	    break loop46;
                                        }
                                    } while (true);


                                    }
                                    break;

                            }

                            otherlv_12=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_12, grammarAccess.getSTAtomicExpressionAccess().getRightParenthesisKeyword_1_4_2());
                              				
                            }

                            }
                            break;

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
    // $ANTLR end "ruleSTAtomicExpression"


    // $ANTLR start "entryRuleSTLiteralExpressions"
    // InternalSTCoreParser.g:2607:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTCoreParser.g:2607:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTCoreParser.g:2608:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTCoreParser.g:2614:1: ruleSTLiteralExpressions returns [EObject current=null] : ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) ) ;
    public final EObject ruleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_boolLiteral_1_0 = null;

        EObject lv_numericLiteral_3_0 = null;

        EObject lv_dateLiteral_5_0 = null;

        EObject lv_timeLiteral_7_0 = null;

        EObject lv_timeOfDayLiteral_9_0 = null;

        EObject lv_timeLiteral_11_0 = null;

        EObject lv_stringLiteral_13_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2620:2: ( ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) ) )
            // InternalSTCoreParser.g:2621:2: ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) )
            {
            // InternalSTCoreParser.g:2621:2: ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) )
            int alt50=7;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                int LA50_1 = input.LA(2);

                if ( (LA50_1==FALSE||LA50_1==TRUE) ) {
                    alt50=1;
                }
                else if ( (LA50_1==PlusSign||LA50_1==HyphenMinus||(LA50_1>=RULE_NON_DECIMAL && LA50_1<=RULE_INT)) ) {
                    alt50=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 50, 1, input);

                    throw nvae;
                }
                }
                break;
            case FALSE:
            case TRUE:
                {
                alt50=1;
                }
                break;
            case DWORD:
            case LREAL:
            case LWORD:
            case UDINT:
            case ULINT:
            case USINT:
            case BYTE:
            case DINT:
            case LINT:
            case REAL:
            case SINT:
            case UINT:
            case WORD:
            case INT:
            case PlusSign:
            case HyphenMinus:
            case RULE_NON_DECIMAL:
            case RULE_INT:
                {
                alt50=2;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt50=3;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt50=4;
                }
                break;
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt50=5;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt50=6;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt50=7;
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
                    // InternalSTCoreParser.g:2622:3: ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2622:3: ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) )
                    // InternalSTCoreParser.g:2623:4: () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2623:4: ()
                    // InternalSTCoreParser.g:2624:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTBoolLiteralAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2630:4: ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) )
                    // InternalSTCoreParser.g:2631:5: (lv_boolLiteral_1_0= ruleBOOL_LITERAL )
                    {
                    // InternalSTCoreParser.g:2631:5: (lv_boolLiteral_1_0= ruleBOOL_LITERAL )
                    // InternalSTCoreParser.g:2632:6: lv_boolLiteral_1_0= ruleBOOL_LITERAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getBoolLiteralBOOL_LITERALParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_boolLiteral_1_0=ruleBOOL_LITERAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTLiteralExpressionsRule());
                      						}
                      						set(
                      							current,
                      							"boolLiteral",
                      							lv_boolLiteral_1_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.BOOL_LITERAL");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2651:3: ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2651:3: ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) )
                    // InternalSTCoreParser.g:2652:4: () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2652:4: ()
                    // InternalSTCoreParser.g:2653:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTNumericLiteralAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2659:4: ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) )
                    // InternalSTCoreParser.g:2660:5: (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL )
                    {
                    // InternalSTCoreParser.g:2660:5: (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL )
                    // InternalSTCoreParser.g:2661:6: lv_numericLiteral_3_0= ruleNUMERIC_LITERAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getNumericLiteralNUMERIC_LITERALParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_numericLiteral_3_0=ruleNUMERIC_LITERAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTLiteralExpressionsRule());
                      						}
                      						set(
                      							current,
                      							"numericLiteral",
                      							lv_numericLiteral_3_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.NUMERIC_LITERAL");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2680:3: ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2680:3: ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) )
                    // InternalSTCoreParser.g:2681:4: () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2681:4: ()
                    // InternalSTCoreParser.g:2682:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTDateLiteralAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2688:4: ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) )
                    // InternalSTCoreParser.g:2689:5: (lv_dateLiteral_5_0= ruleDATE_LITERAL )
                    {
                    // InternalSTCoreParser.g:2689:5: (lv_dateLiteral_5_0= ruleDATE_LITERAL )
                    // InternalSTCoreParser.g:2690:6: lv_dateLiteral_5_0= ruleDATE_LITERAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getDateLiteralDATE_LITERALParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_dateLiteral_5_0=ruleDATE_LITERAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTLiteralExpressionsRule());
                      						}
                      						set(
                      							current,
                      							"dateLiteral",
                      							lv_dateLiteral_5_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.DATE_LITERAL");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:2709:3: ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2709:3: ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) )
                    // InternalSTCoreParser.g:2710:4: () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2710:4: ()
                    // InternalSTCoreParser.g:2711:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTTimeLiteralAction_3_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2717:4: ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) )
                    // InternalSTCoreParser.g:2718:5: (lv_timeLiteral_7_0= ruleTIME_LITERAL )
                    {
                    // InternalSTCoreParser.g:2718:5: (lv_timeLiteral_7_0= ruleTIME_LITERAL )
                    // InternalSTCoreParser.g:2719:6: lv_timeLiteral_7_0= ruleTIME_LITERAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getTimeLiteralTIME_LITERALParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_timeLiteral_7_0=ruleTIME_LITERAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTLiteralExpressionsRule());
                      						}
                      						set(
                      							current,
                      							"timeLiteral",
                      							lv_timeLiteral_7_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_LITERAL");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:2738:3: ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2738:3: ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) )
                    // InternalSTCoreParser.g:2739:4: () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2739:4: ()
                    // InternalSTCoreParser.g:2740:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTTimeOfDayLiteralAction_4_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2746:4: ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) )
                    // InternalSTCoreParser.g:2747:5: (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL )
                    {
                    // InternalSTCoreParser.g:2747:5: (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL )
                    // InternalSTCoreParser.g:2748:6: lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getTimeOfDayLiteralTIME_OF_DAY_LITERALParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_timeOfDayLiteral_9_0=ruleTIME_OF_DAY_LITERAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTLiteralExpressionsRule());
                      						}
                      						set(
                      							current,
                      							"timeOfDayLiteral",
                      							lv_timeOfDayLiteral_9_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_OF_DAY_LITERAL");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalSTCoreParser.g:2767:3: ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2767:3: ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) )
                    // InternalSTCoreParser.g:2768:4: () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2768:4: ()
                    // InternalSTCoreParser.g:2769:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTDateAndTimeLiteralAction_5_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2775:4: ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) )
                    // InternalSTCoreParser.g:2776:5: (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL )
                    {
                    // InternalSTCoreParser.g:2776:5: (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL )
                    // InternalSTCoreParser.g:2777:6: lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getTimeLiteralDATE_AND_TIME_LITERALParserRuleCall_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_timeLiteral_11_0=ruleDATE_AND_TIME_LITERAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTLiteralExpressionsRule());
                      						}
                      						set(
                      							current,
                      							"timeLiteral",
                      							lv_timeLiteral_11_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.DATE_AND_TIME_LITERAL");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalSTCoreParser.g:2796:3: ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2796:3: ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) )
                    // InternalSTCoreParser.g:2797:4: () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2797:4: ()
                    // InternalSTCoreParser.g:2798:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTStringLiteralAction_6_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2804:4: ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) )
                    // InternalSTCoreParser.g:2805:5: (lv_stringLiteral_13_0= ruleSTRING_LITERAL )
                    {
                    // InternalSTCoreParser.g:2805:5: (lv_stringLiteral_13_0= ruleSTRING_LITERAL )
                    // InternalSTCoreParser.g:2806:6: lv_stringLiteral_13_0= ruleSTRING_LITERAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getStringLiteralSTRING_LITERALParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_stringLiteral_13_0=ruleSTRING_LITERAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTLiteralExpressionsRule());
                      						}
                      						set(
                      							current,
                      							"stringLiteral",
                      							lv_stringLiteral_13_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STRING_LITERAL");
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
    // $ANTLR end "ruleSTLiteralExpressions"


    // $ANTLR start "entryRuleBOOL_LITERAL"
    // InternalSTCoreParser.g:2828:1: entryRuleBOOL_LITERAL returns [String current=null] : iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF ;
    public final String entryRuleBOOL_LITERAL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOL_LITERAL = null;


        try {
            // InternalSTCoreParser.g:2828:52: (iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF )
            // InternalSTCoreParser.g:2829:2: iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBOOL_LITERALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBOOL_LITERAL=ruleBOOL_LITERAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBOOL_LITERAL.getText(); 
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
    // $ANTLR end "entryRuleBOOL_LITERAL"


    // $ANTLR start "ruleBOOL_LITERAL"
    // InternalSTCoreParser.g:2835:1: ruleBOOL_LITERAL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= BOOL )? (kw= TRUE | kw= FALSE ) ) ;
    public final AntlrDatatypeRuleToken ruleBOOL_LITERAL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:2841:2: ( ( (kw= BOOL )? (kw= TRUE | kw= FALSE ) ) )
            // InternalSTCoreParser.g:2842:2: ( (kw= BOOL )? (kw= TRUE | kw= FALSE ) )
            {
            // InternalSTCoreParser.g:2842:2: ( (kw= BOOL )? (kw= TRUE | kw= FALSE ) )
            // InternalSTCoreParser.g:2843:3: (kw= BOOL )? (kw= TRUE | kw= FALSE )
            {
            // InternalSTCoreParser.g:2843:3: (kw= BOOL )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==BOOL) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalSTCoreParser.g:2844:4: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getBOOL_LITERALAccess().getBOOLKeyword_0());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:2850:3: (kw= TRUE | kw= FALSE )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==TRUE) ) {
                alt52=1;
            }
            else if ( (LA52_0==FALSE) ) {
                alt52=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // InternalSTCoreParser.g:2851:4: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getBOOL_LITERALAccess().getTRUEKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2857:4: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getBOOL_LITERALAccess().getFALSEKeyword_1_1());
                      			
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
    // $ANTLR end "ruleBOOL_LITERAL"


    // $ANTLR start "entryRuleNUMERIC_LITERAL"
    // InternalSTCoreParser.g:2867:1: entryRuleNUMERIC_LITERAL returns [EObject current=null] : iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF ;
    public final EObject entryRuleNUMERIC_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNUMERIC_LITERAL = null;


        try {
            // InternalSTCoreParser.g:2867:56: (iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF )
            // InternalSTCoreParser.g:2868:2: iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNUMERIC_LITERALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNUMERIC_LITERAL=ruleNUMERIC_LITERAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNUMERIC_LITERAL; 
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
    // $ANTLR end "entryRuleNUMERIC_LITERAL"


    // $ANTLR start "ruleNUMERIC_LITERAL"
    // InternalSTCoreParser.g:2874:1: ruleNUMERIC_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL ) ) )? ( ( (lv_intValue_1_0= ruleINTEGER ) ) | ( (lv_realValue_2_0= ruleREAL ) ) | ( (lv_hexValue_3_0= RULE_NON_DECIMAL ) ) ) ) ;
    public final EObject ruleNUMERIC_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        Token lv_keyword_0_5=null;
        Token lv_keyword_0_6=null;
        Token lv_keyword_0_7=null;
        Token lv_keyword_0_8=null;
        Token lv_keyword_0_9=null;
        Token lv_keyword_0_10=null;
        Token lv_keyword_0_11=null;
        Token lv_keyword_0_12=null;
        Token lv_keyword_0_13=null;
        Token lv_keyword_0_14=null;
        Token lv_keyword_0_15=null;
        Token lv_hexValue_3_0=null;
        AntlrDatatypeRuleToken lv_intValue_1_0 = null;

        AntlrDatatypeRuleToken lv_realValue_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2880:2: ( ( ( ( (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL ) ) )? ( ( (lv_intValue_1_0= ruleINTEGER ) ) | ( (lv_realValue_2_0= ruleREAL ) ) | ( (lv_hexValue_3_0= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTCoreParser.g:2881:2: ( ( ( (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL ) ) )? ( ( (lv_intValue_1_0= ruleINTEGER ) ) | ( (lv_realValue_2_0= ruleREAL ) ) | ( (lv_hexValue_3_0= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTCoreParser.g:2881:2: ( ( ( (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL ) ) )? ( ( (lv_intValue_1_0= ruleINTEGER ) ) | ( (lv_realValue_2_0= ruleREAL ) ) | ( (lv_hexValue_3_0= RULE_NON_DECIMAL ) ) ) )
            // InternalSTCoreParser.g:2882:3: ( ( (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL ) ) )? ( ( (lv_intValue_1_0= ruleINTEGER ) ) | ( (lv_realValue_2_0= ruleREAL ) ) | ( (lv_hexValue_3_0= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTCoreParser.g:2882:3: ( ( (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL ) ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==DWORD||LA54_0==LREAL||LA54_0==LWORD||(LA54_0>=UDINT && LA54_0<=USINT)||(LA54_0>=BOOL && LA54_0<=BYTE)||LA54_0==DINT||LA54_0==LINT||(LA54_0>=REAL && LA54_0<=SINT)||LA54_0==UINT||LA54_0==WORD||LA54_0==INT) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalSTCoreParser.g:2883:4: ( (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL ) )
                    {
                    // InternalSTCoreParser.g:2883:4: ( (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL ) )
                    // InternalSTCoreParser.g:2884:5: (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL )
                    {
                    // InternalSTCoreParser.g:2884:5: (lv_keyword_0_1= BOOL | lv_keyword_0_2= BYTE | lv_keyword_0_3= WORD | lv_keyword_0_4= DWORD | lv_keyword_0_5= LWORD | lv_keyword_0_6= SINT | lv_keyword_0_7= INT | lv_keyword_0_8= DINT | lv_keyword_0_9= LINT | lv_keyword_0_10= USINT | lv_keyword_0_11= UINT | lv_keyword_0_12= UDINT | lv_keyword_0_13= ULINT | lv_keyword_0_14= REAL | lv_keyword_0_15= LREAL )
                    int alt53=15;
                    switch ( input.LA(1) ) {
                    case BOOL:
                        {
                        alt53=1;
                        }
                        break;
                    case BYTE:
                        {
                        alt53=2;
                        }
                        break;
                    case WORD:
                        {
                        alt53=3;
                        }
                        break;
                    case DWORD:
                        {
                        alt53=4;
                        }
                        break;
                    case LWORD:
                        {
                        alt53=5;
                        }
                        break;
                    case SINT:
                        {
                        alt53=6;
                        }
                        break;
                    case INT:
                        {
                        alt53=7;
                        }
                        break;
                    case DINT:
                        {
                        alt53=8;
                        }
                        break;
                    case LINT:
                        {
                        alt53=9;
                        }
                        break;
                    case USINT:
                        {
                        alt53=10;
                        }
                        break;
                    case UINT:
                        {
                        alt53=11;
                        }
                        break;
                    case UDINT:
                        {
                        alt53=12;
                        }
                        break;
                    case ULINT:
                        {
                        alt53=13;
                        }
                        break;
                    case REAL:
                        {
                        alt53=14;
                        }
                        break;
                    case LREAL:
                        {
                        alt53=15;
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
                            // InternalSTCoreParser.g:2885:6: lv_keyword_0_1= BOOL
                            {
                            lv_keyword_0_1=(Token)match(input,BOOL,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_1, grammarAccess.getNUMERIC_LITERALAccess().getKeywordBOOLKeyword_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:2896:6: lv_keyword_0_2= BYTE
                            {
                            lv_keyword_0_2=(Token)match(input,BYTE,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_2, grammarAccess.getNUMERIC_LITERALAccess().getKeywordBYTEKeyword_0_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_2, null);
                              					
                            }

                            }
                            break;
                        case 3 :
                            // InternalSTCoreParser.g:2907:6: lv_keyword_0_3= WORD
                            {
                            lv_keyword_0_3=(Token)match(input,WORD,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_3, grammarAccess.getNUMERIC_LITERALAccess().getKeywordWORDKeyword_0_0_2());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_3, null);
                              					
                            }

                            }
                            break;
                        case 4 :
                            // InternalSTCoreParser.g:2918:6: lv_keyword_0_4= DWORD
                            {
                            lv_keyword_0_4=(Token)match(input,DWORD,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_4, grammarAccess.getNUMERIC_LITERALAccess().getKeywordDWORDKeyword_0_0_3());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_4, null);
                              					
                            }

                            }
                            break;
                        case 5 :
                            // InternalSTCoreParser.g:2929:6: lv_keyword_0_5= LWORD
                            {
                            lv_keyword_0_5=(Token)match(input,LWORD,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_5, grammarAccess.getNUMERIC_LITERALAccess().getKeywordLWORDKeyword_0_0_4());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_5, null);
                              					
                            }

                            }
                            break;
                        case 6 :
                            // InternalSTCoreParser.g:2940:6: lv_keyword_0_6= SINT
                            {
                            lv_keyword_0_6=(Token)match(input,SINT,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_6, grammarAccess.getNUMERIC_LITERALAccess().getKeywordSINTKeyword_0_0_5());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_6, null);
                              					
                            }

                            }
                            break;
                        case 7 :
                            // InternalSTCoreParser.g:2951:6: lv_keyword_0_7= INT
                            {
                            lv_keyword_0_7=(Token)match(input,INT,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_7, grammarAccess.getNUMERIC_LITERALAccess().getKeywordINTKeyword_0_0_6());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_7, null);
                              					
                            }

                            }
                            break;
                        case 8 :
                            // InternalSTCoreParser.g:2962:6: lv_keyword_0_8= DINT
                            {
                            lv_keyword_0_8=(Token)match(input,DINT,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_8, grammarAccess.getNUMERIC_LITERALAccess().getKeywordDINTKeyword_0_0_7());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_8, null);
                              					
                            }

                            }
                            break;
                        case 9 :
                            // InternalSTCoreParser.g:2973:6: lv_keyword_0_9= LINT
                            {
                            lv_keyword_0_9=(Token)match(input,LINT,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_9, grammarAccess.getNUMERIC_LITERALAccess().getKeywordLINTKeyword_0_0_8());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_9, null);
                              					
                            }

                            }
                            break;
                        case 10 :
                            // InternalSTCoreParser.g:2984:6: lv_keyword_0_10= USINT
                            {
                            lv_keyword_0_10=(Token)match(input,USINT,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_10, grammarAccess.getNUMERIC_LITERALAccess().getKeywordUSINTKeyword_0_0_9());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_10, null);
                              					
                            }

                            }
                            break;
                        case 11 :
                            // InternalSTCoreParser.g:2995:6: lv_keyword_0_11= UINT
                            {
                            lv_keyword_0_11=(Token)match(input,UINT,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_11, grammarAccess.getNUMERIC_LITERALAccess().getKeywordUINTKeyword_0_0_10());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_11, null);
                              					
                            }

                            }
                            break;
                        case 12 :
                            // InternalSTCoreParser.g:3006:6: lv_keyword_0_12= UDINT
                            {
                            lv_keyword_0_12=(Token)match(input,UDINT,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_12, grammarAccess.getNUMERIC_LITERALAccess().getKeywordUDINTKeyword_0_0_11());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_12, null);
                              					
                            }

                            }
                            break;
                        case 13 :
                            // InternalSTCoreParser.g:3017:6: lv_keyword_0_13= ULINT
                            {
                            lv_keyword_0_13=(Token)match(input,ULINT,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_13, grammarAccess.getNUMERIC_LITERALAccess().getKeywordULINTKeyword_0_0_12());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_13, null);
                              					
                            }

                            }
                            break;
                        case 14 :
                            // InternalSTCoreParser.g:3028:6: lv_keyword_0_14= REAL
                            {
                            lv_keyword_0_14=(Token)match(input,REAL,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_14, grammarAccess.getNUMERIC_LITERALAccess().getKeywordREALKeyword_0_0_13());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_14, null);
                              					
                            }

                            }
                            break;
                        case 15 :
                            // InternalSTCoreParser.g:3039:6: lv_keyword_0_15= LREAL
                            {
                            lv_keyword_0_15=(Token)match(input,LREAL,FOLLOW_55); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_0_15, grammarAccess.getNUMERIC_LITERALAccess().getKeywordLREALKeyword_0_0_14());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_0_15, null);
                              					
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }

            // InternalSTCoreParser.g:3052:3: ( ( (lv_intValue_1_0= ruleINTEGER ) ) | ( (lv_realValue_2_0= ruleREAL ) ) | ( (lv_hexValue_3_0= RULE_NON_DECIMAL ) ) )
            int alt55=3;
            switch ( input.LA(1) ) {
            case PlusSign:
                {
                int LA55_1 = input.LA(2);

                if ( (LA55_1==RULE_INT) ) {
                    int LA55_3 = input.LA(3);

                    if ( (LA55_3==FullStop) ) {
                        alt55=2;
                    }
                    else if ( (LA55_3==EOF||LA55_3==END_REPEAT||LA55_3==THEN||LA55_3==AND||LA55_3==MOD||(LA55_3>=XOR && LA55_3<=FullStopFullStop)||(LA55_3>=LessThanSignEqualsSign && LA55_3<=GreaterThanSignEqualsSign)||LA55_3==BY||LA55_3==DO||(LA55_3>=OF && LA55_3<=OR)||LA55_3==TO||(LA55_3>=Ampersand && LA55_3<=HyphenMinus)||(LA55_3>=Solidus && LA55_3<=GreaterThanSign)||LA55_3==RightSquareBracket) ) {
                        alt55=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 55, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 55, 1, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA55_2 = input.LA(2);

                if ( (LA55_2==RULE_INT) ) {
                    int LA55_3 = input.LA(3);

                    if ( (LA55_3==FullStop) ) {
                        alt55=2;
                    }
                    else if ( (LA55_3==EOF||LA55_3==END_REPEAT||LA55_3==THEN||LA55_3==AND||LA55_3==MOD||(LA55_3>=XOR && LA55_3<=FullStopFullStop)||(LA55_3>=LessThanSignEqualsSign && LA55_3<=GreaterThanSignEqualsSign)||LA55_3==BY||LA55_3==DO||(LA55_3>=OF && LA55_3<=OR)||LA55_3==TO||(LA55_3>=Ampersand && LA55_3<=HyphenMinus)||(LA55_3>=Solidus && LA55_3<=GreaterThanSign)||LA55_3==RightSquareBracket) ) {
                        alt55=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 55, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 55, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
                {
                int LA55_3 = input.LA(2);

                if ( (LA55_3==FullStop) ) {
                    alt55=2;
                }
                else if ( (LA55_3==EOF||LA55_3==END_REPEAT||LA55_3==THEN||LA55_3==AND||LA55_3==MOD||(LA55_3>=XOR && LA55_3<=FullStopFullStop)||(LA55_3>=LessThanSignEqualsSign && LA55_3<=GreaterThanSignEqualsSign)||LA55_3==BY||LA55_3==DO||(LA55_3>=OF && LA55_3<=OR)||LA55_3==TO||(LA55_3>=Ampersand && LA55_3<=HyphenMinus)||(LA55_3>=Solidus && LA55_3<=GreaterThanSign)||LA55_3==RightSquareBracket) ) {
                    alt55=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 55, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt55=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }

            switch (alt55) {
                case 1 :
                    // InternalSTCoreParser.g:3053:4: ( (lv_intValue_1_0= ruleINTEGER ) )
                    {
                    // InternalSTCoreParser.g:3053:4: ( (lv_intValue_1_0= ruleINTEGER ) )
                    // InternalSTCoreParser.g:3054:5: (lv_intValue_1_0= ruleINTEGER )
                    {
                    // InternalSTCoreParser.g:3054:5: (lv_intValue_1_0= ruleINTEGER )
                    // InternalSTCoreParser.g:3055:6: lv_intValue_1_0= ruleINTEGER
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNUMERIC_LITERALAccess().getIntValueINTEGERParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_intValue_1_0=ruleINTEGER();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNUMERIC_LITERALRule());
                      						}
                      						set(
                      							current,
                      							"intValue",
                      							lv_intValue_1_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.INTEGER");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3073:4: ( (lv_realValue_2_0= ruleREAL ) )
                    {
                    // InternalSTCoreParser.g:3073:4: ( (lv_realValue_2_0= ruleREAL ) )
                    // InternalSTCoreParser.g:3074:5: (lv_realValue_2_0= ruleREAL )
                    {
                    // InternalSTCoreParser.g:3074:5: (lv_realValue_2_0= ruleREAL )
                    // InternalSTCoreParser.g:3075:6: lv_realValue_2_0= ruleREAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNUMERIC_LITERALAccess().getRealValueREALParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_realValue_2_0=ruleREAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNUMERIC_LITERALRule());
                      						}
                      						set(
                      							current,
                      							"realValue",
                      							lv_realValue_2_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.REAL");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3093:4: ( (lv_hexValue_3_0= RULE_NON_DECIMAL ) )
                    {
                    // InternalSTCoreParser.g:3093:4: ( (lv_hexValue_3_0= RULE_NON_DECIMAL ) )
                    // InternalSTCoreParser.g:3094:5: (lv_hexValue_3_0= RULE_NON_DECIMAL )
                    {
                    // InternalSTCoreParser.g:3094:5: (lv_hexValue_3_0= RULE_NON_DECIMAL )
                    // InternalSTCoreParser.g:3095:6: lv_hexValue_3_0= RULE_NON_DECIMAL
                    {
                    lv_hexValue_3_0=(Token)match(input,RULE_NON_DECIMAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_hexValue_3_0, grammarAccess.getNUMERIC_LITERALAccess().getHexValueNON_DECIMALTerminalRuleCall_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"hexValue",
                      							lv_hexValue_3_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.NON_DECIMAL");
                      					
                    }

                    }


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
    // $ANTLR end "ruleNUMERIC_LITERAL"


    // $ANTLR start "entryRuleDATE_LITERAL"
    // InternalSTCoreParser.g:3116:1: entryRuleDATE_LITERAL returns [EObject current=null] : iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF ;
    public final EObject entryRuleDATE_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDATE_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3116:53: (iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF )
            // InternalSTCoreParser.g:3117:2: iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDATE_LITERALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDATE_LITERAL=ruleDATE_LITERAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDATE_LITERAL; 
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
    // $ANTLR end "entryRuleDATE_LITERAL"


    // $ANTLR start "ruleDATE_LITERAL"
    // InternalSTCoreParser.g:3123:1: ruleDATE_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) ) ;
    public final EObject ruleDATE_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3129:2: ( ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) ) )
            // InternalSTCoreParser.g:3130:2: ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) )
            {
            // InternalSTCoreParser.g:3130:2: ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) )
            // InternalSTCoreParser.g:3131:3: ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) )
            {
            // InternalSTCoreParser.g:3131:3: ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) )
            // InternalSTCoreParser.g:3132:4: ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) )
            {
            // InternalSTCoreParser.g:3132:4: ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) )
            // InternalSTCoreParser.g:3133:5: (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD )
            {
            // InternalSTCoreParser.g:3133:5: (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD )
            int alt56=4;
            switch ( input.LA(1) ) {
            case DATE:
                {
                alt56=1;
                }
                break;
            case LDATE:
                {
                alt56=2;
                }
                break;
            case D:
                {
                alt56=3;
                }
                break;
            case LD:
                {
                alt56=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }

            switch (alt56) {
                case 1 :
                    // InternalSTCoreParser.g:3134:6: lv_keyword_0_1= DATE
                    {
                    lv_keyword_0_1=(Token)match(input,DATE,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_1, grammarAccess.getDATE_LITERALAccess().getKeywordDATEKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDATE_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_1, null);
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3145:6: lv_keyword_0_2= LDATE
                    {
                    lv_keyword_0_2=(Token)match(input,LDATE,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_2, grammarAccess.getDATE_LITERALAccess().getKeywordLDATEKeyword_0_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDATE_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_2, null);
                      					
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3156:6: lv_keyword_0_3= D
                    {
                    lv_keyword_0_3=(Token)match(input,D,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_3, grammarAccess.getDATE_LITERALAccess().getKeywordDKeyword_0_0_2());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDATE_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_3, null);
                      					
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3167:6: lv_keyword_0_4= LD
                    {
                    lv_keyword_0_4=(Token)match(input,LD,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_4, grammarAccess.getDATE_LITERALAccess().getKeywordLDKeyword_0_0_3());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDATE_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_4, null);
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalSTCoreParser.g:3180:3: ( (lv_value_1_0= ruleDATE ) )
            // InternalSTCoreParser.g:3181:4: (lv_value_1_0= ruleDATE )
            {
            // InternalSTCoreParser.g:3181:4: (lv_value_1_0= ruleDATE )
            // InternalSTCoreParser.g:3182:5: lv_value_1_0= ruleDATE
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDATE_LITERALAccess().getValueDATEParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_1_0=ruleDATE();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDATE_LITERALRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.DATE");
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
    // $ANTLR end "ruleDATE_LITERAL"


    // $ANTLR start "entryRuleTIME_LITERAL"
    // InternalSTCoreParser.g:3203:1: entryRuleTIME_LITERAL returns [EObject current=null] : iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF ;
    public final EObject entryRuleTIME_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTIME_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3203:53: (iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF )
            // InternalSTCoreParser.g:3204:2: iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTIME_LITERALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTIME_LITERAL=ruleTIME_LITERAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTIME_LITERAL; 
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
    // $ANTLR end "entryRuleTIME_LITERAL"


    // $ANTLR start "ruleTIME_LITERAL"
    // InternalSTCoreParser.g:3210:1: ruleTIME_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) ) ;
    public final EObject ruleTIME_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        Token lv_value_1_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3216:2: ( ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) ) )
            // InternalSTCoreParser.g:3217:2: ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) )
            {
            // InternalSTCoreParser.g:3217:2: ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) )
            // InternalSTCoreParser.g:3218:3: ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) )
            {
            // InternalSTCoreParser.g:3218:3: ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) )
            // InternalSTCoreParser.g:3219:4: ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) )
            {
            // InternalSTCoreParser.g:3219:4: ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) )
            // InternalSTCoreParser.g:3220:5: (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT )
            {
            // InternalSTCoreParser.g:3220:5: (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT )
            int alt57=4;
            switch ( input.LA(1) ) {
            case TIME:
                {
                alt57=1;
                }
                break;
            case LTIME:
                {
                alt57=2;
                }
                break;
            case T:
                {
                alt57=3;
                }
                break;
            case LT:
                {
                alt57=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }

            switch (alt57) {
                case 1 :
                    // InternalSTCoreParser.g:3221:6: lv_keyword_0_1= TIME
                    {
                    lv_keyword_0_1=(Token)match(input,TIME,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_1, grammarAccess.getTIME_LITERALAccess().getKeywordTIMEKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTIME_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_1, null);
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3232:6: lv_keyword_0_2= LTIME
                    {
                    lv_keyword_0_2=(Token)match(input,LTIME,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_2, grammarAccess.getTIME_LITERALAccess().getKeywordLTIMEKeyword_0_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTIME_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_2, null);
                      					
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3243:6: lv_keyword_0_3= T
                    {
                    lv_keyword_0_3=(Token)match(input,T,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_3, grammarAccess.getTIME_LITERALAccess().getKeywordTKeyword_0_0_2());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTIME_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_3, null);
                      					
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3254:6: lv_keyword_0_4= LT
                    {
                    lv_keyword_0_4=(Token)match(input,LT,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_4, grammarAccess.getTIME_LITERALAccess().getKeywordLTKeyword_0_0_3());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTIME_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_4, null);
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalSTCoreParser.g:3267:3: ( (lv_value_1_0= RULE_TIME ) )
            // InternalSTCoreParser.g:3268:4: (lv_value_1_0= RULE_TIME )
            {
            // InternalSTCoreParser.g:3268:4: (lv_value_1_0= RULE_TIME )
            // InternalSTCoreParser.g:3269:5: lv_value_1_0= RULE_TIME
            {
            lv_value_1_0=(Token)match(input,RULE_TIME,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_value_1_0, grammarAccess.getTIME_LITERALAccess().getValueTIMETerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getTIME_LITERALRule());
              					}
              					setWithLastConsumed(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME");
              				
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
    // $ANTLR end "ruleTIME_LITERAL"


    // $ANTLR start "entryRuleTIME_OF_DAY_LITERAL"
    // InternalSTCoreParser.g:3289:1: entryRuleTIME_OF_DAY_LITERAL returns [EObject current=null] : iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF ;
    public final EObject entryRuleTIME_OF_DAY_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTIME_OF_DAY_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3289:60: (iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF )
            // InternalSTCoreParser.g:3290:2: iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTIME_OF_DAY_LITERALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTIME_OF_DAY_LITERAL=ruleTIME_OF_DAY_LITERAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTIME_OF_DAY_LITERAL; 
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
    // $ANTLR end "entryRuleTIME_OF_DAY_LITERAL"


    // $ANTLR start "ruleTIME_OF_DAY_LITERAL"
    // InternalSTCoreParser.g:3296:1: ruleTIME_OF_DAY_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) ) ;
    public final EObject ruleTIME_OF_DAY_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3302:2: ( ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) ) )
            // InternalSTCoreParser.g:3303:2: ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) )
            {
            // InternalSTCoreParser.g:3303:2: ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) )
            // InternalSTCoreParser.g:3304:3: ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) )
            {
            // InternalSTCoreParser.g:3304:3: ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) )
            // InternalSTCoreParser.g:3305:4: ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) )
            {
            // InternalSTCoreParser.g:3305:4: ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) )
            // InternalSTCoreParser.g:3306:5: (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD )
            {
            // InternalSTCoreParser.g:3306:5: (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD )
            int alt58=3;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt58=1;
                }
                break;
            case TOD:
                {
                alt58=2;
                }
                break;
            case LTOD:
                {
                alt58=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }

            switch (alt58) {
                case 1 :
                    // InternalSTCoreParser.g:3307:6: lv_keyword_0_1= TIME_OF_DAY
                    {
                    lv_keyword_0_1=(Token)match(input,TIME_OF_DAY,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_1, grammarAccess.getTIME_OF_DAY_LITERALAccess().getKeywordTIME_OF_DAYKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTIME_OF_DAY_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_1, null);
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3318:6: lv_keyword_0_2= TOD
                    {
                    lv_keyword_0_2=(Token)match(input,TOD,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_2, grammarAccess.getTIME_OF_DAY_LITERALAccess().getKeywordTODKeyword_0_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTIME_OF_DAY_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_2, null);
                      					
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3329:6: lv_keyword_0_3= LTOD
                    {
                    lv_keyword_0_3=(Token)match(input,LTOD,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_3, grammarAccess.getTIME_OF_DAY_LITERALAccess().getKeywordLTODKeyword_0_0_2());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTIME_OF_DAY_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_3, null);
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalSTCoreParser.g:3342:3: ( (lv_value_1_0= ruleTIME_OF_DAY ) )
            // InternalSTCoreParser.g:3343:4: (lv_value_1_0= ruleTIME_OF_DAY )
            {
            // InternalSTCoreParser.g:3343:4: (lv_value_1_0= ruleTIME_OF_DAY )
            // InternalSTCoreParser.g:3344:5: lv_value_1_0= ruleTIME_OF_DAY
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTIME_OF_DAY_LITERALAccess().getValueTIME_OF_DAYParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_1_0=ruleTIME_OF_DAY();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTIME_OF_DAY_LITERALRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_OF_DAY");
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
    // $ANTLR end "ruleTIME_OF_DAY_LITERAL"


    // $ANTLR start "entryRuleDATE_AND_TIME_LITERAL"
    // InternalSTCoreParser.g:3365:1: entryRuleDATE_AND_TIME_LITERAL returns [EObject current=null] : iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF ;
    public final EObject entryRuleDATE_AND_TIME_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDATE_AND_TIME_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3365:62: (iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF )
            // InternalSTCoreParser.g:3366:2: iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDATE_AND_TIME_LITERALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDATE_AND_TIME_LITERAL=ruleDATE_AND_TIME_LITERAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDATE_AND_TIME_LITERAL; 
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
    // $ANTLR end "entryRuleDATE_AND_TIME_LITERAL"


    // $ANTLR start "ruleDATE_AND_TIME_LITERAL"
    // InternalSTCoreParser.g:3372:1: ruleDATE_AND_TIME_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) ) ;
    public final EObject ruleDATE_AND_TIME_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_dateValue_1_0 = null;

        AntlrDatatypeRuleToken lv_timeOfDayValue_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3378:2: ( ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) ) )
            // InternalSTCoreParser.g:3379:2: ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) )
            {
            // InternalSTCoreParser.g:3379:2: ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) )
            // InternalSTCoreParser.g:3380:3: ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) )
            {
            // InternalSTCoreParser.g:3380:3: ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) )
            // InternalSTCoreParser.g:3381:4: ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) )
            {
            // InternalSTCoreParser.g:3381:4: ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) )
            // InternalSTCoreParser.g:3382:5: (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT )
            {
            // InternalSTCoreParser.g:3382:5: (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT )
            int alt59=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt59=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt59=2;
                }
                break;
            case DT:
                {
                alt59=3;
                }
                break;
            case LDT:
                {
                alt59=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // InternalSTCoreParser.g:3383:6: lv_keyword_0_1= DATE_AND_TIME
                    {
                    lv_keyword_0_1=(Token)match(input,DATE_AND_TIME,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_1, grammarAccess.getDATE_AND_TIME_LITERALAccess().getKeywordDATE_AND_TIMEKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDATE_AND_TIME_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_1, null);
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3394:6: lv_keyword_0_2= LDATE_AND_TIME
                    {
                    lv_keyword_0_2=(Token)match(input,LDATE_AND_TIME,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_2, grammarAccess.getDATE_AND_TIME_LITERALAccess().getKeywordLDATE_AND_TIMEKeyword_0_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDATE_AND_TIME_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_2, null);
                      					
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3405:6: lv_keyword_0_3= DT
                    {
                    lv_keyword_0_3=(Token)match(input,DT,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_3, grammarAccess.getDATE_AND_TIME_LITERALAccess().getKeywordDTKeyword_0_0_2());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDATE_AND_TIME_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_3, null);
                      					
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3416:6: lv_keyword_0_4= LDT
                    {
                    lv_keyword_0_4=(Token)match(input,LDT,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_4, grammarAccess.getDATE_AND_TIME_LITERALAccess().getKeywordLDTKeyword_0_0_3());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDATE_AND_TIME_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_4, null);
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalSTCoreParser.g:3429:3: ( (lv_dateValue_1_0= ruleDATE ) )
            // InternalSTCoreParser.g:3430:4: (lv_dateValue_1_0= ruleDATE )
            {
            // InternalSTCoreParser.g:3430:4: (lv_dateValue_1_0= ruleDATE )
            // InternalSTCoreParser.g:3431:5: lv_dateValue_1_0= ruleDATE
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDATE_AND_TIME_LITERALAccess().getDateValueDATEParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_57);
            lv_dateValue_1_0=ruleDATE();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDATE_AND_TIME_LITERALRule());
              					}
              					set(
              						current,
              						"dateValue",
              						lv_dateValue_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.DATE");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,HyphenMinus,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getDATE_AND_TIME_LITERALAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalSTCoreParser.g:3452:3: ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) )
            // InternalSTCoreParser.g:3453:4: (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY )
            {
            // InternalSTCoreParser.g:3453:4: (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY )
            // InternalSTCoreParser.g:3454:5: lv_timeOfDayValue_3_0= ruleTIME_OF_DAY
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDATE_AND_TIME_LITERALAccess().getTimeOfDayValueTIME_OF_DAYParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_timeOfDayValue_3_0=ruleTIME_OF_DAY();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDATE_AND_TIME_LITERALRule());
              					}
              					set(
              						current,
              						"timeOfDayValue",
              						lv_timeOfDayValue_3_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_OF_DAY");
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
    // $ANTLR end "ruleDATE_AND_TIME_LITERAL"


    // $ANTLR start "entryRuleSTRING_LITERAL"
    // InternalSTCoreParser.g:3475:1: entryRuleSTRING_LITERAL returns [EObject current=null] : iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF ;
    public final EObject entryRuleSTRING_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRING_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3475:55: (iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF )
            // InternalSTCoreParser.g:3476:2: iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTRING_LITERALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTRING_LITERAL=ruleSTRING_LITERAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTRING_LITERAL; 
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
    // $ANTLR end "entryRuleSTRING_LITERAL"


    // $ANTLR start "ruleSTRING_LITERAL"
    // InternalSTCoreParser.g:3482:1: ruleSTRING_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTRING_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_value_4_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3488:2: ( ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) ) )
            // InternalSTCoreParser.g:3489:2: ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) )
            {
            // InternalSTCoreParser.g:3489:2: ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) )
            // InternalSTCoreParser.g:3490:3: ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) )
            {
            // InternalSTCoreParser.g:3490:3: ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )?
            int alt60=5;
            switch ( input.LA(1) ) {
                case STRING:
                    {
                    alt60=1;
                    }
                    break;
                case WSTRING:
                    {
                    alt60=2;
                    }
                    break;
                case CHAR:
                    {
                    alt60=3;
                    }
                    break;
                case WCHAR:
                    {
                    alt60=4;
                    }
                    break;
            }

            switch (alt60) {
                case 1 :
                    // InternalSTCoreParser.g:3491:4: ( (lv_keyword_0_0= STRING ) )
                    {
                    // InternalSTCoreParser.g:3491:4: ( (lv_keyword_0_0= STRING ) )
                    // InternalSTCoreParser.g:3492:5: (lv_keyword_0_0= STRING )
                    {
                    // InternalSTCoreParser.g:3492:5: (lv_keyword_0_0= STRING )
                    // InternalSTCoreParser.g:3493:6: lv_keyword_0_0= STRING
                    {
                    lv_keyword_0_0=(Token)match(input,STRING,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_keyword_0_0, grammarAccess.getSTRING_LITERALAccess().getKeywordSTRINGKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTRING_LITERALRule());
                      						}
                      						setWithLastConsumed(current, "keyword", lv_keyword_0_0, "STRING#");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3506:4: otherlv_1= WSTRING
                    {
                    otherlv_1=(Token)match(input,WSTRING,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTRING_LITERALAccess().getWSTRINGKeyword_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3511:4: otherlv_2= CHAR
                    {
                    otherlv_2=(Token)match(input,CHAR,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTRING_LITERALAccess().getCHARKeyword_0_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3516:4: otherlv_3= WCHAR
                    {
                    otherlv_3=(Token)match(input,WCHAR,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTRING_LITERALAccess().getWCHARKeyword_0_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:3521:3: ( (lv_value_4_0= RULE_STRING ) )
            // InternalSTCoreParser.g:3522:4: (lv_value_4_0= RULE_STRING )
            {
            // InternalSTCoreParser.g:3522:4: (lv_value_4_0= RULE_STRING )
            // InternalSTCoreParser.g:3523:5: lv_value_4_0= RULE_STRING
            {
            lv_value_4_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_value_4_0, grammarAccess.getSTRING_LITERALAccess().getValueSTRINGTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTRING_LITERALRule());
              					}
              					setWithLastConsumed(
              						current,
              						"value",
              						lv_value_4_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STRING");
              				
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
    // $ANTLR end "ruleSTRING_LITERAL"


    // $ANTLR start "entryRuleINTEGER"
    // InternalSTCoreParser.g:3543:1: entryRuleINTEGER returns [String current=null] : iv_ruleINTEGER= ruleINTEGER EOF ;
    public final String entryRuleINTEGER() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINTEGER = null;


        try {
            // InternalSTCoreParser.g:3543:47: (iv_ruleINTEGER= ruleINTEGER EOF )
            // InternalSTCoreParser.g:3544:2: iv_ruleINTEGER= ruleINTEGER EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getINTEGERRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleINTEGER=ruleINTEGER();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleINTEGER.getText(); 
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
    // $ANTLR end "entryRuleINTEGER"


    // $ANTLR start "ruleINTEGER"
    // InternalSTCoreParser.g:3550:1: ruleINTEGER returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleINTEGER() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3556:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ) )
            // InternalSTCoreParser.g:3557:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT )
            {
            // InternalSTCoreParser.g:3557:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT )
            // InternalSTCoreParser.g:3558:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT
            {
            // InternalSTCoreParser.g:3558:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt61=3;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==PlusSign) ) {
                alt61=1;
            }
            else if ( (LA61_0==HyphenMinus) ) {
                alt61=2;
            }
            switch (alt61) {
                case 1 :
                    // InternalSTCoreParser.g:3559:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getINTEGERAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3565:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getINTEGERAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getINTEGERAccess().getINTTerminalRuleCall_1());
              		
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
    // $ANTLR end "ruleINTEGER"


    // $ANTLR start "entryRuleREAL"
    // InternalSTCoreParser.g:3582:1: entryRuleREAL returns [String current=null] : iv_ruleREAL= ruleREAL EOF ;
    public final String entryRuleREAL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleREAL = null;


        try {
            // InternalSTCoreParser.g:3582:44: (iv_ruleREAL= ruleREAL EOF )
            // InternalSTCoreParser.g:3583:2: iv_ruleREAL= ruleREAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getREALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleREAL=ruleREAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleREAL.getText(); 
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
    // $ANTLR end "entryRuleREAL"


    // $ANTLR start "ruleREAL"
    // InternalSTCoreParser.g:3589:1: ruleREAL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleREAL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_EXT_INT_2=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_INTEGER_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3595:2: ( (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) ) )
            // InternalSTCoreParser.g:3596:2: (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) )
            {
            // InternalSTCoreParser.g:3596:2: (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) )
            // InternalSTCoreParser.g:3597:3: this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getREALAccess().getINTEGERParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_59);
            this_INTEGER_0=ruleINTEGER();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INTEGER_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,FullStop,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getREALAccess().getFullStopKeyword_1());
              		
            }
            // InternalSTCoreParser.g:3612:3: (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==RULE_EXT_INT) ) {
                alt62=1;
            }
            else if ( (LA62_0==RULE_INT) ) {
                alt62=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // InternalSTCoreParser.g:3613:4: this_EXT_INT_2= RULE_EXT_INT
                    {
                    this_EXT_INT_2=(Token)match(input,RULE_EXT_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_EXT_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_EXT_INT_2, grammarAccess.getREALAccess().getEXT_INTTerminalRuleCall_2_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3621:4: this_INT_3= RULE_INT
                    {
                    this_INT_3=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_3);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_3, grammarAccess.getREALAccess().getINTTerminalRuleCall_2_1());
                      			
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
    // $ANTLR end "ruleREAL"


    // $ANTLR start "entryRuleDATE"
    // InternalSTCoreParser.g:3633:1: entryRuleDATE returns [String current=null] : iv_ruleDATE= ruleDATE EOF ;
    public final String entryRuleDATE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDATE = null;


        try {
            // InternalSTCoreParser.g:3633:44: (iv_ruleDATE= ruleDATE EOF )
            // InternalSTCoreParser.g:3634:2: iv_ruleDATE= ruleDATE EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDATERule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDATE=ruleDATE();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDATE.getText(); 
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
    // $ANTLR end "entryRuleDATE"


    // $ANTLR start "ruleDATE"
    // InternalSTCoreParser.g:3640:1: ruleDATE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDATE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3646:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTCoreParser.g:3647:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTCoreParser.g:3647:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTCoreParser.g:3648:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDATEAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDATEAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDATEAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDATEAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDATEAccess().getINTTerminalRuleCall_4());
              		
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
    // $ANTLR end "ruleDATE"


    // $ANTLR start "entryRuleTIME_OF_DAY"
    // InternalSTCoreParser.g:3683:1: entryRuleTIME_OF_DAY returns [String current=null] : iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF ;
    public final String entryRuleTIME_OF_DAY() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTIME_OF_DAY = null;


        try {
            // InternalSTCoreParser.g:3683:51: (iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF )
            // InternalSTCoreParser.g:3684:2: iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTIME_OF_DAYRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTIME_OF_DAY=ruleTIME_OF_DAY();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTIME_OF_DAY.getText(); 
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
    // $ANTLR end "entryRuleTIME_OF_DAY"


    // $ANTLR start "ruleTIME_OF_DAY"
    // InternalSTCoreParser.g:3690:1: ruleTIME_OF_DAY returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTIME_OF_DAY() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3696:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) )
            // InternalSTCoreParser.g:3697:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            {
            // InternalSTCoreParser.g:3697:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            // InternalSTCoreParser.g:3698:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTIME_OF_DAYAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTIME_OF_DAYAccess().getColonKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getTIME_OF_DAYAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTIME_OF_DAYAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_61); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTIME_OF_DAYAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTCoreParser.g:3729:3: (kw= FullStop this_INT_6= RULE_INT )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==FullStop) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalSTCoreParser.g:3730:4: kw= FullStop this_INT_6= RULE_INT
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTIME_OF_DAYAccess().getFullStopKeyword_5_0());
                      			
                    }
                    this_INT_6=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_6);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_6, grammarAccess.getTIME_OF_DAYAccess().getINTTerminalRuleCall_5_1());
                      			
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
    // $ANTLR end "ruleTIME_OF_DAY"


    // $ANTLR start "ruleSubrangeOperator"
    // InternalSTCoreParser.g:3747:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3753:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTCoreParser.g:3754:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTCoreParser.g:3754:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTCoreParser.g:3755:3: enumLiteral_0= FullStopFullStop
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
    // InternalSTCoreParser.g:3764:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3770:2: ( (enumLiteral_0= OR ) )
            // InternalSTCoreParser.g:3771:2: (enumLiteral_0= OR )
            {
            // InternalSTCoreParser.g:3771:2: (enumLiteral_0= OR )
            // InternalSTCoreParser.g:3772:3: enumLiteral_0= OR
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
    // InternalSTCoreParser.g:3781:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3787:2: ( (enumLiteral_0= XOR ) )
            // InternalSTCoreParser.g:3788:2: (enumLiteral_0= XOR )
            {
            // InternalSTCoreParser.g:3788:2: (enumLiteral_0= XOR )
            // InternalSTCoreParser.g:3789:3: enumLiteral_0= XOR
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
    // InternalSTCoreParser.g:3798:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3804:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTCoreParser.g:3805:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTCoreParser.g:3805:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
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
                    // InternalSTCoreParser.g:3806:3: (enumLiteral_0= AND )
                    {
                    // InternalSTCoreParser.g:3806:3: (enumLiteral_0= AND )
                    // InternalSTCoreParser.g:3807:4: enumLiteral_0= AND
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
                    // InternalSTCoreParser.g:3814:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTCoreParser.g:3814:3: (enumLiteral_1= Ampersand )
                    // InternalSTCoreParser.g:3815:4: enumLiteral_1= Ampersand
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
    // InternalSTCoreParser.g:3825:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3831:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTCoreParser.g:3832:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTCoreParser.g:3832:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
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
                    // InternalSTCoreParser.g:3833:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTCoreParser.g:3833:3: (enumLiteral_0= EqualsSign )
                    // InternalSTCoreParser.g:3834:4: enumLiteral_0= EqualsSign
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
                    // InternalSTCoreParser.g:3841:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTCoreParser.g:3841:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTCoreParser.g:3842:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalSTCoreParser.g:3852:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3858:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTCoreParser.g:3859:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTCoreParser.g:3859:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
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
                    // InternalSTCoreParser.g:3860:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTCoreParser.g:3860:3: (enumLiteral_0= LessThanSign )
                    // InternalSTCoreParser.g:3861:4: enumLiteral_0= LessThanSign
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
                    // InternalSTCoreParser.g:3868:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTCoreParser.g:3868:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTCoreParser.g:3869:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalSTCoreParser.g:3876:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTCoreParser.g:3876:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTCoreParser.g:3877:4: enumLiteral_2= GreaterThanSign
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
                    // InternalSTCoreParser.g:3884:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTCoreParser.g:3884:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTCoreParser.g:3885:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalSTCoreParser.g:3895:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3901:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTCoreParser.g:3902:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTCoreParser.g:3902:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
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
                    // InternalSTCoreParser.g:3903:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTCoreParser.g:3903:3: (enumLiteral_0= PlusSign )
                    // InternalSTCoreParser.g:3904:4: enumLiteral_0= PlusSign
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
                    // InternalSTCoreParser.g:3911:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTCoreParser.g:3911:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTCoreParser.g:3912:4: enumLiteral_1= HyphenMinus
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
    // InternalSTCoreParser.g:3922:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3928:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTCoreParser.g:3929:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTCoreParser.g:3929:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
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
                    // InternalSTCoreParser.g:3930:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTCoreParser.g:3930:3: (enumLiteral_0= Asterisk )
                    // InternalSTCoreParser.g:3931:4: enumLiteral_0= Asterisk
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
                    // InternalSTCoreParser.g:3938:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTCoreParser.g:3938:3: (enumLiteral_1= Solidus )
                    // InternalSTCoreParser.g:3939:4: enumLiteral_1= Solidus
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
                    // InternalSTCoreParser.g:3946:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTCoreParser.g:3946:3: (enumLiteral_2= MOD )
                    // InternalSTCoreParser.g:3947:4: enumLiteral_2= MOD
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
    // InternalSTCoreParser.g:3957:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3963:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTCoreParser.g:3964:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTCoreParser.g:3964:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTCoreParser.g:3965:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalSTCoreParser.g:3974:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3980:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTCoreParser.g:3981:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTCoreParser.g:3981:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
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
                    // InternalSTCoreParser.g:3982:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTCoreParser.g:3982:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTCoreParser.g:3983:4: enumLiteral_0= HyphenMinus
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
                    // InternalSTCoreParser.g:3990:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTCoreParser.g:3990:3: (enumLiteral_1= PlusSign )
                    // InternalSTCoreParser.g:3991:4: enumLiteral_1= PlusSign
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
                    // InternalSTCoreParser.g:3998:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTCoreParser.g:3998:3: (enumLiteral_2= NOT )
                    // InternalSTCoreParser.g:3999:4: enumLiteral_2= NOT
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


    // $ANTLR start "ruleMultiBitAccessSpecifier"
    // InternalSTCoreParser.g:4009:1: ruleMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) ) ;
    public final Enumerator ruleMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4015:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) ) )
            // InternalSTCoreParser.g:4016:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) )
            {
            // InternalSTCoreParser.g:4016:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) )
            int alt70=6;
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
            case FullStop:
                {
                alt70=6;
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
                    // InternalSTCoreParser.g:4017:3: (enumLiteral_0= L )
                    {
                    // InternalSTCoreParser.g:4017:3: (enumLiteral_0= L )
                    // InternalSTCoreParser.g:4018:4: enumLiteral_0= L
                    {
                    enumLiteral_0=(Token)match(input,L,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMultiBitAccessSpecifierAccess().getLwordAccessEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getMultiBitAccessSpecifierAccess().getLwordAccessEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:4025:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTCoreParser.g:4025:3: (enumLiteral_1= D_1 )
                    // InternalSTCoreParser.g:4026:4: enumLiteral_1= D_1
                    {
                    enumLiteral_1=(Token)match(input,D_1,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMultiBitAccessSpecifierAccess().getDwordAccessEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getMultiBitAccessSpecifierAccess().getDwordAccessEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:4033:3: (enumLiteral_2= W )
                    {
                    // InternalSTCoreParser.g:4033:3: (enumLiteral_2= W )
                    // InternalSTCoreParser.g:4034:4: enumLiteral_2= W
                    {
                    enumLiteral_2=(Token)match(input,W,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMultiBitAccessSpecifierAccess().getWordAccessEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getMultiBitAccessSpecifierAccess().getWordAccessEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:4041:3: (enumLiteral_3= B )
                    {
                    // InternalSTCoreParser.g:4041:3: (enumLiteral_3= B )
                    // InternalSTCoreParser.g:4042:4: enumLiteral_3= B
                    {
                    enumLiteral_3=(Token)match(input,B,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMultiBitAccessSpecifierAccess().getByteAccessEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_3, grammarAccess.getMultiBitAccessSpecifierAccess().getByteAccessEnumLiteralDeclaration_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:4049:3: (enumLiteral_4= X )
                    {
                    // InternalSTCoreParser.g:4049:3: (enumLiteral_4= X )
                    // InternalSTCoreParser.g:4050:4: enumLiteral_4= X
                    {
                    enumLiteral_4=(Token)match(input,X,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMultiBitAccessSpecifierAccess().getBitAccessEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_4, grammarAccess.getMultiBitAccessSpecifierAccess().getBitAccessEnumLiteralDeclaration_4());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSTCoreParser.g:4057:3: (enumLiteral_5= FullStop )
                    {
                    // InternalSTCoreParser.g:4057:3: (enumLiteral_5= FullStop )
                    // InternalSTCoreParser.g:4058:4: enumLiteral_5= FullStop
                    {
                    enumLiteral_5=(Token)match(input,FullStop,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMultiBitAccessSpecifierAccess().getBitAccessShortcutEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_5, grammarAccess.getMultiBitAccessSpecifierAccess().getBitAccessShortcutEnumLiteralDeclaration_5());
                      			
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
    // $ANTLR end "ruleMultiBitAccessSpecifier"

    // $ANTLR start synpred1_InternalSTCoreParser
    public final void synpred1_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:1035:4: ( ( ruleSTStatement ) )
        // InternalSTCoreParser.g:1035:5: ( ruleSTStatement )
        {
        // InternalSTCoreParser.g:1035:5: ( ruleSTStatement )
        // InternalSTCoreParser.g:1036:5: ruleSTStatement
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
        // InternalSTCoreParser.g:2287:6: ( ( LeftParenthesis ) )
        // InternalSTCoreParser.g:2287:7: ( LeftParenthesis )
        {
        // InternalSTCoreParser.g:2287:7: ( LeftParenthesis )
        // InternalSTCoreParser.g:2288:7: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSTCoreParser

    // $ANTLR start synpred3_InternalSTCoreParser
    public final void synpred3_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:2356:5: ( ( ruleMultibitPartialAccess ) )
        // InternalSTCoreParser.g:2356:6: ( ruleMultibitPartialAccess )
        {
        // InternalSTCoreParser.g:2356:6: ( ruleMultibitPartialAccess )
        // InternalSTCoreParser.g:2357:6: ruleMultibitPartialAccess
        {
        pushFollow(FOLLOW_2);
        ruleMultibitPartialAccess();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred3_InternalSTCoreParser

    // $ANTLR start synpred4_InternalSTCoreParser
    public final void synpred4_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:2534:6: ( ( LeftParenthesis ) )
        // InternalSTCoreParser.g:2534:7: ( LeftParenthesis )
        {
        // InternalSTCoreParser.g:2534:7: ( LeftParenthesis )
        // InternalSTCoreParser.g:2535:7: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred4_InternalSTCoreParser

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
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA48 dfa48 = new DFA48(this);
    static final String dfa_1s = "\15\uffff";
    static final String dfa_2s = "\1\1\14\uffff";
    static final String dfa_3s = "\1\4\1\uffff\1\70\12\uffff";
    static final String dfa_4s = "\1\154\1\uffff\1\144\12\uffff";
    static final String dfa_5s = "\1\uffff\1\2\1\uffff\12\1";
    static final String dfa_6s = "\1\0\1\uffff\1\1\12\uffff}>";
    static final String[] dfa_7s = {
            "\3\1\5\uffff\1\11\1\1\1\uffff\1\1\2\uffff\2\1\1\uffff\4\1\1\7\1\10\4\1\1\uffff\5\1\1\uffff\7\1\1\uffff\1\6\1\1\1\4\1\1\1\12\2\1\1\uffff\2\1\6\uffff\1\1\1\5\2\1\1\uffff\1\1\12\uffff\1\1\1\uffff\1\3\2\uffff\1\1\3\uffff\1\1\2\uffff\1\1\1\uffff\1\1\3\uffff\1\13\6\uffff\2\1\2\uffff\1\2\1\1",
            "",
            "\6\1\4\uffff\1\1\2\uffff\3\1\1\14\3\1\6\uffff\1\1\2\uffff\3\1\1\uffff\7\1\1\uffff\4\1",
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
            return "()* loopback of 1034:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
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
                        if ( (LA21_0==EOF||(LA21_0>=LDATE_AND_TIME && LA21_0<=TIME_OF_DAY)||LA21_0==END_CASE||LA21_0==WSTRING||(LA21_0>=STRING && LA21_0<=DWORD)||(LA21_0>=LDATE && LA21_0<=LWORD)||(LA21_0>=UDINT && LA21_0<=WCHAR)||(LA21_0>=BOOL && LA21_0<=DINT)||(LA21_0>=FALSE && LA21_0<=UINT)||LA21_0==WORD||LA21_0==ELSE||(LA21_0>=INT && LA21_0<=LDT)||(LA21_0>=TOD && LA21_0<=TRUE)||LA21_0==DT||(LA21_0>=LD && LA21_0<=LT)||LA21_0==NOT||LA21_0==D||LA21_0==T||LA21_0==LeftParenthesis||LA21_0==PlusSign||LA21_0==HyphenMinus||(LA21_0>=RULE_NON_DECIMAL && LA21_0<=RULE_INT)||LA21_0==RULE_STRING) ) {s = 1;}

                        else if ( (LA21_0==RULE_ID) ) {s = 2;}

                        else if ( (LA21_0==IF) && (synpred1_InternalSTCoreParser())) {s = 3;}

                        else if ( (LA21_0==CASE) && (synpred1_InternalSTCoreParser())) {s = 4;}

                        else if ( (LA21_0==FOR) && (synpred1_InternalSTCoreParser())) {s = 5;}

                        else if ( (LA21_0==WHILE) && (synpred1_InternalSTCoreParser())) {s = 6;}

                        else if ( (LA21_0==REPEAT) && (synpred1_InternalSTCoreParser())) {s = 7;}

                        else if ( (LA21_0==RETURN) && (synpred1_InternalSTCoreParser())) {s = 8;}

                        else if ( (LA21_0==CONTINUE) && (synpred1_InternalSTCoreParser())) {s = 9;}

                        else if ( (LA21_0==EXIT) && (synpred1_InternalSTCoreParser())) {s = 10;}

                        else if ( (LA21_0==Semicolon) && (synpred1_InternalSTCoreParser())) {s = 11;}

                         
                        input.seek(index21_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA21_2 = input.LA(1);

                         
                        int index21_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA21_2>=B && LA21_2<=AND)||LA21_2==MOD||(LA21_2>=XOR && LA21_2<=FullStopFullStop)||(LA21_2>=LessThanSignEqualsSign && LA21_2<=GreaterThanSignEqualsSign)||LA21_2==OR||(LA21_2>=NumberSign && LA21_2<=LeftParenthesis)||(LA21_2>=Asterisk && LA21_2<=Colon)||(LA21_2>=LessThanSign && LA21_2<=LeftSquareBracket)) ) {s = 1;}

                        else if ( (LA21_2==ColonEqualsSign) && (synpred1_InternalSTCoreParser())) {s = 12;}

                         
                        input.seek(index21_2);
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
    static final String dfa_8s = "\47\uffff";
    static final String dfa_9s = "\1\2\46\uffff";
    static final String dfa_10s = "\1\7\1\0\45\uffff";
    static final String dfa_11s = "\1\145\1\0\45\uffff";
    static final String dfa_12s = "\2\uffff\1\2\43\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\45\uffff}>";
    static final String[] dfa_14s = {
            "\1\2\55\uffff\1\2\2\uffff\6\2\4\uffff\1\2\2\uffff\3\2\1\uffff\3\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\16\2",
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
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "2285:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA41_1 = input.LA(1);

                         
                        int index41_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 38;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index41_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 41, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\42\uffff";
    static final String dfa_16s = "\1\2\41\uffff";
    static final String dfa_17s = "\1\7\1\0\40\uffff";
    static final String dfa_18s = "\1\145\1\0\40\uffff";
    static final String dfa_19s = "\2\uffff\1\2\36\uffff\1\1";
    static final String dfa_20s = "\1\uffff\1\0\40\uffff}>";
    static final String[] dfa_21s = {
            "\1\2\55\uffff\1\2\7\uffff\1\2\4\uffff\1\2\2\uffff\3\2\1\uffff\3\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\16\2",
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
            ""
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA48 extends DFA {

        public DFA48(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 48;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "2532:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA48_1 = input.LA(1);

                         
                        int index48_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSTCoreParser()) ) {s = 33;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index48_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 48, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x8005400006001002L,0x0000080100010000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000080001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000080000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x40D89FDF79EC8070L,0x000019801488400BL});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000002008000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000100L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000100L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x40D89FDF79EC8070L,0x000019901488400BL});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x8007402006101000L,0x0000080100010000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0002002000100000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x8005400006001000L,0x0000080100010000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x40DA9FDF79ECA070L,0x000019801488400BL});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000088000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x000000000000A000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x8005400006011000L,0x0000080100010000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x8005400006001200L,0x0000080100010000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x8005600006001000L,0x0000080100010000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x2000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000400L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000000A00000A00L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000000014000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000042000004L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0000080000800000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000001020000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x1F00000000000002L,0x0000001020800000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x40D89FDF79EC8070L,0x000019801588400BL});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000000009000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x1F00000000000002L,0x0000001020000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x1F00000000000002L,0x0000000020800000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0080004000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000018014000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000050000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});

}