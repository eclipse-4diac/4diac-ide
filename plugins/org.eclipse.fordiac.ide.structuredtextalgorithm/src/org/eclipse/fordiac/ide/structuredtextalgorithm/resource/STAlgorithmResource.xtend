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
package org.eclipse.fordiac.ide.structuredtextalgorithm.resource

import java.io.IOException
import java.io.InputStream
import java.util.Map
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.linking.lazy.LazyLinkingResource

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy

class STAlgorithmResource extends LazyLinkingResource {
	@Accessors
	FBType fbType

	override doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		clearInternalFBType
		super.doLoad(inputStream, options)
		updateInternalFBType
	}

	def setFbType(FBType fbType) {
		this.fbType = fbType
		updateInternalFBType
	}

	def protected void updateInternalFBType() {
		clearInternalFBType
		if (!contents.nullOrEmpty && fbType !== null) {
			contents.add(fbType.copy)
		}
	}

	def protected void clearInternalFBType() {
		contents?.removeIf[it instanceof FBType]
	}
}
