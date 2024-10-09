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
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

@FinalFieldsConstructor
class STMethodSupport extends StructuredTextSupport {
	final org.eclipse.fordiac.ide.model.libraryElement.STMethod method

	STMethod parseResult
	CharSequence outReturn

	override prepare() {
		if (parseResult === null && errors.empty) {
			parseResult = method.parse(errors, warnings, infos)
		}
		return parseResult !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare()
		parseResult?.generateStructuredTextMethod
	}

	def private CharSequence generateStructuredTextMethod(STMethod method) {
		outReturn = generateOutReturn(method)
		if (this.method.rootContainer instanceof BaseFBType) {
			val container = this.method.rootContainer as BaseFBType
			return '''
				local function method_«method.name»(fb, «method.generateStructuredTextMethodParameters»)
					local ENV = {}
					st_ret_val = nil
					«container.interfaceList.generateFBVariablePrefix»
					«container.internalVars.generateInternalVariablePrefix»
					«method.body.varDeclarations.filter(STVarInputDeclarationBlock).generateInParameterPrefix»
					«method.body.varDeclarations.filter(STVarInOutDeclarationBlock).generateInParameterPrefix»
					«method.body.varDeclarations.filter(STVarTempDeclarationBlock).generateLocalVariables»
					
					«method.body.statements.generateStatementList»
					«container.internalVars.generateInternalVariableSuffix»
					«container.interfaceList.generateFBVariableSuffix»
					return st_ret_val«outReturn»
				end
			'''
		} else {
			return '''
				local function method_«method.name»(fb, «method.generateStructuredTextMethodParameters»)
					local ENV = {}
					st_ret_val = nil
					«method.body.varDeclarations.filter(STVarInputDeclarationBlock).generateInParameterPrefix»
					«method.body.varDeclarations.filter(STVarInOutDeclarationBlock).generateInParameterPrefix»
					«method.body.varDeclarations.filter(STVarTempDeclarationBlock).generateLocalVariables»
					
					«method.body.statements.generateStatementList»
					
					return st_ret_val«outReturn»
				end
			'''
		}
	}

	def private CharSequence generateStructuredTextMethodParameters(STMethod method) //
	'''«FOR param : method.structuredTextMethodParameters SEPARATOR ', '»par_«param.name»«ENDFOR»'''

	def private getStructuredTextMethodParameters(STMethod method) {
		method.body.varDeclarations.filter(STVarInputDeclarationBlock).flatMap[varDeclarations] +
			method.body.varDeclarations.filter(STVarInOutDeclarationBlock).flatMap[varDeclarations] // +
			// method.body.varDeclarations.filter(STVarOutputDeclarationBlock).flatMap[varDeclarations].map[it -> true]
	}

	def private getStructuredTextOutParameters(STMethod method) {
		method.body.varDeclarations.filter(STVarInOutDeclarationBlock).flatMap[varDeclarations] +
			method.body.varDeclarations.filter(STVarOutputDeclarationBlock).flatMap[varDeclarations]
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

	override protected dispatch CharSequence generateFeatureName(STMethod feature, boolean call) {
		if (call) {
			return '''method_«feature.name»'''
		} else {
			return '''st_ret_val'''
		}
	}

	override protected dispatch CharSequence generateStatement(STReturn stmt) '''
		return st_ret_val«outReturn»
	'''

	def private CharSequence generateOutReturn(STMethod method) '''
		«FOR param : method.structuredTextOutParameters BEFORE ', ' SEPARATOR ', '»«param.generateFeatureName(false)»«ENDFOR»
	'''

	override getDependencies(Map<?, ?> options) {
		prepare()
		if (parseResult !== null) {
//			if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
//				(#[parseResult.returnType].filterNull + parseResult.body.varDeclarations.filter [
//					it instanceof STVarInputDeclarationBlock || it instanceof STVarOutputDeclarationBlock
//				].flatMap[varDeclarations].map[type]).toSet
//			else
			parseResult.containedDependencies
		} else
			emptySet
	}
}
