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
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.formatting2

import com.google.inject.Inject
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.globalconstantseditor.services.GlobalConstantsGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreFormatter
import org.eclipse.xtext.formatting2.IFormattableDocument

class GlobalConstantsFormatter extends STCoreFormatter {
	
	@Inject extension GlobalConstantsGrammarAccess

	def dispatch void format(STVarGlobalDeclarationBlock sTVarGlobalDeclarationBlock, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (sTVarDeclaration : sTVarGlobalDeclarationBlock.varDeclarations) {
			sTVarDeclaration.format
		}
	}
	
}
