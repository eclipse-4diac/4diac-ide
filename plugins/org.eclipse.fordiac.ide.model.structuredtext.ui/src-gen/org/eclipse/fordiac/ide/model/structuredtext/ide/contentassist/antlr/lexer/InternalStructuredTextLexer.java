package org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalStructuredTextLexer extends Lexer {
    public static final int LWORD=27;
    public static final int LessThanSignGreaterThanSign=68;
    public static final int EqualsSignGreaterThanSign=69;
    public static final int VAR=62;
    public static final int END_IF=16;
    public static final int ULINT=30;
    public static final int END_CASE=12;
    public static final int LessThanSign=92;
    public static final int RULE_BIT=101;
    public static final int LeftParenthesis=82;
    public static final int BYTE=36;
    public static final int ELSE=41;
    public static final int RULE_TIME=106;
    public static final int IF=75;
    public static final int LINT=43;
    public static final int GreaterThanSign=94;
    public static final int WORD=51;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=109;
    public static final int TOD=61;
    public static final int RULE_DIGIT=100;
    public static final int DINT=40;
    public static final int UDINT=29;
    public static final int CASE=37;
    public static final int GreaterThanSignEqualsSign=70;
    public static final int AT=71;
    public static final int RULE_DATE=108;
    public static final int RULE_OCTAL_INT=111;
    public static final int PlusSign=85;
    public static final int END_FOR=13;
    public static final int RULE_ML_COMMENT=118;
    public static final int THEN=47;
    public static final int XOR=63;
    public static final int LeftSquareBracket=97;
    public static final int EXIT=42;
    public static final int TIME_OF_DAY=7;
    public static final int B=52;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=17;
    public static final int D=53;
    public static final int E=95;
    public static final int RULE_TIMEOFDAY=105;
    public static final int CHAR=38;
    public static final int RULE_D_BYTE_CHAR_STR=116;
    public static final int RULE_UNSIGNED_INT=104;
    public static final int LTIME=26;
    public static final int Comma=86;
    public static final int HyphenMinus=87;
    public static final int T=96;
    public static final int W=54;
    public static final int BY=72;
    public static final int X=55;
    public static final int ELSIF=22;
    public static final int END_REPEAT=8;
    public static final int LessThanSignEqualsSign=67;
    public static final int Solidus=89;
    public static final int RULE_OCTAL_DIGIT=102;
    public static final int RULE_HEX_INT=112;
    public static final int FullStop=88;
    public static final int CONSTANT=10;
    public static final int CONTINUE=11;
    public static final int Semicolon=91;
    public static final int RULE_LETTER=99;
    public static final int STRING=19;
    public static final int RULE_HEX_DIGIT=103;
    public static final int TO=79;
    public static final int UINT=50;
    public static final int LTOD=44;
    public static final int ARRAY=20;
    public static final int LT=76;
    public static final int DO=73;
    public static final int WSTRING=15;
    public static final int DT=74;
    public static final int END_VAR=14;
    public static final int FullStopFullStop=65;
    public static final int Ampersand=81;
    public static final int RightSquareBracket=98;
    public static final int RULE_BINARY_INT=110;
    public static final int USINT=32;
    public static final int DWORD=21;
    public static final int FOR=57;
    public static final int RightParenthesis=83;
    public static final int TRUE=49;
    public static final int ColonEqualsSign=66;
    public static final int END_WHILE=9;
    public static final int DATE=39;
    public static final int NOT=60;
    public static final int LDATE=24;
    public static final int AND=56;
    public static final int NumberSign=80;
    public static final int REAL=45;
    public static final int AsteriskAsterisk=64;
    public static final int SINT=46;
    public static final int LTIME_OF_DAY=6;
    public static final int LREAL=25;
    public static final int WCHAR=33;
    public static final int RULE_DATETIME=107;
    public static final int INT=58;
    public static final int RULE_SL_COMMENT=119;
    public static final int RETURN=18;
    public static final int EqualsSign=93;
    public static final int RULE_COMMON_CHAR_VALUE=117;
    public static final int OF=77;
    public static final int Colon=90;
    public static final int EOF=-1;
    public static final int Asterisk=84;
    public static final int SUPER=28;
    public static final int MOD=59;
    public static final int OR=78;
    public static final int RULE_S_BYTE_CHAR_VALUE=113;
    public static final int RULE_WS=120;
    public static final int TIME=48;
    public static final int RULE_ANY_OTHER=121;
    public static final int UNTIL=31;
    public static final int RULE_S_BYTE_CHAR_STR=114;
    public static final int BOOL=35;
    public static final int FALSE=23;
    public static final int WHILE=34;
    public static final int RULE_D_BYTE_CHAR_VALUE=115;

    // delegates
    // delegators

    public InternalStructuredTextLexer() {;} 
    public InternalStructuredTextLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalStructuredTextLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalStructuredTextLexer.g"; }

    // $ANTLR start "LDATE_AND_TIME"
    public final void mLDATE_AND_TIME() throws RecognitionException {
        try {
            int _type = LDATE_AND_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:14:16: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:14:18: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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

    // $ANTLR start "DATE_AND_TIME"
    public final void mDATE_AND_TIME() throws RecognitionException {
        try {
            int _type = DATE_AND_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:16:15: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:16:17: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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

    // $ANTLR start "LTIME_OF_DAY"
    public final void mLTIME_OF_DAY() throws RecognitionException {
        try {
            int _type = LTIME_OF_DAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:18:14: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalStructuredTextLexer.g:18:16: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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

    // $ANTLR start "TIME_OF_DAY"
    public final void mTIME_OF_DAY() throws RecognitionException {
        try {
            int _type = TIME_OF_DAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:20:13: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalStructuredTextLexer.g:20:15: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' ( 'O' | 'o' ) ( 'F' | 'f' ) '_' ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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

    // $ANTLR start "END_REPEAT"
    public final void mEND_REPEAT() throws RecognitionException {
        try {
            int _type = END_REPEAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:22:12: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:22:14: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
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

    // $ANTLR start "END_WHILE"
    public final void mEND_WHILE() throws RecognitionException {
        try {
            int _type = END_WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:24:11: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:24:13: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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

    // $ANTLR start "CONSTANT"
    public final void mCONSTANT() throws RecognitionException {
        try {
            int _type = CONSTANT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:26:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:26:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:28:10: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:28:12: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:30:10: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:30:12: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
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

    // $ANTLR start "END_FOR"
    public final void mEND_FOR() throws RecognitionException {
        try {
            int _type = END_FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:32:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:32:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalStructuredTextLexer.g:34:9: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:34:11: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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

    // $ANTLR start "WSTRING"
    public final void mWSTRING() throws RecognitionException {
        try {
            int _type = WSTRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:36:9: ( ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalStructuredTextLexer.g:36:11: ( 'W' | 'w' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
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

    // $ANTLR start "END_IF"
    public final void mEND_IF() throws RecognitionException {
        try {
            int _type = END_IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:38:8: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalStructuredTextLexer.g:38:10: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' ( 'I' | 'i' ) ( 'F' | 'f' )
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

    // $ANTLR start "REPEAT"
    public final void mREPEAT() throws RecognitionException {
        try {
            int _type = REPEAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:40:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:40:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:42:8: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' ) )
            // InternalStructuredTextLexer.g:42:10: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'N' | 'n' )
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

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:44:8: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // InternalStructuredTextLexer.g:44:10: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
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

    // $ANTLR start "ARRAY"
    public final void mARRAY() throws RecognitionException {
        try {
            int _type = ARRAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:46:7: ( ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) )
            // InternalStructuredTextLexer.g:46:9: ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'Y' | 'y' )
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

    // $ANTLR start "DWORD"
    public final void mDWORD() throws RecognitionException {
        try {
            int _type = DWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:48:7: ( ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalStructuredTextLexer.g:48:9: ( 'D' | 'd' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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
            // InternalStructuredTextLexer.g:50:7: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalStructuredTextLexer.g:50:9: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // InternalStructuredTextLexer.g:52:7: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:52:9: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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

    // $ANTLR start "LDATE"
    public final void mLDATE() throws RecognitionException {
        try {
            int _type = LDATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:54:7: ( ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:54:9: ( 'L' | 'l' ) ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:56:7: ( ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalStructuredTextLexer.g:56:9: ( 'L' | 'l' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalStructuredTextLexer.g:58:7: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:58:9: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:60:7: ( ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalStructuredTextLexer.g:60:9: ( 'L' | 'l' ) ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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
            // InternalStructuredTextLexer.g:62:7: ( ( 'S' | 's' ) ( 'U' | 'u' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:62:9: ( 'S' | 's' ) ( 'U' | 'u' ) ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'R' | 'r' )
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
            // InternalStructuredTextLexer.g:64:7: ( ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:64:9: ( 'U' | 'u' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:66:7: ( ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:66:9: ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:68:7: ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' ) )
            // InternalStructuredTextLexer.g:68:9: ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'L' | 'l' )
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
            // InternalStructuredTextLexer.g:70:7: ( ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:70:9: ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:72:7: ( ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:72:9: ( 'W' | 'w' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalStructuredTextLexer.g:74:7: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:74:9: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:76:6: ( ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) )
            // InternalStructuredTextLexer.g:76:8: ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' )
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
            // InternalStructuredTextLexer.g:78:6: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:78:8: ( 'B' | 'b' ) ( 'Y' | 'y' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:80:6: ( ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:80:8: ( 'C' | 'c' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:82:6: ( ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:82:8: ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalStructuredTextLexer.g:84:6: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:84:8: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:86:6: ( ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:86:8: ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:88:6: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:88:8: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:90:6: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:90:8: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:92:6: ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:92:8: ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:94:6: ( ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalStructuredTextLexer.g:94:8: ( 'L' | 'l' ) ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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

    // $ANTLR start "REAL"
    public final void mREAL() throws RecognitionException {
        try {
            int _type = REAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:96:6: ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalStructuredTextLexer.g:96:8: ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'L' | 'l' )
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
            // InternalStructuredTextLexer.g:98:6: ( ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:98:8: ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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

    // $ANTLR start "THEN"
    public final void mTHEN() throws RecognitionException {
        try {
            int _type = THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:100:6: ( ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' ) )
            // InternalStructuredTextLexer.g:100:8: ( 'T' | 't' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' )
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

    // $ANTLR start "TIME"
    public final void mTIME() throws RecognitionException {
        try {
            int _type = TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:102:6: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:102:8: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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
            // InternalStructuredTextLexer.g:104:6: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:104:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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

    // $ANTLR start "UINT"
    public final void mUINT() throws RecognitionException {
        try {
            int _type = UINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:106:6: ( ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:106:8: ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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

    // $ANTLR start "WORD"
    public final void mWORD() throws RecognitionException {
        try {
            int _type = WORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:108:6: ( ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) )
            // InternalStructuredTextLexer.g:108:8: ( 'W' | 'w' ) ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' )
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

    // $ANTLR start "B"
    public final void mB() throws RecognitionException {
        try {
            int _type = B;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:110:3: ( '.' '%' ( 'B' | 'b' ) )
            // InternalStructuredTextLexer.g:110:5: '.' '%' ( 'B' | 'b' )
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

    // $ANTLR start "D"
    public final void mD() throws RecognitionException {
        try {
            int _type = D;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:112:3: ( '.' '%' ( 'D' | 'd' ) )
            // InternalStructuredTextLexer.g:112:5: '.' '%' ( 'D' | 'd' )
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
    // $ANTLR end "D"

    // $ANTLR start "W"
    public final void mW() throws RecognitionException {
        try {
            int _type = W;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:114:3: ( '.' '%' ( 'W' | 'w' ) )
            // InternalStructuredTextLexer.g:114:5: '.' '%' ( 'W' | 'w' )
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
            // InternalStructuredTextLexer.g:116:3: ( '.' '%' ( 'X' | 'x' ) )
            // InternalStructuredTextLexer.g:116:5: '.' '%' ( 'X' | 'x' )
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
            // InternalStructuredTextLexer.g:118:5: ( ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
            // InternalStructuredTextLexer.g:118:7: ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' )
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
            // InternalStructuredTextLexer.g:120:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:120:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalStructuredTextLexer.g:122:5: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:122:7: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
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

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:124:5: ( ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalStructuredTextLexer.g:124:7: ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalStructuredTextLexer.g:126:5: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:126:7: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' )
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

    // $ANTLR start "TOD"
    public final void mTOD() throws RecognitionException {
        try {
            int _type = TOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:128:5: ( ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // InternalStructuredTextLexer.g:128:7: ( 'T' | 't' ) ( 'O' | 'o' ) ( 'D' | 'd' )
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
            // InternalStructuredTextLexer.g:130:5: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:130:7: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' )
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
            // InternalStructuredTextLexer.g:132:5: ( ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:132:7: ( 'X' | 'x' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalStructuredTextLexer.g:134:18: ( '*' '*' )
            // InternalStructuredTextLexer.g:134:20: '*' '*'
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
            // InternalStructuredTextLexer.g:136:18: ( '.' '.' )
            // InternalStructuredTextLexer.g:136:20: '.' '.'
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
            // InternalStructuredTextLexer.g:138:17: ( ':' '=' )
            // InternalStructuredTextLexer.g:138:19: ':' '='
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
            // InternalStructuredTextLexer.g:140:24: ( '<' '=' )
            // InternalStructuredTextLexer.g:140:26: '<' '='
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
            // InternalStructuredTextLexer.g:142:29: ( '<' '>' )
            // InternalStructuredTextLexer.g:142:31: '<' '>'
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
            // InternalStructuredTextLexer.g:144:27: ( '=' '>' )
            // InternalStructuredTextLexer.g:144:29: '=' '>'
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
            // InternalStructuredTextLexer.g:146:27: ( '>' '=' )
            // InternalStructuredTextLexer.g:146:29: '>' '='
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
            // InternalStructuredTextLexer.g:148:4: ( ( 'A' | 'a' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:148:6: ( 'A' | 'a' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:150:4: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) )
            // InternalStructuredTextLexer.g:150:6: ( 'B' | 'b' ) ( 'Y' | 'y' )
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
            // InternalStructuredTextLexer.g:152:4: ( ( 'D' | 'd' ) ( 'O' | 'o' ) )
            // InternalStructuredTextLexer.g:152:6: ( 'D' | 'd' ) ( 'O' | 'o' )
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
            // InternalStructuredTextLexer.g:154:4: ( ( 'D' | 'd' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:154:6: ( 'D' | 'd' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:156:4: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalStructuredTextLexer.g:156:6: ( 'I' | 'i' ) ( 'F' | 'f' )
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

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:158:4: ( ( 'L' | 'l' ) ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:158:6: ( 'L' | 'l' ) ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:160:4: ( ( 'O' | 'o' ) ( 'F' | 'f' ) )
            // InternalStructuredTextLexer.g:160:6: ( 'O' | 'o' ) ( 'F' | 'f' )
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
            // InternalStructuredTextLexer.g:162:4: ( ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // InternalStructuredTextLexer.g:162:6: ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // InternalStructuredTextLexer.g:164:4: ( ( 'T' | 't' ) ( 'O' | 'o' ) )
            // InternalStructuredTextLexer.g:164:6: ( 'T' | 't' ) ( 'O' | 'o' )
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
            // InternalStructuredTextLexer.g:166:12: ( '#' )
            // InternalStructuredTextLexer.g:166:14: '#'
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
            // InternalStructuredTextLexer.g:168:11: ( '&' )
            // InternalStructuredTextLexer.g:168:13: '&'
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
            // InternalStructuredTextLexer.g:170:17: ( '(' )
            // InternalStructuredTextLexer.g:170:19: '('
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
            // InternalStructuredTextLexer.g:172:18: ( ')' )
            // InternalStructuredTextLexer.g:172:20: ')'
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
            // InternalStructuredTextLexer.g:174:10: ( '*' )
            // InternalStructuredTextLexer.g:174:12: '*'
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
            // InternalStructuredTextLexer.g:176:10: ( '+' )
            // InternalStructuredTextLexer.g:176:12: '+'
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
            // InternalStructuredTextLexer.g:178:7: ( ',' )
            // InternalStructuredTextLexer.g:178:9: ','
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
            // InternalStructuredTextLexer.g:180:13: ( '-' )
            // InternalStructuredTextLexer.g:180:15: '-'
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
            // InternalStructuredTextLexer.g:182:10: ( '.' )
            // InternalStructuredTextLexer.g:182:12: '.'
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
            // InternalStructuredTextLexer.g:184:9: ( '/' )
            // InternalStructuredTextLexer.g:184:11: '/'
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
            // InternalStructuredTextLexer.g:186:7: ( ':' )
            // InternalStructuredTextLexer.g:186:9: ':'
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
            // InternalStructuredTextLexer.g:188:11: ( ';' )
            // InternalStructuredTextLexer.g:188:13: ';'
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
            // InternalStructuredTextLexer.g:190:14: ( '<' )
            // InternalStructuredTextLexer.g:190:16: '<'
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
            // InternalStructuredTextLexer.g:192:12: ( '=' )
            // InternalStructuredTextLexer.g:192:14: '='
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
            // InternalStructuredTextLexer.g:194:17: ( '>' )
            // InternalStructuredTextLexer.g:194:19: '>'
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

    // $ANTLR start "E"
    public final void mE() throws RecognitionException {
        try {
            int _type = E;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:196:3: ( ( 'E' | 'e' ) )
            // InternalStructuredTextLexer.g:196:5: ( 'E' | 'e' )
            {
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
    // $ANTLR end "E"

    // $ANTLR start "T"
    public final void mT() throws RecognitionException {
        try {
            int _type = T;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:198:3: ( ( 'T' | 't' ) )
            // InternalStructuredTextLexer.g:198:5: ( 'T' | 't' )
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
            // InternalStructuredTextLexer.g:200:19: ( '[' )
            // InternalStructuredTextLexer.g:200:21: '['
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
            // InternalStructuredTextLexer.g:202:20: ( ']' )
            // InternalStructuredTextLexer.g:202:22: ']'
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

    // $ANTLR start "RULE_LETTER"
    public final void mRULE_LETTER() throws RecognitionException {
        try {
            // InternalStructuredTextLexer.g:204:22: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) )
            // InternalStructuredTextLexer.g:204:24: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
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
    // $ANTLR end "RULE_LETTER"

    // $ANTLR start "RULE_DIGIT"
    public final void mRULE_DIGIT() throws RecognitionException {
        try {
            // InternalStructuredTextLexer.g:206:21: ( '0' .. '9' )
            // InternalStructuredTextLexer.g:206:23: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DIGIT"

    // $ANTLR start "RULE_BIT"
    public final void mRULE_BIT() throws RecognitionException {
        try {
            // InternalStructuredTextLexer.g:208:19: ( '0' .. '1' )
            // InternalStructuredTextLexer.g:208:21: '0' .. '1'
            {
            matchRange('0','1'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_BIT"

    // $ANTLR start "RULE_OCTAL_DIGIT"
    public final void mRULE_OCTAL_DIGIT() throws RecognitionException {
        try {
            // InternalStructuredTextLexer.g:210:27: ( '0' .. '7' )
            // InternalStructuredTextLexer.g:210:29: '0' .. '7'
            {
            matchRange('0','7'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_OCTAL_DIGIT"

    // $ANTLR start "RULE_HEX_DIGIT"
    public final void mRULE_HEX_DIGIT() throws RecognitionException {
        try {
            // InternalStructuredTextLexer.g:212:25: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // InternalStructuredTextLexer.g:212:27: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
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

    // $ANTLR start "RULE_TIMEOFDAY"
    public final void mRULE_TIMEOFDAY() throws RecognitionException {
        try {
            int _type = RULE_TIMEOFDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:214:16: ( ( 'L' | 'l' )? ( 'T' | 't' ) ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' )? ( 'O' | 'o' ) ( ( 'F' | 'f' ) '_' )? ( 'D' | 'd' ) ( ( 'A' | 'a' ) ( 'Y' | 'y' ) )? '#' RULE_UNSIGNED_INT ':' RULE_UNSIGNED_INT ':' RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? )
            // InternalStructuredTextLexer.g:214:18: ( 'L' | 'l' )? ( 'T' | 't' ) ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' )? ( 'O' | 'o' ) ( ( 'F' | 'f' ) '_' )? ( 'D' | 'd' ) ( ( 'A' | 'a' ) ( 'Y' | 'y' ) )? '#' RULE_UNSIGNED_INT ':' RULE_UNSIGNED_INT ':' RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )?
            {
            // InternalStructuredTextLexer.g:214:18: ( 'L' | 'l' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='L'||LA1_0=='l') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalStructuredTextLexer.g:
                    {
                    if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalStructuredTextLexer.g:214:39: ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='I'||LA2_0=='i') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalStructuredTextLexer.g:214:40: ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) '_'
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

                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    match('_'); 

                    }
                    break;

            }

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalStructuredTextLexer.g:214:86: ( ( 'F' | 'f' ) '_' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='F'||LA3_0=='f') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalStructuredTextLexer.g:214:87: ( 'F' | 'f' ) '_'
                    {
                    if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    match('_'); 

                    }
                    break;

            }

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalStructuredTextLexer.g:214:113: ( ( 'A' | 'a' ) ( 'Y' | 'y' ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='A'||LA4_0=='a') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalStructuredTextLexer.g:214:114: ( 'A' | 'a' ) ( 'Y' | 'y' )
                    {
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
                    break;

            }

            match('#'); 
            mRULE_UNSIGNED_INT(); 
            match(':'); 
            mRULE_UNSIGNED_INT(); 
            match(':'); 
            mRULE_UNSIGNED_INT(); 
            // InternalStructuredTextLexer.g:214:202: ( '.' RULE_UNSIGNED_INT )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='.') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalStructuredTextLexer.g:214:203: '.' RULE_UNSIGNED_INT
                    {
                    match('.'); 
                    mRULE_UNSIGNED_INT(); 

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
    // $ANTLR end "RULE_TIMEOFDAY"

    // $ANTLR start "RULE_TIME"
    public final void mRULE_TIME() throws RecognitionException {
        try {
            int _type = RULE_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:216:11: ( ( 'L' | 'l' )? ( 'T' | 't' ) ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )? '#' ( '+' | '-' )? RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? ( ( 'D' | 'd' ) | ( 'H' | 'h' ) | ( 'M' | 'm' ) | ( 'S' | 's' ) | ( 'M' | 'm' ) ( 'S' | 's' ) | ( 'U' | 'u' ) ( 'S' | 's' ) | ( 'N' | 'n' ) ( 'S' | 's' ) ) ( ( '_' )? RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? ( ( 'D' | 'd' ) | ( 'H' | 'h' ) | ( 'M' | 'm' ) | ( 'S' | 's' ) | ( 'M' | 'm' ) ( 'S' | 's' ) | ( 'U' | 'u' ) ( 'S' | 's' ) | ( 'N' | 'n' ) ( 'S' | 's' ) ) )* )
            // InternalStructuredTextLexer.g:216:13: ( 'L' | 'l' )? ( 'T' | 't' ) ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )? '#' ( '+' | '-' )? RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? ( ( 'D' | 'd' ) | ( 'H' | 'h' ) | ( 'M' | 'm' ) | ( 'S' | 's' ) | ( 'M' | 'm' ) ( 'S' | 's' ) | ( 'U' | 'u' ) ( 'S' | 's' ) | ( 'N' | 'n' ) ( 'S' | 's' ) ) ( ( '_' )? RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? ( ( 'D' | 'd' ) | ( 'H' | 'h' ) | ( 'M' | 'm' ) | ( 'S' | 's' ) | ( 'M' | 'm' ) ( 'S' | 's' ) | ( 'U' | 'u' ) ( 'S' | 's' ) | ( 'N' | 'n' ) ( 'S' | 's' ) ) )*
            {
            // InternalStructuredTextLexer.g:216:13: ( 'L' | 'l' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='L'||LA6_0=='l') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalStructuredTextLexer.g:
                    {
                    if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalStructuredTextLexer.g:216:34: ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='I'||LA7_0=='i') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalStructuredTextLexer.g:216:35: ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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

                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            match('#'); 
            // InternalStructuredTextLexer.g:216:71: ( '+' | '-' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='+'||LA8_0=='-') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalStructuredTextLexer.g:
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

            mRULE_UNSIGNED_INT(); 
            // InternalStructuredTextLexer.g:216:100: ( '.' RULE_UNSIGNED_INT )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='.') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalStructuredTextLexer.g:216:101: '.' RULE_UNSIGNED_INT
                    {
                    match('.'); 
                    mRULE_UNSIGNED_INT(); 

                    }
                    break;

            }

            // InternalStructuredTextLexer.g:216:125: ( ( 'D' | 'd' ) | ( 'H' | 'h' ) | ( 'M' | 'm' ) | ( 'S' | 's' ) | ( 'M' | 'm' ) ( 'S' | 's' ) | ( 'U' | 'u' ) ( 'S' | 's' ) | ( 'N' | 'n' ) ( 'S' | 's' ) )
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
                    // InternalStructuredTextLexer.g:216:126: ( 'D' | 'd' )
                    {
                    if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // InternalStructuredTextLexer.g:216:136: ( 'H' | 'h' )
                    {
                    if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 3 :
                    // InternalStructuredTextLexer.g:216:146: ( 'M' | 'm' )
                    {
                    if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 4 :
                    // InternalStructuredTextLexer.g:216:156: ( 'S' | 's' )
                    {
                    if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 5 :
                    // InternalStructuredTextLexer.g:216:166: ( 'M' | 'm' ) ( 'S' | 's' )
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
                    break;
                case 6 :
                    // InternalStructuredTextLexer.g:216:186: ( 'U' | 'u' ) ( 'S' | 's' )
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
                    break;
                case 7 :
                    // InternalStructuredTextLexer.g:216:206: ( 'N' | 'n' ) ( 'S' | 's' )
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
                    break;

            }

            // InternalStructuredTextLexer.g:216:227: ( ( '_' )? RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? ( ( 'D' | 'd' ) | ( 'H' | 'h' ) | ( 'M' | 'm' ) | ( 'S' | 's' ) | ( 'M' | 'm' ) ( 'S' | 's' ) | ( 'U' | 'u' ) ( 'S' | 's' ) | ( 'N' | 'n' ) ( 'S' | 's' ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='0' && LA14_0<='9')||LA14_0=='_') ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:216:228: ( '_' )? RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? ( ( 'D' | 'd' ) | ( 'H' | 'h' ) | ( 'M' | 'm' ) | ( 'S' | 's' ) | ( 'M' | 'm' ) ( 'S' | 's' ) | ( 'U' | 'u' ) ( 'S' | 's' ) | ( 'N' | 'n' ) ( 'S' | 's' ) )
            	    {
            	    // InternalStructuredTextLexer.g:216:228: ( '_' )?
            	    int alt11=2;
            	    int LA11_0 = input.LA(1);

            	    if ( (LA11_0=='_') ) {
            	        alt11=1;
            	    }
            	    switch (alt11) {
            	        case 1 :
            	            // InternalStructuredTextLexer.g:216:228: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }

            	    mRULE_UNSIGNED_INT(); 
            	    // InternalStructuredTextLexer.g:216:251: ( '.' RULE_UNSIGNED_INT )?
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0=='.') ) {
            	        alt12=1;
            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // InternalStructuredTextLexer.g:216:252: '.' RULE_UNSIGNED_INT
            	            {
            	            match('.'); 
            	            mRULE_UNSIGNED_INT(); 

            	            }
            	            break;

            	    }

            	    // InternalStructuredTextLexer.g:216:276: ( ( 'D' | 'd' ) | ( 'H' | 'h' ) | ( 'M' | 'm' ) | ( 'S' | 's' ) | ( 'M' | 'm' ) ( 'S' | 's' ) | ( 'U' | 'u' ) ( 'S' | 's' ) | ( 'N' | 'n' ) ( 'S' | 's' ) )
            	    int alt13=7;
            	    switch ( input.LA(1) ) {
            	    case 'D':
            	    case 'd':
            	        {
            	        alt13=1;
            	        }
            	        break;
            	    case 'H':
            	    case 'h':
            	        {
            	        alt13=2;
            	        }
            	        break;
            	    case 'M':
            	    case 'm':
            	        {
            	        int LA13_3 = input.LA(2);

            	        if ( (LA13_3=='S'||LA13_3=='s') ) {
            	            alt13=5;
            	        }
            	        else {
            	            alt13=3;}
            	        }
            	        break;
            	    case 'S':
            	    case 's':
            	        {
            	        alt13=4;
            	        }
            	        break;
            	    case 'U':
            	    case 'u':
            	        {
            	        alt13=6;
            	        }
            	        break;
            	    case 'N':
            	    case 'n':
            	        {
            	        alt13=7;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 13, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt13) {
            	        case 1 :
            	            // InternalStructuredTextLexer.g:216:277: ( 'D' | 'd' )
            	            {
            	            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
            	                input.consume();

            	            }
            	            else {
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                recover(mse);
            	                throw mse;}


            	            }
            	            break;
            	        case 2 :
            	            // InternalStructuredTextLexer.g:216:287: ( 'H' | 'h' )
            	            {
            	            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
            	                input.consume();

            	            }
            	            else {
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                recover(mse);
            	                throw mse;}


            	            }
            	            break;
            	        case 3 :
            	            // InternalStructuredTextLexer.g:216:297: ( 'M' | 'm' )
            	            {
            	            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
            	                input.consume();

            	            }
            	            else {
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                recover(mse);
            	                throw mse;}


            	            }
            	            break;
            	        case 4 :
            	            // InternalStructuredTextLexer.g:216:307: ( 'S' | 's' )
            	            {
            	            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
            	                input.consume();

            	            }
            	            else {
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                recover(mse);
            	                throw mse;}


            	            }
            	            break;
            	        case 5 :
            	            // InternalStructuredTextLexer.g:216:317: ( 'M' | 'm' ) ( 'S' | 's' )
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
            	            break;
            	        case 6 :
            	            // InternalStructuredTextLexer.g:216:337: ( 'U' | 'u' ) ( 'S' | 's' )
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
            	            break;
            	        case 7 :
            	            // InternalStructuredTextLexer.g:216:357: ( 'N' | 'n' ) ( 'S' | 's' )
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
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_TIME"

    // $ANTLR start "RULE_DATETIME"
    public final void mRULE_DATETIME() throws RecognitionException {
        try {
            int _type = RULE_DATETIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:218:15: ( ( 'L' | 'l' )? ( 'D' | 'd' ) ( ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' )? ( 'T' | 't' ) ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )? '#' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT ':' RULE_UNSIGNED_INT ':' RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? )
            // InternalStructuredTextLexer.g:218:17: ( 'L' | 'l' )? ( 'D' | 'd' ) ( ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' )? ( 'T' | 't' ) ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )? '#' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT ':' RULE_UNSIGNED_INT ':' RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )?
            {
            // InternalStructuredTextLexer.g:218:17: ( 'L' | 'l' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='L'||LA15_0=='l') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalStructuredTextLexer.g:
                    {
                    if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalStructuredTextLexer.g:218:38: ( ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_' )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='A'||LA16_0=='a') ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalStructuredTextLexer.g:218:39: ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) '_' ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) '_'
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

                    }
                    break;

            }

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalStructuredTextLexer.g:218:119: ( ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='I'||LA17_0=='i') ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalStructuredTextLexer.g:218:120: ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
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

                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            match('#'); 
            mRULE_UNSIGNED_INT(); 
            match('-'); 
            mRULE_UNSIGNED_INT(); 
            match('-'); 
            mRULE_UNSIGNED_INT(); 
            match('-'); 
            mRULE_UNSIGNED_INT(); 
            match(':'); 
            mRULE_UNSIGNED_INT(); 
            match(':'); 
            mRULE_UNSIGNED_INT(); 
            // InternalStructuredTextLexer.g:218:284: ( '.' RULE_UNSIGNED_INT )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='.') ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalStructuredTextLexer.g:218:285: '.' RULE_UNSIGNED_INT
                    {
                    match('.'); 
                    mRULE_UNSIGNED_INT(); 

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
    // $ANTLR end "RULE_DATETIME"

    // $ANTLR start "RULE_DATE"
    public final void mRULE_DATE() throws RecognitionException {
        try {
            int _type = RULE_DATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:220:11: ( ( 'L' | 'l' )? ( 'D' | 'd' ) ( ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )? '#' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )? )
            // InternalStructuredTextLexer.g:220:13: ( 'L' | 'l' )? ( 'D' | 'd' ) ( ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )? '#' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT '-' RULE_UNSIGNED_INT ( '.' RULE_UNSIGNED_INT )?
            {
            // InternalStructuredTextLexer.g:220:13: ( 'L' | 'l' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='L'||LA19_0=='l') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalStructuredTextLexer.g:
                    {
                    if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalStructuredTextLexer.g:220:34: ( ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='A'||LA20_0=='a') ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalStructuredTextLexer.g:220:35: ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
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

                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            match('#'); 
            mRULE_UNSIGNED_INT(); 
            match('-'); 
            mRULE_UNSIGNED_INT(); 
            match('-'); 
            mRULE_UNSIGNED_INT(); 
            // InternalStructuredTextLexer.g:220:133: ( '.' RULE_UNSIGNED_INT )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='.') ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalStructuredTextLexer.g:220:134: '.' RULE_UNSIGNED_INT
                    {
                    match('.'); 
                    mRULE_UNSIGNED_INT(); 

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
    // $ANTLR end "RULE_DATE"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:222:9: ( RULE_LETTER ( RULE_LETTER | RULE_DIGIT )* )
            // InternalStructuredTextLexer.g:222:11: RULE_LETTER ( RULE_LETTER | RULE_DIGIT )*
            {
            mRULE_LETTER(); 
            // InternalStructuredTextLexer.g:222:23: ( RULE_LETTER | RULE_DIGIT )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='0' && LA22_0<='9')||(LA22_0>='A' && LA22_0<='Z')||LA22_0=='_'||(LA22_0>='a' && LA22_0<='z')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:
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
            	    break loop22;
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

    // $ANTLR start "RULE_BINARY_INT"
    public final void mRULE_BINARY_INT() throws RecognitionException {
        try {
            int _type = RULE_BINARY_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:224:17: ( '2#' ( ( '_' )? RULE_BIT )+ )
            // InternalStructuredTextLexer.g:224:19: '2#' ( ( '_' )? RULE_BIT )+
            {
            match("2#"); 

            // InternalStructuredTextLexer.g:224:24: ( ( '_' )? RULE_BIT )+
            int cnt24=0;
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>='0' && LA24_0<='1')||LA24_0=='_') ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:224:25: ( '_' )? RULE_BIT
            	    {
            	    // InternalStructuredTextLexer.g:224:25: ( '_' )?
            	    int alt23=2;
            	    int LA23_0 = input.LA(1);

            	    if ( (LA23_0=='_') ) {
            	        alt23=1;
            	    }
            	    switch (alt23) {
            	        case 1 :
            	            // InternalStructuredTextLexer.g:224:25: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }

            	    mRULE_BIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt24 >= 1 ) break loop24;
                        EarlyExitException eee =
                            new EarlyExitException(24, input);
                        throw eee;
                }
                cnt24++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_BINARY_INT"

    // $ANTLR start "RULE_OCTAL_INT"
    public final void mRULE_OCTAL_INT() throws RecognitionException {
        try {
            int _type = RULE_OCTAL_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:226:16: ( '8#' ( ( '_' )? RULE_OCTAL_DIGIT )+ )
            // InternalStructuredTextLexer.g:226:18: '8#' ( ( '_' )? RULE_OCTAL_DIGIT )+
            {
            match("8#"); 

            // InternalStructuredTextLexer.g:226:23: ( ( '_' )? RULE_OCTAL_DIGIT )+
            int cnt26=0;
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>='0' && LA26_0<='7')||LA26_0=='_') ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:226:24: ( '_' )? RULE_OCTAL_DIGIT
            	    {
            	    // InternalStructuredTextLexer.g:226:24: ( '_' )?
            	    int alt25=2;
            	    int LA25_0 = input.LA(1);

            	    if ( (LA25_0=='_') ) {
            	        alt25=1;
            	    }
            	    switch (alt25) {
            	        case 1 :
            	            // InternalStructuredTextLexer.g:226:24: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }

            	    mRULE_OCTAL_DIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt26 >= 1 ) break loop26;
                        EarlyExitException eee =
                            new EarlyExitException(26, input);
                        throw eee;
                }
                cnt26++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_OCTAL_INT"

    // $ANTLR start "RULE_HEX_INT"
    public final void mRULE_HEX_INT() throws RecognitionException {
        try {
            int _type = RULE_HEX_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:228:14: ( '16#' ( ( '_' )? RULE_HEX_DIGIT )+ )
            // InternalStructuredTextLexer.g:228:16: '16#' ( ( '_' )? RULE_HEX_DIGIT )+
            {
            match("16#"); 

            // InternalStructuredTextLexer.g:228:22: ( ( '_' )? RULE_HEX_DIGIT )+
            int cnt28=0;
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>='0' && LA28_0<='9')||(LA28_0>='A' && LA28_0<='F')||LA28_0=='_'||(LA28_0>='a' && LA28_0<='f')) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:228:23: ( '_' )? RULE_HEX_DIGIT
            	    {
            	    // InternalStructuredTextLexer.g:228:23: ( '_' )?
            	    int alt27=2;
            	    int LA27_0 = input.LA(1);

            	    if ( (LA27_0=='_') ) {
            	        alt27=1;
            	    }
            	    switch (alt27) {
            	        case 1 :
            	            // InternalStructuredTextLexer.g:228:23: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }

            	    mRULE_HEX_DIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt28 >= 1 ) break loop28;
                        EarlyExitException eee =
                            new EarlyExitException(28, input);
                        throw eee;
                }
                cnt28++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_HEX_INT"

    // $ANTLR start "RULE_UNSIGNED_INT"
    public final void mRULE_UNSIGNED_INT() throws RecognitionException {
        try {
            int _type = RULE_UNSIGNED_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:230:19: ( RULE_DIGIT ( ( '_' )? RULE_DIGIT )* )
            // InternalStructuredTextLexer.g:230:21: RULE_DIGIT ( ( '_' )? RULE_DIGIT )*
            {
            mRULE_DIGIT(); 
            // InternalStructuredTextLexer.g:230:32: ( ( '_' )? RULE_DIGIT )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>='0' && LA30_0<='9')||LA30_0=='_') ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:230:33: ( '_' )? RULE_DIGIT
            	    {
            	    // InternalStructuredTextLexer.g:230:33: ( '_' )?
            	    int alt29=2;
            	    int LA29_0 = input.LA(1);

            	    if ( (LA29_0=='_') ) {
            	        alt29=1;
            	    }
            	    switch (alt29) {
            	        case 1 :
            	            // InternalStructuredTextLexer.g:230:33: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }

            	    mRULE_DIGIT(); 

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNSIGNED_INT"

    // $ANTLR start "RULE_S_BYTE_CHAR_STR"
    public final void mRULE_S_BYTE_CHAR_STR() throws RecognitionException {
        try {
            int _type = RULE_S_BYTE_CHAR_STR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:232:22: ( '\\'' ( RULE_S_BYTE_CHAR_VALUE )* '\\'' )
            // InternalStructuredTextLexer.g:232:24: '\\'' ( RULE_S_BYTE_CHAR_VALUE )* '\\''
            {
            match('\''); 
            // InternalStructuredTextLexer.g:232:29: ( RULE_S_BYTE_CHAR_VALUE )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( ((LA31_0>=' ' && LA31_0<='&')||(LA31_0>='(' && LA31_0<='~')) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:232:29: RULE_S_BYTE_CHAR_VALUE
            	    {
            	    mRULE_S_BYTE_CHAR_VALUE(); 

            	    }
            	    break;

            	default :
            	    break loop31;
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
    // $ANTLR end "RULE_S_BYTE_CHAR_STR"

    // $ANTLR start "RULE_D_BYTE_CHAR_STR"
    public final void mRULE_D_BYTE_CHAR_STR() throws RecognitionException {
        try {
            int _type = RULE_D_BYTE_CHAR_STR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:234:22: ( '\"' ( RULE_D_BYTE_CHAR_VALUE )* '\"' )
            // InternalStructuredTextLexer.g:234:24: '\"' ( RULE_D_BYTE_CHAR_VALUE )* '\"'
            {
            match('\"'); 
            // InternalStructuredTextLexer.g:234:28: ( RULE_D_BYTE_CHAR_VALUE )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( ((LA32_0>=' ' && LA32_0<='!')||(LA32_0>='#' && LA32_0<='~')) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:234:28: RULE_D_BYTE_CHAR_VALUE
            	    {
            	    mRULE_D_BYTE_CHAR_VALUE(); 

            	    }
            	    break;

            	default :
            	    break loop32;
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
    // $ANTLR end "RULE_D_BYTE_CHAR_STR"

    // $ANTLR start "RULE_S_BYTE_CHAR_VALUE"
    public final void mRULE_S_BYTE_CHAR_VALUE() throws RecognitionException {
        try {
            // InternalStructuredTextLexer.g:236:33: ( ( RULE_COMMON_CHAR_VALUE | '$\\'' | '\"' | '$' RULE_HEX_DIGIT RULE_HEX_DIGIT ) )
            // InternalStructuredTextLexer.g:236:35: ( RULE_COMMON_CHAR_VALUE | '$\\'' | '\"' | '$' RULE_HEX_DIGIT RULE_HEX_DIGIT )
            {
            // InternalStructuredTextLexer.g:236:35: ( RULE_COMMON_CHAR_VALUE | '$\\'' | '\"' | '$' RULE_HEX_DIGIT RULE_HEX_DIGIT )
            int alt33=4;
            switch ( input.LA(1) ) {
            case ' ':
            case '!':
            case '#':
            case '%':
            case '&':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case '{':
            case '|':
            case '}':
            case '~':
                {
                alt33=1;
                }
                break;
            case '$':
                {
                switch ( input.LA(2) ) {
                case '$':
                case 'L':
                case 'N':
                case 'P':
                case 'R':
                case 'T':
                    {
                    alt33=1;
                    }
                    break;
                case '\'':
                    {
                    alt33=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    {
                    alt33=4;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 2, input);

                    throw nvae;
                }

                }
                break;
            case '\"':
                {
                alt33=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }

            switch (alt33) {
                case 1 :
                    // InternalStructuredTextLexer.g:236:36: RULE_COMMON_CHAR_VALUE
                    {
                    mRULE_COMMON_CHAR_VALUE(); 

                    }
                    break;
                case 2 :
                    // InternalStructuredTextLexer.g:236:59: '$\\''
                    {
                    match("$'"); 


                    }
                    break;
                case 3 :
                    // InternalStructuredTextLexer.g:236:65: '\"'
                    {
                    match('\"'); 

                    }
                    break;
                case 4 :
                    // InternalStructuredTextLexer.g:236:69: '$' RULE_HEX_DIGIT RULE_HEX_DIGIT
                    {
                    match('$'); 
                    mRULE_HEX_DIGIT(); 
                    mRULE_HEX_DIGIT(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_S_BYTE_CHAR_VALUE"

    // $ANTLR start "RULE_D_BYTE_CHAR_VALUE"
    public final void mRULE_D_BYTE_CHAR_VALUE() throws RecognitionException {
        try {
            // InternalStructuredTextLexer.g:238:33: ( ( RULE_COMMON_CHAR_VALUE | '\\'' | '$\"' | '$' RULE_HEX_DIGIT RULE_HEX_DIGIT RULE_HEX_DIGIT RULE_HEX_DIGIT ) )
            // InternalStructuredTextLexer.g:238:35: ( RULE_COMMON_CHAR_VALUE | '\\'' | '$\"' | '$' RULE_HEX_DIGIT RULE_HEX_DIGIT RULE_HEX_DIGIT RULE_HEX_DIGIT )
            {
            // InternalStructuredTextLexer.g:238:35: ( RULE_COMMON_CHAR_VALUE | '\\'' | '$\"' | '$' RULE_HEX_DIGIT RULE_HEX_DIGIT RULE_HEX_DIGIT RULE_HEX_DIGIT )
            int alt34=4;
            switch ( input.LA(1) ) {
            case ' ':
            case '!':
            case '#':
            case '%':
            case '&':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case '{':
            case '|':
            case '}':
            case '~':
                {
                alt34=1;
                }
                break;
            case '$':
                {
                switch ( input.LA(2) ) {
                case '$':
                case 'L':
                case 'N':
                case 'P':
                case 'R':
                case 'T':
                    {
                    alt34=1;
                    }
                    break;
                case '\"':
                    {
                    alt34=3;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    {
                    alt34=4;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 34, 2, input);

                    throw nvae;
                }

                }
                break;
            case '\'':
                {
                alt34=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }

            switch (alt34) {
                case 1 :
                    // InternalStructuredTextLexer.g:238:36: RULE_COMMON_CHAR_VALUE
                    {
                    mRULE_COMMON_CHAR_VALUE(); 

                    }
                    break;
                case 2 :
                    // InternalStructuredTextLexer.g:238:59: '\\''
                    {
                    match('\''); 

                    }
                    break;
                case 3 :
                    // InternalStructuredTextLexer.g:238:64: '$\"'
                    {
                    match("$\""); 


                    }
                    break;
                case 4 :
                    // InternalStructuredTextLexer.g:238:69: '$' RULE_HEX_DIGIT RULE_HEX_DIGIT RULE_HEX_DIGIT RULE_HEX_DIGIT
                    {
                    match('$'); 
                    mRULE_HEX_DIGIT(); 
                    mRULE_HEX_DIGIT(); 
                    mRULE_HEX_DIGIT(); 
                    mRULE_HEX_DIGIT(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_D_BYTE_CHAR_VALUE"

    // $ANTLR start "RULE_COMMON_CHAR_VALUE"
    public final void mRULE_COMMON_CHAR_VALUE() throws RecognitionException {
        try {
            // InternalStructuredTextLexer.g:240:33: ( ( ' ' | '!' | '#' | '%' | '&' | '(' .. '/' | '0' .. '9' | ':' .. '@' | 'A' .. 'Z' | '[' .. '`' | 'a' .. 'z' | '{' .. '~' | '$$' | '$L' | '$N' | '$P' | '$R' | '$T' ) )
            // InternalStructuredTextLexer.g:240:35: ( ' ' | '!' | '#' | '%' | '&' | '(' .. '/' | '0' .. '9' | ':' .. '@' | 'A' .. 'Z' | '[' .. '`' | 'a' .. 'z' | '{' .. '~' | '$$' | '$L' | '$N' | '$P' | '$R' | '$T' )
            {
            // InternalStructuredTextLexer.g:240:35: ( ' ' | '!' | '#' | '%' | '&' | '(' .. '/' | '0' .. '9' | ':' .. '@' | 'A' .. 'Z' | '[' .. '`' | 'a' .. 'z' | '{' .. '~' | '$$' | '$L' | '$N' | '$P' | '$R' | '$T' )
            int alt35=18;
            alt35 = dfa35.predict(input);
            switch (alt35) {
                case 1 :
                    // InternalStructuredTextLexer.g:240:36: ' '
                    {
                    match(' '); 

                    }
                    break;
                case 2 :
                    // InternalStructuredTextLexer.g:240:40: '!'
                    {
                    match('!'); 

                    }
                    break;
                case 3 :
                    // InternalStructuredTextLexer.g:240:44: '#'
                    {
                    match('#'); 

                    }
                    break;
                case 4 :
                    // InternalStructuredTextLexer.g:240:48: '%'
                    {
                    match('%'); 

                    }
                    break;
                case 5 :
                    // InternalStructuredTextLexer.g:240:52: '&'
                    {
                    match('&'); 

                    }
                    break;
                case 6 :
                    // InternalStructuredTextLexer.g:240:56: '(' .. '/'
                    {
                    matchRange('(','/'); 

                    }
                    break;
                case 7 :
                    // InternalStructuredTextLexer.g:240:65: '0' .. '9'
                    {
                    matchRange('0','9'); 

                    }
                    break;
                case 8 :
                    // InternalStructuredTextLexer.g:240:74: ':' .. '@'
                    {
                    matchRange(':','@'); 

                    }
                    break;
                case 9 :
                    // InternalStructuredTextLexer.g:240:83: 'A' .. 'Z'
                    {
                    matchRange('A','Z'); 

                    }
                    break;
                case 10 :
                    // InternalStructuredTextLexer.g:240:92: '[' .. '`'
                    {
                    matchRange('[','`'); 

                    }
                    break;
                case 11 :
                    // InternalStructuredTextLexer.g:240:101: 'a' .. 'z'
                    {
                    matchRange('a','z'); 

                    }
                    break;
                case 12 :
                    // InternalStructuredTextLexer.g:240:110: '{' .. '~'
                    {
                    matchRange('{','~'); 

                    }
                    break;
                case 13 :
                    // InternalStructuredTextLexer.g:240:119: '$$'
                    {
                    match("$$"); 


                    }
                    break;
                case 14 :
                    // InternalStructuredTextLexer.g:240:124: '$L'
                    {
                    match("$L"); 


                    }
                    break;
                case 15 :
                    // InternalStructuredTextLexer.g:240:129: '$N'
                    {
                    match("$N"); 


                    }
                    break;
                case 16 :
                    // InternalStructuredTextLexer.g:240:134: '$P'
                    {
                    match("$P"); 


                    }
                    break;
                case 17 :
                    // InternalStructuredTextLexer.g:240:139: '$R'
                    {
                    match("$R"); 


                    }
                    break;
                case 18 :
                    // InternalStructuredTextLexer.g:240:144: '$T'
                    {
                    match("$T"); 


                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_COMMON_CHAR_VALUE"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalStructuredTextLexer.g:242:17: ( ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' ) )
            // InternalStructuredTextLexer.g:242:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
            {
            // InternalStructuredTextLexer.g:242:19: ( '/*' ( options {greedy=false; } : . )* '*/' | '(*' ( options {greedy=false; } : . )* '*)' )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0=='/') ) {
                alt38=1;
            }
            else if ( (LA38_0=='(') ) {
                alt38=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // InternalStructuredTextLexer.g:242:20: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 

                    // InternalStructuredTextLexer.g:242:25: ( options {greedy=false; } : . )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0=='*') ) {
                            int LA36_1 = input.LA(2);

                            if ( (LA36_1=='/') ) {
                                alt36=2;
                            }
                            else if ( ((LA36_1>='\u0000' && LA36_1<='.')||(LA36_1>='0' && LA36_1<='\uFFFF')) ) {
                                alt36=1;
                            }


                        }
                        else if ( ((LA36_0>='\u0000' && LA36_0<=')')||(LA36_0>='+' && LA36_0<='\uFFFF')) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // InternalStructuredTextLexer.g:242:53: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    match("*/"); 


                    }
                    break;
                case 2 :
                    // InternalStructuredTextLexer.g:242:62: '(*' ( options {greedy=false; } : . )* '*)'
                    {
                    match("(*"); 

                    // InternalStructuredTextLexer.g:242:67: ( options {greedy=false; } : . )*
                    loop37:
                    do {
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( (LA37_0=='*') ) {
                            int LA37_1 = input.LA(2);

                            if ( (LA37_1==')') ) {
                                alt37=2;
                            }
                            else if ( ((LA37_1>='\u0000' && LA37_1<='(')||(LA37_1>='*' && LA37_1<='\uFFFF')) ) {
                                alt37=1;
                            }


                        }
                        else if ( ((LA37_0>='\u0000' && LA37_0<=')')||(LA37_0>='+' && LA37_0<='\uFFFF')) ) {
                            alt37=1;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // InternalStructuredTextLexer.g:242:95: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop37;
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
            // InternalStructuredTextLexer.g:244:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalStructuredTextLexer.g:244:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalStructuredTextLexer.g:244:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( ((LA39_0>='\u0000' && LA39_0<='\t')||(LA39_0>='\u000B' && LA39_0<='\f')||(LA39_0>='\u000E' && LA39_0<='\uFFFF')) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:244:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop39;
                }
            } while (true);

            // InternalStructuredTextLexer.g:244:40: ( ( '\\r' )? '\\n' )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0=='\n'||LA41_0=='\r') ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalStructuredTextLexer.g:244:41: ( '\\r' )? '\\n'
                    {
                    // InternalStructuredTextLexer.g:244:41: ( '\\r' )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0=='\r') ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // InternalStructuredTextLexer.g:244:41: '\\r'
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
            // InternalStructuredTextLexer.g:246:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalStructuredTextLexer.g:246:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalStructuredTextLexer.g:246:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt42=0;
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( ((LA42_0>='\t' && LA42_0<='\n')||LA42_0=='\r'||LA42_0==' ') ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalStructuredTextLexer.g:
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
            	    if ( cnt42 >= 1 ) break loop42;
                        EarlyExitException eee =
                            new EarlyExitException(42, input);
                        throw eee;
                }
                cnt42++;
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
            // InternalStructuredTextLexer.g:248:16: ( . )
            // InternalStructuredTextLexer.g:248:18: .
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
        // InternalStructuredTextLexer.g:1:8: ( LDATE_AND_TIME | DATE_AND_TIME | LTIME_OF_DAY | TIME_OF_DAY | END_REPEAT | END_WHILE | CONSTANT | CONTINUE | END_CASE | END_FOR | END_VAR | WSTRING | END_IF | REPEAT | RETURN | STRING | ARRAY | DWORD | ELSIF | FALSE | LDATE | LREAL | LTIME | LWORD | SUPER | UDINT | ULINT | UNTIL | USINT | WCHAR | WHILE | BOOL | BYTE | CASE | CHAR | DATE | DINT | ELSE | EXIT | LINT | LTOD | REAL | SINT | THEN | TIME | TRUE | UINT | WORD | B | D | W | X | AND | FOR | INT | MOD | NOT | TOD | VAR | XOR | AsteriskAsterisk | FullStopFullStop | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | DO | DT | IF | LT | OF | OR | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | E | T | LeftSquareBracket | RightSquareBracket | RULE_TIMEOFDAY | RULE_TIME | RULE_DATETIME | RULE_DATE | RULE_ID | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_UNSIGNED_INT | RULE_S_BYTE_CHAR_STR | RULE_D_BYTE_CHAR_STR | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt43=110;
        alt43 = dfa43.predict(input);
        switch (alt43) {
            case 1 :
                // InternalStructuredTextLexer.g:1:10: LDATE_AND_TIME
                {
                mLDATE_AND_TIME(); 

                }
                break;
            case 2 :
                // InternalStructuredTextLexer.g:1:25: DATE_AND_TIME
                {
                mDATE_AND_TIME(); 

                }
                break;
            case 3 :
                // InternalStructuredTextLexer.g:1:39: LTIME_OF_DAY
                {
                mLTIME_OF_DAY(); 

                }
                break;
            case 4 :
                // InternalStructuredTextLexer.g:1:52: TIME_OF_DAY
                {
                mTIME_OF_DAY(); 

                }
                break;
            case 5 :
                // InternalStructuredTextLexer.g:1:64: END_REPEAT
                {
                mEND_REPEAT(); 

                }
                break;
            case 6 :
                // InternalStructuredTextLexer.g:1:75: END_WHILE
                {
                mEND_WHILE(); 

                }
                break;
            case 7 :
                // InternalStructuredTextLexer.g:1:85: CONSTANT
                {
                mCONSTANT(); 

                }
                break;
            case 8 :
                // InternalStructuredTextLexer.g:1:94: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 9 :
                // InternalStructuredTextLexer.g:1:103: END_CASE
                {
                mEND_CASE(); 

                }
                break;
            case 10 :
                // InternalStructuredTextLexer.g:1:112: END_FOR
                {
                mEND_FOR(); 

                }
                break;
            case 11 :
                // InternalStructuredTextLexer.g:1:120: END_VAR
                {
                mEND_VAR(); 

                }
                break;
            case 12 :
                // InternalStructuredTextLexer.g:1:128: WSTRING
                {
                mWSTRING(); 

                }
                break;
            case 13 :
                // InternalStructuredTextLexer.g:1:136: END_IF
                {
                mEND_IF(); 

                }
                break;
            case 14 :
                // InternalStructuredTextLexer.g:1:143: REPEAT
                {
                mREPEAT(); 

                }
                break;
            case 15 :
                // InternalStructuredTextLexer.g:1:150: RETURN
                {
                mRETURN(); 

                }
                break;
            case 16 :
                // InternalStructuredTextLexer.g:1:157: STRING
                {
                mSTRING(); 

                }
                break;
            case 17 :
                // InternalStructuredTextLexer.g:1:164: ARRAY
                {
                mARRAY(); 

                }
                break;
            case 18 :
                // InternalStructuredTextLexer.g:1:170: DWORD
                {
                mDWORD(); 

                }
                break;
            case 19 :
                // InternalStructuredTextLexer.g:1:176: ELSIF
                {
                mELSIF(); 

                }
                break;
            case 20 :
                // InternalStructuredTextLexer.g:1:182: FALSE
                {
                mFALSE(); 

                }
                break;
            case 21 :
                // InternalStructuredTextLexer.g:1:188: LDATE
                {
                mLDATE(); 

                }
                break;
            case 22 :
                // InternalStructuredTextLexer.g:1:194: LREAL
                {
                mLREAL(); 

                }
                break;
            case 23 :
                // InternalStructuredTextLexer.g:1:200: LTIME
                {
                mLTIME(); 

                }
                break;
            case 24 :
                // InternalStructuredTextLexer.g:1:206: LWORD
                {
                mLWORD(); 

                }
                break;
            case 25 :
                // InternalStructuredTextLexer.g:1:212: SUPER
                {
                mSUPER(); 

                }
                break;
            case 26 :
                // InternalStructuredTextLexer.g:1:218: UDINT
                {
                mUDINT(); 

                }
                break;
            case 27 :
                // InternalStructuredTextLexer.g:1:224: ULINT
                {
                mULINT(); 

                }
                break;
            case 28 :
                // InternalStructuredTextLexer.g:1:230: UNTIL
                {
                mUNTIL(); 

                }
                break;
            case 29 :
                // InternalStructuredTextLexer.g:1:236: USINT
                {
                mUSINT(); 

                }
                break;
            case 30 :
                // InternalStructuredTextLexer.g:1:242: WCHAR
                {
                mWCHAR(); 

                }
                break;
            case 31 :
                // InternalStructuredTextLexer.g:1:248: WHILE
                {
                mWHILE(); 

                }
                break;
            case 32 :
                // InternalStructuredTextLexer.g:1:254: BOOL
                {
                mBOOL(); 

                }
                break;
            case 33 :
                // InternalStructuredTextLexer.g:1:259: BYTE
                {
                mBYTE(); 

                }
                break;
            case 34 :
                // InternalStructuredTextLexer.g:1:264: CASE
                {
                mCASE(); 

                }
                break;
            case 35 :
                // InternalStructuredTextLexer.g:1:269: CHAR
                {
                mCHAR(); 

                }
                break;
            case 36 :
                // InternalStructuredTextLexer.g:1:274: DATE
                {
                mDATE(); 

                }
                break;
            case 37 :
                // InternalStructuredTextLexer.g:1:279: DINT
                {
                mDINT(); 

                }
                break;
            case 38 :
                // InternalStructuredTextLexer.g:1:284: ELSE
                {
                mELSE(); 

                }
                break;
            case 39 :
                // InternalStructuredTextLexer.g:1:289: EXIT
                {
                mEXIT(); 

                }
                break;
            case 40 :
                // InternalStructuredTextLexer.g:1:294: LINT
                {
                mLINT(); 

                }
                break;
            case 41 :
                // InternalStructuredTextLexer.g:1:299: LTOD
                {
                mLTOD(); 

                }
                break;
            case 42 :
                // InternalStructuredTextLexer.g:1:304: REAL
                {
                mREAL(); 

                }
                break;
            case 43 :
                // InternalStructuredTextLexer.g:1:309: SINT
                {
                mSINT(); 

                }
                break;
            case 44 :
                // InternalStructuredTextLexer.g:1:314: THEN
                {
                mTHEN(); 

                }
                break;
            case 45 :
                // InternalStructuredTextLexer.g:1:319: TIME
                {
                mTIME(); 

                }
                break;
            case 46 :
                // InternalStructuredTextLexer.g:1:324: TRUE
                {
                mTRUE(); 

                }
                break;
            case 47 :
                // InternalStructuredTextLexer.g:1:329: UINT
                {
                mUINT(); 

                }
                break;
            case 48 :
                // InternalStructuredTextLexer.g:1:334: WORD
                {
                mWORD(); 

                }
                break;
            case 49 :
                // InternalStructuredTextLexer.g:1:339: B
                {
                mB(); 

                }
                break;
            case 50 :
                // InternalStructuredTextLexer.g:1:341: D
                {
                mD(); 

                }
                break;
            case 51 :
                // InternalStructuredTextLexer.g:1:343: W
                {
                mW(); 

                }
                break;
            case 52 :
                // InternalStructuredTextLexer.g:1:345: X
                {
                mX(); 

                }
                break;
            case 53 :
                // InternalStructuredTextLexer.g:1:347: AND
                {
                mAND(); 

                }
                break;
            case 54 :
                // InternalStructuredTextLexer.g:1:351: FOR
                {
                mFOR(); 

                }
                break;
            case 55 :
                // InternalStructuredTextLexer.g:1:355: INT
                {
                mINT(); 

                }
                break;
            case 56 :
                // InternalStructuredTextLexer.g:1:359: MOD
                {
                mMOD(); 

                }
                break;
            case 57 :
                // InternalStructuredTextLexer.g:1:363: NOT
                {
                mNOT(); 

                }
                break;
            case 58 :
                // InternalStructuredTextLexer.g:1:367: TOD
                {
                mTOD(); 

                }
                break;
            case 59 :
                // InternalStructuredTextLexer.g:1:371: VAR
                {
                mVAR(); 

                }
                break;
            case 60 :
                // InternalStructuredTextLexer.g:1:375: XOR
                {
                mXOR(); 

                }
                break;
            case 61 :
                // InternalStructuredTextLexer.g:1:379: AsteriskAsterisk
                {
                mAsteriskAsterisk(); 

                }
                break;
            case 62 :
                // InternalStructuredTextLexer.g:1:396: FullStopFullStop
                {
                mFullStopFullStop(); 

                }
                break;
            case 63 :
                // InternalStructuredTextLexer.g:1:413: ColonEqualsSign
                {
                mColonEqualsSign(); 

                }
                break;
            case 64 :
                // InternalStructuredTextLexer.g:1:429: LessThanSignEqualsSign
                {
                mLessThanSignEqualsSign(); 

                }
                break;
            case 65 :
                // InternalStructuredTextLexer.g:1:452: LessThanSignGreaterThanSign
                {
                mLessThanSignGreaterThanSign(); 

                }
                break;
            case 66 :
                // InternalStructuredTextLexer.g:1:480: EqualsSignGreaterThanSign
                {
                mEqualsSignGreaterThanSign(); 

                }
                break;
            case 67 :
                // InternalStructuredTextLexer.g:1:506: GreaterThanSignEqualsSign
                {
                mGreaterThanSignEqualsSign(); 

                }
                break;
            case 68 :
                // InternalStructuredTextLexer.g:1:532: AT
                {
                mAT(); 

                }
                break;
            case 69 :
                // InternalStructuredTextLexer.g:1:535: BY
                {
                mBY(); 

                }
                break;
            case 70 :
                // InternalStructuredTextLexer.g:1:538: DO
                {
                mDO(); 

                }
                break;
            case 71 :
                // InternalStructuredTextLexer.g:1:541: DT
                {
                mDT(); 

                }
                break;
            case 72 :
                // InternalStructuredTextLexer.g:1:544: IF
                {
                mIF(); 

                }
                break;
            case 73 :
                // InternalStructuredTextLexer.g:1:547: LT
                {
                mLT(); 

                }
                break;
            case 74 :
                // InternalStructuredTextLexer.g:1:550: OF
                {
                mOF(); 

                }
                break;
            case 75 :
                // InternalStructuredTextLexer.g:1:553: OR
                {
                mOR(); 

                }
                break;
            case 76 :
                // InternalStructuredTextLexer.g:1:556: TO
                {
                mTO(); 

                }
                break;
            case 77 :
                // InternalStructuredTextLexer.g:1:559: NumberSign
                {
                mNumberSign(); 

                }
                break;
            case 78 :
                // InternalStructuredTextLexer.g:1:570: Ampersand
                {
                mAmpersand(); 

                }
                break;
            case 79 :
                // InternalStructuredTextLexer.g:1:580: LeftParenthesis
                {
                mLeftParenthesis(); 

                }
                break;
            case 80 :
                // InternalStructuredTextLexer.g:1:596: RightParenthesis
                {
                mRightParenthesis(); 

                }
                break;
            case 81 :
                // InternalStructuredTextLexer.g:1:613: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 82 :
                // InternalStructuredTextLexer.g:1:622: PlusSign
                {
                mPlusSign(); 

                }
                break;
            case 83 :
                // InternalStructuredTextLexer.g:1:631: Comma
                {
                mComma(); 

                }
                break;
            case 84 :
                // InternalStructuredTextLexer.g:1:637: HyphenMinus
                {
                mHyphenMinus(); 

                }
                break;
            case 85 :
                // InternalStructuredTextLexer.g:1:649: FullStop
                {
                mFullStop(); 

                }
                break;
            case 86 :
                // InternalStructuredTextLexer.g:1:658: Solidus
                {
                mSolidus(); 

                }
                break;
            case 87 :
                // InternalStructuredTextLexer.g:1:666: Colon
                {
                mColon(); 

                }
                break;
            case 88 :
                // InternalStructuredTextLexer.g:1:672: Semicolon
                {
                mSemicolon(); 

                }
                break;
            case 89 :
                // InternalStructuredTextLexer.g:1:682: LessThanSign
                {
                mLessThanSign(); 

                }
                break;
            case 90 :
                // InternalStructuredTextLexer.g:1:695: EqualsSign
                {
                mEqualsSign(); 

                }
                break;
            case 91 :
                // InternalStructuredTextLexer.g:1:706: GreaterThanSign
                {
                mGreaterThanSign(); 

                }
                break;
            case 92 :
                // InternalStructuredTextLexer.g:1:722: E
                {
                mE(); 

                }
                break;
            case 93 :
                // InternalStructuredTextLexer.g:1:724: T
                {
                mT(); 

                }
                break;
            case 94 :
                // InternalStructuredTextLexer.g:1:726: LeftSquareBracket
                {
                mLeftSquareBracket(); 

                }
                break;
            case 95 :
                // InternalStructuredTextLexer.g:1:744: RightSquareBracket
                {
                mRightSquareBracket(); 

                }
                break;
            case 96 :
                // InternalStructuredTextLexer.g:1:763: RULE_TIMEOFDAY
                {
                mRULE_TIMEOFDAY(); 

                }
                break;
            case 97 :
                // InternalStructuredTextLexer.g:1:778: RULE_TIME
                {
                mRULE_TIME(); 

                }
                break;
            case 98 :
                // InternalStructuredTextLexer.g:1:788: RULE_DATETIME
                {
                mRULE_DATETIME(); 

                }
                break;
            case 99 :
                // InternalStructuredTextLexer.g:1:802: RULE_DATE
                {
                mRULE_DATE(); 

                }
                break;
            case 100 :
                // InternalStructuredTextLexer.g:1:812: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 101 :
                // InternalStructuredTextLexer.g:1:820: RULE_BINARY_INT
                {
                mRULE_BINARY_INT(); 

                }
                break;
            case 102 :
                // InternalStructuredTextLexer.g:1:836: RULE_OCTAL_INT
                {
                mRULE_OCTAL_INT(); 

                }
                break;
            case 103 :
                // InternalStructuredTextLexer.g:1:851: RULE_HEX_INT
                {
                mRULE_HEX_INT(); 

                }
                break;
            case 104 :
                // InternalStructuredTextLexer.g:1:864: RULE_UNSIGNED_INT
                {
                mRULE_UNSIGNED_INT(); 

                }
                break;
            case 105 :
                // InternalStructuredTextLexer.g:1:882: RULE_S_BYTE_CHAR_STR
                {
                mRULE_S_BYTE_CHAR_STR(); 

                }
                break;
            case 106 :
                // InternalStructuredTextLexer.g:1:903: RULE_D_BYTE_CHAR_STR
                {
                mRULE_D_BYTE_CHAR_STR(); 

                }
                break;
            case 107 :
                // InternalStructuredTextLexer.g:1:924: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 108 :
                // InternalStructuredTextLexer.g:1:940: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 109 :
                // InternalStructuredTextLexer.g:1:956: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 110 :
                // InternalStructuredTextLexer.g:1:964: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA35 dfa35 = new DFA35(this);
    protected DFA43 dfa43 = new DFA43(this);
    static final String DFA35_eotS =
        "\24\uffff";
    static final String DFA35_eofS =
        "\24\uffff";
    static final String DFA35_minS =
        "\1\40\14\uffff\1\44\6\uffff";
    static final String DFA35_maxS =
        "\1\176\14\uffff\1\124\6\uffff";
    static final String DFA35_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\uffff\1\15\1\16\1\17\1\20\1\21\1\22";
    static final String DFA35_specialS =
        "\24\uffff}>";
    static final String[] DFA35_transitionS = {
            "\1\1\1\2\1\uffff\1\3\1\15\1\4\1\5\1\uffff\10\6\12\7\7\10\32\11\6\12\32\13\4\14",
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
            "\1\16\47\uffff\1\17\1\uffff\1\20\1\uffff\1\21\1\uffff\1\22\1\uffff\1\23",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA35_eot = DFA.unpackEncodedString(DFA35_eotS);
    static final short[] DFA35_eof = DFA.unpackEncodedString(DFA35_eofS);
    static final char[] DFA35_min = DFA.unpackEncodedStringToUnsignedChars(DFA35_minS);
    static final char[] DFA35_max = DFA.unpackEncodedStringToUnsignedChars(DFA35_maxS);
    static final short[] DFA35_accept = DFA.unpackEncodedString(DFA35_acceptS);
    static final short[] DFA35_special = DFA.unpackEncodedString(DFA35_specialS);
    static final short[][] DFA35_transition;

    static {
        int numStates = DFA35_transitionS.length;
        DFA35_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA35_transition[i] = DFA.unpackEncodedString(DFA35_transitionS[i]);
        }
    }

    class DFA35 extends DFA {

        public DFA35(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 35;
            this.eot = DFA35_eot;
            this.eof = DFA35_eof;
            this.min = DFA35_min;
            this.max = DFA35_max;
            this.accept = DFA35_accept;
            this.special = DFA35_special;
            this.transition = DFA35_transition;
        }
        public String getDescription() {
            return "240:35: ( ' ' | '!' | '#' | '%' | '&' | '(' .. '/' | '0' .. '9' | ':' .. '@' | 'A' .. 'Z' | '[' .. '`' | 'a' .. 'z' | '{' .. '~' | '$$' | '$L' | '$N' | '$P' | '$R' | '$T' )";
        }
    }
    static final String DFA43_eotS =
        "\1\uffff\2\62\1\75\1\102\10\62\1\134\5\62\1\144\1\146\1\151\1\153\1\155\1\62\2\uffff\1\163\4\uffff\1\171\4\uffff\3\176\1\uffff\2\54\2\uffff\1\62\1\u0088\3\62\1\uffff\3\62\1\u008f\1\u0090\1\uffff\3\62\1\u0098\2\uffff\3\62\1\uffff\15\62\1\u00ab\10\62\1\u00b5\3\uffff\1\62\1\u00bb\4\62\13\uffff\1\u00c0\1\u00c1\20\uffff\1\176\3\uffff\4\62\1\uffff\6\62\2\uffff\1\62\1\uffff\3\62\1\u00d0\1\62\1\uffff\21\62\1\u00e7\1\uffff\1\62\1\u00e9\7\62\5\uffff\1\u00f1\1\uffff\1\u00f2\1\u00f3\1\u00f4\1\u00f5\3\uffff\2\62\1\u00f8\2\62\1\u00fb\1\u00fd\1\62\1\u00ff\1\62\1\u0102\1\u0103\1\u0104\1\uffff\1\62\1\uffff\3\62\1\u010e\1\u010f\2\62\1\u0112\1\u0113\3\62\1\u0117\2\62\1\u011a\2\62\1\u011d\1\62\1\uffff\1\62\1\uffff\4\62\1\u0124\1\u0125\1\u0126\5\uffff\1\u0128\1\u012a\1\uffff\1\u012b\1\u012c\1\uffff\1\62\1\uffff\1\u012e\1\uffff\2\62\3\uffff\10\62\1\u0136\2\uffff\2\62\2\uffff\1\62\1\u013a\1\u013b\1\uffff\2\62\1\uffff\1\62\1\u013f\1\uffff\1\u0140\1\u0141\1\u0142\1\u0143\1\u0144\1\u0145\3\uffff\1\62\1\uffff\1\62\3\uffff\1\62\1\uffff\6\62\1\u014f\1\uffff\3\62\2\uffff\1\u0153\1\u0154\1\u0155\7\uffff\7\62\1\u015d\1\u015e\1\uffff\2\62\1\u0161\3\uffff\6\62\1\u0168\2\uffff\1\u0169\1\u016a\1\uffff\5\62\1\u0170\3\uffff\4\62\1\u0175\1\uffff\3\62\1\u0179\1\uffff\1\62\1\u017b\1\62\1\uffff\1\62\1\uffff\1\u017e\1\u017f\2\uffff";
    static final String DFA43_eofS =
        "\u0180\uffff";
    static final String DFA43_minS =
        "\1\0\1\104\2\43\1\60\1\101\1\103\1\105\1\111\1\116\1\101\1\104\1\117\1\45\1\106\2\117\1\101\1\117\1\52\2\75\1\76\1\75\1\106\2\uffff\1\52\4\uffff\1\52\4\uffff\2\43\1\66\1\uffff\2\40\2\uffff\2\43\1\105\1\117\1\116\1\uffff\1\124\1\117\1\116\1\60\1\43\1\uffff\1\115\1\105\1\125\1\60\2\uffff\1\104\1\123\1\111\1\uffff\1\116\1\123\1\101\1\124\1\110\1\111\1\122\1\101\1\122\1\120\1\116\1\122\1\104\1\60\1\114\1\122\2\111\1\124\1\111\1\116\1\117\1\60\1\102\2\uffff\1\124\1\60\1\104\1\124\2\122\13\uffff\2\60\20\uffff\1\43\3\uffff\1\124\1\43\1\115\1\104\1\uffff\1\101\1\122\1\124\1\105\1\122\1\124\2\uffff\1\115\1\uffff\1\105\1\116\1\105\1\43\1\137\1\uffff\1\137\1\105\1\124\1\123\1\105\2\122\1\101\1\114\1\104\1\105\1\125\1\114\1\111\1\105\1\124\1\101\1\60\1\uffff\1\123\1\60\2\116\1\111\1\116\1\124\1\114\1\105\5\uffff\1\60\1\uffff\4\60\3\uffff\2\105\1\43\1\114\1\104\1\60\1\43\1\104\1\60\1\105\1\43\2\60\1\uffff\1\131\1\uffff\1\104\1\103\1\106\2\60\1\124\1\111\2\60\1\111\1\122\1\105\1\60\1\101\1\122\1\60\1\116\1\122\1\60\1\131\1\uffff\1\105\1\uffff\2\124\1\114\1\124\3\60\5\uffff\2\43\1\uffff\2\60\1\uffff\1\101\1\uffff\1\60\1\uffff\1\43\1\117\3\uffff\2\43\1\105\1\110\1\101\1\117\1\101\1\106\1\60\2\uffff\1\101\1\116\2\uffff\1\116\2\60\1\uffff\1\124\1\116\1\uffff\1\107\1\60\1\uffff\6\60\3\uffff\1\101\1\uffff\1\117\3\uffff\1\116\1\uffff\1\104\1\120\1\111\1\123\2\122\1\60\1\uffff\1\116\1\125\1\107\2\uffff\3\60\7\uffff\1\116\2\104\1\137\1\105\1\114\1\105\2\60\1\uffff\1\124\1\105\1\60\3\uffff\1\104\2\137\1\104\1\101\1\105\1\60\2\uffff\2\60\1\uffff\1\137\1\104\1\124\1\43\1\124\1\60\3\uffff\1\124\2\43\1\131\1\60\1\uffff\1\43\1\131\1\115\1\43\1\uffff\1\115\1\43\1\105\1\uffff\1\105\1\uffff\2\43\2\uffff";
    static final String DFA43_maxS =
        "\1\uffff\2\167\2\172\1\157\1\163\1\145\1\165\1\164\1\157\1\163\1\171\1\56\1\156\2\157\1\141\1\157\1\52\1\75\2\76\1\75\1\162\2\uffff\1\52\4\uffff\1\57\4\uffff\2\43\1\66\1\uffff\2\176\2\uffff\1\164\1\172\1\145\1\157\1\156\1\uffff\1\164\1\157\1\156\2\172\1\uffff\1\155\1\145\1\165\1\172\2\uffff\1\144\1\163\1\151\1\uffff\1\156\1\163\1\141\1\164\1\150\1\151\1\162\1\164\1\162\1\160\1\156\1\162\1\144\1\172\1\154\1\162\2\151\1\164\1\151\1\156\1\157\1\172\1\170\2\uffff\1\164\1\172\1\144\1\164\2\162\13\uffff\2\172\20\uffff\1\43\3\uffff\1\164\1\151\1\155\1\146\1\uffff\1\141\1\162\1\164\1\145\1\162\1\164\2\uffff\1\155\1\uffff\1\145\1\156\1\145\1\172\1\137\1\uffff\1\137\1\151\2\164\1\145\2\162\1\141\1\154\1\144\1\145\1\165\1\154\1\151\1\145\1\164\1\141\1\172\1\uffff\1\163\1\172\2\156\1\151\1\156\1\164\1\154\1\145\5\uffff\1\172\1\uffff\4\172\3\uffff\2\145\1\172\1\154\1\144\2\172\1\144\1\172\1\145\3\172\1\uffff\1\171\1\uffff\1\144\1\167\1\146\2\172\1\164\1\151\2\172\1\151\1\162\1\145\1\172\1\141\1\162\1\172\1\156\1\162\1\172\1\171\1\uffff\1\145\1\uffff\2\164\1\154\1\164\3\172\5\uffff\2\172\1\uffff\2\172\1\uffff\1\141\1\uffff\1\172\1\uffff\1\43\1\157\3\uffff\1\43\1\141\1\145\1\150\1\141\1\157\1\141\1\146\1\172\2\uffff\1\141\1\156\2\uffff\1\156\2\172\1\uffff\1\164\1\156\1\uffff\1\147\1\172\1\uffff\6\172\3\uffff\1\141\1\uffff\1\157\3\uffff\1\156\1\uffff\1\146\1\160\1\151\1\163\2\162\1\172\1\uffff\1\156\1\165\1\147\2\uffff\3\172\7\uffff\1\156\1\146\1\144\1\137\1\145\1\154\1\145\2\172\1\uffff\1\164\1\145\1\172\3\uffff\1\144\2\137\1\144\1\141\1\145\1\172\2\uffff\2\172\1\uffff\1\137\1\144\1\164\1\141\1\164\1\172\3\uffff\1\164\1\141\1\151\1\171\1\172\1\uffff\1\151\1\171\1\155\1\172\1\uffff\1\155\1\172\1\145\1\uffff\1\145\1\uffff\2\172\2\uffff";
    static final String DFA43_acceptS =
        "\31\uffff\1\115\1\116\1\uffff\1\120\1\122\1\123\1\124\1\uffff\1\130\1\136\1\137\1\144\3\uffff\1\150\2\uffff\1\155\1\156\5\uffff\1\144\5\uffff\1\143\4\uffff\1\135\1\141\3\uffff\1\134\30\uffff\1\76\1\125\6\uffff\1\75\1\121\1\77\1\127\1\100\1\101\1\131\1\102\1\132\1\103\1\133\2\uffff\1\115\1\116\1\153\1\117\1\120\1\122\1\123\1\124\1\154\1\126\1\130\1\136\1\137\1\145\1\150\1\146\1\uffff\1\151\1\152\1\155\4\uffff\1\111\6\uffff\1\106\1\107\1\uffff\1\142\5\uffff\1\114\22\uffff\1\104\11\uffff\1\105\1\61\1\62\1\63\1\64\1\uffff\1\110\4\uffff\1\112\1\113\1\147\15\uffff\1\72\1\uffff\1\140\24\uffff\1\65\1\uffff\1\66\7\uffff\1\67\1\70\1\71\1\73\1\74\2\uffff\1\51\2\uffff\1\50\1\uffff\1\44\1\uffff\1\45\2\uffff\1\55\1\54\1\56\11\uffff\1\46\1\47\2\uffff\1\42\1\43\3\uffff\1\60\2\uffff\1\52\2\uffff\1\53\6\uffff\1\57\1\40\1\41\1\uffff\1\25\1\uffff\1\27\1\26\1\30\1\uffff\1\22\7\uffff\1\23\3\uffff\1\36\1\37\3\uffff\1\31\1\21\1\24\1\32\1\33\1\34\1\35\11\uffff\1\15\3\uffff\1\16\1\17\1\20\7\uffff\1\12\1\13\2\uffff\1\14\6\uffff\1\11\1\7\1\10\5\uffff\1\6\4\uffff\1\5\3\uffff\1\4\1\uffff\1\3\2\uffff\1\2\1\1";
    static final String DFA43_specialS =
        "\1\0\u017f\uffff}>";
    static final String[] DFA43_transitionS = {
            "\11\54\2\53\2\54\1\53\22\54\1\53\1\54\1\52\1\31\2\54\1\32\1\51\1\33\1\34\1\23\1\35\1\36\1\37\1\15\1\40\1\50\1\47\1\45\5\50\1\46\1\50\1\24\1\41\1\25\1\26\1\27\2\54\1\11\1\14\1\5\1\2\1\4\1\12\2\44\1\16\2\44\1\1\1\17\1\20\1\30\2\44\1\7\1\10\1\3\1\13\1\21\1\6\1\22\2\44\1\42\1\54\1\43\1\54\1\44\1\54\1\11\1\14\1\5\1\2\1\4\1\12\2\44\1\16\2\44\1\1\1\17\1\20\1\30\2\44\1\7\1\10\1\3\1\13\1\21\1\6\1\22\2\44\uff85\54",
            "\1\55\4\uffff\1\61\10\uffff\1\57\1\uffff\1\56\2\uffff\1\60\14\uffff\1\55\4\uffff\1\61\10\uffff\1\57\1\uffff\1\56\2\uffff\1\60",
            "\1\70\35\uffff\1\63\7\uffff\1\65\5\uffff\1\66\4\uffff\1\67\2\uffff\1\64\11\uffff\1\63\7\uffff\1\65\5\uffff\1\66\4\uffff\1\67\2\uffff\1\64",
            "\1\76\14\uffff\12\62\7\uffff\7\62\1\72\1\71\5\62\1\74\2\62\1\73\10\62\4\uffff\1\62\1\uffff\7\62\1\72\1\71\5\62\1\74\2\62\1\73\10\62",
            "\12\62\7\uffff\13\62\1\100\1\62\1\77\11\62\1\101\2\62\4\uffff\1\62\1\uffff\13\62\1\100\1\62\1\77\11\62\1\101\2\62",
            "\1\104\6\uffff\1\105\6\uffff\1\103\21\uffff\1\104\6\uffff\1\105\6\uffff\1\103",
            "\1\107\4\uffff\1\110\6\uffff\1\111\3\uffff\1\106\17\uffff\1\107\4\uffff\1\110\6\uffff\1\111\3\uffff\1\106",
            "\1\112\37\uffff\1\112",
            "\1\115\12\uffff\1\113\1\114\23\uffff\1\115\12\uffff\1\113\1\114",
            "\1\117\3\uffff\1\116\1\uffff\1\120\31\uffff\1\117\3\uffff\1\116\1\uffff\1\120",
            "\1\121\15\uffff\1\122\21\uffff\1\121\15\uffff\1\122",
            "\1\123\4\uffff\1\127\2\uffff\1\124\1\uffff\1\125\4\uffff\1\126\20\uffff\1\123\4\uffff\1\127\2\uffff\1\124\1\uffff\1\125\4\uffff\1\126",
            "\1\130\11\uffff\1\131\25\uffff\1\130\11\uffff\1\131",
            "\1\132\10\uffff\1\133",
            "\1\136\7\uffff\1\135\27\uffff\1\136\7\uffff\1\135",
            "\1\137\37\uffff\1\137",
            "\1\140\37\uffff\1\140",
            "\1\141\37\uffff\1\141",
            "\1\142\37\uffff\1\142",
            "\1\143",
            "\1\145",
            "\1\147\1\150",
            "\1\152",
            "\1\154",
            "\1\156\13\uffff\1\157\23\uffff\1\156\13\uffff\1\157",
            "",
            "",
            "\1\162",
            "",
            "",
            "",
            "",
            "\1\162\4\uffff\1\170",
            "",
            "",
            "",
            "",
            "\1\175",
            "\1\177",
            "\1\u0080",
            "",
            "\137\u0081",
            "\137\u0082",
            "",
            "",
            "\1\70\35\uffff\1\u0084\22\uffff\1\u0085\14\uffff\1\u0084\22\uffff\1\u0085",
            "\1\76\14\uffff\12\62\7\uffff\10\62\1\u0086\5\62\1\u0087\13\62\4\uffff\1\62\1\uffff\10\62\1\u0086\5\62\1\u0087\13\62",
            "\1\u0089\37\uffff\1\u0089",
            "\1\u008a\37\uffff\1\u008a",
            "\1\u008b\37\uffff\1\u008b",
            "",
            "\1\u008c\37\uffff\1\u008c",
            "\1\u008d\37\uffff\1\u008d",
            "\1\u008e\37\uffff\1\u008e",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u0092\14\uffff\12\62\7\uffff\10\62\1\u0091\21\62\4\uffff\1\62\1\uffff\10\62\1\u0091\21\62",
            "",
            "\1\u0093\37\uffff\1\u0093",
            "\1\u0094\37\uffff\1\u0094",
            "\1\u0095\37\uffff\1\u0095",
            "\12\62\7\uffff\3\62\1\u0096\1\62\1\u0097\24\62\4\uffff\1\62\1\uffff\3\62\1\u0096\1\62\1\u0097\24\62",
            "",
            "",
            "\1\u0099\37\uffff\1\u0099",
            "\1\u009a\37\uffff\1\u009a",
            "\1\u009b\37\uffff\1\u009b",
            "",
            "\1\u009c\37\uffff\1\u009c",
            "\1\u009d\37\uffff\1\u009d",
            "\1\u009e\37\uffff\1\u009e",
            "\1\u009f\37\uffff\1\u009f",
            "\1\u00a0\37\uffff\1\u00a0",
            "\1\u00a1\37\uffff\1\u00a1",
            "\1\u00a2\37\uffff\1\u00a2",
            "\1\u00a5\16\uffff\1\u00a3\3\uffff\1\u00a4\14\uffff\1\u00a5\16\uffff\1\u00a3\3\uffff\1\u00a4",
            "\1\u00a6\37\uffff\1\u00a6",
            "\1\u00a7\37\uffff\1\u00a7",
            "\1\u00a8\37\uffff\1\u00a8",
            "\1\u00a9\37\uffff\1\u00a9",
            "\1\u00aa\37\uffff\1\u00aa",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u00ac\37\uffff\1\u00ac",
            "\1\u00ad\37\uffff\1\u00ad",
            "\1\u00ae\37\uffff\1\u00ae",
            "\1\u00af\37\uffff\1\u00af",
            "\1\u00b0\37\uffff\1\u00b0",
            "\1\u00b1\37\uffff\1\u00b1",
            "\1\u00b2\37\uffff\1\u00b2",
            "\1\u00b3\37\uffff\1\u00b3",
            "\12\62\7\uffff\23\62\1\u00b4\6\62\4\uffff\1\62\1\uffff\23\62\1\u00b4\6\62",
            "\1\u00b6\1\uffff\1\u00b7\22\uffff\1\u00b8\1\u00b9\11\uffff\1\u00b6\1\uffff\1\u00b7\22\uffff\1\u00b8\1\u00b9",
            "",
            "",
            "\1\u00ba\37\uffff\1\u00ba",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u00bc\37\uffff\1\u00bc",
            "\1\u00bd\37\uffff\1\u00bd",
            "\1\u00be\37\uffff\1\u00be",
            "\1\u00bf\37\uffff\1\u00bf",
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
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
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
            "\1\u00c2",
            "",
            "",
            "",
            "\1\u00c3\37\uffff\1\u00c3",
            "\1\u0092\45\uffff\1\u0091\37\uffff\1\u0091",
            "\1\u00c4\37\uffff\1\u00c4",
            "\1\u00c5\1\uffff\1\u0097\35\uffff\1\u00c5\1\uffff\1\u0097",
            "",
            "\1\u00c6\37\uffff\1\u00c6",
            "\1\u00c7\37\uffff\1\u00c7",
            "\1\u00c8\37\uffff\1\u00c8",
            "\1\u00c9\37\uffff\1\u00c9",
            "\1\u00ca\37\uffff\1\u00ca",
            "\1\u00cb\37\uffff\1\u00cb",
            "",
            "",
            "\1\u00cc\37\uffff\1\u00cc",
            "",
            "\1\u00cd\37\uffff\1\u00cd",
            "\1\u00ce\37\uffff\1\u00ce",
            "\1\u00cf\37\uffff\1\u00cf",
            "\1\u00d2\14\uffff\12\62\7\uffff\1\u00d1\31\62\4\uffff\1\62\1\uffff\1\u00d1\31\62",
            "\1\u00d3",
            "",
            "\1\u00d4",
            "\1\u00d6\3\uffff\1\u00d5\33\uffff\1\u00d6\3\uffff\1\u00d5",
            "\1\u00d7\37\uffff\1\u00d7",
            "\1\u00d8\1\u00d9\36\uffff\1\u00d8\1\u00d9",
            "\1\u00da\37\uffff\1\u00da",
            "\1\u00db\37\uffff\1\u00db",
            "\1\u00dc\37\uffff\1\u00dc",
            "\1\u00dd\37\uffff\1\u00dd",
            "\1\u00de\37\uffff\1\u00de",
            "\1\u00df\37\uffff\1\u00df",
            "\1\u00e0\37\uffff\1\u00e0",
            "\1\u00e1\37\uffff\1\u00e1",
            "\1\u00e2\37\uffff\1\u00e2",
            "\1\u00e3\37\uffff\1\u00e3",
            "\1\u00e4\37\uffff\1\u00e4",
            "\1\u00e5\37\uffff\1\u00e5",
            "\1\u00e6\37\uffff\1\u00e6",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u00e8\37\uffff\1\u00e8",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u00ea\37\uffff\1\u00ea",
            "\1\u00eb\37\uffff\1\u00eb",
            "\1\u00ec\37\uffff\1\u00ec",
            "\1\u00ed\37\uffff\1\u00ed",
            "\1\u00ee\37\uffff\1\u00ee",
            "\1\u00ef\37\uffff\1\u00ef",
            "\1\u00f0\37\uffff\1\u00f0",
            "",
            "",
            "",
            "",
            "",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "",
            "",
            "\1\u00f6\37\uffff\1\u00f6",
            "\1\u00f7\37\uffff\1\u00f7",
            "\1\u00d2\14\uffff\12\62\7\uffff\1\u00d1\31\62\4\uffff\1\62\1\uffff\1\u00d1\31\62",
            "\1\u00f9\37\uffff\1\u00f9",
            "\1\u00fa\37\uffff\1\u00fa",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\70\14\uffff\12\62\7\uffff\32\62\4\uffff\1\u00fc\1\uffff\32\62",
            "\1\u00fe\37\uffff\1\u00fe",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u0100\37\uffff\1\u0100",
            "\1\76\14\uffff\12\62\7\uffff\32\62\4\uffff\1\u0101\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u0105\37\uffff\1\u0105",
            "",
            "\1\u0106\37\uffff\1\u0106",
            "\1\u0109\2\uffff\1\u010a\2\uffff\1\u010c\10\uffff\1\u0107\3\uffff\1\u010b\1\u0108\13\uffff\1\u0109\2\uffff\1\u010a\2\uffff\1\u010c\10\uffff\1\u0107\3\uffff\1\u010b\1\u0108",
            "\1\u010d\37\uffff\1\u010d",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u0110\37\uffff\1\u0110",
            "\1\u0111\37\uffff\1\u0111",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u0114\37\uffff\1\u0114",
            "\1\u0115\37\uffff\1\u0115",
            "\1\u0116\37\uffff\1\u0116",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u0118\37\uffff\1\u0118",
            "\1\u0119\37\uffff\1\u0119",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u011b\37\uffff\1\u011b",
            "\1\u011c\37\uffff\1\u011c",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u011e\37\uffff\1\u011e",
            "",
            "\1\u011f\37\uffff\1\u011f",
            "",
            "\1\u0120\37\uffff\1\u0120",
            "\1\u0121\37\uffff\1\u0121",
            "\1\u0122\37\uffff\1\u0122",
            "\1\u0123\37\uffff\1\u0123",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "",
            "",
            "",
            "",
            "\1\70\14\uffff\12\62\7\uffff\32\62\4\uffff\1\u0127\1\uffff\32\62",
            "\1\76\14\uffff\12\62\7\uffff\32\62\4\uffff\1\u0129\1\uffff\32\62",
            "",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u012d\37\uffff\1\u012d",
            "",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u0092",
            "\1\u012f\37\uffff\1\u012f",
            "",
            "",
            "",
            "\1\u00d2",
            "\1\u00d2\35\uffff\1\u00d1\37\uffff\1\u00d1",
            "\1\u0130\37\uffff\1\u0130",
            "\1\u0131\37\uffff\1\u0131",
            "\1\u0132\37\uffff\1\u0132",
            "\1\u0133\37\uffff\1\u0133",
            "\1\u0134\37\uffff\1\u0134",
            "\1\u0135\37\uffff\1\u0135",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "",
            "\1\u0137\37\uffff\1\u0137",
            "\1\u0138\37\uffff\1\u0138",
            "",
            "",
            "\1\u0139\37\uffff\1\u0139",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u013c\37\uffff\1\u013c",
            "\1\u013d\37\uffff\1\u013d",
            "",
            "\1\u013e\37\uffff\1\u013e",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "",
            "",
            "\1\u0146\37\uffff\1\u0146",
            "",
            "\1\u0147\37\uffff\1\u0147",
            "",
            "",
            "",
            "\1\u0148\37\uffff\1\u0148",
            "",
            "\1\u0106\1\uffff\1\u0149\35\uffff\1\u0106\1\uffff\1\u0149",
            "\1\u014a\37\uffff\1\u014a",
            "\1\u014b\37\uffff\1\u014b",
            "\1\u014c\37\uffff\1\u014c",
            "\1\u014d\37\uffff\1\u014d",
            "\1\u014e\37\uffff\1\u014e",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u0150\37\uffff\1\u0150",
            "\1\u0151\37\uffff\1\u0151",
            "\1\u0152\37\uffff\1\u0152",
            "",
            "",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0156\37\uffff\1\u0156",
            "\1\u0106\1\uffff\1\u0157\35\uffff\1\u0106\1\uffff\1\u0157",
            "\1\u0158\37\uffff\1\u0158",
            "\1\u0159",
            "\1\u015a\37\uffff\1\u015a",
            "\1\u015b\37\uffff\1\u015b",
            "\1\u015c\37\uffff\1\u015c",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u015f\37\uffff\1\u015f",
            "\1\u0160\37\uffff\1\u0160",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "",
            "",
            "\1\u0162\37\uffff\1\u0162",
            "\1\u0163",
            "\1\u0164",
            "\1\u0165\37\uffff\1\u0165",
            "\1\u0166\37\uffff\1\u0166",
            "\1\u0167\37\uffff\1\u0167",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u016b",
            "\1\u016c\37\uffff\1\u016c",
            "\1\u016d\37\uffff\1\u016d",
            "\1\u00d2\35\uffff\1\u016e\37\uffff\1\u016e",
            "\1\u016f\37\uffff\1\u016f",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "",
            "",
            "\1\u0171\37\uffff\1\u0171",
            "\1\u00d2\35\uffff\1\u0172\37\uffff\1\u0172",
            "\1\u0092\45\uffff\1\u0173\37\uffff\1\u0173",
            "\1\u0174\37\uffff\1\u0174",
            "\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u0092\45\uffff\1\u0176\37\uffff\1\u0176",
            "\1\u0177\37\uffff\1\u0177",
            "\1\u0178\37\uffff\1\u0178",
            "\1\u00d2\14\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            "\1\u017a\37\uffff\1\u017a",
            "\1\u00d2\14\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u017c\37\uffff\1\u017c",
            "",
            "\1\u017d\37\uffff\1\u017d",
            "",
            "\1\u0092\14\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "\1\u0092\14\uffff\12\62\7\uffff\32\62\4\uffff\1\62\1\uffff\32\62",
            "",
            ""
    };

    static final short[] DFA43_eot = DFA.unpackEncodedString(DFA43_eotS);
    static final short[] DFA43_eof = DFA.unpackEncodedString(DFA43_eofS);
    static final char[] DFA43_min = DFA.unpackEncodedStringToUnsignedChars(DFA43_minS);
    static final char[] DFA43_max = DFA.unpackEncodedStringToUnsignedChars(DFA43_maxS);
    static final short[] DFA43_accept = DFA.unpackEncodedString(DFA43_acceptS);
    static final short[] DFA43_special = DFA.unpackEncodedString(DFA43_specialS);
    static final short[][] DFA43_transition;

    static {
        int numStates = DFA43_transitionS.length;
        DFA43_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA43_transition[i] = DFA.unpackEncodedString(DFA43_transitionS[i]);
        }
    }

    class DFA43 extends DFA {

        public DFA43(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 43;
            this.eot = DFA43_eot;
            this.eof = DFA43_eof;
            this.min = DFA43_min;
            this.max = DFA43_max;
            this.accept = DFA43_accept;
            this.special = DFA43_special;
            this.transition = DFA43_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( LDATE_AND_TIME | DATE_AND_TIME | LTIME_OF_DAY | TIME_OF_DAY | END_REPEAT | END_WHILE | CONSTANT | CONTINUE | END_CASE | END_FOR | END_VAR | WSTRING | END_IF | REPEAT | RETURN | STRING | ARRAY | DWORD | ELSIF | FALSE | LDATE | LREAL | LTIME | LWORD | SUPER | UDINT | ULINT | UNTIL | USINT | WCHAR | WHILE | BOOL | BYTE | CASE | CHAR | DATE | DINT | ELSE | EXIT | LINT | LTOD | REAL | SINT | THEN | TIME | TRUE | UINT | WORD | B | D | W | X | AND | FOR | INT | MOD | NOT | TOD | VAR | XOR | AsteriskAsterisk | FullStopFullStop | ColonEqualsSign | LessThanSignEqualsSign | LessThanSignGreaterThanSign | EqualsSignGreaterThanSign | GreaterThanSignEqualsSign | AT | BY | DO | DT | IF | LT | OF | OR | TO | NumberSign | Ampersand | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | Semicolon | LessThanSign | EqualsSign | GreaterThanSign | E | T | LeftSquareBracket | RightSquareBracket | RULE_TIMEOFDAY | RULE_TIME | RULE_DATETIME | RULE_DATE | RULE_ID | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_UNSIGNED_INT | RULE_S_BYTE_CHAR_STR | RULE_D_BYTE_CHAR_STR | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA43_0 = input.LA(1);

                        s = -1;
                        if ( (LA43_0=='L'||LA43_0=='l') ) {s = 1;}

                        else if ( (LA43_0=='D'||LA43_0=='d') ) {s = 2;}

                        else if ( (LA43_0=='T'||LA43_0=='t') ) {s = 3;}

                        else if ( (LA43_0=='E'||LA43_0=='e') ) {s = 4;}

                        else if ( (LA43_0=='C'||LA43_0=='c') ) {s = 5;}

                        else if ( (LA43_0=='W'||LA43_0=='w') ) {s = 6;}

                        else if ( (LA43_0=='R'||LA43_0=='r') ) {s = 7;}

                        else if ( (LA43_0=='S'||LA43_0=='s') ) {s = 8;}

                        else if ( (LA43_0=='A'||LA43_0=='a') ) {s = 9;}

                        else if ( (LA43_0=='F'||LA43_0=='f') ) {s = 10;}

                        else if ( (LA43_0=='U'||LA43_0=='u') ) {s = 11;}

                        else if ( (LA43_0=='B'||LA43_0=='b') ) {s = 12;}

                        else if ( (LA43_0=='.') ) {s = 13;}

                        else if ( (LA43_0=='I'||LA43_0=='i') ) {s = 14;}

                        else if ( (LA43_0=='M'||LA43_0=='m') ) {s = 15;}

                        else if ( (LA43_0=='N'||LA43_0=='n') ) {s = 16;}

                        else if ( (LA43_0=='V'||LA43_0=='v') ) {s = 17;}

                        else if ( (LA43_0=='X'||LA43_0=='x') ) {s = 18;}

                        else if ( (LA43_0=='*') ) {s = 19;}

                        else if ( (LA43_0==':') ) {s = 20;}

                        else if ( (LA43_0=='<') ) {s = 21;}

                        else if ( (LA43_0=='=') ) {s = 22;}

                        else if ( (LA43_0=='>') ) {s = 23;}

                        else if ( (LA43_0=='O'||LA43_0=='o') ) {s = 24;}

                        else if ( (LA43_0=='#') ) {s = 25;}

                        else if ( (LA43_0=='&') ) {s = 26;}

                        else if ( (LA43_0=='(') ) {s = 27;}

                        else if ( (LA43_0==')') ) {s = 28;}

                        else if ( (LA43_0=='+') ) {s = 29;}

                        else if ( (LA43_0==',') ) {s = 30;}

                        else if ( (LA43_0=='-') ) {s = 31;}

                        else if ( (LA43_0=='/') ) {s = 32;}

                        else if ( (LA43_0==';') ) {s = 33;}

                        else if ( (LA43_0=='[') ) {s = 34;}

                        else if ( (LA43_0==']') ) {s = 35;}

                        else if ( ((LA43_0>='G' && LA43_0<='H')||(LA43_0>='J' && LA43_0<='K')||(LA43_0>='P' && LA43_0<='Q')||(LA43_0>='Y' && LA43_0<='Z')||LA43_0=='_'||(LA43_0>='g' && LA43_0<='h')||(LA43_0>='j' && LA43_0<='k')||(LA43_0>='p' && LA43_0<='q')||(LA43_0>='y' && LA43_0<='z')) ) {s = 36;}

                        else if ( (LA43_0=='2') ) {s = 37;}

                        else if ( (LA43_0=='8') ) {s = 38;}

                        else if ( (LA43_0=='1') ) {s = 39;}

                        else if ( (LA43_0=='0'||(LA43_0>='3' && LA43_0<='7')||LA43_0=='9') ) {s = 40;}

                        else if ( (LA43_0=='\'') ) {s = 41;}

                        else if ( (LA43_0=='\"') ) {s = 42;}

                        else if ( ((LA43_0>='\t' && LA43_0<='\n')||LA43_0=='\r'||LA43_0==' ') ) {s = 43;}

                        else if ( ((LA43_0>='\u0000' && LA43_0<='\b')||(LA43_0>='\u000B' && LA43_0<='\f')||(LA43_0>='\u000E' && LA43_0<='\u001F')||LA43_0=='!'||(LA43_0>='$' && LA43_0<='%')||(LA43_0>='?' && LA43_0<='@')||LA43_0=='\\'||LA43_0=='^'||LA43_0=='`'||(LA43_0>='{' && LA43_0<='\uFFFF')) ) {s = 44;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 43, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}