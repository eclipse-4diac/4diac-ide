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
 *   Ulzii Jargalsaikhan - initial API and implementation and/or initial documentation,
 * 		comment formatter
 *   Martin Melik-Merkumians - comment formatter
 *   Martin Jobst - return type autowrap support
 */
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.formatting2;

import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreAutowrapFormatter;
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreFormatter;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;

import com.google.inject.Inject;

@SuppressWarnings("java:S100")
public class STFunctionFormatter extends STCoreFormatter {
	@Inject
	private STFunctionGrammarAccess grammarAccess;

	protected void _format(final STFunctionSource source, final IFormattableDocument document) {
		formatSource(source, document);
		if (source.getName() != null) {
			formatPackage(source, document);
		}
		formatImports(source.getImports(), document);
		source.getFunctions().forEach(document::format);
	}

	protected void _format(final STFunction function, final IFormattableDocument document) {
		final ISemanticRegion functionKeyword = textRegionExtensions.regionFor(function).keyword("FUNCTION"); //$NON-NLS-1$
		final ISemanticRegion endKeyword = textRegionExtensions.regionFor(function).keyword("END_FUNCTION"); //$NON-NLS-1$

		document.prepend(functionKeyword, IHiddenRegionFormatter::noIndentation);
		document.append(functionKeyword, IHiddenRegionFormatter::oneSpace);
		if (function.getReturnType() != null) {
			final ISemanticRegion colonKeyword = textRegionExtensions.regionFor(function).keyword(":"); //$NON-NLS-1$
			document.surround(colonKeyword, IHiddenRegionFormatter::oneSpace);
			document.prepend(colonKeyword, format -> {
				format.autowrap();
				format.setOnAutowrap(new STCoreAutowrapFormatter(textRegionExtensions.regionFor(function)
						.feature(STFunctionPackage.Literals.ST_FUNCTION__RETURN_TYPE).getNextHiddenRegion()));
			});
			document.append(
					document.prepend(
							textRegionExtensions.regionFor(function)
									.feature(STFunctionPackage.Literals.ST_FUNCTION__RETURN_TYPE),
							IHiddenRegionFormatter::oneSpace),
					it -> it.setNewLines(1, 1, 2));
		} else {
			document.append(textRegionExtensions.regionFor(function).assignment(
					grammarAccess.getSTFunctionAccess().getNameAssignment_2()), it -> it.setNewLines(1, 1, 2));
		}
		formatVarDeclarationBlocks(function.getVarDeclarations(), document);
		function.getCode().forEach(document::format);
		document.prepend(endKeyword, IHiddenRegionFormatter::noIndentation);
		document.append(endKeyword, it -> it.setNewLines(1, 2, 2));
	}

	@Override
	public void format(final Object object, final IFormattableDocument document) {
		switch (object) {
		case final STFunctionSource element -> _format(element, document);
		case final STFunction element -> _format(element, document);
		case null, default -> super.format(object, document);
		}
	}
}
