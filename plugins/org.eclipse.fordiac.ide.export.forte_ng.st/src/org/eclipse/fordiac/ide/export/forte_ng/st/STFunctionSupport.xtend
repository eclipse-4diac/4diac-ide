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
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

@FinalFieldsConstructor
class STFunctionSupport extends StructuredTextSupport {
	final STFunctionSource source
	STFunction currentFunction

	override prepare() {
		return true
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare()
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
			source.generateStructuredTextFunctionSourceHeader
		else
			source.generateStructuredTextFunctionSourceImpl
	}

	def private CharSequence generateStructuredTextFunctionSourceHeader(STFunctionSource source) {
		val result = new StringBuilder
		for (function : source.functions) {
			currentFunction = function
			result.append(function.generateStructuredTextFunctionHeader)
			currentFunction = null
		}
		result
	}

	def private CharSequence generateStructuredTextFunctionSourceImpl(STFunctionSource source) {
		val result = new StringBuilder
		for (function : source.functions) {
			currentFunction = function
			result.append(function.generateStructuredTextFunctionImpl)
			currentFunction = null
		}
		result
	}

	def private CharSequence generateStructuredTextFunctionHeader(STFunction func) '''
		«func.generateStructuredTextFunctionDeclaration»;
	'''

	def private CharSequence generateStructuredTextFunctionImpl(STFunction func) '''
		«func.generateStructuredTextFunctionDeclaration» {
		  «func.generateStructuredTextFunctionBody»
		}
		
	'''

	def private CharSequence generateStructuredTextFunctionDeclaration(STFunction func) //
	'''«func.returnType?.generateTypeName ?: "void"» func_«func.name»(«func.generateStructuredTextFunctionParameters»)'''

	def private CharSequence generateStructuredTextFunctionParameters(STFunction func) //
	'''«FOR param : func.structuredTextFunctionParameters SEPARATOR ", "»«param.key.generateFeatureTypeName(param.value)» «IF param.value»&«ENDIF»«param.key.generateFeatureName»«ENDFOR»'''

	def private getStructuredTextFunctionParameters(STFunction func) {
		func.varDeclarations.filter(STVarInputDeclarationBlock).flatMap[varDeclarations].map[it -> false] +
		func.varDeclarations.filter(STVarInOutDeclarationBlock).flatMap[varDeclarations].map[it -> true] +
			func.varDeclarations.filter(STVarOutputDeclarationBlock).flatMap[varDeclarations].map[it -> true]
	}

	def private CharSequence generateStructuredTextFunctionBody(STFunction func) '''
		«IF func.returnType !== null»«func.returnType.generateTypeName» st_ret_val = «func.returnType.generateTypeDefaultValue»;«ENDIF»
		«func.varDeclarations.filter(STVarOutputDeclarationBlock).generateVariables(false)»
		«func.varDeclarations.filter(STVarPlainDeclarationBlock).generateVariables(true)»
		«func.varDeclarations.filter(STVarTempDeclarationBlock).generateVariables(true)»
		
		«func.code.generateStatementList»
		
		«IF func.returnType !== null»return st_ret_val;«ENDIF»
	'''

	override protected dispatch CharSequence generateStatement(STReturn stmt) //
	'''return«IF currentFunction.returnType !== null» st_ret_val«ENDIF»;'''

	override protected dispatch CharSequence generateExpression(STFeatureExpression expr) {
		if (expr.feature === currentFunction && !expr.call)
			"st_ret_val"
		else
			super._generateExpression(expr)
	}

	override getDependencies(Map<?, ?> options) {
		prepare()
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
			(source.functions.map[returnType].filterNull + source.functions.flatMap [
				varDeclarations.filter [
					it instanceof STVarInputDeclarationBlock || it instanceof STVarOutputDeclarationBlock
				]
			].flatMap[varDeclarations].map[type]).toSet
		else
			source.containedDependencies
	}
}
