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
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *   Martin Jobst - algorithm and method name format
 *                - return type autowrap support
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.formatting2;

import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreAutowrapFormatter;
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreFormatter;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;

import com.google.inject.Inject;

@SuppressWarnings("java:S100")
public class STAlgorithmFormatter extends STCoreFormatter {
	@Inject
	private STAlgorithmGrammarAccess grammarAccess;

	protected void _format(final STAlgorithmSource source, final IFormattableDocument document) {
		formatSource(source, document);
		source.getElements().forEach(document::format);
	}

	protected void _format(final STAlgorithm algorithm, final IFormattableDocument document) {
		final ISemanticRegion algorithmKeyword = textRegionExtensions.regionFor(algorithm).keyword("ALGORITHM"); //$NON-NLS-1$
		final ISemanticRegion endKeyword = textRegionExtensions.regionFor(algorithm).keyword("END_ALGORITHM"); //$NON-NLS-1$

		document.prepend(algorithmKeyword, IHiddenRegionFormatter::noIndentation);
		document.append(algorithmKeyword, IHiddenRegionFormatter::oneSpace);
		document.append(textRegionExtensions.regionFor(algorithm)
				.assignment(grammarAccess.getSTAlgorithmAccess().getNameAssignment_1()), it -> it.setNewLines(1, 1, 2));
		formatVarDeclarationBlocks(algorithm.getBody().getVarTempDeclarations(), document);
		algorithm.getBody().getStatements().forEach(document::format);
		document.prepend(endKeyword, IHiddenRegionFormatter::noIndentation);
		document.append(endKeyword, it -> it.setNewLines(2, 2, 2));
	}

	protected void _format(final STMethod method, final IFormattableDocument document) {
		final ISemanticRegion methodKeyword = textRegionExtensions.regionFor(method).keyword("METHOD"); //$NON-NLS-1$
		final ISemanticRegion endKeyword = textRegionExtensions.regionFor(method).keyword("END_METHOD"); //$NON-NLS-1$

		document.prepend(methodKeyword, IHiddenRegionFormatter::noIndentation);
		document.append(methodKeyword, IHiddenRegionFormatter::oneSpace);
		if (method.getReturnType() != null) {
			final ISemanticRegion colonKeyword = textRegionExtensions.regionFor(method).keyword(":"); //$NON-NLS-1$
			document.surround(colonKeyword, IHiddenRegionFormatter::oneSpace);
			document.prepend(colonKeyword, format -> {
				format.autowrap();
				format.setOnAutowrap(new STCoreAutowrapFormatter(textRegionExtensions.regionFor(method)
						.feature(STAlgorithmPackage.Literals.ST_METHOD__RETURN_TYPE).getNextHiddenRegion()));
			});
			document.append(
					textRegionExtensions.regionFor(method)
							.assignment(grammarAccess.getSTMethodAccess().getReturnTypeAssignment_2_1()),
					it -> it.setNewLines(1, 1, 2));
		} else {
			document.append(textRegionExtensions.regionFor(method).assignment(
					grammarAccess.getSTMethodAccess().getNameAssignment_1()), it -> it.setNewLines(1, 1, 2));
		}
		formatVarDeclarationBlocks(method.getBody().getVarDeclarations(), document);
		method.getBody().getStatements().forEach(document::format);
		document.prepend(endKeyword, IHiddenRegionFormatter::noIndentation);
		document.append(endKeyword, it -> it.setNewLines(2, 2, 2));
	}

	@Override
	public void format(final Object object, final IFormattableDocument document) {
		switch (object) {
		case final STAlgorithmSource element -> _format(element, document);
		case final STAlgorithm element -> _format(element, document);
		case final STMethod element -> _format(element, document);
		case null, default -> super.format(object, document);
		}
	}
}
