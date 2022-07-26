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
 * 	 Christoph Binder - Extracted code from STAlgorithmResource, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypextext

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.linking.lazy.LazyLinkingResource

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*


class FBTypeXtextResource extends LazyLinkingResource{
	
	@Accessors
	FBType fbType
	@Accessors
	List<EObject> additionalContent = newArrayList
	
	def setFbType(FBType fbType) {
		this.fbType = fbType
		updateInternalFBType
	}
	
	def protected void updateInternalFBType() {
		clearInternalFBType
		if (!contents.nullOrEmpty) {
			if(fbType !== null){
				contents.add(fbType.copy)
			}
			contents.addAll(additionalContent.copyAll)
			relink
		}
	}
	
	def protected void clearInternalFBType() {
		contents?.removeIf[it instanceof FBType]
		contents?.removeAll(additionalContent)
	}
	
	override synchronized getEObject(String uriFragment) {
		try {
			super.getEObject(uriFragment)
		} catch (IllegalArgumentException e) {
			null
		}
	}
	
}
