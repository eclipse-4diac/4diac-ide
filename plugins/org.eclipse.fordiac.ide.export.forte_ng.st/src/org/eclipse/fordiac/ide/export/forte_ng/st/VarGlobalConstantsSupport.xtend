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
package org.eclipse.fordiac.ide.export.forte_ng.st

import java.util.Map
import org.eclipse.fordiac.ide.export.ExportException
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock

@FinalFieldsConstructor
class VarGlobalConstantsSupport extends StructuredTextSupport {
	final STVarGlobalDeclarationBlock source

	override generate(Map<?, ?> options) throws ExportException {
		prepare(options)
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
			source.generateStructuredTextGlobalVariablesSourceHeader
		else
			source.generateStructuredTextGlobalVariablesSourceImpl
	}

	def CharSequence generateStructuredTextGlobalVariablesSourceImpl(STVarGlobalDeclarationBlock block) {
		val result = new StringBuilder
		for (varDeclaration : source.varDeclarations) {
			result.append(varDeclaration.generateStructuredTextVarDeclarationSourceImpl)
		}
		result
	}

	def private CharSequence generateStructuredTextVarDeclarationSourceImpl(STVarDeclaration declaration) '''
		const «declaration.generateTypeName» «declaration.generateFeatureName» = «declaration.defaultValue.generateInitializerExpression»;
	'''

	def private CharSequence generateStructuredTextGlobalVariablesSourceHeader(STVarGlobalDeclarationBlock block) {
		val result = new StringBuilder
		for (varDeclaration : source.varDeclarations) {
			result.append(varDeclaration.generateStructuredTextVarDeclarationHeader)
		}
		result
	}

	def private CharSequence generateStructuredTextVarDeclarationHeader(STVarDeclaration declaration) '''
		extern const «declaration.generateTypeName» «declaration.generateFeatureName»;
	'''

	override getDependencies(Map<?, ?> options) {
		prepare(options)
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
			source.getContainedDependencies
		else
			emptySet
	}

	override prepare(Map<?, ?> options) {
		return true
	}

}
