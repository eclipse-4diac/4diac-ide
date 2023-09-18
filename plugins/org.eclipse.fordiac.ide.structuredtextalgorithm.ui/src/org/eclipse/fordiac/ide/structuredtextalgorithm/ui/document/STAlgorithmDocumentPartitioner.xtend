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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document

import java.util.Optional
import org.eclipse.emf.common.util.ECollections
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartition
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartitioner
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.STCoreDocumentPartitioner
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.model.XtextDocument

import static org.eclipse.emf.common.util.ECollections.*

class STAlgorithmDocumentPartitioner extends STAlgorithmPartitioner implements STCoreDocumentPartitioner {

	override Optional<? extends STCorePartition> partition(XtextDocument document) {
		try {
			document.readOnly [ resource |
				if (resource.modificationStamp != document.modificationStamp) {
					resource.reparse(document.get)
				}
				resource.partition
			]
		} catch (Exception e) {
			document.emergencyPartition // try to salvage what we can
		}
	}

	def protected Optional<? extends STCorePartition> getEmergencyPartition(XtextDocument document) {
		Optional.of(new STAlgorithmPartition(null, ECollections.emptyEList, document.get, newBasicEList(document.get.newLostAndFound(0))))
	}

	override getEmergencyPartition(XtextResource resource) {
		throw new UnsupportedOperationException // don't try to serialize the document but get it from the document instead
	}

	override getEmergencyPartition(STAlgorithmSource source) {
		throw new UnsupportedOperationException // don't try to use the node model but get it from the document instead
	}
}
