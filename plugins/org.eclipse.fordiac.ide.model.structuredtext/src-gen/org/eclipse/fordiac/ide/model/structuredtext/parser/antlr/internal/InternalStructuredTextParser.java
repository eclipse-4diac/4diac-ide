package org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.internal;

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
import org.eclipse.fordiac.ide.model.structuredtext.services.StructuredTextGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalStructuredTextParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "LTIME_OF_DAY", "TIME_OF_DAY", "END_REPEAT", "END_WHILE", "CONSTANT", "CONTINUE", "END_CASE", "END_FOR", "END_VAR", "WSTRING", "END_IF", "REPEAT", "RETURN", "STRING", "ARRAY", "DWORD", "ELSIF", "FALSE", "LDATE", "LREAL", "LTIME", "LWORD", "SUPER", "UDINT", "ULINT", "UNTIL", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "LINT", "LTOD", "REAL", "SINT", "THEN", "TIME", "TRUE", "UINT", "WORD", "B", "D", "W", "X", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "TOD", "VAR", "XOR", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "OF", "OR", "TO", "Ms", "Ns", "Us", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "E", "T", "LeftSquareBracket", "RightSquareBracket", "KW__", "D_1", "H", "M", "S", "RULE_LETTER", "RULE_DIGIT", "RULE_BIT", "RULE_OCTAL_DIGIT", "RULE_HEX_DIGIT", "RULE_ID", "RULE_BINARY_INT", "RULE_OCTAL_INT", "RULE_HEX_INT", "RULE_UNSIGNED_INT", "RULE_S_BYTE_CHAR_VALUE", "RULE_S_BYTE_CHAR_STR", "RULE_D_BYTE_CHAR_VALUE", "RULE_D_BYTE_CHAR_STR", "RULE_COMMON_CHAR_VALUE", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=27;
    public static final int LessThanSignGreaterThanSign=69;
    public static final int EqualsSignGreaterThanSign=70;
    public static final int VAR=63;
    public static final int END_IF=16;
    public static final int ULINT=30;
    public static final int END_CASE=12;
    public static final int LessThanSign=97;
    public static final int RULE_BIT=111;
    public static final int LeftParenthesis=87;
    public static final int BYTE=36;
    public static final int ELSE=41;
    public static final int IF=76;
    public static final int LINT=43;
    public static final int GreaterThanSign=99;
    public static final int WORD=51;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=114;
    public static final int TOD=62;
    public static final int RULE_DIGIT=110;
    public static final int DINT=40;
    public static final int UDINT=29;
    public static final int CASE=37;
    public static final int GreaterThanSignEqualsSign=71;
    public static final int AT=72;
    public static final int RULE_OCTAL_INT=116;
    public static final int PlusSign=90;
    public static final int END_FOR=13;
    public static final int RULE_ML_COMMENT=124;
    public static final int THEN=47;
    public static final int XOR=64;
    public static final int LeftSquareBracket=102;
    public static final int EXIT=42;
    public static final int TIME_OF_DAY=7;
    public static final int B=52;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=17;
    public static final int D=53;
    public static final int E=100;
    public static final int H=106;
    public static final int CHAR=38;
    public static final int RULE_D_BYTE_CHAR_STR=122;
    public static final int RULE_UNSIGNED_INT=118;
    public static final int M=107;
    public static final int LTIME=26;
    public static final int Comma=91;
    public static final int HyphenMinus=92;
    public static final int S=108;
    public static final int T=101;
    public static final int W=54;
    public static final int BY=73;
    public static final int X=55;
    public static final int ELSIF=22;
    public static final int END_REPEAT=8;
    public static final int LessThanSignEqualsSign=68;
    public static final int Solidus=94;
    public static final int RULE_OCTAL_DIGIT=112;
    public static final int RULE_HEX_INT=117;
    public static final int FullStop=93;
    public static final int CONSTANT=10;
    public static final int KW__=104;
    public static final int CONTINUE=11;
    public static final int Semicolon=96;
    public static final int RULE_LETTER=109;
    public static final int LD=77;
    public static final int STRING=19;
    public static final int RULE_HEX_DIGIT=113;
    public static final int TO=81;
    public static final int UINT=50;
    public static final int LTOD=44;
    public static final int ARRAY=20;
    public static final int LT=78;
    public static final int DO=74;
    public static final int WSTRING=15;
    public static final int DT=75;
    public static final int END_VAR=14;
    public static final int FullStopFullStop=66;
    public static final int Ampersand=86;
    public static final int RightSquareBracket=103;
    public static final int RULE_BINARY_INT=115;
    public static final int USINT=32;
    public static final int DWORD=21;
    public static final int FOR=57;
    public static final int RightParenthesis=88;
    public static final int TRUE=49;
    public static final int ColonEqualsSign=67;
    public static final int END_WHILE=9;
    public static final int DATE=39;
    public static final int NOT=61;
    public static final int LDATE=24;
    public static final int AND=56;
    public static final int NumberSign=85;
    public static final int REAL=45;
    public static final int AsteriskAsterisk=65;
    public static final int SINT=46;
    public static final int LTIME_OF_DAY=6;
    public static final int Us=84;
    public static final int LREAL=25;
    public static final int WCHAR=33;
    public static final int Ms=82;
    public static final int INT=58;
    public static final int RULE_SL_COMMENT=125;
    public static final int RETURN=18;
    public static final int EqualsSign=98;
    public static final int RULE_COMMON_CHAR_VALUE=123;
    public static final int OF=79;
    public static final int Colon=95;
    public static final int EOF=-1;
    public static final int LDT=59;
    public static final int Asterisk=89;
    public static final int SUPER=28;
    public static final int MOD=60;
    public static final int OR=80;
    public static final int RULE_S_BYTE_CHAR_VALUE=119;
    public static final int Ns=83;
    public static final int RULE_WS=126;
    public static final int TIME=48;
    public static final int RULE_ANY_OTHER=127;
    public static final int UNTIL=31;
    public static final int RULE_S_BYTE_CHAR_STR=120;
    public static final int BOOL=35;
    public static final int D_1=105;
    public static final int FALSE=23;
    public static final int WHILE=34;
    public static final int RULE_D_BYTE_CHAR_VALUE=121;

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

        public InternalStructuredTextParser(TokenStream input, StructuredTextGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "StructuredTextAlgorithm";
       	}

       	@Override
       	protected StructuredTextGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleStructuredTextAlgorithm"
    // InternalStructuredTextParser.g:58:1: entryRuleStructuredTextAlgorithm returns [EObject current=null] : iv_ruleStructuredTextAlgorithm= ruleStructuredTextAlgorithm EOF ;
    public final EObject entryRuleStructuredTextAlgorithm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStructuredTextAlgorithm = null;


        try {
            // InternalStructuredTextParser.g:58:64: (iv_ruleStructuredTextAlgorithm= ruleStructuredTextAlgorithm EOF )
            // InternalStructuredTextParser.g:59:2: iv_ruleStructuredTextAlgorithm= ruleStructuredTextAlgorithm EOF
            {
             newCompositeNode(grammarAccess.getStructuredTextAlgorithmRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStructuredTextAlgorithm=ruleStructuredTextAlgorithm();

            state._fsp--;

             current =iv_ruleStructuredTextAlgorithm; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStructuredTextAlgorithm"


    // $ANTLR start "ruleStructuredTextAlgorithm"
    // InternalStructuredTextParser.g:65:1: ruleStructuredTextAlgorithm returns [EObject current=null] : ( () (otherlv_1= VAR ( ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )* otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) ) ) ;
    public final EObject ruleStructuredTextAlgorithm() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_localVariables_2_0 = null;

        EObject lv_statements_5_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:71:2: ( ( () (otherlv_1= VAR ( ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )* otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) ) ) )
            // InternalStructuredTextParser.g:72:2: ( () (otherlv_1= VAR ( ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )* otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) ) )
            {
            // InternalStructuredTextParser.g:72:2: ( () (otherlv_1= VAR ( ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )* otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) ) )
            // InternalStructuredTextParser.g:73:3: () (otherlv_1= VAR ( ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )* otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) )
            {
            // InternalStructuredTextParser.g:73:3: ()
            // InternalStructuredTextParser.g:74:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getStructuredTextAlgorithmAccess().getStructuredTextAlgorithmAction_0(),
            					current);
            			

            }

            // InternalStructuredTextParser.g:80:3: (otherlv_1= VAR ( ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )* otherlv_4= END_VAR )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==VAR) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalStructuredTextParser.g:81:4: otherlv_1= VAR ( ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )* otherlv_4= END_VAR
                    {
                    otherlv_1=(Token)match(input,VAR,FOLLOW_3); 

                    				newLeafNode(otherlv_1, grammarAccess.getStructuredTextAlgorithmAccess().getVARKeyword_1_0());
                    			
                    // InternalStructuredTextParser.g:85:4: ( ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==CONSTANT||LA1_0==RULE_ID) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // InternalStructuredTextParser.g:86:5: ( (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon
                    	    {
                    	    // InternalStructuredTextParser.g:86:5: ( (lv_localVariables_2_0= ruleVar_Decl_Init ) )
                    	    // InternalStructuredTextParser.g:87:6: (lv_localVariables_2_0= ruleVar_Decl_Init )
                    	    {
                    	    // InternalStructuredTextParser.g:87:6: (lv_localVariables_2_0= ruleVar_Decl_Init )
                    	    // InternalStructuredTextParser.g:88:7: lv_localVariables_2_0= ruleVar_Decl_Init
                    	    {

                    	    							newCompositeNode(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesVar_Decl_InitParserRuleCall_1_1_0_0());
                    	    						
                    	    pushFollow(FOLLOW_4);
                    	    lv_localVariables_2_0=ruleVar_Decl_Init();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStructuredTextAlgorithmRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"localVariables",
                    	    								lv_localVariables_2_0,
                    	    								"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Var_Decl_Init");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }

                    	    otherlv_3=(Token)match(input,Semicolon,FOLLOW_3); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getStructuredTextAlgorithmAccess().getSemicolonKeyword_1_1_1());
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,END_VAR,FOLLOW_5); 

                    				newLeafNode(otherlv_4, grammarAccess.getStructuredTextAlgorithmAccess().getEND_VARKeyword_1_2());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:115:3: ( (lv_statements_5_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:116:4: (lv_statements_5_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:116:4: (lv_statements_5_0= ruleStmt_List )
            // InternalStructuredTextParser.g:117:5: lv_statements_5_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsStmt_ListParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_statements_5_0=ruleStmt_List();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getStructuredTextAlgorithmRule());
            					}
            					set(
            						current,
            						"statements",
            						lv_statements_5_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStructuredTextAlgorithm"


    // $ANTLR start "entryRuleVar_Decl_Init"
    // InternalStructuredTextParser.g:138:1: entryRuleVar_Decl_Init returns [EObject current=null] : iv_ruleVar_Decl_Init= ruleVar_Decl_Init EOF ;
    public final EObject entryRuleVar_Decl_Init() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVar_Decl_Init = null;


        try {
            // InternalStructuredTextParser.g:138:54: (iv_ruleVar_Decl_Init= ruleVar_Decl_Init EOF )
            // InternalStructuredTextParser.g:139:2: iv_ruleVar_Decl_Init= ruleVar_Decl_Init EOF
            {
             newCompositeNode(grammarAccess.getVar_Decl_InitRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVar_Decl_Init=ruleVar_Decl_Init();

            state._fsp--;

             current =iv_ruleVar_Decl_Init; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVar_Decl_Init"


    // $ANTLR start "ruleVar_Decl_Init"
    // InternalStructuredTextParser.g:145:1: ruleVar_Decl_Init returns [EObject current=null] : this_Var_Decl_Local_0= ruleVar_Decl_Local ;
    public final EObject ruleVar_Decl_Init() throws RecognitionException {
        EObject current = null;

        EObject this_Var_Decl_Local_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:151:2: (this_Var_Decl_Local_0= ruleVar_Decl_Local )
            // InternalStructuredTextParser.g:152:2: this_Var_Decl_Local_0= ruleVar_Decl_Local
            {

            		newCompositeNode(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocalParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_Var_Decl_Local_0=ruleVar_Decl_Local();

            state._fsp--;


            		current = this_Var_Decl_Local_0;
            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVar_Decl_Init"


    // $ANTLR start "entryRuleVar_Decl_Local"
    // InternalStructuredTextParser.g:163:1: entryRuleVar_Decl_Local returns [EObject current=null] : iv_ruleVar_Decl_Local= ruleVar_Decl_Local EOF ;
    public final EObject entryRuleVar_Decl_Local() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVar_Decl_Local = null;


        try {
            // InternalStructuredTextParser.g:163:55: (iv_ruleVar_Decl_Local= ruleVar_Decl_Local EOF )
            // InternalStructuredTextParser.g:164:2: iv_ruleVar_Decl_Local= ruleVar_Decl_Local EOF
            {
             newCompositeNode(grammarAccess.getVar_Decl_LocalRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVar_Decl_Local=ruleVar_Decl_Local();

            state._fsp--;

             current =iv_ruleVar_Decl_Local; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVar_Decl_Local"


    // $ANTLR start "ruleVar_Decl_Local"
    // InternalStructuredTextParser.g:170:1: ruleVar_Decl_Local returns [EObject current=null] : ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( (lv_located_3_0= AT ) ) ( (lv_location_4_0= ruleVariable ) ) )? otherlv_5= Colon ( ( (lv_array_6_0= ARRAY ) ) otherlv_7= LeftSquareBracket ( (lv_arrayStart_8_0= ruleArray_Size ) ) otherlv_9= FullStopFullStop ( (lv_arrayStop_10_0= ruleArray_Size ) ) otherlv_11= RightSquareBracket otherlv_12= OF )? ( ( ruleType_Name ) ) ( ( (lv_initalized_14_0= ColonEqualsSign ) ) ( (lv_initialValue_15_0= ruleConstant ) ) )? ) ;
    public final EObject ruleVar_Decl_Local() throws RecognitionException {
        EObject current = null;

        Token lv_constant_1_0=null;
        Token lv_name_2_0=null;
        Token lv_located_3_0=null;
        Token otherlv_5=null;
        Token lv_array_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_initalized_14_0=null;
        EObject lv_location_4_0 = null;

        AntlrDatatypeRuleToken lv_arrayStart_8_0 = null;

        AntlrDatatypeRuleToken lv_arrayStop_10_0 = null;

        EObject lv_initialValue_15_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:176:2: ( ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( (lv_located_3_0= AT ) ) ( (lv_location_4_0= ruleVariable ) ) )? otherlv_5= Colon ( ( (lv_array_6_0= ARRAY ) ) otherlv_7= LeftSquareBracket ( (lv_arrayStart_8_0= ruleArray_Size ) ) otherlv_9= FullStopFullStop ( (lv_arrayStop_10_0= ruleArray_Size ) ) otherlv_11= RightSquareBracket otherlv_12= OF )? ( ( ruleType_Name ) ) ( ( (lv_initalized_14_0= ColonEqualsSign ) ) ( (lv_initialValue_15_0= ruleConstant ) ) )? ) )
            // InternalStructuredTextParser.g:177:2: ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( (lv_located_3_0= AT ) ) ( (lv_location_4_0= ruleVariable ) ) )? otherlv_5= Colon ( ( (lv_array_6_0= ARRAY ) ) otherlv_7= LeftSquareBracket ( (lv_arrayStart_8_0= ruleArray_Size ) ) otherlv_9= FullStopFullStop ( (lv_arrayStop_10_0= ruleArray_Size ) ) otherlv_11= RightSquareBracket otherlv_12= OF )? ( ( ruleType_Name ) ) ( ( (lv_initalized_14_0= ColonEqualsSign ) ) ( (lv_initialValue_15_0= ruleConstant ) ) )? )
            {
            // InternalStructuredTextParser.g:177:2: ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( (lv_located_3_0= AT ) ) ( (lv_location_4_0= ruleVariable ) ) )? otherlv_5= Colon ( ( (lv_array_6_0= ARRAY ) ) otherlv_7= LeftSquareBracket ( (lv_arrayStart_8_0= ruleArray_Size ) ) otherlv_9= FullStopFullStop ( (lv_arrayStop_10_0= ruleArray_Size ) ) otherlv_11= RightSquareBracket otherlv_12= OF )? ( ( ruleType_Name ) ) ( ( (lv_initalized_14_0= ColonEqualsSign ) ) ( (lv_initialValue_15_0= ruleConstant ) ) )? )
            // InternalStructuredTextParser.g:178:3: () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( (lv_located_3_0= AT ) ) ( (lv_location_4_0= ruleVariable ) ) )? otherlv_5= Colon ( ( (lv_array_6_0= ARRAY ) ) otherlv_7= LeftSquareBracket ( (lv_arrayStart_8_0= ruleArray_Size ) ) otherlv_9= FullStopFullStop ( (lv_arrayStop_10_0= ruleArray_Size ) ) otherlv_11= RightSquareBracket otherlv_12= OF )? ( ( ruleType_Name ) ) ( ( (lv_initalized_14_0= ColonEqualsSign ) ) ( (lv_initialValue_15_0= ruleConstant ) ) )?
            {
            // InternalStructuredTextParser.g:178:3: ()
            // InternalStructuredTextParser.g:179:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getVar_Decl_LocalAccess().getLocalVariableAction_0(),
            					current);
            			

            }

            // InternalStructuredTextParser.g:185:3: ( (lv_constant_1_0= CONSTANT ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==CONSTANT) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalStructuredTextParser.g:186:4: (lv_constant_1_0= CONSTANT )
                    {
                    // InternalStructuredTextParser.g:186:4: (lv_constant_1_0= CONSTANT )
                    // InternalStructuredTextParser.g:187:5: lv_constant_1_0= CONSTANT
                    {
                    lv_constant_1_0=(Token)match(input,CONSTANT,FOLLOW_6); 

                    					newLeafNode(lv_constant_1_0, grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
                    					}
                    					setWithLastConsumed(current, "constant", true, "CONSTANT");
                    				

                    }


                    }
                    break;

            }

            // InternalStructuredTextParser.g:199:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalStructuredTextParser.g:200:4: (lv_name_2_0= RULE_ID )
            {
            // InternalStructuredTextParser.g:200:4: (lv_name_2_0= RULE_ID )
            // InternalStructuredTextParser.g:201:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_2_0, grammarAccess.getVar_Decl_LocalAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");
            				

            }


            }

            // InternalStructuredTextParser.g:217:3: ( ( (lv_located_3_0= AT ) ) ( (lv_location_4_0= ruleVariable ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==AT) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalStructuredTextParser.g:218:4: ( (lv_located_3_0= AT ) ) ( (lv_location_4_0= ruleVariable ) )
                    {
                    // InternalStructuredTextParser.g:218:4: ( (lv_located_3_0= AT ) )
                    // InternalStructuredTextParser.g:219:5: (lv_located_3_0= AT )
                    {
                    // InternalStructuredTextParser.g:219:5: (lv_located_3_0= AT )
                    // InternalStructuredTextParser.g:220:6: lv_located_3_0= AT
                    {
                    lv_located_3_0=(Token)match(input,AT,FOLLOW_8); 

                    						newLeafNode(lv_located_3_0, grammarAccess.getVar_Decl_LocalAccess().getLocatedATKeyword_3_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
                    						}
                    						setWithLastConsumed(current, "located", true, "AT");
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:232:4: ( (lv_location_4_0= ruleVariable ) )
                    // InternalStructuredTextParser.g:233:5: (lv_location_4_0= ruleVariable )
                    {
                    // InternalStructuredTextParser.g:233:5: (lv_location_4_0= ruleVariable )
                    // InternalStructuredTextParser.g:234:6: lv_location_4_0= ruleVariable
                    {

                    						newCompositeNode(grammarAccess.getVar_Decl_LocalAccess().getLocationVariableParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_9);
                    lv_location_4_0=ruleVariable();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVar_Decl_LocalRule());
                    						}
                    						set(
                    							current,
                    							"location",
                    							lv_location_4_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Variable");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,Colon,FOLLOW_10); 

            			newLeafNode(otherlv_5, grammarAccess.getVar_Decl_LocalAccess().getColonKeyword_4());
            		
            // InternalStructuredTextParser.g:256:3: ( ( (lv_array_6_0= ARRAY ) ) otherlv_7= LeftSquareBracket ( (lv_arrayStart_8_0= ruleArray_Size ) ) otherlv_9= FullStopFullStop ( (lv_arrayStop_10_0= ruleArray_Size ) ) otherlv_11= RightSquareBracket otherlv_12= OF )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ARRAY) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalStructuredTextParser.g:257:4: ( (lv_array_6_0= ARRAY ) ) otherlv_7= LeftSquareBracket ( (lv_arrayStart_8_0= ruleArray_Size ) ) otherlv_9= FullStopFullStop ( (lv_arrayStop_10_0= ruleArray_Size ) ) otherlv_11= RightSquareBracket otherlv_12= OF
                    {
                    // InternalStructuredTextParser.g:257:4: ( (lv_array_6_0= ARRAY ) )
                    // InternalStructuredTextParser.g:258:5: (lv_array_6_0= ARRAY )
                    {
                    // InternalStructuredTextParser.g:258:5: (lv_array_6_0= ARRAY )
                    // InternalStructuredTextParser.g:259:6: lv_array_6_0= ARRAY
                    {
                    lv_array_6_0=(Token)match(input,ARRAY,FOLLOW_11); 

                    						newLeafNode(lv_array_6_0, grammarAccess.getVar_Decl_LocalAccess().getArrayARRAYKeyword_5_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
                    						}
                    						setWithLastConsumed(current, "array", true, "ARRAY");
                    					

                    }


                    }

                    otherlv_7=(Token)match(input,LeftSquareBracket,FOLLOW_12); 

                    				newLeafNode(otherlv_7, grammarAccess.getVar_Decl_LocalAccess().getLeftSquareBracketKeyword_5_1());
                    			
                    // InternalStructuredTextParser.g:275:4: ( (lv_arrayStart_8_0= ruleArray_Size ) )
                    // InternalStructuredTextParser.g:276:5: (lv_arrayStart_8_0= ruleArray_Size )
                    {
                    // InternalStructuredTextParser.g:276:5: (lv_arrayStart_8_0= ruleArray_Size )
                    // InternalStructuredTextParser.g:277:6: lv_arrayStart_8_0= ruleArray_Size
                    {

                    						newCompositeNode(grammarAccess.getVar_Decl_LocalAccess().getArrayStartArray_SizeParserRuleCall_5_2_0());
                    					
                    pushFollow(FOLLOW_13);
                    lv_arrayStart_8_0=ruleArray_Size();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVar_Decl_LocalRule());
                    						}
                    						set(
                    							current,
                    							"arrayStart",
                    							lv_arrayStart_8_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Array_Size");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_9=(Token)match(input,FullStopFullStop,FOLLOW_12); 

                    				newLeafNode(otherlv_9, grammarAccess.getVar_Decl_LocalAccess().getFullStopFullStopKeyword_5_3());
                    			
                    // InternalStructuredTextParser.g:298:4: ( (lv_arrayStop_10_0= ruleArray_Size ) )
                    // InternalStructuredTextParser.g:299:5: (lv_arrayStop_10_0= ruleArray_Size )
                    {
                    // InternalStructuredTextParser.g:299:5: (lv_arrayStop_10_0= ruleArray_Size )
                    // InternalStructuredTextParser.g:300:6: lv_arrayStop_10_0= ruleArray_Size
                    {

                    						newCompositeNode(grammarAccess.getVar_Decl_LocalAccess().getArrayStopArray_SizeParserRuleCall_5_4_0());
                    					
                    pushFollow(FOLLOW_14);
                    lv_arrayStop_10_0=ruleArray_Size();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVar_Decl_LocalRule());
                    						}
                    						set(
                    							current,
                    							"arrayStop",
                    							lv_arrayStop_10_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Array_Size");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_11=(Token)match(input,RightSquareBracket,FOLLOW_15); 

                    				newLeafNode(otherlv_11, grammarAccess.getVar_Decl_LocalAccess().getRightSquareBracketKeyword_5_5());
                    			
                    otherlv_12=(Token)match(input,OF,FOLLOW_10); 

                    				newLeafNode(otherlv_12, grammarAccess.getVar_Decl_LocalAccess().getOFKeyword_5_6());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:326:3: ( ( ruleType_Name ) )
            // InternalStructuredTextParser.g:327:4: ( ruleType_Name )
            {
            // InternalStructuredTextParser.g:327:4: ( ruleType_Name )
            // InternalStructuredTextParser.g:328:5: ruleType_Name
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
            					}
            				

            					newCompositeNode(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeCrossReference_6_0());
            				
            pushFollow(FOLLOW_16);
            ruleType_Name();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalStructuredTextParser.g:342:3: ( ( (lv_initalized_14_0= ColonEqualsSign ) ) ( (lv_initialValue_15_0= ruleConstant ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ColonEqualsSign) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalStructuredTextParser.g:343:4: ( (lv_initalized_14_0= ColonEqualsSign ) ) ( (lv_initialValue_15_0= ruleConstant ) )
                    {
                    // InternalStructuredTextParser.g:343:4: ( (lv_initalized_14_0= ColonEqualsSign ) )
                    // InternalStructuredTextParser.g:344:5: (lv_initalized_14_0= ColonEqualsSign )
                    {
                    // InternalStructuredTextParser.g:344:5: (lv_initalized_14_0= ColonEqualsSign )
                    // InternalStructuredTextParser.g:345:6: lv_initalized_14_0= ColonEqualsSign
                    {
                    lv_initalized_14_0=(Token)match(input,ColonEqualsSign,FOLLOW_17); 

                    						newLeafNode(lv_initalized_14_0, grammarAccess.getVar_Decl_LocalAccess().getInitalizedColonEqualsSignKeyword_7_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
                    						}
                    						setWithLastConsumed(current, "initalized", true, ":=");
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:357:4: ( (lv_initialValue_15_0= ruleConstant ) )
                    // InternalStructuredTextParser.g:358:5: (lv_initialValue_15_0= ruleConstant )
                    {
                    // InternalStructuredTextParser.g:358:5: (lv_initialValue_15_0= ruleConstant )
                    // InternalStructuredTextParser.g:359:6: lv_initialValue_15_0= ruleConstant
                    {

                    						newCompositeNode(grammarAccess.getVar_Decl_LocalAccess().getInitialValueConstantParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_initialValue_15_0=ruleConstant();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVar_Decl_LocalRule());
                    						}
                    						set(
                    							current,
                    							"initialValue",
                    							lv_initialValue_15_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Constant");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVar_Decl_Local"


    // $ANTLR start "entryRuleStmt_List"
    // InternalStructuredTextParser.g:381:1: entryRuleStmt_List returns [EObject current=null] : iv_ruleStmt_List= ruleStmt_List EOF ;
    public final EObject entryRuleStmt_List() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStmt_List = null;


        try {
            // InternalStructuredTextParser.g:381:50: (iv_ruleStmt_List= ruleStmt_List EOF )
            // InternalStructuredTextParser.g:382:2: iv_ruleStmt_List= ruleStmt_List EOF
            {
             newCompositeNode(grammarAccess.getStmt_ListRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStmt_List=ruleStmt_List();

            state._fsp--;

             current =iv_ruleStmt_List; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStmt_List"


    // $ANTLR start "ruleStmt_List"
    // InternalStructuredTextParser.g:388:1: ruleStmt_List returns [EObject current=null] : ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )* ) ;
    public final EObject ruleStmt_List() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_statements_1_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:394:2: ( ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )* ) )
            // InternalStructuredTextParser.g:395:2: ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )* )
            {
            // InternalStructuredTextParser.g:395:2: ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )* )
            // InternalStructuredTextParser.g:396:3: () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )*
            {
            // InternalStructuredTextParser.g:396:3: ()
            // InternalStructuredTextParser.g:397:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getStmt_ListAccess().getStatementListAction_0(),
            					current);
            			

            }

            // InternalStructuredTextParser.g:403:3: ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )*
            loop8:
            do {
                int alt8=2;
                switch ( input.LA(1) ) {
                case TIME:
                    {
                    int LA8_2 = input.LA(2);

                    if ( (LA8_2==LeftParenthesis) ) {
                        alt8=1;
                    }


                    }
                    break;
                case T:
                    {
                    int LA8_3 = input.LA(2);

                    if ( ((LA8_3>=B && LA8_3<=X)||LA8_3==ColonEqualsSign||LA8_3==FullStop||LA8_3==LeftSquareBracket) ) {
                        alt8=1;
                    }


                    }
                    break;
                case LT:
                    {
                    int LA8_4 = input.LA(2);

                    if ( ((LA8_4>=B && LA8_4<=X)||LA8_4==ColonEqualsSign||LA8_4==FullStop||LA8_4==LeftSquareBracket) ) {
                        alt8=1;
                    }


                    }
                    break;
                case DT:
                    {
                    int LA8_5 = input.LA(2);

                    if ( ((LA8_5>=B && LA8_5<=X)||LA8_5==ColonEqualsSign||LA8_5==FullStop||LA8_5==LeftSquareBracket) ) {
                        alt8=1;
                    }


                    }
                    break;
                case CONTINUE:
                case REPEAT:
                case RETURN:
                case SUPER:
                case WHILE:
                case CASE:
                case EXIT:
                case FOR:
                case IF:
                case Semicolon:
                case RULE_ID:
                    {
                    alt8=1;
                    }
                    break;

                }

                switch (alt8) {
            	case 1 :
            	    // InternalStructuredTextParser.g:404:4: ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon
            	    {
            	    // InternalStructuredTextParser.g:404:4: ( (lv_statements_1_0= ruleStmt ) )?
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0==CONTINUE||(LA7_0>=REPEAT && LA7_0<=RETURN)||LA7_0==SUPER||LA7_0==WHILE||LA7_0==CASE||LA7_0==EXIT||LA7_0==TIME||LA7_0==FOR||(LA7_0>=DT && LA7_0<=IF)||LA7_0==LT||LA7_0==T||LA7_0==RULE_ID) ) {
            	        alt7=1;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // InternalStructuredTextParser.g:405:5: (lv_statements_1_0= ruleStmt )
            	            {
            	            // InternalStructuredTextParser.g:405:5: (lv_statements_1_0= ruleStmt )
            	            // InternalStructuredTextParser.g:406:6: lv_statements_1_0= ruleStmt
            	            {

            	            						newCompositeNode(grammarAccess.getStmt_ListAccess().getStatementsStmtParserRuleCall_1_0_0());
            	            					
            	            pushFollow(FOLLOW_4);
            	            lv_statements_1_0=ruleStmt();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getStmt_ListRule());
            	            						}
            	            						add(
            	            							current,
            	            							"statements",
            	            							lv_statements_1_0,
            	            							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }


            	            }
            	            break;

            	    }

            	    otherlv_2=(Token)match(input,Semicolon,FOLLOW_18); 

            	    				newLeafNode(otherlv_2, grammarAccess.getStmt_ListAccess().getSemicolonKeyword_1_1());
            	    			

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStmt_List"


    // $ANTLR start "entryRuleStmt"
    // InternalStructuredTextParser.g:432:1: entryRuleStmt returns [EObject current=null] : iv_ruleStmt= ruleStmt EOF ;
    public final EObject entryRuleStmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStmt = null;


        try {
            // InternalStructuredTextParser.g:432:45: (iv_ruleStmt= ruleStmt EOF )
            // InternalStructuredTextParser.g:433:2: iv_ruleStmt= ruleStmt EOF
            {
             newCompositeNode(grammarAccess.getStmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStmt=ruleStmt();

            state._fsp--;

             current =iv_ruleStmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStmt"


    // $ANTLR start "ruleStmt"
    // InternalStructuredTextParser.g:439:1: ruleStmt returns [EObject current=null] : (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt ) ;
    public final EObject ruleStmt() throws RecognitionException {
        EObject current = null;

        EObject this_Assign_Stmt_0 = null;

        EObject this_Subprog_Ctrl_Stmt_1 = null;

        EObject this_Selection_Stmt_2 = null;

        EObject this_Iteration_Stmt_3 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:445:2: ( (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt ) )
            // InternalStructuredTextParser.g:446:2: (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt )
            {
            // InternalStructuredTextParser.g:446:2: (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt )
            int alt9=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA9_1 = input.LA(2);

                if ( ((LA9_1>=B && LA9_1<=X)||LA9_1==ColonEqualsSign||LA9_1==FullStop||LA9_1==LeftSquareBracket) ) {
                    alt9=1;
                }
                else if ( (LA9_1==LeftParenthesis) ) {
                    alt9=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
                }
                break;
            case DT:
            case LT:
            case T:
                {
                alt9=1;
                }
                break;
            case RETURN:
            case SUPER:
            case TIME:
                {
                alt9=2;
                }
                break;
            case CASE:
            case IF:
                {
                alt9=3;
                }
                break;
            case CONTINUE:
            case REPEAT:
            case WHILE:
            case EXIT:
            case FOR:
                {
                alt9=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalStructuredTextParser.g:447:3: this_Assign_Stmt_0= ruleAssign_Stmt
                    {

                    			newCompositeNode(grammarAccess.getStmtAccess().getAssign_StmtParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Assign_Stmt_0=ruleAssign_Stmt();

                    state._fsp--;


                    			current = this_Assign_Stmt_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:456:3: this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt
                    {

                    			newCompositeNode(grammarAccess.getStmtAccess().getSubprog_Ctrl_StmtParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Subprog_Ctrl_Stmt_1=ruleSubprog_Ctrl_Stmt();

                    state._fsp--;


                    			current = this_Subprog_Ctrl_Stmt_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:465:3: this_Selection_Stmt_2= ruleSelection_Stmt
                    {

                    			newCompositeNode(grammarAccess.getStmtAccess().getSelection_StmtParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Selection_Stmt_2=ruleSelection_Stmt();

                    state._fsp--;


                    			current = this_Selection_Stmt_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:474:3: this_Iteration_Stmt_3= ruleIteration_Stmt
                    {

                    			newCompositeNode(grammarAccess.getStmtAccess().getIteration_StmtParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Iteration_Stmt_3=ruleIteration_Stmt();

                    state._fsp--;


                    			current = this_Iteration_Stmt_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStmt"


    // $ANTLR start "entryRuleAssign_Stmt"
    // InternalStructuredTextParser.g:486:1: entryRuleAssign_Stmt returns [EObject current=null] : iv_ruleAssign_Stmt= ruleAssign_Stmt EOF ;
    public final EObject entryRuleAssign_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssign_Stmt = null;


        try {
            // InternalStructuredTextParser.g:486:52: (iv_ruleAssign_Stmt= ruleAssign_Stmt EOF )
            // InternalStructuredTextParser.g:487:2: iv_ruleAssign_Stmt= ruleAssign_Stmt EOF
            {
             newCompositeNode(grammarAccess.getAssign_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAssign_Stmt=ruleAssign_Stmt();

            state._fsp--;

             current =iv_ruleAssign_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAssign_Stmt"


    // $ANTLR start "ruleAssign_Stmt"
    // InternalStructuredTextParser.g:493:1: ruleAssign_Stmt returns [EObject current=null] : ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) ) ;
    public final EObject ruleAssign_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_variable_0_0 = null;

        EObject lv_expression_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:499:2: ( ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) ) )
            // InternalStructuredTextParser.g:500:2: ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) )
            {
            // InternalStructuredTextParser.g:500:2: ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) )
            // InternalStructuredTextParser.g:501:3: ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) )
            {
            // InternalStructuredTextParser.g:501:3: ( (lv_variable_0_0= ruleVariable ) )
            // InternalStructuredTextParser.g:502:4: (lv_variable_0_0= ruleVariable )
            {
            // InternalStructuredTextParser.g:502:4: (lv_variable_0_0= ruleVariable )
            // InternalStructuredTextParser.g:503:5: lv_variable_0_0= ruleVariable
            {

            					newCompositeNode(grammarAccess.getAssign_StmtAccess().getVariableVariableParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_19);
            lv_variable_0_0=ruleVariable();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAssign_StmtRule());
            					}
            					set(
            						current,
            						"variable",
            						lv_variable_0_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Variable");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_20); 

            			newLeafNode(otherlv_1, grammarAccess.getAssign_StmtAccess().getColonEqualsSignKeyword_1());
            		
            // InternalStructuredTextParser.g:524:3: ( (lv_expression_2_0= ruleExpression ) )
            // InternalStructuredTextParser.g:525:4: (lv_expression_2_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:525:4: (lv_expression_2_0= ruleExpression )
            // InternalStructuredTextParser.g:526:5: lv_expression_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getAssign_StmtAccess().getExpressionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_expression_2_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAssign_StmtRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAssign_Stmt"


    // $ANTLR start "entryRuleSubprog_Ctrl_Stmt"
    // InternalStructuredTextParser.g:547:1: entryRuleSubprog_Ctrl_Stmt returns [EObject current=null] : iv_ruleSubprog_Ctrl_Stmt= ruleSubprog_Ctrl_Stmt EOF ;
    public final EObject entryRuleSubprog_Ctrl_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSubprog_Ctrl_Stmt = null;


        try {
            // InternalStructuredTextParser.g:547:58: (iv_ruleSubprog_Ctrl_Stmt= ruleSubprog_Ctrl_Stmt EOF )
            // InternalStructuredTextParser.g:548:2: iv_ruleSubprog_Ctrl_Stmt= ruleSubprog_Ctrl_Stmt EOF
            {
             newCompositeNode(grammarAccess.getSubprog_Ctrl_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSubprog_Ctrl_Stmt=ruleSubprog_Ctrl_Stmt();

            state._fsp--;

             current =iv_ruleSubprog_Ctrl_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSubprog_Ctrl_Stmt"


    // $ANTLR start "ruleSubprog_Ctrl_Stmt"
    // InternalStructuredTextParser.g:554:1: ruleSubprog_Ctrl_Stmt returns [EObject current=null] : (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6= RETURN ) ) ;
    public final EObject ruleSubprog_Ctrl_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject this_Func_Call_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:560:2: ( (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6= RETURN ) ) )
            // InternalStructuredTextParser.g:561:2: (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6= RETURN ) )
            {
            // InternalStructuredTextParser.g:561:2: (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6= RETURN ) )
            int alt10=3;
            switch ( input.LA(1) ) {
            case TIME:
            case RULE_ID:
                {
                alt10=1;
                }
                break;
            case SUPER:
                {
                alt10=2;
                }
                break;
            case RETURN:
                {
                alt10=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalStructuredTextParser.g:562:3: this_Func_Call_0= ruleFunc_Call
                    {

                    			newCompositeNode(grammarAccess.getSubprog_Ctrl_StmtAccess().getFunc_CallParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Func_Call_0=ruleFunc_Call();

                    state._fsp--;


                    			current = this_Func_Call_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:571:3: ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis )
                    {
                    // InternalStructuredTextParser.g:571:3: ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis )
                    // InternalStructuredTextParser.g:572:4: () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis
                    {
                    // InternalStructuredTextParser.g:572:4: ()
                    // InternalStructuredTextParser.g:573:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,SUPER,FOLLOW_21); 

                    				newLeafNode(otherlv_2, grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_1_1());
                    			
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_22); 

                    				newLeafNode(otherlv_3, grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_1_2());
                    			
                    otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); 

                    				newLeafNode(otherlv_4, grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_1_3());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:593:3: ( () otherlv_6= RETURN )
                    {
                    // InternalStructuredTextParser.g:593:3: ( () otherlv_6= RETURN )
                    // InternalStructuredTextParser.g:594:4: () otherlv_6= RETURN
                    {
                    // InternalStructuredTextParser.g:594:4: ()
                    // InternalStructuredTextParser.g:595:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_2_0(),
                    						current);
                    				

                    }

                    otherlv_6=(Token)match(input,RETURN,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getSubprog_Ctrl_StmtAccess().getRETURNKeyword_2_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSubprog_Ctrl_Stmt"


    // $ANTLR start "entryRuleSelection_Stmt"
    // InternalStructuredTextParser.g:610:1: entryRuleSelection_Stmt returns [EObject current=null] : iv_ruleSelection_Stmt= ruleSelection_Stmt EOF ;
    public final EObject entryRuleSelection_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSelection_Stmt = null;


        try {
            // InternalStructuredTextParser.g:610:55: (iv_ruleSelection_Stmt= ruleSelection_Stmt EOF )
            // InternalStructuredTextParser.g:611:2: iv_ruleSelection_Stmt= ruleSelection_Stmt EOF
            {
             newCompositeNode(grammarAccess.getSelection_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSelection_Stmt=ruleSelection_Stmt();

            state._fsp--;

             current =iv_ruleSelection_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSelection_Stmt"


    // $ANTLR start "ruleSelection_Stmt"
    // InternalStructuredTextParser.g:617:1: ruleSelection_Stmt returns [EObject current=null] : (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1= ruleCase_Stmt ) ;
    public final EObject ruleSelection_Stmt() throws RecognitionException {
        EObject current = null;

        EObject this_IF_Stmt_0 = null;

        EObject this_Case_Stmt_1 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:623:2: ( (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1= ruleCase_Stmt ) )
            // InternalStructuredTextParser.g:624:2: (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1= ruleCase_Stmt )
            {
            // InternalStructuredTextParser.g:624:2: (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1= ruleCase_Stmt )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IF) ) {
                alt11=1;
            }
            else if ( (LA11_0==CASE) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalStructuredTextParser.g:625:3: this_IF_Stmt_0= ruleIF_Stmt
                    {

                    			newCompositeNode(grammarAccess.getSelection_StmtAccess().getIF_StmtParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_IF_Stmt_0=ruleIF_Stmt();

                    state._fsp--;


                    			current = this_IF_Stmt_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:634:3: this_Case_Stmt_1= ruleCase_Stmt
                    {

                    			newCompositeNode(grammarAccess.getSelection_StmtAccess().getCase_StmtParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Case_Stmt_1=ruleCase_Stmt();

                    state._fsp--;


                    			current = this_Case_Stmt_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSelection_Stmt"


    // $ANTLR start "entryRuleIF_Stmt"
    // InternalStructuredTextParser.g:646:1: entryRuleIF_Stmt returns [EObject current=null] : iv_ruleIF_Stmt= ruleIF_Stmt EOF ;
    public final EObject entryRuleIF_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIF_Stmt = null;


        try {
            // InternalStructuredTextParser.g:646:48: (iv_ruleIF_Stmt= ruleIF_Stmt EOF )
            // InternalStructuredTextParser.g:647:2: iv_ruleIF_Stmt= ruleIF_Stmt EOF
            {
             newCompositeNode(grammarAccess.getIF_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIF_Stmt=ruleIF_Stmt();

            state._fsp--;

             current =iv_ruleIF_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIF_Stmt"


    // $ANTLR start "ruleIF_Stmt"
    // InternalStructuredTextParser.g:653:1: ruleIF_Stmt returns [EObject current=null] : (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF ) ;
    public final EObject ruleIF_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_6=null;
        EObject lv_expression_1_0 = null;

        EObject lv_statments_3_0 = null;

        EObject lv_elseif_4_0 = null;

        EObject lv_else_5_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:659:2: ( (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF ) )
            // InternalStructuredTextParser.g:660:2: (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF )
            {
            // InternalStructuredTextParser.g:660:2: (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF )
            // InternalStructuredTextParser.g:661:3: otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_20); 

            			newLeafNode(otherlv_0, grammarAccess.getIF_StmtAccess().getIFKeyword_0());
            		
            // InternalStructuredTextParser.g:665:3: ( (lv_expression_1_0= ruleExpression ) )
            // InternalStructuredTextParser.g:666:4: (lv_expression_1_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:666:4: (lv_expression_1_0= ruleExpression )
            // InternalStructuredTextParser.g:667:5: lv_expression_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getIF_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_23);
            lv_expression_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIF_StmtRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,THEN,FOLLOW_24); 

            			newLeafNode(otherlv_2, grammarAccess.getIF_StmtAccess().getTHENKeyword_2());
            		
            // InternalStructuredTextParser.g:688:3: ( (lv_statments_3_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:689:4: (lv_statments_3_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:689:4: (lv_statments_3_0= ruleStmt_List )
            // InternalStructuredTextParser.g:690:5: lv_statments_3_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getIF_StmtAccess().getStatmentsStmt_ListParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_25);
            lv_statments_3_0=ruleStmt_List();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIF_StmtRule());
            					}
            					set(
            						current,
            						"statments",
            						lv_statments_3_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalStructuredTextParser.g:707:3: ( (lv_elseif_4_0= ruleELSIF_Clause ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==ELSIF) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalStructuredTextParser.g:708:4: (lv_elseif_4_0= ruleELSIF_Clause )
            	    {
            	    // InternalStructuredTextParser.g:708:4: (lv_elseif_4_0= ruleELSIF_Clause )
            	    // InternalStructuredTextParser.g:709:5: lv_elseif_4_0= ruleELSIF_Clause
            	    {

            	    					newCompositeNode(grammarAccess.getIF_StmtAccess().getElseifELSIF_ClauseParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_25);
            	    lv_elseif_4_0=ruleELSIF_Clause();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getIF_StmtRule());
            	    					}
            	    					add(
            	    						current,
            	    						"elseif",
            	    						lv_elseif_4_0,
            	    						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ELSIF_Clause");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            // InternalStructuredTextParser.g:726:3: ( (lv_else_5_0= ruleELSE_Clause ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==ELSE) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalStructuredTextParser.g:727:4: (lv_else_5_0= ruleELSE_Clause )
                    {
                    // InternalStructuredTextParser.g:727:4: (lv_else_5_0= ruleELSE_Clause )
                    // InternalStructuredTextParser.g:728:5: lv_else_5_0= ruleELSE_Clause
                    {

                    					newCompositeNode(grammarAccess.getIF_StmtAccess().getElseELSE_ClauseParserRuleCall_5_0());
                    				
                    pushFollow(FOLLOW_26);
                    lv_else_5_0=ruleELSE_Clause();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getIF_StmtRule());
                    					}
                    					set(
                    						current,
                    						"else",
                    						lv_else_5_0,
                    						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ELSE_Clause");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,END_IF,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getIF_StmtAccess().getEND_IFKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIF_Stmt"


    // $ANTLR start "entryRuleELSIF_Clause"
    // InternalStructuredTextParser.g:753:1: entryRuleELSIF_Clause returns [EObject current=null] : iv_ruleELSIF_Clause= ruleELSIF_Clause EOF ;
    public final EObject entryRuleELSIF_Clause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleELSIF_Clause = null;


        try {
            // InternalStructuredTextParser.g:753:53: (iv_ruleELSIF_Clause= ruleELSIF_Clause EOF )
            // InternalStructuredTextParser.g:754:2: iv_ruleELSIF_Clause= ruleELSIF_Clause EOF
            {
             newCompositeNode(grammarAccess.getELSIF_ClauseRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleELSIF_Clause=ruleELSIF_Clause();

            state._fsp--;

             current =iv_ruleELSIF_Clause; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleELSIF_Clause"


    // $ANTLR start "ruleELSIF_Clause"
    // InternalStructuredTextParser.g:760:1: ruleELSIF_Clause returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) ) ;
    public final EObject ruleELSIF_Clause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_expression_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:766:2: ( (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) ) )
            // InternalStructuredTextParser.g:767:2: (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) )
            {
            // InternalStructuredTextParser.g:767:2: (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) )
            // InternalStructuredTextParser.g:768:3: otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) )
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_20); 

            			newLeafNode(otherlv_0, grammarAccess.getELSIF_ClauseAccess().getELSIFKeyword_0());
            		
            // InternalStructuredTextParser.g:772:3: ( (lv_expression_1_0= ruleExpression ) )
            // InternalStructuredTextParser.g:773:4: (lv_expression_1_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:773:4: (lv_expression_1_0= ruleExpression )
            // InternalStructuredTextParser.g:774:5: lv_expression_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getELSIF_ClauseAccess().getExpressionExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_23);
            lv_expression_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getELSIF_ClauseRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,THEN,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getELSIF_ClauseAccess().getTHENKeyword_2());
            		
            // InternalStructuredTextParser.g:795:3: ( (lv_statements_3_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:796:4: (lv_statements_3_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:796:4: (lv_statements_3_0= ruleStmt_List )
            // InternalStructuredTextParser.g:797:5: lv_statements_3_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getELSIF_ClauseAccess().getStatementsStmt_ListParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_statements_3_0=ruleStmt_List();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getELSIF_ClauseRule());
            					}
            					set(
            						current,
            						"statements",
            						lv_statements_3_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleELSIF_Clause"


    // $ANTLR start "entryRuleELSE_Clause"
    // InternalStructuredTextParser.g:818:1: entryRuleELSE_Clause returns [EObject current=null] : iv_ruleELSE_Clause= ruleELSE_Clause EOF ;
    public final EObject entryRuleELSE_Clause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleELSE_Clause = null;


        try {
            // InternalStructuredTextParser.g:818:52: (iv_ruleELSE_Clause= ruleELSE_Clause EOF )
            // InternalStructuredTextParser.g:819:2: iv_ruleELSE_Clause= ruleELSE_Clause EOF
            {
             newCompositeNode(grammarAccess.getELSE_ClauseRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleELSE_Clause=ruleELSE_Clause();

            state._fsp--;

             current =iv_ruleELSE_Clause; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleELSE_Clause"


    // $ANTLR start "ruleELSE_Clause"
    // InternalStructuredTextParser.g:825:1: ruleELSE_Clause returns [EObject current=null] : (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) ) ;
    public final EObject ruleELSE_Clause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_statements_1_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:831:2: ( (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) ) )
            // InternalStructuredTextParser.g:832:2: (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) )
            {
            // InternalStructuredTextParser.g:832:2: (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) )
            // InternalStructuredTextParser.g:833:3: otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) )
            {
            otherlv_0=(Token)match(input,ELSE,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getELSE_ClauseAccess().getELSEKeyword_0());
            		
            // InternalStructuredTextParser.g:837:3: ( (lv_statements_1_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:838:4: (lv_statements_1_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:838:4: (lv_statements_1_0= ruleStmt_List )
            // InternalStructuredTextParser.g:839:5: lv_statements_1_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getELSE_ClauseAccess().getStatementsStmt_ListParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_statements_1_0=ruleStmt_List();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getELSE_ClauseRule());
            					}
            					set(
            						current,
            						"statements",
            						lv_statements_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleELSE_Clause"


    // $ANTLR start "entryRuleCase_Stmt"
    // InternalStructuredTextParser.g:860:1: entryRuleCase_Stmt returns [EObject current=null] : iv_ruleCase_Stmt= ruleCase_Stmt EOF ;
    public final EObject entryRuleCase_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCase_Stmt = null;


        try {
            // InternalStructuredTextParser.g:860:50: (iv_ruleCase_Stmt= ruleCase_Stmt EOF )
            // InternalStructuredTextParser.g:861:2: iv_ruleCase_Stmt= ruleCase_Stmt EOF
            {
             newCompositeNode(grammarAccess.getCase_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCase_Stmt=ruleCase_Stmt();

            state._fsp--;

             current =iv_ruleCase_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCase_Stmt"


    // $ANTLR start "ruleCase_Stmt"
    // InternalStructuredTextParser.g:867:1: ruleCase_Stmt returns [EObject current=null] : (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE ) ;
    public final EObject ruleCase_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_expression_1_0 = null;

        EObject lv_case_3_0 = null;

        EObject lv_else_4_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:873:2: ( (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE ) )
            // InternalStructuredTextParser.g:874:2: (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE )
            {
            // InternalStructuredTextParser.g:874:2: (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE )
            // InternalStructuredTextParser.g:875:3: otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_20); 

            			newLeafNode(otherlv_0, grammarAccess.getCase_StmtAccess().getCASEKeyword_0());
            		
            // InternalStructuredTextParser.g:879:3: ( (lv_expression_1_0= ruleExpression ) )
            // InternalStructuredTextParser.g:880:4: (lv_expression_1_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:880:4: (lv_expression_1_0= ruleExpression )
            // InternalStructuredTextParser.g:881:5: lv_expression_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getCase_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_15);
            lv_expression_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCase_StmtRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,OF,FOLLOW_17); 

            			newLeafNode(otherlv_2, grammarAccess.getCase_StmtAccess().getOFKeyword_2());
            		
            // InternalStructuredTextParser.g:902:3: ( (lv_case_3_0= ruleCase_Selection ) )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>=LDATE_AND_TIME && LA14_0<=TIME_OF_DAY)||LA14_0==WSTRING||LA14_0==STRING||(LA14_0>=FALSE && LA14_0<=LTIME)||(LA14_0>=UDINT && LA14_0<=ULINT)||(LA14_0>=USINT && LA14_0<=WCHAR)||LA14_0==BOOL||(LA14_0>=CHAR && LA14_0<=DINT)||(LA14_0>=LINT && LA14_0<=SINT)||(LA14_0>=TIME && LA14_0<=UINT)||(LA14_0>=INT && LA14_0<=LDT)||LA14_0==TOD||LA14_0==DT||(LA14_0>=LD && LA14_0<=LT)||LA14_0==PlusSign||LA14_0==HyphenMinus||LA14_0==T||LA14_0==D_1||(LA14_0>=RULE_BINARY_INT && LA14_0<=RULE_UNSIGNED_INT)||LA14_0==RULE_S_BYTE_CHAR_STR||LA14_0==RULE_D_BYTE_CHAR_STR) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalStructuredTextParser.g:903:4: (lv_case_3_0= ruleCase_Selection )
            	    {
            	    // InternalStructuredTextParser.g:903:4: (lv_case_3_0= ruleCase_Selection )
            	    // InternalStructuredTextParser.g:904:5: lv_case_3_0= ruleCase_Selection
            	    {

            	    					newCompositeNode(grammarAccess.getCase_StmtAccess().getCaseCase_SelectionParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_27);
            	    lv_case_3_0=ruleCase_Selection();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getCase_StmtRule());
            	    					}
            	    					add(
            	    						current,
            	    						"case",
            	    						lv_case_3_0,
            	    						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Case_Selection");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);

            // InternalStructuredTextParser.g:921:3: ( (lv_else_4_0= ruleELSE_Clause ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==ELSE) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalStructuredTextParser.g:922:4: (lv_else_4_0= ruleELSE_Clause )
                    {
                    // InternalStructuredTextParser.g:922:4: (lv_else_4_0= ruleELSE_Clause )
                    // InternalStructuredTextParser.g:923:5: lv_else_4_0= ruleELSE_Clause
                    {

                    					newCompositeNode(grammarAccess.getCase_StmtAccess().getElseELSE_ClauseParserRuleCall_4_0());
                    				
                    pushFollow(FOLLOW_28);
                    lv_else_4_0=ruleELSE_Clause();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getCase_StmtRule());
                    					}
                    					set(
                    						current,
                    						"else",
                    						lv_else_4_0,
                    						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ELSE_Clause");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,END_CASE,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getCase_StmtAccess().getEND_CASEKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCase_Stmt"


    // $ANTLR start "entryRuleCase_Selection"
    // InternalStructuredTextParser.g:948:1: entryRuleCase_Selection returns [EObject current=null] : iv_ruleCase_Selection= ruleCase_Selection EOF ;
    public final EObject entryRuleCase_Selection() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCase_Selection = null;


        try {
            // InternalStructuredTextParser.g:948:55: (iv_ruleCase_Selection= ruleCase_Selection EOF )
            // InternalStructuredTextParser.g:949:2: iv_ruleCase_Selection= ruleCase_Selection EOF
            {
             newCompositeNode(grammarAccess.getCase_SelectionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCase_Selection=ruleCase_Selection();

            state._fsp--;

             current =iv_ruleCase_Selection; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCase_Selection"


    // $ANTLR start "ruleCase_Selection"
    // InternalStructuredTextParser.g:955:1: ruleCase_Selection returns [EObject current=null] : ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) ) ) ;
    public final EObject ruleCase_Selection() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_case_0_0 = null;

        EObject lv_case_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:961:2: ( ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) ) ) )
            // InternalStructuredTextParser.g:962:2: ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) ) )
            {
            // InternalStructuredTextParser.g:962:2: ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) ) )
            // InternalStructuredTextParser.g:963:3: ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) )
            {
            // InternalStructuredTextParser.g:963:3: ( (lv_case_0_0= ruleConstant ) )
            // InternalStructuredTextParser.g:964:4: (lv_case_0_0= ruleConstant )
            {
            // InternalStructuredTextParser.g:964:4: (lv_case_0_0= ruleConstant )
            // InternalStructuredTextParser.g:965:5: lv_case_0_0= ruleConstant
            {

            					newCompositeNode(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_29);
            lv_case_0_0=ruleConstant();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCase_SelectionRule());
            					}
            					add(
            						current,
            						"case",
            						lv_case_0_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Constant");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalStructuredTextParser.g:982:3: (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==Comma) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalStructuredTextParser.g:983:4: otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_17); 

            	    				newLeafNode(otherlv_1, grammarAccess.getCase_SelectionAccess().getCommaKeyword_1_0());
            	    			
            	    // InternalStructuredTextParser.g:987:4: ( (lv_case_2_0= ruleConstant ) )
            	    // InternalStructuredTextParser.g:988:5: (lv_case_2_0= ruleConstant )
            	    {
            	    // InternalStructuredTextParser.g:988:5: (lv_case_2_0= ruleConstant )
            	    // InternalStructuredTextParser.g:989:6: lv_case_2_0= ruleConstant
            	    {

            	    						newCompositeNode(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_29);
            	    lv_case_2_0=ruleConstant();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCase_SelectionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"case",
            	    							lv_case_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Constant");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_5); 

            			newLeafNode(otherlv_3, grammarAccess.getCase_SelectionAccess().getColonKeyword_2());
            		
            // InternalStructuredTextParser.g:1011:3: ( (lv_statements_4_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:1012:4: (lv_statements_4_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:1012:4: (lv_statements_4_0= ruleStmt_List )
            // InternalStructuredTextParser.g:1013:5: lv_statements_4_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getCase_SelectionAccess().getStatementsStmt_ListParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_statements_4_0=ruleStmt_List();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCase_SelectionRule());
            					}
            					set(
            						current,
            						"statements",
            						lv_statements_4_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCase_Selection"


    // $ANTLR start "entryRuleIteration_Stmt"
    // InternalStructuredTextParser.g:1034:1: entryRuleIteration_Stmt returns [EObject current=null] : iv_ruleIteration_Stmt= ruleIteration_Stmt EOF ;
    public final EObject entryRuleIteration_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIteration_Stmt = null;


        try {
            // InternalStructuredTextParser.g:1034:55: (iv_ruleIteration_Stmt= ruleIteration_Stmt EOF )
            // InternalStructuredTextParser.g:1035:2: iv_ruleIteration_Stmt= ruleIteration_Stmt EOF
            {
             newCompositeNode(grammarAccess.getIteration_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIteration_Stmt=ruleIteration_Stmt();

            state._fsp--;

             current =iv_ruleIteration_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIteration_Stmt"


    // $ANTLR start "ruleIteration_Stmt"
    // InternalStructuredTextParser.g:1041:1: ruleIteration_Stmt returns [EObject current=null] : (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) ) ;
    public final EObject ruleIteration_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject this_For_Stmt_0 = null;

        EObject this_While_Stmt_1 = null;

        EObject this_Repeat_Stmt_2 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1047:2: ( (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) ) )
            // InternalStructuredTextParser.g:1048:2: (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) )
            {
            // InternalStructuredTextParser.g:1048:2: (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) )
            int alt17=5;
            switch ( input.LA(1) ) {
            case FOR:
                {
                alt17=1;
                }
                break;
            case WHILE:
                {
                alt17=2;
                }
                break;
            case REPEAT:
                {
                alt17=3;
                }
                break;
            case EXIT:
                {
                alt17=4;
                }
                break;
            case CONTINUE:
                {
                alt17=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // InternalStructuredTextParser.g:1049:3: this_For_Stmt_0= ruleFor_Stmt
                    {

                    			newCompositeNode(grammarAccess.getIteration_StmtAccess().getFor_StmtParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_For_Stmt_0=ruleFor_Stmt();

                    state._fsp--;


                    			current = this_For_Stmt_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1058:3: this_While_Stmt_1= ruleWhile_Stmt
                    {

                    			newCompositeNode(grammarAccess.getIteration_StmtAccess().getWhile_StmtParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_While_Stmt_1=ruleWhile_Stmt();

                    state._fsp--;


                    			current = this_While_Stmt_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1067:3: this_Repeat_Stmt_2= ruleRepeat_Stmt
                    {

                    			newCompositeNode(grammarAccess.getIteration_StmtAccess().getRepeat_StmtParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Repeat_Stmt_2=ruleRepeat_Stmt();

                    state._fsp--;


                    			current = this_Repeat_Stmt_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:1076:3: ( () otherlv_4= EXIT )
                    {
                    // InternalStructuredTextParser.g:1076:3: ( () otherlv_4= EXIT )
                    // InternalStructuredTextParser.g:1077:4: () otherlv_4= EXIT
                    {
                    // InternalStructuredTextParser.g:1077:4: ()
                    // InternalStructuredTextParser.g:1078:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getIteration_StmtAccess().getExitStatementAction_3_0(),
                    						current);
                    				

                    }

                    otherlv_4=(Token)match(input,EXIT,FOLLOW_2); 

                    				newLeafNode(otherlv_4, grammarAccess.getIteration_StmtAccess().getEXITKeyword_3_1());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:1090:3: ( () otherlv_6= CONTINUE )
                    {
                    // InternalStructuredTextParser.g:1090:3: ( () otherlv_6= CONTINUE )
                    // InternalStructuredTextParser.g:1091:4: () otherlv_6= CONTINUE
                    {
                    // InternalStructuredTextParser.g:1091:4: ()
                    // InternalStructuredTextParser.g:1092:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getIteration_StmtAccess().getContinueStatementAction_4_0(),
                    						current);
                    				

                    }

                    otherlv_6=(Token)match(input,CONTINUE,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getIteration_StmtAccess().getCONTINUEKeyword_4_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIteration_Stmt"


    // $ANTLR start "entryRuleFor_Stmt"
    // InternalStructuredTextParser.g:1107:1: entryRuleFor_Stmt returns [EObject current=null] : iv_ruleFor_Stmt= ruleFor_Stmt EOF ;
    public final EObject entryRuleFor_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFor_Stmt = null;


        try {
            // InternalStructuredTextParser.g:1107:49: (iv_ruleFor_Stmt= ruleFor_Stmt EOF )
            // InternalStructuredTextParser.g:1108:2: iv_ruleFor_Stmt= ruleFor_Stmt EOF
            {
             newCompositeNode(grammarAccess.getFor_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFor_Stmt=ruleFor_Stmt();

            state._fsp--;

             current =iv_ruleFor_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFor_Stmt"


    // $ANTLR start "ruleFor_Stmt"
    // InternalStructuredTextParser.g:1114:1: ruleFor_Stmt returns [EObject current=null] : (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR ) ;
    public final EObject ruleFor_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_variable_1_0 = null;

        EObject lv_from_3_0 = null;

        EObject lv_to_5_0 = null;

        EObject lv_by_7_0 = null;

        EObject lv_statements_9_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1120:2: ( (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR ) )
            // InternalStructuredTextParser.g:1121:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR )
            {
            // InternalStructuredTextParser.g:1121:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR )
            // InternalStructuredTextParser.g:1122:3: otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getFor_StmtAccess().getFORKeyword_0());
            		
            // InternalStructuredTextParser.g:1126:3: ( (lv_variable_1_0= ruleVariable_Primary ) )
            // InternalStructuredTextParser.g:1127:4: (lv_variable_1_0= ruleVariable_Primary )
            {
            // InternalStructuredTextParser.g:1127:4: (lv_variable_1_0= ruleVariable_Primary )
            // InternalStructuredTextParser.g:1128:5: lv_variable_1_0= ruleVariable_Primary
            {

            					newCompositeNode(grammarAccess.getFor_StmtAccess().getVariableVariable_PrimaryParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_19);
            lv_variable_1_0=ruleVariable_Primary();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFor_StmtRule());
            					}
            					set(
            						current,
            						"variable",
            						lv_variable_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Variable_Primary");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_20); 

            			newLeafNode(otherlv_2, grammarAccess.getFor_StmtAccess().getColonEqualsSignKeyword_2());
            		
            // InternalStructuredTextParser.g:1149:3: ( (lv_from_3_0= ruleExpression ) )
            // InternalStructuredTextParser.g:1150:4: (lv_from_3_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:1150:4: (lv_from_3_0= ruleExpression )
            // InternalStructuredTextParser.g:1151:5: lv_from_3_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getFor_StmtAccess().getFromExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_30);
            lv_from_3_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFor_StmtRule());
            					}
            					set(
            						current,
            						"from",
            						lv_from_3_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,TO,FOLLOW_20); 

            			newLeafNode(otherlv_4, grammarAccess.getFor_StmtAccess().getTOKeyword_4());
            		
            // InternalStructuredTextParser.g:1172:3: ( (lv_to_5_0= ruleExpression ) )
            // InternalStructuredTextParser.g:1173:4: (lv_to_5_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:1173:4: (lv_to_5_0= ruleExpression )
            // InternalStructuredTextParser.g:1174:5: lv_to_5_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getFor_StmtAccess().getToExpressionParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_31);
            lv_to_5_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFor_StmtRule());
            					}
            					set(
            						current,
            						"to",
            						lv_to_5_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalStructuredTextParser.g:1191:3: (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==BY) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalStructuredTextParser.g:1192:4: otherlv_6= BY ( (lv_by_7_0= ruleExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_20); 

                    				newLeafNode(otherlv_6, grammarAccess.getFor_StmtAccess().getBYKeyword_6_0());
                    			
                    // InternalStructuredTextParser.g:1196:4: ( (lv_by_7_0= ruleExpression ) )
                    // InternalStructuredTextParser.g:1197:5: (lv_by_7_0= ruleExpression )
                    {
                    // InternalStructuredTextParser.g:1197:5: (lv_by_7_0= ruleExpression )
                    // InternalStructuredTextParser.g:1198:6: lv_by_7_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getFor_StmtAccess().getByExpressionParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_32);
                    lv_by_7_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFor_StmtRule());
                    						}
                    						set(
                    							current,
                    							"by",
                    							lv_by_7_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,DO,FOLLOW_33); 

            			newLeafNode(otherlv_8, grammarAccess.getFor_StmtAccess().getDOKeyword_7());
            		
            // InternalStructuredTextParser.g:1220:3: ( (lv_statements_9_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:1221:4: (lv_statements_9_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:1221:4: (lv_statements_9_0= ruleStmt_List )
            // InternalStructuredTextParser.g:1222:5: lv_statements_9_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getFor_StmtAccess().getStatementsStmt_ListParserRuleCall_8_0());
            				
            pushFollow(FOLLOW_34);
            lv_statements_9_0=ruleStmt_List();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFor_StmtRule());
            					}
            					set(
            						current,
            						"statements",
            						lv_statements_9_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,END_FOR,FOLLOW_2); 

            			newLeafNode(otherlv_10, grammarAccess.getFor_StmtAccess().getEND_FORKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFor_Stmt"


    // $ANTLR start "entryRuleWhile_Stmt"
    // InternalStructuredTextParser.g:1247:1: entryRuleWhile_Stmt returns [EObject current=null] : iv_ruleWhile_Stmt= ruleWhile_Stmt EOF ;
    public final EObject entryRuleWhile_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhile_Stmt = null;


        try {
            // InternalStructuredTextParser.g:1247:51: (iv_ruleWhile_Stmt= ruleWhile_Stmt EOF )
            // InternalStructuredTextParser.g:1248:2: iv_ruleWhile_Stmt= ruleWhile_Stmt EOF
            {
             newCompositeNode(grammarAccess.getWhile_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWhile_Stmt=ruleWhile_Stmt();

            state._fsp--;

             current =iv_ruleWhile_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWhile_Stmt"


    // $ANTLR start "ruleWhile_Stmt"
    // InternalStructuredTextParser.g:1254:1: ruleWhile_Stmt returns [EObject current=null] : (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE ) ;
    public final EObject ruleWhile_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_expression_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1260:2: ( (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE ) )
            // InternalStructuredTextParser.g:1261:2: (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE )
            {
            // InternalStructuredTextParser.g:1261:2: (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE )
            // InternalStructuredTextParser.g:1262:3: otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_20); 

            			newLeafNode(otherlv_0, grammarAccess.getWhile_StmtAccess().getWHILEKeyword_0());
            		
            // InternalStructuredTextParser.g:1266:3: ( (lv_expression_1_0= ruleExpression ) )
            // InternalStructuredTextParser.g:1267:4: (lv_expression_1_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:1267:4: (lv_expression_1_0= ruleExpression )
            // InternalStructuredTextParser.g:1268:5: lv_expression_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getWhile_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_32);
            lv_expression_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWhile_StmtRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,DO,FOLLOW_35); 

            			newLeafNode(otherlv_2, grammarAccess.getWhile_StmtAccess().getDOKeyword_2());
            		
            // InternalStructuredTextParser.g:1289:3: ( (lv_statements_3_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:1290:4: (lv_statements_3_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:1290:4: (lv_statements_3_0= ruleStmt_List )
            // InternalStructuredTextParser.g:1291:5: lv_statements_3_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getWhile_StmtAccess().getStatementsStmt_ListParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_36);
            lv_statements_3_0=ruleStmt_List();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWhile_StmtRule());
            					}
            					set(
            						current,
            						"statements",
            						lv_statements_3_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,END_WHILE,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getWhile_StmtAccess().getEND_WHILEKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWhile_Stmt"


    // $ANTLR start "entryRuleRepeat_Stmt"
    // InternalStructuredTextParser.g:1316:1: entryRuleRepeat_Stmt returns [EObject current=null] : iv_ruleRepeat_Stmt= ruleRepeat_Stmt EOF ;
    public final EObject entryRuleRepeat_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRepeat_Stmt = null;


        try {
            // InternalStructuredTextParser.g:1316:52: (iv_ruleRepeat_Stmt= ruleRepeat_Stmt EOF )
            // InternalStructuredTextParser.g:1317:2: iv_ruleRepeat_Stmt= ruleRepeat_Stmt EOF
            {
             newCompositeNode(grammarAccess.getRepeat_StmtRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRepeat_Stmt=ruleRepeat_Stmt();

            state._fsp--;

             current =iv_ruleRepeat_Stmt; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRepeat_Stmt"


    // $ANTLR start "ruleRepeat_Stmt"
    // InternalStructuredTextParser.g:1323:1: ruleRepeat_Stmt returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleRepeat_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1329:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalStructuredTextParser.g:1330:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalStructuredTextParser.g:1330:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT )
            // InternalStructuredTextParser.g:1331:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_37); 

            			newLeafNode(otherlv_0, grammarAccess.getRepeat_StmtAccess().getREPEATKeyword_0());
            		
            // InternalStructuredTextParser.g:1335:3: ( (lv_statements_1_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:1336:4: (lv_statements_1_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:1336:4: (lv_statements_1_0= ruleStmt_List )
            // InternalStructuredTextParser.g:1337:5: lv_statements_1_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getRepeat_StmtAccess().getStatementsStmt_ListParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_38);
            lv_statements_1_0=ruleStmt_List();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRepeat_StmtRule());
            					}
            					set(
            						current,
            						"statements",
            						lv_statements_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_20); 

            			newLeafNode(otherlv_2, grammarAccess.getRepeat_StmtAccess().getUNTILKeyword_2());
            		
            // InternalStructuredTextParser.g:1358:3: ( (lv_expression_3_0= ruleExpression ) )
            // InternalStructuredTextParser.g:1359:4: (lv_expression_3_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:1359:4: (lv_expression_3_0= ruleExpression )
            // InternalStructuredTextParser.g:1360:5: lv_expression_3_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getRepeat_StmtAccess().getExpressionExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_39);
            lv_expression_3_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRepeat_StmtRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_3_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,END_REPEAT,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getRepeat_StmtAccess().getEND_REPEATKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRepeat_Stmt"


    // $ANTLR start "entryRuleExpression"
    // InternalStructuredTextParser.g:1385:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalStructuredTextParser.g:1385:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalStructuredTextParser.g:1386:2: iv_ruleExpression= ruleExpression EOF
            {
             newCompositeNode(grammarAccess.getExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpression=ruleExpression();

            state._fsp--;

             current =iv_ruleExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpression"


    // $ANTLR start "ruleExpression"
    // InternalStructuredTextParser.g:1392:1: ruleExpression returns [EObject current=null] : this_Or_Expression_0= ruleOr_Expression ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_Or_Expression_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1398:2: (this_Or_Expression_0= ruleOr_Expression )
            // InternalStructuredTextParser.g:1399:2: this_Or_Expression_0= ruleOr_Expression
            {

            		newCompositeNode(grammarAccess.getExpressionAccess().getOr_ExpressionParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_Or_Expression_0=ruleOr_Expression();

            state._fsp--;


            		current = this_Or_Expression_0;
            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpression"


    // $ANTLR start "entryRuleOr_Expression"
    // InternalStructuredTextParser.g:1410:1: entryRuleOr_Expression returns [EObject current=null] : iv_ruleOr_Expression= ruleOr_Expression EOF ;
    public final EObject entryRuleOr_Expression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOr_Expression = null;


        try {
            // InternalStructuredTextParser.g:1410:54: (iv_ruleOr_Expression= ruleOr_Expression EOF )
            // InternalStructuredTextParser.g:1411:2: iv_ruleOr_Expression= ruleOr_Expression EOF
            {
             newCompositeNode(grammarAccess.getOr_ExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOr_Expression=ruleOr_Expression();

            state._fsp--;

             current =iv_ruleOr_Expression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOr_Expression"


    // $ANTLR start "ruleOr_Expression"
    // InternalStructuredTextParser.g:1417:1: ruleOr_Expression returns [EObject current=null] : (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* ) ;
    public final EObject ruleOr_Expression() throws RecognitionException {
        EObject current = null;

        EObject this_Xor_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1423:2: ( (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1424:2: (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1424:2: (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* )
            // InternalStructuredTextParser.g:1425:3: this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getOr_ExpressionAccess().getXor_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_40);
            this_Xor_Expr_0=ruleXor_Expr();

            state._fsp--;


            			current = this_Xor_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1433:3: ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==OR) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1434:4: () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1434:4: ()
            	    // InternalStructuredTextParser.g:1435:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1441:4: ( (lv_operator_2_0= ruleOr_Operator ) )
            	    // InternalStructuredTextParser.g:1442:5: (lv_operator_2_0= ruleOr_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1442:5: (lv_operator_2_0= ruleOr_Operator )
            	    // InternalStructuredTextParser.g:1443:6: lv_operator_2_0= ruleOr_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getOr_ExpressionAccess().getOperatorOr_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=ruleOr_Operator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getOr_ExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Or_Operator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalStructuredTextParser.g:1460:4: ( (lv_right_3_0= ruleXor_Expr ) )
            	    // InternalStructuredTextParser.g:1461:5: (lv_right_3_0= ruleXor_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1461:5: (lv_right_3_0= ruleXor_Expr )
            	    // InternalStructuredTextParser.g:1462:6: lv_right_3_0= ruleXor_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getOr_ExpressionAccess().getRightXor_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_40);
            	    lv_right_3_0=ruleXor_Expr();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getOr_ExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Xor_Expr");
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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOr_Expression"


    // $ANTLR start "entryRuleXor_Expr"
    // InternalStructuredTextParser.g:1484:1: entryRuleXor_Expr returns [EObject current=null] : iv_ruleXor_Expr= ruleXor_Expr EOF ;
    public final EObject entryRuleXor_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXor_Expr = null;


        try {
            // InternalStructuredTextParser.g:1484:49: (iv_ruleXor_Expr= ruleXor_Expr EOF )
            // InternalStructuredTextParser.g:1485:2: iv_ruleXor_Expr= ruleXor_Expr EOF
            {
             newCompositeNode(grammarAccess.getXor_ExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleXor_Expr=ruleXor_Expr();

            state._fsp--;

             current =iv_ruleXor_Expr; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXor_Expr"


    // $ANTLR start "ruleXor_Expr"
    // InternalStructuredTextParser.g:1491:1: ruleXor_Expr returns [EObject current=null] : (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* ) ;
    public final EObject ruleXor_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_And_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1497:2: ( (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1498:2: (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1498:2: (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* )
            // InternalStructuredTextParser.g:1499:3: this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getXor_ExprAccess().getAnd_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_41);
            this_And_Expr_0=ruleAnd_Expr();

            state._fsp--;


            			current = this_And_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1507:3: ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==XOR) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1508:4: () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1508:4: ()
            	    // InternalStructuredTextParser.g:1509:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1515:4: ( (lv_operator_2_0= ruleXor_Operator ) )
            	    // InternalStructuredTextParser.g:1516:5: (lv_operator_2_0= ruleXor_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1516:5: (lv_operator_2_0= ruleXor_Operator )
            	    // InternalStructuredTextParser.g:1517:6: lv_operator_2_0= ruleXor_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getXor_ExprAccess().getOperatorXor_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=ruleXor_Operator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getXor_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Xor_Operator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalStructuredTextParser.g:1534:4: ( (lv_right_3_0= ruleAnd_Expr ) )
            	    // InternalStructuredTextParser.g:1535:5: (lv_right_3_0= ruleAnd_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1535:5: (lv_right_3_0= ruleAnd_Expr )
            	    // InternalStructuredTextParser.g:1536:6: lv_right_3_0= ruleAnd_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getXor_ExprAccess().getRightAnd_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_41);
            	    lv_right_3_0=ruleAnd_Expr();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getXor_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.And_Expr");
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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXor_Expr"


    // $ANTLR start "entryRuleAnd_Expr"
    // InternalStructuredTextParser.g:1558:1: entryRuleAnd_Expr returns [EObject current=null] : iv_ruleAnd_Expr= ruleAnd_Expr EOF ;
    public final EObject entryRuleAnd_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnd_Expr = null;


        try {
            // InternalStructuredTextParser.g:1558:49: (iv_ruleAnd_Expr= ruleAnd_Expr EOF )
            // InternalStructuredTextParser.g:1559:2: iv_ruleAnd_Expr= ruleAnd_Expr EOF
            {
             newCompositeNode(grammarAccess.getAnd_ExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAnd_Expr=ruleAnd_Expr();

            state._fsp--;

             current =iv_ruleAnd_Expr; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnd_Expr"


    // $ANTLR start "ruleAnd_Expr"
    // InternalStructuredTextParser.g:1565:1: ruleAnd_Expr returns [EObject current=null] : (this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )* ) ;
    public final EObject ruleAnd_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Compare_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1571:2: ( (this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1572:2: (this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1572:2: (this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )* )
            // InternalStructuredTextParser.g:1573:3: this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getAnd_ExprAccess().getCompare_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_42);
            this_Compare_Expr_0=ruleCompare_Expr();

            state._fsp--;


            			current = this_Compare_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1581:3: ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==AND||LA21_0==Ampersand) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1582:4: () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1582:4: ()
            	    // InternalStructuredTextParser.g:1583:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1589:4: ( (lv_operator_2_0= ruleAnd_Operator ) )
            	    // InternalStructuredTextParser.g:1590:5: (lv_operator_2_0= ruleAnd_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1590:5: (lv_operator_2_0= ruleAnd_Operator )
            	    // InternalStructuredTextParser.g:1591:6: lv_operator_2_0= ruleAnd_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getAnd_ExprAccess().getOperatorAnd_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=ruleAnd_Operator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAnd_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.And_Operator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalStructuredTextParser.g:1608:4: ( (lv_right_3_0= ruleCompare_Expr ) )
            	    // InternalStructuredTextParser.g:1609:5: (lv_right_3_0= ruleCompare_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1609:5: (lv_right_3_0= ruleCompare_Expr )
            	    // InternalStructuredTextParser.g:1610:6: lv_right_3_0= ruleCompare_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getAnd_ExprAccess().getRightCompare_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_42);
            	    lv_right_3_0=ruleCompare_Expr();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAnd_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Compare_Expr");
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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnd_Expr"


    // $ANTLR start "entryRuleCompare_Expr"
    // InternalStructuredTextParser.g:1632:1: entryRuleCompare_Expr returns [EObject current=null] : iv_ruleCompare_Expr= ruleCompare_Expr EOF ;
    public final EObject entryRuleCompare_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCompare_Expr = null;


        try {
            // InternalStructuredTextParser.g:1632:53: (iv_ruleCompare_Expr= ruleCompare_Expr EOF )
            // InternalStructuredTextParser.g:1633:2: iv_ruleCompare_Expr= ruleCompare_Expr EOF
            {
             newCompositeNode(grammarAccess.getCompare_ExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCompare_Expr=ruleCompare_Expr();

            state._fsp--;

             current =iv_ruleCompare_Expr; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCompare_Expr"


    // $ANTLR start "ruleCompare_Expr"
    // InternalStructuredTextParser.g:1639:1: ruleCompare_Expr returns [EObject current=null] : (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* ) ;
    public final EObject ruleCompare_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Equ_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1645:2: ( (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1646:2: (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1646:2: (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* )
            // InternalStructuredTextParser.g:1647:3: this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getCompare_ExprAccess().getEqu_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_43);
            this_Equ_Expr_0=ruleEqu_Expr();

            state._fsp--;


            			current = this_Equ_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1655:3: ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==LessThanSignGreaterThanSign||LA22_0==EqualsSign) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1656:4: () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1656:4: ()
            	    // InternalStructuredTextParser.g:1657:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1663:4: ( (lv_operator_2_0= ruleCompare_Operator ) )
            	    // InternalStructuredTextParser.g:1664:5: (lv_operator_2_0= ruleCompare_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1664:5: (lv_operator_2_0= ruleCompare_Operator )
            	    // InternalStructuredTextParser.g:1665:6: lv_operator_2_0= ruleCompare_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getCompare_ExprAccess().getOperatorCompare_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=ruleCompare_Operator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCompare_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Compare_Operator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalStructuredTextParser.g:1682:4: ( (lv_right_3_0= ruleEqu_Expr ) )
            	    // InternalStructuredTextParser.g:1683:5: (lv_right_3_0= ruleEqu_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1683:5: (lv_right_3_0= ruleEqu_Expr )
            	    // InternalStructuredTextParser.g:1684:6: lv_right_3_0= ruleEqu_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getCompare_ExprAccess().getRightEqu_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_43);
            	    lv_right_3_0=ruleEqu_Expr();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCompare_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Equ_Expr");
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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCompare_Expr"


    // $ANTLR start "entryRuleEqu_Expr"
    // InternalStructuredTextParser.g:1706:1: entryRuleEqu_Expr returns [EObject current=null] : iv_ruleEqu_Expr= ruleEqu_Expr EOF ;
    public final EObject entryRuleEqu_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqu_Expr = null;


        try {
            // InternalStructuredTextParser.g:1706:49: (iv_ruleEqu_Expr= ruleEqu_Expr EOF )
            // InternalStructuredTextParser.g:1707:2: iv_ruleEqu_Expr= ruleEqu_Expr EOF
            {
             newCompositeNode(grammarAccess.getEqu_ExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEqu_Expr=ruleEqu_Expr();

            state._fsp--;

             current =iv_ruleEqu_Expr; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEqu_Expr"


    // $ANTLR start "ruleEqu_Expr"
    // InternalStructuredTextParser.g:1713:1: ruleEqu_Expr returns [EObject current=null] : (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* ) ;
    public final EObject ruleEqu_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Add_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1719:2: ( (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1720:2: (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1720:2: (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* )
            // InternalStructuredTextParser.g:1721:3: this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getEqu_ExprAccess().getAdd_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_44);
            this_Add_Expr_0=ruleAdd_Expr();

            state._fsp--;


            			current = this_Add_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1729:3: ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==LessThanSignEqualsSign||LA23_0==GreaterThanSignEqualsSign||LA23_0==LessThanSign||LA23_0==GreaterThanSign) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1730:4: () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1730:4: ()
            	    // InternalStructuredTextParser.g:1731:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1737:4: ( (lv_operator_2_0= ruleEqu_Operator ) )
            	    // InternalStructuredTextParser.g:1738:5: (lv_operator_2_0= ruleEqu_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1738:5: (lv_operator_2_0= ruleEqu_Operator )
            	    // InternalStructuredTextParser.g:1739:6: lv_operator_2_0= ruleEqu_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getEqu_ExprAccess().getOperatorEqu_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=ruleEqu_Operator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getEqu_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Equ_Operator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalStructuredTextParser.g:1756:4: ( (lv_right_3_0= ruleAdd_Expr ) )
            	    // InternalStructuredTextParser.g:1757:5: (lv_right_3_0= ruleAdd_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1757:5: (lv_right_3_0= ruleAdd_Expr )
            	    // InternalStructuredTextParser.g:1758:6: lv_right_3_0= ruleAdd_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getEqu_ExprAccess().getRightAdd_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_44);
            	    lv_right_3_0=ruleAdd_Expr();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getEqu_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Add_Expr");
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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEqu_Expr"


    // $ANTLR start "entryRuleAdd_Expr"
    // InternalStructuredTextParser.g:1780:1: entryRuleAdd_Expr returns [EObject current=null] : iv_ruleAdd_Expr= ruleAdd_Expr EOF ;
    public final EObject entryRuleAdd_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdd_Expr = null;


        try {
            // InternalStructuredTextParser.g:1780:49: (iv_ruleAdd_Expr= ruleAdd_Expr EOF )
            // InternalStructuredTextParser.g:1781:2: iv_ruleAdd_Expr= ruleAdd_Expr EOF
            {
             newCompositeNode(grammarAccess.getAdd_ExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAdd_Expr=ruleAdd_Expr();

            state._fsp--;

             current =iv_ruleAdd_Expr; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAdd_Expr"


    // $ANTLR start "ruleAdd_Expr"
    // InternalStructuredTextParser.g:1787:1: ruleAdd_Expr returns [EObject current=null] : (this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* ) ;
    public final EObject ruleAdd_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Term_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1793:2: ( (this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* ) )
            // InternalStructuredTextParser.g:1794:2: (this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* )
            {
            // InternalStructuredTextParser.g:1794:2: (this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* )
            // InternalStructuredTextParser.g:1795:3: this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )*
            {

            			newCompositeNode(grammarAccess.getAdd_ExprAccess().getTermParserRuleCall_0());
            		
            pushFollow(FOLLOW_45);
            this_Term_0=ruleTerm();

            state._fsp--;


            			current = this_Term_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1803:3: ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==PlusSign||LA24_0==HyphenMinus) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1804:4: () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) )
            	    {
            	    // InternalStructuredTextParser.g:1804:4: ()
            	    // InternalStructuredTextParser.g:1805:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1811:4: ( (lv_operator_2_0= ruleAdd_Operator ) )
            	    // InternalStructuredTextParser.g:1812:5: (lv_operator_2_0= ruleAdd_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1812:5: (lv_operator_2_0= ruleAdd_Operator )
            	    // InternalStructuredTextParser.g:1813:6: lv_operator_2_0= ruleAdd_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getAdd_ExprAccess().getOperatorAdd_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=ruleAdd_Operator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAdd_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Add_Operator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalStructuredTextParser.g:1830:4: ( (lv_right_3_0= ruleTerm ) )
            	    // InternalStructuredTextParser.g:1831:5: (lv_right_3_0= ruleTerm )
            	    {
            	    // InternalStructuredTextParser.g:1831:5: (lv_right_3_0= ruleTerm )
            	    // InternalStructuredTextParser.g:1832:6: lv_right_3_0= ruleTerm
            	    {

            	    						newCompositeNode(grammarAccess.getAdd_ExprAccess().getRightTermParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_45);
            	    lv_right_3_0=ruleTerm();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAdd_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Term");
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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAdd_Expr"


    // $ANTLR start "entryRuleTerm"
    // InternalStructuredTextParser.g:1854:1: entryRuleTerm returns [EObject current=null] : iv_ruleTerm= ruleTerm EOF ;
    public final EObject entryRuleTerm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTerm = null;


        try {
            // InternalStructuredTextParser.g:1854:45: (iv_ruleTerm= ruleTerm EOF )
            // InternalStructuredTextParser.g:1855:2: iv_ruleTerm= ruleTerm EOF
            {
             newCompositeNode(grammarAccess.getTermRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTerm=ruleTerm();

            state._fsp--;

             current =iv_ruleTerm; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTerm"


    // $ANTLR start "ruleTerm"
    // InternalStructuredTextParser.g:1861:1: ruleTerm returns [EObject current=null] : (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* ) ;
    public final EObject ruleTerm() throws RecognitionException {
        EObject current = null;

        EObject this_Power_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1867:2: ( (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1868:2: (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1868:2: (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* )
            // InternalStructuredTextParser.g:1869:3: this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getTermAccess().getPower_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_46);
            this_Power_Expr_0=rulePower_Expr();

            state._fsp--;


            			current = this_Power_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1877:3: ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==MOD||LA25_0==Asterisk||LA25_0==Solidus) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1878:4: () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1878:4: ()
            	    // InternalStructuredTextParser.g:1879:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1885:4: ( (lv_operator_2_0= ruleTerm_Operator ) )
            	    // InternalStructuredTextParser.g:1886:5: (lv_operator_2_0= ruleTerm_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1886:5: (lv_operator_2_0= ruleTerm_Operator )
            	    // InternalStructuredTextParser.g:1887:6: lv_operator_2_0= ruleTerm_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getTermAccess().getOperatorTerm_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=ruleTerm_Operator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getTermRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Term_Operator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalStructuredTextParser.g:1904:4: ( (lv_right_3_0= rulePower_Expr ) )
            	    // InternalStructuredTextParser.g:1905:5: (lv_right_3_0= rulePower_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1905:5: (lv_right_3_0= rulePower_Expr )
            	    // InternalStructuredTextParser.g:1906:6: lv_right_3_0= rulePower_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getTermAccess().getRightPower_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_46);
            	    lv_right_3_0=rulePower_Expr();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getTermRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Power_Expr");
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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTerm"


    // $ANTLR start "entryRulePower_Expr"
    // InternalStructuredTextParser.g:1928:1: entryRulePower_Expr returns [EObject current=null] : iv_rulePower_Expr= rulePower_Expr EOF ;
    public final EObject entryRulePower_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePower_Expr = null;


        try {
            // InternalStructuredTextParser.g:1928:51: (iv_rulePower_Expr= rulePower_Expr EOF )
            // InternalStructuredTextParser.g:1929:2: iv_rulePower_Expr= rulePower_Expr EOF
            {
             newCompositeNode(grammarAccess.getPower_ExprRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePower_Expr=rulePower_Expr();

            state._fsp--;

             current =iv_rulePower_Expr; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePower_Expr"


    // $ANTLR start "rulePower_Expr"
    // InternalStructuredTextParser.g:1935:1: rulePower_Expr returns [EObject current=null] : (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* ) ;
    public final EObject rulePower_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Unary_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1941:2: ( (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1942:2: (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1942:2: (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* )
            // InternalStructuredTextParser.g:1943:3: this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getPower_ExprAccess().getUnary_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_47);
            this_Unary_Expr_0=ruleUnary_Expr();

            state._fsp--;


            			current = this_Unary_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1951:3: ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==AsteriskAsterisk) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1952:4: () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1952:4: ()
            	    // InternalStructuredTextParser.g:1953:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1959:4: ( (lv_operator_2_0= rulePower_Operator ) )
            	    // InternalStructuredTextParser.g:1960:5: (lv_operator_2_0= rulePower_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1960:5: (lv_operator_2_0= rulePower_Operator )
            	    // InternalStructuredTextParser.g:1961:6: lv_operator_2_0= rulePower_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getPower_ExprAccess().getOperatorPower_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=rulePower_Operator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getPower_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Power_Operator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalStructuredTextParser.g:1978:4: ( (lv_right_3_0= ruleUnary_Expr ) )
            	    // InternalStructuredTextParser.g:1979:5: (lv_right_3_0= ruleUnary_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1979:5: (lv_right_3_0= ruleUnary_Expr )
            	    // InternalStructuredTextParser.g:1980:6: lv_right_3_0= ruleUnary_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getPower_ExprAccess().getRightUnary_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_47);
            	    lv_right_3_0=ruleUnary_Expr();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getPower_ExprRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Unary_Expr");
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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePower_Expr"


    // $ANTLR start "entryRuleUnary_Expr"
    // InternalStructuredTextParser.g:2002:1: entryRuleUnary_Expr returns [EObject current=null] : iv_ruleUnary_Expr= ruleUnary_Expr EOF ;
    public final EObject entryRuleUnary_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnary_Expr = null;


        try {
            // InternalStructuredTextParser.g:2002:51: (iv_ruleUnary_Expr= ruleUnary_Expr EOF )
            // InternalStructuredTextParser.g:2003:2: iv_ruleUnary_Expr= ruleUnary_Expr EOF
            {
             newCompositeNode(grammarAccess.getUnary_ExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUnary_Expr=ruleUnary_Expr();

            state._fsp--;

             current =iv_ruleUnary_Expr; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnary_Expr"


    // $ANTLR start "ruleUnary_Expr"
    // InternalStructuredTextParser.g:2009:1: ruleUnary_Expr returns [EObject current=null] : ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant ) ;
    public final EObject ruleUnary_Expr() throws RecognitionException {
        EObject current = null;

        Enumerator lv_operator_1_0 = null;

        EObject lv_expression_2_0 = null;

        EObject this_Primary_Expr_3 = null;

        EObject this_Constant_4 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2015:2: ( ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant ) )
            // InternalStructuredTextParser.g:2016:2: ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )
            {
            // InternalStructuredTextParser.g:2016:2: ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )
            int alt27=3;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalStructuredTextParser.g:2017:3: ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) )
                    {
                    // InternalStructuredTextParser.g:2017:3: ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) )
                    // InternalStructuredTextParser.g:2018:4: () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) )
                    {
                    // InternalStructuredTextParser.g:2018:4: ()
                    // InternalStructuredTextParser.g:2019:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0(),
                    						current);
                    				

                    }

                    // InternalStructuredTextParser.g:2025:4: ( (lv_operator_1_0= ruleUnary_Operator ) )
                    // InternalStructuredTextParser.g:2026:5: (lv_operator_1_0= ruleUnary_Operator )
                    {
                    // InternalStructuredTextParser.g:2026:5: (lv_operator_1_0= ruleUnary_Operator )
                    // InternalStructuredTextParser.g:2027:6: lv_operator_1_0= ruleUnary_Operator
                    {

                    						newCompositeNode(grammarAccess.getUnary_ExprAccess().getOperatorUnary_OperatorEnumRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_48);
                    lv_operator_1_0=ruleUnary_Operator();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getUnary_ExprRule());
                    						}
                    						set(
                    							current,
                    							"operator",
                    							lv_operator_1_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Unary_Operator");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:2044:4: ( (lv_expression_2_0= rulePrimary_Expr ) )
                    // InternalStructuredTextParser.g:2045:5: (lv_expression_2_0= rulePrimary_Expr )
                    {
                    // InternalStructuredTextParser.g:2045:5: (lv_expression_2_0= rulePrimary_Expr )
                    // InternalStructuredTextParser.g:2046:6: lv_expression_2_0= rulePrimary_Expr
                    {

                    						newCompositeNode(grammarAccess.getUnary_ExprAccess().getExpressionPrimary_ExprParserRuleCall_0_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_expression_2_0=rulePrimary_Expr();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getUnary_ExprRule());
                    						}
                    						set(
                    							current,
                    							"expression",
                    							lv_expression_2_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Primary_Expr");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2065:3: this_Primary_Expr_3= rulePrimary_Expr
                    {

                    			newCompositeNode(grammarAccess.getUnary_ExprAccess().getPrimary_ExprParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Primary_Expr_3=rulePrimary_Expr();

                    state._fsp--;


                    			current = this_Primary_Expr_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2074:3: this_Constant_4= ruleConstant
                    {

                    			newCompositeNode(grammarAccess.getUnary_ExprAccess().getConstantParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Constant_4=ruleConstant();

                    state._fsp--;


                    			current = this_Constant_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnary_Expr"


    // $ANTLR start "entryRulePrimary_Expr"
    // InternalStructuredTextParser.g:2086:1: entryRulePrimary_Expr returns [EObject current=null] : iv_rulePrimary_Expr= rulePrimary_Expr EOF ;
    public final EObject entryRulePrimary_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary_Expr = null;


        try {
            // InternalStructuredTextParser.g:2086:53: (iv_rulePrimary_Expr= rulePrimary_Expr EOF )
            // InternalStructuredTextParser.g:2087:2: iv_rulePrimary_Expr= rulePrimary_Expr EOF
            {
             newCompositeNode(grammarAccess.getPrimary_ExprRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePrimary_Expr=rulePrimary_Expr();

            state._fsp--;

             current =iv_rulePrimary_Expr; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimary_Expr"


    // $ANTLR start "rulePrimary_Expr"
    // InternalStructuredTextParser.g:2093:1: rulePrimary_Expr returns [EObject current=null] : (this_Variable_0= ruleVariable | this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) ) ;
    public final EObject rulePrimary_Expr() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_Variable_0 = null;

        EObject this_Func_Call_1 = null;

        EObject this_Expression_3 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2099:2: ( (this_Variable_0= ruleVariable | this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) ) )
            // InternalStructuredTextParser.g:2100:2: (this_Variable_0= ruleVariable | this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) )
            {
            // InternalStructuredTextParser.g:2100:2: (this_Variable_0= ruleVariable | this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) )
            int alt28=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA28_1 = input.LA(2);

                if ( (LA28_1==EOF||LA28_1==END_REPEAT||LA28_1==THEN||(LA28_1>=B && LA28_1<=AND)||LA28_1==MOD||(LA28_1>=XOR && LA28_1<=AsteriskAsterisk)||(LA28_1>=LessThanSignEqualsSign && LA28_1<=LessThanSignGreaterThanSign)||LA28_1==GreaterThanSignEqualsSign||(LA28_1>=BY && LA28_1<=DO)||(LA28_1>=OF && LA28_1<=TO)||LA28_1==Ampersand||(LA28_1>=RightParenthesis && LA28_1<=Solidus)||(LA28_1>=Semicolon && LA28_1<=GreaterThanSign)||(LA28_1>=LeftSquareBracket && LA28_1<=RightSquareBracket)) ) {
                    alt28=1;
                }
                else if ( (LA28_1==LeftParenthesis) ) {
                    alt28=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 1, input);

                    throw nvae;
                }
                }
                break;
            case DT:
            case LT:
            case T:
                {
                alt28=1;
                }
                break;
            case TIME:
                {
                alt28=2;
                }
                break;
            case LeftParenthesis:
                {
                alt28=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // InternalStructuredTextParser.g:2101:3: this_Variable_0= ruleVariable
                    {

                    			newCompositeNode(grammarAccess.getPrimary_ExprAccess().getVariableParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Variable_0=ruleVariable();

                    state._fsp--;


                    			current = this_Variable_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2110:3: this_Func_Call_1= ruleFunc_Call
                    {

                    			newCompositeNode(grammarAccess.getPrimary_ExprAccess().getFunc_CallParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Func_Call_1=ruleFunc_Call();

                    state._fsp--;


                    			current = this_Func_Call_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2119:3: (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis )
                    {
                    // InternalStructuredTextParser.g:2119:3: (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis )
                    // InternalStructuredTextParser.g:2120:4: otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis
                    {
                    otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_20); 

                    				newLeafNode(otherlv_2, grammarAccess.getPrimary_ExprAccess().getLeftParenthesisKeyword_2_0());
                    			

                    				newCompositeNode(grammarAccess.getPrimary_ExprAccess().getExpressionParserRuleCall_2_1());
                    			
                    pushFollow(FOLLOW_22);
                    this_Expression_3=ruleExpression();

                    state._fsp--;


                    				current = this_Expression_3;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); 

                    				newLeafNode(otherlv_4, grammarAccess.getPrimary_ExprAccess().getRightParenthesisKeyword_2_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimary_Expr"


    // $ANTLR start "entryRuleFunc_Call"
    // InternalStructuredTextParser.g:2141:1: entryRuleFunc_Call returns [EObject current=null] : iv_ruleFunc_Call= ruleFunc_Call EOF ;
    public final EObject entryRuleFunc_Call() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunc_Call = null;


        try {
            // InternalStructuredTextParser.g:2141:50: (iv_ruleFunc_Call= ruleFunc_Call EOF )
            // InternalStructuredTextParser.g:2142:2: iv_ruleFunc_Call= ruleFunc_Call EOF
            {
             newCompositeNode(grammarAccess.getFunc_CallRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFunc_Call=ruleFunc_Call();

            state._fsp--;

             current =iv_ruleFunc_Call; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunc_Call"


    // $ANTLR start "ruleFunc_Call"
    // InternalStructuredTextParser.g:2148:1: ruleFunc_Call returns [EObject current=null] : ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis ) ;
    public final EObject ruleFunc_Call() throws RecognitionException {
        EObject current = null;

        Token lv_func_0_1=null;
        Token lv_func_0_2=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_args_2_0 = null;

        EObject lv_args_4_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2154:2: ( ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis ) )
            // InternalStructuredTextParser.g:2155:2: ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis )
            {
            // InternalStructuredTextParser.g:2155:2: ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis )
            // InternalStructuredTextParser.g:2156:3: ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis
            {
            // InternalStructuredTextParser.g:2156:3: ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) )
            // InternalStructuredTextParser.g:2157:4: ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) )
            {
            // InternalStructuredTextParser.g:2157:4: ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) )
            // InternalStructuredTextParser.g:2158:5: (lv_func_0_1= RULE_ID | lv_func_0_2= TIME )
            {
            // InternalStructuredTextParser.g:2158:5: (lv_func_0_1= RULE_ID | lv_func_0_2= TIME )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_ID) ) {
                alt29=1;
            }
            else if ( (LA29_0==TIME) ) {
                alt29=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // InternalStructuredTextParser.g:2159:6: lv_func_0_1= RULE_ID
                    {
                    lv_func_0_1=(Token)match(input,RULE_ID,FOLLOW_21); 

                    						newLeafNode(lv_func_0_1, grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getFunc_CallRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"func",
                    							lv_func_0_1,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");
                    					

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2174:6: lv_func_0_2= TIME
                    {
                    lv_func_0_2=(Token)match(input,TIME,FOLLOW_21); 

                    						newLeafNode(lv_func_0_2, grammarAccess.getFunc_CallAccess().getFuncTIMEKeyword_0_0_1());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getFunc_CallRule());
                    						}
                    						setWithLastConsumed(current, "func", lv_func_0_2, null);
                    					

                    }
                    break;

            }


            }


            }

            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_49); 

            			newLeafNode(otherlv_1, grammarAccess.getFunc_CallAccess().getLeftParenthesisKeyword_1());
            		
            // InternalStructuredTextParser.g:2191:3: ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>=LDATE_AND_TIME && LA31_0<=TIME_OF_DAY)||LA31_0==WSTRING||LA31_0==STRING||(LA31_0>=FALSE && LA31_0<=LTIME)||(LA31_0>=UDINT && LA31_0<=ULINT)||(LA31_0>=USINT && LA31_0<=WCHAR)||LA31_0==BOOL||(LA31_0>=CHAR && LA31_0<=DINT)||(LA31_0>=LINT && LA31_0<=SINT)||(LA31_0>=TIME && LA31_0<=UINT)||(LA31_0>=INT && LA31_0<=LDT)||(LA31_0>=NOT && LA31_0<=TOD)||LA31_0==DT||(LA31_0>=LD && LA31_0<=LT)||LA31_0==LeftParenthesis||LA31_0==PlusSign||LA31_0==HyphenMinus||LA31_0==T||LA31_0==D_1||(LA31_0>=RULE_ID && LA31_0<=RULE_UNSIGNED_INT)||LA31_0==RULE_S_BYTE_CHAR_STR||LA31_0==RULE_D_BYTE_CHAR_STR) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalStructuredTextParser.g:2192:4: ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )*
                    {
                    // InternalStructuredTextParser.g:2192:4: ( (lv_args_2_0= ruleParam_Assign ) )
                    // InternalStructuredTextParser.g:2193:5: (lv_args_2_0= ruleParam_Assign )
                    {
                    // InternalStructuredTextParser.g:2193:5: (lv_args_2_0= ruleParam_Assign )
                    // InternalStructuredTextParser.g:2194:6: lv_args_2_0= ruleParam_Assign
                    {

                    						newCompositeNode(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_50);
                    lv_args_2_0=ruleParam_Assign();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFunc_CallRule());
                    						}
                    						add(
                    							current,
                    							"args",
                    							lv_args_2_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Param_Assign");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:2211:4: (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( (LA30_0==Comma) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // InternalStructuredTextParser.g:2212:5: otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) )
                    	    {
                    	    otherlv_3=(Token)match(input,Comma,FOLLOW_20); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunc_CallAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalStructuredTextParser.g:2216:5: ( (lv_args_4_0= ruleParam_Assign ) )
                    	    // InternalStructuredTextParser.g:2217:6: (lv_args_4_0= ruleParam_Assign )
                    	    {
                    	    // InternalStructuredTextParser.g:2217:6: (lv_args_4_0= ruleParam_Assign )
                    	    // InternalStructuredTextParser.g:2218:7: lv_args_4_0= ruleParam_Assign
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_50);
                    	    lv_args_4_0=ruleParam_Assign();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getFunc_CallRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"args",
                    	    								lv_args_4_0,
                    	    								"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Param_Assign");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop30;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getFunc_CallAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunc_Call"


    // $ANTLR start "entryRuleParam_Assign"
    // InternalStructuredTextParser.g:2245:1: entryRuleParam_Assign returns [EObject current=null] : iv_ruleParam_Assign= ruleParam_Assign EOF ;
    public final EObject entryRuleParam_Assign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam_Assign = null;


        try {
            // InternalStructuredTextParser.g:2245:53: (iv_ruleParam_Assign= ruleParam_Assign EOF )
            // InternalStructuredTextParser.g:2246:2: iv_ruleParam_Assign= ruleParam_Assign EOF
            {
             newCompositeNode(grammarAccess.getParam_AssignRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParam_Assign=ruleParam_Assign();

            state._fsp--;

             current =iv_ruleParam_Assign; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParam_Assign"


    // $ANTLR start "ruleParam_Assign"
    // InternalStructuredTextParser.g:2252:1: ruleParam_Assign returns [EObject current=null] : (this_Param_Assign_In_0= ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out ) ;
    public final EObject ruleParam_Assign() throws RecognitionException {
        EObject current = null;

        EObject this_Param_Assign_In_0 = null;

        EObject this_Param_Assign_Out_1 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2258:2: ( (this_Param_Assign_In_0= ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out ) )
            // InternalStructuredTextParser.g:2259:2: (this_Param_Assign_In_0= ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out )
            {
            // InternalStructuredTextParser.g:2259:2: (this_Param_Assign_In_0= ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out )
            int alt32=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA32_1 = input.LA(2);

                if ( (LA32_1==EqualsSignGreaterThanSign) ) {
                    alt32=2;
                }
                else if ( (LA32_1==EOF||(LA32_1>=B && LA32_1<=AND)||LA32_1==MOD||(LA32_1>=XOR && LA32_1<=AsteriskAsterisk)||(LA32_1>=ColonEqualsSign && LA32_1<=LessThanSignGreaterThanSign)||LA32_1==GreaterThanSignEqualsSign||LA32_1==OR||(LA32_1>=Ampersand && LA32_1<=Solidus)||(LA32_1>=LessThanSign && LA32_1<=GreaterThanSign)||LA32_1==LeftSquareBracket) ) {
                    alt32=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 32, 1, input);

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
            case FALSE:
            case LDATE:
            case LREAL:
            case LTIME:
            case UDINT:
            case ULINT:
            case USINT:
            case WCHAR:
            case BOOL:
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
            case INT:
            case LDT:
            case TOD:
            case DT:
            case LD:
            case LT:
            case LeftParenthesis:
            case PlusSign:
            case HyphenMinus:
            case T:
            case D_1:
            case RULE_BINARY_INT:
            case RULE_OCTAL_INT:
            case RULE_HEX_INT:
            case RULE_UNSIGNED_INT:
            case RULE_S_BYTE_CHAR_STR:
            case RULE_D_BYTE_CHAR_STR:
                {
                alt32=1;
                }
                break;
            case NOT:
                {
                int LA32_3 = input.LA(2);

                if ( (LA32_3==RULE_ID) ) {
                    int LA32_5 = input.LA(3);

                    if ( (LA32_5==EqualsSignGreaterThanSign) ) {
                        alt32=2;
                    }
                    else if ( (LA32_5==EOF||(LA32_5>=B && LA32_5<=AND)||LA32_5==MOD||(LA32_5>=XOR && LA32_5<=AsteriskAsterisk)||(LA32_5>=LessThanSignEqualsSign && LA32_5<=LessThanSignGreaterThanSign)||LA32_5==GreaterThanSignEqualsSign||LA32_5==OR||(LA32_5>=Ampersand && LA32_5<=Solidus)||(LA32_5>=LessThanSign && LA32_5<=GreaterThanSign)||LA32_5==LeftSquareBracket) ) {
                        alt32=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 32, 5, input);

                        throw nvae;
                    }
                }
                else if ( (LA32_3==TIME||LA32_3==DT||LA32_3==LT||LA32_3==LeftParenthesis||LA32_3==T) ) {
                    alt32=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 32, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }

            switch (alt32) {
                case 1 :
                    // InternalStructuredTextParser.g:2260:3: this_Param_Assign_In_0= ruleParam_Assign_In
                    {

                    			newCompositeNode(grammarAccess.getParam_AssignAccess().getParam_Assign_InParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Param_Assign_In_0=ruleParam_Assign_In();

                    state._fsp--;


                    			current = this_Param_Assign_In_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2269:3: this_Param_Assign_Out_1= ruleParam_Assign_Out
                    {

                    			newCompositeNode(grammarAccess.getParam_AssignAccess().getParam_Assign_OutParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Param_Assign_Out_1=ruleParam_Assign_Out();

                    state._fsp--;


                    			current = this_Param_Assign_Out_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParam_Assign"


    // $ANTLR start "entryRuleParam_Assign_In"
    // InternalStructuredTextParser.g:2281:1: entryRuleParam_Assign_In returns [EObject current=null] : iv_ruleParam_Assign_In= ruleParam_Assign_In EOF ;
    public final EObject entryRuleParam_Assign_In() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam_Assign_In = null;


        try {
            // InternalStructuredTextParser.g:2281:56: (iv_ruleParam_Assign_In= ruleParam_Assign_In EOF )
            // InternalStructuredTextParser.g:2282:2: iv_ruleParam_Assign_In= ruleParam_Assign_In EOF
            {
             newCompositeNode(grammarAccess.getParam_Assign_InRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParam_Assign_In=ruleParam_Assign_In();

            state._fsp--;

             current =iv_ruleParam_Assign_In; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParam_Assign_In"


    // $ANTLR start "ruleParam_Assign_In"
    // InternalStructuredTextParser.g:2288:1: ruleParam_Assign_In returns [EObject current=null] : ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) ) ;
    public final EObject ruleParam_Assign_In() throws RecognitionException {
        EObject current = null;

        Token lv_var_0_0=null;
        Token otherlv_1=null;
        EObject lv_expr_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2294:2: ( ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) ) )
            // InternalStructuredTextParser.g:2295:2: ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) )
            {
            // InternalStructuredTextParser.g:2295:2: ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) )
            // InternalStructuredTextParser.g:2296:3: ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) )
            {
            // InternalStructuredTextParser.g:2296:3: ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RULE_ID) ) {
                int LA33_1 = input.LA(2);

                if ( (LA33_1==ColonEqualsSign) ) {
                    alt33=1;
                }
            }
            switch (alt33) {
                case 1 :
                    // InternalStructuredTextParser.g:2297:4: ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign
                    {
                    // InternalStructuredTextParser.g:2297:4: ( (lv_var_0_0= RULE_ID ) )
                    // InternalStructuredTextParser.g:2298:5: (lv_var_0_0= RULE_ID )
                    {
                    // InternalStructuredTextParser.g:2298:5: (lv_var_0_0= RULE_ID )
                    // InternalStructuredTextParser.g:2299:6: lv_var_0_0= RULE_ID
                    {
                    lv_var_0_0=(Token)match(input,RULE_ID,FOLLOW_19); 

                    						newLeafNode(lv_var_0_0, grammarAccess.getParam_Assign_InAccess().getVarIDTerminalRuleCall_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getParam_Assign_InRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"var",
                    							lv_var_0_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_20); 

                    				newLeafNode(otherlv_1, grammarAccess.getParam_Assign_InAccess().getColonEqualsSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:2320:3: ( (lv_expr_2_0= ruleExpression ) )
            // InternalStructuredTextParser.g:2321:4: (lv_expr_2_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:2321:4: (lv_expr_2_0= ruleExpression )
            // InternalStructuredTextParser.g:2322:5: lv_expr_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getParam_Assign_InAccess().getExprExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_expr_2_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getParam_Assign_InRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParam_Assign_In"


    // $ANTLR start "entryRuleParam_Assign_Out"
    // InternalStructuredTextParser.g:2343:1: entryRuleParam_Assign_Out returns [EObject current=null] : iv_ruleParam_Assign_Out= ruleParam_Assign_Out EOF ;
    public final EObject entryRuleParam_Assign_Out() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam_Assign_Out = null;


        try {
            // InternalStructuredTextParser.g:2343:57: (iv_ruleParam_Assign_Out= ruleParam_Assign_Out EOF )
            // InternalStructuredTextParser.g:2344:2: iv_ruleParam_Assign_Out= ruleParam_Assign_Out EOF
            {
             newCompositeNode(grammarAccess.getParam_Assign_OutRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParam_Assign_Out=ruleParam_Assign_Out();

            state._fsp--;

             current =iv_ruleParam_Assign_Out; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParam_Assign_Out"


    // $ANTLR start "ruleParam_Assign_Out"
    // InternalStructuredTextParser.g:2350:1: ruleParam_Assign_Out returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) ) ;
    public final EObject ruleParam_Assign_Out() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token lv_var_1_0=null;
        Token otherlv_2=null;
        EObject lv_expr_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2356:2: ( ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) ) )
            // InternalStructuredTextParser.g:2357:2: ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) )
            {
            // InternalStructuredTextParser.g:2357:2: ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) )
            // InternalStructuredTextParser.g:2358:3: ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) )
            {
            // InternalStructuredTextParser.g:2358:3: ( (lv_not_0_0= NOT ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==NOT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalStructuredTextParser.g:2359:4: (lv_not_0_0= NOT )
                    {
                    // InternalStructuredTextParser.g:2359:4: (lv_not_0_0= NOT )
                    // InternalStructuredTextParser.g:2360:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_6); 

                    					newLeafNode(lv_not_0_0, grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getParam_Assign_OutRule());
                    					}
                    					setWithLastConsumed(current, "not", true, "NOT");
                    				

                    }


                    }
                    break;

            }

            // InternalStructuredTextParser.g:2372:3: ( (lv_var_1_0= RULE_ID ) )
            // InternalStructuredTextParser.g:2373:4: (lv_var_1_0= RULE_ID )
            {
            // InternalStructuredTextParser.g:2373:4: (lv_var_1_0= RULE_ID )
            // InternalStructuredTextParser.g:2374:5: lv_var_1_0= RULE_ID
            {
            lv_var_1_0=(Token)match(input,RULE_ID,FOLLOW_51); 

            					newLeafNode(lv_var_1_0, grammarAccess.getParam_Assign_OutAccess().getVarIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParam_Assign_OutRule());
            					}
            					setWithLastConsumed(
            						current,
            						"var",
            						lv_var_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_8); 

            			newLeafNode(otherlv_2, grammarAccess.getParam_Assign_OutAccess().getEqualsSignGreaterThanSignKeyword_2());
            		
            // InternalStructuredTextParser.g:2394:3: ( (lv_expr_3_0= ruleVariable ) )
            // InternalStructuredTextParser.g:2395:4: (lv_expr_3_0= ruleVariable )
            {
            // InternalStructuredTextParser.g:2395:4: (lv_expr_3_0= ruleVariable )
            // InternalStructuredTextParser.g:2396:5: lv_expr_3_0= ruleVariable
            {

            					newCompositeNode(grammarAccess.getParam_Assign_OutAccess().getExprVariableParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_expr_3_0=ruleVariable();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getParam_Assign_OutRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_3_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Variable");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParam_Assign_Out"


    // $ANTLR start "entryRuleVariable"
    // InternalStructuredTextParser.g:2417:1: entryRuleVariable returns [EObject current=null] : iv_ruleVariable= ruleVariable EOF ;
    public final EObject entryRuleVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable = null;


        try {
            // InternalStructuredTextParser.g:2417:49: (iv_ruleVariable= ruleVariable EOF )
            // InternalStructuredTextParser.g:2418:2: iv_ruleVariable= ruleVariable EOF
            {
             newCompositeNode(grammarAccess.getVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariable=ruleVariable();

            state._fsp--;

             current =iv_ruleVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable"


    // $ANTLR start "ruleVariable"
    // InternalStructuredTextParser.g:2424:1: ruleVariable returns [EObject current=null] : (this_Variable_Subscript_0= ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )? ) ;
    public final EObject ruleVariable() throws RecognitionException {
        EObject current = null;

        EObject this_Variable_Subscript_0 = null;

        EObject lv_part_1_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2430:2: ( (this_Variable_Subscript_0= ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )? ) )
            // InternalStructuredTextParser.g:2431:2: (this_Variable_Subscript_0= ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )? )
            {
            // InternalStructuredTextParser.g:2431:2: (this_Variable_Subscript_0= ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )? )
            // InternalStructuredTextParser.g:2432:3: this_Variable_Subscript_0= ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )?
            {

            			newCompositeNode(grammarAccess.getVariableAccess().getVariable_SubscriptParserRuleCall_0());
            		
            pushFollow(FOLLOW_52);
            this_Variable_Subscript_0=ruleVariable_Subscript();

            state._fsp--;


            			current = this_Variable_Subscript_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:2440:3: ( (lv_part_1_0= ruleMultibit_Part_Access ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( ((LA35_0>=B && LA35_0<=X)||LA35_0==FullStop) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalStructuredTextParser.g:2441:4: (lv_part_1_0= ruleMultibit_Part_Access )
                    {
                    // InternalStructuredTextParser.g:2441:4: (lv_part_1_0= ruleMultibit_Part_Access )
                    // InternalStructuredTextParser.g:2442:5: lv_part_1_0= ruleMultibit_Part_Access
                    {

                    					newCompositeNode(grammarAccess.getVariableAccess().getPartMultibit_Part_AccessParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_part_1_0=ruleMultibit_Part_Access();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getVariableRule());
                    					}
                    					set(
                    						current,
                    						"part",
                    						lv_part_1_0,
                    						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Multibit_Part_Access");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable"


    // $ANTLR start "entryRuleVariable_Subscript"
    // InternalStructuredTextParser.g:2463:1: entryRuleVariable_Subscript returns [EObject current=null] : iv_ruleVariable_Subscript= ruleVariable_Subscript EOF ;
    public final EObject entryRuleVariable_Subscript() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable_Subscript = null;


        try {
            // InternalStructuredTextParser.g:2463:59: (iv_ruleVariable_Subscript= ruleVariable_Subscript EOF )
            // InternalStructuredTextParser.g:2464:2: iv_ruleVariable_Subscript= ruleVariable_Subscript EOF
            {
             newCompositeNode(grammarAccess.getVariable_SubscriptRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariable_Subscript=ruleVariable_Subscript();

            state._fsp--;

             current =iv_ruleVariable_Subscript; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable_Subscript"


    // $ANTLR start "ruleVariable_Subscript"
    // InternalStructuredTextParser.g:2470:1: ruleVariable_Subscript returns [EObject current=null] : ( (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? ) ;
    public final EObject ruleVariable_Subscript() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject this_Variable_Primary_0 = null;

        EObject this_Variable_Adapter_1 = null;

        EObject lv_index_4_0 = null;

        EObject lv_index_6_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2476:2: ( ( (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? ) )
            // InternalStructuredTextParser.g:2477:2: ( (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? )
            {
            // InternalStructuredTextParser.g:2477:2: ( (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? )
            // InternalStructuredTextParser.g:2478:3: (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
            {
            // InternalStructuredTextParser.g:2478:3: (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter )
            int alt36=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA36_1 = input.LA(2);

                if ( (LA36_1==EOF||LA36_1==END_REPEAT||LA36_1==THEN||(LA36_1>=B && LA36_1<=AND)||LA36_1==MOD||(LA36_1>=XOR && LA36_1<=AsteriskAsterisk)||(LA36_1>=ColonEqualsSign && LA36_1<=LessThanSignGreaterThanSign)||LA36_1==GreaterThanSignEqualsSign||(LA36_1>=BY && LA36_1<=DO)||(LA36_1>=OF && LA36_1<=TO)||LA36_1==Ampersand||(LA36_1>=RightParenthesis && LA36_1<=HyphenMinus)||(LA36_1>=Solidus && LA36_1<=GreaterThanSign)||(LA36_1>=LeftSquareBracket && LA36_1<=RightSquareBracket)) ) {
                    alt36=1;
                }
                else if ( (LA36_1==FullStop) ) {
                    int LA36_6 = input.LA(3);

                    if ( (LA36_6==DT||LA36_6==LT||LA36_6==T||LA36_6==RULE_ID) ) {
                        alt36=2;
                    }
                    else if ( (LA36_6==RULE_UNSIGNED_INT) ) {
                        alt36=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 6, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 1, input);

                    throw nvae;
                }
                }
                break;
            case T:
                {
                int LA36_2 = input.LA(2);

                if ( (LA36_2==EOF||LA36_2==END_REPEAT||LA36_2==THEN||(LA36_2>=B && LA36_2<=AND)||LA36_2==MOD||(LA36_2>=XOR && LA36_2<=AsteriskAsterisk)||(LA36_2>=ColonEqualsSign && LA36_2<=LessThanSignGreaterThanSign)||LA36_2==GreaterThanSignEqualsSign||(LA36_2>=BY && LA36_2<=DO)||(LA36_2>=OF && LA36_2<=TO)||LA36_2==Ampersand||(LA36_2>=RightParenthesis && LA36_2<=HyphenMinus)||(LA36_2>=Solidus && LA36_2<=GreaterThanSign)||(LA36_2>=LeftSquareBracket && LA36_2<=RightSquareBracket)) ) {
                    alt36=1;
                }
                else if ( (LA36_2==FullStop) ) {
                    int LA36_6 = input.LA(3);

                    if ( (LA36_6==DT||LA36_6==LT||LA36_6==T||LA36_6==RULE_ID) ) {
                        alt36=2;
                    }
                    else if ( (LA36_6==RULE_UNSIGNED_INT) ) {
                        alt36=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 6, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 2, input);

                    throw nvae;
                }
                }
                break;
            case LT:
                {
                int LA36_3 = input.LA(2);

                if ( (LA36_3==EOF||LA36_3==END_REPEAT||LA36_3==THEN||(LA36_3>=B && LA36_3<=AND)||LA36_3==MOD||(LA36_3>=XOR && LA36_3<=AsteriskAsterisk)||(LA36_3>=ColonEqualsSign && LA36_3<=LessThanSignGreaterThanSign)||LA36_3==GreaterThanSignEqualsSign||(LA36_3>=BY && LA36_3<=DO)||(LA36_3>=OF && LA36_3<=TO)||LA36_3==Ampersand||(LA36_3>=RightParenthesis && LA36_3<=HyphenMinus)||(LA36_3>=Solidus && LA36_3<=GreaterThanSign)||(LA36_3>=LeftSquareBracket && LA36_3<=RightSquareBracket)) ) {
                    alt36=1;
                }
                else if ( (LA36_3==FullStop) ) {
                    int LA36_6 = input.LA(3);

                    if ( (LA36_6==DT||LA36_6==LT||LA36_6==T||LA36_6==RULE_ID) ) {
                        alt36=2;
                    }
                    else if ( (LA36_6==RULE_UNSIGNED_INT) ) {
                        alt36=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 6, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 3, input);

                    throw nvae;
                }
                }
                break;
            case DT:
                {
                int LA36_4 = input.LA(2);

                if ( (LA36_4==EOF||LA36_4==END_REPEAT||LA36_4==THEN||(LA36_4>=B && LA36_4<=AND)||LA36_4==MOD||(LA36_4>=XOR && LA36_4<=AsteriskAsterisk)||(LA36_4>=ColonEqualsSign && LA36_4<=LessThanSignGreaterThanSign)||LA36_4==GreaterThanSignEqualsSign||(LA36_4>=BY && LA36_4<=DO)||(LA36_4>=OF && LA36_4<=TO)||LA36_4==Ampersand||(LA36_4>=RightParenthesis && LA36_4<=HyphenMinus)||(LA36_4>=Solidus && LA36_4<=GreaterThanSign)||(LA36_4>=LeftSquareBracket && LA36_4<=RightSquareBracket)) ) {
                    alt36=1;
                }
                else if ( (LA36_4==FullStop) ) {
                    int LA36_6 = input.LA(3);

                    if ( (LA36_6==DT||LA36_6==LT||LA36_6==T||LA36_6==RULE_ID) ) {
                        alt36=2;
                    }
                    else if ( (LA36_6==RULE_UNSIGNED_INT) ) {
                        alt36=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 6, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // InternalStructuredTextParser.g:2479:4: this_Variable_Primary_0= ruleVariable_Primary
                    {

                    				newCompositeNode(grammarAccess.getVariable_SubscriptAccess().getVariable_PrimaryParserRuleCall_0_0());
                    			
                    pushFollow(FOLLOW_53);
                    this_Variable_Primary_0=ruleVariable_Primary();

                    state._fsp--;


                    				current = this_Variable_Primary_0;
                    				afterParserOrEnumRuleCall();
                    			

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2488:4: this_Variable_Adapter_1= ruleVariable_Adapter
                    {

                    				newCompositeNode(grammarAccess.getVariable_SubscriptAccess().getVariable_AdapterParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_53);
                    this_Variable_Adapter_1=ruleVariable_Adapter();

                    state._fsp--;


                    				current = this_Variable_Adapter_1;
                    				afterParserOrEnumRuleCall();
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:2497:3: ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==LeftSquareBracket) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalStructuredTextParser.g:2498:4: () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket
                    {
                    // InternalStructuredTextParser.g:2498:4: ()
                    // InternalStructuredTextParser.g:2499:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_3=(Token)match(input,LeftSquareBracket,FOLLOW_20); 

                    				newLeafNode(otherlv_3, grammarAccess.getVariable_SubscriptAccess().getLeftSquareBracketKeyword_1_1());
                    			
                    // InternalStructuredTextParser.g:2509:4: ( (lv_index_4_0= ruleExpression ) )
                    // InternalStructuredTextParser.g:2510:5: (lv_index_4_0= ruleExpression )
                    {
                    // InternalStructuredTextParser.g:2510:5: (lv_index_4_0= ruleExpression )
                    // InternalStructuredTextParser.g:2511:6: lv_index_4_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_54);
                    lv_index_4_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVariable_SubscriptRule());
                    						}
                    						add(
                    							current,
                    							"index",
                    							lv_index_4_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:2528:4: (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )*
                    loop37:
                    do {
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( (LA37_0==Comma) ) {
                            alt37=1;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // InternalStructuredTextParser.g:2529:5: otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) )
                    	    {
                    	    otherlv_5=(Token)match(input,Comma,FOLLOW_20); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getVariable_SubscriptAccess().getCommaKeyword_1_3_0());
                    	    				
                    	    // InternalStructuredTextParser.g:2533:5: ( (lv_index_6_0= ruleExpression ) )
                    	    // InternalStructuredTextParser.g:2534:6: (lv_index_6_0= ruleExpression )
                    	    {
                    	    // InternalStructuredTextParser.g:2534:6: (lv_index_6_0= ruleExpression )
                    	    // InternalStructuredTextParser.g:2535:7: lv_index_6_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_54);
                    	    lv_index_6_0=ruleExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getVariable_SubscriptRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"index",
                    	    								lv_index_6_0,
                    	    								"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,RightSquareBracket,FOLLOW_2); 

                    				newLeafNode(otherlv_7, grammarAccess.getVariable_SubscriptAccess().getRightSquareBracketKeyword_1_4());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable_Subscript"


    // $ANTLR start "entryRuleVariable_Adapter"
    // InternalStructuredTextParser.g:2562:1: entryRuleVariable_Adapter returns [EObject current=null] : iv_ruleVariable_Adapter= ruleVariable_Adapter EOF ;
    public final EObject entryRuleVariable_Adapter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable_Adapter = null;


        try {
            // InternalStructuredTextParser.g:2562:57: (iv_ruleVariable_Adapter= ruleVariable_Adapter EOF )
            // InternalStructuredTextParser.g:2563:2: iv_ruleVariable_Adapter= ruleVariable_Adapter EOF
            {
             newCompositeNode(grammarAccess.getVariable_AdapterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariable_Adapter=ruleVariable_Adapter();

            state._fsp--;

             current =iv_ruleVariable_Adapter; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable_Adapter"


    // $ANTLR start "ruleVariable_Adapter"
    // InternalStructuredTextParser.g:2569:1: ruleVariable_Adapter returns [EObject current=null] : ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) ) ) ;
    public final EObject ruleVariable_Adapter() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2575:2: ( ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) ) ) )
            // InternalStructuredTextParser.g:2576:2: ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) ) )
            {
            // InternalStructuredTextParser.g:2576:2: ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:2577:3: () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:2577:3: ()
            // InternalStructuredTextParser.g:2578:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getVariable_AdapterAccess().getAdapterVariableAction_0(),
            					current);
            			

            }

            // InternalStructuredTextParser.g:2584:3: ( ( ruleAdapter_Name ) )
            // InternalStructuredTextParser.g:2585:4: ( ruleAdapter_Name )
            {
            // InternalStructuredTextParser.g:2585:4: ( ruleAdapter_Name )
            // InternalStructuredTextParser.g:2586:5: ruleAdapter_Name
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariable_AdapterRule());
            					}
            				

            					newCompositeNode(grammarAccess.getVariable_AdapterAccess().getAdapterAdapterDeclarationCrossReference_1_0());
            				
            pushFollow(FOLLOW_55);
            ruleAdapter_Name();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,FullStop,FOLLOW_8); 

            			newLeafNode(otherlv_2, grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_2());
            		
            // InternalStructuredTextParser.g:2604:3: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:2605:4: ( ruleVariable_Name )
            {
            // InternalStructuredTextParser.g:2605:4: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:2606:5: ruleVariable_Name
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariable_AdapterRule());
            					}
            				

            					newCompositeNode(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_3_0());
            				
            pushFollow(FOLLOW_2);
            ruleVariable_Name();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable_Adapter"


    // $ANTLR start "entryRuleMultibit_Part_Access"
    // InternalStructuredTextParser.g:2624:1: entryRuleMultibit_Part_Access returns [EObject current=null] : iv_ruleMultibit_Part_Access= ruleMultibit_Part_Access EOF ;
    public final EObject entryRuleMultibit_Part_Access() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultibit_Part_Access = null;


        try {
            // InternalStructuredTextParser.g:2624:61: (iv_ruleMultibit_Part_Access= ruleMultibit_Part_Access EOF )
            // InternalStructuredTextParser.g:2625:2: iv_ruleMultibit_Part_Access= ruleMultibit_Part_Access EOF
            {
             newCompositeNode(grammarAccess.getMultibit_Part_AccessRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMultibit_Part_Access=ruleMultibit_Part_Access();

            state._fsp--;

             current =iv_ruleMultibit_Part_Access; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultibit_Part_Access"


    // $ANTLR start "ruleMultibit_Part_Access"
    // InternalStructuredTextParser.g:2631:1: ruleMultibit_Part_Access returns [EObject current=null] : ( ( ( (lv_dwordaccess_0_0= D ) ) ( (lv_index_1_0= rulePartial_Value ) ) ) | ( ( (lv_wordaccess_2_0= W ) ) ( (lv_index_3_0= rulePartial_Value ) ) ) | ( ( (lv_byteaccess_4_0= B ) ) ( (lv_index_5_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_6_0= X ) ) ( (lv_index_7_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_8_0= FullStop ) ) ( (lv_index_9_0= rulePartial_Value ) ) ) ) ;
    public final EObject ruleMultibit_Part_Access() throws RecognitionException {
        EObject current = null;

        Token lv_dwordaccess_0_0=null;
        Token lv_wordaccess_2_0=null;
        Token lv_byteaccess_4_0=null;
        Token lv_bitaccess_6_0=null;
        Token lv_bitaccess_8_0=null;
        AntlrDatatypeRuleToken lv_index_1_0 = null;

        AntlrDatatypeRuleToken lv_index_3_0 = null;

        AntlrDatatypeRuleToken lv_index_5_0 = null;

        AntlrDatatypeRuleToken lv_index_7_0 = null;

        AntlrDatatypeRuleToken lv_index_9_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2637:2: ( ( ( ( (lv_dwordaccess_0_0= D ) ) ( (lv_index_1_0= rulePartial_Value ) ) ) | ( ( (lv_wordaccess_2_0= W ) ) ( (lv_index_3_0= rulePartial_Value ) ) ) | ( ( (lv_byteaccess_4_0= B ) ) ( (lv_index_5_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_6_0= X ) ) ( (lv_index_7_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_8_0= FullStop ) ) ( (lv_index_9_0= rulePartial_Value ) ) ) ) )
            // InternalStructuredTextParser.g:2638:2: ( ( ( (lv_dwordaccess_0_0= D ) ) ( (lv_index_1_0= rulePartial_Value ) ) ) | ( ( (lv_wordaccess_2_0= W ) ) ( (lv_index_3_0= rulePartial_Value ) ) ) | ( ( (lv_byteaccess_4_0= B ) ) ( (lv_index_5_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_6_0= X ) ) ( (lv_index_7_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_8_0= FullStop ) ) ( (lv_index_9_0= rulePartial_Value ) ) ) )
            {
            // InternalStructuredTextParser.g:2638:2: ( ( ( (lv_dwordaccess_0_0= D ) ) ( (lv_index_1_0= rulePartial_Value ) ) ) | ( ( (lv_wordaccess_2_0= W ) ) ( (lv_index_3_0= rulePartial_Value ) ) ) | ( ( (lv_byteaccess_4_0= B ) ) ( (lv_index_5_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_6_0= X ) ) ( (lv_index_7_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_8_0= FullStop ) ) ( (lv_index_9_0= rulePartial_Value ) ) ) )
            int alt39=5;
            switch ( input.LA(1) ) {
            case D:
                {
                alt39=1;
                }
                break;
            case W:
                {
                alt39=2;
                }
                break;
            case B:
                {
                alt39=3;
                }
                break;
            case X:
                {
                alt39=4;
                }
                break;
            case FullStop:
                {
                alt39=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }

            switch (alt39) {
                case 1 :
                    // InternalStructuredTextParser.g:2639:3: ( ( (lv_dwordaccess_0_0= D ) ) ( (lv_index_1_0= rulePartial_Value ) ) )
                    {
                    // InternalStructuredTextParser.g:2639:3: ( ( (lv_dwordaccess_0_0= D ) ) ( (lv_index_1_0= rulePartial_Value ) ) )
                    // InternalStructuredTextParser.g:2640:4: ( (lv_dwordaccess_0_0= D ) ) ( (lv_index_1_0= rulePartial_Value ) )
                    {
                    // InternalStructuredTextParser.g:2640:4: ( (lv_dwordaccess_0_0= D ) )
                    // InternalStructuredTextParser.g:2641:5: (lv_dwordaccess_0_0= D )
                    {
                    // InternalStructuredTextParser.g:2641:5: (lv_dwordaccess_0_0= D )
                    // InternalStructuredTextParser.g:2642:6: lv_dwordaccess_0_0= D
                    {
                    lv_dwordaccess_0_0=(Token)match(input,D,FOLLOW_12); 

                    						newLeafNode(lv_dwordaccess_0_0, grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						setWithLastConsumed(current, "dwordaccess", true, ".%D");
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:2654:4: ( (lv_index_1_0= rulePartial_Value ) )
                    // InternalStructuredTextParser.g:2655:5: (lv_index_1_0= rulePartial_Value )
                    {
                    // InternalStructuredTextParser.g:2655:5: (lv_index_1_0= rulePartial_Value )
                    // InternalStructuredTextParser.g:2656:6: lv_index_1_0= rulePartial_Value
                    {

                    						newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_index_1_0=rulePartial_Value();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						set(
                    							current,
                    							"index",
                    							lv_index_1_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2675:3: ( ( (lv_wordaccess_2_0= W ) ) ( (lv_index_3_0= rulePartial_Value ) ) )
                    {
                    // InternalStructuredTextParser.g:2675:3: ( ( (lv_wordaccess_2_0= W ) ) ( (lv_index_3_0= rulePartial_Value ) ) )
                    // InternalStructuredTextParser.g:2676:4: ( (lv_wordaccess_2_0= W ) ) ( (lv_index_3_0= rulePartial_Value ) )
                    {
                    // InternalStructuredTextParser.g:2676:4: ( (lv_wordaccess_2_0= W ) )
                    // InternalStructuredTextParser.g:2677:5: (lv_wordaccess_2_0= W )
                    {
                    // InternalStructuredTextParser.g:2677:5: (lv_wordaccess_2_0= W )
                    // InternalStructuredTextParser.g:2678:6: lv_wordaccess_2_0= W
                    {
                    lv_wordaccess_2_0=(Token)match(input,W,FOLLOW_12); 

                    						newLeafNode(lv_wordaccess_2_0, grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						setWithLastConsumed(current, "wordaccess", true, ".%W");
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:2690:4: ( (lv_index_3_0= rulePartial_Value ) )
                    // InternalStructuredTextParser.g:2691:5: (lv_index_3_0= rulePartial_Value )
                    {
                    // InternalStructuredTextParser.g:2691:5: (lv_index_3_0= rulePartial_Value )
                    // InternalStructuredTextParser.g:2692:6: lv_index_3_0= rulePartial_Value
                    {

                    						newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_index_3_0=rulePartial_Value();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						set(
                    							current,
                    							"index",
                    							lv_index_3_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2711:3: ( ( (lv_byteaccess_4_0= B ) ) ( (lv_index_5_0= rulePartial_Value ) ) )
                    {
                    // InternalStructuredTextParser.g:2711:3: ( ( (lv_byteaccess_4_0= B ) ) ( (lv_index_5_0= rulePartial_Value ) ) )
                    // InternalStructuredTextParser.g:2712:4: ( (lv_byteaccess_4_0= B ) ) ( (lv_index_5_0= rulePartial_Value ) )
                    {
                    // InternalStructuredTextParser.g:2712:4: ( (lv_byteaccess_4_0= B ) )
                    // InternalStructuredTextParser.g:2713:5: (lv_byteaccess_4_0= B )
                    {
                    // InternalStructuredTextParser.g:2713:5: (lv_byteaccess_4_0= B )
                    // InternalStructuredTextParser.g:2714:6: lv_byteaccess_4_0= B
                    {
                    lv_byteaccess_4_0=(Token)match(input,B,FOLLOW_12); 

                    						newLeafNode(lv_byteaccess_4_0, grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						setWithLastConsumed(current, "byteaccess", true, ".%B");
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:2726:4: ( (lv_index_5_0= rulePartial_Value ) )
                    // InternalStructuredTextParser.g:2727:5: (lv_index_5_0= rulePartial_Value )
                    {
                    // InternalStructuredTextParser.g:2727:5: (lv_index_5_0= rulePartial_Value )
                    // InternalStructuredTextParser.g:2728:6: lv_index_5_0= rulePartial_Value
                    {

                    						newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_index_5_0=rulePartial_Value();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						set(
                    							current,
                    							"index",
                    							lv_index_5_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2747:3: ( ( (lv_bitaccess_6_0= X ) ) ( (lv_index_7_0= rulePartial_Value ) ) )
                    {
                    // InternalStructuredTextParser.g:2747:3: ( ( (lv_bitaccess_6_0= X ) ) ( (lv_index_7_0= rulePartial_Value ) ) )
                    // InternalStructuredTextParser.g:2748:4: ( (lv_bitaccess_6_0= X ) ) ( (lv_index_7_0= rulePartial_Value ) )
                    {
                    // InternalStructuredTextParser.g:2748:4: ( (lv_bitaccess_6_0= X ) )
                    // InternalStructuredTextParser.g:2749:5: (lv_bitaccess_6_0= X )
                    {
                    // InternalStructuredTextParser.g:2749:5: (lv_bitaccess_6_0= X )
                    // InternalStructuredTextParser.g:2750:6: lv_bitaccess_6_0= X
                    {
                    lv_bitaccess_6_0=(Token)match(input,X,FOLLOW_12); 

                    						newLeafNode(lv_bitaccess_6_0, grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						setWithLastConsumed(current, "bitaccess", true, ".%X");
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:2762:4: ( (lv_index_7_0= rulePartial_Value ) )
                    // InternalStructuredTextParser.g:2763:5: (lv_index_7_0= rulePartial_Value )
                    {
                    // InternalStructuredTextParser.g:2763:5: (lv_index_7_0= rulePartial_Value )
                    // InternalStructuredTextParser.g:2764:6: lv_index_7_0= rulePartial_Value
                    {

                    						newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_index_7_0=rulePartial_Value();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						set(
                    							current,
                    							"index",
                    							lv_index_7_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:2783:3: ( ( (lv_bitaccess_8_0= FullStop ) ) ( (lv_index_9_0= rulePartial_Value ) ) )
                    {
                    // InternalStructuredTextParser.g:2783:3: ( ( (lv_bitaccess_8_0= FullStop ) ) ( (lv_index_9_0= rulePartial_Value ) ) )
                    // InternalStructuredTextParser.g:2784:4: ( (lv_bitaccess_8_0= FullStop ) ) ( (lv_index_9_0= rulePartial_Value ) )
                    {
                    // InternalStructuredTextParser.g:2784:4: ( (lv_bitaccess_8_0= FullStop ) )
                    // InternalStructuredTextParser.g:2785:5: (lv_bitaccess_8_0= FullStop )
                    {
                    // InternalStructuredTextParser.g:2785:5: (lv_bitaccess_8_0= FullStop )
                    // InternalStructuredTextParser.g:2786:6: lv_bitaccess_8_0= FullStop
                    {
                    lv_bitaccess_8_0=(Token)match(input,FullStop,FOLLOW_12); 

                    						newLeafNode(lv_bitaccess_8_0, grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						setWithLastConsumed(current, "bitaccess", true, ".");
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:2798:4: ( (lv_index_9_0= rulePartial_Value ) )
                    // InternalStructuredTextParser.g:2799:5: (lv_index_9_0= rulePartial_Value )
                    {
                    // InternalStructuredTextParser.g:2799:5: (lv_index_9_0= rulePartial_Value )
                    // InternalStructuredTextParser.g:2800:6: lv_index_9_0= rulePartial_Value
                    {

                    						newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_index_9_0=rulePartial_Value();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
                    						}
                    						set(
                    							current,
                    							"index",
                    							lv_index_9_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultibit_Part_Access"


    // $ANTLR start "entryRuleAdapter_Name"
    // InternalStructuredTextParser.g:2822:1: entryRuleAdapter_Name returns [String current=null] : iv_ruleAdapter_Name= ruleAdapter_Name EOF ;
    public final String entryRuleAdapter_Name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAdapter_Name = null;


        try {
            // InternalStructuredTextParser.g:2822:52: (iv_ruleAdapter_Name= ruleAdapter_Name EOF )
            // InternalStructuredTextParser.g:2823:2: iv_ruleAdapter_Name= ruleAdapter_Name EOF
            {
             newCompositeNode(grammarAccess.getAdapter_NameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAdapter_Name=ruleAdapter_Name();

            state._fsp--;

             current =iv_ruleAdapter_Name.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAdapter_Name"


    // $ANTLR start "ruleAdapter_Name"
    // InternalStructuredTextParser.g:2829:1: ruleAdapter_Name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT ) ;
    public final AntlrDatatypeRuleToken ruleAdapter_Name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2835:2: ( (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT ) )
            // InternalStructuredTextParser.g:2836:2: (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT )
            {
            // InternalStructuredTextParser.g:2836:2: (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT )
            int alt40=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt40=1;
                }
                break;
            case T:
                {
                alt40=2;
                }
                break;
            case LT:
                {
                alt40=3;
                }
                break;
            case DT:
                {
                alt40=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // InternalStructuredTextParser.g:2837:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2845:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAdapter_NameAccess().getTKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2851:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAdapter_NameAccess().getLTKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2857:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAdapter_NameAccess().getDTKeyword_3());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAdapter_Name"


    // $ANTLR start "entryRuleVariable_Primary"
    // InternalStructuredTextParser.g:2866:1: entryRuleVariable_Primary returns [EObject current=null] : iv_ruleVariable_Primary= ruleVariable_Primary EOF ;
    public final EObject entryRuleVariable_Primary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable_Primary = null;


        try {
            // InternalStructuredTextParser.g:2866:57: (iv_ruleVariable_Primary= ruleVariable_Primary EOF )
            // InternalStructuredTextParser.g:2867:2: iv_ruleVariable_Primary= ruleVariable_Primary EOF
            {
             newCompositeNode(grammarAccess.getVariable_PrimaryRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariable_Primary=ruleVariable_Primary();

            state._fsp--;

             current =iv_ruleVariable_Primary; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable_Primary"


    // $ANTLR start "ruleVariable_Primary"
    // InternalStructuredTextParser.g:2873:1: ruleVariable_Primary returns [EObject current=null] : ( ( ruleVariable_Name ) ) ;
    public final EObject ruleVariable_Primary() throws RecognitionException {
        EObject current = null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2879:2: ( ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:2880:2: ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:2880:2: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:2881:3: ( ruleVariable_Name )
            {
            // InternalStructuredTextParser.g:2881:3: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:2882:4: ruleVariable_Name
            {

            				if (current==null) {
            					current = createModelElement(grammarAccess.getVariable_PrimaryRule());
            				}
            			

            				newCompositeNode(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationCrossReference_0());
            			
            pushFollow(FOLLOW_2);
            ruleVariable_Name();

            state._fsp--;


            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable_Primary"


    // $ANTLR start "entryRuleVariable_Name"
    // InternalStructuredTextParser.g:2899:1: entryRuleVariable_Name returns [String current=null] : iv_ruleVariable_Name= ruleVariable_Name EOF ;
    public final String entryRuleVariable_Name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVariable_Name = null;


        try {
            // InternalStructuredTextParser.g:2899:53: (iv_ruleVariable_Name= ruleVariable_Name EOF )
            // InternalStructuredTextParser.g:2900:2: iv_ruleVariable_Name= ruleVariable_Name EOF
            {
             newCompositeNode(grammarAccess.getVariable_NameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariable_Name=ruleVariable_Name();

            state._fsp--;

             current =iv_ruleVariable_Name.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable_Name"


    // $ANTLR start "ruleVariable_Name"
    // InternalStructuredTextParser.g:2906:1: ruleVariable_Name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT ) ;
    public final AntlrDatatypeRuleToken ruleVariable_Name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2912:2: ( (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT ) )
            // InternalStructuredTextParser.g:2913:2: (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT )
            {
            // InternalStructuredTextParser.g:2913:2: (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT )
            int alt41=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt41=1;
                }
                break;
            case T:
                {
                alt41=2;
                }
                break;
            case LT:
                {
                alt41=3;
                }
                break;
            case DT:
                {
                alt41=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }

            switch (alt41) {
                case 1 :
                    // InternalStructuredTextParser.g:2914:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2922:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVariable_NameAccess().getTKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2928:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVariable_NameAccess().getLTKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2934:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVariable_NameAccess().getDTKeyword_3());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable_Name"


    // $ANTLR start "entryRuleConstant"
    // InternalStructuredTextParser.g:2943:1: entryRuleConstant returns [EObject current=null] : iv_ruleConstant= ruleConstant EOF ;
    public final EObject entryRuleConstant() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstant = null;


        try {
            // InternalStructuredTextParser.g:2943:49: (iv_ruleConstant= ruleConstant EOF )
            // InternalStructuredTextParser.g:2944:2: iv_ruleConstant= ruleConstant EOF
            {
             newCompositeNode(grammarAccess.getConstantRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConstant=ruleConstant();

            state._fsp--;

             current =iv_ruleConstant; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConstant"


    // $ANTLR start "ruleConstant"
    // InternalStructuredTextParser.g:2950:1: ruleConstant returns [EObject current=null] : (this_Numeric_Literal_0= ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal ) ;
    public final EObject ruleConstant() throws RecognitionException {
        EObject current = null;

        EObject this_Numeric_Literal_0 = null;

        EObject this_Char_Literal_1 = null;

        EObject this_Time_Literal_2 = null;

        EObject this_Bool_Literal_3 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2956:2: ( (this_Numeric_Literal_0= ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal ) )
            // InternalStructuredTextParser.g:2957:2: (this_Numeric_Literal_0= ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal )
            {
            // InternalStructuredTextParser.g:2957:2: (this_Numeric_Literal_0= ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal )
            int alt42=4;
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
            case RULE_BINARY_INT:
            case RULE_OCTAL_INT:
            case RULE_HEX_INT:
            case RULE_UNSIGNED_INT:
                {
                alt42=1;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_S_BYTE_CHAR_STR:
            case RULE_D_BYTE_CHAR_STR:
                {
                alt42=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LDATE:
            case LTIME:
            case DATE:
            case LTOD:
            case TIME:
            case LDT:
            case TOD:
            case DT:
            case LD:
            case LT:
            case T:
            case D_1:
                {
                alt42=3;
                }
                break;
            case FALSE:
            case BOOL:
            case TRUE:
                {
                alt42=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }

            switch (alt42) {
                case 1 :
                    // InternalStructuredTextParser.g:2958:3: this_Numeric_Literal_0= ruleNumeric_Literal
                    {

                    			newCompositeNode(grammarAccess.getConstantAccess().getNumeric_LiteralParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Numeric_Literal_0=ruleNumeric_Literal();

                    state._fsp--;


                    			current = this_Numeric_Literal_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2967:3: this_Char_Literal_1= ruleChar_Literal
                    {

                    			newCompositeNode(grammarAccess.getConstantAccess().getChar_LiteralParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Char_Literal_1=ruleChar_Literal();

                    state._fsp--;


                    			current = this_Char_Literal_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2976:3: this_Time_Literal_2= ruleTime_Literal
                    {

                    			newCompositeNode(grammarAccess.getConstantAccess().getTime_LiteralParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Time_Literal_2=ruleTime_Literal();

                    state._fsp--;


                    			current = this_Time_Literal_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2985:3: this_Bool_Literal_3= ruleBool_Literal
                    {

                    			newCompositeNode(grammarAccess.getConstantAccess().getBool_LiteralParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Bool_Literal_3=ruleBool_Literal();

                    state._fsp--;


                    			current = this_Bool_Literal_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConstant"


    // $ANTLR start "entryRuleNumeric_Literal"
    // InternalStructuredTextParser.g:2997:1: entryRuleNumeric_Literal returns [EObject current=null] : iv_ruleNumeric_Literal= ruleNumeric_Literal EOF ;
    public final EObject entryRuleNumeric_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumeric_Literal = null;


        try {
            // InternalStructuredTextParser.g:2997:56: (iv_ruleNumeric_Literal= ruleNumeric_Literal EOF )
            // InternalStructuredTextParser.g:2998:2: iv_ruleNumeric_Literal= ruleNumeric_Literal EOF
            {
             newCompositeNode(grammarAccess.getNumeric_LiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNumeric_Literal=ruleNumeric_Literal();

            state._fsp--;

             current =iv_ruleNumeric_Literal; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumeric_Literal"


    // $ANTLR start "ruleNumeric_Literal"
    // InternalStructuredTextParser.g:3004:1: ruleNumeric_Literal returns [EObject current=null] : (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1= ruleReal_Literal ) ;
    public final EObject ruleNumeric_Literal() throws RecognitionException {
        EObject current = null;

        EObject this_Int_Literal_0 = null;

        EObject this_Real_Literal_1 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3010:2: ( (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1= ruleReal_Literal ) )
            // InternalStructuredTextParser.g:3011:2: (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1= ruleReal_Literal )
            {
            // InternalStructuredTextParser.g:3011:2: (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1= ruleReal_Literal )
            int alt43=2;
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
                alt43=1;
                }
                break;
            case PlusSign:
                {
                int LA43_2 = input.LA(2);

                if ( (LA43_2==RULE_UNSIGNED_INT) ) {
                    int LA43_4 = input.LA(3);

                    if ( (LA43_4==FullStop) ) {
                        alt43=2;
                    }
                    else if ( (LA43_4==EOF||LA43_4==END_REPEAT||LA43_4==THEN||LA43_4==AND||LA43_4==MOD||(LA43_4>=XOR && LA43_4<=AsteriskAsterisk)||(LA43_4>=LessThanSignEqualsSign && LA43_4<=LessThanSignGreaterThanSign)||LA43_4==GreaterThanSignEqualsSign||(LA43_4>=BY && LA43_4<=DO)||(LA43_4>=OF && LA43_4<=TO)||LA43_4==Ampersand||(LA43_4>=RightParenthesis && LA43_4<=HyphenMinus)||(LA43_4>=Solidus && LA43_4<=GreaterThanSign)||LA43_4==RightSquareBracket) ) {
                        alt43=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 43, 4, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 2, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA43_3 = input.LA(2);

                if ( (LA43_3==RULE_UNSIGNED_INT) ) {
                    int LA43_4 = input.LA(3);

                    if ( (LA43_4==FullStop) ) {
                        alt43=2;
                    }
                    else if ( (LA43_4==EOF||LA43_4==END_REPEAT||LA43_4==THEN||LA43_4==AND||LA43_4==MOD||(LA43_4>=XOR && LA43_4<=AsteriskAsterisk)||(LA43_4>=LessThanSignEqualsSign && LA43_4<=LessThanSignGreaterThanSign)||LA43_4==GreaterThanSignEqualsSign||(LA43_4>=BY && LA43_4<=DO)||(LA43_4>=OF && LA43_4<=TO)||LA43_4==Ampersand||(LA43_4>=RightParenthesis && LA43_4<=HyphenMinus)||(LA43_4>=Solidus && LA43_4<=GreaterThanSign)||LA43_4==RightSquareBracket) ) {
                        alt43=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 43, 4, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_UNSIGNED_INT:
                {
                int LA43_4 = input.LA(2);

                if ( (LA43_4==FullStop) ) {
                    alt43=2;
                }
                else if ( (LA43_4==EOF||LA43_4==END_REPEAT||LA43_4==THEN||LA43_4==AND||LA43_4==MOD||(LA43_4>=XOR && LA43_4<=AsteriskAsterisk)||(LA43_4>=LessThanSignEqualsSign && LA43_4<=LessThanSignGreaterThanSign)||LA43_4==GreaterThanSignEqualsSign||(LA43_4>=BY && LA43_4<=DO)||(LA43_4>=OF && LA43_4<=TO)||LA43_4==Ampersand||(LA43_4>=RightParenthesis && LA43_4<=HyphenMinus)||(LA43_4>=Solidus && LA43_4<=GreaterThanSign)||LA43_4==RightSquareBracket) ) {
                    alt43=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 4, input);

                    throw nvae;
                }
                }
                break;
            case LREAL:
            case REAL:
                {
                alt43=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // InternalStructuredTextParser.g:3012:3: this_Int_Literal_0= ruleInt_Literal
                    {

                    			newCompositeNode(grammarAccess.getNumeric_LiteralAccess().getInt_LiteralParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Int_Literal_0=ruleInt_Literal();

                    state._fsp--;


                    			current = this_Int_Literal_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3021:3: this_Real_Literal_1= ruleReal_Literal
                    {

                    			newCompositeNode(grammarAccess.getNumeric_LiteralAccess().getReal_LiteralParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Real_Literal_1=ruleReal_Literal();

                    state._fsp--;


                    			current = this_Real_Literal_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNumeric_Literal"


    // $ANTLR start "entryRuleInt_Literal"
    // InternalStructuredTextParser.g:3033:1: entryRuleInt_Literal returns [EObject current=null] : iv_ruleInt_Literal= ruleInt_Literal EOF ;
    public final EObject entryRuleInt_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInt_Literal = null;


        try {
            // InternalStructuredTextParser.g:3033:52: (iv_ruleInt_Literal= ruleInt_Literal EOF )
            // InternalStructuredTextParser.g:3034:2: iv_ruleInt_Literal= ruleInt_Literal EOF
            {
             newCompositeNode(grammarAccess.getInt_LiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInt_Literal=ruleInt_Literal();

            state._fsp--;

             current =iv_ruleInt_Literal; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInt_Literal"


    // $ANTLR start "ruleInt_Literal"
    // InternalStructuredTextParser.g:3040:1: ruleInt_Literal returns [EObject current=null] : ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) ) ) ;
    public final EObject ruleInt_Literal() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_2=null;
        Token lv_value_2_3=null;
        Token lv_value_2_4=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_1 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3046:2: ( ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) ) ) )
            // InternalStructuredTextParser.g:3047:2: ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) ) )
            {
            // InternalStructuredTextParser.g:3047:2: ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) ) )
            // InternalStructuredTextParser.g:3048:3: ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) )
            {
            // InternalStructuredTextParser.g:3048:3: ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( ((LA44_0>=UDINT && LA44_0<=ULINT)||LA44_0==USINT||LA44_0==DINT||LA44_0==LINT||LA44_0==SINT||LA44_0==UINT||LA44_0==INT) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalStructuredTextParser.g:3049:4: ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign
                    {
                    // InternalStructuredTextParser.g:3049:4: ( (lv_type_0_0= ruleInt_Type_Name ) )
                    // InternalStructuredTextParser.g:3050:5: (lv_type_0_0= ruleInt_Type_Name )
                    {
                    // InternalStructuredTextParser.g:3050:5: (lv_type_0_0= ruleInt_Type_Name )
                    // InternalStructuredTextParser.g:3051:6: lv_type_0_0= ruleInt_Type_Name
                    {

                    						newCompositeNode(grammarAccess.getInt_LiteralAccess().getTypeInt_Type_NameEnumRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_56);
                    lv_type_0_0=ruleInt_Type_Name();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getInt_LiteralRule());
                    						}
                    						set(
                    							current,
                    							"type",
                    							lv_type_0_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Int_Type_Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_57); 

                    				newLeafNode(otherlv_1, grammarAccess.getInt_LiteralAccess().getNumberSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:3073:3: ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) )
            // InternalStructuredTextParser.g:3074:4: ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) )
            {
            // InternalStructuredTextParser.g:3074:4: ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) )
            // InternalStructuredTextParser.g:3075:5: (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT )
            {
            // InternalStructuredTextParser.g:3075:5: (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT )
            int alt45=4;
            switch ( input.LA(1) ) {
            case PlusSign:
            case HyphenMinus:
            case RULE_UNSIGNED_INT:
                {
                alt45=1;
                }
                break;
            case RULE_BINARY_INT:
                {
                alt45=2;
                }
                break;
            case RULE_OCTAL_INT:
                {
                alt45=3;
                }
                break;
            case RULE_HEX_INT:
                {
                alt45=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }

            switch (alt45) {
                case 1 :
                    // InternalStructuredTextParser.g:3076:6: lv_value_2_1= ruleSigned_Int
                    {

                    						newCompositeNode(grammarAccess.getInt_LiteralAccess().getValueSigned_IntParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_value_2_1=ruleSigned_Int();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getInt_LiteralRule());
                    						}
                    						set(
                    							current,
                    							"value",
                    							lv_value_2_1,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Signed_Int");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3092:6: lv_value_2_2= RULE_BINARY_INT
                    {
                    lv_value_2_2=(Token)match(input,RULE_BINARY_INT,FOLLOW_2); 

                    						newLeafNode(lv_value_2_2, grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getInt_LiteralRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_2_2,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.BINARY_INT");
                    					

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:3107:6: lv_value_2_3= RULE_OCTAL_INT
                    {
                    lv_value_2_3=(Token)match(input,RULE_OCTAL_INT,FOLLOW_2); 

                    						newLeafNode(lv_value_2_3, grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getInt_LiteralRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_2_3,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.OCTAL_INT");
                    					

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:3122:6: lv_value_2_4= RULE_HEX_INT
                    {
                    lv_value_2_4=(Token)match(input,RULE_HEX_INT,FOLLOW_2); 

                    						newLeafNode(lv_value_2_4, grammarAccess.getInt_LiteralAccess().getValueHEX_INTTerminalRuleCall_1_0_3());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getInt_LiteralRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_2_4,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.HEX_INT");
                    					

                    }
                    break;

            }


            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInt_Literal"


    // $ANTLR start "entryRuleSigned_Int"
    // InternalStructuredTextParser.g:3143:1: entryRuleSigned_Int returns [String current=null] : iv_ruleSigned_Int= ruleSigned_Int EOF ;
    public final String entryRuleSigned_Int() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSigned_Int = null;


        try {
            // InternalStructuredTextParser.g:3143:50: (iv_ruleSigned_Int= ruleSigned_Int EOF )
            // InternalStructuredTextParser.g:3144:2: iv_ruleSigned_Int= ruleSigned_Int EOF
            {
             newCompositeNode(grammarAccess.getSigned_IntRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSigned_Int=ruleSigned_Int();

            state._fsp--;

             current =iv_ruleSigned_Int.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSigned_Int"


    // $ANTLR start "ruleSigned_Int"
    // InternalStructuredTextParser.g:3150:1: ruleSigned_Int returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT ) ;
    public final AntlrDatatypeRuleToken ruleSigned_Int() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_UNSIGNED_INT_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3156:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:3157:2: ( (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:3157:2: ( (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3158:3: (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT
            {
            // InternalStructuredTextParser.g:3158:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt46=3;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==PlusSign) ) {
                alt46=1;
            }
            else if ( (LA46_0==HyphenMinus) ) {
                alt46=2;
            }
            switch (alt46) {
                case 1 :
                    // InternalStructuredTextParser.g:3159:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_12); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3165:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_12); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getSigned_IntAccess().getHyphenMinusKeyword_0_1());
                    			

                    }
                    break;

            }

            this_UNSIGNED_INT_2=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

            			current.merge(this_UNSIGNED_INT_2);
            		

            			newLeafNode(this_UNSIGNED_INT_2, grammarAccess.getSigned_IntAccess().getUNSIGNED_INTTerminalRuleCall_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSigned_Int"


    // $ANTLR start "entryRulePartial_Value"
    // InternalStructuredTextParser.g:3182:1: entryRulePartial_Value returns [String current=null] : iv_rulePartial_Value= rulePartial_Value EOF ;
    public final String entryRulePartial_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePartial_Value = null;


        try {
            // InternalStructuredTextParser.g:3182:53: (iv_rulePartial_Value= rulePartial_Value EOF )
            // InternalStructuredTextParser.g:3183:2: iv_rulePartial_Value= rulePartial_Value EOF
            {
             newCompositeNode(grammarAccess.getPartial_ValueRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePartial_Value=rulePartial_Value();

            state._fsp--;

             current =iv_rulePartial_Value.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePartial_Value"


    // $ANTLR start "rulePartial_Value"
    // InternalStructuredTextParser.g:3189:1: rulePartial_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken rulePartial_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3195:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3196:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

            		current.merge(this_UNSIGNED_INT_0);
            	

            		newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getPartial_ValueAccess().getUNSIGNED_INTTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePartial_Value"


    // $ANTLR start "entryRuleArray_Size"
    // InternalStructuredTextParser.g:3206:1: entryRuleArray_Size returns [String current=null] : iv_ruleArray_Size= ruleArray_Size EOF ;
    public final String entryRuleArray_Size() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleArray_Size = null;


        try {
            // InternalStructuredTextParser.g:3206:50: (iv_ruleArray_Size= ruleArray_Size EOF )
            // InternalStructuredTextParser.g:3207:2: iv_ruleArray_Size= ruleArray_Size EOF
            {
             newCompositeNode(grammarAccess.getArray_SizeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleArray_Size=ruleArray_Size();

            state._fsp--;

             current =iv_ruleArray_Size.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArray_Size"


    // $ANTLR start "ruleArray_Size"
    // InternalStructuredTextParser.g:3213:1: ruleArray_Size returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleArray_Size() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3219:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3220:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

            		current.merge(this_UNSIGNED_INT_0);
            	

            		newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getArray_SizeAccess().getUNSIGNED_INTTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArray_Size"


    // $ANTLR start "entryRuleReal_Literal"
    // InternalStructuredTextParser.g:3230:1: entryRuleReal_Literal returns [EObject current=null] : iv_ruleReal_Literal= ruleReal_Literal EOF ;
    public final EObject entryRuleReal_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReal_Literal = null;


        try {
            // InternalStructuredTextParser.g:3230:53: (iv_ruleReal_Literal= ruleReal_Literal EOF )
            // InternalStructuredTextParser.g:3231:2: iv_ruleReal_Literal= ruleReal_Literal EOF
            {
             newCompositeNode(grammarAccess.getReal_LiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReal_Literal=ruleReal_Literal();

            state._fsp--;

             current =iv_ruleReal_Literal; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReal_Literal"


    // $ANTLR start "ruleReal_Literal"
    // InternalStructuredTextParser.g:3237:1: ruleReal_Literal returns [EObject current=null] : ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) ) ;
    public final EObject ruleReal_Literal() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3243:2: ( ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) ) )
            // InternalStructuredTextParser.g:3244:2: ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) )
            {
            // InternalStructuredTextParser.g:3244:2: ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) )
            // InternalStructuredTextParser.g:3245:3: ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) )
            {
            // InternalStructuredTextParser.g:3245:3: ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==LREAL||LA47_0==REAL) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalStructuredTextParser.g:3246:4: ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign
                    {
                    // InternalStructuredTextParser.g:3246:4: ( (lv_type_0_0= ruleReal_Type_Name ) )
                    // InternalStructuredTextParser.g:3247:5: (lv_type_0_0= ruleReal_Type_Name )
                    {
                    // InternalStructuredTextParser.g:3247:5: (lv_type_0_0= ruleReal_Type_Name )
                    // InternalStructuredTextParser.g:3248:6: lv_type_0_0= ruleReal_Type_Name
                    {

                    						newCompositeNode(grammarAccess.getReal_LiteralAccess().getTypeReal_Type_NameEnumRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_56);
                    lv_type_0_0=ruleReal_Type_Name();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getReal_LiteralRule());
                    						}
                    						set(
                    							current,
                    							"type",
                    							lv_type_0_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Real_Type_Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_58); 

                    				newLeafNode(otherlv_1, grammarAccess.getReal_LiteralAccess().getNumberSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:3270:3: ( (lv_value_2_0= ruleReal_Value ) )
            // InternalStructuredTextParser.g:3271:4: (lv_value_2_0= ruleReal_Value )
            {
            // InternalStructuredTextParser.g:3271:4: (lv_value_2_0= ruleReal_Value )
            // InternalStructuredTextParser.g:3272:5: lv_value_2_0= ruleReal_Value
            {

            					newCompositeNode(grammarAccess.getReal_LiteralAccess().getValueReal_ValueParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleReal_Value();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReal_LiteralRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Real_Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReal_Literal"


    // $ANTLR start "entryRuleReal_Value"
    // InternalStructuredTextParser.g:3293:1: entryRuleReal_Value returns [String current=null] : iv_ruleReal_Value= ruleReal_Value EOF ;
    public final String entryRuleReal_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleReal_Value = null;


        try {
            // InternalStructuredTextParser.g:3293:50: (iv_ruleReal_Value= ruleReal_Value EOF )
            // InternalStructuredTextParser.g:3294:2: iv_ruleReal_Value= ruleReal_Value EOF
            {
             newCompositeNode(grammarAccess.getReal_ValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReal_Value=ruleReal_Value();

            state._fsp--;

             current =iv_ruleReal_Value.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReal_Value"


    // $ANTLR start "ruleReal_Value"
    // InternalStructuredTextParser.g:3300:1: ruleReal_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? ) ;
    public final AntlrDatatypeRuleToken ruleReal_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_UNSIGNED_INT_2=null;
        AntlrDatatypeRuleToken this_Signed_Int_0 = null;

        AntlrDatatypeRuleToken this_Signed_Int_4 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3306:2: ( (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? ) )
            // InternalStructuredTextParser.g:3307:2: (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? )
            {
            // InternalStructuredTextParser.g:3307:2: (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? )
            // InternalStructuredTextParser.g:3308:3: this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )?
            {

            			newCompositeNode(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_0());
            		
            pushFollow(FOLLOW_55);
            this_Signed_Int_0=ruleSigned_Int();

            state._fsp--;


            			current.merge(this_Signed_Int_0);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,FullStop,FOLLOW_12); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getReal_ValueAccess().getFullStopKeyword_1());
            		
            this_UNSIGNED_INT_2=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_59); 

            			current.merge(this_UNSIGNED_INT_2);
            		

            			newLeafNode(this_UNSIGNED_INT_2, grammarAccess.getReal_ValueAccess().getUNSIGNED_INTTerminalRuleCall_2());
            		
            // InternalStructuredTextParser.g:3330:3: (kw= E this_Signed_Int_4= ruleSigned_Int )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==E) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalStructuredTextParser.g:3331:4: kw= E this_Signed_Int_4= ruleSigned_Int
                    {
                    kw=(Token)match(input,E,FOLLOW_60); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getReal_ValueAccess().getEKeyword_3_0());
                    			

                    				newCompositeNode(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_3_1());
                    			
                    pushFollow(FOLLOW_2);
                    this_Signed_Int_4=ruleSigned_Int();

                    state._fsp--;


                    				current.merge(this_Signed_Int_4);
                    			

                    				afterParserOrEnumRuleCall();
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReal_Value"


    // $ANTLR start "entryRuleBool_Literal"
    // InternalStructuredTextParser.g:3351:1: entryRuleBool_Literal returns [EObject current=null] : iv_ruleBool_Literal= ruleBool_Literal EOF ;
    public final EObject entryRuleBool_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBool_Literal = null;


        try {
            // InternalStructuredTextParser.g:3351:53: (iv_ruleBool_Literal= ruleBool_Literal EOF )
            // InternalStructuredTextParser.g:3352:2: iv_ruleBool_Literal= ruleBool_Literal EOF
            {
             newCompositeNode(grammarAccess.getBool_LiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBool_Literal=ruleBool_Literal();

            state._fsp--;

             current =iv_ruleBool_Literal; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBool_Literal"


    // $ANTLR start "ruleBool_Literal"
    // InternalStructuredTextParser.g:3358:1: ruleBool_Literal returns [EObject current=null] : ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) ) ;
    public final EObject ruleBool_Literal() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3364:2: ( ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) ) )
            // InternalStructuredTextParser.g:3365:2: ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) )
            {
            // InternalStructuredTextParser.g:3365:2: ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) )
            // InternalStructuredTextParser.g:3366:3: ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) )
            {
            // InternalStructuredTextParser.g:3366:3: ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==BOOL) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalStructuredTextParser.g:3367:4: ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign
                    {
                    // InternalStructuredTextParser.g:3367:4: ( (lv_type_0_0= ruleBool_Type_Name ) )
                    // InternalStructuredTextParser.g:3368:5: (lv_type_0_0= ruleBool_Type_Name )
                    {
                    // InternalStructuredTextParser.g:3368:5: (lv_type_0_0= ruleBool_Type_Name )
                    // InternalStructuredTextParser.g:3369:6: lv_type_0_0= ruleBool_Type_Name
                    {

                    						newCompositeNode(grammarAccess.getBool_LiteralAccess().getTypeBool_Type_NameEnumRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_56);
                    lv_type_0_0=ruleBool_Type_Name();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getBool_LiteralRule());
                    						}
                    						set(
                    							current,
                    							"type",
                    							lv_type_0_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Bool_Type_Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_17); 

                    				newLeafNode(otherlv_1, grammarAccess.getBool_LiteralAccess().getNumberSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:3391:3: ( (lv_value_2_0= ruleBool_Value ) )
            // InternalStructuredTextParser.g:3392:4: (lv_value_2_0= ruleBool_Value )
            {
            // InternalStructuredTextParser.g:3392:4: (lv_value_2_0= ruleBool_Value )
            // InternalStructuredTextParser.g:3393:5: lv_value_2_0= ruleBool_Value
            {

            					newCompositeNode(grammarAccess.getBool_LiteralAccess().getValueBool_ValueParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleBool_Value();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getBool_LiteralRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Bool_Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBool_Literal"


    // $ANTLR start "entryRuleBool_Value"
    // InternalStructuredTextParser.g:3414:1: entryRuleBool_Value returns [String current=null] : iv_ruleBool_Value= ruleBool_Value EOF ;
    public final String entryRuleBool_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBool_Value = null;


        try {
            // InternalStructuredTextParser.g:3414:50: (iv_ruleBool_Value= ruleBool_Value EOF )
            // InternalStructuredTextParser.g:3415:2: iv_ruleBool_Value= ruleBool_Value EOF
            {
             newCompositeNode(grammarAccess.getBool_ValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBool_Value=ruleBool_Value();

            state._fsp--;

             current =iv_ruleBool_Value.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBool_Value"


    // $ANTLR start "ruleBool_Value"
    // InternalStructuredTextParser.g:3421:1: ruleBool_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= FALSE | kw= TRUE ) ;
    public final AntlrDatatypeRuleToken ruleBool_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3427:2: ( (kw= FALSE | kw= TRUE ) )
            // InternalStructuredTextParser.g:3428:2: (kw= FALSE | kw= TRUE )
            {
            // InternalStructuredTextParser.g:3428:2: (kw= FALSE | kw= TRUE )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==FALSE) ) {
                alt50=1;
            }
            else if ( (LA50_0==TRUE) ) {
                alt50=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // InternalStructuredTextParser.g:3429:3: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBool_ValueAccess().getFALSEKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3435:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBool_ValueAccess().getTRUEKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBool_Value"


    // $ANTLR start "entryRuleChar_Literal"
    // InternalStructuredTextParser.g:3444:1: entryRuleChar_Literal returns [EObject current=null] : iv_ruleChar_Literal= ruleChar_Literal EOF ;
    public final EObject entryRuleChar_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChar_Literal = null;


        try {
            // InternalStructuredTextParser.g:3444:53: (iv_ruleChar_Literal= ruleChar_Literal EOF )
            // InternalStructuredTextParser.g:3445:2: iv_ruleChar_Literal= ruleChar_Literal EOF
            {
             newCompositeNode(grammarAccess.getChar_LiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleChar_Literal=ruleChar_Literal();

            state._fsp--;

             current =iv_ruleChar_Literal; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleChar_Literal"


    // $ANTLR start "ruleChar_Literal"
    // InternalStructuredTextParser.g:3451:1: ruleChar_Literal returns [EObject current=null] : ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) ) ) ;
    public final EObject ruleChar_Literal() throws RecognitionException {
        EObject current = null;

        Token lv_length_1_0=null;
        Token otherlv_2=null;
        Token lv_value_3_1=null;
        Token lv_value_3_2=null;
        Enumerator lv_type_0_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3457:2: ( ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) ) ) )
            // InternalStructuredTextParser.g:3458:2: ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) ) )
            {
            // InternalStructuredTextParser.g:3458:2: ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) ) )
            // InternalStructuredTextParser.g:3459:3: ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) )
            {
            // InternalStructuredTextParser.g:3459:3: ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==WSTRING||LA52_0==STRING||LA52_0==WCHAR||LA52_0==CHAR) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalStructuredTextParser.g:3460:4: ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign
                    {
                    // InternalStructuredTextParser.g:3460:4: ( (lv_type_0_0= ruleString_Type_Name ) )
                    // InternalStructuredTextParser.g:3461:5: (lv_type_0_0= ruleString_Type_Name )
                    {
                    // InternalStructuredTextParser.g:3461:5: (lv_type_0_0= ruleString_Type_Name )
                    // InternalStructuredTextParser.g:3462:6: lv_type_0_0= ruleString_Type_Name
                    {

                    						newCompositeNode(grammarAccess.getChar_LiteralAccess().getTypeString_Type_NameEnumRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_61);
                    lv_type_0_0=ruleString_Type_Name();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getChar_LiteralRule());
                    						}
                    						set(
                    							current,
                    							"type",
                    							lv_type_0_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.String_Type_Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:3479:4: ( (lv_length_1_0= RULE_UNSIGNED_INT ) )?
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==RULE_UNSIGNED_INT) ) {
                        alt51=1;
                    }
                    switch (alt51) {
                        case 1 :
                            // InternalStructuredTextParser.g:3480:5: (lv_length_1_0= RULE_UNSIGNED_INT )
                            {
                            // InternalStructuredTextParser.g:3480:5: (lv_length_1_0= RULE_UNSIGNED_INT )
                            // InternalStructuredTextParser.g:3481:6: lv_length_1_0= RULE_UNSIGNED_INT
                            {
                            lv_length_1_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_56); 

                            						newLeafNode(lv_length_1_0, grammarAccess.getChar_LiteralAccess().getLengthUNSIGNED_INTTerminalRuleCall_0_1_0());
                            					

                            						if (current==null) {
                            							current = createModelElement(grammarAccess.getChar_LiteralRule());
                            						}
                            						setWithLastConsumed(
                            							current,
                            							"length",
                            							lv_length_1_0,
                            							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.UNSIGNED_INT");
                            					

                            }


                            }
                            break;

                    }

                    otherlv_2=(Token)match(input,NumberSign,FOLLOW_62); 

                    				newLeafNode(otherlv_2, grammarAccess.getChar_LiteralAccess().getNumberSignKeyword_0_2());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:3502:3: ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) )
            // InternalStructuredTextParser.g:3503:4: ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) )
            {
            // InternalStructuredTextParser.g:3503:4: ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) )
            // InternalStructuredTextParser.g:3504:5: (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR )
            {
            // InternalStructuredTextParser.g:3504:5: (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==RULE_S_BYTE_CHAR_STR) ) {
                alt53=1;
            }
            else if ( (LA53_0==RULE_D_BYTE_CHAR_STR) ) {
                alt53=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // InternalStructuredTextParser.g:3505:6: lv_value_3_1= RULE_S_BYTE_CHAR_STR
                    {
                    lv_value_3_1=(Token)match(input,RULE_S_BYTE_CHAR_STR,FOLLOW_2); 

                    						newLeafNode(lv_value_3_1, grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getChar_LiteralRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_3_1,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.S_BYTE_CHAR_STR");
                    					

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3520:6: lv_value_3_2= RULE_D_BYTE_CHAR_STR
                    {
                    lv_value_3_2=(Token)match(input,RULE_D_BYTE_CHAR_STR,FOLLOW_2); 

                    						newLeafNode(lv_value_3_2, grammarAccess.getChar_LiteralAccess().getValueD_BYTE_CHAR_STRTerminalRuleCall_1_0_1());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getChar_LiteralRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_3_2,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.D_BYTE_CHAR_STR");
                    					

                    }
                    break;

            }


            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleChar_Literal"


    // $ANTLR start "entryRuleTime_Literal"
    // InternalStructuredTextParser.g:3541:1: entryRuleTime_Literal returns [EObject current=null] : iv_ruleTime_Literal= ruleTime_Literal EOF ;
    public final EObject entryRuleTime_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTime_Literal = null;


        try {
            // InternalStructuredTextParser.g:3541:53: (iv_ruleTime_Literal= ruleTime_Literal EOF )
            // InternalStructuredTextParser.g:3542:2: iv_ruleTime_Literal= ruleTime_Literal EOF
            {
             newCompositeNode(grammarAccess.getTime_LiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTime_Literal=ruleTime_Literal();

            state._fsp--;

             current =iv_ruleTime_Literal; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTime_Literal"


    // $ANTLR start "ruleTime_Literal"
    // InternalStructuredTextParser.g:3548:1: ruleTime_Literal returns [EObject current=null] : (this_Duration_0= ruleDuration | this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3= ruleDate_And_Time ) ;
    public final EObject ruleTime_Literal() throws RecognitionException {
        EObject current = null;

        EObject this_Duration_0 = null;

        EObject this_Time_Of_Day_1 = null;

        EObject this_Date_2 = null;

        EObject this_Date_And_Time_3 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3554:2: ( (this_Duration_0= ruleDuration | this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3= ruleDate_And_Time ) )
            // InternalStructuredTextParser.g:3555:2: (this_Duration_0= ruleDuration | this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3= ruleDate_And_Time )
            {
            // InternalStructuredTextParser.g:3555:2: (this_Duration_0= ruleDuration | this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3= ruleDate_And_Time )
            int alt54=4;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt54=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt54=2;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D_1:
                {
                alt54=3;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt54=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }

            switch (alt54) {
                case 1 :
                    // InternalStructuredTextParser.g:3556:3: this_Duration_0= ruleDuration
                    {

                    			newCompositeNode(grammarAccess.getTime_LiteralAccess().getDurationParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Duration_0=ruleDuration();

                    state._fsp--;


                    			current = this_Duration_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3565:3: this_Time_Of_Day_1= ruleTime_Of_Day
                    {

                    			newCompositeNode(grammarAccess.getTime_LiteralAccess().getTime_Of_DayParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Time_Of_Day_1=ruleTime_Of_Day();

                    state._fsp--;


                    			current = this_Time_Of_Day_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:3574:3: this_Date_2= ruleDate
                    {

                    			newCompositeNode(grammarAccess.getTime_LiteralAccess().getDateParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Date_2=ruleDate();

                    state._fsp--;


                    			current = this_Date_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:3583:3: this_Date_And_Time_3= ruleDate_And_Time
                    {

                    			newCompositeNode(grammarAccess.getTime_LiteralAccess().getDate_And_TimeParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Date_And_Time_3=ruleDate_And_Time();

                    state._fsp--;


                    			current = this_Date_And_Time_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTime_Literal"


    // $ANTLR start "entryRuleDuration"
    // InternalStructuredTextParser.g:3595:1: entryRuleDuration returns [EObject current=null] : iv_ruleDuration= ruleDuration EOF ;
    public final EObject entryRuleDuration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDuration = null;


        try {
            // InternalStructuredTextParser.g:3595:49: (iv_ruleDuration= ruleDuration EOF )
            // InternalStructuredTextParser.g:3596:2: iv_ruleDuration= ruleDuration EOF
            {
             newCompositeNode(grammarAccess.getDurationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDuration=ruleDuration();

            state._fsp--;

             current =iv_ruleDuration; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDuration"


    // $ANTLR start "ruleDuration"
    // InternalStructuredTextParser.g:3602:1: ruleDuration returns [EObject current=null] : ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )* ) ;
    public final EObject ruleDuration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_negative_3_0=null;
        Token otherlv_5=null;
        Enumerator lv_type_0_0 = null;

        EObject lv_value_4_0 = null;

        EObject lv_value_6_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3608:2: ( ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )* ) )
            // InternalStructuredTextParser.g:3609:2: ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )* )
            {
            // InternalStructuredTextParser.g:3609:2: ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )* )
            // InternalStructuredTextParser.g:3610:3: ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )*
            {
            // InternalStructuredTextParser.g:3610:3: ( (lv_type_0_0= ruleTime_Type_Name ) )
            // InternalStructuredTextParser.g:3611:4: (lv_type_0_0= ruleTime_Type_Name )
            {
            // InternalStructuredTextParser.g:3611:4: (lv_type_0_0= ruleTime_Type_Name )
            // InternalStructuredTextParser.g:3612:5: lv_type_0_0= ruleTime_Type_Name
            {

            					newCompositeNode(grammarAccess.getDurationAccess().getTypeTime_Type_NameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_56);
            lv_type_0_0=ruleTime_Type_Name();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDurationRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_0_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Time_Type_Name");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_60); 

            			newLeafNode(otherlv_1, grammarAccess.getDurationAccess().getNumberSignKeyword_1());
            		
            // InternalStructuredTextParser.g:3633:3: (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )?
            int alt55=3;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==PlusSign) ) {
                alt55=1;
            }
            else if ( (LA55_0==HyphenMinus) ) {
                alt55=2;
            }
            switch (alt55) {
                case 1 :
                    // InternalStructuredTextParser.g:3634:4: otherlv_2= PlusSign
                    {
                    otherlv_2=(Token)match(input,PlusSign,FOLLOW_60); 

                    				newLeafNode(otherlv_2, grammarAccess.getDurationAccess().getPlusSignKeyword_2_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3639:4: ( (lv_negative_3_0= HyphenMinus ) )
                    {
                    // InternalStructuredTextParser.g:3639:4: ( (lv_negative_3_0= HyphenMinus ) )
                    // InternalStructuredTextParser.g:3640:5: (lv_negative_3_0= HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:3640:5: (lv_negative_3_0= HyphenMinus )
                    // InternalStructuredTextParser.g:3641:6: lv_negative_3_0= HyphenMinus
                    {
                    lv_negative_3_0=(Token)match(input,HyphenMinus,FOLLOW_60); 

                    						newLeafNode(lv_negative_3_0, grammarAccess.getDurationAccess().getNegativeHyphenMinusKeyword_2_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDurationRule());
                    						}
                    						setWithLastConsumed(current, "negative", true, "-");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalStructuredTextParser.g:3654:3: ( (lv_value_4_0= ruleDuration_Value ) )
            // InternalStructuredTextParser.g:3655:4: (lv_value_4_0= ruleDuration_Value )
            {
            // InternalStructuredTextParser.g:3655:4: (lv_value_4_0= ruleDuration_Value )
            // InternalStructuredTextParser.g:3656:5: lv_value_4_0= ruleDuration_Value
            {

            					newCompositeNode(grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_63);
            lv_value_4_0=ruleDuration_Value();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDurationRule());
            					}
            					add(
            						current,
            						"value",
            						lv_value_4_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Duration_Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalStructuredTextParser.g:3673:3: (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==KW__) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalStructuredTextParser.g:3674:4: otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) )
            	    {
            	    otherlv_5=(Token)match(input,KW__,FOLLOW_60); 

            	    				newLeafNode(otherlv_5, grammarAccess.getDurationAccess().get_Keyword_4_0());
            	    			
            	    // InternalStructuredTextParser.g:3678:4: ( (lv_value_6_0= ruleDuration_Value ) )
            	    // InternalStructuredTextParser.g:3679:5: (lv_value_6_0= ruleDuration_Value )
            	    {
            	    // InternalStructuredTextParser.g:3679:5: (lv_value_6_0= ruleDuration_Value )
            	    // InternalStructuredTextParser.g:3680:6: lv_value_6_0= ruleDuration_Value
            	    {

            	    						newCompositeNode(grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_63);
            	    lv_value_6_0=ruleDuration_Value();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getDurationRule());
            	    						}
            	    						add(
            	    							current,
            	    							"value",
            	    							lv_value_6_0,
            	    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Duration_Value");
            	    						afterParserOrEnumRuleCall();
            	    					

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


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDuration"


    // $ANTLR start "entryRuleDuration_Value"
    // InternalStructuredTextParser.g:3702:1: entryRuleDuration_Value returns [EObject current=null] : iv_ruleDuration_Value= ruleDuration_Value EOF ;
    public final EObject entryRuleDuration_Value() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDuration_Value = null;


        try {
            // InternalStructuredTextParser.g:3702:55: (iv_ruleDuration_Value= ruleDuration_Value EOF )
            // InternalStructuredTextParser.g:3703:2: iv_ruleDuration_Value= ruleDuration_Value EOF
            {
             newCompositeNode(grammarAccess.getDuration_ValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDuration_Value=ruleDuration_Value();

            state._fsp--;

             current =iv_ruleDuration_Value; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDuration_Value"


    // $ANTLR start "ruleDuration_Value"
    // InternalStructuredTextParser.g:3709:1: ruleDuration_Value returns [EObject current=null] : ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) ) ) ;
    public final EObject ruleDuration_Value() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;

        Enumerator lv_unit_1_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3715:2: ( ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) ) ) )
            // InternalStructuredTextParser.g:3716:2: ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) ) )
            {
            // InternalStructuredTextParser.g:3716:2: ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) ) )
            // InternalStructuredTextParser.g:3717:3: ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) )
            {
            // InternalStructuredTextParser.g:3717:3: ( (lv_value_0_0= ruleFix_Point ) )
            // InternalStructuredTextParser.g:3718:4: (lv_value_0_0= ruleFix_Point )
            {
            // InternalStructuredTextParser.g:3718:4: (lv_value_0_0= ruleFix_Point )
            // InternalStructuredTextParser.g:3719:5: lv_value_0_0= ruleFix_Point
            {

            					newCompositeNode(grammarAccess.getDuration_ValueAccess().getValueFix_PointParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_64);
            lv_value_0_0=ruleFix_Point();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDuration_ValueRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_0_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Fix_Point");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalStructuredTextParser.g:3736:3: ( (lv_unit_1_0= ruleDuration_Unit ) )
            // InternalStructuredTextParser.g:3737:4: (lv_unit_1_0= ruleDuration_Unit )
            {
            // InternalStructuredTextParser.g:3737:4: (lv_unit_1_0= ruleDuration_Unit )
            // InternalStructuredTextParser.g:3738:5: lv_unit_1_0= ruleDuration_Unit
            {

            					newCompositeNode(grammarAccess.getDuration_ValueAccess().getUnitDuration_UnitEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_unit_1_0=ruleDuration_Unit();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDuration_ValueRule());
            					}
            					set(
            						current,
            						"unit",
            						lv_unit_1_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Duration_Unit");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDuration_Value"


    // $ANTLR start "entryRuleFix_Point"
    // InternalStructuredTextParser.g:3759:1: entryRuleFix_Point returns [String current=null] : iv_ruleFix_Point= ruleFix_Point EOF ;
    public final String entryRuleFix_Point() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFix_Point = null;


        try {
            // InternalStructuredTextParser.g:3759:49: (iv_ruleFix_Point= ruleFix_Point EOF )
            // InternalStructuredTextParser.g:3760:2: iv_ruleFix_Point= ruleFix_Point EOF
            {
             newCompositeNode(grammarAccess.getFix_PointRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFix_Point=ruleFix_Point();

            state._fsp--;

             current =iv_ruleFix_Point.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFix_Point"


    // $ANTLR start "ruleFix_Point"
    // InternalStructuredTextParser.g:3766:1: ruleFix_Point returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleFix_Point() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;
        Token kw=null;
        Token this_UNSIGNED_INT_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3772:2: ( (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? ) )
            // InternalStructuredTextParser.g:3773:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? )
            {
            // InternalStructuredTextParser.g:3773:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? )
            // InternalStructuredTextParser.g:3774:3: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )?
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_65); 

            			current.merge(this_UNSIGNED_INT_0);
            		

            			newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_0());
            		
            // InternalStructuredTextParser.g:3781:3: (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==FullStop) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalStructuredTextParser.g:3782:4: kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_12); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getFix_PointAccess().getFullStopKeyword_1_0());
                    			
                    this_UNSIGNED_INT_2=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

                    				current.merge(this_UNSIGNED_INT_2);
                    			

                    				newLeafNode(this_UNSIGNED_INT_2, grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_1_1());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFix_Point"


    // $ANTLR start "entryRuleTime_Of_Day"
    // InternalStructuredTextParser.g:3799:1: entryRuleTime_Of_Day returns [EObject current=null] : iv_ruleTime_Of_Day= ruleTime_Of_Day EOF ;
    public final EObject entryRuleTime_Of_Day() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTime_Of_Day = null;


        try {
            // InternalStructuredTextParser.g:3799:52: (iv_ruleTime_Of_Day= ruleTime_Of_Day EOF )
            // InternalStructuredTextParser.g:3800:2: iv_ruleTime_Of_Day= ruleTime_Of_Day EOF
            {
             newCompositeNode(grammarAccess.getTime_Of_DayRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTime_Of_Day=ruleTime_Of_Day();

            state._fsp--;

             current =iv_ruleTime_Of_Day; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTime_Of_Day"


    // $ANTLR start "ruleTime_Of_Day"
    // InternalStructuredTextParser.g:3806:1: ruleTime_Of_Day returns [EObject current=null] : ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) ) ;
    public final EObject ruleTime_Of_Day() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3812:2: ( ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) ) )
            // InternalStructuredTextParser.g:3813:2: ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) )
            {
            // InternalStructuredTextParser.g:3813:2: ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) )
            // InternalStructuredTextParser.g:3814:3: ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) )
            {
            // InternalStructuredTextParser.g:3814:3: ( (lv_type_0_0= ruleTod_Type_Name ) )
            // InternalStructuredTextParser.g:3815:4: (lv_type_0_0= ruleTod_Type_Name )
            {
            // InternalStructuredTextParser.g:3815:4: (lv_type_0_0= ruleTod_Type_Name )
            // InternalStructuredTextParser.g:3816:5: lv_type_0_0= ruleTod_Type_Name
            {

            					newCompositeNode(grammarAccess.getTime_Of_DayAccess().getTypeTod_Type_NameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_56);
            lv_type_0_0=ruleTod_Type_Name();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTime_Of_DayRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_0_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Tod_Type_Name");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_12); 

            			newLeafNode(otherlv_1, grammarAccess.getTime_Of_DayAccess().getNumberSignKeyword_1());
            		
            // InternalStructuredTextParser.g:3837:3: ( (lv_value_2_0= ruleDaytime ) )
            // InternalStructuredTextParser.g:3838:4: (lv_value_2_0= ruleDaytime )
            {
            // InternalStructuredTextParser.g:3838:4: (lv_value_2_0= ruleDaytime )
            // InternalStructuredTextParser.g:3839:5: lv_value_2_0= ruleDaytime
            {

            					newCompositeNode(grammarAccess.getTime_Of_DayAccess().getValueDaytimeParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleDaytime();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTime_Of_DayRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Daytime");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTime_Of_Day"


    // $ANTLR start "entryRuleDaytime"
    // InternalStructuredTextParser.g:3860:1: entryRuleDaytime returns [String current=null] : iv_ruleDaytime= ruleDaytime EOF ;
    public final String entryRuleDaytime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDaytime = null;


        try {
            // InternalStructuredTextParser.g:3860:47: (iv_ruleDaytime= ruleDaytime EOF )
            // InternalStructuredTextParser.g:3861:2: iv_ruleDaytime= ruleDaytime EOF
            {
             newCompositeNode(grammarAccess.getDaytimeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDaytime=ruleDaytime();

            state._fsp--;

             current =iv_ruleDaytime.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDaytime"


    // $ANTLR start "ruleDaytime"
    // InternalStructuredTextParser.g:3867:1: ruleDaytime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second ) ;
    public final AntlrDatatypeRuleToken ruleDaytime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Day_Hour_0 = null;

        AntlrDatatypeRuleToken this_Day_Minute_2 = null;

        AntlrDatatypeRuleToken this_Day_Second_4 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3873:2: ( (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second ) )
            // InternalStructuredTextParser.g:3874:2: (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second )
            {
            // InternalStructuredTextParser.g:3874:2: (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second )
            // InternalStructuredTextParser.g:3875:3: this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second
            {

            			newCompositeNode(grammarAccess.getDaytimeAccess().getDay_HourParserRuleCall_0());
            		
            pushFollow(FOLLOW_9);
            this_Day_Hour_0=ruleDay_Hour();

            state._fsp--;


            			current.merge(this_Day_Hour_0);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,Colon,FOLLOW_12); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getDaytimeAccess().getColonKeyword_1());
            		

            			newCompositeNode(grammarAccess.getDaytimeAccess().getDay_MinuteParserRuleCall_2());
            		
            pushFollow(FOLLOW_9);
            this_Day_Minute_2=ruleDay_Minute();

            state._fsp--;


            			current.merge(this_Day_Minute_2);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,Colon,FOLLOW_60); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getDaytimeAccess().getColonKeyword_3());
            		

            			newCompositeNode(grammarAccess.getDaytimeAccess().getDay_SecondParserRuleCall_4());
            		
            pushFollow(FOLLOW_2);
            this_Day_Second_4=ruleDay_Second();

            state._fsp--;


            			current.merge(this_Day_Second_4);
            		

            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDaytime"


    // $ANTLR start "entryRuleDay_Hour"
    // InternalStructuredTextParser.g:3919:1: entryRuleDay_Hour returns [String current=null] : iv_ruleDay_Hour= ruleDay_Hour EOF ;
    public final String entryRuleDay_Hour() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDay_Hour = null;


        try {
            // InternalStructuredTextParser.g:3919:48: (iv_ruleDay_Hour= ruleDay_Hour EOF )
            // InternalStructuredTextParser.g:3920:2: iv_ruleDay_Hour= ruleDay_Hour EOF
            {
             newCompositeNode(grammarAccess.getDay_HourRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDay_Hour=ruleDay_Hour();

            state._fsp--;

             current =iv_ruleDay_Hour.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDay_Hour"


    // $ANTLR start "ruleDay_Hour"
    // InternalStructuredTextParser.g:3926:1: ruleDay_Hour returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleDay_Hour() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3932:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3933:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

            		current.merge(this_UNSIGNED_INT_0);
            	

            		newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getDay_HourAccess().getUNSIGNED_INTTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDay_Hour"


    // $ANTLR start "entryRuleDay_Minute"
    // InternalStructuredTextParser.g:3943:1: entryRuleDay_Minute returns [String current=null] : iv_ruleDay_Minute= ruleDay_Minute EOF ;
    public final String entryRuleDay_Minute() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDay_Minute = null;


        try {
            // InternalStructuredTextParser.g:3943:50: (iv_ruleDay_Minute= ruleDay_Minute EOF )
            // InternalStructuredTextParser.g:3944:2: iv_ruleDay_Minute= ruleDay_Minute EOF
            {
             newCompositeNode(grammarAccess.getDay_MinuteRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDay_Minute=ruleDay_Minute();

            state._fsp--;

             current =iv_ruleDay_Minute.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDay_Minute"


    // $ANTLR start "ruleDay_Minute"
    // InternalStructuredTextParser.g:3950:1: ruleDay_Minute returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleDay_Minute() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3956:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3957:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

            		current.merge(this_UNSIGNED_INT_0);
            	

            		newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getDay_MinuteAccess().getUNSIGNED_INTTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDay_Minute"


    // $ANTLR start "entryRuleDay_Second"
    // InternalStructuredTextParser.g:3967:1: entryRuleDay_Second returns [String current=null] : iv_ruleDay_Second= ruleDay_Second EOF ;
    public final String entryRuleDay_Second() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDay_Second = null;


        try {
            // InternalStructuredTextParser.g:3967:50: (iv_ruleDay_Second= ruleDay_Second EOF )
            // InternalStructuredTextParser.g:3968:2: iv_ruleDay_Second= ruleDay_Second EOF
            {
             newCompositeNode(grammarAccess.getDay_SecondRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDay_Second=ruleDay_Second();

            state._fsp--;

             current =iv_ruleDay_Second.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDay_Second"


    // $ANTLR start "ruleDay_Second"
    // InternalStructuredTextParser.g:3974:1: ruleDay_Second returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_Fix_Point_0= ruleFix_Point ;
    public final AntlrDatatypeRuleToken ruleDay_Second() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_Fix_Point_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3980:2: (this_Fix_Point_0= ruleFix_Point )
            // InternalStructuredTextParser.g:3981:2: this_Fix_Point_0= ruleFix_Point
            {

            		newCompositeNode(grammarAccess.getDay_SecondAccess().getFix_PointParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_Fix_Point_0=ruleFix_Point();

            state._fsp--;


            		current.merge(this_Fix_Point_0);
            	

            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDay_Second"


    // $ANTLR start "entryRuleDate"
    // InternalStructuredTextParser.g:3994:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalStructuredTextParser.g:3994:45: (iv_ruleDate= ruleDate EOF )
            // InternalStructuredTextParser.g:3995:2: iv_ruleDate= ruleDate EOF
            {
             newCompositeNode(grammarAccess.getDateRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDate=ruleDate();

            state._fsp--;

             current =iv_ruleDate; 
            match(input,EOF,FOLLOW_2); 

            }

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
    // InternalStructuredTextParser.g:4001:1: ruleDate returns [EObject current=null] : ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) ) ;
    public final EObject ruleDate() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:4007:2: ( ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) ) )
            // InternalStructuredTextParser.g:4008:2: ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) )
            {
            // InternalStructuredTextParser.g:4008:2: ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) )
            // InternalStructuredTextParser.g:4009:3: ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) )
            {
            // InternalStructuredTextParser.g:4009:3: ( (lv_type_0_0= ruleDate_Type_Name ) )
            // InternalStructuredTextParser.g:4010:4: (lv_type_0_0= ruleDate_Type_Name )
            {
            // InternalStructuredTextParser.g:4010:4: (lv_type_0_0= ruleDate_Type_Name )
            // InternalStructuredTextParser.g:4011:5: lv_type_0_0= ruleDate_Type_Name
            {

            					newCompositeNode(grammarAccess.getDateAccess().getTypeDate_Type_NameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_56);
            lv_type_0_0=ruleDate_Type_Name();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDateRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_0_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Date_Type_Name");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_12); 

            			newLeafNode(otherlv_1, grammarAccess.getDateAccess().getNumberSignKeyword_1());
            		
            // InternalStructuredTextParser.g:4032:3: ( (lv_value_2_0= ruleDate_Literal ) )
            // InternalStructuredTextParser.g:4033:4: (lv_value_2_0= ruleDate_Literal )
            {
            // InternalStructuredTextParser.g:4033:4: (lv_value_2_0= ruleDate_Literal )
            // InternalStructuredTextParser.g:4034:5: lv_value_2_0= ruleDate_Literal
            {

            					newCompositeNode(grammarAccess.getDateAccess().getValueDate_LiteralParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleDate_Literal();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDateRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Date_Literal");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

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


    // $ANTLR start "entryRuleDate_Literal"
    // InternalStructuredTextParser.g:4055:1: entryRuleDate_Literal returns [String current=null] : iv_ruleDate_Literal= ruleDate_Literal EOF ;
    public final String entryRuleDate_Literal() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate_Literal = null;


        try {
            // InternalStructuredTextParser.g:4055:52: (iv_ruleDate_Literal= ruleDate_Literal EOF )
            // InternalStructuredTextParser.g:4056:2: iv_ruleDate_Literal= ruleDate_Literal EOF
            {
             newCompositeNode(grammarAccess.getDate_LiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDate_Literal=ruleDate_Literal();

            state._fsp--;

             current =iv_ruleDate_Literal.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDate_Literal"


    // $ANTLR start "ruleDate_Literal"
    // InternalStructuredTextParser.g:4062:1: ruleDate_Literal returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay ) ;
    public final AntlrDatatypeRuleToken ruleDate_Literal() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Year_0 = null;

        AntlrDatatypeRuleToken this_Month_2 = null;

        AntlrDatatypeRuleToken this_Day_4 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:4068:2: ( (this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay ) )
            // InternalStructuredTextParser.g:4069:2: (this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay )
            {
            // InternalStructuredTextParser.g:4069:2: (this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay )
            // InternalStructuredTextParser.g:4070:3: this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay
            {

            			newCompositeNode(grammarAccess.getDate_LiteralAccess().getYearParserRuleCall_0());
            		
            pushFollow(FOLLOW_66);
            this_Year_0=ruleYear();

            state._fsp--;


            			current.merge(this_Year_0);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,HyphenMinus,FOLLOW_12); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_1());
            		

            			newCompositeNode(grammarAccess.getDate_LiteralAccess().getMonthParserRuleCall_2());
            		
            pushFollow(FOLLOW_66);
            this_Month_2=ruleMonth();

            state._fsp--;


            			current.merge(this_Month_2);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,HyphenMinus,FOLLOW_12); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_3());
            		

            			newCompositeNode(grammarAccess.getDate_LiteralAccess().getDayParserRuleCall_4());
            		
            pushFollow(FOLLOW_2);
            this_Day_4=ruleDay();

            state._fsp--;


            			current.merge(this_Day_4);
            		

            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDate_Literal"


    // $ANTLR start "entryRuleYear"
    // InternalStructuredTextParser.g:4114:1: entryRuleYear returns [String current=null] : iv_ruleYear= ruleYear EOF ;
    public final String entryRuleYear() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleYear = null;


        try {
            // InternalStructuredTextParser.g:4114:44: (iv_ruleYear= ruleYear EOF )
            // InternalStructuredTextParser.g:4115:2: iv_ruleYear= ruleYear EOF
            {
             newCompositeNode(grammarAccess.getYearRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleYear=ruleYear();

            state._fsp--;

             current =iv_ruleYear.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleYear"


    // $ANTLR start "ruleYear"
    // InternalStructuredTextParser.g:4121:1: ruleYear returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleYear() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4127:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:4128:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

            		current.merge(this_UNSIGNED_INT_0);
            	

            		newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getYearAccess().getUNSIGNED_INTTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleYear"


    // $ANTLR start "entryRuleMonth"
    // InternalStructuredTextParser.g:4138:1: entryRuleMonth returns [String current=null] : iv_ruleMonth= ruleMonth EOF ;
    public final String entryRuleMonth() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMonth = null;


        try {
            // InternalStructuredTextParser.g:4138:45: (iv_ruleMonth= ruleMonth EOF )
            // InternalStructuredTextParser.g:4139:2: iv_ruleMonth= ruleMonth EOF
            {
             newCompositeNode(grammarAccess.getMonthRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMonth=ruleMonth();

            state._fsp--;

             current =iv_ruleMonth.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMonth"


    // $ANTLR start "ruleMonth"
    // InternalStructuredTextParser.g:4145:1: ruleMonth returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleMonth() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4151:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:4152:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

            		current.merge(this_UNSIGNED_INT_0);
            	

            		newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getMonthAccess().getUNSIGNED_INTTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMonth"


    // $ANTLR start "entryRuleDay"
    // InternalStructuredTextParser.g:4162:1: entryRuleDay returns [String current=null] : iv_ruleDay= ruleDay EOF ;
    public final String entryRuleDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDay = null;


        try {
            // InternalStructuredTextParser.g:4162:43: (iv_ruleDay= ruleDay EOF )
            // InternalStructuredTextParser.g:4163:2: iv_ruleDay= ruleDay EOF
            {
             newCompositeNode(grammarAccess.getDayRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDay=ruleDay();

            state._fsp--;

             current =iv_ruleDay.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDay"


    // $ANTLR start "ruleDay"
    // InternalStructuredTextParser.g:4169:1: ruleDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4175:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:4176:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_2); 

            		current.merge(this_UNSIGNED_INT_0);
            	

            		newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getDayAccess().getUNSIGNED_INTTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDay"


    // $ANTLR start "entryRuleDate_And_Time"
    // InternalStructuredTextParser.g:4186:1: entryRuleDate_And_Time returns [EObject current=null] : iv_ruleDate_And_Time= ruleDate_And_Time EOF ;
    public final EObject entryRuleDate_And_Time() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate_And_Time = null;


        try {
            // InternalStructuredTextParser.g:4186:54: (iv_ruleDate_And_Time= ruleDate_And_Time EOF )
            // InternalStructuredTextParser.g:4187:2: iv_ruleDate_And_Time= ruleDate_And_Time EOF
            {
             newCompositeNode(grammarAccess.getDate_And_TimeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDate_And_Time=ruleDate_And_Time();

            state._fsp--;

             current =iv_ruleDate_And_Time; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDate_And_Time"


    // $ANTLR start "ruleDate_And_Time"
    // InternalStructuredTextParser.g:4193:1: ruleDate_And_Time returns [EObject current=null] : ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) ) ;
    public final EObject ruleDate_And_Time() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:4199:2: ( ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) ) )
            // InternalStructuredTextParser.g:4200:2: ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) )
            {
            // InternalStructuredTextParser.g:4200:2: ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) )
            // InternalStructuredTextParser.g:4201:3: ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) )
            {
            // InternalStructuredTextParser.g:4201:3: ( (lv_type_0_0= ruleDT_Type_Name ) )
            // InternalStructuredTextParser.g:4202:4: (lv_type_0_0= ruleDT_Type_Name )
            {
            // InternalStructuredTextParser.g:4202:4: (lv_type_0_0= ruleDT_Type_Name )
            // InternalStructuredTextParser.g:4203:5: lv_type_0_0= ruleDT_Type_Name
            {

            					newCompositeNode(grammarAccess.getDate_And_TimeAccess().getTypeDT_Type_NameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_56);
            lv_type_0_0=ruleDT_Type_Name();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDate_And_TimeRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_0_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.DT_Type_Name");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_12); 

            			newLeafNode(otherlv_1, grammarAccess.getDate_And_TimeAccess().getNumberSignKeyword_1());
            		
            // InternalStructuredTextParser.g:4224:3: ( (lv_value_2_0= ruleDate_And_Time_Value ) )
            // InternalStructuredTextParser.g:4225:4: (lv_value_2_0= ruleDate_And_Time_Value )
            {
            // InternalStructuredTextParser.g:4225:4: (lv_value_2_0= ruleDate_And_Time_Value )
            // InternalStructuredTextParser.g:4226:5: lv_value_2_0= ruleDate_And_Time_Value
            {

            					newCompositeNode(grammarAccess.getDate_And_TimeAccess().getValueDate_And_Time_ValueParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleDate_And_Time_Value();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDate_And_TimeRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Date_And_Time_Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDate_And_Time"


    // $ANTLR start "entryRuleDate_And_Time_Value"
    // InternalStructuredTextParser.g:4247:1: entryRuleDate_And_Time_Value returns [String current=null] : iv_ruleDate_And_Time_Value= ruleDate_And_Time_Value EOF ;
    public final String entryRuleDate_And_Time_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate_And_Time_Value = null;


        try {
            // InternalStructuredTextParser.g:4247:59: (iv_ruleDate_And_Time_Value= ruleDate_And_Time_Value EOF )
            // InternalStructuredTextParser.g:4248:2: iv_ruleDate_And_Time_Value= ruleDate_And_Time_Value EOF
            {
             newCompositeNode(grammarAccess.getDate_And_Time_ValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDate_And_Time_Value=ruleDate_And_Time_Value();

            state._fsp--;

             current =iv_ruleDate_And_Time_Value.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDate_And_Time_Value"


    // $ANTLR start "ruleDate_And_Time_Value"
    // InternalStructuredTextParser.g:4254:1: ruleDate_And_Time_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime ) ;
    public final AntlrDatatypeRuleToken ruleDate_And_Time_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Date_Literal_0 = null;

        AntlrDatatypeRuleToken this_Daytime_2 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:4260:2: ( (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime ) )
            // InternalStructuredTextParser.g:4261:2: (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime )
            {
            // InternalStructuredTextParser.g:4261:2: (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime )
            // InternalStructuredTextParser.g:4262:3: this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime
            {

            			newCompositeNode(grammarAccess.getDate_And_Time_ValueAccess().getDate_LiteralParserRuleCall_0());
            		
            pushFollow(FOLLOW_66);
            this_Date_Literal_0=ruleDate_Literal();

            state._fsp--;


            			current.merge(this_Date_Literal_0);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,HyphenMinus,FOLLOW_12); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getDate_And_Time_ValueAccess().getHyphenMinusKeyword_1());
            		

            			newCompositeNode(grammarAccess.getDate_And_Time_ValueAccess().getDaytimeParserRuleCall_2());
            		
            pushFollow(FOLLOW_2);
            this_Daytime_2=ruleDaytime();

            state._fsp--;


            			current.merge(this_Daytime_2);
            		

            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDate_And_Time_Value"


    // $ANTLR start "entryRuleType_Name"
    // InternalStructuredTextParser.g:4291:1: entryRuleType_Name returns [String current=null] : iv_ruleType_Name= ruleType_Name EOF ;
    public final String entryRuleType_Name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleType_Name = null;


        try {
            // InternalStructuredTextParser.g:4291:49: (iv_ruleType_Name= ruleType_Name EOF )
            // InternalStructuredTextParser.g:4292:2: iv_ruleType_Name= ruleType_Name EOF
            {
             newCompositeNode(grammarAccess.getType_NameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleType_Name=ruleType_Name();

            state._fsp--;

             current =iv_ruleType_Name.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleType_Name"


    // $ANTLR start "ruleType_Name"
    // InternalStructuredTextParser.g:4298:1: ruleType_Name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL | kw= LWORD | kw= DWORD | kw= WORD | kw= BYTE ) ;
    public final AntlrDatatypeRuleToken ruleType_Name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4304:2: ( (this_ID_0= RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL | kw= LWORD | kw= DWORD | kw= WORD | kw= BYTE ) )
            // InternalStructuredTextParser.g:4305:2: (this_ID_0= RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL | kw= LWORD | kw= DWORD | kw= WORD | kw= BYTE )
            {
            // InternalStructuredTextParser.g:4305:2: (this_ID_0= RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL | kw= LWORD | kw= DWORD | kw= WORD | kw= BYTE )
            int alt58=30;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt58=1;
                }
                break;
            case DINT:
                {
                alt58=2;
                }
                break;
            case INT:
                {
                alt58=3;
                }
                break;
            case SINT:
                {
                alt58=4;
                }
                break;
            case LINT:
                {
                alt58=5;
                }
                break;
            case UINT:
                {
                alt58=6;
                }
                break;
            case USINT:
                {
                alt58=7;
                }
                break;
            case UDINT:
                {
                alt58=8;
                }
                break;
            case ULINT:
                {
                alt58=9;
                }
                break;
            case REAL:
                {
                alt58=10;
                }
                break;
            case LREAL:
                {
                alt58=11;
                }
                break;
            case STRING:
                {
                alt58=12;
                }
                break;
            case WSTRING:
                {
                alt58=13;
                }
                break;
            case CHAR:
                {
                alt58=14;
                }
                break;
            case WCHAR:
                {
                alt58=15;
                }
                break;
            case TIME:
                {
                alt58=16;
                }
                break;
            case LTIME:
                {
                alt58=17;
                }
                break;
            case TIME_OF_DAY:
                {
                alt58=18;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt58=19;
                }
                break;
            case TOD:
                {
                alt58=20;
                }
                break;
            case LTOD:
                {
                alt58=21;
                }
                break;
            case DATE:
                {
                alt58=22;
                }
                break;
            case LDATE:
                {
                alt58=23;
                }
                break;
            case DATE_AND_TIME:
                {
                alt58=24;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt58=25;
                }
                break;
            case BOOL:
                {
                alt58=26;
                }
                break;
            case LWORD:
                {
                alt58=27;
                }
                break;
            case DWORD:
                {
                alt58=28;
                }
                break;
            case WORD:
                {
                alt58=29;
                }
                break;
            case BYTE:
                {
                alt58=30;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }

            switch (alt58) {
                case 1 :
                    // InternalStructuredTextParser.g:4306:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4314:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getDINTKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4320:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getINTKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4326:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getSINTKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:4332:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLINTKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:4338:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getUINTKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:4344:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getUSINTKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:4350:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getUDINTKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalStructuredTextParser.g:4356:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getULINTKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalStructuredTextParser.g:4362:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getREALKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalStructuredTextParser.g:4368:3: kw= LREAL
                    {
                    kw=(Token)match(input,LREAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLREALKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalStructuredTextParser.g:4374:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getSTRINGKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalStructuredTextParser.g:4380:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getWSTRINGKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalStructuredTextParser.g:4386:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getCHARKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalStructuredTextParser.g:4392:3: kw= WCHAR
                    {
                    kw=(Token)match(input,WCHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getWCHARKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalStructuredTextParser.g:4398:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getTIMEKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalStructuredTextParser.g:4404:3: kw= LTIME
                    {
                    kw=(Token)match(input,LTIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLTIMEKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalStructuredTextParser.g:4410:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalStructuredTextParser.g:4416:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalStructuredTextParser.g:4422:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getTODKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalStructuredTextParser.g:4428:3: kw= LTOD
                    {
                    kw=(Token)match(input,LTOD,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLTODKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalStructuredTextParser.g:4434:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getDATEKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalStructuredTextParser.g:4440:3: kw= LDATE
                    {
                    kw=(Token)match(input,LDATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLDATEKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalStructuredTextParser.g:4446:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalStructuredTextParser.g:4452:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalStructuredTextParser.g:4458:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getBOOLKeyword_25());
                    		

                    }
                    break;
                case 27 :
                    // InternalStructuredTextParser.g:4464:3: kw= LWORD
                    {
                    kw=(Token)match(input,LWORD,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLWORDKeyword_26());
                    		

                    }
                    break;
                case 28 :
                    // InternalStructuredTextParser.g:4470:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getDWORDKeyword_27());
                    		

                    }
                    break;
                case 29 :
                    // InternalStructuredTextParser.g:4476:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getWORDKeyword_28());
                    		

                    }
                    break;
                case 30 :
                    // InternalStructuredTextParser.g:4482:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getBYTEKeyword_29());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleType_Name"


    // $ANTLR start "ruleOr_Operator"
    // InternalStructuredTextParser.g:4491:1: ruleOr_Operator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOr_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4497:2: ( (enumLiteral_0= OR ) )
            // InternalStructuredTextParser.g:4498:2: (enumLiteral_0= OR )
            {
            // InternalStructuredTextParser.g:4498:2: (enumLiteral_0= OR )
            // InternalStructuredTextParser.g:4499:3: enumLiteral_0= OR
            {
            enumLiteral_0=(Token)match(input,OR,FOLLOW_2); 

            			current = grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOr_Operator"


    // $ANTLR start "ruleXor_Operator"
    // InternalStructuredTextParser.g:4508:1: ruleXor_Operator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXor_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4514:2: ( (enumLiteral_0= XOR ) )
            // InternalStructuredTextParser.g:4515:2: (enumLiteral_0= XOR )
            {
            // InternalStructuredTextParser.g:4515:2: (enumLiteral_0= XOR )
            // InternalStructuredTextParser.g:4516:3: enumLiteral_0= XOR
            {
            enumLiteral_0=(Token)match(input,XOR,FOLLOW_2); 

            			current = grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXor_Operator"


    // $ANTLR start "ruleAnd_Operator"
    // InternalStructuredTextParser.g:4525:1: ruleAnd_Operator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAnd_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4531:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalStructuredTextParser.g:4532:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalStructuredTextParser.g:4532:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==AND) ) {
                alt59=1;
            }
            else if ( (LA59_0==Ampersand) ) {
                alt59=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // InternalStructuredTextParser.g:4533:3: (enumLiteral_0= AND )
                    {
                    // InternalStructuredTextParser.g:4533:3: (enumLiteral_0= AND )
                    // InternalStructuredTextParser.g:4534:4: enumLiteral_0= AND
                    {
                    enumLiteral_0=(Token)match(input,AND,FOLLOW_2); 

                    				current = grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4541:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalStructuredTextParser.g:4541:3: (enumLiteral_1= Ampersand )
                    // InternalStructuredTextParser.g:4542:4: enumLiteral_1= Ampersand
                    {
                    enumLiteral_1=(Token)match(input,Ampersand,FOLLOW_2); 

                    				current = grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnd_Operator"


    // $ANTLR start "ruleCompare_Operator"
    // InternalStructuredTextParser.g:4552:1: ruleCompare_Operator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleCompare_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4558:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalStructuredTextParser.g:4559:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalStructuredTextParser.g:4559:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==EqualsSign) ) {
                alt60=1;
            }
            else if ( (LA60_0==LessThanSignGreaterThanSign) ) {
                alt60=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // InternalStructuredTextParser.g:4560:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalStructuredTextParser.g:4560:3: (enumLiteral_0= EqualsSign )
                    // InternalStructuredTextParser.g:4561:4: enumLiteral_0= EqualsSign
                    {
                    enumLiteral_0=(Token)match(input,EqualsSign,FOLLOW_2); 

                    				current = grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4568:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalStructuredTextParser.g:4568:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalStructuredTextParser.g:4569:4: enumLiteral_1= LessThanSignGreaterThanSign
                    {
                    enumLiteral_1=(Token)match(input,LessThanSignGreaterThanSign,FOLLOW_2); 

                    				current = grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCompare_Operator"


    // $ANTLR start "ruleEqu_Operator"
    // InternalStructuredTextParser.g:4579:1: ruleEqu_Operator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleEqu_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4585:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalStructuredTextParser.g:4586:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalStructuredTextParser.g:4586:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt61=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt61=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt61=2;
                }
                break;
            case GreaterThanSign:
                {
                alt61=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt61=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }

            switch (alt61) {
                case 1 :
                    // InternalStructuredTextParser.g:4587:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalStructuredTextParser.g:4587:3: (enumLiteral_0= LessThanSign )
                    // InternalStructuredTextParser.g:4588:4: enumLiteral_0= LessThanSign
                    {
                    enumLiteral_0=(Token)match(input,LessThanSign,FOLLOW_2); 

                    				current = grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4595:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalStructuredTextParser.g:4595:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalStructuredTextParser.g:4596:4: enumLiteral_1= LessThanSignEqualsSign
                    {
                    enumLiteral_1=(Token)match(input,LessThanSignEqualsSign,FOLLOW_2); 

                    				current = grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4603:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalStructuredTextParser.g:4603:3: (enumLiteral_2= GreaterThanSign )
                    // InternalStructuredTextParser.g:4604:4: enumLiteral_2= GreaterThanSign
                    {
                    enumLiteral_2=(Token)match(input,GreaterThanSign,FOLLOW_2); 

                    				current = grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4611:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalStructuredTextParser.g:4611:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalStructuredTextParser.g:4612:4: enumLiteral_3= GreaterThanSignEqualsSign
                    {
                    enumLiteral_3=(Token)match(input,GreaterThanSignEqualsSign,FOLLOW_2); 

                    				current = grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEqu_Operator"


    // $ANTLR start "ruleAdd_Operator"
    // InternalStructuredTextParser.g:4622:1: ruleAdd_Operator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAdd_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4628:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalStructuredTextParser.g:4629:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalStructuredTextParser.g:4629:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==PlusSign) ) {
                alt62=1;
            }
            else if ( (LA62_0==HyphenMinus) ) {
                alt62=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // InternalStructuredTextParser.g:4630:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalStructuredTextParser.g:4630:3: (enumLiteral_0= PlusSign )
                    // InternalStructuredTextParser.g:4631:4: enumLiteral_0= PlusSign
                    {
                    enumLiteral_0=(Token)match(input,PlusSign,FOLLOW_2); 

                    				current = grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4638:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:4638:3: (enumLiteral_1= HyphenMinus )
                    // InternalStructuredTextParser.g:4639:4: enumLiteral_1= HyphenMinus
                    {
                    enumLiteral_1=(Token)match(input,HyphenMinus,FOLLOW_2); 

                    				current = grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAdd_Operator"


    // $ANTLR start "ruleTerm_Operator"
    // InternalStructuredTextParser.g:4649:1: ruleTerm_Operator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleTerm_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4655:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalStructuredTextParser.g:4656:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalStructuredTextParser.g:4656:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt63=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt63=1;
                }
                break;
            case Solidus:
                {
                alt63=2;
                }
                break;
            case MOD:
                {
                alt63=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // InternalStructuredTextParser.g:4657:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalStructuredTextParser.g:4657:3: (enumLiteral_0= Asterisk )
                    // InternalStructuredTextParser.g:4658:4: enumLiteral_0= Asterisk
                    {
                    enumLiteral_0=(Token)match(input,Asterisk,FOLLOW_2); 

                    				current = grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4665:3: (enumLiteral_1= Solidus )
                    {
                    // InternalStructuredTextParser.g:4665:3: (enumLiteral_1= Solidus )
                    // InternalStructuredTextParser.g:4666:4: enumLiteral_1= Solidus
                    {
                    enumLiteral_1=(Token)match(input,Solidus,FOLLOW_2); 

                    				current = grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4673:3: (enumLiteral_2= MOD )
                    {
                    // InternalStructuredTextParser.g:4673:3: (enumLiteral_2= MOD )
                    // InternalStructuredTextParser.g:4674:4: enumLiteral_2= MOD
                    {
                    enumLiteral_2=(Token)match(input,MOD,FOLLOW_2); 

                    				current = grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTerm_Operator"


    // $ANTLR start "rulePower_Operator"
    // InternalStructuredTextParser.g:4684:1: rulePower_Operator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePower_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4690:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalStructuredTextParser.g:4691:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalStructuredTextParser.g:4691:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalStructuredTextParser.g:4692:3: enumLiteral_0= AsteriskAsterisk
            {
            enumLiteral_0=(Token)match(input,AsteriskAsterisk,FOLLOW_2); 

            			current = grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePower_Operator"


    // $ANTLR start "ruleUnary_Operator"
    // InternalStructuredTextParser.g:4701:1: ruleUnary_Operator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnary_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4707:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalStructuredTextParser.g:4708:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalStructuredTextParser.g:4708:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt64=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt64=1;
                }
                break;
            case PlusSign:
                {
                alt64=2;
                }
                break;
            case NOT:
                {
                alt64=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }

            switch (alt64) {
                case 1 :
                    // InternalStructuredTextParser.g:4709:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:4709:3: (enumLiteral_0= HyphenMinus )
                    // InternalStructuredTextParser.g:4710:4: enumLiteral_0= HyphenMinus
                    {
                    enumLiteral_0=(Token)match(input,HyphenMinus,FOLLOW_2); 

                    				current = grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4717:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalStructuredTextParser.g:4717:3: (enumLiteral_1= PlusSign )
                    // InternalStructuredTextParser.g:4718:4: enumLiteral_1= PlusSign
                    {
                    enumLiteral_1=(Token)match(input,PlusSign,FOLLOW_2); 

                    				current = grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4725:3: (enumLiteral_2= NOT )
                    {
                    // InternalStructuredTextParser.g:4725:3: (enumLiteral_2= NOT )
                    // InternalStructuredTextParser.g:4726:4: enumLiteral_2= NOT
                    {
                    enumLiteral_2=(Token)match(input,NOT,FOLLOW_2); 

                    				current = grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnary_Operator"


    // $ANTLR start "ruleDuration_Unit"
    // InternalStructuredTextParser.g:4736:1: ruleDuration_Unit returns [Enumerator current=null] : ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) ) ;
    public final Enumerator ruleDuration_Unit() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4742:2: ( ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) ) )
            // InternalStructuredTextParser.g:4743:2: ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) )
            {
            // InternalStructuredTextParser.g:4743:2: ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) )
            int alt65=7;
            switch ( input.LA(1) ) {
            case D_1:
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
            case Ms:
                {
                alt65=5;
                }
                break;
            case Us:
                {
                alt65=6;
                }
                break;
            case Ns:
                {
                alt65=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }

            switch (alt65) {
                case 1 :
                    // InternalStructuredTextParser.g:4744:3: (enumLiteral_0= D_1 )
                    {
                    // InternalStructuredTextParser.g:4744:3: (enumLiteral_0= D_1 )
                    // InternalStructuredTextParser.g:4745:4: enumLiteral_0= D_1
                    {
                    enumLiteral_0=(Token)match(input,D_1,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getDAYSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDuration_UnitAccess().getDAYSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4752:3: (enumLiteral_1= H )
                    {
                    // InternalStructuredTextParser.g:4752:3: (enumLiteral_1= H )
                    // InternalStructuredTextParser.g:4753:4: enumLiteral_1= H
                    {
                    enumLiteral_1=(Token)match(input,H,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getHOURSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDuration_UnitAccess().getHOURSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4760:3: (enumLiteral_2= M )
                    {
                    // InternalStructuredTextParser.g:4760:3: (enumLiteral_2= M )
                    // InternalStructuredTextParser.g:4761:4: enumLiteral_2= M
                    {
                    enumLiteral_2=(Token)match(input,M,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getMINUTESEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDuration_UnitAccess().getMINUTESEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4768:3: (enumLiteral_3= S )
                    {
                    // InternalStructuredTextParser.g:4768:3: (enumLiteral_3= S )
                    // InternalStructuredTextParser.g:4769:4: enumLiteral_3= S
                    {
                    enumLiteral_3=(Token)match(input,S,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getSECONDSEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getDuration_UnitAccess().getSECONDSEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:4776:3: (enumLiteral_4= Ms )
                    {
                    // InternalStructuredTextParser.g:4776:3: (enumLiteral_4= Ms )
                    // InternalStructuredTextParser.g:4777:4: enumLiteral_4= Ms
                    {
                    enumLiteral_4=(Token)match(input,Ms,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getMILLISEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getDuration_UnitAccess().getMILLISEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:4784:3: (enumLiteral_5= Us )
                    {
                    // InternalStructuredTextParser.g:4784:3: (enumLiteral_5= Us )
                    // InternalStructuredTextParser.g:4785:4: enumLiteral_5= Us
                    {
                    enumLiteral_5=(Token)match(input,Us,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getMICROSEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getDuration_UnitAccess().getMICROSEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:4792:3: (enumLiteral_6= Ns )
                    {
                    // InternalStructuredTextParser.g:4792:3: (enumLiteral_6= Ns )
                    // InternalStructuredTextParser.g:4793:4: enumLiteral_6= Ns
                    {
                    enumLiteral_6=(Token)match(input,Ns,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getNANOSEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getDuration_UnitAccess().getNANOSEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDuration_Unit"


    // $ANTLR start "ruleInt_Type_Name"
    // InternalStructuredTextParser.g:4803:1: ruleInt_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) ) ;
    public final Enumerator ruleInt_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4809:2: ( ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) ) )
            // InternalStructuredTextParser.g:4810:2: ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) )
            {
            // InternalStructuredTextParser.g:4810:2: ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) )
            int alt66=8;
            switch ( input.LA(1) ) {
            case DINT:
                {
                alt66=1;
                }
                break;
            case INT:
                {
                alt66=2;
                }
                break;
            case SINT:
                {
                alt66=3;
                }
                break;
            case LINT:
                {
                alt66=4;
                }
                break;
            case UINT:
                {
                alt66=5;
                }
                break;
            case USINT:
                {
                alt66=6;
                }
                break;
            case UDINT:
                {
                alt66=7;
                }
                break;
            case ULINT:
                {
                alt66=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    // InternalStructuredTextParser.g:4811:3: (enumLiteral_0= DINT )
                    {
                    // InternalStructuredTextParser.g:4811:3: (enumLiteral_0= DINT )
                    // InternalStructuredTextParser.g:4812:4: enumLiteral_0= DINT
                    {
                    enumLiteral_0=(Token)match(input,DINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4819:3: (enumLiteral_1= INT )
                    {
                    // InternalStructuredTextParser.g:4819:3: (enumLiteral_1= INT )
                    // InternalStructuredTextParser.g:4820:4: enumLiteral_1= INT
                    {
                    enumLiteral_1=(Token)match(input,INT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4827:3: (enumLiteral_2= SINT )
                    {
                    // InternalStructuredTextParser.g:4827:3: (enumLiteral_2= SINT )
                    // InternalStructuredTextParser.g:4828:4: enumLiteral_2= SINT
                    {
                    enumLiteral_2=(Token)match(input,SINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4835:3: (enumLiteral_3= LINT )
                    {
                    // InternalStructuredTextParser.g:4835:3: (enumLiteral_3= LINT )
                    // InternalStructuredTextParser.g:4836:4: enumLiteral_3= LINT
                    {
                    enumLiteral_3=(Token)match(input,LINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:4843:3: (enumLiteral_4= UINT )
                    {
                    // InternalStructuredTextParser.g:4843:3: (enumLiteral_4= UINT )
                    // InternalStructuredTextParser.g:4844:4: enumLiteral_4= UINT
                    {
                    enumLiteral_4=(Token)match(input,UINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:4851:3: (enumLiteral_5= USINT )
                    {
                    // InternalStructuredTextParser.g:4851:3: (enumLiteral_5= USINT )
                    // InternalStructuredTextParser.g:4852:4: enumLiteral_5= USINT
                    {
                    enumLiteral_5=(Token)match(input,USINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:4859:3: (enumLiteral_6= UDINT )
                    {
                    // InternalStructuredTextParser.g:4859:3: (enumLiteral_6= UDINT )
                    // InternalStructuredTextParser.g:4860:4: enumLiteral_6= UDINT
                    {
                    enumLiteral_6=(Token)match(input,UDINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:4867:3: (enumLiteral_7= ULINT )
                    {
                    // InternalStructuredTextParser.g:4867:3: (enumLiteral_7= ULINT )
                    // InternalStructuredTextParser.g:4868:4: enumLiteral_7= ULINT
                    {
                    enumLiteral_7=(Token)match(input,ULINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInt_Type_Name"


    // $ANTLR start "ruleReal_Type_Name"
    // InternalStructuredTextParser.g:4878:1: ruleReal_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) ) ;
    public final Enumerator ruleReal_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4884:2: ( ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) ) )
            // InternalStructuredTextParser.g:4885:2: ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) )
            {
            // InternalStructuredTextParser.g:4885:2: ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==REAL) ) {
                alt67=1;
            }
            else if ( (LA67_0==LREAL) ) {
                alt67=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // InternalStructuredTextParser.g:4886:3: (enumLiteral_0= REAL )
                    {
                    // InternalStructuredTextParser.g:4886:3: (enumLiteral_0= REAL )
                    // InternalStructuredTextParser.g:4887:4: enumLiteral_0= REAL
                    {
                    enumLiteral_0=(Token)match(input,REAL,FOLLOW_2); 

                    				current = grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4894:3: (enumLiteral_1= LREAL )
                    {
                    // InternalStructuredTextParser.g:4894:3: (enumLiteral_1= LREAL )
                    // InternalStructuredTextParser.g:4895:4: enumLiteral_1= LREAL
                    {
                    enumLiteral_1=(Token)match(input,LREAL,FOLLOW_2); 

                    				current = grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReal_Type_Name"


    // $ANTLR start "ruleString_Type_Name"
    // InternalStructuredTextParser.g:4905:1: ruleString_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= STRING ) | (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) ) ;
    public final Enumerator ruleString_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4911:2: ( ( (enumLiteral_0= STRING ) | (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) ) )
            // InternalStructuredTextParser.g:4912:2: ( (enumLiteral_0= STRING ) | (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) )
            {
            // InternalStructuredTextParser.g:4912:2: ( (enumLiteral_0= STRING ) | (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) )
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
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }

            switch (alt68) {
                case 1 :
                    // InternalStructuredTextParser.g:4913:3: (enumLiteral_0= STRING )
                    {
                    // InternalStructuredTextParser.g:4913:3: (enumLiteral_0= STRING )
                    // InternalStructuredTextParser.g:4914:4: enumLiteral_0= STRING
                    {
                    enumLiteral_0=(Token)match(input,STRING,FOLLOW_2); 

                    				current = grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4921:3: (enumLiteral_1= WSTRING )
                    {
                    // InternalStructuredTextParser.g:4921:3: (enumLiteral_1= WSTRING )
                    // InternalStructuredTextParser.g:4922:4: enumLiteral_1= WSTRING
                    {
                    enumLiteral_1=(Token)match(input,WSTRING,FOLLOW_2); 

                    				current = grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4929:3: (enumLiteral_2= CHAR )
                    {
                    // InternalStructuredTextParser.g:4929:3: (enumLiteral_2= CHAR )
                    // InternalStructuredTextParser.g:4930:4: enumLiteral_2= CHAR
                    {
                    enumLiteral_2=(Token)match(input,CHAR,FOLLOW_2); 

                    				current = grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4937:3: (enumLiteral_3= WCHAR )
                    {
                    // InternalStructuredTextParser.g:4937:3: (enumLiteral_3= WCHAR )
                    // InternalStructuredTextParser.g:4938:4: enumLiteral_3= WCHAR
                    {
                    enumLiteral_3=(Token)match(input,WCHAR,FOLLOW_2); 

                    				current = grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleString_Type_Name"


    // $ANTLR start "ruleTime_Type_Name"
    // InternalStructuredTextParser.g:4948:1: ruleTime_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) ) ;
    public final Enumerator ruleTime_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4954:2: ( ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) ) )
            // InternalStructuredTextParser.g:4955:2: ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) )
            {
            // InternalStructuredTextParser.g:4955:2: ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) )
            int alt69=4;
            switch ( input.LA(1) ) {
            case TIME:
                {
                alt69=1;
                }
                break;
            case LTIME:
                {
                alt69=2;
                }
                break;
            case T:
                {
                alt69=3;
                }
                break;
            case LT:
                {
                alt69=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // InternalStructuredTextParser.g:4956:3: (enumLiteral_0= TIME )
                    {
                    // InternalStructuredTextParser.g:4956:3: (enumLiteral_0= TIME )
                    // InternalStructuredTextParser.g:4957:4: enumLiteral_0= TIME
                    {
                    enumLiteral_0=(Token)match(input,TIME,FOLLOW_2); 

                    				current = grammarAccess.getTime_Type_NameAccess().getTIMEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTime_Type_NameAccess().getTIMEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4964:3: (enumLiteral_1= LTIME )
                    {
                    // InternalStructuredTextParser.g:4964:3: (enumLiteral_1= LTIME )
                    // InternalStructuredTextParser.g:4965:4: enumLiteral_1= LTIME
                    {
                    enumLiteral_1=(Token)match(input,LTIME,FOLLOW_2); 

                    				current = grammarAccess.getTime_Type_NameAccess().getLTIMEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTime_Type_NameAccess().getLTIMEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4972:3: (enumLiteral_2= T )
                    {
                    // InternalStructuredTextParser.g:4972:3: (enumLiteral_2= T )
                    // InternalStructuredTextParser.g:4973:4: enumLiteral_2= T
                    {
                    enumLiteral_2=(Token)match(input,T,FOLLOW_2); 

                    				current = grammarAccess.getTime_Type_NameAccess().getTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTime_Type_NameAccess().getTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4980:3: (enumLiteral_3= LT )
                    {
                    // InternalStructuredTextParser.g:4980:3: (enumLiteral_3= LT )
                    // InternalStructuredTextParser.g:4981:4: enumLiteral_3= LT
                    {
                    enumLiteral_3=(Token)match(input,LT,FOLLOW_2); 

                    				current = grammarAccess.getTime_Type_NameAccess().getLTEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getTime_Type_NameAccess().getLTEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTime_Type_Name"


    // $ANTLR start "ruleTod_Type_Name"
    // InternalStructuredTextParser.g:4991:1: ruleTod_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) ) ;
    public final Enumerator ruleTod_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4997:2: ( ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) ) )
            // InternalStructuredTextParser.g:4998:2: ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) )
            {
            // InternalStructuredTextParser.g:4998:2: ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) )
            int alt70=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt70=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt70=2;
                }
                break;
            case TOD:
                {
                alt70=3;
                }
                break;
            case LTOD:
                {
                alt70=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // InternalStructuredTextParser.g:4999:3: (enumLiteral_0= TIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:4999:3: (enumLiteral_0= TIME_OF_DAY )
                    // InternalStructuredTextParser.g:5000:4: enumLiteral_0= TIME_OF_DAY
                    {
                    enumLiteral_0=(Token)match(input,TIME_OF_DAY,FOLLOW_2); 

                    				current = grammarAccess.getTod_Type_NameAccess().getTIME_OF_DAYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTod_Type_NameAccess().getTIME_OF_DAYEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:5007:3: (enumLiteral_1= LTIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:5007:3: (enumLiteral_1= LTIME_OF_DAY )
                    // InternalStructuredTextParser.g:5008:4: enumLiteral_1= LTIME_OF_DAY
                    {
                    enumLiteral_1=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); 

                    				current = grammarAccess.getTod_Type_NameAccess().getLTIME_OF_DAYEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTod_Type_NameAccess().getLTIME_OF_DAYEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:5015:3: (enumLiteral_2= TOD )
                    {
                    // InternalStructuredTextParser.g:5015:3: (enumLiteral_2= TOD )
                    // InternalStructuredTextParser.g:5016:4: enumLiteral_2= TOD
                    {
                    enumLiteral_2=(Token)match(input,TOD,FOLLOW_2); 

                    				current = grammarAccess.getTod_Type_NameAccess().getTODEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTod_Type_NameAccess().getTODEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:5023:3: (enumLiteral_3= LTOD )
                    {
                    // InternalStructuredTextParser.g:5023:3: (enumLiteral_3= LTOD )
                    // InternalStructuredTextParser.g:5024:4: enumLiteral_3= LTOD
                    {
                    enumLiteral_3=(Token)match(input,LTOD,FOLLOW_2); 

                    				current = grammarAccess.getTod_Type_NameAccess().getLTODEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getTod_Type_NameAccess().getLTODEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTod_Type_Name"


    // $ANTLR start "ruleDate_Type_Name"
    // InternalStructuredTextParser.g:5034:1: ruleDate_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) ) ;
    public final Enumerator ruleDate_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:5040:2: ( ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) ) )
            // InternalStructuredTextParser.g:5041:2: ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) )
            {
            // InternalStructuredTextParser.g:5041:2: ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) )
            int alt71=4;
            switch ( input.LA(1) ) {
            case DATE:
                {
                alt71=1;
                }
                break;
            case LDATE:
                {
                alt71=2;
                }
                break;
            case D_1:
                {
                alt71=3;
                }
                break;
            case LD:
                {
                alt71=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 71, 0, input);

                throw nvae;
            }

            switch (alt71) {
                case 1 :
                    // InternalStructuredTextParser.g:5042:3: (enumLiteral_0= DATE )
                    {
                    // InternalStructuredTextParser.g:5042:3: (enumLiteral_0= DATE )
                    // InternalStructuredTextParser.g:5043:4: enumLiteral_0= DATE
                    {
                    enumLiteral_0=(Token)match(input,DATE,FOLLOW_2); 

                    				current = grammarAccess.getDate_Type_NameAccess().getDATEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDate_Type_NameAccess().getDATEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:5050:3: (enumLiteral_1= LDATE )
                    {
                    // InternalStructuredTextParser.g:5050:3: (enumLiteral_1= LDATE )
                    // InternalStructuredTextParser.g:5051:4: enumLiteral_1= LDATE
                    {
                    enumLiteral_1=(Token)match(input,LDATE,FOLLOW_2); 

                    				current = grammarAccess.getDate_Type_NameAccess().getLDATEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDate_Type_NameAccess().getLDATEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:5058:3: (enumLiteral_2= D_1 )
                    {
                    // InternalStructuredTextParser.g:5058:3: (enumLiteral_2= D_1 )
                    // InternalStructuredTextParser.g:5059:4: enumLiteral_2= D_1
                    {
                    enumLiteral_2=(Token)match(input,D_1,FOLLOW_2); 

                    				current = grammarAccess.getDate_Type_NameAccess().getDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDate_Type_NameAccess().getDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:5066:3: (enumLiteral_3= LD )
                    {
                    // InternalStructuredTextParser.g:5066:3: (enumLiteral_3= LD )
                    // InternalStructuredTextParser.g:5067:4: enumLiteral_3= LD
                    {
                    enumLiteral_3=(Token)match(input,LD,FOLLOW_2); 

                    				current = grammarAccess.getDate_Type_NameAccess().getLDEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getDate_Type_NameAccess().getLDEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDate_Type_Name"


    // $ANTLR start "ruleDT_Type_Name"
    // InternalStructuredTextParser.g:5077:1: ruleDT_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) ) ;
    public final Enumerator ruleDT_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:5083:2: ( ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) ) )
            // InternalStructuredTextParser.g:5084:2: ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) )
            {
            // InternalStructuredTextParser.g:5084:2: ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) )
            int alt72=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt72=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt72=2;
                }
                break;
            case DT:
                {
                alt72=3;
                }
                break;
            case LDT:
                {
                alt72=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // InternalStructuredTextParser.g:5085:3: (enumLiteral_0= DATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:5085:3: (enumLiteral_0= DATE_AND_TIME )
                    // InternalStructuredTextParser.g:5086:4: enumLiteral_0= DATE_AND_TIME
                    {
                    enumLiteral_0=(Token)match(input,DATE_AND_TIME,FOLLOW_2); 

                    				current = grammarAccess.getDT_Type_NameAccess().getDATE_AND_TIMEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDT_Type_NameAccess().getDATE_AND_TIMEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:5093:3: (enumLiteral_1= LDATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:5093:3: (enumLiteral_1= LDATE_AND_TIME )
                    // InternalStructuredTextParser.g:5094:4: enumLiteral_1= LDATE_AND_TIME
                    {
                    enumLiteral_1=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); 

                    				current = grammarAccess.getDT_Type_NameAccess().getLDATE_AND_TIMEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDT_Type_NameAccess().getLDATE_AND_TIMEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:5101:3: (enumLiteral_2= DT )
                    {
                    // InternalStructuredTextParser.g:5101:3: (enumLiteral_2= DT )
                    // InternalStructuredTextParser.g:5102:4: enumLiteral_2= DT
                    {
                    enumLiteral_2=(Token)match(input,DT,FOLLOW_2); 

                    				current = grammarAccess.getDT_Type_NameAccess().getDTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDT_Type_NameAccess().getDTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:5109:3: (enumLiteral_3= LDT )
                    {
                    // InternalStructuredTextParser.g:5109:3: (enumLiteral_3= LDT )
                    // InternalStructuredTextParser.g:5110:4: enumLiteral_3= LDT
                    {
                    enumLiteral_3=(Token)match(input,LDT,FOLLOW_2); 

                    				current = grammarAccess.getDT_Type_NameAccess().getLDTEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getDT_Type_NameAccess().getLDTEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDT_Type_Name"


    // $ANTLR start "ruleBool_Type_Name"
    // InternalStructuredTextParser.g:5120:1: ruleBool_Type_Name returns [Enumerator current=null] : (enumLiteral_0= BOOL ) ;
    public final Enumerator ruleBool_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:5126:2: ( (enumLiteral_0= BOOL ) )
            // InternalStructuredTextParser.g:5127:2: (enumLiteral_0= BOOL )
            {
            // InternalStructuredTextParser.g:5127:2: (enumLiteral_0= BOOL )
            // InternalStructuredTextParser.g:5128:3: enumLiteral_0= BOOL
            {
            enumLiteral_0=(Token)match(input,BOOL,FOLLOW_2); 

            			current = grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBool_Type_Name"

    // Delegated rules


    protected DFA27 dfa27 = new DFA27(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\5\uffff\3\4\2\uffff";
    static final String dfa_3s = "\1\4\2\60\2\uffff\3\10\1\125\1\uffff";
    static final String dfa_4s = "\1\172\2\166\2\uffff\3\147\1\127\1\uffff";
    static final String dfa_5s = "\3\uffff\1\1\1\2\4\uffff\1\3";
    static final String dfa_6s = "\12\uffff}>";
    static final String[] dfa_7s = {
            "\4\11\7\uffff\1\11\3\uffff\1\11\3\uffff\4\11\2\uffff\2\11\1\uffff\2\11\1\uffff\1\11\2\uffff\3\11\2\uffff\4\11\1\uffff\1\10\2\11\7\uffff\2\11\1\uffff\1\3\1\11\14\uffff\1\7\1\uffff\1\11\1\6\10\uffff\1\4\2\uffff\1\2\1\uffff\1\1\10\uffff\1\5\3\uffff\1\11\10\uffff\1\4\4\11\1\uffff\1\11\1\uffff\1\11",
            "\1\3\32\uffff\1\3\2\uffff\1\3\10\uffff\1\3\15\uffff\1\3\14\uffff\1\3\3\uffff\1\11",
            "\1\3\32\uffff\1\3\2\uffff\1\3\10\uffff\1\3\15\uffff\1\3\14\uffff\1\3\3\uffff\1\11",
            "",
            "",
            "\1\4\46\uffff\1\4\4\uffff\5\4\3\uffff\1\4\3\uffff\2\4\2\uffff\2\4\1\uffff\1\4\1\uffff\2\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
            "\1\4\46\uffff\1\4\4\uffff\5\4\3\uffff\1\4\3\uffff\2\4\2\uffff\2\4\1\uffff\1\4\1\uffff\2\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
            "\1\4\46\uffff\1\4\4\uffff\5\4\3\uffff\1\4\3\uffff\2\4\2\uffff\2\4\1\uffff\1\4\1\uffff\2\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
            "\1\11\1\uffff\1\4",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "2016:2: ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000004400L,0x0004000000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0201042410060800L,0x0004002100005800L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000100L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0004002000004800L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x440D79DB6F3880F0L,0x0004000000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x4C0779CB678880F0L,0x0578022014006800L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0201042410060802L,0x0004002100005800L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x6C0779CB678880F0L,0x057C022014806800L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0201062410470800L,0x0004002100005800L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000020000410000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x4C077BCB678890F0L,0x0578022014006800L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x0000000088000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0201042410062800L,0x0004002100005800L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0201042410060A00L,0x0004002100005800L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0201042490060800L,0x0004002100005800L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0100000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000020L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0000000A00000090L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000000014000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x1000000000000002L,0x0000000042000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0001000000000000L,0x0004002000804800L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x6C0779CB678880F0L,0x057C022015806800L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0000000009000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x00F0000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000008008000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0078000014000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0404690162000000L,0x0078000014000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0040000014000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0040000000200000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0500000000000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x0000010000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x00001E00001C0000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});

}