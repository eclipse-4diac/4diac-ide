package org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSTAlgorithmLexer extends Lexer {
    public static final int LWORD=80;
    public static final int LessThanSignGreaterThanSign=128;
    public static final int FUNCTION_BLOCK=7;
    public static final int EqualsSignGreaterThanSign=129;
    public static final int VAR=117;
    public static final int END_IF=61;
    public static final int ULINT=82;
    public static final int END_CASE=44;
    public static final int LessThanSign=154;
    public static final int RULE_TIME_HOURS=169;
    public static final int LeftParenthesis=144;
    public static final int INTERFACE=36;
    public static final int VAR_CONFIG=29;
    public static final int BYTE=88;
    public static final int INTERNAL=47;
    public static final int ELSE=93;
    public static final int REF_TO=64;
    public static final int TYPE=105;
    public static final int IF=135;
    public static final int LINT=95;
    public static final int GreaterThanSign=156;
    public static final int WORD=108;
    public static final int DATE_AND_TIME=10;
    public static final int RULE_ID=175;
    public static final int CONFIGURATION=9;
    public static final int TOD=116;
    public static final int DINT=92;
    public static final int UDINT=81;
    public static final int RULE_TIME_NANOS=174;
    public static final int CASE=89;
    public static final int GreaterThanSignEqualsSign=130;
    public static final int END_ALGORITHM=11;
    public static final int AT=131;
    public static final int PlusSign=147;
    public static final int RULE_INT=163;
    public static final int END_FOR=53;
    public static final int RULE_TIME_DAYS=168;
    public static final int RULE_ML_COMMENT=177;
    public static final int PUBLIC=63;
    public static final int THEN=101;
    public static final int XOR=118;
    public static final int LeftSquareBracket=159;
    public static final int PROGRAM=58;
    public static final int EXIT=94;
    public static final int TIME_OF_DAY=19;
    public static final int B=119;
    public static final int LDATE_AND_TIME=8;
    public static final int REPEAT=65;
    public static final int D=157;
    public static final int CHAR=90;
    public static final int L=121;
    public static final int LTIME=79;
    public static final int Comma=148;
    public static final int HyphenMinus=149;
    public static final int T=158;
    public static final int W=122;
    public static final int BY=132;
    public static final int X=123;
    public static final int ELSIF=74;
    public static final int END_REPEAT=22;
    public static final int LessThanSignEqualsSign=127;
    public static final int Solidus=151;
    public static final int PROTECTED=38;
    public static final int VAR_INPUT=40;
    public static final int RESOURCE=51;
    public static final int INTERVAL=48;
    public static final int FullStop=150;
    public static final int RULE_TIME_SECONDS=171;
    public static final int VAR_TEMP=52;
    public static final int CONSTANT=42;
    public static final int RULE_TIME_VALUE=167;
    public static final int PRIVATE=57;
    public static final int TRANSITION=27;
    public static final int CONTINUE=43;
    public static final int Semicolon=153;
    public static final int REF=115;
    public static final int LD=136;
    public static final int VAR_OUTPUT=32;
    public static final int STRING=69;
    public static final int RULE_HEX_DIGIT=161;
    public static final int TO=141;
    public static final int END_TYPE=46;
    public static final int UINT=106;
    public static final int INITIAL_STEP=15;
    public static final int LTOD=96;
    public static final int NAMESPACE=37;
    public static final int EXTENDS=55;
    public static final int SINGLE=68;
    public static final int ARRAY=71;
    public static final int LT=137;
    public static final int PRIORITY=50;
    public static final int CLASS=72;
    public static final int DO=133;
    public static final int WSTRING=59;
    public static final int READ_WRITE=26;
    public static final int END_CONFIGURATION=5;
    public static final int DT=134;
    public static final int END_VAR=54;
    public static final int END_STEP=45;
    public static final int END_STRUCT=23;
    public static final int RULE_TIME_PART=166;
    public static final int FullStopFullStop=125;
    public static final int OVERLAP=56;
    public static final int Ampersand=143;
    public static final int END_NAMESPACE=13;
    public static final int END_ACTION=20;
    public static final int RightSquareBracket=160;
    public static final int NULL=97;
    public static final int USINT=84;
    public static final int DWORD=73;
    public static final int FOR=110;
    public static final int RightParenthesis=145;
    public static final int TRUE=104;
    public static final int FINAL=76;
    public static final int ColonEqualsSign=126;
    public static final int END_WHILE=35;
    public static final int DATE=91;
    public static final int NOT=114;
    public static final int LDATE=77;
    public static final int AND=109;
    public static final int NumberSign=142;
    public static final int REAL=98;
    public static final int AsteriskAsterisk=124;
    public static final int METHOD=62;
    public static final int THIS=102;
    public static final int SINT=99;
    public static final int NON_RETAIN=25;
    public static final int STRUCT=70;
    public static final int LTIME_OF_DAY=16;
    public static final int END_TRANSITION=6;
    public static final int LREAL=78;
    public static final int VAR_GLOBAL=30;
    public static final int WCHAR=85;
    public static final int ALGORITHM=33;
    public static final int END_FUNCTION_BLOCK=4;
    public static final int VAR_EXTERNAL=17;
    public static final int RULE_STRING=176;
    public static final int VAR_ACCESS=28;
    public static final int ABSTRACT=41;
    public static final int READ_ONLY=39;
    public static final int INT=111;
    public static final int RULE_SL_COMMENT=178;
    public static final int RETURN=67;
    public static final int EqualsSign=155;
    public static final int OF=138;
    public static final int END_METHOD=21;
    public static final int END_RESOURCE=14;
    public static final int Colon=152;
    public static final int EOF=-1;
    public static final int LDT=112;
    public static final int Asterisk=146;
    public static final int RULE_TIME_MILLIS=172;
    public static final int ON=139;
    public static final int MOD=113;
    public static final int OR=140;
    public static final int RETAIN=66;
    public static final int RULE_WS=179;
    public static final int RULE_EXT_INT=164;
    public static final int VAR_IN_OUT=31;
    public static final int END_INTERFACE=12;
    public static final int IMPLEMENTS=24;
    public static final int STEP=100;
    public static final int TIME=103;
    public static final int RULE_ANY_OTHER=180;
    public static final int UNTIL=83;
    public static final int RULE_NUMBER=165;
    public static final int WITH=107;
    public static final int RULE_TIME_MINUTES=170;
    public static final int END_CLASS=34;
    public static final int OVERRIDE=49;
    public static final int ACTION=60;
    public static final int BOOL=87;
    public static final int D_1=120;
    public static final int FALSE=75;
    public static final int WHILE=86;
    public static final int RULE_TIME_MICROS=173;
    public static final int END_PROGRAM=18;
    public static final int RULE_NON_DECIMAL=162;

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
            // InternalSTAlgorithmLexer.g:14:20: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' ) )
            // InternalSTAlgorithmLexer.g:14:22: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' )
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
            // InternalSTAlgorithmLexer.g:16:19: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:16:21: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:18:16: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:18:18: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:20:16: ( ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' ) )
            // InternalSTAlgorithmLexer.g:20:18: ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'C' | 'c' ) ( 'K' | 'k' )
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
            // InternalSTAlgorithmLexer.g:22:16: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:22:18: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:24:15: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:24:17: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:26:15: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:26:17: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:28:15: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'G' | 'g' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:28:17: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'G' | 'g' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:30:15: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:30:17: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:32:15: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:32:17: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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

    // $ANTLR start "END_RESOURCE"
    public final void mEND_RESOURCE() throws RecognitionException {
        try {
            int _type = END_RESOURCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:34:14: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:34:16: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:36:14: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:36:16: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
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
            // InternalSTAlgorithmLexer.g:38:14: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:38:16: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:40:14: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:40:16: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:42:13: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:42:15: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:44:13: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:44:15: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:46:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:46:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:48:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:48:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:50:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:50:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:52:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:52:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:54:12: ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:54:14: ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'P' | 'p' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:56:12: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:56:14: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'N' | 'n' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:58:12: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:58:14: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:60:12: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:60:14: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:62:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:62:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:64:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' ) )
            // InternalSTAlgorithmLexer.g:64:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'G' | 'g' )
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
            // InternalSTAlgorithmLexer.g:66:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:66:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:68:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:68:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:70:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:70:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:72:11: ( ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'G' | 'g' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:72:13: ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'G' | 'g' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:74:11: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:74:13: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:76:11: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:76:13: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:78:11: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:78:13: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:80:11: ( ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:80:13: ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'P' | 'p' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:82:11: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:82:13: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:84:11: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'L' | 'l' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:84:13: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) '_' ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'L' | 'l' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:86:11: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:86:13: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:88:10: ( ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:88:12: ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:90:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:90:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:92:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:92:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:94:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:94:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:96:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:96:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
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
            // InternalSTAlgorithmLexer.g:98:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:98:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' )
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

    // $ANTLR start "INTERNAL"
    public final void mINTERNAL() throws RecognitionException {
        try {
            int _type = INTERNAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:100:10: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:100:12: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:102:10: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:102:12: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:104:10: ( ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'D' | 'd' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:104:12: ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'D' | 'd' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:106:10: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:106:12: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:108:10: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:108:12: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:110:10: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'T' | 't' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:110:12: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'T' | 't' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'P' | 'p' )
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
            // InternalSTAlgorithmLexer.g:112:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:112:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:114:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:114:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:116:9: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:116:11: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' )
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

    // $ANTLR start "OVERLAP"
    public final void mOVERLAP() throws RecognitionException {
        try {
            int _type = OVERLAP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:118:9: ( ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:118:11: ( 'O' | 'o' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'P' | 'p' )
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
            // InternalSTAlgorithmLexer.g:120:9: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:120:11: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:122:9: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:122:11: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:124:9: ( ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalSTAlgorithmLexer.g:124:11: ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
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
            // InternalSTAlgorithmLexer.g:126:8: ( ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:126:10: ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:128:8: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:128:10: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' )
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

    // $ANTLR start "METHOD"
    public final void mMETHOD() throws RecognitionException {
        try {
            int _type = METHOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:130:8: ( ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:130:10: ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:132:8: ( ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'C' | 'c' ) )
            // InternalSTAlgorithmLexer.g:132:10: ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'C' | 'c' )
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
            // InternalSTAlgorithmLexer.g:134:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) '_' ( 'T' | 't' ) ( 'O' | 'o' ) )
            // InternalSTAlgorithmLexer.g:134:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) '_' ( 'T' | 't' ) ( 'O' | 'o' )
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
            // InternalSTAlgorithmLexer.g:136:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:136:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:138:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:138:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:140:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:140:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:142:8: ( ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:142:10: ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:144:8: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalSTAlgorithmLexer.g:144:10: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
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
            // InternalSTAlgorithmLexer.g:146:8: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:146:10: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:148:7: ( ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:148:9: ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:150:7: ( ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:150:9: ( 'C' | 'c' ) ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:152:7: ( ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:152:9: ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:154:7: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:154:9: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:156:7: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:156:9: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:158:7: ( ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:158:9: ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:160:7: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:160:9: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:162:7: ( ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:162:9: ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:164:7: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:164:9: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:166:7: ( ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:166:9: ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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

    // $ANTLR start "UDINT"
    public final void mUDINT() throws RecognitionException {
        try {
            int _type = UDINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:168:7: ( ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:168:9: ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:170:7: ( ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:170:9: ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:172:7: ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:172:9: ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' )
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

    // $ANTLR start "USINT"
    public final void mUSINT() throws RecognitionException {
        try {
            int _type = USINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:174:7: ( ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:174:9: ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:176:7: ( ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:176:9: ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:178:7: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:178:9: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:180:6: ( ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:180:8: ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:182:6: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:182:8: ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:184:6: ( ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:184:8: ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:186:6: ( ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:186:8: ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:188:6: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:188:8: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:190:6: ( ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:190:8: ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:192:6: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:192:8: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:194:6: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:194:8: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' )
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

    // $ANTLR start "LINT"
    public final void mLINT() throws RecognitionException {
        try {
            int _type = LINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:196:6: ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:196:8: ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:198:6: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:198:8: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:200:6: ( ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:200:8: ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:202:6: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:202:8: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:204:6: ( ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:204:8: ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:206:6: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' ) )
            // InternalSTAlgorithmLexer.g:206:8: ( 'S' | 's' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'P' | 'p' )
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

    // $ANTLR start "THEN"
    public final void mTHEN() throws RecognitionException {
        try {
            int _type = THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:208:6: ( ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:208:8: ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:210:6: ( ( 'T' | 't' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:210:8: ( 'T' | 't' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:212:6: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:212:8: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:214:6: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:214:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:216:6: ( ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' ) )
            // InternalSTAlgorithmLexer.g:216:8: ( 'T' | 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' )
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
            // InternalSTAlgorithmLexer.g:218:6: ( ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:218:8: ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:220:6: ( ( 'W' | 'w' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' ) )
            // InternalSTAlgorithmLexer.g:220:8: ( 'W' | 'w' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'H' | 'h' )
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
            // InternalSTAlgorithmLexer.g:222:6: ( ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:222:8: ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:224:5: ( ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:224:7: ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:226:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:226:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:228:5: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:228:7: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:230:5: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:230:7: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:232:5: ( ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:232:7: ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:234:5: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:234:7: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:236:5: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:236:7: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:238:5: ( ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:238:7: ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:240:5: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:240:7: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:242:5: ( ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:242:7: ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:244:3: ( '%' ( 'B' | 'b' ) )
            // InternalSTAlgorithmLexer.g:244:5: '%' ( 'B' | 'b' )
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
            // InternalSTAlgorithmLexer.g:246:5: ( '%' ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:246:7: '%' ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:248:3: ( '%' ( 'L' | 'l' ) )
            // InternalSTAlgorithmLexer.g:248:5: '%' ( 'L' | 'l' )
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
            // InternalSTAlgorithmLexer.g:250:3: ( '%' ( 'W' | 'w' ) )
            // InternalSTAlgorithmLexer.g:250:5: '%' ( 'W' | 'w' )
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
            // InternalSTAlgorithmLexer.g:252:3: ( '%' ( 'X' | 'x' ) )
            // InternalSTAlgorithmLexer.g:252:5: '%' ( 'X' | 'x' )
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
            // InternalSTAlgorithmLexer.g:254:18: ( '*' '*' )
            // InternalSTAlgorithmLexer.g:254:20: '*' '*'
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
            // InternalSTAlgorithmLexer.g:256:18: ( '.' '.' )
            // InternalSTAlgorithmLexer.g:256:20: '.' '.'
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

    // $ANTLR start "ColonEqualsSign"
    public final void mColonEqualsSign() throws RecognitionException {
        try {
            int _type = ColonEqualsSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:258:17: ( ':' '=' )
            // InternalSTAlgorithmLexer.g:258:19: ':' '='
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
            // InternalSTAlgorithmLexer.g:260:24: ( '<' '=' )
            // InternalSTAlgorithmLexer.g:260:26: '<' '='
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
            // InternalSTAlgorithmLexer.g:262:29: ( '<' '>' )
            // InternalSTAlgorithmLexer.g:262:31: '<' '>'
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
            // InternalSTAlgorithmLexer.g:264:27: ( '=' '>' )
            // InternalSTAlgorithmLexer.g:264:29: '=' '>'
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
            // InternalSTAlgorithmLexer.g:266:27: ( '>' '=' )
            // InternalSTAlgorithmLexer.g:266:29: '>' '='
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
            // InternalSTAlgorithmLexer.g:268:4: ( ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:268:6: ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:270:4: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) )
            // InternalSTAlgorithmLexer.g:270:6: ( 'B' | 'b' ) ( 'Y' | 'y' )
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
            // InternalSTAlgorithmLexer.g:272:4: ( ( 'D' | 'd' ) ( 'O' | 'o' ) )
            // InternalSTAlgorithmLexer.g:272:6: ( 'D' | 'd' ) ( 'O' | 'o' )
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
            // InternalSTAlgorithmLexer.g:274:4: ( ( 'D' | 'd' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:274:6: ( 'D' | 'd' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:276:4: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:276:6: ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:278:4: ( ( 'L' | 'l' ) ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:278:6: ( 'L' | 'l' ) ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:280:4: ( ( 'L' | 'l' ) ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:280:6: ( 'L' | 'l' ) ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:282:4: ( ( 'O' | 'o' ) ( 'F' | 'f' ) )
            // InternalSTAlgorithmLexer.g:282:6: ( 'O' | 'o' ) ( 'F' | 'f' )
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
            // InternalSTAlgorithmLexer.g:284:4: ( ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTAlgorithmLexer.g:284:6: ( 'O' | 'o' ) ( 'N' | 'n' )
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
            // InternalSTAlgorithmLexer.g:286:4: ( ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTAlgorithmLexer.g:286:6: ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTAlgorithmLexer.g:288:4: ( ( 'T' | 't' ) ( 'O' | 'o' ) )
            // InternalSTAlgorithmLexer.g:288:6: ( 'T' | 't' ) ( 'O' | 'o' )
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
            // InternalSTAlgorithmLexer.g:290:12: ( '#' )
            // InternalSTAlgorithmLexer.g:290:14: '#'
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
            // InternalSTAlgorithmLexer.g:292:11: ( '&' )
            // InternalSTAlgorithmLexer.g:292:13: '&'
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
            // InternalSTAlgorithmLexer.g:294:17: ( '(' )
            // InternalSTAlgorithmLexer.g:294:19: '('
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
            // InternalSTAlgorithmLexer.g:296:18: ( ')' )
            // InternalSTAlgorithmLexer.g:296:20: ')'
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
            // InternalSTAlgorithmLexer.g:298:10: ( '*' )
            // InternalSTAlgorithmLexer.g:298:12: '*'
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
            // InternalSTAlgorithmLexer.g:300:10: ( '+' )
            // InternalSTAlgorithmLexer.g:300:12: '+'
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
            // InternalSTAlgorithmLexer.g:302:7: ( ',' )
            // InternalSTAlgorithmLexer.g:302:9: ','
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
            // InternalSTAlgorithmLexer.g:304:13: ( '-' )
            // InternalSTAlgorithmLexer.g:304:15: '-'
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
            // InternalSTAlgorithmLexer.g:306:10: ( '.' )
            // InternalSTAlgorithmLexer.g:306:12: '.'
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
            // InternalSTAlgorithmLexer.g:308:9: ( '/' )
            // InternalSTAlgorithmLexer.g:308:11: '/'
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
            // InternalSTAlgorithmLexer.g:310:7: ( ':' )
            // InternalSTAlgorithmLexer.g:310:9: ':'
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
            // InternalSTAlgorithmLexer.g:312:11: ( ';' )
            // InternalSTAlgorithmLexer.g:312:13: ';'
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
            // InternalSTAlgorithmLexer.g:314:14: ( '<' )
            // InternalSTAlgorithmLexer.g:314:16: '<'
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
            // InternalSTAlgorithmLexer.g:316:12: ( '=' )
            // InternalSTAlgorithmLexer.g:316:14: '='
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
            // InternalSTAlgorithmLexer.g:318:17: ( '>' )
            // InternalSTAlgorithmLexer.g:318:19: '>'
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
            // InternalSTAlgorithmLexer.g:320:3: ( ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:320:5: ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:322:3: ( ( 'T' | 't' ) )
            // InternalSTAlgorithmLexer.g:322:5: ( 'T' | 't' )
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
            // InternalSTAlgorithmLexer.g:324:19: ( '[' )
            // InternalSTAlgorithmLexer.g:324:21: '['
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
            // InternalSTAlgorithmLexer.g:326:20: ( ']' )
            // InternalSTAlgorithmLexer.g:326:22: ']'
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

    // $ANTLR start "RULE_HEX_DIGIT"
    public final void mRULE_HEX_DIGIT() throws RecognitionException {
        try {
            // InternalSTAlgorithmLexer.g:328:25: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' ) )
            // InternalSTAlgorithmLexer.g:328:27: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' )
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
            // InternalSTAlgorithmLexer.g:330:18: ( ( '2#' | '8#' | '16#' ) ( RULE_HEX_DIGIT )+ )
            // InternalSTAlgorithmLexer.g:330:20: ( '2#' | '8#' | '16#' ) ( RULE_HEX_DIGIT )+
            {
            // InternalSTAlgorithmLexer.g:330:20: ( '2#' | '8#' | '16#' )
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
                    // InternalSTAlgorithmLexer.g:330:21: '2#'
                    {
                    match("2#"); 


                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmLexer.g:330:26: '8#'
                    {
                    match("8#"); 


                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmLexer.g:330:31: '16#'
                    {
                    match("16#"); 


                    }
                    break;

            }

            // InternalSTAlgorithmLexer.g:330:38: ( RULE_HEX_DIGIT )+
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
            	    // InternalSTAlgorithmLexer.g:330:38: RULE_HEX_DIGIT
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
            // InternalSTAlgorithmLexer.g:332:10: ( '0' .. '9' ( ( '_' )? '0' .. '9' )* )
            // InternalSTAlgorithmLexer.g:332:12: '0' .. '9' ( ( '_' )? '0' .. '9' )*
            {
            matchRange('0','9'); 
            // InternalSTAlgorithmLexer.g:332:21: ( ( '_' )? '0' .. '9' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||LA4_0=='_') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSTAlgorithmLexer.g:332:22: ( '_' )? '0' .. '9'
            	    {
            	    // InternalSTAlgorithmLexer.g:332:22: ( '_' )?
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( (LA3_0=='_') ) {
            	        alt3=1;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // InternalSTAlgorithmLexer.g:332:22: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }

            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    break loop4;
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

    // $ANTLR start "RULE_EXT_INT"
    public final void mRULE_EXT_INT() throws RecognitionException {
        try {
            // InternalSTAlgorithmLexer.g:334:23: ( RULE_INT ( 'e' | 'E' ) ( '-' | '+' )? RULE_INT )
            // InternalSTAlgorithmLexer.g:334:25: RULE_INT ( 'e' | 'E' ) ( '-' | '+' )? RULE_INT
            {
            mRULE_INT(); 
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalSTAlgorithmLexer.g:334:44: ( '-' | '+' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='+'||LA5_0=='-') ) {
                alt5=1;
            }
            switch (alt5) {
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

        }
        finally {
        }
    }
    // $ANTLR end "RULE_EXT_INT"

    // $ANTLR start "RULE_NUMBER"
    public final void mRULE_NUMBER() throws RecognitionException {
        try {
            int _type = RULE_NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:336:13: ( RULE_INT '.' ( RULE_EXT_INT | RULE_INT ) )
            // InternalSTAlgorithmLexer.g:336:15: RULE_INT '.' ( RULE_EXT_INT | RULE_INT )
            {
            mRULE_INT(); 
            match('.'); 
            // InternalSTAlgorithmLexer.g:336:28: ( RULE_EXT_INT | RULE_INT )
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:336:29: RULE_EXT_INT
                    {
                    mRULE_EXT_INT(); 

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmLexer.g:336:42: RULE_INT
                    {
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
    // $ANTLR end "RULE_NUMBER"

    // $ANTLR start "RULE_TIME_VALUE"
    public final void mRULE_TIME_VALUE() throws RecognitionException {
        try {
            int _type = RULE_TIME_VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTAlgorithmLexer.g:338:17: ( ( RULE_TIME_PART ( '_' )? )+ )
            // InternalSTAlgorithmLexer.g:338:19: ( RULE_TIME_PART ( '_' )? )+
            {
            // InternalSTAlgorithmLexer.g:338:19: ( RULE_TIME_PART ( '_' )? )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalSTAlgorithmLexer.g:338:20: RULE_TIME_PART ( '_' )?
            	    {
            	    mRULE_TIME_PART(); 
            	    // InternalSTAlgorithmLexer.g:338:35: ( '_' )?
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0=='_') ) {
            	        alt7=1;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // InternalSTAlgorithmLexer.g:338:35: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
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
            // InternalSTAlgorithmLexer.g:340:25: ( ( RULE_INT | RULE_NUMBER ) ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS ) )
            // InternalSTAlgorithmLexer.g:340:27: ( RULE_INT | RULE_NUMBER ) ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS )
            {
            // InternalSTAlgorithmLexer.g:340:27: ( RULE_INT | RULE_NUMBER )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:340:28: RULE_INT
                    {
                    mRULE_INT(); 

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmLexer.g:340:37: RULE_NUMBER
                    {
                    mRULE_NUMBER(); 

                    }
                    break;

            }

            // InternalSTAlgorithmLexer.g:340:50: ( RULE_TIME_DAYS | RULE_TIME_HOURS | RULE_TIME_MINUTES | RULE_TIME_SECONDS | RULE_TIME_MILLIS | RULE_TIME_MICROS | RULE_TIME_NANOS )
            int alt10=7;
            switch ( input.LA(1) ) {
            case 'D':
            case 'd':
                {
                alt10=1;
                }
                break;
            case 'H':
            case 'h':
                {
                alt10=2;
                }
                break;
            case 'M':
            case 'm':
                {
                int LA10_3 = input.LA(2);

                if ( (LA10_3=='S'||LA10_3=='s') ) {
                    alt10=5;
                }
                else {
                    alt10=3;}
                }
                break;
            case 'S':
            case 's':
                {
                alt10=4;
                }
                break;
            case 'U':
            case 'u':
                {
                alt10=6;
                }
                break;
            case 'N':
            case 'n':
                {
                alt10=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:340:51: RULE_TIME_DAYS
                    {
                    mRULE_TIME_DAYS(); 

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmLexer.g:340:66: RULE_TIME_HOURS
                    {
                    mRULE_TIME_HOURS(); 

                    }
                    break;
                case 3 :
                    // InternalSTAlgorithmLexer.g:340:82: RULE_TIME_MINUTES
                    {
                    mRULE_TIME_MINUTES(); 

                    }
                    break;
                case 4 :
                    // InternalSTAlgorithmLexer.g:340:100: RULE_TIME_SECONDS
                    {
                    mRULE_TIME_SECONDS(); 

                    }
                    break;
                case 5 :
                    // InternalSTAlgorithmLexer.g:340:118: RULE_TIME_MILLIS
                    {
                    mRULE_TIME_MILLIS(); 

                    }
                    break;
                case 6 :
                    // InternalSTAlgorithmLexer.g:340:135: RULE_TIME_MICROS
                    {
                    mRULE_TIME_MICROS(); 

                    }
                    break;
                case 7 :
                    // InternalSTAlgorithmLexer.g:340:152: RULE_TIME_NANOS
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
            // InternalSTAlgorithmLexer.g:342:25: ( ( 'D' | 'd' ) )
            // InternalSTAlgorithmLexer.g:342:27: ( 'D' | 'd' )
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
            // InternalSTAlgorithmLexer.g:344:26: ( ( 'H' | 'h' ) )
            // InternalSTAlgorithmLexer.g:344:28: ( 'H' | 'h' )
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
            // InternalSTAlgorithmLexer.g:346:28: ( ( 'M' | 'm' ) )
            // InternalSTAlgorithmLexer.g:346:30: ( 'M' | 'm' )
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
            // InternalSTAlgorithmLexer.g:348:28: ( ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:348:30: ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:350:27: ( ( 'M' | 'm' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:350:29: ( 'M' | 'm' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:352:27: ( ( 'U' | 'u' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:352:29: ( 'U' | 'u' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:354:26: ( ( 'N' | 'n' ) ( 'S' | 's' ) )
            // InternalSTAlgorithmLexer.g:354:28: ( 'N' | 'n' ) ( 'S' | 's' )
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
            // InternalSTAlgorithmLexer.g:356:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalSTAlgorithmLexer.g:356:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalSTAlgorithmLexer.g:356:11: ( '^' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='^') ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:356:11: '^'
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

            // InternalSTAlgorithmLexer.g:356:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='0' && LA12_0<='9')||(LA12_0>='A' && LA12_0<='Z')||LA12_0=='_'||(LA12_0>='a' && LA12_0<='z')) ) {
                    alt12=1;
                }


                switch (alt12) {
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
            	    break loop12;
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
            // InternalSTAlgorithmLexer.g:358:13: ( ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' ) )
            // InternalSTAlgorithmLexer.g:358:15: ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' )
            {
            // InternalSTAlgorithmLexer.g:358:15: ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' | '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\"') ) {
                alt15=1;
            }
            else if ( (LA15_0=='\'') ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:358:16: '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalSTAlgorithmLexer.g:358:20: ( '$' . | ~ ( ( '$' | '\"' ) ) )*
                    loop13:
                    do {
                        int alt13=3;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0=='$') ) {
                            alt13=1;
                        }
                        else if ( ((LA13_0>='\u0000' && LA13_0<='!')||LA13_0=='#'||(LA13_0>='%' && LA13_0<='\uFFFF')) ) {
                            alt13=2;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // InternalSTAlgorithmLexer.g:358:21: '$' .
                    	    {
                    	    match('$'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalSTAlgorithmLexer.g:358:27: ~ ( ( '$' | '\"' ) )
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
                    	    break loop13;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmLexer.g:358:46: '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalSTAlgorithmLexer.g:358:51: ( '$' . | ~ ( ( '$' | '\\'' ) ) )*
                    loop14:
                    do {
                        int alt14=3;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0=='$') ) {
                            alt14=1;
                        }
                        else if ( ((LA14_0>='\u0000' && LA14_0<='#')||(LA14_0>='%' && LA14_0<='&')||(LA14_0>='(' && LA14_0<='\uFFFF')) ) {
                            alt14=2;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalSTAlgorithmLexer.g:358:52: '$' .
                    	    {
                    	    match('$'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalSTAlgorithmLexer.g:358:58: ~ ( ( '$' | '\\'' ) )
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
                    	    break loop14;
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
            // InternalSTAlgorithmLexer.g:360:17: ( ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' ) )
            // InternalSTAlgorithmLexer.g:360:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
            {
            // InternalSTAlgorithmLexer.g:360:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='/') ) {
                alt18=1;
            }
            else if ( (LA18_0=='(') ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:360:20: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 

                    // InternalSTAlgorithmLexer.g:360:25: ( options {greedy=false; } : . )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0=='*') ) {
                            int LA16_1 = input.LA(2);

                            if ( (LA16_1=='/') ) {
                                alt16=2;
                            }
                            else if ( ((LA16_1>='\u0000' && LA16_1<='.')||(LA16_1>='0' && LA16_1<='\uFFFF')) ) {
                                alt16=1;
                            }


                        }
                        else if ( ((LA16_0>='\u0000' && LA16_0<=')')||(LA16_0>='+' && LA16_0<='\uFFFF')) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // InternalSTAlgorithmLexer.g:360:53: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop16;
                        }
                    } while (true);

                    match("*/"); 


                    }
                    break;
                case 2 :
                    // InternalSTAlgorithmLexer.g:360:62: '(*' ( options {greedy=false; } : . )* '*)'
                    {
                    match("(*"); 

                    // InternalSTAlgorithmLexer.g:360:67: ( options {greedy=false; } : . )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0=='*') ) {
                            int LA17_1 = input.LA(2);

                            if ( (LA17_1==')') ) {
                                alt17=2;
                            }
                            else if ( ((LA17_1>='\u0000' && LA17_1<='(')||(LA17_1>='*' && LA17_1<='\uFFFF')) ) {
                                alt17=1;
                            }


                        }
                        else if ( ((LA17_0>='\u0000' && LA17_0<=')')||(LA17_0>='+' && LA17_0<='\uFFFF')) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // InternalSTAlgorithmLexer.g:360:95: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop17;
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
            // InternalSTAlgorithmLexer.g:362:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalSTAlgorithmLexer.g:362:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalSTAlgorithmLexer.g:362:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>='\u0000' && LA19_0<='\t')||(LA19_0>='\u000B' && LA19_0<='\f')||(LA19_0>='\u000E' && LA19_0<='\uFFFF')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSTAlgorithmLexer.g:362:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop19;
                }
            } while (true);

            // InternalSTAlgorithmLexer.g:362:40: ( ( '\\r' )? '\\n' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='\n'||LA21_0=='\r') ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSTAlgorithmLexer.g:362:41: ( '\\r' )? '\\n'
                    {
                    // InternalSTAlgorithmLexer.g:362:41: ( '\\r' )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0=='\r') ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // InternalSTAlgorithmLexer.g:362:41: '\\r'
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
            // InternalSTAlgorithmLexer.g:364:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalSTAlgorithmLexer.g:364:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalSTAlgorithmLexer.g:364:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt22=0;
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='\t' && LA22_0<='\n')||LA22_0=='\r'||LA22_0==' ') ) {
                    alt22=1;
                }


                switch (alt22) {
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
            	    if ( cnt22 >= 1 ) break loop22;
                        EarlyExitException eee =
                            new EarlyExitException(22, input);
                        throw eee;
                }
                cnt22++;
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
            // InternalSTAlgorithmLexer.g:366:16: ( . )
            // InternalSTAlgorithmLexer.g:366:18: .
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
        // InternalSTAlgorithmLexer.g:1:8: ( END_FUNCTION_BLOCK | END_CONFIGURATION | END_TRANSITION | FUNCTION_BLOCK | LDATE_AND_TIME | CONFIGURATION | DATE_AND_TIME | END_ALGORITHM | END_INTERFACE | END_NAMESPACE | END_RESOURCE | INITIAL_STEP | LTIME_OF_DAY | VAR_EXTERNAL | END_PROGRAM | TIME_OF_DAY | END_ACTION | END_METHOD | END_REPEAT | END_STRUCT | IMPLEMENTS | NON_RETAIN | READ_WRITE | TRANSITION | VAR_ACCESS | VAR_CONFIG | VAR_GLOBAL | VAR_IN_OUT | VAR_OUTPUT | ALGORITHM | END_CLASS | END_WHILE | INTERFACE | NAMESPACE | PROTECTED | READ_ONLY | VAR_INPUT | ABSTRACT | CONSTANT | CONTINUE | END_CASE | END_STEP | END_TYPE | INTERNAL | INTERVAL | OVERRIDE | PRIORITY | RESOURCE | VAR_TEMP | END_FOR | END_VAR | EXTENDS | OVERLAP | PRIVATE | PROGRAM | WSTRING | ACTION | END_IF | METHOD | PUBLIC | REF_TO | REPEAT | RETAIN | RETURN | SINGLE | STRING | STRUCT | ARRAY | CLASS | DWORD | ELSIF | FALSE | FINAL | LDATE | LREAL | LTIME | LWORD | UDINT | ULINT | UNTIL | USINT | WCHAR | WHILE | BOOL | BYTE | CASE | CHAR | DATE | DINT | ELSE | EXIT | LINT | LTOD | NULL | REAL | SINT | STEP | THEN | THIS | TIME | TRUE | TYPE | UINT | WITH | WORD | AND | FOR | INT | LDT | MOD | NOT | REF | TOD | VAR | XOR | B | D_1 | L | W | X | AsteriskAsterisk | FullStopFullStop | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | DO | DT | IF | LD | LT | OF | ON | OR | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | D | T | LeftSquareBracket | RightSquareBracket | RULE_NON_DECIMAL | RULE_INT | RULE_NUMBER | RULE_TIME_VALUE | RULE_ID | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt23=167;
        alt23 = dfa23.predict(input);
        switch (alt23) {
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
                // InternalSTAlgorithmLexer.g:1:162: END_RESOURCE
                {
                mEND_RESOURCE(); 

                }
                break;
            case 12 :
                // InternalSTAlgorithmLexer.g:1:175: INITIAL_STEP
                {
                mINITIAL_STEP(); 

                }
                break;
            case 13 :
                // InternalSTAlgorithmLexer.g:1:188: LTIME_OF_DAY
                {
                mLTIME_OF_DAY(); 

                }
                break;
            case 14 :
                // InternalSTAlgorithmLexer.g:1:201: VAR_EXTERNAL
                {
                mVAR_EXTERNAL(); 

                }
                break;
            case 15 :
                // InternalSTAlgorithmLexer.g:1:214: END_PROGRAM
                {
                mEND_PROGRAM(); 

                }
                break;
            case 16 :
                // InternalSTAlgorithmLexer.g:1:226: TIME_OF_DAY
                {
                mTIME_OF_DAY(); 

                }
                break;
            case 17 :
                // InternalSTAlgorithmLexer.g:1:238: END_ACTION
                {
                mEND_ACTION(); 

                }
                break;
            case 18 :
                // InternalSTAlgorithmLexer.g:1:249: END_METHOD
                {
                mEND_METHOD(); 

                }
                break;
            case 19 :
                // InternalSTAlgorithmLexer.g:1:260: END_REPEAT
                {
                mEND_REPEAT(); 

                }
                break;
            case 20 :
                // InternalSTAlgorithmLexer.g:1:271: END_STRUCT
                {
                mEND_STRUCT(); 

                }
                break;
            case 21 :
                // InternalSTAlgorithmLexer.g:1:282: IMPLEMENTS
                {
                mIMPLEMENTS(); 

                }
                break;
            case 22 :
                // InternalSTAlgorithmLexer.g:1:293: NON_RETAIN
                {
                mNON_RETAIN(); 

                }
                break;
            case 23 :
                // InternalSTAlgorithmLexer.g:1:304: READ_WRITE
                {
                mREAD_WRITE(); 

                }
                break;
            case 24 :
                // InternalSTAlgorithmLexer.g:1:315: TRANSITION
                {
                mTRANSITION(); 

                }
                break;
            case 25 :
                // InternalSTAlgorithmLexer.g:1:326: VAR_ACCESS
                {
                mVAR_ACCESS(); 

                }
                break;
            case 26 :
                // InternalSTAlgorithmLexer.g:1:337: VAR_CONFIG
                {
                mVAR_CONFIG(); 

                }
                break;
            case 27 :
                // InternalSTAlgorithmLexer.g:1:348: VAR_GLOBAL
                {
                mVAR_GLOBAL(); 

                }
                break;
            case 28 :
                // InternalSTAlgorithmLexer.g:1:359: VAR_IN_OUT
                {
                mVAR_IN_OUT(); 

                }
                break;
            case 29 :
                // InternalSTAlgorithmLexer.g:1:370: VAR_OUTPUT
                {
                mVAR_OUTPUT(); 

                }
                break;
            case 30 :
                // InternalSTAlgorithmLexer.g:1:381: ALGORITHM
                {
                mALGORITHM(); 

                }
                break;
            case 31 :
                // InternalSTAlgorithmLexer.g:1:391: END_CLASS
                {
                mEND_CLASS(); 

                }
                break;
            case 32 :
                // InternalSTAlgorithmLexer.g:1:401: END_WHILE
                {
                mEND_WHILE(); 

                }
                break;
            case 33 :
                // InternalSTAlgorithmLexer.g:1:411: INTERFACE
                {
                mINTERFACE(); 

                }
                break;
            case 34 :
                // InternalSTAlgorithmLexer.g:1:421: NAMESPACE
                {
                mNAMESPACE(); 

                }
                break;
            case 35 :
                // InternalSTAlgorithmLexer.g:1:431: PROTECTED
                {
                mPROTECTED(); 

                }
                break;
            case 36 :
                // InternalSTAlgorithmLexer.g:1:441: READ_ONLY
                {
                mREAD_ONLY(); 

                }
                break;
            case 37 :
                // InternalSTAlgorithmLexer.g:1:451: VAR_INPUT
                {
                mVAR_INPUT(); 

                }
                break;
            case 38 :
                // InternalSTAlgorithmLexer.g:1:461: ABSTRACT
                {
                mABSTRACT(); 

                }
                break;
            case 39 :
                // InternalSTAlgorithmLexer.g:1:470: CONSTANT
                {
                mCONSTANT(); 

                }
                break;
            case 40 :
                // InternalSTAlgorithmLexer.g:1:479: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 41 :
                // InternalSTAlgorithmLexer.g:1:488: END_CASE
                {
                mEND_CASE(); 

                }
                break;
            case 42 :
                // InternalSTAlgorithmLexer.g:1:497: END_STEP
                {
                mEND_STEP(); 

                }
                break;
            case 43 :
                // InternalSTAlgorithmLexer.g:1:506: END_TYPE
                {
                mEND_TYPE(); 

                }
                break;
            case 44 :
                // InternalSTAlgorithmLexer.g:1:515: INTERNAL
                {
                mINTERNAL(); 

                }
                break;
            case 45 :
                // InternalSTAlgorithmLexer.g:1:524: INTERVAL
                {
                mINTERVAL(); 

                }
                break;
            case 46 :
                // InternalSTAlgorithmLexer.g:1:533: OVERRIDE
                {
                mOVERRIDE(); 

                }
                break;
            case 47 :
                // InternalSTAlgorithmLexer.g:1:542: PRIORITY
                {
                mPRIORITY(); 

                }
                break;
            case 48 :
                // InternalSTAlgorithmLexer.g:1:551: RESOURCE
                {
                mRESOURCE(); 

                }
                break;
            case 49 :
                // InternalSTAlgorithmLexer.g:1:560: VAR_TEMP
                {
                mVAR_TEMP(); 

                }
                break;
            case 50 :
                // InternalSTAlgorithmLexer.g:1:569: END_FOR
                {
                mEND_FOR(); 

                }
                break;
            case 51 :
                // InternalSTAlgorithmLexer.g:1:577: END_VAR
                {
                mEND_VAR(); 

                }
                break;
            case 52 :
                // InternalSTAlgorithmLexer.g:1:585: EXTENDS
                {
                mEXTENDS(); 

                }
                break;
            case 53 :
                // InternalSTAlgorithmLexer.g:1:593: OVERLAP
                {
                mOVERLAP(); 

                }
                break;
            case 54 :
                // InternalSTAlgorithmLexer.g:1:601: PRIVATE
                {
                mPRIVATE(); 

                }
                break;
            case 55 :
                // InternalSTAlgorithmLexer.g:1:609: PROGRAM
                {
                mPROGRAM(); 

                }
                break;
            case 56 :
                // InternalSTAlgorithmLexer.g:1:617: WSTRING
                {
                mWSTRING(); 

                }
                break;
            case 57 :
                // InternalSTAlgorithmLexer.g:1:625: ACTION
                {
                mACTION(); 

                }
                break;
            case 58 :
                // InternalSTAlgorithmLexer.g:1:632: END_IF
                {
                mEND_IF(); 

                }
                break;
            case 59 :
                // InternalSTAlgorithmLexer.g:1:639: METHOD
                {
                mMETHOD(); 

                }
                break;
            case 60 :
                // InternalSTAlgorithmLexer.g:1:646: PUBLIC
                {
                mPUBLIC(); 

                }
                break;
            case 61 :
                // InternalSTAlgorithmLexer.g:1:653: REF_TO
                {
                mREF_TO(); 

                }
                break;
            case 62 :
                // InternalSTAlgorithmLexer.g:1:660: REPEAT
                {
                mREPEAT(); 

                }
                break;
            case 63 :
                // InternalSTAlgorithmLexer.g:1:667: RETAIN
                {
                mRETAIN(); 

                }
                break;
            case 64 :
                // InternalSTAlgorithmLexer.g:1:674: RETURN
                {
                mRETURN(); 

                }
                break;
            case 65 :
                // InternalSTAlgorithmLexer.g:1:681: SINGLE
                {
                mSINGLE(); 

                }
                break;
            case 66 :
                // InternalSTAlgorithmLexer.g:1:688: STRING
                {
                mSTRING(); 

                }
                break;
            case 67 :
                // InternalSTAlgorithmLexer.g:1:695: STRUCT
                {
                mSTRUCT(); 

                }
                break;
            case 68 :
                // InternalSTAlgorithmLexer.g:1:702: ARRAY
                {
                mARRAY(); 

                }
                break;
            case 69 :
                // InternalSTAlgorithmLexer.g:1:708: CLASS
                {
                mCLASS(); 

                }
                break;
            case 70 :
                // InternalSTAlgorithmLexer.g:1:714: DWORD
                {
                mDWORD(); 

                }
                break;
            case 71 :
                // InternalSTAlgorithmLexer.g:1:720: ELSIF
                {
                mELSIF(); 

                }
                break;
            case 72 :
                // InternalSTAlgorithmLexer.g:1:726: FALSE
                {
                mFALSE(); 

                }
                break;
            case 73 :
                // InternalSTAlgorithmLexer.g:1:732: FINAL
                {
                mFINAL(); 

                }
                break;
            case 74 :
                // InternalSTAlgorithmLexer.g:1:738: LDATE
                {
                mLDATE(); 

                }
                break;
            case 75 :
                // InternalSTAlgorithmLexer.g:1:744: LREAL
                {
                mLREAL(); 

                }
                break;
            case 76 :
                // InternalSTAlgorithmLexer.g:1:750: LTIME
                {
                mLTIME(); 

                }
                break;
            case 77 :
                // InternalSTAlgorithmLexer.g:1:756: LWORD
                {
                mLWORD(); 

                }
                break;
            case 78 :
                // InternalSTAlgorithmLexer.g:1:762: UDINT
                {
                mUDINT(); 

                }
                break;
            case 79 :
                // InternalSTAlgorithmLexer.g:1:768: ULINT
                {
                mULINT(); 

                }
                break;
            case 80 :
                // InternalSTAlgorithmLexer.g:1:774: UNTIL
                {
                mUNTIL(); 

                }
                break;
            case 81 :
                // InternalSTAlgorithmLexer.g:1:780: USINT
                {
                mUSINT(); 

                }
                break;
            case 82 :
                // InternalSTAlgorithmLexer.g:1:786: WCHAR
                {
                mWCHAR(); 

                }
                break;
            case 83 :
                // InternalSTAlgorithmLexer.g:1:792: WHILE
                {
                mWHILE(); 

                }
                break;
            case 84 :
                // InternalSTAlgorithmLexer.g:1:798: BOOL
                {
                mBOOL(); 

                }
                break;
            case 85 :
                // InternalSTAlgorithmLexer.g:1:803: BYTE
                {
                mBYTE(); 

                }
                break;
            case 86 :
                // InternalSTAlgorithmLexer.g:1:808: CASE
                {
                mCASE(); 

                }
                break;
            case 87 :
                // InternalSTAlgorithmLexer.g:1:813: CHAR
                {
                mCHAR(); 

                }
                break;
            case 88 :
                // InternalSTAlgorithmLexer.g:1:818: DATE
                {
                mDATE(); 

                }
                break;
            case 89 :
                // InternalSTAlgorithmLexer.g:1:823: DINT
                {
                mDINT(); 

                }
                break;
            case 90 :
                // InternalSTAlgorithmLexer.g:1:828: ELSE
                {
                mELSE(); 

                }
                break;
            case 91 :
                // InternalSTAlgorithmLexer.g:1:833: EXIT
                {
                mEXIT(); 

                }
                break;
            case 92 :
                // InternalSTAlgorithmLexer.g:1:838: LINT
                {
                mLINT(); 

                }
                break;
            case 93 :
                // InternalSTAlgorithmLexer.g:1:843: LTOD
                {
                mLTOD(); 

                }
                break;
            case 94 :
                // InternalSTAlgorithmLexer.g:1:848: NULL
                {
                mNULL(); 

                }
                break;
            case 95 :
                // InternalSTAlgorithmLexer.g:1:853: REAL
                {
                mREAL(); 

                }
                break;
            case 96 :
                // InternalSTAlgorithmLexer.g:1:858: SINT
                {
                mSINT(); 

                }
                break;
            case 97 :
                // InternalSTAlgorithmLexer.g:1:863: STEP
                {
                mSTEP(); 

                }
                break;
            case 98 :
                // InternalSTAlgorithmLexer.g:1:868: THEN
                {
                mTHEN(); 

                }
                break;
            case 99 :
                // InternalSTAlgorithmLexer.g:1:873: THIS
                {
                mTHIS(); 

                }
                break;
            case 100 :
                // InternalSTAlgorithmLexer.g:1:878: TIME
                {
                mTIME(); 

                }
                break;
            case 101 :
                // InternalSTAlgorithmLexer.g:1:883: TRUE
                {
                mTRUE(); 

                }
                break;
            case 102 :
                // InternalSTAlgorithmLexer.g:1:888: TYPE
                {
                mTYPE(); 

                }
                break;
            case 103 :
                // InternalSTAlgorithmLexer.g:1:893: UINT
                {
                mUINT(); 

                }
                break;
            case 104 :
                // InternalSTAlgorithmLexer.g:1:898: WITH
                {
                mWITH(); 

                }
                break;
            case 105 :
                // InternalSTAlgorithmLexer.g:1:903: WORD
                {
                mWORD(); 

                }
                break;
            case 106 :
                // InternalSTAlgorithmLexer.g:1:908: AND
                {
                mAND(); 

                }
                break;
            case 107 :
                // InternalSTAlgorithmLexer.g:1:912: FOR
                {
                mFOR(); 

                }
                break;
            case 108 :
                // InternalSTAlgorithmLexer.g:1:916: INT
                {
                mINT(); 

                }
                break;
            case 109 :
                // InternalSTAlgorithmLexer.g:1:920: LDT
                {
                mLDT(); 

                }
                break;
            case 110 :
                // InternalSTAlgorithmLexer.g:1:924: MOD
                {
                mMOD(); 

                }
                break;
            case 111 :
                // InternalSTAlgorithmLexer.g:1:928: NOT
                {
                mNOT(); 

                }
                break;
            case 112 :
                // InternalSTAlgorithmLexer.g:1:932: REF
                {
                mREF(); 

                }
                break;
            case 113 :
                // InternalSTAlgorithmLexer.g:1:936: TOD
                {
                mTOD(); 

                }
                break;
            case 114 :
                // InternalSTAlgorithmLexer.g:1:940: VAR
                {
                mVAR(); 

                }
                break;
            case 115 :
                // InternalSTAlgorithmLexer.g:1:944: XOR
                {
                mXOR(); 

                }
                break;
            case 116 :
                // InternalSTAlgorithmLexer.g:1:948: B
                {
                mB(); 

                }
                break;
            case 117 :
                // InternalSTAlgorithmLexer.g:1:950: D_1
                {
                mD_1(); 

                }
                break;
            case 118 :
                // InternalSTAlgorithmLexer.g:1:954: L
                {
                mL(); 

                }
                break;
            case 119 :
                // InternalSTAlgorithmLexer.g:1:956: W
                {
                mW(); 

                }
                break;
            case 120 :
                // InternalSTAlgorithmLexer.g:1:958: X
                {
                mX(); 

                }
                break;
            case 121 :
                // InternalSTAlgorithmLexer.g:1:960: AsteriskAsterisk
                {
                mAsteriskAsterisk(); 

                }
                break;
            case 122 :
                // InternalSTAlgorithmLexer.g:1:977: FullStopFullStop
                {
                mFullStopFullStop(); 

                }
                break;
            case 123 :
                // InternalSTAlgorithmLexer.g:1:994: ColonEqualsSign
                {
                mColonEqualsSign(); 

                }
                break;
            case 124 :
                // InternalSTAlgorithmLexer.g:1:1010: LessThanSignEqualsSign
                {
                mLessThanSignEqualsSign(); 

                }
                break;
            case 125 :
                // InternalSTAlgorithmLexer.g:1:1033: LessThanSignGreaterThanSign
                {
                mLessThanSignGreaterThanSign(); 

                }
                break;
            case 126 :
                // InternalSTAlgorithmLexer.g:1:1061: EqualsSignGreaterThanSign
                {
                mEqualsSignGreaterThanSign(); 

                }
                break;
            case 127 :
                // InternalSTAlgorithmLexer.g:1:1087: GreaterThanSignEqualsSign
                {
                mGreaterThanSignEqualsSign(); 

                }
                break;
            case 128 :
                // InternalSTAlgorithmLexer.g:1:1113: AT
                {
                mAT(); 

                }
                break;
            case 129 :
                // InternalSTAlgorithmLexer.g:1:1116: BY
                {
                mBY(); 

                }
                break;
            case 130 :
                // InternalSTAlgorithmLexer.g:1:1119: DO
                {
                mDO(); 

                }
                break;
            case 131 :
                // InternalSTAlgorithmLexer.g:1:1122: DT
                {
                mDT(); 

                }
                break;
            case 132 :
                // InternalSTAlgorithmLexer.g:1:1125: IF
                {
                mIF(); 

                }
                break;
            case 133 :
                // InternalSTAlgorithmLexer.g:1:1128: LD
                {
                mLD(); 

                }
                break;
            case 134 :
                // InternalSTAlgorithmLexer.g:1:1131: LT
                {
                mLT(); 

                }
                break;
            case 135 :
                // InternalSTAlgorithmLexer.g:1:1134: OF
                {
                mOF(); 

                }
                break;
            case 136 :
                // InternalSTAlgorithmLexer.g:1:1137: ON
                {
                mON(); 

                }
                break;
            case 137 :
                // InternalSTAlgorithmLexer.g:1:1140: OR
                {
                mOR(); 

                }
                break;
            case 138 :
                // InternalSTAlgorithmLexer.g:1:1143: TO
                {
                mTO(); 

                }
                break;
            case 139 :
                // InternalSTAlgorithmLexer.g:1:1146: NumberSign
                {
                mNumberSign(); 

                }
                break;
            case 140 :
                // InternalSTAlgorithmLexer.g:1:1157: Ampersand
                {
                mAmpersand(); 

                }
                break;
            case 141 :
                // InternalSTAlgorithmLexer.g:1:1167: LeftParenthesis
                {
                mLeftParenthesis(); 

                }
                break;
            case 142 :
                // InternalSTAlgorithmLexer.g:1:1183: RightParenthesis
                {
                mRightParenthesis(); 

                }
                break;
            case 143 :
                // InternalSTAlgorithmLexer.g:1:1200: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 144 :
                // InternalSTAlgorithmLexer.g:1:1209: PlusSign
                {
                mPlusSign(); 

                }
                break;
            case 145 :
                // InternalSTAlgorithmLexer.g:1:1218: Comma
                {
                mComma(); 

                }
                break;
            case 146 :
                // InternalSTAlgorithmLexer.g:1:1224: HyphenMinus
                {
                mHyphenMinus(); 

                }
                break;
            case 147 :
                // InternalSTAlgorithmLexer.g:1:1236: FullStop
                {
                mFullStop(); 

                }
                break;
            case 148 :
                // InternalSTAlgorithmLexer.g:1:1245: Solidus
                {
                mSolidus(); 

                }
                break;
            case 149 :
                // InternalSTAlgorithmLexer.g:1:1253: Colon
                {
                mColon(); 

                }
                break;
            case 150 :
                // InternalSTAlgorithmLexer.g:1:1259: Semicolon
                {
                mSemicolon(); 

                }
                break;
            case 151 :
                // InternalSTAlgorithmLexer.g:1:1269: LessThanSign
                {
                mLessThanSign(); 

                }
                break;
            case 152 :
                // InternalSTAlgorithmLexer.g:1:1282: EqualsSign
                {
                mEqualsSign(); 

                }
                break;
            case 153 :
                // InternalSTAlgorithmLexer.g:1:1293: GreaterThanSign
                {
                mGreaterThanSign(); 

                }
                break;
            case 154 :
                // InternalSTAlgorithmLexer.g:1:1309: D
                {
                mD(); 

                }
                break;
            case 155 :
                // InternalSTAlgorithmLexer.g:1:1311: T
                {
                mT(); 

                }
                break;
            case 156 :
                // InternalSTAlgorithmLexer.g:1:1313: LeftSquareBracket
                {
                mLeftSquareBracket(); 

                }
                break;
            case 157 :
                // InternalSTAlgorithmLexer.g:1:1331: RightSquareBracket
                {
                mRightSquareBracket(); 

                }
                break;
            case 158 :
                // InternalSTAlgorithmLexer.g:1:1350: RULE_NON_DECIMAL
                {
                mRULE_NON_DECIMAL(); 

                }
                break;
            case 159 :
                // InternalSTAlgorithmLexer.g:1:1367: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 160 :
                // InternalSTAlgorithmLexer.g:1:1376: RULE_NUMBER
                {
                mRULE_NUMBER(); 

                }
                break;
            case 161 :
                // InternalSTAlgorithmLexer.g:1:1388: RULE_TIME_VALUE
                {
                mRULE_TIME_VALUE(); 

                }
                break;
            case 162 :
                // InternalSTAlgorithmLexer.g:1:1404: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 163 :
                // InternalSTAlgorithmLexer.g:1:1412: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 164 :
                // InternalSTAlgorithmLexer.g:1:1424: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 165 :
                // InternalSTAlgorithmLexer.g:1:1440: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 166 :
                // InternalSTAlgorithmLexer.g:1:1456: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 167 :
                // InternalSTAlgorithmLexer.g:1:1464: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA23 dfa23 = new DFA23(this);
    static final String DFA6_eotS =
        "\1\uffff\1\4\1\uffff\1\4\2\uffff";
    static final String DFA6_eofS =
        "\6\uffff";
    static final String DFA6_minS =
        "\4\60\2\uffff";
    static final String DFA6_maxS =
        "\1\71\1\145\1\71\1\145\2\uffff";
    static final String DFA6_acceptS =
        "\4\uffff\1\2\1\1";
    static final String DFA6_specialS =
        "\6\uffff}>";
    static final String[] DFA6_transitionS = {
            "\12\1",
            "\12\3\13\uffff\1\5\31\uffff\1\2\5\uffff\1\5",
            "\12\3",
            "\12\3\13\uffff\1\5\31\uffff\1\2\5\uffff\1\5",
            "",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "336:28: ( RULE_EXT_INT | RULE_INT )";
        }
    }
    static final String DFA9_eotS =
        "\6\uffff";
    static final String DFA9_eofS =
        "\6\uffff";
    static final String DFA9_minS =
        "\1\60\1\56\1\60\1\56\2\uffff";
    static final String DFA9_maxS =
        "\1\71\1\165\1\71\1\165\2\uffff";
    static final String DFA9_acceptS =
        "\4\uffff\1\1\1\2";
    static final String DFA9_specialS =
        "\6\uffff}>";
    static final String[] DFA9_transitionS = {
            "\12\1",
            "\1\5\1\uffff\12\3\12\uffff\1\4\3\uffff\1\4\4\uffff\2\4\4\uffff\1\4\1\uffff\1\4\11\uffff\1\2\4\uffff\1\4\3\uffff\1\4\4\uffff\2\4\4\uffff\1\4\1\uffff\1\4",
            "\12\3",
            "\1\5\1\uffff\12\3\12\uffff\1\4\3\uffff\1\4\4\uffff\2\4\4\uffff\1\4\1\uffff\1\4\11\uffff\1\2\4\uffff\1\4\3\uffff\1\4\4\uffff\2\4\4\uffff\1\4\1\uffff\1\4",
            "",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "340:27: ( RULE_INT | RULE_NUMBER )";
        }
    }
    static final String DFA23_eotS =
        "\1\uffff\4\63\1\106\2\63\1\120\13\63\1\57\1\170\1\172\1\174\1\177\1\u0081\1\u0083\2\uffff\1\u0087\4\uffff\1\u008d\3\uffff\4\u0094\1\57\1\uffff\2\57\2\uffff\3\63\1\uffff\4\63\1\u00a4\1\u00a7\12\63\1\u00b2\1\u00b3\1\uffff\2\63\1\u00b7\5\63\1\u00c0\1\uffff\11\63\1\u00cf\3\63\1\u00d4\1\u00d5\1\u00d6\17\63\1\u00e8\1\63\41\uffff\1\u0094\3\uffff\1\u0094\2\uffff\7\63\1\u00f3\1\63\1\u00f5\1\uffff\2\63\1\uffff\12\63\2\uffff\1\63\1\u0106\1\63\1\uffff\1\u0109\6\63\1\u0110\1\uffff\1\63\1\u0112\4\63\1\u0119\6\63\1\u0121\1\uffff\4\63\3\uffff\6\63\1\u012e\12\63\1\uffff\1\u013b\1\u013e\2\63\1\u014d\1\63\1\u014f\3\63\1\uffff\1\63\1\uffff\1\63\1\u0155\2\63\1\u0158\4\63\1\u015d\1\u015e\1\u0160\1\63\1\u0162\2\63\1\uffff\2\63\1\uffff\1\u016e\1\63\1\u0170\1\u0171\1\u0172\1\u0173\1\uffff\1\63\1\uffff\1\63\1\u0176\1\63\1\u0178\2\63\1\uffff\7\63\1\uffff\11\63\1\u018c\1\u018d\1\63\1\uffff\1\63\1\u0190\2\63\1\u0193\4\63\1\u0198\1\u0199\1\u019a\2\uffff\1\u013e\2\uffff\15\63\1\uffff\1\u01b0\1\uffff\1\63\1\u01b2\1\u01b3\1\u01b5\1\u01b7\1\uffff\1\u01b8\1\u01b9\1\uffff\3\63\1\u01bd\2\uffff\1\63\1\uffff\1\u01bf\1\uffff\13\63\1\uffff\1\63\4\uffff\2\63\1\uffff\1\63\1\uffff\10\63\1\u01da\10\63\1\u01e3\1\u01e4\2\uffff\2\63\1\uffff\2\63\1\uffff\1\u01e9\1\u01ea\1\u01eb\1\u01ec\4\uffff\1\u013e\12\63\1\u01f9\10\63\1\uffff\1\63\2\uffff\1\63\1\uffff\1\63\3\uffff\3\63\1\uffff\1\63\1\uffff\23\63\1\u021f\1\u0220\1\u0221\1\u0222\2\63\1\u0225\1\uffff\4\63\1\u022a\3\63\2\uffff\1\u022e\1\u022f\1\u0230\1\u0231\5\uffff\1\u013e\1\63\1\u0233\10\63\1\uffff\10\63\1\u0244\1\u0245\33\63\4\uffff\2\63\1\uffff\1\63\1\u0264\1\63\1\u0266\1\uffff\1\63\1\u0268\1\u0269\4\uffff\1\63\1\uffff\2\63\1\u026d\1\63\1\u026f\11\63\1\u0279\1\63\2\uffff\4\63\1\u027f\1\u0280\3\63\1\u0284\1\u0285\10\63\1\u028e\6\63\1\u0295\1\63\1\u0297\1\63\1\uffff\1\u0299\1\uffff\1\u029a\2\uffff\2\63\1\u029d\1\uffff\1\63\1\uffff\11\63\1\uffff\1\u02a8\4\63\2\uffff\2\63\1\u02af\2\uffff\6\63\1\u02b6\1\63\1\uffff\3\63\1\u02bb\1\63\1\u02bd\1\uffff\1\u02be\1\uffff\1\u02bf\2\uffff\2\63\1\uffff\2\63\1\u02c4\3\63\1\u02c8\1\63\1\u02ca\1\u02cb\1\uffff\6\63\1\uffff\1\u02d2\1\63\1\u02d4\1\u02d5\1\u02d6\1\u02d7\1\uffff\1\u02d8\1\63\1\u02da\1\u02db\1\uffff\1\u02dc\3\uffff\4\63\1\uffff\3\63\1\uffff\1\u02e4\2\uffff\6\63\1\uffff\1\63\5\uffff\1\u02ec\3\uffff\6\63\1\u02f3\1\uffff\2\63\1\u02f6\2\63\1\u02f9\1\u02fa\1\uffff\3\63\1\u02fe\1\u02ff\1\u0300\1\uffff\2\63\1\uffff\1\u0303\1\u0304\2\uffff\2\63\1\u0307\3\uffff\1\u0308\1\u0309\2\uffff\2\63\3\uffff\3\63\1\u030f\1\u0310\2\uffff";
    static final String DFA23_eofS =
        "\u0311\uffff";
    static final String DFA23_minS =
        "\1\0\1\114\1\101\1\104\1\101\1\60\1\106\1\101\1\60\1\101\1\105\1\102\1\122\1\106\1\103\1\105\1\111\1\104\2\117\1\102\1\52\1\56\2\75\1\76\1\75\2\uffff\1\52\4\uffff\1\52\3\uffff\2\43\2\56\1\101\1\uffff\2\0\2\uffff\1\104\1\111\1\123\1\uffff\1\116\1\114\1\116\1\122\2\60\1\105\1\117\2\116\1\101\1\123\1\101\1\124\1\117\1\116\2\60\1\uffff\1\111\1\120\1\60\1\122\1\115\1\101\1\105\1\120\1\60\1\uffff\1\116\1\115\1\114\1\101\1\107\1\123\1\124\1\122\1\104\1\60\1\111\1\102\1\105\3\60\1\124\1\110\1\111\1\124\1\122\1\124\1\104\1\116\1\105\2\111\1\124\1\111\1\116\1\117\1\60\1\122\40\uffff\1\60\1\56\1\uffff\1\60\1\uffff\1\43\2\uffff\1\137\1\105\1\124\1\105\1\103\1\123\1\101\1\60\1\124\1\60\1\uffff\1\115\1\104\1\uffff\1\101\1\122\1\124\1\106\1\123\1\105\1\122\1\105\1\122\1\124\2\uffff\1\124\1\60\1\114\1\uffff\1\60\1\105\1\116\1\105\1\116\1\123\1\105\1\60\1\uffff\1\137\1\60\1\105\1\114\1\104\1\117\1\60\1\105\1\101\1\117\1\124\1\111\1\101\1\60\1\uffff\1\107\1\117\1\114\1\122\3\uffff\1\122\1\101\1\114\1\110\1\104\1\110\1\60\1\107\1\111\1\120\2\116\1\111\1\116\1\124\1\114\1\105\1\uffff\2\60\1\101\1\116\1\60\1\106\1\60\1\124\1\105\1\114\1\uffff\1\105\1\uffff\1\105\1\60\1\114\1\104\1\60\1\111\1\124\1\111\1\123\3\60\1\104\1\60\1\111\1\122\1\uffff\1\105\1\101\1\uffff\1\60\1\123\4\60\1\uffff\1\122\1\uffff\1\123\1\60\1\137\1\60\1\125\1\124\1\uffff\1\101\1\111\3\122\1\117\1\131\1\uffff\1\105\2\122\1\101\1\111\1\114\1\111\1\122\1\105\2\60\1\117\1\uffff\1\114\1\60\1\116\1\103\1\60\2\124\1\114\1\124\3\60\1\uffff\2\60\1\uffff\1\53\1\117\1\101\1\122\1\103\1\106\1\101\1\105\1\122\1\105\1\124\1\110\1\101\1\104\1\uffff\1\60\1\uffff\1\111\4\60\1\uffff\2\60\1\uffff\1\107\1\101\1\116\1\60\2\uffff\1\101\1\uffff\1\60\1\uffff\1\101\1\106\1\115\1\130\1\103\1\117\1\114\1\116\1\125\1\105\1\117\1\uffff\1\111\4\uffff\1\105\1\120\1\uffff\1\117\1\uffff\1\122\1\117\1\124\2\116\1\111\1\101\1\116\1\60\1\103\1\101\1\111\1\124\1\103\1\111\1\101\1\116\2\60\2\uffff\1\104\1\105\1\uffff\1\107\1\124\1\uffff\4\60\3\uffff\2\60\1\116\1\122\1\116\1\101\1\123\1\101\1\120\1\107\2\124\1\60\1\115\1\120\1\117\1\124\1\105\1\111\1\122\1\123\1\uffff\1\117\2\uffff\1\101\1\uffff\1\117\3\uffff\1\125\1\116\1\125\1\uffff\1\116\1\uffff\1\114\3\101\1\105\1\124\1\103\1\116\1\117\1\120\1\124\1\115\1\106\2\124\1\101\1\122\1\116\1\103\4\60\1\124\1\103\1\60\1\uffff\1\124\1\115\1\124\1\105\1\60\1\104\1\120\1\107\2\uffff\4\60\4\uffff\2\60\1\103\1\60\1\106\1\123\1\105\1\116\1\105\1\117\1\111\1\105\1\uffff\1\105\1\117\1\105\1\107\1\110\1\125\1\120\1\114\2\60\2\116\1\106\1\122\1\124\1\105\1\104\1\137\1\103\2\114\1\116\2\105\1\106\1\102\1\117\1\125\2\120\1\137\1\111\1\101\1\103\1\111\1\114\1\105\4\uffff\1\110\1\124\1\uffff\1\105\1\60\1\131\1\60\1\uffff\1\105\2\60\4\uffff\1\124\1\uffff\1\111\1\123\1\60\1\123\1\60\1\122\1\117\1\122\1\123\1\125\1\101\1\122\1\117\1\103\1\60\1\105\2\uffff\1\137\1\104\1\137\1\101\2\60\1\137\1\123\1\105\2\60\1\124\1\122\1\123\1\111\1\101\1\125\1\124\1\125\1\60\1\104\1\117\1\111\1\105\1\124\1\131\1\60\1\115\1\60\1\104\1\uffff\1\60\1\uffff\1\60\2\uffff\1\111\1\107\1\60\1\uffff\1\111\1\uffff\1\111\1\116\1\106\1\120\1\122\1\124\1\101\1\104\1\124\1\uffff\1\60\1\102\1\137\1\104\1\124\2\uffff\2\124\1\60\2\uffff\1\123\1\116\1\123\1\107\1\114\1\124\1\60\1\124\1\uffff\1\101\2\116\1\60\1\105\1\60\1\uffff\1\60\1\uffff\1\60\2\uffff\1\117\1\125\1\uffff\2\124\1\60\2\101\1\103\1\60\1\115\2\60\1\uffff\1\114\1\124\1\101\2\111\1\105\1\uffff\1\60\1\101\4\60\1\uffff\1\60\1\131\2\60\1\uffff\1\60\3\uffff\1\116\1\122\1\111\1\110\1\uffff\2\103\1\105\1\uffff\1\60\2\uffff\1\117\1\111\1\131\1\117\1\115\1\120\1\uffff\1\114\5\uffff\1\60\3\uffff\1\137\1\101\1\117\1\115\2\105\1\60\1\uffff\1\103\1\115\1\60\1\116\1\105\2\60\1\uffff\1\102\1\124\1\116\3\60\1\uffff\1\113\1\105\1\uffff\2\60\2\uffff\1\114\1\111\1\60\3\uffff\2\60\2\uffff\2\117\3\uffff\1\103\1\116\1\113\2\60\2\uffff";
    static final String DFA23_maxS =
        "\1\uffff\1\170\1\165\1\167\1\157\1\172\1\156\1\141\1\172\1\165\1\145\1\164\1\165\1\166\1\163\1\157\1\164\1\163\1\171\1\157\1\170\1\52\1\56\1\75\2\76\1\75\2\uffff\1\52\4\uffff\1\57\3\uffff\4\165\1\172\1\uffff\2\uffff\2\uffff\1\144\1\164\1\163\1\uffff\1\156\1\154\1\156\1\162\2\172\1\145\1\157\2\156\1\141\1\163\1\141\1\164\1\157\1\156\2\172\1\uffff\1\164\1\160\1\172\1\162\1\155\1\165\1\151\1\160\1\172\1\uffff\1\164\1\155\1\154\1\164\1\147\1\163\1\164\1\162\1\144\1\172\1\157\1\142\1\145\3\172\1\164\1\150\1\151\1\164\1\162\1\164\1\144\1\156\1\162\2\151\1\164\1\151\1\156\1\157\1\172\1\162\40\uffff\1\71\1\165\1\uffff\1\71\1\uffff\1\165\2\uffff\1\137\1\145\1\164\1\151\1\143\1\163\1\141\1\172\1\164\1\172\1\uffff\1\155\1\144\1\uffff\1\141\1\162\2\164\1\163\1\145\1\162\1\145\1\162\1\164\2\uffff\1\164\1\172\1\154\1\uffff\1\172\1\145\1\156\1\145\1\156\1\163\1\145\1\172\1\uffff\1\137\1\172\1\145\2\154\1\157\1\172\1\145\1\165\1\157\1\164\1\151\1\141\1\172\1\uffff\1\164\1\166\1\154\1\162\3\uffff\1\162\1\141\1\154\1\150\1\144\1\150\1\172\1\164\1\165\1\160\2\156\1\151\1\156\1\164\1\154\1\145\1\uffff\1\172\1\165\1\167\1\156\1\172\1\146\1\172\1\164\1\145\1\154\1\uffff\1\145\1\uffff\1\145\1\172\1\154\1\144\1\172\1\151\1\164\1\151\1\163\3\172\1\144\1\172\1\151\1\162\1\uffff\1\145\1\164\1\uffff\1\172\1\163\4\172\1\uffff\1\162\1\uffff\1\163\1\172\1\137\1\172\1\165\1\164\1\uffff\1\141\1\151\3\162\1\157\1\171\1\uffff\1\145\2\162\1\141\1\151\1\162\1\151\1\162\1\145\2\172\1\157\1\uffff\1\154\1\172\1\156\1\143\1\172\2\164\1\154\1\164\3\172\1\uffff\1\71\1\165\1\uffff\1\71\1\165\1\157\1\171\1\154\1\156\1\141\1\145\1\162\1\145\1\164\1\150\1\141\1\144\1\uffff\1\172\1\uffff\1\151\4\172\1\uffff\2\172\1\uffff\1\147\1\141\1\156\1\172\2\uffff\1\141\1\uffff\1\172\1\uffff\1\141\1\166\1\155\1\170\1\143\1\157\1\154\1\156\1\165\1\145\1\157\1\uffff\1\151\4\uffff\1\145\1\160\1\uffff\1\167\1\uffff\1\162\1\157\1\164\2\156\1\151\1\141\1\156\1\172\1\143\1\141\1\151\1\164\1\143\1\151\1\141\1\156\2\172\2\uffff\1\144\1\145\1\uffff\1\147\1\164\1\uffff\4\172\3\uffff\1\71\1\165\1\156\1\162\1\156\1\141\1\163\1\141\1\160\1\147\2\164\1\172\1\155\1\163\1\157\1\164\1\162\1\151\1\162\1\163\1\uffff\1\157\2\uffff\1\141\1\uffff\1\157\3\uffff\1\165\1\156\1\165\1\uffff\1\156\1\uffff\1\154\3\141\1\145\1\164\1\143\1\156\1\157\1\160\1\164\1\155\1\146\2\164\1\141\1\162\1\156\1\143\4\172\1\164\1\143\1\172\1\uffff\1\164\1\155\1\164\1\145\1\172\1\144\1\160\1\147\2\uffff\4\172\4\uffff\1\71\1\165\1\143\1\172\1\146\1\163\1\145\1\156\1\145\1\157\1\151\1\145\1\uffff\1\145\1\157\1\145\1\147\1\150\1\165\1\160\1\154\2\172\2\156\1\146\1\162\1\164\1\145\1\144\1\137\1\143\2\154\1\156\2\145\1\146\1\142\1\157\1\165\2\160\1\137\1\151\1\141\1\143\1\151\1\154\1\145\4\uffff\1\150\1\164\1\uffff\1\145\1\172\1\171\1\172\1\uffff\1\145\2\172\4\uffff\1\164\1\uffff\1\151\1\163\1\172\1\163\1\172\1\162\1\157\1\162\1\163\1\165\1\141\1\162\1\157\1\143\1\172\1\145\2\uffff\1\137\1\144\1\137\1\141\2\172\1\137\1\163\1\145\2\172\1\164\1\162\1\163\1\151\1\141\1\165\1\164\1\165\1\172\1\144\1\157\1\151\1\145\1\164\1\171\1\172\1\155\1\172\1\144\1\uffff\1\172\1\uffff\1\172\2\uffff\1\151\1\147\1\172\1\uffff\1\151\1\uffff\1\151\1\156\1\146\1\160\1\162\1\164\1\141\1\144\1\164\1\uffff\1\172\1\142\1\137\1\144\1\164\2\uffff\2\164\1\172\2\uffff\1\163\1\156\1\163\1\147\1\154\1\164\1\172\1\164\1\uffff\1\141\2\156\1\172\1\145\1\172\1\uffff\1\172\1\uffff\1\172\2\uffff\1\157\1\165\1\uffff\2\164\1\172\2\141\1\143\1\172\1\155\2\172\1\uffff\1\154\1\164\1\141\2\151\1\145\1\uffff\1\172\1\141\4\172\1\uffff\1\172\1\171\2\172\1\uffff\1\172\3\uffff\1\156\1\162\1\151\1\150\1\uffff\2\143\1\145\1\uffff\1\172\2\uffff\1\157\1\151\1\171\1\157\1\155\1\160\1\uffff\1\154\5\uffff\1\172\3\uffff\1\137\1\141\1\157\1\155\2\145\1\172\1\uffff\1\143\1\155\1\172\1\156\1\145\2\172\1\uffff\1\142\1\164\1\156\3\172\1\uffff\1\153\1\145\1\uffff\2\172\2\uffff\1\154\1\151\1\172\3\uffff\2\172\2\uffff\2\157\3\uffff\1\143\1\156\1\153\2\172\2\uffff";
    static final String DFA23_acceptS =
        "\33\uffff\1\u008b\1\u008c\1\uffff\1\u008e\1\u0090\1\u0091\1\u0092\1\uffff\1\u0096\1\u009c\1\u009d\5\uffff\1\u00a2\2\uffff\1\u00a6\1\u00a7\3\uffff\1\u00a2\22\uffff\1\u009a\11\uffff\1\u009b\41\uffff\1\164\1\165\1\166\1\167\1\170\1\171\1\u008f\1\172\1\u0093\1\173\1\u0095\1\174\1\175\1\u0097\1\176\1\u0098\1\177\1\u0099\1\u008b\1\u008c\1\u00a4\1\u008d\1\u008e\1\u0090\1\u0091\1\u0092\1\u00a5\1\u0094\1\u0096\1\u009c\1\u009d\1\u009e\2\uffff\1\u009f\1\uffff\1\u00a1\1\uffff\1\u00a3\1\u00a6\12\uffff\1\u0085\2\uffff\1\u0086\12\uffff\1\u0082\1\u0083\3\uffff\1\u0084\10\uffff\1\u008a\16\uffff\1\u0080\4\uffff\1\u0087\1\u0088\1\u0089\21\uffff\1\u0081\12\uffff\1\153\1\uffff\1\155\20\uffff\1\154\2\uffff\1\162\6\uffff\1\161\1\uffff\1\157\6\uffff\1\160\7\uffff\1\152\14\uffff\1\156\14\uffff\1\163\2\uffff\1\u00a0\16\uffff\1\133\1\uffff\1\132\5\uffff\1\135\2\uffff\1\134\4\uffff\1\126\1\127\1\uffff\1\130\1\uffff\1\131\13\uffff\1\144\1\uffff\1\145\1\142\1\143\1\146\2\uffff\1\136\1\uffff\1\137\23\uffff\1\150\1\151\2\uffff\1\140\2\uffff\1\141\4\uffff\1\147\1\124\1\125\25\uffff\1\107\1\uffff\1\110\1\111\1\uffff\1\112\1\uffff\1\114\1\113\1\115\3\uffff\1\105\1\uffff\1\106\32\uffff\1\104\10\uffff\1\122\1\123\4\uffff\1\116\1\117\1\120\1\121\14\uffff\1\72\45\uffff\1\75\1\76\1\77\1\100\2\uffff\1\71\4\uffff\1\74\3\uffff\1\73\1\101\1\102\1\103\1\uffff\1\62\20\uffff\1\63\1\64\36\uffff\1\67\1\uffff\1\66\1\uffff\1\65\1\70\3\uffff\1\51\1\uffff\1\53\11\uffff\1\52\5\uffff\1\47\1\50\3\uffff\1\54\1\55\10\uffff\1\61\6\uffff\1\60\1\uffff\1\46\1\uffff\1\57\1\56\2\uffff\1\37\12\uffff\1\40\6\uffff\1\41\6\uffff\1\45\4\uffff\1\42\1\uffff\1\44\1\36\1\43\4\uffff\1\21\3\uffff\1\23\1\uffff\1\22\1\24\6\uffff\1\25\1\uffff\1\31\1\32\1\33\1\34\1\35\1\uffff\1\30\1\26\1\27\7\uffff\1\17\7\uffff\1\20\6\uffff\1\13\2\uffff\1\15\2\uffff\1\14\1\16\3\uffff\1\10\1\11\1\12\2\uffff\1\6\1\7\2\uffff\1\3\1\4\1\5\5\uffff\1\2\1\1";
    static final String DFA23_specialS =
        "\1\1\53\uffff\1\2\1\0\u02e3\uffff}>";
    static final String[] DFA23_transitionS = {
            "\11\57\2\56\2\57\1\56\22\57\1\56\1\57\1\54\1\33\1\57\1\24\1\34\1\55\1\35\1\36\1\25\1\37\1\40\1\41\1\26\1\42\1\51\1\50\1\46\5\51\1\47\1\51\1\27\1\43\1\30\1\31\1\32\2\57\1\13\1\22\1\4\1\5\1\1\1\2\2\53\1\6\2\53\1\3\1\17\1\11\1\15\1\14\1\53\1\12\1\20\1\10\1\21\1\7\1\16\1\23\2\53\1\44\1\57\1\45\1\52\1\53\1\57\1\13\1\22\1\4\1\5\1\1\1\2\2\53\1\6\2\53\1\3\1\17\1\11\1\15\1\14\1\53\1\12\1\20\1\10\1\21\1\7\1\16\1\23\2\53\uff85\57",
            "\1\62\1\uffff\1\60\11\uffff\1\61\23\uffff\1\62\1\uffff\1\60\11\uffff\1\61",
            "\1\65\7\uffff\1\66\5\uffff\1\67\5\uffff\1\64\13\uffff\1\65\7\uffff\1\66\5\uffff\1\67\5\uffff\1\64",
            "\1\70\4\uffff\1\74\10\uffff\1\72\1\uffff\1\71\2\uffff\1\73\14\uffff\1\70\4\uffff\1\74\10\uffff\1\72\1\uffff\1\71\2\uffff\1\73",
            "\1\77\6\uffff\1\100\3\uffff\1\76\2\uffff\1\75\21\uffff\1\77\6\uffff\1\100\3\uffff\1\76\2\uffff\1\75",
            "\12\63\7\uffff\1\101\7\63\1\103\5\63\1\104\4\63\1\105\2\63\1\102\3\63\4\uffff\1\63\1\uffff\1\101\7\63\1\103\5\63\1\104\4\63\1\105\2\63\1\102\3\63",
            "\1\111\6\uffff\1\110\1\107\27\uffff\1\111\6\uffff\1\110\1\107",
            "\1\112\37\uffff\1\112",
            "\12\63\7\uffff\7\63\1\115\1\113\5\63\1\117\2\63\1\114\6\63\1\116\1\63\4\uffff\1\63\1\uffff\7\63\1\115\1\113\5\63\1\117\2\63\1\114\6\63\1\116\1\63",
            "\1\122\15\uffff\1\121\5\uffff\1\123\13\uffff\1\122\15\uffff\1\121\5\uffff\1\123",
            "\1\124\37\uffff\1\124",
            "\1\126\1\127\10\uffff\1\125\1\uffff\1\131\3\uffff\1\130\1\uffff\1\132\15\uffff\1\126\1\127\10\uffff\1\125\1\uffff\1\131\3\uffff\1\130\1\uffff\1\132",
            "\1\133\2\uffff\1\134\34\uffff\1\133\2\uffff\1\134",
            "\1\136\7\uffff\1\137\3\uffff\1\140\3\uffff\1\135\17\uffff\1\136\7\uffff\1\137\3\uffff\1\140\3\uffff\1\135",
            "\1\142\4\uffff\1\143\1\144\5\uffff\1\145\3\uffff\1\141\17\uffff\1\142\4\uffff\1\143\1\144\5\uffff\1\145\3\uffff\1\141",
            "\1\146\11\uffff\1\147\25\uffff\1\146\11\uffff\1\147",
            "\1\150\12\uffff\1\151\24\uffff\1\150\12\uffff\1\151",
            "\1\152\4\uffff\1\156\2\uffff\1\153\1\uffff\1\154\4\uffff\1\155\20\uffff\1\152\4\uffff\1\156\2\uffff\1\153\1\uffff\1\154\4\uffff\1\155",
            "\1\157\11\uffff\1\160\25\uffff\1\157\11\uffff\1\160",
            "\1\161\37\uffff\1\161",
            "\1\162\1\uffff\1\163\7\uffff\1\164\12\uffff\1\165\1\166\11\uffff\1\162\1\uffff\1\163\7\uffff\1\164\12\uffff\1\165\1\166",
            "\1\167",
            "\1\171",
            "\1\173",
            "\1\175\1\176",
            "\1\u0080",
            "\1\u0082",
            "",
            "",
            "\1\u0086",
            "",
            "",
            "",
            "",
            "\1\u0086\4\uffff\1\u008c",
            "",
            "",
            "",
            "\1\u0091\12\uffff\1\u0095\1\uffff\12\u0093\12\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u0092\4\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "\1\u0091\12\uffff\1\u0095\1\uffff\12\u0093\12\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u0092\4\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "\1\u0095\1\uffff\6\u0093\1\u0097\3\u0093\12\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u0092\4\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "\1\u0095\1\uffff\12\u0093\12\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u0092\4\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\0\u0098",
            "\0\u0098",
            "",
            "",
            "\1\u009a\37\uffff\1\u009a",
            "\1\u009c\12\uffff\1\u009b\24\uffff\1\u009c\12\uffff\1\u009b",
            "\1\u009d\37\uffff\1\u009d",
            "",
            "\1\u009e\37\uffff\1\u009e",
            "\1\u009f\37\uffff\1\u009f",
            "\1\u00a0\37\uffff\1\u00a0",
            "\1\u00a1\37\uffff\1\u00a1",
            "\12\63\7\uffff\1\u00a2\22\63\1\u00a3\6\63\4\uffff\1\63\1\uffff\1\u00a2\22\63\1\u00a3\6\63",
            "\12\63\7\uffff\10\63\1\u00a5\5\63\1\u00a6\13\63\4\uffff\1\63\1\uffff\10\63\1\u00a5\5\63\1\u00a6\13\63",
            "\1\u00a8\37\uffff\1\u00a8",
            "\1\u00a9\37\uffff\1\u00a9",
            "\1\u00aa\37\uffff\1\u00aa",
            "\1\u00ab\37\uffff\1\u00ab",
            "\1\u00ac\37\uffff\1\u00ac",
            "\1\u00ad\37\uffff\1\u00ad",
            "\1\u00ae\37\uffff\1\u00ae",
            "\1\u00af\37\uffff\1\u00af",
            "\1\u00b0\37\uffff\1\u00b0",
            "\1\u00b1\37\uffff\1\u00b1",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u00b4\12\uffff\1\u00b5\24\uffff\1\u00b4\12\uffff\1\u00b5",
            "\1\u00b6\37\uffff\1\u00b6",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u00b8\37\uffff\1\u00b8",
            "\1\u00b9\37\uffff\1\u00b9",
            "\1\u00ba\23\uffff\1\u00bb\13\uffff\1\u00ba\23\uffff\1\u00bb",
            "\1\u00bc\3\uffff\1\u00bd\33\uffff\1\u00bc\3\uffff\1\u00bd",
            "\1\u00be\37\uffff\1\u00be",
            "\12\63\7\uffff\3\63\1\u00bf\26\63\4\uffff\1\63\1\uffff\3\63\1\u00bf\26\63",
            "",
            "\1\u00c1\5\uffff\1\u00c2\31\uffff\1\u00c1\5\uffff\1\u00c2",
            "\1\u00c3\37\uffff\1\u00c3",
            "\1\u00c4\37\uffff\1\u00c4",
            "\1\u00c5\4\uffff\1\u00c7\11\uffff\1\u00c8\2\uffff\1\u00c6\1\u00c9\14\uffff\1\u00c5\4\uffff\1\u00c7\11\uffff\1\u00c8\2\uffff\1\u00c6\1\u00c9",
            "\1\u00ca\37\uffff\1\u00ca",
            "\1\u00cb\37\uffff\1\u00cb",
            "\1\u00cc\37\uffff\1\u00cc",
            "\1\u00cd\37\uffff\1\u00cd",
            "\1\u00ce\37\uffff\1\u00ce",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u00d1\5\uffff\1\u00d0\31\uffff\1\u00d1\5\uffff\1\u00d0",
            "\1\u00d2\37\uffff\1\u00d2",
            "\1\u00d3\37\uffff\1\u00d3",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u00d7\37\uffff\1\u00d7",
            "\1\u00d8\37\uffff\1\u00d8",
            "\1\u00d9\37\uffff\1\u00d9",
            "\1\u00da\37\uffff\1\u00da",
            "\1\u00db\37\uffff\1\u00db",
            "\1\u00dc\37\uffff\1\u00dc",
            "\1\u00dd\37\uffff\1\u00dd",
            "\1\u00de\37\uffff\1\u00de",
            "\1\u00e0\14\uffff\1\u00df\22\uffff\1\u00e0\14\uffff\1\u00df",
            "\1\u00e1\37\uffff\1\u00e1",
            "\1\u00e2\37\uffff\1\u00e2",
            "\1\u00e3\37\uffff\1\u00e3",
            "\1\u00e4\37\uffff\1\u00e4",
            "\1\u00e5\37\uffff\1\u00e5",
            "\1\u00e6\37\uffff\1\u00e6",
            "\12\63\7\uffff\23\63\1\u00e7\6\63\4\uffff\1\63\1\uffff\23\63\1\u00e7\6\63",
            "\1\u00e9\37\uffff\1\u00e9",
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
            "\12\u0093",
            "\1\u0095\1\uffff\12\u0093\12\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u0092\4\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "",
            "\12\u00ea",
            "",
            "\1\u0091\12\uffff\1\u0095\1\uffff\12\u0093\12\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u0092\4\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "",
            "",
            "\1\u00eb",
            "\1\u00ec\37\uffff\1\u00ec",
            "\1\u00ed\37\uffff\1\u00ed",
            "\1\u00ef\3\uffff\1\u00ee\33\uffff\1\u00ef\3\uffff\1\u00ee",
            "\1\u00f0\37\uffff\1\u00f0",
            "\1\u00f1\37\uffff\1\u00f1",
            "\1\u00f2\37\uffff\1\u00f2",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u00f4\37\uffff\1\u00f4",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u00f6\37\uffff\1\u00f6",
            "\1\u00f7\37\uffff\1\u00f7",
            "",
            "\1\u00f8\37\uffff\1\u00f8",
            "\1\u00f9\37\uffff\1\u00f9",
            "\1\u00fa\37\uffff\1\u00fa",
            "\1\u00fb\14\uffff\1\u00fc\1\u00fd\21\uffff\1\u00fb\14\uffff\1\u00fc\1\u00fd",
            "\1\u00fe\37\uffff\1\u00fe",
            "\1\u00ff\37\uffff\1\u00ff",
            "\1\u0100\37\uffff\1\u0100",
            "\1\u0101\37\uffff\1\u0101",
            "\1\u0102\37\uffff\1\u0102",
            "\1\u0103\37\uffff\1\u0103",
            "",
            "",
            "\1\u0104\37\uffff\1\u0104",
            "\12\63\7\uffff\4\63\1\u0105\25\63\4\uffff\1\63\1\uffff\4\63\1\u0105\25\63",
            "\1\u0107\37\uffff\1\u0107",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\u0108\1\uffff\32\63",
            "\1\u010a\37\uffff\1\u010a",
            "\1\u010b\37\uffff\1\u010b",
            "\1\u010c\37\uffff\1\u010c",
            "\1\u010d\37\uffff\1\u010d",
            "\1\u010e\37\uffff\1\u010e",
            "\1\u010f\37\uffff\1\u010f",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u0111",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0113\37\uffff\1\u0113",
            "\1\u0114\37\uffff\1\u0114",
            "\1\u0115\7\uffff\1\u0116\27\uffff\1\u0115\7\uffff\1\u0116",
            "\1\u0117\37\uffff\1\u0117",
            "\12\63\7\uffff\32\63\4\uffff\1\u0118\1\uffff\32\63",
            "\1\u011a\37\uffff\1\u011a",
            "\1\u011b\23\uffff\1\u011c\13\uffff\1\u011b\23\uffff\1\u011c",
            "\1\u011d\37\uffff\1\u011d",
            "\1\u011e\37\uffff\1\u011e",
            "\1\u011f\37\uffff\1\u011f",
            "\1\u0120\37\uffff\1\u0120",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u0123\14\uffff\1\u0122\22\uffff\1\u0123\14\uffff\1\u0122",
            "\1\u0124\6\uffff\1\u0125\30\uffff\1\u0124\6\uffff\1\u0125",
            "\1\u0126\37\uffff\1\u0126",
            "\1\u0127\37\uffff\1\u0127",
            "",
            "",
            "",
            "\1\u0128\37\uffff\1\u0128",
            "\1\u0129\37\uffff\1\u0129",
            "\1\u012a\37\uffff\1\u012a",
            "\1\u012b\37\uffff\1\u012b",
            "\1\u012c\37\uffff\1\u012c",
            "\1\u012d\37\uffff\1\u012d",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u012f\14\uffff\1\u0130\22\uffff\1\u012f\14\uffff\1\u0130",
            "\1\u0131\13\uffff\1\u0132\23\uffff\1\u0131\13\uffff\1\u0132",
            "\1\u0133\37\uffff\1\u0133",
            "\1\u0134\37\uffff\1\u0134",
            "\1\u0135\37\uffff\1\u0135",
            "\1\u0136\37\uffff\1\u0136",
            "\1\u0137\37\uffff\1\u0137",
            "\1\u0138\37\uffff\1\u0138",
            "\1\u0139\37\uffff\1\u0139",
            "\1\u013a\37\uffff\1\u013a",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\u013d\12\uffff\1\u0096\1\u013f\2\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u013c\4\uffff\1\u0096\1\u013f\2\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "\1\u0143\1\uffff\1\u0141\2\uffff\1\u0140\2\uffff\1\u0144\3\uffff\1\u0148\1\u0145\1\uffff\1\u0147\1\uffff\1\u0146\1\u0149\1\u0142\1\uffff\1\u014b\1\u014a\11\uffff\1\u0143\1\uffff\1\u0141\2\uffff\1\u0140\2\uffff\1\u0144\3\uffff\1\u0148\1\u0145\1\uffff\1\u0147\1\uffff\1\u0146\1\u0149\1\u0142\1\uffff\1\u014b\1\u014a",
            "\1\u014c\37\uffff\1\u014c",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u014e\37\uffff\1\u014e",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0150\37\uffff\1\u0150",
            "\1\u0151\37\uffff\1\u0151",
            "\1\u0152\37\uffff\1\u0152",
            "",
            "\1\u0153\37\uffff\1\u0153",
            "",
            "\1\u0154\37\uffff\1\u0154",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0156\37\uffff\1\u0156",
            "\1\u0157\37\uffff\1\u0157",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0159\37\uffff\1\u0159",
            "\1\u015a\37\uffff\1\u015a",
            "\1\u015b\37\uffff\1\u015b",
            "\1\u015c\37\uffff\1\u015c",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\u015f\1\uffff\32\63",
            "\1\u0161\37\uffff\1\u0161",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0163\37\uffff\1\u0163",
            "\1\u0164\37\uffff\1\u0164",
            "",
            "\1\u0165\37\uffff\1\u0165",
            "\1\u0167\1\uffff\1\u0168\1\uffff\1\u0166\1\uffff\1\u0169\1\uffff\1\u016a\5\uffff\1\u016b\4\uffff\1\u016c\14\uffff\1\u0167\1\uffff\1\u0168\1\uffff\1\u0166\1\uffff\1\u0169\1\uffff\1\u016a\5\uffff\1\u016b\4\uffff\1\u016c",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\u016d\1\uffff\32\63",
            "\1\u016f\37\uffff\1\u016f",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u0174\37\uffff\1\u0174",
            "",
            "\1\u0175\37\uffff\1\u0175",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0177",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0179\37\uffff\1\u0179",
            "\1\u017a\37\uffff\1\u017a",
            "",
            "\1\u017b\37\uffff\1\u017b",
            "\1\u017c\37\uffff\1\u017c",
            "\1\u017d\37\uffff\1\u017d",
            "\1\u017e\37\uffff\1\u017e",
            "\1\u017f\37\uffff\1\u017f",
            "\1\u0180\37\uffff\1\u0180",
            "\1\u0181\37\uffff\1\u0181",
            "",
            "\1\u0182\37\uffff\1\u0182",
            "\1\u0183\37\uffff\1\u0183",
            "\1\u0184\37\uffff\1\u0184",
            "\1\u0185\37\uffff\1\u0185",
            "\1\u0186\37\uffff\1\u0186",
            "\1\u0188\5\uffff\1\u0187\31\uffff\1\u0188\5\uffff\1\u0187",
            "\1\u0189\37\uffff\1\u0189",
            "\1\u018a\37\uffff\1\u018a",
            "\1\u018b\37\uffff\1\u018b",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u018e\37\uffff\1\u018e",
            "",
            "\1\u018f\37\uffff\1\u018f",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0191\37\uffff\1\u0191",
            "\1\u0192\37\uffff\1\u0192",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0194\37\uffff\1\u0194",
            "\1\u0195\37\uffff\1\u0195",
            "\1\u0196\37\uffff\1\u0196",
            "\1\u0197\37\uffff\1\u0197",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\12\u013d",
            "\12\u013d\12\uffff\1\u0096\1\u013f\2\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u013c\4\uffff\1\u0096\1\u013f\2\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "",
            "\1\u019b\1\uffff\1\u019b\2\uffff\12\u019c",
            "\1\u019e\5\uffff\1\u019d\31\uffff\1\u019e\5\uffff\1\u019d",
            "\1\u01a1\12\uffff\1\u01a0\2\uffff\1\u019f\21\uffff\1\u01a1\12\uffff\1\u01a0\2\uffff\1\u019f",
            "\1\u01a2\6\uffff\1\u01a3\30\uffff\1\u01a2\6\uffff\1\u01a3",
            "\1\u01a5\10\uffff\1\u01a4\26\uffff\1\u01a5\10\uffff\1\u01a4",
            "\1\u01a7\7\uffff\1\u01a6\27\uffff\1\u01a7\7\uffff\1\u01a6",
            "\1\u01a8\37\uffff\1\u01a8",
            "\1\u01a9\37\uffff\1\u01a9",
            "\1\u01aa\37\uffff\1\u01aa",
            "\1\u01ab\37\uffff\1\u01ab",
            "\1\u01ac\37\uffff\1\u01ac",
            "\1\u01ad\37\uffff\1\u01ad",
            "\1\u01ae\37\uffff\1\u01ae",
            "\1\u01af\37\uffff\1\u01af",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u01b1\37\uffff\1\u01b1",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\u01b4\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\u01b6\1\uffff\32\63",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u01ba\37\uffff\1\u01ba",
            "\1\u01bb\37\uffff\1\u01bb",
            "\1\u01bc\37\uffff\1\u01bc",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "\1\u01be\37\uffff\1\u01be",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u01c0\37\uffff\1\u01c0",
            "\1\u01c1\7\uffff\1\u01c2\7\uffff\1\u01c3\17\uffff\1\u01c1\7\uffff\1\u01c2\7\uffff\1\u01c3",
            "\1\u01c4\37\uffff\1\u01c4",
            "\1\u01c5\37\uffff\1\u01c5",
            "\1\u01c6\37\uffff\1\u01c6",
            "\1\u01c7\37\uffff\1\u01c7",
            "\1\u01c8\37\uffff\1\u01c8",
            "\1\u01c9\37\uffff\1\u01c9",
            "\1\u01ca\37\uffff\1\u01ca",
            "\1\u01cb\37\uffff\1\u01cb",
            "\1\u01cc\37\uffff\1\u01cc",
            "",
            "\1\u01cd\37\uffff\1\u01cd",
            "",
            "",
            "",
            "",
            "\1\u01ce\37\uffff\1\u01ce",
            "\1\u01cf\37\uffff\1\u01cf",
            "",
            "\1\u01d1\7\uffff\1\u01d0\27\uffff\1\u01d1\7\uffff\1\u01d0",
            "",
            "\1\u01d2\37\uffff\1\u01d2",
            "\1\u01d3\37\uffff\1\u01d3",
            "\1\u01d4\37\uffff\1\u01d4",
            "\1\u01d5\37\uffff\1\u01d5",
            "\1\u01d6\37\uffff\1\u01d6",
            "\1\u01d7\37\uffff\1\u01d7",
            "\1\u01d8\37\uffff\1\u01d8",
            "\1\u01d9\37\uffff\1\u01d9",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u01db\37\uffff\1\u01db",
            "\1\u01dc\37\uffff\1\u01dc",
            "\1\u01dd\37\uffff\1\u01dd",
            "\1\u01de\37\uffff\1\u01de",
            "\1\u01df\37\uffff\1\u01df",
            "\1\u01e0\37\uffff\1\u01e0",
            "\1\u01e1\37\uffff\1\u01e1",
            "\1\u01e2\37\uffff\1\u01e2",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "\1\u01e5\37\uffff\1\u01e5",
            "\1\u01e6\37\uffff\1\u01e6",
            "",
            "\1\u01e7\37\uffff\1\u01e7",
            "\1\u01e8\37\uffff\1\u01e8",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "",
            "\12\u019c",
            "\12\u01ee\12\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u01ed\4\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "\1\u01ef\37\uffff\1\u01ef",
            "\1\u01f0\37\uffff\1\u01f0",
            "\1\u01f1\37\uffff\1\u01f1",
            "\1\u01f2\37\uffff\1\u01f2",
            "\1\u01f3\37\uffff\1\u01f3",
            "\1\u01f4\37\uffff\1\u01f4",
            "\1\u01f5\37\uffff\1\u01f5",
            "\1\u01f6\37\uffff\1\u01f6",
            "\1\u01f7\37\uffff\1\u01f7",
            "\1\u01f8\37\uffff\1\u01f8",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u01fa\37\uffff\1\u01fa",
            "\1\u01fc\2\uffff\1\u01fb\34\uffff\1\u01fc\2\uffff\1\u01fb",
            "\1\u01fd\37\uffff\1\u01fd",
            "\1\u01fe\37\uffff\1\u01fe",
            "\1\u0200\14\uffff\1\u01ff\22\uffff\1\u0200\14\uffff\1\u01ff",
            "\1\u0201\37\uffff\1\u0201",
            "\1\u0202\37\uffff\1\u0202",
            "\1\u0203\37\uffff\1\u0203",
            "",
            "\1\u0204\37\uffff\1\u0204",
            "",
            "",
            "\1\u0205\37\uffff\1\u0205",
            "",
            "\1\u0206\37\uffff\1\u0206",
            "",
            "",
            "",
            "\1\u0207\37\uffff\1\u0207",
            "\1\u0208\37\uffff\1\u0208",
            "\1\u0209\37\uffff\1\u0209",
            "",
            "\1\u020a\37\uffff\1\u020a",
            "",
            "\1\u020b\37\uffff\1\u020b",
            "\1\u020c\37\uffff\1\u020c",
            "\1\u020d\37\uffff\1\u020d",
            "\1\u020e\37\uffff\1\u020e",
            "\1\u020f\37\uffff\1\u020f",
            "\1\u0210\37\uffff\1\u0210",
            "\1\u0211\37\uffff\1\u0211",
            "\1\u0212\37\uffff\1\u0212",
            "\1\u0213\37\uffff\1\u0213",
            "\1\u0215\16\uffff\1\u0214\20\uffff\1\u0215",
            "\1\u0216\37\uffff\1\u0216",
            "\1\u0217\37\uffff\1\u0217",
            "\1\u0218\37\uffff\1\u0218",
            "\1\u0219\37\uffff\1\u0219",
            "\1\u021a\37\uffff\1\u021a",
            "\1\u021b\37\uffff\1\u021b",
            "\1\u021c\37\uffff\1\u021c",
            "\1\u021d\37\uffff\1\u021d",
            "\1\u021e\37\uffff\1\u021e",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0223\37\uffff\1\u0223",
            "\1\u0224\37\uffff\1\u0224",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u0226\37\uffff\1\u0226",
            "\1\u0227\37\uffff\1\u0227",
            "\1\u0228\37\uffff\1\u0228",
            "\1\u0229\37\uffff\1\u0229",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u022b\37\uffff\1\u022b",
            "\1\u022c\37\uffff\1\u022c",
            "\1\u022d\37\uffff\1\u022d",
            "",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "",
            "",
            "\12\u01ee",
            "\12\u01ee\12\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096\11\uffff\1\u01ed\4\uffff\1\u0096\3\uffff\1\u0096\4\uffff\2\u0096\4\uffff\1\u0096\1\uffff\1\u0096",
            "\1\u0232\37\uffff\1\u0232",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0234\37\uffff\1\u0234",
            "\1\u0235\37\uffff\1\u0235",
            "\1\u0236\37\uffff\1\u0236",
            "\1\u0237\37\uffff\1\u0237",
            "\1\u0238\37\uffff\1\u0238",
            "\1\u0239\37\uffff\1\u0239",
            "\1\u023a\37\uffff\1\u023a",
            "\1\u023b\37\uffff\1\u023b",
            "",
            "\1\u023c\37\uffff\1\u023c",
            "\1\u023d\37\uffff\1\u023d",
            "\1\u023e\37\uffff\1\u023e",
            "\1\u023f\37\uffff\1\u023f",
            "\1\u0240\37\uffff\1\u0240",
            "\1\u0241\37\uffff\1\u0241",
            "\1\u0242\37\uffff\1\u0242",
            "\1\u0243\37\uffff\1\u0243",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0246\37\uffff\1\u0246",
            "\1\u0247\37\uffff\1\u0247",
            "\1\u0248\37\uffff\1\u0248",
            "\1\u0249\37\uffff\1\u0249",
            "\1\u024a\37\uffff\1\u024a",
            "\1\u024b\37\uffff\1\u024b",
            "\1\u024c\37\uffff\1\u024c",
            "\1\u024d",
            "\1\u024e\37\uffff\1\u024e",
            "\1\u024f\37\uffff\1\u024f",
            "\1\u0250\37\uffff\1\u0250",
            "\1\u0251\37\uffff\1\u0251",
            "\1\u0252\37\uffff\1\u0252",
            "\1\u0253\37\uffff\1\u0253",
            "\1\u0254\37\uffff\1\u0254",
            "\1\u0255\37\uffff\1\u0255",
            "\1\u0256\37\uffff\1\u0256",
            "\1\u0257\37\uffff\1\u0257",
            "\1\u0258\37\uffff\1\u0258",
            "\1\u0259\37\uffff\1\u0259",
            "\1\u025a",
            "\1\u025b\37\uffff\1\u025b",
            "\1\u025c\37\uffff\1\u025c",
            "\1\u025d\37\uffff\1\u025d",
            "\1\u025e\37\uffff\1\u025e",
            "\1\u025f\37\uffff\1\u025f",
            "\1\u0260\37\uffff\1\u0260",
            "",
            "",
            "",
            "",
            "\1\u0261\37\uffff\1\u0261",
            "\1\u0262\37\uffff\1\u0262",
            "",
            "\1\u0263\37\uffff\1\u0263",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0265\37\uffff\1\u0265",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u0267\37\uffff\1\u0267",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "",
            "",
            "\1\u026a\37\uffff\1\u026a",
            "",
            "\1\u026b\37\uffff\1\u026b",
            "\1\u026c\37\uffff\1\u026c",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u026e\37\uffff\1\u026e",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0270\37\uffff\1\u0270",
            "\1\u0271\37\uffff\1\u0271",
            "\1\u0272\37\uffff\1\u0272",
            "\1\u0273\37\uffff\1\u0273",
            "\1\u0274\37\uffff\1\u0274",
            "\1\u0275\37\uffff\1\u0275",
            "\1\u0276\37\uffff\1\u0276",
            "\1\u0277\37\uffff\1\u0277",
            "\1\u0278\37\uffff\1\u0278",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u027a\37\uffff\1\u027a",
            "",
            "",
            "\1\u027b",
            "\1\u027c\37\uffff\1\u027c",
            "\1\u027d",
            "\1\u027e\37\uffff\1\u027e",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0281",
            "\1\u0282\37\uffff\1\u0282",
            "\1\u0283\37\uffff\1\u0283",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0286\37\uffff\1\u0286",
            "\1\u0287\37\uffff\1\u0287",
            "\1\u0288\37\uffff\1\u0288",
            "\1\u0289\37\uffff\1\u0289",
            "\1\u028a\37\uffff\1\u028a",
            "\1\u028b\37\uffff\1\u028b",
            "\1\u028c\37\uffff\1\u028c",
            "\1\u028d\37\uffff\1\u028d",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u028f\37\uffff\1\u028f",
            "\1\u0290\37\uffff\1\u0290",
            "\1\u0291\37\uffff\1\u0291",
            "\1\u0292\37\uffff\1\u0292",
            "\1\u0293\37\uffff\1\u0293",
            "\1\u0294\37\uffff\1\u0294",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0296\37\uffff\1\u0296",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u0298\37\uffff\1\u0298",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "\1\u029b\37\uffff\1\u029b",
            "\1\u029c\37\uffff\1\u029c",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u029e\37\uffff\1\u029e",
            "",
            "\1\u029f\37\uffff\1\u029f",
            "\1\u02a0\37\uffff\1\u02a0",
            "\1\u02a1\37\uffff\1\u02a1",
            "\1\u02a2\37\uffff\1\u02a2",
            "\1\u02a3\37\uffff\1\u02a3",
            "\1\u02a4\37\uffff\1\u02a4",
            "\1\u02a5\37\uffff\1\u02a5",
            "\1\u02a6\37\uffff\1\u02a6",
            "\1\u02a7\37\uffff\1\u02a7",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u02a9\37\uffff\1\u02a9",
            "\1\u02aa",
            "\1\u02ab\37\uffff\1\u02ab",
            "\1\u02ac\37\uffff\1\u02ac",
            "",
            "",
            "\1\u02ad\37\uffff\1\u02ad",
            "\1\u02ae\37\uffff\1\u02ae",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "\1\u02b0\37\uffff\1\u02b0",
            "\1\u02b1\37\uffff\1\u02b1",
            "\1\u02b2\37\uffff\1\u02b2",
            "\1\u02b3\37\uffff\1\u02b3",
            "\1\u02b4\37\uffff\1\u02b4",
            "\1\u02b5\37\uffff\1\u02b5",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u02b7\37\uffff\1\u02b7",
            "",
            "\1\u02b8\37\uffff\1\u02b8",
            "\1\u02b9\37\uffff\1\u02b9",
            "\1\u02ba\37\uffff\1\u02ba",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u02bc\37\uffff\1\u02bc",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "\1\u02c0\37\uffff\1\u02c0",
            "\1\u02c1\37\uffff\1\u02c1",
            "",
            "\1\u02c2\37\uffff\1\u02c2",
            "\1\u02c3\37\uffff\1\u02c3",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u02c5\37\uffff\1\u02c5",
            "\1\u02c6\37\uffff\1\u02c6",
            "\1\u02c7\37\uffff\1\u02c7",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u02c9\37\uffff\1\u02c9",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u02cc\37\uffff\1\u02cc",
            "\1\u02cd\37\uffff\1\u02cd",
            "\1\u02ce\37\uffff\1\u02ce",
            "\1\u02cf\37\uffff\1\u02cf",
            "\1\u02d0\37\uffff\1\u02d0",
            "\1\u02d1\37\uffff\1\u02d1",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u02d3\37\uffff\1\u02d3",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u02d9\37\uffff\1\u02d9",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "",
            "\1\u02dd\37\uffff\1\u02dd",
            "\1\u02de\37\uffff\1\u02de",
            "\1\u02df\37\uffff\1\u02df",
            "\1\u02e0\37\uffff\1\u02e0",
            "",
            "\1\u02e1\37\uffff\1\u02e1",
            "\1\u02e2\37\uffff\1\u02e2",
            "\1\u02e3\37\uffff\1\u02e3",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "\1\u02e5\37\uffff\1\u02e5",
            "\1\u02e6\37\uffff\1\u02e6",
            "\1\u02e7\37\uffff\1\u02e7",
            "\1\u02e8\37\uffff\1\u02e8",
            "\1\u02e9\37\uffff\1\u02e9",
            "\1\u02ea\37\uffff\1\u02ea",
            "",
            "\1\u02eb\37\uffff\1\u02eb",
            "",
            "",
            "",
            "",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "",
            "\1\u02ed",
            "\1\u02ee\37\uffff\1\u02ee",
            "\1\u02ef\37\uffff\1\u02ef",
            "\1\u02f0\37\uffff\1\u02f0",
            "\1\u02f1\37\uffff\1\u02f1",
            "\1\u02f2\37\uffff\1\u02f2",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u02f4\37\uffff\1\u02f4",
            "\1\u02f5\37\uffff\1\u02f5",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\1\u02f7\37\uffff\1\u02f7",
            "\1\u02f8\37\uffff\1\u02f8",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u02fb\37\uffff\1\u02fb",
            "\1\u02fc\37\uffff\1\u02fc",
            "\1\u02fd\37\uffff\1\u02fd",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "\1\u0301\37\uffff\1\u0301",
            "\1\u0302\37\uffff\1\u0302",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "\1\u0305\37\uffff\1\u0305",
            "\1\u0306\37\uffff\1\u0306",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            "",
            "\1\u030a\37\uffff\1\u030a",
            "\1\u030b\37\uffff\1\u030b",
            "",
            "",
            "",
            "\1\u030c\37\uffff\1\u030c",
            "\1\u030d\37\uffff\1\u030d",
            "\1\u030e\37\uffff\1\u030e",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "\12\63\7\uffff\32\63\4\uffff\1\63\1\uffff\32\63",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( END_FUNCTION_BLOCK | END_CONFIGURATION | END_TRANSITION | FUNCTION_BLOCK | LDATE_AND_TIME | CONFIGURATION | DATE_AND_TIME | END_ALGORITHM | END_INTERFACE | END_NAMESPACE | END_RESOURCE | INITIAL_STEP | LTIME_OF_DAY | VAR_EXTERNAL | END_PROGRAM | TIME_OF_DAY | END_ACTION | END_METHOD | END_REPEAT | END_STRUCT | IMPLEMENTS | NON_RETAIN | READ_WRITE | TRANSITION | VAR_ACCESS | VAR_CONFIG | VAR_GLOBAL | VAR_IN_OUT | VAR_OUTPUT | ALGORITHM | END_CLASS | END_WHILE | INTERFACE | NAMESPACE | PROTECTED | READ_ONLY | VAR_INPUT | ABSTRACT | CONSTANT | CONTINUE | END_CASE | END_STEP | END_TYPE | INTERNAL | INTERVAL | OVERRIDE | PRIORITY | RESOURCE | VAR_TEMP | END_FOR | END_VAR | EXTENDS | OVERLAP | PRIVATE | PROGRAM | WSTRING | ACTION | END_IF | METHOD | PUBLIC | REF_TO | REPEAT | RETAIN | RETURN | SINGLE | STRING | STRUCT | ARRAY | CLASS | DWORD | ELSIF | FALSE | FINAL | LDATE | LREAL | LTIME | LWORD | UDINT | ULINT | UNTIL | USINT | WCHAR | WHILE | BOOL | BYTE | CASE | CHAR | DATE | DINT | ELSE | EXIT | LINT | LTOD | NULL | REAL | SINT | STEP | THEN | THIS | TIME | TRUE | TYPE | UINT | WITH | WORD | AND | FOR | INT | LDT | MOD | NOT | REF | TOD | VAR | XOR | B | D_1 | L | W | X | AsteriskAsterisk | FullStopFullStop | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | DO | DT | IF | LD | LT | OF | ON | OR | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | D | T | LeftSquareBracket | RightSquareBracket | RULE_NON_DECIMAL | RULE_INT | RULE_NUMBER | RULE_TIME_VALUE | RULE_ID | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA23_45 = input.LA(1);

                        s = -1;
                        if ( ((LA23_45>='\u0000' && LA23_45<='\uFFFF')) ) {s = 152;}

                        else s = 47;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA23_0 = input.LA(1);

                        s = -1;
                        if ( (LA23_0=='E'||LA23_0=='e') ) {s = 1;}

                        else if ( (LA23_0=='F'||LA23_0=='f') ) {s = 2;}

                        else if ( (LA23_0=='L'||LA23_0=='l') ) {s = 3;}

                        else if ( (LA23_0=='C'||LA23_0=='c') ) {s = 4;}

                        else if ( (LA23_0=='D'||LA23_0=='d') ) {s = 5;}

                        else if ( (LA23_0=='I'||LA23_0=='i') ) {s = 6;}

                        else if ( (LA23_0=='V'||LA23_0=='v') ) {s = 7;}

                        else if ( (LA23_0=='T'||LA23_0=='t') ) {s = 8;}

                        else if ( (LA23_0=='N'||LA23_0=='n') ) {s = 9;}

                        else if ( (LA23_0=='R'||LA23_0=='r') ) {s = 10;}

                        else if ( (LA23_0=='A'||LA23_0=='a') ) {s = 11;}

                        else if ( (LA23_0=='P'||LA23_0=='p') ) {s = 12;}

                        else if ( (LA23_0=='O'||LA23_0=='o') ) {s = 13;}

                        else if ( (LA23_0=='W'||LA23_0=='w') ) {s = 14;}

                        else if ( (LA23_0=='M'||LA23_0=='m') ) {s = 15;}

                        else if ( (LA23_0=='S'||LA23_0=='s') ) {s = 16;}

                        else if ( (LA23_0=='U'||LA23_0=='u') ) {s = 17;}

                        else if ( (LA23_0=='B'||LA23_0=='b') ) {s = 18;}

                        else if ( (LA23_0=='X'||LA23_0=='x') ) {s = 19;}

                        else if ( (LA23_0=='%') ) {s = 20;}

                        else if ( (LA23_0=='*') ) {s = 21;}

                        else if ( (LA23_0=='.') ) {s = 22;}

                        else if ( (LA23_0==':') ) {s = 23;}

                        else if ( (LA23_0=='<') ) {s = 24;}

                        else if ( (LA23_0=='=') ) {s = 25;}

                        else if ( (LA23_0=='>') ) {s = 26;}

                        else if ( (LA23_0=='#') ) {s = 27;}

                        else if ( (LA23_0=='&') ) {s = 28;}

                        else if ( (LA23_0=='(') ) {s = 29;}

                        else if ( (LA23_0==')') ) {s = 30;}

                        else if ( (LA23_0=='+') ) {s = 31;}

                        else if ( (LA23_0==',') ) {s = 32;}

                        else if ( (LA23_0=='-') ) {s = 33;}

                        else if ( (LA23_0=='/') ) {s = 34;}

                        else if ( (LA23_0==';') ) {s = 35;}

                        else if ( (LA23_0=='[') ) {s = 36;}

                        else if ( (LA23_0==']') ) {s = 37;}

                        else if ( (LA23_0=='2') ) {s = 38;}

                        else if ( (LA23_0=='8') ) {s = 39;}

                        else if ( (LA23_0=='1') ) {s = 40;}

                        else if ( (LA23_0=='0'||(LA23_0>='3' && LA23_0<='7')||LA23_0=='9') ) {s = 41;}

                        else if ( (LA23_0=='^') ) {s = 42;}

                        else if ( ((LA23_0>='G' && LA23_0<='H')||(LA23_0>='J' && LA23_0<='K')||LA23_0=='Q'||(LA23_0>='Y' && LA23_0<='Z')||LA23_0=='_'||(LA23_0>='g' && LA23_0<='h')||(LA23_0>='j' && LA23_0<='k')||LA23_0=='q'||(LA23_0>='y' && LA23_0<='z')) ) {s = 43;}

                        else if ( (LA23_0=='\"') ) {s = 44;}

                        else if ( (LA23_0=='\'') ) {s = 45;}

                        else if ( ((LA23_0>='\t' && LA23_0<='\n')||LA23_0=='\r'||LA23_0==' ') ) {s = 46;}

                        else if ( ((LA23_0>='\u0000' && LA23_0<='\b')||(LA23_0>='\u000B' && LA23_0<='\f')||(LA23_0>='\u000E' && LA23_0<='\u001F')||LA23_0=='!'||LA23_0=='$'||(LA23_0>='?' && LA23_0<='@')||LA23_0=='\\'||LA23_0=='`'||(LA23_0>='{' && LA23_0<='\uFFFF')) ) {s = 47;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA23_44 = input.LA(1);

                        s = -1;
                        if ( ((LA23_44>='\u0000' && LA23_44<='\uFFFF')) ) {s = 152;}

                        else s = 47;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 23, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}