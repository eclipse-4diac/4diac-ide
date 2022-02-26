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

import com.google.inject.Inject
import java.io.ByteArrayOutputStream
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.resource.XtextResource

import static extension org.eclipse.emf.common.util.ECollections.*
import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

class STAlgorithmPartitioner {
	
	@Inject
	extension STAlgorithmGrammarAccess grammarAccess

	def String combine(BaseFBType fbType) {
		fbType.algorithm.combine
	}

	def String combine(EList<? extends Algorithm> algorithms) {
		algorithms.filter(org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm).map[toSTAlgorithmText].join + "\n"
	}

	def String toSTAlgorithmText(org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm) {
		val text = algorithm.text
		if (text.contains(STAlgorithmAccess.ALGORITHMKeyword_0.value) || text.contains(STAlgorithmAccess.END_ALGORITHMKeyword_3.value)) {
			text
		} else {
			algorithm.generateAlgorithmDefinition
		}
	}

	def EList<Algorithm> partition(XtextResource resource) {
		val source = resource.contents.get(0)
		if (source instanceof STAlgorithmSource) {
			source.partition
		} else {
			resource.emergencyPartition // try to salvage what we can
		}
	}

	def protected EList<Algorithm> getEmergencyPartition(XtextResource resource) {
		val stream = new ByteArrayOutputStream
		resource.save(stream, emptyMap)
		val text = new String(stream.toByteArray, resource.encoding)
		if (text.nullOrEmpty) {
			throw new IllegalStateException("Cannot serialize contents from resource")
		}
		newBasicEList(text.newLostAndFound(0))
	}

	def EList<Algorithm> partition(STAlgorithmSource source) {
		try {
			val result = source.algorithms.map[convertSTAlgorithm].filterNull.<Algorithm>newBasicEList
			source.handleLostAndFound(result) // salvage unclaimed content
			result.handleDuplicates // handle duplicate names
			result
		} catch (Exception e) {
			source.emergencyPartition // try to salvage what we can
		}
	}

	def protected EList<Algorithm> getEmergencyPartition(STAlgorithmSource source) {
		val text = source.node?.rootNode?.text
		if (text.nullOrEmpty) {
			throw new IllegalStateException("Cannot get text from root node")
		}
		newBasicEList(text.newLostAndFound(0))
	}

	def protected convertSTAlgorithm(STAlgorithm algorithm) {
		if (algorithm.name.nullOrEmpty) {
			return null
		}
		val node = algorithm.findActualNodeFor
		if (node === null || node.text.nullOrEmpty) {
			return null
		}
		LibraryElementFactory.eINSTANCE.createSTAlgorithm => [
			name = algorithm.name
			comment = node.extractComments
			text = node.text
		]
	}

	def protected extractComments(ICompositeNode node) {
		node.rootNode.text.substring(node.totalOffset, node.offset).trim
	}

	def protected handleDuplicates(EList<Algorithm> result) {
		result.forEach [ algorithm, index |
			var count = 0
			var original = algorithm.name
			while (result.take(index).exists[name == algorithm.name]) {
				algorithm.name = original.generateDuplicateName(count++)
			}
		]
	}

	def protected String generateDuplicateName(String name, int count) '''«name»_«count»'''

	def protected handleLostAndFound(STAlgorithmSource source, EList<Algorithm> result) {
		var lastOffset = 0;
		var lostAndFoundIndex = 0;
		for (algorithm : source.algorithms) {
			val node = algorithm.findActualNodeFor
			val totalOffset = node.totalOffset
			if (totalOffset > lastOffset) {
				source.handleLostAndFound(lostAndFoundIndex++, lastOffset, totalOffset, result)
			}
			lastOffset = node.totalEndOffset
		}
		val totalEndOffset = source.node.rootNode.totalEndOffset
		if (totalEndOffset > lastOffset) {
			source.handleLostAndFound(lostAndFoundIndex++, lastOffset, totalEndOffset, result)
		}
	}

	def protected void handleLostAndFound(STAlgorithmSource source, int index, int start, int end,
		EList<Algorithm> result) {
		val text = source.node.rootNode.text.substring(start, end)
		if (!text.trim.empty) {
			result.add(text.newLostAndFound(index))
		}
	}

	def protected newLostAndFound(String content, int index) {
		LibraryElementFactory.eINSTANCE.createSTAlgorithm => [
			name = index.generateLostAndFoundName
			comment = index.generateLostAndFoundComment
			text = content
		]
	}

	def protected String generateLostAndFoundName(int index) '''LOST_AND_FOUND_«index»'''

	def protected String generateLostAndFoundComment(int index) '''// lost+found «index»'''

	def protected String generateAlgorithmDefinition(
		org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm) '''
		ALGORITHM «algorithm.name»
		«algorithm.text»
		END_ALGORITHM
		
	'''
}
