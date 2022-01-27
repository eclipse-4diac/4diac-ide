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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "END_FUNCTION", "TIME_OF_DAY", "END_REPEAT", "VAR_OUTPUT", "END_WHILE", "VAR_INPUT", "CONSTANT", "CONTINUE", "END_CASE", "FUNCTION", "VAR_TEMP", "WSTRING", "END_FOR", "END_VAR", "STRING", "DWORD", "END_IF", "LDATE", "LREAL", "LTIME", "LWORD", "REPEAT", "RETURN", "UDINT", "ULINT", "USINT", "WCHAR", "ARRAY", "BOOL", "BYTE", "CHAR", "DATE", "DINT", "ELSIF", "LINT", "LTOD", "REAL", "SINT", "TIME", "UINT", "UNTIL", "WHILE", "WORD", "CASE", "ELSE", "EXIT", "INT", "LDT", "THEN", "TOD", "B", "D_1", "L", "W", "X", "AND", "DT", "FOR", "LD", "LT", "MOD", "NOT", "VAR", "XOR", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "D", "DO", "IF", "OF", "OR", "T", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "LeftSquareBracket", "RightSquareBracket", "RULE_BOOL_VALUES", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_TIME", "RULE_EXT_INT", "RULE_ID", "RULE_STRING", "RULE_WSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=26;
    public static final int LessThanSignGreaterThanSign=74;
    public static final int VAR=68;
    public static final int END_IF=22;
    public static final int ULINT=30;
    public static final int END_CASE=14;
    public static final int LessThanSign=97;
    public static final int LeftParenthesis=87;
    public static final int BYTE=35;
    public static final int ELSE=50;
    public static final int RULE_TIME=106;
    public static final int IF=80;
    public static final int LINT=40;
    public static final int GreaterThanSign=99;
    public static final int WORD=48;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=108;
    public static final int TOD=55;
    public static final int DINT=38;
    public static final int FUNCTION=15;
    public static final int UDINT=29;
    public static final int CASE=49;
    public static final int GreaterThanSignEqualsSign=75;
    public static final int AT=76;
    public static final int PlusSign=90;
    public static final int RULE_INT=105;
    public static final int END_FOR=18;
    public static final int RULE_ML_COMMENT=111;
    public static final int THEN=54;
    public static final int XOR=69;
    public static final int LeftSquareBracket=100;
    public static final int EXIT=51;
    public static final int TIME_OF_DAY=7;
    public static final int B=56;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=27;
    public static final int D=78;
    public static final int CHAR=36;
    public static final int L=58;
    public static final int LTIME=25;
    public static final int Comma=91;
    public static final int HyphenMinus=92;
    public static final int T=83;
    public static final int W=59;
    public static final int BY=77;
    public static final int X=60;
    public static final int ELSIF=39;
    public static final int END_REPEAT=8;
    public static final int LessThanSignEqualsSign=73;
    public static final int Solidus=94;
    public static final int VAR_INPUT=11;
    public static final int FullStop=93;
    public static final int VAR_TEMP=16;
    public static final int CONSTANT=12;
    public static final int CONTINUE=13;
    public static final int Semicolon=96;
    public static final int LD=64;
    public static final int VAR_OUTPUT=9;
    public static final int STRING=20;
    public static final int RULE_HEX_DIGIT=103;
    public static final int TO=84;
    public static final int RULE_BOOL_VALUES=102;
    public static final int UINT=45;
    public static final int LTOD=41;
    public static final int ARRAY=33;
    public static final int LT=65;
    public static final int DO=79;
    public static final int WSTRING=17;
    public static final int DT=62;
    public static final int END_VAR=19;
    public static final int FullStopFullStop=71;
    public static final int Ampersand=86;
    public static final int RightSquareBracket=101;
    public static final int USINT=31;
    public static final int DWORD=21;
    public static final int FOR=63;
    public static final int RightParenthesis=88;
    public static final int ColonEqualsSign=72;
    public static final int RULE_WSTRING=110;
    public static final int END_FUNCTION=6;
    public static final int END_WHILE=10;
    public static final int DATE=37;
    public static final int NOT=67;
    public static final int LDATE=23;
    public static final int AND=61;
    public static final int NumberSign=85;
    public static final int REAL=42;
    public static final int AsteriskAsterisk=70;
    public static final int SINT=43;
    public static final int LREAL=24;
    public static final int WCHAR=32;
    public static final int RULE_STRING=109;
    public static final int INT=52;
    public static final int RULE_SL_COMMENT=112;
    public static final int RETURN=28;
    public static final int EqualsSign=98;
    public static final int OF=81;
    public static final int Colon=95;
    public static final int EOF=-1;
    public static final int LDT=53;
    public static final int Asterisk=89;
    public static final int MOD=66;
    public static final int OR=82;
    public static final int RULE_WS=113;
    public static final int RULE_EXT_INT=107;
    public static final int TIME=44;
    public static final int RULE_ANY_OTHER=114;
    public static final int UNTIL=46;
    public static final int BOOL=34;
    public static final int D_1=57;
    public static final int WHILE=47;
    public static final int RULE_NON_DECIMAL=104;

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

                if ( (LA4_0==CONTINUE||(LA4_0>=REPEAT && LA4_0<=RETURN)||LA4_0==WHILE||LA4_0==CASE||LA4_0==EXIT||LA4_0==FOR||LA4_0==IF||LA4_0==Semicolon||LA4_0==RULE_ID) ) {
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

                        if ( ((LA14_1>=LDATE_AND_TIME && LA14_1<=DATE_AND_TIME)||LA14_1==TIME_OF_DAY||LA14_1==WSTRING||(LA14_1>=STRING && LA14_1<=DWORD)||(LA14_1>=LDATE && LA14_1<=LWORD)||(LA14_1>=UDINT && LA14_1<=WCHAR)||(LA14_1>=BOOL && LA14_1<=DINT)||(LA14_1>=LINT && LA14_1<=UINT)||LA14_1==WORD||(LA14_1>=INT && LA14_1<=LDT)||LA14_1==TOD||LA14_1==DT||(LA14_1>=LD && LA14_1<=LT)||LA14_1==NOT||LA14_1==D||LA14_1==T||LA14_1==LeftParenthesis||LA14_1==PlusSign||LA14_1==HyphenMinus||LA14_1==RULE_BOOL_VALUES||(LA14_1>=RULE_NON_DECIMAL && LA14_1<=RULE_INT)||(LA14_1>=RULE_ID && LA14_1<=RULE_STRING)) ) {
                            alt14=1;
                        }
                        else if ( (LA14_1==Asterisk) ) {
                            alt14=2;
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

            if ( ((LA18_0>=LDATE_AND_TIME && LA18_0<=DATE_AND_TIME)||LA18_0==TIME_OF_DAY||LA18_0==WSTRING||(LA18_0>=STRING && LA18_0<=DWORD)||(LA18_0>=LDATE && LA18_0<=LWORD)||(LA18_0>=UDINT && LA18_0<=WCHAR)||(LA18_0>=BOOL && LA18_0<=DINT)||(LA18_0>=LINT && LA18_0<=UINT)||LA18_0==WORD||(LA18_0>=INT && LA18_0<=LDT)||LA18_0==TOD||LA18_0==DT||(LA18_0>=LD && LA18_0<=LT)||LA18_0==NOT||LA18_0==D||LA18_0==T||LA18_0==LeftParenthesis||LA18_0==PlusSign||LA18_0==HyphenMinus||LA18_0==RULE_BOOL_VALUES||(LA18_0>=RULE_NON_DECIMAL && LA18_0<=RULE_INT)||(LA18_0>=RULE_ID && LA18_0<=RULE_STRING)) ) {
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
    // InternalSTFunctionParser.g:892:1: ruleSTStatement returns [EObject current=null] : ( (this_STBranchStatement_0= ruleSTBranchStatement otherlv_1= Semicolon ) | (this_STLoopStatement_2= ruleSTLoopStatement otherlv_3= Semicolon ) | (this_STAssignmentStatement_4= ruleSTAssignmentStatement otherlv_5= Semicolon ) | ( () otherlv_7= Semicolon ) | ( () otherlv_9= RETURN otherlv_10= Semicolon ) | ( () otherlv_12= CONTINUE otherlv_13= Semicolon ) | ( () otherlv_15= EXIT otherlv_16= Semicolon ) ) ;
    public final EObject ruleSTStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        EObject this_STBranchStatement_0 = null;

        EObject this_STLoopStatement_2 = null;

        EObject this_STAssignmentStatement_4 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:898:2: ( ( (this_STBranchStatement_0= ruleSTBranchStatement otherlv_1= Semicolon ) | (this_STLoopStatement_2= ruleSTLoopStatement otherlv_3= Semicolon ) | (this_STAssignmentStatement_4= ruleSTAssignmentStatement otherlv_5= Semicolon ) | ( () otherlv_7= Semicolon ) | ( () otherlv_9= RETURN otherlv_10= Semicolon ) | ( () otherlv_12= CONTINUE otherlv_13= Semicolon ) | ( () otherlv_15= EXIT otherlv_16= Semicolon ) ) )
            // InternalSTFunctionParser.g:899:2: ( (this_STBranchStatement_0= ruleSTBranchStatement otherlv_1= Semicolon ) | (this_STLoopStatement_2= ruleSTLoopStatement otherlv_3= Semicolon ) | (this_STAssignmentStatement_4= ruleSTAssignmentStatement otherlv_5= Semicolon ) | ( () otherlv_7= Semicolon ) | ( () otherlv_9= RETURN otherlv_10= Semicolon ) | ( () otherlv_12= CONTINUE otherlv_13= Semicolon ) | ( () otherlv_15= EXIT otherlv_16= Semicolon ) )
            {
            // InternalSTFunctionParser.g:899:2: ( (this_STBranchStatement_0= ruleSTBranchStatement otherlv_1= Semicolon ) | (this_STLoopStatement_2= ruleSTLoopStatement otherlv_3= Semicolon ) | (this_STAssignmentStatement_4= ruleSTAssignmentStatement otherlv_5= Semicolon ) | ( () otherlv_7= Semicolon ) | ( () otherlv_9= RETURN otherlv_10= Semicolon ) | ( () otherlv_12= CONTINUE otherlv_13= Semicolon ) | ( () otherlv_15= EXIT otherlv_16= Semicolon ) )
            int alt21=7;
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
            case Semicolon:
                {
                alt21=4;
                }
                break;
            case RETURN:
                {
                alt21=5;
                }
                break;
            case CONTINUE:
                {
                alt21=6;
                }
                break;
            case EXIT:
                {
                alt21=7;
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
                    // InternalSTFunctionParser.g:900:3: (this_STBranchStatement_0= ruleSTBranchStatement otherlv_1= Semicolon )
                    {
                    // InternalSTFunctionParser.g:900:3: (this_STBranchStatement_0= ruleSTBranchStatement otherlv_1= Semicolon )
                    // InternalSTFunctionParser.g:901:4: this_STBranchStatement_0= ruleSTBranchStatement otherlv_1= Semicolon
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTStatementAccess().getSTBranchStatementParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_22);
                    this_STBranchStatement_0=ruleSTBranchStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_STBranchStatement_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    otherlv_1=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStatementAccess().getSemicolonKeyword_0_1());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:915:3: (this_STLoopStatement_2= ruleSTLoopStatement otherlv_3= Semicolon )
                    {
                    // InternalSTFunctionParser.g:915:3: (this_STLoopStatement_2= ruleSTLoopStatement otherlv_3= Semicolon )
                    // InternalSTFunctionParser.g:916:4: this_STLoopStatement_2= ruleSTLoopStatement otherlv_3= Semicolon
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTStatementAccess().getSTLoopStatementParserRuleCall_1_0());
                      			
                    }
                    pushFollow(FOLLOW_22);
                    this_STLoopStatement_2=ruleSTLoopStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_STLoopStatement_2;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    otherlv_3=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTStatementAccess().getSemicolonKeyword_1_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:930:3: (this_STAssignmentStatement_4= ruleSTAssignmentStatement otherlv_5= Semicolon )
                    {
                    // InternalSTFunctionParser.g:930:3: (this_STAssignmentStatement_4= ruleSTAssignmentStatement otherlv_5= Semicolon )
                    // InternalSTFunctionParser.g:931:4: this_STAssignmentStatement_4= ruleSTAssignmentStatement otherlv_5= Semicolon
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentStatementParserRuleCall_2_0());
                      			
                    }
                    pushFollow(FOLLOW_22);
                    this_STAssignmentStatement_4=ruleSTAssignmentStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_STAssignmentStatement_4;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    otherlv_5=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getSTStatementAccess().getSemicolonKeyword_2_1());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:945:3: ( () otherlv_7= Semicolon )
                    {
                    // InternalSTFunctionParser.g:945:3: ( () otherlv_7= Semicolon )
                    // InternalSTFunctionParser.g:946:4: () otherlv_7= Semicolon
                    {
                    // InternalSTFunctionParser.g:946:4: ()
                    // InternalSTFunctionParser.g:947:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTNopAction_3_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_7=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getSTStatementAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSTFunctionParser.g:959:3: ( () otherlv_9= RETURN otherlv_10= Semicolon )
                    {
                    // InternalSTFunctionParser.g:959:3: ( () otherlv_9= RETURN otherlv_10= Semicolon )
                    // InternalSTFunctionParser.g:960:4: () otherlv_9= RETURN otherlv_10= Semicolon
                    {
                    // InternalSTFunctionParser.g:960:4: ()
                    // InternalSTFunctionParser.g:961:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTReturnAction_4_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_9=(Token)match(input,RETURN,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getSTStatementAccess().getRETURNKeyword_4_1());
                      			
                    }
                    otherlv_10=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getSTStatementAccess().getSemicolonKeyword_4_2());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSTFunctionParser.g:977:3: ( () otherlv_12= CONTINUE otherlv_13= Semicolon )
                    {
                    // InternalSTFunctionParser.g:977:3: ( () otherlv_12= CONTINUE otherlv_13= Semicolon )
                    // InternalSTFunctionParser.g:978:4: () otherlv_12= CONTINUE otherlv_13= Semicolon
                    {
                    // InternalSTFunctionParser.g:978:4: ()
                    // InternalSTFunctionParser.g:979:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTContinueAction_5_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_12=(Token)match(input,CONTINUE,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_5_1());
                      			
                    }
                    otherlv_13=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getSTStatementAccess().getSemicolonKeyword_5_2());
                      			
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSTFunctionParser.g:995:3: ( () otherlv_15= EXIT otherlv_16= Semicolon )
                    {
                    // InternalSTFunctionParser.g:995:3: ( () otherlv_15= EXIT otherlv_16= Semicolon )
                    // InternalSTFunctionParser.g:996:4: () otherlv_15= EXIT otherlv_16= Semicolon
                    {
                    // InternalSTFunctionParser.g:996:4: ()
                    // InternalSTFunctionParser.g:997:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTStatementAccess().getSTExitAction_6_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_15=(Token)match(input,EXIT,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_15, grammarAccess.getSTStatementAccess().getEXITKeyword_6_1());
                      			
                    }
                    otherlv_16=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTStatementAccess().getSemicolonKeyword_6_2());
                      			
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
    // InternalSTFunctionParser.g:1016:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalSTFunctionParser.g:1016:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalSTFunctionParser.g:1017:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalSTFunctionParser.g:1023:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_rhs_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1029:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) ) ) )
            // InternalSTFunctionParser.g:1030:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) ) )
            {
            // InternalSTFunctionParser.g:1030:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:1031:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_rhs_2_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:1031:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTFunctionParser.g:1032:4: (otherlv_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:1032:4: (otherlv_0= RULE_ID )
            // InternalSTFunctionParser.g:1033:5: otherlv_0= RULE_ID
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

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTAssignmentStatementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1048:3: ( (lv_rhs_2_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1049:4: (lv_rhs_2_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1049:4: (lv_rhs_2_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1050:5: lv_rhs_2_0= ruleSTExpression
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


    // $ANTLR start "entryRuleSTBranchStatement"
    // InternalSTFunctionParser.g:1071:1: entryRuleSTBranchStatement returns [EObject current=null] : iv_ruleSTBranchStatement= ruleSTBranchStatement EOF ;
    public final EObject entryRuleSTBranchStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTBranchStatement = null;


        try {
            // InternalSTFunctionParser.g:1071:58: (iv_ruleSTBranchStatement= ruleSTBranchStatement EOF )
            // InternalSTFunctionParser.g:1072:2: iv_ruleSTBranchStatement= ruleSTBranchStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTBranchStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTBranchStatement=ruleSTBranchStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTBranchStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTBranchStatement"


    // $ANTLR start "ruleSTBranchStatement"
    // InternalSTFunctionParser.g:1078:1: ruleSTBranchStatement returns [EObject current=null] : (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement ) ;
    public final EObject ruleSTBranchStatement() throws RecognitionException {
        EObject current = null;

        EObject this_STIfStatment_0 = null;

        EObject this_STCaseStatement_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1084:2: ( (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement ) )
            // InternalSTFunctionParser.g:1085:2: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement )
            {
            // InternalSTFunctionParser.g:1085:2: (this_STIfStatment_0= ruleSTIfStatment | this_STCaseStatement_1= ruleSTCaseStatement )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==IF) ) {
                alt22=1;
            }
            else if ( (LA22_0==CASE) ) {
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
                    // InternalSTFunctionParser.g:1086:3: this_STIfStatment_0= ruleSTIfStatment
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTBranchStatementAccess().getSTIfStatmentParserRuleCall_0());
                      		
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
                    // InternalSTFunctionParser.g:1095:3: this_STCaseStatement_1= ruleSTCaseStatement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTBranchStatementAccess().getSTCaseStatementParserRuleCall_1());
                      		
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
    // $ANTLR end "ruleSTBranchStatement"


    // $ANTLR start "entryRuleSTIfStatment"
    // InternalSTFunctionParser.g:1107:1: entryRuleSTIfStatment returns [EObject current=null] : iv_ruleSTIfStatment= ruleSTIfStatment EOF ;
    public final EObject entryRuleSTIfStatment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatment = null;


        try {
            // InternalSTFunctionParser.g:1107:53: (iv_ruleSTIfStatment= ruleSTIfStatment EOF )
            // InternalSTFunctionParser.g:1108:2: iv_ruleSTIfStatment= ruleSTIfStatment EOF
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
    // InternalSTFunctionParser.g:1114:1: ruleSTIfStatment returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTFunctionParser.g:1120:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTFunctionParser.g:1121:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTFunctionParser.g:1121:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTFunctionParser.g:1122:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatmentAccess().getIFKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1126:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1127:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1127:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1128:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTFunctionParser.g:1149:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==CONTINUE||(LA23_0>=REPEAT && LA23_0<=RETURN)||LA23_0==WHILE||LA23_0==CASE||LA23_0==EXIT||LA23_0==FOR||LA23_0==IF||LA23_0==Semicolon||LA23_0==RULE_ID) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1150:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1150:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1151:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop23;
                }
            } while (true);

            // InternalSTFunctionParser.g:1168:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==ELSIF) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1169:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTFunctionParser.g:1169:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTFunctionParser.g:1170:5: lv_elseifs_4_0= ruleSTElseIfPart
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
            	    break loop24;
                }
            } while (true);

            // InternalSTFunctionParser.g:1187:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==ELSE) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSTFunctionParser.g:1188:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTFunctionParser.g:1188:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTFunctionParser.g:1189:5: lv_else_5_0= ruleSTElsePart
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
    // InternalSTFunctionParser.g:1214:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTFunctionParser.g:1214:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTFunctionParser.g:1215:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTFunctionParser.g:1221:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1227:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1228:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1228:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1229:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1233:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1234:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1234:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1235:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTFunctionParser.g:1256:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==CONTINUE||(LA26_0>=REPEAT && LA26_0<=RETURN)||LA26_0==WHILE||LA26_0==CASE||LA26_0==EXIT||LA26_0==FOR||LA26_0==IF||LA26_0==Semicolon||LA26_0==RULE_ID) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1257:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1257:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1258:5: lv_statements_3_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalSTFunctionParser.g:1279:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTFunctionParser.g:1279:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTFunctionParser.g:1280:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTFunctionParser.g:1286:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTFunctionParser.g:1292:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTFunctionParser.g:1293:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTFunctionParser.g:1293:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTFunctionParser.g:1294:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1298:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1299:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1299:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1300:5: lv_selector_1_0= ruleSTExpression
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
            // InternalSTFunctionParser.g:1321:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt27=0;
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>=LDATE_AND_TIME && LA27_0<=DATE_AND_TIME)||LA27_0==TIME_OF_DAY||LA27_0==WSTRING||(LA27_0>=STRING && LA27_0<=DWORD)||(LA27_0>=LDATE && LA27_0<=LWORD)||(LA27_0>=UDINT && LA27_0<=WCHAR)||(LA27_0>=BOOL && LA27_0<=DINT)||(LA27_0>=LINT && LA27_0<=UINT)||LA27_0==WORD||(LA27_0>=INT && LA27_0<=LDT)||LA27_0==TOD||LA27_0==DT||(LA27_0>=LD && LA27_0<=LT)||LA27_0==NOT||LA27_0==D||LA27_0==T||LA27_0==LeftParenthesis||LA27_0==PlusSign||LA27_0==HyphenMinus||LA27_0==RULE_BOOL_VALUES||(LA27_0>=RULE_NON_DECIMAL && LA27_0<=RULE_INT)||(LA27_0>=RULE_ID && LA27_0<=RULE_STRING)) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1322:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTFunctionParser.g:1322:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTFunctionParser.g:1323:5: lv_cases_3_0= ruleSTCaseCases
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
            	    if ( cnt27 >= 1 ) break loop27;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(27, input);
                        throw eee;
                }
                cnt27++;
            } while (true);

            // InternalSTFunctionParser.g:1340:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==ELSE) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalSTFunctionParser.g:1341:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTFunctionParser.g:1341:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTFunctionParser.g:1342:5: lv_else_4_0= ruleSTElsePart
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
    // InternalSTFunctionParser.g:1367:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTFunctionParser.g:1367:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTFunctionParser.g:1368:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTFunctionParser.g:1374:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1380:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1381:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1381:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1382:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTFunctionParser.g:1382:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1383:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1383:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1384:5: lv_conditions_0_0= ruleSTExpression
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

            // InternalSTFunctionParser.g:1401:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==Comma) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1402:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:1406:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTFunctionParser.g:1407:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTFunctionParser.g:1407:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTFunctionParser.g:1408:6: lv_conditions_2_0= ruleSTExpression
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
            	    break loop29;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1430:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop30:
            do {
                int alt30=2;
                alt30 = dfa30.predict(input);
                switch (alt30) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1431:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1435:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1436:5: lv_statements_4_0= ruleSTStatement
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTFunctionParser.g:1457:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTFunctionParser.g:1457:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTFunctionParser.g:1458:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTFunctionParser.g:1464:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1470:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:1471:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:1471:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:1472:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTFunctionParser.g:1472:3: ()
            // InternalSTFunctionParser.g:1473:4: 
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
            // InternalSTFunctionParser.g:1483:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==CONTINUE||(LA31_0>=REPEAT && LA31_0<=RETURN)||LA31_0==WHILE||LA31_0==CASE||LA31_0==EXIT||LA31_0==FOR||LA31_0==IF||LA31_0==Semicolon||LA31_0==RULE_ID) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1484:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1484:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1485:5: lv_statements_2_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTLoopStatement"
    // InternalSTFunctionParser.g:1506:1: entryRuleSTLoopStatement returns [EObject current=null] : iv_ruleSTLoopStatement= ruleSTLoopStatement EOF ;
    public final EObject entryRuleSTLoopStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLoopStatement = null;


        try {
            // InternalSTFunctionParser.g:1506:56: (iv_ruleSTLoopStatement= ruleSTLoopStatement EOF )
            // InternalSTFunctionParser.g:1507:2: iv_ruleSTLoopStatement= ruleSTLoopStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTLoopStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTLoopStatement=ruleSTLoopStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTLoopStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTLoopStatement"


    // $ANTLR start "ruleSTLoopStatement"
    // InternalSTFunctionParser.g:1513:1: ruleSTLoopStatement returns [EObject current=null] : (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement ) ;
    public final EObject ruleSTLoopStatement() throws RecognitionException {
        EObject current = null;

        EObject this_STForStatement_0 = null;

        EObject this_STWhileStatement_1 = null;

        EObject this_STRepeatStatement_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1519:2: ( (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement ) )
            // InternalSTFunctionParser.g:1520:2: (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement )
            {
            // InternalSTFunctionParser.g:1520:2: (this_STForStatement_0= ruleSTForStatement | this_STWhileStatement_1= ruleSTWhileStatement | this_STRepeatStatement_2= ruleSTRepeatStatement )
            int alt32=3;
            switch ( input.LA(1) ) {
            case FOR:
                {
                alt32=1;
                }
                break;
            case WHILE:
                {
                alt32=2;
                }
                break;
            case REPEAT:
                {
                alt32=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }

            switch (alt32) {
                case 1 :
                    // InternalSTFunctionParser.g:1521:3: this_STForStatement_0= ruleSTForStatement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLoopStatementAccess().getSTForStatementParserRuleCall_0());
                      		
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
                    // InternalSTFunctionParser.g:1530:3: this_STWhileStatement_1= ruleSTWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLoopStatementAccess().getSTWhileStatementParserRuleCall_1());
                      		
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
                    // InternalSTFunctionParser.g:1539:3: this_STRepeatStatement_2= ruleSTRepeatStatement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLoopStatementAccess().getSTRepeatStatementParserRuleCall_2());
                      		
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
    // $ANTLR end "ruleSTLoopStatement"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalSTFunctionParser.g:1551:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTFunctionParser.g:1551:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTFunctionParser.g:1552:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTFunctionParser.g:1558:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) ;
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
            // InternalSTFunctionParser.g:1564:2: ( (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR ) )
            // InternalSTFunctionParser.g:1565:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            {
            // InternalSTFunctionParser.g:1565:2: (otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR )
            // InternalSTFunctionParser.g:1566:3: otherlv_0= FOR ( (lv_for_1_0= ruleSTExpression ) ) otherlv_2= TO ( (lv_to_3_0= ruleSTExpression ) ) (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )? otherlv_6= DO ( (lv_statements_7_0= ruleSTStatement ) )* otherlv_8= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1570:3: ( (lv_for_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1571:4: (lv_for_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1571:4: (lv_for_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1572:5: lv_for_1_0= ruleSTExpression
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
            // InternalSTFunctionParser.g:1593:3: ( (lv_to_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1594:4: (lv_to_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1594:4: (lv_to_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1595:5: lv_to_3_0= ruleSTExpression
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

            // InternalSTFunctionParser.g:1612:3: (otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==BY) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalSTFunctionParser.g:1613:4: otherlv_4= BY ( (lv_by_5_0= ruleSTExpression ) )
                    {
                    otherlv_4=(Token)match(input,BY,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getBYKeyword_4_0());
                      			
                    }
                    // InternalSTFunctionParser.g:1617:4: ( (lv_by_5_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:1618:5: (lv_by_5_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:1618:5: (lv_by_5_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:1619:6: lv_by_5_0= ruleSTExpression
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
            // InternalSTFunctionParser.g:1641:3: ( (lv_statements_7_0= ruleSTStatement ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==CONTINUE||(LA34_0>=REPEAT && LA34_0<=RETURN)||LA34_0==WHILE||LA34_0==CASE||LA34_0==EXIT||LA34_0==FOR||LA34_0==IF||LA34_0==Semicolon||LA34_0==RULE_ID) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1642:4: (lv_statements_7_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1642:4: (lv_statements_7_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1643:5: lv_statements_7_0= ruleSTStatement
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
            	    break loop34;
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
    // InternalSTFunctionParser.g:1668:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTFunctionParser.g:1668:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTFunctionParser.g:1669:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTFunctionParser.g:1675:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1681:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTFunctionParser.g:1682:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTFunctionParser.g:1682:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTFunctionParser.g:1683:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1687:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1688:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1688:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1689:5: lv_condition_1_0= ruleSTExpression
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
            // InternalSTFunctionParser.g:1710:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==CONTINUE||(LA35_0>=REPEAT && LA35_0<=RETURN)||LA35_0==WHILE||LA35_0==CASE||LA35_0==EXIT||LA35_0==FOR||LA35_0==IF||LA35_0==Semicolon||LA35_0==RULE_ID) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1711:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1711:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1712:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop35;
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
    // InternalSTFunctionParser.g:1737:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTFunctionParser.g:1737:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTFunctionParser.g:1738:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTFunctionParser.g:1744:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1750:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTFunctionParser.g:1751:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTFunctionParser.g:1751:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTFunctionParser.g:1752:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1756:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==CONTINUE||(LA36_0>=REPEAT && LA36_0<=RETURN)||LA36_0==WHILE||LA36_0==CASE||LA36_0==EXIT||LA36_0==FOR||LA36_0==IF||LA36_0==Semicolon||LA36_0==RULE_ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1757:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:1757:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:1758:5: lv_statements_1_0= ruleSTStatement
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
            	    break loop36;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:1779:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1780:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1780:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1781:5: lv_condition_3_0= ruleSTExpression
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
    // InternalSTFunctionParser.g:1806:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTFunctionParser.g:1806:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTFunctionParser.g:1807:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTFunctionParser.g:1813:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1819:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTFunctionParser.g:1820:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTFunctionParser.g:1831:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTFunctionParser.g:1831:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTFunctionParser.g:1832:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTFunctionParser.g:1838:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1844:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTFunctionParser.g:1845:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:1845:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTFunctionParser.g:1846:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
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
            // InternalSTFunctionParser.g:1854:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==FullStopFullStop) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1855:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:1855:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTFunctionParser.g:1856:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:1856:5: ()
            	    // InternalSTFunctionParser.g:1857:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:1863:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTFunctionParser.g:1864:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTFunctionParser.g:1864:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTFunctionParser.g:1865:7: lv_op_2_0= ruleSubrangeOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:1883:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTFunctionParser.g:1884:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTFunctionParser.g:1884:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTFunctionParser.g:1885:6: lv_right_3_0= ruleSTOrExpression
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalSTFunctionParser.g:1907:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTFunctionParser.g:1907:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTFunctionParser.g:1908:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTFunctionParser.g:1914:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1920:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTFunctionParser.g:1921:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:1921:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTFunctionParser.g:1922:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
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
            // InternalSTFunctionParser.g:1930:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==OR) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1931:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:1931:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTFunctionParser.g:1932:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:1932:5: ()
            	    // InternalSTFunctionParser.g:1933:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:1939:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTFunctionParser.g:1940:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTFunctionParser.g:1940:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTFunctionParser.g:1941:7: lv_op_2_0= ruleOrOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:1959:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTFunctionParser.g:1960:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTFunctionParser.g:1960:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTFunctionParser.g:1961:6: lv_right_3_0= ruleSTXorExpression
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalSTFunctionParser.g:1983:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTFunctionParser.g:1983:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTFunctionParser.g:1984:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTFunctionParser.g:1990:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1996:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTFunctionParser.g:1997:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:1997:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTFunctionParser.g:1998:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
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
            // InternalSTFunctionParser.g:2006:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==XOR) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2007:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2007:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTFunctionParser.g:2008:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2008:5: ()
            	    // InternalSTFunctionParser.g:2009:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2015:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTFunctionParser.g:2016:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTFunctionParser.g:2016:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTFunctionParser.g:2017:7: lv_op_2_0= ruleXorOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:2035:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTFunctionParser.g:2036:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTFunctionParser.g:2036:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTFunctionParser.g:2037:6: lv_right_3_0= ruleSTAndExpression
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalSTFunctionParser.g:2059:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTFunctionParser.g:2059:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTFunctionParser.g:2060:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTFunctionParser.g:2066:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2072:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2073:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2073:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTFunctionParser.g:2074:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
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
            // InternalSTFunctionParser.g:2082:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==AND||LA40_0==Ampersand) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2083:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2083:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTFunctionParser.g:2084:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2084:5: ()
            	    // InternalSTFunctionParser.g:2085:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2091:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTFunctionParser.g:2092:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTFunctionParser.g:2092:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTFunctionParser.g:2093:7: lv_op_2_0= ruleAndOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:2111:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTFunctionParser.g:2112:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTFunctionParser.g:2112:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTFunctionParser.g:2113:6: lv_right_3_0= ruleSTEqualityExpression
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalSTFunctionParser.g:2135:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTFunctionParser.g:2135:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTFunctionParser.g:2136:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTFunctionParser.g:2142:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2148:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2149:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2149:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTFunctionParser.g:2150:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
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
            // InternalSTFunctionParser.g:2158:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==LessThanSignGreaterThanSign||LA41_0==EqualsSign) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2159:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2159:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTFunctionParser.g:2160:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2160:5: ()
            	    // InternalSTFunctionParser.g:2161:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2167:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTFunctionParser.g:2168:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTFunctionParser.g:2168:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTFunctionParser.g:2169:7: lv_op_2_0= ruleEqualityOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:2187:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTFunctionParser.g:2188:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTFunctionParser.g:2188:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTFunctionParser.g:2189:6: lv_right_3_0= ruleSTComparisonExpression
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalSTFunctionParser.g:2211:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTFunctionParser.g:2211:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTFunctionParser.g:2212:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTFunctionParser.g:2218:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2224:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2225:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2225:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTFunctionParser.g:2226:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
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
            // InternalSTFunctionParser.g:2234:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==LessThanSignEqualsSign||LA42_0==GreaterThanSignEqualsSign||LA42_0==LessThanSign||LA42_0==GreaterThanSign) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2235:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2235:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTFunctionParser.g:2236:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2236:5: ()
            	    // InternalSTFunctionParser.g:2237:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2243:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTFunctionParser.g:2244:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTFunctionParser.g:2244:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTFunctionParser.g:2245:7: lv_op_2_0= ruleCompareOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:2263:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTFunctionParser.g:2264:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTFunctionParser.g:2264:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTFunctionParser.g:2265:6: lv_right_3_0= ruleSTAddSubExpression
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
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalSTFunctionParser.g:2287:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTFunctionParser.g:2287:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTFunctionParser.g:2288:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTFunctionParser.g:2294:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2300:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2301:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2301:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTFunctionParser.g:2302:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
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
            // InternalSTFunctionParser.g:2310:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==PlusSign||LA43_0==HyphenMinus) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2311:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2311:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTFunctionParser.g:2312:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2312:5: ()
            	    // InternalSTFunctionParser.g:2313:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2319:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTFunctionParser.g:2320:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTFunctionParser.g:2320:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTFunctionParser.g:2321:7: lv_op_2_0= ruleAddSubOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:2339:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTFunctionParser.g:2340:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTFunctionParser.g:2340:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTFunctionParser.g:2341:6: lv_right_3_0= ruleSTMulDivModExpression
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
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalSTFunctionParser.g:2363:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTFunctionParser.g:2363:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTFunctionParser.g:2364:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTFunctionParser.g:2370:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2376:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2377:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2377:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTFunctionParser.g:2378:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
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
            // InternalSTFunctionParser.g:2386:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==MOD||LA44_0==Asterisk||LA44_0==Solidus) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2387:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2387:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTFunctionParser.g:2388:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2388:5: ()
            	    // InternalSTFunctionParser.g:2389:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2395:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTFunctionParser.g:2396:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTFunctionParser.g:2396:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTFunctionParser.g:2397:7: lv_op_2_0= ruleMulDivModOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:2415:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTFunctionParser.g:2416:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTFunctionParser.g:2416:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTFunctionParser.g:2417:6: lv_right_3_0= ruleSTPowerExpression
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
    // $ANTLR end "ruleSTMulDivModExpression"


    // $ANTLR start "entryRuleSTPowerExpression"
    // InternalSTFunctionParser.g:2439:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTFunctionParser.g:2439:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTFunctionParser.g:2440:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTFunctionParser.g:2446:1: ruleSTPowerExpression returns [EObject current=null] : (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSignumExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2452:2: ( (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* ) )
            // InternalSTFunctionParser.g:2453:2: (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:2453:2: (this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )* )
            // InternalSTFunctionParser.g:2454:3: this_STSignumExpression_0= ruleSTSignumExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )*
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
            // InternalSTFunctionParser.g:2462:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==AsteriskAsterisk) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2463:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTSignumExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:2463:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTFunctionParser.g:2464:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:2464:5: ()
            	    // InternalSTFunctionParser.g:2465:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2471:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTFunctionParser.g:2472:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTFunctionParser.g:2472:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTFunctionParser.g:2473:7: lv_op_2_0= rulePowerOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSTFunctionParser.g:2491:4: ( (lv_right_3_0= ruleSTSignumExpression ) )
            	    // InternalSTFunctionParser.g:2492:5: (lv_right_3_0= ruleSTSignumExpression )
            	    {
            	    // InternalSTFunctionParser.g:2492:5: (lv_right_3_0= ruleSTSignumExpression )
            	    // InternalSTFunctionParser.g:2493:6: lv_right_3_0= ruleSTSignumExpression
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTSignumExpression"
    // InternalSTFunctionParser.g:2515:1: entryRuleSTSignumExpression returns [EObject current=null] : iv_ruleSTSignumExpression= ruleSTSignumExpression EOF ;
    public final EObject entryRuleSTSignumExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignumExpression = null;


        try {
            // InternalSTFunctionParser.g:2515:59: (iv_ruleSTSignumExpression= ruleSTSignumExpression EOF )
            // InternalSTFunctionParser.g:2516:2: iv_ruleSTSignumExpression= ruleSTSignumExpression EOF
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
    // InternalSTFunctionParser.g:2522:1: ruleSTSignumExpression returns [EObject current=null] : (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) ) ;
    public final EObject ruleSTSignumExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STLiteralExpressions_0 = null;

        EObject this_STSelectionExpression_1 = null;

        Enumerator lv_op_3_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2528:2: ( (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) ) )
            // InternalSTFunctionParser.g:2529:2: (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) )
            {
            // InternalSTFunctionParser.g:2529:2: (this_STLiteralExpressions_0= ruleSTLiteralExpressions | this_STSelectionExpression_1= ruleSTSelectionExpression | ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) ) )
            int alt46=3;
            switch ( input.LA(1) ) {
            case NOT:
                {
                int LA46_1 = input.LA(2);

                if ( (LA46_1==LeftParenthesis||LA46_1==RULE_ID) ) {
                    alt46=3;
                }
                else if ( (LA46_1==DWORD||LA46_1==LREAL||LA46_1==LWORD||(LA46_1>=UDINT && LA46_1<=USINT)||(LA46_1>=BOOL && LA46_1<=BYTE)||LA46_1==DINT||LA46_1==LINT||(LA46_1>=REAL && LA46_1<=SINT)||LA46_1==UINT||LA46_1==WORD||LA46_1==INT||LA46_1==PlusSign||LA46_1==HyphenMinus||LA46_1==RULE_BOOL_VALUES||(LA46_1>=RULE_NON_DECIMAL && LA46_1<=RULE_INT)) ) {
                    alt46=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 46, 1, input);

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
                alt46=1;
                }
                break;
            case PlusSign:
                {
                int LA46_3 = input.LA(2);

                if ( (LA46_3==RULE_INT) ) {
                    alt46=1;
                }
                else if ( (LA46_3==LeftParenthesis||LA46_3==RULE_ID) ) {
                    alt46=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 46, 3, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA46_4 = input.LA(2);

                if ( (LA46_4==LeftParenthesis||LA46_4==RULE_ID) ) {
                    alt46=3;
                }
                else if ( (LA46_4==RULE_INT) ) {
                    alt46=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 46, 4, input);

                    throw nvae;
                }
                }
                break;
            case LeftParenthesis:
            case RULE_ID:
                {
                alt46=2;
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
                    // InternalSTFunctionParser.g:2530:3: this_STLiteralExpressions_0= ruleSTLiteralExpressions
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
                    // InternalSTFunctionParser.g:2539:3: this_STSelectionExpression_1= ruleSTSelectionExpression
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
                    // InternalSTFunctionParser.g:2548:3: ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) )
                    {
                    // InternalSTFunctionParser.g:2548:3: ( () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) ) )
                    // InternalSTFunctionParser.g:2549:4: () ( (lv_op_3_0= ruleUnaryOperator ) ) ( (lv_expression_4_0= ruleSTSelectionExpression ) )
                    {
                    // InternalSTFunctionParser.g:2549:4: ()
                    // InternalSTFunctionParser.g:2550:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTSignumExpressionAccess().getSTUnaryExpressionAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:2556:4: ( (lv_op_3_0= ruleUnaryOperator ) )
                    // InternalSTFunctionParser.g:2557:5: (lv_op_3_0= ruleUnaryOperator )
                    {
                    // InternalSTFunctionParser.g:2557:5: (lv_op_3_0= ruleUnaryOperator )
                    // InternalSTFunctionParser.g:2558:6: lv_op_3_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTSignumExpressionAccess().getOpUnaryOperatorEnumRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_50);
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

                    // InternalSTFunctionParser.g:2575:4: ( (lv_expression_4_0= ruleSTSelectionExpression ) )
                    // InternalSTFunctionParser.g:2576:5: (lv_expression_4_0= ruleSTSelectionExpression )
                    {
                    // InternalSTFunctionParser.g:2576:5: (lv_expression_4_0= ruleSTSelectionExpression )
                    // InternalSTFunctionParser.g:2577:6: lv_expression_4_0= ruleSTSelectionExpression
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
    // InternalSTFunctionParser.g:2599:1: entryRuleSTSelectionExpression returns [EObject current=null] : iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF ;
    public final EObject entryRuleSTSelectionExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSelectionExpression = null;


        try {
            // InternalSTFunctionParser.g:2599:62: (iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF )
            // InternalSTFunctionParser.g:2600:2: iv_ruleSTSelectionExpression= ruleSTSelectionExpression EOF
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
    // InternalSTFunctionParser.g:2606:1: ruleSTSelectionExpression returns [EObject current=null] : (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* ) ;
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
            // InternalSTFunctionParser.g:2612:2: ( (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* ) )
            // InternalSTFunctionParser.g:2613:2: (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* )
            {
            // InternalSTFunctionParser.g:2613:2: (this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )* )
            // InternalSTFunctionParser.g:2614:3: this_STAtomicExpression_0= ruleSTAtomicExpression ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )*
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
            // InternalSTFunctionParser.g:2622:3: ( () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )? )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==FullStop||LA53_0==LeftSquareBracket) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2623:4: () ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) ) ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )? ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )?
            	    {
            	    // InternalSTFunctionParser.g:2623:4: ()
            	    // InternalSTFunctionParser.g:2624:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getSTSelectionExpressionAccess().getSTMemberSelectionReceiverAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }

            	    // InternalSTFunctionParser.g:2630:4: ( ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) ) | ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket ) )
            	    int alt48=2;
            	    int LA48_0 = input.LA(1);

            	    if ( (LA48_0==FullStop) ) {
            	        alt48=1;
            	    }
            	    else if ( (LA48_0==LeftSquareBracket) ) {
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
            	            // InternalSTFunctionParser.g:2631:5: ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) )
            	            {
            	            // InternalSTFunctionParser.g:2631:5: ( ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) ) )
            	            // InternalSTFunctionParser.g:2632:6: ( (lv_structAccess_2_0= FullStop ) ) ( (otherlv_3= RULE_ID ) )
            	            {
            	            // InternalSTFunctionParser.g:2632:6: ( (lv_structAccess_2_0= FullStop ) )
            	            // InternalSTFunctionParser.g:2633:7: (lv_structAccess_2_0= FullStop )
            	            {
            	            // InternalSTFunctionParser.g:2633:7: (lv_structAccess_2_0= FullStop )
            	            // InternalSTFunctionParser.g:2634:8: lv_structAccess_2_0= FullStop
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

            	            // InternalSTFunctionParser.g:2646:6: ( (otherlv_3= RULE_ID ) )
            	            // InternalSTFunctionParser.g:2647:7: (otherlv_3= RULE_ID )
            	            {
            	            // InternalSTFunctionParser.g:2647:7: (otherlv_3= RULE_ID )
            	            // InternalSTFunctionParser.g:2648:8: otherlv_3= RULE_ID
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
            	            // InternalSTFunctionParser.g:2661:5: ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket )
            	            {
            	            // InternalSTFunctionParser.g:2661:5: ( ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket )
            	            // InternalSTFunctionParser.g:2662:6: ( (lv_arrayAccess_4_0= LeftSquareBracket ) ) ( (lv_index_5_0= ruleSTExpression ) ) (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )* otherlv_8= RightSquareBracket
            	            {
            	            // InternalSTFunctionParser.g:2662:6: ( (lv_arrayAccess_4_0= LeftSquareBracket ) )
            	            // InternalSTFunctionParser.g:2663:7: (lv_arrayAccess_4_0= LeftSquareBracket )
            	            {
            	            // InternalSTFunctionParser.g:2663:7: (lv_arrayAccess_4_0= LeftSquareBracket )
            	            // InternalSTFunctionParser.g:2664:8: lv_arrayAccess_4_0= LeftSquareBracket
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

            	            // InternalSTFunctionParser.g:2676:6: ( (lv_index_5_0= ruleSTExpression ) )
            	            // InternalSTFunctionParser.g:2677:7: (lv_index_5_0= ruleSTExpression )
            	            {
            	            // InternalSTFunctionParser.g:2677:7: (lv_index_5_0= ruleSTExpression )
            	            // InternalSTFunctionParser.g:2678:8: lv_index_5_0= ruleSTExpression
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

            	            // InternalSTFunctionParser.g:2695:6: (otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) ) )*
            	            loop47:
            	            do {
            	                int alt47=2;
            	                int LA47_0 = input.LA(1);

            	                if ( (LA47_0==Comma) ) {
            	                    alt47=1;
            	                }


            	                switch (alt47) {
            	            	case 1 :
            	            	    // InternalSTFunctionParser.g:2696:7: otherlv_6= Comma ( (lv_index_7_0= ruleSTExpression ) )
            	            	    {
            	            	    otherlv_6=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
            	            	    if ( state.backtracking==0 ) {

            	            	      							newLeafNode(otherlv_6, grammarAccess.getSTSelectionExpressionAccess().getCommaKeyword_1_1_1_2_0());
            	            	      						
            	            	    }
            	            	    // InternalSTFunctionParser.g:2700:7: ( (lv_index_7_0= ruleSTExpression ) )
            	            	    // InternalSTFunctionParser.g:2701:8: (lv_index_7_0= ruleSTExpression )
            	            	    {
            	            	    // InternalSTFunctionParser.g:2701:8: (lv_index_7_0= ruleSTExpression )
            	            	    // InternalSTFunctionParser.g:2702:9: lv_index_7_0= ruleSTExpression
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
            	            	    break loop47;
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

            	    // InternalSTFunctionParser.g:2726:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )?
            	    int alt51=2;
            	    alt51 = dfa51.predict(input);
            	    switch (alt51) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:2727:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis
            	            {
            	            // InternalSTFunctionParser.g:2727:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) )
            	            // InternalSTFunctionParser.g:2728:6: ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis )
            	            {
            	            // InternalSTFunctionParser.g:2732:6: (lv_poeInvocation_9_0= LeftParenthesis )
            	            // InternalSTFunctionParser.g:2733:7: lv_poeInvocation_9_0= LeftParenthesis
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

            	            // InternalSTFunctionParser.g:2745:5: ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )?
            	            int alt50=2;
            	            int LA50_0 = input.LA(1);

            	            if ( ((LA50_0>=LDATE_AND_TIME && LA50_0<=DATE_AND_TIME)||LA50_0==TIME_OF_DAY||LA50_0==WSTRING||(LA50_0>=STRING && LA50_0<=DWORD)||(LA50_0>=LDATE && LA50_0<=LWORD)||(LA50_0>=UDINT && LA50_0<=WCHAR)||(LA50_0>=BOOL && LA50_0<=DINT)||(LA50_0>=LINT && LA50_0<=UINT)||LA50_0==WORD||(LA50_0>=INT && LA50_0<=LDT)||LA50_0==TOD||LA50_0==DT||(LA50_0>=LD && LA50_0<=LT)||LA50_0==NOT||LA50_0==D||LA50_0==T||LA50_0==LeftParenthesis||LA50_0==PlusSign||LA50_0==HyphenMinus||LA50_0==RULE_BOOL_VALUES||(LA50_0>=RULE_NON_DECIMAL && LA50_0<=RULE_INT)||(LA50_0>=RULE_ID && LA50_0<=RULE_STRING)) ) {
            	                alt50=1;
            	            }
            	            switch (alt50) {
            	                case 1 :
            	                    // InternalSTFunctionParser.g:2746:6: ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )*
            	                    {
            	                    // InternalSTFunctionParser.g:2746:6: ( (lv_parameters_10_0= ruleSTExpression ) )
            	                    // InternalSTFunctionParser.g:2747:7: (lv_parameters_10_0= ruleSTExpression )
            	                    {
            	                    // InternalSTFunctionParser.g:2747:7: (lv_parameters_10_0= ruleSTExpression )
            	                    // InternalSTFunctionParser.g:2748:8: lv_parameters_10_0= ruleSTExpression
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

            	                    // InternalSTFunctionParser.g:2765:6: (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )*
            	                    loop49:
            	                    do {
            	                        int alt49=2;
            	                        int LA49_0 = input.LA(1);

            	                        if ( (LA49_0==Comma) ) {
            	                            alt49=1;
            	                        }


            	                        switch (alt49) {
            	                    	case 1 :
            	                    	    // InternalSTFunctionParser.g:2766:7: otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) )
            	                    	    {
            	                    	    otherlv_11=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
            	                    	    if ( state.backtracking==0 ) {

            	                    	      							newLeafNode(otherlv_11, grammarAccess.getSTSelectionExpressionAccess().getCommaKeyword_1_2_1_1_0());
            	                    	      						
            	                    	    }
            	                    	    // InternalSTFunctionParser.g:2770:7: ( (lv_parameters_12_0= ruleSTExpression ) )
            	                    	    // InternalSTFunctionParser.g:2771:8: (lv_parameters_12_0= ruleSTExpression )
            	                    	    {
            	                    	    // InternalSTFunctionParser.g:2771:8: (lv_parameters_12_0= ruleSTExpression )
            	                    	    // InternalSTFunctionParser.g:2772:9: lv_parameters_12_0= ruleSTExpression
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
            	                    	    break loop49;
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

            	    // InternalSTFunctionParser.g:2796:4: ( ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess ) )?
            	    int alt52=2;
            	    int LA52_0 = input.LA(1);

            	    if ( (LA52_0==L) && (synpred3_InternalSTFunctionParser())) {
            	        alt52=1;
            	    }
            	    else if ( (LA52_0==D_1) && (synpred3_InternalSTFunctionParser())) {
            	        alt52=1;
            	    }
            	    else if ( (LA52_0==W) && (synpred3_InternalSTFunctionParser())) {
            	        alt52=1;
            	    }
            	    else if ( (LA52_0==B) && (synpred3_InternalSTFunctionParser())) {
            	        alt52=1;
            	    }
            	    else if ( (LA52_0==X) && (synpred3_InternalSTFunctionParser())) {
            	        alt52=1;
            	    }
            	    else if ( (LA52_0==FullStop) ) {
            	        int LA52_6 = input.LA(2);

            	        if ( (LA52_6==RULE_INT) && (synpred3_InternalSTFunctionParser())) {
            	            alt52=1;
            	        }
            	    }
            	    switch (alt52) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:2797:5: ( ( ruleMultibitPartialAccess ) )=> (lv_bitaccessor_14_0= ruleMultibitPartialAccess )
            	            {
            	            // InternalSTFunctionParser.g:2801:5: (lv_bitaccessor_14_0= ruleMultibitPartialAccess )
            	            // InternalSTFunctionParser.g:2802:6: lv_bitaccessor_14_0= ruleMultibitPartialAccess
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
            	    break loop53;
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
    // InternalSTFunctionParser.g:2824:1: entryRuleMultibitPartialAccess returns [EObject current=null] : iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF ;
    public final EObject entryRuleMultibitPartialAccess() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultibitPartialAccess = null;


        try {
            // InternalSTFunctionParser.g:2824:62: (iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF )
            // InternalSTFunctionParser.g:2825:2: iv_ruleMultibitPartialAccess= ruleMultibitPartialAccess EOF
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
    // InternalSTFunctionParser.g:2831:1: ruleMultibitPartialAccess returns [EObject current=null] : ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) ) ;
    public final EObject ruleMultibitPartialAccess() throws RecognitionException {
        EObject current = null;

        Token lv_index_1_0=null;
        Enumerator lv_accessSpecifier_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2837:2: ( ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) ) )
            // InternalSTFunctionParser.g:2838:2: ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) )
            {
            // InternalSTFunctionParser.g:2838:2: ( ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) ) )
            // InternalSTFunctionParser.g:2839:3: ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) ) ( (lv_index_1_0= RULE_INT ) )
            {
            // InternalSTFunctionParser.g:2839:3: ( (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier ) )
            // InternalSTFunctionParser.g:2840:4: (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier )
            {
            // InternalSTFunctionParser.g:2840:4: (lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier )
            // InternalSTFunctionParser.g:2841:5: lv_accessSpecifier_0_0= ruleMultiBitAccessSpecifier
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

            // InternalSTFunctionParser.g:2858:3: ( (lv_index_1_0= RULE_INT ) )
            // InternalSTFunctionParser.g:2859:4: (lv_index_1_0= RULE_INT )
            {
            // InternalSTFunctionParser.g:2859:4: (lv_index_1_0= RULE_INT )
            // InternalSTFunctionParser.g:2860:5: lv_index_1_0= RULE_INT
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
    // InternalSTFunctionParser.g:2880:1: entryRuleSTAtomicExpression returns [EObject current=null] : iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF ;
    public final EObject entryRuleSTAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAtomicExpression = null;


        try {
            // InternalSTFunctionParser.g:2880:59: (iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF )
            // InternalSTFunctionParser.g:2881:2: iv_ruleSTAtomicExpression= ruleSTAtomicExpression EOF
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
    // InternalSTFunctionParser.g:2887:1: ruleSTAtomicExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) ) ;
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
            // InternalSTFunctionParser.g:2893:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) ) )
            // InternalSTFunctionParser.g:2894:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) )
            {
            // InternalSTFunctionParser.g:2894:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? ) )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==LeftParenthesis) ) {
                alt59=1;
            }
            else if ( (LA59_0==RULE_ID) ) {
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
                    // InternalSTFunctionParser.g:2895:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTFunctionParser.g:2895:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTFunctionParser.g:2896:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
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
                    // InternalSTFunctionParser.g:2914:3: ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? )
                    {
                    // InternalSTFunctionParser.g:2914:3: ( () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )? )
                    // InternalSTFunctionParser.g:2915:4: () ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )? ( (otherlv_6= RULE_ID ) ) ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )? ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?
                    {
                    // InternalSTFunctionParser.g:2915:4: ()
                    // InternalSTFunctionParser.g:2916:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTAtomicExpressionAccess().getSTSymbolAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:2922:4: ( ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==RULE_ID) ) {
                        int LA54_1 = input.LA(2);

                        if ( (LA54_1==NumberSign) ) {
                            alt54=1;
                        }
                    }
                    switch (alt54) {
                        case 1 :
                            // InternalSTFunctionParser.g:2923:5: ( (otherlv_4= RULE_ID ) ) otherlv_5= NumberSign
                            {
                            // InternalSTFunctionParser.g:2923:5: ( (otherlv_4= RULE_ID ) )
                            // InternalSTFunctionParser.g:2924:6: (otherlv_4= RULE_ID )
                            {
                            // InternalSTFunctionParser.g:2924:6: (otherlv_4= RULE_ID )
                            // InternalSTFunctionParser.g:2925:7: otherlv_4= RULE_ID
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

                    // InternalSTFunctionParser.g:2941:4: ( (otherlv_6= RULE_ID ) )
                    // InternalSTFunctionParser.g:2942:5: (otherlv_6= RULE_ID )
                    {
                    // InternalSTFunctionParser.g:2942:5: (otherlv_6= RULE_ID )
                    // InternalSTFunctionParser.g:2943:6: otherlv_6= RULE_ID
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

                    // InternalSTFunctionParser.g:2954:4: ( (lv_bitaccessor_7_0= ruleMultibitPartialAccess ) )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( ((LA55_0>=B && LA55_0<=X)) ) {
                        alt55=1;
                    }
                    else if ( (LA55_0==FullStop) ) {
                        int LA55_2 = input.LA(2);

                        if ( (LA55_2==RULE_INT) ) {
                            alt55=1;
                        }
                    }
                    switch (alt55) {
                        case 1 :
                            // InternalSTFunctionParser.g:2955:5: (lv_bitaccessor_7_0= ruleMultibitPartialAccess )
                            {
                            // InternalSTFunctionParser.g:2955:5: (lv_bitaccessor_7_0= ruleMultibitPartialAccess )
                            // InternalSTFunctionParser.g:2956:6: lv_bitaccessor_7_0= ruleMultibitPartialAccess
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

                    // InternalSTFunctionParser.g:2973:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?
                    int alt58=2;
                    alt58 = dfa58.predict(input);
                    switch (alt58) {
                        case 1 :
                            // InternalSTFunctionParser.g:2974:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis
                            {
                            // InternalSTFunctionParser.g:2974:5: ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) )
                            // InternalSTFunctionParser.g:2975:6: ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis )
                            {
                            // InternalSTFunctionParser.g:2979:6: (lv_poeInvocation_8_0= LeftParenthesis )
                            // InternalSTFunctionParser.g:2980:7: lv_poeInvocation_8_0= LeftParenthesis
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

                            // InternalSTFunctionParser.g:2992:5: ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )?
                            int alt57=2;
                            int LA57_0 = input.LA(1);

                            if ( ((LA57_0>=LDATE_AND_TIME && LA57_0<=DATE_AND_TIME)||LA57_0==TIME_OF_DAY||LA57_0==WSTRING||(LA57_0>=STRING && LA57_0<=DWORD)||(LA57_0>=LDATE && LA57_0<=LWORD)||(LA57_0>=UDINT && LA57_0<=WCHAR)||(LA57_0>=BOOL && LA57_0<=DINT)||(LA57_0>=LINT && LA57_0<=UINT)||LA57_0==WORD||(LA57_0>=INT && LA57_0<=LDT)||LA57_0==TOD||LA57_0==DT||(LA57_0>=LD && LA57_0<=LT)||LA57_0==NOT||LA57_0==D||LA57_0==T||LA57_0==LeftParenthesis||LA57_0==PlusSign||LA57_0==HyphenMinus||LA57_0==RULE_BOOL_VALUES||(LA57_0>=RULE_NON_DECIMAL && LA57_0<=RULE_INT)||(LA57_0>=RULE_ID && LA57_0<=RULE_STRING)) ) {
                                alt57=1;
                            }
                            switch (alt57) {
                                case 1 :
                                    // InternalSTFunctionParser.g:2993:6: ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )*
                                    {
                                    // InternalSTFunctionParser.g:2993:6: ( (lv_parameters_9_0= ruleSTExpression ) )
                                    // InternalSTFunctionParser.g:2994:7: (lv_parameters_9_0= ruleSTExpression )
                                    {
                                    // InternalSTFunctionParser.g:2994:7: (lv_parameters_9_0= ruleSTExpression )
                                    // InternalSTFunctionParser.g:2995:8: lv_parameters_9_0= ruleSTExpression
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

                                    // InternalSTFunctionParser.g:3012:6: (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )*
                                    loop56:
                                    do {
                                        int alt56=2;
                                        int LA56_0 = input.LA(1);

                                        if ( (LA56_0==Comma) ) {
                                            alt56=1;
                                        }


                                        switch (alt56) {
                                    	case 1 :
                                    	    // InternalSTFunctionParser.g:3013:7: otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) )
                                    	    {
                                    	    otherlv_10=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
                                    	    if ( state.backtracking==0 ) {

                                    	      							newLeafNode(otherlv_10, grammarAccess.getSTAtomicExpressionAccess().getCommaKeyword_1_4_1_1_0());
                                    	      						
                                    	    }
                                    	    // InternalSTFunctionParser.g:3017:7: ( (lv_parameters_11_0= ruleSTExpression ) )
                                    	    // InternalSTFunctionParser.g:3018:8: (lv_parameters_11_0= ruleSTExpression )
                                    	    {
                                    	    // InternalSTFunctionParser.g:3018:8: (lv_parameters_11_0= ruleSTExpression )
                                    	    // InternalSTFunctionParser.g:3019:9: lv_parameters_11_0= ruleSTExpression
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
                                    	    break loop56;
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
    // InternalSTFunctionParser.g:3048:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTFunctionParser.g:3048:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTFunctionParser.g:3049:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTFunctionParser.g:3055:1: ruleSTLiteralExpressions returns [EObject current=null] : ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) ) ;
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
            // InternalSTFunctionParser.g:3061:2: ( ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) ) )
            // InternalSTFunctionParser.g:3062:2: ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) )
            {
            // InternalSTFunctionParser.g:3062:2: ( ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) ) | ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) ) | ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) ) | ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) ) | ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) ) | ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) ) | ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) ) )
            int alt60=7;
            switch ( input.LA(1) ) {
            case NOT:
                {
                int LA60_1 = input.LA(2);

                if ( (LA60_1==BOOL||LA60_1==RULE_BOOL_VALUES) ) {
                    alt60=1;
                }
                else if ( (LA60_1==DWORD||LA60_1==LREAL||LA60_1==LWORD||(LA60_1>=UDINT && LA60_1<=USINT)||LA60_1==BYTE||LA60_1==DINT||LA60_1==LINT||(LA60_1>=REAL && LA60_1<=SINT)||LA60_1==UINT||LA60_1==WORD||LA60_1==INT||LA60_1==PlusSign||LA60_1==HyphenMinus||(LA60_1>=RULE_NON_DECIMAL && LA60_1<=RULE_INT)) ) {
                    alt60=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 60, 1, input);

                    throw nvae;
                }
                }
                break;
            case BOOL:
            case RULE_BOOL_VALUES:
                {
                alt60=1;
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
                alt60=2;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt60=3;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt60=4;
                }
                break;
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt60=5;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt60=6;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt60=7;
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
                    // InternalSTFunctionParser.g:3063:3: ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3063:3: ( () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3064:4: () ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3064:4: ()
                    // InternalSTFunctionParser.g:3065:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTBoolLiteralAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3071:4: ( (lv_boolLiteral_1_0= ruleBOOL_LITERAL ) )
                    // InternalSTFunctionParser.g:3072:5: (lv_boolLiteral_1_0= ruleBOOL_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3072:5: (lv_boolLiteral_1_0= ruleBOOL_LITERAL )
                    // InternalSTFunctionParser.g:3073:6: lv_boolLiteral_1_0= ruleBOOL_LITERAL
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
                    // InternalSTFunctionParser.g:3092:3: ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3092:3: ( () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3093:4: () ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3093:4: ()
                    // InternalSTFunctionParser.g:3094:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTNumericLiteralAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3100:4: ( (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL ) )
                    // InternalSTFunctionParser.g:3101:5: (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3101:5: (lv_numericLiteral_3_0= ruleNUMERIC_LITERAL )
                    // InternalSTFunctionParser.g:3102:6: lv_numericLiteral_3_0= ruleNUMERIC_LITERAL
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
                    // InternalSTFunctionParser.g:3121:3: ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3121:3: ( () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3122:4: () ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3122:4: ()
                    // InternalSTFunctionParser.g:3123:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTDateLiteralAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3129:4: ( (lv_dateLiteral_5_0= ruleDATE_LITERAL ) )
                    // InternalSTFunctionParser.g:3130:5: (lv_dateLiteral_5_0= ruleDATE_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3130:5: (lv_dateLiteral_5_0= ruleDATE_LITERAL )
                    // InternalSTFunctionParser.g:3131:6: lv_dateLiteral_5_0= ruleDATE_LITERAL
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
                    // InternalSTFunctionParser.g:3150:3: ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3150:3: ( () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3151:4: () ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3151:4: ()
                    // InternalSTFunctionParser.g:3152:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTTimeLiteralAction_3_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3158:4: ( (lv_timeLiteral_7_0= ruleTIME_LITERAL ) )
                    // InternalSTFunctionParser.g:3159:5: (lv_timeLiteral_7_0= ruleTIME_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3159:5: (lv_timeLiteral_7_0= ruleTIME_LITERAL )
                    // InternalSTFunctionParser.g:3160:6: lv_timeLiteral_7_0= ruleTIME_LITERAL
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
                    // InternalSTFunctionParser.g:3179:3: ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3179:3: ( () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3180:4: () ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3180:4: ()
                    // InternalSTFunctionParser.g:3181:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTTimeOfDayLiteralAction_4_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3187:4: ( (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL ) )
                    // InternalSTFunctionParser.g:3188:5: (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3188:5: (lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL )
                    // InternalSTFunctionParser.g:3189:6: lv_timeOfDayLiteral_9_0= ruleTIME_OF_DAY_LITERAL
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
                    // InternalSTFunctionParser.g:3208:3: ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3208:3: ( () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3209:4: () ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3209:4: ()
                    // InternalSTFunctionParser.g:3210:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTDateAndTimeLiteralAction_5_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3216:4: ( (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL ) )
                    // InternalSTFunctionParser.g:3217:5: (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3217:5: (lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL )
                    // InternalSTFunctionParser.g:3218:6: lv_timeLiteral_11_0= ruleDATE_AND_TIME_LITERAL
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
                    // InternalSTFunctionParser.g:3237:3: ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) )
                    {
                    // InternalSTFunctionParser.g:3237:3: ( () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) ) )
                    // InternalSTFunctionParser.g:3238:4: () ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) )
                    {
                    // InternalSTFunctionParser.g:3238:4: ()
                    // InternalSTFunctionParser.g:3239:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTLiteralExpressionsAccess().getSTStringLiteralAction_6_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3245:4: ( (lv_stringLiteral_13_0= ruleSTRING_LITERAL ) )
                    // InternalSTFunctionParser.g:3246:5: (lv_stringLiteral_13_0= ruleSTRING_LITERAL )
                    {
                    // InternalSTFunctionParser.g:3246:5: (lv_stringLiteral_13_0= ruleSTRING_LITERAL )
                    // InternalSTFunctionParser.g:3247:6: lv_stringLiteral_13_0= ruleSTRING_LITERAL
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
    // InternalSTFunctionParser.g:3269:1: entryRuleBOOL_LITERAL returns [EObject current=null] : iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF ;
    public final EObject entryRuleBOOL_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBOOL_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3269:53: (iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF )
            // InternalSTFunctionParser.g:3270:2: iv_ruleBOOL_LITERAL= ruleBOOL_LITERAL EOF
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
    // InternalSTFunctionParser.g:3276:1: ruleBOOL_LITERAL returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) ) ;
    public final EObject ruleBOOL_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token lv_keyWordValue_2_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:3282:2: ( ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) ) )
            // InternalSTFunctionParser.g:3283:2: ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) )
            {
            // InternalSTFunctionParser.g:3283:2: ( ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) ) )
            // InternalSTFunctionParser.g:3284:3: ( (lv_not_0_0= NOT ) )? (otherlv_1= BOOL )? ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) )
            {
            // InternalSTFunctionParser.g:3284:3: ( (lv_not_0_0= NOT ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==NOT) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalSTFunctionParser.g:3285:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTFunctionParser.g:3285:4: (lv_not_0_0= NOT )
                    // InternalSTFunctionParser.g:3286:5: lv_not_0_0= NOT
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

            // InternalSTFunctionParser.g:3298:3: (otherlv_1= BOOL )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==BOOL) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalSTFunctionParser.g:3299:4: otherlv_1= BOOL
                    {
                    otherlv_1=(Token)match(input,BOOL,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getBOOL_LITERALAccess().getBOOLKeyword_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:3304:3: ( (lv_keyWordValue_2_0= RULE_BOOL_VALUES ) )
            // InternalSTFunctionParser.g:3305:4: (lv_keyWordValue_2_0= RULE_BOOL_VALUES )
            {
            // InternalSTFunctionParser.g:3305:4: (lv_keyWordValue_2_0= RULE_BOOL_VALUES )
            // InternalSTFunctionParser.g:3306:5: lv_keyWordValue_2_0= RULE_BOOL_VALUES
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
    // InternalSTFunctionParser.g:3326:1: entryRuleNUMERIC_LITERAL returns [EObject current=null] : iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF ;
    public final EObject entryRuleNUMERIC_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNUMERIC_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3326:56: (iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF )
            // InternalSTFunctionParser.g:3327:2: iv_ruleNUMERIC_LITERAL= ruleNUMERIC_LITERAL EOF
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
    // InternalSTFunctionParser.g:3333:1: ruleNUMERIC_LITERAL returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) ) ;
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
            // InternalSTFunctionParser.g:3339:2: ( ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) ) )
            // InternalSTFunctionParser.g:3340:2: ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) )
            {
            // InternalSTFunctionParser.g:3340:2: ( ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) ) )
            // InternalSTFunctionParser.g:3341:3: ( (lv_not_0_0= NOT ) )? ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )? ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) )
            {
            // InternalSTFunctionParser.g:3341:3: ( (lv_not_0_0= NOT ) )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==NOT) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalSTFunctionParser.g:3342:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTFunctionParser.g:3342:4: (lv_not_0_0= NOT )
                    // InternalSTFunctionParser.g:3343:5: lv_not_0_0= NOT
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

            // InternalSTFunctionParser.g:3355:3: ( ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==DWORD||LA65_0==LREAL||LA65_0==LWORD||(LA65_0>=UDINT && LA65_0<=USINT)||LA65_0==BYTE||LA65_0==DINT||LA65_0==LINT||(LA65_0>=REAL && LA65_0<=SINT)||LA65_0==UINT||LA65_0==WORD||LA65_0==INT) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalSTFunctionParser.g:3356:4: ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) )
                    {
                    // InternalSTFunctionParser.g:3356:4: ( (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL ) )
                    // InternalSTFunctionParser.g:3357:5: (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL )
                    {
                    // InternalSTFunctionParser.g:3357:5: (lv_keyword_1_1= BYTE | lv_keyword_1_2= WORD | lv_keyword_1_3= DWORD | lv_keyword_1_4= LWORD | lv_keyword_1_5= SINT | lv_keyword_1_6= INT | lv_keyword_1_7= DINT | lv_keyword_1_8= LINT | lv_keyword_1_9= USINT | lv_keyword_1_10= UINT | lv_keyword_1_11= UDINT | lv_keyword_1_12= ULINT | lv_keyword_1_13= REAL | lv_keyword_1_14= LREAL )
                    int alt64=14;
                    switch ( input.LA(1) ) {
                    case BYTE:
                        {
                        alt64=1;
                        }
                        break;
                    case WORD:
                        {
                        alt64=2;
                        }
                        break;
                    case DWORD:
                        {
                        alt64=3;
                        }
                        break;
                    case LWORD:
                        {
                        alt64=4;
                        }
                        break;
                    case SINT:
                        {
                        alt64=5;
                        }
                        break;
                    case INT:
                        {
                        alt64=6;
                        }
                        break;
                    case DINT:
                        {
                        alt64=7;
                        }
                        break;
                    case LINT:
                        {
                        alt64=8;
                        }
                        break;
                    case USINT:
                        {
                        alt64=9;
                        }
                        break;
                    case UINT:
                        {
                        alt64=10;
                        }
                        break;
                    case UDINT:
                        {
                        alt64=11;
                        }
                        break;
                    case ULINT:
                        {
                        alt64=12;
                        }
                        break;
                    case REAL:
                        {
                        alt64=13;
                        }
                        break;
                    case LREAL:
                        {
                        alt64=14;
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
                            // InternalSTFunctionParser.g:3358:6: lv_keyword_1_1= BYTE
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
                            // InternalSTFunctionParser.g:3369:6: lv_keyword_1_2= WORD
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
                            // InternalSTFunctionParser.g:3380:6: lv_keyword_1_3= DWORD
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
                            // InternalSTFunctionParser.g:3391:6: lv_keyword_1_4= LWORD
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
                            // InternalSTFunctionParser.g:3402:6: lv_keyword_1_5= SINT
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
                            // InternalSTFunctionParser.g:3413:6: lv_keyword_1_6= INT
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
                            // InternalSTFunctionParser.g:3424:6: lv_keyword_1_7= DINT
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
                            // InternalSTFunctionParser.g:3435:6: lv_keyword_1_8= LINT
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
                            // InternalSTFunctionParser.g:3446:6: lv_keyword_1_9= USINT
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
                            // InternalSTFunctionParser.g:3457:6: lv_keyword_1_10= UINT
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
                            // InternalSTFunctionParser.g:3468:6: lv_keyword_1_11= UDINT
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
                            // InternalSTFunctionParser.g:3479:6: lv_keyword_1_12= ULINT
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
                            // InternalSTFunctionParser.g:3490:6: lv_keyword_1_13= REAL
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
                            // InternalSTFunctionParser.g:3501:6: lv_keyword_1_14= LREAL
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

            // InternalSTFunctionParser.g:3514:3: ( ( (lv_intValue_2_0= ruleINTEGER ) ) | ( (lv_realValue_3_0= ruleREAL ) ) | ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) ) )
            int alt66=3;
            switch ( input.LA(1) ) {
            case PlusSign:
                {
                int LA66_1 = input.LA(2);

                if ( (LA66_1==RULE_INT) ) {
                    int LA66_3 = input.LA(3);

                    if ( (LA66_3==EOF||LA66_3==END_REPEAT||LA66_3==THEN||LA66_3==AND||LA66_3==MOD||(LA66_3>=XOR && LA66_3<=FullStopFullStop)||(LA66_3>=LessThanSignEqualsSign && LA66_3<=GreaterThanSignEqualsSign)||LA66_3==BY||LA66_3==DO||(LA66_3>=OF && LA66_3<=OR)||LA66_3==TO||(LA66_3>=Ampersand && LA66_3<=HyphenMinus)||(LA66_3>=Solidus && LA66_3<=GreaterThanSign)||LA66_3==RightSquareBracket) ) {
                        alt66=1;
                    }
                    else if ( (LA66_3==FullStop) ) {
                        alt66=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 66, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 66, 1, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA66_2 = input.LA(2);

                if ( (LA66_2==RULE_INT) ) {
                    int LA66_3 = input.LA(3);

                    if ( (LA66_3==EOF||LA66_3==END_REPEAT||LA66_3==THEN||LA66_3==AND||LA66_3==MOD||(LA66_3>=XOR && LA66_3<=FullStopFullStop)||(LA66_3>=LessThanSignEqualsSign && LA66_3<=GreaterThanSignEqualsSign)||LA66_3==BY||LA66_3==DO||(LA66_3>=OF && LA66_3<=OR)||LA66_3==TO||(LA66_3>=Ampersand && LA66_3<=HyphenMinus)||(LA66_3>=Solidus && LA66_3<=GreaterThanSign)||LA66_3==RightSquareBracket) ) {
                        alt66=1;
                    }
                    else if ( (LA66_3==FullStop) ) {
                        alt66=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 66, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 66, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
                {
                int LA66_3 = input.LA(2);

                if ( (LA66_3==EOF||LA66_3==END_REPEAT||LA66_3==THEN||LA66_3==AND||LA66_3==MOD||(LA66_3>=XOR && LA66_3<=FullStopFullStop)||(LA66_3>=LessThanSignEqualsSign && LA66_3<=GreaterThanSignEqualsSign)||LA66_3==BY||LA66_3==DO||(LA66_3>=OF && LA66_3<=OR)||LA66_3==TO||(LA66_3>=Ampersand && LA66_3<=HyphenMinus)||(LA66_3>=Solidus && LA66_3<=GreaterThanSign)||LA66_3==RightSquareBracket) ) {
                    alt66=1;
                }
                else if ( (LA66_3==FullStop) ) {
                    alt66=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 66, 3, input);

                    throw nvae;
                }
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
                    // InternalSTFunctionParser.g:3515:4: ( (lv_intValue_2_0= ruleINTEGER ) )
                    {
                    // InternalSTFunctionParser.g:3515:4: ( (lv_intValue_2_0= ruleINTEGER ) )
                    // InternalSTFunctionParser.g:3516:5: (lv_intValue_2_0= ruleINTEGER )
                    {
                    // InternalSTFunctionParser.g:3516:5: (lv_intValue_2_0= ruleINTEGER )
                    // InternalSTFunctionParser.g:3517:6: lv_intValue_2_0= ruleINTEGER
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
                    // InternalSTFunctionParser.g:3535:4: ( (lv_realValue_3_0= ruleREAL ) )
                    {
                    // InternalSTFunctionParser.g:3535:4: ( (lv_realValue_3_0= ruleREAL ) )
                    // InternalSTFunctionParser.g:3536:5: (lv_realValue_3_0= ruleREAL )
                    {
                    // InternalSTFunctionParser.g:3536:5: (lv_realValue_3_0= ruleREAL )
                    // InternalSTFunctionParser.g:3537:6: lv_realValue_3_0= ruleREAL
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
                    // InternalSTFunctionParser.g:3555:4: ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) )
                    {
                    // InternalSTFunctionParser.g:3555:4: ( (lv_hexValue_4_0= RULE_NON_DECIMAL ) )
                    // InternalSTFunctionParser.g:3556:5: (lv_hexValue_4_0= RULE_NON_DECIMAL )
                    {
                    // InternalSTFunctionParser.g:3556:5: (lv_hexValue_4_0= RULE_NON_DECIMAL )
                    // InternalSTFunctionParser.g:3557:6: lv_hexValue_4_0= RULE_NON_DECIMAL
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
    // InternalSTFunctionParser.g:3578:1: entryRuleDATE_LITERAL returns [EObject current=null] : iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF ;
    public final EObject entryRuleDATE_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDATE_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3578:53: (iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF )
            // InternalSTFunctionParser.g:3579:2: iv_ruleDATE_LITERAL= ruleDATE_LITERAL EOF
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
    // InternalSTFunctionParser.g:3585:1: ruleDATE_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) ) ;
    public final EObject ruleDATE_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3591:2: ( ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) ) )
            // InternalSTFunctionParser.g:3592:2: ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) )
            {
            // InternalSTFunctionParser.g:3592:2: ( ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) ) )
            // InternalSTFunctionParser.g:3593:3: ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) ) ( (lv_value_1_0= ruleDATE ) )
            {
            // InternalSTFunctionParser.g:3593:3: ( ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) ) )
            // InternalSTFunctionParser.g:3594:4: ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) )
            {
            // InternalSTFunctionParser.g:3594:4: ( (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD ) )
            // InternalSTFunctionParser.g:3595:5: (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD )
            {
            // InternalSTFunctionParser.g:3595:5: (lv_keyword_0_1= DATE | lv_keyword_0_2= LDATE | lv_keyword_0_3= D | lv_keyword_0_4= LD )
            int alt67=4;
            switch ( input.LA(1) ) {
            case DATE:
                {
                alt67=1;
                }
                break;
            case LDATE:
                {
                alt67=2;
                }
                break;
            case D:
                {
                alt67=3;
                }
                break;
            case LD:
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
                    // InternalSTFunctionParser.g:3596:6: lv_keyword_0_1= DATE
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
                    // InternalSTFunctionParser.g:3607:6: lv_keyword_0_2= LDATE
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
                    // InternalSTFunctionParser.g:3618:6: lv_keyword_0_3= D
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
                    // InternalSTFunctionParser.g:3629:6: lv_keyword_0_4= LD
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

            // InternalSTFunctionParser.g:3642:3: ( (lv_value_1_0= ruleDATE ) )
            // InternalSTFunctionParser.g:3643:4: (lv_value_1_0= ruleDATE )
            {
            // InternalSTFunctionParser.g:3643:4: (lv_value_1_0= ruleDATE )
            // InternalSTFunctionParser.g:3644:5: lv_value_1_0= ruleDATE
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
    // InternalSTFunctionParser.g:3665:1: entryRuleTIME_LITERAL returns [EObject current=null] : iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF ;
    public final EObject entryRuleTIME_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTIME_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3665:53: (iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF )
            // InternalSTFunctionParser.g:3666:2: iv_ruleTIME_LITERAL= ruleTIME_LITERAL EOF
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
    // InternalSTFunctionParser.g:3672:1: ruleTIME_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) ) ;
    public final EObject ruleTIME_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        Token lv_keyword_0_4=null;
        Token lv_value_1_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:3678:2: ( ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) ) )
            // InternalSTFunctionParser.g:3679:2: ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) )
            {
            // InternalSTFunctionParser.g:3679:2: ( ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) ) )
            // InternalSTFunctionParser.g:3680:3: ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) ) ( (lv_value_1_0= RULE_TIME ) )
            {
            // InternalSTFunctionParser.g:3680:3: ( ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) ) )
            // InternalSTFunctionParser.g:3681:4: ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) )
            {
            // InternalSTFunctionParser.g:3681:4: ( (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT ) )
            // InternalSTFunctionParser.g:3682:5: (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT )
            {
            // InternalSTFunctionParser.g:3682:5: (lv_keyword_0_1= TIME | lv_keyword_0_2= LTIME | lv_keyword_0_3= T | lv_keyword_0_4= LT )
            int alt68=4;
            switch ( input.LA(1) ) {
            case TIME:
                {
                alt68=1;
                }
                break;
            case LTIME:
                {
                alt68=2;
                }
                break;
            case T:
                {
                alt68=3;
                }
                break;
            case LT:
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
                    // InternalSTFunctionParser.g:3683:6: lv_keyword_0_1= TIME
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
                    // InternalSTFunctionParser.g:3694:6: lv_keyword_0_2= LTIME
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
                    // InternalSTFunctionParser.g:3705:6: lv_keyword_0_3= T
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
                    // InternalSTFunctionParser.g:3716:6: lv_keyword_0_4= LT
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

            // InternalSTFunctionParser.g:3729:3: ( (lv_value_1_0= RULE_TIME ) )
            // InternalSTFunctionParser.g:3730:4: (lv_value_1_0= RULE_TIME )
            {
            // InternalSTFunctionParser.g:3730:4: (lv_value_1_0= RULE_TIME )
            // InternalSTFunctionParser.g:3731:5: lv_value_1_0= RULE_TIME
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
    // InternalSTFunctionParser.g:3751:1: entryRuleTIME_OF_DAY_LITERAL returns [EObject current=null] : iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF ;
    public final EObject entryRuleTIME_OF_DAY_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTIME_OF_DAY_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3751:60: (iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF )
            // InternalSTFunctionParser.g:3752:2: iv_ruleTIME_OF_DAY_LITERAL= ruleTIME_OF_DAY_LITERAL EOF
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
    // InternalSTFunctionParser.g:3758:1: ruleTIME_OF_DAY_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) ) ;
    public final EObject ruleTIME_OF_DAY_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_1=null;
        Token lv_keyword_0_2=null;
        Token lv_keyword_0_3=null;
        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3764:2: ( ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) ) )
            // InternalSTFunctionParser.g:3765:2: ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) )
            {
            // InternalSTFunctionParser.g:3765:2: ( ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) ) )
            // InternalSTFunctionParser.g:3766:3: ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) ) ( (lv_value_1_0= ruleTIME_OF_DAY ) )
            {
            // InternalSTFunctionParser.g:3766:3: ( ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) ) )
            // InternalSTFunctionParser.g:3767:4: ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) )
            {
            // InternalSTFunctionParser.g:3767:4: ( (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD ) )
            // InternalSTFunctionParser.g:3768:5: (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD )
            {
            // InternalSTFunctionParser.g:3768:5: (lv_keyword_0_1= TIME_OF_DAY | lv_keyword_0_2= TOD | lv_keyword_0_3= LTOD )
            int alt69=3;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt69=1;
                }
                break;
            case TOD:
                {
                alt69=2;
                }
                break;
            case LTOD:
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
                    // InternalSTFunctionParser.g:3769:6: lv_keyword_0_1= TIME_OF_DAY
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
                    // InternalSTFunctionParser.g:3780:6: lv_keyword_0_2= TOD
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
                    // InternalSTFunctionParser.g:3791:6: lv_keyword_0_3= LTOD
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

            // InternalSTFunctionParser.g:3804:3: ( (lv_value_1_0= ruleTIME_OF_DAY ) )
            // InternalSTFunctionParser.g:3805:4: (lv_value_1_0= ruleTIME_OF_DAY )
            {
            // InternalSTFunctionParser.g:3805:4: (lv_value_1_0= ruleTIME_OF_DAY )
            // InternalSTFunctionParser.g:3806:5: lv_value_1_0= ruleTIME_OF_DAY
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
    // InternalSTFunctionParser.g:3827:1: entryRuleDATE_AND_TIME_LITERAL returns [EObject current=null] : iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF ;
    public final EObject entryRuleDATE_AND_TIME_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDATE_AND_TIME_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3827:62: (iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF )
            // InternalSTFunctionParser.g:3828:2: iv_ruleDATE_AND_TIME_LITERAL= ruleDATE_AND_TIME_LITERAL EOF
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
    // InternalSTFunctionParser.g:3834:1: ruleDATE_AND_TIME_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) ) ;
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
            // InternalSTFunctionParser.g:3840:2: ( ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) ) )
            // InternalSTFunctionParser.g:3841:2: ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) )
            {
            // InternalSTFunctionParser.g:3841:2: ( ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) ) )
            // InternalSTFunctionParser.g:3842:3: ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) ) ( (lv_dateValue_1_0= ruleDATE ) ) otherlv_2= HyphenMinus ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) )
            {
            // InternalSTFunctionParser.g:3842:3: ( ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) ) )
            // InternalSTFunctionParser.g:3843:4: ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) )
            {
            // InternalSTFunctionParser.g:3843:4: ( (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT ) )
            // InternalSTFunctionParser.g:3844:5: (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT )
            {
            // InternalSTFunctionParser.g:3844:5: (lv_keyword_0_1= DATE_AND_TIME | lv_keyword_0_2= LDATE_AND_TIME | lv_keyword_0_3= DT | lv_keyword_0_4= LDT )
            int alt70=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt70=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt70=2;
                }
                break;
            case DT:
                {
                alt70=3;
                }
                break;
            case LDT:
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
                    // InternalSTFunctionParser.g:3845:6: lv_keyword_0_1= DATE_AND_TIME
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
                    // InternalSTFunctionParser.g:3856:6: lv_keyword_0_2= LDATE_AND_TIME
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
                    // InternalSTFunctionParser.g:3867:6: lv_keyword_0_3= DT
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
                    // InternalSTFunctionParser.g:3878:6: lv_keyword_0_4= LDT
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

            // InternalSTFunctionParser.g:3891:3: ( (lv_dateValue_1_0= ruleDATE ) )
            // InternalSTFunctionParser.g:3892:4: (lv_dateValue_1_0= ruleDATE )
            {
            // InternalSTFunctionParser.g:3892:4: (lv_dateValue_1_0= ruleDATE )
            // InternalSTFunctionParser.g:3893:5: lv_dateValue_1_0= ruleDATE
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
            // InternalSTFunctionParser.g:3914:3: ( (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY ) )
            // InternalSTFunctionParser.g:3915:4: (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY )
            {
            // InternalSTFunctionParser.g:3915:4: (lv_timeOfDayValue_3_0= ruleTIME_OF_DAY )
            // InternalSTFunctionParser.g:3916:5: lv_timeOfDayValue_3_0= ruleTIME_OF_DAY
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
    // InternalSTFunctionParser.g:3937:1: entryRuleSTRING_LITERAL returns [EObject current=null] : iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF ;
    public final EObject entryRuleSTRING_LITERAL() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRING_LITERAL = null;


        try {
            // InternalSTFunctionParser.g:3937:55: (iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF )
            // InternalSTFunctionParser.g:3938:2: iv_ruleSTRING_LITERAL= ruleSTRING_LITERAL EOF
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
    // InternalSTFunctionParser.g:3944:1: ruleSTRING_LITERAL returns [EObject current=null] : ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTRING_LITERAL() throws RecognitionException {
        EObject current = null;

        Token lv_keyword_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_value_4_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:3950:2: ( ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) ) )
            // InternalSTFunctionParser.g:3951:2: ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) )
            {
            // InternalSTFunctionParser.g:3951:2: ( ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) ) )
            // InternalSTFunctionParser.g:3952:3: ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )? ( (lv_value_4_0= RULE_STRING ) )
            {
            // InternalSTFunctionParser.g:3952:3: ( ( (lv_keyword_0_0= STRING ) ) | otherlv_1= WSTRING | otherlv_2= CHAR | otherlv_3= WCHAR )?
            int alt71=5;
            switch ( input.LA(1) ) {
                case STRING:
                    {
                    alt71=1;
                    }
                    break;
                case WSTRING:
                    {
                    alt71=2;
                    }
                    break;
                case CHAR:
                    {
                    alt71=3;
                    }
                    break;
                case WCHAR:
                    {
                    alt71=4;
                    }
                    break;
            }

            switch (alt71) {
                case 1 :
                    // InternalSTFunctionParser.g:3953:4: ( (lv_keyword_0_0= STRING ) )
                    {
                    // InternalSTFunctionParser.g:3953:4: ( (lv_keyword_0_0= STRING ) )
                    // InternalSTFunctionParser.g:3954:5: (lv_keyword_0_0= STRING )
                    {
                    // InternalSTFunctionParser.g:3954:5: (lv_keyword_0_0= STRING )
                    // InternalSTFunctionParser.g:3955:6: lv_keyword_0_0= STRING
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
                    // InternalSTFunctionParser.g:3968:4: otherlv_1= WSTRING
                    {
                    otherlv_1=(Token)match(input,WSTRING,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTRING_LITERALAccess().getWSTRINGKeyword_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:3973:4: otherlv_2= CHAR
                    {
                    otherlv_2=(Token)match(input,CHAR,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTRING_LITERALAccess().getCHARKeyword_0_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:3978:4: otherlv_3= WCHAR
                    {
                    otherlv_3=(Token)match(input,WCHAR,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTRING_LITERALAccess().getWCHARKeyword_0_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:3983:3: ( (lv_value_4_0= RULE_STRING ) )
            // InternalSTFunctionParser.g:3984:4: (lv_value_4_0= RULE_STRING )
            {
            // InternalSTFunctionParser.g:3984:4: (lv_value_4_0= RULE_STRING )
            // InternalSTFunctionParser.g:3985:5: lv_value_4_0= RULE_STRING
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
    // InternalSTFunctionParser.g:4005:1: entryRuleINTEGER returns [String current=null] : iv_ruleINTEGER= ruleINTEGER EOF ;
    public final String entryRuleINTEGER() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINTEGER = null;


        try {
            // InternalSTFunctionParser.g:4005:47: (iv_ruleINTEGER= ruleINTEGER EOF )
            // InternalSTFunctionParser.g:4006:2: iv_ruleINTEGER= ruleINTEGER EOF
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
    // InternalSTFunctionParser.g:4012:1: ruleINTEGER returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleINTEGER() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4018:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT ) )
            // InternalSTFunctionParser.g:4019:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT )
            {
            // InternalSTFunctionParser.g:4019:2: ( (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT )
            // InternalSTFunctionParser.g:4020:3: (kw= PlusSign | kw= HyphenMinus )? this_INT_2= RULE_INT
            {
            // InternalSTFunctionParser.g:4020:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt72=3;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==PlusSign) ) {
                alt72=1;
            }
            else if ( (LA72_0==HyphenMinus) ) {
                alt72=2;
            }
            switch (alt72) {
                case 1 :
                    // InternalSTFunctionParser.g:4021:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getINTEGERAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4027:4: kw= HyphenMinus
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
    // InternalSTFunctionParser.g:4044:1: entryRuleREAL returns [String current=null] : iv_ruleREAL= ruleREAL EOF ;
    public final String entryRuleREAL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleREAL = null;


        try {
            // InternalSTFunctionParser.g:4044:44: (iv_ruleREAL= ruleREAL EOF )
            // InternalSTFunctionParser.g:4045:2: iv_ruleREAL= ruleREAL EOF
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
    // InternalSTFunctionParser.g:4051:1: ruleREAL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleREAL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_EXT_INT_2=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_INTEGER_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4057:2: ( (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) ) )
            // InternalSTFunctionParser.g:4058:2: (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) )
            {
            // InternalSTFunctionParser.g:4058:2: (this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT ) )
            // InternalSTFunctionParser.g:4059:3: this_INTEGER_0= ruleINTEGER kw= FullStop (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT )
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
            // InternalSTFunctionParser.g:4074:3: (this_EXT_INT_2= RULE_EXT_INT | this_INT_3= RULE_INT )
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==RULE_EXT_INT) ) {
                alt73=1;
            }
            else if ( (LA73_0==RULE_INT) ) {
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
                    // InternalSTFunctionParser.g:4075:4: this_EXT_INT_2= RULE_EXT_INT
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
                    // InternalSTFunctionParser.g:4083:4: this_INT_3= RULE_INT
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
    // InternalSTFunctionParser.g:4095:1: entryRuleDATE returns [String current=null] : iv_ruleDATE= ruleDATE EOF ;
    public final String entryRuleDATE() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDATE = null;


        try {
            // InternalSTFunctionParser.g:4095:44: (iv_ruleDATE= ruleDATE EOF )
            // InternalSTFunctionParser.g:4096:2: iv_ruleDATE= ruleDATE EOF
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
    // InternalSTFunctionParser.g:4102:1: ruleDATE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDATE() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4108:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTFunctionParser.g:4109:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTFunctionParser.g:4109:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTFunctionParser.g:4110:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
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
    // InternalSTFunctionParser.g:4145:1: entryRuleTIME_OF_DAY returns [String current=null] : iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF ;
    public final String entryRuleTIME_OF_DAY() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTIME_OF_DAY = null;


        try {
            // InternalSTFunctionParser.g:4145:51: (iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF )
            // InternalSTFunctionParser.g:4146:2: iv_ruleTIME_OF_DAY= ruleTIME_OF_DAY EOF
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
    // InternalSTFunctionParser.g:4152:1: ruleTIME_OF_DAY returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTIME_OF_DAY() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4158:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) )
            // InternalSTFunctionParser.g:4159:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            {
            // InternalSTFunctionParser.g:4159:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            // InternalSTFunctionParser.g:4160:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )?
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
            // InternalSTFunctionParser.g:4191:3: (kw= FullStop this_INT_6= RULE_INT )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==FullStop) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalSTFunctionParser.g:4192:4: kw= FullStop this_INT_6= RULE_INT
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


    // $ANTLR start "ruleSubrangeOperator"
    // InternalSTFunctionParser.g:4209:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4215:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTFunctionParser.g:4216:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTFunctionParser.g:4216:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTFunctionParser.g:4217:3: enumLiteral_0= FullStopFullStop
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
    // InternalSTFunctionParser.g:4226:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4232:2: ( (enumLiteral_0= OR ) )
            // InternalSTFunctionParser.g:4233:2: (enumLiteral_0= OR )
            {
            // InternalSTFunctionParser.g:4233:2: (enumLiteral_0= OR )
            // InternalSTFunctionParser.g:4234:3: enumLiteral_0= OR
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
    // InternalSTFunctionParser.g:4243:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4249:2: ( (enumLiteral_0= XOR ) )
            // InternalSTFunctionParser.g:4250:2: (enumLiteral_0= XOR )
            {
            // InternalSTFunctionParser.g:4250:2: (enumLiteral_0= XOR )
            // InternalSTFunctionParser.g:4251:3: enumLiteral_0= XOR
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
    // InternalSTFunctionParser.g:4260:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4266:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTFunctionParser.g:4267:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTFunctionParser.g:4267:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==AND) ) {
                alt75=1;
            }
            else if ( (LA75_0==Ampersand) ) {
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
                    // InternalSTFunctionParser.g:4268:3: (enumLiteral_0= AND )
                    {
                    // InternalSTFunctionParser.g:4268:3: (enumLiteral_0= AND )
                    // InternalSTFunctionParser.g:4269:4: enumLiteral_0= AND
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
                    // InternalSTFunctionParser.g:4276:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTFunctionParser.g:4276:3: (enumLiteral_1= Ampersand )
                    // InternalSTFunctionParser.g:4277:4: enumLiteral_1= Ampersand
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
    // InternalSTFunctionParser.g:4287:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4293:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTFunctionParser.g:4294:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTFunctionParser.g:4294:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==EqualsSign) ) {
                alt76=1;
            }
            else if ( (LA76_0==LessThanSignGreaterThanSign) ) {
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
                    // InternalSTFunctionParser.g:4295:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTFunctionParser.g:4295:3: (enumLiteral_0= EqualsSign )
                    // InternalSTFunctionParser.g:4296:4: enumLiteral_0= EqualsSign
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
                    // InternalSTFunctionParser.g:4303:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTFunctionParser.g:4303:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTFunctionParser.g:4304:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalSTFunctionParser.g:4314:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4320:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTFunctionParser.g:4321:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTFunctionParser.g:4321:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt77=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt77=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt77=2;
                }
                break;
            case GreaterThanSign:
                {
                alt77=3;
                }
                break;
            case GreaterThanSignEqualsSign:
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
                    // InternalSTFunctionParser.g:4322:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTFunctionParser.g:4322:3: (enumLiteral_0= LessThanSign )
                    // InternalSTFunctionParser.g:4323:4: enumLiteral_0= LessThanSign
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
                    // InternalSTFunctionParser.g:4330:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTFunctionParser.g:4330:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTFunctionParser.g:4331:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalSTFunctionParser.g:4338:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTFunctionParser.g:4338:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTFunctionParser.g:4339:4: enumLiteral_2= GreaterThanSign
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
                    // InternalSTFunctionParser.g:4346:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTFunctionParser.g:4346:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTFunctionParser.g:4347:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalSTFunctionParser.g:4357:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4363:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTFunctionParser.g:4364:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTFunctionParser.g:4364:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==PlusSign) ) {
                alt78=1;
            }
            else if ( (LA78_0==HyphenMinus) ) {
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
                    // InternalSTFunctionParser.g:4365:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTFunctionParser.g:4365:3: (enumLiteral_0= PlusSign )
                    // InternalSTFunctionParser.g:4366:4: enumLiteral_0= PlusSign
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
                    // InternalSTFunctionParser.g:4373:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTFunctionParser.g:4373:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTFunctionParser.g:4374:4: enumLiteral_1= HyphenMinus
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
    // InternalSTFunctionParser.g:4384:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4390:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTFunctionParser.g:4391:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTFunctionParser.g:4391:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt79=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt79=1;
                }
                break;
            case Solidus:
                {
                alt79=2;
                }
                break;
            case MOD:
                {
                alt79=3;
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
                    // InternalSTFunctionParser.g:4392:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTFunctionParser.g:4392:3: (enumLiteral_0= Asterisk )
                    // InternalSTFunctionParser.g:4393:4: enumLiteral_0= Asterisk
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
                    // InternalSTFunctionParser.g:4400:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTFunctionParser.g:4400:3: (enumLiteral_1= Solidus )
                    // InternalSTFunctionParser.g:4401:4: enumLiteral_1= Solidus
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
                    // InternalSTFunctionParser.g:4408:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTFunctionParser.g:4408:3: (enumLiteral_2= MOD )
                    // InternalSTFunctionParser.g:4409:4: enumLiteral_2= MOD
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
    // InternalSTFunctionParser.g:4419:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4425:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTFunctionParser.g:4426:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTFunctionParser.g:4426:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTFunctionParser.g:4427:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalSTFunctionParser.g:4436:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4442:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTFunctionParser.g:4443:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTFunctionParser.g:4443:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt80=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt80=1;
                }
                break;
            case PlusSign:
                {
                alt80=2;
                }
                break;
            case NOT:
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
                    // InternalSTFunctionParser.g:4444:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTFunctionParser.g:4444:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTFunctionParser.g:4445:4: enumLiteral_0= HyphenMinus
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
                    // InternalSTFunctionParser.g:4452:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTFunctionParser.g:4452:3: (enumLiteral_1= PlusSign )
                    // InternalSTFunctionParser.g:4453:4: enumLiteral_1= PlusSign
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
                    // InternalSTFunctionParser.g:4460:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTFunctionParser.g:4460:3: (enumLiteral_2= NOT )
                    // InternalSTFunctionParser.g:4461:4: enumLiteral_2= NOT
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
    // InternalSTFunctionParser.g:4471:1: ruleMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) ) ;
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
            // InternalSTFunctionParser.g:4477:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) ) )
            // InternalSTFunctionParser.g:4478:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) )
            {
            // InternalSTFunctionParser.g:4478:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) | (enumLiteral_5= FullStop ) )
            int alt81=6;
            switch ( input.LA(1) ) {
            case L:
                {
                alt81=1;
                }
                break;
            case D_1:
                {
                alt81=2;
                }
                break;
            case W:
                {
                alt81=3;
                }
                break;
            case B:
                {
                alt81=4;
                }
                break;
            case X:
                {
                alt81=5;
                }
                break;
            case FullStop:
                {
                alt81=6;
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
                    // InternalSTFunctionParser.g:4479:3: (enumLiteral_0= L )
                    {
                    // InternalSTFunctionParser.g:4479:3: (enumLiteral_0= L )
                    // InternalSTFunctionParser.g:4480:4: enumLiteral_0= L
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
                    // InternalSTFunctionParser.g:4487:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTFunctionParser.g:4487:3: (enumLiteral_1= D_1 )
                    // InternalSTFunctionParser.g:4488:4: enumLiteral_1= D_1
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
                    // InternalSTFunctionParser.g:4495:3: (enumLiteral_2= W )
                    {
                    // InternalSTFunctionParser.g:4495:3: (enumLiteral_2= W )
                    // InternalSTFunctionParser.g:4496:4: enumLiteral_2= W
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
                    // InternalSTFunctionParser.g:4503:3: (enumLiteral_3= B )
                    {
                    // InternalSTFunctionParser.g:4503:3: (enumLiteral_3= B )
                    // InternalSTFunctionParser.g:4504:4: enumLiteral_3= B
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
                    // InternalSTFunctionParser.g:4511:3: (enumLiteral_4= X )
                    {
                    // InternalSTFunctionParser.g:4511:3: (enumLiteral_4= X )
                    // InternalSTFunctionParser.g:4512:4: enumLiteral_4= X
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
                    // InternalSTFunctionParser.g:4519:3: (enumLiteral_5= FullStop )
                    {
                    // InternalSTFunctionParser.g:4519:3: (enumLiteral_5= FullStop )
                    // InternalSTFunctionParser.g:4520:4: enumLiteral_5= FullStop
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
        // InternalSTFunctionParser.g:1431:4: ( ( ruleSTStatement ) )
        // InternalSTFunctionParser.g:1431:5: ( ruleSTStatement )
        {
        // InternalSTFunctionParser.g:1431:5: ( ruleSTStatement )
        // InternalSTFunctionParser.g:1432:5: ruleSTStatement
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
        // InternalSTFunctionParser.g:2728:6: ( ( LeftParenthesis ) )
        // InternalSTFunctionParser.g:2728:7: ( LeftParenthesis )
        {
        // InternalSTFunctionParser.g:2728:7: ( LeftParenthesis )
        // InternalSTFunctionParser.g:2729:7: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSTFunctionParser

    // $ANTLR start synpred3_InternalSTFunctionParser
    public final void synpred3_InternalSTFunctionParser_fragment() throws RecognitionException {   
        // InternalSTFunctionParser.g:2797:5: ( ( ruleMultibitPartialAccess ) )
        // InternalSTFunctionParser.g:2797:6: ( ruleMultibitPartialAccess )
        {
        // InternalSTFunctionParser.g:2797:6: ( ruleMultibitPartialAccess )
        // InternalSTFunctionParser.g:2798:6: ruleMultibitPartialAccess
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
        // InternalSTFunctionParser.g:2975:6: ( ( LeftParenthesis ) )
        // InternalSTFunctionParser.g:2975:7: ( LeftParenthesis )
        {
        // InternalSTFunctionParser.g:2975:7: ( LeftParenthesis )
        // InternalSTFunctionParser.g:2976:7: LeftParenthesis
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


    protected DFA30 dfa30 = new DFA30(this);
    protected DFA51 dfa51 = new DFA51(this);
    protected DFA58 dfa58 = new DFA58(this);
    static final String dfa_1s = "\15\uffff";
    static final String dfa_2s = "\1\1\14\uffff";
    static final String dfa_3s = "\1\4\1\uffff\1\70\12\uffff";
    static final String dfa_4s = "\1\155\1\uffff\1\144\12\uffff";
    static final String dfa_5s = "\1\uffff\1\2\1\uffff\12\1";
    static final String dfa_6s = "\1\0\1\uffff\1\1\12\uffff}>";
    static final String[] dfa_7s = {
            "\2\1\1\uffff\1\1\5\uffff\1\12\1\1\2\uffff\1\1\2\uffff\2\1\1\uffff\4\1\1\7\1\11\4\1\1\uffff\5\1\1\uffff\6\1\1\uffff\1\6\1\1\1\4\1\1\1\13\2\1\1\uffff\1\1\6\uffff\1\1\1\5\2\1\1\uffff\1\1\12\uffff\1\1\1\uffff\1\3\2\uffff\1\1\3\uffff\1\1\2\uffff\1\1\1\uffff\1\1\3\uffff\1\10\5\uffff\1\1\1\uffff\2\1\2\uffff\1\2\1\1",
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

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 1430:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA30_0 = input.LA(1);

                         
                        int index30_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA30_0==EOF||(LA30_0>=LDATE_AND_TIME && LA30_0<=DATE_AND_TIME)||LA30_0==TIME_OF_DAY||LA30_0==END_CASE||LA30_0==WSTRING||(LA30_0>=STRING && LA30_0<=DWORD)||(LA30_0>=LDATE && LA30_0<=LWORD)||(LA30_0>=UDINT && LA30_0<=WCHAR)||(LA30_0>=BOOL && LA30_0<=DINT)||(LA30_0>=LINT && LA30_0<=UINT)||LA30_0==WORD||LA30_0==ELSE||(LA30_0>=INT && LA30_0<=LDT)||LA30_0==TOD||LA30_0==DT||(LA30_0>=LD && LA30_0<=LT)||LA30_0==NOT||LA30_0==D||LA30_0==T||LA30_0==LeftParenthesis||LA30_0==PlusSign||LA30_0==HyphenMinus||LA30_0==RULE_BOOL_VALUES||(LA30_0>=RULE_NON_DECIMAL && LA30_0<=RULE_INT)||LA30_0==RULE_STRING) ) {s = 1;}

                        else if ( (LA30_0==RULE_ID) ) {s = 2;}

                        else if ( (LA30_0==IF) && (synpred1_InternalSTFunctionParser())) {s = 3;}

                        else if ( (LA30_0==CASE) && (synpred1_InternalSTFunctionParser())) {s = 4;}

                        else if ( (LA30_0==FOR) && (synpred1_InternalSTFunctionParser())) {s = 5;}

                        else if ( (LA30_0==WHILE) && (synpred1_InternalSTFunctionParser())) {s = 6;}

                        else if ( (LA30_0==REPEAT) && (synpred1_InternalSTFunctionParser())) {s = 7;}

                        else if ( (LA30_0==Semicolon) && (synpred1_InternalSTFunctionParser())) {s = 8;}

                        else if ( (LA30_0==RETURN) && (synpred1_InternalSTFunctionParser())) {s = 9;}

                        else if ( (LA30_0==CONTINUE) && (synpred1_InternalSTFunctionParser())) {s = 10;}

                        else if ( (LA30_0==EXIT) && (synpred1_InternalSTFunctionParser())) {s = 11;}

                         
                        input.seek(index30_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA30_2 = input.LA(1);

                         
                        int index30_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA30_2==ColonEqualsSign) && (synpred1_InternalSTFunctionParser())) {s = 12;}

                        else if ( ((LA30_2>=B && LA30_2<=AND)||LA30_2==MOD||(LA30_2>=XOR && LA30_2<=FullStopFullStop)||(LA30_2>=LessThanSignEqualsSign && LA30_2<=GreaterThanSignEqualsSign)||LA30_2==OR||(LA30_2>=NumberSign && LA30_2<=LeftParenthesis)||(LA30_2>=Asterisk && LA30_2<=Colon)||(LA30_2>=LessThanSign && LA30_2<=LeftSquareBracket)) ) {s = 1;}

                         
                        input.seek(index30_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 30, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\47\uffff";
    static final String dfa_9s = "\1\2\46\uffff";
    static final String dfa_10s = "\1\10\1\0\45\uffff";
    static final String dfa_11s = "\1\145\1\0\45\uffff";
    static final String dfa_12s = "\2\uffff\1\2\43\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\45\uffff}>";
    static final String[] dfa_14s = {
            "\1\2\55\uffff\1\2\1\uffff\6\2\4\uffff\1\2\2\uffff\3\2\1\uffff\3\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\16\2",
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

    class DFA51 extends DFA {

        public DFA51(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 51;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "2726:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_9_0= LeftParenthesis ) ) ( ( (lv_parameters_10_0= ruleSTExpression ) ) (otherlv_11= Comma ( (lv_parameters_12_0= ruleSTExpression ) ) )* )? otherlv_13= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA51_1 = input.LA(1);

                         
                        int index51_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 38;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index51_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 51, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\42\uffff";
    static final String dfa_16s = "\1\2\41\uffff";
    static final String dfa_17s = "\1\10\1\0\40\uffff";
    static final String dfa_18s = "\1\145\1\0\40\uffff";
    static final String dfa_19s = "\2\uffff\1\2\36\uffff\1\1";
    static final String dfa_20s = "\1\uffff\1\0\40\uffff}>";
    static final String[] dfa_21s = {
            "\1\2\55\uffff\1\2\6\uffff\1\2\4\uffff\1\2\2\uffff\3\2\1\uffff\3\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\uffff\1\2\1\1\16\2",
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

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "2973:4: ( ( ( ( LeftParenthesis ) )=> (lv_poeInvocation_8_0= LeftParenthesis ) ) ( ( (lv_parameters_9_0= ruleSTExpression ) ) (otherlv_10= Comma ( (lv_parameters_11_0= ruleSTExpression ) ) )* )? otherlv_12= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA58_1 = input.LA(1);

                         
                        int index58_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSTFunctionParser()) ) {s = 33;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index58_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 58, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x800A800018012A40L,0x0000100180010000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x800A800018012A40L,0x0000100100010000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x800A800018002040L,0x0000100100010000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000081000L,0x0000100000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000080000L,0x0000100000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000000080001000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000200000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x40B13F7DE7B200B0L,0x000033401488400BL});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000002008000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000001100000100L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000100L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x40B13F7DE7B200B0L,0x000033501488400BL});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x800E808018402000L,0x0000100100010000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0004008000400000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x800A800018002002L,0x0000100100010000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x40B53F7DE7B240B0L,0x000033401488400BL});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000000088000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x000000000000A000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x800A800018042000L,0x0000100100010000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x800A800018002400L,0x0000100100010000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x800AC00018002000L,0x0000100100010000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x2000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000400L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000000A00000A00L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000014000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000042000004L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0000100000800000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000001020000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x1F00000000000002L,0x0000001020800000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x40B13F7DE7B200B0L,0x000033401588400BL});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000009000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x1F00000000000002L,0x0000001020000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x1F00000000000002L,0x0000000020800000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000400000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x00112D48E5200000L,0x0000030014000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000030014000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x00000A0000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});

}