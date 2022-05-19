package org.eclipse.fordiac.ide.structuredtextcore.ui.hovering

import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.xtext.ui.editor.hover.html.DefaultHoverDocumentationProvider

class STCoreHoverDocumentationProvider extends DefaultHoverDocumentationProvider {
	override getDocumentation(EObject object) {
		'''«object.getSTElementAutoDocumentation»«super.getDocumentation(object)»'''
	}

	def dispatch getSTElementAutoDocumentation(STFunction object) '''
		«IF !object.inputParameters.isEmpty»
			<p>INPUTS:
			«FOR in : object.inputParameters»
				<div style="text-indent:10px;"><b>«in.name» : «(in as STVarDeclaration).type.name»</b>«IF !in.documentation.nullOrEmpty» - «in.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.outputParameters.isEmpty»
			<p>OUTPUTS:
			«FOR out : object.outputParameters»
				<div style="text-indent:10px;"><b>«out.name» : «(out as STVarDeclaration).type.name»</b>«IF !out.documentation.nullOrEmpty» - «out.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.inOutParameters.isEmpty»
			<p>IN_OUTS:
			«FOR inout : object.inOutParameters»
				<div style="text-indent:10px;"><b>«inout.name» : «(inout as STVarDeclaration).type.name»</b>«IF !inout.documentation.nullOrEmpty» - «inout.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
	'''

	def dispatch getSTElementAutoDocumentation(STMethod object) '''
		«IF !object.inputParameters.isEmpty»
			<p>INPUTS:
			«FOR in : object.inputParameters»
				<div style="text-indent:10px;"><b>«in.name» : «(in as STVarDeclaration).type.name»</b>«IF !in.documentation.nullOrEmpty» - «in.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.outputParameters.isEmpty»
			<p>OUTPUTS:
			«FOR out : object.outputParameters»
				<div style="text-indent:10px;"><b>«out.name» : «(out as STVarDeclaration).type.name»</b>«IF !out.documentation.nullOrEmpty» - «out.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
		«IF !object.inOutParameters.isEmpty»
			<p>IN_OUTS:
			«FOR inout : object.inOutParameters»
				<div style="text-indent:10px;"><b>«inout.name» : «(inout as STVarDeclaration).type.name»</b>«IF !inout.documentation.nullOrEmpty» - «inout.documentation»«ENDIF»</div>
			«ENDFOR»
			</p>
		«ENDIF»
	'''

	def dispatch getSTElementAutoDocumentation(VarDeclaration object) {
		object.comment
	}

	def dispatch getSTElementAutoDocumentation(FB object) {
		object.comment
	}

	def dispatch getSTElementAutoDocumentation(StructuredType object) {
		object.comment
	}

	def dispatch getSTElementAutoDocumentation(EObject object) '''''' // No ST element or no auto-documentation needed ST element
}
