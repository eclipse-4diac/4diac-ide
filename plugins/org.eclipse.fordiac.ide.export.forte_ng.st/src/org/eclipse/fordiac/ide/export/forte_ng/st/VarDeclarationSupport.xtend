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
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.*

@FinalFieldsConstructor
class VarDeclarationSupport extends StructuredTextSupport {
	final VarDeclaration varDeclaration

	INamedElement resultType
	STInitializerExpressionSource parseResult

	override prepare() {
		prepareResultType && prepareInitialValue
	}

	def protected prepareResultType() {
		if (resultType === null) {
			if (varDeclaration.array) {
				try {
					resultType = VariableOperations.evaluateResultType(varDeclaration)
				} catch (Exception e) {
					errors.add(e.message)
				}
			} else
				resultType = varDeclaration.type
		}
		return resultType !== null
	}

	def protected prepareInitialValue() {
		prepareResultType
		if (parseResult === null && errors.empty) {
			parseResult = (varDeclaration.value?.value ?: "").parse(
				varDeclaration?.eResource?.URI,
				resultType,
				varDeclaration?.getContainerOfType(LibraryElement),
				null,
				errors,
				warnings,
				infos
			)
		}
		return parseResult !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		if (options.get(ForteNgExportFilter.OPTION_TYPE) == Boolean.TRUE) {
			prepareResultType
			resultType?.generateTypeName
		} else if (options.get(ForteNgExportFilter.OPTION_TYPE_PARAM) == Boolean.TRUE) {
			prepareResultType
			resultType?.generateTypeNameAsParameter
		} else if (options.get(ForteNgExportFilter.OPTION_TYPE_SPEC) == Boolean.TRUE) {
			prepareResultType
			resultType?.generateTypeSpec
		} else if (varDeclaration.value?.value.nullOrEmpty) {
			prepareResultType
			resultType?.generateTypeDefaultValue
		} else {
			prepareInitialValue
			parseResult?.initializerExpression?.generateInitializerExpression
		}
	}

	override getDependencies(Map<?, ?> options) {
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE) {
			#{varDeclaration.type}
		} else if (!varDeclaration.value?.value.nullOrEmpty) {
			prepareInitialValue
			parseResult?.containedDependencies ?: emptySet
		} else
			emptySet
	}
}
