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
/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies GmbH, 
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
@SuppressWarnings("all")
public class InternalSTFunctionParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "END_FUNCTION_BLOCK", "END_CONFIGURATION", "END_TRANSITION", "FUNCTION_BLOCK", "LDATE_AND_TIME", "CONFIGURATION", "DATE_AND_TIME", "END_INTERFACE", "END_NAMESPACE", "END_FUNCTION", "END_RESOURCE", "INITIAL_STEP", "LTIME_OF_DAY", "VAR_EXTERNAL", "END_PROGRAM", "TIME_OF_DAY", "END_ACTION", "END_METHOD", "END_REPEAT", "END_STRUCT", "IMPLEMENTS", "NON_RETAIN", "READ_WRITE", "TRANSITION", "VAR_ACCESS", "VAR_CONFIG", "VAR_GLOBAL", "VAR_IN_OUT", "VAR_OUTPUT", "END_CLASS", "END_WHILE", "INTERFACE", "NAMESPACE", "PROTECTED", "READ_ONLY", "VAR_INPUT", "ABSTRACT", "CONSTANT", "CONTINUE", "END_CASE", "END_STEP", "END_TYPE", "FUNCTION", "INTERNAL", "INTERVAL", "OVERRIDE", "PRIORITY", "RESOURCE", "VAR_TEMP", "END_FOR", "END_VAR", "EXTENDS", "INTERAL", "OVERLAP", "PACKAGE", "PRIVATE", "PROGRAM", "WSTRING", "ACTION", "END_IF", "IMPORT", "METHOD", "PUBLIC", "REF_TO", "REPEAT", "RETAIN", "RETURN", "SINGLE", "STRING", "STRUCT", "ARRAY", "CLASS", "DWORD", "ELSIF", "FALSE", "FINAL", "LDATE", "LREAL", "LTIME", "LWORD", "SUPER", "UDINT", "ULINT", "UNTIL", "USING", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "FROM", "LINT", "LTOD", "NULL", "REAL", "SINT", "STEP", "TASK", "THEN", "THIS", "TIME", "TRUE", "TYPE", "UINT", "WITH", "WORD", "ColonColonAsterisk", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "REF", "TOD", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonColon", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "OF", "ON", "OR", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "T", "LeftSquareBracket", "RightSquareBracket", "LeftCurlyBracket", "RightCurlyBracket", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_DECIMAL", "RULE_TIME_PART", "RULE_TIME_VALUE", "RULE_TIME_DAYS", "RULE_TIME_HOURS", "RULE_TIME_MINUTES", "RULE_TIME_SECONDS", "RULE_TIME_MILLIS", "RULE_TIME_MICROS", "RULE_TIME_NANOS", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int EqualsSignGreaterThanSign=138;
    public static final int LessThanSign=163;
    public static final int RULE_TIME_HOURS=179;
    public static final int INTERNAL=47;
    public static final int REF_TO=67;
    public static final int LINT=101;
    public static final int GreaterThanSign=165;
    public static final int RULE_ID=185;
    public static final int CONFIGURATION=9;
    public static final int UDINT=85;
    public static final int GreaterThanSignEqualsSign=139;
    public static final int ColonColon=134;
    public static final int AT=140;
    public static final int RULE_INT=174;
    public static final int END_FOR=53;
    public static final int THEN=108;
    public static final int XOR=126;
    public static final int PROGRAM=60;
    public static final int TIME_OF_DAY=19;
    public static final int B=127;
    public static final int LDATE_AND_TIME=8;
    public static final int REPEAT=68;
    public static final int D=166;
    public static final int L=129;
    public static final int T=167;
    public static final int W=130;
    public static final int BY=141;
    public static final int X=131;
    public static final int END_REPEAT=22;
    public static final int Solidus=160;
    public static final int RightCurlyBracket=171;
    public static final int PROTECTED=37;
    public static final int RESOURCE=51;
    public static final int INTERVAL=48;
    public static final int FullStop=159;
    public static final int RULE_TIME_SECONDS=181;
    public static final int INTERAL=56;
    public static final int RULE_TIME_VALUE=177;
    public static final int CONTINUE=42;
    public static final int Semicolon=162;
    public static final int REF=123;
    public static final int VAR_OUTPUT=32;
    public static final int STRING=72;
    public static final int TO=150;
    public static final int INITIAL_STEP=15;
    public static final int EXTENDS=55;
    public static final int PRIORITY=50;
    public static final int CLASS=75;
    public static final int DO=142;
    public static final int END_CONFIGURATION=5;
    public static final int DT=143;
    public static final int END_VAR=54;
    public static final int END_STEP=44;
    public static final int RULE_TIME_PART=176;
    public static final int PACKAGE=58;
    public static final int FullStopFullStop=133;
    public static final int Ampersand=152;
    public static final int END_NAMESPACE=12;
    public static final int END_ACTION=20;
    public static final int USINT=89;
    public static final int DWORD=76;
    public static final int FOR=118;
    public static final int RightParenthesis=154;
    public static final int FINAL=79;
    public static final int END_FUNCTION=13;
    public static final int USING=88;
    public static final int RULE_DECIMAL=175;
    public static final int NOT=122;
    public static final int AsteriskAsterisk=132;
    public static final int THIS=109;
    public static final int SINT=105;
    public static final int VAR_GLOBAL=30;
    public static final int WCHAR=90;
    public static final int VAR_EXTERNAL=17;
    public static final int RULE_SL_COMMENT=188;
    public static final int RETURN=70;
    public static final int ColonColonAsterisk=116;
    public static final int END_RESOURCE=14;
    public static final int Colon=161;
    public static final int EOF=-1;
    public static final int Asterisk=155;
    public static final int RULE_TIME_MILLIS=182;
    public static final int MOD=121;
    public static final int RETAIN=69;
    public static final int LeftCurlyBracket=170;
    public static final int STEP=106;
    public static final int TIME=110;
    public static final int WITH=114;
    public static final int RULE_TIME_MINUTES=180;
    public static final int END_CLASS=33;
    public static final int ACTION=62;
    public static final int BOOL=92;
    public static final int D_1=128;
    public static final int FALSE=78;
    public static final int RULE_TIME_MICROS=183;
    public static final int LWORD=83;
    public static final int LessThanSignGreaterThanSign=137;
    public static final int FUNCTION_BLOCK=7;
    public static final int VAR=125;
    public static final int END_IF=63;
    public static final int ULINT=86;
    public static final int END_CASE=43;
    public static final int LeftParenthesis=153;
    public static final int INTERFACE=35;
    public static final int VAR_CONFIG=29;
    public static final int BYTE=93;
    public static final int ELSE=98;
    public static final int TYPE=112;
    public static final int IF=144;
    public static final int WORD=115;
    public static final int DATE_AND_TIME=10;
    public static final int TOD=124;
    public static final int DINT=97;
    public static final int FUNCTION=46;
    public static final int RULE_TIME_NANOS=184;
    public static final int CASE=94;
    public static final int PlusSign=156;
    public static final int RULE_TIME_DAYS=178;
    public static final int RULE_ML_COMMENT=187;
    public static final int PUBLIC=66;
    public static final int LeftSquareBracket=168;
    public static final int EXIT=99;
    public static final int CHAR=95;
    public static final int LTIME=82;
    public static final int IMPORT=64;
    public static final int Comma=157;
    public static final int HyphenMinus=158;
    public static final int ELSIF=77;
    public static final int LessThanSignEqualsSign=136;
    public static final int VAR_INPUT=39;
    public static final int VAR_TEMP=52;
    public static final int CONSTANT=41;
    public static final int PRIVATE=59;
    public static final int TRANSITION=27;
    public static final int LD=145;
    public static final int RULE_HEX_DIGIT=172;
    public static final int END_TYPE=45;
    public static final int UINT=113;
    public static final int LTOD=102;
    public static final int NAMESPACE=36;
    public static final int SINGLE=71;
    public static final int ARRAY=74;
    public static final int LT=146;
    public static final int FROM=100;
    public static final int WSTRING=61;
    public static final int READ_WRITE=26;
    public static final int END_STRUCT=23;
    public static final int OVERLAP=57;
    public static final int RightSquareBracket=169;
    public static final int TASK=107;
    public static final int NULL=103;
    public static final int TRUE=111;
    public static final int ColonEqualsSign=135;
    public static final int END_WHILE=34;
    public static final int DATE=96;
    public static final int LDATE=80;
    public static final int AND=117;
    public static final int NumberSign=151;
    public static final int REAL=104;
    public static final int METHOD=65;
    public static final int NON_RETAIN=25;
    public static final int STRUCT=73;
    public static final int LTIME_OF_DAY=16;
    public static final int END_TRANSITION=6;
    public static final int LREAL=81;
    public static final int END_FUNCTION_BLOCK=4;
    public static final int RULE_STRING=186;
    public static final int VAR_ACCESS=28;
    public static final int ABSTRACT=40;
    public static final int READ_ONLY=38;
    public static final int INT=119;
    public static final int EqualsSign=164;
    public static final int OF=147;
    public static final int END_METHOD=21;
    public static final int LDT=120;
    public static final int ON=148;
    public static final int SUPER=84;
    public static final int OR=149;
    public static final int RULE_WS=189;
    public static final int VAR_IN_OUT=31;
    public static final int END_INTERFACE=11;
    public static final int IMPLEMENTS=24;
    public static final int RULE_ANY_OTHER=190;
    public static final int UNTIL=87;
    public static final int OVERRIDE=49;
    public static final int WHILE=91;
    public static final int END_PROGRAM=18;
    public static final int RULE_NON_DECIMAL=173;

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
    // InternalSTFunctionParser.g:69:1: entryRuleSTFunctionSource returns [EObject current=null] : iv_ruleSTFunctionSource= ruleSTFunctionSource EOF ;
    public final EObject entryRuleSTFunctionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFunctionSource = null;


        try {
            // InternalSTFunctionParser.g:69:57: (iv_ruleSTFunctionSource= ruleSTFunctionSource EOF )
            // InternalSTFunctionParser.g:70:2: iv_ruleSTFunctionSource= ruleSTFunctionSource EOF
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
    // InternalSTFunctionParser.g:76:1: ruleSTFunctionSource returns [EObject current=null] : ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_functions_5_0= ruleSTFunction ) )* ) ;
    public final EObject ruleSTFunctionSource() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_imports_4_0 = null;

        EObject lv_functions_5_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:82:2: ( ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_functions_5_0= ruleSTFunction ) )* ) )
            // InternalSTFunctionParser.g:83:2: ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_functions_5_0= ruleSTFunction ) )* )
            {
            // InternalSTFunctionParser.g:83:2: ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_functions_5_0= ruleSTFunction ) )* )
            // InternalSTFunctionParser.g:84:3: () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_functions_5_0= ruleSTFunction ) )*
            {
            // InternalSTFunctionParser.g:84:3: ()
            // InternalSTFunctionParser.g:85:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFunctionSourceAccess().getSTFunctionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:91:3: (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==PACKAGE) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalSTFunctionParser.g:92:4: otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon
                    {
                    otherlv_1=(Token)match(input,PACKAGE,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTFunctionSourceAccess().getPACKAGEKeyword_1_0());
                      			
                    }
                    // InternalSTFunctionParser.g:96:4: ( (lv_name_2_0= ruleQualifiedName ) )
                    // InternalSTFunctionParser.g:97:5: (lv_name_2_0= ruleQualifiedName )
                    {
                    // InternalSTFunctionParser.g:97:5: (lv_name_2_0= ruleQualifiedName )
                    // InternalSTFunctionParser.g:98:6: lv_name_2_0= ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTFunctionSourceAccess().getNameQualifiedNameParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_4);
                    lv_name_2_0=ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTFunctionSourceRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_2_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.QualifiedName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,Semicolon,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTFunctionSourceAccess().getSemicolonKeyword_1_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:120:3: ( (lv_imports_4_0= ruleSTImport ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==IMPORT) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSTFunctionParser.g:121:4: (lv_imports_4_0= ruleSTImport )
            	    {
            	    // InternalSTFunctionParser.g:121:4: (lv_imports_4_0= ruleSTImport )
            	    // InternalSTFunctionParser.g:122:5: lv_imports_4_0= ruleSTImport
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTFunctionSourceAccess().getImportsSTImportParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_imports_4_0=ruleSTImport();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTFunctionSourceRule());
            	      					}
            	      					add(
            	      						current,
            	      						"imports",
            	      						lv_imports_4_0,
            	      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STImport");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalSTFunctionParser.g:139:3: ( (lv_functions_5_0= ruleSTFunction ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==FUNCTION) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSTFunctionParser.g:140:4: (lv_functions_5_0= ruleSTFunction )
            	    {
            	    // InternalSTFunctionParser.g:140:4: (lv_functions_5_0= ruleSTFunction )
            	    // InternalSTFunctionParser.g:141:5: lv_functions_5_0= ruleSTFunction
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTFunctionSourceAccess().getFunctionsSTFunctionParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_functions_5_0=ruleSTFunction();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTFunctionSourceRule());
            	      					}
            	      					add(
            	      						current,
            	      						"functions",
            	      						lv_functions_5_0,
            	      						"org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunction.STFunction");
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
    // $ANTLR end "ruleSTFunctionSource"


    // $ANTLR start "entryRuleSTFunction"
    // InternalSTFunctionParser.g:162:1: entryRuleSTFunction returns [EObject current=null] : iv_ruleSTFunction= ruleSTFunction EOF ;
    public final EObject entryRuleSTFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFunction = null;


        try {
            // InternalSTFunctionParser.g:162:51: (iv_ruleSTFunction= ruleSTFunction EOF )
            // InternalSTFunctionParser.g:163:2: iv_ruleSTFunction= ruleSTFunction EOF
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
    // InternalSTFunctionParser.g:169:1: ruleSTFunction returns [EObject current=null] : ( () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION ) ;
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
            // InternalSTFunctionParser.g:175:2: ( ( () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION ) )
            // InternalSTFunctionParser.g:176:2: ( () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION )
            {
            // InternalSTFunctionParser.g:176:2: ( () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION )
            // InternalSTFunctionParser.g:177:3: () otherlv_1= FUNCTION ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= Colon ( ( ruleSTAnyType ) ) )? ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )* ( (lv_code_6_0= ruleSTStatement ) )* otherlv_7= END_FUNCTION
            {
            // InternalSTFunctionParser.g:177:3: ()
            // InternalSTFunctionParser.g:178:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFunctionAccess().getSTFunctionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,FUNCTION,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTFunctionAccess().getFUNCTIONKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:188:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalSTFunctionParser.g:189:4: (lv_name_2_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:189:4: (lv_name_2_0= RULE_ID )
            // InternalSTFunctionParser.g:190:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:206:3: (otherlv_3= Colon ( ( ruleSTAnyType ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==Colon) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalSTFunctionParser.g:207:4: otherlv_3= Colon ( ( ruleSTAnyType ) )
                    {
                    otherlv_3=(Token)match(input,Colon,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getSTFunctionAccess().getColonKeyword_3_0());
                      			
                    }
                    // InternalSTFunctionParser.g:211:4: ( ( ruleSTAnyType ) )
                    // InternalSTFunctionParser.g:212:5: ( ruleSTAnyType )
                    {
                    // InternalSTFunctionParser.g:212:5: ( ruleSTAnyType )
                    // InternalSTFunctionParser.g:213:6: ruleSTAnyType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTFunctionRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTFunctionAccess().getReturnTypeDataTypeCrossReference_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_9);
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

            // InternalSTFunctionParser.g:228:3: ( ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>=VAR_IN_OUT && LA6_0<=VAR_OUTPUT)||LA6_0==VAR_INPUT||LA6_0==VAR_TEMP||LA6_0==VAR) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSTFunctionParser.g:229:4: ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) )
            	    {
            	    // InternalSTFunctionParser.g:229:4: ( (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock ) )
            	    // InternalSTFunctionParser.g:230:5: (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock )
            	    {
            	    // InternalSTFunctionParser.g:230:5: (lv_varDeclarations_5_1= ruleSTVarDeclarationBlock | lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock | lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock | lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock | lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock )
            	    int alt5=5;
            	    switch ( input.LA(1) ) {
            	    case VAR:
            	        {
            	        alt5=1;
            	        }
            	        break;
            	    case VAR_TEMP:
            	        {
            	        alt5=2;
            	        }
            	        break;
            	    case VAR_INPUT:
            	        {
            	        alt5=3;
            	        }
            	        break;
            	    case VAR_OUTPUT:
            	        {
            	        alt5=4;
            	        }
            	        break;
            	    case VAR_IN_OUT:
            	        {
            	        alt5=5;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 5, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt5) {
            	        case 1 :
            	            // InternalSTFunctionParser.g:231:6: lv_varDeclarations_5_1= ruleSTVarDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarDeclarationBlockParserRuleCall_4_0_0());
            	              					
            	            }
            	            pushFollow(FOLLOW_9);
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
            	            // InternalSTFunctionParser.g:247:6: lv_varDeclarations_5_2= ruleSTVarTempDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarTempDeclarationBlockParserRuleCall_4_0_1());
            	              					
            	            }
            	            pushFollow(FOLLOW_9);
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
            	            // InternalSTFunctionParser.g:263:6: lv_varDeclarations_5_3= ruleSTVarInputDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarInputDeclarationBlockParserRuleCall_4_0_2());
            	              					
            	            }
            	            pushFollow(FOLLOW_9);
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
            	            // InternalSTFunctionParser.g:279:6: lv_varDeclarations_5_4= ruleSTVarOutputDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarOutputDeclarationBlockParserRuleCall_4_0_3());
            	              					
            	            }
            	            pushFollow(FOLLOW_9);
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
            	            // InternalSTFunctionParser.g:295:6: lv_varDeclarations_5_5= ruleSTVarInOutDeclarationBlock
            	            {
            	            if ( state.backtracking==0 ) {

            	              						newCompositeNode(grammarAccess.getSTFunctionAccess().getVarDeclarationsSTVarInOutDeclarationBlockParserRuleCall_4_0_4());
            	              					
            	            }
            	            pushFollow(FOLLOW_9);
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
            	    break loop6;
                }
            } while (true);

            // InternalSTFunctionParser.g:313:3: ( (lv_code_6_0= ruleSTStatement ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==LDATE_AND_TIME||LA7_0==DATE_AND_TIME||LA7_0==LTIME_OF_DAY||LA7_0==TIME_OF_DAY||LA7_0==CONTINUE||LA7_0==WSTRING||LA7_0==REPEAT||LA7_0==RETURN||LA7_0==STRING||LA7_0==DWORD||LA7_0==FALSE||(LA7_0>=LDATE && LA7_0<=LWORD)||(LA7_0>=UDINT && LA7_0<=ULINT)||(LA7_0>=USINT && LA7_0<=DINT)||LA7_0==EXIT||(LA7_0>=LINT && LA7_0<=LTOD)||(LA7_0>=REAL && LA7_0<=SINT)||(LA7_0>=THIS && LA7_0<=TRUE)||LA7_0==UINT||LA7_0==WORD||(LA7_0>=AND && LA7_0<=NOT)||LA7_0==TOD||LA7_0==XOR||(LA7_0>=DT && LA7_0<=LT)||LA7_0==OR||LA7_0==LeftParenthesis||LA7_0==PlusSign||LA7_0==HyphenMinus||LA7_0==Semicolon||(LA7_0>=D && LA7_0<=T)||(LA7_0>=RULE_NON_DECIMAL && LA7_0<=RULE_INT)||(LA7_0>=RULE_ID && LA7_0<=RULE_STRING)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSTFunctionParser.g:314:4: (lv_code_6_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:314:4: (lv_code_6_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:315:5: lv_code_6_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTFunctionAccess().getCodeSTStatementParserRuleCall_5_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_10);
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
            	    break loop7;
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


    // $ANTLR start "entryRuleSTExpressionSource"
    // InternalSTFunctionParser.g:340:1: entryRuleSTExpressionSource returns [EObject current=null] : iv_ruleSTExpressionSource= ruleSTExpressionSource EOF ;
    public final EObject entryRuleSTExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpressionSource = null;


        try {
            // InternalSTFunctionParser.g:340:59: (iv_ruleSTExpressionSource= ruleSTExpressionSource EOF )
            // InternalSTFunctionParser.g:341:2: iv_ruleSTExpressionSource= ruleSTExpressionSource EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTExpressionSourceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTExpressionSource=ruleSTExpressionSource();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTExpressionSource; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTExpressionSource"


    // $ANTLR start "ruleSTExpressionSource"
    // InternalSTFunctionParser.g:347:1: ruleSTExpressionSource returns [EObject current=null] : ( () ( (lv_expression_1_0= ruleSTExpression ) )? ) ;
    public final EObject ruleSTExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_expression_1_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:353:2: ( ( () ( (lv_expression_1_0= ruleSTExpression ) )? ) )
            // InternalSTFunctionParser.g:354:2: ( () ( (lv_expression_1_0= ruleSTExpression ) )? )
            {
            // InternalSTFunctionParser.g:354:2: ( () ( (lv_expression_1_0= ruleSTExpression ) )? )
            // InternalSTFunctionParser.g:355:3: () ( (lv_expression_1_0= ruleSTExpression ) )?
            {
            // InternalSTFunctionParser.g:355:3: ()
            // InternalSTFunctionParser.g:356:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTExpressionSourceAccess().getSTExpressionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:362:3: ( (lv_expression_1_0= ruleSTExpression ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==LDATE_AND_TIME||LA8_0==DATE_AND_TIME||LA8_0==LTIME_OF_DAY||LA8_0==TIME_OF_DAY||LA8_0==WSTRING||LA8_0==STRING||LA8_0==DWORD||LA8_0==FALSE||(LA8_0>=LDATE && LA8_0<=LWORD)||(LA8_0>=UDINT && LA8_0<=ULINT)||(LA8_0>=USINT && LA8_0<=WCHAR)||(LA8_0>=BOOL && LA8_0<=BYTE)||(LA8_0>=CHAR && LA8_0<=DINT)||(LA8_0>=LINT && LA8_0<=LTOD)||(LA8_0>=REAL && LA8_0<=SINT)||(LA8_0>=THIS && LA8_0<=TRUE)||LA8_0==UINT||LA8_0==WORD||LA8_0==AND||(LA8_0>=INT && LA8_0<=NOT)||LA8_0==TOD||LA8_0==XOR||LA8_0==DT||(LA8_0>=LD && LA8_0<=LT)||LA8_0==OR||LA8_0==LeftParenthesis||LA8_0==PlusSign||LA8_0==HyphenMinus||(LA8_0>=D && LA8_0<=T)||(LA8_0>=RULE_NON_DECIMAL && LA8_0<=RULE_INT)||(LA8_0>=RULE_ID && LA8_0<=RULE_STRING)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSTFunctionParser.g:363:4: (lv_expression_1_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:363:4: (lv_expression_1_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:364:5: lv_expression_1_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTExpressionSourceAccess().getExpressionSTExpressionParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_1_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTExpressionSourceRule());
                      					}
                      					set(
                      						current,
                      						"expression",
                      						lv_expression_1_0,
                      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      					afterParserOrEnumRuleCall();
                      				
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
    // $ANTLR end "ruleSTExpressionSource"


    // $ANTLR start "entryRuleSTInitializerExpressionSource"
    // InternalSTFunctionParser.g:385:1: entryRuleSTInitializerExpressionSource returns [EObject current=null] : iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF ;
    public final EObject entryRuleSTInitializerExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpressionSource = null;


        try {
            // InternalSTFunctionParser.g:385:70: (iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF )
            // InternalSTFunctionParser.g:386:2: iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTInitializerExpressionSourceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTInitializerExpressionSource=ruleSTInitializerExpressionSource();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTInitializerExpressionSource; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTInitializerExpressionSource"


    // $ANTLR start "ruleSTInitializerExpressionSource"
    // InternalSTFunctionParser.g:392:1: ruleSTInitializerExpressionSource returns [EObject current=null] : ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? ) ;
    public final EObject ruleSTInitializerExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_initializerExpression_1_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:398:2: ( ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? ) )
            // InternalSTFunctionParser.g:399:2: ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? )
            {
            // InternalSTFunctionParser.g:399:2: ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? )
            // InternalSTFunctionParser.g:400:3: () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )?
            {
            // InternalSTFunctionParser.g:400:3: ()
            // InternalSTFunctionParser.g:401:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTInitializerExpressionSourceAccess().getSTInitializerExpressionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:407:3: ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==LDATE_AND_TIME||LA9_0==DATE_AND_TIME||LA9_0==LTIME_OF_DAY||LA9_0==TIME_OF_DAY||LA9_0==WSTRING||LA9_0==STRING||LA9_0==DWORD||LA9_0==FALSE||(LA9_0>=LDATE && LA9_0<=LWORD)||(LA9_0>=UDINT && LA9_0<=ULINT)||(LA9_0>=USINT && LA9_0<=WCHAR)||(LA9_0>=BOOL && LA9_0<=BYTE)||(LA9_0>=CHAR && LA9_0<=DINT)||(LA9_0>=LINT && LA9_0<=LTOD)||(LA9_0>=REAL && LA9_0<=SINT)||(LA9_0>=THIS && LA9_0<=TRUE)||LA9_0==UINT||LA9_0==WORD||LA9_0==AND||(LA9_0>=INT && LA9_0<=NOT)||LA9_0==TOD||LA9_0==XOR||LA9_0==DT||(LA9_0>=LD && LA9_0<=LT)||LA9_0==OR||LA9_0==LeftParenthesis||LA9_0==PlusSign||LA9_0==HyphenMinus||(LA9_0>=D && LA9_0<=LeftSquareBracket)||(LA9_0>=RULE_NON_DECIMAL && LA9_0<=RULE_INT)||(LA9_0>=RULE_ID && LA9_0<=RULE_STRING)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSTFunctionParser.g:408:4: (lv_initializerExpression_1_0= ruleSTInitializerExpression )
                    {
                    // InternalSTFunctionParser.g:408:4: (lv_initializerExpression_1_0= ruleSTInitializerExpression )
                    // InternalSTFunctionParser.g:409:5: lv_initializerExpression_1_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTInitializerExpressionSourceAccess().getInitializerExpressionSTInitializerExpressionParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_initializerExpression_1_0=ruleSTInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTInitializerExpressionSourceRule());
                      					}
                      					set(
                      						current,
                      						"initializerExpression",
                      						lv_initializerExpression_1_0,
                      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
                      					afterParserOrEnumRuleCall();
                      				
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
    // $ANTLR end "ruleSTInitializerExpressionSource"


    // $ANTLR start "entryRuleSTImport"
    // InternalSTFunctionParser.g:430:1: entryRuleSTImport returns [EObject current=null] : iv_ruleSTImport= ruleSTImport EOF ;
    public final EObject entryRuleSTImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTImport = null;


        try {
            // InternalSTFunctionParser.g:430:49: (iv_ruleSTImport= ruleSTImport EOF )
            // InternalSTFunctionParser.g:431:2: iv_ruleSTImport= ruleSTImport EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTImportRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTImport=ruleSTImport();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTImport; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTImport"


    // $ANTLR start "ruleSTImport"
    // InternalSTFunctionParser.g:437:1: ruleSTImport returns [EObject current=null] : (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon ) ;
    public final EObject ruleSTImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_importedNamespace_1_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:443:2: ( (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon ) )
            // InternalSTFunctionParser.g:444:2: (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon )
            {
            // InternalSTFunctionParser.g:444:2: (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon )
            // InternalSTFunctionParser.g:445:3: otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon
            {
            otherlv_0=(Token)match(input,IMPORT,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTImportAccess().getIMPORTKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:449:3: ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) )
            // InternalSTFunctionParser.g:450:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            {
            // InternalSTFunctionParser.g:450:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            // InternalSTFunctionParser.g:451:5: lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTImportAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_4);
            lv_importedNamespace_1_0=ruleQualifiedNameWithWildcard();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTImportRule());
              					}
              					set(
              						current,
              						"importedNamespace",
              						lv_importedNamespace_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.QualifiedNameWithWildcard");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTImportAccess().getSemicolonKeyword_2());
              		
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
    // $ANTLR end "ruleSTImport"


    // $ANTLR start "entryRuleSTVarDeclarationBlock"
    // InternalSTFunctionParser.g:476:1: entryRuleSTVarDeclarationBlock returns [EObject current=null] : iv_ruleSTVarDeclarationBlock= ruleSTVarDeclarationBlock EOF ;
    public final EObject entryRuleSTVarDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:476:62: (iv_ruleSTVarDeclarationBlock= ruleSTVarDeclarationBlock EOF )
            // InternalSTFunctionParser.g:477:2: iv_ruleSTVarDeclarationBlock= ruleSTVarDeclarationBlock EOF
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
    // InternalSTFunctionParser.g:483:1: ruleSTVarDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:489:2: ( ( () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:490:2: ( () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:490:2: ( () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:491:3: () otherlv_1= VAR ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:491:3: ()
            // InternalSTFunctionParser.g:492:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationBlockAccess().getSTVarPlainDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarDeclarationBlockAccess().getVARKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:502:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==CONSTANT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalSTFunctionParser.g:503:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:503:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:504:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_12); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:516:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ID) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalSTFunctionParser.g:517:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:517:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:518:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
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
            	    break loop11;
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
    // InternalSTFunctionParser.g:543:1: entryRuleSTVarTempDeclarationBlock returns [EObject current=null] : iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF ;
    public final EObject entryRuleSTVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarTempDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:543:66: (iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF )
            // InternalSTFunctionParser.g:544:2: iv_ruleSTVarTempDeclarationBlock= ruleSTVarTempDeclarationBlock EOF
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
    // InternalSTFunctionParser.g:550:1: ruleSTVarTempDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarTempDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:556:2: ( ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:557:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:557:2: ( () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:558:3: () otherlv_1= VAR_TEMP ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:558:3: ()
            // InternalSTFunctionParser.g:559:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarTempDeclarationBlockAccess().getSTVarTempDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_TEMP,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarTempDeclarationBlockAccess().getVAR_TEMPKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:569:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==CONSTANT) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalSTFunctionParser.g:570:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:570:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:571:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_12); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:583:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_ID) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalSTFunctionParser.g:584:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:584:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:585:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarTempDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
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
            	    break loop13;
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
    // InternalSTFunctionParser.g:610:1: entryRuleSTVarInputDeclarationBlock returns [EObject current=null] : iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF ;
    public final EObject entryRuleSTVarInputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarInputDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:610:67: (iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF )
            // InternalSTFunctionParser.g:611:2: iv_ruleSTVarInputDeclarationBlock= ruleSTVarInputDeclarationBlock EOF
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
    // InternalSTFunctionParser.g:617:1: ruleSTVarInputDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarInputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:623:2: ( ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:624:2: ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:624:2: ( () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:625:3: () otherlv_1= VAR_INPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:625:3: ()
            // InternalSTFunctionParser.g:626:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarInputDeclarationBlockAccess().getSTVarInputDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_INPUT,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarInputDeclarationBlockAccess().getVAR_INPUTKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:636:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==CONSTANT) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalSTFunctionParser.g:637:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:637:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:638:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_12); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:650:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_ID) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalSTFunctionParser.g:651:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:651:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:652:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarInputDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
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
            	    break loop15;
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
    // InternalSTFunctionParser.g:677:1: entryRuleSTVarOutputDeclarationBlock returns [EObject current=null] : iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF ;
    public final EObject entryRuleSTVarOutputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarOutputDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:677:68: (iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF )
            // InternalSTFunctionParser.g:678:2: iv_ruleSTVarOutputDeclarationBlock= ruleSTVarOutputDeclarationBlock EOF
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
    // InternalSTFunctionParser.g:684:1: ruleSTVarOutputDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarOutputDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:690:2: ( ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:691:2: ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:691:2: ( () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:692:3: () otherlv_1= VAR_OUTPUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:692:3: ()
            // InternalSTFunctionParser.g:693:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarOutputDeclarationBlockAccess().getSTVarOutputDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_OUTPUT,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarOutputDeclarationBlockAccess().getVAR_OUTPUTKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:703:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==CONSTANT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSTFunctionParser.g:704:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:704:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:705:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_12); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:717:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==RULE_ID) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSTFunctionParser.g:718:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:718:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:719:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarOutputDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
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
            	    break loop17;
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
    // InternalSTFunctionParser.g:744:1: entryRuleSTVarInOutDeclarationBlock returns [EObject current=null] : iv_ruleSTVarInOutDeclarationBlock= ruleSTVarInOutDeclarationBlock EOF ;
    public final EObject entryRuleSTVarInOutDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarInOutDeclarationBlock = null;


        try {
            // InternalSTFunctionParser.g:744:67: (iv_ruleSTVarInOutDeclarationBlock= ruleSTVarInOutDeclarationBlock EOF )
            // InternalSTFunctionParser.g:745:2: iv_ruleSTVarInOutDeclarationBlock= ruleSTVarInOutDeclarationBlock EOF
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
    // InternalSTFunctionParser.g:751:1: ruleSTVarInOutDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarInOutDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:757:2: ( ( () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalSTFunctionParser.g:758:2: ( () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalSTFunctionParser.g:758:2: ( () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalSTFunctionParser.g:759:3: () otherlv_1= VAR_IN_OUT ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalSTFunctionParser.g:759:3: ()
            // InternalSTFunctionParser.g:760:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarInOutDeclarationBlockAccess().getSTVarInOutDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_IN_OUT,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarInOutDeclarationBlockAccess().getVAR_IN_OUTKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:770:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==CONSTANT) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalSTFunctionParser.g:771:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalSTFunctionParser.g:771:4: (lv_constant_2_0= CONSTANT )
                    // InternalSTFunctionParser.g:772:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_12); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:784:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==RULE_ID) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSTFunctionParser.g:785:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalSTFunctionParser.g:785:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalSTFunctionParser.g:786:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarInOutDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
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
            	    break loop19;
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
    // InternalSTFunctionParser.g:811:1: entryRuleSTVarDeclaration returns [EObject current=null] : iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF ;
    public final EObject entryRuleSTVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclaration = null;


        try {
            // InternalSTFunctionParser.g:811:57: (iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF )
            // InternalSTFunctionParser.g:812:2: iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF
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
    // InternalSTFunctionParser.g:818:1: ruleSTVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon ) ;
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
        Token otherlv_24=null;
        EObject lv_ranges_7_0 = null;

        EObject lv_ranges_9_0 = null;

        EObject lv_maxLength_19_0 = null;

        EObject lv_defaultValue_22_0 = null;

        EObject lv_pragma_23_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:824:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon ) )
            // InternalSTFunctionParser.g:825:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon )
            {
            // InternalSTFunctionParser.g:825:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon )
            // InternalSTFunctionParser.g:826:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon
            {
            // InternalSTFunctionParser.g:826:3: ()
            // InternalSTFunctionParser.g:827:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:833:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSTFunctionParser.g:834:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:834:4: (lv_name_1_0= RULE_ID )
            // InternalSTFunctionParser.g:835:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_13); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:851:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==AT) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSTFunctionParser.g:852:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalSTFunctionParser.g:856:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSTFunctionParser.g:857:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSTFunctionParser.g:857:5: (otherlv_3= RULE_ID )
                    // InternalSTFunctionParser.g:858:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Colon,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3());
              		
            }
            // InternalSTFunctionParser.g:874:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==ARRAY) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalSTFunctionParser.g:875:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalSTFunctionParser.g:875:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalSTFunctionParser.g:876:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalSTFunctionParser.g:876:5: (lv_array_5_0= ARRAY )
                    // InternalSTFunctionParser.g:877:6: lv_array_5_0= ARRAY
                    {
                    lv_array_5_0=(Token)match(input,ARRAY,FOLLOW_16); if (state.failed) return current;
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

                    // InternalSTFunctionParser.g:889:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==LeftSquareBracket) ) {
                        int LA23_1 = input.LA(2);

                        if ( (LA23_1==LDATE_AND_TIME||LA23_1==DATE_AND_TIME||LA23_1==LTIME_OF_DAY||LA23_1==TIME_OF_DAY||LA23_1==WSTRING||LA23_1==STRING||LA23_1==DWORD||LA23_1==FALSE||(LA23_1>=LDATE && LA23_1<=LWORD)||(LA23_1>=UDINT && LA23_1<=ULINT)||(LA23_1>=USINT && LA23_1<=WCHAR)||(LA23_1>=BOOL && LA23_1<=BYTE)||(LA23_1>=CHAR && LA23_1<=DINT)||(LA23_1>=LINT && LA23_1<=LTOD)||(LA23_1>=REAL && LA23_1<=SINT)||(LA23_1>=THIS && LA23_1<=TRUE)||LA23_1==UINT||LA23_1==WORD||LA23_1==AND||(LA23_1>=INT && LA23_1<=NOT)||LA23_1==TOD||LA23_1==XOR||LA23_1==DT||(LA23_1>=LD && LA23_1<=LT)||LA23_1==OR||LA23_1==LeftParenthesis||LA23_1==PlusSign||LA23_1==HyphenMinus||(LA23_1>=D && LA23_1<=T)||(LA23_1>=RULE_NON_DECIMAL && LA23_1<=RULE_INT)||(LA23_1>=RULE_ID && LA23_1<=RULE_STRING)) ) {
                            alt23=1;
                        }
                        else if ( (LA23_1==Asterisk) ) {
                            alt23=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 23, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 23, 0, input);

                        throw nvae;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalSTFunctionParser.g:890:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalSTFunctionParser.g:890:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalSTFunctionParser.g:891:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalSTFunctionParser.g:895:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalSTFunctionParser.g:896:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalSTFunctionParser.g:896:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalSTFunctionParser.g:897:8: lv_ranges_7_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_18);
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

                            // InternalSTFunctionParser.g:914:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop21:
                            do {
                                int alt21=2;
                                int LA21_0 = input.LA(1);

                                if ( (LA21_0==Comma) ) {
                                    alt21=1;
                                }


                                switch (alt21) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:915:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_17); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTFunctionParser.g:919:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalSTFunctionParser.g:920:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalSTFunctionParser.g:920:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalSTFunctionParser.g:921:9: lv_ranges_9_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_18);
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
                            	    break loop21;
                                }
                            } while (true);

                            otherlv_10=(Token)match(input,RightSquareBracket,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTFunctionParser.g:945:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalSTFunctionParser.g:945:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalSTFunctionParser.g:946:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalSTFunctionParser.g:950:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalSTFunctionParser.g:951:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalSTFunctionParser.g:951:7: (lv_count_12_0= Asterisk )
                            // InternalSTFunctionParser.g:952:8: lv_count_12_0= Asterisk
                            {
                            lv_count_12_0=(Token)match(input,Asterisk,FOLLOW_18); if (state.failed) return current;
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

                            // InternalSTFunctionParser.g:964:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop22:
                            do {
                                int alt22=2;
                                int LA22_0 = input.LA(1);

                                if ( (LA22_0==Comma) ) {
                                    alt22=1;
                                }


                                switch (alt22) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:965:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTFunctionParser.g:969:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalSTFunctionParser.g:970:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalSTFunctionParser.g:970:8: (lv_count_14_0= Asterisk )
                            	    // InternalSTFunctionParser.g:971:9: lv_count_14_0= Asterisk
                            	    {
                            	    lv_count_14_0=(Token)match(input,Asterisk,FOLLOW_18); if (state.failed) return current;
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
                            	    break loop22;
                                }
                            } while (true);

                            otherlv_15=(Token)match(input,RightSquareBracket,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_15, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,OF,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:995:3: ( ( ruleSTAnyType ) )
            // InternalSTFunctionParser.g:996:4: ( ruleSTAnyType )
            {
            // InternalSTFunctionParser.g:996:4: ( ruleSTAnyType )
            // InternalSTFunctionParser.g:997:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTVarDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0());
              				
            }
            pushFollow(FOLLOW_21);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTFunctionParser.g:1011:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==LeftSquareBracket) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSTFunctionParser.g:1012:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalSTFunctionParser.g:1016:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:1017:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:1017:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:1018:6: lv_maxLength_19_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_22);
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

                    otherlv_20=(Token)match(input,RightSquareBracket,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:1040:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==ColonEqualsSign) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalSTFunctionParser.g:1041:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_24); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalSTFunctionParser.g:1045:4: ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    // InternalSTFunctionParser.g:1046:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    {
                    // InternalSTFunctionParser.g:1046:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    // InternalSTFunctionParser.g:1047:6: lv_defaultValue_22_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_25);
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

            // InternalSTFunctionParser.g:1065:3: ( (lv_pragma_23_0= ruleSTPragma ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==LeftCurlyBracket) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalSTFunctionParser.g:1066:4: (lv_pragma_23_0= ruleSTPragma )
                    {
                    // InternalSTFunctionParser.g:1066:4: (lv_pragma_23_0= ruleSTPragma )
                    // InternalSTFunctionParser.g:1067:5: lv_pragma_23_0= ruleSTPragma
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getPragmaSTPragmaParserRuleCall_8_0());
                      				
                    }
                    pushFollow(FOLLOW_4);
                    lv_pragma_23_0=ruleSTPragma();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
                      					}
                      					set(
                      						current,
                      						"pragma",
                      						lv_pragma_23_0,
                      						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STPragma");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_24=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_24, grammarAccess.getSTVarDeclarationAccess().getSemicolonKeyword_9());
              		
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


    // $ANTLR start "entryRuleSTTypeDeclaration"
    // InternalSTFunctionParser.g:1092:1: entryRuleSTTypeDeclaration returns [EObject current=null] : iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF ;
    public final EObject entryRuleSTTypeDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTypeDeclaration = null;


        try {
            // InternalSTFunctionParser.g:1092:58: (iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF )
            // InternalSTFunctionParser.g:1093:2: iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTTypeDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTTypeDeclaration=ruleSTTypeDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTTypeDeclaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTTypeDeclaration"


    // $ANTLR start "ruleSTTypeDeclaration"
    // InternalSTFunctionParser.g:1099:1: ruleSTTypeDeclaration returns [EObject current=null] : ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) ;
    public final EObject ruleSTTypeDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_array_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_count_8_0=null;
        Token otherlv_9=null;
        Token lv_count_10_0=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        EObject lv_ranges_3_0 = null;

        EObject lv_ranges_5_0 = null;

        EObject lv_maxLength_15_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1105:2: ( ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) )
            // InternalSTFunctionParser.g:1106:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            {
            // InternalSTFunctionParser.g:1106:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            // InternalSTFunctionParser.g:1107:3: () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            {
            // InternalSTFunctionParser.g:1107:3: ()
            // InternalSTFunctionParser.g:1108:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTTypeDeclarationAccess().getSTTypeDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:1114:3: ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==ARRAY) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalSTFunctionParser.g:1115:4: ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF
                    {
                    // InternalSTFunctionParser.g:1115:4: ( (lv_array_1_0= ARRAY ) )
                    // InternalSTFunctionParser.g:1116:5: (lv_array_1_0= ARRAY )
                    {
                    // InternalSTFunctionParser.g:1116:5: (lv_array_1_0= ARRAY )
                    // InternalSTFunctionParser.g:1117:6: lv_array_1_0= ARRAY
                    {
                    lv_array_1_0=(Token)match(input,ARRAY,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_array_1_0, grammarAccess.getSTTypeDeclarationAccess().getArrayARRAYKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
                      						}
                      						setWithLastConsumed(current, "array", lv_array_1_0 != null, "ARRAY");
                      					
                    }

                    }


                    }

                    // InternalSTFunctionParser.g:1129:4: ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) )
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==LeftSquareBracket) ) {
                        int LA30_1 = input.LA(2);

                        if ( (LA30_1==LDATE_AND_TIME||LA30_1==DATE_AND_TIME||LA30_1==LTIME_OF_DAY||LA30_1==TIME_OF_DAY||LA30_1==WSTRING||LA30_1==STRING||LA30_1==DWORD||LA30_1==FALSE||(LA30_1>=LDATE && LA30_1<=LWORD)||(LA30_1>=UDINT && LA30_1<=ULINT)||(LA30_1>=USINT && LA30_1<=WCHAR)||(LA30_1>=BOOL && LA30_1<=BYTE)||(LA30_1>=CHAR && LA30_1<=DINT)||(LA30_1>=LINT && LA30_1<=LTOD)||(LA30_1>=REAL && LA30_1<=SINT)||(LA30_1>=THIS && LA30_1<=TRUE)||LA30_1==UINT||LA30_1==WORD||LA30_1==AND||(LA30_1>=INT && LA30_1<=NOT)||LA30_1==TOD||LA30_1==XOR||LA30_1==DT||(LA30_1>=LD && LA30_1<=LT)||LA30_1==OR||LA30_1==LeftParenthesis||LA30_1==PlusSign||LA30_1==HyphenMinus||(LA30_1>=D && LA30_1<=T)||(LA30_1>=RULE_NON_DECIMAL && LA30_1<=RULE_INT)||(LA30_1>=RULE_ID && LA30_1<=RULE_STRING)) ) {
                            alt30=1;
                        }
                        else if ( (LA30_1==Asterisk) ) {
                            alt30=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 0, input);

                        throw nvae;
                    }
                    switch (alt30) {
                        case 1 :
                            // InternalSTFunctionParser.g:1130:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            {
                            // InternalSTFunctionParser.g:1130:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            // InternalSTFunctionParser.g:1131:6: otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket
                            {
                            otherlv_2=(Token)match(input,LeftSquareBracket,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_0_0());
                              					
                            }
                            // InternalSTFunctionParser.g:1135:6: ( (lv_ranges_3_0= ruleSTExpression ) )
                            // InternalSTFunctionParser.g:1136:7: (lv_ranges_3_0= ruleSTExpression )
                            {
                            // InternalSTFunctionParser.g:1136:7: (lv_ranges_3_0= ruleSTExpression )
                            // InternalSTFunctionParser.g:1137:8: lv_ranges_3_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_18);
                            lv_ranges_3_0=ruleSTExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getSTTypeDeclarationRule());
                              								}
                              								add(
                              									current,
                              									"ranges",
                              									lv_ranges_3_0,
                              									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            // InternalSTFunctionParser.g:1154:6: (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )*
                            loop28:
                            do {
                                int alt28=2;
                                int LA28_0 = input.LA(1);

                                if ( (LA28_0==Comma) ) {
                                    alt28=1;
                                }


                                switch (alt28) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:1155:7: otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_17); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_4, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalSTFunctionParser.g:1159:7: ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    // InternalSTFunctionParser.g:1160:8: (lv_ranges_5_0= ruleSTExpression )
                            	    {
                            	    // InternalSTFunctionParser.g:1160:8: (lv_ranges_5_0= ruleSTExpression )
                            	    // InternalSTFunctionParser.g:1161:9: lv_ranges_5_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_18);
                            	    lv_ranges_5_0=ruleSTExpression();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElementForParent(grammarAccess.getSTTypeDeclarationRule());
                            	      									}
                            	      									add(
                            	      										current,
                            	      										"ranges",
                            	      										lv_ranges_5_0,
                            	      										"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                            	      									afterParserOrEnumRuleCall();
                            	      								
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop28;
                                }
                            } while (true);

                            otherlv_6=(Token)match(input,RightSquareBracket,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSTFunctionParser.g:1185:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            {
                            // InternalSTFunctionParser.g:1185:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            // InternalSTFunctionParser.g:1186:6: otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket
                            {
                            otherlv_7=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_1_0());
                              					
                            }
                            // InternalSTFunctionParser.g:1190:6: ( (lv_count_8_0= Asterisk ) )
                            // InternalSTFunctionParser.g:1191:7: (lv_count_8_0= Asterisk )
                            {
                            // InternalSTFunctionParser.g:1191:7: (lv_count_8_0= Asterisk )
                            // InternalSTFunctionParser.g:1192:8: lv_count_8_0= Asterisk
                            {
                            lv_count_8_0=(Token)match(input,Asterisk,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_count_8_0, grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
                              								}
                              								addWithLastConsumed(current, "count", lv_count_8_0, "*");
                              							
                            }

                            }


                            }

                            // InternalSTFunctionParser.g:1204:6: (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )*
                            loop29:
                            do {
                                int alt29=2;
                                int LA29_0 = input.LA(1);

                                if ( (LA29_0==Comma) ) {
                                    alt29=1;
                                }


                                switch (alt29) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:1205:7: otherlv_9= Comma ( (lv_count_10_0= Asterisk ) )
                            	    {
                            	    otherlv_9=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_9, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalSTFunctionParser.g:1209:7: ( (lv_count_10_0= Asterisk ) )
                            	    // InternalSTFunctionParser.g:1210:8: (lv_count_10_0= Asterisk )
                            	    {
                            	    // InternalSTFunctionParser.g:1210:8: (lv_count_10_0= Asterisk )
                            	    // InternalSTFunctionParser.g:1211:9: lv_count_10_0= Asterisk
                            	    {
                            	    lv_count_10_0=(Token)match(input,Asterisk,FOLLOW_18); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									newLeafNode(lv_count_10_0, grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_2_1_0());
                            	      								
                            	    }
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
                            	      									}
                            	      									addWithLastConsumed(current, "count", lv_count_10_0, "*");
                            	      								
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop29;
                                }
                            } while (true);

                            otherlv_11=(Token)match(input,RightSquareBracket,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_12=(Token)match(input,OF,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getSTTypeDeclarationAccess().getOFKeyword_1_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:1235:3: ( ( ruleSTAnyType ) )
            // InternalSTFunctionParser.g:1236:4: ( ruleSTAnyType )
            {
            // InternalSTFunctionParser.g:1236:4: ( ruleSTAnyType )
            // InternalSTFunctionParser.g:1237:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementCrossReference_2_0());
              				
            }
            pushFollow(FOLLOW_26);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTFunctionParser.g:1251:3: (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==LeftSquareBracket) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalSTFunctionParser.g:1252:4: otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket
                    {
                    otherlv_14=(Token)match(input,LeftSquareBracket,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_3_0());
                      			
                    }
                    // InternalSTFunctionParser.g:1256:4: ( (lv_maxLength_15_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:1257:5: (lv_maxLength_15_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:1257:5: (lv_maxLength_15_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:1258:6: lv_maxLength_15_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_22);
                    lv_maxLength_15_0=ruleSTExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTTypeDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"maxLength",
                      							lv_maxLength_15_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_16=(Token)match(input,RightSquareBracket,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_3_2());
                      			
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
    // $ANTLR end "ruleSTTypeDeclaration"


    // $ANTLR start "entryRuleSTInitializerExpression"
    // InternalSTFunctionParser.g:1284:1: entryRuleSTInitializerExpression returns [EObject current=null] : iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF ;
    public final EObject entryRuleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:1284:64: (iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF )
            // InternalSTFunctionParser.g:1285:2: iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF
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
    // InternalSTFunctionParser.g:1291:1: ruleSTInitializerExpression returns [EObject current=null] : (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) ;
    public final EObject ruleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STElementaryInitializerExpression_0 = null;

        EObject this_STArrayInitializerExpression_1 = null;

        EObject this_STStructInitializerExpression_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1297:2: ( (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) )
            // InternalSTFunctionParser.g:1298:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            {
            // InternalSTFunctionParser.g:1298:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            int alt33=3;
            alt33 = dfa33.predict(input);
            switch (alt33) {
                case 1 :
                    // InternalSTFunctionParser.g:1299:3: this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression
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
                    // InternalSTFunctionParser.g:1308:3: this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression
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
                case 3 :
                    // InternalSTFunctionParser.g:1317:3: this_STStructInitializerExpression_2= ruleSTStructInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTInitializerExpressionAccess().getSTStructInitializerExpressionParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STStructInitializerExpression_2=ruleSTStructInitializerExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STStructInitializerExpression_2;
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
    // InternalSTFunctionParser.g:1329:1: entryRuleSTElementaryInitializerExpression returns [EObject current=null] : iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF ;
    public final EObject entryRuleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElementaryInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:1329:74: (iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF )
            // InternalSTFunctionParser.g:1330:2: iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF
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
    // InternalSTFunctionParser.g:1336:1: ruleSTElementaryInitializerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1342:2: ( ( (lv_value_0_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:1343:2: ( (lv_value_0_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:1343:2: ( (lv_value_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:1344:3: (lv_value_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:1344:3: (lv_value_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:1345:4: lv_value_0_0= ruleSTExpression
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
    // InternalSTFunctionParser.g:1365:1: entryRuleSTArrayInitializerExpression returns [EObject current=null] : iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF ;
    public final EObject entryRuleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:1365:69: (iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF )
            // InternalSTFunctionParser.g:1366:2: iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF
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
    // InternalSTFunctionParser.g:1372:1: ruleSTArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1378:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalSTFunctionParser.g:1379:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalSTFunctionParser.g:1379:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalSTFunctionParser.g:1380:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:1384:3: ( (lv_values_1_0= ruleSTArrayInitElement ) )
            // InternalSTFunctionParser.g:1385:4: (lv_values_1_0= ruleSTArrayInitElement )
            {
            // InternalSTFunctionParser.g:1385:4: (lv_values_1_0= ruleSTArrayInitElement )
            // InternalSTFunctionParser.g:1386:5: lv_values_1_0= ruleSTArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_18);
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

            // InternalSTFunctionParser.g:1403:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==Comma) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1404:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_24); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:1408:4: ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    // InternalSTFunctionParser.g:1409:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    {
            	    // InternalSTFunctionParser.g:1409:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    // InternalSTFunctionParser.g:1410:6: lv_values_3_0= ruleSTArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_18);
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
            	    break loop34;
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
    // InternalSTFunctionParser.g:1436:1: entryRuleSTArrayInitElement returns [EObject current=null] : iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF ;
    public final EObject entryRuleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitElement = null;


        try {
            // InternalSTFunctionParser.g:1436:59: (iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF )
            // InternalSTFunctionParser.g:1437:2: iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF
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
    // InternalSTFunctionParser.g:1443:1: ruleSTArrayInitElement returns [EObject current=null] : (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement ) ;
    public final EObject ruleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject this_STSingleArrayInitElement_0 = null;

        EObject this_STRepeatArrayInitElement_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1449:2: ( (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement ) )
            // InternalSTFunctionParser.g:1450:2: (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement )
            {
            // InternalSTFunctionParser.g:1450:2: (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==LDATE_AND_TIME||LA35_0==DATE_AND_TIME||LA35_0==LTIME_OF_DAY||LA35_0==TIME_OF_DAY||LA35_0==WSTRING||LA35_0==STRING||LA35_0==DWORD||LA35_0==FALSE||(LA35_0>=LDATE && LA35_0<=LWORD)||(LA35_0>=UDINT && LA35_0<=ULINT)||(LA35_0>=USINT && LA35_0<=WCHAR)||(LA35_0>=BOOL && LA35_0<=BYTE)||(LA35_0>=CHAR && LA35_0<=DINT)||(LA35_0>=LINT && LA35_0<=LTOD)||(LA35_0>=REAL && LA35_0<=SINT)||(LA35_0>=THIS && LA35_0<=TRUE)||LA35_0==UINT||LA35_0==WORD||LA35_0==AND||(LA35_0>=INT && LA35_0<=NOT)||LA35_0==TOD||LA35_0==XOR||LA35_0==DT||(LA35_0>=LD && LA35_0<=LT)||LA35_0==OR||LA35_0==LeftParenthesis||LA35_0==PlusSign||LA35_0==HyphenMinus||(LA35_0>=D && LA35_0<=LeftSquareBracket)||LA35_0==RULE_NON_DECIMAL||(LA35_0>=RULE_ID && LA35_0<=RULE_STRING)) ) {
                alt35=1;
            }
            else if ( (LA35_0==RULE_INT) ) {
                int LA35_2 = input.LA(2);

                if ( (LA35_2==EOF||LA35_2==AND||LA35_2==MOD||LA35_2==XOR||(LA35_2>=AsteriskAsterisk && LA35_2<=FullStopFullStop)||(LA35_2>=LessThanSignEqualsSign && LA35_2<=LessThanSignGreaterThanSign)||LA35_2==GreaterThanSignEqualsSign||LA35_2==OR||LA35_2==Ampersand||(LA35_2>=Asterisk && LA35_2<=Solidus)||(LA35_2>=LessThanSign && LA35_2<=GreaterThanSign)||LA35_2==RightSquareBracket) ) {
                    alt35=1;
                }
                else if ( (LA35_2==LeftParenthesis) ) {
                    alt35=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // InternalSTFunctionParser.g:1451:3: this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getSTSingleArrayInitElementParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STSingleArrayInitElement_0=ruleSTSingleArrayInitElement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STSingleArrayInitElement_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:1460:3: this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getSTRepeatArrayInitElementParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STRepeatArrayInitElement_1=ruleSTRepeatArrayInitElement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STRepeatArrayInitElement_1;
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
    // $ANTLR end "ruleSTArrayInitElement"


    // $ANTLR start "entryRuleSTSingleArrayInitElement"
    // InternalSTFunctionParser.g:1472:1: entryRuleSTSingleArrayInitElement returns [EObject current=null] : iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF ;
    public final EObject entryRuleSTSingleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSingleArrayInitElement = null;


        try {
            // InternalSTFunctionParser.g:1472:65: (iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF )
            // InternalSTFunctionParser.g:1473:2: iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTSingleArrayInitElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTSingleArrayInitElement=ruleSTSingleArrayInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTSingleArrayInitElement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTSingleArrayInitElement"


    // $ANTLR start "ruleSTSingleArrayInitElement"
    // InternalSTFunctionParser.g:1479:1: ruleSTSingleArrayInitElement returns [EObject current=null] : ( (lv_initExpression_0_0= ruleSTInitializerExpression ) ) ;
    public final EObject ruleSTSingleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject lv_initExpression_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1485:2: ( ( (lv_initExpression_0_0= ruleSTInitializerExpression ) ) )
            // InternalSTFunctionParser.g:1486:2: ( (lv_initExpression_0_0= ruleSTInitializerExpression ) )
            {
            // InternalSTFunctionParser.g:1486:2: ( (lv_initExpression_0_0= ruleSTInitializerExpression ) )
            // InternalSTFunctionParser.g:1487:3: (lv_initExpression_0_0= ruleSTInitializerExpression )
            {
            // InternalSTFunctionParser.g:1487:3: (lv_initExpression_0_0= ruleSTInitializerExpression )
            // InternalSTFunctionParser.g:1488:4: lv_initExpression_0_0= ruleSTInitializerExpression
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getSTSingleArrayInitElementAccess().getInitExpressionSTInitializerExpressionParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_initExpression_0_0=ruleSTInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getSTSingleArrayInitElementRule());
              				}
              				set(
              					current,
              					"initExpression",
              					lv_initExpression_0_0,
              					"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
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
    // $ANTLR end "ruleSTSingleArrayInitElement"


    // $ANTLR start "entryRuleSTRepeatArrayInitElement"
    // InternalSTFunctionParser.g:1508:1: entryRuleSTRepeatArrayInitElement returns [EObject current=null] : iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF ;
    public final EObject entryRuleSTRepeatArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatArrayInitElement = null;


        try {
            // InternalSTFunctionParser.g:1508:65: (iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF )
            // InternalSTFunctionParser.g:1509:2: iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTRepeatArrayInitElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTRepeatArrayInitElement=ruleSTRepeatArrayInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTRepeatArrayInitElement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTRepeatArrayInitElement"


    // $ANTLR start "ruleSTRepeatArrayInitElement"
    // InternalSTFunctionParser.g:1515:1: ruleSTRepeatArrayInitElement returns [EObject current=null] : ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis ) ;
    public final EObject ruleSTRepeatArrayInitElement() throws RecognitionException {
        EObject current = null;

        Token lv_repetitions_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_initExpressions_2_0 = null;

        EObject lv_initExpressions_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1521:2: ( ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis ) )
            // InternalSTFunctionParser.g:1522:2: ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )
            {
            // InternalSTFunctionParser.g:1522:2: ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )
            // InternalSTFunctionParser.g:1523:3: ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis
            {
            // InternalSTFunctionParser.g:1523:3: ( (lv_repetitions_0_0= RULE_INT ) )
            // InternalSTFunctionParser.g:1524:4: (lv_repetitions_0_0= RULE_INT )
            {
            // InternalSTFunctionParser.g:1524:4: (lv_repetitions_0_0= RULE_INT )
            // InternalSTFunctionParser.g:1525:5: lv_repetitions_0_0= RULE_INT
            {
            lv_repetitions_0_0=(Token)match(input,RULE_INT,FOLLOW_27); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_repetitions_0_0, grammarAccess.getSTRepeatArrayInitElementAccess().getRepetitionsINTTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTRepeatArrayInitElementRule());
              					}
              					setWithLastConsumed(
              						current,
              						"repetitions",
              						lv_repetitions_0_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.INT");
              				
            }

            }


            }

            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTRepeatArrayInitElementAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1545:3: ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) )
            // InternalSTFunctionParser.g:1546:4: (lv_initExpressions_2_0= ruleSTInitializerExpression )
            {
            // InternalSTFunctionParser.g:1546:4: (lv_initExpressions_2_0= ruleSTInitializerExpression )
            // InternalSTFunctionParser.g:1547:5: lv_initExpressions_2_0= ruleSTInitializerExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_28);
            lv_initExpressions_2_0=ruleSTInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTRepeatArrayInitElementRule());
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

            // InternalSTFunctionParser.g:1564:3: (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==Comma) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1565:4: otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_24); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getSTRepeatArrayInitElementAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:1569:4: ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
            	    // InternalSTFunctionParser.g:1570:5: (lv_initExpressions_4_0= ruleSTInitializerExpression )
            	    {
            	    // InternalSTFunctionParser.g:1570:5: (lv_initExpressions_4_0= ruleSTInitializerExpression )
            	    // InternalSTFunctionParser.g:1571:6: lv_initExpressions_4_0= ruleSTInitializerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_3_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_28);
            	    lv_initExpressions_4_0=ruleSTInitializerExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTRepeatArrayInitElementRule());
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
            	    break loop36;
                }
            } while (true);

            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getSTRepeatArrayInitElementAccess().getRightParenthesisKeyword_4());
              		
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
    // $ANTLR end "ruleSTRepeatArrayInitElement"


    // $ANTLR start "entryRuleSTStructInitializerExpression"
    // InternalSTFunctionParser.g:1597:1: entryRuleSTStructInitializerExpression returns [EObject current=null] : iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF ;
    public final EObject entryRuleSTStructInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitializerExpression = null;


        try {
            // InternalSTFunctionParser.g:1597:70: (iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF )
            // InternalSTFunctionParser.g:1598:2: iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTStructInitializerExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTStructInitializerExpression=ruleSTStructInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTStructInitializerExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTStructInitializerExpression"


    // $ANTLR start "ruleSTStructInitializerExpression"
    // InternalSTFunctionParser.g:1604:1: ruleSTStructInitializerExpression returns [EObject current=null] : ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis ) ;
    public final EObject ruleSTStructInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_values_3_0 = null;

        EObject lv_values_5_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1610:2: ( ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis ) )
            // InternalSTFunctionParser.g:1611:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis )
            {
            // InternalSTFunctionParser.g:1611:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis )
            // InternalSTFunctionParser.g:1612:3: ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis
            {
            // InternalSTFunctionParser.g:1612:3: ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_ID) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalSTFunctionParser.g:1613:4: ( ( ruleQualifiedName ) ) otherlv_1= NumberSign
                    {
                    // InternalSTFunctionParser.g:1613:4: ( ( ruleQualifiedName ) )
                    // InternalSTFunctionParser.g:1614:5: ( ruleQualifiedName )
                    {
                    // InternalSTFunctionParser.g:1614:5: ( ruleQualifiedName )
                    // InternalSTFunctionParser.g:1615:6: ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStructInitializerExpressionRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getTypeStructuredTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
                    ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_27); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStructInitializerExpressionAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTStructInitializerExpressionAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1638:3: ( (lv_values_3_0= ruleSTStructInitElement ) )
            // InternalSTFunctionParser.g:1639:4: (lv_values_3_0= ruleSTStructInitElement )
            {
            // InternalSTFunctionParser.g:1639:4: (lv_values_3_0= ruleSTStructInitElement )
            // InternalSTFunctionParser.g:1640:5: lv_values_3_0= ruleSTStructInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_28);
            lv_values_3_0=ruleSTStructInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTStructInitializerExpressionRule());
              					}
              					add(
              						current,
              						"values",
              						lv_values_3_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStructInitElement");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTFunctionParser.g:1657:3: (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==Comma) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1658:4: otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) )
            	    {
            	    otherlv_4=(Token)match(input,Comma,FOLLOW_30); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getSTStructInitializerExpressionAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:1662:4: ( (lv_values_5_0= ruleSTStructInitElement ) )
            	    // InternalSTFunctionParser.g:1663:5: (lv_values_5_0= ruleSTStructInitElement )
            	    {
            	    // InternalSTFunctionParser.g:1663:5: (lv_values_5_0= ruleSTStructInitElement )
            	    // InternalSTFunctionParser.g:1664:6: lv_values_5_0= ruleSTStructInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_3_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_28);
            	    lv_values_5_0=ruleSTStructInitElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTStructInitializerExpressionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"values",
            	      							lv_values_5_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStructInitElement");
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

            otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getSTStructInitializerExpressionAccess().getRightParenthesisKeyword_4());
              		
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
    // $ANTLR end "ruleSTStructInitializerExpression"


    // $ANTLR start "entryRuleSTStructInitElement"
    // InternalSTFunctionParser.g:1690:1: entryRuleSTStructInitElement returns [EObject current=null] : iv_ruleSTStructInitElement= ruleSTStructInitElement EOF ;
    public final EObject entryRuleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitElement = null;


        try {
            // InternalSTFunctionParser.g:1690:60: (iv_ruleSTStructInitElement= ruleSTStructInitElement EOF )
            // InternalSTFunctionParser.g:1691:2: iv_ruleSTStructInitElement= ruleSTStructInitElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTStructInitElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTStructInitElement=ruleSTStructInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTStructInitElement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTStructInitElement"


    // $ANTLR start "ruleSTStructInitElement"
    // InternalSTFunctionParser.g:1697:1: ruleSTStructInitElement returns [EObject current=null] : ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) ;
    public final EObject ruleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1703:2: ( ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) )
            // InternalSTFunctionParser.g:1704:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            {
            // InternalSTFunctionParser.g:1704:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            // InternalSTFunctionParser.g:1705:3: ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) )
            {
            // InternalSTFunctionParser.g:1705:3: ( ( ruleSTFeatureName ) )
            // InternalSTFunctionParser.g:1706:4: ( ruleSTFeatureName )
            {
            // InternalSTFunctionParser.g:1706:4: ( ruleSTFeatureName )
            // InternalSTFunctionParser.g:1707:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTStructInitElementRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_31);
            ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTStructInitElementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1725:3: ( (lv_value_2_0= ruleSTInitializerExpression ) )
            // InternalSTFunctionParser.g:1726:4: (lv_value_2_0= ruleSTInitializerExpression )
            {
            // InternalSTFunctionParser.g:1726:4: (lv_value_2_0= ruleSTInitializerExpression )
            // InternalSTFunctionParser.g:1727:5: lv_value_2_0= ruleSTInitializerExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitElementAccess().getValueSTInitializerExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleSTInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTStructInitElementRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
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
    // $ANTLR end "ruleSTStructInitElement"


    // $ANTLR start "entryRuleSTPragma"
    // InternalSTFunctionParser.g:1748:1: entryRuleSTPragma returns [EObject current=null] : iv_ruleSTPragma= ruleSTPragma EOF ;
    public final EObject entryRuleSTPragma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPragma = null;


        try {
            // InternalSTFunctionParser.g:1748:49: (iv_ruleSTPragma= ruleSTPragma EOF )
            // InternalSTFunctionParser.g:1749:2: iv_ruleSTPragma= ruleSTPragma EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTPragmaRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTPragma=ruleSTPragma();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTPragma; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTPragma"


    // $ANTLR start "ruleSTPragma"
    // InternalSTFunctionParser.g:1755:1: ruleSTPragma returns [EObject current=null] : ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket ) ;
    public final EObject ruleSTPragma() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_attributes_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1761:2: ( ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket ) )
            // InternalSTFunctionParser.g:1762:2: ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket )
            {
            // InternalSTFunctionParser.g:1762:2: ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket )
            // InternalSTFunctionParser.g:1763:3: () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket
            {
            // InternalSTFunctionParser.g:1763:3: ()
            // InternalSTFunctionParser.g:1764:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTPragmaAccess().getSTPragmaAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,LeftCurlyBracket,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTPragmaAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1774:3: ( (lv_attributes_2_0= ruleSTAttribute ) )
            // InternalSTFunctionParser.g:1775:4: (lv_attributes_2_0= ruleSTAttribute )
            {
            // InternalSTFunctionParser.g:1775:4: (lv_attributes_2_0= ruleSTAttribute )
            // InternalSTFunctionParser.g:1776:5: lv_attributes_2_0= ruleSTAttribute
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_32);
            lv_attributes_2_0=ruleSTAttribute();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTPragmaRule());
              					}
              					add(
              						current,
              						"attributes",
              						lv_attributes_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAttribute");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTFunctionParser.g:1793:3: (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==Comma) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSTFunctionParser.g:1794:4: otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getSTPragmaAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:1798:4: ( (lv_attributes_4_0= ruleSTAttribute ) )
            	    // InternalSTFunctionParser.g:1799:5: (lv_attributes_4_0= ruleSTAttribute )
            	    {
            	    // InternalSTFunctionParser.g:1799:5: (lv_attributes_4_0= ruleSTAttribute )
            	    // InternalSTFunctionParser.g:1800:6: lv_attributes_4_0= ruleSTAttribute
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_3_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_32);
            	    lv_attributes_4_0=ruleSTAttribute();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSTPragmaRule());
            	      						}
            	      						add(
            	      							current,
            	      							"attributes",
            	      							lv_attributes_4_0,
            	      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAttribute");
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

            otherlv_5=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getSTPragmaAccess().getRightCurlyBracketKeyword_4());
              		
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
    // $ANTLR end "ruleSTPragma"


    // $ANTLR start "entryRuleSTAttribute"
    // InternalSTFunctionParser.g:1826:1: entryRuleSTAttribute returns [EObject current=null] : iv_ruleSTAttribute= ruleSTAttribute EOF ;
    public final EObject entryRuleSTAttribute() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAttribute = null;


        try {
            // InternalSTFunctionParser.g:1826:52: (iv_ruleSTAttribute= ruleSTAttribute EOF )
            // InternalSTFunctionParser.g:1827:2: iv_ruleSTAttribute= ruleSTAttribute EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAttributeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAttribute=ruleSTAttribute();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAttribute; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAttribute"


    // $ANTLR start "ruleSTAttribute"
    // InternalSTFunctionParser.g:1833:1: ruleSTAttribute returns [EObject current=null] : ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) ;
    public final EObject ruleSTAttribute() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1839:2: ( ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) )
            // InternalSTFunctionParser.g:1840:2: ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            {
            // InternalSTFunctionParser.g:1840:2: ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            // InternalSTFunctionParser.g:1841:3: ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) )
            {
            // InternalSTFunctionParser.g:1841:3: ( ( ruleSTAttributeName ) )
            // InternalSTFunctionParser.g:1842:4: ( ruleSTAttributeName )
            {
            // InternalSTFunctionParser.g:1842:4: ( ruleSTAttributeName )
            // InternalSTFunctionParser.g:1843:5: ruleSTAttributeName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTAttributeRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAttributeAccess().getDeclarationAttributeDeclarationCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_31);
            ruleSTAttributeName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTAttributeAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:1861:3: ( (lv_value_2_0= ruleSTInitializerExpression ) )
            // InternalSTFunctionParser.g:1862:4: (lv_value_2_0= ruleSTInitializerExpression )
            {
            // InternalSTFunctionParser.g:1862:4: (lv_value_2_0= ruleSTInitializerExpression )
            // InternalSTFunctionParser.g:1863:5: lv_value_2_0= ruleSTInitializerExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAttributeAccess().getValueSTInitializerExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleSTInitializerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTAttributeRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
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
    // $ANTLR end "ruleSTAttribute"


    // $ANTLR start "entryRuleSTAttributeName"
    // InternalSTFunctionParser.g:1884:1: entryRuleSTAttributeName returns [String current=null] : iv_ruleSTAttributeName= ruleSTAttributeName EOF ;
    public final String entryRuleSTAttributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAttributeName = null;


        try {
            // InternalSTFunctionParser.g:1884:55: (iv_ruleSTAttributeName= ruleSTAttributeName EOF )
            // InternalSTFunctionParser.g:1885:2: iv_ruleSTAttributeName= ruleSTAttributeName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAttributeNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAttributeName=ruleSTAttributeName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAttributeName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAttributeName"


    // $ANTLR start "ruleSTAttributeName"
    // InternalSTFunctionParser.g:1891:1: ruleSTAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_QualifiedName_0= ruleQualifiedName ;
    public final AntlrDatatypeRuleToken ruleSTAttributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1897:2: (this_QualifiedName_0= ruleQualifiedName )
            // InternalSTFunctionParser.g:1898:2: this_QualifiedName_0= ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getSTAttributeNameAccess().getQualifiedNameParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_QualifiedName_0);
              	
            }
            if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleSTAttributeName"


    // $ANTLR start "entryRuleSTStatement"
    // InternalSTFunctionParser.g:1911:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalSTFunctionParser.g:1911:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalSTFunctionParser.g:1912:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalSTFunctionParser.g:1918:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) ;
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

        EObject this_STAssignment_5 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:1924:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) )
            // InternalSTFunctionParser.g:1925:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            {
            // InternalSTFunctionParser.g:1925:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==LDATE_AND_TIME||LA41_0==DATE_AND_TIME||LA41_0==LTIME_OF_DAY||LA41_0==TIME_OF_DAY||LA41_0==CONTINUE||LA41_0==WSTRING||LA41_0==REPEAT||LA41_0==RETURN||LA41_0==STRING||LA41_0==DWORD||LA41_0==FALSE||(LA41_0>=LDATE && LA41_0<=LWORD)||(LA41_0>=UDINT && LA41_0<=ULINT)||(LA41_0>=USINT && LA41_0<=DINT)||LA41_0==EXIT||(LA41_0>=LINT && LA41_0<=LTOD)||(LA41_0>=REAL && LA41_0<=SINT)||(LA41_0>=THIS && LA41_0<=TRUE)||LA41_0==UINT||LA41_0==WORD||(LA41_0>=AND && LA41_0<=NOT)||LA41_0==TOD||LA41_0==XOR||(LA41_0>=DT && LA41_0<=LT)||LA41_0==OR||LA41_0==LeftParenthesis||LA41_0==PlusSign||LA41_0==HyphenMinus||(LA41_0>=D && LA41_0<=T)||(LA41_0>=RULE_NON_DECIMAL && LA41_0<=RULE_INT)||(LA41_0>=RULE_ID && LA41_0<=RULE_STRING)) ) {
                alt41=1;
            }
            else if ( (LA41_0==Semicolon) ) {
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
                    // InternalSTFunctionParser.g:1926:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    {
                    // InternalSTFunctionParser.g:1926:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    // InternalSTFunctionParser.g:1927:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon
                    {
                    // InternalSTFunctionParser.g:1927:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )
                    int alt40=9;
                    alt40 = dfa40.predict(input);
                    switch (alt40) {
                        case 1 :
                            // InternalSTFunctionParser.g:1928:5: this_STIfStatement_0= ruleSTIfStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTIfStatementParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_4);
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
                            // InternalSTFunctionParser.g:1937:5: this_STCaseStatement_1= ruleSTCaseStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCaseStatementParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_4);
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
                            // InternalSTFunctionParser.g:1946:5: this_STForStatement_2= ruleSTForStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTForStatementParserRuleCall_0_0_2());
                              				
                            }
                            pushFollow(FOLLOW_4);
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
                            // InternalSTFunctionParser.g:1955:5: this_STWhileStatement_3= ruleSTWhileStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTWhileStatementParserRuleCall_0_0_3());
                              				
                            }
                            pushFollow(FOLLOW_4);
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
                            // InternalSTFunctionParser.g:1964:5: this_STRepeatStatement_4= ruleSTRepeatStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTRepeatStatementParserRuleCall_0_0_4());
                              				
                            }
                            pushFollow(FOLLOW_4);
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
                            // InternalSTFunctionParser.g:1973:5: this_STAssignment_5= ruleSTAssignment
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentParserRuleCall_0_0_5());
                              				
                            }
                            pushFollow(FOLLOW_4);
                            this_STAssignment_5=ruleSTAssignment();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_STAssignment_5;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 7 :
                            // InternalSTFunctionParser.g:1982:5: ( () otherlv_7= RETURN )
                            {
                            // InternalSTFunctionParser.g:1982:5: ( () otherlv_7= RETURN )
                            // InternalSTFunctionParser.g:1983:6: () otherlv_7= RETURN
                            {
                            // InternalSTFunctionParser.g:1983:6: ()
                            // InternalSTFunctionParser.g:1984:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_6_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_7=(Token)match(input,RETURN,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_6_1());
                              					
                            }

                            }


                            }
                            break;
                        case 8 :
                            // InternalSTFunctionParser.g:1996:5: ( () otherlv_9= CONTINUE )
                            {
                            // InternalSTFunctionParser.g:1996:5: ( () otherlv_9= CONTINUE )
                            // InternalSTFunctionParser.g:1997:6: () otherlv_9= CONTINUE
                            {
                            // InternalSTFunctionParser.g:1997:6: ()
                            // InternalSTFunctionParser.g:1998:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_7_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_9=(Token)match(input,CONTINUE,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_9, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_7_1());
                              					
                            }

                            }


                            }
                            break;
                        case 9 :
                            // InternalSTFunctionParser.g:2010:5: ( () otherlv_11= EXIT )
                            {
                            // InternalSTFunctionParser.g:2010:5: ( () otherlv_11= EXIT )
                            // InternalSTFunctionParser.g:2011:6: () otherlv_11= EXIT
                            {
                            // InternalSTFunctionParser.g:2011:6: ()
                            // InternalSTFunctionParser.g:2012:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTExitAction_0_0_8_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_11=(Token)match(input,EXIT,FOLLOW_4); if (state.failed) return current;
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
                    // InternalSTFunctionParser.g:2030:3: ( () otherlv_14= Semicolon )
                    {
                    // InternalSTFunctionParser.g:2030:3: ( () otherlv_14= Semicolon )
                    // InternalSTFunctionParser.g:2031:4: () otherlv_14= Semicolon
                    {
                    // InternalSTFunctionParser.g:2031:4: ()
                    // InternalSTFunctionParser.g:2032:5: 
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


    // $ANTLR start "entryRuleSTAssignment"
    // InternalSTFunctionParser.g:2047:1: entryRuleSTAssignment returns [EObject current=null] : iv_ruleSTAssignment= ruleSTAssignment EOF ;
    public final EObject entryRuleSTAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignment = null;


        try {
            // InternalSTFunctionParser.g:2047:53: (iv_ruleSTAssignment= ruleSTAssignment EOF )
            // InternalSTFunctionParser.g:2048:2: iv_ruleSTAssignment= ruleSTAssignment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTAssignmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTAssignment=ruleSTAssignment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTAssignment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTAssignment"


    // $ANTLR start "ruleSTAssignment"
    // InternalSTFunctionParser.g:2054:1: ruleSTAssignment returns [EObject current=null] : (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? ) ;
    public final EObject ruleSTAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_STExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2060:2: ( (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? ) )
            // InternalSTFunctionParser.g:2061:2: (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? )
            {
            // InternalSTFunctionParser.g:2061:2: (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? )
            // InternalSTFunctionParser.g:2062:3: this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAssignmentAccess().getSTExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_33);
            this_STExpression_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:2070:3: ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==ColonEqualsSign) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalSTFunctionParser.g:2071:4: () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) )
                    {
                    // InternalSTFunctionParser.g:2071:4: ()
                    // InternalSTFunctionParser.g:2072:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElementAndSet(
                      						grammarAccess.getSTAssignmentAccess().getSTAssignmentLeftAction_1_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTAssignmentAccess().getColonEqualsSignKeyword_1_1());
                      			
                    }
                    // InternalSTFunctionParser.g:2082:4: ( (lv_right_3_0= ruleSTAssignment ) )
                    // InternalSTFunctionParser.g:2083:5: (lv_right_3_0= ruleSTAssignment )
                    {
                    // InternalSTFunctionParser.g:2083:5: (lv_right_3_0= ruleSTAssignment )
                    // InternalSTFunctionParser.g:2084:6: lv_right_3_0= ruleSTAssignment
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTAssignmentAccess().getRightSTAssignmentParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_3_0=ruleSTAssignment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTAssignmentRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAssignment");
                      						afterParserOrEnumRuleCall();
                      					
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
    // $ANTLR end "ruleSTAssignment"


    // $ANTLR start "entryRuleSTCallArgument"
    // InternalSTFunctionParser.g:2106:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalSTFunctionParser.g:2106:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalSTFunctionParser.g:2107:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
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
    // InternalSTFunctionParser.g:2113:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2119:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalSTFunctionParser.g:2120:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalSTFunctionParser.g:2120:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            int alt43=3;
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
            case THIS:
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
                alt43=1;
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
                case ColonColon:
                case LessThanSignEqualsSign:
                case LessThanSignGreaterThanSign:
                case GreaterThanSignEqualsSign:
                case OR:
                case NumberSign:
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
                    alt43=1;
                    }
                    break;
                case ColonEqualsSign:
                    {
                    alt43=2;
                    }
                    break;
                case EqualsSignGreaterThanSign:
                    {
                    alt43=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 2, input);

                    throw nvae;
                }

                }
                break;
            case NOT:
                {
                int LA43_3 = input.LA(2);

                if ( (LA43_3==RULE_ID) ) {
                    int LA43_6 = input.LA(3);

                    if ( (LA43_6==EOF||LA43_6==AND||LA43_6==MOD||LA43_6==XOR||(LA43_6>=AsteriskAsterisk && LA43_6<=ColonColon)||(LA43_6>=LessThanSignEqualsSign && LA43_6<=LessThanSignGreaterThanSign)||LA43_6==GreaterThanSignEqualsSign||LA43_6==OR||(LA43_6>=NumberSign && LA43_6<=Solidus)||(LA43_6>=LessThanSign && LA43_6<=GreaterThanSign)||LA43_6==LeftSquareBracket) ) {
                        alt43=1;
                    }
                    else if ( (LA43_6==EqualsSignGreaterThanSign) ) {
                        alt43=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 43, 6, input);

                        throw nvae;
                    }
                }
                else if ( (LA43_3==LDATE_AND_TIME||LA43_3==DATE_AND_TIME||LA43_3==LTIME_OF_DAY||LA43_3==TIME_OF_DAY||LA43_3==WSTRING||LA43_3==STRING||LA43_3==DWORD||LA43_3==FALSE||(LA43_3>=LDATE && LA43_3<=LWORD)||(LA43_3>=UDINT && LA43_3<=ULINT)||(LA43_3>=USINT && LA43_3<=WCHAR)||(LA43_3>=BOOL && LA43_3<=BYTE)||(LA43_3>=CHAR && LA43_3<=DINT)||(LA43_3>=LINT && LA43_3<=LTOD)||(LA43_3>=REAL && LA43_3<=SINT)||(LA43_3>=THIS && LA43_3<=TRUE)||LA43_3==UINT||LA43_3==WORD||LA43_3==AND||(LA43_3>=INT && LA43_3<=NOT)||LA43_3==TOD||LA43_3==XOR||LA43_3==DT||(LA43_3>=LD && LA43_3<=LT)||LA43_3==OR||LA43_3==LeftParenthesis||LA43_3==PlusSign||LA43_3==HyphenMinus||(LA43_3>=D && LA43_3<=T)||(LA43_3>=RULE_NON_DECIMAL && LA43_3<=RULE_INT)||LA43_3==RULE_STRING) ) {
                    alt43=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 3, input);

                    throw nvae;
                }
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
                    // InternalSTFunctionParser.g:2121:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
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
                    // InternalSTFunctionParser.g:2130:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
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
                    // InternalSTFunctionParser.g:2139:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
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
    // InternalSTFunctionParser.g:2151:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalSTFunctionParser.g:2151:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalSTFunctionParser.g:2152:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
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
    // InternalSTFunctionParser.g:2158:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_argument_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_argument_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2164:2: ( ( (lv_argument_0_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:2165:2: ( (lv_argument_0_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:2165:2: ( (lv_argument_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2166:3: (lv_argument_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2166:3: (lv_argument_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2167:4: lv_argument_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getSTCallUnnamedArgumentAccess().getArgumentSTExpressionParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_argument_0_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getSTCallUnnamedArgumentRule());
              				}
              				set(
              					current,
              					"argument",
              					lv_argument_0_0,
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
    // InternalSTFunctionParser.g:2187:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalSTFunctionParser.g:2187:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalSTFunctionParser.g:2188:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
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
    // InternalSTFunctionParser.g:2194:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_argument_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2200:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) )
            // InternalSTFunctionParser.g:2201:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            {
            // InternalSTFunctionParser.g:2201:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:2202:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:2202:3: ( (otherlv_0= RULE_ID ) )
            // InternalSTFunctionParser.g:2203:4: (otherlv_0= RULE_ID )
            {
            // InternalSTFunctionParser.g:2203:4: (otherlv_0= RULE_ID )
            // InternalSTFunctionParser.g:2204:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_31); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementCrossReference_0_0());
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:2219:3: ( (lv_argument_2_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2220:4: (lv_argument_2_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2220:4: (lv_argument_2_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2221:5: lv_argument_2_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCallNamedInputArgumentAccess().getArgumentSTExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_argument_2_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              					set(
              						current,
              						"argument",
              						lv_argument_2_0,
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
    // InternalSTFunctionParser.g:2242:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalSTFunctionParser.g:2242:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalSTFunctionParser.g:2243:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
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
    // InternalSTFunctionParser.g:2249:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_argument_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2255:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) )
            // InternalSTFunctionParser.g:2256:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            {
            // InternalSTFunctionParser.g:2256:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            // InternalSTFunctionParser.g:2257:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) )
            {
            // InternalSTFunctionParser.g:2257:3: ( (lv_not_0_0= NOT ) )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==NOT) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalSTFunctionParser.g:2258:4: (lv_not_0_0= NOT )
                    {
                    // InternalSTFunctionParser.g:2258:4: (lv_not_0_0= NOT )
                    // InternalSTFunctionParser.g:2259:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_3); if (state.failed) return current;
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

            // InternalSTFunctionParser.g:2271:3: ( (otherlv_1= RULE_ID ) )
            // InternalSTFunctionParser.g:2272:4: (otherlv_1= RULE_ID )
            {
            // InternalSTFunctionParser.g:2272:4: (otherlv_1= RULE_ID )
            // InternalSTFunctionParser.g:2273:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2288:3: ( (lv_argument_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2289:4: (lv_argument_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2289:4: (lv_argument_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2290:5: lv_argument_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCallNamedOutputArgumentAccess().getArgumentSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_argument_3_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              					set(
              						current,
              						"argument",
              						lv_argument_3_0,
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
    // $ANTLR end "ruleSTCallNamedOutputArgument"


    // $ANTLR start "entryRuleSTIfStatement"
    // InternalSTFunctionParser.g:2311:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalSTFunctionParser.g:2311:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalSTFunctionParser.g:2312:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalSTFunctionParser.g:2318:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalSTFunctionParser.g:2324:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalSTFunctionParser.g:2325:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalSTFunctionParser.g:2325:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalSTFunctionParser.g:2326:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:2330:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2331:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2331:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2332:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_35);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_36); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatementAccess().getTHENKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2353:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==LDATE_AND_TIME||LA45_0==DATE_AND_TIME||LA45_0==LTIME_OF_DAY||LA45_0==TIME_OF_DAY||LA45_0==CONTINUE||LA45_0==WSTRING||LA45_0==REPEAT||LA45_0==RETURN||LA45_0==STRING||LA45_0==DWORD||LA45_0==FALSE||(LA45_0>=LDATE && LA45_0<=LWORD)||(LA45_0>=UDINT && LA45_0<=ULINT)||(LA45_0>=USINT && LA45_0<=DINT)||LA45_0==EXIT||(LA45_0>=LINT && LA45_0<=LTOD)||(LA45_0>=REAL && LA45_0<=SINT)||(LA45_0>=THIS && LA45_0<=TRUE)||LA45_0==UINT||LA45_0==WORD||(LA45_0>=AND && LA45_0<=NOT)||LA45_0==TOD||LA45_0==XOR||(LA45_0>=DT && LA45_0<=LT)||LA45_0==OR||LA45_0==LeftParenthesis||LA45_0==PlusSign||LA45_0==HyphenMinus||LA45_0==Semicolon||(LA45_0>=D && LA45_0<=T)||(LA45_0>=RULE_NON_DECIMAL && LA45_0<=RULE_INT)||(LA45_0>=RULE_ID && LA45_0<=RULE_STRING)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2354:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2354:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2355:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_36);
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
            	    break loop45;
                }
            } while (true);

            // InternalSTFunctionParser.g:2372:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==ELSIF) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2373:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalSTFunctionParser.g:2373:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalSTFunctionParser.g:2374:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_37);
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
            	    break loop46;
                }
            } while (true);

            // InternalSTFunctionParser.g:2391:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==ELSE) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalSTFunctionParser.g:2392:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalSTFunctionParser.g:2392:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalSTFunctionParser.g:2393:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_38);
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
    // InternalSTFunctionParser.g:2418:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalSTFunctionParser.g:2418:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalSTFunctionParser.g:2419:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalSTFunctionParser.g:2425:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2431:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:2432:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:2432:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:2433:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:2437:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2438:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2438:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2439:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_35);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2460:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==LDATE_AND_TIME||LA48_0==DATE_AND_TIME||LA48_0==LTIME_OF_DAY||LA48_0==TIME_OF_DAY||LA48_0==CONTINUE||LA48_0==WSTRING||LA48_0==REPEAT||LA48_0==RETURN||LA48_0==STRING||LA48_0==DWORD||LA48_0==FALSE||(LA48_0>=LDATE && LA48_0<=LWORD)||(LA48_0>=UDINT && LA48_0<=ULINT)||(LA48_0>=USINT && LA48_0<=DINT)||LA48_0==EXIT||(LA48_0>=LINT && LA48_0<=LTOD)||(LA48_0>=REAL && LA48_0<=SINT)||(LA48_0>=THIS && LA48_0<=TRUE)||LA48_0==UINT||LA48_0==WORD||(LA48_0>=AND && LA48_0<=NOT)||LA48_0==TOD||LA48_0==XOR||(LA48_0>=DT && LA48_0<=LT)||LA48_0==OR||LA48_0==LeftParenthesis||LA48_0==PlusSign||LA48_0==HyphenMinus||LA48_0==Semicolon||(LA48_0>=D && LA48_0<=T)||(LA48_0>=RULE_NON_DECIMAL && LA48_0<=RULE_INT)||(LA48_0>=RULE_ID && LA48_0<=RULE_STRING)) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2461:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2461:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2462:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_39);
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalSTFunctionParser.g:2483:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalSTFunctionParser.g:2483:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalSTFunctionParser.g:2484:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalSTFunctionParser.g:2490:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalSTFunctionParser.g:2496:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalSTFunctionParser.g:2497:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalSTFunctionParser.g:2497:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalSTFunctionParser.g:2498:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:2502:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2503:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2503:4: (lv_selector_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2504:5: lv_selector_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_19);
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

            otherlv_2=(Token)match(input,OF,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCaseStatementAccess().getOFKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2525:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt49=0;
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==LDATE_AND_TIME||LA49_0==DATE_AND_TIME||LA49_0==LTIME_OF_DAY||LA49_0==TIME_OF_DAY||LA49_0==WSTRING||LA49_0==STRING||LA49_0==DWORD||LA49_0==FALSE||(LA49_0>=LDATE && LA49_0<=LWORD)||(LA49_0>=UDINT && LA49_0<=ULINT)||(LA49_0>=USINT && LA49_0<=WCHAR)||(LA49_0>=BOOL && LA49_0<=BYTE)||(LA49_0>=CHAR && LA49_0<=DINT)||(LA49_0>=LINT && LA49_0<=LTOD)||(LA49_0>=REAL && LA49_0<=SINT)||(LA49_0>=THIS && LA49_0<=TRUE)||LA49_0==UINT||LA49_0==WORD||LA49_0==AND||(LA49_0>=INT && LA49_0<=NOT)||LA49_0==TOD||LA49_0==XOR||LA49_0==DT||(LA49_0>=LD && LA49_0<=LT)||LA49_0==OR||LA49_0==LeftParenthesis||LA49_0==PlusSign||LA49_0==HyphenMinus||(LA49_0>=D && LA49_0<=T)||(LA49_0>=RULE_NON_DECIMAL && LA49_0<=RULE_INT)||(LA49_0>=RULE_ID && LA49_0<=RULE_STRING)) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2526:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalSTFunctionParser.g:2526:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalSTFunctionParser.g:2527:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_40);
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
            	    if ( cnt49 >= 1 ) break loop49;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(49, input);
                        throw eee;
                }
                cnt49++;
            } while (true);

            // InternalSTFunctionParser.g:2544:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==ELSE) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalSTFunctionParser.g:2545:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalSTFunctionParser.g:2545:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalSTFunctionParser.g:2546:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_41);
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
    // InternalSTFunctionParser.g:2571:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalSTFunctionParser.g:2571:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalSTFunctionParser.g:2572:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalSTFunctionParser.g:2578:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2584:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:2585:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:2585:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:2586:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalSTFunctionParser.g:2586:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2587:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2587:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2588:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_42);
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

            // InternalSTFunctionParser.g:2605:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==Comma) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2606:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_17); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalSTFunctionParser.g:2610:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalSTFunctionParser.g:2611:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalSTFunctionParser.g:2611:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalSTFunctionParser.g:2612:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_42);
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
            	    break loop51;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2634:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop52:
            do {
                int alt52=2;
                alt52 = dfa52.predict(input);
                switch (alt52) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2635:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2639:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2640:5: lv_statements_4_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_39);
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalSTFunctionParser.g:2661:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalSTFunctionParser.g:2661:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalSTFunctionParser.g:2662:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalSTFunctionParser.g:2668:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2674:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalSTFunctionParser.g:2675:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalSTFunctionParser.g:2675:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalSTFunctionParser.g:2676:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalSTFunctionParser.g:2676:3: ()
            // InternalSTFunctionParser.g:2677:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,ELSE,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:2687:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==LDATE_AND_TIME||LA53_0==DATE_AND_TIME||LA53_0==LTIME_OF_DAY||LA53_0==TIME_OF_DAY||LA53_0==CONTINUE||LA53_0==WSTRING||LA53_0==REPEAT||LA53_0==RETURN||LA53_0==STRING||LA53_0==DWORD||LA53_0==FALSE||(LA53_0>=LDATE && LA53_0<=LWORD)||(LA53_0>=UDINT && LA53_0<=ULINT)||(LA53_0>=USINT && LA53_0<=DINT)||LA53_0==EXIT||(LA53_0>=LINT && LA53_0<=LTOD)||(LA53_0>=REAL && LA53_0<=SINT)||(LA53_0>=THIS && LA53_0<=TRUE)||LA53_0==UINT||LA53_0==WORD||(LA53_0>=AND && LA53_0<=NOT)||LA53_0==TOD||LA53_0==XOR||(LA53_0>=DT && LA53_0<=LT)||LA53_0==OR||LA53_0==LeftParenthesis||LA53_0==PlusSign||LA53_0==HyphenMinus||LA53_0==Semicolon||(LA53_0>=D && LA53_0<=T)||(LA53_0>=RULE_NON_DECIMAL && LA53_0<=RULE_INT)||(LA53_0>=RULE_ID && LA53_0<=RULE_STRING)) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2688:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2688:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2689:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_39);
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalSTFunctionParser.g:2710:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalSTFunctionParser.g:2710:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalSTFunctionParser.g:2711:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalSTFunctionParser.g:2717:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
    public final EObject ruleSTForStatement() throws RecognitionException {
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
            // InternalSTFunctionParser.g:2723:2: ( (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalSTFunctionParser.g:2724:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalSTFunctionParser.g:2724:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalSTFunctionParser.g:2725:3: otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:2729:3: ( (lv_variable_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2730:4: (lv_variable_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2730:4: (lv_variable_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2731:5: lv_variable_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getVariableSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_variable_1_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTForStatementRule());
              					}
              					set(
              						current,
              						"variable",
              						lv_variable_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2752:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2753:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2753:4: (lv_from_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2754:5: lv_from_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_43);
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

            otherlv_4=(Token)match(input,TO,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getTOKeyword_4());
              		
            }
            // InternalSTFunctionParser.g:2775:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2776:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2776:4: (lv_to_5_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2777:5: lv_to_5_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_44);
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

            // InternalSTFunctionParser.g:2794:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==BY) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalSTFunctionParser.g:2795:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalSTFunctionParser.g:2799:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:2800:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:2800:5: (lv_by_7_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:2801:6: lv_by_7_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_45);
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

            otherlv_8=(Token)match(input,DO,FOLLOW_46); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
              		
            }
            // InternalSTFunctionParser.g:2823:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==LDATE_AND_TIME||LA55_0==DATE_AND_TIME||LA55_0==LTIME_OF_DAY||LA55_0==TIME_OF_DAY||LA55_0==CONTINUE||LA55_0==WSTRING||LA55_0==REPEAT||LA55_0==RETURN||LA55_0==STRING||LA55_0==DWORD||LA55_0==FALSE||(LA55_0>=LDATE && LA55_0<=LWORD)||(LA55_0>=UDINT && LA55_0<=ULINT)||(LA55_0>=USINT && LA55_0<=DINT)||LA55_0==EXIT||(LA55_0>=LINT && LA55_0<=LTOD)||(LA55_0>=REAL && LA55_0<=SINT)||(LA55_0>=THIS && LA55_0<=TRUE)||LA55_0==UINT||LA55_0==WORD||(LA55_0>=AND && LA55_0<=NOT)||LA55_0==TOD||LA55_0==XOR||(LA55_0>=DT && LA55_0<=LT)||LA55_0==OR||LA55_0==LeftParenthesis||LA55_0==PlusSign||LA55_0==HyphenMinus||LA55_0==Semicolon||(LA55_0>=D && LA55_0<=T)||(LA55_0>=RULE_NON_DECIMAL && LA55_0<=RULE_INT)||(LA55_0>=RULE_ID && LA55_0<=RULE_STRING)) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2824:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2824:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2825:5: lv_statements_9_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_46);
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
            	    break loop55;
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
    // InternalSTFunctionParser.g:2850:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalSTFunctionParser.g:2850:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalSTFunctionParser.g:2851:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalSTFunctionParser.g:2857:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2863:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalSTFunctionParser.g:2864:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalSTFunctionParser.g:2864:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalSTFunctionParser.g:2865:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:2869:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2870:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2870:4: (lv_condition_1_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2871:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_45);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_47); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2892:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==LDATE_AND_TIME||LA56_0==DATE_AND_TIME||LA56_0==LTIME_OF_DAY||LA56_0==TIME_OF_DAY||LA56_0==CONTINUE||LA56_0==WSTRING||LA56_0==REPEAT||LA56_0==RETURN||LA56_0==STRING||LA56_0==DWORD||LA56_0==FALSE||(LA56_0>=LDATE && LA56_0<=LWORD)||(LA56_0>=UDINT && LA56_0<=ULINT)||(LA56_0>=USINT && LA56_0<=DINT)||LA56_0==EXIT||(LA56_0>=LINT && LA56_0<=LTOD)||(LA56_0>=REAL && LA56_0<=SINT)||(LA56_0>=THIS && LA56_0<=TRUE)||LA56_0==UINT||LA56_0==WORD||(LA56_0>=AND && LA56_0<=NOT)||LA56_0==TOD||LA56_0==XOR||(LA56_0>=DT && LA56_0<=LT)||LA56_0==OR||LA56_0==LeftParenthesis||LA56_0==PlusSign||LA56_0==HyphenMinus||LA56_0==Semicolon||(LA56_0>=D && LA56_0<=T)||(LA56_0>=RULE_NON_DECIMAL && LA56_0<=RULE_INT)||(LA56_0>=RULE_ID && LA56_0<=RULE_STRING)) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2893:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2893:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2894:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_47);
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
            	    break loop56;
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
    // InternalSTFunctionParser.g:2919:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalSTFunctionParser.g:2919:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalSTFunctionParser.g:2920:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalSTFunctionParser.g:2926:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:2932:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalSTFunctionParser.g:2933:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalSTFunctionParser.g:2933:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalSTFunctionParser.g:2934:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_48); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalSTFunctionParser.g:2938:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==LDATE_AND_TIME||LA57_0==DATE_AND_TIME||LA57_0==LTIME_OF_DAY||LA57_0==TIME_OF_DAY||LA57_0==CONTINUE||LA57_0==WSTRING||LA57_0==REPEAT||LA57_0==RETURN||LA57_0==STRING||LA57_0==DWORD||LA57_0==FALSE||(LA57_0>=LDATE && LA57_0<=LWORD)||(LA57_0>=UDINT && LA57_0<=ULINT)||(LA57_0>=USINT && LA57_0<=DINT)||LA57_0==EXIT||(LA57_0>=LINT && LA57_0<=LTOD)||(LA57_0>=REAL && LA57_0<=SINT)||(LA57_0>=THIS && LA57_0<=TRUE)||LA57_0==UINT||LA57_0==WORD||(LA57_0>=AND && LA57_0<=NOT)||LA57_0==TOD||LA57_0==XOR||(LA57_0>=DT && LA57_0<=LT)||LA57_0==OR||LA57_0==LeftParenthesis||LA57_0==PlusSign||LA57_0==HyphenMinus||LA57_0==Semicolon||(LA57_0>=D && LA57_0<=T)||(LA57_0>=RULE_NON_DECIMAL && LA57_0<=RULE_INT)||(LA57_0>=RULE_ID && LA57_0<=RULE_STRING)) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // InternalSTFunctionParser.g:2939:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalSTFunctionParser.g:2939:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalSTFunctionParser.g:2940:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_48);
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
            	    break loop57;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalSTFunctionParser.g:2961:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalSTFunctionParser.g:2962:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalSTFunctionParser.g:2962:4: (lv_condition_3_0= ruleSTExpression )
            // InternalSTFunctionParser.g:2963:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_49);
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
    // InternalSTFunctionParser.g:2988:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalSTFunctionParser.g:2988:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalSTFunctionParser.g:2989:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalSTFunctionParser.g:2995:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3001:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalSTFunctionParser.g:3002:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalSTFunctionParser.g:3013:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalSTFunctionParser.g:3013:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalSTFunctionParser.g:3014:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalSTFunctionParser.g:3020:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3026:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3027:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3027:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalSTFunctionParser.g:3028:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_50);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3036:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==FullStopFullStop) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3037:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3037:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalSTFunctionParser.g:3038:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3038:5: ()
            	    // InternalSTFunctionParser.g:3039:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3045:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalSTFunctionParser.g:3046:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalSTFunctionParser.g:3046:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalSTFunctionParser.g:3047:7: lv_op_2_0= ruleSubrangeOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3065:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalSTFunctionParser.g:3066:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalSTFunctionParser.g:3066:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalSTFunctionParser.g:3067:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_50);
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
            	    break loop58;
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
    // InternalSTFunctionParser.g:3089:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalSTFunctionParser.g:3089:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalSTFunctionParser.g:3090:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalSTFunctionParser.g:3096:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3102:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3103:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3103:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalSTFunctionParser.g:3104:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3112:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==OR) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3113:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3113:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalSTFunctionParser.g:3114:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3114:5: ()
            	    // InternalSTFunctionParser.g:3115:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3121:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalSTFunctionParser.g:3122:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalSTFunctionParser.g:3122:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalSTFunctionParser.g:3123:7: lv_op_2_0= ruleOrOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3141:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalSTFunctionParser.g:3142:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalSTFunctionParser.g:3142:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalSTFunctionParser.g:3143:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_51);
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
            	    break loop59;
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
    // InternalSTFunctionParser.g:3165:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalSTFunctionParser.g:3165:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalSTFunctionParser.g:3166:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalSTFunctionParser.g:3172:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3178:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3179:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3179:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalSTFunctionParser.g:3180:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_52);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3188:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==XOR) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3189:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3189:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalSTFunctionParser.g:3190:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3190:5: ()
            	    // InternalSTFunctionParser.g:3191:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3197:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalSTFunctionParser.g:3198:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalSTFunctionParser.g:3198:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalSTFunctionParser.g:3199:7: lv_op_2_0= ruleXorOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3217:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalSTFunctionParser.g:3218:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalSTFunctionParser.g:3218:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalSTFunctionParser.g:3219:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_52);
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
            	    break loop60;
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
    // InternalSTFunctionParser.g:3241:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalSTFunctionParser.g:3241:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalSTFunctionParser.g:3242:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalSTFunctionParser.g:3248:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3254:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3255:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3255:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalSTFunctionParser.g:3256:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_53);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3264:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==AND||LA61_0==Ampersand) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3265:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3265:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalSTFunctionParser.g:3266:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3266:5: ()
            	    // InternalSTFunctionParser.g:3267:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3273:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalSTFunctionParser.g:3274:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalSTFunctionParser.g:3274:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalSTFunctionParser.g:3275:7: lv_op_2_0= ruleAndOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3293:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalSTFunctionParser.g:3294:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalSTFunctionParser.g:3294:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalSTFunctionParser.g:3295:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_53);
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalSTFunctionParser.g:3317:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalSTFunctionParser.g:3317:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalSTFunctionParser.g:3318:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalSTFunctionParser.g:3324:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3330:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3331:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3331:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalSTFunctionParser.g:3332:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_54);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3340:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==LessThanSignGreaterThanSign||LA62_0==EqualsSign) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3341:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3341:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalSTFunctionParser.g:3342:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3342:5: ()
            	    // InternalSTFunctionParser.g:3343:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3349:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalSTFunctionParser.g:3350:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalSTFunctionParser.g:3350:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalSTFunctionParser.g:3351:7: lv_op_2_0= ruleEqualityOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3369:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalSTFunctionParser.g:3370:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalSTFunctionParser.g:3370:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalSTFunctionParser.g:3371:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_54);
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
            	    break loop62;
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
    // InternalSTFunctionParser.g:3393:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalSTFunctionParser.g:3393:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalSTFunctionParser.g:3394:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalSTFunctionParser.g:3400:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3406:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3407:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3407:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalSTFunctionParser.g:3408:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_55);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3416:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==LessThanSignEqualsSign||LA63_0==GreaterThanSignEqualsSign||LA63_0==LessThanSign||LA63_0==GreaterThanSign) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3417:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3417:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalSTFunctionParser.g:3418:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3418:5: ()
            	    // InternalSTFunctionParser.g:3419:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3425:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalSTFunctionParser.g:3426:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalSTFunctionParser.g:3426:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalSTFunctionParser.g:3427:7: lv_op_2_0= ruleCompareOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3445:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalSTFunctionParser.g:3446:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalSTFunctionParser.g:3446:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalSTFunctionParser.g:3447:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_55);
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
            	    break loop63;
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
    // InternalSTFunctionParser.g:3469:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalSTFunctionParser.g:3469:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalSTFunctionParser.g:3470:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalSTFunctionParser.g:3476:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3482:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3483:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3483:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalSTFunctionParser.g:3484:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_56);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3492:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==PlusSign||LA64_0==HyphenMinus) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3493:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3493:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalSTFunctionParser.g:3494:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3494:5: ()
            	    // InternalSTFunctionParser.g:3495:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3501:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalSTFunctionParser.g:3502:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalSTFunctionParser.g:3502:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalSTFunctionParser.g:3503:7: lv_op_2_0= ruleAddSubOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3521:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalSTFunctionParser.g:3522:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalSTFunctionParser.g:3522:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalSTFunctionParser.g:3523:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_56);
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
            	    break loop64;
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
    // InternalSTFunctionParser.g:3545:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalSTFunctionParser.g:3545:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalSTFunctionParser.g:3546:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalSTFunctionParser.g:3552:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3558:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3559:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3559:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalSTFunctionParser.g:3560:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_57);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3568:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==MOD||LA65_0==Asterisk||LA65_0==Solidus) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3569:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3569:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalSTFunctionParser.g:3570:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3570:5: ()
            	    // InternalSTFunctionParser.g:3571:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3577:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalSTFunctionParser.g:3578:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalSTFunctionParser.g:3578:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalSTFunctionParser.g:3579:7: lv_op_2_0= ruleMulDivModOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3597:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalSTFunctionParser.g:3598:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalSTFunctionParser.g:3598:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalSTFunctionParser.g:3599:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_57);
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
            	    break loop65;
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
    // InternalSTFunctionParser.g:3621:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalSTFunctionParser.g:3621:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalSTFunctionParser.g:3622:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalSTFunctionParser.g:3628:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3634:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalSTFunctionParser.g:3635:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalSTFunctionParser.g:3635:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalSTFunctionParser.g:3636:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_58);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3644:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==AsteriskAsterisk) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3645:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3645:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalSTFunctionParser.g:3646:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalSTFunctionParser.g:3646:5: ()
            	    // InternalSTFunctionParser.g:3647:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalSTFunctionParser.g:3653:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalSTFunctionParser.g:3654:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalSTFunctionParser.g:3654:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalSTFunctionParser.g:3655:7: lv_op_2_0= rulePowerOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_17);
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

            	    // InternalSTFunctionParser.g:3673:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalSTFunctionParser.g:3674:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalSTFunctionParser.g:3674:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalSTFunctionParser.g:3675:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_58);
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
            	    break loop66;
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
    // InternalSTFunctionParser.g:3697:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalSTFunctionParser.g:3697:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalSTFunctionParser.g:3698:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalSTFunctionParser.g:3704:1: ruleSTUnaryExpression returns [EObject current=null] : (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        EObject this_STLiteralExpressions_1 = null;

        EObject this_STSignedLiteralExpressions_2 = null;

        Enumerator lv_op_4_0 = null;

        EObject lv_expression_5_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3710:2: ( (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalSTFunctionParser.g:3711:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalSTFunctionParser.g:3711:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )
            int alt67=4;
            alt67 = dfa67.predict(input);
            switch (alt67) {
                case 1 :
                    // InternalSTFunctionParser.g:3712:3: this_STAccessExpression_0= ruleSTAccessExpression
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
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:3721:3: this_STLiteralExpressions_1= ruleSTLiteralExpressions
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getSTLiteralExpressionsParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STLiteralExpressions_1=ruleSTLiteralExpressions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STLiteralExpressions_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:3730:3: ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions )
                    {
                    // InternalSTFunctionParser.g:3730:3: ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions )
                    // InternalSTFunctionParser.g:3731:4: ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getSTSignedLiteralExpressionsParserRuleCall_2());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_STSignedLiteralExpressions_2=ruleSTSignedLiteralExpressions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_STSignedLiteralExpressions_2;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:3742:3: ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalSTFunctionParser.g:3742:3: ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) )
                    // InternalSTFunctionParser.g:3743:4: () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) )
                    {
                    // InternalSTFunctionParser.g:3743:4: ()
                    // InternalSTFunctionParser.g:3744:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_3_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSTFunctionParser.g:3750:4: ( (lv_op_4_0= ruleUnaryOperator ) )
                    // InternalSTFunctionParser.g:3751:5: (lv_op_4_0= ruleUnaryOperator )
                    {
                    // InternalSTFunctionParser.g:3751:5: (lv_op_4_0= ruleUnaryOperator )
                    // InternalSTFunctionParser.g:3752:6: lv_op_4_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_op_4_0=ruleUnaryOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTUnaryExpressionRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_4_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.UnaryOperator");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSTFunctionParser.g:3769:4: ( (lv_expression_5_0= ruleSTUnaryExpression ) )
                    // InternalSTFunctionParser.g:3770:5: (lv_expression_5_0= ruleSTUnaryExpression )
                    {
                    // InternalSTFunctionParser.g:3770:5: (lv_expression_5_0= ruleSTUnaryExpression )
                    // InternalSTFunctionParser.g:3771:6: lv_expression_5_0= ruleSTUnaryExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getExpressionSTUnaryExpressionParserRuleCall_3_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_5_0=ruleSTUnaryExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTUnaryExpressionRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_5_0,
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
    // InternalSTFunctionParser.g:3793:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalSTFunctionParser.g:3793:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalSTFunctionParser.g:3794:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalSTFunctionParser.g:3800:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalSTFunctionParser.g:3806:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalSTFunctionParser.g:3807:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalSTFunctionParser.g:3807:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalSTFunctionParser.g:3808:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_59);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:3816:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop70:
            do {
                int alt70=3;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==FullStop) ) {
                    alt70=1;
                }
                else if ( (LA70_0==LeftSquareBracket) ) {
                    alt70=2;
                }


                switch (alt70) {
            	case 1 :
            	    // InternalSTFunctionParser.g:3817:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalSTFunctionParser.g:3817:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalSTFunctionParser.g:3818:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalSTFunctionParser.g:3818:5: ()
            	    // InternalSTFunctionParser.g:3819:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_60); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalSTFunctionParser.g:3829:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalSTFunctionParser.g:3830:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalSTFunctionParser.g:3830:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalSTFunctionParser.g:3831:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalSTFunctionParser.g:3831:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt68=2;
            	    int LA68_0 = input.LA(1);

            	    if ( (LA68_0==AND||LA68_0==MOD||LA68_0==XOR||LA68_0==DT||(LA68_0>=LD && LA68_0<=LT)||LA68_0==OR||LA68_0==D||LA68_0==RULE_ID) ) {
            	        alt68=1;
            	    }
            	    else if ( ((LA68_0>=B && LA68_0<=X)||LA68_0==LeftParenthesis||LA68_0==RULE_INT) ) {
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
            	            // InternalSTFunctionParser.g:3832:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_59);
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
            	            // InternalSTFunctionParser.g:3848:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_59);
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
            	    // InternalSTFunctionParser.g:3868:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalSTFunctionParser.g:3868:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalSTFunctionParser.g:3869:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalSTFunctionParser.g:3869:5: ()
            	    // InternalSTFunctionParser.g:3870:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_5=(Token)match(input,LeftSquareBracket,FOLLOW_17); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_5, grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1());
            	      				
            	    }
            	    // InternalSTFunctionParser.g:3880:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalSTFunctionParser.g:3881:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalSTFunctionParser.g:3881:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalSTFunctionParser.g:3882:7: lv_index_6_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_18);
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

            	    // InternalSTFunctionParser.g:3899:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop69:
            	    do {
            	        int alt69=2;
            	        int LA69_0 = input.LA(1);

            	        if ( (LA69_0==Comma) ) {
            	            alt69=1;
            	        }


            	        switch (alt69) {
            	    	case 1 :
            	    	    // InternalSTFunctionParser.g:3900:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_17); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalSTFunctionParser.g:3904:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalSTFunctionParser.g:3905:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalSTFunctionParser.g:3905:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalSTFunctionParser.g:3906:8: lv_index_8_0= ruleSTExpression
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0());
            	    	      							
            	    	    }
            	    	    pushFollow(FOLLOW_18);
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
            	    	    break loop69;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_59); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop70;
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
    // InternalSTFunctionParser.g:3934:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalSTFunctionParser.g:3934:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalSTFunctionParser.g:3935:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalSTFunctionParser.g:3941:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STBuiltinFeatureExpression_4 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:3947:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression ) )
            // InternalSTFunctionParser.g:3948:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression )
            {
            // InternalSTFunctionParser.g:3948:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression )
            int alt71=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt71=1;
                }
                break;
            case AND:
            case MOD:
            case XOR:
            case DT:
            case LD:
            case LT:
            case OR:
            case D:
            case RULE_ID:
                {
                alt71=2;
                }
                break;
            case THIS:
                {
                alt71=3;
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
                    // InternalSTFunctionParser.g:3949:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalSTFunctionParser.g:3949:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalSTFunctionParser.g:3950:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_61);
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
                    // InternalSTFunctionParser.g:3968:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalSTFunctionParser.g:3977:3: this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTBuiltinFeatureExpressionParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STBuiltinFeatureExpression_4=ruleSTBuiltinFeatureExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STBuiltinFeatureExpression_4;
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
    // InternalSTFunctionParser.g:3989:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalSTFunctionParser.g:3989:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalSTFunctionParser.g:3990:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalSTFunctionParser.g:3996:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token lv_call_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4002:2: ( ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalSTFunctionParser.g:4003:2: ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalSTFunctionParser.g:4003:2: ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalSTFunctionParser.g:4004:3: () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalSTFunctionParser.g:4004:3: ()
            // InternalSTFunctionParser.g:4005:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:4011:3: ( ( ruleSTFeatureName ) )
            // InternalSTFunctionParser.g:4012:4: ( ruleSTFeatureName )
            {
            // InternalSTFunctionParser.g:4012:4: ( ruleSTFeatureName )
            // InternalSTFunctionParser.g:4013:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_62);
            ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTFunctionParser.g:4027:3: ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==LeftParenthesis) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalSTFunctionParser.g:4028:4: ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalSTFunctionParser.g:4028:4: ( (lv_call_2_0= LeftParenthesis ) )
                    // InternalSTFunctionParser.g:4029:5: (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalSTFunctionParser.g:4029:5: (lv_call_2_0= LeftParenthesis )
                    // InternalSTFunctionParser.g:4030:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_63); if (state.failed) return current;
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

                    // InternalSTFunctionParser.g:4042:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==LDATE_AND_TIME||LA73_0==DATE_AND_TIME||LA73_0==LTIME_OF_DAY||LA73_0==TIME_OF_DAY||LA73_0==WSTRING||LA73_0==STRING||LA73_0==DWORD||LA73_0==FALSE||(LA73_0>=LDATE && LA73_0<=LWORD)||(LA73_0>=UDINT && LA73_0<=ULINT)||(LA73_0>=USINT && LA73_0<=WCHAR)||(LA73_0>=BOOL && LA73_0<=BYTE)||(LA73_0>=CHAR && LA73_0<=DINT)||(LA73_0>=LINT && LA73_0<=LTOD)||(LA73_0>=REAL && LA73_0<=SINT)||(LA73_0>=THIS && LA73_0<=TRUE)||LA73_0==UINT||LA73_0==WORD||LA73_0==AND||(LA73_0>=INT && LA73_0<=NOT)||LA73_0==TOD||LA73_0==XOR||LA73_0==DT||(LA73_0>=LD && LA73_0<=LT)||LA73_0==OR||LA73_0==LeftParenthesis||LA73_0==PlusSign||LA73_0==HyphenMinus||(LA73_0>=D && LA73_0<=T)||(LA73_0>=RULE_NON_DECIMAL && LA73_0<=RULE_INT)||(LA73_0>=RULE_ID && LA73_0<=RULE_STRING)) ) {
                        alt73=1;
                    }
                    switch (alt73) {
                        case 1 :
                            // InternalSTFunctionParser.g:4043:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalSTFunctionParser.g:4043:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalSTFunctionParser.g:4044:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalSTFunctionParser.g:4044:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalSTFunctionParser.g:4045:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_28);
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

                            // InternalSTFunctionParser.g:4062:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop72:
                            do {
                                int alt72=2;
                                int LA72_0 = input.LA(1);

                                if ( (LA72_0==Comma) ) {
                                    alt72=1;
                                }


                                switch (alt72) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:4063:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_17); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalSTFunctionParser.g:4067:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalSTFunctionParser.g:4068:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalSTFunctionParser.g:4068:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalSTFunctionParser.g:4069:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_28);
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
                            	    break loop72;
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
    // InternalSTFunctionParser.g:4097:1: entryRuleSTFeatureName returns [String current=null] : iv_ruleSTFeatureName= ruleSTFeatureName EOF ;
    public final String entryRuleSTFeatureName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTFeatureName = null;


        try {
            // InternalSTFunctionParser.g:4097:53: (iv_ruleSTFeatureName= ruleSTFeatureName EOF )
            // InternalSTFunctionParser.g:4098:2: iv_ruleSTFeatureName= ruleSTFeatureName EOF
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
    // InternalSTFunctionParser.g:4104:1: ruleSTFeatureName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTFeatureName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4110:2: ( (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) )
            // InternalSTFunctionParser.g:4111:2: (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            {
            // InternalSTFunctionParser.g:4111:2: (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            int alt75=9;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt75=1;
                }
                break;
            case LT:
                {
                alt75=2;
                }
                break;
            case AND:
                {
                alt75=3;
                }
                break;
            case OR:
                {
                alt75=4;
                }
                break;
            case XOR:
                {
                alt75=5;
                }
                break;
            case MOD:
                {
                alt75=6;
                }
                break;
            case D:
                {
                alt75=7;
                }
                break;
            case DT:
                {
                alt75=8;
                }
                break;
            case LD:
                {
                alt75=9;
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
                    // InternalSTFunctionParser.g:4112:3: this_QualifiedName_0= ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTFeatureNameAccess().getQualifiedNameParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_QualifiedName_0=ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_QualifiedName_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4123:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4129:3: kw= AND
                    {
                    kw=(Token)match(input,AND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getANDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:4135:3: kw= OR
                    {
                    kw=(Token)match(input,OR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getORKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTFunctionParser.g:4141:3: kw= XOR
                    {
                    kw=(Token)match(input,XOR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getXORKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTFunctionParser.g:4147:3: kw= MOD
                    {
                    kw=(Token)match(input,MOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getMODKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTFunctionParser.g:4153:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTFunctionParser.g:4159:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTFunctionParser.g:4165:3: kw= LD
                    {
                    kw=(Token)match(input,LD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLDKeyword_8());
                      		
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


    // $ANTLR start "entryRuleSTBuiltinFeatureExpression"
    // InternalSTFunctionParser.g:4174:1: entryRuleSTBuiltinFeatureExpression returns [EObject current=null] : iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF ;
    public final EObject entryRuleSTBuiltinFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTBuiltinFeatureExpression = null;


        try {
            // InternalSTFunctionParser.g:4174:67: (iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF )
            // InternalSTFunctionParser.g:4175:2: iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTBuiltinFeatureExpression=ruleSTBuiltinFeatureExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTBuiltinFeatureExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTBuiltinFeatureExpression"


    // $ANTLR start "ruleSTBuiltinFeatureExpression"
    // InternalSTFunctionParser.g:4181:1: ruleSTBuiltinFeatureExpression returns [EObject current=null] : ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTBuiltinFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token lv_call_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Enumerator lv_feature_1_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4187:2: ( ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalSTFunctionParser.g:4188:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalSTFunctionParser.g:4188:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalSTFunctionParser.g:4189:3: () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalSTFunctionParser.g:4189:3: ()
            // InternalSTFunctionParser.g:4190:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTBuiltinFeatureExpressionAccess().getSTBuiltinFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:4196:3: ( (lv_feature_1_0= ruleSTBuiltinFeature ) )
            // InternalSTFunctionParser.g:4197:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            {
            // InternalSTFunctionParser.g:4197:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            // InternalSTFunctionParser.g:4198:5: lv_feature_1_0= ruleSTBuiltinFeature
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureSTBuiltinFeatureEnumRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_62);
            lv_feature_1_0=ruleSTBuiltinFeature();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTBuiltinFeatureExpressionRule());
              					}
              					set(
              						current,
              						"feature",
              						lv_feature_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STBuiltinFeature");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSTFunctionParser.g:4215:3: ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==LeftParenthesis) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalSTFunctionParser.g:4216:4: ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalSTFunctionParser.g:4216:4: ( (lv_call_2_0= LeftParenthesis ) )
                    // InternalSTFunctionParser.g:4217:5: (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalSTFunctionParser.g:4217:5: (lv_call_2_0= LeftParenthesis )
                    // InternalSTFunctionParser.g:4218:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_call_2_0, grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTBuiltinFeatureExpressionRule());
                      						}
                      						setWithLastConsumed(current, "call", lv_call_2_0 != null, "(");
                      					
                    }

                    }


                    }

                    // InternalSTFunctionParser.g:4230:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==LDATE_AND_TIME||LA77_0==DATE_AND_TIME||LA77_0==LTIME_OF_DAY||LA77_0==TIME_OF_DAY||LA77_0==WSTRING||LA77_0==STRING||LA77_0==DWORD||LA77_0==FALSE||(LA77_0>=LDATE && LA77_0<=LWORD)||(LA77_0>=UDINT && LA77_0<=ULINT)||(LA77_0>=USINT && LA77_0<=WCHAR)||(LA77_0>=BOOL && LA77_0<=BYTE)||(LA77_0>=CHAR && LA77_0<=DINT)||(LA77_0>=LINT && LA77_0<=LTOD)||(LA77_0>=REAL && LA77_0<=SINT)||(LA77_0>=THIS && LA77_0<=TRUE)||LA77_0==UINT||LA77_0==WORD||LA77_0==AND||(LA77_0>=INT && LA77_0<=NOT)||LA77_0==TOD||LA77_0==XOR||LA77_0==DT||(LA77_0>=LD && LA77_0<=LT)||LA77_0==OR||LA77_0==LeftParenthesis||LA77_0==PlusSign||LA77_0==HyphenMinus||(LA77_0>=D && LA77_0<=T)||(LA77_0>=RULE_NON_DECIMAL && LA77_0<=RULE_INT)||(LA77_0>=RULE_ID && LA77_0<=RULE_STRING)) ) {
                        alt77=1;
                    }
                    switch (alt77) {
                        case 1 :
                            // InternalSTFunctionParser.g:4231:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalSTFunctionParser.g:4231:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalSTFunctionParser.g:4232:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalSTFunctionParser.g:4232:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalSTFunctionParser.g:4233:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_28);
                            lv_parameters_3_0=ruleSTCallArgument();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getSTBuiltinFeatureExpressionRule());
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

                            // InternalSTFunctionParser.g:4250:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop76:
                            do {
                                int alt76=2;
                                int LA76_0 = input.LA(1);

                                if ( (LA76_0==Comma) ) {
                                    alt76=1;
                                }


                                switch (alt76) {
                            	case 1 :
                            	    // InternalSTFunctionParser.g:4251:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_17); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTBuiltinFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalSTFunctionParser.g:4255:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalSTFunctionParser.g:4256:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalSTFunctionParser.g:4256:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalSTFunctionParser.g:4257:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_28);
                            	    lv_parameters_5_0=ruleSTCallArgument();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getSTBuiltinFeatureExpressionRule());
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
                            	    break loop76;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTBuiltinFeatureExpressionAccess().getRightParenthesisKeyword_2_2());
                      			
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
    // $ANTLR end "ruleSTBuiltinFeatureExpression"


    // $ANTLR start "entryRuleSTMultibitPartialExpression"
    // InternalSTFunctionParser.g:4285:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalSTFunctionParser.g:4285:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalSTFunctionParser.g:4286:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalSTFunctionParser.g:4292:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_specifier_1_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4298:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) )
            // InternalSTFunctionParser.g:4299:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            {
            // InternalSTFunctionParser.g:4299:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            // InternalSTFunctionParser.g:4300:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            {
            // InternalSTFunctionParser.g:4300:3: ()
            // InternalSTFunctionParser.g:4301:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalSTFunctionParser.g:4307:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( ((LA79_0>=B && LA79_0<=X)) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalSTFunctionParser.g:4308:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalSTFunctionParser.g:4308:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalSTFunctionParser.g:4309:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_64);
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

            // InternalSTFunctionParser.g:4326:3: ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==RULE_INT) ) {
                alt80=1;
            }
            else if ( (LA80_0==LeftParenthesis) ) {
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
                    // InternalSTFunctionParser.g:4327:4: ( (lv_index_2_0= RULE_INT ) )
                    {
                    // InternalSTFunctionParser.g:4327:4: ( (lv_index_2_0= RULE_INT ) )
                    // InternalSTFunctionParser.g:4328:5: (lv_index_2_0= RULE_INT )
                    {
                    // InternalSTFunctionParser.g:4328:5: (lv_index_2_0= RULE_INT )
                    // InternalSTFunctionParser.g:4329:6: lv_index_2_0= RULE_INT
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
                    // InternalSTFunctionParser.g:4346:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    {
                    // InternalSTFunctionParser.g:4346:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    // InternalSTFunctionParser.g:4347:5: otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
                      				
                    }
                    // InternalSTFunctionParser.g:4351:5: ( (lv_expression_4_0= ruleSTExpression ) )
                    // InternalSTFunctionParser.g:4352:6: (lv_expression_4_0= ruleSTExpression )
                    {
                    // InternalSTFunctionParser.g:4352:6: (lv_expression_4_0= ruleSTExpression )
                    // InternalSTFunctionParser.g:4353:7: lv_expression_4_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_61);
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
    // InternalSTFunctionParser.g:4380:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalSTFunctionParser.g:4380:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalSTFunctionParser.g:4381:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalSTFunctionParser.g:4387:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral | this_STEnumLiteral_6= ruleSTEnumLiteral ) ;
    public final EObject ruleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject this_STNumericLiteral_0 = null;

        EObject this_STDateLiteral_1 = null;

        EObject this_STTimeLiteral_2 = null;

        EObject this_STTimeOfDayLiteral_3 = null;

        EObject this_STDateAndTimeLiteral_4 = null;

        EObject this_STStringLiteral_5 = null;

        EObject this_STEnumLiteral_6 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4393:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral | this_STEnumLiteral_6= ruleSTEnumLiteral ) )
            // InternalSTFunctionParser.g:4394:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral | this_STEnumLiteral_6= ruleSTEnumLiteral )
            {
            // InternalSTFunctionParser.g:4394:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral | this_STEnumLiteral_6= ruleSTEnumLiteral )
            int alt81=7;
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
            case RULE_NON_DECIMAL:
            case RULE_INT:
                {
                alt81=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt81=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt81=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt81=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt81=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt81=6;
                }
                break;
            case RULE_ID:
                {
                alt81=7;
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
                    // InternalSTFunctionParser.g:4395:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalSTFunctionParser.g:4404:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalSTFunctionParser.g:4413:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalSTFunctionParser.g:4422:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalSTFunctionParser.g:4431:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalSTFunctionParser.g:4440:3: this_STStringLiteral_5= ruleSTStringLiteral
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
                case 7 :
                    // InternalSTFunctionParser.g:4449:3: this_STEnumLiteral_6= ruleSTEnumLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTEnumLiteralParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STEnumLiteral_6=ruleSTEnumLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STEnumLiteral_6;
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


    // $ANTLR start "entryRuleSTSignedLiteralExpressions"
    // InternalSTFunctionParser.g:4461:1: entryRuleSTSignedLiteralExpressions returns [EObject current=null] : iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF ;
    public final EObject entryRuleSTSignedLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignedLiteralExpressions = null;


        try {
            // InternalSTFunctionParser.g:4461:67: (iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF )
            // InternalSTFunctionParser.g:4462:2: iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTSignedLiteralExpressionsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTSignedLiteralExpressions=ruleSTSignedLiteralExpressions();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTSignedLiteralExpressions; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTSignedLiteralExpressions"


    // $ANTLR start "ruleSTSignedLiteralExpressions"
    // InternalSTFunctionParser.g:4468:1: ruleSTSignedLiteralExpressions returns [EObject current=null] : this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral ;
    public final EObject ruleSTSignedLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject this_STSignedNumericLiteral_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4474:2: (this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral )
            // InternalSTFunctionParser.g:4475:2: this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getSTSignedLiteralExpressionsAccess().getSTSignedNumericLiteralParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_STSignedNumericLiteral_0=ruleSTSignedNumericLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current = this_STSignedNumericLiteral_0;
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
    // $ANTLR end "ruleSTSignedLiteralExpressions"


    // $ANTLR start "entryRuleSTNumericLiteralType"
    // InternalSTFunctionParser.g:4486:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalSTFunctionParser.g:4486:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalSTFunctionParser.g:4487:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalSTFunctionParser.g:4493:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4499:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalSTFunctionParser.g:4500:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalSTFunctionParser.g:4500:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==DWORD||LA82_0==LWORD||(LA82_0>=BOOL && LA82_0<=BYTE)||LA82_0==WORD) ) {
                alt82=1;
            }
            else if ( (LA82_0==LREAL||(LA82_0>=UDINT && LA82_0<=ULINT)||LA82_0==USINT||LA82_0==DINT||LA82_0==LINT||(LA82_0>=REAL && LA82_0<=SINT)||LA82_0==UINT||LA82_0==INT) ) {
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
                    // InternalSTFunctionParser.g:4501:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalSTFunctionParser.g:4512:3: this_STAnyNumType_1= ruleSTAnyNumType
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
    // InternalSTFunctionParser.g:4526:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalSTFunctionParser.g:4526:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalSTFunctionParser.g:4527:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalSTFunctionParser.g:4533:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;

        AntlrDatatypeRuleToken lv_value_5_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4539:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) ) )
            // InternalSTFunctionParser.g:4540:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )
            {
            // InternalSTFunctionParser.g:4540:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )
            int alt84=2;
            alt84 = dfa84.predict(input);
            switch (alt84) {
                case 1 :
                    // InternalSTFunctionParser.g:4541:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) )
                    {
                    // InternalSTFunctionParser.g:4541:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) )
                    // InternalSTFunctionParser.g:4542:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) )
                    {
                    // InternalSTFunctionParser.g:4542:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalSTFunctionParser.g:4543:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalSTFunctionParser.g:4543:5: ( ruleSTNumericLiteralType )
                    // InternalSTFunctionParser.g:4544:6: ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
                    ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }
                    // InternalSTFunctionParser.g:4562:4: ( (lv_value_2_0= ruleSignedNumeric ) )
                    // InternalSTFunctionParser.g:4563:5: (lv_value_2_0= ruleSignedNumeric )
                    {
                    // InternalSTFunctionParser.g:4563:5: (lv_value_2_0= ruleSignedNumeric )
                    // InternalSTFunctionParser.g:4564:6: lv_value_2_0= ruleSignedNumeric
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueSignedNumericParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_2_0=ruleSignedNumeric();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_2_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.SignedNumeric");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:4583:3: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) )
                    {
                    // InternalSTFunctionParser.g:4583:3: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) )
                    // InternalSTFunctionParser.g:4584:4: ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) )
                    {
                    // InternalSTFunctionParser.g:4584:4: ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==DWORD||LA83_0==LREAL||LA83_0==LWORD||(LA83_0>=UDINT && LA83_0<=ULINT)||LA83_0==USINT||(LA83_0>=BOOL && LA83_0<=BYTE)||LA83_0==DINT||LA83_0==LINT||(LA83_0>=REAL && LA83_0<=SINT)||LA83_0==UINT||LA83_0==WORD||LA83_0==INT) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // InternalSTFunctionParser.g:4585:5: ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign
                            {
                            // InternalSTFunctionParser.g:4585:5: ( ( ruleSTNumericLiteralType ) )
                            // InternalSTFunctionParser.g:4586:6: ( ruleSTNumericLiteralType )
                            {
                            // InternalSTFunctionParser.g:4586:6: ( ruleSTNumericLiteralType )
                            // InternalSTFunctionParser.g:4587:7: ruleSTNumericLiteralType
                            {
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                              							}
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_1_0_0_0());
                              						
                            }
                            pushFollow(FOLLOW_29);
                            ruleSTNumericLiteralType();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_4=(Token)match(input,NumberSign,FOLLOW_66); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_1_0_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalSTFunctionParser.g:4606:4: ( (lv_value_5_0= ruleNumeric ) )
                    // InternalSTFunctionParser.g:4607:5: (lv_value_5_0= ruleNumeric )
                    {
                    // InternalSTFunctionParser.g:4607:5: (lv_value_5_0= ruleNumeric )
                    // InternalSTFunctionParser.g:4608:6: lv_value_5_0= ruleNumeric
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueNumericParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_5_0=ruleNumeric();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_5_0,
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.Numeric");
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
    // $ANTLR end "ruleSTNumericLiteral"


    // $ANTLR start "entryRuleSTSignedNumericLiteral"
    // InternalSTFunctionParser.g:4630:1: entryRuleSTSignedNumericLiteral returns [EObject current=null] : iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF ;
    public final EObject entryRuleSTSignedNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignedNumericLiteral = null;


        try {
            // InternalSTFunctionParser.g:4630:63: (iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF )
            // InternalSTFunctionParser.g:4631:2: iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTSignedNumericLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTSignedNumericLiteral=ruleSTSignedNumericLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTSignedNumericLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTSignedNumericLiteral"


    // $ANTLR start "ruleSTSignedNumericLiteral"
    // InternalSTFunctionParser.g:4637:1: ruleSTSignedNumericLiteral returns [EObject current=null] : ( (lv_value_0_0= ruleSignedNumeric ) ) ;
    public final EObject ruleSTSignedNumericLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4643:2: ( ( (lv_value_0_0= ruleSignedNumeric ) ) )
            // InternalSTFunctionParser.g:4644:2: ( (lv_value_0_0= ruleSignedNumeric ) )
            {
            // InternalSTFunctionParser.g:4644:2: ( (lv_value_0_0= ruleSignedNumeric ) )
            // InternalSTFunctionParser.g:4645:3: (lv_value_0_0= ruleSignedNumeric )
            {
            // InternalSTFunctionParser.g:4645:3: (lv_value_0_0= ruleSignedNumeric )
            // InternalSTFunctionParser.g:4646:4: lv_value_0_0= ruleSignedNumeric
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getSTSignedNumericLiteralAccess().getValueSignedNumericParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleSignedNumeric();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getSTSignedNumericLiteralRule());
              				}
              				set(
              					current,
              					"value",
              					lv_value_0_0,
              					"org.eclipse.fordiac.ide.structuredtextcore.STCore.SignedNumeric");
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
    // $ANTLR end "ruleSTSignedNumericLiteral"


    // $ANTLR start "entryRuleSTDateLiteralType"
    // InternalSTFunctionParser.g:4666:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalSTFunctionParser.g:4666:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalSTFunctionParser.g:4667:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalSTFunctionParser.g:4673:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4679:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalSTFunctionParser.g:4680:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalSTFunctionParser.g:4680:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt85=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt85=1;
                }
                break;
            case D:
                {
                alt85=2;
                }
                break;
            case LD:
                {
                alt85=3;
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
                    // InternalSTFunctionParser.g:4681:3: this_STDateType_0= ruleSTDateType
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
                    // InternalSTFunctionParser.g:4692:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4698:3: kw= LD
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
    // InternalSTFunctionParser.g:4707:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalSTFunctionParser.g:4707:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalSTFunctionParser.g:4708:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalSTFunctionParser.g:4714:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4720:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalSTFunctionParser.g:4721:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalSTFunctionParser.g:4721:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalSTFunctionParser.g:4722:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalSTFunctionParser.g:4722:3: ( ( ruleSTDateLiteralType ) )
            // InternalSTFunctionParser.g:4723:4: ( ruleSTDateLiteralType )
            {
            // InternalSTFunctionParser.g:4723:4: ( ruleSTDateLiteralType )
            // InternalSTFunctionParser.g:4724:5: ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_29);
            ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:4742:3: ( (lv_value_2_0= ruleDate ) )
            // InternalSTFunctionParser.g:4743:4: (lv_value_2_0= ruleDate )
            {
            // InternalSTFunctionParser.g:4743:4: (lv_value_2_0= ruleDate )
            // InternalSTFunctionParser.g:4744:5: lv_value_2_0= ruleDate
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
    // InternalSTFunctionParser.g:4765:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalSTFunctionParser.g:4765:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalSTFunctionParser.g:4766:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalSTFunctionParser.g:4772:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4778:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalSTFunctionParser.g:4779:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalSTFunctionParser.g:4779:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt86=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt86=1;
                }
                break;
            case T:
                {
                alt86=2;
                }
                break;
            case LT:
                {
                alt86=3;
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
                    // InternalSTFunctionParser.g:4780:3: this_STAnyDurationType_0= ruleSTAnyDurationType
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
                    // InternalSTFunctionParser.g:4791:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:4797:3: kw= LT
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
    // InternalSTFunctionParser.g:4806:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalSTFunctionParser.g:4806:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalSTFunctionParser.g:4807:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalSTFunctionParser.g:4813:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4819:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalSTFunctionParser.g:4820:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalSTFunctionParser.g:4820:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalSTFunctionParser.g:4821:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalSTFunctionParser.g:4821:3: ( ( ruleSTTimeLiteralType ) )
            // InternalSTFunctionParser.g:4822:4: ( ruleSTTimeLiteralType )
            {
            // InternalSTFunctionParser.g:4822:4: ( ruleSTTimeLiteralType )
            // InternalSTFunctionParser.g:4823:5: ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_29);
            ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:4841:3: ( (lv_value_2_0= ruleTime ) )
            // InternalSTFunctionParser.g:4842:4: (lv_value_2_0= ruleTime )
            {
            // InternalSTFunctionParser.g:4842:4: (lv_value_2_0= ruleTime )
            // InternalSTFunctionParser.g:4843:5: lv_value_2_0= ruleTime
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
    // InternalSTFunctionParser.g:4864:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalSTFunctionParser.g:4864:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalSTFunctionParser.g:4865:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalSTFunctionParser.g:4871:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4877:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalSTFunctionParser.g:4878:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalSTFunctionParser.g:4878:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalSTFunctionParser.g:4879:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalSTFunctionParser.g:4879:3: ( ( ruleSTTimeOfDayType ) )
            // InternalSTFunctionParser.g:4880:4: ( ruleSTTimeOfDayType )
            {
            // InternalSTFunctionParser.g:4880:4: ( ruleSTTimeOfDayType )
            // InternalSTFunctionParser.g:4881:5: ruleSTTimeOfDayType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_29);
            ruleSTTimeOfDayType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:4899:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalSTFunctionParser.g:4900:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalSTFunctionParser.g:4900:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalSTFunctionParser.g:4901:5: lv_value_2_0= ruleTimeOfDay
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
    // InternalSTFunctionParser.g:4922:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalSTFunctionParser.g:4922:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalSTFunctionParser.g:4923:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalSTFunctionParser.g:4929:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:4935:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalSTFunctionParser.g:4936:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalSTFunctionParser.g:4936:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalSTFunctionParser.g:4937:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalSTFunctionParser.g:4937:3: ( ( ruleSTDateAndTimeType ) )
            // InternalSTFunctionParser.g:4938:4: ( ruleSTDateAndTimeType )
            {
            // InternalSTFunctionParser.g:4938:4: ( ruleSTDateAndTimeType )
            // InternalSTFunctionParser.g:4939:5: ruleSTDateAndTimeType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_29);
            ruleSTDateAndTimeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalSTFunctionParser.g:4957:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalSTFunctionParser.g:4958:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalSTFunctionParser.g:4958:4: (lv_value_2_0= ruleDateAndTime )
            // InternalSTFunctionParser.g:4959:5: lv_value_2_0= ruleDateAndTime
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
    // InternalSTFunctionParser.g:4980:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalSTFunctionParser.g:4980:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalSTFunctionParser.g:4981:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalSTFunctionParser.g:4987:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:4993:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) )
            // InternalSTFunctionParser.g:4994:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            {
            // InternalSTFunctionParser.g:4994:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            // InternalSTFunctionParser.g:4995:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) )
            {
            // InternalSTFunctionParser.g:4995:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==WSTRING||LA87_0==STRING||LA87_0==WCHAR||LA87_0==CHAR) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // InternalSTFunctionParser.g:4996:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalSTFunctionParser.g:4996:4: ( ( ruleSTAnyCharsType ) )
                    // InternalSTFunctionParser.g:4997:5: ( ruleSTAnyCharsType )
                    {
                    // InternalSTFunctionParser.g:4997:5: ( ruleSTAnyCharsType )
                    // InternalSTFunctionParser.g:4998:6: ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
                    ruleSTAnyCharsType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSTFunctionParser.g:5017:3: ( (lv_value_2_0= RULE_STRING ) )
            // InternalSTFunctionParser.g:5018:4: (lv_value_2_0= RULE_STRING )
            {
            // InternalSTFunctionParser.g:5018:4: (lv_value_2_0= RULE_STRING )
            // InternalSTFunctionParser.g:5019:5: lv_value_2_0= RULE_STRING
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


    // $ANTLR start "entryRuleSTEnumLiteral"
    // InternalSTFunctionParser.g:5039:1: entryRuleSTEnumLiteral returns [EObject current=null] : iv_ruleSTEnumLiteral= ruleSTEnumLiteral EOF ;
    public final EObject entryRuleSTEnumLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEnumLiteral = null;


        try {
            // InternalSTFunctionParser.g:5039:54: (iv_ruleSTEnumLiteral= ruleSTEnumLiteral EOF )
            // InternalSTFunctionParser.g:5040:2: iv_ruleSTEnumLiteral= ruleSTEnumLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTEnumLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTEnumLiteral=ruleSTEnumLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTEnumLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTEnumLiteral"


    // $ANTLR start "ruleSTEnumLiteral"
    // InternalSTFunctionParser.g:5046:1: ruleSTEnumLiteral returns [EObject current=null] : ( ( ruleEnumValue ) ) ;
    public final EObject ruleSTEnumLiteral() throws RecognitionException {
        EObject current = null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5052:2: ( ( ( ruleEnumValue ) ) )
            // InternalSTFunctionParser.g:5053:2: ( ( ruleEnumValue ) )
            {
            // InternalSTFunctionParser.g:5053:2: ( ( ruleEnumValue ) )
            // InternalSTFunctionParser.g:5054:3: ( ruleEnumValue )
            {
            // InternalSTFunctionParser.g:5054:3: ( ruleEnumValue )
            // InternalSTFunctionParser.g:5055:4: ruleEnumValue
            {
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getSTEnumLiteralRule());
              				}
              			
            }
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getSTEnumLiteralAccess().getValueEnumeratedValueCrossReference_0());
              			
            }
            pushFollow(FOLLOW_2);
            ruleEnumValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleSTEnumLiteral"


    // $ANTLR start "entryRuleEnumValue"
    // InternalSTFunctionParser.g:5072:1: entryRuleEnumValue returns [String current=null] : iv_ruleEnumValue= ruleEnumValue EOF ;
    public final String entryRuleEnumValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEnumValue = null;


        try {
            // InternalSTFunctionParser.g:5072:49: (iv_ruleEnumValue= ruleEnumValue EOF )
            // InternalSTFunctionParser.g:5073:2: iv_ruleEnumValue= ruleEnumValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEnumValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleEnumValue=ruleEnumValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEnumValue.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEnumValue"


    // $ANTLR start "ruleEnumValue"
    // InternalSTFunctionParser.g:5079:1: ruleEnumValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID ) ;
    public final AntlrDatatypeRuleToken ruleEnumValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_ID_2=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:5085:2: ( (this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID ) )
            // InternalSTFunctionParser.g:5086:2: (this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID )
            {
            // InternalSTFunctionParser.g:5086:2: (this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID )
            // InternalSTFunctionParser.g:5087:3: this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getEnumValueAccess().getQualifiedNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_29);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_QualifiedName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,NumberSign,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getEnumValueAccess().getNumberSignKeyword_1());
              		
            }
            this_ID_2=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ID_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ID_2, grammarAccess.getEnumValueAccess().getIDTerminalRuleCall_2());
              		
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
    // $ANTLR end "ruleEnumValue"


    // $ANTLR start "entryRuleSTAnyType"
    // InternalSTFunctionParser.g:5113:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalSTFunctionParser.g:5113:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalSTFunctionParser.g:5114:2: iv_ruleSTAnyType= ruleSTAnyType EOF
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
    // InternalSTFunctionParser.g:5120:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_QualifiedName_0 = null;

        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:5126:2: ( (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalSTFunctionParser.g:5127:2: (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalSTFunctionParser.g:5127:2: (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==RULE_ID) ) {
                alt88=1;
            }
            else if ( (LA88_0==LDATE_AND_TIME||LA88_0==DATE_AND_TIME||LA88_0==LTIME_OF_DAY||LA88_0==TIME_OF_DAY||LA88_0==WSTRING||LA88_0==STRING||LA88_0==DWORD||(LA88_0>=LDATE && LA88_0<=LWORD)||(LA88_0>=UDINT && LA88_0<=ULINT)||(LA88_0>=USINT && LA88_0<=WCHAR)||(LA88_0>=BOOL && LA88_0<=BYTE)||(LA88_0>=CHAR && LA88_0<=DINT)||(LA88_0>=LINT && LA88_0<=LTOD)||(LA88_0>=REAL && LA88_0<=SINT)||LA88_0==TIME||LA88_0==UINT||LA88_0==WORD||(LA88_0>=INT && LA88_0<=LDT)||LA88_0==TOD||LA88_0==DT) ) {
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
                    // InternalSTFunctionParser.g:5128:3: this_QualifiedName_0= ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTAnyTypeAccess().getQualifiedNameParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_QualifiedName_0=ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_QualifiedName_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5139:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
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
    // InternalSTFunctionParser.g:5153:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalSTFunctionParser.g:5153:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalSTFunctionParser.g:5154:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
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
    // InternalSTFunctionParser.g:5160:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:5166:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalSTFunctionParser.g:5167:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalSTFunctionParser.g:5167:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt89=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt89=1;
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
                alt89=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt89=3;
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
                alt89=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
                {
                alt89=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }

            switch (alt89) {
                case 1 :
                    // InternalSTFunctionParser.g:5168:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalSTFunctionParser.g:5179:3: this_STAnyNumType_1= ruleSTAnyNumType
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
                    // InternalSTFunctionParser.g:5190:3: this_STAnyDurationType_2= ruleSTAnyDurationType
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
                    // InternalSTFunctionParser.g:5201:3: this_STAnyDateType_3= ruleSTAnyDateType
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
                    // InternalSTFunctionParser.g:5212:3: this_STAnyCharsType_4= ruleSTAnyCharsType
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
    // InternalSTFunctionParser.g:5226:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalSTFunctionParser.g:5226:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalSTFunctionParser.g:5227:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
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
    // InternalSTFunctionParser.g:5233:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5239:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalSTFunctionParser.g:5240:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalSTFunctionParser.g:5240:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt90=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt90=1;
                }
                break;
            case BYTE:
                {
                alt90=2;
                }
                break;
            case WORD:
                {
                alt90=3;
                }
                break;
            case DWORD:
                {
                alt90=4;
                }
                break;
            case LWORD:
                {
                alt90=5;
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
                    // InternalSTFunctionParser.g:5241:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5247:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:5253:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:5259:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTFunctionParser.g:5265:3: kw= LWORD
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
    // InternalSTFunctionParser.g:5274:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalSTFunctionParser.g:5274:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalSTFunctionParser.g:5275:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
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
    // InternalSTFunctionParser.g:5281:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5287:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalSTFunctionParser.g:5288:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalSTFunctionParser.g:5288:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt91=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt91=1;
                }
                break;
            case INT:
                {
                alt91=2;
                }
                break;
            case DINT:
                {
                alt91=3;
                }
                break;
            case LINT:
                {
                alt91=4;
                }
                break;
            case USINT:
                {
                alt91=5;
                }
                break;
            case UINT:
                {
                alt91=6;
                }
                break;
            case UDINT:
                {
                alt91=7;
                }
                break;
            case ULINT:
                {
                alt91=8;
                }
                break;
            case REAL:
                {
                alt91=9;
                }
                break;
            case LREAL:
                {
                alt91=10;
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
                    // InternalSTFunctionParser.g:5289:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5295:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:5301:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:5307:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSTFunctionParser.g:5313:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSTFunctionParser.g:5319:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSTFunctionParser.g:5325:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSTFunctionParser.g:5331:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSTFunctionParser.g:5337:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSTFunctionParser.g:5343:3: kw= LREAL
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
    // InternalSTFunctionParser.g:5352:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalSTFunctionParser.g:5352:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalSTFunctionParser.g:5353:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
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
    // InternalSTFunctionParser.g:5359:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5365:2: ( (kw= TIME | kw= LTIME ) )
            // InternalSTFunctionParser.g:5366:2: (kw= TIME | kw= LTIME )
            {
            // InternalSTFunctionParser.g:5366:2: (kw= TIME | kw= LTIME )
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==TIME) ) {
                alt92=1;
            }
            else if ( (LA92_0==LTIME) ) {
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
                    // InternalSTFunctionParser.g:5367:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5373:3: kw= LTIME
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
    // InternalSTFunctionParser.g:5382:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalSTFunctionParser.g:5382:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalSTFunctionParser.g:5383:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
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
    // InternalSTFunctionParser.g:5389:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:5395:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalSTFunctionParser.g:5396:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalSTFunctionParser.g:5396:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt93=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt93=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt93=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
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
                    // InternalSTFunctionParser.g:5397:3: this_STDateType_0= ruleSTDateType
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
                    // InternalSTFunctionParser.g:5408:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
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
                    // InternalSTFunctionParser.g:5419:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
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
    // InternalSTFunctionParser.g:5433:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalSTFunctionParser.g:5433:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalSTFunctionParser.g:5434:2: iv_ruleSTDateType= ruleSTDateType EOF
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
    // InternalSTFunctionParser.g:5440:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5446:2: ( (kw= DATE | kw= LDATE ) )
            // InternalSTFunctionParser.g:5447:2: (kw= DATE | kw= LDATE )
            {
            // InternalSTFunctionParser.g:5447:2: (kw= DATE | kw= LDATE )
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==DATE) ) {
                alt94=1;
            }
            else if ( (LA94_0==LDATE) ) {
                alt94=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                throw nvae;
            }
            switch (alt94) {
                case 1 :
                    // InternalSTFunctionParser.g:5448:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5454:3: kw= LDATE
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
    // InternalSTFunctionParser.g:5463:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalSTFunctionParser.g:5463:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalSTFunctionParser.g:5464:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
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
    // InternalSTFunctionParser.g:5470:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5476:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalSTFunctionParser.g:5477:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalSTFunctionParser.g:5477:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt95=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt95=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt95=2;
                }
                break;
            case TOD:
                {
                alt95=3;
                }
                break;
            case LTOD:
                {
                alt95=4;
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
                    // InternalSTFunctionParser.g:5478:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5484:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:5490:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:5496:3: kw= LTOD
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
    // InternalSTFunctionParser.g:5505:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalSTFunctionParser.g:5505:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalSTFunctionParser.g:5506:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
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
    // InternalSTFunctionParser.g:5512:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5518:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalSTFunctionParser.g:5519:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalSTFunctionParser.g:5519:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt96=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt96=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt96=2;
                }
                break;
            case DT:
                {
                alt96=3;
                }
                break;
            case LDT:
                {
                alt96=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;
            }

            switch (alt96) {
                case 1 :
                    // InternalSTFunctionParser.g:5520:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5526:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:5532:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:5538:3: kw= LDT
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
    // InternalSTFunctionParser.g:5547:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalSTFunctionParser.g:5547:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalSTFunctionParser.g:5548:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
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
    // InternalSTFunctionParser.g:5554:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5560:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalSTFunctionParser.g:5561:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalSTFunctionParser.g:5561:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt97=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt97=1;
                }
                break;
            case WSTRING:
                {
                alt97=2;
                }
                break;
            case CHAR:
                {
                alt97=3;
                }
                break;
            case WCHAR:
                {
                alt97=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }

            switch (alt97) {
                case 1 :
                    // InternalSTFunctionParser.g:5562:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5568:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:5574:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:5580:3: kw= WCHAR
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


    // $ANTLR start "entryRuleQualifiedName"
    // InternalSTFunctionParser.g:5589:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalSTFunctionParser.g:5589:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalSTFunctionParser.g:5590:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // InternalSTFunctionParser.g:5596:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5602:2: ( (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* ) )
            // InternalSTFunctionParser.g:5603:2: (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* )
            {
            // InternalSTFunctionParser.g:5603:2: (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* )
            // InternalSTFunctionParser.g:5604:3: this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_70); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0());
              		
            }
            // InternalSTFunctionParser.g:5611:3: (kw= ColonColon this_ID_2= RULE_ID )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==ColonColon) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // InternalSTFunctionParser.g:5612:4: kw= ColonColon this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,ColonColon,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getColonColonKeyword_1_0());
            	      			
            	    }
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_70); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop98;
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
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleQualifiedNameWithWildcard"
    // InternalSTFunctionParser.g:5629:1: entryRuleQualifiedNameWithWildcard returns [String current=null] : iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF ;
    public final String entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildcard = null;


        try {
            // InternalSTFunctionParser.g:5629:65: (iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF )
            // InternalSTFunctionParser.g:5630:2: iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameWithWildcardRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedNameWithWildcard=ruleQualifiedNameWithWildcard();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedNameWithWildcard.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedNameWithWildcard"


    // $ANTLR start "ruleQualifiedNameWithWildcard"
    // InternalSTFunctionParser.g:5636:1: ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildcard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:5642:2: ( (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? ) )
            // InternalSTFunctionParser.g:5643:2: (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? )
            {
            // InternalSTFunctionParser.g:5643:2: (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? )
            // InternalSTFunctionParser.g:5644:3: this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_71);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_QualifiedName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSTFunctionParser.g:5654:3: (kw= ColonColonAsterisk )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==ColonColonAsterisk) ) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    // InternalSTFunctionParser.g:5655:4: kw= ColonColonAsterisk
                    {
                    kw=(Token)match(input,ColonColonAsterisk,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getColonColonAsteriskKeyword_1());
                      			
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
    // $ANTLR end "ruleQualifiedNameWithWildcard"


    // $ANTLR start "entryRuleNumeric"
    // InternalSTFunctionParser.g:5665:1: entryRuleNumeric returns [String current=null] : iv_ruleNumeric= ruleNumeric EOF ;
    public final String entryRuleNumeric() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumeric = null;


        try {
            // InternalSTFunctionParser.g:5665:47: (iv_ruleNumeric= ruleNumeric EOF )
            // InternalSTFunctionParser.g:5666:2: iv_ruleNumeric= ruleNumeric EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNumericRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNumeric=ruleNumeric();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNumeric.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumeric"


    // $ANTLR start "ruleNumeric"
    // InternalSTFunctionParser.g:5672:1: ruleNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) ;
    public final AntlrDatatypeRuleToken ruleNumeric() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_NON_DECIMAL_3=null;
        AntlrDatatypeRuleToken this_Number_2 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:5678:2: ( (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) )
            // InternalSTFunctionParser.g:5679:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            {
            // InternalSTFunctionParser.g:5679:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            int alt100=4;
            switch ( input.LA(1) ) {
            case TRUE:
                {
                alt100=1;
                }
                break;
            case FALSE:
                {
                alt100=2;
                }
                break;
            case RULE_INT:
                {
                alt100=3;
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt100=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }

            switch (alt100) {
                case 1 :
                    // InternalSTFunctionParser.g:5680:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5686:3: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getFALSEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSTFunctionParser.g:5692:3: this_Number_2= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getNumericAccess().getNumberParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_Number_2=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_Number_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSTFunctionParser.g:5703:3: this_NON_DECIMAL_3= RULE_NON_DECIMAL
                    {
                    this_NON_DECIMAL_3=(Token)match(input,RULE_NON_DECIMAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_NON_DECIMAL_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_NON_DECIMAL_3, grammarAccess.getNumericAccess().getNON_DECIMALTerminalRuleCall_3());
                      		
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
    // $ANTLR end "ruleNumeric"


    // $ANTLR start "entryRuleNumber"
    // InternalSTFunctionParser.g:5714:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalSTFunctionParser.g:5716:2: (iv_ruleNumber= ruleNumber EOF )
            // InternalSTFunctionParser.g:5717:2: iv_ruleNumber= ruleNumber EOF
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

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRuleNumber"


    // $ANTLR start "ruleNumber"
    // InternalSTFunctionParser.g:5726:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_DECIMAL_3=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalSTFunctionParser.g:5733:2: ( (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? ) )
            // InternalSTFunctionParser.g:5734:2: (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? )
            {
            // InternalSTFunctionParser.g:5734:2: (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? )
            // InternalSTFunctionParser.g:5735:3: this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalSTFunctionParser.g:5742:3: (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==FullStop) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // InternalSTFunctionParser.g:5743:4: kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL )
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    // InternalSTFunctionParser.g:5748:4: (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL )
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==RULE_INT) ) {
                        alt101=1;
                    }
                    else if ( (LA101_0==RULE_DECIMAL) ) {
                        alt101=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 101, 0, input);

                        throw nvae;
                    }
                    switch (alt101) {
                        case 1 :
                            // InternalSTFunctionParser.g:5749:5: this_INT_2= RULE_INT
                            {
                            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_INT_2);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_1_0());
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTFunctionParser.g:5757:5: this_DECIMAL_3= RULE_DECIMAL
                            {
                            this_DECIMAL_3=(Token)match(input,RULE_DECIMAL,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_DECIMAL_3);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_DECIMAL_3, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_1_1());
                              				
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

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "ruleNumber"


    // $ANTLR start "entryRuleSignedNumeric"
    // InternalSTFunctionParser.g:5773:1: entryRuleSignedNumeric returns [String current=null] : iv_ruleSignedNumeric= ruleSignedNumeric EOF ;
    public final String entryRuleSignedNumeric() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSignedNumeric = null;


        try {
            // InternalSTFunctionParser.g:5773:53: (iv_ruleSignedNumeric= ruleSignedNumeric EOF )
            // InternalSTFunctionParser.g:5774:2: iv_ruleSignedNumeric= ruleSignedNumeric EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSignedNumericRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSignedNumeric=ruleSignedNumeric();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSignedNumeric.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSignedNumeric"


    // $ANTLR start "ruleSignedNumeric"
    // InternalSTFunctionParser.g:5780:1: ruleSignedNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_SignedNumber_0= ruleSignedNumber ;
    public final AntlrDatatypeRuleToken ruleSignedNumeric() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_SignedNumber_0 = null;



        	enterRule();

        try {
            // InternalSTFunctionParser.g:5786:2: (this_SignedNumber_0= ruleSignedNumber )
            // InternalSTFunctionParser.g:5787:2: this_SignedNumber_0= ruleSignedNumber
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getSignedNumericAccess().getSignedNumberParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_SignedNumber_0=ruleSignedNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_SignedNumber_0);
              	
            }
            if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleSignedNumeric"


    // $ANTLR start "entryRuleSignedNumber"
    // InternalSTFunctionParser.g:5800:1: entryRuleSignedNumber returns [String current=null] : iv_ruleSignedNumber= ruleSignedNumber EOF ;
    public final String entryRuleSignedNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSignedNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalSTFunctionParser.g:5802:2: (iv_ruleSignedNumber= ruleSignedNumber EOF )
            // InternalSTFunctionParser.g:5803:2: iv_ruleSignedNumber= ruleSignedNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSignedNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSignedNumber=ruleSignedNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSignedNumber.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRuleSignedNumber"


    // $ANTLR start "ruleSignedNumber"
    // InternalSTFunctionParser.g:5812:1: ruleSignedNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) ;
    public final AntlrDatatypeRuleToken ruleSignedNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_DECIMAL_5=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalSTFunctionParser.g:5819:2: ( ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) )
            // InternalSTFunctionParser.g:5820:2: ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? )
            {
            // InternalSTFunctionParser.g:5820:2: ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? )
            // InternalSTFunctionParser.g:5821:3: (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )?
            {
            // InternalSTFunctionParser.g:5821:3: (kw= PlusSign | kw= HyphenMinus )
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==PlusSign) ) {
                alt103=1;
            }
            else if ( (LA103_0==HyphenMinus) ) {
                alt103=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }
            switch (alt103) {
                case 1 :
                    // InternalSTFunctionParser.g:5822:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5828:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_1());
              		
            }
            // InternalSTFunctionParser.g:5841:3: (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==FullStop) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // InternalSTFunctionParser.g:5842:4: kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL )
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getFullStopKeyword_2_0());
                      			
                    }
                    // InternalSTFunctionParser.g:5847:4: (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL )
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==RULE_INT) ) {
                        alt104=1;
                    }
                    else if ( (LA104_0==RULE_DECIMAL) ) {
                        alt104=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 104, 0, input);

                        throw nvae;
                    }
                    switch (alt104) {
                        case 1 :
                            // InternalSTFunctionParser.g:5848:5: this_INT_4= RULE_INT
                            {
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_INT_4);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_INT_4, grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_2_1_0());
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalSTFunctionParser.g:5856:5: this_DECIMAL_5= RULE_DECIMAL
                            {
                            this_DECIMAL_5=(Token)match(input,RULE_DECIMAL,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_DECIMAL_5);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_DECIMAL_5, grammarAccess.getSignedNumberAccess().getDECIMALTerminalRuleCall_2_1_1());
                              				
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

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "ruleSignedNumber"


    // $ANTLR start "entryRuleTime"
    // InternalSTFunctionParser.g:5872:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalSTFunctionParser.g:5874:2: (iv_ruleTime= ruleTime EOF )
            // InternalSTFunctionParser.g:5875:2: iv_ruleTime= ruleTime EOF
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

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRuleTime"


    // $ANTLR start "ruleTime"
    // InternalSTFunctionParser.g:5884:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_TIME_VALUE_2=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalSTFunctionParser.g:5891:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) )
            // InternalSTFunctionParser.g:5892:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            {
            // InternalSTFunctionParser.g:5892:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            // InternalSTFunctionParser.g:5893:3: (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE
            {
            // InternalSTFunctionParser.g:5893:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt106=3;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==PlusSign) ) {
                alt106=1;
            }
            else if ( (LA106_0==HyphenMinus) ) {
                alt106=2;
            }
            switch (alt106) {
                case 1 :
                    // InternalSTFunctionParser.g:5894:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTimeAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSTFunctionParser.g:5900:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTimeAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_TIME_VALUE_2=(Token)match(input,RULE_TIME_VALUE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_TIME_VALUE_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_TIME_VALUE_2, grammarAccess.getTimeAccess().getTIME_VALUETerminalRuleCall_1());
              		
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

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "ruleTime"


    // $ANTLR start "entryRuleDate"
    // InternalSTFunctionParser.g:5920:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalSTFunctionParser.g:5920:44: (iv_ruleDate= ruleDate EOF )
            // InternalSTFunctionParser.g:5921:2: iv_ruleDate= ruleDate EOF
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
    // InternalSTFunctionParser.g:5927:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:5933:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalSTFunctionParser.g:5934:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalSTFunctionParser.g:5934:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalSTFunctionParser.g:5935:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_75); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_75); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_67); if (state.failed) return current;
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
    // InternalSTFunctionParser.g:5970:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalSTFunctionParser.g:5970:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalSTFunctionParser.g:5971:2: iv_ruleDateAndTime= ruleDateAndTime EOF
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
    // InternalSTFunctionParser.g:5977:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? ) ;
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
            // InternalSTFunctionParser.g:5983:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? ) )
            // InternalSTFunctionParser.g:5984:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? )
            {
            // InternalSTFunctionParser.g:5984:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? )
            // InternalSTFunctionParser.g:5985:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_75); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_75); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_75); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_5());
              		
            }
            this_INT_6=(Token)match(input,RULE_INT,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_6);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_6, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_6());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_7());
              		
            }
            this_INT_8=(Token)match(input,RULE_INT,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_8);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_8, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_8());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_9());
              		
            }
            this_INT_10=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_10);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
              		
            }
            // InternalSTFunctionParser.g:6052:3: (kw= FullStop this_INT_12= RULE_INT )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==FullStop) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // InternalSTFunctionParser.g:6053:4: kw= FullStop this_INT_12= RULE_INT
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getFullStopKeyword_11_0());
                      			
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
    // InternalSTFunctionParser.g:6070:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalSTFunctionParser.g:6070:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalSTFunctionParser.g:6071:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalSTFunctionParser.g:6077:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6083:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) )
            // InternalSTFunctionParser.g:6084:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            {
            // InternalSTFunctionParser.g:6084:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            // InternalSTFunctionParser.g:6085:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalSTFunctionParser.g:6116:3: (kw= FullStop this_INT_6= RULE_INT )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==FullStop) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // InternalSTFunctionParser.g:6117:4: kw= FullStop this_INT_6= RULE_INT
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getFullStopKeyword_5_0());
                      			
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


    // $ANTLR start "ruleSubrangeOperator"
    // InternalSTFunctionParser.g:6134:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6140:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalSTFunctionParser.g:6141:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalSTFunctionParser.g:6141:2: (enumLiteral_0= FullStopFullStop )
            // InternalSTFunctionParser.g:6142:3: enumLiteral_0= FullStopFullStop
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
    // InternalSTFunctionParser.g:6151:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6157:2: ( (enumLiteral_0= OR ) )
            // InternalSTFunctionParser.g:6158:2: (enumLiteral_0= OR )
            {
            // InternalSTFunctionParser.g:6158:2: (enumLiteral_0= OR )
            // InternalSTFunctionParser.g:6159:3: enumLiteral_0= OR
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
    // InternalSTFunctionParser.g:6168:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6174:2: ( (enumLiteral_0= XOR ) )
            // InternalSTFunctionParser.g:6175:2: (enumLiteral_0= XOR )
            {
            // InternalSTFunctionParser.g:6175:2: (enumLiteral_0= XOR )
            // InternalSTFunctionParser.g:6176:3: enumLiteral_0= XOR
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
    // InternalSTFunctionParser.g:6185:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6191:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalSTFunctionParser.g:6192:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalSTFunctionParser.g:6192:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==AND) ) {
                alt109=1;
            }
            else if ( (LA109_0==Ampersand) ) {
                alt109=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 109, 0, input);

                throw nvae;
            }
            switch (alt109) {
                case 1 :
                    // InternalSTFunctionParser.g:6193:3: (enumLiteral_0= AND )
                    {
                    // InternalSTFunctionParser.g:6193:3: (enumLiteral_0= AND )
                    // InternalSTFunctionParser.g:6194:4: enumLiteral_0= AND
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
                    // InternalSTFunctionParser.g:6201:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalSTFunctionParser.g:6201:3: (enumLiteral_1= Ampersand )
                    // InternalSTFunctionParser.g:6202:4: enumLiteral_1= Ampersand
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
    // InternalSTFunctionParser.g:6212:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6218:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalSTFunctionParser.g:6219:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalSTFunctionParser.g:6219:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==EqualsSign) ) {
                alt110=1;
            }
            else if ( (LA110_0==LessThanSignGreaterThanSign) ) {
                alt110=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }
            switch (alt110) {
                case 1 :
                    // InternalSTFunctionParser.g:6220:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalSTFunctionParser.g:6220:3: (enumLiteral_0= EqualsSign )
                    // InternalSTFunctionParser.g:6221:4: enumLiteral_0= EqualsSign
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
                    // InternalSTFunctionParser.g:6228:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalSTFunctionParser.g:6228:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalSTFunctionParser.g:6229:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalSTFunctionParser.g:6239:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6245:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalSTFunctionParser.g:6246:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalSTFunctionParser.g:6246:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt111=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt111=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt111=2;
                }
                break;
            case GreaterThanSign:
                {
                alt111=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt111=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }

            switch (alt111) {
                case 1 :
                    // InternalSTFunctionParser.g:6247:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalSTFunctionParser.g:6247:3: (enumLiteral_0= LessThanSign )
                    // InternalSTFunctionParser.g:6248:4: enumLiteral_0= LessThanSign
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
                    // InternalSTFunctionParser.g:6255:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalSTFunctionParser.g:6255:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalSTFunctionParser.g:6256:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalSTFunctionParser.g:6263:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalSTFunctionParser.g:6263:3: (enumLiteral_2= GreaterThanSign )
                    // InternalSTFunctionParser.g:6264:4: enumLiteral_2= GreaterThanSign
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
                    // InternalSTFunctionParser.g:6271:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalSTFunctionParser.g:6271:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalSTFunctionParser.g:6272:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalSTFunctionParser.g:6282:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6288:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalSTFunctionParser.g:6289:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalSTFunctionParser.g:6289:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==PlusSign) ) {
                alt112=1;
            }
            else if ( (LA112_0==HyphenMinus) ) {
                alt112=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }
            switch (alt112) {
                case 1 :
                    // InternalSTFunctionParser.g:6290:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalSTFunctionParser.g:6290:3: (enumLiteral_0= PlusSign )
                    // InternalSTFunctionParser.g:6291:4: enumLiteral_0= PlusSign
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
                    // InternalSTFunctionParser.g:6298:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalSTFunctionParser.g:6298:3: (enumLiteral_1= HyphenMinus )
                    // InternalSTFunctionParser.g:6299:4: enumLiteral_1= HyphenMinus
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
    // InternalSTFunctionParser.g:6309:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6315:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalSTFunctionParser.g:6316:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalSTFunctionParser.g:6316:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt113=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt113=1;
                }
                break;
            case Solidus:
                {
                alt113=2;
                }
                break;
            case MOD:
                {
                alt113=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // InternalSTFunctionParser.g:6317:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalSTFunctionParser.g:6317:3: (enumLiteral_0= Asterisk )
                    // InternalSTFunctionParser.g:6318:4: enumLiteral_0= Asterisk
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
                    // InternalSTFunctionParser.g:6325:3: (enumLiteral_1= Solidus )
                    {
                    // InternalSTFunctionParser.g:6325:3: (enumLiteral_1= Solidus )
                    // InternalSTFunctionParser.g:6326:4: enumLiteral_1= Solidus
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
                    // InternalSTFunctionParser.g:6333:3: (enumLiteral_2= MOD )
                    {
                    // InternalSTFunctionParser.g:6333:3: (enumLiteral_2= MOD )
                    // InternalSTFunctionParser.g:6334:4: enumLiteral_2= MOD
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
    // InternalSTFunctionParser.g:6344:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6350:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalSTFunctionParser.g:6351:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalSTFunctionParser.g:6351:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalSTFunctionParser.g:6352:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalSTFunctionParser.g:6361:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6367:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalSTFunctionParser.g:6368:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalSTFunctionParser.g:6368:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt114=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt114=1;
                }
                break;
            case PlusSign:
                {
                alt114=2;
                }
                break;
            case NOT:
                {
                alt114=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 114, 0, input);

                throw nvae;
            }

            switch (alt114) {
                case 1 :
                    // InternalSTFunctionParser.g:6369:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalSTFunctionParser.g:6369:3: (enumLiteral_0= HyphenMinus )
                    // InternalSTFunctionParser.g:6370:4: enumLiteral_0= HyphenMinus
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
                    // InternalSTFunctionParser.g:6377:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalSTFunctionParser.g:6377:3: (enumLiteral_1= PlusSign )
                    // InternalSTFunctionParser.g:6378:4: enumLiteral_1= PlusSign
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
                    // InternalSTFunctionParser.g:6385:3: (enumLiteral_2= NOT )
                    {
                    // InternalSTFunctionParser.g:6385:3: (enumLiteral_2= NOT )
                    // InternalSTFunctionParser.g:6386:4: enumLiteral_2= NOT
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


    // $ANTLR start "ruleSTBuiltinFeature"
    // InternalSTFunctionParser.g:6396:1: ruleSTBuiltinFeature returns [Enumerator current=null] : (enumLiteral_0= THIS ) ;
    public final Enumerator ruleSTBuiltinFeature() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6402:2: ( (enumLiteral_0= THIS ) )
            // InternalSTFunctionParser.g:6403:2: (enumLiteral_0= THIS )
            {
            // InternalSTFunctionParser.g:6403:2: (enumLiteral_0= THIS )
            // InternalSTFunctionParser.g:6404:3: enumLiteral_0= THIS
            {
            enumLiteral_0=(Token)match(input,THIS,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = grammarAccess.getSTBuiltinFeatureAccess().getTHISEnumLiteralDeclaration().getEnumLiteral().getInstance();
              			newLeafNode(enumLiteral_0, grammarAccess.getSTBuiltinFeatureAccess().getTHISEnumLiteralDeclaration());
              		
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
    // $ANTLR end "ruleSTBuiltinFeature"


    // $ANTLR start "ruleSTMultiBitAccessSpecifier"
    // InternalSTFunctionParser.g:6413:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSTFunctionParser.g:6419:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalSTFunctionParser.g:6420:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalSTFunctionParser.g:6420:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt115=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt115=1;
                }
                break;
            case D_1:
                {
                alt115=2;
                }
                break;
            case W:
                {
                alt115=3;
                }
                break;
            case B:
                {
                alt115=4;
                }
                break;
            case X:
                {
                alt115=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 115, 0, input);

                throw nvae;
            }

            switch (alt115) {
                case 1 :
                    // InternalSTFunctionParser.g:6421:3: (enumLiteral_0= L )
                    {
                    // InternalSTFunctionParser.g:6421:3: (enumLiteral_0= L )
                    // InternalSTFunctionParser.g:6422:4: enumLiteral_0= L
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
                    // InternalSTFunctionParser.g:6429:3: (enumLiteral_1= D_1 )
                    {
                    // InternalSTFunctionParser.g:6429:3: (enumLiteral_1= D_1 )
                    // InternalSTFunctionParser.g:6430:4: enumLiteral_1= D_1
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
                    // InternalSTFunctionParser.g:6437:3: (enumLiteral_2= W )
                    {
                    // InternalSTFunctionParser.g:6437:3: (enumLiteral_2= W )
                    // InternalSTFunctionParser.g:6438:4: enumLiteral_2= W
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
                    // InternalSTFunctionParser.g:6445:3: (enumLiteral_3= B )
                    {
                    // InternalSTFunctionParser.g:6445:3: (enumLiteral_3= B )
                    // InternalSTFunctionParser.g:6446:4: enumLiteral_3= B
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
                    // InternalSTFunctionParser.g:6453:3: (enumLiteral_4= X )
                    {
                    // InternalSTFunctionParser.g:6453:3: (enumLiteral_4= X )
                    // InternalSTFunctionParser.g:6454:4: enumLiteral_4= X
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
        // InternalSTFunctionParser.g:2635:4: ( ( ruleSTStatement ) )
        // InternalSTFunctionParser.g:2635:5: ( ruleSTStatement )
        {
        // InternalSTFunctionParser.g:2635:5: ( ruleSTStatement )
        // InternalSTFunctionParser.g:2636:5: ruleSTStatement
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
        // InternalSTFunctionParser.g:3731:4: ( ruleSTSignedLiteralExpressions )
        // InternalSTFunctionParser.g:3731:5: ruleSTSignedLiteralExpressions
        {
        pushFollow(FOLLOW_2);
        ruleSTSignedLiteralExpressions();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalSTFunctionParser

    // Delegated rules

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


    protected DFA33 dfa33 = new DFA33(this);
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA52 dfa52 = new DFA52(this);
    protected DFA67 dfa67 = new DFA67(this);
    protected DFA84 dfa84 = new DFA84(this);
    static final String dfa_1s = "\24\uffff";
    static final String dfa_2s = "\2\uffff\1\3\17\uffff\1\3\1\uffff";
    static final String dfa_3s = "\2\10\1\165\2\uffff\11\165\1\u00b9\1\u0099\1\u00b9\1\uffff\2\165";
    static final String dfa_4s = "\2\u00ba\1\u00ab\2\uffff\11\u00a8\3\u00b9\1\uffff\1\u00ab\1\u00a8";
    static final String dfa_5s = "\3\uffff\1\1\1\2\14\uffff\1\3\2\uffff";
    static final String dfa_6s = "\24\uffff}>";
    static final String[] dfa_7s = {
            "\1\3\1\uffff\1\3\5\uffff\1\3\2\uffff\1\3\51\uffff\1\3\12\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff\4\3\1\uffff\2\3\2\uffff\2\3\1\uffff\2\3\1\uffff\3\3\3\uffff\2\3\1\uffff\2\3\3\uffff\3\3\1\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\4\3\1\uffff\1\3\1\uffff\1\3\20\uffff\1\3\1\uffff\2\3\2\uffff\1\3\3\uffff\1\1\2\uffff\1\3\1\uffff\1\3\7\uffff\2\3\1\4\4\uffff\2\3\12\uffff\1\2\1\3",
            "\1\3\1\uffff\1\3\5\uffff\1\3\2\uffff\1\3\51\uffff\1\3\12\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff\4\3\1\uffff\2\3\2\uffff\2\3\1\uffff\2\3\1\uffff\3\3\3\uffff\2\3\1\uffff\2\3\3\uffff\3\3\1\uffff\1\3\1\uffff\1\3\1\uffff\1\7\1\uffff\2\3\1\12\1\3\1\uffff\1\3\1\uffff\1\11\20\uffff\1\14\1\uffff\1\15\1\6\2\uffff\1\10\3\uffff\1\3\2\uffff\1\3\1\uffff\1\3\7\uffff\1\13\1\3\5\uffff\2\3\12\uffff\1\5\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\16\1\uffff\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\1\17\11\3\1\uffff\4\3\2\uffff\4\3",
            "",
            "",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\20\1\21\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\21\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\21\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\21\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\21\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\21\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\21\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\21\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\21\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\22",
            "\1\21\37\uffff\1\3",
            "\1\23",
            "",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\16\1\uffff\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\1\17\11\3\1\uffff\4\3\2\uffff\4\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\20\1\21\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA33 extends DFA {

        public DFA33(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 33;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "1298:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )";
        }
    }
    static final String dfa_8s = "\12\uffff";
    static final String dfa_9s = "\1\10\11\uffff";
    static final String dfa_10s = "\1\u00ba\11\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
    static final String dfa_12s = "\12\uffff}>";
    static final String[] dfa_13s = {
            "\1\6\1\uffff\1\6\5\uffff\1\6\2\uffff\1\6\26\uffff\1\10\22\uffff\1\6\6\uffff\1\5\1\uffff\1\7\1\uffff\1\6\3\uffff\1\6\1\uffff\1\6\1\uffff\4\6\1\uffff\2\6\2\uffff\2\6\1\4\2\6\1\2\3\6\1\uffff\1\11\1\uffff\2\6\1\uffff\2\6\3\uffff\3\6\1\uffff\1\6\1\uffff\1\6\1\uffff\1\6\1\3\4\6\1\uffff\1\6\1\uffff\1\6\20\uffff\1\6\1\1\2\6\2\uffff\1\6\3\uffff\1\6\2\uffff\1\6\1\uffff\1\6\7\uffff\2\6\5\uffff\2\6\12\uffff\2\6",
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
            return "1927:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )";
        }
    }
    static final String dfa_14s = "\77\uffff";
    static final String dfa_15s = "\1\1\76\uffff";
    static final String dfa_16s = "\1\10\2\uffff\62\0\12\uffff";
    static final String dfa_17s = "\1\u00ba\2\uffff\62\0\12\uffff";
    static final String dfa_18s = "\1\uffff\1\2\64\uffff\11\1";
    static final String dfa_19s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\12\uffff}>";
    static final String[] dfa_20s = {
            "\1\53\1\uffff\1\52\5\uffff\1\47\2\uffff\1\46\26\uffff\1\74\1\1\21\uffff\1\56\6\uffff\1\72\1\uffff\1\73\1\uffff\1\55\3\uffff\1\21\1\uffff\1\36\1\uffff\1\42\1\34\1\44\1\22\1\uffff\1\31\1\32\2\uffff\1\27\1\60\1\71\1\16\1\17\1\67\1\57\1\41\1\25\1\1\1\75\1\uffff\1\26\1\51\1\uffff\1\33\1\23\3\uffff\1\15\1\43\1\35\1\uffff\1\30\1\uffff\1\20\1\uffff\1\6\1\70\1\24\1\54\1\11\1\64\1\uffff\1\50\1\uffff\1\10\20\uffff\1\13\1\66\1\14\1\5\2\uffff\1\7\3\uffff\1\3\2\uffff\1\62\1\uffff\1\63\3\uffff\1\76\3\uffff\1\12\1\45\5\uffff\1\40\1\37\12\uffff\1\4\1\61",
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

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA52 extends DFA {

        public DFA52(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "()* loopback of 2634:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA52_0 = input.LA(1);

                         
                        int index52_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA52_0==EOF||LA52_0==END_CASE||LA52_0==ELSE) ) {s = 1;}

                        else if ( (LA52_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA52_0==RULE_ID) ) {s = 4;}

                        else if ( (LA52_0==LT) ) {s = 5;}

                        else if ( (LA52_0==AND) ) {s = 6;}

                        else if ( (LA52_0==OR) ) {s = 7;}

                        else if ( (LA52_0==XOR) ) {s = 8;}

                        else if ( (LA52_0==MOD) ) {s = 9;}

                        else if ( (LA52_0==D) ) {s = 10;}

                        else if ( (LA52_0==DT) ) {s = 11;}

                        else if ( (LA52_0==LD) ) {s = 12;}

                        else if ( (LA52_0==THIS) ) {s = 13;}

                        else if ( (LA52_0==BOOL) ) {s = 14;}

                        else if ( (LA52_0==BYTE) ) {s = 15;}

                        else if ( (LA52_0==WORD) ) {s = 16;}

                        else if ( (LA52_0==DWORD) ) {s = 17;}

                        else if ( (LA52_0==LWORD) ) {s = 18;}

                        else if ( (LA52_0==SINT) ) {s = 19;}

                        else if ( (LA52_0==INT) ) {s = 20;}

                        else if ( (LA52_0==DINT) ) {s = 21;}

                        else if ( (LA52_0==LINT) ) {s = 22;}

                        else if ( (LA52_0==USINT) ) {s = 23;}

                        else if ( (LA52_0==UINT) ) {s = 24;}

                        else if ( (LA52_0==UDINT) ) {s = 25;}

                        else if ( (LA52_0==ULINT) ) {s = 26;}

                        else if ( (LA52_0==REAL) ) {s = 27;}

                        else if ( (LA52_0==LREAL) ) {s = 28;}

                        else if ( (LA52_0==TRUE) ) {s = 29;}

                        else if ( (LA52_0==FALSE) ) {s = 30;}

                        else if ( (LA52_0==RULE_INT) ) {s = 31;}

                        else if ( (LA52_0==RULE_NON_DECIMAL) ) {s = 32;}

                        else if ( (LA52_0==DATE) ) {s = 33;}

                        else if ( (LA52_0==LDATE) ) {s = 34;}

                        else if ( (LA52_0==TIME) ) {s = 35;}

                        else if ( (LA52_0==LTIME) ) {s = 36;}

                        else if ( (LA52_0==T) ) {s = 37;}

                        else if ( (LA52_0==TIME_OF_DAY) ) {s = 38;}

                        else if ( (LA52_0==LTIME_OF_DAY) ) {s = 39;}

                        else if ( (LA52_0==TOD) ) {s = 40;}

                        else if ( (LA52_0==LTOD) ) {s = 41;}

                        else if ( (LA52_0==DATE_AND_TIME) ) {s = 42;}

                        else if ( (LA52_0==LDATE_AND_TIME) ) {s = 43;}

                        else if ( (LA52_0==LDT) ) {s = 44;}

                        else if ( (LA52_0==STRING) ) {s = 45;}

                        else if ( (LA52_0==WSTRING) ) {s = 46;}

                        else if ( (LA52_0==CHAR) ) {s = 47;}

                        else if ( (LA52_0==WCHAR) ) {s = 48;}

                        else if ( (LA52_0==RULE_STRING) ) {s = 49;}

                        else if ( (LA52_0==PlusSign) ) {s = 50;}

                        else if ( (LA52_0==HyphenMinus) ) {s = 51;}

                        else if ( (LA52_0==NOT) ) {s = 52;}

                        else if ( (LA52_0==IF) && (synpred1_InternalSTFunctionParser())) {s = 54;}

                        else if ( (LA52_0==CASE) && (synpred1_InternalSTFunctionParser())) {s = 55;}

                        else if ( (LA52_0==FOR) && (synpred1_InternalSTFunctionParser())) {s = 56;}

                        else if ( (LA52_0==WHILE) && (synpred1_InternalSTFunctionParser())) {s = 57;}

                        else if ( (LA52_0==REPEAT) && (synpred1_InternalSTFunctionParser())) {s = 58;}

                        else if ( (LA52_0==RETURN) && (synpred1_InternalSTFunctionParser())) {s = 59;}

                        else if ( (LA52_0==CONTINUE) && (synpred1_InternalSTFunctionParser())) {s = 60;}

                        else if ( (LA52_0==EXIT) && (synpred1_InternalSTFunctionParser())) {s = 61;}

                        else if ( (LA52_0==Semicolon) && (synpred1_InternalSTFunctionParser())) {s = 62;}

                         
                        input.seek(index52_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA52_3 = input.LA(1);

                         
                        int index52_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA52_4 = input.LA(1);

                         
                        int index52_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA52_5 = input.LA(1);

                         
                        int index52_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA52_6 = input.LA(1);

                         
                        int index52_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA52_7 = input.LA(1);

                         
                        int index52_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA52_8 = input.LA(1);

                         
                        int index52_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA52_9 = input.LA(1);

                         
                        int index52_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA52_10 = input.LA(1);

                         
                        int index52_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA52_11 = input.LA(1);

                         
                        int index52_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA52_12 = input.LA(1);

                         
                        int index52_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA52_13 = input.LA(1);

                         
                        int index52_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA52_14 = input.LA(1);

                         
                        int index52_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA52_15 = input.LA(1);

                         
                        int index52_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA52_16 = input.LA(1);

                         
                        int index52_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA52_17 = input.LA(1);

                         
                        int index52_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA52_18 = input.LA(1);

                         
                        int index52_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA52_19 = input.LA(1);

                         
                        int index52_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA52_20 = input.LA(1);

                         
                        int index52_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA52_21 = input.LA(1);

                         
                        int index52_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA52_22 = input.LA(1);

                         
                        int index52_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA52_23 = input.LA(1);

                         
                        int index52_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA52_24 = input.LA(1);

                         
                        int index52_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA52_25 = input.LA(1);

                         
                        int index52_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA52_26 = input.LA(1);

                         
                        int index52_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA52_27 = input.LA(1);

                         
                        int index52_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA52_28 = input.LA(1);

                         
                        int index52_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA52_29 = input.LA(1);

                         
                        int index52_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA52_30 = input.LA(1);

                         
                        int index52_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA52_31 = input.LA(1);

                         
                        int index52_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA52_32 = input.LA(1);

                         
                        int index52_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA52_33 = input.LA(1);

                         
                        int index52_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA52_34 = input.LA(1);

                         
                        int index52_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA52_35 = input.LA(1);

                         
                        int index52_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA52_36 = input.LA(1);

                         
                        int index52_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA52_37 = input.LA(1);

                         
                        int index52_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA52_38 = input.LA(1);

                         
                        int index52_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA52_39 = input.LA(1);

                         
                        int index52_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA52_40 = input.LA(1);

                         
                        int index52_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA52_41 = input.LA(1);

                         
                        int index52_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA52_42 = input.LA(1);

                         
                        int index52_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA52_43 = input.LA(1);

                         
                        int index52_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA52_44 = input.LA(1);

                         
                        int index52_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA52_45 = input.LA(1);

                         
                        int index52_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA52_46 = input.LA(1);

                         
                        int index52_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_46);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA52_47 = input.LA(1);

                         
                        int index52_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_47);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA52_48 = input.LA(1);

                         
                        int index52_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_48);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA52_49 = input.LA(1);

                         
                        int index52_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_49);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA52_50 = input.LA(1);

                         
                        int index52_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_50);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA52_51 = input.LA(1);

                         
                        int index52_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_51);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA52_52 = input.LA(1);

                         
                        int index52_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSTFunctionParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index52_52);
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
    static final String dfa_21s = "\17\uffff";
    static final String dfa_22s = "\2\uffff\5\1\6\uffff\1\1\1\uffff";
    static final String dfa_23s = "\1\10\1\uffff\5\26\1\uffff\2\10\1\uffff\1\u00b9\1\0\1\26\1\uffff";
    static final String dfa_24s = "\1\u00ba\1\uffff\5\u00ab\1\uffff\2\u00ba\1\uffff\1\u00b9\1\0\1\u00ab\1\uffff";
    static final String dfa_25s = "\1\uffff\1\1\5\uffff\1\2\2\uffff\1\4\3\uffff\1\3";
    static final String dfa_26s = "\14\uffff\1\0\2\uffff}>";
    static final String[] dfa_27s = {
            "\1\7\1\uffff\1\7\5\uffff\1\7\2\uffff\1\7\51\uffff\1\7\12\uffff\1\7\3\uffff\1\7\1\uffff\1\7\1\uffff\4\7\1\uffff\2\7\2\uffff\2\7\1\uffff\2\7\1\uffff\3\7\3\uffff\2\7\1\uffff\2\7\3\uffff\1\1\2\7\1\uffff\1\7\1\uffff\1\7\1\uffff\1\1\1\uffff\2\7\1\1\1\12\1\uffff\1\7\1\uffff\1\1\20\uffff\1\5\1\uffff\1\6\1\3\2\uffff\1\1\3\uffff\1\1\2\uffff\1\10\1\uffff\1\11\7\uffff\1\4\1\7\5\uffff\2\7\12\uffff\1\2\1\7",
            "",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\13\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\7\16\1\2\uffff\4\1",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\uffff\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\7\16\1\2\uffff\4\1",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\uffff\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\7\16\1\2\uffff\4\1",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\uffff\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\7\16\1\2\uffff\4\1",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\uffff\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\7\16\1\2\uffff\4\1",
            "",
            "\1\12\1\uffff\1\12\5\uffff\1\12\2\uffff\1\12\51\uffff\1\12\12\uffff\1\12\3\uffff\1\12\1\uffff\1\12\1\uffff\4\12\1\uffff\2\12\2\uffff\2\12\1\uffff\2\12\1\uffff\3\12\3\uffff\2\12\1\uffff\2\12\3\uffff\3\12\1\uffff\1\12\1\uffff\1\12\1\uffff\1\12\1\uffff\4\12\1\uffff\1\12\1\uffff\1\12\20\uffff\1\12\1\uffff\2\12\2\uffff\1\12\3\uffff\1\12\2\uffff\1\12\1\uffff\1\12\7\uffff\2\12\5\uffff\1\12\1\14\12\uffff\2\12",
            "\1\12\1\uffff\1\12\5\uffff\1\12\2\uffff\1\12\51\uffff\1\12\12\uffff\1\12\3\uffff\1\12\1\uffff\1\12\1\uffff\4\12\1\uffff\2\12\2\uffff\2\12\1\uffff\2\12\1\uffff\3\12\3\uffff\2\12\1\uffff\2\12\3\uffff\3\12\1\uffff\1\12\1\uffff\1\12\1\uffff\1\12\1\uffff\4\12\1\uffff\1\12\1\uffff\1\12\20\uffff\1\12\1\uffff\2\12\2\uffff\1\12\3\uffff\1\12\2\uffff\1\12\1\uffff\1\12\7\uffff\2\12\5\uffff\1\12\1\14\12\uffff\2\12",
            "",
            "\1\15",
            "\1\uffff",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\13\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\7\16\1\2\uffff\4\1",
            ""
    };

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA67 extends DFA {

        public DFA67(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 67;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "3711:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA67_12 = input.LA(1);

                         
                        int index67_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSTFunctionParser()) ) {s = 14;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index67_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 67, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_28s = "\23\uffff";
    static final String dfa_29s = "\1\114\17\u0097\1\uffff\1\116\1\uffff";
    static final String dfa_30s = "\1\u00ae\17\u0097\1\uffff\1\u00ae\1\uffff";
    static final String dfa_31s = "\20\uffff\1\2\1\uffff\1\1";
    static final String dfa_32s = "\23\uffff}>";
    static final String[] dfa_33s = {
            "\1\4\1\uffff\1\20\2\uffff\1\17\1\uffff\1\5\1\uffff\1\14\1\15\2\uffff\1\12\2\uffff\1\1\1\2\3\uffff\1\10\3\uffff\1\11\2\uffff\1\16\1\6\5\uffff\1\20\1\uffff\1\13\1\uffff\1\3\3\uffff\1\7\65\uffff\2\20",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "\1\21",
            "",
            "\1\20\40\uffff\1\20\54\uffff\1\22\1\uffff\1\22\16\uffff\2\20",
            ""
    };

    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[][] dfa_33 = unpackEncodedStringArray(dfa_33s);

    class DFA84 extends DFA {

        public DFA84(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 84;
            this.eot = dfa_28;
            this.eof = dfa_28;
            this.min = dfa_29;
            this.max = dfa_30;
            this.accept = dfa_31;
            this.special = dfa_32;
            this.transition = dfa_33;
        }
        public String getDescription() {
            return "4540:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x2010048180092500L,0x77EAE36BFE6F5150L,0x060060C652278000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x2000000000090500L,0x118A4363B66F1100L,0x0200000000008000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x2010048180092500L,0x77EAE36BFE6F5150L,0x060060C452278000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x2000040000092500L,0x57EAE36BFE6F5150L,0x060060C452278000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0040020000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0040000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200001000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x2000000000090500L,0x118A4363B66F1500L,0x0200000000008000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5100L,0x060060C052268000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020020000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000050400000080L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040400000080L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5100L,0x060061C052268000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040400000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x4220000000000000L,0x0200004000268000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000080020000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0xA000040000090500L,0x57EAE36FFE6F7150L,0x060060C452278000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x8000000000000000L,0x0000000400002000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x2000040000090502L,0x57EAE36BFE6F5150L,0x060060C452278000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x2000080000090500L,0x57AAE367B66F5100L,0x060060C052268000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000220000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x2020040000090500L,0x57EAE36BFE6F5150L,0x060060C452278000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x2000040400090500L,0x57EAE36BFE6F5150L,0x060060C452278000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x2000040000090500L,0x57EAE36BFEEF5150L,0x060060C452278000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0020000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000200L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000002800000900L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000050000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000002L,0x0200000000000000L,0x0000000108000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000010080000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0xC220000000000000L,0x020040400226800FL});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5100L,0x060060C056268000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400002000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000050000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x008A8322326A5000L,0x0000600000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000050000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000002L,0x0010000000000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000C00000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});

}