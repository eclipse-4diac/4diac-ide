package org.eclipse.fordiac.ide.structuredtextfunctioneditor.parser.antlr.internal;

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
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalSTFunctionParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "END_FUNCTION", "LTIME_OF_DAY", "TIME_OF_DAY", "END_REPEAT", "VAR_IN_OUT", "VAR_OUTPUT", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "FUNCTION", "VAR_TEMP", "END_FOR", "END_VAR", "WSTRING", "END_IF", "REPEAT", "RETURN", "STRING", "ARRAY", "DWORD", "ELSIF", "FALSE", "LDATE", "LREAL", "LTIME", "LWORD", "UDINT", "ULINT", "UNTIL", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "LINT", "LTOD", "REAL", "SINT", "THEN", "TIME", "TRUE", "UINT", "WORD", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "TOD", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "MS", "NS", "OF", "OR", "TO", "US", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "H", "M", "S", "T", "LeftSquareBracket", "RightSquareBracket", "KW__", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=33;
    public static final int LessThanSignGreaterThanSign=75;
    public static final int EqualsSignGreaterThanSign=76;
    public static final int VAR=64;
    public static final int END_IF=22;
    public static final int ULINT=35;
    public static final int END_CASE=16;
    public static final int LessThanSign=103;
    public static final int LeftParenthesis=93;
    public static final int BYTE=41;
    public static final int ELSE=46;
    public static final int IF=82;
    public static final int LINT=48;
    public static final int GreaterThanSign=105;
    public static final int WORD=56;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=118;
    public static final int TOD=63;
    public static final int DINT=45;
    public static final int FUNCTION=17;
    public static final int UDINT=34;
    public static final int CASE=42;
    public static final int GreaterThanSignEqualsSign=77;
    public static final int AT=78;
    public static final int PlusSign=96;
    public static final int RULE_INT=116;
    public static final int END_FOR=19;
    public static final int RULE_ML_COMMENT=120;
    public static final int THEN=52;
    public static final int XOR=65;
    public static final int LeftSquareBracket=111;
    public static final int EXIT=47;
    public static final int TIME_OF_DAY=8;
    public static final int B=66;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=23;
    public static final int D=106;
    public static final int H=107;
    public static final int CHAR=43;
    public static final int L=68;
    public static final int M=108;
    public static final int LTIME=32;
    public static final int Comma=97;
    public static final int HyphenMinus=98;
    public static final int S=109;
    public static final int T=110;
    public static final int W=69;
    public static final int BY=79;
    public static final int X=70;
    public static final int ELSIF=28;
    public static final int END_REPEAT=9;
    public static final int LessThanSignEqualsSign=74;
    public static final int Solidus=100;
    public static final int VAR_INPUT=13;
    public static final int FullStop=99;
    public static final int VAR_TEMP=18;
    public static final int CONSTANT=14;
    public static final int KW__=113;
    public static final int CONTINUE=15;
    public static final int Semicolon=102;
    public static final int LD=83;
    public static final int VAR_OUTPUT=11;
    public static final int STRING=25;
    public static final int RULE_HEX_DIGIT=114;
    public static final int TO=89;
    public static final int UINT=55;
    public static final int LTOD=49;
    public static final int ARRAY=26;
    public static final int LT=84;
    public static final int DO=80;
    public static final int WSTRING=21;
    public static final int DT=81;
    public static final int END_VAR=20;
    public static final int FullStopFullStop=72;
    public static final int Ampersand=92;
    public static final int US=90;
    public static final int RightSquareBracket=112;
    public static final int USINT=37;
    public static final int MS=85;
    public static final int DWORD=27;
    public static final int FOR=58;
    public static final int RightParenthesis=94;
    public static final int TRUE=54;
    public static final int ColonEqualsSign=73;
    public static final int END_FUNCTION=6;
    public static final int END_WHILE=12;
    public static final int DATE=44;
    public static final int NOT=62;
    public static final int LDATE=30;
    public static final int AND=57;
    public static final int NumberSign=91;
    public static final int REAL=50;
    public static final int AsteriskAsterisk=71;
    public static final int SINT=51;
    public static final int LTIME_OF_DAY=7;
    public static final int LREAL=31;
    public static final int WCHAR=38;
    public static final int NS=86;
    public static final int RULE_STRING=119;
    public static final int INT=59;
    public static final int RULE_SL_COMMENT=121;
    public static final int RETURN=24;
    public static final int EqualsSign=104;
    public static final int OF=87;
    public static final int Colon=101;
    public static final int EOF=-1;
    public static final int LDT=60;
    public static final int Asterisk=95;
    public static final int MOD=61;
    public static final int OR=88;
    public static final int RULE_WS=122;
    public static final int RULE_EXT_INT=117;
    public static final int VAR_IN_OUT=10;
    public static final int TIME=53;
    public static final int RULE_ANY_OTHER=123;
    public static final int UNTIL=36;
    public static final int BOOL=40;
    public static final int D_1=67;
    public static final int FALSE=29;
    public static final int WHILE=39;
    public static final int RULE_NON_DECIMAL=115;

    // delegates
    // delegators


        public InternalSTFunctionParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSTFunctionParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSTFunctionParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSTFunctionParser.g"; }



     	private STFunctionGrammarAccess grammarAccess;

        public InternalSTFunctionParser(TokenStream input, STFunctionGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "STFunctionSource";
       	}

       	@Override
       	protected STFunctionGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleSTFunctionSource"
    // InternalSTFunctionParser.g:58:1: entryRuleSTFunctionSource returns [EObject current=null] : iv_ruleSTFunctionSource= ruleSTFunctionSource EOF ;
    public final EObject entryRuleSTFunctionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFunctionSource = null;


        try {
            // InternalSTFunctionParser.g:58:57: (iv_ruleSTFunctionSource= ruleSTFunctionSource EOF )
            // InternalSTFunctionParser.g:59:2: iv_ruleSTFunctionSource= ruleSTFunctionSource EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTFunctionSourceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTFunctionSource=ruleSTFunctionSource();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTFunctionSource; 
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
    // $ANTLR end "entryRuleSTFunctionSource"


    // $ANTLR start "ruleSTFunctionSource"
    // InternalSTFunctionParser.g:65:1: ruleSTFunctionSource returns [EObject current=null] : ( () ( (lv_functions_1_0= ruleSTFunction ) )* ) ;
    public final EObject ruleSTFunctionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_functions_1_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:71:2: ( ( () ( (lv_functions_1_0= ruleSTFunction ) )* ) )
            // InternalSTFunctionParser.g:72:2: ( () ( (lv_functions_1_0= ruleSTFunction ) )* )
            {
            // InternalSTFunctionParser.g:72:2: ( () ( (lv_functions_1_0= ruleSTFunction ) )* )
            // InternalSTFunctionParser.g:73:3: () ( (lv_functions_1_0= ruleSTFunction ) )*
            {
            // InternalSTFunctionParser.g:73:3: ()
            // InternalSTFunctionParser.g:74:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFunctionSourceAccess().getSTFunctionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:80:3: ( (lv_functions_1_0= ruleSTFunction ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==FUNCTION) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSTFunctionParser.g:81:4: (lv_functions_1_0= ruleSTFunction )
            	    {
            	    // InternalSTFunctionParser.g:81:4: (lv_functions_1_0= ruleSTFunction )
            	    // InternalSTFunctionParser.g:82:5: lv_functions_1_0= ruleSTFunction
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTFunctionSourceAccess().getFunctionsSTFunctionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_functions_1_0=ruleSTFunction();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTFunctionSourceRule());
            	      					}
            	      					add(
            	      						current,
            	      						"functions",
            	      						lv_functions_1_0,
            	      						"org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunction.STFunction");
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
    // $ANTLR end "ruleSTFunctionSource"


    // $ANTLR start "entryRuleSTFunction"
    // InternalSTFunctionParser.g:103:1: entryRuleSTFunction returns [EObject current=null] : iv_ruleSTFunction= ruleSTFunction EOF ;
    public final EObject entryRuleSTFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFunction = null;


        try {
            // InternalSTFunctionParser.g:103:51: (iv_ruleSTFunction= ruleSTFunction EOF )
            // InternalSTFunctionParser.g:104:2: iv_ruleSTFunction= ruleSTFunction EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTFunctionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTFunction=ruleSTFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTFunction; 
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
    // $ANTLR end "entryRuleSTFunction"


    // $ANTLR start "ruleSTFunction"
    // InternalSTFunctionParser.g:110:1: ruleSTFunction returns [EObject current=null] : ( () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION ) ;
    public final EObject ruleSTFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_7=null;
        EObject lv_varDeclarations_5_1 = null;

        EObject lv_varDeclarations_5_2 = null;

        EObject lv_varDeclarations_5_3 = null;

        EObject lv_varDeclarations_5_4 = null;

        EObject lv_varDeclarations_5_5 = null;

        EObject lv_code_6_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:116:2: ( ( () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION ) )
            // InternalSTFunctionParser.g:117:2: ( () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION )
            {
            // InternalSTFunctionParser.g:117:2: ( () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION )
            // InternalSTFunctionParser.g:118:3: () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION
            {
            // InternalSTFunctionParser.g:118:3: ()
            // InternalSTFunctionParser.g:119:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFunctionAccess().getSTFunctionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,FUNCTION,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTFunctionAccess().getFUNCTIONKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:129:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalSTFunctionParser.g:130:4: (lv_name_2_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:130:4: (lv_name_2_0= RULE_ID )
            // InternalSTFunctionParser.g:131:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_2_0, grammarAccess.getSTFunctionAccess().getNameIDTerminalRuleCall_2_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFunctionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
              				
            }

            }


            }

            // InternalSTFunctionParser.g:147:3: (otherlv_3= Colon ( ( ruleSTAnyType ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==Colon) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalSTFunctionParser.g:148:4: otherlv_3= Colon ( ( ruleSTAnyType ) )
                    {
                    otherlv_3=(Token)match(input,Colon,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTFunctionAccess().getColonKeyword_3_0());
                      			
                    }
                    // InternalSTFunctionParser.g:152:4: ( ( ruleSTAnyType ) )
                    // InternalSTFunctionParser.g:153:5: ( ruleSTAnyType )
                    {
                    // InternalSTFunctionParser.g:153:5: ( ruleSTAnyType )
                    // InternalSTFunctionParser.g:154:6: ruleSTAnyType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTFunctionRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTFunctionAccess().getReturnTypeDataTypeCrossReference_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_7);
                    ruleSTAnyType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:169:3: ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=VAR_IN_OUT && LA4_0<=VAR_OUTPUT)||LA4_0==VAR_INPUT||LA4_0==VAR_TEMP||LA4_0==VAR) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSTFunctionParser.g:170:4: ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) )
            	    {
            	    // InternalSTFunctionParser.g:170:4: ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) )
            	    // InternalSTFunctionParser.g:171:5: (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock )
            	    {
            	    // InternalSTFunctionParser.g:171:5: (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock )
            	    int alt3=5;
            	    switch ( input.LA(1) ) {
            	    case VAR:
            	        {
            	        alt3=1;
            	        }
            	        break;
            	    case VAR_TEMP:
            	        {
            	        alt3=2;
            	        }
            	        break;
            	    case VAR_INPUT:
            	        {
            	        alt3=3;
            	        }
            	        break;
            	    case VAR_OUTPUT:
            	        {
            	        alt3=4;
            	        }
            	        break;
            	    case VAR_IN_OUT:
            	        {
            	        alt3=5;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 3, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt3) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:172:6: lv_varDeclarations_5_1= ruleSTVarDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarDeclarationBlockParserRuleCall_4_0_0());
            	              					
            	            }
            	            pushFollow(FOLLOW_7);
            	            lv_varDeclarations_5_1=ruleSTVarDeclarationBlock();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTFunctionRule());
            	              						}
            	              						add(
            	              							current,
            	              							"varDeclarations",
            	              							lv_varDeclarations_5_1,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclarationBlock");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTFunctionParser.g:188:6: lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarTempDeclarationBlockParserRuleCall_4_0_1());
            	              					
            	            }
            	            pushFollow(FOLLOW_7);
            	            lv_varDeclarations_5_2=ruleSTVarTempDeclarationBlock();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTFunctionRule());
            	              						}
            	              						add(
            	              							current,
            	              							"varDeclarations",
            	              							lv_varDeclarations_5_2,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarTempDeclarationBlock");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTFunctionParser.g:204:6: lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarInputDeclarationBlockParserRuleCall_4_0_2());
            	              					
            	            }
            	            pushFollow(FOLLOW_7);
            	            lv_varDeclarations_5_3=ruleSTVarInputDeclarationBlock();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTFunctionRule());
            	              						}
            	              						add(
            	              							current,
            	              							"varDeclarations",
            	              							lv_varDeclarations_5_3,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarInputDeclarationBlock");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTFunctionParser.g:220:6: lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarOutputDeclarationBlockParserRuleCall_4_0_3());
            	              					
            	            }
            	            pushFollow(FOLLOW_7);
            	            lv_varDeclarations_5_4=ruleSTVarOutputDeclarationBlock();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTFunctionRule());
            	              						}
            	              						add(
            	              							current,
            	              							"varDeclarations",
            	              							lv_varDeclarations_5_4,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarOutputDeclarationBlock");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalSTFunctionParser.g:236:6: lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarInOutDeclarationBlockParserRuleCall_4_0_4());
            	              					
            	            }
            	            pushFollow(FOLLOW_7);
            	            lv_varDeclarations_5_5=ruleSTVarInOutDeclarationBlock();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTFunctionRule());
            	              						}
            	              						add(
            	              							current,
            	              							"varDeclarations",
            	              							lv_varDeclarations_5_5,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarInOutDeclarationBlock");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            // InternalSTFunctionParser.g:254:3: ( (lv_code_6_0= ruleSTStatement ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>=LDATE_AND_TIME && LA5_0<=DATE_AND_TIME)||(LA5_0>=LTIME_OF_DAY && LA5_0<=TIME_OF_DAY)||LA5_0==CONTINUE||LA5_0==WSTRING||(LA5_0>=REPEAT && LA5_0<=STRING)||LA5_0==DWORD||(LA5_0>=FALSE && LA5_0<=ULINT)||(LA5_0>=USINT && LA5_0<=DINT)||(LA5_0>=EXIT && LA5_0<=SINT)||(LA5_0>=TIME && LA5_0<=WORD)||(LA5_0>=FOR && LA5_0<=LDT)||LA5_0==TOD||(LA5_0>=DT && LA5_0<=LT)||LA5_0==LeftParenthesis||LA5_0==PlusSign||LA5_0==HyphenMinus||LA5_0==Semicolon||LA5_0==D||LA5_0==T||(LA5_0>=RULE_NON_DECIMAL && LA5_0<=RULE_INT)||(LA5_0>=RULE_ID && LA5_0<=RULE_STRING)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSTFunctionParser.g:255:4: (lv_code_6_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:255:4: (lv_code_6_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:256:5: lv_code_6_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTFunctionAccess().getCodeSTStatementParserRuleCall_5_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
            	    lv_code_6_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTFunctionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"code",
            	      						lv_code_6_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            otherlv_7=(Token)match(input,END_FUNCTION,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getSTFunctionAccess().getEND_FUNCTIONKeyword_6());
              		
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
    // $ANTLR end "ruleSTFunction"


    // $ANTLR start "entryRuleSTVarDeclarationBlock"
    // InternalSTFunctionParser.g:281:1: entryRuleSTVarDeclarationBlock returns [EObject current=null] : iv_ruleSTVarDeclarationBlock= ruleSTVarDeclarationBlock EOF ;
    public final EObject entryRuleSTVarDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:281:62: (iv_ruleSTVarDeclarationBlock= ruleSTVarDeclarationBlock EOF )
            // InternalSTFunctionParser.g:282:2: iv_ruleSTVarDeclarationBlock= ruleSTVarDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTVarDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTVarDeclarationBlock=ruleSTVarDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTVarDeclarationBlock; 
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
    // $ANTLR end "entryRuleSTVarDeclarationBlock"


    // $ANTLR start "ruleSTVarDeclarationBlock"
    // InternalSTFunctionParser.g:288:1: ruleSTVarDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:294:2: ( ( () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:295:2: ( () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:295:2: ( () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:296:3: () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:296:3: ()
            // InternalSTFunctionParser.g:297:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationBlockAccess().getSTVarPlainDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarDeclarationBlockAccess().getVARKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:307:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==CONSTANT) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSTFunctionParser.g:308:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:308:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:309:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSTVarDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:321:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_ID) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSTFunctionParser.g:322:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:322:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:323:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_varDeclarations_3_0=ruleSTVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTVarDeclarationBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"varDeclarations",
            	      						lv_varDeclarations_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarDeclarationBlockAccess().getEND_VARKeyword_4());
              		
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
    // $ANTLR end "ruleSTVarDeclarationBlock"


    // $ANTLR start "entryRuleSTVarTempDeclarationBlock"
    // InternalSTFunctionParser.g:348:1: entryRuleSTVarTempDeclarationBlock returns [EObject current=null] : iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF ;
    public final EObject entryRuleSTVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarTempDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:348:66: (iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF )
            // InternalSTFunctionParser.g:349:2: iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTVarTempDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTVarTempDeclarationBlock=ruleSTVarTempDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTVarTempDeclarationBlock; 
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
    // $ANTLR end "entryRuleSTVarTempDeclarationBlock"


    // $ANTLR start "ruleSTVarTempDeclarationBlock"
    // InternalSTFunctionParser.g:355:1: ruleSTVarTempDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:361:2: ( ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:362:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:362:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:363:3: () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:363:3: ()
            // InternalSTFunctionParser.g:364:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarTempDeclarationBlockAccess().getSTVarTempDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_TEMP,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarTempDeclarationBlockAccess().getVAR_TEMPKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:374:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==CONSTANT) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSTFunctionParser.g:375:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:375:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:376:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarTempDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSTVarTempDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:388:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_ID) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalSTFunctionParser.g:389:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:389:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:390:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarTempDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_varDeclarations_3_0=ruleSTVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTVarTempDeclarationBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"varDeclarations",
            	      						lv_varDeclarations_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarTempDeclarationBlockAccess().getEND_VARKeyword_4());
              		
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
    // $ANTLR end "ruleSTVarTempDeclarationBlock"


    // $ANTLR start "entryRuleSTVarInputDeclarationBlock"
    // InternalSTFunctionParser.g:415:1: entryRuleSTVarInputDeclarationBlock returns [EObject current=null] : iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF ;
    public final EObject entryRuleSTVarInputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarInputDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:415:67: (iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF )
            // InternalSTFunctionParser.g:416:2: iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTVarInputDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTVarInputDeclarationBlock=ruleSTVarInputDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTVarInputDeclarationBlock; 
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
    // $ANTLR end "entryRuleSTVarInputDeclarationBlock"


    // $ANTLR start "ruleSTVarInputDeclarationBlock"
    // InternalSTFunctionParser.g:422:1: ruleSTVarInputDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarInputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:428:2: ( ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:429:2: ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:429:2: ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:430:3: () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:430:3: ()
            // InternalSTFunctionParser.g:431:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarInputDeclarationBlockAccess().getSTVarInputDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_INPUT,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarInputDeclarationBlockAccess().getVAR_INPUTKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:441:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==CONSTANT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalSTFunctionParser.g:442:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:442:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:443:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarInputDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSTVarInputDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:455:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ID) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalSTFunctionParser.g:456:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:456:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:457:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarInputDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_varDeclarations_3_0=ruleSTVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTVarInputDeclarationBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"varDeclarations",
            	      						lv_varDeclarations_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarInputDeclarationBlockAccess().getEND_VARKeyword_4());
              		
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
    // $ANTLR end "ruleSTVarInputDeclarationBlock"


    // $ANTLR start "entryRuleSTVarOutputDeclarationBlock"
    // InternalSTFunctionParser.g:482:1: entryRuleSTVarOutputDeclarationBlock returns [EObject current=null] : iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF ;
    public final EObject entryRuleSTVarOutputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarOutputDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:482:68: (iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF )
            // InternalSTFunctionParser.g:483:2: iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTVarOutputDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTVarOutputDeclarationBlock=ruleSTVarOutputDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTVarOutputDeclarationBlock; 
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
    // $ANTLR end "entryRuleSTVarOutputDeclarationBlock"


    // $ANTLR start "ruleSTVarOutputDeclarationBlock"
    // InternalSTFunctionParser.g:489:1: ruleSTVarOutputDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarOutputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:495:2: ( ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:496:2: ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:496:2: ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:497:3: () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:497:3: ()
            // InternalSTFunctionParser.g:498:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarOutputDeclarationBlockAccess().getSTVarOutputDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_OUTPUT,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarOutputDeclarationBlockAccess().getVAR_OUTPUTKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:508:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==CONSTANT) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalSTFunctionParser.g:509:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:509:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:510:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarOutputDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSTVarOutputDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:522:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_ID) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalSTFunctionParser.g:523:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:523:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:524:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarOutputDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_varDeclarations_3_0=ruleSTVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTVarOutputDeclarationBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"varDeclarations",
            	      						lv_varDeclarations_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarOutputDeclarationBlockAccess().getEND_VARKeyword_4());
              		
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
    // $ANTLR end "ruleSTVarOutputDeclarationBlock"


    // $ANTLR start "entryRuleSTVarInOutDeclarationBlock"
    // InternalSTFunctionParser.g:549:1: entryRuleSTVarInOutDeclarationBlock returns [EObject current=null] : iv_ruleSTVarInOutDeclarationBlock= ruleSTVarInOutDeclarationBlock EOF ;
    public final EObject entryRuleSTVarInOutDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarInOutDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:549:67: (iv_ruleSTVarInOutDeclarationBlock= ruleSTVarInOutDeclarationBlock EOF )
            // InternalSTFunctionParser.g:550:2: iv_ruleSTVarInOutDeclarationBlock= ruleSTVarInOutDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTVarInOutDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTVarInOutDeclarationBlock=ruleSTVarInOutDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTVarInOutDeclarationBlock; 
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
    // $ANTLR end "entryRuleSTVarInOutDeclarationBlock"


    // $ANTLR start "ruleSTVarInOutDeclarationBlock"
    // InternalSTFunctionParser.g:556:1: ruleSTVarInOutDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarInOutDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:562:2: ( ( () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:563:2: ( () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:563:2: ( () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:564:3: () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:564:3: ()
            // InternalSTFunctionParser.g:565:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarInOutDeclarationBlockAccess().getSTVarInOutDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_IN_OUT,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarInOutDeclarationBlockAccess().getVAR_IN_OUTKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:575:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==CONSTANT) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalSTFunctionParser.g:576:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:576:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:577:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarInOutDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSTVarInOutDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:589:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_ID) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalSTFunctionParser.g:590:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:590:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:591:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarInOutDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_varDeclarations_3_0=ruleSTVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTVarInOutDeclarationBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"varDeclarations",
            	      						lv_varDeclarations_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarInOutDeclarationBlockAccess().getEND_VARKeyword_4());
              		
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
    // $ANTLR end "ruleSTVarInOutDeclarationBlock"


    // $ANTLR start "entryRuleSTVarDeclaration"
    // InternalSTFunctionParser.g:616:1: entryRuleSTVarDeclaration returns [EObject current=null] : iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF ;
    public final EObject entryRuleSTVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclaration = null;


        try {
            // InternalSTFunctionParser.g:616:57: (iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF )
            // InternalSTFunctionParser.g:617:2: iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF
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
    // InternalSTFunctionParser.g:623:1: ruleSTVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) ;
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
            // InternalSTFunctionParser.g:629:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) )
            // InternalSTFunctionParser.g:630:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            {
            // InternalSTFunctionParser.g:630:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            // InternalSTFunctionParser.g:631:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon
            {
            // InternalSTFunctionParser.g:631:3: ()
            // InternalSTFunctionParser.g:632:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:638:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTFunctionParser.g:639:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:639:4: (lv_name_1_0= RULE_ID )
            // InternalSTFunctionParser.g:640:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:656:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==AT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSTFunctionParser.g:657:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalSTFunctionParser.g:661:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSTFunctionParser.g:662:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSTFunctionParser.g:662:5: (otherlv_3= RULE_ID )
                    // InternalSTFunctionParser.g:663:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Colon,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3());
              		
            }
            // InternalSTFunctionParser.g:679:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==ARRAY) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSTFunctionParser.g:680:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalSTFunctionParser.g:680:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalSTFunctionParser.g:681:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalSTFunctionParser.g:681:5: (lv_array_5_0= ARRAY )
                    // InternalSTFunctionParser.g:682:6: lv_array_5_0= ARRAY
                    {
                    lv_array_5_0=(Token)match(input,ARRAY,FOLLOW_14); if (state.failed) return current;
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

                    // InternalSTFunctionParser.g:694:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==LeftSquareBracket) ) {
                        int LA19_1 = input.LA(2);

                        if ( ((LA19_1>=LDATE_AND_TIME && LA19_1<=DATE_AND_TIME)||(LA19_1>=LTIME_OF_DAY && LA19_1<=TIME_OF_DAY)||LA19_1==WSTRING||LA19_1==STRING||LA19_1==DWORD||(LA19_1>=FALSE && LA19_1<=ULINT)||(LA19_1>=USINT && LA19_1<=WCHAR)||(LA19_1>=BOOL && LA19_1<=BYTE)||(LA19_1>=CHAR && LA19_1<=DINT)||(LA19_1>=LINT && LA19_1<=SINT)||(LA19_1>=TIME && LA19_1<=WORD)||(LA19_1>=INT && LA19_1<=LDT)||(LA19_1>=NOT && LA19_1<=TOD)||LA19_1==DT||(LA19_1>=LD && LA19_1<=LT)||LA19_1==LeftParenthesis||LA19_1==PlusSign||LA19_1==HyphenMinus||LA19_1==D||LA19_1==T||(LA19_1>=RULE_NON_DECIMAL && LA19_1<=RULE_INT)||(LA19_1>=RULE_ID && LA19_1<=RULE_STRING)) ) {
                            alt19=1;
                        }
                        else if ( (LA19_1==Asterisk) ) {
                            alt19=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 19, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 19, 0, input);

                        throw nvae;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalSTFunctionParser.g:695:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalSTFunctionParser.g:695:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalSTFunctionParser.g:696:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalSTFunctionParser.g:700:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalSTFunctionParser.g:701:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalSTFunctionParser.g:701:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalSTFunctionParser.g:702:8: lv_ranges_7_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_16);
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

                            // InternalSTFunctionParser.g:719:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop17:
                            do {
                                int alt17=2;
                                int LA17_0 = input.LA(1);

                                if ( (LA17_0==Comma) ) {
                                    alt17=1;
                                }


                                switch (alt17) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:720:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTFunctionParser.g:724:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalSTFunctionParser.g:725:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalSTFunctionParser.g:725:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalSTFunctionParser.g:726:9: lv_ranges_9_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_16);
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
                            	    break loop17;
                                }
                            } while (true);

                            otherlv_10=(Token)match(input,RightSquareBracket,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTFunctionParser.g:750:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalSTFunctionParser.g:750:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalSTFunctionParser.g:751:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalSTFunctionParser.g:755:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalSTFunctionParser.g:756:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalSTFunctionParser.g:756:7: (lv_count_12_0= Asterisk )
                            // InternalSTFunctionParser.g:757:8: lv_count_12_0= Asterisk
                            {
                            lv_count_12_0=(Token)match(input,Asterisk,FOLLOW_16); if (state.failed) return current;
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

                            // InternalSTFunctionParser.g:769:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( (LA18_0==Comma) ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:770:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_18); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTFunctionParser.g:774:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalSTFunctionParser.g:775:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalSTFunctionParser.g:775:8: (lv_count_14_0= Asterisk )
                            	    // InternalSTFunctionParser.g:776:9: lv_count_14_0= Asterisk
                            	    {
                            	    lv_count_14_0=(Token)match(input,Asterisk,FOLLOW_16); if (state.failed) return current;
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
                            	    break loop18;
                                }
                            } while (true);

                            otherlv_15=(Token)match(input,RightSquareBracket,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_15, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,OF,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:800:3: ( ( ruleSTAnyType ) )
            // InternalSTFunctionParser.g:801:4: ( ruleSTAnyType )
            {
            // InternalSTFunctionParser.g:801:4: ( ruleSTAnyType )
            // InternalSTFunctionParser.g:802:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTVarDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0());
              				
            }
            pushFollow(FOLLOW_19);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTFunctionParser.g:816:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==LeftSquareBracket) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSTFunctionParser.g:817:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalSTFunctionParser.g:821:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:822:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:822:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:823:6: lv_maxLength_19_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_20);
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

                    otherlv_20=(Token)match(input,RightSquareBracket,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:845:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==ColonEqualsSign) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSTFunctionParser.g:846:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalSTFunctionParser.g:850:4: ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    // InternalSTFunctionParser.g:851:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    {
                    // InternalSTFunctionParser.g:851:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    // InternalSTFunctionParser.g:852:6: lv_defaultValue_22_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_23);
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
    // InternalSTFunctionParser.g:878:1: entryRuleSTInitializerExpression returns [EObject current=null] : iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF ;
    public final EObject entryRuleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:878:64: (iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF )
            // InternalSTFunctionParser.g:879:2: iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF
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
    // InternalSTFunctionParser.g:885:1: ruleSTInitializerExpression returns [EObject current=null] : (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression ) ;
    public final EObject ruleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STElementaryInitializerExpression_0 = null;

        EObject this_STArrayInitializerExpression_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:891:2: ( (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression ) )
            // InternalSTFunctionParser.g:892:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression )
            {
            // InternalSTFunctionParser.g:892:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( ((LA23_0>=LDATE_AND_TIME && LA23_0<=DATE_AND_TIME)||(LA23_0>=LTIME_OF_DAY && LA23_0<=TIME_OF_DAY)||LA23_0==WSTRING||LA23_0==STRING||LA23_0==DWORD||(LA23_0>=FALSE && LA23_0<=ULINT)||(LA23_0>=USINT && LA23_0<=WCHAR)||(LA23_0>=BOOL && LA23_0<=BYTE)||(LA23_0>=CHAR && LA23_0<=DINT)||(LA23_0>=LINT && LA23_0<=SINT)||(LA23_0>=TIME && LA23_0<=WORD)||(LA23_0>=INT && LA23_0<=LDT)||(LA23_0>=NOT && LA23_0<=TOD)||LA23_0==DT||(LA23_0>=LD && LA23_0<=LT)||LA23_0==LeftParenthesis||LA23_0==PlusSign||LA23_0==HyphenMinus||LA23_0==D||LA23_0==T||(LA23_0>=RULE_NON_DECIMAL && LA23_0<=RULE_INT)||(LA23_0>=RULE_ID && LA23_0<=RULE_STRING)) ) {
                alt23=1;
            }
            else if ( (LA23_0==LeftSquareBracket) ) {
                alt23=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // InternalSTFunctionParser.g:893:3: this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression
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
                    // InternalSTFunctionParser.g:902:3: this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression
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
    // InternalSTFunctionParser.g:914:1: entryRuleSTElementaryInitializerExpression returns [EObject current=null] : iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF ;
    public final EObject entryRuleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElementaryInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:914:74: (iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF )
            // InternalSTFunctionParser.g:915:2: iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF
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
    // InternalSTFunctionParser.g:921:1: ruleSTElementaryInitializerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:927:2: ( ( (lv_value_0_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:928:2: ( (lv_value_0_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:928:2: ( (lv_value_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:929:3: (lv_value_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:929:3: (lv_value_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:930:4: lv_value_0_0= ruleSTExpression
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
    // InternalSTFunctionParser.g:950:1: entryRuleSTArrayInitializerExpression returns [EObject current=null] : iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF ;
    public final EObject entryRuleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:950:69: (iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF )
            // InternalSTFunctionParser.g:951:2: iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF
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
    // InternalSTFunctionParser.g:957:1: ruleSTArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:963:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTFunctionParser.g:964:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTFunctionParser.g:964:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTFunctionParser.g:965:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:969:3: ( (lv_values_1_0= ruleSTArrayInitElement ) )
            // InternalSTFunctionParser.g:970:4: (lv_values_1_0= ruleSTArrayInitElement )
            {
            // InternalSTFunctionParser.g:970:4: (lv_values_1_0= ruleSTArrayInitElement )
            // InternalSTFunctionParser.g:971:5: lv_values_1_0= ruleSTArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_16);
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

            // InternalSTFunctionParser.g:988:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==Comma) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalSTFunctionParser.g:989:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:993:4: ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    // InternalSTFunctionParser.g:994:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    {
            	    // InternalSTFunctionParser.g:994:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    // InternalSTFunctionParser.g:995:6: lv_values_3_0= ruleSTArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_16);
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
            	    break loop24;
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
    // InternalSTFunctionParser.g:1021:1: entryRuleSTArrayInitElement returns [EObject current=null] : iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF ;
    public final EObject entryRuleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitElement = null;


        try {
            // InternalSTFunctionParser.g:1021:59: (iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF )
            // InternalSTFunctionParser.g:1022:2: iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF
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
    // InternalSTFunctionParser.g:1028:1: ruleSTArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )? ) ;
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
            // InternalSTFunctionParser.g:1034:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )? ) )
            // InternalSTFunctionParser.g:1035:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )? )
            {
            // InternalSTFunctionParser.g:1035:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )? )
            // InternalSTFunctionParser.g:1036:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )?
            {
            // InternalSTFunctionParser.g:1036:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1037:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1037:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1038:5: lv_indexOrInitExpression_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getIndexOrInitExpressionSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_24);
            lv_indexOrInitExpression_0_0=ruleSTExpression();

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
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTFunctionParser.g:1055:3: (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==LeftParenthesis) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalSTFunctionParser.g:1056:4: otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalSTFunctionParser.g:1060:4: ( (lv_initExpressions_2_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:1061:5: (lv_initExpressions_2_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:1061:5: (lv_initExpressions_2_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:1062:6: lv_initExpressions_2_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_25);
                    lv_initExpressions_2_0=ruleSTExpression();

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
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSTFunctionParser.g:1079:4: (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==Comma) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // InternalSTFunctionParser.g:1080:5: otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getSTArrayInitElementAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalSTFunctionParser.g:1084:5: ( (lv_initExpressions_4_0= ruleSTExpression ) )
                    	    // InternalSTFunctionParser.g:1085:6: (lv_initExpressions_4_0= ruleSTExpression )
                    	    {
                    	    // InternalSTFunctionParser.g:1085:6: (lv_initExpressions_4_0= ruleSTExpression )
                    	    // InternalSTFunctionParser.g:1086:7: lv_initExpressions_4_0= ruleSTExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTExpressionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_25);
                    	    lv_initExpressions_4_0=ruleSTExpression();

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
                    	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
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
    // InternalSTFunctionParser.g:1113:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTFunctionParser.g:1113:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTFunctionParser.g:1114:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalSTFunctionParser.g:1120:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) ;
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
            // InternalSTFunctionParser.g:1126:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) )
            // InternalSTFunctionParser.g:1127:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            {
            // InternalSTFunctionParser.g:1127:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( ((LA28_0>=LDATE_AND_TIME && LA28_0<=DATE_AND_TIME)||(LA28_0>=LTIME_OF_DAY && LA28_0<=TIME_OF_DAY)||LA28_0==CONTINUE||LA28_0==WSTRING||(LA28_0>=REPEAT && LA28_0<=STRING)||LA28_0==DWORD||(LA28_0>=FALSE && LA28_0<=ULINT)||(LA28_0>=USINT && LA28_0<=DINT)||(LA28_0>=EXIT && LA28_0<=SINT)||(LA28_0>=TIME && LA28_0<=WORD)||(LA28_0>=FOR && LA28_0<=LDT)||LA28_0==TOD||(LA28_0>=DT && LA28_0<=LT)||LA28_0==LeftParenthesis||LA28_0==PlusSign||LA28_0==HyphenMinus||LA28_0==D||LA28_0==T||(LA28_0>=RULE_NON_DECIMAL && LA28_0<=RULE_INT)||(LA28_0>=RULE_ID && LA28_0<=RULE_STRING)) ) {
                alt28=1;
            }
            else if ( (LA28_0==Semicolon) ) {
                alt28=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // InternalSTFunctionParser.g:1128:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    {
                    // InternalSTFunctionParser.g:1128:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    // InternalSTFunctionParser.g:1129:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon
                    {
                    // InternalSTFunctionParser.g:1129:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )
                    int alt27=10;
                    alt27 = dfa27.predict(input);
                    switch (alt27) {
                        case 1 :
                            // InternalSTFunctionParser.g:1130:5: this_STIfStatement_0= ruleSTIfStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTIfStatementParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_23);
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
                            // InternalSTFunctionParser.g:1139:5: this_STCaseStatement_1= ruleSTCaseStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCaseStatementParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_23);
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
                            // InternalSTFunctionParser.g:1148:5: this_STForStatement_2= ruleSTForStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTForStatementParserRuleCall_0_0_2());
                              				
                            }
                            pushFollow(FOLLOW_23);
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
                            // InternalSTFunctionParser.g:1157:5: this_STWhileStatement_3= ruleSTWhileStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTWhileStatementParserRuleCall_0_0_3());
                              				
                            }
                            pushFollow(FOLLOW_23);
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
                            // InternalSTFunctionParser.g:1166:5: this_STRepeatStatement_4= ruleSTRepeatStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTRepeatStatementParserRuleCall_0_0_4());
                              				
                            }
                            pushFollow(FOLLOW_23);
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
                            // InternalSTFunctionParser.g:1175:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            {
                            // InternalSTFunctionParser.g:1175:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            // InternalSTFunctionParser.g:1176:6: ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentStatementParserRuleCall_0_0_5());
                              					
                            }
                            pushFollow(FOLLOW_23);
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
                            // InternalSTFunctionParser.g:1187:5: this_STCallStatement_6= ruleSTCallStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCallStatementParserRuleCall_0_0_6());
                              				
                            }
                            pushFollow(FOLLOW_23);
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
                            // InternalSTFunctionParser.g:1196:5: ( () otherlv_8= RETURN )
                            {
                            // InternalSTFunctionParser.g:1196:5: ( () otherlv_8= RETURN )
                            // InternalSTFunctionParser.g:1197:6: () otherlv_8= RETURN
                            {
                            // InternalSTFunctionParser.g:1197:6: ()
                            // InternalSTFunctionParser.g:1198:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_7_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_8=(Token)match(input,RETURN,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_8, grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_7_1());
                              					
                            }

                            }


                            }
                            break;
                        case 9 :
                            // InternalSTFunctionParser.g:1210:5: ( () otherlv_10= CONTINUE )
                            {
                            // InternalSTFunctionParser.g:1210:5: ( () otherlv_10= CONTINUE )
                            // InternalSTFunctionParser.g:1211:6: () otherlv_10= CONTINUE
                            {
                            // InternalSTFunctionParser.g:1211:6: ()
                            // InternalSTFunctionParser.g:1212:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_8_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_10=(Token)match(input,CONTINUE,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_8_1());
                              					
                            }

                            }


                            }
                            break;
                        case 10 :
                            // InternalSTFunctionParser.g:1224:5: ( () otherlv_12= EXIT )
                            {
                            // InternalSTFunctionParser.g:1224:5: ( () otherlv_12= EXIT )
                            // InternalSTFunctionParser.g:1225:6: () otherlv_12= EXIT
                            {
                            // InternalSTFunctionParser.g:1225:6: ()
                            // InternalSTFunctionParser.g:1226:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTExitAction_0_0_9_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_12=(Token)match(input,EXIT,FOLLOW_23); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:1244:3: ( () otherlv_15= Semicolon )
                    {
                    // InternalSTFunctionParser.g:1244:3: ( () otherlv_15= Semicolon )
                    // InternalSTFunctionParser.g:1245:4: () otherlv_15= Semicolon
                    {
                    // InternalSTFunctionParser.g:1245:4: ()
                    // InternalSTFunctionParser.g:1246:5: 
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
    // InternalSTFunctionParser.g:1261:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTFunctionParser.g:1261:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTFunctionParser.g:1262:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTFunctionParser.g:1268:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1274:2: ( ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) )
            // InternalSTFunctionParser.g:1275:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            {
            // InternalSTFunctionParser.g:1275:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:1276:3: ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:1276:3: ( (lv_left_0_0= ruleSTAccessExpression ) )
            // InternalSTFunctionParser.g:1277:4: (lv_left_0_0= ruleSTAccessExpression )
            {
            // InternalSTFunctionParser.g:1277:4: (lv_left_0_0= ruleSTAccessExpression )
            // InternalSTFunctionParser.g:1278:5: lv_left_0_0= ruleSTAccessExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAssignmentStatementAccess().getLeftSTAccessExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_26);
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

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTAssignmentStatementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1299:3: ( (lv_right_2_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1300:4: (lv_right_2_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1300:4: (lv_right_2_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1301:5: lv_right_2_0= ruleSTExpression
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
    // InternalSTFunctionParser.g:1322:1: entryRuleSTCallStatement returns [EObject current=null] : iv_ruleSTCallStatement= ruleSTCallStatement EOF ;
    public final EObject entryRuleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallStatement = null;


        try {
            // InternalSTFunctionParser.g:1322:56: (iv_ruleSTCallStatement= ruleSTCallStatement EOF )
            // InternalSTFunctionParser.g:1323:2: iv_ruleSTCallStatement= ruleSTCallStatement EOF
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
    // InternalSTFunctionParser.g:1329:1: ruleSTCallStatement returns [EObject current=null] : ( (lv_call_0_0= ruleSTAccessExpression ) ) ;
    public final EObject ruleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject lv_call_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1335:2: ( ( (lv_call_0_0= ruleSTAccessExpression ) ) )
            // InternalSTFunctionParser.g:1336:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            {
            // InternalSTFunctionParser.g:1336:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            // InternalSTFunctionParser.g:1337:3: (lv_call_0_0= ruleSTAccessExpression )
            {
            // InternalSTFunctionParser.g:1337:3: (lv_call_0_0= ruleSTAccessExpression )
            // InternalSTFunctionParser.g:1338:4: lv_call_0_0= ruleSTAccessExpression
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
    // InternalSTFunctionParser.g:1358:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalSTFunctionParser.g:1358:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalSTFunctionParser.g:1359:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
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
    // InternalSTFunctionParser.g:1365:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1371:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalSTFunctionParser.g:1372:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalSTFunctionParser.g:1372:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            int alt29=3;
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
            case INT:
            case LDT:
            case TOD:
            case DT:
            case LD:
            case LT:
            case LeftParenthesis:
            case PlusSign:
            case HyphenMinus:
            case D:
            case T:
            case RULE_NON_DECIMAL:
            case RULE_INT:
            case RULE_STRING:
                {
                alt29=1;
                }
                break;
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case ColonEqualsSign:
                    {
                    alt29=2;
                    }
                    break;
                case EqualsSignGreaterThanSign:
                    {
                    alt29=3;
                    }
                    break;
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
                    alt29=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 2, input);

                    throw nvae;
                }

                }
                break;
            case NOT:
                {
                int LA29_3 = input.LA(2);

                if ( (LA29_3==RULE_ID) ) {
                    int LA29_6 = input.LA(3);

                    if ( (LA29_6==EOF||LA29_6==AND||LA29_6==MOD||LA29_6==XOR||(LA29_6>=AsteriskAsterisk && LA29_6<=FullStopFullStop)||(LA29_6>=LessThanSignEqualsSign && LA29_6<=LessThanSignGreaterThanSign)||LA29_6==GreaterThanSignEqualsSign||LA29_6==OR||(LA29_6>=Ampersand && LA29_6<=Solidus)||(LA29_6>=LessThanSign && LA29_6<=GreaterThanSign)||LA29_6==LeftSquareBracket) ) {
                        alt29=1;
                    }
                    else if ( (LA29_6==EqualsSignGreaterThanSign) ) {
                        alt29=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 29, 6, input);

                        throw nvae;
                    }
                }
                else if ( ((LA29_3>=LDATE_AND_TIME && LA29_3<=DATE_AND_TIME)||(LA29_3>=LTIME_OF_DAY && LA29_3<=TIME_OF_DAY)||LA29_3==WSTRING||LA29_3==STRING||LA29_3==DWORD||(LA29_3>=FALSE && LA29_3<=ULINT)||(LA29_3>=USINT && LA29_3<=WCHAR)||(LA29_3>=BOOL && LA29_3<=BYTE)||(LA29_3>=CHAR && LA29_3<=DINT)||(LA29_3>=LINT && LA29_3<=SINT)||(LA29_3>=TIME && LA29_3<=WORD)||(LA29_3>=INT && LA29_3<=LDT)||(LA29_3>=NOT && LA29_3<=TOD)||LA29_3==DT||(LA29_3>=LD && LA29_3<=LT)||LA29_3==LeftParenthesis||LA29_3==PlusSign||LA29_3==HyphenMinus||LA29_3==D||LA29_3==T||(LA29_3>=RULE_NON_DECIMAL && LA29_3<=RULE_INT)||LA29_3==RULE_STRING) ) {
                    alt29=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }

            switch (alt29) {
                case 1 :
                    // InternalSTFunctionParser.g:1373:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
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
                    // InternalSTFunctionParser.g:1382:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
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
                    // InternalSTFunctionParser.g:1391:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
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
    // InternalSTFunctionParser.g:1403:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalSTFunctionParser.g:1403:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalSTFunctionParser.g:1404:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
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
    // InternalSTFunctionParser.g:1410:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_arg_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_arg_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1416:2: ( ( (lv_arg_0_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:1417:2: ( (lv_arg_0_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:1417:2: ( (lv_arg_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1418:3: (lv_arg_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1418:3: (lv_arg_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1419:4: lv_arg_0_0= ruleSTExpression
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
    // InternalSTFunctionParser.g:1439:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalSTFunctionParser.g:1439:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalSTFunctionParser.g:1440:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
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
    // InternalSTFunctionParser.g:1446:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_source_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1452:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) ) )
            // InternalSTFunctionParser.g:1453:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) )
            {
            // InternalSTFunctionParser.g:1453:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:1454:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:1454:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTFunctionParser.g:1455:4: (otherlv_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:1455:4: (otherlv_0= RULE_ID )
            // InternalSTFunctionParser.g:1456:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_26); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTCallNamedInputArgumentAccess().getTargetINamedElementCrossReference_0_0());
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1471:3: ( (lv_source_2_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1472:4: (lv_source_2_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1472:4: (lv_source_2_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1473:5: lv_source_2_0= ruleSTExpression
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
    // InternalSTFunctionParser.g:1494:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalSTFunctionParser.g:1494:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalSTFunctionParser.g:1495:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
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
    // InternalSTFunctionParser.g:1501:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:1507:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) ) )
            // InternalSTFunctionParser.g:1508:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) )
            {
            // InternalSTFunctionParser.g:1508:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) )
            // InternalSTFunctionParser.g:1509:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) )
            {
            // InternalSTFunctionParser.g:1509:3: ( (lv_not_0_0= NOT ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==NOT) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalSTFunctionParser.g:1510:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTFunctionParser.g:1510:4: (lv_not_0_0= NOT )
                    // InternalSTFunctionParser.g:1511:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_4); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:1523:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTFunctionParser.g:1524:4: (otherlv_1= RULE_ID )
            {
            // InternalSTFunctionParser.g:1524:4: (otherlv_1= RULE_ID )
            // InternalSTFunctionParser.g:1525:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_27); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTCallNamedOutputArgumentAccess().getSourceINamedElementCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1540:3: ( (otherlv_3= RULE_ID ) )
            // InternalSTFunctionParser.g:1541:4: (otherlv_3= RULE_ID )
            {
            // InternalSTFunctionParser.g:1541:4: (otherlv_3= RULE_ID )
            // InternalSTFunctionParser.g:1542:5: otherlv_3= RULE_ID
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
    // InternalSTFunctionParser.g:1557:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalSTFunctionParser.g:1557:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalSTFunctionParser.g:1558:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalSTFunctionParser.g:1564:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTFunctionParser.g:1570:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTFunctionParser.g:1571:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTFunctionParser.g:1571:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTFunctionParser.g:1572:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1576:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1577:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1577:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1578:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_28);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_29); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatementAccess().getTHENKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1599:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( ((LA31_0>=LDATE_AND_TIME && LA31_0<=DATE_AND_TIME)||(LA31_0>=LTIME_OF_DAY && LA31_0<=TIME_OF_DAY)||LA31_0==CONTINUE||LA31_0==WSTRING||(LA31_0>=REPEAT && LA31_0<=STRING)||LA31_0==DWORD||(LA31_0>=FALSE && LA31_0<=ULINT)||(LA31_0>=USINT && LA31_0<=DINT)||(LA31_0>=EXIT && LA31_0<=SINT)||(LA31_0>=TIME && LA31_0<=WORD)||(LA31_0>=FOR && LA31_0<=LDT)||LA31_0==TOD||(LA31_0>=DT && LA31_0<=LT)||LA31_0==LeftParenthesis||LA31_0==PlusSign||LA31_0==HyphenMinus||LA31_0==Semicolon||LA31_0==D||LA31_0==T||(LA31_0>=RULE_NON_DECIMAL && LA31_0<=RULE_INT)||(LA31_0>=RULE_ID && LA31_0<=RULE_STRING)) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1600:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1600:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1601:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_29);
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
            	    break loop31;
                }
            } while (true);

            // InternalSTFunctionParser.g:1618:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==ELSIF) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1619:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTFunctionParser.g:1619:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTFunctionParser.g:1620:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_30);
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
            	    break loop32;
                }
            } while (true);

            // InternalSTFunctionParser.g:1637:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==ELSE) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalSTFunctionParser.g:1638:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTFunctionParser.g:1638:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTFunctionParser.g:1639:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_31);
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
    // InternalSTFunctionParser.g:1664:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTFunctionParser.g:1664:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTFunctionParser.g:1665:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTFunctionParser.g:1671:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1677:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1678:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1678:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1679:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1683:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1684:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1684:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1685:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_28);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1706:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( ((LA34_0>=LDATE_AND_TIME && LA34_0<=DATE_AND_TIME)||(LA34_0>=LTIME_OF_DAY && LA34_0<=TIME_OF_DAY)||LA34_0==CONTINUE||LA34_0==WSTRING||(LA34_0>=REPEAT && LA34_0<=STRING)||LA34_0==DWORD||(LA34_0>=FALSE && LA34_0<=ULINT)||(LA34_0>=USINT && LA34_0<=DINT)||(LA34_0>=EXIT && LA34_0<=SINT)||(LA34_0>=TIME && LA34_0<=WORD)||(LA34_0>=FOR && LA34_0<=LDT)||LA34_0==TOD||(LA34_0>=DT && LA34_0<=LT)||LA34_0==LeftParenthesis||LA34_0==PlusSign||LA34_0==HyphenMinus||LA34_0==Semicolon||LA34_0==D||LA34_0==T||(LA34_0>=RULE_NON_DECIMAL && LA34_0<=RULE_INT)||(LA34_0>=RULE_ID && LA34_0<=RULE_STRING)) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1707:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1707:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1708:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalSTFunctionParser.g:1729:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTFunctionParser.g:1729:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTFunctionParser.g:1730:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTFunctionParser.g:1736:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTFunctionParser.g:1742:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTFunctionParser.g:1743:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTFunctionParser.g:1743:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTFunctionParser.g:1744:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1748:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1749:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1749:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1750:5: lv_selector_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_17);
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

            otherlv_2=(Token)match(input,OF,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCaseStatementAccess().getOFKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1771:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt35=0;
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( ((LA35_0>=LDATE_AND_TIME && LA35_0<=DATE_AND_TIME)||(LA35_0>=LTIME_OF_DAY && LA35_0<=TIME_OF_DAY)||LA35_0==WSTRING||LA35_0==STRING||LA35_0==DWORD||(LA35_0>=FALSE && LA35_0<=ULINT)||(LA35_0>=USINT && LA35_0<=WCHAR)||(LA35_0>=BOOL && LA35_0<=BYTE)||(LA35_0>=CHAR && LA35_0<=DINT)||(LA35_0>=LINT && LA35_0<=SINT)||(LA35_0>=TIME && LA35_0<=WORD)||(LA35_0>=INT && LA35_0<=LDT)||(LA35_0>=NOT && LA35_0<=TOD)||LA35_0==DT||(LA35_0>=LD && LA35_0<=LT)||LA35_0==LeftParenthesis||LA35_0==PlusSign||LA35_0==HyphenMinus||LA35_0==D||LA35_0==T||(LA35_0>=RULE_NON_DECIMAL && LA35_0<=RULE_INT)||(LA35_0>=RULE_ID && LA35_0<=RULE_STRING)) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1772:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTFunctionParser.g:1772:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTFunctionParser.g:1773:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_33);
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
            	    if ( cnt35 >= 1 ) break loop35;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(35, input);
                        throw eee;
                }
                cnt35++;
            } while (true);

            // InternalSTFunctionParser.g:1790:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==ELSE) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalSTFunctionParser.g:1791:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTFunctionParser.g:1791:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTFunctionParser.g:1792:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_34);
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
    // InternalSTFunctionParser.g:1817:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTFunctionParser.g:1817:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTFunctionParser.g:1818:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTFunctionParser.g:1824:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1830:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1831:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1831:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1832:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTFunctionParser.g:1832:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1833:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1833:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1834:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_35);
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

            // InternalSTFunctionParser.g:1851:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==Comma) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1852:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:1856:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTFunctionParser.g:1857:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTFunctionParser.g:1857:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTFunctionParser.g:1858:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_35);
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
            	    break loop37;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1880:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop38:
            do {
                int alt38=2;
                alt38 = dfa38.predict(input);
                switch (alt38) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1881:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1885:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1886:5: lv_statements_4_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTFunctionParser.g:1907:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTFunctionParser.g:1907:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTFunctionParser.g:1908:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTFunctionParser.g:1914:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1920:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1921:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1921:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1922:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTFunctionParser.g:1922:3: ()
            // InternalSTFunctionParser.g:1923:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,ELSE,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1933:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( ((LA39_0>=LDATE_AND_TIME && LA39_0<=DATE_AND_TIME)||(LA39_0>=LTIME_OF_DAY && LA39_0<=TIME_OF_DAY)||LA39_0==CONTINUE||LA39_0==WSTRING||(LA39_0>=REPEAT && LA39_0<=STRING)||LA39_0==DWORD||(LA39_0>=FALSE && LA39_0<=ULINT)||(LA39_0>=USINT && LA39_0<=DINT)||(LA39_0>=EXIT && LA39_0<=SINT)||(LA39_0>=TIME && LA39_0<=WORD)||(LA39_0>=FOR && LA39_0<=LDT)||LA39_0==TOD||(LA39_0>=DT && LA39_0<=LT)||LA39_0==LeftParenthesis||LA39_0==PlusSign||LA39_0==HyphenMinus||LA39_0==Semicolon||LA39_0==D||LA39_0==T||(LA39_0>=RULE_NON_DECIMAL && LA39_0<=RULE_INT)||(LA39_0>=RULE_ID && LA39_0<=RULE_STRING)) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1934:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1934:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1935:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalSTFunctionParser.g:1956:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTFunctionParser.g:1956:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTFunctionParser.g:1957:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTFunctionParser.g:1963:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
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
            // InternalSTFunctionParser.g:1969:2: ( (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalSTFunctionParser.g:1970:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalSTFunctionParser.g:1970:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalSTFunctionParser.g:1971:3: otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1975:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTFunctionParser.g:1976:4: (otherlv_1= RULE_ID )
            {
            // InternalSTFunctionParser.g:1976:4: (otherlv_1= RULE_ID )
            // InternalSTFunctionParser.g:1977:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTForStatementRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_26); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTForStatementAccess().getVariableSTVarDeclarationCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1992:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1993:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1993:4: (lv_from_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1994:5: lv_from_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_36);
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

            otherlv_4=(Token)match(input,TO,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getTOKeyword_4());
              		
            }
            // InternalSTFunctionParser.g:2015:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2016:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2016:4: (lv_to_5_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2017:5: lv_to_5_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_37);
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

            // InternalSTFunctionParser.g:2034:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==BY) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalSTFunctionParser.g:2035:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalSTFunctionParser.g:2039:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:2040:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:2040:5: (lv_by_7_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:2041:6: lv_by_7_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_38);
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

            otherlv_8=(Token)match(input,DO,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
              		
            }
            // InternalSTFunctionParser.g:2063:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( ((LA41_0>=LDATE_AND_TIME && LA41_0<=DATE_AND_TIME)||(LA41_0>=LTIME_OF_DAY && LA41_0<=TIME_OF_DAY)||LA41_0==CONTINUE||LA41_0==WSTRING||(LA41_0>=REPEAT && LA41_0<=STRING)||LA41_0==DWORD||(LA41_0>=FALSE && LA41_0<=ULINT)||(LA41_0>=USINT && LA41_0<=DINT)||(LA41_0>=EXIT && LA41_0<=SINT)||(LA41_0>=TIME && LA41_0<=WORD)||(LA41_0>=FOR && LA41_0<=LDT)||LA41_0==TOD||(LA41_0>=DT && LA41_0<=LT)||LA41_0==LeftParenthesis||LA41_0==PlusSign||LA41_0==HyphenMinus||LA41_0==Semicolon||LA41_0==D||LA41_0==T||(LA41_0>=RULE_NON_DECIMAL && LA41_0<=RULE_INT)||(LA41_0>=RULE_ID && LA41_0<=RULE_STRING)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2064:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2064:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2065:5: lv_statements_9_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_39);
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
            	    break loop41;
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
    // InternalSTFunctionParser.g:2090:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTFunctionParser.g:2090:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTFunctionParser.g:2091:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTFunctionParser.g:2097:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2103:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTFunctionParser.g:2104:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTFunctionParser.g:2104:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTFunctionParser.g:2105:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:2109:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2110:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2110:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2111:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_38);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_40); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2132:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( ((LA42_0>=LDATE_AND_TIME && LA42_0<=DATE_AND_TIME)||(LA42_0>=LTIME_OF_DAY && LA42_0<=TIME_OF_DAY)||LA42_0==CONTINUE||LA42_0==WSTRING||(LA42_0>=REPEAT && LA42_0<=STRING)||LA42_0==DWORD||(LA42_0>=FALSE && LA42_0<=ULINT)||(LA42_0>=USINT && LA42_0<=DINT)||(LA42_0>=EXIT && LA42_0<=SINT)||(LA42_0>=TIME && LA42_0<=WORD)||(LA42_0>=FOR && LA42_0<=LDT)||LA42_0==TOD||(LA42_0>=DT && LA42_0<=LT)||LA42_0==LeftParenthesis||LA42_0==PlusSign||LA42_0==HyphenMinus||LA42_0==Semicolon||LA42_0==D||LA42_0==T||(LA42_0>=RULE_NON_DECIMAL && LA42_0<=RULE_INT)||(LA42_0>=RULE_ID && LA42_0<=RULE_STRING)) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2133:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2133:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2134:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_40);
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
            	    break loop42;
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
    // InternalSTFunctionParser.g:2159:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTFunctionParser.g:2159:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTFunctionParser.g:2160:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTFunctionParser.g:2166:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2172:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTFunctionParser.g:2173:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTFunctionParser.g:2173:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTFunctionParser.g:2174:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_41); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:2178:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=LDATE_AND_TIME && LA43_0<=DATE_AND_TIME)||(LA43_0>=LTIME_OF_DAY && LA43_0<=TIME_OF_DAY)||LA43_0==CONTINUE||LA43_0==WSTRING||(LA43_0>=REPEAT && LA43_0<=STRING)||LA43_0==DWORD||(LA43_0>=FALSE && LA43_0<=ULINT)||(LA43_0>=USINT && LA43_0<=DINT)||(LA43_0>=EXIT && LA43_0<=SINT)||(LA43_0>=TIME && LA43_0<=WORD)||(LA43_0>=FOR && LA43_0<=LDT)||LA43_0==TOD||(LA43_0>=DT && LA43_0<=LT)||LA43_0==LeftParenthesis||LA43_0==PlusSign||LA43_0==HyphenMinus||LA43_0==Semicolon||LA43_0==D||LA43_0==T||(LA43_0>=RULE_NON_DECIMAL && LA43_0<=RULE_INT)||(LA43_0>=RULE_ID && LA43_0<=RULE_STRING)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2179:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2179:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2180:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_41);
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
            	    break loop43;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2201:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2202:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2202:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2203:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_42);
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
    // InternalSTFunctionParser.g:2228:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTFunctionParser.g:2228:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTFunctionParser.g:2229:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTFunctionParser.g:2235:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2241:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTFunctionParser.g:2242:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTFunctionParser.g:2253:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTFunctionParser.g:2253:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTFunctionParser.g:2254:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTFunctionParser.g:2260:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2266:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2267:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2267:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTFunctionParser.g:2268:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_43);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2276:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==FullStopFullStop) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2277:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2277:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTFunctionParser.g:2278:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2278:5: ()
            	    // InternalSTFunctionParser.g:2279:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2285:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTFunctionParser.g:2286:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTFunctionParser.g:2286:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTFunctionParser.g:2287:7: lv_op_2_0= ruleSubrangeOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2305:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTFunctionParser.g:2306:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTFunctionParser.g:2306:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTFunctionParser.g:2307:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_43);
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
            	    break loop44;
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
    // InternalSTFunctionParser.g:2329:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTFunctionParser.g:2329:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTFunctionParser.g:2330:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTFunctionParser.g:2336:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2342:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2343:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2343:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTFunctionParser.g:2344:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2352:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==OR) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2353:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2353:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTFunctionParser.g:2354:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2354:5: ()
            	    // InternalSTFunctionParser.g:2355:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2361:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTFunctionParser.g:2362:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTFunctionParser.g:2362:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTFunctionParser.g:2363:7: lv_op_2_0= ruleOrOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2381:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTFunctionParser.g:2382:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTFunctionParser.g:2382:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTFunctionParser.g:2383:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
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
            	    break loop45;
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
    // InternalSTFunctionParser.g:2405:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTFunctionParser.g:2405:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTFunctionParser.g:2406:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTFunctionParser.g:2412:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2418:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2419:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2419:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTFunctionParser.g:2420:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_45);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2428:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==XOR) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2429:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2429:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTFunctionParser.g:2430:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2430:5: ()
            	    // InternalSTFunctionParser.g:2431:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2437:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTFunctionParser.g:2438:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTFunctionParser.g:2438:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTFunctionParser.g:2439:7: lv_op_2_0= ruleXorOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2457:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTFunctionParser.g:2458:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTFunctionParser.g:2458:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTFunctionParser.g:2459:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_45);
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
            	    break loop46;
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
    // InternalSTFunctionParser.g:2481:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTFunctionParser.g:2481:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTFunctionParser.g:2482:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTFunctionParser.g:2488:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2494:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2495:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2495:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTFunctionParser.g:2496:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2504:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==AND||LA47_0==Ampersand) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2505:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2505:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTFunctionParser.g:2506:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2506:5: ()
            	    // InternalSTFunctionParser.g:2507:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2513:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTFunctionParser.g:2514:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTFunctionParser.g:2514:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTFunctionParser.g:2515:7: lv_op_2_0= ruleAndOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2533:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTFunctionParser.g:2534:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTFunctionParser.g:2534:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTFunctionParser.g:2535:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_46);
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
            	    break loop47;
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
    // InternalSTFunctionParser.g:2557:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTFunctionParser.g:2557:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTFunctionParser.g:2558:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTFunctionParser.g:2564:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2570:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2571:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2571:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTFunctionParser.g:2572:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_47);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2580:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==LessThanSignGreaterThanSign||LA48_0==EqualsSign) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2581:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2581:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTFunctionParser.g:2582:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2582:5: ()
            	    // InternalSTFunctionParser.g:2583:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2589:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTFunctionParser.g:2590:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTFunctionParser.g:2590:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTFunctionParser.g:2591:7: lv_op_2_0= ruleEqualityOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2609:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTFunctionParser.g:2610:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTFunctionParser.g:2610:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTFunctionParser.g:2611:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
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
            	    break loop48;
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
    // InternalSTFunctionParser.g:2633:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTFunctionParser.g:2633:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTFunctionParser.g:2634:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTFunctionParser.g:2640:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2646:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2647:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2647:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTFunctionParser.g:2648:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_48);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2656:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==LessThanSignEqualsSign||LA49_0==GreaterThanSignEqualsSign||LA49_0==LessThanSign||LA49_0==GreaterThanSign) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2657:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2657:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTFunctionParser.g:2658:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2658:5: ()
            	    // InternalSTFunctionParser.g:2659:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2665:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTFunctionParser.g:2666:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTFunctionParser.g:2666:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTFunctionParser.g:2667:7: lv_op_2_0= ruleCompareOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2685:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTFunctionParser.g:2686:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTFunctionParser.g:2686:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTFunctionParser.g:2687:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_48);
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
            	    break loop49;
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
    // InternalSTFunctionParser.g:2709:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTFunctionParser.g:2709:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTFunctionParser.g:2710:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTFunctionParser.g:2716:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2722:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2723:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2723:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTFunctionParser.g:2724:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2732:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==PlusSign||LA50_0==HyphenMinus) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2733:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2733:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTFunctionParser.g:2734:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2734:5: ()
            	    // InternalSTFunctionParser.g:2735:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2741:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTFunctionParser.g:2742:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTFunctionParser.g:2742:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTFunctionParser.g:2743:7: lv_op_2_0= ruleAddSubOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2761:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTFunctionParser.g:2762:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTFunctionParser.g:2762:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTFunctionParser.g:2763:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
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
            	    break loop50;
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
    // InternalSTFunctionParser.g:2785:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTFunctionParser.g:2785:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTFunctionParser.g:2786:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTFunctionParser.g:2792:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2798:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2799:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2799:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTFunctionParser.g:2800:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_50);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2808:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==MOD||LA51_0==Asterisk||LA51_0==Solidus) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2809:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2809:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTFunctionParser.g:2810:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2810:5: ()
            	    // InternalSTFunctionParser.g:2811:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2817:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTFunctionParser.g:2818:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTFunctionParser.g:2818:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTFunctionParser.g:2819:7: lv_op_2_0= ruleMulDivModOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2837:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTFunctionParser.g:2838:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTFunctionParser.g:2838:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTFunctionParser.g:2839:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_50);
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
            	    break loop51;
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
    // InternalSTFunctionParser.g:2861:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTFunctionParser.g:2861:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTFunctionParser.g:2862:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTFunctionParser.g:2868:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2874:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2875:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2875:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalSTFunctionParser.g:2876:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2884:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==AsteriskAsterisk) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2885:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2885:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTFunctionParser.g:2886:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2886:5: ()
            	    // InternalSTFunctionParser.g:2887:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2893:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTFunctionParser.g:2894:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTFunctionParser.g:2894:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTFunctionParser.g:2895:7: lv_op_2_0= rulePowerOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_15);
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

            	    // InternalSTFunctionParser.g:2913:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalSTFunctionParser.g:2914:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalSTFunctionParser.g:2914:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalSTFunctionParser.g:2915:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_51);
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTUnaryExpression"
    // InternalSTFunctionParser.g:2937:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalSTFunctionParser.g:2937:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalSTFunctionParser.g:2938:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalSTFunctionParser.g:2944:1: ruleSTUnaryExpression returns [EObject current=null] : ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2950:2: ( ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalSTFunctionParser.g:2951:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalSTFunctionParser.g:2951:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            int alt53=2;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalSTFunctionParser.g:2952:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    {
                    // InternalSTFunctionParser.g:2952:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    // InternalSTFunctionParser.g:2953:4: ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalSTFunctionParser.g:2964:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalSTFunctionParser.g:2964:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    // InternalSTFunctionParser.g:2965:4: () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    {
                    // InternalSTFunctionParser.g:2965:4: ()
                    // InternalSTFunctionParser.g:2966:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:2972:4: ( (lv_op_2_0= ruleUnaryOperator ) )
                    // InternalSTFunctionParser.g:2973:5: (lv_op_2_0= ruleUnaryOperator )
                    {
                    // InternalSTFunctionParser.g:2973:5: (lv_op_2_0= ruleUnaryOperator )
                    // InternalSTFunctionParser.g:2974:6: lv_op_2_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
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

                    // InternalSTFunctionParser.g:2991:4: ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    // InternalSTFunctionParser.g:2992:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    {
                    // InternalSTFunctionParser.g:2992:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    // InternalSTFunctionParser.g:2993:6: lv_expression_3_0= ruleSTUnaryExpression
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
    // InternalSTFunctionParser.g:3015:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalSTFunctionParser.g:3015:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalSTFunctionParser.g:3016:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalSTFunctionParser.g:3022:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalSTFunctionParser.g:3028:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalSTFunctionParser.g:3029:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalSTFunctionParser.g:3029:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalSTFunctionParser.g:3030:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_52);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3038:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop56:
            do {
                int alt56=3;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==FullStop) ) {
                    alt56=1;
                }
                else if ( (LA56_0==LeftSquareBracket) ) {
                    alt56=2;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3039:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalSTFunctionParser.g:3039:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalSTFunctionParser.g:3040:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalSTFunctionParser.g:3040:5: ()
            	    // InternalSTFunctionParser.g:3041:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_53); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalSTFunctionParser.g:3051:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalSTFunctionParser.g:3052:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3052:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalSTFunctionParser.g:3053:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalSTFunctionParser.g:3053:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt54=2;
            	    int LA54_0 = input.LA(1);

            	    if ( (LA54_0==RULE_ID) ) {
            	        alt54=1;
            	    }
            	    else if ( ((LA54_0>=B && LA54_0<=X)||LA54_0==LeftParenthesis||LA54_0==RULE_INT) ) {
            	        alt54=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 54, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt54) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:3054:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_52);
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
            	            // InternalSTFunctionParser.g:3070:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_52);
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
            	    // InternalSTFunctionParser.g:3090:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalSTFunctionParser.g:3090:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalSTFunctionParser.g:3091:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalSTFunctionParser.g:3091:5: ()
            	    // InternalSTFunctionParser.g:3092:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_5=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_5, grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1());
            	      				
            	    }
            	    // InternalSTFunctionParser.g:3102:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalSTFunctionParser.g:3103:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalSTFunctionParser.g:3103:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalSTFunctionParser.g:3104:7: lv_index_6_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_16);
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

            	    // InternalSTFunctionParser.g:3121:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop55:
            	    do {
            	        int alt55=2;
            	        int LA55_0 = input.LA(1);

            	        if ( (LA55_0==Comma) ) {
            	            alt55=1;
            	        }


            	        switch (alt55) {
            	    	case 1 :
            	    	    // InternalSTFunctionParser.g:3122:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalSTFunctionParser.g:3126:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalSTFunctionParser.g:3127:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalSTFunctionParser.g:3127:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalSTFunctionParser.g:3128:8: lv_index_8_0= ruleSTExpression
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0());
            	    	      							
            	    	    }
            	    	    pushFollow(FOLLOW_16);
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
            	    	    break loop55;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
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
    // InternalSTFunctionParser.g:3156:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalSTFunctionParser.g:3156:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalSTFunctionParser.g:3157:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalSTFunctionParser.g:3163:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STLiteralExpressions_4 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3169:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) )
            // InternalSTFunctionParser.g:3170:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            {
            // InternalSTFunctionParser.g:3170:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            int alt57=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt57=1;
                }
                break;
            case RULE_ID:
                {
                alt57=2;
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
            case DT:
            case LD:
            case LT:
            case PlusSign:
            case HyphenMinus:
            case D:
            case T:
            case RULE_NON_DECIMAL:
            case RULE_INT:
            case RULE_STRING:
                {
                alt57=3;
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
                    // InternalSTFunctionParser.g:3171:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTFunctionParser.g:3171:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTFunctionParser.g:3172:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_54);
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
                    // InternalSTFunctionParser.g:3190:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalSTFunctionParser.g:3199:3: this_STLiteralExpressions_4= ruleSTLiteralExpressions
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
    // InternalSTFunctionParser.g:3211:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalSTFunctionParser.g:3211:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalSTFunctionParser.g:3212:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalSTFunctionParser.g:3218:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( (otherlv_1= RULE_ID ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_call_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3224:2: ( ( () ( (otherlv_1= RULE_ID ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalSTFunctionParser.g:3225:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalSTFunctionParser.g:3225:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalSTFunctionParser.g:3226:3: () ( (otherlv_1= RULE_ID ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalSTFunctionParser.g:3226:3: ()
            // InternalSTFunctionParser.g:3227:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:3233:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTFunctionParser.g:3234:4: (otherlv_1= RULE_ID )
            {
            // InternalSTFunctionParser.g:3234:4: (otherlv_1= RULE_ID )
            // InternalSTFunctionParser.g:3235:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
              				
            }

            }


            }

            // InternalSTFunctionParser.g:3246:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt60=2;
            alt60 = dfa60.predict(input);
            switch (alt60) {
                case 1 :
                    // InternalSTFunctionParser.g:3247:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalSTFunctionParser.g:3247:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) )
                    // InternalSTFunctionParser.g:3248:5: ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalSTFunctionParser.g:3252:5: (lv_call_2_0= LeftParenthesis )
                    // InternalSTFunctionParser.g:3253:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_55); if (state.failed) return current;
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

                    // InternalSTFunctionParser.g:3265:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( ((LA59_0>=LDATE_AND_TIME && LA59_0<=DATE_AND_TIME)||(LA59_0>=LTIME_OF_DAY && LA59_0<=TIME_OF_DAY)||LA59_0==WSTRING||LA59_0==STRING||LA59_0==DWORD||(LA59_0>=FALSE && LA59_0<=ULINT)||(LA59_0>=USINT && LA59_0<=WCHAR)||(LA59_0>=BOOL && LA59_0<=BYTE)||(LA59_0>=CHAR && LA59_0<=DINT)||(LA59_0>=LINT && LA59_0<=SINT)||(LA59_0>=TIME && LA59_0<=WORD)||(LA59_0>=INT && LA59_0<=LDT)||(LA59_0>=NOT && LA59_0<=TOD)||LA59_0==DT||(LA59_0>=LD && LA59_0<=LT)||LA59_0==LeftParenthesis||LA59_0==PlusSign||LA59_0==HyphenMinus||LA59_0==D||LA59_0==T||(LA59_0>=RULE_NON_DECIMAL && LA59_0<=RULE_INT)||(LA59_0>=RULE_ID && LA59_0<=RULE_STRING)) ) {
                        alt59=1;
                    }
                    switch (alt59) {
                        case 1 :
                            // InternalSTFunctionParser.g:3266:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalSTFunctionParser.g:3266:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalSTFunctionParser.g:3267:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalSTFunctionParser.g:3267:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalSTFunctionParser.g:3268:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_25);
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

                            // InternalSTFunctionParser.g:3285:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop58:
                            do {
                                int alt58=2;
                                int LA58_0 = input.LA(1);

                                if ( (LA58_0==Comma) ) {
                                    alt58=1;
                                }


                                switch (alt58) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:3286:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalSTFunctionParser.g:3290:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalSTFunctionParser.g:3291:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalSTFunctionParser.g:3291:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalSTFunctionParser.g:3292:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_25);
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
                            	    break loop58;
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
    // InternalSTFunctionParser.g:3320:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalSTFunctionParser.g:3320:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalSTFunctionParser.g:3321:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalSTFunctionParser.g:3327:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_specifier_1_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3333:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) )
            // InternalSTFunctionParser.g:3334:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            {
            // InternalSTFunctionParser.g:3334:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            // InternalSTFunctionParser.g:3335:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            {
            // InternalSTFunctionParser.g:3335:3: ()
            // InternalSTFunctionParser.g:3336:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:3342:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( ((LA61_0>=B && LA61_0<=X)) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalSTFunctionParser.g:3343:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalSTFunctionParser.g:3343:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalSTFunctionParser.g:3344:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_56);
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

            // InternalSTFunctionParser.g:3361:3: ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==RULE_INT) ) {
                alt62=1;
            }
            else if ( (LA62_0==LeftParenthesis) ) {
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
                    // InternalSTFunctionParser.g:3362:4: ( (lv_index_2_0= RULE_INT ) )
                    {
                    // InternalSTFunctionParser.g:3362:4: ( (lv_index_2_0= RULE_INT ) )
                    // InternalSTFunctionParser.g:3363:5: (lv_index_2_0= RULE_INT )
                    {
                    // InternalSTFunctionParser.g:3363:5: (lv_index_2_0= RULE_INT )
                    // InternalSTFunctionParser.g:3364:6: lv_index_2_0= RULE_INT
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
                    // InternalSTFunctionParser.g:3381:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    {
                    // InternalSTFunctionParser.g:3381:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    // InternalSTFunctionParser.g:3382:5: otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
                      				
                    }
                    // InternalSTFunctionParser.g:3386:5: ( (lv_expression_4_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:3387:6: (lv_expression_4_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:3387:6: (lv_expression_4_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:3388:7: lv_expression_4_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_54);
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
    // InternalSTFunctionParser.g:3415:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTFunctionParser.g:3415:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTFunctionParser.g:3416:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTFunctionParser.g:3422:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
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
            // InternalSTFunctionParser.g:3428:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalSTFunctionParser.g:3429:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalSTFunctionParser.g:3429:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt63=6;
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
                alt63=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt63=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt63=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt63=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt63=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt63=6;
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
                    // InternalSTFunctionParser.g:3430:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalSTFunctionParser.g:3439:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalSTFunctionParser.g:3448:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalSTFunctionParser.g:3457:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalSTFunctionParser.g:3466:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalSTFunctionParser.g:3475:3: this_STStringLiteral_5= ruleSTStringLiteral
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
    // InternalSTFunctionParser.g:3487:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalSTFunctionParser.g:3487:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalSTFunctionParser.g:3488:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalSTFunctionParser.g:3494:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3500:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalSTFunctionParser.g:3501:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalSTFunctionParser.g:3501:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==DWORD||LA64_0==LWORD||(LA64_0>=BOOL && LA64_0<=BYTE)||LA64_0==WORD) ) {
                alt64=1;
            }
            else if ( (LA64_0==LREAL||(LA64_0>=UDINT && LA64_0<=ULINT)||LA64_0==USINT||LA64_0==DINT||LA64_0==LINT||(LA64_0>=REAL && LA64_0<=SINT)||LA64_0==UINT||LA64_0==INT) ) {
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
                    // InternalSTFunctionParser.g:3502:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalSTFunctionParser.g:3513:3: this_STAnyNumType_1= ruleSTAnyNumType
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
    // InternalSTFunctionParser.g:3527:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalSTFunctionParser.g:3527:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalSTFunctionParser.g:3528:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalSTFunctionParser.g:3534:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_3=null;
        AntlrDatatypeRuleToken lv_value_2_1 = null;

        AntlrDatatypeRuleToken lv_value_2_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3540:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTFunctionParser.g:3541:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTFunctionParser.g:3541:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) )
            // InternalSTFunctionParser.g:3542:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTFunctionParser.g:3542:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==DWORD||LA65_0==LREAL||(LA65_0>=LWORD && LA65_0<=ULINT)||LA65_0==USINT||(LA65_0>=BOOL && LA65_0<=BYTE)||LA65_0==DINT||LA65_0==LINT||(LA65_0>=REAL && LA65_0<=SINT)||(LA65_0>=UINT && LA65_0<=WORD)||LA65_0==INT) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalSTFunctionParser.g:3543:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTFunctionParser.g:3543:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalSTFunctionParser.g:3544:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalSTFunctionParser.g:3544:5: ( ruleSTNumericLiteralType )
                    // InternalSTFunctionParser.g:3545:6: ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_57);
                    ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:3564:3: ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) )
            // InternalSTFunctionParser.g:3565:4: ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) )
            {
            // InternalSTFunctionParser.g:3565:4: ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) )
            // InternalSTFunctionParser.g:3566:5: (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL )
            {
            // InternalSTFunctionParser.g:3566:5: (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL )
            int alt66=3;
            switch ( input.LA(1) ) {
            case FALSE:
            case TRUE:
                {
                alt66=1;
                }
                break;
            case PlusSign:
            case HyphenMinus:
            case RULE_INT:
                {
                alt66=2;
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt66=3;
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
                    // InternalSTFunctionParser.g:3567:6: lv_value_2_1= ruleBoolLiteral
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
                    // InternalSTFunctionParser.g:3583:6: lv_value_2_2= ruleNumber
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
                    // InternalSTFunctionParser.g:3599:6: lv_value_2_3= RULE_NON_DECIMAL
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
    // InternalSTFunctionParser.g:3620:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalSTFunctionParser.g:3620:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalSTFunctionParser.g:3621:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalSTFunctionParser.g:3627:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3633:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalSTFunctionParser.g:3634:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalSTFunctionParser.g:3634:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt67=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt67=1;
                }
                break;
            case D:
                {
                alt67=2;
                }
                break;
            case LD:
                {
                alt67=3;
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
                    // InternalSTFunctionParser.g:3635:3: this_STDateType_0= ruleSTDateType
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
                    // InternalSTFunctionParser.g:3646:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:3652:3: kw= LD
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
    // InternalSTFunctionParser.g:3661:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalSTFunctionParser.g:3661:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalSTFunctionParser.g:3662:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalSTFunctionParser.g:3668:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3674:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalSTFunctionParser.g:3675:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalSTFunctionParser.g:3675:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalSTFunctionParser.g:3676:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalSTFunctionParser.g:3676:3: ( ( ruleSTDateLiteralType ) )
            // InternalSTFunctionParser.g:3677:4: ( ruleSTDateLiteralType )
            {
            // InternalSTFunctionParser.g:3677:4: ( ruleSTDateLiteralType )
            // InternalSTFunctionParser.g:3678:5: ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_57);
            ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:3696:3: ( (lv_value_2_0= ruleDate ) )
            // InternalSTFunctionParser.g:3697:4: (lv_value_2_0= ruleDate )
            {
            // InternalSTFunctionParser.g:3697:4: (lv_value_2_0= ruleDate )
            // InternalSTFunctionParser.g:3698:5: lv_value_2_0= ruleDate
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
    // InternalSTFunctionParser.g:3719:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalSTFunctionParser.g:3719:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalSTFunctionParser.g:3720:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalSTFunctionParser.g:3726:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3732:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalSTFunctionParser.g:3733:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalSTFunctionParser.g:3733:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt68=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt68=1;
                }
                break;
            case T:
                {
                alt68=2;
                }
                break;
            case LT:
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
                    // InternalSTFunctionParser.g:3734:3: this_STAnyDurationType_0= ruleSTAnyDurationType
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
                    // InternalSTFunctionParser.g:3745:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:3751:3: kw= LT
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
    // InternalSTFunctionParser.g:3760:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalSTFunctionParser.g:3760:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalSTFunctionParser.g:3761:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalSTFunctionParser.g:3767:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3773:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalSTFunctionParser.g:3774:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalSTFunctionParser.g:3774:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalSTFunctionParser.g:3775:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalSTFunctionParser.g:3775:3: ( ( ruleSTTimeLiteralType ) )
            // InternalSTFunctionParser.g:3776:4: ( ruleSTTimeLiteralType )
            {
            // InternalSTFunctionParser.g:3776:4: ( ruleSTTimeLiteralType )
            // InternalSTFunctionParser.g:3777:5: ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_57);
            ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:3795:3: ( (lv_value_2_0= ruleTime ) )
            // InternalSTFunctionParser.g:3796:4: (lv_value_2_0= ruleTime )
            {
            // InternalSTFunctionParser.g:3796:4: (lv_value_2_0= ruleTime )
            // InternalSTFunctionParser.g:3797:5: lv_value_2_0= ruleTime
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
    // InternalSTFunctionParser.g:3818:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalSTFunctionParser.g:3818:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalSTFunctionParser.g:3819:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalSTFunctionParser.g:3825:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3831:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalSTFunctionParser.g:3832:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalSTFunctionParser.g:3832:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalSTFunctionParser.g:3833:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalSTFunctionParser.g:3833:3: ( ( ruleSTTimeOfDayType ) )
            // InternalSTFunctionParser.g:3834:4: ( ruleSTTimeOfDayType )
            {
            // InternalSTFunctionParser.g:3834:4: ( ruleSTTimeOfDayType )
            // InternalSTFunctionParser.g:3835:5: ruleSTTimeOfDayType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_57);
            ruleSTTimeOfDayType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:3853:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalSTFunctionParser.g:3854:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalSTFunctionParser.g:3854:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalSTFunctionParser.g:3855:5: lv_value_2_0= ruleTimeOfDay
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
    // InternalSTFunctionParser.g:3876:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalSTFunctionParser.g:3876:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalSTFunctionParser.g:3877:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalSTFunctionParser.g:3883:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3889:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalSTFunctionParser.g:3890:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalSTFunctionParser.g:3890:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalSTFunctionParser.g:3891:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalSTFunctionParser.g:3891:3: ( ( ruleSTDateAndTimeType ) )
            // InternalSTFunctionParser.g:3892:4: ( ruleSTDateAndTimeType )
            {
            // InternalSTFunctionParser.g:3892:4: ( ruleSTDateAndTimeType )
            // InternalSTFunctionParser.g:3893:5: ruleSTDateAndTimeType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_57);
            ruleSTDateAndTimeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:3911:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalSTFunctionParser.g:3912:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalSTFunctionParser.g:3912:4: (lv_value_2_0= ruleDateAndTime )
            // InternalSTFunctionParser.g:3913:5: lv_value_2_0= ruleDateAndTime
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
    // InternalSTFunctionParser.g:3934:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalSTFunctionParser.g:3934:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalSTFunctionParser.g:3935:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalSTFunctionParser.g:3941:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:3947:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) )
            // InternalSTFunctionParser.g:3948:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            {
            // InternalSTFunctionParser.g:3948:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            // InternalSTFunctionParser.g:3949:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) )
            {
            // InternalSTFunctionParser.g:3949:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==WSTRING||LA69_0==STRING||LA69_0==WCHAR||LA69_0==CHAR) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalSTFunctionParser.g:3950:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTFunctionParser.g:3950:4: ( ( ruleSTAnyCharsType ) )
                    // InternalSTFunctionParser.g:3951:5: ( ruleSTAnyCharsType )
                    {
                    // InternalSTFunctionParser.g:3951:5: ( ruleSTAnyCharsType )
                    // InternalSTFunctionParser.g:3952:6: ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_57);
                    ruleSTAnyCharsType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:3971:3: ( (lv_value_2_0= RULE_STRING ) )
            // InternalSTFunctionParser.g:3972:4: (lv_value_2_0= RULE_STRING )
            {
            // InternalSTFunctionParser.g:3972:4: (lv_value_2_0= RULE_STRING )
            // InternalSTFunctionParser.g:3973:5: lv_value_2_0= RULE_STRING
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
    // InternalSTFunctionParser.g:3993:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalSTFunctionParser.g:3993:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalSTFunctionParser.g:3994:2: iv_ruleSTAnyType= ruleSTAnyType EOF
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
    // InternalSTFunctionParser.g:4000:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4006:2: ( (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalSTFunctionParser.g:4007:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalSTFunctionParser.g:4007:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==RULE_ID) ) {
                alt70=1;
            }
            else if ( ((LA70_0>=LDATE_AND_TIME && LA70_0<=DATE_AND_TIME)||(LA70_0>=LTIME_OF_DAY && LA70_0<=TIME_OF_DAY)||LA70_0==WSTRING||LA70_0==STRING||LA70_0==DWORD||(LA70_0>=LDATE && LA70_0<=ULINT)||(LA70_0>=USINT && LA70_0<=WCHAR)||(LA70_0>=BOOL && LA70_0<=BYTE)||(LA70_0>=CHAR && LA70_0<=DINT)||(LA70_0>=LINT && LA70_0<=SINT)||LA70_0==TIME||(LA70_0>=UINT && LA70_0<=WORD)||(LA70_0>=INT && LA70_0<=LDT)||LA70_0==TOD||LA70_0==DT) ) {
                alt70=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // InternalSTFunctionParser.g:4008:3: this_ID_0= RULE_ID
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
                    // InternalSTFunctionParser.g:4016:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
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
    // InternalSTFunctionParser.g:4030:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalSTFunctionParser.g:4030:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalSTFunctionParser.g:4031:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
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
    // InternalSTFunctionParser.g:4037:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4043:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalSTFunctionParser.g:4044:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalSTFunctionParser.g:4044:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt71=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt71=1;
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
                alt71=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt71=3;
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
                alt71=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
                {
                alt71=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 71, 0, input);

                throw nvae;
            }

            switch (alt71) {
                case 1 :
                    // InternalSTFunctionParser.g:4045:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalSTFunctionParser.g:4056:3: this_STAnyNumType_1= ruleSTAnyNumType
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
                    // InternalSTFunctionParser.g:4067:3: this_STAnyDurationType_2= ruleSTAnyDurationType
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
                    // InternalSTFunctionParser.g:4078:3: this_STAnyDateType_3= ruleSTAnyDateType
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
                    // InternalSTFunctionParser.g:4089:3: this_STAnyCharsType_4= ruleSTAnyCharsType
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
    // InternalSTFunctionParser.g:4103:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalSTFunctionParser.g:4103:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalSTFunctionParser.g:4104:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
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
    // InternalSTFunctionParser.g:4110:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4116:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalSTFunctionParser.g:4117:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalSTFunctionParser.g:4117:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt72=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt72=1;
                }
                break;
            case BYTE:
                {
                alt72=2;
                }
                break;
            case WORD:
                {
                alt72=3;
                }
                break;
            case DWORD:
                {
                alt72=4;
                }
                break;
            case LWORD:
                {
                alt72=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // InternalSTFunctionParser.g:4118:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4124:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4130:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:4136:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTFunctionParser.g:4142:3: kw= LWORD
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
    // InternalSTFunctionParser.g:4151:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalSTFunctionParser.g:4151:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalSTFunctionParser.g:4152:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
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
    // InternalSTFunctionParser.g:4158:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4164:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalSTFunctionParser.g:4165:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalSTFunctionParser.g:4165:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt73=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt73=1;
                }
                break;
            case INT:
                {
                alt73=2;
                }
                break;
            case DINT:
                {
                alt73=3;
                }
                break;
            case LINT:
                {
                alt73=4;
                }
                break;
            case USINT:
                {
                alt73=5;
                }
                break;
            case UINT:
                {
                alt73=6;
                }
                break;
            case UDINT:
                {
                alt73=7;
                }
                break;
            case ULINT:
                {
                alt73=8;
                }
                break;
            case REAL:
                {
                alt73=9;
                }
                break;
            case LREAL:
                {
                alt73=10;
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
                    // InternalSTFunctionParser.g:4166:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4172:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4178:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:4184:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTFunctionParser.g:4190:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTFunctionParser.g:4196:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTFunctionParser.g:4202:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTFunctionParser.g:4208:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTFunctionParser.g:4214:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSTFunctionParser.g:4220:3: kw= LREAL
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
    // InternalSTFunctionParser.g:4229:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalSTFunctionParser.g:4229:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalSTFunctionParser.g:4230:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
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
    // InternalSTFunctionParser.g:4236:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4242:2: ( (kw= TIME | kw= LTIME ) )
            // InternalSTFunctionParser.g:4243:2: (kw= TIME | kw= LTIME )
            {
            // InternalSTFunctionParser.g:4243:2: (kw= TIME | kw= LTIME )
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==TIME) ) {
                alt74=1;
            }
            else if ( (LA74_0==LTIME) ) {
                alt74=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;
            }
            switch (alt74) {
                case 1 :
                    // InternalSTFunctionParser.g:4244:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4250:3: kw= LTIME
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
    // InternalSTFunctionParser.g:4259:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalSTFunctionParser.g:4259:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalSTFunctionParser.g:4260:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
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
    // InternalSTFunctionParser.g:4266:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4272:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalSTFunctionParser.g:4273:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalSTFunctionParser.g:4273:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt75=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt75=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt75=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt75=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // InternalSTFunctionParser.g:4274:3: this_STDateType_0= ruleSTDateType
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
                    // InternalSTFunctionParser.g:4285:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
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
                    // InternalSTFunctionParser.g:4296:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
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
    // InternalSTFunctionParser.g:4310:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalSTFunctionParser.g:4310:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalSTFunctionParser.g:4311:2: iv_ruleSTDateType= ruleSTDateType EOF
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
    // InternalSTFunctionParser.g:4317:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4323:2: ( (kw= DATE | kw= LDATE ) )
            // InternalSTFunctionParser.g:4324:2: (kw= DATE | kw= LDATE )
            {
            // InternalSTFunctionParser.g:4324:2: (kw= DATE | kw= LDATE )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==DATE) ) {
                alt76=1;
            }
            else if ( (LA76_0==LDATE) ) {
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
                    // InternalSTFunctionParser.g:4325:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4331:3: kw= LDATE
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
    // InternalSTFunctionParser.g:4340:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalSTFunctionParser.g:4340:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalSTFunctionParser.g:4341:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
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
    // InternalSTFunctionParser.g:4347:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4353:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalSTFunctionParser.g:4354:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalSTFunctionParser.g:4354:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt77=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt77=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt77=2;
                }
                break;
            case TOD:
                {
                alt77=3;
                }
                break;
            case LTOD:
                {
                alt77=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }

            switch (alt77) {
                case 1 :
                    // InternalSTFunctionParser.g:4355:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4361:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4367:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:4373:3: kw= LTOD
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
    // InternalSTFunctionParser.g:4382:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalSTFunctionParser.g:4382:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalSTFunctionParser.g:4383:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
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
    // InternalSTFunctionParser.g:4389:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4395:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalSTFunctionParser.g:4396:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalSTFunctionParser.g:4396:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt78=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt78=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt78=2;
                }
                break;
            case DT:
                {
                alt78=3;
                }
                break;
            case LDT:
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
                    // InternalSTFunctionParser.g:4397:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4403:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4409:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:4415:3: kw= LDT
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
    // InternalSTFunctionParser.g:4424:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalSTFunctionParser.g:4424:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalSTFunctionParser.g:4425:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
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
    // InternalSTFunctionParser.g:4431:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4437:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalSTFunctionParser.g:4438:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalSTFunctionParser.g:4438:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt79=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt79=1;
                }
                break;
            case WSTRING:
                {
                alt79=2;
                }
                break;
            case CHAR:
                {
                alt79=3;
                }
                break;
            case WCHAR:
                {
                alt79=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // InternalSTFunctionParser.g:4439:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4445:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4451:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:4457:3: kw= WCHAR
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
    // InternalSTFunctionParser.g:4466:1: entryRuleBoolLiteral returns [String current=null] : iv_ruleBoolLiteral= ruleBoolLiteral EOF ;
    public final String entryRuleBoolLiteral() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoolLiteral = null;


        try {
            // InternalSTFunctionParser.g:4466:51: (iv_ruleBoolLiteral= ruleBoolLiteral EOF )
            // InternalSTFunctionParser.g:4467:2: iv_ruleBoolLiteral= ruleBoolLiteral EOF
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
    // InternalSTFunctionParser.g:4473:1: ruleBoolLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE ) ;
    public final AntlrDatatypeRuleToken ruleBoolLiteral() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4479:2: ( (kw= TRUE | kw= FALSE ) )
            // InternalSTFunctionParser.g:4480:2: (kw= TRUE | kw= FALSE )
            {
            // InternalSTFunctionParser.g:4480:2: (kw= TRUE | kw= FALSE )
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==TRUE) ) {
                alt80=1;
            }
            else if ( (LA80_0==FALSE) ) {
                alt80=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }
            switch (alt80) {
                case 1 :
                    // InternalSTFunctionParser.g:4481:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getBoolLiteralAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4487:3: kw= FALSE
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
    // InternalSTFunctionParser.g:4496:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;


        try {
            // InternalSTFunctionParser.g:4496:46: (iv_ruleNumber= ruleNumber EOF )
            // InternalSTFunctionParser.g:4497:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalSTFunctionParser.g:4503:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_EXT_INT_4=null;
        Token this_INT_5=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4509:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) )
            // InternalSTFunctionParser.g:4510:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            {
            // InternalSTFunctionParser.g:4510:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            // InternalSTFunctionParser.g:4511:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            {
            // InternalSTFunctionParser.g:4511:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt81=3;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==PlusSign) ) {
                alt81=1;
            }
            else if ( (LA81_0==HyphenMinus) ) {
                alt81=2;
            }
            switch (alt81) {
                case 1 :
                    // InternalSTFunctionParser.g:4512:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4518:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1());
              		
            }
            // InternalSTFunctionParser.g:4531:3: ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==FullStop) ) {
                int LA83_1 = input.LA(2);

                if ( (LA83_1==RULE_INT) ) {
                    int LA83_3 = input.LA(3);

                    if ( (synpred5_InternalSTFunctionParser()) ) {
                        alt83=1;
                    }
                }
                else if ( (LA83_1==RULE_EXT_INT) && (synpred5_InternalSTFunctionParser())) {
                    alt83=1;
                }
            }
            switch (alt83) {
                case 1 :
                    // InternalSTFunctionParser.g:4532:4: ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    {
                    // InternalSTFunctionParser.g:4532:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTFunctionParser.g:4533:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:4540:4: (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==RULE_EXT_INT) ) {
                        alt82=1;
                    }
                    else if ( (LA82_0==RULE_INT) ) {
                        alt82=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 82, 0, input);

                        throw nvae;
                    }
                    switch (alt82) {
                        case 1 :
                            // InternalSTFunctionParser.g:4541:5: this_EXT_INT_4= RULE_EXT_INT
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
                            // InternalSTFunctionParser.g:4549:5: this_INT_5= RULE_INT
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
    // InternalSTFunctionParser.g:4562:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalSTFunctionParser.g:4562:44: (iv_ruleDate= ruleDate EOF )
            // InternalSTFunctionParser.g:4563:2: iv_ruleDate= ruleDate EOF
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
    // InternalSTFunctionParser.g:4569:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4575:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTFunctionParser.g:4576:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTFunctionParser.g:4576:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTFunctionParser.g:4577:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
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
    // InternalSTFunctionParser.g:4612:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalSTFunctionParser.g:4612:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalSTFunctionParser.g:4613:2: iv_ruleDateAndTime= ruleDateAndTime EOF
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
    // InternalSTFunctionParser.g:4619:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) ;
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
            // InternalSTFunctionParser.g:4625:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) )
            // InternalSTFunctionParser.g:4626:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            {
            // InternalSTFunctionParser.g:4626:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            // InternalSTFunctionParser.g:4627:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_5());
              		
            }
            this_INT_6=(Token)match(input,RULE_INT,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_6);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_6, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_6());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_7());
              		
            }
            this_INT_8=(Token)match(input,RULE_INT,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_8);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_8, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_8());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_9());
              		
            }
            this_INT_10=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_10);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
              		
            }
            // InternalSTFunctionParser.g:4694:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==FullStop) ) {
                int LA84_1 = input.LA(2);

                if ( (LA84_1==RULE_INT) ) {
                    int LA84_3 = input.LA(3);

                    if ( (synpred6_InternalSTFunctionParser()) ) {
                        alt84=1;
                    }
                }
            }
            switch (alt84) {
                case 1 :
                    // InternalSTFunctionParser.g:4695:4: ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT
                    {
                    // InternalSTFunctionParser.g:4695:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTFunctionParser.g:4696:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_59); if (state.failed) return current;
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
    // InternalSTFunctionParser.g:4715:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalSTFunctionParser.g:4715:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalSTFunctionParser.g:4716:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalSTFunctionParser.g:4722:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4728:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) )
            // InternalSTFunctionParser.g:4729:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            {
            // InternalSTFunctionParser.g:4729:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            // InternalSTFunctionParser.g:4730:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTFunctionParser.g:4761:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==FullStop) ) {
                int LA85_1 = input.LA(2);

                if ( (LA85_1==RULE_INT) ) {
                    int LA85_3 = input.LA(3);

                    if ( (synpred7_InternalSTFunctionParser()) ) {
                        alt85=1;
                    }
                }
            }
            switch (alt85) {
                case 1 :
                    // InternalSTFunctionParser.g:4762:4: ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT
                    {
                    // InternalSTFunctionParser.g:4762:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTFunctionParser.g:4763:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_59); if (state.failed) return current;
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
    // InternalSTFunctionParser.g:4782:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;


        try {
            // InternalSTFunctionParser.g:4782:44: (iv_ruleTime= ruleTime EOF )
            // InternalSTFunctionParser.g:4783:2: iv_ruleTime= ruleTime EOF
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
    // InternalSTFunctionParser.g:4789:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Number_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4795:2: ( (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ )
            // InternalSTFunctionParser.g:4796:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            {
            // InternalSTFunctionParser.g:4796:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            int cnt88=0;
            loop88:
            do {
                int alt88=2;
                switch ( input.LA(1) ) {
                case PlusSign:
                    {
                    int LA88_2 = input.LA(2);

                    if ( (LA88_2==RULE_INT) ) {
                        int LA88_5 = input.LA(3);

                        if ( (LA88_5==FullStop) ) {
                            int LA88_6 = input.LA(4);

                            if ( (LA88_6==RULE_EXT_INT) ) {
                                int LA88_7 = input.LA(5);

                                if ( ((LA88_7>=MS && LA88_7<=NS)||LA88_7==US||(LA88_7>=D && LA88_7<=S)) ) {
                                    alt88=1;
                                }


                            }
                            else if ( (LA88_6==RULE_INT) ) {
                                int LA88_8 = input.LA(5);

                                if ( ((LA88_8>=MS && LA88_8<=NS)||LA88_8==US||(LA88_8>=D && LA88_8<=S)) ) {
                                    alt88=1;
                                }


                            }


                        }
                        else if ( ((LA88_5>=MS && LA88_5<=NS)||LA88_5==US||(LA88_5>=D && LA88_5<=S)) ) {
                            alt88=1;
                        }


                    }


                    }
                    break;
                case HyphenMinus:
                    {
                    int LA88_3 = input.LA(2);

                    if ( (LA88_3==RULE_INT) ) {
                        int LA88_5 = input.LA(3);

                        if ( (LA88_5==FullStop) ) {
                            int LA88_6 = input.LA(4);

                            if ( (LA88_6==RULE_EXT_INT) ) {
                                int LA88_7 = input.LA(5);

                                if ( ((LA88_7>=MS && LA88_7<=NS)||LA88_7==US||(LA88_7>=D && LA88_7<=S)) ) {
                                    alt88=1;
                                }


                            }
                            else if ( (LA88_6==RULE_INT) ) {
                                int LA88_8 = input.LA(5);

                                if ( ((LA88_8>=MS && LA88_8<=NS)||LA88_8==US||(LA88_8>=D && LA88_8<=S)) ) {
                                    alt88=1;
                                }


                            }


                        }
                        else if ( ((LA88_5>=MS && LA88_5<=NS)||LA88_5==US||(LA88_5>=D && LA88_5<=S)) ) {
                            alt88=1;
                        }


                    }


                    }
                    break;
                case RULE_INT:
                    {
                    alt88=1;
                    }
                    break;

                }

                switch (alt88) {
            	case 1 :
            	    // InternalSTFunctionParser.g:4797:3: this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )?
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getTimeAccess().getNumberParserRuleCall_0());
            	      		
            	    }
            	    pushFollow(FOLLOW_65);
            	    this_Number_0=ruleNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_Number_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }
            	    // InternalSTFunctionParser.g:4807:3: (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS )
            	    int alt86=7;
            	    switch ( input.LA(1) ) {
            	    case D:
            	        {
            	        alt86=1;
            	        }
            	        break;
            	    case H:
            	        {
            	        alt86=2;
            	        }
            	        break;
            	    case M:
            	        {
            	        alt86=3;
            	        }
            	        break;
            	    case S:
            	        {
            	        alt86=4;
            	        }
            	        break;
            	    case MS:
            	        {
            	        alt86=5;
            	        }
            	        break;
            	    case US:
            	        {
            	        alt86=6;
            	        }
            	        break;
            	    case NS:
            	        {
            	        alt86=7;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 86, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt86) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:4808:4: kw= D
            	            {
            	            kw=(Token)match(input,D,FOLLOW_66); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getDKeyword_1_0());
            	              			
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTFunctionParser.g:4814:4: kw= H
            	            {
            	            kw=(Token)match(input,H,FOLLOW_66); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getHKeyword_1_1());
            	              			
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTFunctionParser.g:4820:4: kw= M
            	            {
            	            kw=(Token)match(input,M,FOLLOW_66); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMKeyword_1_2());
            	              			
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTFunctionParser.g:4826:4: kw= S
            	            {
            	            kw=(Token)match(input,S,FOLLOW_66); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getSKeyword_1_3());
            	              			
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalSTFunctionParser.g:4832:4: kw= MS
            	            {
            	            kw=(Token)match(input,MS,FOLLOW_66); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMSKeyword_1_4());
            	              			
            	            }

            	            }
            	            break;
            	        case 6 :
            	            // InternalSTFunctionParser.g:4838:4: kw= US
            	            {
            	            kw=(Token)match(input,US,FOLLOW_66); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getUSKeyword_1_5());
            	              			
            	            }

            	            }
            	            break;
            	        case 7 :
            	            // InternalSTFunctionParser.g:4844:4: kw= NS
            	            {
            	            kw=(Token)match(input,NS,FOLLOW_66); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getNSKeyword_1_6());
            	              			
            	            }

            	            }
            	            break;

            	    }

            	    // InternalSTFunctionParser.g:4850:3: (kw= KW__ )?
            	    int alt87=2;
            	    int LA87_0 = input.LA(1);

            	    if ( (LA87_0==KW__) ) {
            	        alt87=1;
            	    }
            	    switch (alt87) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:4851:4: kw= KW__
            	            {
            	            kw=(Token)match(input,KW__,FOLLOW_67); if (state.failed) return current;
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
            	    if ( cnt88 >= 1 ) break loop88;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(88, input);
                        throw eee;
                }
                cnt88++;
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
    // InternalSTFunctionParser.g:4861:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4867:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTFunctionParser.g:4868:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTFunctionParser.g:4868:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTFunctionParser.g:4869:3: enumLiteral_0= FullStopFullStop
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
    // InternalSTFunctionParser.g:4878:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4884:2: ( (enumLiteral_0= OR ) )
            // InternalSTFunctionParser.g:4885:2: (enumLiteral_0= OR )
            {
            // InternalSTFunctionParser.g:4885:2: (enumLiteral_0= OR )
            // InternalSTFunctionParser.g:4886:3: enumLiteral_0= OR
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
    // InternalSTFunctionParser.g:4895:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4901:2: ( (enumLiteral_0= XOR ) )
            // InternalSTFunctionParser.g:4902:2: (enumLiteral_0= XOR )
            {
            // InternalSTFunctionParser.g:4902:2: (enumLiteral_0= XOR )
            // InternalSTFunctionParser.g:4903:3: enumLiteral_0= XOR
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
    // InternalSTFunctionParser.g:4912:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4918:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTFunctionParser.g:4919:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTFunctionParser.g:4919:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==AND) ) {
                alt89=1;
            }
            else if ( (LA89_0==Ampersand) ) {
                alt89=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }
            switch (alt89) {
                case 1 :
                    // InternalSTFunctionParser.g:4920:3: (enumLiteral_0= AND )
                    {
                    // InternalSTFunctionParser.g:4920:3: (enumLiteral_0= AND )
                    // InternalSTFunctionParser.g:4921:4: enumLiteral_0= AND
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
                    // InternalSTFunctionParser.g:4928:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTFunctionParser.g:4928:3: (enumLiteral_1= Ampersand )
                    // InternalSTFunctionParser.g:4929:4: enumLiteral_1= Ampersand
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
    // InternalSTFunctionParser.g:4939:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4945:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTFunctionParser.g:4946:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTFunctionParser.g:4946:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==EqualsSign) ) {
                alt90=1;
            }
            else if ( (LA90_0==LessThanSignGreaterThanSign) ) {
                alt90=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }
            switch (alt90) {
                case 1 :
                    // InternalSTFunctionParser.g:4947:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTFunctionParser.g:4947:3: (enumLiteral_0= EqualsSign )
                    // InternalSTFunctionParser.g:4948:4: enumLiteral_0= EqualsSign
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
                    // InternalSTFunctionParser.g:4955:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTFunctionParser.g:4955:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTFunctionParser.g:4956:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalSTFunctionParser.g:4966:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4972:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTFunctionParser.g:4973:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTFunctionParser.g:4973:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt91=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt91=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt91=2;
                }
                break;
            case GreaterThanSign:
                {
                alt91=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt91=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }

            switch (alt91) {
                case 1 :
                    // InternalSTFunctionParser.g:4974:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTFunctionParser.g:4974:3: (enumLiteral_0= LessThanSign )
                    // InternalSTFunctionParser.g:4975:4: enumLiteral_0= LessThanSign
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
                    // InternalSTFunctionParser.g:4982:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTFunctionParser.g:4982:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTFunctionParser.g:4983:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalSTFunctionParser.g:4990:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTFunctionParser.g:4990:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTFunctionParser.g:4991:4: enumLiteral_2= GreaterThanSign
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
                    // InternalSTFunctionParser.g:4998:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTFunctionParser.g:4998:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTFunctionParser.g:4999:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalSTFunctionParser.g:5009:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5015:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTFunctionParser.g:5016:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTFunctionParser.g:5016:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==PlusSign) ) {
                alt92=1;
            }
            else if ( (LA92_0==HyphenMinus) ) {
                alt92=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }
            switch (alt92) {
                case 1 :
                    // InternalSTFunctionParser.g:5017:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTFunctionParser.g:5017:3: (enumLiteral_0= PlusSign )
                    // InternalSTFunctionParser.g:5018:4: enumLiteral_0= PlusSign
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
                    // InternalSTFunctionParser.g:5025:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTFunctionParser.g:5025:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTFunctionParser.g:5026:4: enumLiteral_1= HyphenMinus
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
    // InternalSTFunctionParser.g:5036:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5042:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTFunctionParser.g:5043:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTFunctionParser.g:5043:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt93=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt93=1;
                }
                break;
            case Solidus:
                {
                alt93=2;
                }
                break;
            case MOD:
                {
                alt93=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }

            switch (alt93) {
                case 1 :
                    // InternalSTFunctionParser.g:5044:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTFunctionParser.g:5044:3: (enumLiteral_0= Asterisk )
                    // InternalSTFunctionParser.g:5045:4: enumLiteral_0= Asterisk
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
                    // InternalSTFunctionParser.g:5052:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTFunctionParser.g:5052:3: (enumLiteral_1= Solidus )
                    // InternalSTFunctionParser.g:5053:4: enumLiteral_1= Solidus
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
                    // InternalSTFunctionParser.g:5060:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTFunctionParser.g:5060:3: (enumLiteral_2= MOD )
                    // InternalSTFunctionParser.g:5061:4: enumLiteral_2= MOD
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
    // InternalSTFunctionParser.g:5071:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5077:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTFunctionParser.g:5078:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTFunctionParser.g:5078:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTFunctionParser.g:5079:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalSTFunctionParser.g:5088:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5094:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTFunctionParser.g:5095:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTFunctionParser.g:5095:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt94=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt94=1;
                }
                break;
            case PlusSign:
                {
                alt94=2;
                }
                break;
            case NOT:
                {
                alt94=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                throw nvae;
            }

            switch (alt94) {
                case 1 :
                    // InternalSTFunctionParser.g:5096:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTFunctionParser.g:5096:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTFunctionParser.g:5097:4: enumLiteral_0= HyphenMinus
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
                    // InternalSTFunctionParser.g:5104:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTFunctionParser.g:5104:3: (enumLiteral_1= PlusSign )
                    // InternalSTFunctionParser.g:5105:4: enumLiteral_1= PlusSign
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
                    // InternalSTFunctionParser.g:5112:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTFunctionParser.g:5112:3: (enumLiteral_2= NOT )
                    // InternalSTFunctionParser.g:5113:4: enumLiteral_2= NOT
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
    // InternalSTFunctionParser.g:5123:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5129:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalSTFunctionParser.g:5130:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalSTFunctionParser.g:5130:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt95=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt95=1;
                }
                break;
            case D_1:
                {
                alt95=2;
                }
                break;
            case W:
                {
                alt95=3;
                }
                break;
            case B:
                {
                alt95=4;
                }
                break;
            case X:
                {
                alt95=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }

            switch (alt95) {
                case 1 :
                    // InternalSTFunctionParser.g:5131:3: (enumLiteral_0= L )
                    {
                    // InternalSTFunctionParser.g:5131:3: (enumLiteral_0= L )
                    // InternalSTFunctionParser.g:5132:4: enumLiteral_0= L
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
                    // InternalSTFunctionParser.g:5139:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTFunctionParser.g:5139:3: (enumLiteral_1= D_1 )
                    // InternalSTFunctionParser.g:5140:4: enumLiteral_1= D_1
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
                    // InternalSTFunctionParser.g:5147:3: (enumLiteral_2= W )
                    {
                    // InternalSTFunctionParser.g:5147:3: (enumLiteral_2= W )
                    // InternalSTFunctionParser.g:5148:4: enumLiteral_2= W
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
                    // InternalSTFunctionParser.g:5155:3: (enumLiteral_3= B )
                    {
                    // InternalSTFunctionParser.g:5155:3: (enumLiteral_3= B )
                    // InternalSTFunctionParser.g:5156:4: enumLiteral_3= B
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
                    // InternalSTFunctionParser.g:5163:3: (enumLiteral_4= X )
                    {
                    // InternalSTFunctionParser.g:5163:3: (enumLiteral_4= X )
                    // InternalSTFunctionParser.g:5164:4: enumLiteral_4= X
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

    // $ANTLR start synpred1_InternalSTFunctionParser
    public final void synpred1_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:1176:6: ( ruleSTAssignmentStatement )
        // InternalSTFunctionParser.g:1176:7: ruleSTAssignmentStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTAssignmentStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_InternalSTFunctionParser

    // $ANTLR start synpred2_InternalSTFunctionParser
    public final void synpred2_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:1881:4: ( ( ruleSTStatement ) )
        // InternalSTFunctionParser.g:1881:5: ( ruleSTStatement )
        {
        // InternalSTFunctionParser.g:1881:5: ( ruleSTStatement )
        // InternalSTFunctionParser.g:1882:5: ruleSTStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSTFunctionParser

    // $ANTLR start synpred3_InternalSTFunctionParser
    public final void synpred3_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:2953:4: ( ruleSTAccessExpression )
        // InternalSTFunctionParser.g:2953:5: ruleSTAccessExpression
        {
        pushFollow(FOLLOW_2);
        ruleSTAccessExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalSTFunctionParser

    // $ANTLR start synpred4_InternalSTFunctionParser
    public final void synpred4_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:3248:5: ( ( LeftParenthesis ) )
        // InternalSTFunctionParser.g:3248:6: ( LeftParenthesis )
        {
        // InternalSTFunctionParser.g:3248:6: ( LeftParenthesis )
        // InternalSTFunctionParser.g:3249:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred4_InternalSTFunctionParser

    // $ANTLR start synpred5_InternalSTFunctionParser
    public final void synpred5_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:4533:5: ( FullStop )
        // InternalSTFunctionParser.g:4533:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_InternalSTFunctionParser

    // $ANTLR start synpred6_InternalSTFunctionParser
    public final void synpred6_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:4696:5: ( FullStop )
        // InternalSTFunctionParser.g:4696:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_InternalSTFunctionParser

    // $ANTLR start synpred7_InternalSTFunctionParser
    public final void synpred7_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:4763:5: ( FullStop )
        // InternalSTFunctionParser.g:4763:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_InternalSTFunctionParser

    // Delegated rules

    public final boolean synpred7_InternalSTFunctionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalSTFunctionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalSTFunctionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalSTFunctionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalSTFunctionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalSTFunctionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalSTFunctionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalSTFunctionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalSTFunctionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalSTFunctionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalSTFunctionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalSTFunctionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalSTFunctionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalSTFunctionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA27 dfa27 = new DFA27(this);
    protected DFA38 dfa38 = new DFA38(this);
    protected DFA53 dfa53 = new DFA53(this);
    protected DFA60 dfa60 = new DFA60(this);
    static final String dfa_1s = "\67\uffff";
    static final String dfa_2s = "\1\4\5\uffff\54\0\5\uffff";
    static final String dfa_3s = "\1\167\5\uffff\54\0\5\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\54\uffff\1\10\1\11\1\12\1\6\1\7";
    static final String dfa_5s = "\6\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\5\uffff}>";
    static final String[] dfa_6s = {
            "\1\52\1\51\1\uffff\1\46\1\45\6\uffff\1\63\5\uffff\1\56\1\uffff\1\5\1\62\1\55\1\uffff\1\13\1\uffff\1\30\1\36\1\26\1\42\1\14\1\23\1\24\1\uffff\1\21\1\60\1\4\1\10\1\11\1\2\1\57\1\35\1\17\1\uffff\1\64\1\20\1\50\1\25\1\15\1\uffff\1\41\1\27\1\22\1\12\1\uffff\1\3\1\16\1\54\2\uffff\1\47\21\uffff\1\53\1\1\1\40\1\44\10\uffff\1\6\2\uffff\1\31\1\uffff\1\32\7\uffff\1\37\3\uffff\1\43\4\uffff\1\34\1\33\1\uffff\1\7\1\61",
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

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "1129:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_6 = input.LA(1);

                         
                        int index27_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_7 = input.LA(1);

                         
                        int index27_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA27_8 = input.LA(1);

                         
                        int index27_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA27_9 = input.LA(1);

                         
                        int index27_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_9);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA27_10 = input.LA(1);

                         
                        int index27_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA27_11 = input.LA(1);

                         
                        int index27_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_11);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA27_12 = input.LA(1);

                         
                        int index27_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_12);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA27_13 = input.LA(1);

                         
                        int index27_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_13);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA27_14 = input.LA(1);

                         
                        int index27_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_14);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA27_15 = input.LA(1);

                         
                        int index27_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_15);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA27_16 = input.LA(1);

                         
                        int index27_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_16);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA27_17 = input.LA(1);

                         
                        int index27_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_17);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA27_18 = input.LA(1);

                         
                        int index27_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_18);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA27_19 = input.LA(1);

                         
                        int index27_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_19);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA27_20 = input.LA(1);

                         
                        int index27_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_20);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA27_21 = input.LA(1);

                         
                        int index27_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_21);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA27_22 = input.LA(1);

                         
                        int index27_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_22);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA27_23 = input.LA(1);

                         
                        int index27_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_23);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA27_24 = input.LA(1);

                         
                        int index27_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_24);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA27_25 = input.LA(1);

                         
                        int index27_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_25);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA27_26 = input.LA(1);

                         
                        int index27_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_26);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA27_27 = input.LA(1);

                         
                        int index27_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_27);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA27_28 = input.LA(1);

                         
                        int index27_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_28);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA27_29 = input.LA(1);

                         
                        int index27_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_29);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA27_30 = input.LA(1);

                         
                        int index27_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_30);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA27_31 = input.LA(1);

                         
                        int index27_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_31);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA27_32 = input.LA(1);

                         
                        int index27_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_32);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA27_33 = input.LA(1);

                         
                        int index27_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_33);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA27_34 = input.LA(1);

                         
                        int index27_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_34);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA27_35 = input.LA(1);

                         
                        int index27_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_35);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA27_36 = input.LA(1);

                         
                        int index27_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_36);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA27_37 = input.LA(1);

                         
                        int index27_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_37);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA27_38 = input.LA(1);

                         
                        int index27_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_38);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA27_39 = input.LA(1);

                         
                        int index27_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_39);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA27_40 = input.LA(1);

                         
                        int index27_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_40);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA27_41 = input.LA(1);

                         
                        int index27_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_41);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA27_42 = input.LA(1);

                         
                        int index27_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_42);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA27_43 = input.LA(1);

                         
                        int index27_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_43);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA27_44 = input.LA(1);

                         
                        int index27_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_44);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA27_45 = input.LA(1);

                         
                        int index27_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_45);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA27_46 = input.LA(1);

                         
                        int index27_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_46);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA27_47 = input.LA(1);

                         
                        int index27_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_47);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA27_48 = input.LA(1);

                         
                        int index27_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_48);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA27_49 = input.LA(1);

                         
                        int index27_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index27_49);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 27, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_7s = "\72\uffff";
    static final String dfa_8s = "\1\1\71\uffff";
    static final String dfa_9s = "\1\4\2\uffff\54\0\13\uffff";
    static final String dfa_10s = "\1\167\2\uffff\54\0\13\uffff";
    static final String dfa_11s = "\1\uffff\1\2\57\uffff\11\1";
    static final String dfa_12s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\13\uffff}>";
    static final String[] dfa_13s = {
            "\1\47\1\46\1\uffff\1\43\1\42\6\uffff\1\67\1\1\4\uffff\1\53\1\uffff\1\65\1\66\1\52\1\uffff\1\10\1\uffff\1\25\1\33\1\23\1\37\1\11\1\20\1\21\1\uffff\1\16\1\55\1\64\1\5\1\6\1\62\1\54\1\32\1\14\1\1\1\70\1\15\1\45\1\22\1\12\1\uffff\1\36\1\24\1\17\1\7\1\uffff\1\63\1\13\1\51\1\uffff\1\1\1\44\21\uffff\1\50\1\61\1\35\1\41\10\uffff\1\3\2\uffff\1\26\1\uffff\1\27\3\uffff\1\71\3\uffff\1\34\3\uffff\1\40\4\uffff\1\31\1\30\1\uffff\1\4\1\56",
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

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA38 extends DFA {

        public DFA38(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 38;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "()* loopback of 1880:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA38_0 = input.LA(1);

                         
                        int index38_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA38_0==EOF||LA38_0==END_CASE||LA38_0==ELSE||LA38_0==NOT) ) {s = 1;}

                        else if ( (LA38_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA38_0==RULE_ID) ) {s = 4;}

                        else if ( (LA38_0==BOOL) ) {s = 5;}

                        else if ( (LA38_0==BYTE) ) {s = 6;}

                        else if ( (LA38_0==WORD) ) {s = 7;}

                        else if ( (LA38_0==DWORD) ) {s = 8;}

                        else if ( (LA38_0==LWORD) ) {s = 9;}

                        else if ( (LA38_0==SINT) ) {s = 10;}

                        else if ( (LA38_0==INT) ) {s = 11;}

                        else if ( (LA38_0==DINT) ) {s = 12;}

                        else if ( (LA38_0==LINT) ) {s = 13;}

                        else if ( (LA38_0==USINT) ) {s = 14;}

                        else if ( (LA38_0==UINT) ) {s = 15;}

                        else if ( (LA38_0==UDINT) ) {s = 16;}

                        else if ( (LA38_0==ULINT) ) {s = 17;}

                        else if ( (LA38_0==REAL) ) {s = 18;}

                        else if ( (LA38_0==LREAL) ) {s = 19;}

                        else if ( (LA38_0==TRUE) ) {s = 20;}

                        else if ( (LA38_0==FALSE) ) {s = 21;}

                        else if ( (LA38_0==PlusSign) ) {s = 22;}

                        else if ( (LA38_0==HyphenMinus) ) {s = 23;}

                        else if ( (LA38_0==RULE_INT) ) {s = 24;}

                        else if ( (LA38_0==RULE_NON_DECIMAL) ) {s = 25;}

                        else if ( (LA38_0==DATE) ) {s = 26;}

                        else if ( (LA38_0==LDATE) ) {s = 27;}

                        else if ( (LA38_0==D) ) {s = 28;}

                        else if ( (LA38_0==LD) ) {s = 29;}

                        else if ( (LA38_0==TIME) ) {s = 30;}

                        else if ( (LA38_0==LTIME) ) {s = 31;}

                        else if ( (LA38_0==T) ) {s = 32;}

                        else if ( (LA38_0==LT) ) {s = 33;}

                        else if ( (LA38_0==TIME_OF_DAY) ) {s = 34;}

                        else if ( (LA38_0==LTIME_OF_DAY) ) {s = 35;}

                        else if ( (LA38_0==TOD) ) {s = 36;}

                        else if ( (LA38_0==LTOD) ) {s = 37;}

                        else if ( (LA38_0==DATE_AND_TIME) ) {s = 38;}

                        else if ( (LA38_0==LDATE_AND_TIME) ) {s = 39;}

                        else if ( (LA38_0==DT) ) {s = 40;}

                        else if ( (LA38_0==LDT) ) {s = 41;}

                        else if ( (LA38_0==STRING) ) {s = 42;}

                        else if ( (LA38_0==WSTRING) ) {s = 43;}

                        else if ( (LA38_0==CHAR) ) {s = 44;}

                        else if ( (LA38_0==WCHAR) ) {s = 45;}

                        else if ( (LA38_0==RULE_STRING) ) {s = 46;}

                        else if ( (LA38_0==IF) && (synpred2_InternalSTFunctionParser())) {s = 49;}

                        else if ( (LA38_0==CASE) && (synpred2_InternalSTFunctionParser())) {s = 50;}

                        else if ( (LA38_0==FOR) && (synpred2_InternalSTFunctionParser())) {s = 51;}

                        else if ( (LA38_0==WHILE) && (synpred2_InternalSTFunctionParser())) {s = 52;}

                        else if ( (LA38_0==REPEAT) && (synpred2_InternalSTFunctionParser())) {s = 53;}

                        else if ( (LA38_0==RETURN) && (synpred2_InternalSTFunctionParser())) {s = 54;}

                        else if ( (LA38_0==CONTINUE) && (synpred2_InternalSTFunctionParser())) {s = 55;}

                        else if ( (LA38_0==EXIT) && (synpred2_InternalSTFunctionParser())) {s = 56;}

                        else if ( (LA38_0==Semicolon) && (synpred2_InternalSTFunctionParser())) {s = 57;}

                         
                        input.seek(index38_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA38_3 = input.LA(1);

                         
                        int index38_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA38_4 = input.LA(1);

                         
                        int index38_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA38_5 = input.LA(1);

                         
                        int index38_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA38_6 = input.LA(1);

                         
                        int index38_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA38_7 = input.LA(1);

                         
                        int index38_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA38_8 = input.LA(1);

                         
                        int index38_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA38_9 = input.LA(1);

                         
                        int index38_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA38_10 = input.LA(1);

                         
                        int index38_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA38_11 = input.LA(1);

                         
                        int index38_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA38_12 = input.LA(1);

                         
                        int index38_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA38_13 = input.LA(1);

                         
                        int index38_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA38_14 = input.LA(1);

                         
                        int index38_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA38_15 = input.LA(1);

                         
                        int index38_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA38_16 = input.LA(1);

                         
                        int index38_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA38_17 = input.LA(1);

                         
                        int index38_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA38_18 = input.LA(1);

                         
                        int index38_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA38_19 = input.LA(1);

                         
                        int index38_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA38_20 = input.LA(1);

                         
                        int index38_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA38_21 = input.LA(1);

                         
                        int index38_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA38_22 = input.LA(1);

                         
                        int index38_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA38_23 = input.LA(1);

                         
                        int index38_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA38_24 = input.LA(1);

                         
                        int index38_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA38_25 = input.LA(1);

                         
                        int index38_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA38_26 = input.LA(1);

                         
                        int index38_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA38_27 = input.LA(1);

                         
                        int index38_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA38_28 = input.LA(1);

                         
                        int index38_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA38_29 = input.LA(1);

                         
                        int index38_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA38_30 = input.LA(1);

                         
                        int index38_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA38_31 = input.LA(1);

                         
                        int index38_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA38_32 = input.LA(1);

                         
                        int index38_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA38_33 = input.LA(1);

                         
                        int index38_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA38_34 = input.LA(1);

                         
                        int index38_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA38_35 = input.LA(1);

                         
                        int index38_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA38_36 = input.LA(1);

                         
                        int index38_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA38_37 = input.LA(1);

                         
                        int index38_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA38_38 = input.LA(1);

                         
                        int index38_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA38_39 = input.LA(1);

                         
                        int index38_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA38_40 = input.LA(1);

                         
                        int index38_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA38_41 = input.LA(1);

                         
                        int index38_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA38_42 = input.LA(1);

                         
                        int index38_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA38_43 = input.LA(1);

                         
                        int index38_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA38_44 = input.LA(1);

                         
                        int index38_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA38_45 = input.LA(1);

                         
                        int index38_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA38_46 = input.LA(1);

                         
                        int index38_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index38_46);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 38, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_14s = "\56\uffff";
    static final String dfa_15s = "\1\4\23\uffff\2\0\30\uffff";
    static final String dfa_16s = "\1\167\23\uffff\2\0\30\uffff";
    static final String dfa_17s = "\1\uffff\23\1\2\uffff\27\1\1\2";
    static final String dfa_18s = "\1\0\23\uffff\1\1\1\2\30\uffff}>";
    static final String[] dfa_19s = {
            "\1\45\1\44\1\uffff\1\41\1\40\14\uffff\1\51\3\uffff\1\50\1\uffff\1\6\1\uffff\1\23\1\31\1\21\1\35\1\7\1\16\1\17\1\uffff\1\14\1\53\1\uffff\1\3\1\4\1\uffff\1\52\1\30\1\12\2\uffff\1\13\1\43\1\20\1\10\1\uffff\1\34\1\22\1\15\1\5\2\uffff\1\11\1\47\1\uffff\1\55\1\42\21\uffff\1\46\1\uffff\1\33\1\37\10\uffff\1\1\2\uffff\1\24\1\uffff\1\25\7\uffff\1\32\3\uffff\1\36\4\uffff\1\27\1\26\1\uffff\1\2\1\54",
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

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA53 extends DFA {

        public DFA53(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 53;
            this.eot = dfa_14;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "2951:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA53_0 = input.LA(1);

                         
                        int index53_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_0==LeftParenthesis) && (synpred3_InternalSTFunctionParser())) {s = 1;}

                        else if ( (LA53_0==RULE_ID) && (synpred3_InternalSTFunctionParser())) {s = 2;}

                        else if ( (LA53_0==BOOL) && (synpred3_InternalSTFunctionParser())) {s = 3;}

                        else if ( (LA53_0==BYTE) && (synpred3_InternalSTFunctionParser())) {s = 4;}

                        else if ( (LA53_0==WORD) && (synpred3_InternalSTFunctionParser())) {s = 5;}

                        else if ( (LA53_0==DWORD) && (synpred3_InternalSTFunctionParser())) {s = 6;}

                        else if ( (LA53_0==LWORD) && (synpred3_InternalSTFunctionParser())) {s = 7;}

                        else if ( (LA53_0==SINT) && (synpred3_InternalSTFunctionParser())) {s = 8;}

                        else if ( (LA53_0==INT) && (synpred3_InternalSTFunctionParser())) {s = 9;}

                        else if ( (LA53_0==DINT) && (synpred3_InternalSTFunctionParser())) {s = 10;}

                        else if ( (LA53_0==LINT) && (synpred3_InternalSTFunctionParser())) {s = 11;}

                        else if ( (LA53_0==USINT) && (synpred3_InternalSTFunctionParser())) {s = 12;}

                        else if ( (LA53_0==UINT) && (synpred3_InternalSTFunctionParser())) {s = 13;}

                        else if ( (LA53_0==UDINT) && (synpred3_InternalSTFunctionParser())) {s = 14;}

                        else if ( (LA53_0==ULINT) && (synpred3_InternalSTFunctionParser())) {s = 15;}

                        else if ( (LA53_0==REAL) && (synpred3_InternalSTFunctionParser())) {s = 16;}

                        else if ( (LA53_0==LREAL) && (synpred3_InternalSTFunctionParser())) {s = 17;}

                        else if ( (LA53_0==TRUE) && (synpred3_InternalSTFunctionParser())) {s = 18;}

                        else if ( (LA53_0==FALSE) && (synpred3_InternalSTFunctionParser())) {s = 19;}

                        else if ( (LA53_0==PlusSign) ) {s = 20;}

                        else if ( (LA53_0==HyphenMinus) ) {s = 21;}

                        else if ( (LA53_0==RULE_INT) && (synpred3_InternalSTFunctionParser())) {s = 22;}

                        else if ( (LA53_0==RULE_NON_DECIMAL) && (synpred3_InternalSTFunctionParser())) {s = 23;}

                        else if ( (LA53_0==DATE) && (synpred3_InternalSTFunctionParser())) {s = 24;}

                        else if ( (LA53_0==LDATE) && (synpred3_InternalSTFunctionParser())) {s = 25;}

                        else if ( (LA53_0==D) && (synpred3_InternalSTFunctionParser())) {s = 26;}

                        else if ( (LA53_0==LD) && (synpred3_InternalSTFunctionParser())) {s = 27;}

                        else if ( (LA53_0==TIME) && (synpred3_InternalSTFunctionParser())) {s = 28;}

                        else if ( (LA53_0==LTIME) && (synpred3_InternalSTFunctionParser())) {s = 29;}

                        else if ( (LA53_0==T) && (synpred3_InternalSTFunctionParser())) {s = 30;}

                        else if ( (LA53_0==LT) && (synpred3_InternalSTFunctionParser())) {s = 31;}

                        else if ( (LA53_0==TIME_OF_DAY) && (synpred3_InternalSTFunctionParser())) {s = 32;}

                        else if ( (LA53_0==LTIME_OF_DAY) && (synpred3_InternalSTFunctionParser())) {s = 33;}

                        else if ( (LA53_0==TOD) && (synpred3_InternalSTFunctionParser())) {s = 34;}

                        else if ( (LA53_0==LTOD) && (synpred3_InternalSTFunctionParser())) {s = 35;}

                        else if ( (LA53_0==DATE_AND_TIME) && (synpred3_InternalSTFunctionParser())) {s = 36;}

                        else if ( (LA53_0==LDATE_AND_TIME) && (synpred3_InternalSTFunctionParser())) {s = 37;}

                        else if ( (LA53_0==DT) && (synpred3_InternalSTFunctionParser())) {s = 38;}

                        else if ( (LA53_0==LDT) && (synpred3_InternalSTFunctionParser())) {s = 39;}

                        else if ( (LA53_0==STRING) && (synpred3_InternalSTFunctionParser())) {s = 40;}

                        else if ( (LA53_0==WSTRING) && (synpred3_InternalSTFunctionParser())) {s = 41;}

                        else if ( (LA53_0==CHAR) && (synpred3_InternalSTFunctionParser())) {s = 42;}

                        else if ( (LA53_0==WCHAR) && (synpred3_InternalSTFunctionParser())) {s = 43;}

                        else if ( (LA53_0==RULE_STRING) && (synpred3_InternalSTFunctionParser())) {s = 44;}

                        else if ( (LA53_0==NOT) ) {s = 45;}

                         
                        input.seek(index53_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA53_20 = input.LA(1);

                         
                        int index53_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTFunctionParser()) ) {s = 44;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index53_20);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA53_21 = input.LA(1);

                         
                        int index53_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTFunctionParser()) ) {s = 44;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index53_21);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 53, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_20s = "\43\uffff";
    static final String dfa_21s = "\1\2\42\uffff";
    static final String dfa_22s = "\1\11\1\0\41\uffff";
    static final String dfa_23s = "\1\160\1\0\41\uffff";
    static final String dfa_24s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_25s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_26s = {
            "\1\2\52\uffff\1\2\4\uffff\1\2\3\uffff\1\2\3\uffff\1\2\5\uffff\5\2\1\uffff\1\2\1\uffff\2\2\6\uffff\3\2\2\uffff\1\2\1\1\14\2\5\uffff\2\2",
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

    class DFA60 extends DFA {

        public DFA60(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 60;
            this.eot = dfa_20;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "3246:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA60_1 = input.LA(1);

                         
                        int index60_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSTFunctionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index60_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 60, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x9DEFBFEFEBA4ADF0L,0x00D84465201E0001L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x99AF3B6FCA2001B0L,0x0040000000020000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x9DEFBFEFEBA4ADF0L,0x00D84445201E0001L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x9DEFBFEFEBA081F0L,0x00D84445201E0000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000104000L,0x0040000000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000100000L,0x0040000000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000002000004000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x99AF3B6FCE2001B0L,0x0040000000020000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0xD9EF3B6FEA2001B0L,0x00D84405201A0000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0001000200000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000804000000200L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000200L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0xD9EF3B6FEA2001B0L,0x00D8C405201A0000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000240000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x9DEFFFEFFBE081B0L,0x00D84445201E0000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000400010400000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x9DEFBFEFEBA081B2L,0x00D84445201E0000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0xD9EF7B6FEA2101B0L,0x00D84405201A0000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000002200000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000000000018000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x9DEFBFEFEBA881B0L,0x00D84445201E0000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x9DEFBFEFEBA091B0L,0x00D84445201E0000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x9DEFBFFFEBA081B0L,0x00D84445201E0000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0200000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000010000000800L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000028000002400L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000500000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x2000000000000002L,0x0000001080000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000800800000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x005000002000007CL});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0xD9EF3B6FEA2001B0L,0x00D84405601A0000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0010000020000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0040000020000000L,0x0018000500000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0010000500000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x00003C0004600000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000002L,0x0012000500000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0010000500000000L});

}