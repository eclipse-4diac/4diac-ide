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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
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
public class STFunctionGrammarAccess extends AbstractElementFinder.AbstractGrammarElementFinder {
	
	public class STFunctionSourceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunction.STFunctionSource");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTFunctionSourceAction_0 = (Action)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cPACKAGEKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cNameAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cNameQualifiedNameParserRuleCall_1_1_0 = (RuleCall)cNameAssignment_1_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		private final Assignment cImportsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cImportsSTImportParserRuleCall_2_0 = (RuleCall)cImportsAssignment_2.eContents().get(0);
		private final Assignment cFunctionsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cFunctionsSTFunctionParserRuleCall_3_0 = (RuleCall)cFunctionsAssignment_3.eContents().get(0);
		
		//STFunctionSource returns stcore::STSource:
		//    {STFunctionSource}
		//    ('PACKAGE' name=QualifiedName ';')? // package declaration (optional)
		//    imports+=STImport* // imports (optional)
		//    functions+=STFunction*;
		@Override public ParserRule getRule() { return rule; }
		
		//{STFunctionSource}
		//('PACKAGE' name=QualifiedName ';')? // package declaration (optional)
		//imports+=STImport* // imports (optional)
		//functions+=STFunction*
		public Group getGroup() { return cGroup; }
		
		//{STFunctionSource}
		public Action getSTFunctionSourceAction_0() { return cSTFunctionSourceAction_0; }
		
		//('PACKAGE' name=QualifiedName ';')?
		public Group getGroup_1() { return cGroup_1; }
		
		//'PACKAGE'
		public Keyword getPACKAGEKeyword_1_0() { return cPACKAGEKeyword_1_0; }
		
		//name=QualifiedName
		public Assignment getNameAssignment_1_1() { return cNameAssignment_1_1; }
		
		//QualifiedName
		public RuleCall getNameQualifiedNameParserRuleCall_1_1_0() { return cNameQualifiedNameParserRuleCall_1_1_0; }
		
		//';'
		public Keyword getSemicolonKeyword_1_2() { return cSemicolonKeyword_1_2; }
		
		//// package declaration (optional)
		//   imports+=STImport*
		public Assignment getImportsAssignment_2() { return cImportsAssignment_2; }
		
		//STImport
		public RuleCall getImportsSTImportParserRuleCall_2_0() { return cImportsSTImportParserRuleCall_2_0; }
		
		//// imports (optional)
		//   functions+=STFunction*
		public Assignment getFunctionsAssignment_3() { return cFunctionsAssignment_3; }
		
		//STFunction
		public RuleCall getFunctionsSTFunctionParserRuleCall_3_0() { return cFunctionsSTFunctionParserRuleCall_3_0; }
	}
	public class STFunctionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunction.STFunction");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSTFunctionAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cFUNCTIONKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cNameAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cNameIDTerminalRuleCall_2_0 = (RuleCall)cNameAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cColonKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cReturnTypeAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final CrossReference cReturnTypeDataTypeCrossReference_3_1_0 = (CrossReference)cReturnTypeAssignment_3_1.eContents().get(0);
		private final RuleCall cReturnTypeDataTypeSTAnyTypeParserRuleCall_3_1_0_1 = (RuleCall)cReturnTypeDataTypeCrossReference_3_1_0.eContents().get(1);
		private final Assignment cVarDeclarationsAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final Alternatives cVarDeclarationsAlternatives_4_0 = (Alternatives)cVarDeclarationsAssignment_4.eContents().get(0);
		private final RuleCall cVarDeclarationsSTVarDeclarationBlockParserRuleCall_4_0_0 = (RuleCall)cVarDeclarationsAlternatives_4_0.eContents().get(0);
		private final RuleCall cVarDeclarationsSTVarTempDeclarationBlockParserRuleCall_4_0_1 = (RuleCall)cVarDeclarationsAlternatives_4_0.eContents().get(1);
		private final RuleCall cVarDeclarationsSTVarInputDeclarationBlockParserRuleCall_4_0_2 = (RuleCall)cVarDeclarationsAlternatives_4_0.eContents().get(2);
		private final RuleCall cVarDeclarationsSTVarOutputDeclarationBlockParserRuleCall_4_0_3 = (RuleCall)cVarDeclarationsAlternatives_4_0.eContents().get(3);
		private final RuleCall cVarDeclarationsSTVarInOutDeclarationBlockParserRuleCall_4_0_4 = (RuleCall)cVarDeclarationsAlternatives_4_0.eContents().get(4);
		private final Assignment cCodeAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cCodeSTStatementParserRuleCall_5_0 = (RuleCall)cCodeAssignment_5.eContents().get(0);
		private final Keyword cEND_FUNCTIONKeyword_6 = (Keyword)cGroup.eContents().get(6);
		
		//STFunction:
		//    {STFunction}
		//    'FUNCTION' name=ID (':' returnType=[datatype::DataType|STAnyType])?
		//        varDeclarations+=(STVarDeclarationBlock | STVarTempDeclarationBlock |
		//        STVarInputDeclarationBlock | STVarOutputDeclarationBlock | STVarInOutDeclarationBlock)*
		//        code += STStatement*
		//    'END_FUNCTION';
		@Override public ParserRule getRule() { return rule; }
		
		//{STFunction}
		//'FUNCTION' name=ID (':' returnType=[datatype::DataType|STAnyType])?
		//    varDeclarations+=(STVarDeclarationBlock | STVarTempDeclarationBlock |
		//    STVarInputDeclarationBlock | STVarOutputDeclarationBlock | STVarInOutDeclarationBlock)*
		//    code += STStatement*
		//'END_FUNCTION'
		public Group getGroup() { return cGroup; }
		
		//{STFunction}
		public Action getSTFunctionAction_0() { return cSTFunctionAction_0; }
		
		//'FUNCTION'
		public Keyword getFUNCTIONKeyword_1() { return cFUNCTIONKeyword_1; }
		
		//name=ID
		public Assignment getNameAssignment_2() { return cNameAssignment_2; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_2_0() { return cNameIDTerminalRuleCall_2_0; }
		
		//(':' returnType=[datatype::DataType|STAnyType])?
		public Group getGroup_3() { return cGroup_3; }
		
		//':'
		public Keyword getColonKeyword_3_0() { return cColonKeyword_3_0; }
		
		//returnType=[datatype::DataType|STAnyType]
		public Assignment getReturnTypeAssignment_3_1() { return cReturnTypeAssignment_3_1; }
		
		//[datatype::DataType|STAnyType]
		public CrossReference getReturnTypeDataTypeCrossReference_3_1_0() { return cReturnTypeDataTypeCrossReference_3_1_0; }
		
		//STAnyType
		public RuleCall getReturnTypeDataTypeSTAnyTypeParserRuleCall_3_1_0_1() { return cReturnTypeDataTypeSTAnyTypeParserRuleCall_3_1_0_1; }
		
		//varDeclarations+=(STVarDeclarationBlock | STVarTempDeclarationBlock |
		//STVarInputDeclarationBlock | STVarOutputDeclarationBlock | STVarInOutDeclarationBlock)*
		public Assignment getVarDeclarationsAssignment_4() { return cVarDeclarationsAssignment_4; }
		
		//(STVarDeclarationBlock | STVarTempDeclarationBlock |
		//        STVarInputDeclarationBlock | STVarOutputDeclarationBlock | STVarInOutDeclarationBlock)
		public Alternatives getVarDeclarationsAlternatives_4_0() { return cVarDeclarationsAlternatives_4_0; }
		
		//STVarDeclarationBlock
		public RuleCall getVarDeclarationsSTVarDeclarationBlockParserRuleCall_4_0_0() { return cVarDeclarationsSTVarDeclarationBlockParserRuleCall_4_0_0; }
		
		//STVarTempDeclarationBlock
		public RuleCall getVarDeclarationsSTVarTempDeclarationBlockParserRuleCall_4_0_1() { return cVarDeclarationsSTVarTempDeclarationBlockParserRuleCall_4_0_1; }
		
		//STVarInputDeclarationBlock
		public RuleCall getVarDeclarationsSTVarInputDeclarationBlockParserRuleCall_4_0_2() { return cVarDeclarationsSTVarInputDeclarationBlockParserRuleCall_4_0_2; }
		
		//STVarOutputDeclarationBlock
		public RuleCall getVarDeclarationsSTVarOutputDeclarationBlockParserRuleCall_4_0_3() { return cVarDeclarationsSTVarOutputDeclarationBlockParserRuleCall_4_0_3; }
		
		//STVarInOutDeclarationBlock
		public RuleCall getVarDeclarationsSTVarInOutDeclarationBlockParserRuleCall_4_0_4() { return cVarDeclarationsSTVarInOutDeclarationBlockParserRuleCall_4_0_4; }
		
		//code += STStatement*
		public Assignment getCodeAssignment_5() { return cCodeAssignment_5; }
		
		//STStatement
		public RuleCall getCodeSTStatementParserRuleCall_5_0() { return cCodeSTStatementParserRuleCall_5_0; }
		
		//'END_FUNCTION'
		public Keyword getEND_FUNCTIONKeyword_6() { return cEND_FUNCTIONKeyword_6; }
	}
	
	
	private final STFunctionSourceElements pSTFunctionSource;
	private final STFunctionElements pSTFunction;
	
	private final Grammar grammar;
	
	private final STCoreGrammarAccess gaSTCore;

	@Inject
	public STFunctionGrammarAccess(GrammarProvider grammarProvider,
			STCoreGrammarAccess gaSTCore) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaSTCore = gaSTCore;
		this.pSTFunctionSource = new STFunctionSourceElements();
		this.pSTFunction = new STFunctionElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunction".equals(grammar.getName())) {
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
	
	
	public STCoreGrammarAccess getSTCoreGrammarAccess() {
		return gaSTCore;
	}

	
	//STFunctionSource returns stcore::STSource:
	//    {STFunctionSource}
	//    ('PACKAGE' name=QualifiedName ';')? // package declaration (optional)
	//    imports+=STImport* // imports (optional)
	//    functions+=STFunction*;
	public STFunctionSourceElements getSTFunctionSourceAccess() {
		return pSTFunctionSource;
	}
	
	public ParserRule getSTFunctionSourceRule() {
		return getSTFunctionSourceAccess().getRule();
	}
	
	//STFunction:
	//    {STFunction}
	//    'FUNCTION' name=ID (':' returnType=[datatype::DataType|STAnyType])?
	//        varDeclarations+=(STVarDeclarationBlock | STVarTempDeclarationBlock |
	//        STVarInputDeclarationBlock | STVarOutputDeclarationBlock | STVarInOutDeclarationBlock)*
	//        code += STStatement*
	//    'END_FUNCTION';
	public STFunctionElements getSTFunctionAccess() {
		return pSTFunction;
	}
	
	public ParserRule getSTFunctionRule() {
		return getSTFunctionAccess().getRule();
	}
	
	//STCoreSource returns STSource:
	//    {STCoreSource} statements+=STStatement*;
	public STCoreGrammarAccess.STCoreSourceElements getSTCoreSourceAccess() {
		return gaSTCore.getSTCoreSourceAccess();
	}
	
	public ParserRule getSTCoreSourceRule() {
		return getSTCoreSourceAccess().getRule();
	}
	
	//STExpressionSource returns STSource:
	//    {STExpressionSource} expression=STExpression?;
	public STCoreGrammarAccess.STExpressionSourceElements getSTExpressionSourceAccess() {
		return gaSTCore.getSTExpressionSourceAccess();
	}
	
	public ParserRule getSTExpressionSourceRule() {
		return getSTExpressionSourceAccess().getRule();
	}
	
	//STExpressionSource0 returns STSource:
	//    STExpressionSource;
	public STCoreGrammarAccess.STExpressionSource0Elements getSTExpressionSource0Access() {
		return gaSTCore.getSTExpressionSource0Access();
	}
	
	public ParserRule getSTExpressionSource0Rule() {
		return getSTExpressionSource0Access().getRule();
	}
	
	// // necessary to keep Xtext from skipping this rule
	//STInitializerExpressionSource returns STSource:
	//    {STInitializerExpressionSource} initializerExpression=STInitializerExpression?;
	public STCoreGrammarAccess.STInitializerExpressionSourceElements getSTInitializerExpressionSourceAccess() {
		return gaSTCore.getSTInitializerExpressionSourceAccess();
	}
	
	public ParserRule getSTInitializerExpressionSourceRule() {
		return getSTInitializerExpressionSourceAccess().getRule();
	}
	
	//STInitializerExpressionSource0 returns STSource:
	//    STInitializerExpressionSource;
	public STCoreGrammarAccess.STInitializerExpressionSource0Elements getSTInitializerExpressionSource0Access() {
		return gaSTCore.getSTInitializerExpressionSource0Access();
	}
	
	public ParserRule getSTInitializerExpressionSource0Rule() {
		return getSTInitializerExpressionSource0Access().getRule();
	}
	
	// // necessary to keep Xtext from skipping this rule
	//STImport returns STImport:
	//    'IMPORT' importedNamespace=QualifiedNameWithWildcard ';';
	public STCoreGrammarAccess.STImportElements getSTImportAccess() {
		return gaSTCore.getSTImportAccess();
	}
	
	public ParserRule getSTImportRule() {
		return getSTImportAccess().getRule();
	}
	
	//STVarDeclarationBlock returns STVarPlainDeclarationBlock:
	//    {STVarPlainDeclarationBlock} 'VAR' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STCoreGrammarAccess.STVarDeclarationBlockElements getSTVarDeclarationBlockAccess() {
		return gaSTCore.getSTVarDeclarationBlockAccess();
	}
	
	public ParserRule getSTVarDeclarationBlockRule() {
		return getSTVarDeclarationBlockAccess().getRule();
	}
	
	//STVarTempDeclarationBlock returns STVarTempDeclarationBlock:
	//    {STVarTempDeclarationBlock} 'VAR_TEMP' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STCoreGrammarAccess.STVarTempDeclarationBlockElements getSTVarTempDeclarationBlockAccess() {
		return gaSTCore.getSTVarTempDeclarationBlockAccess();
	}
	
	public ParserRule getSTVarTempDeclarationBlockRule() {
		return getSTVarTempDeclarationBlockAccess().getRule();
	}
	
	//STVarInputDeclarationBlock returns STVarInputDeclarationBlock:
	//    {STVarInputDeclarationBlock} 'VAR_INPUT' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STCoreGrammarAccess.STVarInputDeclarationBlockElements getSTVarInputDeclarationBlockAccess() {
		return gaSTCore.getSTVarInputDeclarationBlockAccess();
	}
	
	public ParserRule getSTVarInputDeclarationBlockRule() {
		return getSTVarInputDeclarationBlockAccess().getRule();
	}
	
	//STVarOutputDeclarationBlock returns STVarOutputDeclarationBlock:
	//    {STVarOutputDeclarationBlock} 'VAR_OUTPUT' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STCoreGrammarAccess.STVarOutputDeclarationBlockElements getSTVarOutputDeclarationBlockAccess() {
		return gaSTCore.getSTVarOutputDeclarationBlockAccess();
	}
	
	public ParserRule getSTVarOutputDeclarationBlockRule() {
		return getSTVarOutputDeclarationBlockAccess().getRule();
	}
	
	//STVarInOutDeclarationBlock returns STVarInOutDeclarationBlock:
	//    {STVarInOutDeclarationBlock} 'VAR_IN_OUT' (constant?='CONSTANT')?
	//    varDeclarations+=STVarDeclaration*
	//    'END_VAR';
	public STCoreGrammarAccess.STVarInOutDeclarationBlockElements getSTVarInOutDeclarationBlockAccess() {
		return gaSTCore.getSTVarInOutDeclarationBlockAccess();
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
	public STCoreGrammarAccess.STVarDeclarationElements getSTVarDeclarationAccess() {
		return gaSTCore.getSTVarDeclarationAccess();
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
	public STCoreGrammarAccess.STTypeDeclarationElements getSTTypeDeclarationAccess() {
		return gaSTCore.getSTTypeDeclarationAccess();
	}
	
	public ParserRule getSTTypeDeclarationRule() {
		return getSTTypeDeclarationAccess().getRule();
	}
	
	//STTypeDeclaration0 returns STTypeDeclaration:
	//    STTypeDeclaration;
	public STCoreGrammarAccess.STTypeDeclaration0Elements getSTTypeDeclaration0Access() {
		return gaSTCore.getSTTypeDeclaration0Access();
	}
	
	public ParserRule getSTTypeDeclaration0Rule() {
		return getSTTypeDeclaration0Access().getRule();
	}
	
	// // necessary to keep Xtext from skipping this rule
	//STInitializerExpression:
	//    STElementaryInitializerExpression | STArrayInitializerExpression | STStructInitializerExpression;
	public STCoreGrammarAccess.STInitializerExpressionElements getSTInitializerExpressionAccess() {
		return gaSTCore.getSTInitializerExpressionAccess();
	}
	
	public ParserRule getSTInitializerExpressionRule() {
		return getSTInitializerExpressionAccess().getRule();
	}
	
	//STElementaryInitializerExpression:
	//    value=STExpression;
	public STCoreGrammarAccess.STElementaryInitializerExpressionElements getSTElementaryInitializerExpressionAccess() {
		return gaSTCore.getSTElementaryInitializerExpressionAccess();
	}
	
	public ParserRule getSTElementaryInitializerExpressionRule() {
		return getSTElementaryInitializerExpressionAccess().getRule();
	}
	
	//STArrayInitializerExpression:
	//    '[' values+=STArrayInitElement (',' values+=STArrayInitElement)* ']';
	public STCoreGrammarAccess.STArrayInitializerExpressionElements getSTArrayInitializerExpressionAccess() {
		return gaSTCore.getSTArrayInitializerExpressionAccess();
	}
	
	public ParserRule getSTArrayInitializerExpressionRule() {
		return getSTArrayInitializerExpressionAccess().getRule();
	}
	
	//STArrayInitElement:
	//    STSingleArrayInitElement | STRepeatArrayInitElement;
	public STCoreGrammarAccess.STArrayInitElementElements getSTArrayInitElementAccess() {
		return gaSTCore.getSTArrayInitElementAccess();
	}
	
	public ParserRule getSTArrayInitElementRule() {
		return getSTArrayInitElementAccess().getRule();
	}
	
	//STSingleArrayInitElement:
	//    initExpression=STInitializerExpression;
	public STCoreGrammarAccess.STSingleArrayInitElementElements getSTSingleArrayInitElementAccess() {
		return gaSTCore.getSTSingleArrayInitElementAccess();
	}
	
	public ParserRule getSTSingleArrayInitElementRule() {
		return getSTSingleArrayInitElementAccess().getRule();
	}
	
	//STRepeatArrayInitElement:
	//    repetitions=INT '(' initExpressions+=STInitializerExpression (','
	//    initExpressions+=STInitializerExpression)* ')';
	public STCoreGrammarAccess.STRepeatArrayInitElementElements getSTRepeatArrayInitElementAccess() {
		return gaSTCore.getSTRepeatArrayInitElementAccess();
	}
	
	public ParserRule getSTRepeatArrayInitElementRule() {
		return getSTRepeatArrayInitElementAccess().getRule();
	}
	
	//STStructInitializerExpression:
	//    (type=[datatype::StructuredType|QualifiedName] '#')? '(' values+=STStructInitElement (','
	//    values+=STStructInitElement)* ')';
	public STCoreGrammarAccess.STStructInitializerExpressionElements getSTStructInitializerExpressionAccess() {
		return gaSTCore.getSTStructInitializerExpressionAccess();
	}
	
	public ParserRule getSTStructInitializerExpressionRule() {
		return getSTStructInitializerExpressionAccess().getRule();
	}
	
	//STStructInitElement:
	//    variable=[libraryElement::INamedElement|STFeatureName] ':=' value=STInitializerExpression;
	public STCoreGrammarAccess.STStructInitElementElements getSTStructInitElementAccess() {
		return gaSTCore.getSTStructInitElementAccess();
	}
	
	public ParserRule getSTStructInitElementRule() {
		return getSTStructInitElementAccess().getRule();
	}
	
	//STPragma:
	//    {STPragma}
	//    '{' attributes+=STAttribute (',' attributes+=STAttribute)* '}';
	public STCoreGrammarAccess.STPragmaElements getSTPragmaAccess() {
		return gaSTCore.getSTPragmaAccess();
	}
	
	public ParserRule getSTPragmaRule() {
		return getSTPragmaAccess().getRule();
	}
	
	//STAttribute:
	//    declaration=[libraryElement::AttributeDeclaration|STAttributeName] ':=' value=STInitializerExpression;
	public STCoreGrammarAccess.STAttributeElements getSTAttributeAccess() {
		return gaSTCore.getSTAttributeAccess();
	}
	
	public ParserRule getSTAttributeRule() {
		return getSTAttributeAccess().getRule();
	}
	
	//STAttributeName:
	//    QualifiedName;
	public STCoreGrammarAccess.STAttributeNameElements getSTAttributeNameAccess() {
		return gaSTCore.getSTAttributeNameAccess();
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
	public STCoreGrammarAccess.STStatementElements getSTStatementAccess() {
		return gaSTCore.getSTStatementAccess();
	}
	
	public ParserRule getSTStatementRule() {
		return getSTStatementAccess().getRule();
	}
	
	//STAssignment returns STExpression:
	//    STExpression ({STAssignment.left=current} ':=' right=STAssignment)?;
	public STCoreGrammarAccess.STAssignmentElements getSTAssignmentAccess() {
		return gaSTCore.getSTAssignmentAccess();
	}
	
	public ParserRule getSTAssignmentRule() {
		return getSTAssignmentAccess().getRule();
	}
	
	//STCallArgument:
	//    STCallUnnamedArgument | STCallNamedInputArgument | STCallNamedOutputArgument;
	public STCoreGrammarAccess.STCallArgumentElements getSTCallArgumentAccess() {
		return gaSTCore.getSTCallArgumentAccess();
	}
	
	public ParserRule getSTCallArgumentRule() {
		return getSTCallArgumentAccess().getRule();
	}
	
	//STCallUnnamedArgument:
	//    argument=STExpression;
	public STCoreGrammarAccess.STCallUnnamedArgumentElements getSTCallUnnamedArgumentAccess() {
		return gaSTCore.getSTCallUnnamedArgumentAccess();
	}
	
	public ParserRule getSTCallUnnamedArgumentRule() {
		return getSTCallUnnamedArgumentAccess().getRule();
	}
	
	//STCallNamedInputArgument:
	//    parameter=[libraryElement::INamedElement] ':=' argument=STExpression;
	public STCoreGrammarAccess.STCallNamedInputArgumentElements getSTCallNamedInputArgumentAccess() {
		return gaSTCore.getSTCallNamedInputArgumentAccess();
	}
	
	public ParserRule getSTCallNamedInputArgumentRule() {
		return getSTCallNamedInputArgumentAccess().getRule();
	}
	
	//STCallNamedOutputArgument:
	//    not?='NOT'? parameter=[libraryElement::INamedElement] '=>' argument=STExpression;
	public STCoreGrammarAccess.STCallNamedOutputArgumentElements getSTCallNamedOutputArgumentAccess() {
		return gaSTCore.getSTCallNamedOutputArgumentAccess();
	}
	
	public ParserRule getSTCallNamedOutputArgumentRule() {
		return getSTCallNamedOutputArgumentAccess().getRule();
	}
	
	//STIfStatement:
	//    'IF' condition=STExpression 'THEN' statements+=STStatement* elseifs+=(STElseIfPart)* (else=STElsePart)? 'END_IF';
	public STCoreGrammarAccess.STIfStatementElements getSTIfStatementAccess() {
		return gaSTCore.getSTIfStatementAccess();
	}
	
	public ParserRule getSTIfStatementRule() {
		return getSTIfStatementAccess().getRule();
	}
	
	//STElseIfPart:
	//    'ELSIF' condition=STExpression 'THEN' statements+=STStatement*;
	public STCoreGrammarAccess.STElseIfPartElements getSTElseIfPartAccess() {
		return gaSTCore.getSTElseIfPartAccess();
	}
	
	public ParserRule getSTElseIfPartRule() {
		return getSTElseIfPartAccess().getRule();
	}
	
	//STCaseStatement:
	//    'CASE' selector=STExpression 'OF' cases+=STCaseCases+ (else=STElsePart)? 'END_CASE';
	public STCoreGrammarAccess.STCaseStatementElements getSTCaseStatementAccess() {
		return gaSTCore.getSTCaseStatementAccess();
	}
	
	public ParserRule getSTCaseStatementRule() {
		return getSTCaseStatementAccess().getRule();
	}
	
	//STCaseCases:
	//    conditions+=STExpression (',' conditions+=STExpression)* ':' =>statements+=STStatement*;
	public STCoreGrammarAccess.STCaseCasesElements getSTCaseCasesAccess() {
		return gaSTCore.getSTCaseCasesAccess();
	}
	
	public ParserRule getSTCaseCasesRule() {
		return getSTCaseCasesAccess().getRule();
	}
	
	//STElsePart:
	//    {STElsePart} 'ELSE' statements+=STStatement*;
	public STCoreGrammarAccess.STElsePartElements getSTElsePartAccess() {
		return gaSTCore.getSTElsePartAccess();
	}
	
	public ParserRule getSTElsePartRule() {
		return getSTElsePartAccess().getRule();
	}
	
	//STForStatement:
	//    'FOR' variable=STExpression ':=' from=STExpression 'TO' to=STExpression ('BY' by=STExpression)? 'DO'
	//    statements+=STStatement*
	//    'END_FOR';
	public STCoreGrammarAccess.STForStatementElements getSTForStatementAccess() {
		return gaSTCore.getSTForStatementAccess();
	}
	
	public ParserRule getSTForStatementRule() {
		return getSTForStatementAccess().getRule();
	}
	
	//STWhileStatement:
	//    'WHILE' condition=STExpression 'DO'
	//    statements+=STStatement*
	//    'END_WHILE';
	public STCoreGrammarAccess.STWhileStatementElements getSTWhileStatementAccess() {
		return gaSTCore.getSTWhileStatementAccess();
	}
	
	public ParserRule getSTWhileStatementRule() {
		return getSTWhileStatementAccess().getRule();
	}
	
	//STRepeatStatement:
	//    'REPEAT'
	//    statements+=STStatement*
	//    'UNTIL' condition=STExpression
	//    'END_REPEAT';
	public STCoreGrammarAccess.STRepeatStatementElements getSTRepeatStatementAccess() {
		return gaSTCore.getSTRepeatStatementAccess();
	}
	
	public ParserRule getSTRepeatStatementRule() {
		return getSTRepeatStatementAccess().getRule();
	}
	
	//STExpression returns STExpression:
	//    STSubrangeExpression;
	public STCoreGrammarAccess.STExpressionElements getSTExpressionAccess() {
		return gaSTCore.getSTExpressionAccess();
	}
	
	public ParserRule getSTExpressionRule() {
		return getSTExpressionAccess().getRule();
	}
	
	//enum SubrangeOperator returns STBinaryOperator:
	//    Range='..';
	public STCoreGrammarAccess.SubrangeOperatorElements getSubrangeOperatorAccess() {
		return gaSTCore.getSubrangeOperatorAccess();
	}
	
	public EnumRule getSubrangeOperatorRule() {
		return getSubrangeOperatorAccess().getRule();
	}
	
	//STSubrangeExpression returns STExpression:
	//    STOrExpression (({STBinaryExpression.left=current} op=SubrangeOperator) right=STOrExpression)*;
	public STCoreGrammarAccess.STSubrangeExpressionElements getSTSubrangeExpressionAccess() {
		return gaSTCore.getSTSubrangeExpressionAccess();
	}
	
	public ParserRule getSTSubrangeExpressionRule() {
		return getSTSubrangeExpressionAccess().getRule();
	}
	
	//enum OrOperator returns STBinaryOperator:
	//    OR;
	public STCoreGrammarAccess.OrOperatorElements getOrOperatorAccess() {
		return gaSTCore.getOrOperatorAccess();
	}
	
	public EnumRule getOrOperatorRule() {
		return getOrOperatorAccess().getRule();
	}
	
	//STOrExpression returns STExpression:
	//    STXorExpression (({STBinaryExpression.left=current} op=OrOperator) right=STXorExpression)*;
	public STCoreGrammarAccess.STOrExpressionElements getSTOrExpressionAccess() {
		return gaSTCore.getSTOrExpressionAccess();
	}
	
	public ParserRule getSTOrExpressionRule() {
		return getSTOrExpressionAccess().getRule();
	}
	
	//enum XorOperator returns STBinaryOperator:
	//    XOR;
	public STCoreGrammarAccess.XorOperatorElements getXorOperatorAccess() {
		return gaSTCore.getXorOperatorAccess();
	}
	
	public EnumRule getXorOperatorRule() {
		return getXorOperatorAccess().getRule();
	}
	
	//STXorExpression returns STExpression:
	//    STAndExpression (({STBinaryExpression.left=current} op=XorOperator) right=STAndExpression)*;
	public STCoreGrammarAccess.STXorExpressionElements getSTXorExpressionAccess() {
		return gaSTCore.getSTXorExpressionAccess();
	}
	
	public ParserRule getSTXorExpressionRule() {
		return getSTXorExpressionAccess().getRule();
	}
	
	//enum AndOperator returns STBinaryOperator:
	//    AND | AMPERSAND='&';
	public STCoreGrammarAccess.AndOperatorElements getAndOperatorAccess() {
		return gaSTCore.getAndOperatorAccess();
	}
	
	public EnumRule getAndOperatorRule() {
		return getAndOperatorAccess().getRule();
	}
	
	//STAndExpression returns STExpression:
	//    STEqualityExpression (({STBinaryExpression.left=current} op=AndOperator) right=STEqualityExpression)*;
	public STCoreGrammarAccess.STAndExpressionElements getSTAndExpressionAccess() {
		return gaSTCore.getSTAndExpressionAccess();
	}
	
	public ParserRule getSTAndExpressionRule() {
		return getSTAndExpressionAccess().getRule();
	}
	
	//enum EqualityOperator returns STBinaryOperator:
	//    EQ='=' | NE='<>';
	public STCoreGrammarAccess.EqualityOperatorElements getEqualityOperatorAccess() {
		return gaSTCore.getEqualityOperatorAccess();
	}
	
	public EnumRule getEqualityOperatorRule() {
		return getEqualityOperatorAccess().getRule();
	}
	
	//STEqualityExpression returns STExpression:
	//    STComparisonExpression (({STBinaryExpression.left=current} op=EqualityOperator) right=STComparisonExpression)*;
	public STCoreGrammarAccess.STEqualityExpressionElements getSTEqualityExpressionAccess() {
		return gaSTCore.getSTEqualityExpressionAccess();
	}
	
	public ParserRule getSTEqualityExpressionRule() {
		return getSTEqualityExpressionAccess().getRule();
	}
	
	//enum CompareOperator returns STBinaryOperator:
	//    LT='<' | LE='<=' | GT='>' | GE='>=';
	public STCoreGrammarAccess.CompareOperatorElements getCompareOperatorAccess() {
		return gaSTCore.getCompareOperatorAccess();
	}
	
	public EnumRule getCompareOperatorRule() {
		return getCompareOperatorAccess().getRule();
	}
	
	//STComparisonExpression returns STExpression:
	//    STAddSubExpression (({STBinaryExpression.left=current} op=CompareOperator) right=STAddSubExpression)*;
	public STCoreGrammarAccess.STComparisonExpressionElements getSTComparisonExpressionAccess() {
		return gaSTCore.getSTComparisonExpressionAccess();
	}
	
	public ParserRule getSTComparisonExpressionRule() {
		return getSTComparisonExpressionAccess().getRule();
	}
	
	//enum AddSubOperator returns STBinaryOperator:
	//    ADD='+' | SUB='-';
	public STCoreGrammarAccess.AddSubOperatorElements getAddSubOperatorAccess() {
		return gaSTCore.getAddSubOperatorAccess();
	}
	
	public EnumRule getAddSubOperatorRule() {
		return getAddSubOperatorAccess().getRule();
	}
	
	//STAddSubExpression returns STExpression:
	//    STMulDivModExpression (({STBinaryExpression.left=current} op=AddSubOperator) right=STMulDivModExpression)*;
	public STCoreGrammarAccess.STAddSubExpressionElements getSTAddSubExpressionAccess() {
		return gaSTCore.getSTAddSubExpressionAccess();
	}
	
	public ParserRule getSTAddSubExpressionRule() {
		return getSTAddSubExpressionAccess().getRule();
	}
	
	//enum MulDivModOperator returns STBinaryOperator:
	//    MUL='*' | DIV='/' | MOD;
	public STCoreGrammarAccess.MulDivModOperatorElements getMulDivModOperatorAccess() {
		return gaSTCore.getMulDivModOperatorAccess();
	}
	
	public EnumRule getMulDivModOperatorRule() {
		return getMulDivModOperatorAccess().getRule();
	}
	
	//STMulDivModExpression returns STExpression:
	//    STPowerExpression (({STBinaryExpression.left=current} op=MulDivModOperator) right=STPowerExpression)*;
	public STCoreGrammarAccess.STMulDivModExpressionElements getSTMulDivModExpressionAccess() {
		return gaSTCore.getSTMulDivModExpressionAccess();
	}
	
	public ParserRule getSTMulDivModExpressionRule() {
		return getSTMulDivModExpressionAccess().getRule();
	}
	
	//enum PowerOperator returns STBinaryOperator:
	//    POWER='**';
	public STCoreGrammarAccess.PowerOperatorElements getPowerOperatorAccess() {
		return gaSTCore.getPowerOperatorAccess();
	}
	
	public EnumRule getPowerOperatorRule() {
		return getPowerOperatorAccess().getRule();
	}
	
	//STPowerExpression returns STExpression:
	//    STUnaryExpression (({STBinaryExpression.left=current} op=PowerOperator) right=STUnaryExpression)*;
	public STCoreGrammarAccess.STPowerExpressionElements getSTPowerExpressionAccess() {
		return gaSTCore.getSTPowerExpressionAccess();
	}
	
	public ParserRule getSTPowerExpressionRule() {
		return getSTPowerExpressionAccess().getRule();
	}
	
	//enum UnaryOperator returns STUnaryOperator:
	//    MINUS='-' | PLUS='+' | NOT;
	public STCoreGrammarAccess.UnaryOperatorElements getUnaryOperatorAccess() {
		return gaSTCore.getUnaryOperatorAccess();
	}
	
	public EnumRule getUnaryOperatorRule() {
		return getUnaryOperatorAccess().getRule();
	}
	
	//STUnaryExpression returns STExpression:
	//    STAccessExpression | STLiteralExpressions | => STSignedLiteralExpressions | ({STUnaryExpression} op=UnaryOperator
	//    expression=STUnaryExpression);
	public STCoreGrammarAccess.STUnaryExpressionElements getSTUnaryExpressionAccess() {
		return gaSTCore.getSTUnaryExpressionAccess();
	}
	
	public ParserRule getSTUnaryExpressionRule() {
		return getSTUnaryExpressionAccess().getRule();
	}
	
	//STAccessExpression returns STExpression:
	//    STPrimaryExpression (({STMemberAccessExpression.receiver=current} '.' member=(STFeatureExpression |
	//    STMultibitPartialExpression)) |
	//    ({STArrayAccessExpression.receiver=current} '[' index+=STExpression (',' index+=STExpression)* ']'))*;
	public STCoreGrammarAccess.STAccessExpressionElements getSTAccessExpressionAccess() {
		return gaSTCore.getSTAccessExpressionAccess();
	}
	
	public ParserRule getSTAccessExpressionRule() {
		return getSTAccessExpressionAccess().getRule();
	}
	
	//STPrimaryExpression returns STExpression:
	//    '(' STExpression ')' | STFeatureExpression | STBuiltinFeatureExpression;
	public STCoreGrammarAccess.STPrimaryExpressionElements getSTPrimaryExpressionAccess() {
		return gaSTCore.getSTPrimaryExpressionAccess();
	}
	
	public ParserRule getSTPrimaryExpressionRule() {
		return getSTPrimaryExpressionAccess().getRule();
	}
	
	//STFeatureExpression returns STExpression:
	//    {STFeatureExpression} feature=[libraryElement::INamedElement|STFeatureName]
	//    (call?='('
	//    (parameters+=STCallArgument (',' parameters+=STCallArgument)*)?
	//    ')')?;
	public STCoreGrammarAccess.STFeatureExpressionElements getSTFeatureExpressionAccess() {
		return gaSTCore.getSTFeatureExpressionAccess();
	}
	
	public ParserRule getSTFeatureExpressionRule() {
		return getSTFeatureExpressionAccess().getRule();
	}
	
	//STFeatureName:
	//    QualifiedName | 'LT' | 'AND' | 'OR' | 'XOR' | 'MOD' | 'D' | 'DT' | 'LD';
	public STCoreGrammarAccess.STFeatureNameElements getSTFeatureNameAccess() {
		return gaSTCore.getSTFeatureNameAccess();
	}
	
	public ParserRule getSTFeatureNameRule() {
		return getSTFeatureNameAccess().getRule();
	}
	
	//STBuiltinFeatureExpression returns STExpression:
	//    {STBuiltinFeatureExpression} feature=STBuiltinFeature (call?='(' (parameters+=STCallArgument
	//    (',' parameters+=STCallArgument)*)? ')')?;
	public STCoreGrammarAccess.STBuiltinFeatureExpressionElements getSTBuiltinFeatureExpressionAccess() {
		return gaSTCore.getSTBuiltinFeatureExpressionAccess();
	}
	
	public ParserRule getSTBuiltinFeatureExpressionRule() {
		return getSTBuiltinFeatureExpressionAccess().getRule();
	}
	
	//enum STBuiltinFeature:
	//    THIS;
	public STCoreGrammarAccess.STBuiltinFeatureElements getSTBuiltinFeatureAccess() {
		return gaSTCore.getSTBuiltinFeatureAccess();
	}
	
	public EnumRule getSTBuiltinFeatureRule() {
		return getSTBuiltinFeatureAccess().getRule();
	}
	
	//enum STMultiBitAccessSpecifier:
	//    L='%L' | D='%D' | W='%W' | B='%B' | X='%X';
	public STCoreGrammarAccess.STMultiBitAccessSpecifierElements getSTMultiBitAccessSpecifierAccess() {
		return gaSTCore.getSTMultiBitAccessSpecifierAccess();
	}
	
	public EnumRule getSTMultiBitAccessSpecifierRule() {
		return getSTMultiBitAccessSpecifierAccess().getRule();
	}
	
	//STMultibitPartialExpression returns STExpression:
	//    {STMultibitPartialExpression} (specifier=STMultiBitAccessSpecifier)? (index=INT | ('(' expression=STExpression ')'))
	//;
	public STCoreGrammarAccess.STMultibitPartialExpressionElements getSTMultibitPartialExpressionAccess() {
		return gaSTCore.getSTMultibitPartialExpressionAccess();
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
	public STCoreGrammarAccess.STLiteralExpressionsElements getSTLiteralExpressionsAccess() {
		return gaSTCore.getSTLiteralExpressionsAccess();
	}
	
	public ParserRule getSTLiteralExpressionsRule() {
		return getSTLiteralExpressionsAccess().getRule();
	}
	
	//STSignedLiteralExpressions returns STExpression:
	//    STSignedNumericLiteral;
	public STCoreGrammarAccess.STSignedLiteralExpressionsElements getSTSignedLiteralExpressionsAccess() {
		return gaSTCore.getSTSignedLiteralExpressionsAccess();
	}
	
	public ParserRule getSTSignedLiteralExpressionsRule() {
		return getSTSignedLiteralExpressionsAccess().getRule();
	}
	
	//STNumericLiteralType:
	//    STAnyBitType | STAnyNumType;
	public STCoreGrammarAccess.STNumericLiteralTypeElements getSTNumericLiteralTypeAccess() {
		return gaSTCore.getSTNumericLiteralTypeAccess();
	}
	
	public ParserRule getSTNumericLiteralTypeRule() {
		return getSTNumericLiteralTypeAccess().getRule();
	}
	
	//STNumericLiteral:
	//    (type=[datatype::DataType|STNumericLiteralType] '#' value=SignedNumeric) |
	//    ((type=[datatype::DataType|STNumericLiteralType] '#')?
	//    value=Numeric);
	public STCoreGrammarAccess.STNumericLiteralElements getSTNumericLiteralAccess() {
		return gaSTCore.getSTNumericLiteralAccess();
	}
	
	public ParserRule getSTNumericLiteralRule() {
		return getSTNumericLiteralAccess().getRule();
	}
	
	//STSignedNumericLiteral returns STNumericLiteral:
	//    value=SignedNumeric;
	public STCoreGrammarAccess.STSignedNumericLiteralElements getSTSignedNumericLiteralAccess() {
		return gaSTCore.getSTSignedNumericLiteralAccess();
	}
	
	public ParserRule getSTSignedNumericLiteralRule() {
		return getSTSignedNumericLiteralAccess().getRule();
	}
	
	//STDateLiteralType:
	//    STDateType |
	//    'D' |
	//    'LD';
	public STCoreGrammarAccess.STDateLiteralTypeElements getSTDateLiteralTypeAccess() {
		return gaSTCore.getSTDateLiteralTypeAccess();
	}
	
	public ParserRule getSTDateLiteralTypeRule() {
		return getSTDateLiteralTypeAccess().getRule();
	}
	
	//STDateLiteral:
	//    type=[datatype::DataType|STDateLiteralType] '#' value=Date;
	public STCoreGrammarAccess.STDateLiteralElements getSTDateLiteralAccess() {
		return gaSTCore.getSTDateLiteralAccess();
	}
	
	public ParserRule getSTDateLiteralRule() {
		return getSTDateLiteralAccess().getRule();
	}
	
	//STTimeLiteralType:
	//    STAnyDurationType |
	//    'T' |
	//    'LT';
	public STCoreGrammarAccess.STTimeLiteralTypeElements getSTTimeLiteralTypeAccess() {
		return gaSTCore.getSTTimeLiteralTypeAccess();
	}
	
	public ParserRule getSTTimeLiteralTypeRule() {
		return getSTTimeLiteralTypeAccess().getRule();
	}
	
	//STTimeLiteral:
	//    type=[datatype::DataType|STTimeLiteralType] '#' value=Time;
	public STCoreGrammarAccess.STTimeLiteralElements getSTTimeLiteralAccess() {
		return gaSTCore.getSTTimeLiteralAccess();
	}
	
	public ParserRule getSTTimeLiteralRule() {
		return getSTTimeLiteralAccess().getRule();
	}
	
	//STTimeOfDayLiteral:
	//    type=[datatype::DataType|STTimeOfDayType] '#' value=TimeOfDay;
	public STCoreGrammarAccess.STTimeOfDayLiteralElements getSTTimeOfDayLiteralAccess() {
		return gaSTCore.getSTTimeOfDayLiteralAccess();
	}
	
	public ParserRule getSTTimeOfDayLiteralRule() {
		return getSTTimeOfDayLiteralAccess().getRule();
	}
	
	//STDateAndTimeLiteral:
	//    type=[datatype::DataType|STDateAndTimeType] '#' value=DateAndTime;
	public STCoreGrammarAccess.STDateAndTimeLiteralElements getSTDateAndTimeLiteralAccess() {
		return gaSTCore.getSTDateAndTimeLiteralAccess();
	}
	
	public ParserRule getSTDateAndTimeLiteralRule() {
		return getSTDateAndTimeLiteralAccess().getRule();
	}
	
	//STStringLiteral:
	//    (type=[datatype::DataType|STAnyCharsType] '#')? value=STRING;
	public STCoreGrammarAccess.STStringLiteralElements getSTStringLiteralAccess() {
		return gaSTCore.getSTStringLiteralAccess();
	}
	
	public ParserRule getSTStringLiteralRule() {
		return getSTStringLiteralAccess().getRule();
	}
	
	//STEnumLiteral:
	//    value=[datatype::EnumeratedValue|EnumValue];
	public STCoreGrammarAccess.STEnumLiteralElements getSTEnumLiteralAccess() {
		return gaSTCore.getSTEnumLiteralAccess();
	}
	
	public ParserRule getSTEnumLiteralRule() {
		return getSTEnumLiteralAccess().getRule();
	}
	
	//EnumValue:
	//    QualifiedName '#' ID;
	public STCoreGrammarAccess.EnumValueElements getEnumValueAccess() {
		return gaSTCore.getEnumValueAccess();
	}
	
	public ParserRule getEnumValueRule() {
		return getEnumValueAccess().getRule();
	}
	
	//STAnyType:
	//    QualifiedName | STAnyBuiltinType;
	public STCoreGrammarAccess.STAnyTypeElements getSTAnyTypeAccess() {
		return gaSTCore.getSTAnyTypeAccess();
	}
	
	public ParserRule getSTAnyTypeRule() {
		return getSTAnyTypeAccess().getRule();
	}
	
	//STAnyBuiltinType:
	//    STAnyBitType | STAnyNumType | STAnyDurationType | STAnyDateType | STAnyCharsType;
	public STCoreGrammarAccess.STAnyBuiltinTypeElements getSTAnyBuiltinTypeAccess() {
		return gaSTCore.getSTAnyBuiltinTypeAccess();
	}
	
	public ParserRule getSTAnyBuiltinTypeRule() {
		return getSTAnyBuiltinTypeAccess().getRule();
	}
	
	//STAnyBitType:
	//    'BOOL' | 'BYTE' | 'WORD' | 'DWORD' | 'LWORD';
	public STCoreGrammarAccess.STAnyBitTypeElements getSTAnyBitTypeAccess() {
		return gaSTCore.getSTAnyBitTypeAccess();
	}
	
	public ParserRule getSTAnyBitTypeRule() {
		return getSTAnyBitTypeAccess().getRule();
	}
	
	//STAnyNumType:
	//    'SINT' | 'INT' | 'DINT' | 'LINT' | 'USINT' | 'UINT' | 'UDINT' | 'ULINT' | 'REAL' | 'LREAL';
	public STCoreGrammarAccess.STAnyNumTypeElements getSTAnyNumTypeAccess() {
		return gaSTCore.getSTAnyNumTypeAccess();
	}
	
	public ParserRule getSTAnyNumTypeRule() {
		return getSTAnyNumTypeAccess().getRule();
	}
	
	//STAnyDurationType:
	//    'TIME' | 'LTIME';
	public STCoreGrammarAccess.STAnyDurationTypeElements getSTAnyDurationTypeAccess() {
		return gaSTCore.getSTAnyDurationTypeAccess();
	}
	
	public ParserRule getSTAnyDurationTypeRule() {
		return getSTAnyDurationTypeAccess().getRule();
	}
	
	//STAnyDateType:
	//    STDateType | STTimeOfDayType | STDateAndTimeType;
	public STCoreGrammarAccess.STAnyDateTypeElements getSTAnyDateTypeAccess() {
		return gaSTCore.getSTAnyDateTypeAccess();
	}
	
	public ParserRule getSTAnyDateTypeRule() {
		return getSTAnyDateTypeAccess().getRule();
	}
	
	//STDateType:
	//    'DATE' | 'LDATE';
	public STCoreGrammarAccess.STDateTypeElements getSTDateTypeAccess() {
		return gaSTCore.getSTDateTypeAccess();
	}
	
	public ParserRule getSTDateTypeRule() {
		return getSTDateTypeAccess().getRule();
	}
	
	//STTimeOfDayType:
	//    'TIME_OF_DAY' |
	//    'LTIME_OF_DAY' |
	//    'TOD' |
	//    'LTOD';
	public STCoreGrammarAccess.STTimeOfDayTypeElements getSTTimeOfDayTypeAccess() {
		return gaSTCore.getSTTimeOfDayTypeAccess();
	}
	
	public ParserRule getSTTimeOfDayTypeRule() {
		return getSTTimeOfDayTypeAccess().getRule();
	}
	
	//STDateAndTimeType:
	//    'DATE_AND_TIME' |
	//    'LDATE_AND_TIME' |
	//    'DT' |
	//    'LDT';
	public STCoreGrammarAccess.STDateAndTimeTypeElements getSTDateAndTimeTypeAccess() {
		return gaSTCore.getSTDateAndTimeTypeAccess();
	}
	
	public ParserRule getSTDateAndTimeTypeRule() {
		return getSTDateAndTimeTypeAccess().getRule();
	}
	
	//STAnyCharsType:
	//    'STRING' | 'WSTRING' | 'CHAR' | 'WCHAR';
	public STCoreGrammarAccess.STAnyCharsTypeElements getSTAnyCharsTypeAccess() {
		return gaSTCore.getSTAnyCharsTypeAccess();
	}
	
	public ParserRule getSTAnyCharsTypeRule() {
		return getSTAnyCharsTypeAccess().getRule();
	}
	
	//QualifiedName:
	//    ID ('::' ID)*;
	public STCoreGrammarAccess.QualifiedNameElements getQualifiedNameAccess() {
		return gaSTCore.getQualifiedNameAccess();
	}
	
	public ParserRule getQualifiedNameRule() {
		return getQualifiedNameAccess().getRule();
	}
	
	//QualifiedNameWithWildcard:
	//    QualifiedName '::*'?;
	public STCoreGrammarAccess.QualifiedNameWithWildcardElements getQualifiedNameWithWildcardAccess() {
		return gaSTCore.getQualifiedNameWithWildcardAccess();
	}
	
	public ParserRule getQualifiedNameWithWildcardRule() {
		return getQualifiedNameWithWildcardAccess().getRule();
	}
	
	//Numeric returns ecore::EJavaObject:
	//    'TRUE' | 'FALSE' | Number | NON_DECIMAL;
	public STCoreGrammarAccess.NumericElements getNumericAccess() {
		return gaSTCore.getNumericAccess();
	}
	
	public ParserRule getNumericRule() {
		return getNumericAccess().getRule();
	}
	
	//Number hidden():
	//    INT ('.' (INT | DECIMAL))?;
	public STCoreGrammarAccess.NumberElements getNumberAccess() {
		return gaSTCore.getNumberAccess();
	}
	
	public ParserRule getNumberRule() {
		return getNumberAccess().getRule();
	}
	
	//SignedNumeric returns ecore::EJavaObject:
	//    SignedNumber;
	public STCoreGrammarAccess.SignedNumericElements getSignedNumericAccess() {
		return gaSTCore.getSignedNumericAccess();
	}
	
	public ParserRule getSignedNumericRule() {
		return getSignedNumericAccess().getRule();
	}
	
	//SignedNumber hidden():
	//    ('+' | '-') INT ('.' (INT | DECIMAL))?;
	public STCoreGrammarAccess.SignedNumberElements getSignedNumberAccess() {
		return gaSTCore.getSignedNumberAccess();
	}
	
	public ParserRule getSignedNumberRule() {
		return getSignedNumberAccess().getRule();
	}
	
	//Time returns STTime hidden():
	//    ('+' | '-')? TIME_VALUE;
	public STCoreGrammarAccess.TimeElements getTimeAccess() {
		return gaSTCore.getTimeAccess();
	}
	
	public ParserRule getTimeRule() {
		return getTimeAccess().getRule();
	}
	
	//Date returns STDate:
	//    INT '-' INT '-' INT;
	public STCoreGrammarAccess.DateElements getDateAccess() {
		return gaSTCore.getDateAccess();
	}
	
	public ParserRule getDateRule() {
		return getDateAccess().getRule();
	}
	
	//DateAndTime returns STDateAndTime:
	//    INT '-' INT '-' INT '-' INT ':' INT ':' INT ('.' INT)?;
	public STCoreGrammarAccess.DateAndTimeElements getDateAndTimeAccess() {
		return gaSTCore.getDateAndTimeAccess();
	}
	
	public ParserRule getDateAndTimeRule() {
		return getDateAndTimeAccess().getRule();
	}
	
	//TimeOfDay returns STTimeOfDay:
	//    INT ':' INT ':' INT ('.' INT)?;
	public STCoreGrammarAccess.TimeOfDayElements getTimeOfDayAccess() {
		return gaSTCore.getTimeOfDayAccess();
	}
	
	public ParserRule getTimeOfDayRule() {
		return getTimeOfDayAccess().getRule();
	}
	
	//enum STAccessSpecifier:
	//    PROTECTED='PROTECTED' | PUBLIC='PUBLIC' | PRIVATE='PRIVATE' | INTERNAL='INTERNAL';
	public STCoreGrammarAccess.STAccessSpecifierElements getSTAccessSpecifierAccess() {
		return gaSTCore.getSTAccessSpecifierAccess();
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
	public STCoreGrammarAccess.RESERVED_KEYWORDSElements getRESERVED_KEYWORDSAccess() {
		return gaSTCore.getRESERVED_KEYWORDSAccess();
	}
	
	public ParserRule getRESERVED_KEYWORDSRule() {
		return getRESERVED_KEYWORDSAccess().getRule();
	}
	
	//terminal fragment HEX_DIGIT:
	//    '0'..'9' | 'a'..'f' | 'A'..'F' | '_';
	public TerminalRule getHEX_DIGITRule() {
		return gaSTCore.getHEX_DIGITRule();
	}
	
	//terminal NON_DECIMAL:
	//    ('2#' | '8#' | '16#') HEX_DIGIT+;
	public TerminalRule getNON_DECIMALRule() {
		return gaSTCore.getNON_DECIMALRule();
	}
	
	//terminal INT returns ecore::EBigInteger:
	//    '0'..'9' ('0'..'9' | '_')*;
	public TerminalRule getINTRule() {
		return gaSTCore.getINTRule();
	}
	
	//terminal DECIMAL:
	//    INT (('e' | 'E') ('+' | '-')? INT)?;
	public TerminalRule getDECIMALRule() {
		return gaSTCore.getDECIMALRule();
	}
	
	//terminal TIME_VALUE:
	//    (TIME_PART ('_')?)+;
	public TerminalRule getTIME_VALUERule() {
		return gaSTCore.getTIME_VALUERule();
	}
	
	//terminal fragment TIME_PART:
	//    INT (TIME_DAYS | TIME_HOURS | TIME_MINUTES | TIME_SECONDS | TIME_MILLIS | TIME_MICROS | TIME_NANOS);
	public TerminalRule getTIME_PARTRule() {
		return gaSTCore.getTIME_PARTRule();
	}
	
	//terminal fragment TIME_DAYS:
	//    'D' | 'd';
	public TerminalRule getTIME_DAYSRule() {
		return gaSTCore.getTIME_DAYSRule();
	}
	
	//terminal fragment TIME_HOURS:
	//    'H' | 'h';
	public TerminalRule getTIME_HOURSRule() {
		return gaSTCore.getTIME_HOURSRule();
	}
	
	//terminal fragment TIME_MINUTES:
	//    'M' | 'm';
	public TerminalRule getTIME_MINUTESRule() {
		return gaSTCore.getTIME_MINUTESRule();
	}
	
	//terminal fragment TIME_SECONDS:
	//    'S' | 's';
	public TerminalRule getTIME_SECONDSRule() {
		return gaSTCore.getTIME_SECONDSRule();
	}
	
	//terminal fragment TIME_MILLIS:
	//    ('M' | 'm') ('S' | 's');
	public TerminalRule getTIME_MILLISRule() {
		return gaSTCore.getTIME_MILLISRule();
	}
	
	// // MS
	//terminal fragment TIME_MICROS:
	//    ('U' | 'u') ('S' | 's');
	public TerminalRule getTIME_MICROSRule() {
		return gaSTCore.getTIME_MICROSRule();
	}
	
	// // US
	//terminal fragment TIME_NANOS:
	//    ('N' | 'n') ('S' | 's');
	public TerminalRule getTIME_NANOSRule() {
		return gaSTCore.getTIME_NANOSRule();
	}
	
	// // NS
	//terminal ID:
	//    '^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaSTCore.getIDRule();
	}
	
	//terminal STRING returns STString:
	//    '"' ('$' . /* 'L'|'N'|'P'|'R'|'T'|'"'|'$' */ | !('$' | '"'))* '"' |
	//    "'" ('$' . /* "L"|"N"|"P"|"R"|"T"|"'"|"$" */ | !('$' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaSTCore.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//    '/*'->'*/' | '(*'->'*)';
	public TerminalRule getML_COMMENTRule() {
		return gaSTCore.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//    '//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaSTCore.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//    (' ' | '\t' | '\r' | '\n')+;
	public TerminalRule getWSRule() {
		return gaSTCore.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//    .;
	public TerminalRule getANY_OTHERRule() {
		return gaSTCore.getANY_OTHERRule();
	}
}
