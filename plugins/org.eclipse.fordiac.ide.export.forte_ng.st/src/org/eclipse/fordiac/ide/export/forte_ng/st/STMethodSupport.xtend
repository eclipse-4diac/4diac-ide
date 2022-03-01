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
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

@FinalFieldsConstructor
class STMethodSupport extends StructuredTextSupport {
	final org.eclipse.fordiac.ide.model.libraryElement.STMethod method

	STMethod parseResult

	override prepare(Map<?, ?> options) {
		if (parseResult === null && errors.empty) {
			parseResult = method.parse(errors)
		}
		return parseResult !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare(options)
		if (options.get(ForteNgExportFilter.OPTION_HEADER) == Boolean.TRUE)
			parseResult.generateStructuredTextMethodHeader
		else
			parseResult.generateStructuredTextMethodImpl
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
	'''«method.returnType?.generateTypeName ?: "void"» «IF !header»FORTE_«FBType?.name»::«ENDIF»«method.generateFeatureName»(«method.generateStructuredTextMethodInputs»«method.generateStructuredTextMethodOutputs»)'''

	def private CharSequence generateStructuredTextMethodInputs(STMethod method) //
	'''«FOR param : method.body.varDeclarations.filter(STVarInputDeclarationBlock).flatMap[varDeclarations] SEPARATOR ", "»«param.generateTypeName» «param.generateFeatureName»«ENDFOR»'''

	def private CharSequence generateStructuredTextMethodOutputs(STMethod method) //
	'''«FOR param : method.body.varDeclarations.filter(STVarOutputDeclarationBlock).flatMap[varDeclarations] BEFORE ", " SEPARATOR ", "»«param.generateTypeName» «param.generateFeatureName»«ENDFOR»'''

	def private CharSequence generateStructuredTextMethodBody(STMethod method) '''
		«method.body.varDeclarations.filter(STVarPlainDeclarationBlock).generateLocalVariables(false)»
		«method.body.varDeclarations.filter(STVarTempDeclarationBlock).generateLocalVariables(true)»
		
		«method.body.statements.generateStatementList»
	'''

	def private getFBType() { switch (root : method.rootContainer) { BaseFBType: root } }

	override getDependencies(Map<?, ?> options) {
		prepare(options)
		parseResult?.containedDependencies ?: emptySet
	}
}
