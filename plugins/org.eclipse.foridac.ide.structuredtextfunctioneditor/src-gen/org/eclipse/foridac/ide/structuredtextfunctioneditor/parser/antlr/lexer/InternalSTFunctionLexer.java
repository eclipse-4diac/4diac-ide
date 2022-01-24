package org.eclipse.foridac.ide.structuredtextfunctioneditor.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSTFunctionLexer extends Lexer {
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

    public InternalSTFunctionLexer() {;} 
    public InternalSTFunctionLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalSTFunctionLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalSTFunctionLexer.g"; }

    // $ANTLR start "LDATE_AND_TIME"
    public final void mLDATE_AND_TIME() throws RecognitionException {
        try {
            int _type = LDATE_AND_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:14:16: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '#' )
            // InternalSTFunctionLexer.g:14:18: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LDATE_AND_TIME"

    // $ANTLR start "DATE_AND_TIME"
    public final void mDATE_AND_TIME() throws RecognitionException {
        try {
            int _type = DATE_AND_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:16:15: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '#' )
            // InternalSTFunctionLexer.g:16:17: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATE_AND_TIME"

    // $ANTLR start "END_FUNCTION"
    public final void mEND_FUNCTION() throws RecognitionException {
        try {
            int _type = END_FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:18:14: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTFunctionLexer.g:18:16: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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

    // $ANTLR start "TIME_OF_DAY"
    public final void mTIME_OF_DAY() throws RecognitionException {
        try {
            int _type = TIME_OF_DAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:20:13: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) '#' )
            // InternalSTFunctionLexer.g:20:15: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIME_OF_DAY"

    // $ANTLR start "END_REPEAT"
    public final void mEND_REPEAT() throws RecognitionException {
        try {
            int _type = END_REPEAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:22:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTFunctionLexer.g:22:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
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

    // $ANTLR start "VAR_OUTPUT"
    public final void mVAR_OUTPUT() throws RecognitionException {
        try {
            int _type = VAR_OUTPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:24:12: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalSTFunctionLexer.g:24:14: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' )
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

    // $ANTLR start "END_WHILE"
    public final void mEND_WHILE() throws RecognitionException {
        try {
            int _type = END_WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:26:11: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSTFunctionLexer.g:26:13: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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

    // $ANTLR start "VAR_INPUT"
    public final void mVAR_INPUT() throws RecognitionException {
        try {
            int _type = VAR_INPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:28:11: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' ) )
            // InternalSTFunctionLexer.g:28:13: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'T' | 't' )
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

    // $ANTLR start "CONSTANT"
    public final void mCONSTANT() throws RecognitionException {
        try {
            int _type = CONSTANT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:30:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSTFunctionLexer.g:30:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalSTFunctionLexer.g:32:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalSTFunctionLexer.g:32:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
            // InternalSTFunctionLexer.g:34:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTFunctionLexer.g:34:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
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

    // $ANTLR start "FUNCTION"
    public final void mFUNCTION() throws RecognitionException {
        try {
            int _type = FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:36:10: ( ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // InternalSTFunctionLexer.g:36:12: ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
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

    // $ANTLR start "VAR_TEMP"
    public final void mVAR_TEMP() throws RecognitionException {
        try {
            int _type = VAR_TEMP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:38:10: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'T' | 't' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'P' | 'p' ) )
            // InternalSTFunctionLexer.g:38:12: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '_' ( 'T' | 't' ) ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'P' | 'p' )
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

    // $ANTLR start "WSTRING"
    public final void mWSTRING() throws RecognitionException {
        try {
            int _type = WSTRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:40:9: ( ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) '#' )
            // InternalSTFunctionLexer.g:40:11: ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WSTRING"

    // $ANTLR start "END_FOR"
    public final void mEND_FOR() throws RecognitionException {
        try {
            int _type = END_FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:42:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTFunctionLexer.g:42:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalSTFunctionLexer.g:44:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTFunctionLexer.g:44:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:46:8: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) '#' )
            // InternalSTFunctionLexer.g:46:10: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "DWORD"
    public final void mDWORD() throws RecognitionException {
        try {
            int _type = DWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:48:7: ( ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) '#' )
            // InternalSTFunctionLexer.g:48:9: ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DWORD"

    // $ANTLR start "END_IF"
    public final void mEND_IF() throws RecognitionException {
        try {
            int _type = END_IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:50:8: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTFunctionLexer.g:50:10: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' )
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

    // $ANTLR start "LDATE"
    public final void mLDATE() throws RecognitionException {
        try {
            int _type = LDATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:52:7: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '#' )
            // InternalSTFunctionLexer.g:52:9: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:54:7: ( ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '#' )
            // InternalSTFunctionLexer.g:54:9: ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:56:7: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '#' )
            // InternalSTFunctionLexer.g:56:9: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:58:7: ( ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) '#' )
            // InternalSTFunctionLexer.g:58:9: ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LWORD"

    // $ANTLR start "REPEAT"
    public final void mREPEAT() throws RecognitionException {
        try {
            int _type = REPEAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:60:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTFunctionLexer.g:60:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
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

    // $ANTLR start "RETURN"
    public final void mRETURN() throws RecognitionException {
        try {
            int _type = RETURN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:62:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' ) )
            // InternalSTFunctionLexer.g:62:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' )
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

    // $ANTLR start "UDINT"
    public final void mUDINT() throws RecognitionException {
        try {
            int _type = UDINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:64:7: ( ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:64:9: ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:66:7: ( ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:66:9: ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ULINT"

    // $ANTLR start "USINT"
    public final void mUSINT() throws RecognitionException {
        try {
            int _type = USINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:68:7: ( ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:68:9: ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:70:7: ( ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '#' )
            // InternalSTFunctionLexer.g:70:9: ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WCHAR"

    // $ANTLR start "ARRAY"
    public final void mARRAY() throws RecognitionException {
        try {
            int _type = ARRAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:72:7: ( ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalSTFunctionLexer.g:72:9: ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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

    // $ANTLR start "BOOL"
    public final void mBOOL() throws RecognitionException {
        try {
            int _type = BOOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:74:6: ( ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) '#' )
            // InternalSTFunctionLexer.g:74:8: ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:76:6: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' ) '#' )
            // InternalSTFunctionLexer.g:76:8: ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BYTE"

    // $ANTLR start "CHAR"
    public final void mCHAR() throws RecognitionException {
        try {
            int _type = CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:78:6: ( ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '#' )
            // InternalSTFunctionLexer.g:78:8: ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:80:6: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '#' )
            // InternalSTFunctionLexer.g:80:8: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:82:6: ( ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:82:8: ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DINT"

    // $ANTLR start "ELSIF"
    public final void mELSIF() throws RecognitionException {
        try {
            int _type = ELSIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:84:7: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTFunctionLexer.g:84:9: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' )
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

    // $ANTLR start "LINT"
    public final void mLINT() throws RecognitionException {
        try {
            int _type = LINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:86:6: ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:86:8: ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:88:6: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) '#' )
            // InternalSTFunctionLexer.g:88:8: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LTOD"

    // $ANTLR start "REAL"
    public final void mREAL() throws RecognitionException {
        try {
            int _type = REAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:90:6: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '#' )
            // InternalSTFunctionLexer.g:90:8: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:92:6: ( ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:92:8: ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SINT"

    // $ANTLR start "TIME"
    public final void mTIME() throws RecognitionException {
        try {
            int _type = TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:94:6: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '#' )
            // InternalSTFunctionLexer.g:94:8: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIME"

    // $ANTLR start "UINT"
    public final void mUINT() throws RecognitionException {
        try {
            int _type = UINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:96:6: ( ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:96:8: ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UINT"

    // $ANTLR start "UNTIL"
    public final void mUNTIL() throws RecognitionException {
        try {
            int _type = UNTIL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:98:7: ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' ) )
            // InternalSTFunctionLexer.g:98:9: ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' )
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

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:100:7: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSTFunctionLexer.g:100:9: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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

    // $ANTLR start "WORD"
    public final void mWORD() throws RecognitionException {
        try {
            int _type = WORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:102:6: ( ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) '#' )
            // InternalSTFunctionLexer.g:102:8: ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WORD"

    // $ANTLR start "CASE"
    public final void mCASE() throws RecognitionException {
        try {
            int _type = CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:104:6: ( ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTFunctionLexer.g:104:8: ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
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

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:106:6: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalSTFunctionLexer.g:106:8: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalSTFunctionLexer.g:108:6: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' ) )
            // InternalSTFunctionLexer.g:108:8: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' )
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

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:110:5: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:110:7: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:112:5: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:112:7: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'T' | 't' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LDT"

    // $ANTLR start "THEN"
    public final void mTHEN() throws RecognitionException {
        try {
            int _type = THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:114:6: ( ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' ) )
            // InternalSTFunctionLexer.g:114:8: ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' )
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

    // $ANTLR start "TOD"
    public final void mTOD() throws RecognitionException {
        try {
            int _type = TOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:116:5: ( ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) '#' )
            // InternalSTFunctionLexer.g:116:7: ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOD"

    // $ANTLR start "B"
    public final void mB() throws RecognitionException {
        try {
            int _type = B;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:118:3: ( '.' '%' ( 'B' | 'b' ) )
            // InternalSTFunctionLexer.g:118:5: '.' '%' ( 'B' | 'b' )
            {
            match('.'); 
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
            // InternalSTFunctionLexer.g:120:5: ( '.' '%' ( 'D' | 'd' ) )
            // InternalSTFunctionLexer.g:120:7: '.' '%' ( 'D' | 'd' )
            {
            match('.'); 
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
            // InternalSTFunctionLexer.g:122:3: ( '.' '%' ( 'L' | 'l' ) )
            // InternalSTFunctionLexer.g:122:5: '.' '%' ( 'L' | 'l' )
            {
            match('.'); 
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
            // InternalSTFunctionLexer.g:124:3: ( '.' '%' ( 'W' | 'w' ) )
            // InternalSTFunctionLexer.g:124:5: '.' '%' ( 'W' | 'w' )
            {
            match('.'); 
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
            // InternalSTFunctionLexer.g:126:3: ( '.' '%' ( 'X' | 'x' ) )
            // InternalSTFunctionLexer.g:126:5: '.' '%' ( 'X' | 'x' )
            {
            match('.'); 
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

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:128:5: ( ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
            // InternalSTFunctionLexer.g:128:7: ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' )
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

    // $ANTLR start "DT"
    public final void mDT() throws RecognitionException {
        try {
            int _type = DT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:130:4: ( ( 'D' | 'd' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:130:6: ( 'D' | 'd' ) ( 'T' | 't' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DT"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:132:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTFunctionLexer.g:132:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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

    // $ANTLR start "LD"
    public final void mLD() throws RecognitionException {
        try {
            int _type = LD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:134:4: ( ( 'L' | 'l' ) ( 'D' | 'd' ) '#' )
            // InternalSTFunctionLexer.g:134:6: ( 'L' | 'l' ) ( 'D' | 'd' ) '#'
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

            match('#'); 

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
            // InternalSTFunctionLexer.g:136:4: ( ( 'L' | 'l' ) ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:136:6: ( 'L' | 'l' ) ( 'T' | 't' ) '#'
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

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:138:5: ( ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalSTFunctionLexer.g:138:7: ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalSTFunctionLexer.g:140:5: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' ) )
            // InternalSTFunctionLexer.g:140:7: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' )
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

    // $ANTLR start "VAR"
    public final void mVAR() throws RecognitionException {
        try {
            int _type = VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:142:5: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSTFunctionLexer.g:142:7: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalSTFunctionLexer.g:144:5: ( ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTFunctionLexer.g:144:7: ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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

    // $ANTLR start "AsteriskAsterisk"
    public final void mAsteriskAsterisk() throws RecognitionException {
        try {
            int _type = AsteriskAsterisk;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:146:18: ( '*' '*' )
            // InternalSTFunctionLexer.g:146:20: '*' '*'
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
            // InternalSTFunctionLexer.g:148:18: ( '.' '.' )
            // InternalSTFunctionLexer.g:148:20: '.' '.'
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
            // InternalSTFunctionLexer.g:150:17: ( ':' '=' )
            // InternalSTFunctionLexer.g:150:19: ':' '='
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
            // InternalSTFunctionLexer.g:152:24: ( '<' '=' )
            // InternalSTFunctionLexer.g:152:26: '<' '='
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
            // InternalSTFunctionLexer.g:154:29: ( '<' '>' )
            // InternalSTFunctionLexer.g:154:31: '<' '>'
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
            // InternalSTFunctionLexer.g:156:27: ( '=' '>' )
            // InternalSTFunctionLexer.g:156:29: '=' '>'
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
            // InternalSTFunctionLexer.g:158:27: ( '>' '=' )
            // InternalSTFunctionLexer.g:158:29: '>' '='
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
            // InternalSTFunctionLexer.g:160:4: ( ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalSTFunctionLexer.g:160:6: ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalSTFunctionLexer.g:162:4: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) )
            // InternalSTFunctionLexer.g:162:6: ( 'B' | 'b' ) ( 'Y' | 'y' )
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

    // $ANTLR start "D"
    public final void mD() throws RecognitionException {
        try {
            int _type = D;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:164:3: ( ( 'D' | 'd' ) '#' )
            // InternalSTFunctionLexer.g:164:5: ( 'D' | 'd' ) '#'
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "D"

    // $ANTLR start "DO"
    public final void mDO() throws RecognitionException {
        try {
            int _type = DO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:166:4: ( ( 'D' | 'd' ) ( 'O' | 'o' ) )
            // InternalSTFunctionLexer.g:166:6: ( 'D' | 'd' ) ( 'O' | 'o' )
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

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:168:4: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSTFunctionLexer.g:168:6: ( 'I' | 'i' ) ( 'F' | 'f' )
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

    // $ANTLR start "OF"
    public final void mOF() throws RecognitionException {
        try {
            int _type = OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:170:4: ( ( 'O' | 'o' ) ( 'F' | 'f' ) )
            // InternalSTFunctionLexer.g:170:6: ( 'O' | 'o' ) ( 'F' | 'f' )
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

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:172:4: ( ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalSTFunctionLexer.g:172:6: ( 'O' | 'o' ) ( 'R' | 'r' )
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

    // $ANTLR start "T"
    public final void mT() throws RecognitionException {
        try {
            int _type = T;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:174:3: ( ( 'T' | 't' ) '#' )
            // InternalSTFunctionLexer.g:174:5: ( 'T' | 't' ) '#'
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T"

    // $ANTLR start "TO"
    public final void mTO() throws RecognitionException {
        try {
            int _type = TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:176:4: ( ( 'T' | 't' ) ( 'O' | 'o' ) )
            // InternalSTFunctionLexer.g:176:6: ( 'T' | 't' ) ( 'O' | 'o' )
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
            // InternalSTFunctionLexer.g:178:12: ( '#' )
            // InternalSTFunctionLexer.g:178:14: '#'
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
            // InternalSTFunctionLexer.g:180:11: ( '&' )
            // InternalSTFunctionLexer.g:180:13: '&'
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
            // InternalSTFunctionLexer.g:182:17: ( '(' )
            // InternalSTFunctionLexer.g:182:19: '('
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
            // InternalSTFunctionLexer.g:184:18: ( ')' )
            // InternalSTFunctionLexer.g:184:20: ')'
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
            // InternalSTFunctionLexer.g:186:10: ( '*' )
            // InternalSTFunctionLexer.g:186:12: '*'
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
            // InternalSTFunctionLexer.g:188:10: ( '+' )
            // InternalSTFunctionLexer.g:188:12: '+'
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
            // InternalSTFunctionLexer.g:190:7: ( ',' )
            // InternalSTFunctionLexer.g:190:9: ','
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
            // InternalSTFunctionLexer.g:192:13: ( '-' )
            // InternalSTFunctionLexer.g:192:15: '-'
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
            // InternalSTFunctionLexer.g:194:10: ( '.' )
            // InternalSTFunctionLexer.g:194:12: '.'
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
            // InternalSTFunctionLexer.g:196:9: ( '/' )
            // InternalSTFunctionLexer.g:196:11: '/'
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
            // InternalSTFunctionLexer.g:198:7: ( ':' )
            // InternalSTFunctionLexer.g:198:9: ':'
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
            // InternalSTFunctionLexer.g:200:11: ( ';' )
            // InternalSTFunctionLexer.g:200:13: ';'
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
            // InternalSTFunctionLexer.g:202:14: ( '<' )
            // InternalSTFunctionLexer.g:202:16: '<'
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
            // InternalSTFunctionLexer.g:204:12: ( '=' )
            // InternalSTFunctionLexer.g:204:14: '='
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
            // InternalSTFunctionLexer.g:206:17: ( '>' )
            // InternalSTFunctionLexer.g:206:19: '>'
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

    // $ANTLR start "LeftSquareBracket"
    public final void mLeftSquareBracket() throws RecognitionException {
        try {
            int _type = LeftSquareBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:208:19: ( '[' )
            // InternalSTFunctionLexer.g:208:21: '['
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
            // InternalSTFunctionLexer.g:210:20: ( ']' )
            // InternalSTFunctionLexer.g:210:22: ']'
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

    // $ANTLR start "RULE_BOOL_VALUES"
    public final void mRULE_BOOL_VALUES() throws RecognitionException {
        try {
            int _type = RULE_BOOL_VALUES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:212:18: ( ( 'TRUE' | 'FALSE' ) )
            // InternalSTFunctionLexer.g:212:20: ( 'TRUE' | 'FALSE' )
            {
            // InternalSTFunctionLexer.g:212:20: ( 'TRUE' | 'FALSE' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='T') ) {
                alt1=1;
            }
            else if ( (LA1_0=='F') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalSTFunctionLexer.g:212:21: 'TRUE'
                    {
                    match("TRUE"); 


                    }
                    break;
                case 2 :
                    // InternalSTFunctionLexer.g:212:28: 'FALSE'
                    {
                    match("FALSE"); 


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
    // $ANTLR end "RULE_BOOL_VALUES"

    // $ANTLR start "RULE_HEX_DIGIT"
    public final void mRULE_HEX_DIGIT() throws RecognitionException {
        try {
            // InternalSTFunctionLexer.g:214:25: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' ) )
            // InternalSTFunctionLexer.g:214:27: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' )
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
            // InternalSTFunctionLexer.g:216:18: ( ( '2#' | '8#' | '16#' ) ( RULE_HEX_DIGIT )+ )
            // InternalSTFunctionLexer.g:216:20: ( '2#' | '8#' | '16#' ) ( RULE_HEX_DIGIT )+
            {
            // InternalSTFunctionLexer.g:216:20: ( '2#' | '8#' | '16#' )
            int alt2=3;
            switch ( input.LA(1) ) {
            case '2':
                {
                alt2=1;
                }
                break;
            case '8':
                {
                alt2=2;
                }
                break;
            case '1':
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalSTFunctionLexer.g:216:21: '2#'
                    {
                    match("2#"); 


                    }
                    break;
                case 2 :
                    // InternalSTFunctionLexer.g:216:26: '8#'
                    {
                    match("8#"); 


                    }
                    break;
                case 3 :
                    // InternalSTFunctionLexer.g:216:31: '16#'
                    {
                    match("16#"); 


                    }
                    break;

            }

            // InternalSTFunctionLexer.g:216:38: ( RULE_HEX_DIGIT )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='F')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='f')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSTFunctionLexer.g:216:38: RULE_HEX_DIGIT
            	    {
            	    mRULE_HEX_DIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
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
            // InternalSTFunctionLexer.g:218:10: ( '0' .. '9' ( ( '_' )? '0' .. '9' )* )
            // InternalSTFunctionLexer.g:218:12: '0' .. '9' ( ( '_' )? '0' .. '9' )*
            {
            matchRange('0','9'); 
            // InternalSTFunctionLexer.g:218:21: ( ( '_' )? '0' .. '9' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||LA5_0=='_') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSTFunctionLexer.g:218:22: ( '_' )? '0' .. '9'
            	    {
            	    // InternalSTFunctionLexer.g:218:22: ( '_' )?
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0=='_') ) {
            	        alt4=1;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // InternalSTFunctionLexer.g:218:22: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }

            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    break loop5;
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

    // $ANTLR start "RULE_TIME"
    public final void mRULE_TIME() throws RecognitionException {
        try {
            int _type = RULE_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:220:11: ( ( '+' | '-' )? ( RULE_INT ( '.' RULE_INT )? ( 'D' | 'H' | 'M' | 'S' | 'MS' | 'US' | 'NS' ) ( '_' )? )+ )
            // InternalSTFunctionLexer.g:220:13: ( '+' | '-' )? ( RULE_INT ( '.' RULE_INT )? ( 'D' | 'H' | 'M' | 'S' | 'MS' | 'US' | 'NS' ) ( '_' )? )+
            {
            // InternalSTFunctionLexer.g:220:13: ( '+' | '-' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='+'||LA6_0=='-') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSTFunctionLexer.g:
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

            // InternalSTFunctionLexer.g:220:24: ( RULE_INT ( '.' RULE_INT )? ( 'D' | 'H' | 'M' | 'S' | 'MS' | 'US' | 'NS' ) ( '_' )? )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSTFunctionLexer.g:220:25: RULE_INT ( '.' RULE_INT )? ( 'D' | 'H' | 'M' | 'S' | 'MS' | 'US' | 'NS' ) ( '_' )?
            	    {
            	    mRULE_INT(); 
            	    // InternalSTFunctionLexer.g:220:34: ( '.' RULE_INT )?
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0=='.') ) {
            	        alt7=1;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // InternalSTFunctionLexer.g:220:35: '.' RULE_INT
            	            {
            	            match('.'); 
            	            mRULE_INT(); 

            	            }
            	            break;

            	    }

            	    // InternalSTFunctionLexer.g:220:50: ( 'D' | 'H' | 'M' | 'S' | 'MS' | 'US' | 'NS' )
            	    int alt8=7;
            	    switch ( input.LA(1) ) {
            	    case 'D':
            	        {
            	        alt8=1;
            	        }
            	        break;
            	    case 'H':
            	        {
            	        alt8=2;
            	        }
            	        break;
            	    case 'M':
            	        {
            	        int LA8_3 = input.LA(2);

            	        if ( (LA8_3=='S') ) {
            	            alt8=5;
            	        }
            	        else {
            	            alt8=3;}
            	        }
            	        break;
            	    case 'S':
            	        {
            	        alt8=4;
            	        }
            	        break;
            	    case 'U':
            	        {
            	        alt8=6;
            	        }
            	        break;
            	    case 'N':
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
            	            // InternalSTFunctionLexer.g:220:51: 'D'
            	            {
            	            match('D'); 

            	            }
            	            break;
            	        case 2 :
            	            // InternalSTFunctionLexer.g:220:55: 'H'
            	            {
            	            match('H'); 

            	            }
            	            break;
            	        case 3 :
            	            // InternalSTFunctionLexer.g:220:59: 'M'
            	            {
            	            match('M'); 

            	            }
            	            break;
            	        case 4 :
            	            // InternalSTFunctionLexer.g:220:63: 'S'
            	            {
            	            match('S'); 

            	            }
            	            break;
            	        case 5 :
            	            // InternalSTFunctionLexer.g:220:67: 'MS'
            	            {
            	            match("MS"); 


            	            }
            	            break;
            	        case 6 :
            	            // InternalSTFunctionLexer.g:220:72: 'US'
            	            {
            	            match("US"); 


            	            }
            	            break;
            	        case 7 :
            	            // InternalSTFunctionLexer.g:220:77: 'NS'
            	            {
            	            match("NS"); 


            	            }
            	            break;

            	    }

            	    // InternalSTFunctionLexer.g:220:83: ( '_' )?
            	    int alt9=2;
            	    int LA9_0 = input.LA(1);

            	    if ( (LA9_0=='_') ) {
            	        alt9=1;
            	    }
            	    switch (alt9) {
            	        case 1 :
            	            // InternalSTFunctionLexer.g:220:83: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME"

    // $ANTLR start "RULE_EXT_INT"
    public final void mRULE_EXT_INT() throws RecognitionException {
        try {
            int _type = RULE_EXT_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:222:14: ( RULE_INT ( 'e' | 'E' ) ( '-' | '+' )? RULE_INT )
            // InternalSTFunctionLexer.g:222:16: RULE_INT ( 'e' | 'E' ) ( '-' | '+' )? RULE_INT
            {
            mRULE_INT(); 
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalSTFunctionLexer.g:222:35: ( '-' | '+' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='+'||LA11_0=='-') ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSTFunctionLexer.g:
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

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_EXT_INT"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:224:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalSTFunctionLexer.g:224:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalSTFunctionLexer.g:224:11: ( '^' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='^') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalSTFunctionLexer.g:224:11: '^'
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

            // InternalSTFunctionLexer.g:224:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='0' && LA13_0<='9')||(LA13_0>='A' && LA13_0<='Z')||LA13_0=='_'||(LA13_0>='a' && LA13_0<='z')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalSTFunctionLexer.g:
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
            	    break loop13;
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
            // InternalSTFunctionLexer.g:226:13: ( '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"' )
            // InternalSTFunctionLexer.g:226:15: '\"' ( '$' . | ~ ( ( '$' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // InternalSTFunctionLexer.g:226:19: ( '$' . | ~ ( ( '$' | '\"' ) ) )*
            loop14:
            do {
                int alt14=3;
                int LA14_0 = input.LA(1);

                if ( (LA14_0=='$') ) {
                    alt14=1;
                }
                else if ( ((LA14_0>='\u0000' && LA14_0<='!')||LA14_0=='#'||(LA14_0>='%' && LA14_0<='\uFFFF')) ) {
                    alt14=2;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSTFunctionLexer.g:226:20: '$' .
            	    {
            	    match('$'); 
            	    matchAny(); 

            	    }
            	    break;
            	case 2 :
            	    // InternalSTFunctionLexer.g:226:26: ~ ( ( '$' | '\"' ) )
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
            	    break loop14;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_WSTRING"
    public final void mRULE_WSTRING() throws RecognitionException {
        try {
            int _type = RULE_WSTRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:228:14: ( '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\'' )
            // InternalSTFunctionLexer.g:228:16: '\\'' ( '$' . | ~ ( ( '$' | '\\'' ) ) )* '\\''
            {
            match('\''); 
            // InternalSTFunctionLexer.g:228:21: ( '$' . | ~ ( ( '$' | '\\'' ) ) )*
            loop15:
            do {
                int alt15=3;
                int LA15_0 = input.LA(1);

                if ( (LA15_0=='$') ) {
                    alt15=1;
                }
                else if ( ((LA15_0>='\u0000' && LA15_0<='#')||(LA15_0>='%' && LA15_0<='&')||(LA15_0>='(' && LA15_0<='\uFFFF')) ) {
                    alt15=2;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalSTFunctionLexer.g:228:22: '$' .
            	    {
            	    match('$'); 
            	    matchAny(); 

            	    }
            	    break;
            	case 2 :
            	    // InternalSTFunctionLexer.g:228:28: ~ ( ( '$' | '\\'' ) )
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
            	    break loop15;
                }
            } while (true);

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WSTRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSTFunctionLexer.g:230:17: ( ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' ) )
            // InternalSTFunctionLexer.g:230:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
            {
            // InternalSTFunctionLexer.g:230:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
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
                    // InternalSTFunctionLexer.g:230:20: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 

                    // InternalSTFunctionLexer.g:230:25: ( options {greedy=false; } : . )*
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
                    	    // InternalSTFunctionLexer.g:230:53: .
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
                    // InternalSTFunctionLexer.g:230:62: '(*' ( options {greedy=false; } : . )* '*)'
                    {
                    match("(*"); 

                    // InternalSTFunctionLexer.g:230:67: ( options {greedy=false; } : . )*
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
                    	    // InternalSTFunctionLexer.g:230:95: .
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
            // InternalSTFunctionLexer.g:232:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalSTFunctionLexer.g:232:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalSTFunctionLexer.g:232:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>='\u0000' && LA19_0<='\t')||(LA19_0>='\u000B' && LA19_0<='\f')||(LA19_0>='\u000E' && LA19_0<='\uFFFF')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSTFunctionLexer.g:232:24: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalSTFunctionLexer.g:232:40: ( ( '\\r' )? '\\n' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='\n'||LA21_0=='\r') ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSTFunctionLexer.g:232:41: ( '\\r' )? '\\n'
                    {
                    // InternalSTFunctionLexer.g:232:41: ( '\\r' )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0=='\r') ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // InternalSTFunctionLexer.g:232:41: '\\r'
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
            // InternalSTFunctionLexer.g:234:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalSTFunctionLexer.g:234:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalSTFunctionLexer.g:234:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            	    // InternalSTFunctionLexer.g:
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
            // InternalSTFunctionLexer.g:236:16: ( . )
            // InternalSTFunctionLexer.g:236:18: .
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
        // InternalSTFunctionLexer.g:1:8: ( LDATE_AND_TIME | DATE_AND_TIME | END_FUNCTION | TIME_OF_DAY | END_REPEAT | VAR_OUTPUT | END_WHILE | VAR_INPUT | CONSTANT | CONTINUE | END_CASE | FUNCTION | VAR_TEMP | WSTRING | END_FOR | END_VAR | STRING | DWORD | END_IF | LDATE | LREAL | LTIME | LWORD | REPEAT | RETURN | UDINT | ULINT | USINT | WCHAR | ARRAY | BOOL | BYTE | CHAR | DATE | DINT | ELSIF | LINT | LTOD | REAL | SINT | TIME | UINT | UNTIL | WHILE | WORD | CASE | ELSE | EXIT | INT | LDT | THEN | TOD | B | D_1 | L | W | X | AND | DT | FOR | LD | LT | MOD | NOT | VAR | XOR | AsteriskAsterisk | FullStopFullStop | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | D | DO | IF | OF | OR | T | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | LeftSquareBracket | RightSquareBracket | RULE_BOOL_VALUES | RULE_NON_DECIMAL | RULE_INT | RULE_TIME | RULE_EXT_INT | RULE_ID | RULE_STRING | RULE_WSTRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt23=111;
        alt23 = dfa23.predict(input);
        switch (alt23) {
            case 1 :
                // InternalSTFunctionLexer.g:1:10: LDATE_AND_TIME
                {
                mLDATE_AND_TIME(); 

                }
                break;
            case 2 :
                // InternalSTFunctionLexer.g:1:25: DATE_AND_TIME
                {
                mDATE_AND_TIME(); 

                }
                break;
            case 3 :
                // InternalSTFunctionLexer.g:1:39: END_FUNCTION
                {
                mEND_FUNCTION(); 

                }
                break;
            case 4 :
                // InternalSTFunctionLexer.g:1:52: TIME_OF_DAY
                {
                mTIME_OF_DAY(); 

                }
                break;
            case 5 :
                // InternalSTFunctionLexer.g:1:64: END_REPEAT
                {
                mEND_REPEAT(); 

                }
                break;
            case 6 :
                // InternalSTFunctionLexer.g:1:75: VAR_OUTPUT
                {
                mVAR_OUTPUT(); 

                }
                break;
            case 7 :
                // InternalSTFunctionLexer.g:1:86: END_WHILE
                {
                mEND_WHILE(); 

                }
                break;
            case 8 :
                // InternalSTFunctionLexer.g:1:96: VAR_INPUT
                {
                mVAR_INPUT(); 

                }
                break;
            case 9 :
                // InternalSTFunctionLexer.g:1:106: CONSTANT
                {
                mCONSTANT(); 

                }
                break;
            case 10 :
                // InternalSTFunctionLexer.g:1:115: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 11 :
                // InternalSTFunctionLexer.g:1:124: END_CASE
                {
                mEND_CASE(); 

                }
                break;
            case 12 :
                // InternalSTFunctionLexer.g:1:133: FUNCTION
                {
                mFUNCTION(); 

                }
                break;
            case 13 :
                // InternalSTFunctionLexer.g:1:142: VAR_TEMP
                {
                mVAR_TEMP(); 

                }
                break;
            case 14 :
                // InternalSTFunctionLexer.g:1:151: WSTRING
                {
                mWSTRING(); 

                }
                break;
            case 15 :
                // InternalSTFunctionLexer.g:1:159: END_FOR
                {
                mEND_FOR(); 

                }
                break;
            case 16 :
                // InternalSTFunctionLexer.g:1:167: END_VAR
                {
                mEND_VAR(); 

                }
                break;
            case 17 :
                // InternalSTFunctionLexer.g:1:175: STRING
                {
                mSTRING(); 

                }
                break;
            case 18 :
                // InternalSTFunctionLexer.g:1:182: DWORD
                {
                mDWORD(); 

                }
                break;
            case 19 :
                // InternalSTFunctionLexer.g:1:188: END_IF
                {
                mEND_IF(); 

                }
                break;
            case 20 :
                // InternalSTFunctionLexer.g:1:195: LDATE
                {
                mLDATE(); 

                }
                break;
            case 21 :
                // InternalSTFunctionLexer.g:1:201: LREAL
                {
                mLREAL(); 

                }
                break;
            case 22 :
                // InternalSTFunctionLexer.g:1:207: LTIME
                {
                mLTIME(); 

                }
                break;
            case 23 :
                // InternalSTFunctionLexer.g:1:213: LWORD
                {
                mLWORD(); 

                }
                break;
            case 24 :
                // InternalSTFunctionLexer.g:1:219: REPEAT
                {
                mREPEAT(); 

                }
                break;
            case 25 :
                // InternalSTFunctionLexer.g:1:226: RETURN
                {
                mRETURN(); 

                }
                break;
            case 26 :
                // InternalSTFunctionLexer.g:1:233: UDINT
                {
                mUDINT(); 

                }
                break;
            case 27 :
                // InternalSTFunctionLexer.g:1:239: ULINT
                {
                mULINT(); 

                }
                break;
            case 28 :
                // InternalSTFunctionLexer.g:1:245: USINT
                {
                mUSINT(); 

                }
                break;
            case 29 :
                // InternalSTFunctionLexer.g:1:251: WCHAR
                {
                mWCHAR(); 

                }
                break;
            case 30 :
                // InternalSTFunctionLexer.g:1:257: ARRAY
                {
                mARRAY(); 

                }
                break;
            case 31 :
                // InternalSTFunctionLexer.g:1:263: BOOL
                {
                mBOOL(); 

                }
                break;
            case 32 :
                // InternalSTFunctionLexer.g:1:268: BYTE
                {
                mBYTE(); 

                }
                break;
            case 33 :
                // InternalSTFunctionLexer.g:1:273: CHAR
                {
                mCHAR(); 

                }
                break;
            case 34 :
                // InternalSTFunctionLexer.g:1:278: DATE
                {
                mDATE(); 

                }
                break;
            case 35 :
                // InternalSTFunctionLexer.g:1:283: DINT
                {
                mDINT(); 

                }
                break;
            case 36 :
                // InternalSTFunctionLexer.g:1:288: ELSIF
                {
                mELSIF(); 

                }
                break;
            case 37 :
                // InternalSTFunctionLexer.g:1:294: LINT
                {
                mLINT(); 

                }
                break;
            case 38 :
                // InternalSTFunctionLexer.g:1:299: LTOD
                {
                mLTOD(); 

                }
                break;
            case 39 :
                // InternalSTFunctionLexer.g:1:304: REAL
                {
                mREAL(); 

                }
                break;
            case 40 :
                // InternalSTFunctionLexer.g:1:309: SINT
                {
                mSINT(); 

                }
                break;
            case 41 :
                // InternalSTFunctionLexer.g:1:314: TIME
                {
                mTIME(); 

                }
                break;
            case 42 :
                // InternalSTFunctionLexer.g:1:319: UINT
                {
                mUINT(); 

                }
                break;
            case 43 :
                // InternalSTFunctionLexer.g:1:324: UNTIL
                {
                mUNTIL(); 

                }
                break;
            case 44 :
                // InternalSTFunctionLexer.g:1:330: WHILE
                {
                mWHILE(); 

                }
                break;
            case 45 :
                // InternalSTFunctionLexer.g:1:336: WORD
                {
                mWORD(); 

                }
                break;
            case 46 :
                // InternalSTFunctionLexer.g:1:341: CASE
                {
                mCASE(); 

                }
                break;
            case 47 :
                // InternalSTFunctionLexer.g:1:346: ELSE
                {
                mELSE(); 

                }
                break;
            case 48 :
                // InternalSTFunctionLexer.g:1:351: EXIT
                {
                mEXIT(); 

                }
                break;
            case 49 :
                // InternalSTFunctionLexer.g:1:356: INT
                {
                mINT(); 

                }
                break;
            case 50 :
                // InternalSTFunctionLexer.g:1:360: LDT
                {
                mLDT(); 

                }
                break;
            case 51 :
                // InternalSTFunctionLexer.g:1:364: THEN
                {
                mTHEN(); 

                }
                break;
            case 52 :
                // InternalSTFunctionLexer.g:1:369: TOD
                {
                mTOD(); 

                }
                break;
            case 53 :
                // InternalSTFunctionLexer.g:1:373: B
                {
                mB(); 

                }
                break;
            case 54 :
                // InternalSTFunctionLexer.g:1:375: D_1
                {
                mD_1(); 

                }
                break;
            case 55 :
                // InternalSTFunctionLexer.g:1:379: L
                {
                mL(); 

                }
                break;
            case 56 :
                // InternalSTFunctionLexer.g:1:381: W
                {
                mW(); 

                }
                break;
            case 57 :
                // InternalSTFunctionLexer.g:1:383: X
                {
                mX(); 

                }
                break;
            case 58 :
                // InternalSTFunctionLexer.g:1:385: AND
                {
                mAND(); 

                }
                break;
            case 59 :
                // InternalSTFunctionLexer.g:1:389: DT
                {
                mDT(); 

                }
                break;
            case 60 :
                // InternalSTFunctionLexer.g:1:392: FOR
                {
                mFOR(); 

                }
                break;
            case 61 :
                // InternalSTFunctionLexer.g:1:396: LD
                {
                mLD(); 

                }
                break;
            case 62 :
                // InternalSTFunctionLexer.g:1:399: LT
                {
                mLT(); 

                }
                break;
            case 63 :
                // InternalSTFunctionLexer.g:1:402: MOD
                {
                mMOD(); 

                }
                break;
            case 64 :
                // InternalSTFunctionLexer.g:1:406: NOT
                {
                mNOT(); 

                }
                break;
            case 65 :
                // InternalSTFunctionLexer.g:1:410: VAR
                {
                mVAR(); 

                }
                break;
            case 66 :
                // InternalSTFunctionLexer.g:1:414: XOR
                {
                mXOR(); 

                }
                break;
            case 67 :
                // InternalSTFunctionLexer.g:1:418: AsteriskAsterisk
                {
                mAsteriskAsterisk(); 

                }
                break;
            case 68 :
                // InternalSTFunctionLexer.g:1:435: FullStopFullStop
                {
                mFullStopFullStop(); 

                }
                break;
            case 69 :
                // InternalSTFunctionLexer.g:1:452: ColonEqualsSign
                {
                mColonEqualsSign(); 

                }
                break;
            case 70 :
                // InternalSTFunctionLexer.g:1:468: LessThanSignEqualsSign
                {
                mLessThanSignEqualsSign(); 

                }
                break;
            case 71 :
                // InternalSTFunctionLexer.g:1:491: LessThanSignGreaterThanSign
                {
                mLessThanSignGreaterThanSign(); 

                }
                break;
            case 72 :
                // InternalSTFunctionLexer.g:1:519: EqualsSignGreaterThanSign
                {
                mEqualsSignGreaterThanSign(); 

                }
                break;
            case 73 :
                // InternalSTFunctionLexer.g:1:545: GreaterThanSignEqualsSign
                {
                mGreaterThanSignEqualsSign(); 

                }
                break;
            case 74 :
                // InternalSTFunctionLexer.g:1:571: AT
                {
                mAT(); 

                }
                break;
            case 75 :
                // InternalSTFunctionLexer.g:1:574: BY
                {
                mBY(); 

                }
                break;
            case 76 :
                // InternalSTFunctionLexer.g:1:577: D
                {
                mD(); 

                }
                break;
            case 77 :
                // InternalSTFunctionLexer.g:1:579: DO
                {
                mDO(); 

                }
                break;
            case 78 :
                // InternalSTFunctionLexer.g:1:582: IF
                {
                mIF(); 

                }
                break;
            case 79 :
                // InternalSTFunctionLexer.g:1:585: OF
                {
                mOF(); 

                }
                break;
            case 80 :
                // InternalSTFunctionLexer.g:1:588: OR
                {
                mOR(); 

                }
                break;
            case 81 :
                // InternalSTFunctionLexer.g:1:591: T
                {
                mT(); 

                }
                break;
            case 82 :
                // InternalSTFunctionLexer.g:1:593: TO
                {
                mTO(); 

                }
                break;
            case 83 :
                // InternalSTFunctionLexer.g:1:596: NumberSign
                {
                mNumberSign(); 

                }
                break;
            case 84 :
                // InternalSTFunctionLexer.g:1:607: Ampersand
                {
                mAmpersand(); 

                }
                break;
            case 85 :
                // InternalSTFunctionLexer.g:1:617: LeftParenthesis
                {
                mLeftParenthesis(); 

                }
                break;
            case 86 :
                // InternalSTFunctionLexer.g:1:633: RightParenthesis
                {
                mRightParenthesis(); 

                }
                break;
            case 87 :
                // InternalSTFunctionLexer.g:1:650: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 88 :
                // InternalSTFunctionLexer.g:1:659: PlusSign
                {
                mPlusSign(); 

                }
                break;
            case 89 :
                // InternalSTFunctionLexer.g:1:668: Comma
                {
                mComma(); 

                }
                break;
            case 90 :
                // InternalSTFunctionLexer.g:1:674: HyphenMinus
                {
                mHyphenMinus(); 

                }
                break;
            case 91 :
                // InternalSTFunctionLexer.g:1:686: FullStop
                {
                mFullStop(); 

                }
                break;
            case 92 :
                // InternalSTFunctionLexer.g:1:695: Solidus
                {
                mSolidus(); 

                }
                break;
            case 93 :
                // InternalSTFunctionLexer.g:1:703: Colon
                {
                mColon(); 

                }
                break;
            case 94 :
                // InternalSTFunctionLexer.g:1:709: Semicolon
                {
                mSemicolon(); 

                }
                break;
            case 95 :
                // InternalSTFunctionLexer.g:1:719: LessThanSign
                {
                mLessThanSign(); 

                }
                break;
            case 96 :
                // InternalSTFunctionLexer.g:1:732: EqualsSign
                {
                mEqualsSign(); 

                }
                break;
            case 97 :
                // InternalSTFunctionLexer.g:1:743: GreaterThanSign
                {
                mGreaterThanSign(); 

                }
                break;
            case 98 :
                // InternalSTFunctionLexer.g:1:759: LeftSquareBracket
                {
                mLeftSquareBracket(); 

                }
                break;
            case 99 :
                // InternalSTFunctionLexer.g:1:777: RightSquareBracket
                {
                mRightSquareBracket(); 

                }
                break;
            case 100 :
                // InternalSTFunctionLexer.g:1:796: RULE_BOOL_VALUES
                {
                mRULE_BOOL_VALUES(); 

                }
                break;
            case 101 :
                // InternalSTFunctionLexer.g:1:813: RULE_NON_DECIMAL
                {
                mRULE_NON_DECIMAL(); 

                }
                break;
            case 102 :
                // InternalSTFunctionLexer.g:1:830: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 103 :
                // InternalSTFunctionLexer.g:1:839: RULE_TIME
                {
                mRULE_TIME(); 

                }
                break;
            case 104 :
                // InternalSTFunctionLexer.g:1:849: RULE_EXT_INT
                {
                mRULE_EXT_INT(); 

                }
                break;
            case 105 :
                // InternalSTFunctionLexer.g:1:862: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 106 :
                // InternalSTFunctionLexer.g:1:870: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 107 :
                // InternalSTFunctionLexer.g:1:882: RULE_WSTRING
                {
                mRULE_WSTRING(); 

                }
                break;
            case 108 :
                // InternalSTFunctionLexer.g:1:895: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 109 :
                // InternalSTFunctionLexer.g:1:911: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 110 :
                // InternalSTFunctionLexer.g:1:927: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 111 :
                // InternalSTFunctionLexer.g:1:935: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA23 dfa23 = new DFA23(this);
    static final String DFA23_eotS =
        "\1\uffff\16\65\1\140\3\65\1\145\1\147\1\152\1\154\1\156\1\65\2\uffff\1\164\1\uffff\1\167\1\uffff\1\171\1\173\3\uffff\2\65\4\u0082\1\57\1\uffff\2\57\2\uffff\5\65\1\uffff\4\65\1\uffff\1\u0095\5\65\1\u009c\1\uffff\26\65\1\u00b5\1\65\1\u00b8\1\65\1\u00ba\3\uffff\3\65\13\uffff\1\u00c3\1\u00c4\20\uffff\1\u0082\2\uffff\1\u0082\3\uffff\2\65\1\uffff\3\65\1\uffff\5\65\2\uffff\6\65\1\uffff\1\65\1\u00d8\4\65\1\u00de\20\65\1\u00ef\1\uffff\2\65\1\uffff\1\65\6\uffff\1\u00f3\1\u00f4\1\u00f5\2\uffff\1\65\1\uffff\12\65\1\u0107\1\u0108\1\65\1\u010b\1\uffff\1\u010c\1\65\1\uffff\3\65\1\u0113\1\65\1\uffff\20\65\1\uffff\2\65\4\uffff\3\65\1\uffff\1\65\1\uffff\1\65\1\uffff\1\65\1\uffff\6\65\1\u0135\2\uffff\1\65\3\uffff\5\65\2\uffff\1\65\1\u010c\2\65\1\u013f\1\uffff\1\65\1\uffff\2\65\1\uffff\3\65\1\uffff\1\u0146\1\u0147\2\uffff\1\65\4\uffff\1\65\1\uffff\6\65\1\u0150\1\uffff\10\65\2\uffff\1\65\1\u015a\1\u015b\5\uffff\3\65\1\u015f\3\65\1\u0163\1\uffff\10\65\3\uffff\3\65\1\uffff\2\65\1\u0171\1\uffff\3\65\1\u0175\1\u0176\1\u0177\1\u0178\1\uffff\4\65\1\u017d\1\uffff\2\65\1\u0180\4\uffff\3\65\1\u0184\1\uffff\1\65\1\u0186\1\uffff\3\65\1\uffff\1\65\1\uffff\2\65\1\u018d\1\uffff\2\65\1\uffff\1\65\2\uffff";
    static final String DFA23_eofS =
        "\u0191\uffff";
    static final String DFA23_minS =
        "\1\0\1\104\1\43\1\114\1\43\3\101\1\103\1\111\1\105\1\104\1\116\1\117\1\106\1\45\3\117\1\52\2\75\1\76\1\75\1\106\2\uffff\1\52\1\uffff\1\60\1\uffff\1\60\1\52\3\uffff\1\43\1\117\2\43\2\56\1\101\1\uffff\2\0\2\uffff\1\43\1\105\1\43\1\117\1\116\1\uffff\1\124\1\117\1\116\1\43\1\uffff\1\60\1\104\1\123\1\111\1\115\1\105\1\60\1\uffff\1\125\1\122\1\116\1\101\1\123\1\116\1\122\1\114\1\124\1\110\1\111\2\122\1\116\1\101\3\111\1\116\1\124\1\122\1\104\1\60\1\117\1\60\1\124\1\60\1\102\2\uffff\1\104\1\124\1\122\13\uffff\2\60\17\uffff\1\60\1\56\2\uffff\1\43\3\uffff\1\124\1\43\1\uffff\1\101\1\115\1\104\1\uffff\1\122\1\124\1\105\1\122\1\124\2\uffff\1\137\1\105\1\124\1\105\1\116\1\43\1\uffff\1\105\1\60\1\123\1\122\1\105\1\103\1\60\1\123\1\122\1\101\1\114\1\104\1\111\1\124\1\105\1\125\1\114\3\116\1\124\1\111\1\101\1\60\1\uffff\1\114\1\105\1\uffff\1\43\6\uffff\3\60\2\uffff\1\105\1\uffff\1\114\1\105\1\43\1\104\2\43\1\104\1\43\1\103\1\106\2\60\1\43\1\60\1\uffff\1\60\1\111\1\uffff\1\124\1\111\1\43\1\60\1\124\1\uffff\1\105\1\111\1\122\1\105\1\43\1\116\1\43\1\101\1\122\1\43\3\124\1\43\1\114\1\131\1\uffff\2\43\4\uffff\3\43\1\uffff\1\43\1\uffff\1\101\1\uffff\1\43\1\uffff\1\117\1\105\1\110\2\101\1\106\1\60\2\uffff\1\117\3\uffff\1\125\1\116\1\105\1\101\1\116\2\uffff\1\111\1\60\1\116\1\43\1\60\1\uffff\1\107\1\uffff\1\124\1\116\1\uffff\3\43\1\uffff\2\60\2\uffff\1\101\4\uffff\1\116\1\uffff\1\116\1\122\1\120\1\111\1\123\1\122\1\60\1\uffff\1\106\1\124\1\120\1\115\1\116\1\125\1\117\1\107\2\uffff\1\43\2\60\5\uffff\1\116\1\104\1\103\1\60\1\105\1\114\1\105\1\60\1\uffff\1\137\1\120\1\125\1\120\1\124\1\105\1\116\1\43\3\uffff\1\104\1\137\1\124\1\uffff\1\101\1\105\1\60\1\uffff\1\104\1\125\1\124\4\60\1\uffff\1\137\1\124\1\111\1\124\1\60\1\uffff\1\101\1\124\1\60\4\uffff\1\124\1\111\1\117\1\60\1\uffff\1\131\1\60\1\uffff\1\111\1\115\1\116\1\uffff\1\43\1\uffff\1\115\1\105\1\60\1\uffff\1\105\1\43\1\uffff\1\43\2\uffff";
    static final String DFA23_maxS =
        "\1\uffff\2\167\1\170\1\157\1\141\1\157\1\165\1\163\1\164\1\145\1\163\1\164\1\171\1\156\1\56\3\157\1\52\1\75\2\76\1\75\1\162\2\uffff\1\52\1\uffff\1\71\1\uffff\1\71\1\57\3\uffff\1\157\1\165\4\145\1\172\1\uffff\2\uffff\2\uffff\1\164\1\145\2\157\1\156\1\uffff\1\164\1\157\1\156\1\43\1\uffff\1\172\1\144\1\163\1\151\1\155\1\145\1\172\1\uffff\1\125\1\162\1\156\1\141\1\163\1\156\1\162\1\114\1\164\1\150\1\151\2\162\1\156\1\164\3\151\1\156\1\164\1\162\1\144\1\172\1\157\1\172\1\164\1\172\1\170\2\uffff\1\144\1\164\1\162\13\uffff\2\172\17\uffff\1\71\1\145\2\uffff\1\145\3\uffff\1\164\1\43\1\uffff\1\141\1\155\1\144\1\uffff\1\162\1\164\1\145\1\162\1\164\2\uffff\1\137\1\151\1\164\1\145\1\156\1\43\1\uffff\1\105\1\172\1\164\1\162\1\145\1\143\1\172\1\123\1\162\1\141\1\154\1\144\1\151\1\164\1\145\1\165\1\154\3\156\1\164\1\151\1\141\1\172\1\uffff\1\154\1\145\1\uffff\1\43\6\uffff\3\172\2\uffff\1\145\1\uffff\1\154\1\145\1\43\1\144\1\43\1\137\1\144\1\43\1\167\1\146\2\172\1\137\1\172\1\uffff\1\172\1\164\1\uffff\1\164\1\151\1\43\1\172\1\164\1\uffff\1\105\1\151\1\162\1\145\1\43\1\156\1\43\1\141\1\162\1\43\3\164\1\43\1\154\1\171\1\uffff\2\43\4\uffff\1\137\2\43\1\uffff\1\43\1\uffff\1\141\1\uffff\1\43\1\uffff\1\165\1\145\1\150\2\141\1\146\1\172\2\uffff\1\157\3\uffff\1\165\1\156\1\145\1\141\1\156\2\uffff\1\151\1\172\1\156\1\43\1\172\1\uffff\1\147\1\uffff\1\164\1\156\1\uffff\3\43\1\uffff\2\172\2\uffff\1\141\4\uffff\1\156\1\uffff\1\156\1\162\1\160\1\151\1\163\1\162\1\172\1\uffff\1\146\1\164\1\160\1\155\1\156\1\165\1\157\1\147\2\uffff\1\43\2\172\5\uffff\1\156\1\144\1\143\1\172\1\145\1\154\1\145\1\172\1\uffff\1\137\1\160\1\165\1\160\1\164\1\145\1\156\1\43\3\uffff\1\144\1\137\1\164\1\uffff\1\141\1\145\1\172\1\uffff\1\144\1\165\1\164\4\172\1\uffff\1\137\1\164\1\151\1\164\1\172\1\uffff\1\141\1\164\1\172\4\uffff\1\164\1\151\1\157\1\172\1\uffff\1\171\1\172\1\uffff\1\151\1\155\1\156\1\uffff\1\43\1\uffff\1\155\1\145\1\172\1\uffff\1\145\1\43\1\uffff\1\43\2\uffff";
    static final String DFA23_acceptS =
        "\31\uffff\1\123\1\124\1\uffff\1\126\1\uffff\1\131\2\uffff\1\136\1\142\1\143\7\uffff\1\151\2\uffff\1\156\1\157\5\uffff\1\151\4\uffff\1\114\7\uffff\1\121\34\uffff\1\104\1\133\3\uffff\1\103\1\127\1\105\1\135\1\106\1\107\1\137\1\110\1\140\1\111\1\141\2\uffff\1\123\1\124\1\154\1\125\1\126\1\147\1\130\1\131\1\132\1\155\1\134\1\136\1\142\1\143\1\145\2\uffff\1\146\1\150\1\uffff\1\152\1\153\1\156\2\uffff\1\75\3\uffff\1\76\5\uffff\1\73\1\115\6\uffff\1\122\30\uffff\1\112\2\uffff\1\113\1\uffff\1\116\1\65\1\66\1\67\1\70\1\71\3\uffff\1\117\1\120\1\uffff\1\62\16\uffff\1\64\2\uffff\1\101\5\uffff\1\74\20\uffff\1\72\2\uffff\1\61\1\77\1\100\1\102\3\uffff\1\46\1\uffff\1\45\1\uffff\1\42\1\uffff\1\43\7\uffff\1\57\1\60\1\uffff\1\51\1\63\1\144\5\uffff\1\41\1\56\5\uffff\1\55\1\uffff\1\50\2\uffff\1\47\3\uffff\1\52\2\uffff\1\37\1\40\1\uffff\1\24\1\25\1\26\1\27\1\uffff\1\22\7\uffff\1\44\10\uffff\1\35\1\54\3\uffff\1\32\1\33\1\34\1\53\1\36\10\uffff\1\23\10\uffff\1\21\1\30\1\31\3\uffff\1\17\3\uffff\1\20\7\uffff\1\16\5\uffff\1\13\3\uffff\1\15\1\11\1\12\1\14\4\uffff\1\7\2\uffff\1\10\3\uffff\1\5\1\uffff\1\6\3\uffff\1\4\2\uffff\1\3\1\uffff\1\2\1\1";
    static final String DFA23_specialS =
        "\1\0\53\uffff\1\1\1\2\u0163\uffff}>";
    static final String[] DFA23_transitionS = {
            "\11\57\2\56\2\57\1\56\22\57\1\56\1\57\1\54\1\31\2\57\1\32\1\55\1\33\1\34\1\23\1\35\1\36\1\37\1\17\1\40\1\51\1\50\1\46\5\51\1\47\1\51\1\24\1\41\1\25\1\26\1\27\2\57\1\14\1\15\1\6\1\2\1\3\1\7\2\53\1\16\2\53\1\1\1\20\1\21\1\30\2\53\1\12\1\11\1\4\1\13\1\5\1\10\1\22\2\53\1\42\1\57\1\43\1\52\1\53\1\57\1\14\1\15\1\6\1\2\1\3\1\45\2\53\1\16\2\53\1\1\1\20\1\21\1\30\2\53\1\12\1\11\1\44\1\13\1\5\1\10\1\22\2\53\uff85\57",
            "\1\60\4\uffff\1\64\10\uffff\1\61\1\uffff\1\62\2\uffff\1\63\14\uffff\1\60\4\uffff\1\64\10\uffff\1\61\1\uffff\1\62\2\uffff\1\63",
            "\1\72\35\uffff\1\66\7\uffff\1\70\5\uffff\1\73\4\uffff\1\71\2\uffff\1\67\11\uffff\1\66\7\uffff\1\70\5\uffff\1\73\4\uffff\1\71\2\uffff\1\67",
            "\1\75\1\uffff\1\74\11\uffff\1\76\23\uffff\1\75\1\uffff\1\74\11\uffff\1\76",
            "\1\102\44\uffff\1\100\1\77\5\uffff\1\101\2\uffff\1\103\25\uffff\1\100\1\77\5\uffff\1\101",
            "\1\104\37\uffff\1\104",
            "\1\107\6\uffff\1\106\6\uffff\1\105\21\uffff\1\107\6\uffff\1\106\6\uffff\1\105",
            "\1\112\15\uffff\1\111\5\uffff\1\110\31\uffff\1\111\5\uffff\1\110",
            "\1\114\4\uffff\1\115\6\uffff\1\116\3\uffff\1\113\17\uffff\1\114\4\uffff\1\115\6\uffff\1\116\3\uffff\1\113",
            "\1\120\12\uffff\1\117\24\uffff\1\120\12\uffff\1\117",
            "\1\121\37\uffff\1\121",
            "\1\122\4\uffff\1\125\2\uffff\1\123\1\uffff\1\126\4\uffff\1\124\20\uffff\1\122\4\uffff\1\125\2\uffff\1\123\1\uffff\1\126\4\uffff\1\124",
            "\1\130\3\uffff\1\127\1\uffff\1\131\31\uffff\1\130\3\uffff\1\127\1\uffff\1\131",
            "\1\132\11\uffff\1\133\25\uffff\1\132\11\uffff\1\133",
            "\1\135\7\uffff\1\134\27\uffff\1\135\7\uffff\1\134",
            "\1\136\10\uffff\1\137",
            "\1\141\37\uffff\1\141",
            "\1\142\37\uffff\1\142",
            "\1\143\37\uffff\1\143",
            "\1\144",
            "\1\146",
            "\1\150\1\151",
            "\1\153",
            "\1\155",
            "\1\157\13\uffff\1\160\23\uffff\1\157\13\uffff\1\160",
            "",
            "",
            "\1\163",
            "",
            "\12\166",
            "",
            "\12\166",
            "\1\163\4\uffff\1\172",
            "",
            "",
            "",
            "\1\102\44\uffff\1\100\1\77\5\uffff\1\101\30\uffff\1\100\1\77\5\uffff\1\101",
            "\1\111\5\uffff\1\110\31\uffff\1\111\5\uffff\1\110",
            "\1\177\12\uffff\1\166\1\uffff\12\u0081\12\uffff\1\166\1\u0083\2\uffff\1\166\4\uffff\2\166\4\uffff\1\166\1\uffff\1\166\11\uffff\1\u0080\5\uffff\1\u0083",
            "\1\177\12\uffff\1\166\1\uffff\12\u0081\12\uffff\1\166\1\u0083\2\uffff\1\166\4\uffff\2\166\4\uffff\1\166\1\uffff\1\166\11\uffff\1\u0080\5\uffff\1\u0083",
            "\1\166\1\uffff\6\u0081\1\u0084\3\u0081\12\uffff\1\166\1\u0083\2\uffff\1\166\4\uffff\2\166\4\uffff\1\166\1\uffff\1\166\11\uffff\1\u0080\5\uffff\1\u0083",
            "\1\166\1\uffff\12\u0081\12\uffff\1\166\1\u0083\2\uffff\1\166\4\uffff\2\166\4\uffff\1\166\1\uffff\1\166\11\uffff\1\u0080\5\uffff\1\u0083",
            "\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\0\u0085",
            "\0\u0086",
            "",
            "",
            "\1\u008a\35\uffff\1\u0088\22\uffff\1\u0089\14\uffff\1\u0088\22\uffff\1\u0089",
            "\1\u008b\37\uffff\1\u008b",
            "\1\u008e\45\uffff\1\u008c\5\uffff\1\u008d\31\uffff\1\u008c\5\uffff\1\u008d",
            "\1\u008f\37\uffff\1\u008f",
            "\1\u0090\37\uffff\1\u0090",
            "",
            "\1\u0091\37\uffff\1\u0091",
            "\1\u0092\37\uffff\1\u0092",
            "\1\u0093\37\uffff\1\u0093",
            "\1\u0094",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0096\37\uffff\1\u0096",
            "\1\u0097\37\uffff\1\u0097",
            "\1\u0098\37\uffff\1\u0098",
            "\1\u0099\37\uffff\1\u0099",
            "\1\u009a\37\uffff\1\u009a",
            "\12\65\7\uffff\3\65\1\u009b\26\65\4\uffff\1\65\1\uffff\3\65\1\u009b\26\65",
            "",
            "\1\u009d",
            "\1\u009e\37\uffff\1\u009e",
            "\1\u009f\37\uffff\1\u009f",
            "\1\u00a0\37\uffff\1\u00a0",
            "\1\u00a1\37\uffff\1\u00a1",
            "\1\u00a2\37\uffff\1\u00a2",
            "\1\u00a3\37\uffff\1\u00a3",
            "\1\u00a4",
            "\1\u00a5\37\uffff\1\u00a5",
            "\1\u00a6\37\uffff\1\u00a6",
            "\1\u00a7\37\uffff\1\u00a7",
            "\1\u00a8\37\uffff\1\u00a8",
            "\1\u00a9\37\uffff\1\u00a9",
            "\1\u00aa\37\uffff\1\u00aa",
            "\1\u00ad\16\uffff\1\u00ab\3\uffff\1\u00ac\14\uffff\1\u00ad\16\uffff\1\u00ab\3\uffff\1\u00ac",
            "\1\u00ae\37\uffff\1\u00ae",
            "\1\u00af\37\uffff\1\u00af",
            "\1\u00b0\37\uffff\1\u00b0",
            "\1\u00b1\37\uffff\1\u00b1",
            "\1\u00b2\37\uffff\1\u00b2",
            "\1\u00b3\37\uffff\1\u00b3",
            "\1\u00b4\37\uffff\1\u00b4",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00b6\37\uffff\1\u00b6",
            "\12\65\7\uffff\23\65\1\u00b7\6\65\4\uffff\1\65\1\uffff\23\65\1\u00b7\6\65",
            "\1\u00b9\37\uffff\1\u00b9",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00bb\1\uffff\1\u00bc\7\uffff\1\u00bd\12\uffff\1\u00be\1\u00bf\11\uffff\1\u00bb\1\uffff\1\u00bc\7\uffff\1\u00bd\12\uffff\1\u00be\1\u00bf",
            "",
            "",
            "\1\u00c0\37\uffff\1\u00c0",
            "\1\u00c1\37\uffff\1\u00c1",
            "\1\u00c2\37\uffff\1\u00c2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\u0081",
            "\1\166\1\uffff\12\u0081\12\uffff\1\166\1\u0083\2\uffff\1\166\4\uffff\2\166\4\uffff\1\166\1\uffff\1\166\11\uffff\1\u0080\5\uffff\1\u0083",
            "",
            "",
            "\1\177\12\uffff\1\166\1\uffff\12\u0081\12\uffff\1\166\1\u0083\2\uffff\1\166\4\uffff\2\166\4\uffff\1\166\1\uffff\1\166\11\uffff\1\u0080\5\uffff\1\u0083",
            "",
            "",
            "",
            "\1\u00c5\37\uffff\1\u00c5",
            "\1\u00c6",
            "",
            "\1\u00c7\37\uffff\1\u00c7",
            "\1\u00c8\37\uffff\1\u00c8",
            "\1\u00c9\37\uffff\1\u00c9",
            "",
            "\1\u00ca\37\uffff\1\u00ca",
            "\1\u00cb\37\uffff\1\u00cb",
            "\1\u00cc\37\uffff\1\u00cc",
            "\1\u00cd\37\uffff\1\u00cd",
            "\1\u00ce\37\uffff\1\u00ce",
            "",
            "",
            "\1\u00cf",
            "\1\u00d1\3\uffff\1\u00d0\33\uffff\1\u00d1\3\uffff\1\u00d0",
            "\1\u00d2\37\uffff\1\u00d2",
            "\1\u00d3\37\uffff\1\u00d3",
            "\1\u00d4\37\uffff\1\u00d4",
            "\1\u00d5",
            "",
            "\1\u00d6",
            "\12\65\7\uffff\32\65\4\uffff\1\u00d7\1\uffff\32\65",
            "\1\u00d9\1\u00da\36\uffff\1\u00d9\1\u00da",
            "\1\u00db\37\uffff\1\u00db",
            "\1\u00dc\37\uffff\1\u00dc",
            "\1\u00dd\37\uffff\1\u00dd",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u00df",
            "\1\u00e0\37\uffff\1\u00e0",
            "\1\u00e1\37\uffff\1\u00e1",
            "\1\u00e2\37\uffff\1\u00e2",
            "\1\u00e3\37\uffff\1\u00e3",
            "\1\u00e4\37\uffff\1\u00e4",
            "\1\u00e5\37\uffff\1\u00e5",
            "\1\u00e6\37\uffff\1\u00e6",
            "\1\u00e7\37\uffff\1\u00e7",
            "\1\u00e8\37\uffff\1\u00e8",
            "\1\u00e9\37\uffff\1\u00e9",
            "\1\u00ea\37\uffff\1\u00ea",
            "\1\u00eb\37\uffff\1\u00eb",
            "\1\u00ec\37\uffff\1\u00ec",
            "\1\u00ed\37\uffff\1\u00ed",
            "\1\u00ee\37\uffff\1\u00ee",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u00f0\37\uffff\1\u00f0",
            "\1\u00f1\37\uffff\1\u00f1",
            "",
            "\1\u00f2",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u00f6\37\uffff\1\u00f6",
            "",
            "\1\u00f7\37\uffff\1\u00f7",
            "\1\u00f8\37\uffff\1\u00f8",
            "\1\u00f9",
            "\1\u00fa\37\uffff\1\u00fa",
            "\1\u00fb",
            "\1\u00fd\73\uffff\1\u00fc",
            "\1\u00fe\37\uffff\1\u00fe",
            "\1\u00ff",
            "\1\u0103\2\uffff\1\u0100\2\uffff\1\u0105\10\uffff\1\u0101\3\uffff\1\u0104\1\u0102\13\uffff\1\u0103\2\uffff\1\u0100\2\uffff\1\u0105\10\uffff\1\u0101\3\uffff\1\u0104\1\u0102",
            "\1\u0106\37\uffff\1\u0106",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u010a\73\uffff\1\u0109",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u010e\5\uffff\1\u010d\4\uffff\1\u010f\24\uffff\1\u010e\5\uffff\1\u010d\4\uffff\1\u010f",
            "",
            "\1\u0110\37\uffff\1\u0110",
            "\1\u0111\37\uffff\1\u0111",
            "\1\u0112",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0114\37\uffff\1\u0114",
            "",
            "\1\u0115",
            "\1\u0116\37\uffff\1\u0116",
            "\1\u0117\37\uffff\1\u0117",
            "\1\u0118\37\uffff\1\u0118",
            "\1\u0119",
            "\1\u011a\37\uffff\1\u011a",
            "\1\u011b",
            "\1\u011c\37\uffff\1\u011c",
            "\1\u011d\37\uffff\1\u011d",
            "\1\u011e",
            "\1\u011f\37\uffff\1\u011f",
            "\1\u0120\37\uffff\1\u0120",
            "\1\u0121\37\uffff\1\u0121",
            "\1\u0122",
            "\1\u0123\37\uffff\1\u0123",
            "\1\u0124\37\uffff\1\u0124",
            "",
            "\1\u0125",
            "\1\u0126",
            "",
            "",
            "",
            "",
            "\1\u0128\73\uffff\1\u0127",
            "\1\u0129",
            "\1\u012a",
            "",
            "\1\u012b",
            "",
            "\1\u012c\37\uffff\1\u012c",
            "",
            "\1\u012d",
            "",
            "\1\u012f\5\uffff\1\u012e\31\uffff\1\u012f\5\uffff\1\u012e",
            "\1\u0130\37\uffff\1\u0130",
            "\1\u0131\37\uffff\1\u0131",
            "\1\u0132\37\uffff\1\u0132",
            "\1\u0133\37\uffff\1\u0133",
            "\1\u0134\37\uffff\1\u0134",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u0136\37\uffff\1\u0136",
            "",
            "",
            "",
            "\1\u0137\37\uffff\1\u0137",
            "\1\u0138\37\uffff\1\u0138",
            "\1\u0139\37\uffff\1\u0139",
            "\1\u013a\37\uffff\1\u013a",
            "\1\u013b\37\uffff\1\u013b",
            "",
            "",
            "\1\u013c\37\uffff\1\u013c",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u013d\37\uffff\1\u013d",
            "\1\u013e",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0140\37\uffff\1\u0140",
            "",
            "\1\u0141\37\uffff\1\u0141",
            "\1\u0142\37\uffff\1\u0142",
            "",
            "\1\u0143",
            "\1\u0144",
            "\1\u0145",
            "",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "\1\u0148\37\uffff\1\u0148",
            "",
            "",
            "",
            "",
            "\1\u0149\37\uffff\1\u0149",
            "",
            "\1\u014a\37\uffff\1\u014a",
            "\1\u014b\37\uffff\1\u014b",
            "\1\u014c\37\uffff\1\u014c",
            "\1\u014d\37\uffff\1\u014d",
            "\1\u014e\37\uffff\1\u014e",
            "\1\u014f\37\uffff\1\u014f",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0151\37\uffff\1\u0151",
            "\1\u0152\37\uffff\1\u0152",
            "\1\u0153\37\uffff\1\u0153",
            "\1\u0154\37\uffff\1\u0154",
            "\1\u0155\37\uffff\1\u0155",
            "\1\u0156\37\uffff\1\u0156",
            "\1\u0157\37\uffff\1\u0157",
            "\1\u0158\37\uffff\1\u0158",
            "",
            "",
            "\1\u0159",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "",
            "",
            "\1\u015c\37\uffff\1\u015c",
            "\1\u015d\37\uffff\1\u015d",
            "\1\u015e\37\uffff\1\u015e",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\1\u0160\37\uffff\1\u0160",
            "\1\u0161\37\uffff\1\u0161",
            "\1\u0162\37\uffff\1\u0162",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0164",
            "\1\u0165\37\uffff\1\u0165",
            "\1\u0166\37\uffff\1\u0166",
            "\1\u0167\37\uffff\1\u0167",
            "\1\u0168\37\uffff\1\u0168",
            "\1\u0169\37\uffff\1\u0169",
            "\1\u016a\37\uffff\1\u016a",
            "\1\u016b",
            "",
            "",
            "",
            "\1\u016c\37\uffff\1\u016c",
            "\1\u016d",
            "\1\u016e\37\uffff\1\u016e",
            "",
            "\1\u016f\37\uffff\1\u016f",
            "\1\u0170\37\uffff\1\u0170",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0172\37\uffff\1\u0172",
            "\1\u0173\37\uffff\1\u0173",
            "\1\u0174\37\uffff\1\u0174",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0179",
            "\1\u017a\37\uffff\1\u017a",
            "\1\u017b\37\uffff\1\u017b",
            "\1\u017c\37\uffff\1\u017c",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u017e\37\uffff\1\u017e",
            "\1\u017f\37\uffff\1\u017f",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "",
            "",
            "",
            "\1\u0181\37\uffff\1\u0181",
            "\1\u0182\37\uffff\1\u0182",
            "\1\u0183\37\uffff\1\u0183",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0185\37\uffff\1\u0185",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u0187\37\uffff\1\u0187",
            "\1\u0188\37\uffff\1\u0188",
            "\1\u0189\37\uffff\1\u0189",
            "",
            "\1\u018a",
            "",
            "\1\u018b\37\uffff\1\u018b",
            "\1\u018c\37\uffff\1\u018c",
            "\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65",
            "",
            "\1\u018e\37\uffff\1\u018e",
            "\1\u018f",
            "",
            "\1\u0190",
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
            return "1:1: Tokens : ( LDATE_AND_TIME | DATE_AND_TIME | END_FUNCTION | TIME_OF_DAY | END_REPEAT | VAR_OUTPUT | END_WHILE | VAR_INPUT | CONSTANT | CONTINUE | END_CASE | FUNCTION | VAR_TEMP | WSTRING | END_FOR | END_VAR | STRING | DWORD | END_IF | LDATE | LREAL | LTIME | LWORD | REPEAT | RETURN | UDINT | ULINT | USINT | WCHAR | ARRAY | BOOL | BYTE | CHAR | DATE | DINT | ELSIF | LINT | LTOD | REAL | SINT | TIME | UINT | UNTIL | WHILE | WORD | CASE | ELSE | EXIT | INT | LDT | THEN | TOD | B | D_1 | L | W | X | AND | DT | FOR | LD | LT | MOD | NOT | VAR | XOR | AsteriskAsterisk | FullStopFullStop | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | D | DO | IF | OF | OR | T | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | LeftSquareBracket | RightSquareBracket | RULE_BOOL_VALUES | RULE_NON_DECIMAL | RULE_INT | RULE_TIME | RULE_EXT_INT | RULE_ID | RULE_STRING | RULE_WSTRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA23_0 = input.LA(1);

                        s = -1;
                        if ( (LA23_0=='L'||LA23_0=='l') ) {s = 1;}

                        else if ( (LA23_0=='D'||LA23_0=='d') ) {s = 2;}

                        else if ( (LA23_0=='E'||LA23_0=='e') ) {s = 3;}

                        else if ( (LA23_0=='T') ) {s = 4;}

                        else if ( (LA23_0=='V'||LA23_0=='v') ) {s = 5;}

                        else if ( (LA23_0=='C'||LA23_0=='c') ) {s = 6;}

                        else if ( (LA23_0=='F') ) {s = 7;}

                        else if ( (LA23_0=='W'||LA23_0=='w') ) {s = 8;}

                        else if ( (LA23_0=='S'||LA23_0=='s') ) {s = 9;}

                        else if ( (LA23_0=='R'||LA23_0=='r') ) {s = 10;}

                        else if ( (LA23_0=='U'||LA23_0=='u') ) {s = 11;}

                        else if ( (LA23_0=='A'||LA23_0=='a') ) {s = 12;}

                        else if ( (LA23_0=='B'||LA23_0=='b') ) {s = 13;}

                        else if ( (LA23_0=='I'||LA23_0=='i') ) {s = 14;}

                        else if ( (LA23_0=='.') ) {s = 15;}

                        else if ( (LA23_0=='M'||LA23_0=='m') ) {s = 16;}

                        else if ( (LA23_0=='N'||LA23_0=='n') ) {s = 17;}

                        else if ( (LA23_0=='X'||LA23_0=='x') ) {s = 18;}

                        else if ( (LA23_0=='*') ) {s = 19;}

                        else if ( (LA23_0==':') ) {s = 20;}

                        else if ( (LA23_0=='<') ) {s = 21;}

                        else if ( (LA23_0=='=') ) {s = 22;}

                        else if ( (LA23_0=='>') ) {s = 23;}

                        else if ( (LA23_0=='O'||LA23_0=='o') ) {s = 24;}

                        else if ( (LA23_0=='#') ) {s = 25;}

                        else if ( (LA23_0=='&') ) {s = 26;}

                        else if ( (LA23_0=='(') ) {s = 27;}

                        else if ( (LA23_0==')') ) {s = 28;}

                        else if ( (LA23_0=='+') ) {s = 29;}

                        else if ( (LA23_0==',') ) {s = 30;}

                        else if ( (LA23_0=='-') ) {s = 31;}

                        else if ( (LA23_0=='/') ) {s = 32;}

                        else if ( (LA23_0==';') ) {s = 33;}

                        else if ( (LA23_0=='[') ) {s = 34;}

                        else if ( (LA23_0==']') ) {s = 35;}

                        else if ( (LA23_0=='t') ) {s = 36;}

                        else if ( (LA23_0=='f') ) {s = 37;}

                        else if ( (LA23_0=='2') ) {s = 38;}

                        else if ( (LA23_0=='8') ) {s = 39;}

                        else if ( (LA23_0=='1') ) {s = 40;}

                        else if ( (LA23_0=='0'||(LA23_0>='3' && LA23_0<='7')||LA23_0=='9') ) {s = 41;}

                        else if ( (LA23_0=='^') ) {s = 42;}

                        else if ( ((LA23_0>='G' && LA23_0<='H')||(LA23_0>='J' && LA23_0<='K')||(LA23_0>='P' && LA23_0<='Q')||(LA23_0>='Y' && LA23_0<='Z')||LA23_0=='_'||(LA23_0>='g' && LA23_0<='h')||(LA23_0>='j' && LA23_0<='k')||(LA23_0>='p' && LA23_0<='q')||(LA23_0>='y' && LA23_0<='z')) ) {s = 43;}

                        else if ( (LA23_0=='\"') ) {s = 44;}

                        else if ( (LA23_0=='\'') ) {s = 45;}

                        else if ( ((LA23_0>='\t' && LA23_0<='\n')||LA23_0=='\r'||LA23_0==' ') ) {s = 46;}

                        else if ( ((LA23_0>='\u0000' && LA23_0<='\b')||(LA23_0>='\u000B' && LA23_0<='\f')||(LA23_0>='\u000E' && LA23_0<='\u001F')||LA23_0=='!'||(LA23_0>='$' && LA23_0<='%')||(LA23_0>='?' && LA23_0<='@')||LA23_0=='\\'||LA23_0=='`'||(LA23_0>='{' && LA23_0<='\uFFFF')) ) {s = 47;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA23_44 = input.LA(1);

                        s = -1;
                        if ( ((LA23_44>='\u0000' && LA23_44<='\uFFFF')) ) {s = 133;}

                        else s = 47;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA23_45 = input.LA(1);

                        s = -1;
                        if ( ((LA23_45>='\u0000' && LA23_45<='\uFFFF')) ) {s = 134;}

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