/*******************************************************************************
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
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *   Martin Jobst - add ST source as top-level element
 *                - add STGlobalConstants intermediate element
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.st

import java.util.Map
import org.eclipse.fordiac.ide.export.ExportException
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstants
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.fordiac.ide.globalconstantseditor.util.GlobalConstantsParseUtil.parse

@FinalFieldsConstructor
class VarGlobalConstantsSupport extends StructuredTextSupport {
	final GlobalConstants globalConstants
	STGlobalConstsSource source

	new(STGlobalConstsSource source) {
		this(null as GlobalConstants)
		this.source = source
	}

	override prepare() {
		if (source === null && errors.empty) {
			source = globalConstants.parse(errors, warnings, infos)
		}
		return source !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare()
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
			source.constants?.generateStructuredTextGlobalVariablesSourceHeader
		else
			source.constants?.generateStructuredTextGlobalVariablesSourceImpl
	}

	def CharSequence generateStructuredTextGlobalVariablesSourceImpl(STGlobalConstants constants) {
		val result = new StringBuilder
		for (block : constants.elements) {
			result.append(block.generateStructuredTextGlobalVariablesSourceImpl)
		}
		result
	}

	def CharSequence generateStructuredTextGlobalVariablesSourceImpl(STVarGlobalDeclarationBlock block) {
		val result = new StringBuilder
		for (varDeclaration : block.varDeclarations) {
			result.append(varDeclaration.generateStructuredTextVarDeclarationSourceImpl)
		}
		result
	}

	def private CharSequence generateStructuredTextVarDeclarationSourceImpl(STVarDeclaration declaration) '''
		const «declaration.generateFeatureTypeName» «declaration.generateFeatureName» = «declaration.defaultValue.generateInitializerExpression»;
	'''

	def private CharSequence generateStructuredTextGlobalVariablesSourceHeader(STGlobalConstants constants) {
		val result = new StringBuilder
		for (block : constants.elements) {
			result.append(block.generateStructuredTextGlobalVariablesSourceHeader)
		}
		result
	}

	def private CharSequence generateStructuredTextGlobalVariablesSourceHeader(STVarGlobalDeclarationBlock block) {
		val result = new StringBuilder
		for (varDeclaration : block.varDeclarations) {
			result.append(varDeclaration.generateStructuredTextVarDeclarationHeader)
		}
		result
	}

	def private CharSequence generateStructuredTextVarDeclarationHeader(STVarDeclaration declaration) '''
		extern const «declaration.generateFeatureTypeName» «declaration.generateFeatureName»;
	'''

	override getDependencies(Map<?, ?> options) {
		prepare()
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
			if (source.constants !== null)
				source.constants.elements.flatMap[varDeclarations].map[type].toSet
			else
				emptySet
		else
			source.containedDependencies
	}
}
