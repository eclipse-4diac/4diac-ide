/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst, Patrick Aigner
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Patrick Aigner - adapted for Lua Code generation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.st

import java.util.Map
import org.eclipse.fordiac.ide.export.ExportException
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

@FinalFieldsConstructor
class STFunctionSupport extends StructuredTextSupport {
	final STFunctionSource source
	CharSequence outReturn

	override prepare() {
		return true
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare()
		source.generateStructuredTextFunctionSource
	}

	def private CharSequence generateStructuredTextFunctionSource(STFunctionSource source) {
		val result = new StringBuilder
		for (function : source.functions) {
			result.append(function.generateStructuredTextFunction)
		}
		result
	}

	def private CharSequence generateStructuredTextFunction(STFunction func) {
		outReturn = generateOutReturn(func)
		return '''
			local function func_«func.name»(fb, «func.generateStructuredTextFunctionParameters»)
				local ENV = {}
				st_ret_val = nil
				«func.varDeclarations.filter(STVarInputDeclarationBlock).generateInParameterPrefix»
				«func.varDeclarations.filter(STVarInOutDeclarationBlock).generateInParameterPrefix»
				«func.varDeclarations.filter(STVarTempDeclarationBlock).generateLocalVariables»
				
				«func.code.generateStatementList»
				
				return st_ret_val«outReturn»
			end
		'''
	}

	def private CharSequence generateStructuredTextFunctionParameters(STFunction func) //
	'''«FOR param : func.structuredTextFunctionParameters SEPARATOR ', '»par_«param.name»«ENDFOR»'''

	def private getStructuredTextFunctionParameters(STFunction func) {
		func.varDeclarations.filter(STVarInputDeclarationBlock).flatMap[varDeclarations] +
			func.varDeclarations.filter(STVarInOutDeclarationBlock).flatMap[varDeclarations] // +
			// method.body.varDeclarations.filter(STVarOutputDeclarationBlock).flatMap[varDeclarations].map[it -> true]
	}

	def private getStructuredTextOutParameters(STFunction func) {
		func.varDeclarations.filter(STVarInOutDeclarationBlock).flatMap[varDeclarations] +
			func.varDeclarations.filter(STVarOutputDeclarationBlock).flatMap[varDeclarations]
	}

	def protected CharSequence generateInParameterPrefix(Iterable<? extends STVarDeclarationBlock> blocks) '''
		«FOR block : blocks»
			«block.generateInParameterBlock»
		«ENDFOR»
	'''

	def protected CharSequence generateInParameterBlock(STVarDeclarationBlock block) '''
		«FOR variable : block.varDeclarations.filter(STVarDeclaration)»
			«variable.generateInParameter»
		«ENDFOR»
	'''

	def protected CharSequence generateInParameter(STVarDeclaration variable) '''
		ENV.st_lv_«variable.name» = par_«variable.name»
	'''

	def protected CharSequence generateOutParameterSuffix(Iterable<? extends STVarDeclarationBlock> blocks) '''
		«FOR block : blocks»
			«block.generateOutParameterBlock»
		«ENDFOR»
	'''

	def protected CharSequence generateOutParameterBlock(STVarDeclarationBlock block) '''
		«FOR variable : block.varDeclarations.filter(STVarDeclaration)»
			«variable.generateOutParameter»
		«ENDFOR»
	'''

	def protected CharSequence generateOutParameter(STVarDeclaration variable) '''
		par_«variable.name» = ENV.st_lv_«variable.name»
	'''

	override protected dispatch CharSequence generateFeatureName(STFunction feature, boolean call) {
		if (call) {
			return '''method_«feature.name»'''
		} else {
			return '''st_ret_val'''
		}
	}

	override protected dispatch CharSequence generateStatement(STReturn stmt) '''
		return st_ret_val«outReturn»
	'''

	def private CharSequence generateOutReturn(STFunction function) '''
		«FOR param : function.structuredTextOutParameters BEFORE ', ' SEPARATOR ', '»«param.generateFeatureName(false)»«ENDFOR»
	'''

	override getDependencies(Map<?, ?> options) {
		prepare()
//		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
//			(source.functions.map[returnType].filterNull + source.functions.flatMap [
//				varDeclarations.filter [
//					it instanceof STVarInputDeclarationBlock || it instanceof STVarOutputDeclarationBlock
//				]
//			].flatMap[varDeclarations].map[type]).toSet
//		else
		source.containedDependencies
	}
}
