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
package org.eclipse.fordiac.ide.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.fordiac.ide.services.ContractSpecGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalContractSpecParser extends AbstractInternalContentAssistParser {
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

    	public void setGrammarAccess(ContractSpecGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleModel"
    // InternalContractSpec.g:63:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // InternalContractSpec.g:64:1: ( ruleModel EOF )
            // InternalContractSpec.g:65:1: ruleModel EOF
            {
             before(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            ruleModel();

            state._fsp--;

             after(grammarAccess.getModelRule()); 
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
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalContractSpec.g:72:1: ruleModel : ( ( rule__Model__TimeSpecAssignment )* ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalContractSpec.g:76:2: ( ( ( rule__Model__TimeSpecAssignment )* ) )
            // InternalContractSpec.g:77:2: ( ( rule__Model__TimeSpecAssignment )* )
            {
            // InternalContractSpec.g:77:2: ( ( rule__Model__TimeSpecAssignment )* )
            // InternalContractSpec.g:78:3: ( rule__Model__TimeSpecAssignment )*
            {
             before(grammarAccess.getModelAccess().getTimeSpecAssignment()); 
            // InternalContractSpec.g:79:3: ( rule__Model__TimeSpecAssignment )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalContractSpec.g:79:4: rule__Model__TimeSpecAssignment
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__Model__TimeSpecAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getModelAccess().getTimeSpecAssignment()); 

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
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleTimeSpec"
    // InternalContractSpec.g:88:1: entryRuleTimeSpec : ruleTimeSpec EOF ;
    public final void entryRuleTimeSpec() throws RecognitionException {
        try {
            // InternalContractSpec.g:89:1: ( ruleTimeSpec EOF )
            // InternalContractSpec.g:90:1: ruleTimeSpec EOF
            {
             before(grammarAccess.getTimeSpecRule()); 
            pushFollow(FOLLOW_1);
            ruleTimeSpec();

            state._fsp--;

             after(grammarAccess.getTimeSpecRule()); 
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
    // $ANTLR end "entryRuleTimeSpec"


    // $ANTLR start "ruleTimeSpec"
    // InternalContractSpec.g:97:1: ruleTimeSpec : ( 'dummy_rule' ) ;
    public final void ruleTimeSpec() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalContractSpec.g:101:2: ( ( 'dummy_rule' ) )
            // InternalContractSpec.g:102:2: ( 'dummy_rule' )
            {
            // InternalContractSpec.g:102:2: ( 'dummy_rule' )
            // InternalContractSpec.g:103:3: 'dummy_rule'
            {
             before(grammarAccess.getTimeSpecAccess().getDummy_ruleKeyword()); 
            match(input,11,FOLLOW_2); 
             after(grammarAccess.getTimeSpecAccess().getDummy_ruleKeyword()); 

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
    // $ANTLR end "ruleTimeSpec"


    // $ANTLR start "rule__Model__TimeSpecAssignment"
    // InternalContractSpec.g:112:1: rule__Model__TimeSpecAssignment : ( ruleTimeSpec ) ;
    public final void rule__Model__TimeSpecAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalContractSpec.g:116:1: ( ( ruleTimeSpec ) )
            // InternalContractSpec.g:117:2: ( ruleTimeSpec )
            {
            // InternalContractSpec.g:117:2: ( ruleTimeSpec )
            // InternalContractSpec.g:118:3: ruleTimeSpec
            {
             before(grammarAccess.getModelAccess().getTimeSpecTimeSpecParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleTimeSpec();

            state._fsp--;

             after(grammarAccess.getModelAccess().getTimeSpecTimeSpecParserRuleCall_0()); 

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
    // $ANTLR end "rule__Model__TimeSpecAssignment"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000802L});

}