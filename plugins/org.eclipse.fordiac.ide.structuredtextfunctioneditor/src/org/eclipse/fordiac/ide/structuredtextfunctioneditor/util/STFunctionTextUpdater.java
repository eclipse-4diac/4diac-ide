/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.structuredtextcore.documentation.STCoreCommentDocumentationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.ReplaceRegion;

public final class STFunctionTextUpdater {

	private static Optional<STVarDeclaration> findParameter(final FunctionFBType type, final String text,
			final String name) {
		final URI uri = type.eResource() != null ? type.eResource().getURI() : null;
		final var parseResult = STFunctionParseUtil2.parse(text,
				STFunctionParseUtil2.getGrammarAccess().getSTFunctionSourceRule(), uri, type, null, null);
		if (!(parseResult.getRootASTElement() instanceof final STFunctionSource source)) {
			return Optional.empty();
		}
		return source.getFunctions().stream().filter(function -> type.getName().equals(function.getName())).findFirst()
				.flatMap(function -> findParameter(function, name));
	}

	private static Optional<STVarDeclaration> findParameter(final STFunction function, final String name) {
		return Stream.of(function.getInputParameters(), function.getOutputParameters(), function.getInOutParameters())
				.flatMap(List::stream).filter(STVarDeclaration.class::isInstance).map(STVarDeclaration.class::cast)
				.filter(declaration -> name.equals(declaration.getName())).findFirst();
	}

	// initial value
	public static Optional<ReplaceRegion> changeParameterInitialValue(final FunctionFBType type, final String text,
			final String parameterName, final String newValue) {
		return findParameter(type, text, parameterName)
				.map(declaration -> replaceInitialValue(text, declaration, newValue));
	}

	private static ReplaceRegion replaceInitialValue(final String text, final STVarDeclaration declaration,
			final String newValue) {
		final INode declarationNode = NodeModelUtils.getNode(declaration);
		final int initializerOffset = findInitializerOffset(declarationNode);
		final int start = text.substring(0, initializerOffset).stripTrailing().length();
		final int end = declaration.getDefaultValue() != null
				? NodeModelUtils.getNode(declaration.getDefaultValue()).getEndOffset()
				: initializerOffset;
		return new ReplaceRegion(start, end - start, newValue.isEmpty() ? "" : " := " + newValue); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static int findInitializerOffset(final INode declarationNode) {
		final var access = STFunctionParseUtil2.getGrammarAccess().getSTVarDeclarationAccess();
		final Set<EObject> initializerStart = Set.of(access.getColonEqualsSignKeyword_7_0(),
				access.getPragmaSTPragmaParserRuleCall_8_0(), access.getSemicolonKeyword_9());
		for (final INode node : declarationNode.getAsTreeIterable()) {
			if (initializerStart.contains(node.getGrammarElement())) {
				return node.getOffset();
			}
		}
		return declarationNode.getEndOffset();
	}

	// comment
	public static Optional<ReplaceRegion> changeParameterComment(final FunctionFBType type, final String text,
			final String parameterName, final String newComment) {
		return findParameter(type, text, parameterName)
				.map(declaration -> replaceComment(text, declaration, newComment));
	}

	private static ReplaceRegion replaceComment(final String text, final STVarDeclaration declaration,
			final String newComment) {
		final INode declarationNode = NodeModelUtils.getNode(declaration);
		final INode lineComment = STCoreCommentDocumentationProvider
				.getSingleLineDocumentationForSTVarDeclarationNodes(declaration).stream().findFirst().orElse(null);
		final INode blockComment = findBlockComment(declaration);

		if (!newComment.isEmpty()) {
			if (lineComment != null) {
				return new ReplaceRegion(lineComment.getOffset(), getLineCommentLength(lineComment),
						"// " + newComment); //$NON-NLS-1$
			}
			if (blockComment != null) {
				final boolean iecStyle = blockComment.getText().startsWith("(*"); //$NON-NLS-1$
				final String commentStart = iecStyle ? "(* " : "/* "; //$NON-NLS-1$ //$NON-NLS-2$
				final String commentEnd = iecStyle ? " *)" : " */"; //$NON-NLS-1$ //$NON-NLS-2$
				return new ReplaceRegion(blockComment.getTextRegion(), commentStart + newComment + commentEnd);
			}
			return new ReplaceRegion(declarationNode.getEndOffset(), 0, " // " + newComment); //$NON-NLS-1$
		}

		// remove comment
		final int start = blockComment != null ? blockComment.getOffset() : declarationNode.getOffset();
		final int end = lineComment != null ? lineComment.getOffset() + getLineCommentLength(lineComment)
				: declarationNode.getEndOffset();
		final int declarationEnd = lineComment != null
				? text.substring(0, lineComment.getOffset()).stripTrailing().length()
				: declarationNode.getEndOffset();
		return new ReplaceRegion(start, end - start, text.substring(declarationNode.getOffset(), declarationEnd));
	}

	private static INode findBlockComment(final STVarDeclaration declaration) {
		final TerminalRule blockCommentRule = STFunctionParseUtil2.getGrammarAccess().getML_COMMENTRule();
		INode blockComment = null;
		for (final ILeafNode leaf : NodeModelUtils.getNode(declaration).getLeafNodes()) {
			if (!leaf.isHidden()) {
				break; // only comments before the first token of the declaration
			}
			if (leaf.getGrammarElement() == blockCommentRule) {
				blockComment = leaf; // the last one before the declaration is read
			}
		}
		return blockComment;
	}

	private static int getLineCommentLength(final INode lineComment) {
		return lineComment.getText().stripTrailing().length();
	}

	private STFunctionTextUpdater() {
		throw new UnsupportedOperationException();
	}
}
