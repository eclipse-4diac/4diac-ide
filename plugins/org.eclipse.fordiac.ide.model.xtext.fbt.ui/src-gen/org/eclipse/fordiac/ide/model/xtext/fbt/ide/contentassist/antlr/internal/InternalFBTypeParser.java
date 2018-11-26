package org.eclipse.fordiac.ide.model.xtext.fbt.ide.contentassist.antlr.internal;
import java.util.Map;
import java.util.HashMap;

import org.eclipse.xtext.*;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.fordiac.ide.model.xtext.fbt.services.FBTypeGrammarAccess;



import org.antlr.runtime.*;

@SuppressWarnings("all")
public class InternalFBTypeParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_STRING=6;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_INT=5;
    public static final int RULE_ML_COMMENT=7;
    public static final int EOF=-1;

    // delegates
    // delegators


        public InternalFBTypeParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalFBTypeParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    @Override
	public String[] getTokenNames() { return InternalFBTypeParser.tokenNames; }
    @Override
	public String getGrammarFileName() { return "InternalFBTypeParser.g"; }


    	private FBTypeGrammarAccess grammarAccess;
    	private final Map<String, String> tokenNameToValue = new HashMap<String, String>();
    	
    	{
    	}

    	public void setGrammarAccess(FBTypeGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		String result = tokenNameToValue.get(tokenName);
    		if (result == null)
    			result = tokenName;
    		return result;
    	}



    // $ANTLR start "entryRuleLibraryElement"
    // InternalFBTypeParser.g:55:1: entryRuleLibraryElement : ruleLibraryElement EOF ;
    public final void entryRuleLibraryElement() throws RecognitionException {
        try {
            // InternalFBTypeParser.g:56:1: ( ruleLibraryElement EOF )
            // InternalFBTypeParser.g:57:1: ruleLibraryElement EOF
            {
             before(grammarAccess.getLibraryElementRule()); 
            pushFollow(FOLLOW_1);
            ruleLibraryElement();

            state._fsp--;

             after(grammarAccess.getLibraryElementRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLibraryElement"


    // $ANTLR start "ruleLibraryElement"
    // InternalFBTypeParser.g:64:1: ruleLibraryElement : ( ( rule__LibraryElement__NameAssignment ) ) ;
    public final void ruleLibraryElement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalFBTypeParser.g:68:2: ( ( ( rule__LibraryElement__NameAssignment ) ) )
            // InternalFBTypeParser.g:69:2: ( ( rule__LibraryElement__NameAssignment ) )
            {
            // InternalFBTypeParser.g:69:2: ( ( rule__LibraryElement__NameAssignment ) )
            // InternalFBTypeParser.g:70:3: ( rule__LibraryElement__NameAssignment )
            {
             before(grammarAccess.getLibraryElementAccess().getNameAssignment()); 
            // InternalFBTypeParser.g:71:3: ( rule__LibraryElement__NameAssignment )
            // InternalFBTypeParser.g:71:4: rule__LibraryElement__NameAssignment
            {
            pushFollow(FOLLOW_2);
            rule__LibraryElement__NameAssignment();

            state._fsp--;


            }

             after(grammarAccess.getLibraryElementAccess().getNameAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLibraryElement"


    // $ANTLR start "rule__LibraryElement__NameAssignment"
    // InternalFBTypeParser.g:79:1: rule__LibraryElement__NameAssignment : ( RULE_ID ) ;
    public final void rule__LibraryElement__NameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalFBTypeParser.g:83:1: ( ( RULE_ID ) )
            // InternalFBTypeParser.g:84:2: ( RULE_ID )
            {
            // InternalFBTypeParser.g:84:2: ( RULE_ID )
            // InternalFBTypeParser.g:85:3: RULE_ID
            {
             before(grammarAccess.getLibraryElementAccess().getNameIDTerminalRuleCall_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLibraryElementAccess().getNameIDTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LibraryElement__NameAssignment"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});

}