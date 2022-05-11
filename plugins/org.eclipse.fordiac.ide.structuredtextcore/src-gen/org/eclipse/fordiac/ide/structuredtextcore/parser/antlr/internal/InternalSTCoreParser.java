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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "LTIME_OF_DAY", "TIME_OF_DAY", "END_REPEAT", "VAR_IN_OUT", "VAR_OUTPUT", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "VAR_TEMP", "END_FOR", "END_VAR", "WSTRING", "END_IF", "REPEAT", "RETURN", "STRING", "ARRAY", "DWORD", "ELSIF", "FALSE", "LDATE", "LREAL", "LTIME", "LWORD", "UDINT", "ULINT", "UNTIL", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "LINT", "LTOD", "REAL", "SINT", "THEN", "TIME", "TRUE", "UINT", "WORD", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "TOD", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "MS", "NS", "OF", "OR", "TO", "US", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "H", "M", "S", "T", "LeftSquareBracket", "RightSquareBracket", "KW__", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=31;
    public static final int LessThanSignGreaterThanSign=73;
    public static final int EqualsSignGreaterThanSign=74;
    public static final int VAR=62;
    public static final int END_IF=20;
    public static final int ULINT=33;
    public static final int END_CASE=15;
    public static final int LessThanSign=101;
    public static final int LeftParenthesis=91;
    public static final int BYTE=39;
    public static final int ELSE=44;
    public static final int IF=80;
    public static final int LINT=46;
    public static final int GreaterThanSign=103;
    public static final int WORD=54;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=116;
    public static final int TOD=61;
    public static final int DINT=43;
    public static final int UDINT=32;
    public static final int CASE=40;
    public static final int GreaterThanSignEqualsSign=75;
    public static final int AT=76;
    public static final int PlusSign=94;
    public static final int RULE_INT=114;
    public static final int END_FOR=17;
    public static final int RULE_ML_COMMENT=118;
    public static final int THEN=50;
    public static final int XOR=63;
    public static final int LeftSquareBracket=109;
    public static final int EXIT=45;
    public static final int TIME_OF_DAY=7;
    public static final int B=64;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=21;
    public static final int D=104;
    public static final int H=105;
    public static final int CHAR=41;
    public static final int L=66;
    public static final int M=106;
    public static final int LTIME=30;
    public static final int Comma=95;
    public static final int HyphenMinus=96;
    public static final int S=107;
    public static final int T=108;
    public static final int W=67;
    public static final int BY=77;
    public static final int X=68;
    public static final int ELSIF=26;
    public static final int END_REPEAT=8;
    public static final int LessThanSignEqualsSign=72;
    public static final int Solidus=98;
    public static final int VAR_INPUT=12;
    public static final int FullStop=97;
    public static final int VAR_TEMP=16;
    public static final int CONSTANT=13;
    public static final int KW__=111;
    public static final int CONTINUE=14;
    public static final int Semicolon=100;
    public static final int LD=81;
    public static final int VAR_OUTPUT=10;
    public static final int STRING=23;
    public static final int RULE_HEX_DIGIT=112;
    public static final int TO=87;
    public static final int UINT=53;
    public static final int LTOD=47;
    public static final int ARRAY=24;
    public static final int LT=82;
    public static final int DO=78;
    public static final int WSTRING=19;
    public static final int DT=79;
    public static final int END_VAR=18;
    public static final int FullStopFullStop=70;
    public static final int Ampersand=90;
    public static final int US=88;
    public static final int RightSquareBracket=110;
    public static final int USINT=35;
    public static final int MS=83;
    public static final int DWORD=25;
    public static final int FOR=56;
    public static final int RightParenthesis=92;
    public static final int TRUE=52;
    public static final int ColonEqualsSign=71;
    public static final int END_WHILE=11;
    public static final int DATE=42;
    public static final int NOT=60;
    public static final int LDATE=28;
    public static final int AND=55;
    public static final int NumberSign=89;
    public static final int REAL=48;
    public static final int AsteriskAsterisk=69;
    public static final int SINT=49;
    public static final int LTIME_OF_DAY=6;
    public static final int LREAL=29;
    public static final int WCHAR=36;
    public static final int NS=84;
    public static final int RULE_STRING=117;
    public static final int INT=57;
    public static final int RULE_SL_COMMENT=119;
    public static final int RETURN=22;
    public static final int EqualsSign=102;
    public static final int OF=85;
    public static final int Colon=99;
    public static final int EOF=-1;
    public static final int LDT=58;
    public static final int Asterisk=93;
    public static final int MOD=59;
    public static final int OR=86;
    public static final int RULE_WS=120;
    public static final int RULE_EXT_INT=115;
    public static final int VAR_IN_OUT=9;
    public static final int TIME=51;
    public static final int RULE_ANY_OTHER=121;
    public static final int UNTIL=34;
    public static final int BOOL=38;
    public static final int D_1=65;
    public static final int FALSE=27;
    public static final int WHILE=37;
    public static final int RULE_NON_DECIMAL=113;

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
        	return "STCoreSource";
       	}

       	@Override
       	protected STCoreGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleSTCoreSource"
    // InternalSTCoreParser.g:58:1: entryRuleSTCoreSource returns [EObject current=null] : iv_ruleSTCoreSource= ruleSTCoreSource EOF ;
    public final EObject entryRuleSTCoreSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCoreSource = null;


        try {
            // InternalSTCoreParser.g:58:53: (iv_ruleSTCoreSource= ruleSTCoreSource EOF )
            // InternalSTCoreParser.g:59:2: iv_ruleSTCoreSource= ruleSTCoreSource EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTCoreSourceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTCoreSource=ruleSTCoreSource();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTCoreSource; 
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
    // $ANTLR end "entryRuleSTCoreSource"


    // $ANTLR start "ruleSTCoreSource"
    // InternalSTCoreParser.g:65:1: ruleSTCoreSource returns [EObject current=null] : ( () ( (lv_statements_1_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCoreSource() throws RecognitionException {
        EObject current = null;

        EObject lv_statements_1_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:71:2: ( ( () ( (lv_statements_1_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:72:2: ( () ( (lv_statements_1_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:72:2: ( () ( (lv_statements_1_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:73:3: () ( (lv_statements_1_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:73:3: ()
            // InternalSTCoreParser.g:74:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTCoreSourceAccess().getSTCoreSourceAction_0(),
              					current);
              			
            }

            }

            // InternalSTCoreParser.g:80:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=LDATE_AND_TIME && LA1_0<=TIME_OF_DAY)||LA1_0==CONTINUE||LA1_0==WSTRING||(LA1_0>=REPEAT && LA1_0<=STRING)||LA1_0==DWORD||(LA1_0>=FALSE && LA1_0<=ULINT)||(LA1_0>=USINT && LA1_0<=DINT)||(LA1_0>=EXIT && LA1_0<=SINT)||(LA1_0>=TIME && LA1_0<=TOD)||LA1_0==XOR||(LA1_0>=DT && LA1_0<=LT)||LA1_0==OR||LA1_0==LeftParenthesis||LA1_0==PlusSign||LA1_0==HyphenMinus||LA1_0==Semicolon||LA1_0==D||LA1_0==T||(LA1_0>=RULE_NON_DECIMAL && LA1_0<=RULE_INT)||(LA1_0>=RULE_ID && LA1_0<=RULE_STRING)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSTCoreParser.g:81:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:81:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:82:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCoreSourceAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_statements_1_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTCoreSourceRule());
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
            	    break loop1;
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
    // $ANTLR end "ruleSTCoreSource"


    // $ANTLR start "entryRuleSTVarDeclaration"
    // InternalSTCoreParser.g:103:1: entryRuleSTVarDeclaration returns [EObject current=null] : iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF ;
    public final EObject entryRuleSTVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclaration = null;


        try {
            // InternalSTCoreParser.g:103:57: (iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF )
            // InternalSTCoreParser.g:104:2: iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTVarDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTVarDeclaration=ruleSTVarDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTVarDeclaration; 
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
    // $ANTLR end "entryRuleSTVarDeclaration"


    // $ANTLR start "ruleSTVarDeclaration"
    // InternalSTCoreParser.g:110:1: ruleSTVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) ;
    public final EObject ruleSTVarDeclaration() throws RecognitionException {
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
            // InternalSTCoreParser.g:116:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) )
            // InternalSTCoreParser.g:117:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            {
            // InternalSTCoreParser.g:117:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            // InternalSTCoreParser.g:118:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon
            {
            // InternalSTCoreParser.g:118:3: ()
            // InternalSTCoreParser.g:119:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalSTCoreParser.g:125:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTCoreParser.g:126:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTCoreParser.g:126:4: (lv_name_1_0= RULE_ID )
            // InternalSTCoreParser.g:127:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getSTVarDeclarationAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTVarDeclarationRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
              				
            }

            }


            }

            // InternalSTCoreParser.g:143:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==AT) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalSTCoreParser.g:144:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalSTCoreParser.g:148:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSTCoreParser.g:149:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSTCoreParser.g:149:5: (otherlv_3= RULE_ID )
                    // InternalSTCoreParser.g:150:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Colon,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3());
              		
            }
            // InternalSTCoreParser.g:166:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ARRAY) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSTCoreParser.g:167:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalSTCoreParser.g:167:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalSTCoreParser.g:168:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalSTCoreParser.g:168:5: (lv_array_5_0= ARRAY )
                    // InternalSTCoreParser.g:169:6: lv_array_5_0= ARRAY
                    {
                    lv_array_5_0=(Token)match(input,ARRAY,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_array_5_0, grammarAccess.getSTVarDeclarationAccess().getArrayARRAYKeyword_4_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTVarDeclarationRule());
                      						}
                      						setWithLastConsumed(current, "array", lv_array_5_0 != null, "ARRAY");
                      					
                    }

                    }


                    }

                    // InternalSTCoreParser.g:181:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==LeftSquareBracket) ) {
                        int LA5_1 = input.LA(2);

                        if ( ((LA5_1>=LDATE_AND_TIME && LA5_1<=TIME_OF_DAY)||LA5_1==WSTRING||LA5_1==STRING||LA5_1==DWORD||(LA5_1>=FALSE && LA5_1<=ULINT)||(LA5_1>=USINT && LA5_1<=WCHAR)||(LA5_1>=BOOL && LA5_1<=BYTE)||(LA5_1>=CHAR && LA5_1<=DINT)||(LA5_1>=LINT && LA5_1<=SINT)||(LA5_1>=TIME && LA5_1<=AND)||(LA5_1>=INT && LA5_1<=TOD)||LA5_1==XOR||LA5_1==DT||(LA5_1>=LD && LA5_1<=LT)||LA5_1==OR||LA5_1==LeftParenthesis||LA5_1==PlusSign||LA5_1==HyphenMinus||LA5_1==D||LA5_1==T||(LA5_1>=RULE_NON_DECIMAL && LA5_1<=RULE_INT)||(LA5_1>=RULE_ID && LA5_1<=RULE_STRING)) ) {
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
                            // InternalSTCoreParser.g:182:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalSTCoreParser.g:182:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalSTCoreParser.g:183:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalSTCoreParser.g:187:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalSTCoreParser.g:188:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalSTCoreParser.g:188:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalSTCoreParser.g:189:8: lv_ranges_7_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_10);
                            lv_ranges_7_0=ruleSTExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
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

                            // InternalSTCoreParser.g:206:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop3:
                            do {
                                int alt3=2;
                                int LA3_0 = input.LA(1);

                                if ( (LA3_0==Comma) ) {
                                    alt3=1;
                                }


                                switch (alt3) {
                            	case 1 :
                            	    // InternalSTCoreParser.g:207:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTCoreParser.g:211:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalSTCoreParser.g:212:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalSTCoreParser.g:212:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalSTCoreParser.g:213:9: lv_ranges_9_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_10);
                            	    lv_ranges_9_0=ruleSTExpression();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
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

                              						newLeafNode(otherlv_10, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:237:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalSTCoreParser.g:237:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalSTCoreParser.g:238:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_12); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalSTCoreParser.g:242:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalSTCoreParser.g:243:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalSTCoreParser.g:243:7: (lv_count_12_0= Asterisk )
                            // InternalSTCoreParser.g:244:8: lv_count_12_0= Asterisk
                            {
                            lv_count_12_0=(Token)match(input,Asterisk,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_count_12_0, grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getSTVarDeclarationRule());
                              								}
                              								addWithLastConsumed(current, "count", lv_count_12_0, "*");
                              							
                            }

                            }


                            }

                            // InternalSTCoreParser.g:256:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop4:
                            do {
                                int alt4=2;
                                int LA4_0 = input.LA(1);

                                if ( (LA4_0==Comma) ) {
                                    alt4=1;
                                }


                                switch (alt4) {
                            	case 1 :
                            	    // InternalSTCoreParser.g:257:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_12); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTCoreParser.g:261:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalSTCoreParser.g:262:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalSTCoreParser.g:262:8: (lv_count_14_0= Asterisk )
                            	    // InternalSTCoreParser.g:263:9: lv_count_14_0= Asterisk
                            	    {
                            	    lv_count_14_0=(Token)match(input,Asterisk,FOLLOW_10); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									newLeafNode(lv_count_14_0, grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_2_1_0());
                            	      								
                            	    }
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElement(grammarAccess.getSTVarDeclarationRule());
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

                              						newLeafNode(otherlv_15, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,OF,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:287:3: ( ( ruleSTAnyType ) )
            // InternalSTCoreParser.g:288:4: ( ruleSTAnyType )
            {
            // InternalSTCoreParser.g:288:4: ( ruleSTAnyType )
            // InternalSTCoreParser.g:289:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTVarDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0());
              				
            }
            pushFollow(FOLLOW_13);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:303:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LeftSquareBracket) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSTCoreParser.g:304:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalSTCoreParser.g:308:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:309:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:309:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalSTCoreParser.g:310:6: lv_maxLength_19_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_14);
                    lv_maxLength_19_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
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

                      				newLeafNode(otherlv_20, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:332:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==ColonEqualsSign) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSTCoreParser.g:333:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalSTCoreParser.g:337:4: ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    // InternalSTCoreParser.g:338:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    {
                    // InternalSTCoreParser.g:338:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    // InternalSTCoreParser.g:339:6: lv_defaultValue_22_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_defaultValue_22_0=ruleSTInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"defaultValue",
                      							lv_defaultValue_22_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_23=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_23, grammarAccess.getSTVarDeclarationAccess().getSemicolonKeyword_8());
              		
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
    // $ANTLR end "ruleSTVarDeclaration"


    // $ANTLR start "entryRuleSTInitializerExpression"
    // InternalSTCoreParser.g:365:1: entryRuleSTInitializerExpression returns [EObject current=null] : iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF ;
    public final EObject entryRuleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpression = null;


        try {
            // InternalSTCoreParser.g:365:64: (iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF )
            // InternalSTCoreParser.g:366:2: iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTInitializerExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTInitializerExpression=ruleSTInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTInitializerExpression; 
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
    // $ANTLR end "entryRuleSTInitializerExpression"


    // $ANTLR start "ruleSTInitializerExpression"
    // InternalSTCoreParser.g:372:1: ruleSTInitializerExpression returns [EObject current=null] : (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression ) ;
    public final EObject ruleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STElementaryInitializerExpression_0 = null;

        EObject this_STArrayInitializerExpression_1 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:378:2: ( (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression ) )
            // InternalSTCoreParser.g:379:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression )
            {
            // InternalSTCoreParser.g:379:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=LDATE_AND_TIME && LA9_0<=TIME_OF_DAY)||LA9_0==WSTRING||LA9_0==STRING||LA9_0==DWORD||(LA9_0>=FALSE && LA9_0<=ULINT)||(LA9_0>=USINT && LA9_0<=WCHAR)||(LA9_0>=BOOL && LA9_0<=BYTE)||(LA9_0>=CHAR && LA9_0<=DINT)||(LA9_0>=LINT && LA9_0<=SINT)||(LA9_0>=TIME && LA9_0<=AND)||(LA9_0>=INT && LA9_0<=TOD)||LA9_0==XOR||LA9_0==DT||(LA9_0>=LD && LA9_0<=LT)||LA9_0==OR||LA9_0==LeftParenthesis||LA9_0==PlusSign||LA9_0==HyphenMinus||LA9_0==D||LA9_0==T||(LA9_0>=RULE_NON_DECIMAL && LA9_0<=RULE_INT)||(LA9_0>=RULE_ID && LA9_0<=RULE_STRING)) ) {
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
                    // InternalSTCoreParser.g:380:3: this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTInitializerExpressionAccess().getSTElementaryInitializerExpressionParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STElementaryInitializerExpression_0=ruleSTElementaryInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STElementaryInitializerExpression_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:389:3: this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTInitializerExpressionAccess().getSTArrayInitializerExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STArrayInitializerExpression_1=ruleSTArrayInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STArrayInitializerExpression_1;
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
    // $ANTLR end "ruleSTInitializerExpression"


    // $ANTLR start "entryRuleSTElementaryInitializerExpression"
    // InternalSTCoreParser.g:401:1: entryRuleSTElementaryInitializerExpression returns [EObject current=null] : iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF ;
    public final EObject entryRuleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElementaryInitializerExpression = null;


        try {
            // InternalSTCoreParser.g:401:74: (iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF )
            // InternalSTCoreParser.g:402:2: iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTElementaryInitializerExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTElementaryInitializerExpression=ruleSTElementaryInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTElementaryInitializerExpression; 
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
    // $ANTLR end "entryRuleSTElementaryInitializerExpression"


    // $ANTLR start "ruleSTElementaryInitializerExpression"
    // InternalSTCoreParser.g:408:1: ruleSTElementaryInitializerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:414:2: ( ( (lv_value_0_0= ruleSTExpression ) ) )
            // InternalSTCoreParser.g:415:2: ( (lv_value_0_0= ruleSTExpression ) )
            {
            // InternalSTCoreParser.g:415:2: ( (lv_value_0_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:416:3: (lv_value_0_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:416:3: (lv_value_0_0= ruleSTExpression )
            // InternalSTCoreParser.g:417:4: lv_value_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getSTElementaryInitializerExpressionAccess().getValueSTExpressionParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getSTElementaryInitializerExpressionRule());
              				}
              				set(
              					current,
              					"value",
              					lv_value_0_0,
              					"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              				afterParserOrEnumRuleCall();
              			
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
    // $ANTLR end "ruleSTElementaryInitializerExpression"


    // $ANTLR start "entryRuleSTArrayInitializerExpression"
    // InternalSTCoreParser.g:437:1: entryRuleSTArrayInitializerExpression returns [EObject current=null] : iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF ;
    public final EObject entryRuleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitializerExpression = null;


        try {
            // InternalSTCoreParser.g:437:69: (iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF )
            // InternalSTCoreParser.g:438:2: iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTArrayInitializerExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTArrayInitializerExpression=ruleSTArrayInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTArrayInitializerExpression; 
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
    // $ANTLR end "entryRuleSTArrayInitializerExpression"


    // $ANTLR start "ruleSTArrayInitializerExpression"
    // InternalSTCoreParser.g:444:1: ruleSTArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:450:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTCoreParser.g:451:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTCoreParser.g:451:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTCoreParser.g:452:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTCoreParser.g:456:3: ( (lv_values_1_0= ruleSTArrayInitElement ) )
            // InternalSTCoreParser.g:457:4: (lv_values_1_0= ruleSTArrayInitElement )
            {
            // InternalSTCoreParser.g:457:4: (lv_values_1_0= ruleSTArrayInitElement )
            // InternalSTCoreParser.g:458:5: lv_values_1_0= ruleSTArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_10);
            lv_values_1_0=ruleSTArrayInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTArrayInitializerExpressionRule());
              					}
              					add(
              						current,
              						"values",
              						lv_values_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STArrayInitElement");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:475:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==Comma) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSTCoreParser.g:476:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_16); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTCoreParser.g:480:4: ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    // InternalSTCoreParser.g:481:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    {
            	    // InternalSTCoreParser.g:481:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    // InternalSTCoreParser.g:482:6: lv_values_3_0= ruleSTArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_values_3_0=ruleSTArrayInitElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTArrayInitializerExpressionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"values",
            	      							lv_values_3_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STArrayInitElement");
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

              			newLeafNode(otherlv_4, grammarAccess.getSTArrayInitializerExpressionAccess().getRightSquareBracketKeyword_3());
              		
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
    // $ANTLR end "ruleSTArrayInitializerExpression"


    // $ANTLR start "entryRuleSTArrayInitElement"
    // InternalSTCoreParser.g:508:1: entryRuleSTArrayInitElement returns [EObject current=null] : iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF ;
    public final EObject entryRuleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitElement = null;


        try {
            // InternalSTCoreParser.g:508:59: (iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF )
            // InternalSTCoreParser.g:509:2: iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTArrayInitElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTArrayInitElement=ruleSTArrayInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTArrayInitElement; 
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
    // $ANTLR end "entryRuleSTArrayInitElement"


    // $ANTLR start "ruleSTArrayInitElement"
    // InternalSTCoreParser.g:515:1: ruleSTArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? ) ;
    public final EObject ruleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_indexOrInitExpression_0_0 = null;

        EObject lv_initExpressions_2_0 = null;

        EObject lv_initExpressions_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:521:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? ) )
            // InternalSTCoreParser.g:522:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? )
            {
            // InternalSTCoreParser.g:522:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? )
            // InternalSTCoreParser.g:523:3: ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )?
            {
            // InternalSTCoreParser.g:523:3: ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) )
            // InternalSTCoreParser.g:524:4: (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression )
            {
            // InternalSTCoreParser.g:524:4: (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression )
            // InternalSTCoreParser.g:525:5: lv_indexOrInitExpression_0_0= ruleSTInitializerExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getIndexOrInitExpressionSTInitializerExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_18);
            lv_indexOrInitExpression_0_0=ruleSTInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTArrayInitElementRule());
              					}
              					set(
              						current,
              						"indexOrInitExpression",
              						lv_indexOrInitExpression_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:542:3: (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==LeftParenthesis) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalSTCoreParser.g:543:4: otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalSTCoreParser.g:547:4: ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) )
                    // InternalSTCoreParser.g:548:5: (lv_initExpressions_2_0= ruleSTInitializerExpression )
                    {
                    // InternalSTCoreParser.g:548:5: (lv_initExpressions_2_0= ruleSTInitializerExpression )
                    // InternalSTCoreParser.g:549:6: lv_initExpressions_2_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_19);
                    lv_initExpressions_2_0=ruleSTInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTArrayInitElementRule());
                      						}
                      						add(
                      							current,
                      							"initExpressions",
                      							lv_initExpressions_2_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSTCoreParser.g:566:4: (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==Comma) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // InternalSTCoreParser.g:567:5: otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,Comma,FOLLOW_16); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getSTArrayInitElementAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalSTCoreParser.g:571:5: ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
                    	    // InternalSTCoreParser.g:572:6: (lv_initExpressions_4_0= ruleSTInitializerExpression )
                    	    {
                    	    // InternalSTCoreParser.g:572:6: (lv_initExpressions_4_0= ruleSTInitializerExpression )
                    	    // InternalSTCoreParser.g:573:7: lv_initExpressions_4_0= ruleSTInitializerExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_19);
                    	    lv_initExpressions_4_0=ruleSTInitializerExpression();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getSTArrayInitElementRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"initExpressions",
                    	      								lv_initExpressions_4_0,
                    	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getSTArrayInitElementAccess().getRightParenthesisKeyword_1_3());
                      			
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
    // $ANTLR end "ruleSTArrayInitElement"


    // $ANTLR start "entryRuleSTStatement"
    // InternalSTCoreParser.g:600:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTCoreParser.g:600:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTCoreParser.g:601:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalSTCoreParser.g:607:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) ;
    public final EObject ruleSTStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        EObject this_STIfStatement_0 = null;

        EObject this_STCaseStatement_1 = null;

        EObject this_STForStatement_2 = null;

        EObject this_STWhileStatement_3 = null;

        EObject this_STRepeatStatement_4 = null;

        EObject this_STAssignmentStatement_5 = null;

        EObject this_STCallStatement_6 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:613:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) )
            // InternalSTCoreParser.g:614:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            {
            // InternalSTCoreParser.g:614:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=LDATE_AND_TIME && LA14_0<=TIME_OF_DAY)||LA14_0==CONTINUE||LA14_0==WSTRING||(LA14_0>=REPEAT && LA14_0<=STRING)||LA14_0==DWORD||(LA14_0>=FALSE && LA14_0<=ULINT)||(LA14_0>=USINT && LA14_0<=DINT)||(LA14_0>=EXIT && LA14_0<=SINT)||(LA14_0>=TIME && LA14_0<=TOD)||LA14_0==XOR||(LA14_0>=DT && LA14_0<=LT)||LA14_0==OR||LA14_0==LeftParenthesis||LA14_0==PlusSign||LA14_0==HyphenMinus||LA14_0==D||LA14_0==T||(LA14_0>=RULE_NON_DECIMAL && LA14_0<=RULE_INT)||(LA14_0>=RULE_ID && LA14_0<=RULE_STRING)) ) {
                alt14=1;
            }
            else if ( (LA14_0==Semicolon) ) {
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
                    // InternalSTCoreParser.g:615:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    {
                    // InternalSTCoreParser.g:615:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    // InternalSTCoreParser.g:616:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon
                    {
                    // InternalSTCoreParser.g:616:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )
                    int alt13=10;
                    alt13 = dfa13.predict(input);
                    switch (alt13) {
                        case 1 :
                            // InternalSTCoreParser.g:617:5: this_STIfStatement_0= ruleSTIfStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTIfStatementParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STIfStatement_0=ruleSTIfStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STIfStatement_0;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTCoreParser.g:626:5: this_STCaseStatement_1= ruleSTCaseStatement
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
                            // InternalSTCoreParser.g:635:5: this_STForStatement_2= ruleSTForStatement
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
                            // InternalSTCoreParser.g:644:5: this_STWhileStatement_3= ruleSTWhileStatement
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
                            // InternalSTCoreParser.g:653:5: this_STRepeatStatement_4= ruleSTRepeatStatement
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
                            // InternalSTCoreParser.g:662:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            {
                            // InternalSTCoreParser.g:662:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            // InternalSTCoreParser.g:663:6: ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement
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


                            }
                            break;
                        case 7 :
                            // InternalSTCoreParser.g:674:5: this_STCallStatement_6= ruleSTCallStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCallStatementParserRuleCall_0_0_6());
                              				
                            }
                            pushFollow(FOLLOW_17);
                            this_STCallStatement_6=ruleSTCallStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STCallStatement_6;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 8 :
                            // InternalSTCoreParser.g:683:5: ( () otherlv_8= RETURN )
                            {
                            // InternalSTCoreParser.g:683:5: ( () otherlv_8= RETURN )
                            // InternalSTCoreParser.g:684:6: () otherlv_8= RETURN
                            {
                            // InternalSTCoreParser.g:684:6: ()
                            // InternalSTCoreParser.g:685:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_7_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_8=(Token)match(input,RETURN,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_8, grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_7_1());
                              					
                            }

                            }


                            }
                            break;
                        case 9 :
                            // InternalSTCoreParser.g:697:5: ( () otherlv_10= CONTINUE )
                            {
                            // InternalSTCoreParser.g:697:5: ( () otherlv_10= CONTINUE )
                            // InternalSTCoreParser.g:698:6: () otherlv_10= CONTINUE
                            {
                            // InternalSTCoreParser.g:698:6: ()
                            // InternalSTCoreParser.g:699:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_8_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_10=(Token)match(input,CONTINUE,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_8_1());
                              					
                            }

                            }


                            }
                            break;
                        case 10 :
                            // InternalSTCoreParser.g:711:5: ( () otherlv_12= EXIT )
                            {
                            // InternalSTCoreParser.g:711:5: ( () otherlv_12= EXIT )
                            // InternalSTCoreParser.g:712:6: () otherlv_12= EXIT
                            {
                            // InternalSTCoreParser.g:712:6: ()
                            // InternalSTCoreParser.g:713:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTExitAction_0_0_9_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_12=(Token)match(input,EXIT,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_12, grammarAccess.getSTStatementAccess().getEXITKeyword_0_0_9_1());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_13=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getSTStatementAccess().getSemicolonKeyword_0_1());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:731:3: ( () otherlv_15= Semicolon )
                    {
                    // InternalSTCoreParser.g:731:3: ( () otherlv_15= Semicolon )
                    // InternalSTCoreParser.g:732:4: () otherlv_15= Semicolon
                    {
                    // InternalSTCoreParser.g:732:4: ()
                    // InternalSTCoreParser.g:733:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTNopAction_1_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_15=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_15, grammarAccess.getSTStatementAccess().getSemicolonKeyword_1_1());
                      			
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
    // InternalSTCoreParser.g:748:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTCoreParser.g:748:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTCoreParser.g:749:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTCoreParser.g:755:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:761:2: ( ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) )
            // InternalSTCoreParser.g:762:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            {
            // InternalSTCoreParser.g:762:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            // InternalSTCoreParser.g:763:3: ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) )
            {
            // InternalSTCoreParser.g:763:3: ( (lv_left_0_0= ruleSTAccessExpression ) )
            // InternalSTCoreParser.g:764:4: (lv_left_0_0= ruleSTAccessExpression )
            {
            // InternalSTCoreParser.g:764:4: (lv_left_0_0= ruleSTAccessExpression )
            // InternalSTCoreParser.g:765:5: lv_left_0_0= ruleSTAccessExpression
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
            // InternalSTCoreParser.g:786:3: ( (lv_right_2_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:787:4: (lv_right_2_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:787:4: (lv_right_2_0= ruleSTExpression )
            // InternalSTCoreParser.g:788:5: lv_right_2_0= ruleSTExpression
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


    // $ANTLR start "entryRuleSTCallStatement"
    // InternalSTCoreParser.g:809:1: entryRuleSTCallStatement returns [EObject current=null] : iv_ruleSTCallStatement= ruleSTCallStatement EOF ;
    public final EObject entryRuleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallStatement = null;


        try {
            // InternalSTCoreParser.g:809:56: (iv_ruleSTCallStatement= ruleSTCallStatement EOF )
            // InternalSTCoreParser.g:810:2: iv_ruleSTCallStatement= ruleSTCallStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTCallStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTCallStatement=ruleSTCallStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTCallStatement; 
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
    // $ANTLR end "entryRuleSTCallStatement"


    // $ANTLR start "ruleSTCallStatement"
    // InternalSTCoreParser.g:816:1: ruleSTCallStatement returns [EObject current=null] : ( (lv_call_0_0= ruleSTAccessExpression ) ) ;
    public final EObject ruleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject lv_call_0_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:822:2: ( ( (lv_call_0_0= ruleSTAccessExpression ) ) )
            // InternalSTCoreParser.g:823:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            {
            // InternalSTCoreParser.g:823:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            // InternalSTCoreParser.g:824:3: (lv_call_0_0= ruleSTAccessExpression )
            {
            // InternalSTCoreParser.g:824:3: (lv_call_0_0= ruleSTAccessExpression )
            // InternalSTCoreParser.g:825:4: lv_call_0_0= ruleSTAccessExpression
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getSTCallStatementAccess().getCallSTAccessExpressionParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_call_0_0=ruleSTAccessExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getSTCallStatementRule());
              				}
              				set(
              					current,
              					"call",
              					lv_call_0_0,
              					"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAccessExpression");
              				afterParserOrEnumRuleCall();
              			
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
    // $ANTLR end "ruleSTCallStatement"


    // $ANTLR start "entryRuleSTCallArgument"
    // InternalSTCoreParser.g:845:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalSTCoreParser.g:845:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalSTCoreParser.g:846:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTCallArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTCallArgument=ruleSTCallArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTCallArgument; 
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
    // $ANTLR end "entryRuleSTCallArgument"


    // $ANTLR start "ruleSTCallArgument"
    // InternalSTCoreParser.g:852:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:858:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalSTCoreParser.g:859:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalSTCoreParser.g:859:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            int alt15=3;
            switch ( input.LA(1) ) {
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case WSTRING:
            case STRING:
            case DWORD:
            case FALSE:
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
            case TRUE:
            case UINT:
            case WORD:
            case AND:
            case INT:
            case LDT:
            case MOD:
            case TOD:
            case XOR:
            case DT:
            case LD:
            case LT:
            case OR:
            case LeftParenthesis:
            case PlusSign:
            case HyphenMinus:
            case D:
            case T:
            case RULE_NON_DECIMAL:
            case RULE_INT:
            case RULE_STRING:
                {
                alt15=1;
                }
                break;
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case EOF:
                case AND:
                case MOD:
                case XOR:
                case AsteriskAsterisk:
                case FullStopFullStop:
                case LessThanSignEqualsSign:
                case LessThanSignGreaterThanSign:
                case GreaterThanSignEqualsSign:
                case OR:
                case Ampersand:
                case LeftParenthesis:
                case RightParenthesis:
                case Asterisk:
                case PlusSign:
                case Comma:
                case HyphenMinus:
                case FullStop:
                case Solidus:
                case LessThanSign:
                case EqualsSign:
                case GreaterThanSign:
                case LeftSquareBracket:
                    {
                    alt15=1;
                    }
                    break;
                case ColonEqualsSign:
                    {
                    alt15=2;
                    }
                    break;
                case EqualsSignGreaterThanSign:
                    {
                    alt15=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 2, input);

                    throw nvae;
                }

                }
                break;
            case NOT:
                {
                int LA15_3 = input.LA(2);

                if ( (LA15_3==EOF||(LA15_3>=LDATE_AND_TIME && LA15_3<=TIME_OF_DAY)||LA15_3==WSTRING||LA15_3==STRING||LA15_3==DWORD||(LA15_3>=FALSE && LA15_3<=ULINT)||(LA15_3>=USINT && LA15_3<=WCHAR)||(LA15_3>=BOOL && LA15_3<=BYTE)||(LA15_3>=CHAR && LA15_3<=DINT)||(LA15_3>=LINT && LA15_3<=SINT)||(LA15_3>=TIME && LA15_3<=AND)||(LA15_3>=INT && LA15_3<=TOD)||LA15_3==XOR||(LA15_3>=AsteriskAsterisk && LA15_3<=FullStopFullStop)||(LA15_3>=LessThanSignEqualsSign && LA15_3<=LessThanSignGreaterThanSign)||LA15_3==GreaterThanSignEqualsSign||LA15_3==DT||(LA15_3>=LD && LA15_3<=LT)||LA15_3==OR||(LA15_3>=Ampersand && LA15_3<=Solidus)||(LA15_3>=LessThanSign && LA15_3<=D)||(LA15_3>=T && LA15_3<=LeftSquareBracket)||(LA15_3>=RULE_NON_DECIMAL && LA15_3<=RULE_INT)||LA15_3==RULE_STRING) ) {
                    alt15=1;
                }
                else if ( (LA15_3==RULE_ID) ) {
                    int LA15_6 = input.LA(3);

                    if ( (LA15_6==EOF||LA15_6==AND||LA15_6==MOD||LA15_6==XOR||(LA15_6>=AsteriskAsterisk && LA15_6<=FullStopFullStop)||(LA15_6>=LessThanSignEqualsSign && LA15_6<=LessThanSignGreaterThanSign)||LA15_6==GreaterThanSignEqualsSign||LA15_6==OR||(LA15_6>=Ampersand && LA15_6<=Solidus)||(LA15_6>=LessThanSign && LA15_6<=GreaterThanSign)||LA15_6==LeftSquareBracket) ) {
                        alt15=1;
                    }
                    else if ( (LA15_6==EqualsSignGreaterThanSign) ) {
                        alt15=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 6, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalSTCoreParser.g:860:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTCallArgumentAccess().getSTCallUnnamedArgumentParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STCallUnnamedArgument_0=ruleSTCallUnnamedArgument();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STCallUnnamedArgument_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:869:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTCallArgumentAccess().getSTCallNamedInputArgumentParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STCallNamedInputArgument_1=ruleSTCallNamedInputArgument();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STCallNamedInputArgument_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:878:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTCallArgumentAccess().getSTCallNamedOutputArgumentParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STCallNamedOutputArgument_2=ruleSTCallNamedOutputArgument();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STCallNamedOutputArgument_2;
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
    // $ANTLR end "ruleSTCallArgument"


    // $ANTLR start "entryRuleSTCallUnnamedArgument"
    // InternalSTCoreParser.g:890:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalSTCoreParser.g:890:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalSTCoreParser.g:891:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTCallUnnamedArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTCallUnnamedArgument=ruleSTCallUnnamedArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTCallUnnamedArgument; 
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
    // $ANTLR end "entryRuleSTCallUnnamedArgument"


    // $ANTLR start "ruleSTCallUnnamedArgument"
    // InternalSTCoreParser.g:897:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_arg_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_arg_0_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:903:2: ( ( (lv_arg_0_0= ruleSTExpression ) ) )
            // InternalSTCoreParser.g:904:2: ( (lv_arg_0_0= ruleSTExpression ) )
            {
            // InternalSTCoreParser.g:904:2: ( (lv_arg_0_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:905:3: (lv_arg_0_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:905:3: (lv_arg_0_0= ruleSTExpression )
            // InternalSTCoreParser.g:906:4: lv_arg_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getSTCallUnnamedArgumentAccess().getArgSTExpressionParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_arg_0_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getSTCallUnnamedArgumentRule());
              				}
              				set(
              					current,
              					"arg",
              					lv_arg_0_0,
              					"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              				afterParserOrEnumRuleCall();
              			
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
    // $ANTLR end "ruleSTCallUnnamedArgument"


    // $ANTLR start "entryRuleSTCallNamedInputArgument"
    // InternalSTCoreParser.g:926:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalSTCoreParser.g:926:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalSTCoreParser.g:927:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTCallNamedInputArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTCallNamedInputArgument=ruleSTCallNamedInputArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTCallNamedInputArgument; 
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
    // $ANTLR end "entryRuleSTCallNamedInputArgument"


    // $ANTLR start "ruleSTCallNamedInputArgument"
    // InternalSTCoreParser.g:933:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_source_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:939:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) ) )
            // InternalSTCoreParser.g:940:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) )
            {
            // InternalSTCoreParser.g:940:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) )
            // InternalSTCoreParser.g:941:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) )
            {
            // InternalSTCoreParser.g:941:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTCoreParser.g:942:4: (otherlv_0= RULE_ID )
            {
            // InternalSTCoreParser.g:942:4: (otherlv_0= RULE_ID )
            // InternalSTCoreParser.g:943:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTCallNamedInputArgumentAccess().getTargetINamedElementCrossReference_0_0());
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTCoreParser.g:958:3: ( (lv_source_2_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:959:4: (lv_source_2_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:959:4: (lv_source_2_0= ruleSTExpression )
            // InternalSTCoreParser.g:960:5: lv_source_2_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCallNamedInputArgumentAccess().getSourceSTExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_source_2_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              					set(
              						current,
              						"source",
              						lv_source_2_0,
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
    // $ANTLR end "ruleSTCallNamedInputArgument"


    // $ANTLR start "entryRuleSTCallNamedOutputArgument"
    // InternalSTCoreParser.g:981:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalSTCoreParser.g:981:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalSTCoreParser.g:982:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTCallNamedOutputArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTCallNamedOutputArgument=ruleSTCallNamedOutputArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTCallNamedOutputArgument; 
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
    // $ANTLR end "entryRuleSTCallNamedOutputArgument"


    // $ANTLR start "ruleSTCallNamedOutputArgument"
    // InternalSTCoreParser.g:988:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:994:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) ) )
            // InternalSTCoreParser.g:995:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) )
            {
            // InternalSTCoreParser.g:995:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) )
            // InternalSTCoreParser.g:996:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) )
            {
            // InternalSTCoreParser.g:996:3: ( (lv_not_0_0= NOT ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==NOT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSTCoreParser.g:997:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTCoreParser.g:997:4: (lv_not_0_0= NOT )
                    // InternalSTCoreParser.g:998:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_not_0_0, grammarAccess.getSTCallNamedOutputArgumentAccess().getNotNOTKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
                      					}
                      					setWithLastConsumed(current, "not", lv_not_0_0 != null, "NOT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTCoreParser.g:1010:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTCoreParser.g:1011:4: (otherlv_1= RULE_ID )
            {
            // InternalSTCoreParser.g:1011:4: (otherlv_1= RULE_ID )
            // InternalSTCoreParser.g:1012:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTCallNamedOutputArgumentAccess().getSourceINamedElementCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1027:3: ( (otherlv_3= RULE_ID ) )
            // InternalSTCoreParser.g:1028:4: (otherlv_3= RULE_ID )
            {
            // InternalSTCoreParser.g:1028:4: (otherlv_3= RULE_ID )
            // InternalSTCoreParser.g:1029:5: otherlv_3= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              				
            }
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_3, grammarAccess.getSTCallNamedOutputArgumentAccess().getTargetINamedElementCrossReference_3_0());
              				
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
    // $ANTLR end "ruleSTCallNamedOutputArgument"


    // $ANTLR start "entryRuleSTIfStatement"
    // InternalSTCoreParser.g:1044:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalSTCoreParser.g:1044:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalSTCoreParser.g:1045:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTIfStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTIfStatement=ruleSTIfStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTIfStatement; 
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
    // $ANTLR end "entryRuleSTIfStatement"


    // $ANTLR start "ruleSTIfStatement"
    // InternalSTCoreParser.g:1051:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
    public final EObject ruleSTIfStatement() throws RecognitionException {
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
            // InternalSTCoreParser.g:1057:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTCoreParser.g:1058:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTCoreParser.g:1058:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTCoreParser.g:1059:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1063:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1064:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1064:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1065:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_22);
            lv_condition_1_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTIfStatementRule());
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatementAccess().getTHENKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1086:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>=LDATE_AND_TIME && LA17_0<=TIME_OF_DAY)||LA17_0==CONTINUE||LA17_0==WSTRING||(LA17_0>=REPEAT && LA17_0<=STRING)||LA17_0==DWORD||(LA17_0>=FALSE && LA17_0<=ULINT)||(LA17_0>=USINT && LA17_0<=DINT)||(LA17_0>=EXIT && LA17_0<=SINT)||(LA17_0>=TIME && LA17_0<=TOD)||LA17_0==XOR||(LA17_0>=DT && LA17_0<=LT)||LA17_0==OR||LA17_0==LeftParenthesis||LA17_0==PlusSign||LA17_0==HyphenMinus||LA17_0==Semicolon||LA17_0==D||LA17_0==T||(LA17_0>=RULE_NON_DECIMAL && LA17_0<=RULE_INT)||(LA17_0>=RULE_ID && LA17_0<=RULE_STRING)) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSTCoreParser.g:1087:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1087:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1088:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_23);
            	    lv_statements_3_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTIfStatementRule());
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

            // InternalSTCoreParser.g:1105:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==ELSIF) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalSTCoreParser.g:1106:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTCoreParser.g:1106:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTCoreParser.g:1107:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_24);
            	    lv_elseifs_4_0=ruleSTElseIfPart();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTIfStatementRule());
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
            	    break loop18;
                }
            } while (true);

            // InternalSTCoreParser.g:1124:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ELSE) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSTCoreParser.g:1125:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTCoreParser.g:1125:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTCoreParser.g:1126:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_25);
                    lv_else_5_0=ruleSTElsePart();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTIfStatementRule());
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

              			newLeafNode(otherlv_6, grammarAccess.getSTIfStatementAccess().getEND_IFKeyword_6());
              		
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
    // $ANTLR end "ruleSTIfStatement"


    // $ANTLR start "entryRuleSTElseIfPart"
    // InternalSTCoreParser.g:1151:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTCoreParser.g:1151:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTCoreParser.g:1152:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTCoreParser.g:1158:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1164:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:1165:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:1165:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:1166:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1170:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1171:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1171:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1172:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_22);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_26); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1193:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=LDATE_AND_TIME && LA20_0<=TIME_OF_DAY)||LA20_0==CONTINUE||LA20_0==WSTRING||(LA20_0>=REPEAT && LA20_0<=STRING)||LA20_0==DWORD||(LA20_0>=FALSE && LA20_0<=ULINT)||(LA20_0>=USINT && LA20_0<=DINT)||(LA20_0>=EXIT && LA20_0<=SINT)||(LA20_0>=TIME && LA20_0<=TOD)||LA20_0==XOR||(LA20_0>=DT && LA20_0<=LT)||LA20_0==OR||LA20_0==LeftParenthesis||LA20_0==PlusSign||LA20_0==HyphenMinus||LA20_0==Semicolon||LA20_0==D||LA20_0==T||(LA20_0>=RULE_NON_DECIMAL && LA20_0<=RULE_INT)||(LA20_0>=RULE_ID && LA20_0<=RULE_STRING)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSTCoreParser.g:1194:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1194:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1195:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop20;
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
    // InternalSTCoreParser.g:1216:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTCoreParser.g:1216:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTCoreParser.g:1217:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTCoreParser.g:1223:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTCoreParser.g:1229:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTCoreParser.g:1230:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTCoreParser.g:1230:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTCoreParser.g:1231:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1235:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1236:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1236:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1237:5: lv_selector_1_0= ruleSTExpression
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
            // InternalSTCoreParser.g:1258:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt21=0;
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>=LDATE_AND_TIME && LA21_0<=TIME_OF_DAY)||LA21_0==WSTRING||LA21_0==STRING||LA21_0==DWORD||(LA21_0>=FALSE && LA21_0<=ULINT)||(LA21_0>=USINT && LA21_0<=WCHAR)||(LA21_0>=BOOL && LA21_0<=BYTE)||(LA21_0>=CHAR && LA21_0<=DINT)||(LA21_0>=LINT && LA21_0<=SINT)||(LA21_0>=TIME && LA21_0<=AND)||(LA21_0>=INT && LA21_0<=TOD)||LA21_0==XOR||LA21_0==DT||(LA21_0>=LD && LA21_0<=LT)||LA21_0==OR||LA21_0==LeftParenthesis||LA21_0==PlusSign||LA21_0==HyphenMinus||LA21_0==D||LA21_0==T||(LA21_0>=RULE_NON_DECIMAL && LA21_0<=RULE_INT)||(LA21_0>=RULE_ID && LA21_0<=RULE_STRING)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalSTCoreParser.g:1259:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTCoreParser.g:1259:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTCoreParser.g:1260:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_27);
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
            	    if ( cnt21 >= 1 ) break loop21;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
            } while (true);

            // InternalSTCoreParser.g:1277:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==ELSE) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSTCoreParser.g:1278:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTCoreParser.g:1278:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTCoreParser.g:1279:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_28);
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
    // InternalSTCoreParser.g:1304:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTCoreParser.g:1304:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTCoreParser.g:1305:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTCoreParser.g:1311:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1317:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:1318:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:1318:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:1319:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:1319:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1320:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1320:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTCoreParser.g:1321:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_29);
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

            // InternalSTCoreParser.g:1338:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==Comma) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalSTCoreParser.g:1339:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTCoreParser.g:1343:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTCoreParser.g:1344:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTCoreParser.g:1344:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTCoreParser.g:1345:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_29);
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
            	    break loop23;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_26); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1367:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop24:
            do {
                int alt24=2;
                alt24 = dfa24.predict(input);
                switch (alt24) {
            	case 1 :
            	    // InternalSTCoreParser.g:1368:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1372:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1373:5: lv_statements_4_0= ruleSTStatement
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTCoreParser.g:1394:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTCoreParser.g:1394:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTCoreParser.g:1395:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTCoreParser.g:1401:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1407:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTCoreParser.g:1408:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTCoreParser.g:1408:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTCoreParser.g:1409:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTCoreParser.g:1409:3: ()
            // InternalSTCoreParser.g:1410:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,ELSE,FOLLOW_26); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
              		
            }
            // InternalSTCoreParser.g:1420:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>=LDATE_AND_TIME && LA25_0<=TIME_OF_DAY)||LA25_0==CONTINUE||LA25_0==WSTRING||(LA25_0>=REPEAT && LA25_0<=STRING)||LA25_0==DWORD||(LA25_0>=FALSE && LA25_0<=ULINT)||(LA25_0>=USINT && LA25_0<=DINT)||(LA25_0>=EXIT && LA25_0<=SINT)||(LA25_0>=TIME && LA25_0<=TOD)||LA25_0==XOR||(LA25_0>=DT && LA25_0<=LT)||LA25_0==OR||LA25_0==LeftParenthesis||LA25_0==PlusSign||LA25_0==HyphenMinus||LA25_0==Semicolon||LA25_0==D||LA25_0==T||(LA25_0>=RULE_NON_DECIMAL && LA25_0<=RULE_INT)||(LA25_0>=RULE_ID && LA25_0<=RULE_STRING)) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalSTCoreParser.g:1421:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1421:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1422:5: lv_statements_2_0= ruleSTStatement
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
            	    break loop25;
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
    // InternalSTCoreParser.g:1443:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTCoreParser.g:1443:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTCoreParser.g:1444:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTCoreParser.g:1450:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
    public final EObject ruleSTForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_from_3_0 = null;

        EObject lv_to_5_0 = null;

        EObject lv_by_7_0 = null;

        EObject lv_statements_9_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1456:2: ( (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalSTCoreParser.g:1457:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalSTCoreParser.g:1457:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalSTCoreParser.g:1458:3: otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1462:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTCoreParser.g:1463:4: (otherlv_1= RULE_ID )
            {
            // InternalSTCoreParser.g:1463:4: (otherlv_1= RULE_ID )
            // InternalSTCoreParser.g:1464:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTForStatementRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTForStatementAccess().getVariableSTVarDeclarationCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1479:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1480:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1480:4: (lv_from_3_0= ruleSTExpression )
            // InternalSTCoreParser.g:1481:5: lv_from_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_30);
            lv_from_3_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTForStatementRule());
              					}
              					set(
              						current,
              						"from",
              						lv_from_3_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_4=(Token)match(input,TO,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getTOKeyword_4());
              		
            }
            // InternalSTCoreParser.g:1502:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1503:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1503:4: (lv_to_5_0= ruleSTExpression )
            // InternalSTCoreParser.g:1504:5: lv_to_5_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_to_5_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTForStatementRule());
              					}
              					set(
              						current,
              						"to",
              						lv_to_5_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:1521:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==BY) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalSTCoreParser.g:1522:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalSTCoreParser.g:1526:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:1527:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:1527:5: (lv_by_7_0= ruleSTExpression )
                    // InternalSTCoreParser.g:1528:6: lv_by_7_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_by_7_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTForStatementRule());
                      						}
                      						set(
                      							current,
                      							"by",
                      							lv_by_7_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,DO,FOLLOW_33); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
              		
            }
            // InternalSTCoreParser.g:1550:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>=LDATE_AND_TIME && LA27_0<=TIME_OF_DAY)||LA27_0==CONTINUE||LA27_0==WSTRING||(LA27_0>=REPEAT && LA27_0<=STRING)||LA27_0==DWORD||(LA27_0>=FALSE && LA27_0<=ULINT)||(LA27_0>=USINT && LA27_0<=DINT)||(LA27_0>=EXIT && LA27_0<=SINT)||(LA27_0>=TIME && LA27_0<=TOD)||LA27_0==XOR||(LA27_0>=DT && LA27_0<=LT)||LA27_0==OR||LA27_0==LeftParenthesis||LA27_0==PlusSign||LA27_0==HyphenMinus||LA27_0==Semicolon||LA27_0==D||LA27_0==T||(LA27_0>=RULE_NON_DECIMAL && LA27_0<=RULE_INT)||(LA27_0>=RULE_ID && LA27_0<=RULE_STRING)) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSTCoreParser.g:1551:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1551:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1552:5: lv_statements_9_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_33);
            	    lv_statements_9_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTForStatementRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_9_0,
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

            otherlv_10=(Token)match(input,END_FOR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_10, grammarAccess.getSTForStatementAccess().getEND_FORKeyword_9());
              		
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
    // InternalSTCoreParser.g:1577:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTCoreParser.g:1577:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTCoreParser.g:1578:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTCoreParser.g:1584:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1590:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTCoreParser.g:1591:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTCoreParser.g:1591:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTCoreParser.g:1592:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1596:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1597:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1597:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTCoreParser.g:1598:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_32);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalSTCoreParser.g:1619:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>=LDATE_AND_TIME && LA28_0<=TIME_OF_DAY)||LA28_0==CONTINUE||LA28_0==WSTRING||(LA28_0>=REPEAT && LA28_0<=STRING)||LA28_0==DWORD||(LA28_0>=FALSE && LA28_0<=ULINT)||(LA28_0>=USINT && LA28_0<=DINT)||(LA28_0>=EXIT && LA28_0<=SINT)||(LA28_0>=TIME && LA28_0<=TOD)||LA28_0==XOR||(LA28_0>=DT && LA28_0<=LT)||LA28_0==OR||LA28_0==LeftParenthesis||LA28_0==PlusSign||LA28_0==HyphenMinus||LA28_0==Semicolon||LA28_0==D||LA28_0==T||(LA28_0>=RULE_NON_DECIMAL && LA28_0<=RULE_INT)||(LA28_0>=RULE_ID && LA28_0<=RULE_STRING)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSTCoreParser.g:1620:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1620:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1621:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_34);
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
    // InternalSTCoreParser.g:1646:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTCoreParser.g:1646:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTCoreParser.g:1647:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTCoreParser.g:1653:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1659:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTCoreParser.g:1660:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTCoreParser.g:1660:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTCoreParser.g:1661:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_35); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTCoreParser.g:1665:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>=LDATE_AND_TIME && LA29_0<=TIME_OF_DAY)||LA29_0==CONTINUE||LA29_0==WSTRING||(LA29_0>=REPEAT && LA29_0<=STRING)||LA29_0==DWORD||(LA29_0>=FALSE && LA29_0<=ULINT)||(LA29_0>=USINT && LA29_0<=DINT)||(LA29_0>=EXIT && LA29_0<=SINT)||(LA29_0>=TIME && LA29_0<=TOD)||LA29_0==XOR||(LA29_0>=DT && LA29_0<=LT)||LA29_0==OR||LA29_0==LeftParenthesis||LA29_0==PlusSign||LA29_0==HyphenMinus||LA29_0==Semicolon||LA29_0==D||LA29_0==T||(LA29_0>=RULE_NON_DECIMAL && LA29_0<=RULE_INT)||(LA29_0>=RULE_ID && LA29_0<=RULE_STRING)) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSTCoreParser.g:1666:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTCoreParser.g:1666:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTCoreParser.g:1667:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_35);
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
            // InternalSTCoreParser.g:1688:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTCoreParser.g:1689:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTCoreParser.g:1689:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTCoreParser.g:1690:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_36);
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
    // InternalSTCoreParser.g:1715:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTCoreParser.g:1715:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTCoreParser.g:1716:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTCoreParser.g:1722:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1728:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTCoreParser.g:1729:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTCoreParser.g:1740:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTCoreParser.g:1740:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTCoreParser.g:1741:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTCoreParser.g:1747:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1753:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTCoreParser.g:1754:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1754:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTCoreParser.g:1755:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_37);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1763:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==FullStopFullStop) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSTCoreParser.g:1764:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1764:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTCoreParser.g:1765:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1765:5: ()
            	    // InternalSTCoreParser.g:1766:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1772:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTCoreParser.g:1773:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTCoreParser.g:1773:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTCoreParser.g:1774:7: lv_op_2_0= ruleSubrangeOperator
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

            	    // InternalSTCoreParser.g:1792:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTCoreParser.g:1793:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTCoreParser.g:1793:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTCoreParser.g:1794:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_37);
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
    // InternalSTCoreParser.g:1816:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTCoreParser.g:1816:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTCoreParser.g:1817:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTCoreParser.g:1823:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1829:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTCoreParser.g:1830:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1830:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTCoreParser.g:1831:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_38);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1839:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==OR) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTCoreParser.g:1840:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1840:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTCoreParser.g:1841:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1841:5: ()
            	    // InternalSTCoreParser.g:1842:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1848:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTCoreParser.g:1849:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTCoreParser.g:1849:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTCoreParser.g:1850:7: lv_op_2_0= ruleOrOperator
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

            	    // InternalSTCoreParser.g:1868:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTCoreParser.g:1869:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTCoreParser.g:1869:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTCoreParser.g:1870:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_38);
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
    // InternalSTCoreParser.g:1892:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTCoreParser.g:1892:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTCoreParser.g:1893:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTCoreParser.g:1899:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1905:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTCoreParser.g:1906:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1906:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTCoreParser.g:1907:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_39);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1915:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==XOR) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSTCoreParser.g:1916:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1916:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTCoreParser.g:1917:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1917:5: ()
            	    // InternalSTCoreParser.g:1918:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:1924:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTCoreParser.g:1925:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTCoreParser.g:1925:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTCoreParser.g:1926:7: lv_op_2_0= ruleXorOperator
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

            	    // InternalSTCoreParser.g:1944:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTCoreParser.g:1945:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTCoreParser.g:1945:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTCoreParser.g:1946:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_39);
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
    // InternalSTCoreParser.g:1968:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTCoreParser.g:1968:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTCoreParser.g:1969:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTCoreParser.g:1975:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:1981:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTCoreParser.g:1982:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTCoreParser.g:1982:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTCoreParser.g:1983:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_40);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:1991:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==AND||LA33_0==Ampersand) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSTCoreParser.g:1992:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTCoreParser.g:1992:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTCoreParser.g:1993:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTCoreParser.g:1993:5: ()
            	    // InternalSTCoreParser.g:1994:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2000:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTCoreParser.g:2001:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTCoreParser.g:2001:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTCoreParser.g:2002:7: lv_op_2_0= ruleAndOperator
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

            	    // InternalSTCoreParser.g:2020:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTCoreParser.g:2021:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTCoreParser.g:2021:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTCoreParser.g:2022:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_40);
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalSTCoreParser.g:2044:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTCoreParser.g:2044:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTCoreParser.g:2045:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTCoreParser.g:2051:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2057:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTCoreParser.g:2058:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2058:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTCoreParser.g:2059:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_41);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2067:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==LessThanSignGreaterThanSign||LA34_0==EqualsSign) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTCoreParser.g:2068:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2068:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTCoreParser.g:2069:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTCoreParser.g:2069:5: ()
            	    // InternalSTCoreParser.g:2070:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2076:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTCoreParser.g:2077:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTCoreParser.g:2077:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTCoreParser.g:2078:7: lv_op_2_0= ruleEqualityOperator
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

            	    // InternalSTCoreParser.g:2096:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTCoreParser.g:2097:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTCoreParser.g:2097:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTCoreParser.g:2098:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_41);
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalSTCoreParser.g:2120:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTCoreParser.g:2120:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTCoreParser.g:2121:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTCoreParser.g:2127:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2133:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTCoreParser.g:2134:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2134:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTCoreParser.g:2135:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_42);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2143:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==LessThanSignEqualsSign||LA35_0==GreaterThanSignEqualsSign||LA35_0==LessThanSign||LA35_0==GreaterThanSign) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSTCoreParser.g:2144:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2144:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTCoreParser.g:2145:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTCoreParser.g:2145:5: ()
            	    // InternalSTCoreParser.g:2146:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2152:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTCoreParser.g:2153:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTCoreParser.g:2153:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTCoreParser.g:2154:7: lv_op_2_0= ruleCompareOperator
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

            	    // InternalSTCoreParser.g:2172:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTCoreParser.g:2173:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTCoreParser.g:2173:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTCoreParser.g:2174:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_42);
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
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalSTCoreParser.g:2196:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTCoreParser.g:2196:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTCoreParser.g:2197:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTCoreParser.g:2203:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2209:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTCoreParser.g:2210:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2210:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTCoreParser.g:2211:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_43);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2219:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==PlusSign||LA36_0==HyphenMinus) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSTCoreParser.g:2220:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2220:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTCoreParser.g:2221:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTCoreParser.g:2221:5: ()
            	    // InternalSTCoreParser.g:2222:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2228:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTCoreParser.g:2229:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTCoreParser.g:2229:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTCoreParser.g:2230:7: lv_op_2_0= ruleAddSubOperator
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

            	    // InternalSTCoreParser.g:2248:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTCoreParser.g:2249:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTCoreParser.g:2249:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTCoreParser.g:2250:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_43);
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
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalSTCoreParser.g:2272:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTCoreParser.g:2272:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTCoreParser.g:2273:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTCoreParser.g:2279:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2285:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTCoreParser.g:2286:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2286:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTCoreParser.g:2287:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2295:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==MOD||LA37_0==Asterisk||LA37_0==Solidus) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalSTCoreParser.g:2296:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2296:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTCoreParser.g:2297:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTCoreParser.g:2297:5: ()
            	    // InternalSTCoreParser.g:2298:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2304:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTCoreParser.g:2305:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTCoreParser.g:2305:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTCoreParser.g:2306:7: lv_op_2_0= ruleMulDivModOperator
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

            	    // InternalSTCoreParser.g:2324:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTCoreParser.g:2325:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTCoreParser.g:2325:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTCoreParser.g:2326:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
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
            	    break loop37;
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
    // InternalSTCoreParser.g:2348:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTCoreParser.g:2348:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTCoreParser.g:2349:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTCoreParser.g:2355:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2361:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalSTCoreParser.g:2362:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalSTCoreParser.g:2362:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalSTCoreParser.g:2363:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_45);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2371:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==AsteriskAsterisk) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSTCoreParser.g:2372:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2372:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTCoreParser.g:2373:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTCoreParser.g:2373:5: ()
            	    // InternalSTCoreParser.g:2374:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTCoreParser.g:2380:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTCoreParser.g:2381:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTCoreParser.g:2381:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTCoreParser.g:2382:7: lv_op_2_0= rulePowerOperator
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

            	    // InternalSTCoreParser.g:2400:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalSTCoreParser.g:2401:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalSTCoreParser.g:2401:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalSTCoreParser.g:2402:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_45);
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTUnaryExpression"
    // InternalSTCoreParser.g:2424:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalSTCoreParser.g:2424:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalSTCoreParser.g:2425:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalSTCoreParser.g:2431:1: ruleSTUnaryExpression returns [EObject current=null] : ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2437:2: ( ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalSTCoreParser.g:2438:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalSTCoreParser.g:2438:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            int alt39=2;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // InternalSTCoreParser.g:2439:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    {
                    // InternalSTCoreParser.g:2439:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    // InternalSTCoreParser.g:2440:4: ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalSTCoreParser.g:2451:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalSTCoreParser.g:2451:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    // InternalSTCoreParser.g:2452:4: () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    {
                    // InternalSTCoreParser.g:2452:4: ()
                    // InternalSTCoreParser.g:2453:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTCoreParser.g:2459:4: ( (lv_op_2_0= ruleUnaryOperator ) )
                    // InternalSTCoreParser.g:2460:5: (lv_op_2_0= ruleUnaryOperator )
                    {
                    // InternalSTCoreParser.g:2460:5: (lv_op_2_0= ruleUnaryOperator )
                    // InternalSTCoreParser.g:2461:6: lv_op_2_0= ruleUnaryOperator
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

                    // InternalSTCoreParser.g:2478:4: ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    // InternalSTCoreParser.g:2479:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    {
                    // InternalSTCoreParser.g:2479:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    // InternalSTCoreParser.g:2480:6: lv_expression_3_0= ruleSTUnaryExpression
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
    // InternalSTCoreParser.g:2502:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalSTCoreParser.g:2502:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalSTCoreParser.g:2503:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalSTCoreParser.g:2509:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalSTCoreParser.g:2515:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalSTCoreParser.g:2516:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalSTCoreParser.g:2516:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalSTCoreParser.g:2517:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTCoreParser.g:2525:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop42:
            do {
                int alt42=3;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==FullStop) ) {
                    alt42=1;
                }
                else if ( (LA42_0==LeftSquareBracket) ) {
                    alt42=2;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSTCoreParser.g:2526:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalSTCoreParser.g:2526:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalSTCoreParser.g:2527:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalSTCoreParser.g:2527:5: ()
            	    // InternalSTCoreParser.g:2528:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_47); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalSTCoreParser.g:2538:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalSTCoreParser.g:2539:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalSTCoreParser.g:2539:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalSTCoreParser.g:2540:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalSTCoreParser.g:2540:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt40=2;
            	    int LA40_0 = input.LA(1);

            	    if ( (LA40_0==AND||(LA40_0>=MOD && LA40_0<=NOT)||LA40_0==XOR||LA40_0==DT||(LA40_0>=LD && LA40_0<=LT)||LA40_0==OR||LA40_0==D||LA40_0==RULE_ID) ) {
            	        alt40=1;
            	    }
            	    else if ( ((LA40_0>=B && LA40_0<=X)||LA40_0==LeftParenthesis||LA40_0==RULE_INT) ) {
            	        alt40=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 40, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt40) {
            	        case 1 :
            	            // InternalSTCoreParser.g:2541:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_46);
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
            	            // InternalSTCoreParser.g:2557:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_46);
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
            	    // InternalSTCoreParser.g:2577:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalSTCoreParser.g:2577:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalSTCoreParser.g:2578:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalSTCoreParser.g:2578:5: ()
            	    // InternalSTCoreParser.g:2579:6: 
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
            	    // InternalSTCoreParser.g:2589:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalSTCoreParser.g:2590:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalSTCoreParser.g:2590:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalSTCoreParser.g:2591:7: lv_index_6_0= ruleSTExpression
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

            	    // InternalSTCoreParser.g:2608:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop41:
            	    do {
            	        int alt41=2;
            	        int LA41_0 = input.LA(1);

            	        if ( (LA41_0==Comma) ) {
            	            alt41=1;
            	        }


            	        switch (alt41) {
            	    	case 1 :
            	    	    // InternalSTCoreParser.g:2609:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalSTCoreParser.g:2613:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalSTCoreParser.g:2614:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalSTCoreParser.g:2614:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalSTCoreParser.g:2615:8: lv_index_8_0= ruleSTExpression
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
            	    	    break loop41;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_46); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
            	      				
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
    // $ANTLR end "ruleSTAccessExpression"


    // $ANTLR start "entryRuleSTPrimaryExpression"
    // InternalSTCoreParser.g:2643:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalSTCoreParser.g:2643:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalSTCoreParser.g:2644:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalSTCoreParser.g:2650:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STLiteralExpressions_4 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2656:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) )
            // InternalSTCoreParser.g:2657:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            {
            // InternalSTCoreParser.g:2657:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            int alt43=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt43=1;
                }
                break;
            case AND:
            case MOD:
            case NOT:
            case XOR:
            case OR:
            case RULE_ID:
                {
                alt43=2;
                }
                break;
            case LT:
                {
                int LA43_3 = input.LA(2);

                if ( (LA43_3==EOF||LA43_3==END_REPEAT||LA43_3==THEN||LA43_3==AND||LA43_3==MOD||LA43_3==XOR||(LA43_3>=AsteriskAsterisk && LA43_3<=LessThanSignGreaterThanSign)||LA43_3==GreaterThanSignEqualsSign||(LA43_3>=BY && LA43_3<=DO)||(LA43_3>=OF && LA43_3<=TO)||(LA43_3>=Ampersand && LA43_3<=GreaterThanSign)||(LA43_3>=LeftSquareBracket && LA43_3<=RightSquareBracket)) ) {
                    alt43=2;
                }
                else if ( (LA43_3==NumberSign) ) {
                    alt43=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 3, input);

                    throw nvae;
                }
                }
                break;
            case D:
                {
                int LA43_4 = input.LA(2);

                if ( (LA43_4==NumberSign) ) {
                    alt43=3;
                }
                else if ( (LA43_4==EOF||LA43_4==END_REPEAT||LA43_4==THEN||LA43_4==AND||LA43_4==MOD||LA43_4==XOR||(LA43_4>=AsteriskAsterisk && LA43_4<=LessThanSignGreaterThanSign)||LA43_4==GreaterThanSignEqualsSign||(LA43_4>=BY && LA43_4<=DO)||(LA43_4>=OF && LA43_4<=TO)||(LA43_4>=Ampersand && LA43_4<=GreaterThanSign)||(LA43_4>=LeftSquareBracket && LA43_4<=RightSquareBracket)) ) {
                    alt43=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 4, input);

                    throw nvae;
                }
                }
                break;
            case DT:
                {
                int LA43_5 = input.LA(2);

                if ( (LA43_5==NumberSign) ) {
                    alt43=3;
                }
                else if ( (LA43_5==EOF||LA43_5==END_REPEAT||LA43_5==THEN||LA43_5==AND||LA43_5==MOD||LA43_5==XOR||(LA43_5>=AsteriskAsterisk && LA43_5<=LessThanSignGreaterThanSign)||LA43_5==GreaterThanSignEqualsSign||(LA43_5>=BY && LA43_5<=DO)||(LA43_5>=OF && LA43_5<=TO)||(LA43_5>=Ampersand && LA43_5<=GreaterThanSign)||(LA43_5>=LeftSquareBracket && LA43_5<=RightSquareBracket)) ) {
                    alt43=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 5, input);

                    throw nvae;
                }
                }
                break;
            case LD:
                {
                int LA43_6 = input.LA(2);

                if ( (LA43_6==EOF||LA43_6==END_REPEAT||LA43_6==THEN||LA43_6==AND||LA43_6==MOD||LA43_6==XOR||(LA43_6>=AsteriskAsterisk && LA43_6<=LessThanSignGreaterThanSign)||LA43_6==GreaterThanSignEqualsSign||(LA43_6>=BY && LA43_6<=DO)||(LA43_6>=OF && LA43_6<=TO)||(LA43_6>=Ampersand && LA43_6<=GreaterThanSign)||(LA43_6>=LeftSquareBracket && LA43_6<=RightSquareBracket)) ) {
                    alt43=2;
                }
                else if ( (LA43_6==NumberSign) ) {
                    alt43=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 6, input);

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
            case DWORD:
            case FALSE:
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
            case TRUE:
            case UINT:
            case WORD:
            case INT:
            case LDT:
            case TOD:
            case PlusSign:
            case HyphenMinus:
            case T:
            case RULE_NON_DECIMAL:
            case RULE_INT:
            case RULE_STRING:
                {
                alt43=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // InternalSTCoreParser.g:2658:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTCoreParser.g:2658:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTCoreParser.g:2659:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_48);
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
                    // InternalSTCoreParser.g:2677:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalSTCoreParser.g:2686:3: this_STLiteralExpressions_4= ruleSTLiteralExpressions
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
    // InternalSTCoreParser.g:2698:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalSTCoreParser.g:2698:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalSTCoreParser.g:2699:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalSTCoreParser.g:2705:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token lv_call_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2711:2: ( ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalSTCoreParser.g:2712:2: ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalSTCoreParser.g:2712:2: ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalSTCoreParser.g:2713:3: () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalSTCoreParser.g:2713:3: ()
            // InternalSTCoreParser.g:2714:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTCoreParser.g:2720:3: ( ( ruleSTFeatureName ) )
            // InternalSTCoreParser.g:2721:4: ( ruleSTFeatureName )
            {
            // InternalSTCoreParser.g:2721:4: ( ruleSTFeatureName )
            // InternalSTCoreParser.g:2722:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_18);
            ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTCoreParser.g:2736:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt46=2;
            alt46 = dfa46.predict(input);
            switch (alt46) {
                case 1 :
                    // InternalSTCoreParser.g:2737:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalSTCoreParser.g:2737:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) )
                    // InternalSTCoreParser.g:2738:5: ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalSTCoreParser.g:2742:5: (lv_call_2_0= LeftParenthesis )
                    // InternalSTCoreParser.g:2743:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_call_2_0, grammarAccess.getSTFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
                      						}
                      						setWithLastConsumed(current, "call", lv_call_2_0 != null, "(");
                      					
                    }

                    }


                    }

                    // InternalSTCoreParser.g:2755:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( ((LA45_0>=LDATE_AND_TIME && LA45_0<=TIME_OF_DAY)||LA45_0==WSTRING||LA45_0==STRING||LA45_0==DWORD||(LA45_0>=FALSE && LA45_0<=ULINT)||(LA45_0>=USINT && LA45_0<=WCHAR)||(LA45_0>=BOOL && LA45_0<=BYTE)||(LA45_0>=CHAR && LA45_0<=DINT)||(LA45_0>=LINT && LA45_0<=SINT)||(LA45_0>=TIME && LA45_0<=AND)||(LA45_0>=INT && LA45_0<=TOD)||LA45_0==XOR||LA45_0==DT||(LA45_0>=LD && LA45_0<=LT)||LA45_0==OR||LA45_0==LeftParenthesis||LA45_0==PlusSign||LA45_0==HyphenMinus||LA45_0==D||LA45_0==T||(LA45_0>=RULE_NON_DECIMAL && LA45_0<=RULE_INT)||(LA45_0>=RULE_ID && LA45_0<=RULE_STRING)) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // InternalSTCoreParser.g:2756:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalSTCoreParser.g:2756:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalSTCoreParser.g:2757:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalSTCoreParser.g:2757:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalSTCoreParser.g:2758:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_19);
                            lv_parameters_3_0=ruleSTCallArgument();

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
                              								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallArgument");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalSTCoreParser.g:2775:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop44:
                            do {
                                int alt44=2;
                                int LA44_0 = input.LA(1);

                                if ( (LA44_0==Comma) ) {
                                    alt44=1;
                                }


                                switch (alt44) {
                            	case 1 :
                            	    // InternalSTCoreParser.g:2776:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalSTCoreParser.g:2780:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalSTCoreParser.g:2781:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalSTCoreParser.g:2781:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalSTCoreParser.g:2782:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_19);
                            	    lv_parameters_5_0=ruleSTCallArgument();

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
                            	      									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallArgument");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop44;
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


    // $ANTLR start "entryRuleSTFeatureName"
    // InternalSTCoreParser.g:2810:1: entryRuleSTFeatureName returns [String current=null] : iv_ruleSTFeatureName= ruleSTFeatureName EOF ;
    public final String entryRuleSTFeatureName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTFeatureName = null;


        try {
            // InternalSTCoreParser.g:2810:53: (iv_ruleSTFeatureName= ruleSTFeatureName EOF )
            // InternalSTCoreParser.g:2811:2: iv_ruleSTFeatureName= ruleSTFeatureName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTFeatureNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTFeatureName=ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTFeatureName.getText(); 
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
    // $ANTLR end "entryRuleSTFeatureName"


    // $ANTLR start "ruleSTFeatureName"
    // InternalSTCoreParser.g:2817:1: ruleSTFeatureName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= LT | kw= AND | kw= OR | kw= XOR | kw= NOT | kw= MOD | kw= D | kw= DT | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTFeatureName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:2823:2: ( (this_ID_0= RULE_ID | kw= LT | kw= AND | kw= OR | kw= XOR | kw= NOT | kw= MOD | kw= D | kw= DT | kw= LD ) )
            // InternalSTCoreParser.g:2824:2: (this_ID_0= RULE_ID | kw= LT | kw= AND | kw= OR | kw= XOR | kw= NOT | kw= MOD | kw= D | kw= DT | kw= LD )
            {
            // InternalSTCoreParser.g:2824:2: (this_ID_0= RULE_ID | kw= LT | kw= AND | kw= OR | kw= XOR | kw= NOT | kw= MOD | kw= D | kw= DT | kw= LD )
            int alt47=10;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt47=1;
                }
                break;
            case LT:
                {
                alt47=2;
                }
                break;
            case AND:
                {
                alt47=3;
                }
                break;
            case OR:
                {
                alt47=4;
                }
                break;
            case XOR:
                {
                alt47=5;
                }
                break;
            case NOT:
                {
                alt47=6;
                }
                break;
            case MOD:
                {
                alt47=7;
                }
                break;
            case D:
                {
                alt47=8;
                }
                break;
            case DT:
                {
                alt47=9;
                }
                break;
            case LD:
                {
                alt47=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }

            switch (alt47) {
                case 1 :
                    // InternalSTCoreParser.g:2825:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ID_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_ID_0, grammarAccess.getSTFeatureNameAccess().getIDTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2833:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:2839:3: kw= AND
                    {
                    kw=(Token)match(input,AND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getANDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:2845:3: kw= OR
                    {
                    kw=(Token)match(input,OR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getORKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:2851:3: kw= XOR
                    {
                    kw=(Token)match(input,XOR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getXORKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTCoreParser.g:2857:3: kw= NOT
                    {
                    kw=(Token)match(input,NOT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getNOTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTCoreParser.g:2863:3: kw= MOD
                    {
                    kw=(Token)match(input,MOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getMODKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTCoreParser.g:2869:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTCoreParser.g:2875:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDTKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSTCoreParser.g:2881:3: kw= LD
                    {
                    kw=(Token)match(input,LD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLDKeyword_9());
                      		
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
    // $ANTLR end "ruleSTFeatureName"


    // $ANTLR start "entryRuleSTMultibitPartialExpression"
    // InternalSTCoreParser.g:2890:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalSTCoreParser.g:2890:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalSTCoreParser.g:2891:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalSTCoreParser.g:2897:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_specifier_1_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:2903:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) )
            // InternalSTCoreParser.g:2904:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            {
            // InternalSTCoreParser.g:2904:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            // InternalSTCoreParser.g:2905:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            {
            // InternalSTCoreParser.g:2905:3: ()
            // InternalSTCoreParser.g:2906:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTCoreParser.g:2912:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( ((LA48_0>=B && LA48_0<=X)) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalSTCoreParser.g:2913:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalSTCoreParser.g:2913:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalSTCoreParser.g:2914:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_50);
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

            // InternalSTCoreParser.g:2931:3: ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==RULE_INT) ) {
                alt49=1;
            }
            else if ( (LA49_0==LeftParenthesis) ) {
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
                    // InternalSTCoreParser.g:2932:4: ( (lv_index_2_0= RULE_INT ) )
                    {
                    // InternalSTCoreParser.g:2932:4: ( (lv_index_2_0= RULE_INT ) )
                    // InternalSTCoreParser.g:2933:5: (lv_index_2_0= RULE_INT )
                    {
                    // InternalSTCoreParser.g:2933:5: (lv_index_2_0= RULE_INT )
                    // InternalSTCoreParser.g:2934:6: lv_index_2_0= RULE_INT
                    {
                    lv_index_2_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_index_2_0, grammarAccess.getSTMultibitPartialExpressionAccess().getIndexINTTerminalRuleCall_2_0_0());
                      					
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
                    break;
                case 2 :
                    // InternalSTCoreParser.g:2951:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    {
                    // InternalSTCoreParser.g:2951:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    // InternalSTCoreParser.g:2952:5: otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
                      				
                    }
                    // InternalSTCoreParser.g:2956:5: ( (lv_expression_4_0= ruleSTExpression ) )
                    // InternalSTCoreParser.g:2957:6: (lv_expression_4_0= ruleSTExpression )
                    {
                    // InternalSTCoreParser.g:2957:6: (lv_expression_4_0= ruleSTExpression )
                    // InternalSTCoreParser.g:2958:7: lv_expression_4_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_48);
                    lv_expression_4_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getSTMultibitPartialExpressionRule());
                      							}
                      							set(
                      								current,
                      								"expression",
                      								lv_expression_4_0,
                      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getSTMultibitPartialExpressionAccess().getRightParenthesisKeyword_2_1_2());
                      				
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
    // $ANTLR end "ruleSTMultibitPartialExpression"


    // $ANTLR start "entryRuleSTLiteralExpressions"
    // InternalSTCoreParser.g:2985:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTCoreParser.g:2985:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTCoreParser.g:2986:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTCoreParser.g:2992:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
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
            // InternalSTCoreParser.g:2998:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalSTCoreParser.g:2999:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalSTCoreParser.g:2999:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt50=6;
            switch ( input.LA(1) ) {
            case DWORD:
            case FALSE:
            case LREAL:
            case LWORD:
            case UDINT:
            case ULINT:
            case USINT:
            case BOOL:
            case BYTE:
            case DINT:
            case LINT:
            case REAL:
            case SINT:
            case TRUE:
            case UINT:
            case WORD:
            case INT:
            case PlusSign:
            case HyphenMinus:
            case RULE_NON_DECIMAL:
            case RULE_INT:
                {
                alt50=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt50=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt50=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt50=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt50=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt50=6;
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
                    // InternalSTCoreParser.g:3000:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalSTCoreParser.g:3009:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalSTCoreParser.g:3018:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalSTCoreParser.g:3027:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalSTCoreParser.g:3036:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalSTCoreParser.g:3045:3: this_STStringLiteral_5= ruleSTStringLiteral
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
    // InternalSTCoreParser.g:3057:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalSTCoreParser.g:3057:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalSTCoreParser.g:3058:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTNumericLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTNumericLiteralType=ruleSTNumericLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTNumericLiteralType.getText(); 
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
    // InternalSTCoreParser.g:3064:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3070:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalSTCoreParser.g:3071:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalSTCoreParser.g:3071:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==DWORD||LA51_0==LWORD||(LA51_0>=BOOL && LA51_0<=BYTE)||LA51_0==WORD) ) {
                alt51=1;
            }
            else if ( (LA51_0==LREAL||(LA51_0>=UDINT && LA51_0<=ULINT)||LA51_0==USINT||LA51_0==DINT||LA51_0==LINT||(LA51_0>=REAL && LA51_0<=SINT)||LA51_0==UINT||LA51_0==INT) ) {
                alt51=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // InternalSTCoreParser.g:3072:3: this_STAnyBitType_0= ruleSTAnyBitType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTNumericLiteralTypeAccess().getSTAnyBitTypeParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyBitType_0=ruleSTAnyBitType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyBitType_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3083:3: this_STAnyNumType_1= ruleSTAnyNumType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTNumericLiteralTypeAccess().getSTAnyNumTypeParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyNumType_1=ruleSTAnyNumType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyNumType_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleSTNumericLiteralType"


    // $ANTLR start "entryRuleSTNumericLiteral"
    // InternalSTCoreParser.g:3097:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalSTCoreParser.g:3097:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalSTCoreParser.g:3098:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalSTCoreParser.g:3104:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_3=null;
        AntlrDatatypeRuleToken lv_value_2_1 = null;

        AntlrDatatypeRuleToken lv_value_2_2 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3110:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTCoreParser.g:3111:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTCoreParser.g:3111:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) )
            // InternalSTCoreParser.g:3112:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTCoreParser.g:3112:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==DWORD||LA52_0==LREAL||(LA52_0>=LWORD && LA52_0<=ULINT)||LA52_0==USINT||(LA52_0>=BOOL && LA52_0<=BYTE)||LA52_0==DINT||LA52_0==LINT||(LA52_0>=REAL && LA52_0<=SINT)||(LA52_0>=UINT && LA52_0<=WORD)||LA52_0==INT) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalSTCoreParser.g:3113:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTCoreParser.g:3113:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalSTCoreParser.g:3114:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalSTCoreParser.g:3114:5: ( ruleSTNumericLiteralType )
                    // InternalSTCoreParser.g:3115:6: ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_51);
                    ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:3134:3: ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) )
            // InternalSTCoreParser.g:3135:4: ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) )
            {
            // InternalSTCoreParser.g:3135:4: ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) )
            // InternalSTCoreParser.g:3136:5: (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL )
            {
            // InternalSTCoreParser.g:3136:5: (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL )
            int alt53=3;
            switch ( input.LA(1) ) {
            case FALSE:
            case TRUE:
                {
                alt53=1;
                }
                break;
            case PlusSign:
            case HyphenMinus:
            case RULE_INT:
                {
                alt53=2;
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt53=3;
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
                    // InternalSTCoreParser.g:3137:6: lv_value_2_1= ruleBoolLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueBoolLiteralParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_2_1=ruleBoolLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_2_1,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.BoolLiteral");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3153:6: lv_value_2_2= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueNumberParserRuleCall_1_0_1());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_2_2=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_2_2,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3169:6: lv_value_2_3= RULE_NON_DECIMAL
                    {
                    lv_value_2_3=(Token)match(input,RULE_NON_DECIMAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_2_3, grammarAccess.getSTNumericLiteralAccess().getValueNON_DECIMALTerminalRuleCall_1_0_2());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"value",
                      							lv_value_2_3,
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
    // InternalSTCoreParser.g:3190:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalSTCoreParser.g:3190:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalSTCoreParser.g:3191:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTDateLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTDateLiteralType=ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTDateLiteralType.getText(); 
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
    // InternalSTCoreParser.g:3197:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3203:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalSTCoreParser.g:3204:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalSTCoreParser.g:3204:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt54=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt54=1;
                }
                break;
            case D:
                {
                alt54=2;
                }
                break;
            case LD:
                {
                alt54=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }

            switch (alt54) {
                case 1 :
                    // InternalSTCoreParser.g:3205:3: this_STDateType_0= ruleSTDateType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTDateLiteralTypeAccess().getSTDateTypeParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STDateType_0=ruleSTDateType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STDateType_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3216:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3222:3: kw= LD
                    {
                    kw=(Token)match(input,LD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getLDKeyword_2());
                      		
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
    // InternalSTCoreParser.g:3231:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalSTCoreParser.g:3231:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalSTCoreParser.g:3232:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalSTCoreParser.g:3238:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3244:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalSTCoreParser.g:3245:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalSTCoreParser.g:3245:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalSTCoreParser.g:3246:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalSTCoreParser.g:3246:3: ( ( ruleSTDateLiteralType ) )
            // InternalSTCoreParser.g:3247:4: ( ruleSTDateLiteralType )
            {
            // InternalSTCoreParser.g:3247:4: ( ruleSTDateLiteralType )
            // InternalSTCoreParser.g:3248:5: ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_51);
            ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTCoreParser.g:3266:3: ( (lv_value_2_0= ruleDate ) )
            // InternalSTCoreParser.g:3267:4: (lv_value_2_0= ruleDate )
            {
            // InternalSTCoreParser.g:3267:4: (lv_value_2_0= ruleDate )
            // InternalSTCoreParser.g:3268:5: lv_value_2_0= ruleDate
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getValueDateParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTDateLiteralRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
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
    // InternalSTCoreParser.g:3289:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalSTCoreParser.g:3289:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalSTCoreParser.g:3290:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTTimeLiteralTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTTimeLiteralType=ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTTimeLiteralType.getText(); 
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
    // InternalSTCoreParser.g:3296:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3302:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalSTCoreParser.g:3303:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalSTCoreParser.g:3303:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt55=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt55=1;
                }
                break;
            case T:
                {
                alt55=2;
                }
                break;
            case LT:
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
                    // InternalSTCoreParser.g:3304:3: this_STAnyDurationType_0= ruleSTAnyDurationType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTTimeLiteralTypeAccess().getSTAnyDurationTypeParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyDurationType_0=ruleSTAnyDurationType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyDurationType_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3315:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3321:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getLTKeyword_2());
                      		
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
    // InternalSTCoreParser.g:3330:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalSTCoreParser.g:3330:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalSTCoreParser.g:3331:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalSTCoreParser.g:3337:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3343:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalSTCoreParser.g:3344:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalSTCoreParser.g:3344:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalSTCoreParser.g:3345:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalSTCoreParser.g:3345:3: ( ( ruleSTTimeLiteralType ) )
            // InternalSTCoreParser.g:3346:4: ( ruleSTTimeLiteralType )
            {
            // InternalSTCoreParser.g:3346:4: ( ruleSTTimeLiteralType )
            // InternalSTCoreParser.g:3347:5: ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_51);
            ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTCoreParser.g:3365:3: ( (lv_value_2_0= ruleTime ) )
            // InternalSTCoreParser.g:3366:4: (lv_value_2_0= ruleTime )
            {
            // InternalSTCoreParser.g:3366:4: (lv_value_2_0= ruleTime )
            // InternalSTCoreParser.g:3367:5: lv_value_2_0= ruleTime
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getValueTimeParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleTime();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTTimeLiteralRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
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


    // $ANTLR start "entryRuleSTTimeOfDayLiteral"
    // InternalSTCoreParser.g:3388:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalSTCoreParser.g:3388:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalSTCoreParser.g:3389:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalSTCoreParser.g:3395:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3401:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalSTCoreParser.g:3402:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalSTCoreParser.g:3402:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalSTCoreParser.g:3403:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalSTCoreParser.g:3403:3: ( ( ruleSTTimeOfDayType ) )
            // InternalSTCoreParser.g:3404:4: ( ruleSTTimeOfDayType )
            {
            // InternalSTCoreParser.g:3404:4: ( ruleSTTimeOfDayType )
            // InternalSTCoreParser.g:3405:5: ruleSTTimeOfDayType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_51);
            ruleSTTimeOfDayType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTCoreParser.g:3423:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalSTCoreParser.g:3424:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalSTCoreParser.g:3424:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalSTCoreParser.g:3425:5: lv_value_2_0= ruleTimeOfDay
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getValueTimeOfDayParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleTimeOfDay();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
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


    // $ANTLR start "entryRuleSTDateAndTimeLiteral"
    // InternalSTCoreParser.g:3446:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalSTCoreParser.g:3446:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalSTCoreParser.g:3447:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalSTCoreParser.g:3453:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3459:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalSTCoreParser.g:3460:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalSTCoreParser.g:3460:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalSTCoreParser.g:3461:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalSTCoreParser.g:3461:3: ( ( ruleSTDateAndTimeType ) )
            // InternalSTCoreParser.g:3462:4: ( ruleSTDateAndTimeType )
            {
            // InternalSTCoreParser.g:3462:4: ( ruleSTDateAndTimeType )
            // InternalSTCoreParser.g:3463:5: ruleSTDateAndTimeType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_51);
            ruleSTDateAndTimeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTCoreParser.g:3481:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalSTCoreParser.g:3482:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalSTCoreParser.g:3482:4: (lv_value_2_0= ruleDateAndTime )
            // InternalSTCoreParser.g:3483:5: lv_value_2_0= ruleDateAndTime
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getValueDateAndTimeParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleDateAndTime();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.DateAndTime");
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


    // $ANTLR start "entryRuleSTStringLiteral"
    // InternalSTCoreParser.g:3504:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalSTCoreParser.g:3504:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalSTCoreParser.g:3505:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalSTCoreParser.g:3511:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3517:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) )
            // InternalSTCoreParser.g:3518:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            {
            // InternalSTCoreParser.g:3518:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            // InternalSTCoreParser.g:3519:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) )
            {
            // InternalSTCoreParser.g:3519:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==WSTRING||LA56_0==STRING||LA56_0==WCHAR||LA56_0==CHAR) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalSTCoreParser.g:3520:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTCoreParser.g:3520:4: ( ( ruleSTAnyCharsType ) )
                    // InternalSTCoreParser.g:3521:5: ( ruleSTAnyCharsType )
                    {
                    // InternalSTCoreParser.g:3521:5: ( ruleSTAnyCharsType )
                    // InternalSTCoreParser.g:3522:6: ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_51);
                    ruleSTAnyCharsType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTCoreParser.g:3541:3: ( (lv_value_2_0= RULE_STRING ) )
            // InternalSTCoreParser.g:3542:4: (lv_value_2_0= RULE_STRING )
            {
            // InternalSTCoreParser.g:3542:4: (lv_value_2_0= RULE_STRING )
            // InternalSTCoreParser.g:3543:5: lv_value_2_0= RULE_STRING
            {
            lv_value_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_value_2_0, grammarAccess.getSTStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTStringLiteralRule());
              					}
              					setWithLastConsumed(
              						current,
              						"value",
              						lv_value_2_0,
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
    // $ANTLR end "ruleSTStringLiteral"


    // $ANTLR start "entryRuleSTAnyType"
    // InternalSTCoreParser.g:3563:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalSTCoreParser.g:3563:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalSTCoreParser.g:3564:2: iv_ruleSTAnyType= ruleSTAnyType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAnyTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAnyType=ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAnyType.getText(); 
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
    // $ANTLR end "entryRuleSTAnyType"


    // $ANTLR start "ruleSTAnyType"
    // InternalSTCoreParser.g:3570:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3576:2: ( (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalSTCoreParser.g:3577:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalSTCoreParser.g:3577:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==RULE_ID) ) {
                alt57=1;
            }
            else if ( ((LA57_0>=LDATE_AND_TIME && LA57_0<=TIME_OF_DAY)||LA57_0==WSTRING||LA57_0==STRING||LA57_0==DWORD||(LA57_0>=LDATE && LA57_0<=ULINT)||(LA57_0>=USINT && LA57_0<=WCHAR)||(LA57_0>=BOOL && LA57_0<=BYTE)||(LA57_0>=CHAR && LA57_0<=DINT)||(LA57_0>=LINT && LA57_0<=SINT)||LA57_0==TIME||(LA57_0>=UINT && LA57_0<=WORD)||(LA57_0>=INT && LA57_0<=LDT)||LA57_0==TOD||LA57_0==DT) ) {
                alt57=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // InternalSTCoreParser.g:3578:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ID_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_ID_0, grammarAccess.getSTAnyTypeAccess().getIDTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3586:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyTypeAccess().getSTAnyBuiltinTypeParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyBuiltinType_1=ruleSTAnyBuiltinType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyBuiltinType_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleSTAnyType"


    // $ANTLR start "entryRuleSTAnyBuiltinType"
    // InternalSTCoreParser.g:3600:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalSTCoreParser.g:3600:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalSTCoreParser.g:3601:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAnyBuiltinTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAnyBuiltinType=ruleSTAnyBuiltinType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAnyBuiltinType.getText(); 
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
    // $ANTLR end "entryRuleSTAnyBuiltinType"


    // $ANTLR start "ruleSTAnyBuiltinType"
    // InternalSTCoreParser.g:3607:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3613:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalSTCoreParser.g:3614:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalSTCoreParser.g:3614:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt58=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt58=1;
                }
                break;
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
                {
                alt58=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt58=3;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LDATE:
            case DATE:
            case LTOD:
            case LDT:
            case TOD:
            case DT:
                {
                alt58=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
                {
                alt58=5;
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
                    // InternalSTCoreParser.g:3615:3: this_STAnyBitType_0= ruleSTAnyBitType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyBitTypeParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyBitType_0=ruleSTAnyBitType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyBitType_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3626:3: this_STAnyNumType_1= ruleSTAnyNumType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyNumTypeParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyNumType_1=ruleSTAnyNumType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyNumType_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3637:3: this_STAnyDurationType_2= ruleSTAnyDurationType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyDurationTypeParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyDurationType_2=ruleSTAnyDurationType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyDurationType_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3648:3: this_STAnyDateType_3= ruleSTAnyDateType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyDateTypeParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyDateType_3=ruleSTAnyDateType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyDateType_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:3659:3: this_STAnyCharsType_4= ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyCharsTypeParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAnyCharsType_4=ruleSTAnyCharsType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STAnyCharsType_4);
                      		
                    }
                    if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleSTAnyBuiltinType"


    // $ANTLR start "entryRuleSTAnyBitType"
    // InternalSTCoreParser.g:3673:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalSTCoreParser.g:3673:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalSTCoreParser.g:3674:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAnyBitTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAnyBitType=ruleSTAnyBitType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAnyBitType.getText(); 
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
    // $ANTLR end "entryRuleSTAnyBitType"


    // $ANTLR start "ruleSTAnyBitType"
    // InternalSTCoreParser.g:3680:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3686:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalSTCoreParser.g:3687:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalSTCoreParser.g:3687:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt59=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt59=1;
                }
                break;
            case BYTE:
                {
                alt59=2;
                }
                break;
            case WORD:
                {
                alt59=3;
                }
                break;
            case DWORD:
                {
                alt59=4;
                }
                break;
            case LWORD:
                {
                alt59=5;
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
                    // InternalSTCoreParser.g:3688:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3694:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3700:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3706:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:3712:3: kw= LWORD
                    {
                    kw=(Token)match(input,LWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getLWORDKeyword_4());
                      		
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
    // $ANTLR end "ruleSTAnyBitType"


    // $ANTLR start "entryRuleSTAnyNumType"
    // InternalSTCoreParser.g:3721:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalSTCoreParser.g:3721:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalSTCoreParser.g:3722:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAnyNumTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAnyNumType=ruleSTAnyNumType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAnyNumType.getText(); 
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
    // $ANTLR end "entryRuleSTAnyNumType"


    // $ANTLR start "ruleSTAnyNumType"
    // InternalSTCoreParser.g:3728:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3734:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalSTCoreParser.g:3735:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalSTCoreParser.g:3735:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt60=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt60=1;
                }
                break;
            case INT:
                {
                alt60=2;
                }
                break;
            case DINT:
                {
                alt60=3;
                }
                break;
            case LINT:
                {
                alt60=4;
                }
                break;
            case USINT:
                {
                alt60=5;
                }
                break;
            case UINT:
                {
                alt60=6;
                }
                break;
            case UDINT:
                {
                alt60=7;
                }
                break;
            case ULINT:
                {
                alt60=8;
                }
                break;
            case REAL:
                {
                alt60=9;
                }
                break;
            case LREAL:
                {
                alt60=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }

            switch (alt60) {
                case 1 :
                    // InternalSTCoreParser.g:3736:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3742:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3748:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3754:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTCoreParser.g:3760:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTCoreParser.g:3766:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTCoreParser.g:3772:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTCoreParser.g:3778:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTCoreParser.g:3784:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSTCoreParser.g:3790:3: kw= LREAL
                    {
                    kw=(Token)match(input,LREAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLREALKeyword_9());
                      		
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
    // $ANTLR end "ruleSTAnyNumType"


    // $ANTLR start "entryRuleSTAnyDurationType"
    // InternalSTCoreParser.g:3799:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalSTCoreParser.g:3799:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalSTCoreParser.g:3800:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAnyDurationTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAnyDurationType=ruleSTAnyDurationType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAnyDurationType.getText(); 
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
    // $ANTLR end "entryRuleSTAnyDurationType"


    // $ANTLR start "ruleSTAnyDurationType"
    // InternalSTCoreParser.g:3806:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3812:2: ( (kw= TIME | kw= LTIME ) )
            // InternalSTCoreParser.g:3813:2: (kw= TIME | kw= LTIME )
            {
            // InternalSTCoreParser.g:3813:2: (kw= TIME | kw= LTIME )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==TIME) ) {
                alt61=1;
            }
            else if ( (LA61_0==LTIME) ) {
                alt61=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    // InternalSTCoreParser.g:3814:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3820:3: kw= LTIME
                    {
                    kw=(Token)match(input,LTIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getLTIMEKeyword_1());
                      		
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
    // $ANTLR end "ruleSTAnyDurationType"


    // $ANTLR start "entryRuleSTAnyDateType"
    // InternalSTCoreParser.g:3829:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalSTCoreParser.g:3829:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalSTCoreParser.g:3830:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAnyDateTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAnyDateType=ruleSTAnyDateType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAnyDateType.getText(); 
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
    // $ANTLR end "entryRuleSTAnyDateType"


    // $ANTLR start "ruleSTAnyDateType"
    // InternalSTCoreParser.g:3836:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:3842:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalSTCoreParser.g:3843:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalSTCoreParser.g:3843:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt62=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt62=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt62=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt62=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }

            switch (alt62) {
                case 1 :
                    // InternalSTCoreParser.g:3844:3: this_STDateType_0= ruleSTDateType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyDateTypeAccess().getSTDateTypeParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STDateType_0=ruleSTDateType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STDateType_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3855:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyDateTypeAccess().getSTTimeOfDayTypeParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STTimeOfDayType_1=ruleSTTimeOfDayType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STTimeOfDayType_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3866:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyDateTypeAccess().getSTDateAndTimeTypeParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STDateAndTimeType_2=ruleSTDateAndTimeType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STDateAndTimeType_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleSTAnyDateType"


    // $ANTLR start "entryRuleSTDateType"
    // InternalSTCoreParser.g:3880:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalSTCoreParser.g:3880:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalSTCoreParser.g:3881:2: iv_ruleSTDateType= ruleSTDateType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTDateTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTDateType=ruleSTDateType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTDateType.getText(); 
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
    // $ANTLR end "entryRuleSTDateType"


    // $ANTLR start "ruleSTDateType"
    // InternalSTCoreParser.g:3887:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3893:2: ( (kw= DATE | kw= LDATE ) )
            // InternalSTCoreParser.g:3894:2: (kw= DATE | kw= LDATE )
            {
            // InternalSTCoreParser.g:3894:2: (kw= DATE | kw= LDATE )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==DATE) ) {
                alt63=1;
            }
            else if ( (LA63_0==LDATE) ) {
                alt63=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // InternalSTCoreParser.g:3895:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3901:3: kw= LDATE
                    {
                    kw=(Token)match(input,LDATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getLDATEKeyword_1());
                      		
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
    // $ANTLR end "ruleSTDateType"


    // $ANTLR start "entryRuleSTTimeOfDayType"
    // InternalSTCoreParser.g:3910:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalSTCoreParser.g:3910:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalSTCoreParser.g:3911:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTTimeOfDayTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTTimeOfDayType=ruleSTTimeOfDayType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTTimeOfDayType.getText(); 
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
    // $ANTLR end "entryRuleSTTimeOfDayType"


    // $ANTLR start "ruleSTTimeOfDayType"
    // InternalSTCoreParser.g:3917:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3923:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalSTCoreParser.g:3924:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalSTCoreParser.g:3924:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt64=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt64=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt64=2;
                }
                break;
            case TOD:
                {
                alt64=3;
                }
                break;
            case LTOD:
                {
                alt64=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }

            switch (alt64) {
                case 1 :
                    // InternalSTCoreParser.g:3925:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3931:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3937:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3943:3: kw= LTOD
                    {
                    kw=(Token)match(input,LTOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTODKeyword_3());
                      		
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
    // $ANTLR end "ruleSTTimeOfDayType"


    // $ANTLR start "entryRuleSTDateAndTimeType"
    // InternalSTCoreParser.g:3952:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalSTCoreParser.g:3952:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalSTCoreParser.g:3953:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTDateAndTimeTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTDateAndTimeType=ruleSTDateAndTimeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTDateAndTimeType.getText(); 
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
    // $ANTLR end "entryRuleSTDateAndTimeType"


    // $ANTLR start "ruleSTDateAndTimeType"
    // InternalSTCoreParser.g:3959:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:3965:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalSTCoreParser.g:3966:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalSTCoreParser.g:3966:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt65=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt65=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt65=2;
                }
                break;
            case DT:
                {
                alt65=3;
                }
                break;
            case LDT:
                {
                alt65=4;
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
                    // InternalSTCoreParser.g:3967:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:3973:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:3979:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:3985:3: kw= LDT
                    {
                    kw=(Token)match(input,LDT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDTKeyword_3());
                      		
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
    // $ANTLR end "ruleSTDateAndTimeType"


    // $ANTLR start "entryRuleSTAnyCharsType"
    // InternalSTCoreParser.g:3994:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalSTCoreParser.g:3994:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalSTCoreParser.g:3995:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAnyCharsTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAnyCharsType=ruleSTAnyCharsType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAnyCharsType.getText(); 
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
    // $ANTLR end "entryRuleSTAnyCharsType"


    // $ANTLR start "ruleSTAnyCharsType"
    // InternalSTCoreParser.g:4001:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4007:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalSTCoreParser.g:4008:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalSTCoreParser.g:4008:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt66=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt66=1;
                }
                break;
            case WSTRING:
                {
                alt66=2;
                }
                break;
            case CHAR:
                {
                alt66=3;
                }
                break;
            case WCHAR:
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
                    // InternalSTCoreParser.g:4009:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:4015:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTCoreParser.g:4021:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTCoreParser.g:4027:3: kw= WCHAR
                    {
                    kw=(Token)match(input,WCHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWCHARKeyword_3());
                      		
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
    // $ANTLR end "ruleSTAnyCharsType"


    // $ANTLR start "entryRuleBoolLiteral"
    // InternalSTCoreParser.g:4036:1: entryRuleBoolLiteral returns [String current=null] : iv_ruleBoolLiteral= ruleBoolLiteral EOF ;
    public final String entryRuleBoolLiteral() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoolLiteral = null;


        try {
            // InternalSTCoreParser.g:4036:51: (iv_ruleBoolLiteral= ruleBoolLiteral EOF )
            // InternalSTCoreParser.g:4037:2: iv_ruleBoolLiteral= ruleBoolLiteral EOF
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
    // InternalSTCoreParser.g:4043:1: ruleBoolLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE ) ;
    public final AntlrDatatypeRuleToken ruleBoolLiteral() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4049:2: ( (kw= TRUE | kw= FALSE ) )
            // InternalSTCoreParser.g:4050:2: (kw= TRUE | kw= FALSE )
            {
            // InternalSTCoreParser.g:4050:2: (kw= TRUE | kw= FALSE )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==TRUE) ) {
                alt67=1;
            }
            else if ( (LA67_0==FALSE) ) {
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
                    // InternalSTCoreParser.g:4051:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getBoolLiteralAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:4057:3: kw= FALSE
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
    // InternalSTCoreParser.g:4066:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;


        try {
            // InternalSTCoreParser.g:4066:46: (iv_ruleNumber= ruleNumber EOF )
            // InternalSTCoreParser.g:4067:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalSTCoreParser.g:4073:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_EXT_INT_4=null;
        Token this_INT_5=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4079:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) )
            // InternalSTCoreParser.g:4080:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            {
            // InternalSTCoreParser.g:4080:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            // InternalSTCoreParser.g:4081:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            {
            // InternalSTCoreParser.g:4081:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt68=3;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==PlusSign) ) {
                alt68=1;
            }
            else if ( (LA68_0==HyphenMinus) ) {
                alt68=2;
            }
            switch (alt68) {
                case 1 :
                    // InternalSTCoreParser.g:4082:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTCoreParser.g:4088:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1());
              		
            }
            // InternalSTCoreParser.g:4101:3: ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==FullStop) ) {
                int LA70_1 = input.LA(2);

                if ( (LA70_1==RULE_EXT_INT) && (synpred5_InternalSTCoreParser())) {
                    alt70=1;
                }
                else if ( (LA70_1==RULE_INT) ) {
                    int LA70_4 = input.LA(3);

                    if ( (synpred5_InternalSTCoreParser()) ) {
                        alt70=1;
                    }
                }
            }
            switch (alt70) {
                case 1 :
                    // InternalSTCoreParser.g:4102:4: ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    {
                    // InternalSTCoreParser.g:4102:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTCoreParser.g:4103:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_57); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTCoreParser.g:4110:4: (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==RULE_EXT_INT) ) {
                        alt69=1;
                    }
                    else if ( (LA69_0==RULE_INT) ) {
                        alt69=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 69, 0, input);

                        throw nvae;
                    }
                    switch (alt69) {
                        case 1 :
                            // InternalSTCoreParser.g:4111:5: this_EXT_INT_4= RULE_EXT_INT
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
                            // InternalSTCoreParser.g:4119:5: this_INT_5= RULE_INT
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
    // InternalSTCoreParser.g:4132:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalSTCoreParser.g:4132:44: (iv_ruleDate= ruleDate EOF )
            // InternalSTCoreParser.g:4133:2: iv_ruleDate= ruleDate EOF
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
    // InternalSTCoreParser.g:4139:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4145:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTCoreParser.g:4146:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTCoreParser.g:4146:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTCoreParser.g:4147:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_53); if (state.failed) return current;
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


    // $ANTLR start "entryRuleDateAndTime"
    // InternalSTCoreParser.g:4182:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalSTCoreParser.g:4182:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalSTCoreParser.g:4183:2: iv_ruleDateAndTime= ruleDateAndTime EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDateAndTimeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDateAndTime=ruleDateAndTime();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDateAndTime.getText(); 
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
    // $ANTLR end "entryRuleDateAndTime"


    // $ANTLR start "ruleDateAndTime"
    // InternalSTCoreParser.g:4189:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDateAndTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;
        Token this_INT_8=null;
        Token this_INT_10=null;
        Token this_INT_12=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4195:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) )
            // InternalSTCoreParser.g:4196:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            {
            // InternalSTCoreParser.g:4196:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            // InternalSTCoreParser.g:4197:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_5());
              		
            }
            this_INT_6=(Token)match(input,RULE_INT,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_6);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_6, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_6());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_7());
              		
            }
            this_INT_8=(Token)match(input,RULE_INT,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_8);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_8, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_8());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_9());
              		
            }
            this_INT_10=(Token)match(input,RULE_INT,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_10);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
              		
            }
            // InternalSTCoreParser.g:4264:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==FullStop) ) {
                int LA71_1 = input.LA(2);

                if ( (LA71_1==RULE_INT) ) {
                    int LA71_3 = input.LA(3);

                    if ( (synpred6_InternalSTCoreParser()) ) {
                        alt71=1;
                    }
                }
            }
            switch (alt71) {
                case 1 :
                    // InternalSTCoreParser.g:4265:4: ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT
                    {
                    // InternalSTCoreParser.g:4265:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTCoreParser.g:4266:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getFullStopKeyword_11_0());
                      				
                    }

                    }

                    this_INT_12=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_12);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_12, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_11_1());
                      			
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
    // $ANTLR end "ruleDateAndTime"


    // $ANTLR start "entryRuleTimeOfDay"
    // InternalSTCoreParser.g:4285:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalSTCoreParser.g:4285:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalSTCoreParser.g:4286:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalSTCoreParser.g:4292:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4298:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) )
            // InternalSTCoreParser.g:4299:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            {
            // InternalSTCoreParser.g:4299:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            // InternalSTCoreParser.g:4300:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_53); if (state.failed) return current;
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
            kw=(Token)match(input,Colon,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTCoreParser.g:4331:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==FullStop) ) {
                int LA72_1 = input.LA(2);

                if ( (LA72_1==RULE_INT) ) {
                    int LA72_3 = input.LA(3);

                    if ( (synpred7_InternalSTCoreParser()) ) {
                        alt72=1;
                    }
                }
            }
            switch (alt72) {
                case 1 :
                    // InternalSTCoreParser.g:4332:4: ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT
                    {
                    // InternalSTCoreParser.g:4332:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTCoreParser.g:4333:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_53); if (state.failed) return current;
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
    // InternalSTCoreParser.g:4352:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;


        try {
            // InternalSTCoreParser.g:4352:44: (iv_ruleTime= ruleTime EOF )
            // InternalSTCoreParser.g:4353:2: iv_ruleTime= ruleTime EOF
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
    // InternalSTCoreParser.g:4359:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Number_0 = null;



        	enterRule();

        try {
            // InternalSTCoreParser.g:4365:2: ( (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ )
            // InternalSTCoreParser.g:4366:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            {
            // InternalSTCoreParser.g:4366:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            int cnt75=0;
            loop75:
            do {
                int alt75=2;
                switch ( input.LA(1) ) {
                case PlusSign:
                    {
                    int LA75_2 = input.LA(2);

                    if ( (LA75_2==RULE_INT) ) {
                        int LA75_5 = input.LA(3);

                        if ( (LA75_5==FullStop) ) {
                            int LA75_6 = input.LA(4);

                            if ( (LA75_6==RULE_EXT_INT) ) {
                                int LA75_7 = input.LA(5);

                                if ( ((LA75_7>=MS && LA75_7<=NS)||LA75_7==US||(LA75_7>=D && LA75_7<=S)) ) {
                                    alt75=1;
                                }


                            }
                            else if ( (LA75_6==RULE_INT) ) {
                                int LA75_8 = input.LA(5);

                                if ( ((LA75_8>=MS && LA75_8<=NS)||LA75_8==US||(LA75_8>=D && LA75_8<=S)) ) {
                                    alt75=1;
                                }


                            }


                        }
                        else if ( ((LA75_5>=MS && LA75_5<=NS)||LA75_5==US||(LA75_5>=D && LA75_5<=S)) ) {
                            alt75=1;
                        }


                    }


                    }
                    break;
                case HyphenMinus:
                    {
                    int LA75_3 = input.LA(2);

                    if ( (LA75_3==RULE_INT) ) {
                        int LA75_5 = input.LA(3);

                        if ( (LA75_5==FullStop) ) {
                            int LA75_6 = input.LA(4);

                            if ( (LA75_6==RULE_EXT_INT) ) {
                                int LA75_7 = input.LA(5);

                                if ( ((LA75_7>=MS && LA75_7<=NS)||LA75_7==US||(LA75_7>=D && LA75_7<=S)) ) {
                                    alt75=1;
                                }


                            }
                            else if ( (LA75_6==RULE_INT) ) {
                                int LA75_8 = input.LA(5);

                                if ( ((LA75_8>=MS && LA75_8<=NS)||LA75_8==US||(LA75_8>=D && LA75_8<=S)) ) {
                                    alt75=1;
                                }


                            }


                        }
                        else if ( ((LA75_5>=MS && LA75_5<=NS)||LA75_5==US||(LA75_5>=D && LA75_5<=S)) ) {
                            alt75=1;
                        }


                    }


                    }
                    break;
                case RULE_INT:
                    {
                    alt75=1;
                    }
                    break;

                }

                switch (alt75) {
            	case 1 :
            	    // InternalSTCoreParser.g:4367:3: this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )?
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getTimeAccess().getNumberParserRuleCall_0());
            	      		
            	    }
            	    pushFollow(FOLLOW_59);
            	    this_Number_0=ruleNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_Number_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }
            	    // InternalSTCoreParser.g:4377:3: (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS )
            	    int alt73=7;
            	    switch ( input.LA(1) ) {
            	    case D:
            	        {
            	        alt73=1;
            	        }
            	        break;
            	    case H:
            	        {
            	        alt73=2;
            	        }
            	        break;
            	    case M:
            	        {
            	        alt73=3;
            	        }
            	        break;
            	    case S:
            	        {
            	        alt73=4;
            	        }
            	        break;
            	    case MS:
            	        {
            	        alt73=5;
            	        }
            	        break;
            	    case US:
            	        {
            	        alt73=6;
            	        }
            	        break;
            	    case NS:
            	        {
            	        alt73=7;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 73, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt73) {
            	        case 1 :
            	            // InternalSTCoreParser.g:4378:4: kw= D
            	            {
            	            kw=(Token)match(input,D,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getDKeyword_1_0());
            	              			
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTCoreParser.g:4384:4: kw= H
            	            {
            	            kw=(Token)match(input,H,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getHKeyword_1_1());
            	              			
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTCoreParser.g:4390:4: kw= M
            	            {
            	            kw=(Token)match(input,M,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMKeyword_1_2());
            	              			
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTCoreParser.g:4396:4: kw= S
            	            {
            	            kw=(Token)match(input,S,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getSKeyword_1_3());
            	              			
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalSTCoreParser.g:4402:4: kw= MS
            	            {
            	            kw=(Token)match(input,MS,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMSKeyword_1_4());
            	              			
            	            }

            	            }
            	            break;
            	        case 6 :
            	            // InternalSTCoreParser.g:4408:4: kw= US
            	            {
            	            kw=(Token)match(input,US,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getUSKeyword_1_5());
            	              			
            	            }

            	            }
            	            break;
            	        case 7 :
            	            // InternalSTCoreParser.g:4414:4: kw= NS
            	            {
            	            kw=(Token)match(input,NS,FOLLOW_60); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getNSKeyword_1_6());
            	              			
            	            }

            	            }
            	            break;

            	    }

            	    // InternalSTCoreParser.g:4420:3: (kw= KW__ )?
            	    int alt74=2;
            	    int LA74_0 = input.LA(1);

            	    if ( (LA74_0==KW__) ) {
            	        alt74=1;
            	    }
            	    switch (alt74) {
            	        case 1 :
            	            // InternalSTCoreParser.g:4421:4: kw= KW__
            	            {
            	            kw=(Token)match(input,KW__,FOLLOW_61); if (state.failed) return current;
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
            	    if ( cnt75 >= 1 ) break loop75;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(75, input);
                        throw eee;
                }
                cnt75++;
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
    // InternalSTCoreParser.g:4431:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4437:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTCoreParser.g:4438:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTCoreParser.g:4438:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTCoreParser.g:4439:3: enumLiteral_0= FullStopFullStop
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
    // InternalSTCoreParser.g:4448:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4454:2: ( (enumLiteral_0= OR ) )
            // InternalSTCoreParser.g:4455:2: (enumLiteral_0= OR )
            {
            // InternalSTCoreParser.g:4455:2: (enumLiteral_0= OR )
            // InternalSTCoreParser.g:4456:3: enumLiteral_0= OR
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
    // InternalSTCoreParser.g:4465:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4471:2: ( (enumLiteral_0= XOR ) )
            // InternalSTCoreParser.g:4472:2: (enumLiteral_0= XOR )
            {
            // InternalSTCoreParser.g:4472:2: (enumLiteral_0= XOR )
            // InternalSTCoreParser.g:4473:3: enumLiteral_0= XOR
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
    // InternalSTCoreParser.g:4482:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4488:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTCoreParser.g:4489:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTCoreParser.g:4489:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==AND) ) {
                alt76=1;
            }
            else if ( (LA76_0==Ampersand) ) {
                alt76=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }
            switch (alt76) {
                case 1 :
                    // InternalSTCoreParser.g:4490:3: (enumLiteral_0= AND )
                    {
                    // InternalSTCoreParser.g:4490:3: (enumLiteral_0= AND )
                    // InternalSTCoreParser.g:4491:4: enumLiteral_0= AND
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
                    // InternalSTCoreParser.g:4498:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTCoreParser.g:4498:3: (enumLiteral_1= Ampersand )
                    // InternalSTCoreParser.g:4499:4: enumLiteral_1= Ampersand
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
    // InternalSTCoreParser.g:4509:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4515:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTCoreParser.g:4516:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTCoreParser.g:4516:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==EqualsSign) ) {
                alt77=1;
            }
            else if ( (LA77_0==LessThanSignGreaterThanSign) ) {
                alt77=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }
            switch (alt77) {
                case 1 :
                    // InternalSTCoreParser.g:4517:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTCoreParser.g:4517:3: (enumLiteral_0= EqualsSign )
                    // InternalSTCoreParser.g:4518:4: enumLiteral_0= EqualsSign
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
                    // InternalSTCoreParser.g:4525:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTCoreParser.g:4525:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTCoreParser.g:4526:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalSTCoreParser.g:4536:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4542:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTCoreParser.g:4543:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTCoreParser.g:4543:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt78=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt78=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt78=2;
                }
                break;
            case GreaterThanSign:
                {
                alt78=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt78=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // InternalSTCoreParser.g:4544:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTCoreParser.g:4544:3: (enumLiteral_0= LessThanSign )
                    // InternalSTCoreParser.g:4545:4: enumLiteral_0= LessThanSign
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
                    // InternalSTCoreParser.g:4552:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTCoreParser.g:4552:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTCoreParser.g:4553:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalSTCoreParser.g:4560:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTCoreParser.g:4560:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTCoreParser.g:4561:4: enumLiteral_2= GreaterThanSign
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
                    // InternalSTCoreParser.g:4568:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTCoreParser.g:4568:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTCoreParser.g:4569:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalSTCoreParser.g:4579:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4585:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTCoreParser.g:4586:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTCoreParser.g:4586:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==PlusSign) ) {
                alt79=1;
            }
            else if ( (LA79_0==HyphenMinus) ) {
                alt79=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }
            switch (alt79) {
                case 1 :
                    // InternalSTCoreParser.g:4587:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTCoreParser.g:4587:3: (enumLiteral_0= PlusSign )
                    // InternalSTCoreParser.g:4588:4: enumLiteral_0= PlusSign
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
                    // InternalSTCoreParser.g:4595:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTCoreParser.g:4595:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTCoreParser.g:4596:4: enumLiteral_1= HyphenMinus
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
    // InternalSTCoreParser.g:4606:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4612:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTCoreParser.g:4613:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTCoreParser.g:4613:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt80=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt80=1;
                }
                break;
            case Solidus:
                {
                alt80=2;
                }
                break;
            case MOD:
                {
                alt80=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // InternalSTCoreParser.g:4614:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTCoreParser.g:4614:3: (enumLiteral_0= Asterisk )
                    // InternalSTCoreParser.g:4615:4: enumLiteral_0= Asterisk
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
                    // InternalSTCoreParser.g:4622:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTCoreParser.g:4622:3: (enumLiteral_1= Solidus )
                    // InternalSTCoreParser.g:4623:4: enumLiteral_1= Solidus
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
                    // InternalSTCoreParser.g:4630:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTCoreParser.g:4630:3: (enumLiteral_2= MOD )
                    // InternalSTCoreParser.g:4631:4: enumLiteral_2= MOD
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
    // InternalSTCoreParser.g:4641:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4647:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTCoreParser.g:4648:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTCoreParser.g:4648:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTCoreParser.g:4649:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalSTCoreParser.g:4658:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4664:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTCoreParser.g:4665:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTCoreParser.g:4665:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt81=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt81=1;
                }
                break;
            case PlusSign:
                {
                alt81=2;
                }
                break;
            case NOT:
                {
                alt81=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }

            switch (alt81) {
                case 1 :
                    // InternalSTCoreParser.g:4666:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTCoreParser.g:4666:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTCoreParser.g:4667:4: enumLiteral_0= HyphenMinus
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
                    // InternalSTCoreParser.g:4674:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTCoreParser.g:4674:3: (enumLiteral_1= PlusSign )
                    // InternalSTCoreParser.g:4675:4: enumLiteral_1= PlusSign
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
                    // InternalSTCoreParser.g:4682:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTCoreParser.g:4682:3: (enumLiteral_2= NOT )
                    // InternalSTCoreParser.g:4683:4: enumLiteral_2= NOT
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
    // InternalSTCoreParser.g:4693:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSTCoreParser.g:4699:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalSTCoreParser.g:4700:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalSTCoreParser.g:4700:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt82=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt82=1;
                }
                break;
            case D_1:
                {
                alt82=2;
                }
                break;
            case W:
                {
                alt82=3;
                }
                break;
            case B:
                {
                alt82=4;
                }
                break;
            case X:
                {
                alt82=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // InternalSTCoreParser.g:4701:3: (enumLiteral_0= L )
                    {
                    // InternalSTCoreParser.g:4701:3: (enumLiteral_0= L )
                    // InternalSTCoreParser.g:4702:4: enumLiteral_0= L
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
                    // InternalSTCoreParser.g:4709:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTCoreParser.g:4709:3: (enumLiteral_1= D_1 )
                    // InternalSTCoreParser.g:4710:4: enumLiteral_1= D_1
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
                    // InternalSTCoreParser.g:4717:3: (enumLiteral_2= W )
                    {
                    // InternalSTCoreParser.g:4717:3: (enumLiteral_2= W )
                    // InternalSTCoreParser.g:4718:4: enumLiteral_2= W
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
                    // InternalSTCoreParser.g:4725:3: (enumLiteral_3= B )
                    {
                    // InternalSTCoreParser.g:4725:3: (enumLiteral_3= B )
                    // InternalSTCoreParser.g:4726:4: enumLiteral_3= B
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
                    // InternalSTCoreParser.g:4733:3: (enumLiteral_4= X )
                    {
                    // InternalSTCoreParser.g:4733:3: (enumLiteral_4= X )
                    // InternalSTCoreParser.g:4734:4: enumLiteral_4= X
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
        // InternalSTCoreParser.g:663:6: ( ruleSTAssignmentStatement )
        // InternalSTCoreParser.g:663:7: ruleSTAssignmentStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTAssignmentStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_InternalSTCoreParser

    // $ANTLR start synpred2_InternalSTCoreParser
    public final void synpred2_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:1368:4: ( ( ruleSTStatement ) )
        // InternalSTCoreParser.g:1368:5: ( ruleSTStatement )
        {
        // InternalSTCoreParser.g:1368:5: ( ruleSTStatement )
        // InternalSTCoreParser.g:1369:5: ruleSTStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSTCoreParser

    // $ANTLR start synpred3_InternalSTCoreParser
    public final void synpred3_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:2440:4: ( ruleSTAccessExpression )
        // InternalSTCoreParser.g:2440:5: ruleSTAccessExpression
        {
        pushFollow(FOLLOW_2);
        ruleSTAccessExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalSTCoreParser

    // $ANTLR start synpred4_InternalSTCoreParser
    public final void synpred4_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:2738:5: ( ( LeftParenthesis ) )
        // InternalSTCoreParser.g:2738:6: ( LeftParenthesis )
        {
        // InternalSTCoreParser.g:2738:6: ( LeftParenthesis )
        // InternalSTCoreParser.g:2739:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred4_InternalSTCoreParser

    // $ANTLR start synpred5_InternalSTCoreParser
    public final void synpred5_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:4103:5: ( FullStop )
        // InternalSTCoreParser.g:4103:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_InternalSTCoreParser

    // $ANTLR start synpred6_InternalSTCoreParser
    public final void synpred6_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:4266:5: ( FullStop )
        // InternalSTCoreParser.g:4266:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_InternalSTCoreParser

    // $ANTLR start synpred7_InternalSTCoreParser
    public final void synpred7_InternalSTCoreParser_fragment() throws RecognitionException {   
        // InternalSTCoreParser.g:4333:5: ( FullStop )
        // InternalSTCoreParser.g:4333:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_InternalSTCoreParser

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
    public final boolean synpred6_InternalSTCoreParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalSTCoreParser_fragment(); // can never throw exception
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
    public final boolean synpred7_InternalSTCoreParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalSTCoreParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA13 dfa13 = new DFA13(this);
    protected DFA24 dfa24 = new DFA24(this);
    protected DFA39 dfa39 = new DFA39(this);
    protected DFA46 dfa46 = new DFA46(this);
    static final String dfa_1s = "\74\uffff";
    static final String dfa_2s = "\1\4\5\uffff\61\0\5\uffff";
    static final String dfa_3s = "\1\165\5\uffff\61\0\5\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\61\uffff\1\10\1\11\1\12\1\6\1\7";
    static final String dfa_5s = "\6\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\5\uffff}>";
    static final String[] dfa_6s = {
            "\1\60\1\57\1\54\1\53\6\uffff\1\70\4\uffff\1\63\1\uffff\1\5\1\67\1\62\1\uffff\1\24\1\uffff\1\41\1\47\1\37\1\51\1\25\1\34\1\35\1\uffff\1\32\1\65\1\4\1\21\1\22\1\2\1\64\1\46\1\30\1\uffff\1\71\1\31\1\56\1\36\1\26\1\uffff\1\50\1\40\1\33\1\23\1\11\1\3\1\27\1\61\1\15\1\14\1\55\1\uffff\1\13\17\uffff\1\17\1\1\1\20\1\10\3\uffff\1\12\4\uffff\1\6\2\uffff\1\42\1\uffff\1\43\7\uffff\1\16\3\uffff\1\52\4\uffff\1\45\1\44\1\uffff\1\7\1\66",
            "",
            "",
            "",
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
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "616:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA13_6 = input.LA(1);

                         
                        int index13_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA13_7 = input.LA(1);

                         
                        int index13_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA13_8 = input.LA(1);

                         
                        int index13_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA13_9 = input.LA(1);

                         
                        int index13_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_9);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA13_10 = input.LA(1);

                         
                        int index13_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA13_11 = input.LA(1);

                         
                        int index13_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_11);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA13_12 = input.LA(1);

                         
                        int index13_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_12);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA13_13 = input.LA(1);

                         
                        int index13_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_13);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA13_14 = input.LA(1);

                         
                        int index13_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_14);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA13_15 = input.LA(1);

                         
                        int index13_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_15);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA13_16 = input.LA(1);

                         
                        int index13_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_16);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA13_17 = input.LA(1);

                         
                        int index13_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_17);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA13_18 = input.LA(1);

                         
                        int index13_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_18);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA13_19 = input.LA(1);

                         
                        int index13_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_19);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA13_20 = input.LA(1);

                         
                        int index13_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_20);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA13_21 = input.LA(1);

                         
                        int index13_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_21);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA13_22 = input.LA(1);

                         
                        int index13_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_22);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA13_23 = input.LA(1);

                         
                        int index13_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_23);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA13_24 = input.LA(1);

                         
                        int index13_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_24);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA13_25 = input.LA(1);

                         
                        int index13_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_25);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA13_26 = input.LA(1);

                         
                        int index13_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_26);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA13_27 = input.LA(1);

                         
                        int index13_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_27);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA13_28 = input.LA(1);

                         
                        int index13_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_28);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA13_29 = input.LA(1);

                         
                        int index13_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_29);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA13_30 = input.LA(1);

                         
                        int index13_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_30);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA13_31 = input.LA(1);

                         
                        int index13_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_31);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA13_32 = input.LA(1);

                         
                        int index13_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_32);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA13_33 = input.LA(1);

                         
                        int index13_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_33);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA13_34 = input.LA(1);

                         
                        int index13_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_34);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA13_35 = input.LA(1);

                         
                        int index13_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_35);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA13_36 = input.LA(1);

                         
                        int index13_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_36);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA13_37 = input.LA(1);

                         
                        int index13_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_37);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA13_38 = input.LA(1);

                         
                        int index13_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_38);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA13_39 = input.LA(1);

                         
                        int index13_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_39);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA13_40 = input.LA(1);

                         
                        int index13_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_40);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA13_41 = input.LA(1);

                         
                        int index13_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_41);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA13_42 = input.LA(1);

                         
                        int index13_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_42);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA13_43 = input.LA(1);

                         
                        int index13_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_43);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA13_44 = input.LA(1);

                         
                        int index13_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_44);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA13_45 = input.LA(1);

                         
                        int index13_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_45);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA13_46 = input.LA(1);

                         
                        int index13_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_46);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA13_47 = input.LA(1);

                         
                        int index13_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_47);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA13_48 = input.LA(1);

                         
                        int index13_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_48);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA13_49 = input.LA(1);

                         
                        int index13_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_49);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA13_50 = input.LA(1);

                         
                        int index13_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_50);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA13_51 = input.LA(1);

                         
                        int index13_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_51);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA13_52 = input.LA(1);

                         
                        int index13_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_52);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA13_53 = input.LA(1);

                         
                        int index13_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_53);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA13_54 = input.LA(1);

                         
                        int index13_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTCoreParser()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index13_54);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 13, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_7s = "\76\uffff";
    static final String dfa_8s = "\1\1\75\uffff";
    static final String dfa_9s = "\1\4\2\uffff\61\0\12\uffff";
    static final String dfa_10s = "\1\165\2\uffff\61\0\12\uffff";
    static final String dfa_11s = "\1\uffff\1\2\63\uffff\11\1";
    static final String dfa_12s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\12\uffff}>";
    static final String[] dfa_13s = {
            "\1\55\1\54\1\51\1\50\6\uffff\1\73\1\1\3\uffff\1\60\1\uffff\1\71\1\72\1\57\1\uffff\1\21\1\uffff\1\36\1\44\1\34\1\46\1\22\1\31\1\32\1\uffff\1\27\1\62\1\70\1\16\1\17\1\66\1\61\1\43\1\25\1\1\1\74\1\26\1\53\1\33\1\23\1\uffff\1\45\1\35\1\30\1\20\1\6\1\67\1\24\1\56\1\12\1\11\1\52\1\uffff\1\10\17\uffff\1\14\1\65\1\15\1\5\3\uffff\1\7\4\uffff\1\3\2\uffff\1\37\1\uffff\1\40\3\uffff\1\75\3\uffff\1\13\3\uffff\1\47\4\uffff\1\42\1\41\1\uffff\1\4\1\63",
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
            ""
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "()* loopback of 1367:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA24_0 = input.LA(1);

                         
                        int index24_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA24_0==EOF||LA24_0==END_CASE||LA24_0==ELSE) ) {s = 1;}

                        else if ( (LA24_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA24_0==RULE_ID) ) {s = 4;}

                        else if ( (LA24_0==LT) ) {s = 5;}

                        else if ( (LA24_0==AND) ) {s = 6;}

                        else if ( (LA24_0==OR) ) {s = 7;}

                        else if ( (LA24_0==XOR) ) {s = 8;}

                        else if ( (LA24_0==NOT) ) {s = 9;}

                        else if ( (LA24_0==MOD) ) {s = 10;}

                        else if ( (LA24_0==D) ) {s = 11;}

                        else if ( (LA24_0==DT) ) {s = 12;}

                        else if ( (LA24_0==LD) ) {s = 13;}

                        else if ( (LA24_0==BOOL) ) {s = 14;}

                        else if ( (LA24_0==BYTE) ) {s = 15;}

                        else if ( (LA24_0==WORD) ) {s = 16;}

                        else if ( (LA24_0==DWORD) ) {s = 17;}

                        else if ( (LA24_0==LWORD) ) {s = 18;}

                        else if ( (LA24_0==SINT) ) {s = 19;}

                        else if ( (LA24_0==INT) ) {s = 20;}

                        else if ( (LA24_0==DINT) ) {s = 21;}

                        else if ( (LA24_0==LINT) ) {s = 22;}

                        else if ( (LA24_0==USINT) ) {s = 23;}

                        else if ( (LA24_0==UINT) ) {s = 24;}

                        else if ( (LA24_0==UDINT) ) {s = 25;}

                        else if ( (LA24_0==ULINT) ) {s = 26;}

                        else if ( (LA24_0==REAL) ) {s = 27;}

                        else if ( (LA24_0==LREAL) ) {s = 28;}

                        else if ( (LA24_0==TRUE) ) {s = 29;}

                        else if ( (LA24_0==FALSE) ) {s = 30;}

                        else if ( (LA24_0==PlusSign) ) {s = 31;}

                        else if ( (LA24_0==HyphenMinus) ) {s = 32;}

                        else if ( (LA24_0==RULE_INT) ) {s = 33;}

                        else if ( (LA24_0==RULE_NON_DECIMAL) ) {s = 34;}

                        else if ( (LA24_0==DATE) ) {s = 35;}

                        else if ( (LA24_0==LDATE) ) {s = 36;}

                        else if ( (LA24_0==TIME) ) {s = 37;}

                        else if ( (LA24_0==LTIME) ) {s = 38;}

                        else if ( (LA24_0==T) ) {s = 39;}

                        else if ( (LA24_0==TIME_OF_DAY) ) {s = 40;}

                        else if ( (LA24_0==LTIME_OF_DAY) ) {s = 41;}

                        else if ( (LA24_0==TOD) ) {s = 42;}

                        else if ( (LA24_0==LTOD) ) {s = 43;}

                        else if ( (LA24_0==DATE_AND_TIME) ) {s = 44;}

                        else if ( (LA24_0==LDATE_AND_TIME) ) {s = 45;}

                        else if ( (LA24_0==LDT) ) {s = 46;}

                        else if ( (LA24_0==STRING) ) {s = 47;}

                        else if ( (LA24_0==WSTRING) ) {s = 48;}

                        else if ( (LA24_0==CHAR) ) {s = 49;}

                        else if ( (LA24_0==WCHAR) ) {s = 50;}

                        else if ( (LA24_0==RULE_STRING) ) {s = 51;}

                        else if ( (LA24_0==IF) && (synpred2_InternalSTCoreParser())) {s = 53;}

                        else if ( (LA24_0==CASE) && (synpred2_InternalSTCoreParser())) {s = 54;}

                        else if ( (LA24_0==FOR) && (synpred2_InternalSTCoreParser())) {s = 55;}

                        else if ( (LA24_0==WHILE) && (synpred2_InternalSTCoreParser())) {s = 56;}

                        else if ( (LA24_0==REPEAT) && (synpred2_InternalSTCoreParser())) {s = 57;}

                        else if ( (LA24_0==RETURN) && (synpred2_InternalSTCoreParser())) {s = 58;}

                        else if ( (LA24_0==CONTINUE) && (synpred2_InternalSTCoreParser())) {s = 59;}

                        else if ( (LA24_0==EXIT) && (synpred2_InternalSTCoreParser())) {s = 60;}

                        else if ( (LA24_0==Semicolon) && (synpred2_InternalSTCoreParser())) {s = 61;}

                         
                        input.seek(index24_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA24_3 = input.LA(1);

                         
                        int index24_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA24_4 = input.LA(1);

                         
                        int index24_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA24_5 = input.LA(1);

                         
                        int index24_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA24_6 = input.LA(1);

                         
                        int index24_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA24_7 = input.LA(1);

                         
                        int index24_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA24_8 = input.LA(1);

                         
                        int index24_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA24_9 = input.LA(1);

                         
                        int index24_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA24_10 = input.LA(1);

                         
                        int index24_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA24_11 = input.LA(1);

                         
                        int index24_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA24_12 = input.LA(1);

                         
                        int index24_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA24_13 = input.LA(1);

                         
                        int index24_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA24_14 = input.LA(1);

                         
                        int index24_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA24_15 = input.LA(1);

                         
                        int index24_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA24_16 = input.LA(1);

                         
                        int index24_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA24_17 = input.LA(1);

                         
                        int index24_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA24_18 = input.LA(1);

                         
                        int index24_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA24_19 = input.LA(1);

                         
                        int index24_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA24_20 = input.LA(1);

                         
                        int index24_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA24_21 = input.LA(1);

                         
                        int index24_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA24_22 = input.LA(1);

                         
                        int index24_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA24_23 = input.LA(1);

                         
                        int index24_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA24_24 = input.LA(1);

                         
                        int index24_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA24_25 = input.LA(1);

                         
                        int index24_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA24_26 = input.LA(1);

                         
                        int index24_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA24_27 = input.LA(1);

                         
                        int index24_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA24_28 = input.LA(1);

                         
                        int index24_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA24_29 = input.LA(1);

                         
                        int index24_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA24_30 = input.LA(1);

                         
                        int index24_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA24_31 = input.LA(1);

                         
                        int index24_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA24_32 = input.LA(1);

                         
                        int index24_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA24_33 = input.LA(1);

                         
                        int index24_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA24_34 = input.LA(1);

                         
                        int index24_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA24_35 = input.LA(1);

                         
                        int index24_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA24_36 = input.LA(1);

                         
                        int index24_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA24_37 = input.LA(1);

                         
                        int index24_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA24_38 = input.LA(1);

                         
                        int index24_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA24_39 = input.LA(1);

                         
                        int index24_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA24_40 = input.LA(1);

                         
                        int index24_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA24_41 = input.LA(1);

                         
                        int index24_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA24_42 = input.LA(1);

                         
                        int index24_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA24_43 = input.LA(1);

                         
                        int index24_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA24_44 = input.LA(1);

                         
                        int index24_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA24_45 = input.LA(1);

                         
                        int index24_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA24_46 = input.LA(1);

                         
                        int index24_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_46);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA24_47 = input.LA(1);

                         
                        int index24_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_47);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA24_48 = input.LA(1);

                         
                        int index24_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_48);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA24_49 = input.LA(1);

                         
                        int index24_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_49);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA24_50 = input.LA(1);

                         
                        int index24_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_50);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA24_51 = input.LA(1);

                         
                        int index24_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTCoreParser()) ) {s = 61;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index24_51);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 24, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_14s = "\63\uffff";
    static final String dfa_15s = "\1\4\6\uffff\1\0\25\uffff\2\0\24\uffff";
    static final String dfa_16s = "\1\165\6\uffff\1\0\25\uffff\2\0\24\uffff";
    static final String dfa_17s = "\1\uffff\6\1\1\uffff\25\1\2\uffff\23\1\1\2";
    static final String dfa_18s = "\1\0\6\uffff\1\1\25\uffff\1\2\1\3\24\uffff}>";
    static final String[] dfa_19s = {
            "\1\53\1\52\1\47\1\46\13\uffff\1\56\3\uffff\1\55\1\uffff\1\17\1\uffff\1\34\1\42\1\32\1\44\1\20\1\27\1\30\1\uffff\1\25\1\60\1\uffff\1\14\1\15\1\uffff\1\57\1\41\1\23\2\uffff\1\24\1\51\1\31\1\21\1\uffff\1\43\1\33\1\26\1\16\1\4\1\uffff\1\22\1\54\1\10\1\7\1\50\1\uffff\1\6\17\uffff\1\12\1\uffff\1\13\1\3\3\uffff\1\5\4\uffff\1\1\2\uffff\1\35\1\uffff\1\36\7\uffff\1\11\3\uffff\1\45\4\uffff\1\40\1\37\1\uffff\1\2\1\61",
            "",
            "",
            "",
            "",
            "",
            "",
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
            ""
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA39 extends DFA {

        public DFA39(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 39;
            this.eot = dfa_14;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "2438:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA39_0 = input.LA(1);

                         
                        int index39_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA39_0==LeftParenthesis) && (synpred3_InternalSTCoreParser())) {s = 1;}

                        else if ( (LA39_0==RULE_ID) && (synpred3_InternalSTCoreParser())) {s = 2;}

                        else if ( (LA39_0==LT) && (synpred3_InternalSTCoreParser())) {s = 3;}

                        else if ( (LA39_0==AND) && (synpred3_InternalSTCoreParser())) {s = 4;}

                        else if ( (LA39_0==OR) && (synpred3_InternalSTCoreParser())) {s = 5;}

                        else if ( (LA39_0==XOR) && (synpred3_InternalSTCoreParser())) {s = 6;}

                        else if ( (LA39_0==NOT) ) {s = 7;}

                        else if ( (LA39_0==MOD) && (synpred3_InternalSTCoreParser())) {s = 8;}

                        else if ( (LA39_0==D) && (synpred3_InternalSTCoreParser())) {s = 9;}

                        else if ( (LA39_0==DT) && (synpred3_InternalSTCoreParser())) {s = 10;}

                        else if ( (LA39_0==LD) && (synpred3_InternalSTCoreParser())) {s = 11;}

                        else if ( (LA39_0==BOOL) && (synpred3_InternalSTCoreParser())) {s = 12;}

                        else if ( (LA39_0==BYTE) && (synpred3_InternalSTCoreParser())) {s = 13;}

                        else if ( (LA39_0==WORD) && (synpred3_InternalSTCoreParser())) {s = 14;}

                        else if ( (LA39_0==DWORD) && (synpred3_InternalSTCoreParser())) {s = 15;}

                        else if ( (LA39_0==LWORD) && (synpred3_InternalSTCoreParser())) {s = 16;}

                        else if ( (LA39_0==SINT) && (synpred3_InternalSTCoreParser())) {s = 17;}

                        else if ( (LA39_0==INT) && (synpred3_InternalSTCoreParser())) {s = 18;}

                        else if ( (LA39_0==DINT) && (synpred3_InternalSTCoreParser())) {s = 19;}

                        else if ( (LA39_0==LINT) && (synpred3_InternalSTCoreParser())) {s = 20;}

                        else if ( (LA39_0==USINT) && (synpred3_InternalSTCoreParser())) {s = 21;}

                        else if ( (LA39_0==UINT) && (synpred3_InternalSTCoreParser())) {s = 22;}

                        else if ( (LA39_0==UDINT) && (synpred3_InternalSTCoreParser())) {s = 23;}

                        else if ( (LA39_0==ULINT) && (synpred3_InternalSTCoreParser())) {s = 24;}

                        else if ( (LA39_0==REAL) && (synpred3_InternalSTCoreParser())) {s = 25;}

                        else if ( (LA39_0==LREAL) && (synpred3_InternalSTCoreParser())) {s = 26;}

                        else if ( (LA39_0==TRUE) && (synpred3_InternalSTCoreParser())) {s = 27;}

                        else if ( (LA39_0==FALSE) && (synpred3_InternalSTCoreParser())) {s = 28;}

                        else if ( (LA39_0==PlusSign) ) {s = 29;}

                        else if ( (LA39_0==HyphenMinus) ) {s = 30;}

                        else if ( (LA39_0==RULE_INT) && (synpred3_InternalSTCoreParser())) {s = 31;}

                        else if ( (LA39_0==RULE_NON_DECIMAL) && (synpred3_InternalSTCoreParser())) {s = 32;}

                        else if ( (LA39_0==DATE) && (synpred3_InternalSTCoreParser())) {s = 33;}

                        else if ( (LA39_0==LDATE) && (synpred3_InternalSTCoreParser())) {s = 34;}

                        else if ( (LA39_0==TIME) && (synpred3_InternalSTCoreParser())) {s = 35;}

                        else if ( (LA39_0==LTIME) && (synpred3_InternalSTCoreParser())) {s = 36;}

                        else if ( (LA39_0==T) && (synpred3_InternalSTCoreParser())) {s = 37;}

                        else if ( (LA39_0==TIME_OF_DAY) && (synpred3_InternalSTCoreParser())) {s = 38;}

                        else if ( (LA39_0==LTIME_OF_DAY) && (synpred3_InternalSTCoreParser())) {s = 39;}

                        else if ( (LA39_0==TOD) && (synpred3_InternalSTCoreParser())) {s = 40;}

                        else if ( (LA39_0==LTOD) && (synpred3_InternalSTCoreParser())) {s = 41;}

                        else if ( (LA39_0==DATE_AND_TIME) && (synpred3_InternalSTCoreParser())) {s = 42;}

                        else if ( (LA39_0==LDATE_AND_TIME) && (synpred3_InternalSTCoreParser())) {s = 43;}

                        else if ( (LA39_0==LDT) && (synpred3_InternalSTCoreParser())) {s = 44;}

                        else if ( (LA39_0==STRING) && (synpred3_InternalSTCoreParser())) {s = 45;}

                        else if ( (LA39_0==WSTRING) && (synpred3_InternalSTCoreParser())) {s = 46;}

                        else if ( (LA39_0==CHAR) && (synpred3_InternalSTCoreParser())) {s = 47;}

                        else if ( (LA39_0==WCHAR) && (synpred3_InternalSTCoreParser())) {s = 48;}

                        else if ( (LA39_0==RULE_STRING) && (synpred3_InternalSTCoreParser())) {s = 49;}

                         
                        input.seek(index39_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA39_7 = input.LA(1);

                         
                        int index39_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTCoreParser()) ) {s = 49;}

                        else if ( (true) ) {s = 50;}

                         
                        input.seek(index39_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA39_29 = input.LA(1);

                         
                        int index39_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTCoreParser()) ) {s = 49;}

                        else if ( (true) ) {s = 50;}

                         
                        input.seek(index39_29);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA39_30 = input.LA(1);

                         
                        int index39_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTCoreParser()) ) {s = 49;}

                        else if ( (true) ) {s = 50;}

                         
                        input.seek(index39_30);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 39, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_20s = "\43\uffff";
    static final String dfa_21s = "\1\2\42\uffff";
    static final String dfa_22s = "\1\10\1\0\41\uffff";
    static final String dfa_23s = "\1\156\1\0\41\uffff";
    static final String dfa_24s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_25s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_26s = {
            "\1\2\51\uffff\1\2\4\uffff\1\2\3\uffff\1\2\3\uffff\1\2\5\uffff\5\2\1\uffff\1\2\1\uffff\2\2\6\uffff\3\2\2\uffff\1\2\1\1\14\2\5\uffff\2\2",
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

    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[][] dfa_26 = unpackEncodedStringArray(dfa_26s);

    class DFA46 extends DFA {

        public DFA46(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 46;
            this.eot = dfa_20;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "2736:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA46_1 = input.LA(1);

                         
                        int index46_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSTCoreParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index46_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 46, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0xBFFBEFFBFAE840F2L,0x0036111148478000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000800001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x266BCEDBF38800F0L,0x0010000000008000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0xBEFBCEDBFA8800F0L,0x0036110148468000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000400080000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000201000000080L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000080L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0xBEFBCEDBFA8800F0L,0x0036310148468000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000090000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0xBFFBFFFBFEF840F0L,0x0036111148478000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000100004100000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0xBFFBEFFBFAE840F0L,0x0036111148478000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0xBEFBDEDBFA8880F0L,0x0036110148468000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x0000000880000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0xBFFBEFFBFAEA40F0L,0x0036111148478000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0xBFFBEFFBFAE848F0L,0x0036111148478000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0xBFFBEFFFFAE840F0L,0x0036111148478000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0080000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000200L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x000000A000000900L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000140000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0800000000000002L,0x0000000420000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000200200000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x9880000000000000L,0x001401000846801FL});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0xBEFBCEDBFA8800F0L,0x0036110158468000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0004000008000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0010000008000000L,0x0006000140000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0004000140000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x000C000000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x00000F0001180000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0004800140000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0004000140000000L});

}