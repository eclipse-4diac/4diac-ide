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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "END_FUNCTION_BLOCK", "END_CONFIGURATION", "END_TRANSITION", "FUNCTION_BLOCK", "LDATE_AND_TIME", "CONFIGURATION", "DATE_AND_TIME", "END_INTERFACE", "END_NAMESPACE", "END_FUNCTION", "END_RESOURCE", "INITIAL_STEP", "LTIME_OF_DAY", "VAR_EXTERNAL", "END_PROGRAM", "TIME_OF_DAY", "END_ACTION", "END_METHOD", "END_REPEAT", "END_STRUCT", "IMPLEMENTS", "NON_RETAIN", "READ_WRITE", "TRANSITION", "VAR_ACCESS", "VAR_CONFIG", "VAR_GLOBAL", "VAR_IN_OUT", "VAR_OUTPUT", "END_CLASS", "END_WHILE", "INTERFACE", "NAMESPACE", "PROTECTED", "READ_ONLY", "VAR_INPUT", "ABSTRACT", "CONSTANT", "CONTINUE", "END_CASE", "END_STEP", "END_TYPE", "FUNCTION", "INTERNAL", "INTERVAL", "OVERRIDE", "PRIORITY", "RESOURCE", "VAR_TEMP", "END_FOR", "END_VAR", "EXTENDS", "INTERAL", "OVERLAP", "PRIVATE", "PROGRAM", "WSTRING", "ACTION", "END_IF", "METHOD", "PUBLIC", "REF_TO", "REPEAT", "RETAIN", "RETURN", "SINGLE", "STRING", "STRUCT", "ARRAY", "CLASS", "DWORD", "ELSIF", "FALSE", "FINAL", "LDATE", "LREAL", "LTIME", "LWORD", "SUPER", "UDINT", "ULINT", "UNTIL", "USING", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "FROM", "LINT", "LTOD", "NULL", "REAL", "SINT", "STEP", "TASK", "THEN", "THIS", "TIME", "TRUE", "TYPE", "UINT", "WITH", "WORD", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "REF", "TOD", "VAR", "XOR", "B", "D_1", "L", "W", "X", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "OF", "ON", "OR", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "D", "T", "LeftSquareBracket", "RightSquareBracket", "RULE_HEX_DIGIT", "RULE_NON_DECIMAL", "RULE_INT", "RULE_DECIMAL", "RULE_TIME_PART", "RULE_TIME_VALUE", "RULE_TIME_DAYS", "RULE_TIME_HOURS", "RULE_TIME_MINUTES", "RULE_TIME_SECONDS", "RULE_TIME_MILLIS", "RULE_TIME_MICROS", "RULE_TIME_NANOS", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=81;
    public static final int LessThanSignGreaterThanSign=133;
    public static final int FUNCTION_BLOCK=7;
    public static final int EqualsSignGreaterThanSign=134;
    public static final int VAR=122;
    public static final int END_IF=62;
    public static final int ULINT=84;
    public static final int END_CASE=43;
    public static final int LessThanSign=159;
    public static final int RULE_TIME_HOURS=173;
    public static final int LeftParenthesis=149;
    public static final int INTERFACE=35;
    public static final int VAR_CONFIG=29;
    public static final int BYTE=91;
    public static final int INTERNAL=47;
    public static final int ELSE=96;
    public static final int REF_TO=65;
    public static final int TYPE=110;
    public static final int IF=140;
    public static final int LINT=99;
    public static final int GreaterThanSign=161;
    public static final int WORD=113;
    public static final int DATE_AND_TIME=10;
    public static final int RULE_ID=179;
    public static final int CONFIGURATION=9;
    public static final int TOD=121;
    public static final int DINT=95;
    public static final int FUNCTION=46;
    public static final int UDINT=83;
    public static final int RULE_TIME_NANOS=178;
    public static final int CASE=92;
    public static final int GreaterThanSignEqualsSign=135;
    public static final int AT=136;
    public static final int PlusSign=152;
    public static final int RULE_INT=168;
    public static final int END_FOR=53;
    public static final int RULE_TIME_DAYS=172;
    public static final int RULE_ML_COMMENT=181;
    public static final int PUBLIC=64;
    public static final int THEN=106;
    public static final int XOR=123;
    public static final int LeftSquareBracket=164;
    public static final int PROGRAM=59;
    public static final int EXIT=97;
    public static final int TIME_OF_DAY=19;
    public static final int B=124;
    public static final int LDATE_AND_TIME=8;
    public static final int REPEAT=66;
    public static final int D=162;
    public static final int CHAR=93;
    public static final int L=126;
    public static final int LTIME=80;
    public static final int Comma=153;
    public static final int HyphenMinus=154;
    public static final int T=163;
    public static final int W=127;
    public static final int BY=137;
    public static final int X=128;
    public static final int ELSIF=75;
    public static final int END_REPEAT=22;
    public static final int LessThanSignEqualsSign=132;
    public static final int Solidus=156;
    public static final int PROTECTED=37;
    public static final int VAR_INPUT=39;
    public static final int RESOURCE=51;
    public static final int INTERVAL=48;
    public static final int FullStop=155;
    public static final int RULE_TIME_SECONDS=175;
    public static final int VAR_TEMP=52;
    public static final int INTERAL=56;
    public static final int CONSTANT=41;
    public static final int RULE_TIME_VALUE=171;
    public static final int PRIVATE=58;
    public static final int TRANSITION=27;
    public static final int CONTINUE=42;
    public static final int Semicolon=158;
    public static final int REF=120;
    public static final int LD=141;
    public static final int VAR_OUTPUT=32;
    public static final int STRING=70;
    public static final int RULE_HEX_DIGIT=166;
    public static final int TO=146;
    public static final int END_TYPE=45;
    public static final int UINT=111;
    public static final int INITIAL_STEP=15;
    public static final int LTOD=100;
    public static final int NAMESPACE=36;
    public static final int EXTENDS=55;
    public static final int SINGLE=69;
    public static final int ARRAY=72;
    public static final int LT=142;
    public static final int PRIORITY=50;
    public static final int CLASS=73;
    public static final int FROM=98;
    public static final int DO=138;
    public static final int WSTRING=60;
    public static final int READ_WRITE=26;
    public static final int END_CONFIGURATION=5;
    public static final int DT=139;
    public static final int END_VAR=54;
    public static final int END_STEP=44;
    public static final int END_STRUCT=23;
    public static final int RULE_TIME_PART=170;
    public static final int FullStopFullStop=130;
    public static final int OVERLAP=57;
    public static final int Ampersand=148;
    public static final int END_NAMESPACE=12;
    public static final int END_ACTION=20;
    public static final int RightSquareBracket=165;
    public static final int TASK=105;
    public static final int NULL=101;
    public static final int USINT=87;
    public static final int DWORD=74;
    public static final int FOR=115;
    public static final int RightParenthesis=150;
    public static final int TRUE=109;
    public static final int FINAL=77;
    public static final int ColonEqualsSign=131;
    public static final int END_FUNCTION=13;
    public static final int END_WHILE=34;
    public static final int USING=86;
    public static final int RULE_DECIMAL=169;
    public static final int DATE=94;
    public static final int NOT=119;
    public static final int LDATE=78;
    public static final int AND=114;
    public static final int NumberSign=147;
    public static final int REAL=102;
    public static final int AsteriskAsterisk=129;
    public static final int METHOD=63;
    public static final int THIS=107;
    public static final int SINT=103;
    public static final int NON_RETAIN=25;
    public static final int STRUCT=71;
    public static final int LTIME_OF_DAY=16;
    public static final int END_TRANSITION=6;
    public static final int LREAL=79;
    public static final int VAR_GLOBAL=30;
    public static final int WCHAR=88;
    public static final int END_FUNCTION_BLOCK=4;
    public static final int VAR_EXTERNAL=17;
    public static final int RULE_STRING=180;
    public static final int VAR_ACCESS=28;
    public static final int ABSTRACT=40;
    public static final int READ_ONLY=38;
    public static final int INT=116;
    public static final int RULE_SL_COMMENT=182;
    public static final int RETURN=68;
    public static final int EqualsSign=160;
    public static final int OF=143;
    public static final int END_METHOD=21;
    public static final int END_RESOURCE=14;
    public static final int Colon=157;
    public static final int EOF=-1;
    public static final int LDT=117;
    public static final int Asterisk=151;
    public static final int RULE_TIME_MILLIS=176;
    public static final int ON=144;
    public static final int SUPER=82;
    public static final int MOD=118;
    public static final int OR=145;
    public static final int RETAIN=67;
    public static final int RULE_WS=183;
    public static final int VAR_IN_OUT=31;
    public static final int END_INTERFACE=11;
    public static final int IMPLEMENTS=24;
    public static final int STEP=104;
    public static final int TIME=108;
    public static final int RULE_ANY_OTHER=184;
    public static final int UNTIL=85;
    public static final int WITH=112;
    public static final int RULE_TIME_MINUTES=174;
    public static final int END_CLASS=33;
    public static final int OVERRIDE=49;
    public static final int ACTION=61;
    public static final int BOOL=90;
    public static final int D_1=125;
    public static final int FALSE=76;
    public static final int WHILE=89;
    public static final int RULE_TIME_MICROS=177;
    public static final int END_PROGRAM=18;
    public static final int RULE_NON_DECIMAL=167;

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
    // InternalGlobalConstantsParser.g:76:1: ruleSTGlobalConstsSource returns [EObject current=null] : ( () ( (lv_elements_1_0= ruleSTVarGlobalDeclarationBlock ) )* ) ;
    public final EObject ruleSTGlobalConstsSource() throws RecognitionException {
        EObject current = null;

        EObject lv_elements_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:82:2: ( ( () ( (lv_elements_1_0= ruleSTVarGlobalDeclarationBlock ) )* ) )
            // InternalGlobalConstantsParser.g:83:2: ( () ( (lv_elements_1_0= ruleSTVarGlobalDeclarationBlock ) )* )
            {
            // InternalGlobalConstantsParser.g:83:2: ( () ( (lv_elements_1_0= ruleSTVarGlobalDeclarationBlock ) )* )
            // InternalGlobalConstantsParser.g:84:3: () ( (lv_elements_1_0= ruleSTVarGlobalDeclarationBlock ) )*
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

            // InternalGlobalConstantsParser.g:91:3: ( (lv_elements_1_0= ruleSTVarGlobalDeclarationBlock ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==VAR_GLOBAL) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:92:4: (lv_elements_1_0= ruleSTVarGlobalDeclarationBlock )
            	    {
            	    // InternalGlobalConstantsParser.g:92:4: (lv_elements_1_0= ruleSTVarGlobalDeclarationBlock )
            	    // InternalGlobalConstantsParser.g:93:5: lv_elements_1_0= ruleSTVarGlobalDeclarationBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTGlobalConstsSourceAccess().getElementsSTVarGlobalDeclarationBlockParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_elements_1_0=ruleSTVarGlobalDeclarationBlock();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSTGlobalConstsSourceRule());
            	      					}
            	      					add(
            	      						current,
            	      						"elements",
            	      						lv_elements_1_0,
            	      						"org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstants.STVarGlobalDeclarationBlock");
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
    // $ANTLR end "ruleSTGlobalConstsSource"


    // $ANTLR start "entryRuleSTVarGlobalDeclarationBlock"
    // InternalGlobalConstantsParser.g:114:1: entryRuleSTVarGlobalDeclarationBlock returns [EObject current=null] : iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF ;
    public final EObject entryRuleSTVarGlobalDeclarationBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarGlobalDeclarationBlock = null;


        try {
            // InternalGlobalConstantsParser.g:114:68: (iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF )
            // InternalGlobalConstantsParser.g:115:2: iv_ruleSTVarGlobalDeclarationBlock= ruleSTVarGlobalDeclarationBlock EOF
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
    // InternalGlobalConstantsParser.g:121:1: ruleSTVarGlobalDeclarationBlock returns [EObject current=null] : ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) ;
    public final EObject ruleSTVarGlobalDeclarationBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constant_2_0=null;
        Token otherlv_4=null;
        EObject lv_varDeclarations_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:127:2: ( ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR ) )
            // InternalGlobalConstantsParser.g:128:2: ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            {
            // InternalGlobalConstantsParser.g:128:2: ( () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR )
            // InternalGlobalConstantsParser.g:129:3: () otherlv_1= VAR_GLOBAL ( (lv_constant_2_0= CONSTANT ) )? ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )* otherlv_4= END_VAR
            {
            // InternalGlobalConstantsParser.g:129:3: ()
            // InternalGlobalConstantsParser.g:130:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarGlobalDeclarationBlockAccess().getSTVarGlobalDeclarationBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VAR_GLOBAL,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTVarGlobalDeclarationBlockAccess().getVAR_GLOBALKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:140:3: ( (lv_constant_2_0= CONSTANT ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==CONSTANT) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalGlobalConstantsParser.g:141:4: (lv_constant_2_0= CONSTANT )
                    {
                    // InternalGlobalConstantsParser.g:141:4: (lv_constant_2_0= CONSTANT )
                    // InternalGlobalConstantsParser.g:142:5: lv_constant_2_0= CONSTANT
                    {
                    lv_constant_2_0=(Token)match(input,CONSTANT,FOLLOW_5); if (state.failed) return current;
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

            // InternalGlobalConstantsParser.g:154:3: ( (lv_varDeclarations_3_0= ruleSTVarDeclaration ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==RULE_ID) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:155:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    {
            	    // InternalGlobalConstantsParser.g:155:4: (lv_varDeclarations_3_0= ruleSTVarDeclaration )
            	    // InternalGlobalConstantsParser.g:156:5: lv_varDeclarations_3_0= ruleSTVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTVarGlobalDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_5);
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
            	    break loop3;
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
    // InternalGlobalConstantsParser.g:181:1: entryRuleSTExpressionSource returns [EObject current=null] : iv_ruleSTExpressionSource= ruleSTExpressionSource EOF ;
    public final EObject entryRuleSTExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpressionSource = null;


        try {
            // InternalGlobalConstantsParser.g:181:59: (iv_ruleSTExpressionSource= ruleSTExpressionSource EOF )
            // InternalGlobalConstantsParser.g:182:2: iv_ruleSTExpressionSource= ruleSTExpressionSource EOF
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
    // InternalGlobalConstantsParser.g:188:1: ruleSTExpressionSource returns [EObject current=null] : ( () ( (lv_expression_1_0= ruleSTExpression ) )? ) ;
    public final EObject ruleSTExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_expression_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:194:2: ( ( () ( (lv_expression_1_0= ruleSTExpression ) )? ) )
            // InternalGlobalConstantsParser.g:195:2: ( () ( (lv_expression_1_0= ruleSTExpression ) )? )
            {
            // InternalGlobalConstantsParser.g:195:2: ( () ( (lv_expression_1_0= ruleSTExpression ) )? )
            // InternalGlobalConstantsParser.g:196:3: () ( (lv_expression_1_0= ruleSTExpression ) )?
            {
            // InternalGlobalConstantsParser.g:196:3: ()
            // InternalGlobalConstantsParser.g:197:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTExpressionSourceAccess().getSTExpressionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:203:3: ( (lv_expression_1_0= ruleSTExpression ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LDATE_AND_TIME||LA4_0==DATE_AND_TIME||LA4_0==LTIME_OF_DAY||LA4_0==TIME_OF_DAY||LA4_0==WSTRING||LA4_0==STRING||LA4_0==DWORD||LA4_0==FALSE||(LA4_0>=LDATE && LA4_0<=LWORD)||(LA4_0>=UDINT && LA4_0<=ULINT)||(LA4_0>=USINT && LA4_0<=WCHAR)||(LA4_0>=BOOL && LA4_0<=BYTE)||(LA4_0>=CHAR && LA4_0<=DINT)||(LA4_0>=LINT && LA4_0<=LTOD)||(LA4_0>=REAL && LA4_0<=SINT)||(LA4_0>=THIS && LA4_0<=TRUE)||LA4_0==UINT||(LA4_0>=WORD && LA4_0<=AND)||(LA4_0>=INT && LA4_0<=NOT)||LA4_0==TOD||LA4_0==XOR||LA4_0==DT||(LA4_0>=LD && LA4_0<=LT)||LA4_0==OR||LA4_0==LeftParenthesis||LA4_0==PlusSign||LA4_0==HyphenMinus||(LA4_0>=D && LA4_0<=T)||(LA4_0>=RULE_NON_DECIMAL && LA4_0<=RULE_DECIMAL)||(LA4_0>=RULE_ID && LA4_0<=RULE_STRING)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalGlobalConstantsParser.g:204:4: (lv_expression_1_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:204:4: (lv_expression_1_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:205:5: lv_expression_1_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:226:1: entryRuleSTInitializerExpressionSource returns [EObject current=null] : iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF ;
    public final EObject entryRuleSTInitializerExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpressionSource = null;


        try {
            // InternalGlobalConstantsParser.g:226:70: (iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF )
            // InternalGlobalConstantsParser.g:227:2: iv_ruleSTInitializerExpressionSource= ruleSTInitializerExpressionSource EOF
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
    // InternalGlobalConstantsParser.g:233:1: ruleSTInitializerExpressionSource returns [EObject current=null] : ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? ) ;
    public final EObject ruleSTInitializerExpressionSource() throws RecognitionException {
        EObject current = null;

        EObject lv_initializerExpression_1_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:239:2: ( ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? ) )
            // InternalGlobalConstantsParser.g:240:2: ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? )
            {
            // InternalGlobalConstantsParser.g:240:2: ( () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )? )
            // InternalGlobalConstantsParser.g:241:3: () ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )?
            {
            // InternalGlobalConstantsParser.g:241:3: ()
            // InternalGlobalConstantsParser.g:242:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTInitializerExpressionSourceAccess().getSTInitializerExpressionSourceAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:248:3: ( (lv_initializerExpression_1_0= ruleSTInitializerExpression ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==LDATE_AND_TIME||LA5_0==DATE_AND_TIME||LA5_0==LTIME_OF_DAY||LA5_0==TIME_OF_DAY||LA5_0==WSTRING||LA5_0==STRING||LA5_0==DWORD||LA5_0==FALSE||(LA5_0>=LDATE && LA5_0<=LWORD)||(LA5_0>=UDINT && LA5_0<=ULINT)||(LA5_0>=USINT && LA5_0<=WCHAR)||(LA5_0>=BOOL && LA5_0<=BYTE)||(LA5_0>=CHAR && LA5_0<=DINT)||(LA5_0>=LINT && LA5_0<=LTOD)||(LA5_0>=REAL && LA5_0<=SINT)||(LA5_0>=THIS && LA5_0<=TRUE)||LA5_0==UINT||(LA5_0>=WORD && LA5_0<=AND)||(LA5_0>=INT && LA5_0<=NOT)||LA5_0==TOD||LA5_0==XOR||LA5_0==DT||(LA5_0>=LD && LA5_0<=LT)||LA5_0==OR||LA5_0==LeftParenthesis||LA5_0==PlusSign||LA5_0==HyphenMinus||(LA5_0>=D && LA5_0<=LeftSquareBracket)||(LA5_0>=RULE_NON_DECIMAL && LA5_0<=RULE_DECIMAL)||(LA5_0>=RULE_ID && LA5_0<=RULE_STRING)) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalGlobalConstantsParser.g:249:4: (lv_initializerExpression_1_0= ruleSTInitializerExpression )
                    {
                    // InternalGlobalConstantsParser.g:249:4: (lv_initializerExpression_1_0= ruleSTInitializerExpression )
                    // InternalGlobalConstantsParser.g:250:5: lv_initializerExpression_1_0= ruleSTInitializerExpression
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


    // $ANTLR start "entryRuleSTVarDeclaration"
    // InternalGlobalConstantsParser.g:271:1: entryRuleSTVarDeclaration returns [EObject current=null] : iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF ;
    public final EObject entryRuleSTVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTVarDeclaration = null;


        try {
            // InternalGlobalConstantsParser.g:271:57: (iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF )
            // InternalGlobalConstantsParser.g:272:2: iv_ruleSTVarDeclaration= ruleSTVarDeclaration EOF
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
    // InternalGlobalConstantsParser.g:278:1: ruleSTVarDeclaration returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) ;
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
            // InternalGlobalConstantsParser.g:284:2: ( ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon ) )
            // InternalGlobalConstantsParser.g:285:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            {
            // InternalGlobalConstantsParser.g:285:2: ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon )
            // InternalGlobalConstantsParser.g:286:3: () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )? otherlv_4= Colon ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )? ( ( ruleSTAnyType ) ) (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )? (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )? otherlv_23= Semicolon
            {
            // InternalGlobalConstantsParser.g:286:3: ()
            // InternalGlobalConstantsParser.g:287:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:293:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalGlobalConstantsParser.g:294:4: (lv_name_1_0= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:294:4: (lv_name_1_0= RULE_ID )
            // InternalGlobalConstantsParser.g:295:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_6); if (state.failed) return current;
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

            // InternalGlobalConstantsParser.g:311:3: (otherlv_2= AT ( (otherlv_3= RULE_ID ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==AT) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalGlobalConstantsParser.g:312:4: otherlv_2= AT ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,AT,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:316:4: ( (otherlv_3= RULE_ID ) )
                    // InternalGlobalConstantsParser.g:317:5: (otherlv_3= RULE_ID )
                    {
                    // InternalGlobalConstantsParser.g:317:5: (otherlv_3= RULE_ID )
                    // InternalGlobalConstantsParser.g:318:6: otherlv_3= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTVarDeclarationRule());
                      						}
                      					
                    }
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(otherlv_3, grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Colon,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3());
              		
            }
            // InternalGlobalConstantsParser.g:334:3: ( ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ARRAY) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalGlobalConstantsParser.g:335:4: ( (lv_array_5_0= ARRAY ) ) ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) ) otherlv_16= OF
                    {
                    // InternalGlobalConstantsParser.g:335:4: ( (lv_array_5_0= ARRAY ) )
                    // InternalGlobalConstantsParser.g:336:5: (lv_array_5_0= ARRAY )
                    {
                    // InternalGlobalConstantsParser.g:336:5: (lv_array_5_0= ARRAY )
                    // InternalGlobalConstantsParser.g:337:6: lv_array_5_0= ARRAY
                    {
                    lv_array_5_0=(Token)match(input,ARRAY,FOLLOW_10); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:349:4: ( (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket ) | (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket ) )
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==LeftSquareBracket) ) {
                        int LA9_1 = input.LA(2);

                        if ( (LA9_1==LDATE_AND_TIME||LA9_1==DATE_AND_TIME||LA9_1==LTIME_OF_DAY||LA9_1==TIME_OF_DAY||LA9_1==WSTRING||LA9_1==STRING||LA9_1==DWORD||LA9_1==FALSE||(LA9_1>=LDATE && LA9_1<=LWORD)||(LA9_1>=UDINT && LA9_1<=ULINT)||(LA9_1>=USINT && LA9_1<=WCHAR)||(LA9_1>=BOOL && LA9_1<=BYTE)||(LA9_1>=CHAR && LA9_1<=DINT)||(LA9_1>=LINT && LA9_1<=LTOD)||(LA9_1>=REAL && LA9_1<=SINT)||(LA9_1>=THIS && LA9_1<=TRUE)||LA9_1==UINT||(LA9_1>=WORD && LA9_1<=AND)||(LA9_1>=INT && LA9_1<=NOT)||LA9_1==TOD||LA9_1==XOR||LA9_1==DT||(LA9_1>=LD && LA9_1<=LT)||LA9_1==OR||LA9_1==LeftParenthesis||LA9_1==PlusSign||LA9_1==HyphenMinus||(LA9_1>=D && LA9_1<=T)||(LA9_1>=RULE_NON_DECIMAL && LA9_1<=RULE_DECIMAL)||(LA9_1>=RULE_ID && LA9_1<=RULE_STRING)) ) {
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
                            // InternalGlobalConstantsParser.g:350:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:350:5: (otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:351:6: otherlv_6= LeftSquareBracket ( (lv_ranges_7_0= ruleSTExpression ) ) (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )* otherlv_10= RightSquareBracket
                            {
                            otherlv_6=(Token)match(input,LeftSquareBracket,FOLLOW_11); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:355:6: ( (lv_ranges_7_0= ruleSTExpression ) )
                            // InternalGlobalConstantsParser.g:356:7: (lv_ranges_7_0= ruleSTExpression )
                            {
                            // InternalGlobalConstantsParser.g:356:7: (lv_ranges_7_0= ruleSTExpression )
                            // InternalGlobalConstantsParser.g:357:8: lv_ranges_7_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_12);
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

                            // InternalGlobalConstantsParser.g:374:6: (otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) ) )*
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( (LA7_0==Comma) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:375:7: otherlv_8= Comma ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_8=(Token)match(input,Comma,FOLLOW_11); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_8, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:379:7: ( (lv_ranges_9_0= ruleSTExpression ) )
                            	    // InternalGlobalConstantsParser.g:380:8: (lv_ranges_9_0= ruleSTExpression )
                            	    {
                            	    // InternalGlobalConstantsParser.g:380:8: (lv_ranges_9_0= ruleSTExpression )
                            	    // InternalGlobalConstantsParser.g:381:9: lv_ranges_9_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_12);
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
                            	    break loop7;
                                }
                            } while (true);

                            otherlv_10=(Token)match(input,RightSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalGlobalConstantsParser.g:405:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:405:5: (otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:406:6: otherlv_11= LeftSquareBracket ( (lv_count_12_0= Asterisk ) ) (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )* otherlv_15= RightSquareBracket
                            {
                            otherlv_11=(Token)match(input,LeftSquareBracket,FOLLOW_14); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:410:6: ( (lv_count_12_0= Asterisk ) )
                            // InternalGlobalConstantsParser.g:411:7: (lv_count_12_0= Asterisk )
                            {
                            // InternalGlobalConstantsParser.g:411:7: (lv_count_12_0= Asterisk )
                            // InternalGlobalConstantsParser.g:412:8: lv_count_12_0= Asterisk
                            {
                            lv_count_12_0=(Token)match(input,Asterisk,FOLLOW_12); if (state.failed) return current;
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

                            // InternalGlobalConstantsParser.g:424:6: (otherlv_13= Comma ( (lv_count_14_0= Asterisk ) ) )*
                            loop8:
                            do {
                                int alt8=2;
                                int LA8_0 = input.LA(1);

                                if ( (LA8_0==Comma) ) {
                                    alt8=1;
                                }


                                switch (alt8) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:425:7: otherlv_13= Comma ( (lv_count_14_0= Asterisk ) )
                            	    {
                            	    otherlv_13=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_13, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:429:7: ( (lv_count_14_0= Asterisk ) )
                            	    // InternalGlobalConstantsParser.g:430:8: (lv_count_14_0= Asterisk )
                            	    {
                            	    // InternalGlobalConstantsParser.g:430:8: (lv_count_14_0= Asterisk )
                            	    // InternalGlobalConstantsParser.g:431:9: lv_count_14_0= Asterisk
                            	    {
                            	    lv_count_14_0=(Token)match(input,Asterisk,FOLLOW_12); if (state.failed) return current;
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
                            	    break loop8;
                                }
                            } while (true);

                            otherlv_15=(Token)match(input,RightSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_15, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,OF,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:455:3: ( ( ruleSTAnyType ) )
            // InternalGlobalConstantsParser.g:456:4: ( ruleSTAnyType )
            {
            // InternalGlobalConstantsParser.g:456:4: ( ruleSTAnyType )
            // InternalGlobalConstantsParser.g:457:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTVarDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0());
              				
            }
            pushFollow(FOLLOW_15);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:471:3: (otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==LeftSquareBracket) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalGlobalConstantsParser.g:472:4: otherlv_18= LeftSquareBracket ( (lv_maxLength_19_0= ruleSTExpression ) ) otherlv_20= RightSquareBracket
                    {
                    otherlv_18=(Token)match(input,LeftSquareBracket,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:476:4: ( (lv_maxLength_19_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:477:5: (lv_maxLength_19_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:477:5: (lv_maxLength_19_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:478:6: lv_maxLength_19_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_16);
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

                    otherlv_20=(Token)match(input,RightSquareBracket,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:500:3: (otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ColonEqualsSign) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalGlobalConstantsParser.g:501:4: otherlv_21= ColonEqualsSign ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    {
                    otherlv_21=(Token)match(input,ColonEqualsSign,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:505:4: ( (lv_defaultValue_22_0= ruleSTInitializerExpression ) )
                    // InternalGlobalConstantsParser.g:506:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    {
                    // InternalGlobalConstantsParser.g:506:5: (lv_defaultValue_22_0= ruleSTInitializerExpression )
                    // InternalGlobalConstantsParser.g:507:6: lv_defaultValue_22_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_19);
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
    // InternalGlobalConstantsParser.g:533:1: entryRuleSTTypeDeclaration returns [EObject current=null] : iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF ;
    public final EObject entryRuleSTTypeDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTypeDeclaration = null;


        try {
            // InternalGlobalConstantsParser.g:533:58: (iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF )
            // InternalGlobalConstantsParser.g:534:2: iv_ruleSTTypeDeclaration= ruleSTTypeDeclaration EOF
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
    // InternalGlobalConstantsParser.g:540:1: ruleSTTypeDeclaration returns [EObject current=null] : ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) ;
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
            // InternalGlobalConstantsParser.g:546:2: ( ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? ) )
            // InternalGlobalConstantsParser.g:547:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            {
            // InternalGlobalConstantsParser.g:547:2: ( () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )? )
            // InternalGlobalConstantsParser.g:548:3: () ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )? ( ( ruleSTAnyType ) ) (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            {
            // InternalGlobalConstantsParser.g:548:3: ()
            // InternalGlobalConstantsParser.g:549:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTTypeDeclarationAccess().getSTTypeDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:555:3: ( ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ARRAY) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalGlobalConstantsParser.g:556:4: ( (lv_array_1_0= ARRAY ) ) ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) ) otherlv_12= OF
                    {
                    // InternalGlobalConstantsParser.g:556:4: ( (lv_array_1_0= ARRAY ) )
                    // InternalGlobalConstantsParser.g:557:5: (lv_array_1_0= ARRAY )
                    {
                    // InternalGlobalConstantsParser.g:557:5: (lv_array_1_0= ARRAY )
                    // InternalGlobalConstantsParser.g:558:6: lv_array_1_0= ARRAY
                    {
                    lv_array_1_0=(Token)match(input,ARRAY,FOLLOW_10); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:570:4: ( (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket ) | (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket ) )
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==LeftSquareBracket) ) {
                        int LA15_1 = input.LA(2);

                        if ( (LA15_1==Asterisk) ) {
                            alt15=2;
                        }
                        else if ( (LA15_1==LDATE_AND_TIME||LA15_1==DATE_AND_TIME||LA15_1==LTIME_OF_DAY||LA15_1==TIME_OF_DAY||LA15_1==WSTRING||LA15_1==STRING||LA15_1==DWORD||LA15_1==FALSE||(LA15_1>=LDATE && LA15_1<=LWORD)||(LA15_1>=UDINT && LA15_1<=ULINT)||(LA15_1>=USINT && LA15_1<=WCHAR)||(LA15_1>=BOOL && LA15_1<=BYTE)||(LA15_1>=CHAR && LA15_1<=DINT)||(LA15_1>=LINT && LA15_1<=LTOD)||(LA15_1>=REAL && LA15_1<=SINT)||(LA15_1>=THIS && LA15_1<=TRUE)||LA15_1==UINT||(LA15_1>=WORD && LA15_1<=AND)||(LA15_1>=INT && LA15_1<=NOT)||LA15_1==TOD||LA15_1==XOR||LA15_1==DT||(LA15_1>=LD && LA15_1<=LT)||LA15_1==OR||LA15_1==LeftParenthesis||LA15_1==PlusSign||LA15_1==HyphenMinus||(LA15_1>=D && LA15_1<=T)||(LA15_1>=RULE_NON_DECIMAL && LA15_1<=RULE_DECIMAL)||(LA15_1>=RULE_ID && LA15_1<=RULE_STRING)) ) {
                            alt15=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 15, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 0, input);

                        throw nvae;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:571:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:571:5: (otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:572:6: otherlv_2= LeftSquareBracket ( (lv_ranges_3_0= ruleSTExpression ) ) (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )* otherlv_6= RightSquareBracket
                            {
                            otherlv_2=(Token)match(input,LeftSquareBracket,FOLLOW_11); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_0_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:576:6: ( (lv_ranges_3_0= ruleSTExpression ) )
                            // InternalGlobalConstantsParser.g:577:7: (lv_ranges_3_0= ruleSTExpression )
                            {
                            // InternalGlobalConstantsParser.g:577:7: (lv_ranges_3_0= ruleSTExpression )
                            // InternalGlobalConstantsParser.g:578:8: lv_ranges_3_0= ruleSTExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_12);
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

                            // InternalGlobalConstantsParser.g:595:6: (otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) ) )*
                            loop13:
                            do {
                                int alt13=2;
                                int LA13_0 = input.LA(1);

                                if ( (LA13_0==Comma) ) {
                                    alt13=1;
                                }


                                switch (alt13) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:596:7: otherlv_4= Comma ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_11); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_4, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_0_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:600:7: ( (lv_ranges_5_0= ruleSTExpression ) )
                            	    // InternalGlobalConstantsParser.g:601:8: (lv_ranges_5_0= ruleSTExpression )
                            	    {
                            	    // InternalGlobalConstantsParser.g:601:8: (lv_ranges_5_0= ruleSTExpression )
                            	    // InternalGlobalConstantsParser.g:602:9: lv_ranges_5_0= ruleSTExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_2_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_12);
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
                            	    break loop13;
                                }
                            } while (true);

                            otherlv_6=(Token)match(input,RightSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalGlobalConstantsParser.g:626:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            {
                            // InternalGlobalConstantsParser.g:626:5: (otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket )
                            // InternalGlobalConstantsParser.g:627:6: otherlv_7= LeftSquareBracket ( (lv_count_8_0= Asterisk ) ) (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )* otherlv_11= RightSquareBracket
                            {
                            otherlv_7=(Token)match(input,LeftSquareBracket,FOLLOW_14); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_1_0());
                              					
                            }
                            // InternalGlobalConstantsParser.g:631:6: ( (lv_count_8_0= Asterisk ) )
                            // InternalGlobalConstantsParser.g:632:7: (lv_count_8_0= Asterisk )
                            {
                            // InternalGlobalConstantsParser.g:632:7: (lv_count_8_0= Asterisk )
                            // InternalGlobalConstantsParser.g:633:8: lv_count_8_0= Asterisk
                            {
                            lv_count_8_0=(Token)match(input,Asterisk,FOLLOW_12); if (state.failed) return current;
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

                            // InternalGlobalConstantsParser.g:645:6: (otherlv_9= Comma ( (lv_count_10_0= Asterisk ) ) )*
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( (LA14_0==Comma) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:646:7: otherlv_9= Comma ( (lv_count_10_0= Asterisk ) )
                            	    {
                            	    otherlv_9=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_9, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_1_2_0());
                            	      						
                            	    }
                            	    // InternalGlobalConstantsParser.g:650:7: ( (lv_count_10_0= Asterisk ) )
                            	    // InternalGlobalConstantsParser.g:651:8: (lv_count_10_0= Asterisk )
                            	    {
                            	    // InternalGlobalConstantsParser.g:651:8: (lv_count_10_0= Asterisk )
                            	    // InternalGlobalConstantsParser.g:652:9: lv_count_10_0= Asterisk
                            	    {
                            	    lv_count_10_0=(Token)match(input,Asterisk,FOLLOW_12); if (state.failed) return current;
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
                            	    break loop14;
                                }
                            } while (true);

                            otherlv_11=(Token)match(input,RightSquareBracket,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_11, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_1_3());
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_12=(Token)match(input,OF,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getSTTypeDeclarationAccess().getOFKeyword_1_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:676:3: ( ( ruleSTAnyType ) )
            // InternalGlobalConstantsParser.g:677:4: ( ruleSTAnyType )
            {
            // InternalGlobalConstantsParser.g:677:4: ( ruleSTAnyType )
            // InternalGlobalConstantsParser.g:678:5: ruleSTAnyType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementCrossReference_2_0());
              				
            }
            pushFollow(FOLLOW_20);
            ruleSTAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:692:3: (otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==LeftSquareBracket) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalGlobalConstantsParser.g:693:4: otherlv_14= LeftSquareBracket ( (lv_maxLength_15_0= ruleSTExpression ) ) otherlv_16= RightSquareBracket
                    {
                    otherlv_14=(Token)match(input,LeftSquareBracket,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_3_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:697:4: ( (lv_maxLength_15_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:698:5: (lv_maxLength_15_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:698:5: (lv_maxLength_15_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:699:6: lv_maxLength_15_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_16);
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
    // InternalGlobalConstantsParser.g:725:1: entryRuleSTInitializerExpression returns [EObject current=null] : iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF ;
    public final EObject entryRuleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:725:64: (iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:726:2: iv_ruleSTInitializerExpression= ruleSTInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:732:1: ruleSTInitializerExpression returns [EObject current=null] : (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) ;
    public final EObject ruleSTInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STElementaryInitializerExpression_0 = null;

        EObject this_STArrayInitializerExpression_1 = null;

        EObject this_STStructInitializerExpression_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:738:2: ( (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression ) )
            // InternalGlobalConstantsParser.g:739:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:739:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )
            int alt18=3;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // InternalGlobalConstantsParser.g:740:3: this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression
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
                    // InternalGlobalConstantsParser.g:749:3: this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression
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
                    // InternalGlobalConstantsParser.g:758:3: this_STStructInitializerExpression_2= ruleSTStructInitializerExpression
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
    // InternalGlobalConstantsParser.g:770:1: entryRuleSTElementaryInitializerExpression returns [EObject current=null] : iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF ;
    public final EObject entryRuleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElementaryInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:770:74: (iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:771:2: iv_ruleSTElementaryInitializerExpression= ruleSTElementaryInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:777:1: ruleSTElementaryInitializerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTElementaryInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:783:2: ( ( (lv_value_0_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:784:2: ( (lv_value_0_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:784:2: ( (lv_value_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:785:3: (lv_value_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:785:3: (lv_value_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:786:4: lv_value_0_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:806:1: entryRuleSTArrayInitializerExpression returns [EObject current=null] : iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF ;
    public final EObject entryRuleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:806:69: (iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:807:2: iv_ruleSTArrayInitializerExpression= ruleSTArrayInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:813:1: ruleSTArrayInitializerExpression returns [EObject current=null] : (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleSTArrayInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:819:2: ( (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket ) )
            // InternalGlobalConstantsParser.g:820:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            {
            // InternalGlobalConstantsParser.g:820:2: (otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket )
            // InternalGlobalConstantsParser.g:821:3: otherlv_0= LeftSquareBracket ( (lv_values_1_0= ruleSTArrayInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )* otherlv_4= RightSquareBracket
            {
            otherlv_0=(Token)match(input,LeftSquareBracket,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:825:3: ( (lv_values_1_0= ruleSTArrayInitElement ) )
            // InternalGlobalConstantsParser.g:826:4: (lv_values_1_0= ruleSTArrayInitElement )
            {
            // InternalGlobalConstantsParser.g:826:4: (lv_values_1_0= ruleSTArrayInitElement )
            // InternalGlobalConstantsParser.g:827:5: lv_values_1_0= ruleSTArrayInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_12);
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

            // InternalGlobalConstantsParser.g:844:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==Comma) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:845:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_18); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:849:4: ( (lv_values_3_0= ruleSTArrayInitElement ) )
            	    // InternalGlobalConstantsParser.g:850:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    {
            	    // InternalGlobalConstantsParser.g:850:5: (lv_values_3_0= ruleSTArrayInitElement )
            	    // InternalGlobalConstantsParser.g:851:6: lv_values_3_0= ruleSTArrayInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_12);
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
            	    break loop19;
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
    // InternalGlobalConstantsParser.g:877:1: entryRuleSTArrayInitElement returns [EObject current=null] : iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF ;
    public final EObject entryRuleSTArrayInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTArrayInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:877:59: (iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF )
            // InternalGlobalConstantsParser.g:878:2: iv_ruleSTArrayInitElement= ruleSTArrayInitElement EOF
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
    // InternalGlobalConstantsParser.g:884:1: ruleSTArrayInitElement returns [EObject current=null] : ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? ) ;
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
            // InternalGlobalConstantsParser.g:890:2: ( ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:891:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:891:2: ( ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:892:3: ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) ) (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:892:3: ( (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:893:4: (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:893:4: (lv_indexOrInitExpression_0_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:894:5: lv_indexOrInitExpression_0_0= ruleSTInitializerExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getIndexOrInitExpressionSTInitializerExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_21);
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

            // InternalGlobalConstantsParser.g:911:3: (otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==LeftParenthesis) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalGlobalConstantsParser.g:912:4: otherlv_1= LeftParenthesis ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) ) (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )* otherlv_5= RightParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTArrayInitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:916:4: ( (lv_initExpressions_2_0= ruleSTInitializerExpression ) )
                    // InternalGlobalConstantsParser.g:917:5: (lv_initExpressions_2_0= ruleSTInitializerExpression )
                    {
                    // InternalGlobalConstantsParser.g:917:5: (lv_initExpressions_2_0= ruleSTInitializerExpression )
                    // InternalGlobalConstantsParser.g:918:6: lv_initExpressions_2_0= ruleSTInitializerExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_22);
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

                    // InternalGlobalConstantsParser.g:935:4: (otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) ) )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==Comma) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // InternalGlobalConstantsParser.g:936:5: otherlv_3= Comma ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,Comma,FOLLOW_18); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getSTArrayInitElementAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalGlobalConstantsParser.g:940:5: ( (lv_initExpressions_4_0= ruleSTInitializerExpression ) )
                    	    // InternalGlobalConstantsParser.g:941:6: (lv_initExpressions_4_0= ruleSTInitializerExpression )
                    	    {
                    	    // InternalGlobalConstantsParser.g:941:6: (lv_initExpressions_4_0= ruleSTInitializerExpression )
                    	    // InternalGlobalConstantsParser.g:942:7: lv_initExpressions_4_0= ruleSTInitializerExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_22);
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
                    	    break loop20;
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
    // InternalGlobalConstantsParser.g:969:1: entryRuleSTStructInitializerExpression returns [EObject current=null] : iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF ;
    public final EObject entryRuleSTStructInitializerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitializerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:969:70: (iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF )
            // InternalGlobalConstantsParser.g:970:2: iv_ruleSTStructInitializerExpression= ruleSTStructInitializerExpression EOF
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
    // InternalGlobalConstantsParser.g:976:1: ruleSTStructInitializerExpression returns [EObject current=null] : (otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis ) ;
    public final EObject ruleSTStructInitializerExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_values_1_0 = null;

        EObject lv_values_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:982:2: ( (otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis ) )
            // InternalGlobalConstantsParser.g:983:2: (otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis )
            {
            // InternalGlobalConstantsParser.g:983:2: (otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis )
            // InternalGlobalConstantsParser.g:984:3: otherlv_0= LeftParenthesis ( (lv_values_1_0= ruleSTStructInitElement ) ) (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )* otherlv_4= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTStructInitializerExpressionAccess().getLeftParenthesisKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:988:3: ( (lv_values_1_0= ruleSTStructInitElement ) )
            // InternalGlobalConstantsParser.g:989:4: (lv_values_1_0= ruleSTStructInitElement )
            {
            // InternalGlobalConstantsParser.g:989:4: (lv_values_1_0= ruleSTStructInitElement )
            // InternalGlobalConstantsParser.g:990:5: lv_values_1_0= ruleSTStructInitElement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_22);
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

            // InternalGlobalConstantsParser.g:1007:3: (otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==Comma) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1008:4: otherlv_2= Comma ( (lv_values_3_0= ruleSTStructInitElement ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getSTStructInitializerExpressionAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1012:4: ( (lv_values_3_0= ruleSTStructInitElement ) )
            	    // InternalGlobalConstantsParser.g:1013:5: (lv_values_3_0= ruleSTStructInitElement )
            	    {
            	    // InternalGlobalConstantsParser.g:1013:5: (lv_values_3_0= ruleSTStructInitElement )
            	    // InternalGlobalConstantsParser.g:1014:6: lv_values_3_0= ruleSTStructInitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_22);
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
            	    break loop22;
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
    // InternalGlobalConstantsParser.g:1040:1: entryRuleSTStructInitElement returns [EObject current=null] : iv_ruleSTStructInitElement= ruleSTStructInitElement EOF ;
    public final EObject entryRuleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStructInitElement = null;


        try {
            // InternalGlobalConstantsParser.g:1040:60: (iv_ruleSTStructInitElement= ruleSTStructInitElement EOF )
            // InternalGlobalConstantsParser.g:1041:2: iv_ruleSTStructInitElement= ruleSTStructInitElement EOF
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
    // InternalGlobalConstantsParser.g:1047:1: ruleSTStructInitElement returns [EObject current=null] : ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) ;
    public final EObject ruleSTStructInitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1053:2: ( ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1054:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1054:2: ( ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) ) )
            // InternalGlobalConstantsParser.g:1055:3: ( ( ruleSTFeatureName ) ) otherlv_1= ColonEqualsSign ( (lv_value_2_0= ruleSTInitializerExpression ) )
            {
            // InternalGlobalConstantsParser.g:1055:3: ( ( ruleSTFeatureName ) )
            // InternalGlobalConstantsParser.g:1056:4: ( ruleSTFeatureName )
            {
            // InternalGlobalConstantsParser.g:1056:4: ( ruleSTFeatureName )
            // InternalGlobalConstantsParser.g:1057:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTStructInitElementRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_24);
            ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTStructInitElementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1075:3: ( (lv_value_2_0= ruleSTInitializerExpression ) )
            // InternalGlobalConstantsParser.g:1076:4: (lv_value_2_0= ruleSTInitializerExpression )
            {
            // InternalGlobalConstantsParser.g:1076:4: (lv_value_2_0= ruleSTInitializerExpression )
            // InternalGlobalConstantsParser.g:1077:5: lv_value_2_0= ruleSTInitializerExpression
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
    // InternalGlobalConstantsParser.g:1098:1: entryRuleSTStatement returns [EObject current=null] : iv_ruleSTStatement= ruleSTStatement EOF ;
    public final EObject entryRuleSTStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1098:52: (iv_ruleSTStatement= ruleSTStatement EOF )
            // InternalGlobalConstantsParser.g:1099:2: iv_ruleSTStatement= ruleSTStatement EOF
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
    // InternalGlobalConstantsParser.g:1105:1: ruleSTStatement returns [EObject current=null] : ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) ;
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
            // InternalGlobalConstantsParser.g:1111:2: ( ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) ) )
            // InternalGlobalConstantsParser.g:1112:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            {
            // InternalGlobalConstantsParser.g:1112:2: ( ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon ) | ( () otherlv_15= Semicolon ) )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==LDATE_AND_TIME||LA24_0==DATE_AND_TIME||LA24_0==LTIME_OF_DAY||LA24_0==TIME_OF_DAY||LA24_0==CONTINUE||LA24_0==WSTRING||LA24_0==REPEAT||LA24_0==RETURN||LA24_0==STRING||LA24_0==DWORD||LA24_0==FALSE||(LA24_0>=LDATE && LA24_0<=LWORD)||(LA24_0>=UDINT && LA24_0<=ULINT)||(LA24_0>=USINT && LA24_0<=DINT)||LA24_0==EXIT||(LA24_0>=LINT && LA24_0<=LTOD)||(LA24_0>=REAL && LA24_0<=SINT)||(LA24_0>=THIS && LA24_0<=TRUE)||LA24_0==UINT||(LA24_0>=WORD && LA24_0<=MOD)||LA24_0==TOD||LA24_0==XOR||(LA24_0>=DT && LA24_0<=LT)||LA24_0==OR||LA24_0==LeftParenthesis||LA24_0==PlusSign||LA24_0==HyphenMinus||(LA24_0>=D && LA24_0<=T)||(LA24_0>=RULE_NON_DECIMAL && LA24_0<=RULE_DECIMAL)||(LA24_0>=RULE_ID && LA24_0<=RULE_STRING)) ) {
                alt24=1;
            }
            else if ( (LA24_0==Semicolon) ) {
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
                    // InternalGlobalConstantsParser.g:1113:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    {
                    // InternalGlobalConstantsParser.g:1113:3: ( (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon )
                    // InternalGlobalConstantsParser.g:1114:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) ) otherlv_13= Semicolon
                    {
                    // InternalGlobalConstantsParser.g:1114:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )
                    int alt23=10;
                    alt23 = dfa23.predict(input);
                    switch (alt23) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:1115:5: this_STIfStatement_0= ruleSTIfStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTIfStatementParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_19);
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
                            // InternalGlobalConstantsParser.g:1124:5: this_STCaseStatement_1= ruleSTCaseStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCaseStatementParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_19);
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
                            // InternalGlobalConstantsParser.g:1133:5: this_STForStatement_2= ruleSTForStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTForStatementParserRuleCall_0_0_2());
                              				
                            }
                            pushFollow(FOLLOW_19);
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
                            // InternalGlobalConstantsParser.g:1142:5: this_STWhileStatement_3= ruleSTWhileStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTWhileStatementParserRuleCall_0_0_3());
                              				
                            }
                            pushFollow(FOLLOW_19);
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
                            // InternalGlobalConstantsParser.g:1151:5: this_STRepeatStatement_4= ruleSTRepeatStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTRepeatStatementParserRuleCall_0_0_4());
                              				
                            }
                            pushFollow(FOLLOW_19);
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
                            // InternalGlobalConstantsParser.g:1160:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            {
                            // InternalGlobalConstantsParser.g:1160:5: ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement )
                            // InternalGlobalConstantsParser.g:1161:6: ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentStatementParserRuleCall_0_0_5());
                              					
                            }
                            pushFollow(FOLLOW_19);
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
                            // InternalGlobalConstantsParser.g:1172:5: this_STCallStatement_6= ruleSTCallStatement
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCallStatementParserRuleCall_0_0_6());
                              				
                            }
                            pushFollow(FOLLOW_19);
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
                            // InternalGlobalConstantsParser.g:1181:5: ( () otherlv_8= RETURN )
                            {
                            // InternalGlobalConstantsParser.g:1181:5: ( () otherlv_8= RETURN )
                            // InternalGlobalConstantsParser.g:1182:6: () otherlv_8= RETURN
                            {
                            // InternalGlobalConstantsParser.g:1182:6: ()
                            // InternalGlobalConstantsParser.g:1183:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_7_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_8=(Token)match(input,RETURN,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_8, grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_7_1());
                              					
                            }

                            }


                            }
                            break;
                        case 9 :
                            // InternalGlobalConstantsParser.g:1195:5: ( () otherlv_10= CONTINUE )
                            {
                            // InternalGlobalConstantsParser.g:1195:5: ( () otherlv_10= CONTINUE )
                            // InternalGlobalConstantsParser.g:1196:6: () otherlv_10= CONTINUE
                            {
                            // InternalGlobalConstantsParser.g:1196:6: ()
                            // InternalGlobalConstantsParser.g:1197:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_8_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_10=(Token)match(input,CONTINUE,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_10, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_8_1());
                              					
                            }

                            }


                            }
                            break;
                        case 10 :
                            // InternalGlobalConstantsParser.g:1209:5: ( () otherlv_12= EXIT )
                            {
                            // InternalGlobalConstantsParser.g:1209:5: ( () otherlv_12= EXIT )
                            // InternalGlobalConstantsParser.g:1210:6: () otherlv_12= EXIT
                            {
                            // InternalGlobalConstantsParser.g:1210:6: ()
                            // InternalGlobalConstantsParser.g:1211:7: 
                            {
                            if ( state.backtracking==0 ) {

                              							current = forceCreateModelElement(
                              								grammarAccess.getSTStatementAccess().getSTExitAction_0_0_9_0(),
                              								current);
                              						
                            }

                            }

                            otherlv_12=(Token)match(input,EXIT,FOLLOW_19); if (state.failed) return current;
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
                    // InternalGlobalConstantsParser.g:1229:3: ( () otherlv_15= Semicolon )
                    {
                    // InternalGlobalConstantsParser.g:1229:3: ( () otherlv_15= Semicolon )
                    // InternalGlobalConstantsParser.g:1230:4: () otherlv_15= Semicolon
                    {
                    // InternalGlobalConstantsParser.g:1230:4: ()
                    // InternalGlobalConstantsParser.g:1231:5: 
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
    // InternalGlobalConstantsParser.g:1246:1: entryRuleSTAssignmentStatement returns [EObject current=null] : iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF ;
    public final EObject entryRuleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAssignmentStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1246:62: (iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF )
            // InternalGlobalConstantsParser.g:1247:2: iv_ruleSTAssignmentStatement= ruleSTAssignmentStatement EOF
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
    // InternalGlobalConstantsParser.g:1253:1: ruleSTAssignmentStatement returns [EObject current=null] : ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTAssignmentStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1259:2: ( ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1260:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1260:2: ( ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1261:3: ( (lv_left_0_0= ruleSTAccessExpression ) ) otherlv_1= ColonEqualsSign ( (lv_right_2_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1261:3: ( (lv_left_0_0= ruleSTAccessExpression ) )
            // InternalGlobalConstantsParser.g:1262:4: (lv_left_0_0= ruleSTAccessExpression )
            {
            // InternalGlobalConstantsParser.g:1262:4: (lv_left_0_0= ruleSTAccessExpression )
            // InternalGlobalConstantsParser.g:1263:5: lv_left_0_0= ruleSTAccessExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTAssignmentStatementAccess().getLeftSTAccessExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_24);
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

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTAssignmentStatementAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1284:3: ( (lv_right_2_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1285:4: (lv_right_2_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1285:4: (lv_right_2_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1286:5: lv_right_2_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1307:1: entryRuleSTCallStatement returns [EObject current=null] : iv_ruleSTCallStatement= ruleSTCallStatement EOF ;
    public final EObject entryRuleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1307:56: (iv_ruleSTCallStatement= ruleSTCallStatement EOF )
            // InternalGlobalConstantsParser.g:1308:2: iv_ruleSTCallStatement= ruleSTCallStatement EOF
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
    // InternalGlobalConstantsParser.g:1314:1: ruleSTCallStatement returns [EObject current=null] : ( (lv_call_0_0= ruleSTAccessExpression ) ) ;
    public final EObject ruleSTCallStatement() throws RecognitionException {
        EObject current = null;

        EObject lv_call_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1320:2: ( ( (lv_call_0_0= ruleSTAccessExpression ) ) )
            // InternalGlobalConstantsParser.g:1321:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            {
            // InternalGlobalConstantsParser.g:1321:2: ( (lv_call_0_0= ruleSTAccessExpression ) )
            // InternalGlobalConstantsParser.g:1322:3: (lv_call_0_0= ruleSTAccessExpression )
            {
            // InternalGlobalConstantsParser.g:1322:3: (lv_call_0_0= ruleSTAccessExpression )
            // InternalGlobalConstantsParser.g:1323:4: lv_call_0_0= ruleSTAccessExpression
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
    // InternalGlobalConstantsParser.g:1343:1: entryRuleSTCallArgument returns [EObject current=null] : iv_ruleSTCallArgument= ruleSTCallArgument EOF ;
    public final EObject entryRuleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1343:55: (iv_ruleSTCallArgument= ruleSTCallArgument EOF )
            // InternalGlobalConstantsParser.g:1344:2: iv_ruleSTCallArgument= ruleSTCallArgument EOF
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
    // InternalGlobalConstantsParser.g:1350:1: ruleSTCallArgument returns [EObject current=null] : (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) ;
    public final EObject ruleSTCallArgument() throws RecognitionException {
        EObject current = null;

        EObject this_STCallUnnamedArgument_0 = null;

        EObject this_STCallNamedInputArgument_1 = null;

        EObject this_STCallNamedOutputArgument_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1356:2: ( (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument ) )
            // InternalGlobalConstantsParser.g:1357:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            {
            // InternalGlobalConstantsParser.g:1357:2: (this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument | this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument | this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument )
            int alt25=3;
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
                alt25=1;
                }
                break;
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case ColonEqualsSign:
                    {
                    alt25=2;
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
                    alt25=1;
                    }
                    break;
                case EqualsSignGreaterThanSign:
                    {
                    alt25=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 25, 2, input);

                    throw nvae;
                }

                }
                break;
            case NOT:
                {
                int LA25_3 = input.LA(2);

                if ( (LA25_3==RULE_ID) ) {
                    int LA25_6 = input.LA(3);

                    if ( (LA25_6==EOF||LA25_6==AND||LA25_6==MOD||LA25_6==XOR||(LA25_6>=AsteriskAsterisk && LA25_6<=FullStopFullStop)||(LA25_6>=LessThanSignEqualsSign && LA25_6<=LessThanSignGreaterThanSign)||LA25_6==GreaterThanSignEqualsSign||LA25_6==OR||(LA25_6>=Ampersand && LA25_6<=Solidus)||(LA25_6>=LessThanSign && LA25_6<=GreaterThanSign)||LA25_6==LeftSquareBracket) ) {
                        alt25=1;
                    }
                    else if ( (LA25_6==EqualsSignGreaterThanSign) ) {
                        alt25=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 25, 6, input);

                        throw nvae;
                    }
                }
                else if ( (LA25_3==LDATE_AND_TIME||LA25_3==DATE_AND_TIME||LA25_3==LTIME_OF_DAY||LA25_3==TIME_OF_DAY||LA25_3==WSTRING||LA25_3==STRING||LA25_3==DWORD||LA25_3==FALSE||(LA25_3>=LDATE && LA25_3<=LWORD)||(LA25_3>=UDINT && LA25_3<=ULINT)||(LA25_3>=USINT && LA25_3<=WCHAR)||(LA25_3>=BOOL && LA25_3<=BYTE)||(LA25_3>=CHAR && LA25_3<=DINT)||(LA25_3>=LINT && LA25_3<=LTOD)||(LA25_3>=REAL && LA25_3<=SINT)||(LA25_3>=THIS && LA25_3<=TRUE)||LA25_3==UINT||(LA25_3>=WORD && LA25_3<=AND)||(LA25_3>=INT && LA25_3<=NOT)||LA25_3==TOD||LA25_3==XOR||LA25_3==DT||(LA25_3>=LD && LA25_3<=LT)||LA25_3==OR||LA25_3==LeftParenthesis||LA25_3==PlusSign||LA25_3==HyphenMinus||(LA25_3>=D && LA25_3<=T)||(LA25_3>=RULE_NON_DECIMAL && LA25_3<=RULE_DECIMAL)||LA25_3==RULE_STRING) ) {
                    alt25=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 25, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1358:3: this_STCallUnnamedArgument_0= ruleSTCallUnnamedArgument
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
                    // InternalGlobalConstantsParser.g:1367:3: this_STCallNamedInputArgument_1= ruleSTCallNamedInputArgument
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
                    // InternalGlobalConstantsParser.g:1376:3: this_STCallNamedOutputArgument_2= ruleSTCallNamedOutputArgument
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
    // InternalGlobalConstantsParser.g:1388:1: entryRuleSTCallUnnamedArgument returns [EObject current=null] : iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF ;
    public final EObject entryRuleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallUnnamedArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1388:62: (iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF )
            // InternalGlobalConstantsParser.g:1389:2: iv_ruleSTCallUnnamedArgument= ruleSTCallUnnamedArgument EOF
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
    // InternalGlobalConstantsParser.g:1395:1: ruleSTCallUnnamedArgument returns [EObject current=null] : ( (lv_argument_0_0= ruleSTExpression ) ) ;
    public final EObject ruleSTCallUnnamedArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_argument_0_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1401:2: ( ( (lv_argument_0_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1402:2: ( (lv_argument_0_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1402:2: ( (lv_argument_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1403:3: (lv_argument_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1403:3: (lv_argument_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1404:4: lv_argument_0_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1424:1: entryRuleSTCallNamedInputArgument returns [EObject current=null] : iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF ;
    public final EObject entryRuleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedInputArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1424:65: (iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF )
            // InternalGlobalConstantsParser.g:1425:2: iv_ruleSTCallNamedInputArgument= ruleSTCallNamedInputArgument EOF
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
    // InternalGlobalConstantsParser.g:1431:1: ruleSTCallNamedInputArgument returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedInputArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_argument_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1437:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1438:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1438:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1439:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ColonEqualsSign ( (lv_argument_2_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1439:3: ( (otherlv_0= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1440:4: (otherlv_0= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1440:4: (otherlv_0= RULE_ID )
            // InternalGlobalConstantsParser.g:1441:5: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedInputArgumentRule());
              					}
              				
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementCrossReference_0_0());
              				
            }

            }


            }

            otherlv_1=(Token)match(input,ColonEqualsSign,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:1456:3: ( (lv_argument_2_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1457:4: (lv_argument_2_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1457:4: (lv_argument_2_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1458:5: lv_argument_2_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1479:1: entryRuleSTCallNamedOutputArgument returns [EObject current=null] : iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF ;
    public final EObject entryRuleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCallNamedOutputArgument = null;


        try {
            // InternalGlobalConstantsParser.g:1479:66: (iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF )
            // InternalGlobalConstantsParser.g:1480:2: iv_ruleSTCallNamedOutputArgument= ruleSTCallNamedOutputArgument EOF
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
    // InternalGlobalConstantsParser.g:1486:1: ruleSTCallNamedOutputArgument returns [EObject current=null] : ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) ;
    public final EObject ruleSTCallNamedOutputArgument() throws RecognitionException {
        EObject current = null;

        Token lv_not_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_argument_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1492:2: ( ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) ) )
            // InternalGlobalConstantsParser.g:1493:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            {
            // InternalGlobalConstantsParser.g:1493:2: ( ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) ) )
            // InternalGlobalConstantsParser.g:1494:3: ( (lv_not_0_0= NOT ) )? ( (otherlv_1= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_argument_3_0= ruleSTExpression ) )
            {
            // InternalGlobalConstantsParser.g:1494:3: ( (lv_not_0_0= NOT ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==NOT) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1495:4: (lv_not_0_0= NOT )
                    {
                    // InternalGlobalConstantsParser.g:1495:4: (lv_not_0_0= NOT )
                    // InternalGlobalConstantsParser.g:1496:5: lv_not_0_0= NOT
                    {
                    lv_not_0_0=(Token)match(input,NOT,FOLLOW_7); if (state.failed) return current;
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

            // InternalGlobalConstantsParser.g:1508:3: ( (otherlv_1= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1509:4: (otherlv_1= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1509:4: (otherlv_1= RULE_ID )
            // InternalGlobalConstantsParser.g:1510:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1525:3: ( (lv_argument_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1526:4: (lv_argument_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1526:4: (lv_argument_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1527:5: lv_argument_3_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:1548:1: entryRuleSTIfStatement returns [EObject current=null] : iv_ruleSTIfStatement= ruleSTIfStatement EOF ;
    public final EObject entryRuleSTIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTIfStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1548:54: (iv_ruleSTIfStatement= ruleSTIfStatement EOF )
            // InternalGlobalConstantsParser.g:1549:2: iv_ruleSTIfStatement= ruleSTIfStatement EOF
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
    // InternalGlobalConstantsParser.g:1555:1: ruleSTIfStatement returns [EObject current=null] : (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) ;
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
            // InternalGlobalConstantsParser.g:1561:2: ( (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF ) )
            // InternalGlobalConstantsParser.g:1562:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            {
            // InternalGlobalConstantsParser.g:1562:2: (otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF )
            // InternalGlobalConstantsParser.g:1563:3: otherlv_0= IF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ( (lv_elseifs_4_0= ruleSTElseIfPart ) )* ( (lv_else_5_0= ruleSTElsePart ) )? otherlv_6= END_IF
            {
            otherlv_0=(Token)match(input,IF,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1567:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1568:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1568:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1569:5: lv_condition_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_26);
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

            otherlv_2=(Token)match(input,THEN,FOLLOW_27); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTIfStatementAccess().getTHENKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1590:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==LDATE_AND_TIME||LA27_0==DATE_AND_TIME||LA27_0==LTIME_OF_DAY||LA27_0==TIME_OF_DAY||LA27_0==CONTINUE||LA27_0==WSTRING||LA27_0==REPEAT||LA27_0==RETURN||LA27_0==STRING||LA27_0==DWORD||LA27_0==FALSE||(LA27_0>=LDATE && LA27_0<=LWORD)||(LA27_0>=UDINT && LA27_0<=ULINT)||(LA27_0>=USINT && LA27_0<=DINT)||LA27_0==EXIT||(LA27_0>=LINT && LA27_0<=LTOD)||(LA27_0>=REAL && LA27_0<=SINT)||(LA27_0>=THIS && LA27_0<=TRUE)||LA27_0==UINT||(LA27_0>=WORD && LA27_0<=MOD)||LA27_0==TOD||LA27_0==XOR||(LA27_0>=DT && LA27_0<=LT)||LA27_0==OR||LA27_0==LeftParenthesis||LA27_0==PlusSign||LA27_0==HyphenMinus||LA27_0==Semicolon||(LA27_0>=D && LA27_0<=T)||(LA27_0>=RULE_NON_DECIMAL && LA27_0<=RULE_DECIMAL)||(LA27_0>=RULE_ID && LA27_0<=RULE_STRING)) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1591:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1591:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1592:5: lv_statements_3_0= ruleSTStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_27);
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
            	    break loop27;
                }
            } while (true);

            // InternalGlobalConstantsParser.g:1609:3: ( (lv_elseifs_4_0= ruleSTElseIfPart ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==ELSIF) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1610:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    {
            	    // InternalGlobalConstantsParser.g:1610:4: (lv_elseifs_4_0= ruleSTElseIfPart )
            	    // InternalGlobalConstantsParser.g:1611:5: lv_elseifs_4_0= ruleSTElseIfPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_28);
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
            	    break loop28;
                }
            } while (true);

            // InternalGlobalConstantsParser.g:1628:3: ( (lv_else_5_0= ruleSTElsePart ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==ELSE) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1629:4: (lv_else_5_0= ruleSTElsePart )
                    {
                    // InternalGlobalConstantsParser.g:1629:4: (lv_else_5_0= ruleSTElsePart )
                    // InternalGlobalConstantsParser.g:1630:5: lv_else_5_0= ruleSTElsePart
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_29);
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
    // InternalGlobalConstantsParser.g:1655:1: entryRuleSTElseIfPart returns [EObject current=null] : iv_ruleSTElseIfPart= ruleSTElseIfPart EOF ;
    public final EObject entryRuleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElseIfPart = null;


        try {
            // InternalGlobalConstantsParser.g:1655:53: (iv_ruleSTElseIfPart= ruleSTElseIfPart EOF )
            // InternalGlobalConstantsParser.g:1656:2: iv_ruleSTElseIfPart= ruleSTElseIfPart EOF
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
    // InternalGlobalConstantsParser.g:1662:1: ruleSTElseIfPart returns [EObject current=null] : (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElseIfPart() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1668:2: ( (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:1669:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:1669:2: (otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:1670:3: otherlv_0= ELSIF ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleSTStatement ) )*
            {
            otherlv_0=(Token)match(input,ELSIF,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1674:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1675:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1675:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1676:5: lv_condition_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:1697:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==LDATE_AND_TIME||LA30_0==DATE_AND_TIME||LA30_0==LTIME_OF_DAY||LA30_0==TIME_OF_DAY||LA30_0==CONTINUE||LA30_0==WSTRING||LA30_0==REPEAT||LA30_0==RETURN||LA30_0==STRING||LA30_0==DWORD||LA30_0==FALSE||(LA30_0>=LDATE && LA30_0<=LWORD)||(LA30_0>=UDINT && LA30_0<=ULINT)||(LA30_0>=USINT && LA30_0<=DINT)||LA30_0==EXIT||(LA30_0>=LINT && LA30_0<=LTOD)||(LA30_0>=REAL && LA30_0<=SINT)||(LA30_0>=THIS && LA30_0<=TRUE)||LA30_0==UINT||(LA30_0>=WORD && LA30_0<=MOD)||LA30_0==TOD||LA30_0==XOR||(LA30_0>=DT && LA30_0<=LT)||LA30_0==OR||LA30_0==LeftParenthesis||LA30_0==PlusSign||LA30_0==HyphenMinus||LA30_0==Semicolon||(LA30_0>=D && LA30_0<=T)||(LA30_0>=RULE_NON_DECIMAL && LA30_0<=RULE_DECIMAL)||(LA30_0>=RULE_ID && LA30_0<=RULE_STRING)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1698:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1698:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1699:5: lv_statements_3_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElseIfPart"


    // $ANTLR start "entryRuleSTCaseStatement"
    // InternalGlobalConstantsParser.g:1720:1: entryRuleSTCaseStatement returns [EObject current=null] : iv_ruleSTCaseStatement= ruleSTCaseStatement EOF ;
    public final EObject entryRuleSTCaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1720:56: (iv_ruleSTCaseStatement= ruleSTCaseStatement EOF )
            // InternalGlobalConstantsParser.g:1721:2: iv_ruleSTCaseStatement= ruleSTCaseStatement EOF
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
    // InternalGlobalConstantsParser.g:1727:1: ruleSTCaseStatement returns [EObject current=null] : (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) ;
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
            // InternalGlobalConstantsParser.g:1733:2: ( (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE ) )
            // InternalGlobalConstantsParser.g:1734:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            {
            // InternalGlobalConstantsParser.g:1734:2: (otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE )
            // InternalGlobalConstantsParser.g:1735:3: otherlv_0= CASE ( (lv_selector_1_0= ruleSTExpression ) ) otherlv_2= OF ( (lv_cases_3_0= ruleSTCaseCases ) )+ ( (lv_else_4_0= ruleSTElsePart ) )? otherlv_5= END_CASE
            {
            otherlv_0=(Token)match(input,CASE,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1739:3: ( (lv_selector_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1740:4: (lv_selector_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1740:4: (lv_selector_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1741:5: lv_selector_1_0= ruleSTExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_13);
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

            otherlv_2=(Token)match(input,OF,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTCaseStatementAccess().getOFKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1762:3: ( (lv_cases_3_0= ruleSTCaseCases ) )+
            int cnt31=0;
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==LDATE_AND_TIME||LA31_0==DATE_AND_TIME||LA31_0==LTIME_OF_DAY||LA31_0==TIME_OF_DAY||LA31_0==WSTRING||LA31_0==STRING||LA31_0==DWORD||LA31_0==FALSE||(LA31_0>=LDATE && LA31_0<=LWORD)||(LA31_0>=UDINT && LA31_0<=ULINT)||(LA31_0>=USINT && LA31_0<=WCHAR)||(LA31_0>=BOOL && LA31_0<=BYTE)||(LA31_0>=CHAR && LA31_0<=DINT)||(LA31_0>=LINT && LA31_0<=LTOD)||(LA31_0>=REAL && LA31_0<=SINT)||(LA31_0>=THIS && LA31_0<=TRUE)||LA31_0==UINT||(LA31_0>=WORD && LA31_0<=AND)||(LA31_0>=INT && LA31_0<=NOT)||LA31_0==TOD||LA31_0==XOR||LA31_0==DT||(LA31_0>=LD && LA31_0<=LT)||LA31_0==OR||LA31_0==LeftParenthesis||LA31_0==PlusSign||LA31_0==HyphenMinus||(LA31_0>=D && LA31_0<=T)||(LA31_0>=RULE_NON_DECIMAL && LA31_0<=RULE_DECIMAL)||(LA31_0>=RULE_ID && LA31_0<=RULE_STRING)) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1763:4: (lv_cases_3_0= ruleSTCaseCases )
            	    {
            	    // InternalGlobalConstantsParser.g:1763:4: (lv_cases_3_0= ruleSTCaseCases )
            	    // InternalGlobalConstantsParser.g:1764:5: lv_cases_3_0= ruleSTCaseCases
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
            	    if ( cnt31 >= 1 ) break loop31;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(31, input);
                        throw eee;
                }
                cnt31++;
            } while (true);

            // InternalGlobalConstantsParser.g:1781:3: ( (lv_else_4_0= ruleSTElsePart ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==ELSE) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalGlobalConstantsParser.g:1782:4: (lv_else_4_0= ruleSTElsePart )
                    {
                    // InternalGlobalConstantsParser.g:1782:4: (lv_else_4_0= ruleSTElsePart )
                    // InternalGlobalConstantsParser.g:1783:5: lv_else_4_0= ruleSTElsePart
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
    // InternalGlobalConstantsParser.g:1808:1: entryRuleSTCaseCases returns [EObject current=null] : iv_ruleSTCaseCases= ruleSTCaseCases EOF ;
    public final EObject entryRuleSTCaseCases() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTCaseCases = null;


        try {
            // InternalGlobalConstantsParser.g:1808:52: (iv_ruleSTCaseCases= ruleSTCaseCases EOF )
            // InternalGlobalConstantsParser.g:1809:2: iv_ruleSTCaseCases= ruleSTCaseCases EOF
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
    // InternalGlobalConstantsParser.g:1815:1: ruleSTCaseCases returns [EObject current=null] : ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTCaseCases() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_conditions_0_0 = null;

        EObject lv_conditions_2_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1821:2: ( ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:1822:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:1822:2: ( ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:1823:3: ( (lv_conditions_0_0= ruleSTExpression ) ) (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )* otherlv_3= Colon ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            {
            // InternalGlobalConstantsParser.g:1823:3: ( (lv_conditions_0_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1824:4: (lv_conditions_0_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1824:4: (lv_conditions_0_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1825:5: lv_conditions_0_0= ruleSTExpression
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

            // InternalGlobalConstantsParser.g:1842:3: (otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==Comma) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1843:4: otherlv_1= Comma ( (lv_conditions_2_0= ruleSTExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,Comma,FOLLOW_11); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalGlobalConstantsParser.g:1847:4: ( (lv_conditions_2_0= ruleSTExpression ) )
            	    // InternalGlobalConstantsParser.g:1848:5: (lv_conditions_2_0= ruleSTExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:1848:5: (lv_conditions_2_0= ruleSTExpression )
            	    // InternalGlobalConstantsParser.g:1849:6: lv_conditions_2_0= ruleSTExpression
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
            	    break loop33;
                }
            } while (true);

            otherlv_3=(Token)match(input,Colon,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1871:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*
            loop34:
            do {
                int alt34=2;
                alt34 = dfa34.predict(input);
                switch (alt34) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1872:4: ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1876:4: (lv_statements_4_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1877:5: lv_statements_4_0= ruleSTStatement
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
    // $ANTLR end "ruleSTCaseCases"


    // $ANTLR start "entryRuleSTElsePart"
    // InternalGlobalConstantsParser.g:1898:1: entryRuleSTElsePart returns [EObject current=null] : iv_ruleSTElsePart= ruleSTElsePart EOF ;
    public final EObject entryRuleSTElsePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTElsePart = null;


        try {
            // InternalGlobalConstantsParser.g:1898:51: (iv_ruleSTElsePart= ruleSTElsePart EOF )
            // InternalGlobalConstantsParser.g:1899:2: iv_ruleSTElsePart= ruleSTElsePart EOF
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
    // InternalGlobalConstantsParser.g:1905:1: ruleSTElsePart returns [EObject current=null] : ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) ;
    public final EObject ruleSTElsePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:1911:2: ( ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* ) )
            // InternalGlobalConstantsParser.g:1912:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            {
            // InternalGlobalConstantsParser.g:1912:2: ( () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )* )
            // InternalGlobalConstantsParser.g:1913:3: () otherlv_1= ELSE ( (lv_statements_2_0= ruleSTStatement ) )*
            {
            // InternalGlobalConstantsParser.g:1913:3: ()
            // InternalGlobalConstantsParser.g:1914:4: 
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
            // InternalGlobalConstantsParser.g:1924:3: ( (lv_statements_2_0= ruleSTStatement ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==LDATE_AND_TIME||LA35_0==DATE_AND_TIME||LA35_0==LTIME_OF_DAY||LA35_0==TIME_OF_DAY||LA35_0==CONTINUE||LA35_0==WSTRING||LA35_0==REPEAT||LA35_0==RETURN||LA35_0==STRING||LA35_0==DWORD||LA35_0==FALSE||(LA35_0>=LDATE && LA35_0<=LWORD)||(LA35_0>=UDINT && LA35_0<=ULINT)||(LA35_0>=USINT && LA35_0<=DINT)||LA35_0==EXIT||(LA35_0>=LINT && LA35_0<=LTOD)||(LA35_0>=REAL && LA35_0<=SINT)||(LA35_0>=THIS && LA35_0<=TRUE)||LA35_0==UINT||(LA35_0>=WORD && LA35_0<=MOD)||LA35_0==TOD||LA35_0==XOR||(LA35_0>=DT && LA35_0<=LT)||LA35_0==OR||LA35_0==LeftParenthesis||LA35_0==PlusSign||LA35_0==HyphenMinus||LA35_0==Semicolon||(LA35_0>=D && LA35_0<=T)||(LA35_0>=RULE_NON_DECIMAL && LA35_0<=RULE_DECIMAL)||(LA35_0>=RULE_ID && LA35_0<=RULE_STRING)) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:1925:4: (lv_statements_2_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:1925:4: (lv_statements_2_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:1926:5: lv_statements_2_0= ruleSTStatement
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
    // $ANTLR end "ruleSTElsePart"


    // $ANTLR start "entryRuleSTForStatement"
    // InternalGlobalConstantsParser.g:1947:1: entryRuleSTForStatement returns [EObject current=null] : iv_ruleSTForStatement= ruleSTForStatement EOF ;
    public final EObject entryRuleSTForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTForStatement = null;


        try {
            // InternalGlobalConstantsParser.g:1947:55: (iv_ruleSTForStatement= ruleSTForStatement EOF )
            // InternalGlobalConstantsParser.g:1948:2: iv_ruleSTForStatement= ruleSTForStatement EOF
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
    // InternalGlobalConstantsParser.g:1954:1: ruleSTForStatement returns [EObject current=null] : (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) ;
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
            // InternalGlobalConstantsParser.g:1960:2: ( (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR ) )
            // InternalGlobalConstantsParser.g:1961:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            {
            // InternalGlobalConstantsParser.g:1961:2: (otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR )
            // InternalGlobalConstantsParser.g:1962:3: otherlv_0= FOR ( (otherlv_1= RULE_ID ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleSTExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleSTExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0= ruleSTStatement ) )* otherlv_10= END_FOR
            {
            otherlv_0=(Token)match(input,FOR,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:1966:3: ( (otherlv_1= RULE_ID ) )
            // InternalGlobalConstantsParser.g:1967:4: (otherlv_1= RULE_ID )
            {
            // InternalGlobalConstantsParser.g:1967:4: (otherlv_1= RULE_ID )
            // InternalGlobalConstantsParser.g:1968:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTForStatementRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getSTForStatementAccess().getVariableSTVarDeclarationCrossReference_1_0());
              				
            }

            }


            }

            otherlv_2=(Token)match(input,ColonEqualsSign,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:1983:3: ( (lv_from_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:1984:4: (lv_from_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:1984:4: (lv_from_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:1985:5: lv_from_3_0= ruleSTExpression
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

            otherlv_4=(Token)match(input,TO,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getTOKeyword_4());
              		
            }
            // InternalGlobalConstantsParser.g:2006:3: ( (lv_to_5_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2007:4: (lv_to_5_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2007:4: (lv_to_5_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2008:5: lv_to_5_0= ruleSTExpression
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

            // InternalGlobalConstantsParser.g:2025:3: (otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) ) )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==BY) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2026:4: otherlv_6= BY ( (lv_by_7_0= ruleSTExpression ) )
                    {
                    otherlv_6=(Token)match(input,BY,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
                      			
                    }
                    // InternalGlobalConstantsParser.g:2030:4: ( (lv_by_7_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:2031:5: (lv_by_7_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:2031:5: (lv_by_7_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:2032:6: lv_by_7_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:2054:3: ( (lv_statements_9_0= ruleSTStatement ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==LDATE_AND_TIME||LA37_0==DATE_AND_TIME||LA37_0==LTIME_OF_DAY||LA37_0==TIME_OF_DAY||LA37_0==CONTINUE||LA37_0==WSTRING||LA37_0==REPEAT||LA37_0==RETURN||LA37_0==STRING||LA37_0==DWORD||LA37_0==FALSE||(LA37_0>=LDATE && LA37_0<=LWORD)||(LA37_0>=UDINT && LA37_0<=ULINT)||(LA37_0>=USINT && LA37_0<=DINT)||LA37_0==EXIT||(LA37_0>=LINT && LA37_0<=LTOD)||(LA37_0>=REAL && LA37_0<=SINT)||(LA37_0>=THIS && LA37_0<=TRUE)||LA37_0==UINT||(LA37_0>=WORD && LA37_0<=MOD)||LA37_0==TOD||LA37_0==XOR||(LA37_0>=DT && LA37_0<=LT)||LA37_0==OR||LA37_0==LeftParenthesis||LA37_0==PlusSign||LA37_0==HyphenMinus||LA37_0==Semicolon||(LA37_0>=D && LA37_0<=T)||(LA37_0>=RULE_NON_DECIMAL && LA37_0<=RULE_DECIMAL)||(LA37_0>=RULE_ID && LA37_0<=RULE_STRING)) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2055:4: (lv_statements_9_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2055:4: (lv_statements_9_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2056:5: lv_statements_9_0= ruleSTStatement
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
            	    break loop37;
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
    // InternalGlobalConstantsParser.g:2081:1: entryRuleSTWhileStatement returns [EObject current=null] : iv_ruleSTWhileStatement= ruleSTWhileStatement EOF ;
    public final EObject entryRuleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTWhileStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2081:57: (iv_ruleSTWhileStatement= ruleSTWhileStatement EOF )
            // InternalGlobalConstantsParser.g:2082:2: iv_ruleSTWhileStatement= ruleSTWhileStatement EOF
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
    // InternalGlobalConstantsParser.g:2088:1: ruleSTWhileStatement returns [EObject current=null] : (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) ;
    public final EObject ruleSTWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condition_1_0 = null;

        EObject lv_statements_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2094:2: ( (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE ) )
            // InternalGlobalConstantsParser.g:2095:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            {
            // InternalGlobalConstantsParser.g:2095:2: (otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE )
            // InternalGlobalConstantsParser.g:2096:3: otherlv_0= WHILE ( (lv_condition_1_0= ruleSTExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleSTStatement ) )* otherlv_4= END_WHILE
            {
            otherlv_0=(Token)match(input,WHILE,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2100:3: ( (lv_condition_1_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2101:4: (lv_condition_1_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2101:4: (lv_condition_1_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2102:5: lv_condition_1_0= ruleSTExpression
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
            // InternalGlobalConstantsParser.g:2123:3: ( (lv_statements_3_0= ruleSTStatement ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==LDATE_AND_TIME||LA38_0==DATE_AND_TIME||LA38_0==LTIME_OF_DAY||LA38_0==TIME_OF_DAY||LA38_0==CONTINUE||LA38_0==WSTRING||LA38_0==REPEAT||LA38_0==RETURN||LA38_0==STRING||LA38_0==DWORD||LA38_0==FALSE||(LA38_0>=LDATE && LA38_0<=LWORD)||(LA38_0>=UDINT && LA38_0<=ULINT)||(LA38_0>=USINT && LA38_0<=DINT)||LA38_0==EXIT||(LA38_0>=LINT && LA38_0<=LTOD)||(LA38_0>=REAL && LA38_0<=SINT)||(LA38_0>=THIS && LA38_0<=TRUE)||LA38_0==UINT||(LA38_0>=WORD && LA38_0<=MOD)||LA38_0==TOD||LA38_0==XOR||(LA38_0>=DT && LA38_0<=LT)||LA38_0==OR||LA38_0==LeftParenthesis||LA38_0==PlusSign||LA38_0==HyphenMinus||LA38_0==Semicolon||(LA38_0>=D && LA38_0<=T)||(LA38_0>=RULE_NON_DECIMAL && LA38_0<=RULE_DECIMAL)||(LA38_0>=RULE_ID && LA38_0<=RULE_STRING)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2124:4: (lv_statements_3_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2124:4: (lv_statements_3_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2125:5: lv_statements_3_0= ruleSTStatement
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
            	    break loop38;
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
    // InternalGlobalConstantsParser.g:2150:1: entryRuleSTRepeatStatement returns [EObject current=null] : iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF ;
    public final EObject entryRuleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTRepeatStatement = null;


        try {
            // InternalGlobalConstantsParser.g:2150:58: (iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF )
            // InternalGlobalConstantsParser.g:2151:2: iv_ruleSTRepeatStatement= ruleSTRepeatStatement EOF
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
    // InternalGlobalConstantsParser.g:2157:1: ruleSTRepeatStatement returns [EObject current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) ;
    public final EObject ruleSTRepeatStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_statements_1_0 = null;

        EObject lv_condition_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2163:2: ( (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT ) )
            // InternalGlobalConstantsParser.g:2164:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            {
            // InternalGlobalConstantsParser.g:2164:2: (otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT )
            // InternalGlobalConstantsParser.g:2165:3: otherlv_0= REPEAT ( (lv_statements_1_0= ruleSTStatement ) )* otherlv_2= UNTIL ( (lv_condition_3_0= ruleSTExpression ) ) otherlv_4= END_REPEAT
            {
            otherlv_0=(Token)match(input,REPEAT,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
              		
            }
            // InternalGlobalConstantsParser.g:2169:3: ( (lv_statements_1_0= ruleSTStatement ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==LDATE_AND_TIME||LA39_0==DATE_AND_TIME||LA39_0==LTIME_OF_DAY||LA39_0==TIME_OF_DAY||LA39_0==CONTINUE||LA39_0==WSTRING||LA39_0==REPEAT||LA39_0==RETURN||LA39_0==STRING||LA39_0==DWORD||LA39_0==FALSE||(LA39_0>=LDATE && LA39_0<=LWORD)||(LA39_0>=UDINT && LA39_0<=ULINT)||(LA39_0>=USINT && LA39_0<=DINT)||LA39_0==EXIT||(LA39_0>=LINT && LA39_0<=LTOD)||(LA39_0>=REAL && LA39_0<=SINT)||(LA39_0>=THIS && LA39_0<=TRUE)||LA39_0==UINT||(LA39_0>=WORD && LA39_0<=MOD)||LA39_0==TOD||LA39_0==XOR||(LA39_0>=DT && LA39_0<=LT)||LA39_0==OR||LA39_0==LeftParenthesis||LA39_0==PlusSign||LA39_0==HyphenMinus||LA39_0==Semicolon||(LA39_0>=D && LA39_0<=T)||(LA39_0>=RULE_NON_DECIMAL && LA39_0<=RULE_DECIMAL)||(LA39_0>=RULE_ID && LA39_0<=RULE_STRING)) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2170:4: (lv_statements_1_0= ruleSTStatement )
            	    {
            	    // InternalGlobalConstantsParser.g:2170:4: (lv_statements_1_0= ruleSTStatement )
            	    // InternalGlobalConstantsParser.g:2171:5: lv_statements_1_0= ruleSTStatement
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
            	    break loop39;
                }
            } while (true);

            otherlv_2=(Token)match(input,UNTIL,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
              		
            }
            // InternalGlobalConstantsParser.g:2192:3: ( (lv_condition_3_0= ruleSTExpression ) )
            // InternalGlobalConstantsParser.g:2193:4: (lv_condition_3_0= ruleSTExpression )
            {
            // InternalGlobalConstantsParser.g:2193:4: (lv_condition_3_0= ruleSTExpression )
            // InternalGlobalConstantsParser.g:2194:5: lv_condition_3_0= ruleSTExpression
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
    // InternalGlobalConstantsParser.g:2219:1: entryRuleSTExpression returns [EObject current=null] : iv_ruleSTExpression= ruleSTExpression EOF ;
    public final EObject entryRuleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2219:53: (iv_ruleSTExpression= ruleSTExpression EOF )
            // InternalGlobalConstantsParser.g:2220:2: iv_ruleSTExpression= ruleSTExpression EOF
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
    // InternalGlobalConstantsParser.g:2226:1: ruleSTExpression returns [EObject current=null] : this_STSubrangeExpression_0= ruleSTSubrangeExpression ;
    public final EObject ruleSTExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STSubrangeExpression_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2232:2: (this_STSubrangeExpression_0= ruleSTSubrangeExpression )
            // InternalGlobalConstantsParser.g:2233:2: this_STSubrangeExpression_0= ruleSTSubrangeExpression
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
    // InternalGlobalConstantsParser.g:2244:1: entryRuleSTSubrangeExpression returns [EObject current=null] : iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF ;
    public final EObject entryRuleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTSubrangeExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2244:61: (iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF )
            // InternalGlobalConstantsParser.g:2245:2: iv_ruleSTSubrangeExpression= ruleSTSubrangeExpression EOF
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
    // InternalGlobalConstantsParser.g:2251:1: ruleSTSubrangeExpression returns [EObject current=null] : (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) ;
    public final EObject ruleSTSubrangeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STOrExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2257:2: ( (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2258:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2258:2: (this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2259:3: this_STOrExpression_0= ruleSTOrExpression ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2267:3: ( ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==FullStopFullStop) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2268:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) ) ( (lv_right_3_0= ruleSTOrExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2268:4: ( () ( (lv_op_2_0= ruleSubrangeOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2269:5: () ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2269:5: ()
            	    // InternalGlobalConstantsParser.g:2270:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2276:5: ( (lv_op_2_0= ruleSubrangeOperator ) )
            	    // InternalGlobalConstantsParser.g:2277:6: (lv_op_2_0= ruleSubrangeOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2277:6: (lv_op_2_0= ruleSubrangeOperator )
            	    // InternalGlobalConstantsParser.g:2278:7: lv_op_2_0= ruleSubrangeOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2296:4: ( (lv_right_3_0= ruleSTOrExpression ) )
            	    // InternalGlobalConstantsParser.g:2297:5: (lv_right_3_0= ruleSTOrExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2297:5: (lv_right_3_0= ruleSTOrExpression )
            	    // InternalGlobalConstantsParser.g:2298:6: lv_right_3_0= ruleSTOrExpression
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
    // $ANTLR end "ruleSTSubrangeExpression"


    // $ANTLR start "entryRuleSTOrExpression"
    // InternalGlobalConstantsParser.g:2320:1: entryRuleSTOrExpression returns [EObject current=null] : iv_ruleSTOrExpression= ruleSTOrExpression EOF ;
    public final EObject entryRuleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTOrExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2320:55: (iv_ruleSTOrExpression= ruleSTOrExpression EOF )
            // InternalGlobalConstantsParser.g:2321:2: iv_ruleSTOrExpression= ruleSTOrExpression EOF
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
    // InternalGlobalConstantsParser.g:2327:1: ruleSTOrExpression returns [EObject current=null] : (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) ;
    public final EObject ruleSTOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STXorExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2333:2: ( (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2334:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2334:2: (this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2335:3: this_STXorExpression_0= ruleSTXorExpression ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2343:3: ( ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==OR) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2344:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) ) ( (lv_right_3_0= ruleSTXorExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2344:4: ( () ( (lv_op_2_0= ruleOrOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2345:5: () ( (lv_op_2_0= ruleOrOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2345:5: ()
            	    // InternalGlobalConstantsParser.g:2346:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2352:5: ( (lv_op_2_0= ruleOrOperator ) )
            	    // InternalGlobalConstantsParser.g:2353:6: (lv_op_2_0= ruleOrOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2353:6: (lv_op_2_0= ruleOrOperator )
            	    // InternalGlobalConstantsParser.g:2354:7: lv_op_2_0= ruleOrOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2372:4: ( (lv_right_3_0= ruleSTXorExpression ) )
            	    // InternalGlobalConstantsParser.g:2373:5: (lv_right_3_0= ruleSTXorExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2373:5: (lv_right_3_0= ruleSTXorExpression )
            	    // InternalGlobalConstantsParser.g:2374:6: lv_right_3_0= ruleSTXorExpression
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
    // $ANTLR end "ruleSTOrExpression"


    // $ANTLR start "entryRuleSTXorExpression"
    // InternalGlobalConstantsParser.g:2396:1: entryRuleSTXorExpression returns [EObject current=null] : iv_ruleSTXorExpression= ruleSTXorExpression EOF ;
    public final EObject entryRuleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTXorExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2396:56: (iv_ruleSTXorExpression= ruleSTXorExpression EOF )
            // InternalGlobalConstantsParser.g:2397:2: iv_ruleSTXorExpression= ruleSTXorExpression EOF
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
    // InternalGlobalConstantsParser.g:2403:1: ruleSTXorExpression returns [EObject current=null] : (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) ;
    public final EObject ruleSTXorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAndExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2409:2: ( (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2410:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2410:2: (this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2411:3: this_STAndExpression_0= ruleSTAndExpression ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2419:3: ( ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==XOR) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2420:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) ) ( (lv_right_3_0= ruleSTAndExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2420:4: ( () ( (lv_op_2_0= ruleXorOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2421:5: () ( (lv_op_2_0= ruleXorOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2421:5: ()
            	    // InternalGlobalConstantsParser.g:2422:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2428:5: ( (lv_op_2_0= ruleXorOperator ) )
            	    // InternalGlobalConstantsParser.g:2429:6: (lv_op_2_0= ruleXorOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2429:6: (lv_op_2_0= ruleXorOperator )
            	    // InternalGlobalConstantsParser.g:2430:7: lv_op_2_0= ruleXorOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2448:4: ( (lv_right_3_0= ruleSTAndExpression ) )
            	    // InternalGlobalConstantsParser.g:2449:5: (lv_right_3_0= ruleSTAndExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2449:5: (lv_right_3_0= ruleSTAndExpression )
            	    // InternalGlobalConstantsParser.g:2450:6: lv_right_3_0= ruleSTAndExpression
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
    // $ANTLR end "ruleSTXorExpression"


    // $ANTLR start "entryRuleSTAndExpression"
    // InternalGlobalConstantsParser.g:2472:1: entryRuleSTAndExpression returns [EObject current=null] : iv_ruleSTAndExpression= ruleSTAndExpression EOF ;
    public final EObject entryRuleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAndExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2472:56: (iv_ruleSTAndExpression= ruleSTAndExpression EOF )
            // InternalGlobalConstantsParser.g:2473:2: iv_ruleSTAndExpression= ruleSTAndExpression EOF
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
    // InternalGlobalConstantsParser.g:2479:1: ruleSTAndExpression returns [EObject current=null] : (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) ;
    public final EObject ruleSTAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STEqualityExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2485:2: ( (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2486:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2486:2: (this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2487:3: this_STEqualityExpression_0= ruleSTEqualityExpression ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2495:3: ( ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==AND||LA43_0==Ampersand) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2496:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) ) ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2496:4: ( () ( (lv_op_2_0= ruleAndOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2497:5: () ( (lv_op_2_0= ruleAndOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2497:5: ()
            	    // InternalGlobalConstantsParser.g:2498:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2504:5: ( (lv_op_2_0= ruleAndOperator ) )
            	    // InternalGlobalConstantsParser.g:2505:6: (lv_op_2_0= ruleAndOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2505:6: (lv_op_2_0= ruleAndOperator )
            	    // InternalGlobalConstantsParser.g:2506:7: lv_op_2_0= ruleAndOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2524:4: ( (lv_right_3_0= ruleSTEqualityExpression ) )
            	    // InternalGlobalConstantsParser.g:2525:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2525:5: (lv_right_3_0= ruleSTEqualityExpression )
            	    // InternalGlobalConstantsParser.g:2526:6: lv_right_3_0= ruleSTEqualityExpression
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
    // InternalGlobalConstantsParser.g:2548:1: entryRuleSTEqualityExpression returns [EObject current=null] : iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF ;
    public final EObject entryRuleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTEqualityExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2548:61: (iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF )
            // InternalGlobalConstantsParser.g:2549:2: iv_ruleSTEqualityExpression= ruleSTEqualityExpression EOF
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
    // InternalGlobalConstantsParser.g:2555:1: ruleSTEqualityExpression returns [EObject current=null] : (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) ;
    public final EObject ruleSTEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STComparisonExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2561:2: ( (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2562:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2562:2: (this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2563:3: this_STComparisonExpression_0= ruleSTComparisonExpression ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2571:3: ( ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==LessThanSignGreaterThanSign||LA44_0==EqualsSign) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2572:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) ) ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2572:4: ( () ( (lv_op_2_0= ruleEqualityOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2573:5: () ( (lv_op_2_0= ruleEqualityOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2573:5: ()
            	    // InternalGlobalConstantsParser.g:2574:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2580:5: ( (lv_op_2_0= ruleEqualityOperator ) )
            	    // InternalGlobalConstantsParser.g:2581:6: (lv_op_2_0= ruleEqualityOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2581:6: (lv_op_2_0= ruleEqualityOperator )
            	    // InternalGlobalConstantsParser.g:2582:7: lv_op_2_0= ruleEqualityOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2600:4: ( (lv_right_3_0= ruleSTComparisonExpression ) )
            	    // InternalGlobalConstantsParser.g:2601:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2601:5: (lv_right_3_0= ruleSTComparisonExpression )
            	    // InternalGlobalConstantsParser.g:2602:6: lv_right_3_0= ruleSTComparisonExpression
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
    // $ANTLR end "ruleSTEqualityExpression"


    // $ANTLR start "entryRuleSTComparisonExpression"
    // InternalGlobalConstantsParser.g:2624:1: entryRuleSTComparisonExpression returns [EObject current=null] : iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF ;
    public final EObject entryRuleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTComparisonExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2624:63: (iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF )
            // InternalGlobalConstantsParser.g:2625:2: iv_ruleSTComparisonExpression= ruleSTComparisonExpression EOF
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
    // InternalGlobalConstantsParser.g:2631:1: ruleSTComparisonExpression returns [EObject current=null] : (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) ;
    public final EObject ruleSTComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAddSubExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2637:2: ( (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2638:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2638:2: (this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2639:3: this_STAddSubExpression_0= ruleSTAddSubExpression ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2647:3: ( ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==LessThanSignEqualsSign||LA45_0==GreaterThanSignEqualsSign||LA45_0==LessThanSign||LA45_0==GreaterThanSign) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2648:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) ) ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2648:4: ( () ( (lv_op_2_0= ruleCompareOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2649:5: () ( (lv_op_2_0= ruleCompareOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2649:5: ()
            	    // InternalGlobalConstantsParser.g:2650:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2656:5: ( (lv_op_2_0= ruleCompareOperator ) )
            	    // InternalGlobalConstantsParser.g:2657:6: (lv_op_2_0= ruleCompareOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2657:6: (lv_op_2_0= ruleCompareOperator )
            	    // InternalGlobalConstantsParser.g:2658:7: lv_op_2_0= ruleCompareOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2676:4: ( (lv_right_3_0= ruleSTAddSubExpression ) )
            	    // InternalGlobalConstantsParser.g:2677:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2677:5: (lv_right_3_0= ruleSTAddSubExpression )
            	    // InternalGlobalConstantsParser.g:2678:6: lv_right_3_0= ruleSTAddSubExpression
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
    // $ANTLR end "ruleSTComparisonExpression"


    // $ANTLR start "entryRuleSTAddSubExpression"
    // InternalGlobalConstantsParser.g:2700:1: entryRuleSTAddSubExpression returns [EObject current=null] : iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF ;
    public final EObject entryRuleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAddSubExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2700:59: (iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF )
            // InternalGlobalConstantsParser.g:2701:2: iv_ruleSTAddSubExpression= ruleSTAddSubExpression EOF
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
    // InternalGlobalConstantsParser.g:2707:1: ruleSTAddSubExpression returns [EObject current=null] : (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) ;
    public final EObject ruleSTAddSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STMulDivModExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2713:2: ( (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2714:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2714:2: (this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2715:3: this_STMulDivModExpression_0= ruleSTMulDivModExpression ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2723:3: ( ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==PlusSign||LA46_0==HyphenMinus) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2724:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) ) ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2724:4: ( () ( (lv_op_2_0= ruleAddSubOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2725:5: () ( (lv_op_2_0= ruleAddSubOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2725:5: ()
            	    // InternalGlobalConstantsParser.g:2726:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2732:5: ( (lv_op_2_0= ruleAddSubOperator ) )
            	    // InternalGlobalConstantsParser.g:2733:6: (lv_op_2_0= ruleAddSubOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2733:6: (lv_op_2_0= ruleAddSubOperator )
            	    // InternalGlobalConstantsParser.g:2734:7: lv_op_2_0= ruleAddSubOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2752:4: ( (lv_right_3_0= ruleSTMulDivModExpression ) )
            	    // InternalGlobalConstantsParser.g:2753:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2753:5: (lv_right_3_0= ruleSTMulDivModExpression )
            	    // InternalGlobalConstantsParser.g:2754:6: lv_right_3_0= ruleSTMulDivModExpression
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
    // $ANTLR end "ruleSTAddSubExpression"


    // $ANTLR start "entryRuleSTMulDivModExpression"
    // InternalGlobalConstantsParser.g:2776:1: entryRuleSTMulDivModExpression returns [EObject current=null] : iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF ;
    public final EObject entryRuleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMulDivModExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2776:62: (iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF )
            // InternalGlobalConstantsParser.g:2777:2: iv_ruleSTMulDivModExpression= ruleSTMulDivModExpression EOF
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
    // InternalGlobalConstantsParser.g:2783:1: ruleSTMulDivModExpression returns [EObject current=null] : (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) ;
    public final EObject ruleSTMulDivModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STPowerExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2789:2: ( (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2790:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2790:2: (this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2791:3: this_STPowerExpression_0= ruleSTPowerExpression ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2799:3: ( ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==MOD||LA47_0==Asterisk||LA47_0==Solidus) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2800:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) ) ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2800:4: ( () ( (lv_op_2_0= ruleMulDivModOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2801:5: () ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2801:5: ()
            	    // InternalGlobalConstantsParser.g:2802:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2808:5: ( (lv_op_2_0= ruleMulDivModOperator ) )
            	    // InternalGlobalConstantsParser.g:2809:6: (lv_op_2_0= ruleMulDivModOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2809:6: (lv_op_2_0= ruleMulDivModOperator )
            	    // InternalGlobalConstantsParser.g:2810:7: lv_op_2_0= ruleMulDivModOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2828:4: ( (lv_right_3_0= ruleSTPowerExpression ) )
            	    // InternalGlobalConstantsParser.g:2829:5: (lv_right_3_0= ruleSTPowerExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2829:5: (lv_right_3_0= ruleSTPowerExpression )
            	    // InternalGlobalConstantsParser.g:2830:6: lv_right_3_0= ruleSTPowerExpression
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
    // $ANTLR end "ruleSTMulDivModExpression"


    // $ANTLR start "entryRuleSTPowerExpression"
    // InternalGlobalConstantsParser.g:2852:1: entryRuleSTPowerExpression returns [EObject current=null] : iv_ruleSTPowerExpression= ruleSTPowerExpression EOF ;
    public final EObject entryRuleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPowerExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2852:58: (iv_ruleSTPowerExpression= ruleSTPowerExpression EOF )
            // InternalGlobalConstantsParser.g:2853:2: iv_ruleSTPowerExpression= ruleSTPowerExpression EOF
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
    // InternalGlobalConstantsParser.g:2859:1: ruleSTPowerExpression returns [EObject current=null] : (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) ;
    public final EObject ruleSTPowerExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STUnaryExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2865:2: ( (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* ) )
            // InternalGlobalConstantsParser.g:2866:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            {
            // InternalGlobalConstantsParser.g:2866:2: (this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )* )
            // InternalGlobalConstantsParser.g:2867:3: this_STUnaryExpression_0= ruleSTUnaryExpression ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
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
            // InternalGlobalConstantsParser.g:2875:3: ( ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==AsteriskAsterisk) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:2876:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) ) ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2876:4: ( () ( (lv_op_2_0= rulePowerOperator ) ) )
            	    // InternalGlobalConstantsParser.g:2877:5: () ( (lv_op_2_0= rulePowerOperator ) )
            	    {
            	    // InternalGlobalConstantsParser.g:2877:5: ()
            	    // InternalGlobalConstantsParser.g:2878:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGlobalConstantsParser.g:2884:5: ( (lv_op_2_0= rulePowerOperator ) )
            	    // InternalGlobalConstantsParser.g:2885:6: (lv_op_2_0= rulePowerOperator )
            	    {
            	    // InternalGlobalConstantsParser.g:2885:6: (lv_op_2_0= rulePowerOperator )
            	    // InternalGlobalConstantsParser.g:2886:7: lv_op_2_0= rulePowerOperator
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalGlobalConstantsParser.g:2904:4: ( (lv_right_3_0= ruleSTUnaryExpression ) )
            	    // InternalGlobalConstantsParser.g:2905:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:2905:5: (lv_right_3_0= ruleSTUnaryExpression )
            	    // InternalGlobalConstantsParser.g:2906:6: lv_right_3_0= ruleSTUnaryExpression
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
    // $ANTLR end "ruleSTPowerExpression"


    // $ANTLR start "entryRuleSTUnaryExpression"
    // InternalGlobalConstantsParser.g:2928:1: entryRuleSTUnaryExpression returns [EObject current=null] : iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF ;
    public final EObject entryRuleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTUnaryExpression = null;


        try {
            // InternalGlobalConstantsParser.g:2928:58: (iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF )
            // InternalGlobalConstantsParser.g:2929:2: iv_ruleSTUnaryExpression= ruleSTUnaryExpression EOF
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
    // InternalGlobalConstantsParser.g:2935:1: ruleSTUnaryExpression returns [EObject current=null] : ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) ;
    public final EObject ruleSTUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_STAccessExpression_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_expression_3_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:2941:2: ( ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) ) )
            // InternalGlobalConstantsParser.g:2942:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            {
            // InternalGlobalConstantsParser.g:2942:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )
            int alt49=2;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // InternalGlobalConstantsParser.g:2943:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    {
                    // InternalGlobalConstantsParser.g:2943:3: ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression )
                    // InternalGlobalConstantsParser.g:2944:4: ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression
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
                    // InternalGlobalConstantsParser.g:2955:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    {
                    // InternalGlobalConstantsParser.g:2955:3: ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) )
                    // InternalGlobalConstantsParser.g:2956:4: () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    {
                    // InternalGlobalConstantsParser.g:2956:4: ()
                    // InternalGlobalConstantsParser.g:2957:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGlobalConstantsParser.g:2963:4: ( (lv_op_2_0= ruleUnaryOperator ) )
                    // InternalGlobalConstantsParser.g:2964:5: (lv_op_2_0= ruleUnaryOperator )
                    {
                    // InternalGlobalConstantsParser.g:2964:5: (lv_op_2_0= ruleUnaryOperator )
                    // InternalGlobalConstantsParser.g:2965:6: lv_op_2_0= ruleUnaryOperator
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_11);
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

                    // InternalGlobalConstantsParser.g:2982:4: ( (lv_expression_3_0= ruleSTUnaryExpression ) )
                    // InternalGlobalConstantsParser.g:2983:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    {
                    // InternalGlobalConstantsParser.g:2983:5: (lv_expression_3_0= ruleSTUnaryExpression )
                    // InternalGlobalConstantsParser.g:2984:6: lv_expression_3_0= ruleSTUnaryExpression
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
    // InternalGlobalConstantsParser.g:3006:1: entryRuleSTAccessExpression returns [EObject current=null] : iv_ruleSTAccessExpression= ruleSTAccessExpression EOF ;
    public final EObject entryRuleSTAccessExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTAccessExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3006:59: (iv_ruleSTAccessExpression= ruleSTAccessExpression EOF )
            // InternalGlobalConstantsParser.g:3007:2: iv_ruleSTAccessExpression= ruleSTAccessExpression EOF
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
    // InternalGlobalConstantsParser.g:3013:1: ruleSTAccessExpression returns [EObject current=null] : (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) ;
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
            // InternalGlobalConstantsParser.g:3019:2: ( (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* ) )
            // InternalGlobalConstantsParser.g:3020:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            {
            // InternalGlobalConstantsParser.g:3020:2: (this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )* )
            // InternalGlobalConstantsParser.g:3021:3: this_STPrimaryExpression_0= ruleSTPrimaryExpression ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
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
            // InternalGlobalConstantsParser.g:3029:3: ( ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) ) | ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket ) )*
            loop52:
            do {
                int alt52=3;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==FullStop) ) {
                    alt52=1;
                }
                else if ( (LA52_0==LeftSquareBracket) ) {
                    alt52=2;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalGlobalConstantsParser.g:3030:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3030:4: ( () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) ) )
            	    // InternalGlobalConstantsParser.g:3031:5: () otherlv_2= FullStop ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3031:5: ()
            	    // InternalGlobalConstantsParser.g:3032:6: 
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
            	    // InternalGlobalConstantsParser.g:3042:5: ( ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) ) )
            	    // InternalGlobalConstantsParser.g:3043:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    {
            	    // InternalGlobalConstantsParser.g:3043:6: ( (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression ) )
            	    // InternalGlobalConstantsParser.g:3044:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3044:7: (lv_member_3_1= ruleSTFeatureExpression | lv_member_3_2= ruleSTMultibitPartialExpression )
            	    int alt50=2;
            	    int LA50_0 = input.LA(1);

            	    if ( (LA50_0==AND||LA50_0==MOD||LA50_0==XOR||LA50_0==DT||(LA50_0>=LD && LA50_0<=LT)||LA50_0==OR||LA50_0==D||LA50_0==RULE_ID) ) {
            	        alt50=1;
            	    }
            	    else if ( ((LA50_0>=B && LA50_0<=X)||LA50_0==LeftParenthesis||LA50_0==RULE_INT) ) {
            	        alt50=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 50, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt50) {
            	        case 1 :
            	            // InternalGlobalConstantsParser.g:3045:8: lv_member_3_1= ruleSTFeatureExpression
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
            	            // InternalGlobalConstantsParser.g:3061:8: lv_member_3_2= ruleSTMultibitPartialExpression
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
            	    // InternalGlobalConstantsParser.g:3081:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    {
            	    // InternalGlobalConstantsParser.g:3081:4: ( () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket )
            	    // InternalGlobalConstantsParser.g:3082:5: () otherlv_5= LeftSquareBracket ( (lv_index_6_0= ruleSTExpression ) ) (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )* otherlv_9= RightSquareBracket
            	    {
            	    // InternalGlobalConstantsParser.g:3082:5: ()
            	    // InternalGlobalConstantsParser.g:3083:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    otherlv_5=(Token)match(input,LeftSquareBracket,FOLLOW_11); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_5, grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1());
            	      				
            	    }
            	    // InternalGlobalConstantsParser.g:3093:5: ( (lv_index_6_0= ruleSTExpression ) )
            	    // InternalGlobalConstantsParser.g:3094:6: (lv_index_6_0= ruleSTExpression )
            	    {
            	    // InternalGlobalConstantsParser.g:3094:6: (lv_index_6_0= ruleSTExpression )
            	    // InternalGlobalConstantsParser.g:3095:7: lv_index_6_0= ruleSTExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_12);
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

            	    // InternalGlobalConstantsParser.g:3112:5: (otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) ) )*
            	    loop51:
            	    do {
            	        int alt51=2;
            	        int LA51_0 = input.LA(1);

            	        if ( (LA51_0==Comma) ) {
            	            alt51=1;
            	        }


            	        switch (alt51) {
            	    	case 1 :
            	    	    // InternalGlobalConstantsParser.g:3113:6: otherlv_7= Comma ( (lv_index_8_0= ruleSTExpression ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,Comma,FOLLOW_11); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
            	    	      					
            	    	    }
            	    	    // InternalGlobalConstantsParser.g:3117:6: ( (lv_index_8_0= ruleSTExpression ) )
            	    	    // InternalGlobalConstantsParser.g:3118:7: (lv_index_8_0= ruleSTExpression )
            	    	    {
            	    	    // InternalGlobalConstantsParser.g:3118:7: (lv_index_8_0= ruleSTExpression )
            	    	    // InternalGlobalConstantsParser.g:3119:8: lv_index_8_0= ruleSTExpression
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0());
            	    	      							
            	    	    }
            	    	    pushFollow(FOLLOW_12);
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
            	    	    break loop51;
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
    // $ANTLR end "ruleSTAccessExpression"


    // $ANTLR start "entryRuleSTPrimaryExpression"
    // InternalGlobalConstantsParser.g:3147:1: entryRuleSTPrimaryExpression returns [EObject current=null] : iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF ;
    public final EObject entryRuleSTPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTPrimaryExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3147:60: (iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF )
            // InternalGlobalConstantsParser.g:3148:2: iv_ruleSTPrimaryExpression= ruleSTPrimaryExpression EOF
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
    // InternalGlobalConstantsParser.g:3154:1: ruleSTPrimaryExpression returns [EObject current=null] : ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression | this_STLiteralExpressions_5= ruleSTLiteralExpressions ) ;
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
            // InternalGlobalConstantsParser.g:3160:2: ( ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression | this_STLiteralExpressions_5= ruleSTLiteralExpressions ) )
            // InternalGlobalConstantsParser.g:3161:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression | this_STLiteralExpressions_5= ruleSTLiteralExpressions )
            {
            // InternalGlobalConstantsParser.g:3161:2: ( (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis ) | this_STFeatureExpression_3= ruleSTFeatureExpression | this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression | this_STLiteralExpressions_5= ruleSTLiteralExpressions )
            int alt53=4;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt53=1;
                }
                break;
            case AND:
            case MOD:
            case XOR:
            case OR:
            case RULE_ID:
                {
                alt53=2;
                }
                break;
            case LT:
                {
                int LA53_3 = input.LA(2);

                if ( (LA53_3==EOF||LA53_3==END_REPEAT||LA53_3==THEN||LA53_3==AND||LA53_3==MOD||LA53_3==XOR||(LA53_3>=AsteriskAsterisk && LA53_3<=LessThanSignGreaterThanSign)||LA53_3==GreaterThanSignEqualsSign||(LA53_3>=BY && LA53_3<=DO)||LA53_3==OF||(LA53_3>=OR && LA53_3<=TO)||(LA53_3>=Ampersand && LA53_3<=GreaterThanSign)||(LA53_3>=LeftSquareBracket && LA53_3<=RightSquareBracket)) ) {
                    alt53=2;
                }
                else if ( (LA53_3==NumberSign) ) {
                    alt53=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 53, 3, input);

                    throw nvae;
                }
                }
                break;
            case D:
                {
                int LA53_4 = input.LA(2);

                if ( (LA53_4==NumberSign) ) {
                    alt53=4;
                }
                else if ( (LA53_4==EOF||LA53_4==END_REPEAT||LA53_4==THEN||LA53_4==AND||LA53_4==MOD||LA53_4==XOR||(LA53_4>=AsteriskAsterisk && LA53_4<=LessThanSignGreaterThanSign)||LA53_4==GreaterThanSignEqualsSign||(LA53_4>=BY && LA53_4<=DO)||LA53_4==OF||(LA53_4>=OR && LA53_4<=TO)||(LA53_4>=Ampersand && LA53_4<=GreaterThanSign)||(LA53_4>=LeftSquareBracket && LA53_4<=RightSquareBracket)) ) {
                    alt53=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 53, 4, input);

                    throw nvae;
                }
                }
                break;
            case DT:
                {
                int LA53_5 = input.LA(2);

                if ( (LA53_5==EOF||LA53_5==END_REPEAT||LA53_5==THEN||LA53_5==AND||LA53_5==MOD||LA53_5==XOR||(LA53_5>=AsteriskAsterisk && LA53_5<=LessThanSignGreaterThanSign)||LA53_5==GreaterThanSignEqualsSign||(LA53_5>=BY && LA53_5<=DO)||LA53_5==OF||(LA53_5>=OR && LA53_5<=TO)||(LA53_5>=Ampersand && LA53_5<=GreaterThanSign)||(LA53_5>=LeftSquareBracket && LA53_5<=RightSquareBracket)) ) {
                    alt53=2;
                }
                else if ( (LA53_5==NumberSign) ) {
                    alt53=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 53, 5, input);

                    throw nvae;
                }
                }
                break;
            case LD:
                {
                int LA53_6 = input.LA(2);

                if ( (LA53_6==EOF||LA53_6==END_REPEAT||LA53_6==THEN||LA53_6==AND||LA53_6==MOD||LA53_6==XOR||(LA53_6>=AsteriskAsterisk && LA53_6<=LessThanSignGreaterThanSign)||LA53_6==GreaterThanSignEqualsSign||(LA53_6>=BY && LA53_6<=DO)||LA53_6==OF||(LA53_6>=OR && LA53_6<=TO)||(LA53_6>=Ampersand && LA53_6<=GreaterThanSign)||(LA53_6>=LeftSquareBracket && LA53_6<=RightSquareBracket)) ) {
                    alt53=2;
                }
                else if ( (LA53_6==NumberSign) ) {
                    alt53=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 53, 6, input);

                    throw nvae;
                }
                }
                break;
            case THIS:
                {
                alt53=3;
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
                    // InternalGlobalConstantsParser.g:3162:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3162:3: (otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis )
                    // InternalGlobalConstantsParser.g:3163:4: otherlv_0= LeftParenthesis this_STExpression_1= ruleSTExpression otherlv_2= RightParenthesis
                    {
                    otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_52);
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
                    // InternalGlobalConstantsParser.g:3181:3: this_STFeatureExpression_3= ruleSTFeatureExpression
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
                    // InternalGlobalConstantsParser.g:3190:3: this_STBuiltinFeatureExpression_4= ruleSTBuiltinFeatureExpression
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
                    // InternalGlobalConstantsParser.g:3199:3: this_STLiteralExpressions_5= ruleSTLiteralExpressions
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
    // InternalGlobalConstantsParser.g:3211:1: entryRuleSTFeatureExpression returns [EObject current=null] : iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF ;
    public final EObject entryRuleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTFeatureExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3211:60: (iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF )
            // InternalGlobalConstantsParser.g:3212:2: iv_ruleSTFeatureExpression= ruleSTFeatureExpression EOF
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
    // InternalGlobalConstantsParser.g:3218:1: ruleSTFeatureExpression returns [EObject current=null] : ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleSTFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token lv_call_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3224:2: ( ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:3225:2: ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:3225:2: ( () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:3226:3: () ( ( ruleSTFeatureName ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:3226:3: ()
            // InternalGlobalConstantsParser.g:3227:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3233:3: ( ( ruleSTFeatureName ) )
            // InternalGlobalConstantsParser.g:3234:4: ( ruleSTFeatureName )
            {
            // InternalGlobalConstantsParser.g:3234:4: ( ruleSTFeatureName )
            // InternalGlobalConstantsParser.g:3235:5: ruleSTFeatureName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_21);
            ruleSTFeatureName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGlobalConstantsParser.g:3249:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt56=2;
            alt56 = dfa56.predict(input);
            switch (alt56) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3250:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalGlobalConstantsParser.g:3250:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) )
                    // InternalGlobalConstantsParser.g:3251:5: ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3255:5: (lv_call_2_0= LeftParenthesis )
                    // InternalGlobalConstantsParser.g:3256:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_53); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:3268:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==LDATE_AND_TIME||LA55_0==DATE_AND_TIME||LA55_0==LTIME_OF_DAY||LA55_0==TIME_OF_DAY||LA55_0==WSTRING||LA55_0==STRING||LA55_0==DWORD||LA55_0==FALSE||(LA55_0>=LDATE && LA55_0<=LWORD)||(LA55_0>=UDINT && LA55_0<=ULINT)||(LA55_0>=USINT && LA55_0<=WCHAR)||(LA55_0>=BOOL && LA55_0<=BYTE)||(LA55_0>=CHAR && LA55_0<=DINT)||(LA55_0>=LINT && LA55_0<=LTOD)||(LA55_0>=REAL && LA55_0<=SINT)||(LA55_0>=THIS && LA55_0<=TRUE)||LA55_0==UINT||(LA55_0>=WORD && LA55_0<=AND)||(LA55_0>=INT && LA55_0<=NOT)||LA55_0==TOD||LA55_0==XOR||LA55_0==DT||(LA55_0>=LD && LA55_0<=LT)||LA55_0==OR||LA55_0==LeftParenthesis||LA55_0==PlusSign||LA55_0==HyphenMinus||(LA55_0>=D && LA55_0<=T)||(LA55_0>=RULE_NON_DECIMAL && LA55_0<=RULE_DECIMAL)||(LA55_0>=RULE_ID && LA55_0<=RULE_STRING)) ) {
                        alt55=1;
                    }
                    switch (alt55) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:3269:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalGlobalConstantsParser.g:3269:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalGlobalConstantsParser.g:3270:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalGlobalConstantsParser.g:3270:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalGlobalConstantsParser.g:3271:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_22);
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

                            // InternalGlobalConstantsParser.g:3288:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop54:
                            do {
                                int alt54=2;
                                int LA54_0 = input.LA(1);

                                if ( (LA54_0==Comma) ) {
                                    alt54=1;
                                }


                                switch (alt54) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:3289:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_11); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalGlobalConstantsParser.g:3293:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalGlobalConstantsParser.g:3294:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalGlobalConstantsParser.g:3294:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalGlobalConstantsParser.g:3295:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_22);
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
                            	    break loop54;
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
    // InternalGlobalConstantsParser.g:3323:1: entryRuleSTFeatureName returns [String current=null] : iv_ruleSTFeatureName= ruleSTFeatureName EOF ;
    public final String entryRuleSTFeatureName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTFeatureName = null;


        try {
            // InternalGlobalConstantsParser.g:3323:53: (iv_ruleSTFeatureName= ruleSTFeatureName EOF )
            // InternalGlobalConstantsParser.g:3324:2: iv_ruleSTFeatureName= ruleSTFeatureName EOF
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
    // InternalGlobalConstantsParser.g:3330:1: ruleSTFeatureName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTFeatureName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3336:2: ( (this_ID_0= RULE_ID | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD ) )
            // InternalGlobalConstantsParser.g:3337:2: (this_ID_0= RULE_ID | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            {
            // InternalGlobalConstantsParser.g:3337:2: (this_ID_0= RULE_ID | kw= LT | kw= AND | kw= OR | kw= XOR | kw= MOD | kw= D | kw= DT | kw= LD )
            int alt57=9;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt57=1;
                }
                break;
            case LT:
                {
                alt57=2;
                }
                break;
            case AND:
                {
                alt57=3;
                }
                break;
            case OR:
                {
                alt57=4;
                }
                break;
            case XOR:
                {
                alt57=5;
                }
                break;
            case MOD:
                {
                alt57=6;
                }
                break;
            case D:
                {
                alt57=7;
                }
                break;
            case DT:
                {
                alt57=8;
                }
                break;
            case LD:
                {
                alt57=9;
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
                    // InternalGlobalConstantsParser.g:3338:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ID_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_ID_0, grammarAccess.getSTFeatureNameAccess().getIDTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:3346:3: kw= LT
                    {
                    kw=(Token)match(input,LT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:3352:3: kw= AND
                    {
                    kw=(Token)match(input,AND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getANDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:3358:3: kw= OR
                    {
                    kw=(Token)match(input,OR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getORKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:3364:3: kw= XOR
                    {
                    kw=(Token)match(input,XOR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getXORKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsParser.g:3370:3: kw= MOD
                    {
                    kw=(Token)match(input,MOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getMODKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsParser.g:3376:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGlobalConstantsParser.g:3382:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGlobalConstantsParser.g:3388:3: kw= LD
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
    // InternalGlobalConstantsParser.g:3397:1: entryRuleSTBuiltinFeatureExpression returns [EObject current=null] : iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF ;
    public final EObject entryRuleSTBuiltinFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTBuiltinFeatureExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3397:67: (iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF )
            // InternalGlobalConstantsParser.g:3398:2: iv_ruleSTBuiltinFeatureExpression= ruleSTBuiltinFeatureExpression EOF
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
    // InternalGlobalConstantsParser.g:3404:1: ruleSTBuiltinFeatureExpression returns [EObject current=null] : ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
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
            // InternalGlobalConstantsParser.g:3410:2: ( ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalGlobalConstantsParser.g:3411:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalGlobalConstantsParser.g:3411:2: ( () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalGlobalConstantsParser.g:3412:3: () ( (lv_feature_1_0= ruleSTBuiltinFeature ) ) ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalGlobalConstantsParser.g:3412:3: ()
            // InternalGlobalConstantsParser.g:3413:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTBuiltinFeatureExpressionAccess().getSTBuiltinFeatureExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3419:3: ( (lv_feature_1_0= ruleSTBuiltinFeature ) )
            // InternalGlobalConstantsParser.g:3420:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            {
            // InternalGlobalConstantsParser.g:3420:4: (lv_feature_1_0= ruleSTBuiltinFeature )
            // InternalGlobalConstantsParser.g:3421:5: lv_feature_1_0= ruleSTBuiltinFeature
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureSTBuiltinFeatureEnumRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_21);
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

            // InternalGlobalConstantsParser.g:3438:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt60=2;
            alt60 = dfa60.predict(input);
            switch (alt60) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3439:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalGlobalConstantsParser.g:3439:4: ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) )
                    // InternalGlobalConstantsParser.g:3440:5: ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3444:5: (lv_call_2_0= LeftParenthesis )
                    // InternalGlobalConstantsParser.g:3445:6: lv_call_2_0= LeftParenthesis
                    {
                    lv_call_2_0=(Token)match(input,LeftParenthesis,FOLLOW_53); if (state.failed) return current;
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

                    // InternalGlobalConstantsParser.g:3457:4: ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==LDATE_AND_TIME||LA59_0==DATE_AND_TIME||LA59_0==LTIME_OF_DAY||LA59_0==TIME_OF_DAY||LA59_0==WSTRING||LA59_0==STRING||LA59_0==DWORD||LA59_0==FALSE||(LA59_0>=LDATE && LA59_0<=LWORD)||(LA59_0>=UDINT && LA59_0<=ULINT)||(LA59_0>=USINT && LA59_0<=WCHAR)||(LA59_0>=BOOL && LA59_0<=BYTE)||(LA59_0>=CHAR && LA59_0<=DINT)||(LA59_0>=LINT && LA59_0<=LTOD)||(LA59_0>=REAL && LA59_0<=SINT)||(LA59_0>=THIS && LA59_0<=TRUE)||LA59_0==UINT||(LA59_0>=WORD && LA59_0<=AND)||(LA59_0>=INT && LA59_0<=NOT)||LA59_0==TOD||LA59_0==XOR||LA59_0==DT||(LA59_0>=LD && LA59_0<=LT)||LA59_0==OR||LA59_0==LeftParenthesis||LA59_0==PlusSign||LA59_0==HyphenMinus||(LA59_0>=D && LA59_0<=T)||(LA59_0>=RULE_NON_DECIMAL && LA59_0<=RULE_DECIMAL)||(LA59_0>=RULE_ID && LA59_0<=RULE_STRING)) ) {
                        alt59=1;
                    }
                    switch (alt59) {
                        case 1 :
                            // InternalGlobalConstantsParser.g:3458:5: ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            {
                            // InternalGlobalConstantsParser.g:3458:5: ( (lv_parameters_3_0= ruleSTCallArgument ) )
                            // InternalGlobalConstantsParser.g:3459:6: (lv_parameters_3_0= ruleSTCallArgument )
                            {
                            // InternalGlobalConstantsParser.g:3459:6: (lv_parameters_3_0= ruleSTCallArgument )
                            // InternalGlobalConstantsParser.g:3460:7: lv_parameters_3_0= ruleSTCallArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_22);
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

                            // InternalGlobalConstantsParser.g:3477:5: (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )*
                            loop58:
                            do {
                                int alt58=2;
                                int LA58_0 = input.LA(1);

                                if ( (LA58_0==Comma) ) {
                                    alt58=1;
                                }


                                switch (alt58) {
                            	case 1 :
                            	    // InternalGlobalConstantsParser.g:3478:6: otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_11); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getSTBuiltinFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
                            	      					
                            	    }
                            	    // InternalGlobalConstantsParser.g:3482:6: ( (lv_parameters_5_0= ruleSTCallArgument ) )
                            	    // InternalGlobalConstantsParser.g:3483:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    {
                            	    // InternalGlobalConstantsParser.g:3483:7: (lv_parameters_5_0= ruleSTCallArgument )
                            	    // InternalGlobalConstantsParser.g:3484:8: lv_parameters_5_0= ruleSTCallArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_22);
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
                            	    break loop58;
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
    // InternalGlobalConstantsParser.g:3512:1: entryRuleSTMultibitPartialExpression returns [EObject current=null] : iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF ;
    public final EObject entryRuleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTMultibitPartialExpression = null;


        try {
            // InternalGlobalConstantsParser.g:3512:68: (iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF )
            // InternalGlobalConstantsParser.g:3513:2: iv_ruleSTMultibitPartialExpression= ruleSTMultibitPartialExpression EOF
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
    // InternalGlobalConstantsParser.g:3519:1: ruleSTMultibitPartialExpression returns [EObject current=null] : ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) ;
    public final EObject ruleSTMultibitPartialExpression() throws RecognitionException {
        EObject current = null;

        Token lv_index_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_specifier_1_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3525:2: ( ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) ) )
            // InternalGlobalConstantsParser.g:3526:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            {
            // InternalGlobalConstantsParser.g:3526:2: ( () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) ) )
            // InternalGlobalConstantsParser.g:3527:3: () ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )? ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            {
            // InternalGlobalConstantsParser.g:3527:3: ()
            // InternalGlobalConstantsParser.g:3528:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalGlobalConstantsParser.g:3534:3: ( (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( ((LA61_0>=B && LA61_0<=X)) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3535:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    {
                    // InternalGlobalConstantsParser.g:3535:4: (lv_specifier_1_0= ruleSTMultiBitAccessSpecifier )
                    // InternalGlobalConstantsParser.g:3536:5: lv_specifier_1_0= ruleSTMultiBitAccessSpecifier
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

            // InternalGlobalConstantsParser.g:3553:3: ( ( (lv_index_2_0= RULE_INT ) ) | (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis ) )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==RULE_INT) ) {
                alt62=1;
            }
            else if ( (LA62_0==LeftParenthesis) ) {
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
                    // InternalGlobalConstantsParser.g:3554:4: ( (lv_index_2_0= RULE_INT ) )
                    {
                    // InternalGlobalConstantsParser.g:3554:4: ( (lv_index_2_0= RULE_INT ) )
                    // InternalGlobalConstantsParser.g:3555:5: (lv_index_2_0= RULE_INT )
                    {
                    // InternalGlobalConstantsParser.g:3555:5: (lv_index_2_0= RULE_INT )
                    // InternalGlobalConstantsParser.g:3556:6: lv_index_2_0= RULE_INT
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
                    // InternalGlobalConstantsParser.g:3573:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    {
                    // InternalGlobalConstantsParser.g:3573:4: (otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis )
                    // InternalGlobalConstantsParser.g:3574:5: otherlv_3= LeftParenthesis ( (lv_expression_4_0= ruleSTExpression ) ) otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
                      				
                    }
                    // InternalGlobalConstantsParser.g:3578:5: ( (lv_expression_4_0= ruleSTExpression ) )
                    // InternalGlobalConstantsParser.g:3579:6: (lv_expression_4_0= ruleSTExpression )
                    {
                    // InternalGlobalConstantsParser.g:3579:6: (lv_expression_4_0= ruleSTExpression )
                    // InternalGlobalConstantsParser.g:3580:7: lv_expression_4_0= ruleSTExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_52);
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
    // InternalGlobalConstantsParser.g:3607:1: entryRuleSTLiteralExpressions returns [EObject current=null] : iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF ;
    public final EObject entryRuleSTLiteralExpressions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTLiteralExpressions = null;


        try {
            // InternalGlobalConstantsParser.g:3607:61: (iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF )
            // InternalGlobalConstantsParser.g:3608:2: iv_ruleSTLiteralExpressions= ruleSTLiteralExpressions EOF
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
    // InternalGlobalConstantsParser.g:3614:1: ruleSTLiteralExpressions returns [EObject current=null] : (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) ;
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
            // InternalGlobalConstantsParser.g:3620:2: ( (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral ) )
            // InternalGlobalConstantsParser.g:3621:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            {
            // InternalGlobalConstantsParser.g:3621:2: (this_STNumericLiteral_0= ruleSTNumericLiteral | this_STDateLiteral_1= ruleSTDateLiteral | this_STTimeLiteral_2= ruleSTTimeLiteral | this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral | this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral | this_STStringLiteral_5= ruleSTStringLiteral )
            int alt63=6;
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
                alt63=1;
                }
                break;
            case LDATE:
            case DATE:
            case LD:
            case D:
                {
                alt63=2;
                }
                break;
            case LTIME:
            case TIME:
            case LT:
            case T:
                {
                alt63=3;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt63=4;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
                {
                alt63=5;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_STRING:
                {
                alt63=6;
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
                    // InternalGlobalConstantsParser.g:3622:3: this_STNumericLiteral_0= ruleSTNumericLiteral
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
                    // InternalGlobalConstantsParser.g:3631:3: this_STDateLiteral_1= ruleSTDateLiteral
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
                    // InternalGlobalConstantsParser.g:3640:3: this_STTimeLiteral_2= ruleSTTimeLiteral
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
                    // InternalGlobalConstantsParser.g:3649:3: this_STTimeOfDayLiteral_3= ruleSTTimeOfDayLiteral
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
                    // InternalGlobalConstantsParser.g:3658:3: this_STDateAndTimeLiteral_4= ruleSTDateAndTimeLiteral
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
                    // InternalGlobalConstantsParser.g:3667:3: this_STStringLiteral_5= ruleSTStringLiteral
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
    // InternalGlobalConstantsParser.g:3679:1: entryRuleSTNumericLiteralType returns [String current=null] : iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF ;
    public final String entryRuleSTNumericLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTNumericLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:3679:60: (iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF )
            // InternalGlobalConstantsParser.g:3680:2: iv_ruleSTNumericLiteralType= ruleSTNumericLiteralType EOF
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
    // InternalGlobalConstantsParser.g:3686:1: ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) ;
    public final AntlrDatatypeRuleToken ruleSTNumericLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3692:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType ) )
            // InternalGlobalConstantsParser.g:3693:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            {
            // InternalGlobalConstantsParser.g:3693:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==DWORD||LA64_0==LWORD||(LA64_0>=BOOL && LA64_0<=BYTE)||LA64_0==WORD) ) {
                alt64=1;
            }
            else if ( (LA64_0==LREAL||(LA64_0>=UDINT && LA64_0<=ULINT)||LA64_0==USINT||LA64_0==DINT||LA64_0==LINT||(LA64_0>=REAL && LA64_0<=SINT)||LA64_0==UINT||LA64_0==INT) ) {
                alt64=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3694:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalGlobalConstantsParser.g:3705:3: this_STAnyNumType_1= ruleSTAnyNumType
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
    // InternalGlobalConstantsParser.g:3719:1: entryRuleSTNumericLiteral returns [EObject current=null] : iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF ;
    public final EObject entryRuleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTNumericLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:3719:57: (iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF )
            // InternalGlobalConstantsParser.g:3720:2: iv_ruleSTNumericLiteral= ruleSTNumericLiteral EOF
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
    // InternalGlobalConstantsParser.g:3726:1: ruleSTNumericLiteral returns [EObject current=null] : ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) ) ) ;
    public final EObject ruleSTNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3732:2: ( ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) ) ) )
            // InternalGlobalConstantsParser.g:3733:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) ) )
            {
            // InternalGlobalConstantsParser.g:3733:2: ( ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) ) )
            // InternalGlobalConstantsParser.g:3734:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleNumeric ) )
            {
            // InternalGlobalConstantsParser.g:3734:3: ( ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==DWORD||LA65_0==LREAL||LA65_0==LWORD||(LA65_0>=UDINT && LA65_0<=ULINT)||LA65_0==USINT||(LA65_0>=BOOL && LA65_0<=BYTE)||LA65_0==DINT||LA65_0==LINT||(LA65_0>=REAL && LA65_0<=SINT)||LA65_0==UINT||LA65_0==WORD||LA65_0==INT) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalGlobalConstantsParser.g:3735:4: ( ( ruleSTNumericLiteralType ) ) otherlv_1= NumberSign
                    {
                    // InternalGlobalConstantsParser.g:3735:4: ( ( ruleSTNumericLiteralType ) )
                    // InternalGlobalConstantsParser.g:3736:5: ( ruleSTNumericLiteralType )
                    {
                    // InternalGlobalConstantsParser.g:3736:5: ( ruleSTNumericLiteralType )
                    // InternalGlobalConstantsParser.g:3737:6: ruleSTNumericLiteralType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTNumericLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
                    ruleSTNumericLiteralType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,NumberSign,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:3756:3: ( (lv_value_2_0= ruleNumeric ) )
            // InternalGlobalConstantsParser.g:3757:4: (lv_value_2_0= ruleNumeric )
            {
            // InternalGlobalConstantsParser.g:3757:4: (lv_value_2_0= ruleNumeric )
            // InternalGlobalConstantsParser.g:3758:5: lv_value_2_0= ruleNumeric
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
    // InternalGlobalConstantsParser.g:3779:1: entryRuleSTDateLiteralType returns [String current=null] : iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF ;
    public final String entryRuleSTDateLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:3779:57: (iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF )
            // InternalGlobalConstantsParser.g:3780:2: iv_ruleSTDateLiteralType= ruleSTDateLiteralType EOF
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
    // InternalGlobalConstantsParser.g:3786:1: ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) ;
    public final AntlrDatatypeRuleToken ruleSTDateLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STDateType_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3792:2: ( (this_STDateType_0= ruleSTDateType | kw= D | kw= LD ) )
            // InternalGlobalConstantsParser.g:3793:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            {
            // InternalGlobalConstantsParser.g:3793:2: (this_STDateType_0= ruleSTDateType | kw= D | kw= LD )
            int alt66=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt66=1;
                }
                break;
            case D:
                {
                alt66=2;
                }
                break;
            case LD:
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
                    // InternalGlobalConstantsParser.g:3794:3: this_STDateType_0= ruleSTDateType
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
                    // InternalGlobalConstantsParser.g:3805:3: kw= D
                    {
                    kw=(Token)match(input,D,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:3811:3: kw= LD
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
    // InternalGlobalConstantsParser.g:3820:1: entryRuleSTDateLiteral returns [EObject current=null] : iv_ruleSTDateLiteral= ruleSTDateLiteral EOF ;
    public final EObject entryRuleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:3820:54: (iv_ruleSTDateLiteral= ruleSTDateLiteral EOF )
            // InternalGlobalConstantsParser.g:3821:2: iv_ruleSTDateLiteral= ruleSTDateLiteral EOF
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
    // InternalGlobalConstantsParser.g:3827:1: ruleSTDateLiteral returns [EObject current=null] : ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) ;
    public final EObject ruleSTDateLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3833:2: ( ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) ) )
            // InternalGlobalConstantsParser.g:3834:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            {
            // InternalGlobalConstantsParser.g:3834:2: ( ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) ) )
            // InternalGlobalConstantsParser.g:3835:3: ( ( ruleSTDateLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate ) )
            {
            // InternalGlobalConstantsParser.g:3835:3: ( ( ruleSTDateLiteralType ) )
            // InternalGlobalConstantsParser.g:3836:4: ( ruleSTDateLiteralType )
            {
            // InternalGlobalConstantsParser.g:3836:4: ( ruleSTDateLiteralType )
            // InternalGlobalConstantsParser.g:3837:5: ruleSTDateLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_55);
            ruleSTDateLiteralType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:3855:3: ( (lv_value_2_0= ruleDate ) )
            // InternalGlobalConstantsParser.g:3856:4: (lv_value_2_0= ruleDate )
            {
            // InternalGlobalConstantsParser.g:3856:4: (lv_value_2_0= ruleDate )
            // InternalGlobalConstantsParser.g:3857:5: lv_value_2_0= ruleDate
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
    // InternalGlobalConstantsParser.g:3878:1: entryRuleSTTimeLiteralType returns [String current=null] : iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF ;
    public final String entryRuleSTTimeLiteralType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeLiteralType = null;


        try {
            // InternalGlobalConstantsParser.g:3878:57: (iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF )
            // InternalGlobalConstantsParser.g:3879:2: iv_ruleSTTimeLiteralType= ruleSTTimeLiteralType EOF
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
    // InternalGlobalConstantsParser.g:3885:1: ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeLiteralType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_STAnyDurationType_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3891:2: ( (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT ) )
            // InternalGlobalConstantsParser.g:3892:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            {
            // InternalGlobalConstantsParser.g:3892:2: (this_STAnyDurationType_0= ruleSTAnyDurationType | kw= T | kw= LT )
            int alt67=3;
            switch ( input.LA(1) ) {
            case LTIME:
            case TIME:
                {
                alt67=1;
                }
                break;
            case T:
                {
                alt67=2;
                }
                break;
            case LT:
                {
                alt67=3;
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
                    // InternalGlobalConstantsParser.g:3893:3: this_STAnyDurationType_0= ruleSTAnyDurationType
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
                    // InternalGlobalConstantsParser.g:3904:3: kw= T
                    {
                    kw=(Token)match(input,T,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:3910:3: kw= LT
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
    // InternalGlobalConstantsParser.g:3919:1: entryRuleSTTimeLiteral returns [EObject current=null] : iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF ;
    public final EObject entryRuleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:3919:54: (iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF )
            // InternalGlobalConstantsParser.g:3920:2: iv_ruleSTTimeLiteral= ruleSTTimeLiteral EOF
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
    // InternalGlobalConstantsParser.g:3926:1: ruleSTTimeLiteral returns [EObject current=null] : ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) ;
    public final EObject ruleSTTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3932:2: ( ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) ) )
            // InternalGlobalConstantsParser.g:3933:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            {
            // InternalGlobalConstantsParser.g:3933:2: ( ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) ) )
            // InternalGlobalConstantsParser.g:3934:3: ( ( ruleSTTimeLiteralType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTime ) )
            {
            // InternalGlobalConstantsParser.g:3934:3: ( ( ruleSTTimeLiteralType ) )
            // InternalGlobalConstantsParser.g:3935:4: ( ruleSTTimeLiteralType )
            {
            // InternalGlobalConstantsParser.g:3935:4: ( ruleSTTimeLiteralType )
            // InternalGlobalConstantsParser.g:3936:5: ruleSTTimeLiteralType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_55);
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
            // InternalGlobalConstantsParser.g:3954:3: ( (lv_value_2_0= ruleTime ) )
            // InternalGlobalConstantsParser.g:3955:4: (lv_value_2_0= ruleTime )
            {
            // InternalGlobalConstantsParser.g:3955:4: (lv_value_2_0= ruleTime )
            // InternalGlobalConstantsParser.g:3956:5: lv_value_2_0= ruleTime
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
    // InternalGlobalConstantsParser.g:3977:1: entryRuleSTTimeOfDayLiteral returns [EObject current=null] : iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF ;
    public final EObject entryRuleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTTimeOfDayLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:3977:59: (iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF )
            // InternalGlobalConstantsParser.g:3978:2: iv_ruleSTTimeOfDayLiteral= ruleSTTimeOfDayLiteral EOF
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
    // InternalGlobalConstantsParser.g:3984:1: ruleSTTimeOfDayLiteral returns [EObject current=null] : ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) ;
    public final EObject ruleSTTimeOfDayLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:3990:2: ( ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) ) )
            // InternalGlobalConstantsParser.g:3991:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            {
            // InternalGlobalConstantsParser.g:3991:2: ( ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) ) )
            // InternalGlobalConstantsParser.g:3992:3: ( ( ruleSTTimeOfDayType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleTimeOfDay ) )
            {
            // InternalGlobalConstantsParser.g:3992:3: ( ( ruleSTTimeOfDayType ) )
            // InternalGlobalConstantsParser.g:3993:4: ( ruleSTTimeOfDayType )
            {
            // InternalGlobalConstantsParser.g:3993:4: ( ruleSTTimeOfDayType )
            // InternalGlobalConstantsParser.g:3994:5: ruleSTTimeOfDayType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_55);
            ruleSTTimeOfDayType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:4012:3: ( (lv_value_2_0= ruleTimeOfDay ) )
            // InternalGlobalConstantsParser.g:4013:4: (lv_value_2_0= ruleTimeOfDay )
            {
            // InternalGlobalConstantsParser.g:4013:4: (lv_value_2_0= ruleTimeOfDay )
            // InternalGlobalConstantsParser.g:4014:5: lv_value_2_0= ruleTimeOfDay
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
    // InternalGlobalConstantsParser.g:4035:1: entryRuleSTDateAndTimeLiteral returns [EObject current=null] : iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF ;
    public final EObject entryRuleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTDateAndTimeLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4035:61: (iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF )
            // InternalGlobalConstantsParser.g:4036:2: iv_ruleSTDateAndTimeLiteral= ruleSTDateAndTimeLiteral EOF
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
    // InternalGlobalConstantsParser.g:4042:1: ruleSTDateAndTimeLiteral returns [EObject current=null] : ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) ;
    public final EObject ruleSTDateAndTimeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4048:2: ( ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) ) )
            // InternalGlobalConstantsParser.g:4049:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            {
            // InternalGlobalConstantsParser.g:4049:2: ( ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) ) )
            // InternalGlobalConstantsParser.g:4050:3: ( ( ruleSTDateAndTimeType ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDateAndTime ) )
            {
            // InternalGlobalConstantsParser.g:4050:3: ( ( ruleSTDateAndTimeType ) )
            // InternalGlobalConstantsParser.g:4051:4: ( ruleSTDateAndTimeType )
            {
            // InternalGlobalConstantsParser.g:4051:4: ( ruleSTDateAndTimeType )
            // InternalGlobalConstantsParser.g:4052:5: ruleSTDateAndTimeType
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_55);
            ruleSTDateAndTimeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,NumberSign,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1());
              		
            }
            // InternalGlobalConstantsParser.g:4070:3: ( (lv_value_2_0= ruleDateAndTime ) )
            // InternalGlobalConstantsParser.g:4071:4: (lv_value_2_0= ruleDateAndTime )
            {
            // InternalGlobalConstantsParser.g:4071:4: (lv_value_2_0= ruleDateAndTime )
            // InternalGlobalConstantsParser.g:4072:5: lv_value_2_0= ruleDateAndTime
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
    // InternalGlobalConstantsParser.g:4093:1: entryRuleSTStringLiteral returns [EObject current=null] : iv_ruleSTStringLiteral= ruleSTStringLiteral EOF ;
    public final EObject entryRuleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSTStringLiteral = null;


        try {
            // InternalGlobalConstantsParser.g:4093:56: (iv_ruleSTStringLiteral= ruleSTStringLiteral EOF )
            // InternalGlobalConstantsParser.g:4094:2: iv_ruleSTStringLiteral= ruleSTStringLiteral EOF
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
    // InternalGlobalConstantsParser.g:4100:1: ruleSTStringLiteral returns [EObject current=null] : ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleSTStringLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4106:2: ( ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) ) )
            // InternalGlobalConstantsParser.g:4107:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            {
            // InternalGlobalConstantsParser.g:4107:2: ( ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) ) )
            // InternalGlobalConstantsParser.g:4108:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= RULE_STRING ) )
            {
            // InternalGlobalConstantsParser.g:4108:3: ( ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==WSTRING||LA68_0==STRING||LA68_0==WCHAR||LA68_0==CHAR) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4109:4: ( ( ruleSTAnyCharsType ) ) otherlv_1= NumberSign
                    {
                    // InternalGlobalConstantsParser.g:4109:4: ( ( ruleSTAnyCharsType ) )
                    // InternalGlobalConstantsParser.g:4110:5: ( ruleSTAnyCharsType )
                    {
                    // InternalGlobalConstantsParser.g:4110:5: ( ruleSTAnyCharsType )
                    // InternalGlobalConstantsParser.g:4111:6: ruleSTAnyCharsType
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSTStringLiteralRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
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

            // InternalGlobalConstantsParser.g:4130:3: ( (lv_value_2_0= RULE_STRING ) )
            // InternalGlobalConstantsParser.g:4131:4: (lv_value_2_0= RULE_STRING )
            {
            // InternalGlobalConstantsParser.g:4131:4: (lv_value_2_0= RULE_STRING )
            // InternalGlobalConstantsParser.g:4132:5: lv_value_2_0= RULE_STRING
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
    // InternalGlobalConstantsParser.g:4152:1: entryRuleSTAnyType returns [String current=null] : iv_ruleSTAnyType= ruleSTAnyType EOF ;
    public final String entryRuleSTAnyType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyType = null;


        try {
            // InternalGlobalConstantsParser.g:4152:49: (iv_ruleSTAnyType= ruleSTAnyType EOF )
            // InternalGlobalConstantsParser.g:4153:2: iv_ruleSTAnyType= ruleSTAnyType EOF
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
    // InternalGlobalConstantsParser.g:4159:1: ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        AntlrDatatypeRuleToken this_STAnyBuiltinType_1 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4165:2: ( (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType ) )
            // InternalGlobalConstantsParser.g:4166:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            {
            // InternalGlobalConstantsParser.g:4166:2: (this_ID_0= RULE_ID | this_STAnyBuiltinType_1= ruleSTAnyBuiltinType )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==RULE_ID) ) {
                alt69=1;
            }
            else if ( (LA69_0==LDATE_AND_TIME||LA69_0==DATE_AND_TIME||LA69_0==LTIME_OF_DAY||LA69_0==TIME_OF_DAY||LA69_0==WSTRING||LA69_0==STRING||LA69_0==DWORD||(LA69_0>=LDATE && LA69_0<=LWORD)||(LA69_0>=UDINT && LA69_0<=ULINT)||(LA69_0>=USINT && LA69_0<=WCHAR)||(LA69_0>=BOOL && LA69_0<=BYTE)||(LA69_0>=CHAR && LA69_0<=DINT)||(LA69_0>=LINT && LA69_0<=LTOD)||(LA69_0>=REAL && LA69_0<=SINT)||LA69_0==TIME||LA69_0==UINT||LA69_0==WORD||(LA69_0>=INT && LA69_0<=LDT)||LA69_0==TOD||LA69_0==DT) ) {
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
                    // InternalGlobalConstantsParser.g:4167:3: this_ID_0= RULE_ID
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
                    // InternalGlobalConstantsParser.g:4175:3: this_STAnyBuiltinType_1= ruleSTAnyBuiltinType
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
    // InternalGlobalConstantsParser.g:4189:1: entryRuleSTAnyBuiltinType returns [String current=null] : iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF ;
    public final String entryRuleSTAnyBuiltinType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBuiltinType = null;


        try {
            // InternalGlobalConstantsParser.g:4189:56: (iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF )
            // InternalGlobalConstantsParser.g:4190:2: iv_ruleSTAnyBuiltinType= ruleSTAnyBuiltinType EOF
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
    // InternalGlobalConstantsParser.g:4196:1: ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBuiltinType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STAnyBitType_0 = null;

        AntlrDatatypeRuleToken this_STAnyNumType_1 = null;

        AntlrDatatypeRuleToken this_STAnyDurationType_2 = null;

        AntlrDatatypeRuleToken this_STAnyDateType_3 = null;

        AntlrDatatypeRuleToken this_STAnyCharsType_4 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4202:2: ( (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType ) )
            // InternalGlobalConstantsParser.g:4203:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            {
            // InternalGlobalConstantsParser.g:4203:2: (this_STAnyBitType_0= ruleSTAnyBitType | this_STAnyNumType_1= ruleSTAnyNumType | this_STAnyDurationType_2= ruleSTAnyDurationType | this_STAnyDateType_3= ruleSTAnyDateType | this_STAnyCharsType_4= ruleSTAnyCharsType )
            int alt70=5;
            switch ( input.LA(1) ) {
            case DWORD:
            case LWORD:
            case BOOL:
            case BYTE:
            case WORD:
                {
                alt70=1;
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
                alt70=2;
                }
                break;
            case LTIME:
            case TIME:
                {
                alt70=3;
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
                alt70=4;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
                {
                alt70=5;
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
                    // InternalGlobalConstantsParser.g:4204:3: this_STAnyBitType_0= ruleSTAnyBitType
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
                    // InternalGlobalConstantsParser.g:4215:3: this_STAnyNumType_1= ruleSTAnyNumType
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
                    // InternalGlobalConstantsParser.g:4226:3: this_STAnyDurationType_2= ruleSTAnyDurationType
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
                    // InternalGlobalConstantsParser.g:4237:3: this_STAnyDateType_3= ruleSTAnyDateType
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
                    // InternalGlobalConstantsParser.g:4248:3: this_STAnyCharsType_4= ruleSTAnyCharsType
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
    // InternalGlobalConstantsParser.g:4262:1: entryRuleSTAnyBitType returns [String current=null] : iv_ruleSTAnyBitType= ruleSTAnyBitType EOF ;
    public final String entryRuleSTAnyBitType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyBitType = null;


        try {
            // InternalGlobalConstantsParser.g:4262:52: (iv_ruleSTAnyBitType= ruleSTAnyBitType EOF )
            // InternalGlobalConstantsParser.g:4263:2: iv_ruleSTAnyBitType= ruleSTAnyBitType EOF
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
    // InternalGlobalConstantsParser.g:4269:1: ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyBitType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4275:2: ( (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD ) )
            // InternalGlobalConstantsParser.g:4276:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            {
            // InternalGlobalConstantsParser.g:4276:2: (kw= BOOL | kw= BYTE | kw= WORD | kw= DWORD | kw= LWORD )
            int alt71=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt71=1;
                }
                break;
            case BYTE:
                {
                alt71=2;
                }
                break;
            case WORD:
                {
                alt71=3;
                }
                break;
            case DWORD:
                {
                alt71=4;
                }
                break;
            case LWORD:
                {
                alt71=5;
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
                    // InternalGlobalConstantsParser.g:4277:3: kw= BOOL
                    {
                    kw=(Token)match(input,BOOL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4283:3: kw= BYTE
                    {
                    kw=(Token)match(input,BYTE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4289:3: kw= WORD
                    {
                    kw=(Token)match(input,WORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4295:3: kw= DWORD
                    {
                    kw=(Token)match(input,DWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:4301:3: kw= LWORD
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
    // InternalGlobalConstantsParser.g:4310:1: entryRuleSTAnyNumType returns [String current=null] : iv_ruleSTAnyNumType= ruleSTAnyNumType EOF ;
    public final String entryRuleSTAnyNumType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyNumType = null;


        try {
            // InternalGlobalConstantsParser.g:4310:52: (iv_ruleSTAnyNumType= ruleSTAnyNumType EOF )
            // InternalGlobalConstantsParser.g:4311:2: iv_ruleSTAnyNumType= ruleSTAnyNumType EOF
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
    // InternalGlobalConstantsParser.g:4317:1: ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyNumType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4323:2: ( (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL ) )
            // InternalGlobalConstantsParser.g:4324:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            {
            // InternalGlobalConstantsParser.g:4324:2: (kw= SINT | kw= INT | kw= DINT | kw= LINT | kw= USINT | kw= UINT | kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL )
            int alt72=10;
            switch ( input.LA(1) ) {
            case SINT:
                {
                alt72=1;
                }
                break;
            case INT:
                {
                alt72=2;
                }
                break;
            case DINT:
                {
                alt72=3;
                }
                break;
            case LINT:
                {
                alt72=4;
                }
                break;
            case USINT:
                {
                alt72=5;
                }
                break;
            case UINT:
                {
                alt72=6;
                }
                break;
            case UDINT:
                {
                alt72=7;
                }
                break;
            case ULINT:
                {
                alt72=8;
                }
                break;
            case REAL:
                {
                alt72=9;
                }
                break;
            case LREAL:
                {
                alt72=10;
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
                    // InternalGlobalConstantsParser.g:4325:3: kw= SINT
                    {
                    kw=(Token)match(input,SINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4331:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4337:3: kw= DINT
                    {
                    kw=(Token)match(input,DINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4343:3: kw= LINT
                    {
                    kw=(Token)match(input,LINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsParser.g:4349:3: kw= USINT
                    {
                    kw=(Token)match(input,USINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsParser.g:4355:3: kw= UINT
                    {
                    kw=(Token)match(input,UINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsParser.g:4361:3: kw= UDINT
                    {
                    kw=(Token)match(input,UDINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGlobalConstantsParser.g:4367:3: kw= ULINT
                    {
                    kw=(Token)match(input,ULINT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGlobalConstantsParser.g:4373:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalGlobalConstantsParser.g:4379:3: kw= LREAL
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
    // InternalGlobalConstantsParser.g:4388:1: entryRuleSTAnyDurationType returns [String current=null] : iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF ;
    public final String entryRuleSTAnyDurationType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDurationType = null;


        try {
            // InternalGlobalConstantsParser.g:4388:57: (iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF )
            // InternalGlobalConstantsParser.g:4389:2: iv_ruleSTAnyDurationType= ruleSTAnyDurationType EOF
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
    // InternalGlobalConstantsParser.g:4395:1: ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME | kw= LTIME ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDurationType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4401:2: ( (kw= TIME | kw= LTIME ) )
            // InternalGlobalConstantsParser.g:4402:2: (kw= TIME | kw= LTIME )
            {
            // InternalGlobalConstantsParser.g:4402:2: (kw= TIME | kw= LTIME )
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==TIME) ) {
                alt73=1;
            }
            else if ( (LA73_0==LTIME) ) {
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
                    // InternalGlobalConstantsParser.g:4403:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4409:3: kw= LTIME
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
    // InternalGlobalConstantsParser.g:4418:1: entryRuleSTAnyDateType returns [String current=null] : iv_ruleSTAnyDateType= ruleSTAnyDateType EOF ;
    public final String entryRuleSTAnyDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyDateType = null;


        try {
            // InternalGlobalConstantsParser.g:4418:53: (iv_ruleSTAnyDateType= ruleSTAnyDateType EOF )
            // InternalGlobalConstantsParser.g:4419:2: iv_ruleSTAnyDateType= ruleSTAnyDateType EOF
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
    // InternalGlobalConstantsParser.g:4425:1: ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_STDateType_0 = null;

        AntlrDatatypeRuleToken this_STTimeOfDayType_1 = null;

        AntlrDatatypeRuleToken this_STDateAndTimeType_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4431:2: ( (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType ) )
            // InternalGlobalConstantsParser.g:4432:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            {
            // InternalGlobalConstantsParser.g:4432:2: (this_STDateType_0= ruleSTDateType | this_STTimeOfDayType_1= ruleSTTimeOfDayType | this_STDateAndTimeType_2= ruleSTDateAndTimeType )
            int alt74=3;
            switch ( input.LA(1) ) {
            case LDATE:
            case DATE:
                {
                alt74=1;
                }
                break;
            case LTIME_OF_DAY:
            case TIME_OF_DAY:
            case LTOD:
            case TOD:
                {
                alt74=2;
                }
                break;
            case LDATE_AND_TIME:
            case DATE_AND_TIME:
            case LDT:
            case DT:
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
                    // InternalGlobalConstantsParser.g:4433:3: this_STDateType_0= ruleSTDateType
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
                    // InternalGlobalConstantsParser.g:4444:3: this_STTimeOfDayType_1= ruleSTTimeOfDayType
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
                    // InternalGlobalConstantsParser.g:4455:3: this_STDateAndTimeType_2= ruleSTDateAndTimeType
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
    // InternalGlobalConstantsParser.g:4469:1: entryRuleSTDateType returns [String current=null] : iv_ruleSTDateType= ruleSTDateType EOF ;
    public final String entryRuleSTDateType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateType = null;


        try {
            // InternalGlobalConstantsParser.g:4469:50: (iv_ruleSTDateType= ruleSTDateType EOF )
            // InternalGlobalConstantsParser.g:4470:2: iv_ruleSTDateType= ruleSTDateType EOF
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
    // InternalGlobalConstantsParser.g:4476:1: ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE | kw= LDATE ) ;
    public final AntlrDatatypeRuleToken ruleSTDateType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4482:2: ( (kw= DATE | kw= LDATE ) )
            // InternalGlobalConstantsParser.g:4483:2: (kw= DATE | kw= LDATE )
            {
            // InternalGlobalConstantsParser.g:4483:2: (kw= DATE | kw= LDATE )
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==DATE) ) {
                alt75=1;
            }
            else if ( (LA75_0==LDATE) ) {
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
                    // InternalGlobalConstantsParser.g:4484:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4490:3: kw= LDATE
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
    // InternalGlobalConstantsParser.g:4499:1: entryRuleSTTimeOfDayType returns [String current=null] : iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF ;
    public final String entryRuleSTTimeOfDayType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTTimeOfDayType = null;


        try {
            // InternalGlobalConstantsParser.g:4499:55: (iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF )
            // InternalGlobalConstantsParser.g:4500:2: iv_ruleSTTimeOfDayType= ruleSTTimeOfDayType EOF
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
    // InternalGlobalConstantsParser.g:4506:1: ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) ;
    public final AntlrDatatypeRuleToken ruleSTTimeOfDayType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4512:2: ( (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD ) )
            // InternalGlobalConstantsParser.g:4513:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            {
            // InternalGlobalConstantsParser.g:4513:2: (kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD )
            int alt76=4;
            switch ( input.LA(1) ) {
            case TIME_OF_DAY:
                {
                alt76=1;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt76=2;
                }
                break;
            case TOD:
                {
                alt76=3;
                }
                break;
            case LTOD:
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
                    // InternalGlobalConstantsParser.g:4514:3: kw= TIME_OF_DAY
                    {
                    kw=(Token)match(input,TIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4520:3: kw= LTIME_OF_DAY
                    {
                    kw=(Token)match(input,LTIME_OF_DAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4526:3: kw= TOD
                    {
                    kw=(Token)match(input,TOD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4532:3: kw= LTOD
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
    // InternalGlobalConstantsParser.g:4541:1: entryRuleSTDateAndTimeType returns [String current=null] : iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF ;
    public final String entryRuleSTDateAndTimeType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTDateAndTimeType = null;


        try {
            // InternalGlobalConstantsParser.g:4541:57: (iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF )
            // InternalGlobalConstantsParser.g:4542:2: iv_ruleSTDateAndTimeType= ruleSTDateAndTimeType EOF
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
    // InternalGlobalConstantsParser.g:4548:1: ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) ;
    public final AntlrDatatypeRuleToken ruleSTDateAndTimeType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4554:2: ( (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT ) )
            // InternalGlobalConstantsParser.g:4555:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            {
            // InternalGlobalConstantsParser.g:4555:2: (kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= DT | kw= LDT )
            int alt77=4;
            switch ( input.LA(1) ) {
            case DATE_AND_TIME:
                {
                alt77=1;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt77=2;
                }
                break;
            case DT:
                {
                alt77=3;
                }
                break;
            case LDT:
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
                    // InternalGlobalConstantsParser.g:4556:3: kw= DATE_AND_TIME
                    {
                    kw=(Token)match(input,DATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4562:3: kw= LDATE_AND_TIME
                    {
                    kw=(Token)match(input,LDATE_AND_TIME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4568:3: kw= DT
                    {
                    kw=(Token)match(input,DT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4574:3: kw= LDT
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
    // InternalGlobalConstantsParser.g:4583:1: entryRuleSTAnyCharsType returns [String current=null] : iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF ;
    public final String entryRuleSTAnyCharsType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSTAnyCharsType = null;


        try {
            // InternalGlobalConstantsParser.g:4583:54: (iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF )
            // InternalGlobalConstantsParser.g:4584:2: iv_ruleSTAnyCharsType= ruleSTAnyCharsType EOF
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
    // InternalGlobalConstantsParser.g:4590:1: ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) ;
    public final AntlrDatatypeRuleToken ruleSTAnyCharsType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4596:2: ( (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR ) )
            // InternalGlobalConstantsParser.g:4597:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            {
            // InternalGlobalConstantsParser.g:4597:2: (kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR )
            int alt78=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt78=1;
                }
                break;
            case WSTRING:
                {
                alt78=2;
                }
                break;
            case CHAR:
                {
                alt78=3;
                }
                break;
            case WCHAR:
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
                    // InternalGlobalConstantsParser.g:4598:3: kw= STRING
                    {
                    kw=(Token)match(input,STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4604:3: kw= WSTRING
                    {
                    kw=(Token)match(input,WSTRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4610:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsParser.g:4616:3: kw= WCHAR
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


    // $ANTLR start "entryRuleNumeric"
    // InternalGlobalConstantsParser.g:4625:1: entryRuleNumeric returns [String current=null] : iv_ruleNumeric= ruleNumeric EOF ;
    public final String entryRuleNumeric() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumeric = null;


        try {
            // InternalGlobalConstantsParser.g:4625:47: (iv_ruleNumeric= ruleNumeric EOF )
            // InternalGlobalConstantsParser.g:4626:2: iv_ruleNumeric= ruleNumeric EOF
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
    // InternalGlobalConstantsParser.g:4632:1: ruleNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) ;
    public final AntlrDatatypeRuleToken ruleNumeric() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_NON_DECIMAL_3=null;
        AntlrDatatypeRuleToken this_Number_2 = null;



        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4638:2: ( (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL ) )
            // InternalGlobalConstantsParser.g:4639:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            {
            // InternalGlobalConstantsParser.g:4639:2: (kw= TRUE | kw= FALSE | this_Number_2= ruleNumber | this_NON_DECIMAL_3= RULE_NON_DECIMAL )
            int alt79=4;
            switch ( input.LA(1) ) {
            case TRUE:
                {
                alt79=1;
                }
                break;
            case FALSE:
                {
                alt79=2;
                }
                break;
            case PlusSign:
            case HyphenMinus:
            case RULE_INT:
            case RULE_DECIMAL:
                {
                alt79=3;
                }
                break;
            case RULE_NON_DECIMAL:
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
                    // InternalGlobalConstantsParser.g:4640:3: kw= TRUE
                    {
                    kw=(Token)match(input,TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getTRUEKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4646:3: kw= FALSE
                    {
                    kw=(Token)match(input,FALSE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getNumericAccess().getFALSEKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsParser.g:4652:3: this_Number_2= ruleNumber
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
                    // InternalGlobalConstantsParser.g:4663:3: this_NON_DECIMAL_3= RULE_NON_DECIMAL
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
    // InternalGlobalConstantsParser.g:4674:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:4676:2: (iv_ruleNumber= ruleNumber EOF )
            // InternalGlobalConstantsParser.g:4677:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalGlobalConstantsParser.g:4686:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )? ) ;
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
            // InternalGlobalConstantsParser.g:4693:2: ( ( (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )? ) )
            // InternalGlobalConstantsParser.g:4694:2: ( (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )? )
            {
            // InternalGlobalConstantsParser.g:4694:2: ( (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )? )
            // InternalGlobalConstantsParser.g:4695:3: (kw= PlusSign | kw= HyphenMinus )? (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL ) ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )?
            {
            // InternalGlobalConstantsParser.g:4695:3: (kw= PlusSign | kw= HyphenMinus )?
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
                    // InternalGlobalConstantsParser.g:4696:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4702:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getNumberAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:4708:3: (this_INT_2= RULE_INT | this_DECIMAL_3= RULE_DECIMAL )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==RULE_INT) ) {
                alt81=1;
            }
            else if ( (LA81_0==RULE_DECIMAL) ) {
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
                    // InternalGlobalConstantsParser.g:4709:4: this_INT_2= RULE_INT
                    {
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4717:4: this_DECIMAL_3= RULE_DECIMAL
                    {
                    this_DECIMAL_3=(Token)match(input,RULE_DECIMAL,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_DECIMAL_3);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_DECIMAL_3, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalGlobalConstantsParser.g:4725:3: ( ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL ) )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==FullStop) ) {
                int LA83_1 = input.LA(2);

                if ( (LA83_1==RULE_INT) ) {
                    int LA83_3 = input.LA(3);

                    if ( (synpred6_InternalGlobalConstantsParser()) ) {
                        alt83=1;
                    }
                }
                else if ( (LA83_1==RULE_DECIMAL) && (synpred6_InternalGlobalConstantsParser())) {
                    alt83=1;
                }
            }
            switch (alt83) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4726:4: ( ( FullStop )=>kw= FullStop ) (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL )
                    {
                    // InternalGlobalConstantsParser.g:4726:4: ( ( FullStop )=>kw= FullStop )
                    // InternalGlobalConstantsParser.g:4727:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					current.merge(kw);
                      					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_2_0());
                      				
                    }

                    }

                    // InternalGlobalConstantsParser.g:4734:4: (this_INT_5= RULE_INT | this_DECIMAL_6= RULE_DECIMAL )
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==RULE_INT) ) {
                        alt82=1;
                    }
                    else if ( (LA82_0==RULE_DECIMAL) ) {
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
                            // InternalGlobalConstantsParser.g:4735:5: this_INT_5= RULE_INT
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
                            // InternalGlobalConstantsParser.g:4743:5: this_DECIMAL_6= RULE_DECIMAL
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
    // InternalGlobalConstantsParser.g:4759:1: entryRuleTime returns [String current=null] : iv_ruleTime= ruleTime EOF ;
    public final String entryRuleTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTime = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:4761:2: (iv_ruleTime= ruleTime EOF )
            // InternalGlobalConstantsParser.g:4762:2: iv_ruleTime= ruleTime EOF
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
    // InternalGlobalConstantsParser.g:4771:1: ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) ;
    public final AntlrDatatypeRuleToken ruleTime() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_TIME_VALUE_2=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalGlobalConstantsParser.g:4778:2: ( ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE ) )
            // InternalGlobalConstantsParser.g:4779:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            {
            // InternalGlobalConstantsParser.g:4779:2: ( (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE )
            // InternalGlobalConstantsParser.g:4780:3: (kw= PlusSign | kw= HyphenMinus )? this_TIME_VALUE_2= RULE_TIME_VALUE
            {
            // InternalGlobalConstantsParser.g:4780:3: (kw= PlusSign | kw= HyphenMinus )?
            int alt84=3;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==PlusSign) ) {
                alt84=1;
            }
            else if ( (LA84_0==HyphenMinus) ) {
                alt84=2;
            }
            switch (alt84) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4781:4: kw= PlusSign
                    {
                    kw=(Token)match(input,PlusSign,FOLLOW_62); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTimeAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsParser.g:4787:4: kw= HyphenMinus
                    {
                    kw=(Token)match(input,HyphenMinus,FOLLOW_62); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:4807:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // InternalGlobalConstantsParser.g:4807:44: (iv_ruleDate= ruleDate EOF )
            // InternalGlobalConstantsParser.g:4808:2: iv_ruleDate= ruleDate EOF
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
    // InternalGlobalConstantsParser.g:4814:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4820:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT ) )
            // InternalGlobalConstantsParser.g:4821:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            {
            // InternalGlobalConstantsParser.g:4821:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT )
            // InternalGlobalConstantsParser.g:4822:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_57); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:4857:1: entryRuleDateAndTime returns [String current=null] : iv_ruleDateAndTime= ruleDateAndTime EOF ;
    public final String entryRuleDateAndTime() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDateAndTime = null;


        try {
            // InternalGlobalConstantsParser.g:4857:51: (iv_ruleDateAndTime= ruleDateAndTime EOF )
            // InternalGlobalConstantsParser.g:4858:2: iv_ruleDateAndTime= ruleDateAndTime EOF
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
    // InternalGlobalConstantsParser.g:4864:1: ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) ;
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
            // InternalGlobalConstantsParser.g:4870:2: ( (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? ) )
            // InternalGlobalConstantsParser.g:4871:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            {
            // InternalGlobalConstantsParser.g:4871:2: (this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )? )
            // InternalGlobalConstantsParser.g:4872:3: this_INT_0= RULE_INT kw= HyphenMinus this_INT_2= RULE_INT kw= HyphenMinus this_INT_4= RULE_INT kw= HyphenMinus this_INT_6= RULE_INT kw= Colon this_INT_8= RULE_INT kw= Colon this_INT_10= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4());
              		
            }
            kw=(Token)match(input,HyphenMinus,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_5());
              		
            }
            this_INT_6=(Token)match(input,RULE_INT,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_6);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_6, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_6());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_7());
              		
            }
            this_INT_8=(Token)match(input,RULE_INT,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_8);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_8, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_8());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_9());
              		
            }
            this_INT_10=(Token)match(input,RULE_INT,FOLLOW_61); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_10);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
              		
            }
            // InternalGlobalConstantsParser.g:4939:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==FullStop) ) {
                int LA85_1 = input.LA(2);

                if ( (LA85_1==RULE_INT) ) {
                    int LA85_3 = input.LA(3);

                    if ( (synpred7_InternalGlobalConstantsParser()) ) {
                        alt85=1;
                    }
                }
            }
            switch (alt85) {
                case 1 :
                    // InternalGlobalConstantsParser.g:4940:4: ( ( FullStop )=>kw= FullStop ) this_INT_12= RULE_INT
                    {
                    // InternalGlobalConstantsParser.g:4940:4: ( ( FullStop )=>kw= FullStop )
                    // InternalGlobalConstantsParser.g:4941:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_57); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:4960:1: entryRuleTimeOfDay returns [String current=null] : iv_ruleTimeOfDay= ruleTimeOfDay EOF ;
    public final String entryRuleTimeOfDay() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeOfDay = null;


        try {
            // InternalGlobalConstantsParser.g:4960:49: (iv_ruleTimeOfDay= ruleTimeOfDay EOF )
            // InternalGlobalConstantsParser.g:4961:2: iv_ruleTimeOfDay= ruleTimeOfDay EOF
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
    // InternalGlobalConstantsParser.g:4967:1: ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleTimeOfDay() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_6=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:4973:2: ( (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? ) )
            // InternalGlobalConstantsParser.g:4974:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            {
            // InternalGlobalConstantsParser.g:4974:2: (this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )? )
            // InternalGlobalConstantsParser.g:4975:3: this_INT_0= RULE_INT kw= Colon this_INT_2= RULE_INT kw= Colon this_INT_4= RULE_INT ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_1());
              		
            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_2, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_2());
              		
            }
            kw=(Token)match(input,Colon,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
              		
            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_61); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
              		
            }
            // InternalGlobalConstantsParser.g:5006:3: ( ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==FullStop) ) {
                int LA86_1 = input.LA(2);

                if ( (LA86_1==RULE_INT) ) {
                    int LA86_3 = input.LA(3);

                    if ( (synpred8_InternalGlobalConstantsParser()) ) {
                        alt86=1;
                    }
                }
            }
            switch (alt86) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5007:4: ( ( FullStop )=>kw= FullStop ) this_INT_6= RULE_INT
                    {
                    // InternalGlobalConstantsParser.g:5007:4: ( ( FullStop )=>kw= FullStop )
                    // InternalGlobalConstantsParser.g:5008:5: ( FullStop )=>kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_57); if (state.failed) return current;
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
    // InternalGlobalConstantsParser.g:5027:1: ruleSubrangeOperator returns [Enumerator current=null] : (enumLiteral_0= FullStopFullStop ) ;
    public final Enumerator ruleSubrangeOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5033:2: ( (enumLiteral_0= FullStopFullStop ) )
            // InternalGlobalConstantsParser.g:5034:2: (enumLiteral_0= FullStopFullStop )
            {
            // InternalGlobalConstantsParser.g:5034:2: (enumLiteral_0= FullStopFullStop )
            // InternalGlobalConstantsParser.g:5035:3: enumLiteral_0= FullStopFullStop
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
    // InternalGlobalConstantsParser.g:5044:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= OR ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5050:2: ( (enumLiteral_0= OR ) )
            // InternalGlobalConstantsParser.g:5051:2: (enumLiteral_0= OR )
            {
            // InternalGlobalConstantsParser.g:5051:2: (enumLiteral_0= OR )
            // InternalGlobalConstantsParser.g:5052:3: enumLiteral_0= OR
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
    // InternalGlobalConstantsParser.g:5061:1: ruleXorOperator returns [Enumerator current=null] : (enumLiteral_0= XOR ) ;
    public final Enumerator ruleXorOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5067:2: ( (enumLiteral_0= XOR ) )
            // InternalGlobalConstantsParser.g:5068:2: (enumLiteral_0= XOR )
            {
            // InternalGlobalConstantsParser.g:5068:2: (enumLiteral_0= XOR )
            // InternalGlobalConstantsParser.g:5069:3: enumLiteral_0= XOR
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
    // InternalGlobalConstantsParser.g:5078:1: ruleAndOperator returns [Enumerator current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5084:2: ( ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) )
            // InternalGlobalConstantsParser.g:5085:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            {
            // InternalGlobalConstantsParser.g:5085:2: ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) )
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==AND) ) {
                alt87=1;
            }
            else if ( (LA87_0==Ampersand) ) {
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
                    // InternalGlobalConstantsParser.g:5086:3: (enumLiteral_0= AND )
                    {
                    // InternalGlobalConstantsParser.g:5086:3: (enumLiteral_0= AND )
                    // InternalGlobalConstantsParser.g:5087:4: enumLiteral_0= AND
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
                    // InternalGlobalConstantsParser.g:5094:3: (enumLiteral_1= Ampersand )
                    {
                    // InternalGlobalConstantsParser.g:5094:3: (enumLiteral_1= Ampersand )
                    // InternalGlobalConstantsParser.g:5095:4: enumLiteral_1= Ampersand
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
    // InternalGlobalConstantsParser.g:5105:1: ruleEqualityOperator returns [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) ;
    public final Enumerator ruleEqualityOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5111:2: ( ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
            // InternalGlobalConstantsParser.g:5112:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            {
            // InternalGlobalConstantsParser.g:5112:2: ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1= LessThanSignGreaterThanSign ) )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==EqualsSign) ) {
                alt88=1;
            }
            else if ( (LA88_0==LessThanSignGreaterThanSign) ) {
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
                    // InternalGlobalConstantsParser.g:5113:3: (enumLiteral_0= EqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5113:3: (enumLiteral_0= EqualsSign )
                    // InternalGlobalConstantsParser.g:5114:4: enumLiteral_0= EqualsSign
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
                    // InternalGlobalConstantsParser.g:5121:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5121:3: (enumLiteral_1= LessThanSignGreaterThanSign )
                    // InternalGlobalConstantsParser.g:5122:4: enumLiteral_1= LessThanSignGreaterThanSign
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
    // InternalGlobalConstantsParser.g:5132:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5138:2: ( ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
            // InternalGlobalConstantsParser.g:5139:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            {
            // InternalGlobalConstantsParser.g:5139:2: ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) | (enumLiteral_3= GreaterThanSignEqualsSign ) )
            int alt89=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt89=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt89=2;
                }
                break;
            case GreaterThanSign:
                {
                alt89=3;
                }
                break;
            case GreaterThanSignEqualsSign:
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
                    // InternalGlobalConstantsParser.g:5140:3: (enumLiteral_0= LessThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5140:3: (enumLiteral_0= LessThanSign )
                    // InternalGlobalConstantsParser.g:5141:4: enumLiteral_0= LessThanSign
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
                    // InternalGlobalConstantsParser.g:5148:3: (enumLiteral_1= LessThanSignEqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5148:3: (enumLiteral_1= LessThanSignEqualsSign )
                    // InternalGlobalConstantsParser.g:5149:4: enumLiteral_1= LessThanSignEqualsSign
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
                    // InternalGlobalConstantsParser.g:5156:3: (enumLiteral_2= GreaterThanSign )
                    {
                    // InternalGlobalConstantsParser.g:5156:3: (enumLiteral_2= GreaterThanSign )
                    // InternalGlobalConstantsParser.g:5157:4: enumLiteral_2= GreaterThanSign
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
                    // InternalGlobalConstantsParser.g:5164:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    {
                    // InternalGlobalConstantsParser.g:5164:3: (enumLiteral_3= GreaterThanSignEqualsSign )
                    // InternalGlobalConstantsParser.g:5165:4: enumLiteral_3= GreaterThanSignEqualsSign
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
    // InternalGlobalConstantsParser.g:5175:1: ruleAddSubOperator returns [Enumerator current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) ;
    public final Enumerator ruleAddSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5181:2: ( ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) ) )
            // InternalGlobalConstantsParser.g:5182:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            {
            // InternalGlobalConstantsParser.g:5182:2: ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus ) )
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==PlusSign) ) {
                alt90=1;
            }
            else if ( (LA90_0==HyphenMinus) ) {
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
                    // InternalGlobalConstantsParser.g:5183:3: (enumLiteral_0= PlusSign )
                    {
                    // InternalGlobalConstantsParser.g:5183:3: (enumLiteral_0= PlusSign )
                    // InternalGlobalConstantsParser.g:5184:4: enumLiteral_0= PlusSign
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
                    // InternalGlobalConstantsParser.g:5191:3: (enumLiteral_1= HyphenMinus )
                    {
                    // InternalGlobalConstantsParser.g:5191:3: (enumLiteral_1= HyphenMinus )
                    // InternalGlobalConstantsParser.g:5192:4: enumLiteral_1= HyphenMinus
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
    // InternalGlobalConstantsParser.g:5202:1: ruleMulDivModOperator returns [Enumerator current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) ;
    public final Enumerator ruleMulDivModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5208:2: ( ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
            // InternalGlobalConstantsParser.g:5209:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            {
            // InternalGlobalConstantsParser.g:5209:2: ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
            int alt91=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt91=1;
                }
                break;
            case Solidus:
                {
                alt91=2;
                }
                break;
            case MOD:
                {
                alt91=3;
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
                    // InternalGlobalConstantsParser.g:5210:3: (enumLiteral_0= Asterisk )
                    {
                    // InternalGlobalConstantsParser.g:5210:3: (enumLiteral_0= Asterisk )
                    // InternalGlobalConstantsParser.g:5211:4: enumLiteral_0= Asterisk
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
                    // InternalGlobalConstantsParser.g:5218:3: (enumLiteral_1= Solidus )
                    {
                    // InternalGlobalConstantsParser.g:5218:3: (enumLiteral_1= Solidus )
                    // InternalGlobalConstantsParser.g:5219:4: enumLiteral_1= Solidus
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
                    // InternalGlobalConstantsParser.g:5226:3: (enumLiteral_2= MOD )
                    {
                    // InternalGlobalConstantsParser.g:5226:3: (enumLiteral_2= MOD )
                    // InternalGlobalConstantsParser.g:5227:4: enumLiteral_2= MOD
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
    // InternalGlobalConstantsParser.g:5237:1: rulePowerOperator returns [Enumerator current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
    public final Enumerator rulePowerOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5243:2: ( (enumLiteral_0= AsteriskAsterisk ) )
            // InternalGlobalConstantsParser.g:5244:2: (enumLiteral_0= AsteriskAsterisk )
            {
            // InternalGlobalConstantsParser.g:5244:2: (enumLiteral_0= AsteriskAsterisk )
            // InternalGlobalConstantsParser.g:5245:3: enumLiteral_0= AsteriskAsterisk
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
    // InternalGlobalConstantsParser.g:5254:1: ruleUnaryOperator returns [Enumerator current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) ;
    public final Enumerator ruleUnaryOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5260:2: ( ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
            // InternalGlobalConstantsParser.g:5261:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            {
            // InternalGlobalConstantsParser.g:5261:2: ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
            int alt92=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt92=1;
                }
                break;
            case PlusSign:
                {
                alt92=2;
                }
                break;
            case NOT:
                {
                alt92=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }

            switch (alt92) {
                case 1 :
                    // InternalGlobalConstantsParser.g:5262:3: (enumLiteral_0= HyphenMinus )
                    {
                    // InternalGlobalConstantsParser.g:5262:3: (enumLiteral_0= HyphenMinus )
                    // InternalGlobalConstantsParser.g:5263:4: enumLiteral_0= HyphenMinus
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
                    // InternalGlobalConstantsParser.g:5270:3: (enumLiteral_1= PlusSign )
                    {
                    // InternalGlobalConstantsParser.g:5270:3: (enumLiteral_1= PlusSign )
                    // InternalGlobalConstantsParser.g:5271:4: enumLiteral_1= PlusSign
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
                    // InternalGlobalConstantsParser.g:5278:3: (enumLiteral_2= NOT )
                    {
                    // InternalGlobalConstantsParser.g:5278:3: (enumLiteral_2= NOT )
                    // InternalGlobalConstantsParser.g:5279:4: enumLiteral_2= NOT
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
    // InternalGlobalConstantsParser.g:5289:1: ruleSTBuiltinFeature returns [Enumerator current=null] : (enumLiteral_0= THIS ) ;
    public final Enumerator ruleSTBuiltinFeature() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5295:2: ( (enumLiteral_0= THIS ) )
            // InternalGlobalConstantsParser.g:5296:2: (enumLiteral_0= THIS )
            {
            // InternalGlobalConstantsParser.g:5296:2: (enumLiteral_0= THIS )
            // InternalGlobalConstantsParser.g:5297:3: enumLiteral_0= THIS
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
    // InternalGlobalConstantsParser.g:5306:1: ruleSTMultiBitAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) ;
    public final Enumerator ruleSTMultiBitAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalGlobalConstantsParser.g:5312:2: ( ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) ) )
            // InternalGlobalConstantsParser.g:5313:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            {
            // InternalGlobalConstantsParser.g:5313:2: ( (enumLiteral_0= L ) | (enumLiteral_1= D_1 ) | (enumLiteral_2= W ) | (enumLiteral_3= B ) | (enumLiteral_4= X ) )
            int alt93=5;
            switch ( input.LA(1) ) {
            case L:
                {
                alt93=1;
                }
                break;
            case D_1:
                {
                alt93=2;
                }
                break;
            case W:
                {
                alt93=3;
                }
                break;
            case B:
                {
                alt93=4;
                }
                break;
            case X:
                {
                alt93=5;
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
                    // InternalGlobalConstantsParser.g:5314:3: (enumLiteral_0= L )
                    {
                    // InternalGlobalConstantsParser.g:5314:3: (enumLiteral_0= L )
                    // InternalGlobalConstantsParser.g:5315:4: enumLiteral_0= L
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
                    // InternalGlobalConstantsParser.g:5322:3: (enumLiteral_1= D_1 )
                    {
                    // InternalGlobalConstantsParser.g:5322:3: (enumLiteral_1= D_1 )
                    // InternalGlobalConstantsParser.g:5323:4: enumLiteral_1= D_1
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
                    // InternalGlobalConstantsParser.g:5330:3: (enumLiteral_2= W )
                    {
                    // InternalGlobalConstantsParser.g:5330:3: (enumLiteral_2= W )
                    // InternalGlobalConstantsParser.g:5331:4: enumLiteral_2= W
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
                    // InternalGlobalConstantsParser.g:5338:3: (enumLiteral_3= B )
                    {
                    // InternalGlobalConstantsParser.g:5338:3: (enumLiteral_3= B )
                    // InternalGlobalConstantsParser.g:5339:4: enumLiteral_3= B
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
                    // InternalGlobalConstantsParser.g:5346:3: (enumLiteral_4= X )
                    {
                    // InternalGlobalConstantsParser.g:5346:3: (enumLiteral_4= X )
                    // InternalGlobalConstantsParser.g:5347:4: enumLiteral_4= X
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
        // InternalGlobalConstantsParser.g:1161:6: ( ruleSTAssignmentStatement )
        // InternalGlobalConstantsParser.g:1161:7: ruleSTAssignmentStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTAssignmentStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_InternalGlobalConstantsParser

    // $ANTLR start synpred2_InternalGlobalConstantsParser
    public final void synpred2_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:1872:4: ( ( ruleSTStatement ) )
        // InternalGlobalConstantsParser.g:1872:5: ( ruleSTStatement )
        {
        // InternalGlobalConstantsParser.g:1872:5: ( ruleSTStatement )
        // InternalGlobalConstantsParser.g:1873:5: ruleSTStatement
        {
        pushFollow(FOLLOW_2);
        ruleSTStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalGlobalConstantsParser

    // $ANTLR start synpred3_InternalGlobalConstantsParser
    public final void synpred3_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:2944:4: ( ruleSTAccessExpression )
        // InternalGlobalConstantsParser.g:2944:5: ruleSTAccessExpression
        {
        pushFollow(FOLLOW_2);
        ruleSTAccessExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalGlobalConstantsParser

    // $ANTLR start synpred4_InternalGlobalConstantsParser
    public final void synpred4_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:3251:5: ( ( LeftParenthesis ) )
        // InternalGlobalConstantsParser.g:3251:6: ( LeftParenthesis )
        {
        // InternalGlobalConstantsParser.g:3251:6: ( LeftParenthesis )
        // InternalGlobalConstantsParser.g:3252:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred4_InternalGlobalConstantsParser

    // $ANTLR start synpred5_InternalGlobalConstantsParser
    public final void synpred5_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:3440:5: ( ( LeftParenthesis ) )
        // InternalGlobalConstantsParser.g:3440:6: ( LeftParenthesis )
        {
        // InternalGlobalConstantsParser.g:3440:6: ( LeftParenthesis )
        // InternalGlobalConstantsParser.g:3441:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred5_InternalGlobalConstantsParser

    // $ANTLR start synpred6_InternalGlobalConstantsParser
    public final void synpred6_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:4727:5: ( FullStop )
        // InternalGlobalConstantsParser.g:4727:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_InternalGlobalConstantsParser

    // $ANTLR start synpred7_InternalGlobalConstantsParser
    public final void synpred7_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:4941:5: ( FullStop )
        // InternalGlobalConstantsParser.g:4941:6: FullStop
        {
        match(input,FullStop,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_InternalGlobalConstantsParser

    // $ANTLR start synpred8_InternalGlobalConstantsParser
    public final void synpred8_InternalGlobalConstantsParser_fragment() throws RecognitionException {   
        // InternalGlobalConstantsParser.g:5008:5: ( FullStop )
        // InternalGlobalConstantsParser.g:5008:6: FullStop
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


    protected DFA18 dfa18 = new DFA18(this);
    protected DFA23 dfa23 = new DFA23(this);
    protected DFA34 dfa34 = new DFA34(this);
    protected DFA49 dfa49 = new DFA49(this);
    protected DFA56 dfa56 = new DFA56(this);
    protected DFA60 dfa60 = new DFA60(this);
    static final String dfa_1s = "\16\uffff";
    static final String dfa_2s = "\2\10\2\uffff\11\162\1\uffff";
    static final String dfa_3s = "\2\u00b4\2\uffff\11\u00a4\1\uffff";
    static final String dfa_4s = "\2\uffff\1\1\1\2\11\uffff\1\3";
    static final String dfa_5s = "\16\uffff}>";
    static final String[] dfa_6s = {
            "\1\2\1\uffff\1\2\5\uffff\1\2\2\uffff\1\2\50\uffff\1\2\11\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\4\2\1\uffff\2\2\2\uffff\2\2\1\uffff\2\2\1\uffff\3\2\3\uffff\2\2\1\uffff\2\2\3\uffff\3\2\1\uffff\1\2\1\uffff\2\2\1\uffff\4\2\1\uffff\1\2\1\uffff\1\2\17\uffff\1\2\1\uffff\2\2\2\uffff\1\2\3\uffff\1\1\2\uffff\1\2\1\uffff\1\2\7\uffff\2\2\1\3\2\uffff\3\2\11\uffff\2\2",
            "\1\2\1\uffff\1\2\5\uffff\1\2\2\uffff\1\2\50\uffff\1\2\11\uffff\1\2\3\uffff\1\2\1\uffff\1\2\1\uffff\4\2\1\uffff\2\2\2\uffff\2\2\1\uffff\2\2\1\uffff\3\2\3\uffff\2\2\1\uffff\2\2\3\uffff\3\2\1\uffff\1\2\1\uffff\1\2\1\6\1\uffff\2\2\1\11\1\2\1\uffff\1\2\1\uffff\1\10\17\uffff\1\13\1\uffff\1\14\1\5\2\uffff\1\7\3\uffff\1\2\2\uffff\1\2\1\uffff\1\2\7\uffff\1\12\1\2\3\uffff\3\2\11\uffff\1\4\1\2",
            "",
            "",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\6\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\2\uffff\5\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\6\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\6\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\2\2\1\15\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\6\2\1\uffff\3\2\2\uffff\3\2\2\uffff\1\2",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "739:2: (this_STElementaryInitializerExpression_0= ruleSTElementaryInitializerExpression | this_STArrayInitializerExpression_1= ruleSTArrayInitializerExpression | this_STStructInitializerExpression_2= ruleSTStructInitializerExpression )";
        }
    }
    static final String dfa_7s = "\75\uffff";
    static final String dfa_8s = "\1\10\5\uffff\62\0\5\uffff";
    static final String dfa_9s = "\1\u00b4\5\uffff\62\0\5\uffff";
    static final String dfa_10s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\62\uffff\1\10\1\11\1\12\1\6\1\7";
    static final String dfa_11s = "\6\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\5\uffff}>";
    static final String[] dfa_12s = {
            "\1\61\1\uffff\1\60\5\uffff\1\55\2\uffff\1\54\26\uffff\1\71\21\uffff\1\64\5\uffff\1\5\1\uffff\1\70\1\uffff\1\63\3\uffff\1\24\1\uffff\1\41\1\uffff\1\50\1\37\1\52\1\25\1\uffff\1\34\1\35\2\uffff\1\32\1\66\1\4\1\21\1\22\1\2\1\65\1\47\1\30\1\uffff\1\72\1\uffff\1\31\1\57\1\uffff\1\36\1\26\3\uffff\1\20\1\51\1\40\1\uffff\1\33\1\uffff\1\23\1\11\1\3\1\27\1\62\1\14\2\uffff\1\56\1\uffff\1\13\17\uffff\1\16\1\1\1\17\1\10\2\uffff\1\12\3\uffff\1\6\2\uffff\1\42\1\uffff\1\43\7\uffff\1\15\1\53\3\uffff\1\46\1\44\1\45\11\uffff\1\7\1\67",
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

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final short[] dfa_10 = DFA.unpackEncodedString(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[][] dfa_12 = unpackEncodedStringArray(dfa_12s);

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = dfa_7;
            this.eof = dfa_7;
            this.min = dfa_8;
            this.max = dfa_9;
            this.accept = dfa_10;
            this.special = dfa_11;
            this.transition = dfa_12;
        }
        public String getDescription() {
            return "1114:4: (this_STIfStatement_0= ruleSTIfStatement | this_STCaseStatement_1= ruleSTCaseStatement | this_STForStatement_2= ruleSTForStatement | this_STWhileStatement_3= ruleSTWhileStatement | this_STRepeatStatement_4= ruleSTRepeatStatement | ( ( ruleSTAssignmentStatement )=>this_STAssignmentStatement_5= ruleSTAssignmentStatement ) | this_STCallStatement_6= ruleSTCallStatement | ( () otherlv_8= RETURN ) | ( () otherlv_10= CONTINUE ) | ( () otherlv_12= EXIT ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA23_6 = input.LA(1);

                         
                        int index23_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA23_7 = input.LA(1);

                         
                        int index23_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA23_8 = input.LA(1);

                         
                        int index23_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA23_9 = input.LA(1);

                         
                        int index23_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_9);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA23_10 = input.LA(1);

                         
                        int index23_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA23_11 = input.LA(1);

                         
                        int index23_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_11);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA23_12 = input.LA(1);

                         
                        int index23_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_12);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA23_13 = input.LA(1);

                         
                        int index23_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_13);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA23_14 = input.LA(1);

                         
                        int index23_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_14);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA23_15 = input.LA(1);

                         
                        int index23_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_15);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA23_16 = input.LA(1);

                         
                        int index23_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_16);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA23_17 = input.LA(1);

                         
                        int index23_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_17);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA23_18 = input.LA(1);

                         
                        int index23_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_18);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA23_19 = input.LA(1);

                         
                        int index23_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_19);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA23_20 = input.LA(1);

                         
                        int index23_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_20);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA23_21 = input.LA(1);

                         
                        int index23_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_21);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA23_22 = input.LA(1);

                         
                        int index23_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_22);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA23_23 = input.LA(1);

                         
                        int index23_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_23);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA23_24 = input.LA(1);

                         
                        int index23_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_24);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA23_25 = input.LA(1);

                         
                        int index23_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_25);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA23_26 = input.LA(1);

                         
                        int index23_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_26);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA23_27 = input.LA(1);

                         
                        int index23_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_27);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA23_28 = input.LA(1);

                         
                        int index23_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_28);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA23_29 = input.LA(1);

                         
                        int index23_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_29);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA23_30 = input.LA(1);

                         
                        int index23_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_30);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA23_31 = input.LA(1);

                         
                        int index23_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_31);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA23_32 = input.LA(1);

                         
                        int index23_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_32);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA23_33 = input.LA(1);

                         
                        int index23_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_33);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA23_34 = input.LA(1);

                         
                        int index23_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_34);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA23_35 = input.LA(1);

                         
                        int index23_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_35);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA23_36 = input.LA(1);

                         
                        int index23_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_36);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA23_37 = input.LA(1);

                         
                        int index23_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_37);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA23_38 = input.LA(1);

                         
                        int index23_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_38);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA23_39 = input.LA(1);

                         
                        int index23_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_39);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA23_40 = input.LA(1);

                         
                        int index23_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_40);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA23_41 = input.LA(1);

                         
                        int index23_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_41);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA23_42 = input.LA(1);

                         
                        int index23_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_42);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA23_43 = input.LA(1);

                         
                        int index23_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_43);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA23_44 = input.LA(1);

                         
                        int index23_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_44);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA23_45 = input.LA(1);

                         
                        int index23_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_45);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA23_46 = input.LA(1);

                         
                        int index23_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_46);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA23_47 = input.LA(1);

                         
                        int index23_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_47);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA23_48 = input.LA(1);

                         
                        int index23_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_48);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA23_49 = input.LA(1);

                         
                        int index23_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_49);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA23_50 = input.LA(1);

                         
                        int index23_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_50);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA23_51 = input.LA(1);

                         
                        int index23_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_51);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA23_52 = input.LA(1);

                         
                        int index23_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_52);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA23_53 = input.LA(1);

                         
                        int index23_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_53);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA23_54 = input.LA(1);

                         
                        int index23_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_54);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA23_55 = input.LA(1);

                         
                        int index23_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalGlobalConstantsParser()) ) {s = 59;}

                        else if ( (true) ) {s = 60;}

                         
                        input.seek(index23_55);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 23, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_13s = "\100\uffff";
    static final String dfa_14s = "\1\1\77\uffff";
    static final String dfa_15s = "\1\10\2\uffff\62\0\13\uffff";
    static final String dfa_16s = "\1\u00b4\2\uffff\62\0\13\uffff";
    static final String dfa_17s = "\1\uffff\1\2\65\uffff\11\1";
    static final String dfa_18s = "\1\0\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\13\uffff}>";
    static final String[] dfa_19s = {
            "\1\56\1\uffff\1\55\5\uffff\1\52\2\uffff\1\51\26\uffff\1\75\1\1\20\uffff\1\61\5\uffff\1\73\1\uffff\1\74\1\uffff\1\60\3\uffff\1\21\1\uffff\1\36\1\uffff\1\45\1\34\1\47\1\22\1\uffff\1\31\1\32\2\uffff\1\27\1\63\1\72\1\16\1\17\1\70\1\62\1\44\1\25\1\1\1\76\1\uffff\1\26\1\54\1\uffff\1\33\1\23\3\uffff\1\15\1\46\1\35\1\uffff\1\30\1\uffff\1\20\1\6\1\71\1\24\1\57\1\11\1\1\1\uffff\1\53\1\uffff\1\10\17\uffff\1\13\1\67\1\14\1\5\2\uffff\1\7\3\uffff\1\3\2\uffff\1\37\1\uffff\1\40\3\uffff\1\77\3\uffff\1\12\1\50\3\uffff\1\43\1\41\1\42\11\uffff\1\4\1\64",
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

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = dfa_13;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "()* loopback of 1871:3: ( ( ( ruleSTStatement ) )=> (lv_statements_4_0= ruleSTStatement ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA34_0 = input.LA(1);

                         
                        int index34_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA34_0==EOF||LA34_0==END_CASE||LA34_0==ELSE||LA34_0==NOT) ) {s = 1;}

                        else if ( (LA34_0==LeftParenthesis) ) {s = 3;}

                        else if ( (LA34_0==RULE_ID) ) {s = 4;}

                        else if ( (LA34_0==LT) ) {s = 5;}

                        else if ( (LA34_0==AND) ) {s = 6;}

                        else if ( (LA34_0==OR) ) {s = 7;}

                        else if ( (LA34_0==XOR) ) {s = 8;}

                        else if ( (LA34_0==MOD) ) {s = 9;}

                        else if ( (LA34_0==D) ) {s = 10;}

                        else if ( (LA34_0==DT) ) {s = 11;}

                        else if ( (LA34_0==LD) ) {s = 12;}

                        else if ( (LA34_0==THIS) ) {s = 13;}

                        else if ( (LA34_0==BOOL) ) {s = 14;}

                        else if ( (LA34_0==BYTE) ) {s = 15;}

                        else if ( (LA34_0==WORD) ) {s = 16;}

                        else if ( (LA34_0==DWORD) ) {s = 17;}

                        else if ( (LA34_0==LWORD) ) {s = 18;}

                        else if ( (LA34_0==SINT) ) {s = 19;}

                        else if ( (LA34_0==INT) ) {s = 20;}

                        else if ( (LA34_0==DINT) ) {s = 21;}

                        else if ( (LA34_0==LINT) ) {s = 22;}

                        else if ( (LA34_0==USINT) ) {s = 23;}

                        else if ( (LA34_0==UINT) ) {s = 24;}

                        else if ( (LA34_0==UDINT) ) {s = 25;}

                        else if ( (LA34_0==ULINT) ) {s = 26;}

                        else if ( (LA34_0==REAL) ) {s = 27;}

                        else if ( (LA34_0==LREAL) ) {s = 28;}

                        else if ( (LA34_0==TRUE) ) {s = 29;}

                        else if ( (LA34_0==FALSE) ) {s = 30;}

                        else if ( (LA34_0==PlusSign) ) {s = 31;}

                        else if ( (LA34_0==HyphenMinus) ) {s = 32;}

                        else if ( (LA34_0==RULE_INT) ) {s = 33;}

                        else if ( (LA34_0==RULE_DECIMAL) ) {s = 34;}

                        else if ( (LA34_0==RULE_NON_DECIMAL) ) {s = 35;}

                        else if ( (LA34_0==DATE) ) {s = 36;}

                        else if ( (LA34_0==LDATE) ) {s = 37;}

                        else if ( (LA34_0==TIME) ) {s = 38;}

                        else if ( (LA34_0==LTIME) ) {s = 39;}

                        else if ( (LA34_0==T) ) {s = 40;}

                        else if ( (LA34_0==TIME_OF_DAY) ) {s = 41;}

                        else if ( (LA34_0==LTIME_OF_DAY) ) {s = 42;}

                        else if ( (LA34_0==TOD) ) {s = 43;}

                        else if ( (LA34_0==LTOD) ) {s = 44;}

                        else if ( (LA34_0==DATE_AND_TIME) ) {s = 45;}

                        else if ( (LA34_0==LDATE_AND_TIME) ) {s = 46;}

                        else if ( (LA34_0==LDT) ) {s = 47;}

                        else if ( (LA34_0==STRING) ) {s = 48;}

                        else if ( (LA34_0==WSTRING) ) {s = 49;}

                        else if ( (LA34_0==CHAR) ) {s = 50;}

                        else if ( (LA34_0==WCHAR) ) {s = 51;}

                        else if ( (LA34_0==RULE_STRING) ) {s = 52;}

                        else if ( (LA34_0==IF) && (synpred2_InternalGlobalConstantsParser())) {s = 55;}

                        else if ( (LA34_0==CASE) && (synpred2_InternalGlobalConstantsParser())) {s = 56;}

                        else if ( (LA34_0==FOR) && (synpred2_InternalGlobalConstantsParser())) {s = 57;}

                        else if ( (LA34_0==WHILE) && (synpred2_InternalGlobalConstantsParser())) {s = 58;}

                        else if ( (LA34_0==REPEAT) && (synpred2_InternalGlobalConstantsParser())) {s = 59;}

                        else if ( (LA34_0==RETURN) && (synpred2_InternalGlobalConstantsParser())) {s = 60;}

                        else if ( (LA34_0==CONTINUE) && (synpred2_InternalGlobalConstantsParser())) {s = 61;}

                        else if ( (LA34_0==EXIT) && (synpred2_InternalGlobalConstantsParser())) {s = 62;}

                        else if ( (LA34_0==Semicolon) && (synpred2_InternalGlobalConstantsParser())) {s = 63;}

                         
                        input.seek(index34_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA34_3 = input.LA(1);

                         
                        int index34_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA34_4 = input.LA(1);

                         
                        int index34_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA34_5 = input.LA(1);

                         
                        int index34_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA34_6 = input.LA(1);

                         
                        int index34_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA34_7 = input.LA(1);

                         
                        int index34_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA34_8 = input.LA(1);

                         
                        int index34_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA34_9 = input.LA(1);

                         
                        int index34_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA34_10 = input.LA(1);

                         
                        int index34_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA34_11 = input.LA(1);

                         
                        int index34_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA34_12 = input.LA(1);

                         
                        int index34_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA34_13 = input.LA(1);

                         
                        int index34_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_13);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA34_14 = input.LA(1);

                         
                        int index34_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_14);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA34_15 = input.LA(1);

                         
                        int index34_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_15);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA34_16 = input.LA(1);

                         
                        int index34_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_16);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA34_17 = input.LA(1);

                         
                        int index34_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_17);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA34_18 = input.LA(1);

                         
                        int index34_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_18);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA34_19 = input.LA(1);

                         
                        int index34_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA34_20 = input.LA(1);

                         
                        int index34_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_20);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA34_21 = input.LA(1);

                         
                        int index34_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_21);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA34_22 = input.LA(1);

                         
                        int index34_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_22);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA34_23 = input.LA(1);

                         
                        int index34_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_23);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA34_24 = input.LA(1);

                         
                        int index34_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA34_25 = input.LA(1);

                         
                        int index34_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_25);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA34_26 = input.LA(1);

                         
                        int index34_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_26);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA34_27 = input.LA(1);

                         
                        int index34_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_27);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA34_28 = input.LA(1);

                         
                        int index34_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_28);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA34_29 = input.LA(1);

                         
                        int index34_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_29);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA34_30 = input.LA(1);

                         
                        int index34_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_30);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA34_31 = input.LA(1);

                         
                        int index34_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_31);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA34_32 = input.LA(1);

                         
                        int index34_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_32);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA34_33 = input.LA(1);

                         
                        int index34_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_33);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA34_34 = input.LA(1);

                         
                        int index34_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_34);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA34_35 = input.LA(1);

                         
                        int index34_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA34_36 = input.LA(1);

                         
                        int index34_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_36);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA34_37 = input.LA(1);

                         
                        int index34_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_37);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA34_38 = input.LA(1);

                         
                        int index34_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_38);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA34_39 = input.LA(1);

                         
                        int index34_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_39);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA34_40 = input.LA(1);

                         
                        int index34_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_40);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA34_41 = input.LA(1);

                         
                        int index34_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_41);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA34_42 = input.LA(1);

                         
                        int index34_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_42);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA34_43 = input.LA(1);

                         
                        int index34_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_43);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA34_44 = input.LA(1);

                         
                        int index34_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_44);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA34_45 = input.LA(1);

                         
                        int index34_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_45);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA34_46 = input.LA(1);

                         
                        int index34_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_46);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA34_47 = input.LA(1);

                         
                        int index34_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_47);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA34_48 = input.LA(1);

                         
                        int index34_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_48);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA34_49 = input.LA(1);

                         
                        int index34_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_49);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA34_50 = input.LA(1);

                         
                        int index34_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_50);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA34_51 = input.LA(1);

                         
                        int index34_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_51);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA34_52 = input.LA(1);

                         
                        int index34_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGlobalConstantsParser()) ) {s = 63;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index34_52);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 34, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_20s = "\64\uffff";
    static final String dfa_21s = "\1\10\34\uffff\2\0\25\uffff";
    static final String dfa_22s = "\1\u00b4\34\uffff\2\0\25\uffff";
    static final String dfa_23s = "\1\uffff\34\1\2\uffff\24\1\1\2";
    static final String dfa_24s = "\1\0\34\uffff\1\2\1\1\25\uffff}>";
    static final String[] dfa_25s = {
            "\1\54\1\uffff\1\53\5\uffff\1\50\2\uffff\1\47\50\uffff\1\57\11\uffff\1\56\3\uffff\1\17\1\uffff\1\34\1\uffff\1\43\1\32\1\45\1\20\1\uffff\1\27\1\30\2\uffff\1\25\1\61\1\uffff\1\14\1\15\1\uffff\1\60\1\42\1\23\3\uffff\1\24\1\52\1\uffff\1\31\1\21\3\uffff\1\13\1\44\1\33\1\uffff\1\26\1\uffff\1\16\1\4\1\uffff\1\22\1\55\1\7\1\63\1\uffff\1\51\1\uffff\1\6\17\uffff\1\11\1\uffff\1\12\1\3\2\uffff\1\5\3\uffff\1\1\2\uffff\1\35\1\uffff\1\36\7\uffff\1\10\1\46\3\uffff\1\41\1\37\1\40\11\uffff\1\2\1\62",
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

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = dfa_20;
            this.eof = dfa_20;
            this.min = dfa_21;
            this.max = dfa_22;
            this.accept = dfa_23;
            this.special = dfa_24;
            this.transition = dfa_25;
        }
        public String getDescription() {
            return "2942:2: ( ( ( ruleSTAccessExpression )=>this_STAccessExpression_0= ruleSTAccessExpression ) | ( () ( (lv_op_2_0= ruleUnaryOperator ) ) ( (lv_expression_3_0= ruleSTUnaryExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA49_0 = input.LA(1);

                         
                        int index49_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA49_0==LeftParenthesis) && (synpred3_InternalGlobalConstantsParser())) {s = 1;}

                        else if ( (LA49_0==RULE_ID) && (synpred3_InternalGlobalConstantsParser())) {s = 2;}

                        else if ( (LA49_0==LT) && (synpred3_InternalGlobalConstantsParser())) {s = 3;}

                        else if ( (LA49_0==AND) && (synpred3_InternalGlobalConstantsParser())) {s = 4;}

                        else if ( (LA49_0==OR) && (synpred3_InternalGlobalConstantsParser())) {s = 5;}

                        else if ( (LA49_0==XOR) && (synpred3_InternalGlobalConstantsParser())) {s = 6;}

                        else if ( (LA49_0==MOD) && (synpred3_InternalGlobalConstantsParser())) {s = 7;}

                        else if ( (LA49_0==D) && (synpred3_InternalGlobalConstantsParser())) {s = 8;}

                        else if ( (LA49_0==DT) && (synpred3_InternalGlobalConstantsParser())) {s = 9;}

                        else if ( (LA49_0==LD) && (synpred3_InternalGlobalConstantsParser())) {s = 10;}

                        else if ( (LA49_0==THIS) && (synpred3_InternalGlobalConstantsParser())) {s = 11;}

                        else if ( (LA49_0==BOOL) && (synpred3_InternalGlobalConstantsParser())) {s = 12;}

                        else if ( (LA49_0==BYTE) && (synpred3_InternalGlobalConstantsParser())) {s = 13;}

                        else if ( (LA49_0==WORD) && (synpred3_InternalGlobalConstantsParser())) {s = 14;}

                        else if ( (LA49_0==DWORD) && (synpred3_InternalGlobalConstantsParser())) {s = 15;}

                        else if ( (LA49_0==LWORD) && (synpred3_InternalGlobalConstantsParser())) {s = 16;}

                        else if ( (LA49_0==SINT) && (synpred3_InternalGlobalConstantsParser())) {s = 17;}

                        else if ( (LA49_0==INT) && (synpred3_InternalGlobalConstantsParser())) {s = 18;}

                        else if ( (LA49_0==DINT) && (synpred3_InternalGlobalConstantsParser())) {s = 19;}

                        else if ( (LA49_0==LINT) && (synpred3_InternalGlobalConstantsParser())) {s = 20;}

                        else if ( (LA49_0==USINT) && (synpred3_InternalGlobalConstantsParser())) {s = 21;}

                        else if ( (LA49_0==UINT) && (synpred3_InternalGlobalConstantsParser())) {s = 22;}

                        else if ( (LA49_0==UDINT) && (synpred3_InternalGlobalConstantsParser())) {s = 23;}

                        else if ( (LA49_0==ULINT) && (synpred3_InternalGlobalConstantsParser())) {s = 24;}

                        else if ( (LA49_0==REAL) && (synpred3_InternalGlobalConstantsParser())) {s = 25;}

                        else if ( (LA49_0==LREAL) && (synpred3_InternalGlobalConstantsParser())) {s = 26;}

                        else if ( (LA49_0==TRUE) && (synpred3_InternalGlobalConstantsParser())) {s = 27;}

                        else if ( (LA49_0==FALSE) && (synpred3_InternalGlobalConstantsParser())) {s = 28;}

                        else if ( (LA49_0==PlusSign) ) {s = 29;}

                        else if ( (LA49_0==HyphenMinus) ) {s = 30;}

                        else if ( (LA49_0==RULE_INT) && (synpred3_InternalGlobalConstantsParser())) {s = 31;}

                        else if ( (LA49_0==RULE_DECIMAL) && (synpred3_InternalGlobalConstantsParser())) {s = 32;}

                        else if ( (LA49_0==RULE_NON_DECIMAL) && (synpred3_InternalGlobalConstantsParser())) {s = 33;}

                        else if ( (LA49_0==DATE) && (synpred3_InternalGlobalConstantsParser())) {s = 34;}

                        else if ( (LA49_0==LDATE) && (synpred3_InternalGlobalConstantsParser())) {s = 35;}

                        else if ( (LA49_0==TIME) && (synpred3_InternalGlobalConstantsParser())) {s = 36;}

                        else if ( (LA49_0==LTIME) && (synpred3_InternalGlobalConstantsParser())) {s = 37;}

                        else if ( (LA49_0==T) && (synpred3_InternalGlobalConstantsParser())) {s = 38;}

                        else if ( (LA49_0==TIME_OF_DAY) && (synpred3_InternalGlobalConstantsParser())) {s = 39;}

                        else if ( (LA49_0==LTIME_OF_DAY) && (synpred3_InternalGlobalConstantsParser())) {s = 40;}

                        else if ( (LA49_0==TOD) && (synpred3_InternalGlobalConstantsParser())) {s = 41;}

                        else if ( (LA49_0==LTOD) && (synpred3_InternalGlobalConstantsParser())) {s = 42;}

                        else if ( (LA49_0==DATE_AND_TIME) && (synpred3_InternalGlobalConstantsParser())) {s = 43;}

                        else if ( (LA49_0==LDATE_AND_TIME) && (synpred3_InternalGlobalConstantsParser())) {s = 44;}

                        else if ( (LA49_0==LDT) && (synpred3_InternalGlobalConstantsParser())) {s = 45;}

                        else if ( (LA49_0==STRING) && (synpred3_InternalGlobalConstantsParser())) {s = 46;}

                        else if ( (LA49_0==WSTRING) && (synpred3_InternalGlobalConstantsParser())) {s = 47;}

                        else if ( (LA49_0==CHAR) && (synpred3_InternalGlobalConstantsParser())) {s = 48;}

                        else if ( (LA49_0==WCHAR) && (synpred3_InternalGlobalConstantsParser())) {s = 49;}

                        else if ( (LA49_0==RULE_STRING) && (synpred3_InternalGlobalConstantsParser())) {s = 50;}

                        else if ( (LA49_0==NOT) ) {s = 51;}

                         
                        input.seek(index49_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA49_30 = input.LA(1);

                         
                        int index49_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGlobalConstantsParser()) ) {s = 50;}

                        else if ( (true) ) {s = 51;}

                         
                        input.seek(index49_30);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA49_29 = input.LA(1);

                         
                        int index49_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGlobalConstantsParser()) ) {s = 50;}

                        else if ( (true) ) {s = 51;}

                         
                        input.seek(index49_29);
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
    static final String dfa_26s = "\43\uffff";
    static final String dfa_27s = "\1\2\42\uffff";
    static final String dfa_28s = "\1\26\1\0\41\uffff";
    static final String dfa_29s = "\1\u00a5\1\0\41\uffff";
    static final String dfa_30s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_31s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_32s = {
            "\1\2\123\uffff\1\2\7\uffff\1\2\3\uffff\1\2\4\uffff\1\2\5\uffff\5\2\1\uffff\1\2\1\uffff\2\2\4\uffff\1\2\1\uffff\2\2\1\uffff\1\2\1\1\14\2\2\uffff\2\2",
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

    class DFA56 extends DFA {

        public DFA56(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 56;
            this.eot = dfa_26;
            this.eof = dfa_27;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_31;
            this.transition = dfa_32;
        }
        public String getDescription() {
            return "3249:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA56_1 = input.LA(1);

                         
                        int index56_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalGlobalConstantsParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index56_1);
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

    class DFA60 extends DFA {

        public DFA60(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 60;
            this.eot = dfa_26;
            this.eof = dfa_27;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_31;
            this.transition = dfa_32;
        }
        public String getDescription() {
            return "3438:3: ( ( ( ( LeftParenthesis ) )=> (lv_call_2_0= LeftParenthesis ) ) ( ( (lv_parameters_3_0= ruleSTCallArgument ) ) (otherlv_4= Comma ( (lv_parameters_5_0= ruleSTCallArgument ) ) )* )? otherlv_6= RightParenthesis )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA60_1 = input.LA(1);

                         
                        int index60_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalGlobalConstantsParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index60_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 60, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0040020000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0040000000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000100L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x1000000000090500L,0x023290D8ED9BC540L,0x0008000000000800L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x1000000000090500L,0x0AF6B8D8ED9BD540L,0x0018038C05226800L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000002002000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000001040000008L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000008L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x1000000000090500L,0x0AF6B8D8ED9BD540L,0x0018039C05226800L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002400000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0844000000000000L,0x0008000400026800L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x5000040000090500L,0x0A7EB8DBFF9BDD54L,0x0018038C45227800L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x4000000000000000L,0x0000000100000800L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x1000040000090502L,0x0A7EB8DAFF9BD554L,0x0018038C45227800L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x1000080000090500L,0x0AF6B8D9ED9BD540L,0x0018038C05226800L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000022000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x1020040000090500L,0x0A7EB8DAFF9BD554L,0x0018038C45227800L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x1000040400090500L,0x0A7EB8DAFF9BD554L,0x0018038C45227800L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x1000040000090500L,0x0A7EB8DAFFBBD554L,0x0018038C45227800L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000002L,0x0800000000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0004000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000100000020L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000280000090L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000005000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L,0x0000000010800000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001008000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0xF844000000000000L,0x0008010400226801L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x1000000000090500L,0x0AF6B8D8ED9BD540L,0x0018038C05626800L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000200000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0012A0C88C9A9400L,0x0000038005000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000080005000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000030000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});

}