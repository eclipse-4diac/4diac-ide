package org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.fordiac.ide.model.structuredtext.services.StructuredTextGrammarAccess;



import org.antlr.runtime.*;

@SuppressWarnings("all")
public class InternalStructuredTextParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "LTIME_OF_DAY", "TIME_OF_DAY", "END_REPEAT", "END_WHILE", "CONSTANT", "CONTINUE", "END_CASE", "END_FOR", "END_VAR", "WSTRING", "END_IF", "REPEAT", "RETURN", "STRING", "DWORD", "ELSIF", "FALSE", "LDATE", "LREAL", "LTIME", "LWORD", "SUPER", "UDINT", "ULINT", "UNTIL", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "LINT", "LTOD", "REAL", "SINT", "THEN", "TIME", "TRUE", "UINT", "WORD", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "TOD", "VAR", "XOR", "AsteriskAsterisk", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "BY", "DO", "DT", "IF", "LD", "LT", "OF", "OR", "TO", "Ms", "Ns", "Us", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "E", "T", "LeftSquareBracket", "RightSquareBracket", "KW__", "D_1", "H", "M", "S", "RULE_LETTER", "RULE_DIGIT", "RULE_BIT", "RULE_OCTAL_DIGIT", "RULE_HEX_DIGIT", "RULE_ID", "RULE_BINARY_INT", "RULE_OCTAL_INT", "RULE_HEX_INT", "RULE_UNSIGNED_INT", "RULE_S_BYTE_CHAR_VALUE", "RULE_S_BYTE_CHAR_STR", "RULE_D_BYTE_CHAR_VALUE", "RULE_D_BYTE_CHAR_STR", "RULE_COMMON_CHAR_VALUE", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=26;
    public static final int LessThanSignGreaterThanSign=63;
    public static final int EqualsSignGreaterThanSign=64;
    public static final int VAR=58;
    public static final int END_IF=16;
    public static final int ULINT=29;
    public static final int END_CASE=12;
    public static final int LessThanSign=90;
    public static final int RULE_BIT=104;
    public static final int LeftParenthesis=80;
    public static final int BYTE=35;
    public static final int ELSE=40;
    public static final int IF=69;
    public static final int LINT=42;
    public static final int GreaterThanSign=92;
    public static final int WORD=50;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=107;
    public static final int TOD=57;
    public static final int RULE_DIGIT=103;
    public static final int DINT=39;
    public static final int UDINT=28;
    public static final int CASE=36;
    public static final int GreaterThanSignEqualsSign=65;
    public static final int RULE_OCTAL_INT=109;
    public static final int PlusSign=83;
    public static final int END_FOR=13;
    public static final int RULE_ML_COMMENT=117;
    public static final int THEN=46;
    public static final int XOR=59;
    public static final int LeftSquareBracket=95;
    public static final int EXIT=41;
    public static final int TIME_OF_DAY=7;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=17;
    public static final int E=93;
    public static final int H=99;
    public static final int CHAR=37;
    public static final int RULE_D_BYTE_CHAR_STR=115;
    public static final int RULE_UNSIGNED_INT=111;
    public static final int M=100;
    public static final int LTIME=25;
    public static final int Comma=84;
    public static final int HyphenMinus=85;
    public static final int S=101;
    public static final int T=94;
    public static final int BY=66;
    public static final int ELSIF=21;
    public static final int END_REPEAT=8;
    public static final int LessThanSignEqualsSign=62;
    public static final int Solidus=87;
    public static final int RULE_OCTAL_DIGIT=105;
    public static final int RULE_HEX_INT=110;
    public static final int FullStop=86;
    public static final int CONSTANT=10;
    public static final int KW__=97;
    public static final int CONTINUE=11;
    public static final int Semicolon=89;
    public static final int RULE_LETTER=102;
    public static final int LD=70;
    public static final int STRING=19;
    public static final int RULE_HEX_DIGIT=106;
    public static final int TO=74;
    public static final int UINT=49;
    public static final int LTOD=43;
    public static final int LT=71;
    public static final int DO=67;
    public static final int WSTRING=15;
    public static final int DT=68;
    public static final int END_VAR=14;
    public static final int Ampersand=79;
    public static final int RightSquareBracket=96;
    public static final int RULE_BINARY_INT=108;
    public static final int USINT=31;
    public static final int DWORD=20;
    public static final int FOR=52;
    public static final int RightParenthesis=81;
    public static final int TRUE=48;
    public static final int ColonEqualsSign=61;
    public static final int END_WHILE=9;
    public static final int DATE=38;
    public static final int NOT=56;
    public static final int LDATE=23;
    public static final int AND=51;
    public static final int NumberSign=78;
    public static final int REAL=44;
    public static final int AsteriskAsterisk=60;
    public static final int SINT=45;
    public static final int LTIME_OF_DAY=6;
    public static final int Us=77;
    public static final int LREAL=24;
    public static final int WCHAR=32;
    public static final int Ms=75;
    public static final int INT=53;
    public static final int RULE_SL_COMMENT=118;
    public static final int RETURN=18;
    public static final int EqualsSign=91;
    public static final int RULE_COMMON_CHAR_VALUE=116;
    public static final int OF=72;
    public static final int Colon=88;
    public static final int EOF=-1;
    public static final int LDT=54;
    public static final int Asterisk=82;
    public static final int SUPER=27;
    public static final int MOD=55;
    public static final int OR=73;
    public static final int RULE_S_BYTE_CHAR_VALUE=112;
    public static final int Ns=76;
    public static final int RULE_WS=119;
    public static final int TIME=47;
    public static final int RULE_ANY_OTHER=120;
    public static final int UNTIL=30;
    public static final int RULE_S_BYTE_CHAR_STR=113;
    public static final int BOOL=34;
    public static final int D_1=98;
    public static final int FALSE=22;
    public static final int WHILE=33;
    public static final int RULE_D_BYTE_CHAR_VALUE=114;

    // delegates
    // delegators


        public InternalStructuredTextParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalStructuredTextParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    @Override
	public String[] getTokenNames() { return InternalStructuredTextParser.tokenNames; }
    @Override
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
    // InternalStructuredTextParser.g:145:1: ruleVar_Decl_Init returns [EObject current=null] : ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( ( (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign ( (lv_initialValue_9_0= ruleConstant ) ) )? ) ;
    public final EObject ruleVar_Decl_Init() throws RecognitionException {
        EObject current = null;

        Token lv_constant_1_0=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token lv_array_5_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_arraySize_6_0 = null;

        EObject lv_initialValue_9_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:151:2: ( ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( ( (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign ( (lv_initialValue_9_0= ruleConstant ) ) )? ) )
            // InternalStructuredTextParser.g:152:2: ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( ( (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign ( (lv_initialValue_9_0= ruleConstant ) ) )? )
            {
            // InternalStructuredTextParser.g:152:2: ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( ( (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign ( (lv_initialValue_9_0= ruleConstant ) ) )? )
            // InternalStructuredTextParser.g:153:3: () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( ( (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign ( (lv_initialValue_9_0= ruleConstant ) ) )?
            {
            // InternalStructuredTextParser.g:153:3: ()
            // InternalStructuredTextParser.g:154:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getVar_Decl_InitAccess().getLocalVariableAction_0(),
            					current);
            			

            }

            // InternalStructuredTextParser.g:160:3: ( (lv_constant_1_0= CONSTANT ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==CONSTANT) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalStructuredTextParser.g:161:4: (lv_constant_1_0= CONSTANT )
                    {
                    // InternalStructuredTextParser.g:161:4: (lv_constant_1_0= CONSTANT )
                    // InternalStructuredTextParser.g:162:5: lv_constant_1_0= CONSTANT
                    {
                    lv_constant_1_0=(Token)match(input,CONSTANT,FOLLOW_6); 

                    					newLeafNode(lv_constant_1_0, grammarAccess.getVar_Decl_InitAccess().getConstantCONSTANTKeyword_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getVar_Decl_InitRule());
                    					}
                    					setWithLastConsumed(current, "constant", true, "CONSTANT");
                    				

                    }


                    }
                    break;

            }

            // InternalStructuredTextParser.g:174:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalStructuredTextParser.g:175:4: (lv_name_2_0= RULE_ID )
            {
            // InternalStructuredTextParser.g:175:4: (lv_name_2_0= RULE_ID )
            // InternalStructuredTextParser.g:176:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_2_0, grammarAccess.getVar_Decl_InitAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVar_Decl_InitRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");
            				

            }


            }

            otherlv_3=(Token)match(input,Colon,FOLLOW_8); 

            			newLeafNode(otherlv_3, grammarAccess.getVar_Decl_InitAccess().getColonKeyword_3());
            		
            // InternalStructuredTextParser.g:196:3: ( ( ruleType_Name ) )
            // InternalStructuredTextParser.g:197:4: ( ruleType_Name )
            {
            // InternalStructuredTextParser.g:197:4: ( ruleType_Name )
            // InternalStructuredTextParser.g:198:5: ruleType_Name
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVar_Decl_InitRule());
            					}
            				

            					newCompositeNode(grammarAccess.getVar_Decl_InitAccess().getTypeDataTypeCrossReference_4_0());
            				
            pushFollow(FOLLOW_9);
            ruleType_Name();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalStructuredTextParser.g:212:3: ( ( (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LeftSquareBracket) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalStructuredTextParser.g:213:4: ( (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket
                    {
                    // InternalStructuredTextParser.g:213:4: ( (lv_array_5_0= LeftSquareBracket ) )
                    // InternalStructuredTextParser.g:214:5: (lv_array_5_0= LeftSquareBracket )
                    {
                    // InternalStructuredTextParser.g:214:5: (lv_array_5_0= LeftSquareBracket )
                    // InternalStructuredTextParser.g:215:6: lv_array_5_0= LeftSquareBracket
                    {
                    lv_array_5_0=(Token)match(input,LeftSquareBracket,FOLLOW_10); 

                    						newLeafNode(lv_array_5_0, grammarAccess.getVar_Decl_InitAccess().getArrayLeftSquareBracketKeyword_5_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getVar_Decl_InitRule());
                    						}
                    						setWithLastConsumed(current, "array", true, "[");
                    					

                    }


                    }

                    // InternalStructuredTextParser.g:227:4: ( (lv_arraySize_6_0= ruleArray_Size ) )
                    // InternalStructuredTextParser.g:228:5: (lv_arraySize_6_0= ruleArray_Size )
                    {
                    // InternalStructuredTextParser.g:228:5: (lv_arraySize_6_0= ruleArray_Size )
                    // InternalStructuredTextParser.g:229:6: lv_arraySize_6_0= ruleArray_Size
                    {

                    						newCompositeNode(grammarAccess.getVar_Decl_InitAccess().getArraySizeArray_SizeParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_11);
                    lv_arraySize_6_0=ruleArray_Size();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVar_Decl_InitRule());
                    						}
                    						set(
                    							current,
                    							"arraySize",
                    							lv_arraySize_6_0,
                    							"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Array_Size");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_7=(Token)match(input,RightSquareBracket,FOLLOW_12); 

                    				newLeafNode(otherlv_7, grammarAccess.getVar_Decl_InitAccess().getRightSquareBracketKeyword_5_2());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:251:3: (otherlv_8= ColonEqualsSign ( (lv_initialValue_9_0= ruleConstant ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ColonEqualsSign) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalStructuredTextParser.g:252:4: otherlv_8= ColonEqualsSign ( (lv_initialValue_9_0= ruleConstant ) )
                    {
                    otherlv_8=(Token)match(input,ColonEqualsSign,FOLLOW_13); 

                    				newLeafNode(otherlv_8, grammarAccess.getVar_Decl_InitAccess().getColonEqualsSignKeyword_6_0());
                    			
                    // InternalStructuredTextParser.g:256:4: ( (lv_initialValue_9_0= ruleConstant ) )
                    // InternalStructuredTextParser.g:257:5: (lv_initialValue_9_0= ruleConstant )
                    {
                    // InternalStructuredTextParser.g:257:5: (lv_initialValue_9_0= ruleConstant )
                    // InternalStructuredTextParser.g:258:6: lv_initialValue_9_0= ruleConstant
                    {

                    						newCompositeNode(grammarAccess.getVar_Decl_InitAccess().getInitialValueConstantParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_initialValue_9_0=ruleConstant();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVar_Decl_InitRule());
                    						}
                    						set(
                    							current,
                    							"initialValue",
                    							lv_initialValue_9_0,
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
    // $ANTLR end "ruleVar_Decl_Init"


    // $ANTLR start "entryRuleStmt_List"
    // InternalStructuredTextParser.g:280:1: entryRuleStmt_List returns [EObject current=null] : iv_ruleStmt_List= ruleStmt_List EOF ;
    public final EObject entryRuleStmt_List() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStmt_List = null;


        try {
            // InternalStructuredTextParser.g:280:50: (iv_ruleStmt_List= ruleStmt_List EOF )
            // InternalStructuredTextParser.g:281:2: iv_ruleStmt_List= ruleStmt_List EOF
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
    // InternalStructuredTextParser.g:287:1: ruleStmt_List returns [EObject current=null] : ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )* ) ;
    public final EObject ruleStmt_List() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_statements_1_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:293:2: ( ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )* ) )
            // InternalStructuredTextParser.g:294:2: ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )* )
            {
            // InternalStructuredTextParser.g:294:2: ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )* )
            // InternalStructuredTextParser.g:295:3: () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )*
            {
            // InternalStructuredTextParser.g:295:3: ()
            // InternalStructuredTextParser.g:296:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getStmt_ListAccess().getStatementListAction_0(),
            					current);
            			

            }

            // InternalStructuredTextParser.g:302:3: ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon )*
            loop7:
            do {
                int alt7=2;
                switch ( input.LA(1) ) {
                case TIME:
                    {
                    int LA7_2 = input.LA(2);

                    if ( (LA7_2==LeftParenthesis) ) {
                        alt7=1;
                    }


                    }
                    break;
                case T:
                    {
                    int LA7_3 = input.LA(2);

                    if ( (LA7_3==ColonEqualsSign||LA7_3==FullStop||LA7_3==LeftSquareBracket) ) {
                        alt7=1;
                    }


                    }
                    break;
                case LT:
                    {
                    int LA7_4 = input.LA(2);

                    if ( (LA7_4==ColonEqualsSign||LA7_4==FullStop||LA7_4==LeftSquareBracket) ) {
                        alt7=1;
                    }


                    }
                    break;
                case DT:
                    {
                    int LA7_5 = input.LA(2);

                    if ( (LA7_5==ColonEqualsSign||LA7_5==FullStop||LA7_5==LeftSquareBracket) ) {
                        alt7=1;
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
                    alt7=1;
                    }
                    break;

                }

                switch (alt7) {
            	case 1 :
            	    // InternalStructuredTextParser.g:303:4: ( (lv_statements_1_0= ruleStmt ) )? otherlv_2= Semicolon
            	    {
            	    // InternalStructuredTextParser.g:303:4: ( (lv_statements_1_0= ruleStmt ) )?
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0==CONTINUE||(LA6_0>=REPEAT && LA6_0<=RETURN)||LA6_0==SUPER||LA6_0==WHILE||LA6_0==CASE||LA6_0==EXIT||LA6_0==TIME||LA6_0==FOR||(LA6_0>=DT && LA6_0<=IF)||LA6_0==LT||LA6_0==T||LA6_0==RULE_ID) ) {
            	        alt6=1;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // InternalStructuredTextParser.g:304:5: (lv_statements_1_0= ruleStmt )
            	            {
            	            // InternalStructuredTextParser.g:304:5: (lv_statements_1_0= ruleStmt )
            	            // InternalStructuredTextParser.g:305:6: lv_statements_1_0= ruleStmt
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

            	    otherlv_2=(Token)match(input,Semicolon,FOLLOW_14); 

            	    				newLeafNode(otherlv_2, grammarAccess.getStmt_ListAccess().getSemicolonKeyword_1_1());
            	    			

            	    }
            	    break;

            	default :
            	    break loop7;
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
    // InternalStructuredTextParser.g:331:1: entryRuleStmt returns [EObject current=null] : iv_ruleStmt= ruleStmt EOF ;
    public final EObject entryRuleStmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStmt = null;


        try {
            // InternalStructuredTextParser.g:331:45: (iv_ruleStmt= ruleStmt EOF )
            // InternalStructuredTextParser.g:332:2: iv_ruleStmt= ruleStmt EOF
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
    // InternalStructuredTextParser.g:338:1: ruleStmt returns [EObject current=null] : (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt ) ;
    public final EObject ruleStmt() throws RecognitionException {
        EObject current = null;

        EObject this_Assign_Stmt_0 = null;

        EObject this_Subprog_Ctrl_Stmt_1 = null;

        EObject this_Selection_Stmt_2 = null;

        EObject this_Iteration_Stmt_3 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:344:2: ( (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt ) )
            // InternalStructuredTextParser.g:345:2: (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt )
            {
            // InternalStructuredTextParser.g:345:2: (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt )
            int alt8=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==LeftParenthesis) ) {
                    alt8=2;
                }
                else if ( (LA8_1==ColonEqualsSign||LA8_1==FullStop||LA8_1==LeftSquareBracket) ) {
                    alt8=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
                }
                break;
            case DT:
            case LT:
            case T:
                {
                alt8=1;
                }
                break;
            case RETURN:
            case SUPER:
            case TIME:
                {
                alt8=2;
                }
                break;
            case CASE:
            case IF:
                {
                alt8=3;
                }
                break;
            case CONTINUE:
            case REPEAT:
            case WHILE:
            case EXIT:
            case FOR:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalStructuredTextParser.g:346:3: this_Assign_Stmt_0= ruleAssign_Stmt
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
                    // InternalStructuredTextParser.g:355:3: this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt
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
                    // InternalStructuredTextParser.g:364:3: this_Selection_Stmt_2= ruleSelection_Stmt
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
                    // InternalStructuredTextParser.g:373:3: this_Iteration_Stmt_3= ruleIteration_Stmt
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
    // InternalStructuredTextParser.g:385:1: entryRuleAssign_Stmt returns [EObject current=null] : iv_ruleAssign_Stmt= ruleAssign_Stmt EOF ;
    public final EObject entryRuleAssign_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssign_Stmt = null;


        try {
            // InternalStructuredTextParser.g:385:52: (iv_ruleAssign_Stmt= ruleAssign_Stmt EOF )
            // InternalStructuredTextParser.g:386:2: iv_ruleAssign_Stmt= ruleAssign_Stmt EOF
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
    // InternalStructuredTextParser.g:392:1: ruleAssign_Stmt returns [EObject current=null] : ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) ) ;
    public final EObject ruleAssign_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_variable_0_0 = null;

        EObject lv_expression_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:398:2: ( ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) ) )
            // InternalStructuredTextParser.g:399:2: ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) )
            {
            // InternalStructuredTextParser.g:399:2: ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) )
            // InternalStructuredTextParser.g:400:3: ( (lv_variable_0_0= ruleVariable ) ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) )
            {
            // InternalStructuredTextParser.g:400:3: ( (lv_variable_0_0= ruleVariable ) )
            // InternalStructuredTextParser.g:401:4: (lv_variable_0_0= ruleVariable )
            {
            // InternalStructuredTextParser.g:401:4: (lv_variable_0_0= ruleVariable )
            // InternalStructuredTextParser.g:402:5: lv_variable_0_0= ruleVariable
            {

            					newCompositeNode(grammarAccess.getAssign_StmtAccess().getVariableVariableParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_15);
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

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_16); 

            			newLeafNode(otherlv_1, grammarAccess.getAssign_StmtAccess().getColonEqualsSignKeyword_1());
            		
            // InternalStructuredTextParser.g:423:3: ( (lv_expression_2_0= ruleExpression ) )
            // InternalStructuredTextParser.g:424:4: (lv_expression_2_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:424:4: (lv_expression_2_0= ruleExpression )
            // InternalStructuredTextParser.g:425:5: lv_expression_2_0= ruleExpression
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
    // InternalStructuredTextParser.g:446:1: entryRuleSubprog_Ctrl_Stmt returns [EObject current=null] : iv_ruleSubprog_Ctrl_Stmt= ruleSubprog_Ctrl_Stmt EOF ;
    public final EObject entryRuleSubprog_Ctrl_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSubprog_Ctrl_Stmt = null;


        try {
            // InternalStructuredTextParser.g:446:58: (iv_ruleSubprog_Ctrl_Stmt= ruleSubprog_Ctrl_Stmt EOF )
            // InternalStructuredTextParser.g:447:2: iv_ruleSubprog_Ctrl_Stmt= ruleSubprog_Ctrl_Stmt EOF
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
    // InternalStructuredTextParser.g:453:1: ruleSubprog_Ctrl_Stmt returns [EObject current=null] : (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6= RETURN ) ) ;
    public final EObject ruleSubprog_Ctrl_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject this_Func_Call_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:459:2: ( (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6= RETURN ) ) )
            // InternalStructuredTextParser.g:460:2: (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6= RETURN ) )
            {
            // InternalStructuredTextParser.g:460:2: (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6= RETURN ) )
            int alt9=3;
            switch ( input.LA(1) ) {
            case TIME:
            case RULE_ID:
                {
                alt9=1;
                }
                break;
            case SUPER:
                {
                alt9=2;
                }
                break;
            case RETURN:
                {
                alt9=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalStructuredTextParser.g:461:3: this_Func_Call_0= ruleFunc_Call
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
                    // InternalStructuredTextParser.g:470:3: ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis )
                    {
                    // InternalStructuredTextParser.g:470:3: ( () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis )
                    // InternalStructuredTextParser.g:471:4: () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis
                    {
                    // InternalStructuredTextParser.g:471:4: ()
                    // InternalStructuredTextParser.g:472:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,SUPER,FOLLOW_17); 

                    				newLeafNode(otherlv_2, grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_1_1());
                    			
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_18); 

                    				newLeafNode(otherlv_3, grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_1_2());
                    			
                    otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); 

                    				newLeafNode(otherlv_4, grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_1_3());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:492:3: ( () otherlv_6= RETURN )
                    {
                    // InternalStructuredTextParser.g:492:3: ( () otherlv_6= RETURN )
                    // InternalStructuredTextParser.g:493:4: () otherlv_6= RETURN
                    {
                    // InternalStructuredTextParser.g:493:4: ()
                    // InternalStructuredTextParser.g:494:5: 
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
    // InternalStructuredTextParser.g:509:1: entryRuleSelection_Stmt returns [EObject current=null] : iv_ruleSelection_Stmt= ruleSelection_Stmt EOF ;
    public final EObject entryRuleSelection_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSelection_Stmt = null;


        try {
            // InternalStructuredTextParser.g:509:55: (iv_ruleSelection_Stmt= ruleSelection_Stmt EOF )
            // InternalStructuredTextParser.g:510:2: iv_ruleSelection_Stmt= ruleSelection_Stmt EOF
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
    // InternalStructuredTextParser.g:516:1: ruleSelection_Stmt returns [EObject current=null] : (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1= ruleCase_Stmt ) ;
    public final EObject ruleSelection_Stmt() throws RecognitionException {
        EObject current = null;

        EObject this_IF_Stmt_0 = null;

        EObject this_Case_Stmt_1 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:522:2: ( (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1= ruleCase_Stmt ) )
            // InternalStructuredTextParser.g:523:2: (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1= ruleCase_Stmt )
            {
            // InternalStructuredTextParser.g:523:2: (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1= ruleCase_Stmt )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==IF) ) {
                alt10=1;
            }
            else if ( (LA10_0==CASE) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalStructuredTextParser.g:524:3: this_IF_Stmt_0= ruleIF_Stmt
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
                    // InternalStructuredTextParser.g:533:3: this_Case_Stmt_1= ruleCase_Stmt
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
    // InternalStructuredTextParser.g:545:1: entryRuleIF_Stmt returns [EObject current=null] : iv_ruleIF_Stmt= ruleIF_Stmt EOF ;
    public final EObject entryRuleIF_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIF_Stmt = null;


        try {
            // InternalStructuredTextParser.g:545:48: (iv_ruleIF_Stmt= ruleIF_Stmt EOF )
            // InternalStructuredTextParser.g:546:2: iv_ruleIF_Stmt= ruleIF_Stmt EOF
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
    // InternalStructuredTextParser.g:552:1: ruleIF_Stmt returns [EObject current=null] : (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF ) ;
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
            // InternalStructuredTextParser.g:558:2: ( (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF ) )
            // InternalStructuredTextParser.g:559:2: (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF )
            {
            // InternalStructuredTextParser.g:559:2: (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF )
            // InternalStructuredTextParser.g:560:3: otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getIF_StmtAccess().getIFKeyword_0());
            		
            // InternalStructuredTextParser.g:564:3: ( (lv_expression_1_0= ruleExpression ) )
            // InternalStructuredTextParser.g:565:4: (lv_expression_1_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:565:4: (lv_expression_1_0= ruleExpression )
            // InternalStructuredTextParser.g:566:5: lv_expression_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getIF_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_19);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_20); 

            			newLeafNode(otherlv_2, grammarAccess.getIF_StmtAccess().getTHENKeyword_2());
            		
            // InternalStructuredTextParser.g:587:3: ( (lv_statments_3_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:588:4: (lv_statments_3_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:588:4: (lv_statments_3_0= ruleStmt_List )
            // InternalStructuredTextParser.g:589:5: lv_statments_3_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getIF_StmtAccess().getStatmentsStmt_ListParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_21);
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

            // InternalStructuredTextParser.g:606:3: ( (lv_elseif_4_0= ruleELSIF_Clause ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==ELSIF) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalStructuredTextParser.g:607:4: (lv_elseif_4_0= ruleELSIF_Clause )
            	    {
            	    // InternalStructuredTextParser.g:607:4: (lv_elseif_4_0= ruleELSIF_Clause )
            	    // InternalStructuredTextParser.g:608:5: lv_elseif_4_0= ruleELSIF_Clause
            	    {

            	    					newCompositeNode(grammarAccess.getIF_StmtAccess().getElseifELSIF_ClauseParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_21);
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
            	    break loop11;
                }
            } while (true);

            // InternalStructuredTextParser.g:625:3: ( (lv_else_5_0= ruleELSE_Clause ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ELSE) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalStructuredTextParser.g:626:4: (lv_else_5_0= ruleELSE_Clause )
                    {
                    // InternalStructuredTextParser.g:626:4: (lv_else_5_0= ruleELSE_Clause )
                    // InternalStructuredTextParser.g:627:5: lv_else_5_0= ruleELSE_Clause
                    {

                    					newCompositeNode(grammarAccess.getIF_StmtAccess().getElseELSE_ClauseParserRuleCall_5_0());
                    				
                    pushFollow(FOLLOW_22);
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
    // InternalStructuredTextParser.g:652:1: entryRuleELSIF_Clause returns [EObject current=null] : iv_ruleELSIF_Clause= ruleELSIF_Clause EOF ;
    public final EObject entryRuleELSIF_Clause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleELSIF_Clause = null;


        try {
            // InternalStructuredTextParser.g:652:53: (iv_ruleELSIF_Clause= ruleELSIF_Clause EOF )
            // InternalStructuredTextParser.g:653:2: iv_ruleELSIF_Clause= ruleELSIF_Clause EOF
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
    // InternalStructuredTextParser.g:659:1: ruleELSIF_Clause returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) ) ;
    public final EObject ruleELSIF_Clause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_expression_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:665:2: ( (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) ) )
            // InternalStructuredTextParser.g:666:2: (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) )
            {
            // InternalStructuredTextParser.g:666:2: (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) )
            // InternalStructuredTextParser.g:667:3: otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) )
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getELSIF_ClauseAccess().getELSIFKeyword_0());
            		
            // InternalStructuredTextParser.g:671:3: ( (lv_expression_1_0= ruleExpression ) )
            // InternalStructuredTextParser.g:672:4: (lv_expression_1_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:672:4: (lv_expression_1_0= ruleExpression )
            // InternalStructuredTextParser.g:673:5: lv_expression_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getELSIF_ClauseAccess().getExpressionExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_19);
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
            		
            // InternalStructuredTextParser.g:694:3: ( (lv_statements_3_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:695:4: (lv_statements_3_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:695:4: (lv_statements_3_0= ruleStmt_List )
            // InternalStructuredTextParser.g:696:5: lv_statements_3_0= ruleStmt_List
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
    // InternalStructuredTextParser.g:717:1: entryRuleELSE_Clause returns [EObject current=null] : iv_ruleELSE_Clause= ruleELSE_Clause EOF ;
    public final EObject entryRuleELSE_Clause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleELSE_Clause = null;


        try {
            // InternalStructuredTextParser.g:717:52: (iv_ruleELSE_Clause= ruleELSE_Clause EOF )
            // InternalStructuredTextParser.g:718:2: iv_ruleELSE_Clause= ruleELSE_Clause EOF
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
    // InternalStructuredTextParser.g:724:1: ruleELSE_Clause returns [EObject current=null] : (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) ) ;
    public final EObject ruleELSE_Clause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_statements_1_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:730:2: ( (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) ) )
            // InternalStructuredTextParser.g:731:2: (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) )
            {
            // InternalStructuredTextParser.g:731:2: (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) )
            // InternalStructuredTextParser.g:732:3: otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) )
            {
            otherlv_0=(Token)match(input,ELSE,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getELSE_ClauseAccess().getELSEKeyword_0());
            		
            // InternalStructuredTextParser.g:736:3: ( (lv_statements_1_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:737:4: (lv_statements_1_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:737:4: (lv_statements_1_0= ruleStmt_List )
            // InternalStructuredTextParser.g:738:5: lv_statements_1_0= ruleStmt_List
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
    // InternalStructuredTextParser.g:759:1: entryRuleCase_Stmt returns [EObject current=null] : iv_ruleCase_Stmt= ruleCase_Stmt EOF ;
    public final EObject entryRuleCase_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCase_Stmt = null;


        try {
            // InternalStructuredTextParser.g:759:50: (iv_ruleCase_Stmt= ruleCase_Stmt EOF )
            // InternalStructuredTextParser.g:760:2: iv_ruleCase_Stmt= ruleCase_Stmt EOF
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
    // InternalStructuredTextParser.g:766:1: ruleCase_Stmt returns [EObject current=null] : (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE ) ;
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
            // InternalStructuredTextParser.g:772:2: ( (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE ) )
            // InternalStructuredTextParser.g:773:2: (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE )
            {
            // InternalStructuredTextParser.g:773:2: (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE )
            // InternalStructuredTextParser.g:774:3: otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getCase_StmtAccess().getCASEKeyword_0());
            		
            // InternalStructuredTextParser.g:778:3: ( (lv_expression_1_0= ruleExpression ) )
            // InternalStructuredTextParser.g:779:4: (lv_expression_1_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:779:4: (lv_expression_1_0= ruleExpression )
            // InternalStructuredTextParser.g:780:5: lv_expression_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getCase_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_23);
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

            otherlv_2=(Token)match(input,OF,FOLLOW_13); 

            			newLeafNode(otherlv_2, grammarAccess.getCase_StmtAccess().getOFKeyword_2());
            		
            // InternalStructuredTextParser.g:801:3: ( (lv_case_3_0= ruleCase_Selection ) )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>=LDATE_AND_TIME && LA13_0<=TIME_OF_DAY)||LA13_0==WSTRING||LA13_0==STRING||(LA13_0>=FALSE && LA13_0<=LTIME)||(LA13_0>=UDINT && LA13_0<=ULINT)||(LA13_0>=USINT && LA13_0<=WCHAR)||LA13_0==BOOL||(LA13_0>=CHAR && LA13_0<=DINT)||(LA13_0>=LINT && LA13_0<=SINT)||(LA13_0>=TIME && LA13_0<=UINT)||(LA13_0>=INT && LA13_0<=LDT)||LA13_0==TOD||LA13_0==DT||(LA13_0>=LD && LA13_0<=LT)||LA13_0==PlusSign||LA13_0==HyphenMinus||LA13_0==T||LA13_0==D_1||(LA13_0>=RULE_BINARY_INT && LA13_0<=RULE_UNSIGNED_INT)||LA13_0==RULE_S_BYTE_CHAR_STR||LA13_0==RULE_D_BYTE_CHAR_STR) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalStructuredTextParser.g:802:4: (lv_case_3_0= ruleCase_Selection )
            	    {
            	    // InternalStructuredTextParser.g:802:4: (lv_case_3_0= ruleCase_Selection )
            	    // InternalStructuredTextParser.g:803:5: lv_case_3_0= ruleCase_Selection
            	    {

            	    					newCompositeNode(grammarAccess.getCase_StmtAccess().getCaseCase_SelectionParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_24);
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
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);

            // InternalStructuredTextParser.g:820:3: ( (lv_else_4_0= ruleELSE_Clause ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ELSE) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalStructuredTextParser.g:821:4: (lv_else_4_0= ruleELSE_Clause )
                    {
                    // InternalStructuredTextParser.g:821:4: (lv_else_4_0= ruleELSE_Clause )
                    // InternalStructuredTextParser.g:822:5: lv_else_4_0= ruleELSE_Clause
                    {

                    					newCompositeNode(grammarAccess.getCase_StmtAccess().getElseELSE_ClauseParserRuleCall_4_0());
                    				
                    pushFollow(FOLLOW_25);
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
    // InternalStructuredTextParser.g:847:1: entryRuleCase_Selection returns [EObject current=null] : iv_ruleCase_Selection= ruleCase_Selection EOF ;
    public final EObject entryRuleCase_Selection() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCase_Selection = null;


        try {
            // InternalStructuredTextParser.g:847:55: (iv_ruleCase_Selection= ruleCase_Selection EOF )
            // InternalStructuredTextParser.g:848:2: iv_ruleCase_Selection= ruleCase_Selection EOF
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
    // InternalStructuredTextParser.g:854:1: ruleCase_Selection returns [EObject current=null] : ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) ) ) ;
    public final EObject ruleCase_Selection() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_case_0_0 = null;

        EObject lv_case_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:860:2: ( ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) ) ) )
            // InternalStructuredTextParser.g:861:2: ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) ) )
            {
            // InternalStructuredTextParser.g:861:2: ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) ) )
            // InternalStructuredTextParser.g:862:3: ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0= ruleStmt_List ) )
            {
            // InternalStructuredTextParser.g:862:3: ( (lv_case_0_0= ruleConstant ) )
            // InternalStructuredTextParser.g:863:4: (lv_case_0_0= ruleConstant )
            {
            // InternalStructuredTextParser.g:863:4: (lv_case_0_0= ruleConstant )
            // InternalStructuredTextParser.g:864:5: lv_case_0_0= ruleConstant
            {

            					newCompositeNode(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_26);
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

            // InternalStructuredTextParser.g:881:3: (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==Comma) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalStructuredTextParser.g:882:4: otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_13); 

            	    				newLeafNode(otherlv_1, grammarAccess.getCase_SelectionAccess().getCommaKeyword_1_0());
            	    			
            	    // InternalStructuredTextParser.g:886:4: ( (lv_case_2_0= ruleConstant ) )
            	    // InternalStructuredTextParser.g:887:5: (lv_case_2_0= ruleConstant )
            	    {
            	    // InternalStructuredTextParser.g:887:5: (lv_case_2_0= ruleConstant )
            	    // InternalStructuredTextParser.g:888:6: lv_case_2_0= ruleConstant
            	    {

            	    						newCompositeNode(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_26);
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
            	    break loop15;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_5); 

            			newLeafNode(otherlv_3, grammarAccess.getCase_SelectionAccess().getColonKeyword_2());
            		
            // InternalStructuredTextParser.g:910:3: ( (lv_statements_4_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:911:4: (lv_statements_4_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:911:4: (lv_statements_4_0= ruleStmt_List )
            // InternalStructuredTextParser.g:912:5: lv_statements_4_0= ruleStmt_List
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
    // InternalStructuredTextParser.g:933:1: entryRuleIteration_Stmt returns [EObject current=null] : iv_ruleIteration_Stmt= ruleIteration_Stmt EOF ;
    public final EObject entryRuleIteration_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIteration_Stmt = null;


        try {
            // InternalStructuredTextParser.g:933:55: (iv_ruleIteration_Stmt= ruleIteration_Stmt EOF )
            // InternalStructuredTextParser.g:934:2: iv_ruleIteration_Stmt= ruleIteration_Stmt EOF
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
    // InternalStructuredTextParser.g:940:1: ruleIteration_Stmt returns [EObject current=null] : (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) ) ;
    public final EObject ruleIteration_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject this_For_Stmt_0 = null;

        EObject this_While_Stmt_1 = null;

        EObject this_Repeat_Stmt_2 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:946:2: ( (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) ) )
            // InternalStructuredTextParser.g:947:2: (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) )
            {
            // InternalStructuredTextParser.g:947:2: (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) )
            int alt16=5;
            switch ( input.LA(1) ) {
            case FOR:
                {
                alt16=1;
                }
                break;
            case WHILE:
                {
                alt16=2;
                }
                break;
            case REPEAT:
                {
                alt16=3;
                }
                break;
            case EXIT:
                {
                alt16=4;
                }
                break;
            case CONTINUE:
                {
                alt16=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // InternalStructuredTextParser.g:948:3: this_For_Stmt_0= ruleFor_Stmt
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
                    // InternalStructuredTextParser.g:957:3: this_While_Stmt_1= ruleWhile_Stmt
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
                    // InternalStructuredTextParser.g:966:3: this_Repeat_Stmt_2= ruleRepeat_Stmt
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
                    // InternalStructuredTextParser.g:975:3: ( () otherlv_4= EXIT )
                    {
                    // InternalStructuredTextParser.g:975:3: ( () otherlv_4= EXIT )
                    // InternalStructuredTextParser.g:976:4: () otherlv_4= EXIT
                    {
                    // InternalStructuredTextParser.g:976:4: ()
                    // InternalStructuredTextParser.g:977:5: 
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
                    // InternalStructuredTextParser.g:989:3: ( () otherlv_6= CONTINUE )
                    {
                    // InternalStructuredTextParser.g:989:3: ( () otherlv_6= CONTINUE )
                    // InternalStructuredTextParser.g:990:4: () otherlv_6= CONTINUE
                    {
                    // InternalStructuredTextParser.g:990:4: ()
                    // InternalStructuredTextParser.g:991:5: 
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
    // InternalStructuredTextParser.g:1006:1: entryRuleFor_Stmt returns [EObject current=null] : iv_ruleFor_Stmt= ruleFor_Stmt EOF ;
    public final EObject entryRuleFor_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFor_Stmt = null;


        try {
            // InternalStructuredTextParser.g:1006:49: (iv_ruleFor_Stmt= ruleFor_Stmt EOF )
            // InternalStructuredTextParser.g:1007:2: iv_ruleFor_Stmt= ruleFor_Stmt EOF
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
    // InternalStructuredTextParser.g:1013:1: ruleFor_Stmt returns [EObject current=null] : (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR ) ;
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
            // InternalStructuredTextParser.g:1019:2: ( (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR ) )
            // InternalStructuredTextParser.g:1020:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR )
            {
            // InternalStructuredTextParser.g:1020:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR )
            // InternalStructuredTextParser.g:1021:3: otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_27); 

            			newLeafNode(otherlv_0, grammarAccess.getFor_StmtAccess().getFORKeyword_0());
            		
            // InternalStructuredTextParser.g:1025:3: ( (lv_variable_1_0= ruleVariable_Primary ) )
            // InternalStructuredTextParser.g:1026:4: (lv_variable_1_0= ruleVariable_Primary )
            {
            // InternalStructuredTextParser.g:1026:4: (lv_variable_1_0= ruleVariable_Primary )
            // InternalStructuredTextParser.g:1027:5: lv_variable_1_0= ruleVariable_Primary
            {

            					newCompositeNode(grammarAccess.getFor_StmtAccess().getVariableVariable_PrimaryParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_15);
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

            otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_16); 

            			newLeafNode(otherlv_2, grammarAccess.getFor_StmtAccess().getColonEqualsSignKeyword_2());
            		
            // InternalStructuredTextParser.g:1048:3: ( (lv_from_3_0= ruleExpression ) )
            // InternalStructuredTextParser.g:1049:4: (lv_from_3_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:1049:4: (lv_from_3_0= ruleExpression )
            // InternalStructuredTextParser.g:1050:5: lv_from_3_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getFor_StmtAccess().getFromExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_28);
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

            otherlv_4=(Token)match(input,TO,FOLLOW_16); 

            			newLeafNode(otherlv_4, grammarAccess.getFor_StmtAccess().getTOKeyword_4());
            		
            // InternalStructuredTextParser.g:1071:3: ( (lv_to_5_0= ruleExpression ) )
            // InternalStructuredTextParser.g:1072:4: (lv_to_5_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:1072:4: (lv_to_5_0= ruleExpression )
            // InternalStructuredTextParser.g:1073:5: lv_to_5_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getFor_StmtAccess().getToExpressionParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_29);
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

            // InternalStructuredTextParser.g:1090:3: (otherlv_6= BY ( (lv_by_7_0= ruleExpression ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==BY) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalStructuredTextParser.g:1091:4: otherlv_6= BY ( (lv_by_7_0= ruleExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_16); 

                    				newLeafNode(otherlv_6, grammarAccess.getFor_StmtAccess().getBYKeyword_6_0());
                    			
                    // InternalStructuredTextParser.g:1095:4: ( (lv_by_7_0= ruleExpression ) )
                    // InternalStructuredTextParser.g:1096:5: (lv_by_7_0= ruleExpression )
                    {
                    // InternalStructuredTextParser.g:1096:5: (lv_by_7_0= ruleExpression )
                    // InternalStructuredTextParser.g:1097:6: lv_by_7_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getFor_StmtAccess().getByExpressionParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_30);
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

            otherlv_8=(Token)match(input,DO,FOLLOW_31); 

            			newLeafNode(otherlv_8, grammarAccess.getFor_StmtAccess().getDOKeyword_7());
            		
            // InternalStructuredTextParser.g:1119:3: ( (lv_statements_9_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:1120:4: (lv_statements_9_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:1120:4: (lv_statements_9_0= ruleStmt_List )
            // InternalStructuredTextParser.g:1121:5: lv_statements_9_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getFor_StmtAccess().getStatementsStmt_ListParserRuleCall_8_0());
            				
            pushFollow(FOLLOW_32);
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
    // InternalStructuredTextParser.g:1146:1: entryRuleWhile_Stmt returns [EObject current=null] : iv_ruleWhile_Stmt= ruleWhile_Stmt EOF ;
    public final EObject entryRuleWhile_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhile_Stmt = null;


        try {
            // InternalStructuredTextParser.g:1146:51: (iv_ruleWhile_Stmt= ruleWhile_Stmt EOF )
            // InternalStructuredTextParser.g:1147:2: iv_ruleWhile_Stmt= ruleWhile_Stmt EOF
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
    // InternalStructuredTextParser.g:1153:1: ruleWhile_Stmt returns [EObject current=null] : (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE ) ;
    public final EObject ruleWhile_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_expression_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1159:2: ( (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE ) )
            // InternalStructuredTextParser.g:1160:2: (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE )
            {
            // InternalStructuredTextParser.g:1160:2: (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE )
            // InternalStructuredTextParser.g:1161:3: otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getWhile_StmtAccess().getWHILEKeyword_0());
            		
            // InternalStructuredTextParser.g:1165:3: ( (lv_expression_1_0= ruleExpression ) )
            // InternalStructuredTextParser.g:1166:4: (lv_expression_1_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:1166:4: (lv_expression_1_0= ruleExpression )
            // InternalStructuredTextParser.g:1167:5: lv_expression_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getWhile_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_30);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_33); 

            			newLeafNode(otherlv_2, grammarAccess.getWhile_StmtAccess().getDOKeyword_2());
            		
            // InternalStructuredTextParser.g:1188:3: ( (lv_statements_3_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:1189:4: (lv_statements_3_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:1189:4: (lv_statements_3_0= ruleStmt_List )
            // InternalStructuredTextParser.g:1190:5: lv_statements_3_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getWhile_StmtAccess().getStatementsStmt_ListParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_34);
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
    // InternalStructuredTextParser.g:1215:1: entryRuleRepeat_Stmt returns [EObject current=null] : iv_ruleRepeat_Stmt= ruleRepeat_Stmt EOF ;
    public final EObject entryRuleRepeat_Stmt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRepeat_Stmt = null;


        try {
            // InternalStructuredTextParser.g:1215:52: (iv_ruleRepeat_Stmt= ruleRepeat_Stmt EOF )
            // InternalStructuredTextParser.g:1216:2: iv_ruleRepeat_Stmt= ruleRepeat_Stmt EOF
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
    // InternalStructuredTextParser.g:1222:1: ruleRepeat_Stmt returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleRepeat_Stmt() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1228:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalStructuredTextParser.g:1229:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalStructuredTextParser.g:1229:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT )
            // InternalStructuredTextParser.g:1230:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_35); 

            			newLeafNode(otherlv_0, grammarAccess.getRepeat_StmtAccess().getREPEATKeyword_0());
            		
            // InternalStructuredTextParser.g:1234:3: ( (lv_statements_1_0= ruleStmt_List ) )
            // InternalStructuredTextParser.g:1235:4: (lv_statements_1_0= ruleStmt_List )
            {
            // InternalStructuredTextParser.g:1235:4: (lv_statements_1_0= ruleStmt_List )
            // InternalStructuredTextParser.g:1236:5: lv_statements_1_0= ruleStmt_List
            {

            					newCompositeNode(grammarAccess.getRepeat_StmtAccess().getStatementsStmt_ListParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_36);
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

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_16); 

            			newLeafNode(otherlv_2, grammarAccess.getRepeat_StmtAccess().getUNTILKeyword_2());
            		
            // InternalStructuredTextParser.g:1257:3: ( (lv_expression_3_0= ruleExpression ) )
            // InternalStructuredTextParser.g:1258:4: (lv_expression_3_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:1258:4: (lv_expression_3_0= ruleExpression )
            // InternalStructuredTextParser.g:1259:5: lv_expression_3_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getRepeat_StmtAccess().getExpressionExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_37);
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
    // InternalStructuredTextParser.g:1284:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalStructuredTextParser.g:1284:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalStructuredTextParser.g:1285:2: iv_ruleExpression= ruleExpression EOF
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
    // InternalStructuredTextParser.g:1291:1: ruleExpression returns [EObject current=null] : this_Or_Expression_0= ruleOr_Expression ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_Or_Expression_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1297:2: (this_Or_Expression_0= ruleOr_Expression )
            // InternalStructuredTextParser.g:1298:2: this_Or_Expression_0= ruleOr_Expression
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
    // InternalStructuredTextParser.g:1309:1: entryRuleOr_Expression returns [EObject current=null] : iv_ruleOr_Expression= ruleOr_Expression EOF ;
    public final EObject entryRuleOr_Expression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOr_Expression = null;


        try {
            // InternalStructuredTextParser.g:1309:54: (iv_ruleOr_Expression= ruleOr_Expression EOF )
            // InternalStructuredTextParser.g:1310:2: iv_ruleOr_Expression= ruleOr_Expression EOF
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
    // InternalStructuredTextParser.g:1316:1: ruleOr_Expression returns [EObject current=null] : (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* ) ;
    public final EObject ruleOr_Expression() throws RecognitionException {
        EObject current = null;

        EObject this_Xor_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1322:2: ( (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1323:2: (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1323:2: (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* )
            // InternalStructuredTextParser.g:1324:3: this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getOr_ExpressionAccess().getXor_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_38);
            this_Xor_Expr_0=ruleXor_Expr();

            state._fsp--;


            			current = this_Xor_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1332:3: ( () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==OR) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1333:4: () ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1333:4: ()
            	    // InternalStructuredTextParser.g:1334:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1340:4: ( (lv_operator_2_0= ruleOr_Operator ) )
            	    // InternalStructuredTextParser.g:1341:5: (lv_operator_2_0= ruleOr_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1341:5: (lv_operator_2_0= ruleOr_Operator )
            	    // InternalStructuredTextParser.g:1342:6: lv_operator_2_0= ruleOr_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getOr_ExpressionAccess().getOperatorOr_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_16);
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

            	    // InternalStructuredTextParser.g:1359:4: ( (lv_right_3_0= ruleXor_Expr ) )
            	    // InternalStructuredTextParser.g:1360:5: (lv_right_3_0= ruleXor_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1360:5: (lv_right_3_0= ruleXor_Expr )
            	    // InternalStructuredTextParser.g:1361:6: lv_right_3_0= ruleXor_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getOr_ExpressionAccess().getRightXor_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_38);
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
            	    break loop18;
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
    // InternalStructuredTextParser.g:1383:1: entryRuleXor_Expr returns [EObject current=null] : iv_ruleXor_Expr= ruleXor_Expr EOF ;
    public final EObject entryRuleXor_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXor_Expr = null;


        try {
            // InternalStructuredTextParser.g:1383:49: (iv_ruleXor_Expr= ruleXor_Expr EOF )
            // InternalStructuredTextParser.g:1384:2: iv_ruleXor_Expr= ruleXor_Expr EOF
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
    // InternalStructuredTextParser.g:1390:1: ruleXor_Expr returns [EObject current=null] : (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* ) ;
    public final EObject ruleXor_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_And_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1396:2: ( (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1397:2: (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1397:2: (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* )
            // InternalStructuredTextParser.g:1398:3: this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getXor_ExprAccess().getAnd_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_39);
            this_And_Expr_0=ruleAnd_Expr();

            state._fsp--;


            			current = this_And_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1406:3: ( () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==XOR) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1407:4: () ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1407:4: ()
            	    // InternalStructuredTextParser.g:1408:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1414:4: ( (lv_operator_2_0= ruleXor_Operator ) )
            	    // InternalStructuredTextParser.g:1415:5: (lv_operator_2_0= ruleXor_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1415:5: (lv_operator_2_0= ruleXor_Operator )
            	    // InternalStructuredTextParser.g:1416:6: lv_operator_2_0= ruleXor_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getXor_ExprAccess().getOperatorXor_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_16);
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

            	    // InternalStructuredTextParser.g:1433:4: ( (lv_right_3_0= ruleAnd_Expr ) )
            	    // InternalStructuredTextParser.g:1434:5: (lv_right_3_0= ruleAnd_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1434:5: (lv_right_3_0= ruleAnd_Expr )
            	    // InternalStructuredTextParser.g:1435:6: lv_right_3_0= ruleAnd_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getXor_ExprAccess().getRightAnd_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_39);
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
    // $ANTLR end "ruleXor_Expr"


    // $ANTLR start "entryRuleAnd_Expr"
    // InternalStructuredTextParser.g:1457:1: entryRuleAnd_Expr returns [EObject current=null] : iv_ruleAnd_Expr= ruleAnd_Expr EOF ;
    public final EObject entryRuleAnd_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnd_Expr = null;


        try {
            // InternalStructuredTextParser.g:1457:49: (iv_ruleAnd_Expr= ruleAnd_Expr EOF )
            // InternalStructuredTextParser.g:1458:2: iv_ruleAnd_Expr= ruleAnd_Expr EOF
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
    // InternalStructuredTextParser.g:1464:1: ruleAnd_Expr returns [EObject current=null] : (this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )* ) ;
    public final EObject ruleAnd_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Compare_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1470:2: ( (this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1471:2: (this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1471:2: (this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )* )
            // InternalStructuredTextParser.g:1472:3: this_Compare_Expr_0= ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getAnd_ExprAccess().getCompare_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_40);
            this_Compare_Expr_0=ruleCompare_Expr();

            state._fsp--;


            			current = this_Compare_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1480:3: ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==AND||LA20_0==Ampersand) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1481:4: () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1481:4: ()
            	    // InternalStructuredTextParser.g:1482:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1488:4: ( (lv_operator_2_0= ruleAnd_Operator ) )
            	    // InternalStructuredTextParser.g:1489:5: (lv_operator_2_0= ruleAnd_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1489:5: (lv_operator_2_0= ruleAnd_Operator )
            	    // InternalStructuredTextParser.g:1490:6: lv_operator_2_0= ruleAnd_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getAnd_ExprAccess().getOperatorAnd_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_16);
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

            	    // InternalStructuredTextParser.g:1507:4: ( (lv_right_3_0= ruleCompare_Expr ) )
            	    // InternalStructuredTextParser.g:1508:5: (lv_right_3_0= ruleCompare_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1508:5: (lv_right_3_0= ruleCompare_Expr )
            	    // InternalStructuredTextParser.g:1509:6: lv_right_3_0= ruleCompare_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getAnd_ExprAccess().getRightCompare_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_40);
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
    // $ANTLR end "ruleAnd_Expr"


    // $ANTLR start "entryRuleCompare_Expr"
    // InternalStructuredTextParser.g:1531:1: entryRuleCompare_Expr returns [EObject current=null] : iv_ruleCompare_Expr= ruleCompare_Expr EOF ;
    public final EObject entryRuleCompare_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCompare_Expr = null;


        try {
            // InternalStructuredTextParser.g:1531:53: (iv_ruleCompare_Expr= ruleCompare_Expr EOF )
            // InternalStructuredTextParser.g:1532:2: iv_ruleCompare_Expr= ruleCompare_Expr EOF
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
    // InternalStructuredTextParser.g:1538:1: ruleCompare_Expr returns [EObject current=null] : (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* ) ;
    public final EObject ruleCompare_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Equ_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1544:2: ( (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1545:2: (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1545:2: (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* )
            // InternalStructuredTextParser.g:1546:3: this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getCompare_ExprAccess().getEqu_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_41);
            this_Equ_Expr_0=ruleEqu_Expr();

            state._fsp--;


            			current = this_Equ_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1554:3: ( () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==LessThanSignGreaterThanSign||LA21_0==EqualsSign) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1555:4: () ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1555:4: ()
            	    // InternalStructuredTextParser.g:1556:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1562:4: ( (lv_operator_2_0= ruleCompare_Operator ) )
            	    // InternalStructuredTextParser.g:1563:5: (lv_operator_2_0= ruleCompare_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1563:5: (lv_operator_2_0= ruleCompare_Operator )
            	    // InternalStructuredTextParser.g:1564:6: lv_operator_2_0= ruleCompare_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getCompare_ExprAccess().getOperatorCompare_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_16);
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

            	    // InternalStructuredTextParser.g:1581:4: ( (lv_right_3_0= ruleEqu_Expr ) )
            	    // InternalStructuredTextParser.g:1582:5: (lv_right_3_0= ruleEqu_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1582:5: (lv_right_3_0= ruleEqu_Expr )
            	    // InternalStructuredTextParser.g:1583:6: lv_right_3_0= ruleEqu_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getCompare_ExprAccess().getRightEqu_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_41);
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
    // $ANTLR end "ruleCompare_Expr"


    // $ANTLR start "entryRuleEqu_Expr"
    // InternalStructuredTextParser.g:1605:1: entryRuleEqu_Expr returns [EObject current=null] : iv_ruleEqu_Expr= ruleEqu_Expr EOF ;
    public final EObject entryRuleEqu_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqu_Expr = null;


        try {
            // InternalStructuredTextParser.g:1605:49: (iv_ruleEqu_Expr= ruleEqu_Expr EOF )
            // InternalStructuredTextParser.g:1606:2: iv_ruleEqu_Expr= ruleEqu_Expr EOF
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
    // InternalStructuredTextParser.g:1612:1: ruleEqu_Expr returns [EObject current=null] : (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* ) ;
    public final EObject ruleEqu_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Add_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1618:2: ( (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1619:2: (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1619:2: (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* )
            // InternalStructuredTextParser.g:1620:3: this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getEqu_ExprAccess().getAdd_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_42);
            this_Add_Expr_0=ruleAdd_Expr();

            state._fsp--;


            			current = this_Add_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1628:3: ( () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==LessThanSignEqualsSign||LA22_0==GreaterThanSignEqualsSign||LA22_0==LessThanSign||LA22_0==GreaterThanSign) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1629:4: () ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1629:4: ()
            	    // InternalStructuredTextParser.g:1630:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1636:4: ( (lv_operator_2_0= ruleEqu_Operator ) )
            	    // InternalStructuredTextParser.g:1637:5: (lv_operator_2_0= ruleEqu_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1637:5: (lv_operator_2_0= ruleEqu_Operator )
            	    // InternalStructuredTextParser.g:1638:6: lv_operator_2_0= ruleEqu_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getEqu_ExprAccess().getOperatorEqu_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_16);
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

            	    // InternalStructuredTextParser.g:1655:4: ( (lv_right_3_0= ruleAdd_Expr ) )
            	    // InternalStructuredTextParser.g:1656:5: (lv_right_3_0= ruleAdd_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1656:5: (lv_right_3_0= ruleAdd_Expr )
            	    // InternalStructuredTextParser.g:1657:6: lv_right_3_0= ruleAdd_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getEqu_ExprAccess().getRightAdd_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_42);
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
    // $ANTLR end "ruleEqu_Expr"


    // $ANTLR start "entryRuleAdd_Expr"
    // InternalStructuredTextParser.g:1679:1: entryRuleAdd_Expr returns [EObject current=null] : iv_ruleAdd_Expr= ruleAdd_Expr EOF ;
    public final EObject entryRuleAdd_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdd_Expr = null;


        try {
            // InternalStructuredTextParser.g:1679:49: (iv_ruleAdd_Expr= ruleAdd_Expr EOF )
            // InternalStructuredTextParser.g:1680:2: iv_ruleAdd_Expr= ruleAdd_Expr EOF
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
    // InternalStructuredTextParser.g:1686:1: ruleAdd_Expr returns [EObject current=null] : (this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* ) ;
    public final EObject ruleAdd_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Term_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1692:2: ( (this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* ) )
            // InternalStructuredTextParser.g:1693:2: (this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* )
            {
            // InternalStructuredTextParser.g:1693:2: (this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* )
            // InternalStructuredTextParser.g:1694:3: this_Term_0= ruleTerm ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )*
            {

            			newCompositeNode(grammarAccess.getAdd_ExprAccess().getTermParserRuleCall_0());
            		
            pushFollow(FOLLOW_43);
            this_Term_0=ruleTerm();

            state._fsp--;


            			current = this_Term_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1702:3: ( () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==PlusSign||LA23_0==HyphenMinus) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1703:4: () ( (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) )
            	    {
            	    // InternalStructuredTextParser.g:1703:4: ()
            	    // InternalStructuredTextParser.g:1704:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1710:4: ( (lv_operator_2_0= ruleAdd_Operator ) )
            	    // InternalStructuredTextParser.g:1711:5: (lv_operator_2_0= ruleAdd_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1711:5: (lv_operator_2_0= ruleAdd_Operator )
            	    // InternalStructuredTextParser.g:1712:6: lv_operator_2_0= ruleAdd_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getAdd_ExprAccess().getOperatorAdd_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_16);
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

            	    // InternalStructuredTextParser.g:1729:4: ( (lv_right_3_0= ruleTerm ) )
            	    // InternalStructuredTextParser.g:1730:5: (lv_right_3_0= ruleTerm )
            	    {
            	    // InternalStructuredTextParser.g:1730:5: (lv_right_3_0= ruleTerm )
            	    // InternalStructuredTextParser.g:1731:6: lv_right_3_0= ruleTerm
            	    {

            	    						newCompositeNode(grammarAccess.getAdd_ExprAccess().getRightTermParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_43);
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
    // $ANTLR end "ruleAdd_Expr"


    // $ANTLR start "entryRuleTerm"
    // InternalStructuredTextParser.g:1753:1: entryRuleTerm returns [EObject current=null] : iv_ruleTerm= ruleTerm EOF ;
    public final EObject entryRuleTerm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTerm = null;


        try {
            // InternalStructuredTextParser.g:1753:45: (iv_ruleTerm= ruleTerm EOF )
            // InternalStructuredTextParser.g:1754:2: iv_ruleTerm= ruleTerm EOF
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
    // InternalStructuredTextParser.g:1760:1: ruleTerm returns [EObject current=null] : (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* ) ;
    public final EObject ruleTerm() throws RecognitionException {
        EObject current = null;

        EObject this_Power_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1766:2: ( (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1767:2: (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1767:2: (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* )
            // InternalStructuredTextParser.g:1768:3: this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getTermAccess().getPower_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_44);
            this_Power_Expr_0=rulePower_Expr();

            state._fsp--;


            			current = this_Power_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1776:3: ( () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==MOD||LA24_0==Asterisk||LA24_0==Solidus) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1777:4: () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1777:4: ()
            	    // InternalStructuredTextParser.g:1778:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1784:4: ( (lv_operator_2_0= ruleTerm_Operator ) )
            	    // InternalStructuredTextParser.g:1785:5: (lv_operator_2_0= ruleTerm_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1785:5: (lv_operator_2_0= ruleTerm_Operator )
            	    // InternalStructuredTextParser.g:1786:6: lv_operator_2_0= ruleTerm_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getTermAccess().getOperatorTerm_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_16);
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

            	    // InternalStructuredTextParser.g:1803:4: ( (lv_right_3_0= rulePower_Expr ) )
            	    // InternalStructuredTextParser.g:1804:5: (lv_right_3_0= rulePower_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1804:5: (lv_right_3_0= rulePower_Expr )
            	    // InternalStructuredTextParser.g:1805:6: lv_right_3_0= rulePower_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getTermAccess().getRightPower_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_44);
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
    // $ANTLR end "ruleTerm"


    // $ANTLR start "entryRulePower_Expr"
    // InternalStructuredTextParser.g:1827:1: entryRulePower_Expr returns [EObject current=null] : iv_rulePower_Expr= rulePower_Expr EOF ;
    public final EObject entryRulePower_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePower_Expr = null;


        try {
            // InternalStructuredTextParser.g:1827:51: (iv_rulePower_Expr= rulePower_Expr EOF )
            // InternalStructuredTextParser.g:1828:2: iv_rulePower_Expr= rulePower_Expr EOF
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
    // InternalStructuredTextParser.g:1834:1: rulePower_Expr returns [EObject current=null] : (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* ) ;
    public final EObject rulePower_Expr() throws RecognitionException {
        EObject current = null;

        EObject this_Unary_Expr_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1840:2: ( (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* ) )
            // InternalStructuredTextParser.g:1841:2: (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* )
            {
            // InternalStructuredTextParser.g:1841:2: (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* )
            // InternalStructuredTextParser.g:1842:3: this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )*
            {

            			newCompositeNode(grammarAccess.getPower_ExprAccess().getUnary_ExprParserRuleCall_0());
            		
            pushFollow(FOLLOW_45);
            this_Unary_Expr_0=ruleUnary_Expr();

            state._fsp--;


            			current = this_Unary_Expr_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalStructuredTextParser.g:1850:3: ( () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==AsteriskAsterisk) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalStructuredTextParser.g:1851:4: () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) )
            	    {
            	    // InternalStructuredTextParser.g:1851:4: ()
            	    // InternalStructuredTextParser.g:1852:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalStructuredTextParser.g:1858:4: ( (lv_operator_2_0= rulePower_Operator ) )
            	    // InternalStructuredTextParser.g:1859:5: (lv_operator_2_0= rulePower_Operator )
            	    {
            	    // InternalStructuredTextParser.g:1859:5: (lv_operator_2_0= rulePower_Operator )
            	    // InternalStructuredTextParser.g:1860:6: lv_operator_2_0= rulePower_Operator
            	    {

            	    						newCompositeNode(grammarAccess.getPower_ExprAccess().getOperatorPower_OperatorEnumRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_16);
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

            	    // InternalStructuredTextParser.g:1877:4: ( (lv_right_3_0= ruleUnary_Expr ) )
            	    // InternalStructuredTextParser.g:1878:5: (lv_right_3_0= ruleUnary_Expr )
            	    {
            	    // InternalStructuredTextParser.g:1878:5: (lv_right_3_0= ruleUnary_Expr )
            	    // InternalStructuredTextParser.g:1879:6: lv_right_3_0= ruleUnary_Expr
            	    {

            	    						newCompositeNode(grammarAccess.getPower_ExprAccess().getRightUnary_ExprParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_45);
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
    // $ANTLR end "rulePower_Expr"


    // $ANTLR start "entryRuleUnary_Expr"
    // InternalStructuredTextParser.g:1901:1: entryRuleUnary_Expr returns [EObject current=null] : iv_ruleUnary_Expr= ruleUnary_Expr EOF ;
    public final EObject entryRuleUnary_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnary_Expr = null;


        try {
            // InternalStructuredTextParser.g:1901:51: (iv_ruleUnary_Expr= ruleUnary_Expr EOF )
            // InternalStructuredTextParser.g:1902:2: iv_ruleUnary_Expr= ruleUnary_Expr EOF
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
    // InternalStructuredTextParser.g:1908:1: ruleUnary_Expr returns [EObject current=null] : ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant ) ;
    public final EObject ruleUnary_Expr() throws RecognitionException {
        EObject current = null;

        Enumerator lv_operator_1_0 = null;

        EObject lv_expression_2_0 = null;

        EObject this_Primary_Expr_3 = null;

        EObject this_Constant_4 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1914:2: ( ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant ) )
            // InternalStructuredTextParser.g:1915:2: ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )
            {
            // InternalStructuredTextParser.g:1915:2: ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )
            int alt26=3;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // InternalStructuredTextParser.g:1916:3: ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) )
                    {
                    // InternalStructuredTextParser.g:1916:3: ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) )
                    // InternalStructuredTextParser.g:1917:4: () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) )
                    {
                    // InternalStructuredTextParser.g:1917:4: ()
                    // InternalStructuredTextParser.g:1918:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0(),
                    						current);
                    				

                    }

                    // InternalStructuredTextParser.g:1924:4: ( (lv_operator_1_0= ruleUnary_Operator ) )
                    // InternalStructuredTextParser.g:1925:5: (lv_operator_1_0= ruleUnary_Operator )
                    {
                    // InternalStructuredTextParser.g:1925:5: (lv_operator_1_0= ruleUnary_Operator )
                    // InternalStructuredTextParser.g:1926:6: lv_operator_1_0= ruleUnary_Operator
                    {

                    						newCompositeNode(grammarAccess.getUnary_ExprAccess().getOperatorUnary_OperatorEnumRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_46);
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

                    // InternalStructuredTextParser.g:1943:4: ( (lv_expression_2_0= rulePrimary_Expr ) )
                    // InternalStructuredTextParser.g:1944:5: (lv_expression_2_0= rulePrimary_Expr )
                    {
                    // InternalStructuredTextParser.g:1944:5: (lv_expression_2_0= rulePrimary_Expr )
                    // InternalStructuredTextParser.g:1945:6: lv_expression_2_0= rulePrimary_Expr
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
                    // InternalStructuredTextParser.g:1964:3: this_Primary_Expr_3= rulePrimary_Expr
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
                    // InternalStructuredTextParser.g:1973:3: this_Constant_4= ruleConstant
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
    // InternalStructuredTextParser.g:1985:1: entryRulePrimary_Expr returns [EObject current=null] : iv_rulePrimary_Expr= rulePrimary_Expr EOF ;
    public final EObject entryRulePrimary_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary_Expr = null;


        try {
            // InternalStructuredTextParser.g:1985:53: (iv_rulePrimary_Expr= rulePrimary_Expr EOF )
            // InternalStructuredTextParser.g:1986:2: iv_rulePrimary_Expr= rulePrimary_Expr EOF
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
    // InternalStructuredTextParser.g:1992:1: rulePrimary_Expr returns [EObject current=null] : (this_Variable_0= ruleVariable | this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) ) ;
    public final EObject rulePrimary_Expr() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_Variable_0 = null;

        EObject this_Func_Call_1 = null;

        EObject this_Expression_3 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:1998:2: ( (this_Variable_0= ruleVariable | this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) ) )
            // InternalStructuredTextParser.g:1999:2: (this_Variable_0= ruleVariable | this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) )
            {
            // InternalStructuredTextParser.g:1999:2: (this_Variable_0= ruleVariable | this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) )
            int alt27=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA27_1 = input.LA(2);

                if ( (LA27_1==EOF||LA27_1==END_REPEAT||LA27_1==THEN||LA27_1==AND||LA27_1==MOD||(LA27_1>=XOR && LA27_1<=AsteriskAsterisk)||(LA27_1>=LessThanSignEqualsSign && LA27_1<=LessThanSignGreaterThanSign)||(LA27_1>=GreaterThanSignEqualsSign && LA27_1<=DO)||(LA27_1>=OF && LA27_1<=TO)||LA27_1==Ampersand||(LA27_1>=RightParenthesis && LA27_1<=Solidus)||(LA27_1>=Semicolon && LA27_1<=GreaterThanSign)||(LA27_1>=LeftSquareBracket && LA27_1<=RightSquareBracket)) ) {
                    alt27=1;
                }
                else if ( (LA27_1==LeftParenthesis) ) {
                    alt27=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 1, input);

                    throw nvae;
                }
                }
                break;
            case DT:
            case LT:
            case T:
                {
                alt27=1;
                }
                break;
            case TIME:
                {
                alt27=2;
                }
                break;
            case LeftParenthesis:
                {
                alt27=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // InternalStructuredTextParser.g:2000:3: this_Variable_0= ruleVariable
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
                    // InternalStructuredTextParser.g:2009:3: this_Func_Call_1= ruleFunc_Call
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
                    // InternalStructuredTextParser.g:2018:3: (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis )
                    {
                    // InternalStructuredTextParser.g:2018:3: (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis )
                    // InternalStructuredTextParser.g:2019:4: otherlv_2= LeftParenthesis this_Expression_3= ruleExpression otherlv_4= RightParenthesis
                    {
                    otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_16); 

                    				newLeafNode(otherlv_2, grammarAccess.getPrimary_ExprAccess().getLeftParenthesisKeyword_2_0());
                    			

                    				newCompositeNode(grammarAccess.getPrimary_ExprAccess().getExpressionParserRuleCall_2_1());
                    			
                    pushFollow(FOLLOW_18);
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
    // InternalStructuredTextParser.g:2040:1: entryRuleFunc_Call returns [EObject current=null] : iv_ruleFunc_Call= ruleFunc_Call EOF ;
    public final EObject entryRuleFunc_Call() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunc_Call = null;


        try {
            // InternalStructuredTextParser.g:2040:50: (iv_ruleFunc_Call= ruleFunc_Call EOF )
            // InternalStructuredTextParser.g:2041:2: iv_ruleFunc_Call= ruleFunc_Call EOF
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
    // InternalStructuredTextParser.g:2047:1: ruleFunc_Call returns [EObject current=null] : ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis ) ;
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
            // InternalStructuredTextParser.g:2053:2: ( ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis ) )
            // InternalStructuredTextParser.g:2054:2: ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis )
            {
            // InternalStructuredTextParser.g:2054:2: ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis )
            // InternalStructuredTextParser.g:2055:3: ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis
            {
            // InternalStructuredTextParser.g:2055:3: ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) )
            // InternalStructuredTextParser.g:2056:4: ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) )
            {
            // InternalStructuredTextParser.g:2056:4: ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) )
            // InternalStructuredTextParser.g:2057:5: (lv_func_0_1= RULE_ID | lv_func_0_2= TIME )
            {
            // InternalStructuredTextParser.g:2057:5: (lv_func_0_1= RULE_ID | lv_func_0_2= TIME )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RULE_ID) ) {
                alt28=1;
            }
            else if ( (LA28_0==TIME) ) {
                alt28=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // InternalStructuredTextParser.g:2058:6: lv_func_0_1= RULE_ID
                    {
                    lv_func_0_1=(Token)match(input,RULE_ID,FOLLOW_17); 

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
                    // InternalStructuredTextParser.g:2073:6: lv_func_0_2= TIME
                    {
                    lv_func_0_2=(Token)match(input,TIME,FOLLOW_17); 

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

            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_47); 

            			newLeafNode(otherlv_1, grammarAccess.getFunc_CallAccess().getLeftParenthesisKeyword_1());
            		
            // InternalStructuredTextParser.g:2090:3: ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( ((LA30_0>=LDATE_AND_TIME && LA30_0<=TIME_OF_DAY)||LA30_0==WSTRING||LA30_0==STRING||(LA30_0>=FALSE && LA30_0<=LTIME)||(LA30_0>=UDINT && LA30_0<=ULINT)||(LA30_0>=USINT && LA30_0<=WCHAR)||LA30_0==BOOL||(LA30_0>=CHAR && LA30_0<=DINT)||(LA30_0>=LINT && LA30_0<=SINT)||(LA30_0>=TIME && LA30_0<=UINT)||(LA30_0>=INT && LA30_0<=LDT)||(LA30_0>=NOT && LA30_0<=TOD)||LA30_0==DT||(LA30_0>=LD && LA30_0<=LT)||LA30_0==LeftParenthesis||LA30_0==PlusSign||LA30_0==HyphenMinus||LA30_0==T||LA30_0==D_1||(LA30_0>=RULE_ID && LA30_0<=RULE_UNSIGNED_INT)||LA30_0==RULE_S_BYTE_CHAR_STR||LA30_0==RULE_D_BYTE_CHAR_STR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalStructuredTextParser.g:2091:4: ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )*
                    {
                    // InternalStructuredTextParser.g:2091:4: ( (lv_args_2_0= ruleParam_Assign ) )
                    // InternalStructuredTextParser.g:2092:5: (lv_args_2_0= ruleParam_Assign )
                    {
                    // InternalStructuredTextParser.g:2092:5: (lv_args_2_0= ruleParam_Assign )
                    // InternalStructuredTextParser.g:2093:6: lv_args_2_0= ruleParam_Assign
                    {

                    						newCompositeNode(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_48);
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

                    // InternalStructuredTextParser.g:2110:4: (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( (LA29_0==Comma) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // InternalStructuredTextParser.g:2111:5: otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) )
                    	    {
                    	    otherlv_3=(Token)match(input,Comma,FOLLOW_16); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunc_CallAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalStructuredTextParser.g:2115:5: ( (lv_args_4_0= ruleParam_Assign ) )
                    	    // InternalStructuredTextParser.g:2116:6: (lv_args_4_0= ruleParam_Assign )
                    	    {
                    	    // InternalStructuredTextParser.g:2116:6: (lv_args_4_0= ruleParam_Assign )
                    	    // InternalStructuredTextParser.g:2117:7: lv_args_4_0= ruleParam_Assign
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_48);
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
                    	    break loop29;
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
    // InternalStructuredTextParser.g:2144:1: entryRuleParam_Assign returns [EObject current=null] : iv_ruleParam_Assign= ruleParam_Assign EOF ;
    public final EObject entryRuleParam_Assign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam_Assign = null;


        try {
            // InternalStructuredTextParser.g:2144:53: (iv_ruleParam_Assign= ruleParam_Assign EOF )
            // InternalStructuredTextParser.g:2145:2: iv_ruleParam_Assign= ruleParam_Assign EOF
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
    // InternalStructuredTextParser.g:2151:1: ruleParam_Assign returns [EObject current=null] : (this_Param_Assign_In_0= ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out ) ;
    public final EObject ruleParam_Assign() throws RecognitionException {
        EObject current = null;

        EObject this_Param_Assign_In_0 = null;

        EObject this_Param_Assign_Out_1 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2157:2: ( (this_Param_Assign_In_0= ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out ) )
            // InternalStructuredTextParser.g:2158:2: (this_Param_Assign_In_0= ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out )
            {
            // InternalStructuredTextParser.g:2158:2: (this_Param_Assign_In_0= ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out )
            int alt31=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==EOF||LA31_1==AND||LA31_1==MOD||(LA31_1>=XOR && LA31_1<=LessThanSignGreaterThanSign)||LA31_1==GreaterThanSignEqualsSign||LA31_1==OR||(LA31_1>=Ampersand && LA31_1<=Solidus)||(LA31_1>=LessThanSign && LA31_1<=GreaterThanSign)||LA31_1==LeftSquareBracket) ) {
                    alt31=1;
                }
                else if ( (LA31_1==EqualsSignGreaterThanSign) ) {
                    alt31=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 1, input);

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
                alt31=1;
                }
                break;
            case NOT:
                {
                int LA31_3 = input.LA(2);

                if ( (LA31_3==RULE_ID) ) {
                    int LA31_5 = input.LA(3);

                    if ( (LA31_5==EOF||LA31_5==AND||LA31_5==MOD||(LA31_5>=XOR && LA31_5<=AsteriskAsterisk)||(LA31_5>=LessThanSignEqualsSign && LA31_5<=LessThanSignGreaterThanSign)||LA31_5==GreaterThanSignEqualsSign||LA31_5==OR||(LA31_5>=Ampersand && LA31_5<=Solidus)||(LA31_5>=LessThanSign && LA31_5<=GreaterThanSign)||LA31_5==LeftSquareBracket) ) {
                        alt31=1;
                    }
                    else if ( (LA31_5==EqualsSignGreaterThanSign) ) {
                        alt31=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 5, input);

                        throw nvae;
                    }
                }
                else if ( (LA31_3==TIME||LA31_3==DT||LA31_3==LT||LA31_3==LeftParenthesis||LA31_3==T) ) {
                    alt31=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }

            switch (alt31) {
                case 1 :
                    // InternalStructuredTextParser.g:2159:3: this_Param_Assign_In_0= ruleParam_Assign_In
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
                    // InternalStructuredTextParser.g:2168:3: this_Param_Assign_Out_1= ruleParam_Assign_Out
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
    // InternalStructuredTextParser.g:2180:1: entryRuleParam_Assign_In returns [EObject current=null] : iv_ruleParam_Assign_In= ruleParam_Assign_In EOF ;
    public final EObject entryRuleParam_Assign_In() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam_Assign_In = null;


        try {
            // InternalStructuredTextParser.g:2180:56: (iv_ruleParam_Assign_In= ruleParam_Assign_In EOF )
            // InternalStructuredTextParser.g:2181:2: iv_ruleParam_Assign_In= ruleParam_Assign_In EOF
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
    // InternalStructuredTextParser.g:2187:1: ruleParam_Assign_In returns [EObject current=null] : ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) ) ;
    public final EObject ruleParam_Assign_In() throws RecognitionException {
        EObject current = null;

        Token lv_var_0_0=null;
        Token otherlv_1=null;
        EObject lv_expr_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2193:2: ( ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) ) )
            // InternalStructuredTextParser.g:2194:2: ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) )
            {
            // InternalStructuredTextParser.g:2194:2: ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) )
            // InternalStructuredTextParser.g:2195:3: ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) )
            {
            // InternalStructuredTextParser.g:2195:3: ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==RULE_ID) ) {
                int LA32_1 = input.LA(2);

                if ( (LA32_1==ColonEqualsSign) ) {
                    alt32=1;
                }
            }
            switch (alt32) {
                case 1 :
                    // InternalStructuredTextParser.g:2196:4: ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign
                    {
                    // InternalStructuredTextParser.g:2196:4: ( (lv_var_0_0= RULE_ID ) )
                    // InternalStructuredTextParser.g:2197:5: (lv_var_0_0= RULE_ID )
                    {
                    // InternalStructuredTextParser.g:2197:5: (lv_var_0_0= RULE_ID )
                    // InternalStructuredTextParser.g:2198:6: lv_var_0_0= RULE_ID
                    {
                    lv_var_0_0=(Token)match(input,RULE_ID,FOLLOW_15); 

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

                    otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_16); 

                    				newLeafNode(otherlv_1, grammarAccess.getParam_Assign_InAccess().getColonEqualsSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:2219:3: ( (lv_expr_2_0= ruleExpression ) )
            // InternalStructuredTextParser.g:2220:4: (lv_expr_2_0= ruleExpression )
            {
            // InternalStructuredTextParser.g:2220:4: (lv_expr_2_0= ruleExpression )
            // InternalStructuredTextParser.g:2221:5: lv_expr_2_0= ruleExpression
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
    // InternalStructuredTextParser.g:2242:1: entryRuleParam_Assign_Out returns [EObject current=null] : iv_ruleParam_Assign_Out= ruleParam_Assign_Out EOF ;
    public final EObject entryRuleParam_Assign_Out() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam_Assign_Out = null;


        try {
            // InternalStructuredTextParser.g:2242:57: (iv_ruleParam_Assign_Out= ruleParam_Assign_Out EOF )
            // InternalStructuredTextParser.g:2243:2: iv_ruleParam_Assign_Out= ruleParam_Assign_Out EOF
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
    // InternalStructuredTextParser.g:2249:1: ruleParam_Assign_Out returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) ) ;
    public final EObject ruleParam_Assign_Out() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token lv_var_1_0=null;
        Token otherlv_2=null;
        EObject lv_expr_3_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2255:2: ( ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) ) )
            // InternalStructuredTextParser.g:2256:2: ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) )
            {
            // InternalStructuredTextParser.g:2256:2: ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) )
            // InternalStructuredTextParser.g:2257:3: ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) )
            {
            // InternalStructuredTextParser.g:2257:3: ( (lv_not_0_0= NOT ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==NOT) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalStructuredTextParser.g:2258:4: (lv_not_0_0= NOT )
                    {
                    // InternalStructuredTextParser.g:2258:4: (lv_not_0_0= NOT )
                    // InternalStructuredTextParser.g:2259:5: lv_not_0_0= NOT
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

            // InternalStructuredTextParser.g:2271:3: ( (lv_var_1_0= RULE_ID ) )
            // InternalStructuredTextParser.g:2272:4: (lv_var_1_0= RULE_ID )
            {
            // InternalStructuredTextParser.g:2272:4: (lv_var_1_0= RULE_ID )
            // InternalStructuredTextParser.g:2273:5: lv_var_1_0= RULE_ID
            {
            lv_var_1_0=(Token)match(input,RULE_ID,FOLLOW_49); 

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

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_27); 

            			newLeafNode(otherlv_2, grammarAccess.getParam_Assign_OutAccess().getEqualsSignGreaterThanSignKeyword_2());
            		
            // InternalStructuredTextParser.g:2293:3: ( (lv_expr_3_0= ruleVariable ) )
            // InternalStructuredTextParser.g:2294:4: (lv_expr_3_0= ruleVariable )
            {
            // InternalStructuredTextParser.g:2294:4: (lv_expr_3_0= ruleVariable )
            // InternalStructuredTextParser.g:2295:5: lv_expr_3_0= ruleVariable
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
    // InternalStructuredTextParser.g:2316:1: entryRuleVariable returns [EObject current=null] : iv_ruleVariable= ruleVariable EOF ;
    public final EObject entryRuleVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable = null;


        try {
            // InternalStructuredTextParser.g:2316:49: (iv_ruleVariable= ruleVariable EOF )
            // InternalStructuredTextParser.g:2317:2: iv_ruleVariable= ruleVariable EOF
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
    // InternalStructuredTextParser.g:2323:1: ruleVariable returns [EObject current=null] : this_Variable_Subscript_0= ruleVariable_Subscript ;
    public final EObject ruleVariable() throws RecognitionException {
        EObject current = null;

        EObject this_Variable_Subscript_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2329:2: (this_Variable_Subscript_0= ruleVariable_Subscript )
            // InternalStructuredTextParser.g:2330:2: this_Variable_Subscript_0= ruleVariable_Subscript
            {

            		newCompositeNode(grammarAccess.getVariableAccess().getVariable_SubscriptParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_Variable_Subscript_0=ruleVariable_Subscript();

            state._fsp--;


            		current = this_Variable_Subscript_0;
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
    // $ANTLR end "ruleVariable"


    // $ANTLR start "entryRuleVariable_Subscript"
    // InternalStructuredTextParser.g:2341:1: entryRuleVariable_Subscript returns [EObject current=null] : iv_ruleVariable_Subscript= ruleVariable_Subscript EOF ;
    public final EObject entryRuleVariable_Subscript() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable_Subscript = null;


        try {
            // InternalStructuredTextParser.g:2341:59: (iv_ruleVariable_Subscript= ruleVariable_Subscript EOF )
            // InternalStructuredTextParser.g:2342:2: iv_ruleVariable_Subscript= ruleVariable_Subscript EOF
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
    // InternalStructuredTextParser.g:2348:1: ruleVariable_Subscript returns [EObject current=null] : ( (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? ) ;
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
            // InternalStructuredTextParser.g:2354:2: ( ( (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? ) )
            // InternalStructuredTextParser.g:2355:2: ( (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? )
            {
            // InternalStructuredTextParser.g:2355:2: ( (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? )
            // InternalStructuredTextParser.g:2356:3: (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
            {
            // InternalStructuredTextParser.g:2356:3: (this_Variable_Primary_0= ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter )
            int alt34=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA34_1 = input.LA(2);

                if ( (LA34_1==EOF||LA34_1==END_REPEAT||LA34_1==THEN||LA34_1==AND||LA34_1==MOD||(LA34_1>=XOR && LA34_1<=LessThanSignGreaterThanSign)||(LA34_1>=GreaterThanSignEqualsSign && LA34_1<=DO)||(LA34_1>=OF && LA34_1<=TO)||LA34_1==Ampersand||(LA34_1>=RightParenthesis && LA34_1<=HyphenMinus)||LA34_1==Solidus||(LA34_1>=Semicolon && LA34_1<=GreaterThanSign)||(LA34_1>=LeftSquareBracket && LA34_1<=RightSquareBracket)) ) {
                    alt34=1;
                }
                else if ( (LA34_1==FullStop) ) {
                    alt34=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 34, 1, input);

                    throw nvae;
                }
                }
                break;
            case T:
                {
                int LA34_2 = input.LA(2);

                if ( (LA34_2==FullStop) ) {
                    alt34=2;
                }
                else if ( (LA34_2==EOF||LA34_2==END_REPEAT||LA34_2==THEN||LA34_2==AND||LA34_2==MOD||(LA34_2>=XOR && LA34_2<=LessThanSignGreaterThanSign)||(LA34_2>=GreaterThanSignEqualsSign && LA34_2<=DO)||(LA34_2>=OF && LA34_2<=TO)||LA34_2==Ampersand||(LA34_2>=RightParenthesis && LA34_2<=HyphenMinus)||LA34_2==Solidus||(LA34_2>=Semicolon && LA34_2<=GreaterThanSign)||(LA34_2>=LeftSquareBracket && LA34_2<=RightSquareBracket)) ) {
                    alt34=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 34, 2, input);

                    throw nvae;
                }
                }
                break;
            case LT:
                {
                int LA34_3 = input.LA(2);

                if ( (LA34_3==FullStop) ) {
                    alt34=2;
                }
                else if ( (LA34_3==EOF||LA34_3==END_REPEAT||LA34_3==THEN||LA34_3==AND||LA34_3==MOD||(LA34_3>=XOR && LA34_3<=LessThanSignGreaterThanSign)||(LA34_3>=GreaterThanSignEqualsSign && LA34_3<=DO)||(LA34_3>=OF && LA34_3<=TO)||LA34_3==Ampersand||(LA34_3>=RightParenthesis && LA34_3<=HyphenMinus)||LA34_3==Solidus||(LA34_3>=Semicolon && LA34_3<=GreaterThanSign)||(LA34_3>=LeftSquareBracket && LA34_3<=RightSquareBracket)) ) {
                    alt34=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 34, 3, input);

                    throw nvae;
                }
                }
                break;
            case DT:
                {
                int LA34_4 = input.LA(2);

                if ( (LA34_4==EOF||LA34_4==END_REPEAT||LA34_4==THEN||LA34_4==AND||LA34_4==MOD||(LA34_4>=XOR && LA34_4<=LessThanSignGreaterThanSign)||(LA34_4>=GreaterThanSignEqualsSign && LA34_4<=DO)||(LA34_4>=OF && LA34_4<=TO)||LA34_4==Ampersand||(LA34_4>=RightParenthesis && LA34_4<=HyphenMinus)||LA34_4==Solidus||(LA34_4>=Semicolon && LA34_4<=GreaterThanSign)||(LA34_4>=LeftSquareBracket && LA34_4<=RightSquareBracket)) ) {
                    alt34=1;
                }
                else if ( (LA34_4==FullStop) ) {
                    alt34=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 34, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }

            switch (alt34) {
                case 1 :
                    // InternalStructuredTextParser.g:2357:4: this_Variable_Primary_0= ruleVariable_Primary
                    {

                    				newCompositeNode(grammarAccess.getVariable_SubscriptAccess().getVariable_PrimaryParserRuleCall_0_0());
                    			
                    pushFollow(FOLLOW_50);
                    this_Variable_Primary_0=ruleVariable_Primary();

                    state._fsp--;


                    				current = this_Variable_Primary_0;
                    				afterParserOrEnumRuleCall();
                    			

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2366:4: this_Variable_Adapter_1= ruleVariable_Adapter
                    {

                    				newCompositeNode(grammarAccess.getVariable_SubscriptAccess().getVariable_AdapterParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_50);
                    this_Variable_Adapter_1=ruleVariable_Adapter();

                    state._fsp--;


                    				current = this_Variable_Adapter_1;
                    				afterParserOrEnumRuleCall();
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:2375:3: ( () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==LeftSquareBracket) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalStructuredTextParser.g:2376:4: () otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket
                    {
                    // InternalStructuredTextParser.g:2376:4: ()
                    // InternalStructuredTextParser.g:2377:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_3=(Token)match(input,LeftSquareBracket,FOLLOW_16); 

                    				newLeafNode(otherlv_3, grammarAccess.getVariable_SubscriptAccess().getLeftSquareBracketKeyword_1_1());
                    			
                    // InternalStructuredTextParser.g:2387:4: ( (lv_index_4_0= ruleExpression ) )
                    // InternalStructuredTextParser.g:2388:5: (lv_index_4_0= ruleExpression )
                    {
                    // InternalStructuredTextParser.g:2388:5: (lv_index_4_0= ruleExpression )
                    // InternalStructuredTextParser.g:2389:6: lv_index_4_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_51);
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

                    // InternalStructuredTextParser.g:2406:4: (otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) ) )*
                    loop35:
                    do {
                        int alt35=2;
                        int LA35_0 = input.LA(1);

                        if ( (LA35_0==Comma) ) {
                            alt35=1;
                        }


                        switch (alt35) {
                    	case 1 :
                    	    // InternalStructuredTextParser.g:2407:5: otherlv_5= Comma ( (lv_index_6_0= ruleExpression ) )
                    	    {
                    	    otherlv_5=(Token)match(input,Comma,FOLLOW_16); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getVariable_SubscriptAccess().getCommaKeyword_1_3_0());
                    	    				
                    	    // InternalStructuredTextParser.g:2411:5: ( (lv_index_6_0= ruleExpression ) )
                    	    // InternalStructuredTextParser.g:2412:6: (lv_index_6_0= ruleExpression )
                    	    {
                    	    // InternalStructuredTextParser.g:2412:6: (lv_index_6_0= ruleExpression )
                    	    // InternalStructuredTextParser.g:2413:7: lv_index_6_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_51);
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
                    	    break loop35;
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
    // InternalStructuredTextParser.g:2440:1: entryRuleVariable_Adapter returns [EObject current=null] : iv_ruleVariable_Adapter= ruleVariable_Adapter EOF ;
    public final EObject entryRuleVariable_Adapter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable_Adapter = null;


        try {
            // InternalStructuredTextParser.g:2440:57: (iv_ruleVariable_Adapter= ruleVariable_Adapter EOF )
            // InternalStructuredTextParser.g:2441:2: iv_ruleVariable_Adapter= ruleVariable_Adapter EOF
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
    // InternalStructuredTextParser.g:2447:1: ruleVariable_Adapter returns [EObject current=null] : ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) ) ) ;
    public final EObject ruleVariable_Adapter() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2453:2: ( ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) ) ) )
            // InternalStructuredTextParser.g:2454:2: ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) ) )
            {
            // InternalStructuredTextParser.g:2454:2: ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:2455:3: () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:2455:3: ()
            // InternalStructuredTextParser.g:2456:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getVariable_AdapterAccess().getAdapterVariableAction_0(),
            					current);
            			

            }

            // InternalStructuredTextParser.g:2462:3: ( ( ruleAdapter_Name ) )
            // InternalStructuredTextParser.g:2463:4: ( ruleAdapter_Name )
            {
            // InternalStructuredTextParser.g:2463:4: ( ruleAdapter_Name )
            // InternalStructuredTextParser.g:2464:5: ruleAdapter_Name
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariable_AdapterRule());
            					}
            				

            					newCompositeNode(grammarAccess.getVariable_AdapterAccess().getAdapterAdapterDeclarationCrossReference_1_0());
            				
            pushFollow(FOLLOW_52);
            ruleAdapter_Name();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,FullStop,FOLLOW_27); 

            			newLeafNode(otherlv_2, grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_2());
            		
            // InternalStructuredTextParser.g:2482:3: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:2483:4: ( ruleVariable_Name )
            {
            // InternalStructuredTextParser.g:2483:4: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:2484:5: ruleVariable_Name
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


    // $ANTLR start "entryRuleAdapter_Name"
    // InternalStructuredTextParser.g:2502:1: entryRuleAdapter_Name returns [String current=null] : iv_ruleAdapter_Name= ruleAdapter_Name EOF ;
    public final String entryRuleAdapter_Name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAdapter_Name = null;


        try {
            // InternalStructuredTextParser.g:2502:52: (iv_ruleAdapter_Name= ruleAdapter_Name EOF )
            // InternalStructuredTextParser.g:2503:2: iv_ruleAdapter_Name= ruleAdapter_Name EOF
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
    // InternalStructuredTextParser.g:2509:1: ruleAdapter_Name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT ) ;
    public final AntlrDatatypeRuleToken ruleAdapter_Name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2515:2: ( (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT ) )
            // InternalStructuredTextParser.g:2516:2: (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT )
            {
            // InternalStructuredTextParser.g:2516:2: (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT )
            int alt37=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt37=1;
                }
                break;
            case T:
                {
                alt37=2;
                }
                break;
            case LT:
                {
                alt37=3;
                }
                break;
            case DT:
                {
                alt37=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }

            switch (alt37) {
                case 1 :
                    // InternalStructuredTextParser.g:2517:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2525:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAdapter_NameAccess().getTKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2531:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAdapter_NameAccess().getLTKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2537:3: kw= DT
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
    // InternalStructuredTextParser.g:2546:1: entryRuleVariable_Primary returns [EObject current=null] : iv_ruleVariable_Primary= ruleVariable_Primary EOF ;
    public final EObject entryRuleVariable_Primary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable_Primary = null;


        try {
            // InternalStructuredTextParser.g:2546:57: (iv_ruleVariable_Primary= ruleVariable_Primary EOF )
            // InternalStructuredTextParser.g:2547:2: iv_ruleVariable_Primary= ruleVariable_Primary EOF
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
    // InternalStructuredTextParser.g:2553:1: ruleVariable_Primary returns [EObject current=null] : ( ( ruleVariable_Name ) ) ;
    public final EObject ruleVariable_Primary() throws RecognitionException {
        EObject current = null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2559:2: ( ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:2560:2: ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:2560:2: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:2561:3: ( ruleVariable_Name )
            {
            // InternalStructuredTextParser.g:2561:3: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:2562:4: ruleVariable_Name
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
    // InternalStructuredTextParser.g:2579:1: entryRuleVariable_Name returns [String current=null] : iv_ruleVariable_Name= ruleVariable_Name EOF ;
    public final String entryRuleVariable_Name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVariable_Name = null;


        try {
            // InternalStructuredTextParser.g:2579:53: (iv_ruleVariable_Name= ruleVariable_Name EOF )
            // InternalStructuredTextParser.g:2580:2: iv_ruleVariable_Name= ruleVariable_Name EOF
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
    // InternalStructuredTextParser.g:2586:1: ruleVariable_Name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT ) ;
    public final AntlrDatatypeRuleToken ruleVariable_Name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2592:2: ( (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT ) )
            // InternalStructuredTextParser.g:2593:2: (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT )
            {
            // InternalStructuredTextParser.g:2593:2: (this_ID_0= RULE_ID | kw= T | kw= LT | kw= DT )
            int alt38=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt38=1;
                }
                break;
            case T:
                {
                alt38=2;
                }
                break;
            case LT:
                {
                alt38=3;
                }
                break;
            case DT:
                {
                alt38=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }

            switch (alt38) {
                case 1 :
                    // InternalStructuredTextParser.g:2594:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2602:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVariable_NameAccess().getTKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2608:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVariable_NameAccess().getLTKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2614:3: kw= DT
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
    // InternalStructuredTextParser.g:2623:1: entryRuleConstant returns [EObject current=null] : iv_ruleConstant= ruleConstant EOF ;
    public final EObject entryRuleConstant() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstant = null;


        try {
            // InternalStructuredTextParser.g:2623:49: (iv_ruleConstant= ruleConstant EOF )
            // InternalStructuredTextParser.g:2624:2: iv_ruleConstant= ruleConstant EOF
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
    // InternalStructuredTextParser.g:2630:1: ruleConstant returns [EObject current=null] : (this_Numeric_Literal_0= ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal ) ;
    public final EObject ruleConstant() throws RecognitionException {
        EObject current = null;

        EObject this_Numeric_Literal_0 = null;

        EObject this_Char_Literal_1 = null;

        EObject this_Time_Literal_2 = null;

        EObject this_Bool_Literal_3 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2636:2: ( (this_Numeric_Literal_0= ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal ) )
            // InternalStructuredTextParser.g:2637:2: (this_Numeric_Literal_0= ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal )
            {
            // InternalStructuredTextParser.g:2637:2: (this_Numeric_Literal_0= ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal )
            int alt39=4;
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
                alt39=1;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_S_BYTE_CHAR_STR:
            case RULE_D_BYTE_CHAR_STR:
                {
                alt39=2;
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
                alt39=3;
                }
                break;
            case FALSE:
            case BOOL:
            case TRUE:
                {
                alt39=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }

            switch (alt39) {
                case 1 :
                    // InternalStructuredTextParser.g:2638:3: this_Numeric_Literal_0= ruleNumeric_Literal
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
                    // InternalStructuredTextParser.g:2647:3: this_Char_Literal_1= ruleChar_Literal
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
                    // InternalStructuredTextParser.g:2656:3: this_Time_Literal_2= ruleTime_Literal
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
                    // InternalStructuredTextParser.g:2665:3: this_Bool_Literal_3= ruleBool_Literal
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
    // InternalStructuredTextParser.g:2677:1: entryRuleNumeric_Literal returns [EObject current=null] : iv_ruleNumeric_Literal= ruleNumeric_Literal EOF ;
    public final EObject entryRuleNumeric_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumeric_Literal = null;


        try {
            // InternalStructuredTextParser.g:2677:56: (iv_ruleNumeric_Literal= ruleNumeric_Literal EOF )
            // InternalStructuredTextParser.g:2678:2: iv_ruleNumeric_Literal= ruleNumeric_Literal EOF
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
    // InternalStructuredTextParser.g:2684:1: ruleNumeric_Literal returns [EObject current=null] : (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1= ruleReal_Literal ) ;
    public final EObject ruleNumeric_Literal() throws RecognitionException {
        EObject current = null;

        EObject this_Int_Literal_0 = null;

        EObject this_Real_Literal_1 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2690:2: ( (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1= ruleReal_Literal ) )
            // InternalStructuredTextParser.g:2691:2: (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1= ruleReal_Literal )
            {
            // InternalStructuredTextParser.g:2691:2: (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1= ruleReal_Literal )
            int alt40=2;
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
                alt40=1;
                }
                break;
            case PlusSign:
                {
                int LA40_2 = input.LA(2);

                if ( (LA40_2==RULE_UNSIGNED_INT) ) {
                    int LA40_4 = input.LA(3);

                    if ( (LA40_4==EOF||LA40_4==END_REPEAT||LA40_4==THEN||LA40_4==AND||LA40_4==MOD||(LA40_4>=XOR && LA40_4<=AsteriskAsterisk)||(LA40_4>=LessThanSignEqualsSign && LA40_4<=LessThanSignGreaterThanSign)||(LA40_4>=GreaterThanSignEqualsSign && LA40_4<=DO)||(LA40_4>=OF && LA40_4<=TO)||LA40_4==Ampersand||(LA40_4>=RightParenthesis && LA40_4<=HyphenMinus)||(LA40_4>=Solidus && LA40_4<=GreaterThanSign)||LA40_4==RightSquareBracket) ) {
                        alt40=1;
                    }
                    else if ( (LA40_4==FullStop) ) {
                        alt40=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 40, 4, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 40, 2, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA40_3 = input.LA(2);

                if ( (LA40_3==RULE_UNSIGNED_INT) ) {
                    int LA40_4 = input.LA(3);

                    if ( (LA40_4==EOF||LA40_4==END_REPEAT||LA40_4==THEN||LA40_4==AND||LA40_4==MOD||(LA40_4>=XOR && LA40_4<=AsteriskAsterisk)||(LA40_4>=LessThanSignEqualsSign && LA40_4<=LessThanSignGreaterThanSign)||(LA40_4>=GreaterThanSignEqualsSign && LA40_4<=DO)||(LA40_4>=OF && LA40_4<=TO)||LA40_4==Ampersand||(LA40_4>=RightParenthesis && LA40_4<=HyphenMinus)||(LA40_4>=Solidus && LA40_4<=GreaterThanSign)||LA40_4==RightSquareBracket) ) {
                        alt40=1;
                    }
                    else if ( (LA40_4==FullStop) ) {
                        alt40=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 40, 4, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 40, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_UNSIGNED_INT:
                {
                int LA40_4 = input.LA(2);

                if ( (LA40_4==EOF||LA40_4==END_REPEAT||LA40_4==THEN||LA40_4==AND||LA40_4==MOD||(LA40_4>=XOR && LA40_4<=AsteriskAsterisk)||(LA40_4>=LessThanSignEqualsSign && LA40_4<=LessThanSignGreaterThanSign)||(LA40_4>=GreaterThanSignEqualsSign && LA40_4<=DO)||(LA40_4>=OF && LA40_4<=TO)||LA40_4==Ampersand||(LA40_4>=RightParenthesis && LA40_4<=HyphenMinus)||(LA40_4>=Solidus && LA40_4<=GreaterThanSign)||LA40_4==RightSquareBracket) ) {
                    alt40=1;
                }
                else if ( (LA40_4==FullStop) ) {
                    alt40=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 40, 4, input);

                    throw nvae;
                }
                }
                break;
            case LREAL:
            case REAL:
                {
                alt40=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // InternalStructuredTextParser.g:2692:3: this_Int_Literal_0= ruleInt_Literal
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
                    // InternalStructuredTextParser.g:2701:3: this_Real_Literal_1= ruleReal_Literal
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
    // InternalStructuredTextParser.g:2713:1: entryRuleInt_Literal returns [EObject current=null] : iv_ruleInt_Literal= ruleInt_Literal EOF ;
    public final EObject entryRuleInt_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInt_Literal = null;


        try {
            // InternalStructuredTextParser.g:2713:52: (iv_ruleInt_Literal= ruleInt_Literal EOF )
            // InternalStructuredTextParser.g:2714:2: iv_ruleInt_Literal= ruleInt_Literal EOF
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
    // InternalStructuredTextParser.g:2720:1: ruleInt_Literal returns [EObject current=null] : ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) ) ) ;
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
            // InternalStructuredTextParser.g:2726:2: ( ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) ) ) )
            // InternalStructuredTextParser.g:2727:2: ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) ) )
            {
            // InternalStructuredTextParser.g:2727:2: ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) ) )
            // InternalStructuredTextParser.g:2728:3: ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) )
            {
            // InternalStructuredTextParser.g:2728:3: ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( ((LA41_0>=UDINT && LA41_0<=ULINT)||LA41_0==USINT||LA41_0==DINT||LA41_0==LINT||LA41_0==SINT||LA41_0==UINT||LA41_0==INT) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalStructuredTextParser.g:2729:4: ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1= NumberSign
                    {
                    // InternalStructuredTextParser.g:2729:4: ( (lv_type_0_0= ruleInt_Type_Name ) )
                    // InternalStructuredTextParser.g:2730:5: (lv_type_0_0= ruleInt_Type_Name )
                    {
                    // InternalStructuredTextParser.g:2730:5: (lv_type_0_0= ruleInt_Type_Name )
                    // InternalStructuredTextParser.g:2731:6: lv_type_0_0= ruleInt_Type_Name
                    {

                    						newCompositeNode(grammarAccess.getInt_LiteralAccess().getTypeInt_Type_NameEnumRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_53);
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

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_54); 

                    				newLeafNode(otherlv_1, grammarAccess.getInt_LiteralAccess().getNumberSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:2753:3: ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) ) )
            // InternalStructuredTextParser.g:2754:4: ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) )
            {
            // InternalStructuredTextParser.g:2754:4: ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT ) )
            // InternalStructuredTextParser.g:2755:5: (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT )
            {
            // InternalStructuredTextParser.g:2755:5: (lv_value_2_1= ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT )
            int alt42=4;
            switch ( input.LA(1) ) {
            case PlusSign:
            case HyphenMinus:
            case RULE_UNSIGNED_INT:
                {
                alt42=1;
                }
                break;
            case RULE_BINARY_INT:
                {
                alt42=2;
                }
                break;
            case RULE_OCTAL_INT:
                {
                alt42=3;
                }
                break;
            case RULE_HEX_INT:
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
                    // InternalStructuredTextParser.g:2756:6: lv_value_2_1= ruleSigned_Int
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
                    // InternalStructuredTextParser.g:2772:6: lv_value_2_2= RULE_BINARY_INT
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
                    // InternalStructuredTextParser.g:2787:6: lv_value_2_3= RULE_OCTAL_INT
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
                    // InternalStructuredTextParser.g:2802:6: lv_value_2_4= RULE_HEX_INT
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
    // InternalStructuredTextParser.g:2823:1: entryRuleSigned_Int returns [String current=null] : iv_ruleSigned_Int= ruleSigned_Int EOF ;
    public final String entryRuleSigned_Int() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSigned_Int = null;


        try {
            // InternalStructuredTextParser.g:2823:50: (iv_ruleSigned_Int= ruleSigned_Int EOF )
            // InternalStructuredTextParser.g:2824:2: iv_ruleSigned_Int= ruleSigned_Int EOF
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
    // InternalStructuredTextParser.g:2830:1: ruleSigned_Int returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT ) ;
    public final AntlrDatatypeRuleToken ruleSigned_Int() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_UNSIGNED_INT_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2836:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:2837:2: ( (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:2837:2: ( (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:2838:3: (kw= PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT
            {
            // InternalStructuredTextParser.g:2838:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt43=3;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==PlusSign) ) {
                alt43=1;
            }
            else if ( (LA43_0==HyphenMinus) ) {
                alt43=2;
            }
            switch (alt43) {
                case 1 :
                    // InternalStructuredTextParser.g:2839:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_10); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2845:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_10); 

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


    // $ANTLR start "entryRuleArray_Size"
    // InternalStructuredTextParser.g:2862:1: entryRuleArray_Size returns [String current=null] : iv_ruleArray_Size= ruleArray_Size EOF ;
    public final String entryRuleArray_Size() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleArray_Size = null;


        try {
            // InternalStructuredTextParser.g:2862:50: (iv_ruleArray_Size= ruleArray_Size EOF )
            // InternalStructuredTextParser.g:2863:2: iv_ruleArray_Size= ruleArray_Size EOF
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
    // InternalStructuredTextParser.g:2869:1: ruleArray_Size returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleArray_Size() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:2875:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:2876:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:2886:1: entryRuleReal_Literal returns [EObject current=null] : iv_ruleReal_Literal= ruleReal_Literal EOF ;
    public final EObject entryRuleReal_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReal_Literal = null;


        try {
            // InternalStructuredTextParser.g:2886:53: (iv_ruleReal_Literal= ruleReal_Literal EOF )
            // InternalStructuredTextParser.g:2887:2: iv_ruleReal_Literal= ruleReal_Literal EOF
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
    // InternalStructuredTextParser.g:2893:1: ruleReal_Literal returns [EObject current=null] : ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) ) ;
    public final EObject ruleReal_Literal() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2899:2: ( ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) ) )
            // InternalStructuredTextParser.g:2900:2: ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) )
            {
            // InternalStructuredTextParser.g:2900:2: ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) )
            // InternalStructuredTextParser.g:2901:3: ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) )
            {
            // InternalStructuredTextParser.g:2901:3: ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==LREAL||LA44_0==REAL) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalStructuredTextParser.g:2902:4: ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1= NumberSign
                    {
                    // InternalStructuredTextParser.g:2902:4: ( (lv_type_0_0= ruleReal_Type_Name ) )
                    // InternalStructuredTextParser.g:2903:5: (lv_type_0_0= ruleReal_Type_Name )
                    {
                    // InternalStructuredTextParser.g:2903:5: (lv_type_0_0= ruleReal_Type_Name )
                    // InternalStructuredTextParser.g:2904:6: lv_type_0_0= ruleReal_Type_Name
                    {

                    						newCompositeNode(grammarAccess.getReal_LiteralAccess().getTypeReal_Type_NameEnumRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_53);
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

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_55); 

                    				newLeafNode(otherlv_1, grammarAccess.getReal_LiteralAccess().getNumberSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:2926:3: ( (lv_value_2_0= ruleReal_Value ) )
            // InternalStructuredTextParser.g:2927:4: (lv_value_2_0= ruleReal_Value )
            {
            // InternalStructuredTextParser.g:2927:4: (lv_value_2_0= ruleReal_Value )
            // InternalStructuredTextParser.g:2928:5: lv_value_2_0= ruleReal_Value
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
    // InternalStructuredTextParser.g:2949:1: entryRuleReal_Value returns [String current=null] : iv_ruleReal_Value= ruleReal_Value EOF ;
    public final String entryRuleReal_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleReal_Value = null;


        try {
            // InternalStructuredTextParser.g:2949:50: (iv_ruleReal_Value= ruleReal_Value EOF )
            // InternalStructuredTextParser.g:2950:2: iv_ruleReal_Value= ruleReal_Value EOF
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
    // InternalStructuredTextParser.g:2956:1: ruleReal_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? ) ;
    public final AntlrDatatypeRuleToken ruleReal_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_UNSIGNED_INT_2=null;
        AntlrDatatypeRuleToken this_Signed_Int_0 = null;

        AntlrDatatypeRuleToken this_Signed_Int_4 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:2962:2: ( (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? ) )
            // InternalStructuredTextParser.g:2963:2: (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? )
            {
            // InternalStructuredTextParser.g:2963:2: (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? )
            // InternalStructuredTextParser.g:2964:3: this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )?
            {

            			newCompositeNode(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_0());
            		
            pushFollow(FOLLOW_52);
            this_Signed_Int_0=ruleSigned_Int();

            state._fsp--;


            			current.merge(this_Signed_Int_0);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,FullStop,FOLLOW_10); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getReal_ValueAccess().getFullStopKeyword_1());
            		
            this_UNSIGNED_INT_2=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_56); 

            			current.merge(this_UNSIGNED_INT_2);
            		

            			newLeafNode(this_UNSIGNED_INT_2, grammarAccess.getReal_ValueAccess().getUNSIGNED_INTTerminalRuleCall_2());
            		
            // InternalStructuredTextParser.g:2986:3: (kw= E this_Signed_Int_4= ruleSigned_Int )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==E) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalStructuredTextParser.g:2987:4: kw= E this_Signed_Int_4= ruleSigned_Int
                    {
                    kw=(Token)match(input,E,FOLLOW_57); 

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
    // InternalStructuredTextParser.g:3007:1: entryRuleBool_Literal returns [EObject current=null] : iv_ruleBool_Literal= ruleBool_Literal EOF ;
    public final EObject entryRuleBool_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBool_Literal = null;


        try {
            // InternalStructuredTextParser.g:3007:53: (iv_ruleBool_Literal= ruleBool_Literal EOF )
            // InternalStructuredTextParser.g:3008:2: iv_ruleBool_Literal= ruleBool_Literal EOF
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
    // InternalStructuredTextParser.g:3014:1: ruleBool_Literal returns [EObject current=null] : ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) ) ;
    public final EObject ruleBool_Literal() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3020:2: ( ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) ) )
            // InternalStructuredTextParser.g:3021:2: ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) )
            {
            // InternalStructuredTextParser.g:3021:2: ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) )
            // InternalStructuredTextParser.g:3022:3: ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) )
            {
            // InternalStructuredTextParser.g:3022:3: ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==BOOL) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalStructuredTextParser.g:3023:4: ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1= NumberSign
                    {
                    // InternalStructuredTextParser.g:3023:4: ( (lv_type_0_0= ruleBool_Type_Name ) )
                    // InternalStructuredTextParser.g:3024:5: (lv_type_0_0= ruleBool_Type_Name )
                    {
                    // InternalStructuredTextParser.g:3024:5: (lv_type_0_0= ruleBool_Type_Name )
                    // InternalStructuredTextParser.g:3025:6: lv_type_0_0= ruleBool_Type_Name
                    {

                    						newCompositeNode(grammarAccess.getBool_LiteralAccess().getTypeBool_Type_NameEnumRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_53);
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

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_13); 

                    				newLeafNode(otherlv_1, grammarAccess.getBool_LiteralAccess().getNumberSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:3047:3: ( (lv_value_2_0= ruleBool_Value ) )
            // InternalStructuredTextParser.g:3048:4: (lv_value_2_0= ruleBool_Value )
            {
            // InternalStructuredTextParser.g:3048:4: (lv_value_2_0= ruleBool_Value )
            // InternalStructuredTextParser.g:3049:5: lv_value_2_0= ruleBool_Value
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
    // InternalStructuredTextParser.g:3070:1: entryRuleBool_Value returns [String current=null] : iv_ruleBool_Value= ruleBool_Value EOF ;
    public final String entryRuleBool_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBool_Value = null;


        try {
            // InternalStructuredTextParser.g:3070:50: (iv_ruleBool_Value= ruleBool_Value EOF )
            // InternalStructuredTextParser.g:3071:2: iv_ruleBool_Value= ruleBool_Value EOF
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
    // InternalStructuredTextParser.g:3077:1: ruleBool_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= FALSE | kw= TRUE ) ;
    public final AntlrDatatypeRuleToken ruleBool_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3083:2: ( (kw= FALSE | kw= TRUE ) )
            // InternalStructuredTextParser.g:3084:2: (kw= FALSE | kw= TRUE )
            {
            // InternalStructuredTextParser.g:3084:2: (kw= FALSE | kw= TRUE )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==FALSE) ) {
                alt47=1;
            }
            else if ( (LA47_0==TRUE) ) {
                alt47=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // InternalStructuredTextParser.g:3085:3: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBool_ValueAccess().getFALSEKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3091:3: kw= TRUE
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
    // InternalStructuredTextParser.g:3100:1: entryRuleChar_Literal returns [EObject current=null] : iv_ruleChar_Literal= ruleChar_Literal EOF ;
    public final EObject entryRuleChar_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChar_Literal = null;


        try {
            // InternalStructuredTextParser.g:3100:53: (iv_ruleChar_Literal= ruleChar_Literal EOF )
            // InternalStructuredTextParser.g:3101:2: iv_ruleChar_Literal= ruleChar_Literal EOF
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
    // InternalStructuredTextParser.g:3107:1: ruleChar_Literal returns [EObject current=null] : ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) ) ) ;
    public final EObject ruleChar_Literal() throws RecognitionException {
        EObject current = null;

        Token lv_length_1_0=null;
        Token otherlv_2=null;
        Token lv_value_3_1=null;
        Token lv_value_3_2=null;
        Enumerator lv_type_0_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3113:2: ( ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) ) ) )
            // InternalStructuredTextParser.g:3114:2: ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) ) )
            {
            // InternalStructuredTextParser.g:3114:2: ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) ) )
            // InternalStructuredTextParser.g:3115:3: ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) )
            {
            // InternalStructuredTextParser.g:3115:3: ( ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==WSTRING||LA49_0==STRING||LA49_0==WCHAR||LA49_0==CHAR) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalStructuredTextParser.g:3116:4: ( (lv_type_0_0= ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign
                    {
                    // InternalStructuredTextParser.g:3116:4: ( (lv_type_0_0= ruleString_Type_Name ) )
                    // InternalStructuredTextParser.g:3117:5: (lv_type_0_0= ruleString_Type_Name )
                    {
                    // InternalStructuredTextParser.g:3117:5: (lv_type_0_0= ruleString_Type_Name )
                    // InternalStructuredTextParser.g:3118:6: lv_type_0_0= ruleString_Type_Name
                    {

                    						newCompositeNode(grammarAccess.getChar_LiteralAccess().getTypeString_Type_NameEnumRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_58);
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

                    // InternalStructuredTextParser.g:3135:4: ( (lv_length_1_0= RULE_UNSIGNED_INT ) )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==RULE_UNSIGNED_INT) ) {
                        alt48=1;
                    }
                    switch (alt48) {
                        case 1 :
                            // InternalStructuredTextParser.g:3136:5: (lv_length_1_0= RULE_UNSIGNED_INT )
                            {
                            // InternalStructuredTextParser.g:3136:5: (lv_length_1_0= RULE_UNSIGNED_INT )
                            // InternalStructuredTextParser.g:3137:6: lv_length_1_0= RULE_UNSIGNED_INT
                            {
                            lv_length_1_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_53); 

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

                    otherlv_2=(Token)match(input,NumberSign,FOLLOW_59); 

                    				newLeafNode(otherlv_2, grammarAccess.getChar_LiteralAccess().getNumberSignKeyword_0_2());
                    			

                    }
                    break;

            }

            // InternalStructuredTextParser.g:3158:3: ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) )
            // InternalStructuredTextParser.g:3159:4: ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) )
            {
            // InternalStructuredTextParser.g:3159:4: ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) )
            // InternalStructuredTextParser.g:3160:5: (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR )
            {
            // InternalStructuredTextParser.g:3160:5: (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==RULE_S_BYTE_CHAR_STR) ) {
                alt50=1;
            }
            else if ( (LA50_0==RULE_D_BYTE_CHAR_STR) ) {
                alt50=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // InternalStructuredTextParser.g:3161:6: lv_value_3_1= RULE_S_BYTE_CHAR_STR
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
                    // InternalStructuredTextParser.g:3176:6: lv_value_3_2= RULE_D_BYTE_CHAR_STR
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
    // InternalStructuredTextParser.g:3197:1: entryRuleTime_Literal returns [EObject current=null] : iv_ruleTime_Literal= ruleTime_Literal EOF ;
    public final EObject entryRuleTime_Literal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTime_Literal = null;


        try {
            // InternalStructuredTextParser.g:3197:53: (iv_ruleTime_Literal= ruleTime_Literal EOF )
            // InternalStructuredTextParser.g:3198:2: iv_ruleTime_Literal= ruleTime_Literal EOF
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
    // InternalStructuredTextParser.g:3204:1: ruleTime_Literal returns [EObject current=null] : (this_Duration_0= ruleDuration | this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3= ruleDate_And_Time ) ;
    public final EObject ruleTime_Literal() throws RecognitionException {
        EObject current = null;

        EObject this_Duration_0 = null;

        EObject this_Time_Of_Day_1 = null;

        EObject this_Date_2 = null;

        EObject this_Date_And_Time_3 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3210:2: ( (this_Duration_0= ruleDuration | this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3= ruleDate_And_Time ) )
            // InternalStructuredTextParser.g:3211:2: (this_Duration_0= ruleDuration | this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3= ruleDate_And_Time )
            {
            // InternalStructuredTextParser.g:3211:2: (this_Duration_0= ruleDuration | this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3= ruleDate_And_Time )
            int alt51=4;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt51=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt51=2;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D_1:
                {
                alt51=3;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt51=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }

            switch (alt51) {
                case 1 :
                    // InternalStructuredTextParser.g:3212:3: this_Duration_0= ruleDuration
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
                    // InternalStructuredTextParser.g:3221:3: this_Time_Of_Day_1= ruleTime_Of_Day
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
                    // InternalStructuredTextParser.g:3230:3: this_Date_2= ruleDate
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
                    // InternalStructuredTextParser.g:3239:3: this_Date_And_Time_3= ruleDate_And_Time
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
    // InternalStructuredTextParser.g:3251:1: entryRuleDuration returns [EObject current=null] : iv_ruleDuration= ruleDuration EOF ;
    public final EObject entryRuleDuration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDuration = null;


        try {
            // InternalStructuredTextParser.g:3251:49: (iv_ruleDuration= ruleDuration EOF )
            // InternalStructuredTextParser.g:3252:2: iv_ruleDuration= ruleDuration EOF
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
    // InternalStructuredTextParser.g:3258:1: ruleDuration returns [EObject current=null] : ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )* ) ;
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
            // InternalStructuredTextParser.g:3264:2: ( ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )* ) )
            // InternalStructuredTextParser.g:3265:2: ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )* )
            {
            // InternalStructuredTextParser.g:3265:2: ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )* )
            // InternalStructuredTextParser.g:3266:3: ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )*
            {
            // InternalStructuredTextParser.g:3266:3: ( (lv_type_0_0= ruleTime_Type_Name ) )
            // InternalStructuredTextParser.g:3267:4: (lv_type_0_0= ruleTime_Type_Name )
            {
            // InternalStructuredTextParser.g:3267:4: (lv_type_0_0= ruleTime_Type_Name )
            // InternalStructuredTextParser.g:3268:5: lv_type_0_0= ruleTime_Type_Name
            {

            					newCompositeNode(grammarAccess.getDurationAccess().getTypeTime_Type_NameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_53);
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

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_57); 

            			newLeafNode(otherlv_1, grammarAccess.getDurationAccess().getNumberSignKeyword_1());
            		
            // InternalStructuredTextParser.g:3289:3: (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )?
            int alt52=3;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==PlusSign) ) {
                alt52=1;
            }
            else if ( (LA52_0==HyphenMinus) ) {
                alt52=2;
            }
            switch (alt52) {
                case 1 :
                    // InternalStructuredTextParser.g:3290:4: otherlv_2= PlusSign
                    {
                    otherlv_2=(Token)match(input,PlusSign,FOLLOW_57); 

                    				newLeafNode(otherlv_2, grammarAccess.getDurationAccess().getPlusSignKeyword_2_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3295:4: ( (lv_negative_3_0= HyphenMinus ) )
                    {
                    // InternalStructuredTextParser.g:3295:4: ( (lv_negative_3_0= HyphenMinus ) )
                    // InternalStructuredTextParser.g:3296:5: (lv_negative_3_0= HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:3296:5: (lv_negative_3_0= HyphenMinus )
                    // InternalStructuredTextParser.g:3297:6: lv_negative_3_0= HyphenMinus
                    {
                    lv_negative_3_0=(Token)match(input,HyphenMinus,FOLLOW_57); 

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

            // InternalStructuredTextParser.g:3310:3: ( (lv_value_4_0= ruleDuration_Value ) )
            // InternalStructuredTextParser.g:3311:4: (lv_value_4_0= ruleDuration_Value )
            {
            // InternalStructuredTextParser.g:3311:4: (lv_value_4_0= ruleDuration_Value )
            // InternalStructuredTextParser.g:3312:5: lv_value_4_0= ruleDuration_Value
            {

            					newCompositeNode(grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_60);
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

            // InternalStructuredTextParser.g:3329:3: (otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==KW__) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalStructuredTextParser.g:3330:4: otherlv_5= KW__ ( (lv_value_6_0= ruleDuration_Value ) )
            	    {
            	    otherlv_5=(Token)match(input,KW__,FOLLOW_57); 

            	    				newLeafNode(otherlv_5, grammarAccess.getDurationAccess().get_Keyword_4_0());
            	    			
            	    // InternalStructuredTextParser.g:3334:4: ( (lv_value_6_0= ruleDuration_Value ) )
            	    // InternalStructuredTextParser.g:3335:5: (lv_value_6_0= ruleDuration_Value )
            	    {
            	    // InternalStructuredTextParser.g:3335:5: (lv_value_6_0= ruleDuration_Value )
            	    // InternalStructuredTextParser.g:3336:6: lv_value_6_0= ruleDuration_Value
            	    {

            	    						newCompositeNode(grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_60);
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
            	    break loop53;
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
    // InternalStructuredTextParser.g:3358:1: entryRuleDuration_Value returns [EObject current=null] : iv_ruleDuration_Value= ruleDuration_Value EOF ;
    public final EObject entryRuleDuration_Value() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDuration_Value = null;


        try {
            // InternalStructuredTextParser.g:3358:55: (iv_ruleDuration_Value= ruleDuration_Value EOF )
            // InternalStructuredTextParser.g:3359:2: iv_ruleDuration_Value= ruleDuration_Value EOF
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
    // InternalStructuredTextParser.g:3365:1: ruleDuration_Value returns [EObject current=null] : ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) ) ) ;
    public final EObject ruleDuration_Value() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;

        Enumerator lv_unit_1_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3371:2: ( ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) ) ) )
            // InternalStructuredTextParser.g:3372:2: ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) ) )
            {
            // InternalStructuredTextParser.g:3372:2: ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) ) )
            // InternalStructuredTextParser.g:3373:3: ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0= ruleDuration_Unit ) )
            {
            // InternalStructuredTextParser.g:3373:3: ( (lv_value_0_0= ruleFix_Point ) )
            // InternalStructuredTextParser.g:3374:4: (lv_value_0_0= ruleFix_Point )
            {
            // InternalStructuredTextParser.g:3374:4: (lv_value_0_0= ruleFix_Point )
            // InternalStructuredTextParser.g:3375:5: lv_value_0_0= ruleFix_Point
            {

            					newCompositeNode(grammarAccess.getDuration_ValueAccess().getValueFix_PointParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_61);
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

            // InternalStructuredTextParser.g:3392:3: ( (lv_unit_1_0= ruleDuration_Unit ) )
            // InternalStructuredTextParser.g:3393:4: (lv_unit_1_0= ruleDuration_Unit )
            {
            // InternalStructuredTextParser.g:3393:4: (lv_unit_1_0= ruleDuration_Unit )
            // InternalStructuredTextParser.g:3394:5: lv_unit_1_0= ruleDuration_Unit
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
    // InternalStructuredTextParser.g:3415:1: entryRuleFix_Point returns [String current=null] : iv_ruleFix_Point= ruleFix_Point EOF ;
    public final String entryRuleFix_Point() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFix_Point = null;


        try {
            // InternalStructuredTextParser.g:3415:49: (iv_ruleFix_Point= ruleFix_Point EOF )
            // InternalStructuredTextParser.g:3416:2: iv_ruleFix_Point= ruleFix_Point EOF
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
    // InternalStructuredTextParser.g:3422:1: ruleFix_Point returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleFix_Point() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;
        Token kw=null;
        Token this_UNSIGNED_INT_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3428:2: ( (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? ) )
            // InternalStructuredTextParser.g:3429:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? )
            {
            // InternalStructuredTextParser.g:3429:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? )
            // InternalStructuredTextParser.g:3430:3: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )?
            {
            this_UNSIGNED_INT_0=(Token)match(input,RULE_UNSIGNED_INT,FOLLOW_62); 

            			current.merge(this_UNSIGNED_INT_0);
            		

            			newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_0());
            		
            // InternalStructuredTextParser.g:3437:3: (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==FullStop) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalStructuredTextParser.g:3438:4: kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_10); 

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
    // InternalStructuredTextParser.g:3455:1: entryRuleTime_Of_Day returns [EObject current=null] : iv_ruleTime_Of_Day= ruleTime_Of_Day EOF ;
    public final EObject entryRuleTime_Of_Day() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTime_Of_Day = null;


        try {
            // InternalStructuredTextParser.g:3455:52: (iv_ruleTime_Of_Day= ruleTime_Of_Day EOF )
            // InternalStructuredTextParser.g:3456:2: iv_ruleTime_Of_Day= ruleTime_Of_Day EOF
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
    // InternalStructuredTextParser.g:3462:1: ruleTime_Of_Day returns [EObject current=null] : ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) ) ;
    public final EObject ruleTime_Of_Day() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3468:2: ( ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) ) )
            // InternalStructuredTextParser.g:3469:2: ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) )
            {
            // InternalStructuredTextParser.g:3469:2: ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) )
            // InternalStructuredTextParser.g:3470:3: ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) )
            {
            // InternalStructuredTextParser.g:3470:3: ( (lv_type_0_0= ruleTod_Type_Name ) )
            // InternalStructuredTextParser.g:3471:4: (lv_type_0_0= ruleTod_Type_Name )
            {
            // InternalStructuredTextParser.g:3471:4: (lv_type_0_0= ruleTod_Type_Name )
            // InternalStructuredTextParser.g:3472:5: lv_type_0_0= ruleTod_Type_Name
            {

            					newCompositeNode(grammarAccess.getTime_Of_DayAccess().getTypeTod_Type_NameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_53);
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

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_10); 

            			newLeafNode(otherlv_1, grammarAccess.getTime_Of_DayAccess().getNumberSignKeyword_1());
            		
            // InternalStructuredTextParser.g:3493:3: ( (lv_value_2_0= ruleDaytime ) )
            // InternalStructuredTextParser.g:3494:4: (lv_value_2_0= ruleDaytime )
            {
            // InternalStructuredTextParser.g:3494:4: (lv_value_2_0= ruleDaytime )
            // InternalStructuredTextParser.g:3495:5: lv_value_2_0= ruleDaytime
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
    // InternalStructuredTextParser.g:3516:1: entryRuleDaytime returns [String current=null] : iv_ruleDaytime= ruleDaytime EOF ;
    public final String entryRuleDaytime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDaytime = null;


        try {
            // InternalStructuredTextParser.g:3516:47: (iv_ruleDaytime= ruleDaytime EOF )
            // InternalStructuredTextParser.g:3517:2: iv_ruleDaytime= ruleDaytime EOF
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
    // InternalStructuredTextParser.g:3523:1: ruleDaytime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second ) ;
    public final AntlrDatatypeRuleToken ruleDaytime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Day_Hour_0 = null;

        AntlrDatatypeRuleToken this_Day_Minute_2 = null;

        AntlrDatatypeRuleToken this_Day_Second_4 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3529:2: ( (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second ) )
            // InternalStructuredTextParser.g:3530:2: (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second )
            {
            // InternalStructuredTextParser.g:3530:2: (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second )
            // InternalStructuredTextParser.g:3531:3: this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4= ruleDay_Second
            {

            			newCompositeNode(grammarAccess.getDaytimeAccess().getDay_HourParserRuleCall_0());
            		
            pushFollow(FOLLOW_7);
            this_Day_Hour_0=ruleDay_Hour();

            state._fsp--;


            			current.merge(this_Day_Hour_0);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,Colon,FOLLOW_10); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getDaytimeAccess().getColonKeyword_1());
            		

            			newCompositeNode(grammarAccess.getDaytimeAccess().getDay_MinuteParserRuleCall_2());
            		
            pushFollow(FOLLOW_7);
            this_Day_Minute_2=ruleDay_Minute();

            state._fsp--;


            			current.merge(this_Day_Minute_2);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,Colon,FOLLOW_57); 

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
    // InternalStructuredTextParser.g:3575:1: entryRuleDay_Hour returns [String current=null] : iv_ruleDay_Hour= ruleDay_Hour EOF ;
    public final String entryRuleDay_Hour() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDay_Hour = null;


        try {
            // InternalStructuredTextParser.g:3575:48: (iv_ruleDay_Hour= ruleDay_Hour EOF )
            // InternalStructuredTextParser.g:3576:2: iv_ruleDay_Hour= ruleDay_Hour EOF
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
    // InternalStructuredTextParser.g:3582:1: ruleDay_Hour returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleDay_Hour() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3588:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3589:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:3599:1: entryRuleDay_Minute returns [String current=null] : iv_ruleDay_Minute= ruleDay_Minute EOF ;
    public final String entryRuleDay_Minute() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDay_Minute = null;


        try {
            // InternalStructuredTextParser.g:3599:50: (iv_ruleDay_Minute= ruleDay_Minute EOF )
            // InternalStructuredTextParser.g:3600:2: iv_ruleDay_Minute= ruleDay_Minute EOF
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
    // InternalStructuredTextParser.g:3606:1: ruleDay_Minute returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleDay_Minute() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3612:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3613:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:3623:1: entryRuleDay_Second returns [String current=null] : iv_ruleDay_Second= ruleDay_Second EOF ;
    public final String entryRuleDay_Second() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDay_Second = null;


        try {
            // InternalStructuredTextParser.g:3623:50: (iv_ruleDay_Second= ruleDay_Second EOF )
            // InternalStructuredTextParser.g:3624:2: iv_ruleDay_Second= ruleDay_Second EOF
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
    // InternalStructuredTextParser.g:3630:1: ruleDay_Second returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_Fix_Point_0= ruleFix_Point ;
    public final AntlrDatatypeRuleToken ruleDay_Second() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_Fix_Point_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3636:2: (this_Fix_Point_0= ruleFix_Point )
            // InternalStructuredTextParser.g:3637:2: this_Fix_Point_0= ruleFix_Point
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
    // InternalStructuredTextParser.g:3650:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalStructuredTextParser.g:3650:45: (iv_ruleDate= ruleDate EOF )
            // InternalStructuredTextParser.g:3651:2: iv_ruleDate= ruleDate EOF
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
    // InternalStructuredTextParser.g:3657:1: ruleDate returns [EObject current=null] : ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) ) ;
    public final EObject ruleDate() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3663:2: ( ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) ) )
            // InternalStructuredTextParser.g:3664:2: ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) )
            {
            // InternalStructuredTextParser.g:3664:2: ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) )
            // InternalStructuredTextParser.g:3665:3: ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) )
            {
            // InternalStructuredTextParser.g:3665:3: ( (lv_type_0_0= ruleDate_Type_Name ) )
            // InternalStructuredTextParser.g:3666:4: (lv_type_0_0= ruleDate_Type_Name )
            {
            // InternalStructuredTextParser.g:3666:4: (lv_type_0_0= ruleDate_Type_Name )
            // InternalStructuredTextParser.g:3667:5: lv_type_0_0= ruleDate_Type_Name
            {

            					newCompositeNode(grammarAccess.getDateAccess().getTypeDate_Type_NameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_53);
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

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_10); 

            			newLeafNode(otherlv_1, grammarAccess.getDateAccess().getNumberSignKeyword_1());
            		
            // InternalStructuredTextParser.g:3688:3: ( (lv_value_2_0= ruleDate_Literal ) )
            // InternalStructuredTextParser.g:3689:4: (lv_value_2_0= ruleDate_Literal )
            {
            // InternalStructuredTextParser.g:3689:4: (lv_value_2_0= ruleDate_Literal )
            // InternalStructuredTextParser.g:3690:5: lv_value_2_0= ruleDate_Literal
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
    // InternalStructuredTextParser.g:3711:1: entryRuleDate_Literal returns [String current=null] : iv_ruleDate_Literal= ruleDate_Literal EOF ;
    public final String entryRuleDate_Literal() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate_Literal = null;


        try {
            // InternalStructuredTextParser.g:3711:52: (iv_ruleDate_Literal= ruleDate_Literal EOF )
            // InternalStructuredTextParser.g:3712:2: iv_ruleDate_Literal= ruleDate_Literal EOF
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
    // InternalStructuredTextParser.g:3718:1: ruleDate_Literal returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay ) ;
    public final AntlrDatatypeRuleToken ruleDate_Literal() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Year_0 = null;

        AntlrDatatypeRuleToken this_Month_2 = null;

        AntlrDatatypeRuleToken this_Day_4 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3724:2: ( (this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay ) )
            // InternalStructuredTextParser.g:3725:2: (this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay )
            {
            // InternalStructuredTextParser.g:3725:2: (this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay )
            // InternalStructuredTextParser.g:3726:3: this_Year_0= ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay
            {

            			newCompositeNode(grammarAccess.getDate_LiteralAccess().getYearParserRuleCall_0());
            		
            pushFollow(FOLLOW_63);
            this_Year_0=ruleYear();

            state._fsp--;


            			current.merge(this_Year_0);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,HyphenMinus,FOLLOW_10); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_1());
            		

            			newCompositeNode(grammarAccess.getDate_LiteralAccess().getMonthParserRuleCall_2());
            		
            pushFollow(FOLLOW_63);
            this_Month_2=ruleMonth();

            state._fsp--;


            			current.merge(this_Month_2);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,HyphenMinus,FOLLOW_10); 

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
    // InternalStructuredTextParser.g:3770:1: entryRuleYear returns [String current=null] : iv_ruleYear= ruleYear EOF ;
    public final String entryRuleYear() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleYear = null;


        try {
            // InternalStructuredTextParser.g:3770:44: (iv_ruleYear= ruleYear EOF )
            // InternalStructuredTextParser.g:3771:2: iv_ruleYear= ruleYear EOF
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
    // InternalStructuredTextParser.g:3777:1: ruleYear returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleYear() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3783:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3784:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:3794:1: entryRuleMonth returns [String current=null] : iv_ruleMonth= ruleMonth EOF ;
    public final String entryRuleMonth() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMonth = null;


        try {
            // InternalStructuredTextParser.g:3794:45: (iv_ruleMonth= ruleMonth EOF )
            // InternalStructuredTextParser.g:3795:2: iv_ruleMonth= ruleMonth EOF
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
    // InternalStructuredTextParser.g:3801:1: ruleMonth returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleMonth() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3807:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3808:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:3818:1: entryRuleDay returns [String current=null] : iv_ruleDay= ruleDay EOF ;
    public final String entryRuleDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDay = null;


        try {
            // InternalStructuredTextParser.g:3818:43: (iv_ruleDay= ruleDay EOF )
            // InternalStructuredTextParser.g:3819:2: iv_ruleDay= ruleDay EOF
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
    // InternalStructuredTextParser.g:3825:1: ruleDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
    public final AntlrDatatypeRuleToken ruleDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNSIGNED_INT_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3831:2: (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:3832:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:3842:1: entryRuleDate_And_Time returns [EObject current=null] : iv_ruleDate_And_Time= ruleDate_And_Time EOF ;
    public final EObject entryRuleDate_And_Time() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate_And_Time = null;


        try {
            // InternalStructuredTextParser.g:3842:54: (iv_ruleDate_And_Time= ruleDate_And_Time EOF )
            // InternalStructuredTextParser.g:3843:2: iv_ruleDate_And_Time= ruleDate_And_Time EOF
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
    // InternalStructuredTextParser.g:3849:1: ruleDate_And_Time returns [EObject current=null] : ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) ) ;
    public final EObject ruleDate_And_Time() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Enumerator lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3855:2: ( ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) ) )
            // InternalStructuredTextParser.g:3856:2: ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) )
            {
            // InternalStructuredTextParser.g:3856:2: ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) )
            // InternalStructuredTextParser.g:3857:3: ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) )
            {
            // InternalStructuredTextParser.g:3857:3: ( (lv_type_0_0= ruleDT_Type_Name ) )
            // InternalStructuredTextParser.g:3858:4: (lv_type_0_0= ruleDT_Type_Name )
            {
            // InternalStructuredTextParser.g:3858:4: (lv_type_0_0= ruleDT_Type_Name )
            // InternalStructuredTextParser.g:3859:5: lv_type_0_0= ruleDT_Type_Name
            {

            					newCompositeNode(grammarAccess.getDate_And_TimeAccess().getTypeDT_Type_NameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_53);
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

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_10); 

            			newLeafNode(otherlv_1, grammarAccess.getDate_And_TimeAccess().getNumberSignKeyword_1());
            		
            // InternalStructuredTextParser.g:3880:3: ( (lv_value_2_0= ruleDate_And_Time_Value ) )
            // InternalStructuredTextParser.g:3881:4: (lv_value_2_0= ruleDate_And_Time_Value )
            {
            // InternalStructuredTextParser.g:3881:4: (lv_value_2_0= ruleDate_And_Time_Value )
            // InternalStructuredTextParser.g:3882:5: lv_value_2_0= ruleDate_And_Time_Value
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
    // InternalStructuredTextParser.g:3903:1: entryRuleDate_And_Time_Value returns [String current=null] : iv_ruleDate_And_Time_Value= ruleDate_And_Time_Value EOF ;
    public final String entryRuleDate_And_Time_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate_And_Time_Value = null;


        try {
            // InternalStructuredTextParser.g:3903:59: (iv_ruleDate_And_Time_Value= ruleDate_And_Time_Value EOF )
            // InternalStructuredTextParser.g:3904:2: iv_ruleDate_And_Time_Value= ruleDate_And_Time_Value EOF
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
    // InternalStructuredTextParser.g:3910:1: ruleDate_And_Time_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime ) ;
    public final AntlrDatatypeRuleToken ruleDate_And_Time_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Date_Literal_0 = null;

        AntlrDatatypeRuleToken this_Daytime_2 = null;



        	enterRule();

        try {
            // InternalStructuredTextParser.g:3916:2: ( (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime ) )
            // InternalStructuredTextParser.g:3917:2: (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime )
            {
            // InternalStructuredTextParser.g:3917:2: (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime )
            // InternalStructuredTextParser.g:3918:3: this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime
            {

            			newCompositeNode(grammarAccess.getDate_And_Time_ValueAccess().getDate_LiteralParserRuleCall_0());
            		
            pushFollow(FOLLOW_63);
            this_Date_Literal_0=ruleDate_Literal();

            state._fsp--;


            			current.merge(this_Date_Literal_0);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,HyphenMinus,FOLLOW_10); 

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
    // InternalStructuredTextParser.g:3947:1: entryRuleType_Name returns [String current=null] : iv_ruleType_Name= ruleType_Name EOF ;
    public final String entryRuleType_Name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleType_Name = null;


        try {
            // InternalStructuredTextParser.g:3947:49: (iv_ruleType_Name= ruleType_Name EOF )
            // InternalStructuredTextParser.g:3948:2: iv_ruleType_Name= ruleType_Name EOF
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
    // InternalStructuredTextParser.g:3954:1: ruleType_Name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL ) ;
    public final AntlrDatatypeRuleToken ruleType_Name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:3960:2: ( (this_ID_0= RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL ) )
            // InternalStructuredTextParser.g:3961:2: (this_ID_0= RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL )
            {
            // InternalStructuredTextParser.g:3961:2: (this_ID_0= RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL )
            int alt55=26;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt55=1;
                }
                break;
            case DINT:
                {
                alt55=2;
                }
                break;
            case INT:
                {
                alt55=3;
                }
                break;
            case SINT:
                {
                alt55=4;
                }
                break;
            case LINT:
                {
                alt55=5;
                }
                break;
            case UINT:
                {
                alt55=6;
                }
                break;
            case USINT:
                {
                alt55=7;
                }
                break;
            case UDINT:
                {
                alt55=8;
                }
                break;
            case ULINT:
                {
                alt55=9;
                }
                break;
            case REAL:
                {
                alt55=10;
                }
                break;
            case LREAL:
                {
                alt55=11;
                }
                break;
            case STRING:
                {
                alt55=12;
                }
                break;
            case WSTRING:
                {
                alt55=13;
                }
                break;
            case CHAR:
                {
                alt55=14;
                }
                break;
            case WCHAR:
                {
                alt55=15;
                }
                break;
            case TIME:
                {
                alt55=16;
                }
                break;
            case LTIME:
                {
                alt55=17;
                }
                break;
            case TIME_OF_DAY:
                {
                alt55=18;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt55=19;
                }
                break;
            case TOD:
                {
                alt55=20;
                }
                break;
            case LTOD:
                {
                alt55=21;
                }
                break;
            case DATE:
                {
                alt55=22;
                }
                break;
            case LDATE:
                {
                alt55=23;
                }
                break;
            case DATE_AND_TIME:
                {
                alt55=24;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt55=25;
                }
                break;
            case BOOL:
                {
                alt55=26;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }

            switch (alt55) {
                case 1 :
                    // InternalStructuredTextParser.g:3962:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:3970:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getDINTKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:3976:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getINTKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:3982:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getSINTKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:3988:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLINTKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:3994:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getUINTKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:4000:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getUSINTKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:4006:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getUDINTKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalStructuredTextParser.g:4012:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getULINTKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalStructuredTextParser.g:4018:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getREALKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalStructuredTextParser.g:4024:3: kw= LREAL
                    {
                    kw=(Token)match(input,LREAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLREALKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalStructuredTextParser.g:4030:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getSTRINGKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalStructuredTextParser.g:4036:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getWSTRINGKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalStructuredTextParser.g:4042:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getCHARKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalStructuredTextParser.g:4048:3: kw= WCHAR
                    {
                    kw=(Token)match(input,WCHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getWCHARKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalStructuredTextParser.g:4054:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getTIMEKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalStructuredTextParser.g:4060:3: kw= LTIME
                    {
                    kw=(Token)match(input,LTIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLTIMEKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalStructuredTextParser.g:4066:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalStructuredTextParser.g:4072:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalStructuredTextParser.g:4078:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getTODKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalStructuredTextParser.g:4084:3: kw= LTOD
                    {
                    kw=(Token)match(input,LTOD,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLTODKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalStructuredTextParser.g:4090:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getDATEKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalStructuredTextParser.g:4096:3: kw= LDATE
                    {
                    kw=(Token)match(input,LDATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLDATEKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalStructuredTextParser.g:4102:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalStructuredTextParser.g:4108:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalStructuredTextParser.g:4114:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getType_NameAccess().getBOOLKeyword_25());
                    		

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
    // InternalStructuredTextParser.g:4123:1: ruleOr_Operator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOr_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4129:2: ( (enumLiteral_0= OR ) )
            // InternalStructuredTextParser.g:4130:2: (enumLiteral_0= OR )
            {
            // InternalStructuredTextParser.g:4130:2: (enumLiteral_0= OR )
            // InternalStructuredTextParser.g:4131:3: enumLiteral_0= OR
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
    // InternalStructuredTextParser.g:4140:1: ruleXor_Operator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXor_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4146:2: ( (enumLiteral_0= XOR ) )
            // InternalStructuredTextParser.g:4147:2: (enumLiteral_0= XOR )
            {
            // InternalStructuredTextParser.g:4147:2: (enumLiteral_0= XOR )
            // InternalStructuredTextParser.g:4148:3: enumLiteral_0= XOR
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
    // InternalStructuredTextParser.g:4157:1: ruleAnd_Operator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAnd_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4163:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalStructuredTextParser.g:4164:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalStructuredTextParser.g:4164:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==AND) ) {
                alt56=1;
            }
            else if ( (LA56_0==Ampersand) ) {
                alt56=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // InternalStructuredTextParser.g:4165:3: (enumLiteral_0= AND )
                    {
                    // InternalStructuredTextParser.g:4165:3: (enumLiteral_0= AND )
                    // InternalStructuredTextParser.g:4166:4: enumLiteral_0= AND
                    {
                    enumLiteral_0=(Token)match(input,AND,FOLLOW_2); 

                    				current = grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4173:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalStructuredTextParser.g:4173:3: (enumLiteral_1= Ampersand )
                    // InternalStructuredTextParser.g:4174:4: enumLiteral_1= Ampersand
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
    // InternalStructuredTextParser.g:4184:1: ruleCompare_Operator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleCompare_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4190:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalStructuredTextParser.g:4191:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalStructuredTextParser.g:4191:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==EqualsSign) ) {
                alt57=1;
            }
            else if ( (LA57_0==LessThanSignGreaterThanSign) ) {
                alt57=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // InternalStructuredTextParser.g:4192:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalStructuredTextParser.g:4192:3: (enumLiteral_0= EqualsSign )
                    // InternalStructuredTextParser.g:4193:4: enumLiteral_0= EqualsSign
                    {
                    enumLiteral_0=(Token)match(input,EqualsSign,FOLLOW_2); 

                    				current = grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4200:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalStructuredTextParser.g:4200:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalStructuredTextParser.g:4201:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalStructuredTextParser.g:4211:1: ruleEqu_Operator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleEqu_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4217:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalStructuredTextParser.g:4218:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalStructuredTextParser.g:4218:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt58=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt58=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt58=2;
                }
                break;
            case GreaterThanSign:
                {
                alt58=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt58=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }

            switch (alt58) {
                case 1 :
                    // InternalStructuredTextParser.g:4219:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalStructuredTextParser.g:4219:3: (enumLiteral_0= LessThanSign )
                    // InternalStructuredTextParser.g:4220:4: enumLiteral_0= LessThanSign
                    {
                    enumLiteral_0=(Token)match(input,LessThanSign,FOLLOW_2); 

                    				current = grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4227:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalStructuredTextParser.g:4227:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalStructuredTextParser.g:4228:4: enumLiteral_1= LessThanSignEqualsSign
                    {
                    enumLiteral_1=(Token)match(input,LessThanSignEqualsSign,FOLLOW_2); 

                    				current = grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4235:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalStructuredTextParser.g:4235:3: (enumLiteral_2= GreaterThanSign )
                    // InternalStructuredTextParser.g:4236:4: enumLiteral_2= GreaterThanSign
                    {
                    enumLiteral_2=(Token)match(input,GreaterThanSign,FOLLOW_2); 

                    				current = grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4243:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalStructuredTextParser.g:4243:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalStructuredTextParser.g:4244:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalStructuredTextParser.g:4254:1: ruleAdd_Operator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAdd_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4260:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalStructuredTextParser.g:4261:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalStructuredTextParser.g:4261:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==PlusSign) ) {
                alt59=1;
            }
            else if ( (LA59_0==HyphenMinus) ) {
                alt59=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // InternalStructuredTextParser.g:4262:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalStructuredTextParser.g:4262:3: (enumLiteral_0= PlusSign )
                    // InternalStructuredTextParser.g:4263:4: enumLiteral_0= PlusSign
                    {
                    enumLiteral_0=(Token)match(input,PlusSign,FOLLOW_2); 

                    				current = grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4270:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:4270:3: (enumLiteral_1= HyphenMinus )
                    // InternalStructuredTextParser.g:4271:4: enumLiteral_1= HyphenMinus
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
    // InternalStructuredTextParser.g:4281:1: ruleTerm_Operator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleTerm_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4287:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalStructuredTextParser.g:4288:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalStructuredTextParser.g:4288:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt60=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt60=1;
                }
                break;
            case Solidus:
                {
                alt60=2;
                }
                break;
            case MOD:
                {
                alt60=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }

            switch (alt60) {
                case 1 :
                    // InternalStructuredTextParser.g:4289:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalStructuredTextParser.g:4289:3: (enumLiteral_0= Asterisk )
                    // InternalStructuredTextParser.g:4290:4: enumLiteral_0= Asterisk
                    {
                    enumLiteral_0=(Token)match(input,Asterisk,FOLLOW_2); 

                    				current = grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4297:3: (enumLiteral_1= Solidus )
                    {
                    // InternalStructuredTextParser.g:4297:3: (enumLiteral_1= Solidus )
                    // InternalStructuredTextParser.g:4298:4: enumLiteral_1= Solidus
                    {
                    enumLiteral_1=(Token)match(input,Solidus,FOLLOW_2); 

                    				current = grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4305:3: (enumLiteral_2= MOD )
                    {
                    // InternalStructuredTextParser.g:4305:3: (enumLiteral_2= MOD )
                    // InternalStructuredTextParser.g:4306:4: enumLiteral_2= MOD
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
    // InternalStructuredTextParser.g:4316:1: rulePower_Operator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePower_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4322:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalStructuredTextParser.g:4323:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalStructuredTextParser.g:4323:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalStructuredTextParser.g:4324:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalStructuredTextParser.g:4333:1: ruleUnary_Operator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnary_Operator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4339:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalStructuredTextParser.g:4340:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalStructuredTextParser.g:4340:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt61=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt61=1;
                }
                break;
            case PlusSign:
                {
                alt61=2;
                }
                break;
            case NOT:
                {
                alt61=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }

            switch (alt61) {
                case 1 :
                    // InternalStructuredTextParser.g:4341:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:4341:3: (enumLiteral_0= HyphenMinus )
                    // InternalStructuredTextParser.g:4342:4: enumLiteral_0= HyphenMinus
                    {
                    enumLiteral_0=(Token)match(input,HyphenMinus,FOLLOW_2); 

                    				current = grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4349:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalStructuredTextParser.g:4349:3: (enumLiteral_1= PlusSign )
                    // InternalStructuredTextParser.g:4350:4: enumLiteral_1= PlusSign
                    {
                    enumLiteral_1=(Token)match(input,PlusSign,FOLLOW_2); 

                    				current = grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4357:3: (enumLiteral_2= NOT )
                    {
                    // InternalStructuredTextParser.g:4357:3: (enumLiteral_2= NOT )
                    // InternalStructuredTextParser.g:4358:4: enumLiteral_2= NOT
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
    // InternalStructuredTextParser.g:4368:1: ruleDuration_Unit returns [Enumerator current=null] : ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) ) ;
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
            // InternalStructuredTextParser.g:4374:2: ( ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) ) )
            // InternalStructuredTextParser.g:4375:2: ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) )
            {
            // InternalStructuredTextParser.g:4375:2: ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) )
            int alt62=7;
            switch ( input.LA(1) ) {
            case D_1:
                {
                alt62=1;
                }
                break;
            case H:
                {
                alt62=2;
                }
                break;
            case M:
                {
                alt62=3;
                }
                break;
            case S:
                {
                alt62=4;
                }
                break;
            case Ms:
                {
                alt62=5;
                }
                break;
            case Us:
                {
                alt62=6;
                }
                break;
            case Ns:
                {
                alt62=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }

            switch (alt62) {
                case 1 :
                    // InternalStructuredTextParser.g:4376:3: (enumLiteral_0= D_1 )
                    {
                    // InternalStructuredTextParser.g:4376:3: (enumLiteral_0= D_1 )
                    // InternalStructuredTextParser.g:4377:4: enumLiteral_0= D_1
                    {
                    enumLiteral_0=(Token)match(input,D_1,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getDAYSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDuration_UnitAccess().getDAYSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4384:3: (enumLiteral_1= H )
                    {
                    // InternalStructuredTextParser.g:4384:3: (enumLiteral_1= H )
                    // InternalStructuredTextParser.g:4385:4: enumLiteral_1= H
                    {
                    enumLiteral_1=(Token)match(input,H,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getHOURSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDuration_UnitAccess().getHOURSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4392:3: (enumLiteral_2= M )
                    {
                    // InternalStructuredTextParser.g:4392:3: (enumLiteral_2= M )
                    // InternalStructuredTextParser.g:4393:4: enumLiteral_2= M
                    {
                    enumLiteral_2=(Token)match(input,M,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getMINUTESEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDuration_UnitAccess().getMINUTESEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4400:3: (enumLiteral_3= S )
                    {
                    // InternalStructuredTextParser.g:4400:3: (enumLiteral_3= S )
                    // InternalStructuredTextParser.g:4401:4: enumLiteral_3= S
                    {
                    enumLiteral_3=(Token)match(input,S,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getSECONDSEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getDuration_UnitAccess().getSECONDSEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:4408:3: (enumLiteral_4= Ms )
                    {
                    // InternalStructuredTextParser.g:4408:3: (enumLiteral_4= Ms )
                    // InternalStructuredTextParser.g:4409:4: enumLiteral_4= Ms
                    {
                    enumLiteral_4=(Token)match(input,Ms,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getMILLISEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getDuration_UnitAccess().getMILLISEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:4416:3: (enumLiteral_5= Us )
                    {
                    // InternalStructuredTextParser.g:4416:3: (enumLiteral_5= Us )
                    // InternalStructuredTextParser.g:4417:4: enumLiteral_5= Us
                    {
                    enumLiteral_5=(Token)match(input,Us,FOLLOW_2); 

                    				current = grammarAccess.getDuration_UnitAccess().getMICROSEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getDuration_UnitAccess().getMICROSEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:4424:3: (enumLiteral_6= Ns )
                    {
                    // InternalStructuredTextParser.g:4424:3: (enumLiteral_6= Ns )
                    // InternalStructuredTextParser.g:4425:4: enumLiteral_6= Ns
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
    // InternalStructuredTextParser.g:4435:1: ruleInt_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) ) ;
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
            // InternalStructuredTextParser.g:4441:2: ( ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) ) )
            // InternalStructuredTextParser.g:4442:2: ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) )
            {
            // InternalStructuredTextParser.g:4442:2: ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) )
            int alt63=8;
            switch ( input.LA(1) ) {
            case DINT:
                {
                alt63=1;
                }
                break;
            case INT:
                {
                alt63=2;
                }
                break;
            case SINT:
                {
                alt63=3;
                }
                break;
            case LINT:
                {
                alt63=4;
                }
                break;
            case UINT:
                {
                alt63=5;
                }
                break;
            case USINT:
                {
                alt63=6;
                }
                break;
            case UDINT:
                {
                alt63=7;
                }
                break;
            case ULINT:
                {
                alt63=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // InternalStructuredTextParser.g:4443:3: (enumLiteral_0= DINT )
                    {
                    // InternalStructuredTextParser.g:4443:3: (enumLiteral_0= DINT )
                    // InternalStructuredTextParser.g:4444:4: enumLiteral_0= DINT
                    {
                    enumLiteral_0=(Token)match(input,DINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4451:3: (enumLiteral_1= INT )
                    {
                    // InternalStructuredTextParser.g:4451:3: (enumLiteral_1= INT )
                    // InternalStructuredTextParser.g:4452:4: enumLiteral_1= INT
                    {
                    enumLiteral_1=(Token)match(input,INT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4459:3: (enumLiteral_2= SINT )
                    {
                    // InternalStructuredTextParser.g:4459:3: (enumLiteral_2= SINT )
                    // InternalStructuredTextParser.g:4460:4: enumLiteral_2= SINT
                    {
                    enumLiteral_2=(Token)match(input,SINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4467:3: (enumLiteral_3= LINT )
                    {
                    // InternalStructuredTextParser.g:4467:3: (enumLiteral_3= LINT )
                    // InternalStructuredTextParser.g:4468:4: enumLiteral_3= LINT
                    {
                    enumLiteral_3=(Token)match(input,LINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:4475:3: (enumLiteral_4= UINT )
                    {
                    // InternalStructuredTextParser.g:4475:3: (enumLiteral_4= UINT )
                    // InternalStructuredTextParser.g:4476:4: enumLiteral_4= UINT
                    {
                    enumLiteral_4=(Token)match(input,UINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:4483:3: (enumLiteral_5= USINT )
                    {
                    // InternalStructuredTextParser.g:4483:3: (enumLiteral_5= USINT )
                    // InternalStructuredTextParser.g:4484:4: enumLiteral_5= USINT
                    {
                    enumLiteral_5=(Token)match(input,USINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:4491:3: (enumLiteral_6= UDINT )
                    {
                    // InternalStructuredTextParser.g:4491:3: (enumLiteral_6= UDINT )
                    // InternalStructuredTextParser.g:4492:4: enumLiteral_6= UDINT
                    {
                    enumLiteral_6=(Token)match(input,UDINT,FOLLOW_2); 

                    				current = grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:4499:3: (enumLiteral_7= ULINT )
                    {
                    // InternalStructuredTextParser.g:4499:3: (enumLiteral_7= ULINT )
                    // InternalStructuredTextParser.g:4500:4: enumLiteral_7= ULINT
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
    // InternalStructuredTextParser.g:4510:1: ruleReal_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) ) ;
    public final Enumerator ruleReal_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4516:2: ( ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) ) )
            // InternalStructuredTextParser.g:4517:2: ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) )
            {
            // InternalStructuredTextParser.g:4517:2: ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==REAL) ) {
                alt64=1;
            }
            else if ( (LA64_0==LREAL) ) {
                alt64=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // InternalStructuredTextParser.g:4518:3: (enumLiteral_0= REAL )
                    {
                    // InternalStructuredTextParser.g:4518:3: (enumLiteral_0= REAL )
                    // InternalStructuredTextParser.g:4519:4: enumLiteral_0= REAL
                    {
                    enumLiteral_0=(Token)match(input,REAL,FOLLOW_2); 

                    				current = grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4526:3: (enumLiteral_1= LREAL )
                    {
                    // InternalStructuredTextParser.g:4526:3: (enumLiteral_1= LREAL )
                    // InternalStructuredTextParser.g:4527:4: enumLiteral_1= LREAL
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
    // InternalStructuredTextParser.g:4537:1: ruleString_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= STRING ) | (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) ) ;
    public final Enumerator ruleString_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4543:2: ( ( (enumLiteral_0= STRING ) | (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) ) )
            // InternalStructuredTextParser.g:4544:2: ( (enumLiteral_0= STRING ) | (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) )
            {
            // InternalStructuredTextParser.g:4544:2: ( (enumLiteral_0= STRING ) | (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) )
            int alt65=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt65=1;
                }
                break;
            case WSTRING:
                {
                alt65=2;
                }
                break;
            case CHAR:
                {
                alt65=3;
                }
                break;
            case WCHAR:
                {
                alt65=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }

            switch (alt65) {
                case 1 :
                    // InternalStructuredTextParser.g:4545:3: (enumLiteral_0= STRING )
                    {
                    // InternalStructuredTextParser.g:4545:3: (enumLiteral_0= STRING )
                    // InternalStructuredTextParser.g:4546:4: enumLiteral_0= STRING
                    {
                    enumLiteral_0=(Token)match(input,STRING,FOLLOW_2); 

                    				current = grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4553:3: (enumLiteral_1= WSTRING )
                    {
                    // InternalStructuredTextParser.g:4553:3: (enumLiteral_1= WSTRING )
                    // InternalStructuredTextParser.g:4554:4: enumLiteral_1= WSTRING
                    {
                    enumLiteral_1=(Token)match(input,WSTRING,FOLLOW_2); 

                    				current = grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4561:3: (enumLiteral_2= CHAR )
                    {
                    // InternalStructuredTextParser.g:4561:3: (enumLiteral_2= CHAR )
                    // InternalStructuredTextParser.g:4562:4: enumLiteral_2= CHAR
                    {
                    enumLiteral_2=(Token)match(input,CHAR,FOLLOW_2); 

                    				current = grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4569:3: (enumLiteral_3= WCHAR )
                    {
                    // InternalStructuredTextParser.g:4569:3: (enumLiteral_3= WCHAR )
                    // InternalStructuredTextParser.g:4570:4: enumLiteral_3= WCHAR
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
    // InternalStructuredTextParser.g:4580:1: ruleTime_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) ) ;
    public final Enumerator ruleTime_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4586:2: ( ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) ) )
            // InternalStructuredTextParser.g:4587:2: ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) )
            {
            // InternalStructuredTextParser.g:4587:2: ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) )
            int alt66=4;
            switch ( input.LA(1) ) {
            case TIME:
                {
                alt66=1;
                }
                break;
            case LTIME:
                {
                alt66=2;
                }
                break;
            case T:
                {
                alt66=3;
                }
                break;
            case LT:
                {
                alt66=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    // InternalStructuredTextParser.g:4588:3: (enumLiteral_0= TIME )
                    {
                    // InternalStructuredTextParser.g:4588:3: (enumLiteral_0= TIME )
                    // InternalStructuredTextParser.g:4589:4: enumLiteral_0= TIME
                    {
                    enumLiteral_0=(Token)match(input,TIME,FOLLOW_2); 

                    				current = grammarAccess.getTime_Type_NameAccess().getTIMEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTime_Type_NameAccess().getTIMEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4596:3: (enumLiteral_1= LTIME )
                    {
                    // InternalStructuredTextParser.g:4596:3: (enumLiteral_1= LTIME )
                    // InternalStructuredTextParser.g:4597:4: enumLiteral_1= LTIME
                    {
                    enumLiteral_1=(Token)match(input,LTIME,FOLLOW_2); 

                    				current = grammarAccess.getTime_Type_NameAccess().getLTIMEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTime_Type_NameAccess().getLTIMEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4604:3: (enumLiteral_2= T )
                    {
                    // InternalStructuredTextParser.g:4604:3: (enumLiteral_2= T )
                    // InternalStructuredTextParser.g:4605:4: enumLiteral_2= T
                    {
                    enumLiteral_2=(Token)match(input,T,FOLLOW_2); 

                    				current = grammarAccess.getTime_Type_NameAccess().getTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTime_Type_NameAccess().getTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4612:3: (enumLiteral_3= LT )
                    {
                    // InternalStructuredTextParser.g:4612:3: (enumLiteral_3= LT )
                    // InternalStructuredTextParser.g:4613:4: enumLiteral_3= LT
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
    // InternalStructuredTextParser.g:4623:1: ruleTod_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) ) ;
    public final Enumerator ruleTod_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4629:2: ( ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) ) )
            // InternalStructuredTextParser.g:4630:2: ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) )
            {
            // InternalStructuredTextParser.g:4630:2: ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) )
            int alt67=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt67=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt67=2;
                }
                break;
            case TOD:
                {
                alt67=3;
                }
                break;
            case LTOD:
                {
                alt67=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }

            switch (alt67) {
                case 1 :
                    // InternalStructuredTextParser.g:4631:3: (enumLiteral_0= TIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:4631:3: (enumLiteral_0= TIME_OF_DAY )
                    // InternalStructuredTextParser.g:4632:4: enumLiteral_0= TIME_OF_DAY
                    {
                    enumLiteral_0=(Token)match(input,TIME_OF_DAY,FOLLOW_2); 

                    				current = grammarAccess.getTod_Type_NameAccess().getTIME_OF_DAYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTod_Type_NameAccess().getTIME_OF_DAYEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4639:3: (enumLiteral_1= LTIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:4639:3: (enumLiteral_1= LTIME_OF_DAY )
                    // InternalStructuredTextParser.g:4640:4: enumLiteral_1= LTIME_OF_DAY
                    {
                    enumLiteral_1=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); 

                    				current = grammarAccess.getTod_Type_NameAccess().getLTIME_OF_DAYEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTod_Type_NameAccess().getLTIME_OF_DAYEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4647:3: (enumLiteral_2= TOD )
                    {
                    // InternalStructuredTextParser.g:4647:3: (enumLiteral_2= TOD )
                    // InternalStructuredTextParser.g:4648:4: enumLiteral_2= TOD
                    {
                    enumLiteral_2=(Token)match(input,TOD,FOLLOW_2); 

                    				current = grammarAccess.getTod_Type_NameAccess().getTODEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTod_Type_NameAccess().getTODEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4655:3: (enumLiteral_3= LTOD )
                    {
                    // InternalStructuredTextParser.g:4655:3: (enumLiteral_3= LTOD )
                    // InternalStructuredTextParser.g:4656:4: enumLiteral_3= LTOD
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
    // InternalStructuredTextParser.g:4666:1: ruleDate_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) ) ;
    public final Enumerator ruleDate_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4672:2: ( ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) ) )
            // InternalStructuredTextParser.g:4673:2: ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) )
            {
            // InternalStructuredTextParser.g:4673:2: ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) )
            int alt68=4;
            switch ( input.LA(1) ) {
            case DATE:
                {
                alt68=1;
                }
                break;
            case LDATE:
                {
                alt68=2;
                }
                break;
            case D_1:
                {
                alt68=3;
                }
                break;
            case LD:
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
                    // InternalStructuredTextParser.g:4674:3: (enumLiteral_0= DATE )
                    {
                    // InternalStructuredTextParser.g:4674:3: (enumLiteral_0= DATE )
                    // InternalStructuredTextParser.g:4675:4: enumLiteral_0= DATE
                    {
                    enumLiteral_0=(Token)match(input,DATE,FOLLOW_2); 

                    				current = grammarAccess.getDate_Type_NameAccess().getDATEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDate_Type_NameAccess().getDATEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4682:3: (enumLiteral_1= LDATE )
                    {
                    // InternalStructuredTextParser.g:4682:3: (enumLiteral_1= LDATE )
                    // InternalStructuredTextParser.g:4683:4: enumLiteral_1= LDATE
                    {
                    enumLiteral_1=(Token)match(input,LDATE,FOLLOW_2); 

                    				current = grammarAccess.getDate_Type_NameAccess().getLDATEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDate_Type_NameAccess().getLDATEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4690:3: (enumLiteral_2= D_1 )
                    {
                    // InternalStructuredTextParser.g:4690:3: (enumLiteral_2= D_1 )
                    // InternalStructuredTextParser.g:4691:4: enumLiteral_2= D_1
                    {
                    enumLiteral_2=(Token)match(input,D_1,FOLLOW_2); 

                    				current = grammarAccess.getDate_Type_NameAccess().getDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDate_Type_NameAccess().getDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4698:3: (enumLiteral_3= LD )
                    {
                    // InternalStructuredTextParser.g:4698:3: (enumLiteral_3= LD )
                    // InternalStructuredTextParser.g:4699:4: enumLiteral_3= LD
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
    // InternalStructuredTextParser.g:4709:1: ruleDT_Type_Name returns [Enumerator current=null] : ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) ) ;
    public final Enumerator ruleDT_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4715:2: ( ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) ) )
            // InternalStructuredTextParser.g:4716:2: ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) )
            {
            // InternalStructuredTextParser.g:4716:2: ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) )
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
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // InternalStructuredTextParser.g:4717:3: (enumLiteral_0= DATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:4717:3: (enumLiteral_0= DATE_AND_TIME )
                    // InternalStructuredTextParser.g:4718:4: enumLiteral_0= DATE_AND_TIME
                    {
                    enumLiteral_0=(Token)match(input,DATE_AND_TIME,FOLLOW_2); 

                    				current = grammarAccess.getDT_Type_NameAccess().getDATE_AND_TIMEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDT_Type_NameAccess().getDATE_AND_TIMEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:4725:3: (enumLiteral_1= LDATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:4725:3: (enumLiteral_1= LDATE_AND_TIME )
                    // InternalStructuredTextParser.g:4726:4: enumLiteral_1= LDATE_AND_TIME
                    {
                    enumLiteral_1=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); 

                    				current = grammarAccess.getDT_Type_NameAccess().getLDATE_AND_TIMEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDT_Type_NameAccess().getLDATE_AND_TIMEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:4733:3: (enumLiteral_2= DT )
                    {
                    // InternalStructuredTextParser.g:4733:3: (enumLiteral_2= DT )
                    // InternalStructuredTextParser.g:4734:4: enumLiteral_2= DT
                    {
                    enumLiteral_2=(Token)match(input,DT,FOLLOW_2); 

                    				current = grammarAccess.getDT_Type_NameAccess().getDTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDT_Type_NameAccess().getDTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:4741:3: (enumLiteral_3= LDT )
                    {
                    // InternalStructuredTextParser.g:4741:3: (enumLiteral_3= LDT )
                    // InternalStructuredTextParser.g:4742:4: enumLiteral_3= LDT
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
    // InternalStructuredTextParser.g:4752:1: ruleBool_Type_Name returns [Enumerator current=null] : (enumLiteral_0= BOOL ) ;
    public final Enumerator ruleBool_Type_Name() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalStructuredTextParser.g:4758:2: ( (enumLiteral_0= BOOL ) )
            // InternalStructuredTextParser.g:4759:2: (enumLiteral_0= BOOL )
            {
            // InternalStructuredTextParser.g:4759:2: (enumLiteral_0= BOOL )
            // InternalStructuredTextParser.g:4760:3: enumLiteral_0= BOOL
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


    protected DFA26 dfa26 = new DFA26(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\5\uffff\3\4\2\uffff";
    static final String dfa_3s = "\1\4\2\57\2\uffff\3\10\1\116\1\uffff";
    static final String dfa_4s = "\1\163\2\157\2\uffff\3\140\1\120\1\uffff";
    static final String dfa_5s = "\3\uffff\1\1\1\2\4\uffff\1\3";
    static final String dfa_6s = "\12\uffff}>";
    static final String[] dfa_7s = {
            "\4\11\7\uffff\1\11\3\uffff\1\11\2\uffff\4\11\2\uffff\2\11\1\uffff\2\11\1\uffff\1\11\2\uffff\3\11\2\uffff\4\11\1\uffff\1\10\2\11\3\uffff\2\11\1\uffff\1\3\1\11\12\uffff\1\7\1\uffff\1\11\1\6\10\uffff\1\4\2\uffff\1\2\1\uffff\1\1\10\uffff\1\5\3\uffff\1\11\10\uffff\1\4\4\11\1\uffff\1\11\1\uffff\1\11",
            "\1\3\24\uffff\1\3\2\uffff\1\3\10\uffff\1\3\15\uffff\1\3\14\uffff\1\3\3\uffff\1\11",
            "\1\3\24\uffff\1\3\2\uffff\1\3\10\uffff\1\3\15\uffff\1\3\14\uffff\1\3\3\uffff\1\11",
            "",
            "",
            "\1\4\45\uffff\1\4\4\uffff\1\4\3\uffff\1\4\3\uffff\2\4\1\uffff\2\4\1\uffff\3\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
            "\1\4\45\uffff\1\4\4\uffff\1\4\3\uffff\1\4\3\uffff\2\4\1\uffff\2\4\1\uffff\3\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
            "\1\4\45\uffff\1\4\4\uffff\1\4\3\uffff\1\4\3\uffff\2\4\1\uffff\2\4\1\uffff\3\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
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

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        @Override
		public String getDescription() {
            return "1915:2: ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000004400L,0x0000080000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0010821208060800L,0x00000800420000B0L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0222BCE5B38880F0L,0x0000080000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x2000000000000002L,0x0000000080000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0263BCE5B3C880F0L,0x000AF004402800D0L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0010821208060802L,0x00000800420000B0L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0363BCE5B3C880F0L,0x000AF804402900D0L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0010831208270800L,0x00000800420000B0L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000010000210000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0263BDE5B3C890F0L,0x000AF004402800D0L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000000L,0x0000080040000090L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x000000000000000CL});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0010821208062800L,0x00000800420000B0L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0010821208060A00L,0x00000800420000B0L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0010821248060800L,0x00000800420000B0L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0008000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x8000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x4000000000000002L,0x0000000014000002L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000000280000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0080000000000002L,0x0000000000840000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000800000000000L,0x0000080040010090L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0363BCE5B3C880F0L,0x000AF804402B00D0L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0000000080000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000100100000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000F00000280000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x00223480B1000000L,0x0000F00000280000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000800000280000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000800000004000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x000A000000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000003C00003800L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});

}