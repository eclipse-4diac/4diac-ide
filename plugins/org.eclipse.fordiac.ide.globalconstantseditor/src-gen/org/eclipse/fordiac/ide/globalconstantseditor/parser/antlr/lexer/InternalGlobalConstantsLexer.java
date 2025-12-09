package org.eclipse.fordiac.ide.globalconstantseditor.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

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
public class InternalGlobalConstantsLexer extends Lexer {
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

    public InternalGlobalConstantsLexer() {;} 
    public InternalGlobalConstantsLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalGlobalConstantsLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalGlobalConstantsLexer.g"; }

    // $ANTLR start "END_GLOBALCONSTANTS"
    public final void mEND_GLOBALCONSTANTS() throws RecognitionException {
        try {
            int _type = END_GLOBALCONSTANTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:25:21: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:25:23: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_GLOBALCONSTANTS"

    // $ANTLR start "END_FUNCTION_BLOCK"
    public final void mEND_FUNCTION_BLOCK() throws RecognitionException {
        try {
            int _type = END_FUNCTION_BLOCK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:27:20: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' ) )
            // InternalGlobalConstantsLexer.g:27:22: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_FUNCTION_BLOCK"

    // $ANTLR start "END_CONFIGURATION"
    public final void mEND_CONFIGURATION() throws RecognitionException {
        try {
            int _type = END_CONFIGURATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:29:19: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:29:21: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_CONFIGURATION"

    // $ANTLR start "GLOBALCONSTANTS"
    public final void mGLOBALCONSTANTS() throws RecognitionException {
        try {
            int _type = GLOBALCONSTANTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:31:17: ( ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:31:19: ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GLOBALCONSTANTS"

    // $ANTLR start "END_TRANSITION"
    public final void mEND_TRANSITION() throws RecognitionException {
        try {
            int _type = END_TRANSITION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:33:16: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:33:18: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_TRANSITION"

    // $ANTLR start "FUNCTION_BLOCK"
    public final void mFUNCTION_BLOCK() throws RecognitionException {
        try {
            int _type = FUNCTION_BLOCK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:35:16: ( ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' ) )
            // InternalGlobalConstantsLexer.g:35:18: ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FUNCTION_BLOCK"

    // $ANTLR start "LDATE_AND_TIME"
    public final void mLDATE_AND_TIME() throws RecognitionException {
        try {
            int _type = LDATE_AND_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:37:16: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:37:18: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LDATE_AND_TIME"

    // $ANTLR start "CONFIGURATION"
    public final void mCONFIGURATION() throws RecognitionException {
        try {
            int _type = CONFIGURATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:39:15: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:39:17: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONFIGURATION"

    // $ANTLR start "DATE_AND_TIME"
    public final void mDATE_AND_TIME() throws RecognitionException {
        try {
            int _type = DATE_AND_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:41:15: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:41:17: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATE_AND_TIME"

    // $ANTLR start "END_INTERFACE"
    public final void mEND_INTERFACE() throws RecognitionException {
        try {
            int _type = END_INTERFACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:43:15: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:43:17: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_INTERFACE"

    // $ANTLR start "END_NAMESPACE"
    public final void mEND_NAMESPACE() throws RecognitionException {
        try {
            int _type = END_NAMESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:45:15: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:45:17: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_NAMESPACE"

    // $ANTLR start "END_FUNCTION"
    public final void mEND_FUNCTION() throws RecognitionException {
        try {
            int _type = END_FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:47:14: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:47:16: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_FUNCTION"

    // $ANTLR start "END_RESOURCE"
    public final void mEND_RESOURCE() throws RecognitionException {
        try {
            int _type = END_RESOURCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:49:14: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:49:16: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_RESOURCE"

    // $ANTLR start "INITIAL_STEP"
    public final void mINITIAL_STEP() throws RecognitionException {
        try {
            int _type = INITIAL_STEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:51:14: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalGlobalConstantsLexer.g:51:16: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INITIAL_STEP"

    // $ANTLR start "LTIME_OF_DAY"
    public final void mLTIME_OF_DAY() throws RecognitionException {
        try {
            int _type = LTIME_OF_DAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:53:14: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalGlobalConstantsLexer.g:53:16: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LTIME_OF_DAY"

    // $ANTLR start "VAR_EXTERNAL"
    public final void mVAR_EXTERNAL() throws RecognitionException {
        try {
            int _type = VAR_EXTERNAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:55:14: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:55:16: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_EXTERNAL"

    // $ANTLR start "END_PROGRAM"
    public final void mEND_PROGRAM() throws RecognitionException {
        try {
            int _type = END_PROGRAM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:57:13: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' ) )
            // InternalGlobalConstantsLexer.g:57:15: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_PROGRAM"

    // $ANTLR start "TIME_OF_DAY"
    public final void mTIME_OF_DAY() throws RecognitionException {
        try {
            int _type = TIME_OF_DAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:59:13: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalGlobalConstantsLexer.g:59:15: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIME_OF_DAY"

    // $ANTLR start "END_ACTION"
    public final void mEND_ACTION() throws RecognitionException {
        try {
            int _type = END_ACTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:61:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:61:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_ACTION"

    // $ANTLR start "END_METHOD"
    public final void mEND_METHOD() throws RecognitionException {
        try {
            int _type = END_METHOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:63:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:63:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_METHOD"

    // $ANTLR start "END_REPEAT"
    public final void mEND_REPEAT() throws RecognitionException {
        try {
            int _type = END_REPEAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:65:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:65:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_REPEAT"

    // $ANTLR start "END_STRUCT"
    public final void mEND_STRUCT() throws RecognitionException {
        try {
            int _type = END_STRUCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:67:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:67:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_STRUCT"

    // $ANTLR start "IMPLEMENTS"
    public final void mIMPLEMENTS() throws RecognitionException {
        try {
            int _type = IMPLEMENTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:69:12: ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:69:14: ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IMPLEMENTS"

    // $ANTLR start "NON_RETAIN"
    public final void mNON_RETAIN() throws RecognitionException {
        try {
            int _type = NON_RETAIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:71:12: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:71:14: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NON_RETAIN"

    // $ANTLR start "READ_WRITE"
    public final void mREAD_WRITE() throws RecognitionException {
        try {
            int _type = READ_WRITE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:73:12: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:73:14: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "READ_WRITE"

    // $ANTLR start "TRANSITION"
    public final void mTRANSITION() throws RecognitionException {
        try {
            int _type = TRANSITION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:75:12: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:75:14: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRANSITION"

    // $ANTLR start "VAR_ACCESS"
    public final void mVAR_ACCESS() throws RecognitionException {
        try {
            int _type = VAR_ACCESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:77:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:77:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_ACCESS"

    // $ANTLR start "VAR_CONFIG"
    public final void mVAR_CONFIG() throws RecognitionException {
        try {
            int _type = VAR_CONFIG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:79:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) )
            // InternalGlobalConstantsLexer.g:79:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_CONFIG"

    // $ANTLR start "VAR_GLOBAL"
    public final void mVAR_GLOBAL() throws RecognitionException {
        try {
            int _type = VAR_GLOBAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:81:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:81:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_GLOBAL"

    // $ANTLR start "VAR_IN_OUT"
    public final void mVAR_IN_OUT() throws RecognitionException {
        try {
            int _type = VAR_IN_OUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:83:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:83:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_IN_OUT"

    // $ANTLR start "VAR_OUTPUT"
    public final void mVAR_OUTPUT() throws RecognitionException {
        try {
            int _type = VAR_OUTPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:85:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:85:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_OUTPUT"

    // $ANTLR start "END_CLASS"
    public final void mEND_CLASS() throws RecognitionException {
        try {
            int _type = END_CLASS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:87:11: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:87:13: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_CLASS"

    // $ANTLR start "END_WHILE"
    public final void mEND_WHILE() throws RecognitionException {
        try {
            int _type = END_WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:89:11: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:89:13: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_WHILE"

    // $ANTLR start "INTERFACE"
    public final void mINTERFACE() throws RecognitionException {
        try {
            int _type = INTERFACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:91:11: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:91:13: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTERFACE"

    // $ANTLR start "NAMESPACE"
    public final void mNAMESPACE() throws RecognitionException {
        try {
            int _type = NAMESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:93:11: ( ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:93:13: ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NAMESPACE"

    // $ANTLR start "PROTECTED"
    public final void mPROTECTED() throws RecognitionException {
        try {
            int _type = PROTECTED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:95:11: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:95:13: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROTECTED"

    // $ANTLR start "READ_ONLY"
    public final void mREAD_ONLY() throws RecognitionException {
        try {
            int _type = READ_ONLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:97:11: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'L' | 'l' ) ( 'Y' | 'y' ) )
            // InternalGlobalConstantsLexer.g:97:13: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'L' | 'l' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "READ_ONLY"

    // $ANTLR start "VAR_INPUT"
    public final void mVAR_INPUT() throws RecognitionException {
        try {
            int _type = VAR_INPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:99:11: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:99:13: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_INPUT"

    // $ANTLR start "ABSTRACT"
    public final void mABSTRACT() throws RecognitionException {
        try {
            int _type = ABSTRACT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:101:10: ( ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:101:12: ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ABSTRACT"

    // $ANTLR start "CONSTANT"
    public final void mCONSTANT() throws RecognitionException {
        try {
            int _type = CONSTANT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:103:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:103:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONSTANT"

    // $ANTLR start "CONTINUE"
    public final void mCONTINUE() throws RecognitionException {
        try {
            int _type = CONTINUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:105:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:105:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTINUE"

    // $ANTLR start "END_CASE"
    public final void mEND_CASE() throws RecognitionException {
        try {
            int _type = END_CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:107:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:107:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_CASE"

    // $ANTLR start "END_STEP"
    public final void mEND_STEP() throws RecognitionException {
        try {
            int _type = END_STEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:109:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalGlobalConstantsLexer.g:109:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_STEP"

    // $ANTLR start "END_TYPE"
    public final void mEND_TYPE() throws RecognitionException {
        try {
            int _type = END_TYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:111:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:111:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_TYPE"

    // $ANTLR start "FUNCTION"
    public final void mFUNCTION() throws RecognitionException {
        try {
            int _type = FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:113:10: ( ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:113:12: ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FUNCTION"

    // $ANTLR start "INTERNAL"
    public final void mINTERNAL() throws RecognitionException {
        try {
            int _type = INTERNAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:115:10: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:115:12: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTERNAL"

    // $ANTLR start "INTERVAL"
    public final void mINTERVAL() throws RecognitionException {
        try {
            int _type = INTERVAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:117:10: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:117:12: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTERVAL"

    // $ANTLR start "OVERRIDE"
    public final void mOVERRIDE() throws RecognitionException {
        try {
            int _type = OVERRIDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:119:10: ( ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'D' | 'd' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:119:12: ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'D' | 'd' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OVERRIDE"

    // $ANTLR start "PRIORITY"
    public final void mPRIORITY() throws RecognitionException {
        try {
            int _type = PRIORITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:121:10: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'Y' | 'y' ) )
            // InternalGlobalConstantsLexer.g:121:12: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRIORITY"

    // $ANTLR start "RESOURCE"
    public final void mRESOURCE() throws RecognitionException {
        try {
            int _type = RESOURCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:123:10: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:123:12: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RESOURCE"

    // $ANTLR start "VAR_TEMP"
    public final void mVAR_TEMP() throws RecognitionException {
        try {
            int _type = VAR_TEMP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:125:10: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'T' | 't' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'P' | 'p' ) )
            // InternalGlobalConstantsLexer.g:125:12: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'T' | 't' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'P' | 'p' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_TEMP"

    // $ANTLR start "END_FOR"
    public final void mEND_FOR() throws RecognitionException {
        try {
            int _type = END_FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:127:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:127:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_FOR"

    // $ANTLR start "END_VAR"
    public final void mEND_VAR() throws RecognitionException {
        try {
            int _type = END_VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:129:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:129:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_VAR"

    // $ANTLR start "EXTENDS"
    public final void mEXTENDS() throws RecognitionException {
        try {
            int _type = EXTENDS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:131:9: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:131:11: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXTENDS"

    // $ANTLR start "INTERAL"
    public final void mINTERAL() throws RecognitionException {
        try {
            int _type = INTERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:133:9: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:133:11: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTERAL"

    // $ANTLR start "OVERLAP"
    public final void mOVERLAP() throws RecognitionException {
        try {
            int _type = OVERLAP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:135:9: ( ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'P' | 'p' ) )
            // InternalGlobalConstantsLexer.g:135:11: ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'P' | 'p' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OVERLAP"

    // $ANTLR start "PACKAGE"
    public final void mPACKAGE() throws RecognitionException {
        try {
            int _type = PACKAGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:137:9: ( ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'K' | 'k' ) ( 'A' | 'a' ) ( 'G' | 'g' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:137:11: ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'K' | 'k' ) ( 'A' | 'a' ) ( 'G' | 'g' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PACKAGE"

    // $ANTLR start "PRIVATE"
    public final void mPRIVATE() throws RecognitionException {
        try {
            int _type = PRIVATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:139:9: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:139:11: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRIVATE"

    // $ANTLR start "PROGRAM"
    public final void mPROGRAM() throws RecognitionException {
        try {
            int _type = PROGRAM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:141:9: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' ) )
            // InternalGlobalConstantsLexer.g:141:11: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROGRAM"

    // $ANTLR start "WSTRING"
    public final void mWSTRING() throws RecognitionException {
        try {
            int _type = WSTRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:143:9: ( ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalGlobalConstantsLexer.g:143:11: ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WSTRING"

    // $ANTLR start "ACTION"
    public final void mACTION() throws RecognitionException {
        try {
            int _type = ACTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:145:8: ( ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:145:10: ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ACTION"

    // $ANTLR start "END_IF"
    public final void mEND_IF() throws RecognitionException {
        try {
            int _type = END_IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:147:8: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalGlobalConstantsLexer.g:147:10: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_IF"

    // $ANTLR start "IMPORT"
    public final void mIMPORT() throws RecognitionException {
        try {
            int _type = IMPORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:149:8: ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:149:10: ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IMPORT"

    // $ANTLR start "METHOD"
    public final void mMETHOD() throws RecognitionException {
        try {
            int _type = METHOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:151:8: ( ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:151:10: ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "METHOD"

    // $ANTLR start "PUBLIC"
    public final void mPUBLIC() throws RecognitionException {
        try {
            int _type = PUBLIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:153:8: ( ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'C' | 'c' ) )
            // InternalGlobalConstantsLexer.g:153:10: ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'C' | 'c' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PUBLIC"

    // $ANTLR start "REF_TO"
    public final void mREF_TO() throws RecognitionException {
        try {
            int _type = REF_TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:155:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) '_' ( 'T' | 't' ) ( 'O' | 'o' ) )
            // InternalGlobalConstantsLexer.g:155:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) '_' ( 'T' | 't' ) ( 'O' | 'o' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('_'); 
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REF_TO"

    // $ANTLR start "REPEAT"
    public final void mREPEAT() throws RecognitionException {
        try {
            int _type = REPEAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:157:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:157:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REPEAT"

    // $ANTLR start "RETAIN"
    public final void mRETAIN() throws RecognitionException {
        try {
            int _type = RETAIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:159:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:159:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RETAIN"

    // $ANTLR start "RETURN"
    public final void mRETURN() throws RecognitionException {
        try {
            int _type = RETURN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:161:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:161:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RETURN"

    // $ANTLR start "SINGLE"
    public final void mSINGLE() throws RecognitionException {
        try {
            int _type = SINGLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:163:8: ( ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:163:10: ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SINGLE"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:165:8: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalGlobalConstantsLexer.g:165:10: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "STRUCT"
    public final void mSTRUCT() throws RecognitionException {
        try {
            int _type = STRUCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:167:8: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:167:10: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRUCT"

    // $ANTLR start "ARRAY"
    public final void mARRAY() throws RecognitionException {
        try {
            int _type = ARRAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:169:7: ( ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalGlobalConstantsLexer.g:169:9: ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ARRAY"

    // $ANTLR start "CLASS"
    public final void mCLASS() throws RecognitionException {
        try {
            int _type = CLASS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:171:7: ( ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:171:9: ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLASS"

    // $ANTLR start "DWORD"
    public final void mDWORD() throws RecognitionException {
        try {
            int _type = DWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:173:7: ( ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:173:9: ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DWORD"

    // $ANTLR start "ELSIF"
    public final void mELSIF() throws RecognitionException {
        try {
            int _type = ELSIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:175:7: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalGlobalConstantsLexer.g:175:9: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSIF"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:177:7: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:177:9: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "FINAL"
    public final void mFINAL() throws RecognitionException {
        try {
            int _type = FINAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:179:7: ( ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:179:9: ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FINAL"

    // $ANTLR start "LDATE"
    public final void mLDATE() throws RecognitionException {
        try {
            int _type = LDATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:181:7: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:181:9: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LDATE"

    // $ANTLR start "LREAL"
    public final void mLREAL() throws RecognitionException {
        try {
            int _type = LREAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:183:7: ( ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:183:9: ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LREAL"

    // $ANTLR start "LTIME"
    public final void mLTIME() throws RecognitionException {
        try {
            int _type = LTIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:185:7: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:185:9: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LTIME"

    // $ANTLR start "LWORD"
    public final void mLWORD() throws RecognitionException {
        try {
            int _type = LWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:187:7: ( ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:187:9: ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LWORD"

    // $ANTLR start "SUPER"
    public final void mSUPER() throws RecognitionException {
        try {
            int _type = SUPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:189:7: ( ( 'S' | 's' ) ( 'U' | 'u' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:189:9: ( 'S' | 's' ) ( 'U' | 'u' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SUPER"

    // $ANTLR start "UDINT"
    public final void mUDINT() throws RecognitionException {
        try {
            int _type = UDINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:191:7: ( ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:191:9: ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UDINT"

    // $ANTLR start "ULINT"
    public final void mULINT() throws RecognitionException {
        try {
            int _type = ULINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:193:7: ( ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:193:9: ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ULINT"

    // $ANTLR start "UNTIL"
    public final void mUNTIL() throws RecognitionException {
        try {
            int _type = UNTIL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:195:7: ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:195:9: ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNTIL"

    // $ANTLR start "USING"
    public final void mUSING() throws RecognitionException {
        try {
            int _type = USING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:197:7: ( ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalGlobalConstantsLexer.g:197:9: ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "USING"

    // $ANTLR start "USINT"
    public final void mUSINT() throws RecognitionException {
        try {
            int _type = USINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:199:7: ( ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:199:9: ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "USINT"

    // $ANTLR start "WCHAR"
    public final void mWCHAR() throws RecognitionException {
        try {
            int _type = WCHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:201:7: ( ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:201:9: ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WCHAR"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:203:7: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:203:9: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "BOOL"
    public final void mBOOL() throws RecognitionException {
        try {
            int _type = BOOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:205:6: ( ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:205:8: ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOL"

    // $ANTLR start "BYTE"
    public final void mBYTE() throws RecognitionException {
        try {
            int _type = BYTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:207:6: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:207:8: ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BYTE"

    // $ANTLR start "CASE"
    public final void mCASE() throws RecognitionException {
        try {
            int _type = CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:209:6: ( ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:209:8: ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CASE"

    // $ANTLR start "CHAR"
    public final void mCHAR() throws RecognitionException {
        try {
            int _type = CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:211:6: ( ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:211:8: ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CHAR"

    // $ANTLR start "DATE"
    public final void mDATE() throws RecognitionException {
        try {
            int _type = DATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:213:6: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:213:8: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATE"

    // $ANTLR start "DINT"
    public final void mDINT() throws RecognitionException {
        try {
            int _type = DINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:215:6: ( ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:215:8: ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DINT"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:217:6: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:217:8: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "EXIT"
    public final void mEXIT() throws RecognitionException {
        try {
            int _type = EXIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:219:6: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:219:8: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXIT"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:221:6: ( ( 'F' | 'f' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'M' | 'm' ) )
            // InternalGlobalConstantsLexer.g:221:8: ( 'F' | 'f' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'M' | 'm' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "LINT"
    public final void mLINT() throws RecognitionException {
        try {
            int _type = LINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:223:6: ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:223:8: ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINT"

    // $ANTLR start "LTOD"
    public final void mLTOD() throws RecognitionException {
        try {
            int _type = LTOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:225:6: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:225:8: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LTOD"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:227:6: ( ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:227:8: ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "REAL"
    public final void mREAL() throws RecognitionException {
        try {
            int _type = REAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:229:6: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:229:8: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REAL"

    // $ANTLR start "SINT"
    public final void mSINT() throws RecognitionException {
        try {
            int _type = SINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:231:6: ( ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:231:8: ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SINT"

    // $ANTLR start "STEP"
    public final void mSTEP() throws RecognitionException {
        try {
            int _type = STEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:233:6: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalGlobalConstantsLexer.g:233:8: ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STEP"

    // $ANTLR start "TASK"
    public final void mTASK() throws RecognitionException {
        try {
            int _type = TASK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:235:6: ( ( 'T' | 't' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'K' | 'k' ) )
            // InternalGlobalConstantsLexer.g:235:8: ( 'T' | 't' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'K' | 'k' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TASK"

    // $ANTLR start "THEN"
    public final void mTHEN() throws RecognitionException {
        try {
            int _type = THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:237:6: ( ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:237:8: ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "THEN"

    // $ANTLR start "THIS"
    public final void mTHIS() throws RecognitionException {
        try {
            int _type = THIS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:239:6: ( ( 'T' | 't' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:239:8: ( 'T' | 't' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "THIS"

    // $ANTLR start "TIME"
    public final void mTIME() throws RecognitionException {
        try {
            int _type = TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:241:6: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:241:8: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIME"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:243:6: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:243:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "TYPE"
    public final void mTYPE() throws RecognitionException {
        try {
            int _type = TYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:245:6: ( ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' ) )
            // InternalGlobalConstantsLexer.g:245:8: ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TYPE"

    // $ANTLR start "UINT"
    public final void mUINT() throws RecognitionException {
        try {
            int _type = UINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:247:6: ( ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:247:8: ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UINT"

    // $ANTLR start "WITH"
    public final void mWITH() throws RecognitionException {
        try {
            int _type = WITH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:249:6: ( ( 'W' | 'w' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) )
            // InternalGlobalConstantsLexer.g:249:8: ( 'W' | 'w' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WITH"

    // $ANTLR start "WORD"
    public final void mWORD() throws RecognitionException {
        try {
            int _type = WORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:251:6: ( ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:251:8: ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WORD"

    // $ANTLR start "ColonColonAsterisk"
    public final void mColonColonAsterisk() throws RecognitionException {
        try {
            int _type = ColonColonAsterisk;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:253:20: ( ':' ':' '*' )
            // InternalGlobalConstantsLexer.g:253:22: ':' ':' '*'
            {
            match(':'); 
            match(':'); 
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ColonColonAsterisk"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:255:5: ( ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:255:7: ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:257:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:257:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:259:5: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:259:7: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "LDT"
    public final void mLDT() throws RecognitionException {
        try {
            int _type = LDT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:261:5: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:261:7: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LDT"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:263:5: ( ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:263:7: ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MOD"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:265:5: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:265:7: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "REF"
    public final void mREF() throws RecognitionException {
        try {
            int _type = REF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:267:5: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) )
            // InternalGlobalConstantsLexer.g:267:7: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REF"

    // $ANTLR start "TOD"
    public final void mTOD() throws RecognitionException {
        try {
            int _type = TOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:269:5: ( ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:269:7: ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOD"

    // $ANTLR start "VAR"
    public final void mVAR() throws RecognitionException {
        try {
            int _type = VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:271:5: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:271:7: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR"

    // $ANTLR start "XOR"
    public final void mXOR() throws RecognitionException {
        try {
            int _type = XOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:273:5: ( ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:273:7: ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "XOR"

    // $ANTLR start "B"
    public final void mB() throws RecognitionException {
        try {
            int _type = B;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:275:3: ( '%' ( 'B' | 'b' ) )
            // InternalGlobalConstantsLexer.g:275:5: '%' ( 'B' | 'b' )
            {
            match('%'); 
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "B"

    // $ANTLR start "D_1"
    public final void mD_1() throws RecognitionException {
        try {
            int _type = D_1;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:277:5: ( '%' ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:277:7: '%' ( 'D' | 'd' )
            {
            match('%'); 
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "D_1"

    // $ANTLR start "L"
    public final void mL() throws RecognitionException {
        try {
            int _type = L;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:279:3: ( '%' ( 'L' | 'l' ) )
            // InternalGlobalConstantsLexer.g:279:5: '%' ( 'L' | 'l' )
            {
            match('%'); 
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "L"

    // $ANTLR start "W"
    public final void mW() throws RecognitionException {
        try {
            int _type = W;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:281:3: ( '%' ( 'W' | 'w' ) )
            // InternalGlobalConstantsLexer.g:281:5: '%' ( 'W' | 'w' )
            {
            match('%'); 
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "W"

    // $ANTLR start "X"
    public final void mX() throws RecognitionException {
        try {
            int _type = X;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:283:3: ( '%' ( 'X' | 'x' ) )
            // InternalGlobalConstantsLexer.g:283:5: '%' ( 'X' | 'x' )
            {
            match('%'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "X"

    // $ANTLR start "AsteriskAsterisk"
    public final void mAsteriskAsterisk() throws RecognitionException {
        try {
            int _type = AsteriskAsterisk;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:285:18: ( '*' '*' )
            // InternalGlobalConstantsLexer.g:285:20: '*' '*'
            {
            match('*'); 
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AsteriskAsterisk"

    // $ANTLR start "FullStopFullStop"
    public final void mFullStopFullStop() throws RecognitionException {
        try {
            int _type = FullStopFullStop;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:287:18: ( '.' '.' )
            // InternalGlobalConstantsLexer.g:287:20: '.' '.'
            {
            match('.'); 
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FullStopFullStop"

    // $ANTLR start "ColonColon"
    public final void mColonColon() throws RecognitionException {
        try {
            int _type = ColonColon;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:289:12: ( ':' ':' )
            // InternalGlobalConstantsLexer.g:289:14: ':' ':'
            {
            match(':'); 
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ColonColon"

    // $ANTLR start "ColonEqualsSign"
    public final void mColonEqualsSign() throws RecognitionException {
        try {
            int _type = ColonEqualsSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:291:17: ( ':' '=' )
            // InternalGlobalConstantsLexer.g:291:19: ':' '='
            {
            match(':'); 
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ColonEqualsSign"

    // $ANTLR start "LessThanSignEqualsSign"
    public final void mLessThanSignEqualsSign() throws RecognitionException {
        try {
            int _type = LessThanSignEqualsSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:293:24: ( '<' '=' )
            // InternalGlobalConstantsLexer.g:293:26: '<' '='
            {
            match('<'); 
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LessThanSignEqualsSign"

    // $ANTLR start "LessThanSignGreaterThanSign"
    public final void mLessThanSignGreaterThanSign() throws RecognitionException {
        try {
            int _type = LessThanSignGreaterThanSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:295:29: ( '<' '>' )
            // InternalGlobalConstantsLexer.g:295:31: '<' '>'
            {
            match('<'); 
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LessThanSignGreaterThanSign"

    // $ANTLR start "EqualsSignGreaterThanSign"
    public final void mEqualsSignGreaterThanSign() throws RecognitionException {
        try {
            int _type = EqualsSignGreaterThanSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:297:27: ( '=' '>' )
            // InternalGlobalConstantsLexer.g:297:29: '=' '>'
            {
            match('='); 
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EqualsSignGreaterThanSign"

    // $ANTLR start "GreaterThanSignEqualsSign"
    public final void mGreaterThanSignEqualsSign() throws RecognitionException {
        try {
            int _type = GreaterThanSignEqualsSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:299:27: ( '>' '=' )
            // InternalGlobalConstantsLexer.g:299:29: '>' '='
            {
            match('>'); 
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GreaterThanSignEqualsSign"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:301:4: ( ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:301:6: ( 'A' | 'a' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:303:4: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) )
            // InternalGlobalConstantsLexer.g:303:6: ( 'B' | 'b' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BY"

    // $ANTLR start "DO"
    public final void mDO() throws RecognitionException {
        try {
            int _type = DO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:305:4: ( ( 'D' | 'd' ) ( 'O' | 'o' ) )
            // InternalGlobalConstantsLexer.g:305:6: ( 'D' | 'd' ) ( 'O' | 'o' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DO"

    // $ANTLR start "DT"
    public final void mDT() throws RecognitionException {
        try {
            int _type = DT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:307:4: ( ( 'D' | 'd' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:307:6: ( 'D' | 'd' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DT"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:309:4: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalGlobalConstantsLexer.g:309:6: ( 'I' | 'i' ) ( 'F' | 'f' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "LD"
    public final void mLD() throws RecognitionException {
        try {
            int _type = LD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:311:4: ( ( 'L' | 'l' ) ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:311:6: ( 'L' | 'l' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LD"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:313:4: ( ( 'L' | 'l' ) ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:313:6: ( 'L' | 'l' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "OF"
    public final void mOF() throws RecognitionException {
        try {
            int _type = OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:315:4: ( ( 'O' | 'o' ) ( 'F' | 'f' ) )
            // InternalGlobalConstantsLexer.g:315:6: ( 'O' | 'o' ) ( 'F' | 'f' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OF"

    // $ANTLR start "ON"
    public final void mON() throws RecognitionException {
        try {
            int _type = ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:317:4: ( ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalGlobalConstantsLexer.g:317:6: ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ON"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:319:4: ( ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalGlobalConstantsLexer.g:319:6: ( 'O' | 'o' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "TO"
    public final void mTO() throws RecognitionException {
        try {
            int _type = TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:321:4: ( ( 'T' | 't' ) ( 'O' | 'o' ) )
            // InternalGlobalConstantsLexer.g:321:6: ( 'T' | 't' ) ( 'O' | 'o' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TO"

    // $ANTLR start "NumberSign"
    public final void mNumberSign() throws RecognitionException {
        try {
            int _type = NumberSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:323:12: ( '#' )
            // InternalGlobalConstantsLexer.g:323:14: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NumberSign"

    // $ANTLR start "Ampersand"
    public final void mAmpersand() throws RecognitionException {
        try {
            int _type = Ampersand;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:325:11: ( '&' )
            // InternalGlobalConstantsLexer.g:325:13: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Ampersand"

    // $ANTLR start "LeftParenthesis"
    public final void mLeftParenthesis() throws RecognitionException {
        try {
            int _type = LeftParenthesis;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:327:17: ( '(' )
            // InternalGlobalConstantsLexer.g:327:19: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LeftParenthesis"

    // $ANTLR start "RightParenthesis"
    public final void mRightParenthesis() throws RecognitionException {
        try {
            int _type = RightParenthesis;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:329:18: ( ')' )
            // InternalGlobalConstantsLexer.g:329:20: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RightParenthesis"

    // $ANTLR start "Asterisk"
    public final void mAsterisk() throws RecognitionException {
        try {
            int _type = Asterisk;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:331:10: ( '*' )
            // InternalGlobalConstantsLexer.g:331:12: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Asterisk"

    // $ANTLR start "PlusSign"
    public final void mPlusSign() throws RecognitionException {
        try {
            int _type = PlusSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:333:10: ( '+' )
            // InternalGlobalConstantsLexer.g:333:12: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PlusSign"

    // $ANTLR start "Comma"
    public final void mComma() throws RecognitionException {
        try {
            int _type = Comma;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:335:7: ( ',' )
            // InternalGlobalConstantsLexer.g:335:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Comma"

    // $ANTLR start "HyphenMinus"
    public final void mHyphenMinus() throws RecognitionException {
        try {
            int _type = HyphenMinus;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:337:13: ( '-' )
            // InternalGlobalConstantsLexer.g:337:15: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HyphenMinus"

    // $ANTLR start "FullStop"
    public final void mFullStop() throws RecognitionException {
        try {
            int _type = FullStop;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:339:10: ( '.' )
            // InternalGlobalConstantsLexer.g:339:12: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FullStop"

    // $ANTLR start "Solidus"
    public final void mSolidus() throws RecognitionException {
        try {
            int _type = Solidus;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:341:9: ( '/' )
            // InternalGlobalConstantsLexer.g:341:11: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Solidus"

    // $ANTLR start "Colon"
    public final void mColon() throws RecognitionException {
        try {
            int _type = Colon;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:343:7: ( ':' )
            // InternalGlobalConstantsLexer.g:343:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Colon"

    // $ANTLR start "Semicolon"
    public final void mSemicolon() throws RecognitionException {
        try {
            int _type = Semicolon;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:345:11: ( ';' )
            // InternalGlobalConstantsLexer.g:345:13: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Semicolon"

    // $ANTLR start "LessThanSign"
    public final void mLessThanSign() throws RecognitionException {
        try {
            int _type = LessThanSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:347:14: ( '<' )
            // InternalGlobalConstantsLexer.g:347:16: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LessThanSign"

    // $ANTLR start "EqualsSign"
    public final void mEqualsSign() throws RecognitionException {
        try {
            int _type = EqualsSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:349:12: ( '=' )
            // InternalGlobalConstantsLexer.g:349:14: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EqualsSign"

    // $ANTLR start "GreaterThanSign"
    public final void mGreaterThanSign() throws RecognitionException {
        try {
            int _type = GreaterThanSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:351:17: ( '>' )
            // InternalGlobalConstantsLexer.g:351:19: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GreaterThanSign"

    // $ANTLR start "D"
    public final void mD() throws RecognitionException {
        try {
            int _type = D;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:353:3: ( ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:353:5: ( 'D' | 'd' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "D"

    // $ANTLR start "T"
    public final void mT() throws RecognitionException {
        try {
            int _type = T;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:355:3: ( ( 'T' | 't' ) )
            // InternalGlobalConstantsLexer.g:355:5: ( 'T' | 't' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T"

    // $ANTLR start "LeftSquareBracket"
    public final void mLeftSquareBracket() throws RecognitionException {
        try {
            int _type = LeftSquareBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:357:19: ( '[' )
            // InternalGlobalConstantsLexer.g:357:21: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LeftSquareBracket"

    // $ANTLR start "RightSquareBracket"
    public final void mRightSquareBracket() throws RecognitionException {
        try {
            int _type = RightSquareBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:359:20: ( ']' )
            // InternalGlobalConstantsLexer.g:359:22: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RightSquareBracket"

    // $ANTLR start "LeftCurlyBracket"
    public final void mLeftCurlyBracket() throws RecognitionException {
        try {
            int _type = LeftCurlyBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:361:18: ( '{' )
            // InternalGlobalConstantsLexer.g:361:20: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LeftCurlyBracket"

    // $ANTLR start "RightCurlyBracket"
    public final void mRightCurlyBracket() throws RecognitionException {
        try {
            int _type = RightCurlyBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:363:19: ( '}' )
            // InternalGlobalConstantsLexer.g:363:21: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RightCurlyBracket"

    // $ANTLR start "RULE_HEX_DIGIT"
    public final void mRULE_HEX_DIGIT() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:365:25: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' ) )
            // InternalGlobalConstantsLexer.g:365:27: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_HEX_DIGIT"

    // $ANTLR start "RULE_NON_DECIMAL"
    public final void mRULE_NON_DECIMAL() throws RecognitionException {
        try {
            int _type = RULE_NON_DECIMAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:367:18: ( ( '2#' | '8#' | '16#' ) ( RULE_HEX_DIGIT )+ )
            // InternalGlobalConstantsLexer.g:367:20: ( '2#' | '8#' | '16#' ) ( RULE_HEX_DIGIT )+
            {
            // InternalGlobalConstantsLexer.g:367:20: ( '2#' | '8#' | '16#' )
            int alt1=3;
            switch ( input.LA(1) ) {
            case '2':
                {
                alt1=1;
                }
                break;
            case '8':
                {
                alt1=2;
                }
                break;
            case '1':
                {
                alt1=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // InternalGlobalConstantsLexer.g:367:21: '2#'
                    {
                    match("2#"); 


                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsLexer.g:367:26: '8#'
                    {
                    match("8#"); 


                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsLexer.g:367:31: '16#'
                    {
                    match("16#"); 


                    }
                    break;

            }

            // InternalGlobalConstantsLexer.g:367:38: ( RULE_HEX_DIGIT )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='F')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='f')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalGlobalConstantsLexer.g:367:38: RULE_HEX_DIGIT
            	    {
            	    mRULE_HEX_DIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NON_DECIMAL"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:369:10: ( '0' .. '9' ( '0' .. '9' | '_' )* )
            // InternalGlobalConstantsLexer.g:369:12: '0' .. '9' ( '0' .. '9' | '_' )*
            {
            matchRange('0','9'); 
            // InternalGlobalConstantsLexer.g:369:21: ( '0' .. '9' | '_' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||LA3_0=='_') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalGlobalConstantsLexer.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||input.LA(1)=='_' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_DECIMAL"
    public final void mRULE_DECIMAL() throws RecognitionException {
        try {
            int _type = RULE_DECIMAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:371:14: ( RULE_INT ( ( 'e' | 'E' ) ( '+' | '-' )? RULE_INT )? )
            // InternalGlobalConstantsLexer.g:371:16: RULE_INT ( ( 'e' | 'E' ) ( '+' | '-' )? RULE_INT )?
            {
            mRULE_INT(); 
            // InternalGlobalConstantsLexer.g:371:25: ( ( 'e' | 'E' ) ( '+' | '-' )? RULE_INT )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='E'||LA5_0=='e') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalGlobalConstantsLexer.g:371:26: ( 'e' | 'E' ) ( '+' | '-' )? RULE_INT
                    {
                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // InternalGlobalConstantsLexer.g:371:36: ( '+' | '-' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='+'||LA4_0=='-') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalGlobalConstantsLexer.g:
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}


                            }
                            break;

                    }

                    mRULE_INT(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_DECIMAL"

    // $ANTLR start "RULE_TIME_VALUE"
    public final void mRULE_TIME_VALUE() throws RecognitionException {
        try {
            int _type = RULE_TIME_VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:373:17: ( ( RULE_TIME_PART ( '_' )? )+ )
            // InternalGlobalConstantsLexer.g:373:19: ( RULE_TIME_PART ( '_' )? )+
            {
            // InternalGlobalConstantsLexer.g:373:19: ( RULE_TIME_PART ( '_' )? )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalGlobalConstantsLexer.g:373:20: RULE_TIME_PART ( '_' )?
            	    {
            	    mRULE_TIME_PART(); 
            	    // InternalGlobalConstantsLexer.g:373:35: ( '_' )?
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0=='_') ) {
            	        alt6=1;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // InternalGlobalConstantsLexer.g:373:35: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_VALUE"

    // $ANTLR start "RULE_TIME_PART"
    public final void mRULE_TIME_PART() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:375:25: ( RULE_INT ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS ) )
            // InternalGlobalConstantsLexer.g:375:27: RULE_INT ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS )
            {
            mRULE_INT(); 
            // InternalGlobalConstantsLexer.g:375:36: ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS )
            int alt8=7;
            switch ( input.LA(1) ) {
            case 'D':
            case 'd':
                {
                alt8=1;
                }
                break;
            case 'H':
            case 'h':
                {
                alt8=2;
                }
                break;
            case 'M':
            case 'm':
                {
                int LA8_3 = input.LA(2);

                if ( (LA8_3=='S'||LA8_3=='s') ) {
                    alt8=5;
                }
                else {
                    alt8=3;}
                }
                break;
            case 'S':
            case 's':
                {
                alt8=4;
                }
                break;
            case 'U':
            case 'u':
                {
                alt8=6;
                }
                break;
            case 'N':
            case 'n':
                {
                alt8=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalGlobalConstantsLexer.g:375:37: RULE_TIME_DAYS
                    {
                    mRULE_TIME_DAYS(); 

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsLexer.g:375:52: RULE_TIME_HOURS
                    {
                    mRULE_TIME_HOURS(); 

                    }
                    break;
                case 3 :
                    // InternalGlobalConstantsLexer.g:375:68: RULE_TIME_MINUTES
                    {
                    mRULE_TIME_MINUTES(); 

                    }
                    break;
                case 4 :
                    // InternalGlobalConstantsLexer.g:375:86: RULE_TIME_SECONDS
                    {
                    mRULE_TIME_SECONDS(); 

                    }
                    break;
                case 5 :
                    // InternalGlobalConstantsLexer.g:375:104: RULE_TIME_MILLIS
                    {
                    mRULE_TIME_MILLIS(); 

                    }
                    break;
                case 6 :
                    // InternalGlobalConstantsLexer.g:375:121: RULE_TIME_MICROS
                    {
                    mRULE_TIME_MICROS(); 

                    }
                    break;
                case 7 :
                    // InternalGlobalConstantsLexer.g:375:138: RULE_TIME_NANOS
                    {
                    mRULE_TIME_NANOS(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_PART"

    // $ANTLR start "RULE_TIME_DAYS"
    public final void mRULE_TIME_DAYS() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:377:25: ( ( 'D' | 'd' ) )
            // InternalGlobalConstantsLexer.g:377:27: ( 'D' | 'd' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_DAYS"

    // $ANTLR start "RULE_TIME_HOURS"
    public final void mRULE_TIME_HOURS() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:379:26: ( ( 'H' | 'h' ) )
            // InternalGlobalConstantsLexer.g:379:28: ( 'H' | 'h' )
            {
            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_HOURS"

    // $ANTLR start "RULE_TIME_MINUTES"
    public final void mRULE_TIME_MINUTES() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:381:28: ( ( 'M' | 'm' ) )
            // InternalGlobalConstantsLexer.g:381:30: ( 'M' | 'm' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_MINUTES"

    // $ANTLR start "RULE_TIME_SECONDS"
    public final void mRULE_TIME_SECONDS() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:383:28: ( ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:383:30: ( 'S' | 's' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_SECONDS"

    // $ANTLR start "RULE_TIME_MILLIS"
    public final void mRULE_TIME_MILLIS() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:385:27: ( ( 'M' | 'm' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:385:29: ( 'M' | 'm' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_MILLIS"

    // $ANTLR start "RULE_TIME_MICROS"
    public final void mRULE_TIME_MICROS() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:387:27: ( ( 'U' | 'u' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:387:29: ( 'U' | 'u' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_MICROS"

    // $ANTLR start "RULE_TIME_NANOS"
    public final void mRULE_TIME_NANOS() throws RecognitionException {
        try {
            // InternalGlobalConstantsLexer.g:389:26: ( ( 'N' | 'n' ) ( 'S' | 's' ) )
            // InternalGlobalConstantsLexer.g:389:28: ( 'N' | 'n' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME_NANOS"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:391:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalGlobalConstantsLexer.g:391:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalGlobalConstantsLexer.g:391:11: ( '^' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='^') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalGlobalConstantsLexer.g:391:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalGlobalConstantsLexer.g:391:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalGlobalConstantsLexer.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:393:13: ( ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' ) )
            // InternalGlobalConstantsLexer.g:393:15: ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' )
            {
            // InternalGlobalConstantsLexer.g:393:15: ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='\"') ) {
                alt13=1;
            }
            else if ( (LA13_0=='\'') ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalGlobalConstantsLexer.g:393:16: '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalGlobalConstantsLexer.g:393:20: ( '$' . | ~ ( ( '$' | '\"' ) ) )*
                    loop11:
                    do {
                        int alt11=3;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0=='$') ) {
                            alt11=1;
                        }
                        else if ( ((LA11_0>='\u0000' && LA11_0<='!')||LA11_0=='#'||(LA11_0>='%' && LA11_0<='\uFFFF')) ) {
                            alt11=2;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // InternalGlobalConstantsLexer.g:393:21: '$' .
                    	    {
                    	    match('$'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalGlobalConstantsLexer.g:393:27: ~ ( ( '$' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||input.LA(1)=='#'||(input.LA(1)>='%' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsLexer.g:393:46: '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalGlobalConstantsLexer.g:393:51: ( '$' . | ~ ( ( '$' | '\\'' ) ) )*
                    loop12:
                    do {
                        int alt12=3;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0=='$') ) {
                            alt12=1;
                        }
                        else if ( ((LA12_0>='\u0000' && LA12_0<='#')||(LA12_0>='%' && LA12_0<='&')||(LA12_0>='(' && LA12_0<='\uFFFF')) ) {
                            alt12=2;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // InternalGlobalConstantsLexer.g:393:52: '$' .
                    	    {
                    	    match('$'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalGlobalConstantsLexer.g:393:58: ~ ( ( '$' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='#')||(input.LA(1)>='%' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:395:17: ( ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' ) )
            // InternalGlobalConstantsLexer.g:395:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
            {
            // InternalGlobalConstantsLexer.g:395:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='/') ) {
                alt16=1;
            }
            else if ( (LA16_0=='(') ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalGlobalConstantsLexer.g:395:20: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 

                    // InternalGlobalConstantsLexer.g:395:25: ( options {greedy=false; } : . )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0=='*') ) {
                            int LA14_1 = input.LA(2);

                            if ( (LA14_1=='/') ) {
                                alt14=2;
                            }
                            else if ( ((LA14_1>='\u0000' && LA14_1<='.')||(LA14_1>='0' && LA14_1<='\uFFFF')) ) {
                                alt14=1;
                            }


                        }
                        else if ( ((LA14_0>='\u0000' && LA14_0<=')')||(LA14_0>='+' && LA14_0<='\uFFFF')) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalGlobalConstantsLexer.g:395:53: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);

                    match("*/"); 


                    }
                    break;
                case 2 :
                    // InternalGlobalConstantsLexer.g:395:62: '(*' ( options {greedy=false; } : . )* '*)'
                    {
                    match("(*"); 

                    // InternalGlobalConstantsLexer.g:395:67: ( options {greedy=false; } : . )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0=='*') ) {
                            int LA15_1 = input.LA(2);

                            if ( (LA15_1==')') ) {
                                alt15=2;
                            }
                            else if ( ((LA15_1>='\u0000' && LA15_1<='(')||(LA15_1>='*' && LA15_1<='\uFFFF')) ) {
                                alt15=1;
                            }


                        }
                        else if ( ((LA15_0>='\u0000' && LA15_0<=')')||(LA15_0>='+' && LA15_0<='\uFFFF')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // InternalGlobalConstantsLexer.g:395:95: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    match("*)"); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:397:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalGlobalConstantsLexer.g:397:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalGlobalConstantsLexer.g:397:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='\u0000' && LA17_0<='\t')||(LA17_0>='\u000B' && LA17_0<='\f')||(LA17_0>='\u000E' && LA17_0<='\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalGlobalConstantsLexer.g:397:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            // InternalGlobalConstantsLexer.g:397:40: ( ( '\\r' )? '\\n' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='\n'||LA19_0=='\r') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalGlobalConstantsLexer.g:397:41: ( '\\r' )? '\\n'
                    {
                    // InternalGlobalConstantsLexer.g:397:41: ( '\\r' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='\r') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalGlobalConstantsLexer.g:397:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:399:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalGlobalConstantsLexer.g:399:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalGlobalConstantsLexer.g:399:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='\t' && LA20_0<='\n')||LA20_0=='\r'||LA20_0==' ') ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalGlobalConstantsLexer.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt20 >= 1 ) break loop20;
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalGlobalConstantsLexer.g:401:16: ( . )
            // InternalGlobalConstantsLexer.g:401:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalGlobalConstantsLexer.g:1:8: ( END_GLOBALCONSTANTS | END_FUNCTION_BLOCK | END_CONFIGURATION | GLOBALCONSTANTS | END_TRANSITION | FUNCTION_BLOCK | LDATE_AND_TIME | CONFIGURATION | DATE_AND_TIME | END_INTERFACE | END_NAMESPACE | END_FUNCTION | END_RESOURCE | INITIAL_STEP | LTIME_OF_DAY | VAR_EXTERNAL | END_PROGRAM | TIME_OF_DAY | END_ACTION | END_METHOD | END_REPEAT | END_STRUCT | IMPLEMENTS | NON_RETAIN | READ_WRITE | TRANSITION | VAR_ACCESS | VAR_CONFIG | VAR_GLOBAL | VAR_IN_OUT | VAR_OUTPUT | END_CLASS | END_WHILE | INTERFACE | NAMESPACE | PROTECTED | READ_ONLY | VAR_INPUT | ABSTRACT | CONSTANT | CONTINUE | END_CASE | END_STEP | END_TYPE | FUNCTION | INTERNAL | INTERVAL | OVERRIDE | PRIORITY | RESOURCE | VAR_TEMP | END_FOR | END_VAR | EXTENDS | INTERAL | OVERLAP | PACKAGE | PRIVATE | PROGRAM | WSTRING | ACTION | END_IF | IMPORT | METHOD | PUBLIC | REF_TO | REPEAT | RETAIN | RETURN | SINGLE | STRING | STRUCT | ARRAY | CLASS | DWORD | ELSIF | FALSE | FINAL | LDATE | LREAL | LTIME | LWORD | SUPER | UDINT | ULINT | UNTIL | USING | USINT | WCHAR | WHILE | BOOL | BYTE | CASE | CHAR | DATE | DINT | ELSE | EXIT | FROM | LINT | LTOD | NULL | REAL | SINT | STEP | TASK | THEN | THIS | TIME | TRUE | TYPE | UINT | WITH | WORD | ColonColonAsterisk | AND | FOR | INT | LDT | MOD | NOT | REF | TOD | VAR | XOR | B | D_1 | L | W | X | AsteriskAsterisk | FullStopFullStop | ColonColon | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | DO | DT | IF | LD | LT | OF | ON | OR | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | D | T | LeftSquareBracket | RightSquareBracket | LeftCurlyBracket | RightCurlyBracket | RULE_NON_DECIMAL | RULE_INT | RULE_DECIMAL | RULE_TIME_VALUE | RULE_ID | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt21=180;
        alt21 = dfa21.predict(input);
        switch (alt21) {
            case 1 :
                // InternalGlobalConstantsLexer.g:1:10: END_GLOBALCONSTANTS
                {
                mEND_GLOBALCONSTANTS(); 

                }
                break;
            case 2 :
                // InternalGlobalConstantsLexer.g:1:30: END_FUNCTION_BLOCK
                {
                mEND_FUNCTION_BLOCK(); 

                }
                break;
            case 3 :
                // InternalGlobalConstantsLexer.g:1:49: END_CONFIGURATION
                {
                mEND_CONFIGURATION(); 

                }
                break;
            case 4 :
                // InternalGlobalConstantsLexer.g:1:67: GLOBALCONSTANTS
                {
                mGLOBALCONSTANTS(); 

                }
                break;
            case 5 :
                // InternalGlobalConstantsLexer.g:1:83: END_TRANSITION
                {
                mEND_TRANSITION(); 

                }
                break;
            case 6 :
                // InternalGlobalConstantsLexer.g:1:98: FUNCTION_BLOCK
                {
                mFUNCTION_BLOCK(); 

                }
                break;
            case 7 :
                // InternalGlobalConstantsLexer.g:1:113: LDATE_AND_TIME
                {
                mLDATE_AND_TIME(); 

                }
                break;
            case 8 :
                // InternalGlobalConstantsLexer.g:1:128: CONFIGURATION
                {
                mCONFIGURATION(); 

                }
                break;
            case 9 :
                // InternalGlobalConstantsLexer.g:1:142: DATE_AND_TIME
                {
                mDATE_AND_TIME(); 

                }
                break;
            case 10 :
                // InternalGlobalConstantsLexer.g:1:156: END_INTERFACE
                {
                mEND_INTERFACE(); 

                }
                break;
            case 11 :
                // InternalGlobalConstantsLexer.g:1:170: END_NAMESPACE
                {
                mEND_NAMESPACE(); 

                }
                break;
            case 12 :
                // InternalGlobalConstantsLexer.g:1:184: END_FUNCTION
                {
                mEND_FUNCTION(); 

                }
                break;
            case 13 :
                // InternalGlobalConstantsLexer.g:1:197: END_RESOURCE
                {
                mEND_RESOURCE(); 

                }
                break;
            case 14 :
                // InternalGlobalConstantsLexer.g:1:210: INITIAL_STEP
                {
                mINITIAL_STEP(); 

                }
                break;
            case 15 :
                // InternalGlobalConstantsLexer.g:1:223: LTIME_OF_DAY
                {
                mLTIME_OF_DAY(); 

                }
                break;
            case 16 :
                // InternalGlobalConstantsLexer.g:1:236: VAR_EXTERNAL
                {
                mVAR_EXTERNAL(); 

                }
                break;
            case 17 :
                // InternalGlobalConstantsLexer.g:1:249: END_PROGRAM
                {
                mEND_PROGRAM(); 

                }
                break;
            case 18 :
                // InternalGlobalConstantsLexer.g:1:261: TIME_OF_DAY
                {
                mTIME_OF_DAY(); 

                }
                break;
            case 19 :
                // InternalGlobalConstantsLexer.g:1:273: END_ACTION
                {
                mEND_ACTION(); 

                }
                break;
            case 20 :
                // InternalGlobalConstantsLexer.g:1:284: END_METHOD
                {
                mEND_METHOD(); 

                }
                break;
            case 21 :
                // InternalGlobalConstantsLexer.g:1:295: END_REPEAT
                {
                mEND_REPEAT(); 

                }
                break;
            case 22 :
                // InternalGlobalConstantsLexer.g:1:306: END_STRUCT
                {
                mEND_STRUCT(); 

                }
                break;
            case 23 :
                // InternalGlobalConstantsLexer.g:1:317: IMPLEMENTS
                {
                mIMPLEMENTS(); 

                }
                break;
            case 24 :
                // InternalGlobalConstantsLexer.g:1:328: NON_RETAIN
                {
                mNON_RETAIN(); 

                }
                break;
            case 25 :
                // InternalGlobalConstantsLexer.g:1:339: READ_WRITE
                {
                mREAD_WRITE(); 

                }
                break;
            case 26 :
                // InternalGlobalConstantsLexer.g:1:350: TRANSITION
                {
                mTRANSITION(); 

                }
                break;
            case 27 :
                // InternalGlobalConstantsLexer.g:1:361: VAR_ACCESS
                {
                mVAR_ACCESS(); 

                }
                break;
            case 28 :
                // InternalGlobalConstantsLexer.g:1:372: VAR_CONFIG
                {
                mVAR_CONFIG(); 

                }
                break;
            case 29 :
                // InternalGlobalConstantsLexer.g:1:383: VAR_GLOBAL
                {
                mVAR_GLOBAL(); 

                }
                break;
            case 30 :
                // InternalGlobalConstantsLexer.g:1:394: VAR_IN_OUT
                {
                mVAR_IN_OUT(); 

                }
                break;
            case 31 :
                // InternalGlobalConstantsLexer.g:1:405: VAR_OUTPUT
                {
                mVAR_OUTPUT(); 

                }
                break;
            case 32 :
                // InternalGlobalConstantsLexer.g:1:416: END_CLASS
                {
                mEND_CLASS(); 

                }
                break;
            case 33 :
                // InternalGlobalConstantsLexer.g:1:426: END_WHILE
                {
                mEND_WHILE(); 

                }
                break;
            case 34 :
                // InternalGlobalConstantsLexer.g:1:436: INTERFACE
                {
                mINTERFACE(); 

                }
                break;
            case 35 :
                // InternalGlobalConstantsLexer.g:1:446: NAMESPACE
                {
                mNAMESPACE(); 

                }
                break;
            case 36 :
                // InternalGlobalConstantsLexer.g:1:456: PROTECTED
                {
                mPROTECTED(); 

                }
                break;
            case 37 :
                // InternalGlobalConstantsLexer.g:1:466: READ_ONLY
                {
                mREAD_ONLY(); 

                }
                break;
            case 38 :
                // InternalGlobalConstantsLexer.g:1:476: VAR_INPUT
                {
                mVAR_INPUT(); 

                }
                break;
            case 39 :
                // InternalGlobalConstantsLexer.g:1:486: ABSTRACT
                {
                mABSTRACT(); 

                }
                break;
            case 40 :
                // InternalGlobalConstantsLexer.g:1:495: CONSTANT
                {
                mCONSTANT(); 

                }
                break;
            case 41 :
                // InternalGlobalConstantsLexer.g:1:504: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 42 :
                // InternalGlobalConstantsLexer.g:1:513: END_CASE
                {
                mEND_CASE(); 

                }
                break;
            case 43 :
                // InternalGlobalConstantsLexer.g:1:522: END_STEP
                {
                mEND_STEP(); 

                }
                break;
            case 44 :
                // InternalGlobalConstantsLexer.g:1:531: END_TYPE
                {
                mEND_TYPE(); 

                }
                break;
            case 45 :
                // InternalGlobalConstantsLexer.g:1:540: FUNCTION
                {
                mFUNCTION(); 

                }
                break;
            case 46 :
                // InternalGlobalConstantsLexer.g:1:549: INTERNAL
                {
                mINTERNAL(); 

                }
                break;
            case 47 :
                // InternalGlobalConstantsLexer.g:1:558: INTERVAL
                {
                mINTERVAL(); 

                }
                break;
            case 48 :
                // InternalGlobalConstantsLexer.g:1:567: OVERRIDE
                {
                mOVERRIDE(); 

                }
                break;
            case 49 :
                // InternalGlobalConstantsLexer.g:1:576: PRIORITY
                {
                mPRIORITY(); 

                }
                break;
            case 50 :
                // InternalGlobalConstantsLexer.g:1:585: RESOURCE
                {
                mRESOURCE(); 

                }
                break;
            case 51 :
                // InternalGlobalConstantsLexer.g:1:594: VAR_TEMP
                {
                mVAR_TEMP(); 

                }
                break;
            case 52 :
                // InternalGlobalConstantsLexer.g:1:603: END_FOR
                {
                mEND_FOR(); 

                }
                break;
            case 53 :
                // InternalGlobalConstantsLexer.g:1:611: END_VAR
                {
                mEND_VAR(); 

                }
                break;
            case 54 :
                // InternalGlobalConstantsLexer.g:1:619: EXTENDS
                {
                mEXTENDS(); 

                }
                break;
            case 55 :
                // InternalGlobalConstantsLexer.g:1:627: INTERAL
                {
                mINTERAL(); 

                }
                break;
            case 56 :
                // InternalGlobalConstantsLexer.g:1:635: OVERLAP
                {
                mOVERLAP(); 

                }
                break;
            case 57 :
                // InternalGlobalConstantsLexer.g:1:643: PACKAGE
                {
                mPACKAGE(); 

                }
                break;
            case 58 :
                // InternalGlobalConstantsLexer.g:1:651: PRIVATE
                {
                mPRIVATE(); 

                }
                break;
            case 59 :
                // InternalGlobalConstantsLexer.g:1:659: PROGRAM
                {
                mPROGRAM(); 

                }
                break;
            case 60 :
                // InternalGlobalConstantsLexer.g:1:667: WSTRING
                {
                mWSTRING(); 

                }
                break;
            case 61 :
                // InternalGlobalConstantsLexer.g:1:675: ACTION
                {
                mACTION(); 

                }
                break;
            case 62 :
                // InternalGlobalConstantsLexer.g:1:682: END_IF
                {
                mEND_IF(); 

                }
                break;
            case 63 :
                // InternalGlobalConstantsLexer.g:1:689: IMPORT
                {
                mIMPORT(); 

                }
                break;
            case 64 :
                // InternalGlobalConstantsLexer.g:1:696: METHOD
                {
                mMETHOD(); 

                }
                break;
            case 65 :
                // InternalGlobalConstantsLexer.g:1:703: PUBLIC
                {
                mPUBLIC(); 

                }
                break;
            case 66 :
                // InternalGlobalConstantsLexer.g:1:710: REF_TO
                {
                mREF_TO(); 

                }
                break;
            case 67 :
                // InternalGlobalConstantsLexer.g:1:717: REPEAT
                {
                mREPEAT(); 

                }
                break;
            case 68 :
                // InternalGlobalConstantsLexer.g:1:724: RETAIN
                {
                mRETAIN(); 

                }
                break;
            case 69 :
                // InternalGlobalConstantsLexer.g:1:731: RETURN
                {
                mRETURN(); 

                }
                break;
            case 70 :
                // InternalGlobalConstantsLexer.g:1:738: SINGLE
                {
                mSINGLE(); 

                }
                break;
            case 71 :
                // InternalGlobalConstantsLexer.g:1:745: STRING
                {
                mSTRING(); 

                }
                break;
            case 72 :
                // InternalGlobalConstantsLexer.g:1:752: STRUCT
                {
                mSTRUCT(); 

                }
                break;
            case 73 :
                // InternalGlobalConstantsLexer.g:1:759: ARRAY
                {
                mARRAY(); 

                }
                break;
            case 74 :
                // InternalGlobalConstantsLexer.g:1:765: CLASS
                {
                mCLASS(); 

                }
                break;
            case 75 :
                // InternalGlobalConstantsLexer.g:1:771: DWORD
                {
                mDWORD(); 

                }
                break;
            case 76 :
                // InternalGlobalConstantsLexer.g:1:777: ELSIF
                {
                mELSIF(); 

                }
                break;
            case 77 :
                // InternalGlobalConstantsLexer.g:1:783: FALSE
                {
                mFALSE(); 

                }
                break;
            case 78 :
                // InternalGlobalConstantsLexer.g:1:789: FINAL
                {
                mFINAL(); 

                }
                break;
            case 79 :
                // InternalGlobalConstantsLexer.g:1:795: LDATE
                {
                mLDATE(); 

                }
                break;
            case 80 :
                // InternalGlobalConstantsLexer.g:1:801: LREAL
                {
                mLREAL(); 

                }
                break;
            case 81 :
                // InternalGlobalConstantsLexer.g:1:807: LTIME
                {
                mLTIME(); 

                }
                break;
            case 82 :
                // InternalGlobalConstantsLexer.g:1:813: LWORD
                {
                mLWORD(); 

                }
                break;
            case 83 :
                // InternalGlobalConstantsLexer.g:1:819: SUPER
                {
                mSUPER(); 

                }
                break;
            case 84 :
                // InternalGlobalConstantsLexer.g:1:825: UDINT
                {
                mUDINT(); 

                }
                break;
            case 85 :
                // InternalGlobalConstantsLexer.g:1:831: ULINT
                {
                mULINT(); 

                }
                break;
            case 86 :
                // InternalGlobalConstantsLexer.g:1:837: UNTIL
                {
                mUNTIL(); 

                }
                break;
            case 87 :
                // InternalGlobalConstantsLexer.g:1:843: USING
                {
                mUSING(); 

                }
                break;
            case 88 :
                // InternalGlobalConstantsLexer.g:1:849: USINT
                {
                mUSINT(); 

                }
                break;
            case 89 :
                // InternalGlobalConstantsLexer.g:1:855: WCHAR
                {
                mWCHAR(); 

                }
                break;
            case 90 :
                // InternalGlobalConstantsLexer.g:1:861: WHILE
                {
                mWHILE(); 

                }
                break;
            case 91 :
                // InternalGlobalConstantsLexer.g:1:867: BOOL
                {
                mBOOL(); 

                }
                break;
            case 92 :
                // InternalGlobalConstantsLexer.g:1:872: BYTE
                {
                mBYTE(); 

                }
                break;
            case 93 :
                // InternalGlobalConstantsLexer.g:1:877: CASE
                {
                mCASE(); 

                }
                break;
            case 94 :
                // InternalGlobalConstantsLexer.g:1:882: CHAR
                {
                mCHAR(); 

                }
                break;
            case 95 :
                // InternalGlobalConstantsLexer.g:1:887: DATE
                {
                mDATE(); 

                }
                break;
            case 96 :
                // InternalGlobalConstantsLexer.g:1:892: DINT
                {
                mDINT(); 

                }
                break;
            case 97 :
                // InternalGlobalConstantsLexer.g:1:897: ELSE
                {
                mELSE(); 

                }
                break;
            case 98 :
                // InternalGlobalConstantsLexer.g:1:902: EXIT
                {
                mEXIT(); 

                }
                break;
            case 99 :
                // InternalGlobalConstantsLexer.g:1:907: FROM
                {
                mFROM(); 

                }
                break;
            case 100 :
                // InternalGlobalConstantsLexer.g:1:912: LINT
                {
                mLINT(); 

                }
                break;
            case 101 :
                // InternalGlobalConstantsLexer.g:1:917: LTOD
                {
                mLTOD(); 

                }
                break;
            case 102 :
                // InternalGlobalConstantsLexer.g:1:922: NULL
                {
                mNULL(); 

                }
                break;
            case 103 :
                // InternalGlobalConstantsLexer.g:1:927: REAL
                {
                mREAL(); 

                }
                break;
            case 104 :
                // InternalGlobalConstantsLexer.g:1:932: SINT
                {
                mSINT(); 

                }
                break;
            case 105 :
                // InternalGlobalConstantsLexer.g:1:937: STEP
                {
                mSTEP(); 

                }
                break;
            case 106 :
                // InternalGlobalConstantsLexer.g:1:942: TASK
                {
                mTASK(); 

                }
                break;
            case 107 :
                // InternalGlobalConstantsLexer.g:1:947: THEN
                {
                mTHEN(); 

                }
                break;
            case 108 :
                // InternalGlobalConstantsLexer.g:1:952: THIS
                {
                mTHIS(); 

                }
                break;
            case 109 :
                // InternalGlobalConstantsLexer.g:1:957: TIME
                {
                mTIME(); 

                }
                break;
            case 110 :
                // InternalGlobalConstantsLexer.g:1:962: TRUE
                {
                mTRUE(); 

                }
                break;
            case 111 :
                // InternalGlobalConstantsLexer.g:1:967: TYPE
                {
                mTYPE(); 

                }
                break;
            case 112 :
                // InternalGlobalConstantsLexer.g:1:972: UINT
                {
                mUINT(); 

                }
                break;
            case 113 :
                // InternalGlobalConstantsLexer.g:1:977: WITH
                {
                mWITH(); 

                }
                break;
            case 114 :
                // InternalGlobalConstantsLexer.g:1:982: WORD
                {
                mWORD(); 

                }
                break;
            case 115 :
                // InternalGlobalConstantsLexer.g:1:987: ColonColonAsterisk
                {
                mColonColonAsterisk(); 

                }
                break;
            case 116 :
                // InternalGlobalConstantsLexer.g:1:1006: AND
                {
                mAND(); 

                }
                break;
            case 117 :
                // InternalGlobalConstantsLexer.g:1:1010: FOR
                {
                mFOR(); 

                }
                break;
            case 118 :
                // InternalGlobalConstantsLexer.g:1:1014: INT
                {
                mINT(); 

                }
                break;
            case 119 :
                // InternalGlobalConstantsLexer.g:1:1018: LDT
                {
                mLDT(); 

                }
                break;
            case 120 :
                // InternalGlobalConstantsLexer.g:1:1022: MOD
                {
                mMOD(); 

                }
                break;
            case 121 :
                // InternalGlobalConstantsLexer.g:1:1026: NOT
                {
                mNOT(); 

                }
                break;
            case 122 :
                // InternalGlobalConstantsLexer.g:1:1030: REF
                {
                mREF(); 

                }
                break;
            case 123 :
                // InternalGlobalConstantsLexer.g:1:1034: TOD
                {
                mTOD(); 

                }
                break;
            case 124 :
                // InternalGlobalConstantsLexer.g:1:1038: VAR
                {
                mVAR(); 

                }
                break;
            case 125 :
                // InternalGlobalConstantsLexer.g:1:1042: XOR
                {
                mXOR(); 

                }
                break;
            case 126 :
                // InternalGlobalConstantsLexer.g:1:1046: B
                {
                mB(); 

                }
                break;
            case 127 :
                // InternalGlobalConstantsLexer.g:1:1048: D_1
                {
                mD_1(); 

                }
                break;
            case 128 :
                // InternalGlobalConstantsLexer.g:1:1052: L
                {
                mL(); 

                }
                break;
            case 129 :
                // InternalGlobalConstantsLexer.g:1:1054: W
                {
                mW(); 

                }
                break;
            case 130 :
                // InternalGlobalConstantsLexer.g:1:1056: X
                {
                mX(); 

                }
                break;
            case 131 :
                // InternalGlobalConstantsLexer.g:1:1058: AsteriskAsterisk
                {
                mAsteriskAsterisk(); 

                }
                break;
            case 132 :
                // InternalGlobalConstantsLexer.g:1:1075: FullStopFullStop
                {
                mFullStopFullStop(); 

                }
                break;
            case 133 :
                // InternalGlobalConstantsLexer.g:1:1092: ColonColon
                {
                mColonColon(); 

                }
                break;
            case 134 :
                // InternalGlobalConstantsLexer.g:1:1103: ColonEqualsSign
                {
                mColonEqualsSign(); 

                }
                break;
            case 135 :
                // InternalGlobalConstantsLexer.g:1:1119: LessThanSignEqualsSign
                {
                mLessThanSignEqualsSign(); 

                }
                break;
            case 136 :
                // InternalGlobalConstantsLexer.g:1:1142: LessThanSignGreaterThanSign
                {
                mLessThanSignGreaterThanSign(); 

                }
                break;
            case 137 :
                // InternalGlobalConstantsLexer.g:1:1170: EqualsSignGreaterThanSign
                {
                mEqualsSignGreaterThanSign(); 

                }
                break;
            case 138 :
                // InternalGlobalConstantsLexer.g:1:1196: GreaterThanSignEqualsSign
                {
                mGreaterThanSignEqualsSign(); 

                }
                break;
            case 139 :
                // InternalGlobalConstantsLexer.g:1:1222: AT
                {
                mAT(); 

                }
                break;
            case 140 :
                // InternalGlobalConstantsLexer.g:1:1225: BY
                {
                mBY(); 

                }
                break;
            case 141 :
                // InternalGlobalConstantsLexer.g:1:1228: DO
                {
                mDO(); 

                }
                break;
            case 142 :
                // InternalGlobalConstantsLexer.g:1:1231: DT
                {
                mDT(); 

                }
                break;
            case 143 :
                // InternalGlobalConstantsLexer.g:1:1234: IF
                {
                mIF(); 

                }
                break;
            case 144 :
                // InternalGlobalConstantsLexer.g:1:1237: LD
                {
                mLD(); 

                }
                break;
            case 145 :
                // InternalGlobalConstantsLexer.g:1:1240: LT
                {
                mLT(); 

                }
                break;
            case 146 :
                // InternalGlobalConstantsLexer.g:1:1243: OF
                {
                mOF(); 

                }
                break;
            case 147 :
                // InternalGlobalConstantsLexer.g:1:1246: ON
                {
                mON(); 

                }
                break;
            case 148 :
                // InternalGlobalConstantsLexer.g:1:1249: OR
                {
                mOR(); 

                }
                break;
            case 149 :
                // InternalGlobalConstantsLexer.g:1:1252: TO
                {
                mTO(); 

                }
                break;
            case 150 :
                // InternalGlobalConstantsLexer.g:1:1255: NumberSign
                {
                mNumberSign(); 

                }
                break;
            case 151 :
                // InternalGlobalConstantsLexer.g:1:1266: Ampersand
                {
                mAmpersand(); 

                }
                break;
            case 152 :
                // InternalGlobalConstantsLexer.g:1:1276: LeftParenthesis
                {
                mLeftParenthesis(); 

                }
                break;
            case 153 :
                // InternalGlobalConstantsLexer.g:1:1292: RightParenthesis
                {
                mRightParenthesis(); 

                }
                break;
            case 154 :
                // InternalGlobalConstantsLexer.g:1:1309: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 155 :
                // InternalGlobalConstantsLexer.g:1:1318: PlusSign
                {
                mPlusSign(); 

                }
                break;
            case 156 :
                // InternalGlobalConstantsLexer.g:1:1327: Comma
                {
                mComma(); 

                }
                break;
            case 157 :
                // InternalGlobalConstantsLexer.g:1:1333: HyphenMinus
                {
                mHyphenMinus(); 

                }
                break;
            case 158 :
                // InternalGlobalConstantsLexer.g:1:1345: FullStop
                {
                mFullStop(); 

                }
                break;
            case 159 :
                // InternalGlobalConstantsLexer.g:1:1354: Solidus
                {
                mSolidus(); 

                }
                break;
            case 160 :
                // InternalGlobalConstantsLexer.g:1:1362: Colon
                {
                mColon(); 

                }
                break;
            case 161 :
                // InternalGlobalConstantsLexer.g:1:1368: Semicolon
                {
                mSemicolon(); 

                }
                break;
            case 162 :
                // InternalGlobalConstantsLexer.g:1:1378: LessThanSign
                {
                mLessThanSign(); 

                }
                break;
            case 163 :
                // InternalGlobalConstantsLexer.g:1:1391: EqualsSign
                {
                mEqualsSign(); 

                }
                break;
            case 164 :
                // InternalGlobalConstantsLexer.g:1:1402: GreaterThanSign
                {
                mGreaterThanSign(); 

                }
                break;
            case 165 :
                // InternalGlobalConstantsLexer.g:1:1418: D
                {
                mD(); 

                }
                break;
            case 166 :
                // InternalGlobalConstantsLexer.g:1:1420: T
                {
                mT(); 

                }
                break;
            case 167 :
                // InternalGlobalConstantsLexer.g:1:1422: LeftSquareBracket
                {
                mLeftSquareBracket(); 

                }
                break;
            case 168 :
                // InternalGlobalConstantsLexer.g:1:1440: RightSquareBracket
                {
                mRightSquareBracket(); 

                }
                break;
            case 169 :
                // InternalGlobalConstantsLexer.g:1:1459: LeftCurlyBracket
                {
                mLeftCurlyBracket(); 

                }
                break;
            case 170 :
                // InternalGlobalConstantsLexer.g:1:1476: RightCurlyBracket
                {
                mRightCurlyBracket(); 

                }
                break;
            case 171 :
                // InternalGlobalConstantsLexer.g:1:1494: RULE_NON_DECIMAL
                {
                mRULE_NON_DECIMAL(); 

                }
                break;
            case 172 :
                // InternalGlobalConstantsLexer.g:1:1511: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 173 :
                // InternalGlobalConstantsLexer.g:1:1520: RULE_DECIMAL
                {
                mRULE_DECIMAL(); 

                }
                break;
            case 174 :
                // InternalGlobalConstantsLexer.g:1:1533: RULE_TIME_VALUE
                {
                mRULE_TIME_VALUE(); 

                }
                break;
            case 175 :
                // InternalGlobalConstantsLexer.g:1:1549: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 176 :
                // InternalGlobalConstantsLexer.g:1:1557: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 177 :
                // InternalGlobalConstantsLexer.g:1:1569: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 178 :
                // InternalGlobalConstantsLexer.g:1:1585: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 179 :
                // InternalGlobalConstantsLexer.g:1:1601: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 180 :
                // InternalGlobalConstantsLexer.g:1:1609: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA21_eotS =
        "\1\uffff\5\66\1\113\2\66\1\126\12\66\1\172\1\66\1\62\1\u0082\1\u0084\1\u0087\1\u0089\1\u008b\2\uffff\1\u008f\4\uffff\1\u0095\5\uffff\4\u009d\1\62\1\uffff\2\62\2\uffff\3\66\1\uffff\6\66\1\u00af\1\u00b2\12\66\1\u00bd\1\u00be\1\uffff\2\66\1\u00c2\6\66\1\u00cc\1\uffff\13\66\1\u00de\1\66\1\u00e0\1\u00e1\1\u00e2\20\66\1\u00f5\1\u00f7\2\uffff\1\66\40\uffff\1\u009d\3\uffff\1\u009d\2\uffff\11\66\1\u0103\1\66\1\u0105\1\uffff\2\66\1\uffff\12\66\2\uffff\1\66\1\u0116\1\66\1\uffff\1\u011a\7\66\1\u0122\1\uffff\1\66\1\u0124\4\66\1\u012b\11\66\1\u0138\1\uffff\1\66\3\uffff\6\66\1\u0140\13\66\3\uffff\1\u014e\2\66\1\u015d\1\66\1\u015f\4\66\1\u0164\1\uffff\1\66\1\uffff\1\66\1\u0167\2\66\1\u016a\4\66\1\u016f\1\u0170\1\u0172\1\66\1\u0174\2\66\1\uffff\3\66\1\uffff\1\u0181\1\66\1\u0183\1\u0184\1\u0185\1\u0186\1\u0187\1\uffff\1\66\1\uffff\1\66\1\u018a\1\66\1\u018c\2\66\1\uffff\14\66\1\uffff\4\66\1\u01a0\1\u01a1\1\66\1\uffff\1\66\1\u01a4\2\66\1\u01a7\5\66\1\u01ae\1\u01af\1\u01b0\1\uffff\16\66\1\uffff\1\u01c4\1\uffff\2\66\1\u01c7\1\u01c8\1\uffff\1\u01ca\1\u01cc\1\uffff\1\u01cd\1\u01ce\1\uffff\3\66\1\u01d2\2\uffff\1\66\1\uffff\1\u01d4\1\uffff\14\66\1\uffff\1\66\5\uffff\2\66\1\uffff\1\66\1\uffff\15\66\1\u01f6\3\66\1\u01fa\1\u01fb\2\uffff\2\66\1\uffff\2\66\1\uffff\1\u0200\1\u0201\1\u0202\1\u0203\1\u0204\1\u0205\3\uffff\11\66\1\u020f\11\66\1\uffff\2\66\2\uffff\1\66\1\uffff\1\66\3\uffff\3\66\1\uffff\1\66\1\uffff\6\66\1\u0229\16\66\1\u0239\1\u023a\1\u023b\1\u023c\5\66\1\u0242\1\66\1\u0244\1\uffff\3\66\2\uffff\1\u0248\1\u0249\1\u024a\1\u024b\6\uffff\2\66\1\u024e\6\66\1\uffff\11\66\1\u025e\1\u025f\14\66\1\u026c\1\66\1\uffff\17\66\4\uffff\1\66\1\u027e\1\66\1\u0280\1\u0281\1\uffff\1\66\1\uffff\1\66\1\u0284\1\u0285\4\uffff\2\66\1\uffff\2\66\1\u028a\1\66\1\u028c\10\66\1\u0295\1\66\2\uffff\1\66\1\u0299\3\66\1\u029d\1\u029e\3\66\1\u02a2\1\u02a3\1\uffff\10\66\1\u02ac\6\66\1\u02b3\1\66\1\uffff\1\u02b5\2\uffff\1\u02b6\1\u02b7\2\uffff\3\66\1\u02bb\1\uffff\1\66\1\uffff\10\66\1\uffff\1\u02c5\2\66\1\uffff\3\66\2\uffff\2\66\1\u02cd\2\uffff\6\66\1\u02d4\1\66\1\uffff\3\66\1\u02d9\1\66\1\u02db\1\uffff\1\u02dc\3\uffff\3\66\1\uffff\4\66\1\u02e4\1\66\1\u02e6\1\u02e7\1\u02e8\1\uffff\7\66\1\uffff\1\u02f0\1\66\1\u02f2\1\u02f3\1\u02f4\1\u02f5\1\uffff\1\u02f6\1\66\1\u02f8\1\u02f9\1\uffff\1\u02fa\2\uffff\7\66\1\uffff\1\u0302\3\uffff\7\66\1\uffff\1\66\5\uffff\1\u030b\3\uffff\1\66\1\u030e\4\66\1\u0313\1\uffff\3\66\1\u0317\2\66\1\u031a\1\u031b\1\uffff\2\66\1\uffff\2\66\1\u0320\1\u0321\1\uffff\3\66\1\uffff\1\u0325\1\u0326\2\uffff\3\66\1\u032a\2\uffff\1\66\1\u032c\1\u032d\2\uffff\3\66\1\uffff\1\u0331\2\uffff\3\66\1\uffff\2\66\1\u0337\1\66\1\u0339\1\uffff\1\u033a\2\uffff";
    static final String DFA21_eofS =
        "\u033b\uffff";
    static final String DFA21_minS =
        "\1\0\2\114\1\101\1\104\1\101\1\60\1\106\1\101\1\60\1\101\1\105\1\101\1\102\1\106\1\103\1\105\1\111\1\104\1\117\1\72\1\117\1\102\1\52\1\56\1\75\1\76\1\75\2\uffff\1\52\4\uffff\1\52\5\uffff\2\43\2\60\1\101\1\uffff\2\0\2\uffff\1\104\1\111\1\123\1\uffff\1\117\1\116\1\114\1\116\1\117\1\122\2\60\1\105\1\117\2\116\1\101\1\123\1\101\1\124\1\117\1\116\2\60\1\uffff\1\111\1\120\1\60\1\122\1\115\1\101\1\123\1\105\1\120\1\60\1\uffff\1\116\1\115\1\114\1\101\1\111\1\103\1\102\1\123\1\124\1\122\1\104\1\60\1\105\3\60\1\124\1\110\1\111\1\124\1\122\1\124\1\104\1\116\1\105\1\120\2\111\1\124\1\111\1\116\1\117\1\60\1\52\2\uffff\1\122\40\uffff\1\60\3\uffff\1\43\2\uffff\1\137\1\105\1\124\1\105\1\102\1\103\1\123\1\101\1\115\1\60\1\124\1\60\1\uffff\1\115\1\104\1\uffff\1\101\1\122\1\124\1\106\1\123\1\105\1\122\1\105\1\122\1\124\2\uffff\1\124\1\60\1\114\1\uffff\1\60\1\105\1\116\1\105\1\113\1\116\1\123\1\105\1\60\1\uffff\1\137\1\60\1\105\1\114\1\104\1\117\1\60\1\105\1\101\1\107\1\117\1\113\1\114\1\124\1\111\1\101\1\60\1\uffff\1\122\3\uffff\1\122\1\101\1\114\1\110\1\104\1\110\1\60\1\107\1\111\1\120\1\105\2\116\1\111\1\116\1\124\1\114\1\105\3\uffff\1\60\1\101\1\116\1\60\1\106\1\60\1\101\1\124\1\105\1\114\1\60\1\uffff\1\105\1\uffff\1\105\1\60\1\114\1\104\1\60\1\111\1\124\1\111\1\123\3\60\1\104\1\60\1\111\1\122\1\uffff\1\105\1\122\1\101\1\uffff\1\60\1\123\5\60\1\uffff\1\122\1\uffff\1\123\1\60\1\137\1\60\1\125\1\124\1\uffff\1\101\1\111\1\122\1\105\2\122\2\101\1\111\1\122\1\117\1\131\1\uffff\1\114\1\111\1\122\1\105\2\60\1\117\1\uffff\1\114\1\60\1\116\1\103\1\60\1\122\2\124\1\114\1\107\3\60\1\uffff\1\114\1\117\1\101\1\122\1\106\1\101\1\105\1\122\1\103\1\105\1\124\1\110\1\101\1\104\1\uffff\1\60\1\uffff\1\114\1\111\2\60\1\uffff\2\60\1\uffff\2\60\1\uffff\1\107\1\101\1\116\1\60\2\uffff\1\101\1\uffff\1\60\1\uffff\2\101\1\115\1\124\1\130\1\103\1\117\1\114\1\116\1\125\1\105\1\117\1\uffff\1\111\5\uffff\1\105\1\120\1\uffff\1\117\1\uffff\1\122\1\117\1\124\2\116\1\103\1\101\1\111\1\124\1\107\1\103\1\101\1\116\1\60\1\111\1\101\1\116\2\60\2\uffff\1\104\1\105\1\uffff\1\107\1\124\1\uffff\6\60\3\uffff\1\117\1\116\1\122\1\116\1\101\1\123\1\101\1\120\1\124\1\60\1\115\1\120\1\117\2\124\1\105\1\111\1\122\1\123\1\uffff\1\103\1\117\2\uffff\1\101\1\uffff\1\117\3\uffff\1\125\1\116\1\125\1\uffff\1\116\1\uffff\1\114\3\101\1\114\1\105\1\60\1\124\1\103\1\116\1\117\1\120\1\124\1\115\1\106\2\124\1\101\1\122\1\116\1\103\4\60\1\124\1\115\1\124\2\105\1\60\1\103\1\60\1\uffff\1\104\1\120\1\107\2\uffff\4\60\6\uffff\1\102\1\103\1\60\1\106\1\123\1\105\1\116\2\105\1\uffff\1\105\1\117\1\105\1\107\1\111\1\110\1\125\1\120\1\114\2\60\1\117\2\116\1\106\1\122\1\124\1\105\1\104\1\137\1\103\2\114\1\60\1\116\1\uffff\2\105\1\106\1\102\1\117\1\125\2\120\1\137\1\111\1\101\1\103\1\111\1\114\1\105\4\uffff\1\105\1\60\1\131\2\60\1\uffff\1\124\1\uffff\1\105\2\60\4\uffff\1\101\1\124\1\uffff\1\111\1\123\1\60\1\123\1\60\1\122\1\123\1\125\1\101\1\122\2\117\1\103\1\60\1\105\2\uffff\1\116\1\60\1\104\1\137\1\101\2\60\1\137\1\123\1\105\2\60\1\uffff\1\124\1\122\1\123\1\111\1\101\1\125\1\124\1\125\1\60\1\104\1\117\1\111\1\105\1\124\1\131\1\60\1\104\1\uffff\1\60\2\uffff\2\60\2\uffff\1\114\1\111\1\107\1\60\1\uffff\1\111\1\uffff\1\106\1\120\1\122\1\124\1\101\1\116\1\104\1\124\1\uffff\1\60\1\123\1\102\1\uffff\1\137\1\104\1\124\2\uffff\2\124\1\60\2\uffff\1\123\1\116\1\123\1\107\1\114\1\124\1\60\1\124\1\uffff\1\101\2\116\1\60\1\105\1\60\1\uffff\1\60\3\uffff\1\103\1\117\1\125\1\uffff\1\124\2\101\1\103\1\60\1\115\3\60\1\uffff\1\124\1\114\1\124\1\101\2\111\1\105\1\uffff\1\60\1\101\4\60\1\uffff\1\60\1\131\2\60\1\uffff\1\60\2\uffff\1\117\1\116\1\122\1\111\2\103\1\105\1\uffff\1\60\3\uffff\1\101\1\117\1\111\1\131\1\117\1\115\1\120\1\uffff\1\114\5\uffff\1\60\3\uffff\1\116\1\60\1\101\1\117\2\105\1\60\1\uffff\1\116\1\103\1\115\1\60\1\116\1\105\2\60\1\uffff\1\123\1\102\1\uffff\1\124\1\116\2\60\1\uffff\1\124\1\113\1\105\1\uffff\2\60\2\uffff\1\124\1\114\1\111\1\60\2\uffff\1\123\2\60\2\uffff\1\101\2\117\1\uffff\1\60\2\uffff\1\116\1\103\1\116\1\uffff\1\124\1\113\1\60\1\123\1\60\1\uffff\1\60\2\uffff";
    static final String DFA21_maxS =
        "\1\uffff\1\170\1\154\1\165\1\167\1\157\1\172\1\156\1\141\1\172\1\165\1\145\1\165\1\164\1\166\1\163\1\157\1\165\1\163\1\171\1\75\1\157\1\170\1\52\1\56\2\76\1\75\2\uffff\1\52\4\uffff\1\57\5\uffff\4\165\1\172\1\uffff\2\uffff\2\uffff\1\144\1\164\1\163\1\uffff\1\157\1\156\1\154\1\156\1\157\1\162\2\172\1\145\1\157\2\156\1\141\1\163\1\141\1\164\1\157\1\156\2\172\1\uffff\1\164\1\160\1\172\1\162\1\155\1\165\1\163\1\151\1\160\1\172\1\uffff\1\164\1\155\1\154\1\164\1\157\1\143\1\142\1\163\1\164\1\162\1\144\1\172\1\145\3\172\1\164\1\150\1\151\1\164\1\162\1\164\1\144\1\156\1\162\1\160\2\151\1\164\1\151\1\156\1\157\1\172\1\52\2\uffff\1\162\40\uffff\1\165\3\uffff\1\165\2\uffff\1\137\1\145\1\164\1\151\1\142\1\143\1\163\1\141\1\155\1\172\1\164\1\172\1\uffff\1\155\1\144\1\uffff\1\141\1\162\2\164\1\163\1\145\1\162\1\145\1\162\1\164\2\uffff\1\164\1\172\1\157\1\uffff\1\172\1\145\1\156\1\145\1\153\1\156\1\163\1\145\1\172\1\uffff\1\137\1\172\1\145\2\154\1\157\1\172\1\145\1\165\1\164\1\166\1\153\1\154\1\164\1\151\1\141\1\172\1\uffff\1\162\3\uffff\1\162\1\141\1\154\1\150\1\144\1\150\1\172\1\164\1\165\1\160\1\145\2\156\1\151\1\156\1\164\1\154\1\145\3\uffff\1\172\1\167\1\156\1\172\1\146\1\172\1\141\1\164\1\145\1\154\1\172\1\uffff\1\145\1\uffff\1\145\1\172\1\154\1\144\1\172\1\151\1\164\1\151\1\163\3\172\1\144\1\172\1\151\1\162\1\uffff\1\145\1\162\1\164\1\uffff\1\172\1\163\5\172\1\uffff\1\162\1\uffff\1\163\1\172\1\137\1\172\1\165\1\164\1\uffff\1\141\1\151\1\162\1\145\2\162\2\141\1\151\1\162\1\157\1\171\1\uffff\1\162\1\151\1\162\1\145\2\172\1\157\1\uffff\1\154\1\172\1\156\1\143\1\172\1\162\2\164\1\154\1\164\3\172\1\uffff\1\154\1\165\1\157\1\171\1\156\1\141\1\145\1\162\1\143\1\145\1\164\1\150\1\141\1\144\1\uffff\1\172\1\uffff\1\154\1\151\2\172\1\uffff\2\172\1\uffff\2\172\1\uffff\1\147\1\141\1\156\1\172\2\uffff\1\141\1\uffff\1\172\1\uffff\1\141\1\166\1\155\1\164\1\170\1\143\1\157\1\154\1\156\1\165\1\145\1\157\1\uffff\1\151\5\uffff\1\145\1\160\1\uffff\1\167\1\uffff\1\162\1\157\1\164\2\156\1\143\1\141\1\151\1\164\1\147\1\143\1\141\1\156\1\172\1\151\1\141\1\156\2\172\2\uffff\1\144\1\145\1\uffff\1\147\1\164\1\uffff\6\172\3\uffff\1\157\1\156\1\162\1\156\1\141\1\163\1\141\1\160\1\164\1\172\1\155\1\163\1\157\2\164\1\162\1\151\1\162\1\163\1\uffff\1\143\1\157\2\uffff\1\141\1\uffff\1\157\3\uffff\1\165\1\156\1\165\1\uffff\1\156\1\uffff\1\154\3\141\1\154\1\145\1\172\1\164\1\143\1\156\1\157\1\160\1\164\1\155\1\146\2\164\1\141\1\162\1\156\1\143\4\172\1\164\1\155\1\164\2\145\1\172\1\143\1\172\1\uffff\1\144\1\160\1\147\2\uffff\4\172\6\uffff\1\142\1\143\1\172\1\146\1\163\1\145\1\156\2\145\1\uffff\1\145\1\157\1\145\1\147\1\151\1\150\1\165\1\160\1\154\2\172\1\157\2\156\1\146\1\162\1\164\1\145\1\144\1\137\1\143\2\154\1\172\1\156\1\uffff\2\145\1\146\1\142\1\157\1\165\2\160\1\137\1\151\1\141\1\143\1\151\1\154\1\145\4\uffff\1\145\1\172\1\171\2\172\1\uffff\1\164\1\uffff\1\145\2\172\4\uffff\1\141\1\164\1\uffff\1\151\1\163\1\172\1\163\1\172\1\162\1\163\1\165\1\141\1\162\2\157\1\143\1\172\1\145\2\uffff\1\156\1\172\1\144\1\137\1\141\2\172\1\137\1\163\1\145\2\172\1\uffff\1\164\1\162\1\163\1\151\1\141\1\165\1\164\1\165\1\172\1\144\1\157\1\151\1\145\1\164\1\171\1\172\1\144\1\uffff\1\172\2\uffff\2\172\2\uffff\1\154\1\151\1\147\1\172\1\uffff\1\151\1\uffff\1\146\1\160\1\162\1\164\1\141\1\156\1\144\1\164\1\uffff\1\172\1\163\1\142\1\uffff\1\137\1\144\1\164\2\uffff\2\164\1\172\2\uffff\1\163\1\156\1\163\1\147\1\154\1\164\1\172\1\164\1\uffff\1\141\2\156\1\172\1\145\1\172\1\uffff\1\172\3\uffff\1\143\1\157\1\165\1\uffff\1\164\2\141\1\143\1\172\1\155\3\172\1\uffff\1\164\1\154\1\164\1\141\2\151\1\145\1\uffff\1\172\1\141\4\172\1\uffff\1\172\1\171\2\172\1\uffff\1\172\2\uffff\1\157\1\156\1\162\1\151\2\143\1\145\1\uffff\1\172\3\uffff\1\141\1\157\1\151\1\171\1\157\1\155\1\160\1\uffff\1\154\5\uffff\1\172\3\uffff\1\156\1\172\1\141\1\157\2\145\1\172\1\uffff\1\156\1\143\1\155\1\172\1\156\1\145\2\172\1\uffff\1\163\1\142\1\uffff\1\164\1\156\2\172\1\uffff\1\164\1\153\1\145\1\uffff\2\172\2\uffff\1\164\1\154\1\151\1\172\2\uffff\1\163\2\172\2\uffff\1\141\2\157\1\uffff\1\172\2\uffff\1\156\1\143\1\156\1\uffff\1\164\1\153\1\172\1\163\1\172\1\uffff\1\172\2\uffff";
    static final String DFA21_acceptS =
        "\34\uffff\1\u0096\1\u0097\1\uffff\1\u0099\1\u009b\1\u009c\1\u009d\1\uffff\1\u00a1\1\u00a7\1\u00a8\1\u00a9\1\u00aa\5\uffff\1\u00af\2\uffff\1\u00b3\1\u00b4\3\uffff\1\u00af\24\uffff\1\u00a5\12\uffff\1\u00a6\42\uffff\1\u0086\1\u00a0\1\uffff\1\176\1\177\1\u0080\1\u0081\1\u0082\1\u0083\1\u009a\1\u0084\1\u009e\1\u0087\1\u0088\1\u00a2\1\u0089\1\u00a3\1\u008a\1\u00a4\1\u0096\1\u0097\1\u00b1\1\u0098\1\u0099\1\u009b\1\u009c\1\u009d\1\u00b2\1\u009f\1\u00a1\1\u00a7\1\u00a8\1\u00a9\1\u00aa\1\u00ab\1\uffff\1\u00ac\1\u00ae\1\u00ad\1\uffff\1\u00b0\1\u00b3\14\uffff\1\u0090\2\uffff\1\u0091\12\uffff\1\u008d\1\u008e\3\uffff\1\u008f\11\uffff\1\u0095\21\uffff\1\u008b\1\uffff\1\u0092\1\u0093\1\u0094\22\uffff\1\u008c\1\163\1\u0085\13\uffff\1\165\1\uffff\1\167\20\uffff\1\166\3\uffff\1\174\7\uffff\1\173\1\uffff\1\171\6\uffff\1\172\14\uffff\1\164\7\uffff\1\170\15\uffff\1\175\16\uffff\1\142\1\uffff\1\141\4\uffff\1\143\2\uffff\1\145\2\uffff\1\144\4\uffff\1\135\1\136\1\uffff\1\137\1\uffff\1\140\14\uffff\1\155\1\uffff\1\156\1\152\1\153\1\154\1\157\2\uffff\1\146\1\uffff\1\147\23\uffff\1\161\1\162\2\uffff\1\150\2\uffff\1\151\6\uffff\1\160\1\133\1\134\23\uffff\1\114\2\uffff\1\115\1\116\1\uffff\1\117\1\uffff\1\121\1\120\1\122\3\uffff\1\112\1\uffff\1\113\41\uffff\1\111\3\uffff\1\131\1\132\4\uffff\1\123\1\124\1\125\1\126\1\127\1\130\11\uffff\1\76\31\uffff\1\77\17\uffff\1\102\1\103\1\104\1\105\5\uffff\1\101\1\uffff\1\75\3\uffff\1\100\1\106\1\107\1\110\2\uffff\1\64\17\uffff\1\65\1\66\14\uffff\1\67\21\uffff\1\73\1\uffff\1\72\1\71\2\uffff\1\70\1\74\4\uffff\1\52\1\uffff\1\54\10\uffff\1\53\3\uffff\1\55\3\uffff\1\50\1\51\3\uffff\1\56\1\57\10\uffff\1\63\6\uffff\1\62\1\uffff\1\61\1\47\1\60\3\uffff\1\40\11\uffff\1\41\7\uffff\1\42\6\uffff\1\46\4\uffff\1\43\1\uffff\1\45\1\44\7\uffff\1\25\1\uffff\1\23\1\24\1\26\7\uffff\1\27\1\uffff\1\33\1\34\1\35\1\36\1\37\1\uffff\1\32\1\30\1\31\7\uffff\1\21\10\uffff\1\22\2\uffff\1\14\4\uffff\1\15\3\uffff\1\17\2\uffff\1\16\1\20\4\uffff\1\12\1\13\3\uffff\1\10\1\11\3\uffff\1\5\1\uffff\1\6\1\7\3\uffff\1\4\5\uffff\1\3\1\uffff\1\2\1\1";
    static final String DFA21_specialS =
        "\1\2\56\uffff\1\0\1\1\u030a\uffff}>";
    static final String[] DFA21_transitionS = {
            "\11\62\2\61\2\62\1\61\22\62\1\61\1\62\1\57\1\34\1\62\1\26\1\35\1\60\1\36\1\37\1\27\1\40\1\41\1\42\1\30\1\43\1\54\1\53\1\51\5\54\1\52\1\54\1\24\1\44\1\31\1\32\1\33\2\62\1\15\1\23\1\5\1\6\1\1\1\3\1\2\1\56\1\7\2\56\1\4\1\20\1\12\1\16\1\14\1\56\1\13\1\21\1\11\1\22\1\10\1\17\1\25\2\56\1\45\1\62\1\46\1\55\1\56\1\62\1\15\1\23\1\5\1\6\1\1\1\3\1\2\1\56\1\7\2\56\1\4\1\20\1\12\1\16\1\14\1\56\1\13\1\21\1\11\1\22\1\10\1\17\1\25\2\56\1\47\1\62\1\50\uff82\62",
            "\1\65\1\uffff\1\63\11\uffff\1\64\23\uffff\1\65\1\uffff\1\63\11\uffff\1\64",
            "\1\67\37\uffff\1\67",
            "\1\71\7\uffff\1\72\5\uffff\1\74\2\uffff\1\73\2\uffff\1\70\13\uffff\1\71\7\uffff\1\72\5\uffff\1\74\2\uffff\1\73\2\uffff\1\70",
            "\1\75\4\uffff\1\101\10\uffff\1\77\1\uffff\1\76\2\uffff\1\100\14\uffff\1\75\4\uffff\1\101\10\uffff\1\77\1\uffff\1\76\2\uffff\1\100",
            "\1\104\6\uffff\1\105\3\uffff\1\103\2\uffff\1\102\21\uffff\1\104\6\uffff\1\105\3\uffff\1\103\2\uffff\1\102",
            "\12\66\7\uffff\1\106\7\66\1\110\5\66\1\111\4\66\1\112\2\66\1\107\3\66\4\uffff\1\66\1\uffff\1\106\7\66\1\110\5\66\1\111\4\66\1\112\2\66\1\107\3\66",
            "\1\116\6\uffff\1\115\1\114\27\uffff\1\116\6\uffff\1\115\1\114",
            "\1\117\37\uffff\1\117",
            "\12\66\7\uffff\1\122\6\66\1\123\1\120\5\66\1\125\2\66\1\121\6\66\1\124\1\66\4\uffff\1\66\1\uffff\1\122\6\66\1\123\1\120\5\66\1\125\2\66\1\121\6\66\1\124\1\66",
            "\1\130\15\uffff\1\127\5\uffff\1\131\13\uffff\1\130\15\uffff\1\127\5\uffff\1\131",
            "\1\132\37\uffff\1\132",
            "\1\134\20\uffff\1\133\2\uffff\1\135\13\uffff\1\134\20\uffff\1\133\2\uffff\1\135",
            "\1\136\1\137\12\uffff\1\141\3\uffff\1\140\1\uffff\1\142\15\uffff\1\136\1\137\12\uffff\1\141\3\uffff\1\140\1\uffff\1\142",
            "\1\144\7\uffff\1\145\3\uffff\1\146\3\uffff\1\143\17\uffff\1\144\7\uffff\1\145\3\uffff\1\146\3\uffff\1\143",
            "\1\150\4\uffff\1\151\1\152\5\uffff\1\153\3\uffff\1\147\17\uffff\1\150\4\uffff\1\151\1\152\5\uffff\1\153\3\uffff\1\147",
            "\1\154\11\uffff\1\155\25\uffff\1\154\11\uffff\1\155",
            "\1\156\12\uffff\1\157\1\160\23\uffff\1\156\12\uffff\1\157\1\160",
            "\1\161\4\uffff\1\165\2\uffff\1\162\1\uffff\1\163\4\uffff\1\164\20\uffff\1\161\4\uffff\1\165\2\uffff\1\162\1\uffff\1\163\4\uffff\1\164",
            "\1\166\11\uffff\1\167\25\uffff\1\166\11\uffff\1\167",
            "\1\170\2\uffff\1\171",
            "\1\173\37\uffff\1\173",
            "\1\174\1\uffff\1\175\7\uffff\1\176\12\uffff\1\177\1\u0080\11\uffff\1\174\1\uffff\1\175\7\uffff\1\176\12\uffff\1\177\1\u0080",
            "\1\u0081",
            "\1\u0083",
            "\1\u0085\1\u0086",
            "\1\u0088",
            "\1\u008a",
            "",
            "",
            "\1\u008e",
            "",
            "",
            "",
            "",
            "\1\u008e\4\uffff\1\u0094",
            "",
            "",
            "",
            "",
            "",
            "\1\u009b\14\uffff\12\u009c\12\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e\11\uffff\1\u009c\4\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e",
            "\1\u009b\14\uffff\12\u009c\12\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e\11\uffff\1\u009c\4\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e",
            "\6\u009c\1\u00a0\3\u009c\12\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e\11\uffff\1\u009c\4\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e",
            "\12\u009c\12\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e\11\uffff\1\u009c\4\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e",
            "\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\0\u00a1",
            "\0\u00a1",
            "",
            "",
            "\1\u00a3\37\uffff\1\u00a3",
            "\1\u00a5\12\uffff\1\u00a4\24\uffff\1\u00a5\12\uffff\1\u00a4",
            "\1\u00a6\37\uffff\1\u00a6",
            "",
            "\1\u00a7\37\uffff\1\u00a7",
            "\1\u00a8\37\uffff\1\u00a8",
            "\1\u00a9\37\uffff\1\u00a9",
            "\1\u00aa\37\uffff\1\u00aa",
            "\1\u00ab\37\uffff\1\u00ab",
            "\1\u00ac\37\uffff\1\u00ac",
            "\12\66\7\uffff\1\u00ad\22\66\1\u00ae\6\66\4\uffff\1\66\1\uffff\1\u00ad\22\66\1\u00ae\6\66",
            "\12\66\7\uffff\10\66\1\u00b0\5\66\1\u00b1\13\66\4\uffff\1\66\1\uffff\10\66\1\u00b0\5\66\1\u00b1\13\66",
            "\1\u00b3\37\uffff\1\u00b3",
            "\1\u00b4\37\uffff\1\u00b4",
            "\1\u00b5\37\uffff\1\u00b5",
            "\1\u00b6\37\uffff\1\u00b6",
            "\1\u00b7\37\uffff\1\u00b7",
            "\1\u00b8\37\uffff\1\u00b8",
            "\1\u00b9\37\uffff\1\u00b9",
            "\1\u00ba\37\uffff\1\u00ba",
            "\1\u00bb\37\uffff\1\u00bb",
            "\1\u00bc\37\uffff\1\u00bc",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u00bf\12\uffff\1\u00c0\24\uffff\1\u00bf\12\uffff\1\u00c0",
            "\1\u00c1\37\uffff\1\u00c1",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u00c3\37\uffff\1\u00c3",
            "\1\u00c4\37\uffff\1\u00c4",
            "\1\u00c5\23\uffff\1\u00c6\13\uffff\1\u00c5\23\uffff\1\u00c6",
            "\1\u00c7\37\uffff\1\u00c7",
            "\1\u00c8\3\uffff\1\u00c9\33\uffff\1\u00c8\3\uffff\1\u00c9",
            "\1\u00ca\37\uffff\1\u00ca",
            "\12\66\7\uffff\3\66\1\u00cb\26\66\4\uffff\1\66\1\uffff\3\66\1\u00cb\26\66",
            "",
            "\1\u00cd\5\uffff\1\u00ce\31\uffff\1\u00cd\5\uffff\1\u00ce",
            "\1\u00cf\37\uffff\1\u00cf",
            "\1\u00d0\37\uffff\1\u00d0",
            "\1\u00d1\4\uffff\1\u00d3\11\uffff\1\u00d4\2\uffff\1\u00d2\1\u00d5\14\uffff\1\u00d1\4\uffff\1\u00d3\11\uffff\1\u00d4\2\uffff\1\u00d2\1\u00d5",
            "\1\u00d7\5\uffff\1\u00d6\31\uffff\1\u00d7\5\uffff\1\u00d6",
            "\1\u00d8\37\uffff\1\u00d8",
            "\1\u00d9\37\uffff\1\u00d9",
            "\1\u00da\37\uffff\1\u00da",
            "\1\u00db\37\uffff\1\u00db",
            "\1\u00dc\37\uffff\1\u00dc",
            "\1\u00dd\37\uffff\1\u00dd",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u00df\37\uffff\1\u00df",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u00e3\37\uffff\1\u00e3",
            "\1\u00e4\37\uffff\1\u00e4",
            "\1\u00e5\37\uffff\1\u00e5",
            "\1\u00e6\37\uffff\1\u00e6",
            "\1\u00e7\37\uffff\1\u00e7",
            "\1\u00e8\37\uffff\1\u00e8",
            "\1\u00e9\37\uffff\1\u00e9",
            "\1\u00ea\37\uffff\1\u00ea",
            "\1\u00ec\14\uffff\1\u00eb\22\uffff\1\u00ec\14\uffff\1\u00eb",
            "\1\u00ed\37\uffff\1\u00ed",
            "\1\u00ee\37\uffff\1\u00ee",
            "\1\u00ef\37\uffff\1\u00ef",
            "\1\u00f0\37\uffff\1\u00f0",
            "\1\u00f1\37\uffff\1\u00f1",
            "\1\u00f2\37\uffff\1\u00f2",
            "\1\u00f3\37\uffff\1\u00f3",
            "\12\66\7\uffff\23\66\1\u00f4\6\66\4\uffff\1\66\1\uffff\23\66\1\u00f4\6\66",
            "\1\u00f6",
            "",
            "",
            "\1\u00f8\37\uffff\1\u00f8",
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
            "\12\u009c\12\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e\11\uffff\1\u009c\4\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e",
            "",
            "",
            "",
            "\1\u009b\14\uffff\12\u009c\12\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e\11\uffff\1\u009c\4\uffff\1\u009e\1\u009f\2\uffff\1\u009e\4\uffff\2\u009e\4\uffff\1\u009e\1\uffff\1\u009e",
            "",
            "",
            "\1\u00f9",
            "\1\u00fa\37\uffff\1\u00fa",
            "\1\u00fb\37\uffff\1\u00fb",
            "\1\u00fd\3\uffff\1\u00fc\33\uffff\1\u00fd\3\uffff\1\u00fc",
            "\1\u00fe\37\uffff\1\u00fe",
            "\1\u00ff\37\uffff\1\u00ff",
            "\1\u0100\37\uffff\1\u0100",
            "\1\u0101\37\uffff\1\u0101",
            "\1\u0102\37\uffff\1\u0102",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0104\37\uffff\1\u0104",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0106\37\uffff\1\u0106",
            "\1\u0107\37\uffff\1\u0107",
            "",
            "\1\u0108\37\uffff\1\u0108",
            "\1\u0109\37\uffff\1\u0109",
            "\1\u010a\37\uffff\1\u010a",
            "\1\u010b\14\uffff\1\u010c\1\u010d\21\uffff\1\u010b\14\uffff\1\u010c\1\u010d",
            "\1\u010e\37\uffff\1\u010e",
            "\1\u010f\37\uffff\1\u010f",
            "\1\u0110\37\uffff\1\u0110",
            "\1\u0111\37\uffff\1\u0111",
            "\1\u0112\37\uffff\1\u0112",
            "\1\u0113\37\uffff\1\u0113",
            "",
            "",
            "\1\u0114\37\uffff\1\u0114",
            "\12\66\7\uffff\4\66\1\u0115\25\66\4\uffff\1\66\1\uffff\4\66\1\u0115\25\66",
            "\1\u0117\2\uffff\1\u0118\34\uffff\1\u0117\2\uffff\1\u0118",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\u0119\1\uffff\32\66",
            "\1\u011b\37\uffff\1\u011b",
            "\1\u011c\37\uffff\1\u011c",
            "\1\u011d\37\uffff\1\u011d",
            "\1\u011e\37\uffff\1\u011e",
            "\1\u011f\37\uffff\1\u011f",
            "\1\u0120\37\uffff\1\u0120",
            "\1\u0121\37\uffff\1\u0121",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0123",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0125\37\uffff\1\u0125",
            "\1\u0126\37\uffff\1\u0126",
            "\1\u0127\7\uffff\1\u0128\27\uffff\1\u0127\7\uffff\1\u0128",
            "\1\u0129\37\uffff\1\u0129",
            "\12\66\7\uffff\32\66\4\uffff\1\u012a\1\uffff\32\66",
            "\1\u012c\37\uffff\1\u012c",
            "\1\u012d\23\uffff\1\u012e\13\uffff\1\u012d\23\uffff\1\u012e",
            "\1\u0130\14\uffff\1\u012f\22\uffff\1\u0130\14\uffff\1\u012f",
            "\1\u0131\6\uffff\1\u0132\30\uffff\1\u0131\6\uffff\1\u0132",
            "\1\u0133\37\uffff\1\u0133",
            "\1\u0134\37\uffff\1\u0134",
            "\1\u0135\37\uffff\1\u0135",
            "\1\u0136\37\uffff\1\u0136",
            "\1\u0137\37\uffff\1\u0137",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0139\37\uffff\1\u0139",
            "",
            "",
            "",
            "\1\u013a\37\uffff\1\u013a",
            "\1\u013b\37\uffff\1\u013b",
            "\1\u013c\37\uffff\1\u013c",
            "\1\u013d\37\uffff\1\u013d",
            "\1\u013e\37\uffff\1\u013e",
            "\1\u013f\37\uffff\1\u013f",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0141\14\uffff\1\u0142\22\uffff\1\u0141\14\uffff\1\u0142",
            "\1\u0143\13\uffff\1\u0144\23\uffff\1\u0143\13\uffff\1\u0144",
            "\1\u0145\37\uffff\1\u0145",
            "\1\u0146\37\uffff\1\u0146",
            "\1\u0147\37\uffff\1\u0147",
            "\1\u0148\37\uffff\1\u0148",
            "\1\u0149\37\uffff\1\u0149",
            "\1\u014a\37\uffff\1\u014a",
            "\1\u014b\37\uffff\1\u014b",
            "\1\u014c\37\uffff\1\u014c",
            "\1\u014d\37\uffff\1\u014d",
            "",
            "",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0157\1\uffff\1\u0151\2\uffff\1\u0150\1\u014f\1\uffff\1\u0153\3\uffff\1\u0158\1\u0154\1\uffff\1\u0156\1\uffff\1\u0155\1\u0159\1\u0152\1\uffff\1\u015b\1\u015a\11\uffff\1\u0157\1\uffff\1\u0151\2\uffff\1\u0150\1\u014f\1\uffff\1\u0153\3\uffff\1\u0158\1\u0154\1\uffff\1\u0156\1\uffff\1\u0155\1\u0159\1\u0152\1\uffff\1\u015b\1\u015a",
            "\1\u015c\37\uffff\1\u015c",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u015e\37\uffff\1\u015e",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0160\37\uffff\1\u0160",
            "\1\u0161\37\uffff\1\u0161",
            "\1\u0162\37\uffff\1\u0162",
            "\1\u0163\37\uffff\1\u0163",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0165\37\uffff\1\u0165",
            "",
            "\1\u0166\37\uffff\1\u0166",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0168\37\uffff\1\u0168",
            "\1\u0169\37\uffff\1\u0169",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u016b\37\uffff\1\u016b",
            "\1\u016c\37\uffff\1\u016c",
            "\1\u016d\37\uffff\1\u016d",
            "\1\u016e\37\uffff\1\u016e",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\u0171\1\uffff\32\66",
            "\1\u0173\37\uffff\1\u0173",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0175\37\uffff\1\u0175",
            "\1\u0176\37\uffff\1\u0176",
            "",
            "\1\u0177\37\uffff\1\u0177",
            "\1\u0178\37\uffff\1\u0178",
            "\1\u017a\1\uffff\1\u017b\1\uffff\1\u0179\1\uffff\1\u017c\1\uffff\1\u017d\5\uffff\1\u017e\4\uffff\1\u017f\14\uffff\1\u017a\1\uffff\1\u017b\1\uffff\1\u0179\1\uffff\1\u017c\1\uffff\1\u017d\5\uffff\1\u017e\4\uffff\1\u017f",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\u0180\1\uffff\32\66",
            "\1\u0182\37\uffff\1\u0182",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0188\37\uffff\1\u0188",
            "",
            "\1\u0189\37\uffff\1\u0189",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u018b",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u018d\37\uffff\1\u018d",
            "\1\u018e\37\uffff\1\u018e",
            "",
            "\1\u018f\37\uffff\1\u018f",
            "\1\u0190\37\uffff\1\u0190",
            "\1\u0191\37\uffff\1\u0191",
            "\1\u0192\37\uffff\1\u0192",
            "\1\u0193\37\uffff\1\u0193",
            "\1\u0194\37\uffff\1\u0194",
            "\1\u0195\37\uffff\1\u0195",
            "\1\u0196\37\uffff\1\u0196",
            "\1\u0197\37\uffff\1\u0197",
            "\1\u0198\37\uffff\1\u0198",
            "\1\u0199\37\uffff\1\u0199",
            "\1\u019a\37\uffff\1\u019a",
            "",
            "\1\u019c\5\uffff\1\u019b\31\uffff\1\u019c\5\uffff\1\u019b",
            "\1\u019d\37\uffff\1\u019d",
            "\1\u019e\37\uffff\1\u019e",
            "\1\u019f\37\uffff\1\u019f",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u01a2\37\uffff\1\u01a2",
            "",
            "\1\u01a3\37\uffff\1\u01a3",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u01a5\37\uffff\1\u01a5",
            "\1\u01a6\37\uffff\1\u01a6",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u01a8\37\uffff\1\u01a8",
            "\1\u01a9\37\uffff\1\u01a9",
            "\1\u01aa\37\uffff\1\u01aa",
            "\1\u01ab\37\uffff\1\u01ab",
            "\1\u01ac\14\uffff\1\u01ad\22\uffff\1\u01ac\14\uffff\1\u01ad",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01b1\37\uffff\1\u01b1",
            "\1\u01b3\5\uffff\1\u01b2\31\uffff\1\u01b3\5\uffff\1\u01b2",
            "\1\u01b6\12\uffff\1\u01b5\2\uffff\1\u01b4\21\uffff\1\u01b6\12\uffff\1\u01b5\2\uffff\1\u01b4",
            "\1\u01b7\6\uffff\1\u01b8\30\uffff\1\u01b7\6\uffff\1\u01b8",
            "\1\u01ba\7\uffff\1\u01b9\27\uffff\1\u01ba\7\uffff\1\u01b9",
            "\1\u01bb\37\uffff\1\u01bb",
            "\1\u01bc\37\uffff\1\u01bc",
            "\1\u01bd\37\uffff\1\u01bd",
            "\1\u01be\37\uffff\1\u01be",
            "\1\u01bf\37\uffff\1\u01bf",
            "\1\u01c0\37\uffff\1\u01c0",
            "\1\u01c1\37\uffff\1\u01c1",
            "\1\u01c2\37\uffff\1\u01c2",
            "\1\u01c3\37\uffff\1\u01c3",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01c5\37\uffff\1\u01c5",
            "\1\u01c6\37\uffff\1\u01c6",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\u01c9\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\u01cb\1\uffff\32\66",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01cf\37\uffff\1\u01cf",
            "\1\u01d0\37\uffff\1\u01d0",
            "\1\u01d1\37\uffff\1\u01d1",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u01d3\37\uffff\1\u01d3",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01d5\37\uffff\1\u01d5",
            "\1\u01d9\4\uffff\1\u01d6\7\uffff\1\u01d7\7\uffff\1\u01d8\12\uffff\1\u01d9\4\uffff\1\u01d6\7\uffff\1\u01d7\7\uffff\1\u01d8",
            "\1\u01da\37\uffff\1\u01da",
            "\1\u01db\37\uffff\1\u01db",
            "\1\u01dc\37\uffff\1\u01dc",
            "\1\u01dd\37\uffff\1\u01dd",
            "\1\u01de\37\uffff\1\u01de",
            "\1\u01df\37\uffff\1\u01df",
            "\1\u01e0\37\uffff\1\u01e0",
            "\1\u01e1\37\uffff\1\u01e1",
            "\1\u01e2\37\uffff\1\u01e2",
            "\1\u01e3\37\uffff\1\u01e3",
            "",
            "\1\u01e4\37\uffff\1\u01e4",
            "",
            "",
            "",
            "",
            "",
            "\1\u01e5\37\uffff\1\u01e5",
            "\1\u01e6\37\uffff\1\u01e6",
            "",
            "\1\u01e8\7\uffff\1\u01e7\27\uffff\1\u01e8\7\uffff\1\u01e7",
            "",
            "\1\u01e9\37\uffff\1\u01e9",
            "\1\u01ea\37\uffff\1\u01ea",
            "\1\u01eb\37\uffff\1\u01eb",
            "\1\u01ec\37\uffff\1\u01ec",
            "\1\u01ed\37\uffff\1\u01ed",
            "\1\u01ee\37\uffff\1\u01ee",
            "\1\u01ef\37\uffff\1\u01ef",
            "\1\u01f0\37\uffff\1\u01f0",
            "\1\u01f1\37\uffff\1\u01f1",
            "\1\u01f2\37\uffff\1\u01f2",
            "\1\u01f3\37\uffff\1\u01f3",
            "\1\u01f4\37\uffff\1\u01f4",
            "\1\u01f5\37\uffff\1\u01f5",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u01f7\37\uffff\1\u01f7",
            "\1\u01f8\37\uffff\1\u01f8",
            "\1\u01f9\37\uffff\1\u01f9",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u01fc\37\uffff\1\u01fc",
            "\1\u01fd\37\uffff\1\u01fd",
            "",
            "\1\u01fe\37\uffff\1\u01fe",
            "\1\u01ff\37\uffff\1\u01ff",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "",
            "\1\u0206\37\uffff\1\u0206",
            "\1\u0207\37\uffff\1\u0207",
            "\1\u0208\37\uffff\1\u0208",
            "\1\u0209\37\uffff\1\u0209",
            "\1\u020a\37\uffff\1\u020a",
            "\1\u020b\37\uffff\1\u020b",
            "\1\u020c\37\uffff\1\u020c",
            "\1\u020d\37\uffff\1\u020d",
            "\1\u020e\37\uffff\1\u020e",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0210\37\uffff\1\u0210",
            "\1\u0212\2\uffff\1\u0211\34\uffff\1\u0212\2\uffff\1\u0211",
            "\1\u0213\37\uffff\1\u0213",
            "\1\u0214\37\uffff\1\u0214",
            "\1\u0215\37\uffff\1\u0215",
            "\1\u0217\14\uffff\1\u0216\22\uffff\1\u0217\14\uffff\1\u0216",
            "\1\u0218\37\uffff\1\u0218",
            "\1\u0219\37\uffff\1\u0219",
            "\1\u021a\37\uffff\1\u021a",
            "",
            "\1\u021b\37\uffff\1\u021b",
            "\1\u021c\37\uffff\1\u021c",
            "",
            "",
            "\1\u021d\37\uffff\1\u021d",
            "",
            "\1\u021e\37\uffff\1\u021e",
            "",
            "",
            "",
            "\1\u021f\37\uffff\1\u021f",
            "\1\u0220\37\uffff\1\u0220",
            "\1\u0221\37\uffff\1\u0221",
            "",
            "\1\u0222\37\uffff\1\u0222",
            "",
            "\1\u0223\37\uffff\1\u0223",
            "\1\u0224\37\uffff\1\u0224",
            "\1\u0225\37\uffff\1\u0225",
            "\1\u0226\37\uffff\1\u0226",
            "\1\u0227\37\uffff\1\u0227",
            "\1\u0228\37\uffff\1\u0228",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u022a\37\uffff\1\u022a",
            "\1\u022b\37\uffff\1\u022b",
            "\1\u022c\37\uffff\1\u022c",
            "\1\u022d\37\uffff\1\u022d",
            "\1\u022f\16\uffff\1\u022e\20\uffff\1\u022f",
            "\1\u0230\37\uffff\1\u0230",
            "\1\u0231\37\uffff\1\u0231",
            "\1\u0232\37\uffff\1\u0232",
            "\1\u0233\37\uffff\1\u0233",
            "\1\u0234\37\uffff\1\u0234",
            "\1\u0235\37\uffff\1\u0235",
            "\1\u0236\37\uffff\1\u0236",
            "\1\u0237\37\uffff\1\u0237",
            "\1\u0238\37\uffff\1\u0238",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u023d\37\uffff\1\u023d",
            "\1\u023e\37\uffff\1\u023e",
            "\1\u023f\37\uffff\1\u023f",
            "\1\u0240\37\uffff\1\u0240",
            "\1\u0241\37\uffff\1\u0241",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0243\37\uffff\1\u0243",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0245\37\uffff\1\u0245",
            "\1\u0246\37\uffff\1\u0246",
            "\1\u0247\37\uffff\1\u0247",
            "",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u024c\37\uffff\1\u024c",
            "\1\u024d\37\uffff\1\u024d",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u024f\37\uffff\1\u024f",
            "\1\u0250\37\uffff\1\u0250",
            "\1\u0251\37\uffff\1\u0251",
            "\1\u0252\37\uffff\1\u0252",
            "\1\u0253\37\uffff\1\u0253",
            "\1\u0254\37\uffff\1\u0254",
            "",
            "\1\u0255\37\uffff\1\u0255",
            "\1\u0256\37\uffff\1\u0256",
            "\1\u0257\37\uffff\1\u0257",
            "\1\u0258\37\uffff\1\u0258",
            "\1\u0259\37\uffff\1\u0259",
            "\1\u025a\37\uffff\1\u025a",
            "\1\u025b\37\uffff\1\u025b",
            "\1\u025c\37\uffff\1\u025c",
            "\1\u025d\37\uffff\1\u025d",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0260\37\uffff\1\u0260",
            "\1\u0261\37\uffff\1\u0261",
            "\1\u0262\37\uffff\1\u0262",
            "\1\u0263\37\uffff\1\u0263",
            "\1\u0264\37\uffff\1\u0264",
            "\1\u0265\37\uffff\1\u0265",
            "\1\u0266\37\uffff\1\u0266",
            "\1\u0267\37\uffff\1\u0267",
            "\1\u0268",
            "\1\u0269\37\uffff\1\u0269",
            "\1\u026a\37\uffff\1\u026a",
            "\1\u026b\37\uffff\1\u026b",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u026d\37\uffff\1\u026d",
            "",
            "\1\u026e\37\uffff\1\u026e",
            "\1\u026f\37\uffff\1\u026f",
            "\1\u0270\37\uffff\1\u0270",
            "\1\u0271\37\uffff\1\u0271",
            "\1\u0272\37\uffff\1\u0272",
            "\1\u0273\37\uffff\1\u0273",
            "\1\u0274\37\uffff\1\u0274",
            "\1\u0275\37\uffff\1\u0275",
            "\1\u0276",
            "\1\u0277\37\uffff\1\u0277",
            "\1\u0278\37\uffff\1\u0278",
            "\1\u0279\37\uffff\1\u0279",
            "\1\u027a\37\uffff\1\u027a",
            "\1\u027b\37\uffff\1\u027b",
            "\1\u027c\37\uffff\1\u027c",
            "",
            "",
            "",
            "",
            "\1\u027d\37\uffff\1\u027d",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u027f\37\uffff\1\u027f",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0282\37\uffff\1\u0282",
            "",
            "\1\u0283\37\uffff\1\u0283",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "",
            "",
            "\1\u0286\37\uffff\1\u0286",
            "\1\u0287\37\uffff\1\u0287",
            "",
            "\1\u0288\37\uffff\1\u0288",
            "\1\u0289\37\uffff\1\u0289",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u028b\37\uffff\1\u028b",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u028d\37\uffff\1\u028d",
            "\1\u028e\37\uffff\1\u028e",
            "\1\u028f\37\uffff\1\u028f",
            "\1\u0290\37\uffff\1\u0290",
            "\1\u0291\37\uffff\1\u0291",
            "\1\u0292\37\uffff\1\u0292",
            "\1\u0293\37\uffff\1\u0293",
            "\1\u0294\37\uffff\1\u0294",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0296\37\uffff\1\u0296",
            "",
            "",
            "\1\u0297\37\uffff\1\u0297",
            "\12\66\7\uffff\32\66\4\uffff\1\u0298\1\uffff\32\66",
            "\1\u029a\37\uffff\1\u029a",
            "\1\u029b",
            "\1\u029c\37\uffff\1\u029c",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u029f",
            "\1\u02a0\37\uffff\1\u02a0",
            "\1\u02a1\37\uffff\1\u02a1",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u02a4\37\uffff\1\u02a4",
            "\1\u02a5\37\uffff\1\u02a5",
            "\1\u02a6\37\uffff\1\u02a6",
            "\1\u02a7\37\uffff\1\u02a7",
            "\1\u02a8\37\uffff\1\u02a8",
            "\1\u02a9\37\uffff\1\u02a9",
            "\1\u02aa\37\uffff\1\u02aa",
            "\1\u02ab\37\uffff\1\u02ab",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u02ad\37\uffff\1\u02ad",
            "\1\u02ae\37\uffff\1\u02ae",
            "\1\u02af\37\uffff\1\u02af",
            "\1\u02b0\37\uffff\1\u02b0",
            "\1\u02b1\37\uffff\1\u02b1",
            "\1\u02b2\37\uffff\1\u02b2",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u02b4\37\uffff\1\u02b4",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u02b8\37\uffff\1\u02b8",
            "\1\u02b9\37\uffff\1\u02b9",
            "\1\u02ba\37\uffff\1\u02ba",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u02bc\37\uffff\1\u02bc",
            "",
            "\1\u02bd\37\uffff\1\u02bd",
            "\1\u02be\37\uffff\1\u02be",
            "\1\u02bf\37\uffff\1\u02bf",
            "\1\u02c0\37\uffff\1\u02c0",
            "\1\u02c1\37\uffff\1\u02c1",
            "\1\u02c2\37\uffff\1\u02c2",
            "\1\u02c3\37\uffff\1\u02c3",
            "\1\u02c4\37\uffff\1\u02c4",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u02c6\37\uffff\1\u02c6",
            "\1\u02c7\37\uffff\1\u02c7",
            "",
            "\1\u02c8",
            "\1\u02c9\37\uffff\1\u02c9",
            "\1\u02ca\37\uffff\1\u02ca",
            "",
            "",
            "\1\u02cb\37\uffff\1\u02cb",
            "\1\u02cc\37\uffff\1\u02cc",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u02ce\37\uffff\1\u02ce",
            "\1\u02cf\37\uffff\1\u02cf",
            "\1\u02d0\37\uffff\1\u02d0",
            "\1\u02d1\37\uffff\1\u02d1",
            "\1\u02d2\37\uffff\1\u02d2",
            "\1\u02d3\37\uffff\1\u02d3",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u02d5\37\uffff\1\u02d5",
            "",
            "\1\u02d6\37\uffff\1\u02d6",
            "\1\u02d7\37\uffff\1\u02d7",
            "\1\u02d8\37\uffff\1\u02d8",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u02da\37\uffff\1\u02da",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "",
            "\1\u02dd\37\uffff\1\u02dd",
            "\1\u02de\37\uffff\1\u02de",
            "\1\u02df\37\uffff\1\u02df",
            "",
            "\1\u02e0\37\uffff\1\u02e0",
            "\1\u02e1\37\uffff\1\u02e1",
            "\1\u02e2\37\uffff\1\u02e2",
            "\1\u02e3\37\uffff\1\u02e3",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u02e5\37\uffff\1\u02e5",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u02e9\37\uffff\1\u02e9",
            "\1\u02ea\37\uffff\1\u02ea",
            "\1\u02eb\37\uffff\1\u02eb",
            "\1\u02ec\37\uffff\1\u02ec",
            "\1\u02ed\37\uffff\1\u02ed",
            "\1\u02ee\37\uffff\1\u02ee",
            "\1\u02ef\37\uffff\1\u02ef",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u02f1\37\uffff\1\u02f1",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u02f7\37\uffff\1\u02f7",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u02fb\37\uffff\1\u02fb",
            "\1\u02fc\37\uffff\1\u02fc",
            "\1\u02fd\37\uffff\1\u02fd",
            "\1\u02fe\37\uffff\1\u02fe",
            "\1\u02ff\37\uffff\1\u02ff",
            "\1\u0300\37\uffff\1\u0300",
            "\1\u0301\37\uffff\1\u0301",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "",
            "\1\u0303\37\uffff\1\u0303",
            "\1\u0304\37\uffff\1\u0304",
            "\1\u0305\37\uffff\1\u0305",
            "\1\u0306\37\uffff\1\u0306",
            "\1\u0307\37\uffff\1\u0307",
            "\1\u0308\37\uffff\1\u0308",
            "\1\u0309\37\uffff\1\u0309",
            "",
            "\1\u030a\37\uffff\1\u030a",
            "",
            "",
            "",
            "",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "",
            "\1\u030c\37\uffff\1\u030c",
            "\12\66\7\uffff\32\66\4\uffff\1\u030d\1\uffff\32\66",
            "\1\u030f\37\uffff\1\u030f",
            "\1\u0310\37\uffff\1\u0310",
            "\1\u0311\37\uffff\1\u0311",
            "\1\u0312\37\uffff\1\u0312",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0314\37\uffff\1\u0314",
            "\1\u0315\37\uffff\1\u0315",
            "\1\u0316\37\uffff\1\u0316",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0318\37\uffff\1\u0318",
            "\1\u0319\37\uffff\1\u0319",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u031c\37\uffff\1\u031c",
            "\1\u031d\37\uffff\1\u031d",
            "",
            "\1\u031e\37\uffff\1\u031e",
            "\1\u031f\37\uffff\1\u031f",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0322\37\uffff\1\u0322",
            "\1\u0323\37\uffff\1\u0323",
            "\1\u0324\37\uffff\1\u0324",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u0327\37\uffff\1\u0327",
            "\1\u0328\37\uffff\1\u0328",
            "\1\u0329\37\uffff\1\u0329",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u032b\37\uffff\1\u032b",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u032e\37\uffff\1\u032e",
            "\1\u032f\37\uffff\1\u032f",
            "\1\u0330\37\uffff\1\u0330",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u0332\37\uffff\1\u0332",
            "\1\u0333\37\uffff\1\u0333",
            "\1\u0334\37\uffff\1\u0334",
            "",
            "\1\u0335\37\uffff\1\u0335",
            "\1\u0336\37\uffff\1\u0336",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\u0338\37\uffff\1\u0338",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            "\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( END_GLOBALCONSTANTS | END_FUNCTION_BLOCK | END_CONFIGURATION | GLOBALCONSTANTS | END_TRANSITION | FUNCTION_BLOCK | LDATE_AND_TIME | CONFIGURATION | DATE_AND_TIME | END_INTERFACE | END_NAMESPACE | END_FUNCTION | END_RESOURCE | INITIAL_STEP | LTIME_OF_DAY | VAR_EXTERNAL | END_PROGRAM | TIME_OF_DAY | END_ACTION | END_METHOD | END_REPEAT | END_STRUCT | IMPLEMENTS | NON_RETAIN | READ_WRITE | TRANSITION | VAR_ACCESS | VAR_CONFIG | VAR_GLOBAL | VAR_IN_OUT | VAR_OUTPUT | END_CLASS | END_WHILE | INTERFACE | NAMESPACE | PROTECTED | READ_ONLY | VAR_INPUT | ABSTRACT | CONSTANT | CONTINUE | END_CASE | END_STEP | END_TYPE | FUNCTION | INTERNAL | INTERVAL | OVERRIDE | PRIORITY | RESOURCE | VAR_TEMP | END_FOR | END_VAR | EXTENDS | INTERAL | OVERLAP | PACKAGE | PRIVATE | PROGRAM | WSTRING | ACTION | END_IF | IMPORT | METHOD | PUBLIC | REF_TO | REPEAT | RETAIN | RETURN | SINGLE | STRING | STRUCT | ARRAY | CLASS | DWORD | ELSIF | FALSE | FINAL | LDATE | LREAL | LTIME | LWORD | SUPER | UDINT | ULINT | UNTIL | USING | USINT | WCHAR | WHILE | BOOL | BYTE | CASE | CHAR | DATE | DINT | ELSE | EXIT | FROM | LINT | LTOD | NULL | REAL | SINT | STEP | TASK | THEN | THIS | TIME | TRUE | TYPE | UINT | WITH | WORD | ColonColonAsterisk | AND | FOR | INT | LDT | MOD | NOT | REF | TOD | VAR | XOR | B | D_1 | L | W | X | AsteriskAsterisk | FullStopFullStop | ColonColon | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | DO | DT | IF | LD | LT | OF | ON | OR | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | D | T | LeftSquareBracket | RightSquareBracket | LeftCurlyBracket | RightCurlyBracket | RULE_NON_DECIMAL | RULE_INT | RULE_DECIMAL | RULE_TIME_VALUE | RULE_ID | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_47 = input.LA(1);

                        s = -1;
                        if ( ((LA21_47>='\u0000' && LA21_47<='\uFFFF')) ) {s = 161;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA21_48 = input.LA(1);

                        s = -1;
                        if ( ((LA21_48>='\u0000' && LA21_48<='\uFFFF')) ) {s = 161;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA21_0 = input.LA(1);

                        s = -1;
                        if ( (LA21_0=='E'||LA21_0=='e') ) {s = 1;}

                        else if ( (LA21_0=='G'||LA21_0=='g') ) {s = 2;}

                        else if ( (LA21_0=='F'||LA21_0=='f') ) {s = 3;}

                        else if ( (LA21_0=='L'||LA21_0=='l') ) {s = 4;}

                        else if ( (LA21_0=='C'||LA21_0=='c') ) {s = 5;}

                        else if ( (LA21_0=='D'||LA21_0=='d') ) {s = 6;}

                        else if ( (LA21_0=='I'||LA21_0=='i') ) {s = 7;}

                        else if ( (LA21_0=='V'||LA21_0=='v') ) {s = 8;}

                        else if ( (LA21_0=='T'||LA21_0=='t') ) {s = 9;}

                        else if ( (LA21_0=='N'||LA21_0=='n') ) {s = 10;}

                        else if ( (LA21_0=='R'||LA21_0=='r') ) {s = 11;}

                        else if ( (LA21_0=='P'||LA21_0=='p') ) {s = 12;}

                        else if ( (LA21_0=='A'||LA21_0=='a') ) {s = 13;}

                        else if ( (LA21_0=='O'||LA21_0=='o') ) {s = 14;}

                        else if ( (LA21_0=='W'||LA21_0=='w') ) {s = 15;}

                        else if ( (LA21_0=='M'||LA21_0=='m') ) {s = 16;}

                        else if ( (LA21_0=='S'||LA21_0=='s') ) {s = 17;}

                        else if ( (LA21_0=='U'||LA21_0=='u') ) {s = 18;}

                        else if ( (LA21_0=='B'||LA21_0=='b') ) {s = 19;}

                        else if ( (LA21_0==':') ) {s = 20;}

                        else if ( (LA21_0=='X'||LA21_0=='x') ) {s = 21;}

                        else if ( (LA21_0=='%') ) {s = 22;}

                        else if ( (LA21_0=='*') ) {s = 23;}

                        else if ( (LA21_0=='.') ) {s = 24;}

                        else if ( (LA21_0=='<') ) {s = 25;}

                        else if ( (LA21_0=='=') ) {s = 26;}

                        else if ( (LA21_0=='>') ) {s = 27;}

                        else if ( (LA21_0=='#') ) {s = 28;}

                        else if ( (LA21_0=='&') ) {s = 29;}

                        else if ( (LA21_0=='(') ) {s = 30;}

                        else if ( (LA21_0==')') ) {s = 31;}

                        else if ( (LA21_0=='+') ) {s = 32;}

                        else if ( (LA21_0==',') ) {s = 33;}

                        else if ( (LA21_0=='-') ) {s = 34;}

                        else if ( (LA21_0=='/') ) {s = 35;}

                        else if ( (LA21_0==';') ) {s = 36;}

                        else if ( (LA21_0=='[') ) {s = 37;}

                        else if ( (LA21_0==']') ) {s = 38;}

                        else if ( (LA21_0=='{') ) {s = 39;}

                        else if ( (LA21_0=='}') ) {s = 40;}

                        else if ( (LA21_0=='2') ) {s = 41;}

                        else if ( (LA21_0=='8') ) {s = 42;}

                        else if ( (LA21_0=='1') ) {s = 43;}

                        else if ( (LA21_0=='0'||(LA21_0>='3' && LA21_0<='7')||LA21_0=='9') ) {s = 44;}

                        else if ( (LA21_0=='^') ) {s = 45;}

                        else if ( (LA21_0=='H'||(LA21_0>='J' && LA21_0<='K')||LA21_0=='Q'||(LA21_0>='Y' && LA21_0<='Z')||LA21_0=='_'||LA21_0=='h'||(LA21_0>='j' && LA21_0<='k')||LA21_0=='q'||(LA21_0>='y' && LA21_0<='z')) ) {s = 46;}

                        else if ( (LA21_0=='\"') ) {s = 47;}

                        else if ( (LA21_0=='\'') ) {s = 48;}

                        else if ( ((LA21_0>='\t' && LA21_0<='\n')||LA21_0=='\r'||LA21_0==' ') ) {s = 49;}

                        else if ( ((LA21_0>='\u0000' && LA21_0<='\b')||(LA21_0>='\u000B' && LA21_0<='\f')||(LA21_0>='\u000E' && LA21_0<='\u001F')||LA21_0=='!'||LA21_0=='$'||(LA21_0>='?' && LA21_0<='@')||LA21_0=='\\'||LA21_0=='`'||LA21_0=='|'||(LA21_0>='~' && LA21_0<='\uFFFF')) ) {s = 50;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 21, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}