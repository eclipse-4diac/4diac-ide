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
    // InternalGlobalConstantsParser.g:76:1: ruleSTGlobalConstsSource returns [EObject current=null] : ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_elements_5_0= ruleSTVarGlobalDeclarationBlock ) )* ) ;
    public final EObject ruleSTGlobalConstsSource() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_imports_4_0 = null;

        EObject lv_elements_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:82:2: ( ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_elements_5_0= ruleSTVarGlobalDeclarationBlock ) )* ) )
            // InternalGlobalConstantsParser.g:83:2: ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_elements_5_0= ruleSTVarGlobalDeclarationBlock ) )* )
            {
            // InternalGlobalConstantsParser.g:83:2: ( () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_elements_5_0= ruleSTVarGlobalDeclarationBlock ) )* )
            // InternalGlobalConstantsParser.g:84:3: () (otherlv_1= PACKAGE ( (lv_name_2_0= ruleQualifiedName ) ) otherlv_3= Semicolon )? ( (lv_imports_4_0= ruleSTImport ) )* ( (lv_elements_5_0= ruleSTVarGlobalDeclarationBlock ) )*
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

            // InternalGlobalConstantsParser.g:139:3: ( (lv_elements_5_0= ruleSTVarGlobalDeclarationBlock ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==VAR_GLOBAL) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:140:4: (lv_elements_5_0= ruleSTVarGlobalDeclarationBlock )
            	    {
            	    // InternalGlobalConstantsParser.g:140:4: (lv_elements_5_0= ruleSTVarGlobalDeclarationBlock )
            	    // InternalGlobalConstantsParser.g:141:5: lv_elements_5_0= ruleSTVarGlobalDeclarationBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTGlobalConstsSourceAccess().getElementsSTVarGlobalDeclarationBlockParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_elements_5_0=ruleSTVarGlobalDeclarationBlock();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTGlobalConstsSourceRule());
            	      					}
            	      					add(
            	      						current,
            	      						"elements",
            	      						lv_elements_5_0,
            	      						"org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstants.STVarGlobalDeclarationBlock");
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
    // $ANTLR end "ruleSTGlobalConstsSource"


    // $ANTLR start "entryRuleSTVarGlobalDeclarationBlock"
    // InternalGlobalConstantsParser.g:162:1: entryRuleSTVarGlobalDeclarationBlock returns [EObject current=null] : iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF ;
    public final EObject entryRuleSTVarGlobalDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarGlobalDeclarationBlock = null;


        try {
            // InternalGlobalConstantsParser.g:162:68: (iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF )
            // InternalGlobalConstantsParser.g:163:2: iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF
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
    // InternalGlobalConstantsParser.g:169:1: ruleSTVarGlobalDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarGlobalDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:175:2: ( ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalGlobalConstantsParser.g:176:2: ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalGlobalConstantsParser.g:176:2: ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalGlobalConstantsParser.g:177:3: () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalGlobalConstantsParser.g:177:3: ()
            // InternalGlobalConstantsParser.g:178:4: 
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
            // InternalGlobalConstantsParser.g:188:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==CONSTANT) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalGlobalConstantsParser.g:189:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalGlobalConstantsParser.g:189:4: (lv_constant_2_0= CONSTANT )
                    // InternalGlobalConstantsParser.g:190:5: lv_constant_2_0= CONSTANT
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

            // InternalGlobalConstantsParser.g:202:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==RULE_ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:203:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalGlobalConstantsParser.g:203:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalGlobalConstantsParser.g:204:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
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
            	    break loop5;
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
    // InternalGlobalConstantsParser.g:229:1: entryRuleSTExpressionSource returns [EObject current=null] : iv_ruleSTExpressionSource= ruleSTExpressionSource EOF ;
    public final EObject entryRuleSTExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpressionSource = null;


        try {
            // InternalGlobalConstantsParser.g:229:59: (iv_ruleSTExpressionSource= ruleSTExpressionSource EOF )
            // InternalGlobalConstantsParser.g:230:2: iv_ruleSTExpressionSource= ruleSTExpressionSource EOF
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
    // InternalGlobalConstantsParser.g:236:1: ruleSTExpressionSource returns [EObject current=null] : ( () ( (lv_expression_1_0= ruleSTExpression ) )? ) ;
    public final EObject ruleSTExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_expression_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:242:2: ( ( () ( (lv_expression_1_0= ruleSTExpression ) )? ) )
            // InternalGlobalConstantsParser.g:243:2: ( () ( (lv_expression_1_0= ruleSTExpression ) )? )
            {
            // InternalGlobalConstantsParser.g:243:2: ( () ( (lv_expression_1_0= ruleSTExpression ) )? )
            // InternalGlobalConstantsParser.g:244:3: () ( (lv_expression_1_0= ruleSTExpression ) )?
            {
            // InternalGlobalConstantsParser.g:244:3: ()
            // InternalGlobalConstantsParser.g:245:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTExpressionSourceAccess().getSTExpressionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:251:3: ( (lv_expression_1_0= ruleSTExpression ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==LDATE_AND_TIME||LA6_0==DATE_AND_TIME||LA6_0==LTIME_OF_DAY||LA6_0==TIME_OF_DAY||LA6_0==WSTRING||LA6_0==STRING||LA6_0==DWORD||LA6_0==FALSE||(LA6_0>=LDATE && LA6_0<=LWORD)||(LA6_0>=UDINT && LA6_0<=ULINT)||(LA6_0>=USINT && LA6_0<=WCHAR)||(LA6_0>=BOOL && LA6_0<=BYTE)||(LA6_0>=CHAR && LA6_0<=DINT)||(LA6_0>=LINT && LA6_0<=LTOD)||(LA6_0>=REAL && LA6_0<=SINT)||(LA6_0>=THIS && LA6_0<=TRUE)||LA6_0==UINT||LA6_0==WORD||LA6_0==AND||(LA6_0>=INT && LA6_0<=NOT)||LA6_0==TOD||LA6_0==XOR||LA6_0==DT||(LA6_0>=LD && LA6_0<=LT)||LA6_0==OR||LA6_0==LeftParenthesis||LA6_0==PlusSign||LA6_0==HyphenMinus||(LA6_0>=D && LA6_0<=T)||(LA6_0>=RULE_NON_DECIMAL && LA6_0<=RULE_INT)||(LA6_0>=RULE_ID && LA6_0<=RULE_STRING)) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalGlobalConstantsParser.g:252:4: (lv_expression_1_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:252:4: (lv_expression_1_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:253:5: lv_expression_1_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:274:1: entryRuleSTInitializerExpressionSource returns [EObject current=null] : iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF ;
    public final EObject entryRuleSTInitializerExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpressionSource = null;


        try {
            // InternalGlobalConstantsParser.g:274:70: (iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF )
            // InternalGlobalConstantsParser.g:275:2: iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF
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
    // InternalGlobalConstantsParser.g:281:1: ruleSTInitializerExpressionSource returns [EObject current=null] : ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? ) ;
    public final EObject ruleSTInitializerExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_initializerExpression_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:287:2: ( ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? ) )
            // InternalGlobalConstantsParser.g:288:2: ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? )
            {
            // InternalGlobalConstantsParser.g:288:2: ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? )
            // InternalGlobalConstantsParser.g:289:3: () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )?
            {
            // InternalGlobalConstantsParser.g:289:3: ()
            // InternalGlobalConstantsParser.g:290:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTInitializerExpressionSourceAccess().getSTInitializerExpressionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:296:3: ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LDATE_AND_TIME||LA7_0==DATE_AND_TIME||LA7_0==LTIME_OF_DAY||LA7_0==TIME_OF_DAY||LA7_0==WSTRING||LA7_0==STRING||LA7_0==DWORD||LA7_0==FALSE||(LA7_0>=LDATE && LA7_0<=LWORD)||(LA7_0>=UDINT && LA7_0<=ULINT)||(LA7_0>=USINT && LA7_0<=WCHAR)||(LA7_0>=BOOL && LA7_0<=BYTE)||(LA7_0>=CHAR && LA7_0<=DINT)||(LA7_0>=LINT && LA7_0<=LTOD)||(LA7_0>=REAL && LA7_0<=SINT)||(LA7_0>=THIS && LA7_0<=TRUE)||LA7_0==UINT||LA7_0==WORD||LA7_0==AND||(LA7_0>=INT && LA7_0<=NOT)||LA7_0==TOD||LA7_0==XOR||LA7_0==DT||(LA7_0>=LD && LA7_0<=LT)||LA7_0==OR||LA7_0==LeftParenthesis||LA7_0==PlusSign||LA7_0==HyphenMinus||(LA7_0>=D && LA7_0<=LeftSquareBracket)||(LA7_0>=RULE_NON_DECIMAL && LA7_0<=RULE_INT)||(LA7_0>=RULE_ID && LA7_0<=RULE_STRING)) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalGlobalConstantsParser.g:297:4: (lv_initializerExpression_1_0= ruleSTInitializerExpression )
                    {
                    // InternalGlobalConstantsParser.g:297:4: (lv_initializerExpression_1_0= ruleSTInitializerExpression )
                    // InternalGlobalConstantsParser.g:298:5: lv_initializerExpression_1_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:319:1: entryRuleSTImport returns [EObject current=null] : iv_ruleSTImport= ruleSTImport EOF ;
    public final EObject entryRuleSTImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTImport = null;


        try {
            // InternalGlobalConstantsParser.g:319:49: (iv_ruleSTImport= ruleSTImport EOF )
            // InternalGlobalConstantsParser.g:320:2: iv_ruleSTImport= ruleSTImport EOF
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
    // InternalGlobalConstantsParser.g:326:1: ruleSTImport returns [EObject current=null] : (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon ) ;
    public final EObject ruleSTImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_importedNamespace_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:332:2: ( (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon ) )
            // InternalGlobalConstantsParser.g:333:2: (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon )
            {
            // InternalGlobalConstantsParser.g:333:2: (otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon )
            // InternalGlobalConstantsParser.g:334:3: otherlv_0= IMPORT ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) otherlv_2= Semicolon
            {
            otherlv_0=(Token)match(input,IMPORT,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTImportAccess().getIMPORTKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:338:3: ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) )
            // InternalGlobalConstantsParser.g:339:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            {
            // InternalGlobalConstantsParser.g:339:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            // InternalGlobalConstantsParser.g:340:5: lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard
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
    // InternalGlobalConstantsParser.g:365:1: entryRuleSTVarDeclaration returns [EObject current=null] : iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF ;
    public final EObject entryRuleSTVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclaration = null;


        try {
            // InternalGlobalConstantsParser.g:365:57: (iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF )
            // InternalGlobalConstantsParser.g:366:2: iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF
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
    // InternalGlobalConstantsParser.g:372:1: ruleSTVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon ) ;
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
            // InternalGlobalConstantsParser.g:378:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon ) )
            // InternalGlobalConstantsParser.g:379:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon )
            {
            // InternalGlobalConstantsParser.g:379:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon )
            // InternalGlobalConstantsParser.g:380:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? ( (lv_pragma_23_0= ruleSTPragma ) )? otherlv_24= Semicolon
            {
            // InternalGlobalConstantsParser.g:380:3: ()
            // InternalGlobalConstantsParser.g:381:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:387:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalGlobalConstantsParser.g:388:4: (lv_name_1_0= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:388:4: (lv_name_1_0= RULE_ID )
            // InternalGlobalConstantsParser.g:389:5: lv_name_1_0= RULE_ID
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

            // InternalGlobalConstantsParser.g:405:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==AT) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalGlobalConstantsParser.g:406:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:410:4: ( (otherlv_3= RULE_ID ) )
                    // InternalGlobalConstantsParser.g:411:5: (otherlv_3= RULE_ID )
                    {
                    // InternalGlobalConstantsParser.g:411:5: (otherlv_3= RULE_ID )
                    // InternalGlobalConstantsParser.g:412:6: otherlv_3= RULE_ID
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
            // InternalGlobalConstantsParser.g:428:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ARRAY) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalGlobalConstantsParser.g:429:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalGlobalConstantsParser.g:429:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalGlobalConstantsParser.g:430:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalGlobalConstantsParser.g:430:5: (lv_array_5_0= ARRAY )
                    // InternalGlobalConstantsParser.g:431:6: lv_array_5_0= ARRAY
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

                    // InternalGlobalConstantsParser.g:443:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==LeftSquareBracket) ) {
                        int LA11_1 = input.LA(2);

                        if ( (LA11_1==LDATE_AND_TIME||LA11_1==DATE_AND_TIME||LA11_1==LTIME_OF_DAY||LA11_1==TIME_OF_DAY||LA11_1==WSTRING||LA11_1==STRING||LA11_1==DWORD||LA11_1==FALSE||(LA11_1>=LDATE && LA11_1<=LWORD)||(LA11_1>=UDINT && LA11_1<=ULINT)||(LA11_1>=USINT && LA11_1<=WCHAR)||(LA11_1>=BOOL && LA11_1<=BYTE)||(LA11_1>=CHAR && LA11_1<=DINT)||(LA11_1>=LINT && LA11_1<=LTOD)||(LA11_1>=REAL && LA11_1<=SINT)||(LA11_1>=THIS && LA11_1<=TRUE)||LA11_1==UINT||LA11_1==WORD||LA11_1==AND||(LA11_1>=INT && LA11_1<=NOT)||LA11_1==TOD||LA11_1==XOR||LA11_1==DT||(LA11_1>=LD && LA11_1<=LT)||LA11_1==OR||LA11_1==LeftParenthesis||LA11_1==PlusSign||LA11_1==HyphenMinus||(LA11_1>=D && LA11_1<=T)||(LA11_1>=RULE_NON_DECIMAL && LA11_1<=RULE_INT)||(LA11_1>=RULE_ID && LA11_1<=RULE_STRING)) ) {
                            alt11=1;
                        }
                        else if ( (LA11_1==Asterisk) ) {
                            alt11=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 11, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 0, input);

                        throw nvae;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:444:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:444:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:445:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:449:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalGlobalConstantsParser.g:450:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalGlobalConstantsParser.g:450:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalGlobalConstantsParser.g:451:8: lv_ranges_7_0= ruleSTExpression
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

                            // InternalGlobalConstantsParser.g:468:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop9:
                            do {
                                int alt9=2;
                                int LA9_0 = input.LA(1);

                                if ( (LA9_0==Comma) ) {
                                    alt9=1;
                                }


                                switch (alt9) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:469:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:473:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalGlobalConstantsParser.g:474:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalGlobalConstantsParser.g:474:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalGlobalConstantsParser.g:475:9: lv_ranges_9_0= ruleSTExpression
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
                            	    break loop9;
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
                            // InternalGlobalConstantsParser.g:499:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:499:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:500:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:504:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalGlobalConstantsParser.g:505:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalGlobalConstantsParser.g:505:7: (lv_count_12_0= Asterisk )
                            // InternalGlobalConstantsParser.g:506:8: lv_count_12_0= Asterisk
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

                            // InternalGlobalConstantsParser.g:518:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop10:
                            do {
                                int alt10=2;
                                int LA10_0 = input.LA(1);

                                if ( (LA10_0==Comma) ) {
                                    alt10=1;
                                }


                                switch (alt10) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:519:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_16); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:523:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalGlobalConstantsParser.g:524:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalGlobalConstantsParser.g:524:8: (lv_count_14_0= Asterisk )
                            	    // InternalGlobalConstantsParser.g:525:9: lv_count_14_0= Asterisk
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
                            	    break loop10;
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

            // InternalGlobalConstantsParser.g:549:3: ( ( ruleSTAnyType ) )
            // InternalGlobalConstantsParser.g:550:4: ( ruleSTAnyType )
            {
            // InternalGlobalConstantsParser.g:550:4: ( ruleSTAnyType )
            // InternalGlobalConstantsParser.g:551:5: ruleSTAnyType
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

            // InternalGlobalConstantsParser.g:565:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==LeftSquareBracket) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalGlobalConstantsParser.g:566:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:570:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:571:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:571:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:572:6: lv_maxLength_19_0= ruleSTExpression
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

            // InternalGlobalConstantsParser.g:594:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ColonEqualsSign) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalGlobalConstantsParser.g:595:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:599:4: ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    // InternalGlobalConstantsParser.g:600:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    {
                    // InternalGlobalConstantsParser.g:600:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    // InternalGlobalConstantsParser.g:601:6: lv_defaultValue_22_0= ruleSTInitializerExpression
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

            // InternalGlobalConstantsParser.g:619:3: ( (lv_pragma_23_0= ruleSTPragma ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==LeftCurlyBracket) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalGlobalConstantsParser.g:620:4: (lv_pragma_23_0= ruleSTPragma )
                    {
                    // InternalGlobalConstantsParser.g:620:4: (lv_pragma_23_0= ruleSTPragma )
                    // InternalGlobalConstantsParser.g:621:5: lv_pragma_23_0= ruleSTPragma
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
    // InternalGlobalConstantsParser.g:646:1: entryRuleSTTypeDeclaration returns [EObject current=null] : iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF ;
    public final EObject entryRuleSTTypeDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTypeDeclaration = null;


        try {
            // InternalGlobalConstantsParser.g:646:58: (iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF )
            // InternalGlobalConstantsParser.g:647:2: iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF
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
    // InternalGlobalConstantsParser.g:653:1: ruleSTTypeDeclaration returns [EObject current=null] : ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) ;
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
            // InternalGlobalConstantsParser.g:659:2: ( ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) )
            // InternalGlobalConstantsParser.g:660:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            {
            // InternalGlobalConstantsParser.g:660:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            // InternalGlobalConstantsParser.g:661:3: () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            {
            // InternalGlobalConstantsParser.g:661:3: ()
            // InternalGlobalConstantsParser.g:662:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTTypeDeclarationAccess().getSTTypeDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:668:3: ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ARRAY) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalGlobalConstantsParser.g:669:4: ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF
                    {
                    // InternalGlobalConstantsParser.g:669:4: ( (lv_array_1_0= ARRAY ) )
                    // InternalGlobalConstantsParser.g:670:5: (lv_array_1_0= ARRAY )
                    {
                    // InternalGlobalConstantsParser.g:670:5: (lv_array_1_0= ARRAY )
                    // InternalGlobalConstantsParser.g:671:6: lv_array_1_0= ARRAY
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

                    // InternalGlobalConstantsParser.g:683:4: ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) )
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==LeftSquareBracket) ) {
                        int LA18_1 = input.LA(2);

                        if ( (LA18_1==LDATE_AND_TIME||LA18_1==DATE_AND_TIME||LA18_1==LTIME_OF_DAY||LA18_1==TIME_OF_DAY||LA18_1==WSTRING||LA18_1==STRING||LA18_1==DWORD||LA18_1==FALSE||(LA18_1>=LDATE && LA18_1<=LWORD)||(LA18_1>=UDINT && LA18_1<=ULINT)||(LA18_1>=USINT && LA18_1<=WCHAR)||(LA18_1>=BOOL && LA18_1<=BYTE)||(LA18_1>=CHAR && LA18_1<=DINT)||(LA18_1>=LINT && LA18_1<=LTOD)||(LA18_1>=REAL && LA18_1<=SINT)||(LA18_1>=THIS && LA18_1<=TRUE)||LA18_1==UINT||LA18_1==WORD||LA18_1==AND||(LA18_1>=INT && LA18_1<=NOT)||LA18_1==TOD||LA18_1==XOR||LA18_1==DT||(LA18_1>=LD && LA18_1<=LT)||LA18_1==OR||LA18_1==LeftParenthesis||LA18_1==PlusSign||LA18_1==HyphenMinus||(LA18_1>=D && LA18_1<=T)||(LA18_1>=RULE_NON_DECIMAL && LA18_1<=RULE_INT)||(LA18_1>=RULE_ID && LA18_1<=RULE_STRING)) ) {
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
                            // InternalGlobalConstantsParser.g:684:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:684:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:685:6: otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket
                            {
                            otherlv_2=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_0_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:689:6: ( (lv_ranges_3_0= ruleSTExpression ) )
                            // InternalGlobalConstantsParser.g:690:7: (lv_ranges_3_0= ruleSTExpression )
                            {
                            // InternalGlobalConstantsParser.g:690:7: (lv_ranges_3_0= ruleSTExpression )
                            // InternalGlobalConstantsParser.g:691:8: lv_ranges_3_0= ruleSTExpression
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

                            // InternalGlobalConstantsParser.g:708:6: (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )*
                            loop16:
                            do {
                                int alt16=2;
                                int LA16_0 = input.LA(1);

                                if ( (LA16_0==Comma) ) {
                                    alt16=1;
                                }


                                switch (alt16) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:709:7: otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_4, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:713:7: ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    // InternalGlobalConstantsParser.g:714:8: (lv_ranges_5_0= ruleSTExpression )
                            	    {
                            	    // InternalGlobalConstantsParser.g:714:8: (lv_ranges_5_0= ruleSTExpression )
                            	    // InternalGlobalConstantsParser.g:715:9: lv_ranges_5_0= ruleSTExpression
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
                            	    break loop16;
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
                            // InternalGlobalConstantsParser.g:739:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:739:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:740:6: otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket
                            {
                            otherlv_7=(Token)match(input,LeftSquareBracket,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_1_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:744:6: ( (lv_count_8_0= Asterisk ) )
                            // InternalGlobalConstantsParser.g:745:7: (lv_count_8_0= Asterisk )
                            {
                            // InternalGlobalConstantsParser.g:745:7: (lv_count_8_0= Asterisk )
                            // InternalGlobalConstantsParser.g:746:8: lv_count_8_0= Asterisk
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

                            // InternalGlobalConstantsParser.g:758:6: (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )*
                            loop17:
                            do {
                                int alt17=2;
                                int LA17_0 = input.LA(1);

                                if ( (LA17_0==Comma) ) {
                                    alt17=1;
                                }


                                switch (alt17) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:759:7: otherlv_9= Comma ( (lv_count_10_0= Asterisk ) )
                            	    {
                            	    otherlv_9=(Token)match(input,Comma,FOLLOW_16); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_9, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:763:7: ( (lv_count_10_0= Asterisk ) )
                            	    // InternalGlobalConstantsParser.g:764:8: (lv_count_10_0= Asterisk )
                            	    {
                            	    // InternalGlobalConstantsParser.g:764:8: (lv_count_10_0= Asterisk )
                            	    // InternalGlobalConstantsParser.g:765:9: lv_count_10_0= Asterisk
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
                            	    break loop17;
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

            // InternalGlobalConstantsParser.g:789:3: ( ( ruleSTAnyType ) )
            // InternalGlobalConstantsParser.g:790:4: ( ruleSTAnyType )
            {
            // InternalGlobalConstantsParser.g:790:4: ( ruleSTAnyType )
            // InternalGlobalConstantsParser.g:791:5: ruleSTAnyType
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

            // InternalGlobalConstantsParser.g:805:3: (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==LeftSquareBracket) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalGlobalConstantsParser.g:806:4: otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket
                    {
                    otherlv_14=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_3_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:810:4: ( (lv_maxLength_15_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:811:5: (lv_maxLength_15_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:811:5: (lv_maxLength_15_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:812:6: lv_maxLength_15_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:838:1: entryRuleSTInitializerExpression returns [EObject current=null] : iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF ;
    public final EObject entryRuleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:838:64: (iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:839:2: iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:845:1: ruleSTInitializerExpression returns [EObject current=null] : (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) ;
    public final EObject ruleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STElementaryInitializerExpression_0 = null;

        EObject this_STArrayInitializerExpression_1 = null;

        EObject this_STStructInitializerExpression_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:851:2: ( (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) )
            // InternalGlobalConstantsParser.g:852:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:852:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            int alt21=3;
            alt21 = dfa21.predict(input);
            switch (alt21) {
                case 1 :
                    // InternalGlobalConstantsParser.g:853:3: this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression
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
                    // InternalGlobalConstantsParser.g:862:3: this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression
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
                    // InternalGlobalConstantsParser.g:871:3: this_STStructInitializerExpression_2= ruleSTStructInitializerExpression
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
    // InternalGlobalConstantsParser.g:883:1: entryRuleSTElementaryInitializerExpression returns [EObject current=null] : iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF ;
    public final EObject entryRuleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElementaryInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:883:74: (iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:884:2: iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:890:1: ruleSTElementaryInitializerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:896:2: ( ( (lv_value_0_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:897:2: ( (lv_value_0_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:897:2: ( (lv_value_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:898:3: (lv_value_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:898:3: (lv_value_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:899:4: lv_value_0_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:919:1: entryRuleSTArrayInitializerExpression returns [EObject current=null] : iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF ;
    public final EObject entryRuleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:919:69: (iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:920:2: iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:926:1: ruleSTArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:932:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalGlobalConstantsParser.g:933:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalGlobalConstantsParser.g:933:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalGlobalConstantsParser.g:934:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:938:3: ( (lv_values_1_0= ruleSTArrayInitElement ) )
            // InternalGlobalConstantsParser.g:939:4: (lv_values_1_0= ruleSTArrayInitElement )
            {
            // InternalGlobalConstantsParser.g:939:4: (lv_values_1_0= ruleSTArrayInitElement )
            // InternalGlobalConstantsParser.g:940:5: lv_values_1_0= ruleSTArrayInitElement
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

            // InternalGlobalConstantsParser.g:957:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==Comma) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:958:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:962:4: ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    // InternalGlobalConstantsParser.g:963:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    {
            	    // InternalGlobalConstantsParser.g:963:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    // InternalGlobalConstantsParser.g:964:6: lv_values_3_0= ruleSTArrayInitElement
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
            	    break loop22;
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
    // InternalGlobalConstantsParser.g:990:1: entryRuleSTArrayInitElement returns [EObject current=null] : iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF ;
    public final EObject entryRuleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:990:59: (iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF )
            // InternalGlobalConstantsParser.g:991:2: iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF
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
    // InternalGlobalConstantsParser.g:997:1: ruleSTArrayInitElement returns [EObject current=null] : (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement ) ;
    public final EObject ruleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject this_STSingleArrayInitElement_0 = null;

        EObject this_STRepeatArrayInitElement_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1003:2: ( (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement ) )
            // InternalGlobalConstantsParser.g:1004:2: (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement )
            {
            // InternalGlobalConstantsParser.g:1004:2: (this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement | this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==LDATE_AND_TIME||LA23_0==DATE_AND_TIME||LA23_0==LTIME_OF_DAY||LA23_0==TIME_OF_DAY||LA23_0==WSTRING||LA23_0==STRING||LA23_0==DWORD||LA23_0==FALSE||(LA23_0>=LDATE && LA23_0<=LWORD)||(LA23_0>=UDINT && LA23_0<=ULINT)||(LA23_0>=USINT && LA23_0<=WCHAR)||(LA23_0>=BOOL && LA23_0<=BYTE)||(LA23_0>=CHAR && LA23_0<=DINT)||(LA23_0>=LINT && LA23_0<=LTOD)||(LA23_0>=REAL && LA23_0<=SINT)||(LA23_0>=THIS && LA23_0<=TRUE)||LA23_0==UINT||LA23_0==WORD||LA23_0==AND||(LA23_0>=INT && LA23_0<=NOT)||LA23_0==TOD||LA23_0==XOR||LA23_0==DT||(LA23_0>=LD && LA23_0<=LT)||LA23_0==OR||LA23_0==LeftParenthesis||LA23_0==PlusSign||LA23_0==HyphenMinus||(LA23_0>=D && LA23_0<=LeftSquareBracket)||LA23_0==RULE_NON_DECIMAL||(LA23_0>=RULE_ID && LA23_0<=RULE_STRING)) ) {
                alt23=1;
            }
            else if ( (LA23_0==RULE_INT) ) {
                int LA23_2 = input.LA(2);

                if ( (LA23_2==EOF||LA23_2==AND||LA23_2==MOD||LA23_2==XOR||(LA23_2>=AsteriskAsterisk && LA23_2<=FullStopFullStop)||(LA23_2>=LessThanSignEqualsSign && LA23_2<=LessThanSignGreaterThanSign)||LA23_2==GreaterThanSignEqualsSign||LA23_2==OR||LA23_2==Ampersand||(LA23_2>=Asterisk && LA23_2<=Solidus)||(LA23_2>=LessThanSign && LA23_2<=GreaterThanSign)||LA23_2==RightSquareBracket) ) {
                    alt23=1;
                }
                else if ( (LA23_2==LeftParenthesis) ) {
                    alt23=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 2, input);

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
                    // InternalGlobalConstantsParser.g:1005:3: this_STSingleArrayInitElement_0= ruleSTSingleArrayInitElement
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
                    // InternalGlobalConstantsParser.g:1014:3: this_STRepeatArrayInitElement_1= ruleSTRepeatArrayInitElement
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
    // InternalGlobalConstantsParser.g:1026:1: entryRuleSTSingleArrayInitElement returns [EObject current=null] : iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF ;
    public final EObject entryRuleSTSingleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSingleArrayInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1026:65: (iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF )
            // InternalGlobalConstantsParser.g:1027:2: iv_ruleSTSingleArrayInitElement= ruleSTSingleArrayInitElement EOF
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
    // InternalGlobalConstantsParser.g:1033:1: ruleSTSingleArrayInitElement returns [EObject current=null] : ( (lv_initExpression_0_0= ruleSTInitializerExpression ) ) ;
    public final EObject ruleSTSingleArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject lv_initExpression_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1039:2: ( ( (lv_initExpression_0_0= ruleSTInitializerExpression ) ) )
            // InternalGlobalConstantsParser.g:1040:2: ( (lv_initExpression_0_0= ruleSTInitializerExpression ) )
            {
            // InternalGlobalConstantsParser.g:1040:2: ( (lv_initExpression_0_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1041:3: (lv_initExpression_0_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1041:3: (lv_initExpression_0_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1042:4: lv_initExpression_0_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:1062:1: entryRuleSTRepeatArrayInitElement returns [EObject current=null] : iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF ;
    public final EObject entryRuleSTRepeatArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatArrayInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1062:65: (iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF )
            // InternalGlobalConstantsParser.g:1063:2: iv_ruleSTRepeatArrayInitElement= ruleSTRepeatArrayInitElement EOF
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
    // InternalGlobalConstantsParser.g:1069:1: ruleSTRepeatArrayInitElement returns [EObject current=null] : ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis ) ;
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
            // InternalGlobalConstantsParser.g:1075:2: ( ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis ) )
            // InternalGlobalConstantsParser.g:1076:2: ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )
            {
            // InternalGlobalConstantsParser.g:1076:2: ( ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )
            // InternalGlobalConstantsParser.g:1077:3: ( (lv_repetitions_0_0= RULE_INT ) ) otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis
            {
            // InternalGlobalConstantsParser.g:1077:3: ( (lv_repetitions_0_0= RULE_INT ) )
            // InternalGlobalConstantsParser.g:1078:4: (lv_repetitions_0_0= RULE_INT )
            {
            // InternalGlobalConstantsParser.g:1078:4: (lv_repetitions_0_0= RULE_INT )
            // InternalGlobalConstantsParser.g:1079:5: lv_repetitions_0_0= RULE_INT
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
            // InternalGlobalConstantsParser.g:1099:3: ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1100:4: (lv_initExpressions_2_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1100:4: (lv_initExpressions_2_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1101:5: lv_initExpressions_2_0= ruleSTInitializerExpression
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

            // InternalGlobalConstantsParser.g:1118:3: (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==Comma) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1119:4: otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getSTRepeatArrayInitElementAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1123:4: ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
            	    // InternalGlobalConstantsParser.g:1124:5: (lv_initExpressions_4_0= ruleSTInitializerExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:1124:5: (lv_initExpressions_4_0= ruleSTInitializerExpression )
            	    // InternalGlobalConstantsParser.g:1125:6: lv_initExpressions_4_0= ruleSTInitializerExpression
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
            	    break loop24;
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
    // InternalGlobalConstantsParser.g:1151:1: entryRuleSTStructInitializerExpression returns [EObject current=null] : iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF ;
    public final EObject entryRuleSTStructInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:1151:70: (iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:1152:2: iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:1158:1: ruleSTStructInitializerExpression returns [EObject current=null] : ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis ) ;
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
            // InternalGlobalConstantsParser.g:1164:2: ( ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis ) )
            // InternalGlobalConstantsParser.g:1165:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis )
            {
            // InternalGlobalConstantsParser.g:1165:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis )
            // InternalGlobalConstantsParser.g:1166:3: ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )? otherlv_2= LeftParenthesis ( (lv_values_3_0= ruleSTStructInitElement ) ) (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )* otherlv_6= RightParenthesis
            {
            // InternalGlobalConstantsParser.g:1166:3: ( ( ( ruleQualifiedName ) ) otherlv_1= NumberSign )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_ID) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1167:4: ( ( ruleQualifiedName ) ) otherlv_1= NumberSign
                    {
                    // InternalGlobalConstantsParser.g:1167:4: ( ( ruleQualifiedName ) )
                    // InternalGlobalConstantsParser.g:1168:5: ( ruleQualifiedName )
                    {
                    // InternalGlobalConstantsParser.g:1168:5: ( ruleQualifiedName )
                    // InternalGlobalConstantsParser.g:1169:6: ruleQualifiedName
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
            // InternalGlobalConstantsParser.g:1192:3: ( (lv_values_3_0= ruleSTStructInitElement ) )
            // InternalGlobalConstantsParser.g:1193:4: (lv_values_3_0= ruleSTStructInitElement )
            {
            // InternalGlobalConstantsParser.g:1193:4: (lv_values_3_0= ruleSTStructInitElement )
            // InternalGlobalConstantsParser.g:1194:5: lv_values_3_0= ruleSTStructInitElement
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

            // InternalGlobalConstantsParser.g:1211:3: (otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==Comma) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1212:4: otherlv_4= Comma ( (lv_values_5_0= ruleSTStructInitElement ) )
            	    {
            	    otherlv_4=(Token)match(input,Comma,FOLLOW_26); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getSTStructInitializerExpressionAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1216:4: ( (lv_values_5_0= ruleSTStructInitElement ) )
            	    // InternalGlobalConstantsParser.g:1217:5: (lv_values_5_0= ruleSTStructInitElement )
            	    {
            	    // InternalGlobalConstantsParser.g:1217:5: (lv_values_5_0= ruleSTStructInitElement )
            	    // InternalGlobalConstantsParser.g:1218:6: lv_values_5_0= ruleSTStructInitElement
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
            	    break loop26;
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
    // InternalGlobalConstantsParser.g:1244:1: entryRuleSTStructInitElement returns [EObject current=null] : iv_ruleSTStructInitElement= ruleSTStructInitElement EOF ;
    public final EObject entryRuleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1244:60: (iv_ruleSTStructInitElement= ruleSTStructInitElement EOF )
            // InternalGlobalConstantsParser.g:1245:2: iv_ruleSTStructInitElement= ruleSTStructInitElement EOF
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
    // InternalGlobalConstantsParser.g:1251:1: ruleSTStructInitElement returns [EObject current=null] : ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) ;
    public final EObject ruleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1257:2: ( ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1258:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1258:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            // InternalGlobalConstantsParser.g:1259:3: ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) )
            {
            // InternalGlobalConstantsParser.g:1259:3: ( ( ruleSTFeatureName ) )
            // InternalGlobalConstantsParser.g:1260:4: ( ruleSTFeatureName )
            {
            // InternalGlobalConstantsParser.g:1260:4: ( ruleSTFeatureName )
            // InternalGlobalConstantsParser.g:1261:5: ruleSTFeatureName
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
            // InternalGlobalConstantsParser.g:1279:3: ( (lv_value_2_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1280:4: (lv_value_2_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1280:4: (lv_value_2_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1281:5: lv_value_2_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:1302:1: entryRuleSTPragma returns [EObject current=null] : iv_ruleSTPragma= ruleSTPragma EOF ;
    public final EObject entryRuleSTPragma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPragma = null;


        try {
            // InternalGlobalConstantsParser.g:1302:49: (iv_ruleSTPragma= ruleSTPragma EOF )
            // InternalGlobalConstantsParser.g:1303:2: iv_ruleSTPragma= ruleSTPragma EOF
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
    // InternalGlobalConstantsParser.g:1309:1: ruleSTPragma returns [EObject current=null] : ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket ) ;
    public final EObject ruleSTPragma() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_attributes_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1315:2: ( ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket ) )
            // InternalGlobalConstantsParser.g:1316:2: ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket )
            {
            // InternalGlobalConstantsParser.g:1316:2: ( () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket )
            // InternalGlobalConstantsParser.g:1317:3: () otherlv_1= LeftCurlyBracket ( (lv_attributes_2_0= ruleSTAttribute ) ) (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )* otherlv_5= RightCurlyBracket
            {
            // InternalGlobalConstantsParser.g:1317:3: ()
            // InternalGlobalConstantsParser.g:1318:4: 
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
            // InternalGlobalConstantsParser.g:1328:3: ( (lv_attributes_2_0= ruleSTAttribute ) )
            // InternalGlobalConstantsParser.g:1329:4: (lv_attributes_2_0= ruleSTAttribute )
            {
            // InternalGlobalConstantsParser.g:1329:4: (lv_attributes_2_0= ruleSTAttribute )
            // InternalGlobalConstantsParser.g:1330:5: lv_attributes_2_0= ruleSTAttribute
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

            // InternalGlobalConstantsParser.g:1347:3: (otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==Comma) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1348:4: otherlv_3= Comma ( (lv_attributes_4_0= ruleSTAttribute ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getSTPragmaAccess().getCommaKeyword_3_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1352:4: ( (lv_attributes_4_0= ruleSTAttribute ) )
            	    // InternalGlobalConstantsParser.g:1353:5: (lv_attributes_4_0= ruleSTAttribute )
            	    {
            	    // InternalGlobalConstantsParser.g:1353:5: (lv_attributes_4_0= ruleSTAttribute )
            	    // InternalGlobalConstantsParser.g:1354:6: lv_attributes_4_0= ruleSTAttribute
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
            	    break loop27;
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
    // InternalGlobalConstantsParser.g:1380:1: entryRuleSTAttribute returns [EObject current=null] : iv_ruleSTAttribute= ruleSTAttribute EOF ;
    public final EObject entryRuleSTAttribute() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAttribute = null;


        try {
            // InternalGlobalConstantsParser.g:1380:52: (iv_ruleSTAttribute= ruleSTAttribute EOF )
            // InternalGlobalConstantsParser.g:1381:2: iv_ruleSTAttribute= ruleSTAttribute EOF
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
    // InternalGlobalConstantsParser.g:1387:1: ruleSTAttribute returns [EObject current=null] : ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) ;
    public final EObject ruleSTAttribute() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1393:2: ( ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1394:2: ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1394:2: ( ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            // InternalGlobalConstantsParser.g:1395:3: ( ( ruleSTAttributeName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) )
            {
            // InternalGlobalConstantsParser.g:1395:3: ( ( ruleSTAttributeName ) )
            // InternalGlobalConstantsParser.g:1396:4: ( ruleSTAttributeName )
            {
            // InternalGlobalConstantsParser.g:1396:4: ( ruleSTAttributeName )
            // InternalGlobalConstantsParser.g:1397:5: ruleSTAttributeName
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
            // InternalGlobalConstantsParser.g:1415:3: ( (lv_value_2_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1416:4: (lv_value_2_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1416:4: (lv_value_2_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1417:5: lv_value_2_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:1438:1: entryRuleSTAttributeName returns [String current=null] : iv_ruleSTAttributeName= ruleSTAttributeName EOF ;
    public final String entryRuleSTAttributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAttributeName = null;


        try {
            // InternalGlobalConstantsParser.g:1438:55: (iv_ruleSTAttributeName= ruleSTAttributeName EOF )
            // InternalGlobalConstantsParser.g:1439:2: iv_ruleSTAttributeName= ruleSTAttributeName EOF
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
    // InternalGlobalConstantsParser.g:1445:1: ruleSTAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_QualifiedName_0= ruleQualifiedName ;
    public final AntlrDatatypeRuleToken ruleSTAttributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1451:2: (this_QualifiedName_0= ruleQualifiedName )
            // InternalGlobalConstantsParser.g:1452:2: this_QualifiedName_0= ruleQualifiedName
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
    // InternalGlobalConstantsParser.g:1465:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1465:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalGlobalConstantsParser.g:1466:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalGlobalConstantsParser.g:1472:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) ;
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
            // InternalGlobalConstantsParser.g:1478:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) )
            // InternalGlobalConstantsParser.g:1479:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            {
            // InternalGlobalConstantsParser.g:1479:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==LDATE_AND_TIME||LA29_0==DATE_AND_TIME||LA29_0==LTIME_OF_DAY||LA29_0==TIME_OF_DAY||LA29_0==CONTINUE||LA29_0==WSTRING||LA29_0==REPEAT||LA29_0==RETURN||LA29_0==STRING||LA29_0==DWORD||LA29_0==FALSE||(LA29_0>=LDATE && LA29_0<=LWORD)||(LA29_0>=UDINT && LA29_0<=ULINT)||(LA29_0>=USINT && LA29_0<=DINT)||LA29_0==EXIT||(LA29_0>=LINT && LA29_0<=LTOD)||(LA29_0>=REAL && LA29_0<=SINT)||(LA29_0>=THIS && LA29_0<=TRUE)||LA29_0==UINT||LA29_0==WORD||(LA29_0>=AND && LA29_0<=NOT)||LA29_0==TOD||LA29_0==XOR||(LA29_0>=DT && LA29_0<=LT)||LA29_0==OR||LA29_0==LeftParenthesis||LA29_0==PlusSign||LA29_0==HyphenMinus||(LA29_0>=D && LA29_0<=T)||(LA29_0>=RULE_NON_DECIMAL && LA29_0<=RULE_INT)||(LA29_0>=RULE_ID && LA29_0<=RULE_STRING)) ) {
                alt29=1;
            }
            else if ( (LA29_0==Semicolon) ) {
                alt29=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1480:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    {
                    // InternalGlobalConstantsParser.g:1480:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    // InternalGlobalConstantsParser.g:1481:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon
                    {
                    // InternalGlobalConstantsParser.g:1481:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )
                    int alt28=9;
                    alt28 = dfa28.predict(input);
                    switch (alt28) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:1482:5: this_STIfStatement_0= ruleSTIfStatement
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
                            // InternalGlobalConstantsParser.g:1491:5: this_STCaseStatement_1= ruleSTCaseStatement
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
                            // InternalGlobalConstantsParser.g:1500:5: this_STForStatement_2= ruleSTForStatement
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
                            // InternalGlobalConstantsParser.g:1509:5: this_STWhileStatement_3= ruleSTWhileStatement
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
                            // InternalGlobalConstantsParser.g:1518:5: this_STRepeatStatement_4= ruleSTRepeatStatement
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
                            // InternalGlobalConstantsParser.g:1527:5: this_STAssignment_5= ruleSTAssignment
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
                            // InternalGlobalConstantsParser.g:1536:5: ( () otherlv_7= RETURN )
                            {
                            // InternalGlobalConstantsParser.g:1536:5: ( () otherlv_7= RETURN )
                            // InternalGlobalConstantsParser.g:1537:6: () otherlv_7= RETURN
                            {
                            // InternalGlobalConstantsParser.g:1537:6: ()
                            // InternalGlobalConstantsParser.g:1538:7: 
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
                            // InternalGlobalConstantsParser.g:1550:5: ( () otherlv_9= CONTINUE )
                            {
                            // InternalGlobalConstantsParser.g:1550:5: ( () otherlv_9= CONTINUE )
                            // InternalGlobalConstantsParser.g:1551:6: () otherlv_9= CONTINUE
                            {
                            // InternalGlobalConstantsParser.g:1551:6: ()
                            // InternalGlobalConstantsParser.g:1552:7: 
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
                            // InternalGlobalConstantsParser.g:1564:5: ( () otherlv_11= EXIT )
                            {
                            // InternalGlobalConstantsParser.g:1564:5: ( () otherlv_11= EXIT )
                            // InternalGlobalConstantsParser.g:1565:6: () otherlv_11= EXIT
                            {
                            // InternalGlobalConstantsParser.g:1565:6: ()
                            // InternalGlobalConstantsParser.g:1566:7: 
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
                    // InternalGlobalConstantsParser.g:1584:3: ( () otherlv_14= Semicolon )
                    {
                    // InternalGlobalConstantsParser.g:1584:3: ( () otherlv_14= Semicolon )
                    // InternalGlobalConstantsParser.g:1585:4: () otherlv_14= Semicolon
                    {
                    // InternalGlobalConstantsParser.g:1585:4: ()
                    // InternalGlobalConstantsParser.g:1586:5: 
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
    // InternalGlobalConstantsParser.g:1601:1: entryRuleSTAssignment returns [EObject current=null] : iv_ruleSTAssignment= ruleSTAssignment EOF ;
    public final EObject entryRuleSTAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignment = null;


        try {
            // InternalGlobalConstantsParser.g:1601:53: (iv_ruleSTAssignment= ruleSTAssignment EOF )
            // InternalGlobalConstantsParser.g:1602:2: iv_ruleSTAssignment= ruleSTAssignment EOF
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
    // InternalGlobalConstantsParser.g:1608:1: ruleSTAssignment returns [EObject current=null] : (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? ) ;
    public final EObject ruleSTAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_STExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1614:2: ( (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? ) )
            // InternalGlobalConstantsParser.g:1615:2: (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? )
            {
            // InternalGlobalConstantsParser.g:1615:2: (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? )
            // InternalGlobalConstantsParser.g:1616:3: this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )?
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
            // InternalGlobalConstantsParser.g:1624:3: ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ColonEqualsSign) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1625:4: () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) )
                    {
                    // InternalGlobalConstantsParser.g:1625:4: ()
                    // InternalGlobalConstantsParser.g:1626:5: 
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
                    // InternalGlobalConstantsParser.g:1636:4: ( (lv_right_3_0= ruleSTAssignment ) )
                    // InternalGlobalConstantsParser.g:1637:5: (lv_right_3_0= ruleSTAssignment )
                    {
                    // InternalGlobalConstantsParser.g:1637:5: (lv_right_3_0= ruleSTAssignment )
                    // InternalGlobalConstantsParser.g:1638:6: lv_right_3_0= ruleSTAssignment
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
    // InternalGlobalConstantsParser.g:1660:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1660:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalGlobalConstantsParser.g:1661:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
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
    // InternalGlobalConstantsParser.g:1667:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1673:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalGlobalConstantsParser.g:1674:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalGlobalConstantsParser.g:1674:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            int alt31=3;
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
                alt31=1;
                }
                break;
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case ColonEqualsSign:
                    {
                    alt31=2;
                    }
                    break;
                case EqualsSignGreaterThanSign:
                    {
                    alt31=3;
                    }
                    break;
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
                    alt31=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 2, input);

                    throw nvae;
                }

                }
                break;
            case NOT:
                {
                int LA31_3 = input.LA(2);

                if ( (LA31_3==LDATE_AND_TIME||LA31_3==DATE_AND_TIME||LA31_3==LTIME_OF_DAY||LA31_3==TIME_OF_DAY||LA31_3==WSTRING||LA31_3==STRING||LA31_3==DWORD||LA31_3==FALSE||(LA31_3>=LDATE && LA31_3<=LWORD)||(LA31_3>=UDINT && LA31_3<=ULINT)||(LA31_3>=USINT && LA31_3<=WCHAR)||(LA31_3>=BOOL && LA31_3<=BYTE)||(LA31_3>=CHAR && LA31_3<=DINT)||(LA31_3>=LINT && LA31_3<=LTOD)||(LA31_3>=REAL && LA31_3<=SINT)||(LA31_3>=THIS && LA31_3<=TRUE)||LA31_3==UINT||LA31_3==WORD||LA31_3==AND||(LA31_3>=INT && LA31_3<=NOT)||LA31_3==TOD||LA31_3==XOR||LA31_3==DT||(LA31_3>=LD && LA31_3<=LT)||LA31_3==OR||LA31_3==LeftParenthesis||LA31_3==PlusSign||LA31_3==HyphenMinus||(LA31_3>=D && LA31_3<=T)||(LA31_3>=RULE_NON_DECIMAL && LA31_3<=RULE_INT)||LA31_3==RULE_STRING) ) {
                    alt31=1;
                }
                else if ( (LA31_3==RULE_ID) ) {
                    int LA31_6 = input.LA(3);

                    if ( (LA31_6==EOF||LA31_6==AND||LA31_6==MOD||LA31_6==XOR||(LA31_6>=AsteriskAsterisk && LA31_6<=ColonColon)||(LA31_6>=LessThanSignEqualsSign && LA31_6<=LessThanSignGreaterThanSign)||LA31_6==GreaterThanSignEqualsSign||LA31_6==OR||(LA31_6>=Ampersand && LA31_6<=Solidus)||(LA31_6>=LessThanSign && LA31_6<=GreaterThanSign)||LA31_6==LeftSquareBracket) ) {
                        alt31=1;
                    }
                    else if ( (LA31_6==EqualsSignGreaterThanSign) ) {
                        alt31=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 6, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }

            switch (alt31) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1675:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
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
                    // InternalGlobalConstantsParser.g:1684:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
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
                    // InternalGlobalConstantsParser.g:1693:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
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
    // InternalGlobalConstantsParser.g:1705:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1705:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalGlobalConstantsParser.g:1706:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
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
    // InternalGlobalConstantsParser.g:1712:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_argument_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_argument_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1718:2: ( ( (lv_argument_0_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1719:2: ( (lv_argument_0_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1719:2: ( (lv_argument_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1720:3: (lv_argument_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1720:3: (lv_argument_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1721:4: lv_argument_0_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1741:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1741:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalGlobalConstantsParser.g:1742:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
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
    // InternalGlobalConstantsParser.g:1748:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_argument_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1754:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1755:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1755:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1756:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1756:3: ( (otherlv_0= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1757:4: (otherlv_0= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1757:4: (otherlv_0= RULE_ID )
            // InternalGlobalConstantsParser.g:1758:5: otherlv_0= RULE_ID
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
            // InternalGlobalConstantsParser.g:1773:3: ( (lv_argument_2_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1774:4: (lv_argument_2_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1774:4: (lv_argument_2_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1775:5: lv_argument_2_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1796:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1796:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalGlobalConstantsParser.g:1797:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
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
    // InternalGlobalConstantsParser.g:1803:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_argument_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1809:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1810:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1810:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1811:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1811:3: ( (lv_not_0_0= NOT ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==NOT) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1812:4: (lv_not_0_0= NOT )
                    {
                    // InternalGlobalConstantsParser.g:1812:4: (lv_not_0_0= NOT )
                    // InternalGlobalConstantsParser.g:1813:5: lv_not_0_0= NOT
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

            // InternalGlobalConstantsParser.g:1825:3: ( (otherlv_1= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1826:4: (otherlv_1= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1826:4: (otherlv_1= RULE_ID )
            // InternalGlobalConstantsParser.g:1827:5: otherlv_1= RULE_ID
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
            // InternalGlobalConstantsParser.g:1842:3: ( (lv_argument_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1843:4: (lv_argument_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1843:4: (lv_argument_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1844:5: lv_argument_3_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1865:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1865:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalGlobalConstantsParser.g:1866:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalGlobalConstantsParser.g:1872:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalGlobalConstantsParser.g:1878:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalGlobalConstantsParser.g:1879:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalGlobalConstantsParser.g:1879:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalGlobalConstantsParser.g:1880:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1884:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1885:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1885:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1886:5: lv_condition_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:1907:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==LDATE_AND_TIME||LA33_0==DATE_AND_TIME||LA33_0==LTIME_OF_DAY||LA33_0==TIME_OF_DAY||LA33_0==CONTINUE||LA33_0==WSTRING||LA33_0==REPEAT||LA33_0==RETURN||LA33_0==STRING||LA33_0==DWORD||LA33_0==FALSE||(LA33_0>=LDATE && LA33_0<=LWORD)||(LA33_0>=UDINT && LA33_0<=ULINT)||(LA33_0>=USINT && LA33_0<=DINT)||LA33_0==EXIT||(LA33_0>=LINT && LA33_0<=LTOD)||(LA33_0>=REAL && LA33_0<=SINT)||(LA33_0>=THIS && LA33_0<=TRUE)||LA33_0==UINT||LA33_0==WORD||(LA33_0>=AND && LA33_0<=NOT)||LA33_0==TOD||LA33_0==XOR||(LA33_0>=DT && LA33_0<=LT)||LA33_0==OR||LA33_0==LeftParenthesis||LA33_0==PlusSign||LA33_0==HyphenMinus||LA33_0==Semicolon||(LA33_0>=D && LA33_0<=T)||(LA33_0>=RULE_NON_DECIMAL && LA33_0<=RULE_INT)||(LA33_0>=RULE_ID && LA33_0<=RULE_STRING)) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1908:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1908:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1909:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop33;
                }
            } while (true);

            // InternalGlobalConstantsParser.g:1926:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==ELSIF) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1927:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalGlobalConstantsParser.g:1927:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalGlobalConstantsParser.g:1928:5: lv_elseifs_4_0= ruleSTElseIfPart
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
            	    break loop34;
                }
            } while (true);

            // InternalGlobalConstantsParser.g:1945:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ELSE) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1946:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalGlobalConstantsParser.g:1946:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalGlobalConstantsParser.g:1947:5: lv_else_5_0= ruleSTElsePart
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
    // InternalGlobalConstantsParser.g:1972:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalGlobalConstantsParser.g:1972:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalGlobalConstantsParser.g:1973:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalGlobalConstantsParser.g:1979:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1985:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:1986:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:1986:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:1987:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1991:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1992:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1992:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1993:5: lv_condition_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:2014:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==LDATE_AND_TIME||LA36_0==DATE_AND_TIME||LA36_0==LTIME_OF_DAY||LA36_0==TIME_OF_DAY||LA36_0==CONTINUE||LA36_0==WSTRING||LA36_0==REPEAT||LA36_0==RETURN||LA36_0==STRING||LA36_0==DWORD||LA36_0==FALSE||(LA36_0>=LDATE && LA36_0<=LWORD)||(LA36_0>=UDINT && LA36_0<=ULINT)||(LA36_0>=USINT && LA36_0<=DINT)||LA36_0==EXIT||(LA36_0>=LINT && LA36_0<=LTOD)||(LA36_0>=REAL && LA36_0<=SINT)||(LA36_0>=THIS && LA36_0<=TRUE)||LA36_0==UINT||LA36_0==WORD||(LA36_0>=AND && LA36_0<=NOT)||LA36_0==TOD||LA36_0==XOR||(LA36_0>=DT && LA36_0<=LT)||LA36_0==OR||LA36_0==LeftParenthesis||LA36_0==PlusSign||LA36_0==HyphenMinus||LA36_0==Semicolon||(LA36_0>=D && LA36_0<=T)||(LA36_0>=RULE_NON_DECIMAL && LA36_0<=RULE_INT)||(LA36_0>=RULE_ID && LA36_0<=RULE_STRING)) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2015:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2015:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2016:5: lv_statements_3_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalGlobalConstantsParser.g:2037:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2037:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalGlobalConstantsParser.g:2038:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalGlobalConstantsParser.g:2044:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalGlobalConstantsParser.g:2050:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalGlobalConstantsParser.g:2051:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalGlobalConstantsParser.g:2051:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalGlobalConstantsParser.g:2052:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2056:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2057:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2057:4: (lv_selector_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2058:5: lv_selector_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:2079:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt37=0;
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==LDATE_AND_TIME||LA37_0==DATE_AND_TIME||LA37_0==LTIME_OF_DAY||LA37_0==TIME_OF_DAY||LA37_0==WSTRING||LA37_0==STRING||LA37_0==DWORD||LA37_0==FALSE||(LA37_0>=LDATE && LA37_0<=LWORD)||(LA37_0>=UDINT && LA37_0<=ULINT)||(LA37_0>=USINT && LA37_0<=WCHAR)||(LA37_0>=BOOL && LA37_0<=BYTE)||(LA37_0>=CHAR && LA37_0<=DINT)||(LA37_0>=LINT && LA37_0<=LTOD)||(LA37_0>=REAL && LA37_0<=SINT)||(LA37_0>=THIS && LA37_0<=TRUE)||LA37_0==UINT||LA37_0==WORD||LA37_0==AND||(LA37_0>=INT && LA37_0<=NOT)||LA37_0==TOD||LA37_0==XOR||LA37_0==DT||(LA37_0>=LD && LA37_0<=LT)||LA37_0==OR||LA37_0==LeftParenthesis||LA37_0==PlusSign||LA37_0==HyphenMinus||(LA37_0>=D && LA37_0<=T)||(LA37_0>=RULE_NON_DECIMAL && LA37_0<=RULE_INT)||(LA37_0>=RULE_ID && LA37_0<=RULE_STRING)) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2080:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalGlobalConstantsParser.g:2080:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalGlobalConstantsParser.g:2081:5: lv_cases_3_0= ruleSTCaseCases
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
            	    if ( cnt37 >= 1 ) break loop37;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(37, input);
                        throw eee;
                }
                cnt37++;
            } while (true);

            // InternalGlobalConstantsParser.g:2098:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==ELSE) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2099:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalGlobalConstantsParser.g:2099:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalGlobalConstantsParser.g:2100:5: lv_else_4_0= ruleSTElsePart
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
    // InternalGlobalConstantsParser.g:2125:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalGlobalConstantsParser.g:2125:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalGlobalConstantsParser.g:2126:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalGlobalConstantsParser.g:2132:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2138:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:2139:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:2139:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:2140:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalGlobalConstantsParser.g:2140:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2141:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2141:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2142:5: lv_conditions_0_0= ruleSTExpression
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

            // InternalGlobalConstantsParser.g:2159:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==Comma) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2160:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:2164:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalGlobalConstantsParser.g:2165:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2165:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalGlobalConstantsParser.g:2166:6: lv_conditions_2_0= ruleSTExpression
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
            	    break loop39;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_35); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2188:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop40:
            do {
                int alt40=2;
                alt40 = dfa40.predict(input);
                switch (alt40) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2189:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2193:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2194:5: lv_statements_4_0= ruleSTStatement
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalGlobalConstantsParser.g:2215:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalGlobalConstantsParser.g:2215:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalGlobalConstantsParser.g:2216:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalGlobalConstantsParser.g:2222:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2228:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:2229:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:2229:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:2230:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalGlobalConstantsParser.g:2230:3: ()
            // InternalGlobalConstantsParser.g:2231:4: 
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
            // InternalGlobalConstantsParser.g:2241:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==LDATE_AND_TIME||LA41_0==DATE_AND_TIME||LA41_0==LTIME_OF_DAY||LA41_0==TIME_OF_DAY||LA41_0==CONTINUE||LA41_0==WSTRING||LA41_0==REPEAT||LA41_0==RETURN||LA41_0==STRING||LA41_0==DWORD||LA41_0==FALSE||(LA41_0>=LDATE && LA41_0<=LWORD)||(LA41_0>=UDINT && LA41_0<=ULINT)||(LA41_0>=USINT && LA41_0<=DINT)||LA41_0==EXIT||(LA41_0>=LINT && LA41_0<=LTOD)||(LA41_0>=REAL && LA41_0<=SINT)||(LA41_0>=THIS && LA41_0<=TRUE)||LA41_0==UINT||LA41_0==WORD||(LA41_0>=AND && LA41_0<=NOT)||LA41_0==TOD||LA41_0==XOR||(LA41_0>=DT && LA41_0<=LT)||LA41_0==OR||LA41_0==LeftParenthesis||LA41_0==PlusSign||LA41_0==HyphenMinus||LA41_0==Semicolon||(LA41_0>=D && LA41_0<=T)||(LA41_0>=RULE_NON_DECIMAL && LA41_0<=RULE_INT)||(LA41_0>=RULE_ID && LA41_0<=RULE_STRING)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2242:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2242:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2243:5: lv_statements_2_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalGlobalConstantsParser.g:2264:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2264:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalGlobalConstantsParser.g:2265:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalGlobalConstantsParser.g:2271:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
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
            // InternalGlobalConstantsParser.g:2277:2: ( (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalGlobalConstantsParser.g:2278:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalGlobalConstantsParser.g:2278:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalGlobalConstantsParser.g:2279:3: otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2283:3: ( (lv_variable_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2284:4: (lv_variable_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2284:4: (lv_variable_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2285:5: lv_variable_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:2306:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2307:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2307:4: (lv_from_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2308:5: lv_from_3_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:2329:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2330:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2330:4: (lv_to_5_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2331:5: lv_to_5_0= ruleSTExpression
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

            // InternalGlobalConstantsParser.g:2348:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==BY) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2349:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:2353:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:2354:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:2354:5: (lv_by_7_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:2355:6: lv_by_7_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:2377:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==LDATE_AND_TIME||LA43_0==DATE_AND_TIME||LA43_0==LTIME_OF_DAY||LA43_0==TIME_OF_DAY||LA43_0==CONTINUE||LA43_0==WSTRING||LA43_0==REPEAT||LA43_0==RETURN||LA43_0==STRING||LA43_0==DWORD||LA43_0==FALSE||(LA43_0>=LDATE && LA43_0<=LWORD)||(LA43_0>=UDINT && LA43_0<=ULINT)||(LA43_0>=USINT && LA43_0<=DINT)||LA43_0==EXIT||(LA43_0>=LINT && LA43_0<=LTOD)||(LA43_0>=REAL && LA43_0<=SINT)||(LA43_0>=THIS && LA43_0<=TRUE)||LA43_0==UINT||LA43_0==WORD||(LA43_0>=AND && LA43_0<=NOT)||LA43_0==TOD||LA43_0==XOR||(LA43_0>=DT && LA43_0<=LT)||LA43_0==OR||LA43_0==LeftParenthesis||LA43_0==PlusSign||LA43_0==HyphenMinus||LA43_0==Semicolon||(LA43_0>=D && LA43_0<=T)||(LA43_0>=RULE_NON_DECIMAL && LA43_0<=RULE_INT)||(LA43_0>=RULE_ID && LA43_0<=RULE_STRING)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2378:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2378:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2379:5: lv_statements_9_0= ruleSTStatement
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
            	    break loop43;
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
    // InternalGlobalConstantsParser.g:2404:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2404:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalGlobalConstantsParser.g:2405:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalGlobalConstantsParser.g:2411:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2417:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalGlobalConstantsParser.g:2418:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalGlobalConstantsParser.g:2418:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalGlobalConstantsParser.g:2419:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2423:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2424:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2424:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2425:5: lv_condition_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:2446:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==LDATE_AND_TIME||LA44_0==DATE_AND_TIME||LA44_0==LTIME_OF_DAY||LA44_0==TIME_OF_DAY||LA44_0==CONTINUE||LA44_0==WSTRING||LA44_0==REPEAT||LA44_0==RETURN||LA44_0==STRING||LA44_0==DWORD||LA44_0==FALSE||(LA44_0>=LDATE && LA44_0<=LWORD)||(LA44_0>=UDINT && LA44_0<=ULINT)||(LA44_0>=USINT && LA44_0<=DINT)||LA44_0==EXIT||(LA44_0>=LINT && LA44_0<=LTOD)||(LA44_0>=REAL && LA44_0<=SINT)||(LA44_0>=THIS && LA44_0<=TRUE)||LA44_0==UINT||LA44_0==WORD||(LA44_0>=AND && LA44_0<=NOT)||LA44_0==TOD||LA44_0==XOR||(LA44_0>=DT && LA44_0<=LT)||LA44_0==OR||LA44_0==LeftParenthesis||LA44_0==PlusSign||LA44_0==HyphenMinus||LA44_0==Semicolon||(LA44_0>=D && LA44_0<=T)||(LA44_0>=RULE_NON_DECIMAL && LA44_0<=RULE_INT)||(LA44_0>=RULE_ID && LA44_0<=RULE_STRING)) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2447:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2447:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2448:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop44;
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
    // InternalGlobalConstantsParser.g:2473:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2473:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalGlobalConstantsParser.g:2474:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalGlobalConstantsParser.g:2480:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2486:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalGlobalConstantsParser.g:2487:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalGlobalConstantsParser.g:2487:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalGlobalConstantsParser.g:2488:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_44); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2492:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==LDATE_AND_TIME||LA45_0==DATE_AND_TIME||LA45_0==LTIME_OF_DAY||LA45_0==TIME_OF_DAY||LA45_0==CONTINUE||LA45_0==WSTRING||LA45_0==REPEAT||LA45_0==RETURN||LA45_0==STRING||LA45_0==DWORD||LA45_0==FALSE||(LA45_0>=LDATE && LA45_0<=LWORD)||(LA45_0>=UDINT && LA45_0<=ULINT)||(LA45_0>=USINT && LA45_0<=DINT)||LA45_0==EXIT||(LA45_0>=LINT && LA45_0<=LTOD)||(LA45_0>=REAL && LA45_0<=SINT)||(LA45_0>=THIS && LA45_0<=TRUE)||LA45_0==UINT||LA45_0==WORD||(LA45_0>=AND && LA45_0<=NOT)||LA45_0==TOD||LA45_0==XOR||(LA45_0>=DT && LA45_0<=LT)||LA45_0==OR||LA45_0==LeftParenthesis||LA45_0==PlusSign||LA45_0==HyphenMinus||LA45_0==Semicolon||(LA45_0>=D && LA45_0<=T)||(LA45_0>=RULE_NON_DECIMAL && LA45_0<=RULE_INT)||(LA45_0>=RULE_ID && LA45_0<=RULE_STRING)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2493:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2493:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2494:5: lv_statements_1_0= ruleSTStatement
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
            	    break loop45;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2515:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2516:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2516:4: (lv_condition_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2517:5: lv_condition_3_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:2542:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2542:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalGlobalConstantsParser.g:2543:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalGlobalConstantsParser.g:2549:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2555:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalGlobalConstantsParser.g:2556:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalGlobalConstantsParser.g:2567:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2567:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalGlobalConstantsParser.g:2568:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalGlobalConstantsParser.g:2574:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2580:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2581:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2581:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2582:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2590:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==FullStopFullStop) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2591:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2591:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2592:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2592:5: ()
            	    // InternalGlobalConstantsParser.g:2593:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2599:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalGlobalConstantsParser.g:2600:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2600:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalGlobalConstantsParser.g:2601:7: lv_op_2_0= ruleSubrangeOperator
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

            	    // InternalGlobalConstantsParser.g:2619:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalGlobalConstantsParser.g:2620:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2620:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalGlobalConstantsParser.g:2621:6: lv_right_3_0= ruleSTOrExpression
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalGlobalConstantsParser.g:2643:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2643:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalGlobalConstantsParser.g:2644:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalGlobalConstantsParser.g:2650:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2656:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2657:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2657:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2658:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2666:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==OR) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2667:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2667:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2668:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2668:5: ()
            	    // InternalGlobalConstantsParser.g:2669:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2675:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalGlobalConstantsParser.g:2676:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2676:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalGlobalConstantsParser.g:2677:7: lv_op_2_0= ruleOrOperator
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

            	    // InternalGlobalConstantsParser.g:2695:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalGlobalConstantsParser.g:2696:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2696:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalGlobalConstantsParser.g:2697:6: lv_right_3_0= ruleSTXorExpression
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalGlobalConstantsParser.g:2719:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2719:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalGlobalConstantsParser.g:2720:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalGlobalConstantsParser.g:2726:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2732:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2733:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2733:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2734:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2742:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==XOR) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2743:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2743:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2744:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2744:5: ()
            	    // InternalGlobalConstantsParser.g:2745:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2751:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalGlobalConstantsParser.g:2752:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2752:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalGlobalConstantsParser.g:2753:7: lv_op_2_0= ruleXorOperator
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

            	    // InternalGlobalConstantsParser.g:2771:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalGlobalConstantsParser.g:2772:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2772:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalGlobalConstantsParser.g:2773:6: lv_right_3_0= ruleSTAndExpression
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalGlobalConstantsParser.g:2795:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2795:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalGlobalConstantsParser.g:2796:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalGlobalConstantsParser.g:2802:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2808:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2809:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2809:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2810:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2818:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==AND||LA49_0==Ampersand) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2819:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2819:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2820:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2820:5: ()
            	    // InternalGlobalConstantsParser.g:2821:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2827:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalGlobalConstantsParser.g:2828:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2828:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalGlobalConstantsParser.g:2829:7: lv_op_2_0= ruleAndOperator
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

            	    // InternalGlobalConstantsParser.g:2847:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalGlobalConstantsParser.g:2848:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2848:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalGlobalConstantsParser.g:2849:6: lv_right_3_0= ruleSTEqualityExpression
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
    // $ANTLR end "ruleSTAndExpression"


    // $ANTLR start "entryRuleSTEqualityExpression"
    // InternalGlobalConstantsParser.g:2871:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2871:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalGlobalConstantsParser.g:2872:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalGlobalConstantsParser.g:2878:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2884:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2885:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2885:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2886:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2894:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==LessThanSignGreaterThanSign||LA50_0==EqualsSign) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2895:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2895:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2896:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2896:5: ()
            	    // InternalGlobalConstantsParser.g:2897:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2903:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalGlobalConstantsParser.g:2904:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2904:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalGlobalConstantsParser.g:2905:7: lv_op_2_0= ruleEqualityOperator
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

            	    // InternalGlobalConstantsParser.g:2923:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalGlobalConstantsParser.g:2924:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2924:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalGlobalConstantsParser.g:2925:6: lv_right_3_0= ruleSTComparisonExpression
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalGlobalConstantsParser.g:2947:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2947:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalGlobalConstantsParser.g:2948:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalGlobalConstantsParser.g:2954:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2960:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2961:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2961:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2962:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2970:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==LessThanSignEqualsSign||LA51_0==GreaterThanSignEqualsSign||LA51_0==LessThanSign||LA51_0==GreaterThanSign) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2971:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2971:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2972:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2972:5: ()
            	    // InternalGlobalConstantsParser.g:2973:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2979:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalGlobalConstantsParser.g:2980:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2980:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalGlobalConstantsParser.g:2981:7: lv_op_2_0= ruleCompareOperator
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

            	    // InternalGlobalConstantsParser.g:2999:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalGlobalConstantsParser.g:3000:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3000:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalGlobalConstantsParser.g:3001:6: lv_right_3_0= ruleSTAddSubExpression
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
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalGlobalConstantsParser.g:3023:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3023:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalGlobalConstantsParser.g:3024:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalGlobalConstantsParser.g:3030:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3036:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:3037:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:3037:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalGlobalConstantsParser.g:3038:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:3046:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==PlusSign||LA52_0==HyphenMinus) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3047:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3047:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalGlobalConstantsParser.g:3048:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3048:5: ()
            	    // InternalGlobalConstantsParser.g:3049:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:3055:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalGlobalConstantsParser.g:3056:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:3056:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalGlobalConstantsParser.g:3057:7: lv_op_2_0= ruleAddSubOperator
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

            	    // InternalGlobalConstantsParser.g:3075:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalGlobalConstantsParser.g:3076:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3076:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalGlobalConstantsParser.g:3077:6: lv_right_3_0= ruleSTMulDivModExpression
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
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalGlobalConstantsParser.g:3099:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3099:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalGlobalConstantsParser.g:3100:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalGlobalConstantsParser.g:3106:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3112:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:3113:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:3113:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalGlobalConstantsParser.g:3114:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:3122:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==MOD||LA53_0==Asterisk||LA53_0==Solidus) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3123:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3123:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalGlobalConstantsParser.g:3124:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3124:5: ()
            	    // InternalGlobalConstantsParser.g:3125:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:3131:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalGlobalConstantsParser.g:3132:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:3132:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalGlobalConstantsParser.g:3133:7: lv_op_2_0= ruleMulDivModOperator
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

            	    // InternalGlobalConstantsParser.g:3151:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalGlobalConstantsParser.g:3152:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3152:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalGlobalConstantsParser.g:3153:6: lv_right_3_0= ruleSTPowerExpression
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
    // $ANTLR end "ruleSTMulDivModExpression"


    // $ANTLR start "entryRuleSTPowerExpression"
    // InternalGlobalConstantsParser.g:3175:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3175:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalGlobalConstantsParser.g:3176:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalGlobalConstantsParser.g:3182:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3188:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:3189:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:3189:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalGlobalConstantsParser.g:3190:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:3198:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==AsteriskAsterisk) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3199:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3199:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalGlobalConstantsParser.g:3200:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3200:5: ()
            	    // InternalGlobalConstantsParser.g:3201:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:3207:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalGlobalConstantsParser.g:3208:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:3208:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalGlobalConstantsParser.g:3209:7: lv_op_2_0= rulePowerOperator
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

            	    // InternalGlobalConstantsParser.g:3227:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalGlobalConstantsParser.g:3228:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3228:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalGlobalConstantsParser.g:3229:6: lv_right_3_0= ruleSTUnaryExpression
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTUnaryExpression"
    // InternalGlobalConstantsParser.g:3251:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3251:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalGlobalConstantsParser.g:3252:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalGlobalConstantsParser.g:3258:1: ruleSTUnaryExpression returns [EObject current=null] : (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        EObject this_STLiteralExpressions_1 = null;

        EObject this_STSignedLiteralExpressions_2 = null;

        Enumerator lv_op_4_0 = null;

        EObject lv_expression_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3264:2: ( (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalGlobalConstantsParser.g:3265:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalGlobalConstantsParser.g:3265:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )
            int alt55=4;
            alt55 = dfa55.predict(input);
            switch (alt55) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3266:3: this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalGlobalConstantsParser.g:3275:3: this_STLiteralExpressions_1= ruleSTLiteralExpressions
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
                    // InternalGlobalConstantsParser.g:3284:3: ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions )
                    {
                    // InternalGlobalConstantsParser.g:3284:3: ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions )
                    // InternalGlobalConstantsParser.g:3285:4: ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions
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
                    // InternalGlobalConstantsParser.g:3296:3: ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalGlobalConstantsParser.g:3296:3: ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) )
                    // InternalGlobalConstantsParser.g:3297:4: () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) )
                    {
                    // InternalGlobalConstantsParser.g:3297:4: ()
                    // InternalGlobalConstantsParser.g:3298:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_3_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGlobalConstantsParser.g:3304:4: ( (lv_op_4_0= ruleUnaryOperator ) )
                    // InternalGlobalConstantsParser.g:3305:5: (lv_op_4_0= ruleUnaryOperator )
                    {
                    // InternalGlobalConstantsParser.g:3305:5: (lv_op_4_0= ruleUnaryOperator )
                    // InternalGlobalConstantsParser.g:3306:6: lv_op_4_0= ruleUnaryOperator
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

                    // InternalGlobalConstantsParser.g:3323:4: ( (lv_expression_5_0= ruleSTUnaryExpression ) )
                    // InternalGlobalConstantsParser.g:3324:5: (lv_expression_5_0= ruleSTUnaryExpression )
                    {
                    // InternalGlobalConstantsParser.g:3324:5: (lv_expression_5_0= ruleSTUnaryExpression )
                    // InternalGlobalConstantsParser.g:3325:6: lv_expression_5_0= ruleSTUnaryExpression
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
    // InternalGlobalConstantsParser.g:3347:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3347:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalGlobalConstantsParser.g:3348:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalGlobalConstantsParser.g:3354:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalGlobalConstantsParser.g:3360:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalGlobalConstantsParser.g:3361:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalGlobalConstantsParser.g:3361:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalGlobalConstantsParser.g:3362:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
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
            // InternalGlobalConstantsParser.g:3370:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop58:
            do {
                int alt58=3;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==FullStop) ) {
                    alt58=1;
                }
                else if ( (LA58_0==LeftSquareBracket) ) {
                    alt58=2;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3371:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3371:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalGlobalConstantsParser.g:3372:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3372:5: ()
            	    // InternalGlobalConstantsParser.g:3373:6: 
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
            	    // InternalGlobalConstantsParser.g:3383:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalGlobalConstantsParser.g:3384:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3384:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalGlobalConstantsParser.g:3385:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3385:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt56=2;
            	    int LA56_0 = input.LA(1);

            	    if ( (LA56_0==AND||LA56_0==MOD||LA56_0==XOR||LA56_0==DT||(LA56_0>=LD && LA56_0<=LT)||LA56_0==OR||LA56_0==D||LA56_0==RULE_ID) ) {
            	        alt56=1;
            	    }
            	    else if ( ((LA56_0>=B && LA56_0<=X)||LA56_0==LeftParenthesis||LA56_0==RULE_INT) ) {
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
            	            // InternalGlobalConstantsParser.g:3386:8: lv_member_3_1= ruleSTFeatureExpression
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
            	            // InternalGlobalConstantsParser.g:3402:8: lv_member_3_2= ruleSTMultibitPartialExpression
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
            	    // InternalGlobalConstantsParser.g:3422:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalGlobalConstantsParser.g:3422:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalGlobalConstantsParser.g:3423:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalGlobalConstantsParser.g:3423:5: ()
            	    // InternalGlobalConstantsParser.g:3424:6: 
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
            	    // InternalGlobalConstantsParser.g:3434:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalGlobalConstantsParser.g:3435:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3435:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalGlobalConstantsParser.g:3436:7: lv_index_6_0= ruleSTExpression
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

            	    // InternalGlobalConstantsParser.g:3453:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop57:
            	    do {
            	        int alt57=2;
            	        int LA57_0 = input.LA(1);

            	        if ( (LA57_0==Comma) ) {
            	            alt57=1;
            	        }


            	        switch (alt57) {
            	    	case 1 :
            	    	    // InternalGlobalConstantsParser.g:3454:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalGlobalConstantsParser.g:3458:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalGlobalConstantsParser.g:3459:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalGlobalConstantsParser.g:3459:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalGlobalConstantsParser.g:3460:8: lv_index_8_0= ruleSTExpression
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
            	    	    break loop57;
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
    // $ANTLR end "ruleSTAccessExpression"


    // $ANTLR start "entryRuleSTPrimaryExpression"
    // InternalGlobalConstantsParser.g:3488:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3488:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalGlobalConstantsParser.g:3489:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalGlobalConstantsParser.g:3495:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STBuiltinFeatureExpression_4 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3501:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression ) )
            // InternalGlobalConstantsParser.g:3502:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression )
            {
            // InternalGlobalConstantsParser.g:3502:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression )
            int alt59=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt59=1;
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
                alt59=2;
                }
                break;
            case THIS:
                {
                alt59=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3503:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3503:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalGlobalConstantsParser.g:3504:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
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
                    // InternalGlobalConstantsParser.g:3522:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalGlobalConstantsParser.g:3531:3: this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression
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
    // InternalGlobalConstantsParser.g:3543:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3543:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalGlobalConstantsParser.g:3544:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalGlobalConstantsParser.g:3550:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token lv_call_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3556:2: ( ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:3557:2: ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:3557:2: ( () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:3558:3: () ( ( ruleSTFeatureName ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:3558:3: ()
            // InternalGlobalConstantsParser.g:3559:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3565:3: ( ( ruleSTFeatureName ) )
            // InternalGlobalConstantsParser.g:3566:4: ( ruleSTFeatureName )
            {
            // InternalGlobalConstantsParser.g:3566:4: ( ruleSTFeatureName )
            // InternalGlobalConstantsParser.g:3567:5: ruleSTFeatureName
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

            // InternalGlobalConstantsParser.g:3581:3: ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==LeftParenthesis) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3582:4: ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalGlobalConstantsParser.g:3582:4: ( (lv_call_2_0= LeftParenthesis ) )
                    // InternalGlobalConstantsParser.g:3583:5: (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3583:5: (lv_call_2_0= LeftParenthesis )
                    // InternalGlobalConstantsParser.g:3584:6: lv_call_2_0= LeftParenthesis
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

                    // InternalGlobalConstantsParser.g:3596:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==LDATE_AND_TIME||LA61_0==DATE_AND_TIME||LA61_0==LTIME_OF_DAY||LA61_0==TIME_OF_DAY||LA61_0==WSTRING||LA61_0==STRING||LA61_0==DWORD||LA61_0==FALSE||(LA61_0>=LDATE && LA61_0<=LWORD)||(LA61_0>=UDINT && LA61_0<=ULINT)||(LA61_0>=USINT && LA61_0<=WCHAR)||(LA61_0>=BOOL && LA61_0<=BYTE)||(LA61_0>=CHAR && LA61_0<=DINT)||(LA61_0>=LINT && LA61_0<=LTOD)||(LA61_0>=REAL && LA61_0<=SINT)||(LA61_0>=THIS && LA61_0<=TRUE)||LA61_0==UINT||LA61_0==WORD||LA61_0==AND||(LA61_0>=INT && LA61_0<=NOT)||LA61_0==TOD||LA61_0==XOR||LA61_0==DT||(LA61_0>=LD && LA61_0<=LT)||LA61_0==OR||LA61_0==LeftParenthesis||LA61_0==PlusSign||LA61_0==HyphenMinus||(LA61_0>=D && LA61_0<=T)||(LA61_0>=RULE_NON_DECIMAL && LA61_0<=RULE_INT)||(LA61_0>=RULE_ID && LA61_0<=RULE_STRING)) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:3597:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalGlobalConstantsParser.g:3597:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalGlobalConstantsParser.g:3598:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalGlobalConstantsParser.g:3598:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalGlobalConstantsParser.g:3599:7: lv_parameters_3_0= ruleSTCallArgument
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

                            // InternalGlobalConstantsParser.g:3616:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop60:
                            do {
                                int alt60=2;
                                int LA60_0 = input.LA(1);

                                if ( (LA60_0==Comma) ) {
                                    alt60=1;
                                }


                                switch (alt60) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:3617:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalGlobalConstantsParser.g:3621:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalGlobalConstantsParser.g:3622:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalGlobalConstantsParser.g:3622:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalGlobalConstantsParser.g:3623:8: lv_parameters_5_0= ruleSTCallArgument
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
                            	    break loop60;
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
    // InternalGlobalConstantsParser.g:3651:1: entryRuleSTFeatureName returns [String current=null] : iv_ruleSTFeatureName= ruleSTFeatureName EOF ;
    public final String entryRuleSTFeatureName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTFeatureName = null;


        try {
            // InternalGlobalConstantsParser.g:3651:53: (iv_ruleSTFeatureName= ruleSTFeatureName EOF )
            // InternalGlobalConstantsParser.g:3652:2: iv_ruleSTFeatureName= ruleSTFeatureName EOF
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
    // InternalGlobalConstantsParser.g:3658:1: ruleSTFeatureName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTFeatureName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3664:2: ( (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) )
            // InternalGlobalConstantsParser.g:3665:2: (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            {
            // InternalGlobalConstantsParser.g:3665:2: (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            int alt63=9;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt63=1;
                }
                break;
            case LT:
                {
                alt63=2;
                }
                break;
            case AND:
                {
                alt63=3;
                }
                break;
            case OR:
                {
                alt63=4;
                }
                break;
            case XOR:
                {
                alt63=5;
                }
                break;
            case MOD:
                {
                alt63=6;
                }
                break;
            case D:
                {
                alt63=7;
                }
                break;
            case DT:
                {
                alt63=8;
                }
                break;
            case LD:
                {
                alt63=9;
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
                    // InternalGlobalConstantsParser.g:3666:3: this_QualifiedName_0= ruleQualifiedName
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
                    // InternalGlobalConstantsParser.g:3677:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:3683:3: kw= AND
                    {
                    kw=(Token)match(input,AND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getANDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:3689:3: kw= OR
                    {
                    kw=(Token)match(input,OR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getORKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:3695:3: kw= XOR
                    {
                    kw=(Token)match(input,XOR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getXORKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsParser.g:3701:3: kw= MOD
                    {
                    kw=(Token)match(input,MOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getMODKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsParser.g:3707:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGlobalConstantsParser.g:3713:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGlobalConstantsParser.g:3719:3: kw= LD
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
    // InternalGlobalConstantsParser.g:3728:1: entryRuleSTBuiltinFeatureExpression returns [EObject current=null] : iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF ;
    public final EObject entryRuleSTBuiltinFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTBuiltinFeatureExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3728:67: (iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF )
            // InternalGlobalConstantsParser.g:3729:2: iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF
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
    // InternalGlobalConstantsParser.g:3735:1: ruleSTBuiltinFeatureExpression returns [EObject current=null] : ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
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
            // InternalGlobalConstantsParser.g:3741:2: ( ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:3742:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:3742:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:3743:3: () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:3743:3: ()
            // InternalGlobalConstantsParser.g:3744:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTBuiltinFeatureExpressionAccess().getSTBuiltinFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3750:3: ( (lv_feature_1_0= ruleSTBuiltinFeature ) )
            // InternalGlobalConstantsParser.g:3751:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            {
            // InternalGlobalConstantsParser.g:3751:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            // InternalGlobalConstantsParser.g:3752:5: lv_feature_1_0= ruleSTBuiltinFeature
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

            // InternalGlobalConstantsParser.g:3769:3: ( ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==LeftParenthesis) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3770:4: ( (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalGlobalConstantsParser.g:3770:4: ( (lv_call_2_0= LeftParenthesis ) )
                    // InternalGlobalConstantsParser.g:3771:5: (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3771:5: (lv_call_2_0= LeftParenthesis )
                    // InternalGlobalConstantsParser.g:3772:6: lv_call_2_0= LeftParenthesis
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

                    // InternalGlobalConstantsParser.g:3784:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==LDATE_AND_TIME||LA65_0==DATE_AND_TIME||LA65_0==LTIME_OF_DAY||LA65_0==TIME_OF_DAY||LA65_0==WSTRING||LA65_0==STRING||LA65_0==DWORD||LA65_0==FALSE||(LA65_0>=LDATE && LA65_0<=LWORD)||(LA65_0>=UDINT && LA65_0<=ULINT)||(LA65_0>=USINT && LA65_0<=WCHAR)||(LA65_0>=BOOL && LA65_0<=BYTE)||(LA65_0>=CHAR && LA65_0<=DINT)||(LA65_0>=LINT && LA65_0<=LTOD)||(LA65_0>=REAL && LA65_0<=SINT)||(LA65_0>=THIS && LA65_0<=TRUE)||LA65_0==UINT||LA65_0==WORD||LA65_0==AND||(LA65_0>=INT && LA65_0<=NOT)||LA65_0==TOD||LA65_0==XOR||LA65_0==DT||(LA65_0>=LD && LA65_0<=LT)||LA65_0==OR||LA65_0==LeftParenthesis||LA65_0==PlusSign||LA65_0==HyphenMinus||(LA65_0>=D && LA65_0<=T)||(LA65_0>=RULE_NON_DECIMAL && LA65_0<=RULE_INT)||(LA65_0>=RULE_ID && LA65_0<=RULE_STRING)) ) {
                        alt65=1;
                    }
                    switch (alt65) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:3785:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalGlobalConstantsParser.g:3785:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalGlobalConstantsParser.g:3786:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalGlobalConstantsParser.g:3786:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalGlobalConstantsParser.g:3787:7: lv_parameters_3_0= ruleSTCallArgument
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

                            // InternalGlobalConstantsParser.g:3804:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop64:
                            do {
                                int alt64=2;
                                int LA64_0 = input.LA(1);

                                if ( (LA64_0==Comma) ) {
                                    alt64=1;
                                }


                                switch (alt64) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:3805:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTBuiltinFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalGlobalConstantsParser.g:3809:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalGlobalConstantsParser.g:3810:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalGlobalConstantsParser.g:3810:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalGlobalConstantsParser.g:3811:8: lv_parameters_5_0= ruleSTCallArgument
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
                            	    break loop64;
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
    // InternalGlobalConstantsParser.g:3839:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3839:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalGlobalConstantsParser.g:3840:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalGlobalConstantsParser.g:3846:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_specifier_1_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3852:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) )
            // InternalGlobalConstantsParser.g:3853:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            {
            // InternalGlobalConstantsParser.g:3853:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            // InternalGlobalConstantsParser.g:3854:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            {
            // InternalGlobalConstantsParser.g:3854:3: ()
            // InternalGlobalConstantsParser.g:3855:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3861:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( ((LA67_0>=B && LA67_0<=X)) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3862:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalGlobalConstantsParser.g:3862:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalGlobalConstantsParser.g:3863:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
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

            // InternalGlobalConstantsParser.g:3880:3: ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_INT) ) {
                alt68=1;
            }
            else if ( (LA68_0==LeftParenthesis) ) {
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
                    // InternalGlobalConstantsParser.g:3881:4: ( (lv_index_2_0= RULE_INT ) )
                    {
                    // InternalGlobalConstantsParser.g:3881:4: ( (lv_index_2_0= RULE_INT ) )
                    // InternalGlobalConstantsParser.g:3882:5: (lv_index_2_0= RULE_INT )
                    {
                    // InternalGlobalConstantsParser.g:3882:5: (lv_index_2_0= RULE_INT )
                    // InternalGlobalConstantsParser.g:3883:6: lv_index_2_0= RULE_INT
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
                    // InternalGlobalConstantsParser.g:3900:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3900:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    // InternalGlobalConstantsParser.g:3901:5: otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
                      				
                    }
                    // InternalGlobalConstantsParser.g:3905:5: ( (lv_expression_4_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:3906:6: (lv_expression_4_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:3906:6: (lv_expression_4_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:3907:7: lv_expression_4_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:3934:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalGlobalConstantsParser.g:3934:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalGlobalConstantsParser.g:3935:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalGlobalConstantsParser.g:3941:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
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
            // InternalGlobalConstantsParser.g:3947:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalGlobalConstantsParser.g:3948:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalGlobalConstantsParser.g:3948:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt69=6;
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
                alt69=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt69=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt69=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt69=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt69=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt69=6;
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
                    // InternalGlobalConstantsParser.g:3949:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalGlobalConstantsParser.g:3958:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalGlobalConstantsParser.g:3967:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalGlobalConstantsParser.g:3976:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalGlobalConstantsParser.g:3985:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalGlobalConstantsParser.g:3994:3: this_STStringLiteral_5= ruleSTStringLiteral
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


    // $ANTLR start "entryRuleSTSignedLiteralExpressions"
    // InternalGlobalConstantsParser.g:4006:1: entryRuleSTSignedLiteralExpressions returns [EObject current=null] : iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF ;
    public final EObject entryRuleSTSignedLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignedLiteralExpressions = null;


        try {
            // InternalGlobalConstantsParser.g:4006:67: (iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF )
            // InternalGlobalConstantsParser.g:4007:2: iv_ruleSTSignedLiteralExpressions= ruleSTSignedLiteralExpressions EOF
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
    // InternalGlobalConstantsParser.g:4013:1: ruleSTSignedLiteralExpressions returns [EObject current=null] : this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral ;
    public final EObject ruleSTSignedLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject this_STSignedNumericLiteral_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4019:2: (this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral )
            // InternalGlobalConstantsParser.g:4020:2: this_STSignedNumericLiteral_0= ruleSTSignedNumericLiteral
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
    // InternalGlobalConstantsParser.g:4031:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:4031:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalGlobalConstantsParser.g:4032:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalGlobalConstantsParser.g:4038:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4044:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalGlobalConstantsParser.g:4045:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalGlobalConstantsParser.g:4045:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==DWORD||LA70_0==LWORD||(LA70_0>=BOOL && LA70_0<=BYTE)||LA70_0==WORD) ) {
                alt70=1;
            }
            else if ( (LA70_0==LREAL||(LA70_0>=UDINT && LA70_0<=ULINT)||LA70_0==USINT||LA70_0==DINT||LA70_0==LINT||(LA70_0>=REAL && LA70_0<=SINT)||LA70_0==UINT||LA70_0==INT) ) {
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
                    // InternalGlobalConstantsParser.g:4046:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalGlobalConstantsParser.g:4057:3: this_STAnyNumType_1= ruleSTAnyNumType
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
    // InternalGlobalConstantsParser.g:4071:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4071:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalGlobalConstantsParser.g:4072:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalGlobalConstantsParser.g:4078:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;

        AntlrDatatypeRuleToken lv_value_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4084:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) ) )
            // InternalGlobalConstantsParser.g:4085:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )
            {
            // InternalGlobalConstantsParser.g:4085:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )
            int alt72=2;
            alt72 = dfa72.predict(input);
            switch (alt72) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4086:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) )
                    {
                    // InternalGlobalConstantsParser.g:4086:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) )
                    // InternalGlobalConstantsParser.g:4087:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) )
                    {
                    // InternalGlobalConstantsParser.g:4087:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalGlobalConstantsParser.g:4088:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalGlobalConstantsParser.g:4088:5: ( ruleSTNumericLiteralType )
                    // InternalGlobalConstantsParser.g:4089:6: ruleSTNumericLiteralType
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
                    // InternalGlobalConstantsParser.g:4107:4: ( (lv_value_2_0= ruleSignedNumeric ) )
                    // InternalGlobalConstantsParser.g:4108:5: (lv_value_2_0= ruleSignedNumeric )
                    {
                    // InternalGlobalConstantsParser.g:4108:5: (lv_value_2_0= ruleSignedNumeric )
                    // InternalGlobalConstantsParser.g:4109:6: lv_value_2_0= ruleSignedNumeric
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
                    // InternalGlobalConstantsParser.g:4128:3: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) )
                    {
                    // InternalGlobalConstantsParser.g:4128:3: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) )
                    // InternalGlobalConstantsParser.g:4129:4: ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) )
                    {
                    // InternalGlobalConstantsParser.g:4129:4: ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==DWORD||LA71_0==LREAL||LA71_0==LWORD||(LA71_0>=UDINT && LA71_0<=ULINT)||LA71_0==USINT||(LA71_0>=BOOL && LA71_0<=BYTE)||LA71_0==DINT||LA71_0==LINT||(LA71_0>=REAL && LA71_0<=SINT)||LA71_0==UINT||LA71_0==WORD||LA71_0==INT) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:4130:5: ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign
                            {
                            // InternalGlobalConstantsParser.g:4130:5: ( ( ruleSTNumericLiteralType ) )
                            // InternalGlobalConstantsParser.g:4131:6: ( ruleSTNumericLiteralType )
                            {
                            // InternalGlobalConstantsParser.g:4131:6: ( ruleSTNumericLiteralType )
                            // InternalGlobalConstantsParser.g:4132:7: ruleSTNumericLiteralType
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

                    // InternalGlobalConstantsParser.g:4151:4: ( (lv_value_5_0= ruleNumeric ) )
                    // InternalGlobalConstantsParser.g:4152:5: (lv_value_5_0= ruleNumeric )
                    {
                    // InternalGlobalConstantsParser.g:4152:5: (lv_value_5_0= ruleNumeric )
                    // InternalGlobalConstantsParser.g:4153:6: lv_value_5_0= ruleNumeric
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
    // InternalGlobalConstantsParser.g:4175:1: entryRuleSTSignedNumericLiteral returns [EObject current=null] : iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF ;
    public final EObject entryRuleSTSignedNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSignedNumericLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4175:63: (iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF )
            // InternalGlobalConstantsParser.g:4176:2: iv_ruleSTSignedNumericLiteral= ruleSTSignedNumericLiteral EOF
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
    // InternalGlobalConstantsParser.g:4182:1: ruleSTSignedNumericLiteral returns [EObject current=null] : ( (lv_value_0_0= ruleSignedNumeric ) ) ;
    public final EObject ruleSTSignedNumericLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4188:2: ( ( (lv_value_0_0= ruleSignedNumeric ) ) )
            // InternalGlobalConstantsParser.g:4189:2: ( (lv_value_0_0= ruleSignedNumeric ) )
            {
            // InternalGlobalConstantsParser.g:4189:2: ( (lv_value_0_0= ruleSignedNumeric ) )
            // InternalGlobalConstantsParser.g:4190:3: (lv_value_0_0= ruleSignedNumeric )
            {
            // InternalGlobalConstantsParser.g:4190:3: (lv_value_0_0= ruleSignedNumeric )
            // InternalGlobalConstantsParser.g:4191:4: lv_value_0_0= ruleSignedNumeric
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
    // InternalGlobalConstantsParser.g:4211:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:4211:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalGlobalConstantsParser.g:4212:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalGlobalConstantsParser.g:4218:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4224:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalGlobalConstantsParser.g:4225:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalGlobalConstantsParser.g:4225:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt73=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt73=1;
                }
                break;
            case D:
                {
                alt73=2;
                }
                break;
            case LD:
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
                    // InternalGlobalConstantsParser.g:4226:3: this_STDateType_0= ruleSTDateType
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
                    // InternalGlobalConstantsParser.g:4237:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4243:3: kw= LD
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
    // InternalGlobalConstantsParser.g:4252:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4252:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalGlobalConstantsParser.g:4253:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalGlobalConstantsParser.g:4259:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4265:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalGlobalConstantsParser.g:4266:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalGlobalConstantsParser.g:4266:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalGlobalConstantsParser.g:4267:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalGlobalConstantsParser.g:4267:3: ( ( ruleSTDateLiteralType ) )
            // InternalGlobalConstantsParser.g:4268:4: ( ruleSTDateLiteralType )
            {
            // InternalGlobalConstantsParser.g:4268:4: ( ruleSTDateLiteralType )
            // InternalGlobalConstantsParser.g:4269:5: ruleSTDateLiteralType
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
            // InternalGlobalConstantsParser.g:4287:3: ( (lv_value_2_0= ruleDate ) )
            // InternalGlobalConstantsParser.g:4288:4: (lv_value_2_0= ruleDate )
            {
            // InternalGlobalConstantsParser.g:4288:4: (lv_value_2_0= ruleDate )
            // InternalGlobalConstantsParser.g:4289:5: lv_value_2_0= ruleDate
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
    // InternalGlobalConstantsParser.g:4310:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:4310:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalGlobalConstantsParser.g:4311:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalGlobalConstantsParser.g:4317:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4323:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalGlobalConstantsParser.g:4324:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalGlobalConstantsParser.g:4324:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt74=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt74=1;
                }
                break;
            case T:
                {
                alt74=2;
                }
                break;
            case LT:
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
                    // InternalGlobalConstantsParser.g:4325:3: this_STAnyDurationType_0= ruleSTAnyDurationType
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
                    // InternalGlobalConstantsParser.g:4336:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4342:3: kw= LT
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
    // InternalGlobalConstantsParser.g:4351:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4351:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalGlobalConstantsParser.g:4352:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalGlobalConstantsParser.g:4358:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4364:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalGlobalConstantsParser.g:4365:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalGlobalConstantsParser.g:4365:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalGlobalConstantsParser.g:4366:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalGlobalConstantsParser.g:4366:3: ( ( ruleSTTimeLiteralType ) )
            // InternalGlobalConstantsParser.g:4367:4: ( ruleSTTimeLiteralType )
            {
            // InternalGlobalConstantsParser.g:4367:4: ( ruleSTTimeLiteralType )
            // InternalGlobalConstantsParser.g:4368:5: ruleSTTimeLiteralType
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
            // InternalGlobalConstantsParser.g:4386:3: ( (lv_value_2_0= ruleTime ) )
            // InternalGlobalConstantsParser.g:4387:4: (lv_value_2_0= ruleTime )
            {
            // InternalGlobalConstantsParser.g:4387:4: (lv_value_2_0= ruleTime )
            // InternalGlobalConstantsParser.g:4388:5: lv_value_2_0= ruleTime
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
    // InternalGlobalConstantsParser.g:4409:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4409:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalGlobalConstantsParser.g:4410:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalGlobalConstantsParser.g:4416:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4422:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalGlobalConstantsParser.g:4423:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalGlobalConstantsParser.g:4423:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalGlobalConstantsParser.g:4424:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalGlobalConstantsParser.g:4424:3: ( ( ruleSTTimeOfDayType ) )
            // InternalGlobalConstantsParser.g:4425:4: ( ruleSTTimeOfDayType )
            {
            // InternalGlobalConstantsParser.g:4425:4: ( ruleSTTimeOfDayType )
            // InternalGlobalConstantsParser.g:4426:5: ruleSTTimeOfDayType
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
            // InternalGlobalConstantsParser.g:4444:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalGlobalConstantsParser.g:4445:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalGlobalConstantsParser.g:4445:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalGlobalConstantsParser.g:4446:5: lv_value_2_0= ruleTimeOfDay
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
    // InternalGlobalConstantsParser.g:4467:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4467:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalGlobalConstantsParser.g:4468:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalGlobalConstantsParser.g:4474:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4480:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalGlobalConstantsParser.g:4481:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalGlobalConstantsParser.g:4481:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalGlobalConstantsParser.g:4482:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalGlobalConstantsParser.g:4482:3: ( ( ruleSTDateAndTimeType ) )
            // InternalGlobalConstantsParser.g:4483:4: ( ruleSTDateAndTimeType )
            {
            // InternalGlobalConstantsParser.g:4483:4: ( ruleSTDateAndTimeType )
            // InternalGlobalConstantsParser.g:4484:5: ruleSTDateAndTimeType
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
            // InternalGlobalConstantsParser.g:4502:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalGlobalConstantsParser.g:4503:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalGlobalConstantsParser.g:4503:4: (lv_value_2_0= ruleDateAndTime )
            // InternalGlobalConstantsParser.g:4504:5: lv_value_2_0= ruleDateAndTime
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
    // InternalGlobalConstantsParser.g:4525:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4525:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalGlobalConstantsParser.g:4526:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalGlobalConstantsParser.g:4532:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4538:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) )
            // InternalGlobalConstantsParser.g:4539:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            {
            // InternalGlobalConstantsParser.g:4539:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            // InternalGlobalConstantsParser.g:4540:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) )
            {
            // InternalGlobalConstantsParser.g:4540:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==WSTRING||LA75_0==STRING||LA75_0==WCHAR||LA75_0==CHAR) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4541:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalGlobalConstantsParser.g:4541:4: ( ( ruleSTAnyCharsType ) )
                    // InternalGlobalConstantsParser.g:4542:5: ( ruleSTAnyCharsType )
                    {
                    // InternalGlobalConstantsParser.g:4542:5: ( ruleSTAnyCharsType )
                    // InternalGlobalConstantsParser.g:4543:6: ruleSTAnyCharsType
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

            // InternalGlobalConstantsParser.g:4562:3: ( (lv_value_2_0= RULE_STRING ) )
            // InternalGlobalConstantsParser.g:4563:4: (lv_value_2_0= RULE_STRING )
            {
            // InternalGlobalConstantsParser.g:4563:4: (lv_value_2_0= RULE_STRING )
            // InternalGlobalConstantsParser.g:4564:5: lv_value_2_0= RULE_STRING
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
    // InternalGlobalConstantsParser.g:4584:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalGlobalConstantsParser.g:4584:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalGlobalConstantsParser.g:4585:2: iv_ruleSTAnyType= ruleSTAnyType EOF
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
    // InternalGlobalConstantsParser.g:4591:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_QualifiedName_0 = null;

        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4597:2: ( (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalGlobalConstantsParser.g:4598:2: (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalGlobalConstantsParser.g:4598:2: (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==RULE_ID) ) {
                alt76=1;
            }
            else if ( (LA76_0==LDATE_AND_TIME||LA76_0==DATE_AND_TIME||LA76_0==LTIME_OF_DAY||LA76_0==TIME_OF_DAY||LA76_0==WSTRING||LA76_0==STRING||LA76_0==DWORD||(LA76_0>=LDATE && LA76_0<=LWORD)||(LA76_0>=UDINT && LA76_0<=ULINT)||(LA76_0>=USINT && LA76_0<=WCHAR)||(LA76_0>=BOOL && LA76_0<=BYTE)||(LA76_0>=CHAR && LA76_0<=DINT)||(LA76_0>=LINT && LA76_0<=LTOD)||(LA76_0>=REAL && LA76_0<=SINT)||LA76_0==TIME||LA76_0==UINT||LA76_0==WORD||(LA76_0>=INT && LA76_0<=LDT)||LA76_0==TOD||LA76_0==DT) ) {
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
                    // InternalGlobalConstantsParser.g:4599:3: this_QualifiedName_0= ruleQualifiedName
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
                    // InternalGlobalConstantsParser.g:4610:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
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
    // InternalGlobalConstantsParser.g:4624:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalGlobalConstantsParser.g:4624:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalGlobalConstantsParser.g:4625:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
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
    // InternalGlobalConstantsParser.g:4631:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4637:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalGlobalConstantsParser.g:4638:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalGlobalConstantsParser.g:4638:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt77=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt77=1;
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
                alt77=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt77=3;
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
                alt77=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
                {
                alt77=5;
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
                    // InternalGlobalConstantsParser.g:4639:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalGlobalConstantsParser.g:4650:3: this_STAnyNumType_1= ruleSTAnyNumType
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
                    // InternalGlobalConstantsParser.g:4661:3: this_STAnyDurationType_2= ruleSTAnyDurationType
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
                    // InternalGlobalConstantsParser.g:4672:3: this_STAnyDateType_3= ruleSTAnyDateType
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
                    // InternalGlobalConstantsParser.g:4683:3: this_STAnyCharsType_4= ruleSTAnyCharsType
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
    // InternalGlobalConstantsParser.g:4697:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalGlobalConstantsParser.g:4697:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalGlobalConstantsParser.g:4698:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
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
    // InternalGlobalConstantsParser.g:4704:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4710:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalGlobalConstantsParser.g:4711:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalGlobalConstantsParser.g:4711:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt78=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt78=1;
                }
                break;
            case BYTE:
                {
                alt78=2;
                }
                break;
            case WORD:
                {
                alt78=3;
                }
                break;
            case DWORD:
                {
                alt78=4;
                }
                break;
            case LWORD:
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
                    // InternalGlobalConstantsParser.g:4712:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4718:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4724:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4730:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:4736:3: kw= LWORD
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
    // InternalGlobalConstantsParser.g:4745:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalGlobalConstantsParser.g:4745:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalGlobalConstantsParser.g:4746:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
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
    // InternalGlobalConstantsParser.g:4752:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4758:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalGlobalConstantsParser.g:4759:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalGlobalConstantsParser.g:4759:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt79=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt79=1;
                }
                break;
            case INT:
                {
                alt79=2;
                }
                break;
            case DINT:
                {
                alt79=3;
                }
                break;
            case LINT:
                {
                alt79=4;
                }
                break;
            case USINT:
                {
                alt79=5;
                }
                break;
            case UINT:
                {
                alt79=6;
                }
                break;
            case UDINT:
                {
                alt79=7;
                }
                break;
            case ULINT:
                {
                alt79=8;
                }
                break;
            case REAL:
                {
                alt79=9;
                }
                break;
            case LREAL:
                {
                alt79=10;
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
                    // InternalGlobalConstantsParser.g:4760:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4766:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4772:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4778:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:4784:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsParser.g:4790:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsParser.g:4796:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGlobalConstantsParser.g:4802:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGlobalConstantsParser.g:4808:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalGlobalConstantsParser.g:4814:3: kw= LREAL
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
    // InternalGlobalConstantsParser.g:4823:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalGlobalConstantsParser.g:4823:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalGlobalConstantsParser.g:4824:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
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
    // InternalGlobalConstantsParser.g:4830:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4836:2: ( (kw= TIME | kw= LTIME ) )
            // InternalGlobalConstantsParser.g:4837:2: (kw= TIME | kw= LTIME )
            {
            // InternalGlobalConstantsParser.g:4837:2: (kw= TIME | kw= LTIME )
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==TIME) ) {
                alt80=1;
            }
            else if ( (LA80_0==LTIME) ) {
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
                    // InternalGlobalConstantsParser.g:4838:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4844:3: kw= LTIME
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
    // InternalGlobalConstantsParser.g:4853:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalGlobalConstantsParser.g:4853:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalGlobalConstantsParser.g:4854:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
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
    // InternalGlobalConstantsParser.g:4860:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4866:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalGlobalConstantsParser.g:4867:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalGlobalConstantsParser.g:4867:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt81=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt81=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt81=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt81=3;
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
                    // InternalGlobalConstantsParser.g:4868:3: this_STDateType_0= ruleSTDateType
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
                    // InternalGlobalConstantsParser.g:4879:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
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
                    // InternalGlobalConstantsParser.g:4890:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
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
    // InternalGlobalConstantsParser.g:4904:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalGlobalConstantsParser.g:4904:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalGlobalConstantsParser.g:4905:2: iv_ruleSTDateType= ruleSTDateType EOF
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
    // InternalGlobalConstantsParser.g:4911:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4917:2: ( (kw= DATE | kw= LDATE ) )
            // InternalGlobalConstantsParser.g:4918:2: (kw= DATE | kw= LDATE )
            {
            // InternalGlobalConstantsParser.g:4918:2: (kw= DATE | kw= LDATE )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==DATE) ) {
                alt82=1;
            }
            else if ( (LA82_0==LDATE) ) {
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
                    // InternalGlobalConstantsParser.g:4919:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4925:3: kw= LDATE
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
    // InternalGlobalConstantsParser.g:4934:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalGlobalConstantsParser.g:4934:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalGlobalConstantsParser.g:4935:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
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
    // InternalGlobalConstantsParser.g:4941:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4947:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalGlobalConstantsParser.g:4948:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalGlobalConstantsParser.g:4948:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt83=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt83=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt83=2;
                }
                break;
            case TOD:
                {
                alt83=3;
                }
                break;
            case LTOD:
                {
                alt83=4;
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
                    // InternalGlobalConstantsParser.g:4949:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4955:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4961:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4967:3: kw= LTOD
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
    // InternalGlobalConstantsParser.g:4976:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalGlobalConstantsParser.g:4976:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalGlobalConstantsParser.g:4977:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
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
    // InternalGlobalConstantsParser.g:4983:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4989:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalGlobalConstantsParser.g:4990:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalGlobalConstantsParser.g:4990:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt84=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt84=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt84=2;
                }
                break;
            case DT:
                {
                alt84=3;
                }
                break;
            case LDT:
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
                    // InternalGlobalConstantsParser.g:4991:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4997:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:5003:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:5009:3: kw= LDT
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
    // InternalGlobalConstantsParser.g:5018:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalGlobalConstantsParser.g:5018:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalGlobalConstantsParser.g:5019:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
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
    // InternalGlobalConstantsParser.g:5025:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5031:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalGlobalConstantsParser.g:5032:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalGlobalConstantsParser.g:5032:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt85=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt85=1;
                }
                break;
            case WSTRING:
                {
                alt85=2;
                }
                break;
            case CHAR:
                {
                alt85=3;
                }
                break;
            case WCHAR:
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
                    // InternalGlobalConstantsParser.g:5033:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5039:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:5045:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:5051:3: kw= WCHAR
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
    // InternalGlobalConstantsParser.g:5060:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalGlobalConstantsParser.g:5060:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalGlobalConstantsParser.g:5061:2: iv_ruleQualifiedName= ruleQualifiedName EOF
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
    // InternalGlobalConstantsParser.g:5067:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5073:2: ( (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* ) )
            // InternalGlobalConstantsParser.g:5074:2: (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* )
            {
            // InternalGlobalConstantsParser.g:5074:2: (this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )* )
            // InternalGlobalConstantsParser.g:5075:3: this_ID_0= RULE_ID (kw= ColonColon this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_66); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0());
              		
            }
            // InternalGlobalConstantsParser.g:5082:3: (kw= ColonColon this_ID_2= RULE_ID )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==ColonColon) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:5083:4: kw= ColonColon this_ID_2= RULE_ID
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
            	    break loop86;
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
    // InternalGlobalConstantsParser.g:5100:1: entryRuleQualifiedNameWithWildcard returns [String current=null] : iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF ;
    public final String entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildcard = null;


        try {
            // InternalGlobalConstantsParser.g:5100:65: (iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF )
            // InternalGlobalConstantsParser.g:5101:2: iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF
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
    // InternalGlobalConstantsParser.g:5107:1: ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildcard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5113:2: ( (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? ) )
            // InternalGlobalConstantsParser.g:5114:2: (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? )
            {
            // InternalGlobalConstantsParser.g:5114:2: (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? )
            // InternalGlobalConstantsParser.g:5115:3: this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )?
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
            // InternalGlobalConstantsParser.g:5125:3: (kw= ColonColonAsterisk )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==ColonColonAsterisk) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5126:4: kw= ColonColonAsterisk
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
    // InternalGlobalConstantsParser.g:5136:1: entryRuleNumeric returns [String current=null] : iv_ruleNumeric= ruleNumeric EOF ;
    public final String entryRuleNumeric() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumeric = null;


        try {
            // InternalGlobalConstantsParser.g:5136:47: (iv_ruleNumeric= ruleNumeric EOF )
            // InternalGlobalConstantsParser.g:5137:2: iv_ruleNumeric= ruleNumeric EOF
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
    // InternalGlobalConstantsParser.g:5143:1: ruleNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) ;
    public final AntlrDatatypeRuleToken ruleNumeric() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_NON_DECIMAL_3=null;
        AntlrDatatypeRuleToken this_Number_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5149:2: ( (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) )
            // InternalGlobalConstantsParser.g:5150:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            {
            // InternalGlobalConstantsParser.g:5150:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            int alt88=4;
            switch ( input.LA(1) ) {
            case TRUE:
                {
                alt88=1;
                }
                break;
            case FALSE:
                {
                alt88=2;
                }
                break;
            case RULE_INT:
                {
                alt88=3;
                }
                break;
            case RULE_NON_DECIMAL:
                {
                alt88=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }

            switch (alt88) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5151:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5157:3: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getFALSEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:5163:3: this_Number_2= ruleNumber
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
                    // InternalGlobalConstantsParser.g:5174:3: this_NON_DECIMAL_3= RULE_NON_DECIMAL
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
    // InternalGlobalConstantsParser.g:5185:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5187:2: (iv_ruleNumber= ruleNumber EOF )
            // InternalGlobalConstantsParser.g:5188:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalGlobalConstantsParser.g:5197:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_DECIMAL_3=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5204:2: ( (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? ) )
            // InternalGlobalConstantsParser.g:5205:2: (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? )
            {
            // InternalGlobalConstantsParser.g:5205:2: (this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )? )
            // InternalGlobalConstantsParser.g:5206:3: this_INT_0= RULE_INT (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalGlobalConstantsParser.g:5213:3: (kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==FullStop) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5214:4: kw= FullStop (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL )
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:5219:4: (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL )
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==RULE_INT) ) {
                        alt89=1;
                    }
                    else if ( (LA89_0==RULE_DECIMAL) ) {
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
                            // InternalGlobalConstantsParser.g:5220:5: this_INT_2= RULE_INT
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
                            // InternalGlobalConstantsParser.g:5228:5: this_DECIMAL_3= RULE_DECIMAL
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
    // InternalGlobalConstantsParser.g:5244:1: entryRuleSignedNumeric returns [String current=null] : iv_ruleSignedNumeric= ruleSignedNumeric EOF ;
    public final String entryRuleSignedNumeric() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSignedNumeric = null;


        try {
            // InternalGlobalConstantsParser.g:5244:53: (iv_ruleSignedNumeric= ruleSignedNumeric EOF )
            // InternalGlobalConstantsParser.g:5245:2: iv_ruleSignedNumeric= ruleSignedNumeric EOF
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
    // InternalGlobalConstantsParser.g:5251:1: ruleSignedNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_SignedNumber_0= ruleSignedNumber ;
    public final AntlrDatatypeRuleToken ruleSignedNumeric() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_SignedNumber_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5257:2: (this_SignedNumber_0= ruleSignedNumber )
            // InternalGlobalConstantsParser.g:5258:2: this_SignedNumber_0= ruleSignedNumber
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
    // InternalGlobalConstantsParser.g:5271:1: entryRuleSignedNumber returns [String current=null] : iv_ruleSignedNumber= ruleSignedNumber EOF ;
    public final String entryRuleSignedNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSignedNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5273:2: (iv_ruleSignedNumber= ruleSignedNumber EOF )
            // InternalGlobalConstantsParser.g:5274:2: iv_ruleSignedNumber= ruleSignedNumber EOF
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
    // InternalGlobalConstantsParser.g:5283:1: ruleSignedNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) ;
    public final AntlrDatatypeRuleToken ruleSignedNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_DECIMAL_5=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5290:2: ( ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) )
            // InternalGlobalConstantsParser.g:5291:2: ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? )
            {
            // InternalGlobalConstantsParser.g:5291:2: ( (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? )
            // InternalGlobalConstantsParser.g:5292:3: (kw= PlusSign | kw= HyphenMinus ) this_INT_2= RULE_INT (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )?
            {
            // InternalGlobalConstantsParser.g:5292:3: (kw= PlusSign | kw= HyphenMinus )
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
                    // InternalGlobalConstantsParser.g:5293:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5299:4: kw= HyphenMinus
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
            // InternalGlobalConstantsParser.g:5312:3: (kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==FullStop) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5313:4: kw= FullStop (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL )
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getFullStopKeyword_2_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:5318:4: (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL )
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==RULE_INT) ) {
                        alt92=1;
                    }
                    else if ( (LA92_0==RULE_DECIMAL) ) {
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
                            // InternalGlobalConstantsParser.g:5319:5: this_INT_4= RULE_INT
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
                            // InternalGlobalConstantsParser.g:5327:5: this_DECIMAL_5= RULE_DECIMAL
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
    // InternalGlobalConstantsParser.g:5343:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5345:2: (iv_ruleTime= ruleTime EOF )
            // InternalGlobalConstantsParser.g:5346:2: iv_ruleTime= ruleTime EOF
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
    // InternalGlobalConstantsParser.g:5355:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_TIME_VALUE_2=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:5362:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) )
            // InternalGlobalConstantsParser.g:5363:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            {
            // InternalGlobalConstantsParser.g:5363:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            // InternalGlobalConstantsParser.g:5364:3: (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE
            {
            // InternalGlobalConstantsParser.g:5364:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt94=3;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==PlusSign) ) {
                alt94=1;
            }
            else if ( (LA94_0==HyphenMinus) ) {
                alt94=2;
            }
            switch (alt94) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5365:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTimeAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:5371:4: kw= HyphenMinus
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
    // InternalGlobalConstantsParser.g:5391:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalGlobalConstantsParser.g:5391:44: (iv_ruleDate= ruleDate EOF )
            // InternalGlobalConstantsParser.g:5392:2: iv_ruleDate= ruleDate EOF
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
    // InternalGlobalConstantsParser.g:5398:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5404:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalGlobalConstantsParser.g:5405:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalGlobalConstantsParser.g:5405:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalGlobalConstantsParser.g:5406:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
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
    // InternalGlobalConstantsParser.g:5441:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalGlobalConstantsParser.g:5441:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalGlobalConstantsParser.g:5442:2: iv_ruleDateAndTime= ruleDateAndTime EOF
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
    // InternalGlobalConstantsParser.g:5448:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? ) ;
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
            // InternalGlobalConstantsParser.g:5454:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? ) )
            // InternalGlobalConstantsParser.g:5455:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? )
            {
            // InternalGlobalConstantsParser.g:5455:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )? )
            // InternalGlobalConstantsParser.g:5456:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT (kw= FullStop this_INT_12= RULE_INT )?
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
            // InternalGlobalConstantsParser.g:5523:3: (kw= FullStop this_INT_12= RULE_INT )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==FullStop) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5524:4: kw= FullStop this_INT_12= RULE_INT
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
    // InternalGlobalConstantsParser.g:5541:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalGlobalConstantsParser.g:5541:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalGlobalConstantsParser.g:5542:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalGlobalConstantsParser.g:5548:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5554:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? ) )
            // InternalGlobalConstantsParser.g:5555:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            {
            // InternalGlobalConstantsParser.g:5555:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )? )
            // InternalGlobalConstantsParser.g:5556:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT (kw= FullStop this_INT_6= RULE_INT )?
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
            // InternalGlobalConstantsParser.g:5587:3: (kw= FullStop this_INT_6= RULE_INT )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==FullStop) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5588:4: kw= FullStop this_INT_6= RULE_INT
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
    // InternalGlobalConstantsParser.g:5605:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5611:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalGlobalConstantsParser.g:5612:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalGlobalConstantsParser.g:5612:2: (enumLiteral_0= FullStopFullStop )
            // InternalGlobalConstantsParser.g:5613:3: enumLiteral_0= FullStopFullStop
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
    // InternalGlobalConstantsParser.g:5622:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5628:2: ( (enumLiteral_0= OR ) )
            // InternalGlobalConstantsParser.g:5629:2: (enumLiteral_0= OR )
            {
            // InternalGlobalConstantsParser.g:5629:2: (enumLiteral_0= OR )
            // InternalGlobalConstantsParser.g:5630:3: enumLiteral_0= OR
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
    // InternalGlobalConstantsParser.g:5639:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5645:2: ( (enumLiteral_0= XOR ) )
            // InternalGlobalConstantsParser.g:5646:2: (enumLiteral_0= XOR )
            {
            // InternalGlobalConstantsParser.g:5646:2: (enumLiteral_0= XOR )
            // InternalGlobalConstantsParser.g:5647:3: enumLiteral_0= XOR
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
    // InternalGlobalConstantsParser.g:5656:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5662:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalGlobalConstantsParser.g:5663:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalGlobalConstantsParser.g:5663:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==AND) ) {
                alt97=1;
            }
            else if ( (LA97_0==Ampersand) ) {
                alt97=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }
            switch (alt97) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5664:3: (enumLiteral_0= AND )
                    {
                    // InternalGlobalConstantsParser.g:5664:3: (enumLiteral_0= AND )
                    // InternalGlobalConstantsParser.g:5665:4: enumLiteral_0= AND
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
                    // InternalGlobalConstantsParser.g:5672:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalGlobalConstantsParser.g:5672:3: (enumLiteral_1= Ampersand )
                    // InternalGlobalConstantsParser.g:5673:4: enumLiteral_1= Ampersand
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
    // InternalGlobalConstantsParser.g:5683:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5689:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalGlobalConstantsParser.g:5690:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalGlobalConstantsParser.g:5690:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==EqualsSign) ) {
                alt98=1;
            }
            else if ( (LA98_0==LessThanSignGreaterThanSign) ) {
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
                    // InternalGlobalConstantsParser.g:5691:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5691:3: (enumLiteral_0= EqualsSign )
                    // InternalGlobalConstantsParser.g:5692:4: enumLiteral_0= EqualsSign
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
                    // InternalGlobalConstantsParser.g:5699:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5699:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalGlobalConstantsParser.g:5700:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalGlobalConstantsParser.g:5710:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5716:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalGlobalConstantsParser.g:5717:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalGlobalConstantsParser.g:5717:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt99=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt99=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt99=2;
                }
                break;
            case GreaterThanSign:
                {
                alt99=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt99=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }

            switch (alt99) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5718:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5718:3: (enumLiteral_0= LessThanSign )
                    // InternalGlobalConstantsParser.g:5719:4: enumLiteral_0= LessThanSign
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
                    // InternalGlobalConstantsParser.g:5726:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5726:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalGlobalConstantsParser.g:5727:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalGlobalConstantsParser.g:5734:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5734:3: (enumLiteral_2= GreaterThanSign )
                    // InternalGlobalConstantsParser.g:5735:4: enumLiteral_2= GreaterThanSign
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
                    // InternalGlobalConstantsParser.g:5742:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5742:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalGlobalConstantsParser.g:5743:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalGlobalConstantsParser.g:5753:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5759:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalGlobalConstantsParser.g:5760:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalGlobalConstantsParser.g:5760:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==PlusSign) ) {
                alt100=1;
            }
            else if ( (LA100_0==HyphenMinus) ) {
                alt100=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }
            switch (alt100) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5761:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalGlobalConstantsParser.g:5761:3: (enumLiteral_0= PlusSign )
                    // InternalGlobalConstantsParser.g:5762:4: enumLiteral_0= PlusSign
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
                    // InternalGlobalConstantsParser.g:5769:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalGlobalConstantsParser.g:5769:3: (enumLiteral_1= HyphenMinus )
                    // InternalGlobalConstantsParser.g:5770:4: enumLiteral_1= HyphenMinus
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
    // InternalGlobalConstantsParser.g:5780:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5786:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalGlobalConstantsParser.g:5787:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalGlobalConstantsParser.g:5787:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt101=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt101=1;
                }
                break;
            case Solidus:
                {
                alt101=2;
                }
                break;
            case MOD:
                {
                alt101=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }

            switch (alt101) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5788:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalGlobalConstantsParser.g:5788:3: (enumLiteral_0= Asterisk )
                    // InternalGlobalConstantsParser.g:5789:4: enumLiteral_0= Asterisk
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
                    // InternalGlobalConstantsParser.g:5796:3: (enumLiteral_1= Solidus )
                    {
                    // InternalGlobalConstantsParser.g:5796:3: (enumLiteral_1= Solidus )
                    // InternalGlobalConstantsParser.g:5797:4: enumLiteral_1= Solidus
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
                    // InternalGlobalConstantsParser.g:5804:3: (enumLiteral_2= MOD )
                    {
                    // InternalGlobalConstantsParser.g:5804:3: (enumLiteral_2= MOD )
                    // InternalGlobalConstantsParser.g:5805:4: enumLiteral_2= MOD
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
    // InternalGlobalConstantsParser.g:5815:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5821:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalGlobalConstantsParser.g:5822:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalGlobalConstantsParser.g:5822:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalGlobalConstantsParser.g:5823:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalGlobalConstantsParser.g:5832:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5838:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalGlobalConstantsParser.g:5839:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalGlobalConstantsParser.g:5839:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt102=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt102=1;
                }
                break;
            case PlusSign:
                {
                alt102=2;
                }
                break;
            case NOT:
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
                    // InternalGlobalConstantsParser.g:5840:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalGlobalConstantsParser.g:5840:3: (enumLiteral_0= HyphenMinus )
                    // InternalGlobalConstantsParser.g:5841:4: enumLiteral_0= HyphenMinus
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
                    // InternalGlobalConstantsParser.g:5848:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalGlobalConstantsParser.g:5848:3: (enumLiteral_1= PlusSign )
                    // InternalGlobalConstantsParser.g:5849:4: enumLiteral_1= PlusSign
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
                    // InternalGlobalConstantsParser.g:5856:3: (enumLiteral_2= NOT )
                    {
                    // InternalGlobalConstantsParser.g:5856:3: (enumLiteral_2= NOT )
                    // InternalGlobalConstantsParser.g:5857:4: enumLiteral_2= NOT
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
    // InternalGlobalConstantsParser.g:5867:1: ruleSTBuiltinFeature returns [Enumerator current=null] : (enumLiteral_0= THIS ) ;
    public final Enumerator ruleSTBuiltinFeature() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5873:2: ( (enumLiteral_0= THIS ) )
            // InternalGlobalConstantsParser.g:5874:2: (enumLiteral_0= THIS )
            {
            // InternalGlobalConstantsParser.g:5874:2: (enumLiteral_0= THIS )
            // InternalGlobalConstantsParser.g:5875:3: enumLiteral_0= THIS
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
    // InternalGlobalConstantsParser.g:5884:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5890:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalGlobalConstantsParser.g:5891:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalGlobalConstantsParser.g:5891:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt103=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt103=1;
                }
                break;
            case D_1:
                {
                alt103=2;
                }
                break;
            case W:
                {
                alt103=3;
                }
                break;
            case B:
                {
                alt103=4;
                }
                break;
            case X:
                {
                alt103=5;
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
                    // InternalGlobalConstantsParser.g:5892:3: (enumLiteral_0= L )
                    {
                    // InternalGlobalConstantsParser.g:5892:3: (enumLiteral_0= L )
                    // InternalGlobalConstantsParser.g:5893:4: enumLiteral_0= L
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
                    // InternalGlobalConstantsParser.g:5900:3: (enumLiteral_1= D_1 )
                    {
                    // InternalGlobalConstantsParser.g:5900:3: (enumLiteral_1= D_1 )
                    // InternalGlobalConstantsParser.g:5901:4: enumLiteral_1= D_1
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
                    // InternalGlobalConstantsParser.g:5908:3: (enumLiteral_2= W )
                    {
                    // InternalGlobalConstantsParser.g:5908:3: (enumLiteral_2= W )
                    // InternalGlobalConstantsParser.g:5909:4: enumLiteral_2= W
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
                    // InternalGlobalConstantsParser.g:5916:3: (enumLiteral_3= B )
                    {
                    // InternalGlobalConstantsParser.g:5916:3: (enumLiteral_3= B )
                    // InternalGlobalConstantsParser.g:5917:4: enumLiteral_3= B
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
                    // InternalGlobalConstantsParser.g:5924:3: (enumLiteral_4= X )
                    {
                    // InternalGlobalConstantsParser.g:5924:3: (enumLiteral_4= X )
                    // InternalGlobalConstantsParser.g:5925:4: enumLiteral_4= X
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
        // InternalGlobalConstantsParser.g:2189:4: ( ( ruleSTStatement ) )
        // InternalGlobalConstantsParser.g:2189:5: ( ruleSTStatement )
        {
        // InternalGlobalConstantsParser.g:2189:5: ( ruleSTStatement )
        // InternalGlobalConstantsParser.g:2190:5: ruleSTStatement
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
        // InternalGlobalConstantsParser.g:3285:4: ( ruleSTSignedLiteralExpressions )
        // InternalGlobalConstantsParser.g:3285:5: ruleSTSignedLiteralExpressions
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


    protected DFA21 dfa21 = new DFA21(this);
    protected DFA28 dfa28 = new DFA28(this);
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA55 dfa55 = new DFA55(this);
    protected DFA72 dfa72 = new DFA72(this);
    static final String dfa_1s = "\23\uffff";
    static final String dfa_2s = "\2\uffff\1\3\16\uffff\1\3\1\uffff";
    static final String dfa_3s = "\2\10\1\165\2\uffff\11\165\1\u00b9\1\uffff\1\u00b9\2\165";
    static final String dfa_4s = "\2\u00ba\1\u00ab\2\uffff\11\u00a8\1\u00b9\1\uffff\1\u00b9\1\u00ab\1\u00a8";
    static final String dfa_5s = "\3\uffff\1\1\1\2\12\uffff\1\3\3\uffff";
    static final String dfa_6s = "\23\uffff}>";
    static final String[] dfa_7s = {
            "\1\3\1\uffff\1\3\5\uffff\1\3\2\uffff\1\3\51\uffff\1\3\12\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff\4\3\1\uffff\2\3\2\uffff\2\3\1\uffff\2\3\1\uffff\3\3\3\uffff\2\3\1\uffff\2\3\3\uffff\3\3\1\uffff\1\3\1\uffff\1\3\1\uffff\1\3\1\uffff\4\3\1\uffff\1\3\1\uffff\1\3\20\uffff\1\3\1\uffff\2\3\2\uffff\1\3\3\uffff\1\1\2\uffff\1\3\1\uffff\1\3\7\uffff\2\3\1\4\4\uffff\2\3\12\uffff\1\2\1\3",
            "\1\3\1\uffff\1\3\5\uffff\1\3\2\uffff\1\3\51\uffff\1\3\12\uffff\1\3\3\uffff\1\3\1\uffff\1\3\1\uffff\4\3\1\uffff\2\3\2\uffff\2\3\1\uffff\2\3\1\uffff\3\3\3\uffff\2\3\1\uffff\2\3\3\uffff\3\3\1\uffff\1\3\1\uffff\1\3\1\uffff\1\7\1\uffff\2\3\1\12\1\3\1\uffff\1\3\1\uffff\1\11\20\uffff\1\14\1\uffff\1\15\1\6\2\uffff\1\10\3\uffff\1\3\2\uffff\1\3\1\uffff\1\3\7\uffff\1\13\1\3\5\uffff\2\3\12\uffff\1\5\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\16\1\uffff\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\1\17\11\3\1\uffff\4\3\2\uffff\4\3",
            "",
            "",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\20\1\17\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\17\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\17\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\17\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\17\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\17\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\17\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\17\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\uffff\1\17\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\6\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3",
            "\1\21",
            "",
            "\1\22",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\16\1\uffff\2\3\1\uffff\1\3\11\uffff\1\3\1\uffff\1\17\11\3\1\uffff\4\3\2\uffff\4\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\5\uffff\2\3\1\20\1\17\2\3\1\uffff\1\3\11\uffff\1\3\2\uffff\5\3\1\uffff\3\3\2\uffff\3\3\2\uffff\1\3"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "852:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )";
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

    class DFA28 extends DFA {

        public DFA28(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 28;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "1481:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )";
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

    class DFA40 extends DFA {

        public DFA40(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 40;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "()* loopback of 2188:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA40_0 = input.LA(1);

                         
                        int index40_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA40_0==EOF||LA40_0==END_CASE||LA40_0==ELSE) ) {s = 1;}

                        else if ( (LA40_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA40_0==RULE_ID) ) {s = 4;}

                        else if ( (LA40_0==LT) ) {s = 5;}

                        else if ( (LA40_0==AND) ) {s = 6;}

                        else if ( (LA40_0==OR) ) {s = 7;}

                        else if ( (LA40_0==XOR) ) {s = 8;}

                        else if ( (LA40_0==MOD) ) {s = 9;}

                        else if ( (LA40_0==D) ) {s = 10;}

                        else if ( (LA40_0==DT) ) {s = 11;}

                        else if ( (LA40_0==LD) ) {s = 12;}

                        else if ( (LA40_0==THIS) ) {s = 13;}

                        else if ( (LA40_0==BOOL) ) {s = 14;}

                        else if ( (LA40_0==BYTE) ) {s = 15;}

                        else if ( (LA40_0==WORD) ) {s = 16;}

                        else if ( (LA40_0==DWORD) ) {s = 17;}

                        else if ( (LA40_0==LWORD) ) {s = 18;}

                        else if ( (LA40_0==SINT) ) {s = 19;}

                        else if ( (LA40_0==INT) ) {s = 20;}

                        else if ( (LA40_0==DINT) ) {s = 21;}

                        else if ( (LA40_0==LINT) ) {s = 22;}

                        else if ( (LA40_0==USINT) ) {s = 23;}

                        else if ( (LA40_0==UINT) ) {s = 24;}

                        else if ( (LA40_0==UDINT) ) {s = 25;}

                        else if ( (LA40_0==ULINT) ) {s = 26;}

                        else if ( (LA40_0==REAL) ) {s = 27;}

                        else if ( (LA40_0==LREAL) ) {s = 28;}

                        else if ( (LA40_0==TRUE) ) {s = 29;}

                        else if ( (LA40_0==FALSE) ) {s = 30;}

                        else if ( (LA40_0==RULE_INT) ) {s = 31;}

                        else if ( (LA40_0==RULE_NON_DECIMAL) ) {s = 32;}

                        else if ( (LA40_0==DATE) ) {s = 33;}

                        else if ( (LA40_0==LDATE) ) {s = 34;}

                        else if ( (LA40_0==TIME) ) {s = 35;}

                        else if ( (LA40_0==LTIME) ) {s = 36;}

                        else if ( (LA40_0==T) ) {s = 37;}

                        else if ( (LA40_0==TIME_OF_DAY) ) {s = 38;}

                        else if ( (LA40_0==LTIME_OF_DAY) ) {s = 39;}

                        else if ( (LA40_0==TOD) ) {s = 40;}

                        else if ( (LA40_0==LTOD) ) {s = 41;}

                        else if ( (LA40_0==DATE_AND_TIME) ) {s = 42;}

                        else if ( (LA40_0==LDATE_AND_TIME) ) {s = 43;}

                        else if ( (LA40_0==LDT) ) {s = 44;}

                        else if ( (LA40_0==STRING) ) {s = 45;}

                        else if ( (LA40_0==WSTRING) ) {s = 46;}

                        else if ( (LA40_0==CHAR) ) {s = 47;}

                        else if ( (LA40_0==WCHAR) ) {s = 48;}

                        else if ( (LA40_0==RULE_STRING) ) {s = 49;}

                        else if ( (LA40_0==PlusSign) ) {s = 50;}

                        else if ( (LA40_0==HyphenMinus) ) {s = 51;}

                        else if ( (LA40_0==NOT) ) {s = 52;}

                        else if ( (LA40_0==IF) && (synpred1_InternalGlobalConstantsParser())) {s = 54;}

                        else if ( (LA40_0==CASE) && (synpred1_InternalGlobalConstantsParser())) {s = 55;}

                        else if ( (LA40_0==FOR) && (synpred1_InternalGlobalConstantsParser())) {s = 56;}

                        else if ( (LA40_0==WHILE) && (synpred1_InternalGlobalConstantsParser())) {s = 57;}

                        else if ( (LA40_0==REPEAT) && (synpred1_InternalGlobalConstantsParser())) {s = 58;}

                        else if ( (LA40_0==RETURN) && (synpred1_InternalGlobalConstantsParser())) {s = 59;}

                        else if ( (LA40_0==CONTINUE) && (synpred1_InternalGlobalConstantsParser())) {s = 60;}

                        else if ( (LA40_0==EXIT) && (synpred1_InternalGlobalConstantsParser())) {s = 61;}

                        else if ( (LA40_0==Semicolon) && (synpred1_InternalGlobalConstantsParser())) {s = 62;}

                         
                        input.seek(index40_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA40_3 = input.LA(1);

                         
                        int index40_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA40_4 = input.LA(1);

                         
                        int index40_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA40_5 = input.LA(1);

                         
                        int index40_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA40_6 = input.LA(1);

                         
                        int index40_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA40_7 = input.LA(1);

                         
                        int index40_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA40_8 = input.LA(1);

                         
                        int index40_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA40_9 = input.LA(1);

                         
                        int index40_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA40_10 = input.LA(1);

                         
                        int index40_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA40_11 = input.LA(1);

                         
                        int index40_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA40_12 = input.LA(1);

                         
                        int index40_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA40_13 = input.LA(1);

                         
                        int index40_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA40_14 = input.LA(1);

                         
                        int index40_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA40_15 = input.LA(1);

                         
                        int index40_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA40_16 = input.LA(1);

                         
                        int index40_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA40_17 = input.LA(1);

                         
                        int index40_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA40_18 = input.LA(1);

                         
                        int index40_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA40_19 = input.LA(1);

                         
                        int index40_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA40_20 = input.LA(1);

                         
                        int index40_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA40_21 = input.LA(1);

                         
                        int index40_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA40_22 = input.LA(1);

                         
                        int index40_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA40_23 = input.LA(1);

                         
                        int index40_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA40_24 = input.LA(1);

                         
                        int index40_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA40_25 = input.LA(1);

                         
                        int index40_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA40_26 = input.LA(1);

                         
                        int index40_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA40_27 = input.LA(1);

                         
                        int index40_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA40_28 = input.LA(1);

                         
                        int index40_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA40_29 = input.LA(1);

                         
                        int index40_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA40_30 = input.LA(1);

                         
                        int index40_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA40_31 = input.LA(1);

                         
                        int index40_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA40_32 = input.LA(1);

                         
                        int index40_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA40_33 = input.LA(1);

                         
                        int index40_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA40_34 = input.LA(1);

                         
                        int index40_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA40_35 = input.LA(1);

                         
                        int index40_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA40_36 = input.LA(1);

                         
                        int index40_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA40_37 = input.LA(1);

                         
                        int index40_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA40_38 = input.LA(1);

                         
                        int index40_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA40_39 = input.LA(1);

                         
                        int index40_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA40_40 = input.LA(1);

                         
                        int index40_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA40_41 = input.LA(1);

                         
                        int index40_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA40_42 = input.LA(1);

                         
                        int index40_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA40_43 = input.LA(1);

                         
                        int index40_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA40_44 = input.LA(1);

                         
                        int index40_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA40_45 = input.LA(1);

                         
                        int index40_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA40_46 = input.LA(1);

                         
                        int index40_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_46);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA40_47 = input.LA(1);

                         
                        int index40_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_47);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA40_48 = input.LA(1);

                         
                        int index40_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_48);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA40_49 = input.LA(1);

                         
                        int index40_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_49);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA40_50 = input.LA(1);

                         
                        int index40_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_50);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA40_51 = input.LA(1);

                         
                        int index40_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_51);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA40_52 = input.LA(1);

                         
                        int index40_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 62;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index40_52);
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
    static final String dfa_21s = "\14\uffff";
    static final String dfa_22s = "\2\uffff\4\1\6\uffff";
    static final String dfa_23s = "\1\10\1\uffff\4\26\1\uffff\2\10\1\uffff\1\0\1\uffff";
    static final String dfa_24s = "\1\u00ba\1\uffff\4\u00ab\1\uffff\2\u00ba\1\uffff\1\0\1\uffff";
    static final String dfa_25s = "\1\uffff\1\1\4\uffff\1\2\2\uffff\1\4\1\uffff\1\3";
    static final String dfa_26s = "\12\uffff\1\0\1\uffff}>";
    static final String[] dfa_27s = {
            "\1\6\1\uffff\1\6\5\uffff\1\6\2\uffff\1\6\51\uffff\1\6\12\uffff\1\6\3\uffff\1\6\1\uffff\1\6\1\uffff\4\6\1\uffff\2\6\2\uffff\2\6\1\uffff\2\6\1\uffff\3\6\3\uffff\2\6\1\uffff\2\6\3\uffff\1\1\2\6\1\uffff\1\6\1\uffff\1\6\1\uffff\1\1\1\uffff\2\6\1\1\1\11\1\uffff\1\6\1\uffff\1\1\20\uffff\1\4\1\uffff\1\5\1\2\2\uffff\1\1\3\uffff\1\1\2\uffff\1\7\1\uffff\1\10\7\uffff\1\3\1\6\5\uffff\2\6\12\uffff\1\1\1\6",
            "",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\uffff\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\6\16\1\2\uffff\4\1",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\uffff\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\6\16\1\2\uffff\4\1",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\uffff\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\6\16\1\2\uffff\4\1",
            "\1\1\125\uffff\1\1\10\uffff\1\1\3\uffff\1\1\4\uffff\1\1\5\uffff\2\1\1\uffff\3\1\1\uffff\1\1\1\uffff\2\1\4\uffff\1\1\1\uffff\2\1\1\6\16\1\2\uffff\4\1",
            "",
            "\1\11\1\uffff\1\11\5\uffff\1\11\2\uffff\1\11\51\uffff\1\11\12\uffff\1\11\3\uffff\1\11\1\uffff\1\11\1\uffff\4\11\1\uffff\2\11\2\uffff\2\11\1\uffff\2\11\1\uffff\3\11\3\uffff\2\11\1\uffff\2\11\3\uffff\3\11\1\uffff\1\11\1\uffff\1\11\1\uffff\1\11\1\uffff\4\11\1\uffff\1\11\1\uffff\1\11\20\uffff\1\11\1\uffff\2\11\2\uffff\1\11\3\uffff\1\11\2\uffff\1\11\1\uffff\1\11\7\uffff\2\11\5\uffff\1\11\1\12\12\uffff\2\11",
            "\1\11\1\uffff\1\11\5\uffff\1\11\2\uffff\1\11\51\uffff\1\11\12\uffff\1\11\3\uffff\1\11\1\uffff\1\11\1\uffff\4\11\1\uffff\2\11\2\uffff\2\11\1\uffff\2\11\1\uffff\3\11\3\uffff\2\11\1\uffff\2\11\3\uffff\3\11\1\uffff\1\11\1\uffff\1\11\1\uffff\1\11\1\uffff\4\11\1\uffff\1\11\1\uffff\1\11\20\uffff\1\11\1\uffff\2\11\2\uffff\1\11\3\uffff\1\11\2\uffff\1\11\1\uffff\1\11\7\uffff\2\11\5\uffff\1\11\1\12\12\uffff\2\11",
            "",
            "\1\uffff",
            ""
    };

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA55 extends DFA {

        public DFA55(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 55;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "3265:2: (this_STAccessExpression_0= ruleSTAccessExpression | this_STLiteralExpressions_1= ruleSTLiteralExpressions | ( ( ruleSTSignedLiteralExpressions )=>this_STSignedLiteralExpressions_2= ruleSTSignedLiteralExpressions ) | ( () ( (lv_op_4_0= ruleUnaryOperator ) ) ( (lv_expression_5_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA55_10 = input.LA(1);

                         
                        int index55_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 11;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index55_10);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 55, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_28s = "\1\114\17\u0097\1\uffff\1\116\1\uffff";
    static final String dfa_29s = "\1\u00ae\17\u0097\1\uffff\1\u00ae\1\uffff";
    static final String dfa_30s = "\20\uffff\1\2\1\uffff\1\1";
    static final String[] dfa_31s = {
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
    static final char[] dfa_28 = DFA.unpackEncodedStringToUnsignedChars(dfa_28s);
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final short[][] dfa_31 = unpackEncodedStringArray(dfa_31s);

    class DFA72 extends DFA {

        public DFA72(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 72;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_6;
            this.transition = dfa_31;
        }
        public String getDescription() {
            return "4085:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleSignedNumeric ) ) ) | ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_4= NumberSign )? ( (lv_value_5_0= ruleNumeric ) ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000040000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0040020000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0040000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200001000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x2000000000090500L,0x118A4363B66F1500L,0x0200000000008000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5500L,0x060060C052268000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020020000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000050400000080L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040400000080L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5500L,0x060061C052268000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040400000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x4220000000000000L,0x0200004000268000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000080020000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0xA000040000090500L,0x57EAE36FFE6F7550L,0x060060C452278000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x8000000000000000L,0x0000000400002000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x2000040000090502L,0x57EAE36BFE6F5550L,0x060060C452278000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x2000080000090500L,0x57AAE367B66F5500L,0x060060C052268000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000220000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x2020040000090500L,0x57EAE36BFE6F5550L,0x060060C452278000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x2000040400090500L,0x57EAE36BFE6F5550L,0x060060C452278000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x2000040000090500L,0x57EAE36BFEEF5550L,0x060060C452278000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0020000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000200L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000002800000900L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000050000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0200000000000000L,0x0000000108000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000010080000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0xC220000000000000L,0x020040400226800FL});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5500L,0x060060C056268000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400002000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000050000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x008A8322326A5000L,0x0000600000000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000050000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0010000000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000C00000000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});

}