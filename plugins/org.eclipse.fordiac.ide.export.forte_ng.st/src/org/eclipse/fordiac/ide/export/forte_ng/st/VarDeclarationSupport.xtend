/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*

@FinalFieldsConstructor
class VarDeclarationSupport extends StructuredTextSupport {
	final VarDeclaration varDeclaration
	
	STInitializerExpressionSource parseResult
	
	override prepare(Map<?, ?> options) {
		if (parseResult === null && errors.empty) {
			parseResult = (varDeclaration.value?.value ?: "").parse(
				varDeclaration?.eResource?.URI,
				varDeclaration.featureType,
				null,
				null,
				errors,
				warnings,
				infos
			)
		}
		return parseResult !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare(options)
		parseResult?.initializerExpression?.generateInitializerExpression
	}

	override getDependencies(Map<?, ?> options) {
		prepare(options)
		parseResult?.containedDependencies ?: emptySet
	}
}
