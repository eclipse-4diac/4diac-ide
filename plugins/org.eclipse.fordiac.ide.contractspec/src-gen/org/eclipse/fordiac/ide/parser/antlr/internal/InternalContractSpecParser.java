/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.fordiac.ide.services.ContractSpecGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalContractSpecParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'dummy_rule'"
    };
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_STRING=6;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_INT=5;
    public static final int T__11=11;
    public static final int RULE_ML_COMMENT=7;
    public static final int EOF=-1;

    // delegates
    // delegators


        public InternalContractSpecParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalContractSpecParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalContractSpecParser.tokenNames; }
    public String getGrammarFileName() { return "InternalContractSpec.g"; }



     	private ContractSpecGrammarAccess grammarAccess;

        public InternalContractSpecParser(TokenStream input, ContractSpecGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected ContractSpecGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalContractSpec.g:74:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalContractSpec.g:74:46: (iv_ruleModel= ruleModel EOF )
            // InternalContractSpec.g:75:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
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
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalContractSpec.g:81:1: ruleModel returns [EObject current=null] : ( (lv_timeSpec_0_0= ruleTimeSpec ) )* ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_timeSpec_0_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:87:2: ( ( (lv_timeSpec_0_0= ruleTimeSpec ) )* )
            // InternalContractSpec.g:88:2: ( (lv_timeSpec_0_0= ruleTimeSpec ) )*
            {
            // InternalContractSpec.g:88:2: ( (lv_timeSpec_0_0= ruleTimeSpec ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalContractSpec.g:89:3: (lv_timeSpec_0_0= ruleTimeSpec )
            	    {
            	    // InternalContractSpec.g:89:3: (lv_timeSpec_0_0= ruleTimeSpec )
            	    // InternalContractSpec.g:90:4: lv_timeSpec_0_0= ruleTimeSpec
            	    {

            	    				newCompositeNode(grammarAccess.getModelAccess().getTimeSpecTimeSpecParserRuleCall_0());
            	    			
            	    pushFollow(FOLLOW_3);
            	    lv_timeSpec_0_0=ruleTimeSpec();

            	    state._fsp--;


            	    				if (current==null) {
            	    					current = createModelElementForParent(grammarAccess.getModelRule());
            	    				}
            	    				add(
            	    					current,
            	    					"timeSpec",
            	    					lv_timeSpec_0_0,
            	    					"org.eclipse.fordiac.ide.ContractSpec.TimeSpec");
            	    				afterParserOrEnumRuleCall();
            	    			

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


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
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleTimeSpec"
    // InternalContractSpec.g:110:1: entryRuleTimeSpec returns [String current=null] : iv_ruleTimeSpec= ruleTimeSpec EOF ;
    public final String entryRuleTimeSpec() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTimeSpec = null;


        try {
            // InternalContractSpec.g:110:48: (iv_ruleTimeSpec= ruleTimeSpec EOF )
            // InternalContractSpec.g:111:2: iv_ruleTimeSpec= ruleTimeSpec EOF
            {
             newCompositeNode(grammarAccess.getTimeSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTimeSpec=ruleTimeSpec();

            state._fsp--;

             current =iv_ruleTimeSpec.getText(); 
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
    // $ANTLR end "entryRuleTimeSpec"


    // $ANTLR start "ruleTimeSpec"
    // InternalContractSpec.g:117:1: ruleTimeSpec returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'dummy_rule' ;
    public final AntlrDatatypeRuleToken ruleTimeSpec() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalContractSpec.g:123:2: (kw= 'dummy_rule' )
            // InternalContractSpec.g:124:2: kw= 'dummy_rule'
            {
            kw=(Token)match(input,11,FOLLOW_2); 

            		current.merge(kw);
            		newLeafNode(kw, grammarAccess.getTimeSpecAccess().getDummy_ruleKeyword());
            	

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
    // $ANTLR end "ruleTimeSpec"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000802L});

}