package org.eclipse.fordiac.ide.globalconstantseditor.parser.antlr.internal;

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
import org.eclipse.fordiac.ide.globalconstantseditor.services.GlobalConstantsGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
@SuppressWarnings("all")
public class InternalGlobalConstantsParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "END_GLOBALCONSTANTS", "END_FUNCTION_BLOCK", "END_CONFIGURATION", "GLOBALCONSTANTS", "END_TRANSITION", "FUNCTION_BLOCK", "LDATE_AND_TIME", "CONFIGURATION", "DATE_AND_TIME", "END_INTERFACE", "END_NAMESPACE", "END_FUNCTION", "END_RESOURCE", "INITIAL_STEP", "LTIME_OF_DAY", "VAR_EXTERNAL", "END_PROGRAM", "TIME_OF_DAY", "END_ACTION", "END_METHOD", "END_REPEAT", "END_STRUCT", "IMPLEMENTS", "NON_RETAIN", "READ_WRITE", "TRANSITION", "VAR_ACCESS", "VAR_CONFIG", "VAR_GLOBAL", "VAR_IN_OUT", "VAR_OUTPUT", "END_CLASS", "END_WHILE", "INTERFACE", "NAMESPACE", "PROTECTED", "READ_ONLY", "VAR_INPUT", "ABSTRACT", "CONSTANT", "CONTINUE", "END_CASE", "END_STEP", "END_TYPE", "FUNCTION", "INTERNAL", "INTERVAL", "OVERRIDE", "PRIORITY", "RESOURCE", "VAR_TEMP", "END_FOR", "END_VAR", "EXTENDS", "INTERAL", "OVERLAP", "PACKAGE", "PRIVATE", "PROGRAM", "WSTRING", "ACTION", "END_IF", "IMPORT", "METHOD", "PUBLIC", "REF_TO", "REPEAT", "RETAIN", "RETURN", "SINGLE", "STRING", "STRUCT", "ARRAY", "CLASS", "DWORD", "ELSIF", "FALSE", "FINAL", "LDATE", "LREAL", "LTIME", "LWORD", "SUPER", "UDINT", "ULINT", "UNTIL", "USING", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "FROM", "LINT", "LTOD", "NULL", "REAL", "SINT", "STEP", "TASK", "THEN", "THIS", "TIME", "TRUE", "TYPE", "UINT", "WITH", "WORD", "ColonColonAsterisk", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "REF", "TOD", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonColon", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "OF", "ON", "OR", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "T", "LeftSquareBracket", "RightSquareBracket", "LeftCurlyBracket", "RightCurlyBracket", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_DECIMAL", "RULE_TIME_PART", "RULE_TIME_VALUE", "RULE_TIME_DAYS", "RULE_TIME_HOURS", "RULE_TIME_MINUTES", "RULE_TIME_SECONDS", "RULE_TIME_MILLIS", "RULE_TIME_MICROS", "RULE_TIME_NANOS", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int EqualsSignGreaterThanSign=140;
    public static final int LessThanSign=165;
    public static final int RULE_TIME_HOURS=181;
    public static final int INTERNAL=49;
    public static final int REF_TO=69;
    public static final int LINT=103;
    public static final int GreaterThanSign=167;
    public static final int RULE_ID=187;
    public static final int CONFIGURATION=11;
    public static final int UDINT=87;
    public static final int GreaterThanSignEqualsSign=141;
    public static final int ColonColon=136;
    public static final int AT=142;
    public static final int RULE_INT=176;
    public static final int END_FOR=55;
    public static final int THEN=110;
    public static final int XOR=128;
    public static final int PROGRAM=62;
    public static final int TIME_OF_DAY=21;
    public static final int B=129;
    public static final int LDATE_AND_TIME=10;
    public static final int REPEAT=70;
    public static final int D=168;
    public static final int L=131;
    public static final int T=169;
    public static final int GLOBALCONSTANTS=7;
    public static final int W=132;
    public static final int BY=143;
    public static final int X=133;
    public static final int END_REPEAT=24;
    public static final int Solidus=162;
    public static final int RightCurlyBracket=173;
    public static final int PROTECTED=39;
    public static final int RESOURCE=53;
    public static final int INTERVAL=50;
    public static final int FullStop=161;
    public static final int RULE_TIME_SECONDS=183;
    public static final int INTERAL=58;
    public static final int RULE_TIME_VALUE=179;
    public static final int CONTINUE=44;
    public static final int Semicolon=164;
    public static final int REF=125;
    public static final int VAR_OUTPUT=34;
    public static final int STRING=74;
    public static final int TO=152;
    public static final int INITIAL_STEP=17;
    public static final int EXTENDS=57;
    public static final int PRIORITY=52;
    public static final int CLASS=77;
    public static final int DO=144;
    public static final int END_CONFIGURATION=6;
    public static final int DT=145;
    public static final int END_VAR=56;
    public static final int END_STEP=46;
    public static final int RULE_TIME_PART=178;
    public static final int PACKAGE=60;
    public static final int FullStopFullStop=135;
    public static final int Ampersand=154;
    public static final int END_NAMESPACE=14;
    public static final int END_ACTION=22;
    public static final int USINT=91;
    public static final int DWORD=78;
    public static final int FOR=120;
    public static final int RightParenthesis=156;
    public static final int FINAL=81;
    public static final int END_FUNCTION=15;
    public static final int USING=90;
    public static final int RULE_DECIMAL=177;
    public static final int NOT=124;
    public static final int AsteriskAsterisk=134;
    public static final int THIS=111;
    public static final int SINT=107;
    public static final int VAR_GLOBAL=32;
    public static final int WCHAR=92;
    public static final int VAR_EXTERNAL=19;
    public static final int RULE_SL_COMMENT=190;
    public static final int RETURN=72;
    public static final int ColonColonAsterisk=118;
    public static final int END_RESOURCE=16;
    public static final int Colon=163;
    public static final int EOF=-1;
    public static final int Asterisk=157;
    public static final int RULE_TIME_MILLIS=184;
    public static final int MOD=123;
    public static final int RETAIN=71;
    public static final int LeftCurlyBracket=172;
    public static final int STEP=108;
    public static final int TIME=112;
    public static final int WITH=116;
    public static final int RULE_TIME_MINUTES=182;
    public static final int END_CLASS=35;
    public static final int ACTION=64;
    public static final int BOOL=94;
    public static final int D_1=130;
    public static final int FALSE=80;
    public static final int RULE_TIME_MICROS=185;
    public static final int LWORD=85;
    public static final int LessThanSignGreaterThanSign=139;
    public static final int FUNCTION_BLOCK=9;
    public static final int VAR=127;
    public static final int END_IF=65;
    public static final int ULINT=88;
    public static final int END_CASE=45;
    public static final int LeftParenthesis=155;
    public static final int INTERFACE=37;
    public static final int VAR_CONFIG=31;
    public static final int BYTE=95;
    public static final int ELSE=100;
    public static final int TYPE=114;
    public static final int IF=146;
    public static final int WORD=117;
    public static final int DATE_AND_TIME=12;
    public static final int TOD=126;
    public static final int DINT=99;
    public static final int FUNCTION=48;
    public static final int RULE_TIME_NANOS=186;
    public static final int CASE=96;
    public static final int END_GLOBALCONSTANTS=4;
    public static final int PlusSign=158;
    public static final int RULE_TIME_DAYS=180;
    public static final int RULE_ML_COMMENT=189;
    public static final int PUBLIC=68;
    public static final int LeftSquareBracket=170;
    public static final int EXIT=101;
    public static final int CHAR=97;
    public static final int LTIME=84;
    public static final int IMPORT=66;
    public static final int Comma=159;
    public static final int HyphenMinus=160;
    public static final int ELSIF=79;
    public static final int LessThanSignEqualsSign=138;
    public static final int VAR_INPUT=41;
    public static final int VAR_TEMP=54;
    public static final int CONSTANT=43;
    public static final int PRIVATE=61;
    public static final int TRANSITION=29;
    public static final int LD=147;
    public static final int RULE_HEX_DIGIT=174;
    public static final int END_TYPE=47;
    public static final int UINT=115;
    public static final int LTOD=104;
    public static final int NAMESPACE=38;
    public static final int SINGLE=73;
    public static final int ARRAY=76;
    public static final int LT=148;
    public static final int FROM=102;
    public static final int WSTRING=63;
    public static final int READ_WRITE=28;
    public static final int END_STRUCT=25;
    public static final int OVERLAP=59;
    public static final int RightSquareBracket=171;
    public static final int TASK=109;
    public static final int NULL=105;
    public static final int TRUE=113;
    public static final int ColonEqualsSign=137;
    public static final int END_WHILE=36;
    public static final int DATE=98;
    public static final int LDATE=82;
    public static final int AND=119;
    public static final int NumberSign=153;
    public static final int REAL=106;
    public static final int METHOD=67;
    public static final int NON_RETAIN=27;
    public static final int STRUCT=75;
    public static final int LTIME_OF_DAY=18;
    public static final int END_TRANSITION=8;
    public static final int LREAL=83;
    public static final int END_FUNCTION_BLOCK=5;
    public static final int RULE_STRING=188;
    public static final int VAR_ACCESS=30;
    public static final int ABSTRACT=42;
    public static final int READ_ONLY=40;
    public static final int INT=121;
    public static final int EqualsSign=166;
    public static final int OF=149;
    public static final int END_METHOD=23;
    public static final int LDT=122;
    public static final int ON=150;
    public static final int SUPER=86;
    public static final int OR=151;
    public static final int RULE_WS=191;
    public static final int VAR_IN_OUT=33;
    public static final int END_INTERFACE=13;
    public static final int IMPLEMENTS=26;
    public static final int RULE_ANY_OTHER=192;
    public static final int UNTIL=89;
    public static final int OVERRIDE=51;
    public static final int WHILE=93;
    public static final int END_PROGRAM=20;
    public static final int RULE_NON_DECIMAL=175;

    // delegates
    // delegators


        public InternalGlobalConstantsParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalGlobalConstantsParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalGlobalConstantsParser.tokenNames; }
    public String getGrammarFileName() { return "InternalGlobalConstantsParser.g"; }



     	private GlobalConstantsGrammarAccess grammarAccess;

        public InternalGlobalConstantsParser(TokenStream input, GlobalConstantsGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "STGlobalConstsSource";
       	}

       	@Override
       	protected GlobalConstantsGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleSTGlobalConstsSource"
    // InternalGlobalConstantsParser.g:69:1: entryRuleSTGlobalConstsSource returns [EObject current=null] : iv_ruleSTGlobalConstsSource= ruleSTGlobalConstsSource EOF ;
    public final EObject entryRuleSTGlobalConstsSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTGlobalConstsSource = null;


        try {
            // InternalGlobalConstantsParser.g:69:61: (iv_ruleSTGlobalConstsSource= ruleSTGlobalConstsSource EOF )
            // InternalGlobalConstantsParser.g:70:2: iv_ruleSTGlobalConstsSource= ruleSTGlobalConstsSource EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTGlobalConstsSourceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTGlobalConstsSource=ruleSTGlobalConstsSource();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTGlobalConstsSource; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTGlobalConstsSource"


    // $ANTLR start "ruleSTGlobalConstsSource"
    // InternalGlobalConstantsParser.g:76:1: ruleSTGlobalConstsSource returns [EObject current=null] : ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_constants_5_0= ruleSTGlobalConstants ) )? ) ;
    public final EObject ruleSTGlobalConstsSource() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_imports_4_0 = null;

        EObject lv_constants_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:82:2: ( ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_constants_5_0= ruleSTGlobalConstants ) )? ) )
            // InternalGlobalConstantsParser.g:83:2: ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_constants_5_0= ruleSTGlobalConstants ) )? )
            {
            // InternalGlobalConstantsParser.g:83:2: ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_constants_5_0= ruleSTGlobalConstants ) )? )
            // InternalGlobalConstantsParser.g:84:3: () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_constants_5_0= ruleSTGlobalConstants ) )?
            {
            // InternalGlobalConstantsParser.g:84:3: ()
            // InternalGlobalConstantsParser.g:85:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTGlobalConstsSourceAccess().getSTGlobalConstsSourceAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:91:3: (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==PACKAGE) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalGlobalConstantsParser.g:92:4: otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon
                    {
                    otherlv_1=(Token)match(input,PACKAGE,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTGlobalConstsSourceAccess().getPACKAGEKeyword_1_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:96:4: ( (lv_name_2_0= ruleQualifiedName ) )
                    // InternalGlobalConstantsParser.g:97:5: (lv_name_2_0= ruleQualifiedName )
                    {
                    // InternalGlobalConstantsParser.g:97:5: (lv_name_2_0= ruleQualifiedName )
                    // InternalGlobalConstantsParser.g:98:6: lv_name_2_0= ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTGlobalConstsSourceAccess().getNameQualifiedNameParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_4);
                    lv_name_2_0=ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getSTGlobalConstsSourceRule());
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

                      				newLeafNode(otherlv_3, grammarAccess.getSTGlobalConstsSourceAccess().getSemicolonKeyword_1_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:120:3: ( (lv_imports_4_0= ruleSTImport ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==IMPORT) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:121:4: (lv_imports_4_0= ruleSTImport )
            	    {
            	    // InternalGlobalConstantsParser.g:121:4: (lv_imports_4_0= ruleSTImport )
            	    // InternalGlobalConstantsParser.g:122:5: lv_imports_4_0= ruleSTImport
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTGlobalConstsSourceAccess().getImportsSTImportParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_imports_4_0=ruleSTImport();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTGlobalConstsSourceRule());
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

            // InternalGlobalConstantsParser.g:139:3: ( (lv_constants_5_0= ruleSTGlobalConstants ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==GLOBALCONSTANTS) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalGlobalConstantsParser.g:140:4: (lv_constants_5_0= ruleSTGlobalConstants )
                    {
                    // InternalGlobalConstantsParser.g:140:4: (lv_constants_5_0= ruleSTGlobalConstants )
                    // InternalGlobalConstantsParser.g:141:5: lv_constants_5_0= ruleSTGlobalConstants
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTGlobalConstsSourceAccess().getConstantsSTGlobalConstantsParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_constants_5_0=ruleSTGlobalConstants();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getSTGlobalConstsSourceRule());
                      					}
                      					set(
                      						current,
                      						"constants",
                      						lv_constants_5_0,
                      						"org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstants.STGlobalConstants");
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
    // $ANTLR end "ruleSTGlobalConstsSource"


    // $ANTLR start "entryRuleSTGlobalConstants"
    // InternalGlobalConstantsParser.g:162:1: entryRuleSTGlobalConstants returns [EObject current=null] : iv_ruleSTGlobalConstants= ruleSTGlobalConstants EOF ;
    public final EObject entryRuleSTGlobalConstants() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTGlobalConstants = null;


        try {
            // InternalGlobalConstantsParser.g:162:58: (iv_ruleSTGlobalConstants= ruleSTGlobalConstants EOF )
            // InternalGlobalConstantsParser.g:163:2: iv_ruleSTGlobalConstants= ruleSTGlobalConstants EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTGlobalConstantsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTGlobalConstants=ruleSTGlobalConstants();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTGlobalConstants; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTGlobalConstants"


    // $ANTLR start "ruleSTGlobalConstants"
    // InternalGlobalConstantsParser.g:169:1: ruleSTGlobalConstants returns [EObject current=null] : ( () otherlv_1= GLOBALCONSTANTS ( (lv_name_2_0= RULE_ID ) ) ( (lv_elements_3_0= ruleSTVarGlobalDeclarationBlock ) )* otherlv_4= END_GLOBALCONSTANTS ) ;
    public final EObject ruleSTGlobalConstants() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        EObject lv_elements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:175:2: ( ( () otherlv_1= GLOBALCONSTANTS ( (lv_name_2_0= RULE_ID ) ) ( (lv_elements_3_0= ruleSTVarGlobalDeclarationBlock ) )* otherlv_4= END_GLOBALCONSTANTS ) )
            // InternalGlobalConstantsParser.g:176:2: ( () otherlv_1= GLOBALCONSTANTS ( (lv_name_2_0= RULE_ID ) ) ( (lv_elements_3_0= ruleSTVarGlobalDeclarationBlock ) )* otherlv_4= END_GLOBALCONSTANTS )
            {
            // InternalGlobalConstantsParser.g:176:2: ( () otherlv_1= GLOBALCONSTANTS ( (lv_name_2_0= RULE_ID ) ) ( (lv_elements_3_0= ruleSTVarGlobalDeclarationBlock ) )* otherlv_4= END_GLOBALCONSTANTS )
            // InternalGlobalConstantsParser.g:177:3: () otherlv_1= GLOBALCONSTANTS ( (lv_name_2_0= RULE_ID ) ) ( (lv_elements_3_0= ruleSTVarGlobalDeclarationBlock ) )* otherlv_4= END_GLOBALCONSTANTS
            {
            // InternalGlobalConstantsParser.g:177:3: ()
            // InternalGlobalConstantsParser.g:178:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTGlobalConstantsAccess().getSTGlobalConstantsAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,GLOBALCONSTANTS,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTGlobalConstantsAccess().getGLOBALCONSTANTSKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:188:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalGlobalConstantsParser.g:189:4: (lv_name_2_0= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:189:4: (lv_name_2_0= RULE_ID )
            // InternalGlobalConstantsParser.g:190:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_2_0, grammarAccess.getSTGlobalConstantsAccess().getNameIDTerminalRuleCall_2_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTGlobalConstantsRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_2_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:206:3: ( (lv_elements_3_0= ruleSTVarGlobalDeclarationBlock ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==VAR_GLOBAL) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:207:4: (lv_elements_3_0= ruleSTVarGlobalDeclarationBlock )
            	    {
            	    // InternalGlobalConstantsParser.g:207:4: (lv_elements_3_0= ruleSTVarGlobalDeclarationBlock )
            	    // InternalGlobalConstantsParser.g:208:5: lv_elements_3_0= ruleSTVarGlobalDeclarationBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTGlobalConstantsAccess().getElementsSTVarGlobalDeclarationBlockParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_elements_3_0=ruleSTVarGlobalDeclarationBlock();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTGlobalConstantsRule());
            	      					}
            	      					add(
            	      						current,
            	      						"elements",
            	      						lv_elements_3_0,
            	      						"org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstants.STVarGlobalDeclarationBlock");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_GLOBALCONSTANTS,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTGlobalConstantsAccess().getEND_GLOBALCONSTANTSKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTGlobalConstants"


    // $ANTLR start "entryRuleSTVarGlobalDeclarationBlock"
    // InternalGlobalConstantsParser.g:233:1: entryRuleSTVarGlobalDeclarationBlock returns [EObject current=null] : iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF ;
    public final EObject entryRuleSTVarGlobalDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarGlobalDeclarationBlock = null;


        try {
            // InternalGlobalConstantsParser.g:233:68: (iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF )
            // InternalGlobalConstantsParser.g:234:2: iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSTVarGlobalDeclarationBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSTVarGlobalDeclarationBlock=ruleSTVarGlobalDeclarationBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSTVarGlobalDeclarationBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSTVarGlobalDeclarationBlock"


    // $ANTLR start "ruleSTVarGlobalDeclarationBlock"
    // InternalGlobalConstantsParser.g:240:1: ruleSTVarGlobalDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarGlobalDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:246:2: ( ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalGlobalConstantsParser.g:247:2: ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalGlobalConstantsParser.g:247:2: ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalGlobalConstantsParser.g:248:3: () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalGlobalConstantsParser.g:248:3: ()
            // InternalGlobalConstantsParser.g:249:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarGlobalDeclarationBlockAccess().getSTVarGlobalDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_GLOBAL,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarGlobalDeclarationBlockAccess().getVAR_GLOBALKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:259:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==CONSTANT) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalGlobalConstantsParser.g:260:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalGlobalConstantsParser.g:260:4: (lv_constant_2_0= CONSTANT )
                    // InternalGlobalConstantsParser.g:261:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarGlobalDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSTVarGlobalDeclarationBlockRule());
                      					}
                      					setWithLastConsumed(current, "constant", lv_constant_2_0 != null, "CONSTANT");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:273:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==RULE_ID) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:274:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalGlobalConstantsParser.g:274:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalGlobalConstantsParser.g:275:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarGlobalDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
            	    lv_varDeclarations_3_0=ruleSTVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTVarGlobalDeclarationBlockRule());
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
            	    break loop6;
                }
            } while (true);

            otherlv_4=(Token)match(input,END_VAR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarGlobalDeclarationBlockAccess().getEND_VARKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSTVarGlobalDeclarationBlock"


    // $ANTLR start "entryRuleSTExpressionSource"
    // InternalGlobalConstantsParser.g:300:1: entryRuleSTExpressionSource returns [EObject current=null] : iv_ruleSTExpressionSource= ruleSTExpressionSource EOF ;
    public final EObject entryRuleSTExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpressionSource = null;


        try {
            // InternalGlobalConstantsParser.g:300:59: (iv_ruleSTExpressionSource= ruleSTExpressionSource EOF )
            // InternalGlobalConstantsParser.g:301:2: iv_ruleSTExpressionSource= ruleSTExpressionSource EOF
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
    // InternalGlobalConstantsParser.g:307:1: ruleSTExpressionSource returns [EObject current=null] : ( () ( (lv_expression_1_0= ruleSTExpression ) )? ) ;
    public final EObject ruleSTExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_expression_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:313:2: ( ( () ( (lv_expression_1_0= ruleSTExpression ) )? ) )
            // InternalGlobalConstantsParser.g:314:2: ( () ( (lv_expression_1_0= ruleSTExpression ) )? )
            {
            // InternalGlobalConstantsParser.g:314:2: ( () ( (lv_expression_1_0= ruleSTExpression ) )? )
            // InternalGlobalConstantsParser.g:315:3: () ( (lv_expression_1_0= ruleSTExpression ) )?
            {
            // InternalGlobalConstantsParser.g:315:3: ()
            // InternalGlobalConstantsParser.g:316:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTExpressionSourceAccess().getSTExpressionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:322:3: ( (lv_expression_1_0= ruleSTExpression ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LDATE_AND_TIME||LA7_0==DATE_AND_TIME||LA7_0==LTIME_OF_DAY||LA7_0==TIME_OF_DAY||LA7_0==WSTRING||LA7_0==STRING||LA7_0==DWORD||LA7_0==FALSE||(LA7_0>=LDATE && LA7_0<=LWORD)||(LA7_0>=UDINT && LA7_0<=ULINT)||(LA7_0>=USINT && LA7_0<=WCHAR)||(LA7_0>=BOOL && LA7_0<=BYTE)||(LA7_0>=CHAR && LA7_0<=DINT)||(LA7_0>=LINT && LA7_0<=LTOD)||(LA7_0>=REAL && LA7_0<=SINT)||(LA7_0>=THIS && LA7_0<=TRUE)||LA7_0==UINT||LA7_0==WORD||LA7_0==AND||(LA7_0>=INT && LA7_0<=NOT)||LA7_0==TOD||LA7_0==XOR||LA7_0==DT||(LA7_0>=LD && LA7_0<=LT)||LA7_0==OR||LA7_0==LeftParenthesis||LA7_0==PlusSign||LA7_0==HyphenMinus||(LA7_0>=D && LA7_0<=T)||(LA7_0>=RULE_NON_DECIMAL && LA7_0<=RULE_INT)||(LA7_0>=RULE_ID && LA7_0<=RULE_STRING)) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalGlobalConstantsParser.g:323:4: (lv_expression_1_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:323:4: (lv_expression_1_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:324:5: lv_expression_1_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:345:1: entryRuleSTInitializerExpressionSource returns [EObject current=null] : iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF ;
    public final EObject entryRuleSTInitializerExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpressionSource = null;


        try {
            // InternalGlobalConstantsParser.g:345:70: (iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF )
            // InternalGlobalConstantsParser.g:346:2: iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF
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
    // InternalGlobalConstantsParser.g:352:1: ruleSTInitializerExpressionSource returns [EObject current=null] : ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? ) ;
    public final EObject ruleSTInitializerExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_initializerExpression_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:358:2: ( ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? ) )
            // InternalGlobalConstantsParser.g:359:2: ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? )
            {
            // InternalGlobalConstantsParser.g:359:2: ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? )
            // InternalGlobalConstantsParser.g:360:3: () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )?
            {
            // InternalGlobalConstantsParser.g:360:3: ()
            // InternalGlobalConstantsParser.g:361:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTInitializerExpressionSourceAccess().getSTInitializerExpressionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:367:3: ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==LDATE_AND_TIME||LA8_0==DATE_AND_TIME||LA8_0==LTIME_OF_DAY||LA8_0==TIME_OF_DAY||LA8_0==WSTRING||LA8_0==STRING||LA8_0==DWORD||LA8_0==FALSE||(LA8_0>=LDATE && LA8_0<=LWORD)||(LA8_0>=UDINT && LA8_0<=ULINT)||(LA8_0>=USINT && LA8_0<=WCHAR)||(LA8_0>=BOOL && LA8_0<=BYTE)||(LA8_0>=CHAR && LA8_0<=DINT)||(LA8_0>=LINT && LA8_0<=LTOD)||(LA8_0>=REAL && LA8_0<=SINT)||(LA8_0>=THIS && LA8_0<=TRUE)||LA8_0==UINT||LA8_0==WORD||LA8_0==AND||(LA8_0>=INT && LA8_0<=NOT)||LA8_0==TOD||LA8_0==XOR||LA8_0==DT||(LA8_0>=LD && LA8_0<=LT)||LA8_0==OR||LA8_0==LeftParenthesis||LA8_0==PlusSign||LA8_0==HyphenMinus||(LA8_0>=D && LA8_0<=LeftSquareBracket)||(LA8_0>=RULE_NON_DECIMAL && LA8_0<=RULE_INT)||(LA8_0>=RULE_ID && LA8_0<=RULE_STRING)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalGlobalConstantsParser.g:368:4: (lv_initializerExpression_1_0= ruleSTInitializerExpression )
                    {
                    // InternalGlobalConstantsParser.g:368:4: (lv_initializerExpression_1_0= ruleSTInitializerExpression )
                    // InternalGlobalConstantsParser.g:369:5: lv_initializerExpression_1_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:390:1: entryRuleSTImport returns [EObject current=null] : iv_ruleSTImport= ruleSTImport EOF ;
    public final EObject entryRuleSTImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTImport = null;


        try {
            // InternalGlobalConstantsParser.g:390:49: (iv_ruleSTImport= ruleSTImport EOF )
            // InternalGlobalConstantsParser.g:391:2: iv_ruleSTImport= ruleSTImport EOF
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
    // InternalGlobalConstantsParser.g:397:1: ruleSTImport returns [EObject current=null] : (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon ) ;
    public final EObject ruleSTImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_importedNamespace_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:403:2: ( (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon ) )
            // InternalGlobalConstantsParser.g:404:2: (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon )
            {
            // InternalGlobalConstantsParser.g:404:2: (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon )
            // InternalGlobalConstantsParser.g:405:3: otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon
            {
            otherlv_0=(Token)match(input,IMPORT,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTImportAccess().getIMPORTKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:409:3: ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) )
            // InternalGlobalConstantsParser.g:410:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            {
            // InternalGlobalConstantsParser.g:410:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            // InternalGlobalConstantsParser.g:411:5: lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard
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


    // $ANTLR start "entryRuleSTVarDeclaration"
    // InternalGlobalConstantsParser.g:436:1: entryRuleSTVarDeclaration returns [EObject current=null] : iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF ;
    public final EObject entryRuleSTVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclaration = null;


        try {
            // InternalGlobalConstantsParser.g:436:57: (iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF )
            // InternalGlobalConstantsParser.g:437:2: iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF
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
    // InternalGlobalConstantsParser.g:443:1: ruleSTVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon ) ;
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
            // InternalGlobalConstantsParser.g:449:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon ) )
            // InternalGlobalConstantsParser.g:450:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon )
            {
            // InternalGlobalConstantsParser.g:450:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon )
            // InternalGlobalConstantsParser.g:451:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon
            {
            // InternalGlobalConstantsParser.g:451:3: ()
            // InternalGlobalConstantsParser.g:452:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:458:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalGlobalConstantsParser.g:459:4: (lv_name_1_0= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:459:4: (lv_name_1_0= RULE_ID )
            // InternalGlobalConstantsParser.g:460:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_9); if (state.failed) return current;
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

            // InternalGlobalConstantsParser.g:476:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==AT) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalGlobalConstantsParser.g:477:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:481:4: ( (otherlv_3= RULE_ID ) )
                    // InternalGlobalConstantsParser.g:482:5: (otherlv_3= RULE_ID )
                    {
                    // InternalGlobalConstantsParser.g:482:5: (otherlv_3= RULE_ID )
                    // InternalGlobalConstantsParser.g:483:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Colon,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3());
              		
            }
            // InternalGlobalConstantsParser.g:499:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==ARRAY) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalGlobalConstantsParser.g:500:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalGlobalConstantsParser.g:500:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalGlobalConstantsParser.g:501:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalGlobalConstantsParser.g:501:5: (lv_array_5_0= ARRAY )
                    // InternalGlobalConstantsParser.g:502:6: lv_array_5_0= ARRAY
                    {
                    lv_array_5_0=(Token)match(input,ARRAY,FOLLOW_12); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:514:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==LeftSquareBracket) ) {
                        int LA12_1 = input.LA(2);

                        if ( (LA12_1==LDATE_AND_TIME||LA12_1==DATE_AND_TIME||LA12_1==LTIME_OF_DAY||LA12_1==TIME_OF_DAY||LA12_1==WSTRING||LA12_1==STRING||LA12_1==DWORD||LA12_1==FALSE||(LA12_1>=LDATE && LA12_1<=LWORD)||(LA12_1>=UDINT && LA12_1<=ULINT)||(LA12_1>=USINT && LA12_1<=WCHAR)||(LA12_1>=BOOL && LA12_1<=BYTE)||(LA12_1>=CHAR && LA12_1<=DINT)||(LA12_1>=LINT && LA12_1<=LTOD)||(LA12_1>=REAL && LA12_1<=SINT)||(LA12_1>=THIS && LA12_1<=TRUE)||LA12_1==UINT||LA12_1==WORD||LA12_1==AND||(LA12_1>=INT && LA12_1<=NOT)||LA12_1==TOD||LA12_1==XOR||LA12_1==DT||(LA12_1>=LD && LA12_1<=LT)||LA12_1==OR||LA12_1==LeftParenthesis||LA12_1==PlusSign||LA12_1==HyphenMinus||(LA12_1>=D && LA12_1<=T)||(LA12_1>=RULE_NON_DECIMAL && LA12_1<=RULE_INT)||(LA12_1>=RULE_ID && LA12_1<=RULE_STRING)) ) {
                            alt12=1;
                        }
                        else if ( (LA12_1==Asterisk) ) {
                            alt12=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 12, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:515:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:515:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:516:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:520:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalGlobalConstantsParser.g:521:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalGlobalConstantsParser.g:521:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalGlobalConstantsParser.g:522:8: lv_ranges_7_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_14);
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

                            // InternalGlobalConstantsParser.g:539:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop10:
                            do {
                                int alt10=2;
                                int LA10_0 = input.LA(1);

                                if ( (LA10_0==Comma) ) {
                                    alt10=1;
                                }


                                switch (alt10) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:540:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:544:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalGlobalConstantsParser.g:545:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalGlobalConstantsParser.g:545:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalGlobalConstantsParser.g:546:9: lv_ranges_9_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_14);
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
                            	    break loop10;
                                }
                            } while (true);

                            otherlv_10=(Token)match(input,RightSquareBracket,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalGlobalConstantsParser.g:570:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:570:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:571:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:575:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalGlobalConstantsParser.g:576:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalGlobalConstantsParser.g:576:7: (lv_count_12_0= Asterisk )
                            // InternalGlobalConstantsParser.g:577:8: lv_count_12_0= Asterisk
                            {
                            lv_count_12_0=(Token)match(input,Asterisk,FOLLOW_14); if (state.failed) return current;
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

                            // InternalGlobalConstantsParser.g:589:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop11:
                            do {
                                int alt11=2;
                                int LA11_0 = input.LA(1);

                                if ( (LA11_0==Comma) ) {
                                    alt11=1;
                                }


                                switch (alt11) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:590:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_16); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:594:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalGlobalConstantsParser.g:595:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalGlobalConstantsParser.g:595:8: (lv_count_14_0= Asterisk )
                            	    // InternalGlobalConstantsParser.g:596:9: lv_count_14_0= Asterisk
                            	    {
                            	    lv_count_14_0=(Token)match(input,Asterisk,FOLLOW_14); if (state.failed) return current;
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
                            	    break loop11;
                                }
                            } while (true);

                            otherlv_15=(Token)match(input,RightSquareBracket,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_15, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,OF,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:620:3: ( ( ruleSTAnyType ) )
            // InternalGlobalConstantsParser.g:621:4: ( ruleSTAnyType )
            {
            // InternalGlobalConstantsParser.g:621:4: ( ruleSTAnyType )
            // InternalGlobalConstantsParser.g:622:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTVarDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0());
              				
            }
            pushFollow(FOLLOW_17);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:636:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==LeftSquareBracket) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalGlobalConstantsParser.g:637:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:641:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:642:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:642:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:643:6: lv_maxLength_19_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_18);
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

                    otherlv_20=(Token)match(input,RightSquareBracket,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:665:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==ColonEqualsSign) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalGlobalConstantsParser.g:666:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:670:4: ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    // InternalGlobalConstantsParser.g:671:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    {
                    // InternalGlobalConstantsParser.g:671:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    // InternalGlobalConstantsParser.g:672:6: lv_defaultValue_22_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_21);
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

            // InternalGlobalConstantsParser.g:690:3: ( (lv_pragma_23_0= ruleSTPragma ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==LeftCurlyBracket) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalGlobalConstantsParser.g:691:4: (lv_pragma_23_0= ruleSTPragma )
                    {
                    // InternalGlobalConstantsParser.g:691:4: (lv_pragma_23_0= ruleSTPragma )
                    // InternalGlobalConstantsParser.g:692:5: lv_pragma_23_0= ruleSTPragma
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
    // InternalGlobalConstantsParser.g:717:1: entryRuleSTTypeDeclaration returns [EObject current=null] : iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF ;
    public final EObject entryRuleSTTypeDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTypeDeclaration = null;


        try {
            // InternalGlobalConstantsParser.g:717:58: (iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF )
            // InternalGlobalConstantsParser.g:718:2: iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF
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
    // InternalGlobalConstantsParser.g:724:1: ruleSTTypeDeclaration returns [EObject current=null] : ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) ;
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
            // InternalGlobalConstantsParser.g:730:2: ( ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) )
            // InternalGlobalConstantsParser.g:731:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            {
            // InternalGlobalConstantsParser.g:731:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            // InternalGlobalConstantsParser.g:732:3: () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            {
            // InternalGlobalConstantsParser.g:732:3: ()
            // InternalGlobalConstantsParser.g:733:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTTypeDeclarationAccess().getSTTypeDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:739:3: ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==ARRAY) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalGlobalConstantsParser.g:740:4: ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF
                    {
                    // InternalGlobalConstantsParser.g:740:4: ( (lv_array_1_0= ARRAY ) )
                    // InternalGlobalConstantsParser.g:741:5: (lv_array_1_0= ARRAY )
                    {
                    // InternalGlobalConstantsParser.g:741:5: (lv_array_1_0= ARRAY )
                    // InternalGlobalConstantsParser.g:742:6: lv_array_1_0= ARRAY
                    {
                    lv_array_1_0=(Token)match(input,ARRAY,FOLLOW_12); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:754:4: ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) )
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==LeftSquareBracket) ) {
                        int LA19_1 = input.LA(2);

                        if ( (LA19_1==LDATE_AND_TIME||LA19_1==DATE_AND_TIME||LA19_1==LTIME_OF_DAY||LA19_1==TIME_OF_DAY||LA19_1==WSTRING||LA19_1==STRING||LA19_1==DWORD||LA19_1==FALSE||(LA19_1>=LDATE && LA19_1<=LWORD)||(LA19_1>=UDINT && LA19_1<=ULINT)||(LA19_1>=USINT && LA19_1<=WCHAR)||(LA19_1>=BOOL && LA19_1<=BYTE)||(LA19_1>=CHAR && LA19_1<=DINT)||(LA19_1>=LINT && LA19_1<=LTOD)||(LA19_1>=REAL && LA19_1<=SINT)||(LA19_1>=THIS && LA19_1<=TRUE)||LA19_1==UINT||LA19_1==WORD||LA19_1==AND||(LA19_1>=INT && LA19_1<=NOT)||LA19_1==TOD||LA19_1==XOR||LA19_1==DT||(LA19_1>=LD && LA19_1<=LT)||LA19_1==OR||LA19_1==LeftParenthesis||LA19_1==PlusSign||LA19_1==HyphenMinus||(LA19_1>=D && LA19_1<=T)||(LA19_1>=RULE_NON_DECIMAL && LA19_1<=RULE_INT)||(LA19_1>=RULE_ID && LA19_1<=RULE_STRING)) ) {
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
                            // InternalGlobalConstantsParser.g:755:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:755:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:756:6: otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket
                            {
                            otherlv_2=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_0_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:760:6: ( (lv_ranges_3_0= ruleSTExpression ) )
                            // InternalGlobalConstantsParser.g:761:7: (lv_ranges_3_0= ruleSTExpression )
                            {
                            // InternalGlobalConstantsParser.g:761:7: (lv_ranges_3_0= ruleSTExpression )
                            // InternalGlobalConstantsParser.g:762:8: lv_ranges_3_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_14);
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

                            // InternalGlobalConstantsParser.g:779:6: (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )*
                            loop17:
                            do {
                                int alt17=2;
                                int LA17_0 = input.LA(1);

                                if ( (LA17_0==Comma) ) {
                                    alt17=1;
                                }


                                switch (alt17) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:780:7: otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_4, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:784:7: ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    // InternalGlobalConstantsParser.g:785:8: (lv_ranges_5_0= ruleSTExpression )
                            	    {
                            	    // InternalGlobalConstantsParser.g:785:8: (lv_ranges_5_0= ruleSTExpression )
                            	    // InternalGlobalConstantsParser.g:786:9: lv_ranges_5_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_14);
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
                            	    break loop17;
                                }
                            } while (true);

                            otherlv_6=(Token)match(input,RightSquareBracket,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalGlobalConstantsParser.g:810:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:810:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:811:6: otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket
                            {
                            otherlv_7=(Token)match(input,LeftSquareBracket,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_1_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:815:6: ( (lv_count_8_0= Asterisk ) )
                            // InternalGlobalConstantsParser.g:816:7: (lv_count_8_0= Asterisk )
                            {
                            // InternalGlobalConstantsParser.g:816:7: (lv_count_8_0= Asterisk )
                            // InternalGlobalConstantsParser.g:817:8: lv_count_8_0= Asterisk
                            {
                            lv_count_8_0=(Token)match(input,Asterisk,FOLLOW_14); if (state.failed) return current;
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

                            // InternalGlobalConstantsParser.g:829:6: (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )*
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( (LA18_0==Comma) ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:830:7: otherlv_9= Comma ( (lv_count_10_0= Asterisk ) )
                            	    {
                            	    otherlv_9=(Token)match(input,Comma,FOLLOW_16); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_9, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:834:7: ( (lv_count_10_0= Asterisk ) )
                            	    // InternalGlobalConstantsParser.g:835:8: (lv_count_10_0= Asterisk )
                            	    {
                            	    // InternalGlobalConstantsParser.g:835:8: (lv_count_10_0= Asterisk )
                            	    // InternalGlobalConstantsParser.g:836:9: lv_count_10_0= Asterisk
                            	    {
                            	    lv_count_10_0=(Token)match(input,Asterisk,FOLLOW_14); if (state.failed) return current;
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
                            	    break loop18;
                                }
                            } while (true);

                            otherlv_11=(Token)match(input,RightSquareBracket,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_12=(Token)match(input,OF,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getSTTypeDeclarationAccess().getOFKeyword_1_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:860:3: ( ( ruleSTAnyType ) )
            // InternalGlobalConstantsParser.g:861:4: ( ruleSTAnyType )
            {
            // InternalGlobalConstantsParser.g:861:4: ( ruleSTAnyType )
            // InternalGlobalConstantsParser.g:862:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementCrossReference_2_0());
              				
            }
            pushFollow(FOLLOW_22);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:876:3: (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==LeftSquareBracket) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalGlobalConstantsParser.g:877:4: otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket
                    {
                    otherlv_14=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_3_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:881:4: ( (lv_maxLength_15_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:882:5: (lv_maxLength_15_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:882:5: (lv_maxLength_15_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:883:6: lv_maxLength_15_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_18);
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
    // InternalGlobalConstantsParser.g:909:1: entryRuleSTInitializerExpression returns [EObject current=null] : iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF ;
    public final EObject entryRuleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:909:64: (iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:910:2: iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:916:1: ruleSTInitializerExpression returns [EObject current=null] : (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) ;
    public final EObject ruleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STElementaryInitializerExpression_0 = null;

        EObject this_STArrayInitializerExpression_1 = null;

        EObject this_STStructInitializerExpression_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:922:2: ( (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) )
            // InternalGlobalConstantsParser.g:923:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:923:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            int alt22=3;
            alt22 = dfa22.predict(input);
            switch (alt22) {
                case 1 :
                    // InternalGlobalConstantsParser.g:924:3: this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression
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
                    // InternalGlobalConstantsParser.g:933:3: this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression
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
                    // InternalGlobalConstantsParser.g:942:3: this_STStructInitializerExpression_2= ruleSTStructInitializerExpression
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
    // InternalGlobalConstantsParser.g:954:1: entryRuleSTElementaryInitializerExpression returns [EObject current=null] : iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF ;
    public final EObject entryRuleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElementaryInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:954:74: (iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:955:2: iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:961:1: ruleSTElementaryInitializerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:967:2: ( ( (lv_value_0_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:968:2: ( (lv_value_0_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:968:2: ( (lv_value_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:969:3: (lv_value_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:969:3: (lv_value_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:970:4: lv_value_0_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:990:1: entryRuleSTArrayInitializerExpression returns [EObject current=null] : iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF ;
    public final EObject entryRuleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:990:69: (iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:991:2: iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:997:1: ruleSTArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1003:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalGlobalConstantsParser.g:1004:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalGlobalConstantsParser.g:1004:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalGlobalConstantsParser.g:1005:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1009:3: ( (lv_values_1_0= ruleSTArrayInitElement ) )
            // InternalGlobalConstantsParser.g:1010:4: (lv_values_1_0= ruleSTArrayInitElement )
            {
            // InternalGlobalConstantsParser.g:1010:4: (lv_values_1_0= ruleSTArrayInitElement )
            // InternalGlobalConstantsParser.g:1011:5: lv_values_1_0= ruleSTArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_14);
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

            // InternalGlobalConstantsParser.g:1028:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==Comma) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1029:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1033:4: ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    // InternalGlobalConstantsParser.g:1034:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    {
            	    // InternalGlobalConstantsParser.g:1034:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    // InternalGlobalConstantsParser.g:1035:6: lv_values_3_0= ruleSTArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_14);
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
    // InternalGlobalConstantsParser.g:1061:1: entryRuleSTArrayInitElement returns [EObject current=null] : iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF ;
    public final EObject entryRuleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1061:59: (iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF )
            // InternalGlobalConstantsParser.g:1062:2: iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF
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
    // InternalGlobalConstantsParser.g:1068:1: ruleSTArrayInitElement returns [EObject current=null] : (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement ) ;
    public final EObject ruleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject this_STSingleArrayInitElement_0 = null;

        EObject this_STRepeatArrayInitElement_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1074:2: ( (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement ) )
            // InternalGlobalConstantsParser.g:1075:2: (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement )
            {
            // InternalGlobalConstantsParser.g:1075:2: (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==LDATE_AND_TIME||LA24_0==DATE_AND_TIME||LA24_0==LTIME_OF_DAY||LA24_0==TIME_OF_DAY||LA24_0==WSTRING||LA24_0==STRING||LA24_0==DWORD||LA24_0==FALSE||(LA24_0>=LDATE && LA24_0<=LWORD)||(LA24_0>=UDINT && LA24_0<=ULINT)||(LA24_0>=USINT && LA24_0<=WCHAR)||(LA24_0>=BOOL && LA24_0<=BYTE)||(LA24_0>=CHAR && LA24_0<=DINT)||(LA24_0>=LINT && LA24_0<=LTOD)||(LA24_0>=REAL && LA24_0<=SINT)||(LA24_0>=THIS && LA24_0<=TRUE)||LA24_0==UINT||LA24_0==WORD||LA24_0==AND||(LA24_0>=INT && LA24_0<=NOT)||LA24_0==TOD||LA24_0==XOR||LA24_0==DT||(LA24_0>=LD && LA24_0<=LT)||LA24_0==OR||LA24_0==LeftParenthesis||LA24_0==PlusSign||LA24_0==HyphenMinus||(LA24_0>=D && LA24_0<=LeftSquareBracket)||LA24_0==RULE_NON_DECIMAL||(LA24_0>=RULE_ID && LA24_0<=RULE_STRING)) ) {
                alt24=1;
            }
            else if ( (LA24_0==RULE_INT) ) {
                int LA24_2 = input.LA(2);

                if ( (LA24_2==EOF||LA24_2==AND||LA24_2==MOD||LA24_2==XOR||(LA24_2>=AsteriskAsterisk && LA24_2<=FullStopFullStop)||(LA24_2>=LessThanSignEqualsSign && LA24_2<=LessThanSignGreaterThanSign)||LA24_2==GreaterThanSignEqualsSign||LA24_2==OR||LA24_2==Ampersand||(LA24_2>=Asterisk && LA24_2<=Solidus)||(LA24_2>=LessThanSign && LA24_2<=GreaterThanSign)||LA24_2==RightSquareBracket) ) {
                    alt24=1;
                }
                else if ( (LA24_2==LeftParenthesis) ) {
                    alt24=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1076:3: this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement
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
                    // InternalGlobalConstantsParser.g:1085:3: this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement
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
    // InternalGlobalConstantsParser.g:1097:1: entryRuleSTSingleArrayInitElement returns [EObject current=null] : iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF ;
    public final EObject entryRuleSTSingleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSingleArrayInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1097:65: (iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF )
            // InternalGlobalConstantsParser.g:1098:2: iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF
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
    // InternalGlobalConstantsParser.g:1104:1: ruleSTSingleArrayInitElement returns [EObject current=null] : ( (lv_initExpression_0_0= ruleSTInitializerExpression ) ) ;
    public final EObject ruleSTSingleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject lv_initExpression_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1110:2: ( ( (lv_initExpression_0_0= ruleSTInitializerExpression ) ) )
            // InternalGlobalConstantsParser.g:1111:2: ( (lv_initExpression_0_0= ruleSTInitializerExpression ) )
            {
            // InternalGlobalConstantsParser.g:1111:2: ( (lv_initExpression_0_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1112:3: (lv_initExpression_0_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1112:3: (lv_initExpression_0_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1113:4: lv_initExpression_0_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:1133:1: entryRuleSTRepeatArrayInitElement returns [EObject current=null] : iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF ;
    public final EObject entryRuleSTRepeatArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatArrayInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1133:65: (iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF )
            // InternalGlobalConstantsParser.g:1134:2: iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF
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
    // InternalGlobalConstantsParser.g:1140:1: ruleSTRepeatArrayInitElement returns [EObject current=null] : ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis ) ;
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
            // InternalGlobalConstantsParser.g:1146:2: ( ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis ) )
            // InternalGlobalConstantsParser.g:1147:2: ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )
            {
            // InternalGlobalConstantsParser.g:1147:2: ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )
            // InternalGlobalConstantsParser.g:1148:3: ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis
            {
            // InternalGlobalConstantsParser.g:1148:3: ( (lv_repetitions_0_0= RULE_INT ) )
            // InternalGlobalConstantsParser.g:1149:4: (lv_repetitions_0_0= RULE_INT )
            {
            // InternalGlobalConstantsParser.g:1149:4: (lv_repetitions_0_0= RULE_INT )
            // InternalGlobalConstantsParser.g:1150:5: lv_repetitions_0_0= RULE_INT
            {
            lv_repetitions_0_0=(Token)match(input,RULE_INT,FOLLOW_23); if (state.failed) return current;
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

            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTRepeatArrayInitElementAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1170:3: ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1171:4: (lv_initExpressions_2_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1171:4: (lv_initExpressions_2_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1172:5: lv_initExpressions_2_0= ruleSTInitializerExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_24);
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

            // InternalGlobalConstantsParser.g:1189:3: (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==Comma) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1190:4: otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getSTRepeatArrayInitElementAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1194:4: ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
            	    // InternalGlobalConstantsParser.g:1195:5: (lv_initExpressions_4_0= ruleSTInitializerExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:1195:5: (lv_initExpressions_4_0= ruleSTInitializerExpression )
            	    // InternalGlobalConstantsParser.g:1196:6: lv_initExpressions_4_0= ruleSTInitializerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_3_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_24);
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
            	    break loop25;
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
    // InternalGlobalConstantsParser.g:1222:1: entryRuleSTStructInitializerExpression returns [EObject current=null] : iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF ;
    public final EObject entryRuleSTStructInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:1222:70: (iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:1223:2: iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:1229:1: ruleSTStructInitializerExpression returns [EObject current=null] : ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis ) ;
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
            // InternalGlobalConstantsParser.g:1235:2: ( ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis ) )
            // InternalGlobalConstantsParser.g:1236:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis )
            {
            // InternalGlobalConstantsParser.g:1236:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis )
            // InternalGlobalConstantsParser.g:1237:3: ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis
            {
            // InternalGlobalConstantsParser.g:1237:3: ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_ID) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1238:4: ( ( ruleQualifiedName ) ) otherlv_1= NumberSign
                    {
                    // InternalGlobalConstantsParser.g:1238:4: ( ( ruleQualifiedName ) )
                    // InternalGlobalConstantsParser.g:1239:5: ( ruleQualifiedName )
                    {
                    // InternalGlobalConstantsParser.g:1239:5: ( ruleQualifiedName )
                    // InternalGlobalConstantsParser.g:1240:6: ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStructInitializerExpressionRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getTypeStructuredTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_25);
                    ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStructInitializerExpressionAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_26); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTStructInitializerExpressionAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1263:3: ( (lv_values_3_0= ruleSTStructInitElement ) )
            // InternalGlobalConstantsParser.g:1264:4: (lv_values_3_0= ruleSTStructInitElement )
            {
            // InternalGlobalConstantsParser.g:1264:4: (lv_values_3_0= ruleSTStructInitElement )
            // InternalGlobalConstantsParser.g:1265:5: lv_values_3_0= ruleSTStructInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_24);
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

            // InternalGlobalConstantsParser.g:1282:3: (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==Comma) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1283:4: otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) )
            	    {
            	    otherlv_4=(Token)match(input,Comma,FOLLOW_26); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getSTStructInitializerExpressionAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1287:4: ( (lv_values_5_0= ruleSTStructInitElement ) )
            	    // InternalGlobalConstantsParser.g:1288:5: (lv_values_5_0= ruleSTStructInitElement )
            	    {
            	    // InternalGlobalConstantsParser.g:1288:5: (lv_values_5_0= ruleSTStructInitElement )
            	    // InternalGlobalConstantsParser.g:1289:6: lv_values_5_0= ruleSTStructInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_3_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_24);
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
            	    break loop27;
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
    // InternalGlobalConstantsParser.g:1315:1: entryRuleSTStructInitElement returns [EObject current=null] : iv_ruleSTStructInitElement= ruleSTStructInitElement EOF ;
    public final EObject entryRuleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1315:60: (iv_ruleSTStructInitElement= ruleSTStructInitElement EOF )
            // InternalGlobalConstantsParser.g:1316:2: iv_ruleSTStructInitElement= ruleSTStructInitElement EOF
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
    // InternalGlobalConstantsParser.g:1322:1: ruleSTStructInitElement returns [EObject current=null] : ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) ;
    public final EObject ruleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1328:2: ( ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1329:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1329:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            // InternalGlobalConstantsParser.g:1330:3: ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) )
            {
            // InternalGlobalConstantsParser.g:1330:3: ( ( ruleSTFeatureName ) )
            // InternalGlobalConstantsParser.g:1331:4: ( ruleSTFeatureName )
            {
            // InternalGlobalConstantsParser.g:1331:4: ( ruleSTFeatureName )
            // InternalGlobalConstantsParser.g:1332:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTStructInitElementRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_27);
            ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTStructInitElementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1350:3: ( (lv_value_2_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1351:4: (lv_value_2_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1351:4: (lv_value_2_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1352:5: lv_value_2_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:1373:1: entryRuleSTPragma returns [EObject current=null] : iv_ruleSTPragma= ruleSTPragma EOF ;
    public final EObject entryRuleSTPragma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPragma = null;


        try {
            // InternalGlobalConstantsParser.g:1373:49: (iv_ruleSTPragma= ruleSTPragma EOF )
            // InternalGlobalConstantsParser.g:1374:2: iv_ruleSTPragma= ruleSTPragma EOF
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
    // InternalGlobalConstantsParser.g:1380:1: ruleSTPragma returns [EObject current=null] : ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket ) ;
    public final EObject ruleSTPragma() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_attributes_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1386:2: ( ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket ) )
            // InternalGlobalConstantsParser.g:1387:2: ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket )
            {
            // InternalGlobalConstantsParser.g:1387:2: ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket )
            // InternalGlobalConstantsParser.g:1388:3: () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket
            {
            // InternalGlobalConstantsParser.g:1388:3: ()
            // InternalGlobalConstantsParser.g:1389:4: 
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
            // InternalGlobalConstantsParser.g:1399:3: ( (lv_attributes_2_0= ruleSTAttribute ) )
            // InternalGlobalConstantsParser.g:1400:4: (lv_attributes_2_0= ruleSTAttribute )
            {
            // InternalGlobalConstantsParser.g:1400:4: (lv_attributes_2_0= ruleSTAttribute )
            // InternalGlobalConstantsParser.g:1401:5: lv_attributes_2_0= ruleSTAttribute
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_28);
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

            // InternalGlobalConstantsParser.g:1418:3: (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==Comma) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1419:4: otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getSTPragmaAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1423:4: ( (lv_attributes_4_0= ruleSTAttribute ) )
            	    // InternalGlobalConstantsParser.g:1424:5: (lv_attributes_4_0= ruleSTAttribute )
            	    {
            	    // InternalGlobalConstantsParser.g:1424:5: (lv_attributes_4_0= ruleSTAttribute )
            	    // InternalGlobalConstantsParser.g:1425:6: lv_attributes_4_0= ruleSTAttribute
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_3_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_28);
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
            	    break loop28;
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
    // InternalGlobalConstantsParser.g:1451:1: entryRuleSTAttribute returns [EObject current=null] : iv_ruleSTAttribute= ruleSTAttribute EOF ;
    public final EObject entryRuleSTAttribute() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAttribute = null;


        try {
            // InternalGlobalConstantsParser.g:1451:52: (iv_ruleSTAttribute= ruleSTAttribute EOF )
            // InternalGlobalConstantsParser.g:1452:2: iv_ruleSTAttribute= ruleSTAttribute EOF
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
    // InternalGlobalConstantsParser.g:1458:1: ruleSTAttribute returns [EObject current=null] : ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) ;
    public final EObject ruleSTAttribute() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1464:2: ( ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1465:2: ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1465:2: ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            // InternalGlobalConstantsParser.g:1466:3: ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) )
            {
            // InternalGlobalConstantsParser.g:1466:3: ( ( ruleSTAttributeName ) )
            // InternalGlobalConstantsParser.g:1467:4: ( ruleSTAttributeName )
            {
            // InternalGlobalConstantsParser.g:1467:4: ( ruleSTAttributeName )
            // InternalGlobalConstantsParser.g:1468:5: ruleSTAttributeName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTAttributeRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAttributeAccess().getDeclarationAttributeDeclarationCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_27);
            ruleSTAttributeName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTAttributeAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1486:3: ( (lv_value_2_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1487:4: (lv_value_2_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1487:4: (lv_value_2_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1488:5: lv_value_2_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:1509:1: entryRuleSTAttributeName returns [String current=null] : iv_ruleSTAttributeName= ruleSTAttributeName EOF ;
    public final String entryRuleSTAttributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAttributeName = null;


        try {
            // InternalGlobalConstantsParser.g:1509:55: (iv_ruleSTAttributeName= ruleSTAttributeName EOF )
            // InternalGlobalConstantsParser.g:1510:2: iv_ruleSTAttributeName= ruleSTAttributeName EOF
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
    // InternalGlobalConstantsParser.g:1516:1: ruleSTAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_QualifiedName_0= ruleQualifiedName ;
    public final AntlrDatatypeRuleToken ruleSTAttributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1522:2: (this_QualifiedName_0= ruleQualifiedName )
            // InternalGlobalConstantsParser.g:1523:2: this_QualifiedName_0= ruleQualifiedName
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
    // InternalGlobalConstantsParser.g:1536:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1536:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalGlobalConstantsParser.g:1537:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalGlobalConstantsParser.g:1543:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) ;
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
            // InternalGlobalConstantsParser.g:1549:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) )
            // InternalGlobalConstantsParser.g:1550:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            {
            // InternalGlobalConstantsParser.g:1550:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==LDATE_AND_TIME||LA30_0==DATE_AND_TIME||LA30_0==LTIME_OF_DAY||LA30_0==TIME_OF_DAY||LA30_0==CONTINUE||LA30_0==WSTRING||LA30_0==REPEAT||LA30_0==RETURN||LA30_0==STRING||LA30_0==DWORD||LA30_0==FALSE||(LA30_0>=LDATE && LA30_0<=LWORD)||(LA30_0>=UDINT && LA30_0<=ULINT)||(LA30_0>=USINT && LA30_0<=DINT)||LA30_0==EXIT||(LA30_0>=LINT && LA30_0<=LTOD)||(LA30_0>=REAL && LA30_0<=SINT)||(LA30_0>=THIS && LA30_0<=TRUE)||LA30_0==UINT||LA30_0==WORD||(LA30_0>=AND && LA30_0<=NOT)||LA30_0==TOD||LA30_0==XOR||(LA30_0>=DT && LA30_0<=LT)||LA30_0==OR||LA30_0==LeftParenthesis||LA30_0==PlusSign||LA30_0==HyphenMinus||(LA30_0>=D && LA30_0<=T)||(LA30_0>=RULE_NON_DECIMAL && LA30_0<=RULE_INT)||(LA30_0>=RULE_ID && LA30_0<=RULE_STRING)) ) {
                alt30=1;
            }
            else if ( (LA30_0==Semicolon) ) {
                alt30=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1551:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    {
                    // InternalGlobalConstantsParser.g:1551:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    // InternalGlobalConstantsParser.g:1552:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon
                    {
                    // InternalGlobalConstantsParser.g:1552:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )
                    int alt29=9;
                    alt29 = dfa29.predict(input);
                    switch (alt29) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:1553:5: this_STIfStatement_0= ruleSTIfStatement
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
                            // InternalGlobalConstantsParser.g:1562:5: this_STCaseStatement_1= ruleSTCaseStatement
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
                            // InternalGlobalConstantsParser.g:1571:5: this_STForStatement_2= ruleSTForStatement
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
                            // InternalGlobalConstantsParser.g:1580:5: this_STWhileStatement_3= ruleSTWhileStatement
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
                            // InternalGlobalConstantsParser.g:1589:5: this_STRepeatStatement_4= ruleSTRepeatStatement
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
                            // InternalGlobalConstantsParser.g:1598:5: this_STAssignment_5= ruleSTAssignment
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
                            // InternalGlobalConstantsParser.g:1607:5: ( () otherlv_7= RETURN )
                            {
                            // InternalGlobalConstantsParser.g:1607:5: ( () otherlv_7= RETURN )
                            // InternalGlobalConstantsParser.g:1608:6: () otherlv_7= RETURN
                            {
                            // InternalGlobalConstantsParser.g:1608:6: ()
                            // InternalGlobalConstantsParser.g:1609:7: 
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
                            // InternalGlobalConstantsParser.g:1621:5: ( () otherlv_9= CONTINUE )
                            {
                            // InternalGlobalConstantsParser.g:1621:5: ( () otherlv_9= CONTINUE )
                            // InternalGlobalConstantsParser.g:1622:6: () otherlv_9= CONTINUE
                            {
                            // InternalGlobalConstantsParser.g:1622:6: ()
                            // InternalGlobalConstantsParser.g:1623:7: 
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
                            // InternalGlobalConstantsParser.g:1635:5: ( () otherlv_11= EXIT )
                            {
                            // InternalGlobalConstantsParser.g:1635:5: ( () otherlv_11= EXIT )
                            // InternalGlobalConstantsParser.g:1636:6: () otherlv_11= EXIT
                            {
                            // InternalGlobalConstantsParser.g:1636:6: ()
                            // InternalGlobalConstantsParser.g:1637:7: 
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
                    // InternalGlobalConstantsParser.g:1655:3: ( () otherlv_14= Semicolon )
                    {
                    // InternalGlobalConstantsParser.g:1655:3: ( () otherlv_14= Semicolon )
                    // InternalGlobalConstantsParser.g:1656:4: () otherlv_14= Semicolon
                    {
                    // InternalGlobalConstantsParser.g:1656:4: ()
                    // InternalGlobalConstantsParser.g:1657:5: 
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
    // InternalGlobalConstantsParser.g:1672:1: entryRuleSTAssignment returns [EObject current=null] : iv_ruleSTAssignment= ruleSTAssignment EOF ;
    public final EObject entryRuleSTAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignment = null;


        try {
            // InternalGlobalConstantsParser.g:1672:53: (iv_ruleSTAssignment= ruleSTAssignment EOF )
            // InternalGlobalConstantsParser.g:1673:2: iv_ruleSTAssignment= ruleSTAssignment EOF
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
    // InternalGlobalConstantsParser.g:1679:1: ruleSTAssignment returns [EObject current=null] : (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? ) ;
    public final EObject ruleSTAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_STExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1685:2: ( (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? ) )
            // InternalGlobalConstantsParser.g:1686:2: (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? )
            {
            // InternalGlobalConstantsParser.g:1686:2: (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? )
            // InternalGlobalConstantsParser.g:1687:3: this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAssignmentAccess().getSTExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_29);
            this_STExpression_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:1695:3: ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==ColonEqualsSign) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1696:4: () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) )
                    {
                    // InternalGlobalConstantsParser.g:1696:4: ()
                    // InternalGlobalConstantsParser.g:1697:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElementAndSet(
                      						grammarAccess.getSTAssignmentAccess().getSTAssignmentLeftAction_1_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTAssignmentAccess().getColonEqualsSignKeyword_1_1());
                      			
                    }
                    // InternalGlobalConstantsParser.g:1707:4: ( (lv_right_3_0= ruleSTAssignment ) )
                    // InternalGlobalConstantsParser.g:1708:5: (lv_right_3_0= ruleSTAssignment )
                    {
                    // InternalGlobalConstantsParser.g:1708:5: (lv_right_3_0= ruleSTAssignment )
                    // InternalGlobalConstantsParser.g:1709:6: lv_right_3_0= ruleSTAssignment
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
    // InternalGlobalConstantsParser.g:1731:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1731:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalGlobalConstantsParser.g:1732:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
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
    // InternalGlobalConstantsParser.g:1738:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1744:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalGlobalConstantsParser.g:1745:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalGlobalConstantsParser.g:1745:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            int alt32=3;
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
                alt32=1;
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
                    alt32=1;
                    }
                    break;
                case ColonEqualsSign:
                    {
                    alt32=2;
                    }
                    break;
                case EqualsSignGreaterThanSign:
                    {
                    alt32=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 32, 2, input);

                    throw nvae;
                }

                }
                break;
            case NOT:
                {
                int LA32_3 = input.LA(2);

                if ( (LA32_3==LDATE_AND_TIME||LA32_3==DATE_AND_TIME||LA32_3==LTIME_OF_DAY||LA32_3==TIME_OF_DAY||LA32_3==WSTRING||LA32_3==STRING||LA32_3==DWORD||LA32_3==FALSE||(LA32_3>=LDATE && LA32_3<=LWORD)||(LA32_3>=UDINT && LA32_3<=ULINT)||(LA32_3>=USINT && LA32_3<=WCHAR)||(LA32_3>=BOOL && LA32_3<=BYTE)||(LA32_3>=CHAR && LA32_3<=DINT)||(LA32_3>=LINT && LA32_3<=LTOD)||(LA32_3>=REAL && LA32_3<=SINT)||(LA32_3>=THIS && LA32_3<=TRUE)||LA32_3==UINT||LA32_3==WORD||LA32_3==AND||(LA32_3>=INT && LA32_3<=NOT)||LA32_3==TOD||LA32_3==XOR||LA32_3==DT||(LA32_3>=LD && LA32_3<=LT)||LA32_3==OR||LA32_3==LeftParenthesis||LA32_3==PlusSign||LA32_3==HyphenMinus||(LA32_3>=D && LA32_3<=T)||(LA32_3>=RULE_NON_DECIMAL && LA32_3<=RULE_INT)||LA32_3==RULE_STRING) ) {
                    alt32=1;
                }
                else if ( (LA32_3==RULE_ID) ) {
                    int LA32_6 = input.LA(3);

                    if ( (LA32_6==EOF||LA32_6==AND||LA32_6==MOD||LA32_6==XOR||(LA32_6>=AsteriskAsterisk && LA32_6<=ColonColon)||(LA32_6>=LessThanSignEqualsSign && LA32_6<=LessThanSignGreaterThanSign)||LA32_6==GreaterThanSignEqualsSign||LA32_6==OR||(LA32_6>=NumberSign && LA32_6<=Solidus)||(LA32_6>=LessThanSign && LA32_6<=GreaterThanSign)||LA32_6==LeftSquareBracket) ) {
                        alt32=1;
                    }
                    else if ( (LA32_6==EqualsSignGreaterThanSign) ) {
                        alt32=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 32, 6, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 32, 3, input);

                    throw nvae;
                }
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
                    // InternalGlobalConstantsParser.g:1746:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
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
                    // InternalGlobalConstantsParser.g:1755:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
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
                    // InternalGlobalConstantsParser.g:1764:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
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
    // InternalGlobalConstantsParser.g:1776:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1776:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalGlobalConstantsParser.g:1777:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
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
    // InternalGlobalConstantsParser.g:1783:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_argument_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_argument_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1789:2: ( ( (lv_argument_0_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1790:2: ( (lv_argument_0_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1790:2: ( (lv_argument_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1791:3: (lv_argument_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1791:3: (lv_argument_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1792:4: lv_argument_0_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1812:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1812:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalGlobalConstantsParser.g:1813:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
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
    // InternalGlobalConstantsParser.g:1819:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_argument_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1825:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1826:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1826:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1827:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1827:3: ( (otherlv_0= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1828:4: (otherlv_0= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1828:4: (otherlv_0= RULE_ID )
            // InternalGlobalConstantsParser.g:1829:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_27); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementCrossReference_0_0());
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1844:3: ( (lv_argument_2_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1845:4: (lv_argument_2_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1845:4: (lv_argument_2_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1846:5: lv_argument_2_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1867:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1867:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalGlobalConstantsParser.g:1868:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
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
    // InternalGlobalConstantsParser.g:1874:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_argument_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1880:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1881:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1881:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1882:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1882:3: ( (lv_not_0_0= NOT ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==NOT) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1883:4: (lv_not_0_0= NOT )
                    {
                    // InternalGlobalConstantsParser.g:1883:4: (lv_not_0_0= NOT )
                    // InternalGlobalConstantsParser.g:1884:5: lv_not_0_0= NOT
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

            // InternalGlobalConstantsParser.g:1896:3: ( (otherlv_1= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1897:4: (otherlv_1= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1897:4: (otherlv_1= RULE_ID )
            // InternalGlobalConstantsParser.g:1898:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1913:3: ( (lv_argument_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1914:4: (lv_argument_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1914:4: (lv_argument_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1915:5: lv_argument_3_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1936:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1936:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalGlobalConstantsParser.g:1937:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalGlobalConstantsParser.g:1943:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalGlobalConstantsParser.g:1949:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalGlobalConstantsParser.g:1950:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalGlobalConstantsParser.g:1950:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalGlobalConstantsParser.g:1951:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1955:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1956:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1956:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1957:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_31);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatementAccess().getTHENKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1978:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==LDATE_AND_TIME||LA34_0==DATE_AND_TIME||LA34_0==LTIME_OF_DAY||LA34_0==TIME_OF_DAY||LA34_0==CONTINUE||LA34_0==WSTRING||LA34_0==REPEAT||LA34_0==RETURN||LA34_0==STRING||LA34_0==DWORD||LA34_0==FALSE||(LA34_0>=LDATE && LA34_0<=LWORD)||(LA34_0>=UDINT && LA34_0<=ULINT)||(LA34_0>=USINT && LA34_0<=DINT)||LA34_0==EXIT||(LA34_0>=LINT && LA34_0<=LTOD)||(LA34_0>=REAL && LA34_0<=SINT)||(LA34_0>=THIS && LA34_0<=TRUE)||LA34_0==UINT||LA34_0==WORD||(LA34_0>=AND && LA34_0<=NOT)||LA34_0==TOD||LA34_0==XOR||(LA34_0>=DT && LA34_0<=LT)||LA34_0==OR||LA34_0==LeftParenthesis||LA34_0==PlusSign||LA34_0==HyphenMinus||LA34_0==Semicolon||(LA34_0>=D && LA34_0<=T)||(LA34_0>=RULE_NON_DECIMAL && LA34_0<=RULE_INT)||(LA34_0>=RULE_ID && LA34_0<=RULE_STRING)) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1979:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1979:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1980:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
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
            	    break loop34;
                }
            } while (true);

            // InternalGlobalConstantsParser.g:1997:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==ELSIF) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1998:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalGlobalConstantsParser.g:1998:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalGlobalConstantsParser.g:1999:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_33);
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
            	    break loop35;
                }
            } while (true);

            // InternalGlobalConstantsParser.g:2016:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==ELSE) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2017:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalGlobalConstantsParser.g:2017:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalGlobalConstantsParser.g:2018:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_34);
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
    // InternalGlobalConstantsParser.g:2043:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalGlobalConstantsParser.g:2043:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalGlobalConstantsParser.g:2044:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalGlobalConstantsParser.g:2050:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2056:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:2057:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:2057:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:2058:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2062:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2063:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2063:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2064:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_31);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_35); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2085:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==LDATE_AND_TIME||LA37_0==DATE_AND_TIME||LA37_0==LTIME_OF_DAY||LA37_0==TIME_OF_DAY||LA37_0==CONTINUE||LA37_0==WSTRING||LA37_0==REPEAT||LA37_0==RETURN||LA37_0==STRING||LA37_0==DWORD||LA37_0==FALSE||(LA37_0>=LDATE && LA37_0<=LWORD)||(LA37_0>=UDINT && LA37_0<=ULINT)||(LA37_0>=USINT && LA37_0<=DINT)||LA37_0==EXIT||(LA37_0>=LINT && LA37_0<=LTOD)||(LA37_0>=REAL && LA37_0<=SINT)||(LA37_0>=THIS && LA37_0<=TRUE)||LA37_0==UINT||LA37_0==WORD||(LA37_0>=AND && LA37_0<=NOT)||LA37_0==TOD||LA37_0==XOR||(LA37_0>=DT && LA37_0<=LT)||LA37_0==OR||LA37_0==LeftParenthesis||LA37_0==PlusSign||LA37_0==HyphenMinus||LA37_0==Semicolon||(LA37_0>=D && LA37_0<=T)||(LA37_0>=RULE_NON_DECIMAL && LA37_0<=RULE_INT)||(LA37_0>=RULE_ID && LA37_0<=RULE_STRING)) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2086:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2086:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2087:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_35);
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalGlobalConstantsParser.g:2108:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2108:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalGlobalConstantsParser.g:2109:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalGlobalConstantsParser.g:2115:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalGlobalConstantsParser.g:2121:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalGlobalConstantsParser.g:2122:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalGlobalConstantsParser.g:2122:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalGlobalConstantsParser.g:2123:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2127:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2128:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2128:4: (lv_selector_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2129:5: lv_selector_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
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

            otherlv_2=(Token)match(input,OF,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCaseStatementAccess().getOFKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2150:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt38=0;
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==LDATE_AND_TIME||LA38_0==DATE_AND_TIME||LA38_0==LTIME_OF_DAY||LA38_0==TIME_OF_DAY||LA38_0==WSTRING||LA38_0==STRING||LA38_0==DWORD||LA38_0==FALSE||(LA38_0>=LDATE && LA38_0<=LWORD)||(LA38_0>=UDINT && LA38_0<=ULINT)||(LA38_0>=USINT && LA38_0<=WCHAR)||(LA38_0>=BOOL && LA38_0<=BYTE)||(LA38_0>=CHAR && LA38_0<=DINT)||(LA38_0>=LINT && LA38_0<=LTOD)||(LA38_0>=REAL && LA38_0<=SINT)||(LA38_0>=THIS && LA38_0<=TRUE)||LA38_0==UINT||LA38_0==WORD||LA38_0==AND||(LA38_0>=INT && LA38_0<=NOT)||LA38_0==TOD||LA38_0==XOR||LA38_0==DT||(LA38_0>=LD && LA38_0<=LT)||LA38_0==OR||LA38_0==LeftParenthesis||LA38_0==PlusSign||LA38_0==HyphenMinus||(LA38_0>=D && LA38_0<=T)||(LA38_0>=RULE_NON_DECIMAL && LA38_0<=RULE_INT)||(LA38_0>=RULE_ID && LA38_0<=RULE_STRING)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2151:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalGlobalConstantsParser.g:2151:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalGlobalConstantsParser.g:2152:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_36);
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
            	    if ( cnt38 >= 1 ) break loop38;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(38, input);
                        throw eee;
                }
                cnt38++;
            } while (true);

            // InternalGlobalConstantsParser.g:2169:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==ELSE) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2170:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalGlobalConstantsParser.g:2170:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalGlobalConstantsParser.g:2171:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_37);
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
    // InternalGlobalConstantsParser.g:2196:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalGlobalConstantsParser.g:2196:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalGlobalConstantsParser.g:2197:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalGlobalConstantsParser.g:2203:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2209:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:2210:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:2210:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:2211:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalGlobalConstantsParser.g:2211:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2212:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2212:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2213:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_38);
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

            // InternalGlobalConstantsParser.g:2230:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==Comma) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2231:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:2235:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalGlobalConstantsParser.g:2236:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2236:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalGlobalConstantsParser.g:2237:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_38);
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
            	    break loop40;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_35); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2259:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop41:
            do {
                int alt41=2;
                alt41 = dfa41.predict(input);
                switch (alt41) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2260:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2264:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2265:5: lv_statements_4_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_35);
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalGlobalConstantsParser.g:2286:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalGlobalConstantsParser.g:2286:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalGlobalConstantsParser.g:2287:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalGlobalConstantsParser.g:2293:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2299:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:2300:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:2300:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:2301:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalGlobalConstantsParser.g:2301:3: ()
            // InternalGlobalConstantsParser.g:2302:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,ELSE,FOLLOW_35); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:2312:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==LDATE_AND_TIME||LA42_0==DATE_AND_TIME||LA42_0==LTIME_OF_DAY||LA42_0==TIME_OF_DAY||LA42_0==CONTINUE||LA42_0==WSTRING||LA42_0==REPEAT||LA42_0==RETURN||LA42_0==STRING||LA42_0==DWORD||LA42_0==FALSE||(LA42_0>=LDATE && LA42_0<=LWORD)||(LA42_0>=UDINT && LA42_0<=ULINT)||(LA42_0>=USINT && LA42_0<=DINT)||LA42_0==EXIT||(LA42_0>=LINT && LA42_0<=LTOD)||(LA42_0>=REAL && LA42_0<=SINT)||(LA42_0>=THIS && LA42_0<=TRUE)||LA42_0==UINT||LA42_0==WORD||(LA42_0>=AND && LA42_0<=NOT)||LA42_0==TOD||LA42_0==XOR||(LA42_0>=DT && LA42_0<=LT)||LA42_0==OR||LA42_0==LeftParenthesis||LA42_0==PlusSign||LA42_0==HyphenMinus||LA42_0==Semicolon||(LA42_0>=D && LA42_0<=T)||(LA42_0>=RULE_NON_DECIMAL && LA42_0<=RULE_INT)||(LA42_0>=RULE_ID && LA42_0<=RULE_STRING)) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2313:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2313:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2314:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_35);
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalGlobalConstantsParser.g:2335:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2335:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalGlobalConstantsParser.g:2336:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalGlobalConstantsParser.g:2342:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
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
            // InternalGlobalConstantsParser.g:2348:2: ( (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalGlobalConstantsParser.g:2349:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalGlobalConstantsParser.g:2349:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalGlobalConstantsParser.g:2350:3: otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2354:3: ( (lv_variable_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2355:4: (lv_variable_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2355:4: (lv_variable_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2356:5: lv_variable_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getVariableSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_27);
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

            otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2377:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2378:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2378:4: (lv_from_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2379:5: lv_from_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_39);
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

            otherlv_4=(Token)match(input,TO,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getTOKeyword_4());
              		
            }
            // InternalGlobalConstantsParser.g:2400:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2401:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2401:4: (lv_to_5_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2402:5: lv_to_5_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_40);
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

            // InternalGlobalConstantsParser.g:2419:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==BY) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2420:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:2424:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:2425:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:2425:5: (lv_by_7_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:2426:6: lv_by_7_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_41);
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

            otherlv_8=(Token)match(input,DO,FOLLOW_42); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
              		
            }
            // InternalGlobalConstantsParser.g:2448:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==LDATE_AND_TIME||LA44_0==DATE_AND_TIME||LA44_0==LTIME_OF_DAY||LA44_0==TIME_OF_DAY||LA44_0==CONTINUE||LA44_0==WSTRING||LA44_0==REPEAT||LA44_0==RETURN||LA44_0==STRING||LA44_0==DWORD||LA44_0==FALSE||(LA44_0>=LDATE && LA44_0<=LWORD)||(LA44_0>=UDINT && LA44_0<=ULINT)||(LA44_0>=USINT && LA44_0<=DINT)||LA44_0==EXIT||(LA44_0>=LINT && LA44_0<=LTOD)||(LA44_0>=REAL && LA44_0<=SINT)||(LA44_0>=THIS && LA44_0<=TRUE)||LA44_0==UINT||LA44_0==WORD||(LA44_0>=AND && LA44_0<=NOT)||LA44_0==TOD||LA44_0==XOR||(LA44_0>=DT && LA44_0<=LT)||LA44_0==OR||LA44_0==LeftParenthesis||LA44_0==PlusSign||LA44_0==HyphenMinus||LA44_0==Semicolon||(LA44_0>=D && LA44_0<=T)||(LA44_0>=RULE_NON_DECIMAL && LA44_0<=RULE_INT)||(LA44_0>=RULE_ID && LA44_0<=RULE_STRING)) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2449:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2449:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2450:5: lv_statements_9_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_42);
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
            	    break loop44;
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
    // InternalGlobalConstantsParser.g:2475:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2475:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalGlobalConstantsParser.g:2476:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalGlobalConstantsParser.g:2482:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2488:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalGlobalConstantsParser.g:2489:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalGlobalConstantsParser.g:2489:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalGlobalConstantsParser.g:2490:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2494:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2495:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2495:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2496:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_41);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_43); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2517:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==LDATE_AND_TIME||LA45_0==DATE_AND_TIME||LA45_0==LTIME_OF_DAY||LA45_0==TIME_OF_DAY||LA45_0==CONTINUE||LA45_0==WSTRING||LA45_0==REPEAT||LA45_0==RETURN||LA45_0==STRING||LA45_0==DWORD||LA45_0==FALSE||(LA45_0>=LDATE && LA45_0<=LWORD)||(LA45_0>=UDINT && LA45_0<=ULINT)||(LA45_0>=USINT && LA45_0<=DINT)||LA45_0==EXIT||(LA45_0>=LINT && LA45_0<=LTOD)||(LA45_0>=REAL && LA45_0<=SINT)||(LA45_0>=THIS && LA45_0<=TRUE)||LA45_0==UINT||LA45_0==WORD||(LA45_0>=AND && LA45_0<=NOT)||LA45_0==TOD||LA45_0==XOR||(LA45_0>=DT && LA45_0<=LT)||LA45_0==OR||LA45_0==LeftParenthesis||LA45_0==PlusSign||LA45_0==HyphenMinus||LA45_0==Semicolon||(LA45_0>=D && LA45_0<=T)||(LA45_0>=RULE_NON_DECIMAL && LA45_0<=RULE_INT)||(LA45_0>=RULE_ID && LA45_0<=RULE_STRING)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2518:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2518:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2519:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_43);
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
            	    break loop45;
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
    // InternalGlobalConstantsParser.g:2544:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2544:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalGlobalConstantsParser.g:2545:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalGlobalConstantsParser.g:2551:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2557:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalGlobalConstantsParser.g:2558:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalGlobalConstantsParser.g:2558:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalGlobalConstantsParser.g:2559:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_44); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2563:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==LDATE_AND_TIME||LA46_0==DATE_AND_TIME||LA46_0==LTIME_OF_DAY||LA46_0==TIME_OF_DAY||LA46_0==CONTINUE||LA46_0==WSTRING||LA46_0==REPEAT||LA46_0==RETURN||LA46_0==STRING||LA46_0==DWORD||LA46_0==FALSE||(LA46_0>=LDATE && LA46_0<=LWORD)||(LA46_0>=UDINT && LA46_0<=ULINT)||(LA46_0>=USINT && LA46_0<=DINT)||LA46_0==EXIT||(LA46_0>=LINT && LA46_0<=LTOD)||(LA46_0>=REAL && LA46_0<=SINT)||(LA46_0>=THIS && LA46_0<=TRUE)||LA46_0==UINT||LA46_0==WORD||(LA46_0>=AND && LA46_0<=NOT)||LA46_0==TOD||LA46_0==XOR||(LA46_0>=DT && LA46_0<=LT)||LA46_0==OR||LA46_0==LeftParenthesis||LA46_0==PlusSign||LA46_0==HyphenMinus||LA46_0==Semicolon||(LA46_0>=D && LA46_0<=T)||(LA46_0>=RULE_NON_DECIMAL && LA46_0<=RULE_INT)||(LA46_0>=RULE_ID && LA46_0<=RULE_STRING)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2564:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2564:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2565:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_44);
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
            	    break loop46;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2586:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2587:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2587:4: (lv_condition_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2588:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_45);
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
    // InternalGlobalConstantsParser.g:2613:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2613:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalGlobalConstantsParser.g:2614:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalGlobalConstantsParser.g:2620:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2626:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalGlobalConstantsParser.g:2627:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalGlobalConstantsParser.g:2638:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2638:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalGlobalConstantsParser.g:2639:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalGlobalConstantsParser.g:2645:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2651:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2652:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2652:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2653:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2661:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==FullStopFullStop) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2662:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2662:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2663:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2663:5: ()
            	    // InternalGlobalConstantsParser.g:2664:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2670:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalGlobalConstantsParser.g:2671:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2671:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalGlobalConstantsParser.g:2672:7: lv_op_2_0= ruleSubrangeOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:2690:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalGlobalConstantsParser.g:2691:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2691:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalGlobalConstantsParser.g:2692:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_46);
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalGlobalConstantsParser.g:2714:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2714:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalGlobalConstantsParser.g:2715:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalGlobalConstantsParser.g:2721:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2727:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2728:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2728:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2729:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_47);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2737:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==OR) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2738:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2738:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2739:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2739:5: ()
            	    // InternalGlobalConstantsParser.g:2740:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2746:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalGlobalConstantsParser.g:2747:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2747:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalGlobalConstantsParser.g:2748:7: lv_op_2_0= ruleOrOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:2766:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalGlobalConstantsParser.g:2767:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2767:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalGlobalConstantsParser.g:2768:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalGlobalConstantsParser.g:2790:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2790:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalGlobalConstantsParser.g:2791:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalGlobalConstantsParser.g:2797:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2803:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2804:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2804:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2805:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_48);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2813:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==XOR) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2814:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2814:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2815:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2815:5: ()
            	    // InternalGlobalConstantsParser.g:2816:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2822:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalGlobalConstantsParser.g:2823:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2823:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalGlobalConstantsParser.g:2824:7: lv_op_2_0= ruleXorOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:2842:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalGlobalConstantsParser.g:2843:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2843:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalGlobalConstantsParser.g:2844:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_48);
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalGlobalConstantsParser.g:2866:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2866:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalGlobalConstantsParser.g:2867:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalGlobalConstantsParser.g:2873:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2879:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2880:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2880:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2881:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2889:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==AND||LA50_0==Ampersand) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2890:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2890:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2891:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2891:5: ()
            	    // InternalGlobalConstantsParser.g:2892:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2898:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalGlobalConstantsParser.g:2899:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2899:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalGlobalConstantsParser.g:2900:7: lv_op_2_0= ruleAndOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:2918:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalGlobalConstantsParser.g:2919:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2919:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalGlobalConstantsParser.g:2920:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalGlobalConstantsParser.g:2942:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2942:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalGlobalConstantsParser.g:2943:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalGlobalConstantsParser.g:2949:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2955:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2956:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2956:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2957:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_50);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2965:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==LessThanSignGreaterThanSign||LA51_0==EqualsSign) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2966:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2966:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2967:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2967:5: ()
            	    // InternalGlobalConstantsParser.g:2968:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2974:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalGlobalConstantsParser.g:2975:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2975:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalGlobalConstantsParser.g:2976:7: lv_op_2_0= ruleEqualityOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:2994:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalGlobalConstantsParser.g:2995:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2995:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalGlobalConstantsParser.g:2996:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_50);
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalGlobalConstantsParser.g:3018:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3018:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalGlobalConstantsParser.g:3019:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalGlobalConstantsParser.g:3025:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3031:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:3032:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:3032:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalGlobalConstantsParser.g:3033:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:3041:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==LessThanSignEqualsSign||LA52_0==GreaterThanSignEqualsSign||LA52_0==LessThanSign||LA52_0==GreaterThanSign) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3042:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3042:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalGlobalConstantsParser.g:3043:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3043:5: ()
            	    // InternalGlobalConstantsParser.g:3044:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:3050:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalGlobalConstantsParser.g:3051:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:3051:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalGlobalConstantsParser.g:3052:7: lv_op_2_0= ruleCompareOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:3070:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalGlobalConstantsParser.g:3071:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3071:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalGlobalConstantsParser.g:3072:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_51);
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
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalGlobalConstantsParser.g:3094:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3094:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalGlobalConstantsParser.g:3095:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalGlobalConstantsParser.g:3101:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3107:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:3108:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:3108:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalGlobalConstantsParser.g:3109:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_52);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:3117:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==PlusSign||LA53_0==HyphenMinus) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3118:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3118:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalGlobalConstantsParser.g:3119:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3119:5: ()
            	    // InternalGlobalConstantsParser.g:3120:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:3126:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalGlobalConstantsParser.g:3127:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:3127:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalGlobalConstantsParser.g:3128:7: lv_op_2_0= ruleAddSubOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:3146:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalGlobalConstantsParser.g:3147:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3147:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalGlobalConstantsParser.g:3148:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_52);
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
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalGlobalConstantsParser.g:3170:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3170:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalGlobalConstantsParser.g:3171:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalGlobalConstantsParser.g:3177:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3183:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:3184:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:3184:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalGlobalConstantsParser.g:3185:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_53);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:3193:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==MOD||LA54_0==Asterisk||LA54_0==Solidus) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3194:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3194:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalGlobalConstantsParser.g:3195:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3195:5: ()
            	    // InternalGlobalConstantsParser.g:3196:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:3202:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalGlobalConstantsParser.g:3203:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:3203:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalGlobalConstantsParser.g:3204:7: lv_op_2_0= ruleMulDivModOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:3222:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalGlobalConstantsParser.g:3223:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3223:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalGlobalConstantsParser.g:3224:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_53);
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
            	    break loop54;
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
    // InternalGlobalConstantsParser.g:3246:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3246:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalGlobalConstantsParser.g:3247:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalGlobalConstantsParser.g:3253:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3259:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:3260:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:3260:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalGlobalConstantsParser.g:3261:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_54);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:3269:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==AsteriskAsterisk) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3270:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3270:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalGlobalConstantsParser.g:3271:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3271:5: ()
            	    // InternalGlobalConstantsParser.g:3272:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:3278:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalGlobalConstantsParser.g:3279:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:3279:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalGlobalConstantsParser.g:3280:7: lv_op_2_0= rulePowerOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_13);
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

            	    // InternalGlobalConstantsParser.g:3298:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalGlobalConstantsParser.g:3299:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3299:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalGlobalConstantsParser.g:3300:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_54);
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTUnaryExpression"
    // InternalGlobalConstantsParser.g:3322:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3322:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalGlobalConstantsParser.g:3323:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalGlobalConstantsParser.g:3329:1: ruleSTUnaryExpression returns [EObject current=null] : (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        EObject this_STLiteralExpressions_1 = null;

        EObject this_STSignedLiteralExpressions_2 = null;

        Enumerator lv_op_4_0 = null;

        EObject lv_expression_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3335:2: ( (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalGlobalConstantsParser.g:3336:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalGlobalConstantsParser.g:3336:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )
            int alt56=4;
            alt56 = dfa56.predict(input);
            switch (alt56) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3337:3: this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalGlobalConstantsParser.g:3346:3: this_STLiteralExpressions_1= ruleSTLiteralExpressions
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
                    // InternalGlobalConstantsParser.g:3355:3: ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions )
                    {
                    // InternalGlobalConstantsParser.g:3355:3: ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions )
                    // InternalGlobalConstantsParser.g:3356:4: ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions
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
                    // InternalGlobalConstantsParser.g:3367:3: ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalGlobalConstantsParser.g:3367:3: ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) )
                    // InternalGlobalConstantsParser.g:3368:4: () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) )
                    {
                    // InternalGlobalConstantsParser.g:3368:4: ()
                    // InternalGlobalConstantsParser.g:3369:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_3_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGlobalConstantsParser.g:3375:4: ( (lv_op_4_0= ruleUnaryOperator ) )
                    // InternalGlobalConstantsParser.g:3376:5: (lv_op_4_0= ruleUnaryOperator )
                    {
                    // InternalGlobalConstantsParser.g:3376:5: (lv_op_4_0= ruleUnaryOperator )
                    // InternalGlobalConstantsParser.g:3377:6: lv_op_4_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_13);
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

                    // InternalGlobalConstantsParser.g:3394:4: ( (lv_expression_5_0= ruleSTUnaryExpression ) )
                    // InternalGlobalConstantsParser.g:3395:5: (lv_expression_5_0= ruleSTUnaryExpression )
                    {
                    // InternalGlobalConstantsParser.g:3395:5: (lv_expression_5_0= ruleSTUnaryExpression )
                    // InternalGlobalConstantsParser.g:3396:6: lv_expression_5_0= ruleSTUnaryExpression
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
    // InternalGlobalConstantsParser.g:3418:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3418:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalGlobalConstantsParser.g:3419:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalGlobalConstantsParser.g:3425:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalGlobalConstantsParser.g:3431:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalGlobalConstantsParser.g:3432:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalGlobalConstantsParser.g:3432:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalGlobalConstantsParser.g:3433:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_55);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:3441:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop59:
            do {
                int alt59=3;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==FullStop) ) {
                    alt59=1;
                }
                else if ( (LA59_0==LeftSquareBracket) ) {
                    alt59=2;
                }


                switch (alt59) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3442:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3442:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalGlobalConstantsParser.g:3443:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3443:5: ()
            	    // InternalGlobalConstantsParser.g:3444:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_56); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalGlobalConstantsParser.g:3454:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalGlobalConstantsParser.g:3455:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3455:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalGlobalConstantsParser.g:3456:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3456:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt57=2;
            	    int LA57_0 = input.LA(1);

            	    if ( (LA57_0==AND||LA57_0==MOD||LA57_0==XOR||LA57_0==DT||(LA57_0>=LD && LA57_0<=LT)||LA57_0==OR||LA57_0==D||LA57_0==RULE_ID) ) {
            	        alt57=1;
            	    }
            	    else if ( ((LA57_0>=B && LA57_0<=X)||LA57_0==LeftParenthesis||LA57_0==RULE_INT) ) {
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
            	            // InternalGlobalConstantsParser.g:3457:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_55);
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
            	            // InternalGlobalConstantsParser.g:3473:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_55);
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
            	    // InternalGlobalConstantsParser.g:3493:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalGlobalConstantsParser.g:3493:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalGlobalConstantsParser.g:3494:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalGlobalConstantsParser.g:3494:5: ()
            	    // InternalGlobalConstantsParser.g:3495:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_5=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_5, grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1());
            	      				
            	    }
            	    // InternalGlobalConstantsParser.g:3505:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalGlobalConstantsParser.g:3506:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3506:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalGlobalConstantsParser.g:3507:7: lv_index_6_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalGlobalConstantsParser.g:3524:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop58:
            	    do {
            	        int alt58=2;
            	        int LA58_0 = input.LA(1);

            	        if ( (LA58_0==Comma) ) {
            	            alt58=1;
            	        }


            	        switch (alt58) {
            	    	case 1 :
            	    	    // InternalGlobalConstantsParser.g:3525:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalGlobalConstantsParser.g:3529:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalGlobalConstantsParser.g:3530:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalGlobalConstantsParser.g:3530:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalGlobalConstantsParser.g:3531:8: lv_index_8_0= ruleSTExpression
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0());
            	    	      							
            	    	    }
            	    	    pushFollow(FOLLOW_14);
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
            	    	    break loop58;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_55); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
            	      				
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
    // $ANTLR end "ruleSTAccessExpression"


    // $ANTLR start "entryRuleSTPrimaryExpression"
    // InternalGlobalConstantsParser.g:3559:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3559:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalGlobalConstantsParser.g:3560:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalGlobalConstantsParser.g:3566:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STBuiltinFeatureExpression_4 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3572:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression ) )
            // InternalGlobalConstantsParser.g:3573:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression )
            {
            // InternalGlobalConstantsParser.g:3573:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression )
            int alt60=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt60=1;
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
                alt60=2;
                }
                break;
            case THIS:
                {
                alt60=3;
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
                    // InternalGlobalConstantsParser.g:3574:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3574:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalGlobalConstantsParser.g:3575:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_57);
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
                    // InternalGlobalConstantsParser.g:3593:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalGlobalConstantsParser.g:3602:3: this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression
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
    // InternalGlobalConstantsParser.g:3614:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3614:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalGlobalConstantsParser.g:3615:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalGlobalConstantsParser.g:3621:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token lv_call_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3627:2: ( ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:3628:2: ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:3628:2: ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:3629:3: () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:3629:3: ()
            // InternalGlobalConstantsParser.g:3630:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3636:3: ( ( ruleSTFeatureName ) )
            // InternalGlobalConstantsParser.g:3637:4: ( ruleSTFeatureName )
            {
            // InternalGlobalConstantsParser.g:3637:4: ( ruleSTFeatureName )
            // InternalGlobalConstantsParser.g:3638:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_58);
            ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:3652:3: ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==LeftParenthesis) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3653:4: ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalGlobalConstantsParser.g:3653:4: ( (lv_call_2_0= LeftParenthesis ) )
                    // InternalGlobalConstantsParser.g:3654:5: (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3654:5: (lv_call_2_0= LeftParenthesis )
                    // InternalGlobalConstantsParser.g:3655:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_59); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:3667:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==LDATE_AND_TIME||LA62_0==DATE_AND_TIME||LA62_0==LTIME_OF_DAY||LA62_0==TIME_OF_DAY||LA62_0==WSTRING||LA62_0==STRING||LA62_0==DWORD||LA62_0==FALSE||(LA62_0>=LDATE && LA62_0<=LWORD)||(LA62_0>=UDINT && LA62_0<=ULINT)||(LA62_0>=USINT && LA62_0<=WCHAR)||(LA62_0>=BOOL && LA62_0<=BYTE)||(LA62_0>=CHAR && LA62_0<=DINT)||(LA62_0>=LINT && LA62_0<=LTOD)||(LA62_0>=REAL && LA62_0<=SINT)||(LA62_0>=THIS && LA62_0<=TRUE)||LA62_0==UINT||LA62_0==WORD||LA62_0==AND||(LA62_0>=INT && LA62_0<=NOT)||LA62_0==TOD||LA62_0==XOR||LA62_0==DT||(LA62_0>=LD && LA62_0<=LT)||LA62_0==OR||LA62_0==LeftParenthesis||LA62_0==PlusSign||LA62_0==HyphenMinus||(LA62_0>=D && LA62_0<=T)||(LA62_0>=RULE_NON_DECIMAL && LA62_0<=RULE_INT)||(LA62_0>=RULE_ID && LA62_0<=RULE_STRING)) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:3668:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalGlobalConstantsParser.g:3668:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalGlobalConstantsParser.g:3669:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalGlobalConstantsParser.g:3669:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalGlobalConstantsParser.g:3670:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_24);
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

                            // InternalGlobalConstantsParser.g:3687:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop61:
                            do {
                                int alt61=2;
                                int LA61_0 = input.LA(1);

                                if ( (LA61_0==Comma) ) {
                                    alt61=1;
                                }


                                switch (alt61) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:3688:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalGlobalConstantsParser.g:3692:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalGlobalConstantsParser.g:3693:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalGlobalConstantsParser.g:3693:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalGlobalConstantsParser.g:3694:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_24);
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
                            	    break loop61;
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
    // InternalGlobalConstantsParser.g:3722:1: entryRuleSTFeatureName returns [String current=null] : iv_ruleSTFeatureName= ruleSTFeatureName EOF ;
    public final String entryRuleSTFeatureName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTFeatureName = null;


        try {
            // InternalGlobalConstantsParser.g:3722:53: (iv_ruleSTFeatureName= ruleSTFeatureName EOF )
            // InternalGlobalConstantsParser.g:3723:2: iv_ruleSTFeatureName= ruleSTFeatureName EOF
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
    // InternalGlobalConstantsParser.g:3729:1: ruleSTFeatureName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTFeatureName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3735:2: ( (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) )
            // InternalGlobalConstantsParser.g:3736:2: (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            {
            // InternalGlobalConstantsParser.g:3736:2: (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            int alt64=9;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt64=1;
                }
                break;
            case LT:
                {
                alt64=2;
                }
                break;
            case AND:
                {
                alt64=3;
                }
                break;
            case OR:
                {
                alt64=4;
                }
                break;
            case XOR:
                {
                alt64=5;
                }
                break;
            case MOD:
                {
                alt64=6;
                }
                break;
            case D:
                {
                alt64=7;
                }
                break;
            case DT:
                {
                alt64=8;
                }
                break;
            case LD:
                {
                alt64=9;
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
                    // InternalGlobalConstantsParser.g:3737:3: this_QualifiedName_0= ruleQualifiedName
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
                    // InternalGlobalConstantsParser.g:3748:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:3754:3: kw= AND
                    {
                    kw=(Token)match(input,AND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getANDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:3760:3: kw= OR
                    {
                    kw=(Token)match(input,OR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getORKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:3766:3: kw= XOR
                    {
                    kw=(Token)match(input,XOR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getXORKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsParser.g:3772:3: kw= MOD
                    {
                    kw=(Token)match(input,MOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getMODKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsParser.g:3778:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGlobalConstantsParser.g:3784:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGlobalConstantsParser.g:3790:3: kw= LD
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
    // InternalGlobalConstantsParser.g:3799:1: entryRuleSTBuiltinFeatureExpression returns [EObject current=null] : iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF ;
    public final EObject entryRuleSTBuiltinFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTBuiltinFeatureExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3799:67: (iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF )
            // InternalGlobalConstantsParser.g:3800:2: iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF
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
    // InternalGlobalConstantsParser.g:3806:1: ruleSTBuiltinFeatureExpression returns [EObject current=null] : ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
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
            // InternalGlobalConstantsParser.g:3812:2: ( ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:3813:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:3813:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:3814:3: () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:3814:3: ()
            // InternalGlobalConstantsParser.g:3815:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTBuiltinFeatureExpressionAccess().getSTBuiltinFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3821:3: ( (lv_feature_1_0= ruleSTBuiltinFeature ) )
            // InternalGlobalConstantsParser.g:3822:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            {
            // InternalGlobalConstantsParser.g:3822:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            // InternalGlobalConstantsParser.g:3823:5: lv_feature_1_0= ruleSTBuiltinFeature
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureSTBuiltinFeatureEnumRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_58);
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

            // InternalGlobalConstantsParser.g:3840:3: ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==LeftParenthesis) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3841:4: ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalGlobalConstantsParser.g:3841:4: ( (lv_call_2_0= LeftParenthesis ) )
                    // InternalGlobalConstantsParser.g:3842:5: (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3842:5: (lv_call_2_0= LeftParenthesis )
                    // InternalGlobalConstantsParser.g:3843:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_59); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:3855:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==LDATE_AND_TIME||LA66_0==DATE_AND_TIME||LA66_0==LTIME_OF_DAY||LA66_0==TIME_OF_DAY||LA66_0==WSTRING||LA66_0==STRING||LA66_0==DWORD||LA66_0==FALSE||(LA66_0>=LDATE && LA66_0<=LWORD)||(LA66_0>=UDINT && LA66_0<=ULINT)||(LA66_0>=USINT && LA66_0<=WCHAR)||(LA66_0>=BOOL && LA66_0<=BYTE)||(LA66_0>=CHAR && LA66_0<=DINT)||(LA66_0>=LINT && LA66_0<=LTOD)||(LA66_0>=REAL && LA66_0<=SINT)||(LA66_0>=THIS && LA66_0<=TRUE)||LA66_0==UINT||LA66_0==WORD||LA66_0==AND||(LA66_0>=INT && LA66_0<=NOT)||LA66_0==TOD||LA66_0==XOR||LA66_0==DT||(LA66_0>=LD && LA66_0<=LT)||LA66_0==OR||LA66_0==LeftParenthesis||LA66_0==PlusSign||LA66_0==HyphenMinus||(LA66_0>=D && LA66_0<=T)||(LA66_0>=RULE_NON_DECIMAL && LA66_0<=RULE_INT)||(LA66_0>=RULE_ID && LA66_0<=RULE_STRING)) ) {
                        alt66=1;
                    }
                    switch (alt66) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:3856:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalGlobalConstantsParser.g:3856:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalGlobalConstantsParser.g:3857:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalGlobalConstantsParser.g:3857:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalGlobalConstantsParser.g:3858:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_24);
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

                            // InternalGlobalConstantsParser.g:3875:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop65:
                            do {
                                int alt65=2;
                                int LA65_0 = input.LA(1);

                                if ( (LA65_0==Comma) ) {
                                    alt65=1;
                                }


                                switch (alt65) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:3876:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTBuiltinFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalGlobalConstantsParser.g:3880:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalGlobalConstantsParser.g:3881:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalGlobalConstantsParser.g:3881:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalGlobalConstantsParser.g:3882:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_24);
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
                            	    break loop65;
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
    // InternalGlobalConstantsParser.g:3910:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3910:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalGlobalConstantsParser.g:3911:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalGlobalConstantsParser.g:3917:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_specifier_1_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3923:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) )
            // InternalGlobalConstantsParser.g:3924:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            {
            // InternalGlobalConstantsParser.g:3924:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            // InternalGlobalConstantsParser.g:3925:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            {
            // InternalGlobalConstantsParser.g:3925:3: ()
            // InternalGlobalConstantsParser.g:3926:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3932:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( ((LA68_0>=B && LA68_0<=X)) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3933:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalGlobalConstantsParser.g:3933:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalGlobalConstantsParser.g:3934:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
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

            // InternalGlobalConstantsParser.g:3951:3: ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==RULE_INT) ) {
                alt69=1;
            }
            else if ( (LA69_0==LeftParenthesis) ) {
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
                    // InternalGlobalConstantsParser.g:3952:4: ( (lv_index_2_0= RULE_INT ) )
                    {
                    // InternalGlobalConstantsParser.g:3952:4: ( (lv_index_2_0= RULE_INT ) )
                    // InternalGlobalConstantsParser.g:3953:5: (lv_index_2_0= RULE_INT )
                    {
                    // InternalGlobalConstantsParser.g:3953:5: (lv_index_2_0= RULE_INT )
                    // InternalGlobalConstantsParser.g:3954:6: lv_index_2_0= RULE_INT
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
                    // InternalGlobalConstantsParser.g:3971:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3971:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    // InternalGlobalConstantsParser.g:3972:5: otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
                      				
                    }
                    // InternalGlobalConstantsParser.g:3976:5: ( (lv_expression_4_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:3977:6: (lv_expression_4_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:3977:6: (lv_expression_4_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:3978:7: lv_expression_4_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_57);
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
    // InternalGlobalConstantsParser.g:4005:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalGlobalConstantsParser.g:4005:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalGlobalConstantsParser.g:4006:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalGlobalConstantsParser.g:4012:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral | this_STEnumLiteral_6= ruleSTEnumLiteral ) ;
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
            // InternalGlobalConstantsParser.g:4018:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral | this_STEnumLiteral_6= ruleSTEnumLiteral ) )
            // InternalGlobalConstantsParser.g:4019:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral | this_STEnumLiteral_6= ruleSTEnumLiteral )
            {
            // InternalGlobalConstantsParser.g:4019:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral | this_STEnumLiteral_6= ruleSTEnumLiteral )
            int alt70=7;
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
                alt70=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt70=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt70=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt70=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt70=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt70=6;
                }
                break;
            case RULE_ID:
                {
                alt70=7;
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
                    // InternalGlobalConstantsParser.g:4020:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalGlobalConstantsParser.g:4029:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalGlobalConstantsParser.g:4038:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalGlobalConstantsParser.g:4047:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalGlobalConstantsParser.g:4056:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalGlobalConstantsParser.g:4065:3: this_STStringLiteral_5= ruleSTStringLiteral
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
                    // InternalGlobalConstantsParser.g:4074:3: this_STEnumLiteral_6= ruleSTEnumLiteral
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
    // InternalGlobalConstantsParser.g:4086:1: entryRuleSTSignedLiteralExpressions returns [EObject current=null] : iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF ;
    public final EObject entryRuleSTSignedLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignedLiteralExpressions = null;


        try {
            // InternalGlobalConstantsParser.g:4086:67: (iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF )
            // InternalGlobalConstantsParser.g:4087:2: iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF
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
    // InternalGlobalConstantsParser.g:4093:1: ruleSTSignedLiteralExpressions returns [EObject current=null] : this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral ;
    public final EObject ruleSTSignedLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject this_STSignedNumericLiteral_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4099:2: (this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral )
            // InternalGlobalConstantsParser.g:4100:2: this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral
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
    // InternalGlobalConstantsParser.g:4111:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:4111:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalGlobalConstantsParser.g:4112:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalGlobalConstantsParser.g:4118:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4124:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalGlobalConstantsParser.g:4125:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalGlobalConstantsParser.g:4125:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==DWORD||LA71_0==LWORD||(LA71_0>=BOOL && LA71_0<=BYTE)||LA71_0==WORD) ) {
                alt71=1;
            }
            else if ( (LA71_0==LREAL||(LA71_0>=UDINT && LA71_0<=ULINT)||LA71_0==USINT||LA71_0==DINT||LA71_0==LINT||(LA71_0>=REAL && LA71_0<=SINT)||LA71_0==UINT||LA71_0==INT) ) {
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
                    // InternalGlobalConstantsParser.g:4126:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalGlobalConstantsParser.g:4137:3: this_STAnyNumType_1= ruleSTAnyNumType
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
    // InternalGlobalConstantsParser.g:4151:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4151:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalGlobalConstantsParser.g:4152:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalGlobalConstantsParser.g:4158:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;

        AntlrDatatypeRuleToken lv_value_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4164:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) ) )
            // InternalGlobalConstantsParser.g:4165:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )
            {
            // InternalGlobalConstantsParser.g:4165:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )
            int alt73=2;
            alt73 = dfa73.predict(input);
            switch (alt73) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4166:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) )
                    {
                    // InternalGlobalConstantsParser.g:4166:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) )
                    // InternalGlobalConstantsParser.g:4167:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) )
                    {
                    // InternalGlobalConstantsParser.g:4167:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalGlobalConstantsParser.g:4168:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalGlobalConstantsParser.g:4168:5: ( ruleSTNumericLiteralType )
                    // InternalGlobalConstantsParser.g:4169:6: ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_25);
                    ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }
                    // InternalGlobalConstantsParser.g:4187:4: ( (lv_value_2_0= ruleSignedNumeric ) )
                    // InternalGlobalConstantsParser.g:4188:5: (lv_value_2_0= ruleSignedNumeric )
                    {
                    // InternalGlobalConstantsParser.g:4188:5: (lv_value_2_0= ruleSignedNumeric )
                    // InternalGlobalConstantsParser.g:4189:6: lv_value_2_0= ruleSignedNumeric
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
                    // InternalGlobalConstantsParser.g:4208:3: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) )
                    {
                    // InternalGlobalConstantsParser.g:4208:3: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) )
                    // InternalGlobalConstantsParser.g:4209:4: ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) )
                    {
                    // InternalGlobalConstantsParser.g:4209:4: ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==DWORD||LA72_0==LREAL||LA72_0==LWORD||(LA72_0>=UDINT && LA72_0<=ULINT)||LA72_0==USINT||(LA72_0>=BOOL && LA72_0<=BYTE)||LA72_0==DINT||LA72_0==LINT||(LA72_0>=REAL && LA72_0<=SINT)||LA72_0==UINT||LA72_0==WORD||LA72_0==INT) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:4210:5: ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign
                            {
                            // InternalGlobalConstantsParser.g:4210:5: ( ( ruleSTNumericLiteralType ) )
                            // InternalGlobalConstantsParser.g:4211:6: ( ruleSTNumericLiteralType )
                            {
                            // InternalGlobalConstantsParser.g:4211:6: ( ruleSTNumericLiteralType )
                            // InternalGlobalConstantsParser.g:4212:7: ruleSTNumericLiteralType
                            {
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                              							}
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_1_0_0_0());
                              						
                            }
                            pushFollow(FOLLOW_25);
                            ruleSTNumericLiteralType();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_4=(Token)match(input,NumberSign,FOLLOW_62); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_1_0_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalGlobalConstantsParser.g:4231:4: ( (lv_value_5_0= ruleNumeric ) )
                    // InternalGlobalConstantsParser.g:4232:5: (lv_value_5_0= ruleNumeric )
                    {
                    // InternalGlobalConstantsParser.g:4232:5: (lv_value_5_0= ruleNumeric )
                    // InternalGlobalConstantsParser.g:4233:6: lv_value_5_0= ruleNumeric
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
    // InternalGlobalConstantsParser.g:4255:1: entryRuleSTSignedNumericLiteral returns [EObject current=null] : iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF ;
    public final EObject entryRuleSTSignedNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignedNumericLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4255:63: (iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF )
            // InternalGlobalConstantsParser.g:4256:2: iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF
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
    // InternalGlobalConstantsParser.g:4262:1: ruleSTSignedNumericLiteral returns [EObject current=null] : ( (lv_value_0_0= ruleSignedNumeric ) ) ;
    public final EObject ruleSTSignedNumericLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4268:2: ( ( (lv_value_0_0= ruleSignedNumeric ) ) )
            // InternalGlobalConstantsParser.g:4269:2: ( (lv_value_0_0= ruleSignedNumeric ) )
            {
            // InternalGlobalConstantsParser.g:4269:2: ( (lv_value_0_0= ruleSignedNumeric ) )
            // InternalGlobalConstantsParser.g:4270:3: (lv_value_0_0= ruleSignedNumeric )
            {
            // InternalGlobalConstantsParser.g:4270:3: (lv_value_0_0= ruleSignedNumeric )
            // InternalGlobalConstantsParser.g:4271:4: lv_value_0_0= ruleSignedNumeric
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
    // InternalGlobalConstantsParser.g:4291:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:4291:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalGlobalConstantsParser.g:4292:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalGlobalConstantsParser.g:4298:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4304:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalGlobalConstantsParser.g:4305:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalGlobalConstantsParser.g:4305:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt74=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt74=1;
                }
                break;
            case D:
                {
                alt74=2;
                }
                break;
            case LD:
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
                    // InternalGlobalConstantsParser.g:4306:3: this_STDateType_0= ruleSTDateType
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
                    // InternalGlobalConstantsParser.g:4317:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4323:3: kw= LD
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
    // InternalGlobalConstantsParser.g:4332:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4332:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalGlobalConstantsParser.g:4333:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalGlobalConstantsParser.g:4339:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4345:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalGlobalConstantsParser.g:4346:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalGlobalConstantsParser.g:4346:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalGlobalConstantsParser.g:4347:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalGlobalConstantsParser.g:4347:3: ( ( ruleSTDateLiteralType ) )
            // InternalGlobalConstantsParser.g:4348:4: ( ruleSTDateLiteralType )
            {
            // InternalGlobalConstantsParser.g:4348:4: ( ruleSTDateLiteralType )
            // InternalGlobalConstantsParser.g:4349:5: ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_25);
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
            // InternalGlobalConstantsParser.g:4367:3: ( (lv_value_2_0= ruleDate ) )
            // InternalGlobalConstantsParser.g:4368:4: (lv_value_2_0= ruleDate )
            {
            // InternalGlobalConstantsParser.g:4368:4: (lv_value_2_0= ruleDate )
            // InternalGlobalConstantsParser.g:4369:5: lv_value_2_0= ruleDate
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
    // InternalGlobalConstantsParser.g:4390:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:4390:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalGlobalConstantsParser.g:4391:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalGlobalConstantsParser.g:4397:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4403:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalGlobalConstantsParser.g:4404:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalGlobalConstantsParser.g:4404:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt75=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt75=1;
                }
                break;
            case T:
                {
                alt75=2;
                }
                break;
            case LT:
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
                    // InternalGlobalConstantsParser.g:4405:3: this_STAnyDurationType_0= ruleSTAnyDurationType
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
                    // InternalGlobalConstantsParser.g:4416:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4422:3: kw= LT
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
    // InternalGlobalConstantsParser.g:4431:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4431:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalGlobalConstantsParser.g:4432:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalGlobalConstantsParser.g:4438:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4444:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalGlobalConstantsParser.g:4445:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalGlobalConstantsParser.g:4445:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalGlobalConstantsParser.g:4446:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalGlobalConstantsParser.g:4446:3: ( ( ruleSTTimeLiteralType ) )
            // InternalGlobalConstantsParser.g:4447:4: ( ruleSTTimeLiteralType )
            {
            // InternalGlobalConstantsParser.g:4447:4: ( ruleSTTimeLiteralType )
            // InternalGlobalConstantsParser.g:4448:5: ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_25);
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
            // InternalGlobalConstantsParser.g:4466:3: ( (lv_value_2_0= ruleTime ) )
            // InternalGlobalConstantsParser.g:4467:4: (lv_value_2_0= ruleTime )
            {
            // InternalGlobalConstantsParser.g:4467:4: (lv_value_2_0= ruleTime )
            // InternalGlobalConstantsParser.g:4468:5: lv_value_2_0= ruleTime
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
    // InternalGlobalConstantsParser.g:4489:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4489:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalGlobalConstantsParser.g:4490:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalGlobalConstantsParser.g:4496:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4502:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalGlobalConstantsParser.g:4503:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalGlobalConstantsParser.g:4503:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalGlobalConstantsParser.g:4504:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalGlobalConstantsParser.g:4504:3: ( ( ruleSTTimeOfDayType ) )
            // InternalGlobalConstantsParser.g:4505:4: ( ruleSTTimeOfDayType )
            {
            // InternalGlobalConstantsParser.g:4505:4: ( ruleSTTimeOfDayType )
            // InternalGlobalConstantsParser.g:4506:5: ruleSTTimeOfDayType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_25);
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
            // InternalGlobalConstantsParser.g:4524:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalGlobalConstantsParser.g:4525:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalGlobalConstantsParser.g:4525:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalGlobalConstantsParser.g:4526:5: lv_value_2_0= ruleTimeOfDay
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
    // InternalGlobalConstantsParser.g:4547:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4547:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalGlobalConstantsParser.g:4548:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalGlobalConstantsParser.g:4554:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4560:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalGlobalConstantsParser.g:4561:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalGlobalConstantsParser.g:4561:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalGlobalConstantsParser.g:4562:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalGlobalConstantsParser.g:4562:3: ( ( ruleSTDateAndTimeType ) )
            // InternalGlobalConstantsParser.g:4563:4: ( ruleSTDateAndTimeType )
            {
            // InternalGlobalConstantsParser.g:4563:4: ( ruleSTDateAndTimeType )
            // InternalGlobalConstantsParser.g:4564:5: ruleSTDateAndTimeType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_25);
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
            // InternalGlobalConstantsParser.g:4582:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalGlobalConstantsParser.g:4583:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalGlobalConstantsParser.g:4583:4: (lv_value_2_0= ruleDateAndTime )
            // InternalGlobalConstantsParser.g:4584:5: lv_value_2_0= ruleDateAndTime
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
    // InternalGlobalConstantsParser.g:4605:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4605:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalGlobalConstantsParser.g:4606:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalGlobalConstantsParser.g:4612:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4618:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) )
            // InternalGlobalConstantsParser.g:4619:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            {
            // InternalGlobalConstantsParser.g:4619:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            // InternalGlobalConstantsParser.g:4620:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) )
            {
            // InternalGlobalConstantsParser.g:4620:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==WSTRING||LA76_0==STRING||LA76_0==WCHAR||LA76_0==CHAR) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4621:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalGlobalConstantsParser.g:4621:4: ( ( ruleSTAnyCharsType ) )
                    // InternalGlobalConstantsParser.g:4622:5: ( ruleSTAnyCharsType )
                    {
                    // InternalGlobalConstantsParser.g:4622:5: ( ruleSTAnyCharsType )
                    // InternalGlobalConstantsParser.g:4623:6: ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_25);
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

            // InternalGlobalConstantsParser.g:4642:3: ( (lv_value_2_0= RULE_STRING ) )
            // InternalGlobalConstantsParser.g:4643:4: (lv_value_2_0= RULE_STRING )
            {
            // InternalGlobalConstantsParser.g:4643:4: (lv_value_2_0= RULE_STRING )
            // InternalGlobalConstantsParser.g:4644:5: lv_value_2_0= RULE_STRING
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
    // InternalGlobalConstantsParser.g:4664:1: entryRuleSTEnumLiteral returns [EObject current=null] : iv_ruleSTEnumLiteral= ruleSTEnumLiteral EOF ;
    public final EObject entryRuleSTEnumLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEnumLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4664:54: (iv_ruleSTEnumLiteral= ruleSTEnumLiteral EOF )
            // InternalGlobalConstantsParser.g:4665:2: iv_ruleSTEnumLiteral= ruleSTEnumLiteral EOF
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
    // InternalGlobalConstantsParser.g:4671:1: ruleSTEnumLiteral returns [EObject current=null] : ( ( ruleEnumValue ) ) ;
    public final EObject ruleSTEnumLiteral() throws RecognitionException {
        EObject current = null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4677:2: ( ( ( ruleEnumValue ) ) )
            // InternalGlobalConstantsParser.g:4678:2: ( ( ruleEnumValue ) )
            {
            // InternalGlobalConstantsParser.g:4678:2: ( ( ruleEnumValue ) )
            // InternalGlobalConstantsParser.g:4679:3: ( ruleEnumValue )
            {
            // InternalGlobalConstantsParser.g:4679:3: ( ruleEnumValue )
            // InternalGlobalConstantsParser.g:4680:4: ruleEnumValue
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
    // InternalGlobalConstantsParser.g:4697:1: entryRuleEnumValue returns [String current=null] : iv_ruleEnumValue= ruleEnumValue EOF ;
    public final String entryRuleEnumValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEnumValue = null;


        try {
            // InternalGlobalConstantsParser.g:4697:49: (iv_ruleEnumValue= ruleEnumValue EOF )
            // InternalGlobalConstantsParser.g:4698:2: iv_ruleEnumValue= ruleEnumValue EOF
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
    // InternalGlobalConstantsParser.g:4704:1: ruleEnumValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID ) ;
    public final AntlrDatatypeRuleToken ruleEnumValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_ID_2=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4710:2: ( (this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID ) )
            // InternalGlobalConstantsParser.g:4711:2: (this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:4711:2: (this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID )
            // InternalGlobalConstantsParser.g:4712:3: this_QualifiedName_0= ruleQualifiedName kw= NumberSign this_ID_2= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getEnumValueAccess().getQualifiedNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_25);
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
    // InternalGlobalConstantsParser.g:4738:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalGlobalConstantsParser.g:4738:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalGlobalConstantsParser.g:4739:2: iv_ruleSTAnyType= ruleSTAnyType EOF
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
    // InternalGlobalConstantsParser.g:4745:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_QualifiedName_0 = null;

        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4751:2: ( (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalGlobalConstantsParser.g:4752:2: (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalGlobalConstantsParser.g:4752:2: (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==RULE_ID) ) {
                alt77=1;
            }
            else if ( (LA77_0==LDATE_AND_TIME||LA77_0==DATE_AND_TIME||LA77_0==LTIME_OF_DAY||LA77_0==TIME_OF_DAY||LA77_0==WSTRING||LA77_0==STRING||LA77_0==DWORD||(LA77_0>=LDATE && LA77_0<=LWORD)||(LA77_0>=UDINT && LA77_0<=ULINT)||(LA77_0>=USINT && LA77_0<=WCHAR)||(LA77_0>=BOOL && LA77_0<=BYTE)||(LA77_0>=CHAR && LA77_0<=DINT)||(LA77_0>=LINT && LA77_0<=LTOD)||(LA77_0>=REAL && LA77_0<=SINT)||LA77_0==TIME||LA77_0==UINT||LA77_0==WORD||(LA77_0>=INT && LA77_0<=LDT)||LA77_0==TOD||LA77_0==DT) ) {
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
                    // InternalGlobalConstantsParser.g:4753:3: this_QualifiedName_0= ruleQualifiedName
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
                    // InternalGlobalConstantsParser.g:4764:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
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
    // InternalGlobalConstantsParser.g:4778:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalGlobalConstantsParser.g:4778:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalGlobalConstantsParser.g:4779:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
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
    // InternalGlobalConstantsParser.g:4785:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4791:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalGlobalConstantsParser.g:4792:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalGlobalConstantsParser.g:4792:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt78=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt78=1;
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
                alt78=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt78=3;
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
                alt78=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
                {
                alt78=5;
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
                    // InternalGlobalConstantsParser.g:4793:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalGlobalConstantsParser.g:4804:3: this_STAnyNumType_1= ruleSTAnyNumType
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
                    // InternalGlobalConstantsParser.g:4815:3: this_STAnyDurationType_2= ruleSTAnyDurationType
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
                    // InternalGlobalConstantsParser.g:4826:3: this_STAnyDateType_3= ruleSTAnyDateType
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
                    // InternalGlobalConstantsParser.g:4837:3: this_STAnyCharsType_4= ruleSTAnyCharsType
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
    // InternalGlobalConstantsParser.g:4851:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalGlobalConstantsParser.g:4851:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalGlobalConstantsParser.g:4852:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
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
    // InternalGlobalConstantsParser.g:4858:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4864:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalGlobalConstantsParser.g:4865:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalGlobalConstantsParser.g:4865:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt79=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt79=1;
                }
                break;
            case BYTE:
                {
                alt79=2;
                }
                break;
            case WORD:
                {
                alt79=3;
                }
                break;
            case DWORD:
                {
                alt79=4;
                }
                break;
            case LWORD:
                {
                alt79=5;
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
                    // InternalGlobalConstantsParser.g:4866:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4872:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4878:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4884:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:4890:3: kw= LWORD
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
    // InternalGlobalConstantsParser.g:4899:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalGlobalConstantsParser.g:4899:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalGlobalConstantsParser.g:4900:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
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
    // InternalGlobalConstantsParser.g:4906:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4912:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalGlobalConstantsParser.g:4913:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalGlobalConstantsParser.g:4913:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt80=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt80=1;
                }
                break;
            case INT:
                {
                alt80=2;
                }
                break;
            case DINT:
                {
                alt80=3;
                }
                break;
            case LINT:
                {
                alt80=4;
                }
                break;
            case USINT:
                {
                alt80=5;
                }
                break;
            case UINT:
                {
                alt80=6;
                }
                break;
            case UDINT:
                {
                alt80=7;
                }
                break;
            case ULINT:
                {
                alt80=8;
                }
                break;
            case REAL:
                {
                alt80=9;
                }
                break;
            case LREAL:
                {
                alt80=10;
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
                    // InternalGlobalConstantsParser.g:4914:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4920:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4926:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4932:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:4938:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsParser.g:4944:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsParser.g:4950:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGlobalConstantsParser.g:4956:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGlobalConstantsParser.g:4962:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalGlobalConstantsParser.g:4968:3: kw= LREAL
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
    // InternalGlobalConstantsParser.g:4977:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalGlobalConstantsParser.g:4977:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalGlobalConstantsParser.g:4978:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
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
    // InternalGlobalConstantsParser.g:4984:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4990:2: ( (kw= TIME | kw= LTIME ) )
            // InternalGlobalConstantsParser.g:4991:2: (kw= TIME | kw= LTIME )
            {
            // InternalGlobalConstantsParser.g:4991:2: (kw= TIME | kw= LTIME )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==TIME) ) {
                alt81=1;
            }
            else if ( (LA81_0==LTIME) ) {
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
                    // InternalGlobalConstantsParser.g:4992:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4998:3: kw= LTIME
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
    // InternalGlobalConstantsParser.g:5007:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalGlobalConstantsParser.g:5007:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalGlobalConstantsParser.g:5008:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
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
    // InternalGlobalConstantsParser.g:5014:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5020:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalGlobalConstantsParser.g:5021:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalGlobalConstantsParser.g:5021:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt82=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt82=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt82=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
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
                    // InternalGlobalConstantsParser.g:5022:3: this_STDateType_0= ruleSTDateType
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
                    // InternalGlobalConstantsParser.g:5033:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
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
                    // InternalGlobalConstantsParser.g:5044:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
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
    // InternalGlobalConstantsParser.g:5058:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalGlobalConstantsParser.g:5058:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalGlobalConstantsParser.g:5059:2: iv_ruleSTDateType= ruleSTDateType EOF
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
    // InternalGlobalConstantsParser.g:5065:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5071:2: ( (kw= DATE | kw= LDATE ) )
            // InternalGlobalConstantsParser.g:5072:2: (kw= DATE | kw= LDATE )
            {
            // InternalGlobalConstantsParser.g:5072:2: (kw= DATE | kw= LDATE )
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==DATE) ) {
                alt83=1;
            }
            else if ( (LA83_0==LDATE) ) {
                alt83=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }
            switch (alt83) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5073:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5079:3: kw= LDATE
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
    // InternalGlobalConstantsParser.g:5088:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalGlobalConstantsParser.g:5088:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalGlobalConstantsParser.g:5089:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
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
    // InternalGlobalConstantsParser.g:5095:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5101:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalGlobalConstantsParser.g:5102:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalGlobalConstantsParser.g:5102:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt84=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt84=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt84=2;
                }
                break;
            case TOD:
                {
                alt84=3;
                }
                break;
            case LTOD:
                {
                alt84=4;
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
                    // InternalGlobalConstantsParser.g:5103:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5109:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:5115:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:5121:3: kw= LTOD
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
    // InternalGlobalConstantsParser.g:5130:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalGlobalConstantsParser.g:5130:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalGlobalConstantsParser.g:5131:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
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
    // InternalGlobalConstantsParser.g:5137:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5143:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalGlobalConstantsParser.g:5144:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalGlobalConstantsParser.g:5144:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt85=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt85=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt85=2;
                }
                break;
            case DT:
                {
                alt85=3;
                }
                break;
            case LDT:
                {
                alt85=4;
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
                    // InternalGlobalConstantsParser.g:5145:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5151:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:5157:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:5163:3: kw= LDT
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
    // InternalGlobalConstantsParser.g:5172:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalGlobalConstantsParser.g:5172:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalGlobalConstantsParser.g:5173:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
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
    // InternalGlobalConstantsParser.g:5179:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5185:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalGlobalConstantsParser.g:5186:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalGlobalConstantsParser.g:5186:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt86=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt86=1;
                }
                break;
            case WSTRING:
                {
                alt86=2;
                }
                break;
            case CHAR:
                {
                alt86=3;
                }
                break;
            case WCHAR:
                {
                alt86=4;
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
                    // InternalGlobalConstantsParser.g:5187:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5193:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:5199:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:5205:3: kw= WCHAR
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
    // InternalGlobalConstantsParser.g:5214:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalGlobalConstantsParser.g:5214:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalGlobalConstantsParser.g:5215:2: iv_ruleQualifiedName= ruleQualifiedName EOF
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
    // InternalGlobalConstantsParser.g:5221:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5227:2: ( (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* ) )
            // InternalGlobalConstantsParser.g:5228:2: (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* )
            {
            // InternalGlobalConstantsParser.g:5228:2: (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* )
            // InternalGlobalConstantsParser.g:5229:3: this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_66); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0());
              		
            }
            // InternalGlobalConstantsParser.g:5236:3: (kw= ColonColon this_ID_2= RULE_ID )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==ColonColon) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:5237:4: kw= ColonColon this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,ColonColon,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getColonColonKeyword_1_0());
            	      			
            	    }
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_66); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop87;
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
    // InternalGlobalConstantsParser.g:5254:1: entryRuleQualifiedNameWithWildcard returns [String current=null] : iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF ;
    public final String entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildcard = null;


        try {
            // InternalGlobalConstantsParser.g:5254:65: (iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF )
            // InternalGlobalConstantsParser.g:5255:2: iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF
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
    // InternalGlobalConstantsParser.g:5261:1: ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildcard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5267:2: ( (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? ) )
            // InternalGlobalConstantsParser.g:5268:2: (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? )
            {
            // InternalGlobalConstantsParser.g:5268:2: (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? )
            // InternalGlobalConstantsParser.g:5269:3: this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_67);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_QualifiedName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:5279:3: (kw= ColonColonAsterisk )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==ColonColonAsterisk) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5280:4: kw= ColonColonAsterisk
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
    // InternalGlobalConstantsParser.g:5290:1: entryRuleNumeric returns [String current=null] : iv_ruleNumeric= ruleNumeric EOF ;
    public final String entryRuleNumeric() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumeric = null;


        try {
            // InternalGlobalConstantsParser.g:5290:47: (iv_ruleNumeric= ruleNumeric EOF )
            // InternalGlobalConstantsParser.g:5291:2: iv_ruleNumeric= ruleNumeric EOF
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
    // InternalGlobalConstantsParser.g:5297:1: ruleNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) ;
    public final AntlrDatatypeRuleToken ruleNumeric() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_NON_DECIMAL_3=null;
        AntlrDatatypeRuleToken this_Number_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5303:2: ( (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) )
            // InternalGlobalConstantsParser.g:5304:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            {
            // InternalGlobalConstantsParser.g:5304:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            int alt89=4;
            switch ( input.LA(1) ) {
            case TRUE:
                {
                alt89=1;
                }
                break;
            case FALSE:
                {
                alt89=2;
                }
                break;
            case RULE_INT:
                {
                alt89=3;
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt89=4;
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
                    // InternalGlobalConstantsParser.g:5305:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5311:3: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getFALSEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:5317:3: this_Number_2= ruleNumber
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
                    // InternalGlobalConstantsParser.g:5328:3: this_NON_DECIMAL_3= RULE_NON_DECIMAL
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
    // InternalGlobalConstantsParser.g:5339:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5341:2: (iv_ruleNumber= ruleNumber EOF )
            // InternalGlobalConstantsParser.g:5342:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalGlobalConstantsParser.g:5351:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_DECIMAL_3=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5358:2: ( (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? ) )
            // InternalGlobalConstantsParser.g:5359:2: (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? )
            {
            // InternalGlobalConstantsParser.g:5359:2: (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? )
            // InternalGlobalConstantsParser.g:5360:3: this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalGlobalConstantsParser.g:5367:3: (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==FullStop) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5368:4: kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL )
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:5373:4: (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL )
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==RULE_INT) ) {
                        alt90=1;
                    }
                    else if ( (LA90_0==RULE_DECIMAL) ) {
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
                            // InternalGlobalConstantsParser.g:5374:5: this_INT_2= RULE_INT
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
                            // InternalGlobalConstantsParser.g:5382:5: this_DECIMAL_3= RULE_DECIMAL
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
    // InternalGlobalConstantsParser.g:5398:1: entryRuleSignedNumeric returns [String current=null] : iv_ruleSignedNumeric= ruleSignedNumeric EOF ;
    public final String entryRuleSignedNumeric() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSignedNumeric = null;


        try {
            // InternalGlobalConstantsParser.g:5398:53: (iv_ruleSignedNumeric= ruleSignedNumeric EOF )
            // InternalGlobalConstantsParser.g:5399:2: iv_ruleSignedNumeric= ruleSignedNumeric EOF
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
    // InternalGlobalConstantsParser.g:5405:1: ruleSignedNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_SignedNumber_0= ruleSignedNumber ;
    public final AntlrDatatypeRuleToken ruleSignedNumeric() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_SignedNumber_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5411:2: (this_SignedNumber_0= ruleSignedNumber )
            // InternalGlobalConstantsParser.g:5412:2: this_SignedNumber_0= ruleSignedNumber
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
    // InternalGlobalConstantsParser.g:5425:1: entryRuleSignedNumber returns [String current=null] : iv_ruleSignedNumber= ruleSignedNumber EOF ;
    public final String entryRuleSignedNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSignedNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5427:2: (iv_ruleSignedNumber= ruleSignedNumber EOF )
            // InternalGlobalConstantsParser.g:5428:2: iv_ruleSignedNumber= ruleSignedNumber EOF
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
    // InternalGlobalConstantsParser.g:5437:1: ruleSignedNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) ;
    public final AntlrDatatypeRuleToken ruleSignedNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_DECIMAL_5=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5444:2: ( ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) )
            // InternalGlobalConstantsParser.g:5445:2: ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? )
            {
            // InternalGlobalConstantsParser.g:5445:2: ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? )
            // InternalGlobalConstantsParser.g:5446:3: (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )?
            {
            // InternalGlobalConstantsParser.g:5446:3: (kw= PlusSign | kw= HyphenMinus )
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
                    // InternalGlobalConstantsParser.g:5447:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5453:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_1());
              		
            }
            // InternalGlobalConstantsParser.g:5466:3: (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==FullStop) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5467:4: kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL )
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getFullStopKeyword_2_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:5472:4: (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL )
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==RULE_INT) ) {
                        alt93=1;
                    }
                    else if ( (LA93_0==RULE_DECIMAL) ) {
                        alt93=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 93, 0, input);

                        throw nvae;
                    }
                    switch (alt93) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:5473:5: this_INT_4= RULE_INT
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
                            // InternalGlobalConstantsParser.g:5481:5: this_DECIMAL_5= RULE_DECIMAL
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
    // InternalGlobalConstantsParser.g:5497:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5499:2: (iv_ruleTime= ruleTime EOF )
            // InternalGlobalConstantsParser.g:5500:2: iv_ruleTime= ruleTime EOF
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
    // InternalGlobalConstantsParser.g:5509:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_TIME_VALUE_2=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5516:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) )
            // InternalGlobalConstantsParser.g:5517:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            {
            // InternalGlobalConstantsParser.g:5517:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            // InternalGlobalConstantsParser.g:5518:3: (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE
            {
            // InternalGlobalConstantsParser.g:5518:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt95=3;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==PlusSign) ) {
                alt95=1;
            }
            else if ( (LA95_0==HyphenMinus) ) {
                alt95=2;
            }
            switch (alt95) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5519:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTimeAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5525:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_70); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:5545:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalGlobalConstantsParser.g:5545:44: (iv_ruleDate= ruleDate EOF )
            // InternalGlobalConstantsParser.g:5546:2: iv_ruleDate= ruleDate EOF
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
    // InternalGlobalConstantsParser.g:5552:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5558:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalGlobalConstantsParser.g:5559:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalGlobalConstantsParser.g:5559:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalGlobalConstantsParser.g:5560:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
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
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:5595:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalGlobalConstantsParser.g:5595:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalGlobalConstantsParser.g:5596:2: iv_ruleDateAndTime= ruleDateAndTime EOF
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
    // InternalGlobalConstantsParser.g:5602:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? ) ;
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
            // InternalGlobalConstantsParser.g:5608:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? ) )
            // InternalGlobalConstantsParser.g:5609:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? )
            {
            // InternalGlobalConstantsParser.g:5609:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? )
            // InternalGlobalConstantsParser.g:5610:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
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
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
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
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
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
            this_INT_6=(Token)match(input,RULE_INT,FOLLOW_10); if (state.failed) return current;
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
            this_INT_8=(Token)match(input,RULE_INT,FOLLOW_10); if (state.failed) return current;
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
            this_INT_10=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_10);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
              		
            }
            // InternalGlobalConstantsParser.g:5677:3: (kw= FullStop this_INT_12= RULE_INT )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==FullStop) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5678:4: kw= FullStop this_INT_12= RULE_INT
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_63); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:5695:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalGlobalConstantsParser.g:5695:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalGlobalConstantsParser.g:5696:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalGlobalConstantsParser.g:5702:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5708:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) )
            // InternalGlobalConstantsParser.g:5709:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            {
            // InternalGlobalConstantsParser.g:5709:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            // InternalGlobalConstantsParser.g:5710:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_10); if (state.failed) return current;
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
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_10); if (state.failed) return current;
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
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalGlobalConstantsParser.g:5741:3: (kw= FullStop this_INT_6= RULE_INT )?
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==FullStop) ) {
                alt97=1;
            }
            switch (alt97) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5742:4: kw= FullStop this_INT_6= RULE_INT
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_63); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:5759:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5765:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalGlobalConstantsParser.g:5766:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalGlobalConstantsParser.g:5766:2: (enumLiteral_0= FullStopFullStop )
            // InternalGlobalConstantsParser.g:5767:3: enumLiteral_0= FullStopFullStop
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
    // InternalGlobalConstantsParser.g:5776:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5782:2: ( (enumLiteral_0= OR ) )
            // InternalGlobalConstantsParser.g:5783:2: (enumLiteral_0= OR )
            {
            // InternalGlobalConstantsParser.g:5783:2: (enumLiteral_0= OR )
            // InternalGlobalConstantsParser.g:5784:3: enumLiteral_0= OR
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
    // InternalGlobalConstantsParser.g:5793:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5799:2: ( (enumLiteral_0= XOR ) )
            // InternalGlobalConstantsParser.g:5800:2: (enumLiteral_0= XOR )
            {
            // InternalGlobalConstantsParser.g:5800:2: (enumLiteral_0= XOR )
            // InternalGlobalConstantsParser.g:5801:3: enumLiteral_0= XOR
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
    // InternalGlobalConstantsParser.g:5810:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5816:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalGlobalConstantsParser.g:5817:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalGlobalConstantsParser.g:5817:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==AND) ) {
                alt98=1;
            }
            else if ( (LA98_0==Ampersand) ) {
                alt98=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }
            switch (alt98) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5818:3: (enumLiteral_0= AND )
                    {
                    // InternalGlobalConstantsParser.g:5818:3: (enumLiteral_0= AND )
                    // InternalGlobalConstantsParser.g:5819:4: enumLiteral_0= AND
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
                    // InternalGlobalConstantsParser.g:5826:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalGlobalConstantsParser.g:5826:3: (enumLiteral_1= Ampersand )
                    // InternalGlobalConstantsParser.g:5827:4: enumLiteral_1= Ampersand
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
    // InternalGlobalConstantsParser.g:5837:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5843:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalGlobalConstantsParser.g:5844:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalGlobalConstantsParser.g:5844:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==EqualsSign) ) {
                alt99=1;
            }
            else if ( (LA99_0==LessThanSignGreaterThanSign) ) {
                alt99=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }
            switch (alt99) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5845:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5845:3: (enumLiteral_0= EqualsSign )
                    // InternalGlobalConstantsParser.g:5846:4: enumLiteral_0= EqualsSign
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
                    // InternalGlobalConstantsParser.g:5853:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5853:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalGlobalConstantsParser.g:5854:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalGlobalConstantsParser.g:5864:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5870:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalGlobalConstantsParser.g:5871:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalGlobalConstantsParser.g:5871:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt100=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt100=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt100=2;
                }
                break;
            case GreaterThanSign:
                {
                alt100=3;
                }
                break;
            case GreaterThanSignEqualsSign:
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
                    // InternalGlobalConstantsParser.g:5872:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5872:3: (enumLiteral_0= LessThanSign )
                    // InternalGlobalConstantsParser.g:5873:4: enumLiteral_0= LessThanSign
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
                    // InternalGlobalConstantsParser.g:5880:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5880:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalGlobalConstantsParser.g:5881:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalGlobalConstantsParser.g:5888:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5888:3: (enumLiteral_2= GreaterThanSign )
                    // InternalGlobalConstantsParser.g:5889:4: enumLiteral_2= GreaterThanSign
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
                    // InternalGlobalConstantsParser.g:5896:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5896:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalGlobalConstantsParser.g:5897:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalGlobalConstantsParser.g:5907:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5913:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalGlobalConstantsParser.g:5914:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalGlobalConstantsParser.g:5914:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==PlusSign) ) {
                alt101=1;
            }
            else if ( (LA101_0==HyphenMinus) ) {
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
                    // InternalGlobalConstantsParser.g:5915:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalGlobalConstantsParser.g:5915:3: (enumLiteral_0= PlusSign )
                    // InternalGlobalConstantsParser.g:5916:4: enumLiteral_0= PlusSign
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
                    // InternalGlobalConstantsParser.g:5923:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalGlobalConstantsParser.g:5923:3: (enumLiteral_1= HyphenMinus )
                    // InternalGlobalConstantsParser.g:5924:4: enumLiteral_1= HyphenMinus
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
    // InternalGlobalConstantsParser.g:5934:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5940:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalGlobalConstantsParser.g:5941:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalGlobalConstantsParser.g:5941:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt102=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt102=1;
                }
                break;
            case Solidus:
                {
                alt102=2;
                }
                break;
            case MOD:
                {
                alt102=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }

            switch (alt102) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5942:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalGlobalConstantsParser.g:5942:3: (enumLiteral_0= Asterisk )
                    // InternalGlobalConstantsParser.g:5943:4: enumLiteral_0= Asterisk
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
                    // InternalGlobalConstantsParser.g:5950:3: (enumLiteral_1= Solidus )
                    {
                    // InternalGlobalConstantsParser.g:5950:3: (enumLiteral_1= Solidus )
                    // InternalGlobalConstantsParser.g:5951:4: enumLiteral_1= Solidus
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
                    // InternalGlobalConstantsParser.g:5958:3: (enumLiteral_2= MOD )
                    {
                    // InternalGlobalConstantsParser.g:5958:3: (enumLiteral_2= MOD )
                    // InternalGlobalConstantsParser.g:5959:4: enumLiteral_2= MOD
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
    // InternalGlobalConstantsParser.g:5969:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5975:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalGlobalConstantsParser.g:5976:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalGlobalConstantsParser.g:5976:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalGlobalConstantsParser.g:5977:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalGlobalConstantsParser.g:5986:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5992:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalGlobalConstantsParser.g:5993:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalGlobalConstantsParser.g:5993:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt103=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt103=1;
                }
                break;
            case PlusSign:
                {
                alt103=2;
                }
                break;
            case NOT:
                {
                alt103=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }

            switch (alt103) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5994:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalGlobalConstantsParser.g:5994:3: (enumLiteral_0= HyphenMinus )
                    // InternalGlobalConstantsParser.g:5995:4: enumLiteral_0= HyphenMinus
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
                    // InternalGlobalConstantsParser.g:6002:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalGlobalConstantsParser.g:6002:3: (enumLiteral_1= PlusSign )
                    // InternalGlobalConstantsParser.g:6003:4: enumLiteral_1= PlusSign
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
                    // InternalGlobalConstantsParser.g:6010:3: (enumLiteral_2= NOT )
                    {
                    // InternalGlobalConstantsParser.g:6010:3: (enumLiteral_2= NOT )
                    // InternalGlobalConstantsParser.g:6011:4: enumLiteral_2= NOT
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
    // InternalGlobalConstantsParser.g:6021:1: ruleSTBuiltinFeature returns [Enumerator current=null] : (enumLiteral_0= THIS ) ;
    public final Enumerator ruleSTBuiltinFeature() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:6027:2: ( (enumLiteral_0= THIS ) )
            // InternalGlobalConstantsParser.g:6028:2: (enumLiteral_0= THIS )
            {
            // InternalGlobalConstantsParser.g:6028:2: (enumLiteral_0= THIS )
            // InternalGlobalConstantsParser.g:6029:3: enumLiteral_0= THIS
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
    // InternalGlobalConstantsParser.g:6038:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:6044:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalGlobalConstantsParser.g:6045:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalGlobalConstantsParser.g:6045:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt104=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt104=1;
                }
                break;
            case D_1:
                {
                alt104=2;
                }
                break;
            case W:
                {
                alt104=3;
                }
                break;
            case B:
                {
                alt104=4;
                }
                break;
            case X:
                {
                alt104=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }

            switch (alt104) {
                case 1 :
                    // InternalGlobalConstantsParser.g:6046:3: (enumLiteral_0= L )
                    {
                    // InternalGlobalConstantsParser.g:6046:3: (enumLiteral_0= L )
                    // InternalGlobalConstantsParser.g:6047:4: enumLiteral_0= L
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
                    // InternalGlobalConstantsParser.g:6054:3: (enumLiteral_1= D_1 )
                    {
                    // InternalGlobalConstantsParser.g:6054:3: (enumLiteral_1= D_1 )
                    // InternalGlobalConstantsParser.g:6055:4: enumLiteral_1= D_1
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
                    // InternalGlobalConstantsParser.g:6062:3: (enumLiteral_2= W )
                    {
                    // InternalGlobalConstantsParser.g:6062:3: (enumLiteral_2= W )
                    // InternalGlobalConstantsParser.g:6063:4: enumLiteral_2= W
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
                    // InternalGlobalConstantsParser.g:6070:3: (enumLiteral_3= B )
                    {
                    // InternalGlobalConstantsParser.g:6070:3: (enumLiteral_3= B )
                    // InternalGlobalConstantsParser.g:6071:4: enumLiteral_3= B
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
                    // InternalGlobalConstantsParser.g:6078:3: (enumLiteral_4= X )
                    {
                    // InternalGlobalConstantsParser.g:6078:3: (enumLiteral_4= X )
                    // InternalGlobalConstantsParser.g:6079:4: enumLiteral_4= X
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

    // $ANTLR start synpred1_InternalGlobalConstantsParser
    public final void synpred1_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:2260:4: ( ( ruleSTStatement ) )
        // InternalGlobalConstantsParser.g:2260:5: ( ruleSTStatement )
        {
        // InternalGlobalConstantsParser.g:2260:5: ( ruleSTStatement )
        // InternalGlobalConstantsParser.g:2261:5: ruleSTStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalGlobalConstantsParser

    // $ANTLR start synpred2_InternalGlobalConstantsParser
    public final void synpred2_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:3356:4: ( ruleSTSignedLiteralExpressions )
        // InternalGlobalConstantsParser.g:3356:5: ruleSTSignedLiteralExpressions
        {
        pushFollow(FOLLOW_2);
        ruleSTSignedLiteralExpressions();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalGlobalConstantsParser

    // Delegated rules

    public final boolean synpred1_InternalGlobalConstantsParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalGlobalConstantsParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalGlobalConstantsParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalGlobalConstantsParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA22 dfa22 = new DFA22(this);
    protected DFA29 dfa29 = new DFA29(this);
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA56 dfa56 = new DFA56(this);
    protected DFA73 dfa73 = new DFA73(this);
    static final String dfa_1s = "\24\uffff";
    static final String dfa_2s = "\2\uffff\1\3\17\uffff\1\3\1\uffff";
    static final String dfa_3s = "\2\12\1\167\2\uffff\11\167\1\u00bb\1\u009b\1\u00bb\1\uffff\2\167";
    static final String dfa_4s = "\2\u00bc\1\u00ad\2\uffff\11\u00aa\3\u00bb\1\uffff\1\u00ad\1\u00aa";
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

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "923:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )";
        }
    }
    static final String dfa_8s = "\12\uffff";
    static final String dfa_9s = "\1\12\11\uffff";
    static final String dfa_10s = "\1\u00bc\11\uffff";
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

    class DFA29 extends DFA {

        public DFA29(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 29;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "1552:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )";
        }
    }
    static final String dfa_14s = "\77\uffff";
    static final String dfa_15s = "\1\1\76\uffff";
    static final String dfa_16s = "\1\12\2\uffff\62\0\12\uffff";
    static final String dfa_17s = "\1\u00bc\2\uffff\62\0\12\uffff";
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

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "()* loopback of 2259:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA41_0 = input.LA(1);

                         
                        int index41_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_0==EOF||LA41_0==END_CASE||LA41_0==ELSE) ) {s = 1;}

                        else if ( (LA41_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA41_0==RULE_ID) ) {s = 4;}

                        else if ( (LA41_0==LT) ) {s = 5;}

                        else if ( (LA41_0==AND) ) {s = 6;}

                        else if ( (LA41_0==OR) ) {s = 7;}

                        else if ( (LA41_0==XOR) ) {s = 8;}

                        else if ( (LA41_0==MOD) ) {s = 9;}

                        else if ( (LA41_0==D) ) {s = 10;}

                        else if ( (LA41_0==DT) ) {s = 11;}

                        else if ( (LA41_0==LD) ) {s = 12;}

                        else if ( (LA41_0==THIS) ) {s = 13;}

                        else if ( (LA41_0==BOOL) ) {s = 14;}

                        else if ( (LA41_0==BYTE) ) {s = 15;}

                        else if ( (LA41_0==WORD) ) {s = 16;}

                        else if ( (LA41_0==DWORD) ) {s = 17;}

                        else if ( (LA41_0==LWORD) ) {s = 18;}

                        else if ( (LA41_0==SINT) ) {s = 19;}

                        else if ( (LA41_0==INT) ) {s = 20;}

                        else if ( (LA41_0==DINT) ) {s = 21;}

                        else if ( (LA41_0==LINT) ) {s = 22;}

                        else if ( (LA41_0==USINT) ) {s = 23;}

                        else if ( (LA41_0==UINT) ) {s = 24;}

                        else if ( (LA41_0==UDINT) ) {s = 25;}

                        else if ( (LA41_0==ULINT) ) {s = 26;}

                        else if ( (LA41_0==REAL) ) {s = 27;}

                        else if ( (LA41_0==LREAL) ) {s = 28;}

                        else if ( (LA41_0==TRUE) ) {s = 29;}

                        else if ( (LA41_0==FALSE) ) {s = 30;}

                        else if ( (LA41_0==RULE_INT) ) {s = 31;}

                        else if ( (LA41_0==RULE_NON_DECIMAL) ) {s = 32;}

                        else if ( (LA41_0==DATE) ) {s = 33;}

                        else if ( (LA41_0==LDATE) ) {s = 34;}

                        else if ( (LA41_0==TIME) ) {s = 35;}

                        else if ( (LA41_0==LTIME) ) {s = 36;}

                        else if ( (LA41_0==T) ) {s = 37;}

                        else if ( (LA41_0==TIME_OF_DAY) ) {s = 38;}

                        else if ( (LA41_0==LTIME_OF_DAY) ) {s = 39;}

                        else if ( (LA41_0==TOD) ) {s = 40;}

                        else if ( (LA41_0==LTOD) ) {s = 41;}

                        else if ( (LA41_0==DATE_AND_TIME) ) {s = 42;}

                        else if ( (LA41_0==LDATE_AND_TIME) ) {s = 43;}

                        else if ( (LA41_0==LDT) ) {s = 44;}

                        else if ( (LA41_0==STRING) ) {s = 45;}

                        else if ( (LA41_0==WSTRING) ) {s = 46;}

                        else if ( (LA41_0==CHAR) ) {s = 47;}

                        else if ( (LA41_0==WCHAR) ) {s = 48;}

                        else if ( (LA41_0==RULE_STRING) ) {s = 49;}

                        else if ( (LA41_0==PlusSign) ) {s = 50;}

                        else if ( (LA41_0==HyphenMinus) ) {s = 51;}

                        else if ( (LA41_0==NOT) ) {s = 52;}

                        else if ( (LA41_0==IF) && (synpred1_InternalGlobalConstantsParser())) {s = 54;}

                        else if ( (LA41_0==CASE) && (synpred1_InternalGlobalConstantsParser())) {s = 55;}

                        else if ( (LA41_0==FOR) && (synpred1_InternalGlobalConstantsParser())) {s = 56;}

                        else if ( (LA41_0==WHILE) && (synpred1_InternalGlobalConstantsParser())) {s = 57;}

                        else if ( (LA41_0==REPEAT) && (synpred1_InternalGlobalConstantsParser())) {s = 58;}

                        else if ( (LA41_0==RETURN) && (synpred1_InternalGlobalConstantsParser())) {s = 59;}

                        else if ( (LA41_0==CONTINUE) && (synpred1_InternalGlobalConstantsParser())) {s = 60;}

                        else if ( (LA41_0==EXIT) && (synpred1_InternalGlobalConstantsParser())) {s = 61;}

                        else if ( (LA41_0==Semicolon) && (synpred1_InternalGlobalConstantsParser())) {s = 62;}

                         
                        input.seek(index41_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA41_3 = input.LA(1);

                         
                        int index41_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA41_4 = input.LA(1);

                         
                        int index41_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA41_5 = input.LA(1);

                         
                        int index41_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA41_6 = input.LA(1);

                         
                        int index41_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA41_7 = input.LA(1);

                         
                        int index41_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA41_8 = input.LA(1);

                         
                        int index41_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA41_9 = input.LA(1);

                         
                        int index41_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA41_10 = input.LA(1);

                         
                        int index41_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA41_11 = input.LA(1);

                         
                        int index41_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA41_12 = input.LA(1);

                         
                        int index41_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA41_13 = input.LA(1);

                         
                        int index41_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA41_14 = input.LA(1);

                         
                        int index41_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA41_15 = input.LA(1);

                         
                        int index41_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA41_16 = input.LA(1);

                         
                        int index41_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA41_17 = input.LA(1);

                         
                        int index41_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA41_18 = input.LA(1);

                         
                        int index41_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA41_19 = input.LA(1);

                         
                        int index41_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA41_20 = input.LA(1);

                         
                        int index41_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA41_21 = input.LA(1);

                         
                        int index41_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA41_22 = input.LA(1);

                         
                        int index41_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA41_23 = input.LA(1);

                         
                        int index41_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA41_24 = input.LA(1);

                         
                        int index41_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA41_25 = input.LA(1);

                         
                        int index41_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA41_26 = input.LA(1);

                         
                        int index41_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA41_27 = input.LA(1);

                         
                        int index41_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA41_28 = input.LA(1);

                         
                        int index41_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA41_29 = input.LA(1);

                         
                        int index41_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA41_30 = input.LA(1);

                         
                        int index41_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA41_31 = input.LA(1);

                         
                        int index41_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA41_32 = input.LA(1);

                         
                        int index41_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA41_33 = input.LA(1);

                         
                        int index41_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA41_34 = input.LA(1);

                         
                        int index41_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA41_35 = input.LA(1);

                         
                        int index41_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA41_36 = input.LA(1);

                         
                        int index41_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA41_37 = input.LA(1);

                         
                        int index41_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA41_38 = input.LA(1);

                         
                        int index41_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA41_39 = input.LA(1);

                         
                        int index41_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA41_40 = input.LA(1);

                         
                        int index41_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA41_41 = input.LA(1);

                         
                        int index41_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA41_42 = input.LA(1);

                         
                        int index41_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA41_43 = input.LA(1);

                         
                        int index41_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA41_44 = input.LA(1);

                         
                        int index41_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA41_45 = input.LA(1);

                         
                        int index41_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA41_46 = input.LA(1);

                         
                        int index41_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_46);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA41_47 = input.LA(1);

                         
                        int index41_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_47);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA41_48 = input.LA(1);

                         
                        int index41_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_48);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA41_49 = input.LA(1);

                         
                        int index41_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_49);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA41_50 = input.LA(1);

                         
                        int index41_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_50);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA41_51 = input.LA(1);

                         
                        int index41_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_51);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA41_52 = input.LA(1);

                         
                        int index41_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index41_52);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 41, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_21s = "\17\uffff";
    static final String dfa_22s = "\2\uffff\5\1\6\uffff\1\1\1\uffff";
    static final String dfa_23s = "\1\12\1\uffff\5\30\1\uffff\2\12\1\uffff\1\u00bb\1\0\1\30\1\uffff";
    static final String dfa_24s = "\1\u00bc\1\uffff\5\u00ad\1\uffff\2\u00bc\1\uffff\1\u00bb\1\0\1\u00ad\1\uffff";
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

    class DFA56 extends DFA {

        public DFA56(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 56;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "3336:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA56_12 = input.LA(1);

                         
                        int index56_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 14;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index56_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 56, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_28s = "\23\uffff";
    static final String dfa_29s = "\1\116\17\u0099\1\uffff\1\120\1\uffff";
    static final String dfa_30s = "\1\u00b0\17\u0099\1\uffff\1\u00b0\1\uffff";
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

    class DFA73 extends DFA {

        public DFA73(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 73;
            this.eot = dfa_28;
            this.eof = dfa_28;
            this.min = dfa_29;
            this.max = dfa_30;
            this.accept = dfa_31;
            this.special = dfa_32;
            this.transition = dfa_33;
        }
        public String getDescription() {
            return "4165:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000004L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000100000010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0100080000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0100000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800004000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x8000000000241400L,0x46290D8ED9BC5400L,0x0800000000020000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x8000000000241400L,0x5EAB8D8ED9BD5400L,0x18018301489A0001L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000080080000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000141000000200L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000101000000200L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x8000000000241400L,0x5EAB8D8ED9BD5400L,0x18018701489A0001L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000101000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000090000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0880000000000000L,0x08000100009A0001L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200080000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x8000100000241400L,0x5FAB8DBFF9BDD542L,0x18018311489E0001L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000001000008002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x8000100000241402L,0x5FAB8DAFF9BD5540L,0x18018311489E0001L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x8000200000241400L,0x5EAB8D9ED9BD5400L,0x18018301489A0001L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000880000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000018000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x8080100000241400L,0x5FAB8DAFF9BD5540L,0x18018311489E0001L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x8000101000241400L,0x5FAB8DAFF9BD5540L,0x18018311489E0001L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x8000100000241400L,0x5FAB8DAFFBBD5540L,0x18018311489E0001L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0080000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000004000000800L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000A000002400L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000140000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0800000000000000L,0x0000000420000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040200000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0880000000000000L,0x08010100089A003FL});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x8000000000241400L,0x5EAB8D8ED9BD5400L,0x18018301589A0001L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000008000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000140000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x022A0C88C9A94000L,0x0001800000000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000140000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0003000000000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});

}