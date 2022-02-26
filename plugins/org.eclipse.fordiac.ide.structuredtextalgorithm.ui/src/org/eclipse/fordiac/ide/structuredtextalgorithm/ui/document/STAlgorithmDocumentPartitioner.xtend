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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document

import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartitioner
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.model.XtextDocument

import static org.eclipse.emf.common.util.ECollections.*

class STAlgorithmDocumentPartitioner extends STAlgorithmPartitioner {

	def EList<Algorithm> partition(XtextDocument document) {
		try {
			document.readOnly[partition]
		} catch (Exception e) {
			document.emergencyPartition // try to salvage what we can
		}
	}

	def protected EList<Algorithm> getEmergencyPartition(XtextDocument document) {
		newBasicEList(document.get.newLostAndFound(0))
	}

	override EList<Algorithm> getEmergencyPartition(XtextResource resource) {
		throw new UnsupportedOperationException // don't try to serialize the document but get it from the document instead
	}

	override EList<Algorithm> getEmergencyPartition(STAlgorithmSource source) {
		throw new UnsupportedOperationException // don't try to use the node model but get it from the document instead
	}
}
