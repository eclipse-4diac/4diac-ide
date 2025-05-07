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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'occurs'", "'within'", "'using'", "'clock'", "'every'", "'with'", "'and'", "'jitter'", "'offset'", "'whenever'", "'then'", "'once'", "'out'", "'of'", "'times'", "'has'", "'occurred'", "'Reaction'", "'('", "','", "')'", "'Age'", "'{'", "'}'", "'.'", "'['", "']'", "'s'", "'ms'", "'us'", "'ns'", "':='", "'|>'", "'<|'", "'FIFO'", "'LIFO'", "'ID'", "'Clock'", "'resolution'", "'skew'", "'drift'", "'maxdiff'"
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

        EObject lv_timeSpec_0_0 = null;



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

                if ( (LA1_0==RULE_ID||LA1_0==20||LA1_0==28||LA1_0==32||(LA1_0>=43 && LA1_0<=44)||LA1_0==48) ) {
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
    // InternalContractSpec.g:110:1: entryRuleTimeSpec returns [EObject current=null] : iv_ruleTimeSpec= ruleTimeSpec EOF ;
    public final EObject entryRuleTimeSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeSpec = null;


        try {
            // InternalContractSpec.g:110:49: (iv_ruleTimeSpec= ruleTimeSpec EOF )
            // InternalContractSpec.g:111:2: iv_ruleTimeSpec= ruleTimeSpec EOF
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
    // InternalContractSpec.g:117:1: ruleTimeSpec returns [EObject current=null] : (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition ) ;
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
            // InternalContractSpec.g:123:2: ( (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition ) )
            // InternalContractSpec.g:124:2: (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition )
            {
            // InternalContractSpec.g:124:2: (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition )
            int alt2=8;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // InternalContractSpec.g:125:3: this_SingleEvent_0= ruleSingleEvent
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
                    // InternalContractSpec.g:134:3: this_Repetition_1= ruleRepetition
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
                    // InternalContractSpec.g:143:3: this_Reaction_2= ruleReaction
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
                    // InternalContractSpec.g:152:3: this_Age_3= ruleAge
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
                    // InternalContractSpec.g:161:3: this_CausalReaction_4= ruleCausalReaction
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
                    // InternalContractSpec.g:170:3: this_CausalAge_5= ruleCausalAge
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
                    // InternalContractSpec.g:179:3: this_CausalFuncDecl_6= ruleCausalFuncDecl
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
                    // InternalContractSpec.g:188:3: this_ClockDefinition_7= ruleClockDefinition
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
    // InternalContractSpec.g:200:1: entryRuleSingleEvent returns [EObject current=null] : iv_ruleSingleEvent= ruleSingleEvent EOF ;
    public final EObject entryRuleSingleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSingleEvent = null;


        try {
            // InternalContractSpec.g:200:52: (iv_ruleSingleEvent= ruleSingleEvent EOF )
            // InternalContractSpec.g:201:2: iv_ruleSingleEvent= ruleSingleEvent EOF
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
    // InternalContractSpec.g:207:1: ruleSingleEvent returns [EObject current=null] : ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )? ) ;
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
            // InternalContractSpec.g:213:2: ( ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:214:2: ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:214:2: ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )? )
            // InternalContractSpec.g:215:3: ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'within' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )?
            {
            // InternalContractSpec.g:215:3: ( (lv_events_0_0= ruleEventList ) )
            // InternalContractSpec.g:216:4: (lv_events_0_0= ruleEventList )
            {
            // InternalContractSpec.g:216:4: (lv_events_0_0= ruleEventList )
            // InternalContractSpec.g:217:5: lv_events_0_0= ruleEventList
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
            		
            // InternalContractSpec.g:242:3: ( (lv_interval_3_0= ruleInterval ) )
            // InternalContractSpec.g:243:4: (lv_interval_3_0= ruleInterval )
            {
            // InternalContractSpec.g:243:4: (lv_interval_3_0= ruleInterval )
            // InternalContractSpec.g:244:5: lv_interval_3_0= ruleInterval
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

            // InternalContractSpec.g:261:3: (otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==13) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalContractSpec.g:262:4: otherlv_4= 'using' otherlv_5= 'clock' ( (otherlv_6= RULE_ID ) )
                    {
                    otherlv_4=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_4, grammarAccess.getSingleEventAccess().getUsingKeyword_4_0());
                    			
                    otherlv_5=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_5, grammarAccess.getSingleEventAccess().getClockKeyword_4_1());
                    			
                    // InternalContractSpec.g:270:4: ( (otherlv_6= RULE_ID ) )
                    // InternalContractSpec.g:271:5: (otherlv_6= RULE_ID )
                    {
                    // InternalContractSpec.g:271:5: (otherlv_6= RULE_ID )
                    // InternalContractSpec.g:272:6: otherlv_6= RULE_ID
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
    // InternalContractSpec.g:288:1: entryRuleRepetition returns [EObject current=null] : iv_ruleRepetition= ruleRepetition EOF ;
    public final EObject entryRuleRepetition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRepetition = null;


        try {
            // InternalContractSpec.g:288:51: (iv_ruleRepetition= ruleRepetition EOF )
            // InternalContractSpec.g:289:2: iv_ruleRepetition= ruleRepetition EOF
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
    // InternalContractSpec.g:295:1: ruleRepetition returns [EObject current=null] : ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )? ) ;
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
            // InternalContractSpec.g:301:2: ( ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:302:2: ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:302:2: ( ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )? )
            // InternalContractSpec.g:303:3: ( (lv_events_0_0= ruleEventList ) ) otherlv_1= 'occurs' otherlv_2= 'every' ( (lv_interval_3_0= ruleInterval ) ) (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )? (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )?
            {
            // InternalContractSpec.g:303:3: ( (lv_events_0_0= ruleEventList ) )
            // InternalContractSpec.g:304:4: (lv_events_0_0= ruleEventList )
            {
            // InternalContractSpec.g:304:4: (lv_events_0_0= ruleEventList )
            // InternalContractSpec.g:305:5: lv_events_0_0= ruleEventList
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
            		
            // InternalContractSpec.g:330:3: ( (lv_interval_3_0= ruleInterval ) )
            // InternalContractSpec.g:331:4: (lv_interval_3_0= ruleInterval )
            {
            // InternalContractSpec.g:331:4: (lv_interval_3_0= ruleInterval )
            // InternalContractSpec.g:332:5: lv_interval_3_0= ruleInterval
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

            // InternalContractSpec.g:349:3: (otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==16) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalContractSpec.g:350:4: otherlv_4= 'with' ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) )
                    {
                    otherlv_4=(Token)match(input,16,FOLLOW_12); 

                    				newLeafNode(otherlv_4, grammarAccess.getRepetitionAccess().getWithKeyword_4_0());
                    			
                    // InternalContractSpec.g:354:4: ( (lv_repetitionOptions_5_0= ruleRepetitionOptions ) )
                    // InternalContractSpec.g:355:5: (lv_repetitionOptions_5_0= ruleRepetitionOptions )
                    {
                    // InternalContractSpec.g:355:5: (lv_repetitionOptions_5_0= ruleRepetitionOptions )
                    // InternalContractSpec.g:356:6: lv_repetitionOptions_5_0= ruleRepetitionOptions
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

            // InternalContractSpec.g:374:3: (otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==13) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalContractSpec.g:375:4: otherlv_6= 'using' otherlv_7= 'clock' ( (otherlv_8= RULE_ID ) )
                    {
                    otherlv_6=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_6, grammarAccess.getRepetitionAccess().getUsingKeyword_5_0());
                    			
                    otherlv_7=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_7, grammarAccess.getRepetitionAccess().getClockKeyword_5_1());
                    			
                    // InternalContractSpec.g:383:4: ( (otherlv_8= RULE_ID ) )
                    // InternalContractSpec.g:384:5: (otherlv_8= RULE_ID )
                    {
                    // InternalContractSpec.g:384:5: (otherlv_8= RULE_ID )
                    // InternalContractSpec.g:385:6: otherlv_8= RULE_ID
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
    // InternalContractSpec.g:401:1: entryRuleRepetitionOptions returns [EObject current=null] : iv_ruleRepetitionOptions= ruleRepetitionOptions EOF ;
    public final EObject entryRuleRepetitionOptions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRepetitionOptions = null;


        try {
            // InternalContractSpec.g:401:58: (iv_ruleRepetitionOptions= ruleRepetitionOptions EOF )
            // InternalContractSpec.g:402:2: iv_ruleRepetitionOptions= ruleRepetitionOptions EOF
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
    // InternalContractSpec.g:408:1: ruleRepetitionOptions returns [EObject current=null] : ( ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? ) | ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? ) ) ;
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
            // InternalContractSpec.g:414:2: ( ( ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? ) | ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? ) ) )
            // InternalContractSpec.g:415:2: ( ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? ) | ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? ) )
            {
            // InternalContractSpec.g:415:2: ( ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? ) | ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? ) )
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
                    // InternalContractSpec.g:416:3: ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? )
                    {
                    // InternalContractSpec.g:416:3: ( ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )? )
                    // InternalContractSpec.g:417:4: ( (lv_jitter_0_0= ruleJitter ) ) (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )?
                    {
                    // InternalContractSpec.g:417:4: ( (lv_jitter_0_0= ruleJitter ) )
                    // InternalContractSpec.g:418:5: (lv_jitter_0_0= ruleJitter )
                    {
                    // InternalContractSpec.g:418:5: (lv_jitter_0_0= ruleJitter )
                    // InternalContractSpec.g:419:6: lv_jitter_0_0= ruleJitter
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

                    // InternalContractSpec.g:436:4: (otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) ) )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==17) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // InternalContractSpec.g:437:5: otherlv_1= 'and' ( (lv_offset_2_0= ruleOffset ) )
                            {
                            otherlv_1=(Token)match(input,17,FOLLOW_12); 

                            					newLeafNode(otherlv_1, grammarAccess.getRepetitionOptionsAccess().getAndKeyword_0_1_0());
                            				
                            // InternalContractSpec.g:441:5: ( (lv_offset_2_0= ruleOffset ) )
                            // InternalContractSpec.g:442:6: (lv_offset_2_0= ruleOffset )
                            {
                            // InternalContractSpec.g:442:6: (lv_offset_2_0= ruleOffset )
                            // InternalContractSpec.g:443:7: lv_offset_2_0= ruleOffset
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
                    // InternalContractSpec.g:463:3: ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? )
                    {
                    // InternalContractSpec.g:463:3: ( ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )? )
                    // InternalContractSpec.g:464:4: ( (lv_offset_3_0= ruleOffset ) ) (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )?
                    {
                    // InternalContractSpec.g:464:4: ( (lv_offset_3_0= ruleOffset ) )
                    // InternalContractSpec.g:465:5: (lv_offset_3_0= ruleOffset )
                    {
                    // InternalContractSpec.g:465:5: (lv_offset_3_0= ruleOffset )
                    // InternalContractSpec.g:466:6: lv_offset_3_0= ruleOffset
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

                    // InternalContractSpec.g:483:4: (otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) ) )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==17) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // InternalContractSpec.g:484:5: otherlv_4= 'and' ( (lv_jitter_5_0= ruleJitter ) )
                            {
                            otherlv_4=(Token)match(input,17,FOLLOW_14); 

                            					newLeafNode(otherlv_4, grammarAccess.getRepetitionOptionsAccess().getAndKeyword_1_1_0());
                            				
                            // InternalContractSpec.g:488:5: ( (lv_jitter_5_0= ruleJitter ) )
                            // InternalContractSpec.g:489:6: (lv_jitter_5_0= ruleJitter )
                            {
                            // InternalContractSpec.g:489:6: (lv_jitter_5_0= ruleJitter )
                            // InternalContractSpec.g:490:7: lv_jitter_5_0= ruleJitter
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
    // InternalContractSpec.g:513:1: entryRuleJitter returns [EObject current=null] : iv_ruleJitter= ruleJitter EOF ;
    public final EObject entryRuleJitter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJitter = null;


        try {
            // InternalContractSpec.g:513:47: (iv_ruleJitter= ruleJitter EOF )
            // InternalContractSpec.g:514:2: iv_ruleJitter= ruleJitter EOF
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
    // InternalContractSpec.g:520:1: ruleJitter returns [EObject current=null] : (otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) ) ) ;
    public final EObject ruleJitter() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_time_1_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:526:2: ( (otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) ) ) )
            // InternalContractSpec.g:527:2: (otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) ) )
            {
            // InternalContractSpec.g:527:2: (otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) ) )
            // InternalContractSpec.g:528:3: otherlv_0= 'jitter' ( (lv_time_1_0= ruleTimeExpr ) )
            {
            otherlv_0=(Token)match(input,18,FOLLOW_15); 

            			newLeafNode(otherlv_0, grammarAccess.getJitterAccess().getJitterKeyword_0());
            		
            // InternalContractSpec.g:532:3: ( (lv_time_1_0= ruleTimeExpr ) )
            // InternalContractSpec.g:533:4: (lv_time_1_0= ruleTimeExpr )
            {
            // InternalContractSpec.g:533:4: (lv_time_1_0= ruleTimeExpr )
            // InternalContractSpec.g:534:5: lv_time_1_0= ruleTimeExpr
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
    // InternalContractSpec.g:555:1: entryRuleOffset returns [EObject current=null] : iv_ruleOffset= ruleOffset EOF ;
    public final EObject entryRuleOffset() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOffset = null;


        try {
            // InternalContractSpec.g:555:47: (iv_ruleOffset= ruleOffset EOF )
            // InternalContractSpec.g:556:2: iv_ruleOffset= ruleOffset EOF
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
    // InternalContractSpec.g:562:1: ruleOffset returns [EObject current=null] : (otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) ) ) ;
    public final EObject ruleOffset() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_interval_1_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:568:2: ( (otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) ) ) )
            // InternalContractSpec.g:569:2: (otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) ) )
            {
            // InternalContractSpec.g:569:2: (otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) ) )
            // InternalContractSpec.g:570:3: otherlv_0= 'offset' ( (lv_interval_1_0= ruleInterval ) )
            {
            otherlv_0=(Token)match(input,19,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getOffsetAccess().getOffsetKeyword_0());
            		
            // InternalContractSpec.g:574:3: ( (lv_interval_1_0= ruleInterval ) )
            // InternalContractSpec.g:575:4: (lv_interval_1_0= ruleInterval )
            {
            // InternalContractSpec.g:575:4: (lv_interval_1_0= ruleInterval )
            // InternalContractSpec.g:576:5: lv_interval_1_0= ruleInterval
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
    // InternalContractSpec.g:597:1: entryRuleReaction returns [EObject current=null] : iv_ruleReaction= ruleReaction EOF ;
    public final EObject entryRuleReaction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReaction = null;


        try {
            // InternalContractSpec.g:597:49: (iv_ruleReaction= ruleReaction EOF )
            // InternalContractSpec.g:598:2: iv_ruleReaction= ruleReaction EOF
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
    // InternalContractSpec.g:604:1: ruleReaction returns [EObject current=null] : (otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'once' )? ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )? ) ;
    public final EObject ruleReaction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_n_9_0=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token lv_outOf_12_0=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        EObject lv_trigger_1_0 = null;

        EObject lv_reaction_4_0 = null;

        EObject lv_interval_7_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:610:2: ( (otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'once' )? ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:611:2: (otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'once' )? ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:611:2: (otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'once' )? ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )? )
            // InternalContractSpec.g:612:3: otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'occurs' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'once' )? ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' )? (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,20,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getReactionAccess().getWheneverKeyword_0());
            		
            // InternalContractSpec.g:616:3: ( (lv_trigger_1_0= ruleEventExpr ) )
            // InternalContractSpec.g:617:4: (lv_trigger_1_0= ruleEventExpr )
            {
            // InternalContractSpec.g:617:4: (lv_trigger_1_0= ruleEventExpr )
            // InternalContractSpec.g:618:5: lv_trigger_1_0= ruleEventExpr
            {

            					newCompositeNode(grammarAccess.getReactionAccess().getTriggerEventExprParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_trigger_1_0=ruleEventExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReactionRule());
            					}
            					set(
            						current,
            						"trigger",
            						lv_trigger_1_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventExpr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,11,FOLLOW_17); 

            			newLeafNode(otherlv_2, grammarAccess.getReactionAccess().getOccursKeyword_2());
            		
            otherlv_3=(Token)match(input,21,FOLLOW_16); 

            			newLeafNode(otherlv_3, grammarAccess.getReactionAccess().getThenKeyword_3());
            		
            // InternalContractSpec.g:643:3: ( (lv_reaction_4_0= ruleEventExpr ) )
            // InternalContractSpec.g:644:4: (lv_reaction_4_0= ruleEventExpr )
            {
            // InternalContractSpec.g:644:4: (lv_reaction_4_0= ruleEventExpr )
            // InternalContractSpec.g:645:5: lv_reaction_4_0= ruleEventExpr
            {

            					newCompositeNode(grammarAccess.getReactionAccess().getReactionEventExprParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_4);
            lv_reaction_4_0=ruleEventExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReactionRule());
            					}
            					set(
            						current,
            						"reaction",
            						lv_reaction_4_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventExpr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,11,FOLLOW_5); 

            			newLeafNode(otherlv_5, grammarAccess.getReactionAccess().getOccursKeyword_5());
            		
            otherlv_6=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getReactionAccess().getWithinKeyword_6());
            		
            // InternalContractSpec.g:670:3: ( (lv_interval_7_0= ruleInterval ) )
            // InternalContractSpec.g:671:4: (lv_interval_7_0= ruleInterval )
            {
            // InternalContractSpec.g:671:4: (lv_interval_7_0= ruleInterval )
            // InternalContractSpec.g:672:5: lv_interval_7_0= ruleInterval
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

            // InternalContractSpec.g:689:3: (otherlv_8= 'once' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==22) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalContractSpec.g:690:4: otherlv_8= 'once'
                    {
                    otherlv_8=(Token)match(input,22,FOLLOW_19); 

                    				newLeafNode(otherlv_8, grammarAccess.getReactionAccess().getOnceKeyword_8());
                    			

                    }
                    break;

            }

            // InternalContractSpec.g:695:3: ( ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_INT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalContractSpec.g:696:4: ( (lv_n_9_0= RULE_INT ) ) otherlv_10= 'out' otherlv_11= 'of' ( (lv_outOf_12_0= RULE_INT ) ) otherlv_13= 'times'
                    {
                    // InternalContractSpec.g:696:4: ( (lv_n_9_0= RULE_INT ) )
                    // InternalContractSpec.g:697:5: (lv_n_9_0= RULE_INT )
                    {
                    // InternalContractSpec.g:697:5: (lv_n_9_0= RULE_INT )
                    // InternalContractSpec.g:698:6: lv_n_9_0= RULE_INT
                    {
                    lv_n_9_0=(Token)match(input,RULE_INT,FOLLOW_20); 

                    						newLeafNode(lv_n_9_0, grammarAccess.getReactionAccess().getNINTTerminalRuleCall_9_0_0());
                    					

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

                    otherlv_10=(Token)match(input,23,FOLLOW_21); 

                    				newLeafNode(otherlv_10, grammarAccess.getReactionAccess().getOutKeyword_9_1());
                    			
                    otherlv_11=(Token)match(input,24,FOLLOW_15); 

                    				newLeafNode(otherlv_11, grammarAccess.getReactionAccess().getOfKeyword_9_2());
                    			
                    // InternalContractSpec.g:722:4: ( (lv_outOf_12_0= RULE_INT ) )
                    // InternalContractSpec.g:723:5: (lv_outOf_12_0= RULE_INT )
                    {
                    // InternalContractSpec.g:723:5: (lv_outOf_12_0= RULE_INT )
                    // InternalContractSpec.g:724:6: lv_outOf_12_0= RULE_INT
                    {
                    lv_outOf_12_0=(Token)match(input,RULE_INT,FOLLOW_22); 

                    						newLeafNode(lv_outOf_12_0, grammarAccess.getReactionAccess().getOutOfINTTerminalRuleCall_9_3_0());
                    					

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

                    				newLeafNode(otherlv_13, grammarAccess.getReactionAccess().getTimesKeyword_9_4());
                    			

                    }
                    break;

            }

            // InternalContractSpec.g:745:3: (otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==13) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalContractSpec.g:746:4: otherlv_14= 'using' otherlv_15= 'clock' ( (otherlv_16= RULE_ID ) )
                    {
                    otherlv_14=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_14, grammarAccess.getReactionAccess().getUsingKeyword_10_0());
                    			
                    otherlv_15=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_15, grammarAccess.getReactionAccess().getClockKeyword_10_1());
                    			
                    // InternalContractSpec.g:754:4: ( (otherlv_16= RULE_ID ) )
                    // InternalContractSpec.g:755:5: (otherlv_16= RULE_ID )
                    {
                    // InternalContractSpec.g:755:5: (otherlv_16= RULE_ID )
                    // InternalContractSpec.g:756:6: otherlv_16= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getReactionRule());
                    						}
                    					
                    otherlv_16=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_16, grammarAccess.getReactionAccess().getClockClockDefinitionCrossReference_10_2_0());
                    					

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
    // InternalContractSpec.g:772:1: entryRuleAge returns [EObject current=null] : iv_ruleAge= ruleAge EOF ;
    public final EObject entryRuleAge() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAge = null;


        try {
            // InternalContractSpec.g:772:44: (iv_ruleAge= ruleAge EOF )
            // InternalContractSpec.g:773:2: iv_ruleAge= ruleAge EOF
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
    // InternalContractSpec.g:779:1: ruleAge returns [EObject current=null] : (otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) (otherlv_9= 'once' )? ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )? ) ;
    public final EObject ruleAge() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token lv_n_10_0=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_outOf_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        EObject lv_trigger_1_0 = null;

        EObject lv_reaction_4_0 = null;

        EObject lv_interval_8_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:785:2: ( (otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) (otherlv_9= 'once' )? ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:786:2: (otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) (otherlv_9= 'once' )? ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:786:2: (otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) (otherlv_9= 'once' )? ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )? )
            // InternalContractSpec.g:787:3: otherlv_0= 'whenever' ( (lv_trigger_1_0= ruleEventExpr ) ) otherlv_2= 'occurs' otherlv_3= 'then' ( (lv_reaction_4_0= ruleEventExpr ) ) otherlv_5= 'has' otherlv_6= 'occurred' otherlv_7= 'within' ( (lv_interval_8_0= ruleInterval ) ) (otherlv_9= 'once' )? ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' )? (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,20,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getAgeAccess().getWheneverKeyword_0());
            		
            // InternalContractSpec.g:791:3: ( (lv_trigger_1_0= ruleEventExpr ) )
            // InternalContractSpec.g:792:4: (lv_trigger_1_0= ruleEventExpr )
            {
            // InternalContractSpec.g:792:4: (lv_trigger_1_0= ruleEventExpr )
            // InternalContractSpec.g:793:5: lv_trigger_1_0= ruleEventExpr
            {

            					newCompositeNode(grammarAccess.getAgeAccess().getTriggerEventExprParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_trigger_1_0=ruleEventExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAgeRule());
            					}
            					set(
            						current,
            						"trigger",
            						lv_trigger_1_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventExpr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,11,FOLLOW_17); 

            			newLeafNode(otherlv_2, grammarAccess.getAgeAccess().getOccursKeyword_2());
            		
            otherlv_3=(Token)match(input,21,FOLLOW_16); 

            			newLeafNode(otherlv_3, grammarAccess.getAgeAccess().getThenKeyword_3());
            		
            // InternalContractSpec.g:818:3: ( (lv_reaction_4_0= ruleEventExpr ) )
            // InternalContractSpec.g:819:4: (lv_reaction_4_0= ruleEventExpr )
            {
            // InternalContractSpec.g:819:4: (lv_reaction_4_0= ruleEventExpr )
            // InternalContractSpec.g:820:5: lv_reaction_4_0= ruleEventExpr
            {

            					newCompositeNode(grammarAccess.getAgeAccess().getReactionEventExprParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_23);
            lv_reaction_4_0=ruleEventExpr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAgeRule());
            					}
            					set(
            						current,
            						"reaction",
            						lv_reaction_4_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventExpr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,26,FOLLOW_24); 

            			newLeafNode(otherlv_5, grammarAccess.getAgeAccess().getHasKeyword_5());
            		
            otherlv_6=(Token)match(input,27,FOLLOW_5); 

            			newLeafNode(otherlv_6, grammarAccess.getAgeAccess().getOccurredKeyword_6());
            		
            otherlv_7=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getAgeAccess().getWithinKeyword_7());
            		
            // InternalContractSpec.g:849:3: ( (lv_interval_8_0= ruleInterval ) )
            // InternalContractSpec.g:850:4: (lv_interval_8_0= ruleInterval )
            {
            // InternalContractSpec.g:850:4: (lv_interval_8_0= ruleInterval )
            // InternalContractSpec.g:851:5: lv_interval_8_0= ruleInterval
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

            // InternalContractSpec.g:868:3: (otherlv_9= 'once' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==22) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalContractSpec.g:869:4: otherlv_9= 'once'
                    {
                    otherlv_9=(Token)match(input,22,FOLLOW_19); 

                    				newLeafNode(otherlv_9, grammarAccess.getAgeAccess().getOnceKeyword_9());
                    			

                    }
                    break;

            }

            // InternalContractSpec.g:874:3: ( ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_INT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalContractSpec.g:875:4: ( (lv_n_10_0= RULE_INT ) ) otherlv_11= 'out' otherlv_12= 'of' ( (lv_outOf_13_0= RULE_INT ) ) otherlv_14= 'times'
                    {
                    // InternalContractSpec.g:875:4: ( (lv_n_10_0= RULE_INT ) )
                    // InternalContractSpec.g:876:5: (lv_n_10_0= RULE_INT )
                    {
                    // InternalContractSpec.g:876:5: (lv_n_10_0= RULE_INT )
                    // InternalContractSpec.g:877:6: lv_n_10_0= RULE_INT
                    {
                    lv_n_10_0=(Token)match(input,RULE_INT,FOLLOW_20); 

                    						newLeafNode(lv_n_10_0, grammarAccess.getAgeAccess().getNINTTerminalRuleCall_10_0_0());
                    					

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

                    otherlv_11=(Token)match(input,23,FOLLOW_21); 

                    				newLeafNode(otherlv_11, grammarAccess.getAgeAccess().getOutKeyword_10_1());
                    			
                    otherlv_12=(Token)match(input,24,FOLLOW_15); 

                    				newLeafNode(otherlv_12, grammarAccess.getAgeAccess().getOfKeyword_10_2());
                    			
                    // InternalContractSpec.g:901:4: ( (lv_outOf_13_0= RULE_INT ) )
                    // InternalContractSpec.g:902:5: (lv_outOf_13_0= RULE_INT )
                    {
                    // InternalContractSpec.g:902:5: (lv_outOf_13_0= RULE_INT )
                    // InternalContractSpec.g:903:6: lv_outOf_13_0= RULE_INT
                    {
                    lv_outOf_13_0=(Token)match(input,RULE_INT,FOLLOW_22); 

                    						newLeafNode(lv_outOf_13_0, grammarAccess.getAgeAccess().getOutOfINTTerminalRuleCall_10_3_0());
                    					

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

                    				newLeafNode(otherlv_14, grammarAccess.getAgeAccess().getTimesKeyword_10_4());
                    			

                    }
                    break;

            }

            // InternalContractSpec.g:924:3: (otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==13) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalContractSpec.g:925:4: otherlv_15= 'using' otherlv_16= 'clock' ( (otherlv_17= RULE_ID ) )
                    {
                    otherlv_15=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_15, grammarAccess.getAgeAccess().getUsingKeyword_11_0());
                    			
                    otherlv_16=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_16, grammarAccess.getAgeAccess().getClockKeyword_11_1());
                    			
                    // InternalContractSpec.g:933:4: ( (otherlv_17= RULE_ID ) )
                    // InternalContractSpec.g:934:5: (otherlv_17= RULE_ID )
                    {
                    // InternalContractSpec.g:934:5: (otherlv_17= RULE_ID )
                    // InternalContractSpec.g:935:6: otherlv_17= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAgeRule());
                    						}
                    					
                    otherlv_17=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_17, grammarAccess.getAgeAccess().getClockClockDefinitionCrossReference_11_2_0());
                    					

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
    // InternalContractSpec.g:951:1: entryRuleCausalReaction returns [EObject current=null] : iv_ruleCausalReaction= ruleCausalReaction EOF ;
    public final EObject entryRuleCausalReaction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCausalReaction = null;


        try {
            // InternalContractSpec.g:951:55: (iv_ruleCausalReaction= ruleCausalReaction EOF )
            // InternalContractSpec.g:952:2: iv_ruleCausalReaction= ruleCausalReaction EOF
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
    // InternalContractSpec.g:958:1: ruleCausalReaction returns [EObject current=null] : (otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? ) ;
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
        EObject lv_e1_2_0 = null;

        EObject lv_e2_4_0 = null;

        EObject lv_interval_7_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:964:2: ( (otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:965:2: (otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:965:2: (otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? )
            // InternalContractSpec.g:966:3: otherlv_0= 'Reaction' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,28,FOLLOW_25); 

            			newLeafNode(otherlv_0, grammarAccess.getCausalReactionAccess().getReactionKeyword_0());
            		
            otherlv_1=(Token)match(input,29,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getCausalReactionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalContractSpec.g:974:3: ( (lv_e1_2_0= ruleEventSpec ) )
            // InternalContractSpec.g:975:4: (lv_e1_2_0= ruleEventSpec )
            {
            // InternalContractSpec.g:975:4: (lv_e1_2_0= ruleEventSpec )
            // InternalContractSpec.g:976:5: lv_e1_2_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getCausalReactionAccess().getE1EventSpecParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_26);
            lv_e1_2_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalReactionRule());
            					}
            					set(
            						current,
            						"e1",
            						lv_e1_2_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,30,FOLLOW_9); 

            			newLeafNode(otherlv_3, grammarAccess.getCausalReactionAccess().getCommaKeyword_3());
            		
            // InternalContractSpec.g:997:3: ( (lv_e2_4_0= ruleEventSpec ) )
            // InternalContractSpec.g:998:4: (lv_e2_4_0= ruleEventSpec )
            {
            // InternalContractSpec.g:998:4: (lv_e2_4_0= ruleEventSpec )
            // InternalContractSpec.g:999:5: lv_e2_4_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getCausalReactionAccess().getE2EventSpecParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_27);
            lv_e2_4_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalReactionRule());
            					}
            					set(
            						current,
            						"e2",
            						lv_e2_4_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,31,FOLLOW_5); 

            			newLeafNode(otherlv_5, grammarAccess.getCausalReactionAccess().getRightParenthesisKeyword_5());
            		
            otherlv_6=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getCausalReactionAccess().getWithinKeyword_6());
            		
            // InternalContractSpec.g:1024:3: ( (lv_interval_7_0= ruleInterval ) )
            // InternalContractSpec.g:1025:4: (lv_interval_7_0= ruleInterval )
            {
            // InternalContractSpec.g:1025:4: (lv_interval_7_0= ruleInterval )
            // InternalContractSpec.g:1026:5: lv_interval_7_0= ruleInterval
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

            // InternalContractSpec.g:1043:3: (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==13) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalContractSpec.g:1044:4: otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) )
                    {
                    otherlv_8=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_8, grammarAccess.getCausalReactionAccess().getUsingKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_9, grammarAccess.getCausalReactionAccess().getClockKeyword_8_1());
                    			
                    // InternalContractSpec.g:1052:4: ( (otherlv_10= RULE_ID ) )
                    // InternalContractSpec.g:1053:5: (otherlv_10= RULE_ID )
                    {
                    // InternalContractSpec.g:1053:5: (otherlv_10= RULE_ID )
                    // InternalContractSpec.g:1054:6: otherlv_10= RULE_ID
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
    // InternalContractSpec.g:1070:1: entryRuleCausalAge returns [EObject current=null] : iv_ruleCausalAge= ruleCausalAge EOF ;
    public final EObject entryRuleCausalAge() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCausalAge = null;


        try {
            // InternalContractSpec.g:1070:50: (iv_ruleCausalAge= ruleCausalAge EOF )
            // InternalContractSpec.g:1071:2: iv_ruleCausalAge= ruleCausalAge EOF
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
    // InternalContractSpec.g:1077:1: ruleCausalAge returns [EObject current=null] : (otherlv_0= 'Age' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? ) ;
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
        EObject lv_e1_2_0 = null;

        EObject lv_e2_4_0 = null;

        EObject lv_interval_7_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1083:2: ( (otherlv_0= 'Age' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:1084:2: (otherlv_0= 'Age' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:1084:2: (otherlv_0= 'Age' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )? )
            // InternalContractSpec.g:1085:3: otherlv_0= 'Age' otherlv_1= '(' ( (lv_e1_2_0= ruleEventSpec ) ) otherlv_3= ',' ( (lv_e2_4_0= ruleEventSpec ) ) otherlv_5= ')' otherlv_6= 'within' ( (lv_interval_7_0= ruleInterval ) ) (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,32,FOLLOW_25); 

            			newLeafNode(otherlv_0, grammarAccess.getCausalAgeAccess().getAgeKeyword_0());
            		
            otherlv_1=(Token)match(input,29,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getCausalAgeAccess().getLeftParenthesisKeyword_1());
            		
            // InternalContractSpec.g:1093:3: ( (lv_e1_2_0= ruleEventSpec ) )
            // InternalContractSpec.g:1094:4: (lv_e1_2_0= ruleEventSpec )
            {
            // InternalContractSpec.g:1094:4: (lv_e1_2_0= ruleEventSpec )
            // InternalContractSpec.g:1095:5: lv_e1_2_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getCausalAgeAccess().getE1EventSpecParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_26);
            lv_e1_2_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalAgeRule());
            					}
            					set(
            						current,
            						"e1",
            						lv_e1_2_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,30,FOLLOW_9); 

            			newLeafNode(otherlv_3, grammarAccess.getCausalAgeAccess().getCommaKeyword_3());
            		
            // InternalContractSpec.g:1116:3: ( (lv_e2_4_0= ruleEventSpec ) )
            // InternalContractSpec.g:1117:4: (lv_e2_4_0= ruleEventSpec )
            {
            // InternalContractSpec.g:1117:4: (lv_e2_4_0= ruleEventSpec )
            // InternalContractSpec.g:1118:5: lv_e2_4_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getCausalAgeAccess().getE2EventSpecParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_27);
            lv_e2_4_0=ruleEventSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCausalAgeRule());
            					}
            					set(
            						current,
            						"e2",
            						lv_e2_4_0,
            						"org.eclipse.fordiac.ide.ContractSpec.EventSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,31,FOLLOW_5); 

            			newLeafNode(otherlv_5, grammarAccess.getCausalAgeAccess().getRightParenthesisKeyword_5());
            		
            otherlv_6=(Token)match(input,12,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getCausalAgeAccess().getWithinKeyword_6());
            		
            // InternalContractSpec.g:1143:3: ( (lv_interval_7_0= ruleInterval ) )
            // InternalContractSpec.g:1144:4: (lv_interval_7_0= ruleInterval )
            {
            // InternalContractSpec.g:1144:4: (lv_interval_7_0= ruleInterval )
            // InternalContractSpec.g:1145:5: lv_interval_7_0= ruleInterval
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

            // InternalContractSpec.g:1162:3: (otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==13) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalContractSpec.g:1163:4: otherlv_8= 'using' otherlv_9= 'clock' ( (otherlv_10= RULE_ID ) )
                    {
                    otherlv_8=(Token)match(input,13,FOLLOW_8); 

                    				newLeafNode(otherlv_8, grammarAccess.getCausalAgeAccess().getUsingKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,14,FOLLOW_9); 

                    				newLeafNode(otherlv_9, grammarAccess.getCausalAgeAccess().getClockKeyword_8_1());
                    			
                    // InternalContractSpec.g:1171:4: ( (otherlv_10= RULE_ID ) )
                    // InternalContractSpec.g:1172:5: (otherlv_10= RULE_ID )
                    {
                    // InternalContractSpec.g:1172:5: (otherlv_10= RULE_ID )
                    // InternalContractSpec.g:1173:6: otherlv_10= RULE_ID
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
    // InternalContractSpec.g:1189:1: entryRuleEventExpr returns [EObject current=null] : iv_ruleEventExpr= ruleEventExpr EOF ;
    public final EObject entryRuleEventExpr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventExpr = null;


        try {
            // InternalContractSpec.g:1189:50: (iv_ruleEventExpr= ruleEventExpr EOF )
            // InternalContractSpec.g:1190:2: iv_ruleEventExpr= ruleEventExpr EOF
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
    // InternalContractSpec.g:1196:1: ruleEventExpr returns [EObject current=null] : ( ( (lv_event_0_0= ruleEventSpec ) ) | (otherlv_1= '(' ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' ) | (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' ) ) ;
    public final EObject ruleEventExpr() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_event_0_0 = null;

        EObject lv_events_2_0 = null;

        EObject lv_events_5_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1202:2: ( ( ( (lv_event_0_0= ruleEventSpec ) ) | (otherlv_1= '(' ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' ) | (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' ) ) )
            // InternalContractSpec.g:1203:2: ( ( (lv_event_0_0= ruleEventSpec ) ) | (otherlv_1= '(' ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' ) | (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' ) )
            {
            // InternalContractSpec.g:1203:2: ( ( (lv_event_0_0= ruleEventSpec ) ) | (otherlv_1= '(' ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' ) | (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' ) )
            int alt17=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt17=1;
                }
                break;
            case 29:
                {
                alt17=2;
                }
                break;
            case 33:
                {
                alt17=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // InternalContractSpec.g:1204:3: ( (lv_event_0_0= ruleEventSpec ) )
                    {
                    // InternalContractSpec.g:1204:3: ( (lv_event_0_0= ruleEventSpec ) )
                    // InternalContractSpec.g:1205:4: (lv_event_0_0= ruleEventSpec )
                    {
                    // InternalContractSpec.g:1205:4: (lv_event_0_0= ruleEventSpec )
                    // InternalContractSpec.g:1206:5: lv_event_0_0= ruleEventSpec
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
                    // InternalContractSpec.g:1224:3: (otherlv_1= '(' ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' )
                    {
                    // InternalContractSpec.g:1224:3: (otherlv_1= '(' ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')' )
                    // InternalContractSpec.g:1225:4: otherlv_1= '(' ( (lv_events_2_0= ruleEventList ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,29,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getEventExprAccess().getLeftParenthesisKeyword_1_0());
                    			
                    // InternalContractSpec.g:1229:4: ( (lv_events_2_0= ruleEventList ) )
                    // InternalContractSpec.g:1230:5: (lv_events_2_0= ruleEventList )
                    {
                    // InternalContractSpec.g:1230:5: (lv_events_2_0= ruleEventList )
                    // InternalContractSpec.g:1231:6: lv_events_2_0= ruleEventList
                    {

                    						newCompositeNode(grammarAccess.getEventExprAccess().getEventsEventListParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_27);
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
                    // InternalContractSpec.g:1254:3: (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' )
                    {
                    // InternalContractSpec.g:1254:3: (otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}' )
                    // InternalContractSpec.g:1255:4: otherlv_4= '{' ( (lv_events_5_0= ruleEventList ) ) otherlv_6= '}'
                    {
                    otherlv_4=(Token)match(input,33,FOLLOW_9); 

                    				newLeafNode(otherlv_4, grammarAccess.getEventExprAccess().getLeftCurlyBracketKeyword_2_0());
                    			
                    // InternalContractSpec.g:1259:4: ( (lv_events_5_0= ruleEventList ) )
                    // InternalContractSpec.g:1260:5: (lv_events_5_0= ruleEventList )
                    {
                    // InternalContractSpec.g:1260:5: (lv_events_5_0= ruleEventList )
                    // InternalContractSpec.g:1261:6: lv_events_5_0= ruleEventList
                    {

                    						newCompositeNode(grammarAccess.getEventExprAccess().getEventsEventListParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_28);
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
    // InternalContractSpec.g:1287:1: entryRuleEventList returns [EObject current=null] : iv_ruleEventList= ruleEventList EOF ;
    public final EObject entryRuleEventList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventList = null;


        try {
            // InternalContractSpec.g:1287:50: (iv_ruleEventList= ruleEventList EOF )
            // InternalContractSpec.g:1288:2: iv_ruleEventList= ruleEventList EOF
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
    // InternalContractSpec.g:1294:1: ruleEventList returns [EObject current=null] : ( ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )* ) ;
    public final EObject ruleEventList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_events_0_0 = null;

        EObject lv_events_2_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1300:2: ( ( ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )* ) )
            // InternalContractSpec.g:1301:2: ( ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )* )
            {
            // InternalContractSpec.g:1301:2: ( ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )* )
            // InternalContractSpec.g:1302:3: ( (lv_events_0_0= ruleEventSpec ) ) (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )*
            {
            // InternalContractSpec.g:1302:3: ( (lv_events_0_0= ruleEventSpec ) )
            // InternalContractSpec.g:1303:4: (lv_events_0_0= ruleEventSpec )
            {
            // InternalContractSpec.g:1303:4: (lv_events_0_0= ruleEventSpec )
            // InternalContractSpec.g:1304:5: lv_events_0_0= ruleEventSpec
            {

            					newCompositeNode(grammarAccess.getEventListAccess().getEventsEventSpecParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_29);
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

            // InternalContractSpec.g:1321:3: (otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==30) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalContractSpec.g:1322:4: otherlv_1= ',' ( (lv_events_2_0= ruleEventSpec ) )
            	    {
            	    otherlv_1=(Token)match(input,30,FOLLOW_9); 

            	    				newLeafNode(otherlv_1, grammarAccess.getEventListAccess().getCommaKeyword_1_0());
            	    			
            	    // InternalContractSpec.g:1326:4: ( (lv_events_2_0= ruleEventSpec ) )
            	    // InternalContractSpec.g:1327:5: (lv_events_2_0= ruleEventSpec )
            	    {
            	    // InternalContractSpec.g:1327:5: (lv_events_2_0= ruleEventSpec )
            	    // InternalContractSpec.g:1328:6: lv_events_2_0= ruleEventSpec
            	    {

            	    						newCompositeNode(grammarAccess.getEventListAccess().getEventsEventSpecParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_29);
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
            	    break loop18;
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
    // InternalContractSpec.g:1350:1: entryRuleEventSpec returns [EObject current=null] : iv_ruleEventSpec= ruleEventSpec EOF ;
    public final EObject entryRuleEventSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventSpec = null;


        try {
            // InternalContractSpec.g:1350:50: (iv_ruleEventSpec= ruleEventSpec EOF )
            // InternalContractSpec.g:1351:2: iv_ruleEventSpec= ruleEventSpec EOF
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
    // InternalContractSpec.g:1357:1: ruleEventSpec returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )? ) ;
    public final EObject ruleEventSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_eventValue_2_0=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1363:2: ( ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )? ) )
            // InternalContractSpec.g:1364:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )? )
            {
            // InternalContractSpec.g:1364:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )? )
            // InternalContractSpec.g:1365:3: ( (otherlv_0= RULE_ID ) ) (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )?
            {
            // InternalContractSpec.g:1365:3: ( (otherlv_0= RULE_ID ) )
            // InternalContractSpec.g:1366:4: (otherlv_0= RULE_ID )
            {
            // InternalContractSpec.g:1366:4: (otherlv_0= RULE_ID )
            // InternalContractSpec.g:1367:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEventSpecRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_30); 

            					newLeafNode(otherlv_0, grammarAccess.getEventSpecAccess().getPortPortCrossReference_0_0());
            				

            }


            }

            // InternalContractSpec.g:1378:3: (otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==35) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalContractSpec.g:1379:4: otherlv_1= '.' ( (lv_eventValue_2_0= RULE_ID ) )
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getEventSpecAccess().getFullStopKeyword_1_0());
                    			
                    // InternalContractSpec.g:1383:4: ( (lv_eventValue_2_0= RULE_ID ) )
                    // InternalContractSpec.g:1384:5: (lv_eventValue_2_0= RULE_ID )
                    {
                    // InternalContractSpec.g:1384:5: (lv_eventValue_2_0= RULE_ID )
                    // InternalContractSpec.g:1385:6: lv_eventValue_2_0= RULE_ID
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
    // InternalContractSpec.g:1406:1: entryRuleInterval returns [EObject current=null] : iv_ruleInterval= ruleInterval EOF ;
    public final EObject entryRuleInterval() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInterval = null;


        try {
            // InternalContractSpec.g:1406:49: (iv_ruleInterval= ruleInterval EOF )
            // InternalContractSpec.g:1407:2: iv_ruleInterval= ruleInterval EOF
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
    // InternalContractSpec.g:1413:1: ruleInterval returns [EObject current=null] : ( ( (lv_time_0_0= ruleTimeExpr ) ) | ( ( (lv_b1_1_0= ruleBoundary ) ) ( (lv_v1_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_v2_4_0= ruleValue ) ) ( (lv_b2_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) ) ) ;
    public final EObject ruleInterval() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        EObject lv_time_0_0 = null;

        AntlrDatatypeRuleToken lv_b1_1_0 = null;

        EObject lv_v1_2_0 = null;

        EObject lv_v2_4_0 = null;

        AntlrDatatypeRuleToken lv_b2_5_0 = null;

        AntlrDatatypeRuleToken lv_unit_6_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1419:2: ( ( ( (lv_time_0_0= ruleTimeExpr ) ) | ( ( (lv_b1_1_0= ruleBoundary ) ) ( (lv_v1_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_v2_4_0= ruleValue ) ) ( (lv_b2_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) ) ) )
            // InternalContractSpec.g:1420:2: ( ( (lv_time_0_0= ruleTimeExpr ) ) | ( ( (lv_b1_1_0= ruleBoundary ) ) ( (lv_v1_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_v2_4_0= ruleValue ) ) ( (lv_b2_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) ) )
            {
            // InternalContractSpec.g:1420:2: ( ( (lv_time_0_0= ruleTimeExpr ) ) | ( ( (lv_b1_1_0= ruleBoundary ) ) ( (lv_v1_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_v2_4_0= ruleValue ) ) ( (lv_b2_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) ) )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_INT) ) {
                alt20=1;
            }
            else if ( ((LA20_0>=36 && LA20_0<=37)) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // InternalContractSpec.g:1421:3: ( (lv_time_0_0= ruleTimeExpr ) )
                    {
                    // InternalContractSpec.g:1421:3: ( (lv_time_0_0= ruleTimeExpr ) )
                    // InternalContractSpec.g:1422:4: (lv_time_0_0= ruleTimeExpr )
                    {
                    // InternalContractSpec.g:1422:4: (lv_time_0_0= ruleTimeExpr )
                    // InternalContractSpec.g:1423:5: lv_time_0_0= ruleTimeExpr
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
                    // InternalContractSpec.g:1441:3: ( ( (lv_b1_1_0= ruleBoundary ) ) ( (lv_v1_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_v2_4_0= ruleValue ) ) ( (lv_b2_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) )
                    {
                    // InternalContractSpec.g:1441:3: ( ( (lv_b1_1_0= ruleBoundary ) ) ( (lv_v1_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_v2_4_0= ruleValue ) ) ( (lv_b2_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) ) )
                    // InternalContractSpec.g:1442:4: ( (lv_b1_1_0= ruleBoundary ) ) ( (lv_v1_2_0= ruleValue ) ) otherlv_3= ',' ( (lv_v2_4_0= ruleValue ) ) ( (lv_b2_5_0= ruleBoundary ) ) ( (lv_unit_6_0= ruleUnit ) )
                    {
                    // InternalContractSpec.g:1442:4: ( (lv_b1_1_0= ruleBoundary ) )
                    // InternalContractSpec.g:1443:5: (lv_b1_1_0= ruleBoundary )
                    {
                    // InternalContractSpec.g:1443:5: (lv_b1_1_0= ruleBoundary )
                    // InternalContractSpec.g:1444:6: lv_b1_1_0= ruleBoundary
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getB1BoundaryParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_15);
                    lv_b1_1_0=ruleBoundary();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"b1",
                    							lv_b1_1_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Boundary");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalContractSpec.g:1461:4: ( (lv_v1_2_0= ruleValue ) )
                    // InternalContractSpec.g:1462:5: (lv_v1_2_0= ruleValue )
                    {
                    // InternalContractSpec.g:1462:5: (lv_v1_2_0= ruleValue )
                    // InternalContractSpec.g:1463:6: lv_v1_2_0= ruleValue
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getV1ValueParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_v1_2_0=ruleValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"v1",
                    							lv_v1_2_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,30,FOLLOW_15); 

                    				newLeafNode(otherlv_3, grammarAccess.getIntervalAccess().getCommaKeyword_1_2());
                    			
                    // InternalContractSpec.g:1484:4: ( (lv_v2_4_0= ruleValue ) )
                    // InternalContractSpec.g:1485:5: (lv_v2_4_0= ruleValue )
                    {
                    // InternalContractSpec.g:1485:5: (lv_v2_4_0= ruleValue )
                    // InternalContractSpec.g:1486:6: lv_v2_4_0= ruleValue
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getV2ValueParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_6);
                    lv_v2_4_0=ruleValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"v2",
                    							lv_v2_4_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Value");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalContractSpec.g:1503:4: ( (lv_b2_5_0= ruleBoundary ) )
                    // InternalContractSpec.g:1504:5: (lv_b2_5_0= ruleBoundary )
                    {
                    // InternalContractSpec.g:1504:5: (lv_b2_5_0= ruleBoundary )
                    // InternalContractSpec.g:1505:6: lv_b2_5_0= ruleBoundary
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getB2BoundaryParserRuleCall_1_4_0());
                    					
                    pushFollow(FOLLOW_31);
                    lv_b2_5_0=ruleBoundary();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIntervalRule());
                    						}
                    						set(
                    							current,
                    							"b2",
                    							lv_b2_5_0,
                    							"org.eclipse.fordiac.ide.ContractSpec.Boundary");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalContractSpec.g:1522:4: ( (lv_unit_6_0= ruleUnit ) )
                    // InternalContractSpec.g:1523:5: (lv_unit_6_0= ruleUnit )
                    {
                    // InternalContractSpec.g:1523:5: (lv_unit_6_0= ruleUnit )
                    // InternalContractSpec.g:1524:6: lv_unit_6_0= ruleUnit
                    {

                    						newCompositeNode(grammarAccess.getIntervalAccess().getUnitUnitParserRuleCall_1_5_0());
                    					
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
    // InternalContractSpec.g:1546:1: entryRuleTimeExpr returns [EObject current=null] : iv_ruleTimeExpr= ruleTimeExpr EOF ;
    public final EObject entryRuleTimeExpr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeExpr = null;


        try {
            // InternalContractSpec.g:1546:49: (iv_ruleTimeExpr= ruleTimeExpr EOF )
            // InternalContractSpec.g:1547:2: iv_ruleTimeExpr= ruleTimeExpr EOF
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
    // InternalContractSpec.g:1553:1: ruleTimeExpr returns [EObject current=null] : ( ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) ) ) ;
    public final EObject ruleTimeExpr() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;

        AntlrDatatypeRuleToken lv_unit_1_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1559:2: ( ( ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) ) ) )
            // InternalContractSpec.g:1560:2: ( ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) ) )
            {
            // InternalContractSpec.g:1560:2: ( ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) ) )
            // InternalContractSpec.g:1561:3: ( (lv_value_0_0= ruleValue ) ) ( (lv_unit_1_0= ruleUnit ) )
            {
            // InternalContractSpec.g:1561:3: ( (lv_value_0_0= ruleValue ) )
            // InternalContractSpec.g:1562:4: (lv_value_0_0= ruleValue )
            {
            // InternalContractSpec.g:1562:4: (lv_value_0_0= ruleValue )
            // InternalContractSpec.g:1563:5: lv_value_0_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getTimeExprAccess().getValueValueParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_31);
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

            // InternalContractSpec.g:1580:3: ( (lv_unit_1_0= ruleUnit ) )
            // InternalContractSpec.g:1581:4: (lv_unit_1_0= ruleUnit )
            {
            // InternalContractSpec.g:1581:4: (lv_unit_1_0= ruleUnit )
            // InternalContractSpec.g:1582:5: lv_unit_1_0= ruleUnit
            {

            					newCompositeNode(grammarAccess.getTimeExprAccess().getUnitUnitParserRuleCall_1_0());
            				
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
    // InternalContractSpec.g:1603:1: entryRuleBoundary returns [String current=null] : iv_ruleBoundary= ruleBoundary EOF ;
    public final String entryRuleBoundary() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoundary = null;


        try {
            // InternalContractSpec.g:1603:48: (iv_ruleBoundary= ruleBoundary EOF )
            // InternalContractSpec.g:1604:2: iv_ruleBoundary= ruleBoundary EOF
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
    // InternalContractSpec.g:1610:1: ruleBoundary returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' | kw= ']' ) ;
    public final AntlrDatatypeRuleToken ruleBoundary() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1616:2: ( (kw= '[' | kw= ']' ) )
            // InternalContractSpec.g:1617:2: (kw= '[' | kw= ']' )
            {
            // InternalContractSpec.g:1617:2: (kw= '[' | kw= ']' )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==36) ) {
                alt21=1;
            }
            else if ( (LA21_0==37) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalContractSpec.g:1618:3: kw= '['
                    {
                    kw=(Token)match(input,36,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoundaryAccess().getLeftSquareBracketKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:1624:3: kw= ']'
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
    // InternalContractSpec.g:1633:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalContractSpec.g:1633:46: (iv_ruleValue= ruleValue EOF )
            // InternalContractSpec.g:1634:2: iv_ruleValue= ruleValue EOF
            {
             newCompositeNode(grammarAccess.getValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleValue=ruleValue();

            state._fsp--;

             current =iv_ruleValue; 
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
    // InternalContractSpec.g:1640:1: ruleValue returns [EObject current=null] : ( ( (lv_integer_0_0= RULE_INT ) ) (otherlv_1= '.' ( (lv_fraction_2_0= RULE_INT ) ) )? ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        Token lv_integer_0_0=null;
        Token otherlv_1=null;
        Token lv_fraction_2_0=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1646:2: ( ( ( (lv_integer_0_0= RULE_INT ) ) (otherlv_1= '.' ( (lv_fraction_2_0= RULE_INT ) ) )? ) )
            // InternalContractSpec.g:1647:2: ( ( (lv_integer_0_0= RULE_INT ) ) (otherlv_1= '.' ( (lv_fraction_2_0= RULE_INT ) ) )? )
            {
            // InternalContractSpec.g:1647:2: ( ( (lv_integer_0_0= RULE_INT ) ) (otherlv_1= '.' ( (lv_fraction_2_0= RULE_INT ) ) )? )
            // InternalContractSpec.g:1648:3: ( (lv_integer_0_0= RULE_INT ) ) (otherlv_1= '.' ( (lv_fraction_2_0= RULE_INT ) ) )?
            {
            // InternalContractSpec.g:1648:3: ( (lv_integer_0_0= RULE_INT ) )
            // InternalContractSpec.g:1649:4: (lv_integer_0_0= RULE_INT )
            {
            // InternalContractSpec.g:1649:4: (lv_integer_0_0= RULE_INT )
            // InternalContractSpec.g:1650:5: lv_integer_0_0= RULE_INT
            {
            lv_integer_0_0=(Token)match(input,RULE_INT,FOLLOW_30); 

            					newLeafNode(lv_integer_0_0, grammarAccess.getValueAccess().getIntegerINTTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getValueRule());
            					}
            					setWithLastConsumed(
            						current,
            						"integer",
            						lv_integer_0_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalContractSpec.g:1666:3: (otherlv_1= '.' ( (lv_fraction_2_0= RULE_INT ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==35) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalContractSpec.g:1667:4: otherlv_1= '.' ( (lv_fraction_2_0= RULE_INT ) )
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_15); 

                    				newLeafNode(otherlv_1, grammarAccess.getValueAccess().getFullStopKeyword_1_0());
                    			
                    // InternalContractSpec.g:1671:4: ( (lv_fraction_2_0= RULE_INT ) )
                    // InternalContractSpec.g:1672:5: (lv_fraction_2_0= RULE_INT )
                    {
                    // InternalContractSpec.g:1672:5: (lv_fraction_2_0= RULE_INT )
                    // InternalContractSpec.g:1673:6: lv_fraction_2_0= RULE_INT
                    {
                    lv_fraction_2_0=(Token)match(input,RULE_INT,FOLLOW_2); 

                    						newLeafNode(lv_fraction_2_0, grammarAccess.getValueAccess().getFractionINTTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getValueRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"fraction",
                    							lv_fraction_2_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

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
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleUnit"
    // InternalContractSpec.g:1694:1: entryRuleUnit returns [String current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final String entryRuleUnit() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUnit = null;


        try {
            // InternalContractSpec.g:1694:44: (iv_ruleUnit= ruleUnit EOF )
            // InternalContractSpec.g:1695:2: iv_ruleUnit= ruleUnit EOF
            {
             newCompositeNode(grammarAccess.getUnitRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUnit=ruleUnit();

            state._fsp--;

             current =iv_ruleUnit.getText(); 
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
    // $ANTLR end "entryRuleUnit"


    // $ANTLR start "ruleUnit"
    // InternalContractSpec.g:1701:1: ruleUnit returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 's' | kw= 'ms' | kw= 'us' | kw= 'ns' ) ;
    public final AntlrDatatypeRuleToken ruleUnit() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1707:2: ( (kw= 's' | kw= 'ms' | kw= 'us' | kw= 'ns' ) )
            // InternalContractSpec.g:1708:2: (kw= 's' | kw= 'ms' | kw= 'us' | kw= 'ns' )
            {
            // InternalContractSpec.g:1708:2: (kw= 's' | kw= 'ms' | kw= 'us' | kw= 'ns' )
            int alt23=4;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt23=1;
                }
                break;
            case 39:
                {
                alt23=2;
                }
                break;
            case 40:
                {
                alt23=3;
                }
                break;
            case 41:
                {
                alt23=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // InternalContractSpec.g:1709:3: kw= 's'
                    {
                    kw=(Token)match(input,38,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getUnitAccess().getSKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:1715:3: kw= 'ms'
                    {
                    kw=(Token)match(input,39,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getUnitAccess().getMsKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalContractSpec.g:1721:3: kw= 'us'
                    {
                    kw=(Token)match(input,40,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getUnitAccess().getUsKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalContractSpec.g:1727:3: kw= 'ns'
                    {
                    kw=(Token)match(input,41,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getUnitAccess().getNsKeyword_3());
                    		

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


    // $ANTLR start "entryRuleCausalFuncDecl"
    // InternalContractSpec.g:1736:1: entryRuleCausalFuncDecl returns [EObject current=null] : iv_ruleCausalFuncDecl= ruleCausalFuncDecl EOF ;
    public final EObject entryRuleCausalFuncDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCausalFuncDecl = null;


        try {
            // InternalContractSpec.g:1736:55: (iv_ruleCausalFuncDecl= ruleCausalFuncDecl EOF )
            // InternalContractSpec.g:1737:2: iv_ruleCausalFuncDecl= ruleCausalFuncDecl EOF
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
    // InternalContractSpec.g:1743:1: ruleCausalFuncDecl returns [EObject current=null] : ( ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) ) ) ;
    public final EObject ruleCausalFuncDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_funcName_0_0 = null;

        AntlrDatatypeRuleToken lv_relation_7_0 = null;



        	enterRule();

        try {
            // InternalContractSpec.g:1749:2: ( ( ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) ) ) )
            // InternalContractSpec.g:1750:2: ( ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) ) )
            {
            // InternalContractSpec.g:1750:2: ( ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) ) )
            // InternalContractSpec.g:1751:3: ( (lv_funcName_0_0= ruleCausalFuncName ) ) otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) otherlv_5= ')' otherlv_6= ':=' ( (lv_relation_7_0= ruleCausalRelation ) )
            {
            // InternalContractSpec.g:1751:3: ( (lv_funcName_0_0= ruleCausalFuncName ) )
            // InternalContractSpec.g:1752:4: (lv_funcName_0_0= ruleCausalFuncName )
            {
            // InternalContractSpec.g:1752:4: (lv_funcName_0_0= ruleCausalFuncName )
            // InternalContractSpec.g:1753:5: lv_funcName_0_0= ruleCausalFuncName
            {

            					newCompositeNode(grammarAccess.getCausalFuncDeclAccess().getFuncNameCausalFuncNameParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_25);
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
            		
            // InternalContractSpec.g:1774:3: ( (otherlv_2= RULE_ID ) )
            // InternalContractSpec.g:1775:4: (otherlv_2= RULE_ID )
            {
            // InternalContractSpec.g:1775:4: (otherlv_2= RULE_ID )
            // InternalContractSpec.g:1776:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCausalFuncDeclRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(otherlv_2, grammarAccess.getCausalFuncDeclAccess().getP1PortCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,30,FOLLOW_9); 

            			newLeafNode(otherlv_3, grammarAccess.getCausalFuncDeclAccess().getCommaKeyword_3());
            		
            // InternalContractSpec.g:1791:3: ( (otherlv_4= RULE_ID ) )
            // InternalContractSpec.g:1792:4: (otherlv_4= RULE_ID )
            {
            // InternalContractSpec.g:1792:4: (otherlv_4= RULE_ID )
            // InternalContractSpec.g:1793:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCausalFuncDeclRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_27); 

            					newLeafNode(otherlv_4, grammarAccess.getCausalFuncDeclAccess().getP2PortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,31,FOLLOW_32); 

            			newLeafNode(otherlv_5, grammarAccess.getCausalFuncDeclAccess().getRightParenthesisKeyword_5());
            		
            otherlv_6=(Token)match(input,42,FOLLOW_33); 

            			newLeafNode(otherlv_6, grammarAccess.getCausalFuncDeclAccess().getColonEqualsSignKeyword_6());
            		
            // InternalContractSpec.g:1812:3: ( (lv_relation_7_0= ruleCausalRelation ) )
            // InternalContractSpec.g:1813:4: (lv_relation_7_0= ruleCausalRelation )
            {
            // InternalContractSpec.g:1813:4: (lv_relation_7_0= ruleCausalRelation )
            // InternalContractSpec.g:1814:5: lv_relation_7_0= ruleCausalRelation
            {

            					newCompositeNode(grammarAccess.getCausalFuncDeclAccess().getRelationCausalRelationParserRuleCall_7_0());
            				
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


    // $ANTLR start "entryRuleCausalFuncName"
    // InternalContractSpec.g:1835:1: entryRuleCausalFuncName returns [String current=null] : iv_ruleCausalFuncName= ruleCausalFuncName EOF ;
    public final String entryRuleCausalFuncName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCausalFuncName = null;


        try {
            // InternalContractSpec.g:1835:54: (iv_ruleCausalFuncName= ruleCausalFuncName EOF )
            // InternalContractSpec.g:1836:2: iv_ruleCausalFuncName= ruleCausalFuncName EOF
            {
             newCompositeNode(grammarAccess.getCausalFuncNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCausalFuncName=ruleCausalFuncName();

            state._fsp--;

             current =iv_ruleCausalFuncName.getText(); 
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
    // $ANTLR end "entryRuleCausalFuncName"


    // $ANTLR start "ruleCausalFuncName"
    // InternalContractSpec.g:1842:1: ruleCausalFuncName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '|>' | kw= '<|' ) ;
    public final AntlrDatatypeRuleToken ruleCausalFuncName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1848:2: ( (kw= '|>' | kw= '<|' ) )
            // InternalContractSpec.g:1849:2: (kw= '|>' | kw= '<|' )
            {
            // InternalContractSpec.g:1849:2: (kw= '|>' | kw= '<|' )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==43) ) {
                alt24=1;
            }
            else if ( (LA24_0==44) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // InternalContractSpec.g:1850:3: kw= '|>'
                    {
                    kw=(Token)match(input,43,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getCausalFuncNameAccess().getVerticalLineGreaterThanSignKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:1856:3: kw= '<|'
                    {
                    kw=(Token)match(input,44,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getCausalFuncNameAccess().getLessThanSignVerticalLineKeyword_1());
                    		

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


    // $ANTLR start "entryRuleCausalRelation"
    // InternalContractSpec.g:1865:1: entryRuleCausalRelation returns [String current=null] : iv_ruleCausalRelation= ruleCausalRelation EOF ;
    public final String entryRuleCausalRelation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCausalRelation = null;


        try {
            // InternalContractSpec.g:1865:54: (iv_ruleCausalRelation= ruleCausalRelation EOF )
            // InternalContractSpec.g:1866:2: iv_ruleCausalRelation= ruleCausalRelation EOF
            {
             newCompositeNode(grammarAccess.getCausalRelationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCausalRelation=ruleCausalRelation();

            state._fsp--;

             current =iv_ruleCausalRelation.getText(); 
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
    // $ANTLR end "entryRuleCausalRelation"


    // $ANTLR start "ruleCausalRelation"
    // InternalContractSpec.g:1872:1: ruleCausalRelation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'FIFO' | kw= 'LIFO' | kw= 'ID' ) ;
    public final AntlrDatatypeRuleToken ruleCausalRelation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalContractSpec.g:1878:2: ( (kw= 'FIFO' | kw= 'LIFO' | kw= 'ID' ) )
            // InternalContractSpec.g:1879:2: (kw= 'FIFO' | kw= 'LIFO' | kw= 'ID' )
            {
            // InternalContractSpec.g:1879:2: (kw= 'FIFO' | kw= 'LIFO' | kw= 'ID' )
            int alt25=3;
            switch ( input.LA(1) ) {
            case 45:
                {
                alt25=1;
                }
                break;
            case 46:
                {
                alt25=2;
                }
                break;
            case 47:
                {
                alt25=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // InternalContractSpec.g:1880:3: kw= 'FIFO'
                    {
                    kw=(Token)match(input,45,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getCausalRelationAccess().getFIFOKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalContractSpec.g:1886:3: kw= 'LIFO'
                    {
                    kw=(Token)match(input,46,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getCausalRelationAccess().getLIFOKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalContractSpec.g:1892:3: kw= 'ID'
                    {
                    kw=(Token)match(input,47,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getCausalRelationAccess().getIDKeyword_2());
                    		

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


    // $ANTLR start "entryRuleClockDefinition"
    // InternalContractSpec.g:1901:1: entryRuleClockDefinition returns [EObject current=null] : iv_ruleClockDefinition= ruleClockDefinition EOF ;
    public final EObject entryRuleClockDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClockDefinition = null;


        try {
            // InternalContractSpec.g:1901:56: (iv_ruleClockDefinition= ruleClockDefinition EOF )
            // InternalContractSpec.g:1902:2: iv_ruleClockDefinition= ruleClockDefinition EOF
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
    // InternalContractSpec.g:1908:1: ruleClockDefinition returns [EObject current=null] : (otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )? ) ;
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
            // InternalContractSpec.g:1914:2: ( (otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )? ) )
            // InternalContractSpec.g:1915:2: (otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )? )
            {
            // InternalContractSpec.g:1915:2: (otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )? )
            // InternalContractSpec.g:1916:3: otherlv_0= 'Clock' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'has' (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )? (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )? (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )? (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )?
            {
            otherlv_0=(Token)match(input,48,FOLLOW_9); 

            			newLeafNode(otherlv_0, grammarAccess.getClockDefinitionAccess().getClockKeyword_0());
            		
            // InternalContractSpec.g:1920:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalContractSpec.g:1921:4: (lv_name_1_0= RULE_ID )
            {
            // InternalContractSpec.g:1921:4: (lv_name_1_0= RULE_ID )
            // InternalContractSpec.g:1922:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_23); 

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

            otherlv_2=(Token)match(input,26,FOLLOW_34); 

            			newLeafNode(otherlv_2, grammarAccess.getClockDefinitionAccess().getHasKeyword_2());
            		
            // InternalContractSpec.g:1942:3: (otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==49) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalContractSpec.g:1943:4: otherlv_3= 'resolution' ( (lv_resolution_4_0= ruleTimeExpr ) )
                    {
                    otherlv_3=(Token)match(input,49,FOLLOW_15); 

                    				newLeafNode(otherlv_3, grammarAccess.getClockDefinitionAccess().getResolutionKeyword_3_0());
                    			
                    // InternalContractSpec.g:1947:4: ( (lv_resolution_4_0= ruleTimeExpr ) )
                    // InternalContractSpec.g:1948:5: (lv_resolution_4_0= ruleTimeExpr )
                    {
                    // InternalContractSpec.g:1948:5: (lv_resolution_4_0= ruleTimeExpr )
                    // InternalContractSpec.g:1949:6: lv_resolution_4_0= ruleTimeExpr
                    {

                    						newCompositeNode(grammarAccess.getClockDefinitionAccess().getResolutionTimeExprParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_35);
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

            // InternalContractSpec.g:1967:3: (otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==50) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalContractSpec.g:1968:4: otherlv_5= 'skew' ( (lv_skew_6_0= ruleTimeExpr ) )
                    {
                    otherlv_5=(Token)match(input,50,FOLLOW_15); 

                    				newLeafNode(otherlv_5, grammarAccess.getClockDefinitionAccess().getSkewKeyword_4_0());
                    			
                    // InternalContractSpec.g:1972:4: ( (lv_skew_6_0= ruleTimeExpr ) )
                    // InternalContractSpec.g:1973:5: (lv_skew_6_0= ruleTimeExpr )
                    {
                    // InternalContractSpec.g:1973:5: (lv_skew_6_0= ruleTimeExpr )
                    // InternalContractSpec.g:1974:6: lv_skew_6_0= ruleTimeExpr
                    {

                    						newCompositeNode(grammarAccess.getClockDefinitionAccess().getSkewTimeExprParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_36);
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

            // InternalContractSpec.g:1992:3: (otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==51) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalContractSpec.g:1993:4: otherlv_7= 'drift' ( (lv_drift_8_0= ruleInterval ) )
                    {
                    otherlv_7=(Token)match(input,51,FOLLOW_6); 

                    				newLeafNode(otherlv_7, grammarAccess.getClockDefinitionAccess().getDriftKeyword_5_0());
                    			
                    // InternalContractSpec.g:1997:4: ( (lv_drift_8_0= ruleInterval ) )
                    // InternalContractSpec.g:1998:5: (lv_drift_8_0= ruleInterval )
                    {
                    // InternalContractSpec.g:1998:5: (lv_drift_8_0= ruleInterval )
                    // InternalContractSpec.g:1999:6: lv_drift_8_0= ruleInterval
                    {

                    						newCompositeNode(grammarAccess.getClockDefinitionAccess().getDriftIntervalParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_37);
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

            // InternalContractSpec.g:2017:3: (otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==52) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalContractSpec.g:2018:4: otherlv_9= 'maxdiff' ( (lv_maxdiff_10_0= ruleTimeExpr ) )
                    {
                    otherlv_9=(Token)match(input,52,FOLLOW_15); 

                    				newLeafNode(otherlv_9, grammarAccess.getClockDefinitionAccess().getMaxdiffKeyword_6_0());
                    			
                    // InternalContractSpec.g:2022:4: ( (lv_maxdiff_10_0= ruleTimeExpr ) )
                    // InternalContractSpec.g:2023:5: (lv_maxdiff_10_0= ruleTimeExpr )
                    {
                    // InternalContractSpec.g:2023:5: (lv_maxdiff_10_0= ruleTimeExpr )
                    // InternalContractSpec.g:2024:6: lv_maxdiff_10_0= ruleTimeExpr
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

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    static final String dfa_1s = "\76\uffff";
    static final String dfa_2s = "\1\4\1\13\1\4\4\uffff\2\4\1\14\1\13\2\4\2\13\2\uffff\1\4\1\25\2\36\1\4\1\13\3\4\1\13\2\4\3\13\2\4\4\36\1\4\2\uffff\2\36\2\4\1\13\2\4\1\13\2\4\1\13\6\36\2\4\2\36";
    static final String dfa_3s = "\1\60\1\43\1\41\4\uffff\2\4\1\17\1\43\2\4\1\36\1\43\2\uffff\1\4\1\25\2\43\1\4\1\13\1\41\2\4\1\13\2\4\1\13\1\36\1\43\2\4\1\37\1\43\1\42\1\43\1\4\2\uffff\2\43\2\4\1\32\2\4\1\32\2\4\1\32\1\37\1\42\1\37\1\43\1\42\1\43\2\4\1\37\1\42";
    static final String dfa_4s = "\3\uffff\1\5\1\6\1\7\1\10\10\uffff\1\2\1\1\26\uffff\1\4\1\3\25\uffff";
    static final String dfa_5s = "\76\uffff}>";
    static final String[] dfa_6s = {
            "\1\1\17\uffff\1\2\7\uffff\1\3\3\uffff\1\4\12\uffff\2\5\3\uffff\1\6",
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
            return "124:2: (this_SingleEvent_0= ruleSingleEvent | this_Repetition_1= ruleRepetition | this_Reaction_2= ruleReaction | this_Age_3= ruleAge | this_CausalReaction_4= ruleCausalReaction | this_CausalAge_5= ruleCausalAge | this_CausalFuncDecl_6= ruleCausalFuncDecl | this_ClockDefinition_7= ruleClockDefinition )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0001180110100012L});
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
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000002022L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x000003C000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000E00000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x001E000000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x001C000000000002L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0018000000000002L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0010000000000002L});

}