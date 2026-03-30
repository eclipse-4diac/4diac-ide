/**
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Hesam Rezaee
 *       - add Hovering features
 *   Martin Erich Jobst
 *       - rework and cleanup hover documentation
 */

package org.eclipse.fordiac.ide.structuredtextcore.ui.hovering

import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.ui.editor.hover.html.DefaultHoverDocumentationProvider

class STCoreHoverDocumentationProvider extends DefaultHoverDocumentationProvider {
	override getDocumentation(EObject object) {
		'''«object.getSTElementAutoDocumentation»«super.getDocumentation(object)»'''
	}

	def dispatch CharSequence getSTElementAutoDocumentation(ICallable object) '''
		«IF !object.inputParameters.isEmpty»
			<p>INPUTS:
			«FOR in : object.inputParameters»
				<div style="text-indent:10px;"><b>«in.name» : «in.fullTypeName»«IF object.varargs && in == object.inputParameters.last» ...«ENDIF»</b>«IF !in.documentation.nullOrEmpty» - «in.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.outputParameters.isEmpty»
			<p>OUTPUTS:
			«FOR out : object.outputParameters»
				<div style="text-indent:10px;"><b>«out.name» : «out.fullTypeName»</b>«IF !out.documentation.nullOrEmpty» - «out.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.inOutParameters.isEmpty»
			<p>IN_OUTS:
			«FOR inout : object.inOutParameters»
				<div style="text-indent:10px;"><b>«inout.name» : «inout.fullTypeName»</b>«IF !inout.documentation.nullOrEmpty» - «inout.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF object.returnType !== null»
			<p>RETURN:
				<div style="text-indent:10px;"><b>«"TYPE"» : «object.returnType.name»</b></div>
			</p>
		«ENDIF»
		«IF !object.comment.isEmpty»
			<p>DESCRIPTION: 
				<div style="text-indent:10px;"><b>«object.comment»</b></div>
			</p>
		«ENDIF»
	'''

	def dispatch CharSequence getSTElementAutoDocumentation(STStandardFunction object) '''
		«IF !object.inputParameters.isEmpty»
			<p>INPUTS:
			«FOR in : object.inputParameters»
				<div style="text-indent:10px;"><b>«in.name» : «in.fullTypeName»«IF object.varargs && in == object.inputParameters.last» ...«ENDIF»</b>«IF !in.documentation.nullOrEmpty» - «in.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.outputParameters.isEmpty»
			<p>OUTPUTS:
			«FOR out : object.outputParameters»
				<div style="text-indent:10px;"><b>«out.name» : «out.fullTypeName»</b>«IF !out.documentation.nullOrEmpty» - «out.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.inOutParameters.isEmpty»
			<p>IN_OUTS:
			«FOR inout : object.inOutParameters»
				<div style="text-indent:10px;"><b>«inout.name» : «inout.fullTypeName»</b>«IF !inout.documentation.nullOrEmpty» - «inout.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF object.returnType !== null»
			<p>RETURN:
				<div style="text-indent:10px;"><b>«object.returnType.name»</b>«IF !object.returnValueComment.blank» - «object.returnValueComment»«ENDIF»</div>
			</p>
		«ENDIF»
		«IF !object.comment.isEmpty»
			<p>DESCRIPTION:
				<div style="text-indent:10px;"><b>«object.comment»</b></div>
			</p>
		«ENDIF»
	'''

	def dispatch CharSequence getSTElementAutoDocumentation(FB object) {
		object.type.STElementAutoDocumentation
	}

	def dispatch CharSequence getSTElementAutoDocumentation(FBType object) '''
		«IF !object.interfaceList.eventInputs.isEmpty»
			<p>INPUT EVENTS:
			«FOR event : object.interfaceList.eventInputs»
				<div style="text-indent:10px;"><b>«event.name»</b></div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.interfaceList.eventOutputs.isEmpty»
			<p>OUTPUT EVENTS:
			«FOR event : object.interfaceList.eventOutputs»
				<div style="text-indent:10px;"><b>«event.name»</b></div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.inputParameters.isEmpty»
			<p>INPUTS:
			«FOR in : object.inputParameters»
				<div style="text-indent:10px;"><b>«in.name» : «in.fullTypeName»«IF object.varargs && in == object.inputParameters.last» ...«ENDIF»«IF !in.comment.blank»  («in.comment»)«ENDIF»</b>«IF !in.documentation.nullOrEmpty» - «in.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.outputParameters.isEmpty»
			<p>OUTPUTS:
			«FOR out : object.outputParameters»
				<div style="text-indent:10px;"><b>«out.name» : «out.fullTypeName»</b>«IF !out.documentation.nullOrEmpty» - «out.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.inOutParameters.isEmpty»
			<p>IN_OUTS:
			«FOR inout : object.inOutParameters»
				<div style="text-indent:10px;"><b>«inout.name» : «inout.fullTypeName»</b>«IF !inout.documentation.nullOrEmpty» - «inout.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF object.returnType !== null»
			<p>RETURN:
				<div style="text-indent:10px;"><b>«"TYPE"» : «object.returnType.name»</b></div>
			</p>
		«ENDIF»
		«IF !object.comment.isEmpty»
			<p>DESCRIPTION:
				<div style="text-indent:10px;"><b>«object.comment»</b></div>
			</p>
		«ENDIF»
	'''

	private def isVarInternalConst(VarDeclaration object) {
		val fbType = object.FBType
		if (fbType instanceof BaseFBType) {
			fbType.internalConstVars.contains(object);
		}
	}

	def dispatch CharSequence getSTElementAutoDocumentation(VarDeclaration object) '''
		«IF !object.comment.isEmpty»
			<p>DESCRIPTION:
				<div style="text-indent:10px;"><b>«object.comment»</b></div>
			</p>
		«ENDIF»
		«IF object.isVarInternalConst»
			<p>Expression:
				<div style="text-indent:10px;"><b>«object.value?.value»</b></div>
			</p>
		«ENDIF»
	'''

	def dispatch CharSequence getSTElementAutoDocumentation(StructuredType object) '''
		«IF !object.comment.isEmpty»
			<p>DESCRIPTION:
				<div style="text-indent:10px;"><b>«object.comment»</b></div>
			</p>
		«ENDIF»
	'''

	def dispatch CharSequence getSTElementAutoDocumentation(STVarDeclaration varDeclaration) {
		if (varDeclaration.eContainer instanceof STVarGlobalDeclarationBlock)
			getVarGlobalDescription(varDeclaration)
		else
			'''
				«IF !varDeclaration.comment.isEmpty»
					<p>DESCRIPTION:
						<div style="text-indent:10px;"><b>«varDeclaration.comment»</b></div>
					</p>
				«ENDIF»
			'''
	}

	private def getVarGlobalDescription(STVarDeclaration declaration) '''
		<p>File location: <b>«declaration.eResource.URI.toPlatformString(true)»</b></p>
		
		<p>Expression: <b>«NodeModelUtils.getNode(declaration.defaultValue)?.text»</b></p>
	'''

	def dispatch CharSequence getSTElementAutoDocumentation(EObject object) '''''' // No ST element or no auto-documentation needed ST element
}
