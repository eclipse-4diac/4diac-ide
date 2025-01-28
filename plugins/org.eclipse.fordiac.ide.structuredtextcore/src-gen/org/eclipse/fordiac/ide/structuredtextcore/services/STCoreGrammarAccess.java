/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies GmbH, 
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.service.AbstractElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class STCoreGrammarAccess extends AbstractElementFinder.AbstractGrammarElementFinder {
	
	public class STCoreSourceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STCoreSource");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTCoreSourceAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cStatementsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cStatementsSTStatementParserRuleCall_1_0 = (RuleCall)cStatementsAssignment_1.eContents().get(0);
		
		//STCoreSource returns STSource:
		//    {STCoreSource} statements+=STStatement*;
		@Override public ParserRule getRule() { return rule; }
		
		//{STCoreSource} statements+=STStatement*
		public Group getGroup() { return cGroup; }
		
		//{STCoreSource}
		public Action getSTCoreSourceAction_0() { return cSTCoreSourceAction_0; }
		
		//statements+=STStatement*
		public Assignment getStatementsAssignment_1() { return cStatementsAssignment_1; }
		
		//STStatement
		public RuleCall getStatementsSTStatementParserRuleCall_1_0() { return cStatementsSTStatementParserRuleCall_1_0; }
	}
	public class STExpressionSourceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpressionSource");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTExpressionSourceAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionSTExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		
		//STExpressionSource returns STSource:
		//    {STExpressionSource} expression=STExpression?;
		@Override public ParserRule getRule() { return rule; }
		
		//{STExpressionSource} expression=STExpression?
		public Group getGroup() { return cGroup; }
		
		//{STExpressionSource}
		public Action getSTExpressionSourceAction_0() { return cSTExpressionSourceAction_0; }
		
		//expression=STExpression?
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//STExpression
		public RuleCall getExpressionSTExpressionParserRuleCall_1_0() { return cExpressionSTExpressionParserRuleCall_1_0; }
	}
	public class STExpressionSource0Elements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpressionSource0");
		private final RuleCall cSTExpressionSourceParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//STExpressionSource0 returns STSource:
		//    STExpressionSource;
		@Override public ParserRule getRule() { return rule; }
		
		//STExpressionSource
		public RuleCall getSTExpressionSourceParserRuleCall() { return cSTExpressionSourceParserRuleCall; }
	}
	public class STInitializerExpressionSourceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpressionSource");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTInitializerExpressionSourceAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cInitializerExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cInitializerExpressionSTInitializerExpressionParserRuleCall_1_0 = (RuleCall)cInitializerExpressionAssignment_1.eContents().get(0);
		
		// // necessary to keep Xtext from skipping this rule
		//STInitializerExpressionSource returns STSource:
		//    {STInitializerExpressionSource} initializerExpression=STInitializerExpression?;
		@Override public ParserRule getRule() { return rule; }
		
		//{STInitializerExpressionSource} initializerExpression=STInitializerExpression?
		public Group getGroup() { return cGroup; }
		
		//{STInitializerExpressionSource}
		public Action getSTInitializerExpressionSourceAction_0() { return cSTInitializerExpressionSourceAction_0; }
		
		//initializerExpression=STInitializerExpression?
		public Assignment getInitializerExpressionAssignment_1() { return cInitializerExpressionAssignment_1; }
		
		//STInitializerExpression
		public RuleCall getInitializerExpressionSTInitializerExpressionParserRuleCall_1_0() { return cInitializerExpressionSTInitializerExpressionParserRuleCall_1_0; }
	}
	public class STInitializerExpressionSource0Elements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpressionSource0");
		private final RuleCall cSTInitializerExpressionSourceParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//STInitializerExpressionSource0 returns STSource:
		//    STInitializerExpressionSource;
		@Override public ParserRule getRule() { return rule; }
		
		//STInitializerExpressionSource
		public RuleCall getSTInitializerExpressionSourceParserRuleCall() { return cSTInitializerExpressionSourceParserRuleCall; }
	}
	public class STImportElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STImport");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cIMPORTKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cImportedNamespaceAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0 = (RuleCall)cImportedNamespaceAssignment_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		// // necessary to keep Xtext from skipping this rule
		//STImport returns STImport:
		//    'IMPORT' importedNamespace=QualifiedNameWithWildcard ';';
		@Override public ParserRule getRule() { return rule; }
		
		//'IMPORT' importedNamespace=QualifiedNameWithWildcard ';'
		public Group getGroup() { return cGroup; }
		
		//'IMPORT'
		public Keyword getIMPORTKeyword_0() { return cIMPORTKeyword_0; }
		
		//importedNamespace=QualifiedNameWithWildcard
		public Assignment getImportedNamespaceAssignment_1() { return cImportedNamespaceAssignment_1; }
		
		//QualifiedNameWithWildcard
		public RuleCall getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0() { return cImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0; }
		
		//';'
		public Keyword getSemicolonKeyword_2() { return cSemicolonKeyword_2; }
	}
	public class STVarDeclarationBlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclarationBlock");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTVarPlainDeclarationBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cVARKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cConstantAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cConstantCONSTANTKeyword_2_0 = (Keyword)cConstantAssignment_2.eContents().get(0);
		private final Assignment cVarDeclarationsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cVarDeclarationsSTVarDeclarationParserRuleCall_3_0 = (RuleCall)cVarDeclarationsAssignment_3.eContents().get(0);
		private final Keyword cEND_VARKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STVarDeclarationBlock returns STVarPlainDeclarationBlock:
		//    {STVarPlainDeclarationBlock} 'VAR' (constant?='CONSTANT')?
		//    varDeclarations+=STVarDeclaration*
		//    'END_VAR';
		@Override public ParserRule getRule() { return rule; }
		
		//{STVarPlainDeclarationBlock} 'VAR' (constant?='CONSTANT')?
		//varDeclarations+=STVarDeclaration*
		//'END_VAR'
		public Group getGroup() { return cGroup; }
		
		//{STVarPlainDeclarationBlock}
		public Action getSTVarPlainDeclarationBlockAction_0() { return cSTVarPlainDeclarationBlockAction_0; }
		
		//'VAR'
		public Keyword getVARKeyword_1() { return cVARKeyword_1; }
		
		//(constant?='CONSTANT')?
		public Assignment getConstantAssignment_2() { return cConstantAssignment_2; }
		
		//'CONSTANT'
		public Keyword getConstantCONSTANTKeyword_2_0() { return cConstantCONSTANTKeyword_2_0; }
		
		//varDeclarations+=STVarDeclaration*
		public Assignment getVarDeclarationsAssignment_3() { return cVarDeclarationsAssignment_3; }
		
		//STVarDeclaration
		public RuleCall getVarDeclarationsSTVarDeclarationParserRuleCall_3_0() { return cVarDeclarationsSTVarDeclarationParserRuleCall_3_0; }
		
		//'END_VAR'
		public Keyword getEND_VARKeyword_4() { return cEND_VARKeyword_4; }
	}
	public class STVarTempDeclarationBlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarTempDeclarationBlock");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTVarTempDeclarationBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cVAR_TEMPKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cConstantAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cConstantCONSTANTKeyword_2_0 = (Keyword)cConstantAssignment_2.eContents().get(0);
		private final Assignment cVarDeclarationsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cVarDeclarationsSTVarDeclarationParserRuleCall_3_0 = (RuleCall)cVarDeclarationsAssignment_3.eContents().get(0);
		private final Keyword cEND_VARKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STVarTempDeclarationBlock returns STVarTempDeclarationBlock:
		//    {STVarTempDeclarationBlock} 'VAR_TEMP' (constant?='CONSTANT')?
		//    varDeclarations+=STVarDeclaration*
		//    'END_VAR';
		@Override public ParserRule getRule() { return rule; }
		
		//{STVarTempDeclarationBlock} 'VAR_TEMP' (constant?='CONSTANT')?
		//varDeclarations+=STVarDeclaration*
		//'END_VAR'
		public Group getGroup() { return cGroup; }
		
		//{STVarTempDeclarationBlock}
		public Action getSTVarTempDeclarationBlockAction_0() { return cSTVarTempDeclarationBlockAction_0; }
		
		//'VAR_TEMP'
		public Keyword getVAR_TEMPKeyword_1() { return cVAR_TEMPKeyword_1; }
		
		//(constant?='CONSTANT')?
		public Assignment getConstantAssignment_2() { return cConstantAssignment_2; }
		
		//'CONSTANT'
		public Keyword getConstantCONSTANTKeyword_2_0() { return cConstantCONSTANTKeyword_2_0; }
		
		//varDeclarations+=STVarDeclaration*
		public Assignment getVarDeclarationsAssignment_3() { return cVarDeclarationsAssignment_3; }
		
		//STVarDeclaration
		public RuleCall getVarDeclarationsSTVarDeclarationParserRuleCall_3_0() { return cVarDeclarationsSTVarDeclarationParserRuleCall_3_0; }
		
		//'END_VAR'
		public Keyword getEND_VARKeyword_4() { return cEND_VARKeyword_4; }
	}
	public class STVarInputDeclarationBlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarInputDeclarationBlock");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTVarInputDeclarationBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cVAR_INPUTKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cConstantAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cConstantCONSTANTKeyword_2_0 = (Keyword)cConstantAssignment_2.eContents().get(0);
		private final Assignment cVarDeclarationsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cVarDeclarationsSTVarDeclarationParserRuleCall_3_0 = (RuleCall)cVarDeclarationsAssignment_3.eContents().get(0);
		private final Keyword cEND_VARKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STVarInputDeclarationBlock returns STVarInputDeclarationBlock:
		//    {STVarInputDeclarationBlock} 'VAR_INPUT' (constant?='CONSTANT')?
		//    varDeclarations+=STVarDeclaration*
		//    'END_VAR';
		@Override public ParserRule getRule() { return rule; }
		
		//{STVarInputDeclarationBlock} 'VAR_INPUT' (constant?='CONSTANT')?
		//varDeclarations+=STVarDeclaration*
		//'END_VAR'
		public Group getGroup() { return cGroup; }
		
		//{STVarInputDeclarationBlock}
		public Action getSTVarInputDeclarationBlockAction_0() { return cSTVarInputDeclarationBlockAction_0; }
		
		//'VAR_INPUT'
		public Keyword getVAR_INPUTKeyword_1() { return cVAR_INPUTKeyword_1; }
		
		//(constant?='CONSTANT')?
		public Assignment getConstantAssignment_2() { return cConstantAssignment_2; }
		
		//'CONSTANT'
		public Keyword getConstantCONSTANTKeyword_2_0() { return cConstantCONSTANTKeyword_2_0; }
		
		//varDeclarations+=STVarDeclaration*
		public Assignment getVarDeclarationsAssignment_3() { return cVarDeclarationsAssignment_3; }
		
		//STVarDeclaration
		public RuleCall getVarDeclarationsSTVarDeclarationParserRuleCall_3_0() { return cVarDeclarationsSTVarDeclarationParserRuleCall_3_0; }
		
		//'END_VAR'
		public Keyword getEND_VARKeyword_4() { return cEND_VARKeyword_4; }
	}
	public class STVarOutputDeclarationBlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarOutputDeclarationBlock");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTVarOutputDeclarationBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cVAR_OUTPUTKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cConstantAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cConstantCONSTANTKeyword_2_0 = (Keyword)cConstantAssignment_2.eContents().get(0);
		private final Assignment cVarDeclarationsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cVarDeclarationsSTVarDeclarationParserRuleCall_3_0 = (RuleCall)cVarDeclarationsAssignment_3.eContents().get(0);
		private final Keyword cEND_VARKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STVarOutputDeclarationBlock returns STVarOutputDeclarationBlock:
		//    {STVarOutputDeclarationBlock} 'VAR_OUTPUT' (constant?='CONSTANT')?
		//    varDeclarations+=STVarDeclaration*
		//    'END_VAR';
		@Override public ParserRule getRule() { return rule; }
		
		//{STVarOutputDeclarationBlock} 'VAR_OUTPUT' (constant?='CONSTANT')?
		//varDeclarations+=STVarDeclaration*
		//'END_VAR'
		public Group getGroup() { return cGroup; }
		
		//{STVarOutputDeclarationBlock}
		public Action getSTVarOutputDeclarationBlockAction_0() { return cSTVarOutputDeclarationBlockAction_0; }
		
		//'VAR_OUTPUT'
		public Keyword getVAR_OUTPUTKeyword_1() { return cVAR_OUTPUTKeyword_1; }
		
		//(constant?='CONSTANT')?
		public Assignment getConstantAssignment_2() { return cConstantAssignment_2; }
		
		//'CONSTANT'
		public Keyword getConstantCONSTANTKeyword_2_0() { return cConstantCONSTANTKeyword_2_0; }
		
		//varDeclarations+=STVarDeclaration*
		public Assignment getVarDeclarationsAssignment_3() { return cVarDeclarationsAssignment_3; }
		
		//STVarDeclaration
		public RuleCall getVarDeclarationsSTVarDeclarationParserRuleCall_3_0() { return cVarDeclarationsSTVarDeclarationParserRuleCall_3_0; }
		
		//'END_VAR'
		public Keyword getEND_VARKeyword_4() { return cEND_VARKeyword_4; }
	}
	public class STVarInOutDeclarationBlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarInOutDeclarationBlock");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTVarInOutDeclarationBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cVAR_IN_OUTKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cConstantAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cConstantCONSTANTKeyword_2_0 = (Keyword)cConstantAssignment_2.eContents().get(0);
		private final Assignment cVarDeclarationsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cVarDeclarationsSTVarDeclarationParserRuleCall_3_0 = (RuleCall)cVarDeclarationsAssignment_3.eContents().get(0);
		private final Keyword cEND_VARKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STVarInOutDeclarationBlock returns STVarInOutDeclarationBlock:
		//    {STVarInOutDeclarationBlock} 'VAR_IN_OUT' (constant?='CONSTANT')?
		//    varDeclarations+=STVarDeclaration*
		//    'END_VAR';
		@Override public ParserRule getRule() { return rule; }
		
		//{STVarInOutDeclarationBlock} 'VAR_IN_OUT' (constant?='CONSTANT')?
		//varDeclarations+=STVarDeclaration*
		//'END_VAR'
		public Group getGroup() { return cGroup; }
		
		//{STVarInOutDeclarationBlock}
		public Action getSTVarInOutDeclarationBlockAction_0() { return cSTVarInOutDeclarationBlockAction_0; }
		
		//'VAR_IN_OUT'
		public Keyword getVAR_IN_OUTKeyword_1() { return cVAR_IN_OUTKeyword_1; }
		
		//(constant?='CONSTANT')?
		public Assignment getConstantAssignment_2() { return cConstantAssignment_2; }
		
		//'CONSTANT'
		public Keyword getConstantCONSTANTKeyword_2_0() { return cConstantCONSTANTKeyword_2_0; }
		
		//varDeclarations+=STVarDeclaration*
		public Assignment getVarDeclarationsAssignment_3() { return cVarDeclarationsAssignment_3; }
		
		//STVarDeclaration
		public RuleCall getVarDeclarationsSTVarDeclarationParserRuleCall_3_0() { return cVarDeclarationsSTVarDeclarationParserRuleCall_3_0; }
		
		//'END_VAR'
		public Keyword getEND_VARKeyword_4() { return cEND_VARKeyword_4; }
	}
	public class STVarDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STVarDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTVarDeclarationAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cATKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cLocatedAtAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final CrossReference cLocatedAtINamedElementCrossReference_2_1_0 = (CrossReference)cLocatedAtAssignment_2_1.eContents().get(0);
		private final RuleCall cLocatedAtINamedElementIDTerminalRuleCall_2_1_0_1 = (RuleCall)cLocatedAtINamedElementCrossReference_2_1_0.eContents().get(1);
		private final Keyword cColonKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Assignment cArrayAssignment_4_0 = (Assignment)cGroup_4.eContents().get(0);
		private final Keyword cArrayARRAYKeyword_4_0_0 = (Keyword)cArrayAssignment_4_0.eContents().get(0);
		private final Alternatives cAlternatives_4_1 = (Alternatives)cGroup_4.eContents().get(1);
		private final Group cGroup_4_1_0 = (Group)cAlternatives_4_1.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_4_1_0_0 = (Keyword)cGroup_4_1_0.eContents().get(0);
		private final Assignment cRangesAssignment_4_1_0_1 = (Assignment)cGroup_4_1_0.eContents().get(1);
		private final RuleCall cRangesSTExpressionParserRuleCall_4_1_0_1_0 = (RuleCall)cRangesAssignment_4_1_0_1.eContents().get(0);
		private final Group cGroup_4_1_0_2 = (Group)cGroup_4_1_0.eContents().get(2);
		private final Keyword cCommaKeyword_4_1_0_2_0 = (Keyword)cGroup_4_1_0_2.eContents().get(0);
		private final Assignment cRangesAssignment_4_1_0_2_1 = (Assignment)cGroup_4_1_0_2.eContents().get(1);
		private final RuleCall cRangesSTExpressionParserRuleCall_4_1_0_2_1_0 = (RuleCall)cRangesAssignment_4_1_0_2_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_4_1_0_3 = (Keyword)cGroup_4_1_0.eContents().get(3);
		private final Group cGroup_4_1_1 = (Group)cAlternatives_4_1.eContents().get(1);
		private final Keyword cLeftSquareBracketKeyword_4_1_1_0 = (Keyword)cGroup_4_1_1.eContents().get(0);
		private final Assignment cCountAssignment_4_1_1_1 = (Assignment)cGroup_4_1_1.eContents().get(1);
		private final Keyword cCountAsteriskKeyword_4_1_1_1_0 = (Keyword)cCountAssignment_4_1_1_1.eContents().get(0);
		private final Group cGroup_4_1_1_2 = (Group)cGroup_4_1_1.eContents().get(2);
		private final Keyword cCommaKeyword_4_1_1_2_0 = (Keyword)cGroup_4_1_1_2.eContents().get(0);
		private final Assignment cCountAssignment_4_1_1_2_1 = (Assignment)cGroup_4_1_1_2.eContents().get(1);
		private final Keyword cCountAsteriskKeyword_4_1_1_2_1_0 = (Keyword)cCountAssignment_4_1_1_2_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_4_1_1_3 = (Keyword)cGroup_4_1_1.eContents().get(3);
		private final Keyword cOFKeyword_4_2 = (Keyword)cGroup_4.eContents().get(2);
		private final Assignment cTypeAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final CrossReference cTypeINamedElementCrossReference_5_0 = (CrossReference)cTypeAssignment_5.eContents().get(0);
		private final RuleCall cTypeINamedElementSTAnyTypeParserRuleCall_5_0_1 = (RuleCall)cTypeINamedElementCrossReference_5_0.eContents().get(1);
		private final Group cGroup_6 = (Group)cGroup.eContents().get(6);
		private final Keyword cLeftSquareBracketKeyword_6_0 = (Keyword)cGroup_6.eContents().get(0);
		private final Assignment cMaxLengthAssignment_6_1 = (Assignment)cGroup_6.eContents().get(1);
		private final RuleCall cMaxLengthSTExpressionParserRuleCall_6_1_0 = (RuleCall)cMaxLengthAssignment_6_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_6_2 = (Keyword)cGroup_6.eContents().get(2);
		private final Group cGroup_7 = (Group)cGroup.eContents().get(7);
		private final Keyword cColonEqualsSignKeyword_7_0 = (Keyword)cGroup_7.eContents().get(0);
		private final Assignment cDefaultValueAssignment_7_1 = (Assignment)cGroup_7.eContents().get(1);
		private final RuleCall cDefaultValueSTInitializerExpressionParserRuleCall_7_1_0 = (RuleCall)cDefaultValueAssignment_7_1.eContents().get(0);
		private final Assignment cPragmaAssignment_8 = (Assignment)cGroup.eContents().get(8);
		private final RuleCall cPragmaSTPragmaParserRuleCall_8_0 = (RuleCall)cPragmaAssignment_8.eContents().get(0);
		private final Keyword cSemicolonKeyword_9 = (Keyword)cGroup.eContents().get(9);
		
		//STVarDeclaration:
		//    {STVarDeclaration}
		//    name=ID ('AT' locatedAt=[libraryElement::INamedElement])? ':' (array?='ARRAY' (('[' ranges+=(STExpression) (','
		//    ranges+=STExpression)* ']') | ('[' count+='*' (',' count+='*')* ']')) 'OF')?
		//    (type=[libraryElement::INamedElement|STAnyType]) ('[' maxLength=STExpression ']')? (':='
		//    defaultValue=STInitializerExpression)? pragma=STPragma? ';';
		@Override public ParserRule getRule() { return rule; }
		
		//{STVarDeclaration}
		//name=ID ('AT' locatedAt=[libraryElement::INamedElement])? ':' (array?='ARRAY' (('[' ranges+=(STExpression) (','
		//ranges+=STExpression)* ']') | ('[' count+='*' (',' count+='*')* ']')) 'OF')?
		//(type=[libraryElement::INamedElement|STAnyType]) ('[' maxLength=STExpression ']')? (':='
		//defaultValue=STInitializerExpression)? pragma=STPragma? ';'
		public Group getGroup() { return cGroup; }
		
		//{STVarDeclaration}
		public Action getSTVarDeclarationAction_0() { return cSTVarDeclarationAction_0; }
		
		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//('AT' locatedAt=[libraryElement::INamedElement])?
		public Group getGroup_2() { return cGroup_2; }
		
		//'AT'
		public Keyword getATKeyword_2_0() { return cATKeyword_2_0; }
		
		//locatedAt=[libraryElement::INamedElement]
		public Assignment getLocatedAtAssignment_2_1() { return cLocatedAtAssignment_2_1; }
		
		//[libraryElement::INamedElement]
		public CrossReference getLocatedAtINamedElementCrossReference_2_1_0() { return cLocatedAtINamedElementCrossReference_2_1_0; }
		
		//ID
		public RuleCall getLocatedAtINamedElementIDTerminalRuleCall_2_1_0_1() { return cLocatedAtINamedElementIDTerminalRuleCall_2_1_0_1; }
		
		//':'
		public Keyword getColonKeyword_3() { return cColonKeyword_3; }
		
		//(array?='ARRAY' (('[' ranges+=(STExpression) (','
		//   ranges+=STExpression)* ']') | ('[' count+='*' (',' count+='*')* ']')) 'OF')?
		public Group getGroup_4() { return cGroup_4; }
		
		//array?='ARRAY'
		public Assignment getArrayAssignment_4_0() { return cArrayAssignment_4_0; }
		
		//'ARRAY'
		public Keyword getArrayARRAYKeyword_4_0_0() { return cArrayARRAYKeyword_4_0_0; }
		
		//(('[' ranges+=(STExpression) (','
		//   ranges+=STExpression)* ']') | ('[' count+='*' (',' count+='*')* ']'))
		public Alternatives getAlternatives_4_1() { return cAlternatives_4_1; }
		
		//('[' ranges+=(STExpression) (','
		//    ranges+=STExpression)* ']')
		public Group getGroup_4_1_0() { return cGroup_4_1_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_4_1_0_0() { return cLeftSquareBracketKeyword_4_1_0_0; }
		
		//ranges+=(STExpression)
		public Assignment getRangesAssignment_4_1_0_1() { return cRangesAssignment_4_1_0_1; }
		
		//(STExpression)
		public RuleCall getRangesSTExpressionParserRuleCall_4_1_0_1_0() { return cRangesSTExpressionParserRuleCall_4_1_0_1_0; }
		
		//(','
		//   ranges+=STExpression)*
		public Group getGroup_4_1_0_2() { return cGroup_4_1_0_2; }
		
		//','
		public Keyword getCommaKeyword_4_1_0_2_0() { return cCommaKeyword_4_1_0_2_0; }
		
		//ranges+=STExpression
		public Assignment getRangesAssignment_4_1_0_2_1() { return cRangesAssignment_4_1_0_2_1; }
		
		//STExpression
		public RuleCall getRangesSTExpressionParserRuleCall_4_1_0_2_1_0() { return cRangesSTExpressionParserRuleCall_4_1_0_2_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_4_1_0_3() { return cRightSquareBracketKeyword_4_1_0_3; }
		
		//('[' count+='*' (',' count+='*')* ']')
		public Group getGroup_4_1_1() { return cGroup_4_1_1; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_4_1_1_0() { return cLeftSquareBracketKeyword_4_1_1_0; }
		
		//count+='*'
		public Assignment getCountAssignment_4_1_1_1() { return cCountAssignment_4_1_1_1; }
		
		//'*'
		public Keyword getCountAsteriskKeyword_4_1_1_1_0() { return cCountAsteriskKeyword_4_1_1_1_0; }
		
		//(',' count+='*')*
		public Group getGroup_4_1_1_2() { return cGroup_4_1_1_2; }
		
		//','
		public Keyword getCommaKeyword_4_1_1_2_0() { return cCommaKeyword_4_1_1_2_0; }
		
		//count+='*'
		public Assignment getCountAssignment_4_1_1_2_1() { return cCountAssignment_4_1_1_2_1; }
		
		//'*'
		public Keyword getCountAsteriskKeyword_4_1_1_2_1_0() { return cCountAsteriskKeyword_4_1_1_2_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_4_1_1_3() { return cRightSquareBracketKeyword_4_1_1_3; }
		
		//'OF'
		public Keyword getOFKeyword_4_2() { return cOFKeyword_4_2; }
		
		//(type=[libraryElement::INamedElement|STAnyType])
		public Assignment getTypeAssignment_5() { return cTypeAssignment_5; }
		
		//[libraryElement::INamedElement|STAnyType]
		public CrossReference getTypeINamedElementCrossReference_5_0() { return cTypeINamedElementCrossReference_5_0; }
		
		//STAnyType
		public RuleCall getTypeINamedElementSTAnyTypeParserRuleCall_5_0_1() { return cTypeINamedElementSTAnyTypeParserRuleCall_5_0_1; }
		
		//('[' maxLength=STExpression ']')?
		public Group getGroup_6() { return cGroup_6; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_6_0() { return cLeftSquareBracketKeyword_6_0; }
		
		//maxLength=STExpression
		public Assignment getMaxLengthAssignment_6_1() { return cMaxLengthAssignment_6_1; }
		
		//STExpression
		public RuleCall getMaxLengthSTExpressionParserRuleCall_6_1_0() { return cMaxLengthSTExpressionParserRuleCall_6_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_6_2() { return cRightSquareBracketKeyword_6_2; }
		
		//(':='
		//   defaultValue=STInitializerExpression)?
		public Group getGroup_7() { return cGroup_7; }
		
		//':='
		public Keyword getColonEqualsSignKeyword_7_0() { return cColonEqualsSignKeyword_7_0; }
		
		//defaultValue=STInitializerExpression
		public Assignment getDefaultValueAssignment_7_1() { return cDefaultValueAssignment_7_1; }
		
		//STInitializerExpression
		public RuleCall getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0() { return cDefaultValueSTInitializerExpressionParserRuleCall_7_1_0; }
		
		//pragma=STPragma?
		public Assignment getPragmaAssignment_8() { return cPragmaAssignment_8; }
		
		//STPragma
		public RuleCall getPragmaSTPragmaParserRuleCall_8_0() { return cPragmaSTPragmaParserRuleCall_8_0; }
		
		//';'
		public Keyword getSemicolonKeyword_9() { return cSemicolonKeyword_9; }
	}
	public class STTypeDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STTypeDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTTypeDeclarationAction_0 = (Action)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cArrayAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final Keyword cArrayARRAYKeyword_1_0_0 = (Keyword)cArrayAssignment_1_0.eContents().get(0);
		private final Alternatives cAlternatives_1_1 = (Alternatives)cGroup_1.eContents().get(1);
		private final Group cGroup_1_1_0 = (Group)cAlternatives_1_1.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_1_1_0_0 = (Keyword)cGroup_1_1_0.eContents().get(0);
		private final Assignment cRangesAssignment_1_1_0_1 = (Assignment)cGroup_1_1_0.eContents().get(1);
		private final RuleCall cRangesSTExpressionParserRuleCall_1_1_0_1_0 = (RuleCall)cRangesAssignment_1_1_0_1.eContents().get(0);
		private final Group cGroup_1_1_0_2 = (Group)cGroup_1_1_0.eContents().get(2);
		private final Keyword cCommaKeyword_1_1_0_2_0 = (Keyword)cGroup_1_1_0_2.eContents().get(0);
		private final Assignment cRangesAssignment_1_1_0_2_1 = (Assignment)cGroup_1_1_0_2.eContents().get(1);
		private final RuleCall cRangesSTExpressionParserRuleCall_1_1_0_2_1_0 = (RuleCall)cRangesAssignment_1_1_0_2_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1_1_0_3 = (Keyword)cGroup_1_1_0.eContents().get(3);
		private final Group cGroup_1_1_1 = (Group)cAlternatives_1_1.eContents().get(1);
		private final Keyword cLeftSquareBracketKeyword_1_1_1_0 = (Keyword)cGroup_1_1_1.eContents().get(0);
		private final Assignment cCountAssignment_1_1_1_1 = (Assignment)cGroup_1_1_1.eContents().get(1);
		private final Keyword cCountAsteriskKeyword_1_1_1_1_0 = (Keyword)cCountAssignment_1_1_1_1.eContents().get(0);
		private final Group cGroup_1_1_1_2 = (Group)cGroup_1_1_1.eContents().get(2);
		private final Keyword cCommaKeyword_1_1_1_2_0 = (Keyword)cGroup_1_1_1_2.eContents().get(0);
		private final Assignment cCountAssignment_1_1_1_2_1 = (Assignment)cGroup_1_1_1_2.eContents().get(1);
		private final Keyword cCountAsteriskKeyword_1_1_1_2_1_0 = (Keyword)cCountAssignment_1_1_1_2_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1_1_1_3 = (Keyword)cGroup_1_1_1.eContents().get(3);
		private final Keyword cOFKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		private final Assignment cTypeAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final CrossReference cTypeINamedElementCrossReference_2_0 = (CrossReference)cTypeAssignment_2.eContents().get(0);
		private final RuleCall cTypeINamedElementSTAnyTypeParserRuleCall_2_0_1 = (RuleCall)cTypeINamedElementCrossReference_2_0.eContents().get(1);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cLeftSquareBracketKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cMaxLengthAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cMaxLengthSTExpressionParserRuleCall_3_1_0 = (RuleCall)cMaxLengthAssignment_3_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_3_2 = (Keyword)cGroup_3.eContents().get(2);
		
		//STTypeDeclaration:
		//    {STTypeDeclaration}
		//    (array?='ARRAY' (('[' ranges+=(STExpression) (',' ranges+=STExpression)* ']') |
		//    ('[' count+='*' (',' count+='*')* ']')) 'OF')?
		//    (type=[libraryElement::INamedElement|STAnyType])
		//    ('[' maxLength=STExpression ']')?;
		@Override public ParserRule getRule() { return rule; }
		
		//{STTypeDeclaration}
		//(array?='ARRAY' (('[' ranges+=(STExpression) (',' ranges+=STExpression)* ']') |
		//('[' count+='*' (',' count+='*')* ']')) 'OF')?
		//(type=[libraryElement::INamedElement|STAnyType])
		//('[' maxLength=STExpression ']')?
		public Group getGroup() { return cGroup; }
		
		//{STTypeDeclaration}
		public Action getSTTypeDeclarationAction_0() { return cSTTypeDeclarationAction_0; }
		
		//(array?='ARRAY' (('[' ranges+=(STExpression) (',' ranges+=STExpression)* ']') |
		//('[' count+='*' (',' count+='*')* ']')) 'OF')?
		public Group getGroup_1() { return cGroup_1; }
		
		//array?='ARRAY'
		public Assignment getArrayAssignment_1_0() { return cArrayAssignment_1_0; }
		
		//'ARRAY'
		public Keyword getArrayARRAYKeyword_1_0_0() { return cArrayARRAYKeyword_1_0_0; }
		
		//(('[' ranges+=(STExpression) (',' ranges+=STExpression)* ']') |
		//   ('[' count+='*' (',' count+='*')* ']'))
		public Alternatives getAlternatives_1_1() { return cAlternatives_1_1; }
		
		//('[' ranges+=(STExpression) (',' ranges+=STExpression)* ']')
		public Group getGroup_1_1_0() { return cGroup_1_1_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_1_1_0_0() { return cLeftSquareBracketKeyword_1_1_0_0; }
		
		//ranges+=(STExpression)
		public Assignment getRangesAssignment_1_1_0_1() { return cRangesAssignment_1_1_0_1; }
		
		//(STExpression)
		public RuleCall getRangesSTExpressionParserRuleCall_1_1_0_1_0() { return cRangesSTExpressionParserRuleCall_1_1_0_1_0; }
		
		//(',' ranges+=STExpression)*
		public Group getGroup_1_1_0_2() { return cGroup_1_1_0_2; }
		
		//','
		public Keyword getCommaKeyword_1_1_0_2_0() { return cCommaKeyword_1_1_0_2_0; }
		
		//ranges+=STExpression
		public Assignment getRangesAssignment_1_1_0_2_1() { return cRangesAssignment_1_1_0_2_1; }
		
		//STExpression
		public RuleCall getRangesSTExpressionParserRuleCall_1_1_0_2_1_0() { return cRangesSTExpressionParserRuleCall_1_1_0_2_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1_1_0_3() { return cRightSquareBracketKeyword_1_1_0_3; }
		
		//('[' count+='*' (',' count+='*')* ']')
		public Group getGroup_1_1_1() { return cGroup_1_1_1; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_1_1_1_0() { return cLeftSquareBracketKeyword_1_1_1_0; }
		
		//count+='*'
		public Assignment getCountAssignment_1_1_1_1() { return cCountAssignment_1_1_1_1; }
		
		//'*'
		public Keyword getCountAsteriskKeyword_1_1_1_1_0() { return cCountAsteriskKeyword_1_1_1_1_0; }
		
		//(',' count+='*')*
		public Group getGroup_1_1_1_2() { return cGroup_1_1_1_2; }
		
		//','
		public Keyword getCommaKeyword_1_1_1_2_0() { return cCommaKeyword_1_1_1_2_0; }
		
		//count+='*'
		public Assignment getCountAssignment_1_1_1_2_1() { return cCountAssignment_1_1_1_2_1; }
		
		//'*'
		public Keyword getCountAsteriskKeyword_1_1_1_2_1_0() { return cCountAsteriskKeyword_1_1_1_2_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1_1_1_3() { return cRightSquareBracketKeyword_1_1_1_3; }
		
		//'OF'
		public Keyword getOFKeyword_1_2() { return cOFKeyword_1_2; }
		
		//(type=[libraryElement::INamedElement|STAnyType])
		public Assignment getTypeAssignment_2() { return cTypeAssignment_2; }
		
		//[libraryElement::INamedElement|STAnyType]
		public CrossReference getTypeINamedElementCrossReference_2_0() { return cTypeINamedElementCrossReference_2_0; }
		
		//STAnyType
		public RuleCall getTypeINamedElementSTAnyTypeParserRuleCall_2_0_1() { return cTypeINamedElementSTAnyTypeParserRuleCall_2_0_1; }
		
		//('[' maxLength=STExpression ']')?
		public Group getGroup_3() { return cGroup_3; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_3_0() { return cLeftSquareBracketKeyword_3_0; }
		
		//maxLength=STExpression
		public Assignment getMaxLengthAssignment_3_1() { return cMaxLengthAssignment_3_1; }
		
		//STExpression
		public RuleCall getMaxLengthSTExpressionParserRuleCall_3_1_0() { return cMaxLengthSTExpressionParserRuleCall_3_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_3_2() { return cRightSquareBracketKeyword_3_2; }
	}
	public class STTypeDeclaration0Elements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STTypeDeclaration0");
		private final RuleCall cSTTypeDeclarationParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//STTypeDeclaration0 returns STTypeDeclaration:
		//    STTypeDeclaration;
		@Override public ParserRule getRule() { return rule; }
		
		//STTypeDeclaration
		public RuleCall getSTTypeDeclarationParserRuleCall() { return cSTTypeDeclarationParserRuleCall; }
	}
	public class STInitializerExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STInitializerExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTElementaryInitializerExpressionParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTArrayInitializerExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cSTStructInitializerExpressionParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		// // necessary to keep Xtext from skipping this rule
		//STInitializerExpression:
		//    STElementaryInitializerExpression | STArrayInitializerExpression | STStructInitializerExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//STElementaryInitializerExpression | STArrayInitializerExpression | STStructInitializerExpression
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STElementaryInitializerExpression
		public RuleCall getSTElementaryInitializerExpressionParserRuleCall_0() { return cSTElementaryInitializerExpressionParserRuleCall_0; }
		
		//STArrayInitializerExpression
		public RuleCall getSTArrayInitializerExpressionParserRuleCall_1() { return cSTArrayInitializerExpressionParserRuleCall_1; }
		
		//STStructInitializerExpression
		public RuleCall getSTStructInitializerExpressionParserRuleCall_2() { return cSTStructInitializerExpressionParserRuleCall_2; }
	}
	public class STElementaryInitializerExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STElementaryInitializerExpression");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueSTExpressionParserRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//STElementaryInitializerExpression:
		//    value=STExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//value=STExpression
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//STExpression
		public RuleCall getValueSTExpressionParserRuleCall_0() { return cValueSTExpressionParserRuleCall_0; }
	}
	public class STArrayInitializerExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STArrayInitializerExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftSquareBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cValuesAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cValuesSTArrayInitElementParserRuleCall_1_0 = (RuleCall)cValuesAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommaKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cValuesAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cValuesSTArrayInitElementParserRuleCall_2_1_0 = (RuleCall)cValuesAssignment_2_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//STArrayInitializerExpression:
		//    '[' values+=STArrayInitElement (',' values+=STArrayInitElement)* ']';
		@Override public ParserRule getRule() { return rule; }
		
		//'[' values+=STArrayInitElement (',' values+=STArrayInitElement)* ']'
		public Group getGroup() { return cGroup; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_0() { return cLeftSquareBracketKeyword_0; }
		
		//values+=STArrayInitElement
		public Assignment getValuesAssignment_1() { return cValuesAssignment_1; }
		
		//STArrayInitElement
		public RuleCall getValuesSTArrayInitElementParserRuleCall_1_0() { return cValuesSTArrayInitElementParserRuleCall_1_0; }
		
		//(',' values+=STArrayInitElement)*
		public Group getGroup_2() { return cGroup_2; }
		
		//','
		public Keyword getCommaKeyword_2_0() { return cCommaKeyword_2_0; }
		
		//values+=STArrayInitElement
		public Assignment getValuesAssignment_2_1() { return cValuesAssignment_2_1; }
		
		//STArrayInitElement
		public RuleCall getValuesSTArrayInitElementParserRuleCall_2_1_0() { return cValuesSTArrayInitElementParserRuleCall_2_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_3() { return cRightSquareBracketKeyword_3; }
	}
	public class STArrayInitElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STArrayInitElement");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTSingleArrayInitElementParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTRepeatArrayInitElementParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//STArrayInitElement:
		//    STSingleArrayInitElement | STRepeatArrayInitElement;
		@Override public ParserRule getRule() { return rule; }
		
		//STSingleArrayInitElement | STRepeatArrayInitElement
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STSingleArrayInitElement
		public RuleCall getSTSingleArrayInitElementParserRuleCall_0() { return cSTSingleArrayInitElementParserRuleCall_0; }
		
		//STRepeatArrayInitElement
		public RuleCall getSTRepeatArrayInitElementParserRuleCall_1() { return cSTRepeatArrayInitElementParserRuleCall_1; }
	}
	public class STSingleArrayInitElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STSingleArrayInitElement");
		private final Assignment cInitExpressionAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cInitExpressionSTInitializerExpressionParserRuleCall_0 = (RuleCall)cInitExpressionAssignment.eContents().get(0);
		
		//STSingleArrayInitElement:
		//    initExpression=STInitializerExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//initExpression=STInitializerExpression
		public Assignment getInitExpressionAssignment() { return cInitExpressionAssignment; }
		
		//STInitializerExpression
		public RuleCall getInitExpressionSTInitializerExpressionParserRuleCall_0() { return cInitExpressionSTInitializerExpressionParserRuleCall_0; }
	}
	public class STRepeatArrayInitElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STRepeatArrayInitElement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cRepetitionsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cRepetitionsINTTerminalRuleCall_0_0 = (RuleCall)cRepetitionsAssignment_0.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cInitExpressionsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cInitExpressionsSTInitializerExpressionParserRuleCall_2_0 = (RuleCall)cInitExpressionsAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cCommaKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cInitExpressionsAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cInitExpressionsSTInitializerExpressionParserRuleCall_3_1_0 = (RuleCall)cInitExpressionsAssignment_3_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STRepeatArrayInitElement:
		//    repetitions=INT '(' initExpressions+=STInitializerExpression (','
		//    initExpressions+=STInitializerExpression)* ')';
		@Override public ParserRule getRule() { return rule; }
		
		//repetitions=INT '(' initExpressions+=STInitializerExpression (','
		//initExpressions+=STInitializerExpression)* ')'
		public Group getGroup() { return cGroup; }
		
		//repetitions=INT
		public Assignment getRepetitionsAssignment_0() { return cRepetitionsAssignment_0; }
		
		//INT
		public RuleCall getRepetitionsINTTerminalRuleCall_0_0() { return cRepetitionsINTTerminalRuleCall_0_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//initExpressions+=STInitializerExpression
		public Assignment getInitExpressionsAssignment_2() { return cInitExpressionsAssignment_2; }
		
		//STInitializerExpression
		public RuleCall getInitExpressionsSTInitializerExpressionParserRuleCall_2_0() { return cInitExpressionsSTInitializerExpressionParserRuleCall_2_0; }
		
		//(','
		//   initExpressions+=STInitializerExpression)*
		public Group getGroup_3() { return cGroup_3; }
		
		//','
		public Keyword getCommaKeyword_3_0() { return cCommaKeyword_3_0; }
		
		//initExpressions+=STInitializerExpression
		public Assignment getInitExpressionsAssignment_3_1() { return cInitExpressionsAssignment_3_1; }
		
		//STInitializerExpression
		public RuleCall getInitExpressionsSTInitializerExpressionParserRuleCall_3_1_0() { return cInitExpressionsSTInitializerExpressionParserRuleCall_3_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
	}
	public class STStructInitializerExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STStructInitializerExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Assignment cTypeAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final CrossReference cTypeStructuredTypeCrossReference_0_0_0 = (CrossReference)cTypeAssignment_0_0.eContents().get(0);
		private final RuleCall cTypeStructuredTypeQualifiedNameParserRuleCall_0_0_0_1 = (RuleCall)cTypeStructuredTypeCrossReference_0_0_0.eContents().get(1);
		private final Keyword cNumberSignKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cValuesAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cValuesSTStructInitElementParserRuleCall_2_0 = (RuleCall)cValuesAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cCommaKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cValuesAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cValuesSTStructInitElementParserRuleCall_3_1_0 = (RuleCall)cValuesAssignment_3_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STStructInitializerExpression:
		//    (type=[datatype::StructuredType|QualifiedName] '#')? '(' values+=STStructInitElement (','
		//    values+=STStructInitElement)* ')';
		@Override public ParserRule getRule() { return rule; }
		
		//(type=[datatype::StructuredType|QualifiedName] '#')? '(' values+=STStructInitElement (','
		//values+=STStructInitElement)* ')'
		public Group getGroup() { return cGroup; }
		
		//(type=[datatype::StructuredType|QualifiedName] '#')?
		public Group getGroup_0() { return cGroup_0; }
		
		//type=[datatype::StructuredType|QualifiedName]
		public Assignment getTypeAssignment_0_0() { return cTypeAssignment_0_0; }
		
		//[datatype::StructuredType|QualifiedName]
		public CrossReference getTypeStructuredTypeCrossReference_0_0_0() { return cTypeStructuredTypeCrossReference_0_0_0; }
		
		//QualifiedName
		public RuleCall getTypeStructuredTypeQualifiedNameParserRuleCall_0_0_0_1() { return cTypeStructuredTypeQualifiedNameParserRuleCall_0_0_0_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_0_1() { return cNumberSignKeyword_0_1; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//values+=STStructInitElement
		public Assignment getValuesAssignment_2() { return cValuesAssignment_2; }
		
		//STStructInitElement
		public RuleCall getValuesSTStructInitElementParserRuleCall_2_0() { return cValuesSTStructInitElementParserRuleCall_2_0; }
		
		//(','
		//   values+=STStructInitElement)*
		public Group getGroup_3() { return cGroup_3; }
		
		//','
		public Keyword getCommaKeyword_3_0() { return cCommaKeyword_3_0; }
		
		//values+=STStructInitElement
		public Assignment getValuesAssignment_3_1() { return cValuesAssignment_3_1; }
		
		//STStructInitElement
		public RuleCall getValuesSTStructInitElementParserRuleCall_3_1_0() { return cValuesSTStructInitElementParserRuleCall_3_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
	}
	public class STStructInitElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STStructInitElement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cVariableAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cVariableINamedElementCrossReference_0_0 = (CrossReference)cVariableAssignment_0.eContents().get(0);
		private final RuleCall cVariableINamedElementSTFeatureNameParserRuleCall_0_0_1 = (RuleCall)cVariableINamedElementCrossReference_0_0.eContents().get(1);
		private final Keyword cColonEqualsSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cValueAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cValueSTInitializerExpressionParserRuleCall_2_0 = (RuleCall)cValueAssignment_2.eContents().get(0);
		
		//STStructInitElement:
		//    variable=[libraryElement::INamedElement|STFeatureName] ':=' value=STInitializerExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//variable=[libraryElement::INamedElement|STFeatureName] ':=' value=STInitializerExpression
		public Group getGroup() { return cGroup; }
		
		//variable=[libraryElement::INamedElement|STFeatureName]
		public Assignment getVariableAssignment_0() { return cVariableAssignment_0; }
		
		//[libraryElement::INamedElement|STFeatureName]
		public CrossReference getVariableINamedElementCrossReference_0_0() { return cVariableINamedElementCrossReference_0_0; }
		
		//STFeatureName
		public RuleCall getVariableINamedElementSTFeatureNameParserRuleCall_0_0_1() { return cVariableINamedElementSTFeatureNameParserRuleCall_0_0_1; }
		
		//':='
		public Keyword getColonEqualsSignKeyword_1() { return cColonEqualsSignKeyword_1; }
		
		//value=STInitializerExpression
		public Assignment getValueAssignment_2() { return cValueAssignment_2; }
		
		//STInitializerExpression
		public RuleCall getValueSTInitializerExpressionParserRuleCall_2_0() { return cValueSTInitializerExpressionParserRuleCall_2_0; }
	}
	public class STPragmaElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STPragma");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTPragmaAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cAttributesAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cAttributesSTAttributeParserRuleCall_2_0 = (RuleCall)cAttributesAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cCommaKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cAttributesAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cAttributesSTAttributeParserRuleCall_3_1_0 = (RuleCall)cAttributesAssignment_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STPragma:
		//    {STPragma}
		//    '{' attributes+=STAttribute (',' attributes+=STAttribute)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{STPragma}
		//'{' attributes+=STAttribute (',' attributes+=STAttribute)* '}'
		public Group getGroup() { return cGroup; }
		
		//{STPragma}
		public Action getSTPragmaAction_0() { return cSTPragmaAction_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//attributes+=STAttribute
		public Assignment getAttributesAssignment_2() { return cAttributesAssignment_2; }
		
		//STAttribute
		public RuleCall getAttributesSTAttributeParserRuleCall_2_0() { return cAttributesSTAttributeParserRuleCall_2_0; }
		
		//(',' attributes+=STAttribute)*
		public Group getGroup_3() { return cGroup_3; }
		
		//','
		public Keyword getCommaKeyword_3_0() { return cCommaKeyword_3_0; }
		
		//attributes+=STAttribute
		public Assignment getAttributesAssignment_3_1() { return cAttributesAssignment_3_1; }
		
		//STAttribute
		public RuleCall getAttributesSTAttributeParserRuleCall_3_1_0() { return cAttributesSTAttributeParserRuleCall_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class STAttributeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAttribute");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDeclarationAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cDeclarationAttributeDeclarationCrossReference_0_0 = (CrossReference)cDeclarationAssignment_0.eContents().get(0);
		private final RuleCall cDeclarationAttributeDeclarationSTAttributeNameParserRuleCall_0_0_1 = (RuleCall)cDeclarationAttributeDeclarationCrossReference_0_0.eContents().get(1);
		private final Keyword cColonEqualsSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cValueAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cValueSTInitializerExpressionParserRuleCall_2_0 = (RuleCall)cValueAssignment_2.eContents().get(0);
		
		//STAttribute:
		//    declaration=[libraryElement::AttributeDeclaration|STAttributeName] ':=' value=STInitializerExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//declaration=[libraryElement::AttributeDeclaration|STAttributeName] ':=' value=STInitializerExpression
		public Group getGroup() { return cGroup; }
		
		//declaration=[libraryElement::AttributeDeclaration|STAttributeName]
		public Assignment getDeclarationAssignment_0() { return cDeclarationAssignment_0; }
		
		//[libraryElement::AttributeDeclaration|STAttributeName]
		public CrossReference getDeclarationAttributeDeclarationCrossReference_0_0() { return cDeclarationAttributeDeclarationCrossReference_0_0; }
		
		//STAttributeName
		public RuleCall getDeclarationAttributeDeclarationSTAttributeNameParserRuleCall_0_0_1() { return cDeclarationAttributeDeclarationSTAttributeNameParserRuleCall_0_0_1; }
		
		//':='
		public Keyword getColonEqualsSignKeyword_1() { return cColonEqualsSignKeyword_1; }
		
		//value=STInitializerExpression
		public Assignment getValueAssignment_2() { return cValueAssignment_2; }
		
		//STInitializerExpression
		public RuleCall getValueSTInitializerExpressionParserRuleCall_2_0() { return cValueSTInitializerExpressionParserRuleCall_2_0; }
	}
	public class STAttributeNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAttributeName");
		private final RuleCall cQualifiedNameParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//STAttributeName:
		//    QualifiedName;
		@Override public ParserRule getRule() { return rule; }
		
		//QualifiedName
		public RuleCall getQualifiedNameParserRuleCall() { return cQualifiedNameParserRuleCall; }
	}
	public class STStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STStatement");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Alternatives cAlternatives_0_0 = (Alternatives)cGroup_0.eContents().get(0);
		private final RuleCall cSTIfStatementParserRuleCall_0_0_0 = (RuleCall)cAlternatives_0_0.eContents().get(0);
		private final RuleCall cSTCaseStatementParserRuleCall_0_0_1 = (RuleCall)cAlternatives_0_0.eContents().get(1);
		private final RuleCall cSTForStatementParserRuleCall_0_0_2 = (RuleCall)cAlternatives_0_0.eContents().get(2);
		private final RuleCall cSTWhileStatementParserRuleCall_0_0_3 = (RuleCall)cAlternatives_0_0.eContents().get(3);
		private final RuleCall cSTRepeatStatementParserRuleCall_0_0_4 = (RuleCall)cAlternatives_0_0.eContents().get(4);
		private final RuleCall cSTAssignmentParserRuleCall_0_0_5 = (RuleCall)cAlternatives_0_0.eContents().get(5);
		private final Group cGroup_0_0_6 = (Group)cAlternatives_0_0.eContents().get(6);
		private final Action cSTReturnAction_0_0_6_0 = (Action)cGroup_0_0_6.eContents().get(0);
		private final Keyword cRETURNKeyword_0_0_6_1 = (Keyword)cGroup_0_0_6.eContents().get(1);
		private final Group cGroup_0_0_7 = (Group)cAlternatives_0_0.eContents().get(7);
		private final Action cSTContinueAction_0_0_7_0 = (Action)cGroup_0_0_7.eContents().get(0);
		private final Keyword cCONTINUEKeyword_0_0_7_1 = (Keyword)cGroup_0_0_7.eContents().get(1);
		private final Group cGroup_0_0_8 = (Group)cAlternatives_0_0.eContents().get(8);
		private final Action cSTExitAction_0_0_8_0 = (Action)cGroup_0_0_8.eContents().get(0);
		private final Keyword cEXITKeyword_0_0_8_1 = (Keyword)cGroup_0_0_8.eContents().get(1);
		private final Keyword cSemicolonKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Action cSTNopAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_1_1 = (Keyword)cGroup_1.eContents().get(1);
		
		//STStatement:
		//    (STIfStatement |
		//    STCaseStatement |
		//    STForStatement |
		//    STWhileStatement |
		//    STRepeatStatement |
		//    STAssignment |
		//    {STReturn} 'RETURN' |
		//    {STContinue} 'CONTINUE' |
		//    {STExit} 'EXIT') ';' |
		//    {STNop} ';';
		@Override public ParserRule getRule() { return rule; }
		
		//(STIfStatement |
		//STCaseStatement |
		//STForStatement |
		//STWhileStatement |
		//STRepeatStatement |
		//STAssignment |
		//{STReturn} 'RETURN' |
		//{STContinue} 'CONTINUE' |
		//{STExit} 'EXIT') ';' |
		//{STNop} ';'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//(STIfStatement |
		//STCaseStatement |
		//STForStatement |
		//STWhileStatement |
		//STRepeatStatement |
		//STAssignment |
		//{STReturn} 'RETURN' |
		//{STContinue} 'CONTINUE' |
		//{STExit} 'EXIT') ';'
		public Group getGroup_0() { return cGroup_0; }
		
		//(STIfStatement |
		//STCaseStatement |
		//STForStatement |
		//STWhileStatement |
		//STRepeatStatement |
		//STAssignment |
		//{STReturn} 'RETURN' |
		//{STContinue} 'CONTINUE' |
		//{STExit} 'EXIT')
		public Alternatives getAlternatives_0_0() { return cAlternatives_0_0; }
		
		//STIfStatement
		public RuleCall getSTIfStatementParserRuleCall_0_0_0() { return cSTIfStatementParserRuleCall_0_0_0; }
		
		//STCaseStatement
		public RuleCall getSTCaseStatementParserRuleCall_0_0_1() { return cSTCaseStatementParserRuleCall_0_0_1; }
		
		//STForStatement
		public RuleCall getSTForStatementParserRuleCall_0_0_2() { return cSTForStatementParserRuleCall_0_0_2; }
		
		//STWhileStatement
		public RuleCall getSTWhileStatementParserRuleCall_0_0_3() { return cSTWhileStatementParserRuleCall_0_0_3; }
		
		//STRepeatStatement
		public RuleCall getSTRepeatStatementParserRuleCall_0_0_4() { return cSTRepeatStatementParserRuleCall_0_0_4; }
		
		//STAssignment
		public RuleCall getSTAssignmentParserRuleCall_0_0_5() { return cSTAssignmentParserRuleCall_0_0_5; }
		
		//{STReturn} 'RETURN'
		public Group getGroup_0_0_6() { return cGroup_0_0_6; }
		
		//{STReturn}
		public Action getSTReturnAction_0_0_6_0() { return cSTReturnAction_0_0_6_0; }
		
		//'RETURN'
		public Keyword getRETURNKeyword_0_0_6_1() { return cRETURNKeyword_0_0_6_1; }
		
		//{STContinue} 'CONTINUE'
		public Group getGroup_0_0_7() { return cGroup_0_0_7; }
		
		//{STContinue}
		public Action getSTContinueAction_0_0_7_0() { return cSTContinueAction_0_0_7_0; }
		
		//'CONTINUE'
		public Keyword getCONTINUEKeyword_0_0_7_1() { return cCONTINUEKeyword_0_0_7_1; }
		
		//{STExit} 'EXIT'
		public Group getGroup_0_0_8() { return cGroup_0_0_8; }
		
		//{STExit}
		public Action getSTExitAction_0_0_8_0() { return cSTExitAction_0_0_8_0; }
		
		//'EXIT'
		public Keyword getEXITKeyword_0_0_8_1() { return cEXITKeyword_0_0_8_1; }
		
		//';'
		public Keyword getSemicolonKeyword_0_1() { return cSemicolonKeyword_0_1; }
		
		//{STNop} ';'
		public Group getGroup_1() { return cGroup_1; }
		
		//{STNop}
		public Action getSTNopAction_1_0() { return cSTNopAction_1_0; }
		
		//';'
		public Keyword getSemicolonKeyword_1_1() { return cSemicolonKeyword_1_1; }
	}
	public class STAssignmentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAssignment");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cSTAssignmentLeftAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Keyword cColonEqualsSignKeyword_1_1 = (Keyword)cGroup_1.eContents().get(1);
		private final Assignment cRightAssignment_1_2 = (Assignment)cGroup_1.eContents().get(2);
		private final RuleCall cRightSTAssignmentParserRuleCall_1_2_0 = (RuleCall)cRightAssignment_1_2.eContents().get(0);
		
		//STAssignment returns STExpression:
		//    STExpression ({STAssignment.left=current} ':=' right=STAssignment)?;
		@Override public ParserRule getRule() { return rule; }
		
		//STExpression ({STAssignment.left=current} ':=' right=STAssignment)?
		public Group getGroup() { return cGroup; }
		
		//STExpression
		public RuleCall getSTExpressionParserRuleCall_0() { return cSTExpressionParserRuleCall_0; }
		
		//({STAssignment.left=current} ':=' right=STAssignment)?
		public Group getGroup_1() { return cGroup_1; }
		
		//{STAssignment.left=current}
		public Action getSTAssignmentLeftAction_1_0() { return cSTAssignmentLeftAction_1_0; }
		
		//':='
		public Keyword getColonEqualsSignKeyword_1_1() { return cColonEqualsSignKeyword_1_1; }
		
		//right=STAssignment
		public Assignment getRightAssignment_1_2() { return cRightAssignment_1_2; }
		
		//STAssignment
		public RuleCall getRightSTAssignmentParserRuleCall_1_2_0() { return cRightSTAssignmentParserRuleCall_1_2_0; }
	}
	public class STCallArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallArgument");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTCallUnnamedArgumentParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTCallNamedInputArgumentParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cSTCallNamedOutputArgumentParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//STCallArgument:
		//    STCallUnnamedArgument | STCallNamedInputArgument | STCallNamedOutputArgument;
		@Override public ParserRule getRule() { return rule; }
		
		//STCallUnnamedArgument | STCallNamedInputArgument | STCallNamedOutputArgument
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STCallUnnamedArgument
		public RuleCall getSTCallUnnamedArgumentParserRuleCall_0() { return cSTCallUnnamedArgumentParserRuleCall_0; }
		
		//STCallNamedInputArgument
		public RuleCall getSTCallNamedInputArgumentParserRuleCall_1() { return cSTCallNamedInputArgumentParserRuleCall_1; }
		
		//STCallNamedOutputArgument
		public RuleCall getSTCallNamedOutputArgumentParserRuleCall_2() { return cSTCallNamedOutputArgumentParserRuleCall_2; }
	}
	public class STCallUnnamedArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallUnnamedArgument");
		private final Assignment cArgumentAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cArgumentSTExpressionParserRuleCall_0 = (RuleCall)cArgumentAssignment.eContents().get(0);
		
		//STCallUnnamedArgument:
		//    argument=STExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//argument=STExpression
		public Assignment getArgumentAssignment() { return cArgumentAssignment; }
		
		//STExpression
		public RuleCall getArgumentSTExpressionParserRuleCall_0() { return cArgumentSTExpressionParserRuleCall_0; }
	}
	public class STCallNamedInputArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallNamedInputArgument");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cParameterAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cParameterINamedElementCrossReference_0_0 = (CrossReference)cParameterAssignment_0.eContents().get(0);
		private final RuleCall cParameterINamedElementIDTerminalRuleCall_0_0_1 = (RuleCall)cParameterINamedElementCrossReference_0_0.eContents().get(1);
		private final Keyword cColonEqualsSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cArgumentAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cArgumentSTExpressionParserRuleCall_2_0 = (RuleCall)cArgumentAssignment_2.eContents().get(0);
		
		//STCallNamedInputArgument:
		//    parameter=[libraryElement::INamedElement] ':=' argument=STExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//parameter=[libraryElement::INamedElement] ':=' argument=STExpression
		public Group getGroup() { return cGroup; }
		
		//parameter=[libraryElement::INamedElement]
		public Assignment getParameterAssignment_0() { return cParameterAssignment_0; }
		
		//[libraryElement::INamedElement]
		public CrossReference getParameterINamedElementCrossReference_0_0() { return cParameterINamedElementCrossReference_0_0; }
		
		//ID
		public RuleCall getParameterINamedElementIDTerminalRuleCall_0_0_1() { return cParameterINamedElementIDTerminalRuleCall_0_0_1; }
		
		//':='
		public Keyword getColonEqualsSignKeyword_1() { return cColonEqualsSignKeyword_1; }
		
		//argument=STExpression
		public Assignment getArgumentAssignment_2() { return cArgumentAssignment_2; }
		
		//STExpression
		public RuleCall getArgumentSTExpressionParserRuleCall_2_0() { return cArgumentSTExpressionParserRuleCall_2_0; }
	}
	public class STCallNamedOutputArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STCallNamedOutputArgument");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNotAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cNotNOTKeyword_0_0 = (Keyword)cNotAssignment_0.eContents().get(0);
		private final Assignment cParameterAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final CrossReference cParameterINamedElementCrossReference_1_0 = (CrossReference)cParameterAssignment_1.eContents().get(0);
		private final RuleCall cParameterINamedElementIDTerminalRuleCall_1_0_1 = (RuleCall)cParameterINamedElementCrossReference_1_0.eContents().get(1);
		private final Keyword cEqualsSignGreaterThanSignKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cArgumentAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cArgumentSTExpressionParserRuleCall_3_0 = (RuleCall)cArgumentAssignment_3.eContents().get(0);
		
		//STCallNamedOutputArgument:
		//    not?='NOT'? parameter=[libraryElement::INamedElement] '=>' argument=STExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//not?='NOT'? parameter=[libraryElement::INamedElement] '=>' argument=STExpression
		public Group getGroup() { return cGroup; }
		
		//not?='NOT'?
		public Assignment getNotAssignment_0() { return cNotAssignment_0; }
		
		//'NOT'
		public Keyword getNotNOTKeyword_0_0() { return cNotNOTKeyword_0_0; }
		
		//parameter=[libraryElement::INamedElement]
		public Assignment getParameterAssignment_1() { return cParameterAssignment_1; }
		
		//[libraryElement::INamedElement]
		public CrossReference getParameterINamedElementCrossReference_1_0() { return cParameterINamedElementCrossReference_1_0; }
		
		//ID
		public RuleCall getParameterINamedElementIDTerminalRuleCall_1_0_1() { return cParameterINamedElementIDTerminalRuleCall_1_0_1; }
		
		//'=>'
		public Keyword getEqualsSignGreaterThanSignKeyword_2() { return cEqualsSignGreaterThanSignKeyword_2; }
		
		//argument=STExpression
		public Assignment getArgumentAssignment_3() { return cArgumentAssignment_3; }
		
		//STExpression
		public RuleCall getArgumentSTExpressionParserRuleCall_3_0() { return cArgumentSTExpressionParserRuleCall_3_0; }
	}
	public class STIfStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STIfStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cIFKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cConditionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cConditionSTExpressionParserRuleCall_1_0 = (RuleCall)cConditionAssignment_1.eContents().get(0);
		private final Keyword cTHENKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cStatementsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cStatementsSTStatementParserRuleCall_3_0 = (RuleCall)cStatementsAssignment_3.eContents().get(0);
		private final Assignment cElseifsAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cElseifsSTElseIfPartParserRuleCall_4_0 = (RuleCall)cElseifsAssignment_4.eContents().get(0);
		private final Assignment cElseAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cElseSTElsePartParserRuleCall_5_0 = (RuleCall)cElseAssignment_5.eContents().get(0);
		private final Keyword cEND_IFKeyword_6 = (Keyword)cGroup.eContents().get(6);
		
		//STIfStatement:
		//    'IF' condition=STExpression 'THEN' statements+=STStatement* elseifs+=(STElseIfPart)* (else=STElsePart)? 'END_IF';
		@Override public ParserRule getRule() { return rule; }
		
		//'IF' condition=STExpression 'THEN' statements+=STStatement* elseifs+=(STElseIfPart)* (else=STElsePart)? 'END_IF'
		public Group getGroup() { return cGroup; }
		
		//'IF'
		public Keyword getIFKeyword_0() { return cIFKeyword_0; }
		
		//condition=STExpression
		public Assignment getConditionAssignment_1() { return cConditionAssignment_1; }
		
		//STExpression
		public RuleCall getConditionSTExpressionParserRuleCall_1_0() { return cConditionSTExpressionParserRuleCall_1_0; }
		
		//'THEN'
		public Keyword getTHENKeyword_2() { return cTHENKeyword_2; }
		
		//statements+=STStatement*
		public Assignment getStatementsAssignment_3() { return cStatementsAssignment_3; }
		
		//STStatement
		public RuleCall getStatementsSTStatementParserRuleCall_3_0() { return cStatementsSTStatementParserRuleCall_3_0; }
		
		//elseifs+=(STElseIfPart)*
		public Assignment getElseifsAssignment_4() { return cElseifsAssignment_4; }
		
		//(STElseIfPart)
		public RuleCall getElseifsSTElseIfPartParserRuleCall_4_0() { return cElseifsSTElseIfPartParserRuleCall_4_0; }
		
		//(else=STElsePart)?
		public Assignment getElseAssignment_5() { return cElseAssignment_5; }
		
		//STElsePart
		public RuleCall getElseSTElsePartParserRuleCall_5_0() { return cElseSTElsePartParserRuleCall_5_0; }
		
		//'END_IF'
		public Keyword getEND_IFKeyword_6() { return cEND_IFKeyword_6; }
	}
	public class STElseIfPartElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STElseIfPart");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cELSIFKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cConditionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cConditionSTExpressionParserRuleCall_1_0 = (RuleCall)cConditionAssignment_1.eContents().get(0);
		private final Keyword cTHENKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cStatementsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cStatementsSTStatementParserRuleCall_3_0 = (RuleCall)cStatementsAssignment_3.eContents().get(0);
		
		//STElseIfPart:
		//    'ELSIF' condition=STExpression 'THEN' statements+=STStatement*;
		@Override public ParserRule getRule() { return rule; }
		
		//'ELSIF' condition=STExpression 'THEN' statements+=STStatement*
		public Group getGroup() { return cGroup; }
		
		//'ELSIF'
		public Keyword getELSIFKeyword_0() { return cELSIFKeyword_0; }
		
		//condition=STExpression
		public Assignment getConditionAssignment_1() { return cConditionAssignment_1; }
		
		//STExpression
		public RuleCall getConditionSTExpressionParserRuleCall_1_0() { return cConditionSTExpressionParserRuleCall_1_0; }
		
		//'THEN'
		public Keyword getTHENKeyword_2() { return cTHENKeyword_2; }
		
		//statements+=STStatement*
		public Assignment getStatementsAssignment_3() { return cStatementsAssignment_3; }
		
		//STStatement
		public RuleCall getStatementsSTStatementParserRuleCall_3_0() { return cStatementsSTStatementParserRuleCall_3_0; }
	}
	public class STCaseStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STCaseStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cCASEKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cSelectorAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cSelectorSTExpressionParserRuleCall_1_0 = (RuleCall)cSelectorAssignment_1.eContents().get(0);
		private final Keyword cOFKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cCasesAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cCasesSTCaseCasesParserRuleCall_3_0 = (RuleCall)cCasesAssignment_3.eContents().get(0);
		private final Assignment cElseAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cElseSTElsePartParserRuleCall_4_0 = (RuleCall)cElseAssignment_4.eContents().get(0);
		private final Keyword cEND_CASEKeyword_5 = (Keyword)cGroup.eContents().get(5);
		
		//STCaseStatement:
		//    'CASE' selector=STExpression 'OF' cases+=STCaseCases+ (else=STElsePart)? 'END_CASE';
		@Override public ParserRule getRule() { return rule; }
		
		//'CASE' selector=STExpression 'OF' cases+=STCaseCases+ (else=STElsePart)? 'END_CASE'
		public Group getGroup() { return cGroup; }
		
		//'CASE'
		public Keyword getCASEKeyword_0() { return cCASEKeyword_0; }
		
		//selector=STExpression
		public Assignment getSelectorAssignment_1() { return cSelectorAssignment_1; }
		
		//STExpression
		public RuleCall getSelectorSTExpressionParserRuleCall_1_0() { return cSelectorSTExpressionParserRuleCall_1_0; }
		
		//'OF'
		public Keyword getOFKeyword_2() { return cOFKeyword_2; }
		
		//cases+=STCaseCases+
		public Assignment getCasesAssignment_3() { return cCasesAssignment_3; }
		
		//STCaseCases
		public RuleCall getCasesSTCaseCasesParserRuleCall_3_0() { return cCasesSTCaseCasesParserRuleCall_3_0; }
		
		//(else=STElsePart)?
		public Assignment getElseAssignment_4() { return cElseAssignment_4; }
		
		//STElsePart
		public RuleCall getElseSTElsePartParserRuleCall_4_0() { return cElseSTElsePartParserRuleCall_4_0; }
		
		//'END_CASE'
		public Keyword getEND_CASEKeyword_5() { return cEND_CASEKeyword_5; }
	}
	public class STCaseCasesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STCaseCases");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cConditionsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cConditionsSTExpressionParserRuleCall_0_0 = (RuleCall)cConditionsAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cCommaKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cConditionsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cConditionsSTExpressionParserRuleCall_1_1_0 = (RuleCall)cConditionsAssignment_1_1.eContents().get(0);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cStatementsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cStatementsSTStatementParserRuleCall_3_0 = (RuleCall)cStatementsAssignment_3.eContents().get(0);
		
		//STCaseCases:
		//    conditions+=STExpression (',' conditions+=STExpression)* ':' =>statements+=STStatement*;
		@Override public ParserRule getRule() { return rule; }
		
		//conditions+=STExpression (',' conditions+=STExpression)* ':' =>statements+=STStatement*
		public Group getGroup() { return cGroup; }
		
		//conditions+=STExpression
		public Assignment getConditionsAssignment_0() { return cConditionsAssignment_0; }
		
		//STExpression
		public RuleCall getConditionsSTExpressionParserRuleCall_0_0() { return cConditionsSTExpressionParserRuleCall_0_0; }
		
		//(',' conditions+=STExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//','
		public Keyword getCommaKeyword_1_0() { return cCommaKeyword_1_0; }
		
		//conditions+=STExpression
		public Assignment getConditionsAssignment_1_1() { return cConditionsAssignment_1_1; }
		
		//STExpression
		public RuleCall getConditionsSTExpressionParserRuleCall_1_1_0() { return cConditionsSTExpressionParserRuleCall_1_1_0; }
		
		//':'
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//=>statements+=STStatement*
		public Assignment getStatementsAssignment_3() { return cStatementsAssignment_3; }
		
		//STStatement
		public RuleCall getStatementsSTStatementParserRuleCall_3_0() { return cStatementsSTStatementParserRuleCall_3_0; }
	}
	public class STElsePartElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STElsePart");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTElsePartAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cELSEKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cStatementsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cStatementsSTStatementParserRuleCall_2_0 = (RuleCall)cStatementsAssignment_2.eContents().get(0);
		
		//STElsePart:
		//    {STElsePart} 'ELSE' statements+=STStatement*;
		@Override public ParserRule getRule() { return rule; }
		
		//{STElsePart} 'ELSE' statements+=STStatement*
		public Group getGroup() { return cGroup; }
		
		//{STElsePart}
		public Action getSTElsePartAction_0() { return cSTElsePartAction_0; }
		
		//'ELSE'
		public Keyword getELSEKeyword_1() { return cELSEKeyword_1; }
		
		//statements+=STStatement*
		public Assignment getStatementsAssignment_2() { return cStatementsAssignment_2; }
		
		//STStatement
		public RuleCall getStatementsSTStatementParserRuleCall_2_0() { return cStatementsSTStatementParserRuleCall_2_0; }
	}
	public class STForStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STForStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cFORKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cVariableAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cVariableSTExpressionParserRuleCall_1_0 = (RuleCall)cVariableAssignment_1.eContents().get(0);
		private final Keyword cColonEqualsSignKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cFromAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cFromSTExpressionParserRuleCall_3_0 = (RuleCall)cFromAssignment_3.eContents().get(0);
		private final Keyword cTOKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cToAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cToSTExpressionParserRuleCall_5_0 = (RuleCall)cToAssignment_5.eContents().get(0);
		private final Group cGroup_6 = (Group)cGroup.eContents().get(6);
		private final Keyword cBYKeyword_6_0 = (Keyword)cGroup_6.eContents().get(0);
		private final Assignment cByAssignment_6_1 = (Assignment)cGroup_6.eContents().get(1);
		private final RuleCall cBySTExpressionParserRuleCall_6_1_0 = (RuleCall)cByAssignment_6_1.eContents().get(0);
		private final Keyword cDOKeyword_7 = (Keyword)cGroup.eContents().get(7);
		private final Assignment cStatementsAssignment_8 = (Assignment)cGroup.eContents().get(8);
		private final RuleCall cStatementsSTStatementParserRuleCall_8_0 = (RuleCall)cStatementsAssignment_8.eContents().get(0);
		private final Keyword cEND_FORKeyword_9 = (Keyword)cGroup.eContents().get(9);
		
		//STForStatement:
		//    'FOR' variable=STExpression ':=' from=STExpression 'TO' to=STExpression ('BY' by=STExpression)? 'DO'
		//    statements+=STStatement*
		//    'END_FOR';
		@Override public ParserRule getRule() { return rule; }
		
		//'FOR' variable=STExpression ':=' from=STExpression 'TO' to=STExpression ('BY' by=STExpression)? 'DO'
		//statements+=STStatement*
		//'END_FOR'
		public Group getGroup() { return cGroup; }
		
		//'FOR'
		public Keyword getFORKeyword_0() { return cFORKeyword_0; }
		
		//variable=STExpression
		public Assignment getVariableAssignment_1() { return cVariableAssignment_1; }
		
		//STExpression
		public RuleCall getVariableSTExpressionParserRuleCall_1_0() { return cVariableSTExpressionParserRuleCall_1_0; }
		
		//':='
		public Keyword getColonEqualsSignKeyword_2() { return cColonEqualsSignKeyword_2; }
		
		//from=STExpression
		public Assignment getFromAssignment_3() { return cFromAssignment_3; }
		
		//STExpression
		public RuleCall getFromSTExpressionParserRuleCall_3_0() { return cFromSTExpressionParserRuleCall_3_0; }
		
		//'TO'
		public Keyword getTOKeyword_4() { return cTOKeyword_4; }
		
		//to=STExpression
		public Assignment getToAssignment_5() { return cToAssignment_5; }
		
		//STExpression
		public RuleCall getToSTExpressionParserRuleCall_5_0() { return cToSTExpressionParserRuleCall_5_0; }
		
		//('BY' by=STExpression)?
		public Group getGroup_6() { return cGroup_6; }
		
		//'BY'
		public Keyword getBYKeyword_6_0() { return cBYKeyword_6_0; }
		
		//by=STExpression
		public Assignment getByAssignment_6_1() { return cByAssignment_6_1; }
		
		//STExpression
		public RuleCall getBySTExpressionParserRuleCall_6_1_0() { return cBySTExpressionParserRuleCall_6_1_0; }
		
		//'DO'
		public Keyword getDOKeyword_7() { return cDOKeyword_7; }
		
		//statements+=STStatement*
		public Assignment getStatementsAssignment_8() { return cStatementsAssignment_8; }
		
		//STStatement
		public RuleCall getStatementsSTStatementParserRuleCall_8_0() { return cStatementsSTStatementParserRuleCall_8_0; }
		
		//'END_FOR'
		public Keyword getEND_FORKeyword_9() { return cEND_FORKeyword_9; }
	}
	public class STWhileStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STWhileStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cWHILEKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cConditionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cConditionSTExpressionParserRuleCall_1_0 = (RuleCall)cConditionAssignment_1.eContents().get(0);
		private final Keyword cDOKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cStatementsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cStatementsSTStatementParserRuleCall_3_0 = (RuleCall)cStatementsAssignment_3.eContents().get(0);
		private final Keyword cEND_WHILEKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STWhileStatement:
		//    'WHILE' condition=STExpression 'DO'
		//    statements+=STStatement*
		//    'END_WHILE';
		@Override public ParserRule getRule() { return rule; }
		
		//'WHILE' condition=STExpression 'DO'
		//statements+=STStatement*
		//'END_WHILE'
		public Group getGroup() { return cGroup; }
		
		//'WHILE'
		public Keyword getWHILEKeyword_0() { return cWHILEKeyword_0; }
		
		//condition=STExpression
		public Assignment getConditionAssignment_1() { return cConditionAssignment_1; }
		
		//STExpression
		public RuleCall getConditionSTExpressionParserRuleCall_1_0() { return cConditionSTExpressionParserRuleCall_1_0; }
		
		//'DO'
		public Keyword getDOKeyword_2() { return cDOKeyword_2; }
		
		//statements+=STStatement*
		public Assignment getStatementsAssignment_3() { return cStatementsAssignment_3; }
		
		//STStatement
		public RuleCall getStatementsSTStatementParserRuleCall_3_0() { return cStatementsSTStatementParserRuleCall_3_0; }
		
		//'END_WHILE'
		public Keyword getEND_WHILEKeyword_4() { return cEND_WHILEKeyword_4; }
	}
	public class STRepeatStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STRepeatStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cREPEATKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cStatementsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cStatementsSTStatementParserRuleCall_1_0 = (RuleCall)cStatementsAssignment_1.eContents().get(0);
		private final Keyword cUNTILKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cConditionAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cConditionSTExpressionParserRuleCall_3_0 = (RuleCall)cConditionAssignment_3.eContents().get(0);
		private final Keyword cEND_REPEATKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//STRepeatStatement:
		//    'REPEAT'
		//    statements+=STStatement*
		//    'UNTIL' condition=STExpression
		//    'END_REPEAT';
		@Override public ParserRule getRule() { return rule; }
		
		//'REPEAT'
		//statements+=STStatement*
		//'UNTIL' condition=STExpression
		//'END_REPEAT'
		public Group getGroup() { return cGroup; }
		
		//'REPEAT'
		public Keyword getREPEATKeyword_0() { return cREPEATKeyword_0; }
		
		//statements+=STStatement*
		public Assignment getStatementsAssignment_1() { return cStatementsAssignment_1; }
		
		//STStatement
		public RuleCall getStatementsSTStatementParserRuleCall_1_0() { return cStatementsSTStatementParserRuleCall_1_0; }
		
		//'UNTIL'
		public Keyword getUNTILKeyword_2() { return cUNTILKeyword_2; }
		
		//condition=STExpression
		public Assignment getConditionAssignment_3() { return cConditionAssignment_3; }
		
		//STExpression
		public RuleCall getConditionSTExpressionParserRuleCall_3_0() { return cConditionSTExpressionParserRuleCall_3_0; }
		
		//'END_REPEAT'
		public Keyword getEND_REPEATKeyword_4() { return cEND_REPEATKeyword_4; }
	}
	public class STExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STExpression");
		private final RuleCall cSTSubrangeExpressionParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//STExpression returns STExpression:
		//    STSubrangeExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//STSubrangeExpression
		public RuleCall getSTSubrangeExpressionParserRuleCall() { return cSTSubrangeExpressionParserRuleCall; }
	}
	public class STSubrangeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STSubrangeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTOrExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpSubrangeOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTOrExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STSubrangeExpression returns STExpression:
		//    STOrExpression (({STBinaryExpression.left=current} op=SubrangeOperator) right=STOrExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STOrExpression (({STBinaryExpression.left=current} op=SubrangeOperator) right=STOrExpression)*
		public Group getGroup() { return cGroup; }
		
		//STOrExpression
		public RuleCall getSTOrExpressionParserRuleCall_0() { return cSTOrExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=SubrangeOperator) right=STOrExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=SubrangeOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=SubrangeOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//SubrangeOperator
		public RuleCall getOpSubrangeOperatorEnumRuleCall_1_0_1_0() { return cOpSubrangeOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STOrExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STOrExpression
		public RuleCall getRightSTOrExpressionParserRuleCall_1_1_0() { return cRightSTOrExpressionParserRuleCall_1_1_0; }
	}
	public class STOrExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STOrExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTXorExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpOrOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTXorExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STOrExpression returns STExpression:
		//    STXorExpression (({STBinaryExpression.left=current} op=OrOperator) right=STXorExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STXorExpression (({STBinaryExpression.left=current} op=OrOperator) right=STXorExpression)*
		public Group getGroup() { return cGroup; }
		
		//STXorExpression
		public RuleCall getSTXorExpressionParserRuleCall_0() { return cSTXorExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=OrOperator) right=STXorExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=OrOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=OrOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//OrOperator
		public RuleCall getOpOrOperatorEnumRuleCall_1_0_1_0() { return cOpOrOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STXorExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STXorExpression
		public RuleCall getRightSTXorExpressionParserRuleCall_1_1_0() { return cRightSTXorExpressionParserRuleCall_1_1_0; }
	}
	public class STXorExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STXorExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTAndExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpXorOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTAndExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STXorExpression returns STExpression:
		//    STAndExpression (({STBinaryExpression.left=current} op=XorOperator) right=STAndExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STAndExpression (({STBinaryExpression.left=current} op=XorOperator) right=STAndExpression)*
		public Group getGroup() { return cGroup; }
		
		//STAndExpression
		public RuleCall getSTAndExpressionParserRuleCall_0() { return cSTAndExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=XorOperator) right=STAndExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=XorOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=XorOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//XorOperator
		public RuleCall getOpXorOperatorEnumRuleCall_1_0_1_0() { return cOpXorOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STAndExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STAndExpression
		public RuleCall getRightSTAndExpressionParserRuleCall_1_1_0() { return cRightSTAndExpressionParserRuleCall_1_1_0; }
	}
	public class STAndExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAndExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTEqualityExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpAndOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTEqualityExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STAndExpression returns STExpression:
		//    STEqualityExpression (({STBinaryExpression.left=current} op=AndOperator) right=STEqualityExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STEqualityExpression (({STBinaryExpression.left=current} op=AndOperator) right=STEqualityExpression)*
		public Group getGroup() { return cGroup; }
		
		//STEqualityExpression
		public RuleCall getSTEqualityExpressionParserRuleCall_0() { return cSTEqualityExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=AndOperator) right=STEqualityExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=AndOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=AndOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//AndOperator
		public RuleCall getOpAndOperatorEnumRuleCall_1_0_1_0() { return cOpAndOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STEqualityExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STEqualityExpression
		public RuleCall getRightSTEqualityExpressionParserRuleCall_1_1_0() { return cRightSTEqualityExpressionParserRuleCall_1_1_0; }
	}
	public class STEqualityExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STEqualityExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTComparisonExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpEqualityOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTComparisonExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STEqualityExpression returns STExpression:
		//    STComparisonExpression (({STBinaryExpression.left=current} op=EqualityOperator) right=STComparisonExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STComparisonExpression (({STBinaryExpression.left=current} op=EqualityOperator) right=STComparisonExpression)*
		public Group getGroup() { return cGroup; }
		
		//STComparisonExpression
		public RuleCall getSTComparisonExpressionParserRuleCall_0() { return cSTComparisonExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=EqualityOperator) right=STComparisonExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=EqualityOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=EqualityOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//EqualityOperator
		public RuleCall getOpEqualityOperatorEnumRuleCall_1_0_1_0() { return cOpEqualityOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STComparisonExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STComparisonExpression
		public RuleCall getRightSTComparisonExpressionParserRuleCall_1_1_0() { return cRightSTComparisonExpressionParserRuleCall_1_1_0; }
	}
	public class STComparisonExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STComparisonExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTAddSubExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpCompareOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTAddSubExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STComparisonExpression returns STExpression:
		//    STAddSubExpression (({STBinaryExpression.left=current} op=CompareOperator) right=STAddSubExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STAddSubExpression (({STBinaryExpression.left=current} op=CompareOperator) right=STAddSubExpression)*
		public Group getGroup() { return cGroup; }
		
		//STAddSubExpression
		public RuleCall getSTAddSubExpressionParserRuleCall_0() { return cSTAddSubExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=CompareOperator) right=STAddSubExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=CompareOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=CompareOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//CompareOperator
		public RuleCall getOpCompareOperatorEnumRuleCall_1_0_1_0() { return cOpCompareOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STAddSubExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STAddSubExpression
		public RuleCall getRightSTAddSubExpressionParserRuleCall_1_1_0() { return cRightSTAddSubExpressionParserRuleCall_1_1_0; }
	}
	public class STAddSubExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAddSubExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTMulDivModExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpAddSubOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTMulDivModExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STAddSubExpression returns STExpression:
		//    STMulDivModExpression (({STBinaryExpression.left=current} op=AddSubOperator) right=STMulDivModExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STMulDivModExpression (({STBinaryExpression.left=current} op=AddSubOperator) right=STMulDivModExpression)*
		public Group getGroup() { return cGroup; }
		
		//STMulDivModExpression
		public RuleCall getSTMulDivModExpressionParserRuleCall_0() { return cSTMulDivModExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=AddSubOperator) right=STMulDivModExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=AddSubOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=AddSubOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//AddSubOperator
		public RuleCall getOpAddSubOperatorEnumRuleCall_1_0_1_0() { return cOpAddSubOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STMulDivModExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STMulDivModExpression
		public RuleCall getRightSTMulDivModExpressionParserRuleCall_1_1_0() { return cRightSTMulDivModExpressionParserRuleCall_1_1_0; }
	}
	public class STMulDivModExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STMulDivModExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTPowerExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpMulDivModOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTPowerExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STMulDivModExpression returns STExpression:
		//    STPowerExpression (({STBinaryExpression.left=current} op=MulDivModOperator) right=STPowerExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STPowerExpression (({STBinaryExpression.left=current} op=MulDivModOperator) right=STPowerExpression)*
		public Group getGroup() { return cGroup; }
		
		//STPowerExpression
		public RuleCall getSTPowerExpressionParserRuleCall_0() { return cSTPowerExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=MulDivModOperator) right=STPowerExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=MulDivModOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=MulDivModOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//MulDivModOperator
		public RuleCall getOpMulDivModOperatorEnumRuleCall_1_0_1_0() { return cOpMulDivModOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STPowerExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STPowerExpression
		public RuleCall getRightSTPowerExpressionParserRuleCall_1_1_0() { return cRightSTPowerExpressionParserRuleCall_1_1_0; }
	}
	public class STPowerExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STPowerExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTUnaryExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cSTBinaryExpressionLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpPowerOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRightAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRightSTUnaryExpressionParserRuleCall_1_1_0 = (RuleCall)cRightAssignment_1_1.eContents().get(0);
		
		//STPowerExpression returns STExpression:
		//    STUnaryExpression (({STBinaryExpression.left=current} op=PowerOperator) right=STUnaryExpression)*;
		@Override public ParserRule getRule() { return rule; }
		
		//STUnaryExpression (({STBinaryExpression.left=current} op=PowerOperator) right=STUnaryExpression)*
		public Group getGroup() { return cGroup; }
		
		//STUnaryExpression
		public RuleCall getSTUnaryExpressionParserRuleCall_0() { return cSTUnaryExpressionParserRuleCall_0; }
		
		//(({STBinaryExpression.left=current} op=PowerOperator) right=STUnaryExpression)*
		public Group getGroup_1() { return cGroup_1; }
		
		//({STBinaryExpression.left=current} op=PowerOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STBinaryExpression.left=current}
		public Action getSTBinaryExpressionLeftAction_1_0_0() { return cSTBinaryExpressionLeftAction_1_0_0; }
		
		//op=PowerOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//PowerOperator
		public RuleCall getOpPowerOperatorEnumRuleCall_1_0_1_0() { return cOpPowerOperatorEnumRuleCall_1_0_1_0; }
		
		//right=STUnaryExpression
		public Assignment getRightAssignment_1_1() { return cRightAssignment_1_1; }
		
		//STUnaryExpression
		public RuleCall getRightSTUnaryExpressionParserRuleCall_1_1_0() { return cRightSTUnaryExpressionParserRuleCall_1_1_0; }
	}
	public class STUnaryExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STUnaryExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTAccessExpressionParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTLiteralExpressionsParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cSTSignedLiteralExpressionsParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final Group cGroup_3 = (Group)cAlternatives.eContents().get(3);
		private final Action cSTUnaryExpressionAction_3_0 = (Action)cGroup_3.eContents().get(0);
		private final Assignment cOpAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cOpUnaryOperatorEnumRuleCall_3_1_0 = (RuleCall)cOpAssignment_3_1.eContents().get(0);
		private final Assignment cExpressionAssignment_3_2 = (Assignment)cGroup_3.eContents().get(2);
		private final RuleCall cExpressionSTUnaryExpressionParserRuleCall_3_2_0 = (RuleCall)cExpressionAssignment_3_2.eContents().get(0);
		
		//STUnaryExpression returns STExpression:
		//    STAccessExpression | STLiteralExpressions | => STSignedLiteralExpressions | ({STUnaryExpression} op=UnaryOperator
		//    expression=STUnaryExpression);
		@Override public ParserRule getRule() { return rule; }
		
		//STAccessExpression | STLiteralExpressions | => STSignedLiteralExpressions | ({STUnaryExpression} op=UnaryOperator
		//expression=STUnaryExpression)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STAccessExpression
		public RuleCall getSTAccessExpressionParserRuleCall_0() { return cSTAccessExpressionParserRuleCall_0; }
		
		//STLiteralExpressions
		public RuleCall getSTLiteralExpressionsParserRuleCall_1() { return cSTLiteralExpressionsParserRuleCall_1; }
		
		//=> STSignedLiteralExpressions
		public RuleCall getSTSignedLiteralExpressionsParserRuleCall_2() { return cSTSignedLiteralExpressionsParserRuleCall_2; }
		
		//({STUnaryExpression} op=UnaryOperator
		//   expression=STUnaryExpression)
		public Group getGroup_3() { return cGroup_3; }
		
		//{STUnaryExpression}
		public Action getSTUnaryExpressionAction_3_0() { return cSTUnaryExpressionAction_3_0; }
		
		//op=UnaryOperator
		public Assignment getOpAssignment_3_1() { return cOpAssignment_3_1; }
		
		//UnaryOperator
		public RuleCall getOpUnaryOperatorEnumRuleCall_3_1_0() { return cOpUnaryOperatorEnumRuleCall_3_1_0; }
		
		//expression=STUnaryExpression
		public Assignment getExpressionAssignment_3_2() { return cExpressionAssignment_3_2; }
		
		//STUnaryExpression
		public RuleCall getExpressionSTUnaryExpressionParserRuleCall_3_2_0() { return cExpressionSTUnaryExpressionParserRuleCall_3_2_0; }
	}
	public class STAccessExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAccessExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSTPrimaryExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Action cSTMemberAccessExpressionReceiverAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Keyword cFullStopKeyword_1_0_1 = (Keyword)cGroup_1_0.eContents().get(1);
		private final Assignment cMemberAssignment_1_0_2 = (Assignment)cGroup_1_0.eContents().get(2);
		private final Alternatives cMemberAlternatives_1_0_2_0 = (Alternatives)cMemberAssignment_1_0_2.eContents().get(0);
		private final RuleCall cMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0 = (RuleCall)cMemberAlternatives_1_0_2_0.eContents().get(0);
		private final RuleCall cMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1 = (RuleCall)cMemberAlternatives_1_0_2_0.eContents().get(1);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Action cSTArrayAccessExpressionReceiverAction_1_1_0 = (Action)cGroup_1_1.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_1_1_1 = (Keyword)cGroup_1_1.eContents().get(1);
		private final Assignment cIndexAssignment_1_1_2 = (Assignment)cGroup_1_1.eContents().get(2);
		private final RuleCall cIndexSTExpressionParserRuleCall_1_1_2_0 = (RuleCall)cIndexAssignment_1_1_2.eContents().get(0);
		private final Group cGroup_1_1_3 = (Group)cGroup_1_1.eContents().get(3);
		private final Keyword cCommaKeyword_1_1_3_0 = (Keyword)cGroup_1_1_3.eContents().get(0);
		private final Assignment cIndexAssignment_1_1_3_1 = (Assignment)cGroup_1_1_3.eContents().get(1);
		private final RuleCall cIndexSTExpressionParserRuleCall_1_1_3_1_0 = (RuleCall)cIndexAssignment_1_1_3_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1_1_4 = (Keyword)cGroup_1_1.eContents().get(4);
		
		//STAccessExpression returns STExpression:
		//    STPrimaryExpression (({STMemberAccessExpression.receiver=current} '.' member=(STFeatureExpression |
		//    STMultibitPartialExpression)) |
		//    ({STArrayAccessExpression.receiver=current} '[' index+=STExpression (',' index+=STExpression)* ']'))*;
		@Override public ParserRule getRule() { return rule; }
		
		//STPrimaryExpression (({STMemberAccessExpression.receiver=current} '.' member=(STFeatureExpression |
		//STMultibitPartialExpression)) |
		//({STArrayAccessExpression.receiver=current} '[' index+=STExpression (',' index+=STExpression)* ']'))*
		public Group getGroup() { return cGroup; }
		
		//STPrimaryExpression
		public RuleCall getSTPrimaryExpressionParserRuleCall_0() { return cSTPrimaryExpressionParserRuleCall_0; }
		
		//(({STMemberAccessExpression.receiver=current} '.' member=(STFeatureExpression |
		//   STMultibitPartialExpression)) |
		//   ({STArrayAccessExpression.receiver=current} '[' index+=STExpression (',' index+=STExpression)* ']'))*
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//({STMemberAccessExpression.receiver=current} '.' member=(STFeatureExpression |
		//    STMultibitPartialExpression))
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{STMemberAccessExpression.receiver=current}
		public Action getSTMemberAccessExpressionReceiverAction_1_0_0() { return cSTMemberAccessExpressionReceiverAction_1_0_0; }
		
		//'.'
		public Keyword getFullStopKeyword_1_0_1() { return cFullStopKeyword_1_0_1; }
		
		//member=(STFeatureExpression |
		//   STMultibitPartialExpression)
		public Assignment getMemberAssignment_1_0_2() { return cMemberAssignment_1_0_2; }
		
		//(STFeatureExpression |
		//    STMultibitPartialExpression)
		public Alternatives getMemberAlternatives_1_0_2_0() { return cMemberAlternatives_1_0_2_0; }
		
		//STFeatureExpression
		public RuleCall getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0() { return cMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0; }
		
		//STMultibitPartialExpression
		public RuleCall getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1() { return cMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1; }
		
		//({STArrayAccessExpression.receiver=current} '[' index+=STExpression (',' index+=STExpression)* ']')
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//{STArrayAccessExpression.receiver=current}
		public Action getSTArrayAccessExpressionReceiverAction_1_1_0() { return cSTArrayAccessExpressionReceiverAction_1_1_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_1_1_1() { return cLeftSquareBracketKeyword_1_1_1; }
		
		//index+=STExpression
		public Assignment getIndexAssignment_1_1_2() { return cIndexAssignment_1_1_2; }
		
		//STExpression
		public RuleCall getIndexSTExpressionParserRuleCall_1_1_2_0() { return cIndexSTExpressionParserRuleCall_1_1_2_0; }
		
		//(',' index+=STExpression)*
		public Group getGroup_1_1_3() { return cGroup_1_1_3; }
		
		//','
		public Keyword getCommaKeyword_1_1_3_0() { return cCommaKeyword_1_1_3_0; }
		
		//index+=STExpression
		public Assignment getIndexAssignment_1_1_3_1() { return cIndexAssignment_1_1_3_1; }
		
		//STExpression
		public RuleCall getIndexSTExpressionParserRuleCall_1_1_3_1_0() { return cIndexSTExpressionParserRuleCall_1_1_3_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1_1_4() { return cRightSquareBracketKeyword_1_1_4; }
	}
	public class STPrimaryExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STPrimaryExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_0_0 = (Keyword)cGroup_0.eContents().get(0);
		private final RuleCall cSTExpressionParserRuleCall_0_1 = (RuleCall)cGroup_0.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_0_2 = (Keyword)cGroup_0.eContents().get(2);
		private final RuleCall cSTFeatureExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cSTBuiltinFeatureExpressionParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//STPrimaryExpression returns STExpression:
		//    '(' STExpression ')' | STFeatureExpression | STBuiltinFeatureExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//'(' STExpression ')' | STFeatureExpression | STBuiltinFeatureExpression
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'(' STExpression ')'
		public Group getGroup_0() { return cGroup_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0_0() { return cLeftParenthesisKeyword_0_0; }
		
		//STExpression
		public RuleCall getSTExpressionParserRuleCall_0_1() { return cSTExpressionParserRuleCall_0_1; }
		
		//')'
		public Keyword getRightParenthesisKeyword_0_2() { return cRightParenthesisKeyword_0_2; }
		
		//STFeatureExpression
		public RuleCall getSTFeatureExpressionParserRuleCall_1() { return cSTFeatureExpressionParserRuleCall_1; }
		
		//STBuiltinFeatureExpression
		public RuleCall getSTBuiltinFeatureExpressionParserRuleCall_2() { return cSTBuiltinFeatureExpressionParserRuleCall_2; }
	}
	public class STFeatureExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STFeatureExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTFeatureExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cFeatureAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final CrossReference cFeatureINamedElementCrossReference_1_0 = (CrossReference)cFeatureAssignment_1.eContents().get(0);
		private final RuleCall cFeatureINamedElementSTFeatureNameParserRuleCall_1_0_1 = (RuleCall)cFeatureINamedElementCrossReference_1_0.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Assignment cCallAssignment_2_0 = (Assignment)cGroup_2.eContents().get(0);
		private final Keyword cCallLeftParenthesisKeyword_2_0_0 = (Keyword)cCallAssignment_2_0.eContents().get(0);
		private final Group cGroup_2_1 = (Group)cGroup_2.eContents().get(1);
		private final Assignment cParametersAssignment_2_1_0 = (Assignment)cGroup_2_1.eContents().get(0);
		private final RuleCall cParametersSTCallArgumentParserRuleCall_2_1_0_0 = (RuleCall)cParametersAssignment_2_1_0.eContents().get(0);
		private final Group cGroup_2_1_1 = (Group)cGroup_2_1.eContents().get(1);
		private final Keyword cCommaKeyword_2_1_1_0 = (Keyword)cGroup_2_1_1.eContents().get(0);
		private final Assignment cParametersAssignment_2_1_1_1 = (Assignment)cGroup_2_1_1.eContents().get(1);
		private final RuleCall cParametersSTCallArgumentParserRuleCall_2_1_1_1_0 = (RuleCall)cParametersAssignment_2_1_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2_2 = (Keyword)cGroup_2.eContents().get(2);
		
		//STFeatureExpression returns STExpression:
		//    {STFeatureExpression} feature=[libraryElement::INamedElement|STFeatureName]
		//    (call?='('
		//    (parameters+=STCallArgument (',' parameters+=STCallArgument)*)?
		//    ')')?;
		@Override public ParserRule getRule() { return rule; }
		
		//{STFeatureExpression} feature=[libraryElement::INamedElement|STFeatureName]
		//(call?='('
		//(parameters+=STCallArgument (',' parameters+=STCallArgument)*)?
		//')')?
		public Group getGroup() { return cGroup; }
		
		//{STFeatureExpression}
		public Action getSTFeatureExpressionAction_0() { return cSTFeatureExpressionAction_0; }
		
		//feature=[libraryElement::INamedElement|STFeatureName]
		public Assignment getFeatureAssignment_1() { return cFeatureAssignment_1; }
		
		//[libraryElement::INamedElement|STFeatureName]
		public CrossReference getFeatureINamedElementCrossReference_1_0() { return cFeatureINamedElementCrossReference_1_0; }
		
		//STFeatureName
		public RuleCall getFeatureINamedElementSTFeatureNameParserRuleCall_1_0_1() { return cFeatureINamedElementSTFeatureNameParserRuleCall_1_0_1; }
		
		//(call?='('
		//(parameters+=STCallArgument (',' parameters+=STCallArgument)*)?
		//')')?
		public Group getGroup_2() { return cGroup_2; }
		
		//call?='('
		public Assignment getCallAssignment_2_0() { return cCallAssignment_2_0; }
		
		//'('
		public Keyword getCallLeftParenthesisKeyword_2_0_0() { return cCallLeftParenthesisKeyword_2_0_0; }
		
		//(parameters+=STCallArgument (',' parameters+=STCallArgument)*)?
		public Group getGroup_2_1() { return cGroup_2_1; }
		
		//parameters+=STCallArgument
		public Assignment getParametersAssignment_2_1_0() { return cParametersAssignment_2_1_0; }
		
		//STCallArgument
		public RuleCall getParametersSTCallArgumentParserRuleCall_2_1_0_0() { return cParametersSTCallArgumentParserRuleCall_2_1_0_0; }
		
		//(',' parameters+=STCallArgument)*
		public Group getGroup_2_1_1() { return cGroup_2_1_1; }
		
		//','
		public Keyword getCommaKeyword_2_1_1_0() { return cCommaKeyword_2_1_1_0; }
		
		//parameters+=STCallArgument
		public Assignment getParametersAssignment_2_1_1_1() { return cParametersAssignment_2_1_1_1; }
		
		//STCallArgument
		public RuleCall getParametersSTCallArgumentParserRuleCall_2_1_1_1_0() { return cParametersSTCallArgumentParserRuleCall_2_1_1_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2_2() { return cRightParenthesisKeyword_2_2; }
	}
	public class STFeatureNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STFeatureName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cQualifiedNameParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Keyword cLTKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cANDKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cORKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cXORKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cMODKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final Keyword cDKeyword_6 = (Keyword)cAlternatives.eContents().get(6);
		private final Keyword cDTKeyword_7 = (Keyword)cAlternatives.eContents().get(7);
		private final Keyword cLDKeyword_8 = (Keyword)cAlternatives.eContents().get(8);
		
		//STFeatureName:
		//    QualifiedName | 'LT' | 'AND' | 'OR' | 'XOR' | 'MOD' | 'D' | 'DT' | 'LD';
		@Override public ParserRule getRule() { return rule; }
		
		//QualifiedName | 'LT' | 'AND' | 'OR' | 'XOR' | 'MOD' | 'D' | 'DT' | 'LD'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//QualifiedName
		public RuleCall getQualifiedNameParserRuleCall_0() { return cQualifiedNameParserRuleCall_0; }
		
		//'LT'
		public Keyword getLTKeyword_1() { return cLTKeyword_1; }
		
		//'AND'
		public Keyword getANDKeyword_2() { return cANDKeyword_2; }
		
		//'OR'
		public Keyword getORKeyword_3() { return cORKeyword_3; }
		
		//'XOR'
		public Keyword getXORKeyword_4() { return cXORKeyword_4; }
		
		//'MOD'
		public Keyword getMODKeyword_5() { return cMODKeyword_5; }
		
		//'D'
		public Keyword getDKeyword_6() { return cDKeyword_6; }
		
		//'DT'
		public Keyword getDTKeyword_7() { return cDTKeyword_7; }
		
		//'LD'
		public Keyword getLDKeyword_8() { return cLDKeyword_8; }
	}
	public class STBuiltinFeatureExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STBuiltinFeatureExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTBuiltinFeatureExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cFeatureAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cFeatureSTBuiltinFeatureEnumRuleCall_1_0 = (RuleCall)cFeatureAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Assignment cCallAssignment_2_0 = (Assignment)cGroup_2.eContents().get(0);
		private final Keyword cCallLeftParenthesisKeyword_2_0_0 = (Keyword)cCallAssignment_2_0.eContents().get(0);
		private final Group cGroup_2_1 = (Group)cGroup_2.eContents().get(1);
		private final Assignment cParametersAssignment_2_1_0 = (Assignment)cGroup_2_1.eContents().get(0);
		private final RuleCall cParametersSTCallArgumentParserRuleCall_2_1_0_0 = (RuleCall)cParametersAssignment_2_1_0.eContents().get(0);
		private final Group cGroup_2_1_1 = (Group)cGroup_2_1.eContents().get(1);
		private final Keyword cCommaKeyword_2_1_1_0 = (Keyword)cGroup_2_1_1.eContents().get(0);
		private final Assignment cParametersAssignment_2_1_1_1 = (Assignment)cGroup_2_1_1.eContents().get(1);
		private final RuleCall cParametersSTCallArgumentParserRuleCall_2_1_1_1_0 = (RuleCall)cParametersAssignment_2_1_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2_2 = (Keyword)cGroup_2.eContents().get(2);
		
		//STBuiltinFeatureExpression returns STExpression:
		//    {STBuiltinFeatureExpression} feature=STBuiltinFeature (call?='(' (parameters+=STCallArgument
		//    (',' parameters+=STCallArgument)*)? ')')?;
		@Override public ParserRule getRule() { return rule; }
		
		//{STBuiltinFeatureExpression} feature=STBuiltinFeature (call?='(' (parameters+=STCallArgument
		//(',' parameters+=STCallArgument)*)? ')')?
		public Group getGroup() { return cGroup; }
		
		//{STBuiltinFeatureExpression}
		public Action getSTBuiltinFeatureExpressionAction_0() { return cSTBuiltinFeatureExpressionAction_0; }
		
		//feature=STBuiltinFeature
		public Assignment getFeatureAssignment_1() { return cFeatureAssignment_1; }
		
		//STBuiltinFeature
		public RuleCall getFeatureSTBuiltinFeatureEnumRuleCall_1_0() { return cFeatureSTBuiltinFeatureEnumRuleCall_1_0; }
		
		//(call?='(' (parameters+=STCallArgument
		//   (',' parameters+=STCallArgument)*)? ')')?
		public Group getGroup_2() { return cGroup_2; }
		
		//call?='('
		public Assignment getCallAssignment_2_0() { return cCallAssignment_2_0; }
		
		//'('
		public Keyword getCallLeftParenthesisKeyword_2_0_0() { return cCallLeftParenthesisKeyword_2_0_0; }
		
		//(parameters+=STCallArgument
		//   (',' parameters+=STCallArgument)*)?
		public Group getGroup_2_1() { return cGroup_2_1; }
		
		//parameters+=STCallArgument
		public Assignment getParametersAssignment_2_1_0() { return cParametersAssignment_2_1_0; }
		
		//STCallArgument
		public RuleCall getParametersSTCallArgumentParserRuleCall_2_1_0_0() { return cParametersSTCallArgumentParserRuleCall_2_1_0_0; }
		
		//(',' parameters+=STCallArgument)*
		public Group getGroup_2_1_1() { return cGroup_2_1_1; }
		
		//','
		public Keyword getCommaKeyword_2_1_1_0() { return cCommaKeyword_2_1_1_0; }
		
		//parameters+=STCallArgument
		public Assignment getParametersAssignment_2_1_1_1() { return cParametersAssignment_2_1_1_1; }
		
		//STCallArgument
		public RuleCall getParametersSTCallArgumentParserRuleCall_2_1_1_1_0() { return cParametersSTCallArgumentParserRuleCall_2_1_1_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2_2() { return cRightParenthesisKeyword_2_2; }
	}
	public class STMultibitPartialExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STMultibitPartialExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTMultibitPartialExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cSpecifierAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0 = (RuleCall)cSpecifierAssignment_1.eContents().get(0);
		private final Alternatives cAlternatives_2 = (Alternatives)cGroup.eContents().get(2);
		private final Assignment cIndexAssignment_2_0 = (Assignment)cAlternatives_2.eContents().get(0);
		private final RuleCall cIndexINTTerminalRuleCall_2_0_0 = (RuleCall)cIndexAssignment_2_0.eContents().get(0);
		private final Group cGroup_2_1 = (Group)cAlternatives_2.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_2_1_0 = (Keyword)cGroup_2_1.eContents().get(0);
		private final Assignment cExpressionAssignment_2_1_1 = (Assignment)cGroup_2_1.eContents().get(1);
		private final RuleCall cExpressionSTExpressionParserRuleCall_2_1_1_0 = (RuleCall)cExpressionAssignment_2_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2_1_2 = (Keyword)cGroup_2_1.eContents().get(2);
		
		//STMultibitPartialExpression returns STExpression:
		//    {STMultibitPartialExpression} (specifier=STMultiBitAccessSpecifier)? (index=INT | ('(' expression=STExpression ')'))
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//{STMultibitPartialExpression} (specifier=STMultiBitAccessSpecifier)? (index=INT | ('(' expression=STExpression ')'))
		public Group getGroup() { return cGroup; }
		
		//{STMultibitPartialExpression}
		public Action getSTMultibitPartialExpressionAction_0() { return cSTMultibitPartialExpressionAction_0; }
		
		//(specifier=STMultiBitAccessSpecifier)?
		public Assignment getSpecifierAssignment_1() { return cSpecifierAssignment_1; }
		
		//STMultiBitAccessSpecifier
		public RuleCall getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0() { return cSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0; }
		
		//(index=INT | ('(' expression=STExpression ')'))
		public Alternatives getAlternatives_2() { return cAlternatives_2; }
		
		//index=INT
		public Assignment getIndexAssignment_2_0() { return cIndexAssignment_2_0; }
		
		//INT
		public RuleCall getIndexINTTerminalRuleCall_2_0_0() { return cIndexINTTerminalRuleCall_2_0_0; }
		
		//('(' expression=STExpression ')')
		public Group getGroup_2_1() { return cGroup_2_1; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2_1_0() { return cLeftParenthesisKeyword_2_1_0; }
		
		//expression=STExpression
		public Assignment getExpressionAssignment_2_1_1() { return cExpressionAssignment_2_1_1; }
		
		//STExpression
		public RuleCall getExpressionSTExpressionParserRuleCall_2_1_1_0() { return cExpressionSTExpressionParserRuleCall_2_1_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2_1_2() { return cRightParenthesisKeyword_2_1_2; }
	}
	public class STLiteralExpressionsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STLiteralExpressions");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTNumericLiteralParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTDateLiteralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cSTTimeLiteralParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cSTTimeOfDayLiteralParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cSTDateAndTimeLiteralParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cSTStringLiteralParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cSTEnumLiteralParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		
		//STLiteralExpressions returns STExpression:
		//    STNumericLiteral |
		//    STDateLiteral |
		//    STTimeLiteral |
		//    STTimeOfDayLiteral |
		//    STDateAndTimeLiteral |
		//    STStringLiteral |
		//    STEnumLiteral;
		@Override public ParserRule getRule() { return rule; }
		
		//STNumericLiteral |
		//STDateLiteral |
		//STTimeLiteral |
		//STTimeOfDayLiteral |
		//STDateAndTimeLiteral |
		//STStringLiteral |
		//STEnumLiteral
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STNumericLiteral
		public RuleCall getSTNumericLiteralParserRuleCall_0() { return cSTNumericLiteralParserRuleCall_0; }
		
		//STDateLiteral
		public RuleCall getSTDateLiteralParserRuleCall_1() { return cSTDateLiteralParserRuleCall_1; }
		
		//STTimeLiteral
		public RuleCall getSTTimeLiteralParserRuleCall_2() { return cSTTimeLiteralParserRuleCall_2; }
		
		//STTimeOfDayLiteral
		public RuleCall getSTTimeOfDayLiteralParserRuleCall_3() { return cSTTimeOfDayLiteralParserRuleCall_3; }
		
		//STDateAndTimeLiteral
		public RuleCall getSTDateAndTimeLiteralParserRuleCall_4() { return cSTDateAndTimeLiteralParserRuleCall_4; }
		
		//STStringLiteral
		public RuleCall getSTStringLiteralParserRuleCall_5() { return cSTStringLiteralParserRuleCall_5; }
		
		//STEnumLiteral
		public RuleCall getSTEnumLiteralParserRuleCall_6() { return cSTEnumLiteralParserRuleCall_6; }
	}
	public class STSignedLiteralExpressionsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STSignedLiteralExpressions");
		private final RuleCall cSTSignedNumericLiteralParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//STSignedLiteralExpressions returns STExpression:
		//    STSignedNumericLiteral;
		@Override public ParserRule getRule() { return rule; }
		
		//STSignedNumericLiteral
		public RuleCall getSTSignedNumericLiteralParserRuleCall() { return cSTSignedNumericLiteralParserRuleCall; }
	}
	public class STNumericLiteralTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STNumericLiteralType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTAnyBitTypeParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTAnyNumTypeParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//STNumericLiteralType:
		//    STAnyBitType | STAnyNumType;
		@Override public ParserRule getRule() { return rule; }
		
		//STAnyBitType | STAnyNumType
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STAnyBitType
		public RuleCall getSTAnyBitTypeParserRuleCall_0() { return cSTAnyBitTypeParserRuleCall_0; }
		
		//STAnyNumType
		public RuleCall getSTAnyNumTypeParserRuleCall_1() { return cSTAnyNumTypeParserRuleCall_1; }
	}
	public class STNumericLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STNumericLiteral");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Assignment cTypeAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final CrossReference cTypeDataTypeCrossReference_0_0_0 = (CrossReference)cTypeAssignment_0_0.eContents().get(0);
		private final RuleCall cTypeDataTypeSTNumericLiteralTypeParserRuleCall_0_0_0_1 = (RuleCall)cTypeDataTypeCrossReference_0_0_0.eContents().get(1);
		private final Keyword cNumberSignKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Assignment cValueAssignment_0_2 = (Assignment)cGroup_0.eContents().get(2);
		private final RuleCall cValueSignedNumericParserRuleCall_0_2_0 = (RuleCall)cValueAssignment_0_2.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Assignment cTypeAssignment_1_0_0 = (Assignment)cGroup_1_0.eContents().get(0);
		private final CrossReference cTypeDataTypeCrossReference_1_0_0_0 = (CrossReference)cTypeAssignment_1_0_0.eContents().get(0);
		private final RuleCall cTypeDataTypeSTNumericLiteralTypeParserRuleCall_1_0_0_0_1 = (RuleCall)cTypeDataTypeCrossReference_1_0_0_0.eContents().get(1);
		private final Keyword cNumberSignKeyword_1_0_1 = (Keyword)cGroup_1_0.eContents().get(1);
		private final Assignment cValueAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cValueNumericParserRuleCall_1_1_0 = (RuleCall)cValueAssignment_1_1.eContents().get(0);
		
		//STNumericLiteral:
		//    (type=[datatype::DataType|STNumericLiteralType] '#' value=SignedNumeric) |
		//    ((type=[datatype::DataType|STNumericLiteralType] '#')?
		//    value=Numeric);
		@Override public ParserRule getRule() { return rule; }
		
		//(type=[datatype::DataType|STNumericLiteralType] '#' value=SignedNumeric) |
		//((type=[datatype::DataType|STNumericLiteralType] '#')?
		//value=Numeric)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//(type=[datatype::DataType|STNumericLiteralType] '#' value=SignedNumeric)
		public Group getGroup_0() { return cGroup_0; }
		
		//type=[datatype::DataType|STNumericLiteralType]
		public Assignment getTypeAssignment_0_0() { return cTypeAssignment_0_0; }
		
		//[datatype::DataType|STNumericLiteralType]
		public CrossReference getTypeDataTypeCrossReference_0_0_0() { return cTypeDataTypeCrossReference_0_0_0; }
		
		//STNumericLiteralType
		public RuleCall getTypeDataTypeSTNumericLiteralTypeParserRuleCall_0_0_0_1() { return cTypeDataTypeSTNumericLiteralTypeParserRuleCall_0_0_0_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_0_1() { return cNumberSignKeyword_0_1; }
		
		//value=SignedNumeric
		public Assignment getValueAssignment_0_2() { return cValueAssignment_0_2; }
		
		//SignedNumeric
		public RuleCall getValueSignedNumericParserRuleCall_0_2_0() { return cValueSignedNumericParserRuleCall_0_2_0; }
		
		//((type=[datatype::DataType|STNumericLiteralType] '#')?
		//value=Numeric)
		public Group getGroup_1() { return cGroup_1; }
		
		//(type=[datatype::DataType|STNumericLiteralType] '#')?
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//type=[datatype::DataType|STNumericLiteralType]
		public Assignment getTypeAssignment_1_0_0() { return cTypeAssignment_1_0_0; }
		
		//[datatype::DataType|STNumericLiteralType]
		public CrossReference getTypeDataTypeCrossReference_1_0_0_0() { return cTypeDataTypeCrossReference_1_0_0_0; }
		
		//STNumericLiteralType
		public RuleCall getTypeDataTypeSTNumericLiteralTypeParserRuleCall_1_0_0_0_1() { return cTypeDataTypeSTNumericLiteralTypeParserRuleCall_1_0_0_0_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_1_0_1() { return cNumberSignKeyword_1_0_1; }
		
		//value=Numeric
		public Assignment getValueAssignment_1_1() { return cValueAssignment_1_1; }
		
		//Numeric
		public RuleCall getValueNumericParserRuleCall_1_1_0() { return cValueNumericParserRuleCall_1_1_0; }
	}
	public class STSignedNumericLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STSignedNumericLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueSignedNumericParserRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//STSignedNumericLiteral returns STNumericLiteral:
		//    value=SignedNumeric;
		@Override public ParserRule getRule() { return rule; }
		
		//value=SignedNumeric
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//SignedNumeric
		public RuleCall getValueSignedNumericParserRuleCall_0() { return cValueSignedNumericParserRuleCall_0; }
	}
	public class STDateLiteralTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STDateLiteralType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTDateTypeParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Keyword cDKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cLDKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		
		//STDateLiteralType:
		//    STDateType |
		//    'D' |
		//    'LD';
		@Override public ParserRule getRule() { return rule; }
		
		//STDateType |
		//'D' |
		//'LD'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STDateType
		public RuleCall getSTDateTypeParserRuleCall_0() { return cSTDateTypeParserRuleCall_0; }
		
		//'D'
		public Keyword getDKeyword_1() { return cDKeyword_1; }
		
		//'LD'
		public Keyword getLDKeyword_2() { return cLDKeyword_2; }
	}
	public class STDateLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STDateLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cTypeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cTypeDataTypeCrossReference_0_0 = (CrossReference)cTypeAssignment_0.eContents().get(0);
		private final RuleCall cTypeDataTypeSTDateLiteralTypeParserRuleCall_0_0_1 = (RuleCall)cTypeDataTypeCrossReference_0_0.eContents().get(1);
		private final Keyword cNumberSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cValueAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cValueDateParserRuleCall_2_0 = (RuleCall)cValueAssignment_2.eContents().get(0);
		
		//STDateLiteral:
		//    type=[datatype::DataType|STDateLiteralType] '#' value=Date;
		@Override public ParserRule getRule() { return rule; }
		
		//type=[datatype::DataType|STDateLiteralType] '#' value=Date
		public Group getGroup() { return cGroup; }
		
		//type=[datatype::DataType|STDateLiteralType]
		public Assignment getTypeAssignment_0() { return cTypeAssignment_0; }
		
		//[datatype::DataType|STDateLiteralType]
		public CrossReference getTypeDataTypeCrossReference_0_0() { return cTypeDataTypeCrossReference_0_0; }
		
		//STDateLiteralType
		public RuleCall getTypeDataTypeSTDateLiteralTypeParserRuleCall_0_0_1() { return cTypeDataTypeSTDateLiteralTypeParserRuleCall_0_0_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_1() { return cNumberSignKeyword_1; }
		
		//value=Date
		public Assignment getValueAssignment_2() { return cValueAssignment_2; }
		
		//Date
		public RuleCall getValueDateParserRuleCall_2_0() { return cValueDateParserRuleCall_2_0; }
	}
	public class STTimeLiteralTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STTimeLiteralType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTAnyDurationTypeParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Keyword cTKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cLTKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		
		//STTimeLiteralType:
		//    STAnyDurationType |
		//    'T' |
		//    'LT';
		@Override public ParserRule getRule() { return rule; }
		
		//STAnyDurationType |
		//'T' |
		//'LT'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STAnyDurationType
		public RuleCall getSTAnyDurationTypeParserRuleCall_0() { return cSTAnyDurationTypeParserRuleCall_0; }
		
		//'T'
		public Keyword getTKeyword_1() { return cTKeyword_1; }
		
		//'LT'
		public Keyword getLTKeyword_2() { return cLTKeyword_2; }
	}
	public class STTimeLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STTimeLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cTypeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cTypeDataTypeCrossReference_0_0 = (CrossReference)cTypeAssignment_0.eContents().get(0);
		private final RuleCall cTypeDataTypeSTTimeLiteralTypeParserRuleCall_0_0_1 = (RuleCall)cTypeDataTypeCrossReference_0_0.eContents().get(1);
		private final Keyword cNumberSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cValueAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cValueTimeParserRuleCall_2_0 = (RuleCall)cValueAssignment_2.eContents().get(0);
		
		//STTimeLiteral:
		//    type=[datatype::DataType|STTimeLiteralType] '#' value=Time;
		@Override public ParserRule getRule() { return rule; }
		
		//type=[datatype::DataType|STTimeLiteralType] '#' value=Time
		public Group getGroup() { return cGroup; }
		
		//type=[datatype::DataType|STTimeLiteralType]
		public Assignment getTypeAssignment_0() { return cTypeAssignment_0; }
		
		//[datatype::DataType|STTimeLiteralType]
		public CrossReference getTypeDataTypeCrossReference_0_0() { return cTypeDataTypeCrossReference_0_0; }
		
		//STTimeLiteralType
		public RuleCall getTypeDataTypeSTTimeLiteralTypeParserRuleCall_0_0_1() { return cTypeDataTypeSTTimeLiteralTypeParserRuleCall_0_0_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_1() { return cNumberSignKeyword_1; }
		
		//value=Time
		public Assignment getValueAssignment_2() { return cValueAssignment_2; }
		
		//Time
		public RuleCall getValueTimeParserRuleCall_2_0() { return cValueTimeParserRuleCall_2_0; }
	}
	public class STTimeOfDayLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STTimeOfDayLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cTypeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cTypeDataTypeCrossReference_0_0 = (CrossReference)cTypeAssignment_0.eContents().get(0);
		private final RuleCall cTypeDataTypeSTTimeOfDayTypeParserRuleCall_0_0_1 = (RuleCall)cTypeDataTypeCrossReference_0_0.eContents().get(1);
		private final Keyword cNumberSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cValueAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cValueTimeOfDayParserRuleCall_2_0 = (RuleCall)cValueAssignment_2.eContents().get(0);
		
		//STTimeOfDayLiteral:
		//    type=[datatype::DataType|STTimeOfDayType] '#' value=TimeOfDay;
		@Override public ParserRule getRule() { return rule; }
		
		//type=[datatype::DataType|STTimeOfDayType] '#' value=TimeOfDay
		public Group getGroup() { return cGroup; }
		
		//type=[datatype::DataType|STTimeOfDayType]
		public Assignment getTypeAssignment_0() { return cTypeAssignment_0; }
		
		//[datatype::DataType|STTimeOfDayType]
		public CrossReference getTypeDataTypeCrossReference_0_0() { return cTypeDataTypeCrossReference_0_0; }
		
		//STTimeOfDayType
		public RuleCall getTypeDataTypeSTTimeOfDayTypeParserRuleCall_0_0_1() { return cTypeDataTypeSTTimeOfDayTypeParserRuleCall_0_0_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_1() { return cNumberSignKeyword_1; }
		
		//value=TimeOfDay
		public Assignment getValueAssignment_2() { return cValueAssignment_2; }
		
		//TimeOfDay
		public RuleCall getValueTimeOfDayParserRuleCall_2_0() { return cValueTimeOfDayParserRuleCall_2_0; }
	}
	public class STDateAndTimeLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STDateAndTimeLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cTypeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cTypeDataTypeCrossReference_0_0 = (CrossReference)cTypeAssignment_0.eContents().get(0);
		private final RuleCall cTypeDataTypeSTDateAndTimeTypeParserRuleCall_0_0_1 = (RuleCall)cTypeDataTypeCrossReference_0_0.eContents().get(1);
		private final Keyword cNumberSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cValueAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cValueDateAndTimeParserRuleCall_2_0 = (RuleCall)cValueAssignment_2.eContents().get(0);
		
		//STDateAndTimeLiteral:
		//    type=[datatype::DataType|STDateAndTimeType] '#' value=DateAndTime;
		@Override public ParserRule getRule() { return rule; }
		
		//type=[datatype::DataType|STDateAndTimeType] '#' value=DateAndTime
		public Group getGroup() { return cGroup; }
		
		//type=[datatype::DataType|STDateAndTimeType]
		public Assignment getTypeAssignment_0() { return cTypeAssignment_0; }
		
		//[datatype::DataType|STDateAndTimeType]
		public CrossReference getTypeDataTypeCrossReference_0_0() { return cTypeDataTypeCrossReference_0_0; }
		
		//STDateAndTimeType
		public RuleCall getTypeDataTypeSTDateAndTimeTypeParserRuleCall_0_0_1() { return cTypeDataTypeSTDateAndTimeTypeParserRuleCall_0_0_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_1() { return cNumberSignKeyword_1; }
		
		//value=DateAndTime
		public Assignment getValueAssignment_2() { return cValueAssignment_2; }
		
		//DateAndTime
		public RuleCall getValueDateAndTimeParserRuleCall_2_0() { return cValueDateAndTimeParserRuleCall_2_0; }
	}
	public class STStringLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STStringLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Assignment cTypeAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final CrossReference cTypeDataTypeCrossReference_0_0_0 = (CrossReference)cTypeAssignment_0_0.eContents().get(0);
		private final RuleCall cTypeDataTypeSTAnyCharsTypeParserRuleCall_0_0_0_1 = (RuleCall)cTypeDataTypeCrossReference_0_0_0.eContents().get(1);
		private final Keyword cNumberSignKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Assignment cValueAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cValueSTRINGTerminalRuleCall_1_0 = (RuleCall)cValueAssignment_1.eContents().get(0);
		
		//STStringLiteral:
		//    (type=[datatype::DataType|STAnyCharsType] '#')? value=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//(type=[datatype::DataType|STAnyCharsType] '#')? value=STRING
		public Group getGroup() { return cGroup; }
		
		//(type=[datatype::DataType|STAnyCharsType] '#')?
		public Group getGroup_0() { return cGroup_0; }
		
		//type=[datatype::DataType|STAnyCharsType]
		public Assignment getTypeAssignment_0_0() { return cTypeAssignment_0_0; }
		
		//[datatype::DataType|STAnyCharsType]
		public CrossReference getTypeDataTypeCrossReference_0_0_0() { return cTypeDataTypeCrossReference_0_0_0; }
		
		//STAnyCharsType
		public RuleCall getTypeDataTypeSTAnyCharsTypeParserRuleCall_0_0_0_1() { return cTypeDataTypeSTAnyCharsTypeParserRuleCall_0_0_0_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_0_1() { return cNumberSignKeyword_0_1; }
		
		//value=STRING
		public Assignment getValueAssignment_1() { return cValueAssignment_1; }
		
		//STRING
		public RuleCall getValueSTRINGTerminalRuleCall_1_0() { return cValueSTRINGTerminalRuleCall_1_0; }
	}
	public class STEnumLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STEnumLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final CrossReference cValueEnumeratedValueCrossReference_0 = (CrossReference)cValueAssignment.eContents().get(0);
		private final RuleCall cValueEnumeratedValueEnumValueParserRuleCall_0_1 = (RuleCall)cValueEnumeratedValueCrossReference_0.eContents().get(1);
		
		//STEnumLiteral:
		//    value=[datatype::EnumeratedValue|EnumValue];
		@Override public ParserRule getRule() { return rule; }
		
		//value=[datatype::EnumeratedValue|EnumValue]
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//[datatype::EnumeratedValue|EnumValue]
		public CrossReference getValueEnumeratedValueCrossReference_0() { return cValueEnumeratedValueCrossReference_0; }
		
		//EnumValue
		public RuleCall getValueEnumeratedValueEnumValueParserRuleCall_0_1() { return cValueEnumeratedValueEnumValueParserRuleCall_0_1; }
	}
	public class EnumValueElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.EnumValue");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cQualifiedNameParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cNumberSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final RuleCall cIDTerminalRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		//EnumValue:
		//    QualifiedName '#' ID;
		@Override public ParserRule getRule() { return rule; }
		
		//QualifiedName '#' ID
		public Group getGroup() { return cGroup; }
		
		//QualifiedName
		public RuleCall getQualifiedNameParserRuleCall_0() { return cQualifiedNameParserRuleCall_0; }
		
		//'#'
		public Keyword getNumberSignKeyword_1() { return cNumberSignKeyword_1; }
		
		//ID
		public RuleCall getIDTerminalRuleCall_2() { return cIDTerminalRuleCall_2; }
	}
	public class STAnyTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAnyType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cQualifiedNameParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTAnyBuiltinTypeParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//STAnyType:
		//    QualifiedName | STAnyBuiltinType;
		@Override public ParserRule getRule() { return rule; }
		
		//QualifiedName | STAnyBuiltinType
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//QualifiedName
		public RuleCall getQualifiedNameParserRuleCall_0() { return cQualifiedNameParserRuleCall_0; }
		
		//STAnyBuiltinType
		public RuleCall getSTAnyBuiltinTypeParserRuleCall_1() { return cSTAnyBuiltinTypeParserRuleCall_1; }
	}
	public class STAnyBuiltinTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAnyBuiltinType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTAnyBitTypeParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTAnyNumTypeParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cSTAnyDurationTypeParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cSTAnyDateTypeParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cSTAnyCharsTypeParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		
		//STAnyBuiltinType:
		//    STAnyBitType | STAnyNumType | STAnyDurationType | STAnyDateType | STAnyCharsType;
		@Override public ParserRule getRule() { return rule; }
		
		//STAnyBitType | STAnyNumType | STAnyDurationType | STAnyDateType | STAnyCharsType
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STAnyBitType
		public RuleCall getSTAnyBitTypeParserRuleCall_0() { return cSTAnyBitTypeParserRuleCall_0; }
		
		//STAnyNumType
		public RuleCall getSTAnyNumTypeParserRuleCall_1() { return cSTAnyNumTypeParserRuleCall_1; }
		
		//STAnyDurationType
		public RuleCall getSTAnyDurationTypeParserRuleCall_2() { return cSTAnyDurationTypeParserRuleCall_2; }
		
		//STAnyDateType
		public RuleCall getSTAnyDateTypeParserRuleCall_3() { return cSTAnyDateTypeParserRuleCall_3; }
		
		//STAnyCharsType
		public RuleCall getSTAnyCharsTypeParserRuleCall_4() { return cSTAnyCharsTypeParserRuleCall_4; }
	}
	public class STAnyBitTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAnyBitType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cBOOLKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cBYTEKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cWORDKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cDWORDKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cLWORDKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		
		//STAnyBitType:
		//    'BOOL' | 'BYTE' | 'WORD' | 'DWORD' | 'LWORD';
		@Override public ParserRule getRule() { return rule; }
		
		//'BOOL' | 'BYTE' | 'WORD' | 'DWORD' | 'LWORD'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'BOOL'
		public Keyword getBOOLKeyword_0() { return cBOOLKeyword_0; }
		
		//'BYTE'
		public Keyword getBYTEKeyword_1() { return cBYTEKeyword_1; }
		
		//'WORD'
		public Keyword getWORDKeyword_2() { return cWORDKeyword_2; }
		
		//'DWORD'
		public Keyword getDWORDKeyword_3() { return cDWORDKeyword_3; }
		
		//'LWORD'
		public Keyword getLWORDKeyword_4() { return cLWORDKeyword_4; }
	}
	public class STAnyNumTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAnyNumType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cSINTKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cINTKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cDINTKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cLINTKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cUSINTKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cUINTKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final Keyword cUDINTKeyword_6 = (Keyword)cAlternatives.eContents().get(6);
		private final Keyword cULINTKeyword_7 = (Keyword)cAlternatives.eContents().get(7);
		private final Keyword cREALKeyword_8 = (Keyword)cAlternatives.eContents().get(8);
		private final Keyword cLREALKeyword_9 = (Keyword)cAlternatives.eContents().get(9);
		
		//STAnyNumType:
		//    'SINT' | 'INT' | 'DINT' | 'LINT' | 'USINT' | 'UINT' | 'UDINT' | 'ULINT' | 'REAL' | 'LREAL';
		@Override public ParserRule getRule() { return rule; }
		
		//'SINT' | 'INT' | 'DINT' | 'LINT' | 'USINT' | 'UINT' | 'UDINT' | 'ULINT' | 'REAL' | 'LREAL'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'SINT'
		public Keyword getSINTKeyword_0() { return cSINTKeyword_0; }
		
		//'INT'
		public Keyword getINTKeyword_1() { return cINTKeyword_1; }
		
		//'DINT'
		public Keyword getDINTKeyword_2() { return cDINTKeyword_2; }
		
		//'LINT'
		public Keyword getLINTKeyword_3() { return cLINTKeyword_3; }
		
		//'USINT'
		public Keyword getUSINTKeyword_4() { return cUSINTKeyword_4; }
		
		//'UINT'
		public Keyword getUINTKeyword_5() { return cUINTKeyword_5; }
		
		//'UDINT'
		public Keyword getUDINTKeyword_6() { return cUDINTKeyword_6; }
		
		//'ULINT'
		public Keyword getULINTKeyword_7() { return cULINTKeyword_7; }
		
		//'REAL'
		public Keyword getREALKeyword_8() { return cREALKeyword_8; }
		
		//'LREAL'
		public Keyword getLREALKeyword_9() { return cLREALKeyword_9; }
	}
	public class STAnyDurationTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAnyDurationType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cTIMEKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cLTIMEKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		
		//STAnyDurationType:
		//    'TIME' | 'LTIME';
		@Override public ParserRule getRule() { return rule; }
		
		//'TIME' | 'LTIME'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'TIME'
		public Keyword getTIMEKeyword_0() { return cTIMEKeyword_0; }
		
		//'LTIME'
		public Keyword getLTIMEKeyword_1() { return cLTIMEKeyword_1; }
	}
	public class STAnyDateTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAnyDateType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTDateTypeParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSTTimeOfDayTypeParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cSTDateAndTimeTypeParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//STAnyDateType:
		//    STDateType | STTimeOfDayType | STDateAndTimeType;
		@Override public ParserRule getRule() { return rule; }
		
		//STDateType | STTimeOfDayType | STDateAndTimeType
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STDateType
		public RuleCall getSTDateTypeParserRuleCall_0() { return cSTDateTypeParserRuleCall_0; }
		
		//STTimeOfDayType
		public RuleCall getSTTimeOfDayTypeParserRuleCall_1() { return cSTTimeOfDayTypeParserRuleCall_1; }
		
		//STDateAndTimeType
		public RuleCall getSTDateAndTimeTypeParserRuleCall_2() { return cSTDateAndTimeTypeParserRuleCall_2; }
	}
	public class STDateTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STDateType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cDATEKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cLDATEKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		
		//STDateType:
		//    'DATE' | 'LDATE';
		@Override public ParserRule getRule() { return rule; }
		
		//'DATE' | 'LDATE'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'DATE'
		public Keyword getDATEKeyword_0() { return cDATEKeyword_0; }
		
		//'LDATE'
		public Keyword getLDATEKeyword_1() { return cLDATEKeyword_1; }
	}
	public class STTimeOfDayTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STTimeOfDayType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cTIME_OF_DAYKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cLTIME_OF_DAYKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cTODKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cLTODKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		
		//STTimeOfDayType:
		//    'TIME_OF_DAY' |
		//    'LTIME_OF_DAY' |
		//    'TOD' |
		//    'LTOD';
		@Override public ParserRule getRule() { return rule; }
		
		//'TIME_OF_DAY' |
		//'LTIME_OF_DAY' |
		//'TOD' |
		//'LTOD'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'TIME_OF_DAY'
		public Keyword getTIME_OF_DAYKeyword_0() { return cTIME_OF_DAYKeyword_0; }
		
		//'LTIME_OF_DAY'
		public Keyword getLTIME_OF_DAYKeyword_1() { return cLTIME_OF_DAYKeyword_1; }
		
		//'TOD'
		public Keyword getTODKeyword_2() { return cTODKeyword_2; }
		
		//'LTOD'
		public Keyword getLTODKeyword_3() { return cLTODKeyword_3; }
	}
	public class STDateAndTimeTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STDateAndTimeType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cDATE_AND_TIMEKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cLDATE_AND_TIMEKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cDTKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cLDTKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		
		//STDateAndTimeType:
		//    'DATE_AND_TIME' |
		//    'LDATE_AND_TIME' |
		//    'DT' |
		//    'LDT';
		@Override public ParserRule getRule() { return rule; }
		
		//'DATE_AND_TIME' |
		//'LDATE_AND_TIME' |
		//'DT' |
		//'LDT'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'DATE_AND_TIME'
		public Keyword getDATE_AND_TIMEKeyword_0() { return cDATE_AND_TIMEKeyword_0; }
		
		//'LDATE_AND_TIME'
		public Keyword getLDATE_AND_TIMEKeyword_1() { return cLDATE_AND_TIMEKeyword_1; }
		
		//'DT'
		public Keyword getDTKeyword_2() { return cDTKeyword_2; }
		
		//'LDT'
		public Keyword getLDTKeyword_3() { return cLDTKeyword_3; }
	}
	public class STAnyCharsTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAnyCharsType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cSTRINGKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cWSTRINGKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cCHARKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cWCHARKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		
		//STAnyCharsType:
		//    'STRING' | 'WSTRING' | 'CHAR' | 'WCHAR';
		@Override public ParserRule getRule() { return rule; }
		
		//'STRING' | 'WSTRING' | 'CHAR' | 'WCHAR'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'STRING'
		public Keyword getSTRINGKeyword_0() { return cSTRINGKeyword_0; }
		
		//'WSTRING'
		public Keyword getWSTRINGKeyword_1() { return cWSTRINGKeyword_1; }
		
		//'CHAR'
		public Keyword getCHARKeyword_2() { return cCHARKeyword_2; }
		
		//'WCHAR'
		public Keyword getWCHARKeyword_3() { return cWCHARKeyword_3; }
	}
	public class QualifiedNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.QualifiedName");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cIDTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cColonColonKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final RuleCall cIDTerminalRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//QualifiedName:
		//    ID ('::' ID)*;
		@Override public ParserRule getRule() { return rule; }
		
		//ID ('::' ID)*
		public Group getGroup() { return cGroup; }
		
		//ID
		public RuleCall getIDTerminalRuleCall_0() { return cIDTerminalRuleCall_0; }
		
		//('::' ID)*
		public Group getGroup_1() { return cGroup_1; }
		
		//'::'
		public Keyword getColonColonKeyword_1_0() { return cColonColonKeyword_1_0; }
		
		//ID
		public RuleCall getIDTerminalRuleCall_1_1() { return cIDTerminalRuleCall_1_1; }
	}
	public class QualifiedNameWithWildcardElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.QualifiedNameWithWildcard");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cQualifiedNameParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cColonColonAsteriskKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//QualifiedNameWithWildcard:
		//    QualifiedName '::*'?;
		@Override public ParserRule getRule() { return rule; }
		
		//QualifiedName '::*'?
		public Group getGroup() { return cGroup; }
		
		//QualifiedName
		public RuleCall getQualifiedNameParserRuleCall_0() { return cQualifiedNameParserRuleCall_0; }
		
		//'::*'?
		public Keyword getColonColonAsteriskKeyword_1() { return cColonColonAsteriskKeyword_1; }
	}
	public class NumericElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.Numeric");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cTRUEKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cFALSEKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final RuleCall cNumberParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cNON_DECIMALTerminalRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		
		//Numeric returns ecore::EJavaObject:
		//    'TRUE' | 'FALSE' | Number | NON_DECIMAL;
		@Override public ParserRule getRule() { return rule; }
		
		//'TRUE' | 'FALSE' | Number | NON_DECIMAL
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'TRUE'
		public Keyword getTRUEKeyword_0() { return cTRUEKeyword_0; }
		
		//'FALSE'
		public Keyword getFALSEKeyword_1() { return cFALSEKeyword_1; }
		
		//Number
		public RuleCall getNumberParserRuleCall_2() { return cNumberParserRuleCall_2; }
		
		//NON_DECIMAL
		public RuleCall getNON_DECIMALTerminalRuleCall_3() { return cNON_DECIMALTerminalRuleCall_3; }
	}
	public class NumberElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.Number");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Alternatives cAlternatives_1_1 = (Alternatives)cGroup_1.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_1_1_0 = (RuleCall)cAlternatives_1_1.eContents().get(0);
		private final RuleCall cDECIMALTerminalRuleCall_1_1_1 = (RuleCall)cAlternatives_1_1.eContents().get(1);
		
		//Number hidden():
		//    INT ('.' (INT | DECIMAL))?;
		@Override public ParserRule getRule() { return rule; }
		
		//INT ('.' (INT | DECIMAL))?
		public Group getGroup() { return cGroup; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_0() { return cINTTerminalRuleCall_0; }
		
		//('.' (INT | DECIMAL))?
		public Group getGroup_1() { return cGroup_1; }
		
		//'.'
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//(INT | DECIMAL)
		public Alternatives getAlternatives_1_1() { return cAlternatives_1_1; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_1_1_0() { return cINTTerminalRuleCall_1_1_0; }
		
		//DECIMAL
		public RuleCall getDECIMALTerminalRuleCall_1_1_1() { return cDECIMALTerminalRuleCall_1_1_1; }
	}
	public class SignedNumericElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.SignedNumeric");
		private final RuleCall cSignedNumberParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//SignedNumeric returns ecore::EJavaObject:
		//    SignedNumber;
		@Override public ParserRule getRule() { return rule; }
		
		//SignedNumber
		public RuleCall getSignedNumberParserRuleCall() { return cSignedNumberParserRuleCall; }
	}
	public class SignedNumberElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.SignedNumber");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Keyword cPlusSignKeyword_0_0 = (Keyword)cAlternatives_0.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_0_1 = (Keyword)cAlternatives_0.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cFullStopKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Alternatives cAlternatives_2_1 = (Alternatives)cGroup_2.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_2_1_0 = (RuleCall)cAlternatives_2_1.eContents().get(0);
		private final RuleCall cDECIMALTerminalRuleCall_2_1_1 = (RuleCall)cAlternatives_2_1.eContents().get(1);
		
		//SignedNumber hidden():
		//    ('+' | '-') INT ('.' (INT | DECIMAL))?;
		@Override public ParserRule getRule() { return rule; }
		
		//('+' | '-') INT ('.' (INT | DECIMAL))?
		public Group getGroup() { return cGroup; }
		
		//('+' | '-')
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//'+'
		public Keyword getPlusSignKeyword_0_0() { return cPlusSignKeyword_0_0; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_0_1() { return cHyphenMinusKeyword_0_1; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_1() { return cINTTerminalRuleCall_1; }
		
		//('.' (INT | DECIMAL))?
		public Group getGroup_2() { return cGroup_2; }
		
		//'.'
		public Keyword getFullStopKeyword_2_0() { return cFullStopKeyword_2_0; }
		
		//(INT | DECIMAL)
		public Alternatives getAlternatives_2_1() { return cAlternatives_2_1; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_2_1_0() { return cINTTerminalRuleCall_2_1_0; }
		
		//DECIMAL
		public RuleCall getDECIMALTerminalRuleCall_2_1_1() { return cDECIMALTerminalRuleCall_2_1_1; }
	}
	public class TimeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.Time");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Keyword cPlusSignKeyword_0_0 = (Keyword)cAlternatives_0.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_0_1 = (Keyword)cAlternatives_0.eContents().get(1);
		private final RuleCall cTIME_VALUETerminalRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//Time returns STTime hidden():
		//    ('+' | '-')? TIME_VALUE;
		@Override public ParserRule getRule() { return rule; }
		
		//('+' | '-')? TIME_VALUE
		public Group getGroup() { return cGroup; }
		
		//('+' | '-')?
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//'+'
		public Keyword getPlusSignKeyword_0_0() { return cPlusSignKeyword_0_0; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_0_1() { return cHyphenMinusKeyword_0_1; }
		
		//TIME_VALUE
		public RuleCall getTIME_VALUETerminalRuleCall_1() { return cTIME_VALUETerminalRuleCall_1; }
	}
	public class DateElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.Date");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final Keyword cHyphenMinusKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final RuleCall cINTTerminalRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		
		//Date returns STDate:
		//    INT '-' INT '-' INT;
		@Override public ParserRule getRule() { return rule; }
		
		//INT '-' INT '-' INT
		public Group getGroup() { return cGroup; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_0() { return cINTTerminalRuleCall_0; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_1() { return cHyphenMinusKeyword_1; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_2() { return cINTTerminalRuleCall_2; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_3() { return cHyphenMinusKeyword_3; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_4() { return cINTTerminalRuleCall_4; }
	}
	public class DateAndTimeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.DateAndTime");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final Keyword cHyphenMinusKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final RuleCall cINTTerminalRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		private final Keyword cHyphenMinusKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final RuleCall cINTTerminalRuleCall_6 = (RuleCall)cGroup.eContents().get(6);
		private final Keyword cColonKeyword_7 = (Keyword)cGroup.eContents().get(7);
		private final RuleCall cINTTerminalRuleCall_8 = (RuleCall)cGroup.eContents().get(8);
		private final Keyword cColonKeyword_9 = (Keyword)cGroup.eContents().get(9);
		private final RuleCall cINTTerminalRuleCall_10 = (RuleCall)cGroup.eContents().get(10);
		private final Group cGroup_11 = (Group)cGroup.eContents().get(11);
		private final Keyword cFullStopKeyword_11_0 = (Keyword)cGroup_11.eContents().get(0);
		private final RuleCall cINTTerminalRuleCall_11_1 = (RuleCall)cGroup_11.eContents().get(1);
		
		//DateAndTime returns STDateAndTime:
		//    INT '-' INT '-' INT '-' INT ':' INT ':' INT ('.' INT)?;
		@Override public ParserRule getRule() { return rule; }
		
		//INT '-' INT '-' INT '-' INT ':' INT ':' INT ('.' INT)?
		public Group getGroup() { return cGroup; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_0() { return cINTTerminalRuleCall_0; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_1() { return cHyphenMinusKeyword_1; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_2() { return cINTTerminalRuleCall_2; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_3() { return cHyphenMinusKeyword_3; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_4() { return cINTTerminalRuleCall_4; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_5() { return cHyphenMinusKeyword_5; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_6() { return cINTTerminalRuleCall_6; }
		
		//':'
		public Keyword getColonKeyword_7() { return cColonKeyword_7; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_8() { return cINTTerminalRuleCall_8; }
		
		//':'
		public Keyword getColonKeyword_9() { return cColonKeyword_9; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_10() { return cINTTerminalRuleCall_10; }
		
		//('.' INT)?
		public Group getGroup_11() { return cGroup_11; }
		
		//'.'
		public Keyword getFullStopKeyword_11_0() { return cFullStopKeyword_11_0; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_11_1() { return cINTTerminalRuleCall_11_1; }
	}
	public class TimeOfDayElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TimeOfDay");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cColonKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final Keyword cColonKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final RuleCall cINTTerminalRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		private final Group cGroup_5 = (Group)cGroup.eContents().get(5);
		private final Keyword cFullStopKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final RuleCall cINTTerminalRuleCall_5_1 = (RuleCall)cGroup_5.eContents().get(1);
		
		//TimeOfDay returns STTimeOfDay:
		//    INT ':' INT ':' INT ('.' INT)?;
		@Override public ParserRule getRule() { return rule; }
		
		//INT ':' INT ':' INT ('.' INT)?
		public Group getGroup() { return cGroup; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_0() { return cINTTerminalRuleCall_0; }
		
		//':'
		public Keyword getColonKeyword_1() { return cColonKeyword_1; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_2() { return cINTTerminalRuleCall_2; }
		
		//':'
		public Keyword getColonKeyword_3() { return cColonKeyword_3; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_4() { return cINTTerminalRuleCall_4; }
		
		//('.' INT)?
		public Group getGroup_5() { return cGroup_5; }
		
		//'.'
		public Keyword getFullStopKeyword_5_0() { return cFullStopKeyword_5_0; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_5_1() { return cINTTerminalRuleCall_5_1; }
	}
	public class RESERVED_KEYWORDSElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.RESERVED_KEYWORDS");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cABSTRACTKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cACTIONKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cCLASSKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cCONFIGURATIONKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cEND_ACTIONKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cEND_CLASSKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final Keyword cEND_CONFIGURATIONKeyword_6 = (Keyword)cAlternatives.eContents().get(6);
		private final Keyword cEND_FUNCTIONKeyword_7 = (Keyword)cAlternatives.eContents().get(7);
		private final Keyword cEND_FUNCTION_BLOCKKeyword_8 = (Keyword)cAlternatives.eContents().get(8);
		private final Keyword cEND_INTERFACEKeyword_9 = (Keyword)cAlternatives.eContents().get(9);
		private final Keyword cEND_METHODKeyword_10 = (Keyword)cAlternatives.eContents().get(10);
		private final Keyword cEND_NAMESPACEKeyword_11 = (Keyword)cAlternatives.eContents().get(11);
		private final Keyword cEND_PROGRAMKeyword_12 = (Keyword)cAlternatives.eContents().get(12);
		private final Keyword cEND_RESOURCEKeyword_13 = (Keyword)cAlternatives.eContents().get(13);
		private final Keyword cEND_STEPKeyword_14 = (Keyword)cAlternatives.eContents().get(14);
		private final Keyword cEND_STRUCTKeyword_15 = (Keyword)cAlternatives.eContents().get(15);
		private final Keyword cEND_TRANSITIONKeyword_16 = (Keyword)cAlternatives.eContents().get(16);
		private final Keyword cEND_TYPEKeyword_17 = (Keyword)cAlternatives.eContents().get(17);
		private final Keyword cEXTENDSKeyword_18 = (Keyword)cAlternatives.eContents().get(18);
		private final Keyword cFINALKeyword_19 = (Keyword)cAlternatives.eContents().get(19);
		private final Keyword cFROMKeyword_20 = (Keyword)cAlternatives.eContents().get(20);
		private final Keyword cFUNCTIONKeyword_21 = (Keyword)cAlternatives.eContents().get(21);
		private final Keyword cFUNCTION_BLOCKKeyword_22 = (Keyword)cAlternatives.eContents().get(22);
		private final Keyword cIMPLEMENTSKeyword_23 = (Keyword)cAlternatives.eContents().get(23);
		private final Keyword cINITIAL_STEPKeyword_24 = (Keyword)cAlternatives.eContents().get(24);
		private final Keyword cINTERFACEKeyword_25 = (Keyword)cAlternatives.eContents().get(25);
		private final Keyword cINTERALKeyword_26 = (Keyword)cAlternatives.eContents().get(26);
		private final Keyword cINTERVALKeyword_27 = (Keyword)cAlternatives.eContents().get(27);
		private final Keyword cMETHODKeyword_28 = (Keyword)cAlternatives.eContents().get(28);
		private final Keyword cNAMESPACEKeyword_29 = (Keyword)cAlternatives.eContents().get(29);
		private final Keyword cNON_RETAINKeyword_30 = (Keyword)cAlternatives.eContents().get(30);
		private final Keyword cNULLKeyword_31 = (Keyword)cAlternatives.eContents().get(31);
		private final Keyword cONKeyword_32 = (Keyword)cAlternatives.eContents().get(32);
		private final Keyword cOVERLAPKeyword_33 = (Keyword)cAlternatives.eContents().get(33);
		private final Keyword cOVERRIDEKeyword_34 = (Keyword)cAlternatives.eContents().get(34);
		private final Keyword cPRIORITYKeyword_35 = (Keyword)cAlternatives.eContents().get(35);
		private final Keyword cPRIVATEKeyword_36 = (Keyword)cAlternatives.eContents().get(36);
		private final Keyword cPROGRAMKeyword_37 = (Keyword)cAlternatives.eContents().get(37);
		private final Keyword cPROTECTEDKeyword_38 = (Keyword)cAlternatives.eContents().get(38);
		private final Keyword cPUBLICKeyword_39 = (Keyword)cAlternatives.eContents().get(39);
		private final Keyword cREAD_ONLYKeyword_40 = (Keyword)cAlternatives.eContents().get(40);
		private final Keyword cREAD_WRITEKeyword_41 = (Keyword)cAlternatives.eContents().get(41);
		private final Keyword cREFKeyword_42 = (Keyword)cAlternatives.eContents().get(42);
		private final Keyword cREF_TOKeyword_43 = (Keyword)cAlternatives.eContents().get(43);
		private final Keyword cRESOURCEKeyword_44 = (Keyword)cAlternatives.eContents().get(44);
		private final Keyword cRETAINKeyword_45 = (Keyword)cAlternatives.eContents().get(45);
		private final Keyword cSINGLEKeyword_46 = (Keyword)cAlternatives.eContents().get(46);
		private final Keyword cSTEPKeyword_47 = (Keyword)cAlternatives.eContents().get(47);
		private final Keyword cSTRUCTKeyword_48 = (Keyword)cAlternatives.eContents().get(48);
		private final Keyword cSUPERKeyword_49 = (Keyword)cAlternatives.eContents().get(49);
		private final Keyword cTASKKeyword_50 = (Keyword)cAlternatives.eContents().get(50);
		private final Keyword cTHISKeyword_51 = (Keyword)cAlternatives.eContents().get(51);
		private final Keyword cTRANSITIONKeyword_52 = (Keyword)cAlternatives.eContents().get(52);
		private final Keyword cTYPEKeyword_53 = (Keyword)cAlternatives.eContents().get(53);
		private final Keyword cUSINGKeyword_54 = (Keyword)cAlternatives.eContents().get(54);
		private final Keyword cVAR_ACCESSKeyword_55 = (Keyword)cAlternatives.eContents().get(55);
		private final Keyword cVAR_CONFIGKeyword_56 = (Keyword)cAlternatives.eContents().get(56);
		private final Keyword cVAR_EXTERNALKeyword_57 = (Keyword)cAlternatives.eContents().get(57);
		private final Keyword cVAR_GLOBALKeyword_58 = (Keyword)cAlternatives.eContents().get(58);
		private final Keyword cWITHKeyword_59 = (Keyword)cAlternatives.eContents().get(59);
		
		///** Keep in sync with fordiac keywords */
		//RESERVED_KEYWORDS:
		//    'ABSTRACT' | 'ACTION' | 'CLASS' | 'CONFIGURATION' | 'END_ACTION' | 'END_CLASS' | 'END_CONFIGURATION' |
		//    'END_FUNCTION' | 'END_FUNCTION_BLOCK' | 'END_INTERFACE' | 'END_METHOD' | 'END_NAMESPACE' | 'END_PROGRAM' |
		//    'END_RESOURCE' | 'END_STEP' | 'END_STRUCT' | 'END_TRANSITION' | 'END_TYPE' | 'EXTENDS' | 'FINAL' | 'FROM' |
		//    'FUNCTION' | 'FUNCTION_BLOCK' | 'IMPLEMENTS' | 'INITIAL_STEP' | 'INTERFACE' | 'INTERAL' | 'INTERVAL' | 'METHOD' |
		//    'NAMESPACE' | 'NON_RETAIN' | 'NULL' | 'ON' | 'OVERLAP' | 'OVERRIDE' | 'PRIORITY' | 'PRIVATE' | 'PROGRAM' |
		//    'PROTECTED' | 'PUBLIC' | 'READ_ONLY' | 'READ_WRITE' | 'REF' | 'REF_TO' | 'RESOURCE' | 'RETAIN' | 'SINGLE' | 'STEP' |
		//    'STRUCT' | 'SUPER' | 'TASK' | 'THIS' | 'TRANSITION' | 'TYPE' |
		//    'USING' | 'VAR_ACCESS' | 'VAR_CONFIG' | 'VAR_EXTERNAL' | 'VAR_GLOBAL' | 'WITH';
		@Override public ParserRule getRule() { return rule; }
		
		//'ABSTRACT' | 'ACTION' | 'CLASS' | 'CONFIGURATION' | 'END_ACTION' | 'END_CLASS' | 'END_CONFIGURATION' |
		//'END_FUNCTION' | 'END_FUNCTION_BLOCK' | 'END_INTERFACE' | 'END_METHOD' | 'END_NAMESPACE' | 'END_PROGRAM' |
		//'END_RESOURCE' | 'END_STEP' | 'END_STRUCT' | 'END_TRANSITION' | 'END_TYPE' | 'EXTENDS' | 'FINAL' | 'FROM' |
		//'FUNCTION' | 'FUNCTION_BLOCK' | 'IMPLEMENTS' | 'INITIAL_STEP' | 'INTERFACE' | 'INTERAL' | 'INTERVAL' | 'METHOD' |
		//'NAMESPACE' | 'NON_RETAIN' | 'NULL' | 'ON' | 'OVERLAP' | 'OVERRIDE' | 'PRIORITY' | 'PRIVATE' | 'PROGRAM' |
		//'PROTECTED' | 'PUBLIC' | 'READ_ONLY' | 'READ_WRITE' | 'REF' | 'REF_TO' | 'RESOURCE' | 'RETAIN' | 'SINGLE' | 'STEP' |
		//'STRUCT' | 'SUPER' | 'TASK' | 'THIS' | 'TRANSITION' | 'TYPE' |
		//'USING' | 'VAR_ACCESS' | 'VAR_CONFIG' | 'VAR_EXTERNAL' | 'VAR_GLOBAL' | 'WITH'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'ABSTRACT'
		public Keyword getABSTRACTKeyword_0() { return cABSTRACTKeyword_0; }
		
		//'ACTION'
		public Keyword getACTIONKeyword_1() { return cACTIONKeyword_1; }
		
		//'CLASS'
		public Keyword getCLASSKeyword_2() { return cCLASSKeyword_2; }
		
		//'CONFIGURATION'
		public Keyword getCONFIGURATIONKeyword_3() { return cCONFIGURATIONKeyword_3; }
		
		//'END_ACTION'
		public Keyword getEND_ACTIONKeyword_4() { return cEND_ACTIONKeyword_4; }
		
		//'END_CLASS'
		public Keyword getEND_CLASSKeyword_5() { return cEND_CLASSKeyword_5; }
		
		//'END_CONFIGURATION'
		public Keyword getEND_CONFIGURATIONKeyword_6() { return cEND_CONFIGURATIONKeyword_6; }
		
		//'END_FUNCTION'
		public Keyword getEND_FUNCTIONKeyword_7() { return cEND_FUNCTIONKeyword_7; }
		
		//'END_FUNCTION_BLOCK'
		public Keyword getEND_FUNCTION_BLOCKKeyword_8() { return cEND_FUNCTION_BLOCKKeyword_8; }
		
		//'END_INTERFACE'
		public Keyword getEND_INTERFACEKeyword_9() { return cEND_INTERFACEKeyword_9; }
		
		//'END_METHOD'
		public Keyword getEND_METHODKeyword_10() { return cEND_METHODKeyword_10; }
		
		//'END_NAMESPACE'
		public Keyword getEND_NAMESPACEKeyword_11() { return cEND_NAMESPACEKeyword_11; }
		
		//'END_PROGRAM'
		public Keyword getEND_PROGRAMKeyword_12() { return cEND_PROGRAMKeyword_12; }
		
		//'END_RESOURCE'
		public Keyword getEND_RESOURCEKeyword_13() { return cEND_RESOURCEKeyword_13; }
		
		//'END_STEP'
		public Keyword getEND_STEPKeyword_14() { return cEND_STEPKeyword_14; }
		
		//'END_STRUCT'
		public Keyword getEND_STRUCTKeyword_15() { return cEND_STRUCTKeyword_15; }
		
		//'END_TRANSITION'
		public Keyword getEND_TRANSITIONKeyword_16() { return cEND_TRANSITIONKeyword_16; }
		
		//'END_TYPE'
		public Keyword getEND_TYPEKeyword_17() { return cEND_TYPEKeyword_17; }
		
		//'EXTENDS'
		public Keyword getEXTENDSKeyword_18() { return cEXTENDSKeyword_18; }
		
		//'FINAL'
		public Keyword getFINALKeyword_19() { return cFINALKeyword_19; }
		
		//'FROM'
		public Keyword getFROMKeyword_20() { return cFROMKeyword_20; }
		
		//'FUNCTION'
		public Keyword getFUNCTIONKeyword_21() { return cFUNCTIONKeyword_21; }
		
		//'FUNCTION_BLOCK'
		public Keyword getFUNCTION_BLOCKKeyword_22() { return cFUNCTION_BLOCKKeyword_22; }
		
		//'IMPLEMENTS'
		public Keyword getIMPLEMENTSKeyword_23() { return cIMPLEMENTSKeyword_23; }
		
		//'INITIAL_STEP'
		public Keyword getINITIAL_STEPKeyword_24() { return cINITIAL_STEPKeyword_24; }
		
		//'INTERFACE'
		public Keyword getINTERFACEKeyword_25() { return cINTERFACEKeyword_25; }
		
		//'INTERAL'
		public Keyword getINTERALKeyword_26() { return cINTERALKeyword_26; }
		
		//'INTERVAL'
		public Keyword getINTERVALKeyword_27() { return cINTERVALKeyword_27; }
		
		//'METHOD'
		public Keyword getMETHODKeyword_28() { return cMETHODKeyword_28; }
		
		//'NAMESPACE'
		public Keyword getNAMESPACEKeyword_29() { return cNAMESPACEKeyword_29; }
		
		//'NON_RETAIN'
		public Keyword getNON_RETAINKeyword_30() { return cNON_RETAINKeyword_30; }
		
		//'NULL'
		public Keyword getNULLKeyword_31() { return cNULLKeyword_31; }
		
		//'ON'
		public Keyword getONKeyword_32() { return cONKeyword_32; }
		
		//'OVERLAP'
		public Keyword getOVERLAPKeyword_33() { return cOVERLAPKeyword_33; }
		
		//'OVERRIDE'
		public Keyword getOVERRIDEKeyword_34() { return cOVERRIDEKeyword_34; }
		
		//'PRIORITY'
		public Keyword getPRIORITYKeyword_35() { return cPRIORITYKeyword_35; }
		
		//'PRIVATE'
		public Keyword getPRIVATEKeyword_36() { return cPRIVATEKeyword_36; }
		
		//'PROGRAM'
		public Keyword getPROGRAMKeyword_37() { return cPROGRAMKeyword_37; }
		
		//'PROTECTED'
		public Keyword getPROTECTEDKeyword_38() { return cPROTECTEDKeyword_38; }
		
		//'PUBLIC'
		public Keyword getPUBLICKeyword_39() { return cPUBLICKeyword_39; }
		
		//'READ_ONLY'
		public Keyword getREAD_ONLYKeyword_40() { return cREAD_ONLYKeyword_40; }
		
		//'READ_WRITE'
		public Keyword getREAD_WRITEKeyword_41() { return cREAD_WRITEKeyword_41; }
		
		//'REF'
		public Keyword getREFKeyword_42() { return cREFKeyword_42; }
		
		//'REF_TO'
		public Keyword getREF_TOKeyword_43() { return cREF_TOKeyword_43; }
		
		//'RESOURCE'
		public Keyword getRESOURCEKeyword_44() { return cRESOURCEKeyword_44; }
		
		//'RETAIN'
		public Keyword getRETAINKeyword_45() { return cRETAINKeyword_45; }
		
		//'SINGLE'
		public Keyword getSINGLEKeyword_46() { return cSINGLEKeyword_46; }
		
		//'STEP'
		public Keyword getSTEPKeyword_47() { return cSTEPKeyword_47; }
		
		//'STRUCT'
		public Keyword getSTRUCTKeyword_48() { return cSTRUCTKeyword_48; }
		
		//'SUPER'
		public Keyword getSUPERKeyword_49() { return cSUPERKeyword_49; }
		
		//'TASK'
		public Keyword getTASKKeyword_50() { return cTASKKeyword_50; }
		
		//'THIS'
		public Keyword getTHISKeyword_51() { return cTHISKeyword_51; }
		
		//'TRANSITION'
		public Keyword getTRANSITIONKeyword_52() { return cTRANSITIONKeyword_52; }
		
		//'TYPE'
		public Keyword getTYPEKeyword_53() { return cTYPEKeyword_53; }
		
		//'USING'
		public Keyword getUSINGKeyword_54() { return cUSINGKeyword_54; }
		
		//'VAR_ACCESS'
		public Keyword getVAR_ACCESSKeyword_55() { return cVAR_ACCESSKeyword_55; }
		
		//'VAR_CONFIG'
		public Keyword getVAR_CONFIGKeyword_56() { return cVAR_CONFIGKeyword_56; }
		
		//'VAR_EXTERNAL'
		public Keyword getVAR_EXTERNALKeyword_57() { return cVAR_EXTERNALKeyword_57; }
		
		//'VAR_GLOBAL'
		public Keyword getVAR_GLOBALKeyword_58() { return cVAR_GLOBALKeyword_58; }
		
		//'WITH'
		public Keyword getWITHKeyword_59() { return cWITHKeyword_59; }
	}
	
	public class SubrangeOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.SubrangeOperator");
		private final EnumLiteralDeclaration cRangeEnumLiteralDeclaration = (EnumLiteralDeclaration)rule.eContents().get(1);
		private final Keyword cRangeFullStopFullStopKeyword_0 = (Keyword)cRangeEnumLiteralDeclaration.eContents().get(0);
		
		//enum SubrangeOperator returns STBinaryOperator:
		//    Range='..';
		public EnumRule getRule() { return rule; }
		
		//Range='..'
		public EnumLiteralDeclaration getRangeEnumLiteralDeclaration() { return cRangeEnumLiteralDeclaration; }
		
		//'..'
		public Keyword getRangeFullStopFullStopKeyword_0() { return cRangeFullStopFullStopKeyword_0; }
	}
	public class OrOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.OrOperator");
		private final EnumLiteralDeclaration cOREnumLiteralDeclaration = (EnumLiteralDeclaration)rule.eContents().get(1);
		private final Keyword cORORKeyword_0 = (Keyword)cOREnumLiteralDeclaration.eContents().get(0);
		
		//enum OrOperator returns STBinaryOperator:
		//    OR;
		public EnumRule getRule() { return rule; }
		
		//OR
		public EnumLiteralDeclaration getOREnumLiteralDeclaration() { return cOREnumLiteralDeclaration; }
		
		public Keyword getORORKeyword_0() { return cORORKeyword_0; }
	}
	public class XorOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.XorOperator");
		private final EnumLiteralDeclaration cXOREnumLiteralDeclaration = (EnumLiteralDeclaration)rule.eContents().get(1);
		private final Keyword cXORXORKeyword_0 = (Keyword)cXOREnumLiteralDeclaration.eContents().get(0);
		
		//enum XorOperator returns STBinaryOperator:
		//    XOR;
		public EnumRule getRule() { return rule; }
		
		//XOR
		public EnumLiteralDeclaration getXOREnumLiteralDeclaration() { return cXOREnumLiteralDeclaration; }
		
		public Keyword getXORXORKeyword_0() { return cXORXORKeyword_0; }
	}
	public class AndOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.AndOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cANDEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cANDANDKeyword_0_0 = (Keyword)cANDEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cAMPERSANDEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cAMPERSANDAmpersandKeyword_1_0 = (Keyword)cAMPERSANDEnumLiteralDeclaration_1.eContents().get(0);
		
		//enum AndOperator returns STBinaryOperator:
		//    AND | AMPERSAND='&';
		public EnumRule getRule() { return rule; }
		
		//AND | AMPERSAND='&'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//AND
		public EnumLiteralDeclaration getANDEnumLiteralDeclaration_0() { return cANDEnumLiteralDeclaration_0; }
		
		public Keyword getANDANDKeyword_0_0() { return cANDANDKeyword_0_0; }
		
		//AMPERSAND='&'
		public EnumLiteralDeclaration getAMPERSANDEnumLiteralDeclaration_1() { return cAMPERSANDEnumLiteralDeclaration_1; }
		
		//'&'
		public Keyword getAMPERSANDAmpersandKeyword_1_0() { return cAMPERSANDAmpersandKeyword_1_0; }
	}
	public class EqualityOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.EqualityOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cEQEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cEQEqualsSignKeyword_0_0 = (Keyword)cEQEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cNEEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cNELessThanSignGreaterThanSignKeyword_1_0 = (Keyword)cNEEnumLiteralDeclaration_1.eContents().get(0);
		
		//enum EqualityOperator returns STBinaryOperator:
		//    EQ='=' | NE='<>';
		public EnumRule getRule() { return rule; }
		
		//EQ='=' | NE='<>'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//EQ='='
		public EnumLiteralDeclaration getEQEnumLiteralDeclaration_0() { return cEQEnumLiteralDeclaration_0; }
		
		//'='
		public Keyword getEQEqualsSignKeyword_0_0() { return cEQEqualsSignKeyword_0_0; }
		
		//NE='<>'
		public EnumLiteralDeclaration getNEEnumLiteralDeclaration_1() { return cNEEnumLiteralDeclaration_1; }
		
		//'<>'
		public Keyword getNELessThanSignGreaterThanSignKeyword_1_0() { return cNELessThanSignGreaterThanSignKeyword_1_0; }
	}
	public class CompareOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.CompareOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cLTEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cLTLessThanSignKeyword_0_0 = (Keyword)cLTEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cLEEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cLELessThanSignEqualsSignKeyword_1_0 = (Keyword)cLEEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cGTEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cGTGreaterThanSignKeyword_2_0 = (Keyword)cGTEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cGEEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cGEGreaterThanSignEqualsSignKeyword_3_0 = (Keyword)cGEEnumLiteralDeclaration_3.eContents().get(0);
		
		//enum CompareOperator returns STBinaryOperator:
		//    LT='<' | LE='<=' | GT='>' | GE='>=';
		public EnumRule getRule() { return rule; }
		
		//LT='<' | LE='<=' | GT='>' | GE='>='
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//LT='<'
		public EnumLiteralDeclaration getLTEnumLiteralDeclaration_0() { return cLTEnumLiteralDeclaration_0; }
		
		//'<'
		public Keyword getLTLessThanSignKeyword_0_0() { return cLTLessThanSignKeyword_0_0; }
		
		//LE='<='
		public EnumLiteralDeclaration getLEEnumLiteralDeclaration_1() { return cLEEnumLiteralDeclaration_1; }
		
		//'<='
		public Keyword getLELessThanSignEqualsSignKeyword_1_0() { return cLELessThanSignEqualsSignKeyword_1_0; }
		
		//GT='>'
		public EnumLiteralDeclaration getGTEnumLiteralDeclaration_2() { return cGTEnumLiteralDeclaration_2; }
		
		//'>'
		public Keyword getGTGreaterThanSignKeyword_2_0() { return cGTGreaterThanSignKeyword_2_0; }
		
		//GE='>='
		public EnumLiteralDeclaration getGEEnumLiteralDeclaration_3() { return cGEEnumLiteralDeclaration_3; }
		
		//'>='
		public Keyword getGEGreaterThanSignEqualsSignKeyword_3_0() { return cGEGreaterThanSignEqualsSignKeyword_3_0; }
	}
	public class AddSubOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.AddSubOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cADDEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cADDPlusSignKeyword_0_0 = (Keyword)cADDEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cSUBEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cSUBHyphenMinusKeyword_1_0 = (Keyword)cSUBEnumLiteralDeclaration_1.eContents().get(0);
		
		//enum AddSubOperator returns STBinaryOperator:
		//    ADD='+' | SUB='-';
		public EnumRule getRule() { return rule; }
		
		//ADD='+' | SUB='-'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ADD='+'
		public EnumLiteralDeclaration getADDEnumLiteralDeclaration_0() { return cADDEnumLiteralDeclaration_0; }
		
		//'+'
		public Keyword getADDPlusSignKeyword_0_0() { return cADDPlusSignKeyword_0_0; }
		
		//SUB='-'
		public EnumLiteralDeclaration getSUBEnumLiteralDeclaration_1() { return cSUBEnumLiteralDeclaration_1; }
		
		//'-'
		public Keyword getSUBHyphenMinusKeyword_1_0() { return cSUBHyphenMinusKeyword_1_0; }
	}
	public class MulDivModOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.MulDivModOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cMULEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cMULAsteriskKeyword_0_0 = (Keyword)cMULEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cDIVEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cDIVSolidusKeyword_1_0 = (Keyword)cDIVEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cMODEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cMODMODKeyword_2_0 = (Keyword)cMODEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum MulDivModOperator returns STBinaryOperator:
		//    MUL='*' | DIV='/' | MOD;
		public EnumRule getRule() { return rule; }
		
		//MUL='*' | DIV='/' | MOD
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//MUL='*'
		public EnumLiteralDeclaration getMULEnumLiteralDeclaration_0() { return cMULEnumLiteralDeclaration_0; }
		
		//'*'
		public Keyword getMULAsteriskKeyword_0_0() { return cMULAsteriskKeyword_0_0; }
		
		//DIV='/'
		public EnumLiteralDeclaration getDIVEnumLiteralDeclaration_1() { return cDIVEnumLiteralDeclaration_1; }
		
		//'/'
		public Keyword getDIVSolidusKeyword_1_0() { return cDIVSolidusKeyword_1_0; }
		
		//MOD
		public EnumLiteralDeclaration getMODEnumLiteralDeclaration_2() { return cMODEnumLiteralDeclaration_2; }
		
		public Keyword getMODMODKeyword_2_0() { return cMODMODKeyword_2_0; }
	}
	public class PowerOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.PowerOperator");
		private final EnumLiteralDeclaration cPOWEREnumLiteralDeclaration = (EnumLiteralDeclaration)rule.eContents().get(1);
		private final Keyword cPOWERAsteriskAsteriskKeyword_0 = (Keyword)cPOWEREnumLiteralDeclaration.eContents().get(0);
		
		//enum PowerOperator returns STBinaryOperator:
		//    POWER='**';
		public EnumRule getRule() { return rule; }
		
		//POWER='**'
		public EnumLiteralDeclaration getPOWEREnumLiteralDeclaration() { return cPOWEREnumLiteralDeclaration; }
		
		//'**'
		public Keyword getPOWERAsteriskAsteriskKeyword_0() { return cPOWERAsteriskAsteriskKeyword_0; }
	}
	public class UnaryOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.UnaryOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cMINUSEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cMINUSHyphenMinusKeyword_0_0 = (Keyword)cMINUSEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cPLUSEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cPLUSPlusSignKeyword_1_0 = (Keyword)cPLUSEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cNOTEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cNOTNOTKeyword_2_0 = (Keyword)cNOTEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum UnaryOperator returns STUnaryOperator:
		//    MINUS='-' | PLUS='+' | NOT;
		public EnumRule getRule() { return rule; }
		
		//MINUS='-' | PLUS='+' | NOT
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//MINUS='-'
		public EnumLiteralDeclaration getMINUSEnumLiteralDeclaration_0() { return cMINUSEnumLiteralDeclaration_0; }
		
		//'-'
		public Keyword getMINUSHyphenMinusKeyword_0_0() { return cMINUSHyphenMinusKeyword_0_0; }
		
		//PLUS='+'
		public EnumLiteralDeclaration getPLUSEnumLiteralDeclaration_1() { return cPLUSEnumLiteralDeclaration_1; }
		
		//'+'
		public Keyword getPLUSPlusSignKeyword_1_0() { return cPLUSPlusSignKeyword_1_0; }
		
		//NOT
		public EnumLiteralDeclaration getNOTEnumLiteralDeclaration_2() { return cNOTEnumLiteralDeclaration_2; }
		
		public Keyword getNOTNOTKeyword_2_0() { return cNOTNOTKeyword_2_0; }
	}
	public class STBuiltinFeatureElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STBuiltinFeature");
		private final EnumLiteralDeclaration cTHISEnumLiteralDeclaration = (EnumLiteralDeclaration)rule.eContents().get(1);
		private final Keyword cTHISTHISKeyword_0 = (Keyword)cTHISEnumLiteralDeclaration.eContents().get(0);
		
		//enum STBuiltinFeature:
		//    THIS;
		public EnumRule getRule() { return rule; }
		
		//THIS
		public EnumLiteralDeclaration getTHISEnumLiteralDeclaration() { return cTHISEnumLiteralDeclaration; }
		
		public Keyword getTHISTHISKeyword_0() { return cTHISTHISKeyword_0; }
	}
	public class STMultiBitAccessSpecifierElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STMultiBitAccessSpecifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cLEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cLLKeyword_0_0 = (Keyword)cLEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cDEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cDDKeyword_1_0 = (Keyword)cDEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cWEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cWWKeyword_2_0 = (Keyword)cWEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cBEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cBBKeyword_3_0 = (Keyword)cBEnumLiteralDeclaration_3.eContents().get(0);
		private final EnumLiteralDeclaration cXEnumLiteralDeclaration_4 = (EnumLiteralDeclaration)cAlternatives.eContents().get(4);
		private final Keyword cXXKeyword_4_0 = (Keyword)cXEnumLiteralDeclaration_4.eContents().get(0);
		
		//enum STMultiBitAccessSpecifier:
		//    L='%L' | D='%D' | W='%W' | B='%B' | X='%X';
		public EnumRule getRule() { return rule; }
		
		//L='%L' | D='%D' | W='%W' | B='%B' | X='%X'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//L='%L'
		public EnumLiteralDeclaration getLEnumLiteralDeclaration_0() { return cLEnumLiteralDeclaration_0; }
		
		//'%L'
		public Keyword getLLKeyword_0_0() { return cLLKeyword_0_0; }
		
		//D='%D'
		public EnumLiteralDeclaration getDEnumLiteralDeclaration_1() { return cDEnumLiteralDeclaration_1; }
		
		//'%D'
		public Keyword getDDKeyword_1_0() { return cDDKeyword_1_0; }
		
		//W='%W'
		public EnumLiteralDeclaration getWEnumLiteralDeclaration_2() { return cWEnumLiteralDeclaration_2; }
		
		//'%W'
		public Keyword getWWKeyword_2_0() { return cWWKeyword_2_0; }
		
		//B='%B'
		public EnumLiteralDeclaration getBEnumLiteralDeclaration_3() { return cBEnumLiteralDeclaration_3; }
		
		//'%B'
		public Keyword getBBKeyword_3_0() { return cBBKeyword_3_0; }
		
		//X='%X'
		public EnumLiteralDeclaration getXEnumLiteralDeclaration_4() { return cXEnumLiteralDeclaration_4; }
		
		//'%X'
		public Keyword getXXKeyword_4_0() { return cXXKeyword_4_0; }
	}
	public class STAccessSpecifierElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STAccessSpecifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cPROTECTEDEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cPROTECTEDPROTECTEDKeyword_0_0 = (Keyword)cPROTECTEDEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cPUBLICEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cPUBLICPUBLICKeyword_1_0 = (Keyword)cPUBLICEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cPRIVATEEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cPRIVATEPRIVATEKeyword_2_0 = (Keyword)cPRIVATEEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cINTERNALEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cINTERNALINTERNALKeyword_3_0 = (Keyword)cINTERNALEnumLiteralDeclaration_3.eContents().get(0);
		
		//enum STAccessSpecifier:
		//    PROTECTED='PROTECTED' | PUBLIC='PUBLIC' | PRIVATE='PRIVATE' | INTERNAL='INTERNAL';
		public EnumRule getRule() { return rule; }
		
		//PROTECTED='PROTECTED' | PUBLIC='PUBLIC' | PRIVATE='PRIVATE' | INTERNAL='INTERNAL'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//PROTECTED='PROTECTED'
		public EnumLiteralDeclaration getPROTECTEDEnumLiteralDeclaration_0() { return cPROTECTEDEnumLiteralDeclaration_0; }
		
		//'PROTECTED'
		public Keyword getPROTECTEDPROTECTEDKeyword_0_0() { return cPROTECTEDPROTECTEDKeyword_0_0; }
		
		//PUBLIC='PUBLIC'
		public EnumLiteralDeclaration getPUBLICEnumLiteralDeclaration_1() { return cPUBLICEnumLiteralDeclaration_1; }
		
		//'PUBLIC'
		public Keyword getPUBLICPUBLICKeyword_1_0() { return cPUBLICPUBLICKeyword_1_0; }
		
		//PRIVATE='PRIVATE'
		public EnumLiteralDeclaration getPRIVATEEnumLiteralDeclaration_2() { return cPRIVATEEnumLiteralDeclaration_2; }
		
		//'PRIVATE'
		public Keyword getPRIVATEPRIVATEKeyword_2_0() { return cPRIVATEPRIVATEKeyword_2_0; }
		
		//INTERNAL='INTERNAL'
		public EnumLiteralDeclaration getINTERNALEnumLiteralDeclaration_3() { return cINTERNALEnumLiteralDeclaration_3; }
		
		//'INTERNAL'
		public Keyword getINTERNALINTERNALKeyword_3_0() { return cINTERNALINTERNALKeyword_3_0; }
	}
	
	private final STCoreSourceElements pSTCoreSource;
	private final STExpressionSourceElements pSTExpressionSource;
	private final STExpressionSource0Elements pSTExpressionSource0;
	private final STInitializerExpressionSourceElements pSTInitializerExpressionSource;
	private final STInitializerExpressionSource0Elements pSTInitializerExpressionSource0;
	private final STImportElements pSTImport;
	private final STVarDeclarationBlockElements pSTVarDeclarationBlock;
	private final STVarTempDeclarationBlockElements pSTVarTempDeclarationBlock;
	private final STVarInputDeclarationBlockElements pSTVarInputDeclarationBlock;
	private final STVarOutputDeclarationBlockElements pSTVarOutputDeclarationBlock;
	private final STVarInOutDeclarationBlockElements pSTVarInOutDeclarationBlock;
	private final STVarDeclarationElements pSTVarDeclaration;
	private final STTypeDeclarationElements pSTTypeDeclaration;
	private final STTypeDeclaration0Elements pSTTypeDeclaration0;
	private final STInitializerExpressionElements pSTInitializerExpression;
	private final STElementaryInitializerExpressionElements pSTElementaryInitializerExpression;
	private final STArrayInitializerExpressionElements pSTArrayInitializerExpression;
	private final STArrayInitElementElements pSTArrayInitElement;
	private final STSingleArrayInitElementElements pSTSingleArrayInitElement;
	private final STRepeatArrayInitElementElements pSTRepeatArrayInitElement;
	private final STStructInitializerExpressionElements pSTStructInitializerExpression;
	private final STStructInitElementElements pSTStructInitElement;
	private final STPragmaElements pSTPragma;
	private final STAttributeElements pSTAttribute;
	private final STAttributeNameElements pSTAttributeName;
	private final STStatementElements pSTStatement;
	private final STAssignmentElements pSTAssignment;
	private final STCallArgumentElements pSTCallArgument;
	private final STCallUnnamedArgumentElements pSTCallUnnamedArgument;
	private final STCallNamedInputArgumentElements pSTCallNamedInputArgument;
	private final STCallNamedOutputArgumentElements pSTCallNamedOutputArgument;
	private final STIfStatementElements pSTIfStatement;
	private final STElseIfPartElements pSTElseIfPart;
	private final STCaseStatementElements pSTCaseStatement;
	private final STCaseCasesElements pSTCaseCases;
	private final STElsePartElements pSTElsePart;
	private final STForStatementElements pSTForStatement;
	private final STWhileStatementElements pSTWhileStatement;
	private final STRepeatStatementElements pSTRepeatStatement;
	private final STExpressionElements pSTExpression;
	private final SubrangeOperatorElements eSubrangeOperator;
	private final STSubrangeExpressionElements pSTSubrangeExpression;
	private final OrOperatorElements eOrOperator;
	private final STOrExpressionElements pSTOrExpression;
	private final XorOperatorElements eXorOperator;
	private final STXorExpressionElements pSTXorExpression;
	private final AndOperatorElements eAndOperator;
	private final STAndExpressionElements pSTAndExpression;
	private final EqualityOperatorElements eEqualityOperator;
	private final STEqualityExpressionElements pSTEqualityExpression;
	private final CompareOperatorElements eCompareOperator;
	private final STComparisonExpressionElements pSTComparisonExpression;
	private final AddSubOperatorElements eAddSubOperator;
	private final STAddSubExpressionElements pSTAddSubExpression;
	private final MulDivModOperatorElements eMulDivModOperator;
	private final STMulDivModExpressionElements pSTMulDivModExpression;
	private final PowerOperatorElements ePowerOperator;
	private final STPowerExpressionElements pSTPowerExpression;
	private final UnaryOperatorElements eUnaryOperator;
	private final STUnaryExpressionElements pSTUnaryExpression;
	private final STAccessExpressionElements pSTAccessExpression;
	private final STPrimaryExpressionElements pSTPrimaryExpression;
	private final STFeatureExpressionElements pSTFeatureExpression;
	private final STFeatureNameElements pSTFeatureName;
	private final STBuiltinFeatureExpressionElements pSTBuiltinFeatureExpression;
	private final STBuiltinFeatureElements eSTBuiltinFeature;
	private final STMultiBitAccessSpecifierElements eSTMultiBitAccessSpecifier;
	private final STMultibitPartialExpressionElements pSTMultibitPartialExpression;
	private final STLiteralExpressionsElements pSTLiteralExpressions;
	private final STSignedLiteralExpressionsElements pSTSignedLiteralExpressions;
	private final STNumericLiteralTypeElements pSTNumericLiteralType;
	private final STNumericLiteralElements pSTNumericLiteral;
	private final STSignedNumericLiteralElements pSTSignedNumericLiteral;
	private final STDateLiteralTypeElements pSTDateLiteralType;
	private final STDateLiteralElements pSTDateLiteral;
	private final STTimeLiteralTypeElements pSTTimeLiteralType;
	private final STTimeLiteralElements pSTTimeLiteral;
	private final STTimeOfDayLiteralElements pSTTimeOfDayLiteral;
	private final STDateAndTimeLiteralElements pSTDateAndTimeLiteral;
	private final STStringLiteralElements pSTStringLiteral;
	private final STEnumLiteralElements pSTEnumLiteral;
	private final EnumValueElements pEnumValue;
	private final STAnyTypeElements pSTAnyType;
	private final STAnyBuiltinTypeElements pSTAnyBuiltinType;
	private final STAnyBitTypeElements pSTAnyBitType;
	private final STAnyNumTypeElements pSTAnyNumType;
	private final STAnyDurationTypeElements pSTAnyDurationType;
	private final STAnyDateTypeElements pSTAnyDateType;
	private final STDateTypeElements pSTDateType;
	private final STTimeOfDayTypeElements pSTTimeOfDayType;
	private final STDateAndTimeTypeElements pSTDateAndTimeType;
	private final STAnyCharsTypeElements pSTAnyCharsType;
	private final QualifiedNameElements pQualifiedName;
	private final QualifiedNameWithWildcardElements pQualifiedNameWithWildcard;
	private final NumericElements pNumeric;
	private final NumberElements pNumber;
	private final SignedNumericElements pSignedNumeric;
	private final SignedNumberElements pSignedNumber;
	private final TimeElements pTime;
	private final DateElements pDate;
	private final DateAndTimeElements pDateAndTime;
	private final TimeOfDayElements pTimeOfDay;
	private final STAccessSpecifierElements eSTAccessSpecifier;
	private final RESERVED_KEYWORDSElements pRESERVED_KEYWORDS;
	private final TerminalRule tHEX_DIGIT;
	private final TerminalRule tNON_DECIMAL;
	private final TerminalRule tINT;
	private final TerminalRule tDECIMAL;
	private final TerminalRule tTIME_VALUE;
	private final TerminalRule tTIME_PART;
	private final TerminalRule tTIME_DAYS;
	private final TerminalRule tTIME_HOURS;
	private final TerminalRule tTIME_MINUTES;
	private final TerminalRule tTIME_SECONDS;
	private final TerminalRule tTIME_MILLIS;
	private final TerminalRule tTIME_MICROS;
	private final TerminalRule tTIME_NANOS;
	private final TerminalRule tID;
	private final TerminalRule tSTRING;
	private final TerminalRule tML_COMMENT;
	private final TerminalRule tSL_COMMENT;
	private final TerminalRule tWS;
	private final TerminalRule tANY_OTHER;
	
	private final Grammar grammar;

	@Inject
	public STCoreGrammarAccess(GrammarProvider grammarProvider) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.pSTCoreSource = new STCoreSourceElements();
		this.pSTExpressionSource = new STExpressionSourceElements();
		this.pSTExpressionSource0 = new STExpressionSource0Elements();
		this.pSTInitializerExpressionSource = new STInitializerExpressionSourceElements();
		this.pSTInitializerExpressionSource0 = new STInitializerExpressionSource0Elements();
		this.pSTImport = new STImportElements();
		this.pSTVarDeclarationBlock = new STVarDeclarationBlockElements();
		this.pSTVarTempDeclarationBlock = new STVarTempDeclarationBlockElements();
		this.pSTVarInputDeclarationBlock = new STVarInputDeclarationBlockElements();
		this.pSTVarOutputDeclarationBlock = new STVarOutputDeclarationBlockElements();
		this.pSTVarInOutDeclarationBlock = new STVarInOutDeclarationBlockElements();
		this.pSTVarDeclaration = new STVarDeclarationElements();
		this.pSTTypeDeclaration = new STTypeDeclarationElements();
		this.pSTTypeDeclaration0 = new STTypeDeclaration0Elements();
		this.pSTInitializerExpression = new STInitializerExpressionElements();
		this.pSTElementaryInitializerExpression = new STElementaryInitializerExpressionElements();
		this.pSTArrayInitializerExpression = new STArrayInitializerExpressionElements();
		this.pSTArrayInitElement = new STArrayInitElementElements();
		this.pSTSingleArrayInitElement = new STSingleArrayInitElementElements();
		this.pSTRepeatArrayInitElement = new STRepeatArrayInitElementElements();
		this.pSTStructInitializerExpression = new STStructInitializerExpressionElements();
		this.pSTStructInitElement = new STStructInitElementElements();
		this.pSTPragma = new STPragmaElements();
		this.pSTAttribute = new STAttributeElements();
		this.pSTAttributeName = new STAttributeNameElements();
		this.pSTStatement = new STStatementElements();
		this.pSTAssignment = new STAssignmentElements();
		this.pSTCallArgument = new STCallArgumentElements();
		this.pSTCallUnnamedArgument = new STCallUnnamedArgumentElements();
		this.pSTCallNamedInputArgument = new STCallNamedInputArgumentElements();
		this.pSTCallNamedOutputArgument = new STCallNamedOutputArgumentElements();
		this.pSTIfStatement = new STIfStatementElements();
		this.pSTElseIfPart = new STElseIfPartElements();
		this.pSTCaseStatement = new STCaseStatementElements();
		this.pSTCaseCases = new STCaseCasesElements();
		this.pSTElsePart = new STElsePartElements();
		this.pSTForStatement = new STForStatementElements();
		this.pSTWhileStatement = new STWhileStatementElements();
		this.pSTRepeatStatement = new STRepeatStatementElements();
		this.pSTExpression = new STExpressionElements();
		this.eSubrangeOperator = new SubrangeOperatorElements();
		this.pSTSubrangeExpression = new STSubrangeExpressionElements();
		this.eOrOperator = new OrOperatorElements();
		this.pSTOrExpression = new STOrExpressionElements();
		this.eXorOperator = new XorOperatorElements();
		this.pSTXorExpression = new STXorExpressionElements();
		this.eAndOperator = new AndOperatorElements();
		this.pSTAndExpression = new STAndExpressionElements();
		this.eEqualityOperator = new EqualityOperatorElements();
		this.pSTEqualityExpression = new STEqualityExpressionElements();
		this.eCompareOperator = new CompareOperatorElements();
		this.pSTComparisonExpression = new STComparisonExpressionElements();
		this.eAddSubOperator = new AddSubOperatorElements();
		this.pSTAddSubExpression = new STAddSubExpressionElements();
		this.eMulDivModOperator = new MulDivModOperatorElements();
		this.pSTMulDivModExpression = new STMulDivModExpressionElements();
		this.ePowerOperator = new PowerOperatorElements();
		this.pSTPowerExpression = new STPowerExpressionElements();
		this.eUnaryOperator = new UnaryOperatorElements();
		this.pSTUnaryExpression = new STUnaryExpressionElements();
		this.pSTAccessExpression = new STAccessExpressionElements();
		this.pSTPrimaryExpression = new STPrimaryExpressionElements();
		this.pSTFeatureExpression = new STFeatureExpressionElements();
		this.pSTFeatureName = new STFeatureNameElements();
		this.pSTBuiltinFeatureExpression = new STBuiltinFeatureExpressionElements();
		this.eSTBuiltinFeature = new STBuiltinFeatureElements();
		this.eSTMultiBitAccessSpecifier = new STMultiBitAccessSpecifierElements();
		this.pSTMultibitPartialExpression = new STMultibitPartialExpressionElements();
		this.pSTLiteralExpressions = new STLiteralExpressionsElements();
		this.pSTSignedLiteralExpressions = new STSignedLiteralExpressionsElements();
		this.pSTNumericLiteralType = new STNumericLiteralTypeElements();
		this.pSTNumericLiteral = new STNumericLiteralElements();
		this.pSTSignedNumericLiteral = new STSignedNumericLiteralElements();
		this.pSTDateLiteralType = new STDateLiteralTypeElements();
		this.pSTDateLiteral = new STDateLiteralElements();
		this.pSTTimeLiteralType = new STTimeLiteralTypeElements();
		this.pSTTimeLiteral = new STTimeLiteralElements();
		this.pSTTimeOfDayLiteral = new STTimeOfDayLiteralElements();
		this.pSTDateAndTimeLiteral = new STDateAndTimeLiteralElements();
		this.pSTStringLiteral = new STStringLiteralElements();
		this.pSTEnumLiteral = new STEnumLiteralElements();
		this.pEnumValue = new EnumValueElements();
		this.pSTAnyType = new STAnyTypeElements();
		this.pSTAnyBuiltinType = new STAnyBuiltinTypeElements();
		this.pSTAnyBitType = new STAnyBitTypeElements();
		this.pSTAnyNumType = new STAnyNumTypeElements();
		this.pSTAnyDurationType = new STAnyDurationTypeElements();
		this.pSTAnyDateType = new STAnyDateTypeElements();
		this.pSTDateType = new STDateTypeElements();
		this.pSTTimeOfDayType = new STTimeOfDayTypeElements();
		this.pSTDateAndTimeType = new STDateAndTimeTypeElements();
		this.pSTAnyCharsType = new STAnyCharsTypeElements();
		this.pQualifiedName = new QualifiedNameElements();
		this.pQualifiedNameWithWildcard = new QualifiedNameWithWildcardElements();
		this.pNumeric = new NumericElements();
		this.pNumber = new NumberElements();
		this.pSignedNumeric = new SignedNumericElements();
		this.pSignedNumber = new SignedNumberElements();
		this.pTime = new TimeElements();
		this.pDate = new DateElements();
		this.pDateAndTime = new DateAndTimeElements();
		this.pTimeOfDay = new TimeOfDayElements();
		this.eSTAccessSpecifier = new STAccessSpecifierElements();
		this.pRESERVED_KEYWORDS = new RESERVED_KEYWORDSElements();
		this.tHEX_DIGIT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.HEX_DIGIT");
		this.tNON_DECIMAL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.NON_DECIMAL");
		this.tINT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.INT");
		this.tDECIMAL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.DECIMAL");
		this.tTIME_VALUE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_VALUE");
		this.tTIME_PART = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_PART");
		this.tTIME_DAYS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_DAYS");
		this.tTIME_HOURS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_HOURS");
		this.tTIME_MINUTES = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_MINUTES");
		this.tTIME_SECONDS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_SECONDS");
		this.tTIME_MILLIS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_MILLIS");
		this.tTIME_MICROS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_MICROS");
		this.tTIME_NANOS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.TIME_NANOS");
		this.tID = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.ID");
		this.tSTRING = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.STRING");
		this.tML_COMMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.ML_COMMENT");
		this.tSL_COMMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.SL_COMMENT");
		this.tWS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.WS");
		this.tANY_OTHER = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextcore.STCore.ANY_OTHER");
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.fordiac.ide.structuredtextcore.STCore".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	

	
	//STCoreSource returns STSource:
	//    {STCoreSource} statements+=STStatement*;
	public STCoreSourceElements getSTCoreSourceAccess() {
		return pSTCoreSource;
	}
	
	public ParserRule getSTCoreSourceRule() {
		return getSTCoreSourceAccess().getRule();
	}
	
	//STExpressionSource returns STSource:
	//    {STExpressionSource} expression=STExpression?;
	public STExpressionSourceElements getSTExpressionSourceAccess() {
		return pSTExpressionSource;
	}
	
	public ParserRule getSTExpressionSourceRule() {
		return getSTExpressionSourceAccess().getRule();
	}
	
	//STExpressionSource0 returns STSource:
	//    STExpressionSource;
	public STExpressionSource0Elements getSTExpressionSource0Access() {
		return pSTExpressionSource0;
	}
	
	public ParserRule getSTExpressionSource0Rule() {
		return getSTExpressionSource0Access().getRule();
	}
	
	// // necessary to keep Xtext from skipping this rule
	//STInitializerExpressionSource returns STSource:
	//    {STInitializerExpressionSource} initializerExpression=STInitializerExpression?;
	public STInitializerExpressionSourceElements getSTInitializerExpressionSourceAccess() {
		return pSTInitializerExpressionSource;
	}
	
	public ParserRule getSTInitializerExpressionSourceRule() {
		return getSTInitializerExpressionSourceAccess().getRule();
	}
	
	//STInitializerExpressionSource0 returns STSource:
	//    STInitializerExpressionSource;
	public STInitializerExpressionSource0Elements getSTInitializerExpressionSource0Access() {
		return pSTInitializerExpressionSource0;
	}
	
	public ParserRule getSTInitializerExpressionSource0Rule() {
		return getSTInitializerExpressionSource0Access().getRule();
	}
	
	// // necessary to keep Xtext from skipping this rule
	//STImport returns STImport:
	//    'IMPORT' importedNamespace=QualifiedNameWithWildcard ';';
	public STImportElements getSTImportAccess() {
		return pSTImport;
	}
	
	public ParserRule getSTImportRule() {
		return getSTImportAccess().getRule();
	}
	
	//STVarDeclarationBlock returns STVarPlainDeclarationBlock:
	//    {STVarPlainDeclarationBlock} 'VAR' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STVarDeclarationBlockElements getSTVarDeclarationBlockAccess() {
		return pSTVarDeclarationBlock;
	}
	
	public ParserRule getSTVarDeclarationBlockRule() {
		return getSTVarDeclarationBlockAccess().getRule();
	}
	
	//STVarTempDeclarationBlock returns STVarTempDeclarationBlock:
	//    {STVarTempDeclarationBlock} 'VAR_TEMP' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STVarTempDeclarationBlockElements getSTVarTempDeclarationBlockAccess() {
		return pSTVarTempDeclarationBlock;
	}
	
	public ParserRule getSTVarTempDeclarationBlockRule() {
		return getSTVarTempDeclarationBlockAccess().getRule();
	}
	
	//STVarInputDeclarationBlock returns STVarInputDeclarationBlock:
	//    {STVarInputDeclarationBlock} 'VAR_INPUT' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STVarInputDeclarationBlockElements getSTVarInputDeclarationBlockAccess() {
		return pSTVarInputDeclarationBlock;
	}
	
	public ParserRule getSTVarInputDeclarationBlockRule() {
		return getSTVarInputDeclarationBlockAccess().getRule();
	}
	
	//STVarOutputDeclarationBlock returns STVarOutputDeclarationBlock:
	//    {STVarOutputDeclarationBlock} 'VAR_OUTPUT' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STVarOutputDeclarationBlockElements getSTVarOutputDeclarationBlockAccess() {
		return pSTVarOutputDeclarationBlock;
	}
	
	public ParserRule getSTVarOutputDeclarationBlockRule() {
		return getSTVarOutputDeclarationBlockAccess().getRule();
	}
	
	//STVarInOutDeclarationBlock returns STVarInOutDeclarationBlock:
	//    {STVarInOutDeclarationBlock} 'VAR_IN_OUT' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STVarInOutDeclarationBlockElements getSTVarInOutDeclarationBlockAccess() {
		return pSTVarInOutDeclarationBlock;
	}
	
	public ParserRule getSTVarInOutDeclarationBlockRule() {
		return getSTVarInOutDeclarationBlockAccess().getRule();
	}
	
	//STVarDeclaration:
	//    {STVarDeclaration}
	//    name=ID ('AT' locatedAt=[libraryElement::INamedElement])? ':' (array?='ARRAY' (('[' ranges+=(STExpression) (','
	//    ranges+=STExpression)* ']') | ('[' count+='*' (',' count+='*')* ']')) 'OF')?
	//    (type=[libraryElement::INamedElement|STAnyType]) ('[' maxLength=STExpression ']')? (':='
	//    defaultValue=STInitializerExpression)? pragma=STPragma? ';';
	public STVarDeclarationElements getSTVarDeclarationAccess() {
		return pSTVarDeclaration;
	}
	
	public ParserRule getSTVarDeclarationRule() {
		return getSTVarDeclarationAccess().getRule();
	}
	
	//STTypeDeclaration:
	//    {STTypeDeclaration}
	//    (array?='ARRAY' (('[' ranges+=(STExpression) (',' ranges+=STExpression)* ']') |
	//    ('[' count+='*' (',' count+='*')* ']')) 'OF')?
	//    (type=[libraryElement::INamedElement|STAnyType])
	//    ('[' maxLength=STExpression ']')?;
	public STTypeDeclarationElements getSTTypeDeclarationAccess() {
		return pSTTypeDeclaration;
	}
	
	public ParserRule getSTTypeDeclarationRule() {
		return getSTTypeDeclarationAccess().getRule();
	}
	
	//STTypeDeclaration0 returns STTypeDeclaration:
	//    STTypeDeclaration;
	public STTypeDeclaration0Elements getSTTypeDeclaration0Access() {
		return pSTTypeDeclaration0;
	}
	
	public ParserRule getSTTypeDeclaration0Rule() {
		return getSTTypeDeclaration0Access().getRule();
	}
	
	// // necessary to keep Xtext from skipping this rule
	//STInitializerExpression:
	//    STElementaryInitializerExpression | STArrayInitializerExpression | STStructInitializerExpression;
	public STInitializerExpressionElements getSTInitializerExpressionAccess() {
		return pSTInitializerExpression;
	}
	
	public ParserRule getSTInitializerExpressionRule() {
		return getSTInitializerExpressionAccess().getRule();
	}
	
	//STElementaryInitializerExpression:
	//    value=STExpression;
	public STElementaryInitializerExpressionElements getSTElementaryInitializerExpressionAccess() {
		return pSTElementaryInitializerExpression;
	}
	
	public ParserRule getSTElementaryInitializerExpressionRule() {
		return getSTElementaryInitializerExpressionAccess().getRule();
	}
	
	//STArrayInitializerExpression:
	//    '[' values+=STArrayInitElement (',' values+=STArrayInitElement)* ']';
	public STArrayInitializerExpressionElements getSTArrayInitializerExpressionAccess() {
		return pSTArrayInitializerExpression;
	}
	
	public ParserRule getSTArrayInitializerExpressionRule() {
		return getSTArrayInitializerExpressionAccess().getRule();
	}
	
	//STArrayInitElement:
	//    STSingleArrayInitElement | STRepeatArrayInitElement;
	public STArrayInitElementElements getSTArrayInitElementAccess() {
		return pSTArrayInitElement;
	}
	
	public ParserRule getSTArrayInitElementRule() {
		return getSTArrayInitElementAccess().getRule();
	}
	
	//STSingleArrayInitElement:
	//    initExpression=STInitializerExpression;
	public STSingleArrayInitElementElements getSTSingleArrayInitElementAccess() {
		return pSTSingleArrayInitElement;
	}
	
	public ParserRule getSTSingleArrayInitElementRule() {
		return getSTSingleArrayInitElementAccess().getRule();
	}
	
	//STRepeatArrayInitElement:
	//    repetitions=INT '(' initExpressions+=STInitializerExpression (','
	//    initExpressions+=STInitializerExpression)* ')';
	public STRepeatArrayInitElementElements getSTRepeatArrayInitElementAccess() {
		return pSTRepeatArrayInitElement;
	}
	
	public ParserRule getSTRepeatArrayInitElementRule() {
		return getSTRepeatArrayInitElementAccess().getRule();
	}
	
	//STStructInitializerExpression:
	//    (type=[datatype::StructuredType|QualifiedName] '#')? '(' values+=STStructInitElement (','
	//    values+=STStructInitElement)* ')';
	public STStructInitializerExpressionElements getSTStructInitializerExpressionAccess() {
		return pSTStructInitializerExpression;
	}
	
	public ParserRule getSTStructInitializerExpressionRule() {
		return getSTStructInitializerExpressionAccess().getRule();
	}
	
	//STStructInitElement:
	//    variable=[libraryElement::INamedElement|STFeatureName] ':=' value=STInitializerExpression;
	public STStructInitElementElements getSTStructInitElementAccess() {
		return pSTStructInitElement;
	}
	
	public ParserRule getSTStructInitElementRule() {
		return getSTStructInitElementAccess().getRule();
	}
	
	//STPragma:
	//    {STPragma}
	//    '{' attributes+=STAttribute (',' attributes+=STAttribute)* '}';
	public STPragmaElements getSTPragmaAccess() {
		return pSTPragma;
	}
	
	public ParserRule getSTPragmaRule() {
		return getSTPragmaAccess().getRule();
	}
	
	//STAttribute:
	//    declaration=[libraryElement::AttributeDeclaration|STAttributeName] ':=' value=STInitializerExpression;
	public STAttributeElements getSTAttributeAccess() {
		return pSTAttribute;
	}
	
	public ParserRule getSTAttributeRule() {
		return getSTAttributeAccess().getRule();
	}
	
	//STAttributeName:
	//    QualifiedName;
	public STAttributeNameElements getSTAttributeNameAccess() {
		return pSTAttributeName;
	}
	
	public ParserRule getSTAttributeNameRule() {
		return getSTAttributeNameAccess().getRule();
	}
	
	//STStatement:
	//    (STIfStatement |
	//    STCaseStatement |
	//    STForStatement |
	//    STWhileStatement |
	//    STRepeatStatement |
	//    STAssignment |
	//    {STReturn} 'RETURN' |
	//    {STContinue} 'CONTINUE' |
	//    {STExit} 'EXIT') ';' |
	//    {STNop} ';';
	public STStatementElements getSTStatementAccess() {
		return pSTStatement;
	}
	
	public ParserRule getSTStatementRule() {
		return getSTStatementAccess().getRule();
	}
	
	//STAssignment returns STExpression:
	//    STExpression ({STAssignment.left=current} ':=' right=STAssignment)?;
	public STAssignmentElements getSTAssignmentAccess() {
		return pSTAssignment;
	}
	
	public ParserRule getSTAssignmentRule() {
		return getSTAssignmentAccess().getRule();
	}
	
	//STCallArgument:
	//    STCallUnnamedArgument | STCallNamedInputArgument | STCallNamedOutputArgument;
	public STCallArgumentElements getSTCallArgumentAccess() {
		return pSTCallArgument;
	}
	
	public ParserRule getSTCallArgumentRule() {
		return getSTCallArgumentAccess().getRule();
	}
	
	//STCallUnnamedArgument:
	//    argument=STExpression;
	public STCallUnnamedArgumentElements getSTCallUnnamedArgumentAccess() {
		return pSTCallUnnamedArgument;
	}
	
	public ParserRule getSTCallUnnamedArgumentRule() {
		return getSTCallUnnamedArgumentAccess().getRule();
	}
	
	//STCallNamedInputArgument:
	//    parameter=[libraryElement::INamedElement] ':=' argument=STExpression;
	public STCallNamedInputArgumentElements getSTCallNamedInputArgumentAccess() {
		return pSTCallNamedInputArgument;
	}
	
	public ParserRule getSTCallNamedInputArgumentRule() {
		return getSTCallNamedInputArgumentAccess().getRule();
	}
	
	//STCallNamedOutputArgument:
	//    not?='NOT'? parameter=[libraryElement::INamedElement] '=>' argument=STExpression;
	public STCallNamedOutputArgumentElements getSTCallNamedOutputArgumentAccess() {
		return pSTCallNamedOutputArgument;
	}
	
	public ParserRule getSTCallNamedOutputArgumentRule() {
		return getSTCallNamedOutputArgumentAccess().getRule();
	}
	
	//STIfStatement:
	//    'IF' condition=STExpression 'THEN' statements+=STStatement* elseifs+=(STElseIfPart)* (else=STElsePart)? 'END_IF';
	public STIfStatementElements getSTIfStatementAccess() {
		return pSTIfStatement;
	}
	
	public ParserRule getSTIfStatementRule() {
		return getSTIfStatementAccess().getRule();
	}
	
	//STElseIfPart:
	//    'ELSIF' condition=STExpression 'THEN' statements+=STStatement*;
	public STElseIfPartElements getSTElseIfPartAccess() {
		return pSTElseIfPart;
	}
	
	public ParserRule getSTElseIfPartRule() {
		return getSTElseIfPartAccess().getRule();
	}
	
	//STCaseStatement:
	//    'CASE' selector=STExpression 'OF' cases+=STCaseCases+ (else=STElsePart)? 'END_CASE';
	public STCaseStatementElements getSTCaseStatementAccess() {
		return pSTCaseStatement;
	}
	
	public ParserRule getSTCaseStatementRule() {
		return getSTCaseStatementAccess().getRule();
	}
	
	//STCaseCases:
	//    conditions+=STExpression (',' conditions+=STExpression)* ':' =>statements+=STStatement*;
	public STCaseCasesElements getSTCaseCasesAccess() {
		return pSTCaseCases;
	}
	
	public ParserRule getSTCaseCasesRule() {
		return getSTCaseCasesAccess().getRule();
	}
	
	//STElsePart:
	//    {STElsePart} 'ELSE' statements+=STStatement*;
	public STElsePartElements getSTElsePartAccess() {
		return pSTElsePart;
	}
	
	public ParserRule getSTElsePartRule() {
		return getSTElsePartAccess().getRule();
	}
	
	//STForStatement:
	//    'FOR' variable=STExpression ':=' from=STExpression 'TO' to=STExpression ('BY' by=STExpression)? 'DO'
	//    statements+=STStatement*
	//    'END_FOR';
	public STForStatementElements getSTForStatementAccess() {
		return pSTForStatement;
	}
	
	public ParserRule getSTForStatementRule() {
		return getSTForStatementAccess().getRule();
	}
	
	//STWhileStatement:
	//    'WHILE' condition=STExpression 'DO'
	//    statements+=STStatement*
	//    'END_WHILE';
	public STWhileStatementElements getSTWhileStatementAccess() {
		return pSTWhileStatement;
	}
	
	public ParserRule getSTWhileStatementRule() {
		return getSTWhileStatementAccess().getRule();
	}
	
	//STRepeatStatement:
	//    'REPEAT'
	//    statements+=STStatement*
	//    'UNTIL' condition=STExpression
	//    'END_REPEAT';
	public STRepeatStatementElements getSTRepeatStatementAccess() {
		return pSTRepeatStatement;
	}
	
	public ParserRule getSTRepeatStatementRule() {
		return getSTRepeatStatementAccess().getRule();
	}
	
	//STExpression returns STExpression:
	//    STSubrangeExpression;
	public STExpressionElements getSTExpressionAccess() {
		return pSTExpression;
	}
	
	public ParserRule getSTExpressionRule() {
		return getSTExpressionAccess().getRule();
	}
	
	//enum SubrangeOperator returns STBinaryOperator:
	//    Range='..';
	public SubrangeOperatorElements getSubrangeOperatorAccess() {
		return eSubrangeOperator;
	}
	
	public EnumRule getSubrangeOperatorRule() {
		return getSubrangeOperatorAccess().getRule();
	}
	
	//STSubrangeExpression returns STExpression:
	//    STOrExpression (({STBinaryExpression.left=current} op=SubrangeOperator) right=STOrExpression)*;
	public STSubrangeExpressionElements getSTSubrangeExpressionAccess() {
		return pSTSubrangeExpression;
	}
	
	public ParserRule getSTSubrangeExpressionRule() {
		return getSTSubrangeExpressionAccess().getRule();
	}
	
	//enum OrOperator returns STBinaryOperator:
	//    OR;
	public OrOperatorElements getOrOperatorAccess() {
		return eOrOperator;
	}
	
	public EnumRule getOrOperatorRule() {
		return getOrOperatorAccess().getRule();
	}
	
	//STOrExpression returns STExpression:
	//    STXorExpression (({STBinaryExpression.left=current} op=OrOperator) right=STXorExpression)*;
	public STOrExpressionElements getSTOrExpressionAccess() {
		return pSTOrExpression;
	}
	
	public ParserRule getSTOrExpressionRule() {
		return getSTOrExpressionAccess().getRule();
	}
	
	//enum XorOperator returns STBinaryOperator:
	//    XOR;
	public XorOperatorElements getXorOperatorAccess() {
		return eXorOperator;
	}
	
	public EnumRule getXorOperatorRule() {
		return getXorOperatorAccess().getRule();
	}
	
	//STXorExpression returns STExpression:
	//    STAndExpression (({STBinaryExpression.left=current} op=XorOperator) right=STAndExpression)*;
	public STXorExpressionElements getSTXorExpressionAccess() {
		return pSTXorExpression;
	}
	
	public ParserRule getSTXorExpressionRule() {
		return getSTXorExpressionAccess().getRule();
	}
	
	//enum AndOperator returns STBinaryOperator:
	//    AND | AMPERSAND='&';
	public AndOperatorElements getAndOperatorAccess() {
		return eAndOperator;
	}
	
	public EnumRule getAndOperatorRule() {
		return getAndOperatorAccess().getRule();
	}
	
	//STAndExpression returns STExpression:
	//    STEqualityExpression (({STBinaryExpression.left=current} op=AndOperator) right=STEqualityExpression)*;
	public STAndExpressionElements getSTAndExpressionAccess() {
		return pSTAndExpression;
	}
	
	public ParserRule getSTAndExpressionRule() {
		return getSTAndExpressionAccess().getRule();
	}
	
	//enum EqualityOperator returns STBinaryOperator:
	//    EQ='=' | NE='<>';
	public EqualityOperatorElements getEqualityOperatorAccess() {
		return eEqualityOperator;
	}
	
	public EnumRule getEqualityOperatorRule() {
		return getEqualityOperatorAccess().getRule();
	}
	
	//STEqualityExpression returns STExpression:
	//    STComparisonExpression (({STBinaryExpression.left=current} op=EqualityOperator) right=STComparisonExpression)*;
	public STEqualityExpressionElements getSTEqualityExpressionAccess() {
		return pSTEqualityExpression;
	}
	
	public ParserRule getSTEqualityExpressionRule() {
		return getSTEqualityExpressionAccess().getRule();
	}
	
	//enum CompareOperator returns STBinaryOperator:
	//    LT='<' | LE='<=' | GT='>' | GE='>=';
	public CompareOperatorElements getCompareOperatorAccess() {
		return eCompareOperator;
	}
	
	public EnumRule getCompareOperatorRule() {
		return getCompareOperatorAccess().getRule();
	}
	
	//STComparisonExpression returns STExpression:
	//    STAddSubExpression (({STBinaryExpression.left=current} op=CompareOperator) right=STAddSubExpression)*;
	public STComparisonExpressionElements getSTComparisonExpressionAccess() {
		return pSTComparisonExpression;
	}
	
	public ParserRule getSTComparisonExpressionRule() {
		return getSTComparisonExpressionAccess().getRule();
	}
	
	//enum AddSubOperator returns STBinaryOperator:
	//    ADD='+' | SUB='-';
	public AddSubOperatorElements getAddSubOperatorAccess() {
		return eAddSubOperator;
	}
	
	public EnumRule getAddSubOperatorRule() {
		return getAddSubOperatorAccess().getRule();
	}
	
	//STAddSubExpression returns STExpression:
	//    STMulDivModExpression (({STBinaryExpression.left=current} op=AddSubOperator) right=STMulDivModExpression)*;
	public STAddSubExpressionElements getSTAddSubExpressionAccess() {
		return pSTAddSubExpression;
	}
	
	public ParserRule getSTAddSubExpressionRule() {
		return getSTAddSubExpressionAccess().getRule();
	}
	
	//enum MulDivModOperator returns STBinaryOperator:
	//    MUL='*' | DIV='/' | MOD;
	public MulDivModOperatorElements getMulDivModOperatorAccess() {
		return eMulDivModOperator;
	}
	
	public EnumRule getMulDivModOperatorRule() {
		return getMulDivModOperatorAccess().getRule();
	}
	
	//STMulDivModExpression returns STExpression:
	//    STPowerExpression (({STBinaryExpression.left=current} op=MulDivModOperator) right=STPowerExpression)*;
	public STMulDivModExpressionElements getSTMulDivModExpressionAccess() {
		return pSTMulDivModExpression;
	}
	
	public ParserRule getSTMulDivModExpressionRule() {
		return getSTMulDivModExpressionAccess().getRule();
	}
	
	//enum PowerOperator returns STBinaryOperator:
	//    POWER='**';
	public PowerOperatorElements getPowerOperatorAccess() {
		return ePowerOperator;
	}
	
	public EnumRule getPowerOperatorRule() {
		return getPowerOperatorAccess().getRule();
	}
	
	//STPowerExpression returns STExpression:
	//    STUnaryExpression (({STBinaryExpression.left=current} op=PowerOperator) right=STUnaryExpression)*;
	public STPowerExpressionElements getSTPowerExpressionAccess() {
		return pSTPowerExpression;
	}
	
	public ParserRule getSTPowerExpressionRule() {
		return getSTPowerExpressionAccess().getRule();
	}
	
	//enum UnaryOperator returns STUnaryOperator:
	//    MINUS='-' | PLUS='+' | NOT;
	public UnaryOperatorElements getUnaryOperatorAccess() {
		return eUnaryOperator;
	}
	
	public EnumRule getUnaryOperatorRule() {
		return getUnaryOperatorAccess().getRule();
	}
	
	//STUnaryExpression returns STExpression:
	//    STAccessExpression | STLiteralExpressions | => STSignedLiteralExpressions | ({STUnaryExpression} op=UnaryOperator
	//    expression=STUnaryExpression);
	public STUnaryExpressionElements getSTUnaryExpressionAccess() {
		return pSTUnaryExpression;
	}
	
	public ParserRule getSTUnaryExpressionRule() {
		return getSTUnaryExpressionAccess().getRule();
	}
	
	//STAccessExpression returns STExpression:
	//    STPrimaryExpression (({STMemberAccessExpression.receiver=current} '.' member=(STFeatureExpression |
	//    STMultibitPartialExpression)) |
	//    ({STArrayAccessExpression.receiver=current} '[' index+=STExpression (',' index+=STExpression)* ']'))*;
	public STAccessExpressionElements getSTAccessExpressionAccess() {
		return pSTAccessExpression;
	}
	
	public ParserRule getSTAccessExpressionRule() {
		return getSTAccessExpressionAccess().getRule();
	}
	
	//STPrimaryExpression returns STExpression:
	//    '(' STExpression ')' | STFeatureExpression | STBuiltinFeatureExpression;
	public STPrimaryExpressionElements getSTPrimaryExpressionAccess() {
		return pSTPrimaryExpression;
	}
	
	public ParserRule getSTPrimaryExpressionRule() {
		return getSTPrimaryExpressionAccess().getRule();
	}
	
	//STFeatureExpression returns STExpression:
	//    {STFeatureExpression} feature=[libraryElement::INamedElement|STFeatureName]
	//    (call?='('
	//    (parameters+=STCallArgument (',' parameters+=STCallArgument)*)?
	//    ')')?;
	public STFeatureExpressionElements getSTFeatureExpressionAccess() {
		return pSTFeatureExpression;
	}
	
	public ParserRule getSTFeatureExpressionRule() {
		return getSTFeatureExpressionAccess().getRule();
	}
	
	//STFeatureName:
	//    QualifiedName | 'LT' | 'AND' | 'OR' | 'XOR' | 'MOD' | 'D' | 'DT' | 'LD';
	public STFeatureNameElements getSTFeatureNameAccess() {
		return pSTFeatureName;
	}
	
	public ParserRule getSTFeatureNameRule() {
		return getSTFeatureNameAccess().getRule();
	}
	
	//STBuiltinFeatureExpression returns STExpression:
	//    {STBuiltinFeatureExpression} feature=STBuiltinFeature (call?='(' (parameters+=STCallArgument
	//    (',' parameters+=STCallArgument)*)? ')')?;
	public STBuiltinFeatureExpressionElements getSTBuiltinFeatureExpressionAccess() {
		return pSTBuiltinFeatureExpression;
	}
	
	public ParserRule getSTBuiltinFeatureExpressionRule() {
		return getSTBuiltinFeatureExpressionAccess().getRule();
	}
	
	//enum STBuiltinFeature:
	//    THIS;
	public STBuiltinFeatureElements getSTBuiltinFeatureAccess() {
		return eSTBuiltinFeature;
	}
	
	public EnumRule getSTBuiltinFeatureRule() {
		return getSTBuiltinFeatureAccess().getRule();
	}
	
	//enum STMultiBitAccessSpecifier:
	//    L='%L' | D='%D' | W='%W' | B='%B' | X='%X';
	public STMultiBitAccessSpecifierElements getSTMultiBitAccessSpecifierAccess() {
		return eSTMultiBitAccessSpecifier;
	}
	
	public EnumRule getSTMultiBitAccessSpecifierRule() {
		return getSTMultiBitAccessSpecifierAccess().getRule();
	}
	
	//STMultibitPartialExpression returns STExpression:
	//    {STMultibitPartialExpression} (specifier=STMultiBitAccessSpecifier)? (index=INT | ('(' expression=STExpression ')'))
	//;
	public STMultibitPartialExpressionElements getSTMultibitPartialExpressionAccess() {
		return pSTMultibitPartialExpression;
	}
	
	public ParserRule getSTMultibitPartialExpressionRule() {
		return getSTMultibitPartialExpressionAccess().getRule();
	}
	
	//STLiteralExpressions returns STExpression:
	//    STNumericLiteral |
	//    STDateLiteral |
	//    STTimeLiteral |
	//    STTimeOfDayLiteral |
	//    STDateAndTimeLiteral |
	//    STStringLiteral |
	//    STEnumLiteral;
	public STLiteralExpressionsElements getSTLiteralExpressionsAccess() {
		return pSTLiteralExpressions;
	}
	
	public ParserRule getSTLiteralExpressionsRule() {
		return getSTLiteralExpressionsAccess().getRule();
	}
	
	//STSignedLiteralExpressions returns STExpression:
	//    STSignedNumericLiteral;
	public STSignedLiteralExpressionsElements getSTSignedLiteralExpressionsAccess() {
		return pSTSignedLiteralExpressions;
	}
	
	public ParserRule getSTSignedLiteralExpressionsRule() {
		return getSTSignedLiteralExpressionsAccess().getRule();
	}
	
	//STNumericLiteralType:
	//    STAnyBitType | STAnyNumType;
	public STNumericLiteralTypeElements getSTNumericLiteralTypeAccess() {
		return pSTNumericLiteralType;
	}
	
	public ParserRule getSTNumericLiteralTypeRule() {
		return getSTNumericLiteralTypeAccess().getRule();
	}
	
	//STNumericLiteral:
	//    (type=[datatype::DataType|STNumericLiteralType] '#' value=SignedNumeric) |
	//    ((type=[datatype::DataType|STNumericLiteralType] '#')?
	//    value=Numeric);
	public STNumericLiteralElements getSTNumericLiteralAccess() {
		return pSTNumericLiteral;
	}
	
	public ParserRule getSTNumericLiteralRule() {
		return getSTNumericLiteralAccess().getRule();
	}
	
	//STSignedNumericLiteral returns STNumericLiteral:
	//    value=SignedNumeric;
	public STSignedNumericLiteralElements getSTSignedNumericLiteralAccess() {
		return pSTSignedNumericLiteral;
	}
	
	public ParserRule getSTSignedNumericLiteralRule() {
		return getSTSignedNumericLiteralAccess().getRule();
	}
	
	//STDateLiteralType:
	//    STDateType |
	//    'D' |
	//    'LD';
	public STDateLiteralTypeElements getSTDateLiteralTypeAccess() {
		return pSTDateLiteralType;
	}
	
	public ParserRule getSTDateLiteralTypeRule() {
		return getSTDateLiteralTypeAccess().getRule();
	}
	
	//STDateLiteral:
	//    type=[datatype::DataType|STDateLiteralType] '#' value=Date;
	public STDateLiteralElements getSTDateLiteralAccess() {
		return pSTDateLiteral;
	}
	
	public ParserRule getSTDateLiteralRule() {
		return getSTDateLiteralAccess().getRule();
	}
	
	//STTimeLiteralType:
	//    STAnyDurationType |
	//    'T' |
	//    'LT';
	public STTimeLiteralTypeElements getSTTimeLiteralTypeAccess() {
		return pSTTimeLiteralType;
	}
	
	public ParserRule getSTTimeLiteralTypeRule() {
		return getSTTimeLiteralTypeAccess().getRule();
	}
	
	//STTimeLiteral:
	//    type=[datatype::DataType|STTimeLiteralType] '#' value=Time;
	public STTimeLiteralElements getSTTimeLiteralAccess() {
		return pSTTimeLiteral;
	}
	
	public ParserRule getSTTimeLiteralRule() {
		return getSTTimeLiteralAccess().getRule();
	}
	
	//STTimeOfDayLiteral:
	//    type=[datatype::DataType|STTimeOfDayType] '#' value=TimeOfDay;
	public STTimeOfDayLiteralElements getSTTimeOfDayLiteralAccess() {
		return pSTTimeOfDayLiteral;
	}
	
	public ParserRule getSTTimeOfDayLiteralRule() {
		return getSTTimeOfDayLiteralAccess().getRule();
	}
	
	//STDateAndTimeLiteral:
	//    type=[datatype::DataType|STDateAndTimeType] '#' value=DateAndTime;
	public STDateAndTimeLiteralElements getSTDateAndTimeLiteralAccess() {
		return pSTDateAndTimeLiteral;
	}
	
	public ParserRule getSTDateAndTimeLiteralRule() {
		return getSTDateAndTimeLiteralAccess().getRule();
	}
	
	//STStringLiteral:
	//    (type=[datatype::DataType|STAnyCharsType] '#')? value=STRING;
	public STStringLiteralElements getSTStringLiteralAccess() {
		return pSTStringLiteral;
	}
	
	public ParserRule getSTStringLiteralRule() {
		return getSTStringLiteralAccess().getRule();
	}
	
	//STEnumLiteral:
	//    value=[datatype::EnumeratedValue|EnumValue];
	public STEnumLiteralElements getSTEnumLiteralAccess() {
		return pSTEnumLiteral;
	}
	
	public ParserRule getSTEnumLiteralRule() {
		return getSTEnumLiteralAccess().getRule();
	}
	
	//EnumValue:
	//    QualifiedName '#' ID;
	public EnumValueElements getEnumValueAccess() {
		return pEnumValue;
	}
	
	public ParserRule getEnumValueRule() {
		return getEnumValueAccess().getRule();
	}
	
	//STAnyType:
	//    QualifiedName | STAnyBuiltinType;
	public STAnyTypeElements getSTAnyTypeAccess() {
		return pSTAnyType;
	}
	
	public ParserRule getSTAnyTypeRule() {
		return getSTAnyTypeAccess().getRule();
	}
	
	//STAnyBuiltinType:
	//    STAnyBitType | STAnyNumType | STAnyDurationType | STAnyDateType | STAnyCharsType;
	public STAnyBuiltinTypeElements getSTAnyBuiltinTypeAccess() {
		return pSTAnyBuiltinType;
	}
	
	public ParserRule getSTAnyBuiltinTypeRule() {
		return getSTAnyBuiltinTypeAccess().getRule();
	}
	
	//STAnyBitType:
	//    'BOOL' | 'BYTE' | 'WORD' | 'DWORD' | 'LWORD';
	public STAnyBitTypeElements getSTAnyBitTypeAccess() {
		return pSTAnyBitType;
	}
	
	public ParserRule getSTAnyBitTypeRule() {
		return getSTAnyBitTypeAccess().getRule();
	}
	
	//STAnyNumType:
	//    'SINT' | 'INT' | 'DINT' | 'LINT' | 'USINT' | 'UINT' | 'UDINT' | 'ULINT' | 'REAL' | 'LREAL';
	public STAnyNumTypeElements getSTAnyNumTypeAccess() {
		return pSTAnyNumType;
	}
	
	public ParserRule getSTAnyNumTypeRule() {
		return getSTAnyNumTypeAccess().getRule();
	}
	
	//STAnyDurationType:
	//    'TIME' | 'LTIME';
	public STAnyDurationTypeElements getSTAnyDurationTypeAccess() {
		return pSTAnyDurationType;
	}
	
	public ParserRule getSTAnyDurationTypeRule() {
		return getSTAnyDurationTypeAccess().getRule();
	}
	
	//STAnyDateType:
	//    STDateType | STTimeOfDayType | STDateAndTimeType;
	public STAnyDateTypeElements getSTAnyDateTypeAccess() {
		return pSTAnyDateType;
	}
	
	public ParserRule getSTAnyDateTypeRule() {
		return getSTAnyDateTypeAccess().getRule();
	}
	
	//STDateType:
	//    'DATE' | 'LDATE';
	public STDateTypeElements getSTDateTypeAccess() {
		return pSTDateType;
	}
	
	public ParserRule getSTDateTypeRule() {
		return getSTDateTypeAccess().getRule();
	}
	
	//STTimeOfDayType:
	//    'TIME_OF_DAY' |
	//    'LTIME_OF_DAY' |
	//    'TOD' |
	//    'LTOD';
	public STTimeOfDayTypeElements getSTTimeOfDayTypeAccess() {
		return pSTTimeOfDayType;
	}
	
	public ParserRule getSTTimeOfDayTypeRule() {
		return getSTTimeOfDayTypeAccess().getRule();
	}
	
	//STDateAndTimeType:
	//    'DATE_AND_TIME' |
	//    'LDATE_AND_TIME' |
	//    'DT' |
	//    'LDT';
	public STDateAndTimeTypeElements getSTDateAndTimeTypeAccess() {
		return pSTDateAndTimeType;
	}
	
	public ParserRule getSTDateAndTimeTypeRule() {
		return getSTDateAndTimeTypeAccess().getRule();
	}
	
	//STAnyCharsType:
	//    'STRING' | 'WSTRING' | 'CHAR' | 'WCHAR';
	public STAnyCharsTypeElements getSTAnyCharsTypeAccess() {
		return pSTAnyCharsType;
	}
	
	public ParserRule getSTAnyCharsTypeRule() {
		return getSTAnyCharsTypeAccess().getRule();
	}
	
	//QualifiedName:
	//    ID ('::' ID)*;
	public QualifiedNameElements getQualifiedNameAccess() {
		return pQualifiedName;
	}
	
	public ParserRule getQualifiedNameRule() {
		return getQualifiedNameAccess().getRule();
	}
	
	//QualifiedNameWithWildcard:
	//    QualifiedName '::*'?;
	public QualifiedNameWithWildcardElements getQualifiedNameWithWildcardAccess() {
		return pQualifiedNameWithWildcard;
	}
	
	public ParserRule getQualifiedNameWithWildcardRule() {
		return getQualifiedNameWithWildcardAccess().getRule();
	}
	
	//Numeric returns ecore::EJavaObject:
	//    'TRUE' | 'FALSE' | Number | NON_DECIMAL;
	public NumericElements getNumericAccess() {
		return pNumeric;
	}
	
	public ParserRule getNumericRule() {
		return getNumericAccess().getRule();
	}
	
	//Number hidden():
	//    INT ('.' (INT | DECIMAL))?;
	public NumberElements getNumberAccess() {
		return pNumber;
	}
	
	public ParserRule getNumberRule() {
		return getNumberAccess().getRule();
	}
	
	//SignedNumeric returns ecore::EJavaObject:
	//    SignedNumber;
	public SignedNumericElements getSignedNumericAccess() {
		return pSignedNumeric;
	}
	
	public ParserRule getSignedNumericRule() {
		return getSignedNumericAccess().getRule();
	}
	
	//SignedNumber hidden():
	//    ('+' | '-') INT ('.' (INT | DECIMAL))?;
	public SignedNumberElements getSignedNumberAccess() {
		return pSignedNumber;
	}
	
	public ParserRule getSignedNumberRule() {
		return getSignedNumberAccess().getRule();
	}
	
	//Time returns STTime hidden():
	//    ('+' | '-')? TIME_VALUE;
	public TimeElements getTimeAccess() {
		return pTime;
	}
	
	public ParserRule getTimeRule() {
		return getTimeAccess().getRule();
	}
	
	//Date returns STDate:
	//    INT '-' INT '-' INT;
	public DateElements getDateAccess() {
		return pDate;
	}
	
	public ParserRule getDateRule() {
		return getDateAccess().getRule();
	}
	
	//DateAndTime returns STDateAndTime:
	//    INT '-' INT '-' INT '-' INT ':' INT ':' INT ('.' INT)?;
	public DateAndTimeElements getDateAndTimeAccess() {
		return pDateAndTime;
	}
	
	public ParserRule getDateAndTimeRule() {
		return getDateAndTimeAccess().getRule();
	}
	
	//TimeOfDay returns STTimeOfDay:
	//    INT ':' INT ':' INT ('.' INT)?;
	public TimeOfDayElements getTimeOfDayAccess() {
		return pTimeOfDay;
	}
	
	public ParserRule getTimeOfDayRule() {
		return getTimeOfDayAccess().getRule();
	}
	
	//enum STAccessSpecifier:
	//    PROTECTED='PROTECTED' | PUBLIC='PUBLIC' | PRIVATE='PRIVATE' | INTERNAL='INTERNAL';
	public STAccessSpecifierElements getSTAccessSpecifierAccess() {
		return eSTAccessSpecifier;
	}
	
	public EnumRule getSTAccessSpecifierRule() {
		return getSTAccessSpecifierAccess().getRule();
	}
	
	///** Keep in sync with fordiac keywords */
	//RESERVED_KEYWORDS:
	//    'ABSTRACT' | 'ACTION' | 'CLASS' | 'CONFIGURATION' | 'END_ACTION' | 'END_CLASS' | 'END_CONFIGURATION' |
	//    'END_FUNCTION' | 'END_FUNCTION_BLOCK' | 'END_INTERFACE' | 'END_METHOD' | 'END_NAMESPACE' | 'END_PROGRAM' |
	//    'END_RESOURCE' | 'END_STEP' | 'END_STRUCT' | 'END_TRANSITION' | 'END_TYPE' | 'EXTENDS' | 'FINAL' | 'FROM' |
	//    'FUNCTION' | 'FUNCTION_BLOCK' | 'IMPLEMENTS' | 'INITIAL_STEP' | 'INTERFACE' | 'INTERAL' | 'INTERVAL' | 'METHOD' |
	//    'NAMESPACE' | 'NON_RETAIN' | 'NULL' | 'ON' | 'OVERLAP' | 'OVERRIDE' | 'PRIORITY' | 'PRIVATE' | 'PROGRAM' |
	//    'PROTECTED' | 'PUBLIC' | 'READ_ONLY' | 'READ_WRITE' | 'REF' | 'REF_TO' | 'RESOURCE' | 'RETAIN' | 'SINGLE' | 'STEP' |
	//    'STRUCT' | 'SUPER' | 'TASK' | 'THIS' | 'TRANSITION' | 'TYPE' |
	//    'USING' | 'VAR_ACCESS' | 'VAR_CONFIG' | 'VAR_EXTERNAL' | 'VAR_GLOBAL' | 'WITH';
	public RESERVED_KEYWORDSElements getRESERVED_KEYWORDSAccess() {
		return pRESERVED_KEYWORDS;
	}
	
	public ParserRule getRESERVED_KEYWORDSRule() {
		return getRESERVED_KEYWORDSAccess().getRule();
	}
	
	//terminal fragment HEX_DIGIT:
	//    '0'..'9' | 'a'..'f' | 'A'..'F' | '_';
	public TerminalRule getHEX_DIGITRule() {
		return tHEX_DIGIT;
	}
	
	//terminal NON_DECIMAL:
	//    ('2#' | '8#' | '16#') HEX_DIGIT+;
	public TerminalRule getNON_DECIMALRule() {
		return tNON_DECIMAL;
	}
	
	//terminal INT returns ecore::EBigInteger:
	//    '0'..'9' ('0'..'9' | '_')*;
	public TerminalRule getINTRule() {
		return tINT;
	}
	
	//terminal DECIMAL:
	//    INT (('e' | 'E') ('+' | '-')? INT)?;
	public TerminalRule getDECIMALRule() {
		return tDECIMAL;
	}
	
	//terminal TIME_VALUE:
	//    (TIME_PART ('_')?)+;
	public TerminalRule getTIME_VALUERule() {
		return tTIME_VALUE;
	}
	
	//terminal fragment TIME_PART:
	//    INT (TIME_DAYS | TIME_HOURS | TIME_MINUTES | TIME_SECONDS | TIME_MILLIS | TIME_MICROS | TIME_NANOS);
	public TerminalRule getTIME_PARTRule() {
		return tTIME_PART;
	}
	
	//terminal fragment TIME_DAYS:
	//    'D' | 'd';
	public TerminalRule getTIME_DAYSRule() {
		return tTIME_DAYS;
	}
	
	//terminal fragment TIME_HOURS:
	//    'H' | 'h';
	public TerminalRule getTIME_HOURSRule() {
		return tTIME_HOURS;
	}
	
	//terminal fragment TIME_MINUTES:
	//    'M' | 'm';
	public TerminalRule getTIME_MINUTESRule() {
		return tTIME_MINUTES;
	}
	
	//terminal fragment TIME_SECONDS:
	//    'S' | 's';
	public TerminalRule getTIME_SECONDSRule() {
		return tTIME_SECONDS;
	}
	
	//terminal fragment TIME_MILLIS:
	//    ('M' | 'm') ('S' | 's');
	public TerminalRule getTIME_MILLISRule() {
		return tTIME_MILLIS;
	}
	
	// // MS
	//terminal fragment TIME_MICROS:
	//    ('U' | 'u') ('S' | 's');
	public TerminalRule getTIME_MICROSRule() {
		return tTIME_MICROS;
	}
	
	// // US
	//terminal fragment TIME_NANOS:
	//    ('N' | 'n') ('S' | 's');
	public TerminalRule getTIME_NANOSRule() {
		return tTIME_NANOS;
	}
	
	// // NS
	//terminal ID:
	//    '^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return tID;
	}
	
	//terminal STRING returns STString:
	//    '"' ('$' . /* 'L'|'N'|'P'|'R'|'T'|'"'|'$' */ | !('$' | '"'))* '"' |
	//    "'" ('$' . /* "L"|"N"|"P"|"R"|"T"|"'"|"$" */ | !('$' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return tSTRING;
	}
	
	//terminal ML_COMMENT:
	//    '/*'->'*/' | '(*'->'*)';
	public TerminalRule getML_COMMENTRule() {
		return tML_COMMENT;
	}
	
	//terminal SL_COMMENT:
	//    '//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return tSL_COMMENT;
	}
	
	//terminal WS:
	//    (' ' | '\t' | '\r' | '\n')+;
	public TerminalRule getWSRule() {
		return tWS;
	}
	
	//terminal ANY_OTHER:
	//    .;
	public TerminalRule getANY_OTHERRule() {
		return tANY_OTHER;
	}
}
