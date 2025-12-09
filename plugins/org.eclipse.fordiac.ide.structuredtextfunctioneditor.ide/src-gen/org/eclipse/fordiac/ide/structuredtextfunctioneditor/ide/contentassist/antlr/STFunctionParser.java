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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ide.contentassist.antlr;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ide.contentassist.antlr.internal.InternalSTFunctionParser;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class STFunctionParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(STFunctionGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, STFunctionGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getSTFunctionAccess().getVarDeclarationsAlternatives_4_0(), "rule__STFunction__VarDeclarationsAlternatives_4_0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getAlternatives_4_1(), "rule__STVarDeclaration__Alternatives_4_1");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getAlternatives_1_1(), "rule__STTypeDeclaration__Alternatives_1_1");
			builder.put(grammarAccess.getSTInitializerExpressionAccess().getAlternatives(), "rule__STInitializerExpression__Alternatives");
			builder.put(grammarAccess.getSTArrayInitElementAccess().getAlternatives(), "rule__STArrayInitElement__Alternatives");
			builder.put(grammarAccess.getSTStatementAccess().getAlternatives(), "rule__STStatement__Alternatives");
			builder.put(grammarAccess.getSTStatementAccess().getAlternatives_0_0(), "rule__STStatement__Alternatives_0_0");
			builder.put(grammarAccess.getSTCallArgumentAccess().getAlternatives(), "rule__STCallArgument__Alternatives");
			builder.put(grammarAccess.getSTUnaryExpressionAccess().getAlternatives(), "rule__STUnaryExpression__Alternatives");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getAlternatives_1(), "rule__STAccessExpression__Alternatives_1");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getMemberAlternatives_1_0_2_0(), "rule__STAccessExpression__MemberAlternatives_1_0_2_0");
			builder.put(grammarAccess.getSTPrimaryExpressionAccess().getAlternatives(), "rule__STPrimaryExpression__Alternatives");
			builder.put(grammarAccess.getSTFeatureNameAccess().getAlternatives(), "rule__STFeatureName__Alternatives");
			builder.put(grammarAccess.getSTMultibitPartialExpressionAccess().getAlternatives_2(), "rule__STMultibitPartialExpression__Alternatives_2");
			builder.put(grammarAccess.getSTLiteralExpressionsAccess().getAlternatives(), "rule__STLiteralExpressions__Alternatives");
			builder.put(grammarAccess.getSTNumericLiteralTypeAccess().getAlternatives(), "rule__STNumericLiteralType__Alternatives");
			builder.put(grammarAccess.getSTNumericLiteralAccess().getAlternatives(), "rule__STNumericLiteral__Alternatives");
			builder.put(grammarAccess.getSTDateLiteralTypeAccess().getAlternatives(), "rule__STDateLiteralType__Alternatives");
			builder.put(grammarAccess.getSTTimeLiteralTypeAccess().getAlternatives(), "rule__STTimeLiteralType__Alternatives");
			builder.put(grammarAccess.getSTAnyTypeAccess().getAlternatives(), "rule__STAnyType__Alternatives");
			builder.put(grammarAccess.getSTAnyBuiltinTypeAccess().getAlternatives(), "rule__STAnyBuiltinType__Alternatives");
			builder.put(grammarAccess.getSTAnyBitTypeAccess().getAlternatives(), "rule__STAnyBitType__Alternatives");
			builder.put(grammarAccess.getSTAnyNumTypeAccess().getAlternatives(), "rule__STAnyNumType__Alternatives");
			builder.put(grammarAccess.getSTAnyDurationTypeAccess().getAlternatives(), "rule__STAnyDurationType__Alternatives");
			builder.put(grammarAccess.getSTAnyDateTypeAccess().getAlternatives(), "rule__STAnyDateType__Alternatives");
			builder.put(grammarAccess.getSTDateTypeAccess().getAlternatives(), "rule__STDateType__Alternatives");
			builder.put(grammarAccess.getSTTimeOfDayTypeAccess().getAlternatives(), "rule__STTimeOfDayType__Alternatives");
			builder.put(grammarAccess.getSTDateAndTimeTypeAccess().getAlternatives(), "rule__STDateAndTimeType__Alternatives");
			builder.put(grammarAccess.getSTAnyCharsTypeAccess().getAlternatives(), "rule__STAnyCharsType__Alternatives");
			builder.put(grammarAccess.getNumericAccess().getAlternatives(), "rule__Numeric__Alternatives");
			builder.put(grammarAccess.getNumberAccess().getAlternatives_1_1(), "rule__Number__Alternatives_1_1");
			builder.put(grammarAccess.getSignedNumberAccess().getAlternatives_0(), "rule__SignedNumber__Alternatives_0");
			builder.put(grammarAccess.getSignedNumberAccess().getAlternatives_2_1(), "rule__SignedNumber__Alternatives_2_1");
			builder.put(grammarAccess.getTimeAccess().getAlternatives_0(), "rule__Time__Alternatives_0");
			builder.put(grammarAccess.getRESERVED_KEYWORDSAccess().getAlternatives(), "rule__RESERVED_KEYWORDS__Alternatives");
			builder.put(grammarAccess.getAndOperatorAccess().getAlternatives(), "rule__AndOperator__Alternatives");
			builder.put(grammarAccess.getEqualityOperatorAccess().getAlternatives(), "rule__EqualityOperator__Alternatives");
			builder.put(grammarAccess.getCompareOperatorAccess().getAlternatives(), "rule__CompareOperator__Alternatives");
			builder.put(grammarAccess.getAddSubOperatorAccess().getAlternatives(), "rule__AddSubOperator__Alternatives");
			builder.put(grammarAccess.getMulDivModOperatorAccess().getAlternatives(), "rule__MulDivModOperator__Alternatives");
			builder.put(grammarAccess.getUnaryOperatorAccess().getAlternatives(), "rule__UnaryOperator__Alternatives");
			builder.put(grammarAccess.getSTMultiBitAccessSpecifierAccess().getAlternatives(), "rule__STMultiBitAccessSpecifier__Alternatives");
			builder.put(grammarAccess.getSTAccessSpecifierAccess().getAlternatives(), "rule__STAccessSpecifier__Alternatives");
			builder.put(grammarAccess.getSTFunctionSourceAccess().getGroup(), "rule__STFunctionSource__Group__0");
			builder.put(grammarAccess.getSTFunctionSourceAccess().getGroup_1(), "rule__STFunctionSource__Group_1__0");
			builder.put(grammarAccess.getSTFunctionAccess().getGroup(), "rule__STFunction__Group__0");
			builder.put(grammarAccess.getSTFunctionAccess().getGroup_3(), "rule__STFunction__Group_3__0");
			builder.put(grammarAccess.getSTCoreSourceAccess().getGroup(), "rule__STCoreSource__Group__0");
			builder.put(grammarAccess.getSTExpressionSourceAccess().getGroup(), "rule__STExpressionSource__Group__0");
			builder.put(grammarAccess.getSTInitializerExpressionSourceAccess().getGroup(), "rule__STInitializerExpressionSource__Group__0");
			builder.put(grammarAccess.getSTImportAccess().getGroup(), "rule__STImport__Group__0");
			builder.put(grammarAccess.getSTVarDeclarationBlockAccess().getGroup(), "rule__STVarDeclarationBlock__Group__0");
			builder.put(grammarAccess.getSTVarTempDeclarationBlockAccess().getGroup(), "rule__STVarTempDeclarationBlock__Group__0");
			builder.put(grammarAccess.getSTVarInputDeclarationBlockAccess().getGroup(), "rule__STVarInputDeclarationBlock__Group__0");
			builder.put(grammarAccess.getSTVarOutputDeclarationBlockAccess().getGroup(), "rule__STVarOutputDeclarationBlock__Group__0");
			builder.put(grammarAccess.getSTVarInOutDeclarationBlockAccess().getGroup(), "rule__STVarInOutDeclarationBlock__Group__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup(), "rule__STVarDeclaration__Group__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup_2(), "rule__STVarDeclaration__Group_2__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup_4(), "rule__STVarDeclaration__Group_4__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_0(), "rule__STVarDeclaration__Group_4_1_0__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_0_2(), "rule__STVarDeclaration__Group_4_1_0_2__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_1(), "rule__STVarDeclaration__Group_4_1_1__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_1_2(), "rule__STVarDeclaration__Group_4_1_1_2__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup_6(), "rule__STVarDeclaration__Group_6__0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getGroup_7(), "rule__STVarDeclaration__Group_7__0");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getGroup(), "rule__STTypeDeclaration__Group__0");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getGroup_1(), "rule__STTypeDeclaration__Group_1__0");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_0(), "rule__STTypeDeclaration__Group_1_1_0__0");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_0_2(), "rule__STTypeDeclaration__Group_1_1_0_2__0");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_1(), "rule__STTypeDeclaration__Group_1_1_1__0");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_1_2(), "rule__STTypeDeclaration__Group_1_1_1_2__0");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getGroup_3(), "rule__STTypeDeclaration__Group_3__0");
			builder.put(grammarAccess.getSTArrayInitializerExpressionAccess().getGroup(), "rule__STArrayInitializerExpression__Group__0");
			builder.put(grammarAccess.getSTArrayInitializerExpressionAccess().getGroup_2(), "rule__STArrayInitializerExpression__Group_2__0");
			builder.put(grammarAccess.getSTRepeatArrayInitElementAccess().getGroup(), "rule__STRepeatArrayInitElement__Group__0");
			builder.put(grammarAccess.getSTRepeatArrayInitElementAccess().getGroup_3(), "rule__STRepeatArrayInitElement__Group_3__0");
			builder.put(grammarAccess.getSTStructInitializerExpressionAccess().getGroup(), "rule__STStructInitializerExpression__Group__0");
			builder.put(grammarAccess.getSTStructInitializerExpressionAccess().getGroup_0(), "rule__STStructInitializerExpression__Group_0__0");
			builder.put(grammarAccess.getSTStructInitializerExpressionAccess().getGroup_3(), "rule__STStructInitializerExpression__Group_3__0");
			builder.put(grammarAccess.getSTStructInitElementAccess().getGroup(), "rule__STStructInitElement__Group__0");
			builder.put(grammarAccess.getSTPragmaAccess().getGroup(), "rule__STPragma__Group__0");
			builder.put(grammarAccess.getSTPragmaAccess().getGroup_3(), "rule__STPragma__Group_3__0");
			builder.put(grammarAccess.getSTAttributeAccess().getGroup(), "rule__STAttribute__Group__0");
			builder.put(grammarAccess.getSTStatementAccess().getGroup_0(), "rule__STStatement__Group_0__0");
			builder.put(grammarAccess.getSTStatementAccess().getGroup_0_0_6(), "rule__STStatement__Group_0_0_6__0");
			builder.put(grammarAccess.getSTStatementAccess().getGroup_0_0_7(), "rule__STStatement__Group_0_0_7__0");
			builder.put(grammarAccess.getSTStatementAccess().getGroup_0_0_8(), "rule__STStatement__Group_0_0_8__0");
			builder.put(grammarAccess.getSTStatementAccess().getGroup_1(), "rule__STStatement__Group_1__0");
			builder.put(grammarAccess.getSTAssignmentAccess().getGroup(), "rule__STAssignment__Group__0");
			builder.put(grammarAccess.getSTAssignmentAccess().getGroup_1(), "rule__STAssignment__Group_1__0");
			builder.put(grammarAccess.getSTCallNamedInputArgumentAccess().getGroup(), "rule__STCallNamedInputArgument__Group__0");
			builder.put(grammarAccess.getSTCallNamedOutputArgumentAccess().getGroup(), "rule__STCallNamedOutputArgument__Group__0");
			builder.put(grammarAccess.getSTIfStatementAccess().getGroup(), "rule__STIfStatement__Group__0");
			builder.put(grammarAccess.getSTElseIfPartAccess().getGroup(), "rule__STElseIfPart__Group__0");
			builder.put(grammarAccess.getSTCaseStatementAccess().getGroup(), "rule__STCaseStatement__Group__0");
			builder.put(grammarAccess.getSTCaseCasesAccess().getGroup(), "rule__STCaseCases__Group__0");
			builder.put(grammarAccess.getSTCaseCasesAccess().getGroup_1(), "rule__STCaseCases__Group_1__0");
			builder.put(grammarAccess.getSTElsePartAccess().getGroup(), "rule__STElsePart__Group__0");
			builder.put(grammarAccess.getSTForStatementAccess().getGroup(), "rule__STForStatement__Group__0");
			builder.put(grammarAccess.getSTForStatementAccess().getGroup_6(), "rule__STForStatement__Group_6__0");
			builder.put(grammarAccess.getSTWhileStatementAccess().getGroup(), "rule__STWhileStatement__Group__0");
			builder.put(grammarAccess.getSTRepeatStatementAccess().getGroup(), "rule__STRepeatStatement__Group__0");
			builder.put(grammarAccess.getSTSubrangeExpressionAccess().getGroup(), "rule__STSubrangeExpression__Group__0");
			builder.put(grammarAccess.getSTSubrangeExpressionAccess().getGroup_1(), "rule__STSubrangeExpression__Group_1__0");
			builder.put(grammarAccess.getSTSubrangeExpressionAccess().getGroup_1_0(), "rule__STSubrangeExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTOrExpressionAccess().getGroup(), "rule__STOrExpression__Group__0");
			builder.put(grammarAccess.getSTOrExpressionAccess().getGroup_1(), "rule__STOrExpression__Group_1__0");
			builder.put(grammarAccess.getSTOrExpressionAccess().getGroup_1_0(), "rule__STOrExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTXorExpressionAccess().getGroup(), "rule__STXorExpression__Group__0");
			builder.put(grammarAccess.getSTXorExpressionAccess().getGroup_1(), "rule__STXorExpression__Group_1__0");
			builder.put(grammarAccess.getSTXorExpressionAccess().getGroup_1_0(), "rule__STXorExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTAndExpressionAccess().getGroup(), "rule__STAndExpression__Group__0");
			builder.put(grammarAccess.getSTAndExpressionAccess().getGroup_1(), "rule__STAndExpression__Group_1__0");
			builder.put(grammarAccess.getSTAndExpressionAccess().getGroup_1_0(), "rule__STAndExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTEqualityExpressionAccess().getGroup(), "rule__STEqualityExpression__Group__0");
			builder.put(grammarAccess.getSTEqualityExpressionAccess().getGroup_1(), "rule__STEqualityExpression__Group_1__0");
			builder.put(grammarAccess.getSTEqualityExpressionAccess().getGroup_1_0(), "rule__STEqualityExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTComparisonExpressionAccess().getGroup(), "rule__STComparisonExpression__Group__0");
			builder.put(grammarAccess.getSTComparisonExpressionAccess().getGroup_1(), "rule__STComparisonExpression__Group_1__0");
			builder.put(grammarAccess.getSTComparisonExpressionAccess().getGroup_1_0(), "rule__STComparisonExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTAddSubExpressionAccess().getGroup(), "rule__STAddSubExpression__Group__0");
			builder.put(grammarAccess.getSTAddSubExpressionAccess().getGroup_1(), "rule__STAddSubExpression__Group_1__0");
			builder.put(grammarAccess.getSTAddSubExpressionAccess().getGroup_1_0(), "rule__STAddSubExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTMulDivModExpressionAccess().getGroup(), "rule__STMulDivModExpression__Group__0");
			builder.put(grammarAccess.getSTMulDivModExpressionAccess().getGroup_1(), "rule__STMulDivModExpression__Group_1__0");
			builder.put(grammarAccess.getSTMulDivModExpressionAccess().getGroup_1_0(), "rule__STMulDivModExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTPowerExpressionAccess().getGroup(), "rule__STPowerExpression__Group__0");
			builder.put(grammarAccess.getSTPowerExpressionAccess().getGroup_1(), "rule__STPowerExpression__Group_1__0");
			builder.put(grammarAccess.getSTPowerExpressionAccess().getGroup_1_0(), "rule__STPowerExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTUnaryExpressionAccess().getGroup_3(), "rule__STUnaryExpression__Group_3__0");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getGroup(), "rule__STAccessExpression__Group__0");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getGroup_1_0(), "rule__STAccessExpression__Group_1_0__0");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getGroup_1_1(), "rule__STAccessExpression__Group_1_1__0");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getGroup_1_1_3(), "rule__STAccessExpression__Group_1_1_3__0");
			builder.put(grammarAccess.getSTPrimaryExpressionAccess().getGroup_0(), "rule__STPrimaryExpression__Group_0__0");
			builder.put(grammarAccess.getSTFeatureExpressionAccess().getGroup(), "rule__STFeatureExpression__Group__0");
			builder.put(grammarAccess.getSTFeatureExpressionAccess().getGroup_2(), "rule__STFeatureExpression__Group_2__0");
			builder.put(grammarAccess.getSTFeatureExpressionAccess().getGroup_2_1(), "rule__STFeatureExpression__Group_2_1__0");
			builder.put(grammarAccess.getSTFeatureExpressionAccess().getGroup_2_1_1(), "rule__STFeatureExpression__Group_2_1_1__0");
			builder.put(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup(), "rule__STBuiltinFeatureExpression__Group__0");
			builder.put(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2(), "rule__STBuiltinFeatureExpression__Group_2__0");
			builder.put(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2_1(), "rule__STBuiltinFeatureExpression__Group_2_1__0");
			builder.put(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2_1_1(), "rule__STBuiltinFeatureExpression__Group_2_1_1__0");
			builder.put(grammarAccess.getSTMultibitPartialExpressionAccess().getGroup(), "rule__STMultibitPartialExpression__Group__0");
			builder.put(grammarAccess.getSTMultibitPartialExpressionAccess().getGroup_2_1(), "rule__STMultibitPartialExpression__Group_2_1__0");
			builder.put(grammarAccess.getSTNumericLiteralAccess().getGroup_0(), "rule__STNumericLiteral__Group_0__0");
			builder.put(grammarAccess.getSTNumericLiteralAccess().getGroup_1(), "rule__STNumericLiteral__Group_1__0");
			builder.put(grammarAccess.getSTNumericLiteralAccess().getGroup_1_0(), "rule__STNumericLiteral__Group_1_0__0");
			builder.put(grammarAccess.getSTDateLiteralAccess().getGroup(), "rule__STDateLiteral__Group__0");
			builder.put(grammarAccess.getSTTimeLiteralAccess().getGroup(), "rule__STTimeLiteral__Group__0");
			builder.put(grammarAccess.getSTTimeOfDayLiteralAccess().getGroup(), "rule__STTimeOfDayLiteral__Group__0");
			builder.put(grammarAccess.getSTDateAndTimeLiteralAccess().getGroup(), "rule__STDateAndTimeLiteral__Group__0");
			builder.put(grammarAccess.getSTStringLiteralAccess().getGroup(), "rule__STStringLiteral__Group__0");
			builder.put(grammarAccess.getSTStringLiteralAccess().getGroup_0(), "rule__STStringLiteral__Group_0__0");
			builder.put(grammarAccess.getEnumValueAccess().getGroup(), "rule__EnumValue__Group__0");
			builder.put(grammarAccess.getQualifiedNameAccess().getGroup(), "rule__QualifiedName__Group__0");
			builder.put(grammarAccess.getQualifiedNameAccess().getGroup_1(), "rule__QualifiedName__Group_1__0");
			builder.put(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup(), "rule__QualifiedNameWithWildcard__Group__0");
			builder.put(grammarAccess.getNumberAccess().getGroup(), "rule__Number__Group__0");
			builder.put(grammarAccess.getNumberAccess().getGroup_1(), "rule__Number__Group_1__0");
			builder.put(grammarAccess.getSignedNumberAccess().getGroup(), "rule__SignedNumber__Group__0");
			builder.put(grammarAccess.getSignedNumberAccess().getGroup_2(), "rule__SignedNumber__Group_2__0");
			builder.put(grammarAccess.getTimeAccess().getGroup(), "rule__Time__Group__0");
			builder.put(grammarAccess.getDateAccess().getGroup(), "rule__Date__Group__0");
			builder.put(grammarAccess.getDateAndTimeAccess().getGroup(), "rule__DateAndTime__Group__0");
			builder.put(grammarAccess.getDateAndTimeAccess().getGroup_11(), "rule__DateAndTime__Group_11__0");
			builder.put(grammarAccess.getTimeOfDayAccess().getGroup(), "rule__TimeOfDay__Group__0");
			builder.put(grammarAccess.getTimeOfDayAccess().getGroup_5(), "rule__TimeOfDay__Group_5__0");
			builder.put(grammarAccess.getSTFunctionSourceAccess().getNameAssignment_1_1(), "rule__STFunctionSource__NameAssignment_1_1");
			builder.put(grammarAccess.getSTFunctionSourceAccess().getImportsAssignment_2(), "rule__STFunctionSource__ImportsAssignment_2");
			builder.put(grammarAccess.getSTFunctionSourceAccess().getFunctionsAssignment_3(), "rule__STFunctionSource__FunctionsAssignment_3");
			builder.put(grammarAccess.getSTFunctionAccess().getNameAssignment_2(), "rule__STFunction__NameAssignment_2");
			builder.put(grammarAccess.getSTFunctionAccess().getReturnTypeAssignment_3_1(), "rule__STFunction__ReturnTypeAssignment_3_1");
			builder.put(grammarAccess.getSTFunctionAccess().getVarDeclarationsAssignment_4(), "rule__STFunction__VarDeclarationsAssignment_4");
			builder.put(grammarAccess.getSTFunctionAccess().getCodeAssignment_5(), "rule__STFunction__CodeAssignment_5");
			builder.put(grammarAccess.getSTCoreSourceAccess().getStatementsAssignment_1(), "rule__STCoreSource__StatementsAssignment_1");
			builder.put(grammarAccess.getSTExpressionSourceAccess().getExpressionAssignment_1(), "rule__STExpressionSource__ExpressionAssignment_1");
			builder.put(grammarAccess.getSTInitializerExpressionSourceAccess().getInitializerExpressionAssignment_1(), "rule__STInitializerExpressionSource__InitializerExpressionAssignment_1");
			builder.put(grammarAccess.getSTImportAccess().getImportedNamespaceAssignment_1(), "rule__STImport__ImportedNamespaceAssignment_1");
			builder.put(grammarAccess.getSTVarDeclarationBlockAccess().getConstantAssignment_2(), "rule__STVarDeclarationBlock__ConstantAssignment_2");
			builder.put(grammarAccess.getSTVarDeclarationBlockAccess().getVarDeclarationsAssignment_3(), "rule__STVarDeclarationBlock__VarDeclarationsAssignment_3");
			builder.put(grammarAccess.getSTVarTempDeclarationBlockAccess().getConstantAssignment_2(), "rule__STVarTempDeclarationBlock__ConstantAssignment_2");
			builder.put(grammarAccess.getSTVarTempDeclarationBlockAccess().getVarDeclarationsAssignment_3(), "rule__STVarTempDeclarationBlock__VarDeclarationsAssignment_3");
			builder.put(grammarAccess.getSTVarInputDeclarationBlockAccess().getConstantAssignment_2(), "rule__STVarInputDeclarationBlock__ConstantAssignment_2");
			builder.put(grammarAccess.getSTVarInputDeclarationBlockAccess().getVarDeclarationsAssignment_3(), "rule__STVarInputDeclarationBlock__VarDeclarationsAssignment_3");
			builder.put(grammarAccess.getSTVarOutputDeclarationBlockAccess().getConstantAssignment_2(), "rule__STVarOutputDeclarationBlock__ConstantAssignment_2");
			builder.put(grammarAccess.getSTVarOutputDeclarationBlockAccess().getVarDeclarationsAssignment_3(), "rule__STVarOutputDeclarationBlock__VarDeclarationsAssignment_3");
			builder.put(grammarAccess.getSTVarInOutDeclarationBlockAccess().getConstantAssignment_2(), "rule__STVarInOutDeclarationBlock__ConstantAssignment_2");
			builder.put(grammarAccess.getSTVarInOutDeclarationBlockAccess().getVarDeclarationsAssignment_3(), "rule__STVarInOutDeclarationBlock__VarDeclarationsAssignment_3");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getNameAssignment_1(), "rule__STVarDeclaration__NameAssignment_1");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getLocatedAtAssignment_2_1(), "rule__STVarDeclaration__LocatedAtAssignment_2_1");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getArrayAssignment_4_0(), "rule__STVarDeclaration__ArrayAssignment_4_0");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getRangesAssignment_4_1_0_1(), "rule__STVarDeclaration__RangesAssignment_4_1_0_1");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getRangesAssignment_4_1_0_2_1(), "rule__STVarDeclaration__RangesAssignment_4_1_0_2_1");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getCountAssignment_4_1_1_1(), "rule__STVarDeclaration__CountAssignment_4_1_1_1");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getCountAssignment_4_1_1_2_1(), "rule__STVarDeclaration__CountAssignment_4_1_1_2_1");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getTypeAssignment_5(), "rule__STVarDeclaration__TypeAssignment_5");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getMaxLengthAssignment_6_1(), "rule__STVarDeclaration__MaxLengthAssignment_6_1");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getDefaultValueAssignment_7_1(), "rule__STVarDeclaration__DefaultValueAssignment_7_1");
			builder.put(grammarAccess.getSTVarDeclarationAccess().getPragmaAssignment_8(), "rule__STVarDeclaration__PragmaAssignment_8");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getArrayAssignment_1_0(), "rule__STTypeDeclaration__ArrayAssignment_1_0");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getRangesAssignment_1_1_0_1(), "rule__STTypeDeclaration__RangesAssignment_1_1_0_1");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getRangesAssignment_1_1_0_2_1(), "rule__STTypeDeclaration__RangesAssignment_1_1_0_2_1");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getCountAssignment_1_1_1_1(), "rule__STTypeDeclaration__CountAssignment_1_1_1_1");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getCountAssignment_1_1_1_2_1(), "rule__STTypeDeclaration__CountAssignment_1_1_1_2_1");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getTypeAssignment_2(), "rule__STTypeDeclaration__TypeAssignment_2");
			builder.put(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthAssignment_3_1(), "rule__STTypeDeclaration__MaxLengthAssignment_3_1");
			builder.put(grammarAccess.getSTElementaryInitializerExpressionAccess().getValueAssignment(), "rule__STElementaryInitializerExpression__ValueAssignment");
			builder.put(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesAssignment_1(), "rule__STArrayInitializerExpression__ValuesAssignment_1");
			builder.put(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesAssignment_2_1(), "rule__STArrayInitializerExpression__ValuesAssignment_2_1");
			builder.put(grammarAccess.getSTSingleArrayInitElementAccess().getInitExpressionAssignment(), "rule__STSingleArrayInitElement__InitExpressionAssignment");
			builder.put(grammarAccess.getSTRepeatArrayInitElementAccess().getRepetitionsAssignment_0(), "rule__STRepeatArrayInitElement__RepetitionsAssignment_0");
			builder.put(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsAssignment_2(), "rule__STRepeatArrayInitElement__InitExpressionsAssignment_2");
			builder.put(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsAssignment_3_1(), "rule__STRepeatArrayInitElement__InitExpressionsAssignment_3_1");
			builder.put(grammarAccess.getSTStructInitializerExpressionAccess().getTypeAssignment_0_0(), "rule__STStructInitializerExpression__TypeAssignment_0_0");
			builder.put(grammarAccess.getSTStructInitializerExpressionAccess().getValuesAssignment_2(), "rule__STStructInitializerExpression__ValuesAssignment_2");
			builder.put(grammarAccess.getSTStructInitializerExpressionAccess().getValuesAssignment_3_1(), "rule__STStructInitializerExpression__ValuesAssignment_3_1");
			builder.put(grammarAccess.getSTStructInitElementAccess().getVariableAssignment_0(), "rule__STStructInitElement__VariableAssignment_0");
			builder.put(grammarAccess.getSTStructInitElementAccess().getValueAssignment_2(), "rule__STStructInitElement__ValueAssignment_2");
			builder.put(grammarAccess.getSTPragmaAccess().getAttributesAssignment_2(), "rule__STPragma__AttributesAssignment_2");
			builder.put(grammarAccess.getSTPragmaAccess().getAttributesAssignment_3_1(), "rule__STPragma__AttributesAssignment_3_1");
			builder.put(grammarAccess.getSTAttributeAccess().getDeclarationAssignment_0(), "rule__STAttribute__DeclarationAssignment_0");
			builder.put(grammarAccess.getSTAttributeAccess().getValueAssignment_2(), "rule__STAttribute__ValueAssignment_2");
			builder.put(grammarAccess.getSTAssignmentAccess().getRightAssignment_1_2(), "rule__STAssignment__RightAssignment_1_2");
			builder.put(grammarAccess.getSTCallUnnamedArgumentAccess().getArgumentAssignment(), "rule__STCallUnnamedArgument__ArgumentAssignment");
			builder.put(grammarAccess.getSTCallNamedInputArgumentAccess().getParameterAssignment_0(), "rule__STCallNamedInputArgument__ParameterAssignment_0");
			builder.put(grammarAccess.getSTCallNamedInputArgumentAccess().getArgumentAssignment_2(), "rule__STCallNamedInputArgument__ArgumentAssignment_2");
			builder.put(grammarAccess.getSTCallNamedOutputArgumentAccess().getNotAssignment_0(), "rule__STCallNamedOutputArgument__NotAssignment_0");
			builder.put(grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterAssignment_1(), "rule__STCallNamedOutputArgument__ParameterAssignment_1");
			builder.put(grammarAccess.getSTCallNamedOutputArgumentAccess().getArgumentAssignment_3(), "rule__STCallNamedOutputArgument__ArgumentAssignment_3");
			builder.put(grammarAccess.getSTIfStatementAccess().getConditionAssignment_1(), "rule__STIfStatement__ConditionAssignment_1");
			builder.put(grammarAccess.getSTIfStatementAccess().getStatementsAssignment_3(), "rule__STIfStatement__StatementsAssignment_3");
			builder.put(grammarAccess.getSTIfStatementAccess().getElseifsAssignment_4(), "rule__STIfStatement__ElseifsAssignment_4");
			builder.put(grammarAccess.getSTIfStatementAccess().getElseAssignment_5(), "rule__STIfStatement__ElseAssignment_5");
			builder.put(grammarAccess.getSTElseIfPartAccess().getConditionAssignment_1(), "rule__STElseIfPart__ConditionAssignment_1");
			builder.put(grammarAccess.getSTElseIfPartAccess().getStatementsAssignment_3(), "rule__STElseIfPart__StatementsAssignment_3");
			builder.put(grammarAccess.getSTCaseStatementAccess().getSelectorAssignment_1(), "rule__STCaseStatement__SelectorAssignment_1");
			builder.put(grammarAccess.getSTCaseStatementAccess().getCasesAssignment_3(), "rule__STCaseStatement__CasesAssignment_3");
			builder.put(grammarAccess.getSTCaseStatementAccess().getElseAssignment_4(), "rule__STCaseStatement__ElseAssignment_4");
			builder.put(grammarAccess.getSTCaseCasesAccess().getConditionsAssignment_0(), "rule__STCaseCases__ConditionsAssignment_0");
			builder.put(grammarAccess.getSTCaseCasesAccess().getConditionsAssignment_1_1(), "rule__STCaseCases__ConditionsAssignment_1_1");
			builder.put(grammarAccess.getSTCaseCasesAccess().getStatementsAssignment_3(), "rule__STCaseCases__StatementsAssignment_3");
			builder.put(grammarAccess.getSTElsePartAccess().getStatementsAssignment_2(), "rule__STElsePart__StatementsAssignment_2");
			builder.put(grammarAccess.getSTForStatementAccess().getVariableAssignment_1(), "rule__STForStatement__VariableAssignment_1");
			builder.put(grammarAccess.getSTForStatementAccess().getFromAssignment_3(), "rule__STForStatement__FromAssignment_3");
			builder.put(grammarAccess.getSTForStatementAccess().getToAssignment_5(), "rule__STForStatement__ToAssignment_5");
			builder.put(grammarAccess.getSTForStatementAccess().getByAssignment_6_1(), "rule__STForStatement__ByAssignment_6_1");
			builder.put(grammarAccess.getSTForStatementAccess().getStatementsAssignment_8(), "rule__STForStatement__StatementsAssignment_8");
			builder.put(grammarAccess.getSTWhileStatementAccess().getConditionAssignment_1(), "rule__STWhileStatement__ConditionAssignment_1");
			builder.put(grammarAccess.getSTWhileStatementAccess().getStatementsAssignment_3(), "rule__STWhileStatement__StatementsAssignment_3");
			builder.put(grammarAccess.getSTRepeatStatementAccess().getStatementsAssignment_1(), "rule__STRepeatStatement__StatementsAssignment_1");
			builder.put(grammarAccess.getSTRepeatStatementAccess().getConditionAssignment_3(), "rule__STRepeatStatement__ConditionAssignment_3");
			builder.put(grammarAccess.getSTSubrangeExpressionAccess().getOpAssignment_1_0_1(), "rule__STSubrangeExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTSubrangeExpressionAccess().getRightAssignment_1_1(), "rule__STSubrangeExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTOrExpressionAccess().getOpAssignment_1_0_1(), "rule__STOrExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTOrExpressionAccess().getRightAssignment_1_1(), "rule__STOrExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTXorExpressionAccess().getOpAssignment_1_0_1(), "rule__STXorExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTXorExpressionAccess().getRightAssignment_1_1(), "rule__STXorExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTAndExpressionAccess().getOpAssignment_1_0_1(), "rule__STAndExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTAndExpressionAccess().getRightAssignment_1_1(), "rule__STAndExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTEqualityExpressionAccess().getOpAssignment_1_0_1(), "rule__STEqualityExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTEqualityExpressionAccess().getRightAssignment_1_1(), "rule__STEqualityExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTComparisonExpressionAccess().getOpAssignment_1_0_1(), "rule__STComparisonExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTComparisonExpressionAccess().getRightAssignment_1_1(), "rule__STComparisonExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTAddSubExpressionAccess().getOpAssignment_1_0_1(), "rule__STAddSubExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTAddSubExpressionAccess().getRightAssignment_1_1(), "rule__STAddSubExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTMulDivModExpressionAccess().getOpAssignment_1_0_1(), "rule__STMulDivModExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTMulDivModExpressionAccess().getRightAssignment_1_1(), "rule__STMulDivModExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTPowerExpressionAccess().getOpAssignment_1_0_1(), "rule__STPowerExpression__OpAssignment_1_0_1");
			builder.put(grammarAccess.getSTPowerExpressionAccess().getRightAssignment_1_1(), "rule__STPowerExpression__RightAssignment_1_1");
			builder.put(grammarAccess.getSTUnaryExpressionAccess().getOpAssignment_3_1(), "rule__STUnaryExpression__OpAssignment_3_1");
			builder.put(grammarAccess.getSTUnaryExpressionAccess().getExpressionAssignment_3_2(), "rule__STUnaryExpression__ExpressionAssignment_3_2");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getMemberAssignment_1_0_2(), "rule__STAccessExpression__MemberAssignment_1_0_2");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getIndexAssignment_1_1_2(), "rule__STAccessExpression__IndexAssignment_1_1_2");
			builder.put(grammarAccess.getSTAccessExpressionAccess().getIndexAssignment_1_1_3_1(), "rule__STAccessExpression__IndexAssignment_1_1_3_1");
			builder.put(grammarAccess.getSTFeatureExpressionAccess().getFeatureAssignment_1(), "rule__STFeatureExpression__FeatureAssignment_1");
			builder.put(grammarAccess.getSTFeatureExpressionAccess().getCallAssignment_2_0(), "rule__STFeatureExpression__CallAssignment_2_0");
			builder.put(grammarAccess.getSTFeatureExpressionAccess().getParametersAssignment_2_1_0(), "rule__STFeatureExpression__ParametersAssignment_2_1_0");
			builder.put(grammarAccess.getSTFeatureExpressionAccess().getParametersAssignment_2_1_1_1(), "rule__STFeatureExpression__ParametersAssignment_2_1_1_1");
			builder.put(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureAssignment_1(), "rule__STBuiltinFeatureExpression__FeatureAssignment_1");
			builder.put(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallAssignment_2_0(), "rule__STBuiltinFeatureExpression__CallAssignment_2_0");
			builder.put(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersAssignment_2_1_0(), "rule__STBuiltinFeatureExpression__ParametersAssignment_2_1_0");
			builder.put(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersAssignment_2_1_1_1(), "rule__STBuiltinFeatureExpression__ParametersAssignment_2_1_1_1");
			builder.put(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierAssignment_1(), "rule__STMultibitPartialExpression__SpecifierAssignment_1");
			builder.put(grammarAccess.getSTMultibitPartialExpressionAccess().getIndexAssignment_2_0(), "rule__STMultibitPartialExpression__IndexAssignment_2_0");
			builder.put(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionAssignment_2_1_1(), "rule__STMultibitPartialExpression__ExpressionAssignment_2_1_1");
			builder.put(grammarAccess.getSTNumericLiteralAccess().getTypeAssignment_0_0(), "rule__STNumericLiteral__TypeAssignment_0_0");
			builder.put(grammarAccess.getSTNumericLiteralAccess().getValueAssignment_0_2(), "rule__STNumericLiteral__ValueAssignment_0_2");
			builder.put(grammarAccess.getSTNumericLiteralAccess().getTypeAssignment_1_0_0(), "rule__STNumericLiteral__TypeAssignment_1_0_0");
			builder.put(grammarAccess.getSTNumericLiteralAccess().getValueAssignment_1_1(), "rule__STNumericLiteral__ValueAssignment_1_1");
			builder.put(grammarAccess.getSTSignedNumericLiteralAccess().getValueAssignment(), "rule__STSignedNumericLiteral__ValueAssignment");
			builder.put(grammarAccess.getSTDateLiteralAccess().getTypeAssignment_0(), "rule__STDateLiteral__TypeAssignment_0");
			builder.put(grammarAccess.getSTDateLiteralAccess().getValueAssignment_2(), "rule__STDateLiteral__ValueAssignment_2");
			builder.put(grammarAccess.getSTTimeLiteralAccess().getTypeAssignment_0(), "rule__STTimeLiteral__TypeAssignment_0");
			builder.put(grammarAccess.getSTTimeLiteralAccess().getValueAssignment_2(), "rule__STTimeLiteral__ValueAssignment_2");
			builder.put(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeAssignment_0(), "rule__STTimeOfDayLiteral__TypeAssignment_0");
			builder.put(grammarAccess.getSTTimeOfDayLiteralAccess().getValueAssignment_2(), "rule__STTimeOfDayLiteral__ValueAssignment_2");
			builder.put(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeAssignment_0(), "rule__STDateAndTimeLiteral__TypeAssignment_0");
			builder.put(grammarAccess.getSTDateAndTimeLiteralAccess().getValueAssignment_2(), "rule__STDateAndTimeLiteral__ValueAssignment_2");
			builder.put(grammarAccess.getSTStringLiteralAccess().getTypeAssignment_0_0(), "rule__STStringLiteral__TypeAssignment_0_0");
			builder.put(grammarAccess.getSTStringLiteralAccess().getValueAssignment_1(), "rule__STStringLiteral__ValueAssignment_1");
			builder.put(grammarAccess.getSTEnumLiteralAccess().getValueAssignment(), "rule__STEnumLiteral__ValueAssignment");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private STFunctionGrammarAccess grammarAccess;

	@Override
	protected InternalSTFunctionParser createParser() {
		InternalSTFunctionParser result = new InternalSTFunctionParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public STFunctionGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(STFunctionGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}
