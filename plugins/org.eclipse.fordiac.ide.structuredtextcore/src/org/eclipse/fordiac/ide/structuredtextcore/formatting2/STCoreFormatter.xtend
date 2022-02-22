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
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.formatting2

import com.google.inject.Inject
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart

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
			varDeclarationBlock.regionFor.keyword("CONSTANT").prepend[oneSpace].append[newLine]
		} else {
			varDeclarationBlock.regionFor.keywords("VAR", "VAR_TEMP", "VAR_INPUT", "VAR_OUTPUT").forEach [
				append[newLine]
			]
		}
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.regionFor.keywords(":", ":=").forEach[surround[oneSpace]]
			varDeclaration.regionFor.keyword(";").prepend[noSpace]
			varDeclaration.surround[indent].append[newLine]
		}
		varDeclarationBlock.append[newLine]
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

	def dispatch void format(STStatement statement, extension IFormattableDocument document) {
		statement.append[newLine]
	}

// TODO: implement for STVarDeclaration, STElementaryInitializerExpression, STArrayInitializerExpression, STArrayInitElement, STAssignmentStatement, STCallStatement, STCallUnnamedArgument, STCallNamedInputArgument, STIfStatement, STElseIfPart, STCaseStatement, STCaseCases, STElsePart, STForStatement, STWhileStatement, STRepeatStatement, STBinaryExpression, STUnaryExpression, STMemberAccessExpression, STArrayAccessExpression, STFeatureExpression, STMultibitPartialExpression
}
