/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreMapper
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction

class STFunctionMapper implements STCoreMapper {
	def dispatch EObject fromModel(Resource resource, Object element) {
		if(resource.allContents.contains(element)) element as EObject else null
	}

	def dispatch EObject fromModel(Resource resource,
		STFunctionBody element) {
		return resource.allContents.filter(STFunction).head
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