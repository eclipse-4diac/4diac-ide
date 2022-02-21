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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "END_ALGORITHM", "LTIME_OF_DAY", "TIME_OF_DAY", "END_REPEAT", "VAR_OUTPUT", "ALGORITHM", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "VAR_TEMP", "END_FOR", "END_VAR", "WSTRING", "END_IF", "REPEAT", "RETURN", "STRING", "ARRAY", "DWORD", "ELSIF", "FALSE", "LDATE", "LREAL", "LTIME", "LWORD", "UDINT", "ULINT", "UNTIL", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "LINT", "LTOD", "REAL", "SINT", "THEN", "TIME", "TRUE", "UINT", "WORD", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "TOD", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "MS", "NS", "OF", "OR", "TO", "US", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "H", "M", "S", "T", "LeftSquareBracket", "RightSquareBracket", "KW__", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_WSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=32;
    public static final int LessThanSignGreaterThanSign=74;
    public static final int EqualsSignGreaterThanSign=75;
    public static final int VAR=63;
    public static final int END_IF=21;
    public static final int ULINT=34;
    public static final int END_CASE=16;
    public static final int LessThanSign=102;
    public static final int LeftParenthesis=92;
    public static final int BYTE=40;
    public static final int ELSE=45;
    public static final int IF=81;
    public static final int LINT=47;
    public static final int GreaterThanSign=104;
    public static final int WORD=55;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=117;
    public static final int TOD=62;
    public static final int DINT=44;
    public static final int UDINT=33;
    public static final int CASE=41;
    public static final int GreaterThanSignEqualsSign=76;
    public static final int END_ALGORITHM=6;
    public static final int AT=77;
    public static final int PlusSign=95;
    public static final int RULE_INT=115;
    public static final int END_FOR=18;
    public static final int RULE_ML_COMMENT=120;
    public static final int THEN=51;
    public static final int XOR=64;
    public static final int LeftSquareBracket=110;
    public static final int EXIT=46;
    public static final int TIME_OF_DAY=8;
    public static final int B=65;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=22;
    public static final int D=105;
    public static final int H=106;
    public static final int CHAR=42;
    public static final int L=67;
    public static final int M=107;
    public static final int LTIME=31;
    public static final int Comma=96;
    public static final int HyphenMinus=97;
    public static final int S=108;
    public static final int T=109;
    public static final int W=68;
    public static final int BY=78;
    public static final int X=69;
    public static final int ELSIF=27;
    public static final int END_REPEAT=9;
    public static final int LessThanSignEqualsSign=73;
    public static final int Solidus=99;
    public static final int VAR_INPUT=13;
    public static final int FullStop=98;
    public static final int VAR_TEMP=17;
    public static final int CONSTANT=14;
    public static final int KW__=112;
    public static final int CONTINUE=15;
    public static final int Semicolon=101;
    public static final int LD=82;
    public static final int VAR_OUTPUT=10;
    public static final int STRING=24;
    public static final int RULE_HEX_DIGIT=113;
    public static final int TO=88;
    public static final int UINT=54;
    public static final int LTOD=48;
    public static final int ARRAY=25;
    public static final int LT=83;
    public static final int DO=79;
    public static final int WSTRING=20;
    public static final int DT=80;
    public static final int END_VAR=19;
    public static final int FullStopFullStop=71;
    public static final int Ampersand=91;
    public static final int US=89;
    public static final int RightSquareBracket=111;
    public static final int USINT=36;
    public static final int MS=84;
    public static final int DWORD=26;
    public static final int FOR=57;
    public static final int RightParenthesis=93;
    public static final int TRUE=53;
    public static final int ColonEqualsSign=72;
    public static final int RULE_WSTRING=119;
    public static final int END_WHILE=12;
    public static final int DATE=43;
    public static final int NOT=61;
    public static final int LDATE=29;
    public static final int AND=56;
    public static final int NumberSign=90;
    public static final int REAL=49;
    public static final int AsteriskAsterisk=70;
    public static final int SINT=50;
    public static final int LTIME_OF_DAY=7;
    public static final int LREAL=30;
    public static final int WCHAR=37;
    public static final int NS=85;
    public static final int ALGORITHM=11;
    public static final int RULE_STRING=118;
    public static final int INT=58;
    public static final int RULE_SL_COMMENT=121;
    public static final int RETURN=23;
    public static final int EqualsSign=103;
    public static final int OF=86;
    public static final int Colon=100;
    public static final int EOF=-1;
    public static final int LDT=59;
    public static final int Asterisk=94;
    public static final int MOD=60;
    public static final int OR=87;
    public static final int RULE_WS=122;
    public static final int RULE_EXT_INT=116;
    public static final int TIME=52;
    public static final int RULE_ANY_OTHER=123;
    public static final int UNTIL=35;
    public static final int BOOL=39;
    public static final int D_1=66;
    public static final int FALSE=28;
    public static final int WHILE=38;
    public static final int RULE_NON_DECIMAL=114;

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
        	return "STAlgorithms";
       	}

       	@Override
       	protected STAlgorithmGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleSTAlgorithms"
    // InternalSTAlgorithmParser.g:58:1: entryRuleSTAlgorithms returns [EObject current=null] : iv_ruleSTAlgorithms= ruleSTAlgorithms EOF ;
    public final EObject entryRuleSTAlgorithms() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAlgorithms = null;


        try {
            // InternalSTAlgorithmParser.g:58:53: (iv_ruleSTAlgorithms= ruleSTAlgorithms EOF )
            // InternalSTAlgorithmParser.g:59:2: iv_ruleSTAlgorithms= ruleSTAlgorithms EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAlgorithmsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAlgorithms=ruleSTAlgorithms();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAlgorithms; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAlgorithms"


    // $ANTLR start "ruleSTAlgorithms"
    // InternalSTAlgorithmParser.g:65:1: ruleSTAlgorithms returns [EObject current=null] : ( () ( (lv_algorithms_1_0= ruleSTAlgorithm ) )* ) ;
    public final EObject ruleSTAlgorithms() throws RecognitionException {
        EObject current = null;

        EObject lv_algorithms_1_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:71:2: ( ( () ( (lv_algorithms_1_0= ruleSTAlgorithm ) )* ) )
            // InternalSTAlgorithmParser.g:72:2: ( () ( (lv_algorithms_1_0= ruleSTAlgorithm ) )* )
            {
            // InternalSTAlgorithmParser.g:72:2: ( () ( (lv_algorithms_1_0= ruleSTAlgorithm ) )* )
            // InternalSTAlgorithmParser.g:73:3: () ( (lv_algorithms_1_0= ruleSTAlgorithm ) )*
            {
            // InternalSTAlgorithmParser.g:73:3: ()
            // InternalSTAlgorithmParser.g:74:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTAlgorithmsAccess().getSTAlgorithmsAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:80:3: ( (lv_algorithms_1_0= ruleSTAlgorithm ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ALGORITHM) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:81:4: (lv_algorithms_1_0= ruleSTAlgorithm )
            	    {
            	    // InternalSTAlgorithmParser.g:81:4: (lv_algorithms_1_0= ruleSTAlgorithm )
            	    // InternalSTAlgorithmParser.g:82:5: lv_algorithms_1_0= ruleSTAlgorithm
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTAlgorithmsAccess().getAlgorithmsSTAlgorithmParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_algorithms_1_0=ruleSTAlgorithm();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTAlgorithmsRule());
            	      					}
            	      					add(
            	      						current,
            	      						"algorithms",
            	      						lv_algorithms_1_0,
            	      						"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm.STAlgorithm");
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
    // $ANTLR end "ruleSTAlgorithms"


    // $ANTLR start "entryRuleSTAlgorithm"
    // InternalSTAlgorithmParser.g:103:1: entryRuleSTAlgorithm returns [EObject current=null] : iv_ruleSTAlgorithm= ruleSTAlgorithm EOF ;
    public final EObject entryRuleSTAlgorithm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAlgorithm = null;


        try {
            // InternalSTAlgorithmParser.g:103:52: (iv_ruleSTAlgorithm= ruleSTAlgorithm EOF )
            // InternalSTAlgorithmParser.g:104:2: iv_ruleSTAlgorithm= ruleSTAlgorithm EOF
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
    // InternalSTAlgorithmParser.g:110:1: ruleSTAlgorithm returns [EObject current=null] : (otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM ) ;
    public final EObject ruleSTAlgorithm() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:116:2: ( (otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM ) )
            // InternalSTAlgorithmParser.g:117:2: (otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM )
            {
            // InternalSTAlgorithmParser.g:117:2: (otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM )
            // InternalSTAlgorithmParser.g:118:3: otherlv_0= ALGORITHM ( (lv_name_1_0= RULE_ID ) ) ( (lv_body_2_0= ruleSTAlgorithmBody ) ) otherlv_3= END_ALGORITHM
            {
            otherlv_0=(Token)match(input,ALGORITHM,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTAlgorithmAccess().getALGORITHMKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:122:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTAlgorithmParser.g:123:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:123:4: (lv_name_1_0= RULE_ID )
            // InternalSTAlgorithmParser.g:124:5: lv_name_1_0= RULE_ID
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

            // InternalSTAlgorithmParser.g:140:3: ( (lv_body_2_0= ruleSTAlgorithmBody ) )
            // InternalSTAlgorithmParser.g:141:4: (lv_body_2_0= ruleSTAlgorithmBody )
            {
            // InternalSTAlgorithmParser.g:141:4: (lv_body_2_0= ruleSTAlgorithmBody )
            // InternalSTAlgorithmParser.g:142:5: lv_body_2_0= ruleSTAlgorithmBody
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
    // InternalSTAlgorithmParser.g:167:1: entryRuleSTAlgorithmBody returns [EObject current=null] : iv_ruleSTAlgorithmBody= ruleSTAlgorithmBody EOF ;
    public final EObject entryRuleSTAlgorithmBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAlgorithmBody = null;


        try {
            // InternalSTAlgorithmParser.g:167:56: (iv_ruleSTAlgorithmBody= ruleSTAlgorithmBody EOF )
            // InternalSTAlgorithmParser.g:168:2: iv_ruleSTAlgorithmBody= ruleSTAlgorithmBody EOF
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
    // InternalSTAlgorithmParser.g:174:1: ruleSTAlgorithmBody returns [EObject current=null] : ( () ( (lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTAlgorithmBody() throws RecognitionException {
        EObject current = null;

        EObject lv_varTempDeclarations_1_0 = null;

        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:180:2: ( ( () ( (lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:181:2: ( () ( (lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:181:2: ( () ( (lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:182:3: () ( (lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock ) )* ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:182:3: ()
            // InternalSTAlgorithmParser.g:183:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTAlgorithmBodyAccess().getSTAlgorithmBodyAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:189:3: ( (lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==VAR_TEMP) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:190:4: (lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock )
            	    {
            	    // InternalSTAlgorithmParser.g:190:4: (lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock )
            	    // InternalSTAlgorithmParser.g:191:5: lv_varTempDeclarations_1_0= ruleVarTempDeclarationBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTAlgorithmBodyAccess().getVarTempDeclarationsVarTempDeclarationBlockParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_7);
            	    lv_varTempDeclarations_1_0=ruleVarTempDeclarationBlock();

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
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.VarTempDeclarationBlock");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:208:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=LDATE_AND_TIME && LA3_0<=DATE_AND_TIME)||(LA3_0>=LTIME_OF_DAY && LA3_0<=TIME_OF_DAY)||LA3_0==CONTINUE||LA3_0==WSTRING||(LA3_0>=REPEAT && LA3_0<=STRING)||LA3_0==DWORD||(LA3_0>=FALSE && LA3_0<=ULINT)||(LA3_0>=USINT && LA3_0<=DINT)||(LA3_0>=EXIT && LA3_0<=SINT)||(LA3_0>=TIME && LA3_0<=WORD)||(LA3_0>=FOR && LA3_0<=LDT)||LA3_0==TOD||(LA3_0>=DT && LA3_0<=LT)||LA3_0==LeftParenthesis||LA3_0==PlusSign||LA3_0==HyphenMinus||LA3_0==Semicolon||LA3_0==D||LA3_0==T||(LA3_0>=RULE_NON_DECIMAL && LA3_0<=RULE_INT)||(LA3_0>=RULE_ID && LA3_0<=RULE_WSTRING)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:209:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:209:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:210:5: lv_statements_2_0= ruleSTStatement
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
            	    break loop3;
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


    // $ANTLR start "entryRuleVarTempDeclarationBlock"
    // InternalSTAlgorithmParser.g:231:1: entryRuleVarTempDeclarationBlock returns [EObject current=null] : iv_ruleVarTempDeclarationBlock= ruleVarTempDeclarationBlock EOF ;
    public final EObject entryRuleVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarTempDeclarationBlock = null;


        try {
            // InternalSTAlgorithmParser.g:231:64: (iv_ruleVarTempDeclarationBlock= ruleVarTempDeclarationBlock EOF )
            // InternalSTAlgorithmParser.g:232:2: iv_ruleVarTempDeclarationBlock= ruleVarTempDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVarTempDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVarTempDeclarationBlock=ruleVarTempDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVarTempDeclarationBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVarTempDeclarationBlock"


    // $ANTLR start "ruleVarTempDeclarationBlock"
    // InternalSTAlgorithmParser.g:238:1: ruleVarTempDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:244:2: ( ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTAlgorithmParser.g:245:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTAlgorithmParser.g:245:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTAlgorithmParser.g:246:3: () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTAlgorithmParser.g:246:3: ()
            // InternalSTAlgorithmParser.g:247:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVarTempDeclarationBlockAccess().getVarDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_TEMP,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getVarTempDeclarationBlockAccess().getVAR_TEMPKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:257:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==CONSTANT) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalSTAlgorithmParser.g:258:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTAlgorithmParser.g:258:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTAlgorithmParser.g:259:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getVarTempDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getVarTempDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:271:3: ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==RULE_ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:272:4: (lv_varDeclarations_3_0= ruleVarDeclaration )
            	    {
            	    // InternalSTAlgorithmParser.g:272:4: (lv_varDeclarations_3_0= ruleVarDeclaration )
            	    // InternalSTAlgorithmParser.g:273:5: lv_varDeclarations_3_0= ruleVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getVarTempDeclarationBlockAccess().getVarDeclarationsVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_varDeclarations_3_0=ruleVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getVarTempDeclarationBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"varDeclarations",
            	      						lv_varDeclarations_3_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.VarDeclaration");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getVarTempDeclarationBlockAccess().getEND_VARKeyword_4());
              		
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
    // $ANTLR end "ruleVarTempDeclarationBlock"


    // $ANTLR start "entryRuleVarDeclaration"
    // InternalSTAlgorithmParser.g:298:1: entryRuleVarDeclaration returns [EObject current=null] : iv_ruleVarDeclaration= ruleVarDeclaration EOF ;
    public final EObject entryRuleVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarDeclaration = null;


        try {
            // InternalSTAlgorithmParser.g:298:55: (iv_ruleVarDeclaration= ruleVarDeclaration EOF )
            // InternalSTAlgorithmParser.g:299:2: iv_ruleVarDeclaration= ruleVarDeclaration EOF
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
    // InternalSTAlgorithmParser.g:305:1: ruleVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon ) ;
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
            // InternalSTAlgorithmParser.g:311:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon ) )
            // InternalSTAlgorithmParser.g:312:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon )
            {
            // InternalSTAlgorithmParser.g:312:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon )
            // InternalSTAlgorithmParser.g:313:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon
            {
            // InternalSTAlgorithmParser.g:313:3: ()
            // InternalSTAlgorithmParser.g:314:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:320:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTAlgorithmParser.g:321:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:321:4: (lv_name_1_0= RULE_ID )
            // InternalSTAlgorithmParser.g:322:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); if (state.failed) return current;
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

            // InternalSTAlgorithmParser.g:338:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==AT) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSTAlgorithmParser.g:339:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:343:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSTAlgorithmParser.g:344:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSTAlgorithmParser.g:344:5: (otherlv_3= RULE_ID )
                    // InternalSTAlgorithmParser.g:345:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Colon,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getVarDeclarationAccess().getColonKeyword_3());
              		
            }
            // InternalSTAlgorithmParser.g:361:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ARRAY) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalSTAlgorithmParser.g:362:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalSTAlgorithmParser.g:362:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalSTAlgorithmParser.g:363:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalSTAlgorithmParser.g:363:5: (lv_array_5_0= ARRAY )
                    // InternalSTAlgorithmParser.g:364:6: lv_array_5_0= ARRAY
                    {
                    lv_array_5_0=(Token)match(input,ARRAY,FOLLOW_14); if (state.failed) return current;
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

                    // InternalSTAlgorithmParser.g:376:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==LeftSquareBracket) ) {
                        int LA9_1 = input.LA(2);

                        if ( ((LA9_1>=LDATE_AND_TIME && LA9_1<=DATE_AND_TIME)||(LA9_1>=LTIME_OF_DAY && LA9_1<=TIME_OF_DAY)||LA9_1==WSTRING||LA9_1==STRING||LA9_1==DWORD||(LA9_1>=FALSE && LA9_1<=ULINT)||(LA9_1>=USINT && LA9_1<=WCHAR)||(LA9_1>=BOOL && LA9_1<=BYTE)||(LA9_1>=CHAR && LA9_1<=DINT)||(LA9_1>=LINT && LA9_1<=SINT)||(LA9_1>=TIME && LA9_1<=WORD)||(LA9_1>=INT && LA9_1<=LDT)||(LA9_1>=NOT && LA9_1<=TOD)||LA9_1==DT||(LA9_1>=LD && LA9_1<=LT)||LA9_1==LeftParenthesis||LA9_1==PlusSign||LA9_1==HyphenMinus||LA9_1==D||LA9_1==T||(LA9_1>=RULE_NON_DECIMAL && LA9_1<=RULE_INT)||(LA9_1>=RULE_ID && LA9_1<=RULE_WSTRING)) ) {
                            alt9=1;
                        }
                        else if ( (LA9_1==Asterisk) ) {
                            alt9=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 9, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 0, input);

                        throw nvae;
                    }
                    switch (alt9) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:377:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalSTAlgorithmParser.g:377:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalSTAlgorithmParser.g:378:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalSTAlgorithmParser.g:382:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalSTAlgorithmParser.g:383:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalSTAlgorithmParser.g:383:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalSTAlgorithmParser.g:384:8: lv_ranges_7_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_16);
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

                            // InternalSTAlgorithmParser.g:401:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( (LA7_0==Comma) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // InternalSTAlgorithmParser.g:402:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTAlgorithmParser.g:406:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalSTAlgorithmParser.g:407:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalSTAlgorithmParser.g:407:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalSTAlgorithmParser.g:408:9: lv_ranges_9_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_16);
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
                            	    break loop7;
                                }
                            } while (true);

                            otherlv_10=(Token)match(input,RightSquareBracket,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTAlgorithmParser.g:432:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalSTAlgorithmParser.g:432:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalSTAlgorithmParser.g:433:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalSTAlgorithmParser.g:437:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalSTAlgorithmParser.g:438:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalSTAlgorithmParser.g:438:7: (lv_count_12_0= Asterisk )
                            // InternalSTAlgorithmParser.g:439:8: lv_count_12_0= Asterisk
                            {
                            lv_count_12_0=(Token)match(input,Asterisk,FOLLOW_16); if (state.failed) return current;
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

                            // InternalSTAlgorithmParser.g:451:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop8:
                            do {
                                int alt8=2;
                                int LA8_0 = input.LA(1);

                                if ( (LA8_0==Comma) ) {
                                    alt8=1;
                                }


                                switch (alt8) {
                            	case 1 :
                            	    // InternalSTAlgorithmParser.g:452:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_18); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTAlgorithmParser.g:456:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalSTAlgorithmParser.g:457:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalSTAlgorithmParser.g:457:8: (lv_count_14_0= Asterisk )
                            	    // InternalSTAlgorithmParser.g:458:9: lv_count_14_0= Asterisk
                            	    {
                            	    lv_count_14_0=(Token)match(input,Asterisk,FOLLOW_16); if (state.failed) return current;
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
                            	    break loop8;
                                }
                            } while (true);

                            otherlv_15=(Token)match(input,RightSquareBracket,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_15, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,OF,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:482:3: ( ( ruleSTAnyType ) )
            // InternalSTAlgorithmParser.g:483:4: ( ruleSTAnyType )
            {
            // InternalSTAlgorithmParser.g:483:4: ( ruleSTAnyType )
            // InternalSTAlgorithmParser.g:484:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVarDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVarDeclarationAccess().getTypeINamedElementCrossReference_5_0());
              				
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

            // InternalSTAlgorithmParser.g:498:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==LeftSquareBracket) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSTAlgorithmParser.g:499:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:503:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:504:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:504:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:505:6: lv_maxLength_19_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_20);
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

                    otherlv_20=(Token)match(input,RightSquareBracket,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:527:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ColonEqualsSign) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalSTAlgorithmParser.g:528:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:532:4: ( (lv_defaultValue_22_0= ruleInitializerExpression ) )
                    // InternalSTAlgorithmParser.g:533:5: (lv_defaultValue_22_0= ruleInitializerExpression )
                    {
                    // InternalSTAlgorithmParser.g:533:5: (lv_defaultValue_22_0= ruleInitializerExpression )
                    // InternalSTAlgorithmParser.g:534:6: lv_defaultValue_22_0= ruleInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVarDeclarationAccess().getDefaultValueInitializerExpressionParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_23);
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
    // InternalSTAlgorithmParser.g:560:1: entryRuleInitializerExpression returns [EObject current=null] : iv_ruleInitializerExpression= ruleInitializerExpression EOF ;
    public final EObject entryRuleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitializerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:560:62: (iv_ruleInitializerExpression= ruleInitializerExpression EOF )
            // InternalSTAlgorithmParser.g:561:2: iv_ruleInitializerExpression= ruleInitializerExpression EOF
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
    // InternalSTAlgorithmParser.g:567:1: ruleInitializerExpression returns [EObject current=null] : (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) ;
    public final EObject ruleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STExpression_0 = null;

        EObject this_ArrayInitializerExpression_1 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:573:2: ( (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) )
            // InternalSTAlgorithmParser.g:574:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            {
            // InternalSTAlgorithmParser.g:574:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=LDATE_AND_TIME && LA13_0<=DATE_AND_TIME)||(LA13_0>=LTIME_OF_DAY && LA13_0<=TIME_OF_DAY)||LA13_0==WSTRING||LA13_0==STRING||LA13_0==DWORD||(LA13_0>=FALSE && LA13_0<=ULINT)||(LA13_0>=USINT && LA13_0<=WCHAR)||(LA13_0>=BOOL && LA13_0<=BYTE)||(LA13_0>=CHAR && LA13_0<=DINT)||(LA13_0>=LINT && LA13_0<=SINT)||(LA13_0>=TIME && LA13_0<=WORD)||(LA13_0>=INT && LA13_0<=LDT)||(LA13_0>=NOT && LA13_0<=TOD)||LA13_0==DT||(LA13_0>=LD && LA13_0<=LT)||LA13_0==LeftParenthesis||LA13_0==PlusSign||LA13_0==HyphenMinus||LA13_0==D||LA13_0==T||(LA13_0>=RULE_NON_DECIMAL && LA13_0<=RULE_INT)||(LA13_0>=RULE_ID && LA13_0<=RULE_WSTRING)) ) {
                alt13=1;
            }
            else if ( (LA13_0==LeftSquareBracket) ) {
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
                    // InternalSTAlgorithmParser.g:575:3: this_STExpression_0= ruleSTExpression
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
                    // InternalSTAlgorithmParser.g:584:3: this_ArrayInitializerExpression_1= ruleArrayInitializerExpression
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
    // InternalSTAlgorithmParser.g:596:1: entryRuleArrayInitializerExpression returns [EObject current=null] : iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF ;
    public final EObject entryRuleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitializerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:596:67: (iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF )
            // InternalSTAlgorithmParser.g:597:2: iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF
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
    // InternalSTAlgorithmParser.g:603:1: ruleArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:609:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTAlgorithmParser.g:610:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTAlgorithmParser.g:610:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTAlgorithmParser.g:611:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:615:3: ( (lv_values_1_0= ruleArrayInitElement ) )
            // InternalSTAlgorithmParser.g:616:4: (lv_values_1_0= ruleArrayInitElement )
            {
            // InternalSTAlgorithmParser.g:616:4: (lv_values_1_0= ruleArrayInitElement )
            // InternalSTAlgorithmParser.g:617:5: lv_values_1_0= ruleArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrayInitializerExpressionAccess().getValuesArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_16);
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

            // InternalSTAlgorithmParser.g:634:3: (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==Comma) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:635:4: otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTAlgorithmParser.g:639:4: ( (lv_values_3_0= ruleArrayInitElement ) )
            	    // InternalSTAlgorithmParser.g:640:5: (lv_values_3_0= ruleArrayInitElement )
            	    {
            	    // InternalSTAlgorithmParser.g:640:5: (lv_values_3_0= ruleArrayInitElement )
            	    // InternalSTAlgorithmParser.g:641:6: lv_values_3_0= ruleArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getArrayInitializerExpressionAccess().getValuesArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_16);
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
            	    break loop14;
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
    // InternalSTAlgorithmParser.g:667:1: entryRuleArrayInitElement returns [EObject current=null] : iv_ruleArrayInitElement= ruleArrayInitElement EOF ;
    public final EObject entryRuleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitElement = null;


        try {
            // InternalSTAlgorithmParser.g:667:57: (iv_ruleArrayInitElement= ruleArrayInitElement EOF )
            // InternalSTAlgorithmParser.g:668:2: iv_ruleArrayInitElement= ruleArrayInitElement EOF
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
    // InternalSTAlgorithmParser.g:674:1: ruleArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) ;
    public final EObject ruleArrayInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_indexOrInitExpression_0_0 = null;

        EObject lv_initExpression_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:680:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) )
            // InternalSTAlgorithmParser.g:681:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            {
            // InternalSTAlgorithmParser.g:681:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            // InternalSTAlgorithmParser.g:682:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            {
            // InternalSTAlgorithmParser.g:682:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:683:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:683:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:684:5: lv_indexOrInitExpression_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrayInitElementAccess().getIndexOrInitExpressionSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_24);
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

            // InternalSTAlgorithmParser.g:701:3: (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==LeftParenthesis) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalSTAlgorithmParser.g:702:4: otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:706:4: ( (lv_initExpression_2_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:707:5: (lv_initExpression_2_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:707:5: (lv_initExpression_2_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:708:6: lv_initExpression_2_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getArrayInitElementAccess().getInitExpressionSTExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_25);
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
    // InternalSTAlgorithmParser.g:734:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTAlgorithmParser.g:734:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTAlgorithmParser.g:735:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalSTAlgorithmParser.g:741:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) ;
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
            // InternalSTAlgorithmParser.g:747:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) )
            // InternalSTAlgorithmParser.g:748:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            {
            // InternalSTAlgorithmParser.g:748:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=LDATE_AND_TIME && LA17_0<=DATE_AND_TIME)||(LA17_0>=LTIME_OF_DAY && LA17_0<=TIME_OF_DAY)||LA17_0==CONTINUE||LA17_0==WSTRING||(LA17_0>=REPEAT && LA17_0<=STRING)||LA17_0==DWORD||(LA17_0>=FALSE && LA17_0<=ULINT)||(LA17_0>=USINT && LA17_0<=DINT)||(LA17_0>=EXIT && LA17_0<=SINT)||(LA17_0>=TIME && LA17_0<=WORD)||(LA17_0>=FOR && LA17_0<=LDT)||LA17_0==TOD||(LA17_0>=DT && LA17_0<=LT)||LA17_0==LeftParenthesis||LA17_0==PlusSign||LA17_0==HyphenMinus||LA17_0==D||LA17_0==T||(LA17_0>=RULE_NON_DECIMAL && LA17_0<=RULE_INT)||(LA17_0>=RULE_ID && LA17_0<=RULE_WSTRING)) ) {
                alt17=1;
            }
            else if ( (LA17_0==Semicolon) ) {
                alt17=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // InternalSTAlgorithmParser.g:749:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    {
                    // InternalSTAlgorithmParser.g:749:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    // InternalSTAlgorithmParser.g:750:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon
                    {
                    // InternalSTAlgorithmParser.g:750:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )
                    int alt16=10;
                    alt16 = dfa16.predict(input);
                    switch (alt16) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:751:5: this_STIfStatement_0= ruleSTIfStatement
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
                            // InternalSTAlgorithmParser.g:760:5: this_STCaseStatement_1= ruleSTCaseStatement
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
                            // InternalSTAlgorithmParser.g:769:5: this_STForStatement_2= ruleSTForStatement
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
                            // InternalSTAlgorithmParser.g:778:5: this_STWhileStatement_3= ruleSTWhileStatement
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
                            // InternalSTAlgorithmParser.g:787:5: this_STRepeatStatement_4= ruleSTRepeatStatement
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
                            // InternalSTAlgorithmParser.g:796:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            {
                            // InternalSTAlgorithmParser.g:796:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            // InternalSTAlgorithmParser.g:797:6: ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement
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
                            // InternalSTAlgorithmParser.g:808:5: this_STCallStatement_6= ruleSTCallStatement
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
                            // InternalSTAlgorithmParser.g:817:5: ( () otherlv_8= RETURN )
                            {
                            // InternalSTAlgorithmParser.g:817:5: ( () otherlv_8= RETURN )
                            // InternalSTAlgorithmParser.g:818:6: () otherlv_8= RETURN
                            {
                            // InternalSTAlgorithmParser.g:818:6: ()
                            // InternalSTAlgorithmParser.g:819:7: 
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
                            // InternalSTAlgorithmParser.g:831:5: ( () otherlv_10= CONTINUE )
                            {
                            // InternalSTAlgorithmParser.g:831:5: ( () otherlv_10= CONTINUE )
                            // InternalSTAlgorithmParser.g:832:6: () otherlv_10= CONTINUE
                            {
                            // InternalSTAlgorithmParser.g:832:6: ()
                            // InternalSTAlgorithmParser.g:833:7: 
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
                            // InternalSTAlgorithmParser.g:845:5: ( () otherlv_12= EXIT )
                            {
                            // InternalSTAlgorithmParser.g:845:5: ( () otherlv_12= EXIT )
                            // InternalSTAlgorithmParser.g:846:6: () otherlv_12= EXIT
                            {
                            // InternalSTAlgorithmParser.g:846:6: ()
                            // InternalSTAlgorithmParser.g:847:7: 
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
                    // InternalSTAlgorithmParser.g:865:3: ( () otherlv_15= Semicolon )
                    {
                    // InternalSTAlgorithmParser.g:865:3: ( () otherlv_15= Semicolon )
                    // InternalSTAlgorithmParser.g:866:4: () otherlv_15= Semicolon
                    {
                    // InternalSTAlgorithmParser.g:866:4: ()
                    // InternalSTAlgorithmParser.g:867:5: 
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
    // InternalSTAlgorithmParser.g:882:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTAlgorithmParser.g:882:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTAlgorithmParser.g:883:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTAlgorithmParser.g:889:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:895:2: ( ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) )
            // InternalSTAlgorithmParser.g:896:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            {
            // InternalSTAlgorithmParser.g:896:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            // InternalSTAlgorithmParser.g:897:3: ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) )
            {
            // InternalSTAlgorithmParser.g:897:3: ( (lv_left_0_0= ruleSTAccessExpression ) )
            // InternalSTAlgorithmParser.g:898:4: (lv_left_0_0= ruleSTAccessExpression )
            {
            // InternalSTAlgorithmParser.g:898:4: (lv_left_0_0= ruleSTAccessExpression )
            // InternalSTAlgorithmParser.g:899:5: lv_left_0_0= ruleSTAccessExpression
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
            // InternalSTAlgorithmParser.g:920:3: ( (lv_right_2_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:921:4: (lv_right_2_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:921:4: (lv_right_2_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:922:5: lv_right_2_0= ruleSTExpression
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
    // InternalSTAlgorithmParser.g:943:1: entryRuleSTCallStatement returns [EObject current=null] : iv_ruleSTCallStatement= ruleSTCallStatement EOF ;
    public final EObject entryRuleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallStatement = null;


        try {
            // InternalSTAlgorithmParser.g:943:56: (iv_ruleSTCallStatement= ruleSTCallStatement EOF )
            // InternalSTAlgorithmParser.g:944:2: iv_ruleSTCallStatement= ruleSTCallStatement EOF
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
    // InternalSTAlgorithmParser.g:950:1: ruleSTCallStatement returns [EObject current=null] : ( (lv_call_0_0= ruleSTAccessExpression ) ) ;
    public final EObject ruleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject lv_call_0_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:956:2: ( ( (lv_call_0_0= ruleSTAccessExpression ) ) )
            // InternalSTAlgorithmParser.g:957:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            {
            // InternalSTAlgorithmParser.g:957:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            // InternalSTAlgorithmParser.g:958:3: (lv_call_0_0= ruleSTAccessExpression )
            {
            // InternalSTAlgorithmParser.g:958:3: (lv_call_0_0= ruleSTAccessExpression )
            // InternalSTAlgorithmParser.g:959:4: lv_call_0_0= ruleSTAccessExpression
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
    // InternalSTAlgorithmParser.g:979:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalSTAlgorithmParser.g:979:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalSTAlgorithmParser.g:980:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
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
    // InternalSTAlgorithmParser.g:986:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:992:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalSTAlgorithmParser.g:993:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalSTAlgorithmParser.g:993:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            int alt18=3;
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
            case RULE_WSTRING:
                {
                alt18=1;
                }
                break;
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case ColonEqualsSign:
                    {
                    alt18=2;
                    }
                    break;
                case EqualsSignGreaterThanSign:
                    {
                    alt18=3;
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
                    alt18=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 2, input);

                    throw nvae;
                }

                }
                break;
            case NOT:
                {
                int LA18_3 = input.LA(2);

                if ( ((LA18_3>=LDATE_AND_TIME && LA18_3<=DATE_AND_TIME)||(LA18_3>=LTIME_OF_DAY && LA18_3<=TIME_OF_DAY)||LA18_3==WSTRING||LA18_3==STRING||LA18_3==DWORD||(LA18_3>=FALSE && LA18_3<=ULINT)||(LA18_3>=USINT && LA18_3<=WCHAR)||(LA18_3>=BOOL && LA18_3<=BYTE)||(LA18_3>=CHAR && LA18_3<=DINT)||(LA18_3>=LINT && LA18_3<=SINT)||(LA18_3>=TIME && LA18_3<=WORD)||(LA18_3>=INT && LA18_3<=LDT)||(LA18_3>=NOT && LA18_3<=TOD)||LA18_3==DT||(LA18_3>=LD && LA18_3<=LT)||LA18_3==LeftParenthesis||LA18_3==PlusSign||LA18_3==HyphenMinus||LA18_3==D||LA18_3==T||(LA18_3>=RULE_NON_DECIMAL && LA18_3<=RULE_INT)||(LA18_3>=RULE_STRING && LA18_3<=RULE_WSTRING)) ) {
                    alt18=1;
                }
                else if ( (LA18_3==RULE_ID) ) {
                    int LA18_6 = input.LA(3);

                    if ( (LA18_6==EqualsSignGreaterThanSign) ) {
                        alt18=3;
                    }
                    else if ( (LA18_6==EOF||LA18_6==AND||LA18_6==MOD||LA18_6==XOR||(LA18_6>=AsteriskAsterisk && LA18_6<=FullStopFullStop)||(LA18_6>=LessThanSignEqualsSign && LA18_6<=LessThanSignGreaterThanSign)||LA18_6==GreaterThanSignEqualsSign||LA18_6==OR||(LA18_6>=Ampersand && LA18_6<=Solidus)||(LA18_6>=LessThanSign && LA18_6<=GreaterThanSign)||LA18_6==LeftSquareBracket) ) {
                        alt18=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 6, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalSTAlgorithmParser.g:994:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
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
                    // InternalSTAlgorithmParser.g:1003:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
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
                    // InternalSTAlgorithmParser.g:1012:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
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
    // InternalSTAlgorithmParser.g:1024:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalSTAlgorithmParser.g:1024:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalSTAlgorithmParser.g:1025:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
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
    // InternalSTAlgorithmParser.g:1031:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_arg_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_arg_0_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1037:2: ( ( (lv_arg_0_0= ruleSTExpression ) ) )
            // InternalSTAlgorithmParser.g:1038:2: ( (lv_arg_0_0= ruleSTExpression ) )
            {
            // InternalSTAlgorithmParser.g:1038:2: ( (lv_arg_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1039:3: (lv_arg_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1039:3: (lv_arg_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1040:4: lv_arg_0_0= ruleSTExpression
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
    // InternalSTAlgorithmParser.g:1060:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalSTAlgorithmParser.g:1060:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalSTAlgorithmParser.g:1061:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
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
    // InternalSTAlgorithmParser.g:1067:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_source_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1073:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) ) )
            // InternalSTAlgorithmParser.g:1074:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) )
            {
            // InternalSTAlgorithmParser.g:1074:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) ) )
            // InternalSTAlgorithmParser.g:1075:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_source_2_0= ruleSTExpression ) )
            {
            // InternalSTAlgorithmParser.g:1075:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTAlgorithmParser.g:1076:4: (otherlv_0= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:1076:4: (otherlv_0= RULE_ID )
            // InternalSTAlgorithmParser.g:1077:5: otherlv_0= RULE_ID
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
            // InternalSTAlgorithmParser.g:1092:3: ( (lv_source_2_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1093:4: (lv_source_2_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1093:4: (lv_source_2_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1094:5: lv_source_2_0= ruleSTExpression
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
    // InternalSTAlgorithmParser.g:1115:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalSTAlgorithmParser.g:1115:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalSTAlgorithmParser.g:1116:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
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
    // InternalSTAlgorithmParser.g:1122:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1128:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) ) )
            // InternalSTAlgorithmParser.g:1129:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) )
            {
            // InternalSTAlgorithmParser.g:1129:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) ) )
            // InternalSTAlgorithmParser.g:1130:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (otherlv_3= RULE_ID ) )
            {
            // InternalSTAlgorithmParser.g:1130:3: ( (lv_not_0_0= NOT ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==NOT) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1131:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTAlgorithmParser.g:1131:4: (lv_not_0_0= NOT )
                    // InternalSTAlgorithmParser.g:1132:5: lv_not_0_0= NOT
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

            // InternalSTAlgorithmParser.g:1144:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTAlgorithmParser.g:1145:4: (otherlv_1= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:1145:4: (otherlv_1= RULE_ID )
            // InternalSTAlgorithmParser.g:1146:5: otherlv_1= RULE_ID
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
            // InternalSTAlgorithmParser.g:1161:3: ( (otherlv_3= RULE_ID ) )
            // InternalSTAlgorithmParser.g:1162:4: (otherlv_3= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:1162:4: (otherlv_3= RULE_ID )
            // InternalSTAlgorithmParser.g:1163:5: otherlv_3= RULE_ID
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
    // InternalSTAlgorithmParser.g:1178:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1178:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalSTAlgorithmParser.g:1179:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalSTAlgorithmParser.g:1185:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTAlgorithmParser.g:1191:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTAlgorithmParser.g:1192:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTAlgorithmParser.g:1192:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTAlgorithmParser.g:1193:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1197:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1198:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1198:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1199:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTAlgorithmParser.g:1220:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=LDATE_AND_TIME && LA20_0<=DATE_AND_TIME)||(LA20_0>=LTIME_OF_DAY && LA20_0<=TIME_OF_DAY)||LA20_0==CONTINUE||LA20_0==WSTRING||(LA20_0>=REPEAT && LA20_0<=STRING)||LA20_0==DWORD||(LA20_0>=FALSE && LA20_0<=ULINT)||(LA20_0>=USINT && LA20_0<=DINT)||(LA20_0>=EXIT && LA20_0<=SINT)||(LA20_0>=TIME && LA20_0<=WORD)||(LA20_0>=FOR && LA20_0<=LDT)||LA20_0==TOD||(LA20_0>=DT && LA20_0<=LT)||LA20_0==LeftParenthesis||LA20_0==PlusSign||LA20_0==HyphenMinus||LA20_0==Semicolon||LA20_0==D||LA20_0==T||(LA20_0>=RULE_NON_DECIMAL && LA20_0<=RULE_INT)||(LA20_0>=RULE_ID && LA20_0<=RULE_WSTRING)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1221:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1221:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1222:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop20;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:1239:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==ELSIF) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1240:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTAlgorithmParser.g:1240:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTAlgorithmParser.g:1241:5: lv_elseifs_4_0= ruleSTElseIfPart
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
            	    break loop21;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:1258:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==ELSE) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1259:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTAlgorithmParser.g:1259:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTAlgorithmParser.g:1260:5: lv_else_5_0= ruleSTElsePart
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
    // InternalSTAlgorithmParser.g:1285:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTAlgorithmParser.g:1285:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTAlgorithmParser.g:1286:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTAlgorithmParser.g:1292:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1298:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1299:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1299:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1300:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1304:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1305:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1305:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1306:5: lv_condition_1_0= ruleSTExpression
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1327:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( ((LA23_0>=LDATE_AND_TIME && LA23_0<=DATE_AND_TIME)||(LA23_0>=LTIME_OF_DAY && LA23_0<=TIME_OF_DAY)||LA23_0==CONTINUE||LA23_0==WSTRING||(LA23_0>=REPEAT && LA23_0<=STRING)||LA23_0==DWORD||(LA23_0>=FALSE && LA23_0<=ULINT)||(LA23_0>=USINT && LA23_0<=DINT)||(LA23_0>=EXIT && LA23_0<=SINT)||(LA23_0>=TIME && LA23_0<=WORD)||(LA23_0>=FOR && LA23_0<=LDT)||LA23_0==TOD||(LA23_0>=DT && LA23_0<=LT)||LA23_0==LeftParenthesis||LA23_0==PlusSign||LA23_0==HyphenMinus||LA23_0==Semicolon||LA23_0==D||LA23_0==T||(LA23_0>=RULE_NON_DECIMAL && LA23_0<=RULE_INT)||(LA23_0>=RULE_ID && LA23_0<=RULE_WSTRING)) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1328:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1328:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1329:5: lv_statements_3_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalSTAlgorithmParser.g:1350:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1350:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTAlgorithmParser.g:1351:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTAlgorithmParser.g:1357:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTAlgorithmParser.g:1363:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTAlgorithmParser.g:1364:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTAlgorithmParser.g:1364:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTAlgorithmParser.g:1365:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1369:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1370:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1370:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1371:5: lv_selector_1_0= ruleSTExpression
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
            // InternalSTAlgorithmParser.g:1392:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt24=0;
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>=LDATE_AND_TIME && LA24_0<=DATE_AND_TIME)||(LA24_0>=LTIME_OF_DAY && LA24_0<=TIME_OF_DAY)||LA24_0==WSTRING||LA24_0==STRING||LA24_0==DWORD||(LA24_0>=FALSE && LA24_0<=ULINT)||(LA24_0>=USINT && LA24_0<=WCHAR)||(LA24_0>=BOOL && LA24_0<=BYTE)||(LA24_0>=CHAR && LA24_0<=DINT)||(LA24_0>=LINT && LA24_0<=SINT)||(LA24_0>=TIME && LA24_0<=WORD)||(LA24_0>=INT && LA24_0<=LDT)||(LA24_0>=NOT && LA24_0<=TOD)||LA24_0==DT||(LA24_0>=LD && LA24_0<=LT)||LA24_0==LeftParenthesis||LA24_0==PlusSign||LA24_0==HyphenMinus||LA24_0==D||LA24_0==T||(LA24_0>=RULE_NON_DECIMAL && LA24_0<=RULE_INT)||(LA24_0>=RULE_ID && LA24_0<=RULE_WSTRING)) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1393:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTAlgorithmParser.g:1393:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTAlgorithmParser.g:1394:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
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
            	    if ( cnt24 >= 1 ) break loop24;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(24, input);
                        throw eee;
                }
                cnt24++;
            } while (true);

            // InternalSTAlgorithmParser.g:1411:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==ELSE) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1412:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTAlgorithmParser.g:1412:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTAlgorithmParser.g:1413:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_33);
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
    // InternalSTAlgorithmParser.g:1438:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTAlgorithmParser.g:1438:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTAlgorithmParser.g:1439:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTAlgorithmParser.g:1445:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1451:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1452:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1452:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1453:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:1453:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1454:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1454:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1455:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_34);
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

            // InternalSTAlgorithmParser.g:1472:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==Comma) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1473:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTAlgorithmParser.g:1477:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTAlgorithmParser.g:1478:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1478:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTAlgorithmParser.g:1479:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_34);
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
            	    break loop26;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1501:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop27:
            do {
                int alt27=2;
                alt27 = dfa27.predict(input);
                switch (alt27) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1502:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1506:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1507:5: lv_statements_4_0= ruleSTStatement
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTAlgorithmParser.g:1528:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTAlgorithmParser.g:1528:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTAlgorithmParser.g:1529:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTAlgorithmParser.g:1535:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1541:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1542:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1542:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1543:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:1543:3: ()
            // InternalSTAlgorithmParser.g:1544:4: 
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
            // InternalSTAlgorithmParser.g:1554:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>=LDATE_AND_TIME && LA28_0<=DATE_AND_TIME)||(LA28_0>=LTIME_OF_DAY && LA28_0<=TIME_OF_DAY)||LA28_0==CONTINUE||LA28_0==WSTRING||(LA28_0>=REPEAT && LA28_0<=STRING)||LA28_0==DWORD||(LA28_0>=FALSE && LA28_0<=ULINT)||(LA28_0>=USINT && LA28_0<=DINT)||(LA28_0>=EXIT && LA28_0<=SINT)||(LA28_0>=TIME && LA28_0<=WORD)||(LA28_0>=FOR && LA28_0<=LDT)||LA28_0==TOD||(LA28_0>=DT && LA28_0<=LT)||LA28_0==LeftParenthesis||LA28_0==PlusSign||LA28_0==HyphenMinus||LA28_0==Semicolon||LA28_0==D||LA28_0==T||(LA28_0>=RULE_NON_DECIMAL && LA28_0<=RULE_INT)||(LA28_0>=RULE_ID && LA28_0<=RULE_WSTRING)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1555:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1555:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1556:5: lv_statements_2_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalSTAlgorithmParser.g:1577:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1577:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTAlgorithmParser.g:1578:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTAlgorithmParser.g:1584:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
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
            // InternalSTAlgorithmParser.g:1590:2: ( (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalSTAlgorithmParser.g:1591:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalSTAlgorithmParser.g:1591:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalSTAlgorithmParser.g:1592:3: otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1596:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTAlgorithmParser.g:1597:4: (otherlv_1= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:1597:4: (otherlv_1= RULE_ID )
            // InternalSTAlgorithmParser.g:1598:5: otherlv_1= RULE_ID
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
            // InternalSTAlgorithmParser.g:1613:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1614:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1614:4: (lv_from_3_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1615:5: lv_from_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_35);
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
            // InternalSTAlgorithmParser.g:1636:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1637:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1637:4: (lv_to_5_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1638:5: lv_to_5_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_36);
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

            // InternalSTAlgorithmParser.g:1655:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==BY) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1656:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:1660:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:1661:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:1661:5: (lv_by_7_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:1662:6: lv_by_7_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_37);
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

            otherlv_8=(Token)match(input,DO,FOLLOW_38); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
              		
            }
            // InternalSTAlgorithmParser.g:1684:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>=LDATE_AND_TIME && LA30_0<=DATE_AND_TIME)||(LA30_0>=LTIME_OF_DAY && LA30_0<=TIME_OF_DAY)||LA30_0==CONTINUE||LA30_0==WSTRING||(LA30_0>=REPEAT && LA30_0<=STRING)||LA30_0==DWORD||(LA30_0>=FALSE && LA30_0<=ULINT)||(LA30_0>=USINT && LA30_0<=DINT)||(LA30_0>=EXIT && LA30_0<=SINT)||(LA30_0>=TIME && LA30_0<=WORD)||(LA30_0>=FOR && LA30_0<=LDT)||LA30_0==TOD||(LA30_0>=DT && LA30_0<=LT)||LA30_0==LeftParenthesis||LA30_0==PlusSign||LA30_0==HyphenMinus||LA30_0==Semicolon||LA30_0==D||LA30_0==T||(LA30_0>=RULE_NON_DECIMAL && LA30_0<=RULE_INT)||(LA30_0>=RULE_ID && LA30_0<=RULE_WSTRING)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1685:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1685:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1686:5: lv_statements_9_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_38);
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
            	    break loop30;
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
    // InternalSTAlgorithmParser.g:1711:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1711:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTAlgorithmParser.g:1712:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTAlgorithmParser.g:1718:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1724:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTAlgorithmParser.g:1725:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTAlgorithmParser.g:1725:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTAlgorithmParser.g:1726:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1730:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1731:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1731:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1732:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_37);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1753:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( ((LA31_0>=LDATE_AND_TIME && LA31_0<=DATE_AND_TIME)||(LA31_0>=LTIME_OF_DAY && LA31_0<=TIME_OF_DAY)||LA31_0==CONTINUE||LA31_0==WSTRING||(LA31_0>=REPEAT && LA31_0<=STRING)||LA31_0==DWORD||(LA31_0>=FALSE && LA31_0<=ULINT)||(LA31_0>=USINT && LA31_0<=DINT)||(LA31_0>=EXIT && LA31_0<=SINT)||(LA31_0>=TIME && LA31_0<=WORD)||(LA31_0>=FOR && LA31_0<=LDT)||LA31_0==TOD||(LA31_0>=DT && LA31_0<=LT)||LA31_0==LeftParenthesis||LA31_0==PlusSign||LA31_0==HyphenMinus||LA31_0==Semicolon||LA31_0==D||LA31_0==T||(LA31_0>=RULE_NON_DECIMAL && LA31_0<=RULE_INT)||(LA31_0>=RULE_ID && LA31_0<=RULE_WSTRING)) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1754:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1754:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1755:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_39);
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
            	    break loop31;
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
    // InternalSTAlgorithmParser.g:1780:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1780:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTAlgorithmParser.g:1781:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTAlgorithmParser.g:1787:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1793:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTAlgorithmParser.g:1794:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTAlgorithmParser.g:1794:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTAlgorithmParser.g:1795:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_40); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1799:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( ((LA32_0>=LDATE_AND_TIME && LA32_0<=DATE_AND_TIME)||(LA32_0>=LTIME_OF_DAY && LA32_0<=TIME_OF_DAY)||LA32_0==CONTINUE||LA32_0==WSTRING||(LA32_0>=REPEAT && LA32_0<=STRING)||LA32_0==DWORD||(LA32_0>=FALSE && LA32_0<=ULINT)||(LA32_0>=USINT && LA32_0<=DINT)||(LA32_0>=EXIT && LA32_0<=SINT)||(LA32_0>=TIME && LA32_0<=WORD)||(LA32_0>=FOR && LA32_0<=LDT)||LA32_0==TOD||(LA32_0>=DT && LA32_0<=LT)||LA32_0==LeftParenthesis||LA32_0==PlusSign||LA32_0==HyphenMinus||LA32_0==Semicolon||LA32_0==D||LA32_0==T||(LA32_0>=RULE_NON_DECIMAL && LA32_0<=RULE_INT)||(LA32_0>=RULE_ID && LA32_0<=RULE_WSTRING)) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1800:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1800:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1801:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_40);
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
            	    break loop32;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1822:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1823:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1823:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1824:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_41);
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
    // InternalSTAlgorithmParser.g:1849:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1849:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTAlgorithmParser.g:1850:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTAlgorithmParser.g:1856:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1862:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTAlgorithmParser.g:1863:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTAlgorithmParser.g:1874:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1874:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTAlgorithmParser.g:1875:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTAlgorithmParser.g:1881:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1887:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:1888:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:1888:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTAlgorithmParser.g:1889:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_42);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:1897:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==FullStopFullStop) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1898:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1898:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTAlgorithmParser.g:1899:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1899:5: ()
            	    // InternalSTAlgorithmParser.g:1900:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:1906:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTAlgorithmParser.g:1907:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:1907:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTAlgorithmParser.g:1908:7: lv_op_2_0= ruleSubrangeOperator
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

            	    // InternalSTAlgorithmParser.g:1926:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTAlgorithmParser.g:1927:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1927:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTAlgorithmParser.g:1928:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_42);
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalSTAlgorithmParser.g:1950:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1950:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTAlgorithmParser.g:1951:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTAlgorithmParser.g:1957:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1963:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:1964:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:1964:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTAlgorithmParser.g:1965:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_43);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:1973:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==OR) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1974:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1974:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTAlgorithmParser.g:1975:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1975:5: ()
            	    // InternalSTAlgorithmParser.g:1976:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:1982:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTAlgorithmParser.g:1983:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:1983:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTAlgorithmParser.g:1984:7: lv_op_2_0= ruleOrOperator
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

            	    // InternalSTAlgorithmParser.g:2002:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTAlgorithmParser.g:2003:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2003:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTAlgorithmParser.g:2004:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_43);
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalSTAlgorithmParser.g:2026:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2026:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTAlgorithmParser.g:2027:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTAlgorithmParser.g:2033:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2039:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2040:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2040:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2041:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2049:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==XOR) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2050:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2050:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2051:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2051:5: ()
            	    // InternalSTAlgorithmParser.g:2052:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2058:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTAlgorithmParser.g:2059:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2059:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTAlgorithmParser.g:2060:7: lv_op_2_0= ruleXorOperator
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

            	    // InternalSTAlgorithmParser.g:2078:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTAlgorithmParser.g:2079:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2079:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTAlgorithmParser.g:2080:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalSTAlgorithmParser.g:2102:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2102:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTAlgorithmParser.g:2103:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTAlgorithmParser.g:2109:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2115:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2116:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2116:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2117:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_45);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2125:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==AND||LA36_0==Ampersand) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2126:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2126:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2127:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2127:5: ()
            	    // InternalSTAlgorithmParser.g:2128:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2134:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTAlgorithmParser.g:2135:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2135:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTAlgorithmParser.g:2136:7: lv_op_2_0= ruleAndOperator
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

            	    // InternalSTAlgorithmParser.g:2154:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTAlgorithmParser.g:2155:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2155:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTAlgorithmParser.g:2156:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_45);
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalSTAlgorithmParser.g:2178:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2178:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTAlgorithmParser.g:2179:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTAlgorithmParser.g:2185:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2191:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2192:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2192:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2193:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2201:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==LessThanSignGreaterThanSign||LA37_0==EqualsSign) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2202:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2202:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2203:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2203:5: ()
            	    // InternalSTAlgorithmParser.g:2204:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2210:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTAlgorithmParser.g:2211:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2211:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTAlgorithmParser.g:2212:7: lv_op_2_0= ruleEqualityOperator
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

            	    // InternalSTAlgorithmParser.g:2230:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTAlgorithmParser.g:2231:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2231:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTAlgorithmParser.g:2232:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_46);
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalSTAlgorithmParser.g:2254:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2254:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTAlgorithmParser.g:2255:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTAlgorithmParser.g:2261:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2267:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2268:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2268:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2269:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_47);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2277:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==LessThanSignEqualsSign||LA38_0==GreaterThanSignEqualsSign||LA38_0==LessThanSign||LA38_0==GreaterThanSign) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2278:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2278:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2279:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2279:5: ()
            	    // InternalSTAlgorithmParser.g:2280:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2286:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTAlgorithmParser.g:2287:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2287:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTAlgorithmParser.g:2288:7: lv_op_2_0= ruleCompareOperator
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

            	    // InternalSTAlgorithmParser.g:2306:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTAlgorithmParser.g:2307:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2307:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTAlgorithmParser.g:2308:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
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
    // InternalSTAlgorithmParser.g:2330:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2330:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTAlgorithmParser.g:2331:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTAlgorithmParser.g:2337:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2343:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2344:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2344:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2345:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_48);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2353:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==PlusSign||LA39_0==HyphenMinus) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2354:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2354:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2355:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2355:5: ()
            	    // InternalSTAlgorithmParser.g:2356:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2362:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTAlgorithmParser.g:2363:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2363:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTAlgorithmParser.g:2364:7: lv_op_2_0= ruleAddSubOperator
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

            	    // InternalSTAlgorithmParser.g:2382:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTAlgorithmParser.g:2383:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2383:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTAlgorithmParser.g:2384:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_48);
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
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalSTAlgorithmParser.g:2406:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2406:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTAlgorithmParser.g:2407:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTAlgorithmParser.g:2413:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2419:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2420:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2420:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2421:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2429:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==MOD||LA40_0==Asterisk||LA40_0==Solidus) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2430:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2430:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2431:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2431:5: ()
            	    // InternalSTAlgorithmParser.g:2432:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2438:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTAlgorithmParser.g:2439:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2439:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTAlgorithmParser.g:2440:7: lv_op_2_0= ruleMulDivModOperator
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

            	    // InternalSTAlgorithmParser.g:2458:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTAlgorithmParser.g:2459:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2459:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTAlgorithmParser.g:2460:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
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
    // $ANTLR end "ruleSTMulDivModExpression"


    // $ANTLR start "entryRuleSTPowerExpression"
    // InternalSTAlgorithmParser.g:2482:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2482:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTAlgorithmParser.g:2483:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTAlgorithmParser.g:2489:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2495:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2496:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2496:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2497:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_50);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2505:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==AsteriskAsterisk) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2506:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2506:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2507:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2507:5: ()
            	    // InternalSTAlgorithmParser.g:2508:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2514:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTAlgorithmParser.g:2515:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2515:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTAlgorithmParser.g:2516:7: lv_op_2_0= rulePowerOperator
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

            	    // InternalSTAlgorithmParser.g:2534:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalSTAlgorithmParser.g:2535:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2535:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalSTAlgorithmParser.g:2536:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_50);
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
            	    break loop41;
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
    // InternalSTAlgorithmParser.g:2558:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2558:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalSTAlgorithmParser.g:2559:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalSTAlgorithmParser.g:2565:1: ruleSTUnaryExpression returns [EObject current=null] : ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2571:2: ( ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalSTAlgorithmParser.g:2572:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalSTAlgorithmParser.g:2572:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            int alt42=2;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2573:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    {
                    // InternalSTAlgorithmParser.g:2573:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    // InternalSTAlgorithmParser.g:2574:4: ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalSTAlgorithmParser.g:2585:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalSTAlgorithmParser.g:2585:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    // InternalSTAlgorithmParser.g:2586:4: () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    {
                    // InternalSTAlgorithmParser.g:2586:4: ()
                    // InternalSTAlgorithmParser.g:2587:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:2593:4: ( (lv_op_2_0= ruleUnaryOperator ) )
                    // InternalSTAlgorithmParser.g:2594:5: (lv_op_2_0= ruleUnaryOperator )
                    {
                    // InternalSTAlgorithmParser.g:2594:5: (lv_op_2_0= ruleUnaryOperator )
                    // InternalSTAlgorithmParser.g:2595:6: lv_op_2_0= ruleUnaryOperator
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

                    // InternalSTAlgorithmParser.g:2612:4: ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    // InternalSTAlgorithmParser.g:2613:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    {
                    // InternalSTAlgorithmParser.g:2613:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    // InternalSTAlgorithmParser.g:2614:6: lv_expression_3_0= ruleSTUnaryExpression
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
    // InternalSTAlgorithmParser.g:2636:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2636:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalSTAlgorithmParser.g:2637:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalSTAlgorithmParser.g:2643:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalSTAlgorithmParser.g:2649:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalSTAlgorithmParser.g:2650:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalSTAlgorithmParser.g:2650:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalSTAlgorithmParser.g:2651:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2659:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop45:
            do {
                int alt45=3;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==FullStop) ) {
                    alt45=1;
                }
                else if ( (LA45_0==LeftSquareBracket) ) {
                    alt45=2;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2660:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2660:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalSTAlgorithmParser.g:2661:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2661:5: ()
            	    // InternalSTAlgorithmParser.g:2662:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalSTAlgorithmParser.g:2672:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalSTAlgorithmParser.g:2673:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2673:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalSTAlgorithmParser.g:2674:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2674:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt43=2;
            	    int LA43_0 = input.LA(1);

            	    if ( (LA43_0==RULE_ID) ) {
            	        alt43=1;
            	    }
            	    else if ( ((LA43_0>=B && LA43_0<=X)||LA43_0==RULE_INT) ) {
            	        alt43=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 43, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt43) {
            	        case 1 :
            	            // InternalSTAlgorithmParser.g:2675:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_51);
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
            	            // InternalSTAlgorithmParser.g:2691:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_51);
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
            	    // InternalSTAlgorithmParser.g:2711:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalSTAlgorithmParser.g:2711:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalSTAlgorithmParser.g:2712:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalSTAlgorithmParser.g:2712:5: ()
            	    // InternalSTAlgorithmParser.g:2713:6: 
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
            	    // InternalSTAlgorithmParser.g:2723:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalSTAlgorithmParser.g:2724:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2724:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalSTAlgorithmParser.g:2725:7: lv_index_6_0= ruleSTExpression
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

            	    // InternalSTAlgorithmParser.g:2742:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop44:
            	    do {
            	        int alt44=2;
            	        int LA44_0 = input.LA(1);

            	        if ( (LA44_0==Comma) ) {
            	            alt44=1;
            	        }


            	        switch (alt44) {
            	    	case 1 :
            	    	    // InternalSTAlgorithmParser.g:2743:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalSTAlgorithmParser.g:2747:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalSTAlgorithmParser.g:2748:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalSTAlgorithmParser.g:2748:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalSTAlgorithmParser.g:2749:8: lv_index_8_0= ruleSTExpression
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
            	    	    break loop44;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_51); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
            	      				
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
    // $ANTLR end "ruleSTAccessExpression"


    // $ANTLR start "entryRuleSTPrimaryExpression"
    // InternalSTAlgorithmParser.g:2777:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2777:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalSTAlgorithmParser.g:2778:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalSTAlgorithmParser.g:2784:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STLiteralExpressions_4 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2790:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) )
            // InternalSTAlgorithmParser.g:2791:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            {
            // InternalSTAlgorithmParser.g:2791:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            int alt46=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt46=1;
                }
                break;
            case RULE_ID:
                {
                alt46=2;
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
            case RULE_WSTRING:
                {
                alt46=3;
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
                    // InternalSTAlgorithmParser.g:2792:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTAlgorithmParser.g:2792:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTAlgorithmParser.g:2793:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_25);
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
                    // InternalSTAlgorithmParser.g:2811:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalSTAlgorithmParser.g:2820:3: this_STLiteralExpressions_4= ruleSTLiteralExpressions
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
    // InternalSTAlgorithmParser.g:2832:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2832:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalSTAlgorithmParser.g:2833:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalSTAlgorithmParser.g:2839:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
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
            // InternalSTAlgorithmParser.g:2845:2: ( ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalSTAlgorithmParser.g:2846:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalSTAlgorithmParser.g:2846:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalSTAlgorithmParser.g:2847:3: () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalSTAlgorithmParser.g:2847:3: ()
            // InternalSTAlgorithmParser.g:2848:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:2854:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTAlgorithmParser.g:2855:4: (otherlv_1= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:2855:4: (otherlv_1= RULE_ID )
            // InternalSTAlgorithmParser.g:2856:5: otherlv_1= RULE_ID
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

            // InternalSTAlgorithmParser.g:2867:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt49=2;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2868:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalSTAlgorithmParser.g:2868:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis )
                    // InternalSTAlgorithmParser.g:2869:5: ( LeftParenthesis )=>otherlv_2= LeftParenthesis
                    {
                    otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getSTFeatureExpressionAccess().getLeftParenthesisKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:2875:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( ((LA48_0>=LDATE_AND_TIME && LA48_0<=DATE_AND_TIME)||(LA48_0>=LTIME_OF_DAY && LA48_0<=TIME_OF_DAY)||LA48_0==WSTRING||LA48_0==STRING||LA48_0==DWORD||(LA48_0>=FALSE && LA48_0<=ULINT)||(LA48_0>=USINT && LA48_0<=WCHAR)||(LA48_0>=BOOL && LA48_0<=BYTE)||(LA48_0>=CHAR && LA48_0<=DINT)||(LA48_0>=LINT && LA48_0<=SINT)||(LA48_0>=TIME && LA48_0<=WORD)||(LA48_0>=INT && LA48_0<=LDT)||(LA48_0>=NOT && LA48_0<=TOD)||LA48_0==DT||(LA48_0>=LD && LA48_0<=LT)||LA48_0==LeftParenthesis||LA48_0==PlusSign||LA48_0==HyphenMinus||LA48_0==D||LA48_0==T||(LA48_0>=RULE_NON_DECIMAL && LA48_0<=RULE_INT)||(LA48_0>=RULE_ID && LA48_0<=RULE_WSTRING)) ) {
                        alt48=1;
                    }
                    switch (alt48) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:2876:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalSTAlgorithmParser.g:2876:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalSTAlgorithmParser.g:2877:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalSTAlgorithmParser.g:2877:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalSTAlgorithmParser.g:2878:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_54);
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

                            // InternalSTAlgorithmParser.g:2895:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop47:
                            do {
                                int alt47=2;
                                int LA47_0 = input.LA(1);

                                if ( (LA47_0==Comma) ) {
                                    alt47=1;
                                }


                                switch (alt47) {
                            	case 1 :
                            	    // InternalSTAlgorithmParser.g:2896:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalSTAlgorithmParser.g:2900:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalSTAlgorithmParser.g:2901:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalSTAlgorithmParser.g:2901:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalSTAlgorithmParser.g:2902:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_54);
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
                            	    break loop47;
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
    // InternalSTAlgorithmParser.g:2930:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2930:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalSTAlgorithmParser.g:2931:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalSTAlgorithmParser.g:2937:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Enumerator lv_specifier_1_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2943:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) ) )
            // InternalSTAlgorithmParser.g:2944:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) )
            {
            // InternalSTAlgorithmParser.g:2944:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) )
            // InternalSTAlgorithmParser.g:2945:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) )
            {
            // InternalSTAlgorithmParser.g:2945:3: ()
            // InternalSTAlgorithmParser.g:2946:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:2952:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( ((LA50_0>=B && LA50_0<=X)) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2953:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalSTAlgorithmParser.g:2953:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalSTAlgorithmParser.g:2954:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_55);
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

            // InternalSTAlgorithmParser.g:2971:3: ( (lv_index_2_0= RULE_INT ) )
            // InternalSTAlgorithmParser.g:2972:4: (lv_index_2_0= RULE_INT )
            {
            // InternalSTAlgorithmParser.g:2972:4: (lv_index_2_0= RULE_INT )
            // InternalSTAlgorithmParser.g:2973:5: lv_index_2_0= RULE_INT
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
    // InternalSTAlgorithmParser.g:2993:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTAlgorithmParser.g:2993:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTAlgorithmParser.g:2994:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTAlgorithmParser.g:3000:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
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
            // InternalSTAlgorithmParser.g:3006:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalSTAlgorithmParser.g:3007:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalSTAlgorithmParser.g:3007:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt51=6;
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
                alt51=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt51=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt51=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt51=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt51=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
            case RULE_WSTRING:
                {
                alt51=6;
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
                    // InternalSTAlgorithmParser.g:3008:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalSTAlgorithmParser.g:3017:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalSTAlgorithmParser.g:3026:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalSTAlgorithmParser.g:3035:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalSTAlgorithmParser.g:3044:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalSTAlgorithmParser.g:3053:3: this_STStringLiteral_5= ruleSTStringLiteral
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
    // InternalSTAlgorithmParser.g:3065:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3065:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalSTAlgorithmParser.g:3066:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3072:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3078:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalSTAlgorithmParser.g:3079:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalSTAlgorithmParser.g:3079:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==DWORD||LA52_0==LWORD||(LA52_0>=BOOL && LA52_0<=BYTE)||LA52_0==WORD) ) {
                alt52=1;
            }
            else if ( (LA52_0==LREAL||(LA52_0>=UDINT && LA52_0<=ULINT)||LA52_0==USINT||LA52_0==DINT||LA52_0==LINT||(LA52_0>=REAL && LA52_0<=SINT)||LA52_0==UINT||LA52_0==INT) ) {
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
                    // InternalSTAlgorithmParser.g:3080:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalSTAlgorithmParser.g:3091:3: this_STAnyNumType_1= ruleSTAnyNumType
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
    // InternalSTAlgorithmParser.g:3105:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3105:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalSTAlgorithmParser.g:3106:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalSTAlgorithmParser.g:3112:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_3=null;
        AntlrDatatypeRuleToken lv_value_2_1 = null;

        AntlrDatatypeRuleToken lv_value_2_2 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3118:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTAlgorithmParser.g:3119:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTAlgorithmParser.g:3119:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) ) )
            // InternalSTAlgorithmParser.g:3120:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTAlgorithmParser.g:3120:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==DWORD||LA53_0==LREAL||(LA53_0>=LWORD && LA53_0<=ULINT)||LA53_0==USINT||(LA53_0>=BOOL && LA53_0<=BYTE)||LA53_0==DINT||LA53_0==LINT||(LA53_0>=REAL && LA53_0<=SINT)||(LA53_0>=UINT && LA53_0<=WORD)||LA53_0==INT) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3121:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTAlgorithmParser.g:3121:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalSTAlgorithmParser.g:3122:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalSTAlgorithmParser.g:3122:5: ( ruleSTNumericLiteralType )
                    // InternalSTAlgorithmParser.g:3123:6: ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_56);
                    ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_57); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:3142:3: ( ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) ) )
            // InternalSTAlgorithmParser.g:3143:4: ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) )
            {
            // InternalSTAlgorithmParser.g:3143:4: ( (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL ) )
            // InternalSTAlgorithmParser.g:3144:5: (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL )
            {
            // InternalSTAlgorithmParser.g:3144:5: (lv_value_2_1= ruleBoolLiteral | lv_value_2_2= ruleNumber | lv_value_2_3= RULE_NON_DECIMAL )
            int alt54=3;
            switch ( input.LA(1) ) {
            case FALSE:
            case TRUE:
                {
                alt54=1;
                }
                break;
            case PlusSign:
            case HyphenMinus:
            case RULE_INT:
                {
                alt54=2;
                }
                break;
            case RULE_NON_DECIMAL:
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
                    // InternalSTAlgorithmParser.g:3145:6: lv_value_2_1= ruleBoolLiteral
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
                    // InternalSTAlgorithmParser.g:3161:6: lv_value_2_2= ruleNumber
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
                    // InternalSTAlgorithmParser.g:3177:6: lv_value_2_3= RULE_NON_DECIMAL
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
    // InternalSTAlgorithmParser.g:3198:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3198:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalSTAlgorithmParser.g:3199:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3205:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3211:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalSTAlgorithmParser.g:3212:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalSTAlgorithmParser.g:3212:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt55=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt55=1;
                }
                break;
            case D:
                {
                alt55=2;
                }
                break;
            case LD:
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
                    // InternalSTAlgorithmParser.g:3213:3: this_STDateType_0= ruleSTDateType
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
                    // InternalSTAlgorithmParser.g:3224:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3230:3: kw= LD
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
    // InternalSTAlgorithmParser.g:3239:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3239:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalSTAlgorithmParser.g:3240:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalSTAlgorithmParser.g:3246:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3252:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalSTAlgorithmParser.g:3253:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalSTAlgorithmParser.g:3253:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalSTAlgorithmParser.g:3254:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalSTAlgorithmParser.g:3254:3: ( ( ruleSTDateLiteralType ) )
            // InternalSTAlgorithmParser.g:3255:4: ( ruleSTDateLiteralType )
            {
            // InternalSTAlgorithmParser.g:3255:4: ( ruleSTDateLiteralType )
            // InternalSTAlgorithmParser.g:3256:5: ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_56);
            ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:3274:3: ( (lv_value_2_0= ruleDate ) )
            // InternalSTAlgorithmParser.g:3275:4: (lv_value_2_0= ruleDate )
            {
            // InternalSTAlgorithmParser.g:3275:4: (lv_value_2_0= ruleDate )
            // InternalSTAlgorithmParser.g:3276:5: lv_value_2_0= ruleDate
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
    // InternalSTAlgorithmParser.g:3297:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3297:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalSTAlgorithmParser.g:3298:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3304:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3310:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalSTAlgorithmParser.g:3311:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalSTAlgorithmParser.g:3311:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt56=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt56=1;
                }
                break;
            case T:
                {
                alt56=2;
                }
                break;
            case LT:
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
                    // InternalSTAlgorithmParser.g:3312:3: this_STAnyDurationType_0= ruleSTAnyDurationType
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
                    // InternalSTAlgorithmParser.g:3323:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3329:3: kw= LT
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
    // InternalSTAlgorithmParser.g:3338:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3338:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalSTAlgorithmParser.g:3339:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalSTAlgorithmParser.g:3345:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3351:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalSTAlgorithmParser.g:3352:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalSTAlgorithmParser.g:3352:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalSTAlgorithmParser.g:3353:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalSTAlgorithmParser.g:3353:3: ( ( ruleSTTimeLiteralType ) )
            // InternalSTAlgorithmParser.g:3354:4: ( ruleSTTimeLiteralType )
            {
            // InternalSTAlgorithmParser.g:3354:4: ( ruleSTTimeLiteralType )
            // InternalSTAlgorithmParser.g:3355:5: ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_56);
            ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:3373:3: ( (lv_value_2_0= ruleTime ) )
            // InternalSTAlgorithmParser.g:3374:4: (lv_value_2_0= ruleTime )
            {
            // InternalSTAlgorithmParser.g:3374:4: (lv_value_2_0= ruleTime )
            // InternalSTAlgorithmParser.g:3375:5: lv_value_2_0= ruleTime
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
    // InternalSTAlgorithmParser.g:3396:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3396:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalSTAlgorithmParser.g:3397:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalSTAlgorithmParser.g:3403:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3409:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalSTAlgorithmParser.g:3410:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalSTAlgorithmParser.g:3410:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalSTAlgorithmParser.g:3411:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalSTAlgorithmParser.g:3411:3: ( ( ruleSTTimeOfDayType ) )
            // InternalSTAlgorithmParser.g:3412:4: ( ruleSTTimeOfDayType )
            {
            // InternalSTAlgorithmParser.g:3412:4: ( ruleSTTimeOfDayType )
            // InternalSTAlgorithmParser.g:3413:5: ruleSTTimeOfDayType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_56);
            ruleSTTimeOfDayType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:3431:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalSTAlgorithmParser.g:3432:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalSTAlgorithmParser.g:3432:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalSTAlgorithmParser.g:3433:5: lv_value_2_0= ruleTimeOfDay
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
    // InternalSTAlgorithmParser.g:3454:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3454:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalSTAlgorithmParser.g:3455:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalSTAlgorithmParser.g:3461:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3467:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalSTAlgorithmParser.g:3468:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalSTAlgorithmParser.g:3468:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalSTAlgorithmParser.g:3469:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalSTAlgorithmParser.g:3469:3: ( ( ruleSTDateAndTimeType ) )
            // InternalSTAlgorithmParser.g:3470:4: ( ruleSTDateAndTimeType )
            {
            // InternalSTAlgorithmParser.g:3470:4: ( ruleSTDateAndTimeType )
            // InternalSTAlgorithmParser.g:3471:5: ruleSTDateAndTimeType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_56);
            ruleSTDateAndTimeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTAlgorithmParser.g:3489:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalSTAlgorithmParser.g:3490:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalSTAlgorithmParser.g:3490:4: (lv_value_2_0= ruleDateAndTime )
            // InternalSTAlgorithmParser.g:3491:5: lv_value_2_0= ruleDateAndTime
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
    // InternalSTAlgorithmParser.g:3512:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3512:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalSTAlgorithmParser.g:3513:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalSTAlgorithmParser.g:3519:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING ) ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_1=null;
        Token lv_value_2_2=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3525:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING ) ) ) ) )
            // InternalSTAlgorithmParser.g:3526:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING ) ) ) )
            {
            // InternalSTAlgorithmParser.g:3526:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING ) ) ) )
            // InternalSTAlgorithmParser.g:3527:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING ) ) )
            {
            // InternalSTAlgorithmParser.g:3527:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==WSTRING||LA57_0==STRING||LA57_0==WCHAR||LA57_0==CHAR) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3528:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTAlgorithmParser.g:3528:4: ( ( ruleSTAnyCharsType ) )
                    // InternalSTAlgorithmParser.g:3529:5: ( ruleSTAnyCharsType )
                    {
                    // InternalSTAlgorithmParser.g:3529:5: ( ruleSTAnyCharsType )
                    // InternalSTAlgorithmParser.g:3530:6: ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_56);
                    ruleSTAnyCharsType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:3549:3: ( ( (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING ) ) )
            // InternalSTAlgorithmParser.g:3550:4: ( (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING ) )
            {
            // InternalSTAlgorithmParser.g:3550:4: ( (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING ) )
            // InternalSTAlgorithmParser.g:3551:5: (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING )
            {
            // InternalSTAlgorithmParser.g:3551:5: (lv_value_2_1= RULE_STRING | lv_value_2_2= RULE_WSTRING )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==RULE_STRING) ) {
                alt58=1;
            }
            else if ( (LA58_0==RULE_WSTRING) ) {
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
                    // InternalSTAlgorithmParser.g:3552:6: lv_value_2_1= RULE_STRING
                    {
                    lv_value_2_1=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_2_1, grammarAccess.getSTStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"value",
                      							lv_value_2_1,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STRING");
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3567:6: lv_value_2_2= RULE_WSTRING
                    {
                    lv_value_2_2=(Token)match(input,RULE_WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_2_2, grammarAccess.getSTStringLiteralAccess().getValueWSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"value",
                      							lv_value_2_2,
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


    // $ANTLR start "entryRuleSTAnyType"
    // InternalSTAlgorithmParser.g:3588:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalSTAlgorithmParser.g:3588:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalSTAlgorithmParser.g:3589:2: iv_ruleSTAnyType= ruleSTAnyType EOF
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
    // InternalSTAlgorithmParser.g:3595:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3601:2: ( (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalSTAlgorithmParser.g:3602:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalSTAlgorithmParser.g:3602:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==RULE_ID) ) {
                alt59=1;
            }
            else if ( ((LA59_0>=LDATE_AND_TIME && LA59_0<=DATE_AND_TIME)||(LA59_0>=LTIME_OF_DAY && LA59_0<=TIME_OF_DAY)||LA59_0==WSTRING||LA59_0==STRING||LA59_0==DWORD||(LA59_0>=LDATE && LA59_0<=ULINT)||(LA59_0>=USINT && LA59_0<=WCHAR)||(LA59_0>=BOOL && LA59_0<=BYTE)||(LA59_0>=CHAR && LA59_0<=DINT)||(LA59_0>=LINT && LA59_0<=SINT)||LA59_0==TIME||(LA59_0>=UINT && LA59_0<=WORD)||(LA59_0>=INT && LA59_0<=LDT)||LA59_0==TOD||LA59_0==DT) ) {
                alt59=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3603:3: this_ID_0= RULE_ID
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
                    // InternalSTAlgorithmParser.g:3611:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
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
    // InternalSTAlgorithmParser.g:3625:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalSTAlgorithmParser.g:3625:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalSTAlgorithmParser.g:3626:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
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
    // InternalSTAlgorithmParser.g:3632:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3638:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalSTAlgorithmParser.g:3639:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalSTAlgorithmParser.g:3639:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt60=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt60=1;
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
                alt60=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt60=3;
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
                alt60=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
                {
                alt60=5;
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
                    // InternalSTAlgorithmParser.g:3640:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalSTAlgorithmParser.g:3651:3: this_STAnyNumType_1= ruleSTAnyNumType
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
                    // InternalSTAlgorithmParser.g:3662:3: this_STAnyDurationType_2= ruleSTAnyDurationType
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
                    // InternalSTAlgorithmParser.g:3673:3: this_STAnyDateType_3= ruleSTAnyDateType
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
                    // InternalSTAlgorithmParser.g:3684:3: this_STAnyCharsType_4= ruleSTAnyCharsType
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
    // InternalSTAlgorithmParser.g:3698:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalSTAlgorithmParser.g:3698:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalSTAlgorithmParser.g:3699:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
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
    // InternalSTAlgorithmParser.g:3705:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3711:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalSTAlgorithmParser.g:3712:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalSTAlgorithmParser.g:3712:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt61=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt61=1;
                }
                break;
            case BYTE:
                {
                alt61=2;
                }
                break;
            case WORD:
                {
                alt61=3;
                }
                break;
            case DWORD:
                {
                alt61=4;
                }
                break;
            case LWORD:
                {
                alt61=5;
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
                    // InternalSTAlgorithmParser.g:3713:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3719:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3725:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:3731:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTAlgorithmParser.g:3737:3: kw= LWORD
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
    // InternalSTAlgorithmParser.g:3746:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalSTAlgorithmParser.g:3746:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalSTAlgorithmParser.g:3747:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
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
    // InternalSTAlgorithmParser.g:3753:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3759:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalSTAlgorithmParser.g:3760:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalSTAlgorithmParser.g:3760:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt62=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt62=1;
                }
                break;
            case INT:
                {
                alt62=2;
                }
                break;
            case DINT:
                {
                alt62=3;
                }
                break;
            case LINT:
                {
                alt62=4;
                }
                break;
            case USINT:
                {
                alt62=5;
                }
                break;
            case UINT:
                {
                alt62=6;
                }
                break;
            case UDINT:
                {
                alt62=7;
                }
                break;
            case ULINT:
                {
                alt62=8;
                }
                break;
            case REAL:
                {
                alt62=9;
                }
                break;
            case LREAL:
                {
                alt62=10;
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
                    // InternalSTAlgorithmParser.g:3761:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3767:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3773:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:3779:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTAlgorithmParser.g:3785:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTAlgorithmParser.g:3791:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTAlgorithmParser.g:3797:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTAlgorithmParser.g:3803:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTAlgorithmParser.g:3809:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSTAlgorithmParser.g:3815:3: kw= LREAL
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
    // InternalSTAlgorithmParser.g:3824:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalSTAlgorithmParser.g:3824:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalSTAlgorithmParser.g:3825:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
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
    // InternalSTAlgorithmParser.g:3831:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3837:2: ( (kw= TIME | kw= LTIME ) )
            // InternalSTAlgorithmParser.g:3838:2: (kw= TIME | kw= LTIME )
            {
            // InternalSTAlgorithmParser.g:3838:2: (kw= TIME | kw= LTIME )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==TIME) ) {
                alt63=1;
            }
            else if ( (LA63_0==LTIME) ) {
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
                    // InternalSTAlgorithmParser.g:3839:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3845:3: kw= LTIME
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
    // InternalSTAlgorithmParser.g:3854:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalSTAlgorithmParser.g:3854:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalSTAlgorithmParser.g:3855:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
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
    // InternalSTAlgorithmParser.g:3861:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3867:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalSTAlgorithmParser.g:3868:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalSTAlgorithmParser.g:3868:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt64=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt64=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt64=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt64=3;
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
                    // InternalSTAlgorithmParser.g:3869:3: this_STDateType_0= ruleSTDateType
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
                    // InternalSTAlgorithmParser.g:3880:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
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
                    // InternalSTAlgorithmParser.g:3891:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
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
    // InternalSTAlgorithmParser.g:3905:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalSTAlgorithmParser.g:3905:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalSTAlgorithmParser.g:3906:2: iv_ruleSTDateType= ruleSTDateType EOF
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
    // InternalSTAlgorithmParser.g:3912:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3918:2: ( (kw= DATE | kw= LDATE ) )
            // InternalSTAlgorithmParser.g:3919:2: (kw= DATE | kw= LDATE )
            {
            // InternalSTAlgorithmParser.g:3919:2: (kw= DATE | kw= LDATE )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==DATE) ) {
                alt65=1;
            }
            else if ( (LA65_0==LDATE) ) {
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
                    // InternalSTAlgorithmParser.g:3920:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3926:3: kw= LDATE
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
    // InternalSTAlgorithmParser.g:3935:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalSTAlgorithmParser.g:3935:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalSTAlgorithmParser.g:3936:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
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
    // InternalSTAlgorithmParser.g:3942:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3948:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalSTAlgorithmParser.g:3949:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalSTAlgorithmParser.g:3949:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt66=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt66=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt66=2;
                }
                break;
            case TOD:
                {
                alt66=3;
                }
                break;
            case LTOD:
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
                    // InternalSTAlgorithmParser.g:3950:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3956:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3962:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:3968:3: kw= LTOD
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
    // InternalSTAlgorithmParser.g:3977:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalSTAlgorithmParser.g:3977:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalSTAlgorithmParser.g:3978:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
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
    // InternalSTAlgorithmParser.g:3984:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3990:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalSTAlgorithmParser.g:3991:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalSTAlgorithmParser.g:3991:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt67=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt67=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt67=2;
                }
                break;
            case DT:
                {
                alt67=3;
                }
                break;
            case LDT:
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
                    // InternalSTAlgorithmParser.g:3992:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3998:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:4004:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:4010:3: kw= LDT
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
    // InternalSTAlgorithmParser.g:4019:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalSTAlgorithmParser.g:4019:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalSTAlgorithmParser.g:4020:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
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
    // InternalSTAlgorithmParser.g:4026:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4032:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalSTAlgorithmParser.g:4033:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalSTAlgorithmParser.g:4033:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt68=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt68=1;
                }
                break;
            case WSTRING:
                {
                alt68=2;
                }
                break;
            case CHAR:
                {
                alt68=3;
                }
                break;
            case WCHAR:
                {
                alt68=4;
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
                    // InternalSTAlgorithmParser.g:4034:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4040:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:4046:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:4052:3: kw= WCHAR
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
    // InternalSTAlgorithmParser.g:4061:1: entryRuleBoolLiteral returns [String current=null] : iv_ruleBoolLiteral= ruleBoolLiteral EOF ;
    public final String entryRuleBoolLiteral() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoolLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:4061:51: (iv_ruleBoolLiteral= ruleBoolLiteral EOF )
            // InternalSTAlgorithmParser.g:4062:2: iv_ruleBoolLiteral= ruleBoolLiteral EOF
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
    // InternalSTAlgorithmParser.g:4068:1: ruleBoolLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE ) ;
    public final AntlrDatatypeRuleToken ruleBoolLiteral() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4074:2: ( (kw= TRUE | kw= FALSE ) )
            // InternalSTAlgorithmParser.g:4075:2: (kw= TRUE | kw= FALSE )
            {
            // InternalSTAlgorithmParser.g:4075:2: (kw= TRUE | kw= FALSE )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==TRUE) ) {
                alt69=1;
            }
            else if ( (LA69_0==FALSE) ) {
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
                    // InternalSTAlgorithmParser.g:4076:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getBoolLiteralAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4082:3: kw= FALSE
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
    // InternalSTAlgorithmParser.g:4091:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;


        try {
            // InternalSTAlgorithmParser.g:4091:46: (iv_ruleNumber= ruleNumber EOF )
            // InternalSTAlgorithmParser.g:4092:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalSTAlgorithmParser.g:4098:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_EXT_INT_4=null;
        Token this_INT_5=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4104:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) )
            // InternalSTAlgorithmParser.g:4105:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            {
            // InternalSTAlgorithmParser.g:4105:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            // InternalSTAlgorithmParser.g:4106:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            {
            // InternalSTAlgorithmParser.g:4106:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt70=3;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==PlusSign) ) {
                alt70=1;
            }
            else if ( (LA70_0==HyphenMinus) ) {
                alt70=2;
            }
            switch (alt70) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4107:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:4113:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1());
              		
            }
            // InternalSTAlgorithmParser.g:4126:3: ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==FullStop) ) {
                int LA72_1 = input.LA(2);

                if ( (LA72_1==RULE_INT) ) {
                    int LA72_3 = input.LA(3);

                    if ( (synpred5_InternalSTAlgorithmParser()) ) {
                        alt72=1;
                    }
                }
                else if ( (LA72_1==RULE_EXT_INT) && (synpred5_InternalSTAlgorithmParser())) {
                    alt72=1;
                }
            }
            switch (alt72) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4127:4: ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    {
                    // InternalSTAlgorithmParser.g:4127:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTAlgorithmParser.g:4128:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:4135:4: (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==RULE_EXT_INT) ) {
                        alt71=1;
                    }
                    else if ( (LA71_0==RULE_INT) ) {
                        alt71=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 71, 0, input);

                        throw nvae;
                    }
                    switch (alt71) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:4136:5: this_EXT_INT_4= RULE_EXT_INT
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
                            // InternalSTAlgorithmParser.g:4144:5: this_INT_5= RULE_INT
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
    // InternalSTAlgorithmParser.g:4157:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalSTAlgorithmParser.g:4157:44: (iv_ruleDate= ruleDate EOF )
            // InternalSTAlgorithmParser.g:4158:2: iv_ruleDate= ruleDate EOF
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
    // InternalSTAlgorithmParser.g:4164:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4170:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTAlgorithmParser.g:4171:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTAlgorithmParser.g:4171:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTAlgorithmParser.g:4172:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_55); if (state.failed) return current;
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
    // InternalSTAlgorithmParser.g:4207:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalSTAlgorithmParser.g:4207:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalSTAlgorithmParser.g:4208:2: iv_ruleDateAndTime= ruleDateAndTime EOF
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
    // InternalSTAlgorithmParser.g:4214:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) ;
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
            // InternalSTAlgorithmParser.g:4220:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) )
            // InternalSTAlgorithmParser.g:4221:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            {
            // InternalSTAlgorithmParser.g:4221:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            // InternalSTAlgorithmParser.g:4222:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_55); if (state.failed) return current;
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
            kw=(Token)match(input,Colon,FOLLOW_55); if (state.failed) return current;
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
            kw=(Token)match(input,Colon,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_9());
              		
            }
            this_INT_10=(Token)match(input,RULE_INT,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_10);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
              		
            }
            // InternalSTAlgorithmParser.g:4289:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==FullStop) ) {
                int LA73_1 = input.LA(2);

                if ( (LA73_1==RULE_INT) ) {
                    int LA73_3 = input.LA(3);

                    if ( (synpred6_InternalSTAlgorithmParser()) ) {
                        alt73=1;
                    }
                }
            }
            switch (alt73) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4290:4: ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT
                    {
                    // InternalSTAlgorithmParser.g:4290:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTAlgorithmParser.g:4291:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_55); if (state.failed) return current;
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
    // InternalSTAlgorithmParser.g:4310:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalSTAlgorithmParser.g:4310:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalSTAlgorithmParser.g:4311:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalSTAlgorithmParser.g:4317:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4323:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) )
            // InternalSTAlgorithmParser.g:4324:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            {
            // InternalSTAlgorithmParser.g:4324:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            // InternalSTAlgorithmParser.g:4325:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_55); if (state.failed) return current;
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
            kw=(Token)match(input,Colon,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTAlgorithmParser.g:4356:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==FullStop) ) {
                int LA74_1 = input.LA(2);

                if ( (LA74_1==RULE_INT) ) {
                    int LA74_3 = input.LA(3);

                    if ( (synpred7_InternalSTAlgorithmParser()) ) {
                        alt74=1;
                    }
                }
            }
            switch (alt74) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4357:4: ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT
                    {
                    // InternalSTAlgorithmParser.g:4357:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTAlgorithmParser.g:4358:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_55); if (state.failed) return current;
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
    // InternalSTAlgorithmParser.g:4377:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;


        try {
            // InternalSTAlgorithmParser.g:4377:44: (iv_ruleTime= ruleTime EOF )
            // InternalSTAlgorithmParser.g:4378:2: iv_ruleTime= ruleTime EOF
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
    // InternalSTAlgorithmParser.g:4384:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Number_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4390:2: ( (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ )
            // InternalSTAlgorithmParser.g:4391:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            {
            // InternalSTAlgorithmParser.g:4391:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            int cnt77=0;
            loop77:
            do {
                int alt77=2;
                switch ( input.LA(1) ) {
                case PlusSign:
                    {
                    int LA77_2 = input.LA(2);

                    if ( (LA77_2==RULE_INT) ) {
                        int LA77_5 = input.LA(3);

                        if ( (LA77_5==FullStop) ) {
                            int LA77_6 = input.LA(4);

                            if ( (LA77_6==RULE_INT) ) {
                                int LA77_7 = input.LA(5);

                                if ( ((LA77_7>=MS && LA77_7<=NS)||LA77_7==US||(LA77_7>=D && LA77_7<=S)) ) {
                                    alt77=1;
                                }


                            }
                            else if ( (LA77_6==RULE_EXT_INT) ) {
                                int LA77_8 = input.LA(5);

                                if ( ((LA77_8>=MS && LA77_8<=NS)||LA77_8==US||(LA77_8>=D && LA77_8<=S)) ) {
                                    alt77=1;
                                }


                            }


                        }
                        else if ( ((LA77_5>=MS && LA77_5<=NS)||LA77_5==US||(LA77_5>=D && LA77_5<=S)) ) {
                            alt77=1;
                        }


                    }


                    }
                    break;
                case HyphenMinus:
                    {
                    int LA77_3 = input.LA(2);

                    if ( (LA77_3==RULE_INT) ) {
                        int LA77_5 = input.LA(3);

                        if ( (LA77_5==FullStop) ) {
                            int LA77_6 = input.LA(4);

                            if ( (LA77_6==RULE_INT) ) {
                                int LA77_7 = input.LA(5);

                                if ( ((LA77_7>=MS && LA77_7<=NS)||LA77_7==US||(LA77_7>=D && LA77_7<=S)) ) {
                                    alt77=1;
                                }


                            }
                            else if ( (LA77_6==RULE_EXT_INT) ) {
                                int LA77_8 = input.LA(5);

                                if ( ((LA77_8>=MS && LA77_8<=NS)||LA77_8==US||(LA77_8>=D && LA77_8<=S)) ) {
                                    alt77=1;
                                }


                            }


                        }
                        else if ( ((LA77_5>=MS && LA77_5<=NS)||LA77_5==US||(LA77_5>=D && LA77_5<=S)) ) {
                            alt77=1;
                        }


                    }


                    }
                    break;
                case RULE_INT:
                    {
                    alt77=1;
                    }
                    break;

                }

                switch (alt77) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:4392:3: this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )?
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getTimeAccess().getNumberParserRuleCall_0());
            	      		
            	    }
            	    pushFollow(FOLLOW_63);
            	    this_Number_0=ruleNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_Number_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }
            	    // InternalSTAlgorithmParser.g:4402:3: (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS )
            	    int alt75=7;
            	    switch ( input.LA(1) ) {
            	    case D:
            	        {
            	        alt75=1;
            	        }
            	        break;
            	    case H:
            	        {
            	        alt75=2;
            	        }
            	        break;
            	    case M:
            	        {
            	        alt75=3;
            	        }
            	        break;
            	    case S:
            	        {
            	        alt75=4;
            	        }
            	        break;
            	    case MS:
            	        {
            	        alt75=5;
            	        }
            	        break;
            	    case US:
            	        {
            	        alt75=6;
            	        }
            	        break;
            	    case NS:
            	        {
            	        alt75=7;
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
            	            // InternalSTAlgorithmParser.g:4403:4: kw= D
            	            {
            	            kw=(Token)match(input,D,FOLLOW_64); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getDKeyword_1_0());
            	              			
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTAlgorithmParser.g:4409:4: kw= H
            	            {
            	            kw=(Token)match(input,H,FOLLOW_64); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getHKeyword_1_1());
            	              			
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTAlgorithmParser.g:4415:4: kw= M
            	            {
            	            kw=(Token)match(input,M,FOLLOW_64); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMKeyword_1_2());
            	              			
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTAlgorithmParser.g:4421:4: kw= S
            	            {
            	            kw=(Token)match(input,S,FOLLOW_64); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getSKeyword_1_3());
            	              			
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalSTAlgorithmParser.g:4427:4: kw= MS
            	            {
            	            kw=(Token)match(input,MS,FOLLOW_64); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMSKeyword_1_4());
            	              			
            	            }

            	            }
            	            break;
            	        case 6 :
            	            // InternalSTAlgorithmParser.g:4433:4: kw= US
            	            {
            	            kw=(Token)match(input,US,FOLLOW_64); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getUSKeyword_1_5());
            	              			
            	            }

            	            }
            	            break;
            	        case 7 :
            	            // InternalSTAlgorithmParser.g:4439:4: kw= NS
            	            {
            	            kw=(Token)match(input,NS,FOLLOW_64); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getNSKeyword_1_6());
            	              			
            	            }

            	            }
            	            break;

            	    }

            	    // InternalSTAlgorithmParser.g:4445:3: (kw= KW__ )?
            	    int alt76=2;
            	    int LA76_0 = input.LA(1);

            	    if ( (LA76_0==KW__) ) {
            	        alt76=1;
            	    }
            	    switch (alt76) {
            	        case 1 :
            	            // InternalSTAlgorithmParser.g:4446:4: kw= KW__
            	            {
            	            kw=(Token)match(input,KW__,FOLLOW_65); if (state.failed) return current;
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
            	    if ( cnt77 >= 1 ) break loop77;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(77, input);
                        throw eee;
                }
                cnt77++;
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
    // InternalSTAlgorithmParser.g:4456:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4462:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTAlgorithmParser.g:4463:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTAlgorithmParser.g:4463:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTAlgorithmParser.g:4464:3: enumLiteral_0= FullStopFullStop
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
    // InternalSTAlgorithmParser.g:4473:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4479:2: ( (enumLiteral_0= OR ) )
            // InternalSTAlgorithmParser.g:4480:2: (enumLiteral_0= OR )
            {
            // InternalSTAlgorithmParser.g:4480:2: (enumLiteral_0= OR )
            // InternalSTAlgorithmParser.g:4481:3: enumLiteral_0= OR
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
    // InternalSTAlgorithmParser.g:4490:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4496:2: ( (enumLiteral_0= XOR ) )
            // InternalSTAlgorithmParser.g:4497:2: (enumLiteral_0= XOR )
            {
            // InternalSTAlgorithmParser.g:4497:2: (enumLiteral_0= XOR )
            // InternalSTAlgorithmParser.g:4498:3: enumLiteral_0= XOR
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
    // InternalSTAlgorithmParser.g:4507:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4513:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTAlgorithmParser.g:4514:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTAlgorithmParser.g:4514:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==AND) ) {
                alt78=1;
            }
            else if ( (LA78_0==Ampersand) ) {
                alt78=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4515:3: (enumLiteral_0= AND )
                    {
                    // InternalSTAlgorithmParser.g:4515:3: (enumLiteral_0= AND )
                    // InternalSTAlgorithmParser.g:4516:4: enumLiteral_0= AND
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
                    // InternalSTAlgorithmParser.g:4523:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTAlgorithmParser.g:4523:3: (enumLiteral_1= Ampersand )
                    // InternalSTAlgorithmParser.g:4524:4: enumLiteral_1= Ampersand
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
    // InternalSTAlgorithmParser.g:4534:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4540:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTAlgorithmParser.g:4541:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTAlgorithmParser.g:4541:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==EqualsSign) ) {
                alt79=1;
            }
            else if ( (LA79_0==LessThanSignGreaterThanSign) ) {
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
                    // InternalSTAlgorithmParser.g:4542:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:4542:3: (enumLiteral_0= EqualsSign )
                    // InternalSTAlgorithmParser.g:4543:4: enumLiteral_0= EqualsSign
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
                    // InternalSTAlgorithmParser.g:4550:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTAlgorithmParser.g:4550:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTAlgorithmParser.g:4551:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalSTAlgorithmParser.g:4561:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4567:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTAlgorithmParser.g:4568:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTAlgorithmParser.g:4568:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt80=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt80=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt80=2;
                }
                break;
            case GreaterThanSign:
                {
                alt80=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt80=4;
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
                    // InternalSTAlgorithmParser.g:4569:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTAlgorithmParser.g:4569:3: (enumLiteral_0= LessThanSign )
                    // InternalSTAlgorithmParser.g:4570:4: enumLiteral_0= LessThanSign
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
                    // InternalSTAlgorithmParser.g:4577:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:4577:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTAlgorithmParser.g:4578:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalSTAlgorithmParser.g:4585:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTAlgorithmParser.g:4585:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTAlgorithmParser.g:4586:4: enumLiteral_2= GreaterThanSign
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
                    // InternalSTAlgorithmParser.g:4593:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:4593:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTAlgorithmParser.g:4594:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalSTAlgorithmParser.g:4604:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4610:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTAlgorithmParser.g:4611:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTAlgorithmParser.g:4611:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==PlusSign) ) {
                alt81=1;
            }
            else if ( (LA81_0==HyphenMinus) ) {
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
                    // InternalSTAlgorithmParser.g:4612:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTAlgorithmParser.g:4612:3: (enumLiteral_0= PlusSign )
                    // InternalSTAlgorithmParser.g:4613:4: enumLiteral_0= PlusSign
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
                    // InternalSTAlgorithmParser.g:4620:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTAlgorithmParser.g:4620:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTAlgorithmParser.g:4621:4: enumLiteral_1= HyphenMinus
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
    // InternalSTAlgorithmParser.g:4631:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4637:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTAlgorithmParser.g:4638:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTAlgorithmParser.g:4638:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt82=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt82=1;
                }
                break;
            case Solidus:
                {
                alt82=2;
                }
                break;
            case MOD:
                {
                alt82=3;
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
                    // InternalSTAlgorithmParser.g:4639:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTAlgorithmParser.g:4639:3: (enumLiteral_0= Asterisk )
                    // InternalSTAlgorithmParser.g:4640:4: enumLiteral_0= Asterisk
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
                    // InternalSTAlgorithmParser.g:4647:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTAlgorithmParser.g:4647:3: (enumLiteral_1= Solidus )
                    // InternalSTAlgorithmParser.g:4648:4: enumLiteral_1= Solidus
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
                    // InternalSTAlgorithmParser.g:4655:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTAlgorithmParser.g:4655:3: (enumLiteral_2= MOD )
                    // InternalSTAlgorithmParser.g:4656:4: enumLiteral_2= MOD
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
    // InternalSTAlgorithmParser.g:4666:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4672:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTAlgorithmParser.g:4673:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTAlgorithmParser.g:4673:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTAlgorithmParser.g:4674:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalSTAlgorithmParser.g:4683:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4689:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTAlgorithmParser.g:4690:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTAlgorithmParser.g:4690:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt83=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt83=1;
                }
                break;
            case PlusSign:
                {
                alt83=2;
                }
                break;
            case NOT:
                {
                alt83=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }

            switch (alt83) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4691:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTAlgorithmParser.g:4691:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTAlgorithmParser.g:4692:4: enumLiteral_0= HyphenMinus
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
                    // InternalSTAlgorithmParser.g:4699:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTAlgorithmParser.g:4699:3: (enumLiteral_1= PlusSign )
                    // InternalSTAlgorithmParser.g:4700:4: enumLiteral_1= PlusSign
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
                    // InternalSTAlgorithmParser.g:4707:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTAlgorithmParser.g:4707:3: (enumLiteral_2= NOT )
                    // InternalSTAlgorithmParser.g:4708:4: enumLiteral_2= NOT
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
    // InternalSTAlgorithmParser.g:4718:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4724:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalSTAlgorithmParser.g:4725:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalSTAlgorithmParser.g:4725:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt84=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt84=1;
                }
                break;
            case D_1:
                {
                alt84=2;
                }
                break;
            case W:
                {
                alt84=3;
                }
                break;
            case B:
                {
                alt84=4;
                }
                break;
            case X:
                {
                alt84=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4726:3: (enumLiteral_0= L )
                    {
                    // InternalSTAlgorithmParser.g:4726:3: (enumLiteral_0= L )
                    // InternalSTAlgorithmParser.g:4727:4: enumLiteral_0= L
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
                    // InternalSTAlgorithmParser.g:4734:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTAlgorithmParser.g:4734:3: (enumLiteral_1= D_1 )
                    // InternalSTAlgorithmParser.g:4735:4: enumLiteral_1= D_1
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
                    // InternalSTAlgorithmParser.g:4742:3: (enumLiteral_2= W )
                    {
                    // InternalSTAlgorithmParser.g:4742:3: (enumLiteral_2= W )
                    // InternalSTAlgorithmParser.g:4743:4: enumLiteral_2= W
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
                    // InternalSTAlgorithmParser.g:4750:3: (enumLiteral_3= B )
                    {
                    // InternalSTAlgorithmParser.g:4750:3: (enumLiteral_3= B )
                    // InternalSTAlgorithmParser.g:4751:4: enumLiteral_3= B
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
                    // InternalSTAlgorithmParser.g:4758:3: (enumLiteral_4= X )
                    {
                    // InternalSTAlgorithmParser.g:4758:3: (enumLiteral_4= X )
                    // InternalSTAlgorithmParser.g:4759:4: enumLiteral_4= X
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
        // InternalSTAlgorithmParser.g:797:6: ( ruleSTAssignmentStatement )
        // InternalSTAlgorithmParser.g:797:7: ruleSTAssignmentStatement
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
        // InternalSTAlgorithmParser.g:1502:4: ( ( ruleSTStatement ) )
        // InternalSTAlgorithmParser.g:1502:5: ( ruleSTStatement )
        {
        // InternalSTAlgorithmParser.g:1502:5: ( ruleSTStatement )
        // InternalSTAlgorithmParser.g:1503:5: ruleSTStatement
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
        // InternalSTAlgorithmParser.g:2574:4: ( ruleSTAccessExpression )
        // InternalSTAlgorithmParser.g:2574:5: ruleSTAccessExpression
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
        // InternalSTAlgorithmParser.g:2869:5: ( LeftParenthesis )
        // InternalSTAlgorithmParser.g:2869:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_InternalSTAlgorithmParser

    // $ANTLR start synpred5_InternalSTAlgorithmParser
    public final void synpred5_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:4128:5: ( FullStop )
        // InternalSTAlgorithmParser.g:4128:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_InternalSTAlgorithmParser

    // $ANTLR start synpred6_InternalSTAlgorithmParser
    public final void synpred6_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:4291:5: ( FullStop )
        // InternalSTAlgorithmParser.g:4291:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_InternalSTAlgorithmParser

    // $ANTLR start synpred7_InternalSTAlgorithmParser
    public final void synpred7_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:4358:5: ( FullStop )
        // InternalSTAlgorithmParser.g:4358:6: FullStop
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


    protected DFA16 dfa16 = new DFA16(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA42 dfa42 = new DFA42(this);
    protected DFA49 dfa49 = new DFA49(this);
    static final String dfa_1s = "\70\uffff";
    static final String dfa_2s = "\1\4\5\uffff\55\0\5\uffff";
    static final String dfa_3s = "\1\167\5\uffff\55\0\5\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\55\uffff\1\10\1\11\1\12\1\6\1\7";
    static final String dfa_5s = "\6\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\5\uffff}>";
    static final String[] dfa_6s = {
            "\1\52\1\51\1\uffff\1\46\1\45\6\uffff\1\64\4\uffff\1\56\1\uffff\1\5\1\63\1\55\1\uffff\1\13\1\uffff\1\30\1\36\1\26\1\42\1\14\1\23\1\24\1\uffff\1\21\1\60\1\4\1\10\1\11\1\2\1\57\1\35\1\17\1\uffff\1\65\1\20\1\50\1\25\1\15\1\uffff\1\41\1\27\1\22\1\12\1\uffff\1\3\1\16\1\54\2\uffff\1\47\21\uffff\1\53\1\1\1\40\1\44\10\uffff\1\6\2\uffff\1\31\1\uffff\1\32\7\uffff\1\37\3\uffff\1\43\4\uffff\1\34\1\33\1\uffff\1\7\1\61\1\62",
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

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "750:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA16_6 = input.LA(1);

                         
                        int index16_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA16_7 = input.LA(1);

                         
                        int index16_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA16_8 = input.LA(1);

                         
                        int index16_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA16_9 = input.LA(1);

                         
                        int index16_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_9);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA16_10 = input.LA(1);

                         
                        int index16_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA16_11 = input.LA(1);

                         
                        int index16_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_11);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA16_12 = input.LA(1);

                         
                        int index16_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_12);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA16_13 = input.LA(1);

                         
                        int index16_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_13);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA16_14 = input.LA(1);

                         
                        int index16_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_14);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA16_15 = input.LA(1);

                         
                        int index16_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_15);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA16_16 = input.LA(1);

                         
                        int index16_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_16);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA16_17 = input.LA(1);

                         
                        int index16_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_17);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA16_18 = input.LA(1);

                         
                        int index16_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_18);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA16_19 = input.LA(1);

                         
                        int index16_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_19);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA16_20 = input.LA(1);

                         
                        int index16_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_20);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA16_21 = input.LA(1);

                         
                        int index16_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_21);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA16_22 = input.LA(1);

                         
                        int index16_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_22);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA16_23 = input.LA(1);

                         
                        int index16_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_23);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA16_24 = input.LA(1);

                         
                        int index16_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_24);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA16_25 = input.LA(1);

                         
                        int index16_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_25);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA16_26 = input.LA(1);

                         
                        int index16_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_26);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA16_27 = input.LA(1);

                         
                        int index16_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_27);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA16_28 = input.LA(1);

                         
                        int index16_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_28);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA16_29 = input.LA(1);

                         
                        int index16_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_29);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA16_30 = input.LA(1);

                         
                        int index16_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_30);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA16_31 = input.LA(1);

                         
                        int index16_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_31);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA16_32 = input.LA(1);

                         
                        int index16_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_32);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA16_33 = input.LA(1);

                         
                        int index16_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_33);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA16_34 = input.LA(1);

                         
                        int index16_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_34);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA16_35 = input.LA(1);

                         
                        int index16_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_35);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA16_36 = input.LA(1);

                         
                        int index16_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_36);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA16_37 = input.LA(1);

                         
                        int index16_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_37);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA16_38 = input.LA(1);

                         
                        int index16_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_38);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA16_39 = input.LA(1);

                         
                        int index16_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_39);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA16_40 = input.LA(1);

                         
                        int index16_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_40);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA16_41 = input.LA(1);

                         
                        int index16_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_41);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA16_42 = input.LA(1);

                         
                        int index16_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_42);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA16_43 = input.LA(1);

                         
                        int index16_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_43);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA16_44 = input.LA(1);

                         
                        int index16_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_44);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA16_45 = input.LA(1);

                         
                        int index16_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_45);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA16_46 = input.LA(1);

                         
                        int index16_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_46);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA16_47 = input.LA(1);

                         
                        int index16_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_47);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA16_48 = input.LA(1);

                         
                        int index16_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_48);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA16_49 = input.LA(1);

                         
                        int index16_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_49);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA16_50 = input.LA(1);

                         
                        int index16_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 54;}

                        else if ( (true) ) {s = 55;}

                         
                        input.seek(index16_50);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 16, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_7s = "\73\uffff";
    static final String dfa_8s = "\1\1\72\uffff";
    static final String dfa_9s = "\1\4\2\uffff\55\0\13\uffff";
    static final String dfa_10s = "\1\167\2\uffff\55\0\13\uffff";
    static final String dfa_11s = "\1\uffff\1\2\60\uffff\11\1";
    static final String dfa_12s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\13\uffff}>";
    static final String[] dfa_13s = {
            "\1\47\1\46\1\uffff\1\43\1\42\6\uffff\1\70\1\1\3\uffff\1\53\1\uffff\1\66\1\67\1\52\1\uffff\1\10\1\uffff\1\25\1\33\1\23\1\37\1\11\1\20\1\21\1\uffff\1\16\1\55\1\65\1\5\1\6\1\63\1\54\1\32\1\14\1\1\1\71\1\15\1\45\1\22\1\12\1\uffff\1\36\1\24\1\17\1\7\1\uffff\1\64\1\13\1\51\1\uffff\1\1\1\44\21\uffff\1\50\1\62\1\35\1\41\10\uffff\1\3\2\uffff\1\26\1\uffff\1\27\3\uffff\1\72\3\uffff\1\34\3\uffff\1\40\4\uffff\1\31\1\30\1\uffff\1\4\1\56\1\57",
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
            "",
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

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "()* loopback of 1501:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_0 = input.LA(1);

                         
                        int index27_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA27_0==EOF||LA27_0==END_CASE||LA27_0==ELSE||LA27_0==NOT) ) {s = 1;}

                        else if ( (LA27_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA27_0==RULE_ID) ) {s = 4;}

                        else if ( (LA27_0==BOOL) ) {s = 5;}

                        else if ( (LA27_0==BYTE) ) {s = 6;}

                        else if ( (LA27_0==WORD) ) {s = 7;}

                        else if ( (LA27_0==DWORD) ) {s = 8;}

                        else if ( (LA27_0==LWORD) ) {s = 9;}

                        else if ( (LA27_0==SINT) ) {s = 10;}

                        else if ( (LA27_0==INT) ) {s = 11;}

                        else if ( (LA27_0==DINT) ) {s = 12;}

                        else if ( (LA27_0==LINT) ) {s = 13;}

                        else if ( (LA27_0==USINT) ) {s = 14;}

                        else if ( (LA27_0==UINT) ) {s = 15;}

                        else if ( (LA27_0==UDINT) ) {s = 16;}

                        else if ( (LA27_0==ULINT) ) {s = 17;}

                        else if ( (LA27_0==REAL) ) {s = 18;}

                        else if ( (LA27_0==LREAL) ) {s = 19;}

                        else if ( (LA27_0==TRUE) ) {s = 20;}

                        else if ( (LA27_0==FALSE) ) {s = 21;}

                        else if ( (LA27_0==PlusSign) ) {s = 22;}

                        else if ( (LA27_0==HyphenMinus) ) {s = 23;}

                        else if ( (LA27_0==RULE_INT) ) {s = 24;}

                        else if ( (LA27_0==RULE_NON_DECIMAL) ) {s = 25;}

                        else if ( (LA27_0==DATE) ) {s = 26;}

                        else if ( (LA27_0==LDATE) ) {s = 27;}

                        else if ( (LA27_0==D) ) {s = 28;}

                        else if ( (LA27_0==LD) ) {s = 29;}

                        else if ( (LA27_0==TIME) ) {s = 30;}

                        else if ( (LA27_0==LTIME) ) {s = 31;}

                        else if ( (LA27_0==T) ) {s = 32;}

                        else if ( (LA27_0==LT) ) {s = 33;}

                        else if ( (LA27_0==TIME_OF_DAY) ) {s = 34;}

                        else if ( (LA27_0==LTIME_OF_DAY) ) {s = 35;}

                        else if ( (LA27_0==TOD) ) {s = 36;}

                        else if ( (LA27_0==LTOD) ) {s = 37;}

                        else if ( (LA27_0==DATE_AND_TIME) ) {s = 38;}

                        else if ( (LA27_0==LDATE_AND_TIME) ) {s = 39;}

                        else if ( (LA27_0==DT) ) {s = 40;}

                        else if ( (LA27_0==LDT) ) {s = 41;}

                        else if ( (LA27_0==STRING) ) {s = 42;}

                        else if ( (LA27_0==WSTRING) ) {s = 43;}

                        else if ( (LA27_0==CHAR) ) {s = 44;}

                        else if ( (LA27_0==WCHAR) ) {s = 45;}

                        else if ( (LA27_0==RULE_STRING) ) {s = 46;}

                        else if ( (LA27_0==RULE_WSTRING) ) {s = 47;}

                        else if ( (LA27_0==IF) && (synpred2_InternalSTAlgorithmParser())) {s = 50;}

                        else if ( (LA27_0==CASE) && (synpred2_InternalSTAlgorithmParser())) {s = 51;}

                        else if ( (LA27_0==FOR) && (synpred2_InternalSTAlgorithmParser())) {s = 52;}

                        else if ( (LA27_0==WHILE) && (synpred2_InternalSTAlgorithmParser())) {s = 53;}

                        else if ( (LA27_0==REPEAT) && (synpred2_InternalSTAlgorithmParser())) {s = 54;}

                        else if ( (LA27_0==RETURN) && (synpred2_InternalSTAlgorithmParser())) {s = 55;}

                        else if ( (LA27_0==CONTINUE) && (synpred2_InternalSTAlgorithmParser())) {s = 56;}

                        else if ( (LA27_0==EXIT) && (synpred2_InternalSTAlgorithmParser())) {s = 57;}

                        else if ( (LA27_0==Semicolon) && (synpred2_InternalSTAlgorithmParser())) {s = 58;}

                         
                        input.seek(index27_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_3 = input.LA(1);

                         
                        int index27_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA27_4 = input.LA(1);

                         
                        int index27_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA27_5 = input.LA(1);

                         
                        int index27_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA27_6 = input.LA(1);

                         
                        int index27_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA27_7 = input.LA(1);

                         
                        int index27_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA27_8 = input.LA(1);

                         
                        int index27_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA27_9 = input.LA(1);

                         
                        int index27_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA27_10 = input.LA(1);

                         
                        int index27_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA27_11 = input.LA(1);

                         
                        int index27_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA27_12 = input.LA(1);

                         
                        int index27_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA27_13 = input.LA(1);

                         
                        int index27_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA27_14 = input.LA(1);

                         
                        int index27_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA27_15 = input.LA(1);

                         
                        int index27_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA27_16 = input.LA(1);

                         
                        int index27_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA27_17 = input.LA(1);

                         
                        int index27_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA27_18 = input.LA(1);

                         
                        int index27_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA27_19 = input.LA(1);

                         
                        int index27_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA27_20 = input.LA(1);

                         
                        int index27_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA27_21 = input.LA(1);

                         
                        int index27_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA27_22 = input.LA(1);

                         
                        int index27_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA27_23 = input.LA(1);

                         
                        int index27_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA27_24 = input.LA(1);

                         
                        int index27_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA27_25 = input.LA(1);

                         
                        int index27_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA27_26 = input.LA(1);

                         
                        int index27_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA27_27 = input.LA(1);

                         
                        int index27_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA27_28 = input.LA(1);

                         
                        int index27_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA27_29 = input.LA(1);

                         
                        int index27_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA27_30 = input.LA(1);

                         
                        int index27_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA27_31 = input.LA(1);

                         
                        int index27_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA27_32 = input.LA(1);

                         
                        int index27_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA27_33 = input.LA(1);

                         
                        int index27_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA27_34 = input.LA(1);

                         
                        int index27_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA27_35 = input.LA(1);

                         
                        int index27_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA27_36 = input.LA(1);

                         
                        int index27_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA27_37 = input.LA(1);

                         
                        int index27_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA27_38 = input.LA(1);

                         
                        int index27_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA27_39 = input.LA(1);

                         
                        int index27_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA27_40 = input.LA(1);

                         
                        int index27_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA27_41 = input.LA(1);

                         
                        int index27_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA27_42 = input.LA(1);

                         
                        int index27_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA27_43 = input.LA(1);

                         
                        int index27_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA27_44 = input.LA(1);

                         
                        int index27_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA27_45 = input.LA(1);

                         
                        int index27_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA27_46 = input.LA(1);

                         
                        int index27_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_46);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA27_47 = input.LA(1);

                         
                        int index27_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 58;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_47);
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
    static final String dfa_14s = "\57\uffff";
    static final String dfa_15s = "\1\4\23\uffff\2\0\31\uffff";
    static final String dfa_16s = "\1\167\23\uffff\2\0\31\uffff";
    static final String dfa_17s = "\1\uffff\23\1\2\uffff\30\1\1\2";
    static final String dfa_18s = "\1\2\23\uffff\1\0\1\1\31\uffff}>";
    static final String[] dfa_19s = {
            "\1\45\1\44\1\uffff\1\41\1\40\13\uffff\1\51\3\uffff\1\50\1\uffff\1\6\1\uffff\1\23\1\31\1\21\1\35\1\7\1\16\1\17\1\uffff\1\14\1\53\1\uffff\1\3\1\4\1\uffff\1\52\1\30\1\12\2\uffff\1\13\1\43\1\20\1\10\1\uffff\1\34\1\22\1\15\1\5\2\uffff\1\11\1\47\1\uffff\1\56\1\42\21\uffff\1\46\1\uffff\1\33\1\37\10\uffff\1\1\2\uffff\1\24\1\uffff\1\25\7\uffff\1\32\3\uffff\1\36\4\uffff\1\27\1\26\1\uffff\1\2\1\54\1\55",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            "",
            ""
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA42 extends DFA {

        public DFA42(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = dfa_14;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "2572:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA42_20 = input.LA(1);

                         
                        int index42_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTAlgorithmParser()) ) {s = 45;}

                        else if ( (true) ) {s = 46;}

                         
                        input.seek(index42_20);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA42_21 = input.LA(1);

                         
                        int index42_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTAlgorithmParser()) ) {s = 45;}

                        else if ( (true) ) {s = 46;}

                         
                        input.seek(index42_21);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA42_0 = input.LA(1);

                         
                        int index42_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA42_0==LeftParenthesis) && (synpred3_InternalSTAlgorithmParser())) {s = 1;}

                        else if ( (LA42_0==RULE_ID) && (synpred3_InternalSTAlgorithmParser())) {s = 2;}

                        else if ( (LA42_0==BOOL) && (synpred3_InternalSTAlgorithmParser())) {s = 3;}

                        else if ( (LA42_0==BYTE) && (synpred3_InternalSTAlgorithmParser())) {s = 4;}

                        else if ( (LA42_0==WORD) && (synpred3_InternalSTAlgorithmParser())) {s = 5;}

                        else if ( (LA42_0==DWORD) && (synpred3_InternalSTAlgorithmParser())) {s = 6;}

                        else if ( (LA42_0==LWORD) && (synpred3_InternalSTAlgorithmParser())) {s = 7;}

                        else if ( (LA42_0==SINT) && (synpred3_InternalSTAlgorithmParser())) {s = 8;}

                        else if ( (LA42_0==INT) && (synpred3_InternalSTAlgorithmParser())) {s = 9;}

                        else if ( (LA42_0==DINT) && (synpred3_InternalSTAlgorithmParser())) {s = 10;}

                        else if ( (LA42_0==LINT) && (synpred3_InternalSTAlgorithmParser())) {s = 11;}

                        else if ( (LA42_0==USINT) && (synpred3_InternalSTAlgorithmParser())) {s = 12;}

                        else if ( (LA42_0==UINT) && (synpred3_InternalSTAlgorithmParser())) {s = 13;}

                        else if ( (LA42_0==UDINT) && (synpred3_InternalSTAlgorithmParser())) {s = 14;}

                        else if ( (LA42_0==ULINT) && (synpred3_InternalSTAlgorithmParser())) {s = 15;}

                        else if ( (LA42_0==REAL) && (synpred3_InternalSTAlgorithmParser())) {s = 16;}

                        else if ( (LA42_0==LREAL) && (synpred3_InternalSTAlgorithmParser())) {s = 17;}

                        else if ( (LA42_0==TRUE) && (synpred3_InternalSTAlgorithmParser())) {s = 18;}

                        else if ( (LA42_0==FALSE) && (synpred3_InternalSTAlgorithmParser())) {s = 19;}

                        else if ( (LA42_0==PlusSign) ) {s = 20;}

                        else if ( (LA42_0==HyphenMinus) ) {s = 21;}

                        else if ( (LA42_0==RULE_INT) && (synpred3_InternalSTAlgorithmParser())) {s = 22;}

                        else if ( (LA42_0==RULE_NON_DECIMAL) && (synpred3_InternalSTAlgorithmParser())) {s = 23;}

                        else if ( (LA42_0==DATE) && (synpred3_InternalSTAlgorithmParser())) {s = 24;}

                        else if ( (LA42_0==LDATE) && (synpred3_InternalSTAlgorithmParser())) {s = 25;}

                        else if ( (LA42_0==D) && (synpred3_InternalSTAlgorithmParser())) {s = 26;}

                        else if ( (LA42_0==LD) && (synpred3_InternalSTAlgorithmParser())) {s = 27;}

                        else if ( (LA42_0==TIME) && (synpred3_InternalSTAlgorithmParser())) {s = 28;}

                        else if ( (LA42_0==LTIME) && (synpred3_InternalSTAlgorithmParser())) {s = 29;}

                        else if ( (LA42_0==T) && (synpred3_InternalSTAlgorithmParser())) {s = 30;}

                        else if ( (LA42_0==LT) && (synpred3_InternalSTAlgorithmParser())) {s = 31;}

                        else if ( (LA42_0==TIME_OF_DAY) && (synpred3_InternalSTAlgorithmParser())) {s = 32;}

                        else if ( (LA42_0==LTIME_OF_DAY) && (synpred3_InternalSTAlgorithmParser())) {s = 33;}

                        else if ( (LA42_0==TOD) && (synpred3_InternalSTAlgorithmParser())) {s = 34;}

                        else if ( (LA42_0==LTOD) && (synpred3_InternalSTAlgorithmParser())) {s = 35;}

                        else if ( (LA42_0==DATE_AND_TIME) && (synpred3_InternalSTAlgorithmParser())) {s = 36;}

                        else if ( (LA42_0==LDATE_AND_TIME) && (synpred3_InternalSTAlgorithmParser())) {s = 37;}

                        else if ( (LA42_0==DT) && (synpred3_InternalSTAlgorithmParser())) {s = 38;}

                        else if ( (LA42_0==LDT) && (synpred3_InternalSTAlgorithmParser())) {s = 39;}

                        else if ( (LA42_0==STRING) && (synpred3_InternalSTAlgorithmParser())) {s = 40;}

                        else if ( (LA42_0==WSTRING) && (synpred3_InternalSTAlgorithmParser())) {s = 41;}

                        else if ( (LA42_0==CHAR) && (synpred3_InternalSTAlgorithmParser())) {s = 42;}

                        else if ( (LA42_0==WCHAR) && (synpred3_InternalSTAlgorithmParser())) {s = 43;}

                        else if ( (LA42_0==RULE_STRING) && (synpred3_InternalSTAlgorithmParser())) {s = 44;}

                        else if ( (LA42_0==RULE_WSTRING) && (synpred3_InternalSTAlgorithmParser())) {s = 45;}

                        else if ( (LA42_0==NOT) ) {s = 46;}

                         
                        input.seek(index42_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 42, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_20s = "\43\uffff";
    static final String dfa_21s = "\1\2\42\uffff";
    static final String dfa_22s = "\1\11\1\0\41\uffff";
    static final String dfa_23s = "\1\157\1\0\41\uffff";
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

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = dfa_20;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "2867:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA49_1 = input.LA(1);

                         
                        int index49_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSTAlgorithmParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index49_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 49, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x4EF7DFF7F5D281F0L,0x00EC2222900F0000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x4EF7DFF7F5D281B2L,0x00EC2222900F0000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x4EF7DFF7F5D081B2L,0x00EC2222900F0000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000084000L,0x0020000000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000080000L,0x0020000000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000001000002000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x4CD79DB7E71001B0L,0x0020000000010000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x6CF79DB7F51001B0L,0x00EC2202900D0000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000800100000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000402000000100L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000100L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x6CF79DB7F51001B0L,0x00EC6202900D0000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x4EF7FFF7FDF081B0L,0x00EC2222900F0000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000200008200000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x6CF7BDB7F51101B0L,0x00EC2202900D0000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x4EF7DFF7F5D481B0L,0x00EC2222900F0000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x4EF7DFF7F5D091B0L,0x00EC2222900F0000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x4EF7DFFFF5D081B0L,0x00EC2222900F0000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0100000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000400L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000014000001200L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000280000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x1000000000000002L,0x0000000840000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000400400000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x002800000000003EL});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x6CF79DB7F51001B0L,0x00EC2202B00D0000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000120000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0020000010000000L,0x000C000280000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0008000280000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0018000000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x00001E0002300000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000002L,0x0009000280000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000002L,0x0008000280000000L});

}