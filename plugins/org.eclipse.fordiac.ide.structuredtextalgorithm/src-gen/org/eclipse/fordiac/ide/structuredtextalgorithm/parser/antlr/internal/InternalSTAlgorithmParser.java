package org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.internal;

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
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalSTAlgorithmParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "END_ALGORITHM", "LTIME_OF_DAY", "TIME_OF_DAY", "END_METHOD", "END_REPEAT", "VAR_OUTPUT", "ALGORITHM", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "VAR_TEMP", "END_FOR", "END_VAR", "WSTRING", "END_IF", "METHOD", "REPEAT", "RETURN", "STRING", "ARRAY", "DWORD", "ELSIF", "FALSE", "LDATE", "LREAL", "LTIME", "LWORD", "UDINT", "ULINT", "UNTIL", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "LINT", "LTOD", "REAL", "SINT", "THEN", "TIME", "TRUE", "UINT", "WORD", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "TOD", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "MS", "NS", "OF", "OR", "TO", "US", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "H", "M", "S", "T", "LeftSquareBracket", "RightSquareBracket", "KW__", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=34;
    public static final int LessThanSignGreaterThanSign=76;
    public static final int EqualsSignGreaterThanSign=77;
    public static final int VAR=65;
    public static final int END_IF=22;
    public static final int ULINT=36;
    public static final int END_CASE=17;
    public static final int LessThanSign=104;
    public static final int LeftParenthesis=94;
    public static final int BYTE=42;
    public static final int ELSE=47;
    public static final int IF=83;
    public static final int LINT=49;
    public static final int GreaterThanSign=106;
    public static final int WORD=57;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=119;
    public static final int TOD=64;
    public static final int DINT=46;
    public static final int UDINT=35;
    public static final int CASE=43;
    public static final int GreaterThanSignEqualsSign=78;
    public static final int END_ALGORITHM=6;
    public static final int AT=79;
    public static final int PlusSign=97;
    public static final int RULE_INT=117;
    public static final int END_FOR=19;
    public static final int RULE_ML_COMMENT=121;
    public static final int THEN=53;
    public static final int XOR=66;
    public static final int LeftSquareBracket=112;
    public static final int EXIT=48;
    public static final int TIME_OF_DAY=8;
    public static final int B=67;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=24;
    public static final int D=107;
    public static final int H=108;
    public static final int CHAR=44;
    public static final int L=69;
    public static final int M=109;
    public static final int LTIME=33;
    public static final int Comma=98;
    public static final int HyphenMinus=99;
    public static final int S=110;
    public static final int T=111;
    public static final int W=70;
    public static final int BY=80;
    public static final int X=71;
    public static final int ELSIF=29;
    public static final int END_REPEAT=10;
    public static final int LessThanSignEqualsSign=75;
    public static final int Solidus=101;
    public static final int VAR_INPUT=14;
    public static final int FullStop=100;
    public static final int VAR_TEMP=18;
    public static final int CONSTANT=15;
    public static final int KW__=114;
    public static final int CONTINUE=16;
    public static final int Semicolon=103;
    public static final int LD=84;
    public static final int VAR_OUTPUT=11;
    public static final int STRING=26;
    public static final int RULE_HEX_DIGIT=115;
    public static final int TO=90;
    public static final int UINT=56;
    public static final int LTOD=50;
    public static final int ARRAY=27;
    public static final int LT=85;
    public static final int DO=81;
    public static final int WSTRING=21;
    public static final int DT=82;
    public static final int END_VAR=20;
    public static final int FullStopFullStop=73;
    public static final int Ampersand=93;
    public static final int US=91;
    public static final int RightSquareBracket=113;
    public static final int USINT=38;
    public static final int MS=86;
    public static final int DWORD=28;
    public static final int FOR=59;
    public static final int RightParenthesis=95;
    public static final int TRUE=55;
    public static final int ColonEqualsSign=74;
    public static final int END_WHILE=13;
    public static final int DATE=45;
    public static final int NOT=63;
    public static final int LDATE=31;
    public static final int AND=58;
    public static final int NumberSign=92;
    public static final int REAL=51;
    public static final int AsteriskAsterisk=72;
    public static final int METHOD=23;
    public static final int SINT=52;
    public static final int LTIME_OF_DAY=7;
    public static final int LREAL=32;
    public static final int WCHAR=39;
    public static final int NS=87;
    public static final int ALGORITHM=12;
    public static final int RULE_STRING=120;
    public static final int INT=60;
    public static final int RULE_SL_COMMENT=122;
    public static final int RETURN=25;
    public static final int EqualsSign=105;
    public static final int OF=88;
    public static final int END_METHOD=9;
    public static final int Colon=102;
    public static final int EOF=-1;
    public static final int LDT=61;
    public static final int Asterisk=96;
    public static final int MOD=62;
    public static final int OR=89;
    public static final int RULE_WS=123;
    public static final int RULE_EXT_INT=118;
    public static final int TIME=54;
    public static final int RULE_ANY_OTHER=124;
    public static final int UNTIL=37;
    public static final int BOOL=41;
    public static final int D_1=68;
    public static final int FALSE=30;
    public static final int WHILE=40;
    public static final int RULE_NON_DECIMAL=116;

    // delegates
    // delegators


        public InternalSTAlgorithmParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSTAlgorithmParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSTAlgorithmParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSTAlgorithmParser.g"; }



     	private STAlgorithmGrammarAccess grammarAccess;

        public InternalSTAlgorithmParser(TokenStream input, STAlgorithmGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "STAlgorithmSource";
       	}

       	@Override
       	protected STAlgorithmGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleSTAlgorithmSource"
    // InternalSTAlgorithmParser.g:58:1: entryRuleSTAlgorithmSource returns [EObject current=null] : iv_ruleSTAlgorithmSource= ruleSTAlgorithmSource EOF ;
    public final EObject entryRuleSTAlgorithmSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAlgorithmSource = null;


        try {
            // InternalSTAlgorithmParser.g:58:58: (iv_ruleSTAlgorithmSource= ruleSTAlgorithmSource EOF )
            // InternalSTAlgorithmParser.g:59:2: iv_ruleSTAlgorithmSource= ruleSTAlgorithmSource EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAlgorithmSourceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAlgorithmSource=ruleSTAlgorithmSource();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAlgorithmSource; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAlgorithmSource"


    // $ANTLR start "ruleSTAlgorithmSource"
    // InternalSTAlgorithmParser.g:65:1: ruleSTAlgorithmSource returns [EObject current=null] : ( () ( (lv_elements_1_0= ruleSTAlgorithmSourceElement ) )* ) ;
    public final EObject ruleSTAlgorithmSource() throws RecognitionException {
        EObject current = null;

        EObject lv_elements_1_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:71:2: ( ( () ( (lv_elements_1_0= ruleSTAlgorithmSourceElement ) )* ) )
            // InternalSTAlgorithmParser.g:72:2: ( () ( (lv_elements_1_0= ruleSTAlgorithmSourceElement ) )* )
            {
            // InternalSTAlgorithmParser.g:72:2: ( () ( (lv_elements_1_0= ruleSTAlgorithmSourceElement ) )* )
            // InternalSTAlgorithmParser.g:73:3: () ( (lv_elements_1_0= ruleSTAlgorithmSourceElement ) )*
            {
            // InternalSTAlgorithmParser.g:73:3: ()
            // InternalSTAlgorithmParser.g:74:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTAlgorithmSourceAccess().getSTAlgorithmSourceAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:80:3: ( (lv_elements_1_0= ruleSTAlgorithmSourceElement ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ALGORITHM||LA1_0==METHOD) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:81:4: (lv_elements_1_0= ruleSTAlgorithmSourceElement )
            	    {
            	    // InternalSTAlgorithmParser.g:81:4: (lv_elements_1_0= ruleSTAlgorithmSourceElement )
            	    // InternalSTAlgorithmParser.g:82:5: lv_elements_1_0= ruleSTAlgorithmSourceElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTAlgorithmSourceAccess().getElementsSTAlgorithmSourceElementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_elements_1_0=ruleSTAlgorithmSourceElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTAlgorithmSourceRule());
            	      					}
            	      					add(
            	      						current,
            	      						"elements",
            	      						lv_elements_1_0,
            	      						"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm.STAlgorithmSourceElement");
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
    // $ANTLR end "ruleSTAlgorithmSource"


    // $ANTLR start "entryRuleSTAlgorithmSourceElement"
    // InternalSTAlgorithmParser.g:103:1: entryRuleSTAlgorithmSourceElement returns [EObject current=null] : iv_ruleSTAlgorithmSourceElement= ruleSTAlgorithmSourceElement EOF ;
    public final EObject entryRuleSTAlgorithmSourceElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAlgorithmSourceElement = null;


        try {
            // InternalSTAlgorithmParser.g:103:65: (iv_ruleSTAlgorithmSourceElement= ruleSTAlgorithmSourceElement EOF )
            // InternalSTAlgorithmParser.g:104:2: iv_ruleSTAlgorithmSourceElement= ruleSTAlgorithmSourceElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAlgorithmSourceElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAlgorithmSourceElement=ruleSTAlgorithmSourceElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAlgorithmSourceElement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAlgorithmSourceElement"


    // $ANTLR start "ruleSTAlgorithmSourceElement"
    // InternalSTAlgorithmParser.g:110:1: ruleSTAlgorithmSourceElement returns [EObject current=null] : (this_STAlgorithm_0= ruleSTAlgorithm | this_STMethod_1= ruleSTMethod ) ;
    public final EObject ruleSTAlgorithmSourceElement() throws RecognitionException {
        EObject current = null;

        EObject this_STAlgorithm_0 = null;

        EObject this_STMethod_1 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:116:2: ( (this_STAlgorithm_0= ruleSTAlgorithm | this_STMethod_1= ruleSTMethod ) )
            // InternalSTAlgorithmParser.g:117:2: (this_STAlgorithm_0= ruleSTAlgorithm | this_STMethod_1= ruleSTMethod )
            {
            // InternalSTAlgorithmParser.g:117:2: (this_STAlgorithm_0= ruleSTAlgorithm | this_STMethod_1= ruleSTMethod )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ALGORITHM) ) {
                alt2=1;
            }
            else if ( (LA2_0==METHOD) ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalSTAlgorithmParser.g:118:3: this_STAlgorithm_0= ruleSTAlgorithm
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAlgorithmSourceElementAccess().getSTAlgorithmParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STAlgorithm_0=ruleSTAlgorithm();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STAlgorithm_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:127:3: this_STMethod_1= ruleSTMethod
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAlgorithmSourceElementAccess().getSTMethodParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STMethod_1=ruleSTMethod();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STMethod_1;
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
    // $ANTLR end "ruleSTAlgorithmSourceElement"


    // $ANTLR start "entryRuleSTAlgorithm"
    // InternalSTAlgorithmParser.g:139:1: entryRuleSTAlgorithm returns [EObject current=null] : iv_ruleSTAlgorithm= ruleSTAlgorithm EOF ;
    public final EObject entryRuleSTAlgorithm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAlgorithm = null;


        try {
            // InternalSTAlgorithmParser.g:139:52: (iv_ruleSTAlgorithm= ruleSTAlgorithm EOF )
            // InternalSTAlgorithmParser.g:140:2: iv_ruleSTAlgorithm= ruleSTAlgorithm EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAlgorithmRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAlgorithm=ruleSTAlgorithm();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAlgorithm; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAlgorithm"


    // $ANTLR start "ruleSTAlgorithm"
    // InternalSTAlgorithmParser.g:146:1: ruleSTAlgorithm returns [EObject current=null] : (otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM ) ;
    public final EObject ruleSTAlgorithm() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:152:2: ( (otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM ) )
            // InternalSTAlgorithmParser.g:153:2: (otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM )
            {
            // InternalSTAlgorithmParser.g:153:2: (otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM )
            // InternalSTAlgorithmParser.g:154:3: otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM
            {
            otherlv_0=(Token)match(input,ALGORITHM,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTAlgorithmAccess().getALGORITHMKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:158:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTAlgorithmParser.g:159:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:159:4: (lv_name_1_0= RULE_ID )
            // InternalSTAlgorithmParser.g:160:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getSTAlgorithmAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTAlgorithmRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
              				
            }

            }


            }

            // InternalSTAlgorithmParser.g:176:3: ( (lv_body_2_0= ruleSTAlgorithmBody ) )
            // InternalSTAlgorithmParser.g:177:4: (lv_body_2_0= ruleSTAlgorithmBody )
            {
            // InternalSTAlgorithmParser.g:177:4: (lv_body_2_0= ruleSTAlgorithmBody )
            // InternalSTAlgorithmParser.g:178:5: lv_body_2_0= ruleSTAlgorithmBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAlgorithmAccess().getBodySTAlgorithmBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_6);
            lv_body_2_0=ruleSTAlgorithmBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTAlgorithmRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_2_0,
              						"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm.STAlgorithmBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_3=(Token)match(input,END_ALGORITHM,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTAlgorithmAccess().getEND_ALGORITHMKeyword_3());
              		
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
    // $ANTLR end "ruleSTAlgorithm"


    // $ANTLR start "entryRuleSTAlgorithmBody"
    // InternalSTAlgorithmParser.g:203:1: entryRuleSTAlgorithmBody returns [EObject current=null] : iv_ruleSTAlgorithmBody= ruleSTAlgorithmBody EOF ;
    public final EObject entryRuleSTAlgorithmBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAlgorithmBody = null;


        try {
            // InternalSTAlgorithmParser.g:203:56: (iv_ruleSTAlgorithmBody= ruleSTAlgorithmBody EOF )
            // InternalSTAlgorithmParser.g:204:2: iv_ruleSTAlgorithmBody= ruleSTAlgorithmBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAlgorithmBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAlgorithmBody=ruleSTAlgorithmBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAlgorithmBody; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAlgorithmBody"


    // $ANTLR start "ruleSTAlgorithmBody"
    // InternalSTAlgorithmParser.g:210:1: ruleSTAlgorithmBody returns [EObject current=null] : ( () ( (lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTAlgorithmBody() throws RecognitionException {
        EObject current = null;

        EObject lv_varTempDeclarations_1_0 = null;

        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:216:2: ( ( () ( (lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:217:2: ( () ( (lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:217:2: ( () ( (lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:218:3: () ( (lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:218:3: ()
            // InternalSTAlgorithmParser.g:219:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTAlgorithmBodyAccess().getSTAlgorithmBodyAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:225:3: ( (lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==VAR_TEMP) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:226:4: (lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock )
            	    {
            	    // InternalSTAlgorithmParser.g:226:4: (lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock )
            	    // InternalSTAlgorithmParser.g:227:5: lv_varTempDeclarations_1_0= ruleSTVarTempDeclarationBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTAlgorithmBodyAccess().getVarTempDeclarationsSTVarTempDeclarationBlockParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_7);
            	    lv_varTempDeclarations_1_0=ruleSTVarTempDeclarationBlock();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTAlgorithmBodyRule());
            	      					}
            	      					add(
            	      						current,
            	      						"varTempDeclarations",
            	      						lv_varTempDeclarations_1_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarTempDeclarationBlock");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:244:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=LDATE_AND_TIME && LA4_0<=DATE_AND_TIME)||(LA4_0>=LTIME_OF_DAY && LA4_0<=TIME_OF_DAY)||LA4_0==CONTINUE||LA4_0==WSTRING||(LA4_0>=REPEAT && LA4_0<=STRING)||LA4_0==DWORD||(LA4_0>=FALSE && LA4_0<=ULINT)||(LA4_0>=USINT && LA4_0<=DINT)||(LA4_0>=EXIT && LA4_0<=SINT)||(LA4_0>=TIME && LA4_0<=WORD)||(LA4_0>=FOR && LA4_0<=LDT)||LA4_0==TOD||(LA4_0>=DT && LA4_0<=LT)||LA4_0==LeftParenthesis||LA4_0==PlusSign||LA4_0==HyphenMinus||LA4_0==Semicolon||LA4_0==D||LA4_0==T||(LA4_0>=RULE_NON_DECIMAL && LA4_0<=RULE_INT)||(LA4_0>=RULE_ID && LA4_0<=RULE_STRING)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:245:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:245:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:246:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTAlgorithmBodyAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
            	    lv_statements_2_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTAlgorithmBodyRule());
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
            	    break loop4;
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
    // $ANTLR end "ruleSTAlgorithmBody"


    // $ANTLR start "entryRuleSTMethod"
    // InternalSTAlgorithmParser.g:267:1: entryRuleSTMethod returns [EObject current=null] : iv_ruleSTMethod= ruleSTMethod EOF ;
    public final EObject entryRuleSTMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMethod = null;


        try {
            // InternalSTAlgorithmParser.g:267:49: (iv_ruleSTMethod= ruleSTMethod EOF )
            // InternalSTAlgorithmParser.g:268:2: iv_ruleSTMethod= ruleSTMethod EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTMethodRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTMethod=ruleSTMethod();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTMethod; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTMethod"


    // $ANTLR start "ruleSTMethod"
    // InternalSTAlgorithmParser.g:274:1: ruleSTMethod returns [EObject current=null] : (otherlv_0= METHOD ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( ( ruleSTAnyType ) ) )? ( (lv_body_4_0= ruleSTMethodBody ) ) otherlv_5= END_METHOD ) ;
    public final EObject ruleSTMethod() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:280:2: ( (otherlv_0= METHOD ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( ( ruleSTAnyType ) ) )? ( (lv_body_4_0= ruleSTMethodBody ) ) otherlv_5= END_METHOD ) )
            // InternalSTAlgorithmParser.g:281:2: (otherlv_0= METHOD ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( ( ruleSTAnyType ) ) )? ( (lv_body_4_0= ruleSTMethodBody ) ) otherlv_5= END_METHOD )
            {
            // InternalSTAlgorithmParser.g:281:2: (otherlv_0= METHOD ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( ( ruleSTAnyType ) ) )? ( (lv_body_4_0= ruleSTMethodBody ) ) otherlv_5= END_METHOD )
            // InternalSTAlgorithmParser.g:282:3: otherlv_0= METHOD ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( ( ruleSTAnyType ) ) )? ( (lv_body_4_0= ruleSTMethodBody ) ) otherlv_5= END_METHOD
            {
            otherlv_0=(Token)match(input,METHOD,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTMethodAccess().getMETHODKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:286:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTAlgorithmParser.g:287:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:287:4: (lv_name_1_0= RULE_ID )
            // InternalSTAlgorithmParser.g:288:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getSTMethodAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTMethodRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
              				
            }

            }


            }

            // InternalSTAlgorithmParser.g:304:3: (otherlv_2= Colon ( ( ruleSTAnyType ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==Colon) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalSTAlgorithmParser.g:305:4: otherlv_2= Colon ( ( ruleSTAnyType ) )
                    {
                    otherlv_2=(Token)match(input,Colon,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTMethodAccess().getColonKeyword_2_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:309:4: ( ( ruleSTAnyType ) )
                    // InternalSTAlgorithmParser.g:310:5: ( ruleSTAnyType )
                    {
                    // InternalSTAlgorithmParser.g:310:5: ( ruleSTAnyType )
                    // InternalSTAlgorithmParser.g:311:6: ruleSTAnyType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTMethodRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTMethodAccess().getReturnTypeDataTypeCrossReference_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_11);
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

            // InternalSTAlgorithmParser.g:326:3: ( (lv_body_4_0= ruleSTMethodBody ) )
            // InternalSTAlgorithmParser.g:327:4: (lv_body_4_0= ruleSTMethodBody )
            {
            // InternalSTAlgorithmParser.g:327:4: (lv_body_4_0= ruleSTMethodBody )
            // InternalSTAlgorithmParser.g:328:5: lv_body_4_0= ruleSTMethodBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTMethodAccess().getBodySTMethodBodyParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_body_4_0=ruleSTMethodBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTMethodRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_4_0,
              						"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm.STMethodBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_5=(Token)match(input,END_METHOD,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getSTMethodAccess().getEND_METHODKeyword_4());
              		
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
    // $ANTLR end "ruleSTMethod"


    // $ANTLR start "entryRuleSTMethodBody"
    // InternalSTAlgorithmParser.g:353:1: entryRuleSTMethodBody returns [EObject current=null] : iv_ruleSTMethodBody= ruleSTMethodBody EOF ;
    public final EObject entryRuleSTMethodBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMethodBody = null;


        try {
            // InternalSTAlgorithmParser.g:353:53: (iv_ruleSTMethodBody= ruleSTMethodBody EOF )
            // InternalSTAlgorithmParser.g:354:2: iv_ruleSTMethodBody= ruleSTMethodBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTMethodBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTMethodBody=ruleSTMethodBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTMethodBody; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTMethodBody"


    // $ANTLR start "ruleSTMethodBody"
    // InternalSTAlgorithmParser.g:360:1: ruleSTMethodBody returns [EObject current=null] : ( () ( ( (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock ) ) )* ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTMethodBody() throws RecognitionException {
        EObject current = null;

        EObject lv_varDeclarations_1_1 = null;

        EObject lv_varDeclarations_1_2 = null;

        EObject lv_varDeclarations_1_3 = null;

        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:366:2: ( ( () ( ( (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock ) ) )* ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:367:2: ( () ( ( (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock ) ) )* ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:367:2: ( () ( ( (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock ) ) )* ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:368:3: () ( ( (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock ) ) )* ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:368:3: ()
            // InternalSTAlgorithmParser.g:369:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMethodBodyAccess().getSTMethodBodyAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:375:3: ( ( (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==VAR_OUTPUT||LA7_0==VAR_INPUT||LA7_0==VAR_TEMP) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:376:4: ( (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock ) )
            	    {
            	    // InternalSTAlgorithmParser.g:376:4: ( (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock ) )
            	    // InternalSTAlgorithmParser.g:377:5: (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock )
            	    {
            	    // InternalSTAlgorithmParser.g:377:5: (lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock | lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock | lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock )
            	    int alt6=3;
            	    switch ( input.LA(1) ) {
            	    case VAR_TEMP:
            	        {
            	        alt6=1;
            	        }
            	        break;
            	    case VAR_INPUT:
            	        {
            	        alt6=2;
            	        }
            	        break;
            	    case VAR_OUTPUT:
            	        {
            	        alt6=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 6, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt6) {
            	        case 1 :
            	            // InternalSTAlgorithmParser.g:378:6: lv_varDeclarations_1_1= ruleSTVarTempDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTMethodBodyAccess().getVarDeclarationsSTVarTempDeclarationBlockParserRuleCall_1_0_0());
            	              					
            	            }
            	            pushFollow(FOLLOW_13);
            	            lv_varDeclarations_1_1=ruleSTVarTempDeclarationBlock();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
            	              						}
            	              						add(
            	              							current,
            	              							"varDeclarations",
            	              							lv_varDeclarations_1_1,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarTempDeclarationBlock");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTAlgorithmParser.g:394:6: lv_varDeclarations_1_2= ruleSTVarInputDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTMethodBodyAccess().getVarDeclarationsSTVarInputDeclarationBlockParserRuleCall_1_0_1());
            	              					
            	            }
            	            pushFollow(FOLLOW_13);
            	            lv_varDeclarations_1_2=ruleSTVarInputDeclarationBlock();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
            	              						}
            	              						add(
            	              							current,
            	              							"varDeclarations",
            	              							lv_varDeclarations_1_2,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarInputDeclarationBlock");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTAlgorithmParser.g:410:6: lv_varDeclarations_1_3= ruleSTVarOutputDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTMethodBodyAccess().getVarDeclarationsSTVarOutputDeclarationBlockParserRuleCall_1_0_2());
            	              					
            	            }
            	            pushFollow(FOLLOW_13);
            	            lv_varDeclarations_1_3=ruleSTVarOutputDeclarationBlock();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
            	              						}
            	              						add(
            	              							current,
            	              							"varDeclarations",
            	              							lv_varDeclarations_1_3,
            	              							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarOutputDeclarationBlock");
            	              						afterParserOrEnumRuleCall();
            	              					
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:428:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>=LDATE_AND_TIME && LA8_0<=DATE_AND_TIME)||(LA8_0>=LTIME_OF_DAY && LA8_0<=TIME_OF_DAY)||LA8_0==CONTINUE||LA8_0==WSTRING||(LA8_0>=REPEAT && LA8_0<=STRING)||LA8_0==DWORD||(LA8_0>=FALSE && LA8_0<=ULINT)||(LA8_0>=USINT && LA8_0<=DINT)||(LA8_0>=EXIT && LA8_0<=SINT)||(LA8_0>=TIME && LA8_0<=WORD)||(LA8_0>=FOR && LA8_0<=LDT)||LA8_0==TOD||(LA8_0>=DT && LA8_0<=LT)||LA8_0==LeftParenthesis||LA8_0==PlusSign||LA8_0==HyphenMinus||LA8_0==Semicolon||LA8_0==D||LA8_0==T||(LA8_0>=RULE_NON_DECIMAL && LA8_0<=RULE_INT)||(LA8_0>=RULE_ID && LA8_0<=RULE_STRING)) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:429:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:429:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:430:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTMethodBodyAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
            	    lv_statements_2_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
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
            	    break loop8;
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
    // $ANTLR end "ruleSTMethodBody"


    // $ANTLR start "entryRuleSTVarTempDeclarationBlock"
    // InternalSTAlgorithmParser.g:451:1: entryRuleSTVarTempDeclarationBlock returns [EObject current=null] : iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF ;
    public final EObject entryRuleSTVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarTempDeclarationBlock = null;


        try {
            // InternalSTAlgorithmParser.g:451:66: (iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF )
            // InternalSTAlgorithmParser.g:452:2: iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF
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
    // InternalSTAlgorithmParser.g:458:1: ruleSTVarTempDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:464:2: ( ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTAlgorithmParser.g:465:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTAlgorithmParser.g:465:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTAlgorithmParser.g:466:3: () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTAlgorithmParser.g:466:3: ()
            // InternalSTAlgorithmParser.g:467:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarTempDeclarationBlockAccess().getSTVarTempDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_TEMP,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarTempDeclarationBlockAccess().getVAR_TEMPKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:477:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==CONSTANT) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSTAlgorithmParser.g:478:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTAlgorithmParser.g:478:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTAlgorithmParser.g:479:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_15); if (state.failed) return current;
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

            // InternalSTAlgorithmParser.g:491:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:492:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTAlgorithmParser.g:492:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTAlgorithmParser.g:493:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarTempDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
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
            	    break loop10;
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
    // InternalSTAlgorithmParser.g:518:1: entryRuleSTVarInputDeclarationBlock returns [EObject current=null] : iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF ;
    public final EObject entryRuleSTVarInputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarInputDeclarationBlock = null;


        try {
            // InternalSTAlgorithmParser.g:518:67: (iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF )
            // InternalSTAlgorithmParser.g:519:2: iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF
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
    // InternalSTAlgorithmParser.g:525:1: ruleSTVarInputDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarInputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:531:2: ( ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTAlgorithmParser.g:532:2: ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTAlgorithmParser.g:532:2: ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTAlgorithmParser.g:533:3: () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTAlgorithmParser.g:533:3: ()
            // InternalSTAlgorithmParser.g:534:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarInputDeclarationBlockAccess().getSTVarInputDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_INPUT,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarInputDeclarationBlockAccess().getVAR_INPUTKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:544:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==CONSTANT) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSTAlgorithmParser.g:545:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTAlgorithmParser.g:545:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTAlgorithmParser.g:546:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_15); if (state.failed) return current;
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

            // InternalSTAlgorithmParser.g:558:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_ID) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:559:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTAlgorithmParser.g:559:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTAlgorithmParser.g:560:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarInputDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
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
            	    break loop12;
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
    // InternalSTAlgorithmParser.g:585:1: entryRuleSTVarOutputDeclarationBlock returns [EObject current=null] : iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF ;
    public final EObject entryRuleSTVarOutputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarOutputDeclarationBlock = null;


        try {
            // InternalSTAlgorithmParser.g:585:68: (iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF )
            // InternalSTAlgorithmParser.g:586:2: iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF
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
    // InternalSTAlgorithmParser.g:592:1: ruleSTVarOutputDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarOutputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:598:2: ( ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTAlgorithmParser.g:599:2: ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTAlgorithmParser.g:599:2: ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTAlgorithmParser.g:600:3: () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTAlgorithmParser.g:600:3: ()
            // InternalSTAlgorithmParser.g:601:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarOutputDeclarationBlockAccess().getSTVarOutputDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_OUTPUT,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarOutputDeclarationBlockAccess().getVAR_OUTPUTKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:611:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==CONSTANT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalSTAlgorithmParser.g:612:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTAlgorithmParser.g:612:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTAlgorithmParser.g:613:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_15); if (state.failed) return current;
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

            // InternalSTAlgorithmParser.g:625:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_ID) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:626:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTAlgorithmParser.g:626:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTAlgorithmParser.g:627:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarOutputDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
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
            	    break loop14;
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


    // $ANTLR start "entryRuleSTVarDeclaration"
    // InternalSTAlgorithmParser.g:652:1: entryRuleSTVarDeclaration returns [EObject current=null] : iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF ;
    public final EObject entryRuleSTVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclaration = null;


        try {
            // InternalSTAlgorithmParser.g:652:57: (iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF )
            // InternalSTAlgorithmParser.g:653:2: iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF
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
    // InternalSTAlgorithmParser.g:659:1: ruleSTVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) ;
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
            // InternalSTAlgorithmParser.g:665:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) )
            // InternalSTAlgorithmParser.g:666:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            {
            // InternalSTAlgorithmParser.g:666:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            // InternalSTAlgorithmParser.g:667:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon
            {
            // InternalSTAlgorithmParser.g:667:3: ()
            // InternalSTAlgorithmParser.g:668:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:674:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTAlgorithmParser.g:675:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:675:4: (lv_name_1_0= RULE_ID )
            // InternalSTAlgorithmParser.g:676:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_16); if (state.failed) return current;
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

            // InternalSTAlgorithmParser.g:692:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==AT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalSTAlgorithmParser.g:693:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:697:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSTAlgorithmParser.g:698:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSTAlgorithmParser.g:698:5: (otherlv_3= RULE_ID )
                    // InternalSTAlgorithmParser.g:699:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Colon,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3());
              		
            }
            // InternalSTAlgorithmParser.g:715:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ARRAY) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSTAlgorithmParser.g:716:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalSTAlgorithmParser.g:716:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalSTAlgorithmParser.g:717:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalSTAlgorithmParser.g:717:5: (lv_array_5_0= ARRAY )
                    // InternalSTAlgorithmParser.g:718:6: lv_array_5_0= ARRAY
                    {
                    lv_array_5_0=(Token)match(input,ARRAY,FOLLOW_19); if (state.failed) return current;
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

                    // InternalSTAlgorithmParser.g:730:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==LeftSquareBracket) ) {
                        int LA18_1 = input.LA(2);

                        if ( ((LA18_1>=LDATE_AND_TIME && LA18_1<=DATE_AND_TIME)||(LA18_1>=LTIME_OF_DAY && LA18_1<=TIME_OF_DAY)||LA18_1==WSTRING||LA18_1==STRING||LA18_1==DWORD||(LA18_1>=FALSE && LA18_1<=ULINT)||(LA18_1>=USINT && LA18_1<=WCHAR)||(LA18_1>=BOOL && LA18_1<=BYTE)||(LA18_1>=CHAR && LA18_1<=DINT)||(LA18_1>=LINT && LA18_1<=SINT)||(LA18_1>=TIME && LA18_1<=WORD)||(LA18_1>=INT && LA18_1<=LDT)||(LA18_1>=NOT && LA18_1<=TOD)||LA18_1==DT||(LA18_1>=LD && LA18_1<=LT)||LA18_1==LeftParenthesis||LA18_1==PlusSign||LA18_1==HyphenMinus||LA18_1==D||LA18_1==T||(LA18_1>=RULE_NON_DECIMAL && LA18_1<=RULE_INT)||(LA18_1>=RULE_ID && LA18_1<=RULE_STRING)) ) {
                            alt18=1;
                        }
                        else if ( (LA18_1==Asterisk) ) {
                            alt18=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 0, input);

                        throw nvae;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:731:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalSTAlgorithmParser.g:731:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalSTAlgorithmParser.g:732:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalSTAlgorithmParser.g:736:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalSTAlgorithmParser.g:737:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalSTAlgorithmParser.g:737:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalSTAlgorithmParser.g:738:8: lv_ranges_7_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_21);
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

                            // InternalSTAlgorithmParser.g:755:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop16:
                            do {
                                int alt16=2;
                                int LA16_0 = input.LA(1);

                                if ( (LA16_0==Comma) ) {
                                    alt16=1;
                                }


                                switch (alt16) {
                            	case 1 :
                            	    // InternalSTAlgorithmParser.g:756:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTAlgorithmParser.g:760:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalSTAlgorithmParser.g:761:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalSTAlgorithmParser.g:761:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalSTAlgorithmParser.g:762:9: lv_ranges_9_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_21);
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
                            	    break loop16;
                                }
                            } while (true);

                            otherlv_10=(Token)match(input,RightSquareBracket,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTAlgorithmParser.g:786:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalSTAlgorithmParser.g:786:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalSTAlgorithmParser.g:787:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalSTAlgorithmParser.g:791:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalSTAlgorithmParser.g:792:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalSTAlgorithmParser.g:792:7: (lv_count_12_0= Asterisk )
                            // InternalSTAlgorithmParser.g:793:8: lv_count_12_0= Asterisk
                            {
                            lv_count_12_0=(Token)match(input,Asterisk,FOLLOW_21); if (state.failed) return current;
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

                            // InternalSTAlgorithmParser.g:805:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop17:
                            do {
                                int alt17=2;
                                int LA17_0 = input.LA(1);

                                if ( (LA17_0==Comma) ) {
                                    alt17=1;
                                }


                                switch (alt17) {
                            	case 1 :
                            	    // InternalSTAlgorithmParser.g:806:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_23); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTAlgorithmParser.g:810:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalSTAlgorithmParser.g:811:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalSTAlgorithmParser.g:811:8: (lv_count_14_0= Asterisk )
                            	    // InternalSTAlgorithmParser.g:812:9: lv_count_14_0= Asterisk
                            	    {
                            	    lv_count_14_0=(Token)match(input,Asterisk,FOLLOW_21); if (state.failed) return current;
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
                            	    break loop17;
                                }
                            } while (true);

                            otherlv_15=(Token)match(input,RightSquareBracket,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_15, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,OF,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:836:3: ( ( ruleSTAnyType ) )
            // InternalSTAlgorithmParser.g:837:4: ( ruleSTAnyType )
            {
            // InternalSTAlgorithmParser.g:837:4: ( ruleSTAnyType )
            // InternalSTAlgorithmParser.g:838:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTVarDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0());
              				
            }
            pushFollow(FOLLOW_24);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTAlgorithmParser.g:852:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==LeftSquareBracket) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSTAlgorithmParser.g:853:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:857:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:858:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:858:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:859:6: lv_maxLength_19_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_25);
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

                    otherlv_20=(Token)match(input,RightSquareBracket,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:881:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==ColonEqualsSign) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSTAlgorithmParser.g:882:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_27); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:886:4: ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    // InternalSTAlgorithmParser.g:887:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    {
                    // InternalSTAlgorithmParser.g:887:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    // InternalSTAlgorithmParser.g:888:6: lv_defaultValue_22_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_28);
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
    // InternalSTAlgorithmParser.g:914:1: entryRuleSTInitializerExpression returns [EObject current=null] : iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF ;
    public final EObject entryRuleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:914:64: (iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF )
            // InternalSTAlgorithmParser.g:915:2: iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF
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
    // InternalSTAlgorithmParser.g:921:1: ruleSTInitializerExpression returns [EObject current=null] : (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression ) ;
    public final EObject ruleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STElementaryInitializerExpression_0 = null;

        EObject this_STArrayInitializerExpression_1 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:927:2: ( (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression ) )
            // InternalSTAlgorithmParser.g:928:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression )
            {
            // InternalSTAlgorithmParser.g:928:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=LDATE_AND_TIME && LA22_0<=DATE_AND_TIME)||(LA22_0>=LTIME_OF_DAY && LA22_0<=TIME_OF_DAY)||LA22_0==WSTRING||LA22_0==STRING||LA22_0==DWORD||(LA22_0>=FALSE && LA22_0<=ULINT)||(LA22_0>=USINT && LA22_0<=WCHAR)||(LA22_0>=BOOL && LA22_0<=BYTE)||(LA22_0>=CHAR && LA22_0<=DINT)||(LA22_0>=LINT && LA22_0<=SINT)||(LA22_0>=TIME && LA22_0<=WORD)||(LA22_0>=INT && LA22_0<=LDT)||(LA22_0>=NOT && LA22_0<=TOD)||LA22_0==DT||(LA22_0>=LD && LA22_0<=LT)||LA22_0==LeftParenthesis||LA22_0==PlusSign||LA22_0==HyphenMinus||LA22_0==D||LA22_0==T||(LA22_0>=RULE_NON_DECIMAL && LA22_0<=RULE_INT)||(LA22_0>=RULE_ID && LA22_0<=RULE_STRING)) ) {
                alt22=1;
            }
            else if ( (LA22_0==LeftSquareBracket) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalSTAlgorithmParser.g:929:3: this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression
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
                    // InternalSTAlgorithmParser.g:938:3: this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression
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
    // InternalSTAlgorithmParser.g:950:1: entryRuleSTElementaryInitializerExpression returns [EObject current=null] : iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF ;
    public final EObject entryRuleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElementaryInitializerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:950:74: (iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF )
            // InternalSTAlgorithmParser.g:951:2: iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF
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
    // InternalSTAlgorithmParser.g:957:1: ruleSTElementaryInitializerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:963:2: ( ( (lv_value_0_0= ruleSTExpression ) ) )
            // InternalSTAlgorithmParser.g:964:2: ( (lv_value_0_0= ruleSTExpression ) )
            {
            // InternalSTAlgorithmParser.g:964:2: ( (lv_value_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:965:3: (lv_value_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:965:3: (lv_value_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:966:4: lv_value_0_0= ruleSTExpression
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
    // InternalSTAlgorithmParser.g:986:1: entryRuleSTArrayInitializerExpression returns [EObject current=null] : iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF ;
    public final EObject entryRuleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitializerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:986:69: (iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF )
            // InternalSTAlgorithmParser.g:987:2: iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF
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
    // InternalSTAlgorithmParser.g:993:1: ruleSTArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:999:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTAlgorithmParser.g:1000:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTAlgorithmParser.g:1000:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTAlgorithmParser.g:1001:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1005:3: ( (lv_values_1_0= ruleSTArrayInitElement ) )
            // InternalSTAlgorithmParser.g:1006:4: (lv_values_1_0= ruleSTArrayInitElement )
            {
            // InternalSTAlgorithmParser.g:1006:4: (lv_values_1_0= ruleSTArrayInitElement )
            // InternalSTAlgorithmParser.g:1007:5: lv_values_1_0= ruleSTArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_21);
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

            // InternalSTAlgorithmParser.g:1024:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==Comma) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1025:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTAlgorithmParser.g:1029:4: ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    // InternalSTAlgorithmParser.g:1030:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    {
            	    // InternalSTAlgorithmParser.g:1030:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    // InternalSTAlgorithmParser.g:1031:6: lv_values_3_0= ruleSTArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_21);
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
            	    break loop23;
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
    // InternalSTAlgorithmParser.g:1057:1: entryRuleSTArrayInitElement returns [EObject current=null] : iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF ;
    public final EObject entryRuleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitElement = null;


        try {
            // InternalSTAlgorithmParser.g:1057:59: (iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF )
            // InternalSTAlgorithmParser.g:1058:2: iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF
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
    // InternalSTAlgorithmParser.g:1064:1: ruleSTArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )? ) ;
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
            // InternalSTAlgorithmParser.g:1070:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )? ) )
            // InternalSTAlgorithmParser.g:1071:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )? )
            {
            // InternalSTAlgorithmParser.g:1071:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )? )
            // InternalSTAlgorithmParser.g:1072:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )?
            {
            // InternalSTAlgorithmParser.g:1072:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1073:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1073:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1074:5: lv_indexOrInitExpression_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getIndexOrInitExpressionSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_29);
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

            // InternalSTAlgorithmParser.g:1091:3: (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==LeftParenthesis) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1092:4: otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )* otherlv_5= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:1096:4: ( (lv_initExpressions_2_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:1097:5: (lv_initExpressions_2_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:1097:5: (lv_initExpressions_2_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:1098:6: lv_initExpressions_2_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_30);
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

                    // InternalSTAlgorithmParser.g:1115:4: (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) ) )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==Comma) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // InternalSTAlgorithmParser.g:1116:5: otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getSTArrayInitElementAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalSTAlgorithmParser.g:1120:5: ( (lv_initExpressions_4_0= ruleSTExpression ) )
                    	    // InternalSTAlgorithmParser.g:1121:6: (lv_initExpressions_4_0= ruleSTExpression )
                    	    {
                    	    // InternalSTAlgorithmParser.g:1121:6: (lv_initExpressions_4_0= ruleSTExpression )
                    	    // InternalSTAlgorithmParser.g:1122:7: lv_initExpressions_4_0= ruleSTExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTExpressionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_30);
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
                    	    break loop24;
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
    // InternalSTAlgorithmParser.g:1149:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1149:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTAlgorithmParser.g:1150:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalSTAlgorithmParser.g:1156:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) ;
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
            // InternalSTAlgorithmParser.g:1162:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) )
            // InternalSTAlgorithmParser.g:1163:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            {
            // InternalSTAlgorithmParser.g:1163:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=LDATE_AND_TIME && LA27_0<=DATE_AND_TIME)||(LA27_0>=LTIME_OF_DAY && LA27_0<=TIME_OF_DAY)||LA27_0==CONTINUE||LA27_0==WSTRING||(LA27_0>=REPEAT && LA27_0<=STRING)||LA27_0==DWORD||(LA27_0>=FALSE && LA27_0<=ULINT)||(LA27_0>=USINT && LA27_0<=DINT)||(LA27_0>=EXIT && LA27_0<=SINT)||(LA27_0>=TIME && LA27_0<=WORD)||(LA27_0>=FOR && LA27_0<=LDT)||LA27_0==TOD||(LA27_0>=DT && LA27_0<=LT)||LA27_0==LeftParenthesis||LA27_0==PlusSign||LA27_0==HyphenMinus||LA27_0==D||LA27_0==T||(LA27_0>=RULE_NON_DECIMAL && LA27_0<=RULE_INT)||(LA27_0>=RULE_ID && LA27_0<=RULE_STRING)) ) {
                alt27=1;
            }
            else if ( (LA27_0==Semicolon) ) {
                alt27=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1164:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    {
                    // InternalSTAlgorithmParser.g:1164:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    // InternalSTAlgorithmParser.g:1165:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon
                    {
                    // InternalSTAlgorithmParser.g:1165:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )
                    int alt26=10;
                    alt26 = dfa26.predict(input);
                    switch (alt26) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:1166:5: this_STIfStatement_0= ruleSTIfStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTIfStatementParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_28);
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
                            // InternalSTAlgorithmParser.g:1175:5: this_STCaseStatement_1= ruleSTCaseStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCaseStatementParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_28);
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
                            // InternalSTAlgorithmParser.g:1184:5: this_STForStatement_2= ruleSTForStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTForStatementParserRuleCall_0_0_2());
                              				
                            }
                            pushFollow(FOLLOW_28);
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
                            // InternalSTAlgorithmParser.g:1193:5: this_STWhileStatement_3= ruleSTWhileStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTWhileStatementParserRuleCall_0_0_3());
                              				
                            }
                            pushFollow(FOLLOW_28);
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
                            // InternalSTAlgorithmParser.g:1202:5: this_STRepeatStatement_4= ruleSTRepeatStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTRepeatStatementParserRuleCall_0_0_4());
                              				
                            }
                            pushFollow(FOLLOW_28);
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
                            // InternalSTAlgorithmParser.g:1211:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            {
                            // InternalSTAlgorithmParser.g:1211:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            // InternalSTAlgorithmParser.g:1212:6: ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentStatementParserRuleCall_0_0_5());
                              					
                            }
                            pushFollow(FOLLOW_28);
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
                            // InternalSTAlgorithmParser.g:1223:5: this_STCallStatement_6= ruleSTCallStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCallStatementParserRuleCall_0_0_6());
                              				
                            }
                            pushFollow(FOLLOW_28);
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
                            // InternalSTAlgorithmParser.g:1232:5: ( () otherlv_8= RETURN )
                            {
                            // InternalSTAlgorithmParser.g:1232:5: ( () otherlv_8= RETURN )
                            // InternalSTAlgorithmParser.g:1233:6: () otherlv_8= RETURN
                            {
                            // InternalSTAlgorithmParser.g:1233:6: ()
                            // InternalSTAlgorithmParser.g:1234:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_7_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_8=(Token)match(input,RETURN,FOLLOW_28); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_8, grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_7_1());
                              					
                            }

                            }


                            }
                            break;
                        case 9 :
                            // InternalSTAlgorithmParser.g:1246:5: ( () otherlv_10= CONTINUE )
                            {
                            // InternalSTAlgorithmParser.g:1246:5: ( () otherlv_10= CONTINUE )
                            // InternalSTAlgorithmParser.g:1247:6: () otherlv_10= CONTINUE
                            {
                            // InternalSTAlgorithmParser.g:1247:6: ()
                            // InternalSTAlgorithmParser.g:1248:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_8_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_10=(Token)match(input,CONTINUE,FOLLOW_28); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_8_1());
                              					
                            }

                            }


                            }
                            break;
                        case 10 :
                            // InternalSTAlgorithmParser.g:1260:5: ( () otherlv_12= EXIT )
                            {
                            // InternalSTAlgorithmParser.g:1260:5: ( () otherlv_12= EXIT )
                            // InternalSTAlgorithmParser.g:1261:6: () otherlv_12= EXIT
                            {
                            // InternalSTAlgorithmParser.g:1261:6: ()
                            // InternalSTAlgorithmParser.g:1262:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTExitAction_0_0_9_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_12=(Token)match(input,EXIT,FOLLOW_28); if (state.failed) return current;
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
                    // InternalSTAlgorithmParser.g:1280:3: ( () otherlv_15= Semicolon )
                    {
                    // InternalSTAlgorithmParser.g:1280:3: ( () otherlv_15= Semicolon )
                    // InternalSTAlgorithmParser.g:1281:4: () otherlv_15= Semicolon
                    {
                    // InternalSTAlgorithmParser.g:1281:4: ()
                    // InternalSTAlgorithmParser.g:1282:5: 
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
    // InternalSTAlgorithmParser.g:1297:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1297:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTAlgorithmParser.g:1298:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTAlgorithmParser.g:1304:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1310:2: ( ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) )
            // InternalSTAlgorithmParser.g:1311:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            {
            // InternalSTAlgorithmParser.g:1311:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            // InternalSTAlgorithmParser.g:1312:3: ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) )
            {
            // InternalSTAlgorithmParser.g:1312:3: ( (lv_left_0_0= ruleSTAccessExpression ) )
            // InternalSTAlgorithmParser.g:1313:4: (lv_left_0_0= ruleSTAccessExpression )
            {
            // InternalSTAlgorithmParser.g:1313:4: (lv_left_0_0= ruleSTAccessExpression )
            // InternalSTAlgorithmParser.g:1314:5: lv_left_0_0= ruleSTAccessExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAssignmentStatementAccess().getLeftSTAccessExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_31);
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

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTAssignmentStatementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:1335:3: ( (lv_right_2_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1336:4: (lv_right_2_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1336:4: (lv_right_2_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1337:5: lv_right_2_0= ruleSTExpression
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
    // InternalSTAlgorithmParser.g:1358:1: entryRuleSTCallStatement returns [EObject current=null] : iv_ruleSTCallStatement= ruleSTCallStatement EOF ;
    public final EObject entryRuleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1358:56: (iv_ruleSTCallStatement= ruleSTCallStatement EOF )
            // InternalSTAlgorithmParser.g:1359:2: iv_ruleSTCallStatement= ruleSTCallStatement EOF
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
    // InternalSTAlgorithmParser.g:1365:1: ruleSTCallStatement returns [EObject current=null] : ( (lv_call_0_0= ruleSTAccessExpression ) ) ;
    public final EObject ruleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject lv_call_0_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1371:2: ( ( (lv_call_0_0= ruleSTAccessExpression ) ) )
            // InternalSTAlgorithmParser.g:1372:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            {
            // InternalSTAlgorithmParser.g:1372:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            // InternalSTAlgorithmParser.g:1373:3: (lv_call_0_0= ruleSTAccessExpression )
            {
            // InternalSTAlgorithmParser.g:1373:3: (lv_call_0_0= ruleSTAccessExpression )
            // InternalSTAlgorithmParser.g:1374:4: lv_call_0_0= ruleSTAccessExpression
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
    // InternalSTAlgorithmParser.g:1394:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalSTAlgorithmParser.g:1394:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalSTAlgorithmParser.g:1395:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
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
    // InternalSTAlgorithmParser.g:1401:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1407:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalSTAlgorithmParser.g:1408:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalSTAlgorithmParser.g:1408:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            int alt28=3;
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
                alt28=1;
                }
                break;
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case ColonEqualsSign:
                    {
                    alt28=2;
                    }
                    break;
                case EqualsSignGreaterThanSign:
                    {
                    alt28=3;
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
                    alt28=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 2, input);

                    throw nvae;
                }

                }
                break;
            case NOT:
                {
                int LA28_3 = input.LA(2);

                if ( ((LA28_3>=LDATE_AND_TIME && LA28_3<=DATE_AND_TIME)||(LA28_3>=LTIME_OF_DAY && LA28_3<=TIME_OF_DAY)||LA28_3==WSTRING||LA28_3==STRING||LA28_3==DWORD||(LA28_3>=FALSE && LA28_3<=ULINT)||(LA28_3>=USINT && LA28_3<=WCHAR)||(LA28_3>=BOOL && LA28_3<=BYTE)||(LA28_3>=CHAR && LA28_3<=DINT)||(LA28_3>=LINT && LA28_3<=SINT)||(LA28_3>=TIME && LA28_3<=WORD)||(LA28_3>=INT && LA28_3<=LDT)||(LA28_3>=NOT && LA28_3<=TOD)||LA28_3==DT||(LA28_3>=LD && LA28_3<=LT)||LA28_3==LeftParenthesis||LA28_3==PlusSign||LA28_3==HyphenMinus||LA28_3==D||LA28_3==T||(LA28_3>=RULE_NON_DECIMAL && LA28_3<=RULE_INT)||LA28_3==RULE_STRING) ) {
                    alt28=1;
                }
                else if ( (LA28_3==RULE_ID) ) {
                    int LA28_6 = input.LA(3);

                    if ( (LA28_6==EqualsSignGreaterThanSign) ) {
                        alt28=3;
                    }
                    else if ( (LA28_6==EOF||LA28_6==AND||LA28_6==MOD||LA28_6==XOR||(LA28_6>=AsteriskAsterisk && LA28_6<=FullStopFullStop)||(LA28_6>=LessThanSignEqualsSign && LA28_6<=LessThanSignGreaterThanSign)||LA28_6==GreaterThanSignEqualsSign||LA28_6==OR||(LA28_6>=Ampersand && LA28_6<=Solidus)||(LA28_6>=LessThanSign && LA28_6<=GreaterThanSign)||LA28_6==LeftSquareBracket) ) {
                        alt28=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 6, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1409:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
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
                    // InternalSTAlgorithmParser.g:1418:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
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
                    // InternalSTAlgorithmParser.g:1427:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
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
    // InternalSTAlgorithmParser.g:1439:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalSTAlgorithmParser.g:1439:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalSTAlgorithmParser.g:1440:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
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
    // InternalSTAlgorithmParser.g:1446:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_arg_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_arg_0_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1452:2: ( ( (lv_arg_0_0= ruleSTExpression ) ) )
            // InternalSTAlgorithmParser.g:1453:2: ( (lv_arg_0_0= ruleSTExpression ) )
            {
            // InternalSTAlgorithmParser.g:1453:2: ( (lv_arg_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1454:3: (lv_arg_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1454:3: (lv_arg_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1455:4: lv_arg_0_0= ruleSTExpression
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
    // InternalSTAlgorithmParser.g:1475:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalSTAlgorithmParser.g:1475:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalSTAlgorithmParser.g:1476:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
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
    // InternalSTAlgorithmParser.g:1482:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_source_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1488:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) ) )
            // InternalSTAlgorithmParser.g:1489:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) )
            {
            // InternalSTAlgorithmParser.g:1489:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) )
            // InternalSTAlgorithmParser.g:1490:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) )
            {
            // InternalSTAlgorithmParser.g:1490:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTAlgorithmParser.g:1491:4: (otherlv_0= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:1491:4: (otherlv_0= RULE_ID )
            // InternalSTAlgorithmParser.g:1492:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_31); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTCallNamedInputArgumentAccess().getTargetINamedElementCrossReference_0_0());
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:1507:3: ( (lv_source_2_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1508:4: (lv_source_2_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1508:4: (lv_source_2_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1509:5: lv_source_2_0= ruleSTExpression
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
    // InternalSTAlgorithmParser.g:1530:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalSTAlgorithmParser.g:1530:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalSTAlgorithmParser.g:1531:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
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
    // InternalSTAlgorithmParser.g:1537:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1543:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) ) )
            // InternalSTAlgorithmParser.g:1544:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) )
            {
            // InternalSTAlgorithmParser.g:1544:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) )
            // InternalSTAlgorithmParser.g:1545:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) )
            {
            // InternalSTAlgorithmParser.g:1545:3: ( (lv_not_0_0= NOT ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==NOT) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1546:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTAlgorithmParser.g:1546:4: (lv_not_0_0= NOT )
                    // InternalSTAlgorithmParser.g:1547:5: lv_not_0_0= NOT
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

            // InternalSTAlgorithmParser.g:1559:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTAlgorithmParser.g:1560:4: (otherlv_1= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:1560:4: (otherlv_1= RULE_ID )
            // InternalSTAlgorithmParser.g:1561:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTCallNamedOutputArgumentAccess().getSourceINamedElementCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1576:3: ( (otherlv_3= RULE_ID ) )
            // InternalSTAlgorithmParser.g:1577:4: (otherlv_3= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:1577:4: (otherlv_3= RULE_ID )
            // InternalSTAlgorithmParser.g:1578:5: otherlv_3= RULE_ID
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
    // InternalSTAlgorithmParser.g:1593:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1593:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalSTAlgorithmParser.g:1594:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalSTAlgorithmParser.g:1600:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTAlgorithmParser.g:1606:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTAlgorithmParser.g:1607:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTAlgorithmParser.g:1607:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTAlgorithmParser.g:1608:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1612:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1613:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1613:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1614:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_33);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatementAccess().getTHENKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1635:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>=LDATE_AND_TIME && LA30_0<=DATE_AND_TIME)||(LA30_0>=LTIME_OF_DAY && LA30_0<=TIME_OF_DAY)||LA30_0==CONTINUE||LA30_0==WSTRING||(LA30_0>=REPEAT && LA30_0<=STRING)||LA30_0==DWORD||(LA30_0>=FALSE && LA30_0<=ULINT)||(LA30_0>=USINT && LA30_0<=DINT)||(LA30_0>=EXIT && LA30_0<=SINT)||(LA30_0>=TIME && LA30_0<=WORD)||(LA30_0>=FOR && LA30_0<=LDT)||LA30_0==TOD||(LA30_0>=DT && LA30_0<=LT)||LA30_0==LeftParenthesis||LA30_0==PlusSign||LA30_0==HyphenMinus||LA30_0==Semicolon||LA30_0==D||LA30_0==T||(LA30_0>=RULE_NON_DECIMAL && LA30_0<=RULE_INT)||(LA30_0>=RULE_ID && LA30_0<=RULE_STRING)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1636:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1636:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1637:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_34);
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
            	    break loop30;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:1654:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==ELSIF) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1655:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTAlgorithmParser.g:1655:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTAlgorithmParser.g:1656:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_35);
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
            	    break loop31;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:1673:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==ELSE) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1674:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTAlgorithmParser.g:1674:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTAlgorithmParser.g:1675:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_36);
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
    // InternalSTAlgorithmParser.g:1700:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTAlgorithmParser.g:1700:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTAlgorithmParser.g:1701:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTAlgorithmParser.g:1707:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1713:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1714:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1714:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1715:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1719:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1720:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1720:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1721:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_33);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1742:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( ((LA33_0>=LDATE_AND_TIME && LA33_0<=DATE_AND_TIME)||(LA33_0>=LTIME_OF_DAY && LA33_0<=TIME_OF_DAY)||LA33_0==CONTINUE||LA33_0==WSTRING||(LA33_0>=REPEAT && LA33_0<=STRING)||LA33_0==DWORD||(LA33_0>=FALSE && LA33_0<=ULINT)||(LA33_0>=USINT && LA33_0<=DINT)||(LA33_0>=EXIT && LA33_0<=SINT)||(LA33_0>=TIME && LA33_0<=WORD)||(LA33_0>=FOR && LA33_0<=LDT)||LA33_0==TOD||(LA33_0>=DT && LA33_0<=LT)||LA33_0==LeftParenthesis||LA33_0==PlusSign||LA33_0==HyphenMinus||LA33_0==Semicolon||LA33_0==D||LA33_0==T||(LA33_0>=RULE_NON_DECIMAL && LA33_0<=RULE_INT)||(LA33_0>=RULE_ID && LA33_0<=RULE_STRING)) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1743:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1743:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1744:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalSTAlgorithmParser.g:1765:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1765:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTAlgorithmParser.g:1766:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTAlgorithmParser.g:1772:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTAlgorithmParser.g:1778:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTAlgorithmParser.g:1779:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTAlgorithmParser.g:1779:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTAlgorithmParser.g:1780:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1784:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1785:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1785:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1786:5: lv_selector_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_22);
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

            otherlv_2=(Token)match(input,OF,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCaseStatementAccess().getOFKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1807:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt34=0;
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( ((LA34_0>=LDATE_AND_TIME && LA34_0<=DATE_AND_TIME)||(LA34_0>=LTIME_OF_DAY && LA34_0<=TIME_OF_DAY)||LA34_0==WSTRING||LA34_0==STRING||LA34_0==DWORD||(LA34_0>=FALSE && LA34_0<=ULINT)||(LA34_0>=USINT && LA34_0<=WCHAR)||(LA34_0>=BOOL && LA34_0<=BYTE)||(LA34_0>=CHAR && LA34_0<=DINT)||(LA34_0>=LINT && LA34_0<=SINT)||(LA34_0>=TIME && LA34_0<=WORD)||(LA34_0>=INT && LA34_0<=LDT)||(LA34_0>=NOT && LA34_0<=TOD)||LA34_0==DT||(LA34_0>=LD && LA34_0<=LT)||LA34_0==LeftParenthesis||LA34_0==PlusSign||LA34_0==HyphenMinus||LA34_0==D||LA34_0==T||(LA34_0>=RULE_NON_DECIMAL && LA34_0<=RULE_INT)||(LA34_0>=RULE_ID && LA34_0<=RULE_STRING)) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1808:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTAlgorithmParser.g:1808:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTAlgorithmParser.g:1809:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_37);
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
            	    if ( cnt34 >= 1 ) break loop34;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(34, input);
                        throw eee;
                }
                cnt34++;
            } while (true);

            // InternalSTAlgorithmParser.g:1826:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ELSE) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1827:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTAlgorithmParser.g:1827:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTAlgorithmParser.g:1828:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_38);
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
    // InternalSTAlgorithmParser.g:1853:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTAlgorithmParser.g:1853:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTAlgorithmParser.g:1854:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTAlgorithmParser.g:1860:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1866:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1867:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1867:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1868:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:1868:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1869:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1869:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1870:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_39);
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

            // InternalSTAlgorithmParser.g:1887:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==Comma) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1888:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTAlgorithmParser.g:1892:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTAlgorithmParser.g:1893:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1893:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTAlgorithmParser.g:1894:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_39);
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
            	    break loop36;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1916:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop37:
            do {
                int alt37=2;
                alt37 = dfa37.predict(input);
                switch (alt37) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1917:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1921:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1922:5: lv_statements_4_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTAlgorithmParser.g:1943:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTAlgorithmParser.g:1943:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTAlgorithmParser.g:1944:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTAlgorithmParser.g:1950:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1956:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1957:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1957:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1958:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:1958:3: ()
            // InternalSTAlgorithmParser.g:1959:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,ELSE,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:1969:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( ((LA38_0>=LDATE_AND_TIME && LA38_0<=DATE_AND_TIME)||(LA38_0>=LTIME_OF_DAY && LA38_0<=TIME_OF_DAY)||LA38_0==CONTINUE||LA38_0==WSTRING||(LA38_0>=REPEAT && LA38_0<=STRING)||LA38_0==DWORD||(LA38_0>=FALSE && LA38_0<=ULINT)||(LA38_0>=USINT && LA38_0<=DINT)||(LA38_0>=EXIT && LA38_0<=SINT)||(LA38_0>=TIME && LA38_0<=WORD)||(LA38_0>=FOR && LA38_0<=LDT)||LA38_0==TOD||(LA38_0>=DT && LA38_0<=LT)||LA38_0==LeftParenthesis||LA38_0==PlusSign||LA38_0==HyphenMinus||LA38_0==Semicolon||LA38_0==D||LA38_0==T||(LA38_0>=RULE_NON_DECIMAL && LA38_0<=RULE_INT)||(LA38_0>=RULE_ID && LA38_0<=RULE_STRING)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1970:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1970:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1971:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalSTAlgorithmParser.g:1992:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1992:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTAlgorithmParser.g:1993:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTAlgorithmParser.g:1999:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
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
            // InternalSTAlgorithmParser.g:2005:2: ( (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalSTAlgorithmParser.g:2006:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalSTAlgorithmParser.g:2006:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalSTAlgorithmParser.g:2007:3: otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:2011:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTAlgorithmParser.g:2012:4: (otherlv_1= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:2012:4: (otherlv_1= RULE_ID )
            // InternalSTAlgorithmParser.g:2013:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTForStatementRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_31); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTForStatementAccess().getVariableSTVarDeclarationCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:2028:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:2029:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:2029:4: (lv_from_3_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:2030:5: lv_from_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_40);
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

            otherlv_4=(Token)match(input,TO,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getTOKeyword_4());
              		
            }
            // InternalSTAlgorithmParser.g:2051:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:2052:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:2052:4: (lv_to_5_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:2053:5: lv_to_5_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_41);
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

            // InternalSTAlgorithmParser.g:2070:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==BY) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2071:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:2075:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:2076:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:2076:5: (lv_by_7_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:2077:6: lv_by_7_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_42);
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

            otherlv_8=(Token)match(input,DO,FOLLOW_43); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
              		
            }
            // InternalSTAlgorithmParser.g:2099:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( ((LA40_0>=LDATE_AND_TIME && LA40_0<=DATE_AND_TIME)||(LA40_0>=LTIME_OF_DAY && LA40_0<=TIME_OF_DAY)||LA40_0==CONTINUE||LA40_0==WSTRING||(LA40_0>=REPEAT && LA40_0<=STRING)||LA40_0==DWORD||(LA40_0>=FALSE && LA40_0<=ULINT)||(LA40_0>=USINT && LA40_0<=DINT)||(LA40_0>=EXIT && LA40_0<=SINT)||(LA40_0>=TIME && LA40_0<=WORD)||(LA40_0>=FOR && LA40_0<=LDT)||LA40_0==TOD||(LA40_0>=DT && LA40_0<=LT)||LA40_0==LeftParenthesis||LA40_0==PlusSign||LA40_0==HyphenMinus||LA40_0==Semicolon||LA40_0==D||LA40_0==T||(LA40_0>=RULE_NON_DECIMAL && LA40_0<=RULE_INT)||(LA40_0>=RULE_ID && LA40_0<=RULE_STRING)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2100:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:2100:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:2101:5: lv_statements_9_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_43);
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
            	    break loop40;
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
    // InternalSTAlgorithmParser.g:2126:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTAlgorithmParser.g:2126:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTAlgorithmParser.g:2127:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTAlgorithmParser.g:2133:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2139:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTAlgorithmParser.g:2140:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTAlgorithmParser.g:2140:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTAlgorithmParser.g:2141:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:2145:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:2146:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:2146:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:2147:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_42);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_44); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:2168:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( ((LA41_0>=LDATE_AND_TIME && LA41_0<=DATE_AND_TIME)||(LA41_0>=LTIME_OF_DAY && LA41_0<=TIME_OF_DAY)||LA41_0==CONTINUE||LA41_0==WSTRING||(LA41_0>=REPEAT && LA41_0<=STRING)||LA41_0==DWORD||(LA41_0>=FALSE && LA41_0<=ULINT)||(LA41_0>=USINT && LA41_0<=DINT)||(LA41_0>=EXIT && LA41_0<=SINT)||(LA41_0>=TIME && LA41_0<=WORD)||(LA41_0>=FOR && LA41_0<=LDT)||LA41_0==TOD||(LA41_0>=DT && LA41_0<=LT)||LA41_0==LeftParenthesis||LA41_0==PlusSign||LA41_0==HyphenMinus||LA41_0==Semicolon||LA41_0==D||LA41_0==T||(LA41_0>=RULE_NON_DECIMAL && LA41_0<=RULE_INT)||(LA41_0>=RULE_ID && LA41_0<=RULE_STRING)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2169:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:2169:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:2170:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_44);
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
            	    break loop41;
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
    // InternalSTAlgorithmParser.g:2195:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTAlgorithmParser.g:2195:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTAlgorithmParser.g:2196:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTAlgorithmParser.g:2202:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2208:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTAlgorithmParser.g:2209:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTAlgorithmParser.g:2209:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTAlgorithmParser.g:2210:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_45); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:2214:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( ((LA42_0>=LDATE_AND_TIME && LA42_0<=DATE_AND_TIME)||(LA42_0>=LTIME_OF_DAY && LA42_0<=TIME_OF_DAY)||LA42_0==CONTINUE||LA42_0==WSTRING||(LA42_0>=REPEAT && LA42_0<=STRING)||LA42_0==DWORD||(LA42_0>=FALSE && LA42_0<=ULINT)||(LA42_0>=USINT && LA42_0<=DINT)||(LA42_0>=EXIT && LA42_0<=SINT)||(LA42_0>=TIME && LA42_0<=WORD)||(LA42_0>=FOR && LA42_0<=LDT)||LA42_0==TOD||(LA42_0>=DT && LA42_0<=LT)||LA42_0==LeftParenthesis||LA42_0==PlusSign||LA42_0==HyphenMinus||LA42_0==Semicolon||LA42_0==D||LA42_0==T||(LA42_0>=RULE_NON_DECIMAL && LA42_0<=RULE_INT)||(LA42_0>=RULE_ID && LA42_0<=RULE_STRING)) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2215:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:2215:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:2216:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_45);
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
            	    break loop42;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:2237:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:2238:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:2238:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:2239:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_46);
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
    // InternalSTAlgorithmParser.g:2264:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2264:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTAlgorithmParser.g:2265:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTAlgorithmParser.g:2271:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2277:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTAlgorithmParser.g:2278:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTAlgorithmParser.g:2289:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2289:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTAlgorithmParser.g:2290:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTAlgorithmParser.g:2296:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2302:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2303:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2303:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2304:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_47);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2312:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==FullStopFullStop) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2313:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2313:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2314:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2314:5: ()
            	    // InternalSTAlgorithmParser.g:2315:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2321:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTAlgorithmParser.g:2322:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2322:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTAlgorithmParser.g:2323:7: lv_op_2_0= ruleSubrangeOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2341:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTAlgorithmParser.g:2342:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2342:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTAlgorithmParser.g:2343:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalSTAlgorithmParser.g:2365:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2365:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTAlgorithmParser.g:2366:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTAlgorithmParser.g:2372:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2378:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2379:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2379:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2380:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_48);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2388:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==OR) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2389:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2389:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2390:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2390:5: ()
            	    // InternalSTAlgorithmParser.g:2391:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2397:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTAlgorithmParser.g:2398:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2398:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTAlgorithmParser.g:2399:7: lv_op_2_0= ruleOrOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2417:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTAlgorithmParser.g:2418:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2418:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTAlgorithmParser.g:2419:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_48);
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalSTAlgorithmParser.g:2441:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2441:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTAlgorithmParser.g:2442:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTAlgorithmParser.g:2448:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2454:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2455:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2455:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2456:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2464:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==XOR) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2465:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2465:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2466:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2466:5: ()
            	    // InternalSTAlgorithmParser.g:2467:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2473:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTAlgorithmParser.g:2474:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2474:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTAlgorithmParser.g:2475:7: lv_op_2_0= ruleXorOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2493:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTAlgorithmParser.g:2494:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2494:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTAlgorithmParser.g:2495:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalSTAlgorithmParser.g:2517:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2517:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTAlgorithmParser.g:2518:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTAlgorithmParser.g:2524:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2530:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2531:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2531:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2532:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_50);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2540:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==AND||LA46_0==Ampersand) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2541:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2541:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2542:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2542:5: ()
            	    // InternalSTAlgorithmParser.g:2543:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2549:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTAlgorithmParser.g:2550:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2550:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTAlgorithmParser.g:2551:7: lv_op_2_0= ruleAndOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2569:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTAlgorithmParser.g:2570:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2570:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTAlgorithmParser.g:2571:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_50);
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalSTAlgorithmParser.g:2593:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2593:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTAlgorithmParser.g:2594:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTAlgorithmParser.g:2600:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2606:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2607:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2607:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2608:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2616:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==LessThanSignGreaterThanSign||LA47_0==EqualsSign) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2617:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2617:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2618:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2618:5: ()
            	    // InternalSTAlgorithmParser.g:2619:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2625:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTAlgorithmParser.g:2626:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2626:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTAlgorithmParser.g:2627:7: lv_op_2_0= ruleEqualityOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2645:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTAlgorithmParser.g:2646:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2646:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTAlgorithmParser.g:2647:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_51);
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalSTAlgorithmParser.g:2669:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2669:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTAlgorithmParser.g:2670:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTAlgorithmParser.g:2676:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2682:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2683:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2683:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2684:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_52);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2692:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==LessThanSignEqualsSign||LA48_0==GreaterThanSignEqualsSign||LA48_0==LessThanSign||LA48_0==GreaterThanSign) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2693:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2693:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2694:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2694:5: ()
            	    // InternalSTAlgorithmParser.g:2695:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2701:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTAlgorithmParser.g:2702:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2702:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTAlgorithmParser.g:2703:7: lv_op_2_0= ruleCompareOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2721:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTAlgorithmParser.g:2722:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2722:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTAlgorithmParser.g:2723:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_52);
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
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalSTAlgorithmParser.g:2745:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2745:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTAlgorithmParser.g:2746:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTAlgorithmParser.g:2752:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2758:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2759:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2759:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2760:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_53);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2768:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==PlusSign||LA49_0==HyphenMinus) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2769:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2769:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2770:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2770:5: ()
            	    // InternalSTAlgorithmParser.g:2771:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2777:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTAlgorithmParser.g:2778:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2778:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTAlgorithmParser.g:2779:7: lv_op_2_0= ruleAddSubOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2797:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTAlgorithmParser.g:2798:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2798:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTAlgorithmParser.g:2799:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_53);
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
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalSTAlgorithmParser.g:2821:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2821:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTAlgorithmParser.g:2822:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTAlgorithmParser.g:2828:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2834:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2835:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2835:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2836:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_54);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2844:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==MOD||LA50_0==Asterisk||LA50_0==Solidus) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2845:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2845:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2846:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2846:5: ()
            	    // InternalSTAlgorithmParser.g:2847:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2853:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTAlgorithmParser.g:2854:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2854:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTAlgorithmParser.g:2855:7: lv_op_2_0= ruleMulDivModOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2873:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTAlgorithmParser.g:2874:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2874:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTAlgorithmParser.g:2875:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_54);
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
    // $ANTLR end "ruleSTMulDivModExpression"


    // $ANTLR start "entryRuleSTPowerExpression"
    // InternalSTAlgorithmParser.g:2897:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2897:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTAlgorithmParser.g:2898:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTAlgorithmParser.g:2904:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2910:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2911:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2911:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2912:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_55);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2920:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==AsteriskAsterisk) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2921:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2921:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2922:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2922:5: ()
            	    // InternalSTAlgorithmParser.g:2923:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2929:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTAlgorithmParser.g:2930:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2930:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTAlgorithmParser.g:2931:7: lv_op_2_0= rulePowerOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_20);
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

            	    // InternalSTAlgorithmParser.g:2949:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalSTAlgorithmParser.g:2950:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2950:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalSTAlgorithmParser.g:2951:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_55);
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTUnaryExpression"
    // InternalSTAlgorithmParser.g:2973:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2973:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalSTAlgorithmParser.g:2974:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalSTAlgorithmParser.g:2980:1: ruleSTUnaryExpression returns [EObject current=null] : ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2986:2: ( ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalSTAlgorithmParser.g:2987:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalSTAlgorithmParser.g:2987:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            int alt52=2;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2988:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    {
                    // InternalSTAlgorithmParser.g:2988:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    // InternalSTAlgorithmParser.g:2989:4: ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalSTAlgorithmParser.g:3000:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalSTAlgorithmParser.g:3000:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    // InternalSTAlgorithmParser.g:3001:4: () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    {
                    // InternalSTAlgorithmParser.g:3001:4: ()
                    // InternalSTAlgorithmParser.g:3002:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:3008:4: ( (lv_op_2_0= ruleUnaryOperator ) )
                    // InternalSTAlgorithmParser.g:3009:5: (lv_op_2_0= ruleUnaryOperator )
                    {
                    // InternalSTAlgorithmParser.g:3009:5: (lv_op_2_0= ruleUnaryOperator )
                    // InternalSTAlgorithmParser.g:3010:6: lv_op_2_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_20);
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

                    // InternalSTAlgorithmParser.g:3027:4: ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    // InternalSTAlgorithmParser.g:3028:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    {
                    // InternalSTAlgorithmParser.g:3028:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    // InternalSTAlgorithmParser.g:3029:6: lv_expression_3_0= ruleSTUnaryExpression
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
    // InternalSTAlgorithmParser.g:3051:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalSTAlgorithmParser.g:3051:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalSTAlgorithmParser.g:3052:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalSTAlgorithmParser.g:3058:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalSTAlgorithmParser.g:3064:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalSTAlgorithmParser.g:3065:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalSTAlgorithmParser.g:3065:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalSTAlgorithmParser.g:3066:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_56);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:3074:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop55:
            do {
                int alt55=3;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==FullStop) ) {
                    alt55=1;
                }
                else if ( (LA55_0==LeftSquareBracket) ) {
                    alt55=2;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:3075:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalSTAlgorithmParser.g:3075:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalSTAlgorithmParser.g:3076:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalSTAlgorithmParser.g:3076:5: ()
            	    // InternalSTAlgorithmParser.g:3077:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_57); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalSTAlgorithmParser.g:3087:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalSTAlgorithmParser.g:3088:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:3088:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalSTAlgorithmParser.g:3089:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:3089:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt53=2;
            	    int LA53_0 = input.LA(1);

            	    if ( (LA53_0==RULE_ID) ) {
            	        alt53=1;
            	    }
            	    else if ( ((LA53_0>=B && LA53_0<=X)||LA53_0==LeftParenthesis||LA53_0==RULE_INT) ) {
            	        alt53=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 53, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt53) {
            	        case 1 :
            	            // InternalSTAlgorithmParser.g:3090:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_56);
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
            	            // InternalSTAlgorithmParser.g:3106:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_56);
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
            	    // InternalSTAlgorithmParser.g:3126:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalSTAlgorithmParser.g:3126:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalSTAlgorithmParser.g:3127:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalSTAlgorithmParser.g:3127:5: ()
            	    // InternalSTAlgorithmParser.g:3128:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_5=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_5, grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1());
            	      				
            	    }
            	    // InternalSTAlgorithmParser.g:3138:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalSTAlgorithmParser.g:3139:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:3139:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalSTAlgorithmParser.g:3140:7: lv_index_6_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_21);
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

            	    // InternalSTAlgorithmParser.g:3157:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop54:
            	    do {
            	        int alt54=2;
            	        int LA54_0 = input.LA(1);

            	        if ( (LA54_0==Comma) ) {
            	            alt54=1;
            	        }


            	        switch (alt54) {
            	    	case 1 :
            	    	    // InternalSTAlgorithmParser.g:3158:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalSTAlgorithmParser.g:3162:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalSTAlgorithmParser.g:3163:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalSTAlgorithmParser.g:3163:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalSTAlgorithmParser.g:3164:8: lv_index_8_0= ruleSTExpression
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0());
            	    	      							
            	    	    }
            	    	    pushFollow(FOLLOW_21);
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
            	    	    break loop54;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_56); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop55;
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
    // InternalSTAlgorithmParser.g:3192:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalSTAlgorithmParser.g:3192:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalSTAlgorithmParser.g:3193:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalSTAlgorithmParser.g:3199:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STLiteralExpressions_4 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3205:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) )
            // InternalSTAlgorithmParser.g:3206:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            {
            // InternalSTAlgorithmParser.g:3206:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            int alt56=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt56=1;
                }
                break;
            case RULE_ID:
                {
                alt56=2;
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
                alt56=3;
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
                    // InternalSTAlgorithmParser.g:3207:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTAlgorithmParser.g:3207:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTAlgorithmParser.g:3208:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_58);
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
                    // InternalSTAlgorithmParser.g:3226:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalSTAlgorithmParser.g:3235:3: this_STLiteralExpressions_4= ruleSTLiteralExpressions
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
    // InternalSTAlgorithmParser.g:3247:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalSTAlgorithmParser.g:3247:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalSTAlgorithmParser.g:3248:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalSTAlgorithmParser.g:3254:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
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
            // InternalSTAlgorithmParser.g:3260:2: ( ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalSTAlgorithmParser.g:3261:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalSTAlgorithmParser.g:3261:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalSTAlgorithmParser.g:3262:3: () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalSTAlgorithmParser.g:3262:3: ()
            // InternalSTAlgorithmParser.g:3263:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:3269:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTAlgorithmParser.g:3270:4: (otherlv_1= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:3270:4: (otherlv_1= RULE_ID )
            // InternalSTAlgorithmParser.g:3271:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_29); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
              				
            }

            }


            }

            // InternalSTAlgorithmParser.g:3282:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt59=2;
            alt59 = dfa59.predict(input);
            switch (alt59) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3283:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalSTAlgorithmParser.g:3283:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis )
                    // InternalSTAlgorithmParser.g:3284:5: ( LeftParenthesis )=>otherlv_2= LeftParenthesis
                    {
                    otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getSTFeatureExpressionAccess().getLeftParenthesisKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:3290:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( ((LA58_0>=LDATE_AND_TIME && LA58_0<=DATE_AND_TIME)||(LA58_0>=LTIME_OF_DAY && LA58_0<=TIME_OF_DAY)||LA58_0==WSTRING||LA58_0==STRING||LA58_0==DWORD||(LA58_0>=FALSE && LA58_0<=ULINT)||(LA58_0>=USINT && LA58_0<=WCHAR)||(LA58_0>=BOOL && LA58_0<=BYTE)||(LA58_0>=CHAR && LA58_0<=DINT)||(LA58_0>=LINT && LA58_0<=SINT)||(LA58_0>=TIME && LA58_0<=WORD)||(LA58_0>=INT && LA58_0<=LDT)||(LA58_0>=NOT && LA58_0<=TOD)||LA58_0==DT||(LA58_0>=LD && LA58_0<=LT)||LA58_0==LeftParenthesis||LA58_0==PlusSign||LA58_0==HyphenMinus||LA58_0==D||LA58_0==T||(LA58_0>=RULE_NON_DECIMAL && LA58_0<=RULE_INT)||(LA58_0>=RULE_ID && LA58_0<=RULE_STRING)) ) {
                        alt58=1;
                    }
                    switch (alt58) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:3291:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalSTAlgorithmParser.g:3291:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalSTAlgorithmParser.g:3292:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalSTAlgorithmParser.g:3292:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalSTAlgorithmParser.g:3293:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_30);
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

                            // InternalSTAlgorithmParser.g:3310:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop57:
                            do {
                                int alt57=2;
                                int LA57_0 = input.LA(1);

                                if ( (LA57_0==Comma) ) {
                                    alt57=1;
                                }


                                switch (alt57) {
                            	case 1 :
                            	    // InternalSTAlgorithmParser.g:3311:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalSTAlgorithmParser.g:3315:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalSTAlgorithmParser.g:3316:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalSTAlgorithmParser.g:3316:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalSTAlgorithmParser.g:3317:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_30);
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
                            	    break loop57;
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
    // InternalSTAlgorithmParser.g:3345:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalSTAlgorithmParser.g:3345:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalSTAlgorithmParser.g:3346:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalSTAlgorithmParser.g:3352:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_specifier_1_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3358:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) )
            // InternalSTAlgorithmParser.g:3359:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            {
            // InternalSTAlgorithmParser.g:3359:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            // InternalSTAlgorithmParser.g:3360:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            {
            // InternalSTAlgorithmParser.g:3360:3: ()
            // InternalSTAlgorithmParser.g:3361:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:3367:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( ((LA60_0>=B && LA60_0<=X)) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3368:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalSTAlgorithmParser.g:3368:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalSTAlgorithmParser.g:3369:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_60);
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

            // InternalSTAlgorithmParser.g:3386:3: ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==RULE_INT) ) {
                alt61=1;
            }
            else if ( (LA61_0==LeftParenthesis) ) {
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
                    // InternalSTAlgorithmParser.g:3387:4: ( (lv_index_2_0= RULE_INT ) )
                    {
                    // InternalSTAlgorithmParser.g:3387:4: ( (lv_index_2_0= RULE_INT ) )
                    // InternalSTAlgorithmParser.g:3388:5: (lv_index_2_0= RULE_INT )
                    {
                    // InternalSTAlgorithmParser.g:3388:5: (lv_index_2_0= RULE_INT )
                    // InternalSTAlgorithmParser.g:3389:6: lv_index_2_0= RULE_INT
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
                    // InternalSTAlgorithmParser.g:3406:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    {
                    // InternalSTAlgorithmParser.g:3406:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    // InternalSTAlgorithmParser.g:3407:5: otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
                      				
                    }
                    // InternalSTAlgorithmParser.g:3411:5: ( (lv_expression_4_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:3412:6: (lv_expression_4_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:3412:6: (lv_expression_4_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:3413:7: lv_expression_4_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_58);
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
    // InternalSTAlgorithmParser.g:3440:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTAlgorithmParser.g:3440:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTAlgorithmParser.g:3441:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTAlgorithmParser.g:3447:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
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
            // InternalSTAlgorithmParser.g:3453:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalSTAlgorithmParser.g:3454:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalSTAlgorithmParser.g:3454:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt62=6;
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
                alt62=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt62=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt62=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt62=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt62=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt62=6;
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
                    // InternalSTAlgorithmParser.g:3455:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalSTAlgorithmParser.g:3464:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalSTAlgorithmParser.g:3473:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalSTAlgorithmParser.g:3482:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalSTAlgorithmParser.g:3491:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalSTAlgorithmParser.g:3500:3: this_STStringLiteral_5= ruleSTStringLiteral
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
    // InternalSTAlgorithmParser.g:3512:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3512:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalSTAlgorithmParser.g:3513:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3519:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3525:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalSTAlgorithmParser.g:3526:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalSTAlgorithmParser.g:3526:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==DWORD||LA63_0==LWORD||(LA63_0>=BOOL && LA63_0<=BYTE)||LA63_0==WORD) ) {
                alt63=1;
            }
            else if ( (LA63_0==LREAL||(LA63_0>=UDINT && LA63_0<=ULINT)||LA63_0==USINT||LA63_0==DINT||LA63_0==LINT||(LA63_0>=REAL && LA63_0<=SINT)||LA63_0==UINT||LA63_0==INT) ) {
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
                    // InternalSTAlgorithmParser.g:3527:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalSTAlgorithmParser.g:3538:3: this_STAnyNumType_1= ruleSTAnyNumType
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
    // InternalSTAlgorithmParser.g:3552:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3552:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalSTAlgorithmParser.g:3553:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalSTAlgorithmParser.g:3559:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_3=null;
        AntlrDatatypeRuleToken lv_value_2_1 = null;

        AntlrDatatypeRuleToken lv_value_2_2 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3565:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTAlgorithmParser.g:3566:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTAlgorithmParser.g:3566:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) )
            // InternalSTAlgorithmParser.g:3567:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTAlgorithmParser.g:3567:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==DWORD||LA64_0==LREAL||(LA64_0>=LWORD && LA64_0<=ULINT)||LA64_0==USINT||(LA64_0>=BOOL && LA64_0<=BYTE)||LA64_0==DINT||LA64_0==LINT||(LA64_0>=REAL && LA64_0<=SINT)||(LA64_0>=UINT && LA64_0<=WORD)||LA64_0==INT) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3568:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTAlgorithmParser.g:3568:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalSTAlgorithmParser.g:3569:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalSTAlgorithmParser.g:3569:5: ( ruleSTNumericLiteralType )
                    // InternalSTAlgorithmParser.g:3570:6: ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_61);
                    ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_62); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:3589:3: ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) )
            // InternalSTAlgorithmParser.g:3590:4: ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) )
            {
            // InternalSTAlgorithmParser.g:3590:4: ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) )
            // InternalSTAlgorithmParser.g:3591:5: (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL )
            {
            // InternalSTAlgorithmParser.g:3591:5: (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL )
            int alt65=3;
            switch ( input.LA(1) ) {
            case FALSE:
            case TRUE:
                {
                alt65=1;
                }
                break;
            case PlusSign:
            case HyphenMinus:
            case RULE_INT:
                {
                alt65=2;
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
                    // InternalSTAlgorithmParser.g:3592:6: lv_value_2_1= ruleBoolLiteral
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
                    // InternalSTAlgorithmParser.g:3608:6: lv_value_2_2= ruleNumber
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
                    // InternalSTAlgorithmParser.g:3624:6: lv_value_2_3= RULE_NON_DECIMAL
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
    // InternalSTAlgorithmParser.g:3645:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3645:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalSTAlgorithmParser.g:3646:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3652:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3658:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalSTAlgorithmParser.g:3659:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalSTAlgorithmParser.g:3659:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt66=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt66=1;
                }
                break;
            case D:
                {
                alt66=2;
                }
                break;
            case LD:
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
                    // InternalSTAlgorithmParser.g:3660:3: this_STDateType_0= ruleSTDateType
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
                    // InternalSTAlgorithmParser.g:3671:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3677:3: kw= LD
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
    // InternalSTAlgorithmParser.g:3686:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3686:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalSTAlgorithmParser.g:3687:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalSTAlgorithmParser.g:3693:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3699:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalSTAlgorithmParser.g:3700:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalSTAlgorithmParser.g:3700:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalSTAlgorithmParser.g:3701:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalSTAlgorithmParser.g:3701:3: ( ( ruleSTDateLiteralType ) )
            // InternalSTAlgorithmParser.g:3702:4: ( ruleSTDateLiteralType )
            {
            // InternalSTAlgorithmParser.g:3702:4: ( ruleSTDateLiteralType )
            // InternalSTAlgorithmParser.g:3703:5: ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_61);
            ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:3721:3: ( (lv_value_2_0= ruleDate ) )
            // InternalSTAlgorithmParser.g:3722:4: (lv_value_2_0= ruleDate )
            {
            // InternalSTAlgorithmParser.g:3722:4: (lv_value_2_0= ruleDate )
            // InternalSTAlgorithmParser.g:3723:5: lv_value_2_0= ruleDate
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
    // InternalSTAlgorithmParser.g:3744:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3744:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalSTAlgorithmParser.g:3745:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3751:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3757:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalSTAlgorithmParser.g:3758:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalSTAlgorithmParser.g:3758:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt67=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt67=1;
                }
                break;
            case T:
                {
                alt67=2;
                }
                break;
            case LT:
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
                    // InternalSTAlgorithmParser.g:3759:3: this_STAnyDurationType_0= ruleSTAnyDurationType
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
                    // InternalSTAlgorithmParser.g:3770:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3776:3: kw= LT
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
    // InternalSTAlgorithmParser.g:3785:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3785:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalSTAlgorithmParser.g:3786:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalSTAlgorithmParser.g:3792:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3798:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalSTAlgorithmParser.g:3799:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalSTAlgorithmParser.g:3799:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalSTAlgorithmParser.g:3800:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalSTAlgorithmParser.g:3800:3: ( ( ruleSTTimeLiteralType ) )
            // InternalSTAlgorithmParser.g:3801:4: ( ruleSTTimeLiteralType )
            {
            // InternalSTAlgorithmParser.g:3801:4: ( ruleSTTimeLiteralType )
            // InternalSTAlgorithmParser.g:3802:5: ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_61);
            ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:3820:3: ( (lv_value_2_0= ruleTime ) )
            // InternalSTAlgorithmParser.g:3821:4: (lv_value_2_0= ruleTime )
            {
            // InternalSTAlgorithmParser.g:3821:4: (lv_value_2_0= ruleTime )
            // InternalSTAlgorithmParser.g:3822:5: lv_value_2_0= ruleTime
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
    // InternalSTAlgorithmParser.g:3843:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3843:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalSTAlgorithmParser.g:3844:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalSTAlgorithmParser.g:3850:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3856:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalSTAlgorithmParser.g:3857:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalSTAlgorithmParser.g:3857:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalSTAlgorithmParser.g:3858:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalSTAlgorithmParser.g:3858:3: ( ( ruleSTTimeOfDayType ) )
            // InternalSTAlgorithmParser.g:3859:4: ( ruleSTTimeOfDayType )
            {
            // InternalSTAlgorithmParser.g:3859:4: ( ruleSTTimeOfDayType )
            // InternalSTAlgorithmParser.g:3860:5: ruleSTTimeOfDayType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_61);
            ruleSTTimeOfDayType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:3878:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalSTAlgorithmParser.g:3879:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalSTAlgorithmParser.g:3879:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalSTAlgorithmParser.g:3880:5: lv_value_2_0= ruleTimeOfDay
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
    // InternalSTAlgorithmParser.g:3901:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3901:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalSTAlgorithmParser.g:3902:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalSTAlgorithmParser.g:3908:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3914:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalSTAlgorithmParser.g:3915:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalSTAlgorithmParser.g:3915:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalSTAlgorithmParser.g:3916:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalSTAlgorithmParser.g:3916:3: ( ( ruleSTDateAndTimeType ) )
            // InternalSTAlgorithmParser.g:3917:4: ( ruleSTDateAndTimeType )
            {
            // InternalSTAlgorithmParser.g:3917:4: ( ruleSTDateAndTimeType )
            // InternalSTAlgorithmParser.g:3918:5: ruleSTDateAndTimeType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_61);
            ruleSTDateAndTimeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:3936:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalSTAlgorithmParser.g:3937:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalSTAlgorithmParser.g:3937:4: (lv_value_2_0= ruleDateAndTime )
            // InternalSTAlgorithmParser.g:3938:5: lv_value_2_0= ruleDateAndTime
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
    // InternalSTAlgorithmParser.g:3959:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3959:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalSTAlgorithmParser.g:3960:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalSTAlgorithmParser.g:3966:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3972:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) )
            // InternalSTAlgorithmParser.g:3973:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            {
            // InternalSTAlgorithmParser.g:3973:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            // InternalSTAlgorithmParser.g:3974:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) )
            {
            // InternalSTAlgorithmParser.g:3974:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==WSTRING||LA68_0==STRING||LA68_0==WCHAR||LA68_0==CHAR) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3975:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTAlgorithmParser.g:3975:4: ( ( ruleSTAnyCharsType ) )
                    // InternalSTAlgorithmParser.g:3976:5: ( ruleSTAnyCharsType )
                    {
                    // InternalSTAlgorithmParser.g:3976:5: ( ruleSTAnyCharsType )
                    // InternalSTAlgorithmParser.g:3977:6: ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_61);
                    ruleSTAnyCharsType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:3996:3: ( (lv_value_2_0= RULE_STRING ) )
            // InternalSTAlgorithmParser.g:3997:4: (lv_value_2_0= RULE_STRING )
            {
            // InternalSTAlgorithmParser.g:3997:4: (lv_value_2_0= RULE_STRING )
            // InternalSTAlgorithmParser.g:3998:5: lv_value_2_0= RULE_STRING
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
    // InternalSTAlgorithmParser.g:4018:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalSTAlgorithmParser.g:4018:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalSTAlgorithmParser.g:4019:2: iv_ruleSTAnyType= ruleSTAnyType EOF
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
    // InternalSTAlgorithmParser.g:4025:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4031:2: ( (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalSTAlgorithmParser.g:4032:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalSTAlgorithmParser.g:4032:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==RULE_ID) ) {
                alt69=1;
            }
            else if ( ((LA69_0>=LDATE_AND_TIME && LA69_0<=DATE_AND_TIME)||(LA69_0>=LTIME_OF_DAY && LA69_0<=TIME_OF_DAY)||LA69_0==WSTRING||LA69_0==STRING||LA69_0==DWORD||(LA69_0>=LDATE && LA69_0<=ULINT)||(LA69_0>=USINT && LA69_0<=WCHAR)||(LA69_0>=BOOL && LA69_0<=BYTE)||(LA69_0>=CHAR && LA69_0<=DINT)||(LA69_0>=LINT && LA69_0<=SINT)||LA69_0==TIME||(LA69_0>=UINT && LA69_0<=WORD)||(LA69_0>=INT && LA69_0<=LDT)||LA69_0==TOD||LA69_0==DT) ) {
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
                    // InternalSTAlgorithmParser.g:4033:3: this_ID_0= RULE_ID
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
                    // InternalSTAlgorithmParser.g:4041:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
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
    // InternalSTAlgorithmParser.g:4055:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalSTAlgorithmParser.g:4055:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalSTAlgorithmParser.g:4056:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
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
    // InternalSTAlgorithmParser.g:4062:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4068:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalSTAlgorithmParser.g:4069:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalSTAlgorithmParser.g:4069:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt70=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt70=1;
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
                alt70=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt70=3;
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
                alt70=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
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
                    // InternalSTAlgorithmParser.g:4070:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalSTAlgorithmParser.g:4081:3: this_STAnyNumType_1= ruleSTAnyNumType
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
                    // InternalSTAlgorithmParser.g:4092:3: this_STAnyDurationType_2= ruleSTAnyDurationType
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
                    // InternalSTAlgorithmParser.g:4103:3: this_STAnyDateType_3= ruleSTAnyDateType
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
                    // InternalSTAlgorithmParser.g:4114:3: this_STAnyCharsType_4= ruleSTAnyCharsType
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
    // InternalSTAlgorithmParser.g:4128:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalSTAlgorithmParser.g:4128:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalSTAlgorithmParser.g:4129:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
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
    // InternalSTAlgorithmParser.g:4135:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4141:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalSTAlgorithmParser.g:4142:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalSTAlgorithmParser.g:4142:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt71=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt71=1;
                }
                break;
            case BYTE:
                {
                alt71=2;
                }
                break;
            case WORD:
                {
                alt71=3;
                }
                break;
            case DWORD:
                {
                alt71=4;
                }
                break;
            case LWORD:
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
                    // InternalSTAlgorithmParser.g:4143:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4149:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:4155:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:4161:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTAlgorithmParser.g:4167:3: kw= LWORD
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
    // InternalSTAlgorithmParser.g:4176:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalSTAlgorithmParser.g:4176:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalSTAlgorithmParser.g:4177:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
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
    // InternalSTAlgorithmParser.g:4183:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4189:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalSTAlgorithmParser.g:4190:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalSTAlgorithmParser.g:4190:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt72=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt72=1;
                }
                break;
            case INT:
                {
                alt72=2;
                }
                break;
            case DINT:
                {
                alt72=3;
                }
                break;
            case LINT:
                {
                alt72=4;
                }
                break;
            case USINT:
                {
                alt72=5;
                }
                break;
            case UINT:
                {
                alt72=6;
                }
                break;
            case UDINT:
                {
                alt72=7;
                }
                break;
            case ULINT:
                {
                alt72=8;
                }
                break;
            case REAL:
                {
                alt72=9;
                }
                break;
            case LREAL:
                {
                alt72=10;
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
                    // InternalSTAlgorithmParser.g:4191:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4197:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:4203:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:4209:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTAlgorithmParser.g:4215:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTAlgorithmParser.g:4221:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTAlgorithmParser.g:4227:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTAlgorithmParser.g:4233:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTAlgorithmParser.g:4239:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSTAlgorithmParser.g:4245:3: kw= LREAL
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
    // InternalSTAlgorithmParser.g:4254:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalSTAlgorithmParser.g:4254:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalSTAlgorithmParser.g:4255:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
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
    // InternalSTAlgorithmParser.g:4261:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4267:2: ( (kw= TIME | kw= LTIME ) )
            // InternalSTAlgorithmParser.g:4268:2: (kw= TIME | kw= LTIME )
            {
            // InternalSTAlgorithmParser.g:4268:2: (kw= TIME | kw= LTIME )
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==TIME) ) {
                alt73=1;
            }
            else if ( (LA73_0==LTIME) ) {
                alt73=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }
            switch (alt73) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4269:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4275:3: kw= LTIME
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
    // InternalSTAlgorithmParser.g:4284:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalSTAlgorithmParser.g:4284:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalSTAlgorithmParser.g:4285:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
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
    // InternalSTAlgorithmParser.g:4291:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4297:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalSTAlgorithmParser.g:4298:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalSTAlgorithmParser.g:4298:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt74=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt74=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt74=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt74=3;
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
                    // InternalSTAlgorithmParser.g:4299:3: this_STDateType_0= ruleSTDateType
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
                    // InternalSTAlgorithmParser.g:4310:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
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
                    // InternalSTAlgorithmParser.g:4321:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
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
    // InternalSTAlgorithmParser.g:4335:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalSTAlgorithmParser.g:4335:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalSTAlgorithmParser.g:4336:2: iv_ruleSTDateType= ruleSTDateType EOF
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
    // InternalSTAlgorithmParser.g:4342:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4348:2: ( (kw= DATE | kw= LDATE ) )
            // InternalSTAlgorithmParser.g:4349:2: (kw= DATE | kw= LDATE )
            {
            // InternalSTAlgorithmParser.g:4349:2: (kw= DATE | kw= LDATE )
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==DATE) ) {
                alt75=1;
            }
            else if ( (LA75_0==LDATE) ) {
                alt75=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }
            switch (alt75) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4350:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4356:3: kw= LDATE
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
    // InternalSTAlgorithmParser.g:4365:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalSTAlgorithmParser.g:4365:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalSTAlgorithmParser.g:4366:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
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
    // InternalSTAlgorithmParser.g:4372:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4378:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalSTAlgorithmParser.g:4379:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalSTAlgorithmParser.g:4379:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt76=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt76=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt76=2;
                }
                break;
            case TOD:
                {
                alt76=3;
                }
                break;
            case LTOD:
                {
                alt76=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4380:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4386:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:4392:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:4398:3: kw= LTOD
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
    // InternalSTAlgorithmParser.g:4407:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalSTAlgorithmParser.g:4407:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalSTAlgorithmParser.g:4408:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
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
    // InternalSTAlgorithmParser.g:4414:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4420:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalSTAlgorithmParser.g:4421:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalSTAlgorithmParser.g:4421:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt77=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt77=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt77=2;
                }
                break;
            case DT:
                {
                alt77=3;
                }
                break;
            case LDT:
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
                    // InternalSTAlgorithmParser.g:4422:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4428:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:4434:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:4440:3: kw= LDT
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
    // InternalSTAlgorithmParser.g:4449:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalSTAlgorithmParser.g:4449:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalSTAlgorithmParser.g:4450:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
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
    // InternalSTAlgorithmParser.g:4456:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4462:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalSTAlgorithmParser.g:4463:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalSTAlgorithmParser.g:4463:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt78=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt78=1;
                }
                break;
            case WSTRING:
                {
                alt78=2;
                }
                break;
            case CHAR:
                {
                alt78=3;
                }
                break;
            case WCHAR:
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
                    // InternalSTAlgorithmParser.g:4464:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4470:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:4476:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:4482:3: kw= WCHAR
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
    // InternalSTAlgorithmParser.g:4491:1: entryRuleBoolLiteral returns [String current=null] : iv_ruleBoolLiteral= ruleBoolLiteral EOF ;
    public final String entryRuleBoolLiteral() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoolLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:4491:51: (iv_ruleBoolLiteral= ruleBoolLiteral EOF )
            // InternalSTAlgorithmParser.g:4492:2: iv_ruleBoolLiteral= ruleBoolLiteral EOF
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
    // InternalSTAlgorithmParser.g:4498:1: ruleBoolLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE ) ;
    public final AntlrDatatypeRuleToken ruleBoolLiteral() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4504:2: ( (kw= TRUE | kw= FALSE ) )
            // InternalSTAlgorithmParser.g:4505:2: (kw= TRUE | kw= FALSE )
            {
            // InternalSTAlgorithmParser.g:4505:2: (kw= TRUE | kw= FALSE )
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==TRUE) ) {
                alt79=1;
            }
            else if ( (LA79_0==FALSE) ) {
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
                    // InternalSTAlgorithmParser.g:4506:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getBoolLiteralAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4512:3: kw= FALSE
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
    // InternalSTAlgorithmParser.g:4521:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;


        try {
            // InternalSTAlgorithmParser.g:4521:46: (iv_ruleNumber= ruleNumber EOF )
            // InternalSTAlgorithmParser.g:4522:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalSTAlgorithmParser.g:4528:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_EXT_INT_4=null;
        Token this_INT_5=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4534:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) )
            // InternalSTAlgorithmParser.g:4535:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            {
            // InternalSTAlgorithmParser.g:4535:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            // InternalSTAlgorithmParser.g:4536:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            {
            // InternalSTAlgorithmParser.g:4536:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt80=3;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==PlusSign) ) {
                alt80=1;
            }
            else if ( (LA80_0==HyphenMinus) ) {
                alt80=2;
            }
            switch (alt80) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4537:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4543:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_66); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1());
              		
            }
            // InternalSTAlgorithmParser.g:4556:3: ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==FullStop) ) {
                int LA82_1 = input.LA(2);

                if ( (LA82_1==RULE_INT) ) {
                    int LA82_3 = input.LA(3);

                    if ( (synpred5_InternalSTAlgorithmParser()) ) {
                        alt82=1;
                    }
                }
                else if ( (LA82_1==RULE_EXT_INT) && (synpred5_InternalSTAlgorithmParser())) {
                    alt82=1;
                }
            }
            switch (alt82) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4557:4: ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    {
                    // InternalSTAlgorithmParser.g:4557:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTAlgorithmParser.g:4558:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:4565:4: (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==RULE_EXT_INT) ) {
                        alt81=1;
                    }
                    else if ( (LA81_0==RULE_INT) ) {
                        alt81=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 81, 0, input);

                        throw nvae;
                    }
                    switch (alt81) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:4566:5: this_EXT_INT_4= RULE_EXT_INT
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
                            // InternalSTAlgorithmParser.g:4574:5: this_INT_5= RULE_INT
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
    // InternalSTAlgorithmParser.g:4587:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalSTAlgorithmParser.g:4587:44: (iv_ruleDate= ruleDate EOF )
            // InternalSTAlgorithmParser.g:4588:2: iv_ruleDate= ruleDate EOF
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
    // InternalSTAlgorithmParser.g:4594:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4600:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTAlgorithmParser.g:4601:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTAlgorithmParser.g:4601:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTAlgorithmParser.g:4602:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_63); if (state.failed) return current;
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
    // InternalSTAlgorithmParser.g:4637:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalSTAlgorithmParser.g:4637:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalSTAlgorithmParser.g:4638:2: iv_ruleDateAndTime= ruleDateAndTime EOF
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
    // InternalSTAlgorithmParser.g:4644:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) ;
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
            // InternalSTAlgorithmParser.g:4650:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) )
            // InternalSTAlgorithmParser.g:4651:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            {
            // InternalSTAlgorithmParser.g:4651:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            // InternalSTAlgorithmParser.g:4652:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_5());
              		
            }
            this_INT_6=(Token)match(input,RULE_INT,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_6);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_6, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_6());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_7());
              		
            }
            this_INT_8=(Token)match(input,RULE_INT,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_8);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_8, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_8());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_9());
              		
            }
            this_INT_10=(Token)match(input,RULE_INT,FOLLOW_66); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_10);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
              		
            }
            // InternalSTAlgorithmParser.g:4719:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==FullStop) ) {
                int LA83_1 = input.LA(2);

                if ( (LA83_1==RULE_INT) ) {
                    int LA83_3 = input.LA(3);

                    if ( (synpred6_InternalSTAlgorithmParser()) ) {
                        alt83=1;
                    }
                }
            }
            switch (alt83) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4720:4: ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT
                    {
                    // InternalSTAlgorithmParser.g:4720:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTAlgorithmParser.g:4721:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_63); if (state.failed) return current;
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
    // InternalSTAlgorithmParser.g:4740:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalSTAlgorithmParser.g:4740:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalSTAlgorithmParser.g:4741:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalSTAlgorithmParser.g:4747:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4753:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) )
            // InternalSTAlgorithmParser.g:4754:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            {
            // InternalSTAlgorithmParser.g:4754:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            // InternalSTAlgorithmParser.g:4755:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_66); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTAlgorithmParser.g:4786:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==FullStop) ) {
                int LA84_1 = input.LA(2);

                if ( (LA84_1==RULE_INT) ) {
                    int LA84_3 = input.LA(3);

                    if ( (synpred7_InternalSTAlgorithmParser()) ) {
                        alt84=1;
                    }
                }
            }
            switch (alt84) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4787:4: ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT
                    {
                    // InternalSTAlgorithmParser.g:4787:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTAlgorithmParser.g:4788:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_63); if (state.failed) return current;
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
    // InternalSTAlgorithmParser.g:4807:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;


        try {
            // InternalSTAlgorithmParser.g:4807:44: (iv_ruleTime= ruleTime EOF )
            // InternalSTAlgorithmParser.g:4808:2: iv_ruleTime= ruleTime EOF
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
    // InternalSTAlgorithmParser.g:4814:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Number_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4820:2: ( (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ )
            // InternalSTAlgorithmParser.g:4821:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            {
            // InternalSTAlgorithmParser.g:4821:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            int cnt87=0;
            loop87:
            do {
                int alt87=2;
                switch ( input.LA(1) ) {
                case PlusSign:
                    {
                    int LA87_2 = input.LA(2);

                    if ( (LA87_2==RULE_INT) ) {
                        int LA87_5 = input.LA(3);

                        if ( (LA87_5==FullStop) ) {
                            int LA87_6 = input.LA(4);

                            if ( (LA87_6==RULE_EXT_INT) ) {
                                int LA87_7 = input.LA(5);

                                if ( ((LA87_7>=MS && LA87_7<=NS)||LA87_7==US||(LA87_7>=D && LA87_7<=S)) ) {
                                    alt87=1;
                                }


                            }
                            else if ( (LA87_6==RULE_INT) ) {
                                int LA87_8 = input.LA(5);

                                if ( ((LA87_8>=MS && LA87_8<=NS)||LA87_8==US||(LA87_8>=D && LA87_8<=S)) ) {
                                    alt87=1;
                                }


                            }


                        }
                        else if ( ((LA87_5>=MS && LA87_5<=NS)||LA87_5==US||(LA87_5>=D && LA87_5<=S)) ) {
                            alt87=1;
                        }


                    }


                    }
                    break;
                case HyphenMinus:
                    {
                    int LA87_3 = input.LA(2);

                    if ( (LA87_3==RULE_INT) ) {
                        int LA87_5 = input.LA(3);

                        if ( (LA87_5==FullStop) ) {
                            int LA87_6 = input.LA(4);

                            if ( (LA87_6==RULE_EXT_INT) ) {
                                int LA87_7 = input.LA(5);

                                if ( ((LA87_7>=MS && LA87_7<=NS)||LA87_7==US||(LA87_7>=D && LA87_7<=S)) ) {
                                    alt87=1;
                                }


                            }
                            else if ( (LA87_6==RULE_INT) ) {
                                int LA87_8 = input.LA(5);

                                if ( ((LA87_8>=MS && LA87_8<=NS)||LA87_8==US||(LA87_8>=D && LA87_8<=S)) ) {
                                    alt87=1;
                                }


                            }


                        }
                        else if ( ((LA87_5>=MS && LA87_5<=NS)||LA87_5==US||(LA87_5>=D && LA87_5<=S)) ) {
                            alt87=1;
                        }


                    }


                    }
                    break;
                case RULE_INT:
                    {
                    alt87=1;
                    }
                    break;

                }

                switch (alt87) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:4822:3: this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )?
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getTimeAccess().getNumberParserRuleCall_0());
            	      		
            	    }
            	    pushFollow(FOLLOW_69);
            	    this_Number_0=ruleNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_Number_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }
            	    // InternalSTAlgorithmParser.g:4832:3: (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS )
            	    int alt85=7;
            	    switch ( input.LA(1) ) {
            	    case D:
            	        {
            	        alt85=1;
            	        }
            	        break;
            	    case H:
            	        {
            	        alt85=2;
            	        }
            	        break;
            	    case M:
            	        {
            	        alt85=3;
            	        }
            	        break;
            	    case S:
            	        {
            	        alt85=4;
            	        }
            	        break;
            	    case MS:
            	        {
            	        alt85=5;
            	        }
            	        break;
            	    case US:
            	        {
            	        alt85=6;
            	        }
            	        break;
            	    case NS:
            	        {
            	        alt85=7;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 85, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt85) {
            	        case 1 :
            	            // InternalSTAlgorithmParser.g:4833:4: kw= D
            	            {
            	            kw=(Token)match(input,D,FOLLOW_70); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getDKeyword_1_0());
            	              			
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTAlgorithmParser.g:4839:4: kw= H
            	            {
            	            kw=(Token)match(input,H,FOLLOW_70); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getHKeyword_1_1());
            	              			
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTAlgorithmParser.g:4845:4: kw= M
            	            {
            	            kw=(Token)match(input,M,FOLLOW_70); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMKeyword_1_2());
            	              			
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTAlgorithmParser.g:4851:4: kw= S
            	            {
            	            kw=(Token)match(input,S,FOLLOW_70); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getSKeyword_1_3());
            	              			
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalSTAlgorithmParser.g:4857:4: kw= MS
            	            {
            	            kw=(Token)match(input,MS,FOLLOW_70); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMSKeyword_1_4());
            	              			
            	            }

            	            }
            	            break;
            	        case 6 :
            	            // InternalSTAlgorithmParser.g:4863:4: kw= US
            	            {
            	            kw=(Token)match(input,US,FOLLOW_70); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getUSKeyword_1_5());
            	              			
            	            }

            	            }
            	            break;
            	        case 7 :
            	            // InternalSTAlgorithmParser.g:4869:4: kw= NS
            	            {
            	            kw=(Token)match(input,NS,FOLLOW_70); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getNSKeyword_1_6());
            	              			
            	            }

            	            }
            	            break;

            	    }

            	    // InternalSTAlgorithmParser.g:4875:3: (kw= KW__ )?
            	    int alt86=2;
            	    int LA86_0 = input.LA(1);

            	    if ( (LA86_0==KW__) ) {
            	        alt86=1;
            	    }
            	    switch (alt86) {
            	        case 1 :
            	            // InternalSTAlgorithmParser.g:4876:4: kw= KW__
            	            {
            	            kw=(Token)match(input,KW__,FOLLOW_71); if (state.failed) return current;
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
            	    if ( cnt87 >= 1 ) break loop87;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(87, input);
                        throw eee;
                }
                cnt87++;
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
    // InternalSTAlgorithmParser.g:4886:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4892:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTAlgorithmParser.g:4893:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTAlgorithmParser.g:4893:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTAlgorithmParser.g:4894:3: enumLiteral_0= FullStopFullStop
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
    // InternalSTAlgorithmParser.g:4903:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4909:2: ( (enumLiteral_0= OR ) )
            // InternalSTAlgorithmParser.g:4910:2: (enumLiteral_0= OR )
            {
            // InternalSTAlgorithmParser.g:4910:2: (enumLiteral_0= OR )
            // InternalSTAlgorithmParser.g:4911:3: enumLiteral_0= OR
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
    // InternalSTAlgorithmParser.g:4920:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4926:2: ( (enumLiteral_0= XOR ) )
            // InternalSTAlgorithmParser.g:4927:2: (enumLiteral_0= XOR )
            {
            // InternalSTAlgorithmParser.g:4927:2: (enumLiteral_0= XOR )
            // InternalSTAlgorithmParser.g:4928:3: enumLiteral_0= XOR
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
    // InternalSTAlgorithmParser.g:4937:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4943:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTAlgorithmParser.g:4944:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTAlgorithmParser.g:4944:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==AND) ) {
                alt88=1;
            }
            else if ( (LA88_0==Ampersand) ) {
                alt88=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4945:3: (enumLiteral_0= AND )
                    {
                    // InternalSTAlgorithmParser.g:4945:3: (enumLiteral_0= AND )
                    // InternalSTAlgorithmParser.g:4946:4: enumLiteral_0= AND
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
                    // InternalSTAlgorithmParser.g:4953:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTAlgorithmParser.g:4953:3: (enumLiteral_1= Ampersand )
                    // InternalSTAlgorithmParser.g:4954:4: enumLiteral_1= Ampersand
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
    // InternalSTAlgorithmParser.g:4964:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4970:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTAlgorithmParser.g:4971:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTAlgorithmParser.g:4971:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==EqualsSign) ) {
                alt89=1;
            }
            else if ( (LA89_0==LessThanSignGreaterThanSign) ) {
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
                    // InternalSTAlgorithmParser.g:4972:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:4972:3: (enumLiteral_0= EqualsSign )
                    // InternalSTAlgorithmParser.g:4973:4: enumLiteral_0= EqualsSign
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
                    // InternalSTAlgorithmParser.g:4980:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTAlgorithmParser.g:4980:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTAlgorithmParser.g:4981:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalSTAlgorithmParser.g:4991:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4997:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTAlgorithmParser.g:4998:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTAlgorithmParser.g:4998:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt90=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt90=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt90=2;
                }
                break;
            case GreaterThanSign:
                {
                alt90=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt90=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }

            switch (alt90) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4999:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTAlgorithmParser.g:4999:3: (enumLiteral_0= LessThanSign )
                    // InternalSTAlgorithmParser.g:5000:4: enumLiteral_0= LessThanSign
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
                    // InternalSTAlgorithmParser.g:5007:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:5007:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTAlgorithmParser.g:5008:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalSTAlgorithmParser.g:5015:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTAlgorithmParser.g:5015:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTAlgorithmParser.g:5016:4: enumLiteral_2= GreaterThanSign
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
                    // InternalSTAlgorithmParser.g:5023:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:5023:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTAlgorithmParser.g:5024:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalSTAlgorithmParser.g:5034:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:5040:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTAlgorithmParser.g:5041:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTAlgorithmParser.g:5041:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==PlusSign) ) {
                alt91=1;
            }
            else if ( (LA91_0==HyphenMinus) ) {
                alt91=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }
            switch (alt91) {
                case 1 :
                    // InternalSTAlgorithmParser.g:5042:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTAlgorithmParser.g:5042:3: (enumLiteral_0= PlusSign )
                    // InternalSTAlgorithmParser.g:5043:4: enumLiteral_0= PlusSign
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
                    // InternalSTAlgorithmParser.g:5050:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTAlgorithmParser.g:5050:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTAlgorithmParser.g:5051:4: enumLiteral_1= HyphenMinus
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
    // InternalSTAlgorithmParser.g:5061:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:5067:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTAlgorithmParser.g:5068:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTAlgorithmParser.g:5068:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt92=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt92=1;
                }
                break;
            case Solidus:
                {
                alt92=2;
                }
                break;
            case MOD:
                {
                alt92=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }

            switch (alt92) {
                case 1 :
                    // InternalSTAlgorithmParser.g:5069:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTAlgorithmParser.g:5069:3: (enumLiteral_0= Asterisk )
                    // InternalSTAlgorithmParser.g:5070:4: enumLiteral_0= Asterisk
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
                    // InternalSTAlgorithmParser.g:5077:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTAlgorithmParser.g:5077:3: (enumLiteral_1= Solidus )
                    // InternalSTAlgorithmParser.g:5078:4: enumLiteral_1= Solidus
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
                    // InternalSTAlgorithmParser.g:5085:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTAlgorithmParser.g:5085:3: (enumLiteral_2= MOD )
                    // InternalSTAlgorithmParser.g:5086:4: enumLiteral_2= MOD
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
    // InternalSTAlgorithmParser.g:5096:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:5102:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTAlgorithmParser.g:5103:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTAlgorithmParser.g:5103:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTAlgorithmParser.g:5104:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalSTAlgorithmParser.g:5113:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:5119:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTAlgorithmParser.g:5120:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTAlgorithmParser.g:5120:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt93=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt93=1;
                }
                break;
            case PlusSign:
                {
                alt93=2;
                }
                break;
            case NOT:
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
                    // InternalSTAlgorithmParser.g:5121:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTAlgorithmParser.g:5121:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTAlgorithmParser.g:5122:4: enumLiteral_0= HyphenMinus
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
                    // InternalSTAlgorithmParser.g:5129:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTAlgorithmParser.g:5129:3: (enumLiteral_1= PlusSign )
                    // InternalSTAlgorithmParser.g:5130:4: enumLiteral_1= PlusSign
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
                    // InternalSTAlgorithmParser.g:5137:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTAlgorithmParser.g:5137:3: (enumLiteral_2= NOT )
                    // InternalSTAlgorithmParser.g:5138:4: enumLiteral_2= NOT
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
    // InternalSTAlgorithmParser.g:5148:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:5154:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalSTAlgorithmParser.g:5155:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalSTAlgorithmParser.g:5155:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt94=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt94=1;
                }
                break;
            case D_1:
                {
                alt94=2;
                }
                break;
            case W:
                {
                alt94=3;
                }
                break;
            case B:
                {
                alt94=4;
                }
                break;
            case X:
                {
                alt94=5;
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
                    // InternalSTAlgorithmParser.g:5156:3: (enumLiteral_0= L )
                    {
                    // InternalSTAlgorithmParser.g:5156:3: (enumLiteral_0= L )
                    // InternalSTAlgorithmParser.g:5157:4: enumLiteral_0= L
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
                    // InternalSTAlgorithmParser.g:5164:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTAlgorithmParser.g:5164:3: (enumLiteral_1= D_1 )
                    // InternalSTAlgorithmParser.g:5165:4: enumLiteral_1= D_1
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
                    // InternalSTAlgorithmParser.g:5172:3: (enumLiteral_2= W )
                    {
                    // InternalSTAlgorithmParser.g:5172:3: (enumLiteral_2= W )
                    // InternalSTAlgorithmParser.g:5173:4: enumLiteral_2= W
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
                    // InternalSTAlgorithmParser.g:5180:3: (enumLiteral_3= B )
                    {
                    // InternalSTAlgorithmParser.g:5180:3: (enumLiteral_3= B )
                    // InternalSTAlgorithmParser.g:5181:4: enumLiteral_3= B
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
                    // InternalSTAlgorithmParser.g:5188:3: (enumLiteral_4= X )
                    {
                    // InternalSTAlgorithmParser.g:5188:3: (enumLiteral_4= X )
                    // InternalSTAlgorithmParser.g:5189:4: enumLiteral_4= X
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

    // $ANTLR start synpred1_InternalSTAlgorithmParser
    public final void synpred1_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:1212:6: ( ruleSTAssignmentStatement )
        // InternalSTAlgorithmParser.g:1212:7: ruleSTAssignmentStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTAssignmentStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_InternalSTAlgorithmParser

    // $ANTLR start synpred2_InternalSTAlgorithmParser
    public final void synpred2_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:1917:4: ( ( ruleSTStatement ) )
        // InternalSTAlgorithmParser.g:1917:5: ( ruleSTStatement )
        {
        // InternalSTAlgorithmParser.g:1917:5: ( ruleSTStatement )
        // InternalSTAlgorithmParser.g:1918:5: ruleSTStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSTAlgorithmParser

    // $ANTLR start synpred3_InternalSTAlgorithmParser
    public final void synpred3_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:2989:4: ( ruleSTAccessExpression )
        // InternalSTAlgorithmParser.g:2989:5: ruleSTAccessExpression
        {
        pushFollow(FOLLOW_2);
        ruleSTAccessExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalSTAlgorithmParser

    // $ANTLR start synpred4_InternalSTAlgorithmParser
    public final void synpred4_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:3284:5: ( LeftParenthesis )
        // InternalSTAlgorithmParser.g:3284:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_InternalSTAlgorithmParser

    // $ANTLR start synpred5_InternalSTAlgorithmParser
    public final void synpred5_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:4558:5: ( FullStop )
        // InternalSTAlgorithmParser.g:4558:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_InternalSTAlgorithmParser

    // $ANTLR start synpred6_InternalSTAlgorithmParser
    public final void synpred6_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:4721:5: ( FullStop )
        // InternalSTAlgorithmParser.g:4721:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_InternalSTAlgorithmParser

    // $ANTLR start synpred7_InternalSTAlgorithmParser
    public final void synpred7_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:4788:5: ( FullStop )
        // InternalSTAlgorithmParser.g:4788:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_InternalSTAlgorithmParser

    // Delegated rules

    public final boolean synpred1_InternalSTAlgorithmParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalSTAlgorithmParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalSTAlgorithmParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalSTAlgorithmParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalSTAlgorithmParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalSTAlgorithmParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalSTAlgorithmParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalSTAlgorithmParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalSTAlgorithmParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalSTAlgorithmParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalSTAlgorithmParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalSTAlgorithmParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_InternalSTAlgorithmParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalSTAlgorithmParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA26 dfa26 = new DFA26(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA52 dfa52 = new DFA52(this);
    protected DFA59 dfa59 = new DFA59(this);
    static final String dfa_1s = "\67\uffff";
    static final String dfa_2s = "\1\4\5\uffff\54\0\5\uffff";
    static final String dfa_3s = "\1\170\5\uffff\54\0\5\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\54\uffff\1\10\1\11\1\12\1\6\1\7";
    static final String dfa_5s = "\6\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\5\uffff}>";
    static final String[] dfa_6s = {
            "\1\52\1\51\1\uffff\1\46\1\45\7\uffff\1\63\4\uffff\1\56\2\uffff\1\5\1\62\1\55\1\uffff\1\13\1\uffff\1\30\1\36\1\26\1\42\1\14\1\23\1\24\1\uffff\1\21\1\60\1\4\1\10\1\11\1\2\1\57\1\35\1\17\1\uffff\1\64\1\20\1\50\1\25\1\15\1\uffff\1\41\1\27\1\22\1\12\1\uffff\1\3\1\16\1\54\2\uffff\1\47\21\uffff\1\53\1\1\1\40\1\44\10\uffff\1\6\2\uffff\1\31\1\uffff\1\32\7\uffff\1\37\3\uffff\1\43\4\uffff\1\34\1\33\1\uffff\1\7\1\61",
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

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "1165:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_6 = input.LA(1);

                         
                        int index26_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_7 = input.LA(1);

                         
                        int index26_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA26_8 = input.LA(1);

                         
                        int index26_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA26_9 = input.LA(1);

                         
                        int index26_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_9);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA26_10 = input.LA(1);

                         
                        int index26_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA26_11 = input.LA(1);

                         
                        int index26_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_11);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA26_12 = input.LA(1);

                         
                        int index26_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_12);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA26_13 = input.LA(1);

                         
                        int index26_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_13);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA26_14 = input.LA(1);

                         
                        int index26_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_14);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA26_15 = input.LA(1);

                         
                        int index26_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_15);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA26_16 = input.LA(1);

                         
                        int index26_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_16);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA26_17 = input.LA(1);

                         
                        int index26_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_17);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA26_18 = input.LA(1);

                         
                        int index26_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_18);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA26_19 = input.LA(1);

                         
                        int index26_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_19);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA26_20 = input.LA(1);

                         
                        int index26_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_20);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA26_21 = input.LA(1);

                         
                        int index26_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_21);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA26_22 = input.LA(1);

                         
                        int index26_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_22);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA26_23 = input.LA(1);

                         
                        int index26_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_23);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA26_24 = input.LA(1);

                         
                        int index26_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_24);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA26_25 = input.LA(1);

                         
                        int index26_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_25);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA26_26 = input.LA(1);

                         
                        int index26_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_26);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA26_27 = input.LA(1);

                         
                        int index26_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_27);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA26_28 = input.LA(1);

                         
                        int index26_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_28);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA26_29 = input.LA(1);

                         
                        int index26_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_29);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA26_30 = input.LA(1);

                         
                        int index26_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_30);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA26_31 = input.LA(1);

                         
                        int index26_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_31);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA26_32 = input.LA(1);

                         
                        int index26_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_32);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA26_33 = input.LA(1);

                         
                        int index26_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_33);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA26_34 = input.LA(1);

                         
                        int index26_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_34);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA26_35 = input.LA(1);

                         
                        int index26_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_35);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA26_36 = input.LA(1);

                         
                        int index26_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_36);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA26_37 = input.LA(1);

                         
                        int index26_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_37);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA26_38 = input.LA(1);

                         
                        int index26_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_38);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA26_39 = input.LA(1);

                         
                        int index26_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_39);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA26_40 = input.LA(1);

                         
                        int index26_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_40);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA26_41 = input.LA(1);

                         
                        int index26_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_41);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA26_42 = input.LA(1);

                         
                        int index26_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_42);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA26_43 = input.LA(1);

                         
                        int index26_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_43);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA26_44 = input.LA(1);

                         
                        int index26_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_44);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA26_45 = input.LA(1);

                         
                        int index26_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_45);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA26_46 = input.LA(1);

                         
                        int index26_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_46);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA26_47 = input.LA(1);

                         
                        int index26_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_47);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA26_48 = input.LA(1);

                         
                        int index26_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_48);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA26_49 = input.LA(1);

                         
                        int index26_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 53;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index26_49);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 26, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_7s = "\72\uffff";
    static final String dfa_8s = "\1\1\71\uffff";
    static final String dfa_9s = "\1\4\2\uffff\54\0\13\uffff";
    static final String dfa_10s = "\1\170\2\uffff\54\0\13\uffff";
    static final String dfa_11s = "\1\uffff\1\2\57\uffff\11\1";
    static final String dfa_12s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\13\uffff}>";
    static final String[] dfa_13s = {
            "\1\47\1\46\1\uffff\1\43\1\42\7\uffff\1\67\1\1\3\uffff\1\53\2\uffff\1\65\1\66\1\52\1\uffff\1\10\1\uffff\1\25\1\33\1\23\1\37\1\11\1\20\1\21\1\uffff\1\16\1\55\1\64\1\5\1\6\1\62\1\54\1\32\1\14\1\1\1\70\1\15\1\45\1\22\1\12\1\uffff\1\36\1\24\1\17\1\7\1\uffff\1\63\1\13\1\51\1\uffff\1\1\1\44\21\uffff\1\50\1\61\1\35\1\41\10\uffff\1\3\2\uffff\1\26\1\uffff\1\27\3\uffff\1\71\3\uffff\1\34\3\uffff\1\40\4\uffff\1\31\1\30\1\uffff\1\4\1\56",
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

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "()* loopback of 1916:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA37_0 = input.LA(1);

                         
                        int index37_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_0==EOF||LA37_0==END_CASE||LA37_0==ELSE||LA37_0==NOT) ) {s = 1;}

                        else if ( (LA37_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA37_0==RULE_ID) ) {s = 4;}

                        else if ( (LA37_0==BOOL) ) {s = 5;}

                        else if ( (LA37_0==BYTE) ) {s = 6;}

                        else if ( (LA37_0==WORD) ) {s = 7;}

                        else if ( (LA37_0==DWORD) ) {s = 8;}

                        else if ( (LA37_0==LWORD) ) {s = 9;}

                        else if ( (LA37_0==SINT) ) {s = 10;}

                        else if ( (LA37_0==INT) ) {s = 11;}

                        else if ( (LA37_0==DINT) ) {s = 12;}

                        else if ( (LA37_0==LINT) ) {s = 13;}

                        else if ( (LA37_0==USINT) ) {s = 14;}

                        else if ( (LA37_0==UINT) ) {s = 15;}

                        else if ( (LA37_0==UDINT) ) {s = 16;}

                        else if ( (LA37_0==ULINT) ) {s = 17;}

                        else if ( (LA37_0==REAL) ) {s = 18;}

                        else if ( (LA37_0==LREAL) ) {s = 19;}

                        else if ( (LA37_0==TRUE) ) {s = 20;}

                        else if ( (LA37_0==FALSE) ) {s = 21;}

                        else if ( (LA37_0==PlusSign) ) {s = 22;}

                        else if ( (LA37_0==HyphenMinus) ) {s = 23;}

                        else if ( (LA37_0==RULE_INT) ) {s = 24;}

                        else if ( (LA37_0==RULE_NON_DECIMAL) ) {s = 25;}

                        else if ( (LA37_0==DATE) ) {s = 26;}

                        else if ( (LA37_0==LDATE) ) {s = 27;}

                        else if ( (LA37_0==D) ) {s = 28;}

                        else if ( (LA37_0==LD) ) {s = 29;}

                        else if ( (LA37_0==TIME) ) {s = 30;}

                        else if ( (LA37_0==LTIME) ) {s = 31;}

                        else if ( (LA37_0==T) ) {s = 32;}

                        else if ( (LA37_0==LT) ) {s = 33;}

                        else if ( (LA37_0==TIME_OF_DAY) ) {s = 34;}

                        else if ( (LA37_0==LTIME_OF_DAY) ) {s = 35;}

                        else if ( (LA37_0==TOD) ) {s = 36;}

                        else if ( (LA37_0==LTOD) ) {s = 37;}

                        else if ( (LA37_0==DATE_AND_TIME) ) {s = 38;}

                        else if ( (LA37_0==LDATE_AND_TIME) ) {s = 39;}

                        else if ( (LA37_0==DT) ) {s = 40;}

                        else if ( (LA37_0==LDT) ) {s = 41;}

                        else if ( (LA37_0==STRING) ) {s = 42;}

                        else if ( (LA37_0==WSTRING) ) {s = 43;}

                        else if ( (LA37_0==CHAR) ) {s = 44;}

                        else if ( (LA37_0==WCHAR) ) {s = 45;}

                        else if ( (LA37_0==RULE_STRING) ) {s = 46;}

                        else if ( (LA37_0==IF) && (synpred2_InternalSTAlgorithmParser())) {s = 49;}

                        else if ( (LA37_0==CASE) && (synpred2_InternalSTAlgorithmParser())) {s = 50;}

                        else if ( (LA37_0==FOR) && (synpred2_InternalSTAlgorithmParser())) {s = 51;}

                        else if ( (LA37_0==WHILE) && (synpred2_InternalSTAlgorithmParser())) {s = 52;}

                        else if ( (LA37_0==REPEAT) && (synpred2_InternalSTAlgorithmParser())) {s = 53;}

                        else if ( (LA37_0==RETURN) && (synpred2_InternalSTAlgorithmParser())) {s = 54;}

                        else if ( (LA37_0==CONTINUE) && (synpred2_InternalSTAlgorithmParser())) {s = 55;}

                        else if ( (LA37_0==EXIT) && (synpred2_InternalSTAlgorithmParser())) {s = 56;}

                        else if ( (LA37_0==Semicolon) && (synpred2_InternalSTAlgorithmParser())) {s = 57;}

                         
                        input.seek(index37_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA37_3 = input.LA(1);

                         
                        int index37_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA37_4 = input.LA(1);

                         
                        int index37_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA37_5 = input.LA(1);

                         
                        int index37_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA37_6 = input.LA(1);

                         
                        int index37_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA37_7 = input.LA(1);

                         
                        int index37_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA37_8 = input.LA(1);

                         
                        int index37_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA37_9 = input.LA(1);

                         
                        int index37_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA37_10 = input.LA(1);

                         
                        int index37_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA37_11 = input.LA(1);

                         
                        int index37_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA37_12 = input.LA(1);

                         
                        int index37_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA37_13 = input.LA(1);

                         
                        int index37_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA37_14 = input.LA(1);

                         
                        int index37_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA37_15 = input.LA(1);

                         
                        int index37_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA37_16 = input.LA(1);

                         
                        int index37_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA37_17 = input.LA(1);

                         
                        int index37_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA37_18 = input.LA(1);

                         
                        int index37_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA37_19 = input.LA(1);

                         
                        int index37_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA37_20 = input.LA(1);

                         
                        int index37_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA37_21 = input.LA(1);

                         
                        int index37_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA37_22 = input.LA(1);

                         
                        int index37_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA37_23 = input.LA(1);

                         
                        int index37_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA37_24 = input.LA(1);

                         
                        int index37_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA37_25 = input.LA(1);

                         
                        int index37_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA37_26 = input.LA(1);

                         
                        int index37_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA37_27 = input.LA(1);

                         
                        int index37_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA37_28 = input.LA(1);

                         
                        int index37_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA37_29 = input.LA(1);

                         
                        int index37_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA37_30 = input.LA(1);

                         
                        int index37_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA37_31 = input.LA(1);

                         
                        int index37_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA37_32 = input.LA(1);

                         
                        int index37_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA37_33 = input.LA(1);

                         
                        int index37_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA37_34 = input.LA(1);

                         
                        int index37_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA37_35 = input.LA(1);

                         
                        int index37_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA37_36 = input.LA(1);

                         
                        int index37_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA37_37 = input.LA(1);

                         
                        int index37_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA37_38 = input.LA(1);

                         
                        int index37_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA37_39 = input.LA(1);

                         
                        int index37_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA37_40 = input.LA(1);

                         
                        int index37_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA37_41 = input.LA(1);

                         
                        int index37_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA37_42 = input.LA(1);

                         
                        int index37_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA37_43 = input.LA(1);

                         
                        int index37_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA37_44 = input.LA(1);

                         
                        int index37_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA37_45 = input.LA(1);

                         
                        int index37_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA37_46 = input.LA(1);

                         
                        int index37_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_46);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 37, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_14s = "\56\uffff";
    static final String dfa_15s = "\1\4\23\uffff\2\0\30\uffff";
    static final String dfa_16s = "\1\170\23\uffff\2\0\30\uffff";
    static final String dfa_17s = "\1\uffff\23\1\2\uffff\27\1\1\2";
    static final String dfa_18s = "\1\2\23\uffff\1\1\1\0\30\uffff}>";
    static final String[] dfa_19s = {
            "\1\45\1\44\1\uffff\1\41\1\40\14\uffff\1\51\4\uffff\1\50\1\uffff\1\6\1\uffff\1\23\1\31\1\21\1\35\1\7\1\16\1\17\1\uffff\1\14\1\53\1\uffff\1\3\1\4\1\uffff\1\52\1\30\1\12\2\uffff\1\13\1\43\1\20\1\10\1\uffff\1\34\1\22\1\15\1\5\2\uffff\1\11\1\47\1\uffff\1\55\1\42\21\uffff\1\46\1\uffff\1\33\1\37\10\uffff\1\1\2\uffff\1\24\1\uffff\1\25\7\uffff\1\32\3\uffff\1\36\4\uffff\1\27\1\26\1\uffff\1\2\1\54",
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

    class DFA52 extends DFA {

        public DFA52(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = dfa_14;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "2987:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA52_21 = input.LA(1);

                         
                        int index52_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTAlgorithmParser()) ) {s = 44;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index52_21);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA52_20 = input.LA(1);

                         
                        int index52_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTAlgorithmParser()) ) {s = 44;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index52_20);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA52_0 = input.LA(1);

                         
                        int index52_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA52_0==LeftParenthesis) && (synpred3_InternalSTAlgorithmParser())) {s = 1;}

                        else if ( (LA52_0==RULE_ID) && (synpred3_InternalSTAlgorithmParser())) {s = 2;}

                        else if ( (LA52_0==BOOL) && (synpred3_InternalSTAlgorithmParser())) {s = 3;}

                        else if ( (LA52_0==BYTE) && (synpred3_InternalSTAlgorithmParser())) {s = 4;}

                        else if ( (LA52_0==WORD) && (synpred3_InternalSTAlgorithmParser())) {s = 5;}

                        else if ( (LA52_0==DWORD) && (synpred3_InternalSTAlgorithmParser())) {s = 6;}

                        else if ( (LA52_0==LWORD) && (synpred3_InternalSTAlgorithmParser())) {s = 7;}

                        else if ( (LA52_0==SINT) && (synpred3_InternalSTAlgorithmParser())) {s = 8;}

                        else if ( (LA52_0==INT) && (synpred3_InternalSTAlgorithmParser())) {s = 9;}

                        else if ( (LA52_0==DINT) && (synpred3_InternalSTAlgorithmParser())) {s = 10;}

                        else if ( (LA52_0==LINT) && (synpred3_InternalSTAlgorithmParser())) {s = 11;}

                        else if ( (LA52_0==USINT) && (synpred3_InternalSTAlgorithmParser())) {s = 12;}

                        else if ( (LA52_0==UINT) && (synpred3_InternalSTAlgorithmParser())) {s = 13;}

                        else if ( (LA52_0==UDINT) && (synpred3_InternalSTAlgorithmParser())) {s = 14;}

                        else if ( (LA52_0==ULINT) && (synpred3_InternalSTAlgorithmParser())) {s = 15;}

                        else if ( (LA52_0==REAL) && (synpred3_InternalSTAlgorithmParser())) {s = 16;}

                        else if ( (LA52_0==LREAL) && (synpred3_InternalSTAlgorithmParser())) {s = 17;}

                        else if ( (LA52_0==TRUE) && (synpred3_InternalSTAlgorithmParser())) {s = 18;}

                        else if ( (LA52_0==FALSE) && (synpred3_InternalSTAlgorithmParser())) {s = 19;}

                        else if ( (LA52_0==PlusSign) ) {s = 20;}

                        else if ( (LA52_0==HyphenMinus) ) {s = 21;}

                        else if ( (LA52_0==RULE_INT) && (synpred3_InternalSTAlgorithmParser())) {s = 22;}

                        else if ( (LA52_0==RULE_NON_DECIMAL) && (synpred3_InternalSTAlgorithmParser())) {s = 23;}

                        else if ( (LA52_0==DATE) && (synpred3_InternalSTAlgorithmParser())) {s = 24;}

                        else if ( (LA52_0==LDATE) && (synpred3_InternalSTAlgorithmParser())) {s = 25;}

                        else if ( (LA52_0==D) && (synpred3_InternalSTAlgorithmParser())) {s = 26;}

                        else if ( (LA52_0==LD) && (synpred3_InternalSTAlgorithmParser())) {s = 27;}

                        else if ( (LA52_0==TIME) && (synpred3_InternalSTAlgorithmParser())) {s = 28;}

                        else if ( (LA52_0==LTIME) && (synpred3_InternalSTAlgorithmParser())) {s = 29;}

                        else if ( (LA52_0==T) && (synpred3_InternalSTAlgorithmParser())) {s = 30;}

                        else if ( (LA52_0==LT) && (synpred3_InternalSTAlgorithmParser())) {s = 31;}

                        else if ( (LA52_0==TIME_OF_DAY) && (synpred3_InternalSTAlgorithmParser())) {s = 32;}

                        else if ( (LA52_0==LTIME_OF_DAY) && (synpred3_InternalSTAlgorithmParser())) {s = 33;}

                        else if ( (LA52_0==TOD) && (synpred3_InternalSTAlgorithmParser())) {s = 34;}

                        else if ( (LA52_0==LTOD) && (synpred3_InternalSTAlgorithmParser())) {s = 35;}

                        else if ( (LA52_0==DATE_AND_TIME) && (synpred3_InternalSTAlgorithmParser())) {s = 36;}

                        else if ( (LA52_0==LDATE_AND_TIME) && (synpred3_InternalSTAlgorithmParser())) {s = 37;}

                        else if ( (LA52_0==DT) && (synpred3_InternalSTAlgorithmParser())) {s = 38;}

                        else if ( (LA52_0==LDT) && (synpred3_InternalSTAlgorithmParser())) {s = 39;}

                        else if ( (LA52_0==STRING) && (synpred3_InternalSTAlgorithmParser())) {s = 40;}

                        else if ( (LA52_0==WSTRING) && (synpred3_InternalSTAlgorithmParser())) {s = 41;}

                        else if ( (LA52_0==CHAR) && (synpred3_InternalSTAlgorithmParser())) {s = 42;}

                        else if ( (LA52_0==WCHAR) && (synpred3_InternalSTAlgorithmParser())) {s = 43;}

                        else if ( (LA52_0==RULE_STRING) && (synpred3_InternalSTAlgorithmParser())) {s = 44;}

                        else if ( (LA52_0==NOT) ) {s = 45;}

                         
                        input.seek(index52_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 52, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_20s = "\43\uffff";
    static final String dfa_21s = "\1\2\42\uffff";
    static final String dfa_22s = "\1\12\1\0\41\uffff";
    static final String dfa_23s = "\1\161\1\0\41\uffff";
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

    class DFA59 extends DFA {

        public DFA59(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 59;
            this.eot = dfa_20;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "3282:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA59_1 = input.LA(1);

                         
                        int index59_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSTAlgorithmParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index59_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 59, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000801002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x3BDF7FDFD72501F0L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x3BDF7FDFD72501B2L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x3BDF7FDFD72101B2L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x3BDF7FDFD7254BB0L,0x01B088CA403C0001L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x335E76DF942001B0L,0x0080000000040001L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x3BDF7FDFD7254BB0L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x3BDF7FDFD72549B2L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000108000L,0x0080000000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000100000L,0x0080000000000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000004000008000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x335E76DF9C2001B0L,0x0080000000040001L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0xB3DE76DFD42001B0L,0x01B0880A40340001L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0002000400000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000000L,0x0001008000000400L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000400L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0xB3DE76DFD42001B0L,0x01B1880A40340001L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000480000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x3BDFFFDFF76101B0L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000800020400000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0xB3DEF6DFD42201B0L,0x01B0880A40340001L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0000004400000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x3BDF7FDFD72901B0L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x3BDF7FDFD72121B0L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x3BDF7FFFD72101B0L,0x01B0888A403C0001L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0400000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000020000001000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000050000004800L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0000000A00000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x4000000000000002L,0x0000002100000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000002L,0x0001001000000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x00A00000400000F8L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0xB3DE76DFD42001B0L,0x01B0880AC0340001L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0020000040000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0080000040000000L,0x0030000A00000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0020000A00000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0060000000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000780008C00000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000002L,0x0024000A00000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000002L,0x0020000A00000000L});

}