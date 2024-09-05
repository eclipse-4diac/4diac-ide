/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
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
 *   Martin Jobst - autowrap support
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.formatting2

import com.google.inject.Inject
import java.util.List
import java.util.regex.Pattern
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
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
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
import org.eclipse.xtext.AbstractRule
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.FormatterPreferenceKeys
import org.eclipse.xtext.formatting2.IFormattableDocument
import org.eclipse.xtext.formatting2.IHiddenRegionFormatting
import org.eclipse.xtext.formatting2.ITextReplacer
import org.eclipse.xtext.formatting2.ITextReplacerContext
import org.eclipse.xtext.formatting2.internal.MultilineCommentReplacer
import org.eclipse.xtext.formatting2.internal.SinglelineCodeCommentReplacer
import org.eclipse.xtext.formatting2.internal.SinglelineDocCommentReplacer
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer
import org.eclipse.xtext.formatting2.regionaccess.IComment
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegionPart
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment
import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch

import static org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage.Literals.*

class STCoreFormatter extends AbstractFormatter2 {

	@Inject extension STCoreGrammarAccess

	/** Formats the STCoureSource */
	def dispatch void format(STCoreSource sTCoreSource, extension IFormattableDocument document) {
		for (sTStatement : sTCoreSource.statements) {
			sTStatement.format
		}
	}

	/** Formats the STImport */
	def dispatch void format(STImport stImport, extension IFormattableDocument document) {
		stImport.regionFor.keyword(STImportAccess.IMPORTKeyword_0).prepend[noIndentation].append[oneSpace]
		stImport.regionFor.keyword(STImportAccess.semicolonKeyword_2).prepend[noSpace]
	}

	/** Formats the STVarDeclarationBlocks */
	def dispatch void format(STVarDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		varDeclarationBlock.regionFor.keyword(STVarDeclarationBlockAccess.VARKeyword_1).prepend[setNewLines(1, 1, 2)]
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
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
	}

	/** Formats the STVarTempDeclarationBlocks */
	def dispatch void format(STVarTempDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		varDeclarationBlock.regionFor.keyword(STVarTempDeclarationBlockAccess.VAR_TEMPKeyword_1).prepend [
			setNewLines(1, 1, 2)
		]
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
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
	}

	/** Formats the STVarInputDeclarationBlocks */
	def dispatch void format(STVarInputDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		varDeclarationBlock.regionFor.keyword(STVarInputDeclarationBlockAccess.VAR_INPUTKeyword_1).prepend [
			setNewLines(1, 1, 2)
		]
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
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
	}

	/** Formats the STVarOutputDeclarationBlocks */
	def dispatch void format(STVarOutputDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		varDeclarationBlock.regionFor.keyword(STVarOutputDeclarationBlockAccess.VAR_OUTPUTKeyword_1).prepend [
			setNewLines(1, 1, 2)
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
	}

	def dispatch void format(STVarInOutDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		varDeclarationBlock.regionFor.keyword(STVarInOutDeclarationBlockAccess.VAR_IN_OUTKeyword_1).prepend [
			setNewLines(1, 1, 2)
		]
		if (varDeclarationBlock.constant) {
			varDeclarationBlock.regionFor.keyword(STVarInOutDeclarationBlockAccess.constantCONSTANTKeyword_2_0).prepend [
				oneSpace
			].append[newLine]
		} else {
			varDeclarationBlock.regionFor.keyword(STVarInOutDeclarationBlockAccess.VAR_IN_OUTKeyword_1).append[newLine]
		}
		interior(
			varDeclarationBlock.regionFor.keyword(STVarInOutDeclarationBlockAccess.VAR_IN_OUTKeyword_1),
			varDeclarationBlock.regionFor.keyword(STVarInOutDeclarationBlockAccess.END_VARKeyword_4),
			[indent]
		)
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
	}

	/** Formats the STVarDeclarations */
	def dispatch void format(STVarDeclaration varDeclaration, extension IFormattableDocument document) {
		varDeclaration.regionFor.keywords(",").forEach [
			prepend[noSpace]
			append[oneSpace]
		]
		varDeclaration.regionFor.keywords(":", ":=").forEach [
			surround[oneSpace]
			append[
				autowrap
				onAutowrap = new STCoreAutowrapFormatter(varDeclaration.nextHiddenRegion)
			]
		]
		varDeclaration.regionFor.keyword(";").prepend[noSpace]

		if (varDeclaration.type.name != "") {
			val typeRegion = varDeclaration.regionFor.assignment(STVarDeclarationAccess.getTypeAssignment_5)
			if (typeRegion !== null) {
				document.addReplacer(new QualifiedNameReplacer(typeRegion, varDeclaration.type))
			}
		}

		if (varDeclaration.array) {
			varDeclaration.regionFor.keyword(STVarDeclarationAccess.getLeftSquareBracketKeyword_4_1_0_0).prepend [
				noSpace
			]
			varDeclaration.regionFor.keyword(STVarDeclarationAccess.getRightSquareBracketKeyword_4_1_0_3).append [
				oneSpace
			]
			varDeclaration.ranges.formatList(document)
		}

		varDeclaration.defaultValue.format
		varDeclaration.pragma.prepend[oneSpace]
		varDeclaration.pragma.format
		varDeclaration.append[newLine]
	}

	/** Formats the STPragma */
	def dispatch void format(STPragma pragma, extension IFormattableDocument document) {
		pragma.formatBlock(pragma.attributes, pragma.regionFor.keyword("{"), pragma.regionFor.keyword("}"), document)
	}

	/** Formats the STPragma */
	def dispatch void format(STAttribute attribute, extension IFormattableDocument document) {
		attribute.regionFor.keyword(":=").surround[oneSpace]
		attribute.value.format
	}

	/** Formats the STIfStatements */
	def dispatch void format(STIfStatement ifStatement, extension IFormattableDocument document) {
		interior(
			ifStatement.regionFor.keyword("THEN"),
			ifStatement.elseifs.empty
				? (ifStatement.^else !== null
				? ifStatement.^else.regionFor.keyword("ELSE")
				: ifStatement.regionFor.keyword("END_IF"))
				: ifStatement.elseifs.get(0).regionFor.keyword("ELSIF"),
			[indent]
		)
		ifStatement.condition.formatCondition(
			ifStatement.regionFor.keyword("IF"),
			ifStatement.regionFor.keyword("THEN"),
			document
		)
		ifStatement.statements.forEach[format]
		ifStatement.elseifs.forEach[format]
		ifStatement.^else.format
		ifStatement.regionFor.keyword(";").surround[noSpace]
		ifStatement.append[setNewLines(1, 2, 2)]
	}

	/** Formats the STElseIfStatements */
	def dispatch void format(STElseIfPart elseIfStatement, extension IFormattableDocument document) {
		elseIfStatement.condition.formatCondition(
			elseIfStatement.regionFor.keyword("ELSIF"),
			elseIfStatement.regionFor.keyword("THEN"),
			document
		)
		elseIfStatement.statements.forEach[surround[indent] format]
	}

	/** Formats the STElseStatements */
	def dispatch void format(STElsePart elseStatement, extension IFormattableDocument document) {
		elseStatement.regionFor.keyword(STElsePartAccess.ELSEKeyword_1).append[newLine]
		elseStatement.statements.forEach[surround[indent] format]
	}

	/** Formats the STForStatements */
	def dispatch void format(STForStatement forStatement, extension IFormattableDocument document) {
		interior(
			forStatement.regionFor.keyword("DO").append[newLine],
			forStatement.regionFor.keyword("END_FOR"),
			[indent]
		)
		val autowrapFormatter = new STCoreAutowrapFormatter(forStatement.regionFor.keyword("DO").previousHiddenRegion)
		forStatement.regionFor.keyword(STForStatementAccess.colonEqualsSignKeyword_2).surround[oneSpace].append [
			autowrap
			onAutowrap = autowrapFormatter
		]
		forStatement.regionFor.keyword(STForStatementAccess.TOKeyword_4).surround[oneSpace].prepend [
			autowrap
			onAutowrap = autowrapFormatter
		]
		forStatement.regionFor.keyword(STForStatementAccess.BYKeyword_6_0).surround[oneSpace].prepend [
			autowrap
			onAutowrap = autowrapFormatter
		]
		forStatement.statements.forEach[format]
		forStatement.regionFor.keyword(";").surround[noSpace]
		forStatement.append[setNewLines(1, 2, 2)]
	}

	/** Formats the STWhileStatements */
	def dispatch void format(STWhileStatement whileStatement, extension IFormattableDocument document) {
		interior(
			whileStatement.regionFor.keyword("DO"),
			whileStatement.regionFor.keyword("END_WHILE"),
			[indent]
		)
		whileStatement.condition.formatCondition(
			whileStatement.regionFor.keyword("WHILE"),
			whileStatement.regionFor.keyword("DO"),
			document
		)
		whileStatement.statements.forEach[format]
		whileStatement.regionFor.keyword(";").surround[noSpace]
		whileStatement.append[setNewLines(1, 2, 2)]
	}

	/** Formats the STRepeatStatements */
	def dispatch void format(STRepeatStatement repeatStatement, extension IFormattableDocument document) {
		interior(
			repeatStatement.regionFor.keyword("REPEAT").append[newLine],
			repeatStatement.regionFor.keyword("UNTIL"),
			[indent]
		)
		repeatStatement.condition.format
		repeatStatement.regionFor.keyword(STRepeatStatementAccess.END_REPEATKeyword_4).prepend[newLine noSpace]
		repeatStatement.statements.forEach[format]
		repeatStatement.regionFor.keyword(";").surround[noSpace]
		repeatStatement.append[setNewLines(1, 2, 2)]
	}

	/** Formats the STCaseStatements */
	def dispatch void format(STCaseStatement caseStatement, extension IFormattableDocument document) {
		interior(
			caseStatement.regionFor.keyword("OF").append[newLine],
			caseStatement.regionFor.keyword("END_CASE"),
			[indent]
		)
		caseStatement.regionFor.keyword(";").surround[noSpace]
		caseStatement.cases.forEach[format]
		caseStatement.^else.format
		caseStatement.append[setNewLines(1, 2, 2)]
	}

	/** Formats the STCaseCases */
	def dispatch format(STCaseCases stCase, extension IFormattableDocument document) {
		stCase.conditions.formatList(document)
		stCase.regionFor.keyword(STCaseCasesAccess.colonKeyword_2).prepend[oneSpace].append[newLine]
		stCase.statements.forEach[surround[indent] format]
	}

	/** Formats the STAssignment */
	def dispatch void format(STAssignment assignment, extension IFormattableDocument document) {
		assignment.left.format
		assignment.regionFor.keyword(":=").surround[oneSpace].append [
			autowrap
			onAutowrap = new STCoreAutowrapFormatter(assignment.nextHiddenRegion)
		]
		assignment.right.format
		assignment.regionFor.keyword(";").prepend[noSpace].append[setNewLines(1, 1, 2)]
	}

	/** Formats the STStatements */
	def dispatch void format(STStatement statement, extension IFormattableDocument document) {
		statement.regionFor.keyword(";").surround[noSpace].append[setNewLines(1, 1, 2)]
	}

	/** Formats the STElementaryInitializerExpression */
	def dispatch void format(STElementaryInitializerExpression initExpression,
		extension IFormattableDocument document) {
		initExpression.value.format
	}

	/** Formats the STArrayInitializerExpression */
	def dispatch void format(STArrayInitializerExpression arrayInitExpression,
		extension IFormattableDocument document) {
		arrayInitExpression.regionFor.keyword(STArrayInitializerExpressionAccess.leftSquareBracketKeyword_0).append [
			noSpace
		]
		arrayInitExpression.regionFor.keyword(STArrayInitializerExpressionAccess.rightSquareBracketKeyword_3).prepend [
			noSpace
		]
		arrayInitExpression.values.formatList(document)
	}

	/** Formats the STSingleArrayInitElement */
	def dispatch void format(STSingleArrayInitElement element, extension IFormattableDocument document) {
		element.initExpression.format
	}

	/** Formats the STRepeatArrayInitElement */
	def dispatch void format(STRepeatArrayInitElement element, extension IFormattableDocument document) {
		element.regionFor.keyword('(').prepend[noSpace].append[noSpace]
		element.regionFor.keyword(',').prepend[noSpace].append[oneSpace]
		element.regionFor.keyword(')').prepend[noSpace]
		element.initExpressions.forEach[format]
	}

	/** Formats the STStructInitializerExpression */
	def dispatch void format(STStructInitializerExpression structInitializerExpression,
		extension IFormattableDocument document) {
		structInitializerExpression.regionFor.keyword(STStructInitializerExpressionAccess.numberSignKeyword_0_1).
			surround[noSpace]
		structInitializerExpression.regionFor.keyword(STStructInitializerExpressionAccess.leftParenthesisKeyword_1).
			append [
				noSpace
			]
		structInitializerExpression.values.formatList(document)
		structInitializerExpression.regionFor.keyword(STStructInitializerExpressionAccess.rightParenthesisKeyword_4).
			prepend [
				noSpace
			]
	}

	/** Formats the STStructInitElement */
	def dispatch void format(STStructInitElement element, extension IFormattableDocument document) {
		element.regionFor.keyword(STStructInitElementAccess.colonEqualsSignKeyword_1).surround[oneSpace].append [
			autowrap
			onAutowrap = new STCoreAutowrapFormatter(element.nextHiddenRegion)
		]
		element.value.format
	}

	/** Formats the STBinaryExpression */
	def dispatch void format(STBinaryExpression binaryExpression, extension IFormattableDocument document) {
		val opRegion = binaryExpression.regionFor.feature(ST_BINARY_EXPRESSION__OP)
		if (opRegion !== null) {
			if (binaryExpression.op != STBinaryOperator.RANGE) {
				opRegion.surround[oneSpace].prepend [
					autowrap(opRegion.length + binaryExpression.right.regionForEObject.length)
					onAutowrap = new STCoreAutowrapFormatter(binaryExpression.nextHiddenRegion)
				]
			}

			if (binaryExpression.op == STBinaryOperator.AMPERSAND) {
				document.addReplacer(new KeywordTextReplacer(opRegion, STBinaryOperator.AND.toString))
			} else if (binaryExpression.op == STBinaryOperator.AND) {
				document.addReplacer(new KeywordCaseTextReplacer(opRegion))
			}
		}

		binaryExpression.left.format
		binaryExpression.right.format
	}

	/** Formats the STUnaryExpression */
	def dispatch void format(STUnaryExpression unaryExpression, extension IFormattableDocument document) {
		if (unaryExpression.op == STUnaryOperator.NOT) {
			unaryExpression.regionFor.feature(ST_UNARY_EXPRESSION__OP).append[oneSpace]
		} else {
			unaryExpression.regionFor.feature(ST_UNARY_EXPRESSION__OP).append[noSpace]
		}
		unaryExpression.expression.format
	}

	/** Formats the STMemberAccessExpression */
	def dispatch void format(STMemberAccessExpression mExpression, extension IFormattableDocument document) {
		mExpression.regionFor.keyword(".").surround[noSpace].prepend [
			autowrap(mExpression.member.regionForEObject.length)
			onAutowrap = new STCoreAutowrapFormatter(mExpression.nextHiddenRegion)
		]
		mExpression.member.format
		mExpression.receiver.format
	}

	/** Formats the STFeatureExpression */
	def dispatch void format(STFeatureExpression featureExpression, extension IFormattableDocument document) {
		featureExpression.regionFor.keyword(STFeatureExpressionAccess.callLeftParenthesisKeyword_2_0_0).surround [
			noSpace
		]
		featureExpression.regionFor.keyword(STFeatureExpressionAccess.rightParenthesisKeyword_2_2).prepend[noSpace]
		featureExpression.parameters.formatList(document)
		featureExpression.regionFor.keyword(";").prepend[noSpace].append[setNewLines(1, 1, 2)]
	}

	/** Formats the STBuiltinFeatureExpression */
	def dispatch void format(STBuiltinFeatureExpression featureExpression, extension IFormattableDocument document) {
		featureExpression.regionFor.keyword(STBuiltinFeatureExpressionAccess.callLeftParenthesisKeyword_2_0_0).surround [
			noSpace
		]
		featureExpression.parameters.formatList(document)
		featureExpression.regionFor.keyword(STBuiltinFeatureExpressionAccess.rightParenthesisKeyword_2_2).prepend [
			noSpace
		]
		featureExpression.regionFor.keyword(";").prepend[noSpace].append[setNewLines(1, 1, 2)]
	}

	def dispatch void format(STMultibitPartialExpression mBPExpression, extension IFormattableDocument document) {
		mBPExpression.regionFor.assignment(STMultibitPartialExpressionAccess.specifierAssignment_1).surround[noSpace]
		mBPExpression.regionFor.keyword(STMultibitPartialExpressionAccess.leftParenthesisKeyword_2_1_0).append[noSpace]
		mBPExpression.regionFor.keyword(STMultibitPartialExpressionAccess.rightParenthesisKeyword_2_1_2).prepend [
			noSpace
		]
		mBPExpression.expression.format
	}

	/** Formats the STCallUnnamedArgument */
	def dispatch void format(STCallUnnamedArgument unnamedArgument, extension IFormattableDocument document) {
		unnamedArgument.argument.format
	}

	/** Formats the STCallNamedInputArgument */
	def dispatch void format(STCallNamedInputArgument namedInputArgument, extension IFormattableDocument document) {
		namedInputArgument.regionFor.keyword(":=").surround[oneSpace].append [
			autowrap
			onAutowrap = new STCoreAutowrapFormatter(namedInputArgument.nextHiddenRegion)
		]
		namedInputArgument.argument.format
	}

	/** Formats the STCallNamedOutputArgument */
	def dispatch void format(STCallNamedOutputArgument namedOutputArgument, extension IFormattableDocument document) {
		if (namedOutputArgument.not) {
			namedOutputArgument.regionFor.keyword(STCallNamedOutputArgumentAccess.notNOTKeyword_0_0).append[oneSpace]
		}
		namedOutputArgument.regionFor.keyword(STCallNamedOutputArgumentAccess.equalsSignGreaterThanSignKeyword_2).
			surround[oneSpace].append [
				autowrap
				onAutowrap = new STCoreAutowrapFormatter(namedOutputArgument.nextHiddenRegion)
			]
		namedOutputArgument.argument.format
	}

	/** Formats the STArrayAccessExpression */
	def dispatch void format(STArrayAccessExpression arrayAccessExpression, extension IFormattableDocument document) {
		arrayAccessExpression.receiver.format
		arrayAccessExpression.regionFor.keyword(STAccessExpressionAccess.leftSquareBracketKeyword_1_1_1).append[noSpace]
		arrayAccessExpression.index.formatList(document)
		arrayAccessExpression.regionFor.keyword(STAccessExpressionAccess.rightSquareBracketKeyword_1_1_4).prepend [
			noSpace
		]
	}

	def protected void formatBlock(EObject blockElement, List<? extends EObject> listElements, ISemanticRegion begin,
		ISemanticRegion end, extension IFormattableDocument document) {
		if (blockElement.multiline) {
			listElements.formatBlockMultiline(begin, end, document)
		} else {
			formatConditionally(begin.offset, end.endOffset - begin.offset, [ subDocument |
				listElements.formatBlockSingleline(begin, end, subDocument.requireFitsInLine)
			], [ subDocument |
				listElements.formatBlockMultiline(begin, end, subDocument)
			])
		}
	}

	def protected void formatBlockSingleline(List<? extends EObject> listElements, ISemanticRegion begin,
		ISemanticRegion end, extension IFormattableDocument document) {
		begin.append[noSpace]
		listElements.formatList(document)
		end.prepend[noSpace]
	}

	def protected void formatBlockMultiline(List<? extends EObject> listElements, ISemanticRegion begin,
		ISemanticRegion end, extension IFormattableDocument document) {
		begin.append[newLine]
		interior(begin, end)[indent]
		listElements.formatListMultiline(document)
		end.prepend[newLine]
	}

	def protected void formatList(List<? extends EObject> semanticElements, extension IFormattableDocument document) {
		if (!semanticElements.empty) {
			val autowrapFormatter = new STCoreAutowrapFormatter(semanticElements.lastOrNull.nextHiddenRegion)
			semanticElements.forEach [
				val autowrapLength = regionForEObject.length
				immediatelyPreceding.keyword(',').prepend[noSpace].append [
					oneSpace
					autowrap(autowrapLength)
					onAutowrap = autowrapFormatter
				]
				format
			]
		}
	}

	def protected void formatListMultiline(List<? extends EObject> semanticElements,
		extension IFormattableDocument document) {
		semanticElements.forEach [
			immediatelyPreceding.keyword(',').prepend[noSpace].append[newLine]
			format
		]
	}

	def protected void formatCondition(EObject semanticElement, ISemanticRegion begin, ISemanticRegion end,
		extension IFormattableDocument document) {
		if (semanticElement.multiline) {
			semanticElement.format
			end.prepend[newLine]
		} else {
			formatConditionally(begin.offset, end.endOffset - begin.offset, [ subDocument |
				semanticElement.formatSinglelineCondition(end, subDocument.requireFitsInLine)
			], [ subDocument |
				semanticElement.formatMultilineCondition(end, subDocument)
			])
		}
		end.append[newLine]
	}

	def protected void formatSinglelineCondition(EObject semanticElement, ISemanticRegion end,
		extension IFormattableDocument document) {
		semanticElement.format
		end.prepend[oneSpace]
	}

	def protected void formatMultilineCondition(EObject semanticElement, ISemanticRegion end,
		extension IFormattableDocument document) {
		semanticElement.format
		end.prepend[newLine]
	}

	override ITextReplacer createWhitespaceReplacer(ITextSegment hiddens, IHiddenRegionFormatting formatting) {
		return new WhitespaceReplacer(hiddens, formatting) {
			override int computeNewLineCount(ITextReplacerContext context) {
				val newLineDefault = formatting.getNewLineDefault();
				val newLineMin = formatting.getNewLineMin();
				val newLineMax = formatting.getNewLineMax();
				if (newLineMin !== null || newLineDefault !== null || newLineMax !== null) {
					if (region instanceof IHiddenRegion && (region as IHiddenRegion).isUndefined()) {
						if (newLineDefault !== null)
							return newLineDefault;
						if (newLineMin !== null)
							return newLineMin;
						if (newLineMax !== null)
							return newLineMax;
					} else {
						var lineCount = 0
						if (region instanceof IHiddenRegionPart) {
							lineCount = (region as IHiddenRegionPart).previousHiddenPart instanceof IComment ? region.
								getLineCount() : region.getLineCount() - 1;
						} else
							lineCount = region.getLineCount() - 1;
						if (newLineMin !== null && newLineMin > lineCount)
							lineCount = newLineMin;
						if (newLineMax !== null && newLineMax < lineCount)
							lineCount = newLineMax;
						return lineCount;
					}
				}
				return 0;
			}
		};
	}

	override ITextReplacer createCommentReplacer(IComment comment) {
		val tabWidth = getPreference(FormatterPreferenceKeys.tabWidth);
		var grammarElement = comment.getGrammarElement()
		if (grammarElement instanceof AbstractRule) {
			val ruleName = grammarElement.getName()
			if (ruleName.startsWith("ML"))
				return new MultilineCommentReplacer(comment, '*') {
					override createReplacements(ITextReplacerContext context) {
						var ITextRegionAccess access = comment.getTextRegionAccess();
						val region = access.regionForOffset(comment.offset, comment.length)
						commentReplacementContext(context, region, ruleName)
					}

					override configureWhitespace(WhitespaceReplacer leading, WhitespaceReplacer trailing) {
						if (!leading.region.multiline) {
							enforceSingleSpace(leading);
						}
					}
				}
			if (ruleName.startsWith("SL")) {
				if (comment.getLineRegions().get(0).getIndentation().getLength() > 0)
					return new SinglelineDocCommentReplacer(comment, "//") {
						override createReplacements(ITextReplacerContext context) {
							var ITextRegionAccess access = comment.getTextRegionAccess();
							val region = access.regionForOffset(comment.offset, comment.length)
							commentReplacementContext(context, region, ruleName)
						}

						override configureWhitespace(WhitespaceReplacer leading, WhitespaceReplacer trailing) {
							leading.formatting.setSpace(" ".repeat(tabWidth))
							if (leading.formatting.indentationDecrease !== null) {
								trailing.formatting.setIndentationDecrease(leading.formatting.indentationDecrease)
								leading.formatting.setIndentationDecrease(null)
							}
						}
					}
				else
					return new SinglelineCodeCommentReplacer(comment, "//") {
						override createReplacements(ITextReplacerContext context) {
							var ITextRegionAccess access = comment.getTextRegionAccess();
							val region = access.regionForOffset(comment.offset, comment.length)
							commentReplacementContext(context, region, ruleName)
						}

						override configureWhitespace(WhitespaceReplacer leading, WhitespaceReplacer trailing) {
							leading.formatting.setSpace(" ".repeat(tabWidth))
							if (leading.formatting.indentationDecrease !== null) {
								trailing.formatting.setIndentationDecrease(leading.formatting.indentationDecrease)
								leading.formatting.setIndentationDecrease(null)
							}
						}
					}
			}
		}
		var elementName = new GrammarElementTitleSwitch().showQualified().showRule().doSwitch(grammarElement)
		throw new IllegalStateException("No " + ITextReplacer.getSimpleName() + " configured for " + elementName)
	}

	private def ITextReplacerContext commentReplacementContext(ITextReplacerContext context, ITextSegment region,
		String name) {

		val lineSeparator = context.getNewLinesString(1)
		val maxLineWidth = getPreference(FormatterPreferenceKeys.maxLineWidth)
		val isML = name.startsWith("ML")

		val lengthBeforeComment = context.getLeadingCharsInLineCount
		val spaceBeforeComment = " ".repeat(lengthBeforeComment)
		val commentLineLength = maxLineWidth - lengthBeforeComment - (isML ? 6 : 3)

		if (commentLineLength < 1) {
			return context
		}

		val commentString = if (isML)
				region.text.replaceFirst("^[(/]\\*", "").replaceFirst("\\*[)/]$", "").replaceAll(
					"(?m)^[\\s&&[^\r\n]]*\\* ", "").replaceAll("[\\s&&[^\r\n]]+", " ").trim
			else
				region.text.replaceFirst("^//", "").replaceFirst("\n$", "").replaceAll("\\s+", " ").trim

		val pattern = Pattern.compile(
			"[\\s&&[^\r\n]]*(?:(\\S{" + commentLineLength + "})|([[\\s&&[^\r\n]]\\S]{1," + commentLineLength +
				"}(?!\\S)[\r\n]*))");
		val matcher = pattern.matcher(commentString.replace("$", "\\$"))

		var replacement = (isML ? "(* " : "") + matcher.replaceAll [ m |
			var g = m.group(1) ?: m.group(2)
			(isML ? spaceBeforeComment : "") + (isML ? " * " : "// ") + (
				g
			) + (g.indexOf(lineSeparator) === -1 ? lineSeparator : "")
		].trim + (isML ? " *)" : lineSeparator);

		replacement = replacement.replaceFirst("^\\(\\*\\s*\\* ", "(* ");

		replacement = replacement.replaceAll(lineSeparator + "(?=" + lineSeparator + ")",
			lineSeparator + spaceBeforeComment + " * ")

		if (region.text != replacement) {
			context.addReplacement(region.replaceWith(replacement))
		}

		context
	}

	override createTextReplacerContext(IFormattableDocument document) {
		new STCoreTextReplacerContext(document)
	}
}
