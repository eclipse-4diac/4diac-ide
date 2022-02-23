/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.st

import java.util.Map
import org.eclipse.fordiac.ide.export.ExportException
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.parser.IParseResult

import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

@FinalFieldsConstructor
class STAlgorithmSupport extends StructuredTextSupport {
	final STAlgorithm algorithm

	IParseResult parseResult

	override prepare(Map<?, ?> options) {
		if (parseResult === null && errors.empty) {
			parseResult = algorithm.text.parse(false, algorithm.name,
				switch (root : algorithm.rootContainer) { FBType: root }, errors)
		}
		return parseResult !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare(options)
		(parseResult?.rootASTElement as STAlgorithmBody)?.generateStructuredTextAlgorithm
	}

	def private CharSequence generateStructuredTextAlgorithm(STAlgorithmBody alg) '''
		«alg.varTempDeclarations.generateLocalVariables(true)»
		
		«alg.statements.generateStatementList»
	'''

	override getDependencies(Map<?, ?> options) {
		prepare(options)
		val root = parseResult?.rootASTElement
		if (root instanceof STAlgorithmBody)
			root.varTempDeclarations.flatMap[varDeclarations].filter(STVarDeclaration).map[type].toSet
		else
			emptySet
	}
}
