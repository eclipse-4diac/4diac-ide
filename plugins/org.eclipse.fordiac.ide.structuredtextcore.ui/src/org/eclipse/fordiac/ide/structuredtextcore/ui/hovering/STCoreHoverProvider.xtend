/**
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
 *    Hesam Rezaee
 *      - add Hovering features
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.hovering

import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock

class STCoreHoverProvider extends DefaultEObjectHoverProvider {

	override protected getFirstLine(EObject o) {
		val label = switch (o) {
			VarDeclaration, STVarDeclaration: o.label + " : " + o.type.name
			FB: o.typeEntry.type.name
			default: o.label
		}
		return o.getKind() + ( (label !== null) ? "<b>" + label + "</b>" : "");
	}

	def dispatch getKind(STFunction object) '''FUNCTION '''

	def dispatch getKind(STAlgorithm object) '''ALGORITHM '''

	def dispatch getKind(STMethod object) '''METHOD '''

	def dispatch getKind(STVarDeclaration object) {
		if(object.eContainer instanceof STVarDeclarationBlock) {
			switch (object.eContainer) {
				STVarInputDeclarationBlock: '''INPUT '''
				STVarOutputDeclarationBlock: '''OUTPUT '''
				STVarInOutDeclarationBlock: '''IN_OUT '''
				STVarPlainDeclarationBlock: '''VAR '''
				STVarTempDeclarationBlock: '''VAR_TEMP '''
				STVarGlobalDeclarationBlock: '''VAR_GLOBAL '''
			} + '''«IF (object.eContainer as STVarDeclarationBlock).constant»CONSTANT «ENDIF»'''		
		}
	}

	def dispatch getKind(VarDeclaration object) {
		if (object.eContainer instanceof InterfaceList) {
			val interfaceList = object.eContainer as InterfaceList
			if (interfaceList.inputVars.contains(object)) {
				return '''INPUT '''
			}
			if (interfaceList.outputVars.contains(object)) {
				return '''OUTPUT '''
			}
			if (interfaceList.inOutVars.contains(object)) {
				return '''IN_OUT '''
			}
		}
		if (object.eContainer instanceof BaseFBType) {
			var baseFbType = object.eContainer as BaseFBType
			if (baseFbType.internalVars.contains(object)) {
				return '''VAR '''
			}
			if (baseFbType.internalConstVars.contains(object)) {
				return '''VAR CONST '''
			}
			if (baseFbType.internalFbs.contains(object)) {
				return '''FUNCTION_BLOCK '''
			}
		}
		if(object.eContainer instanceof StructuredType) return '''«(object.eContainer as StructuredType).name».'''
	}

	def dispatch getKind(STStandardFunction object) '''FUNCTION '''

	def dispatch getKind(FB object) '''FUNCTION_BLOCK '''

	def dispatch getKind(FBType object) '''FUNCTION_BLOCK '''

	def dispatch getKind(StructuredType object) '''STRUCT '''

	def dispatch getKind(EObject object) '''«object.eClass().getName()» '''
}
