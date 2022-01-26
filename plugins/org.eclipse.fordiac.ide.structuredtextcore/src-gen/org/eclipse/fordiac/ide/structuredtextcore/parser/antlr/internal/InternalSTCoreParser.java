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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "TIME_OF_DAY", "END_REPEAT", "VAR_OUTPUT", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "VAR_TEMP", "WSTRING", "END_FOR", "END_VAR", "STRING", "DWORD", "END_IF", "LDATE", "LREAL", "LTIME", "LWORD", "REPEAT", "RETURN", "UDINT", "ULINT", "USINT", "WCHAR", "ARRAY", "BOOL", "BYTE", "CHAR", "DATE", "DINT", "ELSIF", "LINT", "LTOD", "REAL", "SINT", "TIME", "UINT", "UNTIL", "WHILE", "WORD", "CASE", "ELSE", "EXIT", "INT", "LDT", "THEN", "TOD", "B", "D_1", "L", "W", "X", "AND", "DT", "FOR", "LD", "LT", "MOD", "NOT", "VAR", "XOR", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "D", "DO", "IF", "OF", "OR", "T", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "LeftSquareBracket", "RightSquareBracket", "RULE_BOOL_VALUES", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_TIME", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_WSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=24;
    public static final int LessThanSignGreaterThanSign=72;
    public static final int EqualsSignGreaterThanSign=73;
    public static final int VAR=66;
    public static final int END_IF=20;
    public static final int ULINT=28;
    public static final int END_CASE=13;
    public static final int LessThanSign=96;
    public static final int LeftParenthesis=86;
    public static final int BYTE=33;
    public static final int ELSE=48;
    public static final int RULE_TIME=105;
    public static final int IF=79;
    public static final int LINT=38;
    public static final int GreaterThanSign=98;
    public static final int WORD=46;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=107;
    public static final int TOD=53;
    public static final int DINT=36;
    public static final int UDINT=27;
    public static final int CASE=47;
    public static final int GreaterThanSignEqualsSign=74;
    public static final int AT=75;
    public static final int PlusSign=89;
    public static final int RULE_INT=104;
    public static final int END_FOR=16;
    public static final int RULE_ML_COMMENT=110;
    public static final int THEN=52;
    public static final int XOR=67;
    public static final int LeftSquareBracket=99;
    public static final int EXIT=49;
    public static final int TIME_OF_DAY=6;
    public static final int B=54;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=25;
    public static final int D=77;
    public static final int CHAR=34;
    public static final int L=56;
    public static final int LTIME=23;
    public static final int Comma=90;
    public static final int HyphenMinus=91;
    public static final int T=82;
    public static final int W=57;
    public static final int BY=76;
    public static final int X=58;
    public static final int ELSIF=37;
    public static final int END_REPEAT=7;
    public static final int LessThanSignEqualsSign=71;
    public static final int Solidus=93;
    public static final int VAR_INPUT=10;
    public static final int FullStop=92;
    public static final int VAR_TEMP=14;
    public static final int CONSTANT=11;
    public static final int CONTINUE=12;
    public static final int Semicolon=95;
    public static final int LD=62;
    public static final int VAR_OUTPUT=8;
    public static final int STRING=18;
    public static final int RULE_HEX_DIGIT=102;
    public static final int TO=83;
    public static final int RULE_BOOL_VALUES=101;
    public static final int UINT=43;
    public static final int LTOD=39;
    public static final int ARRAY=31;
    public static final int LT=63;
    public static final int DO=78;
    public static final int WSTRING=15;
    public static final int DT=60;
    public static final int END_VAR=17;
    public static final int FullStopFullStop=69;
    public static final int Ampersand=85;
    public static final int RightSquareBracket=100;
    public static final int USINT=29;
    public static final int DWORD=19;
    public static final int FOR=61;
    public static final int RightParenthesis=87;
    public static final int ColonEqualsSign=70;
    public static final int RULE_WSTRING=109;
    public static final int END_WHILE=9;
    public static final int DATE=35;
    public static final int NOT=65;
    public static final int LDATE=21;
    public static final int AND=59;
    public static final int NumberSign=84;
    public static final int REAL=40;
    public static final int AsteriskAsterisk=68;
    public static final int SINT=41;
    public static final int LREAL=22;
    public static final int WCHAR=30;
    public static final int RULE_STRING=108;
    public static final int INT=50;
    public static final int RULE_SL_COMMENT=111;
    public static final int RETURN=26;
    public static final int EqualsSign=97;
    public static final int OF=80;
    public static final int Colon=94;
    public static final int EOF=-1;
    public static final int LDT=51;
    public static final int Asterisk=88;
    public static final int MOD=64;
    public static final int OR=81;
    public static final int RULE_WS=112;
    public static final int RULE_EXT_INT=106;
    public static final int TIME=42;
    public static final int RULE_ANY_OTHER=113;
    public static final int UNTIL=44;
    public static final int BOOL=32;
    public static final int D_1=55;
    public static final int WHILE=45;
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

                        if ( ((LA5_1>=LDATE_AND_TIME && LA5_1<=TIME_OF_DAY)||LA5_1==WSTRING||(LA5_1>=STRING && LA5_1<=DWORD)||(LA5_1>=LDATE && LA5_1<=LWORD)||(LA5_1>=UDINT && LA5_1<=WCHAR)||(LA5_1>=BOOL && LA5_1<=DINT)||(LA5_1>=LINT && LA5_1<=UINT)||LA5_1==WORD||(LA5_1>=INT && LA5_1<=LDT)||LA5_1==TOD||LA5_1==DT||(LA5_1>=LD && LA5_1<=LT)||LA5_1==NOT||LA5_1==D||LA5_1==T||LA5_1==LeftParenthesis||LA5_1==PlusSign||LA5_1==HyphenMinus||LA5_1==RULE_BOOL_VALUES||(LA5_1>=RULE_NON_DECIMAL && LA5_1<=RULE_INT)||(LA5_1>=RULE_ID && LA5_1<=RULE_STRING)) ) {
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

            if ( ((LA9_0>=LDATE_AND_TIME && LA9_0<=TIME_OF_DAY)||LA9_0==WSTRING||(LA9_0>=STRING && LA9_0<=DWORD)||(LA9_0>=LDATE && LA9_0<=LWORD)||(LA9_0>=UDINT && LA9_0<=WCHAR)||(LA9_0>=BOOL && LA9_0<=DINT)||(LA9_0>=LINT && LA9_0<=UINT)||LA9_0==WORD||(LA9_0>=INT && LA9_0<=LDT)||LA9_0==TOD||LA9_0==DT||(LA9_0>=LD && LA9_0<=LT)||LA9_0==NOT||LA9_0==D||LA9_0==T||LA9_0==LeftParenthesis||LA9_0==PlusSign||LA9_0==HyphenMinus||LA9_0==RULE_BOOL_VALUES||(LA9_0>=RULE_NON_DECIMAL && LA9_0<=RULE_INT)||(LA9_0>=RULE_ID && LA9_0<=RULE_STRING)) ) {
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
    // InternalSTCoreParser.g:527:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon ) | ( () otherlv_5= Semicolon ) | ( () otherlv_7= RETURN otherlv_8= Semicolon ) | ( () otherlv_10= CONTINUE otherlv_11= Semicolon ) | ( () otherlv_13= EXIT otherlv_14= Semicolon ) ) ;
    public final EObject ruleSTStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        EObject this_STBranchStatements_0 = null;

        EObject this_STLoopStatements_1 = null;

        EObject this_STAssignmentStatement_2 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:533:2: ( ( ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon ) | ( () otherlv_5= Semicolon ) | ( () otherlv_7= RETURN otherlv_8= Semicolon ) | ( () otherlv_10= CONTINUE otherlv_11= Semicolon ) | ( () otherlv_13= EXIT otherlv_14= Semicolon ) ) )
            // InternalSTCoreParser.g:534:2: ( ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon ) | ( () otherlv_5= Semicolon ) | ( () otherlv_7= RETURN otherlv_8= Semicolon ) | ( () otherlv_10= CONTINUE otherlv_11= Semicolon ) | ( () otherlv_13= EXIT otherlv_14= Semicolon ) )
            {
            // InternalSTCoreParser.g:534:2: ( ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon ) | ( () otherlv_5= Semicolon ) | ( () otherlv_7= RETURN otherlv_8= Semicolon ) | ( () otherlv_10= CONTINUE otherlv_11= Semicolon ) | ( () otherlv_13= EXIT otherlv_14= Semicolon ) )
            int alt13=5;
            switch ( input.LA(1) ) {
            case REPEAT:
            case WHILE:
            case CASE:
            case FOR:
            case IF:
            case RULE_ID:
                {
                alt13=1;
                }
                break;
            case Semicolon:
                {
                alt13=2;
                }
                break;
            case RETURN:
                {
                alt13=3;
                }
                break;
            case CONTINUE:
                {
                alt13=4;
                }
                break;
            case EXIT:
                {
                alt13=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // InternalSTCoreParser.g:535:3: ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon )
                    {
                    // InternalSTCoreParser.g:535:3: ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon )
                    // InternalSTCoreParser.g:536:4: (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon
                    {
                    // InternalSTCoreParser.g:536:4: (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement )
                    int alt12=3;
                    switch ( input.LA(1) ) {
                    case CASE:
                    case IF:
                        {
                        alt12=1;
                        }
                        break;
                    case REPEAT:
                    case WHILE:
                    case FOR:
                        {
                        alt12=2;
                        }
                        break;
                    case RULE_ID:
                        {
                        alt12=3;
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
                            // InternalSTCoreParser.g:537:5: this_STBranchStatements_0= ruleSTBranchStatements
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTBranchStatementsParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STBranchStatements_0=ruleSTBranchStatements();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STBranchStatements_0;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:546:5: this_STLoopStatements_1= ruleSTLoopStatements
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTLoopStatementsParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STLoopStatements_1=ruleSTLoopStatements();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STLoopStatements_1;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 3 :
                            // InternalSTCoreParser.g:555:5: this_STAssignmentStatement_2= ruleSTAssignmentStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentStatementParserRuleCall_0_0_2());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STAssignmentStatement_2=ruleSTAssignmentStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STAssignmentStatement_2;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;

                    }

                    otherlv_3=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTStatementAccess().getSemicolonKeyword_0_1());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:570:3: ( () otherlv_5= Semicolon )
                    {
                    // InternalSTCoreParser.g:570:3: ( () otherlv_5= Semicolon )
                    // InternalSTCoreParser.g:571:4: () otherlv_5= Semicolon
                    {
                    // InternalSTCoreParser.g:571:4: ()
                    // InternalSTCoreParser.g:572:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTStatementsAction_1_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_5=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getSTStatementAccess().getSemicolonKeyword_1_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:584:3: ( () otherlv_7= RETURN otherlv_8= Semicolon )
                    {
                    // InternalSTCoreParser.g:584:3: ( () otherlv_7= RETURN otherlv_8= Semicolon )
                    // InternalSTCoreParser.g:585:4: () otherlv_7= RETURN otherlv_8= Semicolon
                    {
                    // InternalSTCoreParser.g:585:4: ()
                    // InternalSTCoreParser.g:586:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTReturnAction_2_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_7=(Token)match(input,RETURN,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getSTStatementAccess().getRETURNKeyword_2_1());
                      			
                    }
                    otherlv_8=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getSTStatementAccess().getSemicolonKeyword_2_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:602:3: ( () otherlv_10= CONTINUE otherlv_11= Semicolon )
                    {
                    // InternalSTCoreParser.g:602:3: ( () otherlv_10= CONTINUE otherlv_11= Semicolon )
                    // InternalSTCoreParser.g:603:4: () otherlv_10= CONTINUE otherlv_11= Semicolon
                    {
                    // InternalSTCoreParser.g:603:4: ()
                    // InternalSTCoreParser.g:604:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTContinueAction_3_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_10=(Token)match(input,CONTINUE,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_3_1());
                      			
                    }
                    otherlv_11=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_11, grammarAccess.getSTStatementAccess().getSemicolonKeyword_3_2());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:620:3: ( () otherlv_13= EXIT otherlv_14= Semicolon )
                    {
                    // InternalSTCoreParser.g:620:3: ( () otherlv_13= EXIT otherlv_14= Semicolon )
                    // InternalSTCoreParser.g:621:4: () otherlv_13= EXIT otherlv_14= Semicolon
                    {
                    // InternalSTCoreParser.g:621:4: ()
                    // InternalSTCoreParser.g:622:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTExitAction_4_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_13=(Token)match(input,EXIT,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getSTStatementAccess().getEXITKeyword_4_1());
                      			
                    }
                    otherlv_14=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getSTStatementAccess().getSemicolonKeyword_4_2());
                      			
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
    // InternalSTCoreParser.g:641:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTCoreParser.g:641:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTCoreParser.g:642:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTCoreParser.g:648:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_op_1_1=null;
        Token lv_op_1_2=null;
        EObject lv_rhs_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:654:2: ( ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) ) ) )
            // InternalSTCoreParser.g:655:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) ) )
            {
            // InternalSTCoreParser.g:655:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) ) )
            // InternalSTCoreParser.g:656:3: ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) )
            {
            // InternalSTCoreParser.g:656:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTCoreParser.g:657:4: (otherlv_0= RULE_ID )
            {
            // InternalSTCoreParser.g:657:4: (otherlv_0= RULE_ID )
            // InternalSTCoreParser.g:658:5: otherlv_0= RULE_ID
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

            // InternalSTCoreParser.g:669:3: ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) )
            // InternalSTCoreParser.g:670:4: ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) )
            {
            // InternalSTCoreParser.g:670:4: ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) )
            // InternalSTCoreParser.g:671:5: (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign )
            {
            // InternalSTCoreParser.g:671:5: (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ColonEqualsSign) ) {
                alt14=1;
            }
            else if ( (LA14_0==EqualsSignGreaterThanSign) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalSTCoreParser.g:672:6: lv_op_1_1= ColonEqualsSign
                    {
                    lv_op_1_1=(Token)match(input,ColonEqualsSign,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_1_1, grammarAccess.getSTAssignmentStatementAccess().getOpColonEqualsSignKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTAssignmentStatementRule());
                      						}
                      						setWithLastConsumed(current, "op", lv_op_1_1, null);
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:683:6: lv_op_1_2= EqualsSignGreaterThanSign
                    {
                    lv_op_1_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_1_2, grammarAccess.getSTAssignmentStatementAccess().getOpEqualsSignGreaterThanSignKeyword_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTAssignmentStatementRule());
                      						}
                      						setWithLastConsumed(current, "op", lv_op_1_2, null);
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalSTCoreParser.g:696:3: ( (lv_rhs_2_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:697:4: (lv_rhs_2_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:697:4: (lv_rhs_2_0= ruleSTExpression )
            // InternalSTCoreParser.g:698:5: lv_rhs_2_0= ruleSTExpression
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


    // $ANTLR start "entryRuleSTBranchStatements"
    // InternalSTCoreParser.g:719:1: entryRuleSTBranchStatements returns [EObject current=null] : iv_ruleSTBranchStatements= ruleSTBranchStatements EOF ;
    public final EObject entryRuleSTBranchStatements() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTBranchStatements = null;


        try {
            // InternalSTCoreParser.g:719:59: (iv_ruleSTBranchStatements= ruleSTBranchStatements EOF )
            // InternalSTCoreParser.g:720:2: iv_ruleSTBranchStatements= ruleSTBranchStatements EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTBranchStatementsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTBranchStatements=ruleSTBranchStatements();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTBranchStatements; 
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
    // $ANTLR end "entryRuleSTBranchStatements"


    // $ANTLR start "ruleSTBranchStatements"
    // InternalSTCoreParser.g:726:1: ruleSTBranchStatements returns [EObject current=null] : (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement ) ;
    public final EObject ruleSTBranchStatements() throws RecognitionException {
        EObject current = null;

        EObject this_STIfStatment_0 = null;

        EObject this_STCaseStatement_1 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:732:2: ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement ) )
            // InternalSTCoreParser.g:733:2: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement )
            {
            // InternalSTCoreParser.g:733:2: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==IF) ) {
                alt15=1;
            }
            else if ( (LA15_0==CASE) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalSTCoreParser.g:734:3: this_STIfStatment_0= ruleSTIfStatment
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTBranchStatementsAccess().getSTIfStatmentParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
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
                    // InternalSTCoreParser.g:743:3: this_STCaseStatement_1= ruleSTCaseStatement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTBranchStatementsAccess().getSTCaseStatementParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STCaseStatement_1=ruleSTCaseStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STCaseStatement_1;
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
    // $ANTLR end "ruleSTBranchStatements"


    // $ANTLR start "entryRuleSTIfStatment"
    // InternalSTCoreParser.g:755:1: entryRuleSTIfStatment returns [EObject current=null] : iv_ruleSTIfStatment= ruleSTIfStatment EOF ;
    public final EObject entryRuleSTIfStatment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatment = null;


        try {
            // InternalSTCoreParser.g:755:53: (iv_ruleSTIfStatment= ruleSTIfStatment EOF )
            // InternalSTCoreParser.g:756:2: iv_ruleSTIfStatment= ruleSTIfStatment EOF
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
    // InternalSTCoreParser.g:762:1: ruleSTIfStatment returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTCoreParser.g:768:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTCoreParser.g:769:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTCoreParser.g:769:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTCoreParser.g:770:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatmentAccess().getIFKeyword_0());
              		
            }
            // InternalSTCoreParser.g:774:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:775:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:775:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:776:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:797:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==CONTINUE||(LA16_0>=REPEAT && LA16_0<=RETURN)||LA16_0==WHILE||LA16_0==CASE||LA16_0==EXIT||LA16_0==FOR||LA16_0==IF||LA16_0==Semicolon||LA16_0==RULE_ID) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalSTCoreParser.g:798:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:798:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:799:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop16;
                }
            } while (true);

            // InternalSTCoreParser.g:816:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==ELSIF) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSTCoreParser.g:817:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTCoreParser.g:817:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTCoreParser.g:818:5: lv_elseifs_4_0= ruleSTElseIfPart
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
            	    break loop17;
                }
            } while (true);

            // InternalSTCoreParser.g:835:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==ELSE) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalSTCoreParser.g:836:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTCoreParser.g:836:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTCoreParser.g:837:5: lv_else_5_0= ruleSTElsePart
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
    // InternalSTCoreParser.g:862:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTCoreParser.g:862:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTCoreParser.g:863:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTCoreParser.g:869:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:875:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:876:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:876:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:877:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTCoreParser.g:881:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:882:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:882:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:883:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:904:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==CONTINUE||(LA19_0>=REPEAT && LA19_0<=RETURN)||LA19_0==WHILE||LA19_0==CASE||LA19_0==EXIT||LA19_0==FOR||LA19_0==IF||LA19_0==Semicolon||LA19_0==RULE_ID) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSTCoreParser.g:905:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:905:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:906:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop19;
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
    // InternalSTCoreParser.g:927:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTCoreParser.g:927:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTCoreParser.g:928:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTCoreParser.g:934:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTCoreParser.g:940:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTCoreParser.g:941:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTCoreParser.g:941:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTCoreParser.g:942:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTCoreParser.g:946:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:947:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:947:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:948:5: lv_selector_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:969:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=LDATE_AND_TIME && LA20_0<=TIME_OF_DAY)||LA20_0==WSTRING||(LA20_0>=STRING && LA20_0<=DWORD)||(LA20_0>=LDATE && LA20_0<=LWORD)||(LA20_0>=UDINT && LA20_0<=WCHAR)||(LA20_0>=BOOL && LA20_0<=DINT)||(LA20_0>=LINT && LA20_0<=UINT)||LA20_0==WORD||(LA20_0>=INT && LA20_0<=LDT)||LA20_0==TOD||LA20_0==DT||(LA20_0>=LD && LA20_0<=LT)||LA20_0==NOT||LA20_0==D||LA20_0==T||LA20_0==LeftParenthesis||LA20_0==PlusSign||LA20_0==HyphenMinus||LA20_0==RULE_BOOL_VALUES||(LA20_0>=RULE_NON_DECIMAL && LA20_0<=RULE_INT)||(LA20_0>=RULE_ID && LA20_0<=RULE_STRING)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSTCoreParser.g:970:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTCoreParser.g:970:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTCoreParser.g:971:5: lv_cases_3_0= ruleSTCaseCases
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
            	    if ( cnt20 >= 1 ) break loop20;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
            } while (true);

            // InternalSTCoreParser.g:988:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==ELSE) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSTCoreParser.g:989:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTCoreParser.g:989:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTCoreParser.g:990:5: lv_else_4_0= ruleSTElsePart
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
    // InternalSTCoreParser.g:1015:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTCoreParser.g:1015:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTCoreParser.g:1016:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTCoreParser.g:1022:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1028:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:1029:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:1029:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:1030:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:1030:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1031:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1031:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTCoreParser.g:1032:5: lv_conditions_0_0= ruleSTExpression
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

            // InternalSTCoreParser.g:1049:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==Comma) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalSTCoreParser.g:1050:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTCoreParser.g:1054:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTCoreParser.g:1055:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTCoreParser.g:1055:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTCoreParser.g:1056:6: lv_conditions_2_0= ruleSTExpression
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
            	    break loop22;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1078:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop23:
            do {
                int alt23=2;
                alt23 = dfa23.predict(input);
                switch (alt23) {
            	case 1 :
            	    // InternalSTCoreParser.g:1079:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1083:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1084:5: lv_statements_4_0= ruleSTStatement
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
            	    break loop23;
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
    // InternalSTCoreParser.g:1105:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTCoreParser.g:1105:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTCoreParser.g:1106:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTCoreParser.g:1112:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1118:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:1119:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:1119:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:1120:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:1120:3: ()
            // InternalSTCoreParser.g:1121:4: 
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
            // InternalSTCoreParser.g:1131:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==CONTINUE||(LA24_0>=REPEAT && LA24_0<=RETURN)||LA24_0==WHILE||LA24_0==CASE||LA24_0==EXIT||LA24_0==FOR||LA24_0==IF||LA24_0==Semicolon||LA24_0==RULE_ID) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalSTCoreParser.g:1132:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1132:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1133:5: lv_statements_2_0= ruleSTStatement
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
            	    break loop24;
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


    // $ANTLR start "entryRuleSTLoopStatements"
    // InternalSTCoreParser.g:1154:1: entryRuleSTLoopStatements returns [EObject current=null] : iv_ruleSTLoopStatements= ruleSTLoopStatements EOF ;
    public final EObject entryRuleSTLoopStatements() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLoopStatements = null;


        try {
            // InternalSTCoreParser.g:1154:57: (iv_ruleSTLoopStatements= ruleSTLoopStatements EOF )
            // InternalSTCoreParser.g:1155:2: iv_ruleSTLoopStatements= ruleSTLoopStatements EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTLoopStatementsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTLoopStatements=ruleSTLoopStatements();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTLoopStatements; 
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
    // $ANTLR end "entryRuleSTLoopStatements"


    // $ANTLR start "ruleSTLoopStatements"
    // InternalSTCoreParser.g:1161:1: ruleSTLoopStatements returns [EObject current=null] : (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement ) ;
    public final EObject ruleSTLoopStatements() throws RecognitionException {
        EObject current = null;

        EObject this_STForStatement_0 = null;

        EObject this_STWhileStatement_1 = null;

        EObject this_STRepeatStatement_2 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1167:2: ( (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement ) )
            // InternalSTCoreParser.g:1168:2: (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement )
            {
            // InternalSTCoreParser.g:1168:2: (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement )
            int alt25=3;
            switch ( input.LA(1) ) {
            case FOR:
                {
                alt25=1;
                }
                break;
            case WHILE:
                {
                alt25=2;
                }
                break;
            case REPEAT:
                {
                alt25=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // InternalSTCoreParser.g:1169:3: this_STForStatement_0= ruleSTForStatement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLoopStatementsAccess().getSTForStatementParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STForStatement_0=ruleSTForStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STForStatement_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:1178:3: this_STWhileStatement_1= ruleSTWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLoopStatementsAccess().getSTWhileStatementParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STWhileStatement_1=ruleSTWhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STWhileStatement_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:1187:3: this_STRepeatStatement_2= ruleSTRepeatStatement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLoopStatementsAccess().getSTRepeatStatementParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STRepeatStatement_2=ruleSTRepeatStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STRepeatStatement_2;
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
    // $ANTLR end "ruleSTLoopStatements"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalSTCoreParser.g:1199:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTCoreParser.g:1199:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTCoreParser.g:1200:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTCoreParser.g:1206:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) ;
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
            // InternalSTCoreParser.g:1212:2: ( (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) )
            // InternalSTCoreParser.g:1213:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            {
            // InternalSTCoreParser.g:1213:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            // InternalSTCoreParser.g:1214:3: otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1218:3: ( (lv_for_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1219:4: (lv_for_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1219:4: (lv_for_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1220:5: lv_for_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:1241:3: ( (lv_to_3_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1242:4: (lv_to_3_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1242:4: (lv_to_3_0= ruleSTExpression )
            // InternalSTCoreParser.g:1243:5: lv_to_3_0= ruleSTExpression
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

            // InternalSTCoreParser.g:1260:3: (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==BY) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalSTCoreParser.g:1261:4: otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) )
                    {
                    otherlv_4=(Token)match(input,BY,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getBYKeyword_4_0());
                      			
                    }
                    // InternalSTCoreParser.g:1265:4: ( (lv_by_5_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:1266:5: (lv_by_5_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:1266:5: (lv_by_5_0= ruleSTExpression )
                    // InternalSTCoreParser.g:1267:6: lv_by_5_0= ruleSTExpression
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
            // InternalSTCoreParser.g:1289:3: ( (lv_statements_7_0= ruleSTStatement ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==CONTINUE||(LA27_0>=REPEAT && LA27_0<=RETURN)||LA27_0==WHILE||LA27_0==CASE||LA27_0==EXIT||LA27_0==FOR||LA27_0==IF||LA27_0==Semicolon||LA27_0==RULE_ID) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSTCoreParser.g:1290:4: (lv_statements_7_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1290:4: (lv_statements_7_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1291:5: lv_statements_7_0= ruleSTStatement
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
            	    break loop27;
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
    // InternalSTCoreParser.g:1316:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTCoreParser.g:1316:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTCoreParser.g:1317:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTCoreParser.g:1323:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1329:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTCoreParser.g:1330:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTCoreParser.g:1330:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTCoreParser.g:1331:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1335:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1336:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1336:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1337:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:1358:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==CONTINUE||(LA28_0>=REPEAT && LA28_0<=RETURN)||LA28_0==WHILE||LA28_0==CASE||LA28_0==EXIT||LA28_0==FOR||LA28_0==IF||LA28_0==Semicolon||LA28_0==RULE_ID) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSTCoreParser.g:1359:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1359:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1360:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop28;
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
    // InternalSTCoreParser.g:1385:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTCoreParser.g:1385:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTCoreParser.g:1386:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTCoreParser.g:1392:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1398:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTCoreParser.g:1399:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTCoreParser.g:1399:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTCoreParser.g:1400:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1404:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==CONTINUE||(LA29_0>=REPEAT && LA29_0<=RETURN)||LA29_0==WHILE||LA29_0==CASE||LA29_0==EXIT||LA29_0==FOR||LA29_0==IF||LA29_0==Semicolon||LA29_0==RULE_ID) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSTCoreParser.g:1405:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1405:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1406:5: lv_statements_1_0= ruleSTStatement
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
            	    break loop29;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1427:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1428:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1428:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTCoreParser.g:1429:5: lv_condition_3_0= ruleSTExpression
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
    // InternalSTCoreParser.g:1454:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTCoreParser.g:1454:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTCoreParser.g:1455:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTCoreParser.g:1461:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1467:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTCoreParser.g:1468:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTCoreParser.g:1479:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTCoreParser.g:1479:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTCoreParser.g:1480:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTCoreParser.g:1486:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_STOrExpression_0 = null;

        EObject lv_upperBound_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1492:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTCoreParser.g:1493:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1493:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTCoreParser.g:1494:3: this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )*
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
            // InternalSTCoreParser.g:1502:3: ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==FullStopFullStop) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSTCoreParser.g:1503:4: ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1503:4: ( () otherlv_2= FullStopFullStop )
            	    // InternalSTCoreParser.g:1504:5: () otherlv_2= FullStopFullStop
            	    {
            	    // InternalSTCoreParser.g:1504:5: ()
            	    // InternalSTCoreParser.g:1505:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTSubrangeExpressionLowerBoundAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStopFullStop,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTSubrangeExpressionAccess().getFullStopFullStopKeyword_1_0_1());
            	      				
            	    }

            	    }

            	    // InternalSTCoreParser.g:1516:4: ( (lv_upperBound_3_0= ruleSTOrExpression ) )
            	    // InternalSTCoreParser.g:1517:5: (lv_upperBound_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTCoreParser.g:1517:5: (lv_upperBound_3_0= ruleSTOrExpression )
            	    // InternalSTCoreParser.g:1518:6: lv_upperBound_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getUpperBoundSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_36);
            	    lv_upperBound_3_0=ruleSTOrExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTSubrangeExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"upperBound",
            	      							lv_upperBound_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STOrExpression");
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalSTCoreParser.g:1540:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTCoreParser.g:1540:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTCoreParser.g:1541:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTCoreParser.g:1547:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_STXorExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1553:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTCoreParser.g:1554:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1554:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTCoreParser.g:1555:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
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
            // InternalSTCoreParser.g:1563:3: ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==OR) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTCoreParser.g:1564:4: ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1564:4: ( () ( (lv_op_2_0= OR ) ) )
            	    // InternalSTCoreParser.g:1565:5: () ( (lv_op_2_0= OR ) )
            	    {
            	    // InternalSTCoreParser.g:1565:5: ()
            	    // InternalSTCoreParser.g:1566:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTOrExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1572:5: ( (lv_op_2_0= OR ) )
            	    // InternalSTCoreParser.g:1573:6: (lv_op_2_0= OR )
            	    {
            	    // InternalSTCoreParser.g:1573:6: (lv_op_2_0= OR )
            	    // InternalSTCoreParser.g:1574:7: lv_op_2_0= OR
            	    {
            	    lv_op_2_0=(Token)match(input,OR,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							newLeafNode(lv_op_2_0, grammarAccess.getSTOrExpressionAccess().getOpORKeyword_1_0_1_0());
            	      						
            	    }
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElement(grammarAccess.getSTOrExpressionRule());
            	      							}
            	      							setWithLastConsumed(current, "op", lv_op_2_0, "OR");
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1587:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTCoreParser.g:1588:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTCoreParser.g:1588:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTCoreParser.g:1589:6: lv_right_3_0= ruleSTXorExpression
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalSTCoreParser.g:1611:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTCoreParser.g:1611:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTCoreParser.g:1612:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTCoreParser.g:1618:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_STAndExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1624:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTCoreParser.g:1625:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1625:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTCoreParser.g:1626:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
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
            // InternalSTCoreParser.g:1634:3: ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==XOR) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSTCoreParser.g:1635:4: ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1635:4: ( () ( (lv_op_2_0= XOR ) ) )
            	    // InternalSTCoreParser.g:1636:5: () ( (lv_op_2_0= XOR ) )
            	    {
            	    // InternalSTCoreParser.g:1636:5: ()
            	    // InternalSTCoreParser.g:1637:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTXorExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1643:5: ( (lv_op_2_0= XOR ) )
            	    // InternalSTCoreParser.g:1644:6: (lv_op_2_0= XOR )
            	    {
            	    // InternalSTCoreParser.g:1644:6: (lv_op_2_0= XOR )
            	    // InternalSTCoreParser.g:1645:7: lv_op_2_0= XOR
            	    {
            	    lv_op_2_0=(Token)match(input,XOR,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							newLeafNode(lv_op_2_0, grammarAccess.getSTXorExpressionAccess().getOpXORKeyword_1_0_1_0());
            	      						
            	    }
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElement(grammarAccess.getSTXorExpressionRule());
            	      							}
            	      							setWithLastConsumed(current, "op", lv_op_2_0, "XOR");
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1658:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTCoreParser.g:1659:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTCoreParser.g:1659:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTCoreParser.g:1660:6: lv_right_3_0= ruleSTAndExpression
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalSTCoreParser.g:1682:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTCoreParser.g:1682:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTCoreParser.g:1683:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTCoreParser.g:1689:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_STEqualityExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1695:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTCoreParser.g:1696:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1696:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTCoreParser.g:1697:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
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
            // InternalSTCoreParser.g:1705:3: ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==AND||LA34_0==Ampersand) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTCoreParser.g:1706:4: ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1706:4: ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) )
            	    // InternalSTCoreParser.g:1707:5: () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) )
            	    {
            	    // InternalSTCoreParser.g:1707:5: ()
            	    // InternalSTCoreParser.g:1708:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTAndExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1714:5: ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) )
            	    // InternalSTCoreParser.g:1715:6: ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) )
            	    {
            	    // InternalSTCoreParser.g:1715:6: ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) )
            	    // InternalSTCoreParser.g:1716:7: (lv_op_2_1= Ampersand | lv_op_2_2= AND )
            	    {
            	    // InternalSTCoreParser.g:1716:7: (lv_op_2_1= Ampersand | lv_op_2_2= AND )
            	    int alt33=2;
            	    int LA33_0 = input.LA(1);

            	    if ( (LA33_0==Ampersand) ) {
            	        alt33=1;
            	    }
            	    else if ( (LA33_0==AND) ) {
            	        alt33=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 33, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt33) {
            	        case 1 :
            	            // InternalSTCoreParser.g:1717:8: lv_op_2_1= Ampersand
            	            {
            	            lv_op_2_1=(Token)match(input,Ampersand,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getSTAndExpressionAccess().getOpAmpersandKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTAndExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:1728:8: lv_op_2_2= AND
            	            {
            	            lv_op_2_2=(Token)match(input,AND,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getSTAndExpressionAccess().getOpANDKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTAndExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1742:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTCoreParser.g:1743:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTCoreParser.g:1743:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTCoreParser.g:1744:6: lv_right_3_0= ruleSTEqualityExpression
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalSTCoreParser.g:1766:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTCoreParser.g:1766:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTCoreParser.g:1767:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTCoreParser.g:1773:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_STComparisonExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1779:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTCoreParser.g:1780:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1780:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTCoreParser.g:1781:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
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
            // InternalSTCoreParser.g:1789:3: ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==LessThanSignGreaterThanSign||LA36_0==EqualsSign) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSTCoreParser.g:1790:4: ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1790:4: ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) )
            	    // InternalSTCoreParser.g:1791:5: () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) )
            	    {
            	    // InternalSTCoreParser.g:1791:5: ()
            	    // InternalSTCoreParser.g:1792:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTEqualityExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1798:5: ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) )
            	    // InternalSTCoreParser.g:1799:6: ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) )
            	    {
            	    // InternalSTCoreParser.g:1799:6: ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) )
            	    // InternalSTCoreParser.g:1800:7: (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign )
            	    {
            	    // InternalSTCoreParser.g:1800:7: (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign )
            	    int alt35=2;
            	    int LA35_0 = input.LA(1);

            	    if ( (LA35_0==EqualsSign) ) {
            	        alt35=1;
            	    }
            	    else if ( (LA35_0==LessThanSignGreaterThanSign) ) {
            	        alt35=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 35, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt35) {
            	        case 1 :
            	            // InternalSTCoreParser.g:1801:8: lv_op_2_1= EqualsSign
            	            {
            	            lv_op_2_1=(Token)match(input,EqualsSign,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getSTEqualityExpressionAccess().getOpEqualsSignKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTEqualityExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:1812:8: lv_op_2_2= LessThanSignGreaterThanSign
            	            {
            	            lv_op_2_2=(Token)match(input,LessThanSignGreaterThanSign,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getSTEqualityExpressionAccess().getOpLessThanSignGreaterThanSignKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTEqualityExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1826:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTCoreParser.g:1827:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTCoreParser.g:1827:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTCoreParser.g:1828:6: lv_right_3_0= ruleSTComparisonExpression
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
            	    break loop36;
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
    // InternalSTCoreParser.g:1850:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTCoreParser.g:1850:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTCoreParser.g:1851:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTCoreParser.g:1857:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        Token lv_op_2_3=null;
        Token lv_op_2_4=null;
        EObject this_STAddSubExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1863:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTCoreParser.g:1864:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1864:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTCoreParser.g:1865:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
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
            // InternalSTCoreParser.g:1873:3: ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==LessThanSignEqualsSign||LA38_0==GreaterThanSignEqualsSign||LA38_0==LessThanSign||LA38_0==GreaterThanSign) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSTCoreParser.g:1874:4: ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1874:4: ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) )
            	    // InternalSTCoreParser.g:1875:5: () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) )
            	    {
            	    // InternalSTCoreParser.g:1875:5: ()
            	    // InternalSTCoreParser.g:1876:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTComparisonExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1882:5: ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) )
            	    // InternalSTCoreParser.g:1883:6: ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) )
            	    {
            	    // InternalSTCoreParser.g:1883:6: ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) )
            	    // InternalSTCoreParser.g:1884:7: (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign )
            	    {
            	    // InternalSTCoreParser.g:1884:7: (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign )
            	    int alt37=4;
            	    switch ( input.LA(1) ) {
            	    case LessThanSign:
            	        {
            	        alt37=1;
            	        }
            	        break;
            	    case GreaterThanSign:
            	        {
            	        alt37=2;
            	        }
            	        break;
            	    case LessThanSignEqualsSign:
            	        {
            	        alt37=3;
            	        }
            	        break;
            	    case GreaterThanSignEqualsSign:
            	        {
            	        alt37=4;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 37, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt37) {
            	        case 1 :
            	            // InternalSTCoreParser.g:1885:8: lv_op_2_1= LessThanSign
            	            {
            	            lv_op_2_1=(Token)match(input,LessThanSign,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getSTComparisonExpressionAccess().getOpLessThanSignKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTComparisonExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:1896:8: lv_op_2_2= GreaterThanSign
            	            {
            	            lv_op_2_2=(Token)match(input,GreaterThanSign,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getSTComparisonExpressionAccess().getOpGreaterThanSignKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTComparisonExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTCoreParser.g:1907:8: lv_op_2_3= LessThanSignEqualsSign
            	            {
            	            lv_op_2_3=(Token)match(input,LessThanSignEqualsSign,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_3, grammarAccess.getSTComparisonExpressionAccess().getOpLessThanSignEqualsSignKeyword_1_0_1_0_2());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTComparisonExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_3, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTCoreParser.g:1918:8: lv_op_2_4= GreaterThanSignEqualsSign
            	            {
            	            lv_op_2_4=(Token)match(input,GreaterThanSignEqualsSign,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_4, grammarAccess.getSTComparisonExpressionAccess().getOpGreaterThanSignEqualsSignKeyword_1_0_1_0_3());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTComparisonExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_4, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:1932:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTCoreParser.g:1933:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTCoreParser.g:1933:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTCoreParser.g:1934:6: lv_right_3_0= ruleSTAddSubExpression
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
            	    break loop38;
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
    // InternalSTCoreParser.g:1956:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTCoreParser.g:1956:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTCoreParser.g:1957:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTCoreParser.g:1963:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_STMulDivModExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1969:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTCoreParser.g:1970:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1970:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTCoreParser.g:1971:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
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
            // InternalSTCoreParser.g:1979:3: ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==PlusSign||LA40_0==HyphenMinus) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSTCoreParser.g:1980:4: ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1980:4: ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) )
            	    // InternalSTCoreParser.g:1981:5: () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) )
            	    {
            	    // InternalSTCoreParser.g:1981:5: ()
            	    // InternalSTCoreParser.g:1982:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTAddSubExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1988:5: ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) )
            	    // InternalSTCoreParser.g:1989:6: ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) )
            	    {
            	    // InternalSTCoreParser.g:1989:6: ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) )
            	    // InternalSTCoreParser.g:1990:7: (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus )
            	    {
            	    // InternalSTCoreParser.g:1990:7: (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus )
            	    int alt39=2;
            	    int LA39_0 = input.LA(1);

            	    if ( (LA39_0==PlusSign) ) {
            	        alt39=1;
            	    }
            	    else if ( (LA39_0==HyphenMinus) ) {
            	        alt39=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 39, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt39) {
            	        case 1 :
            	            // InternalSTCoreParser.g:1991:8: lv_op_2_1= PlusSign
            	            {
            	            lv_op_2_1=(Token)match(input,PlusSign,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getSTAddSubExpressionAccess().getOpPlusSignKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTAddSubExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:2002:8: lv_op_2_2= HyphenMinus
            	            {
            	            lv_op_2_2=(Token)match(input,HyphenMinus,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getSTAddSubExpressionAccess().getOpHyphenMinusKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTAddSubExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:2016:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTCoreParser.g:2017:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTCoreParser.g:2017:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTCoreParser.g:2018:6: lv_right_3_0= ruleSTMulDivModExpression
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
            	    break loop40;
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
    // InternalSTCoreParser.g:2040:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTCoreParser.g:2040:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTCoreParser.g:2041:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTCoreParser.g:2047:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        Token lv_op_2_3=null;
        EObject this_STPowerExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2053:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTCoreParser.g:2054:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2054:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTCoreParser.g:2055:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
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
            // InternalSTCoreParser.g:2063:3: ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==MOD||LA42_0==Asterisk||LA42_0==Solidus) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSTCoreParser.g:2064:4: ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2064:4: ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) )
            	    // InternalSTCoreParser.g:2065:5: () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) )
            	    {
            	    // InternalSTCoreParser.g:2065:5: ()
            	    // InternalSTCoreParser.g:2066:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTMulDivModExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2072:5: ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) )
            	    // InternalSTCoreParser.g:2073:6: ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) )
            	    {
            	    // InternalSTCoreParser.g:2073:6: ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) )
            	    // InternalSTCoreParser.g:2074:7: (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD )
            	    {
            	    // InternalSTCoreParser.g:2074:7: (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD )
            	    int alt41=3;
            	    switch ( input.LA(1) ) {
            	    case Asterisk:
            	        {
            	        alt41=1;
            	        }
            	        break;
            	    case Solidus:
            	        {
            	        alt41=2;
            	        }
            	        break;
            	    case MOD:
            	        {
            	        alt41=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 41, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt41) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2075:8: lv_op_2_1= Asterisk
            	            {
            	            lv_op_2_1=(Token)match(input,Asterisk,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getSTMulDivModExpressionAccess().getOpAsteriskKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTMulDivModExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:2086:8: lv_op_2_2= Solidus
            	            {
            	            lv_op_2_2=(Token)match(input,Solidus,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getSTMulDivModExpressionAccess().getOpSolidusKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTMulDivModExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTCoreParser.g:2097:8: lv_op_2_3= MOD
            	            {
            	            lv_op_2_3=(Token)match(input,MOD,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_3, grammarAccess.getSTMulDivModExpressionAccess().getOpMODKeyword_1_0_1_0_2());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTMulDivModExpressionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_3, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:2111:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTCoreParser.g:2112:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTCoreParser.g:2112:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTCoreParser.g:2113:6: lv_right_3_0= ruleSTPowerExpression
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
            	    break loop42;
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
    // InternalSTCoreParser.g:2135:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTCoreParser.g:2135:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTCoreParser.g:2136:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTCoreParser.g:2142:1: ruleSTPowerExpression returns [EObject current=null] : (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_STSignumExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2148:2: ( (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* ) )
            // InternalSTCoreParser.g:2149:2: (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2149:2: (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* )
            // InternalSTCoreParser.g:2150:3: this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )*
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
            // InternalSTCoreParser.g:2158:3: ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==AsteriskAsterisk) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSTCoreParser.g:2159:4: ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2159:4: ( () ( (lv_op_2_0= AsteriskAsterisk ) ) )
            	    // InternalSTCoreParser.g:2160:5: () ( (lv_op_2_0= AsteriskAsterisk ) )
            	    {
            	    // InternalSTCoreParser.g:2160:5: ()
            	    // InternalSTCoreParser.g:2161:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTPowerExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2167:5: ( (lv_op_2_0= AsteriskAsterisk ) )
            	    // InternalSTCoreParser.g:2168:6: (lv_op_2_0= AsteriskAsterisk )
            	    {
            	    // InternalSTCoreParser.g:2168:6: (lv_op_2_0= AsteriskAsterisk )
            	    // InternalSTCoreParser.g:2169:7: lv_op_2_0= AsteriskAsterisk
            	    {
            	    lv_op_2_0=(Token)match(input,AsteriskAsterisk,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							newLeafNode(lv_op_2_0, grammarAccess.getSTPowerExpressionAccess().getOpAsteriskAsteriskKeyword_1_0_1_0());
            	      						
            	    }
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElement(grammarAccess.getSTPowerExpressionRule());
            	      							}
            	      							setWithLastConsumed(current, "op", lv_op_2_0, "**");
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalSTCoreParser.g:2182:4: ( (lv_right_3_0= ruleSTSignumExpression ) )
            	    // InternalSTCoreParser.g:2183:5: (lv_right_3_0= ruleSTSignumExpression )
            	    {
            	    // InternalSTCoreParser.g:2183:5: (lv_right_3_0= ruleSTSignumExpression )
            	    // InternalSTCoreParser.g:2184:6: lv_right_3_0= ruleSTSignumExpression
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTSignumExpression"
    // InternalSTCoreParser.g:2206:1: entryRuleSTSignumExpression returns [EObject current=null] : iv_ruleSTSignumExpression= ruleSTSignumExpression EOF ;
    public final EObject entryRuleSTSignumExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignumExpression = null;


        try {
            // InternalSTCoreParser.g:2206:59: (iv_ruleSTSignumExpression= ruleSTSignumExpression EOF )
            // InternalSTCoreParser.g:2207:2: iv_ruleSTSignumExpression= ruleSTSignumExpression EOF
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
    // InternalSTCoreParser.g:2213:1: ruleSTSignumExpression returns [EObject current=null] : (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) ) ;
    public final EObject ruleSTSignumExpression() throws RecognitionException {
        EObject current = null;

        Token lv_signum_3_1=null;
        Token lv_signum_3_2=null;
        Token lv_signum_3_3=null;
        EObject this_STLiteralExpressions_0 = null;

        EObject this_STSelectionExpression_1 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2219:2: ( (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) ) )
            // InternalSTCoreParser.g:2220:2: (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) )
            {
            // InternalSTCoreParser.g:2220:2: (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) )
            int alt45=3;
            switch ( input.LA(1) ) {
            case NOT:
                {
                int LA45_1 = input.LA(2);

                if ( (LA45_1==DWORD||LA45_1==LREAL||LA45_1==LWORD||(LA45_1>=UDINT && LA45_1<=USINT)||(LA45_1>=BOOL && LA45_1<=BYTE)||LA45_1==DINT||LA45_1==LINT||(LA45_1>=REAL && LA45_1<=SINT)||LA45_1==UINT||LA45_1==WORD||LA45_1==INT||LA45_1==PlusSign||LA45_1==HyphenMinus||LA45_1==RULE_BOOL_VALUES||(LA45_1>=RULE_NON_DECIMAL && LA45_1<=RULE_INT)) ) {
                    alt45=1;
                }
                else if ( (LA45_1==LeftParenthesis||LA45_1==RULE_ID) ) {
                    alt45=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 45, 1, input);

                    throw nvae;
                }
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
            case DT:
            case LD:
            case LT:
            case D:
            case T:
            case RULE_BOOL_VALUES:
            case RULE_NON_DECIMAL:
            case RULE_INT:
            case RULE_STRING:
                {
                alt45=1;
                }
                break;
            case PlusSign:
                {
                int LA45_3 = input.LA(2);

                if ( (LA45_3==RULE_INT) ) {
                    alt45=1;
                }
                else if ( (LA45_3==LeftParenthesis||LA45_3==RULE_ID) ) {
                    alt45=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 45, 3, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA45_4 = input.LA(2);

                if ( (LA45_4==LeftParenthesis||LA45_4==RULE_ID) ) {
                    alt45=3;
                }
                else if ( (LA45_4==RULE_INT) ) {
                    alt45=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 45, 4, input);

                    throw nvae;
                }
                }
                break;
            case LeftParenthesis:
            case RULE_ID:
                {
                alt45=2;
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
                    // InternalSTCoreParser.g:2221:3: this_STLiteralExpressions_0= ruleSTLiteralExpressions
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
                    // InternalSTCoreParser.g:2230:3: this_STSelectionExpression_1= ruleSTSelectionExpression
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
                    // InternalSTCoreParser.g:2239:3: ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) )
                    {
                    // InternalSTCoreParser.g:2239:3: ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) )
                    // InternalSTCoreParser.g:2240:4: () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) )
                    {
                    // InternalSTCoreParser.g:2240:4: ()
                    // InternalSTCoreParser.g:2241:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTSignumExpressionAccess().getSTSignumExpressionAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2247:4: ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) )
                    // InternalSTCoreParser.g:2248:5: ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) )
                    {
                    // InternalSTCoreParser.g:2248:5: ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) )
                    // InternalSTCoreParser.g:2249:6: (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT )
                    {
                    // InternalSTCoreParser.g:2249:6: (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT )
                    int alt44=3;
                    switch ( input.LA(1) ) {
                    case HyphenMinus:
                        {
                        alt44=1;
                        }
                        break;
                    case PlusSign:
                        {
                        alt44=2;
                        }
                        break;
                    case NOT:
                        {
                        alt44=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 44, 0, input);

                        throw nvae;
                    }

                    switch (alt44) {
                        case 1 :
                            // InternalSTCoreParser.g:2250:7: lv_signum_3_1= HyphenMinus
                            {
                            lv_signum_3_1=(Token)match(input,HyphenMinus,FOLLOW_45); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_signum_3_1, grammarAccess.getSTSignumExpressionAccess().getSignumHyphenMinusKeyword_2_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getSTSignumExpressionRule());
                              							}
                              							setWithLastConsumed(current, "signum", lv_signum_3_1, null);
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:2261:7: lv_signum_3_2= PlusSign
                            {
                            lv_signum_3_2=(Token)match(input,PlusSign,FOLLOW_45); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_signum_3_2, grammarAccess.getSTSignumExpressionAccess().getSignumPlusSignKeyword_2_1_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getSTSignumExpressionRule());
                              							}
                              							setWithLastConsumed(current, "signum", lv_signum_3_2, null);
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalSTCoreParser.g:2272:7: lv_signum_3_3= NOT
                            {
                            lv_signum_3_3=(Token)match(input,NOT,FOLLOW_45); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_signum_3_3, grammarAccess.getSTSignumExpressionAccess().getSignumNOTKeyword_2_1_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getSTSignumExpressionRule());
                              							}
                              							setWithLastConsumed(current, "signum", lv_signum_3_3, null);
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalSTCoreParser.g:2285:4: ( (lv_expression_4_0= ruleSTSelectionExpression ) )
                    // InternalSTCoreParser.g:2286:5: (lv_expression_4_0= ruleSTSelectionExpression )
                    {
                    // InternalSTCoreParser.g:2286:5: (lv_expression_4_0= ruleSTSelectionExpression )
                    // InternalSTCoreParser.g:2287:6: lv_expression_4_0= ruleSTSelectionExpression
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
    // InternalSTCoreParser.g:2309:1: entryRuleSTSelectionExpression returns [EObject current=null] : iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF ;
    public final EObject entryRuleSTSelectionExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSelectionExpression = null;


        try {
            // InternalSTCoreParser.g:2309:62: (iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF )
            // InternalSTCoreParser.g:2310:2: iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF
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
    // InternalSTCoreParser.g:2316:1: ruleSTSelectionExpression returns [EObject current=null] : (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* ) ;
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
            // InternalSTCoreParser.g:2322:2: ( (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* ) )
            // InternalSTCoreParser.g:2323:2: (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* )
            {
            // InternalSTCoreParser.g:2323:2: (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* )
            // InternalSTCoreParser.g:2324:3: this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )*
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
            // InternalSTCoreParser.g:2332:3: ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==FullStop||LA52_0==LeftSquareBracket) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalSTCoreParser.g:2333:4: () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )?
            	    {
            	    // InternalSTCoreParser.g:2333:4: ()
            	    // InternalSTCoreParser.g:2334:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getSTSelectionExpressionAccess().getSTMemberSelectionReceiverAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }

            	    // InternalSTCoreParser.g:2340:4: ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) )
            	    int alt47=2;
            	    int LA47_0 = input.LA(1);

            	    if ( (LA47_0==FullStop) ) {
            	        alt47=1;
            	    }
            	    else if ( (LA47_0==LeftSquareBracket) ) {
            	        alt47=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 47, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt47) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2341:5: ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) )
            	            {
            	            // InternalSTCoreParser.g:2341:5: ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) )
            	            // InternalSTCoreParser.g:2342:6: ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) )
            	            {
            	            // InternalSTCoreParser.g:2342:6: ( (lv_structAccess_2_0= FullStop ) )
            	            // InternalSTCoreParser.g:2343:7: (lv_structAccess_2_0= FullStop )
            	            {
            	            // InternalSTCoreParser.g:2343:7: (lv_structAccess_2_0= FullStop )
            	            // InternalSTCoreParser.g:2344:8: lv_structAccess_2_0= FullStop
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

            	            // InternalSTCoreParser.g:2356:6: ( (otherlv_3= RULE_ID ) )
            	            // InternalSTCoreParser.g:2357:7: (otherlv_3= RULE_ID )
            	            {
            	            // InternalSTCoreParser.g:2357:7: (otherlv_3= RULE_ID )
            	            // InternalSTCoreParser.g:2358:8: otherlv_3= RULE_ID
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
            	            // InternalSTCoreParser.g:2371:5: ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket )
            	            {
            	            // InternalSTCoreParser.g:2371:5: ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket )
            	            // InternalSTCoreParser.g:2372:6: ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket
            	            {
            	            // InternalSTCoreParser.g:2372:6: ( (lv_arrayAccess_4_0= LeftSquareBracket ) )
            	            // InternalSTCoreParser.g:2373:7: (lv_arrayAccess_4_0= LeftSquareBracket )
            	            {
            	            // InternalSTCoreParser.g:2373:7: (lv_arrayAccess_4_0= LeftSquareBracket )
            	            // InternalSTCoreParser.g:2374:8: lv_arrayAccess_4_0= LeftSquareBracket
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

            	            // InternalSTCoreParser.g:2386:6: ( (lv_index_5_0= ruleSTExpression ) )
            	            // InternalSTCoreParser.g:2387:7: (lv_index_5_0= ruleSTExpression )
            	            {
            	            // InternalSTCoreParser.g:2387:7: (lv_index_5_0= ruleSTExpression )
            	            // InternalSTCoreParser.g:2388:8: lv_index_5_0= ruleSTExpression
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

            	            // InternalSTCoreParser.g:2405:6: (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )*
            	            loop46:
            	            do {
            	                int alt46=2;
            	                int LA46_0 = input.LA(1);

            	                if ( (LA46_0==Comma) ) {
            	                    alt46=1;
            	                }


            	                switch (alt46) {
            	            	case 1 :
            	            	    // InternalSTCoreParser.g:2406:7: otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) )
            	            	    {
            	            	    otherlv_6=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	            	    if ( state.backtracking==0 ) {

            	            	      							newLeafNode(otherlv_6, grammarAccess.getSTSelectionExpressionAccess().getCommaKeyword_1_1_1_2_0());
            	            	      						
            	            	    }
            	            	    // InternalSTCoreParser.g:2410:7: ( (lv_index_7_0= ruleSTExpression ) )
            	            	    // InternalSTCoreParser.g:2411:8: (lv_index_7_0= ruleSTExpression )
            	            	    {
            	            	    // InternalSTCoreParser.g:2411:8: (lv_index_7_0= ruleSTExpression )
            	            	    // InternalSTCoreParser.g:2412:9: lv_index_7_0= ruleSTExpression
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
            	            	    break loop46;
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

            	    // InternalSTCoreParser.g:2436:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )?
            	    int alt50=2;
            	    alt50 = dfa50.predict(input);
            	    switch (alt50) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2437:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis
            	            {
            	            // InternalSTCoreParser.g:2437:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) )
            	            // InternalSTCoreParser.g:2438:6: ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis )
            	            {
            	            // InternalSTCoreParser.g:2442:6: (lv_poeInvocation_9_0= LeftParenthesis )
            	            // InternalSTCoreParser.g:2443:7: lv_poeInvocation_9_0= LeftParenthesis
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

            	            // InternalSTCoreParser.g:2455:5: ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )?
            	            int alt49=2;
            	            int LA49_0 = input.LA(1);

            	            if ( ((LA49_0>=LDATE_AND_TIME && LA49_0<=TIME_OF_DAY)||LA49_0==WSTRING||(LA49_0>=STRING && LA49_0<=DWORD)||(LA49_0>=LDATE && LA49_0<=LWORD)||(LA49_0>=UDINT && LA49_0<=WCHAR)||(LA49_0>=BOOL && LA49_0<=DINT)||(LA49_0>=LINT && LA49_0<=UINT)||LA49_0==WORD||(LA49_0>=INT && LA49_0<=LDT)||LA49_0==TOD||LA49_0==DT||(LA49_0>=LD && LA49_0<=LT)||LA49_0==NOT||LA49_0==D||LA49_0==T||LA49_0==LeftParenthesis||LA49_0==PlusSign||LA49_0==HyphenMinus||LA49_0==RULE_BOOL_VALUES||(LA49_0>=RULE_NON_DECIMAL && LA49_0<=RULE_INT)||(LA49_0>=RULE_ID && LA49_0<=RULE_STRING)) ) {
            	                alt49=1;
            	            }
            	            switch (alt49) {
            	                case 1 :
            	                    // InternalSTCoreParser.g:2456:6: ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )*
            	                    {
            	                    // InternalSTCoreParser.g:2456:6: ( (lv_parameters_10_0= ruleSTExpression ) )
            	                    // InternalSTCoreParser.g:2457:7: (lv_parameters_10_0= ruleSTExpression )
            	                    {
            	                    // InternalSTCoreParser.g:2457:7: (lv_parameters_10_0= ruleSTExpression )
            	                    // InternalSTCoreParser.g:2458:8: lv_parameters_10_0= ruleSTExpression
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

            	                    // InternalSTCoreParser.g:2475:6: (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )*
            	                    loop48:
            	                    do {
            	                        int alt48=2;
            	                        int LA48_0 = input.LA(1);

            	                        if ( (LA48_0==Comma) ) {
            	                            alt48=1;
            	                        }


            	                        switch (alt48) {
            	                    	case 1 :
            	                    	    // InternalSTCoreParser.g:2476:7: otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) )
            	                    	    {
            	                    	    otherlv_11=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	                    	    if ( state.backtracking==0 ) {

            	                    	      							newLeafNode(otherlv_11, grammarAccess.getSTSelectionExpressionAccess().getCommaKeyword_1_2_1_1_0());
            	                    	      						
            	                    	    }
            	                    	    // InternalSTCoreParser.g:2480:7: ( (lv_parameters_12_0= ruleSTExpression ) )
            	                    	    // InternalSTCoreParser.g:2481:8: (lv_parameters_12_0= ruleSTExpression )
            	                    	    {
            	                    	    // InternalSTCoreParser.g:2481:8: (lv_parameters_12_0= ruleSTExpression )
            	                    	    // InternalSTCoreParser.g:2482:9: lv_parameters_12_0= ruleSTExpression
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
            	                    	    break loop48;
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

            	    // InternalSTCoreParser.g:2506:4: ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )?
            	    int alt51=2;
            	    int LA51_0 = input.LA(1);

            	    if ( (LA51_0==L) && (synpred3_InternalSTCoreParser())) {
            	        alt51=1;
            	    }
            	    else if ( (LA51_0==D_1) && (synpred3_InternalSTCoreParser())) {
            	        alt51=1;
            	    }
            	    else if ( (LA51_0==W) && (synpred3_InternalSTCoreParser())) {
            	        alt51=1;
            	    }
            	    else if ( (LA51_0==B) && (synpred3_InternalSTCoreParser())) {
            	        alt51=1;
            	    }
            	    else if ( (LA51_0==X) && (synpred3_InternalSTCoreParser())) {
            	        alt51=1;
            	    }
            	    else if ( (LA51_0==FullStop) ) {
            	        int LA51_6 = input.LA(2);

            	        if ( (LA51_6==RULE_INT) && (synpred3_InternalSTCoreParser())) {
            	            alt51=1;
            	        }
            	    }
            	    switch (alt51) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2507:5: ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess )
            	            {
            	            // InternalSTCoreParser.g:2511:5: (lv_bitaccessor_14_0= ruleMultibitPartialAccess )
            	            // InternalSTCoreParser.g:2512:6: lv_bitaccessor_14_0= ruleMultibitPartialAccess
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
            	    break loop52;
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
    // InternalSTCoreParser.g:2534:1: entryRuleMultibitPartialAccess returns [EObject current=null] : iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF ;
    public final EObject entryRuleMultibitPartialAccess() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultibitPartialAccess = null;


        try {
            // InternalSTCoreParser.g:2534:62: (iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF )
            // InternalSTCoreParser.g:2535:2: iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF
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
    // InternalSTCoreParser.g:2541:1: ruleMultibitPartialAccess returns [EObject current=null] : ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) ) ;
    public final EObject ruleMultibitPartialAccess() throws RecognitionException {
        EObject current = null;

        Token lv_index_1_0=null;
        Enumerator lv_accessSpecifier_0_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2547:2: ( ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) ) )
            // InternalSTCoreParser.g:2548:2: ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) )
            {
            // InternalSTCoreParser.g:2548:2: ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) )
            // InternalSTCoreParser.g:2549:3: ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) )
            {
            // InternalSTCoreParser.g:2549:3: ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) )
            // InternalSTCoreParser.g:2550:4: (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier )
            {
            // InternalSTCoreParser.g:2550:4: (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier )
            // InternalSTCoreParser.g:2551:5: lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier
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

            // InternalSTCoreParser.g:2568:3: ( (lv_index_1_0= RULE_INT ) )
            // InternalSTCoreParser.g:2569:4: (lv_index_1_0= RULE_INT )
            {
            // InternalSTCoreParser.g:2569:4: (lv_index_1_0= RULE_INT )
            // InternalSTCoreParser.g:2570:5: lv_index_1_0= RULE_INT
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
    // InternalSTCoreParser.g:2590:1: entryRuleSTAtomicExpression returns [EObject current=null] : iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF ;
    public final EObject entryRuleSTAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAtomicExpression = null;


        try {
            // InternalSTCoreParser.g:2590:59: (iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF )
            // InternalSTCoreParser.g:2591:2: iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF
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
    // InternalSTCoreParser.g:2597:1: ruleSTAtomicExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) ) ;
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
            // InternalSTCoreParser.g:2603:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) ) )
            // InternalSTCoreParser.g:2604:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) )
            {
            // InternalSTCoreParser.g:2604:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==LeftParenthesis) ) {
                alt58=1;
            }
            else if ( (LA58_0==RULE_ID) ) {
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
                    // InternalSTCoreParser.g:2605:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTCoreParser.g:2605:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTCoreParser.g:2606:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
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
                    // InternalSTCoreParser.g:2624:3: ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? )
                    {
                    // InternalSTCoreParser.g:2624:3: ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? )
                    // InternalSTCoreParser.g:2625:4: () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?
                    {
                    // InternalSTCoreParser.g:2625:4: ()
                    // InternalSTCoreParser.g:2626:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTAtomicExpressionAccess().getSTSymbolAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2632:4: ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )?
                    int alt53=2;
                    int LA53_0 = input.LA(1);

                    if ( (LA53_0==RULE_ID) ) {
                        int LA53_1 = input.LA(2);

                        if ( (LA53_1==NumberSign) ) {
                            alt53=1;
                        }
                    }
                    switch (alt53) {
                        case 1 :
                            // InternalSTCoreParser.g:2633:5: ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign
                            {
                            // InternalSTCoreParser.g:2633:5: ( (otherlv_4= RULE_ID ) )
                            // InternalSTCoreParser.g:2634:6: (otherlv_4= RULE_ID )
                            {
                            // InternalSTCoreParser.g:2634:6: (otherlv_4= RULE_ID )
                            // InternalSTCoreParser.g:2635:7: otherlv_4= RULE_ID
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

                    // InternalSTCoreParser.g:2651:4: ( (otherlv_6= RULE_ID ) )
                    // InternalSTCoreParser.g:2652:5: (otherlv_6= RULE_ID )
                    {
                    // InternalSTCoreParser.g:2652:5: (otherlv_6= RULE_ID )
                    // InternalSTCoreParser.g:2653:6: otherlv_6= RULE_ID
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

                    // InternalSTCoreParser.g:2664:4: ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( ((LA54_0>=B && LA54_0<=X)) ) {
                        alt54=1;
                    }
                    else if ( (LA54_0==FullStop) ) {
                        int LA54_2 = input.LA(2);

                        if ( (LA54_2==RULE_INT) ) {
                            alt54=1;
                        }
                    }
                    switch (alt54) {
                        case 1 :
                            // InternalSTCoreParser.g:2665:5: (lv_bitaccessor_7_0= ruleMultibitPartialAccess )
                            {
                            // InternalSTCoreParser.g:2665:5: (lv_bitaccessor_7_0= ruleMultibitPartialAccess )
                            // InternalSTCoreParser.g:2666:6: lv_bitaccessor_7_0= ruleMultibitPartialAccess
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

                    // InternalSTCoreParser.g:2683:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?
                    int alt57=2;
                    alt57 = dfa57.predict(input);
                    switch (alt57) {
                        case 1 :
                            // InternalSTCoreParser.g:2684:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis
                            {
                            // InternalSTCoreParser.g:2684:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) )
                            // InternalSTCoreParser.g:2685:6: ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis )
                            {
                            // InternalSTCoreParser.g:2689:6: (lv_poeInvocation_8_0= LeftParenthesis )
                            // InternalSTCoreParser.g:2690:7: lv_poeInvocation_8_0= LeftParenthesis
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

                            // InternalSTCoreParser.g:2702:5: ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )?
                            int alt56=2;
                            int LA56_0 = input.LA(1);

                            if ( ((LA56_0>=LDATE_AND_TIME && LA56_0<=TIME_OF_DAY)||LA56_0==WSTRING||(LA56_0>=STRING && LA56_0<=DWORD)||(LA56_0>=LDATE && LA56_0<=LWORD)||(LA56_0>=UDINT && LA56_0<=WCHAR)||(LA56_0>=BOOL && LA56_0<=DINT)||(LA56_0>=LINT && LA56_0<=UINT)||LA56_0==WORD||(LA56_0>=INT && LA56_0<=LDT)||LA56_0==TOD||LA56_0==DT||(LA56_0>=LD && LA56_0<=LT)||LA56_0==NOT||LA56_0==D||LA56_0==T||LA56_0==LeftParenthesis||LA56_0==PlusSign||LA56_0==HyphenMinus||LA56_0==RULE_BOOL_VALUES||(LA56_0>=RULE_NON_DECIMAL && LA56_0<=RULE_INT)||(LA56_0>=RULE_ID && LA56_0<=RULE_STRING)) ) {
                                alt56=1;
                            }
                            switch (alt56) {
                                case 1 :
                                    // InternalSTCoreParser.g:2703:6: ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )*
                                    {
                                    // InternalSTCoreParser.g:2703:6: ( (lv_parameters_9_0= ruleSTExpression ) )
                                    // InternalSTCoreParser.g:2704:7: (lv_parameters_9_0= ruleSTExpression )
                                    {
                                    // InternalSTCoreParser.g:2704:7: (lv_parameters_9_0= ruleSTExpression )
                                    // InternalSTCoreParser.g:2705:8: lv_parameters_9_0= ruleSTExpression
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

                                    // InternalSTCoreParser.g:2722:6: (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )*
                                    loop55:
                                    do {
                                        int alt55=2;
                                        int LA55_0 = input.LA(1);

                                        if ( (LA55_0==Comma) ) {
                                            alt55=1;
                                        }


                                        switch (alt55) {
                                    	case 1 :
                                    	    // InternalSTCoreParser.g:2723:7: otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) )
                                    	    {
                                    	    otherlv_10=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
                                    	    if ( state.backtracking==0 ) {

                                    	      							newLeafNode(otherlv_10, grammarAccess.getSTAtomicExpressionAccess().getCommaKeyword_1_4_1_1_0());
                                    	      						
                                    	    }
                                    	    // InternalSTCoreParser.g:2727:7: ( (lv_parameters_11_0= ruleSTExpression ) )
                                    	    // InternalSTCoreParser.g:2728:8: (lv_parameters_11_0= ruleSTExpression )
                                    	    {
                                    	    // InternalSTCoreParser.g:2728:8: (lv_parameters_11_0= ruleSTExpression )
                                    	    // InternalSTCoreParser.g:2729:9: lv_parameters_11_0= ruleSTExpression
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
                                    	    break loop55;
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
    // InternalSTCoreParser.g:2758:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTCoreParser.g:2758:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTCoreParser.g:2759:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTCoreParser.g:2765:1: ruleSTLiteralExpressions returns [EObject current=null] : ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) ) ;
    public final EObject ruleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject lv_boolLiteral_1_0 = null;

        EObject lv_numericLiteral_3_0 = null;

        EObject lv_dateLiteral_5_0 = null;

        EObject lv_timeLiteral_7_0 = null;

        EObject lv_timeOfDayLiteral_9_0 = null;

        EObject lv_timeLiteral_11_0 = null;

        EObject lv_stringLiteral_13_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2771:2: ( ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) ) )
            // InternalSTCoreParser.g:2772:2: ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) )
            {
            // InternalSTCoreParser.g:2772:2: ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) )
            int alt59=7;
            switch ( input.LA(1) ) {
            case NOT:
                {
                int LA59_1 = input.LA(2);

                if ( (LA59_1==BOOL||LA59_1==RULE_BOOL_VALUES) ) {
                    alt59=1;
                }
                else if ( (LA59_1==DWORD||LA59_1==LREAL||LA59_1==LWORD||(LA59_1>=UDINT && LA59_1<=USINT)||LA59_1==BYTE||LA59_1==DINT||LA59_1==LINT||(LA59_1>=REAL && LA59_1<=SINT)||LA59_1==UINT||LA59_1==WORD||LA59_1==INT||LA59_1==PlusSign||LA59_1==HyphenMinus||(LA59_1>=RULE_NON_DECIMAL && LA59_1<=RULE_INT)) ) {
                    alt59=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 59, 1, input);

                    throw nvae;
                }
                }
                break;
            case BOOL:
            case RULE_BOOL_VALUES:
                {
                alt59=1;
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
                alt59=2;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt59=3;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt59=4;
                }
                break;
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt59=5;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt59=6;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt59=7;
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
                    // InternalSTCoreParser.g:2773:3: ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2773:3: ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) )
                    // InternalSTCoreParser.g:2774:4: () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2774:4: ()
                    // InternalSTCoreParser.g:2775:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTBoolLiteralAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2781:4: ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) )
                    // InternalSTCoreParser.g:2782:5: (lv_boolLiteral_1_0= ruleBOOL_LITERAL )
                    {
                    // InternalSTCoreParser.g:2782:5: (lv_boolLiteral_1_0= ruleBOOL_LITERAL )
                    // InternalSTCoreParser.g:2783:6: lv_boolLiteral_1_0= ruleBOOL_LITERAL
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
                    // InternalSTCoreParser.g:2802:3: ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2802:3: ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) )
                    // InternalSTCoreParser.g:2803:4: () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2803:4: ()
                    // InternalSTCoreParser.g:2804:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTNumericLiteralAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2810:4: ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) )
                    // InternalSTCoreParser.g:2811:5: (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL )
                    {
                    // InternalSTCoreParser.g:2811:5: (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL )
                    // InternalSTCoreParser.g:2812:6: lv_numericLiteral_3_0= ruleNUMERIC_LITERAL
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
                    // InternalSTCoreParser.g:2831:3: ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2831:3: ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) )
                    // InternalSTCoreParser.g:2832:4: () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2832:4: ()
                    // InternalSTCoreParser.g:2833:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTDateLiteralAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2839:4: ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) )
                    // InternalSTCoreParser.g:2840:5: (lv_dateLiteral_5_0= ruleDATE_LITERAL )
                    {
                    // InternalSTCoreParser.g:2840:5: (lv_dateLiteral_5_0= ruleDATE_LITERAL )
                    // InternalSTCoreParser.g:2841:6: lv_dateLiteral_5_0= ruleDATE_LITERAL
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
                    // InternalSTCoreParser.g:2860:3: ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2860:3: ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) )
                    // InternalSTCoreParser.g:2861:4: () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2861:4: ()
                    // InternalSTCoreParser.g:2862:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTTimeLiteralAction_3_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2868:4: ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) )
                    // InternalSTCoreParser.g:2869:5: (lv_timeLiteral_7_0= ruleTIME_LITERAL )
                    {
                    // InternalSTCoreParser.g:2869:5: (lv_timeLiteral_7_0= ruleTIME_LITERAL )
                    // InternalSTCoreParser.g:2870:6: lv_timeLiteral_7_0= ruleTIME_LITERAL
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
                    // InternalSTCoreParser.g:2889:3: ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2889:3: ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) )
                    // InternalSTCoreParser.g:2890:4: () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2890:4: ()
                    // InternalSTCoreParser.g:2891:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTTimeOfDayLiteralAction_4_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2897:4: ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) )
                    // InternalSTCoreParser.g:2898:5: (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL )
                    {
                    // InternalSTCoreParser.g:2898:5: (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL )
                    // InternalSTCoreParser.g:2899:6: lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL
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
                    // InternalSTCoreParser.g:2918:3: ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2918:3: ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) )
                    // InternalSTCoreParser.g:2919:4: () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2919:4: ()
                    // InternalSTCoreParser.g:2920:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTDateAndTimeLiteralAction_5_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2926:4: ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) )
                    // InternalSTCoreParser.g:2927:5: (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL )
                    {
                    // InternalSTCoreParser.g:2927:5: (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL )
                    // InternalSTCoreParser.g:2928:6: lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL
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
                    // InternalSTCoreParser.g:2947:3: ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) )
                    {
                    // InternalSTCoreParser.g:2947:3: ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) )
                    // InternalSTCoreParser.g:2948:4: () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) )
                    {
                    // InternalSTCoreParser.g:2948:4: ()
                    // InternalSTCoreParser.g:2949:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTStringLiteralAction_6_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2955:4: ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) )
                    // InternalSTCoreParser.g:2956:5: (lv_stringLiteral_13_0= ruleSTRING_LITERAL )
                    {
                    // InternalSTCoreParser.g:2956:5: (lv_stringLiteral_13_0= ruleSTRING_LITERAL )
                    // InternalSTCoreParser.g:2957:6: lv_stringLiteral_13_0= ruleSTRING_LITERAL
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
    // InternalSTCoreParser.g:2979:1: entryRuleBOOL_LITERAL returns [EObject current=null] : iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF ;
    public final EObject entryRuleBOOL_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBOOL_LITERAL = null;


        try {
            // InternalSTCoreParser.g:2979:53: (iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF )
            // InternalSTCoreParser.g:2980:2: iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBOOL_LITERALRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBOOL_LITERAL=ruleBOOL_LITERAL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBOOL_LITERAL; 
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
    // InternalSTCoreParser.g:2986:1: ruleBOOL_LITERAL returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) ) ;
    public final EObject ruleBOOL_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token lv_keyWordValue_2_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:2992:2: ( ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) ) )
            // InternalSTCoreParser.g:2993:2: ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) )
            {
            // InternalSTCoreParser.g:2993:2: ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) )
            // InternalSTCoreParser.g:2994:3: ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) )
            {
            // InternalSTCoreParser.g:2994:3: ( (lv_not_0_0= NOT ) )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==NOT) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalSTCoreParser.g:2995:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTCoreParser.g:2995:4: (lv_not_0_0= NOT )
                    // InternalSTCoreParser.g:2996:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_not_0_0, grammarAccess.getBOOL_LITERALAccess().getNotNOTKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getBOOL_LITERALRule());
                      					}
                      					setWithLastConsumed(current, "not", lv_not_0_0, "NOT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTCoreParser.g:3008:3: (otherlv_1= BOOL )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==BOOL) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalSTCoreParser.g:3009:4: otherlv_1= BOOL
                    {
                    otherlv_1=(Token)match(input,BOOL,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getBOOL_LITERALAccess().getBOOLKeyword_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:3014:3: ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) )
            // InternalSTCoreParser.g:3015:4: (lv_keyWordValue_2_0= RULE_BOOL_VALUES )
            {
            // InternalSTCoreParser.g:3015:4: (lv_keyWordValue_2_0= RULE_BOOL_VALUES )
            // InternalSTCoreParser.g:3016:5: lv_keyWordValue_2_0= RULE_BOOL_VALUES
            {
            lv_keyWordValue_2_0=(Token)match(input,RULE_BOOL_VALUES,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_keyWordValue_2_0, grammarAccess.getBOOL_LITERALAccess().getKeyWordValueBOOL_VALUESTerminalRuleCall_2_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getBOOL_LITERALRule());
              					}
              					setWithLastConsumed(
              						current,
              						"keyWordValue",
              						lv_keyWordValue_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.BOOL_VALUES");
              				
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
    // $ANTLR end "ruleBOOL_LITERAL"


    // $ANTLR start "entryRuleNUMERIC_LITERAL"
    // InternalSTCoreParser.g:3036:1: entryRuleNUMERIC_LITERAL returns [EObject current=null] : iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF ;
    public final EObject entryRuleNUMERIC_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNUMERIC_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3036:56: (iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF )
            // InternalSTCoreParser.g:3037:2: iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF
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
    // InternalSTCoreParser.g:3043:1: ruleNUMERIC_LITERAL returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) ) ;
    public final EObject ruleNUMERIC_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token lv_keyword_1_1=null;
        Token lv_keyword_1_2=null;
        Token lv_keyword_1_3=null;
        Token lv_keyword_1_4=null;
        Token lv_keyword_1_5=null;
        Token lv_keyword_1_6=null;
        Token lv_keyword_1_7=null;
        Token lv_keyword_1_8=null;
        Token lv_keyword_1_9=null;
        Token lv_keyword_1_10=null;
        Token lv_keyword_1_11=null;
        Token lv_keyword_1_12=null;
        Token lv_keyword_1_13=null;
        Token lv_keyword_1_14=null;
        Token lv_hexValue_4_0=null;
        AntlrDatatypeRuleToken lv_intValue_2_0 = null;

        AntlrDatatypeRuleToken lv_realValue_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3049:2: ( ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTCoreParser.g:3050:2: ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTCoreParser.g:3050:2: ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) )
            // InternalSTCoreParser.g:3051:3: ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTCoreParser.g:3051:3: ( (lv_not_0_0= NOT ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==NOT) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalSTCoreParser.g:3052:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTCoreParser.g:3052:4: (lv_not_0_0= NOT )
                    // InternalSTCoreParser.g:3053:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_not_0_0, grammarAccess.getNUMERIC_LITERALAccess().getNotNOTKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                      					}
                      					setWithLastConsumed(current, "not", lv_not_0_0, "NOT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTCoreParser.g:3065:3: ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==DWORD||LA64_0==LREAL||LA64_0==LWORD||(LA64_0>=UDINT && LA64_0<=USINT)||LA64_0==BYTE||LA64_0==DINT||LA64_0==LINT||(LA64_0>=REAL && LA64_0<=SINT)||LA64_0==UINT||LA64_0==WORD||LA64_0==INT) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalSTCoreParser.g:3066:4: ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) )
                    {
                    // InternalSTCoreParser.g:3066:4: ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) )
                    // InternalSTCoreParser.g:3067:5: (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL )
                    {
                    // InternalSTCoreParser.g:3067:5: (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL )
                    int alt63=14;
                    switch ( input.LA(1) ) {
                    case BYTE:
                        {
                        alt63=1;
                        }
                        break;
                    case WORD:
                        {
                        alt63=2;
                        }
                        break;
                    case DWORD:
                        {
                        alt63=3;
                        }
                        break;
                    case LWORD:
                        {
                        alt63=4;
                        }
                        break;
                    case SINT:
                        {
                        alt63=5;
                        }
                        break;
                    case INT:
                        {
                        alt63=6;
                        }
                        break;
                    case DINT:
                        {
                        alt63=7;
                        }
                        break;
                    case LINT:
                        {
                        alt63=8;
                        }
                        break;
                    case USINT:
                        {
                        alt63=9;
                        }
                        break;
                    case UINT:
                        {
                        alt63=10;
                        }
                        break;
                    case UDINT:
                        {
                        alt63=11;
                        }
                        break;
                    case ULINT:
                        {
                        alt63=12;
                        }
                        break;
                    case REAL:
                        {
                        alt63=13;
                        }
                        break;
                    case LREAL:
                        {
                        alt63=14;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 63, 0, input);

                        throw nvae;
                    }

                    switch (alt63) {
                        case 1 :
                            // InternalSTCoreParser.g:3068:6: lv_keyword_1_1= BYTE
                            {
                            lv_keyword_1_1=(Token)match(input,BYTE,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_1, grammarAccess.getNUMERIC_LITERALAccess().getKeywordBYTEKeyword_1_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:3079:6: lv_keyword_1_2= WORD
                            {
                            lv_keyword_1_2=(Token)match(input,WORD,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_2, grammarAccess.getNUMERIC_LITERALAccess().getKeywordWORDKeyword_1_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_2, null);
                              					
                            }

                            }
                            break;
                        case 3 :
                            // InternalSTCoreParser.g:3090:6: lv_keyword_1_3= DWORD
                            {
                            lv_keyword_1_3=(Token)match(input,DWORD,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_3, grammarAccess.getNUMERIC_LITERALAccess().getKeywordDWORDKeyword_1_0_2());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_3, null);
                              					
                            }

                            }
                            break;
                        case 4 :
                            // InternalSTCoreParser.g:3101:6: lv_keyword_1_4= LWORD
                            {
                            lv_keyword_1_4=(Token)match(input,LWORD,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_4, grammarAccess.getNUMERIC_LITERALAccess().getKeywordLWORDKeyword_1_0_3());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_4, null);
                              					
                            }

                            }
                            break;
                        case 5 :
                            // InternalSTCoreParser.g:3112:6: lv_keyword_1_5= SINT
                            {
                            lv_keyword_1_5=(Token)match(input,SINT,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_5, grammarAccess.getNUMERIC_LITERALAccess().getKeywordSINTKeyword_1_0_4());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_5, null);
                              					
                            }

                            }
                            break;
                        case 6 :
                            // InternalSTCoreParser.g:3123:6: lv_keyword_1_6= INT
                            {
                            lv_keyword_1_6=(Token)match(input,INT,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_6, grammarAccess.getNUMERIC_LITERALAccess().getKeywordINTKeyword_1_0_5());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_6, null);
                              					
                            }

                            }
                            break;
                        case 7 :
                            // InternalSTCoreParser.g:3134:6: lv_keyword_1_7= DINT
                            {
                            lv_keyword_1_7=(Token)match(input,DINT,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_7, grammarAccess.getNUMERIC_LITERALAccess().getKeywordDINTKeyword_1_0_6());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_7, null);
                              					
                            }

                            }
                            break;
                        case 8 :
                            // InternalSTCoreParser.g:3145:6: lv_keyword_1_8= LINT
                            {
                            lv_keyword_1_8=(Token)match(input,LINT,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_8, grammarAccess.getNUMERIC_LITERALAccess().getKeywordLINTKeyword_1_0_7());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_8, null);
                              					
                            }

                            }
                            break;
                        case 9 :
                            // InternalSTCoreParser.g:3156:6: lv_keyword_1_9= USINT
                            {
                            lv_keyword_1_9=(Token)match(input,USINT,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_9, grammarAccess.getNUMERIC_LITERALAccess().getKeywordUSINTKeyword_1_0_8());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_9, null);
                              					
                            }

                            }
                            break;
                        case 10 :
                            // InternalSTCoreParser.g:3167:6: lv_keyword_1_10= UINT
                            {
                            lv_keyword_1_10=(Token)match(input,UINT,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_10, grammarAccess.getNUMERIC_LITERALAccess().getKeywordUINTKeyword_1_0_9());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_10, null);
                              					
                            }

                            }
                            break;
                        case 11 :
                            // InternalSTCoreParser.g:3178:6: lv_keyword_1_11= UDINT
                            {
                            lv_keyword_1_11=(Token)match(input,UDINT,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_11, grammarAccess.getNUMERIC_LITERALAccess().getKeywordUDINTKeyword_1_0_10());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_11, null);
                              					
                            }

                            }
                            break;
                        case 12 :
                            // InternalSTCoreParser.g:3189:6: lv_keyword_1_12= ULINT
                            {
                            lv_keyword_1_12=(Token)match(input,ULINT,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_12, grammarAccess.getNUMERIC_LITERALAccess().getKeywordULINTKeyword_1_0_11());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_12, null);
                              					
                            }

                            }
                            break;
                        case 13 :
                            // InternalSTCoreParser.g:3200:6: lv_keyword_1_13= REAL
                            {
                            lv_keyword_1_13=(Token)match(input,REAL,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_13, grammarAccess.getNUMERIC_LITERALAccess().getKeywordREALKeyword_1_0_12());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_13, null);
                              					
                            }

                            }
                            break;
                        case 14 :
                            // InternalSTCoreParser.g:3211:6: lv_keyword_1_14= LREAL
                            {
                            lv_keyword_1_14=(Token)match(input,LREAL,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_keyword_1_14, grammarAccess.getNUMERIC_LITERALAccess().getKeywordLREALKeyword_1_0_13());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                              						}
                              						setWithLastConsumed(current, "keyword", lv_keyword_1_14, null);
                              					
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }

            // InternalSTCoreParser.g:3224:3: ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) )
            int alt65=3;
            switch ( input.LA(1) ) {
            case PlusSign:
                {
                int LA65_1 = input.LA(2);

                if ( (LA65_1==RULE_INT) ) {
                    int LA65_3 = input.LA(3);

                    if ( (LA65_3==FullStop) ) {
                        alt65=2;
                    }
                    else if ( (LA65_3==EOF||LA65_3==END_REPEAT||LA65_3==THEN||LA65_3==AND||LA65_3==MOD||(LA65_3>=XOR && LA65_3<=FullStopFullStop)||(LA65_3>=LessThanSignEqualsSign && LA65_3<=LessThanSignGreaterThanSign)||LA65_3==GreaterThanSignEqualsSign||LA65_3==BY||LA65_3==DO||(LA65_3>=OF && LA65_3<=OR)||LA65_3==TO||(LA65_3>=Ampersand && LA65_3<=HyphenMinus)||(LA65_3>=Solidus && LA65_3<=GreaterThanSign)||LA65_3==RightSquareBracket) ) {
                        alt65=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 65, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 65, 1, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA65_2 = input.LA(2);

                if ( (LA65_2==RULE_INT) ) {
                    int LA65_3 = input.LA(3);

                    if ( (LA65_3==FullStop) ) {
                        alt65=2;
                    }
                    else if ( (LA65_3==EOF||LA65_3==END_REPEAT||LA65_3==THEN||LA65_3==AND||LA65_3==MOD||(LA65_3>=XOR && LA65_3<=FullStopFullStop)||(LA65_3>=LessThanSignEqualsSign && LA65_3<=LessThanSignGreaterThanSign)||LA65_3==GreaterThanSignEqualsSign||LA65_3==BY||LA65_3==DO||(LA65_3>=OF && LA65_3<=OR)||LA65_3==TO||(LA65_3>=Ampersand && LA65_3<=HyphenMinus)||(LA65_3>=Solidus && LA65_3<=GreaterThanSign)||LA65_3==RightSquareBracket) ) {
                        alt65=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 65, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 65, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
                {
                int LA65_3 = input.LA(2);

                if ( (LA65_3==FullStop) ) {
                    alt65=2;
                }
                else if ( (LA65_3==EOF||LA65_3==END_REPEAT||LA65_3==THEN||LA65_3==AND||LA65_3==MOD||(LA65_3>=XOR && LA65_3<=FullStopFullStop)||(LA65_3>=LessThanSignEqualsSign && LA65_3<=LessThanSignGreaterThanSign)||LA65_3==GreaterThanSignEqualsSign||LA65_3==BY||LA65_3==DO||(LA65_3>=OF && LA65_3<=OR)||LA65_3==TO||(LA65_3>=Ampersand && LA65_3<=HyphenMinus)||(LA65_3>=Solidus && LA65_3<=GreaterThanSign)||LA65_3==RightSquareBracket) ) {
                    alt65=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 65, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt65=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }

            switch (alt65) {
                case 1 :
                    // InternalSTCoreParser.g:3225:4: ( (lv_intValue_2_0= ruleINTEGER ) )
                    {
                    // InternalSTCoreParser.g:3225:4: ( (lv_intValue_2_0= ruleINTEGER ) )
                    // InternalSTCoreParser.g:3226:5: (lv_intValue_2_0= ruleINTEGER )
                    {
                    // InternalSTCoreParser.g:3226:5: (lv_intValue_2_0= ruleINTEGER )
                    // InternalSTCoreParser.g:3227:6: lv_intValue_2_0= ruleINTEGER
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNUMERIC_LITERALAccess().getIntValueINTEGERParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_intValue_2_0=ruleINTEGER();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNUMERIC_LITERALRule());
                      						}
                      						set(
                      							current,
                      							"intValue",
                      							lv_intValue_2_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.INTEGER");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3245:4: ( (lv_realValue_3_0= ruleREAL ) )
                    {
                    // InternalSTCoreParser.g:3245:4: ( (lv_realValue_3_0= ruleREAL ) )
                    // InternalSTCoreParser.g:3246:5: (lv_realValue_3_0= ruleREAL )
                    {
                    // InternalSTCoreParser.g:3246:5: (lv_realValue_3_0= ruleREAL )
                    // InternalSTCoreParser.g:3247:6: lv_realValue_3_0= ruleREAL
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNUMERIC_LITERALAccess().getRealValueREALParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_realValue_3_0=ruleREAL();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNUMERIC_LITERALRule());
                      						}
                      						set(
                      							current,
                      							"realValue",
                      							lv_realValue_3_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.REAL");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3265:4: ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) )
                    {
                    // InternalSTCoreParser.g:3265:4: ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) )
                    // InternalSTCoreParser.g:3266:5: (lv_hexValue_4_0= RULE_NON_DECIMAL )
                    {
                    // InternalSTCoreParser.g:3266:5: (lv_hexValue_4_0= RULE_NON_DECIMAL )
                    // InternalSTCoreParser.g:3267:6: lv_hexValue_4_0= RULE_NON_DECIMAL
                    {
                    lv_hexValue_4_0=(Token)match(input,RULE_NON_DECIMAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_hexValue_4_0, grammarAccess.getNUMERIC_LITERALAccess().getHexValueNON_DECIMALTerminalRuleCall_2_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getNUMERIC_LITERALRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"hexValue",
                      							lv_hexValue_4_0,
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
    // InternalSTCoreParser.g:3288:1: entryRuleDATE_LITERAL returns [EObject current=null] : iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF ;
    public final EObject entryRuleDATE_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDATE_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3288:53: (iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF )
            // InternalSTCoreParser.g:3289:2: iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF
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
    // InternalSTCoreParser.g:3295:1: ruleDATE_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) ) ;
    public final EObject ruleDATE_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3301:2: ( ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) ) )
            // InternalSTCoreParser.g:3302:2: ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) )
            {
            // InternalSTCoreParser.g:3302:2: ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) )
            // InternalSTCoreParser.g:3303:3: ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) )
            {
            // InternalSTCoreParser.g:3303:3: ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) )
            // InternalSTCoreParser.g:3304:4: ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) )
            {
            // InternalSTCoreParser.g:3304:4: ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) )
            // InternalSTCoreParser.g:3305:5: (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD )
            {
            // InternalSTCoreParser.g:3305:5: (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD )
            int alt66=4;
            switch ( input.LA(1) ) {
            case DATE:
                {
                alt66=1;
                }
                break;
            case LDATE:
                {
                alt66=2;
                }
                break;
            case D:
                {
                alt66=3;
                }
                break;
            case LD:
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
                    // InternalSTCoreParser.g:3306:6: lv_keyword_0_1= DATE
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
                    // InternalSTCoreParser.g:3317:6: lv_keyword_0_2= LDATE
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
                    // InternalSTCoreParser.g:3328:6: lv_keyword_0_3= D
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
                    // InternalSTCoreParser.g:3339:6: lv_keyword_0_4= LD
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

            // InternalSTCoreParser.g:3352:3: ( (lv_value_1_0= ruleDATE ) )
            // InternalSTCoreParser.g:3353:4: (lv_value_1_0= ruleDATE )
            {
            // InternalSTCoreParser.g:3353:4: (lv_value_1_0= ruleDATE )
            // InternalSTCoreParser.g:3354:5: lv_value_1_0= ruleDATE
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
    // InternalSTCoreParser.g:3375:1: entryRuleTIME_LITERAL returns [EObject current=null] : iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF ;
    public final EObject entryRuleTIME_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTIME_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3375:53: (iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF )
            // InternalSTCoreParser.g:3376:2: iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF
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
    // InternalSTCoreParser.g:3382:1: ruleTIME_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) ) ;
    public final EObject ruleTIME_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        Token lv_value_1_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3388:2: ( ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) ) )
            // InternalSTCoreParser.g:3389:2: ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) )
            {
            // InternalSTCoreParser.g:3389:2: ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) )
            // InternalSTCoreParser.g:3390:3: ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) )
            {
            // InternalSTCoreParser.g:3390:3: ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) )
            // InternalSTCoreParser.g:3391:4: ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) )
            {
            // InternalSTCoreParser.g:3391:4: ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) )
            // InternalSTCoreParser.g:3392:5: (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT )
            {
            // InternalSTCoreParser.g:3392:5: (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT )
            int alt67=4;
            switch ( input.LA(1) ) {
            case TIME:
                {
                alt67=1;
                }
                break;
            case LTIME:
                {
                alt67=2;
                }
                break;
            case T:
                {
                alt67=3;
                }
                break;
            case LT:
                {
                alt67=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }

            switch (alt67) {
                case 1 :
                    // InternalSTCoreParser.g:3393:6: lv_keyword_0_1= TIME
                    {
                    lv_keyword_0_1=(Token)match(input,TIME,FOLLOW_58); if (state.failed) return current;
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
                    // InternalSTCoreParser.g:3404:6: lv_keyword_0_2= LTIME
                    {
                    lv_keyword_0_2=(Token)match(input,LTIME,FOLLOW_58); if (state.failed) return current;
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
                    // InternalSTCoreParser.g:3415:6: lv_keyword_0_3= T
                    {
                    lv_keyword_0_3=(Token)match(input,T,FOLLOW_58); if (state.failed) return current;
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
                    // InternalSTCoreParser.g:3426:6: lv_keyword_0_4= LT
                    {
                    lv_keyword_0_4=(Token)match(input,LT,FOLLOW_58); if (state.failed) return current;
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

            // InternalSTCoreParser.g:3439:3: ( (lv_value_1_0= RULE_TIME ) )
            // InternalSTCoreParser.g:3440:4: (lv_value_1_0= RULE_TIME )
            {
            // InternalSTCoreParser.g:3440:4: (lv_value_1_0= RULE_TIME )
            // InternalSTCoreParser.g:3441:5: lv_value_1_0= RULE_TIME
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
    // InternalSTCoreParser.g:3461:1: entryRuleTIME_OF_DAY_LITERAL returns [EObject current=null] : iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF ;
    public final EObject entryRuleTIME_OF_DAY_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTIME_OF_DAY_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3461:60: (iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF )
            // InternalSTCoreParser.g:3462:2: iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF
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
    // InternalSTCoreParser.g:3468:1: ruleTIME_OF_DAY_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) ) ;
    public final EObject ruleTIME_OF_DAY_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3474:2: ( ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) ) )
            // InternalSTCoreParser.g:3475:2: ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) )
            {
            // InternalSTCoreParser.g:3475:2: ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) )
            // InternalSTCoreParser.g:3476:3: ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) )
            {
            // InternalSTCoreParser.g:3476:3: ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) )
            // InternalSTCoreParser.g:3477:4: ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) )
            {
            // InternalSTCoreParser.g:3477:4: ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) )
            // InternalSTCoreParser.g:3478:5: (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD )
            {
            // InternalSTCoreParser.g:3478:5: (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD )
            int alt68=3;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt68=1;
                }
                break;
            case TOD:
                {
                alt68=2;
                }
                break;
            case LTOD:
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
                    // InternalSTCoreParser.g:3479:6: lv_keyword_0_1= TIME_OF_DAY
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
                    // InternalSTCoreParser.g:3490:6: lv_keyword_0_2= TOD
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
                    // InternalSTCoreParser.g:3501:6: lv_keyword_0_3= LTOD
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

            // InternalSTCoreParser.g:3514:3: ( (lv_value_1_0= ruleTIME_OF_DAY ) )
            // InternalSTCoreParser.g:3515:4: (lv_value_1_0= ruleTIME_OF_DAY )
            {
            // InternalSTCoreParser.g:3515:4: (lv_value_1_0= ruleTIME_OF_DAY )
            // InternalSTCoreParser.g:3516:5: lv_value_1_0= ruleTIME_OF_DAY
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
    // InternalSTCoreParser.g:3537:1: entryRuleDATE_AND_TIME_LITERAL returns [EObject current=null] : iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF ;
    public final EObject entryRuleDATE_AND_TIME_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDATE_AND_TIME_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3537:62: (iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF )
            // InternalSTCoreParser.g:3538:2: iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF
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
    // InternalSTCoreParser.g:3544:1: ruleDATE_AND_TIME_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) ) ;
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
            // InternalSTCoreParser.g:3550:2: ( ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) ) )
            // InternalSTCoreParser.g:3551:2: ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) )
            {
            // InternalSTCoreParser.g:3551:2: ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) )
            // InternalSTCoreParser.g:3552:3: ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) )
            {
            // InternalSTCoreParser.g:3552:3: ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) )
            // InternalSTCoreParser.g:3553:4: ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) )
            {
            // InternalSTCoreParser.g:3553:4: ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) )
            // InternalSTCoreParser.g:3554:5: (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT )
            {
            // InternalSTCoreParser.g:3554:5: (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT )
            int alt69=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt69=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt69=2;
                }
                break;
            case DT:
                {
                alt69=3;
                }
                break;
            case LDT:
                {
                alt69=4;
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
                    // InternalSTCoreParser.g:3555:6: lv_keyword_0_1= DATE_AND_TIME
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
                    // InternalSTCoreParser.g:3566:6: lv_keyword_0_2= LDATE_AND_TIME
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
                    // InternalSTCoreParser.g:3577:6: lv_keyword_0_3= DT
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
                    // InternalSTCoreParser.g:3588:6: lv_keyword_0_4= LDT
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

            // InternalSTCoreParser.g:3601:3: ( (lv_dateValue_1_0= ruleDATE ) )
            // InternalSTCoreParser.g:3602:4: (lv_dateValue_1_0= ruleDATE )
            {
            // InternalSTCoreParser.g:3602:4: (lv_dateValue_1_0= ruleDATE )
            // InternalSTCoreParser.g:3603:5: lv_dateValue_1_0= ruleDATE
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDATE_AND_TIME_LITERALAccess().getDateValueDATEParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_59);
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
            // InternalSTCoreParser.g:3624:3: ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) )
            // InternalSTCoreParser.g:3625:4: (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY )
            {
            // InternalSTCoreParser.g:3625:4: (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY )
            // InternalSTCoreParser.g:3626:5: lv_timeOfDayValue_3_0= ruleTIME_OF_DAY
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
    // InternalSTCoreParser.g:3647:1: entryRuleSTRING_LITERAL returns [EObject current=null] : iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF ;
    public final EObject entryRuleSTRING_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRING_LITERAL = null;


        try {
            // InternalSTCoreParser.g:3647:55: (iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF )
            // InternalSTCoreParser.g:3648:2: iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF
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
    // InternalSTCoreParser.g:3654:1: ruleSTRING_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTRING_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_value_4_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3660:2: ( ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) ) )
            // InternalSTCoreParser.g:3661:2: ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) )
            {
            // InternalSTCoreParser.g:3661:2: ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) )
            // InternalSTCoreParser.g:3662:3: ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) )
            {
            // InternalSTCoreParser.g:3662:3: ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )?
            int alt70=5;
            switch ( input.LA(1) ) {
                case STRING:
                    {
                    alt70=1;
                    }
                    break;
                case WSTRING:
                    {
                    alt70=2;
                    }
                    break;
                case CHAR:
                    {
                    alt70=3;
                    }
                    break;
                case WCHAR:
                    {
                    alt70=4;
                    }
                    break;
            }

            switch (alt70) {
                case 1 :
                    // InternalSTCoreParser.g:3663:4: ( (lv_keyword_0_0= STRING ) )
                    {
                    // InternalSTCoreParser.g:3663:4: ( (lv_keyword_0_0= STRING ) )
                    // InternalSTCoreParser.g:3664:5: (lv_keyword_0_0= STRING )
                    {
                    // InternalSTCoreParser.g:3664:5: (lv_keyword_0_0= STRING )
                    // InternalSTCoreParser.g:3665:6: lv_keyword_0_0= STRING
                    {
                    lv_keyword_0_0=(Token)match(input,STRING,FOLLOW_60); if (state.failed) return current;
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
                    // InternalSTCoreParser.g:3678:4: otherlv_1= WSTRING
                    {
                    otherlv_1=(Token)match(input,WSTRING,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTRING_LITERALAccess().getWSTRINGKeyword_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3683:4: otherlv_2= CHAR
                    {
                    otherlv_2=(Token)match(input,CHAR,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTRING_LITERALAccess().getCHARKeyword_0_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3688:4: otherlv_3= WCHAR
                    {
                    otherlv_3=(Token)match(input,WCHAR,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTRING_LITERALAccess().getWCHARKeyword_0_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:3693:3: ( (lv_value_4_0= RULE_STRING ) )
            // InternalSTCoreParser.g:3694:4: (lv_value_4_0= RULE_STRING )
            {
            // InternalSTCoreParser.g:3694:4: (lv_value_4_0= RULE_STRING )
            // InternalSTCoreParser.g:3695:5: lv_value_4_0= RULE_STRING
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
    // InternalSTCoreParser.g:3715:1: entryRuleINTEGER returns [String current=null] : iv_ruleINTEGER= ruleINTEGER EOF ;
    public final String entryRuleINTEGER() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINTEGER = null;


        try {
            // InternalSTCoreParser.g:3715:47: (iv_ruleINTEGER= ruleINTEGER EOF )
            // InternalSTCoreParser.g:3716:2: iv_ruleINTEGER= ruleINTEGER EOF
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
    // InternalSTCoreParser.g:3722:1: ruleINTEGER returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleINTEGER() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3728:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ) )
            // InternalSTCoreParser.g:3729:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT )
            {
            // InternalSTCoreParser.g:3729:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT )
            // InternalSTCoreParser.g:3730:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT
            {
            // InternalSTCoreParser.g:3730:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt71=3;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==PlusSign) ) {
                alt71=1;
            }
            else if ( (LA71_0==HyphenMinus) ) {
                alt71=2;
            }
            switch (alt71) {
                case 1 :
                    // InternalSTCoreParser.g:3731:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getINTEGERAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3737:4: kw= HyphenMinus
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
    // InternalSTCoreParser.g:3754:1: entryRuleREAL returns [String current=null] : iv_ruleREAL= ruleREAL EOF ;
    public final String entryRuleREAL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleREAL = null;


        try {
            // InternalSTCoreParser.g:3754:44: (iv_ruleREAL= ruleREAL EOF )
            // InternalSTCoreParser.g:3755:2: iv_ruleREAL= ruleREAL EOF
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
    // InternalSTCoreParser.g:3761:1: ruleREAL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleREAL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_EXT_INT_2=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_INTEGER_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3767:2: ( (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) ) )
            // InternalSTCoreParser.g:3768:2: (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) )
            {
            // InternalSTCoreParser.g:3768:2: (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) )
            // InternalSTCoreParser.g:3769:3: this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getREALAccess().getINTEGERParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_61);
            this_INTEGER_0=ruleINTEGER();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INTEGER_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,FullStop,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getREALAccess().getFullStopKeyword_1());
              		
            }
            // InternalSTCoreParser.g:3784:3: (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT )
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==RULE_EXT_INT) ) {
                alt72=1;
            }
            else if ( (LA72_0==RULE_INT) ) {
                alt72=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }
            switch (alt72) {
                case 1 :
                    // InternalSTCoreParser.g:3785:4: this_EXT_INT_2= RULE_EXT_INT
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
                    // InternalSTCoreParser.g:3793:4: this_INT_3= RULE_INT
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
    // InternalSTCoreParser.g:3805:1: entryRuleDATE returns [String current=null] : iv_ruleDATE= ruleDATE EOF ;
    public final String entryRuleDATE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDATE = null;


        try {
            // InternalSTCoreParser.g:3805:44: (iv_ruleDATE= ruleDATE EOF )
            // InternalSTCoreParser.g:3806:2: iv_ruleDATE= ruleDATE EOF
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
    // InternalSTCoreParser.g:3812:1: ruleDATE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDATE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3818:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTCoreParser.g:3819:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTCoreParser.g:3819:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTCoreParser.g:3820:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_59); if (state.failed) return current;
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
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_59); if (state.failed) return current;
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
    // InternalSTCoreParser.g:3855:1: entryRuleTIME_OF_DAY returns [String current=null] : iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF ;
    public final String entryRuleTIME_OF_DAY() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTIME_OF_DAY = null;


        try {
            // InternalSTCoreParser.g:3855:51: (iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF )
            // InternalSTCoreParser.g:3856:2: iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF
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
    // InternalSTCoreParser.g:3862:1: ruleTIME_OF_DAY returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTIME_OF_DAY() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3868:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) )
            // InternalSTCoreParser.g:3869:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            {
            // InternalSTCoreParser.g:3869:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            // InternalSTCoreParser.g:3870:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )?
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
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTIME_OF_DAYAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTCoreParser.g:3901:3: (kw= FullStop this_INT_6= RULE_INT )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==FullStop) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalSTCoreParser.g:3902:4: kw= FullStop this_INT_6= RULE_INT
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


    // $ANTLR start "ruleMultiBitAccessSpecifier"
    // InternalSTCoreParser.g:3919:1: ruleMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) ) ;
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
            // InternalSTCoreParser.g:3925:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) ) )
            // InternalSTCoreParser.g:3926:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) )
            {
            // InternalSTCoreParser.g:3926:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) )
            int alt74=6;
            switch ( input.LA(1) ) {
            case L:
                {
                alt74=1;
                }
                break;
            case D_1:
                {
                alt74=2;
                }
                break;
            case W:
                {
                alt74=3;
                }
                break;
            case B:
                {
                alt74=4;
                }
                break;
            case X:
                {
                alt74=5;
                }
                break;
            case FullStop:
                {
                alt74=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;
            }

            switch (alt74) {
                case 1 :
                    // InternalSTCoreParser.g:3927:3: (enumLiteral_0= L )
                    {
                    // InternalSTCoreParser.g:3927:3: (enumLiteral_0= L )
                    // InternalSTCoreParser.g:3928:4: enumLiteral_0= L
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
                    // InternalSTCoreParser.g:3935:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTCoreParser.g:3935:3: (enumLiteral_1= D_1 )
                    // InternalSTCoreParser.g:3936:4: enumLiteral_1= D_1
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
                    // InternalSTCoreParser.g:3943:3: (enumLiteral_2= W )
                    {
                    // InternalSTCoreParser.g:3943:3: (enumLiteral_2= W )
                    // InternalSTCoreParser.g:3944:4: enumLiteral_2= W
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
                    // InternalSTCoreParser.g:3951:3: (enumLiteral_3= B )
                    {
                    // InternalSTCoreParser.g:3951:3: (enumLiteral_3= B )
                    // InternalSTCoreParser.g:3952:4: enumLiteral_3= B
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
                    // InternalSTCoreParser.g:3959:3: (enumLiteral_4= X )
                    {
                    // InternalSTCoreParser.g:3959:3: (enumLiteral_4= X )
                    // InternalSTCoreParser.g:3960:4: enumLiteral_4= X
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
                    // InternalSTCoreParser.g:3967:3: (enumLiteral_5= FullStop )
                    {
                    // InternalSTCoreParser.g:3967:3: (enumLiteral_5= FullStop )
                    // InternalSTCoreParser.g:3968:4: enumLiteral_5= FullStop
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
        // InternalSTCoreParser.g:1079:4: ( ( ruleSTStatement ) )
        // InternalSTCoreParser.g:1079:5: ( ruleSTStatement )
        {
        // InternalSTCoreParser.g:1079:5: ( ruleSTStatement )
        // InternalSTCoreParser.g:1080:5: ruleSTStatement
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
        // InternalSTCoreParser.g:2438:6: ( ( LeftParenthesis ) )
        // InternalSTCoreParser.g:2438:7: ( LeftParenthesis )
        {
        // InternalSTCoreParser.g:2438:7: ( LeftParenthesis )
        // InternalSTCoreParser.g:2439:7: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSTCoreParser

    // $ANTLR start synpred3_InternalSTCoreParser
    public final void synpred3_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:2507:5: ( ( ruleMultibitPartialAccess ) )
        // InternalSTCoreParser.g:2507:6: ( ruleMultibitPartialAccess )
        {
        // InternalSTCoreParser.g:2507:6: ( ruleMultibitPartialAccess )
        // InternalSTCoreParser.g:2508:6: ruleMultibitPartialAccess
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
        // InternalSTCoreParser.g:2685:6: ( ( LeftParenthesis ) )
        // InternalSTCoreParser.g:2685:7: ( LeftParenthesis )
        {
        // InternalSTCoreParser.g:2685:7: ( LeftParenthesis )
        // InternalSTCoreParser.g:2686:7: LeftParenthesis
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


    protected DFA23 dfa23 = new DFA23(this);
    protected DFA50 dfa50 = new DFA50(this);
    protected DFA57 dfa57 = new DFA57(this);
    static final String dfa_1s = "\16\uffff";
    static final String dfa_2s = "\1\1\15\uffff";
    static final String dfa_3s = "\1\4\1\uffff\1\66\13\uffff";
    static final String dfa_4s = "\1\154\1\uffff\1\143\13\uffff";
    static final String dfa_5s = "\1\uffff\1\2\1\uffff\13\1";
    static final String dfa_6s = "\1\0\1\uffff\1\1\13\uffff}>";
    static final String[] dfa_7s = {
            "\3\1\5\uffff\1\12\1\1\1\uffff\1\1\2\uffff\2\1\1\uffff\4\1\1\7\1\11\4\1\1\uffff\5\1\1\uffff\6\1\1\uffff\1\6\1\1\1\4\1\1\1\13\2\1\1\uffff\1\1\6\uffff\1\1\1\5\2\1\1\uffff\1\1\13\uffff\1\1\1\uffff\1\3\2\uffff\1\1\3\uffff\1\1\2\uffff\1\1\1\uffff\1\1\3\uffff\1\10\5\uffff\1\1\1\uffff\2\1\2\uffff\1\2\1\1",
            "",
            "\6\1\4\uffff\1\1\2\uffff\3\1\1\14\2\1\1\15\1\1\6\uffff\1\1\2\uffff\3\1\1\uffff\7\1\1\uffff\4\1",
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

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 1078:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA23_0 = input.LA(1);

                         
                        int index23_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA23_0==EOF||(LA23_0>=LDATE_AND_TIME && LA23_0<=TIME_OF_DAY)||LA23_0==END_CASE||LA23_0==WSTRING||(LA23_0>=STRING && LA23_0<=DWORD)||(LA23_0>=LDATE && LA23_0<=LWORD)||(LA23_0>=UDINT && LA23_0<=WCHAR)||(LA23_0>=BOOL && LA23_0<=DINT)||(LA23_0>=LINT && LA23_0<=UINT)||LA23_0==WORD||LA23_0==ELSE||(LA23_0>=INT && LA23_0<=LDT)||LA23_0==TOD||LA23_0==DT||(LA23_0>=LD && LA23_0<=LT)||LA23_0==NOT||LA23_0==D||LA23_0==T||LA23_0==LeftParenthesis||LA23_0==PlusSign||LA23_0==HyphenMinus||LA23_0==RULE_BOOL_VALUES||(LA23_0>=RULE_NON_DECIMAL && LA23_0<=RULE_INT)||LA23_0==RULE_STRING) ) {s = 1;}

                        else if ( (LA23_0==RULE_ID) ) {s = 2;}

                        else if ( (LA23_0==IF) && (synpred1_InternalSTCoreParser())) {s = 3;}

                        else if ( (LA23_0==CASE) && (synpred1_InternalSTCoreParser())) {s = 4;}

                        else if ( (LA23_0==FOR) && (synpred1_InternalSTCoreParser())) {s = 5;}

                        else if ( (LA23_0==WHILE) && (synpred1_InternalSTCoreParser())) {s = 6;}

                        else if ( (LA23_0==REPEAT) && (synpred1_InternalSTCoreParser())) {s = 7;}

                        else if ( (LA23_0==Semicolon) && (synpred1_InternalSTCoreParser())) {s = 8;}

                        else if ( (LA23_0==RETURN) && (synpred1_InternalSTCoreParser())) {s = 9;}

                        else if ( (LA23_0==CONTINUE) && (synpred1_InternalSTCoreParser())) {s = 10;}

                        else if ( (LA23_0==EXIT) && (synpred1_InternalSTCoreParser())) {s = 11;}

                         
                        input.seek(index23_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA23_2 = input.LA(1);

                         
                        int index23_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA23_2>=B && LA23_2<=AND)||LA23_2==MOD||(LA23_2>=XOR && LA23_2<=FullStopFullStop)||(LA23_2>=LessThanSignEqualsSign && LA23_2<=LessThanSignGreaterThanSign)||LA23_2==GreaterThanSignEqualsSign||LA23_2==OR||(LA23_2>=NumberSign && LA23_2<=LeftParenthesis)||(LA23_2>=Asterisk && LA23_2<=Colon)||(LA23_2>=LessThanSign && LA23_2<=LeftSquareBracket)) ) {s = 1;}

                        else if ( (LA23_2==ColonEqualsSign) && (synpred1_InternalSTCoreParser())) {s = 12;}

                        else if ( (LA23_2==EqualsSignGreaterThanSign) && (synpred1_InternalSTCoreParser())) {s = 13;}

                         
                        input.seek(index23_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 23, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\47\uffff";
    static final String dfa_9s = "\1\2\46\uffff";
    static final String dfa_10s = "\1\7\1\0\45\uffff";
    static final String dfa_11s = "\1\144\1\0\45\uffff";
    static final String dfa_12s = "\2\uffff\1\2\43\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\45\uffff}>";
    static final String[] dfa_14s = {
            "\1\2\54\uffff\1\2\1\uffff\6\2\4\uffff\1\2\2\uffff\3\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\16\2",
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

    class DFA50 extends DFA {

        public DFA50(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 50;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "2436:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA50_1 = input.LA(1);

                         
                        int index50_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 38;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index50_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 50, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\42\uffff";
    static final String dfa_16s = "\1\2\41\uffff";
    static final String dfa_17s = "\1\7\1\0\40\uffff";
    static final String dfa_18s = "\1\144\1\0\40\uffff";
    static final String dfa_19s = "\2\uffff\1\2\36\uffff\1\1";
    static final String dfa_20s = "\1\uffff\1\0\40\uffff}>";
    static final String[] dfa_21s = {
            "\1\2\54\uffff\1\2\6\uffff\1\2\4\uffff\1\2\2\uffff\3\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\16\2",
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

    class DFA57 extends DFA {

        public DFA57(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 57;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "2683:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA57_1 = input.LA(1);

                         
                        int index57_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSTCoreParser()) ) {s = 33;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index57_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 57, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x2002A00006001002L,0x0000080080008000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000800L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000080000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0xD02C4FDF79EC8070L,0x000019A00A442002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000001004000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000000880000040L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000040L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0xD02C4FDF79EC8070L,0x000019A80A442002L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000240L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x2003A02006101000L,0x0000080080008000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0001002000100000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x2002A00006001000L,0x0000080080008000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0xD02D4FDF79ECA070L,0x000019A00A442002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000044000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000000005000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x2002A00006011000L,0x0000080080008000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x2002A00006001200L,0x0000080080008000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x2002B00006001000L,0x0000080080008000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0800000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000100L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000000500000480L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x000000000A000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000021000001L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0000080000400000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000000810000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x07C0000000000002L,0x0000000810400000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0xD02C4FDF79EC8070L,0x000019A00AC42002L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000000004800000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x07C0000000000002L,0x0000000810000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x07C0000000000002L,0x0000000010400000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000100000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x00044B5239480000L,0x000001800A000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x000001800A000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000050000000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});

}