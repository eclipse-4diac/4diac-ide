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
parser grammar InternalSTAlgorithmParser;

options {
	tokenVocab=InternalSTAlgorithmLexer;
	superClass=AbstractInternalAntlrParser;
}

@header {
package org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.internal;

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
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess;

}

@members {

 	private STAlgorithmGrammarAccess grammarAccess;

    public InternalSTAlgorithmParser(TokenStream input, STAlgorithmGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "STAlgorithmSource";
   	}

   	@Override
   	protected STAlgorithmGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleSTAlgorithmSource
entryRuleSTAlgorithmSource returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAlgorithmSourceRule()); }
	iv_ruleSTAlgorithmSource=ruleSTAlgorithmSource
	{ $current=$iv_ruleSTAlgorithmSource.current; }
	EOF;

// Rule STAlgorithmSource
ruleSTAlgorithmSource returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTAlgorithmSourceAccess().getSTAlgorithmSourceAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getSTAlgorithmSourceAccess().getElementsSTAlgorithmSourceElementParserRuleCall_1_0());
				}
				lv_elements_1_0=ruleSTAlgorithmSourceElement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTAlgorithmSourceRule());
					}
					add(
						$current,
						"elements",
						lv_elements_1_0,
						"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm.STAlgorithmSourceElement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleSTAlgorithmSourceElement
entryRuleSTAlgorithmSourceElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAlgorithmSourceElementRule()); }
	iv_ruleSTAlgorithmSourceElement=ruleSTAlgorithmSourceElement
	{ $current=$iv_ruleSTAlgorithmSourceElement.current; }
	EOF;

// Rule STAlgorithmSourceElement
ruleSTAlgorithmSourceElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTAlgorithmSourceElementAccess().getSTAlgorithmParserRuleCall_0());
		}
		this_STAlgorithm_0=ruleSTAlgorithm
		{
			$current = $this_STAlgorithm_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTAlgorithmSourceElementAccess().getSTMethodParserRuleCall_1());
		}
		this_STMethod_1=ruleSTMethod
		{
			$current = $this_STMethod_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTAlgorithm
entryRuleSTAlgorithm returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAlgorithmRule()); }
	iv_ruleSTAlgorithm=ruleSTAlgorithm
	{ $current=$iv_ruleSTAlgorithm.current; }
	EOF;

// Rule STAlgorithm
ruleSTAlgorithm returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=ALGORITHM
		{
			newLeafNode(otherlv_0, grammarAccess.getSTAlgorithmAccess().getALGORITHMKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getSTAlgorithmAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTAlgorithmRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getSTAlgorithmAccess().getBodySTAlgorithmBodyParserRuleCall_2_0());
				}
				lv_body_2_0=ruleSTAlgorithmBody
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTAlgorithmRule());
					}
					set(
						$current,
						"body",
						lv_body_2_0,
						"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm.STAlgorithmBody");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=END_ALGORITHM
		{
			newLeafNode(otherlv_3, grammarAccess.getSTAlgorithmAccess().getEND_ALGORITHMKeyword_3());
		}
	)
;

// Entry rule entryRuleSTAlgorithmBody
entryRuleSTAlgorithmBody returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAlgorithmBodyRule()); }
	iv_ruleSTAlgorithmBody=ruleSTAlgorithmBody
	{ $current=$iv_ruleSTAlgorithmBody.current; }
	EOF;

// Rule STAlgorithmBody
ruleSTAlgorithmBody returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTAlgorithmBodyAccess().getSTAlgorithmBodyAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getSTAlgorithmBodyAccess().getVarTempDeclarationsSTVarTempDeclarationBlockParserRuleCall_1_0());
				}
				lv_varTempDeclarations_1_0=ruleSTVarTempDeclarationBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTAlgorithmBodyRule());
					}
					add(
						$current,
						"varTempDeclarations",
						lv_varTempDeclarations_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarTempDeclarationBlock");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				{
					newCompositeNode(grammarAccess.getSTAlgorithmBodyAccess().getStatementsSTStatementParserRuleCall_2_0());
				}
				lv_statements_2_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTAlgorithmBodyRule());
					}
					add(
						$current,
						"statements",
						lv_statements_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleSTMethod
entryRuleSTMethod returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTMethodRule()); }
	iv_ruleSTMethod=ruleSTMethod
	{ $current=$iv_ruleSTMethod.current; }
	EOF;

// Rule STMethod
ruleSTMethod returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=METHOD
		{
			newLeafNode(otherlv_0, grammarAccess.getSTMethodAccess().getMETHODKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getSTMethodAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTMethodRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
				}
			)
		)
		(
			otherlv_2=Colon
			{
				newLeafNode(otherlv_2, grammarAccess.getSTMethodAccess().getColonKeyword_2_0());
			}
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTMethodRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getSTMethodAccess().getReturnTypeDataTypeCrossReference_2_1_0());
					}
					ruleSTAnyType
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getSTMethodAccess().getBodySTMethodBodyParserRuleCall_3_0());
				}
				lv_body_4_0=ruleSTMethodBody
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTMethodRule());
					}
					set(
						$current,
						"body",
						lv_body_4_0,
						"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm.STMethodBody");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_5=END_METHOD
		{
			newLeafNode(otherlv_5, grammarAccess.getSTMethodAccess().getEND_METHODKeyword_4());
		}
	)
;

// Entry rule entryRuleSTMethodBody
entryRuleSTMethodBody returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTMethodBodyRule()); }
	iv_ruleSTMethodBody=ruleSTMethodBody
	{ $current=$iv_ruleSTMethodBody.current; }
	EOF;

// Rule STMethodBody
ruleSTMethodBody returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTMethodBodyAccess().getSTMethodBodyAction_0(),
					$current);
			}
		)
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getSTMethodBodyAccess().getVarDeclarationsSTVarTempDeclarationBlockParserRuleCall_1_0_0());
					}
					lv_varDeclarations_1_1=ruleSTVarTempDeclarationBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
						}
						add(
							$current,
							"varDeclarations",
							lv_varDeclarations_1_1,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarTempDeclarationBlock");
						afterParserOrEnumRuleCall();
					}
					    |
					{
						newCompositeNode(grammarAccess.getSTMethodBodyAccess().getVarDeclarationsSTVarInputDeclarationBlockParserRuleCall_1_0_1());
					}
					lv_varDeclarations_1_2=ruleSTVarInputDeclarationBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
						}
						add(
							$current,
							"varDeclarations",
							lv_varDeclarations_1_2,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarInputDeclarationBlock");
						afterParserOrEnumRuleCall();
					}
					    |
					{
						newCompositeNode(grammarAccess.getSTMethodBodyAccess().getVarDeclarationsSTVarOutputDeclarationBlockParserRuleCall_1_0_2());
					}
					lv_varDeclarations_1_3=ruleSTVarOutputDeclarationBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
						}
						add(
							$current,
							"varDeclarations",
							lv_varDeclarations_1_3,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarOutputDeclarationBlock");
						afterParserOrEnumRuleCall();
					}
					    |
					{
						newCompositeNode(grammarAccess.getSTMethodBodyAccess().getVarDeclarationsSTVarInOutDeclarationBlockParserRuleCall_1_0_3());
					}
					lv_varDeclarations_1_4=ruleSTVarInOutDeclarationBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
						}
						add(
							$current,
							"varDeclarations",
							lv_varDeclarations_1_4,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarInOutDeclarationBlock");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		(
			(
				{
					newCompositeNode(grammarAccess.getSTMethodBodyAccess().getStatementsSTStatementParserRuleCall_2_0());
				}
				lv_statements_2_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTMethodBodyRule());
					}
					add(
						$current,
						"statements",
						lv_statements_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleSTExpressionSource
entryRuleSTExpressionSource returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTExpressionSourceRule()); }
	iv_ruleSTExpressionSource=ruleSTExpressionSource
	{ $current=$iv_ruleSTExpressionSource.current; }
	EOF;

// Rule STExpressionSource
ruleSTExpressionSource returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTExpressionSourceAccess().getSTExpressionSourceAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getSTExpressionSourceAccess().getExpressionSTExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTExpressionSourceRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleSTInitializerExpressionSource
entryRuleSTInitializerExpressionSource returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTInitializerExpressionSourceRule()); }
	iv_ruleSTInitializerExpressionSource=ruleSTInitializerExpressionSource
	{ $current=$iv_ruleSTInitializerExpressionSource.current; }
	EOF;

// Rule STInitializerExpressionSource
ruleSTInitializerExpressionSource returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTInitializerExpressionSourceAccess().getSTInitializerExpressionSourceAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getSTInitializerExpressionSourceAccess().getInitializerExpressionSTInitializerExpressionParserRuleCall_1_0());
				}
				lv_initializerExpression_1_0=ruleSTInitializerExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTInitializerExpressionSourceRule());
					}
					set(
						$current,
						"initializerExpression",
						lv_initializerExpression_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleSTVarTempDeclarationBlock
entryRuleSTVarTempDeclarationBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTVarTempDeclarationBlockRule()); }
	iv_ruleSTVarTempDeclarationBlock=ruleSTVarTempDeclarationBlock
	{ $current=$iv_ruleSTVarTempDeclarationBlock.current; }
	EOF;

// Rule STVarTempDeclarationBlock
ruleSTVarTempDeclarationBlock returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTVarTempDeclarationBlockAccess().getSTVarTempDeclarationBlockAction_0(),
					$current);
			}
		)
		otherlv_1=VAR_TEMP
		{
			newLeafNode(otherlv_1, grammarAccess.getSTVarTempDeclarationBlockAccess().getVAR_TEMPKeyword_1());
		}
		(
			(
				lv_constant_2_0=CONSTANT
				{
					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarTempDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTVarTempDeclarationBlockRule());
					}
					setWithLastConsumed($current, "constant", lv_constant_2_0 != null, "CONSTANT");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getSTVarTempDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
				}
				lv_varDeclarations_3_0=ruleSTVarDeclaration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTVarTempDeclarationBlockRule());
					}
					add(
						$current,
						"varDeclarations",
						lv_varDeclarations_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4=END_VAR
		{
			newLeafNode(otherlv_4, grammarAccess.getSTVarTempDeclarationBlockAccess().getEND_VARKeyword_4());
		}
	)
;

// Entry rule entryRuleSTVarInputDeclarationBlock
entryRuleSTVarInputDeclarationBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTVarInputDeclarationBlockRule()); }
	iv_ruleSTVarInputDeclarationBlock=ruleSTVarInputDeclarationBlock
	{ $current=$iv_ruleSTVarInputDeclarationBlock.current; }
	EOF;

// Rule STVarInputDeclarationBlock
ruleSTVarInputDeclarationBlock returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTVarInputDeclarationBlockAccess().getSTVarInputDeclarationBlockAction_0(),
					$current);
			}
		)
		otherlv_1=VAR_INPUT
		{
			newLeafNode(otherlv_1, grammarAccess.getSTVarInputDeclarationBlockAccess().getVAR_INPUTKeyword_1());
		}
		(
			(
				lv_constant_2_0=CONSTANT
				{
					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarInputDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTVarInputDeclarationBlockRule());
					}
					setWithLastConsumed($current, "constant", lv_constant_2_0 != null, "CONSTANT");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getSTVarInputDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
				}
				lv_varDeclarations_3_0=ruleSTVarDeclaration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTVarInputDeclarationBlockRule());
					}
					add(
						$current,
						"varDeclarations",
						lv_varDeclarations_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4=END_VAR
		{
			newLeafNode(otherlv_4, grammarAccess.getSTVarInputDeclarationBlockAccess().getEND_VARKeyword_4());
		}
	)
;

// Entry rule entryRuleSTVarOutputDeclarationBlock
entryRuleSTVarOutputDeclarationBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTVarOutputDeclarationBlockRule()); }
	iv_ruleSTVarOutputDeclarationBlock=ruleSTVarOutputDeclarationBlock
	{ $current=$iv_ruleSTVarOutputDeclarationBlock.current; }
	EOF;

// Rule STVarOutputDeclarationBlock
ruleSTVarOutputDeclarationBlock returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTVarOutputDeclarationBlockAccess().getSTVarOutputDeclarationBlockAction_0(),
					$current);
			}
		)
		otherlv_1=VAR_OUTPUT
		{
			newLeafNode(otherlv_1, grammarAccess.getSTVarOutputDeclarationBlockAccess().getVAR_OUTPUTKeyword_1());
		}
		(
			(
				lv_constant_2_0=CONSTANT
				{
					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarOutputDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTVarOutputDeclarationBlockRule());
					}
					setWithLastConsumed($current, "constant", lv_constant_2_0 != null, "CONSTANT");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getSTVarOutputDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
				}
				lv_varDeclarations_3_0=ruleSTVarDeclaration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTVarOutputDeclarationBlockRule());
					}
					add(
						$current,
						"varDeclarations",
						lv_varDeclarations_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4=END_VAR
		{
			newLeafNode(otherlv_4, grammarAccess.getSTVarOutputDeclarationBlockAccess().getEND_VARKeyword_4());
		}
	)
;

// Entry rule entryRuleSTVarInOutDeclarationBlock
entryRuleSTVarInOutDeclarationBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTVarInOutDeclarationBlockRule()); }
	iv_ruleSTVarInOutDeclarationBlock=ruleSTVarInOutDeclarationBlock
	{ $current=$iv_ruleSTVarInOutDeclarationBlock.current; }
	EOF;

// Rule STVarInOutDeclarationBlock
ruleSTVarInOutDeclarationBlock returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTVarInOutDeclarationBlockAccess().getSTVarInOutDeclarationBlockAction_0(),
					$current);
			}
		)
		otherlv_1=VAR_IN_OUT
		{
			newLeafNode(otherlv_1, grammarAccess.getSTVarInOutDeclarationBlockAccess().getVAR_IN_OUTKeyword_1());
		}
		(
			(
				lv_constant_2_0=CONSTANT
				{
					newLeafNode(lv_constant_2_0, grammarAccess.getSTVarInOutDeclarationBlockAccess().getConstantCONSTANTKeyword_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTVarInOutDeclarationBlockRule());
					}
					setWithLastConsumed($current, "constant", lv_constant_2_0 != null, "CONSTANT");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getSTVarInOutDeclarationBlockAccess().getVarDeclarationsSTVarDeclarationParserRuleCall_3_0());
				}
				lv_varDeclarations_3_0=ruleSTVarDeclaration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTVarInOutDeclarationBlockRule());
					}
					add(
						$current,
						"varDeclarations",
						lv_varDeclarations_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4=END_VAR
		{
			newLeafNode(otherlv_4, grammarAccess.getSTVarInOutDeclarationBlockAccess().getEND_VARKeyword_4());
		}
	)
;

// Entry rule entryRuleSTVarDeclaration
entryRuleSTVarDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTVarDeclarationRule()); }
	iv_ruleSTVarDeclaration=ruleSTVarDeclaration
	{ $current=$iv_ruleSTVarDeclaration.current; }
	EOF;

// Rule STVarDeclaration
ruleSTVarDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0(),
					$current);
			}
		)
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getSTVarDeclarationAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTVarDeclarationRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
				}
			)
		)
		(
			otherlv_2=AT
			{
				newLeafNode(otherlv_2, grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0());
			}
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTVarDeclarationRule());
						}
					}
					otherlv_3=RULE_ID
					{
						newLeafNode(otherlv_3, grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0());
					}
				)
			)
		)?
		otherlv_4=Colon
		{
			newLeafNode(otherlv_4, grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3());
		}
		(
			(
				(
					lv_array_5_0=ARRAY
					{
						newLeafNode(lv_array_5_0, grammarAccess.getSTVarDeclarationAccess().getArrayARRAYKeyword_4_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTVarDeclarationRule());
						}
						setWithLastConsumed($current, "array", lv_array_5_0 != null, "ARRAY");
					}
				)
			)
			(
				(
					otherlv_6=LeftSquareBracket
					{
						newLeafNode(otherlv_6, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0());
							}
							lv_ranges_7_0=ruleSTExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
								}
								add(
									$current,
									"ranges",
									lv_ranges_7_0,
									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						otherlv_8=Comma
						{
							newLeafNode(otherlv_8, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0());
								}
								lv_ranges_9_0=ruleSTExpression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
									}
									add(
										$current,
										"ranges",
										lv_ranges_9_0,
										"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)*
					otherlv_10=RightSquareBracket
					{
						newLeafNode(otherlv_10, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3());
					}
				)
				    |
				(
					otherlv_11=LeftSquareBracket
					{
						newLeafNode(otherlv_11, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0());
					}
					(
						(
							lv_count_12_0=Asterisk
							{
								newLeafNode(lv_count_12_0, grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getSTVarDeclarationRule());
								}
								addWithLastConsumed($current, "count", lv_count_12_0, "*");
							}
						)
					)
					(
						otherlv_13=Comma
						{
							newLeafNode(otherlv_13, grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0());
						}
						(
							(
								lv_count_14_0=Asterisk
								{
									newLeafNode(lv_count_14_0, grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_2_1_0());
								}
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getSTVarDeclarationRule());
									}
									addWithLastConsumed($current, "count", lv_count_14_0, "*");
								}
							)
						)
					)*
					otherlv_15=RightSquareBracket
					{
						newLeafNode(otherlv_15, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3());
					}
				)
			)
			otherlv_16=OF
			{
				newLeafNode(otherlv_16, grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2());
			}
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTVarDeclarationRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0());
				}
				ruleSTAnyType
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_18=LeftSquareBracket
			{
				newLeafNode(otherlv_18, grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0());
					}
					lv_maxLength_19_0=ruleSTExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
						}
						set(
							$current,
							"maxLength",
							lv_maxLength_19_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_20=RightSquareBracket
			{
				newLeafNode(otherlv_20, grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2());
			}
		)?
		(
			otherlv_21=ColonEqualsSign
			{
				newLeafNode(otherlv_21, grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0());
					}
					lv_defaultValue_22_0=ruleSTInitializerExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
						}
						set(
							$current,
							"defaultValue",
							lv_defaultValue_22_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getSTVarDeclarationAccess().getPragmaSTPragmaParserRuleCall_8_0());
				}
				lv_pragma_23_0=ruleSTPragma
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTVarDeclarationRule());
					}
					set(
						$current,
						"pragma",
						lv_pragma_23_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STPragma");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		otherlv_24=Semicolon
		{
			newLeafNode(otherlv_24, grammarAccess.getSTVarDeclarationAccess().getSemicolonKeyword_9());
		}
	)
;

// Entry rule entryRuleSTTypeDeclaration
entryRuleSTTypeDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTTypeDeclarationRule()); }
	iv_ruleSTTypeDeclaration=ruleSTTypeDeclaration
	{ $current=$iv_ruleSTTypeDeclaration.current; }
	EOF;

// Rule STTypeDeclaration
ruleSTTypeDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTTypeDeclarationAccess().getSTTypeDeclarationAction_0(),
					$current);
			}
		)
		(
			(
				(
					lv_array_1_0=ARRAY
					{
						newLeafNode(lv_array_1_0, grammarAccess.getSTTypeDeclarationAccess().getArrayARRAYKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
						}
						setWithLastConsumed($current, "array", lv_array_1_0 != null, "ARRAY");
					}
				)
			)
			(
				(
					otherlv_2=LeftSquareBracket
					{
						newLeafNode(otherlv_2, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_0_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_1_0());
							}
							lv_ranges_3_0=ruleSTExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getSTTypeDeclarationRule());
								}
								add(
									$current,
									"ranges",
									lv_ranges_3_0,
									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						otherlv_4=Comma
						{
							newLeafNode(otherlv_4, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_0_2_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_2_1_0());
								}
								lv_ranges_5_0=ruleSTExpression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getSTTypeDeclarationRule());
									}
									add(
										$current,
										"ranges",
										lv_ranges_5_0,
										"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)*
					otherlv_6=RightSquareBracket
					{
						newLeafNode(otherlv_6, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_0_3());
					}
				)
				    |
				(
					otherlv_7=LeftSquareBracket
					{
						newLeafNode(otherlv_7, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_1_0());
					}
					(
						(
							lv_count_8_0=Asterisk
							{
								newLeafNode(lv_count_8_0, grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
								}
								addWithLastConsumed($current, "count", lv_count_8_0, "*");
							}
						)
					)
					(
						otherlv_9=Comma
						{
							newLeafNode(otherlv_9, grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_1_2_0());
						}
						(
							(
								lv_count_10_0=Asterisk
								{
									newLeafNode(lv_count_10_0, grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_2_1_0());
								}
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
									}
									addWithLastConsumed($current, "count", lv_count_10_0, "*");
								}
							)
						)
					)*
					otherlv_11=RightSquareBracket
					{
						newLeafNode(otherlv_11, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_1_3());
					}
				)
			)
			otherlv_12=OF
			{
				newLeafNode(otherlv_12, grammarAccess.getSTTypeDeclarationAccess().getOFKeyword_1_2());
			}
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTTypeDeclarationRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementCrossReference_2_0());
				}
				ruleSTAnyType
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_14=LeftSquareBracket
			{
				newLeafNode(otherlv_14, grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_3_1_0());
					}
					lv_maxLength_15_0=ruleSTExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTTypeDeclarationRule());
						}
						set(
							$current,
							"maxLength",
							lv_maxLength_15_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_16=RightSquareBracket
			{
				newLeafNode(otherlv_16, grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_3_2());
			}
		)?
	)
;

// Entry rule entryRuleSTInitializerExpression
entryRuleSTInitializerExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTInitializerExpressionRule()); }
	iv_ruleSTInitializerExpression=ruleSTInitializerExpression
	{ $current=$iv_ruleSTInitializerExpression.current; }
	EOF;

// Rule STInitializerExpression
ruleSTInitializerExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTInitializerExpressionAccess().getSTElementaryInitializerExpressionParserRuleCall_0());
		}
		this_STElementaryInitializerExpression_0=ruleSTElementaryInitializerExpression
		{
			$current = $this_STElementaryInitializerExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTInitializerExpressionAccess().getSTArrayInitializerExpressionParserRuleCall_1());
		}
		this_STArrayInitializerExpression_1=ruleSTArrayInitializerExpression
		{
			$current = $this_STArrayInitializerExpression_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTInitializerExpressionAccess().getSTStructInitializerExpressionParserRuleCall_2());
		}
		this_STStructInitializerExpression_2=ruleSTStructInitializerExpression
		{
			$current = $this_STStructInitializerExpression_2.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTElementaryInitializerExpression
entryRuleSTElementaryInitializerExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTElementaryInitializerExpressionRule()); }
	iv_ruleSTElementaryInitializerExpression=ruleSTElementaryInitializerExpression
	{ $current=$iv_ruleSTElementaryInitializerExpression.current; }
	EOF;

// Rule STElementaryInitializerExpression
ruleSTElementaryInitializerExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getSTElementaryInitializerExpressionAccess().getValueSTExpressionParserRuleCall_0());
			}
			lv_value_0_0=ruleSTExpression
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getSTElementaryInitializerExpressionRule());
				}
				set(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleSTArrayInitializerExpression
entryRuleSTArrayInitializerExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTArrayInitializerExpressionRule()); }
	iv_ruleSTArrayInitializerExpression=ruleSTArrayInitializerExpression
	{ $current=$iv_ruleSTArrayInitializerExpression.current; }
	EOF;

// Rule STArrayInitializerExpression
ruleSTArrayInitializerExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftSquareBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0());
				}
				lv_values_1_0=ruleSTArrayInitElement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTArrayInitializerExpressionRule());
					}
					add(
						$current,
						"values",
						lv_values_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STArrayInitElement");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0());
					}
					lv_values_3_0=ruleSTArrayInitElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTArrayInitializerExpressionRule());
						}
						add(
							$current,
							"values",
							lv_values_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STArrayInitElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_4=RightSquareBracket
		{
			newLeafNode(otherlv_4, grammarAccess.getSTArrayInitializerExpressionAccess().getRightSquareBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleSTArrayInitElement
entryRuleSTArrayInitElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTArrayInitElementRule()); }
	iv_ruleSTArrayInitElement=ruleSTArrayInitElement
	{ $current=$iv_ruleSTArrayInitElement.current; }
	EOF;

// Rule STArrayInitElement
ruleSTArrayInitElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getSTSingleArrayInitElementParserRuleCall_0());
		}
		this_STSingleArrayInitElement_0=ruleSTSingleArrayInitElement
		{
			$current = $this_STSingleArrayInitElement_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTArrayInitElementAccess().getSTRepeatArrayInitElementParserRuleCall_1());
		}
		this_STRepeatArrayInitElement_1=ruleSTRepeatArrayInitElement
		{
			$current = $this_STRepeatArrayInitElement_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTSingleArrayInitElement
entryRuleSTSingleArrayInitElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTSingleArrayInitElementRule()); }
	iv_ruleSTSingleArrayInitElement=ruleSTSingleArrayInitElement
	{ $current=$iv_ruleSTSingleArrayInitElement.current; }
	EOF;

// Rule STSingleArrayInitElement
ruleSTSingleArrayInitElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getSTSingleArrayInitElementAccess().getInitExpressionSTInitializerExpressionParserRuleCall_0());
			}
			lv_initExpression_0_0=ruleSTInitializerExpression
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getSTSingleArrayInitElementRule());
				}
				set(
					$current,
					"initExpression",
					lv_initExpression_0_0,
					"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleSTRepeatArrayInitElement
entryRuleSTRepeatArrayInitElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTRepeatArrayInitElementRule()); }
	iv_ruleSTRepeatArrayInitElement=ruleSTRepeatArrayInitElement
	{ $current=$iv_ruleSTRepeatArrayInitElement.current; }
	EOF;

// Rule STRepeatArrayInitElement
ruleSTRepeatArrayInitElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_repetitions_0_0=RULE_INT
				{
					newLeafNode(lv_repetitions_0_0, grammarAccess.getSTRepeatArrayInitElementAccess().getRepetitionsINTTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTRepeatArrayInitElementRule());
					}
					setWithLastConsumed(
						$current,
						"repetitions",
						lv_repetitions_0_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.INT");
				}
			)
		)
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getSTRepeatArrayInitElementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_2_0());
				}
				lv_initExpressions_2_0=ruleSTInitializerExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTRepeatArrayInitElementRule());
					}
					add(
						$current,
						"initExpressions",
						lv_initExpressions_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_3=Comma
			{
				newLeafNode(otherlv_3, grammarAccess.getSTRepeatArrayInitElementAccess().getCommaKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_3_1_0());
					}
					lv_initExpressions_4_0=ruleSTInitializerExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTRepeatArrayInitElementRule());
						}
						add(
							$current,
							"initExpressions",
							lv_initExpressions_4_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_5=RightParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getSTRepeatArrayInitElementAccess().getRightParenthesisKeyword_4());
		}
	)
;

// Entry rule entryRuleSTStructInitializerExpression
entryRuleSTStructInitializerExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTStructInitializerExpressionRule()); }
	iv_ruleSTStructInitializerExpression=ruleSTStructInitializerExpression
	{ $current=$iv_ruleSTStructInitializerExpression.current; }
	EOF;

// Rule STStructInitializerExpression
ruleSTStructInitializerExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTStructInitializerExpressionRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getTypeStructuredTypeCrossReference_0_0_0());
					}
					ruleQualifiedName
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_1=NumberSign
			{
				newLeafNode(otherlv_1, grammarAccess.getSTStructInitializerExpressionAccess().getNumberSignKeyword_0_1());
			}
		)?
		otherlv_2=LeftParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getSTStructInitializerExpressionAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_2_0());
				}
				lv_values_3_0=ruleSTStructInitElement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTStructInitializerExpressionRule());
					}
					add(
						$current,
						"values",
						lv_values_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStructInitElement");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_4=Comma
			{
				newLeafNode(otherlv_4, grammarAccess.getSTStructInitializerExpressionAccess().getCommaKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_3_1_0());
					}
					lv_values_5_0=ruleSTStructInitElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTStructInitializerExpressionRule());
						}
						add(
							$current,
							"values",
							lv_values_5_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStructInitElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_6=RightParenthesis
		{
			newLeafNode(otherlv_6, grammarAccess.getSTStructInitializerExpressionAccess().getRightParenthesisKeyword_4());
		}
	)
;

// Entry rule entryRuleSTStructInitElement
entryRuleSTStructInitElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTStructInitElementRule()); }
	iv_ruleSTStructInitElement=ruleSTStructInitElement
	{ $current=$iv_ruleSTStructInitElement.current; }
	EOF;

// Rule STStructInitElement
ruleSTStructInitElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTStructInitElementRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementCrossReference_0_0());
				}
				ruleSTFeatureName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=ColonEqualsSign
		{
			newLeafNode(otherlv_1, grammarAccess.getSTStructInitElementAccess().getColonEqualsSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTStructInitElementAccess().getValueSTInitializerExpressionParserRuleCall_2_0());
				}
				lv_value_2_0=ruleSTInitializerExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTStructInitElementRule());
					}
					set(
						$current,
						"value",
						lv_value_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSTPragma
entryRuleSTPragma returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTPragmaRule()); }
	iv_ruleSTPragma=ruleSTPragma
	{ $current=$iv_ruleSTPragma.current; }
	EOF;

// Rule STPragma
ruleSTPragma returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTPragmaAccess().getSTPragmaAction_0(),
					$current);
			}
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getSTPragmaAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_2_0());
				}
				lv_attributes_2_0=ruleSTAttribute
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTPragmaRule());
					}
					add(
						$current,
						"attributes",
						lv_attributes_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAttribute");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_3=Comma
			{
				newLeafNode(otherlv_3, grammarAccess.getSTPragmaAccess().getCommaKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_3_1_0());
					}
					lv_attributes_4_0=ruleSTAttribute
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTPragmaRule());
						}
						add(
							$current,
							"attributes",
							lv_attributes_4_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAttribute");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_5=RightCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getSTPragmaAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleSTAttribute
entryRuleSTAttribute returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAttributeRule()); }
	iv_ruleSTAttribute=ruleSTAttribute
	{ $current=$iv_ruleSTAttribute.current; }
	EOF;

// Rule STAttribute
ruleSTAttribute returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTAttributeRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTAttributeAccess().getDeclarationAttributeDeclarationCrossReference_0_0());
				}
				ruleSTAttributeName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=ColonEqualsSign
		{
			newLeafNode(otherlv_1, grammarAccess.getSTAttributeAccess().getColonEqualsSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTAttributeAccess().getValueSTInitializerExpressionParserRuleCall_2_0());
				}
				lv_value_2_0=ruleSTInitializerExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTAttributeRule());
					}
					set(
						$current,
						"value",
						lv_value_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSTAttributeName
entryRuleSTAttributeName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTAttributeNameRule()); }
	iv_ruleSTAttributeName=ruleSTAttributeName
	{ $current=$iv_ruleSTAttributeName.current.getText(); }
	EOF;

// Rule STAttributeName
ruleSTAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		newCompositeNode(grammarAccess.getSTAttributeNameAccess().getQualifiedNameParserRuleCall());
	}
	this_QualifiedName_0=ruleQualifiedName
	{
		$current.merge(this_QualifiedName_0);
	}
	{
		afterParserOrEnumRuleCall();
	}
;

// Entry rule entryRuleSTStatement
entryRuleSTStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTStatementRule()); }
	iv_ruleSTStatement=ruleSTStatement
	{ $current=$iv_ruleSTStatement.current; }
	EOF;

// Rule STStatement
ruleSTStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getSTStatementAccess().getSTIfStatementParserRuleCall_0_0_0());
				}
				this_STIfStatement_0=ruleSTIfStatement
				{
					$current = $this_STIfStatement_0.current;
					afterParserOrEnumRuleCall();
				}
				    |
				{
					newCompositeNode(grammarAccess.getSTStatementAccess().getSTCaseStatementParserRuleCall_0_0_1());
				}
				this_STCaseStatement_1=ruleSTCaseStatement
				{
					$current = $this_STCaseStatement_1.current;
					afterParserOrEnumRuleCall();
				}
				    |
				{
					newCompositeNode(grammarAccess.getSTStatementAccess().getSTForStatementParserRuleCall_0_0_2());
				}
				this_STForStatement_2=ruleSTForStatement
				{
					$current = $this_STForStatement_2.current;
					afterParserOrEnumRuleCall();
				}
				    |
				{
					newCompositeNode(grammarAccess.getSTStatementAccess().getSTWhileStatementParserRuleCall_0_0_3());
				}
				this_STWhileStatement_3=ruleSTWhileStatement
				{
					$current = $this_STWhileStatement_3.current;
					afterParserOrEnumRuleCall();
				}
				    |
				{
					newCompositeNode(grammarAccess.getSTStatementAccess().getSTRepeatStatementParserRuleCall_0_0_4());
				}
				this_STRepeatStatement_4=ruleSTRepeatStatement
				{
					$current = $this_STRepeatStatement_4.current;
					afterParserOrEnumRuleCall();
				}
				    |
				{
					newCompositeNode(grammarAccess.getSTStatementAccess().getSTAssignmentParserRuleCall_0_0_5());
				}
				this_STAssignment_5=ruleSTAssignment
				{
					$current = $this_STAssignment_5.current;
					afterParserOrEnumRuleCall();
				}
				    |
				(
					(
						{
							$current = forceCreateModelElement(
								grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_6_0(),
								$current);
						}
					)
					otherlv_7=RETURN
					{
						newLeafNode(otherlv_7, grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_6_1());
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElement(
								grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_7_0(),
								$current);
						}
					)
					otherlv_9=CONTINUE
					{
						newLeafNode(otherlv_9, grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_7_1());
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElement(
								grammarAccess.getSTStatementAccess().getSTExitAction_0_0_8_0(),
								$current);
						}
					)
					otherlv_11=EXIT
					{
						newLeafNode(otherlv_11, grammarAccess.getSTStatementAccess().getEXITKeyword_0_0_8_1());
					}
				)
			)
			otherlv_12=Semicolon
			{
				newLeafNode(otherlv_12, grammarAccess.getSTStatementAccess().getSemicolonKeyword_0_1());
			}
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getSTStatementAccess().getSTNopAction_1_0(),
						$current);
				}
			)
			otherlv_14=Semicolon
			{
				newLeafNode(otherlv_14, grammarAccess.getSTStatementAccess().getSemicolonKeyword_1_1());
			}
		)
	)
;

// Entry rule entryRuleSTAssignment
entryRuleSTAssignment returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAssignmentRule()); }
	iv_ruleSTAssignment=ruleSTAssignment
	{ $current=$iv_ruleSTAssignment.current; }
	EOF;

// Rule STAssignment
ruleSTAssignment returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTAssignmentAccess().getSTExpressionParserRuleCall_0());
		}
		this_STExpression_0=ruleSTExpression
		{
			$current = $this_STExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getSTAssignmentAccess().getSTAssignmentLeftAction_1_0(),
						$current);
				}
			)
			otherlv_2=ColonEqualsSign
			{
				newLeafNode(otherlv_2, grammarAccess.getSTAssignmentAccess().getColonEqualsSignKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTAssignmentAccess().getRightSTAssignmentParserRuleCall_1_2_0());
					}
					lv_right_3_0=ruleSTAssignment
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTAssignmentRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAssignment");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleSTCallArgument
entryRuleSTCallArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTCallArgumentRule()); }
	iv_ruleSTCallArgument=ruleSTCallArgument
	{ $current=$iv_ruleSTCallArgument.current; }
	EOF;

// Rule STCallArgument
ruleSTCallArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTCallArgumentAccess().getSTCallUnnamedArgumentParserRuleCall_0());
		}
		this_STCallUnnamedArgument_0=ruleSTCallUnnamedArgument
		{
			$current = $this_STCallUnnamedArgument_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTCallArgumentAccess().getSTCallNamedInputArgumentParserRuleCall_1());
		}
		this_STCallNamedInputArgument_1=ruleSTCallNamedInputArgument
		{
			$current = $this_STCallNamedInputArgument_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTCallArgumentAccess().getSTCallNamedOutputArgumentParserRuleCall_2());
		}
		this_STCallNamedOutputArgument_2=ruleSTCallNamedOutputArgument
		{
			$current = $this_STCallNamedOutputArgument_2.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTCallUnnamedArgument
entryRuleSTCallUnnamedArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTCallUnnamedArgumentRule()); }
	iv_ruleSTCallUnnamedArgument=ruleSTCallUnnamedArgument
	{ $current=$iv_ruleSTCallUnnamedArgument.current; }
	EOF;

// Rule STCallUnnamedArgument
ruleSTCallUnnamedArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getSTCallUnnamedArgumentAccess().getArgumentSTExpressionParserRuleCall_0());
			}
			lv_argument_0_0=ruleSTExpression
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getSTCallUnnamedArgumentRule());
				}
				set(
					$current,
					"argument",
					lv_argument_0_0,
					"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleSTCallNamedInputArgument
entryRuleSTCallNamedInputArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTCallNamedInputArgumentRule()); }
	iv_ruleSTCallNamedInputArgument=ruleSTCallNamedInputArgument
	{ $current=$iv_ruleSTCallNamedInputArgument.current; }
	EOF;

// Rule STCallNamedInputArgument
ruleSTCallNamedInputArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTCallNamedInputArgumentRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementCrossReference_0_0());
				}
			)
		)
		otherlv_1=ColonEqualsSign
		{
			newLeafNode(otherlv_1, grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTCallNamedInputArgumentAccess().getArgumentSTExpressionParserRuleCall_2_0());
				}
				lv_argument_2_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTCallNamedInputArgumentRule());
					}
					set(
						$current,
						"argument",
						lv_argument_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSTCallNamedOutputArgument
entryRuleSTCallNamedOutputArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTCallNamedOutputArgumentRule()); }
	iv_ruleSTCallNamedOutputArgument=ruleSTCallNamedOutputArgument
	{ $current=$iv_ruleSTCallNamedOutputArgument.current; }
	EOF;

// Rule STCallNamedOutputArgument
ruleSTCallNamedOutputArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_not_0_0=NOT
				{
					newLeafNode(lv_not_0_0, grammarAccess.getSTCallNamedOutputArgumentAccess().getNotNOTKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
					}
					setWithLastConsumed($current, "not", lv_not_0_0 != null, "NOT");
				}
			)
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTCallNamedOutputArgumentRule());
					}
				}
				otherlv_1=RULE_ID
				{
					newLeafNode(otherlv_1, grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementCrossReference_1_0());
				}
			)
		)
		otherlv_2=EqualsSignGreaterThanSign
		{
			newLeafNode(otherlv_2, grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTCallNamedOutputArgumentAccess().getArgumentSTExpressionParserRuleCall_3_0());
				}
				lv_argument_3_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTCallNamedOutputArgumentRule());
					}
					set(
						$current,
						"argument",
						lv_argument_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSTIfStatement
entryRuleSTIfStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTIfStatementRule()); }
	iv_ruleSTIfStatement=ruleSTIfStatement
	{ $current=$iv_ruleSTIfStatement.current; }
	EOF;

// Rule STIfStatement
ruleSTIfStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=IF
		{
			newLeafNode(otherlv_0, grammarAccess.getSTIfStatementAccess().getIFKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
				}
				lv_condition_1_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTIfStatementRule());
					}
					set(
						$current,
						"condition",
						lv_condition_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=THEN
		{
			newLeafNode(otherlv_2, grammarAccess.getSTIfStatementAccess().getTHENKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
				}
				lv_statements_3_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTIfStatementRule());
					}
					add(
						$current,
						"statements",
						lv_statements_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				{
					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0());
				}
				lv_elseifs_4_0=ruleSTElseIfPart
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTIfStatementRule());
					}
					add(
						$current,
						"elseifs",
						lv_elseifs_4_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STElseIfPart");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				{
					newCompositeNode(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0());
				}
				lv_else_5_0=ruleSTElsePart
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTIfStatementRule());
					}
					set(
						$current,
						"else",
						lv_else_5_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STElsePart");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		otherlv_6=END_IF
		{
			newLeafNode(otherlv_6, grammarAccess.getSTIfStatementAccess().getEND_IFKeyword_6());
		}
	)
;

// Entry rule entryRuleSTElseIfPart
entryRuleSTElseIfPart returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTElseIfPartRule()); }
	iv_ruleSTElseIfPart=ruleSTElseIfPart
	{ $current=$iv_ruleSTElseIfPart.current; }
	EOF;

// Rule STElseIfPart
ruleSTElseIfPart returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=ELSIF
		{
			newLeafNode(otherlv_0, grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0());
				}
				lv_condition_1_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTElseIfPartRule());
					}
					set(
						$current,
						"condition",
						lv_condition_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=THEN
		{
			newLeafNode(otherlv_2, grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0());
				}
				lv_statements_3_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTElseIfPartRule());
					}
					add(
						$current,
						"statements",
						lv_statements_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleSTCaseStatement
entryRuleSTCaseStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTCaseStatementRule()); }
	iv_ruleSTCaseStatement=ruleSTCaseStatement
	{ $current=$iv_ruleSTCaseStatement.current; }
	EOF;

// Rule STCaseStatement
ruleSTCaseStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=CASE
		{
			newLeafNode(otherlv_0, grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0());
				}
				lv_selector_1_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTCaseStatementRule());
					}
					set(
						$current,
						"selector",
						lv_selector_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=OF
		{
			newLeafNode(otherlv_2, grammarAccess.getSTCaseStatementAccess().getOFKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0());
				}
				lv_cases_3_0=ruleSTCaseCases
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTCaseStatementRule());
					}
					add(
						$current,
						"cases",
						lv_cases_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STCaseCases");
					afterParserOrEnumRuleCall();
				}
			)
		)+
		(
			(
				{
					newCompositeNode(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0());
				}
				lv_else_4_0=ruleSTElsePart
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTCaseStatementRule());
					}
					set(
						$current,
						"else",
						lv_else_4_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STElsePart");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		otherlv_5=END_CASE
		{
			newLeafNode(otherlv_5, grammarAccess.getSTCaseStatementAccess().getEND_CASEKeyword_5());
		}
	)
;

// Entry rule entryRuleSTCaseCases
entryRuleSTCaseCases returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTCaseCasesRule()); }
	iv_ruleSTCaseCases=ruleSTCaseCases
	{ $current=$iv_ruleSTCaseCases.current; }
	EOF;

// Rule STCaseCases
ruleSTCaseCases returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0());
				}
				lv_conditions_0_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTCaseCasesRule());
					}
					add(
						$current,
						"conditions",
						lv_conditions_0_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=Comma
			{
				newLeafNode(otherlv_1, grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0());
					}
					lv_conditions_2_0=ruleSTExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTCaseCasesRule());
						}
						add(
							$current,
							"conditions",
							lv_conditions_2_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_3=Colon
		{
			newLeafNode(otherlv_3, grammarAccess.getSTCaseCasesAccess().getColonKeyword_2());
		}
		(
			((
				ruleSTStatement
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0());
				}
				lv_statements_4_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTCaseCasesRule());
					}
					add(
						$current,
						"statements",
						lv_statements_4_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleSTElsePart
entryRuleSTElsePart returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTElsePartRule()); }
	iv_ruleSTElsePart=ruleSTElsePart
	{ $current=$iv_ruleSTElsePart.current; }
	EOF;

// Rule STElsePart
ruleSTElsePart returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTElsePartAccess().getSTElsePartAction_0(),
					$current);
			}
		)
		otherlv_1=ELSE
		{
			newLeafNode(otherlv_1, grammarAccess.getSTElsePartAccess().getELSEKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0());
				}
				lv_statements_2_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTElsePartRule());
					}
					add(
						$current,
						"statements",
						lv_statements_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleSTForStatement
entryRuleSTForStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTForStatementRule()); }
	iv_ruleSTForStatement=ruleSTForStatement
	{ $current=$iv_ruleSTForStatement.current; }
	EOF;

// Rule STForStatement
ruleSTForStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=FOR
		{
			newLeafNode(otherlv_0, grammarAccess.getSTForStatementAccess().getFORKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTForStatementAccess().getVariableSTExpressionParserRuleCall_1_0());
				}
				lv_variable_1_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTForStatementRule());
					}
					set(
						$current,
						"variable",
						lv_variable_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=ColonEqualsSign
		{
			newLeafNode(otherlv_2, grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0());
				}
				lv_from_3_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTForStatementRule());
					}
					set(
						$current,
						"from",
						lv_from_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_4=TO
		{
			newLeafNode(otherlv_4, grammarAccess.getSTForStatementAccess().getTOKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0());
				}
				lv_to_5_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTForStatementRule());
					}
					set(
						$current,
						"to",
						lv_to_5_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_6=BY
			{
				newLeafNode(otherlv_6, grammarAccess.getSTForStatementAccess().getBYKeyword_6_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0());
					}
					lv_by_7_0=ruleSTExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTForStatementRule());
						}
						set(
							$current,
							"by",
							lv_by_7_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		otherlv_8=DO
		{
			newLeafNode(otherlv_8, grammarAccess.getSTForStatementAccess().getDOKeyword_7());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0());
				}
				lv_statements_9_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTForStatementRule());
					}
					add(
						$current,
						"statements",
						lv_statements_9_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_10=END_FOR
		{
			newLeafNode(otherlv_10, grammarAccess.getSTForStatementAccess().getEND_FORKeyword_9());
		}
	)
;

// Entry rule entryRuleSTWhileStatement
entryRuleSTWhileStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTWhileStatementRule()); }
	iv_ruleSTWhileStatement=ruleSTWhileStatement
	{ $current=$iv_ruleSTWhileStatement.current; }
	EOF;

// Rule STWhileStatement
ruleSTWhileStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=WHILE
		{
			newLeafNode(otherlv_0, grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0());
				}
				lv_condition_1_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTWhileStatementRule());
					}
					set(
						$current,
						"condition",
						lv_condition_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=DO
		{
			newLeafNode(otherlv_2, grammarAccess.getSTWhileStatementAccess().getDOKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0());
				}
				lv_statements_3_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTWhileStatementRule());
					}
					add(
						$current,
						"statements",
						lv_statements_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4=END_WHILE
		{
			newLeafNode(otherlv_4, grammarAccess.getSTWhileStatementAccess().getEND_WHILEKeyword_4());
		}
	)
;

// Entry rule entryRuleSTRepeatStatement
entryRuleSTRepeatStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTRepeatStatementRule()); }
	iv_ruleSTRepeatStatement=ruleSTRepeatStatement
	{ $current=$iv_ruleSTRepeatStatement.current; }
	EOF;

// Rule STRepeatStatement
ruleSTRepeatStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=REPEAT
		{
			newLeafNode(otherlv_0, grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0());
				}
				lv_statements_1_0=ruleSTStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTRepeatStatementRule());
					}
					add(
						$current,
						"statements",
						lv_statements_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_2=UNTIL
		{
			newLeafNode(otherlv_2, grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0());
				}
				lv_condition_3_0=ruleSTExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTRepeatStatementRule());
					}
					set(
						$current,
						"condition",
						lv_condition_3_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_4=END_REPEAT
		{
			newLeafNode(otherlv_4, grammarAccess.getSTRepeatStatementAccess().getEND_REPEATKeyword_4());
		}
	)
;

// Entry rule entryRuleSTExpression
entryRuleSTExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTExpressionRule()); }
	iv_ruleSTExpression=ruleSTExpression
	{ $current=$iv_ruleSTExpression.current; }
	EOF;

// Rule STExpression
ruleSTExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		newCompositeNode(grammarAccess.getSTExpressionAccess().getSTSubrangeExpressionParserRuleCall());
	}
	this_STSubrangeExpression_0=ruleSTSubrangeExpression
	{
		$current = $this_STSubrangeExpression_0.current;
		afterParserOrEnumRuleCall();
	}
;

// Entry rule entryRuleSTSubrangeExpression
entryRuleSTSubrangeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTSubrangeExpressionRule()); }
	iv_ruleSTSubrangeExpression=ruleSTSubrangeExpression
	{ $current=$iv_ruleSTSubrangeExpression.current; }
	EOF;

// Rule STSubrangeExpression
ruleSTSubrangeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0());
		}
		this_STOrExpression_0=ruleSTOrExpression
		{
			$current = $this_STOrExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleSubrangeOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTSubrangeExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.SubrangeOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTOrExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTSubrangeExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STOrExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTOrExpression
entryRuleSTOrExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTOrExpressionRule()); }
	iv_ruleSTOrExpression=ruleSTOrExpression
	{ $current=$iv_ruleSTOrExpression.current; }
	EOF;

// Rule STOrExpression
ruleSTOrExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0());
		}
		this_STXorExpression_0=ruleSTXorExpression
		{
			$current = $this_STXorExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleOrOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTOrExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.OrOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTXorExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTOrExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STXorExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTXorExpression
entryRuleSTXorExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTXorExpressionRule()); }
	iv_ruleSTXorExpression=ruleSTXorExpression
	{ $current=$iv_ruleSTXorExpression.current; }
	EOF;

// Rule STXorExpression
ruleSTXorExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0());
		}
		this_STAndExpression_0=ruleSTAndExpression
		{
			$current = $this_STAndExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleXorOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTXorExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.XorOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTAndExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTXorExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAndExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTAndExpression
entryRuleSTAndExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAndExpressionRule()); }
	iv_ruleSTAndExpression=ruleSTAndExpression
	{ $current=$iv_ruleSTAndExpression.current; }
	EOF;

// Rule STAndExpression
ruleSTAndExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0());
		}
		this_STEqualityExpression_0=ruleSTEqualityExpression
		{
			$current = $this_STEqualityExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleAndOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTAndExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.AndOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTEqualityExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTAndExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STEqualityExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTEqualityExpression
entryRuleSTEqualityExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTEqualityExpressionRule()); }
	iv_ruleSTEqualityExpression=ruleSTEqualityExpression
	{ $current=$iv_ruleSTEqualityExpression.current; }
	EOF;

// Rule STEqualityExpression
ruleSTEqualityExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0());
		}
		this_STComparisonExpression_0=ruleSTComparisonExpression
		{
			$current = $this_STComparisonExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleEqualityOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTEqualityExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.EqualityOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTComparisonExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTEqualityExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STComparisonExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTComparisonExpression
entryRuleSTComparisonExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTComparisonExpressionRule()); }
	iv_ruleSTComparisonExpression=ruleSTComparisonExpression
	{ $current=$iv_ruleSTComparisonExpression.current; }
	EOF;

// Rule STComparisonExpression
ruleSTComparisonExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0());
		}
		this_STAddSubExpression_0=ruleSTAddSubExpression
		{
			$current = $this_STAddSubExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleCompareOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTComparisonExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.CompareOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTAddSubExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTComparisonExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STAddSubExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTAddSubExpression
entryRuleSTAddSubExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAddSubExpressionRule()); }
	iv_ruleSTAddSubExpression=ruleSTAddSubExpression
	{ $current=$iv_ruleSTAddSubExpression.current; }
	EOF;

// Rule STAddSubExpression
ruleSTAddSubExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0());
		}
		this_STMulDivModExpression_0=ruleSTMulDivModExpression
		{
			$current = $this_STMulDivModExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleAddSubOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTAddSubExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.AddSubOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTMulDivModExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTAddSubExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STMulDivModExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTMulDivModExpression
entryRuleSTMulDivModExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTMulDivModExpressionRule()); }
	iv_ruleSTMulDivModExpression=ruleSTMulDivModExpression
	{ $current=$iv_ruleSTMulDivModExpression.current; }
	EOF;

// Rule STMulDivModExpression
ruleSTMulDivModExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0());
		}
		this_STPowerExpression_0=ruleSTPowerExpression
		{
			$current = $this_STPowerExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleMulDivModOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTMulDivModExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.MulDivModOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTPowerExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTMulDivModExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STPowerExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTPowerExpression
entryRuleSTPowerExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTPowerExpressionRule()); }
	iv_ruleSTPowerExpression=ruleSTPowerExpression
	{ $current=$iv_ruleSTPowerExpression.current; }
	EOF;

// Rule STPowerExpression
ruleSTPowerExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0());
		}
		this_STUnaryExpression_0=ruleSTUnaryExpression
		{
			$current = $this_STUnaryExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=rulePowerOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTPowerExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.PowerOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0());
					}
					lv_right_3_0=ruleSTUnaryExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTPowerExpressionRule());
						}
						set(
							$current,
							"right",
							lv_right_3_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STUnaryExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSTUnaryExpression
entryRuleSTUnaryExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTUnaryExpressionRule()); }
	iv_ruleSTUnaryExpression=ruleSTUnaryExpression
	{ $current=$iv_ruleSTUnaryExpression.current; }
	EOF;

// Rule STUnaryExpression
ruleSTUnaryExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getSTAccessExpressionParserRuleCall_0());
		}
		this_STAccessExpression_0=ruleSTAccessExpression
		{
			$current = $this_STAccessExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getSTLiteralExpressionsParserRuleCall_1());
		}
		this_STLiteralExpressions_1=ruleSTLiteralExpressions
		{
			$current = $this_STLiteralExpressions_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			(ruleSTSignedLiteralExpressions)=>
			{
				newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getSTSignedLiteralExpressionsParserRuleCall_2());
			}
			this_STSignedLiteralExpressions_2=ruleSTSignedLiteralExpressions
			{
				$current = $this_STSignedLiteralExpressions_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_3_0(),
						$current);
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_3_1_0());
					}
					lv_op_4_0=ruleUnaryOperator
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTUnaryExpressionRule());
						}
						set(
							$current,
							"op",
							lv_op_4_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.UnaryOperator");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSTUnaryExpressionAccess().getExpressionSTUnaryExpressionParserRuleCall_3_2_0());
					}
					lv_expression_5_0=ruleSTUnaryExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTUnaryExpressionRule());
						}
						set(
							$current,
							"expression",
							lv_expression_5_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.STUnaryExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleSTAccessExpression
entryRuleSTAccessExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTAccessExpressionRule()); }
	iv_ruleSTAccessExpression=ruleSTAccessExpression
	{ $current=$iv_ruleSTAccessExpression.current; }
	EOF;

// Rule STAccessExpression
ruleSTAccessExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0());
		}
		this_STPrimaryExpression_0=ruleSTPrimaryExpression
		{
			$current = $this_STPrimaryExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0(),
							$current);
					}
				)
				otherlv_2=FullStop
				{
					newLeafNode(otherlv_2, grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1());
				}
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0());
							}
							lv_member_3_1=ruleSTFeatureExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getSTAccessExpressionRule());
								}
								set(
									$current,
									"member",
									lv_member_3_1,
									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STFeatureExpression");
								afterParserOrEnumRuleCall();
							}
							    |
							{
								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1());
							}
							lv_member_3_2=ruleSTMultibitPartialExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getSTAccessExpressionRule());
								}
								set(
									$current,
									"member",
									lv_member_3_2,
									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STMultibitPartialExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0(),
							$current);
					}
				)
				otherlv_5=LeftSquareBracket
				{
					newLeafNode(otherlv_5, grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0());
						}
						lv_index_6_0=ruleSTExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTAccessExpressionRule());
							}
							add(
								$current,
								"index",
								lv_index_6_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_7=Comma
					{
						newLeafNode(otherlv_7, grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0());
							}
							lv_index_8_0=ruleSTExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getSTAccessExpressionRule());
								}
								add(
									$current,
									"index",
									lv_index_8_0,
									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
				otherlv_9=RightSquareBracket
				{
					newLeafNode(otherlv_9, grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4());
				}
			)
		)*
	)
;

// Entry rule entryRuleSTPrimaryExpression
entryRuleSTPrimaryExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTPrimaryExpressionRule()); }
	iv_ruleSTPrimaryExpression=ruleSTPrimaryExpression
	{ $current=$iv_ruleSTPrimaryExpression.current; }
	EOF;

// Rule STPrimaryExpression
ruleSTPrimaryExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			otherlv_0=LeftParenthesis
			{
				newLeafNode(otherlv_0, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
			}
			{
				newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1());
			}
			this_STExpression_1=ruleSTExpression
			{
				$current = $this_STExpression_1.current;
				afterParserOrEnumRuleCall();
			}
			otherlv_2=RightParenthesis
			{
				newLeafNode(otherlv_2, grammarAccess.getSTPrimaryExpressionAccess().getRightParenthesisKeyword_0_2());
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTFeatureExpressionParserRuleCall_1());
		}
		this_STFeatureExpression_3=ruleSTFeatureExpression
		{
			$current = $this_STFeatureExpression_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTPrimaryExpressionAccess().getSTBuiltinFeatureExpressionParserRuleCall_2());
		}
		this_STBuiltinFeatureExpression_4=ruleSTBuiltinFeatureExpression
		{
			$current = $this_STBuiltinFeatureExpression_4.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTFeatureExpression
entryRuleSTFeatureExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTFeatureExpressionRule()); }
	iv_ruleSTFeatureExpression=ruleSTFeatureExpression
	{ $current=$iv_ruleSTFeatureExpression.current; }
	EOF;

// Rule STFeatureExpression
ruleSTFeatureExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0(),
					$current);
			}
		)
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0());
				}
				ruleSTFeatureName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(
					lv_call_2_0=LeftParenthesis
					{
						newLeafNode(lv_call_2_0, grammarAccess.getSTFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTFeatureExpressionRule());
						}
						setWithLastConsumed($current, "call", lv_call_2_0 != null, "(");
					}
				)
			)
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
						}
						lv_parameters_3_0=ruleSTCallArgument
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTFeatureExpressionRule());
							}
							add(
								$current,
								"parameters",
								lv_parameters_3_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallArgument");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_4=Comma
					{
						newLeafNode(otherlv_4, grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
							}
							lv_parameters_5_0=ruleSTCallArgument
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getSTFeatureExpressionRule());
								}
								add(
									$current,
									"parameters",
									lv_parameters_5_0,
									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallArgument");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
			)?
			otherlv_6=RightParenthesis
			{
				newLeafNode(otherlv_6, grammarAccess.getSTFeatureExpressionAccess().getRightParenthesisKeyword_2_2());
			}
		)?
	)
;

// Entry rule entryRuleSTFeatureName
entryRuleSTFeatureName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTFeatureNameRule()); }
	iv_ruleSTFeatureName=ruleSTFeatureName
	{ $current=$iv_ruleSTFeatureName.current.getText(); }
	EOF;

// Rule STFeatureName
ruleSTFeatureName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTFeatureNameAccess().getQualifiedNameParserRuleCall_0());
		}
		this_QualifiedName_0=ruleQualifiedName
		{
			$current.merge(this_QualifiedName_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		kw=LT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLTKeyword_1());
		}
		    |
		kw=AND
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getANDKeyword_2());
		}
		    |
		kw=OR
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getORKeyword_3());
		}
		    |
		kw=XOR
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getXORKeyword_4());
		}
		    |
		kw=MOD
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getMODKeyword_5());
		}
		    |
		kw=D
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDKeyword_6());
		}
		    |
		kw=DT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getDTKeyword_7());
		}
		    |
		kw=LD
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTFeatureNameAccess().getLDKeyword_8());
		}
	)
;

// Entry rule entryRuleSTBuiltinFeatureExpression
entryRuleSTBuiltinFeatureExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionRule()); }
	iv_ruleSTBuiltinFeatureExpression=ruleSTBuiltinFeatureExpression
	{ $current=$iv_ruleSTBuiltinFeatureExpression.current; }
	EOF;

// Rule STBuiltinFeatureExpression
ruleSTBuiltinFeatureExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTBuiltinFeatureExpressionAccess().getSTBuiltinFeatureExpressionAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureSTBuiltinFeatureEnumRuleCall_1_0());
				}
				lv_feature_1_0=ruleSTBuiltinFeature
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTBuiltinFeatureExpressionRule());
					}
					set(
						$current,
						"feature",
						lv_feature_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STBuiltinFeature");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(
					lv_call_2_0=LeftParenthesis
					{
						newLeafNode(lv_call_2_0, grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTBuiltinFeatureExpressionRule());
						}
						setWithLastConsumed($current, "call", lv_call_2_0 != null, "(");
					}
				)
			)
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0());
						}
						lv_parameters_3_0=ruleSTCallArgument
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTBuiltinFeatureExpressionRule());
							}
							add(
								$current,
								"parameters",
								lv_parameters_3_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallArgument");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_4=Comma
					{
						newLeafNode(otherlv_4, grammarAccess.getSTBuiltinFeatureExpressionAccess().getCommaKeyword_2_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0());
							}
							lv_parameters_5_0=ruleSTCallArgument
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getSTBuiltinFeatureExpressionRule());
								}
								add(
									$current,
									"parameters",
									lv_parameters_5_0,
									"org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallArgument");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
			)?
			otherlv_6=RightParenthesis
			{
				newLeafNode(otherlv_6, grammarAccess.getSTBuiltinFeatureExpressionAccess().getRightParenthesisKeyword_2_2());
			}
		)?
	)
;

// Entry rule entryRuleSTMultibitPartialExpression
entryRuleSTMultibitPartialExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTMultibitPartialExpressionRule()); }
	iv_ruleSTMultibitPartialExpression=ruleSTMultibitPartialExpression
	{ $current=$iv_ruleSTMultibitPartialExpression.current; }
	EOF;

// Rule STMultibitPartialExpression
ruleSTMultibitPartialExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0());
				}
				lv_specifier_1_0=ruleSTMultiBitAccessSpecifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTMultibitPartialExpressionRule());
					}
					set(
						$current,
						"specifier",
						lv_specifier_1_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STMultiBitAccessSpecifier");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			(
				(
					lv_index_2_0=RULE_INT
					{
						newLeafNode(lv_index_2_0, grammarAccess.getSTMultibitPartialExpressionAccess().getIndexINTTerminalRuleCall_2_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTMultibitPartialExpressionRule());
						}
						setWithLastConsumed(
							$current,
							"index",
							lv_index_2_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.INT");
					}
				)
			)
			    |
			(
				otherlv_3=LeftParenthesis
				{
					newLeafNode(otherlv_3, grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0());
						}
						lv_expression_4_0=ruleSTExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSTMultibitPartialExpressionRule());
							}
							set(
								$current,
								"expression",
								lv_expression_4_0,
								"org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_5=RightParenthesis
				{
					newLeafNode(otherlv_5, grammarAccess.getSTMultibitPartialExpressionAccess().getRightParenthesisKeyword_2_1_2());
				}
			)
		)
	)
;

// Entry rule entryRuleSTLiteralExpressions
entryRuleSTLiteralExpressions returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTLiteralExpressionsRule()); }
	iv_ruleSTLiteralExpressions=ruleSTLiteralExpressions
	{ $current=$iv_ruleSTLiteralExpressions.current; }
	EOF;

// Rule STLiteralExpressions
ruleSTLiteralExpressions returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTNumericLiteralParserRuleCall_0());
		}
		this_STNumericLiteral_0=ruleSTNumericLiteral
		{
			$current = $this_STNumericLiteral_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTDateLiteralParserRuleCall_1());
		}
		this_STDateLiteral_1=ruleSTDateLiteral
		{
			$current = $this_STDateLiteral_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTTimeLiteralParserRuleCall_2());
		}
		this_STTimeLiteral_2=ruleSTTimeLiteral
		{
			$current = $this_STTimeLiteral_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTTimeOfDayLiteralParserRuleCall_3());
		}
		this_STTimeOfDayLiteral_3=ruleSTTimeOfDayLiteral
		{
			$current = $this_STTimeOfDayLiteral_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTDateAndTimeLiteralParserRuleCall_4());
		}
		this_STDateAndTimeLiteral_4=ruleSTDateAndTimeLiteral
		{
			$current = $this_STDateAndTimeLiteral_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTStringLiteralParserRuleCall_5());
		}
		this_STStringLiteral_5=ruleSTStringLiteral
		{
			$current = $this_STStringLiteral_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTLiteralExpressionsAccess().getSTEnumLiteralParserRuleCall_6());
		}
		this_STEnumLiteral_6=ruleSTEnumLiteral
		{
			$current = $this_STEnumLiteral_6.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTSignedLiteralExpressions
entryRuleSTSignedLiteralExpressions returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTSignedLiteralExpressionsRule()); }
	iv_ruleSTSignedLiteralExpressions=ruleSTSignedLiteralExpressions
	{ $current=$iv_ruleSTSignedLiteralExpressions.current; }
	EOF;

// Rule STSignedLiteralExpressions
ruleSTSignedLiteralExpressions returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		newCompositeNode(grammarAccess.getSTSignedLiteralExpressionsAccess().getSTSignedNumericLiteralParserRuleCall());
	}
	this_STSignedNumericLiteral_0=ruleSTSignedNumericLiteral
	{
		$current = $this_STSignedNumericLiteral_0.current;
		afterParserOrEnumRuleCall();
	}
;

// Entry rule entryRuleSTNumericLiteralType
entryRuleSTNumericLiteralType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTNumericLiteralTypeRule()); }
	iv_ruleSTNumericLiteralType=ruleSTNumericLiteralType
	{ $current=$iv_ruleSTNumericLiteralType.current.getText(); }
	EOF;

// Rule STNumericLiteralType
ruleSTNumericLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTNumericLiteralTypeAccess().getSTAnyBitTypeParserRuleCall_0());
		}
		this_STAnyBitType_0=ruleSTAnyBitType
		{
			$current.merge(this_STAnyBitType_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTNumericLiteralTypeAccess().getSTAnyNumTypeParserRuleCall_1());
		}
		this_STAnyNumType_1=ruleSTAnyNumType
		{
			$current.merge(this_STAnyNumType_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTNumericLiteral
entryRuleSTNumericLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTNumericLiteralRule()); }
	iv_ruleSTNumericLiteral=ruleSTNumericLiteral
	{ $current=$iv_ruleSTNumericLiteral.current; }
	EOF;

// Rule STNumericLiteral
ruleSTNumericLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTNumericLiteralRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
					}
					ruleSTNumericLiteralType
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_1=NumberSign
			{
				newLeafNode(otherlv_1, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueSignedNumericParserRuleCall_0_2_0());
					}
					lv_value_2_0=ruleSignedNumeric
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
						}
						set(
							$current,
							"value",
							lv_value_2_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.SignedNumeric");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			(
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getSTNumericLiteralRule());
							}
						}
						{
							newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_1_0_0_0());
						}
						ruleSTNumericLiteralType
						{
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_4=NumberSign
				{
					newLeafNode(otherlv_4, grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_1_0_1());
				}
			)?
			(
				(
					{
						newCompositeNode(grammarAccess.getSTNumericLiteralAccess().getValueNumericParserRuleCall_1_1_0());
					}
					lv_value_5_0=ruleNumeric
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSTNumericLiteralRule());
						}
						set(
							$current,
							"value",
							lv_value_5_0,
							"org.eclipse.fordiac.ide.structuredtextcore.STCore.Numeric");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleSTSignedNumericLiteral
entryRuleSTSignedNumericLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTSignedNumericLiteralRule()); }
	iv_ruleSTSignedNumericLiteral=ruleSTSignedNumericLiteral
	{ $current=$iv_ruleSTSignedNumericLiteral.current; }
	EOF;

// Rule STSignedNumericLiteral
ruleSTSignedNumericLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getSTSignedNumericLiteralAccess().getValueSignedNumericParserRuleCall_0());
			}
			lv_value_0_0=ruleSignedNumeric
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getSTSignedNumericLiteralRule());
				}
				set(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.fordiac.ide.structuredtextcore.STCore.SignedNumeric");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleSTDateLiteralType
entryRuleSTDateLiteralType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTDateLiteralTypeRule()); }
	iv_ruleSTDateLiteralType=ruleSTDateLiteralType
	{ $current=$iv_ruleSTDateLiteralType.current.getText(); }
	EOF;

// Rule STDateLiteralType
ruleSTDateLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTDateLiteralTypeAccess().getSTDateTypeParserRuleCall_0());
		}
		this_STDateType_0=ruleSTDateType
		{
			$current.merge(this_STDateType_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		kw=D
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1());
		}
		    |
		kw=LD
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTDateLiteralTypeAccess().getLDKeyword_2());
		}
	)
;

// Entry rule entryRuleSTDateLiteral
entryRuleSTDateLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTDateLiteralRule()); }
	iv_ruleSTDateLiteral=ruleSTDateLiteral
	{ $current=$iv_ruleSTDateLiteral.current; }
	EOF;

// Rule STDateLiteral
ruleSTDateLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTDateLiteralRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0());
				}
				ruleSTDateLiteralType
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=NumberSign
		{
			newLeafNode(otherlv_1, grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTDateLiteralAccess().getValueDateParserRuleCall_2_0());
				}
				lv_value_2_0=ruleDate
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTDateLiteralRule());
					}
					set(
						$current,
						"value",
						lv_value_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.Date");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSTTimeLiteralType
entryRuleSTTimeLiteralType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTTimeLiteralTypeRule()); }
	iv_ruleSTTimeLiteralType=ruleSTTimeLiteralType
	{ $current=$iv_ruleSTTimeLiteralType.current.getText(); }
	EOF;

// Rule STTimeLiteralType
ruleSTTimeLiteralType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTTimeLiteralTypeAccess().getSTAnyDurationTypeParserRuleCall_0());
		}
		this_STAnyDurationType_0=ruleSTAnyDurationType
		{
			$current.merge(this_STAnyDurationType_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		kw=T
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1());
		}
		    |
		kw=LT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTTimeLiteralTypeAccess().getLTKeyword_2());
		}
	)
;

// Entry rule entryRuleSTTimeLiteral
entryRuleSTTimeLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTTimeLiteralRule()); }
	iv_ruleSTTimeLiteral=ruleSTTimeLiteral
	{ $current=$iv_ruleSTTimeLiteral.current; }
	EOF;

// Rule STTimeLiteral
ruleSTTimeLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTTimeLiteralRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
				}
				ruleSTTimeLiteralType
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=NumberSign
		{
			newLeafNode(otherlv_1, grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTTimeLiteralAccess().getValueTimeParserRuleCall_2_0());
				}
				lv_value_2_0=ruleTime
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTTimeLiteralRule());
					}
					set(
						$current,
						"value",
						lv_value_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.Time");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSTTimeOfDayLiteral
entryRuleSTTimeOfDayLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTTimeOfDayLiteralRule()); }
	iv_ruleSTTimeOfDayLiteral=ruleSTTimeOfDayLiteral
	{ $current=$iv_ruleSTTimeOfDayLiteral.current; }
	EOF;

// Rule STTimeOfDayLiteral
ruleSTTimeOfDayLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTTimeOfDayLiteralRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0());
				}
				ruleSTTimeOfDayType
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=NumberSign
		{
			newLeafNode(otherlv_1, grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTTimeOfDayLiteralAccess().getValueTimeOfDayParserRuleCall_2_0());
				}
				lv_value_2_0=ruleTimeOfDay
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTTimeOfDayLiteralRule());
					}
					set(
						$current,
						"value",
						lv_value_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.TimeOfDay");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSTDateAndTimeLiteral
entryRuleSTDateAndTimeLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTDateAndTimeLiteralRule()); }
	iv_ruleSTDateAndTimeLiteral=ruleSTDateAndTimeLiteral
	{ $current=$iv_ruleSTDateAndTimeLiteral.current; }
	EOF;

// Rule STDateAndTimeLiteral
ruleSTDateAndTimeLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTDateAndTimeLiteralRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0());
				}
				ruleSTDateAndTimeType
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=NumberSign
		{
			newLeafNode(otherlv_1, grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSTDateAndTimeLiteralAccess().getValueDateAndTimeParserRuleCall_2_0());
				}
				lv_value_2_0=ruleDateAndTime
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSTDateAndTimeLiteralRule());
					}
					set(
						$current,
						"value",
						lv_value_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.DateAndTime");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSTStringLiteral
entryRuleSTStringLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTStringLiteralRule()); }
	iv_ruleSTStringLiteral=ruleSTStringLiteral
	{ $current=$iv_ruleSTStringLiteral.current; }
	EOF;

// Rule STStringLiteral
ruleSTStringLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSTStringLiteralRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0());
					}
					ruleSTAnyCharsType
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_1=NumberSign
			{
				newLeafNode(otherlv_1, grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1());
			}
		)?
		(
			(
				lv_value_2_0=RULE_STRING
				{
					newLeafNode(lv_value_2_0, grammarAccess.getSTStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSTStringLiteralRule());
					}
					setWithLastConsumed(
						$current,
						"value",
						lv_value_2_0,
						"org.eclipse.fordiac.ide.structuredtextcore.STCore.STRING");
				}
			)
		)
	)
;

// Entry rule entryRuleSTEnumLiteral
entryRuleSTEnumLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSTEnumLiteralRule()); }
	iv_ruleSTEnumLiteral=ruleSTEnumLiteral
	{ $current=$iv_ruleSTEnumLiteral.current; }
	EOF;

// Rule STEnumLiteral
ruleSTEnumLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getSTEnumLiteralRule());
				}
			}
			{
				newCompositeNode(grammarAccess.getSTEnumLiteralAccess().getValueEnumeratedValueCrossReference_0());
			}
			ruleEnumValue
			{
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleEnumValue
entryRuleEnumValue returns [String current=null]:
	{ newCompositeNode(grammarAccess.getEnumValueRule()); }
	iv_ruleEnumValue=ruleEnumValue
	{ $current=$iv_ruleEnumValue.current.getText(); }
	EOF;

// Rule EnumValue
ruleEnumValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getEnumValueAccess().getQualifiedNameParserRuleCall_0());
		}
		this_QualifiedName_0=ruleQualifiedName
		{
			$current.merge(this_QualifiedName_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		kw=NumberSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getEnumValueAccess().getNumberSignKeyword_1());
		}
		this_ID_2=RULE_ID
		{
			$current.merge(this_ID_2);
		}
		{
			newLeafNode(this_ID_2, grammarAccess.getEnumValueAccess().getIDTerminalRuleCall_2());
		}
	)
;

// Entry rule entryRuleSTAnyType
entryRuleSTAnyType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTAnyTypeRule()); }
	iv_ruleSTAnyType=ruleSTAnyType
	{ $current=$iv_ruleSTAnyType.current.getText(); }
	EOF;

// Rule STAnyType
ruleSTAnyType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTAnyTypeAccess().getQualifiedNameParserRuleCall_0());
		}
		this_QualifiedName_0=ruleQualifiedName
		{
			$current.merge(this_QualifiedName_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTAnyTypeAccess().getSTAnyBuiltinTypeParserRuleCall_1());
		}
		this_STAnyBuiltinType_1=ruleSTAnyBuiltinType
		{
			$current.merge(this_STAnyBuiltinType_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTAnyBuiltinType
entryRuleSTAnyBuiltinType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTAnyBuiltinTypeRule()); }
	iv_ruleSTAnyBuiltinType=ruleSTAnyBuiltinType
	{ $current=$iv_ruleSTAnyBuiltinType.current.getText(); }
	EOF;

// Rule STAnyBuiltinType
ruleSTAnyBuiltinType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyBitTypeParserRuleCall_0());
		}
		this_STAnyBitType_0=ruleSTAnyBitType
		{
			$current.merge(this_STAnyBitType_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyNumTypeParserRuleCall_1());
		}
		this_STAnyNumType_1=ruleSTAnyNumType
		{
			$current.merge(this_STAnyNumType_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyDurationTypeParserRuleCall_2());
		}
		this_STAnyDurationType_2=ruleSTAnyDurationType
		{
			$current.merge(this_STAnyDurationType_2);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyDateTypeParserRuleCall_3());
		}
		this_STAnyDateType_3=ruleSTAnyDateType
		{
			$current.merge(this_STAnyDateType_3);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyCharsTypeParserRuleCall_4());
		}
		this_STAnyCharsType_4=ruleSTAnyCharsType
		{
			$current.merge(this_STAnyCharsType_4);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTAnyBitType
entryRuleSTAnyBitType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTAnyBitTypeRule()); }
	iv_ruleSTAnyBitType=ruleSTAnyBitType
	{ $current=$iv_ruleSTAnyBitType.current.getText(); }
	EOF;

// Rule STAnyBitType
ruleSTAnyBitType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=BOOL
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0());
		}
		    |
		kw=BYTE
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1());
		}
		    |
		kw=WORD
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2());
		}
		    |
		kw=DWORD
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3());
		}
		    |
		kw=LWORD
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyBitTypeAccess().getLWORDKeyword_4());
		}
	)
;

// Entry rule entryRuleSTAnyNumType
entryRuleSTAnyNumType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTAnyNumTypeRule()); }
	iv_ruleSTAnyNumType=ruleSTAnyNumType
	{ $current=$iv_ruleSTAnyNumType.current.getText(); }
	EOF;

// Rule STAnyNumType
ruleSTAnyNumType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=SINT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0());
		}
		    |
		kw=INT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1());
		}
		    |
		kw=DINT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2());
		}
		    |
		kw=LINT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3());
		}
		    |
		kw=USINT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4());
		}
		    |
		kw=UINT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5());
		}
		    |
		kw=UDINT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6());
		}
		    |
		kw=ULINT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7());
		}
		    |
		kw=REAL
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8());
		}
		    |
		kw=LREAL
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyNumTypeAccess().getLREALKeyword_9());
		}
	)
;

// Entry rule entryRuleSTAnyDurationType
entryRuleSTAnyDurationType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTAnyDurationTypeRule()); }
	iv_ruleSTAnyDurationType=ruleSTAnyDurationType
	{ $current=$iv_ruleSTAnyDurationType.current.getText(); }
	EOF;

// Rule STAnyDurationType
ruleSTAnyDurationType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=TIME
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0());
		}
		    |
		kw=LTIME
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyDurationTypeAccess().getLTIMEKeyword_1());
		}
	)
;

// Entry rule entryRuleSTAnyDateType
entryRuleSTAnyDateType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTAnyDateTypeRule()); }
	iv_ruleSTAnyDateType=ruleSTAnyDateType
	{ $current=$iv_ruleSTAnyDateType.current.getText(); }
	EOF;

// Rule STAnyDateType
ruleSTAnyDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getSTAnyDateTypeAccess().getSTDateTypeParserRuleCall_0());
		}
		this_STDateType_0=ruleSTDateType
		{
			$current.merge(this_STDateType_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTAnyDateTypeAccess().getSTTimeOfDayTypeParserRuleCall_1());
		}
		this_STTimeOfDayType_1=ruleSTTimeOfDayType
		{
			$current.merge(this_STTimeOfDayType_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getSTAnyDateTypeAccess().getSTDateAndTimeTypeParserRuleCall_2());
		}
		this_STDateAndTimeType_2=ruleSTDateAndTimeType
		{
			$current.merge(this_STDateAndTimeType_2);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSTDateType
entryRuleSTDateType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTDateTypeRule()); }
	iv_ruleSTDateType=ruleSTDateType
	{ $current=$iv_ruleSTDateType.current.getText(); }
	EOF;

// Rule STDateType
ruleSTDateType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=DATE
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getDATEKeyword_0());
		}
		    |
		kw=LDATE
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTDateTypeAccess().getLDATEKeyword_1());
		}
	)
;

// Entry rule entryRuleSTTimeOfDayType
entryRuleSTTimeOfDayType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTTimeOfDayTypeRule()); }
	iv_ruleSTTimeOfDayType=ruleSTTimeOfDayType
	{ $current=$iv_ruleSTTimeOfDayType.current.getText(); }
	EOF;

// Rule STTimeOfDayType
ruleSTTimeOfDayType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=TIME_OF_DAY
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0());
		}
		    |
		kw=LTIME_OF_DAY
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1());
		}
		    |
		kw=TOD
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2());
		}
		    |
		kw=LTOD
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTTimeOfDayTypeAccess().getLTODKeyword_3());
		}
	)
;

// Entry rule entryRuleSTDateAndTimeType
entryRuleSTDateAndTimeType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTDateAndTimeTypeRule()); }
	iv_ruleSTDateAndTimeType=ruleSTDateAndTimeType
	{ $current=$iv_ruleSTDateAndTimeType.current.getText(); }
	EOF;

// Rule STDateAndTimeType
ruleSTDateAndTimeType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=DATE_AND_TIME
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0());
		}
		    |
		kw=LDATE_AND_TIME
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1());
		}
		    |
		kw=DT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2());
		}
		    |
		kw=LDT
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTDateAndTimeTypeAccess().getLDTKeyword_3());
		}
	)
;

// Entry rule entryRuleSTAnyCharsType
entryRuleSTAnyCharsType returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSTAnyCharsTypeRule()); }
	iv_ruleSTAnyCharsType=ruleSTAnyCharsType
	{ $current=$iv_ruleSTAnyCharsType.current.getText(); }
	EOF;

// Rule STAnyCharsType
ruleSTAnyCharsType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=STRING
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0());
		}
		    |
		kw=WSTRING
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1());
		}
		    |
		kw=CHAR
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2());
		}
		    |
		kw=WCHAR
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSTAnyCharsTypeAccess().getWCHARKeyword_3());
		}
	)
;

// Entry rule entryRuleQualifiedName
entryRuleQualifiedName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getQualifiedNameRule()); }
	iv_ruleQualifiedName=ruleQualifiedName
	{ $current=$iv_ruleQualifiedName.current.getText(); }
	EOF;

// Rule QualifiedName
ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_ID_0=RULE_ID
		{
			$current.merge(this_ID_0);
		}
		{
			newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0());
		}
		(
			kw=ColonColon
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getColonColonKeyword_1_0());
			}
			this_ID_2=RULE_ID
			{
				$current.merge(this_ID_2);
			}
			{
				newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1());
			}
		)*
	)
;

// Entry rule entryRuleQualifiedNameWithWildcard
entryRuleQualifiedNameWithWildcard returns [String current=null]:
	{ newCompositeNode(grammarAccess.getQualifiedNameWithWildcardRule()); }
	iv_ruleQualifiedNameWithWildcard=ruleQualifiedNameWithWildcard
	{ $current=$iv_ruleQualifiedNameWithWildcard.current.getText(); }
	EOF;

// Rule QualifiedNameWithWildcard
ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());
		}
		this_QualifiedName_0=ruleQualifiedName
		{
			$current.merge(this_QualifiedName_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		(
			kw=ColonColonAsterisk
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getColonColonAsteriskKeyword_1());
			}
		)?
	)
;

// Entry rule entryRuleNumeric
entryRuleNumeric returns [String current=null]:
	{ newCompositeNode(grammarAccess.getNumericRule()); }
	iv_ruleNumeric=ruleNumeric
	{ $current=$iv_ruleNumeric.current.getText(); }
	EOF;

// Rule Numeric
ruleNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=TRUE
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getNumericAccess().getTRUEKeyword_0());
		}
		    |
		kw=FALSE
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getNumericAccess().getFALSEKeyword_1());
		}
		    |
		{
			newCompositeNode(grammarAccess.getNumericAccess().getNumberParserRuleCall_2());
		}
		this_Number_2=ruleNumber
		{
			$current.merge(this_Number_2);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		this_NON_DECIMAL_3=RULE_NON_DECIMAL
		{
			$current.merge(this_NON_DECIMAL_3);
		}
		{
			newLeafNode(this_NON_DECIMAL_3, grammarAccess.getNumericAccess().getNON_DECIMALTerminalRuleCall_3());
		}
	)
;

// Entry rule entryRuleNumber
entryRuleNumber returns [String current=null]@init {
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}:
	{ newCompositeNode(grammarAccess.getNumberRule()); }
	iv_ruleNumber=ruleNumber
	{ $current=$iv_ruleNumber.current.getText(); }
	EOF;
finally {
	myHiddenTokenState.restore();
}

// Rule Number
ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}
@after {
	leaveRule();
}:
	(
		this_INT_0=RULE_INT
		{
			$current.merge(this_INT_0);
		}
		{
			newLeafNode(this_INT_0, grammarAccess.getNumberAccess().getINTTerminalRuleCall_0());
		}
		(
			kw=FullStop
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_1_0());
			}
			(
				this_INT_2=RULE_INT
				{
					$current.merge(this_INT_2);
				}
				{
					newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_1_0());
				}
				    |
				this_DECIMAL_3=RULE_DECIMAL
				{
					$current.merge(this_DECIMAL_3);
				}
				{
					newLeafNode(this_DECIMAL_3, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_1_1());
				}
			)
		)?
	)
;
finally {
	myHiddenTokenState.restore();
}

// Entry rule entryRuleSignedNumeric
entryRuleSignedNumeric returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSignedNumericRule()); }
	iv_ruleSignedNumeric=ruleSignedNumeric
	{ $current=$iv_ruleSignedNumeric.current.getText(); }
	EOF;

// Rule SignedNumeric
ruleSignedNumeric returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		newCompositeNode(grammarAccess.getSignedNumericAccess().getSignedNumberParserRuleCall());
	}
	this_SignedNumber_0=ruleSignedNumber
	{
		$current.merge(this_SignedNumber_0);
	}
	{
		afterParserOrEnumRuleCall();
	}
;

// Entry rule entryRuleSignedNumber
entryRuleSignedNumber returns [String current=null]@init {
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}:
	{ newCompositeNode(grammarAccess.getSignedNumberRule()); }
	iv_ruleSignedNumber=ruleSignedNumber
	{ $current=$iv_ruleSignedNumber.current.getText(); }
	EOF;
finally {
	myHiddenTokenState.restore();
}

// Rule SignedNumber
ruleSignedNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}
@after {
	leaveRule();
}:
	(
		(
			kw=PlusSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getPlusSignKeyword_0_0());
			}
			    |
			kw=HyphenMinus
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getHyphenMinusKeyword_0_1());
			}
		)
		this_INT_2=RULE_INT
		{
			$current.merge(this_INT_2);
		}
		{
			newLeafNode(this_INT_2, grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_1());
		}
		(
			kw=FullStop
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getSignedNumberAccess().getFullStopKeyword_2_0());
			}
			(
				this_INT_4=RULE_INT
				{
					$current.merge(this_INT_4);
				}
				{
					newLeafNode(this_INT_4, grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_2_1_0());
				}
				    |
				this_DECIMAL_5=RULE_DECIMAL
				{
					$current.merge(this_DECIMAL_5);
				}
				{
					newLeafNode(this_DECIMAL_5, grammarAccess.getSignedNumberAccess().getDECIMALTerminalRuleCall_2_1_1());
				}
			)
		)?
	)
;
finally {
	myHiddenTokenState.restore();
}

// Entry rule entryRuleTime
entryRuleTime returns [String current=null]@init {
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}:
	{ newCompositeNode(grammarAccess.getTimeRule()); }
	iv_ruleTime=ruleTime
	{ $current=$iv_ruleTime.current.getText(); }
	EOF;
finally {
	myHiddenTokenState.restore();
}

// Rule Time
ruleTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}
@after {
	leaveRule();
}:
	(
		(
			kw=PlusSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getTimeAccess().getPlusSignKeyword_0_0());
			}
			    |
			kw=HyphenMinus
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getTimeAccess().getHyphenMinusKeyword_0_1());
			}
		)?
		this_TIME_VALUE_2=RULE_TIME_VALUE
		{
			$current.merge(this_TIME_VALUE_2);
		}
		{
			newLeafNode(this_TIME_VALUE_2, grammarAccess.getTimeAccess().getTIME_VALUETerminalRuleCall_1());
		}
	)
;
finally {
	myHiddenTokenState.restore();
}

// Entry rule entryRuleDate
entryRuleDate returns [String current=null]:
	{ newCompositeNode(grammarAccess.getDateRule()); }
	iv_ruleDate=ruleDate
	{ $current=$iv_ruleDate.current.getText(); }
	EOF;

// Rule Date
ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_INT_0=RULE_INT
		{
			$current.merge(this_INT_0);
		}
		{
			newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());
		}
		kw=HyphenMinus
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_1());
		}
		this_INT_2=RULE_INT
		{
			$current.merge(this_INT_2);
		}
		{
			newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());
		}
		kw=HyphenMinus
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getDateAccess().getHyphenMinusKeyword_3());
		}
		this_INT_4=RULE_INT
		{
			$current.merge(this_INT_4);
		}
		{
			newLeafNode(this_INT_4, grammarAccess.getDateAccess().getINTTerminalRuleCall_4());
		}
	)
;

// Entry rule entryRuleDateAndTime
entryRuleDateAndTime returns [String current=null]:
	{ newCompositeNode(grammarAccess.getDateAndTimeRule()); }
	iv_ruleDateAndTime=ruleDateAndTime
	{ $current=$iv_ruleDateAndTime.current.getText(); }
	EOF;

// Rule DateAndTime
ruleDateAndTime returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_INT_0=RULE_INT
		{
			$current.merge(this_INT_0);
		}
		{
			newLeafNode(this_INT_0, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0());
		}
		kw=HyphenMinus
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1());
		}
		this_INT_2=RULE_INT
		{
			$current.merge(this_INT_2);
		}
		{
			newLeafNode(this_INT_2, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2());
		}
		kw=HyphenMinus
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3());
		}
		this_INT_4=RULE_INT
		{
			$current.merge(this_INT_4);
		}
		{
			newLeafNode(this_INT_4, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4());
		}
		kw=HyphenMinus
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_5());
		}
		this_INT_6=RULE_INT
		{
			$current.merge(this_INT_6);
		}
		{
			newLeafNode(this_INT_6, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_6());
		}
		kw=Colon
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_7());
		}
		this_INT_8=RULE_INT
		{
			$current.merge(this_INT_8);
		}
		{
			newLeafNode(this_INT_8, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_8());
		}
		kw=Colon
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getColonKeyword_9());
		}
		this_INT_10=RULE_INT
		{
			$current.merge(this_INT_10);
		}
		{
			newLeafNode(this_INT_10, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10());
		}
		(
			kw=FullStop
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getDateAndTimeAccess().getFullStopKeyword_11_0());
			}
			this_INT_12=RULE_INT
			{
				$current.merge(this_INT_12);
			}
			{
				newLeafNode(this_INT_12, grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_11_1());
			}
		)?
	)
;

// Entry rule entryRuleTimeOfDay
entryRuleTimeOfDay returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTimeOfDayRule()); }
	iv_ruleTimeOfDay=ruleTimeOfDay
	{ $current=$iv_ruleTimeOfDay.current.getText(); }
	EOF;

// Rule TimeOfDay
ruleTimeOfDay returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_INT_0=RULE_INT
		{
			$current.merge(this_INT_0);
		}
		{
			newLeafNode(this_INT_0, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0());
		}
		kw=Colon
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_1());
		}
		this_INT_2=RULE_INT
		{
			$current.merge(this_INT_2);
		}
		{
			newLeafNode(this_INT_2, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_2());
		}
		kw=Colon
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getColonKeyword_3());
		}
		this_INT_4=RULE_INT
		{
			$current.merge(this_INT_4);
		}
		{
			newLeafNode(this_INT_4, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4());
		}
		(
			kw=FullStop
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getTimeOfDayAccess().getFullStopKeyword_5_0());
			}
			this_INT_6=RULE_INT
			{
				$current.merge(this_INT_6);
			}
			{
				newLeafNode(this_INT_6, grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_5_1());
			}
		)?
	)
;

// Rule SubrangeOperator
ruleSubrangeOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		enumLiteral_0=FullStopFullStop
		{
			$current = grammarAccess.getSubrangeOperatorAccess().getRangeEnumLiteralDeclaration().getEnumLiteral().getInstance();
			newLeafNode(enumLiteral_0, grammarAccess.getSubrangeOperatorAccess().getRangeEnumLiteralDeclaration());
		}
	)
;

// Rule OrOperator
ruleOrOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		enumLiteral_0=OR
		{
			$current = grammarAccess.getOrOperatorAccess().getOREnumLiteralDeclaration().getEnumLiteral().getInstance();
			newLeafNode(enumLiteral_0, grammarAccess.getOrOperatorAccess().getOREnumLiteralDeclaration());
		}
	)
;

// Rule XorOperator
ruleXorOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		enumLiteral_0=XOR
		{
			$current = grammarAccess.getXorOperatorAccess().getXOREnumLiteralDeclaration().getEnumLiteral().getInstance();
			newLeafNode(enumLiteral_0, grammarAccess.getXorOperatorAccess().getXOREnumLiteralDeclaration());
		}
	)
;

// Rule AndOperator
ruleAndOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=AND
			{
				$current = grammarAccess.getAndOperatorAccess().getANDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getAndOperatorAccess().getANDEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Ampersand
			{
				$current = grammarAccess.getAndOperatorAccess().getAMPERSANDEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getAndOperatorAccess().getAMPERSANDEnumLiteralDeclaration_1());
			}
		)
	)
;

// Rule EqualityOperator
ruleEqualityOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=EqualsSign
			{
				$current = grammarAccess.getEqualityOperatorAccess().getEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getEqualityOperatorAccess().getEQEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=LessThanSignGreaterThanSign
			{
				$current = grammarAccess.getEqualityOperatorAccess().getNEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getEqualityOperatorAccess().getNEEnumLiteralDeclaration_1());
			}
		)
	)
;

// Rule CompareOperator
ruleCompareOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=LessThanSign
			{
				$current = grammarAccess.getCompareOperatorAccess().getLTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getCompareOperatorAccess().getLTEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=LessThanSignEqualsSign
			{
				$current = grammarAccess.getCompareOperatorAccess().getLEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getCompareOperatorAccess().getLEEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=GreaterThanSign
			{
				$current = grammarAccess.getCompareOperatorAccess().getGTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getCompareOperatorAccess().getGTEnumLiteralDeclaration_2());
			}
		)
		    |
		(
			enumLiteral_3=GreaterThanSignEqualsSign
			{
				$current = grammarAccess.getCompareOperatorAccess().getGEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_3, grammarAccess.getCompareOperatorAccess().getGEEnumLiteralDeclaration_3());
			}
		)
	)
;

// Rule AddSubOperator
ruleAddSubOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=PlusSign
			{
				$current = grammarAccess.getAddSubOperatorAccess().getADDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getAddSubOperatorAccess().getADDEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=HyphenMinus
			{
				$current = grammarAccess.getAddSubOperatorAccess().getSUBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getAddSubOperatorAccess().getSUBEnumLiteralDeclaration_1());
			}
		)
	)
;

// Rule MulDivModOperator
ruleMulDivModOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Asterisk
			{
				$current = grammarAccess.getMulDivModOperatorAccess().getMULEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getMulDivModOperatorAccess().getMULEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Solidus
			{
				$current = grammarAccess.getMulDivModOperatorAccess().getDIVEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getMulDivModOperatorAccess().getDIVEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=MOD
			{
				$current = grammarAccess.getMulDivModOperatorAccess().getMODEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getMulDivModOperatorAccess().getMODEnumLiteralDeclaration_2());
			}
		)
	)
;

// Rule PowerOperator
rulePowerOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		enumLiteral_0=AsteriskAsterisk
		{
			$current = grammarAccess.getPowerOperatorAccess().getPOWEREnumLiteralDeclaration().getEnumLiteral().getInstance();
			newLeafNode(enumLiteral_0, grammarAccess.getPowerOperatorAccess().getPOWEREnumLiteralDeclaration());
		}
	)
;

// Rule UnaryOperator
ruleUnaryOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=HyphenMinus
			{
				$current = grammarAccess.getUnaryOperatorAccess().getMINUSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getUnaryOperatorAccess().getMINUSEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=PlusSign
			{
				$current = grammarAccess.getUnaryOperatorAccess().getPLUSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getUnaryOperatorAccess().getPLUSEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=NOT
			{
				$current = grammarAccess.getUnaryOperatorAccess().getNOTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getUnaryOperatorAccess().getNOTEnumLiteralDeclaration_2());
			}
		)
	)
;

// Rule STBuiltinFeature
ruleSTBuiltinFeature returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		enumLiteral_0=THIS
		{
			$current = grammarAccess.getSTBuiltinFeatureAccess().getTHISEnumLiteralDeclaration().getEnumLiteral().getInstance();
			newLeafNode(enumLiteral_0, grammarAccess.getSTBuiltinFeatureAccess().getTHISEnumLiteralDeclaration());
		}
	)
;

// Rule STMultiBitAccessSpecifier
ruleSTMultiBitAccessSpecifier returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=L
			{
				$current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getLEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getSTMultiBitAccessSpecifierAccess().getLEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=D_1
			{
				$current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getDEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getSTMultiBitAccessSpecifierAccess().getDEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=W
			{
				$current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getWEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getSTMultiBitAccessSpecifierAccess().getWEnumLiteralDeclaration_2());
			}
		)
		    |
		(
			enumLiteral_3=B
			{
				$current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getBEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_3, grammarAccess.getSTMultiBitAccessSpecifierAccess().getBEnumLiteralDeclaration_3());
			}
		)
		    |
		(
			enumLiteral_4=X
			{
				$current = grammarAccess.getSTMultiBitAccessSpecifierAccess().getXEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_4, grammarAccess.getSTMultiBitAccessSpecifierAccess().getXEnumLiteralDeclaration_4());
			}
		)
	)
;
