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
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - add global formatting from STFunctionFormatter
 *       - add formatting for package declaration and imports
 */
package org.eclipse.fordiac.ide.globalconstantseditor.formatting2;

import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreFormatter;
import org.eclipse.xtext.formatting2.IFormattableDocument;

@SuppressWarnings("java:S100")
public class GlobalConstantsFormatter extends STCoreFormatter {
	protected void _format(final STGlobalConstsSource source, final IFormattableDocument document) {
		formatSource(source, document);
		if (source.getName() != null) {
			formatPackage(source, document);
		}
		formatImports(source.getImports(), document);
		formatVarDeclarationBlocks(source.getElements(), document);
	}

	protected void _format(final STVarGlobalDeclarationBlock varDeclarationBlock, final IFormattableDocument document) {
		formatVarDeclarationBlock(varDeclarationBlock, document, "VAR_GLOBAL"); //$NON-NLS-1$
	}

	@Override
	public void format(final Object object, final IFormattableDocument document) {
		switch (object) {
		case final STGlobalConstsSource element -> _format(element, document);
		case final STVarGlobalDeclarationBlock element -> _format(element, document);
		case null, default -> super.format(object, document);
		}
	}
}
