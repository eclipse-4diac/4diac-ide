/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst, Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - Bulk Editor navigation to ST function declarations
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreMapper
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource

class STFunctionMapper implements STCoreMapper {
	def dispatch EObject fromModel(Resource resource, Object element) {
		if(resource.allContents.contains(element)) element as EObject else null
	}

	def dispatch EObject fromModel(Resource resource,
		STFunctionBody element) {
		return resource.allContents.filter(STFunction).head
	}

	def dispatch EObject fromModel(Resource resource, VarDeclaration element) {
		val interfaceList = element.eContainer
		if (interfaceList instanceof InterfaceList) {
			val owner = interfaceList.eContainer
			if (owner instanceof FunctionFBType) {
				val functions = resource.contents.filter(STFunctionSource).head?.functions
				// like STFunctionReconciler, use the first function if none is named like the type
				val function = functions?.findFirst[name == owner.name] ?: functions?.findFirst[name !== null]
				if (function === null) {
					return null
				}
				val feature = element.eContainingFeature
				if (feature == LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS && element.name == "") {
					return if (function.eIsSet(STFunctionPackage.Literals.ST_FUNCTION__RETURN_TYPE)) function else null
				}
				val parameters = switch feature {
					case LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS: function.inputParameters
					case LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS: function.outputParameters
					case LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS,
					case LibraryElementPackage.Literals.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS: function.inOutParameters
					default: return null
				}
				return parameters.findFirst[name == element.name]
			}
		}
		return if (resource.allContents.contains(element)) element else null
	}

	def dispatch EObject toModel(EObject element) {
		if(element.eResource.fbType.eAllContents.contains(element)) element else null
	}

	def dispatch EObject toModel(STFunction function) {
		val type = function.eResource.fbType
		if (type instanceof FunctionFBType) {
			type.body
		} else
			null
	}

	def protected FBType getFbType(Resource resource) {
		if (resource instanceof STFunctionResource)
			resource.fbType
		else
			null
	}
}
