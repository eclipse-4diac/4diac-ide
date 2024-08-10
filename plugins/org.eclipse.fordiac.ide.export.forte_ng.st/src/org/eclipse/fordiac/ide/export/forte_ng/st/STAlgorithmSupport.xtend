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
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm

import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

class STAlgorithmSupport extends StructuredTextSupport {
	final org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm
	final Map<Object, Object> cache;

	STAlgorithm parseResult
	
	new(org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm, Map<?, ?> options) {
		this.algorithm = algorithm
		this.cache = options.cacheOption
	}

	override prepare() {
		if (parseResult === null && errors.empty) {
			val container = algorithm.eContainer
			if (container instanceof BaseFBType)
				parseResult = cache.computeCached(container)[container.parse(errors, warnings, infos)]?.elements?.
					filter(STAlgorithm)?.findFirst[name == algorithm.name]
			else
				parseResult = algorithm.parse(errors, warnings, infos)
		}
		return parseResult !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare()
		parseResult?.generateStructuredTextAlgorithm
	}

	def private CharSequence generateStructuredTextAlgorithm(STAlgorithm alg) '''
		«alg.body.varTempDeclarations.generateVariables(true)»
		
		«alg.body.statements.generateStatementList»
	'''

	override getDependencies(Map<?, ?> options) {
		prepare()
		parseResult?.containedDependencies ?: emptySet
	}
}
