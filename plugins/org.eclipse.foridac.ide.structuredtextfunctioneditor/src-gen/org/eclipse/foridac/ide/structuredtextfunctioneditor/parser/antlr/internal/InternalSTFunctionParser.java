package org.eclipse.foridac.ide.structuredtextfunctioneditor.parser.antlr.internal;

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
import org.eclipse.foridac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalSTFunctionParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "END_FUNCTION", "TIME_OF_DAY", "END_REPEAT", "VAR_OUTPUT", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "FUNCTION", "VAR_TEMP", "WSTRING", "END_FOR", "END_VAR", "STRING", "DWORD", "END_IF", "LDATE", "LREAL", "LTIME", "LWORD", "REPEAT", "RETURN", "UDINT", "ULINT", "USINT", "WCHAR", "ARRAY", "BOOL", "BYTE", "CHAR", "DATE", "DINT", "ELSIF", "LINT", "LTOD", "REAL", "SINT", "TIME", "UINT", "UNTIL", "WHILE", "WORD", "CASE", "ELSE", "EXIT", "INT", "LDT", "THEN", "TOD", "B", "D_1", "L", "W", "X", "AND", "DT", "FOR", "LD", "LT", "MOD", "NOT", "VAR", "XOR", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "D", "DO", "IF", "OF", "OR", "T", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "LeftSquareBracket", "RightSquareBracket", "RULE_BOOL_VALUES", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_TIME", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_WSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=26;
    public static final int LessThanSignGreaterThanSign=74;
    public static final int EqualsSignGreaterThanSign=75;
    public static final int VAR=68;
    public static final int END_IF=22;
    public static final int ULINT=30;
    public static final int END_CASE=14;
    public static final int LessThanSign=98;
    public static final int LeftParenthesis=88;
    public static final int BYTE=35;
    public static final int ELSE=50;
    public static final int RULE_TIME=107;
    public static final int IF=81;
    public static final int LINT=40;
    public static final int GreaterThanSign=100;
    public static final int WORD=48;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=109;
    public static final int TOD=55;
    public static final int DINT=38;
    public static final int FUNCTION=15;
    public static final int UDINT=29;
    public static final int CASE=49;
    public static final int GreaterThanSignEqualsSign=76;
    public static final int AT=77;
    public static final int PlusSign=91;
    public static final int RULE_INT=106;
    public static final int END_FOR=18;
    public static final int RULE_ML_COMMENT=112;
    public static final int THEN=54;
    public static final int XOR=69;
    public static final int LeftSquareBracket=101;
    public static final int EXIT=51;
    public static final int TIME_OF_DAY=7;
    public static final int B=56;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=27;
    public static final int D=79;
    public static final int CHAR=36;
    public static final int L=58;
    public static final int LTIME=25;
    public static final int Comma=92;
    public static final int HyphenMinus=93;
    public static final int T=84;
    public static final int W=59;
    public static final int BY=78;
    public static final int X=60;
    public static final int ELSIF=39;
    public static final int END_REPEAT=8;
    public static final int LessThanSignEqualsSign=73;
    public static final int Solidus=95;
    public static final int VAR_INPUT=11;
    public static final int FullStop=94;
    public static final int VAR_TEMP=16;
    public static final int CONSTANT=12;
    public static final int CONTINUE=13;
    public static final int Semicolon=97;
    public static final int LD=64;
    public static final int VAR_OUTPUT=9;
    public static final int STRING=20;
    public static final int RULE_HEX_DIGIT=104;
    public static final int TO=85;
    public static final int RULE_BOOL_VALUES=103;
    public static final int UINT=45;
    public static final int LTOD=41;
    public static final int ARRAY=33;
    public static final int LT=65;
    public static final int DO=80;
    public static final int WSTRING=17;
    public static final int DT=62;
    public static final int END_VAR=19;
    public static final int FullStopFullStop=71;
    public static final int Ampersand=87;
    public static final int RightSquareBracket=102;
    public static final int USINT=31;
    public static final int DWORD=21;
    public static final int FOR=63;
    public static final int RightParenthesis=89;
    public static final int ColonEqualsSign=72;
    public static final int RULE_WSTRING=111;
    public static final int END_FUNCTION=6;
    public static final int END_WHILE=10;
    public static final int DATE=37;
    public static final int NOT=67;
    public static final int LDATE=23;
    public static final int AND=61;
    public static final int NumberSign=86;
    public static final int REAL=42;
    public static final int AsteriskAsterisk=70;
    public static final int SINT=43;
    public static final int LREAL=24;
    public static final int WCHAR=32;
    public static final int RULE_STRING=110;
    public static final int INT=52;
    public static final int RULE_SL_COMMENT=113;
    public static final int RETURN=28;
    public static final int EqualsSign=99;
    public static final int OF=82;
    public static final int Colon=96;
    public static final int EOF=-1;
    public static final int LDT=53;
    public static final int Asterisk=90;
    public static final int MOD=66;
    public static final int OR=83;
    public static final int RULE_WS=114;
    public static final int RULE_EXT_INT=108;
    public static final int TIME=44;
    public static final int RULE_ANY_OTHER=115;
    public static final int UNTIL=46;
    public static final int BOOL=34;
    public static final int D_1=57;
    public static final int WHILE=47;
    public static final int RULE_NON_DECIMAL=105;

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
        	return "STFunction";
       	}

       	@Override
       	protected STFunctionGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleSTFunction"
    // InternalSTFunctionParser.g:58:1: entryRuleSTFunction returns [EObject current=null] : iv_ruleSTFunction= ruleSTFunction EOF ;
    public final EObject entryRuleSTFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFunction = null;


        try {
            // InternalSTFunctionParser.g:58:51: (iv_ruleSTFunction= ruleSTFunction EOF )
            // InternalSTFunctionParser.g:59:2: iv_ruleSTFunction= ruleSTFunction EOF
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
    // InternalSTFunctionParser.g:65:1: ruleSTFunction returns [EObject current=null] : ( (lv_functions_0_0= ruleFunctionDefinition ) )* ;
    public final EObject ruleSTFunction() throws RecognitionException {
        EObject current = null;

        EObject lv_functions_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:71:2: ( ( (lv_functions_0_0= ruleFunctionDefinition ) )* )
            // InternalSTFunctionParser.g:72:2: ( (lv_functions_0_0= ruleFunctionDefinition ) )*
            {
            // InternalSTFunctionParser.g:72:2: ( (lv_functions_0_0= ruleFunctionDefinition ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==FUNCTION) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSTFunctionParser.g:73:3: (lv_functions_0_0= ruleFunctionDefinition )
            	    {
            	    // InternalSTFunctionParser.g:73:3: (lv_functions_0_0= ruleFunctionDefinition )
            	    // InternalSTFunctionParser.g:74:4: lv_functions_0_0= ruleFunctionDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getSTFunctionAccess().getFunctionsFunctionDefinitionParserRuleCall_0());
            	      			
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_functions_0_0=ruleFunctionDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				if (current==null) {
            	      					current = createModelElementForParent(grammarAccess.getSTFunctionRule());
            	      				}
            	      				add(
            	      					current,
            	      					"functions",
            	      					lv_functions_0_0,
            	      					"org.eclipse.foridac.ide.structuredtextfunctioneditor.STFunction.FunctionDefinition");
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
    // $ANTLR end "ruleSTFunction"


    // $ANTLR start "entryRuleFunctionDefinition"
    // InternalSTFunctionParser.g:94:1: entryRuleFunctionDefinition returns [EObject current=null] : iv_ruleFunctionDefinition= ruleFunctionDefinition EOF ;
    public final EObject entryRuleFunctionDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionDefinition = null;


        try {
            // InternalSTFunctionParser.g:94:59: (iv_ruleFunctionDefinition= ruleFunctionDefinition EOF )
            // InternalSTFunctionParser.g:95:2: iv_ruleFunctionDefinition= ruleFunctionDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFunctionDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFunctionDefinition=ruleFunctionDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFunctionDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunctionDefinition"


    // $ANTLR start "ruleFunctionDefinition"
    // InternalSTFunctionParser.g:101:1: ruleFunctionDefinition returns [EObject current=null] : (otherlv_0= FUNCTION ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_varDeclarations_4_0= ruleVarDeclaration ) ) | ( (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock ) ) | ( (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock ) ) | ( (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock ) ) )* ( (lv_code_8_0= ruleSTStatement ) )* otherlv_9= END_FUNCTION ) ;
    public final EObject ruleFunctionDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_9=null;
        EObject lv_varDeclarations_4_0 = null;

        EObject lv_varTempDeclarations_5_0 = null;

        EObject lv_varInpuDeclarations_6_0 = null;

        EObject lv_varOutputDeclarations_7_0 = null;

        EObject lv_code_8_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:107:2: ( (otherlv_0= FUNCTION ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_varDeclarations_4_0= ruleVarDeclaration ) ) | ( (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock ) ) | ( (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock ) ) | ( (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock ) ) )* ( (lv_code_8_0= ruleSTStatement ) )* otherlv_9= END_FUNCTION ) )
            // InternalSTFunctionParser.g:108:2: (otherlv_0= FUNCTION ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_varDeclarations_4_0= ruleVarDeclaration ) ) | ( (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock ) ) | ( (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock ) ) | ( (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock ) ) )* ( (lv_code_8_0= ruleSTStatement ) )* otherlv_9= END_FUNCTION )
            {
            // InternalSTFunctionParser.g:108:2: (otherlv_0= FUNCTION ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_varDeclarations_4_0= ruleVarDeclaration ) ) | ( (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock ) ) | ( (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock ) ) | ( (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock ) ) )* ( (lv_code_8_0= ruleSTStatement ) )* otherlv_9= END_FUNCTION )
            // InternalSTFunctionParser.g:109:3: otherlv_0= FUNCTION ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= Colon ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_varDeclarations_4_0= ruleVarDeclaration ) ) | ( (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock ) ) | ( (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock ) ) | ( (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock ) ) )* ( (lv_code_8_0= ruleSTStatement ) )* otherlv_9= END_FUNCTION
            {
            otherlv_0=(Token)match(input,FUNCTION,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getFunctionDefinitionAccess().getFUNCTIONKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:113:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTFunctionParser.g:114:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:114:4: (lv_name_1_0= RULE_ID )
            // InternalSTFunctionParser.g:115:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getFunctionDefinitionAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getFunctionDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
              				
            }

            }


            }

            // InternalSTFunctionParser.g:131:3: (otherlv_2= Colon ( (otherlv_3= RULE_ID ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==Colon) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalSTFunctionParser.g:132:4: otherlv_2= Colon ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,Colon,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getFunctionDefinitionAccess().getColonKeyword_2_0());
                      			
                    }
                    // InternalSTFunctionParser.g:136:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSTFunctionParser.g:137:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSTFunctionParser.g:137:5: (otherlv_3= RULE_ID )
                    // InternalSTFunctionParser.g:138:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getFunctionDefinitionRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getFunctionDefinitionAccess().getReturnTypeDataTypeCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:150:3: ( ( (lv_varDeclarations_4_0= ruleVarDeclaration ) ) | ( (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock ) ) | ( (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock ) ) | ( (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock ) ) )*
            loop3:
            do {
                int alt3=5;
                switch ( input.LA(1) ) {
                case RULE_ID:
                    {
                    int LA3_2 = input.LA(2);

                    if ( (LA3_2==AT||LA3_2==Colon) ) {
                        alt3=1;
                    }


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

                }

                switch (alt3) {
            	case 1 :
            	    // InternalSTFunctionParser.g:151:4: ( (lv_varDeclarations_4_0= ruleVarDeclaration ) )
            	    {
            	    // InternalSTFunctionParser.g:151:4: ( (lv_varDeclarations_4_0= ruleVarDeclaration ) )
            	    // InternalSTFunctionParser.g:152:5: (lv_varDeclarations_4_0= ruleVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:152:5: (lv_varDeclarations_4_0= ruleVarDeclaration )
            	    // InternalSTFunctionParser.g:153:6: lv_varDeclarations_4_0= ruleVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getFunctionDefinitionAccess().getVarDeclarationsVarDeclarationParserRuleCall_3_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_varDeclarations_4_0=ruleVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getFunctionDefinitionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"varDeclarations",
            	      							lv_varDeclarations_4_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.VarDeclaration");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalSTFunctionParser.g:171:4: ( (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock ) )
            	    {
            	    // InternalSTFunctionParser.g:171:4: ( (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock ) )
            	    // InternalSTFunctionParser.g:172:5: (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock )
            	    {
            	    // InternalSTFunctionParser.g:172:5: (lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock )
            	    // InternalSTFunctionParser.g:173:6: lv_varTempDeclarations_5_0= ruleVarTempDeclarationBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getFunctionDefinitionAccess().getVarTempDeclarationsVarTempDeclarationBlockParserRuleCall_3_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_varTempDeclarations_5_0=ruleVarTempDeclarationBlock();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getFunctionDefinitionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"varTempDeclarations",
            	      							lv_varTempDeclarations_5_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.VarTempDeclarationBlock");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalSTFunctionParser.g:191:4: ( (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock ) )
            	    {
            	    // InternalSTFunctionParser.g:191:4: ( (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock ) )
            	    // InternalSTFunctionParser.g:192:5: (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock )
            	    {
            	    // InternalSTFunctionParser.g:192:5: (lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock )
            	    // InternalSTFunctionParser.g:193:6: lv_varInpuDeclarations_6_0= ruleVarInputDeclarationBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getFunctionDefinitionAccess().getVarInpuDeclarationsVarInputDeclarationBlockParserRuleCall_3_2_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_varInpuDeclarations_6_0=ruleVarInputDeclarationBlock();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getFunctionDefinitionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"varInpuDeclarations",
            	      							lv_varInpuDeclarations_6_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.VarInputDeclarationBlock");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalSTFunctionParser.g:211:4: ( (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock ) )
            	    {
            	    // InternalSTFunctionParser.g:211:4: ( (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock ) )
            	    // InternalSTFunctionParser.g:212:5: (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock )
            	    {
            	    // InternalSTFunctionParser.g:212:5: (lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock )
            	    // InternalSTFunctionParser.g:213:6: lv_varOutputDeclarations_7_0= ruleVarOutputDeclarationBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getFunctionDefinitionAccess().getVarOutputDeclarationsVarOutputDeclarationBlockParserRuleCall_3_3_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_varOutputDeclarations_7_0=ruleVarOutputDeclarationBlock();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getFunctionDefinitionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"varOutputDeclarations",
            	      							lv_varOutputDeclarations_7_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.VarOutputDeclarationBlock");
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

            // InternalSTFunctionParser.g:231:3: ( (lv_code_8_0= ruleSTStatement ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==REPEAT||LA4_0==WHILE||LA4_0==CASE||LA4_0==FOR||LA4_0==IF||LA4_0==Semicolon||LA4_0==RULE_ID) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSTFunctionParser.g:232:4: (lv_code_8_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:232:4: (lv_code_8_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:233:5: lv_code_8_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getFunctionDefinitionAccess().getCodeSTStatementParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_7);
            	    lv_code_8_0=ruleSTStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getFunctionDefinitionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"code",
            	      						lv_code_8_0,
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

            otherlv_9=(Token)match(input,END_FUNCTION,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_9, grammarAccess.getFunctionDefinitionAccess().getEND_FUNCTIONKeyword_5());
              		
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
    // $ANTLR end "ruleFunctionDefinition"


    // $ANTLR start "entryRuleVarTempDeclarationBlock"
    // InternalSTFunctionParser.g:258:1: entryRuleVarTempDeclarationBlock returns [EObject current=null] : iv_ruleVarTempDeclarationBlock= ruleVarTempDeclarationBlock EOF ;
    public final EObject entryRuleVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarTempDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:258:64: (iv_ruleVarTempDeclarationBlock= ruleVarTempDeclarationBlock EOF )
            // InternalSTFunctionParser.g:259:2: iv_ruleVarTempDeclarationBlock= ruleVarTempDeclarationBlock EOF
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
    // InternalSTFunctionParser.g:265:1: ruleVarTempDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:271:2: ( ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:272:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:272:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:273:3: () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:273:3: ()
            // InternalSTFunctionParser.g:274:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVarTempDeclarationBlockAccess().getVarDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_TEMP,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getVarTempDeclarationBlockAccess().getVAR_TEMPKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:284:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==CONSTANT) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalSTFunctionParser.g:285:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:285:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:286:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_9); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:298:3: ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==RULE_ID) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSTFunctionParser.g:299:4: (lv_varDeclarations_3_0= ruleVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:299:4: (lv_varDeclarations_3_0= ruleVarDeclaration )
            	    // InternalSTFunctionParser.g:300:5: lv_varDeclarations_3_0= ruleVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getVarTempDeclarationBlockAccess().getVarDeclarationsVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_9);
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
            	    break loop6;
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


    // $ANTLR start "entryRuleVarInputDeclarationBlock"
    // InternalSTFunctionParser.g:325:1: entryRuleVarInputDeclarationBlock returns [EObject current=null] : iv_ruleVarInputDeclarationBlock= ruleVarInputDeclarationBlock EOF ;
    public final EObject entryRuleVarInputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarInputDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:325:65: (iv_ruleVarInputDeclarationBlock= ruleVarInputDeclarationBlock EOF )
            // InternalSTFunctionParser.g:326:2: iv_ruleVarInputDeclarationBlock= ruleVarInputDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVarInputDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVarInputDeclarationBlock=ruleVarInputDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVarInputDeclarationBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVarInputDeclarationBlock"


    // $ANTLR start "ruleVarInputDeclarationBlock"
    // InternalSTFunctionParser.g:332:1: ruleVarInputDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleVarInputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:338:2: ( ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:339:2: ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:339:2: ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:340:3: () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:340:3: ()
            // InternalSTFunctionParser.g:341:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVarInputDeclarationBlockAccess().getVarDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_INPUT,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getVarInputDeclarationBlockAccess().getVAR_INPUTKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:351:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==CONSTANT) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSTFunctionParser.g:352:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:352:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:353:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getVarInputDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getVarInputDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:365:3: ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ID) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalSTFunctionParser.g:366:4: (lv_varDeclarations_3_0= ruleVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:366:4: (lv_varDeclarations_3_0= ruleVarDeclaration )
            	    // InternalSTFunctionParser.g:367:5: lv_varDeclarations_3_0= ruleVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getVarInputDeclarationBlockAccess().getVarDeclarationsVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_varDeclarations_3_0=ruleVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getVarInputDeclarationBlockRule());
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
            	    break loop8;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getVarInputDeclarationBlockAccess().getEND_VARKeyword_4());
              		
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
    // $ANTLR end "ruleVarInputDeclarationBlock"


    // $ANTLR start "entryRuleVarOutputDeclarationBlock"
    // InternalSTFunctionParser.g:392:1: entryRuleVarOutputDeclarationBlock returns [EObject current=null] : iv_ruleVarOutputDeclarationBlock= ruleVarOutputDeclarationBlock EOF ;
    public final EObject entryRuleVarOutputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarOutputDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:392:66: (iv_ruleVarOutputDeclarationBlock= ruleVarOutputDeclarationBlock EOF )
            // InternalSTFunctionParser.g:393:2: iv_ruleVarOutputDeclarationBlock= ruleVarOutputDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVarOutputDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVarOutputDeclarationBlock=ruleVarOutputDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVarOutputDeclarationBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVarOutputDeclarationBlock"


    // $ANTLR start "ruleVarOutputDeclarationBlock"
    // InternalSTFunctionParser.g:399:1: ruleVarOutputDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleVarOutputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:405:2: ( ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:406:2: ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:406:2: ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:407:3: () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:407:3: ()
            // InternalSTFunctionParser.g:408:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVarOutputDeclarationBlockAccess().getVarDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_OUTPUT,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getVarOutputDeclarationBlockAccess().getVAR_OUTPUTKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:418:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==CONSTANT) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSTFunctionParser.g:419:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:419:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:420:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getVarOutputDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getVarOutputDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSTFunctionParser.g:432:3: ( (lv_varDeclarations_3_0= ruleVarDeclaration ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSTFunctionParser.g:433:4: (lv_varDeclarations_3_0= ruleVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:433:4: (lv_varDeclarations_3_0= ruleVarDeclaration )
            	    // InternalSTFunctionParser.g:434:5: lv_varDeclarations_3_0= ruleVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getVarOutputDeclarationBlockAccess().getVarDeclarationsVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_varDeclarations_3_0=ruleVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getVarOutputDeclarationBlockRule());
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
            	    break loop10;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getVarOutputDeclarationBlockAccess().getEND_VARKeyword_4());
              		
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
    // $ANTLR end "ruleVarOutputDeclarationBlock"


    // $ANTLR start "entryRuleVarDeclaration"
    // InternalSTFunctionParser.g:459:1: entryRuleVarDeclaration returns [EObject current=null] : iv_ruleVarDeclaration= ruleVarDeclaration EOF ;
    public final EObject entryRuleVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarDeclaration = null;


        try {
            // InternalSTFunctionParser.g:459:55: (iv_ruleVarDeclaration= ruleVarDeclaration EOF )
            // InternalSTFunctionParser.g:460:2: iv_ruleVarDeclaration= ruleVarDeclaration EOF
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
    // InternalSTFunctionParser.g:466:1: ruleVarDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon ) ;
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
            // InternalSTFunctionParser.g:472:2: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon ) )
            // InternalSTFunctionParser.g:473:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon )
            {
            // InternalSTFunctionParser.g:473:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon )
            // InternalSTFunctionParser.g:474:3: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )? otherlv_3= Colon ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )? ( (otherlv_16= RULE_ID ) ) (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )? (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )? otherlv_22= Semicolon
            {
            // InternalSTFunctionParser.g:474:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalSTFunctionParser.g:475:4: (lv_name_0_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:475:4: (lv_name_0_0= RULE_ID )
            // InternalSTFunctionParser.g:476:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_10); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:492:3: (otherlv_1= AT ( (otherlv_2= RULE_ID ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==AT) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSTFunctionParser.g:493:4: otherlv_1= AT ( (otherlv_2= RULE_ID ) )
                    {
                    otherlv_1=(Token)match(input,AT,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getVarDeclarationAccess().getATKeyword_1_0());
                      			
                    }
                    // InternalSTFunctionParser.g:497:4: ( (otherlv_2= RULE_ID ) )
                    // InternalSTFunctionParser.g:498:5: (otherlv_2= RULE_ID )
                    {
                    // InternalSTFunctionParser.g:498:5: (otherlv_2= RULE_ID )
                    // InternalSTFunctionParser.g:499:6: otherlv_2= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_2=(Token)match(input,RULE_ID,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_2, grammarAccess.getVarDeclarationAccess().getLocatedAtVarDeclarationCrossReference_1_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,Colon,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getVarDeclarationAccess().getColonKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:515:3: ( ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==ARRAY) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalSTFunctionParser.g:516:4: ( (lv_array_4_0= ARRAY ) ) ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) ) otherlv_15= OF
                    {
                    // InternalSTFunctionParser.g:516:4: ( (lv_array_4_0= ARRAY ) )
                    // InternalSTFunctionParser.g:517:5: (lv_array_4_0= ARRAY )
                    {
                    // InternalSTFunctionParser.g:517:5: (lv_array_4_0= ARRAY )
                    // InternalSTFunctionParser.g:518:6: lv_array_4_0= ARRAY
                    {
                    lv_array_4_0=(Token)match(input,ARRAY,FOLLOW_13); if (state.failed) return current;
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

                    // InternalSTFunctionParser.g:530:4: ( (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) | (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket ) )
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==LeftSquareBracket) ) {
                        int LA14_1 = input.LA(2);

                        if ( (LA14_1==Asterisk) ) {
                            alt14=2;
                        }
                        else if ( ((LA14_1>=LDATE_AND_TIME && LA14_1<=DATE_AND_TIME)||LA14_1==TIME_OF_DAY||LA14_1==CONTINUE||LA14_1==WSTRING||(LA14_1>=STRING && LA14_1<=DWORD)||(LA14_1>=LDATE && LA14_1<=LWORD)||(LA14_1>=RETURN && LA14_1<=WCHAR)||(LA14_1>=BOOL && LA14_1<=DINT)||(LA14_1>=LINT && LA14_1<=UINT)||LA14_1==WORD||(LA14_1>=EXIT && LA14_1<=LDT)||LA14_1==TOD||LA14_1==DT||(LA14_1>=LD && LA14_1<=LT)||LA14_1==NOT||LA14_1==D||LA14_1==T||LA14_1==LeftParenthesis||LA14_1==PlusSign||LA14_1==HyphenMinus||LA14_1==RULE_BOOL_VALUES||(LA14_1>=RULE_NON_DECIMAL && LA14_1<=RULE_INT)||(LA14_1>=RULE_ID && LA14_1<=RULE_STRING)) ) {
                            alt14=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 14, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 0, input);

                        throw nvae;
                    }
                    switch (alt14) {
                        case 1 :
                            // InternalSTFunctionParser.g:531:5: (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
                            {
                            // InternalSTFunctionParser.g:531:5: (otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
                            // InternalSTFunctionParser.g:532:6: otherlv_5= LeftSquareBracket ( (lv_ranges_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
                            {
                            otherlv_5=(Token)match(input,LeftSquareBracket,FOLLOW_14); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_5, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_3_1_0_0());
                              					
                            }
                            // InternalSTFunctionParser.g:536:6: ( (lv_ranges_6_0= ruleSTExpression ) )
                            // InternalSTFunctionParser.g:537:7: (lv_ranges_6_0= ruleSTExpression )
                            {
                            // InternalSTFunctionParser.g:537:7: (lv_ranges_6_0= ruleSTExpression )
                            // InternalSTFunctionParser.g:538:8: lv_ranges_6_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getVarDeclarationAccess().getRangesSTExpressionParserRuleCall_3_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_15);
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

                            // InternalSTFunctionParser.g:555:6: (otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) ) )*
                            loop12:
                            do {
                                int alt12=2;
                                int LA12_0 = input.LA(1);

                                if ( (LA12_0==Comma) ) {
                                    alt12=1;
                                }


                                switch (alt12) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:556:7: otherlv_7= Comma ( (lv_ranges_8_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_7=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_7, grammarAccess.getVarDeclarationAccess().getCommaKeyword_3_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTFunctionParser.g:560:7: ( (lv_ranges_8_0= ruleSTExpression ) )
                            	    // InternalSTFunctionParser.g:561:8: (lv_ranges_8_0= ruleSTExpression )
                            	    {
                            	    // InternalSTFunctionParser.g:561:8: (lv_ranges_8_0= ruleSTExpression )
                            	    // InternalSTFunctionParser.g:562:9: lv_ranges_8_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getVarDeclarationAccess().getRangesSTExpressionParserRuleCall_3_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_15);
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
                            	    break loop12;
                                }
                            } while (true);

                            otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_9, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_3_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTFunctionParser.g:586:5: (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket )
                            {
                            // InternalSTFunctionParser.g:586:5: (otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket )
                            // InternalSTFunctionParser.g:587:6: otherlv_10= LeftSquareBracket ( (lv_count_11_0= Asterisk ) ) (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )* otherlv_14= RightSquareBracket
                            {
                            otherlv_10=(Token)match(input,LeftSquareBracket,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_3_1_1_0());
                              					
                            }
                            // InternalSTFunctionParser.g:591:6: ( (lv_count_11_0= Asterisk ) )
                            // InternalSTFunctionParser.g:592:7: (lv_count_11_0= Asterisk )
                            {
                            // InternalSTFunctionParser.g:592:7: (lv_count_11_0= Asterisk )
                            // InternalSTFunctionParser.g:593:8: lv_count_11_0= Asterisk
                            {
                            lv_count_11_0=(Token)match(input,Asterisk,FOLLOW_15); if (state.failed) return current;
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

                            // InternalSTFunctionParser.g:605:6: (otherlv_12= Comma ( (lv_count_13_0= Asterisk ) ) )*
                            loop13:
                            do {
                                int alt13=2;
                                int LA13_0 = input.LA(1);

                                if ( (LA13_0==Comma) ) {
                                    alt13=1;
                                }


                                switch (alt13) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:606:7: otherlv_12= Comma ( (lv_count_13_0= Asterisk ) )
                            	    {
                            	    otherlv_12=(Token)match(input,Comma,FOLLOW_17); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_12, grammarAccess.getVarDeclarationAccess().getCommaKeyword_3_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTFunctionParser.g:610:7: ( (lv_count_13_0= Asterisk ) )
                            	    // InternalSTFunctionParser.g:611:8: (lv_count_13_0= Asterisk )
                            	    {
                            	    // InternalSTFunctionParser.g:611:8: (lv_count_13_0= Asterisk )
                            	    // InternalSTFunctionParser.g:612:9: lv_count_13_0= Asterisk
                            	    {
                            	    lv_count_13_0=(Token)match(input,Asterisk,FOLLOW_15); if (state.failed) return current;
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
                            	    break loop13;
                                }
                            } while (true);

                            otherlv_14=(Token)match(input,RightSquareBracket,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_14, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_3_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_15=(Token)match(input,OF,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_15, grammarAccess.getVarDeclarationAccess().getOFKeyword_3_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:636:3: ( (otherlv_16= RULE_ID ) )
            // InternalSTFunctionParser.g:637:4: (otherlv_16= RULE_ID )
            {
            // InternalSTFunctionParser.g:637:4: (otherlv_16= RULE_ID )
            // InternalSTFunctionParser.g:638:5: otherlv_16= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVarDeclarationRule());
              					}
              				
            }
            otherlv_16=(Token)match(input,RULE_ID,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_16, grammarAccess.getVarDeclarationAccess().getTypeLibraryElementCrossReference_4_0());
              				
            }

            }


            }

            // InternalSTFunctionParser.g:649:3: (otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==LeftSquareBracket) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSTFunctionParser.g:650:4: otherlv_17= LeftSquareBracket ( (lv_maxLength_18_0= ruleSTExpression ) ) otherlv_19= RightSquareBracket
                    {
                    otherlv_17=(Token)match(input,LeftSquareBracket,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_17, grammarAccess.getVarDeclarationAccess().getLeftSquareBracketKeyword_5_0());
                      			
                    }
                    // InternalSTFunctionParser.g:654:4: ( (lv_maxLength_18_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:655:5: (lv_maxLength_18_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:655:5: (lv_maxLength_18_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:656:6: lv_maxLength_18_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_19);
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

                    otherlv_19=(Token)match(input,RightSquareBracket,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getVarDeclarationAccess().getRightSquareBracketKeyword_5_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:678:3: (otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==ColonEqualsSign) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalSTFunctionParser.g:679:4: otherlv_20= ColonEqualsSign ( (lv_defaultValue_21_0= ruleInitializerExpression ) )
                    {
                    otherlv_20=(Token)match(input,ColonEqualsSign,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getVarDeclarationAccess().getColonEqualsSignKeyword_6_0());
                      			
                    }
                    // InternalSTFunctionParser.g:683:4: ( (lv_defaultValue_21_0= ruleInitializerExpression ) )
                    // InternalSTFunctionParser.g:684:5: (lv_defaultValue_21_0= ruleInitializerExpression )
                    {
                    // InternalSTFunctionParser.g:684:5: (lv_defaultValue_21_0= ruleInitializerExpression )
                    // InternalSTFunctionParser.g:685:6: lv_defaultValue_21_0= ruleInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVarDeclarationAccess().getDefaultValueInitializerExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_22);
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
    // InternalSTFunctionParser.g:711:1: entryRuleInitializerExpression returns [EObject current=null] : iv_ruleInitializerExpression= ruleInitializerExpression EOF ;
    public final EObject entryRuleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:711:62: (iv_ruleInitializerExpression= ruleInitializerExpression EOF )
            // InternalSTFunctionParser.g:712:2: iv_ruleInitializerExpression= ruleInitializerExpression EOF
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
    // InternalSTFunctionParser.g:718:1: ruleInitializerExpression returns [EObject current=null] : (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) ;
    public final EObject ruleInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STExpression_0 = null;

        EObject this_ArrayInitializerExpression_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:724:2: ( (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression ) )
            // InternalSTFunctionParser.g:725:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            {
            // InternalSTFunctionParser.g:725:2: (this_STExpression_0= ruleSTExpression | this_ArrayInitializerExpression_1= ruleArrayInitializerExpression )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=LDATE_AND_TIME && LA18_0<=DATE_AND_TIME)||LA18_0==TIME_OF_DAY||LA18_0==CONTINUE||LA18_0==WSTRING||(LA18_0>=STRING && LA18_0<=DWORD)||(LA18_0>=LDATE && LA18_0<=LWORD)||(LA18_0>=RETURN && LA18_0<=WCHAR)||(LA18_0>=BOOL && LA18_0<=DINT)||(LA18_0>=LINT && LA18_0<=UINT)||LA18_0==WORD||(LA18_0>=EXIT && LA18_0<=LDT)||LA18_0==TOD||LA18_0==DT||(LA18_0>=LD && LA18_0<=LT)||LA18_0==NOT||LA18_0==D||LA18_0==T||LA18_0==LeftParenthesis||LA18_0==PlusSign||LA18_0==HyphenMinus||LA18_0==RULE_BOOL_VALUES||(LA18_0>=RULE_NON_DECIMAL && LA18_0<=RULE_INT)||(LA18_0>=RULE_ID && LA18_0<=RULE_STRING)) ) {
                alt18=1;
            }
            else if ( (LA18_0==LeftSquareBracket) ) {
                alt18=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalSTFunctionParser.g:726:3: this_STExpression_0= ruleSTExpression
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
                    // InternalSTFunctionParser.g:735:3: this_ArrayInitializerExpression_1= ruleArrayInitializerExpression
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
    // InternalSTFunctionParser.g:747:1: entryRuleArrayInitializerExpression returns [EObject current=null] : iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF ;
    public final EObject entryRuleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:747:67: (iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF )
            // InternalSTFunctionParser.g:748:2: iv_ruleArrayInitializerExpression= ruleArrayInitializerExpression EOF
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
    // InternalSTFunctionParser.g:754:1: ruleArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:760:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTFunctionParser.g:761:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTFunctionParser.g:761:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTFunctionParser.g:762:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:766:3: ( (lv_values_1_0= ruleArrayInitElement ) )
            // InternalSTFunctionParser.g:767:4: (lv_values_1_0= ruleArrayInitElement )
            {
            // InternalSTFunctionParser.g:767:4: (lv_values_1_0= ruleArrayInitElement )
            // InternalSTFunctionParser.g:768:5: lv_values_1_0= ruleArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrayInitializerExpressionAccess().getValuesArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
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

            // InternalSTFunctionParser.g:785:3: (otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==Comma) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSTFunctionParser.g:786:4: otherlv_2= Comma ( (lv_values_3_0= ruleArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:790:4: ( (lv_values_3_0= ruleArrayInitElement ) )
            	    // InternalSTFunctionParser.g:791:5: (lv_values_3_0= ruleArrayInitElement )
            	    {
            	    // InternalSTFunctionParser.g:791:5: (lv_values_3_0= ruleArrayInitElement )
            	    // InternalSTFunctionParser.g:792:6: lv_values_3_0= ruleArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getArrayInitializerExpressionAccess().getValuesArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_15);
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
            	    break loop19;
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
    // InternalSTFunctionParser.g:818:1: entryRuleArrayInitElement returns [EObject current=null] : iv_ruleArrayInitElement= ruleArrayInitElement EOF ;
    public final EObject entryRuleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayInitElement = null;


        try {
            // InternalSTFunctionParser.g:818:57: (iv_ruleArrayInitElement= ruleArrayInitElement EOF )
            // InternalSTFunctionParser.g:819:2: iv_ruleArrayInitElement= ruleArrayInitElement EOF
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
    // InternalSTFunctionParser.g:825:1: ruleArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) ;
    public final EObject ruleArrayInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_indexOrInitExpression_0_0 = null;

        EObject lv_initExpression_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:831:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? ) )
            // InternalSTFunctionParser.g:832:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            {
            // InternalSTFunctionParser.g:832:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )? )
            // InternalSTFunctionParser.g:833:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            {
            // InternalSTFunctionParser.g:833:3: ( (lv_indexOrInitExpression_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:834:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:834:4: (lv_indexOrInitExpression_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:835:5: lv_indexOrInitExpression_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrayInitElementAccess().getIndexOrInitExpressionSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_23);
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

            // InternalSTFunctionParser.g:852:3: (otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==LeftParenthesis) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSTFunctionParser.g:853:4: otherlv_1= LeftParenthesis ( (lv_initExpression_2_0= ruleSTExpression ) ) otherlv_3= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalSTFunctionParser.g:857:4: ( (lv_initExpression_2_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:858:5: (lv_initExpression_2_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:858:5: (lv_initExpression_2_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:859:6: lv_initExpression_2_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getArrayInitElementAccess().getInitExpressionSTExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_24);
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
    // InternalSTFunctionParser.g:885:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTFunctionParser.g:885:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTFunctionParser.g:886:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalSTFunctionParser.g:892:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon ) | ( () otherlv_5= Semicolon ) ) ;
    public final EObject ruleSTStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_STBranchStatements_0 = null;

        EObject this_STLoopStatements_1 = null;

        EObject this_STAssignmentStatement_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:898:2: ( ( ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon ) | ( () otherlv_5= Semicolon ) ) )
            // InternalSTFunctionParser.g:899:2: ( ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon ) | ( () otherlv_5= Semicolon ) )
            {
            // InternalSTFunctionParser.g:899:2: ( ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon ) | ( () otherlv_5= Semicolon ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==REPEAT||LA22_0==WHILE||LA22_0==CASE||LA22_0==FOR||LA22_0==IF||LA22_0==RULE_ID) ) {
                alt22=1;
            }
            else if ( (LA22_0==Semicolon) ) {
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
                    // InternalSTFunctionParser.g:900:3: ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon )
                    {
                    // InternalSTFunctionParser.g:900:3: ( (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon )
                    // InternalSTFunctionParser.g:901:4: (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement ) otherlv_3= Semicolon
                    {
                    // InternalSTFunctionParser.g:901:4: (this_STBranchStatements_0= ruleSTBranchStatements | this_STLoopStatements_1= ruleSTLoopStatements | this_STAssignmentStatement_2= ruleSTAssignmentStatement )
                    int alt21=3;
                    switch ( input.LA(1) ) {
                    case CASE:
                    case IF:
                        {
                        alt21=1;
                        }
                        break;
                    case REPEAT:
                    case WHILE:
                    case FOR:
                        {
                        alt21=2;
                        }
                        break;
                    case RULE_ID:
                        {
                        alt21=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 21, 0, input);

                        throw nvae;
                    }

                    switch (alt21) {
                        case 1 :
                            // InternalSTFunctionParser.g:902:5: this_STBranchStatements_0= ruleSTBranchStatements
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTBranchStatementsParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_22);
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
                            // InternalSTFunctionParser.g:911:5: this_STLoopStatements_1= ruleSTLoopStatements
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTLoopStatementsParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_22);
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
                            // InternalSTFunctionParser.g:920:5: this_STAssignmentStatement_2= ruleSTAssignmentStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentStatementParserRuleCall_0_0_2());
                              				
                            }
                            pushFollow(FOLLOW_22);
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
                    // InternalSTFunctionParser.g:935:3: ( () otherlv_5= Semicolon )
                    {
                    // InternalSTFunctionParser.g:935:3: ( () otherlv_5= Semicolon )
                    // InternalSTFunctionParser.g:936:4: () otherlv_5= Semicolon
                    {
                    // InternalSTFunctionParser.g:936:4: ()
                    // InternalSTFunctionParser.g:937:5: 
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

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
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
    // InternalSTFunctionParser.g:952:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTFunctionParser.g:952:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTFunctionParser.g:953:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTFunctionParser.g:959:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_op_1_1=null;
        Token lv_op_1_2=null;
        EObject lv_rhs_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:965:2: ( ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) ) ) )
            // InternalSTFunctionParser.g:966:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) ) )
            {
            // InternalSTFunctionParser.g:966:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:967:3: ( (otherlv_0= RULE_ID ) ) ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) ) ( (lv_rhs_2_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:967:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTFunctionParser.g:968:4: (otherlv_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:968:4: (otherlv_0= RULE_ID )
            // InternalSTFunctionParser.g:969:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTAssignmentStatementRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTAssignmentStatementAccess().getLhsVarDeclarationCrossReference_0_0());
              				
            }

            }


            }

            // InternalSTFunctionParser.g:980:3: ( ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) ) )
            // InternalSTFunctionParser.g:981:4: ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) )
            {
            // InternalSTFunctionParser.g:981:4: ( (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign ) )
            // InternalSTFunctionParser.g:982:5: (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign )
            {
            // InternalSTFunctionParser.g:982:5: (lv_op_1_1= ColonEqualsSign | lv_op_1_2= EqualsSignGreaterThanSign )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ColonEqualsSign) ) {
                alt23=1;
            }
            else if ( (LA23_0==EqualsSignGreaterThanSign) ) {
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
                    // InternalSTFunctionParser.g:983:6: lv_op_1_1= ColonEqualsSign
                    {
                    lv_op_1_1=(Token)match(input,ColonEqualsSign,FOLLOW_14); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:994:6: lv_op_1_2= EqualsSignGreaterThanSign
                    {
                    lv_op_1_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_14); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:1007:3: ( (lv_rhs_2_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1008:4: (lv_rhs_2_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1008:4: (lv_rhs_2_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1009:5: lv_rhs_2_0= ruleSTExpression
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
    // InternalSTFunctionParser.g:1030:1: entryRuleSTBranchStatements returns [EObject current=null] : iv_ruleSTBranchStatements= ruleSTBranchStatements EOF ;
    public final EObject entryRuleSTBranchStatements() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTBranchStatements = null;


        try {
            // InternalSTFunctionParser.g:1030:59: (iv_ruleSTBranchStatements= ruleSTBranchStatements EOF )
            // InternalSTFunctionParser.g:1031:2: iv_ruleSTBranchStatements= ruleSTBranchStatements EOF
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
    // InternalSTFunctionParser.g:1037:1: ruleSTBranchStatements returns [EObject current=null] : (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement ) ;
    public final EObject ruleSTBranchStatements() throws RecognitionException {
        EObject current = null;

        EObject this_STIfStatment_0 = null;

        EObject this_STCaseStatement_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1043:2: ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement ) )
            // InternalSTFunctionParser.g:1044:2: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement )
            {
            // InternalSTFunctionParser.g:1044:2: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==IF) ) {
                alt24=1;
            }
            else if ( (LA24_0==CASE) ) {
                alt24=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // InternalSTFunctionParser.g:1045:3: this_STIfStatment_0= ruleSTIfStatment
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
                    // InternalSTFunctionParser.g:1054:3: this_STCaseStatement_1= ruleSTCaseStatement
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
    // InternalSTFunctionParser.g:1066:1: entryRuleSTIfStatment returns [EObject current=null] : iv_ruleSTIfStatment= ruleSTIfStatment EOF ;
    public final EObject entryRuleSTIfStatment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatment = null;


        try {
            // InternalSTFunctionParser.g:1066:53: (iv_ruleSTIfStatment= ruleSTIfStatment EOF )
            // InternalSTFunctionParser.g:1067:2: iv_ruleSTIfStatment= ruleSTIfStatment EOF
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
    // InternalSTFunctionParser.g:1073:1: ruleSTIfStatment returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTFunctionParser.g:1079:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTFunctionParser.g:1080:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTFunctionParser.g:1080:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTFunctionParser.g:1081:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatmentAccess().getIFKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1085:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1086:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1086:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1087:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatmentAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_26);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_27); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatmentAccess().getTHENKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1108:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==REPEAT||LA25_0==WHILE||LA25_0==CASE||LA25_0==FOR||LA25_0==IF||LA25_0==Semicolon||LA25_0==RULE_ID) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1109:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1109:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1110:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatmentAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_27);
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
            	    break loop25;
                }
            } while (true);

            // InternalSTFunctionParser.g:1127:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==ELSIF) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1128:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTFunctionParser.g:1128:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTFunctionParser.g:1129:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatmentAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_28);
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
            	    break loop26;
                }
            } while (true);

            // InternalSTFunctionParser.g:1146:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==ELSE) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalSTFunctionParser.g:1147:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTFunctionParser.g:1147:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTFunctionParser.g:1148:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatmentAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_29);
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
    // InternalSTFunctionParser.g:1173:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTFunctionParser.g:1173:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTFunctionParser.g:1174:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTFunctionParser.g:1180:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1186:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1187:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1187:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1188:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1192:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1193:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1193:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1194:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_26);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1215:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==REPEAT||LA28_0==WHILE||LA28_0==CASE||LA28_0==FOR||LA28_0==IF||LA28_0==Semicolon||LA28_0==RULE_ID) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1216:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1216:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1217:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_30);
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalSTFunctionParser.g:1238:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTFunctionParser.g:1238:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTFunctionParser.g:1239:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTFunctionParser.g:1245:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTFunctionParser.g:1251:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTFunctionParser.g:1252:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTFunctionParser.g:1252:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTFunctionParser.g:1253:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1257:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1258:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1258:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1259:5: lv_selector_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_16);
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

            otherlv_2=(Token)match(input,OF,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCaseStatementAccess().getOFKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1280:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt29=0;
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>=LDATE_AND_TIME && LA29_0<=DATE_AND_TIME)||LA29_0==TIME_OF_DAY||LA29_0==CONTINUE||LA29_0==WSTRING||(LA29_0>=STRING && LA29_0<=DWORD)||(LA29_0>=LDATE && LA29_0<=LWORD)||(LA29_0>=RETURN && LA29_0<=WCHAR)||(LA29_0>=BOOL && LA29_0<=DINT)||(LA29_0>=LINT && LA29_0<=UINT)||LA29_0==WORD||(LA29_0>=EXIT && LA29_0<=LDT)||LA29_0==TOD||LA29_0==DT||(LA29_0>=LD && LA29_0<=LT)||LA29_0==NOT||LA29_0==D||LA29_0==T||LA29_0==LeftParenthesis||LA29_0==PlusSign||LA29_0==HyphenMinus||LA29_0==RULE_BOOL_VALUES||(LA29_0>=RULE_NON_DECIMAL && LA29_0<=RULE_INT)||(LA29_0>=RULE_ID && LA29_0<=RULE_STRING)) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1281:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTFunctionParser.g:1281:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTFunctionParser.g:1282:5: lv_cases_3_0= ruleSTCaseCases
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
            	    if ( cnt29 >= 1 ) break loop29;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
            } while (true);

            // InternalSTFunctionParser.g:1299:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ELSE) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalSTFunctionParser.g:1300:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTFunctionParser.g:1300:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTFunctionParser.g:1301:5: lv_else_4_0= ruleSTElsePart
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
    // InternalSTFunctionParser.g:1326:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTFunctionParser.g:1326:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTFunctionParser.g:1327:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTFunctionParser.g:1333:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1339:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1340:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1340:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1341:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTFunctionParser.g:1341:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1342:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1342:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1343:5: lv_conditions_0_0= ruleSTExpression
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

            // InternalSTFunctionParser.g:1360:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==Comma) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1361:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:1365:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTFunctionParser.g:1366:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTFunctionParser.g:1366:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTFunctionParser.g:1367:6: lv_conditions_2_0= ruleSTExpression
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
            	    break loop31;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1389:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop32:
            do {
                int alt32=2;
                alt32 = dfa32.predict(input);
                switch (alt32) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1390:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1394:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1395:5: lv_statements_4_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_30);
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTFunctionParser.g:1416:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTFunctionParser.g:1416:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTFunctionParser.g:1417:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTFunctionParser.g:1423:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1429:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1430:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1430:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1431:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTFunctionParser.g:1431:3: ()
            // InternalSTFunctionParser.g:1432:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,ELSE,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1442:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==REPEAT||LA33_0==WHILE||LA33_0==CASE||LA33_0==FOR||LA33_0==IF||LA33_0==Semicolon||LA33_0==RULE_ID) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1443:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1443:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1444:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_30);
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTLoopStatements"
    // InternalSTFunctionParser.g:1465:1: entryRuleSTLoopStatements returns [EObject current=null] : iv_ruleSTLoopStatements= ruleSTLoopStatements EOF ;
    public final EObject entryRuleSTLoopStatements() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLoopStatements = null;


        try {
            // InternalSTFunctionParser.g:1465:57: (iv_ruleSTLoopStatements= ruleSTLoopStatements EOF )
            // InternalSTFunctionParser.g:1466:2: iv_ruleSTLoopStatements= ruleSTLoopStatements EOF
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
    // InternalSTFunctionParser.g:1472:1: ruleSTLoopStatements returns [EObject current=null] : (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement ) ;
    public final EObject ruleSTLoopStatements() throws RecognitionException {
        EObject current = null;

        EObject this_STForStatement_0 = null;

        EObject this_STWhileStatement_1 = null;

        EObject this_STRepeatStatement_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1478:2: ( (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement ) )
            // InternalSTFunctionParser.g:1479:2: (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement )
            {
            // InternalSTFunctionParser.g:1479:2: (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement )
            int alt34=3;
            switch ( input.LA(1) ) {
            case FOR:
                {
                alt34=1;
                }
                break;
            case WHILE:
                {
                alt34=2;
                }
                break;
            case REPEAT:
                {
                alt34=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }

            switch (alt34) {
                case 1 :
                    // InternalSTFunctionParser.g:1480:3: this_STForStatement_0= ruleSTForStatement
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
                    // InternalSTFunctionParser.g:1489:3: this_STWhileStatement_1= ruleSTWhileStatement
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
                    // InternalSTFunctionParser.g:1498:3: this_STRepeatStatement_2= ruleSTRepeatStatement
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
    // InternalSTFunctionParser.g:1510:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTFunctionParser.g:1510:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTFunctionParser.g:1511:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTFunctionParser.g:1517:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) ;
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
            // InternalSTFunctionParser.g:1523:2: ( (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) )
            // InternalSTFunctionParser.g:1524:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            {
            // InternalSTFunctionParser.g:1524:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            // InternalSTFunctionParser.g:1525:3: otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1529:3: ( (lv_for_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1530:4: (lv_for_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1530:4: (lv_for_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1531:5: lv_for_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getForSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_34);
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

            otherlv_2=(Token)match(input,TO,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getTOKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1552:3: ( (lv_to_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1553:4: (lv_to_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1553:4: (lv_to_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1554:5: lv_to_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_35);
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

            // InternalSTFunctionParser.g:1571:3: (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==BY) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalSTFunctionParser.g:1572:4: otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) )
                    {
                    otherlv_4=(Token)match(input,BY,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getBYKeyword_4_0());
                      			
                    }
                    // InternalSTFunctionParser.g:1576:4: ( (lv_by_5_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:1577:5: (lv_by_5_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:1577:5: (lv_by_5_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:1578:6: lv_by_5_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

            otherlv_6=(Token)match(input,DO,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getDOKeyword_5());
              		
            }
            // InternalSTFunctionParser.g:1600:3: ( (lv_statements_7_0= ruleSTStatement ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==REPEAT||LA36_0==WHILE||LA36_0==CASE||LA36_0==FOR||LA36_0==IF||LA36_0==Semicolon||LA36_0==RULE_ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1601:4: (lv_statements_7_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1601:4: (lv_statements_7_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1602:5: lv_statements_7_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_6_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_37);
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
            	    break loop36;
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
    // InternalSTFunctionParser.g:1627:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTFunctionParser.g:1627:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTFunctionParser.g:1628:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTFunctionParser.g:1634:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1640:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTFunctionParser.g:1641:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTFunctionParser.g:1641:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTFunctionParser.g:1642:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1646:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1647:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1647:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1648:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTFunctionParser.g:1669:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==REPEAT||LA37_0==WHILE||LA37_0==CASE||LA37_0==FOR||LA37_0==IF||LA37_0==Semicolon||LA37_0==RULE_ID) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1670:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1670:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1671:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop37;
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
    // InternalSTFunctionParser.g:1696:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTFunctionParser.g:1696:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTFunctionParser.g:1697:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTFunctionParser.g:1703:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1709:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTFunctionParser.g:1710:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTFunctionParser.g:1710:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTFunctionParser.g:1711:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1715:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==REPEAT||LA38_0==WHILE||LA38_0==CASE||LA38_0==FOR||LA38_0==IF||LA38_0==Semicolon||LA38_0==RULE_ID) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1716:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1716:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1717:5: lv_statements_1_0= ruleSTStatement
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
            	    break loop38;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1738:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1739:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1739:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1740:5: lv_condition_3_0= ruleSTExpression
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
    // InternalSTFunctionParser.g:1765:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTFunctionParser.g:1765:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTFunctionParser.g:1766:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTFunctionParser.g:1772:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1778:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTFunctionParser.g:1779:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTFunctionParser.g:1790:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTFunctionParser.g:1790:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTFunctionParser.g:1791:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTFunctionParser.g:1797:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_STOrExpression_0 = null;

        EObject lv_upperBound_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1803:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTFunctionParser.g:1804:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:1804:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTFunctionParser.g:1805:3: this_STOrExpression_0= ruleSTOrExpression ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )*
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
            // InternalSTFunctionParser.g:1813:3: ( ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==FullStopFullStop) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1814:4: ( () otherlv_2= FullStopFullStop ) ( (lv_upperBound_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:1814:4: ( () otherlv_2= FullStopFullStop )
            	    // InternalSTFunctionParser.g:1815:5: () otherlv_2= FullStopFullStop
            	    {
            	    // InternalSTFunctionParser.g:1815:5: ()
            	    // InternalSTFunctionParser.g:1816:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTSubrangeExpressionLowerBoundAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStopFullStop,FOLLOW_14); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTSubrangeExpressionAccess().getFullStopFullStopKeyword_1_0_1());
            	      				
            	    }

            	    }

            	    // InternalSTFunctionParser.g:1827:4: ( (lv_upperBound_3_0= ruleSTOrExpression ) )
            	    // InternalSTFunctionParser.g:1828:5: (lv_upperBound_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTFunctionParser.g:1828:5: (lv_upperBound_3_0= ruleSTOrExpression )
            	    // InternalSTFunctionParser.g:1829:6: lv_upperBound_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getUpperBoundSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_41);
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalSTFunctionParser.g:1851:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTFunctionParser.g:1851:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTFunctionParser.g:1852:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTFunctionParser.g:1858:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_STXorExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1864:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTFunctionParser.g:1865:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:1865:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTFunctionParser.g:1866:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
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
            // InternalSTFunctionParser.g:1874:3: ( ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==OR) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1875:4: ( () ( (lv_op_2_0= OR ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:1875:4: ( () ( (lv_op_2_0= OR ) ) )
            	    // InternalSTFunctionParser.g:1876:5: () ( (lv_op_2_0= OR ) )
            	    {
            	    // InternalSTFunctionParser.g:1876:5: ()
            	    // InternalSTFunctionParser.g:1877:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTOrExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:1883:5: ( (lv_op_2_0= OR ) )
            	    // InternalSTFunctionParser.g:1884:6: (lv_op_2_0= OR )
            	    {
            	    // InternalSTFunctionParser.g:1884:6: (lv_op_2_0= OR )
            	    // InternalSTFunctionParser.g:1885:7: lv_op_2_0= OR
            	    {
            	    lv_op_2_0=(Token)match(input,OR,FOLLOW_14); if (state.failed) return current;
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

            	    // InternalSTFunctionParser.g:1898:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTFunctionParser.g:1899:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTFunctionParser.g:1899:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTFunctionParser.g:1900:6: lv_right_3_0= ruleSTXorExpression
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalSTFunctionParser.g:1922:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTFunctionParser.g:1922:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTFunctionParser.g:1923:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTFunctionParser.g:1929:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_STAndExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1935:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTFunctionParser.g:1936:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:1936:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTFunctionParser.g:1937:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
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
            // InternalSTFunctionParser.g:1945:3: ( ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==XOR) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1946:4: ( () ( (lv_op_2_0= XOR ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:1946:4: ( () ( (lv_op_2_0= XOR ) ) )
            	    // InternalSTFunctionParser.g:1947:5: () ( (lv_op_2_0= XOR ) )
            	    {
            	    // InternalSTFunctionParser.g:1947:5: ()
            	    // InternalSTFunctionParser.g:1948:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTXorExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:1954:5: ( (lv_op_2_0= XOR ) )
            	    // InternalSTFunctionParser.g:1955:6: (lv_op_2_0= XOR )
            	    {
            	    // InternalSTFunctionParser.g:1955:6: (lv_op_2_0= XOR )
            	    // InternalSTFunctionParser.g:1956:7: lv_op_2_0= XOR
            	    {
            	    lv_op_2_0=(Token)match(input,XOR,FOLLOW_14); if (state.failed) return current;
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

            	    // InternalSTFunctionParser.g:1969:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTFunctionParser.g:1970:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTFunctionParser.g:1970:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTFunctionParser.g:1971:6: lv_right_3_0= ruleSTAndExpression
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalSTFunctionParser.g:1993:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTFunctionParser.g:1993:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTFunctionParser.g:1994:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTFunctionParser.g:2000:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_STEqualityExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2006:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2007:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2007:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTFunctionParser.g:2008:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
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
            // InternalSTFunctionParser.g:2016:3: ( ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==AND||LA43_0==Ampersand) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2017:4: ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2017:4: ( () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) ) )
            	    // InternalSTFunctionParser.g:2018:5: () ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) )
            	    {
            	    // InternalSTFunctionParser.g:2018:5: ()
            	    // InternalSTFunctionParser.g:2019:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTAndExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2025:5: ( ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) ) )
            	    // InternalSTFunctionParser.g:2026:6: ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) )
            	    {
            	    // InternalSTFunctionParser.g:2026:6: ( (lv_op_2_1= Ampersand | lv_op_2_2= AND ) )
            	    // InternalSTFunctionParser.g:2027:7: (lv_op_2_1= Ampersand | lv_op_2_2= AND )
            	    {
            	    // InternalSTFunctionParser.g:2027:7: (lv_op_2_1= Ampersand | lv_op_2_2= AND )
            	    int alt42=2;
            	    int LA42_0 = input.LA(1);

            	    if ( (LA42_0==Ampersand) ) {
            	        alt42=1;
            	    }
            	    else if ( (LA42_0==AND) ) {
            	        alt42=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 42, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt42) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:2028:8: lv_op_2_1= Ampersand
            	            {
            	            lv_op_2_1=(Token)match(input,Ampersand,FOLLOW_14); if (state.failed) return current;
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
            	            // InternalSTFunctionParser.g:2039:8: lv_op_2_2= AND
            	            {
            	            lv_op_2_2=(Token)match(input,AND,FOLLOW_14); if (state.failed) return current;
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

            	    // InternalSTFunctionParser.g:2053:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTFunctionParser.g:2054:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTFunctionParser.g:2054:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTFunctionParser.g:2055:6: lv_right_3_0= ruleSTEqualityExpression
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalSTFunctionParser.g:2077:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTFunctionParser.g:2077:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTFunctionParser.g:2078:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTFunctionParser.g:2084:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_STComparisonExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2090:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2091:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2091:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTFunctionParser.g:2092:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
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
            // InternalSTFunctionParser.g:2100:3: ( ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==LessThanSignGreaterThanSign||LA45_0==EqualsSign) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2101:4: ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2101:4: ( () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) ) )
            	    // InternalSTFunctionParser.g:2102:5: () ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) )
            	    {
            	    // InternalSTFunctionParser.g:2102:5: ()
            	    // InternalSTFunctionParser.g:2103:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTEqualityExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2109:5: ( ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) ) )
            	    // InternalSTFunctionParser.g:2110:6: ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) )
            	    {
            	    // InternalSTFunctionParser.g:2110:6: ( (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign ) )
            	    // InternalSTFunctionParser.g:2111:7: (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign )
            	    {
            	    // InternalSTFunctionParser.g:2111:7: (lv_op_2_1= EqualsSign | lv_op_2_2= LessThanSignGreaterThanSign )
            	    int alt44=2;
            	    int LA44_0 = input.LA(1);

            	    if ( (LA44_0==EqualsSign) ) {
            	        alt44=1;
            	    }
            	    else if ( (LA44_0==LessThanSignGreaterThanSign) ) {
            	        alt44=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 44, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt44) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:2112:8: lv_op_2_1= EqualsSign
            	            {
            	            lv_op_2_1=(Token)match(input,EqualsSign,FOLLOW_14); if (state.failed) return current;
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
            	            // InternalSTFunctionParser.g:2123:8: lv_op_2_2= LessThanSignGreaterThanSign
            	            {
            	            lv_op_2_2=(Token)match(input,LessThanSignGreaterThanSign,FOLLOW_14); if (state.failed) return current;
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

            	    // InternalSTFunctionParser.g:2137:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTFunctionParser.g:2138:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTFunctionParser.g:2138:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTFunctionParser.g:2139:6: lv_right_3_0= ruleSTComparisonExpression
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalSTFunctionParser.g:2161:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTFunctionParser.g:2161:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTFunctionParser.g:2162:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTFunctionParser.g:2168:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
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
            // InternalSTFunctionParser.g:2174:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2175:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2175:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTFunctionParser.g:2176:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
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
            // InternalSTFunctionParser.g:2184:3: ( ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==LessThanSignEqualsSign||LA47_0==GreaterThanSignEqualsSign||LA47_0==LessThanSign||LA47_0==GreaterThanSign) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2185:4: ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2185:4: ( () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) ) )
            	    // InternalSTFunctionParser.g:2186:5: () ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) )
            	    {
            	    // InternalSTFunctionParser.g:2186:5: ()
            	    // InternalSTFunctionParser.g:2187:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTComparisonExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2193:5: ( ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) ) )
            	    // InternalSTFunctionParser.g:2194:6: ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) )
            	    {
            	    // InternalSTFunctionParser.g:2194:6: ( (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign ) )
            	    // InternalSTFunctionParser.g:2195:7: (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign )
            	    {
            	    // InternalSTFunctionParser.g:2195:7: (lv_op_2_1= LessThanSign | lv_op_2_2= GreaterThanSign | lv_op_2_3= LessThanSignEqualsSign | lv_op_2_4= GreaterThanSignEqualsSign )
            	    int alt46=4;
            	    switch ( input.LA(1) ) {
            	    case LessThanSign:
            	        {
            	        alt46=1;
            	        }
            	        break;
            	    case GreaterThanSign:
            	        {
            	        alt46=2;
            	        }
            	        break;
            	    case LessThanSignEqualsSign:
            	        {
            	        alt46=3;
            	        }
            	        break;
            	    case GreaterThanSignEqualsSign:
            	        {
            	        alt46=4;
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
            	            // InternalSTFunctionParser.g:2196:8: lv_op_2_1= LessThanSign
            	            {
            	            lv_op_2_1=(Token)match(input,LessThanSign,FOLLOW_14); if (state.failed) return current;
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
            	            // InternalSTFunctionParser.g:2207:8: lv_op_2_2= GreaterThanSign
            	            {
            	            lv_op_2_2=(Token)match(input,GreaterThanSign,FOLLOW_14); if (state.failed) return current;
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
            	            // InternalSTFunctionParser.g:2218:8: lv_op_2_3= LessThanSignEqualsSign
            	            {
            	            lv_op_2_3=(Token)match(input,LessThanSignEqualsSign,FOLLOW_14); if (state.failed) return current;
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
            	            // InternalSTFunctionParser.g:2229:8: lv_op_2_4= GreaterThanSignEqualsSign
            	            {
            	            lv_op_2_4=(Token)match(input,GreaterThanSignEqualsSign,FOLLOW_14); if (state.failed) return current;
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

            	    // InternalSTFunctionParser.g:2243:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTFunctionParser.g:2244:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTFunctionParser.g:2244:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTFunctionParser.g:2245:6: lv_right_3_0= ruleSTAddSubExpression
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
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalSTFunctionParser.g:2267:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTFunctionParser.g:2267:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTFunctionParser.g:2268:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTFunctionParser.g:2274:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_STMulDivModExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2280:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2281:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2281:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTFunctionParser.g:2282:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
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
            // InternalSTFunctionParser.g:2290:3: ( ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==PlusSign||LA49_0==HyphenMinus) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2291:4: ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2291:4: ( () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) ) )
            	    // InternalSTFunctionParser.g:2292:5: () ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) )
            	    {
            	    // InternalSTFunctionParser.g:2292:5: ()
            	    // InternalSTFunctionParser.g:2293:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTAddSubExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2299:5: ( ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) ) )
            	    // InternalSTFunctionParser.g:2300:6: ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) )
            	    {
            	    // InternalSTFunctionParser.g:2300:6: ( (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus ) )
            	    // InternalSTFunctionParser.g:2301:7: (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus )
            	    {
            	    // InternalSTFunctionParser.g:2301:7: (lv_op_2_1= PlusSign | lv_op_2_2= HyphenMinus )
            	    int alt48=2;
            	    int LA48_0 = input.LA(1);

            	    if ( (LA48_0==PlusSign) ) {
            	        alt48=1;
            	    }
            	    else if ( (LA48_0==HyphenMinus) ) {
            	        alt48=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 48, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt48) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:2302:8: lv_op_2_1= PlusSign
            	            {
            	            lv_op_2_1=(Token)match(input,PlusSign,FOLLOW_14); if (state.failed) return current;
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
            	            // InternalSTFunctionParser.g:2313:8: lv_op_2_2= HyphenMinus
            	            {
            	            lv_op_2_2=(Token)match(input,HyphenMinus,FOLLOW_14); if (state.failed) return current;
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

            	    // InternalSTFunctionParser.g:2327:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTFunctionParser.g:2328:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTFunctionParser.g:2328:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTFunctionParser.g:2329:6: lv_right_3_0= ruleSTMulDivModExpression
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
    // InternalSTFunctionParser.g:2351:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTFunctionParser.g:2351:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTFunctionParser.g:2352:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTFunctionParser.g:2358:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        Token lv_op_2_3=null;
        EObject this_STPowerExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2364:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2365:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2365:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTFunctionParser.g:2366:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
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
            // InternalSTFunctionParser.g:2374:3: ( ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==MOD||LA51_0==Asterisk||LA51_0==Solidus) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2375:4: ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2375:4: ( () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) ) )
            	    // InternalSTFunctionParser.g:2376:5: () ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) )
            	    {
            	    // InternalSTFunctionParser.g:2376:5: ()
            	    // InternalSTFunctionParser.g:2377:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTMulDivModExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2383:5: ( ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) ) )
            	    // InternalSTFunctionParser.g:2384:6: ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) )
            	    {
            	    // InternalSTFunctionParser.g:2384:6: ( (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD ) )
            	    // InternalSTFunctionParser.g:2385:7: (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD )
            	    {
            	    // InternalSTFunctionParser.g:2385:7: (lv_op_2_1= Asterisk | lv_op_2_2= Solidus | lv_op_2_3= MOD )
            	    int alt50=3;
            	    switch ( input.LA(1) ) {
            	    case Asterisk:
            	        {
            	        alt50=1;
            	        }
            	        break;
            	    case Solidus:
            	        {
            	        alt50=2;
            	        }
            	        break;
            	    case MOD:
            	        {
            	        alt50=3;
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
            	            // InternalSTFunctionParser.g:2386:8: lv_op_2_1= Asterisk
            	            {
            	            lv_op_2_1=(Token)match(input,Asterisk,FOLLOW_14); if (state.failed) return current;
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
            	            // InternalSTFunctionParser.g:2397:8: lv_op_2_2= Solidus
            	            {
            	            lv_op_2_2=(Token)match(input,Solidus,FOLLOW_14); if (state.failed) return current;
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
            	            // InternalSTFunctionParser.g:2408:8: lv_op_2_3= MOD
            	            {
            	            lv_op_2_3=(Token)match(input,MOD,FOLLOW_14); if (state.failed) return current;
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

            	    // InternalSTFunctionParser.g:2422:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTFunctionParser.g:2423:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTFunctionParser.g:2423:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTFunctionParser.g:2424:6: lv_right_3_0= ruleSTPowerExpression
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
    // InternalSTFunctionParser.g:2446:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTFunctionParser.g:2446:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTFunctionParser.g:2447:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTFunctionParser.g:2453:1: ruleSTPowerExpression returns [EObject current=null] : (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_STSignumExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2459:2: ( (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2460:2: (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2460:2: (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* )
            // InternalSTFunctionParser.g:2461:3: this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTSignumExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_STSignumExpression_0=ruleSTSignumExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STSignumExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2469:3: ( ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==AsteriskAsterisk) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2470:4: ( () ( (lv_op_2_0= AsteriskAsterisk ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2470:4: ( () ( (lv_op_2_0= AsteriskAsterisk ) ) )
            	    // InternalSTFunctionParser.g:2471:5: () ( (lv_op_2_0= AsteriskAsterisk ) )
            	    {
            	    // InternalSTFunctionParser.g:2471:5: ()
            	    // InternalSTFunctionParser.g:2472:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTPowerExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2478:5: ( (lv_op_2_0= AsteriskAsterisk ) )
            	    // InternalSTFunctionParser.g:2479:6: (lv_op_2_0= AsteriskAsterisk )
            	    {
            	    // InternalSTFunctionParser.g:2479:6: (lv_op_2_0= AsteriskAsterisk )
            	    // InternalSTFunctionParser.g:2480:7: lv_op_2_0= AsteriskAsterisk
            	    {
            	    lv_op_2_0=(Token)match(input,AsteriskAsterisk,FOLLOW_14); if (state.failed) return current;
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

            	    // InternalSTFunctionParser.g:2493:4: ( (lv_right_3_0= ruleSTSignumExpression ) )
            	    // InternalSTFunctionParser.g:2494:5: (lv_right_3_0= ruleSTSignumExpression )
            	    {
            	    // InternalSTFunctionParser.g:2494:5: (lv_right_3_0= ruleSTSignumExpression )
            	    // InternalSTFunctionParser.g:2495:6: lv_right_3_0= ruleSTSignumExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTSignumExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
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


    // $ANTLR start "entryRuleSTSignumExpression"
    // InternalSTFunctionParser.g:2517:1: entryRuleSTSignumExpression returns [EObject current=null] : iv_ruleSTSignumExpression= ruleSTSignumExpression EOF ;
    public final EObject entryRuleSTSignumExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignumExpression = null;


        try {
            // InternalSTFunctionParser.g:2517:59: (iv_ruleSTSignumExpression= ruleSTSignumExpression EOF )
            // InternalSTFunctionParser.g:2518:2: iv_ruleSTSignumExpression= ruleSTSignumExpression EOF
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
    // InternalSTFunctionParser.g:2524:1: ruleSTSignumExpression returns [EObject current=null] : (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) ) ;
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
            // InternalSTFunctionParser.g:2530:2: ( (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) ) )
            // InternalSTFunctionParser.g:2531:2: (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) )
            {
            // InternalSTFunctionParser.g:2531:2: (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) )
            int alt54=3;
            switch ( input.LA(1) ) {
            case NOT:
                {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==DWORD||LA54_1==LREAL||LA54_1==LWORD||(LA54_1>=UDINT && LA54_1<=USINT)||(LA54_1>=BOOL && LA54_1<=BYTE)||LA54_1==DINT||LA54_1==LINT||(LA54_1>=REAL && LA54_1<=SINT)||LA54_1==UINT||LA54_1==WORD||LA54_1==INT||LA54_1==PlusSign||LA54_1==HyphenMinus||LA54_1==RULE_BOOL_VALUES||(LA54_1>=RULE_NON_DECIMAL && LA54_1<=RULE_INT)) ) {
                    alt54=1;
                }
                else if ( (LA54_1==CONTINUE||LA54_1==RETURN||LA54_1==EXIT||LA54_1==LeftParenthesis||LA54_1==RULE_ID) ) {
                    alt54=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 1, input);

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
                alt54=1;
                }
                break;
            case PlusSign:
                {
                int LA54_3 = input.LA(2);

                if ( (LA54_3==RULE_INT) ) {
                    alt54=1;
                }
                else if ( (LA54_3==CONTINUE||LA54_3==RETURN||LA54_3==EXIT||LA54_3==LeftParenthesis||LA54_3==RULE_ID) ) {
                    alt54=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 3, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA54_4 = input.LA(2);

                if ( (LA54_4==CONTINUE||LA54_4==RETURN||LA54_4==EXIT||LA54_4==LeftParenthesis||LA54_4==RULE_ID) ) {
                    alt54=3;
                }
                else if ( (LA54_4==RULE_INT) ) {
                    alt54=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 4, input);

                    throw nvae;
                }
                }
                break;
            case CONTINUE:
            case RETURN:
            case EXIT:
            case LeftParenthesis:
            case RULE_ID:
                {
                alt54=2;
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
                    // InternalSTFunctionParser.g:2532:3: this_STLiteralExpressions_0= ruleSTLiteralExpressions
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
                    // InternalSTFunctionParser.g:2541:3: this_STSelectionExpression_1= ruleSTSelectionExpression
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
                    // InternalSTFunctionParser.g:2550:3: ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) )
                    {
                    // InternalSTFunctionParser.g:2550:3: ( () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) )
                    // InternalSTFunctionParser.g:2551:4: () ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) )
                    {
                    // InternalSTFunctionParser.g:2551:4: ()
                    // InternalSTFunctionParser.g:2552:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTSignumExpressionAccess().getSTSignumExpressionAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:2558:4: ( ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) ) )
                    // InternalSTFunctionParser.g:2559:5: ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) )
                    {
                    // InternalSTFunctionParser.g:2559:5: ( (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT ) )
                    // InternalSTFunctionParser.g:2560:6: (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT )
                    {
                    // InternalSTFunctionParser.g:2560:6: (lv_signum_3_1= HyphenMinus | lv_signum_3_2= PlusSign | lv_signum_3_3= NOT )
                    int alt53=3;
                    switch ( input.LA(1) ) {
                    case HyphenMinus:
                        {
                        alt53=1;
                        }
                        break;
                    case PlusSign:
                        {
                        alt53=2;
                        }
                        break;
                    case NOT:
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
                            // InternalSTFunctionParser.g:2561:7: lv_signum_3_1= HyphenMinus
                            {
                            lv_signum_3_1=(Token)match(input,HyphenMinus,FOLLOW_50); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:2572:7: lv_signum_3_2= PlusSign
                            {
                            lv_signum_3_2=(Token)match(input,PlusSign,FOLLOW_50); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:2583:7: lv_signum_3_3= NOT
                            {
                            lv_signum_3_3=(Token)match(input,NOT,FOLLOW_50); if (state.failed) return current;
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

                    // InternalSTFunctionParser.g:2596:4: ( (lv_expression_4_0= ruleSTSelectionExpression ) )
                    // InternalSTFunctionParser.g:2597:5: (lv_expression_4_0= ruleSTSelectionExpression )
                    {
                    // InternalSTFunctionParser.g:2597:5: (lv_expression_4_0= ruleSTSelectionExpression )
                    // InternalSTFunctionParser.g:2598:6: lv_expression_4_0= ruleSTSelectionExpression
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
    // InternalSTFunctionParser.g:2620:1: entryRuleSTSelectionExpression returns [EObject current=null] : iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF ;
    public final EObject entryRuleSTSelectionExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSelectionExpression = null;


        try {
            // InternalSTFunctionParser.g:2620:62: (iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF )
            // InternalSTFunctionParser.g:2621:2: iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF
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
    // InternalSTFunctionParser.g:2627:1: ruleSTSelectionExpression returns [EObject current=null] : (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* ) ;
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
            // InternalSTFunctionParser.g:2633:2: ( (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* ) )
            // InternalSTFunctionParser.g:2634:2: (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* )
            {
            // InternalSTFunctionParser.g:2634:2: (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* )
            // InternalSTFunctionParser.g:2635:3: this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getSTAtomicExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_STAtomicExpression_0=ruleSTAtomicExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAtomicExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2643:3: ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==FullStop||LA61_0==LeftSquareBracket) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2644:4: () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )?
            	    {
            	    // InternalSTFunctionParser.g:2644:4: ()
            	    // InternalSTFunctionParser.g:2645:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getSTSelectionExpressionAccess().getSTMemberSelectionReceiverAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2651:4: ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) )
            	    int alt56=2;
            	    int LA56_0 = input.LA(1);

            	    if ( (LA56_0==FullStop) ) {
            	        alt56=1;
            	    }
            	    else if ( (LA56_0==LeftSquareBracket) ) {
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
            	            // InternalSTFunctionParser.g:2652:5: ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) )
            	            {
            	            // InternalSTFunctionParser.g:2652:5: ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) )
            	            // InternalSTFunctionParser.g:2653:6: ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) )
            	            {
            	            // InternalSTFunctionParser.g:2653:6: ( (lv_structAccess_2_0= FullStop ) )
            	            // InternalSTFunctionParser.g:2654:7: (lv_structAccess_2_0= FullStop )
            	            {
            	            // InternalSTFunctionParser.g:2654:7: (lv_structAccess_2_0= FullStop )
            	            // InternalSTFunctionParser.g:2655:8: lv_structAccess_2_0= FullStop
            	            {
            	            lv_structAccess_2_0=(Token)match(input,FullStop,FOLLOW_4); if (state.failed) return current;
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

            	            // InternalSTFunctionParser.g:2667:6: ( (otherlv_3= RULE_ID ) )
            	            // InternalSTFunctionParser.g:2668:7: (otherlv_3= RULE_ID )
            	            {
            	            // InternalSTFunctionParser.g:2668:7: (otherlv_3= RULE_ID )
            	            // InternalSTFunctionParser.g:2669:8: otherlv_3= RULE_ID
            	            {
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getSTSelectionExpressionRule());
            	              								}
            	              							
            	            }
            	            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_52); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(otherlv_3, grammarAccess.getSTSelectionExpressionAccess().getMemberVarDeclarationCrossReference_1_1_0_1_0());
            	              							
            	            }

            	            }


            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalSTFunctionParser.g:2682:5: ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket )
            	            {
            	            // InternalSTFunctionParser.g:2682:5: ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket )
            	            // InternalSTFunctionParser.g:2683:6: ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket
            	            {
            	            // InternalSTFunctionParser.g:2683:6: ( (lv_arrayAccess_4_0= LeftSquareBracket ) )
            	            // InternalSTFunctionParser.g:2684:7: (lv_arrayAccess_4_0= LeftSquareBracket )
            	            {
            	            // InternalSTFunctionParser.g:2684:7: (lv_arrayAccess_4_0= LeftSquareBracket )
            	            // InternalSTFunctionParser.g:2685:8: lv_arrayAccess_4_0= LeftSquareBracket
            	            {
            	            lv_arrayAccess_4_0=(Token)match(input,LeftSquareBracket,FOLLOW_14); if (state.failed) return current;
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

            	            // InternalSTFunctionParser.g:2697:6: ( (lv_index_5_0= ruleSTExpression ) )
            	            // InternalSTFunctionParser.g:2698:7: (lv_index_5_0= ruleSTExpression )
            	            {
            	            // InternalSTFunctionParser.g:2698:7: (lv_index_5_0= ruleSTExpression )
            	            // InternalSTFunctionParser.g:2699:8: lv_index_5_0= ruleSTExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_1_1_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_15);
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

            	            // InternalSTFunctionParser.g:2716:6: (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )*
            	            loop55:
            	            do {
            	                int alt55=2;
            	                int LA55_0 = input.LA(1);

            	                if ( (LA55_0==Comma) ) {
            	                    alt55=1;
            	                }


            	                switch (alt55) {
            	            	case 1 :
            	            	    // InternalSTFunctionParser.g:2717:7: otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) )
            	            	    {
            	            	    otherlv_6=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
            	            	    if ( state.backtracking==0 ) {

            	            	      							newLeafNode(otherlv_6, grammarAccess.getSTSelectionExpressionAccess().getCommaKeyword_1_1_1_2_0());
            	            	      						
            	            	    }
            	            	    // InternalSTFunctionParser.g:2721:7: ( (lv_index_7_0= ruleSTExpression ) )
            	            	    // InternalSTFunctionParser.g:2722:8: (lv_index_7_0= ruleSTExpression )
            	            	    {
            	            	    // InternalSTFunctionParser.g:2722:8: (lv_index_7_0= ruleSTExpression )
            	            	    // InternalSTFunctionParser.g:2723:9: lv_index_7_0= ruleSTExpression
            	            	    {
            	            	    if ( state.backtracking==0 ) {

            	            	      									newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_1_2_1_0());
            	            	      								
            	            	    }
            	            	    pushFollow(FOLLOW_15);
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
            	            	    break loop55;
            	                }
            	            } while (true);

            	            otherlv_8=(Token)match(input,RightSquareBracket,FOLLOW_52); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_8, grammarAccess.getSTSelectionExpressionAccess().getRightSquareBracketKeyword_1_1_1_3());
            	              					
            	            }

            	            }


            	            }
            	            break;

            	    }

            	    // InternalSTFunctionParser.g:2747:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )?
            	    int alt59=2;
            	    alt59 = dfa59.predict(input);
            	    switch (alt59) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:2748:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis
            	            {
            	            // InternalSTFunctionParser.g:2748:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) )
            	            // InternalSTFunctionParser.g:2749:6: ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis )
            	            {
            	            // InternalSTFunctionParser.g:2753:6: (lv_poeInvocation_9_0= LeftParenthesis )
            	            // InternalSTFunctionParser.g:2754:7: lv_poeInvocation_9_0= LeftParenthesis
            	            {
            	            lv_poeInvocation_9_0=(Token)match(input,LeftParenthesis,FOLLOW_53); if (state.failed) return current;
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

            	            // InternalSTFunctionParser.g:2766:5: ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )?
            	            int alt58=2;
            	            int LA58_0 = input.LA(1);

            	            if ( ((LA58_0>=LDATE_AND_TIME && LA58_0<=DATE_AND_TIME)||LA58_0==TIME_OF_DAY||LA58_0==CONTINUE||LA58_0==WSTRING||(LA58_0>=STRING && LA58_0<=DWORD)||(LA58_0>=LDATE && LA58_0<=LWORD)||(LA58_0>=RETURN && LA58_0<=WCHAR)||(LA58_0>=BOOL && LA58_0<=DINT)||(LA58_0>=LINT && LA58_0<=UINT)||LA58_0==WORD||(LA58_0>=EXIT && LA58_0<=LDT)||LA58_0==TOD||LA58_0==DT||(LA58_0>=LD && LA58_0<=LT)||LA58_0==NOT||LA58_0==D||LA58_0==T||LA58_0==LeftParenthesis||LA58_0==PlusSign||LA58_0==HyphenMinus||LA58_0==RULE_BOOL_VALUES||(LA58_0>=RULE_NON_DECIMAL && LA58_0<=RULE_INT)||(LA58_0>=RULE_ID && LA58_0<=RULE_STRING)) ) {
            	                alt58=1;
            	            }
            	            switch (alt58) {
            	                case 1 :
            	                    // InternalSTFunctionParser.g:2767:6: ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )*
            	                    {
            	                    // InternalSTFunctionParser.g:2767:6: ( (lv_parameters_10_0= ruleSTExpression ) )
            	                    // InternalSTFunctionParser.g:2768:7: (lv_parameters_10_0= ruleSTExpression )
            	                    {
            	                    // InternalSTFunctionParser.g:2768:7: (lv_parameters_10_0= ruleSTExpression )
            	                    // InternalSTFunctionParser.g:2769:8: lv_parameters_10_0= ruleSTExpression
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      								newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getParametersSTExpressionParserRuleCall_1_2_1_0_0());
            	                      							
            	                    }
            	                    pushFollow(FOLLOW_54);
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

            	                    // InternalSTFunctionParser.g:2786:6: (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )*
            	                    loop57:
            	                    do {
            	                        int alt57=2;
            	                        int LA57_0 = input.LA(1);

            	                        if ( (LA57_0==Comma) ) {
            	                            alt57=1;
            	                        }


            	                        switch (alt57) {
            	                    	case 1 :
            	                    	    // InternalSTFunctionParser.g:2787:7: otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) )
            	                    	    {
            	                    	    otherlv_11=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
            	                    	    if ( state.backtracking==0 ) {

            	                    	      							newLeafNode(otherlv_11, grammarAccess.getSTSelectionExpressionAccess().getCommaKeyword_1_2_1_1_0());
            	                    	      						
            	                    	    }
            	                    	    // InternalSTFunctionParser.g:2791:7: ( (lv_parameters_12_0= ruleSTExpression ) )
            	                    	    // InternalSTFunctionParser.g:2792:8: (lv_parameters_12_0= ruleSTExpression )
            	                    	    {
            	                    	    // InternalSTFunctionParser.g:2792:8: (lv_parameters_12_0= ruleSTExpression )
            	                    	    // InternalSTFunctionParser.g:2793:9: lv_parameters_12_0= ruleSTExpression
            	                    	    {
            	                    	    if ( state.backtracking==0 ) {

            	                    	      									newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getParametersSTExpressionParserRuleCall_1_2_1_1_1_0());
            	                    	      								
            	                    	    }
            	                    	    pushFollow(FOLLOW_54);
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
            	                    	    break loop57;
            	                        }
            	                    } while (true);


            	                    }
            	                    break;

            	            }

            	            otherlv_13=(Token)match(input,RightParenthesis,FOLLOW_55); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(otherlv_13, grammarAccess.getSTSelectionExpressionAccess().getRightParenthesisKeyword_1_2_2());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalSTFunctionParser.g:2817:4: ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )?
            	    int alt60=2;
            	    int LA60_0 = input.LA(1);

            	    if ( (LA60_0==L) && (synpred3_InternalSTFunctionParser())) {
            	        alt60=1;
            	    }
            	    else if ( (LA60_0==D_1) && (synpred3_InternalSTFunctionParser())) {
            	        alt60=1;
            	    }
            	    else if ( (LA60_0==W) && (synpred3_InternalSTFunctionParser())) {
            	        alt60=1;
            	    }
            	    else if ( (LA60_0==B) && (synpred3_InternalSTFunctionParser())) {
            	        alt60=1;
            	    }
            	    else if ( (LA60_0==X) && (synpred3_InternalSTFunctionParser())) {
            	        alt60=1;
            	    }
            	    else if ( (LA60_0==FullStop) ) {
            	        int LA60_6 = input.LA(2);

            	        if ( (LA60_6==RULE_INT) && (synpred3_InternalSTFunctionParser())) {
            	            alt60=1;
            	        }
            	    }
            	    switch (alt60) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:2818:5: ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess )
            	            {
            	            // InternalSTFunctionParser.g:2822:5: (lv_bitaccessor_14_0= ruleMultibitPartialAccess )
            	            // InternalSTFunctionParser.g:2823:6: lv_bitaccessor_14_0= ruleMultibitPartialAccess
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTSelectionExpressionAccess().getBitaccessorMultibitPartialAccessParserRuleCall_1_3_0());
            	              					
            	            }
            	            pushFollow(FOLLOW_51);
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
            	    break loop61;
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
    // InternalSTFunctionParser.g:2845:1: entryRuleMultibitPartialAccess returns [EObject current=null] : iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF ;
    public final EObject entryRuleMultibitPartialAccess() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultibitPartialAccess = null;


        try {
            // InternalSTFunctionParser.g:2845:62: (iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF )
            // InternalSTFunctionParser.g:2846:2: iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF
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
    // InternalSTFunctionParser.g:2852:1: ruleMultibitPartialAccess returns [EObject current=null] : ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) ) ;
    public final EObject ruleMultibitPartialAccess() throws RecognitionException {
        EObject current = null;

        Token lv_index_1_0=null;
        Enumerator lv_accessSpecifier_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2858:2: ( ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) ) )
            // InternalSTFunctionParser.g:2859:2: ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) )
            {
            // InternalSTFunctionParser.g:2859:2: ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) )
            // InternalSTFunctionParser.g:2860:3: ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) )
            {
            // InternalSTFunctionParser.g:2860:3: ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) )
            // InternalSTFunctionParser.g:2861:4: (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier )
            {
            // InternalSTFunctionParser.g:2861:4: (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier )
            // InternalSTFunctionParser.g:2862:5: lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMultibitPartialAccessAccess().getAccessSpecifierMultiBitAccessSpecifierEnumRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_56);
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

            // InternalSTFunctionParser.g:2879:3: ( (lv_index_1_0= RULE_INT ) )
            // InternalSTFunctionParser.g:2880:4: (lv_index_1_0= RULE_INT )
            {
            // InternalSTFunctionParser.g:2880:4: (lv_index_1_0= RULE_INT )
            // InternalSTFunctionParser.g:2881:5: lv_index_1_0= RULE_INT
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
    // InternalSTFunctionParser.g:2901:1: entryRuleSTAtomicExpression returns [EObject current=null] : iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF ;
    public final EObject entryRuleSTAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAtomicExpression = null;


        try {
            // InternalSTFunctionParser.g:2901:59: (iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF )
            // InternalSTFunctionParser.g:2902:2: iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF
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
    // InternalSTFunctionParser.g:2908:1: ruleSTAtomicExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) | ( () otherlv_14= RETURN ) | ( () otherlv_16= CONTINUE ) | ( () otherlv_18= EXIT ) ) ;
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
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        EObject this_STExpression_1 = null;

        EObject lv_bitaccessor_7_0 = null;

        EObject lv_parameters_9_0 = null;

        EObject lv_parameters_11_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2914:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) | ( () otherlv_14= RETURN ) | ( () otherlv_16= CONTINUE ) | ( () otherlv_18= EXIT ) ) )
            // InternalSTFunctionParser.g:2915:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) | ( () otherlv_14= RETURN ) | ( () otherlv_16= CONTINUE ) | ( () otherlv_18= EXIT ) )
            {
            // InternalSTFunctionParser.g:2915:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) | ( () otherlv_14= RETURN ) | ( () otherlv_16= CONTINUE ) | ( () otherlv_18= EXIT ) )
            int alt67=5;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt67=1;
                }
                break;
            case RULE_ID:
                {
                alt67=2;
                }
                break;
            case RETURN:
                {
                alt67=3;
                }
                break;
            case CONTINUE:
                {
                alt67=4;
                }
                break;
            case EXIT:
                {
                alt67=5;
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
                    // InternalSTFunctionParser.g:2916:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTFunctionParser.g:2916:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTFunctionParser.g:2917:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTAtomicExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTAtomicExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_24);
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
                    // InternalSTFunctionParser.g:2935:3: ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? )
                    {
                    // InternalSTFunctionParser.g:2935:3: ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? )
                    // InternalSTFunctionParser.g:2936:4: () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?
                    {
                    // InternalSTFunctionParser.g:2936:4: ()
                    // InternalSTFunctionParser.g:2937:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTAtomicExpressionAccess().getSTSymbolAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:2943:4: ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==RULE_ID) ) {
                        int LA62_1 = input.LA(2);

                        if ( (LA62_1==NumberSign) ) {
                            alt62=1;
                        }
                    }
                    switch (alt62) {
                        case 1 :
                            // InternalSTFunctionParser.g:2944:5: ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign
                            {
                            // InternalSTFunctionParser.g:2944:5: ( (otherlv_4= RULE_ID ) )
                            // InternalSTFunctionParser.g:2945:6: (otherlv_4= RULE_ID )
                            {
                            // InternalSTFunctionParser.g:2945:6: (otherlv_4= RULE_ID )
                            // InternalSTFunctionParser.g:2946:7: otherlv_4= RULE_ID
                            {
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getSTAtomicExpressionRule());
                              							}
                              						
                            }
                            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_57); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(otherlv_4, grammarAccess.getSTAtomicExpressionAccess().getTypeDataTypeCrossReference_1_1_0_0());
                              						
                            }

                            }


                            }

                            otherlv_5=(Token)match(input,NumberSign,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_5, grammarAccess.getSTAtomicExpressionAccess().getNumberSignKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalSTFunctionParser.g:2962:4: ( (otherlv_6= RULE_ID ) )
                    // InternalSTFunctionParser.g:2963:5: (otherlv_6= RULE_ID )
                    {
                    // InternalSTFunctionParser.g:2963:5: (otherlv_6= RULE_ID )
                    // InternalSTFunctionParser.g:2964:6: otherlv_6= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTAtomicExpressionRule());
                      						}
                      					
                    }
                    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_6, grammarAccess.getSTAtomicExpressionAccess().getSymbolVarDeclarationCrossReference_1_2_0());
                      					
                    }

                    }


                    }

                    // InternalSTFunctionParser.g:2975:4: ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )?
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( ((LA63_0>=B && LA63_0<=X)) ) {
                        alt63=1;
                    }
                    else if ( (LA63_0==FullStop) ) {
                        int LA63_2 = input.LA(2);

                        if ( (LA63_2==RULE_INT) ) {
                            alt63=1;
                        }
                    }
                    switch (alt63) {
                        case 1 :
                            // InternalSTFunctionParser.g:2976:5: (lv_bitaccessor_7_0= ruleMultibitPartialAccess )
                            {
                            // InternalSTFunctionParser.g:2976:5: (lv_bitaccessor_7_0= ruleMultibitPartialAccess )
                            // InternalSTFunctionParser.g:2977:6: lv_bitaccessor_7_0= ruleMultibitPartialAccess
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getSTAtomicExpressionAccess().getBitaccessorMultibitPartialAccessParserRuleCall_1_3_0());
                              					
                            }
                            pushFollow(FOLLOW_23);
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

                    // InternalSTFunctionParser.g:2994:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?
                    int alt66=2;
                    alt66 = dfa66.predict(input);
                    switch (alt66) {
                        case 1 :
                            // InternalSTFunctionParser.g:2995:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis
                            {
                            // InternalSTFunctionParser.g:2995:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) )
                            // InternalSTFunctionParser.g:2996:6: ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis )
                            {
                            // InternalSTFunctionParser.g:3000:6: (lv_poeInvocation_8_0= LeftParenthesis )
                            // InternalSTFunctionParser.g:3001:7: lv_poeInvocation_8_0= LeftParenthesis
                            {
                            lv_poeInvocation_8_0=(Token)match(input,LeftParenthesis,FOLLOW_53); if (state.failed) return current;
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

                            // InternalSTFunctionParser.g:3013:5: ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )?
                            int alt65=2;
                            int LA65_0 = input.LA(1);

                            if ( ((LA65_0>=LDATE_AND_TIME && LA65_0<=DATE_AND_TIME)||LA65_0==TIME_OF_DAY||LA65_0==CONTINUE||LA65_0==WSTRING||(LA65_0>=STRING && LA65_0<=DWORD)||(LA65_0>=LDATE && LA65_0<=LWORD)||(LA65_0>=RETURN && LA65_0<=WCHAR)||(LA65_0>=BOOL && LA65_0<=DINT)||(LA65_0>=LINT && LA65_0<=UINT)||LA65_0==WORD||(LA65_0>=EXIT && LA65_0<=LDT)||LA65_0==TOD||LA65_0==DT||(LA65_0>=LD && LA65_0<=LT)||LA65_0==NOT||LA65_0==D||LA65_0==T||LA65_0==LeftParenthesis||LA65_0==PlusSign||LA65_0==HyphenMinus||LA65_0==RULE_BOOL_VALUES||(LA65_0>=RULE_NON_DECIMAL && LA65_0<=RULE_INT)||(LA65_0>=RULE_ID && LA65_0<=RULE_STRING)) ) {
                                alt65=1;
                            }
                            switch (alt65) {
                                case 1 :
                                    // InternalSTFunctionParser.g:3014:6: ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )*
                                    {
                                    // InternalSTFunctionParser.g:3014:6: ( (lv_parameters_9_0= ruleSTExpression ) )
                                    // InternalSTFunctionParser.g:3015:7: (lv_parameters_9_0= ruleSTExpression )
                                    {
                                    // InternalSTFunctionParser.g:3015:7: (lv_parameters_9_0= ruleSTExpression )
                                    // InternalSTFunctionParser.g:3016:8: lv_parameters_9_0= ruleSTExpression
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getSTAtomicExpressionAccess().getParametersSTExpressionParserRuleCall_1_4_1_0_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_54);
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

                                    // InternalSTFunctionParser.g:3033:6: (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )*
                                    loop64:
                                    do {
                                        int alt64=2;
                                        int LA64_0 = input.LA(1);

                                        if ( (LA64_0==Comma) ) {
                                            alt64=1;
                                        }


                                        switch (alt64) {
                                    	case 1 :
                                    	    // InternalSTFunctionParser.g:3034:7: otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) )
                                    	    {
                                    	    otherlv_10=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
                                    	    if ( state.backtracking==0 ) {

                                    	      							newLeafNode(otherlv_10, grammarAccess.getSTAtomicExpressionAccess().getCommaKeyword_1_4_1_1_0());
                                    	      						
                                    	    }
                                    	    // InternalSTFunctionParser.g:3038:7: ( (lv_parameters_11_0= ruleSTExpression ) )
                                    	    // InternalSTFunctionParser.g:3039:8: (lv_parameters_11_0= ruleSTExpression )
                                    	    {
                                    	    // InternalSTFunctionParser.g:3039:8: (lv_parameters_11_0= ruleSTExpression )
                                    	    // InternalSTFunctionParser.g:3040:9: lv_parameters_11_0= ruleSTExpression
                                    	    {
                                    	    if ( state.backtracking==0 ) {

                                    	      									newCompositeNode(grammarAccess.getSTAtomicExpressionAccess().getParametersSTExpressionParserRuleCall_1_4_1_1_1_0());
                                    	      								
                                    	    }
                                    	    pushFollow(FOLLOW_54);
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
                                    	    break loop64;
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
                case 3 :
                    // InternalSTFunctionParser.g:3066:3: ( () otherlv_14= RETURN )
                    {
                    // InternalSTFunctionParser.g:3066:3: ( () otherlv_14= RETURN )
                    // InternalSTFunctionParser.g:3067:4: () otherlv_14= RETURN
                    {
                    // InternalSTFunctionParser.g:3067:4: ()
                    // InternalSTFunctionParser.g:3068:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTAtomicExpressionAccess().getSTReturnAction_2_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_14=(Token)match(input,RETURN,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getSTAtomicExpressionAccess().getRETURNKeyword_2_1());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:3080:3: ( () otherlv_16= CONTINUE )
                    {
                    // InternalSTFunctionParser.g:3080:3: ( () otherlv_16= CONTINUE )
                    // InternalSTFunctionParser.g:3081:4: () otherlv_16= CONTINUE
                    {
                    // InternalSTFunctionParser.g:3081:4: ()
                    // InternalSTFunctionParser.g:3082:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTAtomicExpressionAccess().getSTContinueAction_3_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_16=(Token)match(input,CONTINUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTAtomicExpressionAccess().getCONTINUEKeyword_3_1());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSTFunctionParser.g:3094:3: ( () otherlv_18= EXIT )
                    {
                    // InternalSTFunctionParser.g:3094:3: ( () otherlv_18= EXIT )
                    // InternalSTFunctionParser.g:3095:4: () otherlv_18= EXIT
                    {
                    // InternalSTFunctionParser.g:3095:4: ()
                    // InternalSTFunctionParser.g:3096:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTAtomicExpressionAccess().getSTExitAction_4_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_18=(Token)match(input,EXIT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getSTAtomicExpressionAccess().getEXITKeyword_4_1());
                      			
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
    // InternalSTFunctionParser.g:3111:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTFunctionParser.g:3111:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTFunctionParser.g:3112:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTFunctionParser.g:3118:1: ruleSTLiteralExpressions returns [EObject current=null] : ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) ) ;
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
            // InternalSTFunctionParser.g:3124:2: ( ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) ) )
            // InternalSTFunctionParser.g:3125:2: ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) )
            {
            // InternalSTFunctionParser.g:3125:2: ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) )
            int alt68=7;
            switch ( input.LA(1) ) {
            case NOT:
                {
                int LA68_1 = input.LA(2);

                if ( (LA68_1==BOOL||LA68_1==RULE_BOOL_VALUES) ) {
                    alt68=1;
                }
                else if ( (LA68_1==DWORD||LA68_1==LREAL||LA68_1==LWORD||(LA68_1>=UDINT && LA68_1<=USINT)||LA68_1==BYTE||LA68_1==DINT||LA68_1==LINT||(LA68_1>=REAL && LA68_1<=SINT)||LA68_1==UINT||LA68_1==WORD||LA68_1==INT||LA68_1==PlusSign||LA68_1==HyphenMinus||(LA68_1>=RULE_NON_DECIMAL && LA68_1<=RULE_INT)) ) {
                    alt68=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 68, 1, input);

                    throw nvae;
                }
                }
                break;
            case BOOL:
            case RULE_BOOL_VALUES:
                {
                alt68=1;
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
                alt68=2;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt68=3;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt68=4;
                }
                break;
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt68=5;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt68=6;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt68=7;
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
                    // InternalSTFunctionParser.g:3126:3: ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3126:3: ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3127:4: () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3127:4: ()
                    // InternalSTFunctionParser.g:3128:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTBoolLiteralAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3134:4: ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) )
                    // InternalSTFunctionParser.g:3135:5: (lv_boolLiteral_1_0= ruleBOOL_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3135:5: (lv_boolLiteral_1_0= ruleBOOL_LITERAL )
                    // InternalSTFunctionParser.g:3136:6: lv_boolLiteral_1_0= ruleBOOL_LITERAL
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
                    // InternalSTFunctionParser.g:3155:3: ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3155:3: ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3156:4: () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3156:4: ()
                    // InternalSTFunctionParser.g:3157:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTNumericLiteralAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3163:4: ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) )
                    // InternalSTFunctionParser.g:3164:5: (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3164:5: (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL )
                    // InternalSTFunctionParser.g:3165:6: lv_numericLiteral_3_0= ruleNUMERIC_LITERAL
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
                    // InternalSTFunctionParser.g:3184:3: ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3184:3: ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3185:4: () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3185:4: ()
                    // InternalSTFunctionParser.g:3186:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTDateLiteralAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3192:4: ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) )
                    // InternalSTFunctionParser.g:3193:5: (lv_dateLiteral_5_0= ruleDATE_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3193:5: (lv_dateLiteral_5_0= ruleDATE_LITERAL )
                    // InternalSTFunctionParser.g:3194:6: lv_dateLiteral_5_0= ruleDATE_LITERAL
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
                    // InternalSTFunctionParser.g:3213:3: ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3213:3: ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3214:4: () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3214:4: ()
                    // InternalSTFunctionParser.g:3215:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTTimeLiteralAction_3_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3221:4: ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) )
                    // InternalSTFunctionParser.g:3222:5: (lv_timeLiteral_7_0= ruleTIME_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3222:5: (lv_timeLiteral_7_0= ruleTIME_LITERAL )
                    // InternalSTFunctionParser.g:3223:6: lv_timeLiteral_7_0= ruleTIME_LITERAL
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
                    // InternalSTFunctionParser.g:3242:3: ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3242:3: ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3243:4: () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3243:4: ()
                    // InternalSTFunctionParser.g:3244:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTTimeOfDayLiteralAction_4_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3250:4: ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) )
                    // InternalSTFunctionParser.g:3251:5: (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3251:5: (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL )
                    // InternalSTFunctionParser.g:3252:6: lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL
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
                    // InternalSTFunctionParser.g:3271:3: ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3271:3: ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3272:4: () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3272:4: ()
                    // InternalSTFunctionParser.g:3273:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTDateAndTimeLiteralAction_5_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3279:4: ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) )
                    // InternalSTFunctionParser.g:3280:5: (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3280:5: (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL )
                    // InternalSTFunctionParser.g:3281:6: lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL
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
                    // InternalSTFunctionParser.g:3300:3: ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3300:3: ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3301:4: () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3301:4: ()
                    // InternalSTFunctionParser.g:3302:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTStringLiteralAction_6_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3308:4: ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) )
                    // InternalSTFunctionParser.g:3309:5: (lv_stringLiteral_13_0= ruleSTRING_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3309:5: (lv_stringLiteral_13_0= ruleSTRING_LITERAL )
                    // InternalSTFunctionParser.g:3310:6: lv_stringLiteral_13_0= ruleSTRING_LITERAL
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
    // InternalSTFunctionParser.g:3332:1: entryRuleBOOL_LITERAL returns [EObject current=null] : iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF ;
    public final EObject entryRuleBOOL_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBOOL_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3332:53: (iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF )
            // InternalSTFunctionParser.g:3333:2: iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF
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
    // InternalSTFunctionParser.g:3339:1: ruleBOOL_LITERAL returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) ) ;
    public final EObject ruleBOOL_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token lv_keyWordValue_2_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:3345:2: ( ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) ) )
            // InternalSTFunctionParser.g:3346:2: ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) )
            {
            // InternalSTFunctionParser.g:3346:2: ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) )
            // InternalSTFunctionParser.g:3347:3: ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) )
            {
            // InternalSTFunctionParser.g:3347:3: ( (lv_not_0_0= NOT ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==NOT) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalSTFunctionParser.g:3348:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTFunctionParser.g:3348:4: (lv_not_0_0= NOT )
                    // InternalSTFunctionParser.g:3349:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_59); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:3361:3: (otherlv_1= BOOL )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==BOOL) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalSTFunctionParser.g:3362:4: otherlv_1= BOOL
                    {
                    otherlv_1=(Token)match(input,BOOL,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getBOOL_LITERALAccess().getBOOLKeyword_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:3367:3: ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) )
            // InternalSTFunctionParser.g:3368:4: (lv_keyWordValue_2_0= RULE_BOOL_VALUES )
            {
            // InternalSTFunctionParser.g:3368:4: (lv_keyWordValue_2_0= RULE_BOOL_VALUES )
            // InternalSTFunctionParser.g:3369:5: lv_keyWordValue_2_0= RULE_BOOL_VALUES
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
    // InternalSTFunctionParser.g:3389:1: entryRuleNUMERIC_LITERAL returns [EObject current=null] : iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF ;
    public final EObject entryRuleNUMERIC_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNUMERIC_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3389:56: (iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF )
            // InternalSTFunctionParser.g:3390:2: iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF
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
    // InternalSTFunctionParser.g:3396:1: ruleNUMERIC_LITERAL returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) ) ;
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
            // InternalSTFunctionParser.g:3402:2: ( ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTFunctionParser.g:3403:2: ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTFunctionParser.g:3403:2: ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) )
            // InternalSTFunctionParser.g:3404:3: ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTFunctionParser.g:3404:3: ( (lv_not_0_0= NOT ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==NOT) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalSTFunctionParser.g:3405:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTFunctionParser.g:3405:4: (lv_not_0_0= NOT )
                    // InternalSTFunctionParser.g:3406:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_61); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:3418:3: ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==DWORD||LA73_0==LREAL||LA73_0==LWORD||(LA73_0>=UDINT && LA73_0<=USINT)||LA73_0==BYTE||LA73_0==DINT||LA73_0==LINT||(LA73_0>=REAL && LA73_0<=SINT)||LA73_0==UINT||LA73_0==WORD||LA73_0==INT) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalSTFunctionParser.g:3419:4: ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) )
                    {
                    // InternalSTFunctionParser.g:3419:4: ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) )
                    // InternalSTFunctionParser.g:3420:5: (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL )
                    {
                    // InternalSTFunctionParser.g:3420:5: (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL )
                    int alt72=14;
                    switch ( input.LA(1) ) {
                    case BYTE:
                        {
                        alt72=1;
                        }
                        break;
                    case WORD:
                        {
                        alt72=2;
                        }
                        break;
                    case DWORD:
                        {
                        alt72=3;
                        }
                        break;
                    case LWORD:
                        {
                        alt72=4;
                        }
                        break;
                    case SINT:
                        {
                        alt72=5;
                        }
                        break;
                    case INT:
                        {
                        alt72=6;
                        }
                        break;
                    case DINT:
                        {
                        alt72=7;
                        }
                        break;
                    case LINT:
                        {
                        alt72=8;
                        }
                        break;
                    case USINT:
                        {
                        alt72=9;
                        }
                        break;
                    case UINT:
                        {
                        alt72=10;
                        }
                        break;
                    case UDINT:
                        {
                        alt72=11;
                        }
                        break;
                    case ULINT:
                        {
                        alt72=12;
                        }
                        break;
                    case REAL:
                        {
                        alt72=13;
                        }
                        break;
                    case LREAL:
                        {
                        alt72=14;
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
                            // InternalSTFunctionParser.g:3421:6: lv_keyword_1_1= BYTE
                            {
                            lv_keyword_1_1=(Token)match(input,BYTE,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3432:6: lv_keyword_1_2= WORD
                            {
                            lv_keyword_1_2=(Token)match(input,WORD,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3443:6: lv_keyword_1_3= DWORD
                            {
                            lv_keyword_1_3=(Token)match(input,DWORD,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3454:6: lv_keyword_1_4= LWORD
                            {
                            lv_keyword_1_4=(Token)match(input,LWORD,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3465:6: lv_keyword_1_5= SINT
                            {
                            lv_keyword_1_5=(Token)match(input,SINT,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3476:6: lv_keyword_1_6= INT
                            {
                            lv_keyword_1_6=(Token)match(input,INT,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3487:6: lv_keyword_1_7= DINT
                            {
                            lv_keyword_1_7=(Token)match(input,DINT,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3498:6: lv_keyword_1_8= LINT
                            {
                            lv_keyword_1_8=(Token)match(input,LINT,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3509:6: lv_keyword_1_9= USINT
                            {
                            lv_keyword_1_9=(Token)match(input,USINT,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3520:6: lv_keyword_1_10= UINT
                            {
                            lv_keyword_1_10=(Token)match(input,UINT,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3531:6: lv_keyword_1_11= UDINT
                            {
                            lv_keyword_1_11=(Token)match(input,UDINT,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3542:6: lv_keyword_1_12= ULINT
                            {
                            lv_keyword_1_12=(Token)match(input,ULINT,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3553:6: lv_keyword_1_13= REAL
                            {
                            lv_keyword_1_13=(Token)match(input,REAL,FOLLOW_62); if (state.failed) return current;
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
                            // InternalSTFunctionParser.g:3564:6: lv_keyword_1_14= LREAL
                            {
                            lv_keyword_1_14=(Token)match(input,LREAL,FOLLOW_62); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:3577:3: ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) )
            int alt74=3;
            switch ( input.LA(1) ) {
            case PlusSign:
                {
                int LA74_1 = input.LA(2);

                if ( (LA74_1==RULE_INT) ) {
                    int LA74_3 = input.LA(3);

                    if ( (LA74_3==FullStop) ) {
                        alt74=2;
                    }
                    else if ( (LA74_3==EOF||LA74_3==END_REPEAT||LA74_3==THEN||LA74_3==AND||LA74_3==MOD||(LA74_3>=XOR && LA74_3<=FullStopFullStop)||(LA74_3>=LessThanSignEqualsSign && LA74_3<=LessThanSignGreaterThanSign)||LA74_3==GreaterThanSignEqualsSign||LA74_3==BY||LA74_3==DO||(LA74_3>=OF && LA74_3<=OR)||LA74_3==TO||(LA74_3>=Ampersand && LA74_3<=HyphenMinus)||(LA74_3>=Solidus && LA74_3<=GreaterThanSign)||LA74_3==RightSquareBracket) ) {
                        alt74=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 74, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 74, 1, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA74_2 = input.LA(2);

                if ( (LA74_2==RULE_INT) ) {
                    int LA74_3 = input.LA(3);

                    if ( (LA74_3==FullStop) ) {
                        alt74=2;
                    }
                    else if ( (LA74_3==EOF||LA74_3==END_REPEAT||LA74_3==THEN||LA74_3==AND||LA74_3==MOD||(LA74_3>=XOR && LA74_3<=FullStopFullStop)||(LA74_3>=LessThanSignEqualsSign && LA74_3<=LessThanSignGreaterThanSign)||LA74_3==GreaterThanSignEqualsSign||LA74_3==BY||LA74_3==DO||(LA74_3>=OF && LA74_3<=OR)||LA74_3==TO||(LA74_3>=Ampersand && LA74_3<=HyphenMinus)||(LA74_3>=Solidus && LA74_3<=GreaterThanSign)||LA74_3==RightSquareBracket) ) {
                        alt74=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 74, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 74, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
                {
                int LA74_3 = input.LA(2);

                if ( (LA74_3==FullStop) ) {
                    alt74=2;
                }
                else if ( (LA74_3==EOF||LA74_3==END_REPEAT||LA74_3==THEN||LA74_3==AND||LA74_3==MOD||(LA74_3>=XOR && LA74_3<=FullStopFullStop)||(LA74_3>=LessThanSignEqualsSign && LA74_3<=LessThanSignGreaterThanSign)||LA74_3==GreaterThanSignEqualsSign||LA74_3==BY||LA74_3==DO||(LA74_3>=OF && LA74_3<=OR)||LA74_3==TO||(LA74_3>=Ampersand && LA74_3<=HyphenMinus)||(LA74_3>=Solidus && LA74_3<=GreaterThanSign)||LA74_3==RightSquareBracket) ) {
                    alt74=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 74, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_NON_DECIMAL:
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
                    // InternalSTFunctionParser.g:3578:4: ( (lv_intValue_2_0= ruleINTEGER ) )
                    {
                    // InternalSTFunctionParser.g:3578:4: ( (lv_intValue_2_0= ruleINTEGER ) )
                    // InternalSTFunctionParser.g:3579:5: (lv_intValue_2_0= ruleINTEGER )
                    {
                    // InternalSTFunctionParser.g:3579:5: (lv_intValue_2_0= ruleINTEGER )
                    // InternalSTFunctionParser.g:3580:6: lv_intValue_2_0= ruleINTEGER
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
                    // InternalSTFunctionParser.g:3598:4: ( (lv_realValue_3_0= ruleREAL ) )
                    {
                    // InternalSTFunctionParser.g:3598:4: ( (lv_realValue_3_0= ruleREAL ) )
                    // InternalSTFunctionParser.g:3599:5: (lv_realValue_3_0= ruleREAL )
                    {
                    // InternalSTFunctionParser.g:3599:5: (lv_realValue_3_0= ruleREAL )
                    // InternalSTFunctionParser.g:3600:6: lv_realValue_3_0= ruleREAL
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
                    // InternalSTFunctionParser.g:3618:4: ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) )
                    {
                    // InternalSTFunctionParser.g:3618:4: ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) )
                    // InternalSTFunctionParser.g:3619:5: (lv_hexValue_4_0= RULE_NON_DECIMAL )
                    {
                    // InternalSTFunctionParser.g:3619:5: (lv_hexValue_4_0= RULE_NON_DECIMAL )
                    // InternalSTFunctionParser.g:3620:6: lv_hexValue_4_0= RULE_NON_DECIMAL
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
    // InternalSTFunctionParser.g:3641:1: entryRuleDATE_LITERAL returns [EObject current=null] : iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF ;
    public final EObject entryRuleDATE_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDATE_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3641:53: (iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF )
            // InternalSTFunctionParser.g:3642:2: iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF
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
    // InternalSTFunctionParser.g:3648:1: ruleDATE_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) ) ;
    public final EObject ruleDATE_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3654:2: ( ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) ) )
            // InternalSTFunctionParser.g:3655:2: ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) )
            {
            // InternalSTFunctionParser.g:3655:2: ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) )
            // InternalSTFunctionParser.g:3656:3: ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) )
            {
            // InternalSTFunctionParser.g:3656:3: ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) )
            // InternalSTFunctionParser.g:3657:4: ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) )
            {
            // InternalSTFunctionParser.g:3657:4: ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) )
            // InternalSTFunctionParser.g:3658:5: (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD )
            {
            // InternalSTFunctionParser.g:3658:5: (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD )
            int alt75=4;
            switch ( input.LA(1) ) {
            case DATE:
                {
                alt75=1;
                }
                break;
            case LDATE:
                {
                alt75=2;
                }
                break;
            case D:
                {
                alt75=3;
                }
                break;
            case LD:
                {
                alt75=4;
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
                    // InternalSTFunctionParser.g:3659:6: lv_keyword_0_1= DATE
                    {
                    lv_keyword_0_1=(Token)match(input,DATE,FOLLOW_56); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3670:6: lv_keyword_0_2= LDATE
                    {
                    lv_keyword_0_2=(Token)match(input,LDATE,FOLLOW_56); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3681:6: lv_keyword_0_3= D
                    {
                    lv_keyword_0_3=(Token)match(input,D,FOLLOW_56); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3692:6: lv_keyword_0_4= LD
                    {
                    lv_keyword_0_4=(Token)match(input,LD,FOLLOW_56); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:3705:3: ( (lv_value_1_0= ruleDATE ) )
            // InternalSTFunctionParser.g:3706:4: (lv_value_1_0= ruleDATE )
            {
            // InternalSTFunctionParser.g:3706:4: (lv_value_1_0= ruleDATE )
            // InternalSTFunctionParser.g:3707:5: lv_value_1_0= ruleDATE
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
    // InternalSTFunctionParser.g:3728:1: entryRuleTIME_LITERAL returns [EObject current=null] : iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF ;
    public final EObject entryRuleTIME_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTIME_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3728:53: (iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF )
            // InternalSTFunctionParser.g:3729:2: iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF
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
    // InternalSTFunctionParser.g:3735:1: ruleTIME_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) ) ;
    public final EObject ruleTIME_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        Token lv_value_1_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:3741:2: ( ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) ) )
            // InternalSTFunctionParser.g:3742:2: ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) )
            {
            // InternalSTFunctionParser.g:3742:2: ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) )
            // InternalSTFunctionParser.g:3743:3: ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) )
            {
            // InternalSTFunctionParser.g:3743:3: ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) )
            // InternalSTFunctionParser.g:3744:4: ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) )
            {
            // InternalSTFunctionParser.g:3744:4: ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) )
            // InternalSTFunctionParser.g:3745:5: (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT )
            {
            // InternalSTFunctionParser.g:3745:5: (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT )
            int alt76=4;
            switch ( input.LA(1) ) {
            case TIME:
                {
                alt76=1;
                }
                break;
            case LTIME:
                {
                alt76=2;
                }
                break;
            case T:
                {
                alt76=3;
                }
                break;
            case LT:
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
                    // InternalSTFunctionParser.g:3746:6: lv_keyword_0_1= TIME
                    {
                    lv_keyword_0_1=(Token)match(input,TIME,FOLLOW_63); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3757:6: lv_keyword_0_2= LTIME
                    {
                    lv_keyword_0_2=(Token)match(input,LTIME,FOLLOW_63); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3768:6: lv_keyword_0_3= T
                    {
                    lv_keyword_0_3=(Token)match(input,T,FOLLOW_63); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3779:6: lv_keyword_0_4= LT
                    {
                    lv_keyword_0_4=(Token)match(input,LT,FOLLOW_63); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:3792:3: ( (lv_value_1_0= RULE_TIME ) )
            // InternalSTFunctionParser.g:3793:4: (lv_value_1_0= RULE_TIME )
            {
            // InternalSTFunctionParser.g:3793:4: (lv_value_1_0= RULE_TIME )
            // InternalSTFunctionParser.g:3794:5: lv_value_1_0= RULE_TIME
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
    // InternalSTFunctionParser.g:3814:1: entryRuleTIME_OF_DAY_LITERAL returns [EObject current=null] : iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF ;
    public final EObject entryRuleTIME_OF_DAY_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTIME_OF_DAY_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3814:60: (iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF )
            // InternalSTFunctionParser.g:3815:2: iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF
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
    // InternalSTFunctionParser.g:3821:1: ruleTIME_OF_DAY_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) ) ;
    public final EObject ruleTIME_OF_DAY_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3827:2: ( ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) ) )
            // InternalSTFunctionParser.g:3828:2: ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) )
            {
            // InternalSTFunctionParser.g:3828:2: ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) )
            // InternalSTFunctionParser.g:3829:3: ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) )
            {
            // InternalSTFunctionParser.g:3829:3: ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) )
            // InternalSTFunctionParser.g:3830:4: ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) )
            {
            // InternalSTFunctionParser.g:3830:4: ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) )
            // InternalSTFunctionParser.g:3831:5: (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD )
            {
            // InternalSTFunctionParser.g:3831:5: (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD )
            int alt77=3;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt77=1;
                }
                break;
            case TOD:
                {
                alt77=2;
                }
                break;
            case LTOD:
                {
                alt77=3;
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
                    // InternalSTFunctionParser.g:3832:6: lv_keyword_0_1= TIME_OF_DAY
                    {
                    lv_keyword_0_1=(Token)match(input,TIME_OF_DAY,FOLLOW_56); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3843:6: lv_keyword_0_2= TOD
                    {
                    lv_keyword_0_2=(Token)match(input,TOD,FOLLOW_56); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3854:6: lv_keyword_0_3= LTOD
                    {
                    lv_keyword_0_3=(Token)match(input,LTOD,FOLLOW_56); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:3867:3: ( (lv_value_1_0= ruleTIME_OF_DAY ) )
            // InternalSTFunctionParser.g:3868:4: (lv_value_1_0= ruleTIME_OF_DAY )
            {
            // InternalSTFunctionParser.g:3868:4: (lv_value_1_0= ruleTIME_OF_DAY )
            // InternalSTFunctionParser.g:3869:5: lv_value_1_0= ruleTIME_OF_DAY
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
    // InternalSTFunctionParser.g:3890:1: entryRuleDATE_AND_TIME_LITERAL returns [EObject current=null] : iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF ;
    public final EObject entryRuleDATE_AND_TIME_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDATE_AND_TIME_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3890:62: (iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF )
            // InternalSTFunctionParser.g:3891:2: iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF
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
    // InternalSTFunctionParser.g:3897:1: ruleDATE_AND_TIME_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) ) ;
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
            // InternalSTFunctionParser.g:3903:2: ( ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) ) )
            // InternalSTFunctionParser.g:3904:2: ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) )
            {
            // InternalSTFunctionParser.g:3904:2: ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) )
            // InternalSTFunctionParser.g:3905:3: ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) )
            {
            // InternalSTFunctionParser.g:3905:3: ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) )
            // InternalSTFunctionParser.g:3906:4: ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) )
            {
            // InternalSTFunctionParser.g:3906:4: ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) )
            // InternalSTFunctionParser.g:3907:5: (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT )
            {
            // InternalSTFunctionParser.g:3907:5: (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT )
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
                    // InternalSTFunctionParser.g:3908:6: lv_keyword_0_1= DATE_AND_TIME
                    {
                    lv_keyword_0_1=(Token)match(input,DATE_AND_TIME,FOLLOW_56); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3919:6: lv_keyword_0_2= LDATE_AND_TIME
                    {
                    lv_keyword_0_2=(Token)match(input,LDATE_AND_TIME,FOLLOW_56); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3930:6: lv_keyword_0_3= DT
                    {
                    lv_keyword_0_3=(Token)match(input,DT,FOLLOW_56); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:3941:6: lv_keyword_0_4= LDT
                    {
                    lv_keyword_0_4=(Token)match(input,LDT,FOLLOW_56); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:3954:3: ( (lv_dateValue_1_0= ruleDATE ) )
            // InternalSTFunctionParser.g:3955:4: (lv_dateValue_1_0= ruleDATE )
            {
            // InternalSTFunctionParser.g:3955:4: (lv_dateValue_1_0= ruleDATE )
            // InternalSTFunctionParser.g:3956:5: lv_dateValue_1_0= ruleDATE
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDATE_AND_TIME_LITERALAccess().getDateValueDATEParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_64);
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

            otherlv_2=(Token)match(input,HyphenMinus,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getDATE_AND_TIME_LITERALAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:3977:3: ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) )
            // InternalSTFunctionParser.g:3978:4: (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY )
            {
            // InternalSTFunctionParser.g:3978:4: (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY )
            // InternalSTFunctionParser.g:3979:5: lv_timeOfDayValue_3_0= ruleTIME_OF_DAY
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
    // InternalSTFunctionParser.g:4000:1: entryRuleSTRING_LITERAL returns [EObject current=null] : iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF ;
    public final EObject entryRuleSTRING_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRING_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:4000:55: (iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF )
            // InternalSTFunctionParser.g:4001:2: iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF
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
    // InternalSTFunctionParser.g:4007:1: ruleSTRING_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTRING_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_value_4_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4013:2: ( ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) ) )
            // InternalSTFunctionParser.g:4014:2: ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) )
            {
            // InternalSTFunctionParser.g:4014:2: ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) )
            // InternalSTFunctionParser.g:4015:3: ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) )
            {
            // InternalSTFunctionParser.g:4015:3: ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )?
            int alt79=5;
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
            }

            switch (alt79) {
                case 1 :
                    // InternalSTFunctionParser.g:4016:4: ( (lv_keyword_0_0= STRING ) )
                    {
                    // InternalSTFunctionParser.g:4016:4: ( (lv_keyword_0_0= STRING ) )
                    // InternalSTFunctionParser.g:4017:5: (lv_keyword_0_0= STRING )
                    {
                    // InternalSTFunctionParser.g:4017:5: (lv_keyword_0_0= STRING )
                    // InternalSTFunctionParser.g:4018:6: lv_keyword_0_0= STRING
                    {
                    lv_keyword_0_0=(Token)match(input,STRING,FOLLOW_65); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:4031:4: otherlv_1= WSTRING
                    {
                    otherlv_1=(Token)match(input,WSTRING,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTRING_LITERALAccess().getWSTRINGKeyword_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4036:4: otherlv_2= CHAR
                    {
                    otherlv_2=(Token)match(input,CHAR,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTRING_LITERALAccess().getCHARKeyword_0_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:4041:4: otherlv_3= WCHAR
                    {
                    otherlv_3=(Token)match(input,WCHAR,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTRING_LITERALAccess().getWCHARKeyword_0_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:4046:3: ( (lv_value_4_0= RULE_STRING ) )
            // InternalSTFunctionParser.g:4047:4: (lv_value_4_0= RULE_STRING )
            {
            // InternalSTFunctionParser.g:4047:4: (lv_value_4_0= RULE_STRING )
            // InternalSTFunctionParser.g:4048:5: lv_value_4_0= RULE_STRING
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
    // InternalSTFunctionParser.g:4068:1: entryRuleINTEGER returns [String current=null] : iv_ruleINTEGER= ruleINTEGER EOF ;
    public final String entryRuleINTEGER() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINTEGER = null;


        try {
            // InternalSTFunctionParser.g:4068:47: (iv_ruleINTEGER= ruleINTEGER EOF )
            // InternalSTFunctionParser.g:4069:2: iv_ruleINTEGER= ruleINTEGER EOF
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
    // InternalSTFunctionParser.g:4075:1: ruleINTEGER returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleINTEGER() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4081:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ) )
            // InternalSTFunctionParser.g:4082:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT )
            {
            // InternalSTFunctionParser.g:4082:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT )
            // InternalSTFunctionParser.g:4083:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT
            {
            // InternalSTFunctionParser.g:4083:3: (kw= PlusSign | kw= HyphenMinus )?
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
                    // InternalSTFunctionParser.g:4084:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getINTEGERAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4090:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_56); if (state.failed) return current;
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
    // InternalSTFunctionParser.g:4107:1: entryRuleREAL returns [String current=null] : iv_ruleREAL= ruleREAL EOF ;
    public final String entryRuleREAL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleREAL = null;


        try {
            // InternalSTFunctionParser.g:4107:44: (iv_ruleREAL= ruleREAL EOF )
            // InternalSTFunctionParser.g:4108:2: iv_ruleREAL= ruleREAL EOF
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
    // InternalSTFunctionParser.g:4114:1: ruleREAL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleREAL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_EXT_INT_2=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_INTEGER_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4120:2: ( (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) ) )
            // InternalSTFunctionParser.g:4121:2: (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) )
            {
            // InternalSTFunctionParser.g:4121:2: (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) )
            // InternalSTFunctionParser.g:4122:3: this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getREALAccess().getINTEGERParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_66);
            this_INTEGER_0=ruleINTEGER();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INTEGER_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,FullStop,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getREALAccess().getFullStopKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:4137:3: (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT )
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
                    // InternalSTFunctionParser.g:4138:4: this_EXT_INT_2= RULE_EXT_INT
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
                    // InternalSTFunctionParser.g:4146:4: this_INT_3= RULE_INT
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
    // InternalSTFunctionParser.g:4158:1: entryRuleDATE returns [String current=null] : iv_ruleDATE= ruleDATE EOF ;
    public final String entryRuleDATE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDATE = null;


        try {
            // InternalSTFunctionParser.g:4158:44: (iv_ruleDATE= ruleDATE EOF )
            // InternalSTFunctionParser.g:4159:2: iv_ruleDATE= ruleDATE EOF
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
    // InternalSTFunctionParser.g:4165:1: ruleDATE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDATE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4171:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTFunctionParser.g:4172:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTFunctionParser.g:4172:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTFunctionParser.g:4173:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDATEAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDATEAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDATEAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_56); if (state.failed) return current;
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
    // InternalSTFunctionParser.g:4208:1: entryRuleTIME_OF_DAY returns [String current=null] : iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF ;
    public final String entryRuleTIME_OF_DAY() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTIME_OF_DAY = null;


        try {
            // InternalSTFunctionParser.g:4208:51: (iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF )
            // InternalSTFunctionParser.g:4209:2: iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF
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
    // InternalSTFunctionParser.g:4215:1: ruleTIME_OF_DAY returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTIME_OF_DAY() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4221:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) )
            // InternalSTFunctionParser.g:4222:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            {
            // InternalSTFunctionParser.g:4222:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            // InternalSTFunctionParser.g:4223:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTIME_OF_DAYAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTIME_OF_DAYAccess().getColonKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getTIME_OF_DAYAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTIME_OF_DAYAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTIME_OF_DAYAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTFunctionParser.g:4254:3: (kw= FullStop this_INT_6= RULE_INT )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==FullStop) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalSTFunctionParser.g:4255:4: kw= FullStop this_INT_6= RULE_INT
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_56); if (state.failed) return current;
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
    // InternalSTFunctionParser.g:4272:1: ruleMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) ) ;
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
            // InternalSTFunctionParser.g:4278:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) ) )
            // InternalSTFunctionParser.g:4279:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) )
            {
            // InternalSTFunctionParser.g:4279:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) )
            int alt83=6;
            switch ( input.LA(1) ) {
            case L:
                {
                alt83=1;
                }
                break;
            case D_1:
                {
                alt83=2;
                }
                break;
            case W:
                {
                alt83=3;
                }
                break;
            case B:
                {
                alt83=4;
                }
                break;
            case X:
                {
                alt83=5;
                }
                break;
            case FullStop:
                {
                alt83=6;
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
                    // InternalSTFunctionParser.g:4280:3: (enumLiteral_0= L )
                    {
                    // InternalSTFunctionParser.g:4280:3: (enumLiteral_0= L )
                    // InternalSTFunctionParser.g:4281:4: enumLiteral_0= L
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
                    // InternalSTFunctionParser.g:4288:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTFunctionParser.g:4288:3: (enumLiteral_1= D_1 )
                    // InternalSTFunctionParser.g:4289:4: enumLiteral_1= D_1
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
                    // InternalSTFunctionParser.g:4296:3: (enumLiteral_2= W )
                    {
                    // InternalSTFunctionParser.g:4296:3: (enumLiteral_2= W )
                    // InternalSTFunctionParser.g:4297:4: enumLiteral_2= W
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
                    // InternalSTFunctionParser.g:4304:3: (enumLiteral_3= B )
                    {
                    // InternalSTFunctionParser.g:4304:3: (enumLiteral_3= B )
                    // InternalSTFunctionParser.g:4305:4: enumLiteral_3= B
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
                    // InternalSTFunctionParser.g:4312:3: (enumLiteral_4= X )
                    {
                    // InternalSTFunctionParser.g:4312:3: (enumLiteral_4= X )
                    // InternalSTFunctionParser.g:4313:4: enumLiteral_4= X
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
                    // InternalSTFunctionParser.g:4320:3: (enumLiteral_5= FullStop )
                    {
                    // InternalSTFunctionParser.g:4320:3: (enumLiteral_5= FullStop )
                    // InternalSTFunctionParser.g:4321:4: enumLiteral_5= FullStop
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

    // $ANTLR start synpred1_InternalSTFunctionParser
    public final void synpred1_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:1390:4: ( ( ruleSTStatement ) )
        // InternalSTFunctionParser.g:1390:5: ( ruleSTStatement )
        {
        // InternalSTFunctionParser.g:1390:5: ( ruleSTStatement )
        // InternalSTFunctionParser.g:1391:5: ruleSTStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalSTFunctionParser

    // $ANTLR start synpred2_InternalSTFunctionParser
    public final void synpred2_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:2749:6: ( ( LeftParenthesis ) )
        // InternalSTFunctionParser.g:2749:7: ( LeftParenthesis )
        {
        // InternalSTFunctionParser.g:2749:7: ( LeftParenthesis )
        // InternalSTFunctionParser.g:2750:7: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSTFunctionParser

    // $ANTLR start synpred3_InternalSTFunctionParser
    public final void synpred3_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:2818:5: ( ( ruleMultibitPartialAccess ) )
        // InternalSTFunctionParser.g:2818:6: ( ruleMultibitPartialAccess )
        {
        // InternalSTFunctionParser.g:2818:6: ( ruleMultibitPartialAccess )
        // InternalSTFunctionParser.g:2819:6: ruleMultibitPartialAccess
        {
        pushFollow(FOLLOW_2);
        ruleMultibitPartialAccess();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred3_InternalSTFunctionParser

    // $ANTLR start synpred4_InternalSTFunctionParser
    public final void synpred4_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:2996:6: ( ( LeftParenthesis ) )
        // InternalSTFunctionParser.g:2996:7: ( LeftParenthesis )
        {
        // InternalSTFunctionParser.g:2996:7: ( LeftParenthesis )
        // InternalSTFunctionParser.g:2997:7: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred4_InternalSTFunctionParser

    // Delegated rules

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


    protected DFA32 dfa32 = new DFA32(this);
    protected DFA59 dfa59 = new DFA59(this);
    protected DFA66 dfa66 = new DFA66(this);
    static final String dfa_1s = "\13\uffff";
    static final String dfa_2s = "\1\1\12\uffff";
    static final String dfa_3s = "\1\4\1\uffff\1\70\10\uffff";
    static final String dfa_4s = "\1\156\1\uffff\1\145\10\uffff";
    static final String dfa_5s = "\1\uffff\1\2\1\uffff\10\1";
    static final String dfa_6s = "\1\1\1\uffff\1\0\10\uffff}>";
    static final String[] dfa_7s = {
            "\2\1\1\uffff\1\1\5\uffff\2\1\2\uffff\1\1\2\uffff\2\1\1\uffff\4\1\1\7\5\1\1\uffff\5\1\1\uffff\6\1\1\uffff\1\6\1\1\1\4\4\1\1\uffff\1\1\6\uffff\1\1\1\5\2\1\1\uffff\1\1\13\uffff\1\1\1\uffff\1\3\2\uffff\1\1\3\uffff\1\1\2\uffff\1\1\1\uffff\1\1\3\uffff\1\10\5\uffff\1\1\1\uffff\2\1\2\uffff\1\2\1\1",
            "",
            "\6\1\4\uffff\1\1\2\uffff\3\1\1\11\2\1\1\12\1\1\6\uffff\1\1\2\uffff\3\1\1\uffff\7\1\1\uffff\4\1",
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

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 1389:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA32_2 = input.LA(1);

                         
                        int index32_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA32_2>=B && LA32_2<=AND)||LA32_2==MOD||(LA32_2>=XOR && LA32_2<=FullStopFullStop)||(LA32_2>=LessThanSignEqualsSign && LA32_2<=LessThanSignGreaterThanSign)||LA32_2==GreaterThanSignEqualsSign||LA32_2==OR||(LA32_2>=NumberSign && LA32_2<=LeftParenthesis)||(LA32_2>=Asterisk && LA32_2<=Colon)||(LA32_2>=LessThanSign && LA32_2<=LeftSquareBracket)) ) {s = 1;}

                        else if ( (LA32_2==ColonEqualsSign) && (synpred1_InternalSTFunctionParser())) {s = 9;}

                        else if ( (LA32_2==EqualsSignGreaterThanSign) && (synpred1_InternalSTFunctionParser())) {s = 10;}

                         
                        input.seek(index32_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA32_0 = input.LA(1);

                         
                        int index32_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA32_0==EOF||(LA32_0>=LDATE_AND_TIME && LA32_0<=DATE_AND_TIME)||LA32_0==TIME_OF_DAY||(LA32_0>=CONTINUE && LA32_0<=END_CASE)||LA32_0==WSTRING||(LA32_0>=STRING && LA32_0<=DWORD)||(LA32_0>=LDATE && LA32_0<=LWORD)||(LA32_0>=RETURN && LA32_0<=WCHAR)||(LA32_0>=BOOL && LA32_0<=DINT)||(LA32_0>=LINT && LA32_0<=UINT)||LA32_0==WORD||(LA32_0>=ELSE && LA32_0<=LDT)||LA32_0==TOD||LA32_0==DT||(LA32_0>=LD && LA32_0<=LT)||LA32_0==NOT||LA32_0==D||LA32_0==T||LA32_0==LeftParenthesis||LA32_0==PlusSign||LA32_0==HyphenMinus||LA32_0==RULE_BOOL_VALUES||(LA32_0>=RULE_NON_DECIMAL && LA32_0<=RULE_INT)||LA32_0==RULE_STRING) ) {s = 1;}

                        else if ( (LA32_0==RULE_ID) ) {s = 2;}

                        else if ( (LA32_0==IF) && (synpred1_InternalSTFunctionParser())) {s = 3;}

                        else if ( (LA32_0==CASE) && (synpred1_InternalSTFunctionParser())) {s = 4;}

                        else if ( (LA32_0==FOR) && (synpred1_InternalSTFunctionParser())) {s = 5;}

                        else if ( (LA32_0==WHILE) && (synpred1_InternalSTFunctionParser())) {s = 6;}

                        else if ( (LA32_0==REPEAT) && (synpred1_InternalSTFunctionParser())) {s = 7;}

                        else if ( (LA32_0==Semicolon) && (synpred1_InternalSTFunctionParser())) {s = 8;}

                         
                        input.seek(index32_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 32, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\47\uffff";
    static final String dfa_9s = "\1\2\46\uffff";
    static final String dfa_10s = "\1\10\1\0\45\uffff";
    static final String dfa_11s = "\1\146\1\0\45\uffff";
    static final String dfa_12s = "\2\uffff\1\2\43\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\45\uffff}>";
    static final String[] dfa_14s = {
            "\1\2\55\uffff\1\2\1\uffff\6\2\4\uffff\1\2\2\uffff\3\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\16\2",
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

    class DFA59 extends DFA {

        public DFA59(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 59;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "2747:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )?";
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
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 38;}

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
    static final String dfa_15s = "\42\uffff";
    static final String dfa_16s = "\1\2\41\uffff";
    static final String dfa_17s = "\1\10\1\0\40\uffff";
    static final String dfa_18s = "\1\146\1\0\40\uffff";
    static final String dfa_19s = "\2\uffff\1\2\36\uffff\1\1";
    static final String dfa_20s = "\1\uffff\1\0\40\uffff}>";
    static final String[] dfa_21s = {
            "\1\2\55\uffff\1\2\6\uffff\1\2\4\uffff\1\2\2\uffff\3\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\16\2",
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

    class DFA66 extends DFA {

        public DFA66(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 66;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "2994:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA66_1 = input.LA(1);

                         
                        int index66_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSTFunctionParser()) ) {s = 33;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index66_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 66, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x8002800008010A40L,0x0000200300020000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x8002800008010A40L,0x0000200200020000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x8002800008000040L,0x0000200200020000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000081000L,0x0000200000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000080000L,0x0000200000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000000100002000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000200000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x40B93F7DF7B220B0L,0x000066802910800BL});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000004010000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000002200000100L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000100L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x40B93F7DF7B220B0L,0x000066A02910800BL});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000900L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x8006808008400000L,0x0000200200020000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0004008000400000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x8002800008000002L,0x0000200200020000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x40BD3F7DF7B260B0L,0x000066802910800BL});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000000110000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000000000014000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x8002800008040000L,0x0000200200020000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x8002800008000400L,0x0000200200020000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x8002C00008000000L,0x0000200200020000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x2000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000400L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000001400001200L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000028000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000084000004L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0008000010002000L,0x0000200001000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000002040000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x1F00000000000002L,0x0000002041000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x40B93F7DF7B220B0L,0x000066802B10800BL});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000012000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x1F00000000000002L,0x0000002040000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x1F00000000000002L,0x0000000041000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000400000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x00112D48E5200000L,0x0000060028000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000060028000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000140000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});

}