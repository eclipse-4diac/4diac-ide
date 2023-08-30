/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreMapper

class STAlgorithmMapper implements STCoreMapper {

	def dispatch EObject fromModel(Resource resource, Object element) {
		if(resource.allContents.contains(element)) element as EObject else null
	}

	def dispatch STAlgorithm fromModel(Resource resource,
		org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm element) {
		return resource.allContents.filter(STAlgorithm).filter[name == element.name].head
	}

	def dispatch STMethod fromModel(Resource resource, org.eclipse.fordiac.ide.model.libraryElement.STMethod element) {
		return resource.allContents.filter(STMethod).filter[name == element.name].head
	}

	def dispatch EObject toModel(EObject element) {
		if(element.eResource.libraryElement.eAllContents.contains(element)) element else null
	}

	def dispatch org.eclipse.fordiac.ide.model.libraryElement.STMethod toModel(STMethod method) {
		val type = method.eResource.libraryElement
		if (type instanceof BaseFBType) {
			type.methods.filter(org.eclipse.fordiac.ide.model.libraryElement.STMethod).findFirst [
				name == method.name
			]
		} else
			null
	}

	def dispatch org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm toModel(STAlgorithm algorithm) {
		val type = algorithm.eResource.libraryElement
		if (type instanceof BaseFBType) {
			type.algorithm.filter(org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm).findFirst [
				name == algorithm.name
			]
		} else
			null
	}

	def protected LibraryElement getLibraryElement(Resource resource) {
		if (resource instanceof STAlgorithmResource)
			resource.libraryElement
		else
			null
	}
}
