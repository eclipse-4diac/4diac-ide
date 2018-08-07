package org.eclipse.fordiac.ide.model.xtext.fbt.parser.antlr.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.fordiac.ide.model.xtext.fbt.services.FBTypeGrammarAccess;



import org.antlr.runtime.*;

@SuppressWarnings("all")
public class InternalFBTypeParser extends AbstractInternalAntlrParser {
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

        public InternalFBTypeParser(TokenStream input, FBTypeGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "LibraryElement";
       	}

       	@Override
       	protected FBTypeGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleLibraryElement"
    // InternalFBTypeParser.g:57:1: entryRuleLibraryElement returns [EObject current=null] : iv_ruleLibraryElement= ruleLibraryElement EOF ;
    public final EObject entryRuleLibraryElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLibraryElement = null;


        try {
            // InternalFBTypeParser.g:57:55: (iv_ruleLibraryElement= ruleLibraryElement EOF )
            // InternalFBTypeParser.g:58:2: iv_ruleLibraryElement= ruleLibraryElement EOF
            {
             newCompositeNode(grammarAccess.getLibraryElementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLibraryElement=ruleLibraryElement();

            state._fsp--;

             current =iv_ruleLibraryElement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLibraryElement"


    // $ANTLR start "ruleLibraryElement"
    // InternalFBTypeParser.g:64:1: ruleLibraryElement returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleLibraryElement() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalFBTypeParser.g:70:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalFBTypeParser.g:71:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalFBTypeParser.g:71:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalFBTypeParser.g:72:3: (lv_name_0_0= RULE_ID )
            {
            // InternalFBTypeParser.g:72:3: (lv_name_0_0= RULE_ID )
            // InternalFBTypeParser.g:73:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getLibraryElementAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getLibraryElementRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLibraryElement"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});

}