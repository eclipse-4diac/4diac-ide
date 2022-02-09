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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "END_ALGORITHM", "TIME_OF_DAY", "END_REPEAT", "VAR_OUTPUT", "ALGORITHM", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "VAR_TEMP", "WSTRING", "END_FOR", "END_VAR", "STRING", "DWORD", "END_IF", "LDATE", "LREAL", "LTIME", "LWORD", "REPEAT", "RETURN", "UDINT", "ULINT", "USINT", "WCHAR", "ARRAY", "BOOL", "BYTE", "CHAR", "DATE", "DINT", "ELSIF", "FALSE", "LINT", "LTOD", "REAL", "SINT", "TIME", "UINT", "UNTIL", "WHILE", "WORD", "CASE", "ELSE", "EXIT", "INT", "LDT", "THEN", "TOD", "TRUE", "AND", "DT", "FOR", "LD", "LT", "MOD", "NOT", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "D_2", "DO", "IF", "MS", "NS", "OF", "OR", "T", "TO", "US", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "H", "M", "S", "LeftSquareBracket", "RightSquareBracket", "KW__", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_WSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=26;
    public static final int LessThanSignGreaterThanSign=76;
    public static final int VAR=65;
    public static final int END_IF=22;
    public static final int ULINT=30;
    public static final int END_CASE=15;
    public static final int LessThanSign=101;
    public static final int LeftParenthesis=91;
    public static final int BYTE=35;
    public static final int ELSE=51;
    public static final int IF=82;
    public static final int LINT=41;
    public static final int GreaterThanSign=103;
    public static final int WORD=49;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=115;
    public static final int TOD=56;
    public static final int DINT=38;
    public static final int UDINT=29;
    public static final int CASE=50;
    public static final int GreaterThanSignEqualsSign=77;
    public static final int END_ALGORITHM=6;
    public static final int AT=78;
    public static final int PlusSign=94;
    public static final int RULE_INT=113;
    public static final int END_FOR=18;
    public static final int RULE_ML_COMMENT=118;
    public static final int THEN=55;
    public static final int XOR=66;
    public static final int LeftSquareBracket=108;
    public static final int EXIT=52;
    public static final int TIME_OF_DAY=7;
    public static final int B=67;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=27;
    public static final int D=104;
    public static final int H=105;
    public static final int CHAR=36;
    public static final int L=69;
    public static final int M=106;
    public static final int LTIME=25;
    public static final int Comma=95;
    public static final int HyphenMinus=96;
    public static final int S=107;
    public static final int T=87;
    public static final int W=70;
    public static final int BY=79;
    public static final int X=71;
    public static final int ELSIF=39;
    public static final int END_REPEAT=8;
    public static final int LessThanSignEqualsSign=75;
    public static final int Solidus=98;
    public static final int VAR_INPUT=12;
    public static final int FullStop=97;
    public static final int VAR_TEMP=16;
    public static final int CONSTANT=13;
    public static final int KW__=110;
    public static final int CONTINUE=14;
    public static final int Semicolon=100;
    public static final int LD=61;
    public static final int VAR_OUTPUT=9;
    public static final int STRING=20;
    public static final int RULE_HEX_DIGIT=111;
    public static final int TO=88;
    public static final int UINT=46;
    public static final int LTOD=42;
    public static final int ARRAY=33;
    public static final int LT=62;
    public static final int DO=81;
    public static final int WSTRING=17;
    public static final int DT=59;
    public static final int END_VAR=19;
    public static final int FullStopFullStop=73;
    public static final int Ampersand=90;
    public static final int US=89;
    public static final int RightSquareBracket=109;
    public static final int USINT=31;
    public static final int MS=83;
    public static final int DWORD=21;
    public static final int FOR=60;
    public static final int RightParenthesis=92;
    public static final int TRUE=57;
    public static final int ColonEqualsSign=74;
    public static final int RULE_WSTRING=117;
    public static final int END_WHILE=11;
    public static final int DATE=37;
    public static final int NOT=64;
    public static final int LDATE=23;
    public static final int AND=58;
    public static final int REAL=43;
    public static final int AsteriskAsterisk=72;
    public static final int SINT=44;
    public static final int LREAL=24;
    public static final int WCHAR=32;
    public static final int NS=84;
    public static final int ALGORITHM=10;
    public static final int RULE_STRING=116;
    public static final int INT=53;
    public static final int RULE_SL_COMMENT=119;
    public static final int RETURN=28;
    public static final int EqualsSign=102;
    public static final int OF=85;
    public static final int Colon=99;
    public static final int EOF=-1;
    public static final int LDT=54;
    public static final int Asterisk=93;
    public static final int MOD=63;
    public static final int OR=86;
    public static final int RULE_WS=120;
    public static final int RULE_EXT_INT=114;
    public static final int TIME=45;
    public static final int RULE_ANY_OTHER=121;
    public static final int UNTIL=47;
    public static final int BOOL=34;
    public static final int D_2=80;
    public static final int D_1=68;
    public static final int FALSE=40;
    public static final int WHILE=48;
    public static final int RULE_NON_DECIMAL=112;

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

                if ( ((LA3_0>=LDATE_AND_TIME && LA3_0<=DATE_AND_TIME)||LA3_0==TIME_OF_DAY||LA3_0==CONTINUE||LA3_0==WSTRING||(LA3_0>=STRING && LA3_0<=DWORD)||(LA3_0>=LDATE && LA3_0<=WCHAR)||(LA3_0>=BOOL && LA3_0<=DINT)||(LA3_0>=FALSE && LA3_0<=UINT)||(LA3_0>=WHILE && LA3_0<=CASE)||(LA3_0>=EXIT && LA3_0<=LDT)||(LA3_0>=TOD && LA3_0<=TRUE)||(LA3_0>=DT && LA3_0<=LT)||LA3_0==D_2||LA3_0==IF||LA3_0==T||LA3_0==LeftParenthesis||LA3_0==PlusSign||LA3_0==HyphenMinus||LA3_0==Semicolon||(LA3_0>=RULE_NON_DECIMAL && LA3_0<=RULE_INT)||(LA3_0>=RULE_ID && LA3_0<=RULE_WSTRING)) ) {
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
    // InternalSTAlgorithmParser.g:305:1: ruleVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon ) ;
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
            // InternalSTAlgorithmParser.g:311:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon ) )
            // InternalSTAlgorithmParser.g:312:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon )
            {
            // InternalSTAlgorithmParser.g:312:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon )
            // InternalSTAlgorithmParser.g:313:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( (otherlv_17= RULE_ID ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )? otherlv_23= Semicolon
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

                        if ( ((LA9_1>=LDATE_AND_TIME && LA9_1<=DATE_AND_TIME)||LA9_1==TIME_OF_DAY||LA9_1==WSTRING||(LA9_1>=STRING && LA9_1<=DWORD)||(LA9_1>=LDATE && LA9_1<=LWORD)||(LA9_1>=UDINT && LA9_1<=WCHAR)||(LA9_1>=BOOL && LA9_1<=DINT)||(LA9_1>=FALSE && LA9_1<=UINT)||LA9_1==WORD||(LA9_1>=INT && LA9_1<=LDT)||(LA9_1>=TOD && LA9_1<=TRUE)||LA9_1==DT||(LA9_1>=LD && LA9_1<=LT)||LA9_1==NOT||LA9_1==D_2||LA9_1==T||LA9_1==LeftParenthesis||LA9_1==PlusSign||LA9_1==HyphenMinus||(LA9_1>=RULE_NON_DECIMAL && LA9_1<=RULE_INT)||(LA9_1>=RULE_ID && LA9_1<=RULE_WSTRING)) ) {
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

                    otherlv_16=(Token)match(input,OF,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTAlgorithmParser.g:482:3: ( (otherlv_17= RULE_ID ) )
            // InternalSTAlgorithmParser.g:483:4: (otherlv_17= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:483:4: (otherlv_17= RULE_ID )
            // InternalSTAlgorithmParser.g:484:5: otherlv_17= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVarDeclarationRule());
              					}
              				
            }
            otherlv_17=(Token)match(input,RULE_ID,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_17, grammarAccess.getVarDeclarationAccess().getTypeLibraryElementCrossReference_5_0());
              				
            }

            }


            }

            // InternalSTAlgorithmParser.g:495:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==LeftSquareBracket) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSTAlgorithmParser.g:496:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:500:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:501:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:501:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:502:6: lv_maxLength_19_0= ruleSTExpression
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

            // InternalSTAlgorithmParser.g:524:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ColonEqualsSign) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalSTAlgorithmParser.g:525:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:529:4: ( (lv_defaultValue_22_0= ruleInitializerExpression ) )
                    // InternalSTAlgorithmParser.g:530:5: (lv_defaultValue_22_0= ruleInitializerExpression )
                    {
                    // InternalSTAlgorithmParser.g:530:5: (lv_defaultValue_22_0= ruleInitializerExpression )
                    // InternalSTAlgorithmParser.g:531:6: lv_defaultValue_22_0= ruleInitializerExpression
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
    // InternalSTAlgorithmParser.g:557:1: entryRuleInitializerExpression returns [EObject current=null] : iv_ruleInitializerExpression= ruleInitializerExpression EOF ;
    public final EObject entryRuleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitializerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:557:62: (iv_ruleInitializerExpression= ruleInitializerExpression EOF )
            // InternalSTAlgorithmParser.g:558:2: iv_ruleInitializerExpression= ruleInitializerExpression EOF
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
    // InternalSTAlgorithmParser.g:564:1: ruleInitializerExpression returns [EObject current=null] : (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) ;
    public final EObject ruleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STExpression_0 = null;

        EObject this_ArrayInitializerExpression_1 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:570:2: ( (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) )
            // InternalSTAlgorithmParser.g:571:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            {
            // InternalSTAlgorithmParser.g:571:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=LDATE_AND_TIME && LA13_0<=DATE_AND_TIME)||LA13_0==TIME_OF_DAY||LA13_0==WSTRING||(LA13_0>=STRING && LA13_0<=DWORD)||(LA13_0>=LDATE && LA13_0<=LWORD)||(LA13_0>=UDINT && LA13_0<=WCHAR)||(LA13_0>=BOOL && LA13_0<=DINT)||(LA13_0>=FALSE && LA13_0<=UINT)||LA13_0==WORD||(LA13_0>=INT && LA13_0<=LDT)||(LA13_0>=TOD && LA13_0<=TRUE)||LA13_0==DT||(LA13_0>=LD && LA13_0<=LT)||LA13_0==NOT||LA13_0==D_2||LA13_0==T||LA13_0==LeftParenthesis||LA13_0==PlusSign||LA13_0==HyphenMinus||(LA13_0>=RULE_NON_DECIMAL && LA13_0<=RULE_INT)||(LA13_0>=RULE_ID && LA13_0<=RULE_WSTRING)) ) {
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
                    // InternalSTAlgorithmParser.g:572:3: this_STExpression_0= ruleSTExpression
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
                    // InternalSTAlgorithmParser.g:581:3: this_ArrayInitializerExpression_1= ruleArrayInitializerExpression
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
    // InternalSTAlgorithmParser.g:593:1: entryRuleArrayInitializerExpression returns [EObject current=null] : iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF ;
    public final EObject entryRuleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitializerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:593:67: (iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF )
            // InternalSTAlgorithmParser.g:594:2: iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF
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
    // InternalSTAlgorithmParser.g:600:1: ruleArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:606:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTAlgorithmParser.g:607:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTAlgorithmParser.g:607:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTAlgorithmParser.g:608:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:612:3: ( (lv_values_1_0= ruleArrayInitElement ) )
            // InternalSTAlgorithmParser.g:613:4: (lv_values_1_0= ruleArrayInitElement )
            {
            // InternalSTAlgorithmParser.g:613:4: (lv_values_1_0= ruleArrayInitElement )
            // InternalSTAlgorithmParser.g:614:5: lv_values_1_0= ruleArrayInitElement
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

            // InternalSTAlgorithmParser.g:631:3: (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==Comma) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:632:4: otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTAlgorithmParser.g:636:4: ( (lv_values_3_0= ruleArrayInitElement ) )
            	    // InternalSTAlgorithmParser.g:637:5: (lv_values_3_0= ruleArrayInitElement )
            	    {
            	    // InternalSTAlgorithmParser.g:637:5: (lv_values_3_0= ruleArrayInitElement )
            	    // InternalSTAlgorithmParser.g:638:6: lv_values_3_0= ruleArrayInitElement
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
    // InternalSTAlgorithmParser.g:664:1: entryRuleArrayInitElement returns [EObject current=null] : iv_ruleArrayInitElement= ruleArrayInitElement EOF ;
    public final EObject entryRuleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitElement = null;


        try {
            // InternalSTAlgorithmParser.g:664:57: (iv_ruleArrayInitElement= ruleArrayInitElement EOF )
            // InternalSTAlgorithmParser.g:665:2: iv_ruleArrayInitElement= ruleArrayInitElement EOF
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
    // InternalSTAlgorithmParser.g:671:1: ruleArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) ;
    public final EObject ruleArrayInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_indexOrInitExpression_0_0 = null;

        EObject lv_initExpression_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:677:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) )
            // InternalSTAlgorithmParser.g:678:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            {
            // InternalSTAlgorithmParser.g:678:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            // InternalSTAlgorithmParser.g:679:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            {
            // InternalSTAlgorithmParser.g:679:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:680:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:680:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:681:5: lv_indexOrInitExpression_0_0= ruleSTExpression
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

            // InternalSTAlgorithmParser.g:698:3: (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==LeftParenthesis) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalSTAlgorithmParser.g:699:4: otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:703:4: ( (lv_initExpression_2_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:704:5: (lv_initExpression_2_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:704:5: (lv_initExpression_2_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:705:6: lv_initExpression_2_0= ruleSTExpression
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
    // InternalSTAlgorithmParser.g:731:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTAlgorithmParser.g:731:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTAlgorithmParser.g:732:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalSTAlgorithmParser.g:738:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) ;
    public final EObject ruleSTStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        EObject this_STIfStatement_0 = null;

        EObject this_STCaseStatement_1 = null;

        EObject this_STForStatement_2 = null;

        EObject this_STWhileStatement_3 = null;

        EObject this_STRepeatStatement_4 = null;

        EObject this_STAssignmentStatement_5 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:744:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) )
            // InternalSTAlgorithmParser.g:745:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            {
            // InternalSTAlgorithmParser.g:745:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=LDATE_AND_TIME && LA17_0<=DATE_AND_TIME)||LA17_0==TIME_OF_DAY||LA17_0==CONTINUE||LA17_0==WSTRING||(LA17_0>=STRING && LA17_0<=DWORD)||(LA17_0>=LDATE && LA17_0<=WCHAR)||(LA17_0>=BOOL && LA17_0<=DINT)||(LA17_0>=FALSE && LA17_0<=UINT)||(LA17_0>=WHILE && LA17_0<=CASE)||(LA17_0>=EXIT && LA17_0<=LDT)||(LA17_0>=TOD && LA17_0<=TRUE)||(LA17_0>=DT && LA17_0<=LT)||LA17_0==D_2||LA17_0==IF||LA17_0==T||LA17_0==LeftParenthesis||LA17_0==PlusSign||LA17_0==HyphenMinus||(LA17_0>=RULE_NON_DECIMAL && LA17_0<=RULE_INT)||(LA17_0>=RULE_ID && LA17_0<=RULE_WSTRING)) ) {
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
                    // InternalSTAlgorithmParser.g:746:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    {
                    // InternalSTAlgorithmParser.g:746:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    // InternalSTAlgorithmParser.g:747:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon
                    {
                    // InternalSTAlgorithmParser.g:747:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignmentStatement_5= ruleSTAssignmentStatement | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )
                    int alt16=9;
                    switch ( input.LA(1) ) {
                    case IF:
                        {
                        alt16=1;
                        }
                        break;
                    case CASE:
                        {
                        alt16=2;
                        }
                        break;
                    case FOR:
                        {
                        alt16=3;
                        }
                        break;
                    case WHILE:
                        {
                        alt16=4;
                        }
                        break;
                    case REPEAT:
                        {
                        alt16=5;
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
                        alt16=6;
                        }
                        break;
                    case RETURN:
                        {
                        alt16=7;
                        }
                        break;
                    case CONTINUE:
                        {
                        alt16=8;
                        }
                        break;
                    case EXIT:
                        {
                        alt16=9;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 0, input);

                        throw nvae;
                    }

                    switch (alt16) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:748:5: this_STIfStatement_0= ruleSTIfStatement
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
                            // InternalSTAlgorithmParser.g:757:5: this_STCaseStatement_1= ruleSTCaseStatement
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
                            // InternalSTAlgorithmParser.g:766:5: this_STForStatement_2= ruleSTForStatement
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
                            // InternalSTAlgorithmParser.g:775:5: this_STWhileStatement_3= ruleSTWhileStatement
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
                            // InternalSTAlgorithmParser.g:784:5: this_STRepeatStatement_4= ruleSTRepeatStatement
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
                            // InternalSTAlgorithmParser.g:793:5: this_STAssignmentStatement_5= ruleSTAssignmentStatement
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
                            break;
                        case 7 :
                            // InternalSTAlgorithmParser.g:802:5: ( () otherlv_7= RETURN )
                            {
                            // InternalSTAlgorithmParser.g:802:5: ( () otherlv_7= RETURN )
                            // InternalSTAlgorithmParser.g:803:6: () otherlv_7= RETURN
                            {
                            // InternalSTAlgorithmParser.g:803:6: ()
                            // InternalSTAlgorithmParser.g:804:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_6_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_7=(Token)match(input,RETURN,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_6_1());
                              					
                            }

                            }


                            }
                            break;
                        case 8 :
                            // InternalSTAlgorithmParser.g:816:5: ( () otherlv_9= CONTINUE )
                            {
                            // InternalSTAlgorithmParser.g:816:5: ( () otherlv_9= CONTINUE )
                            // InternalSTAlgorithmParser.g:817:6: () otherlv_9= CONTINUE
                            {
                            // InternalSTAlgorithmParser.g:817:6: ()
                            // InternalSTAlgorithmParser.g:818:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_7_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_9=(Token)match(input,CONTINUE,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_9, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_7_1());
                              					
                            }

                            }


                            }
                            break;
                        case 9 :
                            // InternalSTAlgorithmParser.g:830:5: ( () otherlv_11= EXIT )
                            {
                            // InternalSTAlgorithmParser.g:830:5: ( () otherlv_11= EXIT )
                            // InternalSTAlgorithmParser.g:831:6: () otherlv_11= EXIT
                            {
                            // InternalSTAlgorithmParser.g:831:6: ()
                            // InternalSTAlgorithmParser.g:832:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTExitAction_0_0_8_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_11=(Token)match(input,EXIT,FOLLOW_23); if (state.failed) return current;
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
                    // InternalSTAlgorithmParser.g:850:3: ( () otherlv_14= Semicolon )
                    {
                    // InternalSTAlgorithmParser.g:850:3: ( () otherlv_14= Semicolon )
                    // InternalSTAlgorithmParser.g:851:4: () otherlv_14= Semicolon
                    {
                    // InternalSTAlgorithmParser.g:851:4: ()
                    // InternalSTAlgorithmParser.g:852:5: 
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
    // InternalSTAlgorithmParser.g:867:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTAlgorithmParser.g:867:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTAlgorithmParser.g:868:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTAlgorithmParser.g:874:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:880:2: ( ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) )
            // InternalSTAlgorithmParser.g:881:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            {
            // InternalSTAlgorithmParser.g:881:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            // InternalSTAlgorithmParser.g:882:3: ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) )
            {
            // InternalSTAlgorithmParser.g:882:3: ( (lv_left_0_0= ruleSTAccessExpression ) )
            // InternalSTAlgorithmParser.g:883:4: (lv_left_0_0= ruleSTAccessExpression )
            {
            // InternalSTAlgorithmParser.g:883:4: (lv_left_0_0= ruleSTAccessExpression )
            // InternalSTAlgorithmParser.g:884:5: lv_left_0_0= ruleSTAccessExpression
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
            // InternalSTAlgorithmParser.g:905:3: ( (lv_right_2_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:906:4: (lv_right_2_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:906:4: (lv_right_2_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:907:5: lv_right_2_0= ruleSTExpression
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


    // $ANTLR start "entryRuleSTIfStatement"
    // InternalSTAlgorithmParser.g:928:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalSTAlgorithmParser.g:928:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalSTAlgorithmParser.g:929:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalSTAlgorithmParser.g:935:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTAlgorithmParser.g:941:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTAlgorithmParser.g:942:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTAlgorithmParser.g:942:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTAlgorithmParser.g:943:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:947:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:948:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:948:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:949:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_27);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_28); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatementAccess().getTHENKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:970:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=LDATE_AND_TIME && LA18_0<=DATE_AND_TIME)||LA18_0==TIME_OF_DAY||LA18_0==CONTINUE||LA18_0==WSTRING||(LA18_0>=STRING && LA18_0<=DWORD)||(LA18_0>=LDATE && LA18_0<=WCHAR)||(LA18_0>=BOOL && LA18_0<=DINT)||(LA18_0>=FALSE && LA18_0<=UINT)||(LA18_0>=WHILE && LA18_0<=CASE)||(LA18_0>=EXIT && LA18_0<=LDT)||(LA18_0>=TOD && LA18_0<=TRUE)||(LA18_0>=DT && LA18_0<=LT)||LA18_0==D_2||LA18_0==IF||LA18_0==T||LA18_0==LeftParenthesis||LA18_0==PlusSign||LA18_0==HyphenMinus||LA18_0==Semicolon||(LA18_0>=RULE_NON_DECIMAL && LA18_0<=RULE_INT)||(LA18_0>=RULE_ID && LA18_0<=RULE_WSTRING)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:971:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:971:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:972:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_28);
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
            	    break loop18;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:989:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==ELSIF) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:990:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTAlgorithmParser.g:990:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTAlgorithmParser.g:991:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_29);
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
            	    break loop19;
                }
            } while (true);

            // InternalSTAlgorithmParser.g:1008:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==ELSE) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1009:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTAlgorithmParser.g:1009:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTAlgorithmParser.g:1010:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_30);
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
    // InternalSTAlgorithmParser.g:1035:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTAlgorithmParser.g:1035:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTAlgorithmParser.g:1036:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTAlgorithmParser.g:1042:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1048:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1049:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1049:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1050:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1054:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1055:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1055:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1056:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_27);
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
            // InternalSTAlgorithmParser.g:1077:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>=LDATE_AND_TIME && LA21_0<=DATE_AND_TIME)||LA21_0==TIME_OF_DAY||LA21_0==CONTINUE||LA21_0==WSTRING||(LA21_0>=STRING && LA21_0<=DWORD)||(LA21_0>=LDATE && LA21_0<=WCHAR)||(LA21_0>=BOOL && LA21_0<=DINT)||(LA21_0>=FALSE && LA21_0<=UINT)||(LA21_0>=WHILE && LA21_0<=CASE)||(LA21_0>=EXIT && LA21_0<=LDT)||(LA21_0>=TOD && LA21_0<=TRUE)||(LA21_0>=DT && LA21_0<=LT)||LA21_0==D_2||LA21_0==IF||LA21_0==T||LA21_0==LeftParenthesis||LA21_0==PlusSign||LA21_0==HyphenMinus||LA21_0==Semicolon||(LA21_0>=RULE_NON_DECIMAL && LA21_0<=RULE_INT)||(LA21_0>=RULE_ID && LA21_0<=RULE_WSTRING)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1078:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1078:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1079:5: lv_statements_3_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalSTAlgorithmParser.g:1100:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1100:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTAlgorithmParser.g:1101:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTAlgorithmParser.g:1107:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTAlgorithmParser.g:1113:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTAlgorithmParser.g:1114:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTAlgorithmParser.g:1114:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTAlgorithmParser.g:1115:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1119:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1120:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1120:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1121:5: lv_selector_1_0= ruleSTExpression
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
            // InternalSTAlgorithmParser.g:1142:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt22=0;
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>=LDATE_AND_TIME && LA22_0<=DATE_AND_TIME)||LA22_0==TIME_OF_DAY||LA22_0==WSTRING||(LA22_0>=STRING && LA22_0<=DWORD)||(LA22_0>=LDATE && LA22_0<=LWORD)||(LA22_0>=UDINT && LA22_0<=WCHAR)||(LA22_0>=BOOL && LA22_0<=DINT)||(LA22_0>=FALSE && LA22_0<=UINT)||LA22_0==WORD||(LA22_0>=INT && LA22_0<=LDT)||(LA22_0>=TOD && LA22_0<=TRUE)||LA22_0==DT||(LA22_0>=LD && LA22_0<=LT)||LA22_0==NOT||LA22_0==D_2||LA22_0==T||LA22_0==LeftParenthesis||LA22_0==PlusSign||LA22_0==HyphenMinus||(LA22_0>=RULE_NON_DECIMAL && LA22_0<=RULE_INT)||(LA22_0>=RULE_ID && LA22_0<=RULE_WSTRING)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1143:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTAlgorithmParser.g:1143:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTAlgorithmParser.g:1144:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_31);
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
            	    if ( cnt22 >= 1 ) break loop22;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(22, input);
                        throw eee;
                }
                cnt22++;
            } while (true);

            // InternalSTAlgorithmParser.g:1161:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ELSE) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1162:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTAlgorithmParser.g:1162:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTAlgorithmParser.g:1163:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_32);
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
    // InternalSTAlgorithmParser.g:1188:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTAlgorithmParser.g:1188:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTAlgorithmParser.g:1189:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTAlgorithmParser.g:1195:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1201:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1202:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1202:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1203:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:1203:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1204:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1204:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1205:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_33);
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

            // InternalSTAlgorithmParser.g:1222:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==Comma) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1223:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTAlgorithmParser.g:1227:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTAlgorithmParser.g:1228:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1228:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTAlgorithmParser.g:1229:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_33);
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
            	    break loop24;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1251:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop25:
            do {
                int alt25=2;
                alt25 = dfa25.predict(input);
                switch (alt25) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1252:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1256:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1257:5: lv_statements_4_0= ruleSTStatement
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTAlgorithmParser.g:1278:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTAlgorithmParser.g:1278:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTAlgorithmParser.g:1279:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTAlgorithmParser.g:1285:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1291:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTAlgorithmParser.g:1292:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTAlgorithmParser.g:1292:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTAlgorithmParser.g:1293:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTAlgorithmParser.g:1293:3: ()
            // InternalSTAlgorithmParser.g:1294:4: 
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
            // InternalSTAlgorithmParser.g:1304:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>=LDATE_AND_TIME && LA26_0<=DATE_AND_TIME)||LA26_0==TIME_OF_DAY||LA26_0==CONTINUE||LA26_0==WSTRING||(LA26_0>=STRING && LA26_0<=DWORD)||(LA26_0>=LDATE && LA26_0<=WCHAR)||(LA26_0>=BOOL && LA26_0<=DINT)||(LA26_0>=FALSE && LA26_0<=UINT)||(LA26_0>=WHILE && LA26_0<=CASE)||(LA26_0>=EXIT && LA26_0<=LDT)||(LA26_0>=TOD && LA26_0<=TRUE)||(LA26_0>=DT && LA26_0<=LT)||LA26_0==D_2||LA26_0==IF||LA26_0==T||LA26_0==LeftParenthesis||LA26_0==PlusSign||LA26_0==HyphenMinus||LA26_0==Semicolon||(LA26_0>=RULE_NON_DECIMAL && LA26_0<=RULE_INT)||(LA26_0>=RULE_ID && LA26_0<=RULE_WSTRING)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1305:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1305:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1306:5: lv_statements_2_0= ruleSTStatement
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
            	    break loop26;
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
    // InternalSTAlgorithmParser.g:1327:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1327:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTAlgorithmParser.g:1328:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTAlgorithmParser.g:1334:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
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
            // InternalSTAlgorithmParser.g:1340:2: ( (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalSTAlgorithmParser.g:1341:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalSTAlgorithmParser.g:1341:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalSTAlgorithmParser.g:1342:3: otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1346:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTAlgorithmParser.g:1347:4: (otherlv_1= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:1347:4: (otherlv_1= RULE_ID )
            // InternalSTAlgorithmParser.g:1348:5: otherlv_1= RULE_ID
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
            // InternalSTAlgorithmParser.g:1363:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1364:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1364:4: (lv_from_3_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1365:5: lv_from_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_34);
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
            // InternalSTAlgorithmParser.g:1386:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1387:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1387:4: (lv_to_5_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1388:5: lv_to_5_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_35);
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

            // InternalSTAlgorithmParser.g:1405:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==BY) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalSTAlgorithmParser.g:1406:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalSTAlgorithmParser.g:1410:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalSTAlgorithmParser.g:1411:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalSTAlgorithmParser.g:1411:5: (lv_by_7_0= ruleSTExpression )
                    // InternalSTAlgorithmParser.g:1412:6: lv_by_7_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

            otherlv_8=(Token)match(input,DO,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
              		
            }
            // InternalSTAlgorithmParser.g:1434:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>=LDATE_AND_TIME && LA28_0<=DATE_AND_TIME)||LA28_0==TIME_OF_DAY||LA28_0==CONTINUE||LA28_0==WSTRING||(LA28_0>=STRING && LA28_0<=DWORD)||(LA28_0>=LDATE && LA28_0<=WCHAR)||(LA28_0>=BOOL && LA28_0<=DINT)||(LA28_0>=FALSE && LA28_0<=UINT)||(LA28_0>=WHILE && LA28_0<=CASE)||(LA28_0>=EXIT && LA28_0<=LDT)||(LA28_0>=TOD && LA28_0<=TRUE)||(LA28_0>=DT && LA28_0<=LT)||LA28_0==D_2||LA28_0==IF||LA28_0==T||LA28_0==LeftParenthesis||LA28_0==PlusSign||LA28_0==HyphenMinus||LA28_0==Semicolon||(LA28_0>=RULE_NON_DECIMAL && LA28_0<=RULE_INT)||(LA28_0>=RULE_ID && LA28_0<=RULE_WSTRING)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1435:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1435:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1436:5: lv_statements_9_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_37);
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
            	    break loop28;
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
    // InternalSTAlgorithmParser.g:1461:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1461:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTAlgorithmParser.g:1462:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTAlgorithmParser.g:1468:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1474:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTAlgorithmParser.g:1475:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTAlgorithmParser.g:1475:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTAlgorithmParser.g:1476:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1480:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1481:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1481:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1482:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_36);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_38); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1503:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>=LDATE_AND_TIME && LA29_0<=DATE_AND_TIME)||LA29_0==TIME_OF_DAY||LA29_0==CONTINUE||LA29_0==WSTRING||(LA29_0>=STRING && LA29_0<=DWORD)||(LA29_0>=LDATE && LA29_0<=WCHAR)||(LA29_0>=BOOL && LA29_0<=DINT)||(LA29_0>=FALSE && LA29_0<=UINT)||(LA29_0>=WHILE && LA29_0<=CASE)||(LA29_0>=EXIT && LA29_0<=LDT)||(LA29_0>=TOD && LA29_0<=TRUE)||(LA29_0>=DT && LA29_0<=LT)||LA29_0==D_2||LA29_0==IF||LA29_0==T||LA29_0==LeftParenthesis||LA29_0==PlusSign||LA29_0==HyphenMinus||LA29_0==Semicolon||(LA29_0>=RULE_NON_DECIMAL && LA29_0<=RULE_INT)||(LA29_0>=RULE_ID && LA29_0<=RULE_WSTRING)) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1504:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1504:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1505:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_38);
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
            	    break loop29;
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
    // InternalSTAlgorithmParser.g:1530:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTAlgorithmParser.g:1530:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTAlgorithmParser.g:1531:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTAlgorithmParser.g:1537:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1543:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTAlgorithmParser.g:1544:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTAlgorithmParser.g:1544:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTAlgorithmParser.g:1545:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTAlgorithmParser.g:1549:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>=LDATE_AND_TIME && LA30_0<=DATE_AND_TIME)||LA30_0==TIME_OF_DAY||LA30_0==CONTINUE||LA30_0==WSTRING||(LA30_0>=STRING && LA30_0<=DWORD)||(LA30_0>=LDATE && LA30_0<=WCHAR)||(LA30_0>=BOOL && LA30_0<=DINT)||(LA30_0>=FALSE && LA30_0<=UINT)||(LA30_0>=WHILE && LA30_0<=CASE)||(LA30_0>=EXIT && LA30_0<=LDT)||(LA30_0>=TOD && LA30_0<=TRUE)||(LA30_0>=DT && LA30_0<=LT)||LA30_0==D_2||LA30_0==IF||LA30_0==T||LA30_0==LeftParenthesis||LA30_0==PlusSign||LA30_0==HyphenMinus||LA30_0==Semicolon||(LA30_0>=RULE_NON_DECIMAL && LA30_0<=RULE_INT)||(LA30_0>=RULE_ID && LA30_0<=RULE_WSTRING)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1550:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTAlgorithmParser.g:1550:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTAlgorithmParser.g:1551:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_39);
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
            	    break loop30;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:1572:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTAlgorithmParser.g:1573:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTAlgorithmParser.g:1573:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTAlgorithmParser.g:1574:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_40);
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
    // InternalSTAlgorithmParser.g:1599:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1599:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTAlgorithmParser.g:1600:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTAlgorithmParser.g:1606:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1612:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTAlgorithmParser.g:1613:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTAlgorithmParser.g:1624:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1624:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTAlgorithmParser.g:1625:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTAlgorithmParser.g:1631:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1637:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:1638:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:1638:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTAlgorithmParser.g:1639:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_41);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:1647:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==FullStopFullStop) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1648:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1648:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTAlgorithmParser.g:1649:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1649:5: ()
            	    // InternalSTAlgorithmParser.g:1650:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:1656:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTAlgorithmParser.g:1657:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:1657:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTAlgorithmParser.g:1658:7: lv_op_2_0= ruleSubrangeOperator
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

            	    // InternalSTAlgorithmParser.g:1676:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTAlgorithmParser.g:1677:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1677:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTAlgorithmParser.g:1678:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_41);
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalSTAlgorithmParser.g:1700:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1700:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTAlgorithmParser.g:1701:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTAlgorithmParser.g:1707:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1713:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:1714:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:1714:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTAlgorithmParser.g:1715:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_42);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:1723:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==OR) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1724:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1724:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTAlgorithmParser.g:1725:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1725:5: ()
            	    // InternalSTAlgorithmParser.g:1726:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:1732:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTAlgorithmParser.g:1733:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:1733:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTAlgorithmParser.g:1734:7: lv_op_2_0= ruleOrOperator
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

            	    // InternalSTAlgorithmParser.g:1752:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTAlgorithmParser.g:1753:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1753:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTAlgorithmParser.g:1754:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_42);
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalSTAlgorithmParser.g:1776:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1776:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTAlgorithmParser.g:1777:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTAlgorithmParser.g:1783:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1789:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:1790:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:1790:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTAlgorithmParser.g:1791:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_43);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:1799:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==XOR) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1800:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1800:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTAlgorithmParser.g:1801:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1801:5: ()
            	    // InternalSTAlgorithmParser.g:1802:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:1808:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTAlgorithmParser.g:1809:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:1809:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTAlgorithmParser.g:1810:7: lv_op_2_0= ruleXorOperator
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

            	    // InternalSTAlgorithmParser.g:1828:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTAlgorithmParser.g:1829:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1829:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTAlgorithmParser.g:1830:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_43);
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalSTAlgorithmParser.g:1852:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1852:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTAlgorithmParser.g:1853:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTAlgorithmParser.g:1859:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1865:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:1866:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:1866:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTAlgorithmParser.g:1867:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:1875:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==AND||LA34_0==Ampersand) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1876:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1876:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTAlgorithmParser.g:1877:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1877:5: ()
            	    // InternalSTAlgorithmParser.g:1878:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:1884:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTAlgorithmParser.g:1885:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:1885:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTAlgorithmParser.g:1886:7: lv_op_2_0= ruleAndOperator
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

            	    // InternalSTAlgorithmParser.g:1904:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTAlgorithmParser.g:1905:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1905:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTAlgorithmParser.g:1906:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
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
    // InternalSTAlgorithmParser.g:1928:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTAlgorithmParser.g:1928:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTAlgorithmParser.g:1929:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTAlgorithmParser.g:1935:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:1941:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:1942:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:1942:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTAlgorithmParser.g:1943:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_45);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:1951:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==LessThanSignGreaterThanSign||LA35_0==EqualsSign) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:1952:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1952:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTAlgorithmParser.g:1953:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:1953:5: ()
            	    // InternalSTAlgorithmParser.g:1954:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:1960:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTAlgorithmParser.g:1961:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:1961:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTAlgorithmParser.g:1962:7: lv_op_2_0= ruleEqualityOperator
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

            	    // InternalSTAlgorithmParser.g:1980:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTAlgorithmParser.g:1981:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:1981:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTAlgorithmParser.g:1982:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_45);
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalSTAlgorithmParser.g:2004:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2004:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTAlgorithmParser.g:2005:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTAlgorithmParser.g:2011:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2017:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2018:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2018:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2019:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2027:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==LessThanSignEqualsSign||LA36_0==GreaterThanSignEqualsSign||LA36_0==LessThanSign||LA36_0==GreaterThanSign) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2028:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2028:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2029:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2029:5: ()
            	    // InternalSTAlgorithmParser.g:2030:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2036:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTAlgorithmParser.g:2037:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2037:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTAlgorithmParser.g:2038:7: lv_op_2_0= ruleCompareOperator
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

            	    // InternalSTAlgorithmParser.g:2056:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTAlgorithmParser.g:2057:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2057:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTAlgorithmParser.g:2058:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_46);
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
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalSTAlgorithmParser.g:2080:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2080:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTAlgorithmParser.g:2081:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTAlgorithmParser.g:2087:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2093:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2094:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2094:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2095:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_47);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2103:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==PlusSign||LA37_0==HyphenMinus) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2104:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2104:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2105:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2105:5: ()
            	    // InternalSTAlgorithmParser.g:2106:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2112:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTAlgorithmParser.g:2113:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2113:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTAlgorithmParser.g:2114:7: lv_op_2_0= ruleAddSubOperator
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

            	    // InternalSTAlgorithmParser.g:2132:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTAlgorithmParser.g:2133:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2133:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTAlgorithmParser.g:2134:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
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
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalSTAlgorithmParser.g:2156:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2156:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTAlgorithmParser.g:2157:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTAlgorithmParser.g:2163:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2169:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2170:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2170:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2171:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_48);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2179:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==MOD||LA38_0==Asterisk||LA38_0==Solidus) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2180:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2180:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2181:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2181:5: ()
            	    // InternalSTAlgorithmParser.g:2182:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2188:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTAlgorithmParser.g:2189:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2189:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTAlgorithmParser.g:2190:7: lv_op_2_0= ruleMulDivModOperator
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

            	    // InternalSTAlgorithmParser.g:2208:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTAlgorithmParser.g:2209:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2209:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTAlgorithmParser.g:2210:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_48);
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
    // $ANTLR end "ruleSTMulDivModExpression"


    // $ANTLR start "entryRuleSTPowerExpression"
    // InternalSTAlgorithmParser.g:2232:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2232:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTAlgorithmParser.g:2233:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTAlgorithmParser.g:2239:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2245:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalSTAlgorithmParser.g:2246:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalSTAlgorithmParser.g:2246:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalSTAlgorithmParser.g:2247:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2255:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==AsteriskAsterisk) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2256:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2256:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTAlgorithmParser.g:2257:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2257:5: ()
            	    // InternalSTAlgorithmParser.g:2258:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTAlgorithmParser.g:2264:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTAlgorithmParser.g:2265:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTAlgorithmParser.g:2265:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTAlgorithmParser.g:2266:7: lv_op_2_0= rulePowerOperator
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

            	    // InternalSTAlgorithmParser.g:2284:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalSTAlgorithmParser.g:2285:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2285:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalSTAlgorithmParser.g:2286:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTUnaryExpression"
    // InternalSTAlgorithmParser.g:2308:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2308:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalSTAlgorithmParser.g:2309:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalSTAlgorithmParser.g:2315:1: ruleSTUnaryExpression returns [EObject current=null] : ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2321:2: ( ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalSTAlgorithmParser.g:2322:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalSTAlgorithmParser.g:2322:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            int alt40=2;
            alt40 = dfa40.predict(input);
            switch (alt40) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2323:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    {
                    // InternalSTAlgorithmParser.g:2323:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    // InternalSTAlgorithmParser.g:2324:4: ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalSTAlgorithmParser.g:2335:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalSTAlgorithmParser.g:2335:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    // InternalSTAlgorithmParser.g:2336:4: () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    {
                    // InternalSTAlgorithmParser.g:2336:4: ()
                    // InternalSTAlgorithmParser.g:2337:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:2343:4: ( (lv_op_2_0= ruleUnaryOperator ) )
                    // InternalSTAlgorithmParser.g:2344:5: (lv_op_2_0= ruleUnaryOperator )
                    {
                    // InternalSTAlgorithmParser.g:2344:5: (lv_op_2_0= ruleUnaryOperator )
                    // InternalSTAlgorithmParser.g:2345:6: lv_op_2_0= ruleUnaryOperator
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

                    // InternalSTAlgorithmParser.g:2362:4: ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    // InternalSTAlgorithmParser.g:2363:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    {
                    // InternalSTAlgorithmParser.g:2363:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    // InternalSTAlgorithmParser.g:2364:6: lv_expression_3_0= ruleSTUnaryExpression
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
    // InternalSTAlgorithmParser.g:2386:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2386:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalSTAlgorithmParser.g:2387:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalSTAlgorithmParser.g:2393:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalSTAlgorithmParser.g:2399:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalSTAlgorithmParser.g:2400:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalSTAlgorithmParser.g:2400:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalSTAlgorithmParser.g:2401:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_50);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTAlgorithmParser.g:2409:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop43:
            do {
                int alt43=3;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==FullStop) ) {
                    alt43=1;
                }
                else if ( (LA43_0==LeftSquareBracket) ) {
                    alt43=2;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:2410:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2410:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalSTAlgorithmParser.g:2411:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2411:5: ()
            	    // InternalSTAlgorithmParser.g:2412:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_51); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalSTAlgorithmParser.g:2422:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalSTAlgorithmParser.g:2423:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalSTAlgorithmParser.g:2423:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalSTAlgorithmParser.g:2424:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2424:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt41=2;
            	    int LA41_0 = input.LA(1);

            	    if ( (LA41_0==RULE_ID) ) {
            	        alt41=1;
            	    }
            	    else if ( ((LA41_0>=B && LA41_0<=X)||LA41_0==RULE_INT) ) {
            	        alt41=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 41, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt41) {
            	        case 1 :
            	            // InternalSTAlgorithmParser.g:2425:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_50);
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
            	            // InternalSTAlgorithmParser.g:2441:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_50);
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
            	    // InternalSTAlgorithmParser.g:2461:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalSTAlgorithmParser.g:2461:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalSTAlgorithmParser.g:2462:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalSTAlgorithmParser.g:2462:5: ()
            	    // InternalSTAlgorithmParser.g:2463:6: 
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
            	    // InternalSTAlgorithmParser.g:2473:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalSTAlgorithmParser.g:2474:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalSTAlgorithmParser.g:2474:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalSTAlgorithmParser.g:2475:7: lv_index_6_0= ruleSTExpression
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

            	    // InternalSTAlgorithmParser.g:2492:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop42:
            	    do {
            	        int alt42=2;
            	        int LA42_0 = input.LA(1);

            	        if ( (LA42_0==Comma) ) {
            	            alt42=1;
            	        }


            	        switch (alt42) {
            	    	case 1 :
            	    	    // InternalSTAlgorithmParser.g:2493:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalSTAlgorithmParser.g:2497:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalSTAlgorithmParser.g:2498:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalSTAlgorithmParser.g:2498:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalSTAlgorithmParser.g:2499:8: lv_index_8_0= ruleSTExpression
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
            	    	    break loop42;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
            	      				
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
    // $ANTLR end "ruleSTAccessExpression"


    // $ANTLR start "entryRuleSTPrimaryExpression"
    // InternalSTAlgorithmParser.g:2527:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2527:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalSTAlgorithmParser.g:2528:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalSTAlgorithmParser.g:2534:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STLiteralExpressions_4 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2540:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions ) )
            // InternalSTAlgorithmParser.g:2541:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            {
            // InternalSTAlgorithmParser.g:2541:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STLiteralExpressions_4= ruleSTLiteralExpressions )
            int alt44=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt44=1;
                }
                break;
            case RULE_ID:
                {
                alt44=2;
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
                    // InternalSTAlgorithmParser.g:2542:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTAlgorithmParser.g:2542:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTAlgorithmParser.g:2543:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
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
                    // InternalSTAlgorithmParser.g:2561:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalSTAlgorithmParser.g:2570:3: this_STLiteralExpressions_4= ruleSTLiteralExpressions
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
    // InternalSTAlgorithmParser.g:2582:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2582:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalSTAlgorithmParser.g:2583:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalSTAlgorithmParser.g:2589:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )? ) ;
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
            // InternalSTAlgorithmParser.g:2595:2: ( ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalSTAlgorithmParser.g:2596:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalSTAlgorithmParser.g:2596:2: ( () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalSTAlgorithmParser.g:2597:3: () ( (otherlv_1= RULE_ID ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalSTAlgorithmParser.g:2597:3: ()
            // InternalSTAlgorithmParser.g:2598:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:2604:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTAlgorithmParser.g:2605:4: (otherlv_1= RULE_ID )
            {
            // InternalSTAlgorithmParser.g:2605:4: (otherlv_1= RULE_ID )
            // InternalSTAlgorithmParser.g:2606:5: otherlv_1= RULE_ID
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

            // InternalSTAlgorithmParser.g:2617:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )?
            int alt47=2;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2618:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalSTAlgorithmParser.g:2618:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis )
                    // InternalSTAlgorithmParser.g:2619:5: ( LeftParenthesis )=>otherlv_2= LeftParenthesis
                    {
                    otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getSTFeatureExpressionAccess().getLeftParenthesisKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:2625:4: ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( ((LA46_0>=LDATE_AND_TIME && LA46_0<=DATE_AND_TIME)||LA46_0==TIME_OF_DAY||LA46_0==WSTRING||(LA46_0>=STRING && LA46_0<=DWORD)||(LA46_0>=LDATE && LA46_0<=LWORD)||(LA46_0>=UDINT && LA46_0<=WCHAR)||(LA46_0>=BOOL && LA46_0<=DINT)||(LA46_0>=FALSE && LA46_0<=UINT)||LA46_0==WORD||(LA46_0>=INT && LA46_0<=LDT)||(LA46_0>=TOD && LA46_0<=TRUE)||LA46_0==DT||(LA46_0>=LD && LA46_0<=LT)||LA46_0==NOT||LA46_0==D_2||LA46_0==T||LA46_0==LeftParenthesis||LA46_0==PlusSign||LA46_0==HyphenMinus||(LA46_0>=RULE_NON_DECIMAL && LA46_0<=RULE_INT)||(LA46_0>=RULE_ID && LA46_0<=RULE_WSTRING)) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // InternalSTAlgorithmParser.g:2626:5: ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )*
                            {
                            // InternalSTAlgorithmParser.g:2626:5: ( (lv_parameters_3_0= ruleSTExpression ) )
                            // InternalSTAlgorithmParser.g:2627:6: (lv_parameters_3_0= ruleSTExpression )
                            {
                            // InternalSTAlgorithmParser.g:2627:6: (lv_parameters_3_0= ruleSTExpression )
                            // InternalSTAlgorithmParser.g:2628:7: lv_parameters_3_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTExpressionParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_53);
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

                            // InternalSTAlgorithmParser.g:2645:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )*
                            loop45:
                            do {
                                int alt45=2;
                                int LA45_0 = input.LA(1);

                                if ( (LA45_0==Comma) ) {
                                    alt45=1;
                                }


                                switch (alt45) {
                            	case 1 :
                            	    // InternalSTAlgorithmParser.g:2646:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_15); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalSTAlgorithmParser.g:2650:6: ( (lv_parameters_5_0= ruleSTExpression ) )
                            	    // InternalSTAlgorithmParser.g:2651:7: (lv_parameters_5_0= ruleSTExpression )
                            	    {
                            	    // InternalSTAlgorithmParser.g:2651:7: (lv_parameters_5_0= ruleSTExpression )
                            	    // InternalSTAlgorithmParser.g:2652:8: lv_parameters_5_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTExpressionParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_53);
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
                            	    break loop45;
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
    // InternalSTAlgorithmParser.g:2680:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalSTAlgorithmParser.g:2680:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalSTAlgorithmParser.g:2681:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalSTAlgorithmParser.g:2687:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Enumerator lv_specifier_1_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2693:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) ) )
            // InternalSTAlgorithmParser.g:2694:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) )
            {
            // InternalSTAlgorithmParser.g:2694:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) ) )
            // InternalSTAlgorithmParser.g:2695:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( (lv_index_2_0= RULE_INT ) )
            {
            // InternalSTAlgorithmParser.g:2695:3: ()
            // InternalSTAlgorithmParser.g:2696:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTAlgorithmParser.g:2702:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( ((LA48_0>=B && LA48_0<=X)) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2703:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalSTAlgorithmParser.g:2703:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalSTAlgorithmParser.g:2704:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_54);
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

            // InternalSTAlgorithmParser.g:2721:3: ( (lv_index_2_0= RULE_INT ) )
            // InternalSTAlgorithmParser.g:2722:4: (lv_index_2_0= RULE_INT )
            {
            // InternalSTAlgorithmParser.g:2722:4: (lv_index_2_0= RULE_INT )
            // InternalSTAlgorithmParser.g:2723:5: lv_index_2_0= RULE_INT
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
    // InternalSTAlgorithmParser.g:2743:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTAlgorithmParser.g:2743:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTAlgorithmParser.g:2744:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTAlgorithmParser.g:2750:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
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
            // InternalSTAlgorithmParser.g:2756:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalSTAlgorithmParser.g:2757:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalSTAlgorithmParser.g:2757:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt49=6;
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
                alt49=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D_2:
                {
                alt49=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt49=3;
                }
                break;
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt49=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt49=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
            case RULE_WSTRING:
                {
                alt49=6;
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
                    // InternalSTAlgorithmParser.g:2758:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalSTAlgorithmParser.g:2767:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalSTAlgorithmParser.g:2776:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalSTAlgorithmParser.g:2785:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalSTAlgorithmParser.g:2794:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalSTAlgorithmParser.g:2803:3: this_STStringLiteral_5= ruleSTStringLiteral
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
    // InternalSTAlgorithmParser.g:2815:1: entryRuleSTNumericLiteralType returns [EObject current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final EObject entryRuleSTNumericLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:2815:61: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalSTAlgorithmParser.g:2816:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalSTAlgorithmParser.g:2822:1: ruleSTNumericLiteralType returns [EObject current=null] : (otherlv_0= BOOL | otherlv_1= BYTE | otherlv_2= WORD | otherlv_3= DWORD | otherlv_4= LWORD | otherlv_5= SINT | otherlv_6= INT | otherlv_7= DINT | otherlv_8= LINT | otherlv_9= USINT | otherlv_10= UINT | otherlv_11= UDINT | otherlv_12= ULINT | otherlv_13= REAL | otherlv_14= LREAL ) ;
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
            // InternalSTAlgorithmParser.g:2828:2: ( (otherlv_0= BOOL | otherlv_1= BYTE | otherlv_2= WORD | otherlv_3= DWORD | otherlv_4= LWORD | otherlv_5= SINT | otherlv_6= INT | otherlv_7= DINT | otherlv_8= LINT | otherlv_9= USINT | otherlv_10= UINT | otherlv_11= UDINT | otherlv_12= ULINT | otherlv_13= REAL | otherlv_14= LREAL ) )
            // InternalSTAlgorithmParser.g:2829:2: (otherlv_0= BOOL | otherlv_1= BYTE | otherlv_2= WORD | otherlv_3= DWORD | otherlv_4= LWORD | otherlv_5= SINT | otherlv_6= INT | otherlv_7= DINT | otherlv_8= LINT | otherlv_9= USINT | otherlv_10= UINT | otherlv_11= UDINT | otherlv_12= ULINT | otherlv_13= REAL | otherlv_14= LREAL )
            {
            // InternalSTAlgorithmParser.g:2829:2: (otherlv_0= BOOL | otherlv_1= BYTE | otherlv_2= WORD | otherlv_3= DWORD | otherlv_4= LWORD | otherlv_5= SINT | otherlv_6= INT | otherlv_7= DINT | otherlv_8= LINT | otherlv_9= USINT | otherlv_10= UINT | otherlv_11= UDINT | otherlv_12= ULINT | otherlv_13= REAL | otherlv_14= LREAL )
            int alt50=15;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt50=1;
                }
                break;
            case BYTE:
                {
                alt50=2;
                }
                break;
            case WORD:
                {
                alt50=3;
                }
                break;
            case DWORD:
                {
                alt50=4;
                }
                break;
            case LWORD:
                {
                alt50=5;
                }
                break;
            case SINT:
                {
                alt50=6;
                }
                break;
            case INT:
                {
                alt50=7;
                }
                break;
            case DINT:
                {
                alt50=8;
                }
                break;
            case LINT:
                {
                alt50=9;
                }
                break;
            case USINT:
                {
                alt50=10;
                }
                break;
            case UINT:
                {
                alt50=11;
                }
                break;
            case UDINT:
                {
                alt50=12;
                }
                break;
            case ULINT:
                {
                alt50=13;
                }
                break;
            case REAL:
                {
                alt50=14;
                }
                break;
            case LREAL:
                {
                alt50=15;
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
                    // InternalSTAlgorithmParser.g:2830:3: otherlv_0= BOOL
                    {
                    otherlv_0=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTNumericLiteralTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:2835:3: otherlv_1= BYTE
                    {
                    otherlv_1=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:2840:3: otherlv_2= WORD
                    {
                    otherlv_2=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTNumericLiteralTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:2845:3: otherlv_3= DWORD
                    {
                    otherlv_3=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_3, grammarAccess.getSTNumericLiteralTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTAlgorithmParser.g:2850:3: otherlv_4= LWORD
                    {
                    otherlv_4=(Token)match(input,LWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_4, grammarAccess.getSTNumericLiteralTypeAccess().getLWORDKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTAlgorithmParser.g:2855:3: otherlv_5= SINT
                    {
                    otherlv_5=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_5, grammarAccess.getSTNumericLiteralTypeAccess().getSINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTAlgorithmParser.g:2860:3: otherlv_6= INT
                    {
                    otherlv_6=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_6, grammarAccess.getSTNumericLiteralTypeAccess().getINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTAlgorithmParser.g:2865:3: otherlv_7= DINT
                    {
                    otherlv_7=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_7, grammarAccess.getSTNumericLiteralTypeAccess().getDINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTAlgorithmParser.g:2870:3: otherlv_8= LINT
                    {
                    otherlv_8=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_8, grammarAccess.getSTNumericLiteralTypeAccess().getLINTKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSTAlgorithmParser.g:2875:3: otherlv_9= USINT
                    {
                    otherlv_9=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_9, grammarAccess.getSTNumericLiteralTypeAccess().getUSINTKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalSTAlgorithmParser.g:2880:3: otherlv_10= UINT
                    {
                    otherlv_10=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_10, grammarAccess.getSTNumericLiteralTypeAccess().getUINTKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalSTAlgorithmParser.g:2885:3: otherlv_11= UDINT
                    {
                    otherlv_11=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_11, grammarAccess.getSTNumericLiteralTypeAccess().getUDINTKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalSTAlgorithmParser.g:2890:3: otherlv_12= ULINT
                    {
                    otherlv_12=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_12, grammarAccess.getSTNumericLiteralTypeAccess().getULINTKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalSTAlgorithmParser.g:2895:3: otherlv_13= REAL
                    {
                    otherlv_13=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_13, grammarAccess.getSTNumericLiteralTypeAccess().getREALKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalSTAlgorithmParser.g:2900:3: otherlv_14= LREAL
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
    // InternalSTAlgorithmParser.g:2908:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:2908:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalSTAlgorithmParser.g:2909:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalSTAlgorithmParser.g:2915:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_1_3=null;
        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_1_1 = null;

        AntlrDatatypeRuleToken lv_value_1_2 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:2921:2: ( ( ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTAlgorithmParser.g:2922:2: ( ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTAlgorithmParser.g:2922:2: ( ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) ) )
            // InternalSTAlgorithmParser.g:2923:3: ( (lv_type_0_0= ruleSTNumericLiteralType ) )? ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTAlgorithmParser.g:2923:3: ( (lv_type_0_0= ruleSTNumericLiteralType ) )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==DWORD||LA51_0==LREAL||LA51_0==LWORD||(LA51_0>=UDINT && LA51_0<=USINT)||(LA51_0>=BOOL && LA51_0<=BYTE)||LA51_0==DINT||LA51_0==LINT||(LA51_0>=REAL && LA51_0<=SINT)||LA51_0==UINT||LA51_0==WORD||LA51_0==INT) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalSTAlgorithmParser.g:2924:4: (lv_type_0_0= ruleSTNumericLiteralType )
                    {
                    // InternalSTAlgorithmParser.g:2924:4: (lv_type_0_0= ruleSTNumericLiteralType )
                    // InternalSTAlgorithmParser.g:2925:5: lv_type_0_0= ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeSTNumericLiteralTypeParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_55);
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

            // InternalSTAlgorithmParser.g:2942:3: ( ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) ) )
            // InternalSTAlgorithmParser.g:2943:4: ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) )
            {
            // InternalSTAlgorithmParser.g:2943:4: ( (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL ) )
            // InternalSTAlgorithmParser.g:2944:5: (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL )
            {
            // InternalSTAlgorithmParser.g:2944:5: (lv_value_1_1= ruleBoolLiteral | lv_value_1_2= ruleNumber | lv_value_1_3= RULE_NON_DECIMAL )
            int alt52=3;
            switch ( input.LA(1) ) {
            case FALSE:
            case TRUE:
                {
                alt52=1;
                }
                break;
            case PlusSign:
            case HyphenMinus:
            case RULE_INT:
                {
                alt52=2;
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt52=3;
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
                    // InternalSTAlgorithmParser.g:2945:6: lv_value_1_1= ruleBoolLiteral
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
                    // InternalSTAlgorithmParser.g:2961:6: lv_value_1_2= ruleNumber
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
                    // InternalSTAlgorithmParser.g:2977:6: lv_value_1_3= RULE_NON_DECIMAL
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
    // InternalSTAlgorithmParser.g:2998:1: entryRuleSTDateLiteralType returns [EObject current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final EObject entryRuleSTDateLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:2998:58: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalSTAlgorithmParser.g:2999:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3005:1: ruleSTDateLiteralType returns [EObject current=null] : (otherlv_0= DATE | otherlv_1= LDATE | otherlv_2= D_2 | otherlv_3= LD ) ;
    public final EObject ruleSTDateLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3011:2: ( (otherlv_0= DATE | otherlv_1= LDATE | otherlv_2= D_2 | otherlv_3= LD ) )
            // InternalSTAlgorithmParser.g:3012:2: (otherlv_0= DATE | otherlv_1= LDATE | otherlv_2= D_2 | otherlv_3= LD )
            {
            // InternalSTAlgorithmParser.g:3012:2: (otherlv_0= DATE | otherlv_1= LDATE | otherlv_2= D_2 | otherlv_3= LD )
            int alt53=4;
            switch ( input.LA(1) ) {
            case DATE:
                {
                alt53=1;
                }
                break;
            case LDATE:
                {
                alt53=2;
                }
                break;
            case D_2:
                {
                alt53=3;
                }
                break;
            case LD:
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
                    // InternalSTAlgorithmParser.g:3013:3: otherlv_0= DATE
                    {
                    otherlv_0=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTDateLiteralTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3018:3: otherlv_1= LDATE
                    {
                    otherlv_1=(Token)match(input,LDATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralTypeAccess().getLDATEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3023:3: otherlv_2= D_2
                    {
                    otherlv_2=(Token)match(input,D_2,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:3028:3: otherlv_3= LD
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
    // InternalSTAlgorithmParser.g:3036:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3036:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalSTAlgorithmParser.g:3037:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalSTAlgorithmParser.g:3043:1: ruleSTDateLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3049:2: ( ( ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) ) ) )
            // InternalSTAlgorithmParser.g:3050:2: ( ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) ) )
            {
            // InternalSTAlgorithmParser.g:3050:2: ( ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) ) )
            // InternalSTAlgorithmParser.g:3051:3: ( (lv_type_0_0= ruleSTDateLiteralType ) ) ( (lv_value_1_0= ruleDate ) )
            {
            // InternalSTAlgorithmParser.g:3051:3: ( (lv_type_0_0= ruleSTDateLiteralType ) )
            // InternalSTAlgorithmParser.g:3052:4: (lv_type_0_0= ruleSTDateLiteralType )
            {
            // InternalSTAlgorithmParser.g:3052:4: (lv_type_0_0= ruleSTDateLiteralType )
            // InternalSTAlgorithmParser.g:3053:5: lv_type_0_0= ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeSTDateLiteralTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_54);
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

            // InternalSTAlgorithmParser.g:3070:3: ( (lv_value_1_0= ruleDate ) )
            // InternalSTAlgorithmParser.g:3071:4: (lv_value_1_0= ruleDate )
            {
            // InternalSTAlgorithmParser.g:3071:4: (lv_value_1_0= ruleDate )
            // InternalSTAlgorithmParser.g:3072:5: lv_value_1_0= ruleDate
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
    // InternalSTAlgorithmParser.g:3093:1: entryRuleSTTimeLiteralType returns [EObject current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final EObject entryRuleSTTimeLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3093:58: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalSTAlgorithmParser.g:3094:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3100:1: ruleSTTimeLiteralType returns [EObject current=null] : (otherlv_0= TIME | otherlv_1= LTIME | otherlv_2= T | otherlv_3= LT ) ;
    public final EObject ruleSTTimeLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3106:2: ( (otherlv_0= TIME | otherlv_1= LTIME | otherlv_2= T | otherlv_3= LT ) )
            // InternalSTAlgorithmParser.g:3107:2: (otherlv_0= TIME | otherlv_1= LTIME | otherlv_2= T | otherlv_3= LT )
            {
            // InternalSTAlgorithmParser.g:3107:2: (otherlv_0= TIME | otherlv_1= LTIME | otherlv_2= T | otherlv_3= LT )
            int alt54=4;
            switch ( input.LA(1) ) {
            case TIME:
                {
                alt54=1;
                }
                break;
            case LTIME:
                {
                alt54=2;
                }
                break;
            case T:
                {
                alt54=3;
                }
                break;
            case LT:
                {
                alt54=4;
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
                    // InternalSTAlgorithmParser.g:3108:3: otherlv_0= TIME
                    {
                    otherlv_0=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTTimeLiteralTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3113:3: otherlv_1= LTIME
                    {
                    otherlv_1=(Token)match(input,LTIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralTypeAccess().getLTIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3118:3: otherlv_2= T
                    {
                    otherlv_2=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:3123:3: otherlv_3= LT
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
    // InternalSTAlgorithmParser.g:3131:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3131:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalSTAlgorithmParser.g:3132:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalSTAlgorithmParser.g:3138:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3144:2: ( ( ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) ) ) )
            // InternalSTAlgorithmParser.g:3145:2: ( ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) ) )
            {
            // InternalSTAlgorithmParser.g:3145:2: ( ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) ) )
            // InternalSTAlgorithmParser.g:3146:3: ( (lv_type_0_0= ruleSTTimeLiteralType ) ) ( (lv_value_1_0= ruleTime ) )
            {
            // InternalSTAlgorithmParser.g:3146:3: ( (lv_type_0_0= ruleSTTimeLiteralType ) )
            // InternalSTAlgorithmParser.g:3147:4: (lv_type_0_0= ruleSTTimeLiteralType )
            {
            // InternalSTAlgorithmParser.g:3147:4: (lv_type_0_0= ruleSTTimeLiteralType )
            // InternalSTAlgorithmParser.g:3148:5: lv_type_0_0= ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeSTTimeLiteralTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_56);
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

            // InternalSTAlgorithmParser.g:3165:3: ( (lv_value_1_0= ruleTime ) )
            // InternalSTAlgorithmParser.g:3166:4: (lv_value_1_0= ruleTime )
            {
            // InternalSTAlgorithmParser.g:3166:4: (lv_value_1_0= ruleTime )
            // InternalSTAlgorithmParser.g:3167:5: lv_value_1_0= ruleTime
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
    // InternalSTAlgorithmParser.g:3188:1: entryRuleSTTimeOfDayLiteralType returns [EObject current=null] : iv_ruleSTTimeOfDayLiteralType= ruleSTTimeOfDayLiteralType EOF ;
    public final EObject entryRuleSTTimeOfDayLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3188:63: (iv_ruleSTTimeOfDayLiteralType= ruleSTTimeOfDayLiteralType EOF )
            // InternalSTAlgorithmParser.g:3189:2: iv_ruleSTTimeOfDayLiteralType= ruleSTTimeOfDayLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3195:1: ruleSTTimeOfDayLiteralType returns [EObject current=null] : (otherlv_0= TIME_OF_DAY | otherlv_1= TOD | otherlv_2= LTOD ) ;
    public final EObject ruleSTTimeOfDayLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3201:2: ( (otherlv_0= TIME_OF_DAY | otherlv_1= TOD | otherlv_2= LTOD ) )
            // InternalSTAlgorithmParser.g:3202:2: (otherlv_0= TIME_OF_DAY | otherlv_1= TOD | otherlv_2= LTOD )
            {
            // InternalSTAlgorithmParser.g:3202:2: (otherlv_0= TIME_OF_DAY | otherlv_1= TOD | otherlv_2= LTOD )
            int alt55=3;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt55=1;
                }
                break;
            case TOD:
                {
                alt55=2;
                }
                break;
            case LTOD:
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
                    // InternalSTAlgorithmParser.g:3203:3: otherlv_0= TIME_OF_DAY
                    {
                    otherlv_0=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTTimeOfDayLiteralTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3208:3: otherlv_1= TOD
                    {
                    otherlv_1=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralTypeAccess().getTODKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3213:3: otherlv_2= LTOD
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
    // InternalSTAlgorithmParser.g:3221:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3221:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalSTAlgorithmParser.g:3222:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalSTAlgorithmParser.g:3228:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3234:2: ( ( ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) ) ) )
            // InternalSTAlgorithmParser.g:3235:2: ( ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) ) )
            {
            // InternalSTAlgorithmParser.g:3235:2: ( ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) ) )
            // InternalSTAlgorithmParser.g:3236:3: ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) ) ( (lv_value_1_0= ruleTimeOfDay ) )
            {
            // InternalSTAlgorithmParser.g:3236:3: ( (lv_type_0_0= ruleSTTimeOfDayLiteralType ) )
            // InternalSTAlgorithmParser.g:3237:4: (lv_type_0_0= ruleSTTimeOfDayLiteralType )
            {
            // InternalSTAlgorithmParser.g:3237:4: (lv_type_0_0= ruleSTTimeOfDayLiteralType )
            // InternalSTAlgorithmParser.g:3238:5: lv_type_0_0= ruleSTTimeOfDayLiteralType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeSTTimeOfDayLiteralTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_54);
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

            // InternalSTAlgorithmParser.g:3255:3: ( (lv_value_1_0= ruleTimeOfDay ) )
            // InternalSTAlgorithmParser.g:3256:4: (lv_value_1_0= ruleTimeOfDay )
            {
            // InternalSTAlgorithmParser.g:3256:4: (lv_value_1_0= ruleTimeOfDay )
            // InternalSTAlgorithmParser.g:3257:5: lv_value_1_0= ruleTimeOfDay
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
    // InternalSTAlgorithmParser.g:3278:1: entryRuleSTDateAndTimeLiteralType returns [EObject current=null] : iv_ruleSTDateAndTimeLiteralType= ruleSTDateAndTimeLiteralType EOF ;
    public final EObject entryRuleSTDateAndTimeLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3278:65: (iv_ruleSTDateAndTimeLiteralType= ruleSTDateAndTimeLiteralType EOF )
            // InternalSTAlgorithmParser.g:3279:2: iv_ruleSTDateAndTimeLiteralType= ruleSTDateAndTimeLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3285:1: ruleSTDateAndTimeLiteralType returns [EObject current=null] : (otherlv_0= DATE_AND_TIME | otherlv_1= LDATE_AND_TIME | otherlv_2= DT | otherlv_3= LDT ) ;
    public final EObject ruleSTDateAndTimeLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3291:2: ( (otherlv_0= DATE_AND_TIME | otherlv_1= LDATE_AND_TIME | otherlv_2= DT | otherlv_3= LDT ) )
            // InternalSTAlgorithmParser.g:3292:2: (otherlv_0= DATE_AND_TIME | otherlv_1= LDATE_AND_TIME | otherlv_2= DT | otherlv_3= LDT )
            {
            // InternalSTAlgorithmParser.g:3292:2: (otherlv_0= DATE_AND_TIME | otherlv_1= LDATE_AND_TIME | otherlv_2= DT | otherlv_3= LDT )
            int alt56=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt56=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt56=2;
                }
                break;
            case DT:
                {
                alt56=3;
                }
                break;
            case LDT:
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
                    // InternalSTAlgorithmParser.g:3293:3: otherlv_0= DATE_AND_TIME
                    {
                    otherlv_0=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTDateAndTimeLiteralTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3298:3: otherlv_1= LDATE_AND_TIME
                    {
                    otherlv_1=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3303:3: otherlv_2= DT
                    {
                    otherlv_2=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTDateAndTimeLiteralTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:3308:3: otherlv_3= LDT
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
    // InternalSTAlgorithmParser.g:3316:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3316:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalSTAlgorithmParser.g:3317:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalSTAlgorithmParser.g:3323:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_dateValue_1_0 = null;

        AntlrDatatypeRuleToken lv_timeOfDayValue_3_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3329:2: ( ( ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) ) ) )
            // InternalSTAlgorithmParser.g:3330:2: ( ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) ) )
            {
            // InternalSTAlgorithmParser.g:3330:2: ( ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) ) )
            // InternalSTAlgorithmParser.g:3331:3: ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) ) ( (lv_dateValue_1_0= ruleDate ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) )
            {
            // InternalSTAlgorithmParser.g:3331:3: ( (lv_type_0_0= ruleSTDateAndTimeLiteralType ) )
            // InternalSTAlgorithmParser.g:3332:4: (lv_type_0_0= ruleSTDateAndTimeLiteralType )
            {
            // InternalSTAlgorithmParser.g:3332:4: (lv_type_0_0= ruleSTDateAndTimeLiteralType )
            // InternalSTAlgorithmParser.g:3333:5: lv_type_0_0= ruleSTDateAndTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeSTDateAndTimeLiteralTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_54);
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

            // InternalSTAlgorithmParser.g:3350:3: ( (lv_dateValue_1_0= ruleDate ) )
            // InternalSTAlgorithmParser.g:3351:4: (lv_dateValue_1_0= ruleDate )
            {
            // InternalSTAlgorithmParser.g:3351:4: (lv_dateValue_1_0= ruleDate )
            // InternalSTAlgorithmParser.g:3352:5: lv_dateValue_1_0= ruleDate
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getDateValueDateParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_57);
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

            otherlv_2=(Token)match(input,HyphenMinus,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTDateAndTimeLiteralAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalSTAlgorithmParser.g:3373:3: ( (lv_timeOfDayValue_3_0= ruleTimeOfDay ) )
            // InternalSTAlgorithmParser.g:3374:4: (lv_timeOfDayValue_3_0= ruleTimeOfDay )
            {
            // InternalSTAlgorithmParser.g:3374:4: (lv_timeOfDayValue_3_0= ruleTimeOfDay )
            // InternalSTAlgorithmParser.g:3375:5: lv_timeOfDayValue_3_0= ruleTimeOfDay
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
    // InternalSTAlgorithmParser.g:3396:1: entryRuleSTStringLiteralType returns [EObject current=null] : iv_ruleSTStringLiteralType= ruleSTStringLiteralType EOF ;
    public final EObject entryRuleSTStringLiteralType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteralType = null;


        try {
            // InternalSTAlgorithmParser.g:3396:60: (iv_ruleSTStringLiteralType= ruleSTStringLiteralType EOF )
            // InternalSTAlgorithmParser.g:3397:2: iv_ruleSTStringLiteralType= ruleSTStringLiteralType EOF
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
    // InternalSTAlgorithmParser.g:3403:1: ruleSTStringLiteralType returns [EObject current=null] : (otherlv_0= STRING | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR ) ;
    public final EObject ruleSTStringLiteralType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3409:2: ( (otherlv_0= STRING | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR ) )
            // InternalSTAlgorithmParser.g:3410:2: (otherlv_0= STRING | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )
            {
            // InternalSTAlgorithmParser.g:3410:2: (otherlv_0= STRING | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )
            int alt57=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt57=1;
                }
                break;
            case WSTRING:
                {
                alt57=2;
                }
                break;
            case CHAR:
                {
                alt57=3;
                }
                break;
            case WCHAR:
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
                    // InternalSTAlgorithmParser.g:3411:3: otherlv_0= STRING
                    {
                    otherlv_0=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_0, grammarAccess.getSTStringLiteralTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3416:3: otherlv_1= WSTRING
                    {
                    otherlv_1=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmParser.g:3421:3: otherlv_2= CHAR
                    {
                    otherlv_2=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			newLeafNode(otherlv_2, grammarAccess.getSTStringLiteralTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmParser.g:3426:3: otherlv_3= WCHAR
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
    // InternalSTAlgorithmParser.g:3434:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3434:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalSTAlgorithmParser.g:3435:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalSTAlgorithmParser.g:3441:1: ruleSTStringLiteral returns [EObject current=null] : ( ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_1_1=null;
        Token lv_value_1_2=null;
        EObject lv_type_0_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3447:2: ( ( ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) ) ) )
            // InternalSTAlgorithmParser.g:3448:2: ( ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) ) )
            {
            // InternalSTAlgorithmParser.g:3448:2: ( ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) ) )
            // InternalSTAlgorithmParser.g:3449:3: ( (lv_type_0_0= ruleSTStringLiteralType ) )? ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) )
            {
            // InternalSTAlgorithmParser.g:3449:3: ( (lv_type_0_0= ruleSTStringLiteralType ) )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==WSTRING||LA58_0==STRING||LA58_0==WCHAR||LA58_0==CHAR) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3450:4: (lv_type_0_0= ruleSTStringLiteralType )
                    {
                    // InternalSTAlgorithmParser.g:3450:4: (lv_type_0_0= ruleSTStringLiteralType )
                    // InternalSTAlgorithmParser.g:3451:5: lv_type_0_0= ruleSTStringLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeSTStringLiteralTypeParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_58);
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

            // InternalSTAlgorithmParser.g:3468:3: ( ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) ) )
            // InternalSTAlgorithmParser.g:3469:4: ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) )
            {
            // InternalSTAlgorithmParser.g:3469:4: ( (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING ) )
            // InternalSTAlgorithmParser.g:3470:5: (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING )
            {
            // InternalSTAlgorithmParser.g:3470:5: (lv_value_1_1= RULE_STRING | lv_value_1_2= RULE_WSTRING )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==RULE_STRING) ) {
                alt59=1;
            }
            else if ( (LA59_0==RULE_WSTRING) ) {
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
                    // InternalSTAlgorithmParser.g:3471:6: lv_value_1_1= RULE_STRING
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
                    // InternalSTAlgorithmParser.g:3486:6: lv_value_1_2= RULE_WSTRING
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
    // InternalSTAlgorithmParser.g:3507:1: entryRuleBoolLiteral returns [String current=null] : iv_ruleBoolLiteral= ruleBoolLiteral EOF ;
    public final String entryRuleBoolLiteral() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoolLiteral = null;


        try {
            // InternalSTAlgorithmParser.g:3507:51: (iv_ruleBoolLiteral= ruleBoolLiteral EOF )
            // InternalSTAlgorithmParser.g:3508:2: iv_ruleBoolLiteral= ruleBoolLiteral EOF
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
    // InternalSTAlgorithmParser.g:3514:1: ruleBoolLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE ) ;
    public final AntlrDatatypeRuleToken ruleBoolLiteral() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3520:2: ( (kw= TRUE | kw= FALSE ) )
            // InternalSTAlgorithmParser.g:3521:2: (kw= TRUE | kw= FALSE )
            {
            // InternalSTAlgorithmParser.g:3521:2: (kw= TRUE | kw= FALSE )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==TRUE) ) {
                alt60=1;
            }
            else if ( (LA60_0==FALSE) ) {
                alt60=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3522:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getBoolLiteralAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3528:3: kw= FALSE
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
    // InternalSTAlgorithmParser.g:3537:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;


        try {
            // InternalSTAlgorithmParser.g:3537:46: (iv_ruleNumber= ruleNumber EOF )
            // InternalSTAlgorithmParser.g:3538:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalSTAlgorithmParser.g:3544:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_EXT_INT_4=null;
        Token this_INT_5=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3550:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? ) )
            // InternalSTAlgorithmParser.g:3551:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            {
            // InternalSTAlgorithmParser.g:3551:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )? )
            // InternalSTAlgorithmParser.g:3552:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            {
            // InternalSTAlgorithmParser.g:3552:3: (kw= PlusSign | kw= HyphenMinus )?
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
                    // InternalSTAlgorithmParser.g:3553:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmParser.g:3559:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1());
              		
            }
            // InternalSTAlgorithmParser.g:3572:3: ( ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT ) )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==FullStop) ) {
                int LA63_1 = input.LA(2);

                if ( (LA63_1==RULE_INT) ) {
                    int LA63_3 = input.LA(3);

                    if ( (synpred4_InternalSTAlgorithmParser()) ) {
                        alt63=1;
                    }
                }
                else if ( (LA63_1==RULE_EXT_INT) && (synpred4_InternalSTAlgorithmParser())) {
                    alt63=1;
                }
            }
            switch (alt63) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3573:4: ( ( FullStop )=>kw= FullStop ) (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
                    {
                    // InternalSTAlgorithmParser.g:3573:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTAlgorithmParser.g:3574:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_2_0());
                      				
                    }

                    }

                    // InternalSTAlgorithmParser.g:3581:4: (this_EXT_INT_4= RULE_EXT_INT | this_INT_5= RULE_INT )
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
                            // InternalSTAlgorithmParser.g:3582:5: this_EXT_INT_4= RULE_EXT_INT
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
                            // InternalSTAlgorithmParser.g:3590:5: this_INT_5= RULE_INT
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
    // InternalSTAlgorithmParser.g:3603:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalSTAlgorithmParser.g:3603:44: (iv_ruleDate= ruleDate EOF )
            // InternalSTAlgorithmParser.g:3604:2: iv_ruleDate= ruleDate EOF
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
    // InternalSTAlgorithmParser.g:3610:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3616:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTAlgorithmParser.g:3617:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTAlgorithmParser.g:3617:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTAlgorithmParser.g:3618:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_54); if (state.failed) return current;
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
    // InternalSTAlgorithmParser.g:3653:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalSTAlgorithmParser.g:3653:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalSTAlgorithmParser.g:3654:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalSTAlgorithmParser.g:3660:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3666:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) )
            // InternalSTAlgorithmParser.g:3667:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            {
            // InternalSTAlgorithmParser.g:3667:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            // InternalSTAlgorithmParser.g:3668:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_54); if (state.failed) return current;
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
            kw=(Token)match(input,Colon,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTAlgorithmParser.g:3699:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==FullStop) ) {
                int LA64_1 = input.LA(2);

                if ( (LA64_1==RULE_INT) ) {
                    int LA64_3 = input.LA(3);

                    if ( (synpred5_InternalSTAlgorithmParser()) ) {
                        alt64=1;
                    }
                }
            }
            switch (alt64) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3700:4: ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT
                    {
                    // InternalSTAlgorithmParser.g:3700:4: ( ( FullStop )=>kw= FullStop )
                    // InternalSTAlgorithmParser.g:3701:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_54); if (state.failed) return current;
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
    // InternalSTAlgorithmParser.g:3720:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;


        try {
            // InternalSTAlgorithmParser.g:3720:44: (iv_ruleTime= ruleTime EOF )
            // InternalSTAlgorithmParser.g:3721:2: iv_ruleTime= ruleTime EOF
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
    // InternalSTAlgorithmParser.g:3727:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Number_0 = null;



        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3733:2: ( (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+ )
            // InternalSTAlgorithmParser.g:3734:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            {
            // InternalSTAlgorithmParser.g:3734:2: (this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )? )+
            int cnt67=0;
            loop67:
            do {
                int alt67=2;
                switch ( input.LA(1) ) {
                case PlusSign:
                    {
                    int LA67_2 = input.LA(2);

                    if ( (LA67_2==RULE_INT) ) {
                        int LA67_5 = input.LA(3);

                        if ( (LA67_5==FullStop) ) {
                            int LA67_6 = input.LA(4);

                            if ( (LA67_6==RULE_INT) ) {
                                int LA67_7 = input.LA(5);

                                if ( ((LA67_7>=MS && LA67_7<=NS)||LA67_7==US||(LA67_7>=D && LA67_7<=S)) ) {
                                    alt67=1;
                                }


                            }
                            else if ( (LA67_6==RULE_EXT_INT) ) {
                                int LA67_8 = input.LA(5);

                                if ( ((LA67_8>=MS && LA67_8<=NS)||LA67_8==US||(LA67_8>=D && LA67_8<=S)) ) {
                                    alt67=1;
                                }


                            }


                        }
                        else if ( ((LA67_5>=MS && LA67_5<=NS)||LA67_5==US||(LA67_5>=D && LA67_5<=S)) ) {
                            alt67=1;
                        }


                    }


                    }
                    break;
                case HyphenMinus:
                    {
                    int LA67_3 = input.LA(2);

                    if ( (LA67_3==RULE_INT) ) {
                        int LA67_5 = input.LA(3);

                        if ( (LA67_5==FullStop) ) {
                            int LA67_6 = input.LA(4);

                            if ( (LA67_6==RULE_INT) ) {
                                int LA67_7 = input.LA(5);

                                if ( ((LA67_7>=MS && LA67_7<=NS)||LA67_7==US||(LA67_7>=D && LA67_7<=S)) ) {
                                    alt67=1;
                                }


                            }
                            else if ( (LA67_6==RULE_EXT_INT) ) {
                                int LA67_8 = input.LA(5);

                                if ( ((LA67_8>=MS && LA67_8<=NS)||LA67_8==US||(LA67_8>=D && LA67_8<=S)) ) {
                                    alt67=1;
                                }


                            }


                        }
                        else if ( ((LA67_5>=MS && LA67_5<=NS)||LA67_5==US||(LA67_5>=D && LA67_5<=S)) ) {
                            alt67=1;
                        }


                    }


                    }
                    break;
                case RULE_INT:
                    {
                    alt67=1;
                    }
                    break;

                }

                switch (alt67) {
            	case 1 :
            	    // InternalSTAlgorithmParser.g:3735:3: this_Number_0= ruleNumber (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS ) (kw= KW__ )?
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getTimeAccess().getNumberParserRuleCall_0());
            	      		
            	    }
            	    pushFollow(FOLLOW_61);
            	    this_Number_0=ruleNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_Number_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }
            	    // InternalSTAlgorithmParser.g:3745:3: (kw= D | kw= H | kw= M | kw= S | kw= MS | kw= US | kw= NS )
            	    int alt65=7;
            	    switch ( input.LA(1) ) {
            	    case D:
            	        {
            	        alt65=1;
            	        }
            	        break;
            	    case H:
            	        {
            	        alt65=2;
            	        }
            	        break;
            	    case M:
            	        {
            	        alt65=3;
            	        }
            	        break;
            	    case S:
            	        {
            	        alt65=4;
            	        }
            	        break;
            	    case MS:
            	        {
            	        alt65=5;
            	        }
            	        break;
            	    case US:
            	        {
            	        alt65=6;
            	        }
            	        break;
            	    case NS:
            	        {
            	        alt65=7;
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
            	            // InternalSTAlgorithmParser.g:3746:4: kw= D
            	            {
            	            kw=(Token)match(input,D,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getDKeyword_1_0());
            	              			
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTAlgorithmParser.g:3752:4: kw= H
            	            {
            	            kw=(Token)match(input,H,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getHKeyword_1_1());
            	              			
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTAlgorithmParser.g:3758:4: kw= M
            	            {
            	            kw=(Token)match(input,M,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMKeyword_1_2());
            	              			
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTAlgorithmParser.g:3764:4: kw= S
            	            {
            	            kw=(Token)match(input,S,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getSKeyword_1_3());
            	              			
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalSTAlgorithmParser.g:3770:4: kw= MS
            	            {
            	            kw=(Token)match(input,MS,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getMSKeyword_1_4());
            	              			
            	            }

            	            }
            	            break;
            	        case 6 :
            	            // InternalSTAlgorithmParser.g:3776:4: kw= US
            	            {
            	            kw=(Token)match(input,US,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getUSKeyword_1_5());
            	              			
            	            }

            	            }
            	            break;
            	        case 7 :
            	            // InternalSTAlgorithmParser.g:3782:4: kw= NS
            	            {
            	            kw=(Token)match(input,NS,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              				current.merge(kw);
            	              				newLeafNode(kw, grammarAccess.getTimeAccess().getNSKeyword_1_6());
            	              			
            	            }

            	            }
            	            break;

            	    }

            	    // InternalSTAlgorithmParser.g:3788:3: (kw= KW__ )?
            	    int alt66=2;
            	    int LA66_0 = input.LA(1);

            	    if ( (LA66_0==KW__) ) {
            	        alt66=1;
            	    }
            	    switch (alt66) {
            	        case 1 :
            	            // InternalSTAlgorithmParser.g:3789:4: kw= KW__
            	            {
            	            kw=(Token)match(input,KW__,FOLLOW_63); if (state.failed) return current;
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
            	    if ( cnt67 >= 1 ) break loop67;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(67, input);
                        throw eee;
                }
                cnt67++;
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
    // InternalSTAlgorithmParser.g:3799:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3805:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTAlgorithmParser.g:3806:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTAlgorithmParser.g:3806:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTAlgorithmParser.g:3807:3: enumLiteral_0= FullStopFullStop
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
    // InternalSTAlgorithmParser.g:3816:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3822:2: ( (enumLiteral_0= OR ) )
            // InternalSTAlgorithmParser.g:3823:2: (enumLiteral_0= OR )
            {
            // InternalSTAlgorithmParser.g:3823:2: (enumLiteral_0= OR )
            // InternalSTAlgorithmParser.g:3824:3: enumLiteral_0= OR
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
    // InternalSTAlgorithmParser.g:3833:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3839:2: ( (enumLiteral_0= XOR ) )
            // InternalSTAlgorithmParser.g:3840:2: (enumLiteral_0= XOR )
            {
            // InternalSTAlgorithmParser.g:3840:2: (enumLiteral_0= XOR )
            // InternalSTAlgorithmParser.g:3841:3: enumLiteral_0= XOR
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
    // InternalSTAlgorithmParser.g:3850:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3856:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTAlgorithmParser.g:3857:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTAlgorithmParser.g:3857:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==AND) ) {
                alt68=1;
            }
            else if ( (LA68_0==Ampersand) ) {
                alt68=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }
            switch (alt68) {
                case 1 :
                    // InternalSTAlgorithmParser.g:3858:3: (enumLiteral_0= AND )
                    {
                    // InternalSTAlgorithmParser.g:3858:3: (enumLiteral_0= AND )
                    // InternalSTAlgorithmParser.g:3859:4: enumLiteral_0= AND
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
                    // InternalSTAlgorithmParser.g:3866:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTAlgorithmParser.g:3866:3: (enumLiteral_1= Ampersand )
                    // InternalSTAlgorithmParser.g:3867:4: enumLiteral_1= Ampersand
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
    // InternalSTAlgorithmParser.g:3877:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3883:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTAlgorithmParser.g:3884:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTAlgorithmParser.g:3884:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==EqualsSign) ) {
                alt69=1;
            }
            else if ( (LA69_0==LessThanSignGreaterThanSign) ) {
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
                    // InternalSTAlgorithmParser.g:3885:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:3885:3: (enumLiteral_0= EqualsSign )
                    // InternalSTAlgorithmParser.g:3886:4: enumLiteral_0= EqualsSign
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
                    // InternalSTAlgorithmParser.g:3893:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTAlgorithmParser.g:3893:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTAlgorithmParser.g:3894:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalSTAlgorithmParser.g:3904:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3910:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTAlgorithmParser.g:3911:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTAlgorithmParser.g:3911:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt70=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt70=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt70=2;
                }
                break;
            case GreaterThanSign:
                {
                alt70=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt70=4;
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
                    // InternalSTAlgorithmParser.g:3912:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTAlgorithmParser.g:3912:3: (enumLiteral_0= LessThanSign )
                    // InternalSTAlgorithmParser.g:3913:4: enumLiteral_0= LessThanSign
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
                    // InternalSTAlgorithmParser.g:3920:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:3920:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTAlgorithmParser.g:3921:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalSTAlgorithmParser.g:3928:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTAlgorithmParser.g:3928:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTAlgorithmParser.g:3929:4: enumLiteral_2= GreaterThanSign
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
                    // InternalSTAlgorithmParser.g:3936:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTAlgorithmParser.g:3936:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTAlgorithmParser.g:3937:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalSTAlgorithmParser.g:3947:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3953:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTAlgorithmParser.g:3954:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTAlgorithmParser.g:3954:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==PlusSign) ) {
                alt71=1;
            }
            else if ( (LA71_0==HyphenMinus) ) {
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
                    // InternalSTAlgorithmParser.g:3955:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTAlgorithmParser.g:3955:3: (enumLiteral_0= PlusSign )
                    // InternalSTAlgorithmParser.g:3956:4: enumLiteral_0= PlusSign
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
                    // InternalSTAlgorithmParser.g:3963:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTAlgorithmParser.g:3963:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTAlgorithmParser.g:3964:4: enumLiteral_1= HyphenMinus
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
    // InternalSTAlgorithmParser.g:3974:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:3980:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTAlgorithmParser.g:3981:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTAlgorithmParser.g:3981:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt72=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt72=1;
                }
                break;
            case Solidus:
                {
                alt72=2;
                }
                break;
            case MOD:
                {
                alt72=3;
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
                    // InternalSTAlgorithmParser.g:3982:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTAlgorithmParser.g:3982:3: (enumLiteral_0= Asterisk )
                    // InternalSTAlgorithmParser.g:3983:4: enumLiteral_0= Asterisk
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
                    // InternalSTAlgorithmParser.g:3990:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTAlgorithmParser.g:3990:3: (enumLiteral_1= Solidus )
                    // InternalSTAlgorithmParser.g:3991:4: enumLiteral_1= Solidus
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
                    // InternalSTAlgorithmParser.g:3998:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTAlgorithmParser.g:3998:3: (enumLiteral_2= MOD )
                    // InternalSTAlgorithmParser.g:3999:4: enumLiteral_2= MOD
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
    // InternalSTAlgorithmParser.g:4009:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4015:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTAlgorithmParser.g:4016:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTAlgorithmParser.g:4016:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTAlgorithmParser.g:4017:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalSTAlgorithmParser.g:4026:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4032:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTAlgorithmParser.g:4033:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTAlgorithmParser.g:4033:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt73=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt73=1;
                }
                break;
            case PlusSign:
                {
                alt73=2;
                }
                break;
            case NOT:
                {
                alt73=3;
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
                    // InternalSTAlgorithmParser.g:4034:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTAlgorithmParser.g:4034:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTAlgorithmParser.g:4035:4: enumLiteral_0= HyphenMinus
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
                    // InternalSTAlgorithmParser.g:4042:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTAlgorithmParser.g:4042:3: (enumLiteral_1= PlusSign )
                    // InternalSTAlgorithmParser.g:4043:4: enumLiteral_1= PlusSign
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
                    // InternalSTAlgorithmParser.g:4050:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTAlgorithmParser.g:4050:3: (enumLiteral_2= NOT )
                    // InternalSTAlgorithmParser.g:4051:4: enumLiteral_2= NOT
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
    // InternalSTAlgorithmParser.g:4061:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSTAlgorithmParser.g:4067:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalSTAlgorithmParser.g:4068:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalSTAlgorithmParser.g:4068:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt74=5;
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
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;
            }

            switch (alt74) {
                case 1 :
                    // InternalSTAlgorithmParser.g:4069:3: (enumLiteral_0= L )
                    {
                    // InternalSTAlgorithmParser.g:4069:3: (enumLiteral_0= L )
                    // InternalSTAlgorithmParser.g:4070:4: enumLiteral_0= L
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
                    // InternalSTAlgorithmParser.g:4077:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTAlgorithmParser.g:4077:3: (enumLiteral_1= D_1 )
                    // InternalSTAlgorithmParser.g:4078:4: enumLiteral_1= D_1
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
                    // InternalSTAlgorithmParser.g:4085:3: (enumLiteral_2= W )
                    {
                    // InternalSTAlgorithmParser.g:4085:3: (enumLiteral_2= W )
                    // InternalSTAlgorithmParser.g:4086:4: enumLiteral_2= W
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
                    // InternalSTAlgorithmParser.g:4093:3: (enumLiteral_3= B )
                    {
                    // InternalSTAlgorithmParser.g:4093:3: (enumLiteral_3= B )
                    // InternalSTAlgorithmParser.g:4094:4: enumLiteral_3= B
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
                    // InternalSTAlgorithmParser.g:4101:3: (enumLiteral_4= X )
                    {
                    // InternalSTAlgorithmParser.g:4101:3: (enumLiteral_4= X )
                    // InternalSTAlgorithmParser.g:4102:4: enumLiteral_4= X
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
        // InternalSTAlgorithmParser.g:1252:4: ( ( ruleSTStatement ) )
        // InternalSTAlgorithmParser.g:1252:5: ( ruleSTStatement )
        {
        // InternalSTAlgorithmParser.g:1252:5: ( ruleSTStatement )
        // InternalSTAlgorithmParser.g:1253:5: ruleSTStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalSTAlgorithmParser

    // $ANTLR start synpred2_InternalSTAlgorithmParser
    public final void synpred2_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:2324:4: ( ruleSTAccessExpression )
        // InternalSTAlgorithmParser.g:2324:5: ruleSTAccessExpression
        {
        pushFollow(FOLLOW_2);
        ruleSTAccessExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalSTAlgorithmParser

    // $ANTLR start synpred3_InternalSTAlgorithmParser
    public final void synpred3_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:2619:5: ( LeftParenthesis )
        // InternalSTAlgorithmParser.g:2619:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalSTAlgorithmParser

    // $ANTLR start synpred4_InternalSTAlgorithmParser
    public final void synpred4_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:3574:5: ( FullStop )
        // InternalSTAlgorithmParser.g:3574:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_InternalSTAlgorithmParser

    // $ANTLR start synpred5_InternalSTAlgorithmParser
    public final void synpred5_InternalSTAlgorithmParser_fragment() throws RecognitionException {   
        // InternalSTAlgorithmParser.g:3701:5: ( FullStop )
        // InternalSTAlgorithmParser.g:3701:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_InternalSTAlgorithmParser

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


    protected DFA25 dfa25 = new DFA25(this);
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA47 dfa47 = new DFA47(this);
    static final String dfa_1s = "\72\uffff";
    static final String dfa_2s = "\1\1\71\uffff";
    static final String dfa_3s = "\1\4\2\uffff\54\0\13\uffff";
    static final String dfa_4s = "\1\165\2\uffff\54\0\13\uffff";
    static final String dfa_5s = "\1\uffff\1\2\57\uffff\11\1";
    static final String dfa_6s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\13\uffff}>";
    static final String[] dfa_7s = {
            "\1\46\1\45\1\uffff\1\42\6\uffff\1\67\1\1\1\uffff\1\52\2\uffff\1\51\1\10\1\uffff\1\33\1\23\1\37\1\11\1\65\1\66\1\20\1\21\1\16\1\54\1\uffff\1\5\1\6\1\53\1\32\1\14\1\uffff\1\25\1\15\1\44\1\22\1\12\1\36\1\17\1\uffff\1\64\1\7\1\62\1\1\1\70\1\13\1\50\1\uffff\1\43\1\24\1\uffff\1\47\1\63\1\35\1\41\1\uffff\1\1\17\uffff\1\34\1\uffff\1\61\4\uffff\1\40\3\uffff\1\3\2\uffff\1\26\1\uffff\1\27\3\uffff\1\71\13\uffff\1\31\1\30\1\uffff\1\4\1\55\1\56",
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

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 1251:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA25_0 = input.LA(1);

                         
                        int index25_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA25_0==EOF||LA25_0==END_CASE||LA25_0==ELSE||LA25_0==NOT) ) {s = 1;}

                        else if ( (LA25_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA25_0==RULE_ID) ) {s = 4;}

                        else if ( (LA25_0==BOOL) ) {s = 5;}

                        else if ( (LA25_0==BYTE) ) {s = 6;}

                        else if ( (LA25_0==WORD) ) {s = 7;}

                        else if ( (LA25_0==DWORD) ) {s = 8;}

                        else if ( (LA25_0==LWORD) ) {s = 9;}

                        else if ( (LA25_0==SINT) ) {s = 10;}

                        else if ( (LA25_0==INT) ) {s = 11;}

                        else if ( (LA25_0==DINT) ) {s = 12;}

                        else if ( (LA25_0==LINT) ) {s = 13;}

                        else if ( (LA25_0==USINT) ) {s = 14;}

                        else if ( (LA25_0==UINT) ) {s = 15;}

                        else if ( (LA25_0==UDINT) ) {s = 16;}

                        else if ( (LA25_0==ULINT) ) {s = 17;}

                        else if ( (LA25_0==REAL) ) {s = 18;}

                        else if ( (LA25_0==LREAL) ) {s = 19;}

                        else if ( (LA25_0==TRUE) ) {s = 20;}

                        else if ( (LA25_0==FALSE) ) {s = 21;}

                        else if ( (LA25_0==PlusSign) ) {s = 22;}

                        else if ( (LA25_0==HyphenMinus) ) {s = 23;}

                        else if ( (LA25_0==RULE_INT) ) {s = 24;}

                        else if ( (LA25_0==RULE_NON_DECIMAL) ) {s = 25;}

                        else if ( (LA25_0==DATE) ) {s = 26;}

                        else if ( (LA25_0==LDATE) ) {s = 27;}

                        else if ( (LA25_0==D_2) ) {s = 28;}

                        else if ( (LA25_0==LD) ) {s = 29;}

                        else if ( (LA25_0==TIME) ) {s = 30;}

                        else if ( (LA25_0==LTIME) ) {s = 31;}

                        else if ( (LA25_0==T) ) {s = 32;}

                        else if ( (LA25_0==LT) ) {s = 33;}

                        else if ( (LA25_0==TIME_OF_DAY) ) {s = 34;}

                        else if ( (LA25_0==TOD) ) {s = 35;}

                        else if ( (LA25_0==LTOD) ) {s = 36;}

                        else if ( (LA25_0==DATE_AND_TIME) ) {s = 37;}

                        else if ( (LA25_0==LDATE_AND_TIME) ) {s = 38;}

                        else if ( (LA25_0==DT) ) {s = 39;}

                        else if ( (LA25_0==LDT) ) {s = 40;}

                        else if ( (LA25_0==STRING) ) {s = 41;}

                        else if ( (LA25_0==WSTRING) ) {s = 42;}

                        else if ( (LA25_0==CHAR) ) {s = 43;}

                        else if ( (LA25_0==WCHAR) ) {s = 44;}

                        else if ( (LA25_0==RULE_STRING) ) {s = 45;}

                        else if ( (LA25_0==RULE_WSTRING) ) {s = 46;}

                        else if ( (LA25_0==IF) && (synpred1_InternalSTAlgorithmParser())) {s = 49;}

                        else if ( (LA25_0==CASE) && (synpred1_InternalSTAlgorithmParser())) {s = 50;}

                        else if ( (LA25_0==FOR) && (synpred1_InternalSTAlgorithmParser())) {s = 51;}

                        else if ( (LA25_0==WHILE) && (synpred1_InternalSTAlgorithmParser())) {s = 52;}

                        else if ( (LA25_0==REPEAT) && (synpred1_InternalSTAlgorithmParser())) {s = 53;}

                        else if ( (LA25_0==RETURN) && (synpred1_InternalSTAlgorithmParser())) {s = 54;}

                        else if ( (LA25_0==CONTINUE) && (synpred1_InternalSTAlgorithmParser())) {s = 55;}

                        else if ( (LA25_0==EXIT) && (synpred1_InternalSTAlgorithmParser())) {s = 56;}

                        else if ( (LA25_0==Semicolon) && (synpred1_InternalSTAlgorithmParser())) {s = 57;}

                         
                        input.seek(index25_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA25_3 = input.LA(1);

                         
                        int index25_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA25_4 = input.LA(1);

                         
                        int index25_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA25_5 = input.LA(1);

                         
                        int index25_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA25_6 = input.LA(1);

                         
                        int index25_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA25_7 = input.LA(1);

                         
                        int index25_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA25_8 = input.LA(1);

                         
                        int index25_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA25_9 = input.LA(1);

                         
                        int index25_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA25_10 = input.LA(1);

                         
                        int index25_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA25_11 = input.LA(1);

                         
                        int index25_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA25_12 = input.LA(1);

                         
                        int index25_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA25_13 = input.LA(1);

                         
                        int index25_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA25_14 = input.LA(1);

                         
                        int index25_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA25_15 = input.LA(1);

                         
                        int index25_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA25_16 = input.LA(1);

                         
                        int index25_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA25_17 = input.LA(1);

                         
                        int index25_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA25_18 = input.LA(1);

                         
                        int index25_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA25_19 = input.LA(1);

                         
                        int index25_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA25_20 = input.LA(1);

                         
                        int index25_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA25_21 = input.LA(1);

                         
                        int index25_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA25_22 = input.LA(1);

                         
                        int index25_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA25_23 = input.LA(1);

                         
                        int index25_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA25_24 = input.LA(1);

                         
                        int index25_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA25_25 = input.LA(1);

                         
                        int index25_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA25_26 = input.LA(1);

                         
                        int index25_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA25_27 = input.LA(1);

                         
                        int index25_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA25_28 = input.LA(1);

                         
                        int index25_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA25_29 = input.LA(1);

                         
                        int index25_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA25_30 = input.LA(1);

                         
                        int index25_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA25_31 = input.LA(1);

                         
                        int index25_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA25_32 = input.LA(1);

                         
                        int index25_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA25_33 = input.LA(1);

                         
                        int index25_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA25_34 = input.LA(1);

                         
                        int index25_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA25_35 = input.LA(1);

                         
                        int index25_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA25_36 = input.LA(1);

                         
                        int index25_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA25_37 = input.LA(1);

                         
                        int index25_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA25_38 = input.LA(1);

                         
                        int index25_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA25_39 = input.LA(1);

                         
                        int index25_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA25_40 = input.LA(1);

                         
                        int index25_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA25_41 = input.LA(1);

                         
                        int index25_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA25_42 = input.LA(1);

                         
                        int index25_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA25_43 = input.LA(1);

                         
                        int index25_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA25_44 = input.LA(1);

                         
                        int index25_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA25_45 = input.LA(1);

                         
                        int index25_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA25_46 = input.LA(1);

                         
                        int index25_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTAlgorithmParser()) ) {s = 57;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index25_46);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 25, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\56\uffff";
    static final String dfa_9s = "\1\4\23\uffff\2\0\30\uffff";
    static final String dfa_10s = "\1\165\23\uffff\2\0\30\uffff";
    static final String dfa_11s = "\1\uffff\23\1\2\uffff\27\1\1\2";
    static final String dfa_12s = "\1\2\23\uffff\1\0\1\1\30\uffff}>";
    static final String[] dfa_13s = {
            "\1\44\1\43\1\uffff\1\40\11\uffff\1\50\2\uffff\1\47\1\6\1\uffff\1\31\1\21\1\35\1\7\2\uffff\1\16\1\17\1\14\1\52\1\uffff\1\3\1\4\1\51\1\30\1\12\1\uffff\1\23\1\13\1\42\1\20\1\10\1\34\1\15\2\uffff\1\5\3\uffff\1\11\1\46\1\uffff\1\41\1\22\1\uffff\1\45\1\uffff\1\33\1\37\1\uffff\1\55\17\uffff\1\32\6\uffff\1\36\3\uffff\1\1\2\uffff\1\24\1\uffff\1\25\17\uffff\1\27\1\26\1\uffff\1\2\1\53\1\54",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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

    class DFA40 extends DFA {

        public DFA40(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 40;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "2322:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA40_20 = input.LA(1);

                         
                        int index40_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 44;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index40_20);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA40_21 = input.LA(1);

                         
                        int index40_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTAlgorithmParser()) ) {s = 44;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index40_21);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA40_0 = input.LA(1);

                         
                        int index40_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA40_0==LeftParenthesis) && (synpred2_InternalSTAlgorithmParser())) {s = 1;}

                        else if ( (LA40_0==RULE_ID) && (synpred2_InternalSTAlgorithmParser())) {s = 2;}

                        else if ( (LA40_0==BOOL) && (synpred2_InternalSTAlgorithmParser())) {s = 3;}

                        else if ( (LA40_0==BYTE) && (synpred2_InternalSTAlgorithmParser())) {s = 4;}

                        else if ( (LA40_0==WORD) && (synpred2_InternalSTAlgorithmParser())) {s = 5;}

                        else if ( (LA40_0==DWORD) && (synpred2_InternalSTAlgorithmParser())) {s = 6;}

                        else if ( (LA40_0==LWORD) && (synpred2_InternalSTAlgorithmParser())) {s = 7;}

                        else if ( (LA40_0==SINT) && (synpred2_InternalSTAlgorithmParser())) {s = 8;}

                        else if ( (LA40_0==INT) && (synpred2_InternalSTAlgorithmParser())) {s = 9;}

                        else if ( (LA40_0==DINT) && (synpred2_InternalSTAlgorithmParser())) {s = 10;}

                        else if ( (LA40_0==LINT) && (synpred2_InternalSTAlgorithmParser())) {s = 11;}

                        else if ( (LA40_0==USINT) && (synpred2_InternalSTAlgorithmParser())) {s = 12;}

                        else if ( (LA40_0==UINT) && (synpred2_InternalSTAlgorithmParser())) {s = 13;}

                        else if ( (LA40_0==UDINT) && (synpred2_InternalSTAlgorithmParser())) {s = 14;}

                        else if ( (LA40_0==ULINT) && (synpred2_InternalSTAlgorithmParser())) {s = 15;}

                        else if ( (LA40_0==REAL) && (synpred2_InternalSTAlgorithmParser())) {s = 16;}

                        else if ( (LA40_0==LREAL) && (synpred2_InternalSTAlgorithmParser())) {s = 17;}

                        else if ( (LA40_0==TRUE) && (synpred2_InternalSTAlgorithmParser())) {s = 18;}

                        else if ( (LA40_0==FALSE) && (synpred2_InternalSTAlgorithmParser())) {s = 19;}

                        else if ( (LA40_0==PlusSign) ) {s = 20;}

                        else if ( (LA40_0==HyphenMinus) ) {s = 21;}

                        else if ( (LA40_0==RULE_INT) && (synpred2_InternalSTAlgorithmParser())) {s = 22;}

                        else if ( (LA40_0==RULE_NON_DECIMAL) && (synpred2_InternalSTAlgorithmParser())) {s = 23;}

                        else if ( (LA40_0==DATE) && (synpred2_InternalSTAlgorithmParser())) {s = 24;}

                        else if ( (LA40_0==LDATE) && (synpred2_InternalSTAlgorithmParser())) {s = 25;}

                        else if ( (LA40_0==D_2) && (synpred2_InternalSTAlgorithmParser())) {s = 26;}

                        else if ( (LA40_0==LD) && (synpred2_InternalSTAlgorithmParser())) {s = 27;}

                        else if ( (LA40_0==TIME) && (synpred2_InternalSTAlgorithmParser())) {s = 28;}

                        else if ( (LA40_0==LTIME) && (synpred2_InternalSTAlgorithmParser())) {s = 29;}

                        else if ( (LA40_0==T) && (synpred2_InternalSTAlgorithmParser())) {s = 30;}

                        else if ( (LA40_0==LT) && (synpred2_InternalSTAlgorithmParser())) {s = 31;}

                        else if ( (LA40_0==TIME_OF_DAY) && (synpred2_InternalSTAlgorithmParser())) {s = 32;}

                        else if ( (LA40_0==TOD) && (synpred2_InternalSTAlgorithmParser())) {s = 33;}

                        else if ( (LA40_0==LTOD) && (synpred2_InternalSTAlgorithmParser())) {s = 34;}

                        else if ( (LA40_0==DATE_AND_TIME) && (synpred2_InternalSTAlgorithmParser())) {s = 35;}

                        else if ( (LA40_0==LDATE_AND_TIME) && (synpred2_InternalSTAlgorithmParser())) {s = 36;}

                        else if ( (LA40_0==DT) && (synpred2_InternalSTAlgorithmParser())) {s = 37;}

                        else if ( (LA40_0==LDT) && (synpred2_InternalSTAlgorithmParser())) {s = 38;}

                        else if ( (LA40_0==STRING) && (synpred2_InternalSTAlgorithmParser())) {s = 39;}

                        else if ( (LA40_0==WSTRING) && (synpred2_InternalSTAlgorithmParser())) {s = 40;}

                        else if ( (LA40_0==CHAR) && (synpred2_InternalSTAlgorithmParser())) {s = 41;}

                        else if ( (LA40_0==WCHAR) && (synpred2_InternalSTAlgorithmParser())) {s = 42;}

                        else if ( (LA40_0==RULE_STRING) && (synpred2_InternalSTAlgorithmParser())) {s = 43;}

                        else if ( (LA40_0==RULE_WSTRING) && (synpred2_InternalSTAlgorithmParser())) {s = 44;}

                        else if ( (LA40_0==NOT) ) {s = 45;}

                         
                        input.seek(index40_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 40, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_14s = "\43\uffff";
    static final String dfa_15s = "\1\2\42\uffff";
    static final String dfa_16s = "\1\10\1\0\41\uffff";
    static final String dfa_17s = "\1\155\1\0\41\uffff";
    static final String dfa_18s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_19s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_20s = {
            "\1\2\56\uffff\1\2\2\uffff\1\2\4\uffff\1\2\2\uffff\1\2\5\uffff\6\2\1\uffff\1\2\1\uffff\1\2\3\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\14\2\4\uffff\2\2",
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

    class DFA47 extends DFA {

        public DFA47(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 47;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "2617:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_parameters_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTExpression ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA47_1 = input.LA(1);

                         
                        int index47_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSTAlgorithmParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index47_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 47, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x7B777F7DFFB340F0L,0x003B001148850000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x7B777F7DFFB340B2L,0x003B001148850000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x7B777F7DFFB240B2L,0x003B001148850000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000082000L,0x0008000000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000080000L,0x0008000000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000800004000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000200000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x6B627F7DE7B200B0L,0x003B000148810001L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000200080000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000101000000400L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000400L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x6B627F7DE7B200B0L,0x003B100148810001L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x7B7F7FFDFFF240B0L,0x003B001148850000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0008008000400000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x6B6A7F7DE7B280B0L,0x003B000148810001L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000000880000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000000000028000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x7B777F7DFFB640B0L,0x003B001148850000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x7B777F7DFFB248B0L,0x003B001148850000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x7B77FF7DFFB240B0L,0x003B001148850000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0400000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000004000001000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x000000A000002800L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000140000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x8000000000000002L,0x0000000420000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0000100200000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x000A0000000000F8L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x6B627F7DE7B200B0L,0x003B000158810001L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000090000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0200010000000000L,0x0003000140000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0002000140000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0006000000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x00000F0002180000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0002400140000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x0002000140000000L});

}