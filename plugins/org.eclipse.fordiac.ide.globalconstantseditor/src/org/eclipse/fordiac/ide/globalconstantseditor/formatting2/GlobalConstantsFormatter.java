/**
 * Copyright (c) 2022, 2025 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - add global formatting from STFunctionFormatter
 *       - add formatting for package declaration and imports
 */
package org.eclipse.fordiac.ide.globalconstantseditor.formatting2;

import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstants;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.globalconstantseditor.services.GlobalConstantsGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreFormatter;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;

import com.google.inject.Inject;

@SuppressWarnings("java:S100")
public class GlobalConstantsFormatter extends STCoreFormatter {
	@Inject
	private GlobalConstantsGrammarAccess grammarAccess;

	protected void _format(final STGlobalConstsSource source, final IFormattableDocument document) {
		formatSource(source, document);
		if (source.getName() != null) {
			formatPackage(source, document);
		}
		formatImports(source.getImports(), document);
		document.format(source.getConstants());
	}

	protected void _format(final STGlobalConstants constants, final IFormattableDocument document) {
		final ISemanticRegion begin = textRegionExtensions.regionFor(constants).keyword("GLOBALCONSTANTS"); //$NON-NLS-1$
		final ISemanticRegion end = textRegionExtensions.regionFor(constants).keyword("END_GLOBALCONSTANTS"); //$NON-NLS-1$
		document.prepend(begin, IHiddenRegionFormatter::noIndentation);
		document.append(begin, IHiddenRegionFormatter::oneSpace);

		document.append(
				textRegionExtensions.regionFor(constants)
						.assignment(grammarAccess.getSTGlobalConstantsAccess().getNameAssignment_2()),
				IHiddenRegionFormatter::newLine);

		document.interior(begin, end, IHiddenRegionFormatter::indent);
		formatVarDeclarationBlocks(constants.getElements(), document);
		document.prepend(end, IHiddenRegionFormatter::noIndentation);
		document.append(end, format -> format.setNewLines(1, 2, 2));
	}

	protected void _format(final STVarGlobalDeclarationBlock varDeclarationBlock, final IFormattableDocument document) {
		formatVarDeclarationBlock(varDeclarationBlock, document, "VAR_GLOBAL"); //$NON-NLS-1$
	}

	@Override
	public void format(final Object object, final IFormattableDocument document) {
		switch (object) {
		case final STGlobalConstsSource element -> _format(element, document);
		case final STGlobalConstants element -> _format(element, document);
		case final STVarGlobalDeclarationBlock element -> _format(element, document);
		case null, default -> super.format(object, document);
		}
	}
}
