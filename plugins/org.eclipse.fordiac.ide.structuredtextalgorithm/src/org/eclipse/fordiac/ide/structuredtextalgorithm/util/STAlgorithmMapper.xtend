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
package org.eclipse.fordiac.ide.structuredtextalgorithm.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod

class STAlgorithmMapper {

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
}
