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
package org.eclipse.fordiac.ide.structuredtextcore.util

import com.google.inject.Inject
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.parsetree.reconstr.ICommentAssociater

import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

class STCoreCommentAssociater {
	@Inject
	ICommentAssociater commentAssociater

	def List<STComment> associateComments(STSource source) {
		commentAssociater.associateCommentsWithSemanticEObjects(source, source.roots).entrySet.sortBy [
			key.offset
		].map[newComment(key, value)]
	}

	def protected Set<ICompositeNode> getRoots(STSource source) {
		switch (node : source.findActualNodeFor) { case node !== null: #{node} default: emptySet }
	}

	def protected STComment newComment(ILeafNode commentNode, EObject semanticObject) {
		STCoreFactory.eINSTANCE.createSTComment => [
			context = semanticObject
			text = commentNode.text
			position = commentNode.computeRelativePosition(semanticObject)
		]
	}

	def protected computeRelativePosition(ILeafNode commentNode, EObject semanticObject) {
		val semanticNode = semanticObject.findActualNodeFor
		if (commentNode.offset < semanticNode.offset)
			STCommentPosition.BEFORE
		else if (commentNode.offset > semanticNode.endOffset)
			STCommentPosition.AFTER
		else
			STCommentPosition.INNER
	}
}
