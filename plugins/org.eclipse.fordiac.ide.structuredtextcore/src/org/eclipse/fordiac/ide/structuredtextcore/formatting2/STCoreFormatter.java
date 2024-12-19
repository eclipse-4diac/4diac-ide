/**
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
 */
package org.eclipse.fordiac.ide.structuredtextcore.formatting2;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.formatting2.AbstractFormatter2;
import org.eclipse.xtext.formatting2.FormatterPreferenceKeys;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.MultilineCommentReplacer;
import org.eclipse.xtext.formatting2.internal.SinglelineCodeCommentReplacer;
import org.eclipse.xtext.formatting2.internal.SinglelineDocCommentReplacer;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegionPart;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;
import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

@SuppressWarnings("java:S100")
public class STCoreFormatter extends AbstractFormatter2 {
	@Inject
	private STCoreGrammarAccess grammarAccess;

	protected void _format(final STCoreSource source, final IFormattableDocument document) {
		formatSource(source, document);
		for (final STStatement statement : source.getStatements()) {
			document.format(statement);
		}
	}

	protected void formatSource(final STSource source, final IFormattableDocument document) {
		StreamSupport.stream(textRegionExtensions.allSemanticRegions(source).spliterator(), false)
				.filter(it -> switch (it.getGrammarElement()) {
				case final Keyword element when element.getValue().matches("[_a-zA-Z]+") -> true; //$NON-NLS-1$
				case final RuleCall element when element.getRule() == grammarAccess.getNumericRule() -> true;
				case final RuleCall element when element.getRule() == grammarAccess.getSignedNumericRule() -> true;
				case final RuleCall element when element.getRule() == grammarAccess.getSTNumericLiteralTypeRule() ->
					true;
				case final RuleCall element when element.getRule() == grammarAccess.getSTDateLiteralTypeRule() -> true;
				case final RuleCall element when element.getRule() == grammarAccess.getSTTimeLiteralTypeRule() -> true;
				case final RuleCall element when element.getRule() == grammarAccess.getOrOperatorRule() -> true;
				case final RuleCall element when element.getRule() == grammarAccess.getXorOperatorRule() -> true;
				case final RuleCall element when element.getRule() == grammarAccess.getUnaryOperatorRule() -> true;
				default -> false;
				}).forEach(it -> document.addReplacer(new KeywordCaseTextReplacer(it)));

		textRegionExtensions.allRegionsFor(source)
				.keywords(grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0())
				.forEach(it -> document.append(it, IHiddenRegionFormatter::noSpace));
		textRegionExtensions.allRegionsFor(source)
				.keywords(grammarAccess.getSTPrimaryExpressionAccess().getRightParenthesisKeyword_0_2())
				.forEach(it -> document.prepend(it, IHiddenRegionFormatter::noSpace));
		textRegionExtensions.allRegionsFor(source).ruleCallsTo(grammarAccess.getML_COMMENTRule())
				.forEach(it -> document.append(it, format -> format.setNewLines(1, 2, 2)));
		textRegionExtensions.allRegionsFor(source).ruleCallsTo(grammarAccess.getSL_COMMENTRule())
				.forEach(it -> document.append(it, format -> format.setNewLines(1, 2, 2)));
	}

	protected void formatPackage(final STSource source, final IFormattableDocument document) {
		final ISemanticRegion packageKeyword = textRegionExtensions.regionFor(source).keyword("PACKAGE"); //$NON-NLS-1$
		final ISemanticRegion semicolonKeyword = textRegionExtensions.regionFor(source).keyword(";"); //$NON-NLS-1$
		document.append(document.prepend(packageKeyword, IHiddenRegionFormatter::noSpace),
				IHiddenRegionFormatter::oneSpace);
		document.append(document.prepend(semicolonKeyword, IHiddenRegionFormatter::noSpace), it -> it.setNewLines(2));
	}

	protected void formatImports(final EList<STImport> imports, final IFormattableDocument document) {
		imports.forEach(it -> {
			document.format(it);
			if (it == imports.getLast()) {
				document.append(textRegionExtensions.regionFor(it).keyword(";"), format -> format.setNewLines(2, 2, 2)); //$NON-NLS-1$
			} else {
				document.append(textRegionExtensions.regionFor(it).keyword(";"), format -> format.setNewLines(1, 1, 2)); //$NON-NLS-1$
			}
		});
	}

	protected void _format(final STImport imp, final IFormattableDocument document) {
		final ISemanticRegion importKeyword = textRegionExtensions.regionFor(imp)
				.keyword(grammarAccess.getSTImportAccess().getIMPORTKeyword_0());
		document.prepend(importKeyword, IHiddenRegionFormatter::noIndentation);
		document.append(importKeyword, IHiddenRegionFormatter::oneSpace);

		final ISemanticRegion semicolonKeyword = textRegionExtensions.regionFor(imp)
				.keyword(grammarAccess.getSTImportAccess().getSemicolonKeyword_2());
		document.prepend(semicolonKeyword, IHiddenRegionFormatter::noSpace);
	}

	protected void formatVarDeclarationBlocks(final EList<? extends STVarDeclarationBlock> varDeclarations,
			final IFormattableDocument document) {
		varDeclarations.forEach(it -> {
			document.format(it);
			if (it == varDeclarations.getLast()) {
				document.append(textRegionExtensions.regionFor(it).keyword("END_VAR"), //$NON-NLS-1$
						format -> format.setNewLines(1, 2, 2));
			} else {
				document.append(textRegionExtensions.regionFor(it).keyword("END_VAR"), //$NON-NLS-1$
						format -> format.setNewLines(1, 1, 2));
			}
		});
	}

	protected void _format(final STVarPlainDeclarationBlock varDeclarationBlock, final IFormattableDocument document) {
		formatVarDeclarationBlock(varDeclarationBlock, document, "VAR"); //$NON-NLS-1$
	}

	protected void _format(final STVarTempDeclarationBlock varDeclarationBlock, final IFormattableDocument document) {
		formatVarDeclarationBlock(varDeclarationBlock, document, "VAR_TEMP"); //$NON-NLS-1$
	}

	protected void _format(final STVarInputDeclarationBlock varDeclarationBlock, final IFormattableDocument document) {
		formatVarDeclarationBlock(varDeclarationBlock, document, "VAR_INPUT"); //$NON-NLS-1$
	}

	protected void _format(final STVarOutputDeclarationBlock varDeclarationBlock, final IFormattableDocument document) {
		formatVarDeclarationBlock(varDeclarationBlock, document, "VAR_OUTPUT"); //$NON-NLS-1$
	}

	protected void _format(final STVarInOutDeclarationBlock varDeclarationBlock, final IFormattableDocument document) {
		formatVarDeclarationBlock(varDeclarationBlock, document, "VAR_IN_OUT"); //$NON-NLS-1$
	}

	protected void formatVarDeclarationBlock(final STVarDeclarationBlock varDeclarationBlock,
			final IFormattableDocument document, final String begin) {
		final ISemanticRegion varKeyword = textRegionExtensions.regionFor(varDeclarationBlock).keyword(begin);
		if (varDeclarationBlock.isConstant()) {
			final ISemanticRegion constantKeyword = textRegionExtensions.regionFor(varDeclarationBlock)
					.keyword("CONSTANT"); //$NON-NLS-1$
			document.prepend(constantKeyword, IHiddenRegionFormatter::oneSpace);
			document.append(constantKeyword, IHiddenRegionFormatter::newLine);
		} else {
			document.append(varKeyword, IHiddenRegionFormatter::newLine);
		}

		final ISemanticRegion endVarKeyword = textRegionExtensions.regionFor(varDeclarationBlock).keyword("END_VAR"); //$NON-NLS-1$
		document.interior(varKeyword, endVarKeyword, IHiddenRegionFormatter::indent);
		varDeclarationBlock.getVarDeclarations().forEach(document::format);
	}

	protected void _format(final STVarDeclaration varDeclaration, final IFormattableDocument document) {
		textRegionExtensions.regionFor(varDeclaration).keywords(",").forEach(it -> { //$NON-NLS-1$
			document.prepend(it, IHiddenRegionFormatter::noSpace);
			document.append(it, IHiddenRegionFormatter::oneSpace);
		});

		textRegionExtensions.regionFor(varDeclaration).keywords(":", ":=").forEach(it -> { //$NON-NLS-1$ //$NON-NLS-2$
			document.surround(it, IHiddenRegionFormatter::oneSpace);
			document.append(it, format -> {
				format.autowrap();
				format.setOnAutowrap(
						new STCoreAutowrapFormatter(textRegionExtensions.nextHiddenRegion(varDeclaration)));
			});
		});

		document.prepend(textRegionExtensions.regionFor(varDeclaration).keyword(";"), IHiddenRegionFormatter::noSpace); //$NON-NLS-1$

		final ISemanticRegion typeRegion = textRegionExtensions.regionFor(varDeclaration)
				.assignment(grammarAccess.getSTVarDeclarationAccess().getTypeAssignment_5());
		if (typeRegion != null) {
			document.addReplacer(new QualifiedNameReplacer(typeRegion, varDeclaration.getType()));
		}

		if (varDeclaration.isArray()) {
			document.prepend(textRegionExtensions.regionFor(varDeclaration).keyword("["), //$NON-NLS-1$
					IHiddenRegionFormatter::noSpace);
			document.append(textRegionExtensions.regionFor(varDeclaration).keyword("]"), //$NON-NLS-1$
					IHiddenRegionFormatter::oneSpace);
			formatList(varDeclaration.getRanges(), document);
		}

		document.format(varDeclaration.getDefaultValue());
		document.prepend(varDeclaration.getPragma(), IHiddenRegionFormatter::oneSpace);
		document.format(varDeclaration.getPragma());
		document.append(varDeclaration, IHiddenRegionFormatter::newLine);
	}

	protected void _format(final STPragma pragma, final IFormattableDocument document) {
		formatBlock(pragma, pragma.getAttributes(), textRegionExtensions.regionFor(pragma).keyword("{"), //$NON-NLS-1$
				textRegionExtensions.regionFor(pragma).keyword("}"), document); //$NON-NLS-1$
	}

	protected void _format(final STAttribute attribute, final IFormattableDocument document) {
		document.surround(textRegionExtensions.regionFor(attribute).keyword(":="), IHiddenRegionFormatter::oneSpace); //$NON-NLS-1$
		document.format(attribute.getValue());
	}

	protected void _format(final STIfStatement ifStatement, final IFormattableDocument document) {
		final ISemanticRegion ifKeyword = textRegionExtensions.regionFor(ifStatement).keyword("IF"); //$NON-NLS-1$
		final ISemanticRegion thenKeyword = textRegionExtensions.regionFor(ifStatement).keyword("THEN"); //$NON-NLS-1$
		final ISemanticRegion endKeyword;
		if (!ifStatement.getElseifs().isEmpty()) {
			endKeyword = textRegionExtensions.regionFor(ifStatement.getElseifs().get(0)).keyword("ELSIF"); //$NON-NLS-1$
		} else if (ifStatement.getElse() != null) {
			endKeyword = textRegionExtensions.regionFor(ifStatement.getElse()).keyword("ELSE"); //$NON-NLS-1$
		} else {
			endKeyword = textRegionExtensions.regionFor(ifStatement).keyword("END_IF"); //$NON-NLS-1$
		}
		document.interior(thenKeyword, endKeyword, IHiddenRegionFormatter::indent);
		formatCondition(ifStatement.getCondition(), ifKeyword, thenKeyword, document);
		ifStatement.getStatements().forEach(document::format);
		ifStatement.getElseifs().forEach(document::format);
		document.format(ifStatement.getElse());
		document.surround(textRegionExtensions.regionFor(ifStatement).keyword(";"), IHiddenRegionFormatter::noSpace); //$NON-NLS-1$
		document.append(ifStatement, it -> it.setNewLines(1, 2, 2));
	}

	protected void _format(final STElseIfPart elseIfStatement, final IFormattableDocument document) {
		formatCondition(elseIfStatement.getCondition(),
				textRegionExtensions.regionFor(elseIfStatement).keyword("ELSIF"), //$NON-NLS-1$
				textRegionExtensions.regionFor(elseIfStatement).keyword("THEN"), document); //$NON-NLS-1$
		elseIfStatement.getStatements().forEach(it -> {
			document.surround(it, IHiddenRegionFormatter::indent);
			document.format(it);
		});
	}

	protected void _format(final STElsePart elseStatement, final IFormattableDocument document) {
		document.append(textRegionExtensions.regionFor(elseStatement).keyword("ELSE"), IHiddenRegionFormatter::newLine); //$NON-NLS-1$
		elseStatement.getStatements().forEach(it -> {
			document.surround(it, IHiddenRegionFormatter::indent);
			document.format(it);
		});
	}

	protected void _format(final STForStatement forStatement, final IFormattableDocument document) {
		final ISemanticRegion doKeyword = textRegionExtensions.regionFor(forStatement).keyword("DO"); //$NON-NLS-1$
		final ISemanticRegion endKeyword = textRegionExtensions.regionFor(forStatement).keyword("END_FOR"); //$NON-NLS-1$
		document.append(doKeyword, IHiddenRegionFormatter::newLine);
		document.interior(doKeyword, endKeyword, IHiddenRegionFormatter::indent);
		final STCoreAutowrapFormatter autowrapFormatter = new STCoreAutowrapFormatter(
				doKeyword.getPreviousHiddenRegion());
		textRegionExtensions.regionFor(forStatement).keywords(":=").forEach(keyword -> { //$NON-NLS-1$
			document.surround(keyword, IHiddenRegionFormatter::oneSpace);
			document.append(keyword, it -> {
				it.autowrap();
				it.setOnAutowrap(autowrapFormatter);
			});
		});
		textRegionExtensions.regionFor(forStatement).keywords("TO", "BY").forEach(keyword -> { //$NON-NLS-1$ //$NON-NLS-2$
			document.surround(keyword, IHiddenRegionFormatter::oneSpace);
			document.prepend(keyword, it -> {
				it.autowrap();
				it.setOnAutowrap(autowrapFormatter);
			});
		});
		forStatement.getStatements().forEach(document::format);
		document.surround(textRegionExtensions.regionFor(forStatement).keyword(";"), IHiddenRegionFormatter::noSpace); //$NON-NLS-1$
		document.append(forStatement, it -> it.setNewLines(1, 2, 2));
	}

	protected void _format(final STWhileStatement whileStatement, final IFormattableDocument document) {
		final ISemanticRegion beginKeyword = textRegionExtensions.regionFor(whileStatement).keyword("WHILE"); //$NON-NLS-1$
		final ISemanticRegion doKeyword = textRegionExtensions.regionFor(whileStatement).keyword("DO"); //$NON-NLS-1$
		final ISemanticRegion endKeyword = textRegionExtensions.regionFor(whileStatement).keyword("END_WHILE"); //$NON-NLS-1$
		document.append(doKeyword, IHiddenRegionFormatter::newLine);
		document.interior(doKeyword, endKeyword, IHiddenRegionFormatter::indent);
		formatCondition(whileStatement.getCondition(), beginKeyword, doKeyword, document);
		whileStatement.getStatements().forEach(document::format);
		document.surround(textRegionExtensions.regionFor(whileStatement).keyword(";"), IHiddenRegionFormatter::noSpace); //$NON-NLS-1$
		document.append(whileStatement, it -> it.setNewLines(1, 2, 2));
	}

	protected void _format(final STRepeatStatement repeatStatement, final IFormattableDocument document) {
		final ISemanticRegion beginKeyword = textRegionExtensions.regionFor(repeatStatement).keyword("REPEAT"); //$NON-NLS-1$
		final ISemanticRegion untilKeyword = textRegionExtensions.regionFor(repeatStatement).keyword("UNTIL"); //$NON-NLS-1$
		final ISemanticRegion endKeyword = textRegionExtensions.regionFor(repeatStatement).keyword("END_REPEAT"); //$NON-NLS-1$
		document.append(beginKeyword, IHiddenRegionFormatter::newLine);
		document.interior(beginKeyword, untilKeyword, IHiddenRegionFormatter::indent);
		document.format(repeatStatement.getCondition());
		document.prepend(endKeyword, format -> {
			format.newLine();
			format.noSpace();
		});
		repeatStatement.getStatements().forEach(document::format);
		document.surround(textRegionExtensions.regionFor(repeatStatement).keyword(";"), //$NON-NLS-1$
				IHiddenRegionFormatter::noSpace);
		document.append(repeatStatement, it -> it.setNewLines(1, 2, 2));
	}

	protected void _format(final STCaseStatement caseStatement, final IFormattableDocument document) {
		final ISemanticRegion beginKeyword = textRegionExtensions.regionFor(caseStatement).keyword("OF"); //$NON-NLS-1$
		final ISemanticRegion endKeyword = textRegionExtensions.regionFor(caseStatement).keyword("END_CASE"); //$NON-NLS-1$
		document.append(beginKeyword, IHiddenRegionFormatter::newLine);
		document.interior(beginKeyword, endKeyword, IHiddenRegionFormatter::indent);
		document.surround(textRegionExtensions.regionFor(caseStatement).keyword(";"), IHiddenRegionFormatter::noSpace); //$NON-NLS-1$
		caseStatement.getCases().forEach(document::format);
		document.format(caseStatement.getElse());
		document.append(caseStatement, it -> it.setNewLines(1, 2, 2));
	}

	protected void _format(final STCaseCases stCase, final IFormattableDocument document) {
		final ISemanticRegion colonKeyword = textRegionExtensions.regionFor(stCase).keyword(":"); //$NON-NLS-1$
		formatList(stCase.getConditions(), document);
		document.prepend(colonKeyword, IHiddenRegionFormatter::oneSpace);
		document.append(colonKeyword, IHiddenRegionFormatter::newLine);
		stCase.getStatements().forEach(it -> {
			document.surround(it, IHiddenRegionFormatter::indent);
			document.format(it);
		});
	}

	protected void _format(final STAssignment assignment, final IFormattableDocument document) {
		final ISemanticRegion assignKeyword = textRegionExtensions.regionFor(assignment).keyword(":="); //$NON-NLS-1$
		final ISemanticRegion semicolonKeyword = textRegionExtensions.regionFor(assignment).keyword(";"); //$NON-NLS-1$
		document.format(assignment.getLeft());
		document.surround(assignKeyword, IHiddenRegionFormatter::oneSpace);
		document.append(assignKeyword, it -> {
			it.autowrap();
			it.setOnAutowrap(new STCoreAutowrapFormatter(textRegionExtensions.nextHiddenRegion(assignment)));
		});
		document.format(assignment.getRight());
		document.prepend(semicolonKeyword, IHiddenRegionFormatter::noSpace);
		document.append(semicolonKeyword, it -> it.setNewLines(1, 1, 2));
	}

	protected void _format(final STStatement statement, final IFormattableDocument document) {
		final ISemanticRegion semicolonKeyword = textRegionExtensions.regionFor(statement).keyword(";"); //$NON-NLS-1$
		document.surround(semicolonKeyword, IHiddenRegionFormatter::noSpace);
		document.append(semicolonKeyword, it -> it.setNewLines(1, 1, 2));
	}

	protected static void _format(final STElementaryInitializerExpression initExpression,
			final IFormattableDocument document) {
		document.format(initExpression.getValue());
	}

	protected void _format(final STArrayInitializerExpression arrayInitExpression,
			final IFormattableDocument document) {
		document.append(
				textRegionExtensions.regionFor(arrayInitExpression)
						.keyword(grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0()),
				IHiddenRegionFormatter::noSpace);
		document.prepend(
				textRegionExtensions.regionFor(arrayInitExpression).keyword(
						grammarAccess.getSTArrayInitializerExpressionAccess().getRightSquareBracketKeyword_3()),
				IHiddenRegionFormatter::noSpace);
		formatList(arrayInitExpression.getValues(), document);
	}

	protected static void _format(final STSingleArrayInitElement element, final IFormattableDocument document) {
		document.format(element.getInitExpression());
	}

	protected void _format(final STRepeatArrayInitElement element, final IFormattableDocument document) {
		document.surround(
				textRegionExtensions.regionFor(element)
						.keyword(grammarAccess.getSTRepeatArrayInitElementAccess().getLeftParenthesisKeyword_1()),
				IHiddenRegionFormatter::noSpace);
		formatList(element.getInitExpressions(), document);
		document.prepend(
				textRegionExtensions.regionFor(element)
						.keyword(grammarAccess.getSTRepeatArrayInitElementAccess().getRightParenthesisKeyword_4()),
				IHiddenRegionFormatter::noSpace);
	}

	protected void _format(final STStructInitializerExpression structInitializerExpression,
			final IFormattableDocument document) {
		document.surround(textRegionExtensions.regionFor(structInitializerExpression).keyword("#"), //$NON-NLS-1$
				IHiddenRegionFormatter::noSpace);
		document.append(
				textRegionExtensions.regionFor(structInitializerExpression)
						.keyword(grammarAccess.getSTStructInitializerExpressionAccess().getLeftParenthesisKeyword_1()),
				IHiddenRegionFormatter::noSpace);
		formatList(structInitializerExpression.getValues(), document);
		document.prepend(
				textRegionExtensions.regionFor(structInitializerExpression)
						.keyword(grammarAccess.getSTStructInitializerExpressionAccess().getRightParenthesisKeyword_4()),
				IHiddenRegionFormatter::noSpace);
	}

	protected void _format(final STStructInitElement element, final IFormattableDocument document) {
		final ISemanticRegion assignmentKeyword = textRegionExtensions.regionFor(element).keyword(":="); //$NON-NLS-1$
		document.surround(assignmentKeyword, IHiddenRegionFormatter::oneSpace);
		document.append(assignmentKeyword, format -> {
			format.autowrap();
			format.setOnAutowrap(new STCoreAutowrapFormatter(textRegionExtensions.nextHiddenRegion(element)));
		});
		document.format(element.getValue());
	}

	protected void _format(final STBinaryExpression binaryExpression, final IFormattableDocument document) {
		final ISemanticRegion opRegion = textRegionExtensions.regionFor(binaryExpression)
				.feature(STCorePackage.Literals.ST_BINARY_EXPRESSION__OP);
		if (opRegion != null) {
			if (binaryExpression.getOp() != STBinaryOperator.RANGE) {
				document.surround(opRegion, IHiddenRegionFormatter::oneSpace);
				document.prepend(opRegion, format -> {
					format.autowrap((opRegion.getLength()
							+ textRegionExtensions.regionForEObject(binaryExpression.getRight()).getLength()));
					format.setOnAutowrap(
							new STCoreAutowrapFormatter(textRegionExtensions.nextHiddenRegion(binaryExpression)));
				});
			}
			switch (binaryExpression.getOp()) {
			case AMPERSAND -> document.addReplacer(new KeywordTextReplacer(opRegion, STBinaryOperator.AND.toString()));
			case AND -> document.addReplacer(new KeywordCaseTextReplacer(opRegion));
			default -> {
				// do nothing
			}
			}
		}
		document.format(binaryExpression.getLeft());
		document.format(binaryExpression.getRight());
	}

	protected void _format(final STUnaryExpression unaryExpression, final IFormattableDocument document) {
		final ISemanticRegion opRange = textRegionExtensions.regionFor(unaryExpression)
				.feature(STCorePackage.Literals.ST_UNARY_EXPRESSION__OP);
		if (unaryExpression.getOp() == STUnaryOperator.NOT) {
			document.append(opRange, IHiddenRegionFormatter::oneSpace);
		} else {
			document.append(opRange, IHiddenRegionFormatter::noSpace);
		}
		document.format(unaryExpression.getExpression());
	}

	protected void _format(final STMemberAccessExpression mExpression, final IFormattableDocument document) {
		final ISemanticRegion dotKeyword = textRegionExtensions.regionFor(mExpression).keyword("."); //$NON-NLS-1$
		document.surround(dotKeyword, IHiddenRegionFormatter::noSpace);
		document.prepend(dotKeyword, format -> {
			format.autowrap(textRegionExtensions.regionForEObject(mExpression.getMember()).getLength());
			format.setOnAutowrap(new STCoreAutowrapFormatter(textRegionExtensions.nextHiddenRegion(mExpression)));
		});
		document.format(mExpression.getMember());
		document.format(mExpression.getReceiver());
	}

	protected void _format(final STFeatureExpression featureExpression, final IFormattableDocument document) {
		final ISemanticRegion semicolonKeyword = textRegionExtensions.regionFor(featureExpression).keyword(";"); //$NON-NLS-1$
		document.surround(
				textRegionExtensions.regionFor(featureExpression)
						.keyword(grammarAccess.getSTFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()),
				IHiddenRegionFormatter::noSpace);
		document.prepend(
				textRegionExtensions.regionFor(featureExpression)
						.keyword(grammarAccess.getSTFeatureExpressionAccess().getRightParenthesisKeyword_2_2()),
				IHiddenRegionFormatter::noSpace);
		formatList(featureExpression.getParameters(), document);
		document.prepend(semicolonKeyword, IHiddenRegionFormatter::noSpace);
		document.append(semicolonKeyword, it -> it.setNewLines(1, 1, 2));
	}

	protected void _format(final STBuiltinFeatureExpression featureExpression, final IFormattableDocument document) {
		final ISemanticRegion semicolonKeyword = textRegionExtensions.regionFor(featureExpression).keyword(";"); //$NON-NLS-1$
		document.surround(
				textRegionExtensions.regionFor(featureExpression).keyword(
						grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()),
				IHiddenRegionFormatter::noSpace);
		document.prepend(
				textRegionExtensions.regionFor(featureExpression)
						.keyword(grammarAccess.getSTBuiltinFeatureExpressionAccess().getRightParenthesisKeyword_2_2()),
				IHiddenRegionFormatter::noSpace);
		formatList(featureExpression.getParameters(), document);
		document.prepend(semicolonKeyword, IHiddenRegionFormatter::noSpace);
		document.append(semicolonKeyword, it -> it.setNewLines(1, 1, 2));
	}

	protected void _format(final STMultibitPartialExpression mBPExpression, final IFormattableDocument document) {
		document.surround(
				textRegionExtensions.regionFor(mBPExpression)
						.assignment(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierAssignment_1()),
				IHiddenRegionFormatter::noSpace);
		document.append(
				textRegionExtensions.regionFor(mBPExpression).keyword(
						grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0()),
				IHiddenRegionFormatter::noSpace);
		document.prepend(
				textRegionExtensions.regionFor(mBPExpression).keyword(
						grammarAccess.getSTMultibitPartialExpressionAccess().getRightParenthesisKeyword_2_1_2()),
				IHiddenRegionFormatter::noSpace);
		document.format(mBPExpression.getExpression());
	}

	protected static void _format(final STCallUnnamedArgument unnamedArgument, final IFormattableDocument document) {
		document.format(unnamedArgument.getArgument());
	}

	protected void _format(final STCallNamedInputArgument namedInputArgument, final IFormattableDocument document) {
		final ISemanticRegion assignmentKeyword = textRegionExtensions.regionFor(namedInputArgument).keyword(":="); //$NON-NLS-1$
		document.surround(assignmentKeyword, IHiddenRegionFormatter::oneSpace);
		document.append(assignmentKeyword, format -> {
			format.autowrap();
			format.setOnAutowrap(
					new STCoreAutowrapFormatter(textRegionExtensions.nextHiddenRegion(namedInputArgument)));
		});
		document.format(namedInputArgument.getArgument());
	}

	protected void _format(final STCallNamedOutputArgument namedOutputArgument, final IFormattableDocument document) {
		final ISemanticRegion assignmentKeyword = textRegionExtensions.regionFor(namedOutputArgument).keyword("=>"); //$NON-NLS-1$
		if (namedOutputArgument.isNot()) {
			document.append(textRegionExtensions.regionFor(namedOutputArgument).keyword("NOT"), //$NON-NLS-1$
					IHiddenRegionFormatter::oneSpace);
		}
		document.surround(assignmentKeyword, IHiddenRegionFormatter::oneSpace);
		document.append(assignmentKeyword, it -> {
			it.autowrap();
			it.setOnAutowrap(new STCoreAutowrapFormatter(textRegionExtensions.nextHiddenRegion(namedOutputArgument)));
		});
		document.format(namedOutputArgument.getArgument());
	}

	protected void _format(final STArrayAccessExpression arrayAccessExpression, final IFormattableDocument document) {
		document.format(arrayAccessExpression.getReceiver());
		document.append(
				textRegionExtensions.regionFor(arrayAccessExpression)
						.keyword(grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1()),
				IHiddenRegionFormatter::noSpace);
		document.prepend(
				textRegionExtensions.regionFor(arrayAccessExpression)
						.keyword(grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4()),
				IHiddenRegionFormatter::noSpace);
		formatList(arrayAccessExpression.getIndex(), document);
	}

	protected void formatBlock(final EObject blockElement, final List<? extends EObject> listElements,
			final ISemanticRegion begin, final ISemanticRegion end, final IFormattableDocument document) {
		if (textRegionExtensions.isMultiline(blockElement)) {
			formatBlockMultiline(listElements, begin, end, document);
		} else {
			document.formatConditionally(begin.getOffset(), (end.getEndOffset() - begin.getOffset()),
					subDocument -> formatBlockSingleline(listElements, begin, end, subDocument.requireFitsInLine()),
					subDocument -> formatBlockMultiline(listElements, begin, end, subDocument));
		}
	}

	protected void formatBlockSingleline(final List<? extends EObject> listElements, final ISemanticRegion begin,
			final ISemanticRegion end, final IFormattableDocument document) {
		document.append(begin, IHiddenRegionFormatter::noSpace);
		document.prepend(end, IHiddenRegionFormatter::noSpace);
		formatList(listElements, document);
	}

	protected void formatBlockMultiline(final List<? extends EObject> listElements, final ISemanticRegion begin,
			final ISemanticRegion end, final IFormattableDocument document) {
		document.append(begin, IHiddenRegionFormatter::newLine);
		document.interior(begin, end, IHiddenRegionFormatter::indent);
		document.prepend(end, IHiddenRegionFormatter::newLine);
		formatListMultiline(listElements, document);
	}

	protected void formatList(final List<? extends EObject> semanticElements, final IFormattableDocument document) {
		if (!semanticElements.isEmpty()) {
			final STCoreAutowrapFormatter autowrapFormatter = new STCoreAutowrapFormatter(
					textRegionExtensions.nextHiddenRegion(semanticElements.getLast()));
			semanticElements.forEach(it -> {
				final int autowrapLength = textRegionExtensions.regionForEObject(it).getLength();
				final ISemanticRegion commaKeyword = textRegionExtensions.immediatelyPreceding(it).keyword(","); //$NON-NLS-1$
				document.prepend(commaKeyword, IHiddenRegionFormatter::noSpace);
				document.append(commaKeyword, format -> {
					format.oneSpace();
					format.autowrap(autowrapLength);
					format.setOnAutowrap(autowrapFormatter);
				});
				document.format(it);
			});
		}
	}

	protected void formatListMultiline(final List<? extends EObject> semanticElements,
			final IFormattableDocument document) {
		semanticElements.forEach(it -> {
			final ISemanticRegion commaKeyword = textRegionExtensions.immediatelyPreceding(it).keyword(","); //$NON-NLS-1$
			document.prepend(commaKeyword, IHiddenRegionFormatter::noSpace);
			document.append(commaKeyword, IHiddenRegionFormatter::newLine);
			document.format(it);
		});
	}

	protected void formatCondition(final EObject semanticElement, final ISemanticRegion begin,
			final ISemanticRegion end, final IFormattableDocument document) {
		if (textRegionExtensions.isMultiline(semanticElement)) {
			document.format(semanticElement);
			document.prepend(end, IHiddenRegionFormatter::newLine);
		} else {
			document.formatConditionally(begin.getOffset(), (end.getEndOffset() - begin.getOffset()),
					subDocument -> formatSinglelineCondition(semanticElement, end, subDocument.requireFitsInLine()),
					subDocument -> formatMultilineCondition(semanticElement, end, subDocument));
		}
		document.append(end, IHiddenRegionFormatter::newLine);
	}

	protected static void formatSinglelineCondition(final EObject semanticElement, final ISemanticRegion end,
			final IFormattableDocument document) {
		document.format(semanticElement);
		document.prepend(end, IHiddenRegionFormatter::oneSpace);
	}

	protected static void formatMultilineCondition(final EObject semanticElement, final ISemanticRegion end,
			final IFormattableDocument document) {
		document.format(semanticElement);
		document.prepend(end, IHiddenRegionFormatter::newLine);
	}

	@Override
	@SuppressWarnings("restriction")
	public ITextReplacer createWhitespaceReplacer(final ITextSegment hiddens,
			final IHiddenRegionFormatting formatting) {
		return new WhitespaceReplacer(hiddens, formatting) {
			@Override
			public int computeNewLineCount(final ITextReplacerContext context) {
				final Integer newLineDefault = getFormatting().getNewLineDefault();
				final Integer newLineMin = getFormatting().getNewLineMin();
				final Integer newLineMax = getFormatting().getNewLineMax();
				if (newLineMin != null || newLineDefault != null || newLineMax != null) {
					if (!(getRegion() instanceof final IHiddenRegion hiddenRegion) || !hiddenRegion.isUndefined()) {
						int lineCount = 0;
						if (getRegion() instanceof final IHiddenRegionPart hiddenRegionPart) {
							if (hiddenRegionPart.getPreviousHiddenPart() instanceof IComment) {
								lineCount = getRegion().getLineCount();
							} else {
								lineCount = getRegion().getLineCount() - 1;
							}
						} else {
							lineCount = getRegion().getLineCount() - 1;
						}
						if (newLineMin != null && newLineMin.intValue() > lineCount) {
							lineCount = newLineMin.intValue();
						}
						if (newLineMax != null && newLineMax.intValue() < lineCount) {
							lineCount = newLineMax.intValue();
						}
						return lineCount;
					}
					if (newLineDefault != null) {
						return newLineDefault.intValue();
					}
					if (newLineMin != null) {
						return newLineMin.intValue();
					}
					return newLineMax.intValue();
				}
				return 0;
			}
		};
	}

	@Override
	@SuppressWarnings("restriction")
	public ITextReplacer createCommentReplacer(final IComment comment) {
		final Integer tabWidth = getPreference(FormatterPreferenceKeys.tabWidth);
		final EObject grammarElement = comment.getGrammarElement();
		if (grammarElement instanceof final AbstractRule rule) {
			final String ruleName = rule.getName();
			if (ruleName.startsWith("ML")) { //$NON-NLS-1$
				return new MultilineCommentReplacer(comment, '*') {
					@Override
					public ITextReplacerContext createReplacements(final ITextReplacerContext context) {
						final ITextRegionAccess access = getComment().getTextRegionAccess();
						final ITextSegment region = access.regionForOffset(getComment().getOffset(),
								getComment().getLength());
						return commentReplacementContext(context, region, ruleName);
					}

					@Override
					public void configureWhitespace(final WhitespaceReplacer leading,
							final WhitespaceReplacer trailing) {
						if (!leading.getRegion().isMultiline()) {
							enforceSingleSpace(leading);
						}
					}
				};
			}
			if (ruleName.startsWith("SL")) { //$NON-NLS-1$
				if (comment.getLineRegions().get(0).getIndentation().getLength() > 0) {
					return new SinglelineDocCommentReplacer(comment, "//") { //$NON-NLS-1$
						@Override
						public ITextReplacerContext createReplacements(final ITextReplacerContext context) {
							final ITextRegionAccess access = getComment().getTextRegionAccess();
							final ITextSegment region = access.regionForOffset(getComment().getOffset(),
									getComment().getLength());
							return commentReplacementContext(context, region, ruleName);
						}

						@Override
						public void configureWhitespace(final WhitespaceReplacer leading,
								final WhitespaceReplacer trailing) {
							leading.getFormatting().setSpace(" ".repeat(tabWidth.intValue())); //$NON-NLS-1$
							if (leading.getFormatting().getIndentationDecrease() != null) {
								trailing.getFormatting()
										.setIndentationDecrease(leading.getFormatting().getIndentationDecrease());
								leading.getFormatting().setIndentationDecrease(null);
							}
						}
					};
				}
				return new SinglelineCodeCommentReplacer(comment, "//") { //$NON-NLS-1$
					@Override
					public ITextReplacerContext createReplacements(final ITextReplacerContext context) {
						final ITextRegionAccess access = getComment().getTextRegionAccess();
						final ITextSegment region = access.regionForOffset(getComment().getOffset(),
								getComment().getLength());
						return commentReplacementContext(context, region, ruleName);
					}

					@Override
					public void configureWhitespace(final WhitespaceReplacer leading,
							final WhitespaceReplacer trailing) {
						leading.getFormatting().setSpace(" ".repeat(tabWidth.intValue())); //$NON-NLS-1$
						if (leading.getFormatting().getIndentationDecrease() != null) {
							trailing.getFormatting()
									.setIndentationDecrease(leading.getFormatting().getIndentationDecrease());
							leading.getFormatting().setIndentationDecrease(null);
						}
					}
				};
			}
		}
		final String elementName = new GrammarElementTitleSwitch().showQualified().showRule().doSwitch(grammarElement);
		throw new IllegalStateException("No ITextReplacer configured for " + elementName); //$NON-NLS-1$
	}

	private ITextReplacerContext commentReplacementContext(final ITextReplacerContext context,
			final ITextSegment region, final String name) {
		final String lineSeparator = context.getNewLinesString(1);
		final Integer maxLineWidth = getPreference(FormatterPreferenceKeys.maxLineWidth);
		final boolean isML = name.startsWith("ML"); //$NON-NLS-1$
		final int lengthBeforeComment = context.getLeadingCharsInLineCount();
		final String spaceBeforeComment = " ".repeat(lengthBeforeComment); //$NON-NLS-1$
		final int commentLineLength = (((maxLineWidth).intValue() - lengthBeforeComment) - (isML ? 6 : 3));
		if (commentLineLength < 1) {
			return context;
		}
		final String commentString;
		if (isML) {
			commentString = region.getText().replaceFirst("^[(/]\\*", "").replaceFirst("\\*[)/]$", "") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					.replaceAll("(?m)^[\\s&&[^\r\n]]*\\* ", "").replaceAll("[\\s&&[^\r\n]]+", " ").trim(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		} else {
			commentString = region.getText().replaceFirst("^//", "").replaceFirst("\n$", "").replaceAll("\\s+", " ") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
					.trim();
		}
		final Pattern pattern = Pattern.compile(
				(((("[\\s&&[^\r\n]]*(?:(\\S{" + Integer.valueOf(commentLineLength)) + "})|([[\\s&&[^\r\n]]\\S]{1,") //$NON-NLS-1$ //$NON-NLS-2$
						+ Integer.valueOf(commentLineLength)) + "}(?!\\S)[\r\n]*))")); //$NON-NLS-1$
		final Matcher matcher = pattern.matcher(commentString.replace("$", "\\$")); //$NON-NLS-1$ //$NON-NLS-2$
		String replacement = matcher.replaceAll(m -> {
			final String g = Objects.requireNonNullElse(m.group(1), m.group(2));
			return (isML ? spaceBeforeComment + " * " : "// ") + g //$NON-NLS-1$ //$NON-NLS-2$
					+ (g.indexOf(lineSeparator) == -1 ? lineSeparator : ""); //$NON-NLS-1$
		});
		replacement = (isML ? "(* " : "") + replacement.trim() + (isML ? " *)" : lineSeparator); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		replacement = replacement.replaceFirst("^\\(\\*\\s*\\* ", "(* "); //$NON-NLS-1$ //$NON-NLS-2$
		replacement = replacement.replaceAll(lineSeparator + "(?=" + lineSeparator + ")", //$NON-NLS-1$ //$NON-NLS-2$
				lineSeparator + spaceBeforeComment + " * "); //$NON-NLS-1$
		if (!Objects.equals(region.getText(), replacement)) {
			context.addReplacement(region.replaceWith(replacement));
		}
		return context;
	}

	@Override
	public ITextReplacerContext createTextReplacerContext(final IFormattableDocument document) {
		return new STCoreTextReplacerContext(document);
	}

	@Override
	public void format(final Object object, final IFormattableDocument document) {
		switch (object) { // NOSONAR
		case final STArrayAccessExpression element -> _format(element, document);
		case final STAssignment element -> _format(element, document);
		case final STBinaryExpression element -> _format(element, document);
		case final STBuiltinFeatureExpression element -> _format(element, document);
		case final STFeatureExpression element -> _format(element, document);
		case final STMemberAccessExpression element -> _format(element, document);
		case final STMultibitPartialExpression element -> _format(element, document);
		case final STUnaryExpression element -> _format(element, document);
		case final STVarDeclaration element -> _format(element, document);
		case final STArrayInitializerExpression element -> _format(element, document);
		case final STCallNamedInputArgument element -> _format(element, document);
		case final STCallNamedOutputArgument element -> _format(element, document);
		case final STCallUnnamedArgument element -> _format(element, document);
		case final STCaseStatement element -> _format(element, document);
		case final STCoreSource element -> _format(element, document);
		case final STElementaryInitializerExpression element -> _format(element, document);
		case final STForStatement element -> _format(element, document);
		case final STIfStatement element -> _format(element, document);
		case final STImport element -> _format(element, document);
		case final STRepeatArrayInitElement element -> _format(element, document);
		case final STRepeatStatement element -> _format(element, document);
		case final STSingleArrayInitElement element -> _format(element, document);
		case final STStructInitializerExpression element -> _format(element, document);
		case final STVarInOutDeclarationBlock element -> _format(element, document);
		case final STVarInputDeclarationBlock element -> _format(element, document);
		case final STVarOutputDeclarationBlock element -> _format(element, document);
		case final STVarPlainDeclarationBlock element -> _format(element, document);
		case final STVarTempDeclarationBlock element -> _format(element, document);
		case final STWhileStatement element -> _format(element, document);
		case final STAttribute element -> _format(element, document);
		case final STCaseCases element -> _format(element, document);
		case final STElseIfPart element -> _format(element, document);
		case final STElsePart element -> _format(element, document);
		case final STPragma element -> _format(element, document);
		case final STStatement element -> _format(element, document);
		case final STStructInitElement element -> _format(element, document);
		case final STVarDeclarationBlock element -> _format(element, document);
		case final EObject element -> _format(element, document);
		case final XtextResource element -> _format(element, document);
		case null, default -> _format(object, document);
		}
	}
}
