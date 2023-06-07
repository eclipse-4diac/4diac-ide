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
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.resource.XtextResource

import static extension org.eclipse.emf.common.util.ECollections.*
import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

class STAlgorithmPartitioner {
	final static String LOST_AND_FOUND_NAME = "LOST_AND_FOUND"

	@Inject
	extension STAlgorithmGrammarAccess grammarAccess

	def String combine(FBType fbType) {
		if(fbType instanceof BaseFBType) fbType.combine else ""
	}

	def String combine(BaseFBType fbType) {
		fbType.callables.combine
	}

	def String combine(EList<? extends ICallable> callables) {
		callables.map[toSTText].join
	}

	def dispatch String toSTText(ICallable callable) { "" }

	def dispatch String toSTText(org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm) {
		val text = algorithm.text
		if (text.contains(STAlgorithmAccess.ALGORITHMKeyword_0.value) ||
			text.contains(STAlgorithmAccess.END_ALGORITHMKeyword_3.value)) {
			text
		} else {
			algorithm.generateAlgorithmDefinition
		}
	}

	def dispatch String toSTText(org.eclipse.fordiac.ide.model.libraryElement.STMethod method) { method.text }

	def EList<ICallable> partition(XtextResource resource) {
		val source = resource.contents.get(0)
		if (source instanceof STAlgorithmSource) {
			source.partition
		} else {
			resource.emergencyPartition // try to salvage what we can
		}
	}

	def protected EList<ICallable> getEmergencyPartition(XtextResource resource) {
		val stream = new ByteArrayOutputStream
		resource.save(stream, emptyMap)
		val text = new String(stream.toByteArray, resource.encoding)
		if (text.nullOrEmpty) {
			throw new IllegalStateException("Cannot serialize contents from resource")
		}
		newBasicEList(text.newLostAndFound(0))
	}

	def EList<ICallable> partition(STAlgorithmSource source) {
		try {
			val result = source.elements.map[convertSourceElement].filterNull.<ICallable>newBasicEList
			source.handleLostAndFound(result) // salvage unclaimed content
			result.handleDuplicates // handle duplicate names
			result
		} catch (Exception e) {
			source.emergencyPartition // try to salvage what we can
		}
	}

	def protected EList<ICallable> getEmergencyPartition(STAlgorithmSource source) {
		val text = source.node?.rootNode?.text
		if (text.nullOrEmpty) {
			throw new IllegalStateException("Cannot get text from root node")
		}
		newBasicEList(text.newLostAndFound(0))
	}

	def protected dispatch convertSourceElement(STAlgorithm algorithm) {
		val node = algorithm.findActualNodeFor
		if (node === null || node.text.nullOrEmpty) {
			return null
		}
		LibraryElementFactory.eINSTANCE.createSTAlgorithm => [
			name = algorithm.name ?: LOST_AND_FOUND_NAME
			comment = node.extractComments
			text = node.text
		]
	}

	def protected dispatch convertSourceElement(STMethod method) {
		val node = method.findActualNodeFor
		if (node === null || node.text.nullOrEmpty) {
			return null
		}
		LibraryElementFactory.eINSTANCE.createSTMethod => [
			name = method.name ?: LOST_AND_FOUND_NAME
			comment = node.extractComments
			inputParameters.addAll(method.inputParameters.filter(STVarDeclaration).map[convertParameter(true)])
			outputParameters.addAll(method.outputParameters.filter(STVarDeclaration).map[convertParameter(false)])
			inOutParameters.addAll(method.inOutParameters.filter(STVarDeclaration).map[convertParameter(false)])
			returnType = method.returnType
			text = node.text
		]
	}

	def protected convertParameter(STVarDeclaration declaration, boolean input) {
		if (declaration.name.nullOrEmpty) {
			return null
		}
		LibraryElementFactory.eINSTANCE.createVarDeclaration => [
			name = declaration.name
			type = declaration.type as DataType
			if (declaration.array) {
				arraySize = declaration.extractArraySize
			}
			isInput = input
		]
	}

	def protected String extractArraySize(STVarDeclaration declaration) {
		declaration.findNodesForFeature(STCorePackage.eINSTANCE.STVarDeclaration_Ranges).map[text].join(",")
	}

	def protected extractComments(ICompositeNode node) {
		node.rootNode.text.substring(node.totalOffset, node.offset).trim
	}

	def protected handleDuplicates(EList<ICallable> result) {
		result.forEach [ algorithm, index |
			var count = 0
			var original = algorithm.name
			while (result.take(index).exists[name == algorithm.name]) {
				algorithm.name = original.generateDuplicateName(count++)
			}
		]
	}

	def protected String generateDuplicateName(String name, int count) '''«name»_«count»'''

	def protected handleLostAndFound(STAlgorithmSource source, EList<ICallable> result) {
		var lastOffset = 0;
		for (element : source.elements) {
			val node = element.findActualNodeFor
			val totalOffset = node.totalOffset
			if (totalOffset > lastOffset) {
				source.handleLostAndFound(result.size, lastOffset, totalOffset, result)
			}
			lastOffset = node.totalEndOffset
		}
		val totalEndOffset = source.node.rootNode.totalEndOffset
		if (totalEndOffset > lastOffset) {
			if (result.empty)
				source.handleLostAndFound(result.size, lastOffset, totalEndOffset, result)
			else
				result.last.appendText(source.node.rootNode.text.substring(lastOffset, totalEndOffset))
		}
	}

	def protected void handleLostAndFound(STAlgorithmSource source, int index, int start, int end,
		EList<ICallable> result) {
		val text = source.node.rootNode.text.substring(start, end)
		if (!text.trim.empty) {
			result.add(text.newLostAndFound(index))
		}
	}

	def protected dispatch appendText(org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm, String text) {
		algorithm.text = algorithm.text + text
	}

	def protected dispatch appendText(org.eclipse.fordiac.ide.model.libraryElement.STMethod method, String text) {
		method.text = method.text + text
	}

	def protected newLostAndFound(String content, int index) {
		LibraryElementFactory.eINSTANCE.createSTMethod => [
			name = index.generateLostAndFoundName
			comment = index.generateLostAndFoundComment
			text = content
		]
	}

	def protected String generateLostAndFoundName(int index) '''«LOST_AND_FOUND_NAME»_«index»'''

	def protected String generateLostAndFoundComment(int index) '''// lost+found «index»'''

	def protected String generateAlgorithmDefinition(
		org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm) '''
		ALGORITHM «algorithm.name»
		«algorithm.text»
		END_ALGORITHM
		
	'''
}
