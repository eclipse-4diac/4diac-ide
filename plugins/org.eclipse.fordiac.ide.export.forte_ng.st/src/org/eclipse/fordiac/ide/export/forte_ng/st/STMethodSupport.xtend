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
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

class STMethodSupport extends StructuredTextSupport {
	final org.eclipse.fordiac.ide.model.libraryElement.STMethod method
	final Map<Object, Object> cache;

	STMethod parseResult

	new(org.eclipse.fordiac.ide.model.libraryElement.STMethod method, Map<?, ?> options) {
		this.method = method
		this.cache = options.cacheOption
	}

	override prepare() {
		if (parseResult === null && errors.empty) {
			val container = method.eContainer
			if (container instanceof BaseFBType)
				parseResult = cache.computeCached(container)[container.parse(errors, warnings, infos)]?.elements?.
					filter(STMethod)?.findFirst[name == method.name]
			else
				parseResult = method.parse(errors, warnings, infos)
		}
		return parseResult !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare()
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
			parseResult?.generateStructuredTextMethodHeader
		else
			parseResult?.generateStructuredTextMethodImpl
	}

	def private CharSequence generateStructuredTextMethodHeader(STMethod method) '''
		«method.generateStructuredTextMethodDeclaration(true)»;
	'''

	def private CharSequence generateStructuredTextMethodImpl(STMethod method) '''
		«method.generateStructuredTextMethodDeclaration(false)» {
		  «method.generateStructuredTextMethodBody»
		}
		
	'''

	def private CharSequence generateStructuredTextMethodDeclaration(STMethod method, boolean header) //
	'''«method.returnType?.generateTypeName ?: "void"» «IF !header»«FBType.generateTypeName»::«ENDIF»method_«method.name»(«method.generateStructuredTextMethodParameters»)'''

	def private CharSequence generateStructuredTextMethodParameters(STMethod method) //
	'''«FOR param : method.structuredTextMethodParameters SEPARATOR ", "»«param.key.generateFeatureTypeName(param.value)» «IF param.value»&«ENDIF»«param.key.generateFeatureName»«ENDFOR»'''

	def private getStructuredTextMethodParameters(STMethod method) {
		method.body.varDeclarations.filter(STVarInputDeclarationBlock).flatMap[varDeclarations].map[it -> false] +
			method.body.varDeclarations.filter(STVarInOutDeclarationBlock).flatMap[varDeclarations].map[it -> true] +
			method.body.varDeclarations.filter(STVarOutputDeclarationBlock).flatMap[varDeclarations].map[it -> true]
	}

	def private CharSequence generateStructuredTextMethodBody(STMethod method) '''
		«IF method.returnType !== null»«method.returnType.generateTypeName» st_ret_val = «method.returnType.generateTypeDefaultValue»;«ENDIF»
		«method.body.varDeclarations.filter(STVarOutputDeclarationBlock).generateVariables(false)»
		«method.body.varDeclarations.filter(STVarTempDeclarationBlock).generateVariables(true)»
		
		«method.body.statements.generateStatementList»
		
		«IF method.returnType !== null»return st_ret_val;«ENDIF»
	'''

	override protected dispatch CharSequence generateStatement(STReturn stmt) //
	'''return«IF parseResult.returnType !== null» st_ret_val«ENDIF»;'''

	override protected dispatch CharSequence generateExpression(STFeatureExpression expr) {
		if (expr.feature === parseResult && !expr.call)
			"st_ret_val"
		else
			super._generateExpression(expr)
	}

	def private getFBType() { switch (root : method.rootContainer) { BaseFBType: root } }

	override getDependencies(Map<?, ?> options) {
		prepare()
		if (parseResult !== null) {
			if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
				(#[parseResult.returnType].filterNull + parseResult.body.varDeclarations.filter [
					it instanceof STVarInputDeclarationBlock || it instanceof STVarOutputDeclarationBlock
				].flatMap[varDeclarations].map[type]).toSet
			else
				parseResult.containedDependencies
		} else
			emptySet
	}
}
