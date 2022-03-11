/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Ulzii Jargalsaikhan - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - replace commaSpacing method, fixed some formating methods
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.formatting2

import com.google.inject.Inject
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument

import static org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage.Literals.*
import org.eclipse.xtext.formatting2.internal.AbstractTextReplacer
import org.eclipse.xtext.formatting2.ITextReplacerContext

class STCoreFormatter extends AbstractFormatter2 {

	@Inject extension STCoreGrammarAccess

	def dispatch void format(STCoreSource sTCoreSource, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (sTStatement : sTCoreSource.statements) {
			sTStatement.format
		}
	}

	def dispatch void format(STVarDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		if (varDeclarationBlock.constant) {
			varDeclarationBlock.regionFor.keyword(STVarDeclarationBlockAccess.constantCONSTANTKeyword_2_0).prepend [
				oneSpace
			].append[newLine]
		} else {
			varDeclarationBlock.regionFor.keyword(STVarDeclarationBlockAccess.VARKeyword_1).append[newLine]
		}
		interior(
			varDeclarationBlock.regionFor.keyword(STVarDeclarationBlockAccess.VARKeyword_1),
			varDeclarationBlock.regionFor.keyword(STVarDeclarationBlockAccess.END_VARKeyword_4),
			[indent]
		)
		varDeclarationBlock.regionFor.keywords(STVarDeclarationBlockAccess.VARKeyword_1,
			STVarDeclarationBlockAccess.END_VARKeyword_4, STVarDeclarationBlockAccess.constantCONSTANTKeyword_2_0).
			forEach [
				document.addReplacer(new KeywordCaseTextReplacer(document, it))
			]
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
		varDeclarationBlock.append[newLine]
	}

	def dispatch void format(STVarTempDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		if (varDeclarationBlock.constant) {
			varDeclarationBlock.regionFor.keyword(STVarTempDeclarationBlockAccess.constantCONSTANTKeyword_2_0).prepend [
				oneSpace
			].append[newLine]
		} else {
			varDeclarationBlock.regionFor.keyword(STVarTempDeclarationBlockAccess.VAR_TEMPKeyword_1).append[newLine]
		}
		interior(
			varDeclarationBlock.regionFor.keyword(STVarTempDeclarationBlockAccess.VAR_TEMPKeyword_1),
			varDeclarationBlock.regionFor.keyword(STVarTempDeclarationBlockAccess.END_VARKeyword_4),
			[indent]
		)
		varDeclarationBlock.regionFor.keywords(STVarTempDeclarationBlockAccess.VAR_TEMPKeyword_1,
			STVarTempDeclarationBlockAccess.END_VARKeyword_4,
			STVarTempDeclarationBlockAccess.constantCONSTANTKeyword_2_0).forEach [
			document.addReplacer(new KeywordCaseTextReplacer(document, it))
		]
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
		varDeclarationBlock.append[newLine]
	}

	def dispatch void format(STVarInputDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		if (varDeclarationBlock.constant) {
			varDeclarationBlock.regionFor.keyword(STVarInputDeclarationBlockAccess.constantCONSTANTKeyword_2_0).prepend [
				oneSpace
			].append[newLine]
		} else {
			varDeclarationBlock.regionFor.keyword(STVarInputDeclarationBlockAccess.VAR_INPUTKeyword_1).append[newLine]
		}
		interior(
			varDeclarationBlock.regionFor.keyword(STVarInputDeclarationBlockAccess.VAR_INPUTKeyword_1),
			varDeclarationBlock.regionFor.keyword(STVarInputDeclarationBlockAccess.END_VARKeyword_4),
			[indent]
		)
		varDeclarationBlock.regionFor.keywords(STVarInputDeclarationBlockAccess.VAR_INPUTKeyword_1,
			STVarInputDeclarationBlockAccess.END_VARKeyword_4,
			STVarInputDeclarationBlockAccess.constantCONSTANTKeyword_2_0).forEach [
			document.addReplacer(new KeywordCaseTextReplacer(document, it))
		]
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
		varDeclarationBlock.append[newLine]
	}

	def dispatch void format(STVarOutputDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		varDeclarationBlock.regionFor.keywords(STVarOutputDeclarationBlockAccess.VAR_OUTPUTKeyword_1,
			STVarOutputDeclarationBlockAccess.END_VARKeyword_4,
			STVarOutputDeclarationBlockAccess.constantCONSTANTKeyword_2_0).forEach [
			document.addReplacer(new KeywordCaseTextReplacer(document, it))
		]
		if (varDeclarationBlock.constant) {
			varDeclarationBlock.regionFor.keyword(STVarOutputDeclarationBlockAccess.constantCONSTANTKeyword_2_0).prepend [
				oneSpace
			].append[newLine]
		} else {
			varDeclarationBlock.regionFor.keyword(STVarOutputDeclarationBlockAccess.VAR_OUTPUTKeyword_1).append[newLine]
		}
		interior(
			varDeclarationBlock.regionFor.keyword(STVarOutputDeclarationBlockAccess.VAR_OUTPUTKeyword_1),
			varDeclarationBlock.regionFor.keyword(STVarOutputDeclarationBlockAccess.END_VARKeyword_4),
			[indent]
		)
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
		varDeclarationBlock.append[newLine]
	}

	def dispatch void format(STVarDeclaration varDeclaration, extension IFormattableDocument document) {
		varDeclaration.regionFor.keywords(",").forEach[prepend[noSpace] append[oneSpace]]
		varDeclaration.regionFor.keywords(":", ":=").forEach[surround[oneSpace]]
		varDeclaration.regionFor.keyword(";").prepend[noSpace]
		varDeclaration?.defaultValue.format
		varDeclaration.append[newLine]
	}

	def dispatch void format(STIfStatement ifStatement, extension IFormattableDocument document) {
		ifStatement.regionFor.keyword("THEN").append[newLine]
		ifStatement.statements.forEach[surround[indent] format]
		ifStatement.elseifs.forEach[format]
		ifStatement.^else.format
		ifStatement.regionFor.keyword(";").surround[noSpace]
		ifStatement.append[newLine]
	}

	def dispatch void format(STElseIfPart elseIfStatement, extension IFormattableDocument document) {
		elseIfStatement.regionFor.keyword(STElseIfPartAccess.THENKeyword_2).append[newLine]
		elseIfStatement.statements.forEach[surround[indent] format]
	}

	def dispatch void format(STElsePart elseStatement, extension IFormattableDocument document) {
		elseStatement.regionFor.keyword(STElsePartAccess.ELSEKeyword_1).append[newLine]
		elseStatement.statements.forEach[surround[indent] format]
	}

	def dispatch void format(STForStatement forStatement, extension IFormattableDocument document) {
		interior(
			forStatement.regionFor.keyword("DO").append[newLine],
			forStatement.regionFor.keyword("END_FOR"),
			[indent]
		)
		forStatement.regionFor.keyword(STForStatementAccess.colonEqualsSignKeyword_2).surround[oneSpace]
		forStatement.statements.forEach[format]
		forStatement.regionFor.keyword(";").surround[noSpace]
		forStatement.append[newLine]
	}

	def dispatch void format(STWhileStatement whileStatement, extension IFormattableDocument document) {
		interior(
			whileStatement.regionFor.keyword("DO").append[newLine],
			whileStatement.regionFor.keyword("END_WHILE"),
			[indent]
		)
		whileStatement.statements.forEach[format]
		whileStatement.regionFor.keyword(";").surround[noSpace]
		whileStatement.append[newLine]
	}

	def dispatch void format(STRepeatStatement repeatStatement, extension IFormattableDocument document) {
		interior(
			repeatStatement.regionFor.keyword("REPEAT").append[newLine],
			repeatStatement.regionFor.keyword("UNTIL"),
			[indent]
		)
		repeatStatement.statements.forEach[format]
		repeatStatement.statements.forEach[surround[newLine]]
		repeatStatement.regionFor.keyword(";").surround[noSpace]
		repeatStatement.append[newLine]
	}

	def dispatch void format(STCaseStatement caseStatement, extension IFormattableDocument document) {
		interior(
			caseStatement.regionFor.keyword("OF").append[newLine],
			caseStatement.regionFor.keyword("END_CASE"),
			[indent]
		)
		caseStatement.regionFor.keyword(";").surround[noSpace]
		caseStatement.cases.forEach[format]
		caseStatement.cases.forEach[append[newLine]]
		caseStatement.^else.format
		caseStatement.append[newLine]
	}

	def dispatch format(STCaseCases stCase, extension IFormattableDocument document) {
		stCase.conditions.forEach[format]
		stCase.regionFor.keyword(STCaseCasesAccess.colonKeyword_2).prepend[oneSpace].append[newLine]
		stCase.statements.forEach[surround[indent] format]
	}

	def dispatch void format(STAssignmentStatement assignmentStatement, extension IFormattableDocument document) {
		assignmentStatement.regionFor.keyword(":=").surround[oneSpace]
		assignmentStatement.regionFor.keyword(";").surround[noSpace]
		assignmentStatement.append[newLine]
		assignmentStatement.left.format
		assignmentStatement.right.format
	}

	def dispatch void format(STCallStatement callStatement, extension IFormattableDocument document) {
		callStatement.call.format
		callStatement.regionFor.keyword(";").surround[noSpace]
		callStatement.append[newLine]
	}

	def dispatch void format(STStatement statement, extension IFormattableDocument document) {
		statement.regionFor.keyword(";").surround[noSpace]
		statement.append[newLine]
	}

	def dispatch void format(STElementaryInitializerExpression initExpression,
		extension IFormattableDocument document) {
		initExpression.value.format
	}

	def dispatch void format(STArrayInitializerExpression arrayInitExpression,
		extension IFormattableDocument document) {
		arrayInitExpression.regionFor.keyword(STArrayInitializerExpressionAccess.leftSquareBracketKeyword_0).append [
			noSpace
		]
		arrayInitExpression.regionFor.keyword(STArrayInitializerExpressionAccess.rightSquareBracketKeyword_3).prepend [
			noSpace
		]
		arrayInitExpression.regionFor.keywords(STArrayInitializerExpressionAccess.commaKeyword_2_0).forEach [
			prepend[noSpace]
			append[oneSpace]
		]
		arrayInitExpression.values.forEach[format]
	}

	def dispatch void format(STArrayInitElement element, extension IFormattableDocument document) {
		element.indexOrInitExpression.format
	}

	def dispatch void format(STBinaryExpression binaryExpression, extension IFormattableDocument document) {
		if (binaryExpression.op != STBinaryOperator.RANGE) {
			binaryExpression.regionFor.feature(ST_BINARY_EXPRESSION__OP).surround[oneSpace]
		}
		if (binaryExpression.op == STBinaryOperator.AMPERSAND) {
			document.addReplacer(new AbstractTextReplacer(document, 
			binaryExpression.regionFor.feature(ST_BINARY_EXPRESSION__OP)){		
				override createReplacements(ITextReplacerContext context) {
					context.addReplacement(region.replaceWith(STBinaryOperator.AND.toString))
					return context
				}
				
			})
		}
		binaryExpression.left.format
		binaryExpression.right.format
	}

	def dispatch void format(STUnaryExpression unaryExpression, extension IFormattableDocument document) {
		unaryExpression.regionFor.feature(ST_UNARY_EXPRESSION__OP).append[noSpace]
		unaryExpression.expression.format
	}

	def dispatch void format(STMemberAccessExpression mExpression, extension IFormattableDocument document) {
		mExpression.regionFor.keyword(".").surround[noSpace]
		mExpression.member.format
		mExpression.receiver.format
	}

	def dispatch void format(STFeatureExpression featureExpression, extension IFormattableDocument document) {
		featureExpression.regionFor.keywords(STFeatureExpressionAccess.commaKeyword_2_1_1_0).forEach [
			prepend[noSpace]
			append[oneSpace]
		]
		featureExpression.regionFor.keyword(STFeatureExpressionAccess.callLeftParenthesisKeyword_2_0_0).surround[noSpace]
		featureExpression.regionFor.keyword(STFeatureExpressionAccess.rightParenthesisKeyword_2_2).prepend[noSpace]
		featureExpression.parameters.forEach[format]
	}

	def dispatch void format(STMultibitPartialExpression mBPExpression, extension IFormattableDocument document) {
		mBPExpression.regionFor.assignment(STMultibitPartialExpressionAccess.specifierAssignment_1).surround[noSpace]
		mBPExpression.regionFor.keyword(STMultibitPartialExpressionAccess.leftParenthesisKeyword_2_1_0).append[noSpace]
		mBPExpression.regionFor.keyword(STMultibitPartialExpressionAccess.rightParenthesisKeyword_2_1_2).prepend [
			noSpace
		]
		mBPExpression.expression.format
	}

	def dispatch void format(STCallUnnamedArgument unnamedArgument, extension IFormattableDocument document) {
		unnamedArgument.arg.format
	}

	def dispatch void format(STCallNamedInputArgument namedInputArgument, extension IFormattableDocument document) {
		namedInputArgument.regionFor.keyword(":=").surround[oneSpace]
		namedInputArgument.source.format
	}

	def dispatch void format(STCallNamedOutputArgument namedOutputArgument, extension IFormattableDocument document) {
		if (namedOutputArgument.not) {
			namedOutputArgument.regionFor.keyword(STCallNamedOutputArgumentAccess.notNOTKeyword_0_0).append[oneSpace]
		}
		namedOutputArgument.regionFor.keyword(STCallNamedOutputArgumentAccess.equalsSignGreaterThanSignKeyword_2).
			surround[oneSpace]
	}

	def dispatch void format(STArrayAccessExpression arrayAccessExpression, extension IFormattableDocument document) {
		arrayAccessExpression.regionFor.keyword(STAccessExpressionAccess.leftSquareBracketKeyword_1_1_1).append[noSpace]
		arrayAccessExpression.regionFor.keyword(STAccessExpressionAccess.rightSquareBracketKeyword_1_1_4).prepend [
			noSpace
		]
		arrayAccessExpression.regionFor.keywords(STAccessExpressionAccess.commaKeyword_1_1_3_0).forEach [
			prepend[noSpace]
			append[oneSpace]
		]
		arrayAccessExpression.receiver.format
	}

// TODO: implement for STVarDeclaration, STElementaryInitializerExpression, STArrayInitializerExpression, STArrayInitElement, STAssignmentStatement, STCallStatement, STCallUnnamedArgument, STCallNamedInputArgument, STIfStatement, STElseIfPart, STCaseStatement, STCaseCases, STElsePart, STForStatement, STWhileStatement, STRepeatStatement, STBinaryExpression, STUnaryExpression, STMemberAccessExpression, STArrayAccessExpression, STFeatureExpression, STMultibitPartialExpression
}
