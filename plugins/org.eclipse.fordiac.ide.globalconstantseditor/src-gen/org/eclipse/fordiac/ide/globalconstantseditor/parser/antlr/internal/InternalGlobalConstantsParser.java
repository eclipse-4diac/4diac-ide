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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "END_FUNCTION_BLOCK", "END_CONFIGURATION", "END_TRANSITION", "FUNCTION_BLOCK", "LDATE_AND_TIME", "CONFIGURATION", "DATE_AND_TIME", "END_INTERFACE", "END_NAMESPACE", "END_FUNCTION", "END_RESOURCE", "INITIAL_STEP", "LTIME_OF_DAY", "VAR_EXTERNAL", "END_PROGRAM", "TIME_OF_DAY", "END_ACTION", "END_METHOD", "END_REPEAT", "END_STRUCT", "IMPLEMENTS", "NON_RETAIN", "READ_WRITE", "TRANSITION", "VAR_ACCESS", "VAR_CONFIG", "VAR_GLOBAL", "VAR_IN_OUT", "VAR_OUTPUT", "END_CLASS", "END_WHILE", "INTERFACE", "NAMESPACE", "PROTECTED", "READ_ONLY", "VAR_INPUT", "ABSTRACT", "CONSTANT", "CONTINUE", "END_CASE", "END_STEP", "END_TYPE", "FUNCTION", "INTERNAL", "INTERVAL", "OVERRIDE", "PRIORITY", "RESOURCE", "VAR_TEMP", "END_FOR", "END_VAR", "EXTENDS", "INTERAL", "OVERLAP", "PACKAGE", "PRIVATE", "PROGRAM", "WSTRING", "ACTION", "END_IF", "IMPORT", "METHOD", "PUBLIC", "REF_TO", "REPEAT", "RETAIN", "RETURN", "SINGLE", "STRING", "STRUCT", "ARRAY", "CLASS", "DWORD", "ELSIF", "FALSE", "FINAL", "LDATE", "LREAL", "LTIME", "LWORD", "SUPER", "UDINT", "ULINT", "UNTIL", "USING", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "FROM", "LINT", "LTOD", "NULL", "REAL", "SINT", "STEP", "TASK", "THEN", "THIS", "TIME", "TRUE", "TYPE", "UINT", "WITH", "WORD", "ColonColonAsterisk", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "REF", "TOD", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonColon", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "OF", "ON", "OR", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "T", "LeftSquareBracket", "RightSquareBracket", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_DECIMAL", "RULE_TIME_PART", "RULE_TIME_VALUE", "RULE_TIME_DAYS", "RULE_TIME_HOURS", "RULE_TIME_MINUTES", "RULE_TIME_SECONDS", "RULE_TIME_MILLIS", "RULE_TIME_MICROS", "RULE_TIME_NANOS", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int EqualsSignGreaterThanSign=138;
    public static final int LessThanSign=163;
    public static final int RULE_TIME_HOURS=177;
    public static final int INTERNAL=47;
    public static final int REF_TO=67;
    public static final int LINT=101;
    public static final int GreaterThanSign=165;
    public static final int RULE_ID=183;
    public static final int CONFIGURATION=9;
    public static final int UDINT=85;
    public static final int GreaterThanSignEqualsSign=139;
    public static final int ColonColon=134;
    public static final int AT=140;
    public static final int RULE_INT=172;
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
    public static final int PROTECTED=37;
    public static final int RESOURCE=51;
    public static final int INTERVAL=48;
    public static final int FullStop=159;
    public static final int RULE_TIME_SECONDS=179;
    public static final int INTERAL=56;
    public static final int RULE_TIME_VALUE=175;
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
    public static final int RULE_TIME_PART=174;
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
    public static final int RULE_DECIMAL=173;
    public static final int NOT=122;
    public static final int AsteriskAsterisk=132;
    public static final int THIS=109;
    public static final int SINT=105;
    public static final int VAR_GLOBAL=30;
    public static final int WCHAR=90;
    public static final int VAR_EXTERNAL=17;
    public static final int RULE_SL_COMMENT=186;
    public static final int RETURN=70;
    public static final int ColonColonAsterisk=116;
    public static final int END_RESOURCE=14;
    public static final int Colon=161;
    public static final int EOF=-1;
    public static final int Asterisk=155;
    public static final int RULE_TIME_MILLIS=180;
    public static final int MOD=121;
    public static final int RETAIN=69;
    public static final int STEP=106;
    public static final int TIME=110;
    public static final int WITH=114;
    public static final int RULE_TIME_MINUTES=178;
    public static final int END_CLASS=33;
    public static final int ACTION=62;
    public static final int BOOL=92;
    public static final int D_1=128;
    public static final int FALSE=78;
    public static final int RULE_TIME_MICROS=181;
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
    public static final int RULE_TIME_NANOS=182;
    public static final int CASE=94;
    public static final int PlusSign=156;
    public static final int RULE_TIME_DAYS=176;
    public static final int RULE_ML_COMMENT=185;
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
    public static final int RULE_HEX_DIGIT=170;
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
    public static final int RULE_STRING=184;
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
    public static final int RULE_WS=187;
    public static final int VAR_IN_OUT=31;
    public static final int END_INTERFACE=11;
    public static final int IMPLEMENTS=24;
    public static final int RULE_ANY_OTHER=188;
    public static final int UNTIL=87;
    public static final int OVERRIDE=49;
    public static final int WHILE=91;
    public static final int END_PROGRAM=18;
    public static final int RULE_NON_DECIMAL=171;

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

            if ( (LA6_0==LDATE_AND_TIME||LA6_0==DATE_AND_TIME||LA6_0==LTIME_OF_DAY||LA6_0==TIME_OF_DAY||LA6_0==WSTRING||LA6_0==STRING||LA6_0==DWORD||LA6_0==FALSE||(LA6_0>=LDATE && LA6_0<=LWORD)||(LA6_0>=UDINT && LA6_0<=ULINT)||(LA6_0>=USINT && LA6_0<=WCHAR)||(LA6_0>=BOOL && LA6_0<=BYTE)||(LA6_0>=CHAR && LA6_0<=DINT)||(LA6_0>=LINT && LA6_0<=LTOD)||(LA6_0>=REAL && LA6_0<=SINT)||(LA6_0>=THIS && LA6_0<=TRUE)||LA6_0==UINT||LA6_0==WORD||LA6_0==AND||(LA6_0>=INT && LA6_0<=NOT)||LA6_0==TOD||LA6_0==XOR||LA6_0==DT||(LA6_0>=LD && LA6_0<=LT)||LA6_0==OR||LA6_0==LeftParenthesis||LA6_0==PlusSign||LA6_0==HyphenMinus||(LA6_0>=D && LA6_0<=T)||(LA6_0>=RULE_NON_DECIMAL && LA6_0<=RULE_DECIMAL)||(LA6_0>=RULE_ID && LA6_0<=RULE_STRING)) ) {
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

            if ( (LA7_0==LDATE_AND_TIME||LA7_0==DATE_AND_TIME||LA7_0==LTIME_OF_DAY||LA7_0==TIME_OF_DAY||LA7_0==WSTRING||LA7_0==STRING||LA7_0==DWORD||LA7_0==FALSE||(LA7_0>=LDATE && LA7_0<=LWORD)||(LA7_0>=UDINT && LA7_0<=ULINT)||(LA7_0>=USINT && LA7_0<=WCHAR)||(LA7_0>=BOOL && LA7_0<=BYTE)||(LA7_0>=CHAR && LA7_0<=DINT)||(LA7_0>=LINT && LA7_0<=LTOD)||(LA7_0>=REAL && LA7_0<=SINT)||(LA7_0>=THIS && LA7_0<=TRUE)||LA7_0==UINT||LA7_0==WORD||LA7_0==AND||(LA7_0>=INT && LA7_0<=NOT)||LA7_0==TOD||LA7_0==XOR||LA7_0==DT||(LA7_0>=LD && LA7_0<=LT)||LA7_0==OR||LA7_0==LeftParenthesis||LA7_0==PlusSign||LA7_0==HyphenMinus||(LA7_0>=D && LA7_0<=LeftSquareBracket)||(LA7_0>=RULE_NON_DECIMAL && LA7_0<=RULE_DECIMAL)||(LA7_0>=RULE_ID && LA7_0<=RULE_STRING)) ) {
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
    // InternalGlobalConstantsParser.g:372:1: ruleSTVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) ;
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
            // InternalGlobalConstantsParser.g:378:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) )
            // InternalGlobalConstantsParser.g:379:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            {
            // InternalGlobalConstantsParser.g:379:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            // InternalGlobalConstantsParser.g:380:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon
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

                        if ( (LA11_1==LDATE_AND_TIME||LA11_1==DATE_AND_TIME||LA11_1==LTIME_OF_DAY||LA11_1==TIME_OF_DAY||LA11_1==WSTRING||LA11_1==STRING||LA11_1==DWORD||LA11_1==FALSE||(LA11_1>=LDATE && LA11_1<=LWORD)||(LA11_1>=UDINT && LA11_1<=ULINT)||(LA11_1>=USINT && LA11_1<=WCHAR)||(LA11_1>=BOOL && LA11_1<=BYTE)||(LA11_1>=CHAR && LA11_1<=DINT)||(LA11_1>=LINT && LA11_1<=LTOD)||(LA11_1>=REAL && LA11_1<=SINT)||(LA11_1>=THIS && LA11_1<=TRUE)||LA11_1==UINT||LA11_1==WORD||LA11_1==AND||(LA11_1>=INT && LA11_1<=NOT)||LA11_1==TOD||LA11_1==XOR||LA11_1==DT||(LA11_1>=LD && LA11_1<=LT)||LA11_1==OR||LA11_1==LeftParenthesis||LA11_1==PlusSign||LA11_1==HyphenMinus||(LA11_1>=D && LA11_1<=T)||(LA11_1>=RULE_NON_DECIMAL && LA11_1<=RULE_DECIMAL)||(LA11_1>=RULE_ID && LA11_1<=RULE_STRING)) ) {
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
                    pushFollow(FOLLOW_4);
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


    // $ANTLR start "entryRuleSTTypeDeclaration"
    // InternalGlobalConstantsParser.g:627:1: entryRuleSTTypeDeclaration returns [EObject current=null] : iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF ;
    public final EObject entryRuleSTTypeDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTypeDeclaration = null;


        try {
            // InternalGlobalConstantsParser.g:627:58: (iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF )
            // InternalGlobalConstantsParser.g:628:2: iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF
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
    // InternalGlobalConstantsParser.g:634:1: ruleSTTypeDeclaration returns [EObject current=null] : ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) ;
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
            // InternalGlobalConstantsParser.g:640:2: ( ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) )
            // InternalGlobalConstantsParser.g:641:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            {
            // InternalGlobalConstantsParser.g:641:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            // InternalGlobalConstantsParser.g:642:3: () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            {
            // InternalGlobalConstantsParser.g:642:3: ()
            // InternalGlobalConstantsParser.g:643:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTTypeDeclarationAccess().getSTTypeDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:649:3: ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==ARRAY) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalGlobalConstantsParser.g:650:4: ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF
                    {
                    // InternalGlobalConstantsParser.g:650:4: ( (lv_array_1_0= ARRAY ) )
                    // InternalGlobalConstantsParser.g:651:5: (lv_array_1_0= ARRAY )
                    {
                    // InternalGlobalConstantsParser.g:651:5: (lv_array_1_0= ARRAY )
                    // InternalGlobalConstantsParser.g:652:6: lv_array_1_0= ARRAY
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

                    // InternalGlobalConstantsParser.g:664:4: ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) )
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==LeftSquareBracket) ) {
                        int LA17_1 = input.LA(2);

                        if ( (LA17_1==Asterisk) ) {
                            alt17=2;
                        }
                        else if ( (LA17_1==LDATE_AND_TIME||LA17_1==DATE_AND_TIME||LA17_1==LTIME_OF_DAY||LA17_1==TIME_OF_DAY||LA17_1==WSTRING||LA17_1==STRING||LA17_1==DWORD||LA17_1==FALSE||(LA17_1>=LDATE && LA17_1<=LWORD)||(LA17_1>=UDINT && LA17_1<=ULINT)||(LA17_1>=USINT && LA17_1<=WCHAR)||(LA17_1>=BOOL && LA17_1<=BYTE)||(LA17_1>=CHAR && LA17_1<=DINT)||(LA17_1>=LINT && LA17_1<=LTOD)||(LA17_1>=REAL && LA17_1<=SINT)||(LA17_1>=THIS && LA17_1<=TRUE)||LA17_1==UINT||LA17_1==WORD||LA17_1==AND||(LA17_1>=INT && LA17_1<=NOT)||LA17_1==TOD||LA17_1==XOR||LA17_1==DT||(LA17_1>=LD && LA17_1<=LT)||LA17_1==OR||LA17_1==LeftParenthesis||LA17_1==PlusSign||LA17_1==HyphenMinus||(LA17_1>=D && LA17_1<=T)||(LA17_1>=RULE_NON_DECIMAL && LA17_1<=RULE_DECIMAL)||(LA17_1>=RULE_ID && LA17_1<=RULE_STRING)) ) {
                            alt17=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 17, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 17, 0, input);

                        throw nvae;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:665:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:665:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:666:6: otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket
                            {
                            otherlv_2=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_0_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:670:6: ( (lv_ranges_3_0= ruleSTExpression ) )
                            // InternalGlobalConstantsParser.g:671:7: (lv_ranges_3_0= ruleSTExpression )
                            {
                            // InternalGlobalConstantsParser.g:671:7: (lv_ranges_3_0= ruleSTExpression )
                            // InternalGlobalConstantsParser.g:672:8: lv_ranges_3_0= ruleSTExpression
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

                            // InternalGlobalConstantsParser.g:689:6: (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )*
                            loop15:
                            do {
                                int alt15=2;
                                int LA15_0 = input.LA(1);

                                if ( (LA15_0==Comma) ) {
                                    alt15=1;
                                }


                                switch (alt15) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:690:7: otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_4, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:694:7: ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    // InternalGlobalConstantsParser.g:695:8: (lv_ranges_5_0= ruleSTExpression )
                            	    {
                            	    // InternalGlobalConstantsParser.g:695:8: (lv_ranges_5_0= ruleSTExpression )
                            	    // InternalGlobalConstantsParser.g:696:9: lv_ranges_5_0= ruleSTExpression
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
                            	    break loop15;
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
                            // InternalGlobalConstantsParser.g:720:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:720:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:721:6: otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket
                            {
                            otherlv_7=(Token)match(input,LeftSquareBracket,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_1_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:725:6: ( (lv_count_8_0= Asterisk ) )
                            // InternalGlobalConstantsParser.g:726:7: (lv_count_8_0= Asterisk )
                            {
                            // InternalGlobalConstantsParser.g:726:7: (lv_count_8_0= Asterisk )
                            // InternalGlobalConstantsParser.g:727:8: lv_count_8_0= Asterisk
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

                            // InternalGlobalConstantsParser.g:739:6: (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )*
                            loop16:
                            do {
                                int alt16=2;
                                int LA16_0 = input.LA(1);

                                if ( (LA16_0==Comma) ) {
                                    alt16=1;
                                }


                                switch (alt16) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:740:7: otherlv_9= Comma ( (lv_count_10_0= Asterisk ) )
                            	    {
                            	    otherlv_9=(Token)match(input,Comma,FOLLOW_16); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_9, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:744:7: ( (lv_count_10_0= Asterisk ) )
                            	    // InternalGlobalConstantsParser.g:745:8: (lv_count_10_0= Asterisk )
                            	    {
                            	    // InternalGlobalConstantsParser.g:745:8: (lv_count_10_0= Asterisk )
                            	    // InternalGlobalConstantsParser.g:746:9: lv_count_10_0= Asterisk
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
                            	    break loop16;
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

            // InternalGlobalConstantsParser.g:770:3: ( ( ruleSTAnyType ) )
            // InternalGlobalConstantsParser.g:771:4: ( ruleSTAnyType )
            {
            // InternalGlobalConstantsParser.g:771:4: ( ruleSTAnyType )
            // InternalGlobalConstantsParser.g:772:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementCrossReference_2_0());
              				
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

            // InternalGlobalConstantsParser.g:786:3: (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==LeftSquareBracket) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalGlobalConstantsParser.g:787:4: otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket
                    {
                    otherlv_14=(Token)match(input,LeftSquareBracket,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_3_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:791:4: ( (lv_maxLength_15_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:792:5: (lv_maxLength_15_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:792:5: (lv_maxLength_15_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:793:6: lv_maxLength_15_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:819:1: entryRuleSTInitializerExpression returns [EObject current=null] : iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF ;
    public final EObject entryRuleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:819:64: (iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:820:2: iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:826:1: ruleSTInitializerExpression returns [EObject current=null] : (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) ;
    public final EObject ruleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STElementaryInitializerExpression_0 = null;

        EObject this_STArrayInitializerExpression_1 = null;

        EObject this_STStructInitializerExpression_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:832:2: ( (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) )
            // InternalGlobalConstantsParser.g:833:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:833:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            int alt20=3;
            alt20 = dfa20.predict(input);
            switch (alt20) {
                case 1 :
                    // InternalGlobalConstantsParser.g:834:3: this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression
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
                    // InternalGlobalConstantsParser.g:843:3: this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression
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
                    // InternalGlobalConstantsParser.g:852:3: this_STStructInitializerExpression_2= ruleSTStructInitializerExpression
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
    // InternalGlobalConstantsParser.g:864:1: entryRuleSTElementaryInitializerExpression returns [EObject current=null] : iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF ;
    public final EObject entryRuleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElementaryInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:864:74: (iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:865:2: iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:871:1: ruleSTElementaryInitializerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:877:2: ( ( (lv_value_0_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:878:2: ( (lv_value_0_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:878:2: ( (lv_value_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:879:3: (lv_value_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:879:3: (lv_value_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:880:4: lv_value_0_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:900:1: entryRuleSTArrayInitializerExpression returns [EObject current=null] : iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF ;
    public final EObject entryRuleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:900:69: (iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:901:2: iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:907:1: ruleSTArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:913:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalGlobalConstantsParser.g:914:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalGlobalConstantsParser.g:914:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalGlobalConstantsParser.g:915:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:919:3: ( (lv_values_1_0= ruleSTArrayInitElement ) )
            // InternalGlobalConstantsParser.g:920:4: (lv_values_1_0= ruleSTArrayInitElement )
            {
            // InternalGlobalConstantsParser.g:920:4: (lv_values_1_0= ruleSTArrayInitElement )
            // InternalGlobalConstantsParser.g:921:5: lv_values_1_0= ruleSTArrayInitElement
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

            // InternalGlobalConstantsParser.g:938:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==Comma) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:939:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:943:4: ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    // InternalGlobalConstantsParser.g:944:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    {
            	    // InternalGlobalConstantsParser.g:944:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    // InternalGlobalConstantsParser.g:945:6: lv_values_3_0= ruleSTArrayInitElement
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
            	    break loop21;
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
    // InternalGlobalConstantsParser.g:971:1: entryRuleSTArrayInitElement returns [EObject current=null] : iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF ;
    public final EObject entryRuleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:971:59: (iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF )
            // InternalGlobalConstantsParser.g:972:2: iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF
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
    // InternalGlobalConstantsParser.g:978:1: ruleSTArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? ) ;
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
            // InternalGlobalConstantsParser.g:984:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:985:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:985:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:986:3: ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:986:3: ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:987:4: (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:987:4: (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:988:5: lv_indexOrInitExpression_0_0= ruleSTInitializerExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getIndexOrInitExpressionSTInitializerExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_22);
            lv_indexOrInitExpression_0_0=ruleSTInitializerExpression();

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
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:1005:3: (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==LeftParenthesis) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1006:4: otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:1010:4: ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) )
                    // InternalGlobalConstantsParser.g:1011:5: (lv_initExpressions_2_0= ruleSTInitializerExpression )
                    {
                    // InternalGlobalConstantsParser.g:1011:5: (lv_initExpressions_2_0= ruleSTInitializerExpression )
                    // InternalGlobalConstantsParser.g:1012:6: lv_initExpressions_2_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_23);
                    lv_initExpressions_2_0=ruleSTInitializerExpression();

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
                      							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalGlobalConstantsParser.g:1029:4: (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==Comma) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // InternalGlobalConstantsParser.g:1030:5: otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getSTArrayInitElementAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalGlobalConstantsParser.g:1034:5: ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
                    	    // InternalGlobalConstantsParser.g:1035:6: (lv_initExpressions_4_0= ruleSTInitializerExpression )
                    	    {
                    	    // InternalGlobalConstantsParser.g:1035:6: (lv_initExpressions_4_0= ruleSTInitializerExpression )
                    	    // InternalGlobalConstantsParser.g:1036:7: lv_initExpressions_4_0= ruleSTInitializerExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_23);
                    	    lv_initExpressions_4_0=ruleSTInitializerExpression();

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
                    	      								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
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


    // $ANTLR start "entryRuleSTStructInitializerExpression"
    // InternalGlobalConstantsParser.g:1063:1: entryRuleSTStructInitializerExpression returns [EObject current=null] : iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF ;
    public final EObject entryRuleSTStructInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:1063:70: (iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:1064:2: iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:1070:1: ruleSTStructInitializerExpression returns [EObject current=null] : (otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis ) ;
    public final EObject ruleSTStructInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1076:2: ( (otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis ) )
            // InternalGlobalConstantsParser.g:1077:2: (otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis )
            {
            // InternalGlobalConstantsParser.g:1077:2: (otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis )
            // InternalGlobalConstantsParser.g:1078:3: otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTStructInitializerExpressionAccess().getLeftParenthesisKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1082:3: ( (lv_values_1_0= ruleSTStructInitElement ) )
            // InternalGlobalConstantsParser.g:1083:4: (lv_values_1_0= ruleSTStructInitElement )
            {
            // InternalGlobalConstantsParser.g:1083:4: (lv_values_1_0= ruleSTStructInitElement )
            // InternalGlobalConstantsParser.g:1084:5: lv_values_1_0= ruleSTStructInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_23);
            lv_values_1_0=ruleSTStructInitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSTStructInitializerExpressionRule());
              					}
              					add(
              						current,
              						"values",
              						lv_values_1_0,
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStructInitElement");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:1101:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==Comma) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1102:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_24); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTStructInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1106:4: ( (lv_values_3_0= ruleSTStructInitElement ) )
            	    // InternalGlobalConstantsParser.g:1107:5: (lv_values_3_0= ruleSTStructInitElement )
            	    {
            	    // InternalGlobalConstantsParser.g:1107:5: (lv_values_3_0= ruleSTStructInitElement )
            	    // InternalGlobalConstantsParser.g:1108:6: lv_values_3_0= ruleSTStructInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_23);
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


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTStructInitializerExpressionAccess().getRightParenthesisKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
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
    // InternalGlobalConstantsParser.g:1134:1: entryRuleSTStructInitElement returns [EObject current=null] : iv_ruleSTStructInitElement= ruleSTStructInitElement EOF ;
    public final EObject entryRuleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1134:60: (iv_ruleSTStructInitElement= ruleSTStructInitElement EOF )
            // InternalGlobalConstantsParser.g:1135:2: iv_ruleSTStructInitElement= ruleSTStructInitElement EOF
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
    // InternalGlobalConstantsParser.g:1141:1: ruleSTStructInitElement returns [EObject current=null] : ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) ;
    public final EObject ruleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1147:2: ( ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1148:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1148:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            // InternalGlobalConstantsParser.g:1149:3: ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) )
            {
            // InternalGlobalConstantsParser.g:1149:3: ( ( ruleSTFeatureName ) )
            // InternalGlobalConstantsParser.g:1150:4: ( ruleSTFeatureName )
            {
            // InternalGlobalConstantsParser.g:1150:4: ( ruleSTFeatureName )
            // InternalGlobalConstantsParser.g:1151:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTStructInitElementRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_25);
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
            // InternalGlobalConstantsParser.g:1169:3: ( (lv_value_2_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1170:4: (lv_value_2_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1170:4: (lv_value_2_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1171:5: lv_value_2_0= ruleSTInitializerExpression
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


    // $ANTLR start "entryRuleSTStatement"
    // InternalGlobalConstantsParser.g:1192:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1192:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalGlobalConstantsParser.g:1193:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalGlobalConstantsParser.g:1199:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) ;
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
            // InternalGlobalConstantsParser.g:1205:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) ) )
            // InternalGlobalConstantsParser.g:1206:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            {
            // InternalGlobalConstantsParser.g:1206:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon ) | ( () otherlv_14= Semicolon ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==LDATE_AND_TIME||LA26_0==DATE_AND_TIME||LA26_0==LTIME_OF_DAY||LA26_0==TIME_OF_DAY||LA26_0==CONTINUE||LA26_0==WSTRING||LA26_0==REPEAT||LA26_0==RETURN||LA26_0==STRING||LA26_0==DWORD||LA26_0==FALSE||(LA26_0>=LDATE && LA26_0<=LWORD)||(LA26_0>=UDINT && LA26_0<=ULINT)||(LA26_0>=USINT && LA26_0<=DINT)||LA26_0==EXIT||(LA26_0>=LINT && LA26_0<=LTOD)||(LA26_0>=REAL && LA26_0<=SINT)||(LA26_0>=THIS && LA26_0<=TRUE)||LA26_0==UINT||LA26_0==WORD||(LA26_0>=AND && LA26_0<=NOT)||LA26_0==TOD||LA26_0==XOR||(LA26_0>=DT && LA26_0<=LT)||LA26_0==OR||LA26_0==LeftParenthesis||LA26_0==PlusSign||LA26_0==HyphenMinus||(LA26_0>=D && LA26_0<=T)||(LA26_0>=RULE_NON_DECIMAL && LA26_0<=RULE_DECIMAL)||(LA26_0>=RULE_ID && LA26_0<=RULE_STRING)) ) {
                alt26=1;
            }
            else if ( (LA26_0==Semicolon) ) {
                alt26=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1207:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    {
                    // InternalGlobalConstantsParser.g:1207:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon )
                    // InternalGlobalConstantsParser.g:1208:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) ) otherlv_12= Semicolon
                    {
                    // InternalGlobalConstantsParser.g:1208:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )
                    int alt25=9;
                    alt25 = dfa25.predict(input);
                    switch (alt25) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:1209:5: this_STIfStatement_0= ruleSTIfStatement
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
                            // InternalGlobalConstantsParser.g:1218:5: this_STCaseStatement_1= ruleSTCaseStatement
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
                            // InternalGlobalConstantsParser.g:1227:5: this_STForStatement_2= ruleSTForStatement
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
                            // InternalGlobalConstantsParser.g:1236:5: this_STWhileStatement_3= ruleSTWhileStatement
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
                            // InternalGlobalConstantsParser.g:1245:5: this_STRepeatStatement_4= ruleSTRepeatStatement
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
                            // InternalGlobalConstantsParser.g:1254:5: this_STAssignment_5= ruleSTAssignment
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
                            // InternalGlobalConstantsParser.g:1263:5: ( () otherlv_7= RETURN )
                            {
                            // InternalGlobalConstantsParser.g:1263:5: ( () otherlv_7= RETURN )
                            // InternalGlobalConstantsParser.g:1264:6: () otherlv_7= RETURN
                            {
                            // InternalGlobalConstantsParser.g:1264:6: ()
                            // InternalGlobalConstantsParser.g:1265:7: 
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
                            // InternalGlobalConstantsParser.g:1277:5: ( () otherlv_9= CONTINUE )
                            {
                            // InternalGlobalConstantsParser.g:1277:5: ( () otherlv_9= CONTINUE )
                            // InternalGlobalConstantsParser.g:1278:6: () otherlv_9= CONTINUE
                            {
                            // InternalGlobalConstantsParser.g:1278:6: ()
                            // InternalGlobalConstantsParser.g:1279:7: 
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
                            // InternalGlobalConstantsParser.g:1291:5: ( () otherlv_11= EXIT )
                            {
                            // InternalGlobalConstantsParser.g:1291:5: ( () otherlv_11= EXIT )
                            // InternalGlobalConstantsParser.g:1292:6: () otherlv_11= EXIT
                            {
                            // InternalGlobalConstantsParser.g:1292:6: ()
                            // InternalGlobalConstantsParser.g:1293:7: 
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
                    // InternalGlobalConstantsParser.g:1311:3: ( () otherlv_14= Semicolon )
                    {
                    // InternalGlobalConstantsParser.g:1311:3: ( () otherlv_14= Semicolon )
                    // InternalGlobalConstantsParser.g:1312:4: () otherlv_14= Semicolon
                    {
                    // InternalGlobalConstantsParser.g:1312:4: ()
                    // InternalGlobalConstantsParser.g:1313:5: 
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
    // InternalGlobalConstantsParser.g:1328:1: entryRuleSTAssignment returns [EObject current=null] : iv_ruleSTAssignment= ruleSTAssignment EOF ;
    public final EObject entryRuleSTAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignment = null;


        try {
            // InternalGlobalConstantsParser.g:1328:53: (iv_ruleSTAssignment= ruleSTAssignment EOF )
            // InternalGlobalConstantsParser.g:1329:2: iv_ruleSTAssignment= ruleSTAssignment EOF
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
    // InternalGlobalConstantsParser.g:1335:1: ruleSTAssignment returns [EObject current=null] : (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? ) ;
    public final EObject ruleSTAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_STExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1341:2: ( (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? ) )
            // InternalGlobalConstantsParser.g:1342:2: (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? )
            {
            // InternalGlobalConstantsParser.g:1342:2: (this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )? )
            // InternalGlobalConstantsParser.g:1343:3: this_STExpression_0= ruleSTExpression ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAssignmentAccess().getSTExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_26);
            this_STExpression_0=ruleSTExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:1351:3: ( () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==ColonEqualsSign) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1352:4: () otherlv_2= ColonEqualsSign ( (lv_right_3_0= ruleSTAssignment ) )
                    {
                    // InternalGlobalConstantsParser.g:1352:4: ()
                    // InternalGlobalConstantsParser.g:1353:5: 
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
                    // InternalGlobalConstantsParser.g:1363:4: ( (lv_right_3_0= ruleSTAssignment ) )
                    // InternalGlobalConstantsParser.g:1364:5: (lv_right_3_0= ruleSTAssignment )
                    {
                    // InternalGlobalConstantsParser.g:1364:5: (lv_right_3_0= ruleSTAssignment )
                    // InternalGlobalConstantsParser.g:1365:6: lv_right_3_0= ruleSTAssignment
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
    // InternalGlobalConstantsParser.g:1387:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1387:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalGlobalConstantsParser.g:1388:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
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
    // InternalGlobalConstantsParser.g:1394:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1400:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalGlobalConstantsParser.g:1401:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalGlobalConstantsParser.g:1401:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
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
            case RULE_DECIMAL:
            case RULE_STRING:
                {
                alt28=1;
                }
                break;
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
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
                    alt28=1;
                    }
                    break;
                case ColonEqualsSign:
                    {
                    alt28=2;
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

                if ( (LA28_3==RULE_ID) ) {
                    int LA28_6 = input.LA(3);

                    if ( (LA28_6==EqualsSignGreaterThanSign) ) {
                        alt28=3;
                    }
                    else if ( (LA28_6==EOF||LA28_6==AND||LA28_6==MOD||LA28_6==XOR||(LA28_6>=AsteriskAsterisk && LA28_6<=ColonColon)||(LA28_6>=LessThanSignEqualsSign && LA28_6<=LessThanSignGreaterThanSign)||LA28_6==GreaterThanSignEqualsSign||LA28_6==OR||(LA28_6>=Ampersand && LA28_6<=Solidus)||(LA28_6>=LessThanSign && LA28_6<=GreaterThanSign)||LA28_6==LeftSquareBracket) ) {
                        alt28=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 6, input);

                        throw nvae;
                    }
                }
                else if ( (LA28_3==LDATE_AND_TIME||LA28_3==DATE_AND_TIME||LA28_3==LTIME_OF_DAY||LA28_3==TIME_OF_DAY||LA28_3==WSTRING||LA28_3==STRING||LA28_3==DWORD||LA28_3==FALSE||(LA28_3>=LDATE && LA28_3<=LWORD)||(LA28_3>=UDINT && LA28_3<=ULINT)||(LA28_3>=USINT && LA28_3<=WCHAR)||(LA28_3>=BOOL && LA28_3<=BYTE)||(LA28_3>=CHAR && LA28_3<=DINT)||(LA28_3>=LINT && LA28_3<=LTOD)||(LA28_3>=REAL && LA28_3<=SINT)||(LA28_3>=THIS && LA28_3<=TRUE)||LA28_3==UINT||LA28_3==WORD||LA28_3==AND||(LA28_3>=INT && LA28_3<=NOT)||LA28_3==TOD||LA28_3==XOR||LA28_3==DT||(LA28_3>=LD && LA28_3<=LT)||LA28_3==OR||LA28_3==LeftParenthesis||LA28_3==PlusSign||LA28_3==HyphenMinus||(LA28_3>=D && LA28_3<=T)||(LA28_3>=RULE_NON_DECIMAL && LA28_3<=RULE_DECIMAL)||LA28_3==RULE_STRING) ) {
                    alt28=1;
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
                    // InternalGlobalConstantsParser.g:1402:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
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
                    // InternalGlobalConstantsParser.g:1411:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
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
                    // InternalGlobalConstantsParser.g:1420:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
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
    // InternalGlobalConstantsParser.g:1432:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1432:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalGlobalConstantsParser.g:1433:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
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
    // InternalGlobalConstantsParser.g:1439:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_argument_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_argument_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1445:2: ( ( (lv_argument_0_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1446:2: ( (lv_argument_0_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1446:2: ( (lv_argument_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1447:3: (lv_argument_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1447:3: (lv_argument_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1448:4: lv_argument_0_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1468:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1468:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalGlobalConstantsParser.g:1469:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
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
    // InternalGlobalConstantsParser.g:1475:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_argument_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1481:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1482:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1482:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1483:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1483:3: ( (otherlv_0= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1484:4: (otherlv_0= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1484:4: (otherlv_0= RULE_ID )
            // InternalGlobalConstantsParser.g:1485:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementCrossReference_0_0());
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1500:3: ( (lv_argument_2_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1501:4: (lv_argument_2_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1501:4: (lv_argument_2_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1502:5: lv_argument_2_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1523:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1523:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalGlobalConstantsParser.g:1524:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
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
    // InternalGlobalConstantsParser.g:1530:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_argument_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1536:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1537:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1537:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1538:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1538:3: ( (lv_not_0_0= NOT ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==NOT) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1539:4: (lv_not_0_0= NOT )
                    {
                    // InternalGlobalConstantsParser.g:1539:4: (lv_not_0_0= NOT )
                    // InternalGlobalConstantsParser.g:1540:5: lv_not_0_0= NOT
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

            // InternalGlobalConstantsParser.g:1552:3: ( (otherlv_1= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1553:4: (otherlv_1= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1553:4: (otherlv_1= RULE_ID )
            // InternalGlobalConstantsParser.g:1554:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_27); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1569:3: ( (lv_argument_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1570:4: (lv_argument_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1570:4: (lv_argument_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1571:5: lv_argument_3_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1592:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1592:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalGlobalConstantsParser.g:1593:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalGlobalConstantsParser.g:1599:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalGlobalConstantsParser.g:1605:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalGlobalConstantsParser.g:1606:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalGlobalConstantsParser.g:1606:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalGlobalConstantsParser.g:1607:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1611:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1612:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1612:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1613:5: lv_condition_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:1634:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==LDATE_AND_TIME||LA30_0==DATE_AND_TIME||LA30_0==LTIME_OF_DAY||LA30_0==TIME_OF_DAY||LA30_0==CONTINUE||LA30_0==WSTRING||LA30_0==REPEAT||LA30_0==RETURN||LA30_0==STRING||LA30_0==DWORD||LA30_0==FALSE||(LA30_0>=LDATE && LA30_0<=LWORD)||(LA30_0>=UDINT && LA30_0<=ULINT)||(LA30_0>=USINT && LA30_0<=DINT)||LA30_0==EXIT||(LA30_0>=LINT && LA30_0<=LTOD)||(LA30_0>=REAL && LA30_0<=SINT)||(LA30_0>=THIS && LA30_0<=TRUE)||LA30_0==UINT||LA30_0==WORD||(LA30_0>=AND && LA30_0<=NOT)||LA30_0==TOD||LA30_0==XOR||(LA30_0>=DT && LA30_0<=LT)||LA30_0==OR||LA30_0==LeftParenthesis||LA30_0==PlusSign||LA30_0==HyphenMinus||LA30_0==Semicolon||(LA30_0>=D && LA30_0<=T)||(LA30_0>=RULE_NON_DECIMAL && LA30_0<=RULE_DECIMAL)||(LA30_0>=RULE_ID && LA30_0<=RULE_STRING)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1635:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1635:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1636:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop30;
                }
            } while (true);

            // InternalGlobalConstantsParser.g:1653:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==ELSIF) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1654:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalGlobalConstantsParser.g:1654:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalGlobalConstantsParser.g:1655:5: lv_elseifs_4_0= ruleSTElseIfPart
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
            	    break loop31;
                }
            } while (true);

            // InternalGlobalConstantsParser.g:1672:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==ELSE) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1673:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalGlobalConstantsParser.g:1673:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalGlobalConstantsParser.g:1674:5: lv_else_5_0= ruleSTElsePart
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
    // InternalGlobalConstantsParser.g:1699:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalGlobalConstantsParser.g:1699:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalGlobalConstantsParser.g:1700:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalGlobalConstantsParser.g:1706:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1712:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:1713:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:1713:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:1714:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1718:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1719:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1719:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1720:5: lv_condition_1_0= ruleSTExpression
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1741:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==LDATE_AND_TIME||LA33_0==DATE_AND_TIME||LA33_0==LTIME_OF_DAY||LA33_0==TIME_OF_DAY||LA33_0==CONTINUE||LA33_0==WSTRING||LA33_0==REPEAT||LA33_0==RETURN||LA33_0==STRING||LA33_0==DWORD||LA33_0==FALSE||(LA33_0>=LDATE && LA33_0<=LWORD)||(LA33_0>=UDINT && LA33_0<=ULINT)||(LA33_0>=USINT && LA33_0<=DINT)||LA33_0==EXIT||(LA33_0>=LINT && LA33_0<=LTOD)||(LA33_0>=REAL && LA33_0<=SINT)||(LA33_0>=THIS && LA33_0<=TRUE)||LA33_0==UINT||LA33_0==WORD||(LA33_0>=AND && LA33_0<=NOT)||LA33_0==TOD||LA33_0==XOR||(LA33_0>=DT && LA33_0<=LT)||LA33_0==OR||LA33_0==LeftParenthesis||LA33_0==PlusSign||LA33_0==HyphenMinus||LA33_0==Semicolon||(LA33_0>=D && LA33_0<=T)||(LA33_0>=RULE_NON_DECIMAL && LA33_0<=RULE_DECIMAL)||(LA33_0>=RULE_ID && LA33_0<=RULE_STRING)) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1742:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1742:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1743:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
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
    // InternalGlobalConstantsParser.g:1764:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1764:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalGlobalConstantsParser.g:1765:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalGlobalConstantsParser.g:1771:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalGlobalConstantsParser.g:1777:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalGlobalConstantsParser.g:1778:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalGlobalConstantsParser.g:1778:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalGlobalConstantsParser.g:1779:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1783:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1784:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1784:4: (lv_selector_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1785:5: lv_selector_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:1806:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt34=0;
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==LDATE_AND_TIME||LA34_0==DATE_AND_TIME||LA34_0==LTIME_OF_DAY||LA34_0==TIME_OF_DAY||LA34_0==WSTRING||LA34_0==STRING||LA34_0==DWORD||LA34_0==FALSE||(LA34_0>=LDATE && LA34_0<=LWORD)||(LA34_0>=UDINT && LA34_0<=ULINT)||(LA34_0>=USINT && LA34_0<=WCHAR)||(LA34_0>=BOOL && LA34_0<=BYTE)||(LA34_0>=CHAR && LA34_0<=DINT)||(LA34_0>=LINT && LA34_0<=LTOD)||(LA34_0>=REAL && LA34_0<=SINT)||(LA34_0>=THIS && LA34_0<=TRUE)||LA34_0==UINT||LA34_0==WORD||LA34_0==AND||(LA34_0>=INT && LA34_0<=NOT)||LA34_0==TOD||LA34_0==XOR||LA34_0==DT||(LA34_0>=LD && LA34_0<=LT)||LA34_0==OR||LA34_0==LeftParenthesis||LA34_0==PlusSign||LA34_0==HyphenMinus||(LA34_0>=D && LA34_0<=T)||(LA34_0>=RULE_NON_DECIMAL && LA34_0<=RULE_DECIMAL)||(LA34_0>=RULE_ID && LA34_0<=RULE_STRING)) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1807:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalGlobalConstantsParser.g:1807:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalGlobalConstantsParser.g:1808:5: lv_cases_3_0= ruleSTCaseCases
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_33);
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

            // InternalGlobalConstantsParser.g:1825:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ELSE) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1826:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalGlobalConstantsParser.g:1826:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalGlobalConstantsParser.g:1827:5: lv_else_4_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_34);
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
    // InternalGlobalConstantsParser.g:1852:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalGlobalConstantsParser.g:1852:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalGlobalConstantsParser.g:1853:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalGlobalConstantsParser.g:1859:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1865:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:1866:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:1866:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:1867:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalGlobalConstantsParser.g:1867:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1868:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1868:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1869:5: lv_conditions_0_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_35);
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

            // InternalGlobalConstantsParser.g:1886:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==Comma) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1887:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1891:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalGlobalConstantsParser.g:1892:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:1892:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalGlobalConstantsParser.g:1893:6: lv_conditions_2_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_35);
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

            otherlv_3=(Token)match(input,Colon,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1915:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop37:
            do {
                int alt37=2;
                alt37 = dfa37.predict(input);
                switch (alt37) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1916:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1920:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1921:5: lv_statements_4_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
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
    // InternalGlobalConstantsParser.g:1942:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalGlobalConstantsParser.g:1942:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalGlobalConstantsParser.g:1943:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalGlobalConstantsParser.g:1949:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1955:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:1956:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:1956:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:1957:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalGlobalConstantsParser.g:1957:3: ()
            // InternalGlobalConstantsParser.g:1958:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,ELSE,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1968:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==LDATE_AND_TIME||LA38_0==DATE_AND_TIME||LA38_0==LTIME_OF_DAY||LA38_0==TIME_OF_DAY||LA38_0==CONTINUE||LA38_0==WSTRING||LA38_0==REPEAT||LA38_0==RETURN||LA38_0==STRING||LA38_0==DWORD||LA38_0==FALSE||(LA38_0>=LDATE && LA38_0<=LWORD)||(LA38_0>=UDINT && LA38_0<=ULINT)||(LA38_0>=USINT && LA38_0<=DINT)||LA38_0==EXIT||(LA38_0>=LINT && LA38_0<=LTOD)||(LA38_0>=REAL && LA38_0<=SINT)||(LA38_0>=THIS && LA38_0<=TRUE)||LA38_0==UINT||LA38_0==WORD||(LA38_0>=AND && LA38_0<=NOT)||LA38_0==TOD||LA38_0==XOR||(LA38_0>=DT && LA38_0<=LT)||LA38_0==OR||LA38_0==LeftParenthesis||LA38_0==PlusSign||LA38_0==HyphenMinus||LA38_0==Semicolon||(LA38_0>=D && LA38_0<=T)||(LA38_0>=RULE_NON_DECIMAL && LA38_0<=RULE_DECIMAL)||(LA38_0>=RULE_ID && LA38_0<=RULE_STRING)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1969:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1969:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1970:5: lv_statements_2_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
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
    // InternalGlobalConstantsParser.g:1991:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1991:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalGlobalConstantsParser.g:1992:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalGlobalConstantsParser.g:1998:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
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
            // InternalGlobalConstantsParser.g:2004:2: ( (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalGlobalConstantsParser.g:2005:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalGlobalConstantsParser.g:2005:2: (otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalGlobalConstantsParser.g:2006:3: otherlv_0= FOR ( (lv_variable_1_0= ruleSTExpression ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2010:3: ( (lv_variable_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2011:4: (lv_variable_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2011:4: (lv_variable_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2012:5: lv_variable_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getVariableSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_25);
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
            // InternalGlobalConstantsParser.g:2033:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2034:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2034:4: (lv_from_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2035:5: lv_from_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_36);
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
            // InternalGlobalConstantsParser.g:2056:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2057:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2057:4: (lv_to_5_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2058:5: lv_to_5_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_37);
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

            // InternalGlobalConstantsParser.g:2075:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==BY) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2076:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:2080:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:2081:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:2081:5: (lv_by_7_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:2082:6: lv_by_7_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_38);
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

            otherlv_8=(Token)match(input,DO,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
              		
            }
            // InternalGlobalConstantsParser.g:2104:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==LDATE_AND_TIME||LA40_0==DATE_AND_TIME||LA40_0==LTIME_OF_DAY||LA40_0==TIME_OF_DAY||LA40_0==CONTINUE||LA40_0==WSTRING||LA40_0==REPEAT||LA40_0==RETURN||LA40_0==STRING||LA40_0==DWORD||LA40_0==FALSE||(LA40_0>=LDATE && LA40_0<=LWORD)||(LA40_0>=UDINT && LA40_0<=ULINT)||(LA40_0>=USINT && LA40_0<=DINT)||LA40_0==EXIT||(LA40_0>=LINT && LA40_0<=LTOD)||(LA40_0>=REAL && LA40_0<=SINT)||(LA40_0>=THIS && LA40_0<=TRUE)||LA40_0==UINT||LA40_0==WORD||(LA40_0>=AND && LA40_0<=NOT)||LA40_0==TOD||LA40_0==XOR||(LA40_0>=DT && LA40_0<=LT)||LA40_0==OR||LA40_0==LeftParenthesis||LA40_0==PlusSign||LA40_0==HyphenMinus||LA40_0==Semicolon||(LA40_0>=D && LA40_0<=T)||(LA40_0>=RULE_NON_DECIMAL && LA40_0<=RULE_DECIMAL)||(LA40_0>=RULE_ID && LA40_0<=RULE_STRING)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2105:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2105:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2106:5: lv_statements_9_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_39);
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
    // InternalGlobalConstantsParser.g:2131:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2131:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalGlobalConstantsParser.g:2132:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalGlobalConstantsParser.g:2138:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2144:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalGlobalConstantsParser.g:2145:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalGlobalConstantsParser.g:2145:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalGlobalConstantsParser.g:2146:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2150:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2151:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2151:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2152:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_38);
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

            otherlv_2=(Token)match(input,DO,FOLLOW_40); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2173:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==LDATE_AND_TIME||LA41_0==DATE_AND_TIME||LA41_0==LTIME_OF_DAY||LA41_0==TIME_OF_DAY||LA41_0==CONTINUE||LA41_0==WSTRING||LA41_0==REPEAT||LA41_0==RETURN||LA41_0==STRING||LA41_0==DWORD||LA41_0==FALSE||(LA41_0>=LDATE && LA41_0<=LWORD)||(LA41_0>=UDINT && LA41_0<=ULINT)||(LA41_0>=USINT && LA41_0<=DINT)||LA41_0==EXIT||(LA41_0>=LINT && LA41_0<=LTOD)||(LA41_0>=REAL && LA41_0<=SINT)||(LA41_0>=THIS && LA41_0<=TRUE)||LA41_0==UINT||LA41_0==WORD||(LA41_0>=AND && LA41_0<=NOT)||LA41_0==TOD||LA41_0==XOR||(LA41_0>=DT && LA41_0<=LT)||LA41_0==OR||LA41_0==LeftParenthesis||LA41_0==PlusSign||LA41_0==HyphenMinus||LA41_0==Semicolon||(LA41_0>=D && LA41_0<=T)||(LA41_0>=RULE_NON_DECIMAL && LA41_0<=RULE_DECIMAL)||(LA41_0>=RULE_ID && LA41_0<=RULE_STRING)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2174:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2174:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2175:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_40);
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
    // InternalGlobalConstantsParser.g:2200:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2200:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalGlobalConstantsParser.g:2201:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalGlobalConstantsParser.g:2207:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2213:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalGlobalConstantsParser.g:2214:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalGlobalConstantsParser.g:2214:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalGlobalConstantsParser.g:2215:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_41); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2219:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==LDATE_AND_TIME||LA42_0==DATE_AND_TIME||LA42_0==LTIME_OF_DAY||LA42_0==TIME_OF_DAY||LA42_0==CONTINUE||LA42_0==WSTRING||LA42_0==REPEAT||LA42_0==RETURN||LA42_0==STRING||LA42_0==DWORD||LA42_0==FALSE||(LA42_0>=LDATE && LA42_0<=LWORD)||(LA42_0>=UDINT && LA42_0<=ULINT)||(LA42_0>=USINT && LA42_0<=DINT)||LA42_0==EXIT||(LA42_0>=LINT && LA42_0<=LTOD)||(LA42_0>=REAL && LA42_0<=SINT)||(LA42_0>=THIS && LA42_0<=TRUE)||LA42_0==UINT||LA42_0==WORD||(LA42_0>=AND && LA42_0<=NOT)||LA42_0==TOD||LA42_0==XOR||(LA42_0>=DT && LA42_0<=LT)||LA42_0==OR||LA42_0==LeftParenthesis||LA42_0==PlusSign||LA42_0==HyphenMinus||LA42_0==Semicolon||(LA42_0>=D && LA42_0<=T)||(LA42_0>=RULE_NON_DECIMAL && LA42_0<=RULE_DECIMAL)||(LA42_0>=RULE_ID && LA42_0<=RULE_STRING)) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2220:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2220:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2221:5: lv_statements_1_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_41);
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

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2242:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2243:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2243:4: (lv_condition_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2244:5: lv_condition_3_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_42);
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
    // InternalGlobalConstantsParser.g:2269:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2269:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalGlobalConstantsParser.g:2270:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalGlobalConstantsParser.g:2276:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2282:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalGlobalConstantsParser.g:2283:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalGlobalConstantsParser.g:2294:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2294:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalGlobalConstantsParser.g:2295:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalGlobalConstantsParser.g:2301:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2307:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2308:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2308:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2309:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_43);
            this_STOrExpression_0=ruleSTOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STOrExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2317:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==FullStopFullStop) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2318:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2318:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2319:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2319:5: ()
            	    // InternalGlobalConstantsParser.g:2320:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2326:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalGlobalConstantsParser.g:2327:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2327:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalGlobalConstantsParser.g:2328:7: lv_op_2_0= ruleSubrangeOperator
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

            	    // InternalGlobalConstantsParser.g:2346:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalGlobalConstantsParser.g:2347:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2347:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalGlobalConstantsParser.g:2348:6: lv_right_3_0= ruleSTOrExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_43);
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
    // InternalGlobalConstantsParser.g:2370:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2370:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalGlobalConstantsParser.g:2371:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalGlobalConstantsParser.g:2377:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2383:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2384:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2384:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2385:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_STXorExpression_0=ruleSTXorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STXorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2393:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==OR) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2394:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2394:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2395:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2395:5: ()
            	    // InternalGlobalConstantsParser.g:2396:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2402:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalGlobalConstantsParser.g:2403:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2403:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalGlobalConstantsParser.g:2404:7: lv_op_2_0= ruleOrOperator
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

            	    // InternalGlobalConstantsParser.g:2422:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalGlobalConstantsParser.g:2423:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2423:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalGlobalConstantsParser.g:2424:6: lv_right_3_0= ruleSTXorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
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
    // InternalGlobalConstantsParser.g:2446:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2446:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalGlobalConstantsParser.g:2447:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalGlobalConstantsParser.g:2453:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2459:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2460:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2460:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2461:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_45);
            this_STAndExpression_0=ruleSTAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2469:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==XOR) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2470:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2470:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2471:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2471:5: ()
            	    // InternalGlobalConstantsParser.g:2472:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2478:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalGlobalConstantsParser.g:2479:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2479:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalGlobalConstantsParser.g:2480:7: lv_op_2_0= ruleXorOperator
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

            	    // InternalGlobalConstantsParser.g:2498:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalGlobalConstantsParser.g:2499:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2499:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalGlobalConstantsParser.g:2500:6: lv_right_3_0= ruleSTAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_45);
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
    // InternalGlobalConstantsParser.g:2522:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2522:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalGlobalConstantsParser.g:2523:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalGlobalConstantsParser.g:2529:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2535:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2536:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2536:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2537:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_STEqualityExpression_0=ruleSTEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2545:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==AND||LA46_0==Ampersand) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2546:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2546:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2547:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2547:5: ()
            	    // InternalGlobalConstantsParser.g:2548:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2554:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalGlobalConstantsParser.g:2555:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2555:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalGlobalConstantsParser.g:2556:7: lv_op_2_0= ruleAndOperator
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

            	    // InternalGlobalConstantsParser.g:2574:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalGlobalConstantsParser.g:2575:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2575:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalGlobalConstantsParser.g:2576:6: lv_right_3_0= ruleSTEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_46);
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
    // InternalGlobalConstantsParser.g:2598:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2598:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalGlobalConstantsParser.g:2599:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalGlobalConstantsParser.g:2605:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2611:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2612:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2612:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2613:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_47);
            this_STComparisonExpression_0=ruleSTComparisonExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STComparisonExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2621:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==LessThanSignGreaterThanSign||LA47_0==EqualsSign) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2622:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2622:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2623:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2623:5: ()
            	    // InternalGlobalConstantsParser.g:2624:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2630:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalGlobalConstantsParser.g:2631:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2631:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalGlobalConstantsParser.g:2632:7: lv_op_2_0= ruleEqualityOperator
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

            	    // InternalGlobalConstantsParser.g:2650:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalGlobalConstantsParser.g:2651:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2651:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalGlobalConstantsParser.g:2652:6: lv_right_3_0= ruleSTComparisonExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
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
    // InternalGlobalConstantsParser.g:2674:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2674:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalGlobalConstantsParser.g:2675:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalGlobalConstantsParser.g:2681:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2687:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2688:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2688:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2689:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_48);
            this_STAddSubExpression_0=ruleSTAddSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STAddSubExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2697:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==LessThanSignEqualsSign||LA48_0==GreaterThanSignEqualsSign||LA48_0==LessThanSign||LA48_0==GreaterThanSign) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2698:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2698:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2699:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2699:5: ()
            	    // InternalGlobalConstantsParser.g:2700:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2706:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalGlobalConstantsParser.g:2707:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2707:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalGlobalConstantsParser.g:2708:7: lv_op_2_0= ruleCompareOperator
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

            	    // InternalGlobalConstantsParser.g:2726:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalGlobalConstantsParser.g:2727:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2727:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalGlobalConstantsParser.g:2728:6: lv_right_3_0= ruleSTAddSubExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_48);
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
    // InternalGlobalConstantsParser.g:2750:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2750:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalGlobalConstantsParser.g:2751:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalGlobalConstantsParser.g:2757:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2763:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2764:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2764:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2765:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_STMulDivModExpression_0=ruleSTMulDivModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STMulDivModExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2773:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==PlusSign||LA49_0==HyphenMinus) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2774:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2774:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2775:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2775:5: ()
            	    // InternalGlobalConstantsParser.g:2776:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2782:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalGlobalConstantsParser.g:2783:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2783:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalGlobalConstantsParser.g:2784:7: lv_op_2_0= ruleAddSubOperator
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

            	    // InternalGlobalConstantsParser.g:2802:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalGlobalConstantsParser.g:2803:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2803:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalGlobalConstantsParser.g:2804:6: lv_right_3_0= ruleSTMulDivModExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
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
    // InternalGlobalConstantsParser.g:2826:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2826:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalGlobalConstantsParser.g:2827:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalGlobalConstantsParser.g:2833:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2839:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2840:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2840:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2841:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_50);
            this_STPowerExpression_0=ruleSTPowerExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPowerExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2849:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==MOD||LA50_0==Asterisk||LA50_0==Solidus) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2850:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2850:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2851:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2851:5: ()
            	    // InternalGlobalConstantsParser.g:2852:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2858:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalGlobalConstantsParser.g:2859:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2859:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalGlobalConstantsParser.g:2860:7: lv_op_2_0= ruleMulDivModOperator
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

            	    // InternalGlobalConstantsParser.g:2878:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalGlobalConstantsParser.g:2879:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2879:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalGlobalConstantsParser.g:2880:6: lv_right_3_0= ruleSTPowerExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_50);
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
    // InternalGlobalConstantsParser.g:2902:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2902:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalGlobalConstantsParser.g:2903:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalGlobalConstantsParser.g:2909:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2915:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2916:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2916:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2917:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_STUnaryExpression_0=ruleSTUnaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STUnaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:2925:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==AsteriskAsterisk) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2926:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2926:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2927:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2927:5: ()
            	    // InternalGlobalConstantsParser.g:2928:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2934:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalGlobalConstantsParser.g:2935:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2935:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalGlobalConstantsParser.g:2936:7: lv_op_2_0= rulePowerOperator
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

            	    // InternalGlobalConstantsParser.g:2954:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalGlobalConstantsParser.g:2955:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2955:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalGlobalConstantsParser.g:2956:6: lv_right_3_0= ruleSTUnaryExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_51);
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
    // InternalGlobalConstantsParser.g:2978:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2978:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalGlobalConstantsParser.g:2979:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalGlobalConstantsParser.g:2985:1: ruleSTUnaryExpression returns [EObject current=null] : ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2991:2: ( ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalGlobalConstantsParser.g:2992:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalGlobalConstantsParser.g:2992:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            int alt52=2;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2993:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    {
                    // InternalGlobalConstantsParser.g:2993:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    // InternalGlobalConstantsParser.g:2994:4: ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalGlobalConstantsParser.g:3005:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalGlobalConstantsParser.g:3005:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    // InternalGlobalConstantsParser.g:3006:4: () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    {
                    // InternalGlobalConstantsParser.g:3006:4: ()
                    // InternalGlobalConstantsParser.g:3007:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGlobalConstantsParser.g:3013:4: ( (lv_op_2_0= ruleUnaryOperator ) )
                    // InternalGlobalConstantsParser.g:3014:5: (lv_op_2_0= ruleUnaryOperator )
                    {
                    // InternalGlobalConstantsParser.g:3014:5: (lv_op_2_0= ruleUnaryOperator )
                    // InternalGlobalConstantsParser.g:3015:6: lv_op_2_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_13);
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

                    // InternalGlobalConstantsParser.g:3032:4: ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    // InternalGlobalConstantsParser.g:3033:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    {
                    // InternalGlobalConstantsParser.g:3033:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    // InternalGlobalConstantsParser.g:3034:6: lv_expression_3_0= ruleSTUnaryExpression
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
    // InternalGlobalConstantsParser.g:3056:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3056:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalGlobalConstantsParser.g:3057:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalGlobalConstantsParser.g:3063:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalGlobalConstantsParser.g:3069:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalGlobalConstantsParser.g:3070:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalGlobalConstantsParser.g:3070:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalGlobalConstantsParser.g:3071:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_52);
            this_STPrimaryExpression_0=ruleSTPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_STPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:3079:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
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
            	    // InternalGlobalConstantsParser.g:3080:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3080:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalGlobalConstantsParser.g:3081:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3081:5: ()
            	    // InternalGlobalConstantsParser.g:3082:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_2=(Token)match(input,FullStop,FOLLOW_53); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
            	      				
            	    }
            	    // InternalGlobalConstantsParser.g:3092:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalGlobalConstantsParser.g:3093:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3093:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalGlobalConstantsParser.g:3094:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3094:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt53=2;
            	    int LA53_0 = input.LA(1);

            	    if ( (LA53_0==AND||LA53_0==MOD||LA53_0==XOR||LA53_0==DT||(LA53_0>=LD && LA53_0<=LT)||LA53_0==OR||LA53_0==D||LA53_0==RULE_ID) ) {
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
            	            // InternalGlobalConstantsParser.g:3095:8: lv_member_3_1= ruleSTFeatureExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_52);
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
            	            // InternalGlobalConstantsParser.g:3111:8: lv_member_3_2= ruleSTMultibitPartialExpression
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
            	              							
            	            }
            	            pushFollow(FOLLOW_52);
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
            	    // InternalGlobalConstantsParser.g:3131:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalGlobalConstantsParser.g:3131:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalGlobalConstantsParser.g:3132:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalGlobalConstantsParser.g:3132:5: ()
            	    // InternalGlobalConstantsParser.g:3133:6: 
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
            	    // InternalGlobalConstantsParser.g:3143:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalGlobalConstantsParser.g:3144:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3144:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalGlobalConstantsParser.g:3145:7: lv_index_6_0= ruleSTExpression
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

            	    // InternalGlobalConstantsParser.g:3162:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop54:
            	    do {
            	        int alt54=2;
            	        int LA54_0 = input.LA(1);

            	        if ( (LA54_0==Comma) ) {
            	            alt54=1;
            	        }


            	        switch (alt54) {
            	    	case 1 :
            	    	    // InternalGlobalConstantsParser.g:3163:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalGlobalConstantsParser.g:3167:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalGlobalConstantsParser.g:3168:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalGlobalConstantsParser.g:3168:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalGlobalConstantsParser.g:3169:8: lv_index_8_0= ruleSTExpression
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
            	    	    break loop54;
            	        }
            	    } while (true);

            	    otherlv_9=(Token)match(input,RightSquareBracket,FOLLOW_52); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:3197:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3197:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalGlobalConstantsParser.g:3198:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalGlobalConstantsParser.g:3204:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression | this_STLiteralExpressions_5= ruleSTLiteralExpressions ) ;
    public final EObject ruleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_STExpression_1 = null;

        EObject this_STFeatureExpression_3 = null;

        EObject this_STBuiltinFeatureExpression_4 = null;

        EObject this_STLiteralExpressions_5 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3210:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression | this_STLiteralExpressions_5= ruleSTLiteralExpressions ) )
            // InternalGlobalConstantsParser.g:3211:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression | this_STLiteralExpressions_5= ruleSTLiteralExpressions )
            {
            // InternalGlobalConstantsParser.g:3211:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression | this_STLiteralExpressions_5= ruleSTLiteralExpressions )
            int alt56=4;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt56=1;
                }
                break;
            case AND:
            case MOD:
            case XOR:
            case OR:
            case RULE_ID:
                {
                alt56=2;
                }
                break;
            case LT:
                {
                int LA56_3 = input.LA(2);

                if ( (LA56_3==EOF||LA56_3==END_REPEAT||LA56_3==THEN||LA56_3==AND||LA56_3==MOD||LA56_3==XOR||(LA56_3>=AsteriskAsterisk && LA56_3<=FullStopFullStop)||(LA56_3>=ColonEqualsSign && LA56_3<=LessThanSignGreaterThanSign)||LA56_3==GreaterThanSignEqualsSign||(LA56_3>=BY && LA56_3<=DO)||LA56_3==OF||(LA56_3>=OR && LA56_3<=TO)||(LA56_3>=Ampersand && LA56_3<=GreaterThanSign)||(LA56_3>=LeftSquareBracket && LA56_3<=RightSquareBracket)) ) {
                    alt56=2;
                }
                else if ( (LA56_3==NumberSign) ) {
                    alt56=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 56, 3, input);

                    throw nvae;
                }
                }
                break;
            case D:
                {
                int LA56_4 = input.LA(2);

                if ( (LA56_4==NumberSign) ) {
                    alt56=4;
                }
                else if ( (LA56_4==EOF||LA56_4==END_REPEAT||LA56_4==THEN||LA56_4==AND||LA56_4==MOD||LA56_4==XOR||(LA56_4>=AsteriskAsterisk && LA56_4<=FullStopFullStop)||(LA56_4>=ColonEqualsSign && LA56_4<=LessThanSignGreaterThanSign)||LA56_4==GreaterThanSignEqualsSign||(LA56_4>=BY && LA56_4<=DO)||LA56_4==OF||(LA56_4>=OR && LA56_4<=TO)||(LA56_4>=Ampersand && LA56_4<=GreaterThanSign)||(LA56_4>=LeftSquareBracket && LA56_4<=RightSquareBracket)) ) {
                    alt56=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 56, 4, input);

                    throw nvae;
                }
                }
                break;
            case DT:
                {
                int LA56_5 = input.LA(2);

                if ( (LA56_5==EOF||LA56_5==END_REPEAT||LA56_5==THEN||LA56_5==AND||LA56_5==MOD||LA56_5==XOR||(LA56_5>=AsteriskAsterisk && LA56_5<=FullStopFullStop)||(LA56_5>=ColonEqualsSign && LA56_5<=LessThanSignGreaterThanSign)||LA56_5==GreaterThanSignEqualsSign||(LA56_5>=BY && LA56_5<=DO)||LA56_5==OF||(LA56_5>=OR && LA56_5<=TO)||(LA56_5>=Ampersand && LA56_5<=GreaterThanSign)||(LA56_5>=LeftSquareBracket && LA56_5<=RightSquareBracket)) ) {
                    alt56=2;
                }
                else if ( (LA56_5==NumberSign) ) {
                    alt56=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 56, 5, input);

                    throw nvae;
                }
                }
                break;
            case LD:
                {
                int LA56_6 = input.LA(2);

                if ( (LA56_6==NumberSign) ) {
                    alt56=4;
                }
                else if ( (LA56_6==EOF||LA56_6==END_REPEAT||LA56_6==THEN||LA56_6==AND||LA56_6==MOD||LA56_6==XOR||(LA56_6>=AsteriskAsterisk && LA56_6<=FullStopFullStop)||(LA56_6>=ColonEqualsSign && LA56_6<=LessThanSignGreaterThanSign)||LA56_6==GreaterThanSignEqualsSign||(LA56_6>=BY && LA56_6<=DO)||LA56_6==OF||(LA56_6>=OR && LA56_6<=TO)||(LA56_6>=Ampersand && LA56_6<=GreaterThanSign)||(LA56_6>=LeftSquareBracket && LA56_6<=RightSquareBracket)) ) {
                    alt56=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 56, 6, input);

                    throw nvae;
                }
                }
                break;
            case THIS:
                {
                alt56=3;
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
            case PlusSign:
            case HyphenMinus:
            case T:
            case RULE_NON_DECIMAL:
            case RULE_INT:
            case RULE_DECIMAL:
            case RULE_STRING:
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
                    // InternalGlobalConstantsParser.g:3212:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3212:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalGlobalConstantsParser.g:3213:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_54);
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
                    // InternalGlobalConstantsParser.g:3231:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalGlobalConstantsParser.g:3240:3: this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression
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
                case 4 :
                    // InternalGlobalConstantsParser.g:3249:3: this_STLiteralExpressions_5= ruleSTLiteralExpressions
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTLiteralExpressionsParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_STLiteralExpressions_5=ruleSTLiteralExpressions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_STLiteralExpressions_5;
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
    // InternalGlobalConstantsParser.g:3261:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3261:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalGlobalConstantsParser.g:3262:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalGlobalConstantsParser.g:3268:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token lv_call_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3274:2: ( ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:3275:2: ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:3275:2: ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:3276:3: () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:3276:3: ()
            // InternalGlobalConstantsParser.g:3277:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3283:3: ( ( ruleSTFeatureName ) )
            // InternalGlobalConstantsParser.g:3284:4: ( ruleSTFeatureName )
            {
            // InternalGlobalConstantsParser.g:3284:4: ( ruleSTFeatureName )
            // InternalGlobalConstantsParser.g:3285:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_22);
            ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:3299:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt59=2;
            alt59 = dfa59.predict(input);
            switch (alt59) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3300:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalGlobalConstantsParser.g:3300:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) )
                    // InternalGlobalConstantsParser.g:3301:5: ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3305:5: (lv_call_2_0= LeftParenthesis )
                    // InternalGlobalConstantsParser.g:3306:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_55); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:3318:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==LDATE_AND_TIME||LA58_0==DATE_AND_TIME||LA58_0==LTIME_OF_DAY||LA58_0==TIME_OF_DAY||LA58_0==WSTRING||LA58_0==STRING||LA58_0==DWORD||LA58_0==FALSE||(LA58_0>=LDATE && LA58_0<=LWORD)||(LA58_0>=UDINT && LA58_0<=ULINT)||(LA58_0>=USINT && LA58_0<=WCHAR)||(LA58_0>=BOOL && LA58_0<=BYTE)||(LA58_0>=CHAR && LA58_0<=DINT)||(LA58_0>=LINT && LA58_0<=LTOD)||(LA58_0>=REAL && LA58_0<=SINT)||(LA58_0>=THIS && LA58_0<=TRUE)||LA58_0==UINT||LA58_0==WORD||LA58_0==AND||(LA58_0>=INT && LA58_0<=NOT)||LA58_0==TOD||LA58_0==XOR||LA58_0==DT||(LA58_0>=LD && LA58_0<=LT)||LA58_0==OR||LA58_0==LeftParenthesis||LA58_0==PlusSign||LA58_0==HyphenMinus||(LA58_0>=D && LA58_0<=T)||(LA58_0>=RULE_NON_DECIMAL && LA58_0<=RULE_DECIMAL)||(LA58_0>=RULE_ID && LA58_0<=RULE_STRING)) ) {
                        alt58=1;
                    }
                    switch (alt58) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:3319:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalGlobalConstantsParser.g:3319:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalGlobalConstantsParser.g:3320:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalGlobalConstantsParser.g:3320:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalGlobalConstantsParser.g:3321:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
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

                            // InternalGlobalConstantsParser.g:3338:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop57:
                            do {
                                int alt57=2;
                                int LA57_0 = input.LA(1);

                                if ( (LA57_0==Comma) ) {
                                    alt57=1;
                                }


                                switch (alt57) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:3339:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalGlobalConstantsParser.g:3343:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalGlobalConstantsParser.g:3344:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalGlobalConstantsParser.g:3344:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalGlobalConstantsParser.g:3345:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_23);
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


    // $ANTLR start "entryRuleSTFeatureName"
    // InternalGlobalConstantsParser.g:3373:1: entryRuleSTFeatureName returns [String current=null] : iv_ruleSTFeatureName= ruleSTFeatureName EOF ;
    public final String entryRuleSTFeatureName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTFeatureName = null;


        try {
            // InternalGlobalConstantsParser.g:3373:53: (iv_ruleSTFeatureName= ruleSTFeatureName EOF )
            // InternalGlobalConstantsParser.g:3374:2: iv_ruleSTFeatureName= ruleSTFeatureName EOF
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
    // InternalGlobalConstantsParser.g:3380:1: ruleSTFeatureName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTFeatureName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3386:2: ( (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) )
            // InternalGlobalConstantsParser.g:3387:2: (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            {
            // InternalGlobalConstantsParser.g:3387:2: (this_QualifiedName_0= ruleQualifiedName | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            int alt60=9;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt60=1;
                }
                break;
            case LT:
                {
                alt60=2;
                }
                break;
            case AND:
                {
                alt60=3;
                }
                break;
            case OR:
                {
                alt60=4;
                }
                break;
            case XOR:
                {
                alt60=5;
                }
                break;
            case MOD:
                {
                alt60=6;
                }
                break;
            case D:
                {
                alt60=7;
                }
                break;
            case DT:
                {
                alt60=8;
                }
                break;
            case LD:
                {
                alt60=9;
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
                    // InternalGlobalConstantsParser.g:3388:3: this_QualifiedName_0= ruleQualifiedName
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
                    // InternalGlobalConstantsParser.g:3399:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:3405:3: kw= AND
                    {
                    kw=(Token)match(input,AND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getANDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:3411:3: kw= OR
                    {
                    kw=(Token)match(input,OR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getORKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:3417:3: kw= XOR
                    {
                    kw=(Token)match(input,XOR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getXORKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsParser.g:3423:3: kw= MOD
                    {
                    kw=(Token)match(input,MOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getMODKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsParser.g:3429:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGlobalConstantsParser.g:3435:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGlobalConstantsParser.g:3441:3: kw= LD
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
    // InternalGlobalConstantsParser.g:3450:1: entryRuleSTBuiltinFeatureExpression returns [EObject current=null] : iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF ;
    public final EObject entryRuleSTBuiltinFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTBuiltinFeatureExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3450:67: (iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF )
            // InternalGlobalConstantsParser.g:3451:2: iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF
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
    // InternalGlobalConstantsParser.g:3457:1: ruleSTBuiltinFeatureExpression returns [EObject current=null] : ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
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
            // InternalGlobalConstantsParser.g:3463:2: ( ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:3464:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:3464:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:3465:3: () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:3465:3: ()
            // InternalGlobalConstantsParser.g:3466:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTBuiltinFeatureExpressionAccess().getSTBuiltinFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3472:3: ( (lv_feature_1_0= ruleSTBuiltinFeature ) )
            // InternalGlobalConstantsParser.g:3473:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            {
            // InternalGlobalConstantsParser.g:3473:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            // InternalGlobalConstantsParser.g:3474:5: lv_feature_1_0= ruleSTBuiltinFeature
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureSTBuiltinFeatureEnumRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_22);
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

            // InternalGlobalConstantsParser.g:3491:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt63=2;
            alt63 = dfa63.predict(input);
            switch (alt63) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3492:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalGlobalConstantsParser.g:3492:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) )
                    // InternalGlobalConstantsParser.g:3493:5: ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3497:5: (lv_call_2_0= LeftParenthesis )
                    // InternalGlobalConstantsParser.g:3498:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_55); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:3510:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==LDATE_AND_TIME||LA62_0==DATE_AND_TIME||LA62_0==LTIME_OF_DAY||LA62_0==TIME_OF_DAY||LA62_0==WSTRING||LA62_0==STRING||LA62_0==DWORD||LA62_0==FALSE||(LA62_0>=LDATE && LA62_0<=LWORD)||(LA62_0>=UDINT && LA62_0<=ULINT)||(LA62_0>=USINT && LA62_0<=WCHAR)||(LA62_0>=BOOL && LA62_0<=BYTE)||(LA62_0>=CHAR && LA62_0<=DINT)||(LA62_0>=LINT && LA62_0<=LTOD)||(LA62_0>=REAL && LA62_0<=SINT)||(LA62_0>=THIS && LA62_0<=TRUE)||LA62_0==UINT||LA62_0==WORD||LA62_0==AND||(LA62_0>=INT && LA62_0<=NOT)||LA62_0==TOD||LA62_0==XOR||LA62_0==DT||(LA62_0>=LD && LA62_0<=LT)||LA62_0==OR||LA62_0==LeftParenthesis||LA62_0==PlusSign||LA62_0==HyphenMinus||(LA62_0>=D && LA62_0<=T)||(LA62_0>=RULE_NON_DECIMAL && LA62_0<=RULE_DECIMAL)||(LA62_0>=RULE_ID && LA62_0<=RULE_STRING)) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:3511:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalGlobalConstantsParser.g:3511:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalGlobalConstantsParser.g:3512:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalGlobalConstantsParser.g:3512:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalGlobalConstantsParser.g:3513:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
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

                            // InternalGlobalConstantsParser.g:3530:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop61:
                            do {
                                int alt61=2;
                                int LA61_0 = input.LA(1);

                                if ( (LA61_0==Comma) ) {
                                    alt61=1;
                                }


                                switch (alt61) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:3531:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_13); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTBuiltinFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalGlobalConstantsParser.g:3535:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalGlobalConstantsParser.g:3536:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalGlobalConstantsParser.g:3536:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalGlobalConstantsParser.g:3537:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_23);
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
                            	    break loop61;
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
    // InternalGlobalConstantsParser.g:3565:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3565:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalGlobalConstantsParser.g:3566:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalGlobalConstantsParser.g:3572:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_specifier_1_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3578:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) )
            // InternalGlobalConstantsParser.g:3579:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            {
            // InternalGlobalConstantsParser.g:3579:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            // InternalGlobalConstantsParser.g:3580:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            {
            // InternalGlobalConstantsParser.g:3580:3: ()
            // InternalGlobalConstantsParser.g:3581:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3587:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( ((LA64_0>=B && LA64_0<=X)) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3588:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalGlobalConstantsParser.g:3588:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalGlobalConstantsParser.g:3589:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_56);
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

            // InternalGlobalConstantsParser.g:3606:3: ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==RULE_INT) ) {
                alt65=1;
            }
            else if ( (LA65_0==LeftParenthesis) ) {
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
                    // InternalGlobalConstantsParser.g:3607:4: ( (lv_index_2_0= RULE_INT ) )
                    {
                    // InternalGlobalConstantsParser.g:3607:4: ( (lv_index_2_0= RULE_INT ) )
                    // InternalGlobalConstantsParser.g:3608:5: (lv_index_2_0= RULE_INT )
                    {
                    // InternalGlobalConstantsParser.g:3608:5: (lv_index_2_0= RULE_INT )
                    // InternalGlobalConstantsParser.g:3609:6: lv_index_2_0= RULE_INT
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
                    // InternalGlobalConstantsParser.g:3626:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3626:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    // InternalGlobalConstantsParser.g:3627:5: otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
                      				
                    }
                    // InternalGlobalConstantsParser.g:3631:5: ( (lv_expression_4_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:3632:6: (lv_expression_4_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:3632:6: (lv_expression_4_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:3633:7: lv_expression_4_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_54);
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
    // InternalGlobalConstantsParser.g:3660:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalGlobalConstantsParser.g:3660:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalGlobalConstantsParser.g:3661:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalGlobalConstantsParser.g:3667:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
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
            // InternalGlobalConstantsParser.g:3673:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalGlobalConstantsParser.g:3674:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalGlobalConstantsParser.g:3674:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt66=6;
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
            case RULE_DECIMAL:
                {
                alt66=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt66=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt66=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt66=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt66=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt66=6;
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
                    // InternalGlobalConstantsParser.g:3675:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalGlobalConstantsParser.g:3684:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalGlobalConstantsParser.g:3693:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalGlobalConstantsParser.g:3702:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalGlobalConstantsParser.g:3711:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalGlobalConstantsParser.g:3720:3: this_STStringLiteral_5= ruleSTStringLiteral
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
    // InternalGlobalConstantsParser.g:3732:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:3732:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalGlobalConstantsParser.g:3733:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalGlobalConstantsParser.g:3739:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3745:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalGlobalConstantsParser.g:3746:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalGlobalConstantsParser.g:3746:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==DWORD||LA67_0==LWORD||(LA67_0>=BOOL && LA67_0<=BYTE)||LA67_0==WORD) ) {
                alt67=1;
            }
            else if ( (LA67_0==LREAL||(LA67_0>=UDINT && LA67_0<=ULINT)||LA67_0==USINT||LA67_0==DINT||LA67_0==LINT||(LA67_0>=REAL && LA67_0<=SINT)||LA67_0==UINT||LA67_0==INT) ) {
                alt67=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3747:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalGlobalConstantsParser.g:3758:3: this_STAnyNumType_1= ruleSTAnyNumType
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
    // InternalGlobalConstantsParser.g:3772:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:3772:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalGlobalConstantsParser.g:3773:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalGlobalConstantsParser.g:3779:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3785:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) ) ) )
            // InternalGlobalConstantsParser.g:3786:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) ) )
            {
            // InternalGlobalConstantsParser.g:3786:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) ) )
            // InternalGlobalConstantsParser.g:3787:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) )
            {
            // InternalGlobalConstantsParser.g:3787:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==DWORD||LA68_0==LREAL||LA68_0==LWORD||(LA68_0>=UDINT && LA68_0<=ULINT)||LA68_0==USINT||(LA68_0>=BOOL && LA68_0<=BYTE)||LA68_0==DINT||LA68_0==LINT||(LA68_0>=REAL && LA68_0<=SINT)||LA68_0==UINT||LA68_0==WORD||LA68_0==INT) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3788:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign
                    {
                    // InternalGlobalConstantsParser.g:3788:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalGlobalConstantsParser.g:3789:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalGlobalConstantsParser.g:3789:5: ( ruleSTNumericLiteralType )
                    // InternalGlobalConstantsParser.g:3790:6: ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_57);
                    ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:3809:3: ( (lv_value_2_0= ruleNumeric ) )
            // InternalGlobalConstantsParser.g:3810:4: (lv_value_2_0= ruleNumeric )
            {
            // InternalGlobalConstantsParser.g:3810:4: (lv_value_2_0= ruleNumeric )
            // InternalGlobalConstantsParser.g:3811:5: lv_value_2_0= ruleNumeric
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueNumericParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleNumeric();

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
              						"org.eclipse.fordiac.ide.structuredtextcore.STCore.Numeric");
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
    // $ANTLR end "ruleSTNumericLiteral"


    // $ANTLR start "entryRuleSTDateLiteralType"
    // InternalGlobalConstantsParser.g:3832:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:3832:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalGlobalConstantsParser.g:3833:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalGlobalConstantsParser.g:3839:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3845:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalGlobalConstantsParser.g:3846:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalGlobalConstantsParser.g:3846:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt69=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt69=1;
                }
                break;
            case D:
                {
                alt69=2;
                }
                break;
            case LD:
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
                    // InternalGlobalConstantsParser.g:3847:3: this_STDateType_0= ruleSTDateType
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
                    // InternalGlobalConstantsParser.g:3858:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:3864:3: kw= LD
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
    // InternalGlobalConstantsParser.g:3873:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:3873:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalGlobalConstantsParser.g:3874:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalGlobalConstantsParser.g:3880:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3886:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalGlobalConstantsParser.g:3887:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalGlobalConstantsParser.g:3887:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalGlobalConstantsParser.g:3888:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalGlobalConstantsParser.g:3888:3: ( ( ruleSTDateLiteralType ) )
            // InternalGlobalConstantsParser.g:3889:4: ( ruleSTDateLiteralType )
            {
            // InternalGlobalConstantsParser.g:3889:4: ( ruleSTDateLiteralType )
            // InternalGlobalConstantsParser.g:3890:5: ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_57);
            ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:3908:3: ( (lv_value_2_0= ruleDate ) )
            // InternalGlobalConstantsParser.g:3909:4: (lv_value_2_0= ruleDate )
            {
            // InternalGlobalConstantsParser.g:3909:4: (lv_value_2_0= ruleDate )
            // InternalGlobalConstantsParser.g:3910:5: lv_value_2_0= ruleDate
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
    // InternalGlobalConstantsParser.g:3931:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:3931:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalGlobalConstantsParser.g:3932:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalGlobalConstantsParser.g:3938:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3944:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalGlobalConstantsParser.g:3945:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalGlobalConstantsParser.g:3945:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt70=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt70=1;
                }
                break;
            case T:
                {
                alt70=2;
                }
                break;
            case LT:
                {
                alt70=3;
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
                    // InternalGlobalConstantsParser.g:3946:3: this_STAnyDurationType_0= ruleSTAnyDurationType
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
                    // InternalGlobalConstantsParser.g:3957:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:3963:3: kw= LT
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
    // InternalGlobalConstantsParser.g:3972:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:3972:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalGlobalConstantsParser.g:3973:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalGlobalConstantsParser.g:3979:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3985:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalGlobalConstantsParser.g:3986:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalGlobalConstantsParser.g:3986:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalGlobalConstantsParser.g:3987:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalGlobalConstantsParser.g:3987:3: ( ( ruleSTTimeLiteralType ) )
            // InternalGlobalConstantsParser.g:3988:4: ( ruleSTTimeLiteralType )
            {
            // InternalGlobalConstantsParser.g:3988:4: ( ruleSTTimeLiteralType )
            // InternalGlobalConstantsParser.g:3989:5: ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_57);
            ruleSTTimeLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:4007:3: ( (lv_value_2_0= ruleTime ) )
            // InternalGlobalConstantsParser.g:4008:4: (lv_value_2_0= ruleTime )
            {
            // InternalGlobalConstantsParser.g:4008:4: (lv_value_2_0= ruleTime )
            // InternalGlobalConstantsParser.g:4009:5: lv_value_2_0= ruleTime
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
    // InternalGlobalConstantsParser.g:4030:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4030:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalGlobalConstantsParser.g:4031:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalGlobalConstantsParser.g:4037:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4043:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalGlobalConstantsParser.g:4044:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalGlobalConstantsParser.g:4044:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalGlobalConstantsParser.g:4045:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalGlobalConstantsParser.g:4045:3: ( ( ruleSTTimeOfDayType ) )
            // InternalGlobalConstantsParser.g:4046:4: ( ruleSTTimeOfDayType )
            {
            // InternalGlobalConstantsParser.g:4046:4: ( ruleSTTimeOfDayType )
            // InternalGlobalConstantsParser.g:4047:5: ruleSTTimeOfDayType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_57);
            ruleSTTimeOfDayType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:4065:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalGlobalConstantsParser.g:4066:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalGlobalConstantsParser.g:4066:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalGlobalConstantsParser.g:4067:5: lv_value_2_0= ruleTimeOfDay
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
    // InternalGlobalConstantsParser.g:4088:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4088:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalGlobalConstantsParser.g:4089:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalGlobalConstantsParser.g:4095:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4101:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalGlobalConstantsParser.g:4102:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalGlobalConstantsParser.g:4102:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalGlobalConstantsParser.g:4103:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalGlobalConstantsParser.g:4103:3: ( ( ruleSTDateAndTimeType ) )
            // InternalGlobalConstantsParser.g:4104:4: ( ruleSTDateAndTimeType )
            {
            // InternalGlobalConstantsParser.g:4104:4: ( ruleSTDateAndTimeType )
            // InternalGlobalConstantsParser.g:4105:5: ruleSTDateAndTimeType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_57);
            ruleSTDateAndTimeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:4123:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalGlobalConstantsParser.g:4124:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalGlobalConstantsParser.g:4124:4: (lv_value_2_0= ruleDateAndTime )
            // InternalGlobalConstantsParser.g:4125:5: lv_value_2_0= ruleDateAndTime
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
    // InternalGlobalConstantsParser.g:4146:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4146:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalGlobalConstantsParser.g:4147:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalGlobalConstantsParser.g:4153:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4159:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) )
            // InternalGlobalConstantsParser.g:4160:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            {
            // InternalGlobalConstantsParser.g:4160:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            // InternalGlobalConstantsParser.g:4161:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) )
            {
            // InternalGlobalConstantsParser.g:4161:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==WSTRING||LA71_0==STRING||LA71_0==WCHAR||LA71_0==CHAR) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4162:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalGlobalConstantsParser.g:4162:4: ( ( ruleSTAnyCharsType ) )
                    // InternalGlobalConstantsParser.g:4163:5: ( ruleSTAnyCharsType )
                    {
                    // InternalGlobalConstantsParser.g:4163:5: ( ruleSTAnyCharsType )
                    // InternalGlobalConstantsParser.g:4164:6: ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_57);
                    ruleSTAnyCharsType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:4183:3: ( (lv_value_2_0= RULE_STRING ) )
            // InternalGlobalConstantsParser.g:4184:4: (lv_value_2_0= RULE_STRING )
            {
            // InternalGlobalConstantsParser.g:4184:4: (lv_value_2_0= RULE_STRING )
            // InternalGlobalConstantsParser.g:4185:5: lv_value_2_0= RULE_STRING
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
    // InternalGlobalConstantsParser.g:4205:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalGlobalConstantsParser.g:4205:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalGlobalConstantsParser.g:4206:2: iv_ruleSTAnyType= ruleSTAnyType EOF
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
    // InternalGlobalConstantsParser.g:4212:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_QualifiedName_0 = null;

        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4218:2: ( (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalGlobalConstantsParser.g:4219:2: (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalGlobalConstantsParser.g:4219:2: (this_QualifiedName_0= ruleQualifiedName | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==RULE_ID) ) {
                alt72=1;
            }
            else if ( (LA72_0==LDATE_AND_TIME||LA72_0==DATE_AND_TIME||LA72_0==LTIME_OF_DAY||LA72_0==TIME_OF_DAY||LA72_0==WSTRING||LA72_0==STRING||LA72_0==DWORD||(LA72_0>=LDATE && LA72_0<=LWORD)||(LA72_0>=UDINT && LA72_0<=ULINT)||(LA72_0>=USINT && LA72_0<=WCHAR)||(LA72_0>=BOOL && LA72_0<=BYTE)||(LA72_0>=CHAR && LA72_0<=DINT)||(LA72_0>=LINT && LA72_0<=LTOD)||(LA72_0>=REAL && LA72_0<=SINT)||LA72_0==TIME||LA72_0==UINT||LA72_0==WORD||(LA72_0>=INT && LA72_0<=LDT)||LA72_0==TOD||LA72_0==DT) ) {
                alt72=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }
            switch (alt72) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4220:3: this_QualifiedName_0= ruleQualifiedName
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
                    // InternalGlobalConstantsParser.g:4231:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
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
    // InternalGlobalConstantsParser.g:4245:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalGlobalConstantsParser.g:4245:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalGlobalConstantsParser.g:4246:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
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
    // InternalGlobalConstantsParser.g:4252:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4258:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalGlobalConstantsParser.g:4259:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalGlobalConstantsParser.g:4259:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt73=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt73=1;
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
                alt73=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt73=3;
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
                alt73=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
                {
                alt73=5;
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
                    // InternalGlobalConstantsParser.g:4260:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalGlobalConstantsParser.g:4271:3: this_STAnyNumType_1= ruleSTAnyNumType
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
                    // InternalGlobalConstantsParser.g:4282:3: this_STAnyDurationType_2= ruleSTAnyDurationType
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
                    // InternalGlobalConstantsParser.g:4293:3: this_STAnyDateType_3= ruleSTAnyDateType
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
                    // InternalGlobalConstantsParser.g:4304:3: this_STAnyCharsType_4= ruleSTAnyCharsType
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
    // InternalGlobalConstantsParser.g:4318:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalGlobalConstantsParser.g:4318:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalGlobalConstantsParser.g:4319:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
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
    // InternalGlobalConstantsParser.g:4325:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4331:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalGlobalConstantsParser.g:4332:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalGlobalConstantsParser.g:4332:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt74=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt74=1;
                }
                break;
            case BYTE:
                {
                alt74=2;
                }
                break;
            case WORD:
                {
                alt74=3;
                }
                break;
            case DWORD:
                {
                alt74=4;
                }
                break;
            case LWORD:
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
                    // InternalGlobalConstantsParser.g:4333:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4339:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4345:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4351:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:4357:3: kw= LWORD
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
    // InternalGlobalConstantsParser.g:4366:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalGlobalConstantsParser.g:4366:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalGlobalConstantsParser.g:4367:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
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
    // InternalGlobalConstantsParser.g:4373:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4379:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalGlobalConstantsParser.g:4380:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalGlobalConstantsParser.g:4380:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt75=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt75=1;
                }
                break;
            case INT:
                {
                alt75=2;
                }
                break;
            case DINT:
                {
                alt75=3;
                }
                break;
            case LINT:
                {
                alt75=4;
                }
                break;
            case USINT:
                {
                alt75=5;
                }
                break;
            case UINT:
                {
                alt75=6;
                }
                break;
            case UDINT:
                {
                alt75=7;
                }
                break;
            case ULINT:
                {
                alt75=8;
                }
                break;
            case REAL:
                {
                alt75=9;
                }
                break;
            case LREAL:
                {
                alt75=10;
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
                    // InternalGlobalConstantsParser.g:4381:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4387:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4393:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4399:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:4405:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsParser.g:4411:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsParser.g:4417:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGlobalConstantsParser.g:4423:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGlobalConstantsParser.g:4429:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalGlobalConstantsParser.g:4435:3: kw= LREAL
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
    // InternalGlobalConstantsParser.g:4444:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalGlobalConstantsParser.g:4444:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalGlobalConstantsParser.g:4445:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
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
    // InternalGlobalConstantsParser.g:4451:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4457:2: ( (kw= TIME | kw= LTIME ) )
            // InternalGlobalConstantsParser.g:4458:2: (kw= TIME | kw= LTIME )
            {
            // InternalGlobalConstantsParser.g:4458:2: (kw= TIME | kw= LTIME )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==TIME) ) {
                alt76=1;
            }
            else if ( (LA76_0==LTIME) ) {
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
                    // InternalGlobalConstantsParser.g:4459:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4465:3: kw= LTIME
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
    // InternalGlobalConstantsParser.g:4474:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalGlobalConstantsParser.g:4474:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalGlobalConstantsParser.g:4475:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
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
    // InternalGlobalConstantsParser.g:4481:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4487:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalGlobalConstantsParser.g:4488:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalGlobalConstantsParser.g:4488:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt77=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt77=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt77=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
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
                    // InternalGlobalConstantsParser.g:4489:3: this_STDateType_0= ruleSTDateType
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
                    // InternalGlobalConstantsParser.g:4500:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
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
                    // InternalGlobalConstantsParser.g:4511:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
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
    // InternalGlobalConstantsParser.g:4525:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalGlobalConstantsParser.g:4525:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalGlobalConstantsParser.g:4526:2: iv_ruleSTDateType= ruleSTDateType EOF
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
    // InternalGlobalConstantsParser.g:4532:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4538:2: ( (kw= DATE | kw= LDATE ) )
            // InternalGlobalConstantsParser.g:4539:2: (kw= DATE | kw= LDATE )
            {
            // InternalGlobalConstantsParser.g:4539:2: (kw= DATE | kw= LDATE )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==DATE) ) {
                alt78=1;
            }
            else if ( (LA78_0==LDATE) ) {
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
                    // InternalGlobalConstantsParser.g:4540:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4546:3: kw= LDATE
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
    // InternalGlobalConstantsParser.g:4555:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalGlobalConstantsParser.g:4555:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalGlobalConstantsParser.g:4556:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
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
    // InternalGlobalConstantsParser.g:4562:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4568:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalGlobalConstantsParser.g:4569:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalGlobalConstantsParser.g:4569:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt79=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt79=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt79=2;
                }
                break;
            case TOD:
                {
                alt79=3;
                }
                break;
            case LTOD:
                {
                alt79=4;
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
                    // InternalGlobalConstantsParser.g:4570:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4576:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4582:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4588:3: kw= LTOD
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
    // InternalGlobalConstantsParser.g:4597:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalGlobalConstantsParser.g:4597:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalGlobalConstantsParser.g:4598:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
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
    // InternalGlobalConstantsParser.g:4604:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4610:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalGlobalConstantsParser.g:4611:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalGlobalConstantsParser.g:4611:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt80=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt80=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt80=2;
                }
                break;
            case DT:
                {
                alt80=3;
                }
                break;
            case LDT:
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
                    // InternalGlobalConstantsParser.g:4612:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4618:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4624:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4630:3: kw= LDT
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
    // InternalGlobalConstantsParser.g:4639:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalGlobalConstantsParser.g:4639:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalGlobalConstantsParser.g:4640:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
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
    // InternalGlobalConstantsParser.g:4646:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4652:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalGlobalConstantsParser.g:4653:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalGlobalConstantsParser.g:4653:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt81=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt81=1;
                }
                break;
            case WSTRING:
                {
                alt81=2;
                }
                break;
            case CHAR:
                {
                alt81=3;
                }
                break;
            case WCHAR:
                {
                alt81=4;
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
                    // InternalGlobalConstantsParser.g:4654:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4660:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4666:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4672:3: kw= WCHAR
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
    // InternalGlobalConstantsParser.g:4681:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalGlobalConstantsParser.g:4681:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalGlobalConstantsParser.g:4682:2: iv_ruleQualifiedName= ruleQualifiedName EOF
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
    // InternalGlobalConstantsParser.g:4688:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID ( ( ( ColonColon )=>kw= ColonColon ) this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4694:2: ( (this_ID_0= RULE_ID ( ( ( ColonColon )=>kw= ColonColon ) this_ID_2= RULE_ID )* ) )
            // InternalGlobalConstantsParser.g:4695:2: (this_ID_0= RULE_ID ( ( ( ColonColon )=>kw= ColonColon ) this_ID_2= RULE_ID )* )
            {
            // InternalGlobalConstantsParser.g:4695:2: (this_ID_0= RULE_ID ( ( ( ColonColon )=>kw= ColonColon ) this_ID_2= RULE_ID )* )
            // InternalGlobalConstantsParser.g:4696:3: this_ID_0= RULE_ID ( ( ( ColonColon )=>kw= ColonColon ) this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0());
              		
            }
            // InternalGlobalConstantsParser.g:4703:3: ( ( ( ColonColon )=>kw= ColonColon ) this_ID_2= RULE_ID )*
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( (LA82_0==ColonColon) && (synpred5_InternalGlobalConstantsParser())) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:4704:4: ( ( ColonColon )=>kw= ColonColon ) this_ID_2= RULE_ID
            	    {
            	    // InternalGlobalConstantsParser.g:4704:4: ( ( ColonColon )=>kw= ColonColon )
            	    // InternalGlobalConstantsParser.g:4705:5: ( ColonColon )=>kw= ColonColon
            	    {
            	    kw=(Token)match(input,ColonColon,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					current.merge(kw);
            	      					newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getColonColonKeyword_1_0());
            	      				
            	    }

            	    }

            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_62); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop82;
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
    // InternalGlobalConstantsParser.g:4724:1: entryRuleQualifiedNameWithWildcard returns [String current=null] : iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF ;
    public final String entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildcard = null;


        try {
            // InternalGlobalConstantsParser.g:4724:65: (iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF )
            // InternalGlobalConstantsParser.g:4725:2: iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF
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
    // InternalGlobalConstantsParser.g:4731:1: ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildcard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4737:2: ( (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? ) )
            // InternalGlobalConstantsParser.g:4738:2: (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? )
            {
            // InternalGlobalConstantsParser.g:4738:2: (this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )? )
            // InternalGlobalConstantsParser.g:4739:3: this_QualifiedName_0= ruleQualifiedName (kw= ColonColonAsterisk )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_63);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_QualifiedName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGlobalConstantsParser.g:4749:3: (kw= ColonColonAsterisk )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==ColonColonAsterisk) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4750:4: kw= ColonColonAsterisk
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
    // InternalGlobalConstantsParser.g:4760:1: entryRuleNumeric returns [String current=null] : iv_ruleNumeric= ruleNumeric EOF ;
    public final String entryRuleNumeric() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumeric = null;


        try {
            // InternalGlobalConstantsParser.g:4760:47: (iv_ruleNumeric= ruleNumeric EOF )
            // InternalGlobalConstantsParser.g:4761:2: iv_ruleNumeric= ruleNumeric EOF
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
    // InternalGlobalConstantsParser.g:4767:1: ruleNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) ;
    public final AntlrDatatypeRuleToken ruleNumeric() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_NON_DECIMAL_3=null;
        AntlrDatatypeRuleToken this_Number_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4773:2: ( (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) )
            // InternalGlobalConstantsParser.g:4774:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            {
            // InternalGlobalConstantsParser.g:4774:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            int alt84=4;
            switch ( input.LA(1) ) {
            case TRUE:
                {
                alt84=1;
                }
                break;
            case FALSE:
                {
                alt84=2;
                }
                break;
            case PlusSign:
            case HyphenMinus:
            case RULE_INT:
            case RULE_DECIMAL:
                {
                alt84=3;
                }
                break;
            case RULE_NON_DECIMAL:
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
                    // InternalGlobalConstantsParser.g:4775:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4781:3: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getFALSEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4787:3: this_Number_2= ruleNumber
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
                    // InternalGlobalConstantsParser.g:4798:3: this_NON_DECIMAL_3= RULE_NON_DECIMAL
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
    // InternalGlobalConstantsParser.g:4809:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:4811:2: (iv_ruleNumber= ruleNumber EOF )
            // InternalGlobalConstantsParser.g:4812:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalGlobalConstantsParser.g:4821:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )? ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_DECIMAL_3=null;
        Token this_INT_5=null;
        Token this_DECIMAL_6=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:4828:2: ( ( (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )? ) )
            // InternalGlobalConstantsParser.g:4829:2: ( (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )? )
            {
            // InternalGlobalConstantsParser.g:4829:2: ( (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )? )
            // InternalGlobalConstantsParser.g:4830:3: (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )?
            {
            // InternalGlobalConstantsParser.g:4830:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt85=3;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==PlusSign) ) {
                alt85=1;
            }
            else if ( (LA85_0==HyphenMinus) ) {
                alt85=2;
            }
            switch (alt85) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4831:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4837:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:4843:3: (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL )
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==RULE_INT) ) {
                alt86=1;
            }
            else if ( (LA86_0==RULE_DECIMAL) ) {
                alt86=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4844:4: this_INT_2= RULE_INT
                    {
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4852:4: this_DECIMAL_3= RULE_DECIMAL
                    {
                    this_DECIMAL_3=(Token)match(input,RULE_DECIMAL,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_DECIMAL_3);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_DECIMAL_3, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:4860:3: ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==FullStop) ) {
                int LA88_1 = input.LA(2);

                if ( (LA88_1==RULE_INT) ) {
                    int LA88_3 = input.LA(3);

                    if ( (synpred6_InternalGlobalConstantsParser()) ) {
                        alt88=1;
                    }
                }
                else if ( (LA88_1==RULE_DECIMAL) && (synpred6_InternalGlobalConstantsParser())) {
                    alt88=1;
                }
            }
            switch (alt88) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4861:4: ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL )
                    {
                    // InternalGlobalConstantsParser.g:4861:4: ( ( FullStop )=>kw= FullStop )
                    // InternalGlobalConstantsParser.g:4862:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_2_0());
                      				
                    }

                    }

                    // InternalGlobalConstantsParser.g:4869:4: (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL )
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==RULE_INT) ) {
                        alt87=1;
                    }
                    else if ( (LA87_0==RULE_DECIMAL) ) {
                        alt87=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 87, 0, input);

                        throw nvae;
                    }
                    switch (alt87) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:4870:5: this_INT_5= RULE_INT
                            {
                            this_INT_5=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_INT_5);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_INT_5, grammarAccess.getNumberAccess().getINTTerminalRuleCall_2_1_0());
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalGlobalConstantsParser.g:4878:5: this_DECIMAL_6= RULE_DECIMAL
                            {
                            this_DECIMAL_6=(Token)match(input,RULE_DECIMAL,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_DECIMAL_6);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_DECIMAL_6, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_2_1_1());
                              				
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


    // $ANTLR start "entryRuleTime"
    // InternalGlobalConstantsParser.g:4894:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:4896:2: (iv_ruleTime= ruleTime EOF )
            // InternalGlobalConstantsParser.g:4897:2: iv_ruleTime= ruleTime EOF
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
    // InternalGlobalConstantsParser.g:4906:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_TIME_VALUE_2=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:4913:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) )
            // InternalGlobalConstantsParser.g:4914:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            {
            // InternalGlobalConstantsParser.g:4914:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            // InternalGlobalConstantsParser.g:4915:3: (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE
            {
            // InternalGlobalConstantsParser.g:4915:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt89=3;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==PlusSign) ) {
                alt89=1;
            }
            else if ( (LA89_0==HyphenMinus) ) {
                alt89=2;
            }
            switch (alt89) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4916:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTimeAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4922:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_66); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:4942:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalGlobalConstantsParser.g:4942:44: (iv_ruleDate= ruleDate EOF )
            // InternalGlobalConstantsParser.g:4943:2: iv_ruleDate= ruleDate EOF
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
    // InternalGlobalConstantsParser.g:4949:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4955:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalGlobalConstantsParser.g:4956:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalGlobalConstantsParser.g:4956:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalGlobalConstantsParser.g:4957:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:4992:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalGlobalConstantsParser.g:4992:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalGlobalConstantsParser.g:4993:2: iv_ruleDateAndTime= ruleDateAndTime EOF
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
    // InternalGlobalConstantsParser.g:4999:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) ;
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
            // InternalGlobalConstantsParser.g:5005:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) )
            // InternalGlobalConstantsParser.g:5006:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            {
            // InternalGlobalConstantsParser.g:5006:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            // InternalGlobalConstantsParser.g:5007:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_59); if (state.failed) return current;
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
            kw=(Token)match(input,Colon,FOLLOW_59); if (state.failed) return current;
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
            kw=(Token)match(input,Colon,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_9());
              		
            }
            this_INT_10=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_10);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
              		
            }
            // InternalGlobalConstantsParser.g:5074:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==FullStop) ) {
                int LA90_1 = input.LA(2);

                if ( (LA90_1==RULE_INT) ) {
                    int LA90_3 = input.LA(3);

                    if ( (synpred7_InternalGlobalConstantsParser()) ) {
                        alt90=1;
                    }
                }
            }
            switch (alt90) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5075:4: ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT
                    {
                    // InternalGlobalConstantsParser.g:5075:4: ( ( FullStop )=>kw= FullStop )
                    // InternalGlobalConstantsParser.g:5076:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_59); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:5095:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalGlobalConstantsParser.g:5095:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalGlobalConstantsParser.g:5096:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalGlobalConstantsParser.g:5102:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5108:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) )
            // InternalGlobalConstantsParser.g:5109:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            {
            // InternalGlobalConstantsParser.g:5109:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            // InternalGlobalConstantsParser.g:5110:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_59); if (state.failed) return current;
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
            kw=(Token)match(input,Colon,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalGlobalConstantsParser.g:5141:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==FullStop) ) {
                int LA91_1 = input.LA(2);

                if ( (LA91_1==RULE_INT) ) {
                    int LA91_3 = input.LA(3);

                    if ( (synpred8_InternalGlobalConstantsParser()) ) {
                        alt91=1;
                    }
                }
            }
            switch (alt91) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5142:4: ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT
                    {
                    // InternalGlobalConstantsParser.g:5142:4: ( ( FullStop )=>kw= FullStop )
                    // InternalGlobalConstantsParser.g:5143:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_59); if (state.failed) return current;
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


    // $ANTLR start "ruleSubrangeOperator"
    // InternalGlobalConstantsParser.g:5162:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5168:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalGlobalConstantsParser.g:5169:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalGlobalConstantsParser.g:5169:2: (enumLiteral_0= FullStopFullStop )
            // InternalGlobalConstantsParser.g:5170:3: enumLiteral_0= FullStopFullStop
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
    // InternalGlobalConstantsParser.g:5179:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5185:2: ( (enumLiteral_0= OR ) )
            // InternalGlobalConstantsParser.g:5186:2: (enumLiteral_0= OR )
            {
            // InternalGlobalConstantsParser.g:5186:2: (enumLiteral_0= OR )
            // InternalGlobalConstantsParser.g:5187:3: enumLiteral_0= OR
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
    // InternalGlobalConstantsParser.g:5196:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5202:2: ( (enumLiteral_0= XOR ) )
            // InternalGlobalConstantsParser.g:5203:2: (enumLiteral_0= XOR )
            {
            // InternalGlobalConstantsParser.g:5203:2: (enumLiteral_0= XOR )
            // InternalGlobalConstantsParser.g:5204:3: enumLiteral_0= XOR
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
    // InternalGlobalConstantsParser.g:5213:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5219:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalGlobalConstantsParser.g:5220:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalGlobalConstantsParser.g:5220:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==AND) ) {
                alt92=1;
            }
            else if ( (LA92_0==Ampersand) ) {
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
                    // InternalGlobalConstantsParser.g:5221:3: (enumLiteral_0= AND )
                    {
                    // InternalGlobalConstantsParser.g:5221:3: (enumLiteral_0= AND )
                    // InternalGlobalConstantsParser.g:5222:4: enumLiteral_0= AND
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
                    // InternalGlobalConstantsParser.g:5229:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalGlobalConstantsParser.g:5229:3: (enumLiteral_1= Ampersand )
                    // InternalGlobalConstantsParser.g:5230:4: enumLiteral_1= Ampersand
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
    // InternalGlobalConstantsParser.g:5240:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5246:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalGlobalConstantsParser.g:5247:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalGlobalConstantsParser.g:5247:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==EqualsSign) ) {
                alt93=1;
            }
            else if ( (LA93_0==LessThanSignGreaterThanSign) ) {
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
                    // InternalGlobalConstantsParser.g:5248:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5248:3: (enumLiteral_0= EqualsSign )
                    // InternalGlobalConstantsParser.g:5249:4: enumLiteral_0= EqualsSign
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
                    // InternalGlobalConstantsParser.g:5256:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5256:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalGlobalConstantsParser.g:5257:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalGlobalConstantsParser.g:5267:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5273:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalGlobalConstantsParser.g:5274:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalGlobalConstantsParser.g:5274:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt94=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt94=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt94=2;
                }
                break;
            case GreaterThanSign:
                {
                alt94=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt94=4;
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
                    // InternalGlobalConstantsParser.g:5275:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5275:3: (enumLiteral_0= LessThanSign )
                    // InternalGlobalConstantsParser.g:5276:4: enumLiteral_0= LessThanSign
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
                    // InternalGlobalConstantsParser.g:5283:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5283:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalGlobalConstantsParser.g:5284:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalGlobalConstantsParser.g:5291:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5291:3: (enumLiteral_2= GreaterThanSign )
                    // InternalGlobalConstantsParser.g:5292:4: enumLiteral_2= GreaterThanSign
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
                    // InternalGlobalConstantsParser.g:5299:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5299:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalGlobalConstantsParser.g:5300:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalGlobalConstantsParser.g:5310:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5316:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalGlobalConstantsParser.g:5317:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalGlobalConstantsParser.g:5317:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==PlusSign) ) {
                alt95=1;
            }
            else if ( (LA95_0==HyphenMinus) ) {
                alt95=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5318:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalGlobalConstantsParser.g:5318:3: (enumLiteral_0= PlusSign )
                    // InternalGlobalConstantsParser.g:5319:4: enumLiteral_0= PlusSign
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
                    // InternalGlobalConstantsParser.g:5326:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalGlobalConstantsParser.g:5326:3: (enumLiteral_1= HyphenMinus )
                    // InternalGlobalConstantsParser.g:5327:4: enumLiteral_1= HyphenMinus
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
    // InternalGlobalConstantsParser.g:5337:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5343:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalGlobalConstantsParser.g:5344:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalGlobalConstantsParser.g:5344:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt96=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt96=1;
                }
                break;
            case Solidus:
                {
                alt96=2;
                }
                break;
            case MOD:
                {
                alt96=3;
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
                    // InternalGlobalConstantsParser.g:5345:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalGlobalConstantsParser.g:5345:3: (enumLiteral_0= Asterisk )
                    // InternalGlobalConstantsParser.g:5346:4: enumLiteral_0= Asterisk
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
                    // InternalGlobalConstantsParser.g:5353:3: (enumLiteral_1= Solidus )
                    {
                    // InternalGlobalConstantsParser.g:5353:3: (enumLiteral_1= Solidus )
                    // InternalGlobalConstantsParser.g:5354:4: enumLiteral_1= Solidus
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
                    // InternalGlobalConstantsParser.g:5361:3: (enumLiteral_2= MOD )
                    {
                    // InternalGlobalConstantsParser.g:5361:3: (enumLiteral_2= MOD )
                    // InternalGlobalConstantsParser.g:5362:4: enumLiteral_2= MOD
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
    // InternalGlobalConstantsParser.g:5372:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5378:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalGlobalConstantsParser.g:5379:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalGlobalConstantsParser.g:5379:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalGlobalConstantsParser.g:5380:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalGlobalConstantsParser.g:5389:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5395:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalGlobalConstantsParser.g:5396:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalGlobalConstantsParser.g:5396:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt97=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt97=1;
                }
                break;
            case PlusSign:
                {
                alt97=2;
                }
                break;
            case NOT:
                {
                alt97=3;
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
                    // InternalGlobalConstantsParser.g:5397:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalGlobalConstantsParser.g:5397:3: (enumLiteral_0= HyphenMinus )
                    // InternalGlobalConstantsParser.g:5398:4: enumLiteral_0= HyphenMinus
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
                    // InternalGlobalConstantsParser.g:5405:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalGlobalConstantsParser.g:5405:3: (enumLiteral_1= PlusSign )
                    // InternalGlobalConstantsParser.g:5406:4: enumLiteral_1= PlusSign
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
                    // InternalGlobalConstantsParser.g:5413:3: (enumLiteral_2= NOT )
                    {
                    // InternalGlobalConstantsParser.g:5413:3: (enumLiteral_2= NOT )
                    // InternalGlobalConstantsParser.g:5414:4: enumLiteral_2= NOT
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
    // InternalGlobalConstantsParser.g:5424:1: ruleSTBuiltinFeature returns [Enumerator current=null] : (enumLiteral_0= THIS ) ;
    public final Enumerator ruleSTBuiltinFeature() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5430:2: ( (enumLiteral_0= THIS ) )
            // InternalGlobalConstantsParser.g:5431:2: (enumLiteral_0= THIS )
            {
            // InternalGlobalConstantsParser.g:5431:2: (enumLiteral_0= THIS )
            // InternalGlobalConstantsParser.g:5432:3: enumLiteral_0= THIS
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
    // InternalGlobalConstantsParser.g:5441:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5447:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalGlobalConstantsParser.g:5448:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalGlobalConstantsParser.g:5448:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt98=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt98=1;
                }
                break;
            case D_1:
                {
                alt98=2;
                }
                break;
            case W:
                {
                alt98=3;
                }
                break;
            case B:
                {
                alt98=4;
                }
                break;
            case X:
                {
                alt98=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }

            switch (alt98) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5449:3: (enumLiteral_0= L )
                    {
                    // InternalGlobalConstantsParser.g:5449:3: (enumLiteral_0= L )
                    // InternalGlobalConstantsParser.g:5450:4: enumLiteral_0= L
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
                    // InternalGlobalConstantsParser.g:5457:3: (enumLiteral_1= D_1 )
                    {
                    // InternalGlobalConstantsParser.g:5457:3: (enumLiteral_1= D_1 )
                    // InternalGlobalConstantsParser.g:5458:4: enumLiteral_1= D_1
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
                    // InternalGlobalConstantsParser.g:5465:3: (enumLiteral_2= W )
                    {
                    // InternalGlobalConstantsParser.g:5465:3: (enumLiteral_2= W )
                    // InternalGlobalConstantsParser.g:5466:4: enumLiteral_2= W
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
                    // InternalGlobalConstantsParser.g:5473:3: (enumLiteral_3= B )
                    {
                    // InternalGlobalConstantsParser.g:5473:3: (enumLiteral_3= B )
                    // InternalGlobalConstantsParser.g:5474:4: enumLiteral_3= B
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
                    // InternalGlobalConstantsParser.g:5481:3: (enumLiteral_4= X )
                    {
                    // InternalGlobalConstantsParser.g:5481:3: (enumLiteral_4= X )
                    // InternalGlobalConstantsParser.g:5482:4: enumLiteral_4= X
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
        // InternalGlobalConstantsParser.g:1916:4: ( ( ruleSTStatement ) )
        // InternalGlobalConstantsParser.g:1916:5: ( ruleSTStatement )
        {
        // InternalGlobalConstantsParser.g:1916:5: ( ruleSTStatement )
        // InternalGlobalConstantsParser.g:1917:5: ruleSTStatement
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
        // InternalGlobalConstantsParser.g:2994:4: ( ruleSTAccessExpression )
        // InternalGlobalConstantsParser.g:2994:5: ruleSTAccessExpression
        {
        pushFollow(FOLLOW_2);
        ruleSTAccessExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalGlobalConstantsParser

    // $ANTLR start synpred3_InternalGlobalConstantsParser
    public final void synpred3_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:3301:5: ( ( LeftParenthesis ) )
        // InternalGlobalConstantsParser.g:3301:6: ( LeftParenthesis )
        {
        // InternalGlobalConstantsParser.g:3301:6: ( LeftParenthesis )
        // InternalGlobalConstantsParser.g:3302:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred3_InternalGlobalConstantsParser

    // $ANTLR start synpred4_InternalGlobalConstantsParser
    public final void synpred4_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:3493:5: ( ( LeftParenthesis ) )
        // InternalGlobalConstantsParser.g:3493:6: ( LeftParenthesis )
        {
        // InternalGlobalConstantsParser.g:3493:6: ( LeftParenthesis )
        // InternalGlobalConstantsParser.g:3494:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred4_InternalGlobalConstantsParser

    // $ANTLR start synpred5_InternalGlobalConstantsParser
    public final void synpred5_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:4705:5: ( ColonColon )
        // InternalGlobalConstantsParser.g:4705:6: ColonColon
        {
        match(input,ColonColon,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_InternalGlobalConstantsParser

    // $ANTLR start synpred6_InternalGlobalConstantsParser
    public final void synpred6_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:4862:5: ( FullStop )
        // InternalGlobalConstantsParser.g:4862:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_InternalGlobalConstantsParser

    // $ANTLR start synpred7_InternalGlobalConstantsParser
    public final void synpred7_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:5076:5: ( FullStop )
        // InternalGlobalConstantsParser.g:5076:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_InternalGlobalConstantsParser

    // $ANTLR start synpred8_InternalGlobalConstantsParser
    public final void synpred8_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:5143:5: ( FullStop )
        // InternalGlobalConstantsParser.g:5143:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_InternalGlobalConstantsParser

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
    public final boolean synpred5_InternalGlobalConstantsParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalGlobalConstantsParser_fragment(); // can never throw exception
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
    public final boolean synpred7_InternalGlobalConstantsParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalGlobalConstantsParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalGlobalConstantsParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalGlobalConstantsParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalGlobalConstantsParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalGlobalConstantsParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalGlobalConstantsParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalGlobalConstantsParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalGlobalConstantsParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalGlobalConstantsParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA20 dfa20 = new DFA20(this);
    protected DFA25 dfa25 = new DFA25(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA52 dfa52 = new DFA52(this);
    protected DFA59 dfa59 = new DFA59(this);
    protected DFA63 dfa63 = new DFA63(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\2\10\2\uffff\11\165\1\u00b7\1\uffff\1\165";
    static final String dfa_3s = "\2\u00b8\2\uffff\11\u00a8\1\u00b7\1\uffff\1\u00a8";
    static final String dfa_4s = "\2\uffff\1\1\1\2\12\uffff\1\3\1\uffff";
    static final String dfa_5s = "\20\uffff}>";
    static final String[] dfa_6s = {
            "\1\2\1\uffff\1\2\5\uffff\1\2\2\uffff\1\2\51\uffff\1\2\12\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\4\2\1\uffff\2\2\2\uffff\2\2\1\uffff\2\2\1\uffff\3\2\3\uffff\2\2\1\uffff\2\2\3\uffff\3\2\1\uffff\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\4\2\1\uffff\1\2\1\uffff\1\2\20\uffff\1\2\1\uffff\2\2\2\uffff\1\2\3\uffff\1\1\2\uffff\1\2\1\uffff\1\2\7\uffff\2\2\1\3\2\uffff\3\2\11\uffff\2\2",
            "\1\2\1\uffff\1\2\5\uffff\1\2\2\uffff\1\2\51\uffff\1\2\12\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\4\2\1\uffff\2\2\2\uffff\2\2\1\uffff\2\2\1\uffff\3\2\3\uffff\2\2\1\uffff\2\2\3\uffff\3\2\1\uffff\1\2\1\uffff\1\2\1\uffff\1\6\1\uffff\2\2\1\11\1\2\1\uffff\1\2\1\uffff\1\10\20\uffff\1\13\1\uffff\1\14\1\5\2\uffff\1\7\3\uffff\1\2\2\uffff\1\2\1\uffff\1\2\7\uffff\1\12\1\2\3\uffff\3\2\11\uffff\1\4\1\2",
            "",
            "",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\1\16\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\1\16\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\6\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\1\16\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\1\16\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\1\16\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\1\16\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\1\16\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\6\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\1\16\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\6\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\1\16\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\6\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\17",
            "",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\1\16\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "833:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )";
        }
    }
    static final String dfa_7s = "\12\uffff";
    static final String dfa_8s = "\1\10\11\uffff";
    static final String dfa_9s = "\1\u00b8\11\uffff";
    static final String dfa_10s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
    static final String dfa_11s = "\12\uffff}>";
    static final String[] dfa_12s = {
            "\1\6\1\uffff\1\6\5\uffff\1\6\2\uffff\1\6\26\uffff\1\10\22\uffff\1\6\6\uffff\1\5\1\uffff\1\7\1\uffff\1\6\3\uffff\1\6\1\uffff\1\6\1\uffff\4\6\1\uffff\2\6\2\uffff\2\6\1\4\2\6\1\2\3\6\1\uffff\1\11\1\uffff\2\6\1\uffff\2\6\3\uffff\3\6\1\uffff\1\6\1\uffff\1\6\1\uffff\1\6\1\3\4\6\1\uffff\1\6\1\uffff\1\6\20\uffff\1\6\1\1\2\6\2\uffff\1\6\3\uffff\1\6\2\uffff\1\6\1\uffff\1\6\7\uffff\2\6\3\uffff\3\6\11\uffff\2\6",
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
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final short[] dfa_10 = DFA.unpackEncodedString(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[][] dfa_12 = unpackEncodedStringArray(dfa_12s);

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = dfa_7;
            this.eof = dfa_7;
            this.min = dfa_8;
            this.max = dfa_9;
            this.accept = dfa_10;
            this.special = dfa_11;
            this.transition = dfa_12;
        }
        public String getDescription() {
            return "1208:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | this_STAssignment_5= ruleSTAssignment | ( () otherlv_7= RETURN ) | ( () otherlv_9= CONTINUE ) | ( () otherlv_11= EXIT ) )";
        }
    }
    static final String dfa_13s = "\100\uffff";
    static final String dfa_14s = "\1\1\77\uffff";
    static final String dfa_15s = "\1\10\2\uffff\63\0\12\uffff";
    static final String dfa_16s = "\1\u00b8\2\uffff\63\0\12\uffff";
    static final String dfa_17s = "\1\uffff\1\2\65\uffff\11\1";
    static final String dfa_18s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\12\uffff}>";
    static final String[] dfa_19s = {
            "\1\56\1\uffff\1\55\5\uffff\1\52\2\uffff\1\51\26\uffff\1\75\1\1\21\uffff\1\61\6\uffff\1\73\1\uffff\1\74\1\uffff\1\60\3\uffff\1\21\1\uffff\1\36\1\uffff\1\45\1\34\1\47\1\22\1\uffff\1\31\1\32\2\uffff\1\27\1\63\1\72\1\16\1\17\1\70\1\62\1\44\1\25\1\1\1\76\1\uffff\1\26\1\54\1\uffff\1\33\1\23\3\uffff\1\15\1\46\1\35\1\uffff\1\30\1\uffff\1\20\1\uffff\1\6\1\71\1\24\1\57\1\11\1\65\1\uffff\1\53\1\uffff\1\10\20\uffff\1\13\1\67\1\14\1\5\2\uffff\1\7\3\uffff\1\3\2\uffff\1\37\1\uffff\1\40\3\uffff\1\77\3\uffff\1\12\1\50\3\uffff\1\43\1\41\1\42\11\uffff\1\4\1\64",
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

    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = dfa_13;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "()* loopback of 1915:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
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
                        if ( (LA37_0==EOF||LA37_0==END_CASE||LA37_0==ELSE) ) {s = 1;}

                        else if ( (LA37_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA37_0==RULE_ID) ) {s = 4;}

                        else if ( (LA37_0==LT) ) {s = 5;}

                        else if ( (LA37_0==AND) ) {s = 6;}

                        else if ( (LA37_0==OR) ) {s = 7;}

                        else if ( (LA37_0==XOR) ) {s = 8;}

                        else if ( (LA37_0==MOD) ) {s = 9;}

                        else if ( (LA37_0==D) ) {s = 10;}

                        else if ( (LA37_0==DT) ) {s = 11;}

                        else if ( (LA37_0==LD) ) {s = 12;}

                        else if ( (LA37_0==THIS) ) {s = 13;}

                        else if ( (LA37_0==BOOL) ) {s = 14;}

                        else if ( (LA37_0==BYTE) ) {s = 15;}

                        else if ( (LA37_0==WORD) ) {s = 16;}

                        else if ( (LA37_0==DWORD) ) {s = 17;}

                        else if ( (LA37_0==LWORD) ) {s = 18;}

                        else if ( (LA37_0==SINT) ) {s = 19;}

                        else if ( (LA37_0==INT) ) {s = 20;}

                        else if ( (LA37_0==DINT) ) {s = 21;}

                        else if ( (LA37_0==LINT) ) {s = 22;}

                        else if ( (LA37_0==USINT) ) {s = 23;}

                        else if ( (LA37_0==UINT) ) {s = 24;}

                        else if ( (LA37_0==UDINT) ) {s = 25;}

                        else if ( (LA37_0==ULINT) ) {s = 26;}

                        else if ( (LA37_0==REAL) ) {s = 27;}

                        else if ( (LA37_0==LREAL) ) {s = 28;}

                        else if ( (LA37_0==TRUE) ) {s = 29;}

                        else if ( (LA37_0==FALSE) ) {s = 30;}

                        else if ( (LA37_0==PlusSign) ) {s = 31;}

                        else if ( (LA37_0==HyphenMinus) ) {s = 32;}

                        else if ( (LA37_0==RULE_INT) ) {s = 33;}

                        else if ( (LA37_0==RULE_DECIMAL) ) {s = 34;}

                        else if ( (LA37_0==RULE_NON_DECIMAL) ) {s = 35;}

                        else if ( (LA37_0==DATE) ) {s = 36;}

                        else if ( (LA37_0==LDATE) ) {s = 37;}

                        else if ( (LA37_0==TIME) ) {s = 38;}

                        else if ( (LA37_0==LTIME) ) {s = 39;}

                        else if ( (LA37_0==T) ) {s = 40;}

                        else if ( (LA37_0==TIME_OF_DAY) ) {s = 41;}

                        else if ( (LA37_0==LTIME_OF_DAY) ) {s = 42;}

                        else if ( (LA37_0==TOD) ) {s = 43;}

                        else if ( (LA37_0==LTOD) ) {s = 44;}

                        else if ( (LA37_0==DATE_AND_TIME) ) {s = 45;}

                        else if ( (LA37_0==LDATE_AND_TIME) ) {s = 46;}

                        else if ( (LA37_0==LDT) ) {s = 47;}

                        else if ( (LA37_0==STRING) ) {s = 48;}

                        else if ( (LA37_0==WSTRING) ) {s = 49;}

                        else if ( (LA37_0==CHAR) ) {s = 50;}

                        else if ( (LA37_0==WCHAR) ) {s = 51;}

                        else if ( (LA37_0==RULE_STRING) ) {s = 52;}

                        else if ( (LA37_0==NOT) ) {s = 53;}

                        else if ( (LA37_0==IF) && (synpred1_InternalGlobalConstantsParser())) {s = 55;}

                        else if ( (LA37_0==CASE) && (synpred1_InternalGlobalConstantsParser())) {s = 56;}

                        else if ( (LA37_0==FOR) && (synpred1_InternalGlobalConstantsParser())) {s = 57;}

                        else if ( (LA37_0==WHILE) && (synpred1_InternalGlobalConstantsParser())) {s = 58;}

                        else if ( (LA37_0==REPEAT) && (synpred1_InternalGlobalConstantsParser())) {s = 59;}

                        else if ( (LA37_0==RETURN) && (synpred1_InternalGlobalConstantsParser())) {s = 60;}

                        else if ( (LA37_0==CONTINUE) && (synpred1_InternalGlobalConstantsParser())) {s = 61;}

                        else if ( (LA37_0==EXIT) && (synpred1_InternalGlobalConstantsParser())) {s = 62;}

                        else if ( (LA37_0==Semicolon) && (synpred1_InternalGlobalConstantsParser())) {s = 63;}

                         
                        input.seek(index37_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA37_3 = input.LA(1);

                         
                        int index37_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA37_4 = input.LA(1);

                         
                        int index37_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA37_5 = input.LA(1);

                         
                        int index37_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA37_6 = input.LA(1);

                         
                        int index37_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA37_7 = input.LA(1);

                         
                        int index37_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA37_8 = input.LA(1);

                         
                        int index37_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA37_9 = input.LA(1);

                         
                        int index37_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA37_10 = input.LA(1);

                         
                        int index37_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA37_11 = input.LA(1);

                         
                        int index37_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA37_12 = input.LA(1);

                         
                        int index37_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA37_13 = input.LA(1);

                         
                        int index37_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA37_14 = input.LA(1);

                         
                        int index37_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA37_15 = input.LA(1);

                         
                        int index37_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA37_16 = input.LA(1);

                         
                        int index37_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA37_17 = input.LA(1);

                         
                        int index37_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA37_18 = input.LA(1);

                         
                        int index37_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA37_19 = input.LA(1);

                         
                        int index37_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA37_20 = input.LA(1);

                         
                        int index37_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA37_21 = input.LA(1);

                         
                        int index37_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA37_22 = input.LA(1);

                         
                        int index37_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA37_23 = input.LA(1);

                         
                        int index37_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA37_24 = input.LA(1);

                         
                        int index37_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA37_25 = input.LA(1);

                         
                        int index37_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA37_26 = input.LA(1);

                         
                        int index37_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA37_27 = input.LA(1);

                         
                        int index37_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA37_28 = input.LA(1);

                         
                        int index37_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA37_29 = input.LA(1);

                         
                        int index37_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA37_30 = input.LA(1);

                         
                        int index37_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA37_31 = input.LA(1);

                         
                        int index37_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA37_32 = input.LA(1);

                         
                        int index37_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA37_33 = input.LA(1);

                         
                        int index37_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA37_34 = input.LA(1);

                         
                        int index37_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA37_35 = input.LA(1);

                         
                        int index37_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA37_36 = input.LA(1);

                         
                        int index37_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA37_37 = input.LA(1);

                         
                        int index37_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA37_38 = input.LA(1);

                         
                        int index37_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA37_39 = input.LA(1);

                         
                        int index37_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA37_40 = input.LA(1);

                         
                        int index37_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA37_41 = input.LA(1);

                         
                        int index37_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA37_42 = input.LA(1);

                         
                        int index37_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA37_43 = input.LA(1);

                         
                        int index37_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA37_44 = input.LA(1);

                         
                        int index37_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA37_45 = input.LA(1);

                         
                        int index37_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA37_46 = input.LA(1);

                         
                        int index37_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_46);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA37_47 = input.LA(1);

                         
                        int index37_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_47);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA37_48 = input.LA(1);

                         
                        int index37_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_48);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA37_49 = input.LA(1);

                         
                        int index37_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_49);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA37_50 = input.LA(1);

                         
                        int index37_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_50);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA37_51 = input.LA(1);

                         
                        int index37_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_51);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA37_52 = input.LA(1);

                         
                        int index37_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_52);
                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA37_53 = input.LA(1);

                         
                        int index37_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index37_53);
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
    static final String dfa_20s = "\64\uffff";
    static final String dfa_21s = "\1\10\34\uffff\2\0\25\uffff";
    static final String dfa_22s = "\1\u00b8\34\uffff\2\0\25\uffff";
    static final String dfa_23s = "\1\uffff\34\1\2\uffff\24\1\1\2";
    static final String dfa_24s = "\1\0\34\uffff\1\1\1\2\25\uffff}>";
    static final String[] dfa_25s = {
            "\1\54\1\uffff\1\53\5\uffff\1\50\2\uffff\1\47\51\uffff\1\57\12\uffff\1\56\3\uffff\1\17\1\uffff\1\34\1\uffff\1\43\1\32\1\45\1\20\1\uffff\1\27\1\30\2\uffff\1\25\1\61\1\uffff\1\14\1\15\1\uffff\1\60\1\42\1\23\3\uffff\1\24\1\52\1\uffff\1\31\1\21\3\uffff\1\13\1\44\1\33\1\uffff\1\26\1\uffff\1\16\1\uffff\1\4\1\uffff\1\22\1\55\1\7\1\63\1\uffff\1\51\1\uffff\1\6\20\uffff\1\11\1\uffff\1\12\1\3\2\uffff\1\5\3\uffff\1\1\2\uffff\1\35\1\uffff\1\36\7\uffff\1\10\1\46\3\uffff\1\41\1\37\1\40\11\uffff\1\2\1\62",
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
            ""
    };

    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final char[] dfa_21 = DFA.unpackEncodedStringToUnsignedChars(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[][] dfa_25 = unpackEncodedStringArray(dfa_25s);

    class DFA52 extends DFA {

        public DFA52(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = dfa_20;
            this.eof = dfa_20;
            this.min = dfa_21;
            this.max = dfa_22;
            this.accept = dfa_23;
            this.special = dfa_24;
            this.transition = dfa_25;
        }
        public String getDescription() {
            return "2992:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )";
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
                        if ( (LA52_0==LeftParenthesis) && (synpred2_InternalGlobalConstantsParser())) {s = 1;}

                        else if ( (LA52_0==RULE_ID) && (synpred2_InternalGlobalConstantsParser())) {s = 2;}

                        else if ( (LA52_0==LT) && (synpred2_InternalGlobalConstantsParser())) {s = 3;}

                        else if ( (LA52_0==AND) && (synpred2_InternalGlobalConstantsParser())) {s = 4;}

                        else if ( (LA52_0==OR) && (synpred2_InternalGlobalConstantsParser())) {s = 5;}

                        else if ( (LA52_0==XOR) && (synpred2_InternalGlobalConstantsParser())) {s = 6;}

                        else if ( (LA52_0==MOD) && (synpred2_InternalGlobalConstantsParser())) {s = 7;}

                        else if ( (LA52_0==D) && (synpred2_InternalGlobalConstantsParser())) {s = 8;}

                        else if ( (LA52_0==DT) && (synpred2_InternalGlobalConstantsParser())) {s = 9;}

                        else if ( (LA52_0==LD) && (synpred2_InternalGlobalConstantsParser())) {s = 10;}

                        else if ( (LA52_0==THIS) && (synpred2_InternalGlobalConstantsParser())) {s = 11;}

                        else if ( (LA52_0==BOOL) && (synpred2_InternalGlobalConstantsParser())) {s = 12;}

                        else if ( (LA52_0==BYTE) && (synpred2_InternalGlobalConstantsParser())) {s = 13;}

                        else if ( (LA52_0==WORD) && (synpred2_InternalGlobalConstantsParser())) {s = 14;}

                        else if ( (LA52_0==DWORD) && (synpred2_InternalGlobalConstantsParser())) {s = 15;}

                        else if ( (LA52_0==LWORD) && (synpred2_InternalGlobalConstantsParser())) {s = 16;}

                        else if ( (LA52_0==SINT) && (synpred2_InternalGlobalConstantsParser())) {s = 17;}

                        else if ( (LA52_0==INT) && (synpred2_InternalGlobalConstantsParser())) {s = 18;}

                        else if ( (LA52_0==DINT) && (synpred2_InternalGlobalConstantsParser())) {s = 19;}

                        else if ( (LA52_0==LINT) && (synpred2_InternalGlobalConstantsParser())) {s = 20;}

                        else if ( (LA52_0==USINT) && (synpred2_InternalGlobalConstantsParser())) {s = 21;}

                        else if ( (LA52_0==UINT) && (synpred2_InternalGlobalConstantsParser())) {s = 22;}

                        else if ( (LA52_0==UDINT) && (synpred2_InternalGlobalConstantsParser())) {s = 23;}

                        else if ( (LA52_0==ULINT) && (synpred2_InternalGlobalConstantsParser())) {s = 24;}

                        else if ( (LA52_0==REAL) && (synpred2_InternalGlobalConstantsParser())) {s = 25;}

                        else if ( (LA52_0==LREAL) && (synpred2_InternalGlobalConstantsParser())) {s = 26;}

                        else if ( (LA52_0==TRUE) && (synpred2_InternalGlobalConstantsParser())) {s = 27;}

                        else if ( (LA52_0==FALSE) && (synpred2_InternalGlobalConstantsParser())) {s = 28;}

                        else if ( (LA52_0==PlusSign) ) {s = 29;}

                        else if ( (LA52_0==HyphenMinus) ) {s = 30;}

                        else if ( (LA52_0==RULE_INT) && (synpred2_InternalGlobalConstantsParser())) {s = 31;}

                        else if ( (LA52_0==RULE_DECIMAL) && (synpred2_InternalGlobalConstantsParser())) {s = 32;}

                        else if ( (LA52_0==RULE_NON_DECIMAL) && (synpred2_InternalGlobalConstantsParser())) {s = 33;}

                        else if ( (LA52_0==DATE) && (synpred2_InternalGlobalConstantsParser())) {s = 34;}

                        else if ( (LA52_0==LDATE) && (synpred2_InternalGlobalConstantsParser())) {s = 35;}

                        else if ( (LA52_0==TIME) && (synpred2_InternalGlobalConstantsParser())) {s = 36;}

                        else if ( (LA52_0==LTIME) && (synpred2_InternalGlobalConstantsParser())) {s = 37;}

                        else if ( (LA52_0==T) && (synpred2_InternalGlobalConstantsParser())) {s = 38;}

                        else if ( (LA52_0==TIME_OF_DAY) && (synpred2_InternalGlobalConstantsParser())) {s = 39;}

                        else if ( (LA52_0==LTIME_OF_DAY) && (synpred2_InternalGlobalConstantsParser())) {s = 40;}

                        else if ( (LA52_0==TOD) && (synpred2_InternalGlobalConstantsParser())) {s = 41;}

                        else if ( (LA52_0==LTOD) && (synpred2_InternalGlobalConstantsParser())) {s = 42;}

                        else if ( (LA52_0==DATE_AND_TIME) && (synpred2_InternalGlobalConstantsParser())) {s = 43;}

                        else if ( (LA52_0==LDATE_AND_TIME) && (synpred2_InternalGlobalConstantsParser())) {s = 44;}

                        else if ( (LA52_0==LDT) && (synpred2_InternalGlobalConstantsParser())) {s = 45;}

                        else if ( (LA52_0==STRING) && (synpred2_InternalGlobalConstantsParser())) {s = 46;}

                        else if ( (LA52_0==WSTRING) && (synpred2_InternalGlobalConstantsParser())) {s = 47;}

                        else if ( (LA52_0==CHAR) && (synpred2_InternalGlobalConstantsParser())) {s = 48;}

                        else if ( (LA52_0==WCHAR) && (synpred2_InternalGlobalConstantsParser())) {s = 49;}

                        else if ( (LA52_0==RULE_STRING) && (synpred2_InternalGlobalConstantsParser())) {s = 50;}

                        else if ( (LA52_0==NOT) ) {s = 51;}

                         
                        input.seek(index52_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA52_29 = input.LA(1);

                         
                        int index52_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 50;}

                        else if ( (true) ) {s = 51;}

                         
                        input.seek(index52_29);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA52_30 = input.LA(1);

                         
                        int index52_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 50;}

                        else if ( (true) ) {s = 51;}

                         
                        input.seek(index52_30);
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
    static final String dfa_26s = "\43\uffff";
    static final String dfa_27s = "\1\2\42\uffff";
    static final String dfa_28s = "\1\26\1\0\41\uffff";
    static final String dfa_29s = "\1\u00a9\1\0\41\uffff";
    static final String dfa_30s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_31s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_32s = {
            "\1\2\125\uffff\1\2\10\uffff\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\uffff\3\2\1\uffff\1\2\1\uffff\2\2\4\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\1\14\2\2\uffff\2\2",
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

    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final char[] dfa_28 = DFA.unpackEncodedStringToUnsignedChars(dfa_28s);
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[][] dfa_32 = unpackEncodedStringArray(dfa_32s);

    class DFA59 extends DFA {

        public DFA59(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 59;
            this.eot = dfa_26;
            this.eof = dfa_27;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_31;
            this.transition = dfa_32;
        }
        public String getDescription() {
            return "3299:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?";
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
                        if ( (synpred3_InternalGlobalConstantsParser()) ) {s = 34;}

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

    class DFA63 extends DFA {

        public DFA63(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 63;
            this.eot = dfa_26;
            this.eof = dfa_27;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_31;
            this.transition = dfa_32;
        }
        public String getDescription() {
            return "3491:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA63_1 = input.LA(1);

                         
                        int index63_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalGlobalConstantsParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index63_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 63, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000040000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0040020000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0040000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200001000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x2000000000090500L,0x118A4363B66F1500L,0x0080000000008000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5500L,0x018038C052268000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020020000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010400000080L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000400000080L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5500L,0x018039C052268000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000000L,0x4220000000000000L,0x0080004000268000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0xA000040000090500L,0x57EAE36FFE6F7550L,0x018038C452278000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x8000000000000000L,0x0000000400002000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x2000040000090502L,0x57EAE36BFE6F5550L,0x018038C452278000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x2000080000090500L,0x57AAE367B66F5500L,0x018038C052268000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000220000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x2020040000090500L,0x57EAE36BFE6F5550L,0x018038C452278000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x2000040400090500L,0x57EAE36BFE6F5550L,0x018038C452278000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x2000040000090500L,0x57EAE36BFEEF5550L,0x018038C452278000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0020000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000200L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000002800000900L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000050000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0200000000000000L,0x0000000108000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000010080000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0xC220000000000000L,0x008010400226800FL});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x2000000000090500L,0x57AAE363B66F5500L,0x018038C056268000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000100002000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x008A8322326A5000L,0x0000380050000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800050000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x0010000000000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});

}