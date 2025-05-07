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
import org.eclipse.emf.common.util.Enumerator;
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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'occurs'", "'within'", "'using'", "'clock'", "'every'", "'with'", "'and'", "'jitter'", "'offset'", "'whenever'", "'then'", "'once'", "'out'", "'of'", "'times'", "'has'", "'occurred'", "'Reaction'", "'('", "','", "')'", "'Age'", "'{'", "'}'", "'.'", "'['", "']'", "':='", "'Clock'", "'resolution'", "'skew'", "'drift'", "'maxdiff'", "'s'", "'ms'", "'us'", "'ns'", "'|>'", "'<|'", "'FIFO'", "'LIFO'", "'ID'"
    };
    public static final int T__50=50;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int RULE_ID=4;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=5;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

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
    // InternalContractSpec.g:75:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalContractSpec.g:75:46: (iv_ruleModel= ruleModel EOF )
            // InternalContractSpec.g:76:2: iv_ruleModel= ruleModel EOF
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
    // InternalContractSpec.g:82:1: ruleModel returns [EObject current=null] : ( (lv_timeSpec_0_0= ruleTimeSpec ) )* ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_timeSpec_0_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:88:2: ( ( (lv_timeSpec_0_0= ruleTimeSpec ) )* )
            // InternalContractSpec.g:89:2: ( (lv_timeSpec_0_0= ruleTimeSpec ) )*
            {
            // InternalContractSpec.g:89:2: ( (lv_timeSpec_0_0= ruleTimeSpec ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_ID||LA1_0==20||LA1_0==28||LA1_0==32||LA1_0==39||(LA1_0>=48 && LA1_0<=49)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalContractSpec.g:90:3: (lv_timeSpec_0_0= ruleTimeSpec )
            	    {
            	    // InternalContractSpec.g:90:3: (lv_timeSpec_0_0= ruleTimeSpec )
            	    // InternalContractSpec.g:91:4: lv_timeSpec_0_0= ruleTimeSpec
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
    // InternalContractSpec.g:111:1: entryRuleTimeSpec returns [EObject current=null] : iv_ruleTimeSpec= ruleTimeSpec EOF ;
    public final EObject entryRuleTimeSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeSpec = null;


        try {
            // InternalContractSpec.g:111:49: (iv_ruleTimeSpec= ruleTimeSpec EOF )
            // InternalContractSpec.g:112:2: iv_ruleTimeSpec= ruleTimeSpec EOF
            {
             newCompositeNode(grammarAccess.getTimeSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTimeSpec=ruleTimeSpec();

            state._fsp--;

             current =iv_ruleTimeSpec; 
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
    // InternalContractSpec.g:118:1: ruleTimeSpec returns [EObject current=null] : (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition ) ;
    public final EObject ruleTimeSpec() throws RecognitionException {
        EObject current = null;

        EObject this_SingleEvent_0 = null;

        EObject this_Repetition_1 = null;

        EObject this_Reaction_2 = null;

        EObject this_Age_3 = null;

        EObject this_CausalReaction_4 = null;

        EObject this_CausalAge_5 = null;

        EObject this_CausalFuncDecl_6 = null;

        EObject this_ClockDefinition_7 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:124:2: ( (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition ) )
            // InternalContractSpec.g:125:2: (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition )
            {
            // InternalContractSpec.g:125:2: (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition )
            int alt2=8;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // InternalContractSpec.g:126:3: this_SingleEvent_0= ruleSingleEvent
                    {

                    			newCompositeNode(grammarAccess.getTimeSpecAccess().getSingleEventParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_SingleEvent_0=ruleSingleEvent();

                    state._fsp--;


                    			current = this_SingleEvent_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:135:3: this_Repetition_1= ruleRepetition
                    {

                    			newCompositeNode(grammarAccess.getTimeSpecAccess().getRepetitionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Repetition_1=ruleRepetition();

                    state._fsp--;


                    			current = this_Repetition_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalContractSpec.g:144:3: this_Reaction_2= ruleReaction
                    {

                    			newCompositeNode(grammarAccess.getTimeSpecAccess().getReactionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Reaction_2=ruleReaction();

                    state._fsp--;


                    			current = this_Reaction_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalContractSpec.g:153:3: this_Age_3= ruleAge
                    {

                    			newCompositeNode(grammarAccess.getTimeSpecAccess().getAgeParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Age_3=ruleAge();

                    state._fsp--;


                    			current = this_Age_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalContractSpec.g:162:3: this_CausalReaction_4= ruleCausalReaction
                    {

                    			newCompositeNode(grammarAccess.getTimeSpecAccess().getCausalReactionParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_CausalReaction_4=ruleCausalReaction();

                    state._fsp--;


                    			current = this_CausalReaction_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalContractSpec.g:171:3: this_CausalAge_5= ruleCausalAge
                    {

                    			newCompositeNode(grammarAccess.getTimeSpecAccess().getCausalAgeParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_CausalAge_5=ruleCausalAge();

                    state._fsp--;


                    			current = this_CausalAge_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalContractSpec.g:180:3: this_CausalFuncDecl_6= ruleCausalFuncDecl
                    {

                    			newCompositeNode(grammarAccess.getTimeSpecAccess().getCausalFuncDeclParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_CausalFuncDecl_6=ruleCausalFuncDecl();

                    state._fsp--;


                    			current = this_CausalFuncDecl_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalContractSpec.g:189:3: this_ClockDefinition_7= ruleClockDefinition
                    {

                    			newCompositeNode(grammarAccess.getTimeSpecAccess().getClockDefinitionParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_ClockDefinition_7=ruleClockDefinition();

                    state._fsp--;


                    			current = this_ClockDefinition_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

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
    // $ANTLR end "ruleTimeSpec"


    // $ANTLR start "entryRuleSingleEvent"
    // InternalContractSpec.g:201:1: entryRuleSingleEvent returns [EObject current=null] : iv_ruleSingleEvent= ruleSingleEvent EOF ;
    public final EObject entryRuleSingleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSingleEvent = null;


        try {
            // InternalContractSpec.g:201:52: (iv_ruleSingleEvent= ruleSingleEvent EOF )
            // InternalContractSpec.g:202:2: iv_ruleSingleEvent= ruleSingleEvent EOF
            {
             newCompositeNode(grammarAccess.getSingleEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSingleEvent=ruleSingleEvent();

            state._fsp--;

             current =iv_ruleSingleEvent; 
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
    // $ANTLR end "entryRuleSingleEvent"


    // $ANTLR start "ruleSingleEvent"
    // InternalContractSpec.g:208:1: ruleSingleEvent returns [EObject current=null] : ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )? ) ;
    public final EObject ruleSingleEvent() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        EObject lv_events_0_0 = null;

        EObject lv_interval_3_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:214:2: ( ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:215:2: ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:215:2: ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )? )
            // InternalContractSpec.g:216:3: ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )?
            {
            // InternalContractSpec.g:216:3: ( (lv_events_0_0= ruleEventList ) )
            // InternalContractSpec.g:217:4: (lv_events_0_0= ruleEventList )
            {
            // InternalContractSpec.g:217:4: (lv_events_0_0= ruleEventList )
            // InternalContractSpec.g:218:5: lv_events_0_0= ruleEventList
            {

            					newCompositeNode(grammarAccess.getSingleEventAccess().getEventsEventListParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_4);
            lv_events_0_0=ruleEventList();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSingleEventRule());
            					}
            					set(
            						current,
            						"events",
            						lv_events_0_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventList");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,11,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getSingleEventAccess().getOccursKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getSingleEventAccess().getWithinKeyword_2());
            		
            // InternalContractSpec.g:243:3: ( (lv_interval_3_0= ruleInterval ) )
            // InternalContractSpec.g:244:4: (lv_interval_3_0= ruleInterval )
            {
            // InternalContractSpec.g:244:4: (lv_interval_3_0= ruleInterval )
            // InternalContractSpec.g:245:5: lv_interval_3_0= ruleInterval
            {

            					newCompositeNode(grammarAccess.getSingleEventAccess().getIntervalIntervalParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_7);
            lv_interval_3_0=ruleInterval();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSingleEventRule());
            					}
            					set(
            						current,
            						"interval",
            						lv_interval_3_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Interval");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalContractSpec.g:262:3: (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==13) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalContractSpec.g:263:4: otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) )
                    {
                    otherlv_4=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_4, grammarAccess.getSingleEventAccess().getUsingKeyword_4_0());
                    			
                    otherlv_5=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_5, grammarAccess.getSingleEventAccess().getClockKeyword_4_1());
                    			
                    // InternalContractSpec.g:271:4: ( (otherlv_6= RULE_ID ) )
                    // InternalContractSpec.g:272:5: (otherlv_6= RULE_ID )
                    {
                    // InternalContractSpec.g:272:5: (otherlv_6= RULE_ID )
                    // InternalContractSpec.g:273:6: otherlv_6= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getSingleEventRule());
                    						}
                    					
                    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_6, grammarAccess.getSingleEventAccess().getClockClockDefinitionCrossReference_4_2_0());
                    					

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleSingleEvent"


    // $ANTLR start "entryRuleRepetition"
    // InternalContractSpec.g:289:1: entryRuleRepetition returns [EObject current=null] : iv_ruleRepetition= ruleRepetition EOF ;
    public final EObject entryRuleRepetition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRepetition = null;


        try {
            // InternalContractSpec.g:289:51: (iv_ruleRepetition= ruleRepetition EOF )
            // InternalContractSpec.g:290:2: iv_ruleRepetition= ruleRepetition EOF
            {
             newCompositeNode(grammarAccess.getRepetitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRepetition=ruleRepetition();

            state._fsp--;

             current =iv_ruleRepetition; 
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
    // $ANTLR end "entryRuleRepetition"


    // $ANTLR start "ruleRepetition"
    // InternalContractSpec.g:296:1: ruleRepetition returns [EObject current=null] : ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )? ) ;
    public final EObject ruleRepetition() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        EObject lv_events_0_0 = null;

        EObject lv_interval_3_0 = null;

        EObject lv_repetitionOptions_5_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:302:2: ( ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:303:2: ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:303:2: ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )? )
            // InternalContractSpec.g:304:3: ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )?
            {
            // InternalContractSpec.g:304:3: ( (lv_events_0_0= ruleEventList ) )
            // InternalContractSpec.g:305:4: (lv_events_0_0= ruleEventList )
            {
            // InternalContractSpec.g:305:4: (lv_events_0_0= ruleEventList )
            // InternalContractSpec.g:306:5: lv_events_0_0= ruleEventList
            {

            					newCompositeNode(grammarAccess.getRepetitionAccess().getEventsEventListParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_4);
            lv_events_0_0=ruleEventList();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRepetitionRule());
            					}
            					set(
            						current,
            						"events",
            						lv_events_0_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventList");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,11,FOLLOW_10); 

            			newLeafNode(otherlv_1, grammarAccess.getRepetitionAccess().getOccursKeyword_1());
            		
            otherlv_2=(Token)match(input,15,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getRepetitionAccess().getEveryKeyword_2());
            		
            // InternalContractSpec.g:331:3: ( (lv_interval_3_0= ruleInterval ) )
            // InternalContractSpec.g:332:4: (lv_interval_3_0= ruleInterval )
            {
            // InternalContractSpec.g:332:4: (lv_interval_3_0= ruleInterval )
            // InternalContractSpec.g:333:5: lv_interval_3_0= ruleInterval
            {

            					newCompositeNode(grammarAccess.getRepetitionAccess().getIntervalIntervalParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_11);
            lv_interval_3_0=ruleInterval();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRepetitionRule());
            					}
            					set(
            						current,
            						"interval",
            						lv_interval_3_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Interval");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalContractSpec.g:350:3: (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==16) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalContractSpec.g:351:4: otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) )
                    {
                    otherlv_4=(Token)match(input,16,FOLLOW_12); 

                    				newLeafNode(otherlv_4, grammarAccess.getRepetitionAccess().getWithKeyword_4_0());
                    			
                    // InternalContractSpec.g:355:4: ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) )
                    // InternalContractSpec.g:356:5: (lv_repetitionOptions_5_0= ruleRepetitionOptions )
                    {
                    // InternalContractSpec.g:356:5: (lv_repetitionOptions_5_0= ruleRepetitionOptions )
                    // InternalContractSpec.g:357:6: lv_repetitionOptions_5_0= ruleRepetitionOptions
                    {

                    						newCompositeNode(grammarAccess.getRepetitionAccess().getRepetitionOptionsRepetitionOptionsParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_7);
                    lv_repetitionOptions_5_0=ruleRepetitionOptions();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRepetitionRule());
                    						}
                    						set(
                    							current,
                    							"repetitionOptions",
                    							lv_repetitionOptions_5_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.RepetitionOptions");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalContractSpec.g:375:3: (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==13) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalContractSpec.g:376:4: otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) )
                    {
                    otherlv_6=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_6, grammarAccess.getRepetitionAccess().getUsingKeyword_5_0());
                    			
                    otherlv_7=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_7, grammarAccess.getRepetitionAccess().getClockKeyword_5_1());
                    			
                    // InternalContractSpec.g:384:4: ( (otherlv_8= RULE_ID ) )
                    // InternalContractSpec.g:385:5: (otherlv_8= RULE_ID )
                    {
                    // InternalContractSpec.g:385:5: (otherlv_8= RULE_ID )
                    // InternalContractSpec.g:386:6: otherlv_8= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRepetitionRule());
                    						}
                    					
                    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_8, grammarAccess.getRepetitionAccess().getClockClockDefinitionCrossReference_5_2_0());
                    					

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleRepetition"


    // $ANTLR start "entryRuleRepetitionOptions"
    // InternalContractSpec.g:402:1: entryRuleRepetitionOptions returns [EObject current=null] : iv_ruleRepetitionOptions= ruleRepetitionOptions EOF ;
    public final EObject entryRuleRepetitionOptions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRepetitionOptions = null;


        try {
            // InternalContractSpec.g:402:58: (iv_ruleRepetitionOptions= ruleRepetitionOptions EOF )
            // InternalContractSpec.g:403:2: iv_ruleRepetitionOptions= ruleRepetitionOptions EOF
            {
             newCompositeNode(grammarAccess.getRepetitionOptionsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRepetitionOptions=ruleRepetitionOptions();

            state._fsp--;

             current =iv_ruleRepetitionOptions; 
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
    // $ANTLR end "entryRuleRepetitionOptions"


    // $ANTLR start "ruleRepetitionOptions"
    // InternalContractSpec.g:409:1: ruleRepetitionOptions returns [EObject current=null] : ( ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? ) | ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? ) ) ;
    public final EObject ruleRepetitionOptions() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_jitter_0_0 = null;

        EObject lv_offset_2_0 = null;

        EObject lv_offset_3_0 = null;

        EObject lv_jitter_5_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:415:2: ( ( ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? ) | ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? ) ) )
            // InternalContractSpec.g:416:2: ( ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? ) | ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? ) )
            {
            // InternalContractSpec.g:416:2: ( ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? ) | ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==18) ) {
                alt8=1;
            }
            else if ( (LA8_0==19) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalContractSpec.g:417:3: ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? )
                    {
                    // InternalContractSpec.g:417:3: ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? )
                    // InternalContractSpec.g:418:4: ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )?
                    {
                    // InternalContractSpec.g:418:4: ( (lv_jitter_0_0= ruleJitter ) )
                    // InternalContractSpec.g:419:5: (lv_jitter_0_0= ruleJitter )
                    {
                    // InternalContractSpec.g:419:5: (lv_jitter_0_0= ruleJitter )
                    // InternalContractSpec.g:420:6: lv_jitter_0_0= ruleJitter
                    {

                    						newCompositeNode(grammarAccess.getRepetitionOptionsAccess().getJitterJitterParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_13);
                    lv_jitter_0_0=ruleJitter();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRepetitionOptionsRule());
                    						}
                    						set(
                    							current,
                    							"jitter",
                    							lv_jitter_0_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Jitter");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalContractSpec.g:437:4: (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==17) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // InternalContractSpec.g:438:5: otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) )
                            {
                            otherlv_1=(Token)match(input,17,FOLLOW_12); 

                            					newLeafNode(otherlv_1, grammarAccess.getRepetitionOptionsAccess().getAndKeyword_0_1_0());
                            				
                            // InternalContractSpec.g:442:5: ( (lv_offset_2_0= ruleOffset ) )
                            // InternalContractSpec.g:443:6: (lv_offset_2_0= ruleOffset )
                            {
                            // InternalContractSpec.g:443:6: (lv_offset_2_0= ruleOffset )
                            // InternalContractSpec.g:444:7: lv_offset_2_0= ruleOffset
                            {

                            							newCompositeNode(grammarAccess.getRepetitionOptionsAccess().getOffsetOffsetParserRuleCall_0_1_1_0());
                            						
                            pushFollow(FOLLOW_2);
                            lv_offset_2_0=ruleOffset();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getRepetitionOptionsRule());
                            							}
                            							set(
                            								current,
                            								"offset",
                            								lv_offset_2_0,
                            								"org.eclipse.fordiac.ide.ContractSpec.Offset");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:464:3: ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? )
                    {
                    // InternalContractSpec.g:464:3: ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? )
                    // InternalContractSpec.g:465:4: ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )?
                    {
                    // InternalContractSpec.g:465:4: ( (lv_offset_3_0= ruleOffset ) )
                    // InternalContractSpec.g:466:5: (lv_offset_3_0= ruleOffset )
                    {
                    // InternalContractSpec.g:466:5: (lv_offset_3_0= ruleOffset )
                    // InternalContractSpec.g:467:6: lv_offset_3_0= ruleOffset
                    {

                    						newCompositeNode(grammarAccess.getRepetitionOptionsAccess().getOffsetOffsetParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_13);
                    lv_offset_3_0=ruleOffset();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRepetitionOptionsRule());
                    						}
                    						set(
                    							current,
                    							"offset",
                    							lv_offset_3_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Offset");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalContractSpec.g:484:4: (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==17) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // InternalContractSpec.g:485:5: otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) )
                            {
                            otherlv_4=(Token)match(input,17,FOLLOW_14); 

                            					newLeafNode(otherlv_4, grammarAccess.getRepetitionOptionsAccess().getAndKeyword_1_1_0());
                            				
                            // InternalContractSpec.g:489:5: ( (lv_jitter_5_0= ruleJitter ) )
                            // InternalContractSpec.g:490:6: (lv_jitter_5_0= ruleJitter )
                            {
                            // InternalContractSpec.g:490:6: (lv_jitter_5_0= ruleJitter )
                            // InternalContractSpec.g:491:7: lv_jitter_5_0= ruleJitter
                            {

                            							newCompositeNode(grammarAccess.getRepetitionOptionsAccess().getJitterJitterParserRuleCall_1_1_1_0());
                            						
                            pushFollow(FOLLOW_2);
                            lv_jitter_5_0=ruleJitter();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getRepetitionOptionsRule());
                            							}
                            							set(
                            								current,
                            								"jitter",
                            								lv_jitter_5_0,
                            								"org.eclipse.fordiac.ide.ContractSpec.Jitter");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleRepetitionOptions"


    // $ANTLR start "entryRuleJitter"
    // InternalContractSpec.g:514:1: entryRuleJitter returns [EObject current=null] : iv_ruleJitter= ruleJitter EOF ;
    public final EObject entryRuleJitter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJitter = null;


        try {
            // InternalContractSpec.g:514:47: (iv_ruleJitter= ruleJitter EOF )
            // InternalContractSpec.g:515:2: iv_ruleJitter= ruleJitter EOF
            {
             newCompositeNode(grammarAccess.getJitterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJitter=ruleJitter();

            state._fsp--;

             current =iv_ruleJitter; 
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
    // $ANTLR end "entryRuleJitter"


    // $ANTLR start "ruleJitter"
    // InternalContractSpec.g:521:1: ruleJitter returns [EObject current=null] : (otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) ) ) ;
    public final EObject ruleJitter() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_time_1_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:527:2: ( (otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) ) ) )
            // InternalContractSpec.g:528:2: (otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) ) )
            {
            // InternalContractSpec.g:528:2: (otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) ) )
            // InternalContractSpec.g:529:3: otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) )
            {
            otherlv_0=(Token)match(input,18,FOLLOW_15); 

            			newLeafNode(otherlv_0, grammarAccess.getJitterAccess().getJitterKeyword_0());
            		
            // InternalContractSpec.g:533:3: ( (lv_time_1_0= ruleTimeExpr ) )
            // InternalContractSpec.g:534:4: (lv_time_1_0= ruleTimeExpr )
            {
            // InternalContractSpec.g:534:4: (lv_time_1_0= ruleTimeExpr )
            // InternalContractSpec.g:535:5: lv_time_1_0= ruleTimeExpr
            {

            					newCompositeNode(grammarAccess.getJitterAccess().getTimeTimeExprParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_time_1_0=ruleTimeExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJitterRule());
            					}
            					set(
            						current,
            						"time",
            						lv_time_1_0,
            						"org.eclipse.fordiac.ide.ContractSpec.TimeExpr");
            					afterParserOrEnumRuleCall();
            				

            }


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
    // $ANTLR end "ruleJitter"


    // $ANTLR start "entryRuleOffset"
    // InternalContractSpec.g:556:1: entryRuleOffset returns [EObject current=null] : iv_ruleOffset= ruleOffset EOF ;
    public final EObject entryRuleOffset() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOffset = null;


        try {
            // InternalContractSpec.g:556:47: (iv_ruleOffset= ruleOffset EOF )
            // InternalContractSpec.g:557:2: iv_ruleOffset= ruleOffset EOF
            {
             newCompositeNode(grammarAccess.getOffsetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOffset=ruleOffset();

            state._fsp--;

             current =iv_ruleOffset; 
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
    // $ANTLR end "entryRuleOffset"


    // $ANTLR start "ruleOffset"
    // InternalContractSpec.g:563:1: ruleOffset returns [EObject current=null] : (otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) ) ) ;
    public final EObject ruleOffset() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_interval_1_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:569:2: ( (otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) ) ) )
            // InternalContractSpec.g:570:2: (otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) ) )
            {
            // InternalContractSpec.g:570:2: (otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) ) )
            // InternalContractSpec.g:571:3: otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) )
            {
            otherlv_0=(Token)match(input,19,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getOffsetAccess().getOffsetKeyword_0());
            		
            // InternalContractSpec.g:575:3: ( (lv_interval_1_0= ruleInterval ) )
            // InternalContractSpec.g:576:4: (lv_interval_1_0= ruleInterval )
            {
            // InternalContractSpec.g:576:4: (lv_interval_1_0= ruleInterval )
            // InternalContractSpec.g:577:5: lv_interval_1_0= ruleInterval
            {

            					newCompositeNode(grammarAccess.getOffsetAccess().getIntervalIntervalParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_interval_1_0=ruleInterval();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOffsetRule());
            					}
            					set(
            						current,
            						"interval",
            						lv_interval_1_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Interval");
            					afterParserOrEnumRuleCall();
            				

            }


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
    // $ANTLR end "ruleOffset"


    // $ANTLR start "entryRuleReaction"
    // InternalContractSpec.g:598:1: entryRuleReaction returns [EObject current=null] : iv_ruleReaction= ruleReaction EOF ;
    public final EObject entryRuleReaction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReaction = null;


        try {
            // InternalContractSpec.g:598:49: (iv_ruleReaction= ruleReaction EOF )
            // InternalContractSpec.g:599:2: iv_ruleReaction= ruleReaction EOF
            {
             newCompositeNode(grammarAccess.getReactionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReaction=ruleReaction();

            state._fsp--;

             current =iv_ruleReaction; 
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
    // $ANTLR end "entryRuleReaction"


    // $ANTLR start "ruleReaction"
    // InternalContractSpec.g:605:1: ruleReaction returns [EObject current=null] : (otherlv_0= 'whenever' ( (lv_input_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_output_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) ( ( (lv_once_8_0= 'once' ) ) | ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' ) )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )? ) ;
    public final EObject ruleReaction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_once_8_0=null;
        Token lv_n_9_0=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token lv_outOf_12_0=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        EObject lv_input_1_0 = null;

        EObject lv_output_4_0 = null;

        EObject lv_interval_7_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:611:2: ( (otherlv_0= 'whenever' ( (lv_input_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_output_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) ( ( (lv_once_8_0= 'once' ) ) | ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' ) )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:612:2: (otherlv_0= 'whenever' ( (lv_input_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_output_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) ( ( (lv_once_8_0= 'once' ) ) | ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' ) )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:612:2: (otherlv_0= 'whenever' ( (lv_input_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_output_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) ( ( (lv_once_8_0= 'once' ) ) | ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' ) )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )? )
            // InternalContractSpec.g:613:3: otherlv_0= 'whenever' ( (lv_input_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_output_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) ( ( (lv_once_8_0= 'once' ) ) | ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' ) )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,20,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getReactionAccess().getWheneverKeyword_0());
            		
            // InternalContractSpec.g:617:3: ( (lv_input_1_0= ruleEventExpr ) )
            // InternalContractSpec.g:618:4: (lv_input_1_0= ruleEventExpr )
            {
            // InternalContractSpec.g:618:4: (lv_input_1_0= ruleEventExpr )
            // InternalContractSpec.g:619:5: lv_input_1_0= ruleEventExpr
            {

            					newCompositeNode(grammarAccess.getReactionAccess().getInputEventExprParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_input_1_0=ruleEventExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReactionRule());
            					}
            					set(
            						current,
            						"input",
            						lv_input_1_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventExpr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,11,FOLLOW_17); 

            			newLeafNode(otherlv_2, grammarAccess.getReactionAccess().getOccursKeyword_2());
            		
            otherlv_3=(Token)match(input,21,FOLLOW_16); 

            			newLeafNode(otherlv_3, grammarAccess.getReactionAccess().getThenKeyword_3());
            		
            // InternalContractSpec.g:644:3: ( (lv_output_4_0= ruleEventExpr ) )
            // InternalContractSpec.g:645:4: (lv_output_4_0= ruleEventExpr )
            {
            // InternalContractSpec.g:645:4: (lv_output_4_0= ruleEventExpr )
            // InternalContractSpec.g:646:5: lv_output_4_0= ruleEventExpr
            {

            					newCompositeNode(grammarAccess.getReactionAccess().getOutputEventExprParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_4);
            lv_output_4_0=ruleEventExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReactionRule());
            					}
            					set(
            						current,
            						"output",
            						lv_output_4_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventExpr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,11,FOLLOW_5); 

            			newLeafNode(otherlv_5, grammarAccess.getReactionAccess().getOccursKeyword_5());
            		
            otherlv_6=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getReactionAccess().getWithinKeyword_6());
            		
            // InternalContractSpec.g:671:3: ( (lv_interval_7_0= ruleInterval ) )
            // InternalContractSpec.g:672:4: (lv_interval_7_0= ruleInterval )
            {
            // InternalContractSpec.g:672:4: (lv_interval_7_0= ruleInterval )
            // InternalContractSpec.g:673:5: lv_interval_7_0= ruleInterval
            {

            					newCompositeNode(grammarAccess.getReactionAccess().getIntervalIntervalParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_18);
            lv_interval_7_0=ruleInterval();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReactionRule());
            					}
            					set(
            						current,
            						"interval",
            						lv_interval_7_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Interval");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalContractSpec.g:690:3: ( ( (lv_once_8_0= 'once' ) ) | ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' ) )?
            int alt9=3;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==22) ) {
                alt9=1;
            }
            else if ( (LA9_0==RULE_INT) ) {
                alt9=2;
            }
            switch (alt9) {
                case 1 :
                    // InternalContractSpec.g:691:4: ( (lv_once_8_0= 'once' ) )
                    {
                    // InternalContractSpec.g:691:4: ( (lv_once_8_0= 'once' ) )
                    // InternalContractSpec.g:692:5: (lv_once_8_0= 'once' )
                    {
                    // InternalContractSpec.g:692:5: (lv_once_8_0= 'once' )
                    // InternalContractSpec.g:693:6: lv_once_8_0= 'once'
                    {
                    lv_once_8_0=(Token)match(input,22,FOLLOW_7); 

                    						newLeafNode(lv_once_8_0, grammarAccess.getReactionAccess().getOnceOnceKeyword_8_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getReactionRule());
                    						}
                    						setWithLastConsumed(current, "once", lv_once_8_0 != null, "once");
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:706:4: ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' )
                    {
                    // InternalContractSpec.g:706:4: ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' )
                    // InternalContractSpec.g:707:5: ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times'
                    {
                    // InternalContractSpec.g:707:5: ( (lv_n_9_0= RULE_INT ) )
                    // InternalContractSpec.g:708:6: (lv_n_9_0= RULE_INT )
                    {
                    // InternalContractSpec.g:708:6: (lv_n_9_0= RULE_INT )
                    // InternalContractSpec.g:709:7: lv_n_9_0= RULE_INT
                    {
                    lv_n_9_0=(Token)match(input,RULE_INT,FOLLOW_19); 

                    							newLeafNode(lv_n_9_0, grammarAccess.getReactionAccess().getNINTTerminalRuleCall_8_1_0_0());
                    						

                    							if (current==null) {
                    								current = createModelElement(grammarAccess.getReactionRule());
                    							}
                    							setWithLastConsumed(
                    								current,
                    								"n",
                    								lv_n_9_0,
                    								"org.eclipse.xtext.common.Terminals.INT");
                    						

                    }


                    }

                    otherlv_10=(Token)match(input,23,FOLLOW_20); 

                    					newLeafNode(otherlv_10, grammarAccess.getReactionAccess().getOutKeyword_8_1_1());
                    				
                    otherlv_11=(Token)match(input,24,FOLLOW_15); 

                    					newLeafNode(otherlv_11, grammarAccess.getReactionAccess().getOfKeyword_8_1_2());
                    				
                    // InternalContractSpec.g:733:5: ( (lv_outOf_12_0= RULE_INT ) )
                    // InternalContractSpec.g:734:6: (lv_outOf_12_0= RULE_INT )
                    {
                    // InternalContractSpec.g:734:6: (lv_outOf_12_0= RULE_INT )
                    // InternalContractSpec.g:735:7: lv_outOf_12_0= RULE_INT
                    {
                    lv_outOf_12_0=(Token)match(input,RULE_INT,FOLLOW_21); 

                    							newLeafNode(lv_outOf_12_0, grammarAccess.getReactionAccess().getOutOfINTTerminalRuleCall_8_1_3_0());
                    						

                    							if (current==null) {
                    								current = createModelElement(grammarAccess.getReactionRule());
                    							}
                    							setWithLastConsumed(
                    								current,
                    								"outOf",
                    								lv_outOf_12_0,
                    								"org.eclipse.xtext.common.Terminals.INT");
                    						

                    }


                    }

                    otherlv_13=(Token)match(input,25,FOLLOW_7); 

                    					newLeafNode(otherlv_13, grammarAccess.getReactionAccess().getTimesKeyword_8_1_4());
                    				

                    }


                    }
                    break;

            }

            // InternalContractSpec.g:757:3: (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==13) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalContractSpec.g:758:4: otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) )
                    {
                    otherlv_14=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_14, grammarAccess.getReactionAccess().getUsingKeyword_9_0());
                    			
                    otherlv_15=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_15, grammarAccess.getReactionAccess().getClockKeyword_9_1());
                    			
                    // InternalContractSpec.g:766:4: ( (otherlv_16= RULE_ID ) )
                    // InternalContractSpec.g:767:5: (otherlv_16= RULE_ID )
                    {
                    // InternalContractSpec.g:767:5: (otherlv_16= RULE_ID )
                    // InternalContractSpec.g:768:6: otherlv_16= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getReactionRule());
                    						}
                    					
                    otherlv_16=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_16, grammarAccess.getReactionAccess().getClockClockDefinitionCrossReference_9_2_0());
                    					

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleReaction"


    // $ANTLR start "entryRuleAge"
    // InternalContractSpec.g:784:1: entryRuleAge returns [EObject current=null] : iv_ruleAge= ruleAge EOF ;
    public final EObject entryRuleAge() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAge = null;


        try {
            // InternalContractSpec.g:784:44: (iv_ruleAge= ruleAge EOF )
            // InternalContractSpec.g:785:2: iv_ruleAge= ruleAge EOF
            {
             newCompositeNode(grammarAccess.getAgeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAge=ruleAge();

            state._fsp--;

             current =iv_ruleAge; 
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
    // $ANTLR end "entryRuleAge"


    // $ANTLR start "ruleAge"
    // InternalContractSpec.g:791:1: ruleAge returns [EObject current=null] : (otherlv_0= 'whenever' ( (lv_output_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_input_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) ( ( (lv_once_9_0= 'once' ) ) | ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' ) )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )? ) ;
    public final EObject ruleAge() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_once_9_0=null;
        Token lv_n_10_0=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_outOf_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        EObject lv_output_1_0 = null;

        EObject lv_input_4_0 = null;

        EObject lv_interval_8_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:797:2: ( (otherlv_0= 'whenever' ( (lv_output_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_input_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) ( ( (lv_once_9_0= 'once' ) ) | ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' ) )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:798:2: (otherlv_0= 'whenever' ( (lv_output_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_input_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) ( ( (lv_once_9_0= 'once' ) ) | ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' ) )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:798:2: (otherlv_0= 'whenever' ( (lv_output_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_input_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) ( ( (lv_once_9_0= 'once' ) ) | ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' ) )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )? )
            // InternalContractSpec.g:799:3: otherlv_0= 'whenever' ( (lv_output_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_input_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) ( ( (lv_once_9_0= 'once' ) ) | ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' ) )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,20,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getAgeAccess().getWheneverKeyword_0());
            		
            // InternalContractSpec.g:803:3: ( (lv_output_1_0= ruleEventExpr ) )
            // InternalContractSpec.g:804:4: (lv_output_1_0= ruleEventExpr )
            {
            // InternalContractSpec.g:804:4: (lv_output_1_0= ruleEventExpr )
            // InternalContractSpec.g:805:5: lv_output_1_0= ruleEventExpr
            {

            					newCompositeNode(grammarAccess.getAgeAccess().getOutputEventExprParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_output_1_0=ruleEventExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAgeRule());
            					}
            					set(
            						current,
            						"output",
            						lv_output_1_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventExpr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,11,FOLLOW_17); 

            			newLeafNode(otherlv_2, grammarAccess.getAgeAccess().getOccursKeyword_2());
            		
            otherlv_3=(Token)match(input,21,FOLLOW_16); 

            			newLeafNode(otherlv_3, grammarAccess.getAgeAccess().getThenKeyword_3());
            		
            // InternalContractSpec.g:830:3: ( (lv_input_4_0= ruleEventExpr ) )
            // InternalContractSpec.g:831:4: (lv_input_4_0= ruleEventExpr )
            {
            // InternalContractSpec.g:831:4: (lv_input_4_0= ruleEventExpr )
            // InternalContractSpec.g:832:5: lv_input_4_0= ruleEventExpr
            {

            					newCompositeNode(grammarAccess.getAgeAccess().getInputEventExprParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_22);
            lv_input_4_0=ruleEventExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAgeRule());
            					}
            					set(
            						current,
            						"input",
            						lv_input_4_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventExpr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,26,FOLLOW_23); 

            			newLeafNode(otherlv_5, grammarAccess.getAgeAccess().getHasKeyword_5());
            		
            otherlv_6=(Token)match(input,27,FOLLOW_5); 

            			newLeafNode(otherlv_6, grammarAccess.getAgeAccess().getOccurredKeyword_6());
            		
            otherlv_7=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getAgeAccess().getWithinKeyword_7());
            		
            // InternalContractSpec.g:861:3: ( (lv_interval_8_0= ruleInterval ) )
            // InternalContractSpec.g:862:4: (lv_interval_8_0= ruleInterval )
            {
            // InternalContractSpec.g:862:4: (lv_interval_8_0= ruleInterval )
            // InternalContractSpec.g:863:5: lv_interval_8_0= ruleInterval
            {

            					newCompositeNode(grammarAccess.getAgeAccess().getIntervalIntervalParserRuleCall_8_0());
            				
            pushFollow(FOLLOW_18);
            lv_interval_8_0=ruleInterval();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAgeRule());
            					}
            					set(
            						current,
            						"interval",
            						lv_interval_8_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Interval");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalContractSpec.g:880:3: ( ( (lv_once_9_0= 'once' ) ) | ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' ) )?
            int alt11=3;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==22) ) {
                alt11=1;
            }
            else if ( (LA11_0==RULE_INT) ) {
                alt11=2;
            }
            switch (alt11) {
                case 1 :
                    // InternalContractSpec.g:881:4: ( (lv_once_9_0= 'once' ) )
                    {
                    // InternalContractSpec.g:881:4: ( (lv_once_9_0= 'once' ) )
                    // InternalContractSpec.g:882:5: (lv_once_9_0= 'once' )
                    {
                    // InternalContractSpec.g:882:5: (lv_once_9_0= 'once' )
                    // InternalContractSpec.g:883:6: lv_once_9_0= 'once'
                    {
                    lv_once_9_0=(Token)match(input,22,FOLLOW_7); 

                    						newLeafNode(lv_once_9_0, grammarAccess.getAgeAccess().getOnceOnceKeyword_9_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAgeRule());
                    						}
                    						setWithLastConsumed(current, "once", lv_once_9_0 != null, "once");
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:896:4: ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' )
                    {
                    // InternalContractSpec.g:896:4: ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' )
                    // InternalContractSpec.g:897:5: ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times'
                    {
                    // InternalContractSpec.g:897:5: ( (lv_n_10_0= RULE_INT ) )
                    // InternalContractSpec.g:898:6: (lv_n_10_0= RULE_INT )
                    {
                    // InternalContractSpec.g:898:6: (lv_n_10_0= RULE_INT )
                    // InternalContractSpec.g:899:7: lv_n_10_0= RULE_INT
                    {
                    lv_n_10_0=(Token)match(input,RULE_INT,FOLLOW_19); 

                    							newLeafNode(lv_n_10_0, grammarAccess.getAgeAccess().getNINTTerminalRuleCall_9_1_0_0());
                    						

                    							if (current==null) {
                    								current = createModelElement(grammarAccess.getAgeRule());
                    							}
                    							setWithLastConsumed(
                    								current,
                    								"n",
                    								lv_n_10_0,
                    								"org.eclipse.xtext.common.Terminals.INT");
                    						

                    }


                    }

                    otherlv_11=(Token)match(input,23,FOLLOW_20); 

                    					newLeafNode(otherlv_11, grammarAccess.getAgeAccess().getOutKeyword_9_1_1());
                    				
                    otherlv_12=(Token)match(input,24,FOLLOW_15); 

                    					newLeafNode(otherlv_12, grammarAccess.getAgeAccess().getOfKeyword_9_1_2());
                    				
                    // InternalContractSpec.g:923:5: ( (lv_outOf_13_0= RULE_INT ) )
                    // InternalContractSpec.g:924:6: (lv_outOf_13_0= RULE_INT )
                    {
                    // InternalContractSpec.g:924:6: (lv_outOf_13_0= RULE_INT )
                    // InternalContractSpec.g:925:7: lv_outOf_13_0= RULE_INT
                    {
                    lv_outOf_13_0=(Token)match(input,RULE_INT,FOLLOW_21); 

                    							newLeafNode(lv_outOf_13_0, grammarAccess.getAgeAccess().getOutOfINTTerminalRuleCall_9_1_3_0());
                    						

                    							if (current==null) {
                    								current = createModelElement(grammarAccess.getAgeRule());
                    							}
                    							setWithLastConsumed(
                    								current,
                    								"outOf",
                    								lv_outOf_13_0,
                    								"org.eclipse.xtext.common.Terminals.INT");
                    						

                    }


                    }

                    otherlv_14=(Token)match(input,25,FOLLOW_7); 

                    					newLeafNode(otherlv_14, grammarAccess.getAgeAccess().getTimesKeyword_9_1_4());
                    				

                    }


                    }
                    break;

            }

            // InternalContractSpec.g:947:3: (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==13) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalContractSpec.g:948:4: otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) )
                    {
                    otherlv_15=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_15, grammarAccess.getAgeAccess().getUsingKeyword_10_0());
                    			
                    otherlv_16=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_16, grammarAccess.getAgeAccess().getClockKeyword_10_1());
                    			
                    // InternalContractSpec.g:956:4: ( (otherlv_17= RULE_ID ) )
                    // InternalContractSpec.g:957:5: (otherlv_17= RULE_ID )
                    {
                    // InternalContractSpec.g:957:5: (otherlv_17= RULE_ID )
                    // InternalContractSpec.g:958:6: otherlv_17= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAgeRule());
                    						}
                    					
                    otherlv_17=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_17, grammarAccess.getAgeAccess().getClockClockDefinitionCrossReference_10_2_0());
                    					

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleAge"


    // $ANTLR start "entryRuleCausalReaction"
    // InternalContractSpec.g:974:1: entryRuleCausalReaction returns [EObject current=null] : iv_ruleCausalReaction= ruleCausalReaction EOF ;
    public final EObject entryRuleCausalReaction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCausalReaction = null;


        try {
            // InternalContractSpec.g:974:55: (iv_ruleCausalReaction= ruleCausalReaction EOF )
            // InternalContractSpec.g:975:2: iv_ruleCausalReaction= ruleCausalReaction EOF
            {
             newCompositeNode(grammarAccess.getCausalReactionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCausalReaction=ruleCausalReaction();

            state._fsp--;

             current =iv_ruleCausalReaction; 
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
    // $ANTLR end "entryRuleCausalReaction"


    // $ANTLR start "ruleCausalReaction"
    // InternalContractSpec.g:981:1: ruleCausalReaction returns [EObject current=null] : (otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_input_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_output_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? ) ;
    public final EObject ruleCausalReaction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        EObject lv_input_2_0 = null;

        EObject lv_output_4_0 = null;

        EObject lv_interval_7_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:987:2: ( (otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_input_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_output_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:988:2: (otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_input_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_output_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:988:2: (otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_input_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_output_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? )
            // InternalContractSpec.g:989:3: otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_input_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_output_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,28,FOLLOW_24); 

            			newLeafNode(otherlv_0, grammarAccess.getCausalReactionAccess().getReactionKeyword_0());
            		
            otherlv_1=(Token)match(input,29,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getCausalReactionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalContractSpec.g:997:3: ( (lv_input_2_0= ruleEventSpec ) )
            // InternalContractSpec.g:998:4: (lv_input_2_0= ruleEventSpec )
            {
            // InternalContractSpec.g:998:4: (lv_input_2_0= ruleEventSpec )
            // InternalContractSpec.g:999:5: lv_input_2_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getCausalReactionAccess().getInputEventSpecParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_25);
            lv_input_2_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalReactionRule());
            					}
            					set(
            						current,
            						"input",
            						lv_input_2_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,30,FOLLOW_9); 

            			newLeafNode(otherlv_3, grammarAccess.getCausalReactionAccess().getCommaKeyword_3());
            		
            // InternalContractSpec.g:1020:3: ( (lv_output_4_0= ruleEventSpec ) )
            // InternalContractSpec.g:1021:4: (lv_output_4_0= ruleEventSpec )
            {
            // InternalContractSpec.g:1021:4: (lv_output_4_0= ruleEventSpec )
            // InternalContractSpec.g:1022:5: lv_output_4_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getCausalReactionAccess().getOutputEventSpecParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_26);
            lv_output_4_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalReactionRule());
            					}
            					set(
            						current,
            						"output",
            						lv_output_4_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,31,FOLLOW_5); 

            			newLeafNode(otherlv_5, grammarAccess.getCausalReactionAccess().getRightParenthesisKeyword_5());
            		
            otherlv_6=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getCausalReactionAccess().getWithinKeyword_6());
            		
            // InternalContractSpec.g:1047:3: ( (lv_interval_7_0= ruleInterval ) )
            // InternalContractSpec.g:1048:4: (lv_interval_7_0= ruleInterval )
            {
            // InternalContractSpec.g:1048:4: (lv_interval_7_0= ruleInterval )
            // InternalContractSpec.g:1049:5: lv_interval_7_0= ruleInterval
            {

            					newCompositeNode(grammarAccess.getCausalReactionAccess().getIntervalIntervalParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_7);
            lv_interval_7_0=ruleInterval();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalReactionRule());
            					}
            					set(
            						current,
            						"interval",
            						lv_interval_7_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Interval");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalContractSpec.g:1066:3: (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==13) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalContractSpec.g:1067:4: otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) )
                    {
                    otherlv_8=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_8, grammarAccess.getCausalReactionAccess().getUsingKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_9, grammarAccess.getCausalReactionAccess().getClockKeyword_8_1());
                    			
                    // InternalContractSpec.g:1075:4: ( (otherlv_10= RULE_ID ) )
                    // InternalContractSpec.g:1076:5: (otherlv_10= RULE_ID )
                    {
                    // InternalContractSpec.g:1076:5: (otherlv_10= RULE_ID )
                    // InternalContractSpec.g:1077:6: otherlv_10= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getCausalReactionRule());
                    						}
                    					
                    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_10, grammarAccess.getCausalReactionAccess().getClockClockDefinitionCrossReference_8_2_0());
                    					

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleCausalReaction"


    // $ANTLR start "entryRuleCausalAge"
    // InternalContractSpec.g:1093:1: entryRuleCausalAge returns [EObject current=null] : iv_ruleCausalAge= ruleCausalAge EOF ;
    public final EObject entryRuleCausalAge() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCausalAge = null;


        try {
            // InternalContractSpec.g:1093:50: (iv_ruleCausalAge= ruleCausalAge EOF )
            // InternalContractSpec.g:1094:2: iv_ruleCausalAge= ruleCausalAge EOF
            {
             newCompositeNode(grammarAccess.getCausalAgeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCausalAge=ruleCausalAge();

            state._fsp--;

             current =iv_ruleCausalAge; 
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
    // $ANTLR end "entryRuleCausalAge"


    // $ANTLR start "ruleCausalAge"
    // InternalContractSpec.g:1100:1: ruleCausalAge returns [EObject current=null] : (otherlv_0= 'Age' otherlv_1= '(' ( (lv_output_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_input_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? ) ;
    public final EObject ruleCausalAge() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        EObject lv_output_2_0 = null;

        EObject lv_input_4_0 = null;

        EObject lv_interval_7_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1106:2: ( (otherlv_0= 'Age' otherlv_1= '(' ( (lv_output_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_input_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:1107:2: (otherlv_0= 'Age' otherlv_1= '(' ( (lv_output_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_input_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:1107:2: (otherlv_0= 'Age' otherlv_1= '(' ( (lv_output_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_input_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? )
            // InternalContractSpec.g:1108:3: otherlv_0= 'Age' otherlv_1= '(' ( (lv_output_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_input_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,32,FOLLOW_24); 

            			newLeafNode(otherlv_0, grammarAccess.getCausalAgeAccess().getAgeKeyword_0());
            		
            otherlv_1=(Token)match(input,29,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getCausalAgeAccess().getLeftParenthesisKeyword_1());
            		
            // InternalContractSpec.g:1116:3: ( (lv_output_2_0= ruleEventSpec ) )
            // InternalContractSpec.g:1117:4: (lv_output_2_0= ruleEventSpec )
            {
            // InternalContractSpec.g:1117:4: (lv_output_2_0= ruleEventSpec )
            // InternalContractSpec.g:1118:5: lv_output_2_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getCausalAgeAccess().getOutputEventSpecParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_25);
            lv_output_2_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalAgeRule());
            					}
            					set(
            						current,
            						"output",
            						lv_output_2_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,30,FOLLOW_9); 

            			newLeafNode(otherlv_3, grammarAccess.getCausalAgeAccess().getCommaKeyword_3());
            		
            // InternalContractSpec.g:1139:3: ( (lv_input_4_0= ruleEventSpec ) )
            // InternalContractSpec.g:1140:4: (lv_input_4_0= ruleEventSpec )
            {
            // InternalContractSpec.g:1140:4: (lv_input_4_0= ruleEventSpec )
            // InternalContractSpec.g:1141:5: lv_input_4_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getCausalAgeAccess().getInputEventSpecParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_26);
            lv_input_4_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalAgeRule());
            					}
            					set(
            						current,
            						"input",
            						lv_input_4_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,31,FOLLOW_5); 

            			newLeafNode(otherlv_5, grammarAccess.getCausalAgeAccess().getRightParenthesisKeyword_5());
            		
            otherlv_6=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getCausalAgeAccess().getWithinKeyword_6());
            		
            // InternalContractSpec.g:1166:3: ( (lv_interval_7_0= ruleInterval ) )
            // InternalContractSpec.g:1167:4: (lv_interval_7_0= ruleInterval )
            {
            // InternalContractSpec.g:1167:4: (lv_interval_7_0= ruleInterval )
            // InternalContractSpec.g:1168:5: lv_interval_7_0= ruleInterval
            {

            					newCompositeNode(grammarAccess.getCausalAgeAccess().getIntervalIntervalParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_7);
            lv_interval_7_0=ruleInterval();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalAgeRule());
            					}
            					set(
            						current,
            						"interval",
            						lv_interval_7_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Interval");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalContractSpec.g:1185:3: (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==13) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalContractSpec.g:1186:4: otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) )
                    {
                    otherlv_8=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_8, grammarAccess.getCausalAgeAccess().getUsingKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_9, grammarAccess.getCausalAgeAccess().getClockKeyword_8_1());
                    			
                    // InternalContractSpec.g:1194:4: ( (otherlv_10= RULE_ID ) )
                    // InternalContractSpec.g:1195:5: (otherlv_10= RULE_ID )
                    {
                    // InternalContractSpec.g:1195:5: (otherlv_10= RULE_ID )
                    // InternalContractSpec.g:1196:6: otherlv_10= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getCausalAgeRule());
                    						}
                    					
                    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_10, grammarAccess.getCausalAgeAccess().getClockClockDefinitionCrossReference_8_2_0());
                    					

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleCausalAge"


    // $ANTLR start "entryRuleEventExpr"
    // InternalContractSpec.g:1212:1: entryRuleEventExpr returns [EObject current=null] : iv_ruleEventExpr= ruleEventExpr EOF ;
    public final EObject entryRuleEventExpr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventExpr = null;


        try {
            // InternalContractSpec.g:1212:50: (iv_ruleEventExpr= ruleEventExpr EOF )
            // InternalContractSpec.g:1213:2: iv_ruleEventExpr= ruleEventExpr EOF
            {
             newCompositeNode(grammarAccess.getEventExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEventExpr=ruleEventExpr();

            state._fsp--;

             current =iv_ruleEventExpr; 
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
    // $ANTLR end "entryRuleEventExpr"


    // $ANTLR start "ruleEventExpr"
    // InternalContractSpec.g:1219:1: ruleEventExpr returns [EObject current=null] : ( ( (lv_event_0_0= ruleEventSpec ) ) | ( ( (lv_sequence_1_0= '(' ) ) ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' ) | (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' ) ) ;
    public final EObject ruleEventExpr() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_1_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_event_0_0 = null;

        EObject lv_events_2_0 = null;

        EObject lv_events_5_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1225:2: ( ( ( (lv_event_0_0= ruleEventSpec ) ) | ( ( (lv_sequence_1_0= '(' ) ) ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' ) | (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' ) ) )
            // InternalContractSpec.g:1226:2: ( ( (lv_event_0_0= ruleEventSpec ) ) | ( ( (lv_sequence_1_0= '(' ) ) ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' ) | (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' ) )
            {
            // InternalContractSpec.g:1226:2: ( ( (lv_event_0_0= ruleEventSpec ) ) | ( ( (lv_sequence_1_0= '(' ) ) ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' ) | (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' ) )
            int alt15=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt15=1;
                }
                break;
            case 29:
                {
                alt15=2;
                }
                break;
            case 33:
                {
                alt15=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalContractSpec.g:1227:3: ( (lv_event_0_0= ruleEventSpec ) )
                    {
                    // InternalContractSpec.g:1227:3: ( (lv_event_0_0= ruleEventSpec ) )
                    // InternalContractSpec.g:1228:4: (lv_event_0_0= ruleEventSpec )
                    {
                    // InternalContractSpec.g:1228:4: (lv_event_0_0= ruleEventSpec )
                    // InternalContractSpec.g:1229:5: lv_event_0_0= ruleEventSpec
                    {

                    					newCompositeNode(grammarAccess.getEventExprAccess().getEventEventSpecParserRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_event_0_0=ruleEventSpec();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getEventExprRule());
                    					}
                    					set(
                    						current,
                    						"event",
                    						lv_event_0_0,
                    						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:1247:3: ( ( (lv_sequence_1_0= '(' ) ) ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' )
                    {
                    // InternalContractSpec.g:1247:3: ( ( (lv_sequence_1_0= '(' ) ) ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' )
                    // InternalContractSpec.g:1248:4: ( (lv_sequence_1_0= '(' ) ) ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')'
                    {
                    // InternalContractSpec.g:1248:4: ( (lv_sequence_1_0= '(' ) )
                    // InternalContractSpec.g:1249:5: (lv_sequence_1_0= '(' )
                    {
                    // InternalContractSpec.g:1249:5: (lv_sequence_1_0= '(' )
                    // InternalContractSpec.g:1250:6: lv_sequence_1_0= '('
                    {
                    lv_sequence_1_0=(Token)match(input,29,FOLLOW_9); 

                    						newLeafNode(lv_sequence_1_0, grammarAccess.getEventExprAccess().getSequenceLeftParenthesisKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getEventExprRule());
                    						}
                    						setWithLastConsumed(current, "sequence", lv_sequence_1_0 != null, "(");
                    					

                    }


                    }

                    // InternalContractSpec.g:1262:4: ( (lv_events_2_0= ruleEventList ) )
                    // InternalContractSpec.g:1263:5: (lv_events_2_0= ruleEventList )
                    {
                    // InternalContractSpec.g:1263:5: (lv_events_2_0= ruleEventList )
                    // InternalContractSpec.g:1264:6: lv_events_2_0= ruleEventList
                    {

                    						newCompositeNode(grammarAccess.getEventExprAccess().getEventsEventListParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_events_2_0=ruleEventList();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getEventExprRule());
                    						}
                    						set(
                    							current,
                    							"events",
                    							lv_events_2_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.EventList");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,31,FOLLOW_2); 

                    				newLeafNode(otherlv_3, grammarAccess.getEventExprAccess().getRightParenthesisKeyword_1_2());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalContractSpec.g:1287:3: (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' )
                    {
                    // InternalContractSpec.g:1287:3: (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' )
                    // InternalContractSpec.g:1288:4: otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}'
                    {
                    otherlv_4=(Token)match(input,33,FOLLOW_9); 

                    				newLeafNode(otherlv_4, grammarAccess.getEventExprAccess().getLeftCurlyBracketKeyword_2_0());
                    			
                    // InternalContractSpec.g:1292:4: ( (lv_events_5_0= ruleEventList ) )
                    // InternalContractSpec.g:1293:5: (lv_events_5_0= ruleEventList )
                    {
                    // InternalContractSpec.g:1293:5: (lv_events_5_0= ruleEventList )
                    // InternalContractSpec.g:1294:6: lv_events_5_0= ruleEventList
                    {

                    						newCompositeNode(grammarAccess.getEventExprAccess().getEventsEventListParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_events_5_0=ruleEventList();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getEventExprRule());
                    						}
                    						set(
                    							current,
                    							"events",
                    							lv_events_5_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.EventList");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_6=(Token)match(input,34,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getEventExprAccess().getRightCurlyBracketKeyword_2_2());
                    			

                    }


                    }
                    break;

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
    // $ANTLR end "ruleEventExpr"


    // $ANTLR start "entryRuleEventList"
    // InternalContractSpec.g:1320:1: entryRuleEventList returns [EObject current=null] : iv_ruleEventList= ruleEventList EOF ;
    public final EObject entryRuleEventList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventList = null;


        try {
            // InternalContractSpec.g:1320:50: (iv_ruleEventList= ruleEventList EOF )
            // InternalContractSpec.g:1321:2: iv_ruleEventList= ruleEventList EOF
            {
             newCompositeNode(grammarAccess.getEventListRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEventList=ruleEventList();

            state._fsp--;

             current =iv_ruleEventList; 
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
    // $ANTLR end "entryRuleEventList"


    // $ANTLR start "ruleEventList"
    // InternalContractSpec.g:1327:1: ruleEventList returns [EObject current=null] : ( ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )* ) ;
    public final EObject ruleEventList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_events_0_0 = null;

        EObject lv_events_2_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1333:2: ( ( ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )* ) )
            // InternalContractSpec.g:1334:2: ( ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )* )
            {
            // InternalContractSpec.g:1334:2: ( ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )* )
            // InternalContractSpec.g:1335:3: ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )*
            {
            // InternalContractSpec.g:1335:3: ( (lv_events_0_0= ruleEventSpec ) )
            // InternalContractSpec.g:1336:4: (lv_events_0_0= ruleEventSpec )
            {
            // InternalContractSpec.g:1336:4: (lv_events_0_0= ruleEventSpec )
            // InternalContractSpec.g:1337:5: lv_events_0_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getEventListAccess().getEventsEventSpecParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_28);
            lv_events_0_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getEventListRule());
            					}
            					add(
            						current,
            						"events",
            						lv_events_0_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalContractSpec.g:1354:3: (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==30) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalContractSpec.g:1355:4: otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) )
            	    {
            	    otherlv_1=(Token)match(input,30,FOLLOW_9); 

            	    				newLeafNode(otherlv_1, grammarAccess.getEventListAccess().getCommaKeyword_1_0());
            	    			
            	    // InternalContractSpec.g:1359:4: ( (lv_events_2_0= ruleEventSpec ) )
            	    // InternalContractSpec.g:1360:5: (lv_events_2_0= ruleEventSpec )
            	    {
            	    // InternalContractSpec.g:1360:5: (lv_events_2_0= ruleEventSpec )
            	    // InternalContractSpec.g:1361:6: lv_events_2_0= ruleEventSpec
            	    {

            	    						newCompositeNode(grammarAccess.getEventListAccess().getEventsEventSpecParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_28);
            	    lv_events_2_0=ruleEventSpec();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getEventListRule());
            	    						}
            	    						add(
            	    							current,
            	    							"events",
            	    							lv_events_2_0,
            	    							"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


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
    // $ANTLR end "ruleEventList"


    // $ANTLR start "entryRuleEventSpec"
    // InternalContractSpec.g:1383:1: entryRuleEventSpec returns [EObject current=null] : iv_ruleEventSpec= ruleEventSpec EOF ;
    public final EObject entryRuleEventSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventSpec = null;


        try {
            // InternalContractSpec.g:1383:50: (iv_ruleEventSpec= ruleEventSpec EOF )
            // InternalContractSpec.g:1384:2: iv_ruleEventSpec= ruleEventSpec EOF
            {
             newCompositeNode(grammarAccess.getEventSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEventSpec=ruleEventSpec();

            state._fsp--;

             current =iv_ruleEventSpec; 
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
    // $ANTLR end "entryRuleEventSpec"


    // $ANTLR start "ruleEventSpec"
    // InternalContractSpec.g:1390:1: ruleEventSpec returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )? ) ;
    public final EObject ruleEventSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_eventValue_2_0=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1396:2: ( ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:1397:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:1397:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )? )
            // InternalContractSpec.g:1398:3: ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )?
            {
            // InternalContractSpec.g:1398:3: ( (otherlv_0= RULE_ID ) )
            // InternalContractSpec.g:1399:4: (otherlv_0= RULE_ID )
            {
            // InternalContractSpec.g:1399:4: (otherlv_0= RULE_ID )
            // InternalContractSpec.g:1400:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEventSpecRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(otherlv_0, grammarAccess.getEventSpecAccess().getPortPortCrossReference_0_0());
            				

            }


            }

            // InternalContractSpec.g:1411:3: (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==35) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalContractSpec.g:1412:4: otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) )
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getEventSpecAccess().getFullStopKeyword_1_0());
                    			
                    // InternalContractSpec.g:1416:4: ( (lv_eventValue_2_0= RULE_ID ) )
                    // InternalContractSpec.g:1417:5: (lv_eventValue_2_0= RULE_ID )
                    {
                    // InternalContractSpec.g:1417:5: (lv_eventValue_2_0= RULE_ID )
                    // InternalContractSpec.g:1418:6: lv_eventValue_2_0= RULE_ID
                    {
                    lv_eventValue_2_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(lv_eventValue_2_0, grammarAccess.getEventSpecAccess().getEventValueIDTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getEventSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"eventValue",
                    							lv_eventValue_2_0,
                    							"org.eclipse.xtext.common.Terminals.ID");
                    					

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleEventSpec"


    // $ANTLR start "entryRuleInterval"
    // InternalContractSpec.g:1439:1: entryRuleInterval returns [EObject current=null] : iv_ruleInterval= ruleInterval EOF ;
    public final EObject entryRuleInterval() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInterval = null;


        try {
            // InternalContractSpec.g:1439:49: (iv_ruleInterval= ruleInterval EOF )
            // InternalContractSpec.g:1440:2: iv_ruleInterval= ruleInterval EOF
            {
             newCompositeNode(grammarAccess.getIntervalRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInterval=ruleInterval();

            state._fsp--;

             current =iv_ruleInterval; 
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
    // $ANTLR end "entryRuleInterval"


    // $ANTLR start "ruleInterval"
    // InternalContractSpec.g:1446:1: ruleInterval returns [EObject current=null] : ( ( (lv_time_0_0= ruleTimeExpr ) ) | ( ( (lv_lBound_1_0= ruleBoundary ) ) ( (lv_lbValue_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_ubValue_4_0= ruleValue ) ) ( (lv_uBound_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) ) ) ;
    public final EObject ruleInterval() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        EObject lv_time_0_0 = null;

        AntlrDatatypeRuleToken lv_lBound_1_0 = null;

        AntlrDatatypeRuleToken lv_lbValue_2_0 = null;

        AntlrDatatypeRuleToken lv_ubValue_4_0 = null;

        AntlrDatatypeRuleToken lv_uBound_5_0 = null;

        Enumerator lv_unit_6_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1452:2: ( ( ( (lv_time_0_0= ruleTimeExpr ) ) | ( ( (lv_lBound_1_0= ruleBoundary ) ) ( (lv_lbValue_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_ubValue_4_0= ruleValue ) ) ( (lv_uBound_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) ) ) )
            // InternalContractSpec.g:1453:2: ( ( (lv_time_0_0= ruleTimeExpr ) ) | ( ( (lv_lBound_1_0= ruleBoundary ) ) ( (lv_lbValue_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_ubValue_4_0= ruleValue ) ) ( (lv_uBound_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) ) )
            {
            // InternalContractSpec.g:1453:2: ( ( (lv_time_0_0= ruleTimeExpr ) ) | ( ( (lv_lBound_1_0= ruleBoundary ) ) ( (lv_lbValue_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_ubValue_4_0= ruleValue ) ) ( (lv_uBound_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_INT) ) {
                alt18=1;
            }
            else if ( ((LA18_0>=36 && LA18_0<=37)) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalContractSpec.g:1454:3: ( (lv_time_0_0= ruleTimeExpr ) )
                    {
                    // InternalContractSpec.g:1454:3: ( (lv_time_0_0= ruleTimeExpr ) )
                    // InternalContractSpec.g:1455:4: (lv_time_0_0= ruleTimeExpr )
                    {
                    // InternalContractSpec.g:1455:4: (lv_time_0_0= ruleTimeExpr )
                    // InternalContractSpec.g:1456:5: lv_time_0_0= ruleTimeExpr
                    {

                    					newCompositeNode(grammarAccess.getIntervalAccess().getTimeTimeExprParserRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_time_0_0=ruleTimeExpr();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getIntervalRule());
                    					}
                    					set(
                    						current,
                    						"time",
                    						lv_time_0_0,
                    						"org.eclipse.fordiac.ide.ContractSpec.TimeExpr");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:1474:3: ( ( (lv_lBound_1_0= ruleBoundary ) ) ( (lv_lbValue_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_ubValue_4_0= ruleValue ) ) ( (lv_uBound_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) )
                    {
                    // InternalContractSpec.g:1474:3: ( ( (lv_lBound_1_0= ruleBoundary ) ) ( (lv_lbValue_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_ubValue_4_0= ruleValue ) ) ( (lv_uBound_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) )
                    // InternalContractSpec.g:1475:4: ( (lv_lBound_1_0= ruleBoundary ) ) ( (lv_lbValue_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_ubValue_4_0= ruleValue ) ) ( (lv_uBound_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) )
                    {
                    // InternalContractSpec.g:1475:4: ( (lv_lBound_1_0= ruleBoundary ) )
                    // InternalContractSpec.g:1476:5: (lv_lBound_1_0= ruleBoundary )
                    {
                    // InternalContractSpec.g:1476:5: (lv_lBound_1_0= ruleBoundary )
                    // InternalContractSpec.g:1477:6: lv_lBound_1_0= ruleBoundary
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getLBoundBoundaryParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_15);
                    lv_lBound_1_0=ruleBoundary();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"lBound",
                    							lv_lBound_1_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Boundary");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalContractSpec.g:1494:4: ( (lv_lbValue_2_0= ruleValue ) )
                    // InternalContractSpec.g:1495:5: (lv_lbValue_2_0= ruleValue )
                    {
                    // InternalContractSpec.g:1495:5: (lv_lbValue_2_0= ruleValue )
                    // InternalContractSpec.g:1496:6: lv_lbValue_2_0= ruleValue
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getLbValueValueParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_25);
                    lv_lbValue_2_0=ruleValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"lbValue",
                    							lv_lbValue_2_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,30,FOLLOW_15); 

                    				newLeafNode(otherlv_3, grammarAccess.getIntervalAccess().getCommaKeyword_1_2());
                    			
                    // InternalContractSpec.g:1517:4: ( (lv_ubValue_4_0= ruleValue ) )
                    // InternalContractSpec.g:1518:5: (lv_ubValue_4_0= ruleValue )
                    {
                    // InternalContractSpec.g:1518:5: (lv_ubValue_4_0= ruleValue )
                    // InternalContractSpec.g:1519:6: lv_ubValue_4_0= ruleValue
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getUbValueValueParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_6);
                    lv_ubValue_4_0=ruleValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"ubValue",
                    							lv_ubValue_4_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalContractSpec.g:1536:4: ( (lv_uBound_5_0= ruleBoundary ) )
                    // InternalContractSpec.g:1537:5: (lv_uBound_5_0= ruleBoundary )
                    {
                    // InternalContractSpec.g:1537:5: (lv_uBound_5_0= ruleBoundary )
                    // InternalContractSpec.g:1538:6: lv_uBound_5_0= ruleBoundary
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getUBoundBoundaryParserRuleCall_1_4_0());
                    					
                    pushFollow(FOLLOW_30);
                    lv_uBound_5_0=ruleBoundary();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"uBound",
                    							lv_uBound_5_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Boundary");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalContractSpec.g:1555:4: ( (lv_unit_6_0= ruleUnit ) )
                    // InternalContractSpec.g:1556:5: (lv_unit_6_0= ruleUnit )
                    {
                    // InternalContractSpec.g:1556:5: (lv_unit_6_0= ruleUnit )
                    // InternalContractSpec.g:1557:6: lv_unit_6_0= ruleUnit
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getUnitUnitEnumRuleCall_1_5_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_unit_6_0=ruleUnit();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"unit",
                    							lv_unit_6_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Unit");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleInterval"


    // $ANTLR start "entryRuleTimeExpr"
    // InternalContractSpec.g:1579:1: entryRuleTimeExpr returns [EObject current=null] : iv_ruleTimeExpr= ruleTimeExpr EOF ;
    public final EObject entryRuleTimeExpr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeExpr = null;


        try {
            // InternalContractSpec.g:1579:49: (iv_ruleTimeExpr= ruleTimeExpr EOF )
            // InternalContractSpec.g:1580:2: iv_ruleTimeExpr= ruleTimeExpr EOF
            {
             newCompositeNode(grammarAccess.getTimeExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTimeExpr=ruleTimeExpr();

            state._fsp--;

             current =iv_ruleTimeExpr; 
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
    // $ANTLR end "entryRuleTimeExpr"


    // $ANTLR start "ruleTimeExpr"
    // InternalContractSpec.g:1586:1: ruleTimeExpr returns [EObject current=null] : ( ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) ) ) ;
    public final EObject ruleTimeExpr() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;

        Enumerator lv_unit_1_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1592:2: ( ( ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) ) ) )
            // InternalContractSpec.g:1593:2: ( ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) ) )
            {
            // InternalContractSpec.g:1593:2: ( ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) ) )
            // InternalContractSpec.g:1594:3: ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) )
            {
            // InternalContractSpec.g:1594:3: ( (lv_value_0_0= ruleValue ) )
            // InternalContractSpec.g:1595:4: (lv_value_0_0= ruleValue )
            {
            // InternalContractSpec.g:1595:4: (lv_value_0_0= ruleValue )
            // InternalContractSpec.g:1596:5: lv_value_0_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getTimeExprAccess().getValueValueParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_30);
            lv_value_0_0=ruleValue();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTimeExprRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_0_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalContractSpec.g:1613:3: ( (lv_unit_1_0= ruleUnit ) )
            // InternalContractSpec.g:1614:4: (lv_unit_1_0= ruleUnit )
            {
            // InternalContractSpec.g:1614:4: (lv_unit_1_0= ruleUnit )
            // InternalContractSpec.g:1615:5: lv_unit_1_0= ruleUnit
            {

            					newCompositeNode(grammarAccess.getTimeExprAccess().getUnitUnitEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_unit_1_0=ruleUnit();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTimeExprRule());
            					}
            					set(
            						current,
            						"unit",
            						lv_unit_1_0,
            						"org.eclipse.fordiac.ide.ContractSpec.Unit");
            					afterParserOrEnumRuleCall();
            				

            }


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
    // $ANTLR end "ruleTimeExpr"


    // $ANTLR start "entryRuleBoundary"
    // InternalContractSpec.g:1636:1: entryRuleBoundary returns [String current=null] : iv_ruleBoundary= ruleBoundary EOF ;
    public final String entryRuleBoundary() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoundary = null;


        try {
            // InternalContractSpec.g:1636:48: (iv_ruleBoundary= ruleBoundary EOF )
            // InternalContractSpec.g:1637:2: iv_ruleBoundary= ruleBoundary EOF
            {
             newCompositeNode(grammarAccess.getBoundaryRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBoundary=ruleBoundary();

            state._fsp--;

             current =iv_ruleBoundary.getText(); 
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
    // $ANTLR end "entryRuleBoundary"


    // $ANTLR start "ruleBoundary"
    // InternalContractSpec.g:1643:1: ruleBoundary returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' | kw= ']' ) ;
    public final AntlrDatatypeRuleToken ruleBoundary() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1649:2: ( (kw= '[' | kw= ']' ) )
            // InternalContractSpec.g:1650:2: (kw= '[' | kw= ']' )
            {
            // InternalContractSpec.g:1650:2: (kw= '[' | kw= ']' )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==36) ) {
                alt19=1;
            }
            else if ( (LA19_0==37) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // InternalContractSpec.g:1651:3: kw= '['
                    {
                    kw=(Token)match(input,36,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoundaryAccess().getLeftSquareBracketKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:1657:3: kw= ']'
                    {
                    kw=(Token)match(input,37,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoundaryAccess().getRightSquareBracketKeyword_1());
                    		

                    }
                    break;

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
    // $ANTLR end "ruleBoundary"


    // $ANTLR start "entryRuleValue"
    // InternalContractSpec.g:1666:1: entryRuleValue returns [String current=null] : iv_ruleValue= ruleValue EOF ;
    public final String entryRuleValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValue = null;


        try {
            // InternalContractSpec.g:1666:45: (iv_ruleValue= ruleValue EOF )
            // InternalContractSpec.g:1667:2: iv_ruleValue= ruleValue EOF
            {
             newCompositeNode(grammarAccess.getValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleValue=ruleValue();

            state._fsp--;

             current =iv_ruleValue.getText(); 
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
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // InternalContractSpec.g:1673:1: ruleValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1679:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )? ) )
            // InternalContractSpec.g:1680:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )? )
            {
            // InternalContractSpec.g:1680:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )? )
            // InternalContractSpec.g:1681:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_29); 

            			current.merge(this_INT_0);
            		

            			newLeafNode(this_INT_0, grammarAccess.getValueAccess().getINTTerminalRuleCall_0());
            		
            // InternalContractSpec.g:1688:3: (kw= '.' this_INT_2= RULE_INT )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==35) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalContractSpec.g:1689:4: kw= '.' this_INT_2= RULE_INT
                    {
                    kw=(Token)match(input,35,FOLLOW_15); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getValueAccess().getFullStopKeyword_1_0());
                    			
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); 

                    				current.merge(this_INT_2);
                    			

                    				newLeafNode(this_INT_2, grammarAccess.getValueAccess().getINTTerminalRuleCall_1_1());
                    			

                    }
                    break;

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
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleCausalFuncDecl"
    // InternalContractSpec.g:1706:1: entryRuleCausalFuncDecl returns [EObject current=null] : iv_ruleCausalFuncDecl= ruleCausalFuncDecl EOF ;
    public final EObject entryRuleCausalFuncDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCausalFuncDecl = null;


        try {
            // InternalContractSpec.g:1706:55: (iv_ruleCausalFuncDecl= ruleCausalFuncDecl EOF )
            // InternalContractSpec.g:1707:2: iv_ruleCausalFuncDecl= ruleCausalFuncDecl EOF
            {
             newCompositeNode(grammarAccess.getCausalFuncDeclRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCausalFuncDecl=ruleCausalFuncDecl();

            state._fsp--;

             current =iv_ruleCausalFuncDecl; 
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
    // $ANTLR end "entryRuleCausalFuncDecl"


    // $ANTLR start "ruleCausalFuncDecl"
    // InternalContractSpec.g:1713:1: ruleCausalFuncDecl returns [EObject current=null] : ( ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) ) ) ;
    public final EObject ruleCausalFuncDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Enumerator lv_funcName_0_0 = null;

        Enumerator lv_relation_7_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1719:2: ( ( ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) ) ) )
            // InternalContractSpec.g:1720:2: ( ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) ) )
            {
            // InternalContractSpec.g:1720:2: ( ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) ) )
            // InternalContractSpec.g:1721:3: ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) )
            {
            // InternalContractSpec.g:1721:3: ( (lv_funcName_0_0= ruleCausalFuncName ) )
            // InternalContractSpec.g:1722:4: (lv_funcName_0_0= ruleCausalFuncName )
            {
            // InternalContractSpec.g:1722:4: (lv_funcName_0_0= ruleCausalFuncName )
            // InternalContractSpec.g:1723:5: lv_funcName_0_0= ruleCausalFuncName
            {

            					newCompositeNode(grammarAccess.getCausalFuncDeclAccess().getFuncNameCausalFuncNameEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_24);
            lv_funcName_0_0=ruleCausalFuncName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalFuncDeclRule());
            					}
            					set(
            						current,
            						"funcName",
            						lv_funcName_0_0,
            						"org.eclipse.fordiac.ide.ContractSpec.CausalFuncName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,29,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getCausalFuncDeclAccess().getLeftParenthesisKeyword_1());
            		
            // InternalContractSpec.g:1744:3: ( (otherlv_2= RULE_ID ) )
            // InternalContractSpec.g:1745:4: (otherlv_2= RULE_ID )
            {
            // InternalContractSpec.g:1745:4: (otherlv_2= RULE_ID )
            // InternalContractSpec.g:1746:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCausalFuncDeclRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_25); 

            					newLeafNode(otherlv_2, grammarAccess.getCausalFuncDeclAccess().getPort1PortCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,30,FOLLOW_9); 

            			newLeafNode(otherlv_3, grammarAccess.getCausalFuncDeclAccess().getCommaKeyword_3());
            		
            // InternalContractSpec.g:1761:3: ( (otherlv_4= RULE_ID ) )
            // InternalContractSpec.g:1762:4: (otherlv_4= RULE_ID )
            {
            // InternalContractSpec.g:1762:4: (otherlv_4= RULE_ID )
            // InternalContractSpec.g:1763:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCausalFuncDeclRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(otherlv_4, grammarAccess.getCausalFuncDeclAccess().getPort2PortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,31,FOLLOW_31); 

            			newLeafNode(otherlv_5, grammarAccess.getCausalFuncDeclAccess().getRightParenthesisKeyword_5());
            		
            otherlv_6=(Token)match(input,38,FOLLOW_32); 

            			newLeafNode(otherlv_6, grammarAccess.getCausalFuncDeclAccess().getColonEqualsSignKeyword_6());
            		
            // InternalContractSpec.g:1782:3: ( (lv_relation_7_0= ruleCausalRelation ) )
            // InternalContractSpec.g:1783:4: (lv_relation_7_0= ruleCausalRelation )
            {
            // InternalContractSpec.g:1783:4: (lv_relation_7_0= ruleCausalRelation )
            // InternalContractSpec.g:1784:5: lv_relation_7_0= ruleCausalRelation
            {

            					newCompositeNode(grammarAccess.getCausalFuncDeclAccess().getRelationCausalRelationEnumRuleCall_7_0());
            				
            pushFollow(FOLLOW_2);
            lv_relation_7_0=ruleCausalRelation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalFuncDeclRule());
            					}
            					set(
            						current,
            						"relation",
            						lv_relation_7_0,
            						"org.eclipse.fordiac.ide.ContractSpec.CausalRelation");
            					afterParserOrEnumRuleCall();
            				

            }


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
    // $ANTLR end "ruleCausalFuncDecl"


    // $ANTLR start "entryRuleClockDefinition"
    // InternalContractSpec.g:1805:1: entryRuleClockDefinition returns [EObject current=null] : iv_ruleClockDefinition= ruleClockDefinition EOF ;
    public final EObject entryRuleClockDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClockDefinition = null;


        try {
            // InternalContractSpec.g:1805:56: (iv_ruleClockDefinition= ruleClockDefinition EOF )
            // InternalContractSpec.g:1806:2: iv_ruleClockDefinition= ruleClockDefinition EOF
            {
             newCompositeNode(grammarAccess.getClockDefinitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleClockDefinition=ruleClockDefinition();

            state._fsp--;

             current =iv_ruleClockDefinition; 
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
    // $ANTLR end "entryRuleClockDefinition"


    // $ANTLR start "ruleClockDefinition"
    // InternalContractSpec.g:1812:1: ruleClockDefinition returns [EObject current=null] : (otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )? ) ;
    public final EObject ruleClockDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_resolution_4_0 = null;

        EObject lv_skew_6_0 = null;

        EObject lv_drift_8_0 = null;

        EObject lv_maxdiff_10_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1818:2: ( (otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )? ) )
            // InternalContractSpec.g:1819:2: (otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )? )
            {
            // InternalContractSpec.g:1819:2: (otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )? )
            // InternalContractSpec.g:1820:3: otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )?
            {
            otherlv_0=(Token)match(input,39,FOLLOW_9); 

            			newLeafNode(otherlv_0, grammarAccess.getClockDefinitionAccess().getClockKeyword_0());
            		
            // InternalContractSpec.g:1824:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalContractSpec.g:1825:4: (lv_name_1_0= RULE_ID )
            {
            // InternalContractSpec.g:1825:4: (lv_name_1_0= RULE_ID )
            // InternalContractSpec.g:1826:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_22); 

            					newLeafNode(lv_name_1_0, grammarAccess.getClockDefinitionAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getClockDefinitionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,26,FOLLOW_33); 

            			newLeafNode(otherlv_2, grammarAccess.getClockDefinitionAccess().getHasKeyword_2());
            		
            // InternalContractSpec.g:1846:3: (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==40) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalContractSpec.g:1847:4: otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) )
                    {
                    otherlv_3=(Token)match(input,40,FOLLOW_15); 

                    				newLeafNode(otherlv_3, grammarAccess.getClockDefinitionAccess().getResolutionKeyword_3_0());
                    			
                    // InternalContractSpec.g:1851:4: ( (lv_resolution_4_0= ruleTimeExpr ) )
                    // InternalContractSpec.g:1852:5: (lv_resolution_4_0= ruleTimeExpr )
                    {
                    // InternalContractSpec.g:1852:5: (lv_resolution_4_0= ruleTimeExpr )
                    // InternalContractSpec.g:1853:6: lv_resolution_4_0= ruleTimeExpr
                    {

                    						newCompositeNode(grammarAccess.getClockDefinitionAccess().getResolutionTimeExprParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_34);
                    lv_resolution_4_0=ruleTimeExpr();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getClockDefinitionRule());
                    						}
                    						set(
                    							current,
                    							"resolution",
                    							lv_resolution_4_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.TimeExpr");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalContractSpec.g:1871:3: (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==41) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalContractSpec.g:1872:4: otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) )
                    {
                    otherlv_5=(Token)match(input,41,FOLLOW_15); 

                    				newLeafNode(otherlv_5, grammarAccess.getClockDefinitionAccess().getSkewKeyword_4_0());
                    			
                    // InternalContractSpec.g:1876:4: ( (lv_skew_6_0= ruleTimeExpr ) )
                    // InternalContractSpec.g:1877:5: (lv_skew_6_0= ruleTimeExpr )
                    {
                    // InternalContractSpec.g:1877:5: (lv_skew_6_0= ruleTimeExpr )
                    // InternalContractSpec.g:1878:6: lv_skew_6_0= ruleTimeExpr
                    {

                    						newCompositeNode(grammarAccess.getClockDefinitionAccess().getSkewTimeExprParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_35);
                    lv_skew_6_0=ruleTimeExpr();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getClockDefinitionRule());
                    						}
                    						set(
                    							current,
                    							"skew",
                    							lv_skew_6_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.TimeExpr");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalContractSpec.g:1896:3: (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==42) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalContractSpec.g:1897:4: otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) )
                    {
                    otherlv_7=(Token)match(input,42,FOLLOW_6); 

                    				newLeafNode(otherlv_7, grammarAccess.getClockDefinitionAccess().getDriftKeyword_5_0());
                    			
                    // InternalContractSpec.g:1901:4: ( (lv_drift_8_0= ruleInterval ) )
                    // InternalContractSpec.g:1902:5: (lv_drift_8_0= ruleInterval )
                    {
                    // InternalContractSpec.g:1902:5: (lv_drift_8_0= ruleInterval )
                    // InternalContractSpec.g:1903:6: lv_drift_8_0= ruleInterval
                    {

                    						newCompositeNode(grammarAccess.getClockDefinitionAccess().getDriftIntervalParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_36);
                    lv_drift_8_0=ruleInterval();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getClockDefinitionRule());
                    						}
                    						set(
                    							current,
                    							"drift",
                    							lv_drift_8_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Interval");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalContractSpec.g:1921:3: (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==43) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalContractSpec.g:1922:4: otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) )
                    {
                    otherlv_9=(Token)match(input,43,FOLLOW_15); 

                    				newLeafNode(otherlv_9, grammarAccess.getClockDefinitionAccess().getMaxdiffKeyword_6_0());
                    			
                    // InternalContractSpec.g:1926:4: ( (lv_maxdiff_10_0= ruleTimeExpr ) )
                    // InternalContractSpec.g:1927:5: (lv_maxdiff_10_0= ruleTimeExpr )
                    {
                    // InternalContractSpec.g:1927:5: (lv_maxdiff_10_0= ruleTimeExpr )
                    // InternalContractSpec.g:1928:6: lv_maxdiff_10_0= ruleTimeExpr
                    {

                    						newCompositeNode(grammarAccess.getClockDefinitionAccess().getMaxdiffTimeExprParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_maxdiff_10_0=ruleTimeExpr();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getClockDefinitionRule());
                    						}
                    						set(
                    							current,
                    							"maxdiff",
                    							lv_maxdiff_10_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.TimeExpr");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleClockDefinition"


    // $ANTLR start "ruleUnit"
    // InternalContractSpec.g:1950:1: ruleUnit returns [Enumerator current=null] : ( (enumLiteral_0= 's' ) | (enumLiteral_1= 'ms' ) | (enumLiteral_2= 'us' ) | (enumLiteral_3= 'ns' ) ) ;
    public final Enumerator ruleUnit() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1956:2: ( ( (enumLiteral_0= 's' ) | (enumLiteral_1= 'ms' ) | (enumLiteral_2= 'us' ) | (enumLiteral_3= 'ns' ) ) )
            // InternalContractSpec.g:1957:2: ( (enumLiteral_0= 's' ) | (enumLiteral_1= 'ms' ) | (enumLiteral_2= 'us' ) | (enumLiteral_3= 'ns' ) )
            {
            // InternalContractSpec.g:1957:2: ( (enumLiteral_0= 's' ) | (enumLiteral_1= 'ms' ) | (enumLiteral_2= 'us' ) | (enumLiteral_3= 'ns' ) )
            int alt25=4;
            switch ( input.LA(1) ) {
            case 44:
                {
                alt25=1;
                }
                break;
            case 45:
                {
                alt25=2;
                }
                break;
            case 46:
                {
                alt25=3;
                }
                break;
            case 47:
                {
                alt25=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // InternalContractSpec.g:1958:3: (enumLiteral_0= 's' )
                    {
                    // InternalContractSpec.g:1958:3: (enumLiteral_0= 's' )
                    // InternalContractSpec.g:1959:4: enumLiteral_0= 's'
                    {
                    enumLiteral_0=(Token)match(input,44,FOLLOW_2); 

                    				current = grammarAccess.getUnitAccess().getSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getUnitAccess().getSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:1966:3: (enumLiteral_1= 'ms' )
                    {
                    // InternalContractSpec.g:1966:3: (enumLiteral_1= 'ms' )
                    // InternalContractSpec.g:1967:4: enumLiteral_1= 'ms'
                    {
                    enumLiteral_1=(Token)match(input,45,FOLLOW_2); 

                    				current = grammarAccess.getUnitAccess().getMSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getUnitAccess().getMSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalContractSpec.g:1974:3: (enumLiteral_2= 'us' )
                    {
                    // InternalContractSpec.g:1974:3: (enumLiteral_2= 'us' )
                    // InternalContractSpec.g:1975:4: enumLiteral_2= 'us'
                    {
                    enumLiteral_2=(Token)match(input,46,FOLLOW_2); 

                    				current = grammarAccess.getUnitAccess().getUSEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getUnitAccess().getUSEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalContractSpec.g:1982:3: (enumLiteral_3= 'ns' )
                    {
                    // InternalContractSpec.g:1982:3: (enumLiteral_3= 'ns' )
                    // InternalContractSpec.g:1983:4: enumLiteral_3= 'ns'
                    {
                    enumLiteral_3=(Token)match(input,47,FOLLOW_2); 

                    				current = grammarAccess.getUnitAccess().getNSEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getUnitAccess().getNSEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

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
    // $ANTLR end "ruleUnit"


    // $ANTLR start "ruleCausalFuncName"
    // InternalContractSpec.g:1993:1: ruleCausalFuncName returns [Enumerator current=null] : ( (enumLiteral_0= '|>' ) | (enumLiteral_1= '<|' ) ) ;
    public final Enumerator ruleCausalFuncName() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1999:2: ( ( (enumLiteral_0= '|>' ) | (enumLiteral_1= '<|' ) ) )
            // InternalContractSpec.g:2000:2: ( (enumLiteral_0= '|>' ) | (enumLiteral_1= '<|' ) )
            {
            // InternalContractSpec.g:2000:2: ( (enumLiteral_0= '|>' ) | (enumLiteral_1= '<|' ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==48) ) {
                alt26=1;
            }
            else if ( (LA26_0==49) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // InternalContractSpec.g:2001:3: (enumLiteral_0= '|>' )
                    {
                    // InternalContractSpec.g:2001:3: (enumLiteral_0= '|>' )
                    // InternalContractSpec.g:2002:4: enumLiteral_0= '|>'
                    {
                    enumLiteral_0=(Token)match(input,48,FOLLOW_2); 

                    				current = grammarAccess.getCausalFuncNameAccess().getREACTIONEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCausalFuncNameAccess().getREACTIONEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:2009:3: (enumLiteral_1= '<|' )
                    {
                    // InternalContractSpec.g:2009:3: (enumLiteral_1= '<|' )
                    // InternalContractSpec.g:2010:4: enumLiteral_1= '<|'
                    {
                    enumLiteral_1=(Token)match(input,49,FOLLOW_2); 

                    				current = grammarAccess.getCausalFuncNameAccess().getAGEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCausalFuncNameAccess().getAGEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

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
    // $ANTLR end "ruleCausalFuncName"


    // $ANTLR start "ruleCausalRelation"
    // InternalContractSpec.g:2020:1: ruleCausalRelation returns [Enumerator current=null] : ( (enumLiteral_0= 'FIFO' ) | (enumLiteral_1= 'LIFO' ) | (enumLiteral_2= 'ID' ) ) ;
    public final Enumerator ruleCausalRelation() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalContractSpec.g:2026:2: ( ( (enumLiteral_0= 'FIFO' ) | (enumLiteral_1= 'LIFO' ) | (enumLiteral_2= 'ID' ) ) )
            // InternalContractSpec.g:2027:2: ( (enumLiteral_0= 'FIFO' ) | (enumLiteral_1= 'LIFO' ) | (enumLiteral_2= 'ID' ) )
            {
            // InternalContractSpec.g:2027:2: ( (enumLiteral_0= 'FIFO' ) | (enumLiteral_1= 'LIFO' ) | (enumLiteral_2= 'ID' ) )
            int alt27=3;
            switch ( input.LA(1) ) {
            case 50:
                {
                alt27=1;
                }
                break;
            case 51:
                {
                alt27=2;
                }
                break;
            case 52:
                {
                alt27=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // InternalContractSpec.g:2028:3: (enumLiteral_0= 'FIFO' )
                    {
                    // InternalContractSpec.g:2028:3: (enumLiteral_0= 'FIFO' )
                    // InternalContractSpec.g:2029:4: enumLiteral_0= 'FIFO'
                    {
                    enumLiteral_0=(Token)match(input,50,FOLLOW_2); 

                    				current = grammarAccess.getCausalRelationAccess().getFIFOEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCausalRelationAccess().getFIFOEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:2036:3: (enumLiteral_1= 'LIFO' )
                    {
                    // InternalContractSpec.g:2036:3: (enumLiteral_1= 'LIFO' )
                    // InternalContractSpec.g:2037:4: enumLiteral_1= 'LIFO'
                    {
                    enumLiteral_1=(Token)match(input,51,FOLLOW_2); 

                    				current = grammarAccess.getCausalRelationAccess().getLIFOEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCausalRelationAccess().getLIFOEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalContractSpec.g:2044:3: (enumLiteral_2= 'ID' )
                    {
                    // InternalContractSpec.g:2044:3: (enumLiteral_2= 'ID' )
                    // InternalContractSpec.g:2045:4: enumLiteral_2= 'ID'
                    {
                    enumLiteral_2=(Token)match(input,52,FOLLOW_2); 

                    				current = grammarAccess.getCausalRelationAccess().getIDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getCausalRelationAccess().getIDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

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
    // $ANTLR end "ruleCausalRelation"

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    static final String dfa_1s = "\76\uffff";
    static final String dfa_2s = "\1\4\1\13\1\4\4\uffff\2\4\1\14\1\13\2\4\2\13\2\uffff\1\4\1\25\2\36\1\4\1\13\3\4\1\13\2\4\3\13\2\4\4\36\1\4\2\uffff\2\36\2\4\1\13\2\4\1\13\2\4\1\13\6\36\2\4\2\36";
    static final String dfa_3s = "\1\61\1\43\1\41\4\uffff\2\4\1\17\1\43\2\4\1\36\1\43\2\uffff\1\4\1\25\2\43\1\4\1\13\1\41\2\4\1\13\2\4\1\13\1\36\1\43\2\4\1\37\1\43\1\42\1\43\1\4\2\uffff\2\43\2\4\1\32\2\4\1\32\2\4\1\32\1\37\1\42\1\37\1\43\1\42\1\43\2\4\1\37\1\42";
    static final String dfa_4s = "\3\uffff\1\5\1\6\1\7\1\10\10\uffff\1\2\1\1\26\uffff\1\4\1\3\25\uffff";
    static final String dfa_5s = "\76\uffff}>";
    static final String[] dfa_6s = {
            "\1\1\17\uffff\1\2\7\uffff\1\3\3\uffff\1\4\6\uffff\1\6\10\uffff\2\5",
            "\1\11\22\uffff\1\10\4\uffff\1\7",
            "\1\12\30\uffff\1\13\3\uffff\1\14",
            "",
            "",
            "",
            "",
            "\1\15",
            "\1\16",
            "\1\20\2\uffff\1\17",
            "\1\22\27\uffff\1\21",
            "\1\23",
            "\1\24",
            "\1\11\22\uffff\1\10",
            "\1\11\22\uffff\1\10\4\uffff\1\25",
            "",
            "",
            "\1\26",
            "\1\27",
            "\1\31\1\32\3\uffff\1\30",
            "\1\34\3\uffff\1\35\1\33",
            "\1\36",
            "\1\22",
            "\1\37\30\uffff\1\40\3\uffff\1\41",
            "\1\42",
            "\1\43",
            "\1\22",
            "\1\44",
            "\1\45",
            "\1\22",
            "\1\11\22\uffff\1\10",
            "\1\50\16\uffff\1\47\10\uffff\1\46",
            "\1\51",
            "\1\52",
            "\1\31\1\32",
            "\1\31\1\32\3\uffff\1\53",
            "\1\34\3\uffff\1\35",
            "\1\34\3\uffff\1\35\1\54",
            "\1\55",
            "",
            "",
            "\1\57\1\60\3\uffff\1\56",
            "\1\62\3\uffff\1\63\1\61",
            "\1\64",
            "\1\65",
            "\1\50\16\uffff\1\47",
            "\1\66",
            "\1\67",
            "\1\50\16\uffff\1\47",
            "\1\70",
            "\1\71",
            "\1\50\16\uffff\1\47",
            "\1\31\1\32",
            "\1\34\3\uffff\1\35",
            "\1\57\1\60",
            "\1\57\1\60\3\uffff\1\72",
            "\1\62\3\uffff\1\63",
            "\1\62\3\uffff\1\63\1\73",
            "\1\74",
            "\1\75",
            "\1\57\1\60",
            "\1\62\3\uffff\1\63"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "125:2: (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0003008110100012L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000003000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000012002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000220000010L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000402022L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000F00000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x001C000000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x00000F0000000002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x00000E0000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x00000C0000000002L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000080000000002L});

}