/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.formatting2

import com.google.inject.Inject
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreFormatter
import org.eclipse.xtext.formatting2.IFormattableDocument

class STAlgorithmFormatter extends STCoreFormatter {
	
	@Inject extension STAlgorithmGrammarAccess

	def dispatch void format(STAlgorithmSource sTAlgorithmSource, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (element : sTAlgorithmSource.elements) {
			element.format
		}
	}

	def dispatch void format(STAlgorithm sTAlgorithm, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		sTAlgorithm.body.format
	}
	
	// TODO: implement for STAlgorithmBody
}
