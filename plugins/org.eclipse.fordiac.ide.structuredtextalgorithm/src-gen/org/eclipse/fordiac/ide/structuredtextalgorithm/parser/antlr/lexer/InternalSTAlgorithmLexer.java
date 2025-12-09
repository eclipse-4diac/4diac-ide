package org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
@SuppressWarnings("all")
public class InternalSTAlgorithmLexer extends Lexer {
    public static final int EqualsSignGreaterThanSign=139;
    public static final int LessThanSign=164;
    public static final int RULE_TIME_HOURS=180;
    public static final int INTERNAL=49;
    public static final int REF_TO=68;
    public static final int LINT=102;
    public static final int GreaterThanSign=166;
    public static final int RULE_ID=186;
    public static final int CONFIGURATION=9;
    public static final int UDINT=86;
    public static final int GreaterThanSignEqualsSign=140;
    public static final int ColonColon=135;
    public static final int AT=141;
    public static final int RULE_INT=175;
    public static final int END_FOR=55;
    public static final int THEN=109;
    public static final int XOR=127;
    public static final int PROGRAM=61;
    public static final int TIME_OF_DAY=20;
    public static final int B=128;
    public static final int LDATE_AND_TIME=8;
    public static final int REPEAT=69;
    public static final int D=167;
    public static final int L=130;
    public static final int T=168;
    public static final int W=131;
    public static final int BY=142;
    public static final int X=132;
    public static final int END_REPEAT=23;
    public static final int Solidus=161;
    public static final int RightCurlyBracket=172;
    public static final int PROTECTED=39;
    public static final int RESOURCE=53;
    public static final int INTERVAL=50;
    public static final int FullStop=160;
    public static final int RULE_TIME_SECONDS=182;
    public static final int INTERAL=58;
    public static final int RULE_TIME_VALUE=178;
    public static final int CONTINUE=44;
    public static final int Semicolon=163;
    public static final int REF=124;
    public static final int VAR_OUTPUT=33;
    public static final int STRING=73;
    public static final int TO=151;
    public static final int INITIAL_STEP=16;
    public static final int EXTENDS=57;
    public static final int PRIORITY=52;
    public static final int CLASS=76;
    public static final int DO=143;
    public static final int END_CONFIGURATION=5;
    public static final int DT=144;
    public static final int END_VAR=56;
    public static final int END_STEP=46;
    public static final int RULE_TIME_PART=177;
    public static final int FullStopFullStop=134;
    public static final int Ampersand=153;
    public static final int END_NAMESPACE=13;
    public static final int END_ACTION=21;
    public static final int USINT=90;
    public static final int DWORD=77;
    public static final int FOR=119;
    public static final int RightParenthesis=155;
    public static final int FINAL=80;
    public static final int END_FUNCTION=14;
    public static final int USING=89;
    public static final int RULE_DECIMAL=176;
    public static final int NOT=123;
    public static final int AsteriskAsterisk=133;
    public static final int THIS=110;
    public static final int SINT=106;
    public static final int VAR_GLOBAL=31;
    public static final int WCHAR=91;
    public static final int VAR_EXTERNAL=18;
    public static final int RULE_SL_COMMENT=189;
    public static final int RETURN=71;
    public static final int ColonColonAsterisk=117;
    public static final int END_RESOURCE=15;
    public static final int Colon=162;
    public static final int EOF=-1;
    public static final int Asterisk=156;
    public static final int RULE_TIME_MILLIS=183;
    public static final int MOD=122;
    public static final int RETAIN=70;
    public static final int LeftCurlyBracket=171;
    public static final int STEP=107;
    public static final int TIME=111;
    public static final int WITH=115;
    public static final int RULE_TIME_MINUTES=181;
    public static final int END_CLASS=35;
    public static final int ACTION=63;
    public static final int BOOL=93;
    public static final int D_1=129;
    public static final int FALSE=79;
    public static final int RULE_TIME_MICROS=184;
    public static final int LWORD=84;
    public static final int LessThanSignGreaterThanSign=138;
    public static final int FUNCTION_BLOCK=7;
    public static final int VAR=126;
    public static final int END_IF=64;
    public static final int ULINT=87;
    public static final int END_CASE=45;
    public static final int LeftParenthesis=154;
    public static final int INTERFACE=37;
    public static final int VAR_CONFIG=30;
    public static final int BYTE=94;
    public static final int ELSE=99;
    public static final int TYPE=113;
    public static final int IF=145;
    public static final int WORD=116;
    public static final int DATE_AND_TIME=10;
    public static final int TOD=125;
    public static final int DINT=98;
    public static final int FUNCTION=48;
    public static final int RULE_TIME_NANOS=185;
    public static final int CASE=95;
    public static final int END_ALGORITHM=11;
    public static final int PlusSign=157;
    public static final int RULE_TIME_DAYS=179;
    public static final int RULE_ML_COMMENT=188;
    public static final int PUBLIC=67;
    public static final int LeftSquareBracket=169;
    public static final int EXIT=100;
    public static final int CHAR=96;
    public static final int LTIME=83;
    public static final int IMPORT=65;
    public static final int Comma=158;
    public static final int HyphenMinus=159;
    public static final int ELSIF=78;
    public static final int LessThanSignEqualsSign=137;
    public static final int VAR_INPUT=41;
    public static final int VAR_TEMP=54;
    public static final int CONSTANT=43;
    public static final int PRIVATE=60;
    public static final int TRANSITION=28;
    public static final int LD=146;
    public static final int RULE_HEX_DIGIT=173;
    public static final int END_TYPE=47;
    public static final int UINT=114;
    public static final int LTOD=103;
    public static final int NAMESPACE=38;
    public static final int SINGLE=72;
    public static final int ARRAY=75;
    public static final int LT=147;
    public static final int FROM=101;
    public static final int WSTRING=62;
    public static final int READ_WRITE=27;
    public static final int END_STRUCT=24;
    public static final int OVERLAP=59;
    public static final int RightSquareBracket=170;
    public static final int TASK=108;
    public static final int NULL=104;
    public static final int TRUE=112;
    public static final int ColonEqualsSign=136;
    public static final int END_WHILE=36;
    public static final int DATE=97;
    public static final int LDATE=81;
    public static final int AND=118;
    public static final int NumberSign=152;
    public static final int REAL=105;
    public static final int METHOD=66;
    public static final int NON_RETAIN=26;
    public static final int STRUCT=74;
    public static final int LTIME_OF_DAY=17;
    public static final int END_TRANSITION=6;
    public static final int LREAL=82;
    public static final int ALGORITHM=34;
    public static final int END_FUNCTION_BLOCK=4;
    public static final int RULE_STRING=187;
    public static final int VAR_ACCESS=29;
    public static final int ABSTRACT=42;
    public static final int READ_ONLY=40;
    public static final int INT=120;
    public static final int EqualsSign=165;
    public static final int OF=148;
    public static final int END_METHOD=22;
    public static final int LDT=121;
    public static final int ON=149;
    public static final int SUPER=85;
    public static final int OR=150;
    public static final int RULE_WS=190;
    public static final int VAR_IN_OUT=32;
    public static final int END_INTERFACE=12;
    public static final int IMPLEMENTS=25;
    public static final int RULE_ANY_OTHER=191;
    public static final int UNTIL=88;
    public static final int OVERRIDE=51;
    public static final int WHILE=92;
    public static final int END_PROGRAM=19;
    public static final int RULE_NON_DECIMAL=174;

    // delegates
    // delegators

    public InternalSTAlgorithmLexer() {;} 
    public InternalSTAlgorithmLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalSTAlgorithmLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalSTAlgorithmLexer.g"; }

    // $ANTLR start "END_FUNCTION_BLOCK"
    public final void mEND_FUNCTION_BLOCK() throws RecognitionException {
        try {
            int _type = END_FUNCTION_BLOCK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:23:20: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' ) )
            // InternalSTAlgorithmLexer.g:23:22: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' )
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
            // InternalSTAlgorithmLexer.g:25:19: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:25:21: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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

    // $ANTLR start "END_TRANSITION"
    public final void mEND_TRANSITION() throws RecognitionException {
        try {
            int _type = END_TRANSITION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:27:16: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:27:18: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:29:16: ( ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' ) )
            // InternalSTAlgorithmLexer.g:29:18: ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' )
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
            // InternalSTAlgorithmLexer.g:31:16: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:31:18: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:33:15: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:33:17: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:35:15: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:35:17: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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

    // $ANTLR start "END_ALGORITHM"
    public final void mEND_ALGORITHM() throws RecognitionException {
        try {
            int _type = END_ALGORITHM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:37:15: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'G' | 'g' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:37:17: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'G' | 'g' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'M' | 'm' )
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

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
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

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
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
    // $ANTLR end "END_ALGORITHM"

    // $ANTLR start "END_INTERFACE"
    public final void mEND_INTERFACE() throws RecognitionException {
        try {
            int _type = END_INTERFACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:39:15: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:39:17: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:41:15: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:41:17: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:43:14: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:43:16: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:45:14: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:45:16: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:47:14: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:47:16: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
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
            // InternalSTAlgorithmLexer.g:49:14: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:49:16: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:51:14: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:51:16: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:53:13: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:53:15: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:55:13: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:55:15: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:57:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:57:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:59:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:59:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:61:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:61:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:63:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:63:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:65:12: ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:65:14: ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:67:12: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:67:14: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:69:12: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:69:14: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:71:12: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:71:14: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:73:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:73:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:75:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) )
            // InternalSTAlgorithmLexer.g:75:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' )
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
            // InternalSTAlgorithmLexer.g:77:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:77:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:79:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:79:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:81:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:81:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' )
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

    // $ANTLR start "ALGORITHM"
    public final void mALGORITHM() throws RecognitionException {
        try {
            int _type = ALGORITHM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:83:11: ( ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'G' | 'g' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:83:13: ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'G' | 'g' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'M' | 'm' )
            {
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

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
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

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
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
    // $ANTLR end "ALGORITHM"

    // $ANTLR start "END_CLASS"
    public final void mEND_CLASS() throws RecognitionException {
        try {
            int _type = END_CLASS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:85:11: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:85:13: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:87:11: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:87:13: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:89:11: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:89:13: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:91:11: ( ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:91:13: ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:93:11: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:93:13: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:95:11: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'L' | 'l' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:95:13: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'L' | 'l' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:97:11: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:97:13: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:99:10: ( ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:99:12: ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:101:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:101:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:103:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:103:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:105:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:105:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:107:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:107:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
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
            // InternalSTAlgorithmLexer.g:109:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:109:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:111:10: ( ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:111:12: ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:113:10: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:113:12: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:115:10: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:115:12: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:117:10: ( ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'D' | 'd' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:117:12: ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'D' | 'd' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:119:10: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:119:12: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:121:10: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:121:12: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:123:10: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'T' | 't' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:123:12: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'T' | 't' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'P' | 'p' )
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
            // InternalSTAlgorithmLexer.g:125:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:125:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:127:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:127:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:129:9: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:129:11: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:131:9: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:131:11: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:133:9: ( ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:133:11: ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'P' | 'p' )
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

    // $ANTLR start "PRIVATE"
    public final void mPRIVATE() throws RecognitionException {
        try {
            int _type = PRIVATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:135:9: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:135:11: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:137:9: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:137:11: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:139:9: ( ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalSTAlgorithmLexer.g:139:11: ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
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
            // InternalSTAlgorithmLexer.g:141:8: ( ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:141:10: ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:143:8: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:143:10: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:145:8: ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:145:10: ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:147:8: ( ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:147:10: ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:149:8: ( ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'C' | 'c' ) )
            // InternalSTAlgorithmLexer.g:149:10: ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'C' | 'c' )
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
            // InternalSTAlgorithmLexer.g:151:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) '_' ( 'T' | 't' ) ( 'O' | 'o' ) )
            // InternalSTAlgorithmLexer.g:151:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) '_' ( 'T' | 't' ) ( 'O' | 'o' )
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
            // InternalSTAlgorithmLexer.g:153:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:153:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:155:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:155:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:157:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:157:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:159:8: ( ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:159:10: ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:161:8: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalSTAlgorithmLexer.g:161:10: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
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
            // InternalSTAlgorithmLexer.g:163:8: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:163:10: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:165:7: ( ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:165:9: ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:167:7: ( ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:167:9: ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:169:7: ( ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:169:9: ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:171:7: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:171:9: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:173:7: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:173:9: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:175:7: ( ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:175:9: ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:177:7: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:177:9: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:179:7: ( ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:179:9: ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:181:7: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:181:9: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:183:7: ( ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:183:9: ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:185:7: ( ( 'S' | 's' ) ( 'U' | 'u' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:185:9: ( 'S' | 's' ) ( 'U' | 'u' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:187:7: ( ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:187:9: ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:189:7: ( ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:189:9: ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:191:7: ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:191:9: ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:193:7: ( ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalSTAlgorithmLexer.g:193:9: ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
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
            // InternalSTAlgorithmLexer.g:195:7: ( ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:195:9: ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:197:7: ( ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:197:9: ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:199:7: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:199:9: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:201:6: ( ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:201:8: ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:203:6: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:203:8: ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:205:6: ( ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:205:8: ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:207:6: ( ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:207:8: ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:209:6: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:209:8: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:211:6: ( ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:211:8: ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:213:6: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:213:8: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:215:6: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:215:8: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:217:6: ( ( 'F' | 'f' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:217:8: ( 'F' | 'f' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:219:6: ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:219:8: ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:221:6: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:221:8: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:223:6: ( ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:223:8: ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:225:6: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:225:8: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:227:6: ( ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:227:8: ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:229:6: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:229:8: ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
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
            // InternalSTAlgorithmLexer.g:231:6: ( ( 'T' | 't' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'K' | 'k' ) )
            // InternalSTAlgorithmLexer.g:231:8: ( 'T' | 't' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'K' | 'k' )
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
            // InternalSTAlgorithmLexer.g:233:6: ( ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:233:8: ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:235:6: ( ( 'T' | 't' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:235:8: ( 'T' | 't' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:237:6: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:237:8: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:239:6: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:239:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:241:6: ( ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:241:8: ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:243:6: ( ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:243:8: ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:245:6: ( ( 'W' | 'w' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) )
            // InternalSTAlgorithmLexer.g:245:8: ( 'W' | 'w' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' )
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
            // InternalSTAlgorithmLexer.g:247:6: ( ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:247:8: ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:249:20: ( ':' ':' '*' )
            // InternalSTAlgorithmLexer.g:249:22: ':' ':' '*'
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
            // InternalSTAlgorithmLexer.g:251:5: ( ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:251:7: ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:253:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:253:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:255:5: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:255:7: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:257:5: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:257:7: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:259:5: ( ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:259:7: ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:261:5: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:261:7: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:263:5: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:263:7: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:265:5: ( ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:265:7: ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:267:5: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:267:7: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:269:5: ( ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:269:7: ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:271:3: ( '%' ( 'B' | 'b' ) )
            // InternalSTAlgorithmLexer.g:271:5: '%' ( 'B' | 'b' )
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
            // InternalSTAlgorithmLexer.g:273:5: ( '%' ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:273:7: '%' ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:275:3: ( '%' ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:275:5: '%' ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:277:3: ( '%' ( 'W' | 'w' ) )
            // InternalSTAlgorithmLexer.g:277:5: '%' ( 'W' | 'w' )
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
            // InternalSTAlgorithmLexer.g:279:3: ( '%' ( 'X' | 'x' ) )
            // InternalSTAlgorithmLexer.g:279:5: '%' ( 'X' | 'x' )
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
            // InternalSTAlgorithmLexer.g:281:18: ( '*' '*' )
            // InternalSTAlgorithmLexer.g:281:20: '*' '*'
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
            // InternalSTAlgorithmLexer.g:283:18: ( '.' '.' )
            // InternalSTAlgorithmLexer.g:283:20: '.' '.'
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
            // InternalSTAlgorithmLexer.g:285:12: ( ':' ':' )
            // InternalSTAlgorithmLexer.g:285:14: ':' ':'
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
            // InternalSTAlgorithmLexer.g:287:17: ( ':' '=' )
            // InternalSTAlgorithmLexer.g:287:19: ':' '='
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
            // InternalSTAlgorithmLexer.g:289:24: ( '<' '=' )
            // InternalSTAlgorithmLexer.g:289:26: '<' '='
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
            // InternalSTAlgorithmLexer.g:291:29: ( '<' '>' )
            // InternalSTAlgorithmLexer.g:291:31: '<' '>'
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
            // InternalSTAlgorithmLexer.g:293:27: ( '=' '>' )
            // InternalSTAlgorithmLexer.g:293:29: '=' '>'
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
            // InternalSTAlgorithmLexer.g:295:27: ( '>' '=' )
            // InternalSTAlgorithmLexer.g:295:29: '>' '='
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
            // InternalSTAlgorithmLexer.g:297:4: ( ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:297:6: ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:299:4: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:299:6: ( 'B' | 'b' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:301:4: ( ( 'D' | 'd' ) ( 'O' | 'o' ) )
            // InternalSTAlgorithmLexer.g:301:6: ( 'D' | 'd' ) ( 'O' | 'o' )
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
            // InternalSTAlgorithmLexer.g:303:4: ( ( 'D' | 'd' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:303:6: ( 'D' | 'd' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:305:4: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:305:6: ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:307:4: ( ( 'L' | 'l' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:307:6: ( 'L' | 'l' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:309:4: ( ( 'L' | 'l' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:309:6: ( 'L' | 'l' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:311:4: ( ( 'O' | 'o' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:311:6: ( 'O' | 'o' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:313:4: ( ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:313:6: ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:315:4: ( ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:315:6: ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:317:4: ( ( 'T' | 't' ) ( 'O' | 'o' ) )
            // InternalSTAlgorithmLexer.g:317:6: ( 'T' | 't' ) ( 'O' | 'o' )
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
            // InternalSTAlgorithmLexer.g:319:12: ( '#' )
            // InternalSTAlgorithmLexer.g:319:14: '#'
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
            // InternalSTAlgorithmLexer.g:321:11: ( '&' )
            // InternalSTAlgorithmLexer.g:321:13: '&'
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
            // InternalSTAlgorithmLexer.g:323:17: ( '(' )
            // InternalSTAlgorithmLexer.g:323:19: '('
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
            // InternalSTAlgorithmLexer.g:325:18: ( ')' )
            // InternalSTAlgorithmLexer.g:325:20: ')'
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
            // InternalSTAlgorithmLexer.g:327:10: ( '*' )
            // InternalSTAlgorithmLexer.g:327:12: '*'
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
            // InternalSTAlgorithmLexer.g:329:10: ( '+' )
            // InternalSTAlgorithmLexer.g:329:12: '+'
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
            // InternalSTAlgorithmLexer.g:331:7: ( ',' )
            // InternalSTAlgorithmLexer.g:331:9: ','
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
            // InternalSTAlgorithmLexer.g:333:13: ( '-' )
            // InternalSTAlgorithmLexer.g:333:15: '-'
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
            // InternalSTAlgorithmLexer.g:335:10: ( '.' )
            // InternalSTAlgorithmLexer.g:335:12: '.'
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
            // InternalSTAlgorithmLexer.g:337:9: ( '/' )
            // InternalSTAlgorithmLexer.g:337:11: '/'
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
            // InternalSTAlgorithmLexer.g:339:7: ( ':' )
            // InternalSTAlgorithmLexer.g:339:9: ':'
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
            // InternalSTAlgorithmLexer.g:341:11: ( ';' )
            // InternalSTAlgorithmLexer.g:341:13: ';'
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
            // InternalSTAlgorithmLexer.g:343:14: ( '<' )
            // InternalSTAlgorithmLexer.g:343:16: '<'
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
            // InternalSTAlgorithmLexer.g:345:12: ( '=' )
            // InternalSTAlgorithmLexer.g:345:14: '='
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
            // InternalSTAlgorithmLexer.g:347:17: ( '>' )
            // InternalSTAlgorithmLexer.g:347:19: '>'
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
            // InternalSTAlgorithmLexer.g:349:3: ( ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:349:5: ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:351:3: ( ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:351:5: ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:353:19: ( '[' )
            // InternalSTAlgorithmLexer.g:353:21: '['
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
            // InternalSTAlgorithmLexer.g:355:20: ( ']' )
            // InternalSTAlgorithmLexer.g:355:22: ']'
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
            // InternalSTAlgorithmLexer.g:357:18: ( '{' )
            // InternalSTAlgorithmLexer.g:357:20: '{'
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
            // InternalSTAlgorithmLexer.g:359:19: ( '}' )
            // InternalSTAlgorithmLexer.g:359:21: '}'
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
            // InternalSTAlgorithmLexer.g:361:25: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' ) )
            // InternalSTAlgorithmLexer.g:361:27: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' )
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
            // InternalSTAlgorithmLexer.g:363:18: ( ( '2#' | '8#' | '16#' ) ( RULE_HEX_DIGIT )+ )
            // InternalSTAlgorithmLexer.g:363:20: ( '2#' | '8#' | '16#' ) ( RULE_HEX_DIGIT )+
            {
            // InternalSTAlgorithmLexer.g:363:20: ( '2#' | '8#' | '16#' )
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
                    // InternalSTAlgorithmLexer.g:363:21: '2#'
                    {
                    match("2#"); 


                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmLexer.g:363:26: '8#'
                    {
                    match("8#"); 


                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmLexer.g:363:31: '16#'
                    {
                    match("16#"); 


                    }
                    break;

            }

            // InternalSTAlgorithmLexer.g:363:38: ( RULE_HEX_DIGIT )+
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
            	    // InternalSTAlgorithmLexer.g:363:38: RULE_HEX_DIGIT
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
            // InternalSTAlgorithmLexer.g:365:10: ( '0' .. '9' ( '0' .. '9' | '_' )* )
            // InternalSTAlgorithmLexer.g:365:12: '0' .. '9' ( '0' .. '9' | '_' )*
            {
            matchRange('0','9'); 
            // InternalSTAlgorithmLexer.g:365:21: ( '0' .. '9' | '_' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||LA3_0=='_') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSTAlgorithmLexer.g:
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
            // InternalSTAlgorithmLexer.g:367:14: ( RULE_INT ( ( 'e' | 'E' ) ( '+' | '-' )? RULE_INT )? )
            // InternalSTAlgorithmLexer.g:367:16: RULE_INT ( ( 'e' | 'E' ) ( '+' | '-' )? RULE_INT )?
            {
            mRULE_INT(); 
            // InternalSTAlgorithmLexer.g:367:25: ( ( 'e' | 'E' ) ( '+' | '-' )? RULE_INT )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='E'||LA5_0=='e') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:367:26: ( 'e' | 'E' ) ( '+' | '-' )? RULE_INT
                    {
                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // InternalSTAlgorithmLexer.g:367:36: ( '+' | '-' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='+'||LA4_0=='-') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalSTAlgorithmLexer.g:
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
            // InternalSTAlgorithmLexer.g:369:17: ( ( RULE_TIME_PART ( '_' )? )+ )
            // InternalSTAlgorithmLexer.g:369:19: ( RULE_TIME_PART ( '_' )? )+
            {
            // InternalSTAlgorithmLexer.g:369:19: ( RULE_TIME_PART ( '_' )? )+
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
            	    // InternalSTAlgorithmLexer.g:369:20: RULE_TIME_PART ( '_' )?
            	    {
            	    mRULE_TIME_PART(); 
            	    // InternalSTAlgorithmLexer.g:369:35: ( '_' )?
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0=='_') ) {
            	        alt6=1;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // InternalSTAlgorithmLexer.g:369:35: '_'
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
            // InternalSTAlgorithmLexer.g:371:25: ( RULE_INT ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS ) )
            // InternalSTAlgorithmLexer.g:371:27: RULE_INT ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS )
            {
            mRULE_INT(); 
            // InternalSTAlgorithmLexer.g:371:36: ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS )
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
                    // InternalSTAlgorithmLexer.g:371:37: RULE_TIME_DAYS
                    {
                    mRULE_TIME_DAYS(); 

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmLexer.g:371:52: RULE_TIME_HOURS
                    {
                    mRULE_TIME_HOURS(); 

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmLexer.g:371:68: RULE_TIME_MINUTES
                    {
                    mRULE_TIME_MINUTES(); 

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmLexer.g:371:86: RULE_TIME_SECONDS
                    {
                    mRULE_TIME_SECONDS(); 

                    }
                    break;
                case 5 :
                    // InternalSTAlgorithmLexer.g:371:104: RULE_TIME_MILLIS
                    {
                    mRULE_TIME_MILLIS(); 

                    }
                    break;
                case 6 :
                    // InternalSTAlgorithmLexer.g:371:121: RULE_TIME_MICROS
                    {
                    mRULE_TIME_MICROS(); 

                    }
                    break;
                case 7 :
                    // InternalSTAlgorithmLexer.g:371:138: RULE_TIME_NANOS
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
            // InternalSTAlgorithmLexer.g:373:25: ( ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:373:27: ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:375:26: ( ( 'H' | 'h' ) )
            // InternalSTAlgorithmLexer.g:375:28: ( 'H' | 'h' )
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
            // InternalSTAlgorithmLexer.g:377:28: ( ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:377:30: ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:379:28: ( ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:379:30: ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:381:27: ( ( 'M' | 'm' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:381:29: ( 'M' | 'm' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:383:27: ( ( 'U' | 'u' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:383:29: ( 'U' | 'u' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:385:26: ( ( 'N' | 'n' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:385:28: ( 'N' | 'n' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:387:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalSTAlgorithmLexer.g:387:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalSTAlgorithmLexer.g:387:11: ( '^' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='^') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:387:11: '^'
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

            // InternalSTAlgorithmLexer.g:387:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSTAlgorithmLexer.g:
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
            // InternalSTAlgorithmLexer.g:389:13: ( ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' ) )
            // InternalSTAlgorithmLexer.g:389:15: ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' )
            {
            // InternalSTAlgorithmLexer.g:389:15: ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' )
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
                    // InternalSTAlgorithmLexer.g:389:16: '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalSTAlgorithmLexer.g:389:20: ( '$' . | ~ ( ( '$' | '\"' ) ) )*
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
                    	    // InternalSTAlgorithmLexer.g:389:21: '$' .
                    	    {
                    	    match('$'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalSTAlgorithmLexer.g:389:27: ~ ( ( '$' | '\"' ) )
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
                    // InternalSTAlgorithmLexer.g:389:46: '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalSTAlgorithmLexer.g:389:51: ( '$' . | ~ ( ( '$' | '\\'' ) ) )*
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
                    	    // InternalSTAlgorithmLexer.g:389:52: '$' .
                    	    {
                    	    match('$'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalSTAlgorithmLexer.g:389:58: ~ ( ( '$' | '\\'' ) )
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
            // InternalSTAlgorithmLexer.g:391:17: ( ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' ) )
            // InternalSTAlgorithmLexer.g:391:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
            {
            // InternalSTAlgorithmLexer.g:391:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
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
                    // InternalSTAlgorithmLexer.g:391:20: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 

                    // InternalSTAlgorithmLexer.g:391:25: ( options {greedy=false; } : . )*
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
                    	    // InternalSTAlgorithmLexer.g:391:53: .
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
                    // InternalSTAlgorithmLexer.g:391:62: '(*' ( options {greedy=false; } : . )* '*)'
                    {
                    match("(*"); 

                    // InternalSTAlgorithmLexer.g:391:67: ( options {greedy=false; } : . )*
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
                    	    // InternalSTAlgorithmLexer.g:391:95: .
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
            // InternalSTAlgorithmLexer.g:393:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalSTAlgorithmLexer.g:393:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalSTAlgorithmLexer.g:393:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='\u0000' && LA17_0<='\t')||(LA17_0>='\u000B' && LA17_0<='\f')||(LA17_0>='\u000E' && LA17_0<='\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSTAlgorithmLexer.g:393:24: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalSTAlgorithmLexer.g:393:40: ( ( '\\r' )? '\\n' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='\n'||LA19_0=='\r') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:393:41: ( '\\r' )? '\\n'
                    {
                    // InternalSTAlgorithmLexer.g:393:41: ( '\\r' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='\r') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalSTAlgorithmLexer.g:393:41: '\\r'
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
            // InternalSTAlgorithmLexer.g:395:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalSTAlgorithmLexer.g:395:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalSTAlgorithmLexer.g:395:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            	    // InternalSTAlgorithmLexer.g:
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
            // InternalSTAlgorithmLexer.g:397:16: ( . )
            // InternalSTAlgorithmLexer.g:397:18: .
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
        // InternalSTAlgorithmLexer.g:1:8: ( END_FUNCTION_BLOCK | END_CONFIGURATION | END_TRANSITION | FUNCTION_BLOCK | LDATE_AND_TIME | CONFIGURATION | DATE_AND_TIME | END_ALGORITHM | END_INTERFACE | END_NAMESPACE | END_FUNCTION | END_RESOURCE | INITIAL_STEP | LTIME_OF_DAY | VAR_EXTERNAL | END_PROGRAM | TIME_OF_DAY | END_ACTION | END_METHOD | END_REPEAT | END_STRUCT | IMPLEMENTS | NON_RETAIN | READ_WRITE | TRANSITION | VAR_ACCESS | VAR_CONFIG | VAR_GLOBAL | VAR_IN_OUT | VAR_OUTPUT | ALGORITHM | END_CLASS | END_WHILE | INTERFACE | NAMESPACE | PROTECTED | READ_ONLY | VAR_INPUT | ABSTRACT | CONSTANT | CONTINUE | END_CASE | END_STEP | END_TYPE | FUNCTION | INTERNAL | INTERVAL | OVERRIDE | PRIORITY | RESOURCE | VAR_TEMP | END_FOR | END_VAR | EXTENDS | INTERAL | OVERLAP | PRIVATE | PROGRAM | WSTRING | ACTION | END_IF | IMPORT | METHOD | PUBLIC | REF_TO | REPEAT | RETAIN | RETURN | SINGLE | STRING | STRUCT | ARRAY | CLASS | DWORD | ELSIF | FALSE | FINAL | LDATE | LREAL | LTIME | LWORD | SUPER | UDINT | ULINT | UNTIL | USING | USINT | WCHAR | WHILE | BOOL | BYTE | CASE | CHAR | DATE | DINT | ELSE | EXIT | FROM | LINT | LTOD | NULL | REAL | SINT | STEP | TASK | THEN | THIS | TIME | TRUE | TYPE | UINT | WITH | WORD | ColonColonAsterisk | AND | FOR | INT | LDT | MOD | NOT | REF | TOD | VAR | XOR | B | D_1 | L | W | X | AsteriskAsterisk | FullStopFullStop | ColonColon | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | DO | DT | IF | LD | LT | OF | ON | OR | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | D | T | LeftSquareBracket | RightSquareBracket | LeftCurlyBracket | RightCurlyBracket | RULE_NON_DECIMAL | RULE_INT | RULE_DECIMAL | RULE_TIME_VALUE | RULE_ID | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt21=179;
        alt21 = dfa21.predict(input);
        switch (alt21) {
            case 1 :
                // InternalSTAlgorithmLexer.g:1:10: END_FUNCTION_BLOCK
                {
                mEND_FUNCTION_BLOCK(); 

                }
                break;
            case 2 :
                // InternalSTAlgorithmLexer.g:1:29: END_CONFIGURATION
                {
                mEND_CONFIGURATION(); 

                }
                break;
            case 3 :
                // InternalSTAlgorithmLexer.g:1:47: END_TRANSITION
                {
                mEND_TRANSITION(); 

                }
                break;
            case 4 :
                // InternalSTAlgorithmLexer.g:1:62: FUNCTION_BLOCK
                {
                mFUNCTION_BLOCK(); 

                }
                break;
            case 5 :
                // InternalSTAlgorithmLexer.g:1:77: LDATE_AND_TIME
                {
                mLDATE_AND_TIME(); 

                }
                break;
            case 6 :
                // InternalSTAlgorithmLexer.g:1:92: CONFIGURATION
                {
                mCONFIGURATION(); 

                }
                break;
            case 7 :
                // InternalSTAlgorithmLexer.g:1:106: DATE_AND_TIME
                {
                mDATE_AND_TIME(); 

                }
                break;
            case 8 :
                // InternalSTAlgorithmLexer.g:1:120: END_ALGORITHM
                {
                mEND_ALGORITHM(); 

                }
                break;
            case 9 :
                // InternalSTAlgorithmLexer.g:1:134: END_INTERFACE
                {
                mEND_INTERFACE(); 

                }
                break;
            case 10 :
                // InternalSTAlgorithmLexer.g:1:148: END_NAMESPACE
                {
                mEND_NAMESPACE(); 

                }
                break;
            case 11 :
                // InternalSTAlgorithmLexer.g:1:162: END_FUNCTION
                {
                mEND_FUNCTION(); 

                }
                break;
            case 12 :
                // InternalSTAlgorithmLexer.g:1:175: END_RESOURCE
                {
                mEND_RESOURCE(); 

                }
                break;
            case 13 :
                // InternalSTAlgorithmLexer.g:1:188: INITIAL_STEP
                {
                mINITIAL_STEP(); 

                }
                break;
            case 14 :
                // InternalSTAlgorithmLexer.g:1:201: LTIME_OF_DAY
                {
                mLTIME_OF_DAY(); 

                }
                break;
            case 15 :
                // InternalSTAlgorithmLexer.g:1:214: VAR_EXTERNAL
                {
                mVAR_EXTERNAL(); 

                }
                break;
            case 16 :
                // InternalSTAlgorithmLexer.g:1:227: END_PROGRAM
                {
                mEND_PROGRAM(); 

                }
                break;
            case 17 :
                // InternalSTAlgorithmLexer.g:1:239: TIME_OF_DAY
                {
                mTIME_OF_DAY(); 

                }
                break;
            case 18 :
                // InternalSTAlgorithmLexer.g:1:251: END_ACTION
                {
                mEND_ACTION(); 

                }
                break;
            case 19 :
                // InternalSTAlgorithmLexer.g:1:262: END_METHOD
                {
                mEND_METHOD(); 

                }
                break;
            case 20 :
                // InternalSTAlgorithmLexer.g:1:273: END_REPEAT
                {
                mEND_REPEAT(); 

                }
                break;
            case 21 :
                // InternalSTAlgorithmLexer.g:1:284: END_STRUCT
                {
                mEND_STRUCT(); 

                }
                break;
            case 22 :
                // InternalSTAlgorithmLexer.g:1:295: IMPLEMENTS
                {
                mIMPLEMENTS(); 

                }
                break;
            case 23 :
                // InternalSTAlgorithmLexer.g:1:306: NON_RETAIN
                {
                mNON_RETAIN(); 

                }
                break;
            case 24 :
                // InternalSTAlgorithmLexer.g:1:317: READ_WRITE
                {
                mREAD_WRITE(); 

                }
                break;
            case 25 :
                // InternalSTAlgorithmLexer.g:1:328: TRANSITION
                {
                mTRANSITION(); 

                }
                break;
            case 26 :
                // InternalSTAlgorithmLexer.g:1:339: VAR_ACCESS
                {
                mVAR_ACCESS(); 

                }
                break;
            case 27 :
                // InternalSTAlgorithmLexer.g:1:350: VAR_CONFIG
                {
                mVAR_CONFIG(); 

                }
                break;
            case 28 :
                // InternalSTAlgorithmLexer.g:1:361: VAR_GLOBAL
                {
                mVAR_GLOBAL(); 

                }
                break;
            case 29 :
                // InternalSTAlgorithmLexer.g:1:372: VAR_IN_OUT
                {
                mVAR_IN_OUT(); 

                }
                break;
            case 30 :
                // InternalSTAlgorithmLexer.g:1:383: VAR_OUTPUT
                {
                mVAR_OUTPUT(); 

                }
                break;
            case 31 :
                // InternalSTAlgorithmLexer.g:1:394: ALGORITHM
                {
                mALGORITHM(); 

                }
                break;
            case 32 :
                // InternalSTAlgorithmLexer.g:1:404: END_CLASS
                {
                mEND_CLASS(); 

                }
                break;
            case 33 :
                // InternalSTAlgorithmLexer.g:1:414: END_WHILE
                {
                mEND_WHILE(); 

                }
                break;
            case 34 :
                // InternalSTAlgorithmLexer.g:1:424: INTERFACE
                {
                mINTERFACE(); 

                }
                break;
            case 35 :
                // InternalSTAlgorithmLexer.g:1:434: NAMESPACE
                {
                mNAMESPACE(); 

                }
                break;
            case 36 :
                // InternalSTAlgorithmLexer.g:1:444: PROTECTED
                {
                mPROTECTED(); 

                }
                break;
            case 37 :
                // InternalSTAlgorithmLexer.g:1:454: READ_ONLY
                {
                mREAD_ONLY(); 

                }
                break;
            case 38 :
                // InternalSTAlgorithmLexer.g:1:464: VAR_INPUT
                {
                mVAR_INPUT(); 

                }
                break;
            case 39 :
                // InternalSTAlgorithmLexer.g:1:474: ABSTRACT
                {
                mABSTRACT(); 

                }
                break;
            case 40 :
                // InternalSTAlgorithmLexer.g:1:483: CONSTANT
                {
                mCONSTANT(); 

                }
                break;
            case 41 :
                // InternalSTAlgorithmLexer.g:1:492: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 42 :
                // InternalSTAlgorithmLexer.g:1:501: END_CASE
                {
                mEND_CASE(); 

                }
                break;
            case 43 :
                // InternalSTAlgorithmLexer.g:1:510: END_STEP
                {
                mEND_STEP(); 

                }
                break;
            case 44 :
                // InternalSTAlgorithmLexer.g:1:519: END_TYPE
                {
                mEND_TYPE(); 

                }
                break;
            case 45 :
                // InternalSTAlgorithmLexer.g:1:528: FUNCTION
                {
                mFUNCTION(); 

                }
                break;
            case 46 :
                // InternalSTAlgorithmLexer.g:1:537: INTERNAL
                {
                mINTERNAL(); 

                }
                break;
            case 47 :
                // InternalSTAlgorithmLexer.g:1:546: INTERVAL
                {
                mINTERVAL(); 

                }
                break;
            case 48 :
                // InternalSTAlgorithmLexer.g:1:555: OVERRIDE
                {
                mOVERRIDE(); 

                }
                break;
            case 49 :
                // InternalSTAlgorithmLexer.g:1:564: PRIORITY
                {
                mPRIORITY(); 

                }
                break;
            case 50 :
                // InternalSTAlgorithmLexer.g:1:573: RESOURCE
                {
                mRESOURCE(); 

                }
                break;
            case 51 :
                // InternalSTAlgorithmLexer.g:1:582: VAR_TEMP
                {
                mVAR_TEMP(); 

                }
                break;
            case 52 :
                // InternalSTAlgorithmLexer.g:1:591: END_FOR
                {
                mEND_FOR(); 

                }
                break;
            case 53 :
                // InternalSTAlgorithmLexer.g:1:599: END_VAR
                {
                mEND_VAR(); 

                }
                break;
            case 54 :
                // InternalSTAlgorithmLexer.g:1:607: EXTENDS
                {
                mEXTENDS(); 

                }
                break;
            case 55 :
                // InternalSTAlgorithmLexer.g:1:615: INTERAL
                {
                mINTERAL(); 

                }
                break;
            case 56 :
                // InternalSTAlgorithmLexer.g:1:623: OVERLAP
                {
                mOVERLAP(); 

                }
                break;
            case 57 :
                // InternalSTAlgorithmLexer.g:1:631: PRIVATE
                {
                mPRIVATE(); 

                }
                break;
            case 58 :
                // InternalSTAlgorithmLexer.g:1:639: PROGRAM
                {
                mPROGRAM(); 

                }
                break;
            case 59 :
                // InternalSTAlgorithmLexer.g:1:647: WSTRING
                {
                mWSTRING(); 

                }
                break;
            case 60 :
                // InternalSTAlgorithmLexer.g:1:655: ACTION
                {
                mACTION(); 

                }
                break;
            case 61 :
                // InternalSTAlgorithmLexer.g:1:662: END_IF
                {
                mEND_IF(); 

                }
                break;
            case 62 :
                // InternalSTAlgorithmLexer.g:1:669: IMPORT
                {
                mIMPORT(); 

                }
                break;
            case 63 :
                // InternalSTAlgorithmLexer.g:1:676: METHOD
                {
                mMETHOD(); 

                }
                break;
            case 64 :
                // InternalSTAlgorithmLexer.g:1:683: PUBLIC
                {
                mPUBLIC(); 

                }
                break;
            case 65 :
                // InternalSTAlgorithmLexer.g:1:690: REF_TO
                {
                mREF_TO(); 

                }
                break;
            case 66 :
                // InternalSTAlgorithmLexer.g:1:697: REPEAT
                {
                mREPEAT(); 

                }
                break;
            case 67 :
                // InternalSTAlgorithmLexer.g:1:704: RETAIN
                {
                mRETAIN(); 

                }
                break;
            case 68 :
                // InternalSTAlgorithmLexer.g:1:711: RETURN
                {
                mRETURN(); 

                }
                break;
            case 69 :
                // InternalSTAlgorithmLexer.g:1:718: SINGLE
                {
                mSINGLE(); 

                }
                break;
            case 70 :
                // InternalSTAlgorithmLexer.g:1:725: STRING
                {
                mSTRING(); 

                }
                break;
            case 71 :
                // InternalSTAlgorithmLexer.g:1:732: STRUCT
                {
                mSTRUCT(); 

                }
                break;
            case 72 :
                // InternalSTAlgorithmLexer.g:1:739: ARRAY
                {
                mARRAY(); 

                }
                break;
            case 73 :
                // InternalSTAlgorithmLexer.g:1:745: CLASS
                {
                mCLASS(); 

                }
                break;
            case 74 :
                // InternalSTAlgorithmLexer.g:1:751: DWORD
                {
                mDWORD(); 

                }
                break;
            case 75 :
                // InternalSTAlgorithmLexer.g:1:757: ELSIF
                {
                mELSIF(); 

                }
                break;
            case 76 :
                // InternalSTAlgorithmLexer.g:1:763: FALSE
                {
                mFALSE(); 

                }
                break;
            case 77 :
                // InternalSTAlgorithmLexer.g:1:769: FINAL
                {
                mFINAL(); 

                }
                break;
            case 78 :
                // InternalSTAlgorithmLexer.g:1:775: LDATE
                {
                mLDATE(); 

                }
                break;
            case 79 :
                // InternalSTAlgorithmLexer.g:1:781: LREAL
                {
                mLREAL(); 

                }
                break;
            case 80 :
                // InternalSTAlgorithmLexer.g:1:787: LTIME
                {
                mLTIME(); 

                }
                break;
            case 81 :
                // InternalSTAlgorithmLexer.g:1:793: LWORD
                {
                mLWORD(); 

                }
                break;
            case 82 :
                // InternalSTAlgorithmLexer.g:1:799: SUPER
                {
                mSUPER(); 

                }
                break;
            case 83 :
                // InternalSTAlgorithmLexer.g:1:805: UDINT
                {
                mUDINT(); 

                }
                break;
            case 84 :
                // InternalSTAlgorithmLexer.g:1:811: ULINT
                {
                mULINT(); 

                }
                break;
            case 85 :
                // InternalSTAlgorithmLexer.g:1:817: UNTIL
                {
                mUNTIL(); 

                }
                break;
            case 86 :
                // InternalSTAlgorithmLexer.g:1:823: USING
                {
                mUSING(); 

                }
                break;
            case 87 :
                // InternalSTAlgorithmLexer.g:1:829: USINT
                {
                mUSINT(); 

                }
                break;
            case 88 :
                // InternalSTAlgorithmLexer.g:1:835: WCHAR
                {
                mWCHAR(); 

                }
                break;
            case 89 :
                // InternalSTAlgorithmLexer.g:1:841: WHILE
                {
                mWHILE(); 

                }
                break;
            case 90 :
                // InternalSTAlgorithmLexer.g:1:847: BOOL
                {
                mBOOL(); 

                }
                break;
            case 91 :
                // InternalSTAlgorithmLexer.g:1:852: BYTE
                {
                mBYTE(); 

                }
                break;
            case 92 :
                // InternalSTAlgorithmLexer.g:1:857: CASE
                {
                mCASE(); 

                }
                break;
            case 93 :
                // InternalSTAlgorithmLexer.g:1:862: CHAR
                {
                mCHAR(); 

                }
                break;
            case 94 :
                // InternalSTAlgorithmLexer.g:1:867: DATE
                {
                mDATE(); 

                }
                break;
            case 95 :
                // InternalSTAlgorithmLexer.g:1:872: DINT
                {
                mDINT(); 

                }
                break;
            case 96 :
                // InternalSTAlgorithmLexer.g:1:877: ELSE
                {
                mELSE(); 

                }
                break;
            case 97 :
                // InternalSTAlgorithmLexer.g:1:882: EXIT
                {
                mEXIT(); 

                }
                break;
            case 98 :
                // InternalSTAlgorithmLexer.g:1:887: FROM
                {
                mFROM(); 

                }
                break;
            case 99 :
                // InternalSTAlgorithmLexer.g:1:892: LINT
                {
                mLINT(); 

                }
                break;
            case 100 :
                // InternalSTAlgorithmLexer.g:1:897: LTOD
                {
                mLTOD(); 

                }
                break;
            case 101 :
                // InternalSTAlgorithmLexer.g:1:902: NULL
                {
                mNULL(); 

                }
                break;
            case 102 :
                // InternalSTAlgorithmLexer.g:1:907: REAL
                {
                mREAL(); 

                }
                break;
            case 103 :
                // InternalSTAlgorithmLexer.g:1:912: SINT
                {
                mSINT(); 

                }
                break;
            case 104 :
                // InternalSTAlgorithmLexer.g:1:917: STEP
                {
                mSTEP(); 

                }
                break;
            case 105 :
                // InternalSTAlgorithmLexer.g:1:922: TASK
                {
                mTASK(); 

                }
                break;
            case 106 :
                // InternalSTAlgorithmLexer.g:1:927: THEN
                {
                mTHEN(); 

                }
                break;
            case 107 :
                // InternalSTAlgorithmLexer.g:1:932: THIS
                {
                mTHIS(); 

                }
                break;
            case 108 :
                // InternalSTAlgorithmLexer.g:1:937: TIME
                {
                mTIME(); 

                }
                break;
            case 109 :
                // InternalSTAlgorithmLexer.g:1:942: TRUE
                {
                mTRUE(); 

                }
                break;
            case 110 :
                // InternalSTAlgorithmLexer.g:1:947: TYPE
                {
                mTYPE(); 

                }
                break;
            case 111 :
                // InternalSTAlgorithmLexer.g:1:952: UINT
                {
                mUINT(); 

                }
                break;
            case 112 :
                // InternalSTAlgorithmLexer.g:1:957: WITH
                {
                mWITH(); 

                }
                break;
            case 113 :
                // InternalSTAlgorithmLexer.g:1:962: WORD
                {
                mWORD(); 

                }
                break;
            case 114 :
                // InternalSTAlgorithmLexer.g:1:967: ColonColonAsterisk
                {
                mColonColonAsterisk(); 

                }
                break;
            case 115 :
                // InternalSTAlgorithmLexer.g:1:986: AND
                {
                mAND(); 

                }
                break;
            case 116 :
                // InternalSTAlgorithmLexer.g:1:990: FOR
                {
                mFOR(); 

                }
                break;
            case 117 :
                // InternalSTAlgorithmLexer.g:1:994: INT
                {
                mINT(); 

                }
                break;
            case 118 :
                // InternalSTAlgorithmLexer.g:1:998: LDT
                {
                mLDT(); 

                }
                break;
            case 119 :
                // InternalSTAlgorithmLexer.g:1:1002: MOD
                {
                mMOD(); 

                }
                break;
            case 120 :
                // InternalSTAlgorithmLexer.g:1:1006: NOT
                {
                mNOT(); 

                }
                break;
            case 121 :
                // InternalSTAlgorithmLexer.g:1:1010: REF
                {
                mREF(); 

                }
                break;
            case 122 :
                // InternalSTAlgorithmLexer.g:1:1014: TOD
                {
                mTOD(); 

                }
                break;
            case 123 :
                // InternalSTAlgorithmLexer.g:1:1018: VAR
                {
                mVAR(); 

                }
                break;
            case 124 :
                // InternalSTAlgorithmLexer.g:1:1022: XOR
                {
                mXOR(); 

                }
                break;
            case 125 :
                // InternalSTAlgorithmLexer.g:1:1026: B
                {
                mB(); 

                }
                break;
            case 126 :
                // InternalSTAlgorithmLexer.g:1:1028: D_1
                {
                mD_1(); 

                }
                break;
            case 127 :
                // InternalSTAlgorithmLexer.g:1:1032: L
                {
                mL(); 

                }
                break;
            case 128 :
                // InternalSTAlgorithmLexer.g:1:1034: W
                {
                mW(); 

                }
                break;
            case 129 :
                // InternalSTAlgorithmLexer.g:1:1036: X
                {
                mX(); 

                }
                break;
            case 130 :
                // InternalSTAlgorithmLexer.g:1:1038: AsteriskAsterisk
                {
                mAsteriskAsterisk(); 

                }
                break;
            case 131 :
                // InternalSTAlgorithmLexer.g:1:1055: FullStopFullStop
                {
                mFullStopFullStop(); 

                }
                break;
            case 132 :
                // InternalSTAlgorithmLexer.g:1:1072: ColonColon
                {
                mColonColon(); 

                }
                break;
            case 133 :
                // InternalSTAlgorithmLexer.g:1:1083: ColonEqualsSign
                {
                mColonEqualsSign(); 

                }
                break;
            case 134 :
                // InternalSTAlgorithmLexer.g:1:1099: LessThanSignEqualsSign
                {
                mLessThanSignEqualsSign(); 

                }
                break;
            case 135 :
                // InternalSTAlgorithmLexer.g:1:1122: LessThanSignGreaterThanSign
                {
                mLessThanSignGreaterThanSign(); 

                }
                break;
            case 136 :
                // InternalSTAlgorithmLexer.g:1:1150: EqualsSignGreaterThanSign
                {
                mEqualsSignGreaterThanSign(); 

                }
                break;
            case 137 :
                // InternalSTAlgorithmLexer.g:1:1176: GreaterThanSignEqualsSign
                {
                mGreaterThanSignEqualsSign(); 

                }
                break;
            case 138 :
                // InternalSTAlgorithmLexer.g:1:1202: AT
                {
                mAT(); 

                }
                break;
            case 139 :
                // InternalSTAlgorithmLexer.g:1:1205: BY
                {
                mBY(); 

                }
                break;
            case 140 :
                // InternalSTAlgorithmLexer.g:1:1208: DO
                {
                mDO(); 

                }
                break;
            case 141 :
                // InternalSTAlgorithmLexer.g:1:1211: DT
                {
                mDT(); 

                }
                break;
            case 142 :
                // InternalSTAlgorithmLexer.g:1:1214: IF
                {
                mIF(); 

                }
                break;
            case 143 :
                // InternalSTAlgorithmLexer.g:1:1217: LD
                {
                mLD(); 

                }
                break;
            case 144 :
                // InternalSTAlgorithmLexer.g:1:1220: LT
                {
                mLT(); 

                }
                break;
            case 145 :
                // InternalSTAlgorithmLexer.g:1:1223: OF
                {
                mOF(); 

                }
                break;
            case 146 :
                // InternalSTAlgorithmLexer.g:1:1226: ON
                {
                mON(); 

                }
                break;
            case 147 :
                // InternalSTAlgorithmLexer.g:1:1229: OR
                {
                mOR(); 

                }
                break;
            case 148 :
                // InternalSTAlgorithmLexer.g:1:1232: TO
                {
                mTO(); 

                }
                break;
            case 149 :
                // InternalSTAlgorithmLexer.g:1:1235: NumberSign
                {
                mNumberSign(); 

                }
                break;
            case 150 :
                // InternalSTAlgorithmLexer.g:1:1246: Ampersand
                {
                mAmpersand(); 

                }
                break;
            case 151 :
                // InternalSTAlgorithmLexer.g:1:1256: LeftParenthesis
                {
                mLeftParenthesis(); 

                }
                break;
            case 152 :
                // InternalSTAlgorithmLexer.g:1:1272: RightParenthesis
                {
                mRightParenthesis(); 

                }
                break;
            case 153 :
                // InternalSTAlgorithmLexer.g:1:1289: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 154 :
                // InternalSTAlgorithmLexer.g:1:1298: PlusSign
                {
                mPlusSign(); 

                }
                break;
            case 155 :
                // InternalSTAlgorithmLexer.g:1:1307: Comma
                {
                mComma(); 

                }
                break;
            case 156 :
                // InternalSTAlgorithmLexer.g:1:1313: HyphenMinus
                {
                mHyphenMinus(); 

                }
                break;
            case 157 :
                // InternalSTAlgorithmLexer.g:1:1325: FullStop
                {
                mFullStop(); 

                }
                break;
            case 158 :
                // InternalSTAlgorithmLexer.g:1:1334: Solidus
                {
                mSolidus(); 

                }
                break;
            case 159 :
                // InternalSTAlgorithmLexer.g:1:1342: Colon
                {
                mColon(); 

                }
                break;
            case 160 :
                // InternalSTAlgorithmLexer.g:1:1348: Semicolon
                {
                mSemicolon(); 

                }
                break;
            case 161 :
                // InternalSTAlgorithmLexer.g:1:1358: LessThanSign
                {
                mLessThanSign(); 

                }
                break;
            case 162 :
                // InternalSTAlgorithmLexer.g:1:1371: EqualsSign
                {
                mEqualsSign(); 

                }
                break;
            case 163 :
                // InternalSTAlgorithmLexer.g:1:1382: GreaterThanSign
                {
                mGreaterThanSign(); 

                }
                break;
            case 164 :
                // InternalSTAlgorithmLexer.g:1:1398: D
                {
                mD(); 

                }
                break;
            case 165 :
                // InternalSTAlgorithmLexer.g:1:1400: T
                {
                mT(); 

                }
                break;
            case 166 :
                // InternalSTAlgorithmLexer.g:1:1402: LeftSquareBracket
                {
                mLeftSquareBracket(); 

                }
                break;
            case 167 :
                // InternalSTAlgorithmLexer.g:1:1420: RightSquareBracket
                {
                mRightSquareBracket(); 

                }
                break;
            case 168 :
                // InternalSTAlgorithmLexer.g:1:1439: LeftCurlyBracket
                {
                mLeftCurlyBracket(); 

                }
                break;
            case 169 :
                // InternalSTAlgorithmLexer.g:1:1456: RightCurlyBracket
                {
                mRightCurlyBracket(); 

                }
                break;
            case 170 :
                // InternalSTAlgorithmLexer.g:1:1474: RULE_NON_DECIMAL
                {
                mRULE_NON_DECIMAL(); 

                }
                break;
            case 171 :
                // InternalSTAlgorithmLexer.g:1:1491: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 172 :
                // InternalSTAlgorithmLexer.g:1:1500: RULE_DECIMAL
                {
                mRULE_DECIMAL(); 

                }
                break;
            case 173 :
                // InternalSTAlgorithmLexer.g:1:1513: RULE_TIME_VALUE
                {
                mRULE_TIME_VALUE(); 

                }
                break;
            case 174 :
                // InternalSTAlgorithmLexer.g:1:1529: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 175 :
                // InternalSTAlgorithmLexer.g:1:1537: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 176 :
                // InternalSTAlgorithmLexer.g:1:1549: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 177 :
                // InternalSTAlgorithmLexer.g:1:1565: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 178 :
                // InternalSTAlgorithmLexer.g:1:1581: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 179 :
                // InternalSTAlgorithmLexer.g:1:1589: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA21_eotS =
        "\1\uffff\4\65\1\111\2\65\1\124\12\65\1\170\1\65\1\61\1\u0080\1\u0082\1\u0085\1\u0087\1\u0089\2\uffff\1\u008d\4\uffff\1\u0093\5\uffff\4\u009b\1\61\1\uffff\2\61\2\uffff\3\65\1\uffff\5\65\1\u00ac\1\u00af\12\65\1\u00ba\1\u00bb\1\uffff\2\65\1\u00bf\6\65\1\u00c9\1\uffff\11\65\1\u00d8\3\65\1\u00dd\1\u00de\1\u00df\20\65\1\u00f2\1\u00f4\2\uffff\1\65\40\uffff\1\u009b\3\uffff\1\u009b\2\uffff\10\65\1\u00ff\1\65\1\u0101\1\uffff\2\65\1\uffff\12\65\2\uffff\1\65\1\u0112\1\65\1\uffff\1\u0116\7\65\1\u011e\1\uffff\1\65\1\u0120\4\65\1\u0127\6\65\1\u012f\1\uffff\4\65\3\uffff\6\65\1\u013c\13\65\3\uffff\1\u014a\2\65\1\u0158\1\65\1\u015a\3\65\1\u015e\1\uffff\1\65\1\uffff\1\65\1\u0161\2\65\1\u0164\4\65\1\u0169\1\u016a\1\u016c\1\65\1\u016e\2\65\1\uffff\3\65\1\uffff\1\u017b\1\65\1\u017d\1\u017e\1\u017f\1\u0180\1\u0181\1\uffff\1\65\1\uffff\1\65\1\u0184\1\65\1\u0186\2\65\1\uffff\7\65\1\uffff\11\65\1\u019a\1\u019b\1\65\1\uffff\1\65\1\u019e\2\65\1\u01a1\5\65\1\u01a8\1\u01a9\1\u01aa\1\uffff\15\65\1\uffff\1\u01be\1\uffff\1\65\1\u01c0\1\u01c1\1\uffff\1\u01c3\1\u01c5\1\uffff\1\u01c6\1\u01c7\1\uffff\3\65\1\u01cb\2\uffff\1\65\1\uffff\1\u01cd\1\uffff\14\65\1\uffff\1\65\5\uffff\2\65\1\uffff\1\65\1\uffff\10\65\1\u01ea\10\65\1\u01f3\1\u01f4\2\uffff\2\65\1\uffff\2\65\1\uffff\1\u01f9\1\u01fa\1\u01fb\1\u01fc\1\u01fd\1\u01fe\3\uffff\12\65\1\u0209\10\65\1\uffff\1\65\2\uffff\1\65\1\uffff\1\65\3\uffff\3\65\1\uffff\1\65\1\uffff\6\65\1\u0221\16\65\1\u0231\1\u0232\1\u0233\1\u0234\2\65\1\u0237\1\uffff\4\65\1\u023c\3\65\2\uffff\1\u0240\1\u0241\1\u0242\1\u0243\6\uffff\1\65\1\u0245\10\65\1\uffff\10\65\1\u0256\1\u0257\13\65\1\u0263\1\65\1\uffff\17\65\4\uffff\2\65\1\uffff\1\65\1\u0277\1\65\1\u0279\1\uffff\1\65\1\u027b\1\u027c\4\uffff\1\65\1\uffff\2\65\1\u0280\1\65\1\u0282\11\65\1\u028c\1\65\2\uffff\1\u028f\3\65\1\u0293\1\u0294\3\65\1\u0298\1\u0299\1\uffff\10\65\1\u02a2\6\65\1\u02a9\1\65\1\u02ab\1\65\1\uffff\1\u02ad\1\uffff\1\u02ae\2\uffff\2\65\1\u02b1\1\uffff\1\65\1\uffff\11\65\1\uffff\1\u02bc\1\65\1\uffff\3\65\2\uffff\2\65\1\u02c3\2\uffff\6\65\1\u02ca\1\65\1\uffff\3\65\1\u02cf\1\65\1\u02d1\1\uffff\1\u02d2\1\uffff\1\u02d3\2\uffff\2\65\1\uffff\2\65\1\u02d8\3\65\1\u02dc\1\65\1\u02de\1\u02df\1\uffff\6\65\1\uffff\1\u02e6\1\65\1\u02e8\1\u02e9\1\u02ea\1\u02eb\1\uffff\1\u02ec\1\65\1\u02ee\1\u02ef\1\uffff\1\u02f0\3\uffff\4\65\1\uffff\3\65\1\uffff\1\u02f8\2\uffff\6\65\1\uffff\1\65\5\uffff\1\u0300\3\uffff\1\u0302\5\65\1\u0308\1\uffff\2\65\1\u030b\2\65\1\u030e\1\u030f\1\uffff\1\65\1\uffff\2\65\1\u0313\1\u0314\1\u0315\1\uffff\2\65\1\uffff\1\u0318\1\u0319\2\uffff\2\65\1\u031c\3\uffff\1\u031d\1\u031e\2\uffff\2\65\3\uffff\3\65\1\u0324\1\u0325\2\uffff";
    static final String DFA21_eofS =
        "\u0326\uffff";
    static final String DFA21_minS =
        "\1\0\1\114\1\101\1\104\1\101\1\60\1\106\1\101\1\60\1\101\1\105\1\102\1\122\1\106\1\103\1\105\1\111\1\104\1\117\1\72\1\117\1\102\1\52\1\56\1\75\1\76\1\75\2\uffff\1\52\4\uffff\1\52\5\uffff\2\43\2\60\1\101\1\uffff\2\0\2\uffff\1\104\1\111\1\123\1\uffff\1\116\1\114\1\116\1\117\1\122\2\60\1\105\1\117\2\116\1\101\1\123\1\101\1\124\1\117\1\116\2\60\1\uffff\1\111\1\120\1\60\1\122\1\115\1\101\1\123\1\105\1\120\1\60\1\uffff\1\116\1\115\1\114\1\101\1\107\1\123\1\124\1\122\1\104\1\60\1\111\1\102\1\105\3\60\1\124\1\110\1\111\1\124\1\122\1\124\1\104\1\116\1\105\1\120\2\111\1\124\1\111\1\116\1\117\1\60\1\52\2\uffff\1\122\40\uffff\1\60\3\uffff\1\43\2\uffff\1\137\1\105\1\124\1\105\1\103\1\123\1\101\1\115\1\60\1\124\1\60\1\uffff\1\115\1\104\1\uffff\1\101\1\122\1\124\1\106\1\123\1\105\1\122\1\105\1\122\1\124\2\uffff\1\124\1\60\1\114\1\uffff\1\60\1\105\1\116\1\105\1\113\1\116\1\123\1\105\1\60\1\uffff\1\137\1\60\1\105\1\114\1\104\1\117\1\60\1\105\1\101\1\117\1\124\1\111\1\101\1\60\1\uffff\1\107\1\117\1\114\1\122\3\uffff\1\122\1\101\1\114\1\110\1\104\1\110\1\60\1\107\1\111\1\120\1\105\2\116\1\111\1\116\1\124\1\114\1\105\3\uffff\1\60\1\101\1\116\1\60\1\106\1\60\1\124\1\105\1\114\1\60\1\uffff\1\105\1\uffff\1\105\1\60\1\114\1\104\1\60\1\111\1\124\1\111\1\123\3\60\1\104\1\60\1\111\1\122\1\uffff\1\105\1\122\1\101\1\uffff\1\60\1\123\5\60\1\uffff\1\122\1\uffff\1\123\1\60\1\137\1\60\1\125\1\124\1\uffff\1\101\1\111\3\122\1\117\1\131\1\uffff\1\105\2\122\1\101\1\111\1\114\1\111\1\122\1\105\2\60\1\117\1\uffff\1\114\1\60\1\116\1\103\1\60\1\122\2\124\1\114\1\107\3\60\1\uffff\1\117\1\101\1\122\1\103\1\106\1\101\1\105\1\122\1\105\1\124\1\110\1\101\1\104\1\uffff\1\60\1\uffff\1\111\2\60\1\uffff\2\60\1\uffff\2\60\1\uffff\1\107\1\101\1\116\1\60\2\uffff\1\101\1\uffff\1\60\1\uffff\2\101\1\115\1\124\1\130\1\103\1\117\1\114\1\116\1\125\1\105\1\117\1\uffff\1\111\5\uffff\1\105\1\120\1\uffff\1\117\1\uffff\1\122\1\117\1\124\2\116\1\111\1\101\1\116\1\60\1\103\1\101\1\111\1\124\1\103\1\111\1\101\1\116\2\60\2\uffff\1\104\1\105\1\uffff\1\107\1\124\1\uffff\6\60\3\uffff\1\116\1\122\1\116\1\101\1\123\1\101\1\120\1\107\2\124\1\60\1\115\1\120\1\117\1\124\1\105\1\111\1\122\1\123\1\uffff\1\117\2\uffff\1\101\1\uffff\1\117\3\uffff\1\125\1\116\1\125\1\uffff\1\116\1\uffff\1\114\3\101\1\114\1\105\1\60\1\124\1\103\1\116\1\117\1\120\1\124\1\115\1\106\2\124\1\101\1\122\1\116\1\103\4\60\1\124\1\103\1\60\1\uffff\1\124\1\115\1\124\1\105\1\60\1\104\1\120\1\107\2\uffff\4\60\6\uffff\1\103\1\60\1\106\1\123\1\105\1\116\1\105\1\117\1\111\1\105\1\uffff\1\105\1\117\1\105\1\107\1\110\1\125\1\120\1\114\2\60\2\116\1\106\1\122\1\124\1\105\1\104\1\137\1\103\2\114\1\60\1\116\1\uffff\2\105\1\106\1\102\1\117\1\125\2\120\1\137\1\111\1\101\1\103\1\111\1\114\1\105\4\uffff\1\110\1\124\1\uffff\1\105\1\60\1\131\1\60\1\uffff\1\105\2\60\4\uffff\1\124\1\uffff\1\111\1\123\1\60\1\123\1\60\1\122\1\117\1\122\1\123\1\125\1\101\1\122\1\117\1\103\1\60\1\105\2\uffff\1\60\1\104\1\137\1\101\2\60\1\137\1\123\1\105\2\60\1\uffff\1\124\1\122\1\123\1\111\1\101\1\125\1\124\1\125\1\60\1\104\1\117\1\111\1\105\1\124\1\131\1\60\1\115\1\60\1\104\1\uffff\1\60\1\uffff\1\60\2\uffff\1\111\1\107\1\60\1\uffff\1\111\1\uffff\1\111\1\116\1\106\1\120\1\122\1\124\1\101\1\104\1\124\1\uffff\1\60\1\102\1\uffff\1\137\1\104\1\124\2\uffff\2\124\1\60\2\uffff\1\123\1\116\1\123\1\107\1\114\1\124\1\60\1\124\1\uffff\1\101\2\116\1\60\1\105\1\60\1\uffff\1\60\1\uffff\1\60\2\uffff\1\117\1\125\1\uffff\2\124\1\60\2\101\1\103\1\60\1\115\2\60\1\uffff\1\114\1\124\1\101\2\111\1\105\1\uffff\1\60\1\101\4\60\1\uffff\1\60\1\131\2\60\1\uffff\1\60\3\uffff\1\116\1\122\1\111\1\110\1\uffff\2\103\1\105\1\uffff\1\60\2\uffff\1\117\1\111\1\131\1\117\1\115\1\120\1\uffff\1\114\5\uffff\1\60\3\uffff\1\60\1\101\1\117\1\115\2\105\1\60\1\uffff\1\103\1\115\1\60\1\116\1\105\2\60\1\uffff\1\102\1\uffff\1\124\1\116\3\60\1\uffff\1\113\1\105\1\uffff\2\60\2\uffff\1\114\1\111\1\60\3\uffff\2\60\2\uffff\2\117\3\uffff\1\103\1\116\1\113\2\60\2\uffff";
    static final String DFA21_maxS =
        "\1\uffff\1\170\1\165\1\167\1\157\1\172\1\156\1\141\1\172\1\165\1\145\1\164\1\165\1\166\1\163\1\157\1\165\1\163\1\171\1\75\1\157\1\170\1\52\1\56\2\76\1\75\2\uffff\1\52\4\uffff\1\57\5\uffff\4\165\1\172\1\uffff\2\uffff\2\uffff\1\144\1\164\1\163\1\uffff\1\156\1\154\1\156\1\157\1\162\2\172\1\145\1\157\2\156\1\141\1\163\1\141\1\164\1\157\1\156\2\172\1\uffff\1\164\1\160\1\172\1\162\1\155\1\165\1\163\1\151\1\160\1\172\1\uffff\1\164\1\155\1\154\1\164\1\147\1\163\1\164\1\162\1\144\1\172\1\157\1\142\1\145\3\172\1\164\1\150\1\151\1\164\1\162\1\164\1\144\1\156\1\162\1\160\2\151\1\164\1\151\1\156\1\157\1\172\1\52\2\uffff\1\162\40\uffff\1\165\3\uffff\1\165\2\uffff\1\137\1\145\1\164\1\151\1\143\1\163\1\141\1\155\1\172\1\164\1\172\1\uffff\1\155\1\144\1\uffff\1\141\1\162\2\164\1\163\1\145\1\162\1\145\1\162\1\164\2\uffff\1\164\1\172\1\157\1\uffff\1\172\1\145\1\156\1\145\1\153\1\156\1\163\1\145\1\172\1\uffff\1\137\1\172\1\145\2\154\1\157\1\172\1\145\1\165\1\157\1\164\1\151\1\141\1\172\1\uffff\1\164\1\166\1\154\1\162\3\uffff\1\162\1\141\1\154\1\150\1\144\1\150\1\172\1\164\1\165\1\160\1\145\2\156\1\151\1\156\1\164\1\154\1\145\3\uffff\1\172\1\167\1\156\1\172\1\146\1\172\1\164\1\145\1\154\1\172\1\uffff\1\145\1\uffff\1\145\1\172\1\154\1\144\1\172\1\151\1\164\1\151\1\163\3\172\1\144\1\172\1\151\1\162\1\uffff\1\145\1\162\1\164\1\uffff\1\172\1\163\5\172\1\uffff\1\162\1\uffff\1\163\1\172\1\137\1\172\1\165\1\164\1\uffff\1\141\1\151\3\162\1\157\1\171\1\uffff\1\145\2\162\1\141\1\151\1\162\1\151\1\162\1\145\2\172\1\157\1\uffff\1\154\1\172\1\156\1\143\1\172\1\162\2\164\1\154\1\164\3\172\1\uffff\1\165\1\157\1\171\1\154\1\156\1\141\1\145\1\162\1\145\1\164\1\150\1\141\1\144\1\uffff\1\172\1\uffff\1\151\2\172\1\uffff\2\172\1\uffff\2\172\1\uffff\1\147\1\141\1\156\1\172\2\uffff\1\141\1\uffff\1\172\1\uffff\1\141\1\166\1\155\1\164\1\170\1\143\1\157\1\154\1\156\1\165\1\145\1\157\1\uffff\1\151\5\uffff\1\145\1\160\1\uffff\1\167\1\uffff\1\162\1\157\1\164\2\156\1\151\1\141\1\156\1\172\1\143\1\141\1\151\1\164\1\143\1\151\1\141\1\156\2\172\2\uffff\1\144\1\145\1\uffff\1\147\1\164\1\uffff\6\172\3\uffff\1\156\1\162\1\156\1\141\1\163\1\141\1\160\1\147\2\164\1\172\1\155\1\163\1\157\1\164\1\162\1\151\1\162\1\163\1\uffff\1\157\2\uffff\1\141\1\uffff\1\157\3\uffff\1\165\1\156\1\165\1\uffff\1\156\1\uffff\1\154\3\141\1\154\1\145\1\172\1\164\1\143\1\156\1\157\1\160\1\164\1\155\1\146\2\164\1\141\1\162\1\156\1\143\4\172\1\164\1\143\1\172\1\uffff\1\164\1\155\1\164\1\145\1\172\1\144\1\160\1\147\2\uffff\4\172\6\uffff\1\143\1\172\1\146\1\163\1\145\1\156\1\145\1\157\1\151\1\145\1\uffff\1\145\1\157\1\145\1\147\1\150\1\165\1\160\1\154\2\172\2\156\1\146\1\162\1\164\1\145\1\144\1\137\1\143\2\154\1\172\1\156\1\uffff\2\145\1\146\1\142\1\157\1\165\2\160\1\137\1\151\1\141\1\143\1\151\1\154\1\145\4\uffff\1\150\1\164\1\uffff\1\145\1\172\1\171\1\172\1\uffff\1\145\2\172\4\uffff\1\164\1\uffff\1\151\1\163\1\172\1\163\1\172\1\162\1\157\1\162\1\163\1\165\1\141\1\162\1\157\1\143\1\172\1\145\2\uffff\1\172\1\144\1\137\1\141\2\172\1\137\1\163\1\145\2\172\1\uffff\1\164\1\162\1\163\1\151\1\141\1\165\1\164\1\165\1\172\1\144\1\157\1\151\1\145\1\164\1\171\1\172\1\155\1\172\1\144\1\uffff\1\172\1\uffff\1\172\2\uffff\1\151\1\147\1\172\1\uffff\1\151\1\uffff\1\151\1\156\1\146\1\160\1\162\1\164\1\141\1\144\1\164\1\uffff\1\172\1\142\1\uffff\1\137\1\144\1\164\2\uffff\2\164\1\172\2\uffff\1\163\1\156\1\163\1\147\1\154\1\164\1\172\1\164\1\uffff\1\141\2\156\1\172\1\145\1\172\1\uffff\1\172\1\uffff\1\172\2\uffff\1\157\1\165\1\uffff\2\164\1\172\2\141\1\143\1\172\1\155\2\172\1\uffff\1\154\1\164\1\141\2\151\1\145\1\uffff\1\172\1\141\4\172\1\uffff\1\172\1\171\2\172\1\uffff\1\172\3\uffff\1\156\1\162\1\151\1\150\1\uffff\2\143\1\145\1\uffff\1\172\2\uffff\1\157\1\151\1\171\1\157\1\155\1\160\1\uffff\1\154\5\uffff\1\172\3\uffff\1\172\1\141\1\157\1\155\2\145\1\172\1\uffff\1\143\1\155\1\172\1\156\1\145\2\172\1\uffff\1\142\1\uffff\1\164\1\156\3\172\1\uffff\1\153\1\145\1\uffff\2\172\2\uffff\1\154\1\151\1\172\3\uffff\2\172\2\uffff\2\157\3\uffff\1\143\1\156\1\153\2\172\2\uffff";
    static final String DFA21_acceptS =
        "\33\uffff\1\u0095\1\u0096\1\uffff\1\u0098\1\u009a\1\u009b\1\u009c\1\uffff\1\u00a0\1\u00a6\1\u00a7\1\u00a8\1\u00a9\5\uffff\1\u00ae\2\uffff\1\u00b2\1\u00b3\3\uffff\1\u00ae\23\uffff\1\u00a4\12\uffff\1\u00a5\42\uffff\1\u0085\1\u009f\1\uffff\1\175\1\176\1\177\1\u0080\1\u0081\1\u0082\1\u0099\1\u0083\1\u009d\1\u0086\1\u0087\1\u00a1\1\u0088\1\u00a2\1\u0089\1\u00a3\1\u0095\1\u0096\1\u00b0\1\u0097\1\u0098\1\u009a\1\u009b\1\u009c\1\u00b1\1\u009e\1\u00a0\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\u00aa\1\uffff\1\u00ab\1\u00ac\1\u00ad\1\uffff\1\u00af\1\u00b2\13\uffff\1\u008f\2\uffff\1\u0090\12\uffff\1\u008c\1\u008d\3\uffff\1\u008e\11\uffff\1\u0094\16\uffff\1\u008a\4\uffff\1\u0091\1\u0092\1\u0093\22\uffff\1\u008b\1\162\1\u0084\12\uffff\1\164\1\uffff\1\166\20\uffff\1\165\3\uffff\1\173\7\uffff\1\172\1\uffff\1\170\6\uffff\1\171\7\uffff\1\163\14\uffff\1\167\15\uffff\1\174\15\uffff\1\141\1\uffff\1\140\3\uffff\1\142\2\uffff\1\144\2\uffff\1\143\4\uffff\1\134\1\135\1\uffff\1\136\1\uffff\1\137\14\uffff\1\154\1\uffff\1\155\1\151\1\152\1\153\1\156\2\uffff\1\145\1\uffff\1\146\23\uffff\1\160\1\161\2\uffff\1\147\2\uffff\1\150\6\uffff\1\157\1\132\1\133\23\uffff\1\113\1\uffff\1\114\1\115\1\uffff\1\116\1\uffff\1\120\1\117\1\121\3\uffff\1\111\1\uffff\1\112\34\uffff\1\110\10\uffff\1\130\1\131\4\uffff\1\122\1\123\1\124\1\125\1\126\1\127\12\uffff\1\75\27\uffff\1\76\17\uffff\1\101\1\102\1\103\1\104\2\uffff\1\74\4\uffff\1\100\3\uffff\1\77\1\105\1\106\1\107\1\uffff\1\64\20\uffff\1\65\1\66\13\uffff\1\67\23\uffff\1\72\1\uffff\1\71\1\uffff\1\70\1\73\3\uffff\1\52\1\uffff\1\54\11\uffff\1\53\2\uffff\1\55\3\uffff\1\50\1\51\3\uffff\1\56\1\57\10\uffff\1\63\6\uffff\1\62\1\uffff\1\47\1\uffff\1\61\1\60\2\uffff\1\40\12\uffff\1\41\6\uffff\1\42\6\uffff\1\46\4\uffff\1\43\1\uffff\1\45\1\37\1\44\4\uffff\1\22\3\uffff\1\24\1\uffff\1\23\1\25\6\uffff\1\26\1\uffff\1\32\1\33\1\34\1\35\1\36\1\uffff\1\31\1\27\1\30\7\uffff\1\20\7\uffff\1\21\1\uffff\1\13\5\uffff\1\14\2\uffff\1\16\2\uffff\1\15\1\17\3\uffff\1\10\1\11\1\12\2\uffff\1\6\1\7\2\uffff\1\3\1\4\1\5\5\uffff\1\2\1\1";
    static final String DFA21_specialS =
        "\1\0\55\uffff\1\1\1\2\u02f6\uffff}>";
    static final String[] DFA21_transitionS = {
            "\11\61\2\60\2\61\1\60\22\61\1\60\1\61\1\56\1\33\1\61\1\25\1\34\1\57\1\35\1\36\1\26\1\37\1\40\1\41\1\27\1\42\1\53\1\52\1\50\5\53\1\51\1\53\1\23\1\43\1\30\1\31\1\32\2\61\1\13\1\22\1\4\1\5\1\1\1\2\2\55\1\6\2\55\1\3\1\17\1\11\1\15\1\14\1\55\1\12\1\20\1\10\1\21\1\7\1\16\1\24\2\55\1\44\1\61\1\45\1\54\1\55\1\61\1\13\1\22\1\4\1\5\1\1\1\2\2\55\1\6\2\55\1\3\1\17\1\11\1\15\1\14\1\55\1\12\1\20\1\10\1\21\1\7\1\16\1\24\2\55\1\46\1\61\1\47\uff82\61",
            "\1\64\1\uffff\1\62\11\uffff\1\63\23\uffff\1\64\1\uffff\1\62\11\uffff\1\63",
            "\1\67\7\uffff\1\70\5\uffff\1\72\2\uffff\1\71\2\uffff\1\66\13\uffff\1\67\7\uffff\1\70\5\uffff\1\72\2\uffff\1\71\2\uffff\1\66",
            "\1\73\4\uffff\1\77\10\uffff\1\75\1\uffff\1\74\2\uffff\1\76\14\uffff\1\73\4\uffff\1\77\10\uffff\1\75\1\uffff\1\74\2\uffff\1\76",
            "\1\102\6\uffff\1\103\3\uffff\1\101\2\uffff\1\100\21\uffff\1\102\6\uffff\1\103\3\uffff\1\101\2\uffff\1\100",
            "\12\65\7\uffff\1\104\7\65\1\106\5\65\1\107\4\65\1\110\2\65\1\105\3\65\4\uffff\1\65\1\uffff\1\104\7\65\1\106\5\65\1\107\4\65\1\110\2\65\1\105\3\65",
            "\1\114\6\uffff\1\113\1\112\27\uffff\1\114\6\uffff\1\113\1\112",
            "\1\115\37\uffff\1\115",
            "\12\65\7\uffff\1\120\6\65\1\121\1\116\5\65\1\123\2\65\1\117\6\65\1\122\1\65\4\uffff\1\65\1\uffff\1\120\6\65\1\121\1\116\5\65\1\123\2\65\1\117\6\65\1\122\1\65",
            "\1\126\15\uffff\1\125\5\uffff\1\127\13\uffff\1\126\15\uffff\1\125\5\uffff\1\127",
            "\1\130\37\uffff\1\130",
            "\1\132\1\133\10\uffff\1\131\1\uffff\1\135\3\uffff\1\134\1\uffff\1\136\15\uffff\1\132\1\133\10\uffff\1\131\1\uffff\1\135\3\uffff\1\134\1\uffff\1\136",
            "\1\137\2\uffff\1\140\34\uffff\1\137\2\uffff\1\140",
            "\1\142\7\uffff\1\143\3\uffff\1\144\3\uffff\1\141\17\uffff\1\142\7\uffff\1\143\3\uffff\1\144\3\uffff\1\141",
            "\1\146\4\uffff\1\147\1\150\5\uffff\1\151\3\uffff\1\145\17\uffff\1\146\4\uffff\1\147\1\150\5\uffff\1\151\3\uffff\1\145",
            "\1\152\11\uffff\1\153\25\uffff\1\152\11\uffff\1\153",
            "\1\154\12\uffff\1\155\1\156\23\uffff\1\154\12\uffff\1\155\1\156",
            "\1\157\4\uffff\1\163\2\uffff\1\160\1\uffff\1\161\4\uffff\1\162\20\uffff\1\157\4\uffff\1\163\2\uffff\1\160\1\uffff\1\161\4\uffff\1\162",
            "\1\164\11\uffff\1\165\25\uffff\1\164\11\uffff\1\165",
            "\1\166\2\uffff\1\167",
            "\1\171\37\uffff\1\171",
            "\1\172\1\uffff\1\173\7\uffff\1\174\12\uffff\1\175\1\176\11\uffff\1\172\1\uffff\1\173\7\uffff\1\174\12\uffff\1\175\1\176",
            "\1\177",
            "\1\u0081",
            "\1\u0083\1\u0084",
            "\1\u0086",
            "\1\u0088",
            "",
            "",
            "\1\u008c",
            "",
            "",
            "",
            "",
            "\1\u008c\4\uffff\1\u0092",
            "",
            "",
            "",
            "",
            "",
            "\1\u0099\14\uffff\12\u009a\12\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d\11\uffff\1\u009a\4\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d",
            "\1\u0099\14\uffff\12\u009a\12\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d\11\uffff\1\u009a\4\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d",
            "\6\u009a\1\u009e\3\u009a\12\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d\11\uffff\1\u009a\4\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d",
            "\12\u009a\12\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d\11\uffff\1\u009a\4\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d",
            "\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\0\u009f",
            "\0\u009f",
            "",
            "",
            "\1\u00a1\37\uffff\1\u00a1",
            "\1\u00a3\12\uffff\1\u00a2\24\uffff\1\u00a3\12\uffff\1\u00a2",
            "\1\u00a4\37\uffff\1\u00a4",
            "",
            "\1\u00a5\37\uffff\1\u00a5",
            "\1\u00a6\37\uffff\1\u00a6",
            "\1\u00a7\37\uffff\1\u00a7",
            "\1\u00a8\37\uffff\1\u00a8",
            "\1\u00a9\37\uffff\1\u00a9",
            "\12\65\7\uffff\1\u00aa\22\65\1\u00ab\6\65\4\uffff\1\65\1\uffff\1\u00aa\22\65\1\u00ab\6\65",
            "\12\65\7\uffff\10\65\1\u00ad\5\65\1\u00ae\13\65\4\uffff\1\65\1\uffff\10\65\1\u00ad\5\65\1\u00ae\13\65",
            "\1\u00b0\37\uffff\1\u00b0",
            "\1\u00b1\37\uffff\1\u00b1",
            "\1\u00b2\37\uffff\1\u00b2",
            "\1\u00b3\37\uffff\1\u00b3",
            "\1\u00b4\37\uffff\1\u00b4",
            "\1\u00b5\37\uffff\1\u00b5",
            "\1\u00b6\37\uffff\1\u00b6",
            "\1\u00b7\37\uffff\1\u00b7",
            "\1\u00b8\37\uffff\1\u00b8",
            "\1\u00b9\37\uffff\1\u00b9",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u00bc\12\uffff\1\u00bd\24\uffff\1\u00bc\12\uffff\1\u00bd",
            "\1\u00be\37\uffff\1\u00be",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00c0\37\uffff\1\u00c0",
            "\1\u00c1\37\uffff\1\u00c1",
            "\1\u00c2\23\uffff\1\u00c3\13\uffff\1\u00c2\23\uffff\1\u00c3",
            "\1\u00c4\37\uffff\1\u00c4",
            "\1\u00c5\3\uffff\1\u00c6\33\uffff\1\u00c5\3\uffff\1\u00c6",
            "\1\u00c7\37\uffff\1\u00c7",
            "\12\65\7\uffff\3\65\1\u00c8\26\65\4\uffff\1\65\1\uffff\3\65\1\u00c8\26\65",
            "",
            "\1\u00ca\5\uffff\1\u00cb\31\uffff\1\u00ca\5\uffff\1\u00cb",
            "\1\u00cc\37\uffff\1\u00cc",
            "\1\u00cd\37\uffff\1\u00cd",
            "\1\u00ce\4\uffff\1\u00d0\11\uffff\1\u00d1\2\uffff\1\u00cf\1\u00d2\14\uffff\1\u00ce\4\uffff\1\u00d0\11\uffff\1\u00d1\2\uffff\1\u00cf\1\u00d2",
            "\1\u00d3\37\uffff\1\u00d3",
            "\1\u00d4\37\uffff\1\u00d4",
            "\1\u00d5\37\uffff\1\u00d5",
            "\1\u00d6\37\uffff\1\u00d6",
            "\1\u00d7\37\uffff\1\u00d7",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00da\5\uffff\1\u00d9\31\uffff\1\u00da\5\uffff\1\u00d9",
            "\1\u00db\37\uffff\1\u00db",
            "\1\u00dc\37\uffff\1\u00dc",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00e0\37\uffff\1\u00e0",
            "\1\u00e1\37\uffff\1\u00e1",
            "\1\u00e2\37\uffff\1\u00e2",
            "\1\u00e3\37\uffff\1\u00e3",
            "\1\u00e4\37\uffff\1\u00e4",
            "\1\u00e5\37\uffff\1\u00e5",
            "\1\u00e6\37\uffff\1\u00e6",
            "\1\u00e7\37\uffff\1\u00e7",
            "\1\u00e9\14\uffff\1\u00e8\22\uffff\1\u00e9\14\uffff\1\u00e8",
            "\1\u00ea\37\uffff\1\u00ea",
            "\1\u00eb\37\uffff\1\u00eb",
            "\1\u00ec\37\uffff\1\u00ec",
            "\1\u00ed\37\uffff\1\u00ed",
            "\1\u00ee\37\uffff\1\u00ee",
            "\1\u00ef\37\uffff\1\u00ef",
            "\1\u00f0\37\uffff\1\u00f0",
            "\12\65\7\uffff\23\65\1\u00f1\6\65\4\uffff\1\65\1\uffff\23\65\1\u00f1\6\65",
            "\1\u00f3",
            "",
            "",
            "\1\u00f5\37\uffff\1\u00f5",
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
            "\12\u009a\12\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d\11\uffff\1\u009a\4\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d",
            "",
            "",
            "",
            "\1\u0099\14\uffff\12\u009a\12\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d\11\uffff\1\u009a\4\uffff\1\u009d\1\u009c\2\uffff\1\u009d\4\uffff\2\u009d\4\uffff\1\u009d\1\uffff\1\u009d",
            "",
            "",
            "\1\u00f6",
            "\1\u00f7\37\uffff\1\u00f7",
            "\1\u00f8\37\uffff\1\u00f8",
            "\1\u00fa\3\uffff\1\u00f9\33\uffff\1\u00fa\3\uffff\1\u00f9",
            "\1\u00fb\37\uffff\1\u00fb",
            "\1\u00fc\37\uffff\1\u00fc",
            "\1\u00fd\37\uffff\1\u00fd",
            "\1\u00fe\37\uffff\1\u00fe",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0100\37\uffff\1\u0100",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0102\37\uffff\1\u0102",
            "\1\u0103\37\uffff\1\u0103",
            "",
            "\1\u0104\37\uffff\1\u0104",
            "\1\u0105\37\uffff\1\u0105",
            "\1\u0106\37\uffff\1\u0106",
            "\1\u0107\14\uffff\1\u0108\1\u0109\21\uffff\1\u0107\14\uffff\1\u0108\1\u0109",
            "\1\u010a\37\uffff\1\u010a",
            "\1\u010b\37\uffff\1\u010b",
            "\1\u010c\37\uffff\1\u010c",
            "\1\u010d\37\uffff\1\u010d",
            "\1\u010e\37\uffff\1\u010e",
            "\1\u010f\37\uffff\1\u010f",
            "",
            "",
            "\1\u0110\37\uffff\1\u0110",
            "\12\65\7\uffff\4\65\1\u0111\25\65\4\uffff\1\65\1\uffff\4\65\1\u0111\25\65",
            "\1\u0113\2\uffff\1\u0114\34\uffff\1\u0113\2\uffff\1\u0114",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\u0115\1\uffff\32\65",
            "\1\u0117\37\uffff\1\u0117",
            "\1\u0118\37\uffff\1\u0118",
            "\1\u0119\37\uffff\1\u0119",
            "\1\u011a\37\uffff\1\u011a",
            "\1\u011b\37\uffff\1\u011b",
            "\1\u011c\37\uffff\1\u011c",
            "\1\u011d\37\uffff\1\u011d",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u011f",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0121\37\uffff\1\u0121",
            "\1\u0122\37\uffff\1\u0122",
            "\1\u0123\7\uffff\1\u0124\27\uffff\1\u0123\7\uffff\1\u0124",
            "\1\u0125\37\uffff\1\u0125",
            "\12\65\7\uffff\32\65\4\uffff\1\u0126\1\uffff\32\65",
            "\1\u0128\37\uffff\1\u0128",
            "\1\u0129\23\uffff\1\u012a\13\uffff\1\u0129\23\uffff\1\u012a",
            "\1\u012b\37\uffff\1\u012b",
            "\1\u012c\37\uffff\1\u012c",
            "\1\u012d\37\uffff\1\u012d",
            "\1\u012e\37\uffff\1\u012e",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0131\14\uffff\1\u0130\22\uffff\1\u0131\14\uffff\1\u0130",
            "\1\u0132\6\uffff\1\u0133\30\uffff\1\u0132\6\uffff\1\u0133",
            "\1\u0134\37\uffff\1\u0134",
            "\1\u0135\37\uffff\1\u0135",
            "",
            "",
            "",
            "\1\u0136\37\uffff\1\u0136",
            "\1\u0137\37\uffff\1\u0137",
            "\1\u0138\37\uffff\1\u0138",
            "\1\u0139\37\uffff\1\u0139",
            "\1\u013a\37\uffff\1\u013a",
            "\1\u013b\37\uffff\1\u013b",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u013d\14\uffff\1\u013e\22\uffff\1\u013d\14\uffff\1\u013e",
            "\1\u013f\13\uffff\1\u0140\23\uffff\1\u013f\13\uffff\1\u0140",
            "\1\u0141\37\uffff\1\u0141",
            "\1\u0142\37\uffff\1\u0142",
            "\1\u0143\37\uffff\1\u0143",
            "\1\u0144\37\uffff\1\u0144",
            "\1\u0145\37\uffff\1\u0145",
            "\1\u0146\37\uffff\1\u0146",
            "\1\u0147\37\uffff\1\u0147",
            "\1\u0148\37\uffff\1\u0148",
            "\1\u0149\37\uffff\1\u0149",
            "",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u014e\1\uffff\1\u014c\2\uffff\1\u014b\2\uffff\1\u014f\3\uffff\1\u0153\1\u0150\1\uffff\1\u0152\1\uffff\1\u0151\1\u0154\1\u014d\1\uffff\1\u0156\1\u0155\11\uffff\1\u014e\1\uffff\1\u014c\2\uffff\1\u014b\2\uffff\1\u014f\3\uffff\1\u0153\1\u0150\1\uffff\1\u0152\1\uffff\1\u0151\1\u0154\1\u014d\1\uffff\1\u0156\1\u0155",
            "\1\u0157\37\uffff\1\u0157",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0159\37\uffff\1\u0159",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u015b\37\uffff\1\u015b",
            "\1\u015c\37\uffff\1\u015c",
            "\1\u015d\37\uffff\1\u015d",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u015f\37\uffff\1\u015f",
            "",
            "\1\u0160\37\uffff\1\u0160",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0162\37\uffff\1\u0162",
            "\1\u0163\37\uffff\1\u0163",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0165\37\uffff\1\u0165",
            "\1\u0166\37\uffff\1\u0166",
            "\1\u0167\37\uffff\1\u0167",
            "\1\u0168\37\uffff\1\u0168",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\u016b\1\uffff\32\65",
            "\1\u016d\37\uffff\1\u016d",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u016f\37\uffff\1\u016f",
            "\1\u0170\37\uffff\1\u0170",
            "",
            "\1\u0171\37\uffff\1\u0171",
            "\1\u0172\37\uffff\1\u0172",
            "\1\u0174\1\uffff\1\u0175\1\uffff\1\u0173\1\uffff\1\u0176\1\uffff\1\u0177\5\uffff\1\u0178\4\uffff\1\u0179\14\uffff\1\u0174\1\uffff\1\u0175\1\uffff\1\u0173\1\uffff\1\u0176\1\uffff\1\u0177\5\uffff\1\u0178\4\uffff\1\u0179",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\u017a\1\uffff\32\65",
            "\1\u017c\37\uffff\1\u017c",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0182\37\uffff\1\u0182",
            "",
            "\1\u0183\37\uffff\1\u0183",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0185",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0187\37\uffff\1\u0187",
            "\1\u0188\37\uffff\1\u0188",
            "",
            "\1\u0189\37\uffff\1\u0189",
            "\1\u018a\37\uffff\1\u018a",
            "\1\u018b\37\uffff\1\u018b",
            "\1\u018c\37\uffff\1\u018c",
            "\1\u018d\37\uffff\1\u018d",
            "\1\u018e\37\uffff\1\u018e",
            "\1\u018f\37\uffff\1\u018f",
            "",
            "\1\u0190\37\uffff\1\u0190",
            "\1\u0191\37\uffff\1\u0191",
            "\1\u0192\37\uffff\1\u0192",
            "\1\u0193\37\uffff\1\u0193",
            "\1\u0194\37\uffff\1\u0194",
            "\1\u0196\5\uffff\1\u0195\31\uffff\1\u0196\5\uffff\1\u0195",
            "\1\u0197\37\uffff\1\u0197",
            "\1\u0198\37\uffff\1\u0198",
            "\1\u0199\37\uffff\1\u0199",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u019c\37\uffff\1\u019c",
            "",
            "\1\u019d\37\uffff\1\u019d",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u019f\37\uffff\1\u019f",
            "\1\u01a0\37\uffff\1\u01a0",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01a2\37\uffff\1\u01a2",
            "\1\u01a3\37\uffff\1\u01a3",
            "\1\u01a4\37\uffff\1\u01a4",
            "\1\u01a5\37\uffff\1\u01a5",
            "\1\u01a6\14\uffff\1\u01a7\22\uffff\1\u01a6\14\uffff\1\u01a7",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01ac\5\uffff\1\u01ab\31\uffff\1\u01ac\5\uffff\1\u01ab",
            "\1\u01af\12\uffff\1\u01ae\2\uffff\1\u01ad\21\uffff\1\u01af\12\uffff\1\u01ae\2\uffff\1\u01ad",
            "\1\u01b0\6\uffff\1\u01b1\30\uffff\1\u01b0\6\uffff\1\u01b1",
            "\1\u01b3\10\uffff\1\u01b2\26\uffff\1\u01b3\10\uffff\1\u01b2",
            "\1\u01b5\7\uffff\1\u01b4\27\uffff\1\u01b5\7\uffff\1\u01b4",
            "\1\u01b6\37\uffff\1\u01b6",
            "\1\u01b7\37\uffff\1\u01b7",
            "\1\u01b8\37\uffff\1\u01b8",
            "\1\u01b9\37\uffff\1\u01b9",
            "\1\u01ba\37\uffff\1\u01ba",
            "\1\u01bb\37\uffff\1\u01bb",
            "\1\u01bc\37\uffff\1\u01bc",
            "\1\u01bd\37\uffff\1\u01bd",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01bf\37\uffff\1\u01bf",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\u01c2\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\u01c4\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01c8\37\uffff\1\u01c8",
            "\1\u01c9\37\uffff\1\u01c9",
            "\1\u01ca\37\uffff\1\u01ca",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u01cc\37\uffff\1\u01cc",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u01ce\37\uffff\1\u01ce",
            "\1\u01d2\4\uffff\1\u01cf\7\uffff\1\u01d0\7\uffff\1\u01d1\12\uffff\1\u01d2\4\uffff\1\u01cf\7\uffff\1\u01d0\7\uffff\1\u01d1",
            "\1\u01d3\37\uffff\1\u01d3",
            "\1\u01d4\37\uffff\1\u01d4",
            "\1\u01d5\37\uffff\1\u01d5",
            "\1\u01d6\37\uffff\1\u01d6",
            "\1\u01d7\37\uffff\1\u01d7",
            "\1\u01d8\37\uffff\1\u01d8",
            "\1\u01d9\37\uffff\1\u01d9",
            "\1\u01da\37\uffff\1\u01da",
            "\1\u01db\37\uffff\1\u01db",
            "\1\u01dc\37\uffff\1\u01dc",
            "",
            "\1\u01dd\37\uffff\1\u01dd",
            "",
            "",
            "",
            "",
            "",
            "\1\u01de\37\uffff\1\u01de",
            "\1\u01df\37\uffff\1\u01df",
            "",
            "\1\u01e1\7\uffff\1\u01e0\27\uffff\1\u01e1\7\uffff\1\u01e0",
            "",
            "\1\u01e2\37\uffff\1\u01e2",
            "\1\u01e3\37\uffff\1\u01e3",
            "\1\u01e4\37\uffff\1\u01e4",
            "\1\u01e5\37\uffff\1\u01e5",
            "\1\u01e6\37\uffff\1\u01e6",
            "\1\u01e7\37\uffff\1\u01e7",
            "\1\u01e8\37\uffff\1\u01e8",
            "\1\u01e9\37\uffff\1\u01e9",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u01eb\37\uffff\1\u01eb",
            "\1\u01ec\37\uffff\1\u01ec",
            "\1\u01ed\37\uffff\1\u01ed",
            "\1\u01ee\37\uffff\1\u01ee",
            "\1\u01ef\37\uffff\1\u01ef",
            "\1\u01f0\37\uffff\1\u01f0",
            "\1\u01f1\37\uffff\1\u01f1",
            "\1\u01f2\37\uffff\1\u01f2",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u01f5\37\uffff\1\u01f5",
            "\1\u01f6\37\uffff\1\u01f6",
            "",
            "\1\u01f7\37\uffff\1\u01f7",
            "\1\u01f8\37\uffff\1\u01f8",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "\1\u01ff\37\uffff\1\u01ff",
            "\1\u0200\37\uffff\1\u0200",
            "\1\u0201\37\uffff\1\u0201",
            "\1\u0202\37\uffff\1\u0202",
            "\1\u0203\37\uffff\1\u0203",
            "\1\u0204\37\uffff\1\u0204",
            "\1\u0205\37\uffff\1\u0205",
            "\1\u0206\37\uffff\1\u0206",
            "\1\u0207\37\uffff\1\u0207",
            "\1\u0208\37\uffff\1\u0208",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u020a\37\uffff\1\u020a",
            "\1\u020c\2\uffff\1\u020b\34\uffff\1\u020c\2\uffff\1\u020b",
            "\1\u020d\37\uffff\1\u020d",
            "\1\u020e\37\uffff\1\u020e",
            "\1\u0210\14\uffff\1\u020f\22\uffff\1\u0210\14\uffff\1\u020f",
            "\1\u0211\37\uffff\1\u0211",
            "\1\u0212\37\uffff\1\u0212",
            "\1\u0213\37\uffff\1\u0213",
            "",
            "\1\u0214\37\uffff\1\u0214",
            "",
            "",
            "\1\u0215\37\uffff\1\u0215",
            "",
            "\1\u0216\37\uffff\1\u0216",
            "",
            "",
            "",
            "\1\u0217\37\uffff\1\u0217",
            "\1\u0218\37\uffff\1\u0218",
            "\1\u0219\37\uffff\1\u0219",
            "",
            "\1\u021a\37\uffff\1\u021a",
            "",
            "\1\u021b\37\uffff\1\u021b",
            "\1\u021c\37\uffff\1\u021c",
            "\1\u021d\37\uffff\1\u021d",
            "\1\u021e\37\uffff\1\u021e",
            "\1\u021f\37\uffff\1\u021f",
            "\1\u0220\37\uffff\1\u0220",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0222\37\uffff\1\u0222",
            "\1\u0223\37\uffff\1\u0223",
            "\1\u0224\37\uffff\1\u0224",
            "\1\u0225\37\uffff\1\u0225",
            "\1\u0227\16\uffff\1\u0226\20\uffff\1\u0227",
            "\1\u0228\37\uffff\1\u0228",
            "\1\u0229\37\uffff\1\u0229",
            "\1\u022a\37\uffff\1\u022a",
            "\1\u022b\37\uffff\1\u022b",
            "\1\u022c\37\uffff\1\u022c",
            "\1\u022d\37\uffff\1\u022d",
            "\1\u022e\37\uffff\1\u022e",
            "\1\u022f\37\uffff\1\u022f",
            "\1\u0230\37\uffff\1\u0230",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0235\37\uffff\1\u0235",
            "\1\u0236\37\uffff\1\u0236",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0238\37\uffff\1\u0238",
            "\1\u0239\37\uffff\1\u0239",
            "\1\u023a\37\uffff\1\u023a",
            "\1\u023b\37\uffff\1\u023b",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u023d\37\uffff\1\u023d",
            "\1\u023e\37\uffff\1\u023e",
            "\1\u023f\37\uffff\1\u023f",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0244\37\uffff\1\u0244",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0246\37\uffff\1\u0246",
            "\1\u0247\37\uffff\1\u0247",
            "\1\u0248\37\uffff\1\u0248",
            "\1\u0249\37\uffff\1\u0249",
            "\1\u024a\37\uffff\1\u024a",
            "\1\u024b\37\uffff\1\u024b",
            "\1\u024c\37\uffff\1\u024c",
            "\1\u024d\37\uffff\1\u024d",
            "",
            "\1\u024e\37\uffff\1\u024e",
            "\1\u024f\37\uffff\1\u024f",
            "\1\u0250\37\uffff\1\u0250",
            "\1\u0251\37\uffff\1\u0251",
            "\1\u0252\37\uffff\1\u0252",
            "\1\u0253\37\uffff\1\u0253",
            "\1\u0254\37\uffff\1\u0254",
            "\1\u0255\37\uffff\1\u0255",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0258\37\uffff\1\u0258",
            "\1\u0259\37\uffff\1\u0259",
            "\1\u025a\37\uffff\1\u025a",
            "\1\u025b\37\uffff\1\u025b",
            "\1\u025c\37\uffff\1\u025c",
            "\1\u025d\37\uffff\1\u025d",
            "\1\u025e\37\uffff\1\u025e",
            "\1\u025f",
            "\1\u0260\37\uffff\1\u0260",
            "\1\u0261\37\uffff\1\u0261",
            "\1\u0262\37\uffff\1\u0262",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0264\37\uffff\1\u0264",
            "",
            "\1\u0265\37\uffff\1\u0265",
            "\1\u0266\37\uffff\1\u0266",
            "\1\u0267\37\uffff\1\u0267",
            "\1\u0268\37\uffff\1\u0268",
            "\1\u0269\37\uffff\1\u0269",
            "\1\u026a\37\uffff\1\u026a",
            "\1\u026b\37\uffff\1\u026b",
            "\1\u026c\37\uffff\1\u026c",
            "\1\u026d",
            "\1\u026e\37\uffff\1\u026e",
            "\1\u026f\37\uffff\1\u026f",
            "\1\u0270\37\uffff\1\u0270",
            "\1\u0271\37\uffff\1\u0271",
            "\1\u0272\37\uffff\1\u0272",
            "\1\u0273\37\uffff\1\u0273",
            "",
            "",
            "",
            "",
            "\1\u0274\37\uffff\1\u0274",
            "\1\u0275\37\uffff\1\u0275",
            "",
            "\1\u0276\37\uffff\1\u0276",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0278\37\uffff\1\u0278",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u027a\37\uffff\1\u027a",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "",
            "\1\u027d\37\uffff\1\u027d",
            "",
            "\1\u027e\37\uffff\1\u027e",
            "\1\u027f\37\uffff\1\u027f",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0281\37\uffff\1\u0281",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0283\37\uffff\1\u0283",
            "\1\u0284\37\uffff\1\u0284",
            "\1\u0285\37\uffff\1\u0285",
            "\1\u0286\37\uffff\1\u0286",
            "\1\u0287\37\uffff\1\u0287",
            "\1\u0288\37\uffff\1\u0288",
            "\1\u0289\37\uffff\1\u0289",
            "\1\u028a\37\uffff\1\u028a",
            "\1\u028b\37\uffff\1\u028b",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u028d\37\uffff\1\u028d",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\u028e\1\uffff\32\65",
            "\1\u0290\37\uffff\1\u0290",
            "\1\u0291",
            "\1\u0292\37\uffff\1\u0292",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0295",
            "\1\u0296\37\uffff\1\u0296",
            "\1\u0297\37\uffff\1\u0297",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u029a\37\uffff\1\u029a",
            "\1\u029b\37\uffff\1\u029b",
            "\1\u029c\37\uffff\1\u029c",
            "\1\u029d\37\uffff\1\u029d",
            "\1\u029e\37\uffff\1\u029e",
            "\1\u029f\37\uffff\1\u029f",
            "\1\u02a0\37\uffff\1\u02a0",
            "\1\u02a1\37\uffff\1\u02a1",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02a3\37\uffff\1\u02a3",
            "\1\u02a4\37\uffff\1\u02a4",
            "\1\u02a5\37\uffff\1\u02a5",
            "\1\u02a6\37\uffff\1\u02a6",
            "\1\u02a7\37\uffff\1\u02a7",
            "\1\u02a8\37\uffff\1\u02a8",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02aa\37\uffff\1\u02aa",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02ac\37\uffff\1\u02ac",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u02af\37\uffff\1\u02af",
            "\1\u02b0\37\uffff\1\u02b0",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u02b2\37\uffff\1\u02b2",
            "",
            "\1\u02b3\37\uffff\1\u02b3",
            "\1\u02b4\37\uffff\1\u02b4",
            "\1\u02b5\37\uffff\1\u02b5",
            "\1\u02b6\37\uffff\1\u02b6",
            "\1\u02b7\37\uffff\1\u02b7",
            "\1\u02b8\37\uffff\1\u02b8",
            "\1\u02b9\37\uffff\1\u02b9",
            "\1\u02ba\37\uffff\1\u02ba",
            "\1\u02bb\37\uffff\1\u02bb",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02bd\37\uffff\1\u02bd",
            "",
            "\1\u02be",
            "\1\u02bf\37\uffff\1\u02bf",
            "\1\u02c0\37\uffff\1\u02c0",
            "",
            "",
            "\1\u02c1\37\uffff\1\u02c1",
            "\1\u02c2\37\uffff\1\u02c2",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u02c4\37\uffff\1\u02c4",
            "\1\u02c5\37\uffff\1\u02c5",
            "\1\u02c6\37\uffff\1\u02c6",
            "\1\u02c7\37\uffff\1\u02c7",
            "\1\u02c8\37\uffff\1\u02c8",
            "\1\u02c9\37\uffff\1\u02c9",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02cb\37\uffff\1\u02cb",
            "",
            "\1\u02cc\37\uffff\1\u02cc",
            "\1\u02cd\37\uffff\1\u02cd",
            "\1\u02ce\37\uffff\1\u02ce",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02d0\37\uffff\1\u02d0",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u02d4\37\uffff\1\u02d4",
            "\1\u02d5\37\uffff\1\u02d5",
            "",
            "\1\u02d6\37\uffff\1\u02d6",
            "\1\u02d7\37\uffff\1\u02d7",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02d9\37\uffff\1\u02d9",
            "\1\u02da\37\uffff\1\u02da",
            "\1\u02db\37\uffff\1\u02db",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02dd\37\uffff\1\u02dd",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u02e0\37\uffff\1\u02e0",
            "\1\u02e1\37\uffff\1\u02e1",
            "\1\u02e2\37\uffff\1\u02e2",
            "\1\u02e3\37\uffff\1\u02e3",
            "\1\u02e4\37\uffff\1\u02e4",
            "\1\u02e5\37\uffff\1\u02e5",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02e7\37\uffff\1\u02e7",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u02ed\37\uffff\1\u02ed",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "\1\u02f1\37\uffff\1\u02f1",
            "\1\u02f2\37\uffff\1\u02f2",
            "\1\u02f3\37\uffff\1\u02f3",
            "\1\u02f4\37\uffff\1\u02f4",
            "",
            "\1\u02f5\37\uffff\1\u02f5",
            "\1\u02f6\37\uffff\1\u02f6",
            "\1\u02f7\37\uffff\1\u02f7",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u02f9\37\uffff\1\u02f9",
            "\1\u02fa\37\uffff\1\u02fa",
            "\1\u02fb\37\uffff\1\u02fb",
            "\1\u02fc\37\uffff\1\u02fc",
            "\1\u02fd\37\uffff\1\u02fd",
            "\1\u02fe\37\uffff\1\u02fe",
            "",
            "\1\u02ff\37\uffff\1\u02ff",
            "",
            "",
            "",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\u0301\1\uffff\32\65",
            "\1\u0303\37\uffff\1\u0303",
            "\1\u0304\37\uffff\1\u0304",
            "\1\u0305\37\uffff\1\u0305",
            "\1\u0306\37\uffff\1\u0306",
            "\1\u0307\37\uffff\1\u0307",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0309\37\uffff\1\u0309",
            "\1\u030a\37\uffff\1\u030a",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u030c\37\uffff\1\u030c",
            "\1\u030d\37\uffff\1\u030d",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0310\37\uffff\1\u0310",
            "",
            "\1\u0311\37\uffff\1\u0311",
            "\1\u0312\37\uffff\1\u0312",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0316\37\uffff\1\u0316",
            "\1\u0317\37\uffff\1\u0317",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u031a\37\uffff\1\u031a",
            "\1\u031b\37\uffff\1\u031b",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u031f\37\uffff\1\u031f",
            "\1\u0320\37\uffff\1\u0320",
            "",
            "",
            "",
            "\1\u0321\37\uffff\1\u0321",
            "\1\u0322\37\uffff\1\u0322",
            "\1\u0323\37\uffff\1\u0323",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
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
            return "1:1: Tokens : ( END_FUNCTION_BLOCK | END_CONFIGURATION | END_TRANSITION | FUNCTION_BLOCK | LDATE_AND_TIME | CONFIGURATION | DATE_AND_TIME | END_ALGORITHM | END_INTERFACE | END_NAMESPACE | END_FUNCTION | END_RESOURCE | INITIAL_STEP | LTIME_OF_DAY | VAR_EXTERNAL | END_PROGRAM | TIME_OF_DAY | END_ACTION | END_METHOD | END_REPEAT | END_STRUCT | IMPLEMENTS | NON_RETAIN | READ_WRITE | TRANSITION | VAR_ACCESS | VAR_CONFIG | VAR_GLOBAL | VAR_IN_OUT | VAR_OUTPUT | ALGORITHM | END_CLASS | END_WHILE | INTERFACE | NAMESPACE | PROTECTED | READ_ONLY | VAR_INPUT | ABSTRACT | CONSTANT | CONTINUE | END_CASE | END_STEP | END_TYPE | FUNCTION | INTERNAL | INTERVAL | OVERRIDE | PRIORITY | RESOURCE | VAR_TEMP | END_FOR | END_VAR | EXTENDS | INTERAL | OVERLAP | PRIVATE | PROGRAM | WSTRING | ACTION | END_IF | IMPORT | METHOD | PUBLIC | REF_TO | REPEAT | RETAIN | RETURN | SINGLE | STRING | STRUCT | ARRAY | CLASS | DWORD | ELSIF | FALSE | FINAL | LDATE | LREAL | LTIME | LWORD | SUPER | UDINT | ULINT | UNTIL | USING | USINT | WCHAR | WHILE | BOOL | BYTE | CASE | CHAR | DATE | DINT | ELSE | EXIT | FROM | LINT | LTOD | NULL | REAL | SINT | STEP | TASK | THEN | THIS | TIME | TRUE | TYPE | UINT | WITH | WORD | ColonColonAsterisk | AND | FOR | INT | LDT | MOD | NOT | REF | TOD | VAR | XOR | B | D_1 | L | W | X | AsteriskAsterisk | FullStopFullStop | ColonColon | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | DO | DT | IF | LD | LT | OF | ON | OR | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | D | T | LeftSquareBracket | RightSquareBracket | LeftCurlyBracket | RightCurlyBracket | RULE_NON_DECIMAL | RULE_INT | RULE_DECIMAL | RULE_TIME_VALUE | RULE_ID | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_0 = input.LA(1);

                        s = -1;
                        if ( (LA21_0=='E'||LA21_0=='e') ) {s = 1;}

                        else if ( (LA21_0=='F'||LA21_0=='f') ) {s = 2;}

                        else if ( (LA21_0=='L'||LA21_0=='l') ) {s = 3;}

                        else if ( (LA21_0=='C'||LA21_0=='c') ) {s = 4;}

                        else if ( (LA21_0=='D'||LA21_0=='d') ) {s = 5;}

                        else if ( (LA21_0=='I'||LA21_0=='i') ) {s = 6;}

                        else if ( (LA21_0=='V'||LA21_0=='v') ) {s = 7;}

                        else if ( (LA21_0=='T'||LA21_0=='t') ) {s = 8;}

                        else if ( (LA21_0=='N'||LA21_0=='n') ) {s = 9;}

                        else if ( (LA21_0=='R'||LA21_0=='r') ) {s = 10;}

                        else if ( (LA21_0=='A'||LA21_0=='a') ) {s = 11;}

                        else if ( (LA21_0=='P'||LA21_0=='p') ) {s = 12;}

                        else if ( (LA21_0=='O'||LA21_0=='o') ) {s = 13;}

                        else if ( (LA21_0=='W'||LA21_0=='w') ) {s = 14;}

                        else if ( (LA21_0=='M'||LA21_0=='m') ) {s = 15;}

                        else if ( (LA21_0=='S'||LA21_0=='s') ) {s = 16;}

                        else if ( (LA21_0=='U'||LA21_0=='u') ) {s = 17;}

                        else if ( (LA21_0=='B'||LA21_0=='b') ) {s = 18;}

                        else if ( (LA21_0==':') ) {s = 19;}

                        else if ( (LA21_0=='X'||LA21_0=='x') ) {s = 20;}

                        else if ( (LA21_0=='%') ) {s = 21;}

                        else if ( (LA21_0=='*') ) {s = 22;}

                        else if ( (LA21_0=='.') ) {s = 23;}

                        else if ( (LA21_0=='<') ) {s = 24;}

                        else if ( (LA21_0=='=') ) {s = 25;}

                        else if ( (LA21_0=='>') ) {s = 26;}

                        else if ( (LA21_0=='#') ) {s = 27;}

                        else if ( (LA21_0=='&') ) {s = 28;}

                        else if ( (LA21_0=='(') ) {s = 29;}

                        else if ( (LA21_0==')') ) {s = 30;}

                        else if ( (LA21_0=='+') ) {s = 31;}

                        else if ( (LA21_0==',') ) {s = 32;}

                        else if ( (LA21_0=='-') ) {s = 33;}

                        else if ( (LA21_0=='/') ) {s = 34;}

                        else if ( (LA21_0==';') ) {s = 35;}

                        else if ( (LA21_0=='[') ) {s = 36;}

                        else if ( (LA21_0==']') ) {s = 37;}

                        else if ( (LA21_0=='{') ) {s = 38;}

                        else if ( (LA21_0=='}') ) {s = 39;}

                        else if ( (LA21_0=='2') ) {s = 40;}

                        else if ( (LA21_0=='8') ) {s = 41;}

                        else if ( (LA21_0=='1') ) {s = 42;}

                        else if ( (LA21_0=='0'||(LA21_0>='3' && LA21_0<='7')||LA21_0=='9') ) {s = 43;}

                        else if ( (LA21_0=='^') ) {s = 44;}

                        else if ( ((LA21_0>='G' && LA21_0<='H')||(LA21_0>='J' && LA21_0<='K')||LA21_0=='Q'||(LA21_0>='Y' && LA21_0<='Z')||LA21_0=='_'||(LA21_0>='g' && LA21_0<='h')||(LA21_0>='j' && LA21_0<='k')||LA21_0=='q'||(LA21_0>='y' && LA21_0<='z')) ) {s = 45;}

                        else if ( (LA21_0=='\"') ) {s = 46;}

                        else if ( (LA21_0=='\'') ) {s = 47;}

                        else if ( ((LA21_0>='\t' && LA21_0<='\n')||LA21_0=='\r'||LA21_0==' ') ) {s = 48;}

                        else if ( ((LA21_0>='\u0000' && LA21_0<='\b')||(LA21_0>='\u000B' && LA21_0<='\f')||(LA21_0>='\u000E' && LA21_0<='\u001F')||LA21_0=='!'||LA21_0=='$'||(LA21_0>='?' && LA21_0<='@')||LA21_0=='\\'||LA21_0=='`'||LA21_0=='|'||(LA21_0>='~' && LA21_0<='\uFFFF')) ) {s = 49;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA21_46 = input.LA(1);

                        s = -1;
                        if ( ((LA21_46>='\u0000' && LA21_46<='\uFFFF')) ) {s = 159;}

                        else s = 49;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA21_47 = input.LA(1);

                        s = -1;
                        if ( ((LA21_47>='\u0000' && LA21_47<='\uFFFF')) ) {s = 159;}

                        else s = 49;

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