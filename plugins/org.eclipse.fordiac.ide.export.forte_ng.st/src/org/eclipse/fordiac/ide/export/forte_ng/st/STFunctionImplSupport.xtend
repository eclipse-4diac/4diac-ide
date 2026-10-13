/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.eval.st.variable.STVariableOperations
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class STFunctionImplSupport extends STFunctionSupport {

	new(STFunctionSource source) {
		super(source)
	}

	override protected CharSequence generateStructuredTextFunctionHeader(STFunction func) '''
		«func.generateStructuredTextFunctionImplDeclaration»;
	'''

	override protected CharSequence generateStructuredTextFunctionImpl(STFunction func) '''
		«func.generateStructuredTextFunctionDeclaration» {
		  «func.varDeclarations.filter(STVarOutputDeclarationBlock).generateOutputGuard»
		  «func.generateStructuredTextFunctionCall»
		}
		
		namespace {
		  «func.generateStructuredTextFunctionImplDeclaration» {
		    «func.generateStructuredTextFunctionBody»
		  }
		}
	'''

	def protected CharSequence generateStructuredTextFunctionImplDeclaration(STFunction func) //
	'''«func.returnType?.generateTypeName ?: "void"» func_«func.name»__impl(«func.generateStructuredTextFunctionImplParameters»)'''

	def protected CharSequence generateStructuredTextFunctionImplParameters(STFunction func) //
	'''«FOR param : func.structuredTextFunctionParameters SEPARATOR ", "»«param.generateImplParameterTypeName»«param.generateParameterName»«ENDFOR»'''

	def protected CharSequence generateImplParameterTypeName(STVarDeclaration variable) {
		val type = STVariableOperations.evaluateResultType(variable)
		switch (variable.eContainer) {
			STVarInputDeclarationBlock: '''const «type.generateTypeNameAsInputParameter» &'''
			STVarInOutDeclarationBlock: '''«type.generateTypeNameAsInOutParameter» &'''
			STVarOutputDeclarationBlock: '''«type.generateTypeName» &'''
		}
	}

	def protected CharSequence generateStructuredTextFunctionCall(STFunction func) '''
		«IF func.returnType !== null»return «ENDIF»func_«func.name»__impl(«func.generateStructuredTextFunctionCallArguments»);
	'''

	def protected CharSequence generateStructuredTextFunctionCallArguments(STFunction func) //
	'''«FOR param : func.structuredTextFunctionParameters SEPARATOR ", "»«param.generateParameterArgumentName»«ENDFOR»'''

	def protected CharSequence generateParameterArgumentName(STVarDeclaration variable) {
		switch (variable.eContainer) {
			STVarInputDeclarationBlock,
			STVarInOutDeclarationBlock:
				variable.generateParameterName
			STVarOutputDeclarationBlock: '''*«variable.generateParameterName»'''
		}
	}

	override protected CharSequence _generateFeatureName(STVarDeclaration feature) {
		switch (feature.eContainer) {
			STVarOutputDeclarationBlock: '''st_lv_«feature.name»'''
			default:
				super._generateFeatureName(feature)
		}
	}
}
